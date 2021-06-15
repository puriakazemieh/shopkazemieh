package com.example.mvvm.home.adapterhome;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mvvm.R;
import com.example.mvvm.categorylist.CategortListActivity;
import com.example.mvvm.details.SingleProductActivity;
import com.example.mvvm.home.Banners;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AdapterBanner extends RecyclerView.Adapter<AdapterBanner.BannerViewHolder> {
    private List<Banners> bannersList = new ArrayList<>();
    private onClickUrl onClickUrl;

    public AdapterBanner(AdapterBanner.onClickUrl onClickUrl) {
        this.onClickUrl = onClickUrl;
    }

    public void setBannersList(List<Banners> bannersList) {
        this.bannersList = bannersList;
        notifyDataSetChanged();
    }

    @NonNull
    @io.reactivex.annotations.NonNull
    @Override
    public BannerViewHolder onCreateViewHolder(@NonNull @io.reactivex.annotations.NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_banner, parent, false);
        return new BannerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @io.reactivex.annotations.NonNull BannerViewHolder holder, int position) {
        holder.bindBanner(bannersList.get(position));
    }

    @Override
    public int getItemCount() {
        return bannersList.size();
    }

    public class BannerViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageViewBanner;

        public BannerViewHolder(@NonNull @io.reactivex.annotations.NonNull View itemView) {
            super(itemView);
            imageViewBanner = itemView.findViewById(R.id.img_banner_home);
        }

        public void bindBanner(Banners banners) {
            Picasso.get().load(banners.getImage()).placeholder(R.drawable.placeholder).into(imageViewBanner);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (banners.getType()) {
                        case 0: {
                           // Toast.makeText(itemView.getContext(), "0"+banners.getValue(), Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(v.getContext(), SingleProductActivity.class);
                            intent.putExtra("id",Integer.parseInt(banners.getValue()));
                            v.getContext().startActivity(intent);
                            break;
                        }
                        case 1: {
                            Intent intent=new Intent(v.getContext(), CategortListActivity.class);
                            intent.putExtra("idCategory",Integer.parseInt(banners.getValue()));
                            v.getContext().startActivity(intent);
                            break;
                        }
                        case 2: {
                           // Toast.makeText(itemView.getContext(), "2"+banners.getValue(), Toast.LENGTH_SHORT).show();
                            onClickUrl.onClickValue(banners.getValue());
                            break;
                        }
                    }
                }
            });

        }
    }
    public interface onClickUrl{
        void onClickValue(String url);
    }
}

