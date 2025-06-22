package com.duoc.backend; // Ajusta el paquete según tu estructura

import java.nio.charset.StandardCharsets;
import java.util.Arrays; // Asegúrate de importar tu clase User (si la usas para autenticación)
import java.util.Date; // Importa tu UserRepository
import java.util.HashMap; // Para convertir objetos a JSON
import java.util.Map;

import javax.crypto.SecretKey;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.duoc.backend.Bus.Bus;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) // Inicia la app en un puerto aleatorio
@AutoConfigureMockMvc // Configura MockMvc para simular peticiones HTTP
@ActiveProfiles("test") // Usa la configuración de src/test/resources/application-test.properties
public class BusControllerIntegrationTest {

    @Container // Esto levantará un contenedor MySQL dedicado para la prueba
    static MySQLContainer<?> mySQLContainer = new MySQLContainer<>("mysql:8.0.37")
            .withDatabaseName("valpogodb")
            .withUsername("testuser")
            .withPassword("testpassword");


    @LocalServerPort
    private int port; // Para obtener el puerto aleatorio en el que corre la app

    @Autowired
    private MockMvc mockMvc; // Para simular las peticiones HTTP

    @Autowired
    private ObjectMapper objectMapper; // Para convertir objetos Java a JSON y viceversa

    @Autowired
    private UserRepository userRepository; // Para interactuar con usuarios en la BD de prueba

    @Autowired
    private WebApplicationContext webApplicationContext; // Para configurar MockMvc

    private String adminToken; // Token JWT para un usuario con rol de administrador

    // Asegúrate de que esta clave secreta sea la misma que usas para generar/validar tokens en tu aplicación
    // ¡EN UN ENTORNO REAL, NUNCA HARÍAS ESTO! LA CLAVE NO DEBERÍA ESTAR EN EL CÓDIGO.
    // Para pruebas, asumimos que puedes generar una clave segura o usar una fija para ambos.
    private static final String SECRET_KEY = "tu_clave_secreta_super_segura_de_al_menos_256_bits_para_jwt_aqui_1234567890"; // Reemplaza con tu clave real
    private SecretKey getSigningKey() {
        byte[] keyBytes = SECRET_KEY.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    @BeforeEach
    void setUp() {
        // Inicializar MockMvc antes de cada test para asegurar que todos los filtros (como JWTAuthorizationFilter) estén aplicados
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        // 1. Obtener un usuario de la BD de prueba (creado por init_test_db.sql)
        // Ojo: Si tu User no tiene un constructor o método findByUsername, ajústalo.
        User testUser = userRepository.findByUsername("testuser").orElse(null);
        if (testUser == null) {
            // Esto no debería pasar si init_test_db.sql es correcto
            throw new RuntimeException("Test user not found in database. Check init_test_db.sql");
        }

        // 2. Generar un token JWT para el usuario de prueba
        // La estructura del token depende de cómo lo implementaste en tu TokenUtils
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", Arrays.asList("ROLE_ADMIN")); // Asigna roles si los usas para autorización

        adminToken = Jwts.builder()
                .setClaims(claims)
                .setSubject(testUser.getUsername()) // El subject es el nombre de usuario
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 horas de validez
                .signWith(getSigningKey(), SignatureAlgorithm.HS256) // Usa la misma clave y algoritmo que tu app
                .compact();
    }

    // --- Pruebas para BusController ---

    @Test
    void testCreateBus() throws Exception {
        Bus newBus = new Bus();
        newBus.setPatente("AB123CD");
        newBus.setCapacidad(40);
        // ... setea otras propiedades necesarias de Bus

        mockMvc.perform(post("/bus")
                        .header("Authorization", "Bearer " + adminToken) // Añade el token JWT
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newBus))) // Convierte el objeto Bus a JSON
                .andExpect(status().isCreated()) // Espera un 201 Created
                .andExpect(jsonPath("$.id").exists()) // Espera que el ID exista en la respuesta
                .andExpect(jsonPath("$.patente").value("AB123CD"));
    }

    @Test
    void testGetAllBuses() throws Exception {
        // Primero, crea un bus para asegurarte de que haya datos
        Bus bus1 = new Bus();
        bus1.setPatente("XYZ789");
        bus1.setCapacidad(30);
        mockMvc.perform(post("/bus")
                .header("Authorization", "Bearer " + adminToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(bus1)))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/bus")
                        .header("Authorization", "Bearer " + adminToken) // Añade el token JWT
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()) // Espera un 200 OK
                .andExpect(jsonPath("$").isArray()) // Espera que la respuesta sea un array JSON
                .andExpect(jsonPath("$[0].patente").value("XYZ789"));
    }

    @Test
    void testGetBusById() throws Exception {
        // Primero, crea un bus para obtener un ID
        Bus busToCreate = new Bus();
        busToCreate.setPatente("ID123");
        busToCreate.setCapacidad(50);
        MvcResult result = mockMvc.perform(post("/bus")
                .header("Authorization", "Bearer " + adminToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(busToCreate)))
                .andExpect(status().isCreated())
                .andReturn();

        // Extrae el ID del bus creado
        Bus createdBus = objectMapper.readValue(result.getResponse().getContentAsString(), Bus.class);
        Long busId = createdBus.getId();

        mockMvc.perform(get("/bus/{id}", busId)
                        .header("Authorization", "Bearer " + adminToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(busId))
                .andExpect(jsonPath("$.patente").value("ID123"));
    }

    @Test
    void testUpdateBus() throws Exception {
        // Primero, crea un bus para actualizar
        Bus busToCreate = new Bus();
        busToCreate.setPatente("OLD123");
        busToCreate.setCapacidad(20);
        MvcResult result = mockMvc.perform(post("/bus")
                .header("Authorization", "Bearer " + adminToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(busToCreate)))
                .andExpect(status().isCreated())
                .andReturn();

        Bus createdBus = objectMapper.readValue(result.getResponse().getContentAsString(), Bus.class);
        Long busId = createdBus.getId();

        // Actualiza el bus
        Bus updatedBus = new Bus();
        updatedBus.setPatente("NEW456");
        updatedBus.setCapacidad(25);

        mockMvc.perform(put("/bus/{id}", busId)
                        .header("Authorization", "Bearer " + adminToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedBus)))
                .andExpect(status().isOk()) // O isNoContent() si tu PUT no devuelve cuerpo
                .andExpect(jsonPath("$.patente").value("NEW456"));
    }

    @Test
    void testDeleteBus() throws Exception {
        // Primero, crea un bus para eliminar
        Bus busToCreate = new Bus();
        busToCreate.setPatente("DEL789");
        busToCreate.setCapacidad(10);
        MvcResult result = mockMvc.perform(post("/bus")
                .header("Authorization", "Bearer " + adminToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(busToCreate)))
                .andExpect(status().isCreated())
                .andReturn();

        Bus createdBus = objectMapper.readValue(result.getResponse().getContentAsString(), Bus.class);
        Long busId = createdBus.getId();

        mockMvc.perform(delete("/bus/{id}", busId)
                        .header("Authorization", "Bearer " + adminToken))
                .andExpect(status().isNoContent()); // Espera un 204 No Content

        // Intenta obtener el bus eliminado para verificar que ya no existe (espera 404)
        mockMvc.perform(get("/bus/{id}", busId)
                        .header("Authorization", "Bearer " + adminToken))
                .andExpect(status().isNotFound()); // Espera un 404 Not Found
    }

    @Test
    void testPublicRootPathAccess() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(content().string("¡Bienvenido al API Backend de Valpogo! Accede a Swagger UI en /swagger-ui.html"));
    }

    @Test
    void testPublicSwaggerPathAccess() throws Exception {
        mockMvc.perform(get("/swagger-ui.html"))
                .andExpect(status().isOk())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("Swagger UI")));
    }
}
