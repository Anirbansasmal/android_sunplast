package com.android.sunplast;

import android.annotation.SuppressLint;
import android.content.Context;
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

import java.util.ArrayList;
import java.util.TreeMap;

public class expandable_adapter extends RecyclerView.Adapter<expandable_adapter.MyViewHolder>implements RecyclerView.OnItemTouchListener {

    Context context;
    private static  ArrayList<TreeMap<String,String>> items_update=new ArrayList<>();

    public expandable_adapter(Context applicationContext, ArrayList<TreeMap<String,String>> items_update){
        this.context = applicationContext;
        this.items_update = items_update;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {

        return 0;
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
