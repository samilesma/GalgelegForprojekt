package com.u_09.galgeleg.Model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by Umais on 07/04/2017.
 */

public class GalgelogikFunc {

    private static final String REST_ROOT_URL = "http://galgeleg.dk/GalgelegWeb/AndroidServlet";
    private static final String PARAMETER_TYPE = "?type=";
    private static final String PARAMETER_USERNAME = "&username=";
    private static final String PARAMETER_PASSWORD = "&password=";
    private static final String TYPE_LOGIN = "login";
    private static final String TYPE_ERSIDSTEBOGSTAVKORREKT = "erSidsteBogstavKorrekt";
    private static final String TYPE_ERSPILLETSLUT = "erSpilletSlut";
    private static final String TYPE_ERSPILLETTABT = "erSpilletTabt";
    private static final String TYPE_ERSPILLETVUNDET = "erSpilletVundet";
    private static final String TYPE_GETANTALFORKERTEBOGSTAVER = "getAntalForkerteBogstaver";
    private static final String TYPE_GETBRUGTEBOGSTAVER = "getBrugteBogstaver";
    private static final String TYPE_GETORDET = "getOrdet";
    private static final String TYPE_GETSYNLIGTORD = "getSynligtOrd";
    private static final String TYPE_GÆTBOGSTAV = "gætBogstav";
    private static final String PARAMETER_BOGSTAV = "&bogstav=";
    private static final String TYPE_HENTNAVN = "hentNavn";
    private static final String TYPE_HENTORDFRADR = "hentOrdFraDr";
    private static final String TYPE_HENTURL = "hentUrl";
    private static final String TYPE_GETMULIGEORD = "getMuligeOrd";
    private static final String TYPE_NULSTIL = "nulstil";
    private static final String TYPE_OPDATERSYNLIGTORD = "opdaterSynligtOrd";
    private static final String TYPE_SETORDET = "setOrdet";
    private static final String PARAMETER_I = "&i=";

    public boolean erSidsteBogstavKorrekt() throws ExecutionException, InterruptedException, JSONException {
        JSONObject returnObj = new JSONObject(new Web().execute(REST_ROOT_URL + PARAMETER_TYPE + TYPE_ERSIDSTEBOGSTAVKORREKT).get());
        return returnObj.getBoolean("sidsteBogstavVarKorrekt");
    }

    public boolean erSpilletSlut() throws ExecutionException, InterruptedException, JSONException {
        JSONObject returnObj = new JSONObject(new Web().execute(REST_ROOT_URL + PARAMETER_TYPE + TYPE_ERSPILLETSLUT).get());
        return returnObj.getBoolean("spilletErSlut");
    }

    public boolean erSpilletTabt() throws ExecutionException, InterruptedException, JSONException {
        JSONObject returnObj = new JSONObject(new Web().execute(REST_ROOT_URL + PARAMETER_TYPE + TYPE_ERSPILLETTABT).get());
        return returnObj.getBoolean("spilletErTabt");
    }

    public boolean erSpilletVundet() throws ExecutionException, InterruptedException, JSONException {
        JSONObject returnObj = new JSONObject(new Web().execute(REST_ROOT_URL + PARAMETER_TYPE + TYPE_ERSPILLETVUNDET).get());
        return returnObj.getBoolean("spilletErVundet");
    }

    public int getAntalForkerteBogstaver() throws ExecutionException, InterruptedException, JSONException {
        JSONObject returnObj = new JSONObject(new Web().execute(REST_ROOT_URL + PARAMETER_TYPE + TYPE_GETANTALFORKERTEBOGSTAVER).get());
        return returnObj.getInt("antalForkerteBogstaver");
    }

    public ArrayList<String> getBrugteBogstaver() throws ExecutionException, InterruptedException, JSONException {
        JSONObject returnObj = new JSONObject(new Web().execute(REST_ROOT_URL + PARAMETER_TYPE + TYPE_GETBRUGTEBOGSTAVER).get());
        JSONArray jsonArray = returnObj.getJSONArray("brugteBogstaver");
        ArrayList<String> brugteBogstaver = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            brugteBogstaver.add(jsonArray.getString(i));
        }
        return brugteBogstaver;
    }

    public ArrayList<String> getMuligeOrd() throws ExecutionException, InterruptedException, JSONException {
        JSONObject returnObj = new JSONObject(new Web().execute(REST_ROOT_URL + PARAMETER_TYPE + TYPE_GETMULIGEORD).get());
        JSONArray jsonArray = returnObj.getJSONArray("muligeOrd");
        ArrayList<String> muligeOrd = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            muligeOrd.add(jsonArray.getString(i));
        }
        return muligeOrd;
    }

    public String getOrdet() throws ExecutionException, InterruptedException, JSONException {
        JSONObject returnObj = new JSONObject(new Web().execute(REST_ROOT_URL + PARAMETER_TYPE + TYPE_GETORDET).get());
        return returnObj.getString("ordet");
    }

    public String getSynligtOrd() throws ExecutionException, InterruptedException, JSONException {
        JSONObject returnObj = new JSONObject(new Web().execute(REST_ROOT_URL + PARAMETER_TYPE + TYPE_GETSYNLIGTORD).get());
        return returnObj.getString("synligtOrd");
    }

    public void gætBogstav(String bogstav) throws ExecutionException, InterruptedException, JSONException {
        new Web().execute(REST_ROOT_URL + PARAMETER_TYPE + TYPE_GÆTBOGSTAV + PARAMETER_BOGSTAV + bogstav);
    }

    public boolean hentBruger(String username, String password) throws ExecutionException, InterruptedException, JSONException {
        JSONObject data = new JSONObject(new Web().execute(REST_ROOT_URL + PARAMETER_TYPE + TYPE_LOGIN + PARAMETER_USERNAME + username + PARAMETER_PASSWORD + password).get());
        return data.getBoolean("error");
    }

    public String hentNavn() throws ExecutionException, InterruptedException, JSONException {
        JSONObject returnObj = new JSONObject(new Web().execute(REST_ROOT_URL + PARAMETER_TYPE + TYPE_HENTNAVN).get());
        return returnObj.getString("fuldenavn");
    }

    public void hentOrdFraDr() throws ExecutionException, InterruptedException, JSONException {
        new Web().execute(REST_ROOT_URL + PARAMETER_TYPE + TYPE_HENTORDFRADR);
    }

    public void hentUrl() throws ExecutionException, InterruptedException, JSONException {
        new Web().execute(REST_ROOT_URL + PARAMETER_TYPE + TYPE_HENTURL);
    }

    public void nulstil() throws ExecutionException, InterruptedException, JSONException {
        new Web().execute(REST_ROOT_URL + PARAMETER_TYPE + TYPE_NULSTIL);
    }

    public void opdaterSynligtOrd() throws ExecutionException, InterruptedException, JSONException {
        new Web().execute(REST_ROOT_URL + PARAMETER_TYPE + TYPE_OPDATERSYNLIGTORD);
    }

    public void setOrdet(int i) throws ExecutionException, InterruptedException, JSONException {
        new Web().execute(REST_ROOT_URL + PARAMETER_TYPE + TYPE_SETORDET + PARAMETER_I + i);
    }
}