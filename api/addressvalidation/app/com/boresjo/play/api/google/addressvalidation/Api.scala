package com.boresjo.play.api.google.addressvalidation

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	val scopes = Seq(
		"""https://www.googleapis.com/auth/maps-platform.addressvalidation""" /* Private Service: https://www.googleapis.com/auth/maps-platform.addressvalidation */,
		"""https://www.googleapis.com/auth/cloud-platform""" /* See, edit, configure, and delete your Google Cloud data and see the email address for your Google Account. */
	)

	private val BASE_URL = "https://addressvalidation.googleapis.com/"

	object v1 {
		/** Feedback about the outcome of the sequence of validation attempts. This should be the last call made after a sequence of validation calls for the same address, and should be called once the transaction is concluded. This should only be sent once for the sequence of `ValidateAddress` requests needed to validate an address fully. */
		class provideValidationFeedback(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/maps-platform.addressvalidation""")
			/** Perform the request */
			def withGoogleMapsAddressvalidationV1ProvideValidationFeedbackRequest(body: Schema.GoogleMapsAddressvalidationV1ProvideValidationFeedbackRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleMapsAddressvalidationV1ProvideValidationFeedbackResponse])
		}
		object provideValidationFeedback {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): provideValidationFeedback = new provideValidationFeedback(ws.url(BASE_URL + s"v1:provideValidationFeedback").addQueryStringParameters())
		}
		/** Validates an address. */
		class validateAddress(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/maps-platform.addressvalidation""")
			/** Perform the request */
			def withGoogleMapsAddressvalidationV1ValidateAddressRequest(body: Schema.GoogleMapsAddressvalidationV1ValidateAddressRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleMapsAddressvalidationV1ValidateAddressResponse])
		}
		object validateAddress {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): validateAddress = new validateAddress(ws.url(BASE_URL + s"v1:validateAddress").addQueryStringParameters())
		}
	}
}
