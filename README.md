# Prerequisiti Locali

Questo progetto richiede **MongoDB** e **Ollama** in esecuzione come container Docker.

## Prerequisiti

- **Docker Desktop** installato e in esecuzione
- Almeno **4GB di RAM** disponibili per Ollama
- **curl** o similar tool per testare gli endpoint

---

## 1. Avvio dei container (Opzione consigliata)

Usa il file `docker-compose.yml` incluso nel progetto:

```bash
docker-compose up -d
```

### Download modello per Ollama

Solo al primo avvio, scarica il modello:

```bash
docker exec ollama ollama pull ibm/granite4:1b
```

### Verifica

```bash
# MongoDB
docker exec -it mongodb mongosh -u admin -p secret --eval "db.version()"

# Ollama
curl http://localhost:11434/api/tags
```

### Interfaccia web MongoDB

Mongo Express è disponibile su **http://localhost:8081**

---

## 2. Avvio manuale (Alternativa)

### MongoDB - Pull e avvio

```bash
docker pull mongo:7.0

docker run -d \
  --name mongodb \
  -p 27017:27017 \
  -e MONGO_INITDB_ROOT_USERNAME=admin \
  -e MONGO_INITDB_ROOT_PASSWORD=secret \
  mongo:7.0
```

### Ollama - Pull e avvio

```bash
docker pull ollama/ollama:latest

docker run -d \
  --name ollama \
  -p 11434:11434 \
  -v ollama_data:/root/.ollama \
  ollama/ollama:latest
```

---

## 3. Configurazione

Il progetto usa questi indirizzi di default:

| Servizio | Indirizzo |
|----------|----------|
| MongoDB | localhost:27017 |
| Ollama | localhost:11434 |

Se i container usano hostname diversi, modifica `application.properties`:

```properties
spring.mongodb.uri=mongodb://admin:secret@TUO_IP:27017/orderintelligence?authSource=admin
spring.ai.ollama.base-url=http://TUO_IP:11434
```

Per trovare l'IP del container:
```bash
docker inspect mongodb --format '{{range .NetworkSettings.Networks}}{{.IPAddress}}{{end}}'
```

---

## 4. Avvio dell'applicazione

```bash
./mvnw.cmd spring-boot:run
```

L'app sarà disponibile su **http://localhost:8080**

---

## 5. Verifica

### Endpoint REST

```bash
# Tutti i clienti
curl http://localhost:8080/api/customers

# Tutti i prodotti
curl http://localhost:8080/api/products

# Tutti gli ordini
curl http://localhost:8080/api/orders
```

### Chat AI

```bash
curl -X POST http://localhost:8080/api/ai/chat \
  -H "Content-Type: application/json" \
  -d '{"message": "Who is alice@example.com"}'
```

---

# Step di sviluppo

1. allestimento nuovo progetto MS da zero
2. maven
3. config mongoDB e test integrato
4. config ollama + SpringAI e test integrato
5. primo step di sviluppo usando opencode cli, introduzione i AGENTS.md e primo prompt con prime implementazioni legate a domain/model e introduzione lombok
6. introduzione API base CRUD su entity customer/order/product
7. introduzione API per chat con Ollama di interrogazione DB. adeguamento modello per integrazione tool