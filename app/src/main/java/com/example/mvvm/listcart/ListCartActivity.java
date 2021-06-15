package com.example.mvvm.listcart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mvvm.R;
import com.example.mvvm.auth.SuccessRepose;
import com.example.mvvm.checkout.CheckoutActivity;
import com.example.mvvm.home.CountContainer;
import com.example.mvvm.home.HomeActivity;
import com.example.mvvm.home.OnCartItemChangeCount;
import com.example.mvvm.model.MvvmSingleObserve;
import com.example.mvvm.utils.PriceConverter;

import org.greenrobot.eventbus.EventBus;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import io.supercharge.shimmerlayout.ShimmerLayout;

public class ListCartActivity extends AppCompatActivity implements AadpterListCart.OnlistenerEvent {
    private RecyclerView rcListCart;
    private TextView txtTotalPrice;
    private ProgressBar prObserve;
    ShimmerLayout shimmerText;
    private RelativeLayout rltvBuy;
    private TextView txtChk;
    private Button btnChk;
    private int priceIntent;
    private RelativeLayout rltvHidden;

    private AadpterListCart aadpterListCart;
    private CartViewModel viewModel = new CartViewModel();
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_cart);
        setUpViews();
        observe(true);
        compositeDisposable.add(viewModel.getProgressVisibility().subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            prObserve.setVisibility(View.VISIBLE);
                        } else {
                            prObserve.setVisibility(View.GONE);
                        }
                    }
                }));

    }

    private void setUpViews() {

        rltvHidden = findViewById(R.id.rltv_hidden);
        Button btnShop = findViewById(R.id.btn_go_shop);

        btnChk = findViewById(R.id.btn_intent_chk);
        rcListCart = findViewById(R.id.rc_list_cart);
        txtTotalPrice = findViewById(R.id.txt_total_price);
        prObserve = findViewById(R.id.prObserve);
        rltvBuy = findViewById(R.id.rltv_buy);
        shimmerText = findViewById(R.id.shimmer_text);
        shimmerText.startShimmerAnimation();
        txtChk = findViewById(R.id.txt_show_checkout);
        txtChk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rcListCart.smoothScrollToPosition(aadpterListCart.getItemCount());
            }
        });
        btnChk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListCartActivity.this, CheckoutActivity.class);
                intent.putExtra("price", priceIntent);
                startActivity(intent);
            }
        });
        btnShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListCartActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });


    }

    private void observe(boolean mustReloadData) {
        viewModel.cartItem(mustReloadData).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MvvmSingleObserve<CartItem>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onSuccess(@NonNull CartItem cartItems) {
                        if (cartItems.getCartItems().isEmpty()) {
                            rltvHidden.setVisibility(View.VISIBLE);
                        }else {
                            rltvHidden.setVisibility(View.GONE);
                        }

                        EventBus.getDefault().post(new OnCartItemChangeCount(cartItems.getCount()));
                        CountContainer.updateCount(cartItems.getCount());
                        if (aadpterListCart == null) {

                            aadpterListCart = new AadpterListCart(cartItems, ListCartActivity.this);
                            rcListCart.setLayoutManager(new LinearLayoutManager(ListCartActivity.this, RecyclerView.VERTICAL, false));
                            rcListCart.setAdapter(aadpterListCart);
                        }
                        aadpterListCart.updateNotify(cartItems);
                        txtTotalPrice.setText(PriceConverter.converter(cartItems.getTotalPrice()));
                        priceIntent = cartItems.getTotalPrice();
                        rltvBuy.setVisibility(View.VISIBLE);
                        shimmerText.stopShimmerAnimation();

                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }

    @Override
    public void onRemoveItem(CartItemsItem cartItemsItem) {
        viewModel.remove(cartItemsItem)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MvvmSingleObserve<SuccessRepose>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onSuccess(@NonNull SuccessRepose successRepose) {
                        Toast.makeText(ListCartActivity.this, "حذف شد", Toast.LENGTH_SHORT).show();
                        aadpterListCart.notifyRemoveCartItem(cartItemsItem);
                        observe(false);
                        cartItemsItem.setRemove(false);

                    }
                });
    }

    @Override
    public void onChangeCountProdut(CartItemsItem cartItemsItem, int newCount) {
        shimmerText.startShimmerAnimation();
        rltvBuy.setVisibility(View.GONE);
        viewModel.changeCount(cartItemsItem, newCount).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MvvmSingleObserve<ChangeModelItem>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onSuccess(@NonNull ChangeModelItem changeModelItem) {
                        Toast.makeText(ListCartActivity.this, "آپدیت شد", Toast.LENGTH_SHORT).show();
                        cartItemsItem.setCount(String.valueOf(newCount));
                        aadpterListCart.notifyChangeCartItem(cartItemsItem);
                        //  cartItemsItem.setChangeCount(false);
                        observe(false);

                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        observe(true);
    }
}