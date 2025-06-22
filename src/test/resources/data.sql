-- Puedes añadir sentencias SQL para limpiar o pre-cargar la base de datos de prueba
-- Por ejemplo, si quieres borrar datos existentes (aunque create-drop ya lo hace)
-- DELETE FROM bus;
-- DELETE FROM paradero;
-- DELETE FROM pasajero;
-- DELETE FROM ruta;
-- DELETE FROM user;

-- O insertar un usuario para pruebas de autenticación
INSERT INTO user (id, username, password) VALUES (1, 'testuser', '$2a$10$oY75i760z3Nl7l7y.B2uM.wQ8s8Y8Y8Y8Y8Y8Y8Y8Y8Y8Y8Y8Y8Y8Y8Y8Y8Y8Y8Y'); -- 'password' cifrada (ejemplo)
-- La contraseña cifrada '$2a$10$oY75i760z3Nl7l7y.B2uM.wQ8s8Y8Y8Y8Y8Y8Y8Y8Y8Y8Y8Y8Y8Y8Y8Y8Y8Y8Y8Y' es un ejemplo para 'password'.
-- Debes generar un hash de tu contraseña de prueba real usando un BCryptPasswordEncoder, por ejemplo:
-- System.out.println(new BCryptPasswordEncoder().encode("tu_contraseña_secreta"));