package org.brzy.config

import reflect.BeanProperty

/**
 * @author Michael Fortin
 * @version $Id: $
 */
class Project extends MergeConfig[Project]{
  @BeanProperty var scala_version:String = _
  @BeanProperty var ant_version:String = _
  @BeanProperty var ivy_version:String = _
  @BeanProperty var package_type:String = _
  @BeanProperty var plugin_resources:String = _
  @BeanProperty var plugin_repository:String = _

  def +(that: Project) = {
    val proj = new Project

    if(that == null) {
      proj.scala_version = scala_version
      proj.ant_version = ant_version
      proj.ivy_version = ivy_version
      proj.package_type = package_type 
      proj.plugin_resources = plugin_resources
      proj.plugin_repository = plugin_repository 
    }
    else {
      proj.scala_version = if(that.scala_version != null) that.scala_version else scala_version
      proj.ant_version = if(that.ant_version != null) that.ant_version else ant_version
      proj.ivy_version = if(that.ivy_version != null) that.ivy_version else ivy_version
      proj.package_type = if(that.package_type != null) that.package_type else package_type
      proj.plugin_resources = if(that.plugin_resources != null) that.plugin_resources else plugin_resources
      proj.plugin_repository = if(that.plugin_repository != null) that.plugin_repository else plugin_repository
    }
    proj
  }

//  override def toString = {
//    val newline = System.getProperty("line.separator")
//    val sb = new StringBuilder()
//    sb.append(newline)
//    sb.append("  - project")append(newline)
//    sb.append("   - scala_version").append("=").append(scala_version).append(newline)
//    sb.append("   - ant_version  ").append("=").append(ant_version).append(newline)
//    sb.append("   - ivy_version  ").append("=").append(ivy_version).append(newline)
//    sb.append("   - package_type ").append("=").append(package_type).append(newline)
//    sb.append("   - plugin_resources ").append("=").append(plugin_resources).append(newline)
//    sb.toString
//  }
}