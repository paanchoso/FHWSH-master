package de.fhws.international.fhwsh.dao;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.google.android.gms.maps.model.LatLng;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.fhws.international.fhwsh.models.Place;
import de.fhws.international.fhwsh.models.Post;

public class FakePlaceDao implements PlaceDao {

    // static variable single_instance of type Singleton
    private static FakePlaceDao single_instance = null;

    // variable of type String
    public Map<String, Place> db;

    // private constructor restricted to this class itself
    @RequiresApi(api = Build.VERSION_CODES.O)
    private FakePlaceDao() {
        db = new HashMap<>();
        db.put("Main campus", new Place(1l, "Main campus", "This is the main campus", new LatLng(49.787736, 9.932540)));
        db.put("Dorm", new Place(2l, "Dorm", "Dormitory", new LatLng(49.811135, 9.957151)));
        db.put("Resident", new Place(3l, "Resident", "Wurzburg Resident", new LatLng(49.792635, 9.939097)));
        db.put("Test", new Place(4l, "Test", "Test", new LatLng(49.792635, 9.859097)));
    }

    // static method to create instance of Singleton class
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static FakePlaceDao getInstance() {
        if (single_instance == null)
            single_instance = new FakePlaceDao();

        return single_instance;
    }

    @Override
    public void addNewPlace(Place place) {
        db.put(place.getName(), place);
    }

    @Override
    public void deletePlace(String name) {
        db.remove(name);
    }

    @Override
    public List<Place> getAll() {
        return new ArrayList<>(db.values());
    }

    @Override
    public Place getByName(String name) {
        return db.get(name);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void update(Place place) {
        db.replace(place.getName(), place);
    }

    @Override
    public int size() {
        return db.size();
    }
}
