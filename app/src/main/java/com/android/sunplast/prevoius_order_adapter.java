package com.android.sunplast;

import android.annotation.SuppressLint;
import android.content.Context;
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
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.TreeMap;

public class prevoius_order_adapter extends RecyclerView.Adapter<prevoius_order_adapter.MyViewHolder>implements RecyclerView.OnItemTouchListener {
    Context context;
    private static  ArrayList<TreeMap<String,String>> items_update=new ArrayList<>();
   public prevoius_order_adapter(Context applicationContext, ArrayList<TreeMap<String,String>> items_update){
       this.context = applicationContext;
       this.items_update = items_update;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =  LayoutInflater.from(parent.getContext())
                .inflate(R.layout.orders, parent, false);
        MyViewHolder vh = new MyViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int position) {
        for (int i=0;i<items_update.size();i++){
//    if (items_update.get(i).get("mkStr").equals(items_update.get(position).get("mkStr"))) {
            myViewHolder.tv_Order_Id.setText(""+items_update.get(position).get("admin_order_id"));
            myViewHolder.tv_Dispatch.setText(""+items_update.get(position).get("dispatch"));
            myViewHolder.tv_OrderDate.setText(""+items_update.get(position).get("created_at"));
//            myViewHolder.tv_Notes.setText(""+items_update.get(position).get("dealer_notes"));
//            myViewHolder.tv_admin_details.setText("View Details"+items_update.get(position));

//            myViewHolder.tv_admin_Notes.setText(""+items_update.get(position).get("admin_notes"));


            System.out.println("lkhjkjbgndkjgnk"+items_update.get(position).get("created_at"));
//            final int finalI = i;
//            final Previous_Orders previous_orders=new Previous_Orders();
            final MyCustomObject myCustomObject=new MyCustomObject(new MyCustomObject.MyCustomObjectListener() {

                @Override
                public void onadd(String index, String val) {


                }


                @Override
                public void onsub(String index, JSONObject val) {
//previous_orders.details();
                }
                @Override
                public void ondetail(String index, String val) {
//previous_orders.details();
                }
            });

            final Previous_Orders previous_orders=new Previous_Orders();

//    }
            myViewHolder.tv_admin_details.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    previous_orders.details();
                    myCustomObject.detail(String.valueOf(position),items_update.get(position).get("id"));


//                    items_update.remove(finalI);
                }
            });



        }
    }

    @Override
    public int getItemCount() {

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
        public TextView txt_Order_Id,tv_Order_Id,tv_Dispatch, tv_Notes,tv_Qty,tv_OrderDate,tv_admin_details,tv_admin_Notes,tv_rate,tv_tax,tv_disc,tv_id,tv_barcode;
        EditText editText;
        public ImageView img_fimage;
        public Button btn_delete;
        public RecyclerView selctrecyclerView;
        public RelativeLayout viewBackground,viewForeground;

        @SuppressLint("WrongViewCast")
        public MyViewHolder(View view) {
            super(view);
            //this.setIsRecyclable(false);
            tv_Order_Id = (TextView)view.findViewById(R.id.txt_Order_Id);
            tv_admin_details=view.findViewById(R.id.txt_admin_details);
            tv_Dispatch=view.findViewById(R.id.txt_Dispatch);
//            tv_Notes=view.findViewById(R.id.txt_Notes);
            tv_OrderDate=view.findViewById(R.id.txt_OrderDate);
//            tv_admin_Notes=view.findViewById(R.id.txt_admin_Notes);


//            tv_totalprice = (TextView) view.findViewById(R.id.tv_total);

        }
    }
}
