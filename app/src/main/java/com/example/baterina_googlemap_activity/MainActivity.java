package com.example.baterina_googlemap_activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class MainActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap map1;
    private GoogleMap map2;
    private Button btnSearch;
    private Button btnClear;
    private EditText editTextLocation1;
    private EditText editTextLocation2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(MainActivity.this);

        btnSearch = (Button) findViewById(R.id.button_Search);
        btnClear = (Button) findViewById(R.id.button_Clear);
        editTextLocation1 = (EditText) findViewById(R.id.editText_Search1);
        editTextLocation2 = (EditText) findViewById(R.id.editText_Search2);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String location = editTextLocation1.getText().toString();
                String location2 = editTextLocation2.getText().toString();
                List<Address> addressList = null;

                if (location.isEmpty()) {
                    showMessage("Location 1 is EMPTY!");
                    return;
                }

                if(location != null || !location.equals("")){
                    Geocoder geocoder = new Geocoder(MainActivity.this);
                    try{
                        addressList = geocoder.getFromLocationName(location,1);
                    }
                    catch (IOException e){
                        e.printStackTrace();
                    }

                    Address address = addressList.get(0);
                    LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                    map1.addMarker(new MarkerOptions().position(latLng).title(location));
                    //map.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                    map1.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,10));
                    //Toast.makeText(getApplicationContext(),address.getLatitude()+" "+address.getLongitude(),Toast.LENGTH_LONG).show();
                }

                if (location2.isEmpty()) {
                    showMessage("Location 2 is EMPTY!");
                    return;
                }

                if(location2 != null || !location2.equals("")){
                    Geocoder geocoder = new Geocoder(MainActivity.this);
                    try{
                        addressList = geocoder.getFromLocationName(location2,1);
                    }
                    catch (IOException e){
                        e.printStackTrace();
                    }

                    Address address = addressList.get(0);
                    LatLng latLng2 = new LatLng(address.getLatitude(), address.getLongitude());
                    map2.addMarker(new MarkerOptions().position(latLng2).title(location2));
                    //map.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                    map2.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng2,10));
                    //Toast.makeText(getApplicationContext(),address.getLatitude()+" "+address.getLongitude(),Toast.LENGTH_LONG).show();
                }
            }
        });

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clear();
            }
        });

    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        map1 = googleMap;
        map2 = googleMap;
    }

    private void showMessage(String message) {
        Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT);
        toast.show();
    }

    private void clear (){
        editTextLocation1.setText("");
        editTextLocation2.setText("");
        map1.clear();
        map2.clear();
    }

}