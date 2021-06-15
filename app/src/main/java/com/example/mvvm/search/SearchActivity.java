package com.example.mvvm.search;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.mvvm.R;
import com.example.mvvm.home.Products;
import com.example.mvvm.home.adapterhome.AdapterProduct;
import com.example.mvvm.model.MvvmSingleObserve;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SearchActivity extends AppCompatActivity {

    private RecyclerView recyclerViewSearch;
    private ImageView imgSearch, imageViewClear;
    private EditText editTextSearch;
    private AdapterProduct adapterProduct;
    private SearchViewModel searchViewModel = new SearchViewModel();
    private CompositeDisposable disposable = new CompositeDisposable();
    private List<Products> productsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        setUpViews();
        imgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search(editTextSearch.getText().toString());
                imgSearch.setVisibility(View.GONE);
                imageViewClear.setVisibility(View.VISIBLE);
            }
        });
        imageViewClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                imgSearch.setVisibility(View.VISIBLE);
                imageViewClear.setVisibility(View.GONE);
                editTextSearch.setText("");
                productsList.clear();
                if (adapterProduct!=null){
                    adapterProduct.notifyDataSetChanged();
                }
            }
        });
        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (editTextSearch.length() == 0) {

                    imgSearch.setVisibility(View.VISIBLE);
                    imageViewClear.setVisibility(View.GONE);
                    productsList.clear();
                    if (adapterProduct!=null){
                        adapterProduct.notifyDataSetChanged();
                    }
                }
            }
        });
    }

    private void setUpViews() {
        recyclerViewSearch = findViewById(R.id.recy_search);
        imgSearch = findViewById(R.id.image_back);
        imageViewClear = findViewById(R.id.image_clear);
        editTextSearch = findViewById(R.id.edt_search);
    }

    private void search(String search) {
        searchViewModel.search(search).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MvvmSingleObserve<List<Products>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onSuccess(@NonNull List<Products> products) {
                        productsList = products;
                        adapterProduct = new AdapterProduct(productsList);
                        recyclerViewSearch.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
                        recyclerViewSearch.setAdapter(adapterProduct);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        super.onError(e);
                        Toast.makeText(SearchActivity.this, "مقداری یافت نشد", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}