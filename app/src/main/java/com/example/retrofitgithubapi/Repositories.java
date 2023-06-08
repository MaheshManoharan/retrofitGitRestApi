package com.example.retrofitgithubapi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

import com.example.retrofitgithubapi.model.GitHubRepo;
import com.example.retrofitgithubapi.rest.ApiClient;
import com.example.retrofitgithubapi.rest.GitHubEndPoints;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Repositories extends AppCompatActivity
{

    String receivedUserName;
    TextView userNameTV;
    RecyclerView mRecyclerView;
    List<GitHubRepo> myDataSource = new ArrayList<>();
    RecyclerView.Adapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repositories);

        Bundle extras = getIntent().getExtras();
        receivedUserName = extras.getString("username");
        userNameTV = (TextView) findViewById(R.id.userNameTv);
//        userNameTV.setText("User: "+ receivedUserName);

        mRecyclerView = (RecyclerView) findViewById(R.id.repos_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        myAdapter = new ReposAdapter(myDataSource, R.layout.list_item_repo, getApplicationContext());

        mRecyclerView.setAdapter(myAdapter);
        loadRepositories();

    }

    private void loadRepositories()
    {
        GitHubEndPoints apiService = ApiClient.getClient().create(GitHubEndPoints.class);

        Call<List<GitHubRepo>> call = apiService.getRepo(receivedUserName);
        call.enqueue(new Callback<List<GitHubRepo>>() {
            @Override
            public void onResponse(Call<List<GitHubRepo>> call, Response<List<GitHubRepo>> response) {
                    myDataSource.clear();
                    myDataSource.addAll(response.body());
                    myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<GitHubRepo>> call, Throwable t) {

            }
        });
    }
}