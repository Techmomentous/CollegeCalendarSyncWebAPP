package com.cc.server.dao.modal;

public class EventCat {
	
	private Long id;
	private String eventType;
	private String uniqueID;

	// -----------------------------------------
				// Getter & Setter
	// -----------------------------------------
	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getEventType() {
		return eventType;
	}


	public void setEventType(String eventType) {
		this.eventType = eventType;
	}
	

	public String getUniqueID() {
		return uniqueID;
	}


	public void setUniqueID(String uniqueID) {
		this.uniqueID = uniqueID;
	}


	/**
	 * Default Constructor
	 */
	public EventCat() {
	}
	
	/**
	 * Constructor With All Table fields
	 * @param examID
	 * @param eventType

	 */
	
	public EventCat(Long id, String eventType) {
		
		super();
		this.id=id;
		this.eventType=eventType;
		
	}
	
	/**
	 * Constructor For Adding New Row In Table
	 * @param eventType
    */
	
	public EventCat(String eventType) {
		
		super();
		this.eventType=eventType;
		
	}
	
	public String toString(){
		return "Exam[ id="+ id +", eventType="+ eventType+"] ";
		}


	public boolean setUpdated(boolean b) {
		return b;
	}


}
