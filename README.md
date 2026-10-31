# API de Usuários — Spring Boot | Infoeste 2026

Projeto desenvolvido **durante um curso da Infoeste 2026** para estudar os fundamentos do ecossistema Spring, com foco em **arquitetura hexagonal**, **injeção de dependência**, **inversão de controle**, persistência com **MySQL/JPA** e cache utilizando **Redis**. O ambiente de desenvolvimento também inclui **RabbitMQ via Docker Compose** para exploração de conceitos de mensageria.

> **Contexto acadêmico:** repositório de estudos construído a partir de atividades práticas de um curso da Infoeste. Nem todos os conceitos abordados em aula estão implementados como funcionalidades completas nesta versão.

## Tecnologias

- **Java 25** e **Spring Boot 4.1.2**
- **Spring Web MVC** para a API REST
- **Spring Data JPA / Hibernate** e **MySQL 8.4**
- **Spring Data Redis** e **Redis 7.4** para cache
- **Docker Compose** para subir serviços de infraestrutura
- **RabbitMQ 4 (Management)** disponível no ambiente Docker (sem publicação/consumo de mensagens implementados no código atual)
- **Maven Wrapper** para gerenciamento e execução do projeto
- **Lombok** para reduzir código repetitivo

**Kafka:** foi um dos conceitos de mensageria estudados, mas **não está integrado** ao projeto enviado.

## Conceitos aplicados

### Arquitetura hexagonal (Ports & Adapters)

O projeto separa o **domínio e os casos de uso** das tecnologias de entrada e saída:

- `domain`: modelo `Usuario`, independente das entidades JPA.
- `port/in`: contratos dos casos de uso (cadastrar, buscar e excluir usuário).
- `application/usecase`: implementação da lógica de aplicação.
- `port/out`: abstrações `UsuarioRepositorio` e `UsuarioCache`.
- `adapter/in/http`: controller REST e payloads HTTP.
- `adapter/out`: adaptadores concretos do MySQL, JPA, mapeamento e Redis.

A aplicação depende das **interfaces das portas**, e os adaptadores concretos implementam esses contratos.

### Injeção de dependência e inversão de controle (IoC)

O **Spring** instancia e conecta componentes identificados por anotações como `@Service`, `@Repository` e `@RestController`. O código utiliza tanto **injeção por construtor** (com `@RequiredArgsConstructor`) quanto **injeção por campo** (com `@Autowired`). A preferência para evoluções é padronizar a injeção por construtor.

### Redis: padrão cache-aside

Nas buscas por ID, `UsuarioLookup` consulta primeiro o Redis. Se não encontrar o usuário, consulta o MySQL e grava o resultado no cache. O TTL é configurado por `CACHE_TIME`, com padrão de **90 segundos**.

### Mensageria: RabbitMQ e Kafka

Mensageria permite o **disparo e consumo de eventos** de forma assíncrona, desacoplando produtores e consumidores. **RabbitMQ** e **Kafka** são tecnologias estudadas nesse contexto, mas têm modelos e características diferentes. Nesta versão, o `docker-compose.yml` inicia um container RabbitMQ; não há produtor, consumidor, fila ou integração Kafka implementados no código Java.

## Estrutura

```text
cursoSpringBoot/
├── src/
│   ├── main/
│   │   ├── java/com/infoeste/cursoSpringBoot/
│   │   │   ├── adapter/
│   │   │   │   ├── in/http/            # UsuarioController
│   │   │   │   ├── in/payload/         # Request / Response
│   │   │   │   └── out/                # JPA, MySQL, Redis e mapper
│   │   │   ├── application/
│   │   │   │   ├── services/           # UsuarioLookup
│   │   │   │   └── usecase/            # Implementação dos casos de uso
│   │   │   ├── domain/                 # Usuario
│   │   │   ├── port/in/                # Contratos de entrada
│   │   │   ├── port/out/               # Contratos de saída
│   │   │   └── CursoSpringBootApplication.java
│   │   └── resources/application.yaml
│   └── test/
├── docker-compose.yml
├── pom.xml
├── mvnw
└── mvnw.cmd
```

## Endpoints

| Método | Rota | Finalidade |
|---|---|---|
| `POST` | `/v1/usuario` | Cadastrar usuário (`201`) |
| `GET` | `/v1/usuario/{id}` | Buscar usuário por UUID |
| `GET` | `/v1/usuario/email/{email}` | Buscar usuário por e-mail |
| `DELETE` | `/v1/usuario/{id}` | Exclusão lógica (marcar `ativo=false`, `204`) |

Exemplo de cadastro:

```http
POST http://localhost:8080/v1/usuario
Content-Type: application/json

{
  "nome": "Maria Silva",
  "email": "maria@example.com"
}
```

## Como executar

**Pré-requisitos:** Java 25, Docker com Docker Compose e uma IDE como IntelliJ IDEA. O projeto inclui Maven Wrapper.

1. Clone o repositório e abra a pasta que contém o `pom.xml` no IntelliJ.
2. Inicie a infraestrutura, na raiz do projeto:

   ```bash
   docker compose up -d
   ```

3. **Ajuste a conexão com o MySQL antes de executar:** o `docker-compose.yml` publica o MySQL em `localhost:3307`, mas o `application.yaml` fornecido aponta para `localhost:3306`. Para acessar o container a partir do host, altere a URL para:

   ```yaml
   spring:
     datasource:
       url: jdbc:mysql://localhost:3307/cursoSpringBoot?createDatabaseIfNotExist=true
   ```

4. Revise as credenciais locais. O Compose original permite **senha vazia no MySQL**, apenas para laboratório; nunca use essa configuração em produção ou em um servidor exposto.
5. Execute `CursoSpringBootApplication` pelo IntelliJ ou, no terminal:

   **Linux/macOS:**
   ```bash
   ./mvnw spring-boot:run
   ```

   **Windows (PowerShell):**
   ```powershell
   .\mvnw.cmd spring-boot:run
   ```

6. A API usa a porta `8080` por padrão, a menos que outra seja configurada. O painel de gerenciamento do RabbitMQ fica em `http://localhost:15672` (credenciais de laboratório: `guest` / `guest`).

Para encerrar os containers:

```bash
docker compose down
```

## Observações sobre a versão do curso

- Em `BuscaUsuarioPorIdService`, a verificação atual de `ativo` está invertida: usuários ativos podem receber `404`. A condição deve ser revisada antes de considerar o endpoint validado.
- A exclusão é **lógica**, preservando o registro no banco e alterando `ativo` para `false`.
- O RabbitMQ está disponível na infraestrutura, mas **não há eventos sendo publicados ou consumidos** nesta versão.
- O projeto é voltado a **aprendizado**; ajustes de configuração, tratamento de erros, testes e segurança são oportunidades de evolução.

## Origem

Projeto elaborado em atividades práticas de um **curso da Infoeste 2026**, com propósito educacional e de aprofundamento em desenvolvimento backend com Java e Spring Boot.

## Contato

**Murilo Farina** · [GitHub](https://github.com/murilofarina) · [LinkedIn](https://www.linkedin.com/in/murilofarina/)