package com.bagas.githubusers.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;

import com.bagas.githubusers.R;
import com.bagas.githubusers.databinding.DialogLoadingBinding;

public class LoadingDialogFragment extends DialogFragment {

    DialogLoadingBinding binding;

    public static LoadingDialogFragment newInstance(){
        return new LoadingDialogFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_loading,container,false);
        binding = DataBindingUtil.bind(v);
        return v;
    }
}
