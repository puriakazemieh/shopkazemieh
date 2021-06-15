package com.example.mvvm.listproduct;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mvvm.R;


public class AdapterSort extends RecyclerView.Adapter<AdapterSort.SortViewHolder> {
    private String[] sortType = new String[]{
            "جدید ترین ها",
            "پربازدیدترین ها",
            "پرفروش ترین ها",
            "ارزان ترین ها"
    };
    private int selectedSortType;
    private Context context;
    private OnClickSortType onClickSortType;

    public AdapterSort(int selectedSortType, Context context, OnClickSortType onClickSortType) {
        this.selectedSortType = selectedSortType;
        this.context = context;
        this.onClickSortType = onClickSortType;
    }

    @NonNull
    @Override
    public SortViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_sort, parent, false);
        return new SortViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SortViewHolder holder, int position) {
        holder.textViewSort.setText(sortType[position]);
        if (position == selectedSortType) {
            holder.textViewSort.setBackground(ContextCompat.getDrawable(context, R.drawable.shape_selected_sort));
            holder.textViewSort.setTextColor(ContextCompat.getColor(context, R.color.white));
        } else {

            holder.textViewSort.setBackground(ContextCompat.getDrawable(context, R.drawable.shape_unselected_sort));
            holder.textViewSort.setTextColor(ContextCompat.getColor(context, R.color.gray_900));
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.getAdapterPosition() != selectedSortType) {
//کدوم ایتم کلیک شده و عدد رو بگیر و در ریسایکلرویو دوم اعمال کن
                    onClickSortType.onClickSort(holder.getAdapterPosition());
                    notifyItemChanged(selectedSortType);
                    // توی لیست خودت اپدیت کن و رنگ متن رو تغییر بده
                    selectedSortType = holder.getAdapterPosition();
                    notifyItemChanged(selectedSortType);
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return sortType.length;
    }

    public class SortViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewSort;

        public SortViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewSort = itemView.findViewById(R.id.txt_sort_chipse);
        }
    }

    public interface OnClickSortType {
        void onClickSort(int sortType);
    }

}