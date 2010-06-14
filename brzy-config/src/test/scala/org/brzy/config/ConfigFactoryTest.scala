package org.brzy.config

import org.scalatest.junit.JUnitSuite
import org.junit.Assert._
import org.junit.Test
import org.junit.Before
import org.brzy.webapp.ConfigFactory
import java.io.File
import org.brzy.config.plugin.Plugin
import org.brzy.util.FileUtils._

/**
 * Document Me..
 *
 * @author Michael Fortin
 * @version $Id : $
 */
class ConfigFactoryTest extends JUnitSuite {

  @Before
  def initialize = {

  }

  @Test
  def testRuntimeConfig = {
    val url = new File(getClass.getClassLoader.getResource("brzy-webapp.b.yml").getFile)
    val config = ConfigFactory.makeBootConfig(url, "development")

    assertNotNull(config)
    assertNotNull(config.application.get.applicationClass.get)
    assertNotNull("org.brzy.sample.WebApp", config.application.get.applicationClass.get)
    assertNotNull(config.application.get.version.get)
    assertNotNull(config.application.get.name.get)
    assertEquals("Test app", config.application.get.name.get)
    assertNotNull(config.application.get.author.get)
    assertNotNull(config.application.get.description.get)
    assertNotNull(config.application.get.org.get)
    assertNotNull(config.application.get.artifactId.get)
    assertNotNull(config.application.get.webappContext.get)
    assertNotNull(config.dependencies.get)
    assertNotNull(config.persistence.get)
    assertEquals(1, config.persistence.get.size)
    assertNotNull(config.logging.get)
    assertNotNull(config.logging.get.appenders.get)
    assertEquals(2, config.logging.get.appenders.get.size)
    assertNotNull(config.dependencies.get)

    assertNotNull(config.plugins.get)
    assertEquals(1, config.plugins.get.size)

    assertEquals( 15, config.dependencies.get.size)

    assertEquals( 12, config.webXml.get.size)

  }


  @Test
  def testDownload() = {
    val workDir = new File(new File(System.getProperty("java.io.tmpdir")), "junitwork")

    if (workDir.exists)
      workDir.trash()

    workDir.mkdirs
    assertTrue(workDir.exists)

    val map = Map[String, AnyRef](
      "name" -> "brzy-scalate",
      "org" -> "org.brzy",
      "version" -> "0.1",
      "remote_location" -> "/Users/m410/Projects/brzy/brzy-config/src/test/resources/brzy-test-plugin.zip")
    val plugin = new Plugin(map)
    assertNotNull(plugin)
    assertEquals(0, workDir.listFiles.length)
    ConfigFactory.installPlugin( workDir, plugin)
    assertEquals(1, workDir.listFiles.length)
  }
}