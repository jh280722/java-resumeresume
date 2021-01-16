drop table if exists ITEM;
drop table if exists DOCUMENT;
drop domain if exists itemType;

create domain itemType as varchar check value in (
    'text', 'textArea', 'image', 'date','period','dropDown'
    );
create table if not exists ITEM
(
    id bigint auto_increment not null,
    type varchar(255) not null,
    name varchar(255) not null,
    value varchar(255),
    primary key(id)
    );

create table if not exists DOCUMENT
(
    id bigint auto_increment not null,
    sortation_id bigint,
    name varchar(255) not null unique,
    color varchar(20),
    primary key(id)
    );