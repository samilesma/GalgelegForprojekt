/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author ahmad
 */
public class functions {
    private connector mysql() {
        BufferedReader br = null;
        try {
                String host, db, un, pw;
                br = new BufferedReader(new FileReader("db.txt"));
                host = br.readLine();
                db = br.readLine();
                un = br.readLine();
                pw = br.readLine();
                return new connector(host, db, un, pw);
        } catch(IOException e) {
                e.printStackTrace();
        } finally {
                try {
                        if(br != null)
                                br.close();
                } catch(IOException ex) {
                        ex.printStackTrace();
                }
        }
        return null;
    }
}
