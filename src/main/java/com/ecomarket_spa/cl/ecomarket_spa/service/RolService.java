package com.ecomarket_spa.cl.ecomarket_spa.service;

import com.ecomarket_spa.cl.ecomarket_spa.model.Rol;
import com.ecomarket_spa.cl.ecomarket_spa.model.Rol;
import com.ecomarket_spa.cl.ecomarket_spa.repository.RolRepository;
import com.ecomarket_spa.cl.ecomarket_spa.repository.RolRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class RolService {
    @Autowired
    private RolRepository rolRepository;
    public List<Rol> findAll(){
        return rolRepository.findAll();
    }
    public Rol findById(long id){
        return rolRepository.findById(id).get();
    }
    public Rol save(Rol usuario){
        return rolRepository.save(usuario);
    }
    public void delete(Long id){
        rolRepository.deleteById(id);
    }
}
