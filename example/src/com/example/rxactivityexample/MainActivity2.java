package com.example.rxactivityexample;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.hendraanggrian.rx.activity.ActivityResult;
import com.hendraanggrian.rx.activity.RxActivity;
import com.hendraanggrian.rx.activity.RxActivityKt;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)s
 */
public class MainActivity2 extends AppCompatActivity {

    private Toolbar toolbar;
    private Button button1;
    private Button button2;
    private NestedScrollView scrollView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        scrollView = findViewById(R.id.scrollView);
        setSupportActionBar(toolbar);

        final Intent intent = new Intent(this, NextActivity.class);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RxActivityKt.startActivityForResultOk(MainActivity2.this, intent)
                        .subscribe(new Observer<Intent>() {
                            @Override
                            public void onSubscribe(@NonNull Disposable d) {
                            }

                            @Override
                            public void onNext(@NonNull Intent intent) {
                                showSnackbar("onNext");
                            }

                            @Override
                            public void onError(@NonNull Throwable e) {
                                showSnackbar("onError:\n" + e.getMessage());
                            }

                            @Override
                            public void onComplete() {
                            }
                        });
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RxActivityKt.startActivityForResultBy(MainActivity2.this, intent)
                        .subscribe(new Observer<ActivityResult>() {
                            @Override
                            public void onSubscribe(@NonNull Disposable d) {
                            }

                            @Override
                            public void onNext(@NonNull ActivityResult result) {
                                showSnackbar("onNext:\n" + result.toString());
                            }

                            @Override
                            public void onError(@NonNull Throwable e) {
                                showSnackbar("onError:\n" + e.getMessage());
                            }

                            @Override
                            public void onComplete() {
                            }
                        });
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        RxActivity.INSTANCE.onActivityResult(requestCode, resultCode, data);
    }

    private void showSnackbar(String text) {
        Snackbar snackbar = Snackbar.make(scrollView, text, Snackbar.LENGTH_INDEFINITE)
                .setAction(android.R.string.ok, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    }
                });
        ((TextView) snackbar.getView().findViewById(android.support.design.R.id.snackbar_text)).setMaxLines(5);
        snackbar.show();
    }
}