package com.agrodemanda;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * PONTO DE PARTIDA DO SISTEMA
 * 
 * A anotacao @SpringBootApplication ativa tres coisas de uma vez:
 * 1. Configuracao automatica do Spring (servidor web Tomcat, conexao com banco, etc).
 * 2. Escaneamento dos pacotes (procura controllers, services, repositories).
 * 3. Habilita a injecao de dependencias (@Autowired).
 */
@SpringBootApplication
public class AgrodemandaApplication {

    public static void main(String[] args) {
        // Inicia o servidor web Tomcat e coloca o sistema no ar na porta 8081
        SpringApplication.run(AgrodemandaApplication.class, args);
    }

}
