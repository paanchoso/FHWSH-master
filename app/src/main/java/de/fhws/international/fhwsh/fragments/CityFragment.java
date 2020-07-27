package de.fhws.international.fhwsh.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import de.fhws.international.fhwsh.R;
import de.fhws.international.fhwsh.acrivities.NewPlaceActivity;
import de.fhws.international.fhwsh.acrivities.NewPostActivity;
import de.fhws.international.fhwsh.dao.AdminDao;
import de.fhws.international.fhwsh.dao.FakePlaceDao;
import de.fhws.international.fhwsh.dao.PlaceDao;
import de.fhws.international.fhwsh.models.Place;
import de.fhws.international.fhwsh.utils.PlaceAdapter;

@RequiresApi(api = Build.VERSION_CODES.O)
public class CityFragment extends Fragment implements OnMapReadyCallback {

    GoogleMap gMap;
    MapView mapView;
    View view;
    PlaceDao placeDao = FakePlaceDao.getInstance();

    ListView listView;
    EditText filterQuery;
    PlaceAdapter adapter;

    @SuppressLint("ResourceType")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_city, container, false);
        LinearLayout ll = view.findViewById(R.id.cityButtonsPlace);

        Button btnReload = new Button(view.getContext());
        btnReload.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        btnReload.setText("Reload");
        btnReload.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        btnReload.setTextColor(Color.parseColor("#FFFFFF"));
        btnReload.setHeight(40);

        btnReload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reload();
            }
        });

        if (AdminDao.currentUserIsAdmin) {
            ll.setOrientation(LinearLayout.HORIZONTAL);
            ll.setGravity(1);

            Button btnTag = new Button(view.getContext());
            btnTag.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            btnTag.setText("Add");
            btnTag.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            btnTag.setTextColor(Color.parseColor("#FFFFFF"));
            btnTag.setHeight(40);

            btnTag.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getActivity(), NewPlaceActivity.class));
                }
            });

            Button btnDelete = new Button(view.getContext());
            btnDelete.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            btnDelete.setText("Delete");
            btnDelete.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            btnDelete.setTextColor(Color.parseColor("#FFFFFF"));
            btnDelete.setHeight(40);

            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast toast = Toast.makeText(getContext(),
                            "Click on the marker that you want to delete!", Toast.LENGTH_SHORT);
                    toast.show();
                    gMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                        @Override
                        public boolean onMarkerClick(Marker marker) {
                            placeDao.deletePlace(marker.getTitle());
                            reload();
                            return true;
                        }
                    });
                }
            });

            ll.addView(btnDelete);
            ll.addView(btnTag);
            ll.addView(btnReload);
        } else {
            ll.addView(btnReload);
        }

        filterQuery = view.findViewById(R.id.placeFilter);
        listView = view.findViewById(R.id.listViewOfPlaces);

        adapter = new PlaceAdapter(view.getContext(), placeDao.getAll());
        listView.setTextFilterEnabled(true);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CameraPosition liberty = CameraPosition.builder().target(((Place) parent.getItemAtPosition(position)).getLatLng()).zoom(16).bearing(0).tilt(45).build();
                gMap.moveCamera(CameraUpdateFactory.newCameraPosition(liberty));
            }
        });

        filterQuery.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.getFilter().filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mapView = (MapView) view.findViewById(R.id.mapCity);
        if (mapView != null) {
            mapView.onCreate(null);
            mapView.onResume();
            mapView.getMapAsync(this);
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        MapsInitializer.initialize(getContext());

        gMap = googleMap;
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        for (Place place : placeDao.getAll()) {
            googleMap.addMarker(new MarkerOptions().position(place.getLatLng()).title(place.getName()).snippet(place.getDescription()));
        }

        CameraPosition liberty = CameraPosition.builder().target(new LatLng(49.787736, 9.932540)).zoom(16).bearing(0).tilt(45).build();

        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(liberty));
    }

    private void reload() {
        adapter.update();
        adapter.notifyDataSetChanged();
        gMap.clear();
        for (Place place : placeDao.getAll()) {
            gMap.addMarker(new MarkerOptions().position(place.getLatLng()).title(place.getName()).snippet(place.getDescription()));
        }
        gMap.setOnMarkerClickListener(null);
    }
}
