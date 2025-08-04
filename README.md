# 📊 Datalogger API

Este projeto é uma aplicação backend desenvolvida em **Java com Spring Boot**, projetada para atuar como **Data Logger**, armazenando e processando dados provenientes de sensores, dispositivos IoT ou outras fontes.

🔧 A aplicação foi desenvolvida no contexto da **Universidade Presbiteriana Mackenzie (UPM)** com o objetivo de **automatizar a coleta e armazenamento de dados sensoriais em projetos de Internet das Coisas (IoT)**.

A API está preparada para ser executada em containers **Docker** e integra-se com o **InfluxDB**, um banco de dados de séries temporais ideal para monitoramento e telemetria.

---

## 🚀 Tecnologias Utilizadas

- Java 17+
- Spring Boot 3.4.1
- Maven
- Docker
- Docker Compose
- InfluxDB

---

## 🐳 Como Executar com Docker

1. **Clone o repositório**

```bash
git clone https://github.com/AlvaroHClaver/datalogger.git
cd datalogger
```

2. **Execute com Docker Compose**

```bash
docker-compose up --build
```

A aplicação estará disponível em:

```
http://localhost:8081
```

O InfluxDB estará disponível em:

```
http://localhost:8086
```

---

## ⚙️ Build Manual (sem Docker)

Caso deseje rodar localmente sem Docker:

```bash
./mvnw spring-boot:run
```

Ou:

```bash
./mvnw clean package
java -jar target/datalogger-*.jar
```

---

## 🌐 Endpoints de Exemplo

- `GET /` → Teste de vida da API (`200 OK`)
- `POST /dados` → Envio de dados de sensores (esperado JSON)
- `GET /dados` → Consulta ou exportação dos dados registrados

*(Os endpoints dependem da implementação presente em `controller/`)*

---

## 🗃️ Integração com InfluxDB

A API envia ou consulta dados no **InfluxDB**, um banco de dados especializado para:

- Métricas e telemetria
- Dados sensoriais
- Monitoramento em tempo real

Configuração típica no `application.properties`:

```properties
influx.url=http://localhost:8086
influx.token=SEU_TOKEN
influx.org=SEU_ORG
influx.bucket=datalogger
```

---

## 📁 Estrutura Esperada do Projeto

```
datalogger/
├── src/
│   └── main/java/br/mackenzie/mackleaps/
│       ├── controller/
│       ├── service/
│       ├── model/
│       └── repository/
├── Dockerfile
├── docker-compose.yaml
├── pom.xml
```

---

## 📄 Licença

Distribuído sob a licença MIT ou conforme especificado.

---

> Projeto desenvolvido por [Alvaro H. Claver](https://github.com/AlvaroHClaver) na **Universidade Presbiteriana Mackenzie**, com foco na automação da coleta de dados sensoriais em sistemas IoT usando InfluxDB.
