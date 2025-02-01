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
import org.springframework.web.bind.annotation.PathVariable;
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
        return "formulario";  // Vista formulario donde se mostrará el formulario
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
        return "exito";  // Vista exito.html que indica que el registro fue exitoso
    }

    @GetMapping("/consultar-precios")
    public String consultarPrecios(@RequestParam(name = "marca", required = false) String marca, Model model) {
        // Obtener todos los productos para la lista desplegable
        List<Producto> productos = productoRepository.findAll();
        model.addAttribute("productos", productos);
    
        if (marca != null && !marca.trim().isEmpty()) {
            // Limpiar el valor de la marca (eliminar coma al principio y al final)
            marca = marca.replaceAll("^,", "").replaceAll(",$", "").trim();  // Eliminar coma al principio y al final

    
            try {
                // Buscar el producto por nombre (marca)
                Producto producto = productoRepository.findByNombre(marca)
                    .orElseThrow(() -> new IllegalArgumentException("Marca no encontrada"));
    
                // Obtener todos los precios del producto, ordenados por fecha ascendente
                List<PrecioFecha> precios = precioFechaRepository.findByProductoOrderByFechaAsc(producto);
    
                if (precios.isEmpty()) {
                    model.addAttribute("mensaje", "No hay precios registrados para esta marca.");
                } else {
                    // Calcular variaciones
                    PrecioFecha ultimoPrecio = precios.get(precios.size() - 1);
                    PrecioFecha anteultimoPrecio = precios.size() > 1 ? precios.get(precios.size() - 2) : null;
    
                    double variacionHistorica = calcularVariacion(precios.get(0).getPrecio(), ultimoPrecio.getPrecio());
                    double variacionUltimoAnteultimo = anteultimoPrecio != null ?
                        calcularVariacion(anteultimoPrecio.getPrecio(), ultimoPrecio.getPrecio()) : 0;
    
                    // Agregar datos al modelo
                    model.addAttribute("producto", producto);
                    model.addAttribute("precios", precios);
                    model.addAttribute("ultimoPrecio", ultimoPrecio.getPrecio());
                    model.addAttribute("variacionHistorica", variacionHistorica);
                    model.addAttribute("variacionUltimoAnteultimo", variacionUltimoAnteultimo);
                }
            } catch (IllegalArgumentException e) {
                // Si no se encuentra la marca, mostrar un mensaje de error
                model.addAttribute("mensaje", "La marca '" + marca + "' no está registrada.");
            }
        }
        return "consultar-precios";  // Vista para consultar precios
    }

    // Método para calcular la variación de precios
    private double calcularVariacion(double precioAnterior, double precioActual) {
        if (precioAnterior == 0) {
            // Si el precio anterior es cero, no podemos calcular la variación
            return 0;
        }
        return ((precioActual - precioAnterior) / precioAnterior) * 100;
    }
}
