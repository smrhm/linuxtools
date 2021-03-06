/*******************************************************************************
 * Copyright (c) 2017,2018 Red Hat, Inc.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributor:
 *     Red Hat, Inc. - initial API and implementation
 ******************************************************************************/

package org.eclipse.linuxtools.docker.integration.tests.ui;

import org.eclipse.linuxtools.docker.integration.tests.image.AbstractImageBotTest;
import org.eclipse.linuxtools.docker.integration.tests.mock.MockUtils;
import org.eclipse.linuxtools.docker.reddeer.core.ui.wizards.ImageRunSelectionPage;
import org.eclipse.linuxtools.docker.reddeer.ui.DockerContainersTab;
import org.eclipse.linuxtools.docker.reddeer.ui.DockerImagesTab;
import org.eclipse.reddeer.common.wait.WaitWhile;
import org.eclipse.reddeer.eclipse.ui.views.properties.PropertySheet;
import org.eclipse.reddeer.workbench.core.condition.JobIsRunning;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PropertiesViewTest extends AbstractImageBotTest {

	private static final String IMAGE_NAME = IMAGE_BUSYBOX;
	private static final String CONTAINER_NAME = "test_run_docker_busybox";

	@Before
	public void before() {
		deleteAllConnections();
		getConnection();
		pullImage(IMAGE_NAME);
	}

	@Test
	public void testContainerPropertiesTab() {
		DockerImagesTab imagesTab = openDockerImagesTab();
		imagesTab.runImage(IMAGE_NAME);
		ImageRunSelectionPage firstPage = new ImageRunSelectionPage(imagesTab);
		firstPage.setContainerName(CONTAINER_NAME);
		firstPage.finish();
		// new WaitWhile(new ContainerIsDeployedCondition(CONTAINER_NAME,
		// getConnection()));
		DockerContainersTab containerTab = new DockerContainersTab();
		containerTab.activate();
		containerTab.refresh();
		new WaitWhile(new JobIsRunning());
		if (mockitoIsUsed()) {
			MockUtils.runContainer(DEFAULT_CONNECTION_NAME, IMAGE_NAME, IMAGE_TAG_LATEST, CONTAINER_NAME);
		}
		getConnection();
		// open Properties view
		PropertySheet propertiesView = new PropertySheet();
		propertiesView.open();
		containerTab.select(CONTAINER_NAME);
		propertiesView.selectTab("Info");
	}

	@Test
	public void testImagePropertiesTab() {
		DockerImagesTab imagesTab = openDockerImagesTab();
		imagesTab.selectImage(IMAGE_NAME);
		// DockerExplorerView de = new DockerExplorerView();
		// de.open();
		// de.getDockerConnectionByName(getConnection().getName()).getImage(IMAGE_NAME).select();
		PropertySheet propertiesView = new PropertySheet();
		propertiesView.open();
		propertiesView.selectTab("Info");
	}

	@Override
	@After
	public void after() {
		deleteContainerIfExists(CONTAINER_NAME);
	}
}