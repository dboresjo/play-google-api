package com.boresjo.play.api.google.cloudbilling

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Helpers {
	import Schema.*
	object AutoOption {
		given putString: Conversion[String, Option[String]] = (fun: String) => Option(fun)
		given putBoolean: Conversion[Boolean, Option[Boolean]] = (fun: Boolean) => Option(fun)
		given putListSchemaBillingAccount: Conversion[List[Schema.BillingAccount], Option[List[Schema.BillingAccount]]] = (fun: List[Schema.BillingAccount]) => Option(fun)
		given putListSchemaProjectBillingInfo: Conversion[List[Schema.ProjectBillingInfo], Option[List[Schema.ProjectBillingInfo]]] = (fun: List[Schema.ProjectBillingInfo]) => Option(fun)
		given putInt: Conversion[Int, Option[Int]] = (fun: Int) => Option(fun)
		given putListSchemaBinding: Conversion[List[Schema.Binding], Option[List[Schema.Binding]]] = (fun: List[Schema.Binding]) => Option(fun)
		given putListSchemaAuditConfig: Conversion[List[Schema.AuditConfig], Option[List[Schema.AuditConfig]]] = (fun: List[Schema.AuditConfig]) => Option(fun)
		given putListString: Conversion[List[String], Option[List[String]]] = (fun: List[String]) => Option(fun)
		given putSchemaExpr: Conversion[Schema.Expr, Option[Schema.Expr]] = (fun: Schema.Expr) => Option(fun)
		given putListSchemaAuditLogConfig: Conversion[List[Schema.AuditLogConfig], Option[List[Schema.AuditLogConfig]]] = (fun: List[Schema.AuditLogConfig]) => Option(fun)
		given putSchemaAuditLogConfigLogTypeEnum: Conversion[Schema.AuditLogConfig.LogTypeEnum, Option[Schema.AuditLogConfig.LogTypeEnum]] = (fun: Schema.AuditLogConfig.LogTypeEnum) => Option(fun)
		given putSchemaPolicy: Conversion[Schema.Policy, Option[Schema.Policy]] = (fun: Schema.Policy) => Option(fun)
		given putListSchemaService: Conversion[List[Schema.Service], Option[List[Schema.Service]]] = (fun: List[Schema.Service]) => Option(fun)
		given putListSchemaSku: Conversion[List[Schema.Sku], Option[List[Schema.Sku]]] = (fun: List[Schema.Sku]) => Option(fun)
		given putSchemaCategory: Conversion[Schema.Category, Option[Schema.Category]] = (fun: Schema.Category) => Option(fun)
		given putListSchemaPricingInfo: Conversion[List[Schema.PricingInfo], Option[List[Schema.PricingInfo]]] = (fun: List[Schema.PricingInfo]) => Option(fun)
		given putSchemaGeoTaxonomy: Conversion[Schema.GeoTaxonomy, Option[Schema.GeoTaxonomy]] = (fun: Schema.GeoTaxonomy) => Option(fun)
		given putSchemaPricingExpression: Conversion[Schema.PricingExpression, Option[Schema.PricingExpression]] = (fun: Schema.PricingExpression) => Option(fun)
		given putSchemaAggregationInfo: Conversion[Schema.AggregationInfo, Option[Schema.AggregationInfo]] = (fun: Schema.AggregationInfo) => Option(fun)
		given putBigDecimal: Conversion[BigDecimal, Option[BigDecimal]] = (fun: BigDecimal) => Option(fun)
		given putListSchemaTierRate: Conversion[List[Schema.TierRate], Option[List[Schema.TierRate]]] = (fun: List[Schema.TierRate]) => Option(fun)
		given putSchemaMoney: Conversion[Schema.Money, Option[Schema.Money]] = (fun: Schema.Money) => Option(fun)
		given putSchemaAggregationInfoAggregationLevelEnum: Conversion[Schema.AggregationInfo.AggregationLevelEnum, Option[Schema.AggregationInfo.AggregationLevelEnum]] = (fun: Schema.AggregationInfo.AggregationLevelEnum) => Option(fun)
		given putSchemaAggregationInfoAggregationIntervalEnum: Conversion[Schema.AggregationInfo.AggregationIntervalEnum, Option[Schema.AggregationInfo.AggregationIntervalEnum]] = (fun: Schema.AggregationInfo.AggregationIntervalEnum) => Option(fun)
		given putSchemaGeoTaxonomyTypeEnum: Conversion[Schema.GeoTaxonomy.TypeEnum, Option[Schema.GeoTaxonomy.TypeEnum]] = (fun: Schema.GeoTaxonomy.TypeEnum) => Option(fun)
	}
	object OptionDefault {
		given getString: Conversion[Option[String], String] = (fun: Option[String]) => fun.getOrElse("")
	}
}
