package com.boresjo.play.api.google.dataportability

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://dataportability.googleapis.com/"

	object archiveJobs {
		class getPortabilityArchiveState(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.PortabilityArchiveState]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.PortabilityArchiveState])
		}
		object getPortabilityArchiveState {
			def apply(archiveJobsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): getPortabilityArchiveState = new getPortabilityArchiveState(ws.url(BASE_URL + s"v1/archiveJobs/${archiveJobsId}/portabilityArchiveState").addQueryStringParameters("name" -> name.toString))
			given Conversion[getPortabilityArchiveState, Future[Schema.PortabilityArchiveState]] = (fun: getPortabilityArchiveState) => fun.apply()
		}
		class retry(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withRetryPortabilityArchiveRequest(body: Schema.RetryPortabilityArchiveRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.RetryPortabilityArchiveResponse])
		}
		object retry {
			def apply(archiveJobsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): retry = new retry(ws.url(BASE_URL + s"v1/archiveJobs/${archiveJobsId}:retry").addQueryStringParameters("name" -> name.toString))
		}
	}
	object authorization {
		class reset(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withResetAuthorizationRequest(body: Schema.ResetAuthorizationRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Empty])
		}
		object reset {
			def apply()(using auth: AuthToken, ec: ExecutionContext): reset = new reset(ws.url(BASE_URL + s"v1/authorization:reset").addQueryStringParameters())
		}
	}
	object portabilityArchive {
		class initiate(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withInitiatePortabilityArchiveRequest(body: Schema.InitiatePortabilityArchiveRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.InitiatePortabilityArchiveResponse])
		}
		object initiate {
			def apply()(using auth: AuthToken, ec: ExecutionContext): initiate = new initiate(ws.url(BASE_URL + s"v1/portabilityArchive:initiate").addQueryStringParameters())
		}
	}
}
