// Ensamblador HATEOAS para la entidad Usuario
package com.ecomarket_spa.cl.ecomarket_spa.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import com.ecomarket_spa.cl.ecomarket_spa.model.Usuario;
import com.ecomarket_spa.cl.ecomarket_spa.controller.UsuarioControllerV2;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.hateoas.CollectionModel;

@Component // Indica que es un componente de Spring
public class UsuarioModelAssembler implements RepresentationModelAssembler<Usuario, EntityModel<Usuario>> {

    // Convierte un objeto Usuario en un EntityModel con enlaces HATEOAS
    @Override
    public EntityModel<Usuario> toModel(Usuario usuario) {
        return EntityModel.of(
                usuario,
                // Enlace al recurso propio (self)
                linkTo(methodOn(UsuarioControllerV2.class).getUsuarioById(usuario.getId())).withSelfRel(),
                // Enlace a la colección de usuarios
                linkTo(methodOn(UsuarioControllerV2.class).getAllUsuarios()).withRel("usuarios")
        );
    }

    // Convierte una colección de usuarios en una CollectionModel con enlaces HATEOAS
    @Override
    public CollectionModel<EntityModel<Usuario>> toCollectionModel(Iterable<? extends Usuario> entities) {
        // Transforma cada usuario en un EntityModel
        List<EntityModel<Usuario>> usuarios = ((List<Usuario>) entities).stream()
                .map(this::toModel)
                .collect(Collectors.toList());
        // Devuelve la colección con un enlace a sí misma
        return CollectionModel.of(
                usuarios,
                linkTo(methodOn(UsuarioControllerV2.class).getAllUsuarios()).withSelfRel()
        );
    }
}