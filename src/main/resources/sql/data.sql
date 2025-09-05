INSERT INTO product (dtype, type, selling_status, name, price, created_at)
VALUES ('Product', 'HOME_APPLIANCES', 'SELLING', '가전제품', 5000, NOW());

INSERT INTO product (dtype, type, selling_status, name, price, created_at)
VALUES ('Product', 'FOOD', 'SELLING', '식품', 1000, NOW());

INSERT INTO product (dtype, type, selling_status, name, price, created_at)
VALUES ('Product', 'HOUSEHOLD_GOODS', 'SELLING', '생활용품', 2500, NOW());

INSERT INTO stock (product_id, quantity, created_at, modified_at)
VALUES (1, 10, NOW(), NOW());

INSERT INTO stock (product_id, quantity, created_at, modified_at)
VALUES (2, 20, NOW(), NOW());

INSERT INTO stock (product_id, quantity, created_at, modified_at)
VALUES (3, 30, NOW(), NOW());