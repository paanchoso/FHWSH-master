package de.fhws.international.fhwsh.acrivities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import de.fhws.international.fhwsh.R;


public class LectureActivity extends AppCompatActivity {
    private Button BtnElearning;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_chat);

        BtnElearning = findViewById(R.id.BtnElearning);

        url="https://elearning.fhws.de/login/";

        BtnElearning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
    }
}
