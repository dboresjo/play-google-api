package com.boresjo.play.api.google.addressvalidation

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://addressvalidation.googleapis.com/"

	object v1 {
		class provideValidationFeedback(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withGoogleMapsAddressvalidationV1ProvideValidationFeedbackRequest(body: Schema.GoogleMapsAddressvalidationV1ProvideValidationFeedbackRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleMapsAddressvalidationV1ProvideValidationFeedbackResponse])
		}
		object provideValidationFeedback {
			def apply()(using auth: AuthToken, ec: ExecutionContext): provideValidationFeedback = new provideValidationFeedback(auth(ws.url(BASE_URL + s"v1:provideValidationFeedback")).addQueryStringParameters())
		}
		class validateAddress(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withGoogleMapsAddressvalidationV1ValidateAddressRequest(body: Schema.GoogleMapsAddressvalidationV1ValidateAddressRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleMapsAddressvalidationV1ValidateAddressResponse])
		}
		object validateAddress {
			def apply()(using auth: AuthToken, ec: ExecutionContext): validateAddress = new validateAddress(auth(ws.url(BASE_URL + s"v1:validateAddress")).addQueryStringParameters())
		}
	}
}
