package com.example;

import java.util.Arrays;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class LazyLoadingSpringDemoApplication {
    
    static List<String> fnames = Arrays.asList("Jack", "Cloe", "Kim", "Michelle");

    public static void main(String[] args) {
        SpringApplication.run(LazyLoadingSpringDemoApplication.class, args);
    }

    @Bean
    public CommandLineRunner loadData(PersonRepository repository) {
        return (args) -> {
            for(int i = 0; i < 100; i++) {
                Person person = new Person();
                person.setFirstName(fnames.get(i%fnames.size()));
                person.setLastName("Lastname"+i);
                repository.save(person);
            }

        };
    }
}
