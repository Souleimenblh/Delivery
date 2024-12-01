package com.delivery.deliveryapp.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.delivery.deliveryapp.model.Category;
import com.delivery.deliveryapp.repository.CategoryRepository;
import com.delivery.deliveryapp.repository.CategoryRepositoryImpl;

import java.util.List;

public class CategoryViewModel extends ViewModel {
    private CategoryRepository categoryRepository;
    private LiveData<List<Category>> categories;

    public CategoryViewModel() {
        categoryRepository = new CategoryRepositoryImpl();
        categories = categoryRepository.getCategories();
    }

    public LiveData<List<Category>> getCategories() {
        return categories;
    }
}
