insert into users(givenname, familyname, username, email, password, role, enable) values ('Pisti', 'Nagy', 'Pistike', 'pistike@gmail.com', '$2a$04$YDiv9c./ytEGZQopFfExoOgGlJL6/o0er0K.hiGb5TGKHUL8Ebn..', 'ROLE_ADMIN', true);
insert into users(givenname, familyname, username, email, password, role, enable) values ('Kati', 'Kis', 'Katica', 'katalin@gmail.com', '$2a$04$YDiv9c./ytEGZQopFfExoOgGlJL6/o0er0K.hiGb5TGKHUL8Ebn..', 'ROLE_WORKER', true);
insert into users(givenname, familyname, username, email, password, role, enable) values ('Józsi', 'Ferenc', 'Ferike', 'ferijozsi@gmail.com', '$2a$04$YDiv9c./ytEGZQopFfExoOgGlJL6/o0er0K.hiGb5TGKHUL8Ebn..', 'ROLE_WORKER', true);
insert into users(givenname, familyname, username, email, password, role, enable) values ('Laura', 'Nagy', 'Laura', 'nagylaura@gmail.com', '$2a$04$YDiv9c./ytEGZQopFfExoOgGlJL6/o0er0K.hiGb5TGKHUL8Ebn..', 'ROLE_ADMIN', true);
insert into users(givenname, familyname, username, email, password, role, enable) values ('Kati', 'Nagy', 'katika', 'katalin87@gmail.com', '$2a$04$YDiv9c./ytEGZQopFfExoOgGlJL6/o0er0K.hiGb5TGKHUL8Ebn..', 'ROLE_WORKER', true);

insert into work_time(date, start_hour, end_hour,  user_id) values ('2020-03-05', '08:00', '16:00', 1);
insert into work_time(date, start_hour, end_hour,  user_id) values ('2020-03-07', '09:00', '16:00', 1);
insert into work_time(date, start_hour, end_hour,  user_id) values ('2020-03-08', '08:00', '16:00', 2);
insert into work_time(date, start_hour, end_hour,  user_id) values ('2020-03-09', '08:00', '16:00', 3);
insert into work_time(date, start_hour, end_hour,  user_id) values ('2020-03-10', '08:00', '16:00', 4);
insert into work_time(date, start_hour, end_hour,  user_id) values ('2020-03-11', '08:00', '18:00', 3);
insert into work_time(date, start_hour, end_hour,  user_id) values ('2020-03-12', '08:00', '18:00', 5);



insert into Category(name, sale_price, is_sale) values ('zöldség', 300, false);
insert into Category(name, sale_price, is_sale) values ('gyümölcs', 370, true);
insert into Category(name, sale_price, is_sale) values ('magvak', 100, false);
insert into Category(name, sale_price, is_sale) values ('savanyúság', 100, false);
insert into Category(name, sale_price, is_sale) values ('testCat', 100, false);

insert into Product(name, price, sale_price, category_id, is_sale ) values ('banán', 470, 390, 2, false);
insert into Product(name, price, sale_price, category_id, is_sale ) values ('alma', 300, 250, 2, true);
insert into Product(name, price, sale_price, category_id, is_sale ) values ('dió', 1500, 1330, 3, true);
insert into Product(name, price, sale_price, category_id, is_sale ) values ('uborka', 220, 199, 4, false);
insert into Product(name, price, sale_price, category_id, is_sale ) values ('ribizli', 470, 390, 2, false);
insert into Product(name, price, sale_price, category_id, is_sale ) values ('szőlő', 300, 250, 2, true);
insert into Product(name, price, sale_price, category_id, is_sale ) values ('kiwi', 1500, 1330, 2, true);
insert into Product(name, price, sale_price, category_id, is_sale ) values ('savanyú káposzta', 1500, 1330, 4, true);
insert into Product(name, price, sale_price, category_id, is_sale ) values ('újhagyma', 220, 199, 1, false);
insert into Product(name, price, sale_price, category_id, is_sale ) values ('krumpli', 220, 199, 1, false);
insert into Product(name, price, sale_price, category_id, is_sale ) values ('káposzta', 470, 390, 1, false);
insert into Product(name, price, sale_price, category_id, is_sale ) values ('retek', 300, 250, 1, true);
insert into Product(name, price, sale_price, category_id, is_sale ) values ('cékla', 1500, 1330, 1, true);
insert into Product(name, price, sale_price, category_id, is_sale ) values ('forTest', 220, 199, 1, false);

INSERT INTO stock(product_id, quantity) values (1, 15);
INSERT INTO stock(product_id, quantity) values (2, 20);
INSERT INTO stock(product_id, quantity) values (3, 10);
INSERT INTO stock(product_id, quantity) values (4, 13);
INSERT INTO stock(product_id, quantity) values (5, 12);
INSERT INTO stock(product_id, quantity) values (6, 22);

INSERT INTO income(date, price, quantity, seller, product_id) VALUES ('2020-03-02', 300, 10, 'Éhes kft', 2);
INSERT INTO income(date, price, quantity, seller, product_id) VALUES ('2020-03-03', 222, 6, 'Uborkás kft', 4);
INSERT INTO income(date, price, quantity, seller, product_id) VALUES ('2020-03-04', 250, 10, 'Kanna kft', 2);
INSERT INTO income(date, price, quantity, seller, product_id) VALUES ('2020-03-05', 250, 15, 'Savanyús kft', 1);
INSERT INTO income(date, price, quantity, seller, product_id) VALUES ('2020-03-06', 300, 10, 'Kati néni kft', 3);
INSERT INTO income(date, price, quantity, seller, product_id) VALUES ('2020-03-07', 222, 7, 'Zöld kft', 4);
INSERT INTO income(date, price, quantity, seller, product_id) VALUES ('2020-03-08', 250, 22, 'Piros kft', 6);
INSERT INTO income(date, price, quantity, seller, product_id) VALUES ('2020-03-09', 250, 12, 'Almáskert kft', 5);

INSERT INTO planned_order(quantity, product_id) VALUES (19, 1);
INSERT INTO planned_order(quantity, product_id) VALUES (55, 2);
INSERT INTO planned_order(quantity, product_id) VALUES (35, 3);

INSERT INTO sale(date, buyer, price, quantity, product_id) VALUES ('2020-03-04','Kis Pisti',470 , 10, 1);
INSERT INTO sale(date, buyer, price, quantity, product_id) VALUES ('2020-03-04 16:45','Nagy Pisti',645 , 12, 1);
INSERT INTO sale(date, buyer, price, quantity, product_id) VALUES ('2020-03-04','Fekete Pisti',432 , 7, 8);
INSERT INTO sale(date, price, quantity, product_id) VALUES ('2020-03-04' ,432 , 5, 3);
INSERT INTO sale(date, buyer, price, quantity, product_id) VALUES ('2020-03-05','Kis Pisti',470 , 10, 1);
INSERT INTO sale(date, buyer, price, quantity, product_id) VALUES ('2020-03-05 16:45','Nagy Pisti',645 , 12, 6);
INSERT INTO sale(date, buyer, price, quantity, product_id) VALUES ('2020-03-05','Fekete Pisti',432 , 7, 7);
INSERT INTO sale(date, price, quantity, product_id) VALUES ('2020-03-05' ,432 , 5, 5);
INSERT INTO sale(date, buyer, price, quantity, product_id) VALUES ('2020-03-06','Kis Pisti',470 , 10, 1);
INSERT INTO sale(date, buyer, price, quantity, product_id) VALUES ('2020-03-06 16:45','Nagy Pisti',645 , 12, 3);
INSERT INTO sale(date, buyer, price, quantity, product_id) VALUES ('2020-03-06','Fekete Pisti',432 , 7, 4);
INSERT INTO sale(date, price, quantity, product_id) VALUES ('2020-03-06' ,432 , 5, 3);
INSERT INTO sale(date, buyer, price, quantity, product_id) VALUES ('2020-03-07','Kis Pisti',470 , 10, 6);
INSERT INTO sale(date, buyer, price, quantity, product_id) VALUES ('2020-03-07 16:45','Nagy Pisti',645 , 12, 4);
INSERT INTO sale(date, buyer, price, quantity, product_id) VALUES ('2020-03-07','Fekete Pisti',432 , 7, 7);
INSERT INTO sale(date, price, quantity, product_id) VALUES ('2020-03-07' ,432 , 5, 3);
INSERT INTO sale(date, buyer, price, quantity, product_id) VALUES ('2020-03-08','Kis Pisti',470 , 10, 1);
INSERT INTO sale(date, buyer, price, quantity, product_id) VALUES ('2020-03-08 16:45','Nagy Pisti',645 , 12, 5);
INSERT INTO sale(date, buyer, price, quantity, product_id) VALUES ('2020-03-08','Fekete Pisti',432 , 7, 2);
INSERT INTO sale(date, price, quantity, product_id) VALUES ('2020-03-08' ,432 , 5, 3);
INSERT INTO sale(date, buyer, price, quantity, product_id) VALUES ('2020-03-09','Kis Pisti',470 , 10, 6);
INSERT INTO sale(date, buyer, price, quantity, product_id) VALUES ('2020-03-09 16:45','Nagy Pisti',645 , 12, 3);
INSERT INTO sale(date, buyer, price, quantity, product_id) VALUES ('2020-03-09','Fekete Pisti',432 , 7, 2);
INSERT INTO sale(date, price, quantity, product_id) VALUES ('2020-03-09' ,432 , 5, 3);
INSERT INTO sale(date, buyer, price, quantity, product_id) VALUES ('2020-03-10','Kis Pisti',470 , 10, 1);
INSERT INTO sale(date, buyer, price, quantity, product_id) VALUES ('2020-03-10 16:45','Nagy Pisti',645 , 12, 8);
INSERT INTO sale(date, buyer, price, quantity, product_id) VALUES ('2020-03-10','Fekete Pisti',432 , 7, 2);
INSERT INTO sale(date, price, quantity, product_id) VALUES ('2020-03-10' ,432 , 5, 3);