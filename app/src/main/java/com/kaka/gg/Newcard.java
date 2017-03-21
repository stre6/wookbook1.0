package com.kaka.gg;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by stre6 on 2017-02-23.
 */

public class Newcard extends AppCompatActivity {

    String writer, title, content, regist_day;
    EditText newed, newed2;
    Button newbt1, newbt2;
    SimpleDateFormat format;
    Date date;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newcard);
        newed = (EditText) findViewById(R.id.newed);
        newed2 = (EditText) findViewById(R.id.newed2);
        newbt1 = (Button) findViewById(R.id.newbt1);
        newbt2 = (Button) findViewById(R.id.newbt2);
        format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        date = new Date();

        newbt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent a = getIntent();
                writer = a.getStringExtra("name");
                title = newed.getText().toString();
                content = newed2.getText().toString();
                regist_day = format.format(date);
                if (TextUtils.isEmpty(title)) {
                    Toast.makeText(Newcard.this, "제목을 한 글자 이상 입력해주세요", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(content)) {
                    Toast.makeText(Newcard.this, "내용을 한 글자 이상 입력해주세요", Toast.LENGTH_SHORT).show();
                } else {
                    newc(writer, title, content, regist_day);
                    Toast.makeText(Newcard.this, "글쓰기 완료", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
        newbt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Newcard.this, "글쓰기 취소", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private void newc(String writer, String title, String content, String regist_day) {
        class Card extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... params) {
                try {
                    String writer = params[0];
                    String title = params[1];
                    String content = params[2];
                    String regist_day = params[3];
                    String link = "http://1.224.44.55/wjddnr/cardviewwrite.php";
                    String data = URLEncoder.encode("writer", "UTF-8") + "=" + URLEncoder.encode(writer, "UTF-8");
                    data += "&" + URLEncoder.encode("title", "UTF-8") + "=" + URLEncoder.encode(title, "UTF-8");
                    data += "&" + URLEncoder.encode("content", "UTF-8") + "=" + URLEncoder.encode(content, "UTF-8");
                    data += "&" + URLEncoder.encode("regist_day", "UTF-8") + "=" + URLEncoder.encode(regist_day, "UTF-8");
                    URL url = new URL(link);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    con.setDoInput(true);
                    con.setDoOutput(true);
                    OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
                    wr.write(data);
                    wr.flush();
                    wr.close();
                    BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    StringBuffer sb = new StringBuffer();
                    String line = null;
                    while ((line = br.readLine()) != null) {
                        sb.append(line);
                        break;
                    }
                    return sb.toString();
                } catch (Exception e) {
                    return new String("Exception: " + e.getMessage());
                }
            }
        }
        Card task = new Card();
        task.execute(writer, title, content, regist_day);
    }
}
