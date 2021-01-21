package com.github.atais

import com.github.atais.IteratorModel.{Root => IRoot}
import com.github.atais.ListModel.{Root => LRoot}
import com.github.atais.SeqModel.{Root => SRoot}
import org.apache.flink.api.common.typeinfo.TypeInformation
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment

import java.io.InputStream
import scala.io.Source

object Shared {
  implicit val t1: TypeInformation[String] = TypeInformation.of(classOf[String])
  implicit val t2a: TypeInformation[SRoot] = TypeInformation.of(classOf[SRoot])
  implicit val t2b: TypeInformation[IRoot] = TypeInformation.of(classOf[IRoot])
  implicit val t2c: TypeInformation[LRoot] = TypeInformation.of(classOf[LRoot])
  implicit val t3: TypeInformation[List[Double]] = TypeInformation.of(classOf[List[Double]])

  val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment
  val stream: InputStream = getClass.getResourceAsStream("/data.json")
  val input: String = Source.fromInputStream(stream).getLines().mkString
}
