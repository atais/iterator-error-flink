package com.github.atais

import com.github.atais.IteratorModel._
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import com.github.atais.Shared._

class IteratorModelSpec extends AnyFlatSpec with Matchers {

  it should "parse in different threads with flink" in {
    env.fromCollection(Seq(input))
      .map(i => read(i))
      .map(i => flatten(i))
      .print()

    env.execute()
  }

  it should "parse in the same thread with flink" in {
    env.fromCollection(Seq(input))
      .map(i => flatten(read(i)))
      .print()

    env.execute()
  }

  it should "parse data without flink" in {
    val allPoints = flatten(read(input)).toList

    allPoints should not be empty
    println(allPoints.head)
    println(allPoints.last)
    println(allPoints.size)
  }

}

