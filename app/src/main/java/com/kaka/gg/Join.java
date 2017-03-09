package com.kaka.gg;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by stre6 on 2017-03-07.
 */

public class Join extends AppCompatActivity {
    Button sighbt, back, imgplus;
    EditText id, pa, name, age;
    String res, absoultePath;
    ImageView img;
    Uri imguri;
    private static final int PICK_FROM_CAMERA = 0;
    private static final int PICK_FROM_ALBUM = 1;
    private static final int CROP_FROM_IMAGE = 2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join);
        sighbt = (Button) findViewById(R.id.signbt);
        back = (Button) findViewById(R.id.back);
        id = (EditText) findViewById(R.id.id);
        pa = (EditText) findViewById(R.id.pa);
        name = (EditText) findViewById(R.id.name);
        age = (EditText) findViewById(R.id.age);
        imgplus = (Button) findViewById(R.id.imgbt);
        img = (ImageView) findViewById(R.id.img1);

        sighbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String a_id = id.getText().toString();
                String a_pa = pa.getText().toString();
                String a_name = name.getText().toString();
                String a_age = age.getText().toString();
                Jc jc = new Jc();
                jc.check(a_id);
                res = jc.getres();
                if (TextUtils.isEmpty(a_id) || TextUtils.isEmpty(a_pa) || TextUtils.isEmpty(a_name) || TextUtils.isEmpty(a_pa)) {
                    Toast.makeText(Join.this, "빈 공간이 있습니다", Toast.LENGTH_SHORT).show();
                } else if (res.equals("same")) {
                    Toast.makeText(Join.this, "ID중복", Toast.LENGTH_SHORT).show();
                } else if (res.equals("ss")) {
                    Toast.makeText(Join.this, "회원가입 성공", Toast.LENGTH_SHORT).show();
                    Db(a_id, a_pa, a_name, a_age);
                    finish();
                }
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        imgplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(Join.this).setMessage("카메라 또는 앨범을 선택해주세요")
                        .setPositiveButton("카메라", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                TakePhoto();
                            }
                        }).setNeutralButton("앨범", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        TakeAlbum();
                    }
                }).setNegativeButton("취소", null).show();
            }
        });
    }

    private void Db(String a_id, String a_pa, String a_name, String a_age) {
        class ID extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... params) {
                try {
                    String a_id = params[0];
                    String a_pa = params[1];
                    String a_name = params[2];
                    String a_age = params[3];
                    String link = "http://1.224.44.55/wjddnr/wookbooklo.php";
                    String data = URLEncoder.encode("id", "UTF-8") + "=" + URLEncoder.encode(a_id, "UTF-8");
                    data += "&" + URLEncoder.encode("pa", "UTF-8") + "=" + URLEncoder.encode(a_pa, "UTF-8");
                    data += "&" + URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(a_name, "UTF-8");
                    data += "&" + URLEncoder.encode("age", "UTF-8") + "=" + URLEncoder.encode(a_age, "UTF-8");
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
        ID task = new ID();
        task.execute(a_id, a_pa, a_name, a_age);
    }

    public void TakePhoto() {
        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        String url = "tmp_" + String.valueOf(System.currentTimeMillis()) + ".jpg";
        imguri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), url));
        i.putExtra(MediaStore.EXTRA_OUTPUT, imguri);
        startActivityForResult(i, PICK_FROM_CAMERA);
    }

    public void TakeAlbum() {
        Intent i = new Intent(Intent.ACTION_PICK);
        i.setType(MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(i, PICK_FROM_ALBUM);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_OK){
         return;
        }

        switch (requestCode){
            case PICK_FROM_ALBUM:
            {
                imguri = data.getData();

            }
            case  PICK_FROM_CAMERA:
            {
                Intent i = new Intent("com.android.camera.action.CROP");
                i.setDataAndType(imguri, "image/*");

                i.putExtra("outputX",80);
                i.putExtra("outputY",80);
                i.putExtra("aspectX",1);
                i.putExtra("aspectY",1);
                i.putExtra("scale",true);
                i.putExtra("return-data",true);
                startActivityForResult(i,CROP_FROM_IMAGE);

                break;
            }
            case CROP_FROM_IMAGE:
            {
                if (resultCode != RESULT_OK){
                    return;
                }
                final Bundle extras = data.getExtras();
                String filePath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/imgcrop/"+System.currentTimeMillis()+".jpg";
                if (extras !=null){
                    Bitmap photo = extras.getParcelable("data");
                    img.setImageBitmap(photo);
                    storeCropImage(photo, filePath);
                    absoultePath = filePath;
                    break;
                }
                File f = new File(imguri.getPath());
                if (f.exists()){
                    f.delete();
                }
            }
        }
    }
    public void storeCropImage(Bitmap bitmap, String filePath){
        String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/imgcrop/";
        File directory_imgcrop = new File(dirPath);
        if (!directory_imgcrop.exists()){
            directory_imgcrop.mkdir();
            File copyFile = new File(filePath);
            BufferedOutputStream out = null;
            try{
                copyFile.createNewFile();
                out = new BufferedOutputStream(new FileOutputStream(copyFile));
                bitmap.compress(Bitmap.CompressFormat.JPEG,100,out);
                sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                        Uri.fromFile(copyFile)));
                out.flush();
                out.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
