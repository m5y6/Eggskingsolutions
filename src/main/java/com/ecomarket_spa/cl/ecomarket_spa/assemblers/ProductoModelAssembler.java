package com.ecomarket_spa.cl.ecomarket_spa.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import com.ecomarket_spa.cl.ecomarket_spa.model.Producto;
import com.ecomarket_spa.cl.ecomarket_spa.controller.ProductoControllerV2;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.hateoas.CollectionModel;

@Component // Indica que es un componente de Spring
public class ProductoModelAssembler implements RepresentationModelAssembler<Producto, EntityModel<Producto>> {

    // Convierte un objeto Producto en un EntityModel con enlaces HATEOAS
    @Override
    public EntityModel<Producto> toModel(Producto producto) {
        return EntityModel.of(
                producto,
                // Enlace al recurso propio (self)
                linkTo(methodOn(ProductoControllerV2.class).getProductoById(producto.getId().longValue())).withSelfRel(),
                // Enlace a la colección de productos
                linkTo(methodOn(ProductoControllerV2.class).getAllProductos()).withRel("productos")
        );
    }

    // Convierte una colección de productos en una CollectionModel con enlaces HATEOAS
    @Override
    public CollectionModel<EntityModel<Producto>> toCollectionModel(Iterable<? extends Producto> entities) {
        // Transforma cada producto en un EntityModel
        List<EntityModel<Producto>> productos = ((List<Producto>) entities).stream()
                .map(this::toModel)
                .collect(Collectors.toList());
        // Devuelve la colección con un enlace a sí misma
        return CollectionModel.of(
                productos,
                linkTo(methodOn(ProductoControllerV2.class).getAllProductos()).withSelfRel()
        );
    }
}