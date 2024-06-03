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