package com.agendaprecios.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;



@SpringBootApplication
@ComponentScan(basePackages = {
    "com.agendaprecios.main", 
    "com.agendaprecios.controllers",
    "com.agendaprecios.repositories",
    "com.agendaprecios.models"})
@EnableJpaRepositories(basePackages = "com.agendaprecios.repositories")
@EntityScan("com.agendaprecios.models")
public class AgendaPreciosApplication{
    public static void main(String[] args){
        SpringApplication.run(AgendaPreciosApplication.class, args);
    }
}

