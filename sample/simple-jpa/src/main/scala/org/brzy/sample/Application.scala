package org.brzy.sample

import org.brzy.config.Config
import org.brzy.application.WebApp
import org.brzy.interceptor.ProxyFactory._
import javax.persistence.Persistence
import org.brzy.persistence.scalaJpa.JpaInterceptor
import javax.persistence.Persistence
import org.brzy.interceptor.impl.LoggingInterceptor
/**
 * @author Michael Fortin
 * @version $Id: $
 */
class Application(config:Config) extends WebApp(config){

	val factory = Persistence.createEntityManagerFactory("brzy-unit")

	val personService:PersonService = make(classOf[PersonService],new JpaInterceptor(factory))
	
  override val services = Array(personService)
  
	override val controllers = Array(
		make(classOf[PersonController], new JpaInterceptor(factory)),
		make(classOf[HomeController],new JpaInterceptor(factory)))
}