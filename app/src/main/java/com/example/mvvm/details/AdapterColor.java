package com.example.mvvm.details;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.mvvm.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class AdapterColor extends RecyclerView.Adapter<AdapterColor.ColorViewHolder> {
    private List<Colors> colorList = new ArrayList<>();
    private onClickColor onClickColor;

    public AdapterColor(AdapterColor.onClickColor onClickColor) {
        this.onClickColor = onClickColor;
    }

    public void setColorList(List<Colors> colorList) {
        this.colorList = colorList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ColorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_color, parent, false);
        return new ColorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ColorViewHolder holder, int position) {
        holder.bindColor(colorList.get(position));
    }

    @Override
    public int getItemCount() {
        return colorList.size();
    }

    public class ColorViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgColor;
        private TextView txtColor;

        public ColorViewHolder(@NonNull View itemView) {
            super(itemView);
            imgColor = itemView.findViewById(R.id.img_color_product);
            txtColor = itemView.findViewById(R.id.txt_color_product);
        }

        public void bindColor(Colors colors) {
            txtColor.setText(colors.getNameColors());
            txtColor.setTextColor(Color.parseColor(colors.getColor()));
            imgColor.setColorFilter(Color.parseColor(colors.getColor()));
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClickColor.onColor(colors.getColor());
                }
            });
        }
    }

    public interface onClickColor {
        void onColor(String color);
    }

}
