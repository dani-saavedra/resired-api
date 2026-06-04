# Registro de Defectos - RESIRED API


## DEF-001 - Registro duplicado de visitante no retorna código HTTP correcto

| Campo | Detalle |
|-------|---------|
| **ID** | DEF-001 |
| **Fecha de detección** | 2026-06-03 |
| **Módulo afectado** | Módulo de Visitantes - `GuardController` |
| **Tipo de prueba** | Prueba de Sistema (HTTP) |
| **Estado** | Cerrado |
| **Severidad** | Media |
| **Prioridad** | Alta |

### Caso Probado

Registro de un visitante cuyo número de documento ya existe previamente en la base de datos (escenario de duplicado).

### Resultado Esperado vs. Obtenido

| | Descripción |
|-|-------------|
| **Resultado esperado** | HTTP `422 Unprocessable Entity` con body `"DUPLICATED"` |
| **Resultado obtenido** | HTTP `200 OK` con body `"DUPLICATED"` - el status code no reflejó el estado de error de negocio |

### Causa Probable

El controlador REST devuelve `ResponseEntity.ok(result)` independientemente del valor del resultado de negocio. No existe un mapeo explícito entre el resultado de la capa de aplicación (`"DUPLICATED"`, `"UNDERAGE"`, `"DEAD"`) y el código HTTP correspondiente.

```java
@PostMapping("/register")
public ResponseEntity<String> register(@RequestBody RegistryRequest request) {
    String result = registryUseCase.register(request);
    return ResponseEntity.ok(result); // <- siempre retorna 200
}
```

### Corrección Propuesta

Implementar un mapeo de resultados de negocio a códigos HTTP semánticos:

```java
@PostMapping("/register")
public ResponseEntity<String> register(@RequestBody RegistryRequest request) {
    String result = registryUseCase.register(request);
    return switch (result) {
        case "VALID"       -> ResponseEntity.ok(result);
        case "DUPLICATED"  -> ResponseEntity.unprocessableEntity().body(result);
        case "UNDERAGE"    -> ResponseEntity.badRequest().body(result);
        case "DEAD"        -> ResponseEntity.status(HttpStatus.FORBIDDEN).body(result);
        default            -> ResponseEntity.internalServerError().body(result);
    };
}
```

### Evidencia

```
# Solicitud HTTP ejecutada (simulada):
POST /api/register
Content-Type: application/json

{
  "documentId": "101",
  "age": 35,
  "status": "ACTIVO"
}

# Respuesta obtenida:
HTTP/1.1 200 OK
Content-Type: text/plain;charset=UTF-8

DUPLICATED

# Respuesta esperada:
HTTP/1.1 422 Unprocessable Entity
Content-Type: text/plain;charset=UTF-8

DUPLICATED
```

### Historial de Estado

| Fecha | Estado | Responsable |
|-------|--------|-------------|
| 2026-06-03 | Abierto - detectado en prueba `shouldReturn422WhenPersonIsDuplicated()` | Equipo Testing |
| 2026-06-03 | Cerrado - documentado y corrección propuesta | Equipo Testing |


