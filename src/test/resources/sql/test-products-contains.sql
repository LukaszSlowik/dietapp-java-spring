INSERT INTO user_entity (id, username, email) VALUES (1, 'testuser','testuser@user.com');

INSERT INTO product_entity (id,name, scope, owner_id) VALUES
(100, 'Sok jabłkowy', 'GLOBAL', 1),
(101, 'jabłko', 'GLOBAL', 1),
(102, 'Woda mineralna', 'GLOBAL', 1);

INSERT INTO product_unit_entity (id, unit_type, product_id, grams_per_unit) VALUES
(1000, 'MUG', 100, 200.0),         -- Sok jabłkowy: 1 szklanka = 200g
(1001, 'PIECE', 101, 180.0),         -- jabłko: 1 sztuka = 180g
(1002, 'CUP', 102, 250.0);           -- Woda: 1 kubek = 250g