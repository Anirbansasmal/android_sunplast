package com.android.sunplast;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class confirm extends AppCompatActivity {
    String ext_name,ext_notes;
    public static final String mypreference = "mypref";
    HttpClient_api httpClient_api=new HttpClient_api();
    Button btn_yes,btn_no;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unloading);
        Intent intent_get = getIntent();
        ext_name=intent_get.getStringExtra("ext_name");
        ext_notes=intent_get.getStringExtra("ext_notes");
        System.out.println("dfgdregerrge"+ext_notes);

        btn_yes=findViewById(R.id.btn_yes);
        btn_no=findViewById(R.id.btn_no);
        shared();
        yes();
        no();
    }
    boolean doubleBackToExitPressedOnce = false;
    public static String token;
    public static String token_val,d_id,val;
    public void shared() {

        try {
            SharedPreferences sharedPref = getSharedPreferences(mypreference, Context.MODE_PRIVATE);
            //            String defaultValue = getResources().getString("token",null);
            SharedPreferences.Editor editor = sharedPref.edit();

            token_val = sharedPref.getString("token", "");
            val=sharedPref.getString("dealer_type", "");
            d_id=sharedPref.getString("d_id", "");
            token = token_val;

            System.out.println("kjghcvjhsd" + d_id);
            editor.commit();

        } catch (Exception e) {

        }
//        return token;
    }

    public void yes(){
        btn_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cartUnloading();
                Intent intent=new Intent(confirm.this,Product.class);
                startActivity(intent);
            }
        });
    }

    public void no(){
        btn_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cartloading();

            }
        });
    }

    public void cart_update(){

    }


    public void cartUnloading(){
        class LoginAsync extends AsyncTask<String, Void, String> {
            //private Context context;
            public static final String KEY_EMAIL = "email";
//        private Dialog loadingDialog;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
//            loadingDialog = ProgressDialog.show(Product.this, "Please wait", "Loading...");
            }

            @Override
            protected String doInBackground(String... params) {
                String name = params[0];
//            String id=params[1];
//
                InputStream is = null;
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                List<NameValuePair> nameValuePairs1=new ArrayList<>();

                nameValuePairs.add(new BasicNameValuePair("data",name));
//
                System.out.println("ldkhjgidh"+name);
                String result = null;
                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost=new HttpPost();
                    httpPost = new HttpPost(httpClient_api.call().get("insertIntocarts"));
                    httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
                    httpPost.addHeader("Authorization","Bearer "+token_val);
//                        System.out.println(httpPost);
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
//                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                    HttpResponse response = httpClient.execute(httpPost);
                    HttpEntity entity = response.getEntity();
                    System.out.println("vjdfhjsdhvfkbsdj" + response);

                    is = entity.getContent();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"), 8);
                    StringBuilder sb = new StringBuilder();
                    String line = null;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    result = sb.toString();
                } catch (ClientProtocolException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return result;
            }


            @Override
            protected void onPostExecute(String result) {
                String s = result.trim();
                String name1,parent_id,unit_id,is_last_lavel ,is_altFormula,id,altFormula,is_parent;

            }
        }
    }

    public void cartloading(){
        class LoginAsync extends AsyncTask<String, Void, String> {
            //private Context context;
            public static final String KEY_EMAIL = "email";
//        private Dialog loadingDialog;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
//            loadingDialog = ProgressDialog.show(Product.this, "Please wait", "Loading...");
            }

            @Override
            protected String doInBackground(String... params) {
                String name = params[0];
//            String id=params[1];
//
                InputStream is = null;
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                List<NameValuePair> nameValuePairs1=new ArrayList<>();

                nameValuePairs.add(new BasicNameValuePair("data",name));
//
                String result = null;
                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost=new HttpPost();
                    httpPost = new HttpPost(httpClient_api.call().get("insertIntocarts"));
                    httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
                    httpPost.addHeader("Authorization","Bearer "+token_val);
//                        System.out.println(httpPost);
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
//                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                    HttpResponse response = httpClient.execute(httpPost);
                    HttpEntity entity = response.getEntity();
                    System.out.println("vjdfhjsdhvfkbsdj" + response);

                    is = entity.getContent();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"), 8);
                    StringBuilder sb = new StringBuilder();
                    String line = null;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    result = sb.toString();
                } catch (ClientProtocolException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return result;
            }


            @Override
            protected void onPostExecute(String result) {
                String s = result.trim();
                String name1,parent_id,unit_id,is_last_lavel ,is_altFormula,id,altFormula,is_parent;

            }
        }
    }

}
