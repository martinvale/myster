create table country (
  id bigint not null auto_increment,
  name varchar(100) not null,
  priority int not null default 5,
  primary key(id)
);

create table state (
  id bigint not null auto_increment,
  name varchar(100) not null,
  priority int not null default 5,
  country_id bigint not null,
  primary key(id),
  constraint fk_state_country foreign key fk_state_country (country_id) references country(id)
);

create table location (
  id bigint not null auto_increment,
  address varchar(1000) not null,
  state_id bigint not null,
  primary key(id),
  constraint fk_location_state foreign key fk_location_state (state_id) references state(id)
);

insert into country (name) values ('Argentina');
insert into state (name, priority, country_id) select 'Ciudad Autonoma de Buenos Aires', 1, id from country where name = 'Argentina';
insert into state (name, priority, country_id) select 'Buenos Aires', 2, id from country where name = 'Argentina';
insert into state (name, country_id) select 'Cordoba', id from country where name = 'Argentina';
insert into state (name, country_id) select 'Santa Fe', id from country where name = 'Argentina';
insert into location (address, state_id) select 'Corrientes 345', id from state where name = 'Ciudad Autonoma de Buenos Aires';
insert into location (address, state_id) select 'Hidalgo 80', id from state where name = 'Ciudad Autonoma de Buenos Aires';

update point_of_sale pos set address = 'Corrientes 345' where address = 'Corrientes 345, Buenos Aires';
alter table point_of_sale add column location_id bigint default null;
update point_of_sale pos
  inner join location l on (pos.address = l.address)
set pos.location_id = l.id;

alter table point_of_sale drop column address;
alter table point_of_sale change column location_id location_id bigint not null;
alter table point_of_sale add constraint fk_point_of_sale_location foreign key fk_point_of_sale_location (location_id) references location(id);

alter table point_of_sale add column code varchar(50) not null after name;

update point_of_sale set code = 'CON1' where name = 'Congreso';
update point_of_sale set code = 'CAB1' where name = 'Caballito';