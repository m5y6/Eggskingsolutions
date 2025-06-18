package com.ecomarket_spa.cl.ecomarket_spa.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import com.ecomarket_spa.cl.ecomarket_spa.model.Rol;
import com.ecomarket_spa.cl.ecomarket_spa.controller.RolControllerV2;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.hateoas.CollectionModel;

@Component
public class RolModelAssembler implements RepresentationModelAssembler<Rol, EntityModel<Rol>> {

    @Override
    public EntityModel<Rol> toModel(Rol rol) {
        return EntityModel.of(
                rol,
                linkTo(methodOn(RolControllerV2.class).getRolById(rol.getId())).withSelfRel(),
                linkTo(methodOn(RolControllerV2.class).getAllRoles()).withRel("roles")
        );
    }

    @Override
    public CollectionModel<EntityModel<Rol>> toCollectionModel(Iterable<? extends Rol> entities) {
        List<EntityModel<Rol>> roles = ((List<Rol>) entities).stream()
                .map(this::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(
                roles,
                linkTo(methodOn(RolControllerV2.class).getAllRoles()).withSelfRel()
        );
    }
}