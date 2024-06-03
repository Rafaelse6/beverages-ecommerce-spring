INSERT INTO tb_category(name) VALUES ('Alcoholic')
INSERT INTO tb_category(name) VALUES ('Non-Alcoholic')

INSERT INTO tb_beverages (name, price, description, img_url) VALUES ('Coca-Cola', 1.99, 'Refreshing soft drink', 'https://example.com/cocacola.jpg');
INSERT INTO tb_beverages (name, price, description, img_url) VALUES ('Pepsi', 1.89, 'Popular cola beverage', 'https://example.com/pepsi.jpg');
INSERT INTO tb_beverages (name, price, description, img_url) VALUES ('Orange Juice', 2.99, 'Fresh squeezed orange juice', 'https://example.com/orangejuice.jpg');
INSERT INTO tb_beverages (name, price, description, img_url) VALUES ('Red Wine', 15.99, 'Dry red wine', 'https://example.com/redwine.jpg');
INSERT INTO tb_beverages (name, price, description, img_url) VALUES ('Lager Beer', 5.49, 'Crisp and refreshing lager', 'https://example.com/lagerbeer.jpg');

INSERT INTO tb_beverage_category(beverage_id,category_id) VALUES (1,2);
INSERT INTO tb_beverage_category(beverage_id,category_id) VALUES (2,2);
INSERT INTO tb_beverage_category(beverage_id,category_id) VALUES (3,2);
INSERT INTO tb_beverage_category(beverage_id,category_id) VALUES (4,1);
INSERT INTO tb_beverage_category(beverage_id,category_id) VALUES (5,1);

INSERT INTO tb_user (name, email, phone, password, birth_date) VALUES ('Maria Brown', 'maria@gmail.com', '988888888', '$2a$10$N7SkKCa3r17ga.i.dF9iy.BFUBL2n3b6Z1CWSZWi/qy7ABq/E6VpO', '2001-07-25');
INSERT INTO tb_user (name, email, phone, password, birth_date) VALUES ('Alex Green', 'alex@gmail.com', '977777777', '$2a$10$N7SkKCa3r17ga.i.dF9iy.BFUBL2n3b6Z1CWSZWi/qy7ABq/E6VpO', '1987-12-13');

INSERT INTO tb_role (authority) VALUES ('ROLE_CLIENT');
INSERT INTO tb_role (authority) VALUES ('ROLE_ADMIN');

INSERT INTO tb_user_role (user_id, role_id) VALUES (1, 1);
INSERT INTO tb_user_role (user_id, role_id) VALUES (2, 1);
INSERT INTO tb_user_role (user_id, role_id) VALUES (2, 2);

INSERT INTO tb_order (moment, status, client_id) VALUES (TIMESTAMP WITH TIME ZONE '2022-07-25T13:00:00Z', 1, 1);
INSERT INTO tb_order (moment, status, client_id) VALUES (TIMESTAMP WITH TIME ZONE '2022-07-29T15:50:00Z', 3, 2);
INSERT INTO tb_order (moment, status, client_id) VALUES (TIMESTAMP WITH TIME ZONE '2022-08-03T14:20:00Z', 0, 1);

INSERT INTO tb_order_item (order_id, beverage_id, quantity, price) VALUES (1, 1, 2, 90.5);
INSERT INTO tb_order_item (order_id, beverage_id, quantity, price) VALUES (1, 3, 1, 1250.0);
INSERT INTO tb_order_item (order_id, beverage_id, quantity, price) VALUES (2, 3, 1, 1250.0);
INSERT INTO tb_order_item (order_id, beverage_id, quantity, price) VALUES (3, 1, 1, 90.5);

INSERT INTO tb_payment (order_id, moment) VALUES (1, TIMESTAMP WITH TIME ZONE '2022-07-25T15:00:00Z');
INSERT INTO tb_payment (order_id, moment) VALUES (2, TIMESTAMP WITH TIME ZONE '2022-07-30T11:00:00Z');