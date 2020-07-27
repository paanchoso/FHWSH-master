package de.fhws.international.fhwsh.dao;

import java.util.List;

import de.fhws.international.fhwsh.models.Place;
import de.fhws.international.fhwsh.models.Post;

public interface PlaceDao {
    void addNewPlace(Place place);

    void deletePlace(String name);

    List<Place> getAll();

    Place getByName(String name);

    void update(Place place);

    int size();
}
