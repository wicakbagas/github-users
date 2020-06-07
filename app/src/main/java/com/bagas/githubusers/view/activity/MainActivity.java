package com.bagas.githubusers.view.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bagas.githubusers.R;
import com.bagas.githubusers.base.BaseActivity;
import com.bagas.githubusers.data.model.User;
import com.bagas.githubusers.databinding.ActivityMainBinding;
import com.bagas.githubusers.util.ViewModelFactory;
import com.bagas.githubusers.view.adapter.UsersAdapter;
import com.bagas.githubusers.viewmodel.activity.MainActivityViewModel;

import java.util.List;

import javax.inject.Inject;

public class MainActivity extends BaseActivity {


    ActivityMainBinding binding;

    @Inject
    ViewModelFactory viewModelFactory;
    MainActivityViewModel viewModel;

    UsersAdapter adapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MainActivityViewModel.class);

        viewModel.getErrorMessage().observe(this, this::showSnackBar);
        viewModel.getIsEmpty().observe(this, this::onIsEmptyReturned);
        viewModel.getIsLoading().observe(this, this::showLoading);

        binding.svUser.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                viewModel.getUserList(query).observe(MainActivity.this, MainActivity.this::onUserListReturned);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });


        binding.rvUser.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        DividerItemDecoration itemDecorator = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        itemDecorator.setDrawable(ContextCompat.getDrawable(this, R.drawable.divider_grey));
        binding.rvUser.addItemDecoration(itemDecorator);

        adapter = new UsersAdapter();
        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                super.onChanged();

                binding.tvEmpty.setVisibility(adapter.getItemCount()==0? View.VISIBLE:View.GONE);
            }
        });
        binding.rvUser.setAdapter(adapter);
    }

    void onUserListReturned(PagedList<User> users){
        adapter.submitList(users);

    }

    void onIsEmptyReturned(Boolean isEmpty){
        binding.tvEmpty.setVisibility(isEmpty?View.VISIBLE:View.GONE);
    }

    @Override
    public void showLoading(boolean isLoading){
        binding.pbLoading.setVisibility(isLoading?View.VISIBLE:View.GONE);
    }

}
