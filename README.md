# Resired-api

## Summary
This repository is the monolith of the backend side that offers all the functionalities for the app and front



## Getting started

* [Java] >= 17
* [Docker]

For installing Make in Windows check out the following [Question from Stack Overflow](
https://stackoverflow.com/questions/2532234/how-to-run-a-makefile-in-windows)

```
git clone git@github.com:dani-saavedra/resired-api.git
cd resired-api
make appRun
```
#### Done
http://localhost:8080/api/actuator/health

Look at the output where the application start is specified

```
Started ApiApplication in 2.357 seconds (process running for 2.537)
```

### Concepts :
* **Guardia**: Persona que valida el ingreso de visitas y registras paquetes
* **Residente**: Persona que habita en un hogar adminsitrado por resired
* **Vistante**: Persona que es invitada por un residente a entregar al conjunto
* **Visita**: Acción de entrar a un conjunto, un mismo vistante puede tener mas de una visita

AES (Advanced Encryption Standard):
Propósito: AES es un algoritmo de cifrado simétrico utilizado para cifrar y descifrar datos.
Función: Toma un bloque de datos y una clave como entrada y produce un bloque de datos cifrado.
Uso: Se utiliza comúnmente para proteger la confidencialidad de los datos, como contraseñas, información personal, archivos, etc.
Ejemplo de uso: Cifrado de datos sensibles en una base de datos o en tránsito a través de una red.

SHA-256 (Secure Hash Algorithm 256 bits):
Propósito: SHA-256 es un algoritmo de hash criptográfico utilizado para generar resúmenes de mensajes.
Función: Toma un mensaje de entrada de longitud variable y produce una salida de longitud fija de 256 bits.
Uso: Se utiliza para verificar la integridad de los datos, autenticación de mensajes y para almacenar contraseñas de forma segura.
Ejemplo de uso: Almacenamiento seguro de contraseñas (almacenando hashes de contraseñas en lugar de contraseñas en texto plano), firmas digitales, autenticación de mensajes en comunicaciones seguras, etc.


### Documentation

OPEN API:
http://localhost:8080/api/swagger-ui/index.html

https://myaccount.google.com/u/2/apppasswords
