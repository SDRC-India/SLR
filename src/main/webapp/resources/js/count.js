$(document).ready(function() {
	openFooter();
});
function openFooter() {
	$.ajax({
		url : "getCounterJson",
		type : "GET",
		contentType : 'application/json',
		success : function(mailmsg) {
			$("#counter").text(mailmsg);
		}
	});
}
