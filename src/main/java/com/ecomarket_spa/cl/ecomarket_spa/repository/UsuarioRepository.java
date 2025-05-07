package com.ecomarket_spa.cl.ecomarket_spa.repository;

import com.ecomarket_spa.cl.ecomarket_spa.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, String> {

    public Usuario findByRut(String rut);
    public Usuario deleteByRut(String rut);

}
