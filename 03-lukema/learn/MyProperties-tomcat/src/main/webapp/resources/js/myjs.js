
"use strict";


$(document).ready(function () {
	
	
		
	function getHouseData(){
		
		/**
		 * 1. Get dateUpdated
		 */
		
		var dateUpdated = "";
		
		var url = $("#contextPath").text() + "/rest/house/getDateUpdated";
		
		$.ajax({
			url: url,
			type: "GET",
			dataType: "text",
			success: function( data ) {
				if(data != null) {
					dateUpdated = data;
				}
			},
			error: function(xhr, statusText, err) {
            	alert("Error: statusText = " + statusText);
            	console.log("error: " + xhr.status);
            }
		});
		
		/**
		 * 2. Get propertyList
		 */
		
		var url = $("#contextPath").text() + "/rest/house/getPropertyList";
		
		$.ajax({
			url: url,
			type: "GET",
			dataType: "json",
			success: function( data ) {
				if(data != null) {
					
					$.each(data, function(index, value) {
					
						var html = '';
						
						html += '<header><span class="title1">Property Report. Date Updated: ' + dateUpdated + '</span><span class="title2">Item (' + value["id"] + ')<span></header>';
						html += '<div class="spacer"></div>';
						html += '<table border="0" align="center">';
						html += '  <tr><td><label class="lableAlignRight bold">Description</label></td><td colspan="2"><span class="spanValue bold">' + value["description"] + '</span></td></tr>';
						html += '  <tr><td><label class="lableAlignRight">Tax Id</label></td><td colspan="2"><span class="spanValue">' + value["taxId"] + '</span></td></tr>';
						html += '  <tr><td><label class="lableAlignRight">Address</label></td><td colspan="2"><span class="spanValue">' + value["address"] + '</span></td></tr>';
						html += '  <tr><td></td><td colspan="2"><span class="spanValue">' + value["city"] + ', ' + value["state"] + ' ' + value["zip"] + '</span></td></tr>';
						html += '  <tr><td><label class="lableAlignRight">Purchase Date</label></td><td colspan="2"><span class="spanValue">' + value["purchaseDate"] + '</span></td></tr>';
						html += '  <tr><td><label class="lableAlignRight">Purchase Price</label></td><td style="width: 100px;"><span class="spanValue currency number">' + value["purchasePrice"] + '</span></td><td></td></tr>';
						html += '  <tr><td><label class="lableAlignRight">Current Price</label></td><td><span class="spanValue currency number">' + value["currentPrice"] + '</span></td><td></td></tr>';
						html += '  <tr><td><label class="lableAlignRight">Mortgage Bank</label></td><td colspan="2"><span class="spanValue">' + value["mortgageBank"] + '</span></td></tr>';
						html += '  <tr><td><label class="lableAlignRight">Principla Balance</label></td><td><span class="spanValue currency number">' + value["principlaBalance"] + '</span></td><td></td></tr>';
						html += '  <tr><td><label class="lableAlignRight">Interest Rate</label></td><td colspan="2"><span class="spanValue percent">' + value["interestRate"] + '</span></td></tr>';
						html += '  <tr><td>&nbsp;</td><td>&nbsp;</td><td></td></tr>';
						html += '  <tr><td><label class="lableAlignRight bold">Monthly Rent Income</label></td><td><span class="spanValue currency number bold">' + value["monthlyRentIncome"] + '</span></td><td></td></tr>';
						html += '  <tr><td>&nbsp;</td><td>&nbsp;</td><td></td></tr>';
						html += '  <tr><td><label class="lableAlignRight payment">Monthly Principal Payment</label></td><td><span class="spanValue currency number payment">' + value["monthlyPrincipalPayment"] + '</span></td><td></td></tr>';
						html += '  <tr><td><label class="lableAlignRight payment">Monthly Interest Payment</label></td><td><span class="spanValue currency number payment">' + value["monthlyInterestPayment"] + '</span></td><td></td></tr>';
						html += '  <tr><td><label class="lableAlignRight payment bold">Monthly Mortgage Payment</label></td><td><span class="spanValue currency number payment bold">' + value["monthlyMortgagePayment"] + '</span></td><td></td></tr>';
						html += '  <tr><td>&nbsp;</td><td>&nbsp;</td><td></td></tr>';
						html += '  <tr><td><label class="lableAlignRight payment">Monthly Property Tax</label></td><td><span class="spanValue currency number payment">' + value["monthlyPropertyTax"] + '</span></td><td>(Covered by Escrow)</td></tr>';
						html += '  <tr><td><label class="lableAlignRight payment">Monthly Insurance Payment</label></td><td><span class="spanValue currency number payment">' + value["monthlyInsurancePayment"] + '</span></td><td>(Covered by Escrow)</td></tr>';
						html += '  <tr><td><label class="lableAlignRight payment bold">Monthly Escrow</label></td><td><span class="spanValue currency number payment bold">' + value["monthlyEscrow"] + '</span></td><td></td></tr>';
						html += '  <tr><td>&nbsp;</td><td>&nbsp;</td><td></td></tr>';
						
						html += '  <tr><td><label class="lableAlignRight payment bold">Monthly HOA Payment</label></td><td><span class="spanValue currency number payment bold">' + value["monthlyHOAPayment"] + '</span></td><td></td></tr>';
						html += '  <tr><td>&nbsp;</td><td>&nbsp;</td><td></td></tr>';
						
						var user = urlParam('user');
						if(user == null || user != 'Luke') {
							 html += '  <tr><td><label class="lableAlignRight payment bold">Monthly Total Payment</label></td><td><span class="spanValue currency number payment bold">' + value["monthlyPaymentsForLoanNoLidHudManagement"] + '</span></td><td></td></tr>';
							 html += '  <tr><td>&nbsp;</td><td>&nbsp;</td><td></td></tr>';
							 html += '  <tr><td><label class="lableAlignRight grey bold">Monthly Profit Excluding Principal</label></td><td><span class="spanValue currency number grey bold">' + value["monthlyNetProfitForLoanNoPrincipal"] + '</span></td><td></td></tr>';
							 html += '  <tr><td><label class="lableAlignRight grey bold">Monthly Profit Including Principal</label></td><td><span class="spanValue currency number grey bold">' + value["monthlyNetProfitForLoanWithPrincipal"] + '</span></td><td></td></tr>';
							 html += '  <tr><td>&nbsp;</td><td>&nbsp;</td><td></td></tr>';
						} else {
							if(value["monthlyLIDTax"] != 0) {
							   html += '  <tr><td><label class="lableAlignRight payment grey">Luke\' Monthly LID Tax</label></td><td><span class="spanValue currency number payment grey">' + value["monthlyLIDTax"] + '</span></td><td></td></tr>';
							}
							if(value["monthlyMUDTax"] != 0) {
							   html += '  <tr><td><label class="lableAlignRight payment grey">Luke\' Monthly MUD Tax</label></td><td><span class="spanValue currency number payment grey">' + value["monthlyMUDTax"] + '</span></td><td></td></tr>';
							}
							if(value["monthlyManagementFee"] != 0) {
							   html += '  <tr><td><label class="lableAlignRight payment grey">Luke\' Monthly Management Fee</label></td><td><span class="spanValue currency number payment grey">' + value["monthlyManagementFee"] + '</span></td><td></td></tr>';
							}
							
							html += '  <tr><td><label class="lableAlignRight payment grey bold">Luke\' Monthly Total Payment</label></td><td><span class="spanValue currency number payment grey bold">' + value["monthlyPaymentsForLukeWithLidHudManagement"] + '</span></td><td></td></tr>';
							html += '  <tr><td>&nbsp;</td><td>&nbsp;</td><td></td></tr>';
						    html += '  <tr><td><label class="lableAlignRight grey bold">Luke\' Monthly Profit Excluding Principal</label></td><td><span class="spanValue currency number grey bold">' + value["monthlyNetProfitForLukeNoPrincipal"] + '</span></td><td></td></tr>';
							html += '  <tr><td><label class="lableAlignRight grey bold">Luke\' Monthly Profit Including Principal</label></td><td><span class="spanValue currency number grey bold">' + value["monthlyNetProfitForLukeWithPrincipal"] + '</span></td><td></td></tr>';
						}
					
					    html += '</table>';
					    html += '<footer style="page-break-after: always;"></footer>';

						$("#container").append(html);
					});
					
					$(document).trigger("my-event-afterTableReady");
				}
			},
			error: function(xhr, statusText, err) {
            	alert("Error: statusText = " + statusText);
            	console.log("error: " + xhr.status);
            }
		});
		
		return false;
	}
	
	getHouseData();
	
});


$(document).ready(function () {
	
	$(document).on("my-event-afterTableReady", function () {
		$('span.number').digits();
		
		$('td span.currency').parent().css('text-align', 'right');
	});
});


