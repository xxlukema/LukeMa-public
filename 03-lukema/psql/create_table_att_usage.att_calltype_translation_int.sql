CREATE TABLE
    att_usage.att_calltype_translation
    (
        bass_call_type CHARACTER VARYING NOT NULL,
        att_sku CHARACTER VARYING(16),
        CONSTRAINT pkey PRIMARY KEY (bass_call_type)
    );
    
insert into att_usage.att_calltype_translation (bass_call_type, att_sku) values ('directory_asst_local', 'RXDIRASSIST');
insert into att_usage.att_calltype_translation (bass_call_type, att_sku) values ('international', 'RXINTLC');
insert into att_usage.att_calltype_translation (bass_call_type, att_sku) values ('operator_asst_international', 'RXOPSVCS');
insert into att_usage.att_calltype_translation (bass_call_type, att_sku) values ('operator_asst_ld', 'RXOPSVCS');
insert into att_usage.att_calltype_translation (bass_call_type, att_sku) values ('operator_asst_local', 'RXOPSVCS');
    
