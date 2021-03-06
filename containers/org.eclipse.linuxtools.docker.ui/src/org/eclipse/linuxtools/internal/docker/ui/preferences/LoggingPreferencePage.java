/*******************************************************************************
 * Copyright (c) 2015, 2018 Red Hat.
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
package org.eclipse.linuxtools.internal.docker.ui.preferences;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.linuxtools.docker.ui.Activator;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

public class LoggingPreferencePage extends FieldEditorPreferencePage implements
		IWorkbenchPreferencePage {

	private static final String AUTO_START_MSG = "AutoLogOnStart.label"; //$NON-NLS-1$
	private static final String TIME_STAMP_MSG = "LogTimeStamp.label"; //$NON-NLS-1$

	private BooleanFieldEditor autoLogOnStart;
	private BooleanFieldEditor addTimeStamp;

	public LoggingPreferencePage() {
		super(GRID);
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
	}

	@Override
	public void init(IWorkbench workbench) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void createFieldEditors() {
		autoLogOnStart = new BooleanFieldEditor(
				PreferenceConstants.AUTOLOG_ON_START,
				PreferenceMessages.getString(AUTO_START_MSG), getFieldEditorParent());
		addField(autoLogOnStart);

		addTimeStamp = new BooleanFieldEditor(
				PreferenceConstants.LOG_TIMESTAMP,
				PreferenceMessages.getString(TIME_STAMP_MSG), getFieldEditorParent());
		addField(addTimeStamp);
	}

}
