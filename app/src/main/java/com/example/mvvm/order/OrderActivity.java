package com.example.mvvm.order;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.mvvm.R;
import com.example.mvvm.model.MvvmSingleObserve;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class OrderActivity extends AppCompatActivity {

    private RecyclerView recyclerViewOrder;
    private AdapterOrder adapterOrder;
    private OrderViewModel orderViewModel = new OrderViewModel();
    private CompositeDisposable disposable = new CompositeDisposable();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        adapterOrder = new AdapterOrder();
        recyclerViewOrder = findViewById(R.id.rc_order_history);

        orderViewModel.getOrderHistory().subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MvvmSingleObserve<List<OrderHistory>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onSuccess(List<OrderHistory> orderHistories) {
                        recyclerViewOrder.setLayoutManager(new LinearLayoutManager(OrderActivity.this,RecyclerView.VERTICAL,false));
                        recyclerViewOrder.setAdapter(adapterOrder);
                        adapterOrder.setOrderHistories(orderHistories);
                    }
                });
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.clear();
    }
}