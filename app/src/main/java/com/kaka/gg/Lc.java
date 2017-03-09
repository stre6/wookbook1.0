package com.kaka.gg;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by stre6 on 2017-03-07.
 */

public class Lc extends AppCompatActivity {
    String res;
    String id;
    String pw;
    ArrayList Id = new ArrayList();
    ArrayList Pw = new ArrayList();
    ArrayList Name = new ArrayList();
    String cid, cpw, cname;
    int k;

    public String gets() {
        return res;
    }

    public void loch(String cid, String cpw) {
        id = cid;
        pw = cpw;

        class lc extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... strings) {
                StringBuilder sb = new StringBuilder();
                try {
                    String link = "http://1.224.44.55/wjddnr/wookbookjo.php";
                    URL url = new URL(link);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    con.setDoInput(true);
                    con.setDoOutput(true);
                    BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String line = null;
                    while ((line = br.readLine()) != null) {
                        sb.append(line);
                        break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                res = lcheck(sb.toString());
                return res;
            }
        }
        try {
            res = new lc().execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    String lcheck(String j) {

        try {
            Id.clear();
            Pw.clear();
            Name.clear();
            JSONArray a = new JSONArray(j);
            for (int i = 0; i < a.length(); i++) {
                JSONObject o = a.getJSONObject(i);
                cid = o.getString("id");
                cpw = o.getString("pa");
                cname = o.getString("name");
                Id.add(cid);
                Pw.add(cpw);
                Name.add(cname);
            }
        } catch (JSONException e) {
        }
        for (k = 0; k < Id.size(); k++) {
            if (id.equals(Id.get(k).toString().trim()) && pw.equals(Pw.get(k).toString().trim())) {
                res = "Su";
                break;
            } else {
                res = "fail";
            }
        }
        return res;
    }
}
