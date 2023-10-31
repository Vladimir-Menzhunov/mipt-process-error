package notifications.api

import io.circe.parser._
import io.circe.syntax.EncoderOps
import zio.http._
import zio.http.model.{Method, Status}
import zio.{Duration, ZIO}

object HttpRoutes {
  val app: HttpApp[Any, Response] =
    Http.collectZIO[Request] {
      case Method.GET -> !! / "hello" =>
        ZIO.succeed(Response.text("Hello notifications service"))

      case req @ Method.POST -> !! / "send" / "notification" =>
        val response = for {
          _ <- ZIO.sleep(Duration.fromSeconds(2L))
         notification <- req.body.asString.flatMap(s => ZIO.fromEither(parse(s).flatMap(_.as[Notification])))
          _ <- ZIO.logInfo(s"Notification: ${notification.asJson.toString()}")
        } yield Response.status(Status.Ok)

        response.orElseFail(Response.status(Status.BadRequest))
    }
}
