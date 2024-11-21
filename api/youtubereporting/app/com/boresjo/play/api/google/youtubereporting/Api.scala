package com.boresjo.play.api.google.youtubereporting

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://youtubereporting.googleapis.com/"

	object jobs {
		class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withJob(body: Schema.Job) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Job])
		}
		object create {
			def apply(onBehalfOfContentOwner: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/jobs")).addQueryStringParameters("onBehalfOfContentOwner" -> onBehalfOfContentOwner.toString))
		}
		class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
			def apply() = req.execute("DELETE").map(_.json.as[Schema.Empty])
		}
		object delete {
			def apply(onBehalfOfContentOwner: String, jobId: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/jobs/${jobId}")).addQueryStringParameters("onBehalfOfContentOwner" -> onBehalfOfContentOwner.toString))
			given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
		}
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Job]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.Job])
		}
		object get {
			def apply(onBehalfOfContentOwner: String, jobId: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/jobs/${jobId}")).addQueryStringParameters("onBehalfOfContentOwner" -> onBehalfOfContentOwner.toString))
			given Conversion[get, Future[Schema.Job]] = (fun: get) => fun.apply()
		}
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListJobsResponse]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.ListJobsResponse])
		}
		object list {
			def apply(onBehalfOfContentOwner: String, includeSystemManaged: Boolean, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/jobs")).addQueryStringParameters("onBehalfOfContentOwner" -> onBehalfOfContentOwner.toString, "includeSystemManaged" -> includeSystemManaged.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
			given Conversion[list, Future[Schema.ListJobsResponse]] = (fun: list) => fun.apply()
		}
		object reports {
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Report]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.Report])
			}
			object get {
				def apply(onBehalfOfContentOwner: String, jobId: String, reportId: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/jobs/${jobId}/reports/${reportId}")).addQueryStringParameters("onBehalfOfContentOwner" -> onBehalfOfContentOwner.toString))
				given Conversion[get, Future[Schema.Report]] = (fun: get) => fun.apply()
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListReportsResponse]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.ListReportsResponse])
			}
			object list {
				def apply(createdAfter: String, jobId: String, pageToken: String, startTimeAtOrAfter: String, onBehalfOfContentOwner: String, pageSize: Int, startTimeBefore: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/jobs/${jobId}/reports")).addQueryStringParameters("createdAfter" -> createdAfter.toString, "pageToken" -> pageToken.toString, "startTimeAtOrAfter" -> startTimeAtOrAfter.toString, "onBehalfOfContentOwner" -> onBehalfOfContentOwner.toString, "pageSize" -> pageSize.toString, "startTimeBefore" -> startTimeBefore.toString))
				given Conversion[list, Future[Schema.ListReportsResponse]] = (fun: list) => fun.apply()
			}
		}
	}
	object reportTypes {
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListReportTypesResponse]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.ListReportTypesResponse])
		}
		object list {
			def apply(onBehalfOfContentOwner: String, includeSystemManaged: Boolean, pageToken: String, pageSize: Int)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/reportTypes")).addQueryStringParameters("onBehalfOfContentOwner" -> onBehalfOfContentOwner.toString, "includeSystemManaged" -> includeSystemManaged.toString, "pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString))
			given Conversion[list, Future[Schema.ListReportTypesResponse]] = (fun: list) => fun.apply()
		}
	}
	object media {
		class download(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GdataMedia]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.GdataMedia])
		}
		object download {
			def apply(mediaId :PlayApi, resourceName: String)(using auth: AuthToken, ec: ExecutionContext): download = new download(auth(ws.url(BASE_URL + s"v1/media/${mediaId}")).addQueryStringParameters("resourceName" -> resourceName.toString))
			given Conversion[download, Future[Schema.GdataMedia]] = (fun: download) => fun.apply()
		}
	}
}
