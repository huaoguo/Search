drop table if exists dict;
create table dict (id integer primary key,value varchar(50),idf real);
drop table if exists doc;
create table doc (id integer primary key,text text,url varchar(50));
drop table if exists dict_doc;
create table dict_doc (id integer primary key,dict_id integer,doc_id integer,tf integer);