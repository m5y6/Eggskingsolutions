package com.ecomarket_spa.cl.ecomarket_spa.controller;

import com.ecomarket_spa.cl.ecomarket_spa.model.Usuario;
import com.ecomarket_spa.cl.ecomarket_spa.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/usuarios")
@Tag(name = "Usuarios", description = "Operaciones relacionadas con los usuarios")
public class UsuarioController {
    @Autowired

    private UsuarioService usuarioService;

    @GetMapping
    @Operation(summary = "Listar usuarios", description = "Obtiene una lista de todos los usuarios registrados")
    public ResponseEntity<List<Usuario>>listar(){
        List<Usuario>usuarios =usuarioService.findAll();
        if (usuarios.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return  ResponseEntity.ok(usuarios);
    }
    @PostMapping
    @Operation(summary = "Guardar usuario", description = "Crea un nuevo usuario en el sistema")
    public ResponseEntity<Usuario> guardar(@RequestBody Usuario usuario){
        Usuario usuarioNuevo = usuarioService.save(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioNuevo);
    }
    @GetMapping("/{id}")
    @Operation(summary = "Buscar usuario por ID", description = "Obtiene un usuario específico por su ID")
    public ResponseEntity<Usuario> buscar(@PathVariable Long id){
        try{
            Usuario usuario = usuarioService.findById(id);
            return ResponseEntity.ok(usuario);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar usuario", description = "Actualiza los detalles de un usuario existente")
    public ResponseEntity<Usuario> actualizar(@PathVariable Long id, @RequestBody Usuario usuario) {
        try {
            Usuario usu = usuarioService.findById(id);
            usu.setId(id);
            usu.setRut(usu.getRut());
            usu.setRol(usuario.getRol());
            usu.setApellido(usuario.getApellido());
            usu.setNombre(usuario.getNombre());
            usu.setCorreo(usuario.getCorreo());
            usu.setContra(usuario.getContra());

            usuarioService.save(usu);
            return ResponseEntity.ok(usuario);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar usuario", description = "Elimina un usuario del sistema por su ID")
    public ResponseEntity<?> eliminar(@PathVariable long id){
        try{
            usuarioService.delete(id);
            return ResponseEntity.noContent().build();
        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

}
