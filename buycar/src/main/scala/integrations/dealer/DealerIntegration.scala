package integrations.dealer

import zio.{IO, ZIO}

trait DealerIntegration {
  def checkCar(name: String): IO[Serializable, CarInfoResponse]
}

object DealerIntegration {
  def checkCar(name: String): ZIO[DealerIntegration, Serializable, CarInfoResponse] =
    ZIO.serviceWithZIO[DealerIntegration](_.checkCar(name))
}
