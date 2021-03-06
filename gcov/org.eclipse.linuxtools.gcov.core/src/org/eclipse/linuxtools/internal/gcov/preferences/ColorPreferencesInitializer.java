/*******************************************************************************
 * Copyright (c) 2013, 2018 Kalray.eu and others.
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    Xavier Raynaud <xavier.raynaud@kalray.eu> - initial API and implementation
 *******************************************************************************/
package org.eclipse.linuxtools.internal.gcov.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.linuxtools.internal.gcov.Activator;

/**
 * @author Xavier Raynaud <xavier.raynaud@kalray.eu>
 */
public class ColorPreferencesInitializer extends AbstractPreferenceInitializer {

    public ColorPreferencesInitializer() {
    }

    @Override
    public void initializeDefaultPreferences() {
        IPreferenceStore store = Activator.getDefault().getPreferenceStore();
        store.setDefault(ColorPreferencePage.PREFKEY_COV_MAX_COLOR, "0,128,0"); //$NON-NLS-1$
        store.setDefault(ColorPreferencePage.PREFKEY_COV_MIN_COLOR, "192,255,192"); //$NON-NLS-1$
        store.setDefault(ColorPreferencePage.PREFKEY_COV_0_COLOR, "255,96,96"); //$NON-NLS-1$
        store.setDefault(ColorPreferencePage.PREFKEY_COV_USE_COLORS, true);
        store.setDefault(ColorPreferencePage.PREFKEY_COV_USE_GRADIENT, true);
    }

}
