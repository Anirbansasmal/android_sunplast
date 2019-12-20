package com.android.sunplast;

import java.util.HashMap;
import java.util.Map;

public class HttpClient_api {
    Map<String,String> api=new HashMap<String, String>();
    Map<String,String> token=new HashMap<String, String>();
    public static final String mypreference = "mypref";

    public Map<String, String> call() {
//        String url = "http://192.168.0.122/sunplast/pro-api/public/api/";
        String url="http://intlum.in/sunplast/api/public/api/";
//        String url="http://7awake.com/pos/api/restapi/";
        api.put("login", url + "login");
        api.put("register", url + "register");
        api.put("fetchCatById", url + "fetchCatById");
        api.put("insertIntocarts",  url + "insertIntocarts");
        api.put("fetchAllcarts",  url + "fetchAllcarts");
        api.put("cartDel", url + "cartDel");
        api.put("fetchOrderByUserId",  url + "fetchOrderByUserId");
        api.put("fetchOrderById",  url + "fetchOrderById");
        api.put("orderOptionSort",  url + "orderOptionSort");
        api.put("fetchBrand", url + "fetchBrand");
        api.put("insertOrderFromCart",  url + "insertOrderFromCart");
        api.put("cartUnloading", "" + url + "cartUnloading");
        api.put("updateDealerDetails", "" + url + "updateDealerDetails");
        api.put("auth", "" + url + "auth");
//        api.put("balence", "" + url + "balence");
//        api.put("payment", "" + url + "saveDraft");
        return api;
    }
}
