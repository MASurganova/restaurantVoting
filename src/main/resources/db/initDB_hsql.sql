DROP TABLE IF EXISTS user_roles;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS dishes;
DROP TABLE IF EXISTS restaurants;

DROP SEQUENCE IF EXISTS global_seq;

CREATE SEQUENCE global_seq AS INTEGER START WITH 100000;

CREATE TABLE restaurants
(
  id               INTEGER GENERATED BY DEFAULT AS SEQUENCE GLOBAL_SEQ PRIMARY KEY,
  name             VARCHAR(255)            NOT NULL,
  enabled          BOOLEAN DEFAULT FALSE,
  voters           INTEGER                 NOT NULL
);
CREATE UNIQUE INDEX restaurants_unique_name_idx ON restaurants (name);

CREATE TABLE dishes
(
  id                INTEGER GENERATED BY DEFAULT AS SEQUENCE GLOBAL_SEQ PRIMARY KEY,
  restaurant_id     INTEGER       NOT NULL,
  description       VARCHAR(255)  NOT NULL,
  price             INTEGER,
  FOREIGN KEY (restaurant_id) REFERENCES restaurants (id) ON DELETE CASCADE
);

CREATE TABLE users
(
  id               INTEGER GENERATED BY DEFAULT AS SEQUENCE GLOBAL_SEQ PRIMARY KEY,
  name             VARCHAR(255)                 NOT NULL,
  email            VARCHAR(255)                 NOT NULL,
  password         VARCHAR(255)                 NOT NULL,
  restaurant_id    INTEGER,
  FOREIGN KEY (restaurant_id) REFERENCES restaurants (id)
);
CREATE UNIQUE INDEX users_unique_email_idx ON users (email);

CREATE TABLE user_roles
(
  user_id INTEGER NOT NULL,
  role    VARCHAR(255),
  CONSTRAINT user_roles_idx UNIQUE (user_id, role),
  FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);
