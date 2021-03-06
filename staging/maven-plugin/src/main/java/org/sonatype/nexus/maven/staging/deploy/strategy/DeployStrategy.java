/*
 * Sonatype Nexus (TM) Open Source Version
 * Copyright (c) 2007-2013 Sonatype, Inc.
 * All rights reserved. Includes the third-party code listed at http://links.sonatype.com/products/nexus/oss/attributions.
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License Version 1.0,
 * which accompanies this distribution and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Sonatype Nexus (TM) Professional Version is available from Sonatype, Inc. "Sonatype" and "Sonatype Nexus" are trademarks
 * of Sonatype, Inc. Apache Maven is a trademark of the Apache Software Foundation. M2eclipse is a trademark of the
 * Eclipse Foundation. All other trademarks are the property of their respective owners.
 */

package org.sonatype.nexus.maven.staging.deploy.strategy;

import org.apache.maven.artifact.deployer.ArtifactDeploymentException;
import org.apache.maven.artifact.installer.ArtifactInstallationException;
import org.apache.maven.plugin.MojoExecutionException;

/**
 * Deploy strategy encapsulates the actual deploy method.
 *
 * @author cstamas
 * @since 1.1
 */
public interface DeployStrategy
{
  /**
   * To be invoked at every module's deploy phase (or where deploy mojo is bound), hence, is invoked multiple times
   * (as many times as many modules are built in reactor). Depending on strategy, this method might do nothing, might
   * remotely deploy or locally stage.
   * <p/>
   * Parallel build note: this method is invoked multiple times in parallel builds!
   */
  void deployPerModule(final DeployPerModuleRequest request)
      throws ArtifactInstallationException, ArtifactDeploymentException, MojoExecutionException;

  /**
   * Method to be invoked once at the very end of the reactor build (last module built having the
   * nexus-staging-maven-plugin defined to run in reactor), hence this method is about to be invoked only once.
   * Before calling this method, all the needed {@link #deployPerModule(DeployPerModuleRequest)} calls are made.
   * Depending on strategy, this method might do remote deploys or just nothing (as in direct deploy case).
   * <p/>
   * Parallel build not: this method is invoked only once (from "topologically last" module), even in parallel build
   * (ensured by DeployMojo).
   */
  void finalizeDeploy(final FinalizeDeployRequest request)
      throws ArtifactDeploymentException, MojoExecutionException;
}
