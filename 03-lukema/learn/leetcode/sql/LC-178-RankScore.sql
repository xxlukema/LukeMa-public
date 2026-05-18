select score,
(select count(1)
 from scores s2
 where s2.score > s1.score
) as 'Rank'
from scores s1
order by s1.score desc;


selecr score, dense_rank() (over order by score desc) as 'rank'
from scores;




