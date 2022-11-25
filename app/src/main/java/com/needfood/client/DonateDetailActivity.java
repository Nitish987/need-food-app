package com.needfood.client;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.needfood.client.databinding.ActivityDonateDetailBinding;
import com.needfood.client.models.Donate;

public class DonateDetailActivity extends FragmentActivity implements OnMapReadyCallback {
    private static final int CALL_PERMISSION_CODE = 223;
    private GoogleMap mMap;
    private ActivityDonateDetailBinding binding;
    private double lat = 0, log = 0;
    private String name = null, phone = null, address = null, id = null, by = null;

    private TextView mName, mPhone, mAddress;
    private Button removeBtn;
    private ImageView callBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityDonateDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        name = getIntent().getStringExtra("NAME");
        phone = getIntent().getStringExtra("PHONE");
        address = getIntent().getStringExtra("ADDRESS");
        lat = getIntent().getDoubleExtra("LAT", 0.0);
        log = getIntent().getDoubleExtra("LONG", 0.0);
        by = getIntent().getStringExtra("BY");
        id = getIntent().getStringExtra("ID");

        mName = findViewById(R.id.name);
        mPhone = findViewById(R.id.phone);
        mAddress = findViewById(R.id.address);
        removeBtn = findViewById(R.id.remove_active_donation);
        callBtn = findViewById(R.id.make_call);

        checkCallPermission();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng latLng = new LatLng(lat, log);
        mMap.addMarker(new MarkerOptions().position(latLng).title(name));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.setMinZoomPreference(15);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mName.setText(name);
        mPhone.setText(phone);
        mAddress.setText(address);

        if (by.equals(FirebaseAuth.getInstance().getUid())) {
            removeBtn.setVisibility(View.VISIBLE);
        }

        removeBtn.setOnClickListener(view -> {
            FirebaseFirestore.getInstance().collection("donate").document(id).delete().addOnSuccessListener(unused -> {
                Toast.makeText(this, "Removed.", Toast.LENGTH_SHORT).show();
                finish();
            }).addOnFailureListener(e -> {
                Toast.makeText(this, "Something went wrong!", Toast.LENGTH_SHORT).show();
            });
        });

        callBtn.setOnClickListener(view -> makeCall());
    }

    private void makeCall() {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + phone));
        startActivity(callIntent);
    }

    private void checkCallPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, new String[]{ Manifest.permission.CALL_PHONE }, CALL_PERMISSION_CODE);
        }
    }
}