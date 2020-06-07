package com.bagas.githubusers.viewmodel.activity;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.bagas.githubusers.data.datasource.UserDataSource;
import com.bagas.githubusers.data.datasource.UserDataSourceFactory;
import com.bagas.githubusers.data.model.User;
import com.bagas.githubusers.data.rest.GithubService;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class MainActivityViewModel extends ViewModel {

    private final GithubService githubService;

    private final MutableLiveData<Boolean> loading = new MutableLiveData<>();
    private LiveData<String> errorMessage;
    private LiveData<Boolean> isLoading;
    private LiveData<Boolean> isEmpty;

    private UserDataSourceFactory sourceFactory;
    private LiveData<PagedList<User>> userList;

    @Inject
    MainActivityViewModel( GithubService githubService) {
        //this.executor = Executors.newFixedThreadPool(5);
        this.githubService = githubService;
        this.sourceFactory = new UserDataSourceFactory(githubService);
        errorMessage = Transformations.switchMap(sourceFactory.getUserDataSourceMutableLiveData(),
                UserDataSource::getErrMsg);
        isLoading = Transformations.switchMap(sourceFactory.getUserDataSourceMutableLiveData(),
                UserDataSource::getIsLoading);
        isEmpty = Transformations.switchMap(sourceFactory.getUserDataSourceMutableLiveData(),
                UserDataSource::getIsEmpty);


    }


    public LiveData<PagedList<User>> getUserList(String filter) {
        if(userList==null) {
            PagedList.Config config = new PagedList.Config.Builder()
                    .setInitialLoadSizeHint(60)
                    .setPageSize(30)
                    .setEnablePlaceholders(true)
                    .build();
            //setPrefetchDistance(10)
            userList = new LivePagedListBuilder<Long, User>(sourceFactory, config)
                    .build();
        }
        setFilter(filter);
        return userList;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public void refresh() {
        sourceFactory.refresh();
    }

    public void setFilter(String filter){
        sourceFactory.setFilter(filter);
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public void setIsLoading(LiveData<Boolean> isLoading) {
        this.isLoading = isLoading;
    }

    public LiveData<Boolean> getIsEmpty() {
        return isEmpty;
    }

    public void setIsEmpty(LiveData<Boolean> isEmpty) {
        this.isEmpty = isEmpty;
    }
}
