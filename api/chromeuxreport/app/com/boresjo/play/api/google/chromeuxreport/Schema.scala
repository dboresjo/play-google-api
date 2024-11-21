package com.boresjo.play.api.google.chromeuxreport

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	object QueryRequest {
		enum FormFactorEnum extends Enum[FormFactorEnum] { case ALL_FORM_FACTORS, PHONE, DESKTOP, TABLET }
	}
	case class QueryRequest(
	  /** The url pattern "origin" refers to a url pattern that is the origin of a website. Examples: "https://example.com", "https://cloud.google.com" */
		origin: Option[String] = None,
	  /** The url pattern "url" refers to a url pattern that is any arbitrary url. Examples: "https://example.com/", "https://cloud.google.com/why-google-cloud/" */
		url: Option[String] = None,
	  /** The effective connection type is a query dimension that specifies the effective network class that the record's data should belong to. This field uses the values ["offline", "slow-2G", "2G", "3G", "4G"] as specified in: https://wicg.github.io/netinfo/#effective-connection-types Note: If no effective connection type is specified, then a special record with aggregated data over all effective connection types will be returned. */
		effectiveConnectionType: Option[String] = None,
	  /** The form factor is a query dimension that specifies the device class that the record's data should belong to. Note: If no form factor is specified, then a special record with aggregated data over all form factors will be returned. */
		formFactor: Option[Schema.QueryRequest.FormFactorEnum] = None,
	  /** The metrics that should be included in the response. If none are specified then any metrics found will be returned. Allowed values: ["first_contentful_paint", "first_input_delay", "largest_contentful_paint", "cumulative_layout_shift", "experimental_time_to_first_byte", "experimental_interaction_to_next_paint"] */
		metrics: Option[List[String]] = None
	)
	
	case class QueryResponse(
	  /** The record that was found. */
		record: Option[Schema.Record] = None,
	  /** These are details about automated normalization actions that were taken in order to make the requested `url_pattern` valid. */
		urlNormalizationDetails: Option[Schema.UrlNormalization] = None
	)
	
	case class Record(
	  /** Key defines all of the unique querying parameters needed to look up a user experience record. */
		key: Option[Schema.Key] = None,
	  /** Metrics is the map of user experience data available for the record defined in the key field. Metrics are keyed on the metric name. Allowed key values: ["first_contentful_paint", "first_input_delay", "largest_contentful_paint", "cumulative_layout_shift", "experimental_time_to_first_byte", "experimental_interaction_to_next_paint"] */
		metrics: Option[Map[String, Schema.Metric]] = None,
	  /** The collection period indicates when the data reflected in this record was collected. */
		collectionPeriod: Option[Schema.CollectionPeriod] = None
	)
	
	object Key {
		enum FormFactorEnum extends Enum[FormFactorEnum] { case ALL_FORM_FACTORS, PHONE, DESKTOP, TABLET }
	}
	case class Key(
	  /** Origin specifies the origin that this record is for. Note: When specifying an origin, data for loads under this origin over all pages are aggregated into origin level user experience data. */
		origin: Option[String] = None,
	  /** Url specifies a specific url that this record is for. Note: When specifying a "url" only data for that specific url will be aggregated. */
		url: Option[String] = None,
	  /** The form factor is the device class that all users used to access the site for this record. If the form factor is unspecified, then aggregated data over all form factors will be returned. */
		formFactor: Option[Schema.Key.FormFactorEnum] = None,
	  /** The effective connection type is the general connection class that all users experienced for this record. This field uses the values ["offline", "slow-2G", "2G", "3G", "4G"] as specified in: https://wicg.github.io/netinfo/#effective-connection-types If the effective connection type is unspecified, then aggregated data over all effective connection types will be returned. */
		effectiveConnectionType: Option[String] = None
	)
	
	case class Metric(
	  /** The histogram of user experiences for a metric. The histogram will have at least one bin and the densities of all bins will add up to ~1. */
		histogram: Option[List[Schema.Bin]] = None,
	  /** Commonly useful percentiles of the Metric. The value type for the percentiles will be the same as the value types given for the Histogram bins. */
		percentiles: Option[Schema.Percentiles] = None,
	  /** For enum metrics, provides fractions which add up to approximately 1.0. */
		fractions: Option[Map[String, BigDecimal]] = None
	)
	
	case class Bin(
	  /** Start is the beginning of the data bin. */
		start: Option[JsValue] = None,
	  /** End is the end of the data bin. If end is not populated, then the bin has no end and is valid from start to +inf. */
		end: Option[JsValue] = None,
	  /** The proportion of users that experienced this bin's value for the given metric. */
		density: Option[JsValue] = None
	)
	
	case class Percentiles(
	  /** 75% of users experienced the given metric at or below this value. */
		p75: Option[JsValue] = None
	)
	
	case class CollectionPeriod(
	  /** The first day in the collection period, inclusive. */
		firstDate: Option[Schema.Date] = None,
	  /** The last day in the collection period, inclusive. */
		lastDate: Option[Schema.Date] = None
	)
	
	case class Date(
	  /** Year of the date. Must be from 1 to 9999, or 0 to specify a date without a year. */
		year: Option[Int] = None,
	  /** Month of a year. Must be from 1 to 12, or 0 to specify a year without a month and day. */
		month: Option[Int] = None,
	  /** Day of a month. Must be from 1 to 31 and valid for the year and month, or 0 to specify a year by itself or a year and month where the day isn't significant. */
		day: Option[Int] = None
	)
	
	case class UrlNormalization(
	  /** The original requested URL prior to any normalization actions. */
		originalUrl: Option[String] = None,
	  /** The URL after any normalization actions. This is a valid user experience URL that could reasonably be looked up. */
		normalizedUrl: Option[String] = None
	)
	
	object QueryHistoryRequest {
		enum FormFactorEnum extends Enum[FormFactorEnum] { case ALL_FORM_FACTORS, PHONE, DESKTOP, TABLET }
	}
	case class QueryHistoryRequest(
	  /** The url pattern "origin" refers to a url pattern that is the origin of a website. Examples: "https://example.com", "https://cloud.google.com" */
		origin: Option[String] = None,
	  /** The url pattern "url" refers to a url pattern that is any arbitrary url. Examples: "https://example.com/", "https://cloud.google.com/why-google-cloud/" */
		url: Option[String] = None,
	  /** The form factor is a query dimension that specifies the device class that the record's data should belong to. Note: If no form factor is specified, then a special record with aggregated data over all form factors will be returned. */
		formFactor: Option[Schema.QueryHistoryRequest.FormFactorEnum] = None,
	  /** The metrics that should be included in the response. If none are specified then any metrics found will be returned. Allowed values: ["first_contentful_paint", "first_input_delay", "largest_contentful_paint", "cumulative_layout_shift", "experimental_time_to_first_byte", "experimental_interaction_to_next_paint"] */
		metrics: Option[List[String]] = None
	)
	
	case class QueryHistoryResponse(
	  /** The record that was found. */
		record: Option[Schema.HistoryRecord] = None,
	  /** These are details about automated normalization actions that were taken in order to make the requested `url_pattern` valid. */
		urlNormalizationDetails: Option[Schema.UrlNormalization] = None
	)
	
	case class HistoryRecord(
	  /** Key defines all of the unique querying parameters needed to look up a user experience history record. */
		key: Option[Schema.HistoryKey] = None,
	  /** Metrics is the map of user experience time series data available for the record defined in the key field. Metrics are keyed on the metric name. Allowed key values: ["first_contentful_paint", "first_input_delay", "largest_contentful_paint", "cumulative_layout_shift", "experimental_time_to_first_byte", "experimental_interaction_to_next_paint"] */
		metrics: Option[Map[String, Schema.MetricTimeseries]] = None,
	  /** The collection periods indicate when each of the data points reflected in the time series data in metrics was collected. Note that all the time series share the same collection periods, and it is enforced in the CrUX pipeline that every time series has the same number of data points. */
		collectionPeriods: Option[List[Schema.CollectionPeriod]] = None
	)
	
	object HistoryKey {
		enum FormFactorEnum extends Enum[FormFactorEnum] { case ALL_FORM_FACTORS, PHONE, DESKTOP, TABLET }
	}
	case class HistoryKey(
	  /** Origin specifies the origin that this record is for. Note: When specifying an origin, data for loads under this origin over all pages are aggregated into origin level user experience data. */
		origin: Option[String] = None,
	  /** Url specifies a specific url that this record is for. This url should be normalized, following the normalization actions taken in the request to increase the chances of successful lookup. Note: When specifying a "url" only data for that specific url will be aggregated. */
		url: Option[String] = None,
	  /** The form factor is the device class that all users used to access the site for this record. If the form factor is unspecified, then aggregated data over all form factors will be returned. */
		formFactor: Option[Schema.HistoryKey.FormFactorEnum] = None
	)
	
	case class MetricTimeseries(
	  /** The histogram of user experiences for a metric. The histogram will have at least one bin and the densities of all bins will add up to ~1, for each timeseries entry. */
		histogramTimeseries: Option[List[Schema.TimeseriesBin]] = None,
	  /** Commonly useful percentiles of the Metric. The value type for the percentiles will be the same as the value types given for the Histogram bins. */
		percentilesTimeseries: Option[Schema.TimeseriesPercentiles] = None,
	  /** Mapping from labels to timeseries of fractions attributed to this label. */
		fractionTimeseries: Option[Map[String, Schema.FractionTimeseries]] = None
	)
	
	case class TimeseriesBin(
	  /** Start is the beginning of the data bin. */
		start: Option[JsValue] = None,
	  /** End is the end of the data bin. If end is not populated, then the bin has no end and is valid from start to +inf. */
		end: Option[JsValue] = None,
	  /** The proportion of users that experienced this bin's value for the given metric in a given collection period; the index for each of these entries corresponds to an entry in the CollectionPeriods field in the HistoryRecord message, which describes when the density was observed in the field. Thus, the length of this list of densities is equal to the length of the CollectionPeriods field in the HistoryRecord message. */
		densities: Option[List[BigDecimal]] = None
	)
	
	case class TimeseriesPercentiles(
	  /** 75% of users experienced the given metric at or below this value. The length of this list of densities is equal to the length of the CollectionPeriods field in the HistoryRecord message, which describes when the density was observed in the field. */
		p75s: Option[List[JsValue]] = None
	)
	
	case class FractionTimeseries(
	  /** Values between 0.0 and 1.0 (inclusive) and NaN. */
		fractions: Option[List[BigDecimal]] = None
	)
}
