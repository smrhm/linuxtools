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

public class IterationRelationships {

	private RelationGeneric space;
	
	private RelationGeneric parent;
	
	private RelationGeneric workitems;
	
	// for testing purposes only
	public IterationRelationships (RelationGeneric space, RelationGeneric parent,
			RelationGeneric workitems) {
		this.space = space;
		this.parent = parent;
		this.workitems = workitems;
	}
	
	public RelationGeneric getSpace() {
		return space;
	}
	
	public RelationGeneric getParent() {
		return parent;
	}
	
	public RelationGeneric getWorkItems() {
		return workitems;
	}
	
}
