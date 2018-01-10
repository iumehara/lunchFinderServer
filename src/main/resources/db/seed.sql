INSERT INTO restaurants (name, name_jp, category_ids)
VALUES
  ('Pintokona', 'ぴんとこな', '{1}'),
  ('Pizzakaya', 'ピザカヤ', '{2, 3}'),
  ('Dhiya', 'ディヤ', '{4, 3}') ,
  ('Green Asia', 'グリーンアジア', '{5, 3}'),
  ('Manpukutei', 'まんぷく亭', '{6}')
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