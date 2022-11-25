package com.needfood.client.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.needfood.client.DonateDetailActivity;
import com.needfood.client.R;
import com.needfood.client.models.Donate;

public class DonationNeedRecyclerAdapter extends FirestoreRecyclerAdapter<Donate, DonateRecyclerAdapter.DonateHolder> {
    public DonationNeedRecyclerAdapter(@NonNull FirestoreRecyclerOptions<Donate> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull DonateRecyclerAdapter.DonateHolder holder, int position, @NonNull Donate donate) {
        holder.setName(donate.getName());
        holder.setAddress(donate.getAddress());
        holder.setPhone(donate.getPhone());
        holder.setPhoto(donate.getPhoto());
        holder.setPhone(donate.getPhone());

        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), DonateDetailActivity.class);
            intent.putExtra("LAT", donate.getLocation().getLatitude());
            intent.putExtra("LONG", donate.getLocation().getLongitude());
            intent.putExtra("NAME", donate.getName());
            intent.putExtra("PHONE", donate.getPhone());
            intent.putExtra("ADDRESS", donate.getAddress());
            intent.putExtra("BY", donate.getBy());
            intent.putExtra("ID", donate.getId());
            view.getContext().startActivity(intent);
        });
    }

    @NonNull
    @Override
    public DonateRecyclerAdapter.DonateHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DonateRecyclerAdapter.DonateHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_donate_2, parent, false));
    }
}
