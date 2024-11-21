package com.boresjo.play.api.google.businessprofileperformance

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	case class GetDailyMetricsTimeSeriesResponse(
	  /** The daily time series. */
		timeSeries: Option[Schema.TimeSeries] = None
	)
	
	case class TimeSeries(
	  /** List of datapoints in the timeseries, where each datapoint is a date-value pair. */
		datedValues: Option[List[Schema.DatedValue]] = None
	)
	
	case class DatedValue(
	  /** The date that the datapoint corresponds to. This represents a month value if the day field is not set. */
		date: Option[Schema.Date] = None,
	  /** The value of the datapoint. This will not be present when the value is zero. */
		value: Option[String] = None
	)
	
	case class Date(
	  /** Year of the date. Must be from 1 to 9999, or 0 to specify a date without a year. */
		year: Option[Int] = None,
	  /** Month of a year. Must be from 1 to 12, or 0 to specify a year without a month and day. */
		month: Option[Int] = None,
	  /** Day of a month. Must be from 1 to 31 and valid for the year and month, or 0 to specify a year by itself or a year and month where the day isn't significant. */
		day: Option[Int] = None
	)
	
	case class FetchMultiDailyMetricsTimeSeriesResponse(
	  /** DailyMetrics and their corresponding time series. */
		multiDailyMetricTimeSeries: Option[List[Schema.MultiDailyMetricTimeSeries]] = None
	)
	
	case class MultiDailyMetricTimeSeries(
	  /** List of DailyMetric-TimeSeries pairs. */
		dailyMetricTimeSeries: Option[List[Schema.DailyMetricTimeSeries]] = None
	)
	
	object DailyMetricTimeSeries {
		enum DailyMetricEnum extends Enum[DailyMetricEnum] { case DAILY_METRIC_UNKNOWN, BUSINESS_IMPRESSIONS_DESKTOP_MAPS, BUSINESS_IMPRESSIONS_DESKTOP_SEARCH, BUSINESS_IMPRESSIONS_MOBILE_MAPS, BUSINESS_IMPRESSIONS_MOBILE_SEARCH, BUSINESS_CONVERSATIONS, BUSINESS_DIRECTION_REQUESTS, CALL_CLICKS, WEBSITE_CLICKS, BUSINESS_BOOKINGS, BUSINESS_FOOD_ORDERS, BUSINESS_FOOD_MENU_CLICKS }
	}
	case class DailyMetricTimeSeries(
	  /** The DailyMetric that the TimeSeries represents. */
		dailyMetric: Option[Schema.DailyMetricTimeSeries.DailyMetricEnum] = None,
	  /** The DailySubEntityType that the TimeSeries represents. Will not be present when breakdown does not exist. */
		dailySubEntityType: Option[Schema.DailySubEntityType] = None,
	  /** List of datapoints where each datapoint is a date-value pair. */
		timeSeries: Option[Schema.TimeSeries] = None
	)
	
	object DailySubEntityType {
		enum DayOfWeekEnum extends Enum[DayOfWeekEnum] { case DAY_OF_WEEK_UNSPECIFIED, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY }
	}
	case class DailySubEntityType(
	  /** Represents the day of the week. Eg: MONDAY. Currently supported DailyMetrics = NONE. */
		dayOfWeek: Option[Schema.DailySubEntityType.DayOfWeekEnum] = None,
	  /** Represents the time of the day in 24 hour format. Eg: 13:34:20 Currently supported DailyMetrics = NONE. */
		timeOfDay: Option[Schema.TimeOfDay] = None
	)
	
	case class TimeOfDay(
	  /** Hours of a day in 24 hour format. Must be greater than or equal to 0 and typically must be less than or equal to 23. An API may choose to allow the value "24:00:00" for scenarios like business closing time. */
		hours: Option[Int] = None,
	  /** Minutes of an hour. Must be greater than or equal to 0 and less than or equal to 59. */
		minutes: Option[Int] = None,
	  /** Seconds of a minute. Must be greater than or equal to 0 and typically must be less than or equal to 59. An API may allow the value 60 if it allows leap-seconds. */
		seconds: Option[Int] = None,
	  /** Fractions of seconds, in nanoseconds. Must be greater than or equal to 0 and less than or equal to 999,999,999. */
		nanos: Option[Int] = None
	)
	
	case class ListSearchKeywordImpressionsMonthlyResponse(
	  /** Search terms which have been used to find a business. */
		searchKeywordsCounts: Option[List[Schema.SearchKeywordCount]] = None,
	  /** A token indicating the last paginated result returned. This can be used by succeeding requests to get the next "page" of keywords. It will only be present when there are more results to be returned. */
		nextPageToken: Option[String] = None
	)
	
	case class SearchKeywordCount(
	  /** The lower-cased string that the user entered. */
		searchKeyword: Option[String] = None,
	  /** One of either: 1) The sum of the number of unique users that used the keyword in a month, aggregated for each month requested. 2) A threshold that indicates that the actual value is below this threshold. */
		insightsValue: Option[Schema.InsightsValue] = None
	)
	
	case class InsightsValue(
	  /** Represents the actual value. */
		value: Option[String] = None,
	  /** Represents the threshold below which the actual value falls. */
		threshold: Option[String] = None
	)
}
