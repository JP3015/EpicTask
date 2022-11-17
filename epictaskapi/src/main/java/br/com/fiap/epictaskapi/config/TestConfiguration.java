package br.com.fiap.epictaskapi.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

import br.com.fiap.epictaskapi.model.Role;
import br.com.fiap.epictaskapi.model.Task;
import br.com.fiap.epictaskapi.model.User;
import br.com.fiap.epictaskapi.repository.TaskRepository;
import br.com.fiap.epictaskapi.repository.UserRepository;

@Configuration
@Profile("dev")
public class TestConfiguration implements CommandLineRunner {

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {

        taskRepository.saveAll(List.of(
            new Task("Modelar BD", "Modelar as tabelas do banco", 50, 100),
            new Task("Protipar", "Prototipo das telas do site", 30, 100),
            new Task("Modelar BD", "Modelar as tabelas do banco", 10, 100),
            new Task("Protipar", "Prototipo das telas do site", 90, 100),
            new Task("Modelar BD", "Modelar as tabelas do banco", 70, 100),
            new Task("Protipar", "Prototipo das telas do site"),
            new Task("Modelar BD", "Modelar as tabelas do banco"),
            new Task("Protipar", "Prototipo das telas do site")
        ));

        userRepository.save(
            new User()
                .name("João")
                .email("joao@fiap.com")
                .password(passwordEncoder.encode("123"))
                .githubUsername("joaocarloslima")
                .withRole(new Role("ROLE_USER"))
        );

        userRepository.save(
            new User()
                .name("Maria")
                .email("maria@fiap.com")
                .password(passwordEncoder.encode("123"))
                .githubUsername("maria")
                .withRole(new Role("ROLE_ADMIN"))
        );
        
    }
    
}
