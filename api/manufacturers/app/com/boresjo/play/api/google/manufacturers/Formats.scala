package com.boresjo.play.api.google.manufacturers

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Formats {
	given fmtListProductsResponse: Format[Schema.ListProductsResponse] = Json.format[Schema.ListProductsResponse]
	given fmtProduct: Format[Schema.Product] = Json.format[Schema.Product]
	given fmtAttributes: Format[Schema.Attributes] = Json.format[Schema.Attributes]
	given fmtIssue: Format[Schema.Issue] = Json.format[Schema.Issue]
	given fmtDestinationStatus: Format[Schema.DestinationStatus] = Json.format[Schema.DestinationStatus]
	given fmtFeatureDescription: Format[Schema.FeatureDescription] = Json.format[Schema.FeatureDescription]
	given fmtImage: Format[Schema.Image] = Json.format[Schema.Image]
	given fmtPrice: Format[Schema.Price] = Json.format[Schema.Price]
	given fmtProductDetail: Format[Schema.ProductDetail] = Json.format[Schema.ProductDetail]
	given fmtCapacity: Format[Schema.Capacity] = Json.format[Schema.Capacity]
	given fmtCount: Format[Schema.Count] = Json.format[Schema.Count]
	given fmtNutrition: Format[Schema.Nutrition] = Json.format[Schema.Nutrition]
	given fmtGrocery: Format[Schema.Grocery] = Json.format[Schema.Grocery]
	given fmtGoogleShoppingManufacturersV1ProductCertification: Format[Schema.GoogleShoppingManufacturersV1ProductCertification] = Json.format[Schema.GoogleShoppingManufacturersV1ProductCertification]
	given fmtImageTypeEnum: Format[Schema.Image.TypeEnum] = JsonEnumFormat[Schema.Image.TypeEnum]
	given fmtImageStatusEnum: Format[Schema.Image.StatusEnum] = JsonEnumFormat[Schema.Image.StatusEnum]
	given fmtFloatUnit: Format[Schema.FloatUnit] = Json.format[Schema.FloatUnit]
	given fmtVoluntaryNutritionFact: Format[Schema.VoluntaryNutritionFact] = Json.format[Schema.VoluntaryNutritionFact]
	given fmtIssueSeverityEnum: Format[Schema.Issue.SeverityEnum] = JsonEnumFormat[Schema.Issue.SeverityEnum]
	given fmtIssueResolutionEnum: Format[Schema.Issue.ResolutionEnum] = JsonEnumFormat[Schema.Issue.ResolutionEnum]
	given fmtDestinationStatusStatusEnum: Format[Schema.DestinationStatus.StatusEnum] = JsonEnumFormat[Schema.DestinationStatus.StatusEnum]
	given fmtEmpty: Format[Schema.Empty] = Json.format[Schema.Empty]
	given fmtProductCertification: Format[Schema.ProductCertification] = Json.format[Schema.ProductCertification]
	given fmtCertification: Format[Schema.Certification] = Json.format[Schema.Certification]
	given fmtListProductCertificationsResponse: Format[Schema.ListProductCertificationsResponse] = Json.format[Schema.ListProductCertificationsResponse]
}
