package com.android.sunplast;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerTuchListener implements RecyclerView.OnItemTouchListener {
    private ClickListener mclickListener;
    private GestureDetector gestureDetector;
    public interface ClickListener{
        public void onItemClick(View view, int position);
        public void onLongClick(View view, int position);
    }

    public RecyclerTuchListener(Context context, final RecyclerView recyclerView, ClickListener clickListener){
        mclickListener=clickListener;
        gestureDetector=new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onDown(MotionEvent motionEvent) {
                return false;
            }

            @Override
            public void onShowPress(MotionEvent motionEvent) {

            }

            @Override
            public boolean onSingleTapUp(MotionEvent motionEvent) {
                return true;
            }

            @Override
            public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
                return false;
            }
            public void onLongPress(MotionEvent e) {
                View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                if (child != null && mclickListener != null) {
                    mclickListener.onLongClick(child, recyclerView.getChildAdapterPosition(child));
                }
            }


//            @Override
//            public void onLongPress(MotionEvent motionEvent) {
//                View child=recyclerview.findChildViewUnder(motionEvent.getX(),motionEvent.getY());
//                if (child!=null && clickListener!=null){
//                    clickListener.onLongClick(child,recyclerview.getChildAdapterPosition(child));
//                }
//            }

            @Override
            public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
                return false;
            }
        });
    }
    @Override
    public boolean onInterceptTouchEvent(@NonNull RecyclerView reV, @NonNull MotionEvent motionEvent) {
        View child = reV.findChildViewUnder(motionEvent.getX(), motionEvent.getY());
        if (child != null && mclickListener != null && gestureDetector.onTouchEvent(motionEvent)) {
//        if (child!=null && gestureDetector.onTouchEvent(motionEvent)){
            int position=reV.getChildAdapterPosition(child);
//            Toast.makeText()
            mclickListener.onItemClick(child, reV.getChildAdapterPosition(child));
            return true;
        }
        return false;
    }

    @Override
    public void onTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {
            // TODO Auto-generated method stub
//        int counter=0;
//            if(motionEvent.getAction()==MotionEvent.ACTION_UP)
//                myImageView.setImageDrawable(R.drawable.image1);
//            else if(event.getAction()==MotionEvent.ACTION_DOWN){
//                myImageView.setImageDrawable(R.drawable.image2);
//                counter++;

//                mclickListener.setText("Count: "+counter);

//            return false;
        }




    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean b) {
    }
}
