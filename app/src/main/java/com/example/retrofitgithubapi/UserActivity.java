package com.example.retrofitgithubapi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.AsyncTaskLoader;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.retrofitgithubapi.model.GitHubUser;
import com.example.retrofitgithubapi.rest.ApiClient;
import com.example.retrofitgithubapi.rest.GitHubEndPoints;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserActivity extends AppCompatActivity {

    ImageView avatarImg;
    TextView userNameTV;
    TextView followersTV;
    TextView followingTV;
    TextView login;
    TextView email;
    Button ownedrepos;

    Bundle extras;
    String newString;

    Bitmap myImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        avatarImg = (ImageView) findViewById(R.id.avatar);
        userNameTV = (TextView) findViewById(R.id.username);
        followersTV = (TextView) findViewById(R.id.followers);
        followingTV = (TextView) findViewById(R.id.following);
        login = (TextView) findViewById(R.id.login);
        email = (TextView) findViewById(R.id.emailid);
        ownedrepos = (Button) findViewById(R.id.ownedReposBtn);



        extras = getIntent().getExtras();
        newString = extras.getString("STRING_I_NEED");

        System.out.println("hi");
        System.out.println(newString);

        loadData();


    }

    public void loadOwnRepos(View view)
    {
        Intent intent = new Intent(UserActivity.this, Repositories.class);
        intent.putExtra("username", newString);
        startActivity(intent);
    }

    public void loadData() {
        final GitHubEndPoints apiService = ApiClient.getClient().create(GitHubEndPoints.class);

        Call<GitHubUser> call = apiService.getUser(newString);

        call.enqueue(new Callback<GitHubUser>() {
            @Override
            public void onResponse(Call<GitHubUser> call, Response<GitHubUser> response) {
                userNameTV.setText("UserName: " + response.body().getName());
                followersTV.setText("Followers: " + response.body().getFollowers());
                followingTV.setText("Following: " + response.body().getFollowing());
                login.setText("Login: " + response.body().getLogin());
                email.setText("Email: " + response.body().getEmail());


            ImageDownloader task = new ImageDownloader();

            try
            {
                myImage = task.execute(response.body().getAvatar()).get();
                avatarImg.setImageBitmap(myImage);
                avatarImg.getLayoutParams().height = 220;
                avatarImg.getLayoutParams().width = 220;

            }
            catch (Exception e)
            {
            e.printStackTrace();
            }

            }

            @Override
            public void onFailure(Call<GitHubUser> call, Throwable t) {

            }
        });

    }


    public class ImageDownloader extends AsyncTask<String, Void, Bitmap>
    {

        @Override
        protected Bitmap doInBackground(String... urls) {
            try
            {
              URL url = new URL(urls[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream inputStream = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(inputStream);
                return myBitmap;

            } catch(MalformedURLException e){
                e.printStackTrace();
            } catch (IOException e)
            {
            e.printStackTrace();
            }

            return null;
        }
    }

}