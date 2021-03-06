INSERT INTO categories (name, restaurant_count) VALUES ('Sushi', 1);
INSERT INTO categories (name, restaurant_count) VALUES ('Pizza', 1);
INSERT INTO categories (name, restaurant_count) VALUES ('Spicy', 4);
INSERT INTO categories (name, restaurant_count) VALUES ('Curry', 2);
INSERT INTO categories (name, restaurant_count) VALUES ('Southeast Asia', 1);
INSERT INTO categories (name, restaurant_count) VALUES ('Meat', 2);


INSERT INTO restaurants (name, name_jp, website, geo_lat, geo_long, category_ids) VALUES ('Pizzakaya', 'ピザカヤ', 'pizzakaya.com', 35.660563, 139.726685, '{2,3}');
INSERT INTO restaurants (name, name_jp, website, geo_lat, geo_long, category_ids) VALUES ('Diya', 'ディヤ', NULL, 35.660662, 139.729964, '{3,4}');
INSERT INTO restaurants (name, name_jp, website, geo_lat, geo_long, category_ids) VALUES ('Green Asia', 'グリーンアジア', NULL, 35.660411, 139.730136, '{3,5}');
INSERT INTO restaurants (name, name_jp, website, geo_lat, geo_long, category_ids) VALUES ('Manpukutei', 'まんぷく亭', NULL, 35.660667, 139.730056, '{6}');
INSERT INTO restaurants (name, name_jp, website, geo_lat, geo_long, category_ids) VALUES ('Yakiniku Kintan', '焼肉Kintan', 'kintan', 35.662525, 139.732174, '{6}');
INSERT INTO restaurants (name, name_jp, website, geo_lat, geo_long, category_ids) VALUES ('Sumiyakiya Nishiazabu', '炭やき屋西麻布店', 'sumiyakiya.com', 35.6603734, 139.7253707, '{3,4}');
INSERT INTO restaurants (name, name_jp, website, geo_lat, geo_long, category_ids) VALUES ('Pintokona', 'ぴんとこな', 'https://kiwa-group.co.jp/pintokona/', 35.661553, 139.729330, '{1}');
