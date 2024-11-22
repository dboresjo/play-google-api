package com.boresjo.play.api.google.youtubereporting

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
		"""https://www.googleapis.com/auth/yt-analytics-monetary.readonly""" /* View monetary and non-monetary YouTube Analytics reports for your YouTube content */
	)

	private val BASE_URL = "https://youtubereporting.googleapis.com/"

	object jobs {
		/** Creates a job and returns it. */
		class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/yt-analytics-monetary.readonly""", """https://www.googleapis.com/auth/yt-analytics.readonly""")
			/** Perform the request */
			def withJob(body: Schema.Job) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Job])
		}
		object create {
			def apply(onBehalfOfContentOwner: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/jobs").addQueryStringParameters("onBehalfOfContentOwner" -> onBehalfOfContentOwner.toString))
		}
		/** Deletes a job. */
		class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
			val scopes = Seq("""https://www.googleapis.com/auth/yt-analytics-monetary.readonly""", """https://www.googleapis.com/auth/yt-analytics.readonly""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
		}
		object delete {
			def apply(onBehalfOfContentOwner: String, jobId: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/jobs/${jobId}").addQueryStringParameters("onBehalfOfContentOwner" -> onBehalfOfContentOwner.toString))
			given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
		}
		/** Gets a job. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Job]) {
			val scopes = Seq("""https://www.googleapis.com/auth/yt-analytics-monetary.readonly""", """https://www.googleapis.com/auth/yt-analytics.readonly""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Job])
		}
		object get {
			def apply(onBehalfOfContentOwner: String, jobId: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/jobs/${jobId}").addQueryStringParameters("onBehalfOfContentOwner" -> onBehalfOfContentOwner.toString))
			given Conversion[get, Future[Schema.Job]] = (fun: get) => fun.apply()
		}
		/** Lists jobs. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListJobsResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/yt-analytics-monetary.readonly""", """https://www.googleapis.com/auth/yt-analytics.readonly""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListJobsResponse])
		}
		object list {
			def apply(onBehalfOfContentOwner: String, includeSystemManaged: Boolean, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/jobs").addQueryStringParameters("onBehalfOfContentOwner" -> onBehalfOfContentOwner.toString, "includeSystemManaged" -> includeSystemManaged.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
			given Conversion[list, Future[Schema.ListJobsResponse]] = (fun: list) => fun.apply()
		}
		object reports {
			/** Gets the metadata of a specific report. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Report]) {
				val scopes = Seq("""https://www.googleapis.com/auth/yt-analytics-monetary.readonly""", """https://www.googleapis.com/auth/yt-analytics.readonly""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Report])
			}
			object get {
				def apply(onBehalfOfContentOwner: String, jobId: String, reportId: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/jobs/${jobId}/reports/${reportId}").addQueryStringParameters("onBehalfOfContentOwner" -> onBehalfOfContentOwner.toString))
				given Conversion[get, Future[Schema.Report]] = (fun: get) => fun.apply()
			}
			/** Lists reports created by a specific job. Returns NOT_FOUND if the job does not exist. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListReportsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/yt-analytics-monetary.readonly""", """https://www.googleapis.com/auth/yt-analytics.readonly""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListReportsResponse])
			}
			object list {
				def apply(createdAfter: String, jobId: String, pageToken: String, startTimeAtOrAfter: String, onBehalfOfContentOwner: String, pageSize: Int, startTimeBefore: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/jobs/${jobId}/reports").addQueryStringParameters("createdAfter" -> createdAfter.toString, "pageToken" -> pageToken.toString, "startTimeAtOrAfter" -> startTimeAtOrAfter.toString, "onBehalfOfContentOwner" -> onBehalfOfContentOwner.toString, "pageSize" -> pageSize.toString, "startTimeBefore" -> startTimeBefore.toString))
				given Conversion[list, Future[Schema.ListReportsResponse]] = (fun: list) => fun.apply()
			}
		}
	}
	object reportTypes {
		/** Lists report types. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListReportTypesResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/yt-analytics-monetary.readonly""", """https://www.googleapis.com/auth/yt-analytics.readonly""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListReportTypesResponse])
		}
		object list {
			def apply(onBehalfOfContentOwner: String, includeSystemManaged: Boolean, pageToken: String, pageSize: Int)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/reportTypes").addQueryStringParameters("onBehalfOfContentOwner" -> onBehalfOfContentOwner.toString, "includeSystemManaged" -> includeSystemManaged.toString, "pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString))
			given Conversion[list, Future[Schema.ListReportTypesResponse]] = (fun: list) => fun.apply()
		}
	}
	object media {
		/** Method for media download. Download is supported on the URI `/v1/media/{+name}?alt=media`. */
		class download(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GdataMedia]) {
			val scopes = Seq("""https://www.googleapis.com/auth/yt-analytics-monetary.readonly""", """https://www.googleapis.com/auth/yt-analytics.readonly""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GdataMedia])
		}
		object download {
			def apply(mediaId :PlayApi, resourceName: String)(using signer: RequestSigner, ec: ExecutionContext): download = new download(ws.url(BASE_URL + s"v1/media/${mediaId}").addQueryStringParameters("resourceName" -> resourceName.toString))
			given Conversion[download, Future[Schema.GdataMedia]] = (fun: download) => fun.apply()
		}
	}
}
