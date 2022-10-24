insert into users (id, email, password, role,first_name,last_name)
values(1,'admin@gmail.com', '$2y$10$xn3UpyfoT3lJxBbufVbBWu7AwxTlqRp0ExrH34q1pZ6HWKURLxj9C', 'ADMIN','Adminbek','Adminov'),
      (2,'user@gmail.com', '$2y$10$K3SbId9d9F7wIEY987735uFCnaQlfR38NycPY/Up.MIAFBXPxVqh6', 'USER','Userbek','Userov');

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


