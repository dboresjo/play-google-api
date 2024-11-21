package com.boresjo.play.api.google.solar

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Formats {
	given fmtFinancialDetails: Format[Schema.FinancialDetails] = Json.format[Schema.FinancialDetails]
	given fmtMoney: Format[Schema.Money] = Json.format[Schema.Money]
	given fmtRoofSegmentSizeAndSunshineStats: Format[Schema.RoofSegmentSizeAndSunshineStats] = Json.format[Schema.RoofSegmentSizeAndSunshineStats]
	given fmtLatLngBox: Format[Schema.LatLngBox] = Json.format[Schema.LatLngBox]
	given fmtSizeAndSunshineStats: Format[Schema.SizeAndSunshineStats] = Json.format[Schema.SizeAndSunshineStats]
	given fmtLatLng: Format[Schema.LatLng] = Json.format[Schema.LatLng]
	given fmtSavingsOverTime: Format[Schema.SavingsOverTime] = Json.format[Schema.SavingsOverTime]
	given fmtSolarPanelConfig: Format[Schema.SolarPanelConfig] = Json.format[Schema.SolarPanelConfig]
	given fmtRoofSegmentSummary: Format[Schema.RoofSegmentSummary] = Json.format[Schema.RoofSegmentSummary]
	given fmtBuildingInsights: Format[Schema.BuildingInsights] = Json.format[Schema.BuildingInsights]
	given fmtDate: Format[Schema.Date] = Json.format[Schema.Date]
	given fmtSolarPotential: Format[Schema.SolarPotential] = Json.format[Schema.SolarPotential]
	given fmtBuildingInsightsImageryQualityEnum: Format[Schema.BuildingInsights.ImageryQualityEnum] = JsonEnumFormat[Schema.BuildingInsights.ImageryQualityEnum]
	given fmtSolarPanel: Format[Schema.SolarPanel] = Json.format[Schema.SolarPanel]
	given fmtSolarPanelOrientationEnum: Format[Schema.SolarPanel.OrientationEnum] = JsonEnumFormat[Schema.SolarPanel.OrientationEnum]
	given fmtCashPurchaseSavings: Format[Schema.CashPurchaseSavings] = Json.format[Schema.CashPurchaseSavings]
	given fmtHttpBody: Format[Schema.HttpBody] = Json.format[Schema.HttpBody]
	given fmtFinancedPurchaseSavings: Format[Schema.FinancedPurchaseSavings] = Json.format[Schema.FinancedPurchaseSavings]
	given fmtDataLayers: Format[Schema.DataLayers] = Json.format[Schema.DataLayers]
	given fmtDataLayersImageryQualityEnum: Format[Schema.DataLayers.ImageryQualityEnum] = JsonEnumFormat[Schema.DataLayers.ImageryQualityEnum]
	given fmtFinancialAnalysis: Format[Schema.FinancialAnalysis] = Json.format[Schema.FinancialAnalysis]
	given fmtLeasingSavings: Format[Schema.LeasingSavings] = Json.format[Schema.LeasingSavings]
}
