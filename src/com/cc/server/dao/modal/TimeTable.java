
package com.cc.server.dao.modal;

public class TimeTable {

	private Long	id;
	private Long	userID;
	private String	subject;
	private String	time;
	private String	startDate;
	private String	endDate;
	private String	setReminder;
	private String	setNotif;
	private String	branch;
	private String	year;
	private String	uniqueID;
	private String	attachment;
	private String	attachmenturl;
	boolean			isUpdate;

	// -----------------------------------------
	// Getter & Setter
	// -----------------------------------------
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserID() {
		return userID;
	}

	public void setUserID(Long userID) {
		this.userID = userID;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
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

	public String getSetReminder() {
		return setReminder;
	}

	public void setSetReminder(String setReminder) {
		this.setReminder = setReminder;
	}

	public String getSetNotif() {
		return setNotif;
	}

	public void setSetNotif(String setNotif) {
		this.setNotif = setNotif;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
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

	/**
	 * Default Constructor
	 */
	public TimeTable() {

	}

	/**
	 * Constructor With All Table fields
	 * @param id
	 * @param subject
	 * @param time
	 * @param startdate
	 * @param enddate
	 * @param setreminder
	 * @param setnotif
	 * @param branch
	 */
	public TimeTable(Long id, String subject, String time, String startDate, String endDate, String setReminder, String setNotif, String branch) {

		super();
		this.id = id;
		this.subject = subject;
		this.time = time;
		this.startDate = startDate;
		this.endDate = endDate;
		this.setReminder = setReminder;
		this.setNotif = setNotif;
		this.branch = branch;

	}

	/**
	 * Constructor For Adding New Row In Table
	 * @param subject
	 * @param time
	 * @param startdate
	 * @param enddate
	 * @param setreminder
	 * @param setnotif
	 * @param branch
	 */
	public TimeTable(String subject, String time, String startDate, String endDate, String setReminder, String setNotif, String branch) {

		super();
		this.subject = subject;
		this.time = time;
		this.startDate = startDate;
		this.endDate = endDate;
		this.setReminder = setReminder;
		this.setNotif = setNotif;
		this.branch = branch;
	}

	@Override
	public String toString() {
		return "TimeTable[ id=" + id + ", subject=" + subject + ", time=" + time + ", startDate=" + startDate + ", endDate=" + endDate + "," + "setRemminder=" + setReminder
				+ ", setNotif=" + setNotif + ", branch=" + branch + "] ";
	}

}
