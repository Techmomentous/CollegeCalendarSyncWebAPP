

<div class="col-md-12" id='assignmentCalendar' style="display: none">
	<div class="box box-primary">
		<div class="box-body no-padding">
			<!-- THE CALENDAR -->
			<div id="assignment_calendar"></div>
		</div>
	</div>
</div>


<!-- Add Assignment Modal -->
<div id="assignmentModal" class="modal fade" role="dialog">
	<div class="modal-dialog">

		<!-- Modal content-->
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<h4 class="modal-title">Add Assignment Entry</h4>
			</div>
			<div class="modal-body">
			<form id="newAssignmentForm" method="POST" enctype="multipart/form-data">
			<div class="form-group">
					<label for="inputAssignmentSubject">Subject</label> <input type="text"
						class="form-control" id="inputAssignmentSubject" placeholder="Enter Subject"
						required>
				</div>
				<div class="form-group">
					<label for="inputAssignment">Assignment</label> <input type="text"
						class="form-control" id="inputAssignment" placeholder="Enter Assignment"
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
						class="flat-red" id="assignmentReminder" />
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
							id="assignmentDatePicker" name="assignmentDatePicker" required />
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
								id="assignmentTimePicker" name="assignmentTimePicker" />
						</div>
					</div>
				</div>
				<div class="form-group">
					<label>Attachment</label> 
						<input type="file"  id="assignmentAttachment" name="assignmentAttachment" required="">
				</div>
				<div id="selectedAssignmentDate"></div>
				</form>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default pull-left"
					data-dismiss="modal">Close</button>
				<button type="button" class="btn btn-primary"
					onclick="addAssignmentToCalendar()">Add</button>
			</div>
		</div>
	</div>
</div>
<!-- Manage Assignment Model -->
<div id="manageAssignmentModal" class="modal fade" role="dialog">
	<div class="modal-dialog">

		<!-- Modal content-->
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<h4 class="modal-title">Assignment</h4>
			</div>
			<div class="modal-body">
				<div class="form-group">
					<label for="updateAssignmentSubject">Subject</label> <input type="text"
						class="form-control" id="updateAssignmentSubject" placeholder="Enter Subject"
						required>
				</div>
				<div class="form-group">
					<label for="updateAssignment">Assignment</label> <input type="text"
						class="form-control" id="updateAssignment" placeholder="Enter Assignment"
						required>
				</div>

				<div class="form-group">
					<label>Branch</label> <select class="form-control select2"
						id="updateAssignmentBranch" name="updateAssignmentBranch" required>
						<option selected="selected">CS</option>
					</select>
				</div>
				<!-- /.form-group -->
				<div class="form-group">
					<label>Year</label> <select class="form-control select2" id="updateAssignmentYear"
						name="updateAssignmentYear" required>
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
							id="updateAssignmentDatePicker" required />
					</div>
					<!-- /.input group -->
				</div>

				<div class="bootstrap-timepicker">
					<div class="form-group">
						<label>Time:</label>
						<div class="input-group">
							<input type="text" class="form-control timepicker"
								id="updateAssignmentTimePicker" />
							<div class="input-group-addon">
								<i class="fa fa-clock-o"></i>
							</div>
						</div>
						<!-- /.input group -->
					</div>
					<!-- /.form group -->
				</div>
				<!-- /.form group -->

				<div id="updateAssignmentDate"></div>
				<div class="form-group">
					<label>Set Reminder</label> <label> <input type="checkbox"
						class="flat-red" id="updateAssignmentReminder" />
					</label>
				</div>
				<!-- /.form group -->
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default pull-left"
					data-dismiss="modal">Close</button>
				<button type="button" class="btn btn-danger"
					onclick="removeAssignment()">Remove</button>
				<button type="button" class="btn btn-primary"
					onclick="updateAssignment()">Update</button>
			</div>
		</div>
	</div>
</div>