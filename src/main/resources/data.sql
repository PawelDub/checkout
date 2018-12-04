drop table if exists basket;
drop table if exists item;
drop table if exists basket_item;
drop table if exists price_discount;
drop sequence if exists SEQ_BASKET;
drop sequence if exists SEQ_ITEM;
drop sequence if exists SEQ_BASKET_ITEM;
drop sequence if exists SEQ_PRICE_DISCOUNT;

create sequence if not exists SEQ_BASKET start with 1 increment by 1 nocache minvalue 1 maxvalue 9999999999999 nocycle ;

create table if not exists basket (
  basket_id   int sequence SEQ_BASKET primary key,
  status      varchar2(15) not null  check (status in ('NEW', 'ACTIVE', 'CANCEL', 'CLOSED')),
  total_price DECIMAL(19, 2)   not null,
);

CREATE INDEX basket_id
  ON basket (basket_id);

create sequence if not exists SEQ_ITEM start with 1 increment by 1 nocache minvalue 1 maxvalue 9999999999999 nocycle ;

create table if not exists item (
  item_id  int sequence SEQ_ITEM primary key,
  type     varchar2(32) not null unique,
  price    decimal(19, 2)   not null
);

CREATE INDEX item_type
  ON item (type);

create sequence if not exists SEQ_BASKET_ITEM start with 1 increment by 1 nocache minvalue 1 maxvalue 9999999999999 nocycle ;

CREATE TABLE if not exists basket_item (
  basket_item_id int sequence SEQ_BASKET_ITEM primary key,
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

create sequence if not exists seq_price_discount start with 1 increment by 1 nocache minvalue 1 maxvalue 9999999999999 nocycle ;

create table if not exists price_discount (
  discount_id int sequence seq_price_discount primary key,
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
-- insert into price_discount values (1, 'koszule', 3, 70.00);
-- insert into price_discount values (2, 'spodnie', 2, 15);
-- insert into price_discount values (3, 'buty', 4, 60);
-- insert into price_discount values (4, 'rekawiczki', 2, 40);
