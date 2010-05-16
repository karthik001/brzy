package org.brzy.config

import org.apache.commons.lang.builder.{CompareToBuilder, HashCodeBuilder, EqualsBuilder}
import java.util.{List=>JList}
import collection.JavaConversions._

/**
 * @author Michael Fortin
 * @version $Id: $
 */
class Logging(m:Map[String,AnyRef]) extends Config(m) with MergeConfig[Logging]{


  val provider:String = set[String](m.get("version"))
  val appenders:Seq[Appender] = makeSeq[Appender](classOf[Appender],m.get("appenders"))
  val loggers:Seq[Logger] = makeSeq[Logger](classOf[Logger],m.get("loggers"))
  val root:Root = make(classOf[Root],m.get("root"))

  val configurationName = "Logging"

  def asMap = {
    val map = Map[String,AnyRef]()
    // TODO add each property
    map
  }


  def +(that: Logging) =  new Logging(this.asMap ++ that.asMap)

}

/**
 *
 */
class Appender(m:Map[String,AnyRef]) extends Config(m) with MergeConfig[Appender] with Comparable[Appender] {

  val name:String = set[String](m.get("name"))
  val appenderClass:String = set[String](m.get("appender_class"))
  val layout:String = set[String](m.get("layout"))
  val pattern:String = set[String](m.get("pattern"))

  val file:String = set[String](m.get("file"))
  val rollingPolicy:String = set[String](m.get("rolling_policy"))
  val fileNamePattern:String = set[String](m.get("file_name_pattern"))

  val configurationName = "Appender"

  def asMap = {
    val map = Map[String,AnyRef]()
    // TODO add each property
    map
  }

  def +(that: Appender) =  new Appender(this.asMap ++ that.asMap)

  override def equals(obj: Any) = {
    if (obj == null)
      false
    else if(obj == this)
      true
    else {
      val rhs = obj.asInstanceOf[Appender]
      new EqualsBuilder()
        .append(name, rhs.name)
        .isEquals
    }
  }

  def compareTo(that: Appender) = {
    new CompareToBuilder()
      .append(this.name, that.name)
      .toComparison
  }

  override def hashCode = {
    new HashCodeBuilder(17, 37)
      .append(name)
      .toHashCode
  }
}

/**
 *
 */
class Logger(m:Map[String,AnyRef]) extends Config(m) with MergeConfig[Logger] with Comparable[Logger]{
  val name:String = set[String](m.get("name"))
  val level:String = set[String](m.get("level"))

  val configurationName = "Logger"

  def asMap = {
    val map = Map[String,AnyRef]()
    // TODO add each property
    map
  }

  def +(that: Logger) =  new Logger(this.asMap ++ that.asMap)

  def compareTo(obj: Logger) = {
    new CompareToBuilder()
      .append(this.name, obj.name)
      .toComparison
  }

  override def equals(obj: Any) = {
    if (obj == null)
      false
    else if(obj == this)
      true
    else {
      val rhs = obj.asInstanceOf[Appender]
      new EqualsBuilder()
        .append(name, rhs.name)
        .isEquals
    }
  }

  override def hashCode = {
    new HashCodeBuilder(11, 37)
      .append(name)
      .toHashCode
  }
}

/**
 *
 */
class Root(m:Map[String,AnyRef]) extends Config(m) with MergeConfig[Root] {
  val level:String = set[String](m.get("level"))
  val ref:Seq[String] = m.get("ref") match {
    case s:Some[JList[String]] => s.get.toSeq 
    case _ => null
  }

  val configurationName = "Root"

  def asMap = {
    val map = Map[String,AnyRef]()
    // TODO add each property
    map
  }

  def +(that: Root) =  new Root(this.asMap ++ that.asMap)
}