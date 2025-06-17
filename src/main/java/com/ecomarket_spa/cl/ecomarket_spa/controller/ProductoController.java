package com.ecomarket_spa.cl.ecomarket_spa.controller;

import com.ecomarket_spa.cl.ecomarket_spa.model.Producto;
import com.ecomarket_spa.cl.ecomarket_spa.model.Rol;
import com.ecomarket_spa.cl.ecomarket_spa.service.ProductoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/productos")
@Tag(name = "Productos", description = "Operaciones relacionadas con los productos")
public class ProductoController {
    @Autowired
    private ProductoService productoService;

    @GetMapping
    @Operation(summary = "Listar productos", description = "Obtiene una lista de todos los productos disponibles")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de productos obtenida correctamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Producto.class))),
            @ApiResponse(responseCode = "404", description = "no se encontraron productos")
    })
    public ResponseEntity<List<Producto>> listar(){
        List<Producto> productos =productoService.findAll();
        if (productos.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return  ResponseEntity.ok(productos);
    }
    @PostMapping
    @Operation(summary = "Guardar producto", description = "Crea un nuevo producto en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "producto creado correctmente"),
            @ApiResponse(responseCode = "404", description = "producto no se creo")
    })
    public ResponseEntity<Producto> guardar(@RequestBody Producto producto){
        Producto productoNuevo = productoService.save(producto);
        return ResponseEntity.status(HttpStatus.CREATED).body(producto);
    }
    @GetMapping("/{id}")
    @Operation(summary = "Buscar producto por ID", description = "Obtiene un producto específico por su ID")
    public ResponseEntity<Producto> buscar(@PathVariable Integer id){
        try{
            Producto producto = productoService.findById(id);
            return ResponseEntity.ok(producto);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar producto", description = "Actualiza los detalles de un producto existente")
    public ResponseEntity<Producto> actualizar(@PathVariable Integer id, @RequestBody Producto producto) {
        try {
            Producto prod = productoService.findById(id);
            prod.setId(id);
            prod.setNombre(producto.getNombre());
            prod.setPrecio(producto.getPrecio());
            prod.setStock(producto.getStock());
            prod.setDescripcion(producto.getDescripcion());


            productoService.save(prod);
            return ResponseEntity.ok(producto);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar producto", description = "Elimina un producto del sistema por su ID")
    public ResponseEntity<?> eliminar(@PathVariable Long id){
        try{
            productoService.delete(id);
            return ResponseEntity.noContent().build();
        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

}
