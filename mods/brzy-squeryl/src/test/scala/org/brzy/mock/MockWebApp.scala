package org.brzy.mock

import org.brzy.application.WebApp
import org.brzy.config.webapp.WebAppConfig


class MockWebApp(config:WebAppConfig) extends WebApp(config) {
  override val services = List()
  override val controllers = List()
}