package com.example.construction;

import com.example.construction.model.Issuer;
import com.example.construction.repository.IssuerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class ConstructionApplication implements CommandLineRunner {

    private IssuerRepository issuerRepository;

    @Autowired
    public void issuerRepository(IssuerRepository issuerRepository) {
        this.issuerRepository = issuerRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(ConstructionApplication.class, args);
    }

    @Override
    public void run(String... strings) throws Exception {

        List<Issuer> issuers = new ArrayList<>();
        Issuer issuerInitDataFirst = new Issuer();
        issuerInitDataFirst.setName("First issuer");
        Issuer issuerInitDataSecond = new Issuer();
        issuerInitDataSecond.setName("Second issuer");
        Issuer issuerInitDataThird = new Issuer();
        issuerInitDataThird.setName("Third issuer");
        issuers.add(issuerInitDataFirst);
        issuers.add(issuerInitDataSecond);
        issuers.add(issuerInitDataThird);

        issuerRepository.saveAll(issuers);

    }
}
