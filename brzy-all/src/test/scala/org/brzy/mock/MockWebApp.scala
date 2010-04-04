package org.brzy.mock

import org.brzy.application.WebApp
import org.brzy.config.Config

/**
 * @author Michael Fortin
 * @version $Id: $
 */
class MockWebApp(config:Config) extends WebApp(config) {
  override val services = Array()
  override val controllers = Array()
}