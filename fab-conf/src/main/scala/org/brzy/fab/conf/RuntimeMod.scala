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
package org.brzy.fab.conf

/**
 * Document Me..
 * 
 * @author Michael Fortin
 */
class RuntimeMod(override val map:Map[String,AnyRef]) extends Mod(map) {
  val modName: Option[String] = map.get("mod_name") match {
    case Some(e) => e.asInstanceOf[Option[String]]
    case _ => map.get("name").asInstanceOf[Option[String]].orElse(Option("Unknown"))
  }

  val configClass: Option[String] = map.get("config_class").asInstanceOf[Option[String]].orElse(None)
  val resourceClass: Option[String] = map.get("resource_class").asInstanceOf[Option[String]].orElse(None)

  override def <<(it: BaseConf) = {
    if (it == null) {
      this
    }
    else {
      val that = it.asInstanceOf[RuntimeMod]
      new RuntimeMod(Map[String, AnyRef](
        "mod_name" -> that.modName.getOrElse(this.modName.getOrElse(null)),
        "config_class" -> that.configClass.getOrElse(this.configClass.getOrElse(null)),
        "resource_class" -> that.resourceClass.getOrElse(this.resourceClass.getOrElse(null))
      ) ++ super.<<(that).map )
    }
  }
}