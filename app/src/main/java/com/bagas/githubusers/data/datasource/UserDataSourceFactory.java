package com.bagas.githubusers.data.datasource;

import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

import com.bagas.githubusers.data.rest.GithubService;


public class UserDataSourceFactory extends DataSource.Factory {

    private static final String TAG = "RepairableDataSourceFactory";
    private GithubService githubService;
    private String filter = "";

    private MutableLiveData<UserDataSource> userDataSourceMutableLiveData = new MutableLiveData<>();
    public UserDataSource userDataSource;
//    private MutableLiveData<String> errMsg = new MutableLiveData<>();

    public UserDataSourceFactory(GithubService githubService) {
        this.githubService = githubService;
    }


    @Override
    public DataSource create() {

        //if (userDataSource != null)
            //userDataSource.invalidate();
        userDataSource = new UserDataSource(githubService, filter);
        userDataSourceMutableLiveData.postValue(userDataSource);
        return userDataSource;
    }

    public MutableLiveData<UserDataSource> getUserDataSourceMutableLiveData() {
        return userDataSourceMutableLiveData;
    }

    public void refresh() {
        if(userDataSource !=null)
            userDataSource.invalidate();
    }

    public void setFilter(String filter) {
        this.filter = filter;
        refresh();
    }

}