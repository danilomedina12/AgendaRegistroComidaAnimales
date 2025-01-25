package com.agendaprecios.models;

import jakarta.persistence.*;


@Entity
@Table(name = "producto")

public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String nombre;   

    // Métodos y constructores

    public Producto(){}

    public Producto (String nombre){
        this.nombre = nombre;
    }

    public int getId(){
        return this.id;
    }

    public void setId(int id){
        this.id = id; 
    }

    public String getNombre(){
        return this.nombre;
    }

    public void setNombre(String nombre){
        this.nombre = nombre;
    }



}
