/*package com.pawshelt;

import com.pawshelt.model.Animal;
import com.pawshelt.model.EstadoAnimal;
import com.pawshelt.model.TipoAnimal;
import com.pawshelt.repository.AnimalRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataLoader {

    @Bean
    public CommandLineRunner initData(AnimalRepository repository) {
        return args -> {
            Animal animal1 = new Animal();
            animal1.setNombre("Lola");
            animal1.setRaza("Bulldog Frances");
            animal1.setEdad(14);
            animal1.setTipo(TipoAnimal.PERRO);
            animal1.setEstado(EstadoAnimal.ADOPTADO);
            animal1.setFotoPerfilUrl("/images/loli.jpg");

            Animal animal2 = new Animal();
            animal2.setNombre("Groot");
            animal2.setRaza("Comun Europeo");
            animal2.setEdad(3);
            animal2.setTipo(TipoAnimal.GATO);
            animal2.setEstado(EstadoAnimal.EN_CASA_DE_ACOGIDA);
            animal2.setFotoPerfilUrl("/images/groot.jpg");

            Animal animal3 = new Animal();
            animal3.setNombre("Sena");
            animal3.setRaza("Teckel");
            animal3.setEdad(2);
            animal3.setTipo(TipoAnimal.PERRO);
            animal3.setEstado(EstadoAnimal.EN_ADOPCION);
            animal3.setFotoPerfilUrl("/images/sena.jpg");

            Animal animal4 = new Animal();
            animal4.setNombre("Yako");
            animal4.setRaza("Bulldog Frances");
            animal4.setEdad(11);
            animal4.setTipo(TipoAnimal.PERRO);
            animal4.setEstado(EstadoAnimal.EN_ADOPCION);
            animal4.setFotoPerfilUrl("/images/yako.jpg");

            Animal animal5 = new Animal();
            animal5.setNombre("Milo");
            animal5.setRaza("Mezcla");
            animal5.setEdad(2);
            animal5.setTipo(TipoAnimal.PERRO);
            animal5.setEstado(EstadoAnimal.EN_ADOPCION);
            animal5.setFotoPerfilUrl("/images/milo.jpg");

            Animal animal6 = new Animal();
            animal6.setNombre("Kala");
            animal6.setRaza("Gato naranja loco");
            animal6.setEdad(1);
            animal6.setTipo(TipoAnimal.GATO);
            animal6.setEstado(EstadoAnimal.EN_CASA_DE_ACOGIDA);
            animal6.setFotoPerfilUrl("/images/kala.jpg");

            Animal animal7 = new Animal();
            animal7.setNombre("Hodor");
            animal7.setRaza("Bulldog Frances");
            animal7.setEdad(1);
            animal7.setTipo(TipoAnimal.PERRO);
            animal7.setEstado(EstadoAnimal.EN_CASA_DE_ACOGIDA);
            animal7.setFotoPerfilUrl("/images/hodor.jpg");

            repository.save(animal1);
            repository.save(animal2);
            repository.save(animal3);
            repository.save(animal4);
            repository.save(animal5);
            repository.save(animal6);
            repository.save(animal7);
        };
    }
}*/
