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

INSERT INTO category(name) VALUES('Халуун'),
('Хүйтэн'),
('Ундаа');

INSERT INTO item(id, name, price, status, category_id) VALUES ('1','шарсан төмс',3000,'бэлэн', '1'),
(2,'попкорн',6000,'бэлэн', '1'),
(3,'стрипс 5ш',5000,'бэлэн', '1'),
(4,'кальзоне',3900,'бэлэн', '1'),
(5,'зайрмаг',2000,'бэлэн', '2'),
(6,'зингэр бүргер',5500,'бэлэн', '1'),
(7,'твистэр',5000,'бэлэн', '1'),
(8,'ундаа',800,'бэлэн', '3');

INSERT INTO `iset` VALUES (1,'хүүхдийн сэт',10000,'1ш бургер, 1ш төмс, 1ш ундааа','бэлэн'),
(2,'стрипс сэт',6000,'стрипс','бэлэн'),
(3,'твистер сэт',10500,'твистер','бэлэн');

INSERT INTO set_item(id, item_id, set_id) VALUES ('1','1','1'),
('2','6','1'),
('3','7','3'),
('4','8','1'),
('5','8','3'),
('6','1','3'),
('7','3','2'),
('8','8','2'),
('9','1','2');
