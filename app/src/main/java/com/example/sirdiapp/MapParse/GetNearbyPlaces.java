package com.example.sirdiapp.MapParse;

import android.os.AsyncTask;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class GetNearbyPlaces extends AsyncTask<Object,String,String> {

    private String googleplacesdata;
    private GoogleMap mMap;
    private String url;
    private static final float DEFAULT_ZOOM = 13f;

    @Override
    protected String doInBackground(Object... objects) {
        mMap=(GoogleMap)objects[0];
        url=(String)objects[1];

        DownloadUrl downloadUrl=new DownloadUrl();
        try {
            googleplacesdata = downloadUrl.readUrl(url);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return googleplacesdata;
    }

    @Override
    protected void onPostExecute(String s) {
        List<HashMap<String,String>> nearbyPlaceList = null;
        DataParser parser= new DataParser();
        nearbyPlaceList= parser.parse(s);
        showNearPlaces(nearbyPlaceList);
    }

    private void showNearPlaces(List<HashMap<String,String>>nearbyPlacesList){
        for(int i=0;i<nearbyPlacesList.size();i++){
            MarkerOptions markerOptions =new MarkerOptions();
            HashMap<String,String> googlePlace = nearbyPlacesList.get(i);

            String placeName = googlePlace.get("place_name");
            String vicinity = googlePlace.get("vicinity");
            double lat = Double.parseDouble(googlePlace.get("lat"));
            double lng = Double.parseDouble(googlePlace.get("lng"));

            LatLng latLng = new LatLng(lat,lng);
            markerOptions.position(latLng);
            markerOptions.title(placeName+":"+vicinity);
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));

            CameraUpdate location = CameraUpdateFactory.newLatLngZoom(latLng,DEFAULT_ZOOM);

            mMap.addMarker(markerOptions);
            mMap.moveCamera(location);
        }
    }
}
