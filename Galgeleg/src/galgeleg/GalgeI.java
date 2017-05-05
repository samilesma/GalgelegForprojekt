package galgeleg;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public interface GalgeI {
    @WebMethod
    public boolean erSidsteBogstavKorrekt();
    
    @WebMethod
    public boolean erSpilletSlut();
    
    @WebMethod
    public boolean erSpilletTabt();
    
    @WebMethod
    public boolean erSpilletVundet();
    
    @WebMethod
    public int getAntalForkerteBogstaver();

    @WebMethod
    public ArrayList<String> getBrugteBogstaver();

    @WebMethod
    public String getMuligeOrd();

    @WebMethod
    public String getOrdet();

    @WebMethod
    public String getSynligtOrd();

    @WebMethod
    public void gaetBogstav(String bogstav);

    @WebMethod
    public boolean hentBruger(String brugernavn, String adgangskode);

    @WebMethod
    public String hentNavn();

    @WebMethod
    public void hentOrdFraDr();

    @WebMethod
    public String hentUrl(String url);

    @WebMethod
    public void logStatus();

    @WebMethod
    public void nulstil();

    @WebMethod
    public void opdaterSynligtOrd();

    @WebMethod
    public void setOrdet(int i);
    
    @WebMethod
    public String get(String... p) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException;
    
    @WebMethod
    public boolean check(String... p) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException;
    
    @WebMethod
    public void doit(String... p) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException;
    
    @WebMethod
    public int getint(String... p) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException;
    
    @WebMethod
    public ArrayList<String> getlist(String... p) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException;
    
    @WebMethod
    public void put(String sid, Galgelogik g);
    
    @WebMethod
    public void remove(String sid);
    
    @WebMethod
    public void print();
    
    @WebMethod
    public void users(String cmd);
}
