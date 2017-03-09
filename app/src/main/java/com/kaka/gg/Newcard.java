package com.kaka.gg;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by stre6 on 2017-02-23.
 */

public class Newcard extends AppCompatActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newcard);
        setTitle("글생성");
    }
    private class Card extends AsyncTask<String,Void,Void>{
        @Override
        protected Void doInBackground(String... strings) {
            try{

            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }
    }
}
