package com.boresjo.play.api.google.youtubeAnalytics

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
		"""https://www.googleapis.com/auth/yt-analytics.readonly""" /* View YouTube Analytics reports for your YouTube content */,
		"""https://www.googleapis.com/auth/youtubepartner""" /* View and manage your assets and associated content on YouTube */,
		"""https://www.googleapis.com/auth/youtube.readonly""" /* View your YouTube account */,
		"""https://www.googleapis.com/auth/yt-analytics-monetary.readonly""" /* View monetary and non-monetary YouTube Analytics reports for your YouTube content */,
		"""https://www.googleapis.com/auth/youtube""" /* Manage your YouTube account */
	)

	private val BASE_URL = "https://youtubeanalytics.googleapis.com/"

	object groups {
		/** Creates a group. */
		class insert(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/youtube""", """https://www.googleapis.com/auth/youtube.readonly""", """https://www.googleapis.com/auth/youtubepartner""", """https://www.googleapis.com/auth/yt-analytics-monetary.readonly""", """https://www.googleapis.com/auth/yt-analytics.readonly""")
			/** Perform the request */
			def withGroup(body: Schema.Group) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Group])
		}
		object insert {
			def apply(onBehalfOfContentOwner: String)(using signer: RequestSigner, ec: ExecutionContext): insert = new insert(ws.url(BASE_URL + s"v2/groups").addQueryStringParameters("onBehalfOfContentOwner" -> onBehalfOfContentOwner.toString))
		}
		/** Modifies a group. For example, you could change a group's title. */
		class update(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/youtube""", """https://www.googleapis.com/auth/youtube.readonly""", """https://www.googleapis.com/auth/youtubepartner""", """https://www.googleapis.com/auth/yt-analytics-monetary.readonly""", """https://www.googleapis.com/auth/yt-analytics.readonly""")
			/** Perform the request */
			def withGroup(body: Schema.Group) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.Group])
		}
		object update {
			def apply(onBehalfOfContentOwner: String)(using signer: RequestSigner, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"v2/groups").addQueryStringParameters("onBehalfOfContentOwner" -> onBehalfOfContentOwner.toString))
		}
		/** Returns a collection of groups that match the API request parameters. For example, you can retrieve all groups that the authenticated user owns, or you can retrieve one or more groups by their unique IDs. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListGroupsResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/youtube""", """https://www.googleapis.com/auth/youtube.readonly""", """https://www.googleapis.com/auth/youtubepartner""", """https://www.googleapis.com/auth/yt-analytics-monetary.readonly""", """https://www.googleapis.com/auth/yt-analytics.readonly""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListGroupsResponse])
		}
		object list {
			def apply(mine: Boolean, id: String, onBehalfOfContentOwner: String, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/groups").addQueryStringParameters("mine" -> mine.toString, "id" -> id.toString, "onBehalfOfContentOwner" -> onBehalfOfContentOwner.toString, "pageToken" -> pageToken.toString))
			given Conversion[list, Future[Schema.ListGroupsResponse]] = (fun: list) => fun.apply()
		}
		/** Deletes a group. */
		class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.EmptyResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/youtube""", """https://www.googleapis.com/auth/youtube.readonly""", """https://www.googleapis.com/auth/youtubepartner""", """https://www.googleapis.com/auth/yt-analytics-monetary.readonly""", """https://www.googleapis.com/auth/yt-analytics.readonly""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.EmptyResponse])
		}
		object delete {
			def apply(id: String, onBehalfOfContentOwner: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v2/groups").addQueryStringParameters("id" -> id.toString, "onBehalfOfContentOwner" -> onBehalfOfContentOwner.toString))
			given Conversion[delete, Future[Schema.EmptyResponse]] = (fun: delete) => fun.apply()
		}
	}
	object groupItems {
		/** Returns a collection of group items that match the API request parameters. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListGroupItemsResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/youtube""", """https://www.googleapis.com/auth/youtube.readonly""", """https://www.googleapis.com/auth/youtubepartner""", """https://www.googleapis.com/auth/yt-analytics-monetary.readonly""", """https://www.googleapis.com/auth/yt-analytics.readonly""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListGroupItemsResponse])
		}
		object list {
			def apply(onBehalfOfContentOwner: String, groupId: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/groupItems").addQueryStringParameters("onBehalfOfContentOwner" -> onBehalfOfContentOwner.toString, "groupId" -> groupId.toString))
			given Conversion[list, Future[Schema.ListGroupItemsResponse]] = (fun: list) => fun.apply()
		}
		/** Removes an item from a group. */
		class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.EmptyResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/youtube""", """https://www.googleapis.com/auth/youtube.readonly""", """https://www.googleapis.com/auth/youtubepartner""", """https://www.googleapis.com/auth/yt-analytics-monetary.readonly""", """https://www.googleapis.com/auth/yt-analytics.readonly""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.EmptyResponse])
		}
		object delete {
			def apply(id: String, onBehalfOfContentOwner: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v2/groupItems").addQueryStringParameters("id" -> id.toString, "onBehalfOfContentOwner" -> onBehalfOfContentOwner.toString))
			given Conversion[delete, Future[Schema.EmptyResponse]] = (fun: delete) => fun.apply()
		}
		/** Creates a group item. */
		class insert(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/youtube""", """https://www.googleapis.com/auth/youtube.readonly""", """https://www.googleapis.com/auth/youtubepartner""", """https://www.googleapis.com/auth/yt-analytics-monetary.readonly""", """https://www.googleapis.com/auth/yt-analytics.readonly""")
			/** Perform the request */
			def withGroupItem(body: Schema.GroupItem) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GroupItem])
		}
		object insert {
			def apply(onBehalfOfContentOwner: String)(using signer: RequestSigner, ec: ExecutionContext): insert = new insert(ws.url(BASE_URL + s"v2/groupItems").addQueryStringParameters("onBehalfOfContentOwner" -> onBehalfOfContentOwner.toString))
		}
	}
	object reports {
		/** Retrieve your YouTube Analytics reports. */
		class query(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.QueryResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/youtube""", """https://www.googleapis.com/auth/youtube.readonly""", """https://www.googleapis.com/auth/youtubepartner""", """https://www.googleapis.com/auth/yt-analytics-monetary.readonly""", """https://www.googleapis.com/auth/yt-analytics.readonly""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.QueryResponse])
		}
		object query {
			def apply(startIndex: Int, ids: String, startDate: String, sort: String, filters: String, currency: String, includeHistoricalChannelData: Boolean, metrics: String, dimensions: String, endDate: String, maxResults: Int)(using signer: RequestSigner, ec: ExecutionContext): query = new query(ws.url(BASE_URL + s"v2/reports").addQueryStringParameters("startIndex" -> startIndex.toString, "ids" -> ids.toString, "startDate" -> startDate.toString, "sort" -> sort.toString, "filters" -> filters.toString, "currency" -> currency.toString, "includeHistoricalChannelData" -> includeHistoricalChannelData.toString, "metrics" -> metrics.toString, "dimensions" -> dimensions.toString, "endDate" -> endDate.toString, "maxResults" -> maxResults.toString))
			given Conversion[query, Future[Schema.QueryResponse]] = (fun: query) => fun.apply()
		}
	}
}
