package com.u_09.galgeleg.Model;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.net.Proxy;

/**
 * Created by Umais on 07/04/2017.
 */

public class GalgelogikFunc {

    private static final String NAMESPACE = "http://ubuntu4.javabog.dk:55556/galgeleg";
    private static String METHOD_NAME = "logStatus";
    private static final String MAIN_REQUEST_URL = "http://ubuntu4.javabog.dk:55556/galgeleg?wsdl";
    private static final String SOAP_ACTION = "http://ubuntu4.javabog.dk:55556/galgeleg/";

    public static void main(String[] args) throws IOException, XmlPullParserException {

        //Initialize soap request + add parameters
        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

        //Use this to add parameters
        //request.addProperty("Parameter","Value");

        //Declare the version of the SOAP request
        SoapSerializationEnvelope envelope = getSoapSerializationEnvelope(request);
        envelope.setOutputSoapObject(request);

        //Needed to make the internet call
        HttpTransportSE androidHttpTransport = getHttpTransportSE();
        try {
            //this is the actual part that will call the webservice
            androidHttpTransport.call(SOAP_ACTION, envelope);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Get the SoapResult from the envelope body.
        SoapObject result = (SoapObject) envelope.getResponse();



        if(result != null){
            System.out.println(result.toString());
            System.out.println(result.getProperty(0).toString());
        }
    }

    /*
    URL url = new URL("http://ubuntu4.javabog.dk:55556/galgeleg?wsdl");
    QName qname = new QName("http://galgeleg/", "GalgelogikService");
    Service service = Service.create(url, qname);
    GalgeI spil = service.getPort(GalgeI.class);
    */

    public static SoapSerializationEnvelope getSoapSerializationEnvelope(SoapObject request) {
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.implicitTypes = true;
        envelope.setAddAdornments(false);
        envelope.setOutputSoapObject(request);

        return envelope;
    }

    public static HttpTransportSE getHttpTransportSE() {
        HttpTransportSE ht = new HttpTransportSE(Proxy.NO_PROXY,MAIN_REQUEST_URL,60000);
        ht.debug = true;
        ht.setXmlVersionTag("<!--?xml version=\"1.0\" encoding= \"UTF-8\" ?-->");
        return ht;
    }

}