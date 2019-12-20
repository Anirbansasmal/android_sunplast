package com.android.sunplast;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Login extends AppCompatActivity {

    Button btn_login,btn_signup;

    EditText txt_username,txt_password;

    String username,password;

    public static String token,str,id;

    HttpClient_api httpClient_api=new HttpClient_api();

    public static final String mypreference = "mypref";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        txt_username=findViewById(R.id.etxUsername);
        txt_password=findViewById(R.id.etUserpass);

        btn_login=findViewById(R.id.btnLogin);
        btn_signup=findViewById(R.id.btn_signup);

        auto_login();
        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Login.this,SignUp.class);
                startActivity(intent);
            }
        });
    }

    public void auto_login() {

        SharedPreferences sharedPref = getSharedPreferences(mypreference, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        token = sharedPref.getString("token", "");
        System.out.println("hgfdxhjakshxdhascxvhjacxghagx   " + token.length());
        if (token.length() == 0) {
//        l
            System.out.println("logintest");
//
        } else {
//        login();
            Intent intent = new Intent(Login.this, Product.class);
            startActivity(intent);
//            finish();
        }


        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username=txt_username.getText().toString();
                password=txt_password.getText().toString();
                if (!isValidEmail(username)) {
                    txt_username.setError("Please Enter Valid Email");
                    txt_username.requestFocus();
                } else if (!isValidPassword(password)) {
                    txt_password.setError("Password wrong characters");
                    txt_password.requestFocus();
                } else {
                    login(username, password);
                    // new BackgroundTaskLogin(LoginActivity.this).execute(emailAddress,passWord );
                    txt_username.setText("");
                    txt_password.setText("");
                }

            }
        });
    }

    private void login(final String emailAddress, final String passWord) {
        class LoginAsync extends AsyncTask<String, Void, String> {
            //private Context context;
            public static final String KEY_EMAIL = "email";
            private Dialog loadingDialog;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loadingDialog = ProgressDialog.show(Login.this, "Please wait", "Loading...");
            }

            @Override
            protected String doInBackground(String... params) {
                String emailAddress = params[0];
                String passWord = params[1];
                InputStream is = null;
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("mobile", emailAddress));
                nameValuePairs.add(new BasicNameValuePair("password", passWord));
                //return nameValuePairs;

                String result = null;
                try{
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(httpClient_api.call().get("login"));
//                    HttpPost
//                    httpPost.setHeader("X-API-KEY","1e605442c721541e8f37324f");
                    System.out.println(httpPost);
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                    HttpResponse response = httpClient.execute(httpPost);
                    HttpEntity entity = response.getEntity();

                    is = entity.getContent();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"), 8);
                    StringBuilder sb = new StringBuilder();
                    String line = null;
                    while ((line = reader.readLine()) != null)
                    {
                        sb.append(line + "\n");
                    }
                    result = sb.toString();
                    System.out.println("vjdfhjsdhvfkbsdj"+result);
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
            protected void onPostExecute(String result){
                String s = result.trim();
                String name;
//                JSONObject jsonObject= null;
                try {
//                    JSONObject jsonObject= null;
//                    for (int i=0;i<result.length();i++)
                    System.out.println("kjghfkdjghfjdbgfkbvnjbvldfblr");
                    JSONObject jsonObject = new JSONObject(result);
//                    name=jsonObject.getString("token");
                    loadingDialog.dismiss();

//                    System.out.println(jsonObject);
                    if(jsonObject.has("success")){
                        System.out.println("jsonObject "+jsonObject.has("success"));

//                        System.out.println("value"+jsonObject);
                        JSONObject token_name=(JSONObject) new JSONTokener(result).nextValue();
//                        JSONObject json= (JSONObject) new JSONTokener(result).nextValue();

                        JSONObject json2 = token_name.getJSONObject("success");
                        token = (String) json2.get("token");
                        System.out.println("token"+token);
                        String user=json2.getString("userDetails");




                         str= String.valueOf(jsonObject.getJSONObject("success").getJSONObject("userDetails").get("dealer_type"));
                         id=String.valueOf(jsonObject.getJSONObject("success").getJSONObject("userDetails").get("id"));
                        SharedPreferences sharedPref =    getSharedPreferences(mypreference,Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putString("token", token);
                        editor.putString("dealer_type",str);
                        editor.putString("d_id",id);
                        editor.commit();
                        editor.apply();
                        Intent intent = new Intent(Login.this, Product.class);
//                        intent.putExtra("id",str);
//                        intent.putExtra("d_id",id);
                        startActivity(intent);


                        String api_token=sharedPref.getString("d_id","");

                        System.out.println("fhjdsbgfkdsbgdbdke"+id);
//

                    }else {
                        Toast.makeText(getApplicationContext(), "Invalid Email or Password", Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }


        }
        new LoginAsync().execute(emailAddress, passWord);

    }

    private boolean isValidEmail(String email) {
//        String EMAIL_PATTERN = "[0-9]{10}";

        Pattern pattern = Patterns.PHONE;
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private boolean isValidPassword(String pass) {
        if (pass != null && pass.length() >= 6) {
            return true;
        }
        return false;
    }

}