package com.android.sunplast;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

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
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SignUp extends AppCompatActivity {
    Button btn_login,btn_submit;
    EditText txt_username,txt_password,txt_name,txt_add,txt_email,txt_reenterpassword,txt_mob,txt_shop;
    String username,password,name,add,email,reenter_password,mob,shop_name;

    public static final String mypreference = "mypref";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        btn_submit=findViewById(R.id.btn_submit);
        btn_login=findViewById(R.id.btnLogin);

//        txt_username=findViewById(R.id.etEmailUser);
        txt_password=findViewById(R.id.txt_password);
//        txt_name=findViewById(R.id.txt_name);
//        txt_add=findViewById(R.id.etUseradd);
//        txt_email=findViewById(R.id.txt_email);
        txt_reenterpassword=findViewById(R.id.txt_confirm);
        txt_mob=findViewById(R.id.etUsermob);
        txt_shop=findViewById(R.id.txt_shopname);

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //                LayoutInflater li = LayoutInflater.from(context);
                //                View promptsView = li.inflate(R.layout.prompt, null);
                System.out.println("value   ");
                //                        final EditText uname, uemail, uphone, uaddress;
                Spinner ugrp;
//                String name, phone, email, addn, grp;
                ArrayList<String> n = new ArrayList<>();

                //
//                name = txt_name.getText().toString();
//                username = txt_username.getText().toString();
//                email = txt_email.getText().toString();
                password = txt_password.getText().toString();
                reenter_password = txt_reenterpassword.getText().toString();
                shop_name = txt_shop.getText().toString();
                mob = txt_mob.getText().toString();
//                add = txt_add.getText().toString();

//
 if (password!=null){
     isValidPassword(password);
    txt_password.setError("Please Enter Valid password");
    txt_password.requestFocus();
    txt_password.setText("");
}if (reenter_password!=null){
     isValidPassword(reenter_password);
    txt_reenterpassword.setError("Please Enter Valid reenter password");
    txt_reenterpassword.requestFocus();
    txt_reenterpassword.setText("");
}if (shop_name!=null){
    txt_shop.setError("Please Enter Valid shop name");
    txt_shop.requestFocus();
    txt_reenterpassword.setText("");
}if (mob!=null) {
    txt_mob.setError("Please Enter mobile ");
    txt_mob.requestFocus();
    txt_reenterpassword.setText("");
}if (add!=null){
//    txt_add.setError("Please Enter  address");
    txt_add.requestFocus();
}if (password.equals(reenter_password)){
    customer_add(password,reenter_password,shop_name,mob);
}else {
    Toast.makeText(getApplicationContext(), "Invalid Password", Toast.LENGTH_LONG).show();
}
//
//                System.out.println("json request " + Customer);



            }
        });

    }
HttpClient_api httpClient_api=new HttpClient_api();
    final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private void customer_add(final String password, final String c_password,final String shop_name, final String contact) {

        JSONObject json=new JSONObject();
        try {
            json.put("password", password);
            json.put("c_password", c_password);
            json.put("shop_name", shop_name);
            json.put("contact", contact);
//            json.put("name",name);
//            json.put("email",email);
//            json.put("username",username);
        }catch (Exception e){

        }

System.out.println("kiyguiehgkhg"+json);

        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(JSON,json.toString());
        SharedPreferences sharedPref = getSharedPreferences(mypreference, Context.MODE_PRIVATE);
        //            String defaultValue = getResources().getString("token",null);
        SharedPreferences.Editor editor = sharedPref.edit();
        String token = sharedPref.getString("token", "");
        editor.commit();
        System.out.println("hjsvcsvhcj" + token);
        Request request = new Request.Builder()
                .addHeader("X-API-KEY", "1e605442c721541e8f37324f")
                .addHeader("Content", "application/json; charset=utf-8")
//                .addHeader(httpClient_api.call().get("Authorization"), shared())
                .url(httpClient_api.call().get("register"))
                .post(body)
                .build();
        System.out.println("dkjgvjbvl"+body.toString());
        System.out.println("kjsgfhggdsj"+json);
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
//                Log.d("TAG",response.body().string());
                String str=response.body().string();

                try {
                    JSONObject jsonObject=new JSONObject(str);
                    System.out.println("kjsghfjh"+jsonObject.getJSONObject("success").getString("name").equals("null"));
                    if (jsonObject.getJSONObject("success").getString("name").equals("null")){
                        Intent intent=new Intent(SignUp.this,Login.class);
                        startActivity(intent);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

//            class LoginAsync extends AsyncTask<String, Void, String> {
//                //private Context context;
//                public static final String KEY_EMAIL = "email";
//                private Dialog loadingDialog;
//
//                @Override
//                protected void onPreExecute() {
//                    super.onPreExecute();
//                    loadingDialog = ProgressDialog.show(SignUp.this, "Please wait", "Loading...");
//                }
//
//                @Override
//                protected String doInBackground(String... params) {
//                    String username,password,name,add,email,reenter_password,mob,shop_name;
//                    name = params[0];
//                    email = params[1];
//                    password=params[2];
//                    reenter_password=params[3];
//                    username=params[4];
//                    shop_name=params[5];
//                    mob = params[6];
//                    add = params[7];
//                    InputStream is = null;
//                    //                    String token_name=jsonObject.getString("token");
//                    SharedPreferences sharedPref = getSharedPreferences(mypreference, Context.MODE_PRIVATE);
//                    //            String defaultValue = getResources().getString("token",null);
//                    SharedPreferences.Editor editor = sharedPref.edit();
//                    String token = sharedPref.getString("token", "");
//                    editor.commit();
//                    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
//
//                    nameValuePairs.add(new BasicNameValuePair("name", name));
//                    nameValuePairs.add(new BasicNameValuePair("email", email));
//                    nameValuePairs.add(new BasicNameValuePair("password", password));
//                    nameValuePairs.add(new BasicNameValuePair("c_password", reenter_password));
//                    nameValuePairs.add(new BasicNameValuePair("username", username));
//                    nameValuePairs.add(new BasicNameValuePair("shop_name",shop_name));
//                    nameValuePairs.add(new BasicNameValuePair("contact",mob));
//                    nameValuePairs.add(new BasicNameValuePair("address",add));
//                    //                    return nameValuePairs;
////                    header('Access-Control-Allow-Origin:  *');
////                    header('Access-Control-Allow-Methods:  POST, GET, OPTIONS, PUT, DELETE');
////                    header('Access-Control-Allow-Headers:  Content-Type, X-Auth-Token, Origin, Authorization');
//                    String result = null;
//                    try {
//                        HttpClient httpClient = new DefaultHttpClient();
//                        HttpPost httpPost = new HttpPost("http://192.168.0.122/pro-api/public/api/register");
//                        //                    HttpPost
////                        httpPost.setHeader("X-API-KEY", "1e605442c721541e8f37324f");
////                        httpPost.setHeader("Authorization", token);
//
//                        System.out.println(httpPost);
//                        httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
//                        HttpResponse response = httpClient.execute(httpPost);
//                        HttpEntity entity = response.getEntity();
//                        System.out.println("vjdfhjsdhvfkbsdj" + response);
//
//                        is = entity.getContent();
//
//                        BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"), 8);
//                        StringBuilder sb = new StringBuilder();
//                        String line = null;
//                        while ((line = reader.readLine()) != null) {
//                            sb.append(line + "\n");
//                        }
//                        result = sb.toString();
//                    } catch (ClientProtocolException e) {
//                        e.printStackTrace();
//                    } catch (UnsupportedEncodingException e) {
//                        e.printStackTrace();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    return result;
//                }
//
//
//                @Override
//                protected void onPostExecute(String result) {
//                    String s = result.trim();
//                    String name;
//                    //
//                    try {
//                        //
//                        JSONObject jsonObject = new JSONObject(result);
//                        //                    name=jsonObject.getString("token");
//                        loadingDialog.dismiss();
//
//                        //                    System.out.println(jsonObject);
//                        if (jsonObject.has("token")) {
//                            Toast.makeText(getApplicationContext(), "Invalid Email or Password", Toast.LENGTH_LONG).show();
//
//                        } else {
//                            Intent intent = new Intent(SignUp.this, Login.class);
//
//                            startActivity(intent);
//                        }
//
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//
//                }
//
//
//            }
//            new LoginAsync().execute(name,email,password,c_password,username,contact,shop_name,address);

        }
    private boolean isValidPassword(String pass) {
        if (pass != null && pass.length() >= 6) {
            return true;
        }
        return false;
    }
    }

