package de.fhws.international.fhwsh.acrivities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.textfield.TextInputLayout;

import de.fhws.international.fhwsh.R;
import de.fhws.international.fhwsh.dao.FakePlaceDao;
import de.fhws.international.fhwsh.dao.PlaceDao;
import de.fhws.international.fhwsh.dialogs.ErrorNewPlaceDialog;
import de.fhws.international.fhwsh.models.Place;
import de.fhws.international.fhwsh.models.Post;

@RequiresApi(api = Build.VERSION_CODES.O)
public class NewPlaceActivity extends AppCompatActivity implements OnMapReadyCallback {

    GoogleMap gMap;
    MapView mapView;
    PlaceDao placeDao = FakePlaceDao.getInstance();
    LatLng mapPointPosition;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_place);
        mapView = (MapView) findViewById(R.id.mapCityNew);
        if (mapView != null) {
            mapView.onCreate(null);
            mapView.onResume();
            mapView.getMapAsync(this);
        }

        Button btnCreate = findViewById(R.id.createNewPlace);
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TextInputLayout placeTitleTextInputLayout = findViewById(R.id.placeTitleNew);
                final String title = placeTitleTextInputLayout.getEditText().getText().toString();

                TextInputLayout placeDescriptionTextInputLayout = findViewById(R.id.placeDescriptionNew);
                final String description = placeDescriptionTextInputLayout.getEditText().getText().toString();

                if (title == null || mapPointPosition == null) {
                    ErrorNewPlaceDialog errorNewPlaceDialog = new ErrorNewPlaceDialog();
                    errorNewPlaceDialog.show(getSupportFragmentManager(), "Error");
                    return;
                }
                placeDao.addNewPlace(new Place(title, description, mapPointPosition));
                finish();
            }
        });
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        MapsInitializer.initialize(this);

        gMap = googleMap;
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        for (Place place : placeDao.getAll()) {
            googleMap.addMarker(new MarkerOptions().draggable(true).position(place.getLatLng()).title(place.getName()).snippet(place.getDescription()));
        }

        CameraPosition liberty = CameraPosition.builder().target(new LatLng(49.787736, 9.932540)).zoom(16).bearing(0).tilt(45).build();

        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(liberty));

        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng latLng) {
                mapPointPosition = latLng;
                // Creating a marker
                MarkerOptions markerOptions = new MarkerOptions();

                // Setting the position for the marker
                markerOptions.position(latLng);

                // Setting the title for the marker.
                // This will be displayed on taping the marker
                markerOptions.title(latLng.latitude + " : " + latLng.longitude);

                // Clears the previously touched position
                googleMap.clear();

                // Animating to the touched position
                googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));

                // Placing a marker on the touched position
                googleMap.addMarker(markerOptions);
            }
        });
    }
}
