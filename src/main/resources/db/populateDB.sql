DELETE FROM user_roles;
DELETE FROM users;
DELETE FROM restaurants;
DELETE FROM dishes;
ALTER SEQUENCE user_seq RESTART WITH 100000;
ALTER SEQUENCE restaurant_seq RESTART WITH 100000;
ALTER SEQUENCE dish_seq RESTART WITH 100000;

INSERT INTO restaurants (name) VALUES
  ('My'),
  ('Other');

INSERT INTO dishes (restaurant_id, description, price) VALUES
  (100000, 'Чечевичный суп', 150),
  (100000, 'Салат с семгой', 250),
  (100001, 'Борщ', 250),
  (100001, 'Оливье', 150),
  (100001, 'Овощное рагу', 200);

INSERT INTO users (name, email, password) VALUES
  ('User', 'user@yandex.ru', 'password'),
  ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_ADMIN', 100001);