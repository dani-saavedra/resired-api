# RESIRED API - Pruebas Integracion



## Descripción del Proyecto

**RESIRED** es un sistema backend monolítico desarrollado con Spring Boot que gestiona la operación integral de conjuntos residenciales. Provee todas las funcionalidades tanto para la aplicación móvil de residentes como para el panel administrativo.

### Objetivo del Proyecto de Testing

> Realizar un proceso de **testing funcional y de validación** sobre el sistema RESIRED, con el fin de identificar defectos de manera temprana y validar el correcto funcionamiento de cada uno de sus módulos. Esto permitirá garantizar la calidad y confiabilidad del producto final para los residentes y administradores.

### Alcance - Módulos Bajo Prueba

| Módulo | Descripción |
|--------|-------------|
| **Visitantes** | Registro y control de acceso de visitantes al conjunto |
| **Paquetería** | Gestión de paquetes recibidos para residentes |
| **Pagos** | Procesamiento de pagos de administración |
| **Reserva de Zonas Comunes** | Reserva de zonas comunes del conjunto |
| **Módulo Administrativo** | Gestión de usuarios, residentes, bloques y noticias |

---

## Integrantes del Equipo

|  |  |
|--------|----------------------|
| **Daniel Orlando Saavedra** | |
| **Manuel Alejandro Ovalle** | |
| **Brayan Santiago Chaparro** | |


---

## 1. Repositorio

### Requisitos del Repositorio

- **Repositorio Git** con el proyecto completo: https://github.com/dani-saavedra/resired-api
- **`.gitignore`** configurado (excluye `build/`, `.gradle`, `.idea/`, `.vscode/`, `bin/`, `out/`)
- **`integrantes.txt`** con nombres y correos institucionales
- **Rama principal ejecutable** con tests y compilación mediante Gradle

### Clonar y Ejecutar

```bash
git clone https://github.com/dani-saavedra/resired-api.git
cd resired-api

# Compilar y ejecutar todas las pruebas
./gradlew clean test
```

> **Nota:** Para Windows sin `make`, usa directamente `gradlew.bat`

```bat
gradlew.bat clean test
```

### Verificar arranque local (requiere variables de entorno)

```bash
# Variables requeridas: DB_HOST, DB_PORT, DB_NAME, DB_USER, DB_PASS, EMAIL_USER, EMAIL_PASS, SECRET_KEY
./gradlew bootRun
# Health check: http://localhost:8080/api/actuator/health
```

---

## 2. Documentación (Wiki)

> Toda la documentación técnica del taller se entrega en el **Wiki del repositorio**. El Wiki es la entrega oficial; no se requiere PDF.

### Estructura del Wiki

| Página | Contenido |
|--------|-----------|
| **Inicio** | Descripción del dominio, propósito del sistema y miembros del equipo |
| **Tipos de Pruebas** | Diferencias entre unitarias, integración y sistema (tabla comparativa) |
| **Arquitectura Limpia** | Diagrama de capas: `domain`, `application`, `infrastructure`, `delivery` |
| **Pruebas de Integración (H2)** | Cómo se conectan las capas con base de datos H2 |
| **Pruebas con Mockito** | Ejemplos de `when(...)`, `verify(...)`, `never(...)` |
| **Pruebas de Sistema (HTTP)** | Escenarios y evidencias de ejecución con respuestas JSON |
| **Resultados JaCoCo** | Capturas del reporte y análisis de cobertura |
| **Conclusiones Técnicas** | Aprendizajes y limitaciones detectadas |
| **Reflexión Final** | Preguntas reflexivas sobre diseño y CI |

---

## 3. Arquitectura del Sistema

El proyecto implementa **Arquitectura Limpia (Clean Architecture)** con las siguientes capas:

```
com.resired.api/
├── admin/
│   ├── domain/               ← Entidades, puertos (interfaces), VOs
│   ├── application/          ← Casos de uso, DTOs, excepciones de negocio
│   └── infraestructure/      ← REST controllers, adaptadores SQL, ORM (JPA)
├── guard/
│   ├── domain/
│   ├── application/
│   └── infrastructure/
├── resident/
│   ├── domain/
│   ├── application/
│   └── infraestructure/
├── security/
│   ├── domain/
│   ├── application/
│   └── infraestructure/
└── shared/
    └── notification/         ← Módulo compartido de notificaciones push
```

### Reglas Arquitectónicas Validadas (ArchUnit)

Aunque están fuera del alcance principal del taller, el proyecto cuenta con **Pruebas de Arquitectura** automatizadas en [`ArchitectureTest.java`](./src/test/java/com/resired/api/arq/ArchitectureTest.java) que garantizan el cumplimiento de Clean Architecture verificando que:

- El paquete `domain` **no depende** de `application`
- El paquete `domain` **no depende** de `infraestructure`
- El paquete `application` **no depende** de `infraestructure`

---

## 4. Tipos de Pruebas Implementadas

El proyecto implementa varios niveles de prueba para garantizar la calidad del software:

1. **Pruebas de Arquitectura (ArchUnit):** Validan el diseño del sistema y dependencias de paquetes.
2. **Pruebas con Mockito:** Aisladas de la infraestructura, simulan repositorios externos para validar lógica de negocio (ej. `AdminNeighborhoodUseCaseTest`).
3. **Pruebas de Integración y Sistema (HTTP) con H2:** Levantan el contexto de Spring Boot, conectan capas y verifican persistencia real en una base de datos en memoria (ej. `AdmGuardControllerTest`).

---

## 5. Pruebas de Integración y Sistema (HTTP) con H2

Las pruebas end-to-end validan los **endpoints reales** usando `RestClient` y levantando el contexto completo de Spring Boot. 

Para que estas pruebas de integración funcionen de manera aislada, se utiliza una **base de datos H2 en memoria**. Al usar la anotación `@ActiveProfiles("test")` en la clase de prueba (como se ve en `AdmGuardControllerTest`), Spring Boot carga automáticamente la configuración del archivo [`application-test.yaml`](./src/main/resources/application-test.yaml), el cual implementa H2:

```yaml
spring:
  datasource:
    driver-class-name: org.h2.Driver
    url: jdbc:h2:mem:testdb
    username: sa
    password: ''
  jpa:
    hibernate:
      ddl-auto: create
    database-platform: org.hibernate.dialect.H2Dialect
  flyway:
    enabled: 'false'
```

### Casos Implementados

| Caso | Endpoint | Método | Status Esperado | Implementa |
|------|----------|--------|----------------|------------|
| Inactivar guardia exitosamente | `/api/admin/guard/{id_user}` | `DELETE` | `200 OK` | Base de datos H2 |

### Ejemplo con RestClient - [`AdmGuardControllerTest.java`](./src/test/java/com/resired/api/integration/AdmGuardControllerTest.java)

```java
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class AdmGuardControllerTest {

    @LocalServerPort
    private int port;

    private RestClient restClient;

    @BeforeEach
    void setup() {
        restClient = RestClient.builder()
            .baseUrl("http://localhost:" + port)
            .build();
    }

    @Test
    void shouldInactiveGuardSuccessfully() {
        // Arrange
        String bearerToken = "Bearer [TOKEN]";
        Integer idUser = 123;

        // Act
        ResponseEntity<String> response = restClient
            .delete()
            .uri("/api/admin/guard/{id_user}", idUser)
            .header(HttpHeaders.AUTHORIZATION, bearerToken)
            .retrieve()
            .toEntity(String.class);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Guard inactive successfully", response.getBody());
    }
}
```

---
## 6. Pruebas Unitarias de la arquitectura
El proyecto usa **ArchUnit** para realizar pruebas de cumplimiento de la arquitectura estipulada.
```java
public class ArchitectureTest {

    @ArchTest
    public static final ArchRule domainShouldNotHaveAnyDependenciesFromApplication = ArchRuleDefinition
        .noClasses()
        .that().resideInAPackage("..domain..")
        .should().dependOnClassesThat()
        .resideInAPackage("..application..");
    @ArchTest
    public static final ArchRule domainShouldNotHaveAnyDependenciesFromInfra = ArchRuleDefinition
        .noClasses()
        .that().resideInAPackage("..domain..")
        .should().dependOnClassesThat()
        .resideInAPackage("..infraestructure..");

    @ArchTest
    public static final ArchRule applicationShouldNotHaveAnyDependenciesFromInfra = ArchRuleDefinition
        .noClasses()
        .that().resideInAPackage("..application..")
        .should().dependOnClassesThat()
        .resideInAPackage("..infraestructure..");
}
```

## 6. Pruebas con Mockito

El proyecto usa **Mockito** para simular repositorios y adaptadores externos, aislando la lógica de negocio de las dependencias de infraestructura.

### Ejemplo - [`AdminNeighborhoodUseCaseTest.java`](./src/test/java/com/resired/api/admin/application/usecase/AdminNeighborhoodUseCaseTest.java)

```java
@ExtendWith(MockitoExtension.class)
class AdminNeighborhoodUseCaseTest {

    @InjectMocks
    private AdminNeighborhoodUseCase neighborhoodService;

    @Mock
    private AdminNeighborhoodPort adminNeighborhoodPort;

    @Mock
    private AdminUserUseCase adminUserUseCase;

    @Test
    void createNewNeighborhoodTest() throws GeneralSecurityException {
        // Arrange
        Integer expectedNeighborhoodId = 1;
        when(adminNeighborhoodPort.createNeighborHood(any(CreateNeighborhoodVo.class)))
            .thenReturn(expectedNeighborhoodId);

        // Act
        neighborhoodService.createNewNeighborhood(createNeighborhoodVo);

        // Assert - verify(...) verifica que el método fue llamado
        verify(adminNeighborhoodPort).createNeighborHood(createNeighborhoodVo);
    }
}
```

### Ejemplo - [`AdminNewsUseCaseTest.java`](./src/test/java/com/resired/api/admin/application/usecase/AdminNewsUseCaseTest.java)

```java
@Test
void createNewsTest() throws IOException {
    // Arrange
    when(fileBucket.uploadFileToBucket(eq("cover_image_resired"), anyString(), any()))
        .thenReturn("http://bucket.com/image.jpg");

    // Act
    adminNewsUseCase.createNews(newsRequest, neighborhoodId);

    // Assert - verify interacciones
    verify(fileBucket).uploadFileToBucket(eq("cover_image_resired"), contains("TestNews"), any());
    verify(adminNewsPort).createNews(newsRequest, neighborhoodId, expectedImageUrl, expectedDetailUrl);
    verify(pushAppUseCase).notifyNeighborhood(notificationCaptor.capture());
}
```

### Swagger / OpenAPI

La documentación interactiva de endpoints está disponible en:

```
http://localhost:8080/api/swagger-ui/index.html
```

---

## 7. Matriz de Pruebas Implementadas

| Caso | Entrada | Resultado Esperado | Tipo | Test que lo valida |
|------|---------|-------------------|------|--------------------|
| Creación de barrio | Datos válidos de vecindario | Barrio creado, usuario admin registrado | Mock | `createNewNeighborhoodTest()` |
| Publicación de noticia | Datos de noticia + archivos adjuntos | Archivos subidos, notificación enviada | Mock | `createNewsTest()` |
| Inactivar Guardia | ID válido y token de autorización | `200 OK`, body `"Guard inactive successfully"` | HTTP | `shouldInactiveGuardSuccessfully()` |

---

## 8. Gestión de Defectos

Los defectos detectados durante el proceso de testing se documentan en [`defectos.md`](./defectos.md).

### Resumen de Defectos

| ID | Caso Probado | Estado |
|----|-------------|--------|
| DEF-001 | Registro de visitante con documento duplicado | Cerrado |

> Ver el detalle completo en [defectos.md](./defectos.md).

---

## 9. Cobertura con JaCoCo e Integración Continua (CI)

### Generar el Reporte

```bash
./gradlew clean test jacocoTestReport
```

El reporte se genera en:

```
build/reports/jacoco/test/html/index.html
```

### Umbrales de Cobertura Requeridos

| Paquete | Cobertura Mínima |
|---------|-----------------|
| Global | >= 80% |
| `application` | >= 70% |
| `delivery` / `infraestructure` | >= 70% |

### Configurar Umbral en `build.gradle`

```groovy
jacocoTestCoverageVerification {
    violationRules {
        rule {
            limit {
                minimum = 0.80
            }
        }
    }
}
```

### Clases Excluidas del Reporte

| Clase | Razón de Exclusión |
|-------|-------------------|
| `ApiApplication.java` | Clase de arranque Spring Boot, no contiene lógica de negocio |
| Clases `*Orm.java` | Entidades JPA puras, sin lógica propia |
| Clases `*Config.java` | Configuración de seguridad y beans, difícil de testear sin contexto completo |


---

## 10. Reflexión Final (Wiki)



---

## Stack Tecnológico

| Tecnología | Versión | Uso |
|------------|---------|-----|
| Java | 17 | Lenguaje principal |
| Spring Boot | 3.3.4 | Framework backend |
| Spring Security + JWT | - | Autenticación y autorización |
| Spring Data JPA | - | Persistencia de datos |
| MySQL | 8 | Base de datos de producción |
| H2 | - | Base de datos en memoria para pruebas |
| Flyway | 10.17.0 | Migraciones de base de datos |
| Gradle | 8.x | Build system |
| JUnit 5 | - | Framework de pruebas |
| Mockito | - | Mocking en pruebas unitarias |
| ArchUnit | 1.3.0 | Validación de arquitectura |
| JaCoCo | - | Cobertura de código |
| SpringDoc OpenAPI | 2.6.0 | Documentación Swagger |
| Firebase Admin | 9.3.0 | Notificaciones push |
| Lombok | - | Reducción de boilerplate |

---

## Variables de Entorno Requeridas

| Variable | Descripción |
|----------|-------------|
| `DB_HOST` | Host de la base de datos MySQL |
| `DB_PORT` | Puerto de MySQL (usualmente `3306`) |
| `DB_NAME` | Nombre de la base de datos |
| `DB_USER` | Usuario de la base de datos |
| `DB_PASS` | Contraseña de la base de datos |
| `EMAIL_USER` | Usuario SMTP para envío de correos |
| `EMAIL_PASS` | Contraseña SMTP (usar App Password de Google) |
| `SECRET_KEY` | Clave secreta para firma de tokens JWT |

---

## Seguridad

- **AES (Advanced Encryption Standard)**: Cifrado simétrico para protección de datos sensibles en tránsito y reposo.
- **SHA-256**: Hash criptográfico para almacenamiento seguro de contraseñas y verificación de integridad.
- **JWT (JSON Web Tokens)**: Autenticación stateless con tokens firmados usando RS256.

---

## Conceptos del Dominio

| Concepto | Descripción |
|----------|-------------|
| **Guardia** | Persona que valida el ingreso de visitas y registra paquetes |
| **Residente** | Persona que habita en un hogar administrado por Resired |
| **Visitante** | Persona invitada por un residente a ingresar al conjunto |
| **Visita** | Acción de entrar al conjunto; un mismo visitante puede tener múltiples visitas |
| **Conjunto / Barrio** | Unidad residencial administrada por Resired |

---
