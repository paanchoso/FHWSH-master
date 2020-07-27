package de.fhws.international.fhwsh.acrivities;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;

import de.fhws.international.fhwsh.R;
import de.fhws.international.fhwsh.dao.FakePostDao;
import de.fhws.international.fhwsh.dao.PostDao;
import de.fhws.international.fhwsh.models.Post;

@RequiresApi(api = Build.VERSION_CODES.O)
public class EditPostActivity extends AppCompatActivity {
    Button savePost;
    PostDao postDao = FakePostDao.getInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Long postId = getIntent().getLongExtra("postId", 0l);
        setContentView(R.layout.activity_edit_post);

        final TextInputLayout postTitleTextInputLayout = findViewById(R.id.postTitleEdit);
        EditText titleEntity = postTitleTextInputLayout.getEditText();
        titleEntity.setText(postDao.getById(postId).getTitle());

        final TextInputLayout postTextTextInputLayout = findViewById(R.id.postTextEdit);
        EditText postEntity = postTextTextInputLayout.getEditText();
        postEntity.setText(postDao.getById(postId).getInfo());

        savePost = findViewById(R.id.saveEditedPost);
        savePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String title = postTitleTextInputLayout.getEditText().getText().toString();

                final String text = postTextTextInputLayout.getEditText().getText().toString();

                postDao.update(postId, new Post(postId, title, text));
                finish();
            }
        });
    }
}
