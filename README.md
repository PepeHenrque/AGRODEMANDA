# AGRODEMANDA - Sistema de Gestão das Demandas Rurais

> **"A demanda da comunidade na palma da mão."**

Plataforma digital desenvolvida para conectar os agricultores familiares e associações rurais à Secretaria de Agricultura da Prefeitura Municipal de Santa Cruz do Capibaribe - PE, atendendo conjuntamente aos requisitos do **Desafio de Inovação Digital (Desafio PE 2026)** e da disciplina de desenvolvimento com **Spring Boot**.

---

## 👥 Equipe e Divisão de Responsabilidades

O projeto foi dividido em 4 blocos para garantir uma atuação bem definida e colaborativa via Git:

| Integrante | Bloco | Branch | Responsabilidade Principal |
| :--- | :--- | :--- | :--- |
| **Lívia Silva** | Bloco 1 (Front-end) | `bloco-1-frontend` | Construção das telas responsivas (Portal do Agricultor e Painel da Secretaria) com Thymeleaf, HTML5 semântico, CSS customizado e JavaScript de máscaras/validação. |
| **Pedro Henrique** | Bloco 2 (Back-end) | `bloco-2-backend` | Modelagem da entidade `Demanda`, enum `StatusDemanda`, interface `DemandaRepository` (Spring Data JPA), configuração do banco H2 em memória e carga inicial de dados (`data.sql`). |
| **Letícia Albuquerque** | Bloco 3 (Lógica/Integração) | `bloco-3-logica` | Implementação do `DemandaService` e `DemandaController`, regras de geração de protocolo (`AGRO-XXXX`), transição de status e cruzamento entre front-end e banco. |
| **Miquéias Almeida** | Bloco 4 (Entregáveis) | `bloco-4-docs` | Elaboração do documento técnico da aplicação, relatório de impacto e mitigação do desafio público, além dos slides da apresentação final. |

---

## 🛠️ Tecnologias Utilizadas

- **Linguagem:** Java 17 / 21
- **Framework:** Spring Boot 3.2.5
- **Mapeamento Objeto-Relacional:** Spring Data JPA / Hibernate
- **Banco de Dados:** H2 Database (em memória, sem necessidade de instalação local)
- **Front-end:** Thymeleaf + HTML5 + CSS3 + JavaScript puro (Vanilla JS)
- **Gerenciador de Dependências:** Maven

---

## ⚙️ Funcionalidades da Aplicação

### 1. Portal do Agricultor (Mobile-First)
- **Registro de Demanda:** O produtor informa seu nome, telefone/WhatsApp, seleciona sua comunidade rural (*Magana, Barra da Cruz, Cacimba de Baixo, Lagoa do Barbosa, etc.*), tipo de serviço (*Preparo de solo, estradas, água, etc.*) e descrição da necessidade.
- **Geração Automática de Protocolo:** Ao enviar a solicitação, o sistema gera instantaneamente um código rastreável (ex.: `AGRO-1042`).
- **Consulta Rápida de Protocolo:** O agricultor pode digitar seu número de protocolo a qualquer momento na página inicial para acompanhar se sua solicitação está *Pendente* ou *Concluída*.

### 2. Painel de Gestão da Secretaria de Agricultura
- **Indicadores em Tempo Real:** Cartões informativos no topo com o total de demandas, quantidade pendente e quantidade concluída.
- **Tabela Centralizadora de Atendimentos:** Listagem completa de todos os chamados com identificação do solicitante, contato telefônico, comunidade e necessidade.
- **Ação Rápida de Conclusão:** Botão direto para marcar uma demanda pendente como finalizada.

---

## 🚀 Como Executar o Projeto

### Pré-requisitos
- **Java JDK 17 ou superior** instalado na máquina.
- Uma IDE de sua preferência (IntelliJ IDEA, VS Code com extensões Java, ou Eclipse) ou Maven instalado.

### Passo a Passo

1. **Clonar o repositório:**
   ```bash
   git clone https://github.com/PepeHenrque/AGRODEMANDA.git
   cd AGRODEMANDA
   ```

2. **Executar pela IDE:**
   - Abra a pasta do projeto na sua IDE.
   - Aguarde o Maven baixar as dependências automaticamente.
   - Execute a classe principal: `src/main/java/com/agrodemanda/AgrodemandaApplication.java`.

3. **Executar via Linha de Comando (caso possua Maven instalado):**
   ```bash
   mvn spring-boot:run
   ```

4. **Acessar a Aplicação:**
   - **Portal do Agricultor:** [http://localhost:8080](http://localhost:8080)
   - **Painel da Secretaria:** [http://localhost:8080/painel](http://localhost:8080/painel)
   - **Console do Banco H2:** [http://localhost:8080/h2-console](http://localhost:8080/h2-console)
     - *JDBC URL:* `jdbc:h2:mem:agrodemandadb`
     - *User Name:* `sa`
     - *Password:* *(deixar em branco)*

---

## 📂 Estrutura de Pastas do Projeto

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
│   └── DemandaService.java          # Regras de negócio e protocolo
└── AgrodemandaApplication.java      # Classe de inicialização Spring Boot

src/main/resources/
├── static/
│   ├── css/style.css                # Estilização visual e responsividade
│   └── js/script.js                 # Validações e máscaras de campos
├── templates/
│   ├── fragments.html               # Barra de navegação reutilizável
│   ├── index.html                   # Página do agricultor (registro e consulta)
│   └── painel.html                  # Painel de gestão da secretaria
├── application.properties           # Configurações do H2 e JPA
└── data.sql                         # Carga inicial com registros de teste
```
