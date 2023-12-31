package dealer.api

import io.circe.generic.JsonCodec

@JsonCodec
case class CarInfoResponse(
    name: String,
    isAvailable: Boolean,
    price: Option[Int]
)
