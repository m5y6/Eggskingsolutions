package com.ecomarket_spa.cl.ecomarket_spa;

import com.ecomarket_spa.cl.ecomarket_spa.model.Rol;
import com.ecomarket_spa.cl.ecomarket_spa.model.Usuario;
import com.ecomarket_spa.cl.ecomarket_spa.repository.UsuarioRepository;
import com.ecomarket_spa.cl.ecomarket_spa.service.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UsuarioServiceTest {
    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    private Usuario usuario;
    private Rol rol;

    /**
     * Inicializa los mocks de Mockito y crea un objeto Usuario de ejemplo antes de cada test.
     * Esto asegura que cada prueba se ejecute en un entorno limpio y controlado.
     */
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        rol = Rol.builder().id(1L).nombre("ADMIN").descripcion("Administrador").permisosJson("{}") .build();
        usuario = Usuario.builder()
                .id(1L)
                .rut("12345678-9")
                .correo("test@correo.com")
                .contra("password")
                .nombre("Juan")
                .apellido("Pérez")
                .rol(rol)
                .build();
    }

    /**
     * Prueba el método findAll() de UsuarioService.
     * Verifica que se retorne una lista con los usuarios existentes en el repositorio.
     */
    @Test
    public void testFindAll() {
        when(usuarioRepository.findAll()).thenReturn(List.of(usuario));
        List<Usuario> usuarios = usuarioService.findAll();
        assertNotNull(usuarios);
        assertEquals(1, usuarios.size());
        assertEquals("Juan", usuarios.get(0).getNombre());
    }

    /**
     * Prueba el método findById() de UsuarioService.
     * Verifica que se retorne el usuario correcto al buscar por su ID.
     */
    @Test
    public void testFindById() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        Usuario found = usuarioService.findById(1L);
        assertNotNull(found);
        assertEquals("12345678-9", found.getRut());
    }

    /**
     * Prueba el método save() de UsuarioService.
     * Verifica que se pueda guardar un usuario y que los datos sean correctos.
     */
    @Test
    public void testSave() {
        when(usuarioRepository.save(usuario)).thenReturn(usuario);
        Usuario saved = usuarioService.save(usuario);
        assertNotNull(saved);
        assertEquals("test@correo.com", saved.getCorreo());
    }

    /**
     * Prueba el método delete() de UsuarioService.
     * Verifica que se llame correctamente al repositorio para eliminar un usuario por su ID.
     */
    @Test
    public void testDelete() {
        doNothing().when(usuarioRepository).deleteById(1L);
        usuarioService.delete(1L);
        verify(usuarioRepository, times(1)).deleteById(1L);
    }
}
