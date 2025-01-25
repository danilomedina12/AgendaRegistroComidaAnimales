package com.agendaprecios.main; // Asegúrate de usar el paquete correcto

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AgendaController {

    @GetMapping("/")  // Mapea la URL raíz
    public String index() {
        return "index";  // Este es el nombre de tu archivo HTML sin la extensión (.html)
    }

}
