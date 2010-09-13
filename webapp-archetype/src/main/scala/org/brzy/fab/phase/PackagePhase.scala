/*
 * Copyright 2010 Michael Fortin <mike@brzy.org>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");  you may not use this
 * file except in compliance with the License. You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed 
 * under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR 
 * CONDITIONS OF ANY KIND, either express or implied. See the License for the specific
 * language governing permissions and limitations under the License.
 */
package org.brzy.fab.phase


import org.brzy.fab.build.BuildContext
import org.brzy.fab.task.Task
import org.brzy.fab.print.Debug
import org.brzy.fab.file.FileUtils._
import org.brzy.config.webapp.WebAppConfig
import org.brzy.fab.shell.{WebXml, LogBackXml}

import org.brzy.fab.file.{Files, File, Jar}

/**
 *
 * @author Michael Fortin
 */
@Phase(name = "package", desc = "Clean Generated Artifacts", defaultTask = "pub-local", dependsOn = Array("test"))
class PackagePhase(ctx: BuildContext) {
  @Task(name = "pre-package", desc = "Prepare for Packaging")
  def prePackage = {
    ctx.line.say(Debug("pre-package"))
    File(ctx.webappDir, "WEB-INF/classes").mkdirs
    File(ctx.webappDir, "WEB-INF/lib").mkdirs
    makeLogbackXml
    makeWebXml
    copyModuleConfigs
    copyWebAppConfig
    copyLibs
    copyClasses
  }

  @Task(name = "package-task", desc = "Package artifacts", dependsOn = Array("pre-package"))
  def packageProject = {
    ctx.line.say(Debug("package-task"))
    val webAppConfig = ctx.properties("webAppConfig").asInstanceOf[WebAppConfig]
    val version = webAppConfig.application.version.get
    val artifact = webAppConfig.application.artifactId.get
    val destination = File(ctx.targetDir, artifact + "-" + version + ".jar")
    val source = ctx.webappDir
    Jar(source, destination, null)
    // do source, javadoc, scala doc?
  }

  @Task(name = "pub-local", desc = "Publish artifacts locally", dependsOn = Array("package-task"))
  def postPackage = {
    ctx.line.say(Debug("pub-local"))
    // do a local publish to the local maven repository
  }

  def makeLogbackXml = {
    ctx.line.say(Debug("makeLogbackXml"))
    val file = File(ctx.webappDir, "WEB-INF/classes/logback.xml")
    val config = ctx.properties("webAppConfig").asInstanceOf[WebAppConfig]
    val logBackXml = new LogBackXml(config)
    logBackXml.saveToFile(file.getAbsolutePath)

  }

  def makeWebXml = {
    ctx.line.say(Debug("makeWebXml"))
    val file = File(ctx.webappDir, "WEB-INF/web.xml")
    val config = ctx.properties("webAppConfig").asInstanceOf[WebAppConfig]
    val webXml = new WebXml(config)
    webXml.saveToFile(file.getAbsolutePath)
  }

  def copyModuleConfigs = {
    ctx.line.say(Debug("copyModuleResources"))
    val modConfigs = Files(".brzy/modules/*/brzy-module.b.yml")
    ctx.line.say(Debug("mod configs: " + modConfigs))
    modConfigs.foreach(config => {
      val modName = config.getParentFile.getName
      config.copyTo(File(ctx.webappDir, "WEB-INF/classes/brzy-modules/" + modName))
    })
  }

  def copyWebAppConfig = {
    ctx.line.say(Debug("copyWebAppConfig"))
    val config = File("brzy-webapp.b.yml")
    val destinationFolder = File(ctx.webappDir, "WEB-INF/classes")
    config.copyTo(destinationFolder)
  }

  def copyLibs = {
    ctx.line.say(Debug("copyLibs"))
    val libs = Files(".brzy/app/lib/compile/*.jar")
    val destination = File(ctx.webappDir, "WEB-INF/lib")
    libs.foreach(_.copyTo(destination))
  }

  def copyClasses = {
    ctx.line.say(Debug("copyClasses"))
    val classes = Files(ctx.targetDir, "classes/*")
    val destination = File(ctx.webappDir, "WEB-INF/classes")
    classes.foreach(_.copyTo(destination))
  }

  override def toString = "Packaging Phase"
}