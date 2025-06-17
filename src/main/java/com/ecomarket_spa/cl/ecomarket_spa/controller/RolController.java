package com.ecomarket_spa.cl.ecomarket_spa.controller;

import com.ecomarket_spa.cl.ecomarket_spa.model.Rol;
import com.ecomarket_spa.cl.ecomarket_spa.model.Usuario;
import com.ecomarket_spa.cl.ecomarket_spa.service.RolService;
import com.ecomarket_spa.cl.ecomarket_spa.service.RolService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/roles")
@Tag(name ="roles", description = "Operaciones relacionadas con los roles de usuario")
public class RolController {
    @Autowired
    private RolService rolService;

    @GetMapping
    @Operation(summary = "Listar roles", description = "Obtiene una lista de todos los roles disponibles")
    public ResponseEntity<List<Rol>> listar(){
        List<Rol>roles =rolService.findAll();
        if (roles.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return  ResponseEntity.ok(roles);
    }

    @PostMapping
    @Operation(summary = "Guardar rol", description = "Crea un nuevo rol en el sistema")
    public ResponseEntity<Rol> guardar(@RequestBody Rol rol){
        Rol rolNuevo = rolService.save(rol);
        return ResponseEntity.status(HttpStatus.CREATED).body(rol);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar rol por ID", description = "Obtiene un rol específico por su ID")
    public ResponseEntity<Rol> buscar(@PathVariable Long id){
        try{
            Rol rol = rolService.findById(id);
            return ResponseEntity.ok(rol);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar rol", description = "Actualiza los detalles de un rol existente")
    public ResponseEntity<Rol> actualizar(@PathVariable Long id, @RequestBody Rol rol) {
        try {
            Rol roll = rolService.findById(id);
            roll.setId(id);
            roll.setNombre(rol.getNombre());
            roll.setDescripcion(rol.getDescripcion());
            roll.setPermisosJson(rol.getPermisosJson());

            rolService.save(roll);
            return ResponseEntity.ok(rol);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
