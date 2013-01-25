update dict_doc set title_include = 1
where id in (
select dd.id from doc do
inner join dict_doc dd on do.id = dd.doc_id
inner join dict di on di.id = dd.dict_id
where do.title like '%'||di.value||'%'
)

update dict_doc set title_include = 0 where title_include is null