package com.boresjo.play.api.google.recommendationengine

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Formats {
	given fmtGoogleLongrunningListOperationsResponse: Format[Schema.GoogleLongrunningListOperationsResponse] = Json.format[Schema.GoogleLongrunningListOperationsResponse]
	given fmtGoogleLongrunningOperation: Format[Schema.GoogleLongrunningOperation] = Json.format[Schema.GoogleLongrunningOperation]
	given fmtGoogleRpcStatus: Format[Schema.GoogleRpcStatus] = Json.format[Schema.GoogleRpcStatus]
	given fmtGoogleCloudRecommendationengineV1beta1ListCatalogsResponse: Format[Schema.GoogleCloudRecommendationengineV1beta1ListCatalogsResponse] = Json.format[Schema.GoogleCloudRecommendationengineV1beta1ListCatalogsResponse]
	given fmtGoogleCloudRecommendationengineV1beta1Catalog: Format[Schema.GoogleCloudRecommendationengineV1beta1Catalog] = Json.format[Schema.GoogleCloudRecommendationengineV1beta1Catalog]
	given fmtGoogleCloudRecommendationengineV1beta1CatalogItemLevelConfig: Format[Schema.GoogleCloudRecommendationengineV1beta1CatalogItemLevelConfig] = Json.format[Schema.GoogleCloudRecommendationengineV1beta1CatalogItemLevelConfig]
	given fmtGoogleCloudRecommendationengineV1beta1CatalogItemLevelConfigEventItemLevelEnum: Format[Schema.GoogleCloudRecommendationengineV1beta1CatalogItemLevelConfig.EventItemLevelEnum] = JsonEnumFormat[Schema.GoogleCloudRecommendationengineV1beta1CatalogItemLevelConfig.EventItemLevelEnum]
	given fmtGoogleCloudRecommendationengineV1beta1CatalogItemLevelConfigPredictItemLevelEnum: Format[Schema.GoogleCloudRecommendationengineV1beta1CatalogItemLevelConfig.PredictItemLevelEnum] = JsonEnumFormat[Schema.GoogleCloudRecommendationengineV1beta1CatalogItemLevelConfig.PredictItemLevelEnum]
	given fmtGoogleCloudRecommendationengineV1beta1CatalogItem: Format[Schema.GoogleCloudRecommendationengineV1beta1CatalogItem] = Json.format[Schema.GoogleCloudRecommendationengineV1beta1CatalogItem]
	given fmtGoogleCloudRecommendationengineV1beta1CatalogItemCategoryHierarchy: Format[Schema.GoogleCloudRecommendationengineV1beta1CatalogItemCategoryHierarchy] = Json.format[Schema.GoogleCloudRecommendationengineV1beta1CatalogItemCategoryHierarchy]
	given fmtGoogleCloudRecommendationengineV1beta1FeatureMap: Format[Schema.GoogleCloudRecommendationengineV1beta1FeatureMap] = Json.format[Schema.GoogleCloudRecommendationengineV1beta1FeatureMap]
	given fmtGoogleCloudRecommendationengineV1beta1ProductCatalogItem: Format[Schema.GoogleCloudRecommendationengineV1beta1ProductCatalogItem] = Json.format[Schema.GoogleCloudRecommendationengineV1beta1ProductCatalogItem]
	given fmtGoogleCloudRecommendationengineV1beta1FeatureMapStringList: Format[Schema.GoogleCloudRecommendationengineV1beta1FeatureMapStringList] = Json.format[Schema.GoogleCloudRecommendationengineV1beta1FeatureMapStringList]
	given fmtGoogleCloudRecommendationengineV1beta1FeatureMapFloatList: Format[Schema.GoogleCloudRecommendationengineV1beta1FeatureMapFloatList] = Json.format[Schema.GoogleCloudRecommendationengineV1beta1FeatureMapFloatList]
	given fmtGoogleCloudRecommendationengineV1beta1ProductCatalogItemExactPrice: Format[Schema.GoogleCloudRecommendationengineV1beta1ProductCatalogItemExactPrice] = Json.format[Schema.GoogleCloudRecommendationengineV1beta1ProductCatalogItemExactPrice]
	given fmtGoogleCloudRecommendationengineV1beta1ProductCatalogItemPriceRange: Format[Schema.GoogleCloudRecommendationengineV1beta1ProductCatalogItemPriceRange] = Json.format[Schema.GoogleCloudRecommendationengineV1beta1ProductCatalogItemPriceRange]
	given fmtGoogleCloudRecommendationengineV1beta1ProductCatalogItemStockStateEnum: Format[Schema.GoogleCloudRecommendationengineV1beta1ProductCatalogItem.StockStateEnum] = JsonEnumFormat[Schema.GoogleCloudRecommendationengineV1beta1ProductCatalogItem.StockStateEnum]
	given fmtGoogleCloudRecommendationengineV1beta1Image: Format[Schema.GoogleCloudRecommendationengineV1beta1Image] = Json.format[Schema.GoogleCloudRecommendationengineV1beta1Image]
	given fmtGoogleCloudRecommendationengineV1beta1ListCatalogItemsResponse: Format[Schema.GoogleCloudRecommendationengineV1beta1ListCatalogItemsResponse] = Json.format[Schema.GoogleCloudRecommendationengineV1beta1ListCatalogItemsResponse]
	given fmtGoogleProtobufEmpty: Format[Schema.GoogleProtobufEmpty] = Json.format[Schema.GoogleProtobufEmpty]
	given fmtGoogleCloudRecommendationengineV1beta1ImportCatalogItemsRequest: Format[Schema.GoogleCloudRecommendationengineV1beta1ImportCatalogItemsRequest] = Json.format[Schema.GoogleCloudRecommendationengineV1beta1ImportCatalogItemsRequest]
	given fmtGoogleCloudRecommendationengineV1beta1InputConfig: Format[Schema.GoogleCloudRecommendationengineV1beta1InputConfig] = Json.format[Schema.GoogleCloudRecommendationengineV1beta1InputConfig]
	given fmtGoogleCloudRecommendationengineV1beta1ImportErrorsConfig: Format[Schema.GoogleCloudRecommendationengineV1beta1ImportErrorsConfig] = Json.format[Schema.GoogleCloudRecommendationengineV1beta1ImportErrorsConfig]
	given fmtGoogleCloudRecommendationengineV1beta1CatalogInlineSource: Format[Schema.GoogleCloudRecommendationengineV1beta1CatalogInlineSource] = Json.format[Schema.GoogleCloudRecommendationengineV1beta1CatalogInlineSource]
	given fmtGoogleCloudRecommendationengineV1beta1GcsSource: Format[Schema.GoogleCloudRecommendationengineV1beta1GcsSource] = Json.format[Schema.GoogleCloudRecommendationengineV1beta1GcsSource]
	given fmtGoogleCloudRecommendationengineV1beta1UserEventInlineSource: Format[Schema.GoogleCloudRecommendationengineV1beta1UserEventInlineSource] = Json.format[Schema.GoogleCloudRecommendationengineV1beta1UserEventInlineSource]
	given fmtGoogleCloudRecommendationengineV1beta1BigQuerySource: Format[Schema.GoogleCloudRecommendationengineV1beta1BigQuerySource] = Json.format[Schema.GoogleCloudRecommendationengineV1beta1BigQuerySource]
	given fmtGoogleCloudRecommendationengineV1beta1UserEvent: Format[Schema.GoogleCloudRecommendationengineV1beta1UserEvent] = Json.format[Schema.GoogleCloudRecommendationengineV1beta1UserEvent]
	given fmtGoogleCloudRecommendationengineV1beta1UserInfo: Format[Schema.GoogleCloudRecommendationengineV1beta1UserInfo] = Json.format[Schema.GoogleCloudRecommendationengineV1beta1UserInfo]
	given fmtGoogleCloudRecommendationengineV1beta1EventDetail: Format[Schema.GoogleCloudRecommendationengineV1beta1EventDetail] = Json.format[Schema.GoogleCloudRecommendationengineV1beta1EventDetail]
	given fmtGoogleCloudRecommendationengineV1beta1ProductEventDetail: Format[Schema.GoogleCloudRecommendationengineV1beta1ProductEventDetail] = Json.format[Schema.GoogleCloudRecommendationengineV1beta1ProductEventDetail]
	given fmtGoogleCloudRecommendationengineV1beta1UserEventEventSourceEnum: Format[Schema.GoogleCloudRecommendationengineV1beta1UserEvent.EventSourceEnum] = JsonEnumFormat[Schema.GoogleCloudRecommendationengineV1beta1UserEvent.EventSourceEnum]
	given fmtGoogleCloudRecommendationengineV1beta1ProductDetail: Format[Schema.GoogleCloudRecommendationengineV1beta1ProductDetail] = Json.format[Schema.GoogleCloudRecommendationengineV1beta1ProductDetail]
	given fmtGoogleCloudRecommendationengineV1beta1PurchaseTransaction: Format[Schema.GoogleCloudRecommendationengineV1beta1PurchaseTransaction] = Json.format[Schema.GoogleCloudRecommendationengineV1beta1PurchaseTransaction]
	given fmtGoogleCloudRecommendationengineV1beta1ProductDetailStockStateEnum: Format[Schema.GoogleCloudRecommendationengineV1beta1ProductDetail.StockStateEnum] = JsonEnumFormat[Schema.GoogleCloudRecommendationengineV1beta1ProductDetail.StockStateEnum]
	given fmtGoogleCloudRecommendationengineV1beta1CreatePredictionApiKeyRegistrationRequest: Format[Schema.GoogleCloudRecommendationengineV1beta1CreatePredictionApiKeyRegistrationRequest] = Json.format[Schema.GoogleCloudRecommendationengineV1beta1CreatePredictionApiKeyRegistrationRequest]
	given fmtGoogleCloudRecommendationengineV1beta1PredictionApiKeyRegistration: Format[Schema.GoogleCloudRecommendationengineV1beta1PredictionApiKeyRegistration] = Json.format[Schema.GoogleCloudRecommendationengineV1beta1PredictionApiKeyRegistration]
	given fmtGoogleCloudRecommendationengineV1beta1ListPredictionApiKeyRegistrationsResponse: Format[Schema.GoogleCloudRecommendationengineV1beta1ListPredictionApiKeyRegistrationsResponse] = Json.format[Schema.GoogleCloudRecommendationengineV1beta1ListPredictionApiKeyRegistrationsResponse]
	given fmtGoogleCloudRecommendationengineV1beta1PredictRequest: Format[Schema.GoogleCloudRecommendationengineV1beta1PredictRequest] = Json.format[Schema.GoogleCloudRecommendationengineV1beta1PredictRequest]
	given fmtGoogleCloudRecommendationengineV1beta1PredictResponse: Format[Schema.GoogleCloudRecommendationengineV1beta1PredictResponse] = Json.format[Schema.GoogleCloudRecommendationengineV1beta1PredictResponse]
	given fmtGoogleCloudRecommendationengineV1beta1PredictResponsePredictionResult: Format[Schema.GoogleCloudRecommendationengineV1beta1PredictResponsePredictionResult] = Json.format[Schema.GoogleCloudRecommendationengineV1beta1PredictResponsePredictionResult]
	given fmtGoogleApiHttpBody: Format[Schema.GoogleApiHttpBody] = Json.format[Schema.GoogleApiHttpBody]
	given fmtGoogleCloudRecommendationengineV1beta1ListUserEventsResponse: Format[Schema.GoogleCloudRecommendationengineV1beta1ListUserEventsResponse] = Json.format[Schema.GoogleCloudRecommendationengineV1beta1ListUserEventsResponse]
	given fmtGoogleCloudRecommendationengineV1beta1PurgeUserEventsRequest: Format[Schema.GoogleCloudRecommendationengineV1beta1PurgeUserEventsRequest] = Json.format[Schema.GoogleCloudRecommendationengineV1beta1PurgeUserEventsRequest]
	given fmtGoogleCloudRecommendationengineV1beta1ImportUserEventsRequest: Format[Schema.GoogleCloudRecommendationengineV1beta1ImportUserEventsRequest] = Json.format[Schema.GoogleCloudRecommendationengineV1beta1ImportUserEventsRequest]
	given fmtGoogleCloudRecommendationengineV1beta1RejoinUserEventsRequest: Format[Schema.GoogleCloudRecommendationengineV1beta1RejoinUserEventsRequest] = Json.format[Schema.GoogleCloudRecommendationengineV1beta1RejoinUserEventsRequest]
	given fmtGoogleCloudRecommendationengineV1beta1RejoinUserEventsRequestUserEventRejoinScopeEnum: Format[Schema.GoogleCloudRecommendationengineV1beta1RejoinUserEventsRequest.UserEventRejoinScopeEnum] = JsonEnumFormat[Schema.GoogleCloudRecommendationengineV1beta1RejoinUserEventsRequest.UserEventRejoinScopeEnum]
	given fmtGoogleCloudRecommendationengineV1alphaRejoinCatalogMetadata: Format[Schema.GoogleCloudRecommendationengineV1alphaRejoinCatalogMetadata] = Json.format[Schema.GoogleCloudRecommendationengineV1alphaRejoinCatalogMetadata]
	given fmtGoogleCloudRecommendationengineV1alphaRejoinCatalogResponse: Format[Schema.GoogleCloudRecommendationengineV1alphaRejoinCatalogResponse] = Json.format[Schema.GoogleCloudRecommendationengineV1alphaRejoinCatalogResponse]
	given fmtGoogleCloudRecommendationengineV1alphaTuningMetadata: Format[Schema.GoogleCloudRecommendationengineV1alphaTuningMetadata] = Json.format[Schema.GoogleCloudRecommendationengineV1alphaTuningMetadata]
	given fmtGoogleCloudRecommendationengineV1alphaTuningResponse: Format[Schema.GoogleCloudRecommendationengineV1alphaTuningResponse] = Json.format[Schema.GoogleCloudRecommendationengineV1alphaTuningResponse]
	given fmtGoogleCloudRecommendationengineV1beta1ImportCatalogItemsResponse: Format[Schema.GoogleCloudRecommendationengineV1beta1ImportCatalogItemsResponse] = Json.format[Schema.GoogleCloudRecommendationengineV1beta1ImportCatalogItemsResponse]
	given fmtGoogleCloudRecommendationengineV1beta1ImportMetadata: Format[Schema.GoogleCloudRecommendationengineV1beta1ImportMetadata] = Json.format[Schema.GoogleCloudRecommendationengineV1beta1ImportMetadata]
	given fmtGoogleCloudRecommendationengineV1beta1ImportUserEventsResponse: Format[Schema.GoogleCloudRecommendationengineV1beta1ImportUserEventsResponse] = Json.format[Schema.GoogleCloudRecommendationengineV1beta1ImportUserEventsResponse]
	given fmtGoogleCloudRecommendationengineV1beta1UserEventImportSummary: Format[Schema.GoogleCloudRecommendationengineV1beta1UserEventImportSummary] = Json.format[Schema.GoogleCloudRecommendationengineV1beta1UserEventImportSummary]
	given fmtGoogleCloudRecommendationengineV1beta1PurgeUserEventsMetadata: Format[Schema.GoogleCloudRecommendationengineV1beta1PurgeUserEventsMetadata] = Json.format[Schema.GoogleCloudRecommendationengineV1beta1PurgeUserEventsMetadata]
	given fmtGoogleCloudRecommendationengineV1beta1PurgeUserEventsResponse: Format[Schema.GoogleCloudRecommendationengineV1beta1PurgeUserEventsResponse] = Json.format[Schema.GoogleCloudRecommendationengineV1beta1PurgeUserEventsResponse]
	given fmtGoogleCloudRecommendationengineV1beta1RejoinUserEventsResponse: Format[Schema.GoogleCloudRecommendationengineV1beta1RejoinUserEventsResponse] = Json.format[Schema.GoogleCloudRecommendationengineV1beta1RejoinUserEventsResponse]
	given fmtGoogleCloudRecommendationengineV1beta1RejoinUserEventsMetadata: Format[Schema.GoogleCloudRecommendationengineV1beta1RejoinUserEventsMetadata] = Json.format[Schema.GoogleCloudRecommendationengineV1beta1RejoinUserEventsMetadata]
}