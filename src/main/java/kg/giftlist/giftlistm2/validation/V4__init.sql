insert into holiday(id, image, local_date, name, user_id)
values (1, 'image', 2022.01.07, 'Orthodox Christmas', 1),
       (2, 'image', 2022.02.23, 'Defenders of the Fatherland Day', 1),
       (3, 'image', 2022.03.08, 'Women''s Day', 1),
       (4, 'image', 2022.03.21, 'Nooruz', 1),
       (5, 'image', 2022.08.31, 'Independence Day', 1);

insert into charity(id, charity_status, condition, created_at, description, gift_name, image, is_blocked, category_id,
                    subcategory_id, user_id)
values (1, 'NOT_BOOKED', 'NEW', 2022.11.26, 'This is a new apple product', 'IPhone XR', 'image', false, 1, 1, 2),
       (2, 'NOT_BOOKED', 'NOT_NEW', 2022.11.26, 'Book about global history in X-XIV centuries', 'History Book',
        'image', false, 3, 12, 3),
       (3, 'NOT_BOOKED', 'NEW', 2022.10.16, 'Philips cable headphones', 'Headphones', 'image', false, 1, 3, 4),
       (4, 'NOT_BOOKED', 'NOT_NEW', 2022.02.06, 'Cooking pan is always needed in the kitchen', 'Pan', 'image',
        false, 4, 14, 5),
       (5, 'NOT_BOOKED', 'NEW', 2022.02.06, 'Apple laptop', 'Macbook Air 13', 'image', false, 2, 5, 2),
       (6, 'NOT_BOOKED', 'NOT_NEW', 2022.04.20, 'Excellent winter boots - needed every winter', 'Winter boots',
        'image', false, 5, 16, 3),
       (7, 'NOT_BOOKED', 'NEW', 2022.07.25, 'Pretty pink colored shirts', 'Woman shirts', 'image', false, 2, 9, 4),
       (8, 'NOT_BOOKED', 'NEW', 2022.08.15, 'Huawei electric powered scooter', 'Electric Scooter', 'image', false,
        6, 19, 5);

insert into wish_list (id, created, description, gift_name, holyday_date, image, is_blocked, link,
                       wish_list_status, holiday_id, user_id)
values (1, 2022.09.11, '2021 Asus Tuf 15', 'Laptop', 2022.01.07, 'image', false, 'link', 'NOT_BOOKED', 1, 2),
       (2, 2022.10.12, 'Samsung old vacuum cleaner', 'Vacuum cleaner', 2022.05.15, 'image', false, 'link', 'NOT_BOOKED', 2, 3),
       (3, 2022.11.14, 'Play Station 5 Pro 1Tb', 'PS 5 Pro', 2022.06.20, 'image', false, 'link', 'NOT_BOOKED', 3, 4),
       (4, 2022.04.22, 'More and big money', 'Money', 2022.10.20, 'image', false, 'link', 'NOT_BOOKED', 4, 5);
