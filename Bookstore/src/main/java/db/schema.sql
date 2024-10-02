DROP TABLE IF EXISTS author;
create table author(
    id varchar(36) NOT NULL PRIMARY KEY UNIQUE,
    fio varchar(50) NOT NULL
);

DROP TABLE IF EXISTS users;
create table users(
    id varchar(36) NOT NULL PRIMARY KEY UNIQUE,
    email varchar(250) NOT NULL UNIQUE,
    password varchar(1000) NOT NULL,
    role varchar(25),
    name varchar(250) NOT NULL,
    surname varchar(250) NOT NULL,
    patronymic varchar(250),
    birth_date date,
    status varchar(10)
);

DROP TABLE IF EXISTS admin;
create table admin(
    id varchar(36) NOT NULL PRIMARY KEY UNIQUE,
    FOREIGN KEY (id) REFERENCES users (id)
);

DROP TABLE IF EXISTS sellers;
create table sellers(
    id varchar(36) NOT NULL PRIMARY KEY UNIQUE,
    FOREIGN KEY (id) REFERENCES users (id)
);

DROP TABLE IF EXISTS customers;
create table customers(
    id varchar(36) NOT NULL PRIMARY KEY UNIQUE,
    phone varchar(15) NOT NULL UNIQUE,
    card_number varchar(36) NOT NULL UNIQUE
);

DROP TABLE IF EXISTS orders;
create table orders(
    id varchar(36) NOT NULL PRIMARY KEY UNIQUE,
    track_number varchar(10) NOT NULL UNIQUE,
    pin int,
    total_price int NOT NULL,
    date_create date NOT NULL,
    seller_preparing_id varchar(36),
    seller_finished_id varchar(36),
    customer_id varchar(36) NOT NULL,
    order_status varchar(20) NOT NULL,
    FOREIGN KEY (seller_preparing_id) REFERENCES sellers (id),
    FOREIGN KEY (seller_finished_id) REFERENCES sellers (id),
    FOREIGN KEY (customer_id) REFERENCES customers (id)
);

DROP TABLE IF EXISTS products;
create table products(
    id varchar(36) NOT NULL PRIMARY KEY UNIQUE,
    name varchar(200) NOT NULL,
    definition varchar(2000),
    price int NOT NULL,
    quantity int NOT NULL,
    availability boolean NOT NULL DEFAULT false,
    link varchar(1000)
);

DROP TABLE IF EXISTS order_product;
create table order_product(
    order_id varchar(36) NOT NULL,
    product_id varchar(36) NOT NULL,
    FOREIGN KEY (order_id) REFERENCES orders (id),
    FOREIGN KEY (product_id) REFERENCES products (id)
);

DROP TABLE IF EXISTS reviews;
create table reviews(
    id varchar(36) NOT NULL PRIMARY KEY UNIQUE,
    comment_for_all varchar(1000),
    review_date_for_all date,
    comment_for_friends varchar(1000),
    review_date_for_friends date,
    product_id varchar(36),
    customer_id varchar(36),
    FOREIGN KEY (product_id) REFERENCES products (id),
    FOREIGN KEY (customer_id) REFERENCES customers (id)
);

DROP TABLE IF EXISTS application;
create table application(
    id varchar(36) NOT NULL PRIMARY KEY UNIQUE,
    applicant_id varchar(36) NOT NULL,
    future_friend_id varchar(36) NOT NULL,
    application_status varchar(15) NOT NULL,
    FOREIGN KEY (applicant_id) REFERENCES customers (id),
    FOREIGN KEY (future_friend_id) REFERENCES customers (id)
);

DROP TABLE IF EXISTS product_author;
create table product_author(
    product_id varchar(36) NOT NULL,
    author_id varchar(36) NOT NULL,
    FOREIGN KEY (product_id) REFERENCES products (id),
    FOREIGN KEY (author_id) REFERENCES author (id)
);

DROP TABLE IF EXISTS favourites;
create table favourites(
    customer_id varchar(36) NOT NULL,
    product_id varchar(36) NOT NULL,
    FOREIGN KEY (customer_id) REFERENCES customers (id),
    FOREIGN KEY (product_id) REFERENCES products (id)
);

DROP TABLE IF EXISTS libraries;
create table libraries(
    customer_id varchar(36) NOT NULL,
    product_id varchar(36) NOT NULL,
    FOREIGN KEY (customer_id) REFERENCES customers (id),
    FOREIGN KEY (product_id) REFERENCES products (id)
);

DROP TABLE IF EXISTS baskets;
create table baskets(
    customer_id varchar(36) NOT NULL,
    product_id varchar(36) NOT NULL,
    FOREIGN KEY (customer_id) REFERENCES customers (id),
    FOREIGN KEY (product_id) REFERENCES products (id)
);

DROP TABLE IF EXISTS customer_friends;
create table customer_friends(
    customer_id varchar(36) NOT NULL,
    friend_id varchar(36) NOT NULL,
    FOREIGN KEY (customer_id) REFERENCES customers (id),
    FOREIGN KEY (friend_id) REFERENCES customers (id)
);

-- Вставляем данные в таблицу author
INSERT INTO author (id, fio) values ('5283df3d-2fc6-47d6-a294-907062e59b95', 'Паустовский К. Г.');
INSERT INTO author (id, fio) values ('9d06624b-c61d-44cd-bb5f-f5f416cae3fc', 'Островский А. Н.');
INSERT INTO author (id, fio) values ('72968333-540d-45af-82e8-5bc80edad381', 'Салтыков-Щедрин М. Е.');
INSERT INTO author (id, fio) values ('690ad554-4a14-4dda-b77b-8105fb39611a', 'Сумароков А. П.');
INSERT INTO author (id, fio) values ('40bd880f-0028-4753-ac53-03fe76119a9e', 'Чехов А. П.');
INSERT INTO author (id, fio) values ('f3c6441f-a64b-4114-8974-6bbc57cd897c', 'Пушкин А. С');
INSERT INTO author (id, fio) values ('017ecef3-b639-4f8e-9654-535aee2ab950', 'Солженицын А. С.');
INSERT INTO author (id, fio) values ('73471d9d-1035-414e-ae04-bc134fff0efd', 'Толстой А. Н.');
INSERT INTO author (id, fio) values ('0a4b672c-5004-412f-8de1-c674ef9dd73f', 'Тютчев Ф. И');
INSERT INTO author (id, fio) values ('fad9cdbd-c0c9-4f45-adb3-0efbfa93a240', 'Фет А. А.');

-- Создаем админа
INSERT INTO users (id, email, password, role, name, surname, patronymic, birth_date, status) VALUES ('71b0af32-887c-4903-bc54-af3f96481e9e', 'admin@mail.ru', '$2a$12$2xHLqWTz63INeUGlIo4U/.tOctTfyYEe41NpMti/mlG7v8wR3DW8K', 'ADMIN', 'Admin', 'Adminov', 'Adminovich', '2000-11-16', 'ACTIVE');
INSERT INTO admin (id) VALUES ('71b0af32-887c-4903-bc54-af3f96481e9e');

-- Создаем продавцов
INSERT INTO users (id, email, password, role, name, surname, patronymic, birth_date, status) VALUES ('407612eb-6798-4e00-8f49-f2d87c8dc88b', 'seller1@mail.ru', '$2a$12$ypp51r/A.LpeuiI1x13V6O25N1Y3/8DJhfFHE7fJDSXa9aofF3zmS', 'SELLER', 'Seller1', 'Иванов', 'Иванович', '2002-05-10', 'ACTIVE');
INSERT INTO users (id, email, password, role, name, surname, patronymic, birth_date, status) VALUES ('275ff24e-563b-49b5-b51b-4fcd5e2a9b97', 'seller2@mail.ru', '$2a$12$DHy0xlnlx4Tht/W1AWccmug1hSqo0Wo4bkp7n0kHZEvlM4E4iBxji', 'SELLER', 'Seller2', 'Кутузов', 'Андреевич', '2002-06-12', 'ACTIVE');
INSERT INTO users (id, email, password, role, name, surname, patronymic, birth_date, status) VALUES ('b4d618c6-fcae-4d67-9766-df503edd9f08', 'seller3@mail.ru', '$2a$12$VOywMHyuZVxtPEJWjtGPH.EBGQnYNejTc4BGcEepIQg44Rer9ohVi', 'SELLER', 'Seller3', 'Сидоров', 'Дмитриевич', '2002-07-14', 'ACTIVE');
INSERT INTO sellers (id) VALUES ('407612eb-6798-4e00-8f49-f2d87c8dc88b');
INSERT INTO sellers (id) VALUES ('275ff24e-563b-49b5-b51b-4fcd5e2a9b97');
INSERT INTO sellers (id) VALUES ('b4d618c6-fcae-4d67-9766-df503edd9f08');

-- Создаем покупателей
INSERT INTO users (id, email, password, role, name, surname, patronymic, birth_date, status) VALUES ('1b7808c8-b2e6-4261-87a9-6895b7900803', 'customer1@mail.ru', '$2a$12$xAvZk/uc.CMICoW.K3Kpn.H7iUOogyTQq0nx9hK4X3DyGH66HkrSS', 'CUSTOMER', 'Customer1', 'Чепчиков', 'Сергеевич', '2003-01-10', 'ACTIVE');
INSERT INTO users (id, email, password, role, name, surname, patronymic, birth_date, status) VALUES ('79404a28-7479-45e6-9283-36f44d9c3c4a', 'customer2@mail.ru', '$2a$12$p4H62jK0q.LpFSdwiDUE8OPeC7Exv.IZUajHQon7JFTp8gqFjKvG.', 'CUSTOMER', 'Customer2', 'Власов', 'Николаевич', '2003-01-11', 'ACTIVE');
INSERT INTO users (id, email, password, role, name, surname, patronymic, birth_date, status) VALUES ('8430ea6a-cc6c-4d2e-9418-89f59b98a7ea', 'customer3@mail.ru', '$2a$12$yEZdgoJqMBmmy4QjiN.zbO45gwoa4kle7QFUZpAteFA1bIEmIZpAu', 'CUSTOMER', 'Customer3', 'Куценко', 'Игоревич', '2003-01-12', 'ACTIVE');
INSERT INTO users (id, email, password, role, name, surname, patronymic, birth_date, status) VALUES ('c22472d5-9bc4-402d-80c5-9e99c57ceb5c', 'customer4@mail.ru', '$2a$12$8JjIdZkj6LN3a8s51.g1tec6ZRSzU4Qp9AYrViFvGM7K6ERrWJxZO', 'CUSTOMER', 'Customer4', 'Абрамова', 'Алексеевна', '2003-01-13', 'ACTIVE');
INSERT INTO users (id, email, password, role, name, surname, patronymic, birth_date, status) VALUES ('eac1c22e-7a9e-4329-bb8a-6650336ecd75', 'customer5@mail.ru', '$2a$12$.k5GbATAH8OAmZS1E/Iwu.zBG4BoHblXvZtWxFUDOLngGw3vqO1UW', 'CUSTOMER', 'Customer5', 'Харитонова', 'Валерьевна', '2003-01-14', 'ACTIVE');
INSERT INTO customers (id, phone, card_number) VALUES ('1b7808c8-b2e6-4261-87a9-6895b7900803', '89607187798', '5e4ea12b-e224-46d1-80d8-c013ed34c72d');
INSERT INTO customers (id, phone, card_number) VALUES ('79404a28-7479-45e6-9283-36f44d9c3c4a', '89607187794', '221409a1-2ab1-4f8e-b8e7-fd1191b32986');
INSERT INTO customers (id, phone, card_number) VALUES ('8430ea6a-cc6c-4d2e-9418-89f59b98a7ea', '89607187793', '551835b4-d257-40cb-8836-779ad4d10653');
INSERT INTO customers (id, phone, card_number) VALUES ('c22472d5-9bc4-402d-80c5-9e99c57ceb5c', '89607187792', '7b078ddd-526e-4095-a4b0-04164f414c87');
INSERT INTO customers (id, phone, card_number) VALUES ('eac1c22e-7a9e-4329-bb8a-6650336ecd75', '89607187791', '3e3b4a6f-5714-4a3a-a2fc-c11d973029b6');


-- Добавляем данные в Products (товары)
INSERT INTO products (id, name, definition, price, quantity, availability, link) VALUES ('e9b8cb9b-cb7b-4bc4-b4aa-8c7fd1677377', 'Золотая роса', 'Описание книги Константина Паустовского - Золотая роса', 5, 999, 10, true, 'https://фантазеры.рф/wa-data/public/shop/products/65/01/90165/images/240643/240643.750x0.jpg');
INSERT INTO products (id, name, definition, price, quantity, availability, link) VALUES ('e147ba9a-73fe-423e-b2d0-d465081c631e', 'Теплый хлеб', 'Описание книги Константина Паустовского - Теплый хлеб', 5, 999, 10, true, 'https://фантазеры.рф/wa-data/public/shop/products/65/01/90165/images/240643/240643.750x0.jpg');
INSERT INTO products (id, name, definition, price, quantity, availability, link) VALUES ('2e8a568e-a5ab-404e-a849-26798f35447d', 'Телеграмма', 'Описание книги Константина Паустовского - Телеграмма', 5, 999, 10, true, 'https://фантазеры.рф/wa-data/public/shop/products/65/01/90165/images/240643/240643.750x0.jpg');
INSERT INTO products (id, name, definition, price, quantity, availability, link) VALUES ('5c72566f-7b0e-4fcc-8f3e-586bc5e7c1bf', 'Свои люди - сочтемся', 'Описание книги Александра Островского - Свои люди - сочтемся', 4, 1209, 10, true, 'https://фантазеры.рф/wa-data/public/shop/products/65/01/90165/images/240643/240643.750x0.jpg');
INSERT INTO products (id, name, definition, price, quantity, availability, link) VALUES ('d8e252af-38f2-4af3-836f-78a8ad67b2df', 'Гроза', 'Описание книги Александра Островского - Гроза', 4, 1099, 10, true, 'https://фантазеры.рф/wa-data/public/shop/products/65/01/90165/images/240643/240643.750x0.jpg');
INSERT INTO products (id, name, definition, price, quantity, availability, link) VALUES ('d1d45df2-b342-4a66-bc7d-62f16b203fc6', 'Снегурочка', 'Описание книги Александра Островского - Снегурочка', 4, 990, 10, true, 'https://фантазеры.рф/wa-data/public/shop/products/65/01/90165/images/240643/240643.750x0.jpg');
INSERT INTO products (id, name, definition, price, quantity, availability, link) VALUES ('e0423b46-4816-4d08-974e-2c95013c35ef', 'Я пришёл к тебе с приветом', 'Описание книги Афанасия Фета - Я пришел к тебе с приветом', 4.5, 915, 10, true, 'https://фантазеры.рф/wa-data/public/shop/products/65/01/90165/images/240643/240643.750x0.jpg');
INSERT INTO products (id, name, definition, price, quantity, availability, link) VALUES ('561595b3-d471-4c86-aa63-c7c74c1fb22e', 'Буря', 'Описание книги Афанасия Фета - Буря', 4.5, 2100, 10, true, 'https://фантазеры.рф/wa-data/public/shop/products/65/01/90165/images/240643/240643.750x0.jpg');
INSERT INTO products (id, name, definition, price, quantity, availability, link) VALUES ('182a6a97-86e0-4049-bbe6-2d4561f335be', 'Медный всадник', 'Описание книги Александра Пушкина - Медный всадник', 4.8, 1999, 10, true, 'https://фантазеры.рф/wa-data/public/shop/products/65/01/90165/images/240643/240643.750x0.jpg');
INSERT INTO products (id, name, definition, price, quantity, availability, link) VALUES ('49a8d1f7-def4-4ad3-b380-c2d912ef3ddf', 'Евгений Онегин', 'Описание романа в стихах, написанного Александром Пушкиным - Евгений онегин', 5, 999, 10, true, 'https://фантазеры.рф/wa-data/public/shop/products/65/01/90165/images/240643/240643.750x0.jpg');

-- Заполнение таблицы product_author для связи книг и их авторов
INSERT INTO product_author (product_id, author_id) VALUES ('e9b8cb9b-cb7b-4bc4-b4aa-8c7fd1677377', '5283df3d-2fc6-47d6-a294-907062e59b95');
INSERT INTO product_author (product_id, author_id) VALUES ('e147ba9a-73fe-423e-b2d0-d465081c631e', '5283df3d-2fc6-47d6-a294-907062e59b95');
INSERT INTO product_author (product_id, author_id) VALUES ('2e8a568e-a5ab-404e-a849-26798f35447d', '5283df3d-2fc6-47d6-a294-907062e59b95');
INSERT INTO product_author (product_id, author_id) VALUES ('5c72566f-7b0e-4fcc-8f3e-586bc5e7c1bf', '9d06624b-c61d-44cd-bb5f-f5f416cae3fc');
INSERT INTO product_author (product_id, author_id) VALUES ('d8e252af-38f2-4af3-836f-78a8ad67b2df', '9d06624b-c61d-44cd-bb5f-f5f416cae3fc');
INSERT INTO product_author (product_id, author_id) VALUES ('d1d45df2-b342-4a66-bc7d-62f16b203fc6', '9d06624b-c61d-44cd-bb5f-f5f416cae3fc');
INSERT INTO product_author (product_id, author_id) VALUES ('e0423b46-4816-4d08-974e-2c95013c35ef', 'fad9cdbd-c0c9-4f45-adb3-0efbfa93a240');
INSERT INTO product_author (product_id, author_id) VALUES ('561595b3-d471-4c86-aa63-c7c74c1fb22e', 'fad9cdbd-c0c9-4f45-adb3-0efbfa93a240');
INSERT INTO product_author (product_id, author_id) VALUES ('182a6a97-86e0-4049-bbe6-2d4561f335be', 'f3c6441f-a64b-4114-8974-6bbc57cd897c');
INSERT INTO product_author (product_id, author_id) VALUES ('49a8d1f7-def4-4ad3-b380-c2d912ef3ddf', 'f3c6441f-a64b-4114-8974-6bbc57cd897c');