package com.android.sunplast;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
//import android.support.v7.app.AppCompatActivity;

public class EditProfile extends AppCompatActivity {
String name,shop,mob,add;
EditText tv_name,tv_shop,tv_mob,tv_add;
Button btn_submit,btn_edpass;
    public static String token;
    public static String token_val,d_id,val;
    public static final String mypreference = "mypref";
    HttpClient_api httpClient_api=new HttpClient_api();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit);
    tv_name=findViewById(R.id.etfristUsername);
    tv_mob=findViewById(R.id.etUsermob);
    tv_shop=findViewById(R.id.etshopUser);
    tv_add=findViewById(R.id.etUseradd);
    btn_submit=findViewById(R.id.btn_submit);
    btn_edpass=findViewById(R.id.btn_change_password);
shared();
        edit_user();

    }
    public void shared() {

        try {
            SharedPreferences sharedPref = getSharedPreferences(mypreference, Context.MODE_PRIVATE);
            //            String defaultValue = getResources().getString("token",null);
            SharedPreferences.Editor editor = sharedPref.edit();

            token_val = sharedPref.getString("token", "");
            val=sharedPref.getString("dealer_type", "");
            d_id=sharedPref.getString("d_id", "");
            token = token_val;
            user();
            System.out.println("kjghcvjhsd" + d_id);
            editor.commit();

        } catch (Exception e) {

        }
//        return token;
    }

    public void show(String name,String shop,String mob,String add){
        tv_name.setText(""+name);
        tv_shop.setText(""+shop);
        tv_add.setText(""+add);
        tv_mob.setText(""+mob);
    }

    public void edit_user(){
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    name = tv_name.getText().toString();
                    shop = tv_shop.getText().toString();
                    mob = tv_mob.getText().toString();
                    add = tv_add.getText().toString();
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("name", name);
                    jsonObject.put("shop_name", shop);
                    jsonObject.put("contact", mob);
                    jsonObject.put("address", add);
                    updateDealerDetails(jsonObject);
                }catch (Exception e){

                }
            }
        });

    }


    public void updateDealerDetails(JSONObject jsonObject){
        class LoginAsync extends AsyncTask<String, Void, String> {
            //private Context context;

            public static final String KEY_EMAIL = "email";

            private Dialog loadingDialog;

            @Override

            protected void onPreExecute() {

                super.onPreExecute();
//                loadingDialog = ProgressDialog.show(Previous_Orders.this, "Please wait", "Loading...");

            }

            @Override
            protected String doInBackground(String... params) {
//            String name = params[0];
                String id=params[0];
//
                InputStream is = null;
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
//
                nameValuePairs.add(new BasicNameValuePair("data",id));

                String result = null;
                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost=new HttpPost();
                    //                    HttpPost

//                        httpPost.setHeader(httpclient_api.call().get("Authorization"), shared());
                    httpPost = new HttpPost(httpClient_api.call().get("updateDealerDetails"));
                    httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
                    httpPost.addHeader("Authorization","Bearer "+token_val);
//                        System.out.println(httpPost);
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
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

            }
        }
        new LoginAsync().execute(String.valueOf(jsonObject));
    }

    public void user(){
        class LoginAsync extends AsyncTask<String, Void, String> {
            //private Context context;

            public static final String KEY_EMAIL = "email";

            private Dialog loadingDialog;

            @Override

            protected void onPreExecute() {

                super.onPreExecute();
//                loadingDialog = ProgressDialog.show(Previous_Orders.this, "Please wait", "Loading...");

            }

            @Override
            protected String doInBackground(String... params) {
//            String name = params[0];
                String id=params[0];
//
                InputStream is = null;
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
//
                nameValuePairs.add(new BasicNameValuePair("d_id",id));

                String result = null;
                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost=new HttpPost();
                    //                    HttpPost

//                        httpPost.setHeader(httpclient_api.call().get("Authorization"), shared());
                    httpPost = new HttpPost(httpClient_api.call().get("auth"));
                    httpPost.setHeader("Content-Type", "application/json");
                    httpPost.addHeader("Authorization","Bearer "+token_val);
//                        System.out.println(httpPost);
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
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
                String name1 = null,id2,name = null,shop_name="",contact = null,address,is_last_lavel ,is_altFormula,id,altFormula = null,is_parent;
                int alt_qty=0;
                String  id1;
                String admin_order_id;
                String dispatch;
                String dealer_notes;
                String admin_notes;
                String created_at;

                ArrayList   <String> key = new ArrayList<>();
                ArrayList<String>id_arraylist=new ArrayList<>();
                try {
                    double sub_total = 0, draft_disc1 = 0, draft_tax1 = 0;
                    String cid;
//                    System.out.println("kjghfkdjghfjdbgfkbvnjbvldfblr");
                    JSONObject jsonObject = new JSONObject(result);
//                    JSONObject jsonObject1 = jsonObject.getJSONObject("success");
//                    JSONArray cast = jsonObject.getJSONArray("fetch_order_by_id");
                    for (int i=0;i<jsonObject.length();i++){
//                        JSONObject json_obj=jsonObject.getJSONObject(i);
//
                        name =           jsonObject.getString("name");
                        shop_name =    jsonObject.getString("shop_name");
                        contact =       jsonObject.getString("contact");
                        address =     jsonObject.getString("address");
                        show(name,shop_name,contact,address);
                    }

//
                } catch (JSONException e) {

                }

            }
        }
        new LoginAsync().execute();
    }
}
