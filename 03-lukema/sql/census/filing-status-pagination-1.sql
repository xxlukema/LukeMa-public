SELECT
   count(SURVEY_INSTANCE_ID) as count, SURVEY_INSTANCE_ID
FROM
    SURVEY_FORM
group by SURVEY_INSTANCE_ID
having count(SURVEY_INSTANCE_ID) > 3
order by count desc
