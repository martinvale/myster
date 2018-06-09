create table survey (
  id bigint not null auto_increment,
  name varchar(100),
  enabled bit(1),
  primary key(id)
);

create table category (
  id bigint not null auto_increment,
  survey_id bigint not null,
  name varchar(100) not null,
  position int not null,
  primary key(id),
  constraint fk_category_survey foreign key fk_category_survey (survey_id) references survey(id)
);

create table survey_item (
  id bigint not null auto_increment,
  category_id bigint not null,
  position int not null,
  title varchar(2000) not null,
  description varchar(2000) default null,
  type varchar(20) not null,
  primary key(id),
  constraint fk_survey_item_category foreign key fk_survey_item_category (category_id) references category(id)
);

create table choice (
  id bigint not null auto_increment,
  survey_item_id bigint not null,
  description varchar(2000) not null,
  value int not null,
  primary key(id),
  constraint fk_choice_survey_item foreign key fk_item_option (item_option_id) references single_choice(id)
);

create table user (
  id bigint not null auto_increment,
  username varchar(100) not null,
  password varchar(50) not null,
  first_name varchar(70) not null,
  last_name varchar(70) not null,
  enabled bit(1),
  primary key(id)
);

create table shopper (
  id bigint not null auto_increment,
  user_id bigint not null,
  primary key(id),
  constraint fk_shopper_user foreign key fk_shopper_user (user_id) references user(id)
);

create table assignment (
  id bigint not null auto_increment,
  shopper_id bigint not null,
  survey_id bigint not null,
  point_of_sale_id bigint not null,
  state varchar(10) not null,
  visit_date date default null,
  in_time time default null,
  out_time time default null,
  primary key(id),
  constraint fk_assignment_shopper foreign key fk_assignment_shopper (shopper_id) references shopper(id),
  constraint fk_assignment_survey foreign key fk_assignment_survey (survey_id) references survey(id),
  constraint fk_assignment_point_of_sale foreign key fk_assignment_point_of_sale (point_of_sale_id) references point_of_sale(id)
);

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

create table company (
  id bigint not null auto_increment,
  name varchar(100) not null,
  primary key(id)
);

create table point_of_sale (
  id bigint not null auto_increment,
  company_id bigint not null,
  address varchar(1000) not null,
  primary key(id),
  constraint fk_point_of_sale_company foreign key fk_point_of_sale_company (company_id) references company(id)
);

create table response (
  id bigint not null auto_increment,
  assignment_id bigint not null,
  survey_item_id bigint not null,
  type varchar(20) not null,
  choice_id bigint default null,
  value varchar(2000) not null,
  primary key(id),
  constraint fk_response_assigment foreign key fk_response_assignment (assignment_id) references assignment(id),
  constraint fk_response_survey_item foreign key fk_response_survey_item (survey_item_id) references survey_item(id)
  constraint fk_response_choice foreign key fk_response_choice (choice_id) references choice(id)
);

insert into user (username, password, first_name, last_name, enabled) values ('noelice@msn.com', 'noelice', 'Noelice', 'Correia de Oliveira', 1);
insert into user (username, password, first_name, last_name, enabled) values ('martinvalletta@gmail.com', 'test', 'Martin Horacio', 'Valletta', 1);
insert into user (username, password, first_name, last_name, enabled) values ('jlroffo@shopnchek.com.ar', 'jlroffo', 'Jose Luis', 'Roffo', 1);
insert into shopper (user_id) select id from user where username = 'noelice@msn.com';
insert into shopper (user_id) select id from user where username = 'martinvalletta@gmail.com';
insert into shopper (user_id) select id from user where username = 'jlroffo@shopnchek.com.ar';

insert into country (name) values ('Argentina');
insert into state (name, priority, country_id) select 'Ciudad Autonoma de Buenos Aires', 1, id from country where name = 'Argentina';
insert into state (name, priority, country_id) select 'Buenos Aires', 2, id from country where name = 'Argentina';
insert into state (name, country_id) select 'Cordoba', id from country where name = 'Argentina';
insert into state (name, country_id) select 'Santa Fe', id from country where name = 'Argentina';

insert into company (name) values ('Carrefour Express');
insert into location (address, state_id) select 'Corrientes 345', id from state where name = 'Ciudad Autonoma de Buenos Aires';
insert into location (address, state_id) select 'Hidalgo 80', id from state where name = 'Ciudad Autonoma de Buenos Aires';
insert into point_of_sale (company_id, name, address) select id, 'Congreso', 'Corrientes 345' from company;
insert into point_of_sale (company_id, name, address) select id, 'Caballito', 'Hidalgo 80' from company;

insert into survey(name, enabled) values ('Carrefour', 1);

insert into category (survey_id, name, position) select id, 'Exterior de la tienda', 1 from survey where name = 'Carrefour';
insert into category (survey_id, name, position) select id, 'Interior de la tienda', 2 from survey where name = 'Carrefour';
insert into category (survey_id, name, position) select id, 'Salon de venta', 3 from survey where name = 'Carrefour';
insert into category (survey_id, name, position) select id, 'Caja', 4 from survey where name = 'Carrefour';
insert into category (survey_id, name, position) select id, 'Evaluacion subjetiva', 5 from survey where name = 'Carrefour';

insert into survey_item (category_id, position, title, description, type) select id, 0,
    '¿La vereda de la tienda se encontraba limpia y en buen estado?',
    'La vereda debe estar limpia, sin basura. No debe haber baldosas rotas.', 'SINGLE_CHOICE'
    from category where name = 'Exterior de la tienda';
insert into choice(item_option_id, description, value) select id, 'Si', 100 from survey_item
    where title = '¿La vereda de la tienda se encontraba limpia y en buen estado?';
insert into choice(item_option_id, description, value) select id, 'No', 0 from survey_item
    where title = '¿La vereda de la tienda se encontraba limpia y en buen estado?';

insert into survey_item (category_id, position, title, description, type) select id, 0,
    '¿La marquesina exterior estaba en buen estado?',
    'Vea una foto de la marquesina exterior en su Guía de Trabajo. La marquesina está compuesta por el cartel de Carrefour que se encuentra en el frente de la tienda.', 'SINGLE_CHOICE'
    from category where name = 'Exterior de la tienda';
insert into choice(item_option_id, description, value) select id, 'Si', 100 from survey_item
    where title = '¿La marquesina exterior estaba en buen estado?';
insert into choice(item_option_id, description, value) select id, 'No', 0 from survey_item
    where title = '¿La marquesina exterior estaba en buen estado?';

insert into survey_item (survey_id, title, description, type) select id,
    'Comentarios generales:', null, 'TEXT' from survey where name = 'Carrefour';
insert into survey_item (category_id, position, title, description, type) select id, 1,
    'Archivos adjuntos:', null, 'FILE' from category where name = 'Evaluacion subjetiva';

insert into assignment (shopper_id, survey_id, location_id, state) values (1, 2, 1, 'PENDING');
insert into response (assignment_id, item_option_id) values (1, 1);