INSERT INTO restaurants (name, name_jp, website, geo_lat, geo_long, category_ids)
VALUES
  ('Pintokona', 'ぴんとこな', null, null, null, '{1}'),
  ('Pizzakaya', 'ピザカヤ', 'pizzakaya.com', 35.662265, 139.726658, '{2, 3}'),
  ('Dhiya', 'ディヤ', null, null, null, '{4, 3}') ,
  ('Green Asia', 'グリーンアジア', null, null, null, '{5, 3}'),
  ('Manpukutei', 'まんぷく亭', null, null, null, '{6}')
;

INSERT INTO categories (name)
VALUES
('Sushi'),
('Pizza'),
('Spicy'),
('Curry'),
('Southeast Asia'),
('Meat')
;