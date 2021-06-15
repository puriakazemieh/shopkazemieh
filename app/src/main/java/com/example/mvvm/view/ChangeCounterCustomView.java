package com.example.mvvm.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.example.mvvm.R;

public class ChangeCounterCustomView extends CardView {
    private TextView txtminus, txtadd, txtcont;
    private View rootview;
    private int count;
    private OnChangeCount onChangeCount;

    public void setOnChangeCount(OnChangeCount onChangeCount) {
        this.onChangeCount = onChangeCount;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
        txtcont.setText(String.valueOf(count));
    }

    public ChangeCounterCustomView(Context context) {
        super(context);
        setUpViews();
    }

    public ChangeCounterCustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setUpViews();
    }

    public ChangeCounterCustomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setUpViews();
    }

    public void setUpViews() {
        rootview = LayoutInflater.from(getContext())
                .inflate(R.layout.item_view_counter_product,
                        this, true);
        txtadd = rootview.findViewById(R.id.txt_count_add);
        txtminus = rootview.findViewById(R.id.txt_count_minus);
        txtcont = findViewById(R.id.txt_count_change);
        txtadd.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                count += 1;
                onUpdateChangeCount();
            }
        });
        txtminus.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                count -= 1;
                onUpdateChangeCount();
            }
        });
    }

    private void onUpdateChangeCount() {
        if (count > 0) {
            txtcont.setText(String.valueOf(count));
            if (onChangeCount != null) {
                onChangeCount.onChange(count);
            }
        } else {
            count = 1;
        }
    }

    public interface OnChangeCount {
        void onChange(int count);
    }

}
