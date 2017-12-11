DROP DATABASE kfc;
CREATE DATABASE kfc;
USE kfc;
CREATE TABLE `category` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) CHARACTER SET utf8 NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

CREATE TABLE `item` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) CHARACTER SET utf8 NOT NULL,
  `price` int(11) NOT NULL,
  `status` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `category_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `product_category_ibfk_1` (`category_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

CREATE TABLE `iset` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) CHARACTER SET utf8 NOT NULL,
  `price` int(11) NOT NULL,
  `description` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `status` varchar(45) CHARACTER SET utf8 DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

CREATE TABLE `set_item` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `item_id` int(11) NOT NULL,
  `set_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT fk_item_set_item FOREIGN KEY (item_id) REFERENCES item (id),
  CONSTRAINT fk_iset_set_item FOREIGN KEY (set_id) REFERENCES iset (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

CREATE TABLE `iorder` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `date` datetime NOT NULL,
  `total_price` int(11) NOT NULL,
  `status` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

CREATE TABLE `order_item` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `order_id` int(11) NOT NULL,
  `set_id` int(11) DEFAULT NULL,
  `item_id` int(11) DEFAULT NULL,
  `quantity` int(11) NOT NULL DEFAULT 1,
  `price` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT fk_order_order_item FOREIGN KEY (order_id) REFERENCES iorder (id),
  CONSTRAINT fk_iset_order_item FOREIGN KEY (set_id) REFERENCES iset (id),
  CONSTRAINT fk_item_order_item FOREIGN KEY (item_id) REFERENCES item (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

INSERT INTO category(name) VALUES('Бүргер'),
('Тахиа'),
('Ундаа'),
('Хайрцагтай'),
('Зууш'),
('Салат'),
('Десерт'),
('Сүмс'),
('Бэлэн');

INSERT INTO item(name, price, status, category_id) VALUES ('Шарсан төмс',3000,'бэлэн', '5'),
('попкорн',3500,'бэлэн', '10'),
('стрипс 5ш',5000,'бэлэн', '2'),
('Сиртэй сүмс',800,'бэлэн','8'),
('зайрмаг',2000,'бэлэн', '7'),
('зингэр бүргер',5500,'бэлэн', '1'),
('твистэр',5000,'бэлэн', '1'),
('ундаа',800,'бэлэн', '3'),
('Биггер',7960,'бэлэн','1'),
('Ай-Твистер Чиз',2000,'бэлэн','1'),
('Твистер Веджи',5800,'бэлэн','1'),
('Лонгер BBQ',2000,'бэлэн','1'),
('Тауэр Русс',5160,'бэлэн','1'),
('Дабл Шефбургер',8800,'бэлэн','1'),
('Шефбургер',3960,'бэлэн','1'),
('Боксмастер из тостера',7040,'бэлэн','1'),
('1 Хөл',2000,'бэлэн','2'),
('2 Далавч',2000,'бэлэн','2'),
('9 Далавч',9320,'бэлэн','2'),
('6 Далавч',7000,'бэлэн','2'),
('3 Далавч',3960,'бэлэн','2'),
('2 Хөл',3920,'бэлэн','2'),
('3 Хөл',5880,'бэлэн','2'),
('Стар-баскет',7960,'бэлэн','4'),
('Пати Баскет',3960,'бэлэн','4'),
('Баскет фри',3960,'бэлэн','5'),
('Цезарь Салат',4760,'бэлэн','6'),
('Зун зайрмаг',1160,'бэлэн','7'),
('Чиз кейк Карамелтай',5000,'бэлэн','7'),
('Чиз кейк Гүзээлгэнэтэй',5000,'бэлэн','7'),
('Чиз кейк Шоколадтай',4900,'бэлэн','7'),
('Тирамису',3960,'бэлэн','7'),
('Мөрөөдлийн зайрмаг Карамелтай',3400,'бэлэн','7'),
('Мөрөөдлийн зайрмаг Шоколадтай',3960,'бэлэн','7'),
('пепси 0.3л',2000,'бэлэн','3'),
('пепси 0.4л',2400,'бэлэн','3'),
('пепси 0.5л',2800,'бэлэн','3'),
('пепси 0.8л',3200,'бэлэн','3'),
('Кофе Эспрессо 0,1',2760,'бэлэн','3'),
('Кофе Американо 0,3',3160,'бэлэн','3'),
('Кофе Капучино 0,3',3960,'бэлэн','3'),
('Чай 0,4л',2760,'бэлэн','3'),
('Кофе Латте 0,3',3960,'бэлэн','3'),
('Пепси кола 1,25 л',4800,'бэлэн','3'),
('Пепси Черри 0,6 л',3160,'бэлэн','3'),
('Кетчуп томатный',800,'бэлэн','8'),
('Teriyaki сүмс',800,'бэлэн','8'),
('Барбекью сүмс',800,'бэлэн','8'),
('Чили сүмс',800,'бэлэн','8'),
('Сармистай сүмс',800,'бэлэн','8');

INSERT INTO `iset` VALUES (1,'хүүхдийн сэт',10000,'1ш бургер, Шарсан төмс, 1ш ундааа','бэлэн'),
(2,'стрипс сэт',6000,'стрипс','бэлэн'),
(3,'твистер сэт',10500,'твистер','бэлэн'),
(4,'Бизнес багц',11960,'1ш Боксмастер из тостера, 2 Далавч, Шарсан төмс, 1ш Пепси Черри 0,6 л','бэлэн'),
(5,'Үдийн хоол',7800,'2 Далавч, Шарсан төмс, 1ш жүржийн шүүс, 1ш зайрмаг','бэлэн'),
(6,'Шефбургер багц',9080,'1ш Дабл Шефбургер, 1ш Шефбургер, 1ш пепси 0.3л, шарсан төмс','бэлэн'),
(7,'Бизнес үд багц',7960,'1ш Твистер Веджи, 2 Далавч, шарсан төмс, Пепси Черри 0,6 л','бэлэн');

INSERT INTO set_item(id, item_id, set_id) VALUES ('1','1','1'),
('2','6','1'),
('3','7','3'),
('4','8','1'),
('5','8','3'),
('6','1','3'),
('7','3','2'),
('8','8','2'),
('9','1','2'),
('10','16','4'),
('11','18','4'),
('12','1','4'),
('13','45','4'),
('14','18','5'),
('15','45','5'),
('16','5','5'),
('17','14','6'),
('18','15','6'),
('19','45','6'),
('20','1','6'),
('21','11','7'),
('22','18','7'),
('23','1','7'),
('24','45','7');
