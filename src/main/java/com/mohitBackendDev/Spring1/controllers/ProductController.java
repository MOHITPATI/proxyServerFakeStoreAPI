package com.mohitBackendDev.Spring1.controllers;

import com.mohitBackendDev.Spring1.Dtos.ProductDto;
import com.mohitBackendDev.Spring1.models.Product;
import com.mohitBackendDev.Spring1.models.Category;
import com.mohitBackendDev.Spring1.services.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    private ProductService productService;

    public ProductController( ProductService productService) {
        this.productService = productService;
    }

    @GetMapping()
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{productId}")
    public Product getSingleProduct(@PathVariable("productId") Long productId) {
//        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
//
//        headers.add(
//                "auth-token", "noaccess4uheyhey"
//        );

//        ResponseEntity<Product> response = new ResponseEntity(
//                productService.getSingleProduct(productId),
//                headers,
//                HttpStatus
//        );

        return productService.getSingleProduct(productId);
//        GetSingleProductResponseDto responseDto = new GetSingleProductResponseDto();
//        responseDto.setProduct(
//              return  ;
//        );

//        return responseDto;
    }


    @PostMapping()
    public ResponseEntity<Product> addNewProduct(@RequestBody ProductDto product) {

        Product newProduct = productService.addNewProduct(
                product
        );

        ResponseEntity<Product> response = new ResponseEntity<>(newProduct, HttpStatus.OK);

        return response;
    }

    @PatchMapping("/{productId}")
    public Product updateProduct(@PathVariable("productId") Long productId,
                                 @RequestBody ProductDto productDto) {
        Product product = new Product();
        product.setId(productDto.getId());
        product.setCategory(new Category());
        product.getCategory().setName(productDto.getCategory());
        product.setTitle(productDto.getTitle());
        product.setPrice(productDto.getPrice());
        product.setDescription(productDto.getDescription());
        return productService.updateProduct(productId, product);
    }

    @DeleteMapping("/{productId}")
    public String deleteProduct(@PathVariable("productId") Long productId) {
        return "Deleting a product with id: " + productId;
    }
}
