package com.nj.play.docker.jobservice.compose;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.File;

@Testcontainers
public abstract class BaseTest {
    private static final int MONGO_PORT = 27017;
    private static final String MONGO_SERVICE = "mongo";
    private static final String INIT_JS = "/docker-entrypoint-initdb.d/init.js";
    private static final String MONGO_URI_FORMAT = "mongodb://job_user:job_password@%s:%s/job";

    @Container
    private static final DockerComposeContainer<?> compose = new DockerComposeContainer(new File("docker-compose.yml")  );

    @DynamicPropertySource
    static void mongoProperties(DynamicPropertyRegistry registry){
        compose.withEnv("HOST_PORT", "0")
                .withExposedService(MONGO_SERVICE, MONGO_PORT, Wait.forListeningPort())
                .start();
        var host = compose.getServiceHost(MONGO_SERVICE, MONGO_PORT);
        var port = compose.getServicePort(MONGO_SERVICE, MONGO_PORT);
        registry.add("spring.data.mongodb.uri", () -> String.format(MONGO_URI_FORMAT, host, port));
    }
}
