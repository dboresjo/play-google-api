package com.boresjo.play.api.google.mybusinessverifications

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	val scopes = Seq()

	private val BASE_URL = "https://mybusinessverifications.googleapis.com/"

	object locations {
		/** Gets the VoiceOfMerchant state. */
		class getVoiceOfMerchantState(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.VoiceOfMerchantState]) {
			val scopes = Seq()
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.VoiceOfMerchantState])
		}
		object getVoiceOfMerchantState {
			def apply(locationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): getVoiceOfMerchantState = new getVoiceOfMerchantState(ws.url(BASE_URL + s"v1/locations/${locationsId}/VoiceOfMerchantState").addQueryStringParameters("name" -> name.toString))
			given Conversion[getVoiceOfMerchantState, Future[Schema.VoiceOfMerchantState]] = (fun: getVoiceOfMerchantState) => fun.apply()
		}
		/** Reports all eligible verification options for a location in a specific language. */
		class fetchVerificationOptions(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq()
			/** Perform the request */
			def withFetchVerificationOptionsRequest(body: Schema.FetchVerificationOptionsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.FetchVerificationOptionsResponse])
		}
		object fetchVerificationOptions {
			def apply(locationsId :PlayApi, location: String)(using signer: RequestSigner, ec: ExecutionContext): fetchVerificationOptions = new fetchVerificationOptions(ws.url(BASE_URL + s"v1/locations/${locationsId}:fetchVerificationOptions").addQueryStringParameters("location" -> location.toString))
		}
		/** Starts the verification process for a location. */
		class verify(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq()
			/** Perform the request */
			def withVerifyLocationRequest(body: Schema.VerifyLocationRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.VerifyLocationResponse])
		}
		object verify {
			def apply(locationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): verify = new verify(ws.url(BASE_URL + s"v1/locations/${locationsId}:verify").addQueryStringParameters("name" -> name.toString))
		}
		object verifications {
			/** Completes a `PENDING` verification. It is only necessary for non `AUTO` verification methods. `AUTO` verification request is instantly `VERIFIED` upon creation. */
			class complete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq()
				/** Perform the request */
				def withCompleteVerificationRequest(body: Schema.CompleteVerificationRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.CompleteVerificationResponse])
			}
			object complete {
				def apply(locationsId :PlayApi, verificationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): complete = new complete(ws.url(BASE_URL + s"v1/locations/${locationsId}/verifications/${verificationsId}:complete").addQueryStringParameters("name" -> name.toString))
			}
			/** List verifications of a location, ordered by create time. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListVerificationsResponse]) {
				val scopes = Seq()
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListVerificationsResponse])
			}
			object list {
				def apply(locationsId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/locations/${locationsId}/verifications").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListVerificationsResponse]] = (fun: list) => fun.apply()
			}
		}
	}
}
