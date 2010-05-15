package org.brzy.util

import org.brzy.mock.User
import org.junit.Test
import org.junit.Assert._
import ParameterConversion._

/**
 * @author Michael Fortin
 * @version $Id: $
 */
class ParameterConversionTest {

  @Test
  def testToType = {
    assertEquals("SomeVal",toType(classOf[String],"SomeVal"))
    assertEquals(100L,toType(classOf[java.lang.Long],"100"))
  }
}