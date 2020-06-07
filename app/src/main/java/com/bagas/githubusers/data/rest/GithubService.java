package com.bagas.githubusers.data.rest;

import com.bagas.githubusers.data.model.UserResult;

import io.reactivex.Single;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GithubService {

    @GET("search/users")
    Single<Response<UserResult>> getUsers(@Query("q") String query, @Query("page") Long page);



}
