package galgeleg;

import brugerautorisation.data.Bruger;
import brugerautorisation.transport.soap.Brugeradmin;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jws.WebService;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.json.JSONArray;
import org.json.JSONObject;

@WebService(endpointInterface = "galgeleg.GalgeI")

public class Galgelogik implements GalgeI{
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
    
    
    public ArrayList<String> getBrugteBogstaver() {
        return brugteBogstaver;
    }
    
    public String getSynligtOrd() {
        return synligtOrd;
    }
    
    public String getOrdet() {
        return ordet;
    }
    
    public int getAntalForkerteBogstaver() {
        return antalForkerteBogstaver;
    }
    
    public boolean erSidsteBogstavKorrekt() {
        return sidsteBogstavVarKorrekt;
    }
    
    public boolean erSpilletVundet() {
        return spilletErVundet;
    }
    
    public boolean erSpilletTabt() {
        return spilletErTabt;
    }
    
    public boolean erSpilletSlut() {
        return spilletErTabt || spilletErVundet;
    }
    
    
    public Galgelogik() throws MalformedURLException{
        hentOrdFraDr();
        URL url = new URL("http://javabog.dk:9901/brugeradmin?wsdl");
        QName qname = new QName("http://soap.transport.brugerautorisation/", "BrugeradminImplService");
        Service service = Service.create(url, qname);
        ba = service.getPort(Brugeradmin.class);
        nulstil();
    }
    
    @Override
    public boolean hentBruger(String brugernavn, String adgangskode){
        try {
            b = ba.hentBruger(brugernavn, adgangskode);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    
    public void nulstil(){
        brugteBogstaver.clear();
        antalForkerteBogstaver = 0;
        spilletErVundet = false;
        spilletErTabt = false;
        ordet = muligeOrd.get(new Random().nextInt(muligeOrd.size()));
        opdaterSynligtOrd();
    }
    
    
    @Override
    public void opdaterSynligtOrd(){
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
    
    public void gætBogstav(String bogstav){
        if (bogstav.length() != 1) return;
        System.out.println("Der gættes på bogstavet: " + bogstav);
        if (brugteBogstaver.contains(bogstav)) return;
        if (spilletErVundet || spilletErTabt) return;
        
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
    
    public void logStatus() {
        System.out.println("---------- ");
        System.out.println("- ordet (skult) = " + ordet);
        System.out.println("- synligtOrd = " + synligtOrd);
        System.out.println("- forkerteBogstaver = " + antalForkerteBogstaver);
        System.out.println("- brugeBogstaver = " + brugteBogstaver);
        if (spilletErTabt) System.out.println("- SPILLET ER TABT");
        if (spilletErVundet) System.out.println("- SPILLET ER VUNDET");
        System.out.println("---------- ");
    }
    
    @Override
    public String hentUrl(String url){
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
    
    @Override
    public void hentOrdFraDr(){
        String data="";
        String svar = hentUrl("http://www.dr.dk/mu-online/api/1.3/list/view/mostviewed?channel=dr1"), svar2="",link="";
        try {
            JSONObject json = new JSONObject(svar);
            for(int i=1;i<=3;i++)
            {
                link=link+"\n"+"http://www.dr.dk/mu-online/api/1.3/programcard/"+json.getJSONArray("Items").getJSONObject(i-1).getString("Slug");
                svar2 = hentUrl("http://www.dr.dk/mu-online/api/1.3/programcard/"+json.getJSONArray("Items").getJSONObject(i-1).getString("Slug"));
                JSONObject json2 = new JSONObject(svar2);
                data=data+" "+json2.getString("Description");
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

    @Override
    public String hentNavn() {
        return b.fornavn + " " + b.efternavn;
    }
}
