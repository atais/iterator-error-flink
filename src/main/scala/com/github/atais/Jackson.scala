package com.github.atais

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.{DefaultScalaModule, ScalaObjectMapper}

object Jackson {

  lazy val mapper: ObjectMapper with ScalaObjectMapper =
    new ObjectMapper() with ScalaObjectMapper {}
  mapper.registerModule(DefaultScalaModule)

}
