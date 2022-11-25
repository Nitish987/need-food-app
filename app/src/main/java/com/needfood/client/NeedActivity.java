package com.needfood.client;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.needfood.client.adapter.DonateRecyclerAdapter;
import com.needfood.client.adapter.DonationNeedRecyclerAdapter;
import com.needfood.client.models.Donate;

import java.util.Locale;

public class NeedActivity extends AppCompatActivity {
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_need);

        recyclerView = findViewById(R.id.active_donation_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        recyclerView.setHasFixedSize(true);
    }

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences preferences = getSharedPreferences("CITY", MODE_PRIVATE);
        String city = preferences.getString("CT", null);

        Query query = FirebaseFirestore.getInstance().collection("donate").whereEqualTo("city", city.toLowerCase(Locale.ROOT));
        FirestoreRecyclerOptions<Donate> options = new FirestoreRecyclerOptions.Builder<Donate>().setQuery(query, Donate.class).build();
        DonationNeedRecyclerAdapter adapter = new DonationNeedRecyclerAdapter(options);
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }
}