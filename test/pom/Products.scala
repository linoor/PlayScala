package pom

import play.api.test.TestBrowser

/**
  * Created by linoor on 6/7/16.
  */
trait Products {
  def getProductsCount(browser :TestBrowser): Int = {
    browser.find(".item").size()
  }
}
