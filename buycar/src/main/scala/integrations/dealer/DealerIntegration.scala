package integrations.dealer

import zio.{Task, ZIO}

trait DealerIntegration {
  def checkCar(name: String): Task[CarInfoResponse]
}

object DealerIntegration {
  def checkCar(name: String): ZIO[DealerIntegration, Throwable, CarInfoResponse] =
    ZIO.serviceWithZIO[DealerIntegration](_.checkCar(name))
}
