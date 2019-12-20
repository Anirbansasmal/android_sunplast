package com.android.sunplast;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

public class Previous_Orders extends AppCompatActivity {
    private static RecyclerView recyclerView,recyclerView_previous;
    private static RecyclerView.LayoutManager layoutManager;
    private static RecyclerView.LayoutManager layoutManager_previous;
    private static prevoius_order_adapter prevoius_order_adapter;
    private static Details prevoius_Details_adapter;
     TreeMap<String, String> product1 = new TreeMap<String, String>();
     TreeMap<String, String> product = new TreeMap<String, String>();
     ArrayList<TreeMap<String, String>> items = new ArrayList<TreeMap<String, String>>();
     ArrayList<TreeMap<String, String>> items1 = new ArrayList<TreeMap<String, String>>();
    public static String token;
    public static String token_val,d_id,val;
    Spinner tv_spinner;
    Button btn_back;
    public static final String mypreference = "mypref";
   public static RelativeLayout relativeLayout_prevoius,relativeLayout_orders;
    HttpClient_api httpClient_api=new HttpClient_api();
    List<String> categories = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.previous_orders);
        relativeLayout_prevoius=findViewById(R.id.previous_order);
relativeLayout_orders=findViewById(R.id.orders);
tv_spinner=findViewById(R.id.spinner_item);
btn_back=findViewById(R.id.btn_back);
//adapter order
        recyclerView=findViewById(R.id.recycler_details);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
//adapter prevoius order
        recyclerView_previous=findViewById(R.id.recycler_previous);
        recyclerView_previous.setHasFixedSize(true);
        layoutManager_previous=new LinearLayoutManager(this);
        recyclerView_previous.setLayoutManager(layoutManager_previous);
//        tv_spinner.setOnItemSelectedListener(this);
        categories.add("Select");
        categories.add("Today");
        categories.add("This Month");
        categories.add("All");
        items1.clear();
        product.clear();
        items.clear();
        product1.clear();
        shared();
        spinner();

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
            fetchOrderById(Integer.parseInt(d_id),token_val);
            previous(Integer.parseInt(d_id),token_val);
//            fetchOrderByUserId(Integer.parseInt(d_id));
//            rec_back(Integer.parseInt(val),token_val);
//            fetchAllcarts(Integer.parseInt(d_id));
            System.out.println("kjghcvjhsd" + d_id);
            editor.commit();

        } catch (Exception e) {

        }
//        return token;
    }

    public void spinner(){
        items.clear();
        product1.clear();
//        prevoius_order_adapter.notifyDataSetChanged();
        tv_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                String item = adapterView.getItemAtPosition(position).toString();
                items.clear();
                if (tv_spinner.getSelectedItem().equals("Select")){
//                    items.clear();
//                    product1.clear();
//                    prevoius_order_adapter.notifyDataSetChanged();
                    Toast.makeText(adapterView.getContext(), "Selected: " + item.toUpperCase(), Toast.LENGTH_LONG).show();
//            orderOptionSort(item.toUpperCase(),token_val);
                }else if (tv_spinner.getSelectedItem().equals("Today")){
                    items.clear();
                    product1.clear();
                    prevoius_order_adapter.notifyDataSetChanged();
                    orderOptionSort(item.toUpperCase(),token_val);
                    Toast.makeText(adapterView.getContext(), "Selected: " + item.toUpperCase(), Toast.LENGTH_LONG).show();
                }else if (tv_spinner.getSelectedItem().toString().equals("This Month")){
                    item="MONTHLY";
                    items.clear();
                    product1.clear();
                    prevoius_order_adapter.notifyDataSetChanged();
                    orderOptionSort(item,token_val);
                }else if (tv_spinner.getSelectedItem().equals("All")){
                    items.clear();
                    product1.clear();
                    prevoius_order_adapter.notifyDataSetChanged();
                    orderOptionSort(item.toUpperCase(),token_val);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

//        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
//        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        tv_spinner.setAdapter(dataAdapter);
    }

    public void fetchOrderById(){
        prevoius_order_adapter=new prevoius_order_adapter(this,items);
        recyclerView_previous.setAdapter(prevoius_order_adapter);

    }

    public void fetchOrderById_spinner(){
        prevoius_order_adapter=new prevoius_order_adapter(this,items);
        recyclerView_previous.setAdapter(prevoius_order_adapter);

    }

    public void fetchOrderByUserId(){
        relativeLayout_prevoius.setVisibility(View.INVISIBLE);
        relativeLayout_orders.setVisibility(View.VISIBLE);

        prevoius_Details_adapter=new Details(this,items1);
        recyclerView.setAdapter(prevoius_Details_adapter);
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
                items.clear();
        update(val);
            }

        });
    }

    public void update(String order_id){
        fetchOrderByUserId(Integer.parseInt(order_id));
    }

    public void previous(final int id, final String token_val){
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                relativeLayout_prevoius.setVisibility(View.VISIBLE);
                relativeLayout_orders.setVisibility(View.INVISIBLE);

                items.clear();
                items1.clear();
                product.clear();
                product1.clear();
                fetchOrderById(id,token_val);
                System.out.println("jhdgsjhfbjsf"+id);
            }
        });
    }

    public void fetchOrderById(int id, String token_val){

//        System.out.println("jhdgsjhfbjsf"+id);

        class LoginAsync extends AsyncTask<String, Void, String> {
            //private Context context;

            public static final String KEY_EMAIL = "email";

            private Dialog loadingDialog;

            @Override

            protected void onPreExecute() {

                super.onPreExecute();
                loadingDialog = ProgressDialog.show(Previous_Orders.this, "Please wait", "Loading...");

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
                    httpPost = new HttpPost(httpClient_api.call().get("fetchOrderById"));
                    httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
                    httpPost.addHeader("Authorization","Bearer "+token);
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
                String name1 = null,id2,name = null,namlayer="",parent_id = null,unit_id,is_last_lavel ,is_altFormula,id,altFormula = null,is_parent;
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
                    JSONObject jsonObject1 = jsonObject.getJSONObject("success");

                    //
                    JSONArray cast = jsonObject1.getJSONArray("fetch_order_by_id");
//                    JSONObject json_obj;
//                    loadingDialog.dismiss();
                    for (int i=0;i<cast.length();i++){
                        JSONObject json_obj=cast.getJSONObject(i);
//
                                id1 =           json_obj.getString("id");
                                admin_order_id =    json_obj.getString("admin_order_id");
//                                dispatch =   json_obj.getString("dispatch");
                                dealer_notes =       json_obj.getString("dealer_notes");
                                admin_notes =     json_obj.getString("admin_notes");
                                created_at =      json_obj.getString("created_at");

                        if (admin_notes.equals("null")) {
                            admin_notes="";
                        }else {
                            admin_notes=json_obj.getString("admin_notes");
                        }

//                        if (created_at.length()>0){
//
//                        }else {
                            created_at =      json_obj.getString("created_at");
//                        }

                        System.out.println("dfhfvfmbvh"+(created_at.length()));
                                if ((json_obj.getString("dispatch").equals("null"))){
                                    dispatch="Pending";

                                    product1 = makeProduct_fetchOrderById(id1,admin_order_id,dispatch,dealer_notes,admin_notes,created_at);
                                    items.add(product1);
                                }
                                if ((json_obj.getString("dispatch")!=("null"))){
                                    dispatch =   json_obj.getString("dispatch");
                                    product1 = makeProduct_fetchOrderById(id1,admin_order_id,dispatch,dealer_notes,admin_notes,created_at);
                                    items.add(product1);
                                }
loadingDialog.dismiss();

//                        if ((json_obj.getString("admin_notes").equals(null))){
//                            dealer_notes="";
//
//                            product1 = makeProduct_fetchOrderById(id1,admin_order_id,dispatch,dealer_notes,admin_notes,created_at);
//                            items.add(product1);
//                        }else {
//
//                            product1 = makeProduct_fetchOrderById(id1,admin_order_id,dispatch,dealer_notes,admin_notes,created_at);
//                            items.add(product1);
//                        }
                        System.out.println("dfhfvfmbvh"+items);
                        fetchOrderById();

                        }

//
                } catch (JSONException e) {

                }

            }
        }
        new LoginAsync().execute(String.valueOf(id));
    }

    public void fetchOrderByUserId(int id){

//        System.out.println("jhdgsjhfbjsf"+id);
        class LoginAsync extends AsyncTask<String, Void, String> {
            //private Context context;

            public static final String KEY_EMAIL = "email";

            private Dialog loadingDialog;
//            ProgressDialog progressDialog = new ProgressDialog(Previous_Orders.this);
            @Override

            protected void onPreExecute() {

                super.onPreExecute();

//                progressDialog.setMessage("Loading");
//                progressDialog.show();
            }

            @Override
            protected String doInBackground(String... params) {
//            String name = params[0];
                String id=params[0];
//
                InputStream is = null;
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
//
                nameValuePairs.add(new BasicNameValuePair("orderID",id));

                String result = null;
                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost=new HttpPost();
                    //                    HttpPost

//                        httpPost.setHeader(httpclient_api.call().get("Authorization"), shared());
                    httpPost = new HttpPost(httpClient_api.call().get("fetchOrderByUserId"));
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
//                super.onPostExecute(o);

//                progressDialog.dismiss();
                String s = result.trim();
                String name1 = null,id2,name = null,namlayer="",parent_id = null,unit_id,is_last_lavel ,is_altFormula,id,altFormula = null,is_parent;
                int alt_qty=0;
                int sl_no = 0;
                String brand;
                String layer;
                String qty;
                String created_at;

                ArrayList   <String> key = new ArrayList<>();
                ArrayList<String>id_arraylist=new ArrayList<>();
                try {
                    double sub_total = 0, draft_disc1 = 0, draft_tax1 = 0;
                    String cid;
                    System.out.println("kjghfkdjghfjdbgfkbvnjbvldfblr"+result);
                    JSONObject jsonObject = new JSONObject(result);
                    JSONObject jsonObject1 = jsonObject.getJSONObject("success");
                    System.out.println("dfhfvfmbvh"+jsonObject1);
                    //
                    JSONArray cast = jsonObject1.getJSONArray("fetch_order_by_user_id");
//                    JSONObject json_obj=new JSONObject();
                    for (int i=0;i<cast.length();i++){
                        JSONObject json_obj=cast.getJSONObject(i);
//
//                        id1 =           json_obj.getString("id");
                        brand =    json_obj.getString("brand");
                        layer =   json_obj.getString("layer");
                        created_at = json_obj.getString("created_at");
                        qty =     json_obj.getString("qty");



                            product = makeProduct_fetchOrderByUserId(String.valueOf(i+1),brand,layer,qty,created_at);
                            items1.add(product);

                            System.out.println("dfhfvfmbvgbhnrhh"+items);
                    }



//                    loadingDialog.dismiss();


                    fetchOrderByUserId();
//                for (int l = 0; l < cast.length(); l++) {
//                    System.out.println("cast.length" + cast.length());
//
//                    JSONObject jsonObj = cast.getJSONObject(l);
//
//                    if (jsonObj.has("is_parent")) {
//                        is_parent=null;
//                        name1 = jsonObj.getString("name");
//                        parent_id = jsonObj.getString("parent_id");
//                        unit_id = jsonObj.getString("unit_id");
//                        is_last_lavel = jsonObj.getString("is_last_lavel");
//                        is_altFormula = jsonObj.getString("is_altFormula");
//                        id = jsonObj.getString("id");
//                        altFormula = jsonObj.getString("altFormula");
//                        btn_submit.setVisibility(View.VISIBLE);
////                        product1 = makeProduct_draft_select(id, name1, parent_id, unit_id, is_last_lavel, is_altFormula, altFormula,is_parent);
////                        items.add(product1);
////                        product_idstring_check=makeProduct_draft_select(id, name1, parent_id, unit_id, is_last_lavel, is_altFormula, altFormula,is_parent);
////                        items_idstring_check.add(product_idstring_check);
//                        System.out.println("cast_length" + items);
//
//                    }
//
////                    fatchdata();
//                }
                } catch (JSONException e) {

                }

            }
        }
        new LoginAsync().execute(String.valueOf(id));
    }

    public void orderOptionSort(String myOption, final String token_val){
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
                nameValuePairs.add(new BasicNameValuePair("myOption",id));

                String result = null;
                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost=new HttpPost();
                    //                    HttpPost

//                        httpPost.setHeader(httpclient_api.call().get("Authorization"), shared());
                    httpPost = new HttpPost(httpClient_api.call().get("orderOptionSort"));
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
                String name1 = null,id2,name = null,namlayer="",parent_id = null,unit_id,is_last_lavel ,is_altFormula,id,altFormula = null,is_parent;
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
                    JSONObject jsonObject1 = jsonObject.getJSONObject("success");

                    //
                    JSONArray cast = jsonObject1.getJSONArray("data");
//                    JSONObject json_obj;
//                    loadingDialog.dismiss();
                    for (int i=0;i<cast.length();i++){
                        JSONObject json_obj=cast.getJSONObject(i);
//
                        id1 =           json_obj.getString("id");
                        admin_order_id =    json_obj.getString("admin_order_id");
//                                dispatch =   json_obj.getString("dispatch");
                        dealer_notes =       json_obj.getString("dealer_notes");
                        admin_notes =     json_obj.getString("admin_notes");
                        created_at =      json_obj.getString("created_at");

                        if (admin_notes.equals("null")) {
                            admin_notes="";
                        }else {
                            admin_notes=json_obj.getString("admin_notes");
                        }

//                        if (created_at.length()>0){
//
//                        }else {
                        created_at =      json_obj.getString("created_at");
//                        }

                        System.out.println("dfhfvfmbvh"+(created_at.length()));
                        if ((json_obj.getString("dispatch").equals("null"))){
                            dispatch="Pending";

                            product1 = makeProduct_fetchOrderById(id1,admin_order_id,dispatch,dealer_notes,admin_notes,created_at);
                            items.add(product1);
                        }
                        if ((json_obj.getString("dispatch")!=("null"))){
                            dispatch =   json_obj.getString("dispatch");
                            product1 = makeProduct_fetchOrderById(id1,admin_order_id,dispatch,dealer_notes,admin_notes,created_at);
                            items.add(product1);
                        }
//
                        System.out.println("dfhfvfmbvh"+items);
                        fetchOrderById_spinner();

                    }

//
                } catch (JSONException e) {

                }

            }
        }
        new LoginAsync().execute(String.valueOf(myOption));
    }

    public static TreeMap<String, String> makeProduct_fetchOrderByUserId(String sl_no,String brand, String layer,String qty,String created_at) {
//        TreeMap<String,String> product_update_adepter=new TreeMap<String, String>();

        TreeMap<String, String> treeMap = new TreeMap<String, String>();
        treeMap.put("sl_no", String.valueOf(sl_no));
        treeMap.put("brand", brand);
        treeMap.put("layer",layer);
        treeMap.put("created_at",created_at);
        treeMap.put("qty",qty);
        //
        return treeMap;
    }

    public static TreeMap<String, String>
    makeProduct_fetchOrderById(String id,String admin_order_id,String dispatch,String dealer_notes,String admin_notes, String created_at) {

        TreeMap<String, String> treeMap = new TreeMap<String, String>();
        treeMap.put("id", String.valueOf(id));
        treeMap.put("admin_order_id",admin_order_id);
        treeMap.put("dispatch",dispatch);
        treeMap.put("dealer_notes",dealer_notes);
        treeMap.put("admin_notes",admin_notes);
        treeMap.put("created_at",created_at);
        return treeMap;
    }


}
