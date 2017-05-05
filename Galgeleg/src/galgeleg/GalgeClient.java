package galgeleg;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;

public class GalgeClient {
    
    public static void main(String[] args) throws MalformedURLException {
        URL url = new URL("http://ubuntu4.javabog.dk:55556/galgeleg?wsdl");
//        URL url = new URL("http://localhost:55556/galgeleg?wsdl");
        QName qname = new QName("http://galgeleg/", "GalgelogikService");
        Service service = Service.create(url, qname);
        GalgeI spil = service.getPort(GalgeI.class);
              
        Scanner s = new Scanner(System.in);
        String tast;
        
        while(true){
            System.out.println("Indtast dit DTU-brugernavn:");
            String inUser = s.nextLine();

            System.out.println("Indtast nu dit DTU-password:");
            String inPass = s.nextLine();
            
            if(spil.hentBruger(inUser, inPass)){
                System.out.println("Du er logget ind som "+inUser+". \n Du kan nu spille.\n\n\n\n\n\n\n\n\n\n\n\n");
                                            System.out.println(spil.hentNavn());

                break;
            }else System.out.println("Dit brugernavn eller adgangskode er forkert. \nPrøv igen:");
        }
        
        spil.nulstil();
        spil.logStatus();
        System.out.println(spil.getSynligtOrd());

        System.out.println("Gæt et bogstav");
        
        while(!spil.erSpilletSlut()){
            tast = s.nextLine();
            spil.gaetBogstav(tast);
            spil.logStatus();
            System.out.println("Brugte bogstaver: "+spil.getBrugteBogstaver());
            System.out.println("Antal forkerte bogstaver: " + spil.getAntalForkerteBogstaver());
            System.out.println("" + spil.getSynligtOrd());
            if (spil.erSpilletVundet()){
                System.out.println("Tillykke du har vundet\nVil du spille igen? (Ja/Nej)");
                if(s.nextLine().toString().equalsIgnoreCase("Ja")){
                    spil.nulstil();
                }else break;
            }
            if (spil.erSpilletTabt()){
                System.out.println("Du har tabt spillet, ordet var " + spil.getOrdet() +"\n");
            }
        }
    }
}
