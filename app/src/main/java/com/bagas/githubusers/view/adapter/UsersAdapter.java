package com.bagas.githubusers.view.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.paging.PagedList;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bagas.githubusers.R;
import com.bagas.githubusers.data.model.User;
import com.bagas.githubusers.databinding.RowUserBinding;

import java.util.ArrayList;
import java.util.List;

public class UsersAdapter extends PagedListAdapter<User, UsersAdapter.UserViewHolder> {

    public UsersAdapter() {
        super(DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_user, parent, false);

        return new UserViewHolder(v);
    }

    @Nullable
    @Override
    protected User getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User u = getItem(position);
        holder.binding.ivUser.setImageURI(u.getAvatarUrl());
        holder.binding.tvName.setText(u.getUsername());

    }

    @Override
    public int getItemCount() {
        return super.getItemCount();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder{
        RowUserBinding binding;
        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }
    }

    public static final DiffUtil.ItemCallback<User> DIFF_CALLBACK =
        new DiffUtil.ItemCallback<User>() {
            @Override
            public boolean areItemsTheSame(
                    @NonNull User oldUser, @NonNull User newUser) {
                // User properties may have changed if reloaded from the DB, but ID is fixed
                return oldUser.getUsername().equals(newUser.getUsername());
            }
            @Override
            public boolean areContentsTheSame(
                    @NonNull User oldUser, @NonNull User newUser) {
                // NOTE: if you use equals, your object must properly override Object#equals()
                // Incorrectly returning false here will result in too many animations.
                return oldUser.getUsername().equals(newUser.getUsername());
            }
        };
}
