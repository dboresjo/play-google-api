package com.boresjo.play.api.google.pagespeedonline

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Helpers {
	import Schema.*
	object AutoOption {
		given putString: Conversion[String, Option[String]] = (fun: String) => Option(fun)
		given putSchemaPagespeedApiLoadingExperienceV5: Conversion[Schema.PagespeedApiLoadingExperienceV5, Option[Schema.PagespeedApiLoadingExperienceV5]] = (fun: Schema.PagespeedApiLoadingExperienceV5) => Option(fun)
		given putSchemaLighthouseResultV5: Conversion[Schema.LighthouseResultV5, Option[Schema.LighthouseResultV5]] = (fun: Schema.LighthouseResultV5) => Option(fun)
		given putSchemaPagespeedVersion: Conversion[Schema.PagespeedVersion, Option[Schema.PagespeedVersion]] = (fun: Schema.PagespeedVersion) => Option(fun)
		given putMapStringSchemaUserPageLoadMetricV5: Conversion[Map[String, Schema.UserPageLoadMetricV5], Option[Map[String, Schema.UserPageLoadMetricV5]]] = (fun: Map[String, Schema.UserPageLoadMetricV5]) => Option(fun)
		given putBoolean: Conversion[Boolean, Option[Boolean]] = (fun: Boolean) => Option(fun)
		given putListSchemaBucket: Conversion[List[Schema.Bucket], Option[List[Schema.Bucket]]] = (fun: List[Schema.Bucket]) => Option(fun)
		given putInt: Conversion[Int, Option[Int]] = (fun: Int) => Option(fun)
		given putBigDecimal: Conversion[BigDecimal, Option[BigDecimal]] = (fun: BigDecimal) => Option(fun)
		given putSchemaI18n: Conversion[Schema.I18n, Option[Schema.I18n]] = (fun: Schema.I18n) => Option(fun)
		given putMapStringSchemaLighthouseAuditResultV5: Conversion[Map[String, Schema.LighthouseAuditResultV5], Option[Map[String, Schema.LighthouseAuditResultV5]]] = (fun: Map[String, Schema.LighthouseAuditResultV5]) => Option(fun)
		given putMapStringSchemaCategoryGroupV5: Conversion[Map[String, Schema.CategoryGroupV5], Option[Map[String, Schema.CategoryGroupV5]]] = (fun: Map[String, Schema.CategoryGroupV5]) => Option(fun)
		given putListSchemaStackPack: Conversion[List[Schema.StackPack], Option[List[Schema.StackPack]]] = (fun: List[Schema.StackPack]) => Option(fun)
		given putSchemaEnvironment: Conversion[Schema.Environment, Option[Schema.Environment]] = (fun: Schema.Environment) => Option(fun)
		given putListJsValue: Conversion[List[JsValue], Option[List[JsValue]]] = (fun: List[JsValue]) => Option(fun)
		given putSchemaRuntimeError: Conversion[Schema.RuntimeError, Option[Schema.RuntimeError]] = (fun: Schema.RuntimeError) => Option(fun)
		given putSchemaCategories: Conversion[Schema.Categories, Option[Schema.Categories]] = (fun: Schema.Categories) => Option(fun)
		given putSchemaTiming: Conversion[Schema.Timing, Option[Schema.Timing]] = (fun: Schema.Timing) => Option(fun)
		given putSchemaConfigSettings: Conversion[Schema.ConfigSettings, Option[Schema.ConfigSettings]] = (fun: Schema.ConfigSettings) => Option(fun)
		given putJsValue: Conversion[JsValue, Option[JsValue]] = (fun: JsValue) => Option(fun)
		given putListSchemaLhrEntity: Conversion[List[Schema.LhrEntity], Option[List[Schema.LhrEntity]]] = (fun: List[Schema.LhrEntity]) => Option(fun)
		given putSchemaRendererFormattedStrings: Conversion[Schema.RendererFormattedStrings, Option[Schema.RendererFormattedStrings]] = (fun: Schema.RendererFormattedStrings) => Option(fun)
		given putMapStringJsValue: Conversion[Map[String, JsValue], Option[Map[String, JsValue]]] = (fun: Map[String, JsValue]) => Option(fun)
		given putSchemaMetricSavings: Conversion[Schema.MetricSavings, Option[Schema.MetricSavings]] = (fun: Schema.MetricSavings) => Option(fun)
		given putMapStringString: Conversion[Map[String, String], Option[Map[String, String]]] = (fun: Map[String, String]) => Option(fun)
		given putSchemaLighthouseCategoryV5: Conversion[Schema.LighthouseCategoryV5, Option[Schema.LighthouseCategoryV5]] = (fun: Schema.LighthouseCategoryV5) => Option(fun)
		given putListSchemaAuditRefs: Conversion[List[Schema.AuditRefs], Option[List[Schema.AuditRefs]]] = (fun: List[Schema.AuditRefs]) => Option(fun)
		given putListString: Conversion[List[String], Option[List[String]]] = (fun: List[String]) => Option(fun)
	}
	object OptionDefault {
		given getString: Conversion[Option[String], String] = (fun: Option[String]) => fun.getOrElse("")
	}
}
