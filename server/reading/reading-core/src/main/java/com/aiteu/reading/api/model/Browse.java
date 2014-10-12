package com.aiteu.reading.api.model;

public class Browse {
	
	private String browseId;
	private String browseValue;
	private String description;
	
	public Browse(){
		
	}

	public String getBrowseId() {
		return browseId;
	}

	public void setBrowseId(String browseId) {
		this.browseId = browseId;
	}

	public String getBrowseValue() {
		return browseValue;
	}

	public void setBrowseValue(String browseValue) {
		this.browseValue = browseValue;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
