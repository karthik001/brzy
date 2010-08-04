package org.brzy.reflect

import org.scalatest.junit.JUnitSuite
import java.beans.ConstructorProperties
import org.junit.Test
import org.junit.Assert._


class ConstructTest extends JUnitSuite {
  @Test def testCreateWithMap = {
    val fixture = Construct[Fixture](Map("x" -> "x", "z" -> "z", "y" -> "y"))
    assertNotNull(fixture)
    assertEquals("x", fixture.x)
    assertEquals("y", fixture.y)
    assertEquals("z", fixture.z)
  }

  @Test def testCreateWithArray = {
    val fixture = Construct[Fixture](Array("x","y","z"))
    assertNotNull(fixture)
    assertEquals("x", fixture.x)
    assertEquals("y", fixture.y)
    assertEquals("z", fixture.z)
  }

  @Test def createNoArg = {
    assertNotNull(Construct[Fixture2]())
  }
  @Test def createWithStr = {
    assertNotNull(Construct[Fixture]("org.brzy.reflect.Fixture",Array("x","y","z")))
  }
}


@ConstructorProperties(Array("x", "y", "z"))
case class Fixture(x: String, y: String, z: String)


class Fixture2()