package com.boresjo.play.api.google.youtubeAnalytics

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://youtubeanalytics.googleapis.com/"

	object groups {
		class insert(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withGroup(body: Schema.Group) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Group])
		}
		object insert {
			def apply(onBehalfOfContentOwner: String)(using auth: AuthToken, ec: ExecutionContext): insert = new insert(ws.url(BASE_URL + s"v2/groups").addQueryStringParameters("onBehalfOfContentOwner" -> onBehalfOfContentOwner.toString))
		}
		class update(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withGroup(body: Schema.Group) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.Group])
		}
		object update {
			def apply(onBehalfOfContentOwner: String)(using auth: AuthToken, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"v2/groups").addQueryStringParameters("onBehalfOfContentOwner" -> onBehalfOfContentOwner.toString))
		}
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListGroupsResponse]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListGroupsResponse])
		}
		object list {
			def apply(mine: Boolean, id: String, onBehalfOfContentOwner: String, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/groups").addQueryStringParameters("mine" -> mine.toString, "id" -> id.toString, "onBehalfOfContentOwner" -> onBehalfOfContentOwner.toString, "pageToken" -> pageToken.toString))
			given Conversion[list, Future[Schema.ListGroupsResponse]] = (fun: list) => fun.apply()
		}
		class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.EmptyResponse]) {
			def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.EmptyResponse])
		}
		object delete {
			def apply(id: String, onBehalfOfContentOwner: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v2/groups").addQueryStringParameters("id" -> id.toString, "onBehalfOfContentOwner" -> onBehalfOfContentOwner.toString))
			given Conversion[delete, Future[Schema.EmptyResponse]] = (fun: delete) => fun.apply()
		}
	}
	object groupItems {
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListGroupItemsResponse]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListGroupItemsResponse])
		}
		object list {
			def apply(onBehalfOfContentOwner: String, groupId: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/groupItems").addQueryStringParameters("onBehalfOfContentOwner" -> onBehalfOfContentOwner.toString, "groupId" -> groupId.toString))
			given Conversion[list, Future[Schema.ListGroupItemsResponse]] = (fun: list) => fun.apply()
		}
		class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.EmptyResponse]) {
			def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.EmptyResponse])
		}
		object delete {
			def apply(id: String, onBehalfOfContentOwner: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v2/groupItems").addQueryStringParameters("id" -> id.toString, "onBehalfOfContentOwner" -> onBehalfOfContentOwner.toString))
			given Conversion[delete, Future[Schema.EmptyResponse]] = (fun: delete) => fun.apply()
		}
		class insert(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withGroupItem(body: Schema.GroupItem) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GroupItem])
		}
		object insert {
			def apply(onBehalfOfContentOwner: String)(using auth: AuthToken, ec: ExecutionContext): insert = new insert(ws.url(BASE_URL + s"v2/groupItems").addQueryStringParameters("onBehalfOfContentOwner" -> onBehalfOfContentOwner.toString))
		}
	}
	object reports {
		class query(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.QueryResponse]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.QueryResponse])
		}
		object query {
			def apply(startIndex: Int, ids: String, startDate: String, sort: String, filters: String, currency: String, includeHistoricalChannelData: Boolean, metrics: String, dimensions: String, endDate: String, maxResults: Int)(using auth: AuthToken, ec: ExecutionContext): query = new query(ws.url(BASE_URL + s"v2/reports").addQueryStringParameters("startIndex" -> startIndex.toString, "ids" -> ids.toString, "startDate" -> startDate.toString, "sort" -> sort.toString, "filters" -> filters.toString, "currency" -> currency.toString, "includeHistoricalChannelData" -> includeHistoricalChannelData.toString, "metrics" -> metrics.toString, "dimensions" -> dimensions.toString, "endDate" -> endDate.toString, "maxResults" -> maxResults.toString))
			given Conversion[query, Future[Schema.QueryResponse]] = (fun: query) => fun.apply()
		}
	}
}
