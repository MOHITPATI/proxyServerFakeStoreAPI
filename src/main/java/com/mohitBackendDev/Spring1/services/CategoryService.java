package com.mohitBackendDev.Spring1.services;

public interface CategoryService {
    String getAllCategories();
    String getProductsInCategory(Long categoryId);
}
