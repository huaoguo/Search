--dict data
insert into dict(id,value) values(1,'test');

--doc data
insert into doc(id,text,url) values(1,'����Ҫ��һЩtest','http://www.baidu.com');

--dict_doc data
insert into dict_doc(id,dict_id,doc_id,tf) values(1,1,1,1)