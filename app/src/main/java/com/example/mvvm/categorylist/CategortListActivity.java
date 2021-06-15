package com.example.mvvm.categorylist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.mvvm.R;
import com.example.mvvm.home.Products;
import com.example.mvvm.home.adapterhome.AdapterProduct;
import com.example.mvvm.model.MvvmSingleObserve;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class CategortListActivity extends AppCompatActivity {
    private RecyclerView rcCategoryList;
    private AdapterProduct adapterProduct;
    private CategoryViewModel categoryViewModel = new CategoryViewModel();
    private int idCategory;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categort_list);
        rcCategoryList = findViewById(R.id.rc_product_list_category);
        rcCategoryList.setLayoutManager(new GridLayoutManager(this, 2));
        adapterProduct = new AdapterProduct();
        rcCategoryList.setAdapter(adapterProduct);
        idCategory = getIntent().getIntExtra("idCategory", -1);
        Toast.makeText(this, ""+idCategory, Toast.LENGTH_SHORT).show();
        ImageView imageView = findViewById(R.id.img_banner_list);
        observe();
    }

    public void observe() {
        categoryViewModel.category(idCategory).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MvvmSingleObserve<List<Products>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
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
        compositeDisposable.clear();
    }
}