drop table if exists dict;
create table dict (id integer primary key,value varchar(50),idf real);
drop table if exists doc;
create table doc (id integer primary key,title varchar(50),keywords varchar(50),text text,url varchar(50));
drop table if exists dict_doc;
create table dict_doc (id integer primary key,dict_id integer,doc_id integer,tf integer,tf2 real,tf3 real,title_include boolean);
create index dict_doc_dict_id on dict_doc(dict_id);
create index dict_doc_doc_id on dict_doc(doc_id);