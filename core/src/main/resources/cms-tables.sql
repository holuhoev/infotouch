-- Доменная модель
DROP TABLE IF EXISTS "hse_user" CASCADE;
CREATE TABLE "hse_user"
(
  id         serial      not null primary key,
  first_name text default '',
  last_name  text default '',
  login      text unique not null,
  role       integer
);

DROP TABLE IF EXISTS device CASCADE;
CREATE TABLE device
(
  id          serial not null primary key,
  title       text default '',
  description text default '',
  location    geometry(Point, 4326)
);

DROP TABLE IF EXISTS "tag" CASCADE;
CREATE TABLE "tag"
(
  id    serial not null primary key,
  title text   not null unique
);

DROP TABLE IF EXISTS "topic" CASCADE;
CREATE TABLE "topic"
(
  id    serial not null primary key,
  title text   not null unique,
  color text   not null
);

DROP TABLE IF EXISTS "hse_location" CASCADE;
CREATE TABLE "hse_location"
(
  id          serial                not null primary key,
  type        integer,
  title       text                  not null unique,
  location    geometry(Point, 4326) null,
  building_id integer               not null references building (ID),
  point_id    integer               null references point (ID)
);


DROP TABLE IF EXISTS "news" CASCADE;
CREATE TABLE "news"
(
  id         serial not null primary key,
  title      text   not null,
  content    text default '',
  image      bytea,
  image_url  text default '',
  event_date date,
  topic_id   int references "topic" (id),
  start_time time,
  end_time   time,
  start_date date,
  end_date   date,
  created_by int references "hse_user" (id)
);

DROP TABLE IF EXISTS "ad" CASCADE;
CREATE TABLE "ad"
(
  id         serial not null primary key,
  title      text   not null,
  content    text default '',
  image      bytea,
  video      bytea,
  --   Убрать Видео из модели, хранить его отдельно, как preview
  start_time time,
  end_time   time,
  start_date date,
  end_date   date,
  created_by int references "hse_user" (id)
);

DROP TABLE IF EXISTS "announcement" CASCADE;
CREATE TABLE "announcement"
(
  id         serial not null primary key,
  title      text   not null,
  content    text default '',
  priority   int    not null,
  link       text default '',
  start_date date   not null,
  end_date   date   not null,
  created_by int references "hse_user" (id)
);


-- Авторизация
DROP TABLE IF EXISTS "user2device" CASCADE;
CREATE TABLE "user2device"
(
  user_id      int not null references "hse_user" (id),
  device_id    int not null references "device" (id),
  access_right int not null,
  PRIMARY KEY (user_id, device_id)
);

DROP TABLE IF EXISTS "user2ad" CASCADE;
CREATE TABLE "user2ad"
(
  id           serial not null primary key,
  user_id      int    not null references "hse_user" (id),
  ad_id        int    not null references "ad" (id),
  access_right int    not null
);

DROP TABLE IF EXISTS "user2announcement" CASCADE;
CREATE TABLE "user2announcement"
(
  id              serial not null primary key,
  user_id         int    not null references "hse_user" (id),
  announcement_id int    not null references "announcement" (id),
  access_right    int    not null
);

DROP TABLE IF EXISTS "user2news" CASCADE;
CREATE TABLE "user2news"
(
  id           serial not null primary key,
  user_id      int    not null references "hse_user" (id),
  news_id      int    not null references "news" (id),
  access_right int    not null
);


--  Связи терминалов
DROP TABLE IF EXISTS "event_url" CASCADE;
CREATE TABLE "event_url"
(
  id        serial not null primary key,
  device_id int    not null references "device" (id),
  url       text   not null
);

DROP TABLE IF EXISTS "device2ad" CASCADE;
CREATE TABLE "device2ad"
(
  device_id int not null references "device" (id),
  ad_id     int not null references "ad" (id),
  PRIMARY KEY (device_id, ad_id)
);

DROP TABLE IF EXISTS "device2announcement" CASCADE;
CREATE TABLE "device2announcement"
(
  device_id       int not null references "device" (id),
  announcement_id int not null references "announcement" (id),
  PRIMARY KEY (device_id, announcement_id)
);

DROP TABLE IF EXISTS "device2news" CASCADE;
CREATE TABLE "device2news"
(
  device_id int not null references "device" (id),
  news_id   int not null references "news" (id),
  PRIMARY KEY (device_id, news_id)
);

-- Связи новостей
DROP TABLE IF EXISTS "news2tag" CASCADE;
CREATE TABLE "news2tag"
(
  id      serial not null primary key,
  news_id int    not null references "news" (id),
  tag_id  int    not null references "tag" (id)
);

drop table if exists "hse_unit" cascade;
create table "hse_unit"
(
  id             serial  not null primary key,
  title          text default '',
  description    text default '',
  building_id    integer not null references building (id),
  map_element_id integer null references map_element (id),
  CONSTRAINT hse_unit_ID_uindex UNIQUE (ID)
);

