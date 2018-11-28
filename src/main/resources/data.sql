drop table if exists basket;
drop table if exists item;
drop table if exists basket_item;
drop table if exists price_discount;

create table if not exists basket (
  basket_id   int primary key,
  status      varchar2(15) not null  check (status in ('NEW', 'ACTIVE', 'CANCEL', 'CLOSED')),
  total_price DECIMAL(19, 2)   not null,
);

CREATE INDEX basket_id
  ON basket (basket_id);

create table if not exists item (
  item_id  int primary key,
  type     varchar2(32) not null unique,
  price    decimal(19, 2)   not null
);

CREATE INDEX item_type
  ON item (type);

CREATE TABLE if not exists basket_item (
  basket_item_id int primary key,
  basket_id int NOT NULL,
  item_id   int NOT NULL,
  item_quantity int NOT NULL,
  CONSTRAINT basket_item_id_UNIQUE
  UNIQUE (basket_id, item_id),
  foreign key (basket_id) references basket(basket_id),
  foreign key (item_id) references item(item_id),
);

CREATE INDEX basket_item_basket_id
  ON basket_item (basket_id);

create table if not exists price_discount (
  discount_id int primary key,
  type     varchar2(32) not null,
  quantity int not null ,
  price_discount decimal(19, 2) not null
);

ALTER TABLE price_discount
  ADD CONSTRAINT uq_discountprice UNIQUE (type, quantity, price_discount);

-- temporary for developing time
-- insert into item values (1, 'koszule', 40);
-- insert into item values (2, 'spodnie', 10);
-- insert into item values (3, 'buty', 30);
-- insert into item values (4, 'rekawiczki', 25);
-- insert into item values (5, 'kurtki', 45);
-- insert into item values (6, 'palta', 80);
--
--
-- insert into discount values (1, 'koszule', 3, 70.00);
-- insert into discount values (2, 'spodnie', 2, 15);
-- insert into discount values (3, 'buty', 4, 60);
-- insert into discount values (4, 'rekawiczki', 2, 40);
