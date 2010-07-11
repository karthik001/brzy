package org.brzy.d3

import org.squeryl.PrimitiveTypeMode._
import org.brzy.action.args._
import org.brzy.action.returns._
import Person._
import org.brzy.controller.{Controller,Path}

/**
 * @author Michael Fortin
 * @version $Id: $
 */
@Controller("/persons")
class PersonController {

	@Path("make")  def make = {
		Person.create
		Redirect("/persons")
	}
	
  @Path("") 
	def list = "personsList"->from(persons)(a=> select(a)).toList

  @Path("{id}") 
	def show(params:Parameters) = "person"->Person.get(params("id")(0).toLong)

  @Path("create") 
	def create() = "person"->new Person(0,"first","last")

  @Path("save") 
	def save(p:Parameters) = {
    val person = new Person(0, p("firstName")(0), p("lastName")(0))
		person.save()
    (Model("person"->person), Redirect("/persons/" + person.id))
  }

  @Path("{id}/edit") 
	def edit(params:Parameters) = "person"->Person.get(params("id")(0).toLong)

  @Path("{id}/update") 
	def update(p:Parameters) = {
		val person = new Person(p("id")(0).toLong, p("firstName")(0), p("lastName")(0))
		person.update()
    (Model("person"->person), Redirect("/persons/" + person.id))
  }
}