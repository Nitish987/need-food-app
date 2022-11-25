package com.needfood.client.tabs;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
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
import com.needfood.client.DonateActivity;
import com.needfood.client.NeedActivity;
import com.needfood.client.R;
import com.needfood.client.adapter.DonateRecyclerAdapter;
import com.needfood.client.models.Donate;

public class HomeFragment extends Fragment {
    private CardView help, need;
    private RecyclerView recyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        help = view.findViewById(R.id.donate);
        need = view.findViewById(R.id.need);

        recyclerView = view.findViewById(R.id.active_donation_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext(), RecyclerView.HORIZONTAL, false));
        recyclerView.setHasFixedSize(true);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Query query = FirebaseFirestore.getInstance().collection("donate");
        FirestoreRecyclerOptions<Donate> options = new FirestoreRecyclerOptions.Builder<Donate>().setQuery(query, Donate.class).build();
        DonateRecyclerAdapter adapter = new DonateRecyclerAdapter(options);
        recyclerView.setAdapter(adapter);
        adapter.startListening();

        need.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), NeedActivity.class);
            startActivity(intent);
        });

        help.setOnClickListener(view -> {
            FirebaseFirestore.getInstance().collection("user").document(FirebaseAuth.getInstance().getUid()).get().addOnSuccessListener(documentSnapshot -> {
                String name = documentSnapshot.getString("name");

                Intent intent = new Intent(view.getContext(), DonateActivity.class);
                intent.putExtra("NAME", name);
                startActivity(intent);
            });
        });
    }
}