package com.android.sunplast;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;

public class CartChildViewHolder extends GroupViewHolder {
    private TextView genreName;
    private ImageView arrow;

    public CartChildViewHolder(View itemView) {
        super(itemView);
        genreName = (TextView) itemView.findViewById(R.id.parent_list_item_crime_title_text_view);
        arrow = (ImageView) itemView.findViewById(R.id.parent_list_item_expand_arrow);
    }


}
