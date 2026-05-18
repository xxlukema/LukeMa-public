SELECT DISTINCT
    'CREATE INDEX rootentity_dtype_' || dtype || '_perf ON rootentity (id asc) where dtype = ''' || dtype || ''';'
FROM
    rootentity;