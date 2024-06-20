package msdiabeterisk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients("msdiabeterisk")
public class MicroserviceDiabetesRiskApplication {

    public static void main(String[] args) {
        SpringApplication.run(MicroserviceDiabetesRiskApplication.class, args);
    }

}
