package de.fhws.international.fhwsh.utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.time.format.DateTimeFormatter;
import java.util.List;

import de.fhws.international.fhwsh.R;
import de.fhws.international.fhwsh.acrivities.EditPostActivity;
import de.fhws.international.fhwsh.dao.AdminDao;
import de.fhws.international.fhwsh.dao.PostDao;
import de.fhws.international.fhwsh.models.Post;

public class RecyclePostAdapter extends RecyclerView.Adapter<RecyclePostAdapter.PostHolder> {

    List<Post> posts;
    private PostDao postDao;

    public void update() {
        this.posts = postDao.getAll();
    }

    public RecyclePostAdapter(List<Post> posts, PostDao postDao) {
        this.posts = posts;
        this.postDao = postDao;
    }

    @NonNull
    @Override
    public PostHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.post_layout, parent, false);

        return new PostHolder(linearLayout);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull final PostHolder holder, final int position) {
        holder.title.setText(posts.get(position).getTitle());
        holder.text.setText(posts.get(position).getInfo());
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        holder.date.setText(dtf.format(posts.get(position).getDate()));
        if (AdminDao.currentUserIsAdmin) {
            holder.btnDel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    postDao.deletePost(posts.get(position).getId());
                }
            });
            holder.btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), EditPostActivity.class);
                    intent.putExtra("postId", posts.get(position).getId());
                    v.getContext().startActivity(intent);
                }
            });
        }


    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public static class PostHolder extends RecyclerView.ViewHolder {

        TextView title;
        TextView date;
        TextView text;
        Button btnDel;
        Button btnEdit;

        public PostHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.postTitle);
            text = itemView.findViewById(R.id.postText);
            date = itemView.findViewById(R.id.postDate);


            LinearLayout linearLayout = itemView.findViewById(R.id.postLinearLayout);

            if (AdminDao.currentUserIsAdmin) {
                btnDel = new Button(itemView.getContext());
                btnDel.setLayoutParams(itemView.getLayoutParams());
                btnDel.setText("Delete post");
                btnEdit = new Button(itemView.getContext());
                btnEdit.setLayoutParams(itemView.getLayoutParams());
                btnEdit.setText("Edit post");
                linearLayout.addView(btnDel);
                linearLayout.addView(btnEdit);
            }
        }
    }
}
