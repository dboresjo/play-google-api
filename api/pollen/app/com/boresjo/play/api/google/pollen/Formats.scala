package com.boresjo.play.api.google.pollen

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Formats {
	given fmtHttpBody: Format[Schema.HttpBody] = Json.format[Schema.HttpBody]
	given fmtDate: Format[Schema.Date] = Json.format[Schema.Date]
	given fmtColor: Format[Schema.Color] = Json.format[Schema.Color]
	given fmtPollenTypeInfo: Format[Schema.PollenTypeInfo] = Json.format[Schema.PollenTypeInfo]
	given fmtPollenTypeInfoCodeEnum: Format[Schema.PollenTypeInfo.CodeEnum] = JsonEnumFormat[Schema.PollenTypeInfo.CodeEnum]
	given fmtIndexInfo: Format[Schema.IndexInfo] = Json.format[Schema.IndexInfo]
	given fmtDayInfo: Format[Schema.DayInfo] = Json.format[Schema.DayInfo]
	given fmtPlantInfo: Format[Schema.PlantInfo] = Json.format[Schema.PlantInfo]
	given fmtLookupForecastResponse: Format[Schema.LookupForecastResponse] = Json.format[Schema.LookupForecastResponse]
	given fmtPlantInfoCodeEnum: Format[Schema.PlantInfo.CodeEnum] = JsonEnumFormat[Schema.PlantInfo.CodeEnum]
	given fmtPlantDescription: Format[Schema.PlantDescription] = Json.format[Schema.PlantDescription]
	given fmtIndexInfoCodeEnum: Format[Schema.IndexInfo.CodeEnum] = JsonEnumFormat[Schema.IndexInfo.CodeEnum]
	given fmtPlantDescriptionTypeEnum: Format[Schema.PlantDescription.TypeEnum] = JsonEnumFormat[Schema.PlantDescription.TypeEnum]
}
