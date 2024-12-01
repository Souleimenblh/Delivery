package com.delivery.deliveryapp.view.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.delivery.deliveryapp.R;
import com.delivery.deliveryapp.model.Address;

import java.util.List;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.AddressViewHolder> {
    private List<Address> addresses;

    public AddressAdapter(List<Address> addresses) {
        this.addresses = addresses;
    }

    @NonNull
    @Override
    public AddressViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.address, parent, false);
        return new AddressViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddressViewHolder holder, int position) {
        Address address = addresses.get(position);
        holder.title.setText(address.getLabel());
        holder.address.setText(address.getAddress());
        if (address.getLabel().equals("Home")) {
            holder.icon.setImageResource(R.drawable.home);
        } else if (address.getLabel().equals("Work")) {
            holder.icon.setImageResource(R.drawable.office);
        }
    }

    @Override
    public int getItemCount() {
        return addresses.size();
    }

    public static class AddressViewHolder extends RecyclerView.ViewHolder {
        TextView title, address;
        ImageView icon;

        public AddressViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            address = itemView.findViewById(R.id.address);
            icon = itemView.findViewById(R.id.icon);
        }
    }
}