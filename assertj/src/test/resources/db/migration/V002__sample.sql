alter table example add column col3 int not null default 0;

truncate table example;

insert into example(col1, col2, col3) values
('AAA', 'aaa', 1),
('BBB', 'bbb', 2),
('CCC', 'ccc', 3),
('DDD', 'ddd', 4)
;
