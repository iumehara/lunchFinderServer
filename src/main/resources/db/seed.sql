INSERT INTO restaurants (name, category_ids)
VALUES
  ('Pintokona', '{1}'),
  ('Pizzakaya', '{2, 3}'),
  ('Dhiya', '{4, 3}') ,
  ('Green Asia', '{5, 3}'),
  ('Manpukutei', '{6}')
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