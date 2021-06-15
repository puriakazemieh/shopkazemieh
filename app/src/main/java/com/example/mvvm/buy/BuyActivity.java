package com.example.mvvm.buy;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.JsonReader;
import android.util.JsonToken;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.example.mvvm.R;
import com.example.mvvm.auth.SuccessRepose;
import com.example.mvvm.model.MvvmSingleObserve;

import java.io.StringReader;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class BuyActivity extends AppCompatActivity {
    private WebView webView;
    public static final String NAME = "name";
    public static final String FAMILY = "family";
    public static final String ADDRESS = "address";
    public static final String PHONE = "phone";
    public static final String POST = "post";
    public static final String PRICE = "price";
    private String url = "http://android.novindevelopers.ir/zarinpalrequest.php";

    private String name, family, address, post, price, phone;
    private Intent intent;
    private buyViewModel buyViewModel = new buyViewModel();
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @SuppressLint("JavascriptInterface")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy);
        webView = findViewById(R.id.webView);
        setUpData();
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.addJavascriptInterface(new Js(this), "HTMLViewr");
        webView.setWebViewClient(new WebViewClient() {
                                     //   final ProgressDialog pd = ProgressDialog.show(Activity_WebGat.this, "", "Please wait, your transaction is being processed...", true);

                                     @Override
                                     public void onPageStarted(WebView view, String url, Bitmap favicon) {
                                         //   pd.show();
                                     }

                                     @Override
                                     public void onPageFinished(WebView view, String url) {
                                         if (Build.VERSION.SDK_INT >= 19) {
                                             webView.evaluateJavascript("document.getElementsByTagName('html')[0].innerHTML", new ValueCallback<String>() {
                                                 @Override
                                                 public void onReceiveValue(String value) {
                                                     JsonReader reader = new JsonReader(new StringReader(value));
                                                     reader.setLenient(true);
                                                     try {
                                                         if (reader.peek() != JsonToken.NULL) {
                                                             if (reader.peek() == JsonToken.STRING) {
                                                                 String msg = reader.nextString();
                                                                 Js js = new Js(getApplicationContext());
                                                                 js.getHtml(msg);

                                                             }
                                                         }

                                                     } catch (Exception e) {
                                                         e.printStackTrace();
                                                     } finally {
                                                         try {
                                                             reader.close();
                                                         } catch (Exception e) {
                                                             e.printStackTrace();
                                                         }
                                                     }
                                                 }
                                             });
                                         } else {
                                             webView.loadUrl("javascript:window.HTMLViewr.get(document.getElementsByTagName('html')[0].innerHTML);");
                                         }
                                         //    pd.dismiss();
                                     }

                                     @Override
                                     public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                                         super.onReceivedSslError(view, handler, error);
                                         handler.proceed();
                                     }
                                 }
        );

        webView.loadUrl(url + "?Amount=" + price + "&Email=" + name + " " + family + "&Description=" + "در انتظار پرداخت");
        Toast.makeText(this, "لطفا کمی صبر کنید...", Toast.LENGTH_LONG).show();

    }

    private void setUpData() {
        intent = getIntent();
        name = intent.getStringExtra(BuyActivity.NAME);
        family = intent.getStringExtra(BuyActivity.FAMILY);
        address = intent.getStringExtra(BuyActivity.ADDRESS);
        post = intent.getStringExtra(BuyActivity.POST);
        phone = intent.getStringExtra(BuyActivity.PHONE);
        price = String.valueOf(intent.getIntExtra(BuyActivity.PRICE, 0));
        Toast.makeText(this, price, Toast.LENGTH_SHORT).show();
    }

    class Js {
        Context context;

        public Js(Context context) {
            this.context = context;
        }

        public void getHtml(String html) {
            final String s = Html.fromHtml(html).toString();
            final String[] result = s.split(",");
            if (result[0].equals("OK")) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        buyViewModel.order(name, family, address, phone, post, price, result[1])
                                .subscribeOn(Schedulers.newThread())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new MvvmSingleObserve<SuccessRepose>() {
                                    @Override
                                    public void onSubscribe(Disposable d) {
                                        compositeDisposable.add(d);
                                    }

                                    @Override
                                    public void onSuccess(SuccessRepose susscessReponce) {
                                        Toast.makeText(context, "پرداخت انجام شد" + " " + "شماره تراکنش: " + result[1], Toast.LENGTH_SHORT).show();

                                    }
                                });
//                        Intent intent = new Intent(Activity_WebGat.this,Activity_FinalBuy.class);
//                        intent.putExtra(put.allprice,totalprice);
//                        intent.putExtra(put.refid,result[1]);
//                        startActivity(intent);
                        finish();

                    }
                });

            } else if (result[0].equals("ERROR")) {
                Toast.makeText(context, "پرداخت با شکست مواجه شد" + " " + "شناسه خطا: " + result[1], Toast.LENGTH_SHORT).show();
            } else if (result[0].equals("CANSEL")) {
                Toast.makeText(context, "شما  تراکنش را لغو کرده اید", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }
}