DROP TABLE IF EXISTS restaurants;
CREATE TABLE restaurants (
    id SERIAL,
    name varchar(100),
    name_jp varchar(100),
    category_ids bigint[]
);

DROP TABLE IF EXISTS categories;
CREATE TABLE categories (
    id SERIAL,
    name varchar(100)
);
