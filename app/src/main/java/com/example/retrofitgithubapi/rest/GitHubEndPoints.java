package com.example.retrofitgithubapi.rest;

import com.example.retrofitgithubapi.model.GitHubRepo;
import com.example.retrofitgithubapi.model.GitHubUser;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GitHubEndPoints
{
    @GET("/users/{user}")
    Call<GitHubUser> getUser(@Path("user") String user);

    @GET("/users/{user}/repos")
    Call<List<GitHubRepo>> getRepo(@Path("user") String name);




}
