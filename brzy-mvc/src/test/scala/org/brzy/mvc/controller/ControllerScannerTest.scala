package org.brzy.mvc.controller

import org.junit.Test
import org.junit.Ignore
import org.junit.Assert._
import org.scalatest.junit.JUnitSuite


class ControllerScannerTest extends JUnitSuite {

  val scanner = new ControllerScanner("org.brzy.mvc.mock")

  @Test
  def testControllerScanner = {
    val result = scanner.controllers
    assertNotNull(result)
    assertEquals(2,result.size)
  }

}