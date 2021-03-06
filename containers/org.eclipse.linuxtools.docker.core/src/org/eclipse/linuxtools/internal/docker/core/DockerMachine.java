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
package org.eclipse.linuxtools.internal.docker.core;

import org.eclipse.linuxtools.docker.core.DockerException;
import org.eclipse.linuxtools.internal.docker.core.ProcessLauncher.FluentProcessBuilder;

/**
 * Utility class to discover Docker machines using the 'docker-machine' command
 * line in a process.
 */
public class DockerMachine {

	private static DockerMachine instance = new DockerMachine();

	public static DockerMachine getInstance() {
		return instance;
	}

	private ProcessLauncher processLauncher = new ProcessLauncher();

	private DockerMachine() {

	}

	/**
	 * Replace the default {@link ProcessLauncher} with another instance. Used
	 * to testing by injecting a mock instance here.
	 * 
	 * @param processLauncher
	 *            the new {@link ProcessLauncher}.
	 */
	public void setProcessLauncher(final ProcessLauncher processLauncher) {
		this.processLauncher = processLauncher;
	}

	/**
	 * Checks that the given {@code dockerMachineInstallDir} contains the
	 * {@code docker-machine} command
	 * 
	 * @param dockerMachineInstallDir
	 *            the directory to check
	 * @return <code>true</code> if the system-specific command was found,
	 *         <code>false</code> otherwise.
	 */
	public boolean checkPathToDockerMachine(
			final String dockerMachineInstallDir) {
		return processLauncher.checkPathToCommand(dockerMachineInstallDir,
				getDockerMachineExecutableName());
	}

	/**
	 * @param pathToDockerMachine
	 *            the path to 'docker-machine' stored in the preferences
	 * @return the names of the existing Docker Machines
	 * @throws DockerException
	 *             if something went wrong
	 */
	public String[] getNames(final String pathToDockerMachine)
			throws DockerException {
		return processLauncher.processBuilder(pathToDockerMachine,
				getDockerMachineExecutableName(), new String[] { "ls", "-q" }) //$NON-NLS-1$ //$NON-NLS-2$
				.startAndGetResult();
	}

	/**
	 * @param name
	 *            the name of the Docker Machine to inspect
	 * @param dockerMachineInstallDir
	 *            the installation directory for Docker Machine
	 * @param vmDriverInstallDir
	 *            the installation directory for the underlying VM driver used
	 *            by Docker Machine
	 * @return the host URI to use to connect to it
	 * @throws DockerException
	 *             if something went wrong
	 */
	public String getHost(final String name,
			final String dockerMachineInstallDir,
			final String vmDriverInstallDir) throws DockerException {
		final String[] res = processLauncher
				.processBuilder(dockerMachineInstallDir,
						getDockerMachineExecutableName(),
						new String[] { "url", name }) //$NON-NLS-1$
				.extraPath(vmDriverInstallDir).startAndGetResult();
		return res.length == 1 ? res[0] : null;
	}

	/**
	 * @param name
	 *            the name of the Docker Machine to inspect
	 * @param pathToDockerMachine
	 *            the path to 'docker-machine' stored in the preferences
	 * @param vmDriverInstallDir
	 *            the installation directory for the underlying VM driver used
	 *            by Docker Machine
	 * @return the path to the directory containing the certificates
	 * @throws DockerException
	 *             if something went wrong
	 */
	public String getCertPath(final String name,
			final String pathToDockerMachine, final String vmDriverInstallDir)
			throws DockerException {
		final FluentProcessBuilder processBuilder = processLauncher
				.processBuilder(pathToDockerMachine,
						getDockerMachineExecutableName(),
						new String[] { "env", name }) //$NON-NLS-1$
				.extraPath(vmDriverInstallDir);
		final String[] envVariables = processBuilder.startAndGetResult();
		for (String envVariable : envVariables) {
			if (envVariable.contains("DOCKER_CERT_PATH")) { //$NON-NLS-1$
				// DOCKER_CERT_PATH="/path/to/cert-folder"
				return envVariable.split("=")[1].replace("\"", ""); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			}
		}
		return null;
	}



	/**
	 * @return the name of the Docker Machine executable, depending on the
	 *         current operating system.
	 */
	private static String getDockerMachineExecutableName() {
		if (SystemUtils.isWindows()) {
			return "docker-machine.exe"; //$NON-NLS-1$
		}
		return "docker-machine"; //$NON-NLS-1$
	}
}
