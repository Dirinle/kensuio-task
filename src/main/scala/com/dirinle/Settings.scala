package com.dirinle

import com.typesafe.config.ConfigFactory

object Settings {
  private val conf = ConfigFactory.load()
  val port = conf.getInt("port")
  val interface = conf.getString("interface")

}
