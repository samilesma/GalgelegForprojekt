package galgeleg;

import java.util.Map;
import java.util.Scanner;
import javax.xml.ws.Endpoint;

public class GalgelegServer {
    public static void main(String[] arg) throws Exception
    {
        System.out.println("publicerer Galgeleg");
        GalgeI g = new Galgelogik();
        Endpoint.publish("http://[::]:55556/galgeleg", g);
//        Endpoint.publish("http://ubuntu4.javabog.dk:55556/galgeleg", g);
        
        System.out.println("Galgeleg publiceret.");
        
        while(true)
        {
            Scanner reader = new Scanner(System.in);
            String input = reader.nextLine();
            
            if(input.equals("print")) g.print();
            else if(input.contains(" ")) g.users(input);
        }
    }
}
