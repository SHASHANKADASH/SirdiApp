package com.example.sirdiapp.Health;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.sirdiapp.MapParse.GetNearbyPlaces;
import com.example.sirdiapp.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class HealthMapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private static final float DEFAULT_ZOOM = 15f;

    private Boolean mLocationPermissionsGranted = false;
    private GoogleMap mMap;
    private FloatingActionButton hospital, clinics, medical_store;
    private Location current_location;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_map);

        hospital = findViewById(R.id.button_hospital);
        clinics = findViewById(R.id.button_clinics);
        medical_store = findViewById(R.id.button_store);

        getLocationPermission();
        hospital_button_clicked();
        clinics_button_clicked();
        store_button_clicked();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (mLocationPermissionsGranted) {
            getDeviceLocation();

            if (ActivityCompat.checkSelfPermission(this, FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this, COURSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            mMap.setMyLocationEnabled(true);
            //mMap.getUiSettings().setMyLocationButtonEnabled(false);
        }
        Toast.makeText(this, "Click to Show Nearby Places", Toast.LENGTH_SHORT).show();
    }

    private void hospital_button_clicked() {
        hospital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMap.clear();

                String place = "hospital";
                String nearto = current_location.getLatitude() + "," + current_location.getLongitude();
                String url = getUrl(nearto, place);
                Object[] transferData = new Object[2];
                GetNearbyPlaces getNearbyPlaces = new GetNearbyPlaces();
                transferData[0] = mMap;
                transferData[1] = url;

                getNearbyPlaces.execute(transferData);

                Toast.makeText(HealthMapActivity.this, "Please Wait", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void clinics_button_clicked() {
        clinics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMap.clear();

                String place = "clinic";
                String nearto = current_location.getLatitude() + "," + current_location.getLongitude();
                String url = getUrl(nearto, place);
                Object[] transferData = new Object[2];
                GetNearbyPlaces getNearbyPlaces = new GetNearbyPlaces();
                transferData[0] = mMap;
                transferData[1] = url;

                getNearbyPlaces.execute(transferData);

                Toast.makeText(HealthMapActivity.this, "Please Wait", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void store_button_clicked() {
        medical_store.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMap.clear();

                String place = "pharmacy";
                String nearto = current_location.getLatitude() + "," + current_location.getLongitude();
                String url = getUrl(nearto, place);
                Object[] transferData = new Object[2];
                GetNearbyPlaces getNearbyPlaces = new GetNearbyPlaces();
                transferData[0] = mMap;
                transferData[1] = url;

                getNearbyPlaces.execute(transferData);

                Toast.makeText(HealthMapActivity.this, "Please Wait", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String getUrl(String near_to, String nearby_place) {

        String url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?" + "location=" + near_to +
                "&radius=" + 3000 +
                "&type=" + nearby_place +
                "&keyword=" + nearby_place +
                "&key=" + getString(R.string.google_api_places);
        return url;
    }

    private void getDeviceLocation() {

        FusedLocationProviderClient mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        try {
            if (mLocationPermissionsGranted) {

                final Task location = mFusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()) {
                            current_location = (Location) task.getResult();

                            assert current_location != null;
                            LatLng latlng = new LatLng(current_location.getLatitude(), current_location.getLongitude());
                            moveCamera(latlng);

                        } else {
                            Toast.makeText(HealthMapActivity.this, "Unable to get Current Location", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        } catch (SecurityException ignored) {

        }
    }

    private void moveCamera(LatLng latLng) {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, HealthMapActivity.DEFAULT_ZOOM));
    }

    private void initMap() {

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_view);

        assert mapFragment != null;
        mapFragment.getMapAsync(HealthMapActivity.this);
    }

    private void getLocationPermission() {
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};

        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                    COURSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                mLocationPermissionsGranted = true;
                initMap();
            } else {
                ActivityCompat.requestPermissions(this,
                        permissions,
                        LOCATION_PERMISSION_REQUEST_CODE);
            }
        } else {
            ActivityCompat.requestPermissions(this,
                    permissions,
                    LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        mLocationPermissionsGranted = false;
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0) {
                for (int grantResult : grantResults) {

                    if (grantResult != PackageManager.PERMISSION_GRANTED) {
                        mLocationPermissionsGranted = false;
                        return;
                    }
                }
                mLocationPermissionsGranted = true;
                initMap();
            }
        }
    }
}
