OS=$(shell uname -s)

LOCAL_USER_DB=postgres
LOCAL_DB_URL=jdbc:postgresql://localhost:5432/backend_app

# Run migration for local environment
local-migration:
	@read -p "Enter Database password:" password; \
    mvn flyway:migrate -Dflyway.user=${USER_DB} -Dflyway.url=${LOCAL_DB_URL} -Dflyway.password=$$password -f ../pom.xml

create-local_db:
	docker run -p 5432:5432 -d --env POSTGRES_PASSWORD=postgres --name postgres postgres:11.2
