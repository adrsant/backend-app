# backend-app
[![Build Status](https://travis-ci.com/adrsant/backend-app.svg?branch=master)](https://travis-ci.com/adrsant/backend-app)
[![codecov](https://codecov.io/gh/adrsant/backend-app/branch/master/graph/badge.svg)](https://codecov.io/gh/adrsant/backend-app)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/afc08192c55f42a68151e31f9a96401c)](https://www.codacy.com/manual/adrsant.silva/backend-app?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=adrsant/backend-app&amp;utm_campaign=Badge_Grade)

Link para swagger: `http://localhost:8080/swagger-ui.html`


Requesitos:
- DOCKER 
- JAVA 11
- POSTGRES


Passos para iniciar o backend:
- Instalar postgres local e criar banco de dados `backend_app`

- Rodar migration pelo development-environment/Makefile.mk `local-migration` (validar configuracao de user/password);

- Efetuar o start da api:
LINUX

```bash
./mvnw spring-boot:run
```

WINDOWS

```
mvnw.cmd spring-boot:run
```


para acessar a api devemos efetuar a autenticaçao, temos dois user, `client` e `admin`.
todas a requests estao mapeadas no projeto do postman: `development-environment/back-end_app.postman_collection.json`

