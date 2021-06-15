package com.example.mvvm.orderDetails;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mvvm.R;
import com.example.mvvm.utils.PriceConverter;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterOrderDetils extends RecyclerView.Adapter<AdapterOrderDetils.OrderDetilsViewHolder> {
private List<OrderDetials> orderDetials;

    public void setOrderDetials(List<OrderDetials> orderDetials) {
        this.orderDetials = orderDetials;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public OrderDetilsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_deteilas,parent,false);
        return new OrderDetilsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderDetilsViewHolder holder, int position) {

        holder.bindProduct(orderDetials.get(position));
    }

    @Override
    public int getItemCount() {
        return orderDetials.size();
    }

    public class OrderDetilsViewHolder extends RecyclerView.ViewHolder {
        private TextView txtCounter, txtSeller, txtColor, txtPrice, txtTitle, txtRemove;
        private ImageView imageViewProduct, imgColor;

        public OrderDetilsViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txt_title_cart);
            txtCounter = itemView.findViewById(R.id.txt_counter);
            txtColor = itemView.findViewById(R.id.txt_color);
            txtSeller = itemView.findViewById(R.id.txt_seller);
            txtPrice = itemView.findViewById(R.id.txt_price_new_cart);
            imageViewProduct = itemView.findViewById(R.id.img_product);
            imgColor = itemView.findViewById(R.id.img_color);
            txtRemove = itemView.findViewById(R.id.txt_cart_remove);
        }

        public void bindProduct(OrderDetials orderDetials) {
            txtColor.setTextColor(Color.parseColor(orderDetials.getColor()));
            imgColor.setColorFilter(Color.parseColor(orderDetials.getColor()));
            txtCounter.setText(String.valueOf(orderDetials.getCount()));
            txtTitle.setText(orderDetials.getTitle());
            txtColor.setText(orderDetials.getNameColors());
            txtPrice.setText(PriceConverter.converter(Integer.valueOf(orderDetials.getNewPrice())));
            txtSeller.setText(orderDetials.getSeller());
            Picasso.get().load(orderDetials.getImage()).into(imageViewProduct);



        }
    }
}
