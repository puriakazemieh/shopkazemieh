package com.example.mvvm.home.adapterhome;

import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mvvm.R;
import com.example.mvvm.details.SingleProductActivity;
import com.example.mvvm.home.Products;
import com.example.mvvm.utils.PriceConverter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AdapterProduct extends RecyclerView.Adapter<AdapterProduct.ProductViewHolder> {
    private List<Products> productsList = new ArrayList<>();

    public void setProductsList(List<Products> productsList) {
        this.productsList = productsList;
        notifyDataSetChanged();
    }
    public AdapterProduct() {
    }
    public AdapterProduct(List<Products> productsList) {
        this.productsList = productsList;
    }


    @NonNull
    @io.reactivex.annotations.NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull @io.reactivex.annotations.NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @io.reactivex.annotations.NonNull ProductViewHolder holder, int position) {
        holder.bindProduct(productsList.get(position));
    }

    @Override
    public int getItemCount() {
        return productsList.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {
        private TextView txtTitleProduct, txtOldPrice, txtNewPrice;
        private ImageView imageViewProduct;

        public ProductViewHolder(@NonNull @io.reactivex.annotations.NonNull View itemView) {
            super(itemView);
            txtNewPrice = itemView.findViewById(R.id.txt_price_new_product);
            txtOldPrice = itemView.findViewById(R.id.txt_price_old_product);
            txtTitleProduct = itemView.findViewById(R.id.txt_title_product);
            imageViewProduct = itemView.findViewById(R.id.img_product);
        }

        public void bindProduct(Products products) {

            txtTitleProduct.setText(products.getTitle() + "");
            txtOldPrice.setText(PriceConverter.converter(products.getPreviousPrice()));
            txtNewPrice.setText(PriceConverter.converter(products.getNewPrice()));
            txtOldPrice.setPaintFlags(txtOldPrice.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
            Picasso.get().load(products.getImage()).placeholder(R.drawable.placeholder).into(imageViewProduct);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), SingleProductActivity.class);
                    intent.putExtra("id", products.getId());
                    v.getContext().startActivity(intent);
                }
            });

        }
    }
}
