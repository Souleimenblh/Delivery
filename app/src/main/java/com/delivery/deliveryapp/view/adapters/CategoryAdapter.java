// File: com/delivery/deliveryapp/view/adapters/CategoryAdapter.java

package com.delivery.deliveryapp.view.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.delivery.deliveryapp.R;
import com.delivery.deliveryapp.model.Category;
import com.bumptech.glide.Glide;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    private List<Category> categoryList;
    private Context context;
    private OnCategoryClickListener categoryClickListener;

    public interface OnCategoryClickListener {
        void onCategoryClick(Category category);
    }

    // Correct the constructor to accept OnCategoryClickListener
    public CategoryAdapter(Context context, List<Category> categoryList, OnCategoryClickListener categoryClickListener) {
        this.context = context;
        this.categoryList = categoryList;
        this.categoryClickListener = categoryClickListener;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.category, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Category category = categoryList.get(position);
        holder.name.setText(category.getName());
        Glide.with(context).load(category.getIcon()).into(holder.icon);
        holder.isNew.setVisibility(category.getNew() ? View.VISIBLE : View.GONE);

        holder.itemView.setOnClickListener(v -> {
            if (categoryClickListener != null) {
                categoryClickListener.onCategoryClick(category);
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public static class CategoryViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        ImageView icon, isNew;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            icon = itemView.findViewById(R.id.icon);
            isNew = itemView.findViewById(R.id.isNew);
        }
    }
}

