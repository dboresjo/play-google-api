package com.boresjo.play.api.google.airquality

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Helpers {
	import Schema.*
	object AutoOption {
		given putString: Conversion[String, Option[String]] = (fun: String) => Option(fun)
		given putInt: Conversion[Int, Option[Int]] = (fun: Int) => Option(fun)
		given putBoolean: Conversion[Boolean, Option[Boolean]] = (fun: Boolean) => Option(fun)
		given putSchemaInterval: Conversion[Schema.Interval, Option[Schema.Interval]] = (fun: Schema.Interval) => Option(fun)
		given putSchemaLatLng: Conversion[Schema.LatLng, Option[Schema.LatLng]] = (fun: Schema.LatLng) => Option(fun)
		given putSchemaLookupForecastRequestUaqiColorPaletteEnum: Conversion[Schema.LookupForecastRequest.UaqiColorPaletteEnum, Option[Schema.LookupForecastRequest.UaqiColorPaletteEnum]] = (fun: Schema.LookupForecastRequest.UaqiColorPaletteEnum) => Option(fun)
		given putListSchemaLookupForecastRequestExtraComputationsEnum: Conversion[List[Schema.LookupForecastRequest.ExtraComputationsEnum], Option[List[Schema.LookupForecastRequest.ExtraComputationsEnum]]] = (fun: List[Schema.LookupForecastRequest.ExtraComputationsEnum]) => Option(fun)
		given putListSchemaCustomLocalAqi: Conversion[List[Schema.CustomLocalAqi], Option[List[Schema.CustomLocalAqi]]] = (fun: List[Schema.CustomLocalAqi]) => Option(fun)
		given putSchemaHealthRecommendations: Conversion[Schema.HealthRecommendations, Option[Schema.HealthRecommendations]] = (fun: Schema.HealthRecommendations) => Option(fun)
		given putListSchemaPollutant: Conversion[List[Schema.Pollutant], Option[List[Schema.Pollutant]]] = (fun: List[Schema.Pollutant]) => Option(fun)
		given putListSchemaAirQualityIndex: Conversion[List[Schema.AirQualityIndex], Option[List[Schema.AirQualityIndex]]] = (fun: List[Schema.AirQualityIndex]) => Option(fun)
		given putSchemaColor: Conversion[Schema.Color, Option[Schema.Color]] = (fun: Schema.Color) => Option(fun)
		given putListSchemaLookupHistoryRequestExtraComputationsEnum: Conversion[List[Schema.LookupHistoryRequest.ExtraComputationsEnum], Option[List[Schema.LookupHistoryRequest.ExtraComputationsEnum]]] = (fun: List[Schema.LookupHistoryRequest.ExtraComputationsEnum]) => Option(fun)
		given putSchemaLookupHistoryRequestUaqiColorPaletteEnum: Conversion[Schema.LookupHistoryRequest.UaqiColorPaletteEnum, Option[Schema.LookupHistoryRequest.UaqiColorPaletteEnum]] = (fun: Schema.LookupHistoryRequest.UaqiColorPaletteEnum) => Option(fun)
		given putListSchemaLookupCurrentConditionsRequestExtraComputationsEnum: Conversion[List[Schema.LookupCurrentConditionsRequest.ExtraComputationsEnum], Option[List[Schema.LookupCurrentConditionsRequest.ExtraComputationsEnum]]] = (fun: List[Schema.LookupCurrentConditionsRequest.ExtraComputationsEnum]) => Option(fun)
		given putSchemaLookupCurrentConditionsRequestUaqiColorPaletteEnum: Conversion[Schema.LookupCurrentConditionsRequest.UaqiColorPaletteEnum, Option[Schema.LookupCurrentConditionsRequest.UaqiColorPaletteEnum]] = (fun: Schema.LookupCurrentConditionsRequest.UaqiColorPaletteEnum) => Option(fun)
		given putListMapStringJsValue: Conversion[List[Map[String, JsValue]], Option[List[Map[String, JsValue]]]] = (fun: List[Map[String, JsValue]]) => Option(fun)
		given putBigDecimal: Conversion[BigDecimal, Option[BigDecimal]] = (fun: BigDecimal) => Option(fun)
		given putListSchemaHourlyForecast: Conversion[List[Schema.HourlyForecast], Option[List[Schema.HourlyForecast]]] = (fun: List[Schema.HourlyForecast]) => Option(fun)
		given putSchemaConcentrationUnitsEnum: Conversion[Schema.Concentration.UnitsEnum, Option[Schema.Concentration.UnitsEnum]] = (fun: Schema.Concentration.UnitsEnum) => Option(fun)
		given putSchemaAdditionalInfo: Conversion[Schema.AdditionalInfo, Option[Schema.AdditionalInfo]] = (fun: Schema.AdditionalInfo) => Option(fun)
		given putSchemaConcentration: Conversion[Schema.Concentration, Option[Schema.Concentration]] = (fun: Schema.Concentration) => Option(fun)
		given putListSchemaHourInfo: Conversion[List[Schema.HourInfo], Option[List[Schema.HourInfo]]] = (fun: List[Schema.HourInfo]) => Option(fun)
	}
	object OptionDefault {
		given getString: Conversion[Option[String], String] = (fun: Option[String]) => fun.getOrElse("")
	}
}
