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

import java.util.ArrayList;
import java.util.TreeMap;

public class Details extends RecyclerView.Adapter<Details.MyViewHolder>implements RecyclerView.OnItemTouchListener {
    Context context;
    private static ArrayList<TreeMap<String,String>> items_update=new ArrayList<>();

    public Details(Context applicationContext, ArrayList<TreeMap<String,String>> items_update){
        this.context = applicationContext;
        this.items_update = items_update;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =  LayoutInflater.from(parent.getContext())
                .inflate(R.layout.details, parent, false);
        MyViewHolder vh = new MyViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int position) {
        for (int i=0;i<items_update.size();i++){
//    if (items_update.get(i).get("mkStr").equals(items_update.get(position).get("mkStr"))) {
            myViewHolder.tv_sl.setText(""+items_update.get(position).get("sl_no"));
            myViewHolder.tv_brand.setText(""+items_update.get(position).get("brand"));
            myViewHolder.tv_description.setText(""+items_update.get(position).get("layer"));
            myViewHolder.tv_Qty.setText(""+items_update.get(position).get("qty"));
            myViewHolder.tv_OrderDate.setText(""+items_update.get(position).get("created_at"));
//
            final Product product=new Product();

            System.out.println("lkhjkjbgndkjgnk"+items_update.get(position).get("mkStr"));

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
        public TextView tv_sl,tv_fname, tv_Qty,tv_description,tv_OrderDate,tv_brand,tv_price,tv_totalprice,tv_rate,tv_tax,tv_disc,tv_id,tv_barcode;
        EditText editText;
        public ImageView img_fimage;
        public Button btn_delete;
        public RecyclerView selctrecyclerView;
        public RelativeLayout viewBackground,viewForeground;

        @SuppressLint("WrongViewCast")
        public MyViewHolder(View view) {
            super(view);
            //this.setIsRecyclable(false);
            tv_sl = (TextView)view.findViewById(R.id.txt_slno);
            tv_Qty=view.findViewById(R.id.txt_qty);
            tv_brand=view.findViewById(R.id.txt_brand);
            tv_description=view.findViewById(R.id.txt_description);
            tv_OrderDate=view.findViewById(R.id.txt_OrderDate);


//            tv_totalprice = (TextView) view.findViewById(R.id.tv_total);

        }
    }
}
