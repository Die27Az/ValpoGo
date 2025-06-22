package com.duoc.backend;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers // Habilita la integración de Testcontainers con JUnit 5
@SpringBootTest // Inicia un contexto completo de Spring Boot para la prueba
// Deshabilita la autoconfiguración de base de datos en memoria (H2).
// Queremos que Spring Boot intente conectarse al MySQL de Testcontainers.
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BackendApplicationTests {

    // Define el contenedor MySQL que se iniciará para esta prueba.
    // Es 'static' para que se inicie una sola vez para todos los tests de la clase (más eficiente).
    @Container
    // @ServiceConnection (Spring Boot 3.1+) detecta automáticamente las propiedades de conexión
    // y las aplica al contexto de Spring Boot que se está levantando.
    @ServiceConnection
    static MySQLContainer<?> mysqlContainer = new MySQLContainer<>("mysql:8.0.37"); 
    // Asegúrate de que la versión de MySQL (8.0.37 en este ejemplo)
    // sea la misma que usarás en tu docker-compose.yml y que sea compatible con tu dialecto de Hibernate.

    @Test
    void contextLoads() {
        // Este test simplemente verifica que el contexto de Spring Boot se carga sin errores.
        // Si llega a este punto, significa que Spring pudo inicializarse y,
        // gracias a Testcontainers, se conectó exitosamente a la base de datos MySQL en el contenedor.
    }
}