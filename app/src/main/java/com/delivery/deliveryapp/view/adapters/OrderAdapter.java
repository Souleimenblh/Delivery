package com.delivery.deliveryapp.view.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.delivery.deliveryapp.R;
import com.delivery.deliveryapp.model.Order;
import com.squareup.picasso.Picasso;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {
    private List<Order> orders;

    public OrderAdapter(List<Order> orders) {
        this.orders = orders;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        Order order = orders.get(position);

        Picasso.get().load(order.getProduct().getIcon()).error(R.drawable.discover).into(holder.productImageView);
        holder.bind(order);
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    static class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView productName, orderDate, price;
        public ImageView productImageView;

        OrderViewHolder(View itemView) {
            super(itemView);
            productImageView = itemView.findViewById(R.id.icon);
            productName = itemView.findViewById(R.id.orderName);
            orderDate = itemView.findViewById(R.id.orderDateTV);
            price = itemView.findViewById(R.id.price);
        }

        void bind(Order order) {
            productName.setText(order.getProduct().getName());
            orderDate.setText(order.getOrderDate().toString());
            price.setText(order.getProduct().getPrice().toString());
        }
    }
}
