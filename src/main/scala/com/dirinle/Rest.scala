package com.dirinle

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.stream.ActorMaterializer
import Implicits._
import akka.http.scaladsl.server.Directives.complete
import akka.http.scaladsl.server.StandardRoute
import io.circe.Json
import io.circe.generic.auto._
import io.circe.syntax._

object Rest{
  val handler = new HandlerImpl
  def responseJson(a: Json): StandardRoute = complete(HttpEntity(ContentTypes.`application/json`, a.noSpaces))
  type Entity = Map[String, Either[String, List[String]]]
  implicit val system = ActorSystem("my-system")
  implicit val materializer = ActorMaterializer()
  implicit val executionContext = system.dispatcher
  import akka.http.scaladsl.server.Directives._
  import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._
  val route =
    pathPrefix("alpha") {
      put {
        entity(as[Entity]) { e =>
          responseJson(handler.alphabetOrder(e).asJson)
        }
      }
    } ~ pathPrefix("flatten"){
      put{
        entity(as[Entity]) { e =>
          responseJson(e.asJson(Implicits.flattenEntityEncoder))
        }
      }
    } ~ pathPrefix("status"){ get (responseJson(handler.info.asJson))}

  val bindingFuture = Http().bindAndHandle(route, Settings.interface, Settings.port)

}
