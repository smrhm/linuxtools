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
 *     Red Hat Inc - modified to handle resource moved permanently
 *******************************************************************************/

package org.eclipse.linuxtools.internal.mylyn.osio.rest.core;

import org.apache.http.Header;

public class OSIORestResourceMovedPermanentlyException extends OSIORestException {

	private static final long serialVersionUID = 5227546210820677763L;
	
	private Header header;

	public OSIORestResourceMovedPermanentlyException(Header header) {
		this.header = header;
	}

	public OSIORestResourceMovedPermanentlyException(Header header, String message) {
		super(message);
		this.header = header;
	}

	public OSIORestResourceMovedPermanentlyException(Header header, Throwable cause) {
		super(cause);
		this.header = header;
	}

	public OSIORestResourceMovedPermanentlyException(Header header, String message, Throwable cause) {
		super(message, cause);
		this.header = header;
	}
	
	public Header getHeader() {
		return header;
	}
	
}
