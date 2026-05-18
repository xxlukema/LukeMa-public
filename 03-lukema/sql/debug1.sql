select to_char(csd.create_date, 'dd-Mon-yyyy hh24:mi:ss') 
from COMMAND_STATUS_DETAILS csd, panel_connection_params pcp, panels p, sites s 
where s.id = csd.site_id 
  and p.panel_id = csd.panel_id 
  and p.panel_connection_param_id = pcp.panel_connection_param_id 
  and s.group_id = 100000008 
  and csd.create_date >= to_date('8/1/2007 00:00:00', 'MM/dd/yyyy hh24:mi:ss') 
  and csd.create_date <= to_date('8/3/2007 23:59:59', 'MM/dd/yyyy hh24:mi:ss') 
  and pcp.communication_type in ('network','modem')
ORDER BY csd.command_set_status_id, csd.site_id, csd.panel_id

/

