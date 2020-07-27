package de.fhws.international.fhwsh.acrivities;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;

import de.fhws.international.fhwsh.R;
import de.fhws.international.fhwsh.dao.FakePostDao;
import de.fhws.international.fhwsh.dao.PostDao;
import de.fhws.international.fhwsh.models.Post;

@RequiresApi(api = Build.VERSION_CODES.O)
public class NewPostActivity extends AppCompatActivity {
    Button createPost;
    PostDao postDao = FakePostDao.getInstance();
    long postId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);
        createPost = findViewById(R.id.createNewPost);
        createPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextInputLayout postTitleTextInputLayout = findViewById(R.id.postTitle);
                final String title = postTitleTextInputLayout.getEditText().getText().toString();

                TextInputLayout postTextTextInputLayout = findViewById(R.id.postText);
                final String text = postTextTextInputLayout.getEditText().getText().toString();


                postId = postDao.addNewPost(new Post(title, text));
                finish();
            }
        });
    }
}
