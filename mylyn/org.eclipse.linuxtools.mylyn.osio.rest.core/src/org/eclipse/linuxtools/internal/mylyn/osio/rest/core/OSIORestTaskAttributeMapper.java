/*******************************************************************************
 * Copyright (c) 2015, 2018 Frank Becker and others.
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

package org.eclipse.linuxtools.internal.mylyn.osio.rest.core;

import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.linuxtools.internal.mylyn.osio.rest.core.response.data.IdNamed;
import org.eclipse.linuxtools.internal.mylyn.osio.rest.core.response.data.Space;
import org.eclipse.mylyn.commons.core.StatusHandler;
import org.eclipse.mylyn.tasks.core.RepositoryStatus;
import org.eclipse.mylyn.tasks.core.TaskRepository;
import org.eclipse.mylyn.tasks.core.data.TaskAttribute;
import org.eclipse.mylyn.tasks.core.data.TaskAttributeMapper;

public class OSIORestTaskAttributeMapper extends TaskAttributeMapper {

	private final OSIORestConnector connector;

	public OSIORestTaskAttributeMapper(TaskRepository taskRepository, OSIORestConnector connector) {
		super(taskRepository);
		this.connector = connector;
	}

	@Override
	public Map<String, String> getOptions(@NonNull TaskAttribute attribute) {
		OSIORestTaskSchema taskSchema = OSIORestTaskSchema.getDefault();
		if (attribute.getId().equals(taskSchema.WORKITEM_TYPE.getKey())
				|| attribute.getId().equals(taskSchema.AREA.getKey())
				|| attribute.getId().equals(taskSchema.ASSIGNEES.getKey())
				|| attribute.getId().equals(taskSchema.STATUS.getKey())
				|| attribute.getId().equals(taskSchema.ITERATION.getKey())) {
			TaskAttribute spaceIdAttribute = attribute.getParentAttribute()
					.getAttribute(OSIORestTaskSchema.getDefault().SPACE_ID.getKey());
			TaskAttribute spaceAttribute = attribute.getParentAttribute()
					.getAttribute(OSIORestCreateTaskSchema.getDefault().SPACE.getKey());
			OSIORestConfiguration repositoryConfiguration;
			try {
				repositoryConfiguration = connector.getRepositoryConfiguration(this.getTaskRepository());
				// TODO: change this when we have offline cache for the repository configuration so we build the options in an temp var
				if (repositoryConfiguration != null) {
					if (spaceIdAttribute != null && !spaceIdAttribute.getValue().equals("")) { //$NON-NLS-1$
						attribute.clearOptions();
						for (String spaceId : spaceIdAttribute.getValues()) {
							Space actualSpace = connector.getClient(getTaskRepository()).getSpaceById(spaceId, getTaskRepository());
							internalSetAttributeOptions4Space(attribute, actualSpace.getMapFor(attribute.getId()));
						}
					} else {
						attribute.clearOptions();
						for (String spaceName : spaceAttribute.getValues()) {
							Space actualSpace = repositoryConfiguration.getSpaceWithName(spaceName);
							internalSetAttributeOptions4Space(attribute, actualSpace.getMapFor(attribute.getId()));
						}
						if (attribute.getOptions().size() == 0) {
							if (attribute.getId().equals(taskSchema.ASSIGNEES.getKey())) {
								String userName = repositoryConfiguration.getUserName();
								attribute.putOption(userName, userName);
							}
						}
					}
				}
			} catch (CoreException e) {
				StatusHandler.log(new RepositoryStatus(getTaskRepository(), IStatus.ERROR, OSIORestCore.ID_PLUGIN,
						0, "Failed to obtain repository configuration", e)); //$NON-NLS-1$
			}
		}
		return super.getOptions(attribute);
	}

	private void internalSetAttributeOptions4Space(TaskAttribute taskAttribute,
			Map<String, IdNamed> optionMap) {
		boolean found = false;
		String actualValue = taskAttribute.getValue();
		for (IdNamed entry : optionMap.values()) {
			taskAttribute.putOption(entry.getName(), entry.getName());
			found |= actualValue.equals(entry.getName());
		}
		if (!found) {
			taskAttribute.setValue(""); //$NON-NLS-1$
		}
	}

	@Override
	public String mapToRepositoryKey(@NonNull TaskAttribute parent, @NonNull String key) {
		if (key.equals(TaskAttribute.TASK_KEY)) {
			return OSIORestTaskSchema.getDefault().ID.getKey();
		} else if (key.equals(TaskAttribute.STATUS)) {
			return OSIORestTaskSchema.getDefault().STATUS.getKey();
		}
		else {
			return super.mapToRepositoryKey(parent, key);
		}
	}

}