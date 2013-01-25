create table temp(id integer primary key,tf3 real)

insert into temp
select dd.id,0.4+0.6*dd.tf/t.max_tf tf3 from dict_doc dd
inner join (
select doc_id,max(tf) max_tf from dict_doc
group by doc_id
) t on dd.doc_id = t.doc_id

select count(*) from temp
select count(*) from dict_doc

update dict_doc set tf3 = (select tf3 from temp where dict_doc.id = temp.id)