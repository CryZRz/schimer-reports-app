package com.schimer.reportsapp;

import org.flywaydb.core.Flyway;

public class SetupMigrations {

    public static void main(String[] args) {
        var flyway = Flyway.configure()
                .dataSource("jdbc:postgresql://localhost:5432/schimerapp", "postgres", "schimmer1232746")
                .load();

        flyway.migrate();
        System.out.println("Migrations run exited");
    }

}
