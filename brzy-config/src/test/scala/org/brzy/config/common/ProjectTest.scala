package org.brzy.config.common

import org.junit.Test
import org.junit.Assert._
import org.scalatest.junit.JUnitSuite

/**
 * Document Me..
 * 
 * @author Michael Fortin
 * @version $Id: $
 */
class ProjectTest extends JUnitSuite {

  @Test
  def testMerge = {
    val map1 = Map[String,String](
    "scala_version" -> "one",
      "sbt_version" -> "three",
      "package_type" -> "four",
      "plugin_resources" -> "five",
      "plugin_repository" -> "six"
      )
    val project = new Project(map1)
    assertEquals("one",project.scalaVersion.get)
    assertEquals("three",project.sbtVersion.get)
    assertEquals("four",project.packageType.get)
    assertEquals("five",project.pluginResources.get)
    assertEquals("six",project.pluginRepository.get)

    val map2 = Map[String,String](
    "scala_version" -> "seven",
      "sbt_version" -> "nine",
      "package_type" -> "ten",
      "plugin_resources" -> "eleven",
      "plugin_repository" -> "twelve")
    val project1 = new Project(map2)
    val merge = project << project1
    assertNotNull(merge)
    assertEquals("seven",merge.scalaVersion.get)
    assertEquals("nine",merge.sbtVersion.get)
    assertEquals("ten",merge.packageType.get)
    assertEquals("eleven",merge.pluginResources.get)
    assertEquals("twelve",merge.pluginRepository.get)
  }
}