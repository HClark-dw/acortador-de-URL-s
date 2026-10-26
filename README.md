# LinkSwift - URL Shortener with Redirection and Analytics

A fully-featured URL shortening service developed with **Java 21** and **Spring Boot**, designed with a clean layered architecture, relational data persistence, and a modern, responsive web interface.

---

## 🚀 Main Features

1. **6-Character Code Generation**:
   - Base62 alphanumeric alphabet (`[0-9a-zA-Z]`).
   - Combinatorial space of $62^6 \approx 56.8$ billion unique combinations.
   - High-entropy cryptographic generator (`SecureRandom`) with collision resolution algorithm.
2. **Efficient Redirection (HTTP 302 Found)**:
   - Redirection at `/s/{code}` pointing to the original target URL.
   - **HTTP 302** (temporary redirect) is used to prevent browsers from caching the destination, ensuring **every click is tracked and audited in real time**.
3. **Live Metrics and Statistics**:
   - Atomic visit counter per link.
   - History of shortened URLs with creation date and last access timestamp.
   - Global summary of total URLs and total clicks.
4. **Data Persistence**:
   - Relational **H2** database in file mode (`./data/urldb`), keeping data intact between server reboots.
   - Integrated H2 web console available at `/h2-console`.
5. **Dual Controller Layer**:
   - **Web Controller (Thymeleaf + Tailwind CSS)**: Modern graphical interface featuring a quick clipboard copy button and reactive alerts.
   - **REST API Controller**: JSON endpoints for external integration at `/api/v1/urls` and `/api/v1/shorten`.

---

## 🛠️ Tech Stack

- **Language**: Java 21 (LTS)
- **Framework**: Spring Boot (Spring Web MVC, Spring Data JPA, Validation)
- **Templates**: Thymeleaf
- **Database**: H2 Database (persistent embedded SQL engine)
- **Frontend**: HTML5, Tailwind CSS (CDN), Plus Jakarta Sans Typography
- **Build Tool**: Apache Maven Wrapper (`mvnw`)
- **Testing**: JUnit 5, Spring Boot Starter Test

---

## 📂 Project Structure







# LinkSwift - Acortador de URLs con Redirección y Analíticas

Un servicio completo de acortamiento de URLs desarrollado con **Java 21** y **Spring Boot**, diseñado con una arquitectura limpia en capas, persistencia de datos relacional y una interfaz web moderna y responsiva.

---

## 🚀 Características Principales

1. **Generación de Códigos de 6 Caracteres**:
   - Alfabeto alfanumérico Base62 (`[0-9a-zA-Z]`).
   - Espacio combinatorio de $62^6 \approx 56.8$ mil millones de combinaciones únicas.
   - Generador criptográfico de alta entropía (`SecureRandom`) con algoritmo de resolución de colisiones.
2. **Redirección Eficiente (HTTP 302 Found)**:
   - Redirección en `/s/{codigo}` hacia la URL de destino original.
   - Se utiliza **HTTP 302** (redirección temporal) para evitar que los navegadores guarden el destino en caché y así registrar y auditar **cada clic en tiempo real**.
3. **Métricas y Estadísticas en Vivo**:
   - Contador atómico de visitas por enlace.
   - Historial de URLs acortadas con fecha de creación y último acceso.
   - Resumen global de URLs y clics totales.
4. **Persistencia de Datos**:
   - Base de datos relacional **H2** en modo archivo (`./data/urldb`), manteniendo los datos intactos entre reinicios del servidor.
   - Consola web de H2 integrada en `/h2-console`.
5. **Doble Capa de Controladores**:
   - **Web Controller (Thymeleaf + Tailwind CSS)**: Interfaz gráfica moderna con botón de copia rápida al portapapeles y alertas reactivas.
   - **REST API Controller**: Endpoints JSON para integración externa en `/api/v1/urls` y `/api/v1/shorten`.

---

## 🛠️ Tecnologías Empleadas

- **Lenguaje**: Java 21 (LTS)
- **Framework**: Spring Boot (Spring Web MVC, Spring Data JPA, Validation)
- **Plantillas**: Thymeleaf
- **Base de Datos**: H2 Database (motor SQL embebido persistente)
- **Frontend**: HTML5, Tailwind CSS (CDN), Tipografía Plus Jakarta Sans
- **Build Tool**: Apache Maven Wrapper (`mvnw`)
- **Testing**: JUnit 5, Spring Boot Starter Test

---

## 📂 Estructura del Proyecto

```
acortador de URL's/
├── run.bat                                  # Lanzador rápido de 1 clic para Windows
├── pom.xml                                  # Dependencias y configuración de Maven
├── README.md                                # Documentación técnica
└── src/
    ├── main/
    │   ├── java/com/portfolio/url_shortener/
    │   │   ├── UrlShortenerApplication.java # Clase principal Spring Boot
    │   │   ├── controller/
    │   │   │   ├── UrlWebController.java    # Vistas Web y Redirección /s/{code}
    │   │   │   └── UrlRestController.java   # API REST JSON (/api/v1)
    │   │   ├── dto/
    │   │   │   ├── ShortenRequestDto.java   # Validación de entrada
    │   │   │   └── ShortenResponseDto.java  # DTO de respuesta con métricas
    │   │   ├── exception/
    │   │   │   ├── UrlNotFoundException.java
    │   │   │   ├── InvalidUrlException.java
    │   │   │   └── GlobalExceptionHandler.java
    │   │   ├── model/
    │   │   │   └── ShortenedUrl.java        # Entidad JPA indexada
    │   │   ├── repository/
    │   │   │   └── ShortenedUrlRepository.java # Consultas y métricas JPA
    │   │   └── service/
    │   │       └── UrlShortenerService.java # Lógica de negocio, Base62 y colisiones
    │   └── resources/
    │       ├── application.properties       # Configuración H2 y servidor
    │       └── templates/
    │           ├── index.html               # Dashboard principal
    │           └── error-404.html           # Página 404 personalizada
    └── test/
        └── java/com/portfolio/url_shortener/
            └── UrlShortenerServiceTest.java # Pruebas unitarias y de integración
```

---

## 🏃 Cómo Ejecutar el Proyecto

### Opción 1: Con el lanzador automático (Recomendado)
Simplemente haz doble clic sobre el archivo **`run.bat`** en la carpeta del proyecto.

### Opción 2: Desde la terminal
```bash
# En Windows PowerShell o CMD:
.\mvnw.cmd spring-boot:run
```

Una vez iniciado:
- **Aplicación Web**: Abre [http://localhost:8080](http://localhost:8080)
- **Consola H2 DB**: Abre [http://localhost:8080/h2-console](http://localhost:8080/h2-console)
  - JDBC URL: `jdbc:h2:file:./data/urldb`
  - Usuario: `sa`
  - Contraseña: *(vacía)*
- **API REST**: [http://localhost:8080/api/v1/urls](http://localhost:8080/api/v1/urls)

---

## 📡 API REST

### 1. Acortar una URL
- **POST** `/api/v1/shorten`
- **Body (JSON)**:
```json
{
  "url": "https://es.wikipedia.org/wiki/Base62"
}
```
- **Respuesta (201 Created)**:
```json
{
  "id": 1,
  "originalUrl": "https://es.wikipedia.org/wiki/Base62",
  "shortCode": "k9Ax2Z",
  "shortUrl": "http://localhost:8080/s/k9Ax2Z",
  "clickCount": 0,
  "createdAt": "2026-10-25T15:30:00"
}
```

### 2. Listar todas las URLs con sus métricas
- **GET** `/api/v1/urls`

---

## 🎯 Puntos Clave para la Entrevista Técnica

Cuando te pregunten sobre este proyecto en una entrevista, destaca los siguientes aspectos de diseño:

1. **¿Por qué HTTP 302 y no HTTP 301 para la redirección?**
   > *"Usé HTTP 302 (Found / Redirección Temporal) porque si utilizara HTTP 301 (Moved Permanently), el navegador del usuario guardaría la respuesta en su caché local y las siguientes visitas irían directamente a la URL destino sin pasar por nuestro servidor, perdiendo la capacidad de contabilizar los clics de forma confiable."*

2. **¿Cómo resolviste la generación de códigos y el problema de las colisiones?**
   > *"Diseñé una estrategia de codificación basada en un alfabeto Base62 (`a-z`, `A-Z`, `0-9`). Con solo 6 caracteres obtenemos $62^6 \approx 56.8$ mil millones de combinaciones. Para evitar códigos secuenciales predecibles (que facilitarían ataques de scraping), implementé generación de alta entropía con `SecureRandom` combinada con un índice único en la base de datos y un bucle de reintento transaccional que previene colisiones. Además, dejé documentada la conversión biyectiva sobre auto-increment ID para escenarios de cero colisiones determinísticas."*

3. **¿Cómo garantizas la eficiencia en la búsqueda?**
   > *"La columna `shortCode` cuenta con un índice único a nivel de base de datos (`@Index(name = 'idx_short_code', columnList = 'shortCode', unique = true)`), lo que reduce el tiempo de búsqueda durante la redirección a $O(1)$ o $O(\log N)$ con un árbol B+."*
