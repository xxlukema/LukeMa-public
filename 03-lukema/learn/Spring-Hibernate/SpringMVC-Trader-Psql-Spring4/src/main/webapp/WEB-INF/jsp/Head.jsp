<!DOCTYPE html>

<head>

<meta charset="UTF-8">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta content="no-cache" http-equiv="Cache-Control" />
<meta content="no-cache" http-equiv="Pragma" />
<meta http-equiv="EXPIRES" content="0" />

<title>Spring MVC</title>
<link rel="stylesheet" type="text/css" href="css/learn.css" />

<style type="text/css">
div {
	margin-bottom: 20px;
	padding: 2px;
	display: block;
}
</style>

<script
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
<script src="http://malsup.github.io/jquery.blockUI.js"></script>

<script type="text/javascript">
	//<![CDATA[
	// unblock when ajax activity stops 
	$(document).ajaxStop($.unblockUI);

	function test() {
	//	$.ajax({
	//		url : 'wait.php',
	//		cache : false
	//	});
	}



	$(document).ready(function() {
		$('#submitButtonDisableNotWorking').click(function() {
			$('#inputFormCannotFound').block();
			test();
		});
		$('#submitButtonBlockUI').click(function() {
			$.blockUI({
				message : '<h1><img src="image/ajax-loader.gif" /> Just a moment...</h1>'
			});
			test();
		});
		$('#pageDemo3').click(function() {
			$.blockUI({
				css : {
					backgroundColor : '#f00',
					color : '#fff'
				}
			});
			test();
		});

		$('#pageDemo4').click(function() {
			$.blockUI({
				message : $('#domMessage')
			});
			test();
		});
	});
	//]]>
</script>

</head>