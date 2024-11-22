package com.boresjo.play.api.google.businessprofileperformance

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

	private val BASE_URL = "https://businessprofileperformance.googleapis.com/"

	object locations {
		/**  Returns the values for each date from a given time range that are associated with the specific daily metric. Example request: `GET https://businessprofileperformance.googleapis.com/v1/locations/12345:getDailyMetricsTimeSeries?dailyMetric=WEBSITE_CLICKS&daily_range.start_date.year=2022&daily_range.start_date.month=1&daily_range.start_date.day=1&daily_range.end_date.year=2022&daily_range.end_date.month=3&daily_range.end_date.day=31` */
		class getDailyMetricsTimeSeries(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GetDailyMetricsTimeSeriesResponse]) {
			val scopes = Seq()
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GetDailyMetricsTimeSeriesResponse])
		}
		object getDailyMetricsTimeSeries {
			def apply(locationsId :PlayApi, name: String, dailyMetric: String, dailyRangeStartDateYear: Int, dailyRangeStartDateMonth: Int, dailyRangeStartDateDay: Int, dailyRangeEndDateYear: Int, dailyRangeEndDateMonth: Int, dailyRangeEndDateDay: Int, dailySubEntityTypeDayOfWeek: String, dailySubEntityTypeTimeOfDayHours: Int, dailySubEntityTypeTimeOfDayMinutes: Int, dailySubEntityTypeTimeOfDaySeconds: Int, dailySubEntityTypeTimeOfDayNanos: Int)(using signer: RequestSigner, ec: ExecutionContext): getDailyMetricsTimeSeries = new getDailyMetricsTimeSeries(ws.url(BASE_URL + s"v1/locations/${locationsId}:getDailyMetricsTimeSeries").addQueryStringParameters("name" -> name.toString, "dailyMetric" -> dailyMetric.toString, "dailyRange.startDate.year" -> dailyRangeStartDateYear.toString, "dailyRange.startDate.month" -> dailyRangeStartDateMonth.toString, "dailyRange.startDate.day" -> dailyRangeStartDateDay.toString, "dailyRange.endDate.year" -> dailyRangeEndDateYear.toString, "dailyRange.endDate.month" -> dailyRangeEndDateMonth.toString, "dailyRange.endDate.day" -> dailyRangeEndDateDay.toString, "dailySubEntityType.dayOfWeek" -> dailySubEntityTypeDayOfWeek.toString, "dailySubEntityType.timeOfDay.hours" -> dailySubEntityTypeTimeOfDayHours.toString, "dailySubEntityType.timeOfDay.minutes" -> dailySubEntityTypeTimeOfDayMinutes.toString, "dailySubEntityType.timeOfDay.seconds" -> dailySubEntityTypeTimeOfDaySeconds.toString, "dailySubEntityType.timeOfDay.nanos" -> dailySubEntityTypeTimeOfDayNanos.toString))
			given Conversion[getDailyMetricsTimeSeries, Future[Schema.GetDailyMetricsTimeSeriesResponse]] = (fun: getDailyMetricsTimeSeries) => fun.apply()
		}
		/**  Returns the values for each date from a given time range and optionally the sub entity type, where applicable, that are associated with the specific daily metrics. Example request: `GET https://businessprofileperformance.googleapis.com/v1/locations/12345:fetchMultiDailyMetricsTimeSeries?dailyMetrics=WEBSITE_CLICKS&dailyMetrics=CALL_CLICKS&daily_range.start_date.year=2022&daily_range.start_date.month=1&daily_range.start_date.day=1&daily_range.end_date.year=2022&daily_range.end_date.month=3&daily_range.end_date.day=31` */
		class fetchMultiDailyMetricsTimeSeries(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.FetchMultiDailyMetricsTimeSeriesResponse]) {
			val scopes = Seq()
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.FetchMultiDailyMetricsTimeSeriesResponse])
		}
		object fetchMultiDailyMetricsTimeSeries {
			def apply(locationsId :PlayApi, location: String, dailyMetrics: String, dailyRangeStartDateYear: Int, dailyRangeStartDateMonth: Int, dailyRangeStartDateDay: Int, dailyRangeEndDateYear: Int, dailyRangeEndDateMonth: Int, dailyRangeEndDateDay: Int)(using signer: RequestSigner, ec: ExecutionContext): fetchMultiDailyMetricsTimeSeries = new fetchMultiDailyMetricsTimeSeries(ws.url(BASE_URL + s"v1/locations/${locationsId}:fetchMultiDailyMetricsTimeSeries").addQueryStringParameters("location" -> location.toString, "dailyMetrics" -> dailyMetrics.toString, "dailyRange.startDate.year" -> dailyRangeStartDateYear.toString, "dailyRange.startDate.month" -> dailyRangeStartDateMonth.toString, "dailyRange.startDate.day" -> dailyRangeStartDateDay.toString, "dailyRange.endDate.year" -> dailyRangeEndDateYear.toString, "dailyRange.endDate.month" -> dailyRangeEndDateMonth.toString, "dailyRange.endDate.day" -> dailyRangeEndDateDay.toString))
			given Conversion[fetchMultiDailyMetricsTimeSeries, Future[Schema.FetchMultiDailyMetricsTimeSeriesResponse]] = (fun: fetchMultiDailyMetricsTimeSeries) => fun.apply()
		}
		object searchkeywords {
			object impressions {
				object monthly {
					/** Returns the search keywords used to find a business in search or maps. Each search keyword is accompanied by impressions which are aggregated on a monthly basis. Example request: `GET https://businessprofileperformance.googleapis.com/v1/locations/12345/searchkeywords/impressions/monthly?monthly_range.start_month.year=2022&monthly_range.start_month.month=1&monthly_range.end_month.year=2022&monthly_range.end_month.month=3` */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListSearchKeywordImpressionsMonthlyResponse]) {
						val scopes = Seq()
						/** Optional. The number of results requested. The default page size is 100. Page size can be set to a maximum of 100.<br>Format: int32 */
						def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
						/** Optional. A token indicating the next paginated result to be returned.<br>Format: byte */
						def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListSearchKeywordImpressionsMonthlyResponse])
					}
					object list {
						def apply(locationsId :PlayApi, parent: String, monthlyRangeStartMonthYear: Int, monthlyRangeStartMonthMonth: Int, monthlyRangeStartMonthDay: Int, monthlyRangeEndMonthYear: Int, monthlyRangeEndMonthMonth: Int, monthlyRangeEndMonthDay: Int)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/locations/${locationsId}/searchkeywords/impressions/monthly").addQueryStringParameters("parent" -> parent.toString, "monthlyRange.startMonth.year" -> monthlyRangeStartMonthYear.toString, "monthlyRange.startMonth.month" -> monthlyRangeStartMonthMonth.toString, "monthlyRange.startMonth.day" -> monthlyRangeStartMonthDay.toString, "monthlyRange.endMonth.year" -> monthlyRangeEndMonthYear.toString, "monthlyRange.endMonth.month" -> monthlyRangeEndMonthMonth.toString, "monthlyRange.endMonth.day" -> monthlyRangeEndMonthDay.toString))
						given Conversion[list, Future[Schema.ListSearchKeywordImpressionsMonthlyResponse]] = (fun: list) => fun.apply()
					}
				}
			}
		}
	}
}
