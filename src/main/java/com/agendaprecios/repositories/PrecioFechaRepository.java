package com.agendaprecios.repositories;

import com.agendaprecios.models.PrecioFecha;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrecioFechaRepository extends JpaRepository<PrecioFecha, Integer>{
    
}
