package com.android.sunplast;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
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
import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;

public class Selectedlayers extends RecyclerView.Adapter<Selectedlayers.MyViewHolder>implements RecyclerView.OnItemTouchListener{

    Context context;
//    private List<FruitData> fruitDataArrayList;
//    private List<FruitData> fruitData;
    private ArrayList<String> data;
    private static  ArrayList<TreeMap<String,String>> items_update=new ArrayList<>();
//    private ArrayList<fruit>fruits;
private static JSONObject jsonObject=new JSONObject();
    public Selectedlayers(Context applicationContext, ArrayList<TreeMap<String,String>> items_update) {
        this.context = applicationContext;
        this.items_update = items_update;
    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        // create a new view
        View view =  LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.row_selected_fruit_list, viewGroup, false);
//        View view1=LayoutInflater.from(viewGroup.getContext())
//                .inflate(R.layout.activity_cal,viewGroup,false);


        MyViewHolder vh = new MyViewHolder(view);
//        MyViewHolder vh1=new MyViewHolder(view1);
//        view.setOnClickListener(RecylerviewActivity.myOnClickListener);
//        view.setOnClickListener(RecylerviewActivity.myOnClickListener1);

        return vh;

    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, final int position) {

        for (int i=0;i<items_update.size();i++) {
            myViewHolder.tv_fname.setText(items_update.get(position).get("name"));
            System.out.println("bgsdjbgfj"+(Integer.parseInt(items_update.get(position).get("is_last_lavel"))<=(0)));
            if ((items_update.get(position).get("is_parent"))==(null)){
                myViewHolder.editText.setVisibility(View.VISIBLE);
            }else {
                myViewHolder.editText.setVisibility(View.INVISIBLE);
            }
//            myViewHolder.tv_fdetails.setText();

final Product parent=new Product();
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
final ArrayList<String>arrayList=new ArrayList<>();

            myViewHolder.editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    try {
                        jsonObject.put(items_update.get(position).get("id"),myViewHolder.editText.getText().toString());
                        System.out.println("sdhvgfhjsbf"+jsonObject);
                        myCustomObject.subtract(String.valueOf(position), jsonObject);
                        parent.cart_listinner();
                    }catch (Exception e){

                    }
                }
            });

//                    arrayList.add(myViewHolder.editText.getText().toString());

        }
    }
//
    @Override
    public int getItemCount()
    {
//        System.out.println("jbvjhbvdjbvruegbeugfeiu "+fruitDataArrayList.size());
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
        public TextView tv_fname, tv_fdetails,tv_count,tv_price,tv_totalprice,tv_rate,tv_tax,tv_disc,tv_id,tv_barcode;
        EditText editText;
        public ImageView img_fimage;
        public Button btn_delete;
        public RecyclerView selctrecyclerView;
        public RelativeLayout viewBackground,viewForeground;

        @SuppressLint("WrongViewCast")
        public MyViewHolder(View view) {
            super(view);
            //this.setIsRecyclable(false);
            tv_fname = (TextView)view.findViewById(R.id.txt_itemcount);
            editText=view.findViewById(R.id.txt_quentity);

//            tv_totalprice = (TextView) view.findViewById(R.id.tv_total);


        }



    }
}