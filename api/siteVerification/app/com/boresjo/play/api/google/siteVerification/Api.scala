package com.boresjo.play.api.google.siteVerification

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
		"""https://www.googleapis.com/auth/siteverification""" /* Manage the list of sites and domains you control */,
		"""https://www.googleapis.com/auth/siteverification.verify_only""" /* Manage your new site verifications with Google */
	)

	private val BASE_URL = "https://www.googleapis.com/siteVerification/v1/"

	object webResource {
		/** Attempt verification of a website or domain. */
		class insert(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/siteverification""", """https://www.googleapis.com/auth/siteverification.verify_only""")
			/** Perform the request */
			def withSiteVerificationWebResourceResource(body: Schema.SiteVerificationWebResourceResource) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.SiteVerificationWebResourceResource])
		}
		object insert {
			def apply(verificationMethod: String)(using signer: RequestSigner, ec: ExecutionContext): insert = new insert(ws.url(BASE_URL + s"webResource").addQueryStringParameters("verificationMethod" -> verificationMethod.toString))
		}
		/** Get a verification token for placing on a website or domain. */
		class getToken(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/siteverification""", """https://www.googleapis.com/auth/siteverification.verify_only""")
			/** Perform the request */
			def withSiteVerificationWebResourceGettokenRequest(body: Schema.SiteVerificationWebResourceGettokenRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.SiteVerificationWebResourceGettokenResponse])
		}
		object getToken {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): getToken = new getToken(ws.url(BASE_URL + s"token").addQueryStringParameters())
		}
		/** Relinquish ownership of a website or domain. */
		class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Unit]) {
			val scopes = Seq("""https://www.googleapis.com/auth/siteverification""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_ => ())
		}
		object delete {
			def apply(id: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"webResource/${id}").addQueryStringParameters())
			given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
		}
		/** Get the most current data for a website or domain. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.SiteVerificationWebResourceResource]) {
			val scopes = Seq("""https://www.googleapis.com/auth/siteverification""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.SiteVerificationWebResourceResource])
		}
		object get {
			def apply(id: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"webResource/${id}").addQueryStringParameters())
			given Conversion[get, Future[Schema.SiteVerificationWebResourceResource]] = (fun: get) => fun.apply()
		}
		/** Modify the list of owners for your website or domain. */
		class update(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/siteverification""")
			/** Perform the request */
			def withSiteVerificationWebResourceResource(body: Schema.SiteVerificationWebResourceResource) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.SiteVerificationWebResourceResource])
		}
		object update {
			def apply(id: String)(using signer: RequestSigner, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"webResource/${id}").addQueryStringParameters())
		}
		/** Modify the list of owners for your website or domain. This method supports patch semantics. */
		class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/siteverification""")
			/** Perform the request */
			def withSiteVerificationWebResourceResource(body: Schema.SiteVerificationWebResourceResource) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.SiteVerificationWebResourceResource])
		}
		object patch {
			def apply(id: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"webResource/${id}").addQueryStringParameters())
		}
		/** Get the list of your verified websites and domains. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.SiteVerificationWebResourceListResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/siteverification""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.SiteVerificationWebResourceListResponse])
		}
		object list {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"webResource").addQueryStringParameters())
			given Conversion[list, Future[Schema.SiteVerificationWebResourceListResponse]] = (fun: list) => fun.apply()
		}
	}
}
