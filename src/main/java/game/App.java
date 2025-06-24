package game;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@SpringBootApplication
public class App implements CommandLineRunner {

  @Autowired
  CoreGameService coreGameService;

  public static void main(String[] args) {
    SpringApplication.run(App.class, args);
  }

  @Override
  public void run(String... args) throws IOException {
    Files.createDirectories(Path.of("log"));
    coreGameService.generateStart();
    coreGameService.startGame();
  }
}
