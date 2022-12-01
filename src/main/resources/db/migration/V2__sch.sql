insert into users (id, email, password, role, first_name, last_name, is_subscribe_to_newsletter)
values (1, 'admin@gmail.com', '$2y$10$xn3UpyfoT3lJxBbufVbBWu7AwxTlqRp0ExrH34q1pZ6HWKURLxj9C', 'ADMIN', 'Adminbek',
        'Adminov', false),
       (2, 'user@gmail.com', '$2y$10$K3SbId9d9F7wIEY987735uFCnaQlfR38NycPY/Up.MIAFBXPxVqh6', 'USER', 'Userbek',
        'Userov', false),
       (3, 'samatbeganov@gmail.com', '$2y$10$GD6YdRdOg2oFvNadrrZ8hecl9QxHkkfss2GhXpRRykcGrW24G3XWi', 'USER', 'Samat',
        'Beganov', false),
       (4, 'timur@gmail.com', '$2y$10$yGHbdKvP0X2KmDn37SyW3.9aN8nBZDFvMFL8fNJ.KXApIRzR8XyYq', 'USER', 'Timurlan',
        'Kasymbaev', false),
       (5, 'bayaman@gmail.com', '$2y$10$drmjMvGiOxeZ7LKheL..G.iYFilHTTbgVJhcoXvMon0SbZnGAGnhW', 'USER', 'Bayaman',
        'Baraev', false);

insert into categories (id, category_name)
values (1, 'Electronic'),
       (2, 'Clothes'),
       (3, 'School'),
       (4, 'House and garden'),
       (5, 'Shoe'),
       (6, 'Transport');

insert into subcategories(id, subcategory_name, category_id)
values (1, 'Smartphone', 1),
       (2, 'Telephone', 1),
       (3, 'Audio', 1),
       (4, 'Photo and Camera', 1),
       (5, 'Notebooks and computers', 2),
       (6, 'Television', 1),
       (7, 'Console gaming', 1),
       (8, 'Pants', 2),
       (9, 'Shirt', 2),
       (10, 'Knitwear', 2),
       (11, 'Pen', 3),
       (12, 'Book', 3),
       (13, 'Tableware', 4),
       (14, 'Dishes', 4),
       (15, 'Cleaning equipment', 4),
       (16, 'Boots', 5),
       (17, 'Sandals', 5),
       (18, 'Bicycle', 6),
       (19, 'Scooter', 6);

insert into clothing_size(id, size)
values (1, 'XXS'),
       (2, 'XS'),
       (3, 'S'),
       (4, 'M'),
       (5, 'L'),
       (6, 'XXL'),
       (7, 'XL'),
       (8, 'XXXL');

insert into shoe_size(id, size)
values (1, 20),
       (2, 21),
       (3, 22),
       (4, 23),
       (5, 24),
       (6, 25),
       (7, 26),
       (8, 27),
       (9, 28),
       (10, 29),
       (11, 30),
       (12, 31),
       (13, 32),
       (14, 33),
       (15, 34),
       (16, 35),
       (17, 36),
       (18, 37),
       (19, 38),
       (20, 39),
       (21, 40),
       (22, 41),
       (23, 42),
       (24, 43),
       (25, 44),
       (26, 45),
       (27, 46),
       (28, 47),
       (29, 48),
       (30, 49),
       (31, 50);

insert into holiday(id, image, name, user_id, created_at)
values (1, 'image', 'Orthodox Christmas', 1,'2022.01.07'),
       (2, 'image', 'Defenders of the Fatherland Day', 1,'2022.02.23'),
       (3, 'image', 'Women''s Day', 1,'2022.03.08'),
       (4, 'image', 'Nooruz', 1,'2022.03.21'),
       (5, 'image', 'Independence Day', 1,'2022.08.31');

insert into charity(id, charity_status, condition, description, gift_name, image, is_blocked, category_id,
                    subcategory_id, user_id, created_at)
values (1, 'NOT_BOOKED', 'NEW', 'This is a new apple product', 'IPhone XR', 'image', false, 1, 1, 2, '2022.09.09'),
       (2, 'NOT_BOOKED', 'NOT_NEW', 'Book about global history in X-XIV centuries', 'History Book',
        'image', false, 3, 12, 3,'2021.03.23'),
       (3, 'NOT_BOOKED', 'NEW', 'Philips cable headphones', 'Headphones', 'image', false, 1, 3, 4,'2022.04.24'),
       (4, 'NOT_BOOKED', 'NOT_NEW', 'Cooking pan is always needed in the kitchen', 'Pan', 'image',
        false, 4, 14, 5,'2021.10.10'),
       (5, 'NOT_BOOKED', 'NEW', 'Apple laptop', 'Macbook Air 13', 'image', false, 2, 5, 2,'2022.01.30'),
       (6, 'NOT_BOOKED', 'NOT_NEW', 'Excellent winter boots - needed every winter', 'Winter boots',
        'image', false, 5, 16, 3,'2021.09.18'),
       (7, 'NOT_BOOKED', 'NEW', 'Pretty pink colored shirts', 'Woman shirts', 'image', false, 2, 9, 4,'2021.11.21'),
       (8, 'NOT_BOOKED', 'NEW', 'Huawei electric powered scooter', 'Electric Scooter', 'image', false,
        6, 19, 5,'2022.02.27');

insert into wish_list (id, description, gift_name, image, is_blocked, link,
                       wish_list_status, holiday_id, user_id, created)
values (1, '2021 Asus Tuf 15', 'Laptop', 'image', false, 'link', 'NOT_BOOKED', 1, 2,'2022.03.01'),
       (2, 'Samsung old vacuum cleaner', 'Vacuum cleaner', 'image', false, 'link', 'NOT_BOOKED',
        2, 3,'2022.04.04'),
       (3, 'Play Station 5 Pro 1Tb', 'PS 5 Pro', 'image', false, 'link', 'NOT_BOOKED', 3, 4,'2022.05.23'),
       (4, 'More and big money', 'Money', 'image', false, 'link', 'NOT_BOOKED', 4, 5,'2022.10.25');