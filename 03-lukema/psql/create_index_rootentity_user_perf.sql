CREATE INDEX
    rootentity_dtype_user_perf
ON
    "public"."rootentity"
    (
        "id" ASC
    )
WHERE
    dtype = 'user';