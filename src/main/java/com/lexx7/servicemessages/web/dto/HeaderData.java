package com.lexx7.servicemessages.web.dto;

import java.io.Serializable;

public class HeaderData implements Serializable {
	private static final long serialVersionUID = -720303155014048799L;
	
	private String currentUserId;

	public String getCurrentUserId() {
		return currentUserId;
	}

	public void setCurrentUserId(String currentUserId) {
		this.currentUserId = currentUserId;
	}

}
