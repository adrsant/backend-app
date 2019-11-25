CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

create table if not exists product
(
    id           uuid             not null,
    title        varchar(30)      not null,
    brand        varchar(30)      not null,
    image        varchar(100)     not null,
    price        numeric(10, 2)   not null,
    review_score double precision not null
);

ALTER TABLE ONLY product
    ADD CONSTRAINT product_pk PRIMARY KEY (id);

create table if not exists client
(
    id    uuid         not null,
    name  varchar(60)  not null,
    email varchar(100) not null
);

ALTER TABLE ONLY client
    ADD CONSTRAINT client_pk PRIMARY KEY (id);

CREATE INDEX /*CONCURRENTLY*/ email on client (email);

create table if not exists favorite
(
    client_id  uuid not null,
    product_id uuid not null
);

ALTER TABLE ONLY favorite
    ADD CONSTRAINT favorite_pk PRIMARY KEY (client_id, product_id);

ALTER TABLE favorite
    ADD CONSTRAINT fk_favorite_client FOREIGN KEY (client_id) REFERENCES client (id);
