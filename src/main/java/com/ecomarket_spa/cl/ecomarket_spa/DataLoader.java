package com.ecomarket_spa.cl.ecomarket_spa;

import com.ecomarket_spa.cl.ecomarket_spa.model.Producto;
import com.ecomarket_spa.cl.ecomarket_spa.model.Rol;
import com.ecomarket_spa.cl.ecomarket_spa.model.Usuario;
import com.ecomarket_spa.cl.ecomarket_spa.repository.ProductoRepository;
import com.ecomarket_spa.cl.ecomarket_spa.repository.RolRepository;
import com.ecomarket_spa.cl.ecomarket_spa.repository.UsuarioRepository;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;

@Profile("dev")
@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Override
    public void run(String... args) throws Exception {
        Faker faker = new Faker();
        Random random = new Random();

        // Generar roles
        Rol admin = new Rol();
        admin.setNombre("ADMIN");
        admin.setDescripcion("Administrador del sistema");
        admin.setPermisosJson("{\"permisos\":[\"crear\",\"leer\",\"actualizar\",\"eliminar\"]}");
        rolRepository.save(admin);

        Rol cliente = new Rol();
        cliente.setNombre("CLIENTE");
        cliente.setDescripcion("Usuario cliente");
        cliente.setPermisosJson("{\"permisos\":[\"leer\",\"comprar\"]}");
        rolRepository.save(cliente);

        List<Rol> roles = rolRepository.findAll();

        // Generar usuarios
        for (int i = 0; i < 20; i++) {
            Usuario usuario = new Usuario();
            usuario.setRut(generarRutChilenoValido());
            usuario.setCorreo(faker.internet().emailAddress());
            usuario.setContra(faker.internet().password(8, 12));
            usuario.setNombre(faker.name().firstName());
            usuario.setApellido(faker.name().lastName());
            usuario.setRol(roles.get(random.nextInt(roles.size())));
            usuarioRepository.save(usuario);
        }

        // Generar productos
        for (int i = 0; i < 50; i++) {
            Producto producto = new Producto();
            producto.setNombre(faker.commerce().productName());
            producto.setPrecio(faker.number().numberBetween(1000, 50000));
            producto.setStock(faker.number().numberBetween(0, 100));
            producto.setDescripcion(faker.lorem().sentence(5)); // Descripción de 5 palabras
            productoRepository.save(producto);
        }
    }
    private String generarRutChilenoValido() {
        Random random = new Random();
        int num = random.nextInt(10_000_000, 100_000_000); // 8 dígitos

        // Algoritmo para dígito verificador
        int m = 0, s = 1;
        for (int t = num; t != 0; t /= 10) {
            s = (s + t % 10 * (9 - m++ % 6)) % 11;
        }
        char dv = (s != 0) ? (char)(s + 47) : 'k';

        return String.format("%d-%c", num, dv);
    }
}