# AGRODEMANDA - Sistema Municipal de Gestão das Demandas Rurais

> "A demanda da comunidade na palma da mão."

Projeto desenvolvido para atender conjuntamente ao **Desafio de Inovação Digital 2026 (Prefeitura Municipal de Santa Cruz do Capibaribe - PE / Secretaria de Agricultura)** e ao projeto prático de **Spring Boot**.

## Equipe
- Pedro Henrique (Bloco 2 - Back-end e Persistência)
- Miquéias
- Letícia
- Líbia

## Divisão dos Blocos
- **Bloco 1 (branch `bloco-1-frontend`)**: Telas em Bootstrap e Thymeleaf (Portal Mobile do Agricultor e Painel da Secretaria).
- **Bloco 2 (branch `bloco-2-backend`)**: Estrutura Spring Boot, Entidades JPA, H2 Database e Repositórios.
- **Bloco 3 (branch `bloco-3-logica`)**: Controllers, Services, geração de protocolo e regras de transição de status.
- **Bloco 4 (branch `bloco-4-docs`)**: Documento técnico do Spring Boot, relatório de impacto e slides da apresentação.

## Tecnologias
- Java 21
- Spring Boot 3
- Spring Data JPA
- Thymeleaf
- H2 Database (Console em `/h2-console`)
- Bootstrap 5

## Como Executar
1. Abra o projeto na sua IDE de preferência (IntelliJ, VS Code, Eclipse).
2. Execute a classe principal `AgrodemandaApplication.java`.
3. Acesse no navegador:
   - Aplicação: `http://localhost:8080`
   - Console do H2: `http://localhost:8080/h2-console` (JDBC URL: `jdbc:h2:mem:agrodemandadb`, Usuário: `sa`, Senha: em branco).
