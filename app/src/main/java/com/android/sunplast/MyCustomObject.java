package com.android.sunplast;

import org.json.JSONObject;

import java.util.ArrayList;

public class MyCustomObject {
//    public MyCustomObject(MyCustomObjectListener myCustomObjectListener) {
//    }
    public interface MyCustomObjectListener {
        // These methods are the different events and
        // need to pass relevant arguments related to the event triggered
        // or when data has been loaded
        public void onadd(String index, String val);
        public void onsub(String index, JSONObject val);
        public void ondetail(String index,String val);
    }
    private static MyCustomObjectListener listener;
    //    RecylerviewActivity object2=new RecylerviewActivity();
    public MyCustomObject(MyCustomObjectListener listener) {
        // set null or default listener or accept as argument to constructor
        this.listener = listener;
    }
    // Assign the listener implementing events interface that will receive the events
    public void setCustomObjectListener(MyCustomObjectListener listener) {
        this.listener = listener;
    }

    public void add(String index, String value) {

        if (listener != null) {
            System.out.println("kjvghfebgi"+value);
//            object2.incr_decr();
            listener.onadd(index,value); // <---- fire listener here
        }
    }

    public void subtract(String index, JSONObject value) {
        if (listener != null) {
            System.out.println("kjvghfebgi"+value);
            listener.onsub(index,value); // <---- fire listener here
        }
    }
    public void detail(String index, String value) {
        if (listener != null) {
            System.out.println("kjvghfebgi"+value);
            listener.ondetail(index,value); // <---- fire listener here
        }
    }
}
