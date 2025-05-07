package com.ecomarket_spa.cl.ecomarket_spa.repository;

import com.ecomarket_spa.cl.ecomarket_spa.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
}
