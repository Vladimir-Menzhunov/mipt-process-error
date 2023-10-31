package pay.api

import io.circe.parser._
import io.circe.syntax.EncoderOps
import zio.ZIO
import zio.http._
import zio.http.model.{Method, Status}

object HttpRoutes {
  val app: HttpApp[Any, Response] =
    Http.collectZIO[Request] {
      case Method.GET -> !! / "hello" =>
        ZIO.succeed(Response.text("Hello pay service"))

      case req @ Method.POST -> !! / "pay" =>
        val response = for {
          payRequest <- req.body.asString.flatMap(str =>  ZIO.fromEither(parse(str).flatMap(_.as[PayRequest])))
          _ <- ZIO.logInfo(payRequest.toString)
        } yield Response.json(
          PayResponse(
            payRequest.userName,
            payRequest.email,
            payRequest.balance - payRequest.carPrice,
            Some(payRequest.carName),
          ).asJson.toString()
        )

        response.orElseFail(Response.status(Status.BadRequest))
    }
}
