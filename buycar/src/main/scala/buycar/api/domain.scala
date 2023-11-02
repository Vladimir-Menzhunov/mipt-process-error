package buycar.api

import zio.{IO, ZIO}
import zio.http.Request

object domain {
  case class RequestInfo(userId: String, carName: String)

  def getRequestInfo(req: Request): IO[Option[Nothing], RequestInfo] = {
    for {
      userId <- ZIO
        .fromOption {
          req.url.queryParams.get("userId").flatMap(_.headOption)
        }
        .tapError(_ => ZIO.logError("not provide userId"))
      carName <- ZIO
        .fromOption {
          req.url.queryParams.get("carName").flatMap(_.headOption)
        }
        .tapError(_ => ZIO.logError("not provide name"))
    } yield RequestInfo(userId, carName)
  }
}
