package controllers

import play.api.libs.json.Json
import play.api.mvc._
import services.GoogleScrapper

class Application extends Controller {

  def listSubsidiaries(company: String) = Action { request =>
    val responsePayload = Map("subsidiaries" -> GoogleScrapper.listSubsidiaries(company))
    Ok(Json.toJson(responsePayload).toString())
  }
}
