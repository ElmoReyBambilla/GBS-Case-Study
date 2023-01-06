//Disable future dates
$(function() {
	var dtToday = new Date();

	var month = dtToday.getMonth() + 1;
	var day = dtToday.getDate();
	var year = dtToday.getFullYear();
	if (month < 10)
		month = '0' + month.toString();
	if (day < 10)
		day = '0' + day.toString();

	var maxDate = year + '-' + month + '-' + day;

	$('#date').attr('max', maxDate);
});

//Format inputs for Name		
function format(id, value) {
	var str = value.split(" ");

	for (var i = 0, x = str.length; i < x; i++) {
		str[i] = str[i][0].toUpperCase() + str[i].substr(1).toLowerCase();
	}

	document.getElementById(id).value = str.join(" ");
}

//Remove alert after 2 seconds		
setTimeout(function() {
	$('.alert').remove();
}, 2000);

//Validate form before submiting
(function() {
	'use strict'

	var forms = document.querySelectorAll('.needs-validation')

	Array.prototype.slice.call(forms).forEach(function(form) {
		form.addEventListener('submit', function(event) {
			if (!form.checkValidity()) {
				event.preventDefault()
				event.stopPropagation()
			}

			form.classList.add('was-validated')
		}, false)
	})
})()

$(document).ready(function() {
	
	//Set the amount and decrption to required and minimum value when editing compensation
	var compensationType = $('#compensationType').val();
	if(compensationType != 'Salary'){
		$("#amount").attr('min', null);
		document.getElementById("amounterror").innerHTML = "Amount should be greater than zero(0) on the compensation type chosen!";
		document.getElementById("compDescription").required = true;
		document.getElementById("setRequired").style.display = "inline-block";
	}

	//Set Compensation Description Required depending on Value
	$('#compensationType').on('change', function() {
		var type = $(this).val();
		if (type == "Salary") {
			document.getElementById("amounterror").innerHTML = "Please enter amount value!";
			document.getElementById("compDescription").required = false;
			document.getElementById("compDescription").value = "";
			document.getElementById("setRequired").style.display = "none";
			$("#amount").attr('min',null);
		} else {
			$("#amount").attr('min', 1);
			document.getElementById("amounterror").innerHTML = "Amount should be greater than zero(0) on the compensation type chosen!";
			document.getElementById("compDescription").required = true;
			document.getElementById("setRequired").style.display = "inline-block";
		}
	});
	
	/*$('#employeeList').on('change', function() {
		var id = $(this).val();

		console.log(id);

		$("#employeeId").val(id);
	});*/
});

//Activate tooltip
var tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'))
var tooltipList = tooltipTriggerList.map(function(tooltipTriggerEl) {
	return new bootstrap.Tooltip(tooltipTriggerEl)
})

function enableEndDate(startDate){
	if(startDate.value){
		
		var minDate = startDate.value;
		var splitDate = minDate.split("-");
		var newMonth = parseInt(splitDate[1]) + 1;
		var finalDate = splitDate[0].toString() + '-0' + newMonth.toString();
		
		$("#endDate").removeAttr("readonly");
		$("#endDate").attr("min", finalDate);
	}else{
		$("#endDate").attr("readonly", "readonly");
	}
}