package galgeleg;

import java.util.ArrayList;
import javax.jws.WebMethod;
import javax.jws.WebService;
@WebService
public interface GalgeI
{
    @WebMethod public ArrayList<String> getBrugteBogstaver();
    
    @WebMethod public String getSynligtOrd();
    
    @WebMethod public String getOrdet();
    
    @WebMethod public int getAntalForkerteBogstaver();
    
    @WebMethod public boolean hentBruger(String brugernavn, String adgangskode);
    
    @WebMethod public boolean erSidsteBogstavKorrekt();
    
    @WebMethod public boolean erSpilletVundet();
    
    @WebMethod public boolean erSpilletTabt();
    
    @WebMethod public boolean erSpilletSlut();
    
    @WebMethod public void nulstil();
    
    @WebMethod public void opdaterSynligtOrd();
    
    @WebMethod public void gætBogstav(String bogstav);
    
    @WebMethod public void logStatus();
    
    @WebMethod public String hentUrl(String url);
    
    @WebMethod public void hentOrdFraDr();
}
