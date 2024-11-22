package com.boresjo.play.api.google.abusiveexperiencereport

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

	private val BASE_URL = "https://abusiveexperiencereport.googleapis.com/"

	object sites {
		/** Gets a site's Abusive Experience Report summary. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.SiteSummaryResponse]) {
			val scopes = Seq()
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.SiteSummaryResponse])
		}
		object get {
			def apply(sitesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/sites/${sitesId}").addQueryStringParameters("name" -> name.toString))
			given Conversion[get, Future[Schema.SiteSummaryResponse]] = (fun: get) => fun.apply()
		}
	}
	object violatingSites {
		/** Lists sites that are failing in the Abusive Experience Report. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ViolatingSitesResponse]) {
			val scopes = Seq()
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ViolatingSitesResponse])
		}
		object list {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/violatingSites").addQueryStringParameters())
			given Conversion[list, Future[Schema.ViolatingSitesResponse]] = (fun: list) => fun.apply()
		}
	}
}
