//-----------------------------------------------
//FUNCTION TO ADD NEW EVENT DETAILS
//-----------------------------------------------
function publishEvent() {
	// Remove All Events From Calendar Object
	$('#eventCalendar').fullCalendar('removeEvents');

	jQuery.ajax({
		url : 'EventService?serviceRequest=GetAll',
		type : 'POST',
		async : false
	}).done(function(response) {
		var json = JSON.parse(response);
		if (json.length == 0) {
			alert("No events data found");
		}
		$('#eventCalendar').show();
		initialzieCalendar();
		for (var i = 0; i < json.length; i++) {
			var data = json[i];
			var autoid = data.id;
			var uniqueID = data.uniqueID;
			var eventcatid = data.eventcatid;

			var title = data.title;
			var description = data.description;
			var startDate = data.startDate; //<MM DD YYYY
			console.log("Receivd Date " + startDate);
			var endDate = data.endDate;
			var time = data.time;
			var year = data.year;
			var location = data.location;
			var setNotif = data.setNotif;
			var isRemiderOn = data.setReminder;

			var randomColor = getRandomColor();
//			var dateValue = startDate.split("/")[0];
//			var monthValue = startDate.split("/")[1];
//			var yearValue = startDate.split("/")[2];
			var event = {
				id : uniqueID,
				title : title,
				start :  new Date(startDate),
				description : description,
				isRemiderOn : isRemiderOn,
				backgroundColor : randomColor,
				borderColor : randomColor, 
				allDay:allDay,
				editable:true,
				autoid : autoid,
				eventcatid : eventcatid,
				endDate : endDate,
				time : time,
				location : location,
				year : year,
				setNotif : setNotif,
			};

			
			$('#eventCalendar').fullCalendar('renderEvent', event, true);
		}

	});

	
}

// -----------------------------------------------
// FUNCTION TO MANAGE EXISTING EVENT DETAILS
// -----------------------------------------------
function manageEvents() {
	var manageEventTable = document.getElementById('manageEventTable');
	manageEventTable.innerHTML = '';
	jQuery.ajax({
		url : 'EventServices?serviceRequest=GetAll',
		type : 'POST',
		async : false
	}).done(function(response) {
		var json = JSON.parse(response);
		var dataJSON = JSON.parse(json.data);
		var srcounter = 1;
		for (var i = 0; i < dataJSON.length; i++) {
			var data = dataJSON[i];
			var autoID = data.id;
			var eventName = data.name;
			var eventDescription = data.description;
			srcounter++;
		}

	});
	$('#eventPanel').toggle('slow');
}