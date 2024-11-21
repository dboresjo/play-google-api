package com.boresjo.play.api.google.analyticshub

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Helpers {
	import Schema.*
	object AutoOption {
		given putListSchemaDataExchange: Conversion[List[Schema.DataExchange], Option[List[Schema.DataExchange]]] = (fun: List[Schema.DataExchange]) => Option(fun)
		given putString: Conversion[String, Option[String]] = (fun: String) => Option(fun)
		given putInt: Conversion[Int, Option[Int]] = (fun: Int) => Option(fun)
		given putSchemaSharingEnvironmentConfig: Conversion[Schema.SharingEnvironmentConfig, Option[Schema.SharingEnvironmentConfig]] = (fun: Schema.SharingEnvironmentConfig) => Option(fun)
		given putSchemaDataExchangeDiscoveryTypeEnum: Conversion[Schema.DataExchange.DiscoveryTypeEnum, Option[Schema.DataExchange.DiscoveryTypeEnum]] = (fun: Schema.DataExchange.DiscoveryTypeEnum) => Option(fun)
		given putSchemaDefaultExchangeConfig: Conversion[Schema.DefaultExchangeConfig, Option[Schema.DefaultExchangeConfig]] = (fun: Schema.DefaultExchangeConfig) => Option(fun)
		given putSchemaDcrExchangeConfig: Conversion[Schema.DcrExchangeConfig, Option[Schema.DcrExchangeConfig]] = (fun: Schema.DcrExchangeConfig) => Option(fun)
		given putBoolean: Conversion[Boolean, Option[Boolean]] = (fun: Boolean) => Option(fun)
		given putListSchemaListing: Conversion[List[Schema.Listing], Option[List[Schema.Listing]]] = (fun: List[Schema.Listing]) => Option(fun)
		given putSchemaBigQueryDatasetSource: Conversion[Schema.BigQueryDatasetSource, Option[Schema.BigQueryDatasetSource]] = (fun: Schema.BigQueryDatasetSource) => Option(fun)
		given putSchemaPubSubTopicSource: Conversion[Schema.PubSubTopicSource, Option[Schema.PubSubTopicSource]] = (fun: Schema.PubSubTopicSource) => Option(fun)
		given putSchemaListingStateEnum: Conversion[Schema.Listing.StateEnum, Option[Schema.Listing.StateEnum]] = (fun: Schema.Listing.StateEnum) => Option(fun)
		given putSchemaDataProvider: Conversion[Schema.DataProvider, Option[Schema.DataProvider]] = (fun: Schema.DataProvider) => Option(fun)
		given putListSchemaListingCategoriesEnum: Conversion[List[Schema.Listing.CategoriesEnum], Option[List[Schema.Listing.CategoriesEnum]]] = (fun: List[Schema.Listing.CategoriesEnum]) => Option(fun)
		given putSchemaPublisher: Conversion[Schema.Publisher, Option[Schema.Publisher]] = (fun: Schema.Publisher) => Option(fun)
		given putSchemaRestrictedExportConfig: Conversion[Schema.RestrictedExportConfig, Option[Schema.RestrictedExportConfig]] = (fun: Schema.RestrictedExportConfig) => Option(fun)
		given putSchemaListingDiscoveryTypeEnum: Conversion[Schema.Listing.DiscoveryTypeEnum, Option[Schema.Listing.DiscoveryTypeEnum]] = (fun: Schema.Listing.DiscoveryTypeEnum) => Option(fun)
		given putSchemaListingResourceTypeEnum: Conversion[Schema.Listing.ResourceTypeEnum, Option[Schema.Listing.ResourceTypeEnum]] = (fun: Schema.Listing.ResourceTypeEnum) => Option(fun)
		given putListSchemaSelectedResource: Conversion[List[Schema.SelectedResource], Option[List[Schema.SelectedResource]]] = (fun: List[Schema.SelectedResource]) => Option(fun)
		given putSchemaRestrictedExportPolicy: Conversion[Schema.RestrictedExportPolicy, Option[Schema.RestrictedExportPolicy]] = (fun: Schema.RestrictedExportPolicy) => Option(fun)
		given putListString: Conversion[List[String], Option[List[String]]] = (fun: List[String]) => Option(fun)
		given putSchemaDestinationDataset: Conversion[Schema.DestinationDataset, Option[Schema.DestinationDataset]] = (fun: Schema.DestinationDataset) => Option(fun)
		given putSchemaDestinationPubSubSubscription: Conversion[Schema.DestinationPubSubSubscription, Option[Schema.DestinationPubSubSubscription]] = (fun: Schema.DestinationPubSubSubscription) => Option(fun)
		given putSchemaDestinationDatasetReference: Conversion[Schema.DestinationDatasetReference, Option[Schema.DestinationDatasetReference]] = (fun: Schema.DestinationDatasetReference) => Option(fun)
		given putMapStringString: Conversion[Map[String, String], Option[Map[String, String]]] = (fun: Map[String, String]) => Option(fun)
		given putSchemaGooglePubsubV1Subscription: Conversion[Schema.GooglePubsubV1Subscription, Option[Schema.GooglePubsubV1Subscription]] = (fun: Schema.GooglePubsubV1Subscription) => Option(fun)
		given putSchemaPushConfig: Conversion[Schema.PushConfig, Option[Schema.PushConfig]] = (fun: Schema.PushConfig) => Option(fun)
		given putSchemaBigQueryConfig: Conversion[Schema.BigQueryConfig, Option[Schema.BigQueryConfig]] = (fun: Schema.BigQueryConfig) => Option(fun)
		given putSchemaCloudStorageConfig: Conversion[Schema.CloudStorageConfig, Option[Schema.CloudStorageConfig]] = (fun: Schema.CloudStorageConfig) => Option(fun)
		given putSchemaExpirationPolicy: Conversion[Schema.ExpirationPolicy, Option[Schema.ExpirationPolicy]] = (fun: Schema.ExpirationPolicy) => Option(fun)
		given putSchemaDeadLetterPolicy: Conversion[Schema.DeadLetterPolicy, Option[Schema.DeadLetterPolicy]] = (fun: Schema.DeadLetterPolicy) => Option(fun)
		given putSchemaRetryPolicy: Conversion[Schema.RetryPolicy, Option[Schema.RetryPolicy]] = (fun: Schema.RetryPolicy) => Option(fun)
		given putSchemaGooglePubsubV1SubscriptionStateEnum: Conversion[Schema.GooglePubsubV1Subscription.StateEnum, Option[Schema.GooglePubsubV1Subscription.StateEnum]] = (fun: Schema.GooglePubsubV1Subscription.StateEnum) => Option(fun)
		given putSchemaAnalyticsHubSubscriptionInfo: Conversion[Schema.AnalyticsHubSubscriptionInfo, Option[Schema.AnalyticsHubSubscriptionInfo]] = (fun: Schema.AnalyticsHubSubscriptionInfo) => Option(fun)
		given putSchemaOidcToken: Conversion[Schema.OidcToken, Option[Schema.OidcToken]] = (fun: Schema.OidcToken) => Option(fun)
		given putSchemaPubsubWrapper: Conversion[Schema.PubsubWrapper, Option[Schema.PubsubWrapper]] = (fun: Schema.PubsubWrapper) => Option(fun)
		given putSchemaNoWrapper: Conversion[Schema.NoWrapper, Option[Schema.NoWrapper]] = (fun: Schema.NoWrapper) => Option(fun)
		given putSchemaBigQueryConfigStateEnum: Conversion[Schema.BigQueryConfig.StateEnum, Option[Schema.BigQueryConfig.StateEnum]] = (fun: Schema.BigQueryConfig.StateEnum) => Option(fun)
		given putSchemaTextConfig: Conversion[Schema.TextConfig, Option[Schema.TextConfig]] = (fun: Schema.TextConfig) => Option(fun)
		given putSchemaAvroConfig: Conversion[Schema.AvroConfig, Option[Schema.AvroConfig]] = (fun: Schema.AvroConfig) => Option(fun)
		given putSchemaCloudStorageConfigStateEnum: Conversion[Schema.CloudStorageConfig.StateEnum, Option[Schema.CloudStorageConfig.StateEnum]] = (fun: Schema.CloudStorageConfig.StateEnum) => Option(fun)
		given putSchemaSubscription: Conversion[Schema.Subscription, Option[Schema.Subscription]] = (fun: Schema.Subscription) => Option(fun)
		given putSchemaSubscriptionStateEnum: Conversion[Schema.Subscription.StateEnum, Option[Schema.Subscription.StateEnum]] = (fun: Schema.Subscription.StateEnum) => Option(fun)
		given putMapStringSchemaLinkedResource: Conversion[Map[String, Schema.LinkedResource], Option[Map[String, Schema.LinkedResource]]] = (fun: Map[String, Schema.LinkedResource]) => Option(fun)
		given putListSchemaLinkedResource: Conversion[List[Schema.LinkedResource], Option[List[Schema.LinkedResource]]] = (fun: List[Schema.LinkedResource]) => Option(fun)
		given putSchemaSubscriptionResourceTypeEnum: Conversion[Schema.Subscription.ResourceTypeEnum, Option[Schema.Subscription.ResourceTypeEnum]] = (fun: Schema.Subscription.ResourceTypeEnum) => Option(fun)
		given putMapStringJsValue: Conversion[Map[String, JsValue], Option[Map[String, JsValue]]] = (fun: Map[String, JsValue]) => Option(fun)
		given putSchemaStatus: Conversion[Schema.Status, Option[Schema.Status]] = (fun: Schema.Status) => Option(fun)
		given putListMapStringJsValue: Conversion[List[Map[String, JsValue]], Option[List[Map[String, JsValue]]]] = (fun: List[Map[String, JsValue]]) => Option(fun)
		given putListSchemaSubscription: Conversion[List[Schema.Subscription], Option[List[Schema.Subscription]]] = (fun: List[Schema.Subscription]) => Option(fun)
		given putSchemaGetPolicyOptions: Conversion[Schema.GetPolicyOptions, Option[Schema.GetPolicyOptions]] = (fun: Schema.GetPolicyOptions) => Option(fun)
		given putListSchemaBinding: Conversion[List[Schema.Binding], Option[List[Schema.Binding]]] = (fun: List[Schema.Binding]) => Option(fun)
		given putListSchemaAuditConfig: Conversion[List[Schema.AuditConfig], Option[List[Schema.AuditConfig]]] = (fun: List[Schema.AuditConfig]) => Option(fun)
		given putSchemaExpr: Conversion[Schema.Expr, Option[Schema.Expr]] = (fun: Schema.Expr) => Option(fun)
		given putListSchemaAuditLogConfig: Conversion[List[Schema.AuditLogConfig], Option[List[Schema.AuditLogConfig]]] = (fun: List[Schema.AuditLogConfig]) => Option(fun)
		given putSchemaAuditLogConfigLogTypeEnum: Conversion[Schema.AuditLogConfig.LogTypeEnum, Option[Schema.AuditLogConfig.LogTypeEnum]] = (fun: Schema.AuditLogConfig.LogTypeEnum) => Option(fun)
		given putSchemaPolicy: Conversion[Schema.Policy, Option[Schema.Policy]] = (fun: Schema.Policy) => Option(fun)
	}
	object OptionDefault {
		given getString: Conversion[Option[String], String] = (fun: Option[String]) => fun.getOrElse("")
	}
}
