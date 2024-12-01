package com.delivery.deliveryapp.repository;

import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.delivery.deliveryapp.model.Category;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class CategoryRepositoryImpl implements CategoryRepository{
    private DatabaseReference databaseReference;
    public CategoryRepositoryImpl() {
        databaseReference = FirebaseDatabase.getInstance().getReference("Categories");
    }
    @Override
    public LiveData<List<Category>> getCategories() {
        MutableLiveData<List<Category>> data = new MutableLiveData<>();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Category> categories = new ArrayList<>();
                for (DataSnapshot categorySnapshot : dataSnapshot.getChildren()) {
                    Category category = categorySnapshot.getValue(Category.class);
                    if (category != null) {
                        categories.add(category);
                    }
                }
                data.setValue(categories);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                data.setValue(null);
                Toast.makeText(null, "Error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        return data;
    }
}
