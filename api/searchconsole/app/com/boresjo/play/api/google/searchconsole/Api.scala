package com.boresjo.play.api.google.searchconsole

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://searchconsole.googleapis.com/"

	object sites {
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.WmxSite]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.WmxSite])
		}
		object get {
			def apply(siteUrl: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"webmasters/v3/sites/${siteUrl}").addQueryStringParameters())
			given Conversion[get, Future[Schema.WmxSite]] = (fun: get) => fun.apply()
		}
		class add(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Unit]) {
			def apply() = auth.exec(req,_.execute("PUT")).map(_ => ())
		}
		object add {
			def apply(siteUrl: String)(using auth: AuthToken, ec: ExecutionContext): add = new add(ws.url(BASE_URL + s"webmasters/v3/sites/${siteUrl}").addQueryStringParameters())
			given Conversion[add, Future[Unit]] = (fun: add) => fun.apply()
		}
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.SitesListResponse]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.SitesListResponse])
		}
		object list {
			def apply()(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"webmasters/v3/sites").addQueryStringParameters())
			given Conversion[list, Future[Schema.SitesListResponse]] = (fun: list) => fun.apply()
		}
		class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Unit]) {
			def apply() = auth.exec(req,_.execute("DELETE")).map(_ => ())
		}
		object delete {
			def apply(siteUrl: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"webmasters/v3/sites/${siteUrl}").addQueryStringParameters())
			given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
		}
	}
	object urlInspection {
		object index {
			class inspect(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withInspectUrlIndexRequest(body: Schema.InspectUrlIndexRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.InspectUrlIndexResponse])
			}
			object inspect {
				def apply()(using auth: AuthToken, ec: ExecutionContext): inspect = new inspect(ws.url(BASE_URL + s"v1/urlInspection/index:inspect").addQueryStringParameters())
			}
		}
	}
	object urlTestingTools {
		object mobileFriendlyTest {
			class run(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withRunMobileFriendlyTestRequest(body: Schema.RunMobileFriendlyTestRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.RunMobileFriendlyTestResponse])
			}
			object run {
				def apply()(using auth: AuthToken, ec: ExecutionContext): run = new run(ws.url(BASE_URL + s"v1/urlTestingTools/mobileFriendlyTest:run").addQueryStringParameters())
			}
		}
	}
	object sitemaps {
		class submit(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Unit]) {
			def apply() = auth.exec(req,_.execute("PUT")).map(_ => ())
		}
		object submit {
			def apply(siteUrl: String, feedpath: String)(using auth: AuthToken, ec: ExecutionContext): submit = new submit(ws.url(BASE_URL + s"webmasters/v3/sites/${siteUrl}/sitemaps/${feedpath}").addQueryStringParameters())
			given Conversion[submit, Future[Unit]] = (fun: submit) => fun.apply()
		}
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.SitemapsListResponse]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.SitemapsListResponse])
		}
		object list {
			def apply(siteUrl: String, sitemapIndex: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"webmasters/v3/sites/${siteUrl}/sitemaps").addQueryStringParameters("sitemapIndex" -> sitemapIndex.toString))
			given Conversion[list, Future[Schema.SitemapsListResponse]] = (fun: list) => fun.apply()
		}
		class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Unit]) {
			def apply() = auth.exec(req,_.execute("DELETE")).map(_ => ())
		}
		object delete {
			def apply(siteUrl: String, feedpath: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"webmasters/v3/sites/${siteUrl}/sitemaps/${feedpath}").addQueryStringParameters())
			given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
		}
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.WmxSitemap]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.WmxSitemap])
		}
		object get {
			def apply(siteUrl: String, feedpath: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"webmasters/v3/sites/${siteUrl}/sitemaps/${feedpath}").addQueryStringParameters())
			given Conversion[get, Future[Schema.WmxSitemap]] = (fun: get) => fun.apply()
		}
	}
	object searchanalytics {
		class query(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withSearchAnalyticsQueryRequest(body: Schema.SearchAnalyticsQueryRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.SearchAnalyticsQueryResponse])
		}
		object query {
			def apply(siteUrl: String)(using auth: AuthToken, ec: ExecutionContext): query = new query(ws.url(BASE_URL + s"webmasters/v3/sites/${siteUrl}/searchAnalytics/query").addQueryStringParameters())
		}
	}
}
