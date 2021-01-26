drop table if exists ITEM;
drop table if exists DOCUMENT;
drop table if exists BOX;

create table if not exists DOCUMENT
(
    id bigint auto_increment not null,
    sortation_id bigint,
    name varchar(255) not null unique,
    color varchar(20),
    primary key(id)
);

create table if not exists BOX
(
    id bigint auto_increment not null,
    doc_id bigint,
    primary key(id)
);

create table if not exists ITEM
(
    id bigint auto_increment not null,
    type varchar(255) not null,
    name varchar(255) not null,
    value varchar(255),
    box_id bigint not null,
    primary key(id),
    constraint boxref foreign key (box_id) references box(id)
);

insert into box  values(1,1);