INSERT INTO categories (id, name) VALUES (1, 'Sushi');
INSERT INTO categories (id, name) VALUES (2, 'Pizza');
INSERT INTO categories (id, name) VALUES (3, 'Spicy');
INSERT INTO categories (id, name) VALUES (4, 'Curry');
INSERT INTO categories (id, name) VALUES (5, 'Southeast Asia');
INSERT INTO categories (id, name) VALUES (6, 'Meat');


INSERT INTO restaurants (id, name, name_jp, website, geo_lat, geo_long, category_ids) VALUES (2, 'Pizzakaya', 'ピザカヤ', 'pizzakaya.com', 35.660563, 139.726685, '{2,3}');
INSERT INTO restaurants (id, name, name_jp, website, geo_lat, geo_long, category_ids) VALUES (3, 'Diya', 'ディヤ', NULL, 35.660662, 139.729964, '{3,4}');
INSERT INTO restaurants (id, name, name_jp, website, geo_lat, geo_long, category_ids) VALUES (4, 'Green Asia', 'グリーンアジア', NULL, 35.660411, 139.730136, '{3,5}');
INSERT INTO restaurants (id, name, name_jp, website, geo_lat, geo_long, category_ids) VALUES (5, 'Manpukutei', 'まんぷく亭', NULL, 35.660667, 139.730056, '{6}');
INSERT INTO restaurants (id, name, name_jp, website, geo_lat, geo_long, category_ids) VALUES (7, 'Yakiniku Kintan', '焼肉Kintan', 'kintan', 35.662525, 139.732174, '{6}');
INSERT INTO restaurants (id, name, name_jp, website, geo_lat, geo_long, category_ids) VALUES (6, 'Sumiyakiya Nishiazabu', '炭やき屋西麻布店', 'sumiyakiya.com', 35.6603734, 139.7253707, '{3,4}');
INSERT INTO restaurants (id, name, name_jp, website, geo_lat, geo_long, category_ids) VALUES (1, 'Pintokona', 'ぴんとこな', 'https://kiwa-group.co.jp/pintokona/', 35.661553, 139.729330, '{1}');
