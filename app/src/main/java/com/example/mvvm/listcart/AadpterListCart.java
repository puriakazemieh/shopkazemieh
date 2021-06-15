package com.example.mvvm.listcart;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.dynamicanimation.animation.SpringAnimation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mvvm.R;
import com.example.mvvm.utils.PriceConverter;
import com.example.mvvm.view.ChangeCounterCustomView;
import com.squareup.picasso.Picasso;

public class AadpterListCart extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public final int TYPE_CART_ITEM = 0;
    public final int TYPE_PURCHES_DETAILS = 1;
    private CartItem cartItem;
    private CartItemsItem cartItemsItem;
    private OnlistenerEvent onlistenerEvent;

    public AadpterListCart(CartItem cartItem, OnlistenerEvent onlistenerEvent) {
        this.cartItem = cartItem;
        this.onlistenerEvent = onlistenerEvent;
    }
    public void updateNotify(CartItem item){
        this.cartItem=item;
        notifyDataSetChanged();
    }


    @NonNull
    @io.reactivex.annotations.NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull @io.reactivex.annotations.NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_CART_ITEM) {
            return new CartViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false));
        } else {
            return new PurchesViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_buy, parent, false));

        }
    }

    @Override
    public void onBindViewHolder(@NonNull @io.reactivex.annotations.NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof CartViewHolder) {
            ((CartViewHolder) holder).bindCartItem(cartItem.getCartItems().get(position));
        } else if (holder instanceof PurchesViewHolder) {
            ((PurchesViewHolder) holder).bindPurrches(cartItem.getTotalPrice(), cartItem.getPayablePrice());
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position < cartItem.getCartItems().size()) {
            return TYPE_CART_ITEM;
        } else {
            return TYPE_PURCHES_DETAILS;
        }
    }

    @Override
    public int getItemCount() {
        return cartItem.getCartItems().size() + 1;
    }

    public class PurchesViewHolder extends RecyclerView.ViewHolder {
        private TextView txtDiscount, txtTotalPrice;

        public PurchesViewHolder(@NonNull @io.reactivex.annotations.NonNull View itemView) {
            super(itemView);
            txtDiscount = itemView.findViewById(R.id.txt_total_discount);
            txtTotalPrice = itemView.findViewById(R.id.txt_total_price);
        }

        public void bindPurrches(int price, int discount) {
            txtTotalPrice.setText(PriceConverter.converter(price));
            txtDiscount.setText(PriceConverter.converter(discount));
        }
    }

    public class CartViewHolder extends RecyclerView.ViewHolder {

        private TextView txtCounter, txtSeller, txtColor, txtDiscount, txtPrice, txtTitle, txtRemove;
        private ImageView imageViewProduct, imgColor;
        private ProgressBar prRemove;
        private ChangeCounterCustomView changeCounterCustomView;

        public CartViewHolder(@NonNull @io.reactivex.annotations.NonNull View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txt_title_cart);
            txtCounter = itemView.findViewById(R.id.txt_counter);
            txtColor = itemView.findViewById(R.id.txt_color);
            txtSeller = itemView.findViewById(R.id.txt_seller);
            txtPrice = itemView.findViewById(R.id.txt_price_new_cart);
            txtDiscount = itemView.findViewById(R.id.txt_discount);
            imageViewProduct = itemView.findViewById(R.id.img_product);
            imgColor = itemView.findViewById(R.id.img_color);
            txtRemove = itemView.findViewById(R.id.txt_cart_remove);
            prRemove = itemView.findViewById(R.id.prRemove);
            changeCounterCustomView = itemView.findViewById(R.id.coustomview);
        }

        public void bindCartItem(CartItemsItem item) {
            txtTitle.setText(item.getProduct().getTitle());
            txtColor.setTextColor(Color.parseColor(item.getColor()));
            imgColor.setColorFilter(Color.parseColor(item.getColor()));
            changeCounterCustomView.setCount(Integer.parseInt(item.getCount()));
            txtCounter.setText("موجود در انبار");
            txtTitle.setText(item.getProduct().getTitle());
            txtColor.setText(item.getNameColors());
            txtDiscount.setText(PriceConverter.converter(item.getProduct().getDiscount()) + " تخفیف شما");
            txtPrice.setText(PriceConverter.converter(item.getProduct().getNewPrice()));
            txtSeller.setText(item.getProduct().getSeller());
            Picasso.get().load(item.getProduct().getImage()).into(imageViewProduct);
            if (item.getRemove()) {
                prRemove.setVisibility(View.VISIBLE);
                txtRemove.setTextColor(ContextCompat.getColor(itemView.getContext(),R.color.gray_300));
                txtRemove.setBackgroundColor(ContextCompat.getColor(itemView.getContext(),R.color.gray_300));
            } else {
                prRemove.setVisibility(View.GONE);
                txtRemove.setTextColor(ContextCompat.getColor(itemView.getContext(),R.color.white));
                txtRemove.setBackgroundColor(ContextCompat.getColor(itemView.getContext(),R.color.template));
            }
            txtRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    item.setRemove(true);
                    onlistenerEvent.onRemoveItem(item);
                    notifyItemChanged(getAdapterPosition());
                }
            });
            changeCounterCustomView.setOnChangeCount(new ChangeCounterCustomView.OnChangeCount() {
                @Override
                public void onChange(int count) {
                    onlistenerEvent.onChangeCountProdut(item, count);
                    item.setChangeCount(true);

                }
            });

        }
    }

    public interface OnlistenerEvent {
        void onRemoveItem(CartItemsItem cartItemsItem);

        void onChangeCountProdut(CartItemsItem cartItemsItem, int count);
    }


    public void notifyRemoveCartItem(CartItemsItem cartItemsItem) {
        int index = cartItem.getCartItems().indexOf(cartItemsItem);
        cartItem.getCartItems().remove(index);
        notifyItemRemoved(index);
    }

    public void notifyChangeCartItem(CartItemsItem cartItemsItem) {
        int index = cartItem.getCartItems().indexOf(cartItemsItem);
        notifyItemChanged(index);
    }
}
