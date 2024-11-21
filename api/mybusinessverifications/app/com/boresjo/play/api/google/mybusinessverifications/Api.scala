package com.boresjo.play.api.google.mybusinessverifications

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://mybusinessverifications.googleapis.com/"

	object locations {
		class getVoiceOfMerchantState(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.VoiceOfMerchantState]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.VoiceOfMerchantState])
		}
		object getVoiceOfMerchantState {
			def apply(locationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): getVoiceOfMerchantState = new getVoiceOfMerchantState(auth(ws.url(BASE_URL + s"v1/locations/${locationsId}/VoiceOfMerchantState")).addQueryStringParameters("name" -> name.toString))
			given Conversion[getVoiceOfMerchantState, Future[Schema.VoiceOfMerchantState]] = (fun: getVoiceOfMerchantState) => fun.apply()
		}
		class fetchVerificationOptions(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withFetchVerificationOptionsRequest(body: Schema.FetchVerificationOptionsRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.FetchVerificationOptionsResponse])
		}
		object fetchVerificationOptions {
			def apply(locationsId :PlayApi, location: String)(using auth: AuthToken, ec: ExecutionContext): fetchVerificationOptions = new fetchVerificationOptions(auth(ws.url(BASE_URL + s"v1/locations/${locationsId}:fetchVerificationOptions")).addQueryStringParameters("location" -> location.toString))
		}
		class verify(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withVerifyLocationRequest(body: Schema.VerifyLocationRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.VerifyLocationResponse])
		}
		object verify {
			def apply(locationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): verify = new verify(auth(ws.url(BASE_URL + s"v1/locations/${locationsId}:verify")).addQueryStringParameters("name" -> name.toString))
		}
		object verifications {
			class complete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withCompleteVerificationRequest(body: Schema.CompleteVerificationRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.CompleteVerificationResponse])
			}
			object complete {
				def apply(locationsId :PlayApi, verificationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): complete = new complete(auth(ws.url(BASE_URL + s"v1/locations/${locationsId}/verifications/${verificationsId}:complete")).addQueryStringParameters("name" -> name.toString))
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListVerificationsResponse]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.ListVerificationsResponse])
			}
			object list {
				def apply(locationsId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/locations/${locationsId}/verifications")).addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListVerificationsResponse]] = (fun: list) => fun.apply()
			}
		}
	}
}
