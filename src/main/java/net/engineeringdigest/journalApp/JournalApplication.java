package net.engineeringdigest.journalApp;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;


// controller ---> service ---> repository(interface)
@SpringBootApplication
@EnableTransactionManagement
public class JournalApplication {

    public static void main(String[] args) {

        SpringApplication.run(JournalApplication.class, args);
    }

    // PlatformTransactionManager(interface)
    // MongoTransactionManager(implementation of PlatformTransactionManager)
    // Use this MongoTransactionManager as the transaction manager for my application, and wire it up using the provided MongoDatabaseFactory.
    @Bean
    public PlatformTransactionManager transactionManager(MongoDatabaseFactory dbFactory){
        return new MongoTransactionManager(dbFactory);
    }

}