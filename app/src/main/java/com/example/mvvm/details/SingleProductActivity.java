package com.example.mvvm.details;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mvvm.R;
import com.example.mvvm.addcomment.AddDialogCommnet;
import com.example.mvvm.auth.AuthenticationActivity;
import com.example.mvvm.auth.SuccessRepose;
import com.example.mvvm.auth.Token;
import com.example.mvvm.auth.UnAuthentication;
import com.example.mvvm.auth.UserInfoManager;
import com.example.mvvm.home.CountContainer;
import com.example.mvvm.home.OnCartItemChangeCount;
import com.example.mvvm.home.Products;
import com.example.mvvm.model.MvvmSingleObserve;
import com.example.mvvm.model.TokenContainer;
import com.example.mvvm.utils.PriceConverter;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SingleProductActivity extends AppCompatActivity implements AdapterColor.onClickColor {
    private TextView txtOldPrice, txtNewPrice, txtTitle, txtCountComment;
    private ImageView imgProduct;
    private RecyclerView rcColor, rcComent;
    private ImageView imgBack;
    private Button btnAddToCart, btnAddComment;
    private RelativeLayout layoutEmpty;
    private int idOroduct;
    private DetailsViewModel detailsViewModel = new DetailsViewModel();
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private AdapterColor adapterColor;
    private AdapterComment adapterComment;
    private RelativeLayout LayoutEmpty;
    private String colorProduct="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_product);
        idOroduct = getIntent().getIntExtra("id", 0);
        setUpViews();
        setData();
        obServeData();
        EventBus.getDefault().register(this);
    }

    private void setUpViews() {
        layoutEmpty = findViewById(R.id.rltv_empty_comment);
        rcColor = findViewById(R.id.rc_color_product);
        rcComent = findViewById(R.id.rc_commnet_single_product);
        txtNewPrice = findViewById(R.id.txt_new_price_single_product);
        txtOldPrice = findViewById(R.id.txt_old_price_single_product);
        txtTitle = findViewById(R.id.txt_title_single_product);
        txtCountComment = findViewById(R.id.txt_count_comment);
        btnAddToCart = findViewById(R.id.btn_add_product);
        btnAddComment = findViewById(R.id.btn_add_commnet);
        imgBack = findViewById(R.id.img_back);
        imgProduct = findViewById(R.id.img_single_product);
        layoutEmpty = findViewById(R.id.rltv_empty_comment);


        btnAddComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TokenContainer.getToken() != null) {
                    AddDialogCommnet addDialogCommnet = AddDialogCommnet.newInstance(idOroduct);
                    addDialogCommnet.show(getSupportFragmentManager(), null);
                } else {
                    Toast.makeText(SingleProductActivity.this, "لطفا وارد شوید یا ثبت نام کنید", Toast.LENGTH_SHORT).show();
                    EventBus.getDefault().post(new UnAuthentication());
                }
            }
        });
        btnAddToCart.setOnClickListener(v -> {
            if (TokenContainer.getToken() != null) {
                if (colorProduct.equals("")){
                    Toast.makeText(SingleProductActivity.this, "لطفا رنگ را انتخاب نمایید", Toast.LENGTH_SHORT).show();

                }else {
                    detailsViewModel.addToCar(idOroduct, colorProduct)
                            .subscribeOn(Schedulers.newThread())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new MvvmSingleObserve<SuccessRepose>() {
                                @Override
                                public void onSubscribe(@NonNull Disposable d) {
                                    compositeDisposable.add(d);
                                }

                                @Override
                                public void onSuccess(@NonNull SuccessRepose successRepose) {
                                    Toast.makeText(SingleProductActivity.this, successRepose.getMessage(), Toast.LENGTH_SHORT).show();
                                    int coun= CountContainer.getCount();

                                    coun++;
                                    CountContainer.updateCount(coun);
                                    EventBus.getDefault().post(new OnCartItemChangeCount(coun));
                                }
                            });
                }

            } else {
                EventBus.getDefault().post(new UnAuthentication());
                Toast.makeText(SingleProductActivity.this, "لطفا وارد شوید یا ثبت نام کنید", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setData() {
        rcColor.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        adapterColor = new AdapterColor(this::onColor);
        rcColor.setAdapter(adapterColor);

        rcComent.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        adapterComment = new AdapterComment();
        rcComent.setAdapter(adapterComment);

    }

    private void obServeData() {
        detailsViewModel.productsSingle(idOroduct).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MvvmSingleObserve<Products>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onSuccess(@NonNull Products products) {

                        if (products.getCommentconut() == 0) {
                            txtCountComment.setText("شما اولین نفر باشید که نظر می دهید");
                        } else {

                            txtCountComment.setText("تعداد نظرات: " + products.getCommentconut());
                        }
                        txtTitle.setText(products.getTitle() + "");
                        txtNewPrice.setText(PriceConverter.converter(products.getNewPrice()));
                        txtOldPrice.setText(PriceConverter.converter(products.getPreviousPrice()));
                        txtOldPrice.setPaintFlags(txtOldPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                        Picasso.get().load(products.getImage()).into(imgProduct);
                    }
                });

        detailsViewModel.colors(idOroduct).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MvvmSingleObserve<List<Colors>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onSuccess(@NonNull List<Colors> colors) {
                        adapterColor.setColorList(colors);
                    }
                });
        detailsViewModel.commentProduct(idOroduct).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MvvmSingleObserve<List<CommentProduct>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);

                    }

                    @Override
                    public void onSuccess(@NonNull List<CommentProduct> commentProducts) {
                        if (commentProducts.isEmpty()) {
                            layoutEmpty.setVisibility(View.VISIBLE);
                        } else {

                            adapterComment.setCommentProductList(commentProducts);
                        }
                    }
                });
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void HandlerAuthenticate(UnAuthentication unAuthentication) {
        startActivity(new Intent(this, AuthenticationActivity.class));
    }

    @Override
    protected void onStop() {
        super.onStop();
        compositeDisposable.clear();

    }

    @Override
    public void onColor(String color) {
        colorProduct=color;
    }
}