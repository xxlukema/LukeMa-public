SELECT
  alarm.PANEL_DEVICE_ALARM_ID  -- {info.alarmId}
  ,site.id                     -- {info.siteId}
  ,compGrpRel.owner_id         -- {info.groupId}
  ,site.name                   -- {info.siteName}
  ,event.name                  -- {info.eventName}
  ,alarm.ALARM_DATE            -- {info.alarmDate}
  ,templates.priority          -- {info.notificationTemplatePriority}
  ,device.DEVICE_TYPE_CODE     -- {info.deviceTypeCode}
  ,device.PANEL_DEVICE_NAME    -- {info.deviceName}
  ,device.PANEL_DEVICE_DESC    -- {info.deviceDesc}
  ,alarm.status                -- {info.alarmStatus}
  ,( SELECT value
       FROM metadata
      WHERE id = site.id
        AND name = 'sites_time_zone')  -- {info.siteTimezoneCode}
FROM PANEL_DEVICE_ALARMS alarm
    ,SITES               site
    ,EVENTS              event
    ,TEMPLATES           templates
    ,PANEL_DEVICES       device
    ,Relationships       compGrpRel
WHERE alarm.status <> 4
  AND device.PANEL_DEVICE_ID (+) = alarm.PANEL_DEVICE_ID
  AND site.ID = alarm.SITE_ID
  AND event.ID = alarm.event_id
  AND templates.ID = alarm.TEMPLATE_ID
  and compGrpRel.OBJECT_ID = site.ID
  and compGrpRel.RELATIONSHIP_TYPE_ID =
      (select ID from relationship_types where NAME= 'siteComplianceGroupRelationship')
  and compGrpRel.owner_id = 116871676 and templates.priority = 'P1'
/
