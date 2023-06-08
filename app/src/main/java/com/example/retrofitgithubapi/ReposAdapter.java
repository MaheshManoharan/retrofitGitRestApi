package com.example.retrofitgithubapi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.retrofitgithubapi.model.GitHubRepo;

import java.util.List;

public class ReposAdapter extends RecyclerView.Adapter<ReposAdapter.ReposViewHolder>
{
    private List<GitHubRepo> repos;
    private int rowLayout;
    private Context context;

    public ReposAdapter(List<GitHubRepo> repos, int rowLayout, Context context)
    {
    this.setContext(context);
    this.setRowLayout(rowLayout);
    this.setRepos(repos);
    }

    public int getRowLayout() {
        return rowLayout;
    }

    public void setRowLayout(int rowLayout) {
        this.rowLayout = rowLayout;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ReposViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new ReposViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReposViewHolder holder, int position)
    {
        holder.repoName.setText(repos.get(position).getName());
        holder.repoDescription.setText(repos.get(position).getDescription());
        holder.repoLanguage.setText(repos.get(position).getLanguage());
//        holder.repoDescription.setText(repos.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return repos.size();
    }

    public void setRepos(List<GitHubRepo> repos) {
        this.repos = repos;
    }


    public static class ReposViewHolder extends RecyclerView.ViewHolder{
        LinearLayout reposLayout;
        TextView repoName;
        TextView repoDescription;
        TextView repoLanguage;

        public ReposViewHolder(View view)
        {
            super(view);
            reposLayout = (LinearLayout) view.findViewById(R.id.repo_item_layout);
            repoName = (TextView) view.findViewById(R.id.repoName);
            repoDescription = (TextView) view.findViewById(R.id.repoDescription);
            repoLanguage = (TextView) view.findViewById(R.id.repoLanguage);


        }
    }
}
