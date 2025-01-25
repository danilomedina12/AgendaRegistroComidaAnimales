package com.agendaprecios.controllers;

import com.agendaprecios.models.Producto;
import com.agendaprecios.models.PrecioFecha;
import com.agendaprecios.repositories.ProductoRepository;
import com.agendaprecios.repositories.PrecioFechaRepository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ProductoPrecioController {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private PrecioFechaRepository precioFechaRepository;

    // Mostrar el formulario
    @GetMapping("/formulario")
    public String mostrarFormulario(Model model) {
        // Obtener todos los productos de la base de datos
        List<Producto> productos = productoRepository.findAll();
        model.addAttribute("productos", productos);
        return "formulario";  // Nombre de la vista donde se mostrará el formulario
    }

    // Procesar el formulario
    @PostMapping("/producto-precio")
    public String registrarProductoPrecio(@RequestParam(required = false) Integer productoId,
                                          @RequestParam(required = false) String nombreProducto,
                                          @RequestParam double precio,
                                          @RequestParam String fecha) {

        Producto producto = null;

        // Verificar si se seleccionó un producto existente
        if (productoId != null && productoId > 0) {
            producto = productoRepository.findById(productoId)
                    .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));
        } 
        // Si no se seleccionó un producto, creamos uno nuevo
        else if (nombreProducto != null && !nombreProducto.isEmpty()) {
            producto = new Producto(nombreProducto);
            productoRepository.save(producto);  // Guardar el nuevo producto en la base de datos
        } 
        // Si no hay producto ni nombre proporcionado, lanzamos un error
        else {
            throw new IllegalArgumentException("Debe seleccionar un producto o ingresar uno nuevo.");
        }

        // Crear el registro de precio con la fecha
        PrecioFecha precioFecha = new PrecioFecha();
        precioFecha.setIdProducto(producto);
        precioFecha.setPrecio(precio);
        precioFecha.setFecha(LocalDate.parse(fecha));

        // Guardar el registro de precio en la base de datos
        precioFechaRepository.save(precioFecha);

        // Redirigir a la página de formulario o de éxito
        return "redirect:/exito";  // Redirigir al formulario de nuevo para registrar otro precio
    }

    // Agregar un mapeo para /exito
    @GetMapping("/exito")
    public String exito() {
        return "exito";  // Nombre de la vista exito.html
    }   
}
