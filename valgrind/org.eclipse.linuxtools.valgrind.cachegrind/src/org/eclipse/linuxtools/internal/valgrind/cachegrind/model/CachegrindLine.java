/*******************************************************************************
 * Copyright (c) 2009, 2018 Red Hat, Inc.
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    Elliott Baron <ebaron@redhat.com> - initial API and implementation
 *******************************************************************************/
package org.eclipse.linuxtools.internal.valgrind.cachegrind.model;

import org.eclipse.core.runtime.IAdaptable;


public class CachegrindLine implements ICachegrindElement {
    private CachegrindFunction parent;
    private int line;
    private long[] values;

    public CachegrindLine(CachegrindFunction parent, int line, long[] values) {
        this.parent = parent;
        this.line = line;
        this.values = values;
    }

    @Override
    public ICachegrindElement[] getChildren() {
        return null;
    }

    public int getLine() {
        return line;
    }

    public long[] getValues() {
        return values;
    }

    @Override
    public ICachegrindElement getParent() {
        return parent;
    }

    @Override
    public int compareTo(ICachegrindElement o) {
        int result = 0;
        if (o instanceof CachegrindLine) {
            result = line - ((CachegrindLine) o).getLine();
        }
        return result;
    }

    @Override
    public IAdaptable getModel() {
        return null;
    }

}
