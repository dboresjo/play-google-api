package com.boresjo.play.api.google.businessprofileperformance

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://businessprofileperformance.googleapis.com/"

	object locations {
		class getDailyMetricsTimeSeries(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GetDailyMetricsTimeSeriesResponse]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GetDailyMetricsTimeSeriesResponse])
		}
		object getDailyMetricsTimeSeries {
			def apply(locationsId :PlayApi, name: String, dailyMetric: String, dailyRangeStartDateYear: Int, dailyRangeStartDateMonth: Int, dailyRangeStartDateDay: Int, dailyRangeEndDateYear: Int, dailyRangeEndDateMonth: Int, dailyRangeEndDateDay: Int, dailySubEntityTypeDayOfWeek: String, dailySubEntityTypeTimeOfDayHours: Int, dailySubEntityTypeTimeOfDayMinutes: Int, dailySubEntityTypeTimeOfDaySeconds: Int, dailySubEntityTypeTimeOfDayNanos: Int)(using auth: AuthToken, ec: ExecutionContext): getDailyMetricsTimeSeries = new getDailyMetricsTimeSeries(ws.url(BASE_URL + s"v1/locations/${locationsId}:getDailyMetricsTimeSeries").addQueryStringParameters("name" -> name.toString, "dailyMetric" -> dailyMetric.toString, "dailyRange.startDate.year" -> dailyRangeStartDateYear.toString, "dailyRange.startDate.month" -> dailyRangeStartDateMonth.toString, "dailyRange.startDate.day" -> dailyRangeStartDateDay.toString, "dailyRange.endDate.year" -> dailyRangeEndDateYear.toString, "dailyRange.endDate.month" -> dailyRangeEndDateMonth.toString, "dailyRange.endDate.day" -> dailyRangeEndDateDay.toString, "dailySubEntityType.dayOfWeek" -> dailySubEntityTypeDayOfWeek.toString, "dailySubEntityType.timeOfDay.hours" -> dailySubEntityTypeTimeOfDayHours.toString, "dailySubEntityType.timeOfDay.minutes" -> dailySubEntityTypeTimeOfDayMinutes.toString, "dailySubEntityType.timeOfDay.seconds" -> dailySubEntityTypeTimeOfDaySeconds.toString, "dailySubEntityType.timeOfDay.nanos" -> dailySubEntityTypeTimeOfDayNanos.toString))
			given Conversion[getDailyMetricsTimeSeries, Future[Schema.GetDailyMetricsTimeSeriesResponse]] = (fun: getDailyMetricsTimeSeries) => fun.apply()
		}
		class fetchMultiDailyMetricsTimeSeries(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.FetchMultiDailyMetricsTimeSeriesResponse]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.FetchMultiDailyMetricsTimeSeriesResponse])
		}
		object fetchMultiDailyMetricsTimeSeries {
			def apply(locationsId :PlayApi, location: String, dailyMetrics: String, dailyRangeStartDateYear: Int, dailyRangeStartDateMonth: Int, dailyRangeStartDateDay: Int, dailyRangeEndDateYear: Int, dailyRangeEndDateMonth: Int, dailyRangeEndDateDay: Int)(using auth: AuthToken, ec: ExecutionContext): fetchMultiDailyMetricsTimeSeries = new fetchMultiDailyMetricsTimeSeries(ws.url(BASE_URL + s"v1/locations/${locationsId}:fetchMultiDailyMetricsTimeSeries").addQueryStringParameters("location" -> location.toString, "dailyMetrics" -> dailyMetrics.toString, "dailyRange.startDate.year" -> dailyRangeStartDateYear.toString, "dailyRange.startDate.month" -> dailyRangeStartDateMonth.toString, "dailyRange.startDate.day" -> dailyRangeStartDateDay.toString, "dailyRange.endDate.year" -> dailyRangeEndDateYear.toString, "dailyRange.endDate.month" -> dailyRangeEndDateMonth.toString, "dailyRange.endDate.day" -> dailyRangeEndDateDay.toString))
			given Conversion[fetchMultiDailyMetricsTimeSeries, Future[Schema.FetchMultiDailyMetricsTimeSeriesResponse]] = (fun: fetchMultiDailyMetricsTimeSeries) => fun.apply()
		}
		object searchkeywords {
			object impressions {
				object monthly {
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListSearchKeywordImpressionsMonthlyResponse]) {
						/** Optional. The number of results requested. The default page size is 100. Page size can be set to a maximum of 100.<br>Format: int32 */
						def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
						/** Optional. A token indicating the next paginated result to be returned.<br>Format: byte */
						def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListSearchKeywordImpressionsMonthlyResponse])
					}
					object list {
						def apply(locationsId :PlayApi, parent: String, monthlyRangeStartMonthYear: Int, monthlyRangeStartMonthMonth: Int, monthlyRangeStartMonthDay: Int, monthlyRangeEndMonthYear: Int, monthlyRangeEndMonthMonth: Int, monthlyRangeEndMonthDay: Int)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/locations/${locationsId}/searchkeywords/impressions/monthly").addQueryStringParameters("parent" -> parent.toString, "monthlyRange.startMonth.year" -> monthlyRangeStartMonthYear.toString, "monthlyRange.startMonth.month" -> monthlyRangeStartMonthMonth.toString, "monthlyRange.startMonth.day" -> monthlyRangeStartMonthDay.toString, "monthlyRange.endMonth.year" -> monthlyRangeEndMonthYear.toString, "monthlyRange.endMonth.month" -> monthlyRangeEndMonthMonth.toString, "monthlyRange.endMonth.day" -> monthlyRangeEndMonthDay.toString))
						given Conversion[list, Future[Schema.ListSearchKeywordImpressionsMonthlyResponse]] = (fun: list) => fun.apply()
					}
				}
			}
		}
	}
}
