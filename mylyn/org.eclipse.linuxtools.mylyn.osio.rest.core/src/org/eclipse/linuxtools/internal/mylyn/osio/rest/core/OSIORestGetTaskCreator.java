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

import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;

import org.eclipse.mylyn.commons.repositories.http.core.CommonHttpClient;
import org.eclipse.mylyn.tasks.core.data.TaskAttribute;
import org.eclipse.mylyn.tasks.core.data.TaskAttributeMetaData;
import org.eclipse.mylyn.tasks.core.data.TaskData;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

@SuppressWarnings("restriction")
public class OSIORestGetTaskCreator extends OSIORestGetRequest<TaskAttribute> {
	
	private final TaskData taskData;
	private final String id;

	public OSIORestGetTaskCreator(CommonHttpClient client, TaskData taskData) {
		super(client, "/users/" + taskData.getRoot().getAttribute(OSIORestTaskSchema.getDefault().CREATOR_ID.getKey()).getValue(), null, true); //$NON-NLS-1$
		this.taskData = taskData;
		this.id = taskData.getRoot().getAttribute(OSIORestTaskSchema.getDefault().CREATOR_ID.getKey()).getValue();
	}

	// for testing purposes only
	public TaskAttribute testParseFromJson(InputStreamReader in) {
		return parseFromJson(in);
	}
	
	@Override
	protected TaskAttribute parseFromJson(InputStreamReader in) {
		TypeToken<ArrayList<TaskAttribute>> type = new TypeToken<ArrayList<TaskAttribute>>() {
		};
		return new GsonBuilder().registerTypeAdapter(type.getType(), new JSonTaskDataDeserializer())
				.create()
				.fromJson(in, type.getType());
	}

	OSIORestTaskSchema taskSchema = OSIORestTaskSchema.getDefault();

	private class JSonTaskDataDeserializer implements JsonDeserializer<TaskAttribute> {

		@Override
		public TaskAttribute deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
				throws JsonParseException {
			TaskAttribute creator = taskData.getRoot().getAttribute(taskSchema.CREATOR.getKey());
			TaskAttributeMetaData creatorMeta = creator.getMetaData();
			if (json.getAsJsonObject().get("data") == null) { //$NON-NLS-1$
				return creator;
			}
			JsonObject data = json.getAsJsonObject().get("data").getAsJsonObject(); //$NON-NLS-1$
			JsonObject attributes = data.getAsJsonObject().get("attributes").getAsJsonObject(); //$NON-NLS-1$
			String fullname = attributes.get("fullName").getAsString(); //$NON-NLS-1$
			creator.setValue(fullname);
			String email = attributes.get("email").getAsString(); //$NON-NLS-1$
			creatorMeta.putValue("email", email); //$NON-NLS-1$
			String username = attributes.get("username").getAsString(); //$NON-NLS-1$
			creatorMeta.putValue("username", username); //$NON-NLS-1$
			creatorMeta.putValue("id", id); //$NON-NLS-1$
			return creator;
		}

	}

}