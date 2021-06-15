package com.example.mvvm.checkout;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.mvvm.R;
import com.example.mvvm.buy.BuyActivity;
import com.example.mvvm.utils.PriceConverter;

public class CheckoutActivity extends AppCompatActivity {

    private EditText edtName, edtFamily, edtAddress, edtPhone, edtPostCode;
    private Button btnOrder;
    private TextView txtPrice;
    private int price;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        setUpViews();
    }
    private void setUpViews() {
        price = getIntent().getIntExtra("price",0);
        txtPrice = findViewById(R.id.txt_price_chk);
        txtPrice.setText(PriceConverter.converter(price));
        edtAddress = findViewById(R.id.edt_address_chk);
        edtFamily = findViewById(R.id.edt_family_chk);
        edtName = findViewById(R.id.edt_name_chk);
        edtPhone = findViewById(R.id.edt_phone_chk);
        edtPostCode = findViewById(R.id.edt_postcode_chk);
        btnOrder = findViewById(R.id.btn_webgate);
        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CheckoutActivity.this, BuyActivity.class);
                intent.putExtra(BuyActivity.NAME,edtName.getText().toString().trim());
                intent.putExtra(BuyActivity.FAMILY,edtFamily.getText().toString().trim());
                intent.putExtra(BuyActivity.ADDRESS,edtAddress.getText().toString().trim());
                intent.putExtra(BuyActivity.PHONE,edtPhone.getText().toString().trim());
                intent.putExtra(BuyActivity.POST,edtPostCode.getText().toString().trim());
                intent.putExtra(BuyActivity.PRICE,price);
                startActivity(intent);
                finish();
            }
        });
    }
}