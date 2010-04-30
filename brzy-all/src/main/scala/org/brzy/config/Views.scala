package org.brzy.config

import reflect.BeanProperty

/**
 * @author Michael Fortin
 * @version $Id: $
 */
class Views extends MergeConfig[Views]{
  @BeanProperty var implementation:String = _
  @BeanProperty var html_version:String = _


  def +(that: Views) = {
    val view = new Views
    view.implementation = if(that.implementation != null) that.implementation else implementation
    view.html_version = if(that.html_version != null) that.html_version else html_version
    view
  }

  override def toString = {
    val newline = System.getProperty("line.separator")
    val sb = new StringBuilder()
    sb.append("  - application").append(newline)
    sb.append("   - implementation").append("=").append(implementation).append(newline)
    sb.append("   - html_version").append("=").append(html_version).append(newline)
    sb.toString
  }
}