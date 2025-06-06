package com.softjuarez.pos_api.controller;

import com.softjuarez.pos_api.dto.CreateProductDTO;
import com.softjuarez.pos_api.dto.ProductDTO;
import com.softjuarez.pos_api.service.ProductService;
import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@Tag(name = "Productos", description = "Gestión de productos")
public class ProductController {

    private final ProductService productService;

    @GetMapping
    @Operation(summary = "Obtiene todos los productos")
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @PostMapping
    @Operation(summary = "Crea un nuevo producto")
    public ResponseEntity<ProductDTO> createProduct(@RequestBody CreateProductDTO dto) {
        ProductDTO saved = productService.createProduct(dto);
        return ResponseEntity.created(URI.create("/api/products/" + saved.getId())).body(saved);
    }
}