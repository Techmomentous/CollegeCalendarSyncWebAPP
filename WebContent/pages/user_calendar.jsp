

<div class="col-md-12" id='userCalendar' style="display: none">
	<div class="box box-primary">
		<div class="box-body no-padding">
			<!-- THE CALENDAR -->
			<div id="user_calendar"></div>
		</div>
	</div>
</div>


<!-- Add TimeTable Modal -->
<div id="ttModal" class="modal fade" role="dialog">
	<div class="modal-dialog">
		<!-- Modal content-->
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<h4 class="modal-title">Add Time Table Entry</h4>
			</div>
			<div class="modal-body">
				<form id="newtimeTableForm" method="POST" enctype="multipart/form-data">
				<div class="form-group">
					<label for="inputSubjectName">Subject Name</label> <input type="text"
						class="form-control" id="inputSubjectName" placeholder="Enter Subject"
						required>
				</div>

				<div class="form-group">
					<label>Branch</label> <select class="form-control select2"
						id="branch" name="branch" required>
						<option selected="selected">CS</option>
					</select>
				</div>

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
						class="flat-red" id="ttReminder" />
					</label>
				</div>
				<!-- Date and time range -->
				<div class="form-group">
					<label>Date Range: (MM / DD / YYYY)</label>
					<div class="input-group">
						<div class="input-group-addon">
							<i class="fa fa-clock-o"></i>
						</div>
						<input type="text" class="form-control pull-right"
							id="ttDatePicker" name="ttDatePicker" required />
					</div>
				</div>
				<div class="bootstrap-timepicker">
					<div class="form-group">
						<label>Time:</label>
						<div class="input-group">
						<div class="input-group-addon">
								<i class="fa fa-clock-o"></i>
							</div>
							<input type="text" class="form-control timepicker"
								id="ttTimePicker" name="ttTimePicker" />
						</div>
					</div>
				</div>
				<div class="form-group">
					<label>Attachment</label> 
						<input type="file"  id="ttAttachment" name="ttAttachment" required="">
				</div>		
				<div id="selectedTTDate"></div>
				</form>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default pull-left"
					data-dismiss="modal">Close</button>
				<button type="button" class="btn btn-primary"
					onclick="addTimeTableToCalendar()">Add Time Table</button>
			</div>
		</div>
	</div>
</div>
</div>
<!-- Manage Timetable Model -->
<div id="manageTimeTableModal" class="modal fade" role="dialog">
	<div class="modal-dialog">

		<!-- Modal content-->
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<h4 class="modal-title">Time Table</h4>
			</div>
			<div class="modal-body">
				<div class="form-group">
					<label for="updateSubjectName">Subject Name</label> <input type="text"
						class="form-control" id="updateSubjectName" placeholder="Enter Subject"
						required>
				</div>

				<div class="form-group">
					<label>Branch</label> <select class="form-control select2"
						id="updateTTBranch" name="updateBranch" required>
						<option selected="selected">CS</option>
					</select>
				</div>
				<!-- /.form-group -->
				<div class="form-group">
					<label>Year</label> <select class="form-control select2" id="updateTTYear"
						name="updateTTYear" required>
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
							id="updateTTDatePicker" required />
					</div>
				</div>

				<div class="bootstrap-timepicker">
					<div class="form-group">
						<label>Time:</label>
						<div class="input-group">
							<input type="text" class="form-control timepicker"
								id="updateTTTimePicker" />
							<div class="input-group-addon">
								<i class="fa fa-clock-o"></i>
							</div>
						</div>
					</div>
				</div>
				<div id="updateTTDate"></div>
				<div class="form-group">
					<label>Set Reminder</label> <label> <input type="checkbox"
						class="flat-red" id="updateTTReminder" />
					</label>
				</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default pull-left"
					data-dismiss="modal">Close</button>
				<button type="button" class="btn btn-danger"
					onclick="removeTTToCalendar()">Remove</button>
				<button type="button" class="btn btn-primary"
					onclick="updateTTToCalendar()">Update</button>
			</div>
		</div>
	</div>
</div>