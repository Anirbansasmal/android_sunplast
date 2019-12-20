package com.android.sunplast;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
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
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import okhttp3.MediaType;

public class Product extends AppCompatActivity {

   private static RecyclerView recyclerView,recyclerView_cart,recycler_order;
   private static RecyclerView.LayoutManager layoutManager;
   public static RecyclerView.LayoutManager layoutManager_cart;
    public static RecyclerView.LayoutManager layoutManager_cart_order;
   private static Selectedlayers selectedlayers;
   private static CartDetails cartDetails;
   private static expandable_adapter cartDetails_order;


    TreeMap<String, String> product1 = new TreeMap<String, String>();
    TreeMap<String, String> product_id = new TreeMap<String, String>();
    TreeMap<String, String> product_back = new TreeMap<String, String>();
    TreeMap<String, String> product_idstring_check = new TreeMap<String, String>();
    TreeMap<String, String> product_cart = new TreeMap<String, String>();
   TreeMap<String, String> product_cart_product = new TreeMap<String, String>();
    TreeMap<String, String> product_brand = new TreeMap<String, String>();

     ArrayList<TreeMap<String, String>> items = new ArrayList<TreeMap<String, String>>();
     ArrayList<TreeMap<String, String>> items_idstring = new ArrayList<TreeMap<String, String>>();
    ArrayList<TreeMap<String, String>> items_idstring_cart = new ArrayList<TreeMap<String, String>>();
    ArrayList<TreeMap<String, String>> items_idstring_check = new ArrayList<TreeMap<String, String>>();
  public static ArrayList<TreeMap<String, String>> items_cart = new ArrayList<TreeMap<String, String>>();
    ArrayList<TreeMap<String, String>> items_brand = new ArrayList<TreeMap<String, String>>();

public static JSONObject jsonObj_insert=new JSONObject();
    ArrayList<String> arrayListdel=new ArrayList<>();
    ArrayList<String> arrayList_all_id=new ArrayList<>();
    ArrayList<String> arrayList_insert=new ArrayList<>();
    JSONObject jsonObj_cart=new JSONObject();
   Map<String, HashMap<String, String>> hashMapMap=new HashMap<String, HashMap<String, String>>();
    Map<String,String> map=new HashMap<String,String>();
    Map<String,String> hashMap=new HashMap<String,String>();
Button btn_submit,btn_back,btn_save;
EditText txt_edit,txt_note;
    public static final String mypreference = "mypref";
    HttpClient_api httpClient_api=new HttpClient_api();

    final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_catagoriies);

        //layers adapter
        recyclerView=findViewById(R.id.recycler_Catagories);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        //cart adapter
        recyclerView_cart=findViewById(R.id.recycler_cart);
        recyclerView_cart.setHasFixedSize(true);
        layoutManager_cart=new LinearLayoutManager(this);
        recyclerView_cart.setLayoutManager(layoutManager_cart);

        //recycler_order order
        recycler_order=findViewById(R.id.recycler_order);
        recycler_order.setHasFixedSize(true);
        layoutManager_cart_order=new LinearLayoutManager(this);
        recycler_order.setLayoutManager(layoutManager_cart_order);
        RecyclerView.ItemAnimator animator = recycler_order.getItemAnimator();

        if (animator instanceof DefaultItemAnimator) {
            ((DefaultItemAnimator) animator).setSupportsChangeAnimations(false);
        }

        btn_submit=findViewById(R.id.btn_proceed);
        btn_back=findViewById(R.id.btn_back);
        btn_save=findViewById(R.id.btn_save);
        txt_edit=findViewById(R.id.txt_edit);
        txt_note=findViewById(R.id.txt_addnote);

        Intent intent = getIntent();
        String id=intent.getStringExtra("id");
        String d_id=intent.getStringExtra("d_id");
//        arrayList_all_id.add(id);
        items.clear();
        product1.clear();
        items_idstring.clear();
        product_id.clear();
        items_idstring.clear();
        product_back.clear();
        shared();
        back();
        insert();
        save();
//        btn_back.setVisibility(View.INVISIBLE);

//        cart_listinner();
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
//        adapter.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
//        adapter.onRestoreInstanceState(savedInstanceState);
    }


    boolean doubleBackToExitPressedOnce = false;
    public static String token;
    public static String token_val,d_id,val;
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

    public void back(){
        if (arrayList_all_id.size()<1){
            btn_back.setVisibility(View.INVISIBLE);
        }else {
//            for (int i=0;i<arrayList_all_id.size();i++){
//                arrayList_all_id.remove(i);
                System.out.println("khewfkjghi"+arrayList_all_id);
//            }
            btn_back.setVisibility(View.VISIBLE);
        }
    }

    public void fatchdata(){
        selectedlayers=new Selectedlayers(this,items);
        recyclerView.setAdapter(selectedlayers);
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
            fetchCatById(Integer.parseInt(val), Integer.parseInt(d_id),token_val);
            layer_touch(Integer.parseInt(d_id));
            rec_back(Integer.parseInt(val),token_val);
            fetchAllcarts(Integer.parseInt(d_id));
            System.out.println("kjghcvjhsd" + d_id);
            editor.commit();

        } catch (Exception e) {

        }
//        return token;
    }

    public void cart_update(){
        cartDetails_order=new expandable_adapter(this,items_idstring_cart);
        recycler_order.setAdapter(cartDetails_order);
    }

    public void layer_touch(final int id){

        recyclerView.addOnItemTouchListener(new RecyclerTuchListener(this, recyclerView, new RecyclerTuchListener.ClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                for (int j=0;j<items.size();j++){
                    if (items.get(j).get("id").startsWith(items.get(position).get("id")) && (String.valueOf(items.get(j).get("is_parent")).equals(String.valueOf(0)))) {
                        for (int l=0;l<items_idstring.size();l++){
                            arrayList_all_id.add(items.get(j).get("id"));
                            back();
try {
    jsonObj_cart.put(items.get(j).get("id"), items.get(j).get("parent_id"));
}catch (Exception e){

}

                            System.out.println("kjgfjbdsfsgfewfkjjsonObj_cart"+jsonObj_cart);


//
                            for (int i=0;i<items.size();i++){
                                jsonObj_insert.remove(items.get(i).get("id"));
                            }
hashMapMap.put(items.get(j).get("id"),new HashMap<String, String>());
hashMapMap.get(items.get(j).get("id")).put("starting_id",items.get(j).get("parent_id"));
hashMapMap.get(items.get(j).get("id")).put("d_id", String.valueOf(id));
hashMapMap.get(items.get(j).get("id")).put("id",items.get(j).get("id"));

hashMap.put("starting_id",items.get(j).get("id"));

    fetchCatById(Integer.parseInt(hashMap.get("starting_id")),id,token_val);
                            product_id.clear();
                            items.clear();
                            product1.clear();
                            items_idstring.clear();
                        }
                    }
                }
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    public void rec_back(final int id, final String token_val){
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int j=0;j<items.size();j++) {

                    if (items.get(j).get("id").startsWith(items.get(j).get("id"))) {
//                        for (int l = 0; l < items_idstring.size(); l++) {
//                        System.out.println("kjgfjbdsfsgfewfkj"+hashMapMap.get(items.get(j).get("parent_id")).get("starting_id"));
//                        if (items.get(j).get("parent_id").equals(hashMapMap.get(items.get(j).get("parent_id")).get("starting_id"))){
                            fetchCatById(Integer.parseInt(hashMapMap.get(items.get(j).get("parent_id")).get("starting_id")), id,token_val);
                        for (int i=0;i<arrayList_all_id.size();i++) {
                            arrayList_all_id.remove(i);
                            back();
                            System.out.println("kjgfjbdsfsgfewfkj"+arrayList_all_id);
                        }
//                            System.out.println("utygdhvgwfj"+items.size());
                        items.clear();
//                        btn_back.setVisibility(View.INVISIBLE);
//                        }
                    }
                }
//                System.out.println("khewfkjghi" + items);
//                for (int i=0;i<items.size();i++){
////                    items.clear();
////                    product1.clear();
////                    items_idstring.clear();
////                    product_id.clear();
//
////                    System.out.println("khewfkjghi" + items.get(i).get("parent_id").equals(hashMap.get("starting_id"))+""+hashMap);
//
//                    if (items.get(i).get("id").startsWith(items.get(i).get("id")) && (String.valueOf(items.get(i).get("is_parent")).equals(String.valueOf(0)))){
////                    if (items.get(i).get("id").startsWith(items.get(i).get("id")) && (String.valueOf(items.get(i).get("is_parent")).equals(String.valueOf(0)))) {
//                        System.out.println("khewfkjghi" + hashMapMap.get(items.get(i).get("id")).get("id"));
////                        System.out.println("khewfkjghi" + (hashMapMap.get(items.get(i).get("id")).get("starting_id").toString()));
////                        if (items_idstring_check.get(i).get("id").equals(hashMapMap.get(i))) {
////                            fetchCatById(Integer.parseInt(hashMapMap.get(items.get(i).get("id")).get("starting_id")), id);
////
////                        }
//                    }
////                    fetchCatById(Integer.parseInt(items_idstring_back.get(i).get("starting_id")),Integer.parseInt(items_idstring_back.get(i).get("d_id")));
//                }
            }
        });
    }

    public void details(){
        MyCustomObject myCustomObject=new MyCustomObject(new MyCustomObject.MyCustomObjectListener() {
            @Override
            public void onadd(String index, String val) {


            }

            @Override
            public void onsub(String index, JSONObject val) {
//                System.out.println("lkngfkjbgfknk"+val);
//        update(val);
            }

            @Override
            public void ondetail(String index, String val) {
                System.out.println("lkngfkjbgfknk"+val);
//                items.clear();
//                update(val);
            }

        });
    }

    public void save(){
    btn_save.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent=new Intent(Product.this,Brand.class);
            intent.putExtra("ext_name",txt_edit.getText().toString());
            intent.putExtra("ext_notes",txt_note.getText().toString());
            startActivity(intent);
            fatchdata();
        }
    });
}

public void qty(JSONObject jsonObject){
        insert();
//        insertIntocarts(jsonObject);
    }

    public void insert(){
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("jhdgsjhfbjsf"+jsonObj_cart);
                try {
                    JSONObject jsonObject = new JSONObject();
                    if (jsonObj_insert.length() > 0) {


                        insertIntocarts(jsonObj_insert);
                        for (int i = 0; i < items_cart.size(); i++) {
                            System.out.println("jhfgshdgjd"+items_cart.get(i).get("lastId"));
                            fetchCatById(Integer.parseInt(String.valueOf(items_cart.get(i).get("lastId"))), Integer.parseInt(d_id), token_val);
                        }


                        for (int i = 0; i < items.size(); i++) {
                            jsonObj_insert.remove(items.get(i).get("id"));
                        }
                        product1.clear();
                        items.clear();
                        items_cart.clear();
                        product_cart.clear();
                        fetchAllcarts(Integer.parseInt(d_id));
                    } else {

                    }
                }catch (Exception e){

                }
            }
        });
    }

    public void cart(){
        cartDetails=new CartDetails(this,items_cart);
        recyclerView_cart.setAdapter(cartDetails);
        System.out.println("dfhfvfmbvhlkjhmnrk"+items_cart);
}

    public void cart_order(){

}

    MyCustomObject.MyCustomObjectListener listener;
    MyCustomObject object = new MyCustomObject(listener);
    public void cart_listinner(){

    object.setCustomObjectListener(new MyCustomObject.MyCustomObjectListener() {
        @Override
        public void onadd(String index, String val) {

            System.out.println("kjvghfebgijuihsiu "+index+""+val);
            after_del(Integer.parseInt(index),val);
        }

        @Override

        public void onsub(String index, JSONObject val) {
//            arrayList_insert.add(val);
            System.out.println("kjvghfebgikjbsfkjbnfk"+val);
            jsonObj_insert=val;
//            qty(val);
//            insert();
        }
        @Override
        public void ondetail(String index, String val) {
        }
    });
}

    public void after_del(int index,String arrayList){
//    updatecart(index,arrayList);
//    arrayListdel.add(arrayList);

    cartDel(arrayList);
    items_cart.remove(index);
    System.out.println("jvghfhs"+items_cart);

    cartDetails=new CartDetails(this,items_cart);
    recyclerView_cart.setAdapter(cartDetails);

}

    public void updatecart(int index, String arrayList){


}

    public  void insertIntocarts(JSONObject jsonObject){
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
//            nameValuePairs.add(new BasicNameValuePair("d_id",id));

            for (int i=0;i<items.size();i++){
                jsonObj_insert.remove(items.get(i).get("id"));
            }
            //                    return nameValuePairs;
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
    new LoginAsync().execute(String.valueOf(jsonObject));
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
            HttpResponse httpResponse = httpclient.execute(new HttpGet(url));
            httpResponse.addHeader("Content-Type","application/json");
            httpResponse.addHeader("Authorization","Bearer"+token_val);
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


        Log.d(">>Result","-"+result);

        return result;


    }

    public void fetchBrand() {
    class APICalling extends AsyncTask {

        String result = "";

        ProgressDialog progressDialog = new ProgressDialog(Product.this);

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
//                JSONArray jsonArray = new JSONArray(result);
                for (int i = 0; i < cast.length(); i++) {

                    JSONObject jsonObject2=cast.getJSONObject(i);
                    String brand_name=jsonObject2.getString("brand_name");
                    String id=jsonObject2.getString("id");
                    String updated_at=jsonObject2.getString("unit_id");
                    String created_at=jsonObject2.getString("created_at");
                    Log.d(">>-NAME-", "" + cast);
                    product_brand=makeProduct_brand_select(id,brand_name,updated_at,created_at);
                    items_brand.add(product_brand);
                }

            } catch (Exception e) {
                Log.d(">>Error-", e.toString());
            }
        }
    }
}

    public void fetchCatById(int id1, int d_id, final String token_val){

        product1.clear();
        items.clear();

            class LoginAsync extends AsyncTask<String, Void, String> {
                //private Context context;
                public static final String KEY_EMAIL = "email";
                private Dialog loadingDialog;

                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                    loadingDialog = ProgressDialog.show(Product.this, "Please wait", "Loading...");
                }

                @Override
                protected String doInBackground(String... params) {
                    String name = params[0];
                    String id=params[1];
//
                    InputStream is = null;
                    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                    List<NameValuePair> nameValuePairs1=new ArrayList<>();

                    nameValuePairs.add(new BasicNameValuePair("starting_id",name));
                    nameValuePairs.add(new BasicNameValuePair("d_id",id));

                    //                    return nameValuePairs;
System.out.println("ldkhjgidh"+name);
                    String result = null;
                    try {
                        HttpClient httpClient = new DefaultHttpClient();
                        HttpPost httpPost=new HttpPost();
                        //                    HttpPost

                         httpPost = new HttpPost(httpClient_api.call().get("fetchCatById"));
                         httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
                         httpPost.addHeader("Authorization","Bearer "+token_val);
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
                    String s = result.trim();
                    String name1,parent_id,unit_id,is_last_lavel ,is_altFormula,id,altFormula,is_parent;
                    try {
                        double sub_total = 0, draft_disc1 = 0, draft_tax1 = 0;
                        String cid;
                        System.out.println("kjghfkdjghfjdbgfkbvnjbvldfblr");
                        JSONObject jsonObject = new JSONObject(result);
                        System.out.println("ugxvuudgunagfsunf      " + jsonObject);
                        //
                        JSONArray cast = jsonObject.getJSONArray("allcats");
                        loadingDialog.dismiss();
                        for (int l = 0; l < cast.length(); l++) {
                            System.out.println("cast.length" + cast.length());

                            JSONObject jsonObj = cast.getJSONObject(l);
                            System.out.println("cast_length");
//
                            if (jsonObj.has("is_parent")) {
                                is_parent=null;
                                name1 = jsonObj.getString("name");
                                parent_id = jsonObj.getString("parent_id");
                                unit_id = jsonObj.getString("unit_id");
                                is_last_lavel = jsonObj.getString("is_last_lavel");
                                is_altFormula = jsonObj.getString("is_altFormula");
                                id = jsonObj.getString("id");
                                altFormula = jsonObj.getString("altFormula");
                                btn_submit.setVisibility(View.VISIBLE);
                                product1 = makeProduct_draft_select(id, name1, parent_id, unit_id, is_last_lavel, is_altFormula, altFormula,is_parent);
                                items.add(product1);
                                product_idstring_check=makeProduct_draft_select(id, name1, parent_id, unit_id, is_last_lavel, is_altFormula, altFormula,is_parent);
                                items_idstring_check.add(product_idstring_check);
                                System.out.println("cast_length" + items);
                            }else {
                                is_parent="0";
                                name1 = jsonObj.getString("name");
                                parent_id = jsonObj.getString("parent_id");
                                unit_id = jsonObj.getString("unit_id");
                                is_last_lavel = jsonObj.getString("is_last_lavel");
                                is_altFormula = jsonObj.getString("is_altFormula");
                                id = jsonObj.getString("id");
                                altFormula = jsonObj.getString("altFormula");
                                product1 = makeProduct_draft_select(id, name1, parent_id, unit_id, is_last_lavel, is_altFormula, altFormula,is_parent);
                                items.add(product1);
                                product_idstring_check=makeProduct_draft_select(id, name1, parent_id, unit_id, is_last_lavel, is_altFormula, altFormula,is_parent);
                                items_idstring_check.add(product_idstring_check);
                                System.out.println("cast_length" + items);

                            }

//
                            fatchdata();
                        }
                    } catch (JSONException e) {

                    }
                    try{
                        JSONObject jsonObject1=new JSONObject(String.valueOf(items));
                        System.out.println("cast_length" + jsonObject1);
                    }catch (Exception e){

                    }
                    try{
                        String starting_id,d_id;
                        JSONObject jsonObject = new JSONObject(result);
                        JSONArray cast = jsonObject.getJSONArray("allcats");
                        for (int l = 0; l < cast.length(); l++) {
                            JSONObject jsonObj = cast.getJSONObject(l);
                            starting_id = jsonObj.getString("id");
                            product_id = makeProduct_select(starting_id);
                            items_idstring.add(product_id);
                        }
                    }catch (Exception e1){

                    }
                    try{
//
                        for (int i=0;i<items.size();i++){
                            String starting_id, d_id;
                            JSONObject jsonObject = new JSONObject(result);
                            JSONObject jsonObj = jsonObject.getJSONObject("msgInfo");
                            starting_id = jsonObj.getString("starting_id");
                            d_id = jsonObj.getString("d_id");
                            if (items.get(i).get("is_parent").equals(null)){

                            }else {
//
                            }

map.put("starting_id",starting_id);
map.put("d_id",d_id);
                            System.out.println("lhdgkjhdjgbjd" + hashMapMap);
                        System.out.println("lhdgkjhdjgbjd" + items);
                                }
                    }catch (Exception e1){

                    }
                }
            }
        new LoginAsync().execute(String.valueOf(id1), String.valueOf(d_id));
        }

        public void fetchAllcarts(int id){

        System.out.println("jhdgsjhfbjsf"+id);

        class LoginAsync extends AsyncTask<String, Void, String> {
            public static final String KEY_EMAIL = "email";
            private Dialog loadingDialog;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
//                loadingDialog = ProgressDialog.show(Product.this, "Please wait", "Loading...");
            }

            @Override
            protected String doInBackground(String... params) {
//            String name = params[0];
            String id=params[0];
//
            InputStream is = null;
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("d_id",id));
            String result = null;
            try {
                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost=new HttpPost();
                httpPost = new HttpPost(httpClient_api.call().get("fetchAllcarts"));
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
            String s = result.trim();
            String name1 = null,id2,name = null,namlayer="",parent_id = null,unit_id,is_last_lavel ,is_altFormula,id,altFormula = "",is_parent;
int alt_qty=0;
            String  id1 = "";
            String parent_id1 = "";
            String last_id="";
//            String counter;
            String lock_status="";
            String layer_id="";
            String qty = "";
            String lock_status_1;
            String ext_name;
//            String ext_note;
            String created_at="";
            String brand;
            String lastGroup="";
            String mkStr = "";
            String subLevel="";
            String altFormula1 = "";
            String namestr = "";
            ArrayList   <String> key = new ArrayList<>();
            ArrayList<String>arrayList=new ArrayList<>();
            ArrayList<String>id_arraylist=new ArrayList<>();
            JSONObject json_cart=new JSONObject();
            JSONObject json_cart1=new JSONObject();
            double sub_total = 0, draft_disc1 = 0, draft_tax1 = 0;
            String cid;
            try {

                System.out.println("kjghfkdjghfjdbgfkbvnjbvldfbr");

                JSONObject jsonObject = new JSONObject(result);

                JSONArray cast = jsonObject.getJSONArray("data");

                JSONObject jsonObject1 = new JSONObject();


                for (int i = 0; i < cast.length(); i++) {
                    jsonObject1 = cast.getJSONObject(i);

                    lock_status_1 = jsonObject1.getString("lock_status");
                    System.out.println("ugxvuudgunagfsunfaswdwqadeq      " + lock_status_1.equals(String.valueOf(1)));
                    if (lock_status_1.equals(String.valueOf(1))) {

                        name1 = jsonObject1.getString("cart");
                        try {
                            last_id = jsonObject.getString("lastId");
                        }catch (Exception e){

                        }
                        json_cart = new JSONObject(name1);
                    } else {
                        name = jsonObject1.getString("cart");
                        try {
                            last_id = jsonObject.getString("lastId");
                        }catch (Exception e){

                        }
                        json_cart1 = new JSONObject(name);
                        System.out.println("ugxvuudgunagfsunfaswdwqadeq      " + json_cart);
                    }
                }
            }catch (Exception e) {

            }

            try{
                Iterator<String> iter = json_cart.keys();
                    while (iter.hasNext()) {
                        key.add(iter.next());
//                        JSONArray jsonArray=jsonObject2.getJSONArray(String.valueOf(iter.next()));
System.out.println("oghkjdkdjg"+last_id);

                    }

                    JSONObject jsonObject3 = new JSONObject();
                    String mainstr = "";
                    String substr;

                for (int j=0;j<key.size();j++){
                    JSONArray jsonArray=json_cart.getJSONArray(key.get(j));

                    System.out.println("ugxvuudgunagfsunfaswdwqadeq      " + key.get(j));
                    for (int k=0;k<jsonArray.length();k++) {

                        jsonObject3 = jsonArray.getJSONObject(k);
                        id1 = jsonObject3.getString("id");
                        parent_id1 = jsonObject3.getString("parent_id");
                        lock_status = jsonObject3.getString("lock_status");
//                        counter = jsonObject3.getString("counter");
//                        dealer_id = jsonObject3.getString("dealer_id");
//                        layer_id = jsonObject3.getString("lastId");
                        qty = jsonObject3.getString("qty");
                        brand = jsonObject3.getString("brand");
                        ext_name = jsonObject3.getString("ext_name");
//                        ext_note = jsonObject3.getString("ext_notes");
                        created_at = jsonObject3.getString("created_at");
//                        updated_at = jsonObject3.getString("updated_at");
                        lastGroup = jsonObject3.getString("lastGroup");
                        mkStr = jsonObject3.getString("mkStr");
                        subLevel = jsonObject3.getString("subLevel");
                        altFormula1 = jsonObject3.getString("altFormula");
System.out.println("klhjgjdfkgjgkjdhnsluighi"+last_id);
                            if ((altFormula1.length() <= 4)) {
                                namestr += mkStr;
                                mainstr = ext_name + brand;
                                System.out.println("dfhfvfmbvhnamestr" + (namestr));
                                sub_total = Integer.parseInt(qty);
                                id_arraylist.add(id1);
//                            namestr="";
                            } else {
                                altFormula = "";
                                namlayer += (subLevel + "-" + qty + ",");
                                namestr = mkStr + " \n" + namlayer + "";
                                mainstr = ext_name + brand;
                                sub_total += Integer.parseInt(qty);
                                alt_qty += Integer.parseInt(lastGroup) * (Integer.parseInt(qty));
                                id_arraylist.add(id1);
                            }
                        }
                            product_cart_product = makeProduct_select_back(id_arraylist, id1, parent_id1,lock_status,
                                    layer_id, String.valueOf(sub_total), String.valueOf(alt_qty),mainstr
                                    , created_at, lastGroup, namestr, subLevel, altFormula, last_id);
                    items_idstring_cart.add(product_cart_product);
                            namestr="";
                            namlayer="";
//                            mainstr="";
                            alt_qty=0;
                            id_arraylist.clear();
                            sub_total=0;
//
                    System.out.println("dfhfvfmbvhrgegegegve"+items_idstring_cart);
            }
                key.clear();
                String barcode = null;
//
            } catch (JSONException e) {

            }
key.clear();
            try {
                        Iterator<String> iter = json_cart1.keys();
                        while (iter.hasNext()) {
                            arrayList.add(iter.next());
                        }
                    JSONObject jsonObject3 = new JSONObject();
                    String mainstr;
                    String substr;
                    System.out.println("dfhfvfmbvhefwe"+arrayList.size());

                    for (int j=0;j<arrayList.size();j++){
                        JSONArray jsonArray=json_cart1.getJSONArray(arrayList.get(j));

                        System.out.println("ugxvuudgunagfsunfaswdwqadeq      " + jsonArray.length());
                        for (int k=0;k<jsonArray.length();k++) {

                            jsonObject3 = jsonArray.getJSONObject(k);
                            id1 = jsonObject3.getString("id");
                            parent_id1 = jsonObject3.getString("parent_id");
                            lock_status = jsonObject3.getString("lock_status");
                            qty = jsonObject3.getString("qty");
                            created_at = jsonObject3.getString("created_at");
                            lastGroup = jsonObject3.getString("lastGroup");
                            mkStr = jsonObject3.getString("mkStr");
                            subLevel = jsonObject3.getString("subLevel");
                            altFormula1 = jsonObject3.getString("altFormula");
                            System.out.println("klhjgjdfkgjklhjgjdfkgj" + (lock_status != (null)));
//
                            if ((altFormula1.length() <= 4)) {
                                namestr += mkStr;
                                System.out.println("dfhfvfmbvhnamestr" + (namestr));
                                sub_total = Integer.parseInt(qty);
                                id_arraylist.add(id1);

//                            namestr="";
                            } else {
                                altFormula = "";
                                namlayer += (subLevel + "-" + qty + ",");
                                namestr = mkStr + " \n" + namlayer + "";
                                sub_total += Integer.parseInt(qty);
                                alt_qty += Integer.parseInt(lastGroup) * (Integer.parseInt(qty));
                                id_arraylist.add(id1);
                            }
                        }
                                product_cart=makeProduct_select_cart(id_arraylist, id1, parent_id1,lock_status,
                                        layer_id, String.valueOf(sub_total), String.valueOf(alt_qty)
                                        , created_at, lastGroup, namestr, subLevel, altFormula, last_id);
                                items_cart.add(product_cart);

                                namestr="";
                                namlayer="";
                                alt_qty=0;
                                id_arraylist.clear();
                                sub_total=0;
                        }
                        cart();
                    System.out.println("dfhfvfmbvhlkjhmnrk"+items_cart);
                String barcode = null;
////                    fatchdata();
//                }
            } catch (JSONException e) {
            }
        }
    }
    new LoginAsync().execute(String.valueOf(id));
}

    public void cartDel(String arrayList){

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
//            String name = params[0];
            String id=params[0];
//
            InputStream is = null;
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
//
            nameValuePairs.add(new BasicNameValuePair("id",id));
            System.out.println("oiyhufghjdbfgk"+nameValuePairs);
            String result = null;
            try {
                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost=new HttpPost();

                httpPost = new HttpPost(httpClient_api.call().get("cartDel"));
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
            String s = result.trim();

        }
    }
    new LoginAsync().execute(String.valueOf(arrayList));
}

    public static TreeMap<String, String> makeProduct_draft_select(String id, String name,String parent_id, String unit_id,String is_last_lavel,String is_altFormula,String altFormula,String is_parent) {

        TreeMap<String, String> treeMap = new TreeMap<String, String>();
        treeMap.put("id", String.valueOf(id));
        treeMap.put("name", name);
        treeMap.put("unit_id",unit_id);
        treeMap.put("parent_id",parent_id);
        treeMap.put("is_last_lavel",is_last_lavel);
        treeMap.put("is_altFormula",is_altFormula);
        treeMap.put("altFormula",altFormula);
        treeMap.put("is_parent",is_parent);

        //
        return treeMap;
    }

    public static TreeMap<String, String> makeProduct_select(String starting_id) {
        TreeMap<String, String> treeMap = new TreeMap<String, String>();
        treeMap.put("starting_id",starting_id);
        return treeMap;
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

    public static TreeMap<String, String>
    makeProduct_select_back(ArrayList<String> key,String id,String parent_id,String lock_status,
                            String layer_id,String qty,String alt_qty,String ext_name,
                            String created_at,String lastGroup, String mkStr,
                            String subLevel, String altFormula,String lastId) {

        TreeMap<String, String> treeMap = new TreeMap<String, String>();
        treeMap.put("key", String.valueOf(key));
        treeMap.put("id", String.valueOf(id));
        treeMap.put("parent_id",parent_id);
        treeMap.put("lock_status",lock_status);
//        treeMap.put("counter",counter);
//        treeMap.put("dealer_id",dealer_id);
        treeMap.put("layer_id",layer_id);
        treeMap.put("qty",qty);
//        treeMap.put("brand",brand);
        treeMap.put("alt_qty",alt_qty);
        treeMap.put("ext_name",ext_name);
//        treeMap.put("ext_note",ext_note);
        treeMap.put("created_at",created_at);
        treeMap.put("lastId",lastId);
        treeMap.put("lastGroup",lastGroup);
        treeMap.put("mkStr",mkStr);
        treeMap.put("subLevel",subLevel);
        treeMap.put("altFormula",altFormula);
        return treeMap;
    }



    public static TreeMap<String, String>
    makeProduct_select_cart(ArrayList<String> key,String id,String parent_id,String lock_status,
                            String layer_id,String qty,String alt_qty,
                            String created_at,String lastGroup, String mkStr,
                            String subLevel, String altFormula,String lastId) {

        TreeMap<String, String> treeMap = new TreeMap<String, String>();
        treeMap.put("key", String.valueOf(key));
        treeMap.put("id", String.valueOf(id));
        treeMap.put("parent_id",parent_id);
        treeMap.put("lock_status",lock_status);
//        treeMap.put("counter",counter);
//        treeMap.put("dealer_id",dealer_id);
        treeMap.put("layer_id",layer_id);
        treeMap.put("qty",qty);
//        treeMap.put("brand",brand);
        treeMap.put("alt_qty",alt_qty);
//        treeMap.put("ext_name",ext_name);
//        treeMap.put("ext_note",ext_note);
        treeMap.put("created_at",created_at);
        treeMap.put("lastId",lastId);
        treeMap.put("lastGroup",lastGroup);
        treeMap.put("mkStr",mkStr);
        treeMap.put("subLevel",subLevel);
        treeMap.put("altFormula",altFormula);
        return treeMap;
    }
}
