package com.boresjo.play.api.google.cloudbilling

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Formats {
	given fmtBillingAccount: Format[Schema.BillingAccount] = Json.format[Schema.BillingAccount]
	given fmtListBillingAccountsResponse: Format[Schema.ListBillingAccountsResponse] = Json.format[Schema.ListBillingAccountsResponse]
	given fmtListProjectBillingInfoResponse: Format[Schema.ListProjectBillingInfoResponse] = Json.format[Schema.ListProjectBillingInfoResponse]
	given fmtProjectBillingInfo: Format[Schema.ProjectBillingInfo] = Json.format[Schema.ProjectBillingInfo]
	given fmtPolicy: Format[Schema.Policy] = Json.format[Schema.Policy]
	given fmtBinding: Format[Schema.Binding] = Json.format[Schema.Binding]
	given fmtAuditConfig: Format[Schema.AuditConfig] = Json.format[Schema.AuditConfig]
	given fmtExpr: Format[Schema.Expr] = Json.format[Schema.Expr]
	given fmtAuditLogConfig: Format[Schema.AuditLogConfig] = Json.format[Schema.AuditLogConfig]
	given fmtAuditLogConfigLogTypeEnum: Format[Schema.AuditLogConfig.LogTypeEnum] = JsonEnumFormat[Schema.AuditLogConfig.LogTypeEnum]
	given fmtSetIamPolicyRequest: Format[Schema.SetIamPolicyRequest] = Json.format[Schema.SetIamPolicyRequest]
	given fmtTestIamPermissionsRequest: Format[Schema.TestIamPermissionsRequest] = Json.format[Schema.TestIamPermissionsRequest]
	given fmtTestIamPermissionsResponse: Format[Schema.TestIamPermissionsResponse] = Json.format[Schema.TestIamPermissionsResponse]
	given fmtMoveBillingAccountRequest: Format[Schema.MoveBillingAccountRequest] = Json.format[Schema.MoveBillingAccountRequest]
	given fmtListServicesResponse: Format[Schema.ListServicesResponse] = Json.format[Schema.ListServicesResponse]
	given fmtService: Format[Schema.Service] = Json.format[Schema.Service]
	given fmtListSkusResponse: Format[Schema.ListSkusResponse] = Json.format[Schema.ListSkusResponse]
	given fmtSku: Format[Schema.Sku] = Json.format[Schema.Sku]
	given fmtCategory: Format[Schema.Category] = Json.format[Schema.Category]
	given fmtPricingInfo: Format[Schema.PricingInfo] = Json.format[Schema.PricingInfo]
	given fmtGeoTaxonomy: Format[Schema.GeoTaxonomy] = Json.format[Schema.GeoTaxonomy]
	given fmtPricingExpression: Format[Schema.PricingExpression] = Json.format[Schema.PricingExpression]
	given fmtAggregationInfo: Format[Schema.AggregationInfo] = Json.format[Schema.AggregationInfo]
	given fmtTierRate: Format[Schema.TierRate] = Json.format[Schema.TierRate]
	given fmtMoney: Format[Schema.Money] = Json.format[Schema.Money]
	given fmtAggregationInfoAggregationLevelEnum: Format[Schema.AggregationInfo.AggregationLevelEnum] = JsonEnumFormat[Schema.AggregationInfo.AggregationLevelEnum]
	given fmtAggregationInfoAggregationIntervalEnum: Format[Schema.AggregationInfo.AggregationIntervalEnum] = JsonEnumFormat[Schema.AggregationInfo.AggregationIntervalEnum]
	given fmtGeoTaxonomyTypeEnum: Format[Schema.GeoTaxonomy.TypeEnum] = JsonEnumFormat[Schema.GeoTaxonomy.TypeEnum]
}
