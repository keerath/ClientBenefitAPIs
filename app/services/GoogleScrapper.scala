package services

import com.gargoylesoftware.htmlunit.WebClient
import com.gargoylesoftware.htmlunit.html.{HtmlOrderedList, HtmlPage, HtmlSpan}

import scala.collection.JavaConversions._

/**
  * Created by keerath on 27/11/17.
  */
object GoogleScrapper extends App {

  def listSubsidiaries(company: String): List[String] = {
    val webClient = new WebClient()
    webClient.getOptions.setJavaScriptEnabled(false)
    webClient.getOptions.setCssEnabled(false)

    val subsidiaryPage: HtmlPage = webClient.getPage(s"https://www.google.co.in/search?hl=en-IN&url=hp&biw=&bih=&q=$company subsidiaries&btnG=Google+Search&gbv=2&oq=&gs_l=")

    subsidiaryPage.getElementById("ires").getFirstChild.asInstanceOf[HtmlOrderedList]
      .getFirstChild.getFirstChild.getFirstChild.getChildren.flatMap {
      case span: HtmlSpan => Some(span.getTextContent.stripSuffix(",").stripPrefix(","))
      case _ => None
    }.toList
  }

  println(listSubsidiaries("hyperion insurance group"))
}
