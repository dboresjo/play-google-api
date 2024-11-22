package com.boresjo.play.api.google.admob

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
		"""https://www.googleapis.com/auth/admob.readonly""" /* See your AdMob data */,
		"""https://www.googleapis.com/auth/admob.report""" /* See your AdMob data */
	)

	private val BASE_URL = "https://admob.googleapis.com/"

	object accounts {
		/** Gets information about the specified AdMob publisher account. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.PublisherAccount]) {
			val scopes = Seq("""https://www.googleapis.com/auth/admob.readonly""", """https://www.googleapis.com/auth/admob.report""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.PublisherAccount])
		}
		object get {
			def apply(accountsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/accounts/${accountsId}").addQueryStringParameters("name" -> name.toString))
			given Conversion[get, Future[Schema.PublisherAccount]] = (fun: get) => fun.apply()
		}
		/** Lists the AdMob publisher account that was most recently signed in to from the AdMob UI. For more information, see https://support.google.com/admob/answer/10243672. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListPublisherAccountsResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/admob.readonly""", """https://www.googleapis.com/auth/admob.report""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListPublisherAccountsResponse])
		}
		object list {
			def apply(pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/accounts").addQueryStringParameters("pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
			given Conversion[list, Future[Schema.ListPublisherAccountsResponse]] = (fun: list) => fun.apply()
		}
		object mediationReport {
			/** Generates an AdMob Mediation report based on the provided report specification. Returns result of a server-side streaming RPC. The result is returned in a sequence of responses. */
			class generate(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/admob.readonly""", """https://www.googleapis.com/auth/admob.report""")
				/** Perform the request */
				def withGenerateMediationReportRequest(body: Schema.GenerateMediationReportRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GenerateMediationReportResponse])
			}
			object generate {
				def apply(accountsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): generate = new generate(ws.url(BASE_URL + s"v1/accounts/${accountsId}/mediationReport:generate").addQueryStringParameters("parent" -> parent.toString))
			}
		}
		object adUnits {
			/** List the ad units under the specified AdMob account. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListAdUnitsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/admob.readonly""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListAdUnitsResponse])
			}
			object list {
				def apply(accountsId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/accounts/${accountsId}/adUnits").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListAdUnitsResponse]] = (fun: list) => fun.apply()
			}
		}
		object networkReport {
			/** Generates an AdMob Network report based on the provided report specification. Returns result of a server-side streaming RPC. The result is returned in a sequence of responses. */
			class generate(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/admob.readonly""", """https://www.googleapis.com/auth/admob.report""")
				/** Perform the request */
				def withGenerateNetworkReportRequest(body: Schema.GenerateNetworkReportRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GenerateNetworkReportResponse])
			}
			object generate {
				def apply(accountsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): generate = new generate(ws.url(BASE_URL + s"v1/accounts/${accountsId}/networkReport:generate").addQueryStringParameters("parent" -> parent.toString))
			}
		}
		object apps {
			/** List the apps under the specified AdMob account. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListAppsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/admob.readonly""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListAppsResponse])
			}
			object list {
				def apply(accountsId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/accounts/${accountsId}/apps").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListAppsResponse]] = (fun: list) => fun.apply()
			}
		}
	}
}
