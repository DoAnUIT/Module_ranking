delimiter //

create procedure start_ranking(period_time int)
begin
insert into module_ranking
select IDTableArticle, Title,Url,IDTableCategory,IDTableMagazine,Description,UrlPicture,ArticleDate,FbLike + articlelike, FbCmt,FbShare
from Article
where  datediff(now(),ArticleDate) < period_time;

update module_ranking as m inner join

(SELECT SoluongCmt.IdNews, (SoluongCmt.Tong + SoluongCmt.TotalSub) as TongCong
from (SELECT KetQua.IdNews, count(DISTINCT KetQua.IdParent) as Tong, SUM(KetQua.Total) as TotalSub
from (SELECT Result.IdNews, Result.IdParent, count(DISTINCT Result.IdSub) as Total
FROM
(SELECT TestTable.IdNews as IdNews, TestTable.IdParent as IdParent, subcmt.IDTableSubCmt as IdSub
FROM
(
    SELECT article.IDTableArticle as IdNews, parentcmt.IDTableParentCmt as IdParent
    FROM article LEFT JOIN parentcmt
ON article.IDTableArticle = parentcmt.IDTableArticle 
where datediff(now(),article.articledate) < period_time) as TestTable LEFT JOIN subcmt
on TestTable.IdParent = subcmt.IDTableParentCmt) as Result
GROUP BY Result.IdNews, Result.IdParent) as KetQua
GROUP BY KetQua.IdNews) as SoluongCmt) as tempttable
set m._cmt = m._cmt + tempttable.Tongcong
where m.idtablearticle = tempttable.idNews;

insert into news (id,title,contenturl,categoryid,magazineid,description,imageurl,newstime,rating)
select IDTableArticle, title,contenturl,categoryid,magazineid,description,imageurl,newstime,_like * 0.2 + _cmt*0.6 +_share*0.9 as ranking
from module_ranking
order by ranking DESC;
end//

create procedure clean_ranking()
begin
delete from module_ranking;
delete from news;
alter table news auto_increment = 1;
end//
delimiter ;

