package org.brzy.mock

import org.squeryl.PrimitiveTypeMode._
import org.squeryl.annotations.Column
import org.brzy.action.args.Parameters
import org.brzy.validator.Validation
import javax.validation.{ConstraintViolation,Validation=>jValidation,Validator,ValidatorFactory}
import org.slf4j.{LoggerFactory, Logger}
import org.squeryl.{KeyedEntity, Schema}

/**
 * @author Michael Fortin
 * @version $Id: $
 */
class Person( val id:Long,
    @Column(name="first_name") val firstName:String,
    @Column(name="last_name") val lastName:String)
    extends KeyedEntity[Long] {

  def this() = this(0, "","")
}


object Person extends Schema {
  val log:Logger = LoggerFactory.getLogger(classOf[Person])
  val persons = table[Person]
	
	class EntityCrudOps(t:Person) {

    def validity() ={
      log.trace("validity")
			val factory = jValidation.buildDefaultValidatorFactory
			val validator = factory.getValidator
			val constraintViolations = validator.validate(t);
      new Validation()
    }

    def save() = {
      log.trace("save")
			persons.insert(t)
    }

		def update():Unit = {
      log.trace("save")
			persons.update(t)
    }

    def delete() = {
      log.trace("delete")
			persons.deleteWhere(db => db.id === t.id)
    }
  }

  implicit def applyCrudOps(t:Person) = new EntityCrudOps(t)

  def get(id:Long) = from(persons)(s => where(s.id === id) select(s)).head
	def make(params:Parameters):Person ={
		val person = classOf[Person].newInstance
		person
	}
}