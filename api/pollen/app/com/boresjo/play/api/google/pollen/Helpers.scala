package com.boresjo.play.api.google.pollen

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Helpers {
	import Schema.*
	object AutoOption {
		given putListMapStringJsValue: Conversion[List[Map[String, JsValue]], Option[List[Map[String, JsValue]]]] = (fun: List[Map[String, JsValue]]) => Option(fun)
		given putString: Conversion[String, Option[String]] = (fun: String) => Option(fun)
		given putInt: Conversion[Int, Option[Int]] = (fun: Int) => Option(fun)
		given putBigDecimal: Conversion[BigDecimal, Option[BigDecimal]] = (fun: BigDecimal) => Option(fun)
		given putSchemaPollenTypeInfoCodeEnum: Conversion[Schema.PollenTypeInfo.CodeEnum, Option[Schema.PollenTypeInfo.CodeEnum]] = (fun: Schema.PollenTypeInfo.CodeEnum) => Option(fun)
		given putSchemaIndexInfo: Conversion[Schema.IndexInfo, Option[Schema.IndexInfo]] = (fun: Schema.IndexInfo) => Option(fun)
		given putListString: Conversion[List[String], Option[List[String]]] = (fun: List[String]) => Option(fun)
		given putBoolean: Conversion[Boolean, Option[Boolean]] = (fun: Boolean) => Option(fun)
		given putSchemaDate: Conversion[Schema.Date, Option[Schema.Date]] = (fun: Schema.Date) => Option(fun)
		given putListSchemaPollenTypeInfo: Conversion[List[Schema.PollenTypeInfo], Option[List[Schema.PollenTypeInfo]]] = (fun: List[Schema.PollenTypeInfo]) => Option(fun)
		given putListSchemaPlantInfo: Conversion[List[Schema.PlantInfo], Option[List[Schema.PlantInfo]]] = (fun: List[Schema.PlantInfo]) => Option(fun)
		given putListSchemaDayInfo: Conversion[List[Schema.DayInfo], Option[List[Schema.DayInfo]]] = (fun: List[Schema.DayInfo]) => Option(fun)
		given putSchemaPlantInfoCodeEnum: Conversion[Schema.PlantInfo.CodeEnum, Option[Schema.PlantInfo.CodeEnum]] = (fun: Schema.PlantInfo.CodeEnum) => Option(fun)
		given putSchemaPlantDescription: Conversion[Schema.PlantDescription, Option[Schema.PlantDescription]] = (fun: Schema.PlantDescription) => Option(fun)
		given putSchemaColor: Conversion[Schema.Color, Option[Schema.Color]] = (fun: Schema.Color) => Option(fun)
		given putSchemaIndexInfoCodeEnum: Conversion[Schema.IndexInfo.CodeEnum, Option[Schema.IndexInfo.CodeEnum]] = (fun: Schema.IndexInfo.CodeEnum) => Option(fun)
		given putSchemaPlantDescriptionTypeEnum: Conversion[Schema.PlantDescription.TypeEnum, Option[Schema.PlantDescription.TypeEnum]] = (fun: Schema.PlantDescription.TypeEnum) => Option(fun)
	}
	object OptionDefault {
		given getString: Conversion[Option[String], String] = (fun: Option[String]) => fun.getOrElse("")
	}
}
