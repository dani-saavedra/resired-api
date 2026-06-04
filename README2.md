# RESIRED API

API REST desarrollada para la gestión integral de conjuntos residenciales mediante la plataforma **RESIRED**, permitiendo administrar visitantes, paquetería, residentes, inmuebles, pagos de administración y demás procesos operativos de una copropiedad.

---

## Objetivo del Proyecto

Proporcionar una API segura, escalable y mantenible que centralice la información y los procesos de administración residencial, facilitando la integración con aplicaciones web y móviles.

---

## Tecnologías Utilizadas

* Python 3.11+
* FastAPI
* SQLAlchemy
* PostgreSQL
* Pydantic
* Pytest
* Coverage
* GitHub Actions
* Docker

---

## Estructura del Proyecto

```text
resired-api/
│
├── app/
│   ├── controllers/
│   ├── services/
│   ├── repositories/
│   ├── models/
│   ├── schemas/
│   ├── routes/
│   └── config/
│
├── tests/
│   ├── unit/
│   ├── integration/
│   └── fixtures/
│
├── .github/
│   └── workflows/
│       └── ci.yml
│
├── requirements.txt
├── Dockerfile
├── docker-compose.yml
└── README.md
```

---

## Funcionalidades Principales

### Gestión de Residentes

* Registro de residentes.
* Consulta de información.
* Actualización de datos.
* Control de estados.

### Gestión de Visitantes

* Registro de visitantes.
* Consulta histórica.
* Validación de ingreso.
* Control de acceso.

### Gestión de Paquetería

* Registro de paquetes.
* Notificación de entrega.
* Historial de recepción.
* Confirmación de entrega.

### Administración de Inmuebles

* Gestión de apartamentos.
* Asociación de propietarios.
* Asociación de residentes.

### Gestión de Pagos

* Consulta de obligaciones.
* Registro de pagos.
* Generación de reportes.
* Seguimiento de cartera.

---

## Instalación

### Clonar el repositorio

```bash
git clone https://github.com/dani-saavedra/resired-api.git

cd resired-api
```

### Crear entorno virtual

Linux/Mac

```bash
python -m venv venv
source venv/bin/activate
```

Windows

```bash
python -m venv venv

venv\Scripts\activate
```

### Instalar dependencias

```bash
pip install -r requirements.txt
```

---

## Configuración

Crear archivo `.env`

```env
DB_HOST=localhost
DB_PORT=5432
DB_NAME=resired
DB_USER=postgres
DB_PASSWORD=password

SECRET_KEY=secretkey
```

---

## Ejecutar la Aplicación

```bash
uvicorn app.main:app --reload
```

Acceder a:

```text
http://localhost:8000
```

Documentación Swagger:

```text
http://localhost:8000/docs
```

---

## Pruebas Unitarias

Ejecutar todas las pruebas:

```bash
pytest
```

Ejecutar con detalle:

```bash
pytest -v
```

Ejecutar pruebas de integración:

```bash
pytest tests/integration -v
```

---

## Cobertura de Código

Generar reporte:

```bash
coverage run -m pytest

coverage report

coverage html
```

Abrir reporte:

```bash
htmlcov/index.html
```

Meta esperada:

```text
Cobertura mínima: 75%
Cobertura objetivo: 85%+
```

---

## Integración Continua (CI)

El proyecto utiliza GitHub Actions para:

* Ejecutar pruebas unitarias.
* Ejecutar pruebas de integración.
* Validar calidad del código.
* Generar métricas de cobertura.
* Bloquear integraciones cuando las pruebas fallan.

Pipeline:

```text
Push
   ↓
Build
   ↓
Unit Tests
   ↓
Integration Tests
   ↓
Coverage
   ↓
Merge permitido
```

---

## Ejecución con Docker

Construir imagen:

```bash
docker build -t resired-api .
```

Ejecutar contenedor:

```bash
docker run -p 8000:8000 resired-api
```

---

## Evidencias de Calidad

El proyecto incorpora:

* Verificación mediante pruebas unitarias.
* Validación mediante pruebas de integración.
* Automatización de pruebas.
* Métricas de cobertura.
* Pipeline CI/CD.
* Control de calidad continuo.

---

## Buenas Prácticas Aplicadas

* Arquitectura por capas.
* Principios SOLID.
* Separación de responsabilidades.
* Integración continua (CI).
* Desarrollo guiado por pruebas (TDD).
* Patrón AAA (Arrange – Act – Assert).
* Gestión de defectos mediante pruebas automatizadas.

---

## Autor

**Daniel Saavedra**
**Santiago Chaparro**
**Manuel Ovalle**

Repositorio:

https://github.com/dani-saavedra/resired-api

---

## Licencia

Proyecto académico y de investigación para prácticas de Ingeniería de Software, Testing y DevOps.
