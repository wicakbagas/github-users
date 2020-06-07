package com.bagas.githubusers.data.datasource;

import android.net.UrlQuerySanitizer;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PageKeyedDataSource;

import com.bagas.githubusers.data.model.ErrorBody;
import com.bagas.githubusers.data.model.User;
import com.bagas.githubusers.data.model.UserResult;
import com.bagas.githubusers.data.rest.GithubService;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class UserDataSource extends PageKeyedDataSource<Long, User> {

    private String TAG = "UserDataSource";
    private GithubService githubService;
    private MutableLiveData<String> errMsg = new MutableLiveData<>();
    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    private MutableLiveData<Boolean> isEmpty = new MutableLiveData<>();
    private String filter="";

    UserDataSource(GithubService githubService, String filter) {
        this.githubService = githubService;
        this.filter = filter;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Long> params, @NonNull LoadInitialCallback<Long, User> callback) {
        String param = filter;
        if(filter.isEmpty())
            param = null;
        isLoading.postValue(true);
        githubService.getUsers(param,(long)1).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(new DisposableSingleObserver<Response<UserResult>>(){

                @Override
                public void onSuccess(Response<UserResult> value) {
                    isLoading.postValue(false);
                    if(value.code()==200) {
                        String nextUrl = "";
                        String linkUrl = value.headers().get("LINK");
                        Log.e("ok", value.headers().toString());
                        for (String s : linkUrl.split(",")) {
                            if (s.contains("rel=\"next\"")) {
                                nextUrl = s;
                            }
                        }

                        Long nextKey = null;
                        if (nextUrl.length() > 0) {
                            String url = nextUrl.substring(nextUrl.indexOf("<") + 1,
                                    nextUrl.indexOf((">")));

                            UrlQuerySanitizer sanitizer = new UrlQuerySanitizer(url);
                            nextKey = Long.parseLong(sanitizer.getValue("page"));

                        }
                        isEmpty.postValue(value.body().getItems().size()==0);

                        callback.onResult(value.body().getItems(), null, nextKey);
                    }else{
                        Gson gson = new Gson();
                        try {
                            ErrorBody errorBody = gson.fromJson(value.errorBody().string(), ErrorBody.class);
                            errMsg.postValue(errorBody.getMessage());
                        } catch (IOException e) {
                            errMsg.postValue(value.message());
                        }
                    }
                }

                @Override
                public void onError(Throwable e) {
                    isLoading.postValue(false);
                    errMsg.postValue(e.getMessage());
                }
            });
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Long> params, @NonNull LoadCallback<Long, User> callback) {

    }

    @Override
    public void loadAfter(@NonNull LoadParams<Long> params, @NonNull LoadCallback<Long, User> callback) {
        String param = filter;
        if(filter.isEmpty())
            param = null;
        isLoading.postValue(true);
        githubService.getUsers(param,params.key).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<Response<UserResult>>(){

                    @Override
                    public void onSuccess(Response<UserResult> value) {
                        isLoading.postValue(false);

                        if(value.code()==200) {
                            // find nextPage in the urls received in header
                            String nextUrl = "";
                            String linkUrl = value.headers().get("LINK");
                            for (String s : linkUrl.split(",")) {
                                if (s.contains("rel=\"next\"")) nextUrl = s;
                            }

                            Long nextKey = null;
                            if (nextUrl.length() > 0) {
                                String url = nextUrl.substring(nextUrl.indexOf("<") + 1,
                                        nextUrl.indexOf((">")));

                                UrlQuerySanitizer sanitizer = new UrlQuerySanitizer(url);
                                nextKey = Long.parseLong(sanitizer.getValue("page"));

                            }
                            isEmpty.postValue(value.body().getItems().size()==0);
                            callback.onResult(value.body().getItems(), nextKey);
                        }else{
                            Gson gson = new Gson();
                            try {
                                ErrorBody errorBody = gson.fromJson(value.errorBody().string(), ErrorBody.class);
                                errMsg.postValue(errorBody.getMessage());
                            } catch (IOException e) {
                                errMsg.postValue(value.message());
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        isLoading.postValue(false);
                        errMsg.postValue(e.getMessage());
                    }
                });
    }


    public MutableLiveData<String> getErrMsg() {
        return errMsg;
    }

    public MutableLiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public void setIsLoading(MutableLiveData<Boolean> isLoading) {
        this.isLoading = isLoading;
    }

    public MutableLiveData<Boolean> getIsEmpty() {
        return isEmpty;
    }

    public void setIsEmpty(MutableLiveData<Boolean> isEmpty) {
        this.isEmpty = isEmpty;
    }
}
