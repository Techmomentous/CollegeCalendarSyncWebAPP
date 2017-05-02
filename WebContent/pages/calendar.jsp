

<div class="col-md-12" id='eventCalendar' style="display: none">
	<div class="box box-primary">
		<div class="box-body no-padding">
			<!-- THE CALENDAR -->
			<div id="eventCalendar"></div>
		</div>
	</div>
</div>


<!-- Add Event Modal -->
<div id="eventModal" class="modal fade" role="dialog">
	<div class="modal-dialog">

		<!-- Modal content-->
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<h4 class="modal-title">Add Event Detail</h4>
			</div>
			<div class="modal-body">
				<div class="form-group">
					<label for="inputEventName">Event Name</label> <input type="text"
						class="form-control" id="inputEventName" placeholder="Enter name"
						required>
				</div>
				<div class="form-group">
					<label for="inputEventDesc">Event Description</label> <input
						type="text" class="form-control" id="inputEventDesc"
						placeholder="Enter description" required>
				</div>
				<div class="form-group">
					<label>Branch</label> <select class="form-control select2"
						id="branch" name="branch" required>
						<option selected="selected">CS</option>
					</select>
				</div>

				<div class="form-group">
					<label>Event Type</label> <select class="form-control select2"
						id="eventype" name="eventype" required>
						<option selected="selected">Sports</option>
						<option>Campus</option>
						<option>Fees</option>
					</select>
				</div>
				<!-- /.form-group -->
				<div class="form-group">
					<label>Year</label> <select class="form-control select2" id="year"
						name="year" required>
						<option selected="selected">1 Year</option>
						<option>2 Year</option>
						<option>3 Year</option>
					</select>
				</div>
				<div class="form-group">
					<label>Set Reminder</label> <label> <input type="checkbox"
						class="flat-red" id="reminder" />
					</label>
				</div>
				<!-- /.form-group -->
				<!-- Date and time range -->
				<div class="form-group">
					<label>Date Range: (MM / DD / YYYY)</label>
					<div class="input-group">
						<div class="input-group-addon">
							<i class="fa fa-clock-o"></i>
						</div>
						<input type="text" class="form-control pull-right"
							id="selectedDataTime" required />
					</div>
					<!-- /.input group -->
				</div>
				<div class="bootstrap-timepicker">
					<div class="form-group">
						<label>Time:</label>
						<div class="input-group">
							<div class="input-group-addon">
								<i class="fa fa-clock-o"></i>
							</div>
							<input type="text" class="form-control timepicker"
								id="selectedTime" name="selectedTime" />
						</div>
						<!-- /.input group -->
					</div>
					<!-- /.form group -->
				</div>
<!-- 				<div class="form-group"> -->
<!-- 					<label for="attachment">Attachment</label> <input type="file" -->
<!-- 						id="attachment"> -->
<!-- 					<p class="help-block"></p> -->
<!-- 				</div> -->

				<div id="selectedDate"></div>

				<!-- /.form group -->
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default pull-left"
					data-dismiss="modal">Close</button>
				<button type="button" class="btn btn-primary"
					onclick="addEventToCalendar()">Add Event</button>
			</div>
		</div>
	</div>
</div>
<!-- Manage Event Model -->
<div id="manageEventModal" class="modal fade" role="dialog">
	<div class="modal-dialog">

		<!-- Modal content-->
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<h4 class="modal-title">Event Detail</h4>
			</div>
			<div class="modal-body">
				<div class="form-group">
					<label for="inputEventName">Event Name</label> <input type="text"
						class="form-control" id="updateEventName" placeholder="Enter name"
						required>
				</div>
				<div class="form-group">
					<label for="inputEventDesc">Event Description</label> <input
						type="text" class="form-control" id="updateEventDesc"
						placeholder="Enter description" required>
				</div>
				<div class="form-group">
					<label>Branch</label> <select class="form-control select2"
						id="updateBranch" name="updateBranch" required>
						<option selected="selected">CS</option>
					</select>
				</div>

				<div class="form-group">
					<label>Event Type</label> <select class="form-control select2"
						id="updateEvent" name="updateEvent" required>
						<option selected="selected">Sports</option>
						<option>Campus</option>
						<option>Fees</option>
					</select>
				</div>
				<!-- /.form-group -->
				<div class="form-group">
					<label>Year</label> <select class="form-control select2"
						id="updateYear" name="updateYear" required>
						<option selected="selected">1 Year</option>
						<option>2 Year</option>
						<option>3 Year</option>
					</select>
				</div>
				<!-- Date and time range -->
				<div class="form-group">
					<label>Date : (MM / DD / YYYY)</label>
					<div class="input-group">
						<div class="input-group-addon">
							<i class="fa fa-clock-o"></i>
						</div>
						<input type="text" class="form-control pull-right"
							id="updateDatePicker" required />
					</div>
					<!-- /.input group -->
				</div>

				<div class="bootstrap-timepicker">
					<div class="form-group">
						<label>Time:</label>
						<div class="input-group">
							<input type="text" class="form-control timepicker"
								id="updateTimePicker" />
							<div class="input-group-addon">
								<i class="fa fa-clock-o"></i>
							</div>
						</div>
						<!-- /.input group -->
					</div>
					<!-- /.form group -->
				</div>
				<!-- /.form group -->

				<div id="updateDate"></div>
				<div class="form-group">
					<label>Set Reminder</label> <label> <input type="checkbox"
						class="flat-red" id="updateReminder" />
					</label>
				</div>
				<!-- /.form group -->
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default pull-left"
					data-dismiss="modal">Close</button>
				<button type="button" class="btn btn-danger"
					onclick="removeEventToCalendar()">Remove Event</button>
				<button type="button" class="btn btn-primary"
					onclick="updateEventToCalendar()">Update Event</button>
			</div>
		</div>
	</div>
</div>