package com.example.mvvm.orderDetails;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.mvvm.R;
import com.example.mvvm.model.MvvmSingleObserve;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class OrderDetialsActivity extends AppCompatActivity {

    private RecyclerView recyclerViewOrderDetilas;
    private AdapterOrderDetils adapterOrderDetils;
    private OrderDetilasViewModel orderDetilasViewModel = new OrderDetilasViewModel();
    private CompositeDisposable disposable = new CompositeDisposable();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detials);
        recyclerViewOrderDetilas = findViewById(R.id.rc_order_detilas);
        adapterOrderDetils = new AdapterOrderDetils();
        orderDetilasViewModel.getOrderDetails(getIntent().getStringExtra("refId"))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MvvmSingleObserve<List<OrderDetials>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onSuccess(List<OrderDetials> orderDetials) {

                        recyclerViewOrderDetilas.setLayoutManager(new LinearLayoutManager(OrderDetialsActivity.this, RecyclerView.VERTICAL, false));
                        recyclerViewOrderDetilas.setAdapter(adapterOrderDetils);
                        adapterOrderDetils.setOrderDetials(orderDetials);
                    }
                });
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.clear();
    }
}