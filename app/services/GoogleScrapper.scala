package services

import com.gargoylesoftware.htmlunit.WebClient
import com.gargoylesoftware.htmlunit.html.{HtmlDivision, HtmlOrderedList, HtmlPage, HtmlSpan}

import scala.collection.JavaConversions._

/**
  * Created by keerath on 27/11/17.
  */
object GoogleScrapper {

  val webClientWithJSAndCSS = new WebClient()
  webClientWithJSAndCSS.getOptions.setJavaScriptEnabled(true)
  webClientWithJSAndCSS.getOptions.setThrowExceptionOnScriptError(false)
  webClientWithJSAndCSS.getOptions.setCssEnabled(true)

  val webClientWithoutJSAndCSS = new WebClient()
  webClientWithoutJSAndCSS.getOptions.setJavaScriptEnabled(false)
  webClientWithoutJSAndCSS.getOptions.setCssEnabled(false)


  def listSubsidiaries(company: String): List[String] = {
    val subsidiariesWithoutImage = listSubsidiariesWithoutImage(company).distinct
    if (subsidiariesWithoutImage.isEmpty) {
      listSubsidiariesWithImage(company).distinct
    } else {
      subsidiariesWithoutImage
    }
  }

  private def listSubsidiariesWithoutImage(company: String): List[String] = {
    val subsidiaryPage: HtmlPage = webClientWithoutJSAndCSS
      .getPage(s"https://www.google.co.in/search?hl=en-IN&url=hp&biw=&bih=&q=$company subsidiaries&btnG=Google+Search&gbv=2&oq=&gs_l=")

    subsidiaryPage.getElementById("ires").getFirstChild.asInstanceOf[HtmlOrderedList]
      .getFirstChild.getFirstChild.getFirstChild.getChildren.flatMap {
      case span: HtmlSpan => Some(span.getTextContent.stripSuffix(",").stripPrefix(","))
      case _ => None
    }.toList
  }

  private def listSubsidiariesWithImage(company: String): List[String] = {
    val subsidiaryPage: HtmlPage = webClientWithJSAndCSS
      .getPage(s"https://www.google.co.in/search?hl=en-IN&url=hp&biw=&bih=&q=$company subsidiaries&btnG=Google+Search&gbv=2&oq=&gs_l=")

    subsidiaryPage.getByXPath[HtmlDivision]("//div[@class='kltat']").map(x => x.getChildren.flatMap {
      case span: HtmlSpan => Some(span.getTextContent)
      case _ => None
    }.mkString(" ")).toList
  }
}
