

<div class="col-md-12" id='examCalendar' style="display: none">
	<div class="box box-primary">
		<div class="box-body no-padding">
			<!-- THE CALENDAR -->
			<div id="exam_calendar"></div>
		</div>
	</div>
</div>


<!-- Add Exam Modal -->
<div id="examModal" class="modal fade" role="dialog">
	<div class="modal-dialog">

		<!-- Modal content-->
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<h4 class="modal-title">Add Exam Entry</h4>
			</div>
			<div class="modal-body">
			<form id="newExamForm" method="POST" enctype="multipart/form-data">
				<div class="form-group">
					<label for="inputSubjectName">Subject Name</label> <input type="text"
						class="form-control" id="inputExamSubject" placeholder="Enter Subject"
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
						class="flat-red" id="examReminder" />
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
							id="examDatePicker" name="examDatePicker" required />
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
								id="examTimePicker" name="examTimePicker" />
						</div>
					</div>
				</div>
				<div class="form-group">
					<label>Attachment</label> 
						<input type="file"  id="examAttachment" name="examAttachment" required="">
				</div>	
				<div id="selectedExamDate"></div>
				</form>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default pull-left"
					data-dismiss="modal">Close</button>
				<button type="button" class="btn btn-primary"
					onclick="addExamScheduleToCalendar()">Add</button>
			</div>
		</div>
	</div>
</div>
<!-- Manage Exam Model -->
<div id="manageExamModal" class="modal fade" role="dialog">
	<div class="modal-dialog">

		<!-- Modal content-->
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<h4 class="modal-title">Exam Schedule</h4>
			</div>
			<div class="modal-body">
				<div class="form-group">
					<label for="updateExamSubject">Subject Name</label> <input type="text"
						class="form-control" id="updateExamSubject" placeholder="Enter Subject"
						required>
				</div>

				<div class="form-group">
					<label>Branch</label> <select class="form-control select2"
						id="updateExamBranch" name="updateExamBranch" required>
						<option selected="selected">CS</option>
					</select>
				</div>
				<!-- /.form-group -->
				<div class="form-group">
					<label>Year</label> <select class="form-control select2" id="updateExamYear"
						name="updateExamYear" required>
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
							id="updateExamDatePicker" required />
					</div>
					<!-- /.input group -->
				</div>

				<div class="bootstrap-timepicker">
					<div class="form-group">
						<label>Time:</label>
						<div class="input-group">
							<input type="text" class="form-control timepicker"
								id="updateExamTimePicker" />
							<div class="input-group-addon">
								<i class="fa fa-clock-o"></i>
							</div>
						</div>
						<!-- /.input group -->
					</div>
					<!-- /.form group -->
				</div>
				<!-- /.form group -->

				<div id="updateExamDate"></div>
				<div class="form-group">
					<label>Set Reminder</label> <label> <input type="checkbox"
						class="flat-red" id="updateExamReminder" />
					</label>
				</div>
				<!-- /.form group -->
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default pull-left"
					data-dismiss="modal">Close</button>
				<button type="button" class="btn btn-danger"
					onclick="removeExam()">Remove</button>
				<button type="button" class="btn btn-primary"
					onclick="updateExam()">Update</button>
			</div>
		</div>
	</div>
</div>