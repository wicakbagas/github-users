package com.bagas.githubusers.base;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bagas.githubusers.view.fragment.LoadingDialogFragment;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import dagger.android.support.DaggerFragment;

public class BaseFragment extends DaggerFragment {

    LoadingDialogFragment loadingDialog;
    View root;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadingDialog = LoadingDialogFragment.newInstance();
        root = view;
    }

    public void showLoading(boolean isShowing){
        Log.e("LOADING", "asd "+isShowing);
        if(isShowing) {
            if (!loadingDialog.isVisible())
                loadingDialog.show(getFragmentManager(), "LOADING");
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
