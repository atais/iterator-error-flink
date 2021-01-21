package com.github.atais

import com.fasterxml.jackson.databind.JsonNode
import com.github.atais.Jackson.mapper

import scala.collection.JavaConverters._

object ListModel {

  case class Root(items: List[Data])

  case class Data(data: List[Double])

  private def toRoot(node: JsonNode): Root = {
    val data = if (node.hasNonNull("items")) node.get("items").elements() else node.elements()
    val items = data.asScala.map(x => toData(x)).toList
    Root(items)
  }

  private def toData(node: JsonNode): Data = {
    val data = if (node.hasNonNull("data")) node.get("data").elements() else node.elements()
    val items = data.asScala.map(_.asDouble).toList
    Data(items)
  }

  def read(input: String): Root =
    toRoot(mapper.readTree(input))

  def flatten(in: Root): List[Double] =
    in.items
      .flatMap(_.data)
      .toList

}
