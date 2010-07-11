package org.brzy.security

import org.brzy.config.mod.Mod
import org.brzy.config.webapp.WebXml

/**
 * Defines the Security configuration.
 *
 * @author Michael Fortin
 */
class SecurityModConfig(map: Map[String, AnyRef]) extends Mod(map) with WebXml {
  override val configurationName = "Security Configuration"
  val authorityEntity: Option[String] = map.get("authority_entity").asInstanceOf[Option[String]].orElse(None)
  val userEntity: Option[String] = map.get("user_entity").asInstanceOf[Option[String]].orElse(None)
  val userEntityName: Option[String] = map.get("user_entity_name").asInstanceOf[Option[String]].orElse(None)
  val userEntityPass: Option[String] = map.get("user_entity_pass").asInstanceOf[Option[String]].orElse(None)
  val algorithm: Option[String] = map.get("algorithm").asInstanceOf[Option[String]].orElse(None)
  val defaultPath: Option[String] = map.get("default_path").asInstanceOf[Option[String]].orElse(None)
  val authorityField: Option[String] = map.get("authority_field").asInstanceOf[Option[String]].orElse(None)

  override val webXml: Option[List[Map[String, AnyRef]]] = map.get("web_xml").asInstanceOf[Option[List[Map[String, AnyRef]]]].orElse(None)

  override def <<(that: Mod): Mod = {
    if (that == null) {
      this
    }
    else if (that.isInstanceOf[SecurityModConfig]) {
      val it = that.asInstanceOf[SecurityModConfig]
      new SecurityModConfig(Map[String, AnyRef](
        "authority_entity" -> it.authorityEntity.getOrElse(this.authorityEntity.getOrElse(null)),
        "user_entity" -> it.userEntity.getOrElse(this.userEntity.getOrElse(null)),
        "user_entity_name" -> it.userEntityName.getOrElse(this.userEntityName.getOrElse(null)),
        "user_entity_pass" -> it.userEntityPass.getOrElse(this.userEntityPass.getOrElse(null)),
        "algorithm" -> it.algorithm.getOrElse(this.algorithm.getOrElse(null)),
        "default_path" -> it.defaultPath.getOrElse(this.defaultPath.getOrElse(null)),
        "authority_field" -> it.authorityField.getOrElse(this.authorityField.getOrElse(null)),
        "web_xml" -> {
          if (this.webXml.isDefined && it.webXml.isDefined &&
                  this.webXml.get != null && it.webXml.get != null)
            this.webXml.get ++ it.webXml.get
          else if (this.webXml.isDefined)
            this.webXml.get
          else if (it.webXml.isDefined)
            it.webXml.get
          else
            null
        }) ++ super.<<(that).asMap)
    }
    else {
      new SecurityModConfig(Map[String, AnyRef](
        "authority_entity" -> that.map.get("authority_entity").getOrElse(this.authorityEntity.getOrElse(null)),
        "user_entity" -> that.map.get("user_entity").getOrElse(this.userEntity.getOrElse(null)),
        "user_entity_name" -> that.map.get("user_entity_name").getOrElse(this.userEntityName.getOrElse(null)),
        "user_entity_pass" -> that.map.get("user_entity_pass").getOrElse(this.userEntityPass.getOrElse(null)),
        "algorithm" -> that.map.get("algorithm").getOrElse(this.algorithm.getOrElse(null)),
        "default_path" -> that.map.get("default_path").getOrElse(this.defaultPath.getOrElse(null)),
        "authority_field" -> that.map.get("authority_field").getOrElse(this.authorityField.getOrElse(null)),
        "web_xml" -> {
          if (this.webXml.isDefined && this.webXml.get != null &&
                  that.map.get("web_xml").isDefined && that.map.get("web_xml").get != null)
            this.webXml.get ++ that.map.get("web_xml").get.asInstanceOf[List[_]]
          else if (this.webXml.isDefined)
            this.webXml.get.asInstanceOf[List[_]]
          else if (that.map.get("web_xml").isDefined)
            that.map.get("web_xml").get
          else
            null
        }) ++ super.<<(that).asMap)
    }
  }

  override def asMap: Map[String, AnyRef] = map


}