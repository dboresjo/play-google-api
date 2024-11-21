package com.boresjo.play.api.google.playdeveloperreporting

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://playdeveloperreporting.googleapis.com/"

	object apps {
		class search(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GooglePlayDeveloperReportingV1beta1SearchAccessibleAppsResponse]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.GooglePlayDeveloperReportingV1beta1SearchAccessibleAppsResponse])
		}
		object search {
			def apply(pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): search = new search(auth(ws.url(BASE_URL + s"v1beta1/apps:search")).addQueryStringParameters("pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
			given Conversion[search, Future[Schema.GooglePlayDeveloperReportingV1beta1SearchAccessibleAppsResponse]] = (fun: search) => fun.apply()
		}
		class fetchReleaseFilterOptions(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GooglePlayDeveloperReportingV1beta1ReleaseFilterOptions]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.GooglePlayDeveloperReportingV1beta1ReleaseFilterOptions])
		}
		object fetchReleaseFilterOptions {
			def apply(appsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): fetchReleaseFilterOptions = new fetchReleaseFilterOptions(auth(ws.url(BASE_URL + s"v1beta1/apps/${appsId}:fetchReleaseFilterOptions")).addQueryStringParameters("name" -> name.toString))
			given Conversion[fetchReleaseFilterOptions, Future[Schema.GooglePlayDeveloperReportingV1beta1ReleaseFilterOptions]] = (fun: fetchReleaseFilterOptions) => fun.apply()
		}
	}
	object vitals {
		object anrrate {
			class query(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGooglePlayDeveloperReportingV1beta1QueryAnrRateMetricSetRequest(body: Schema.GooglePlayDeveloperReportingV1beta1QueryAnrRateMetricSetRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GooglePlayDeveloperReportingV1beta1QueryAnrRateMetricSetResponse])
			}
			object query {
				def apply(appsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): query = new query(auth(ws.url(BASE_URL + s"v1beta1/apps/${appsId}/anrRateMetricSet:query")).addQueryStringParameters("name" -> name.toString))
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GooglePlayDeveloperReportingV1beta1AnrRateMetricSet]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.GooglePlayDeveloperReportingV1beta1AnrRateMetricSet])
			}
			object get {
				def apply(appsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1beta1/apps/${appsId}/anrRateMetricSet")).addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.GooglePlayDeveloperReportingV1beta1AnrRateMetricSet]] = (fun: get) => fun.apply()
			}
		}
		object stuckbackgroundwakelockrate {
			class query(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGooglePlayDeveloperReportingV1beta1QueryStuckBackgroundWakelockRateMetricSetRequest(body: Schema.GooglePlayDeveloperReportingV1beta1QueryStuckBackgroundWakelockRateMetricSetRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GooglePlayDeveloperReportingV1beta1QueryStuckBackgroundWakelockRateMetricSetResponse])
			}
			object query {
				def apply(appsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): query = new query(auth(ws.url(BASE_URL + s"v1beta1/apps/${appsId}/stuckBackgroundWakelockRateMetricSet:query")).addQueryStringParameters("name" -> name.toString))
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GooglePlayDeveloperReportingV1beta1StuckBackgroundWakelockRateMetricSet]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.GooglePlayDeveloperReportingV1beta1StuckBackgroundWakelockRateMetricSet])
			}
			object get {
				def apply(appsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1beta1/apps/${appsId}/stuckBackgroundWakelockRateMetricSet")).addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.GooglePlayDeveloperReportingV1beta1StuckBackgroundWakelockRateMetricSet]] = (fun: get) => fun.apply()
			}
		}
		object errors {
			object counts {
				class query(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGooglePlayDeveloperReportingV1beta1QueryErrorCountMetricSetRequest(body: Schema.GooglePlayDeveloperReportingV1beta1QueryErrorCountMetricSetRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GooglePlayDeveloperReportingV1beta1QueryErrorCountMetricSetResponse])
				}
				object query {
					def apply(appsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): query = new query(auth(ws.url(BASE_URL + s"v1beta1/apps/${appsId}/errorCountMetricSet:query")).addQueryStringParameters("name" -> name.toString))
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GooglePlayDeveloperReportingV1beta1ErrorCountMetricSet]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.GooglePlayDeveloperReportingV1beta1ErrorCountMetricSet])
				}
				object get {
					def apply(appsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1beta1/apps/${appsId}/errorCountMetricSet")).addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GooglePlayDeveloperReportingV1beta1ErrorCountMetricSet]] = (fun: get) => fun.apply()
				}
			}
			object reports {
				class search(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GooglePlayDeveloperReportingV1beta1SearchErrorReportsResponse]) {
					/** Optional. Year of date. Must be from 1 to 9999, or 0 if specifying a datetime without a year.<br>Format: int32 */
					def withIntervalEndTimeYear(intervalEndTimeYear: Int) = new search(req.addQueryStringParameters("interval.endTime.year" -> intervalEndTimeYear.toString))
					/** Optional. Minutes of hour of day. Must be from 0 to 59, defaults to 0.<br>Format: int32 */
					def withIntervalEndTimeMinutes(intervalEndTimeMinutes: Int) = new search(req.addQueryStringParameters("interval.endTime.minutes" -> intervalEndTimeMinutes.toString))
					/** Optional. IANA Time Zone Database version number, e.g. "2019a". */
					def withIntervalStartTimeTimeZoneVersion(intervalStartTimeTimeZoneVersion: String) = new search(req.addQueryStringParameters("interval.startTime.timeZone.version" -> intervalStartTimeTimeZoneVersion.toString))
					/** Optional. Day of month. Must be from 1 to 31 and valid for the year and month, or 0 if specifying a datetime without a day.<br>Format: int32 */
					def withIntervalStartTimeDay(intervalStartTimeDay: Int) = new search(req.addQueryStringParameters("interval.startTime.day" -> intervalStartTimeDay.toString))
					/** Optional. Seconds of minutes of the time. Must normally be from 0 to 59, defaults to 0. An API may allow the value 60 if it allows leap-seconds.<br>Format: int32 */
					def withIntervalEndTimeSeconds(intervalEndTimeSeconds: Int) = new search(req.addQueryStringParameters("interval.endTime.seconds" -> intervalEndTimeSeconds.toString))
					/** Optional. Day of month. Must be from 1 to 31 and valid for the year and month, or 0 if specifying a datetime without a day.<br>Format: int32 */
					def withIntervalEndTimeDay(intervalEndTimeDay: Int) = new search(req.addQueryStringParameters("interval.endTime.day" -> intervalEndTimeDay.toString))
					/** Optional. Minutes of hour of day. Must be from 0 to 59, defaults to 0.<br>Format: int32 */
					def withIntervalStartTimeMinutes(intervalStartTimeMinutes: Int) = new search(req.addQueryStringParameters("interval.startTime.minutes" -> intervalStartTimeMinutes.toString))
					/** Optional. Hours of day in 24 hour format. Should be from 0 to 23, defaults to 0 (midnight). An API may choose to allow the value "24:00:00" for scenarios like business closing time.<br>Format: int32 */
					def withIntervalEndTimeHours(intervalEndTimeHours: Int) = new search(req.addQueryStringParameters("interval.endTime.hours" -> intervalEndTimeHours.toString))
					/** Optional. IANA Time Zone Database version number, e.g. "2019a". */
					def withIntervalEndTimeTimeZoneVersion(intervalEndTimeTimeZoneVersion: String) = new search(req.addQueryStringParameters("interval.endTime.timeZone.version" -> intervalEndTimeTimeZoneVersion.toString))
					/** Optional. Year of date. Must be from 1 to 9999, or 0 if specifying a datetime without a year.<br>Format: int32 */
					def withIntervalStartTimeYear(intervalStartTimeYear: Int) = new search(req.addQueryStringParameters("interval.startTime.year" -> intervalStartTimeYear.toString))
					/** Optional. Month of year. Must be from 1 to 12, or 0 if specifying a datetime without a month.<br>Format: int32 */
					def withIntervalEndTimeMonth(intervalEndTimeMonth: Int) = new search(req.addQueryStringParameters("interval.endTime.month" -> intervalEndTimeMonth.toString))
					/** Optional. Hours of day in 24 hour format. Should be from 0 to 23, defaults to 0 (midnight). An API may choose to allow the value "24:00:00" for scenarios like business closing time.<br>Format: int32 */
					def withIntervalStartTimeHours(intervalStartTimeHours: Int) = new search(req.addQueryStringParameters("interval.startTime.hours" -> intervalStartTimeHours.toString))
					/** Optional. Seconds of minutes of the time. Must normally be from 0 to 59, defaults to 0. An API may allow the value 60 if it allows leap-seconds.<br>Format: int32 */
					def withIntervalStartTimeSeconds(intervalStartTimeSeconds: Int) = new search(req.addQueryStringParameters("interval.startTime.seconds" -> intervalStartTimeSeconds.toString))
					/** Optional. Fractions of seconds in nanoseconds. Must be from 0 to 999,999,999, defaults to 0.<br>Format: int32 */
					def withIntervalEndTimeNanos(intervalEndTimeNanos: Int) = new search(req.addQueryStringParameters("interval.endTime.nanos" -> intervalEndTimeNanos.toString))
					/** Optional. Month of year. Must be from 1 to 12, or 0 if specifying a datetime without a month.<br>Format: int32 */
					def withIntervalStartTimeMonth(intervalStartTimeMonth: Int) = new search(req.addQueryStringParameters("interval.startTime.month" -> intervalStartTimeMonth.toString))
					/** Optional. Fractions of seconds in nanoseconds. Must be from 0 to 999,999,999, defaults to 0.<br>Format: int32 */
					def withIntervalStartTimeNanos(intervalStartTimeNanos: Int) = new search(req.addQueryStringParameters("interval.startTime.nanos" -> intervalStartTimeNanos.toString))
					def apply() = req.execute("GET").map(_.json.as[Schema.GooglePlayDeveloperReportingV1beta1SearchErrorReportsResponse])
				}
				object search {
					def apply(appsId :PlayApi, intervalStartTimeTimeZoneId: String, intervalStartTimeUtcOffset: String, intervalEndTimeUtcOffset: String, intervalEndTimeTimeZoneId: String, filter: String, pageToken: String, parent: String, pageSize: Int)(using auth: AuthToken, ec: ExecutionContext): search = new search(auth(ws.url(BASE_URL + s"v1beta1/apps/${appsId}/errorReports:search")).addQueryStringParameters("interval.startTime.timeZone.id" -> intervalStartTimeTimeZoneId.toString, "interval.startTime.utcOffset" -> intervalStartTimeUtcOffset.toString, "interval.endTime.utcOffset" -> intervalEndTimeUtcOffset.toString, "interval.endTime.timeZone.id" -> intervalEndTimeTimeZoneId.toString, "filter" -> filter.toString, "pageToken" -> pageToken.toString, "parent" -> parent.toString, "pageSize" -> pageSize.toString))
					given Conversion[search, Future[Schema.GooglePlayDeveloperReportingV1beta1SearchErrorReportsResponse]] = (fun: search) => fun.apply()
				}
			}
			object issues {
				class search(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GooglePlayDeveloperReportingV1beta1SearchErrorIssuesResponse]) {
					/** Optional. Month of year. Must be from 1 to 12, or 0 if specifying a datetime without a month.<br>Format: int32 */
					def withIntervalEndTimeMonth(intervalEndTimeMonth: Int) = new search(req.addQueryStringParameters("interval.endTime.month" -> intervalEndTimeMonth.toString))
					/** Optional. Year of date. Must be from 1 to 9999, or 0 if specifying a datetime without a year.<br>Format: int32 */
					def withIntervalEndTimeYear(intervalEndTimeYear: Int) = new search(req.addQueryStringParameters("interval.endTime.year" -> intervalEndTimeYear.toString))
					/** Optional. Year of date. Must be from 1 to 9999, or 0 if specifying a datetime without a year.<br>Format: int32 */
					def withIntervalStartTimeYear(intervalStartTimeYear: Int) = new search(req.addQueryStringParameters("interval.startTime.year" -> intervalStartTimeYear.toString))
					/** Optional. Hours of day in 24 hour format. Should be from 0 to 23, defaults to 0 (midnight). An API may choose to allow the value "24:00:00" for scenarios like business closing time.<br>Format: int32 */
					def withIntervalStartTimeHours(intervalStartTimeHours: Int) = new search(req.addQueryStringParameters("interval.startTime.hours" -> intervalStartTimeHours.toString))
					/** Optional. IANA Time Zone Database version number, e.g. "2019a". */
					def withIntervalStartTimeTimeZoneVersion(intervalStartTimeTimeZoneVersion: String) = new search(req.addQueryStringParameters("interval.startTime.timeZone.version" -> intervalStartTimeTimeZoneVersion.toString))
					/** Optional. Day of month. Must be from 1 to 31 and valid for the year and month, or 0 if specifying a datetime without a day.<br>Format: int32 */
					def withIntervalStartTimeDay(intervalStartTimeDay: Int) = new search(req.addQueryStringParameters("interval.startTime.day" -> intervalStartTimeDay.toString))
					/** Optional. Seconds of minutes of the time. Must normally be from 0 to 59, defaults to 0. An API may allow the value 60 if it allows leap-seconds.<br>Format: int32 */
					def withIntervalEndTimeSeconds(intervalEndTimeSeconds: Int) = new search(req.addQueryStringParameters("interval.endTime.seconds" -> intervalEndTimeSeconds.toString))
					/** Optional. Fractions of seconds in nanoseconds. Must be from 0 to 999,999,999, defaults to 0.<br>Format: int32 */
					def withIntervalStartTimeNanos(intervalStartTimeNanos: Int) = new search(req.addQueryStringParameters("interval.startTime.nanos" -> intervalStartTimeNanos.toString))
					/** Optional. Day of month. Must be from 1 to 31 and valid for the year and month, or 0 if specifying a datetime without a day.<br>Format: int32 */
					def withIntervalEndTimeDay(intervalEndTimeDay: Int) = new search(req.addQueryStringParameters("interval.endTime.day" -> intervalEndTimeDay.toString))
					/** Optional. Seconds of minutes of the time. Must normally be from 0 to 59, defaults to 0. An API may allow the value 60 if it allows leap-seconds.<br>Format: int32 */
					def withIntervalStartTimeSeconds(intervalStartTimeSeconds: Int) = new search(req.addQueryStringParameters("interval.startTime.seconds" -> intervalStartTimeSeconds.toString))
					/** Optional. Number of sample error reports to return per ErrorIssue. If unspecified, 0 will be used. &#42;Note:&#42; currently only 0 and 1 are supported.<br>Format: int32 */
					def withSampleErrorReportLimit(sampleErrorReportLimit: Int) = new search(req.addQueryStringParameters("sampleErrorReportLimit" -> sampleErrorReportLimit.toString))
					/** Optional. Minutes of hour of day. Must be from 0 to 59, defaults to 0.<br>Format: int32 */
					def withIntervalEndTimeMinutes(intervalEndTimeMinutes: Int) = new search(req.addQueryStringParameters("interval.endTime.minutes" -> intervalEndTimeMinutes.toString))
					/** Optional. IANA Time Zone Database version number, e.g. "2019a". */
					def withIntervalEndTimeTimeZoneVersion(intervalEndTimeTimeZoneVersion: String) = new search(req.addQueryStringParameters("interval.endTime.timeZone.version" -> intervalEndTimeTimeZoneVersion.toString))
					/** Optional. Minutes of hour of day. Must be from 0 to 59, defaults to 0.<br>Format: int32 */
					def withIntervalStartTimeMinutes(intervalStartTimeMinutes: Int) = new search(req.addQueryStringParameters("interval.startTime.minutes" -> intervalStartTimeMinutes.toString))
					/** Optional. Fractions of seconds in nanoseconds. Must be from 0 to 999,999,999, defaults to 0.<br>Format: int32 */
					def withIntervalEndTimeNanos(intervalEndTimeNanos: Int) = new search(req.addQueryStringParameters("interval.endTime.nanos" -> intervalEndTimeNanos.toString))
					/** Optional. Month of year. Must be from 1 to 12, or 0 if specifying a datetime without a month.<br>Format: int32 */
					def withIntervalStartTimeMonth(intervalStartTimeMonth: Int) = new search(req.addQueryStringParameters("interval.startTime.month" -> intervalStartTimeMonth.toString))
					/** Optional. Hours of day in 24 hour format. Should be from 0 to 23, defaults to 0 (midnight). An API may choose to allow the value "24:00:00" for scenarios like business closing time.<br>Format: int32 */
					def withIntervalEndTimeHours(intervalEndTimeHours: Int) = new search(req.addQueryStringParameters("interval.endTime.hours" -> intervalEndTimeHours.toString))
					def apply() = req.execute("GET").map(_.json.as[Schema.GooglePlayDeveloperReportingV1beta1SearchErrorIssuesResponse])
				}
				object search {
					def apply(appsId :PlayApi, parent: String, pageSize: Int, intervalEndTimeUtcOffset: String, pageToken: String, intervalStartTimeUtcOffset: String, filter: String, orderBy: String, intervalStartTimeTimeZoneId: String, intervalEndTimeTimeZoneId: String)(using auth: AuthToken, ec: ExecutionContext): search = new search(auth(ws.url(BASE_URL + s"v1beta1/apps/${appsId}/errorIssues:search")).addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "interval.endTime.utcOffset" -> intervalEndTimeUtcOffset.toString, "pageToken" -> pageToken.toString, "interval.startTime.utcOffset" -> intervalStartTimeUtcOffset.toString, "filter" -> filter.toString, "orderBy" -> orderBy.toString, "interval.startTime.timeZone.id" -> intervalStartTimeTimeZoneId.toString, "interval.endTime.timeZone.id" -> intervalEndTimeTimeZoneId.toString))
					given Conversion[search, Future[Schema.GooglePlayDeveloperReportingV1beta1SearchErrorIssuesResponse]] = (fun: search) => fun.apply()
				}
			}
		}
		object excessivewakeuprate {
			class query(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGooglePlayDeveloperReportingV1beta1QueryExcessiveWakeupRateMetricSetRequest(body: Schema.GooglePlayDeveloperReportingV1beta1QueryExcessiveWakeupRateMetricSetRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GooglePlayDeveloperReportingV1beta1QueryExcessiveWakeupRateMetricSetResponse])
			}
			object query {
				def apply(appsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): query = new query(auth(ws.url(BASE_URL + s"v1beta1/apps/${appsId}/excessiveWakeupRateMetricSet:query")).addQueryStringParameters("name" -> name.toString))
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GooglePlayDeveloperReportingV1beta1ExcessiveWakeupRateMetricSet]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.GooglePlayDeveloperReportingV1beta1ExcessiveWakeupRateMetricSet])
			}
			object get {
				def apply(appsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1beta1/apps/${appsId}/excessiveWakeupRateMetricSet")).addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.GooglePlayDeveloperReportingV1beta1ExcessiveWakeupRateMetricSet]] = (fun: get) => fun.apply()
			}
		}
		object slowrenderingrate {
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GooglePlayDeveloperReportingV1beta1SlowRenderingRateMetricSet]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.GooglePlayDeveloperReportingV1beta1SlowRenderingRateMetricSet])
			}
			object get {
				def apply(appsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1beta1/apps/${appsId}/slowRenderingRateMetricSet")).addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.GooglePlayDeveloperReportingV1beta1SlowRenderingRateMetricSet]] = (fun: get) => fun.apply()
			}
			class query(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGooglePlayDeveloperReportingV1beta1QuerySlowRenderingRateMetricSetRequest(body: Schema.GooglePlayDeveloperReportingV1beta1QuerySlowRenderingRateMetricSetRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GooglePlayDeveloperReportingV1beta1QuerySlowRenderingRateMetricSetResponse])
			}
			object query {
				def apply(appsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): query = new query(auth(ws.url(BASE_URL + s"v1beta1/apps/${appsId}/slowRenderingRateMetricSet:query")).addQueryStringParameters("name" -> name.toString))
			}
		}
		object crashrate {
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GooglePlayDeveloperReportingV1beta1CrashRateMetricSet]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.GooglePlayDeveloperReportingV1beta1CrashRateMetricSet])
			}
			object get {
				def apply(appsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1beta1/apps/${appsId}/crashRateMetricSet")).addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.GooglePlayDeveloperReportingV1beta1CrashRateMetricSet]] = (fun: get) => fun.apply()
			}
			class query(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGooglePlayDeveloperReportingV1beta1QueryCrashRateMetricSetRequest(body: Schema.GooglePlayDeveloperReportingV1beta1QueryCrashRateMetricSetRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GooglePlayDeveloperReportingV1beta1QueryCrashRateMetricSetResponse])
			}
			object query {
				def apply(appsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): query = new query(auth(ws.url(BASE_URL + s"v1beta1/apps/${appsId}/crashRateMetricSet:query")).addQueryStringParameters("name" -> name.toString))
			}
		}
		object slowstartrate {
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GooglePlayDeveloperReportingV1beta1SlowStartRateMetricSet]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.GooglePlayDeveloperReportingV1beta1SlowStartRateMetricSet])
			}
			object get {
				def apply(appsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1beta1/apps/${appsId}/slowStartRateMetricSet")).addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.GooglePlayDeveloperReportingV1beta1SlowStartRateMetricSet]] = (fun: get) => fun.apply()
			}
			class query(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGooglePlayDeveloperReportingV1beta1QuerySlowStartRateMetricSetRequest(body: Schema.GooglePlayDeveloperReportingV1beta1QuerySlowStartRateMetricSetRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GooglePlayDeveloperReportingV1beta1QuerySlowStartRateMetricSetResponse])
			}
			object query {
				def apply(appsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): query = new query(auth(ws.url(BASE_URL + s"v1beta1/apps/${appsId}/slowStartRateMetricSet:query")).addQueryStringParameters("name" -> name.toString))
			}
		}
	}
	object anomalies {
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GooglePlayDeveloperReportingV1beta1ListAnomaliesResponse]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.GooglePlayDeveloperReportingV1beta1ListAnomaliesResponse])
		}
		object list {
			def apply(appsId :PlayApi, parent: String, filter: String, pageToken: String, pageSize: Int)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1beta1/apps/${appsId}/anomalies")).addQueryStringParameters("parent" -> parent.toString, "filter" -> filter.toString, "pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString))
			given Conversion[list, Future[Schema.GooglePlayDeveloperReportingV1beta1ListAnomaliesResponse]] = (fun: list) => fun.apply()
		}
	}
}
