package utils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import org.json.*;

public class Main {
	final static int MAX=10;
        final static String FILEPATH = "C:\\Users\\ahmad\\Desktop\\Opgaver\\4 - Semester\\Distribuerede systemer\\GalgelegForprojekt\\highscore.txt";
	
	public static boolean canAddHighscore(JSONArray hs, int forkerte, int tid) throws JSONException {
		if(hs.length()<=MAX) return true;
		for(int i=1;i<=hs.length();i++){
			if(forkerte<hs.getJSONObject(i-1).getInt("forkerte")) return true;
			else if(forkerte==hs.getJSONObject(i-1).getInt("forkerte") && tid<hs.getJSONObject(i-1).getInt("tid")) return true;
		}
		return false;
	}
	
	public static JSONArray addHighscore(JSONArray hs, String name, int forkerte, int tid) throws IOException, JSONException {
		JSONObject item=new JSONObject();
		item.put("name",name);
		item.put("forkerte",forkerte);
		item.put("tid",tid);
		
		hs.put(item);
		hs=orderHighscore(hs);
		
		System.out.println(hs.toString());
		
		FileChannel outChan = new FileOutputStream(FILEPATH,true).getChannel();
		outChan.truncate(0);
		outChan.close();
		
		PrintWriter file = new PrintWriter(FILEPATH);
		file.println(hs.toString());
		file.close();
		return hs;
	}

	public static JSONArray orderHighscore(JSONArray hs) throws JSONException {
		JSONObject[] items=new JSONObject[hs.length()];
		for(int i=1;i<=hs.length();i++) items[i-1]=hs.getJSONObject(i-1);
		hs=new JSONArray(new ArrayList<String>());
		int h=1;
		for(int i=1;i<=items.length;i++) {
			if(i==1){
				hs.put(items[0]);
				continue;
			}
			innerloop:
			for(int j=1;j<=hs.length();j++) {
				System.out.println("I: "+i+"\tJ: "+j+"\tH: "+h);
				h++;
				if(h>=1000) break;
				if(items[i-1].getInt("forkerte")<hs.getJSONObject(j-1).getInt("forkerte")) {
					addToPos(j-1,items[i-1],hs);
					break innerloop;
				}
				else if(items[i-1].getInt("forkerte")==hs.getJSONObject(j-1).getInt("forkerte") && items[i-1].getInt("tid")<hs.getJSONObject(j-1).getInt("tid")) {
					addToPos(j-1,items[i-1],hs);
					break innerloop;
				}
				
				if(j==hs.length() && (items[i-1].getInt("forkerte")>hs.getJSONObject(j-1).getInt("forkerte") || items[i-1].getInt("forkerte")==hs.getJSONObject(j-1).getInt("forkerte") && items[i-1].getInt("tid")>=hs.getJSONObject(j-1).getInt("tid"))){
					addToPos(j,items[i-1],hs);
					break innerloop;
				}
			}
		}
		return hs;
	}
	
	public static void addToPos(int pos, JSONObject jsonObj, JSONArray jsonArr) throws JSONException{
		for (int i=jsonArr.length();i>pos;i--){
			jsonArr.put(i,jsonArr.get(i-1));
		}
		jsonArr.put(pos,jsonObj);
	}

	public static void printHighscore(JSONArray hs) throws JSONException {
		System.out.println("Highscore:");
		for(int i=1;i<=hs.length();i++)
		{
			System.out.print("Navn: "+hs.getJSONObject(i-1).getString("name")+"\t");
			System.out.print("Antal forkerte gÊt: "+hs.getJSONObject(i-1).getInt("forkerte")+"\t");
			System.out.println("Tid: "+hs.getJSONObject(i-1).getInt("tid")+"\t");
		}
		System.out.println();
	}

	public static String readFile(String path, Charset encoding) throws IOException 
	{
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return new String(encoded, encoding);
	}
}
