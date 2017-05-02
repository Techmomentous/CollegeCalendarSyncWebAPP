<style>
table {
	width: 100%;
}

thead,tbody,tr,td,th {
	display: block;
}

tr:after {
	content: ' ';
	display: block;
	visibility: hidden;
	clear: both;
}

thead th {
	height: 30px;

	/*text-align: left;*/
}

tbody {
	height: 120px;
	overflow-y: auto;
}

thead {
	/* fallback */
	
}

tbody td,thead th {
	width: 19.2%;
	float: left;
}
</style>
<script>
function showEventDialog(){
	$('#addNewEventDialog').modal('show');
}
</script>
<div class="row col-md-10 col-md-offset-1 custyle" id="eventPanel" style="display:none;">
	<table class="table table-striped custab">
		<thead>
			<a href="javascript:showEventDialog()"
				class="btn btn-primary btn-xs pull-right"><b>+</b> Add new Event</a>
			<tr>
				<th>SRNO</th>
				<th>Event Details</th>
				<th>Event Description</th>
				<th>Event Date</th>
				<th class="text-center">Action</th>
			</tr>
		</thead>
		<tbody id="manageEventTable">
			<tr>
				<td>1</td>
				<td>News</td>
				<td>News Cate</td>
				<td>Event Date</td>
				<td class="text-center"><a class='btn btn-info btn-xs' href="#"><span
						class="fa fa-edit"></span> Edit</a> <a href="#"
					class="btn btn-danger btn-xs"><span class="fa fa-remove"></span>
						Del</a></td>
			</tr>
			<tr>
				<td>2</td>
				<td>Products</td>
				<td>Main Products</td>
				<td>Event Date</td>
				<td class="text-center"><a class='btn btn-info btn-xs' href="#"><span
						class="fa fa-edit"></span> Edit</a> <a href="#"
					class="btn btn-danger btn-xs"><span class="fa fa-remove"></span>
						Del</a></td>
			</tr>
			<tr>
				<td>3</td>
				<td>Blogs</td>
				<td>Parent Blogs</td>
				<td>Event Date</td>
				<td class="text-center"><a class='btn btn-info btn-xs' href="#"><span
						class="fa fa-edit"></span> Edit</a> <a href="#"
					class="btn btn-danger btn-xs"><span class="fa fa-remove"></span>
						Del</a></td>
			</tr>
			<tr>
				<td>3</td>
				<td>Blogs</td>
				<td>Parent Blogs</td>
				<td>Event Date</td>
				<td class="text-center"><a class='btn btn-info btn-xs' href="#"><span
						class="fa fa-edit"></span> Edit</a> <a href="#"
					class="btn btn-danger btn-xs"><span class="fa fa-remove"></span>
						Del</a></td>
			</tr>
			<tr>
				<td>3</td>
				<td>Blogs</td>
				<td>Parent Blogs</td>
				<td>Event Date</td>
				<td class="text-center"><a class='btn btn-info btn-xs' href="#"><span
						class="fa fa-edit"></span> Edit</a> <a href="#"
					class="btn btn-danger btn-xs"><span class="fa fa-remove"></span>
						Del</a></td>
			</tr>
			<tr>
				<td>3</td>
				<td>Blogs</td>
				<td>Parent Blogs</td>
				<td>Event Date</td>
				<td class="text-center"><a class='btn btn-info btn-xs' href="#"><span
						class="fa fa-edit"></span> Edit</a> <a href="#"
					class="btn btn-danger btn-xs"><span class="fa fa-remove"></span>
						Del</a></td>
			</tr>
		</tbody>
	</table>
</div>

<div class="modal fade" id="addNewEventDialog" role="dialog" >
<div class="modal-dialog">
	<!-- Modal content-->
	<div class="modal-content">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal">&times;</button>
			<h4 class="modal-title">Add New Event</h4>
		</div>
		<div class="modal-body">
			<div class="form-group">
				<label for="question">Event Name:</label> <input type="text"
					class="form-control" id="eventName">
			</div>
			<div class="form-group">
				<label for="option1">Event Description:</label> <input type="text"
					class="form-control" id="eventDescription">
			</div>
			<div class="form-group">
				<label for="option2">Event Date:</label> <input type="text"
					class="form-control" id="eventDate">
			</div>
			<div class="form-group">
				<label for="option3">Option 3:</label> <input type="text"
					class="form-control" id="option3">
			</div>
			<div class="form-group">
				<label for="option4">Option 4:</label> <input type="text"
					class="form-control" id="option4">
			</div>
			<div class="form-group">
				<label for="answer">Right Answer:</label> <input type="text"
					class="form-control" id="answer">
			</div>
		</div>
		<div class="modal-footer">
			<button type="button" class="btn btn-info" data-dismiss="modal"
				onclick="performUpdate()">Update</button>
		</div>
	</div>

</div>
</div>
