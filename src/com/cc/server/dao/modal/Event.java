
package com.cc.server.dao.modal;

public class Event {

	private Long	id;
	private Long	eventCatId;
	private String	title;
	private String	description;
	private String	location;
	private String	time;
	private String	startDate;
	private String	endDate;
	private String	setReminder;
	private String	notification;
	private String	branch;
	private String	year;
	private String	uniqueID;
	private String	attachment;
	private String	attachmenturl;
	private boolean	isUpdate;

	/**
	 * Default Constructor
	 */
	public Event() {

	}

	/**
	 * Constructor With All Table fields
	 * @param id
	 * @param uniqueID
	 * @param eventCatId
	 * @param title
	 * @param description
	 * @param location
	 * @param time
	 * @param startDate
	 * @param endDate
	 * @param setReminder
	 * @param setNotif
	 * @param year
	 * @param branch
	 */
	public Event(Long id, String uniqueID, Long eventCatId, String title, String description, String location, String time, String startDate, String endDate, String setReminder,
			String setNotif, String year, String branch) {

		super();
		this.id = id;
		this.uniqueID = uniqueID;
		this.eventCatId = eventCatId;
		this.title = title;
		this.description = description;
		this.location = location;
		this.time = time;
		this.startDate = startDate;
		this.endDate = endDate;
		this.setReminder = setReminder;
		notification = setNotif;
		this.year = year;
		this.branch = branch;

	}

	/**
	 * Constructor For Adding New Row In Table
	/**
	 * @param uniqueID
	 * @param eventCatId
	 * @param title
	 * @param description
	 * @param location
	 * @param time
	 * @param startDate
	 * @param endDate
	 * @param setReminder
	 * @param setNotif
	 * @param year
	 * @param branch
	 */
	public Event(String uniqueID, Long eventCatId, String title, String description, String location, String time, String startDate, String endDate, String setReminder,
			String setNotif, String year, String branch) {

		super();
		this.title = title;
		this.description = description;
		this.location = location;
		this.time = time;
		this.startDate = startDate;
		this.endDate = endDate;
		this.setReminder = setReminder;
		notification = setNotif;
		this.year = year;
		this.branch = branch;

	}

	// -----------------------------------------
	// Getter & Setter
	// -----------------------------------------
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getEventCatId() {
		return eventCatId;
	}

	public void setEventCatId(Long eventCatId) {
		this.eventCatId = eventCatId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getReminder() {
		return setReminder;
	}

	public void setReminder(String setReminder) {
		this.setReminder = setReminder;
	}

	public String getNotification() {
		return notification;
	}

	public void setNotification(String notification) {
		this.notification = notification;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public String getUniqueID() {
		return uniqueID;
	}

	public void setUniqueID(String uniqueID) {
		this.uniqueID = uniqueID;
	}

	public boolean isUpdate() {
		return isUpdate;
	}

	public void setUpdate(boolean isUpdate) {
		this.isUpdate = isUpdate;
	}

	public String getAttachment() {
		return attachment;
	}

	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}

	public String getAttachmenturl() {
		return attachmenturl;
	}

	public void setAttachmenturl(String attachmenturl) {
		this.attachmenturl = attachmenturl;
	}

	@Override
	public String toString() {
		return "Event[ id=" + id + ", title=" + title + ", description=" + description + ", location=" + location + ", time=" + time + ", startDate=" + startDate + ", endDate="
				+ endDate + "," + "setRemminder=" + setReminder + ", setNotif=" + notification + ", year=" + year + ", branch=" + branch + "] ";
	}

}
