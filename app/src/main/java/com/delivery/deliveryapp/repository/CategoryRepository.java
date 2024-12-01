package com.delivery.deliveryapp.repository;

import androidx.lifecycle.LiveData;
import com.delivery.deliveryapp.model.Category;
import java.util.List;

public interface CategoryRepository {
    LiveData<List<Category>> getCategories();
}
