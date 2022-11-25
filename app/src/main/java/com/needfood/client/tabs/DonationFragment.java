package com.needfood.client.tabs;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.needfood.client.R;
import com.needfood.client.adapter.DonationNeedRecyclerAdapter;
import com.needfood.client.adapter.MyDonationRecyclerAdapter;
import com.needfood.client.models.Donate;

import java.util.Locale;

public class DonationFragment extends Fragment {
    private View view;
    private RecyclerView recyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_donation, container, false);

        recyclerView = view.findViewById(R.id.my_donation_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        recyclerView.setHasFixedSize(true);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Query query = FirebaseFirestore.getInstance().collection("donate").whereEqualTo("by", FirebaseAuth.getInstance().getUid());
        FirestoreRecyclerOptions<Donate> options = new FirestoreRecyclerOptions.Builder<Donate>().setQuery(query, Donate.class).build();
        MyDonationRecyclerAdapter adapter = new MyDonationRecyclerAdapter(options);
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }
}