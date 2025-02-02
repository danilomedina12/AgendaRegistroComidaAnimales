package com.agendaprecios.models;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "PrecioFecha")
public class PrecioFecha {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "idProducto", referencedColumnName = "id", nullable = false)
    private Producto producto;

    @Column(nullable = false)
    private double precio;

    @Column(nullable = false)
    private LocalDate fecha;


    public PrecioFecha(){}

    public PrecioFecha(Producto producto, double precio, LocalDate fecha){
        this.producto = producto;
        this.precio = precio;
        this.fecha = fecha;
    }

    public void setIdProducto(Producto producto) {
        this.producto = producto;
    }



    public void setPrecio(double precio2) {
        this.precio = precio2;
    }

    public void setFecha(LocalDate localDate) {
        this.fecha = localDate;
    }

    // getters

    public double getPrecio(){
        return this.precio;
    }

    public LocalDate getFecha(){
        return this.fecha;
    }
}



