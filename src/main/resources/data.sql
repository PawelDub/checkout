drop table if exists basket;
drop table if exists item;

create table basket(
  basket_id int primary key,
  status varchar2(15) not null  check (my_column in ('NEW', 'ACTIVE', 'CANCEL', 'CLOSED')),
  total_price DECIMAL(2) not null,
);

create table item (
  item_id int primary key,
  name varchar2(256) not null,
  type varchar2(32) not null,
  price decimal(2) not null
);

create table basket_item (
  basket_id int,
  item_id int
);