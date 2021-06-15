package com.example.mvvm.listproduct;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.mvvm.R;
import com.example.mvvm.home.Products;
import com.example.mvvm.home.adapterhome.AdapterProduct;
import com.example.mvvm.model.MvvmSingleObserve;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ShowListActivity extends AppCompatActivity {
    private RecyclerView rcSort, rcListProduct;
    public static final String EXTRA_KEY_SORT = "sort";
    private int sortType;
    private SortListViewModel sortListViewModel = new SortListViewModel();
    private AdapterSort adapterSort;
    private AdapterProduct adapterProduct;
    private CompositeDisposable disposable = new CompositeDisposable();
    private ImageView imgBack;
    private TextView txtActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_list);
        sortType = getIntent().getIntExtra(EXTRA_KEY_SORT, Products.SORT_LATEST);
        setUpViews();
        setData();
        observeData(sortType);
    }

    private void setUpViews() {
        rcListProduct = findViewById(R.id.rc_product_list);
        rcSort = findViewById(R.id.rc_chipse_sort);
        imgBack = findViewById(R.id.img_back);
        txtActivity = findViewById(R.id.txt_title_activity);
        imgBack.setOnClickListener(view -> finish());
        txtActivity.setText("مشاهده محصولات");
    }

    private void setData() {
        //setDataProduct
        rcListProduct.setLayoutManager(new GridLayoutManager(this, 2));
        adapterProduct = new AdapterProduct();
        rcListProduct.setAdapter(adapterProduct);
        //setDataSort
        rcSort.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        adapterSort = new AdapterSort(sortType, this, new AdapterSort.OnClickSortType() {
            @Override
            public void onClickSort(int sortType) {
                ShowListActivity.this.sortType = sortType;
                observeData(sortType);
                Log.e("onClickSort   ","click");
            }
        });
        rcSort.setAdapter(adapterSort);
    }

    private void observeData(int sort) {

        sortListViewModel.products(sort).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MvvmSingleObserve<List<Products>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onSuccess(@NonNull List<Products> products) {
                        adapterProduct.setProductsList(products);

                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.clear();
    }
}