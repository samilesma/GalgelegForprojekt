package galgeleg;

import brugerautorisation.data.Bruger;
import brugerautorisation.transport.soap.Brugeradmin;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jws.WebService;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import org.json.JSONObject;

@WebService(endpointInterface = "galgeleg.GalgeI")

public class Galgelogik implements GalgeI {
    
    private ArrayList<String> muligeOrd = new ArrayList<String>();
    private String ordet;
    private ArrayList<String> brugteBogstaver = new ArrayList<String>();
    private Brugeradmin ba;
    private Bruger b;
    private String synligtOrd;
    private int antalForkerteBogstaver;
    private boolean sidsteBogstavVarKorrekt;
    private boolean spilletErVundet;
    private boolean spilletErTabt;
    private HashMap<String,Galgelogik> users=new HashMap<String,Galgelogik>();
    
    public Galgelogik() throws MalformedURLException {
        hentOrdFraDr();
        URL url = new URL("http://javabog.dk:9901/brugeradmin?wsdl");
        QName qname = new QName("http://soap.transport.brugerautorisation/", "BrugeradminImplService");
        Service service = Service.create(url, qname);
        ba = service.getPort(Brugeradmin.class);
        nulstil();
    }
    
    public boolean erSidsteBogstavKorrekt() {
        return sidsteBogstavVarKorrekt;
    }
    
    public boolean erSpilletSlut() {
        return spilletErTabt || spilletErVundet;
    }
    
    public boolean erSpilletTabt() {
        return spilletErTabt;
    }
    
    public boolean erSpilletVundet() {
        return spilletErVundet;
    }
    
    public int getAntalForkerteBogstaver() {
        return antalForkerteBogstaver;
    }
    
    public ArrayList<String> getBrugteBogstaver() {
        return brugteBogstaver;
    }
    
    public ArrayList<String> getMuligeOrd() {
        return muligeOrd;
    }
    
    public String getOrdet() {
        return ordet;
    }
    
    public String getSynligtOrd() {
        return synligtOrd;
    }
    
    public void gætBogstav(String bogstav) {
        if (bogstav.length() != 1) {
            return;
        }
        System.out.println("Der gættes på bogstavet: " + bogstav);
        if (brugteBogstaver.contains(bogstav)) {
            return;
        }
        if (spilletErVundet || spilletErTabt) {
            return;
        }

        brugteBogstaver.add(bogstav);

        if (ordet.contains(bogstav)) {
            sidsteBogstavVarKorrekt = true;
            System.out.println("Bogstavet var korrekt: " + bogstav);
        } else {
            // Vi gættede på et bogstav der ikke var i ordet.
            sidsteBogstavVarKorrekt = false;
            System.out.println("Bogstavet var IKKE korrekt: " + bogstav);
            antalForkerteBogstaver = antalForkerteBogstaver + 1;
            if (antalForkerteBogstaver > 6) {
                spilletErTabt = true;
            }
        }
        opdaterSynligtOrd();
    }
    
    public boolean hentBruger(String brugernavn, String adgangskode) {
        try {
            b = ba.hentBruger(brugernavn, adgangskode);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        
        try {
            put(brugernavn,new Galgelogik());
            users.get(brugernavn).b=b;
        } catch (MalformedURLException ex) {
            Logger.getLogger(Galgelogik.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return true;
    }
    
    public String hentNavn() {
        return b.fornavn + " " + b.efternavn;
    }
    
    public void hentOrdFraDr() {
        String data = "";
        String svar = hentUrl("http://www.dr.dk/mu-online/api/1.3/list/view/mostviewed?channel=dr1"), svar2 = "", link = "";
        try {
            JSONObject json = new JSONObject(svar);
            for (int i = 1; i <= 3; i++) {
                link = link + "\n" + "http://www.dr.dk/mu-online/api/1.3/programcard/" + json.getJSONArray("Items").getJSONObject(i - 1).getString("Slug");
                svar2 = hentUrl("http://www.dr.dk/mu-online/api/1.3/programcard/" + json.getJSONArray("Items").getJSONObject(i - 1).getString("Slug"));
                JSONObject json2 = new JSONObject(svar2);
                data = data + " " + json2.getString("Description");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //*/

        System.out.println("Link:\n" + link + "\n");

        System.out.println("data = " + data);

        data = data.replaceAll("[^a-zæøå]", " ");
        System.out.println("data = " + data);
        muligeOrd.clear();
        muligeOrd.addAll(new HashSet<String>(Arrays.asList(data.split(" "))));

        System.out.println("muligeOrd = " + muligeOrd);
        nulstil();
    }
    
    public String hentUrl(String url) {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(new URL(url).openStream()));
        } catch (MalformedURLException ex) {
            Logger.getLogger(Galgelogik.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Galgelogik.class.getName()).log(Level.SEVERE, null, ex);
        }
        StringBuilder sb = new StringBuilder();
        String linje = null;
        try {
            linje = br.readLine();
        } catch (IOException ex) {
            Logger.getLogger(Galgelogik.class.getName()).log(Level.SEVERE, null, ex);
        }
        while (linje != null) {
            sb.append(linje + "\n");
            try {
                linje = br.readLine();
            } catch (IOException ex) {
                Logger.getLogger(Galgelogik.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return sb.toString();
    }
    
    public void logStatus() {
        System.out.println("---------- ");
        System.out.println("- ordet (skult) = " + ordet);
        System.out.println("- synligtOrd = " + synligtOrd);
        System.out.println("- forkerteBogstaver = " + antalForkerteBogstaver);
        System.out.println("- brugeBogstaver = " + brugteBogstaver);
        if (spilletErTabt) {
            System.out.println("- SPILLET ER TABT");
        }
        if (spilletErVundet) {
            System.out.println("- SPILLET ER VUNDET");
        }
        System.out.println("---------- ");
    }
    
    public void nulstil() {
        brugteBogstaver.clear();
        antalForkerteBogstaver = 0;
        spilletErVundet = false;
        spilletErTabt = false;
        ordet = muligeOrd.get(new Random().nextInt(muligeOrd.size()));
        opdaterSynligtOrd();
    }
    
    public void opdaterSynligtOrd() {
        synligtOrd = "";
        spilletErVundet = true;
        for (int n = 0; n < ordet.length(); n++) {
            String bogstav = ordet.substring(n, n + 1);
            if (brugteBogstaver.contains(bogstav)) {
                synligtOrd = synligtOrd + bogstav;
            } else {
                synligtOrd = synligtOrd + "*";
                spilletErVundet = false;
            }
        }
    }
    
    public void setOrdet(int i) {
        this.ordet = muligeOrd.get(i);
    }
    
    public String get(String... p) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        Method method = Galgelogik.class.getDeclaredMethod(p[1]);
        if(p.length>2) return (String) method.invoke(users.get(p[0]),Arrays.copyOfRange(p,2,p.length-1));
        else return (String) method.invoke(users.get(p[0]));
    }
    
    public int getint(String... p) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        Integer ret;
        Method method = Galgelogik.class.getDeclaredMethod(p[1]);
        if(p.length>2) ret = (Integer) method.invoke(users.get(p[0]),Arrays.copyOfRange(p,2,p.length-1));
        else ret = (Integer) method.invoke(users.get(p[0]));
        return ret.intValue();
    }
    
    public ArrayList<String> getlist(String... p) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        Method method = Galgelogik.class.getDeclaredMethod(p[1]);
        if(p.length>2) return (ArrayList<String>) method.invoke(users.get(p[0]),Arrays.copyOfRange(p,2,p.length-1));
        else return (ArrayList<String>) method.invoke(users.get(p[0]));
    }
    
    public boolean check(String... p) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        Boolean ret;
        Method method = Galgelogik.class.getDeclaredMethod(p[1]);
        if(p.length>2) ret = (Boolean) method.invoke(users.get(p[0]),Arrays.copyOfRange(p,2,p.length-1));
        else ret = (Boolean) method.invoke(users.get(p[0]));
        return ret.booleanValue();
    }
    
    public void doit(String... p) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        Method method = Galgelogik.class.getDeclaredMethod(p[1]);
        if(p.length>2) method.invoke(users.get(p[0]),Arrays.copyOfRange(p,2,p.length-1));
        else method.invoke(users.get(p[0]));
    }
    
    public void put(String sid, Galgelogik g) {
        users.put(sid,g);
    }
    
    public void remove(String sid) {
        users.remove(sid);
    }
    
    public void print() {
        System.out.println("\n[");
        for (Map.Entry<String,Galgelogik> entry : users.entrySet()) {
            String key = entry.getKey();
            Galgelogik value = entry.getValue();

            System.out.println("\t{");
            System.out.println("\t\tSID :\t"+key+",");
            System.out.println("\t\tName:\t"+value.hentNavn()+",");
            System.out.println("\t\tName:\t"+value.getOrdet()+",");
            System.out.println("\t},");
        }
        System.out.println("]\n");
    }
}
