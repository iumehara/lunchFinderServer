DROP TABLE IF EXISTS restaurants;
CREATE TABLE restaurants (
    id SERIAL,
    name varchar(100)
);

DROP TABLE IF EXISTS categories;
CREATE TABLE categories (
    id SERIAL,
    name varchar(100)
);
