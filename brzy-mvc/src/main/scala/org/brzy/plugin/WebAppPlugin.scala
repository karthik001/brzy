package org.brzy.plugin

import java.io.File

/**
 * @author Michael Fortin
 * @version $Id: $
 */
trait WebAppPlugin {

  def build(webappBase:File):Unit = {}

  def services:List[AnyRef] = Nil

  def interceptors:List[AnyRef] = Nil

  def controllers:List[AnyRef] = Nil

  def startup = {}

  def shutdown = {}
}