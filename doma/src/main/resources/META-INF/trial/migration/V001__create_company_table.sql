create sequence company_seq start with 100 increment by 1;

create table company(
    id integer not null primary key,
    name varchar(255) not null
);
