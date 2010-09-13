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
package org.brzy.fab


import build.BuildContext
import phase.{DocPhase, DependencyPhase, CompilePhase, PackagePhase, PublishPhase, TestPhase, CleanPhase}
import print._
import file.File
import plugin.BuildPlugin

import org.brzy.config.ConfigFactory._
import org.brzy.config.webapp.WebAppConfig
import org.brzy.config.common.BootConfig
import org.brzy.config.mod.Mod

import collection.mutable.ListBuffer
import task.{SeqBuilder, TaskRunner}

/**
 *
 */
class WebAppArchetype(ctx: BuildContext, actions: Option[Array[String]], pluginClasses: List[Class[_]])
                     (implicit line: Conversation) {
  val defaultTask = "package"
  val configFile = File(System.getProperty("user.dir") + "/brzy-webapp.b.yml")

  if (!configFile.exists)
    error("No Configuration file at: " + configFile.getAbsolutePath)

  line.say(Debug("config: " + configFile.getAbsolutePath))
  //  val testBootConfig: BootConfig = makeBootConfig(configFile, "test")
  val buildBootConfig: BootConfig = makeBootConfig(configFile, ctx.environment)
  val webAppConfig: WebAppConfig = makeWebAppConfig(buildBootConfig)
  ctx.properties += "webAppConfig" -> webAppConfig


  // create phases & plugins
  val plugins = {
    val list = ListBuffer[BuildPlugin]()
    pluginClasses.foreach(pc => {
      val constructor = pc.getConstructor(Array(classOf[BuildContext]): _*)
      list += constructor.newInstance(ctx).asInstanceOf[BuildPlugin]
    })
    list.toList
  }

  val phases = List(
    new CleanPhase(ctx),
    new CompilePhase(ctx),
    new TestPhase(ctx),
    new PackagePhase(ctx),
    new DependencyPhase(ctx),
    new DocPhase(ctx),
    new PublishPhase(ctx))

  def build(args: Array[String]): Unit = {

    val tasksToRun = if (args.length == 0)
      Array(defaultTask)
    else
      args

    try {
      line.say(Info("build: " + tasksToRun.mkString))
      val runner = new TaskRunner(SeqBuilder(plugins, phases).taskList)
      runner.run(tasksToRun)
    }
    catch {
      case e: Exception => line.endWithError(e)
    }
  }

  def taskInfo(): Unit = {
    line.subject(Phase("list")) {
      line.subject(Task("tasks")) {
        line.say(Info("actions"))
        val tasks = SeqBuilder(plugins, phases)
        tasks.taskList.foreach(info => {
          line.say(Info(info.name + ": " + info.taskType + ": " + info.desc + ": " + info.seq,true))
        })
      }
    }
  }

  /**
   * TODO this doesn't belong here, it was copied from brzy-config.  It belongs there.
   */
  protected def makeWebAppConfig(bootConfig: BootConfig) = {
    val moduleBaseDir = File(".brzy/modules/")
    val view: Mod = bootConfig.views match {
      case Some(v) =>
        if (v != null) {
          val mod = bootConfig.views.get
          makeBuildTimeModule(mod, moduleBaseDir)
        }
        else
          null
      case _ => null
    }

    val persistence: List[Mod] = {
      if (bootConfig.persistence.isDefined)
        bootConfig.persistence.get.map(persist => {
          makeBuildTimeModule(persist, moduleBaseDir)
        })
      else
        Nil
    }
    val modules: List[Mod] = {
      if (bootConfig.modules.isDefined)
        bootConfig.modules.get.map(mod => {
          makeBuildTimeModule(mod, moduleBaseDir)
        })
      else
        Nil
    }
    new WebAppConfig(bootConfig, view, persistence, modules)
  }

}