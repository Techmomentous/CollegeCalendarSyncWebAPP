var allDay = true;
function initialzieCalendar() {

	/*
	 * initialize the external events
	 * -----------------------------------------------------------------
	 */
	function ini_events(ele) {

		ele.each(function() {

			// create an Event Object
			// (http://arshaw.com/fullcalendar/docs/event_data/Event_Object/)
			// it doesn't need to have a start or end
			var eventObject = {
				title : $.trim($(this).text())
			// use the element's text as the event title
			};

			// store the Event Object in the DOM element so we can get to it
			// later
			$(this).data('eventObject', eventObject);

			// make the event draggable using jQuery UI
			$(this).draggable({
			    zIndex : 1070,
			    revert : true, // will cause the event to go back to its
			    revertDuration : 0
			// original position after the drag
			});

		});
	}
	ini_events($('#external-events div.external-event'));

	/*
	 * initialize the calendar
	 * -----------------------------------------------------------------
	 */
	// Date for the calendar events (dummy data)
	var date = new Date();
	var d = date.getDate(), m = date.getMonth(), y = date.getFullYear();
	$('#eventCalendar')
	        .fullCalendar(
	                {
	                    header : {
	                        left : 'prev,next today',
	                        center : 'title',
	                        right : 'month'
	                    },
	                    buttonText : {
	                        today : 'today',
	                        month : 'month',
	                        week : 'week',
	                        day : 'day'
	                    },

	                    editable : false,
	                    droppable : false, // this allows things to be dropped
	                    // onto the calendar !!!
	                    drop : function(date, allDay) { // this function is

		                    // called when something
		                    // is dropped

		                    // retrieve the dropped element's stored Event
		                    // Object
		                    var originalEventObject = $(this).data(
		                            'eventObject');

		                    // we need to copy it, so that multiple events don't
		                    // have a reference to the same object
		                    var copiedEventObject = $.extend({},
		                            originalEventObject);

		                    // assign it the date that was reported
		                    copiedEventObject.start = date;
		                    copiedEventObject.allDay = allDay;
		                    copiedEventObject.backgroundColor = $(this).css(
		                            "background-color");
		                    copiedEventObject.borderColor = $(this).css(
		                            "border-color");

		                    // render the event on the calendar
		                    // the last `true` argument determines if the event
		                    // "sticks"
		                    // (http://arshaw.com/fullcalendar/docs/event_rendering/renderEvent/)
		                    $('#eventCalendar').fullCalendar('renderEvent',
		                            copiedEventObject, true);

		                    // is the "remove after drop" checkbox checked?
		                    if ($('#drop-remove').is(':checked')) {
			                    // if so, remove the element from the "Draggable
			                    // Events" list
			                    $(this).remove();
		                    }

	                    },
	                    dayClick : function() {

		                    showEventPopup(this);
	                    },
	                    eventClick : function(event, element) {

		                    // Set Model popup values
		                    var selectedDate = event.start.format('MM/DD/YYYY');
		                    var selectedTime = event.time;
		                    var selectedYear = event.year;
		                    var eventCategoryId = event.eventcatid;
		                    var autoID = event.autoid;

		                    // Set popup form input value
		                    document.getElementById('updateEventName').value = event.title;
		                    document.getElementById('updateEventDesc').value = event.description;
		                    document.getElementById('updateDatePicker').value = selectedDate;
		                    document.getElementById('updateTimePicker').value = selectedTime;
//		                    document.getElementById('updateYear').value = selectedYear
//		                            + "Year";
		                    if (selectedYear == '1 Year') {
								document.getElementById("updateYear").selectedIndex = 0;
							} else if (selectedYear == '2 Year') {
								document.getElementById("updateYear").selectedIndex = 1;
							} else {
								document.getElementById("updateYear").selectedIndex = 2;
							}
		                    document.getElementById('updateReminder').checked = event.isRemiderOn;

		                    // Set event category dropdown
		                    if (eventCategoryId == 1) {

			                    document.getElementById('updateEvent').value = 'Fees';
		                    }
		                    else if (eventCategoryId == 2) {
			                    document.getElementById('updateEvent').value = 'Campus';
		                    }
		                    else {
			                    document.getElementById('updateEvent').value = 'Sports';
		                    }

		                    $('#updateDatePicker').datepicker({
		                        format : 'mm/dd/yyyy',
		                        startDate : new Date(selectedDate),
		                    });

		                    // Timepicker
		                    $("#updateTimePicker").timepicker({
			                    showInputs : false
		                    });

		                    // Set div attributes
		                    document.getElementById('updateDate').setAttribute(
		                            'uniqueID', event.id);

		                    document.getElementById('updateDate').setAttribute(
		                            'color', event.backgroundColor);

		                    // set other attributes
		                    document.getElementById('updateDate').setAttribute(
		                            'autoID', autoID);
		                    document.getElementById('updateDate').setAttribute(
		                            'eventcatid', eventCategoryId);

		                    document.getElementById('updateDate').setAttribute(
		                            'location', event.location);
		                    document.getElementById('updateDate').setAttribute(
		                            'year', year);
		                    document.getElementById('updateDate').setAttribute(
		                            'setNotif', event.setNotif);
		                    var uid = event.id;
		                    // show update modal
		                    $('#manageEventModal').modal('show');
	                    }

	                });

}

function showEventPopup(calObject) {
	document.getElementById('inputEventName').value='';
	var clickDate = calObject[0].getAttribute('data-date');
	// Initialize The Date range picker without time picker
	$('#selectedDataTime').daterangepicker({
	    timePicker : false,
	    format : 'MM/DD/YYYY h:mm A',
	    startDate : new Date(clickDate)
	});
	// Timepicker
	$(".timepicker").timepicker({
		showInputs : false
	});

	$('#selectedDataTime').on(
	        'apply.daterangepicker',
	        function(ev, picker) {

		        var startRange = picker.startDate.format('MM/DD/YYYY');
		        var endRange = picker.endDate.format('MM/DD/YYYY');
		        $(this).val(startRange + ' - ' + endRange);
		        document.getElementById('selectedDate').setAttribute(
		                'startRange', startRange);
		        document.getElementById('selectedDate').setAttribute(
		                'endRange', endRange);
	        });
	// Initialize TheFlat red color scheme for iCheck
	$('input[type="checkbox"].flat-red, input[type="radio"].flat-red').iCheck({
	    checkboxClass : 'icheckbox_flat-green',
	    radioClass : 'iradio_flat-green'
	});

	$('#eventModal').modal('show');
}

function addEventToCalendar() {

	// Get The data from selected area

	var title = document.getElementById('inputEventName').value;
	var enterDate = document.getElementById('selectedDataTime').value;
	var description = document.getElementById('inputEventDesc').value;

	var isRemiderOn = document.getElementById('reminder').checked;

	// Get start date and time
	var startRange = document.getElementById('selectedDate').getAttribute(
	        'startRange');
	var endRange = document.getElementById('selectedDate').getAttribute(
	        'endRange');
	var time = document.getElementById('selectedTime').value;
	var eventype = document.getElementById('eventype').value;

	// This is for 1 Year ,2 Year not for date year
	var selectdYear = document.getElementById('year').value;
	var selectedBranch = document.getElementById('branch').value;
debugger
	var start = new Date(startRange);
	var end = new Date(endRange);
	var year = start.getFullYear();
	var month = start.getMonth() + 1; // <--Month Start with 0
	var dates = [];
	var startVal = parseInt(start.getDate());
//	var file=document.getElementById('attachment').value;
	// Set the color according to event type
	// For Sport Green
	// For Campus Orange
	// For Fees Red
	var Green = '#008000';
	var Orange = '#FFA500';
	var Red = '#ff2500';
	var randomColor = '#005aff';
	if (eventype == 'Sports') {
		randomColor = Green;
	}
	else if (eventype == 'Campus') {
		randomColor = Orange;
	}
	else {
		randomColor = Red;
	}

	var array = new Array();
	while (startVal <= parseInt(end.getDate())) {

		// MM DD YYYY
		var date = month + "/" + startVal + "/" + year;

		var id = getUniqueID();

		// Create a json object
		var jsonObject = {};
		jsonObject.uniqueID = id;
		jsonObject.title = title;
		jsonObject.description = description;
		jsonObject.time = time;
		jsonObject.date = date;
		jsonObject.setReminder = isRemiderOn;
		jsonObject.setNotif = true;
		jsonObject.branch = selectedBranch;
		jsonObject.year = selectdYear;
		jsonObject.eventType = eventype;

		var event = {
		    id : id,
		    title : title,
		    start : new Date(date),
		    description : description,
		    isRemiderOn : isRemiderOn,
		    backgroundColor : randomColor,
		    borderColor : randomColor,
		    allDay : allDay
		};

		$('#eventCalendar').fullCalendar('renderEvent', event, true);
		dates.push(date);
		array.push(jsonObject);
		startVal++;
		console.log("Event Unique ID " + id + " Event Title : " + title);
	}

	// All this events go into db
	jQuery.ajax(
	        {
	            url : 'EventService?serviceRequest=AddEvents&eventJson='
	                    + JSON.stringify(array),
	            type : 'POST',
	            async : false,
	            cache: false,
	            contentType: false,
	            enctype: 'multipart/form-data',
	        }).done(function(response) {

		var json = JSON.parse(response);
		alert(json.message);
		$('#eventModal').modal('hide');
	});

}

function updateEventToCalendar() {

	// get attributes

	var title = document.getElementById('updateEventName').value;
	var description = document.getElementById('updateEventDesc').value;
	var updateBranch = document.getElementById('updateBranch').value;
	var updateEvent = document.getElementById('updateEvent').value;
	var updateYear = document.getElementById('updateYear').value;

	var uniqueID = document.getElementById('updateDate').getAttribute(
	        'uniqueID');
	var autoID = document.getElementById('updateDate').getAttribute('autoID');

	var color = document.getElementById('updateDate').getAttribute('color');
	var isRemiderOn = document.getElementById('updateReminder').checked;

	// MM DD YYY
	var date = document.getElementById('updateDatePicker').value;
	var eventcatid = document.getElementById('updateDate').getAttribute(
	        'eventcatid');

	if (updateEvent == 'Fees') {
		eventcatid = 1;
	}
	else if (updateEvent == 'Campus') {
		eventcatid = 2;
	}
	else {
		eventcatid = 3;
	}

	var location = document.getElementById('updateDate').getAttribute(
	        'location');
	var time = document.getElementById('updateTimePicker').value;
	var setNotif = document.getElementById('updateDate').getAttribute(
	        'setNotif');

	var eventObject = {
	    id : uniqueID,
	    // Set the updated properties of calendar and db properites
	    title : title,
	    start : new Date(date),
	    description : description,
	    backgroundColor : color,
	    borderColor : color,
	    allDay : allDay,

	    // Set other properties
	    isRemiderOn : isRemiderOn,
	    autoid : autoID,
	    eventcatid : eventcatid,
	    endDate : date,
	    startDate : date,
	    time : time,
	    location : location,
	    branch : updateBranch,
	    year : updateYear,
	    setNotif : setNotif
	};

	var jsonObject = {};
	jsonObject.id = autoID;
	jsonObject.uniqueID = uniqueID;
	jsonObject.title = title;
	jsonObject.description = description;
	jsonObject.time = time;
	jsonObject.date = date;
	jsonObject.setReminder = isRemiderOn;
	jsonObject.setNotif = true;
	jsonObject.branch = updateBranch;
	jsonObject.year = updateYear;
	jsonObject.eventcatid = eventcatid;
	// All this events go into db
	jQuery.ajax(
	        {
	            url : 'EventService?serviceRequest=updateEvent&eventJson='
	                    + JSON.stringify(jsonObject),
	            type : 'POST',
	            async : false
	        }).done(function(response) {

		var json = JSON.parse(response);
		alert(json.message);
		if (json.result == true) {
			// Remove the existing event
			$('#eventCalendar').fullCalendar('removeEvents', uniqueID);
			// Update the event
			$('#eventCalendar').fullCalendar('renderEvent', eventObject);
		}
		$('#manageEventModal').modal('hide');
	});
	// hide the dialog box
	$('#manageEventModal').modal('hide');
}

function removeEventToCalendar() {

	var uniqueID = document.getElementById('updateDate').getAttribute(
	        'uniqueID');

	jQuery.ajax({
	    url : 'EventService?serviceRequest=removeEvent&uniqueID=' + uniqueID,
	    type : 'POST',
	    async : false
	}).done(function(response) {

		var json = JSON.parse(response);
		alert(json.message);
		if (json.result == true) {
			// Remove the existing event
			$('#eventCalendar').fullCalendar('removeEvents', uniqueID);

		}
		$('#manageEventModal').modal('hide');
	});
}
