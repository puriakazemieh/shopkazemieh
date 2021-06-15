package com.example.mvvm.order;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mvvm.R;
import com.example.mvvm.orderDetails.OrderDetialsActivity;

import java.util.List;

public class AdapterOrder extends RecyclerView.Adapter<AdapterOrder.OrderViewHolder> {

    private List<OrderHistory> orderHistories;

    public void setOrderHistories(List<OrderHistory> orderHistories) {
        this.orderHistories = orderHistories;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order,parent,false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {

        holder.bindOrder(orderHistories.get(position));
    }

    @Override
    public int getItemCount() {
        return orderHistories.size();
    }

    public class OrderViewHolder extends RecyclerView.ViewHolder {
        private TextView txtDate,txtrefId;
        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            txtDate = itemView.findViewById(R.id.txt_date_order);
            txtrefId = itemView.findViewById(R.id.txt_refid);
        }
        public void bindOrder(OrderHistory history)
        {
            txtrefId.setText(history.getRefId());
            txtDate.setText(history.getDatetime());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), OrderDetialsActivity.class);
                    intent.putExtra("refId",history.getRefId());
                    view.getContext().startActivity(intent);
                }
            });
        }
    }
}
