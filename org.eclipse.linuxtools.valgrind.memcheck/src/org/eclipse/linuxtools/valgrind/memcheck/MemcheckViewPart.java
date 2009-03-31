/*******************************************************************************
 * Copyright (c) 2008 Red Hat, Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Elliott Baron <ebaron@redhat.com> - initial API and implementation
 *******************************************************************************/ 
package org.eclipse.linuxtools.valgrind.memcheck;

import org.eclipse.core.runtime.Path;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.ITreeSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.linuxtools.profiling.ui.ProfileUIUtils;
import org.eclipse.linuxtools.valgrind.memcheck.model.RootTreeElement;
import org.eclipse.linuxtools.valgrind.memcheck.model.StackFrameTreeElement;
import org.eclipse.linuxtools.valgrind.memcheck.model.ValgrindTreeElement;
import org.eclipse.linuxtools.valgrind.ui.CollapseAction;
import org.eclipse.linuxtools.valgrind.ui.ExpandAction;
import org.eclipse.linuxtools.valgrind.ui.IValgrindToolView;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.ViewPart;

public class MemcheckViewPart extends ViewPart implements IValgrindToolView {
	protected TreeViewer viewer;
	protected ValgrindError[] errors;
	protected IDoubleClickListener doubleClickListener;
	protected ITreeContentProvider contentProvider;
	protected IAction expandAction;
	protected IAction collapseAction;

	@Override
	public void createPartControl(Composite parent) {
		viewer = new TreeViewer(parent, SWT.SINGLE | SWT.H_SCROLL | SWT.V_SCROLL);
		viewer.getControl().setLayoutData(new GridData(GridData.FILL_BOTH));
		contentProvider = new ITreeContentProvider() {

			public Object[] getChildren(Object parentElement) {
				return ((ValgrindTreeElement) parentElement).getChildren();
			}

			public Object getParent(Object element) {
				return ((ValgrindTreeElement) element).getParent();
			}

			public boolean hasChildren(Object element) {
				return getChildren(element).length > 0;
			}

			public Object[] getElements(Object inputElement) {
				return getChildren(inputElement);
			}

			public void dispose() {}

			public void inputChanged(Viewer viewer, Object oldInput,
					Object newInput) {}

		};
		viewer.setContentProvider(contentProvider);

		viewer.setLabelProvider(new LabelProvider() {
			@Override
			public String getText(Object element) {
				return ((ValgrindTreeElement) element).getText();
			}

			@Override
			public Image getImage(Object element) {
				return ((ValgrindTreeElement) element).getImage();
			}

		});

		doubleClickListener = new IDoubleClickListener() {

			public void doubleClick(DoubleClickEvent event) {
				Object element = ((TreeSelection) event.getSelection()).getFirstElement();
				if (element instanceof StackFrameTreeElement) {
					ValgrindStackFrame frame = ((StackFrameTreeElement) element).getFrame();
					if (frame.getFile() != null) {
						String strpath = frame.getDir() + Path.SEPARATOR + frame.getFile();
						int line = frame.getLine();
						try {
							ProfileUIUtils.openEditorAndSelect(strpath, line);
						} catch (PartInitException e) {
							e.printStackTrace();
						} catch (BadLocationException e) {
							e.printStackTrace();
						}						
					}
				}
				else {
					if (viewer.getExpandedState(element)) {
						viewer.collapseToLevel(element, TreeViewer.ALL_LEVELS);
					}
					else {
						viewer.expandToLevel(element, 1);
					}
				}
			}
		};
		viewer.addDoubleClickListener(doubleClickListener);
		
		expandAction = new ExpandAction(viewer);
		collapseAction = new CollapseAction(viewer);
		
		MenuManager manager = new MenuManager();
		manager.addMenuListener(new IMenuListener() {
			public void menuAboutToShow(IMenuManager manager) {
				ITreeSelection selection = (ITreeSelection) viewer.getSelection();
				ValgrindTreeElement element = (ValgrindTreeElement) selection.getFirstElement();
				if (contentProvider.hasChildren(element)) {
					manager.add(expandAction);
					manager.add(collapseAction);
				}
			}			
		});
		
		manager.setRemoveAllWhenShown(true);	
		Menu contextMenu = manager.createContextMenu(viewer.getTree());
		viewer.getControl().setMenu(contextMenu);
	}

	public void setErrors(ValgrindError[] errors) {
		this.errors = errors;
	}

	@Override
	public void setFocus() {
		viewer.getTree().setFocus();
	}

	public void refreshView() {
		if (errors != null) {
			RootTreeElement root = new RootTreeElement(errors);
			viewer.setInput(root);
		}
	}

	public TreeViewer getViewer() {
		return viewer;
	}

	public ValgrindError[] getErrors() {
		return errors;
	}

	public IAction[] getToolbarActions() {
		return null;
	}
	
	public IDoubleClickListener getDoubleClickListener() {
		return doubleClickListener;
	}

}
