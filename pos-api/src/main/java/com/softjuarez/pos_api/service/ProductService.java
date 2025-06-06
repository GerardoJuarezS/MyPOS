package com.softjuarez.pos_api.service;

import org.modelmapper.ModelMapper;
import com.softjuarez.pos_api.dto.CreateProductDTO;
import com.softjuarez.pos_api.dto.ProductDTO;
import com.softjuarez.pos_api.model.Product;
import com.softjuarez.pos_api.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll().stream()
                .map(product -> modelMapper.map(product, ProductDTO.class))
                .collect(Collectors.toList());
    }

    public ProductDTO createProduct(CreateProductDTO dto) {
        Product product = modelMapper.map(dto, Product.class);
        product = productRepository.save(product);
        return modelMapper.map(product, ProductDTO.class);
    }
}