package com.duoc.backend; // Ajusta el paquete si es necesario

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // Indica que esta clase es un controlador REST
public class HomeController {

    @GetMapping("/") // Mapea este método a la ruta raíz "/" para solicitudes GET
    public String home() {
        return "¡Bienvenido al API Backend de Valpogo! Accede a Swagger UI en /swagger-ui.html";
    }
}
