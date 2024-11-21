package com.boresjo.play.api.google.airquality

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Formats {
	given fmtAdditionalInfo: Format[Schema.AdditionalInfo] = Json.format[Schema.AdditionalInfo]
	given fmtLookupForecastRequest: Format[Schema.LookupForecastRequest] = Json.format[Schema.LookupForecastRequest]
	given fmtInterval: Format[Schema.Interval] = Json.format[Schema.Interval]
	given fmtLatLng: Format[Schema.LatLng] = Json.format[Schema.LatLng]
	given fmtLookupForecastRequestUaqiColorPaletteEnum: Format[Schema.LookupForecastRequest.UaqiColorPaletteEnum] = JsonEnumFormat[Schema.LookupForecastRequest.UaqiColorPaletteEnum]
	given fmtLookupForecastRequestExtraComputationsEnum: Format[Schema.LookupForecastRequest.ExtraComputationsEnum] = JsonEnumFormat[Schema.LookupForecastRequest.ExtraComputationsEnum]
	given fmtCustomLocalAqi: Format[Schema.CustomLocalAqi] = Json.format[Schema.CustomLocalAqi]
	given fmtHourInfo: Format[Schema.HourInfo] = Json.format[Schema.HourInfo]
	given fmtHealthRecommendations: Format[Schema.HealthRecommendations] = Json.format[Schema.HealthRecommendations]
	given fmtPollutant: Format[Schema.Pollutant] = Json.format[Schema.Pollutant]
	given fmtAirQualityIndex: Format[Schema.AirQualityIndex] = Json.format[Schema.AirQualityIndex]
	given fmtColor: Format[Schema.Color] = Json.format[Schema.Color]
	given fmtLookupHistoryRequest: Format[Schema.LookupHistoryRequest] = Json.format[Schema.LookupHistoryRequest]
	given fmtLookupHistoryRequestExtraComputationsEnum: Format[Schema.LookupHistoryRequest.ExtraComputationsEnum] = JsonEnumFormat[Schema.LookupHistoryRequest.ExtraComputationsEnum]
	given fmtLookupHistoryRequestUaqiColorPaletteEnum: Format[Schema.LookupHistoryRequest.UaqiColorPaletteEnum] = JsonEnumFormat[Schema.LookupHistoryRequest.UaqiColorPaletteEnum]
	given fmtLookupCurrentConditionsRequest: Format[Schema.LookupCurrentConditionsRequest] = Json.format[Schema.LookupCurrentConditionsRequest]
	given fmtLookupCurrentConditionsRequestExtraComputationsEnum: Format[Schema.LookupCurrentConditionsRequest.ExtraComputationsEnum] = JsonEnumFormat[Schema.LookupCurrentConditionsRequest.ExtraComputationsEnum]
	given fmtLookupCurrentConditionsRequestUaqiColorPaletteEnum: Format[Schema.LookupCurrentConditionsRequest.UaqiColorPaletteEnum] = JsonEnumFormat[Schema.LookupCurrentConditionsRequest.UaqiColorPaletteEnum]
	given fmtHttpBody: Format[Schema.HttpBody] = Json.format[Schema.HttpBody]
	given fmtLookupForecastResponse: Format[Schema.LookupForecastResponse] = Json.format[Schema.LookupForecastResponse]
	given fmtHourlyForecast: Format[Schema.HourlyForecast] = Json.format[Schema.HourlyForecast]
	given fmtConcentration: Format[Schema.Concentration] = Json.format[Schema.Concentration]
	given fmtConcentrationUnitsEnum: Format[Schema.Concentration.UnitsEnum] = JsonEnumFormat[Schema.Concentration.UnitsEnum]
	given fmtLookupCurrentConditionsResponse: Format[Schema.LookupCurrentConditionsResponse] = Json.format[Schema.LookupCurrentConditionsResponse]
	given fmtLookupHistoryResponse: Format[Schema.LookupHistoryResponse] = Json.format[Schema.LookupHistoryResponse]
}
