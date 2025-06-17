package com.ecomarket_spa.cl.ecomarket_spa;

import com.ecomarket_spa.cl.ecomarket_spa.model.Rol;
import com.ecomarket_spa.cl.ecomarket_spa.repository.RolRepository;
import com.ecomarket_spa.cl.ecomarket_spa.service.RolService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class RolServiceTest {
    @Mock
    private RolRepository rolRepository;

    @InjectMocks
    private RolService rolService;

    private Rol rol;

    /**
     * Inicializa el mock de Mockito y crea un objeto Rol de ejemplo antes de cada test.
     * Permite que cada prueba tenga un entorno limpio y controlado.
     */
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        rol = Rol.builder()
                .id(1L)
                .nombre("ADMIN")
                .descripcion("Administrador del sistema")
                .permisosJson("{}")
                .build();
    }

    /**
     * Prueba el método findAll() de RolService.
     * Verifica que se retorne una lista con los roles existentes en el repositorio.
     */
    @Test
    public void testFindAll() {
        when(rolRepository.findAll()).thenReturn(List.of(rol));
        List<Rol> roles = rolService.findAll();
        assertNotNull(roles);
        assertEquals(1, roles.size());
        assertEquals("ADMIN", roles.get(0).getNombre());
    }

    /**
     * Prueba el método findById() de RolService.
     * Verifica que se retorne el rol correcto al buscar por su ID.
     */
    @Test
    public void testFindById() {
        when(rolRepository.findById(1L)).thenReturn(Optional.of(rol));
        Rol found = rolService.findById(1L);
        assertNotNull(found);
        assertEquals("ADMIN", found.getNombre());
    }

    /**
     * Prueba el método save() de RolService.
     * Verifica que se pueda guardar un rol y que los datos sean correctos.
     */
    @Test
    public void testSave() {
        when(rolRepository.save(rol)).thenReturn(rol);
        Rol saved = rolService.save(rol);
        assertNotNull(saved);
        assertEquals("Administrador del sistema", saved.getDescripcion());
    }

    /**
     * Prueba el método delete() de RolService.
     * Verifica que se llame correctamente al repositorio para eliminar un rol por su ID.
     */
    @Test
    public void testDelete() {
        doNothing().when(rolRepository).deleteById(1L);
        rolService.delete(1L);
        verify(rolRepository, times(1)).deleteById(1L);
    }
}
