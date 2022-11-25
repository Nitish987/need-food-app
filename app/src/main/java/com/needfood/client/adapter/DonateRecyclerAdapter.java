package com.needfood.client.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.needfood.client.DonateDetailActivity;
import com.needfood.client.R;
import com.needfood.client.models.Donate;

public class DonateRecyclerAdapter extends FirestoreRecyclerAdapter<Donate, DonateRecyclerAdapter.DonateHolder> {
    public DonateRecyclerAdapter(FirestoreRecyclerOptions<Donate> options) {
        super(options);
    }

    @NonNull
    @Override
    public DonateHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DonateHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_donate, parent, false));
    }

    @Override
    protected void onBindViewHolder(@NonNull DonateHolder holder, int position, @NonNull Donate donate) {
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

    public static class DonateHolder extends RecyclerView.ViewHolder {
        private final ImageView photo;
        private final TextView name, phone, address;

        public DonateHolder(@NonNull View itemView) {
            super(itemView);
            photo = itemView.findViewById(R.id.photo);
            name = itemView.findViewById(R.id.name);
            phone = itemView.findViewById(R.id.phone);
            address = itemView.findViewById(R.id.address);
        }

        public void setPhoto(String photo) {
            if (photo != null) {
                Glide.with(itemView.getContext()).load(photo).into(this.photo);
            }
        }

        public void setName(String name) {
            this.name.setText(name);
        }

        public void setPhone(String phone) {
            this.phone.setText(phone);
        }

        public void setAddress(String address) {
            this.address.setText(address);
        }
    }
}
