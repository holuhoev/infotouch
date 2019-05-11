DROP TABLE IF EXISTS building CASCADE;
CREATE TABLE building
(
  ID            integer NOT NULL PRIMARY KEY,
  address       text    NULL,
  building_name text    NULL,
  city          integer NULL,
  CONSTRAINT BUILDING_ID_uindex UNIQUE (ID)
);

DROP TABLE IF EXISTS auditorium;
CREATE TABLE auditorium
(
  ID              integer                          NOT NULL PRIMARY KEY,
  auditorium_type text                             NULL,
  number          text                             NULL,
  building_id     integer references building (ID) NOT NULL,
  CONSTRAINT AUDITORIUM_ID_uindex UNIQUE (ID)
);

DROP TABLE IF EXISTS faculty CASCADE;
CREATE TABLE faculty
(
  ID           integer NOT NULL PRIMARY KEY,
  faculty_name text    NULL,
  CONSTRAINT FACULTY_ID_uindex UNIQUE (ID)
);

DROP TABLE IF EXISTS chair CASCADE;
CREATE TABLE chair
(
  ID           integer                         NOT NULL PRIMARY KEY,
  faculty_id   integer references faculty (ID) NULL,
  code         text                            NULL,
  chair_name   text                            NULL,
  faculty_name text                            null default '',
  chair_city   integer                         NULL,
  CONSTRAINT CHAIR_ID_uindex UNIQUE (ID)
);

DROP TABLE IF EXISTS lecturer CASCADE;
CREATE TABLE lecturer
(
  ID            integer                       NOT NULL PRIMARY KEY,
  chair_id      integer references chair (ID) NULL,
  fio           text                          NULL,
  short_fio     text                          NULL,
  chair_name    text                          not null default '',
  lecturer_city integer                       NULL,
  faculty_name  text                          not null default '',
  url           text                          NULL,
  CONSTRAINT LECTURER_ID_uindex UNIQUE (ID)
);

drop table if exists person CASCADE;
create table person
(
  ID        uuid not null PRIMARY KEY,
  fio       text not null default '',
  url       text not null default '',
  emails    text null     default '',
  avatarUrl text null     default '',
  CONSTRAINT person_ID_uindex UNIQUE (ID)
);


DROP SEQUENCE if exists employee_seq;
CREATE SEQUENCE employee_seq start with 1042;
drop table if exists employee cascade;
create table employee
(
  ID          uuid                          not null PRIMARY KEY,
  person_id   uuid references person (id)   not null,
  chair_id    integer references chair (id) null,
  position    text default ''               not null,
  lecturer_id integer                       null,
  CONSTRAINT EMPLOYEE_ID_uindex UNIQUE (ID)
);


create table building_scheme
(
  id          serial not null primary key,
  floor       int    not null,
  building_id int    not null references building (id),
  CONSTRAINT building_scheme_id_uindex unique (id)
);

create table point
(
  id                 serial not null primary key,
  x                  int    not null,
  y                  int    not null,
  building_scheme_id int    not null references building_scheme (id),
  CONSTRAINT point_id_uindex unique (id)
);


create table map_element
(
  id                 serial not null primary key unique,
  coordinates        text   not null,
  label              text   null default '',
  building_scheme_id int    not null references building_scheme (id),
  point_id           int    null references point (id),
  --   corridor, room, stairs, door
  element_type       int    not null,
  CONSTRAINT map_element_id_uindex UNIQUE (id)
);


create table edge
(
  id             serial                    not null primary key,
  left_point_id  int references point (id) not null,
  right_point_id int references point (id) not null,
  weight         int default 1             null
);
