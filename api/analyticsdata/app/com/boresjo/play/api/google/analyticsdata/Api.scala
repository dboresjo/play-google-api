package com.boresjo.play.api.google.analyticsdata

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
		"""https://www.googleapis.com/auth/analytics""" /* View and manage your Google Analytics data */,
		"""https://www.googleapis.com/auth/analytics.readonly""" /* See and download your Google Analytics data */
	)

	private val BASE_URL = "https://analyticsdata.googleapis.com/"

	object properties {
		/** Returns metadata for dimensions and metrics available in reporting methods. Used to explore the dimensions and metrics. In this method, a Google Analytics property identifier is specified in the request, and the metadata response includes Custom dimensions and metrics as well as Universal metadata. For example if a custom metric with parameter name `levels_unlocked` is registered to a property, the Metadata response will contain `customEvent:levels_unlocked`. Universal metadata are dimensions and metrics applicable to any property such as `country` and `totalUsers`. */
		class getMetadata(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Metadata]) {
			val scopes = Seq("""https://www.googleapis.com/auth/analytics""", """https://www.googleapis.com/auth/analytics.readonly""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Metadata])
		}
		object getMetadata {
			def apply(propertiesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): getMetadata = new getMetadata(ws.url(BASE_URL + s"v1beta/properties/${propertiesId}/metadata").addQueryStringParameters("name" -> name.toString))
			given Conversion[getMetadata, Future[Schema.Metadata]] = (fun: getMetadata) => fun.apply()
		}
		/** Returns a customized report of realtime event data for your property. Events appear in realtime reports seconds after they have been sent to the Google Analytics. Realtime reports show events and usage data for the periods of time ranging from the present moment to 30 minutes ago (up to 60 minutes for Google Analytics 360 properties). For a guide to constructing realtime requests & understanding responses, see [Creating a Realtime Report](https://developers.google.com/analytics/devguides/reporting/data/v1/realtime-basics). */
		class runRealtimeReport(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/analytics""", """https://www.googleapis.com/auth/analytics.readonly""")
			/** Perform the request */
			def withRunRealtimeReportRequest(body: Schema.RunRealtimeReportRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.RunRealtimeReportResponse])
		}
		object runRealtimeReport {
			def apply(propertiesId :PlayApi, property: String)(using signer: RequestSigner, ec: ExecutionContext): runRealtimeReport = new runRealtimeReport(ws.url(BASE_URL + s"v1beta/properties/${propertiesId}:runRealtimeReport").addQueryStringParameters("property" -> property.toString))
		}
		/** Returns multiple reports in a batch. All reports must be for the same Google Analytics property. */
		class batchRunReports(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/analytics""", """https://www.googleapis.com/auth/analytics.readonly""")
			/** Perform the request */
			def withBatchRunReportsRequest(body: Schema.BatchRunReportsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.BatchRunReportsResponse])
		}
		object batchRunReports {
			def apply(propertiesId :PlayApi, property: String)(using signer: RequestSigner, ec: ExecutionContext): batchRunReports = new batchRunReports(ws.url(BASE_URL + s"v1beta/properties/${propertiesId}:batchRunReports").addQueryStringParameters("property" -> property.toString))
		}
		/** Returns multiple pivot reports in a batch. All reports must be for the same Google Analytics property. */
		class batchRunPivotReports(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/analytics""", """https://www.googleapis.com/auth/analytics.readonly""")
			/** Perform the request */
			def withBatchRunPivotReportsRequest(body: Schema.BatchRunPivotReportsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.BatchRunPivotReportsResponse])
		}
		object batchRunPivotReports {
			def apply(propertiesId :PlayApi, property: String)(using signer: RequestSigner, ec: ExecutionContext): batchRunPivotReports = new batchRunPivotReports(ws.url(BASE_URL + s"v1beta/properties/${propertiesId}:batchRunPivotReports").addQueryStringParameters("property" -> property.toString))
		}
		/** Returns a customized report of your Google Analytics event data. Reports contain statistics derived from data collected by the Google Analytics tracking code. The data returned from the API is as a table with columns for the requested dimensions and metrics. Metrics are individual measurements of user activity on your property, such as active users or event count. Dimensions break down metrics across some common criteria, such as country or event name. For a guide to constructing requests & understanding responses, see [Creating a Report](https://developers.google.com/analytics/devguides/reporting/data/v1/basics). */
		class runReport(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/analytics""", """https://www.googleapis.com/auth/analytics.readonly""")
			/** Perform the request */
			def withRunReportRequest(body: Schema.RunReportRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.RunReportResponse])
		}
		object runReport {
			def apply(propertiesId :PlayApi, property: String)(using signer: RequestSigner, ec: ExecutionContext): runReport = new runReport(ws.url(BASE_URL + s"v1beta/properties/${propertiesId}:runReport").addQueryStringParameters("property" -> property.toString))
		}
		/** Returns a customized pivot report of your Google Analytics event data. Pivot reports are more advanced and expressive formats than regular reports. In a pivot report, dimensions are only visible if they are included in a pivot. Multiple pivots can be specified to further dissect your data. */
		class runPivotReport(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/analytics""", """https://www.googleapis.com/auth/analytics.readonly""")
			/** Perform the request */
			def withRunPivotReportRequest(body: Schema.RunPivotReportRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.RunPivotReportResponse])
		}
		object runPivotReport {
			def apply(propertiesId :PlayApi, property: String)(using signer: RequestSigner, ec: ExecutionContext): runPivotReport = new runPivotReport(ws.url(BASE_URL + s"v1beta/properties/${propertiesId}:runPivotReport").addQueryStringParameters("property" -> property.toString))
		}
		/** This compatibility method lists dimensions and metrics that can be added to a report request and maintain compatibility. This method fails if the request's dimensions and metrics are incompatible. In Google Analytics, reports fail if they request incompatible dimensions and/or metrics; in that case, you will need to remove dimensions and/or metrics from the incompatible report until the report is compatible. The Realtime and Core reports have different compatibility rules. This method checks compatibility for Core reports. */
		class checkCompatibility(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/analytics""", """https://www.googleapis.com/auth/analytics.readonly""")
			/** Perform the request */
			def withCheckCompatibilityRequest(body: Schema.CheckCompatibilityRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.CheckCompatibilityResponse])
		}
		object checkCompatibility {
			def apply(propertiesId :PlayApi, property: String)(using signer: RequestSigner, ec: ExecutionContext): checkCompatibility = new checkCompatibility(ws.url(BASE_URL + s"v1beta/properties/${propertiesId}:checkCompatibility").addQueryStringParameters("property" -> property.toString))
		}
		object audienceExports {
			/** Creates an audience export for later retrieval. This method quickly returns the audience export's resource name and initiates a long running asynchronous request to form an audience export. To export the users in an audience export, first create the audience export through this method and then send the audience resource name to the `QueryAudienceExport` method. See [Creating an Audience Export](https://developers.google.com/analytics/devguides/reporting/data/v1/audience-list-basics) for an introduction to Audience Exports with examples. An audience export is a snapshot of the users currently in the audience at the time of audience export creation. Creating audience exports for one audience on different days will return different results as users enter and exit the audience. Audiences in Google Analytics 4 allow you to segment your users in the ways that are important to your business. To learn more, see https://support.google.com/analytics/answer/9267572. Audience exports contain the users in each audience. Audience Export APIs have some methods at alpha and other methods at beta stability. The intention is to advance methods to beta stability after some feedback and adoption. To give your feedback on this API, complete the [Google Analytics Audience Export API Feedback](https://forms.gle/EeA5u5LW6PEggtCEA) form. */
			class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/analytics""", """https://www.googleapis.com/auth/analytics.readonly""")
				/** Perform the request */
				def withAudienceExport(body: Schema.AudienceExport) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
			}
			object create {
				def apply(propertiesId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1beta/properties/${propertiesId}/audienceExports").addQueryStringParameters("parent" -> parent.toString))
			}
			/** Retrieves an audience export of users. After creating an audience, the users are not immediately available for exporting. First, a request to `CreateAudienceExport` is necessary to create an audience export of users, and then second, this method is used to retrieve the users in the audience export. See [Creating an Audience Export](https://developers.google.com/analytics/devguides/reporting/data/v1/audience-list-basics) for an introduction to Audience Exports with examples. Audiences in Google Analytics 4 allow you to segment your users in the ways that are important to your business. To learn more, see https://support.google.com/analytics/answer/9267572. Audience Export APIs have some methods at alpha and other methods at beta stability. The intention is to advance methods to beta stability after some feedback and adoption. To give your feedback on this API, complete the [Google Analytics Audience Export API Feedback](https://forms.gle/EeA5u5LW6PEggtCEA) form. */
			class query(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/analytics""", """https://www.googleapis.com/auth/analytics.readonly""")
				/** Perform the request */
				def withQueryAudienceExportRequest(body: Schema.QueryAudienceExportRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.QueryAudienceExportResponse])
			}
			object query {
				def apply(propertiesId :PlayApi, audienceExportsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): query = new query(ws.url(BASE_URL + s"v1beta/properties/${propertiesId}/audienceExports/${audienceExportsId}:query").addQueryStringParameters("name" -> name.toString))
			}
			/** Gets configuration metadata about a specific audience export. This method can be used to understand an audience export after it has been created. See [Creating an Audience Export](https://developers.google.com/analytics/devguides/reporting/data/v1/audience-list-basics) for an introduction to Audience Exports with examples. Audience Export APIs have some methods at alpha and other methods at beta stability. The intention is to advance methods to beta stability after some feedback and adoption. To give your feedback on this API, complete the [Google Analytics Audience Export API Feedback](https://forms.gle/EeA5u5LW6PEggtCEA) form. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.AudienceExport]) {
				val scopes = Seq("""https://www.googleapis.com/auth/analytics""", """https://www.googleapis.com/auth/analytics.readonly""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.AudienceExport])
			}
			object get {
				def apply(propertiesId :PlayApi, audienceExportsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1beta/properties/${propertiesId}/audienceExports/${audienceExportsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.AudienceExport]] = (fun: get) => fun.apply()
			}
			/** Lists all audience exports for a property. This method can be used for you to find and reuse existing audience exports rather than creating unnecessary new audience exports. The same audience can have multiple audience exports that represent the export of users that were in an audience on different days. See [Creating an Audience Export](https://developers.google.com/analytics/devguides/reporting/data/v1/audience-list-basics) for an introduction to Audience Exports with examples. Audience Export APIs have some methods at alpha and other methods at beta stability. The intention is to advance methods to beta stability after some feedback and adoption. To give your feedback on this API, complete the [Google Analytics Audience Export API Feedback](https://forms.gle/EeA5u5LW6PEggtCEA) form. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListAudienceExportsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/analytics""", """https://www.googleapis.com/auth/analytics.readonly""")
				/** Optional. The maximum number of audience exports to return. The service may return fewer than this value. If unspecified, at most 200 audience exports will be returned. The maximum value is 1000 (higher values will be coerced to the maximum).<br>Format: int32 */
				def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
				/** Optional. A page token, received from a previous `ListAudienceExports` call. Provide this to retrieve the subsequent page. When paginating, all other parameters provided to `ListAudienceExports` must match the call that provided the page token. */
				def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListAudienceExportsResponse])
			}
			object list {
				def apply(propertiesId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1beta/properties/${propertiesId}/audienceExports").addQueryStringParameters("parent" -> parent.toString))
				given Conversion[list, Future[Schema.ListAudienceExportsResponse]] = (fun: list) => fun.apply()
			}
		}
	}
}
