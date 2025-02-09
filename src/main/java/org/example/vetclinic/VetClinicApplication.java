package org.example.vetclinic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(scanBasePackages = "org.example.vetclinic")
public class VetClinicApplication {

    public static void main(String[] args) {
        SpringApplication.run(VetClinicApplication.class, args);
    }

}
