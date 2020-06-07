package com.bagas.githubusers.base;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.bagas.githubusers.view.fragment.LoadingDialogFragment;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import dagger.android.support.DaggerAppCompatActivity;

public class BaseActivity extends DaggerAppCompatActivity {

    LoadingDialogFragment loadingDialog;
    View root;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadingDialog = LoadingDialogFragment.newInstance();
        root = findViewById(android.R.id.content);
    }


    public void showLoading(boolean isShowing){
        if(isShowing) {
            if (!loadingDialog.isVisible())
                loadingDialog.show(getSupportFragmentManager(), "LOADING");
        }
        else {
            if(loadingDialog.isVisible())
                loadingDialog.dismiss();
        }
    }

    public void showSnackBar(String message){
        Snackbar.make(root,message, BaseTransientBottomBar.LENGTH_LONG).show();
    }
}
