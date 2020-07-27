package de.fhws.international.fhwsh.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import de.fhws.international.fhwsh.R;
import de.fhws.international.fhwsh.acrivities.EditPostActivity;
import de.fhws.international.fhwsh.acrivities.LoginActivity;
import de.fhws.international.fhwsh.acrivities.MainActivity;
import de.fhws.international.fhwsh.acrivities.NewPostActivity;
import de.fhws.international.fhwsh.dao.AdminDao;
import de.fhws.international.fhwsh.dao.FakePostDao;
import de.fhws.international.fhwsh.dao.PostDao;
import de.fhws.international.fhwsh.models.Post;
import de.fhws.international.fhwsh.utils.RecyclePostAdapter;

import static android.app.Activity.RESULT_OK;

@RequiresApi(api = Build.VERSION_CODES.O)
public class InfoFragment extends Fragment {

    PostDao postDao = FakePostDao.getInstance();
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclePostAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Nullable
    @Override

    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        boolean admin = AdminDao.currentUserIsAdmin;
        System.out.println(admin);

        final View view = inflater.inflate(R.layout.fragment_info, container, false);

        if (admin) {
            Button btnTag = new Button(view.getContext());
            btnTag.setLayoutParams(view.getLayoutParams());
            btnTag.setText("Add new post");

            btnTag.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getActivity(), NewPostActivity.class));
                }
            });

            LinearLayout linearLayout = view.findViewById(R.id.topPlace);
            linearLayout.addView(btnTag);
        }

        recyclerView = view.findViewById(R.id.recyclerViewInfo);
        layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);


        adapter = new RecyclePostAdapter(postDao.getAll(), postDao);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        recyclerView.invalidate();
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshPosts);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                adapter.update();
                adapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        return view;
    }
}
