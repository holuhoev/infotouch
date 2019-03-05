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
  auditorium_type integer                          NULL,
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
  ID         integer                         NOT NULL PRIMARY KEY,
  faculty_id integer references faculty (ID) NULL,
  code       text                            NULL,
  chair_name text                            NULL,
  city       integer                         NULL,
  CONSTRAINT CHAIR_ID_uindex UNIQUE (ID)
);

DROP TABLE IF EXISTS lecturer CASCADE;;
CREATE TABLE lecturer
(
  ID        integer                       NOT NULL PRIMARY KEY,
  chair_id  integer references chair (ID) NULL,
  fio       text                          NULL,
  short_fio text                          NULL,
  link      text                          NULL,
  CONSTRAINT LECTURER_ID_uindex UNIQUE (ID)
);