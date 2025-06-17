package com.ecomarket_spa.cl.ecomarket_spa;

import com.ecomarket_spa.cl.ecomarket_spa.model.Producto;
import com.ecomarket_spa.cl.ecomarket_spa.repository.ProductoRepository;
import com.ecomarket_spa.cl.ecomarket_spa.service.ProductoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProductoServiceTest {
    @Mock
    private ProductoRepository productoRepository;

    @InjectMocks
    private ProductoService productoService;

    private Producto producto;

    /**
     * Inicializa los mocks de Mockito y crea un objeto Producto de ejemplo antes de cada test.
     * Esto asegura que cada prueba se ejecute en un entorno limpio y controlado.
     */
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        producto = Producto.builder()
                .id(1)
                .nombre("Manzana")
                .precio(500)
                .stock(100)
                .descripcion("Manzana roja")
                .build();
    }

    /**
     * Prueba el método findAll() de ProductoService.
     * Verifica que se retorne una lista con los productos existentes en el repositorio.
     */
    @Test
    public void testFindAll() {
        when(productoRepository.findAll()).thenReturn(List.of(producto));
        List<Producto> productos = productoService.findAll();
        assertNotNull(productos);
        assertEquals(1, productos.size());
        assertEquals("Manzana", productos.get(0).getNombre());
    }

    /**
     * Prueba el método findById() de ProductoService.
     * Verifica que se retorne el producto correcto al buscar por su ID.
     */
    @Test
    public void testFindById() {
        when(productoRepository.findById(1L)).thenReturn(Optional.of(producto));
        Producto found = productoService.findById(1L);
        assertNotNull(found);
        assertEquals("Manzana", found.getNombre());
    }

    /**
     * Prueba el método save() de ProductoService.
     * Verifica que se pueda guardar un producto y que los datos sean correctos.
     */
    @Test
    public void testSave() {
        when(productoRepository.save(producto)).thenReturn(producto);
        Producto saved = productoService.save(producto);
        assertNotNull(saved);
        assertEquals(500, saved.getPrecio());
    }

    /**
     * Prueba el método delete() de ProductoService.
     * Verifica que se llame correctamente al repositorio para eliminar un producto por su ID.
     */
    @Test
    public void testDelete() {
        doNothing().when(productoRepository).deleteById(1L);
        productoService.delete(1L);
        verify(productoRepository, times(1)).deleteById(1L);
    }
}
