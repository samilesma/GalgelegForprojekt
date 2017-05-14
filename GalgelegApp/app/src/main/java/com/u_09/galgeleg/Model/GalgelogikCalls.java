package com.u_09.galgeleg.Model;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


public class GalgelogikCalls {

    private static final String REST_ROOT_URL = "http://galgeleg.dk/GalgelegWeb/AndroidServlet";
    private static final String PARAMETER_TYPE = "?type=";
    private static final String PARAMETER_SID = "&sid=";
    private static final String TYPE_ERSIDSTEBOGSTAVKORREKT = "erSidsteBogstavKorrekt";
    private static final String TYPE_ERSPILLETSLUT = "erSpilletSlut";
    private static final String TYPE_ERSPILLETTABT = "erSpilletTabt";
    private static final String TYPE_ERSPILLETVUNDET = "erSpilletVundet";
    private static final String TYPE_GETANTALFORKERTEBOGSTAVER = "getAntalForkerteBogstaver";
    private static final String TYPE_GETBRUGTEBOGSTAVER = "getBrugteBogstaver";
    private static final String TYPE_GETORDET = "getOrdet";
    private static final String TYPE_GETSYNLIGTORD = "getSynligtOrd";
    private static final String TYPE_GAET = "gaet";
    private static final String TYPE_GAETBOGSTAV = "gaetBogstav";
    private static final String PARAMETER_BOGSTAV = "&bogstav=";
    private static final String TYPE_HENTBRUGER = "hentBruger";
    private static final String PARAMETER_USERNAME = "&username=";
    private static final String PARAMETER_PASSWORD = "&password=";
    private static final String TYPE_HENTNAVN = "hentNavn";
    private static final String TYPE_HENTORDFRADR = "hentOrdFraDr";
    private static final String TYPE_HENTURL = "hentUrl";
    private static final String TYPE_GETMULIGEORD = "getMuligeOrd";
    private static final String TYPE_NULSTIL = "nulstil";
    private static final String TYPE_OPDATERSYNLIGTORD = "opdaterSynligtOrd";
    private static final String TYPE_SETORDET = "setOrdet";
    private static final String PARAMETER_I = "&i=";
    private static final String REST_CHAT_URL = "http://galgeleg.dk/GalgelegWeb/ChatServlet";
    private static final String TYPE_GETMESSAGE = "getmessage";
    private static final String PARAMETER_DATE = "&date";
    private static final String PARAMETER_TIME = "&time=";
    private static final String TYPE_GETHIGHSCORES = "getHighscores";
    private static final String TYPE_SEND_MSG = "sendMsg";
    private static final String PARAMETER_MSG = "&msg=";

    public boolean erSidsteBogstavKorrekt(String sid) throws ExecutionException, InterruptedException, JSONException {
        JSONObject returnObj = new JSONObject(new Web().execute(REST_ROOT_URL + PARAMETER_TYPE + TYPE_ERSIDSTEBOGSTAVKORREKT + PARAMETER_SID + sid).get());
        return returnObj.getBoolean("sidsteBogstavVarKorrekt");
    }

    public boolean erSpilletSlut(String sid) throws ExecutionException, InterruptedException, JSONException {
        JSONObject returnObj = new JSONObject(new Web().execute(REST_ROOT_URL + PARAMETER_TYPE + TYPE_ERSPILLETSLUT + PARAMETER_SID + sid).get());
        return returnObj.getBoolean("spilletErSlut");
    }

    public boolean erSpilletTabt(String sid) throws ExecutionException, InterruptedException, JSONException {
        JSONObject returnObj = new JSONObject(new Web().execute(REST_ROOT_URL + PARAMETER_TYPE + TYPE_ERSPILLETTABT + PARAMETER_SID + sid).get());
        return returnObj.getBoolean("spilletErTabt");
    }

    public boolean erSpilletVundet(String sid) throws ExecutionException, InterruptedException, JSONException {
        JSONObject returnObj = new JSONObject(new Web().execute(REST_ROOT_URL + PARAMETER_TYPE + TYPE_ERSPILLETVUNDET + PARAMETER_SID + sid).get());
        return returnObj.getBoolean("spilletErVundet");
    }

    public int getAntalForkerteBogstaver(String sid) throws ExecutionException, InterruptedException, JSONException {
        JSONObject returnObj = new JSONObject(new Web().execute(REST_ROOT_URL + PARAMETER_TYPE + TYPE_GETANTALFORKERTEBOGSTAVER + PARAMETER_SID + sid).get());
        return returnObj.getInt("antalForkerteBogstaver");
    }

    public ArrayList<String> getBrugteBogstaver(String sid) throws ExecutionException, InterruptedException, JSONException {
        JSONObject returnObj = new JSONObject(new Web().execute(REST_ROOT_URL + PARAMETER_TYPE + TYPE_GETBRUGTEBOGSTAVER + PARAMETER_SID + sid).get());
        JSONArray jsonArray = returnObj.getJSONArray("brugteBogstaver");
        ArrayList<String> brugteBogstaver = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            brugteBogstaver.add(jsonArray.getString(i));
        }
        return brugteBogstaver;
    }

    public ArrayList<String> getMuligeOrd(String sid) throws ExecutionException, InterruptedException, JSONException {
        JSONObject returnObj = new JSONObject(new Web().execute(REST_ROOT_URL + PARAMETER_TYPE + TYPE_GETMULIGEORD + PARAMETER_SID + sid).get());
        JSONArray jsonArray = returnObj.getJSONArray("muligeOrd");
        ArrayList<String> muligeOrd = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            muligeOrd.add(jsonArray.getString(i));
        }
        return muligeOrd;
    }

    public String getOrdet(String sid) throws ExecutionException, InterruptedException, JSONException {
        JSONObject returnObj = new JSONObject(new Web().execute(REST_ROOT_URL + PARAMETER_TYPE + TYPE_GETORDET + PARAMETER_SID + sid).get());
        return returnObj.getString("ordet");
    }

    public String getSynligtOrd(String sid) throws ExecutionException, InterruptedException, JSONException {
        JSONObject returnObj = new JSONObject(new Web().execute(REST_ROOT_URL + PARAMETER_TYPE + TYPE_GETSYNLIGTORD + PARAMETER_SID + sid).get());
        return returnObj.getString("synligtOrd");
    }

    public void gaetBogstav(String bogstav, String sid) throws ExecutionException, InterruptedException, JSONException {
        new Web().execute(REST_ROOT_URL + PARAMETER_TYPE + TYPE_GAETBOGSTAV + PARAMETER_BOGSTAV + bogstav + PARAMETER_SID + sid);
    }

    public JSONObject gaet(String bogstav, String sid, int time) throws ExecutionException, InterruptedException, JSONException {
        return new JSONObject(new Web().execute(REST_ROOT_URL + PARAMETER_TYPE + TYPE_GAET + PARAMETER_BOGSTAV + bogstav + PARAMETER_SID + sid + PARAMETER_TIME + time, "test").get());
    }

    public JSONObject hentBruger(String username, String password) throws ExecutionException, InterruptedException, JSONException {
        return new JSONObject(new Web().execute(REST_ROOT_URL + PARAMETER_TYPE + TYPE_HENTBRUGER + PARAMETER_USERNAME + username + PARAMETER_PASSWORD + password).get());
    }

    public String hentNavn(String sid) throws ExecutionException, InterruptedException, JSONException {
        JSONObject returnObj = new JSONObject(new Web().execute(REST_ROOT_URL + PARAMETER_TYPE + TYPE_HENTNAVN + PARAMETER_SID + sid).get());
        return returnObj.getString("fuldenavn");
    }

    public void hentOrdFraDr() throws ExecutionException, InterruptedException, JSONException {
        new Web().execute(REST_ROOT_URL + PARAMETER_TYPE + TYPE_HENTORDFRADR);
    }

    public void hentUrl() throws ExecutionException, InterruptedException, JSONException {
        new Web().execute(REST_ROOT_URL + PARAMETER_TYPE + TYPE_HENTURL);
    }

    public JSONObject nulstil(String sid) throws ExecutionException, InterruptedException, JSONException {
        return new JSONObject(new Web().execute(REST_ROOT_URL + PARAMETER_TYPE + TYPE_NULSTIL + PARAMETER_SID + sid).get());
    }

    public void opdaterSynligtOrd(String sid) throws ExecutionException, InterruptedException, JSONException {
        new Web().execute(REST_ROOT_URL + PARAMETER_TYPE + TYPE_OPDATERSYNLIGTORD + PARAMETER_SID + sid);
    }

    public void setOrdet(int i, String sid) throws ExecutionException, InterruptedException, JSONException {
        new Web().execute(REST_ROOT_URL + PARAMETER_TYPE + TYPE_SETORDET + PARAMETER_I + i + PARAMETER_SID + sid);
    }

    public JSONObject hentBeskeder(long timestamp) throws ExecutionException, InterruptedException, JSONException {
        JSONObject jsonObject = new JSONObject(new Web().execute(REST_CHAT_URL + PARAMETER_TYPE + TYPE_GETMESSAGE + PARAMETER_DATE + timestamp).get());
        return jsonObject;
    }

    public JSONArray getHighscores() throws ExecutionException, InterruptedException, JSONException {
        JSONObject jsonObject = new JSONObject(new Web().execute(REST_ROOT_URL + PARAMETER_TYPE + TYPE_GETHIGHSCORES).get());
        return jsonObject.getJSONArray("highscores");
    }

    public void sendMsg(String sid, String msg) throws SQLException, UnsupportedEncodingException {
        String urlMsg = URLEncoder.encode(msg.trim(), "UTF-8");
        Log.d("URLMSG", "" + urlMsg);
        new Web().execute(REST_CHAT_URL + PARAMETER_TYPE + TYPE_SEND_MSG + PARAMETER_SID + sid + PARAMETER_MSG + urlMsg);
    }
}