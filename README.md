Prerequisites:
Java 17
Docker

OPEN API:
http://localhost:8080/api/swagger-ui/index.html

docker run -d -p 3306:3306 -e MYSQL_ROOT_PASSWORD=clave -e MYSQL_DATABASE=resired -e MYSQL_USER=resired -e MYSQL_PASSWORD=resired mysql:8.4.0


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
