create table if not exists site(
                                     id serial primary key,
                                     site text,
                                     login text,
                                     password text
);

create table if not exists url(
                                   id serial primary key,
                                   site_id int references site(id),
                                   shorturl text,
                                   url text,
                                   counter int default 0
);
