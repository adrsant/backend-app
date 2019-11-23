insert into client (id, name, email)
values ('10a859f9-19fe-4f1f-8aba-3009c44a9e17', 'teste-name', 'teste@gmail.com');


insert into client (id, name, email)
values ('1ba100b0-d568-4bb9-838e-c8139852cc5c', 'teste-name', 'teste@gmail.com');

insert into product (id, title, brand, image, price, review_score)
values ('10a859f9-19fe-4f1f-8aba-3009c44a9e17', 'title', 'brand', 'image', 150.56, 7.9);


insert into client_product (client_id, product_id)
values ('1ba100b0-d568-4bb9-838e-c8139852cc5c', '10a859f9-19fe-4f1f-8aba-3009c44a9e17');