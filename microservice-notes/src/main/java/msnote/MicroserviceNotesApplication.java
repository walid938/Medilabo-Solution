package msnote;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;


@SpringBootApplication
public class MicroserviceNotesApplication {

    public static void main(String[] args) {
        SpringApplication.run(MicroserviceNotesApplication.class, args);
    }

}
