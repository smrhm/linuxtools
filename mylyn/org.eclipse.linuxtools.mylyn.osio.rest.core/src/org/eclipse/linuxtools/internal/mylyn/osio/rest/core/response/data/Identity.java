/*******************************************************************************
 * Copyright (c) 2017, 2018 Red Hat Inc. and others.
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Red Hat - Initial Contribution
 *******************************************************************************/
package org.eclipse.linuxtools.internal.mylyn.osio.rest.core.response.data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class Identity implements IdNamed {

	private String userID;
	
	private String identityID;
	
	private Date created_at;
	
	private Date updated_at;
	
	private String fullName;
	
	private String imageURL;
	
	private String username;
	
	private Boolean registrationCompleted;
	
	private String email;
	
	private String company;
	
	private String bio;
	
	private String url;
	
	private String providerType;
	
	// for testing purposes only
	public Identity (String userID, String identityID, Date created_at, Date updated_at,
			String fullName, String imageURL, String username, Boolean registrationCompleted,
			String email, String company, String bio, String url, String providerType) {
		this.userID = userID;
		this.identityID = identityID;
		this.created_at = created_at;
		this.updated_at = updated_at;
		this.fullName = fullName;
		this.imageURL = imageURL;
		this.username = username;
		this.registrationCompleted = registrationCompleted;
		this.email = email;
		this.company = company;
		this.bio = bio;
		this.url = url;
		this.providerType = providerType;
	}
	
	public Identity() {
		
	}
	
	public String getName() {
		return username;
	}
	
	public String getId() {
		return identityID;
	}
	
	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getIdentityID() {
		return identityID;
	}

	public void setIdentityID(String identityID) {
		this.identityID = identityID;
	}

	public Date getCreated_at() {
		return created_at;
	}

	public void setCreated_at(String created_at_string) {
		SimpleDateFormat iso8601Format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'.'SSSSSS'Z'", //$NON-NLS-1$
				Locale.US);
		iso8601Format.setTimeZone(TimeZone.getTimeZone("UTC")); //$NON-NLS-1$
		Date tempDate = null;
		try {
			tempDate = iso8601Format.parse(created_at_string);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		this.created_at = tempDate;
	}

	public Date getUpdated_at() {
		return updated_at;
	}

	public void setUpdated_at(String updated_at_string) {
		SimpleDateFormat iso8601Format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'.'SSSSSS'Z'", //$NON-NLS-1$
				Locale.US);
		iso8601Format.setTimeZone(TimeZone.getTimeZone("UTC")); //$NON-NLS-1$
		Date tempDate = null;
		try {
			tempDate = iso8601Format.parse(updated_at_string);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		this.updated_at = tempDate;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getImageURL() {
		return imageURL;
	}

	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Boolean getRegistrationCompleted() {
		return registrationCompleted;
	}

	public void setRegistrationCompleted(Boolean registrationCompleted) {
		this.registrationCompleted = registrationCompleted;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getProviderType() {
		return providerType;
	}

	public void setProviderType(String providerType) {
		this.providerType = providerType;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	
}
