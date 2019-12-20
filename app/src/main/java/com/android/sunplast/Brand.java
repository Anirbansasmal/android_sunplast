package com.android.sunplast;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.TreeMap;

public class Brand extends AppCompatActivity {
    private static RecyclerView recyclerView,recyclerView_cart,recycler_brand;
    private static RecyclerView.LayoutManager layoutManager;
     adapter_brand adapter_brand;

    TreeMap<String, String> product_brand = new TreeMap<String, String>();
    ArrayList<TreeMap<String, String>> items_brand = new ArrayList<TreeMap<String, String>>();
    HttpClient_api httpClient_api=new HttpClient_api();
    public static final String mypreference = "mypref";
    String ext_name,ext_notes;
    Button btn_confirm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fatch_brand);
        //recycler_order order
        recycler_brand=findViewById(R.id.fetch_brand);
        recycler_brand.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        recycler_brand.setLayoutManager(layoutManager);
        btn_confirm=findViewById(R.id.btn_brand);
        confirm();
//        fetchBrand();
        shared();
    }
    public void fatchdata(){
        adapter_brand=new adapter_brand(this,items_brand);
        recycler_brand.setAdapter(adapter_brand);
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
            fetchBrand();
            System.out.println("kjghcvjhsd" + d_id);
            editor.commit();

        } catch (Exception e) {

        }
//        return token;
    }

    public void confirm(){
        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_get = getIntent();
                Intent intent=new Intent(Brand.this,confirm.class);

                ext_name=intent_get.getStringExtra("ext_name");
                ext_notes=intent_get.getStringExtra("ext_notes");
                intent.putExtra("ext_notes",ext_notes);
                intent.putExtra("ext_name",ext_name);
                intent.putExtra("brand","");
                startActivity(intent);
            }
        });
    }
    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;

    }

    public String getAll_fetchBrand(String url){

        InputStream inputStream = null;
        String result = "";
        try {

            // create HttpClient
            HttpClient httpclient = new DefaultHttpClient();

            // make GET request to the given URL
            HttpGet httpGet = new HttpGet(url);
//            HttpResponse httpResponse = httpclient.execute(new HttpGet(url));
            httpGet.setHeader("Content-Type","application/json");
            httpGet.setHeader("Authorization","Bearer "+token);
            HttpResponse httpResponse = httpclient.execute(httpGet);
            // receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();

            // convert inputstream to string
            if(inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = "Did not work!";

        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }


        Log.d(">>Result  ","-"+result);

        return result;


    }

    public void fetchBrand() {
        class APICalling extends AsyncTask {

            String result = "";

            ProgressDialog progressDialog = new ProgressDialog(Brand.this);

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                progressDialog.setMessage("Loading");
                progressDialog.show();

            }

            @Override
            protected Object doInBackground(Object[] objects) {


                result = getAll_fetchBrand(httpClient_api.call().get("fetchBrand"));

                return result;
            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);

                progressDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(result);

                    JSONObject jsonObject1 = jsonObject.getJSONObject("success");
                    JSONArray cast = jsonObject1.getJSONArray("brand_data");
                    System.out.println("kljhsifh"+cast.length());
//                JSONArray jsonArray = new JSONArray(result);
                    for (int i = 0; i < cast.length(); i++) {

                        JSONObject jsonObject2=cast.getJSONObject(i);
                        String id=jsonObject2.getString("id");
                        String brand_name=jsonObject2.getString("brand_name");
                        String updated_at=jsonObject2.getString("updated_at");
                        String created_at=jsonObject2.getString("created_at");

                        product_brand=makeProduct_brand_select(id,brand_name,updated_at,created_at);
                        items_brand.add(product_brand);

                        System.out.println("kljhsifh"+items_brand);
//                        Log.d(">>-NAME-", "" + cast);
                        fatchdata();
                    }

                } catch (Exception e) {
//                    Log.d(">>Error-", e.toString());
                }
            }
        }
        new APICalling().execute();
    }

    public static TreeMap<String, String> makeProduct_brand_select(String id, String brand_name,String created_at, String updated_at) {

        TreeMap<String, String> treeMap = new TreeMap<String, String>();
        treeMap.put("id", String.valueOf(id));
        treeMap.put("brand_name", brand_name);
        treeMap.put("updated_at",updated_at);
        treeMap.put("created_at",created_at);
//        treeMap.put("updated_at",is_last_lavel);
//        treeMap.put("is_altFormula",is_altFormula);
//        treeMap.put("altFormula",altFormula);
//        treeMap.put("is_parent",is_parent);

        //
        return treeMap;
    }
}
