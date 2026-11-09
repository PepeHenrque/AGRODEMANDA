# AGRODEMANDA - Sistema de Gestão das Demandas Rurais

> "A demanda da comunidade na palma da mão."

Plataforma digital desenvolvida para conectar agricultores familiares e associações rurais à Secretaria de Agricultura da Prefeitura Municipal de Santa Cruz do Capibaribe - PE, atendendo conjuntamente aos requisitos do Desafio de Inovação Digital (Desafio PE 2026) e da disciplina de desenvolvimento com Spring Boot.

---

## Equipe e Divisao de Responsabilidades

O projeto foi dividido em 4 blocos para garantir uma atuacao bem definida e colaborativa via Git:

| Integrante | Bloco | Branch | Responsabilidade Principal |
| :--- | :--- | :--- | :--- |
| **Lívia Silva** | Bloco 1 (Front-end) | `bloco-1-frontend` | Construcao das telas responsivas (Portal do Agricultor e Painel da Secretaria) com Thymeleaf, HTML5 semantico, CSS customizado e JavaScript de mascaras/validacao. |
| **Pedro Henrique** | Bloco 2 (Back-end) | `bloco-2-backend` | Modelagem da entidade `Demanda`, enum `StatusDemanda`, interface `DemandaRepository` (Spring Data JPA), configuracao do banco H2 em memoria e carga inicial de dados (`data.sql`). |
| **Letícia Albuquerque** | Bloco 3 (Lógica/Integração) | `bloco-3-logica` | Implementacao do `DemandaService` e `DemandaController`, regras de geracao de protocolo (`AGRO-XXXX`), transicao de status e cruzamento entre front-end e banco. |
| **Miquéias Almeida** | Bloco 4 (Entregáveis) | `bloco-4-docs` | Elaboracao do documento tecnico da aplicacao, relatorio de impacto e mitigacao do desafio publico, alem dos slides da apresentacao final. |

---

## Tecnologias Utilizadas

- Linguagem: Java 17 / 21
- Framework: Spring Boot 3.2.5
- Mapeamento Objeto-Relacional: Spring Data JPA / Hibernate
- Banco de Dados: H2 Database (em memoria, sem necessidade de instalacao local)
- Front-end: Thymeleaf, HTML5, CSS3 e JavaScript puro
- Gerenciador de Dependencias: Maven

---

## Funcionalidades da Aplicacao

### 1. Portal do Agricultor (Mobile-First)
- Registro de Demanda: O produtor informa seu nome, telefone/WhatsApp, seleciona sua comunidade rural (Magana, Barra da Cruz, Cacimba de Baixo, Lagoa do Barbosa, etc.), tipo de servico (Preparo de solo, estradas, agua, etc.) e descricao da necessidade.
- Geracao Automatica de Protocolo: Ao enviar a solicitacao, o sistema gera instantaneamente um codigo rastreavel (ex.: AGRO-1042).
- Consulta Rapida de Protocolo: O agricultor pode digitar seu numero de protocolo a qualquer momento na pagina inicial para acompanhar se sua solicitacao esta Pendente ou Concluida.

### 2. Painel de Gestao da Secretaria de Agricultura
- Indicadores em Tempo Real: Cartoes informativos no topo com o total de demandas, quantidade pendente e quantidade concluida.
- Tabela Centralizadora de Atendimentos: Listagem completa de todos os chamados com identificacao do solicitante, contato telefonico, comunidade e necessidade.
- Acao Rapida de Conclusao: Botao direto para marcar uma demanda pendente como finalizada.

---

## Como Executar o Projeto

### Pre-requisitos
- Java JDK 17 ou superior instalado na maquina.
- Uma IDE de sua preferencia (IntelliJ IDEA, VS Code com extensoes Java, ou Eclipse) ou Maven instalado.

### Passo a Passo

1. Clonar o repositorio:
   ```bash
   git clone https://github.com/PepeHenrque/AGRODEMANDA.git
   cd AGRODEMANDA
   ```

2. Executar pela IDE:
   - Abra a pasta do projeto na sua IDE.
   - Aguarde o Maven baixar as dependencias automaticamente.
   - Execute a classe principal: `src/main/java/com/agrodemanda/AgrodemandaApplication.java`.

3. Executar via Linha de Comando (caso possua Maven instalado):
   ```bash
   mvn spring-boot:run
   ```

4. Acessar a Aplicacao:
   - Portal do Agricultor: http://localhost:8081
   - Painel da Secretaria: http://localhost:8081/painel
   - Console do Banco H2: http://localhost:8081/h2-console
     - JDBC URL: `jdbc:h2:mem:agrodemandadb`
     - User Name: `sa`
     - Password: *(deixar em branco)*

---

## Estrutura de Pastas do Projeto

```text
src/main/java/com/agrodemanda/
├── controller/
│   └── DemandaController.java       # Rotas do portal e painel
├── model/
│   ├── Demanda.java                 # Entidade JPA (tabela tb_demandas)
│   └── StatusDemanda.java           # Enum de controle de status
├── repository/
│   └── DemandaRepository.java       # Interface Spring Data JPA
├── service/
│   └── DemandaService.java          # Regras de negocio e protocolo
└── AgrodemandaApplication.java      # Classe de inicializacao Spring Boot

src/main/resources/
├── static/
│   ├── css/style.css                # Estilizacao visual e responsividade
│   └── js/script.js                 # Validacoes e mascaras de campos
├── templates/
│   ├── fragments.html               # Barra de navegacao reutilizavel
│   ├── index.html                   # Pagina do agricultor (registro e consulta)
│   └── painel.html                  # Painel de gestao da secretaria
├── application.properties           # Configuracoes do H2 e JPA
└── data.sql                         # Carga inicial com registros de teste
```
