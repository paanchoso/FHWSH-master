package de.fhws.international.fhwsh.models;

import com.google.android.gms.maps.model.LatLng;

public class Place {
    private String name;
    private String description;
    private LatLng latLng;
    private long id;

    public Place(long id, String name, String description, LatLng latLng) {
        this.name = name;
        this.description = description;
        this.latLng = latLng;
        this.id = id;
    }

    public Place(String name, String description, LatLng latLng) {
        this.name = name;
        this.description = description;
        this.latLng = latLng;
        this.id = name.hashCode() + description.hashCode();
    }

    public String getName() {
        return name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    @Override
    public String toString() {
        return "Place{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", latLng=" + latLng +
                ", id=" + id +
                '}';
    }
}
