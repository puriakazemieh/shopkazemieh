package com.example.mvvm.details;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.mvvm.R;

import java.util.ArrayList;
import java.util.List;

public class AdapterComment extends RecyclerView.Adapter<AdapterComment.CommentViewHolder>{
    private List<CommentProduct>commentProductList = new ArrayList<>();

    public void setCommentProductList(List<CommentProduct> commentProductList) {
        this.commentProductList = commentProductList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment,parent,false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {

        holder.bindComment(commentProductList.get(position));
    }

    @Override
    public int getItemCount() {
        return commentProductList.size();
    }

    public class  CommentViewHolder extends RecyclerView.ViewHolder {
        private TextView txtTitle,txtContent,txtDate,txtEmail;
        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            txtContent = itemView.findViewById(R.id.txt_content_commnet);
            txtDate = itemView.findViewById(R.id.txt_date_comment);
            txtTitle = itemView.findViewById(R.id.txt_title_commnet);
            txtEmail = itemView.findViewById(R.id.txt_email_comment);

        }
        public void bindComment(CommentProduct commentProduct)
        {
            txtEmail.setText(commentProduct.getEmail());
            txtTitle.setText(commentProduct.getTitle());
            txtDate.setText(commentProduct.getDate());
            txtContent.setText(commentProduct.getContent());
        }
    }
}
