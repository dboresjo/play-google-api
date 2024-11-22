package com.boresjo.play.api.google.analyticsdata

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://analyticsdata.googleapis.com/"

	object properties {
		class getMetadata(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Metadata]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Metadata])
		}
		object getMetadata {
			def apply(propertiesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): getMetadata = new getMetadata(ws.url(BASE_URL + s"v1beta/properties/${propertiesId}/metadata").addQueryStringParameters("name" -> name.toString))
			given Conversion[getMetadata, Future[Schema.Metadata]] = (fun: getMetadata) => fun.apply()
		}
		class runRealtimeReport(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withRunRealtimeReportRequest(body: Schema.RunRealtimeReportRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.RunRealtimeReportResponse])
		}
		object runRealtimeReport {
			def apply(propertiesId :PlayApi, property: String)(using auth: AuthToken, ec: ExecutionContext): runRealtimeReport = new runRealtimeReport(ws.url(BASE_URL + s"v1beta/properties/${propertiesId}:runRealtimeReport").addQueryStringParameters("property" -> property.toString))
		}
		class batchRunReports(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withBatchRunReportsRequest(body: Schema.BatchRunReportsRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.BatchRunReportsResponse])
		}
		object batchRunReports {
			def apply(propertiesId :PlayApi, property: String)(using auth: AuthToken, ec: ExecutionContext): batchRunReports = new batchRunReports(ws.url(BASE_URL + s"v1beta/properties/${propertiesId}:batchRunReports").addQueryStringParameters("property" -> property.toString))
		}
		class batchRunPivotReports(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withBatchRunPivotReportsRequest(body: Schema.BatchRunPivotReportsRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.BatchRunPivotReportsResponse])
		}
		object batchRunPivotReports {
			def apply(propertiesId :PlayApi, property: String)(using auth: AuthToken, ec: ExecutionContext): batchRunPivotReports = new batchRunPivotReports(ws.url(BASE_URL + s"v1beta/properties/${propertiesId}:batchRunPivotReports").addQueryStringParameters("property" -> property.toString))
		}
		class runReport(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withRunReportRequest(body: Schema.RunReportRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.RunReportResponse])
		}
		object runReport {
			def apply(propertiesId :PlayApi, property: String)(using auth: AuthToken, ec: ExecutionContext): runReport = new runReport(ws.url(BASE_URL + s"v1beta/properties/${propertiesId}:runReport").addQueryStringParameters("property" -> property.toString))
		}
		class runPivotReport(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withRunPivotReportRequest(body: Schema.RunPivotReportRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.RunPivotReportResponse])
		}
		object runPivotReport {
			def apply(propertiesId :PlayApi, property: String)(using auth: AuthToken, ec: ExecutionContext): runPivotReport = new runPivotReport(ws.url(BASE_URL + s"v1beta/properties/${propertiesId}:runPivotReport").addQueryStringParameters("property" -> property.toString))
		}
		class checkCompatibility(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withCheckCompatibilityRequest(body: Schema.CheckCompatibilityRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.CheckCompatibilityResponse])
		}
		object checkCompatibility {
			def apply(propertiesId :PlayApi, property: String)(using auth: AuthToken, ec: ExecutionContext): checkCompatibility = new checkCompatibility(ws.url(BASE_URL + s"v1beta/properties/${propertiesId}:checkCompatibility").addQueryStringParameters("property" -> property.toString))
		}
		object audienceExports {
			class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withAudienceExport(body: Schema.AudienceExport) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
			}
			object create {
				def apply(propertiesId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1beta/properties/${propertiesId}/audienceExports").addQueryStringParameters("parent" -> parent.toString))
			}
			class query(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withQueryAudienceExportRequest(body: Schema.QueryAudienceExportRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.QueryAudienceExportResponse])
			}
			object query {
				def apply(propertiesId :PlayApi, audienceExportsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): query = new query(ws.url(BASE_URL + s"v1beta/properties/${propertiesId}/audienceExports/${audienceExportsId}:query").addQueryStringParameters("name" -> name.toString))
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.AudienceExport]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.AudienceExport])
			}
			object get {
				def apply(propertiesId :PlayApi, audienceExportsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1beta/properties/${propertiesId}/audienceExports/${audienceExportsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.AudienceExport]] = (fun: get) => fun.apply()
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListAudienceExportsResponse]) {
				/** Optional. The maximum number of audience exports to return. The service may return fewer than this value. If unspecified, at most 200 audience exports will be returned. The maximum value is 1000 (higher values will be coerced to the maximum).<br>Format: int32 */
				def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
				/** Optional. A page token, received from a previous `ListAudienceExports` call. Provide this to retrieve the subsequent page. When paginating, all other parameters provided to `ListAudienceExports` must match the call that provided the page token. */
				def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListAudienceExportsResponse])
			}
			object list {
				def apply(propertiesId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1beta/properties/${propertiesId}/audienceExports").addQueryStringParameters("parent" -> parent.toString))
				given Conversion[list, Future[Schema.ListAudienceExportsResponse]] = (fun: list) => fun.apply()
			}
		}
	}
}
