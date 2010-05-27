package org.brzy.build

import xml.transform.RuleTransformer
import xml._
import org.brzy.config.{Appender, WebappConfig}
import collection.mutable.{ArrayBuffer, ListBuffer}
import java.lang.String
import collection.immutable.List

/**
 * @author Michael Fortin
 * @version $Id: $
 */
class LogBackXml(config:WebappConfig) {
  private val parentName = "configuration"
  private val template = XML.load(getClass.getClassLoader.getResource("template.logback.xml"))
  private val children = ListBuffer[Elem]()

  if(config.logging.get.appenders.isDefined)
    config.logging.get.appenders.get.foreach( dep => {
      children += appenders(dep)
    })

  if(config.logging.get.loggers.isDefined)
    config.logging.get.loggers.get.foreach( l => {
      children += <logger name={l.name.get} level={l.level.get} />
    })

  if(config.logging.get.root.isDefined)
    children += {
      val level: String = config.logging.get.root.get.level.get
      val refs: List[String] = config.logging.get.root.get.ref.get
      Elem(null,"root", Attribute(null,"level",level,Null), TopScope, appenderRefs(refs):_*)
    }

// <root level={config.logging.root.level}>
//    {for(ref <- config.logging.root.appender-ref)}
//      <appender-ref name={ref} />
//  </root>
  
  val body = new RuleTransformer(new AddChildrenTo(parentName, children)).transform(template).head

  def appenders(appender:Appender):Elem = {
    val arrayBuf = ArrayBuffer[Elem]()

    if(appender.file.isDefined)
      arrayBuf += <File>{appender.file.get}</File>

    if(appender.rollingPolicy.isDefined)
      arrayBuf += <rollingPolicy class={appender.rollingPolicy.get}>
        <FileNamePattern>{appender.fileNamePattern.get}</FileNamePattern>
      </rollingPolicy>

    if(appender.layout != null)
      arrayBuf += <encoder class={appender.layout.get}>
        <pattern>{appender.pattern.get}</pattern>
      </encoder>

//    <appender name={appender.name} class={appender.appender_class}>
//      {arrayBuf.toArray}
//    </appender>
    val name: String = appender.name.get
    val apClass: String = appender.appenderClass.get
    val attr: Attribute = Attribute(null, "class", apClass, Null)
    Elem(null,"appender", Attribute(null,"name",name, attr), TopScope, arrayBuf.toArray:_*)
  }
  def appenderRefs(root:Seq[String]):Array[Node] = {
    if(root != null)
      root.map(x => <appender-ref ref={x} />).toArray
    else
      Array.empty
  }
}