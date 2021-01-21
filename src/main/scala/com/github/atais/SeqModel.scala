package com.github.atais

import com.fasterxml.jackson.databind.JsonNode
import com.github.atais.Jackson.mapper

import scala.collection.JavaConverters._

object SeqModel {

  case class Root(items: Seq[Data])

  case class Data(data: Seq[Double])

  private def toRoot(node: JsonNode): Root = {
    val data = if (node.hasNonNull("items")) node.get("items").elements() else node.elements()
    val items = data.asScala.map(x => toData(x)).toSeq
    Root(items)
  }

  private def toData(node: JsonNode): Data = {
    val data = if (node.hasNonNull("data")) node.get("data").elements() else node.elements()
    val items = data.asScala.map(_.asDouble).toSeq
    Data(items)
  }

  def read(input: String): Root =
    toRoot(mapper.readTree(input))

  def flatten(in: Root): List[Double] =
    in.items
      .flatMap(_.data)
      .toList

}
