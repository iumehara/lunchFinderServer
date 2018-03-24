DROP TABLE IF EXISTS restaurants;
CREATE TABLE restaurants (
    id SERIAL UNIQUE,
    name varchar(100),
    name_jp varchar(100),
    website varchar(100),
    geo_lat decimal,
    geo_long decimal,
    category_ids bigint[]
);

DROP TABLE IF EXISTS categories;
CREATE TABLE categories (
    id SERIAL UNIQUE,
    name varchar(100),
    restaurant_count bigint DEFAULT 0
);
