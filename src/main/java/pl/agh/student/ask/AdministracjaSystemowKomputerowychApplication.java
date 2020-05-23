package pl.agh.student.ask;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AdministracjaSystemowKomputerowychApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdministracjaSystemowKomputerowychApplication.class, args);
    }

}
