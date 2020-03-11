insert into users(familyname, givenname, username, email, password, role) values ('Pisti', 'Nagy', 'pistike', 'pistike@gmail.com', '$2a$04$YDiv9c./ytEGZQopFfExoOgGlJL6/o0er0K.hiGb5TGKHUL8Ebn..', 'ROLE_ADMIN');
insert into users(familyname, givenname, username, email, password, role) values ('Kati', 'Kis', 'Katica', 'katalin@gmail.com', '$2a$04$YDiv9c./ytEGZQopFfExoOgGlJL6/o0er0K.hiGb5TGKHUL8Ebn..', 'ROLE_WORKER');

insert into work_time(date, end_hour, start_hour, user_id) values ('2020-03-05', '08:00', '16:00', 1);
insert into work_time(date, end_hour, start_hour, user_id) values ('2020-03-06', '09:00', '16:00', 1);
insert into work_time(date, end_hour, start_hour, user_id) values ('2020-03-05', '08:00', '16:00', 2);


insert into Category(category_name, sale_price, is_sale) values ('zöldség', 300, false);
insert into Category(category_name, sale_price, is_sale) values ('gyümölcs', 370, true);
insert into Category(category_name, sale_price, is_sale) values ('magvak', 100, false);

insert into Product(name, price, sale_price, category_id, is_sale ) values ('banán', 470, 390, 2, false);
insert into Product(name, price, sale_price, category_id, is_sale ) values ('alma', 300, 250, 2, true);
insert into Product(name, price, sale_price, category_id, is_sale ) values ('dió', 1500, 1330, 3, false);
insert into Product(name, price, sale_price, category_id, is_sale ) values ('uborka', 220, 199, 1, false);

INSERT INTO stock(product_id, quantity) values (1, 30);
INSERT INTO stock(product_id, quantity) values (2, 55);
INSERT INTO stock(product_id, quantity) values (3, 20);
INSERT INTO stock(product_id, quantity) values (1, 40);

INSERT INTO income(date, price, quantity, seller, product_id) VALUES ('2020-03-02', 300, 10, 'Éhes kft', 2);
INSERT INTO income(date, price, quantity, seller, product_id) VALUES ('2020-03-03', 222, 6, 'Uborkás kft', 4);
INSERT INTO income(date, price, quantity, seller, product_id) VALUES ('2020-03-04', 250, 10, 'Éhes kft', 2);


INSERT INTO planned_order(quantity, product_id) VALUES (19, 1);
INSERT INTO planned_order(quantity, product_id) VALUES (1, 2);
INSERT INTO planned_order(quantity, product_id) VALUES (35, 3);

INSERT INTO sale(date, buyer, price, quantity, product_id) VALUES ('2020-03-04','Kis Pisti',470 , 5, 1);
INSERT INTO sale(date, buyer, price, quantity, product_id) VALUES ('2020-03-04 16:45','Nagy Pisti',470 , 5, 1);
INSERT INTO sale(date, buyer, price, quantity, product_id) VALUES ('2020-03-04','Fekete Pisti',470 , 5, 1);