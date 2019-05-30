package com.dirinle

import com.dirinle.Rest.Entity
import io.circe._

object Implicits {

  def entityEncoder(fun: List[String] => Json): Encoder[Entity] = (e: Entity) => Json.obj(
    e.map(p => p._2 match {
      case Left(e) => p._1 -> Json.fromString(e)
      case Right(l) => p._1 -> fun(l)
    }).toSeq:_*
  )

  implicit val InfoEncoder: Encoder[Info] = (i: Info) => Json.obj("mem-used-pct" -> Json.fromDoubleOrNull(i.memoryUsage))

  implicit val entityDecoder: Decoder[Entity] = (c: HCursor) => {
    val res: Entity = c.keys.map{ keys =>
      val keysResults: Seq[Option[(String, Either[String, List[String]])]] = keys.toSeq.map{ key =>
        val maybeData: Option[(String, Either[String, List[String]])] =
          (c.downField(key).as[String], c.downField(key).as[List[String]]) match {
            case (Right(s), _) => Some(key -> Left(s))
            case (_, Right(s)) => Some(key -> Right(s))
            case _ => None
          }
        maybeData
      }
      if (keysResults.exists(_.isEmpty)) Map.empty else keysResults.collect{ case Some(p) => p}.toMap
    }.getOrElse(Map.empty).map(x =>x._1.toString -> x._2)
    if (res.isEmpty) Left(DecodingFailure("", List.empty)) else Right(res)
  }

  implicit val simpleEntityEncoder: Encoder[Entity] = entityEncoder(l => Json.fromValues(l.map(Json.fromString)))

  val flattenEntityEncoder: Encoder[Entity] = entityEncoder(l => Json.fromString(l.mkString(",")))

}
