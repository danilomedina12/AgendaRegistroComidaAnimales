package com.agendaprecios.repositories;

import com.agendaprecios.models.PrecioFecha;
import com.agendaprecios.models.Producto;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrecioFechaRepository extends JpaRepository<PrecioFecha, Integer>{
 
    List<PrecioFecha> findByProductoOrderByFechaAsc(Producto producto);

}
