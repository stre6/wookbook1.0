package com.kaka.gg;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by stre6 on 2017-03-07.
 */

public class Login extends AppCompatActivity {
    Button sign, autologin, autostop;
    EditText id, pw;
    SharedPreferences shard;
    SharedPreferences.Editor editor;
    Boolean loginChecked;
    String res = "";
    String cid, cpw;
    Lc lc;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        lc = new Lc();
        sign = (Button) findViewById(R.id.sign);
        id = (EditText) findViewById(R.id.Id);
        pw = (EditText) findViewById(R.id.pw);
        autologin = (Button) findViewById(R.id.autologin);
        autostop = (Button) findViewById(R.id.autostop);
        shard = getSharedPreferences("auto", Activity.MODE_PRIVATE);

        autologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lc.loch(shard.getString("id", ""), (shard.getString("pw", "")));
                res = lc.gets();
                if (res.equals("Su")) {
                    id.setText("");
                    pw.setText("");
                    Toast.makeText(Login.this, shard.getString("name", "") + "님 자동 로그인성공", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(Login.this, MainActivity.class);
                    startActivity(i);
                } else {
                    Toast.makeText(Login.this, "최초 로그인을 해주십시오", Toast.LENGTH_SHORT).show();
                }
            }
        });

        autostop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor = shard.edit();
                editor.clear();
                editor.commit();
            }
        });

        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Login.this, Join.class);
                startActivity(i);
            }
        });
    }


    public void login(View v) {
        cid = id.getText().toString();
        cpw = pw.getText().toString();

        lc.loch(cid, cpw);
        res = lc.gets();
        if (res.equals("Su")) {
            id.setText("");
            pw.setText("");
            shard = getSharedPreferences("auto", MODE_PRIVATE);
            editor = shard.edit();
            editor.putString("id", cid);
            editor.putString("pw", cpw);
            editor.putString("name", lc.Name.get(lc.k).toString().trim());
            editor.commit();
            Toast.makeText(Login.this, lc.Name.get(lc.k).toString().trim() + "님 반갑습니다", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(Login.this, MainActivity.class);
            startActivity(i);
        } else if (TextUtils.isEmpty(cid) || TextUtils.isEmpty(cpw)) {
            Toast.makeText(Login.this, "ID또는 PW를 입력하십시오", Toast.LENGTH_SHORT).show();
        } else if (res.equals("fail")) {
            Toast.makeText(Login.this, "로그인 실패", Toast.LENGTH_SHORT).show();
        }
    }
}
