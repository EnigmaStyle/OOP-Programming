# MangaHub Library

MangaHub Library è un'applicazione Java per la gestione di una libreria digitale di manga, con funzionalità di autenticazione utenti e gestione del catalogo.

## 🚀 Caratteristiche Principali

- 🔐 Sistema di autenticazione sicuro con password criptate
- 👥 Gestione ruoli utente (Admin/User)
- 📚 Catalogo manga completo
- 🔍 Ricerca manga per:
  - Titolo
  - Autore
  - Categoria (Shonen, Shojo, Seinen, ecc.)
- 👨‍💼 Funzionalità amministrative:
  - Aggiunta nuovi manga
  - Rimozione manga esistenti
- 💾 Persistenza dati in formato JSON
- 📝 Sistema di logging completo

## 🛠️ Requisiti di Sistema

- Java Development Kit (JDK) 21 o superiore
- Apache Maven 3.8.x o superiore
- Sistema operativo: Windows, macOS, o Linux

## 🔧 Tecnologie Utilizzate

- **Java 21**: Linguaggio di programmazione principale
- **Maven**: Gestione dipendenze e build automation
- **Jackson**: Serializzazione/deserializzazione JSON
- **Log4j2**: Sistema di logging
- **JBCrypt**: Hashing password
- **JUnit 5**: Testing unitario
- **Mockito**: Mocking per i test
- **Hibernate Validator**: Validazione dei dati

## 📦 Installazione

1. Clona il repository:
```bash
git clone https://github.com/EnigmaStyle/OOP-Programming.git
```

2. Naviga nella directory del progetto:
```bash
cd mangahub-library
```

3. Compila il progetto con Maven:
```bash
mvn clean install
```

## 🚀 Esecuzione

Per avviare l'applicazione, esegui:
```bash
mvn exec:java -Dexec.mainClass="com.mangahub.MangaHubApplication"
```

## 📁 Struttura del Progetto

```
mangahub-library/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/mangahub/
│   │   │       ├── common/      # Componenti comuni
│   │   │       ├── manga/       # Gestione manga
│   │   │       └── user/        # Gestione utenti
│   │   └── resources/           # File di configurazione
│   └── test/                    # Test unitari
└── pom.xml                      # Configurazione Maven
```

## 🔒 Sicurezza

- Password criptate con BCrypt
- Protezione contro tentativi di accesso non autorizzati
- Gestione ruoli per funzionalità amministrative
- Input sicuro per le password con mascheramento

## 🧪 Testing

Per eseguire i test:
```bash
mvn test
```

Per generare il report di copertura dei test:
```bash
mvn jacoco:report
```
Per eseguire il programma:
```bash
mvn exec:java -Dexec.mainClass="com.mangahub.MangaHubApplication"  
```

## 📝 Funzionalità Utente

### Utente Standard
- Login/Logout
- Visualizzazione catalogo completo
- Ricerca manga per vari criteri
- Visualizzazione dettagli manga

### Amministratore
- Tutte le funzionalità utente standard
- Aggiunta nuovi manga al catalogo
- Rimozione manga esistenti
- Gestione del catalogo

