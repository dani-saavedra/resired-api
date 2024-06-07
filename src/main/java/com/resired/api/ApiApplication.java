package com.resired.api;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

@SpringBootApplication
@Slf4j
public class ApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiApplication.class, args);
    }

    @Bean
    FirebaseMessaging firebaseMessaging() throws IOException {

        FirebaseOptions options;

        try {
            options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.getApplicationDefault()).build();
        } catch (Exception ex) {
            log.error("error bootstrapping Firebase admin sdk with default Google credentials: ", ex);
            GoogleCredentials googleCredentials = GoogleCredentials
                .fromStream(new ClassPathResource("service-account.json").getInputStream());

            options = FirebaseOptions.builder()
                .setCredentials(googleCredentials).build();
        }

        FirebaseApp app = FirebaseApp.initializeApp(options);
        return FirebaseMessaging.getInstance(app);
    }

}
