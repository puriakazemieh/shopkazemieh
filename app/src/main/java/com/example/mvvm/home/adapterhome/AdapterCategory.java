package com.example.mvvm.home.adapterhome;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mvvm.R;
import com.example.mvvm.home.Categories;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AdapterCategory extends RecyclerView.Adapter<AdapterCategory.CategoryViewHolder> {
    List<Categories> categoriesList = new ArrayList<>();

    public void setCategoriesList(List<Categories> categoriesList) {
        this.categoriesList = categoriesList;
        notifyDataSetChanged();
    }

    @NonNull
    @io.reactivex.annotations.NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull @io.reactivex.annotations.NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @io.reactivex.annotations.NonNull CategoryViewHolder holder, int position) {
        holder.bindCategory(categoriesList.get(position));
    }

    @Override
    public int getItemCount() {
        return categoriesList.size();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageViewCategories;
        private TextView txtTitle;

        public CategoryViewHolder(@NonNull @io.reactivex.annotations.NonNull View itemView) {
            super(itemView);

            imageViewCategories = itemView.findViewById(R.id.img_category_home);
            txtTitle = itemView.findViewById(R.id.txt_title_category_home);
        }

        private void bindCategory(Categories categories) {
            txtTitle.setText(categories.getTitle());
            Picasso.get().load(categories.getImage()).placeholder(R.drawable.placeholder).into(imageViewCategories);
        }
    }
}
