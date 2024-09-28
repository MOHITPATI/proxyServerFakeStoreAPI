package com.mohitBackendDev.Spring1.services;

import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

@Controller
public class FakeStoreCategoryServiceImplementation implements CategoryService {
    @Override
    public String getAllCategories() {
        return null;
    }
    @Override
    public String getProductsInCategory(Long categoryId) {
        return null;
    }
}
