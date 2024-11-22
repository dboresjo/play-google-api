package com.boresjo.play.api.google.solar

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Helpers {
	import Schema.*
	object AutoOption {
		given putSchemaMoney: Conversion[Schema.Money, Option[Schema.Money]] = (fun: Schema.Money) => Option(fun)
		given putBoolean: Conversion[Boolean, Option[Boolean]] = (fun: Boolean) => Option(fun)
		given putBigDecimal: Conversion[BigDecimal, Option[BigDecimal]] = (fun: BigDecimal) => Option(fun)
		given putString: Conversion[String, Option[String]] = (fun: String) => Option(fun)
		given putInt: Conversion[Int, Option[Int]] = (fun: Int) => Option(fun)
		given putSchemaLatLngBox: Conversion[Schema.LatLngBox, Option[Schema.LatLngBox]] = (fun: Schema.LatLngBox) => Option(fun)
		given putSchemaSizeAndSunshineStats: Conversion[Schema.SizeAndSunshineStats, Option[Schema.SizeAndSunshineStats]] = (fun: Schema.SizeAndSunshineStats) => Option(fun)
		given putSchemaLatLng: Conversion[Schema.LatLng, Option[Schema.LatLng]] = (fun: Schema.LatLng) => Option(fun)
		given putListSchemaRoofSegmentSummary: Conversion[List[Schema.RoofSegmentSummary], Option[List[Schema.RoofSegmentSummary]]] = (fun: List[Schema.RoofSegmentSummary]) => Option(fun)
		given putSchemaDate: Conversion[Schema.Date, Option[Schema.Date]] = (fun: Schema.Date) => Option(fun)
		given putSchemaSolarPotential: Conversion[Schema.SolarPotential, Option[Schema.SolarPotential]] = (fun: Schema.SolarPotential) => Option(fun)
		given putSchemaBuildingInsightsImageryQualityEnum: Conversion[Schema.BuildingInsights.ImageryQualityEnum, Option[Schema.BuildingInsights.ImageryQualityEnum]] = (fun: Schema.BuildingInsights.ImageryQualityEnum) => Option(fun)
		given putSchemaSolarPanelOrientationEnum: Conversion[Schema.SolarPanel.OrientationEnum, Option[Schema.SolarPanel.OrientationEnum]] = (fun: Schema.SolarPanel.OrientationEnum) => Option(fun)
		given putSchemaSavingsOverTime: Conversion[Schema.SavingsOverTime, Option[Schema.SavingsOverTime]] = (fun: Schema.SavingsOverTime) => Option(fun)
		given putListMapStringJsValue: Conversion[List[Map[String, JsValue]], Option[List[Map[String, JsValue]]]] = (fun: List[Map[String, JsValue]]) => Option(fun)
		given putSchemaDataLayersImageryQualityEnum: Conversion[Schema.DataLayers.ImageryQualityEnum, Option[Schema.DataLayers.ImageryQualityEnum]] = (fun: Schema.DataLayers.ImageryQualityEnum) => Option(fun)
		given putListString: Conversion[List[String], Option[List[String]]] = (fun: List[String]) => Option(fun)
		given putSchemaFinancedPurchaseSavings: Conversion[Schema.FinancedPurchaseSavings, Option[Schema.FinancedPurchaseSavings]] = (fun: Schema.FinancedPurchaseSavings) => Option(fun)
		given putSchemaFinancialDetails: Conversion[Schema.FinancialDetails, Option[Schema.FinancialDetails]] = (fun: Schema.FinancialDetails) => Option(fun)
		given putSchemaCashPurchaseSavings: Conversion[Schema.CashPurchaseSavings, Option[Schema.CashPurchaseSavings]] = (fun: Schema.CashPurchaseSavings) => Option(fun)
		given putSchemaLeasingSavings: Conversion[Schema.LeasingSavings, Option[Schema.LeasingSavings]] = (fun: Schema.LeasingSavings) => Option(fun)
		given putListBigDecimal: Conversion[List[BigDecimal], Option[List[BigDecimal]]] = (fun: List[BigDecimal]) => Option(fun)
		given putListSchemaSolarPanel: Conversion[List[Schema.SolarPanel], Option[List[Schema.SolarPanel]]] = (fun: List[Schema.SolarPanel]) => Option(fun)
		given putListSchemaSolarPanelConfig: Conversion[List[Schema.SolarPanelConfig], Option[List[Schema.SolarPanelConfig]]] = (fun: List[Schema.SolarPanelConfig]) => Option(fun)
		given putListSchemaRoofSegmentSizeAndSunshineStats: Conversion[List[Schema.RoofSegmentSizeAndSunshineStats], Option[List[Schema.RoofSegmentSizeAndSunshineStats]]] = (fun: List[Schema.RoofSegmentSizeAndSunshineStats]) => Option(fun)
		given putListSchemaFinancialAnalysis: Conversion[List[Schema.FinancialAnalysis], Option[List[Schema.FinancialAnalysis]]] = (fun: List[Schema.FinancialAnalysis]) => Option(fun)
	}
	object OptionDefault {
		given getString: Conversion[Option[String], String] = (fun: Option[String]) => fun.getOrElse("")
	}
}
