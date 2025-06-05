package game;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;

import java.io.IOException;

@SpringBootApplication
public class App implements CommandLineRunner {

    @Autowired
    CoreGameService coreGameService;

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Override
    public void run(String... args) throws IOException {
        coreGameService.generateStart();
        coreGameService.startGame();
    }

}