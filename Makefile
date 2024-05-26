APP_NAME = "resired"
ENV_DOCKER_API_CONTAINER = "resiredApi"
ENV_DOCKER_API_CONTAINER_PORT = 8080
ENV_DOCKER_MYSQL_CONTAINER = "resired"
ENV_DOCKER_MYSQL_CONTAINER_PORT = 3306
ENV_DOCKER_MYSQL_DB_NAME = "resiredDb"
ENV_DOCKER_MYSQL_DB_USER = "resiredUser"
ENV_DOCKER_MYSQL_DB_PASS = "resiredPass"
GRADLE_EXEC = ./gradlew --info clean
DOCKER_EXEC = docker


test:
	@${GRADLE_EXEC} test --info

buildRun: test
	@echo "Running all tasks for build"
	@${GRADLE_EXEC} -x test build --info

#lint: TODO
#	@echo "Running lint"
#	@${GRADLE_EXEC} detekt

apiRun:
	@echo "Remove docker docs api with name $(ENV_DOCKER_API_CONTAINER)"
	@${DOCKER_EXEC} rm -f -v ${ENV_DOCKER_API_CONTAINER} || true
	@echo "Running docker docs api with name $(ENV_DOCKER_API_CONTAINER)"
	@${DOCKER_EXEC} run -d -p ${ENV_DOCKER_API_CONTAINER_PORT}:8080 --name ${ENV_DOCKER_API_CONTAINER} -e SWAGGER_JSON=/api/swagger.yaml -v ${PWD}/docs/specs/:/api/ swaggerapi/swagger-ui:v3.25.4
	@echo "Documentation Api can be viewed at http://localhost:8080/api/swagger-ui/index.html


apiGenerateAndRun: apiGenerate apiRun

mysqlRemove:
	@echo "Remove docker mysql with name $(ENV_DOCKER_MYSQL_CONTAINER)"
	@${DOCKER_EXEC} rm -f -v $(ENV_DOCKER_MYSQL_CONTAINER) || true

mysqlRun: mysqlRemove
	@echo "Running docker mysql with name $(ENV_DOCKER_MYSQL_CONTAINER)"
	@${DOCKER_EXEC} run -d -p ${ENV_DOCKER_MYSQL_CONTAINER_PORT}:3306 --name $(ENV_DOCKER_MYSQL_CONTAINER) -e MYSQL_DATABASE=${ENV_DOCKER_MYSQL_DB_NAME} -e MYSQL_USER=${ENV_DOCKER_MYSQL_DB_USER} -e MYSQL_PASSWORD=${ENV_DOCKER_MYSQL_DB_PASS} -e MYSQL_ROOT_PASSWORD=${ENV_DOCKER_MYSQL_DB_PASS} mysql:8.4.0
	@sleep 10

appRun: mysqlRun
	@${GRADLE_EXEC} bootRun
