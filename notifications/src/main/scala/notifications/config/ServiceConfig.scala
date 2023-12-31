package notifications.config

import pureconfig.ConfigSource
import pureconfig.generic.auto.exportReader
import zio.http.ServerConfig
import zio.{ZLayer, http}

case class ServiceConfig(host: String, port: Int)

object ServiceConfig {
  private val source = ConfigSource.file("./notifications/src/main/resources/application.conf").at("app").at("service-config")
  private val serviceConfig: ServiceConfig = source.loadOrThrow[ServiceConfig]

  val live: ZLayer[Any, Nothing, ServerConfig] = zio.http.ServerConfig.live {
    http
      .ServerConfig
      .default
      .binding(serviceConfig.host, serviceConfig.port)
  }
}
