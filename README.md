# AGRODEMANDA - Sistema de Gestão das Demandas Rurais

> Projeto acadêmico para o Desafio de Inovação Digital (Santa Cruz do Capibaribe - PE) e disciplina de Spring Boot.

## Integrantes e Divisão das Tarefas

Todas as branches já estão criadas no repositório. Cada integrante deve trabalhar na sua branch específica, facilitando a apresentação individual para os professores:

- **Lívia Silva** -> `bloco-1-frontend`: Telas da aplicação em Bootstrap e Thymeleaf (Portal do Agricultor e Painel da Secretaria).
- **Pedro Henrique** -> `bloco-2-backend`: Entidades JPA, banco de dados H2 e repositórios (Concluído).
- **Letícia Albuquerque** -> `bloco-3-logica`: Controllers, regras de negócio e geração de protocolo.
- **Miquéias Almeida** -> `bloco-4-docs`: Documento técnico do Spring Boot, relatório do desafio e slides da apresentação.

## Diretriz do Projeto: Mínimo Funcional

O objetivo principal é entregar uma aplicação **minimamente funcional** que cumpra os requisitos solicitados por ambos os professores (Rafael e Maurício) com o menor retrabalho possível:
- **Professor Maurício:** Aplicação funcional desenvolvida em Spring Boot com persistência de dados.
- **Professor Rafael:** Plataforma digital que atenda ao desafio de gestão de demandas rurais (registro da demanda, acompanhamento e painel da secretaria).

Cada integrante apresentará a sua parte correspondente ao seu bloco e branch.

## Como Trabalhar na sua Branch

As branches já existem no GitHub. Basta clonar e alternar para a sua:

### 1. Clonar o repositório:
```bash
git clone https://github.com/PepeHenrque/AGRODEMANDA.git
cd AGRODEMANDA
```

### 2. Entrar na sua branch específica:
- Lívia: `git checkout bloco-1-frontend`
- Letícia: `git checkout bloco-3-logica`
- Miquéias: `git checkout bloco-4-docs`
- Pedro: `git checkout bloco-2-backend`

### 3. Salvar e enviar suas alterações:
```bash
git add .
git commit -m "descricao simples do que foi feito"
git push origin nome-da-sua-branch
```

*(O README final com a documentação completa da aplicação será gerado na entrega do projeto).*
