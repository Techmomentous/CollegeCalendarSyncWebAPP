var UserID = document.getElementById('userid').innerHTML;
var allDay = true;
var CALENDAR_DIV = '#user_calendar';
function publishTimeTable() {
	$('#assignmentCalendar').hide();
	$('#examCalendar').hide();
	// Remove All Events From Calendar Object
	$(CALENDAR_DIV).fullCalendar('removeEvents');

	jQuery.ajax({
		url : 'TimeTableService?serviceRequest=GetTimeTable&userid=' + UserID,
		type : 'POST',
		async : false
	}).done(function(response) {
		var json = JSON.parse(response);
		if (json.length == 0) {
			alert("No events data found");
		}
		$('#userCalendar').show();
		initialzieCalendar();
		for (var i = 0; i < json.length; i++) {
			var data = json[i];
			var autoid = data.id;
			var uniqueID = data.uniqueID;
			var userid = data.userid;
			var title = data.subject;
			var startDate = data.startDate; // <MM DD YYYY

			var endDate = data.endDate;
			var time = data.time;
			var year = data.year;
			var setNotif = data.setNotif;
			var isRemiderOn = data.setReminder;
			var randomColor = getRandomColor();

			var event = {
				id : uniqueID,
				title : title,
				start : new Date(startDate),
				isRemiderOn : isRemiderOn,
				backgroundColor : randomColor,
				borderColor : randomColor,
				allDay : allDay,
				editable : true,
				autoid : autoid,
				userid : userid,
				endDate : endDate,
				time : time,
				year : year,
				setNotif : setNotif,
			};

			$(CALENDAR_DIV).fullCalendar('renderEvent', event, true);
		}

	});

}

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
	$(CALENDAR_DIV)
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
							$(CALENDAR_DIV).fullCalendar('renderEvent',
									copiedEventObject, true);

							// is the "remove after drop" checkbox checked?
							if ($('#drop-remove').is(':checked')) {
								// if so, remove the element from the "Draggable
								// Events" list
								$(this).remove();
							}

						},
						dayClick : function() {
							showTimeTablePopup(this);
						},
						eventClick : function(event, element) {
							// Set Model popup values
							var selectedDate = event.start.format('MM/DD/YYYY');
							var selectedTime = event.time;
							var selectedYear = event.year;
							var autoID = event.autoid;
							var userID = event.userid;

							// Set popup form input value
							document.getElementById('updateSubjectName').value = event.title;
							document.getElementById('updateTTDatePicker').value = selectedDate;
							document.getElementById('updateTTTimePicker').value = selectedTime;
//							document.getElementById('updateTTYear').value = selectedYear
//									+ "Year";
							if (selectedYear == '1 Year') {
								document.getElementById("updateTTYear").selectedIndex = 0;
							} else if (selectedYear == '2 Year') {
								document.getElementById("updateTTYear").selectedIndex = 1;
							} else {
								document.getElementById("updateTTYear").selectedIndex = 2;
							}
							document.getElementById('updateTTReminder').checked = event.isRemiderOn;

							$('#updateTTDatePicker').datepicker({
								format : 'mm/dd/yyyy',
								startDate : new Date(selectedDate),
							});

							// Timepicker
							$("#updateTTTimePicker").timepicker({
								showInputs : false
							});

							// Set div attributes
							document.getElementById('updateTTDate')
									.setAttribute('uniqueID', event.id);
							document.getElementById('updateTTDate')
									.setAttribute('userID', userID);
							document.getElementById('updateTTDate')
									.setAttribute('color',
											event.backgroundColor);

							// set other attributes
							document.getElementById('updateTTDate')
									.setAttribute('autoID', autoID);

							document.getElementById('updateTTDate')
									.setAttribute('location', event.location);
							document.getElementById('updateTTDate')
									.setAttribute('year', year);
							document.getElementById('updateTTDate')
									.setAttribute('setNotif', event.setNotif);

							// show update modal
							$('#manageTimeTableModal').modal('show');
						}

					});

}

function showTimeTablePopup(calObject) {
	// Reset the fields
	document.getElementById('inputSubjectName').value = '';
	var clickDate = calObject[0].getAttribute('data-date');
	// Initialize The Date range picker without time picker
	$('#ttDatePicker').daterangepicker({
		timePicker : false,
		format : 'MM/DD/YYYY h:mm A',
		startDate : new Date(clickDate)
	});
	// Timepicker
	$("#ttTimePicker").timepicker({
		showInputs : false
	});

	$('#ttDatePicker').on(
			'apply.daterangepicker',
			function(ev, picker) {
				var startRange = picker.startDate.format('MM/DD/YYYY');
				var endRange = picker.endDate.format('MM/DD/YYYY');
				$(this).val(startRange + ' - ' + endRange);
				document.getElementById('selectedTTDate').setAttribute(
						'startRange', startRange);
				document.getElementById('selectedTTDate').setAttribute(
						'endRange', endRange);
			});
	// Initialize TheFlat red color scheme for iCheck
	$('input[type="checkbox"].flat-red, input[type="radio"].flat-red').iCheck({
		checkboxClass : 'icheckbox_flat-green',
		radioClass : 'iradio_flat-green'
	});

	$('#ttModal').modal('show');
}
function addTimeTableToCalendar() {
	// Get The data from selected area

	var title = document.getElementById('inputSubjectName').value;
	var isRemiderOn = document.getElementById('ttReminder').checked;

	// Get start date and time
	var startRange = document.getElementById('selectedTTDate').getAttribute(
			'startRange');
	var endRange = document.getElementById('selectedTTDate').getAttribute(
			'endRange');
	var time = document.getElementById('ttTimePicker').value;

	// This is for 1 Year ,2 Year not for date year
	var selectdYear = document.getElementById('year').value;
	var selectedBranch = document.getElementById('branch').value;

	var start = new Date(startRange);
	var end = new Date(endRange);
	var year = start.getFullYear();
	var month = start.getMonth() + 1; // <--Month Start with 0
	var dates = [];
	var startVal = parseInt(start.getDate());

	var array = new Array();
	while (startVal <= parseInt(end.getDate())) {

		// MM DD YYYY
		var date = month + "/" + startVal + "/" + year;
		var randomColor = getRandomColor();
		var id = getUniqueID();

		// Create a json object
		var jsonObject = {};
		jsonObject.uniqueID = id;
		jsonObject.subject = title;
		jsonObject.time = time;
		jsonObject.date = date;
		jsonObject.setReminder = isRemiderOn;
		jsonObject.setNotif = true;
		jsonObject.branch = selectedBranch;
		jsonObject.year = selectdYear;
		jsonObject.userid = UserID;

		var event = {
			id : id,
			title : title,
			start : new Date(date),
			isRemiderOn : isRemiderOn,
			backgroundColor : randomColor,
			borderColor : randomColor,
			allDay : allDay,
			userid : UserID

		};

		$(CALENDAR_DIV).fullCalendar('renderEvent', event, true);
		dates.push(date);
		array.push(jsonObject);
		startVal++;
		console.log("Time Table Unique ID " + id + " Subject : " + title
				+ " For User ID " + UserID);
	}

	// All this events go into db
	var url='TimeTableService?serviceRequest=AddTimeTable&dataJson='+ JSON.stringify(array);
	document.getElementById("newtimeTableForm").setAttribute('action',url);
	document.getElementById("newtimeTableForm").submit();
	// All this events go into db
//	jQuery.ajax(
//			{
//				url : 'TimeTableService?serviceRequest=AddTimeTable&dataJson='
//						+ JSON.stringify(array),
//				type : 'POST',
//				async : false
//			}).done(function(response) {
//		var json = JSON.parse(response);
//		alert(json.message);
//		$('#ttModal').modal('hide');
//	});

	$('#ttModal').modal('hide');
}

function removeTTToCalendar() {

	var uniqueID = document.getElementById('updateTTDate').getAttribute(
			'uniqueID');
	// All this events go into db
	jQuery
			.ajax(
					{
						url : 'TimeTableService?serviceRequest=RemoveTimeTable&uniqueID='
								+ uniqueID,
						type : 'POST',
						async : false
					}).done(function(response) {
				var json = JSON.parse(response);
				alert(json.message);
				if (json.result == true) {
					// Remove the existing event
					$(CALENDAR_DIV).fullCalendar('removeEvents', uniqueID);

				}
				$('#manageTimeTableModal').modal('hide');
			});
}
function updateTTToCalendar() {

	// Get values from fields
	var title = document.getElementById('updateSubjectName').value;
	var updateBranch = document.getElementById('updateTTBranch').value;
	var updateYear = document.getElementById('updateTTYear').value;
	// MM DD YYY
	var date = document.getElementById('updateTTDatePicker').value;
	var time = document.getElementById('updateTTTimePicker').value;
	var isRemiderOn = document.getElementById('updateTTReminder').checked;

	// Get values from hidden dive
	var uniqueID = document.getElementById('updateTTDate').getAttribute(
			'uniqueID');
	var autoID = document.getElementById('updateTTDate').getAttribute('autoID');
	var userid = document.getElementById('updateTTDate').getAttribute('userID');
	var color = document.getElementById('updateTTDate').getAttribute('color');

	// var location = document.getElementById('updateTTDate').getAttribute(
	// 'location');
	var setNotif = document.getElementById('updateTTDate').getAttribute(
			'setNotif');

	var eventObject = {
		id : uniqueID,
		// Set the updated properties of calendar and db properites
		title : title,
		start : new Date(date),
		backgroundColor : color,
		borderColor : color,
		allDay : allDay,

		// Set other properties
		isRemiderOn : isRemiderOn,
		autoid : autoID,
		userid : userid,
		endDate : date,
		startDate : date,
		time : time,
		branch : updateBranch,
		year : updateYear,
		setNotif : setNotif
	};

	var jsonObject = {};
	jsonObject.id = autoID;
	jsonObject.uniqueID = uniqueID;
	jsonObject.subject = title;
	jsonObject.time = time;
	jsonObject.date = date;
	jsonObject.setReminder = isRemiderOn;
	jsonObject.setNotif = true;
	jsonObject.branch = updateBranch;
	jsonObject.year = updateYear;
	jsonObject.userid = userid;
	// All this events go into db
	jQuery
			.ajax(
					{
						url : 'TimeTableService?serviceRequest=updateTimeTable&dataJson='
								+ JSON.stringify(jsonObject),
						type : 'POST',
						async : false
					}).done(function(response) {
				var json = JSON.parse(response);
				alert(json.message);
				if (json.result == true) {
					// Remove the existing event
					$(CALENDAR_DIV).fullCalendar('removeEvents', uniqueID);
					// Update the event
					$(CALENDAR_DIV).fullCalendar('renderEvent', eventObject);
				}
				$('#manageTimeTableModal').modal('hide');
			});
	// hide the dialog box
	$('#manageTimeTableModal').modal('hide');
}
// -------------------------------------------------------------------------------------------------------------------
// -----------------------------------------------Exam Schedules
// Methods----------------------------------------------
// -------------------------------------------------------------------------------------------------------------------
function publishExam() {
	$('#assignmentCalendar').hide();
	$('#userCalendar').hide();
	// Remove All Events From Calendar Object
	$('#exam_calendar').fullCalendar('removeEvents');

	jQuery.ajax({
		url : 'ExamService?serviceRequest=GetExamSchedules&userid=' + UserID,
		type : 'POST',
		async : false
	}).done(function(response) {
		var json = JSON.parse(response);
		if (json.length == 0) {
			alert("No Exam data found");
		}
		$('#examCalendar').show();
		initialzieExamCalendar();
		for (var i = 0; i < json.length; i++) {
			var data = json[i];
			var autoid = data.id;
			var uniqueID = data.uniqueID;
			var userid = data.userid;

			var title = data.subject;
			var startDate = data.startDate; // <MM DD YYYY
			var endDate = data.endDate;
			var time = data.time;
			var year = data.year;
			var setNotif = data.setNotif;
			var isRemiderOn = data.setReminder;
			var randomColor = getRandomColor();

			var event = {
				id : uniqueID,
				title : title,
				start : new Date(startDate),
				isRemiderOn : isRemiderOn,
				backgroundColor : randomColor,
				borderColor : randomColor,
				allDay : allDay,
				editable : true,
				autoid : autoid,
				userid : userid,
				endDate : endDate,
				time : time,
				year : year,
				setNotif : setNotif,
			};

			$('#exam_calendar').fullCalendar('renderEvent', event, true);
		}

	});
}
function initialzieExamCalendar() {
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
	$('#exam_calendar')
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
							$('#exam_calendar').fullCalendar('renderEvent',
									copiedEventObject, true);

							// is the "remove after drop" checkbox checked?
							if ($('#drop-remove').is(':checked')) {
								// if so, remove the element from the "Draggable
								// Events" list
								$(this).remove();
							}

						},
						dayClick : function() {
							showExamPopup(this);
						},
						eventClick : function(event, element) {
							// Set Model popup values
							var selectedDate = event.start.format('MM/DD/YYYY');
							var selectedTime = event.time;
							var selectedYear = event.year;
							var autoID = event.autoid;
							var userID = event.userid;

							// Set popup form input value
							document.getElementById('updateExamSubject').value = event.title;
							document.getElementById('updateExamDatePicker').value = selectedDate;
							document.getElementById('updateExamTimePicker').value = selectedTime;
//							document.getElementById('updateExamYear').value = selectedYear
//									+ "Year";
							if (selectedYear == '1 Year') {
								document.getElementById("updateExamYear").selectedIndex = 0;
							} else if (selectedYear == '2 Year') {
								document.getElementById("updateExamYear").selectedIndex = 1;
							} else {
								document.getElementById("updateExamYear").selectedIndex = 2;
							}
							document.getElementById('updateExamReminder').checked = event.isRemiderOn;

							$('#updateExamDatePicker').datepicker({
								format : 'mm/dd/yyyy',
								startDate : new Date(selectedDate),
							});

							// Timepicker
							$("#updateExamTimePicker").timepicker({
								showInputs : false
							});

							// Set div attributes
							document.getElementById('updateExamDate')
									.setAttribute('uniqueID', event.id);
							document.getElementById('updateExamDate')
									.setAttribute('userID', userID);
							document.getElementById('updateExamDate')
									.setAttribute('color',
											event.backgroundColor);

							// set other attributes
							document.getElementById('updateExamDate')
									.setAttribute('autoID', autoID);

							document.getElementById('updateExamDate')
									.setAttribute('location', event.location);
							document.getElementById('updateExamDate')
									.setAttribute('year', year);
							document.getElementById('updateExamDate')
									.setAttribute('setNotif', event.setNotif);

							// show update modal
							$('#manageExamModal').modal('show');
						}

					});

}
function showExamPopup(calObject) {
	document.getElementById('inputExamSubject').value='';
	var clickDate = calObject[0].getAttribute('data-date');
	// Initialize The Date range picker without time picker
	$('#examDatePicker').daterangepicker({
		timePicker : false,
		format : 'MM/DD/YYYY h:mm A',
		startDate : new Date(clickDate)
	});
	// Timepicker
	$("#examTimePicker").timepicker({
		showInputs : false
	});

	$('#examDatePicker').on(
			'apply.daterangepicker',
			function(ev, picker) {
				var startRange = picker.startDate.format('MM/DD/YYYY');
				var endRange = picker.endDate.format('MM/DD/YYYY');
				$(this).val(startRange + ' - ' + endRange);
				document.getElementById('selectedExamDate').setAttribute(
						'startRange', startRange);
				document.getElementById('selectedExamDate').setAttribute(
						'endRange', endRange);
			});
	// Initialize TheFlat red color scheme for iCheck
	$('input[type="checkbox"].flat-red, input[type="radio"].flat-red').iCheck({
		checkboxClass : 'icheckbox_flat-green',
		radioClass : 'iradio_flat-green'
	});

	$('#examModal').modal('show');
}
function addExamScheduleToCalendar() {
	// Get The data from selected area

	var title = document.getElementById('inputExamSubject').value;
	var isRemiderOn = document.getElementById('examReminder').checked;

	// Get start date and time
	var startRange = document.getElementById('selectedExamDate').getAttribute(
			'startRange');
	var endRange = document.getElementById('selectedExamDate').getAttribute(
			'endRange');
	var time = document.getElementById('examTimePicker').value;

	// This is for 1 Year ,2 Year not for date year
	var selectdYear = document.getElementById('year').value;
	var selectedBranch = document.getElementById('branch').value;

	var start = new Date(startRange);
	var end = new Date(endRange);
	var year = start.getFullYear();
	var month = start.getMonth() + 1; // <--Month Start with 0
	var dates = [];
	var startVal = parseInt(start.getDate());

	var array = new Array();
	while (startVal <= parseInt(end.getDate())) {

		// MM DD YYYY
		var date = month + "/" + startVal + "/" + year;
		var randomColor = getRandomColor();
		var id = getUniqueID();

		// Create a json object
		var jsonObject = {};
		jsonObject.uniqueID = id;
		jsonObject.subject = title;
		jsonObject.time = time;
		jsonObject.date = date;
		jsonObject.setReminder = isRemiderOn;
		jsonObject.setNotif = true;
		jsonObject.branch = selectedBranch;
		jsonObject.year = selectdYear;
		jsonObject.userid = UserID;

		var event = {
			id : id,
			title : title,
			start : new Date(date),
			isRemiderOn : isRemiderOn,
			backgroundColor : randomColor,
			borderColor : randomColor,
			allDay : allDay,
			userid : UserID

		};

		$('#exam_calendar').fullCalendar('renderEvent', event, true);
		dates.push(date);
		array.push(jsonObject);
		startVal++;
		console.log("Exam Unique ID " + id + " Subject : " + title
				+ " For User ID " + UserID);
	}

	// All this events go into db
	var url='ExamService?serviceRequest=AddExamSchedules&dataJson='+ JSON.stringify(array);
	document.getElementById("newExamForm").setAttribute('action',url);
	document.getElementById("newExamForm").submit();
//	jQuery.ajax(
//			{
//				url : 'ExamService?serviceRequest=AddExamSchedules&dataJson='
//						+ JSON.stringify(array),
//				type : 'POST',
//				async : false
//			}).done(function(response) {
//		var json = JSON.parse(response);
//		alert(json.message);
//	});
	$('#examModal').modal('hide');
}
function removeExam() {

	var uniqueID = document.getElementById('updateExamDate').getAttribute(
			'uniqueID');
	// All this events go into db
	jQuery.ajax(
			{
				url : 'ExamService?serviceRequest=RemoveExamSchedule&uniqueID='
						+ uniqueID,
				type : 'POST',
				async : false
			}).done(function(response) {
		var json = JSON.parse(response);
		alert(json.message);
		if (json.result == true) {
			// Remove the existing event
			$('#exam_calendar').fullCalendar('removeEvents', uniqueID);

		}
		$('#manageExamModal').modal('hide');
	});
}
function updateExam() {
	// Get values from fields
	var title = document.getElementById('updateExamSubject').value;
	var updateBranch = document.getElementById('updateExamBranch').value;
	var updateYear = document.getElementById('updateExamYear').value;
	// MM DD YYY
	var date = document.getElementById('updateExamDatePicker').value;
	var time = document.getElementById('updateExamTimePicker').value;
	var isRemiderOn = document.getElementById('updateExamReminder').checked;

	// Get values from hidden dive
	var uniqueID = document.getElementById('updateExamDate').getAttribute(
			'uniqueID');
	var autoID = document.getElementById('updateExamDate').getAttribute(
			'autoID');
	var userid = document.getElementById('updateExamDate').getAttribute(
			'userID');
	var color = document.getElementById('updateExamDate').getAttribute('color');

	// var location = document.getElementById('updateTTDate').getAttribute(
	// 'location');
	var setNotif = document.getElementById('updateExamDate').getAttribute(
			'setNotif');

	var eventObject = {
		id : uniqueID,
		// Set the updated properties of calendar and db properites
		title : title,
		start : new Date(date),
		backgroundColor : color,
		borderColor : color,
		allDay : allDay,

		// Set other properties
		isRemiderOn : isRemiderOn,
		autoid : autoID,
		userid : userid,
		endDate : date,
		startDate : date,
		time : time,
		branch : updateBranch,
		year : updateYear,
		setNotif : setNotif
	};

	var jsonObject = {};
	jsonObject.id = autoID;
	jsonObject.uniqueID = uniqueID;
	jsonObject.subject = title;
	jsonObject.time = time;
	jsonObject.date = date;
	jsonObject.setReminder = isRemiderOn;
	jsonObject.setNotif = true;
	jsonObject.branch = updateBranch;
	jsonObject.year = updateYear;
	jsonObject.userid = userid;
	// All this events go into db
	jQuery.ajax(
			{
				url : 'ExamService?serviceRequest=UpdateExamSchedule&dataJson='
						+ JSON.stringify(jsonObject),
				type : 'POST',
				async : false
			}).done(function(response) {
		var json = JSON.parse(response);
		alert(json.message);
		if (json.result == true) {
			// Remove the existing event
			$('#exam_calendar').fullCalendar('removeEvents', uniqueID);
			// Update the event
			$('#exam_calendar').fullCalendar('renderEvent', eventObject);
		}
		$('#manageTimeTableModal').modal('hide');
	});
	// hide the dialog box
	$('#manageExamModal').modal('hide');
}
// -------------------------------------------------------------------------------------------------------------------
// -----------------------------------------------Assignments
// Methods----------------------------------------------
// -------------------------------------------------------------------------------------------------------------------
function publishAssignment() {
	$('#examCalendar').hide();
	$('#userCalendar').hide();
	// Remove All Events From Calendar Object
	$('#assignment_calendar').fullCalendar('removeEvents');

	jQuery
			.ajax(
					{
						url : 'AssignmentService?serviceRequest=GetAllAssignment&userid='
								+ UserID,
						type : 'POST',
						async : false
					}).done(
					function(response) {
						var json = JSON.parse(response);
						if (json.length == 0) {
							alert("No Exam data found");
						}
						$('#assignmentCalendar').show();
						initialzieAssignCalendar();
						for (var i = 0; i < json.length; i++) {
							var data = json[i];
							var autoid = data.id;
							var uniqueID = data.uniqueID;
							var userid = data.userid;

							var subject = data.subject;
							var assignment = data.assignment;
							var startDate = data.startDate; // <MM DD YYYY
							var endDate = data.endDate;
							var time = data.time;
							var year = data.year;
							var setNotif = data.setNotif;
							var isRemiderOn = data.setReminder;
							var randomColor = getRandomColor();

							var event = {
								id : uniqueID,
								title : subject,
								start : new Date(startDate),
								isRemiderOn : isRemiderOn,
								backgroundColor : randomColor,
								borderColor : randomColor,
								assignment : assignment,
								allDay : allDay,
								editable : true,
								autoid : autoid,
								userid : userid,
								endDate : endDate,
								time : time,
								year : year,
								setNotif : setNotif,
							};

							$('#assignment_calendar').fullCalendar(
									'renderEvent', event, true);
						}

					});
}
function initialzieAssignCalendar() {
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
	$('#assignment_calendar')
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
							$('#assignment_calendar').fullCalendar(
									'renderEvent', copiedEventObject, true);

							// is the "remove after drop" checkbox checked?
							if ($('#drop-remove').is(':checked')) {
								// if so, remove the element from the "Draggable
								// Events" list
								$(this).remove();
							}

						},
						dayClick : function() {
							showAssignmentPopup(this);
						},
						eventClick : function(event, element) {
							// Set Model popup values
							var selectedDate = event.start.format('MM/DD/YYYY');
							var selectedTime = event.time;
							var selectedYear = event.year;
							var autoID = event.autoid;
							var userID = event.userid;

							// Set popup form input value
							document.getElementById('updateAssignment').value = event.assignment;
							document.getElementById('updateAssignmentSubject').value = event.title;
							document
									.getElementById('updateAssignmentDatePicker').value = selectedDate;
							document
									.getElementById('updateAssignmentTimePicker').value = selectedTime;
//							document.getElementById('updateAssignmentYear').value = selectedYear
//									+ "Year";
							if (selectedYear == '1 Year') {
								document.getElementById("updateAssignmentYear").selectedIndex = 0;
							} else if (selectedYear == '2 Year') {
								document.getElementById("updateAssignmentYear").selectedIndex = 1;
							} else {
								document.getElementById("updateAssignmentYear").selectedIndex = 2;
							}
							document.getElementById('updateAssignmentReminder').checked = event.isRemiderOn;

							$('#updateAssignmentDatePicker').datepicker({
								format : 'mm/dd/yyyy',
								startDate : new Date(selectedDate),
							});

							// Timepicker
							$("#updateAssignmentTimePicker").timepicker({
								showInputs : false
							});

							// Set div attributes
							document.getElementById('updateAssignmentDate')
									.setAttribute('uniqueID', event.id);
							document.getElementById('updateAssignmentDate')
									.setAttribute('userID', userID);
							document.getElementById('updateAssignmentDate')
									.setAttribute('color',
											event.backgroundColor);

							// set other attributes
							document.getElementById('updateAssignmentDate')
									.setAttribute('autoID', autoID);

							document.getElementById('updateAssignmentDate')
									.setAttribute('location', event.location);
							document.getElementById('updateAssignmentDate')
									.setAttribute('year', year);
							document.getElementById('updateAssignmentDate')
									.setAttribute('setNotif', event.setNotif);

							// show update modal
							$('#manageAssignmentModal').modal('show');
						}

					});
}

function showAssignmentPopup(calObject) {
	document.getElementById('inputAssignmentSubject').value='';
	var clickDate = calObject[0].getAttribute('data-date');
	// Initialize The Date range picker without time picker
	$('#assignmentDatePicker').daterangepicker({
		timePicker : false,
		format : 'MM/DD/YYYY h:mm A',
		startDate : new Date(clickDate)
	});
	// Timepicker
	$("#assignmentTimePicker").timepicker({
		showInputs : false
	});

	$('#assignmentDatePicker').on(
			'apply.daterangepicker',
			function(ev, picker) {
				var startRange = picker.startDate.format('MM/DD/YYYY');
				var endRange = picker.endDate.format('MM/DD/YYYY');
				$(this).val(startRange + ' - ' + endRange);
				document.getElementById('selectedAssignmentDate').setAttribute(
						'startRange', startRange);
				document.getElementById('selectedAssignmentDate').setAttribute(
						'endRange', endRange);
			});
	// Initialize TheFlat red color scheme for iCheck
	$('input[type="checkbox"].flat-red, input[type="radio"].flat-red').iCheck({
		checkboxClass : 'icheckbox_flat-green',
		radioClass : 'iradio_flat-green'
	});

	$('#assignmentModal').modal('show');
}
function addAssignmentToCalendar() {
	// Get The data from selected area

	var subject = document.getElementById('inputAssignmentSubject').value;
	var assignment = document.getElementById('inputAssignment').value;
	var isRemiderOn = document.getElementById('assignmentReminder').checked;

	// Get start date and time
	var startRange = document.getElementById('selectedAssignmentDate')
			.getAttribute('startRange');
	var endRange = document.getElementById('selectedAssignmentDate')
			.getAttribute('endRange');
	var time = document.getElementById('assignmentTimePicker').value;

	// This is for 1 Year ,2 Year not for date year
	var selectdYear = document.getElementById('year').value;
	var selectedBranch = document.getElementById('branch').value;

	var start = new Date(startRange);
	var end = new Date(endRange);
	var year = start.getFullYear();
	var month = start.getMonth() + 1; // <--Month Start with 0
	var dates = [];
	var startVal = parseInt(start.getDate());

	var array = new Array();
	while (startVal <= parseInt(end.getDate())) {

		// MM DD YYYY
		var date = month + "/" + startVal + "/" + year;
		var randomColor = getRandomColor();
		var id = getUniqueID();

		// Create a json object
		var jsonObject = {};
		jsonObject.uniqueID = id;
		jsonObject.subject = subject;
		jsonObject.assignment = assignment;
		jsonObject.time = time;
		jsonObject.date = date;
		jsonObject.setReminder = isRemiderOn;
		jsonObject.setNotif = true;
		jsonObject.branch = selectedBranch;
		jsonObject.year = selectdYear;
		jsonObject.userid = UserID;

		var event = {
			id : id,
			title : subject,
			start : new Date(date),
			isRemiderOn : isRemiderOn,
			backgroundColor : randomColor,
			borderColor : randomColor,
			allDay : allDay,
			userid : UserID,
			assignment : assignment,

		};

		$('#assignment_calendar').fullCalendar('renderEvent', event, true);
		dates.push(date);
		array.push(jsonObject);
		startVal++;
		console.log("Assignment Unique ID " + id + " Subject : " + subject
				+ " For User ID " + UserID);
	}
	
	// All this assignment go into db
	
	var url='AssignmentService?serviceRequest=AddAssignment&dataJson='+ JSON.stringify(array);
	document.getElementById("newAssignmentForm").setAttribute('action',url);
	document.getElementById("newAssignmentForm").submit();
//	jQuery
//			.ajax(
//					{
//						url : 'AssignmentService?serviceRequest=AddAssignment&dataJson='
//								+ JSON.stringify(array),
//						type : 'POST',
//						async : false
//					}).done(function(response) {
//				var json = JSON.parse(response);
//				alert(json.message);
//				$('#assignmentModal').modal('hide');
//			});
	$('#assignmentModal').modal('hide');
}
function updateAssignment() {
	// Get values from fields
	var subject = document.getElementById('updateAssignmentSubject').value;
	var assignment = document.getElementById('updateAssignment').value;
	var updateBranch = document.getElementById('updateAssignmentBranch').value;
	var updateYear = document.getElementById('updateAssignmentYear').value;
	// MM DD YYY
	var date = document.getElementById('updateAssignmentDatePicker').value;
	var time = document.getElementById('updateAssignmentTimePicker').value;
	var isRemiderOn = document.getElementById('updateAssignmentReminder').checked;

	// Get values from hidden dive
	var uniqueID = document.getElementById('updateAssignmentDate')
			.getAttribute('uniqueID');
	var autoID = document.getElementById('updateAssignmentDate').getAttribute(
			'autoID');
	var userid = document.getElementById('updateAssignmentDate').getAttribute(
			'userID');
	var color = document.getElementById('updateAssignmentDate').getAttribute(
			'color');

	// var location =
	// document.getElementById('updateAssignmentDate').getAttribute(
	// 'location');
	var setNotif = document.getElementById('updateAssignmentDate')
			.getAttribute('setNotif');

	var eventObject = {
		id : uniqueID,
		// Set the updated properties of calendar and db properites
		title : subject,
		start : new Date(date),
		backgroundColor : color,
		borderColor : color,
		allDay : allDay,

		// Set other properties
		isRemiderOn : isRemiderOn,
		assignment : assignment,
		autoid : autoID,
		userid : userid,
		endDate : date,
		startDate : date,
		time : time,
		branch : updateBranch,
		year : updateYear,
		setNotif : setNotif
	};

	var jsonObject = {};
	jsonObject.id = autoID;
	jsonObject.uniqueID = uniqueID;
	jsonObject.subject = subject;
	jsonObject.assignment = assignment;
	jsonObject.time = time;
	jsonObject.date = date;
	jsonObject.setReminder = isRemiderOn;
	jsonObject.setNotif = true;
	jsonObject.branch = updateBranch;
	jsonObject.year = updateYear;
	jsonObject.userid = userid;
	// All this events go into db
	jQuery
			.ajax(
					{
						url : 'AssignmentService?serviceRequest=UpdateAssignment&dataJson='
								+ JSON.stringify(jsonObject),
						type : 'POST',
						async : false
					}).done(
					function(response) {
						var json = JSON.parse(response);
						alert(json.message);
						if (json.result == true) {
							// Remove the existing event
							$('#assignment_calendar').fullCalendar(
									'removeEvents', uniqueID);
							// Update the event
							$('#assignment_calendar').fullCalendar(
									'renderEvent', eventObject);
						}
						$('#manageAssignmentModal').modal('hide');
					});
	// hide the dialog box
	$('#manageAssignmentModal').modal('hide');
}
function removeAssignment() {
	var uniqueID = document.getElementById('updateAssignmentDate')
			.getAttribute('uniqueID');
	// All this events go into db
	jQuery
			.ajax(
					{
						url : 'AssignmentService?serviceRequest=RemoveAssignment&uniqueID='
								+ uniqueID,
						type : 'POST',
						async : false
					}).done(
					function(response) {
						var json = JSON.parse(response);
						alert(json.message);
						if (json.result == true) {
							// Remove the existing event
							$('#assignment_calendar').fullCalendar(
									'removeEvents', uniqueID);

						}
						$('#manageAssignmentModal').modal('hide');
					});
}