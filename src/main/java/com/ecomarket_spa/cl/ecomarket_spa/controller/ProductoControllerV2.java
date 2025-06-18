package com.ecomarket_spa.cl.ecomarket_spa.controller;

import com.ecomarket_spa.cl.ecomarket_spa.model.Producto;
import com.ecomarket_spa.cl.ecomarket_spa.service.ProductoService;
import com.ecomarket_spa.cl.ecomarket_spa.assemblers.ProductoModelAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController // Indica que esta clase es un controlador REST
@RequestMapping("/api/v2/productos") // Ruta base para los endpoints de productos
public class ProductoControllerV2 {

    @Autowired
    private ProductoService productoService; // Servicio para la lógica de negocio de productos

    @Autowired
    private ProductoModelAssembler assembler; // Ensamblador HATEOAS para productos

    // Obtiene todos los productos y los retorna con enlaces HATEOAS
    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<Producto>> getAllProductos() {
        List<EntityModel<Producto>> productos = productoService.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(productos,
                linkTo(methodOn(ProductoControllerV2.class).getAllProductos()).withSelfRel());
    }

    // Busca un producto por su ID y lo retorna con enlaces HATEOAS
    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public EntityModel<Producto> getProductoById(@PathVariable Long id) {
        Producto producto = productoService.findById(id);
        return assembler.toModel(producto);
    }

    // Guarda un nuevo producto y retorna el recurso creado con enlaces HATEOAS
    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Producto>> createProducto(@RequestBody Producto producto) {
        Producto newProducto = productoService.save(producto);
        return ResponseEntity
                .created(linkTo(methodOn(ProductoControllerV2.class).getProductoById(newProducto.getId().longValue())).toUri())
                .body(assembler.toModel(newProducto));
    }

    // Actualiza un producto existente y retorna el recurso actualizado con enlaces HATEOAS
    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Producto>> updateProducto(@PathVariable Long id, @RequestBody Producto producto) {
        producto.setId(id.intValue());
        Producto updatedProducto = productoService.save(producto);
        return ResponseEntity.ok(assembler.toModel(updatedProducto));
    }

    // Elimina un producto por su ID
    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<?> deleteProducto(@PathVariable Long id) {
        productoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}