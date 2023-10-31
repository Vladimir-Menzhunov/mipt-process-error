package dealer.api

import io.circe.syntax.EncoderOps
import zio.ZIO
import zio.http._
import zio.http.model.{Method, Status}

object HttpRoutes {
  val cars = Map(
    "ferrari" -> CarInfoResponse("ferrari", true, Some(10000000)),
    "vesta" -> CarInfoResponse("vesta", true, Some(1000)),
    "polo" -> CarInfoResponse("polo", true, Some(100000))
  )

  val app: HttpApp[Any, Response] =
    Http.collectZIO[Request] {
      case Method.GET -> !! / "hello" =>
        ZIO.succeed(Response.text("Hello dealer service"))

      case req @ Method.GET -> !! / "check" / "car" =>
        val response = for {
          carName <-
            ZIO.fromOption(
              req.url.queryParams
                .get("name")
                .flatMap(_.headOption)
            )
          result <- ZIO.fromOption(cars.get(carName))

        } yield Response.json(result.asJson.toString())

        response.orElseFail(Response.status(Status.BadRequest))
    }
}
