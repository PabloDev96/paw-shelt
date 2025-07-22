package com.pawshelt;

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
            animal1.setFotoPerfilUrl("url relativa del frontend aquí");

            Animal animal2 = new Animal();
            animal2.setNombre("Groot");
            animal2.setRaza("Comun Europeo");
            animal2.setEdad(3);
            animal2.setTipo(TipoAnimal.GATO);
            animal2.setEstado(EstadoAnimal.EN_CASA_DE_ACOGIDA);
            animal2.setFotoPerfilUrl("url relativa del frontend aquí");


            Animal animal3 = new Animal();
            animal3.setNombre("Sena");
            animal3.setRaza("Teckel");
            animal3.setEdad(2);
            animal3.setTipo(TipoAnimal.PERRO);
            animal3.setEstado(EstadoAnimal.EN_ADOPCION);
            animal3.setFotoPerfilUrl("url relativa del frontend aquí");

            repository.save(animal1);
            repository.save(animal2);
            repository.save(animal3);
        };
    }
}

