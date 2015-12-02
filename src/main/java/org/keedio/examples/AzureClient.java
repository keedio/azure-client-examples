package org.keedio.examples;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Example app showing the usage of Azure Speech-to-text REST API.
 */
@SpringBootApplication
class AzureClient implements CommandLineRunner {

    @Autowired
    private IService service;

    private static final Logger log = LoggerFactory.getLogger(AzureClient.class);

    public static void main(String[] args) {

        SpringApplication.run(AzureClient.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Object response = service.request(args[0]);
        if (response != null)
            log.info(response.toString());
    }

}
