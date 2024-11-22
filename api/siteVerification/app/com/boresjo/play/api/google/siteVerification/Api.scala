package com.boresjo.play.api.google.siteVerification

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://www.googleapis.com/siteVerification/v1/"

	object webResource {
		class insert(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withSiteVerificationWebResourceResource(body: Schema.SiteVerificationWebResourceResource) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.SiteVerificationWebResourceResource])
		}
		object insert {
			def apply(verificationMethod: String)(using auth: AuthToken, ec: ExecutionContext): insert = new insert(ws.url(BASE_URL + s"webResource").addQueryStringParameters("verificationMethod" -> verificationMethod.toString))
		}
		class getToken(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withSiteVerificationWebResourceGettokenRequest(body: Schema.SiteVerificationWebResourceGettokenRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.SiteVerificationWebResourceGettokenResponse])
		}
		object getToken {
			def apply()(using auth: AuthToken, ec: ExecutionContext): getToken = new getToken(ws.url(BASE_URL + s"token").addQueryStringParameters())
		}
		class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Unit]) {
			def apply() = auth.exec(req,_.execute("DELETE")).map(_ => ())
		}
		object delete {
			def apply(id: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"webResource/${id}").addQueryStringParameters())
			given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
		}
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.SiteVerificationWebResourceResource]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.SiteVerificationWebResourceResource])
		}
		object get {
			def apply(id: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"webResource/${id}").addQueryStringParameters())
			given Conversion[get, Future[Schema.SiteVerificationWebResourceResource]] = (fun: get) => fun.apply()
		}
		class update(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withSiteVerificationWebResourceResource(body: Schema.SiteVerificationWebResourceResource) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.SiteVerificationWebResourceResource])
		}
		object update {
			def apply(id: String)(using auth: AuthToken, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"webResource/${id}").addQueryStringParameters())
		}
		class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withSiteVerificationWebResourceResource(body: Schema.SiteVerificationWebResourceResource) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.SiteVerificationWebResourceResource])
		}
		object patch {
			def apply(id: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"webResource/${id}").addQueryStringParameters())
		}
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.SiteVerificationWebResourceListResponse]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.SiteVerificationWebResourceListResponse])
		}
		object list {
			def apply()(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"webResource").addQueryStringParameters())
			given Conversion[list, Future[Schema.SiteVerificationWebResourceListResponse]] = (fun: list) => fun.apply()
		}
	}
}
