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
package org.brzy.reflect

import org.scalatest.junit.JUnitSuite
import org.junit.Test
import org.junit.Assert._
import org.brzy.fab.reflect.Properties


class PropertiesTest extends JUnitSuite {

  @Test def testAccess = {
    import Properties._
    val simple = new SimpleFixture("bob")
    assertEquals("Test", simple.properties("name"))
    assertEquals(2, simple.properties.size)
  }
}

class SimpleFixture(val another:String) {
  val name = "Test"
}