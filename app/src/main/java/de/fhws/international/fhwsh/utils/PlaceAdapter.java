package de.fhws.international.fhwsh.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import de.fhws.international.fhwsh.R;
import de.fhws.international.fhwsh.acrivities.EditPostActivity;
import de.fhws.international.fhwsh.dao.AdminDao;
import de.fhws.international.fhwsh.dao.FakePlaceDao;
import de.fhws.international.fhwsh.dao.PlaceDao;
import de.fhws.international.fhwsh.models.Place;

public class PlaceAdapter extends BaseAdapter {

    private List<Place> originalData = null;
    private List<Place> filteredData = null;
    private LayoutInflater mInflater;
    private PlaceFilter mFilter = new PlaceFilter();

    private static class ViewHolder {
        TextView pName;
        TextView pDescription;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void update() {
        originalData = FakePlaceDao.getInstance().getAll();
        filteredData = FakePlaceDao.getInstance().getAll();
    }

    public PlaceAdapter(@NonNull Context context, @NonNull List<Place> data) {
        this.filteredData = data;
        this.originalData = data;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return filteredData.size();
    }

    @Override
    public Place getItem(int position) {
        return filteredData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Place place = getItem(position);
        ViewHolder viewHolder;

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.place_item, parent, false);
            viewHolder.pName = (TextView) convertView.findViewById(R.id.placeName);
            viewHolder.pDescription = (TextView) convertView.findViewById(R.id.placeDescription);

            result = convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        viewHolder.pName.setText(place.getName());
        viewHolder.pDescription.setText(place.getDescription());
        // Return the completed view to render on screen
        return result;
    }

    public Filter getFilter() {
        return mFilter;
    }

    private class PlaceFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            String filterString = constraint.toString().toLowerCase();

            FilterResults results = new FilterResults();

            final List<Place> list = originalData;

            int count = list.size();
            final ArrayList<Place> nlist = new ArrayList<Place>(count);

            for (Place place : list) {
                if (place.getName().toLowerCase().contains(filterString)) {
                    nlist.add(place);
                }
            }

            results.values = nlist;
            results.count = nlist.size();

            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredData = (ArrayList<Place>) results.values;
            notifyDataSetChanged();
        }

    }
}
