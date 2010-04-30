package org.brzy.config

import reflect.BeanProperty
import java.io.File

/**
 * @author Michael Fortin
 * @version $Id: $
 */
class PluginConfig(file:File) extends MergeConfig[PluginConfig] {

  def this() = this(null)

  @BeanProperty var application:Application = _
  @BeanProperty var name:String = _
  @BeanProperty var implementation:String = _
  @BeanProperty var remote_location:String = _
  @BeanProperty var local_location:String = _
  @BeanProperty var version:String = _
  @BeanProperty var scan_package:String = _
  @BeanProperty var properties:java.util.HashMap[String,String] =_

  if(file != null) {
    // load from file
  }

  def +(that: PluginConfig) = {
    val proj = new PluginConfig
    proj.name = if(that.name != null) that.name else name
    proj.implementation = if(that.implementation != null) that.implementation else implementation
    proj.version = if(that.version != null) that.version else version
    proj.scan_package = if(that.scan_package != null) that.scan_package else scan_package
    proj.properties = if(that.properties != null) that.properties else properties
    proj
  }

  override def toString = {
    val newline = System.getProperty("line.separator")
    val sb = new StringBuilder()
    sb.append(newline)
    sb.append("  plugin")append(newline)
    sb.append("   - name").append("=").append(name).append(newline)
    sb.append("   - implementation").append("=").append(implementation).append(newline)
    sb.append("   - version").append("=").append(version).append(newline)
    sb.append("   - scan_package").append("=").append(scan_package).append(newline)
    sb.append("   - properties").append("=").append(properties).append(newline)
    sb.toString
  }
}