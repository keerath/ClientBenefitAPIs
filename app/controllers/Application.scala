package controllers

import play.api.libs.json.Json
import play.api.mvc._
import services.GoogleScrapper

class Application extends Controller {

  def listSubsidiaries(company: String) = Action {
    Ok(Json.toJson(Map("subsidiaries" -> GoogleScrapper.listSubsidiaries(company))))
  }
}
