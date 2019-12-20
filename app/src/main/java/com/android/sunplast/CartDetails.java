package com.android.sunplast;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.TreeMap;

public class CartDetails extends RecyclerView.Adapter<CartDetails.MyViewHolder>implements RecyclerView.OnItemTouchListener {
    Context context;
    //    private List<FruitData> fruitDataArrayList;
//    private List<FruitData> fruitData;
    private static ArrayList<String> data;
    private static  ArrayList<TreeMap<String,String>> items_update=new ArrayList<>();
    //    private ArrayList<fruit>fruits;
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =  LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_cart, parent, false);
        MyViewHolder vh = new MyViewHolder(view);

        return vh;
    }


    public CartDetails(Context applicationContext, ArrayList<TreeMap<String,String>> items_update) {
        this.context = applicationContext;
        this.items_update = items_update;
//        System.out.println("lkhjkjbgndkjgnk"+items_update);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, final int position) {

for (int i=0;i<items_update.size();i++){
//    if (items_update.get(i).get("mkStr").equals(items_update.get(position).get("mkStr"))) {
        myViewHolder.tv_fname.setText(""+items_update.get(position).get("mkStr"));
        myViewHolder.tv_Qty.setText(""+items_update.get(position).get("qty"));
        myViewHolder.tv_Alt_Qty.setText(""+items_update.get(position).get("alt_qty"));
        final int finalI = i;

         final MyCustomObject myCustomObject=new MyCustomObject(new MyCustomObject.MyCustomObjectListener() {

            @Override
            public void onadd(String index, String val) {


            }


            @Override
            public void onsub(String index, JSONObject val) {

            }
             @Override
             public void ondetail(String index, String val) {

             }
        });

        final Product product=new Product();

//    }
    myViewHolder.img_fimage.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (view.getId()==myViewHolder.img_fimage.getId()) {
                product.cart_listinner();
                myCustomObject.add(String.valueOf(position),items_update.get(position).get("key"));


                }
//
//            }
        }
    });

    System.out.println("lkhjkjbgndkjgnk"+items_update.get(position).get("key"));

}
    }

    @Override
    public int getItemCount() {
//        System.out.println("lkhjkjbgdfghdgndkjgnk"+items_update.size());
        return items_update.size();
    }

    @Override
    public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
        return false;
    }

    @Override
    public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView tv_fname, tv_Qty,tv_Alt_Qty,tv_price,tv_totalprice,tv_rate,tv_tax,tv_disc,tv_id,tv_barcode;
        EditText editText;
        public ImageView img_fimage;
        public Button btn_delete;
        public RecyclerView selctrecyclerView;
        public RelativeLayout viewBackground,viewForeground;

        @SuppressLint("WrongViewCast")
        public MyViewHolder(View view) {
            super(view);
            //this.setIsRecyclable(false);
            tv_fname = (TextView)view.findViewById(R.id.txt_Name);
            tv_Qty=view.findViewById(R.id.txt_Qty);
            tv_Alt_Qty=view.findViewById(R.id.txt_Alt_Qty);
            img_fimage=view.findViewById(R.id.txt_Remove);

//            tv_totalprice = (TextView) view.findViewById(R.id.tv_total);

        }
    }
}
