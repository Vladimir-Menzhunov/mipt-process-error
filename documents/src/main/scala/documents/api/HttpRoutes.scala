package documents.api

import io.circe.syntax.EncoderOps
import zio.ZIO
import zio.http._
import zio.http.model.{Method, Status}

object HttpRoutes {
  val users = Map(
    "1" -> DocumentsUser("Vlad", "vlad.ivanov@gmail.com", 110000000),
    "2" -> DocumentsUser("Ilya", "ilya.petrov@gmail.com", 545610000),
    "3" -> DocumentsUser("Sasha", "sasha.sidorov@gmail.com", 53410000)
  )

  val app: HttpApp[Any, Response] =
    Http.collectZIO[Request] {
      case Method.GET -> !! / "hello" =>
        ZIO.succeed(Response.text("Hello documents service"))

      case req @ Method.GET -> !! / "get" / "documents" / "by" / "user" =>
        val response = for {
          userId <-
            ZIO.fromOption(
              req.url.queryParams
                .get("id")
                .flatMap(_.headOption)
            )
          documents <- ZIO
            .fromOption(users.get(userId))
            .tapError(_ => ZIO.logError(s"Not found user $userId"))
        } yield Response.json(documents.asJson.toString())

        response.orElseFail(Response.status(Status.BadRequest))
    }
}

//      case req @ Method.POST -> !! / "greeting" / "by" =>
//        val response =
//          for {
//            name <- ZIO
//              .fromOption(
//                req.url.queryParams
//                  .get("name")
//                  .flatMap(_.headOption)
//              )
//              .tapError(_ => ZIO.logError("not provide id"))
//          } yield Response.text(s"Hello $name")
//
//        response.orElseFail(Response.status(Status.BadRequest))
