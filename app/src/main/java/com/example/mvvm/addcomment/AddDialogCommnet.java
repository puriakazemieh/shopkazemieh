package com.example.mvvm.addcomment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.mvvm.R;
import com.example.mvvm.auth.AuthenticationActivity;
import com.example.mvvm.auth.SuccessRepose;
import com.example.mvvm.auth.UnAuthentication;
import com.example.mvvm.exeption.ExceptionMessageFactory;
import com.example.mvvm.model.MvvmCompletableObserver;
import com.example.mvvm.model.MvvmSingleObserve;
import com.example.mvvm.utils.App;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class AddDialogCommnet extends DialogFragment {
    private EditText edtTitle, edtContent;
    private Button btnAddCommnet;
    private DialogViewModel viewModel;
    private int idProduct;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        idProduct = getArguments().getInt("product_id");
        if (idProduct == 0) {
            dismiss();
        }
        viewModel = new DialogViewModel();
    }

    @NonNull
    @io.reactivex.annotations.NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.MyAlertTheme);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_add_commnet, null);
        edtContent = view.findViewById(R.id.edt_content_add_commnet);
        edtTitle = view.findViewById(R.id.edt_title_add_commnet);
        btnAddCommnet = view.findViewById(R.id.btn_add_commnet_product);
        btnAddCommnet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.addComment(edtTitle.getText().toString(), edtContent.getText().toString(), idProduct)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new MvvmSingleObserve<SuccessRepose>() {
                            @Override
                            public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {
                                compositeDisposable.add(d);
                            }

                            @Override
                            public void onSuccess(@io.reactivex.annotations.NonNull SuccessRepose successRepose) {
                                Toast.makeText(App.getContext(), "دیدگاه شما ثبت شد بعد از تایید نمایش داده می شود", Toast.LENGTH_SHORT).show();
                                dismiss();
                            }


                        });

            }
        });

        builder.setView(view);
        return builder.create();
    }

    public static AddDialogCommnet newInstance(int idProduct) {
        Bundle bundle = new Bundle();
        bundle.putInt("product_id", idProduct);
        AddDialogCommnet addDialogCommnet = new AddDialogCommnet();
        addDialogCommnet.setArguments(bundle);
        return addDialogCommnet;
    }


}
