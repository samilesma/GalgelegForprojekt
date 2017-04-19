
package galgeleg;

import javax.xml.ws.Endpoint;

public class GalgelegServer {
    public static void main(String[] arg) throws Exception
	{
		
                System.out.println("publicerer Galgeleg");
		GalgeI g = new Galgelogik();
                
                Endpoint.publish("http://[::]:55556/galgeleg", g);
		System.out.println("Galgeleg publiceret.");
	}
}
