/*******************************************************************************
 * Copyright (c) 2013, 2018 Frank Becker and others.
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Frank Becker - initial API and implementation
 *     Red Hat Inc. - modified for use with OpenShift.io
 *******************************************************************************/

package org.eclipse.linuxtools.internal.mylyn.osio.rest.core.response.data;

public class ErrorResponse {
	private boolean error;

	private String message;

	private int code;

	private String documentation;
	
	// for testing purposes only
	public ErrorResponse (boolean error, String message, int code, String documentation) {
		this.error = error;
		this.message = message;
		this.code = code;
		this.documentation = documentation;
	}

	public boolean isError() {
		return error;
	}

	public void setError(boolean error) {
		this.error = error;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
	
	public String getDocumention() {
		return documentation;
	}

}
