--dict data
insert into dict(id,value) values(1,'test');

--doc data
insert into doc(id,title,text,url) values(1,'test','我们要做一些test','http://www.baidu.com');
insert into doc(id,title,text,url) values(2,'tdd','test driven development','http://z.cn');

--dict_doc data
insert into dict_doc(id,dict_id,doc_id,tf) values(1,1,1,1)
insert into dict_doc(id,dict_id,doc_id,tf) values(2,1,2,1)