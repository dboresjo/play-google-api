package com.boresjo.play.api.google.searchconsole

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
		"""https://www.googleapis.com/auth/webmasters""" /* View and manage Search Console data for your verified sites */,
		"""https://www.googleapis.com/auth/webmasters.readonly""" /* View Search Console data for your verified sites */
	)

	private val BASE_URL = "https://searchconsole.googleapis.com/"

	object sites {
		/**  Retrieves information about specific site. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.WmxSite]) {
			val scopes = Seq("""https://www.googleapis.com/auth/webmasters""", """https://www.googleapis.com/auth/webmasters.readonly""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.WmxSite])
		}
		object get {
			def apply(siteUrl: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"webmasters/v3/sites/${siteUrl}").addQueryStringParameters())
			given Conversion[get, Future[Schema.WmxSite]] = (fun: get) => fun.apply()
		}
		/**  Adds a site to the set of the user's sites in Search Console. */
		class add(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Unit]) {
			val scopes = Seq("""https://www.googleapis.com/auth/webmasters""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("PUT")).map(_ => ())
		}
		object add {
			def apply(siteUrl: String)(using signer: RequestSigner, ec: ExecutionContext): add = new add(ws.url(BASE_URL + s"webmasters/v3/sites/${siteUrl}").addQueryStringParameters())
			given Conversion[add, Future[Unit]] = (fun: add) => fun.apply()
		}
		/**  Lists the user's Search Console sites. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.SitesListResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/webmasters""", """https://www.googleapis.com/auth/webmasters.readonly""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.SitesListResponse])
		}
		object list {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"webmasters/v3/sites").addQueryStringParameters())
			given Conversion[list, Future[Schema.SitesListResponse]] = (fun: list) => fun.apply()
		}
		/**  Removes a site from the set of the user's Search Console sites. */
		class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Unit]) {
			val scopes = Seq("""https://www.googleapis.com/auth/webmasters""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_ => ())
		}
		object delete {
			def apply(siteUrl: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"webmasters/v3/sites/${siteUrl}").addQueryStringParameters())
			given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
		}
	}
	object urlInspection {
		object index {
			/** Index inspection. */
			class inspect(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/webmasters""", """https://www.googleapis.com/auth/webmasters.readonly""")
				/** Perform the request */
				def withInspectUrlIndexRequest(body: Schema.InspectUrlIndexRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.InspectUrlIndexResponse])
			}
			object inspect {
				def apply()(using signer: RequestSigner, ec: ExecutionContext): inspect = new inspect(ws.url(BASE_URL + s"v1/urlInspection/index:inspect").addQueryStringParameters())
			}
		}
	}
	object urlTestingTools {
		object mobileFriendlyTest {
			/** Runs Mobile-Friendly Test for a given URL. */
			class run(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq()
				/** Perform the request */
				def withRunMobileFriendlyTestRequest(body: Schema.RunMobileFriendlyTestRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.RunMobileFriendlyTestResponse])
			}
			object run {
				def apply()(using signer: RequestSigner, ec: ExecutionContext): run = new run(ws.url(BASE_URL + s"v1/urlTestingTools/mobileFriendlyTest:run").addQueryStringParameters())
			}
		}
	}
	object sitemaps {
		/** Submits a sitemap for a site. */
		class submit(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Unit]) {
			val scopes = Seq("""https://www.googleapis.com/auth/webmasters""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("PUT")).map(_ => ())
		}
		object submit {
			def apply(siteUrl: String, feedpath: String)(using signer: RequestSigner, ec: ExecutionContext): submit = new submit(ws.url(BASE_URL + s"webmasters/v3/sites/${siteUrl}/sitemaps/${feedpath}").addQueryStringParameters())
			given Conversion[submit, Future[Unit]] = (fun: submit) => fun.apply()
		}
		/**  Lists the [sitemaps-entries](/webmaster-tools/v3/sitemaps) submitted for this site, or included in the sitemap index file (if `sitemapIndex` is specified in the request). */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.SitemapsListResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/webmasters""", """https://www.googleapis.com/auth/webmasters.readonly""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.SitemapsListResponse])
		}
		object list {
			def apply(siteUrl: String, sitemapIndex: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"webmasters/v3/sites/${siteUrl}/sitemaps").addQueryStringParameters("sitemapIndex" -> sitemapIndex.toString))
			given Conversion[list, Future[Schema.SitemapsListResponse]] = (fun: list) => fun.apply()
		}
		/** Deletes a sitemap from the Sitemaps report. Does not stop Google from crawling this sitemap or the URLs that were previously crawled in the deleted sitemap. */
		class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Unit]) {
			val scopes = Seq("""https://www.googleapis.com/auth/webmasters""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_ => ())
		}
		object delete {
			def apply(siteUrl: String, feedpath: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"webmasters/v3/sites/${siteUrl}/sitemaps/${feedpath}").addQueryStringParameters())
			given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
		}
		/** Retrieves information about a specific sitemap. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.WmxSitemap]) {
			val scopes = Seq("""https://www.googleapis.com/auth/webmasters""", """https://www.googleapis.com/auth/webmasters.readonly""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.WmxSitemap])
		}
		object get {
			def apply(siteUrl: String, feedpath: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"webmasters/v3/sites/${siteUrl}/sitemaps/${feedpath}").addQueryStringParameters())
			given Conversion[get, Future[Schema.WmxSitemap]] = (fun: get) => fun.apply()
		}
	}
	object searchanalytics {
		/** Query your data with filters and parameters that you define. Returns zero or more rows grouped by the row keys that you define. You must define a date range of one or more days. When date is one of the group by values, any days without data are omitted from the result list. If you need to know which days have data, issue a broad date range query grouped by date for any metric, and see which day rows are returned. */
		class query(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/webmasters""", """https://www.googleapis.com/auth/webmasters.readonly""")
			/** Perform the request */
			def withSearchAnalyticsQueryRequest(body: Schema.SearchAnalyticsQueryRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.SearchAnalyticsQueryResponse])
		}
		object query {
			def apply(siteUrl: String)(using signer: RequestSigner, ec: ExecutionContext): query = new query(ws.url(BASE_URL + s"webmasters/v3/sites/${siteUrl}/searchAnalytics/query").addQueryStringParameters())
		}
	}
}
