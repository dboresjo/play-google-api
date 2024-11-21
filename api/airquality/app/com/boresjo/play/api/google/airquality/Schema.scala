package com.boresjo.play.api.google.airquality

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	case class AdditionalInfo(
	  /** Text representing the pollutant's main emission sources. */
		sources: Option[String] = None,
	  /** Text representing the pollutant's main health effects. */
		effects: Option[String] = None
	)
	
	object LookupForecastRequest {
		enum UaqiColorPaletteEnum extends Enum[UaqiColorPaletteEnum] { case COLOR_PALETTE_UNSPECIFIED, RED_GREEN, INDIGO_PERSIAN_DARK, INDIGO_PERSIAN_LIGHT }
		enum ExtraComputationsEnum extends Enum[ExtraComputationsEnum] { case EXTRA_COMPUTATION_UNSPECIFIED, LOCAL_AQI, HEALTH_RECOMMENDATIONS, POLLUTANT_ADDITIONAL_INFO, DOMINANT_POLLUTANT_CONCENTRATION, POLLUTANT_CONCENTRATION }
	}
	case class LookupForecastRequest(
	  /** Optional. The maximum number of hourly info records to return per page (default = 24). */
		pageSize: Option[Int] = None,
	  /** A timestamp for which to return the data for a specific point in time. The timestamp is rounded to the previous exact hour. Note: this will return hourly data for the requested timestamp only (i.e. a single hourly info element). For example, a request sent where the date_time parameter is set to 2023-01-03T11:05:49Z will be rounded down to 2023-01-03T11:00:00Z. */
		dateTime: Option[String] = None,
	  /** Optional. If set to true, the Universal AQI will be included in the 'indexes' field of the response (default = true). */
		universalAqi: Option[Boolean] = None,
	  /** Indicates the start and end period for which to get the forecast data. The timestamp is rounded to the previous exact hour. */
		period: Option[Schema.Interval] = None,
	  /** Required. The latitude and longitude for which the API looks for air quality data. */
		location: Option[Schema.LatLng] = None,
	  /** Optional. Determines the color palette used for data provided by the 'Universal Air Quality Index' (UAQI). This color palette is relevant just for UAQI, other AQIs have a predetermined color palette that can't be controlled. */
		uaqiColorPalette: Option[Schema.LookupForecastRequest.UaqiColorPaletteEnum] = None,
	  /** Optional. Additional features that can be optionally enabled. Specifying extra computations will result in the relevant elements and fields to be returned in the response. */
		extraComputations: Option[List[Schema.LookupForecastRequest.ExtraComputationsEnum]] = None,
	  /** Optional. Expresses a 'country/region to AQI' relationship. Pairs a country/region with a desired AQI so that air quality data that is required for that country/region will be displayed according to the chosen AQI. This parameter can be used to specify a non-default AQI for a given country, for example, to get the US EPA index for Canada rather than the default index for Canada. */
		customLocalAqis: Option[List[Schema.CustomLocalAqi]] = None,
	  /** Optional. Allows the client to choose the language for the response. If data cannot be provided for that language the API uses the closest match. Allowed values rely on the IETF standard (default = 'en'). */
		languageCode: Option[String] = None,
	  /** Optional. A page token received from a previous forecast call. It is used to retrieve the subsequent page. */
		pageToken: Option[String] = None
	)
	
	case class HourInfo(
	  /** Health advice and recommended actions related to the reported air quality conditions. Recommendations are tailored differently for populations at risk, groups with greater sensitivities to pollutants, and the general population. */
		healthRecommendations: Option[Schema.HealthRecommendations] = None,
	  /** A rounded down timestamp indicating the time the data refers to in RFC3339 UTC "Zulu" format, with nanosecond resolution and up to nine fractional digits. For example: "2014-10-02T15:00:00Z". */
		dateTime: Option[String] = None,
	  /** A list of pollutants affecting the location specified in the request. Note: This field will be returned only for requests that specified one or more of the following extra computations: POLLUTANT_ADDITIONAL_INFO, DOMINANT_POLLUTANT_CONCENTRATION, POLLUTANT_CONCENTRATION. */
		pollutants: Option[List[Schema.Pollutant]] = None,
	  /** Based on the request parameters, this list will include (up to) two air quality indexes: - Universal AQI. Will be returned if the universalAqi boolean is set to true. - Local AQI. Will be returned if the LOCAL_AQI extra computation is specified. */
		indexes: Option[List[Schema.AirQualityIndex]] = None
	)
	
	case class Interval(
	  /** Optional. Exclusive end of the interval. If specified, a Timestamp matching this interval will have to be before the end. */
		endTime: Option[String] = None,
	  /** Optional. Inclusive start of the interval. If specified, a Timestamp matching this interval will have to be the same or after the start. */
		startTime: Option[String] = None
	)
	
	case class AirQualityIndex(
	  /** The index's code. This field represents the index for programming purposes by using snake case instead of spaces. Examples: "uaqi", "fra_atmo". */
		code: Option[String] = None,
	  /** The chemical symbol of the dominant pollutant. For example: "CO". */
		dominantPollutant: Option[String] = None,
	  /** The color used to represent the AQI numeric score. */
		color: Option[Schema.Color] = None,
	  /** A human readable representation of the index name. Example: "AQI (US)" */
		displayName: Option[String] = None,
	  /** Textual classification of the index numeric score interpretation. For example: "Excellent air quality". */
		category: Option[String] = None,
	  /**  The index's numeric score. Examples: 10, 100. The value is not normalized and should only be interpreted in the context of its related air-quality index. For non-numeric indexes, this field will not be returned. Note: This field should be used for calculations, graph display, etc. For displaying the index score, you should use the AQI display field. */
		aqi: Option[Int] = None,
	  /** Textual representation of the index numeric score, that may include prefix or suffix symbols, which usually represents the worst index score. Example: >100 or 10+. Note: This field should be used when you want to display the index score. For non-numeric indexes, this field is empty. */
		aqiDisplay: Option[String] = None
	)
	
	object LookupHistoryRequest {
		enum ExtraComputationsEnum extends Enum[ExtraComputationsEnum] { case EXTRA_COMPUTATION_UNSPECIFIED, LOCAL_AQI, HEALTH_RECOMMENDATIONS, POLLUTANT_ADDITIONAL_INFO, DOMINANT_POLLUTANT_CONCENTRATION, POLLUTANT_CONCENTRATION }
		enum UaqiColorPaletteEnum extends Enum[UaqiColorPaletteEnum] { case COLOR_PALETTE_UNSPECIFIED, RED_GREEN, INDIGO_PERSIAN_DARK, INDIGO_PERSIAN_LIGHT }
	}
	case class LookupHistoryRequest(
	  /** Indicates the start and end period for which to get the historical data. The timestamp is rounded to the previous exact hour. */
		period: Option[Schema.Interval] = None,
	  /** Optional. A page token received from a previous history call. It is used to retrieve the subsequent page. Note that when providing a value for this parameter all other parameters provided must match the call that provided the page token (the previous call). */
		pageToken: Option[String] = None,
	  /** Optional. Expresses a 'country/region to AQI' relationship. Pairs a country/region with a desired AQI so that air quality data that is required for that country/region will be displayed according to the chosen AQI. This parameter can be used to specify a non-default AQI for a given country, for example, to get the US EPA index for Canada rather than the default index for Canada. */
		customLocalAqis: Option[List[Schema.CustomLocalAqi]] = None,
	  /** A timestamp for which to return historical data. The timestamp is rounded to the previous exact hour. Note: this will return hourly data for the requested timestamp only (i.e. a single hourly info element). For example, a request sent where the dateTime parameter is set to 2023-01-03T11:05:49Z will be rounded down to 2023-01-03T11:00:00Z. A timestamp in RFC3339 UTC "Zulu" format, with nanosecond resolution and up to nine fractional digits. Examples: "2014-10-02T15:01:23Z" and "2014-10-02T15:01:23.045123456Z". */
		dateTime: Option[String] = None,
	  /** Required. The latitude and longitude for which the API looks for air quality history data. */
		location: Option[Schema.LatLng] = None,
	  /** Optional. The maximum number of hourly info records to return per page. The default is 72 and the max value is 168 (7 days of data). */
		pageSize: Option[Int] = None,
	  /** Optional. If set to true, the Universal AQI will be included in the 'indexes' field of the response. Default value is true. */
		universalAqi: Option[Boolean] = None,
	  /** Optional. Allows the client to choose the language for the response. If data cannot be provided for that language the API uses the closest match. Allowed values rely on the IETF standard. Default value is en. */
		languageCode: Option[String] = None,
	  /** Number from 1 to 720 that indicates the hours range for the request. For example: A value of 48 will yield data from the last 48 hours. */
		hours: Option[Int] = None,
	  /** Optional. Additional features that can be optionally enabled. Specifying extra computations will result in the relevant elements and fields to be returned in the response. */
		extraComputations: Option[List[Schema.LookupHistoryRequest.ExtraComputationsEnum]] = None,
	  /** Optional. Determines the color palette used for data provided by the 'Universal Air Quality Index' (UAQI). This color palette is relevant just for UAQI, other AQIs have a predetermined color palette that can't be controlled. */
		uaqiColorPalette: Option[Schema.LookupHistoryRequest.UaqiColorPaletteEnum] = None
	)
	
	object LookupCurrentConditionsRequest {
		enum ExtraComputationsEnum extends Enum[ExtraComputationsEnum] { case EXTRA_COMPUTATION_UNSPECIFIED, LOCAL_AQI, HEALTH_RECOMMENDATIONS, POLLUTANT_ADDITIONAL_INFO, DOMINANT_POLLUTANT_CONCENTRATION, POLLUTANT_CONCENTRATION }
		enum UaqiColorPaletteEnum extends Enum[UaqiColorPaletteEnum] { case COLOR_PALETTE_UNSPECIFIED, RED_GREEN, INDIGO_PERSIAN_DARK, INDIGO_PERSIAN_LIGHT }
	}
	case class LookupCurrentConditionsRequest(
	  /** Optional. Expresses a 'country/region to AQI' relationship. Pairs a country/region with a desired AQI so that air quality data that is required for that country/region will be displayed according to the chosen AQI. This parameter can be used to specify a non-default AQI for a given country, for example, to get the US EPA index for Canada rather than the default index for Canada. */
		customLocalAqis: Option[List[Schema.CustomLocalAqi]] = None,
	  /** Optional. If set to true, the Universal AQI will be included in the 'indexes' field of the response. Default value is true. */
		universalAqi: Option[Boolean] = None,
	  /** Optional. Allows the client to choose the language for the response. If data cannot be provided for that language the API uses the closest match. Allowed values rely on the IETF standard. Default value is en. */
		languageCode: Option[String] = None,
	  /** Optional. Additional features that can be optionally enabled. Specifying extra computations will result in the relevant elements and fields to be returned in the response. */
		extraComputations: Option[List[Schema.LookupCurrentConditionsRequest.ExtraComputationsEnum]] = None,
	  /** Optional. Determines the color palette used for data provided by the 'Universal Air Quality Index' (UAQI). This color palette is relevant just for UAQI, other AQIs have a predetermined color palette that can't be controlled. */
		uaqiColorPalette: Option[Schema.LookupCurrentConditionsRequest.UaqiColorPaletteEnum] = None,
	  /** Required. The longitude and latitude from which the API looks for air quality current conditions data. */
		location: Option[Schema.LatLng] = None
	)
	
	case class HttpBody(
	  /** The HTTP request/response body as raw binary. */
		data: Option[String] = None,
	  /** The HTTP Content-Type header value specifying the content type of the body. */
		contentType: Option[String] = None,
	  /** Application specific response metadata. Must be set in the first response for streaming APIs. */
		extensions: Option[List[Map[String, JsValue]]] = None
	)
	
	case class CustomLocalAqi(
	  /** The country/region requiring the custom AQI. Value should be provided using [ISO 3166-1 alpha-2](https://en.wikipedia.org/wiki/ISO_3166-1_alpha-2) code. */
		regionCode: Option[String] = None,
	  /** The AQI to associate the country/region with. Value should be a [valid index](/maps/documentation/air-quality/laqis) code. */
		aqi: Option[String] = None
	)
	
	case class LatLng(
	  /** The longitude in degrees. It must be in the range [-180.0, +180.0]. */
		longitude: Option[BigDecimal] = None,
	  /** The latitude in degrees. It must be in the range [-90.0, +90.0]. */
		latitude: Option[BigDecimal] = None
	)
	
	case class LookupForecastResponse(
	  /** Optional. The ISO_3166-1 alpha-2 code of the country/region corresponding to the location provided in the request. This field might be omitted from the response if the location provided in the request resides in a disputed territory. */
		regionCode: Option[String] = None,
	  /** Optional. The token to retrieve the next page. */
		nextPageToken: Option[String] = None,
	  /** Optional. Contains the air quality information for each hour in the requested range. For example, if the request is for 48 hours of forecast there will be 48 elements of hourly forecasts. */
		hourlyForecasts: Option[List[Schema.HourlyForecast]] = None
	)
	
	object Concentration {
		enum UnitsEnum extends Enum[UnitsEnum] { case UNIT_UNSPECIFIED, PARTS_PER_BILLION, MICROGRAMS_PER_CUBIC_METER }
	}
	case class Concentration(
	  /** Units for measuring this pollutant concentration. */
		units: Option[Schema.Concentration.UnitsEnum] = None,
	  /** Value of the pollutant concentration. */
		value: Option[BigDecimal] = None
	)
	
	case class LookupCurrentConditionsResponse(
	  /** A list of pollutants affecting the location specified in the request. Note: This field will be returned only for requests that specified one or more of the following extra computations: POLLUTANT_ADDITIONAL_INFO, DOMINANT_POLLUTANT_CONCENTRATION, POLLUTANT_CONCENTRATION. */
		pollutants: Option[List[Schema.Pollutant]] = None,
	  /** Health advice and recommended actions related to the reported air quality conditions. Recommendations are tailored differently for populations at risk, groups with greater sensitivities to pollutants, and the general population. */
		healthRecommendations: Option[Schema.HealthRecommendations] = None,
	  /** Based on the request parameters, this list will include (up to) two air quality indexes: - Universal AQI. Will be returned if the universalAqi boolean is set to true. - Local AQI. Will be returned if the LOCAL_AQI extra computation is specified. */
		indexes: Option[List[Schema.AirQualityIndex]] = None,
	  /** The ISO_3166-1 alpha-2 code of the country/region corresponding to the location provided in the request. This field might be omitted from the response if the location provided in the request resides in a disputed territory. */
		regionCode: Option[String] = None,
	  /** A rounded down timestamp in RFC3339 UTC "Zulu" format, with nanosecond resolution and up to nine fractional digits. For example: "2014-10-02T15:00:00Z". */
		dateTime: Option[String] = None
	)
	
	case class HealthRecommendations(
	  /** Women at all stages of pregnancy. */
		pregnantWomen: Option[String] = None,
	  /** Retirees and people older than the general population. */
		elderly: Option[String] = None,
	  /** Sports and other strenuous outdoor activities. */
		athletes: Option[String] = None,
	  /** Younger populations including children, toddlers, and babies. */
		children: Option[String] = None,
	  /** No specific sensitivities. */
		generalPopulation: Option[String] = None,
	  /** Heart and circulatory system diseases. */
		heartDiseasePopulation: Option[String] = None,
	  /** Respiratory related problems and asthma suffers. */
		lungDiseasePopulation: Option[String] = None
	)
	
	case class Pollutant(
	  /** The pollutant's code name (for example, "so2"). For a list of supported pollutant codes, see [Reported pollutants](/maps/documentation/air-quality/pollutants#reported_pollutants). */
		code: Option[String] = None,
	  /** The pollutant's full name. For chemical compounds, this is the IUPAC name. Example: "Sulfur Dioxide". For more information about the IUPAC names table, see https://iupac.org/what-we-do/periodic-table-of-elements/. */
		fullName: Option[String] = None,
	  /** Additional information about the pollutant. */
		additionalInfo: Option[Schema.AdditionalInfo] = None,
	  /** The pollutant's concentration level measured by one of the standard air pollutation measure units. */
		concentration: Option[Schema.Concentration] = None,
	  /** The pollutant's display name. For example: "NOx". */
		displayName: Option[String] = None
	)
	
	case class LookupHistoryResponse(
	  /** Optional. The token to retrieve the next page. */
		nextPageToken: Option[String] = None,
	  /** Optional. The ISO_3166-1 alpha-2 code of the country/region corresponding to the location provided in the request. This field might be omitted from the response if the location provided in the request resides in a disputed territory. */
		regionCode: Option[String] = None,
	  /** Optional. Contains the air quality information for each hour in the requested range. For example, if the request is for 48 hours of history there will be 48 elements of hourly info. */
		hoursInfo: Option[List[Schema.HourInfo]] = None
	)
	
	case class HourlyForecast(
	  /** A list of pollutants affecting the location specified in the request. Note: This field will be returned only for requests that specified one or more of the following extra computations: POLLUTANT_ADDITIONAL_INFO, DOMINANT_POLLUTANT_CONCENTRATION, POLLUTANT_CONCENTRATION. */
		pollutants: Option[List[Schema.Pollutant]] = None,
	  /** Health advice and recommended actions related to the reported air quality conditions. Recommendations are tailored differently for populations at risk, groups with greater sensitivities to pollutants, and the general population. */
		healthRecommendations: Option[Schema.HealthRecommendations] = None,
	  /** Based on the request parameters, this list will include (up to) two air quality indexes: - Universal AQI. Will be returned if the `universal_aqi` boolean is set to true. - Local AQI. Will be returned if the LOCAL_AQI extra computation is specified. */
		indexes: Option[List[Schema.AirQualityIndex]] = None,
	  /** A rounded down timestamp indicating the time (hour) the data refers to in RFC3339 UTC "Zulu" format. For example: "2014-10-02T15:00:00Z". */
		dateTime: Option[String] = None
	)
	
	case class Color(
	  /** The amount of blue in the color as a value in the interval [0, 1]. */
		blue: Option[BigDecimal] = None,
	  /** The amount of green in the color as a value in the interval [0, 1]. */
		green: Option[BigDecimal] = None,
	  /** The amount of red in the color as a value in the interval [0, 1]. */
		red: Option[BigDecimal] = None,
	  /** The fraction of this color that should be applied to the pixel. That is, the final pixel color is defined by the equation: `pixel color = alpha &#42; (this color) + (1.0 - alpha) &#42; (background color)` This means that a value of 1.0 corresponds to a solid color, whereas a value of 0.0 corresponds to a completely transparent color. This uses a wrapper message rather than a simple float scalar so that it is possible to distinguish between a default value and the value being unset. If omitted, this color object is rendered as a solid color (as if the alpha value had been explicitly given a value of 1.0). */
		alpha: Option[BigDecimal] = None
	)
}
