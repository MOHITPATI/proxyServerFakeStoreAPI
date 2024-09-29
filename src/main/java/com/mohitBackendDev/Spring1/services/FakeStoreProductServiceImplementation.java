package com.mohitBackendDev.Spring1.services;

import com.mohitBackendDev.Spring1.Dtos.ProductDto;
import com.mohitBackendDev.Spring1.models.Category;
import com.mohitBackendDev.Spring1.models.Product;
import jakarta.annotation.Nullable;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class FakeStoreProductServiceImplementation implements ProductService {
    private RestTemplateBuilder restTemplateBuilder;

    public FakeStoreProductServiceImplementation(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplateBuilder = restTemplateBuilder;
    }

    private <T> ResponseEntity<T> requestForEntity(HttpMethod httpMethod, String url, @Nullable Object request,
                                                   Class<T> responseType, Object... uriVariables) throws RestClientException {
        RestTemplate restTemplate = restTemplateBuilder.requestFactory(
                HttpComponentsClientHttpRequestFactory.class
        ).build();
        RequestCallback requestCallback =restTemplate.httpEntityCallback(request, responseType);
        ResponseExtractor<ResponseEntity<T>> responseExtractor = restTemplate.responseEntityExtractor(responseType);
        return restTemplate.execute(url, httpMethod, requestCallback, responseExtractor, uriVariables);
    }
    private Product convertProductDtoToProduct(ProductDto productDto) {
        Product product = new Product();
        product.setId(productDto.getId());
        product.setTitle(productDto.getTitle());
        product.setPrice(productDto.getPrice());
        Category category = new Category();
        category.setName(productDto.getCategory());
        product.setCategory(category);
        product.setImageUrl(productDto.getImage());
        return product;
    }
    @Override
    public List<Product> getAllProducts() {
        RestTemplate restTemplate = restTemplateBuilder.build();

        ResponseEntity<ProductDto[]> l = restTemplate.getForEntity(
                "https://fakestoreapi.com/products",
                ProductDto[].class
        );

        List<Product> answer = new ArrayList<>();

        for (ProductDto productDto: l.getBody()) {
            answer.add(convertProductDtoToProduct(productDto));
        }

        return answer;
    }

    /*
    Return a Product object with all the details of the fetched product.
    The ID of the category will be null but the name of the category shall be
    correct.
     */
    @Override
    public Product getSingleProduct(Long productId) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<ProductDto> response =  restTemplate.getForEntity(
                "https://fakestoreapi.com/products/{id}",
                ProductDto.class, productId);

        ProductDto productDto = response.getBody();

        return convertProductDtoToProduct(productDto);
    }

    @Override
    public Product addNewProduct(ProductDto product) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<ProductDto> response = restTemplate.postForEntity(
                "https://fakestoreapi.com/products",
                product,
                ProductDto.class
        );

        ProductDto productDto = response.getBody();
        return convertProductDtoToProduct(productDto);
    }

    @Override
    public Product updateProduct(Long productId, Product product) {
//        RestTemplate restTemplate = restTemplateBuilder.requestFactory(
//                HttpComponentsClientHttpRequestFactory.class
//        ).build();
//        restTemplate.pat
        ProductDto fakeStoreProductDto = new ProductDto();
        fakeStoreProductDto.setDescription(product.getDescription());
        fakeStoreProductDto.setImage(product.getImageUrl());
        fakeStoreProductDto.setPrice(product.getPrice());
        fakeStoreProductDto.setTitle(product.getTitle());
        fakeStoreProductDto.setCategory(product.getCategory().getName());
        ResponseEntity<ProductDto> fakeStoreProductDtoResponseEntity = requestForEntity(
                HttpMethod.PATCH,
                "https://fakestoreapi.com/products/{id}",
                fakeStoreProductDto,
                ProductDto.class,
                productId
        );
//        if (fakeStoreProductDtoResponseEntity.getHeaders())
//        ProductDto fakeStoreProductDtoResponse = restTemplate.patchForObject(
//                "https://fakestoreapi.com/products/{id}",
//                fakeStoreProductDto,
//                ProductDto.class,
//                productId
//        );
//        return convertProductDtoToProduct(fakeStoreProductDtoResponse);
        return convertProductDtoToProduct(fakeStoreProductDtoResponseEntity.getBody());
    }

    @Override
    public boolean deleteProduct(Long productId) {
        return false;
    }
}