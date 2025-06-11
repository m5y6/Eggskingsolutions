package com.ecomarket_spa.cl.ecomarket_spa.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.*;

@Data
@Table(name="roles")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity

public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,unique = true)
    private String nombre;

    @Column(length = 1000)
    private String descripcion;

    @Column(name="permisos", length = 2000)
    private String permisosJson;
}
