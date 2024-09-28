package com.mohitBackendDev.Spring1.services;

import com.mohitBackendDev.Spring1.Dtos.ProductDto;
import com.mohitBackendDev.Spring1.models.Product;

import java.util.List;

public interface ProductService {
    List<Product> getAllProducts();

    Product getSingleProduct(Long productId);

    Product addNewProduct(ProductDto product);

    /*
    Product object has only those fields filled which need to be updated.
    Everything else is null
     */
    Product updateProduct(Long productId, Product product);
    // if (product.getImageUrl() != null) {
    //
    // }

    boolean deleteProduct(Long productId);
}

// update product with id 123
// {
//   name: iPhone 15
// }
