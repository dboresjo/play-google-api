package com.boresjo.play.api.google.bigqueryconnection

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Helpers {
	import Schema.*
	object AutoOption {
		given putString: Conversion[String, Option[String]] = (fun: String) => Option(fun)
		given putSchemaCloudSqlProperties: Conversion[Schema.CloudSqlProperties, Option[Schema.CloudSqlProperties]] = (fun: Schema.CloudSqlProperties) => Option(fun)
		given putSchemaAwsProperties: Conversion[Schema.AwsProperties, Option[Schema.AwsProperties]] = (fun: Schema.AwsProperties) => Option(fun)
		given putSchemaAzureProperties: Conversion[Schema.AzureProperties, Option[Schema.AzureProperties]] = (fun: Schema.AzureProperties) => Option(fun)
		given putSchemaCloudSpannerProperties: Conversion[Schema.CloudSpannerProperties, Option[Schema.CloudSpannerProperties]] = (fun: Schema.CloudSpannerProperties) => Option(fun)
		given putSchemaCloudResourceProperties: Conversion[Schema.CloudResourceProperties, Option[Schema.CloudResourceProperties]] = (fun: Schema.CloudResourceProperties) => Option(fun)
		given putSchemaSparkProperties: Conversion[Schema.SparkProperties, Option[Schema.SparkProperties]] = (fun: Schema.SparkProperties) => Option(fun)
		given putSchemaSalesforceDataCloudProperties: Conversion[Schema.SalesforceDataCloudProperties, Option[Schema.SalesforceDataCloudProperties]] = (fun: Schema.SalesforceDataCloudProperties) => Option(fun)
		given putSchemaConnectorConfiguration: Conversion[Schema.ConnectorConfiguration, Option[Schema.ConnectorConfiguration]] = (fun: Schema.ConnectorConfiguration) => Option(fun)
		given putBoolean: Conversion[Boolean, Option[Boolean]] = (fun: Boolean) => Option(fun)
		given putSchemaCloudSqlPropertiesTypeEnum: Conversion[Schema.CloudSqlProperties.TypeEnum, Option[Schema.CloudSqlProperties.TypeEnum]] = (fun: Schema.CloudSqlProperties.TypeEnum) => Option(fun)
		given putSchemaCloudSqlCredential: Conversion[Schema.CloudSqlCredential, Option[Schema.CloudSqlCredential]] = (fun: Schema.CloudSqlCredential) => Option(fun)
		given putSchemaAwsAccessRole: Conversion[Schema.AwsAccessRole, Option[Schema.AwsAccessRole]] = (fun: Schema.AwsAccessRole) => Option(fun)
		given putInt: Conversion[Int, Option[Int]] = (fun: Int) => Option(fun)
		given putSchemaMetastoreServiceConfig: Conversion[Schema.MetastoreServiceConfig, Option[Schema.MetastoreServiceConfig]] = (fun: Schema.MetastoreServiceConfig) => Option(fun)
		given putSchemaSparkHistoryServerConfig: Conversion[Schema.SparkHistoryServerConfig, Option[Schema.SparkHistoryServerConfig]] = (fun: Schema.SparkHistoryServerConfig) => Option(fun)
		given putSchemaConnectorConfigurationEndpoint: Conversion[Schema.ConnectorConfigurationEndpoint, Option[Schema.ConnectorConfigurationEndpoint]] = (fun: Schema.ConnectorConfigurationEndpoint) => Option(fun)
		given putSchemaConnectorConfigurationAuthentication: Conversion[Schema.ConnectorConfigurationAuthentication, Option[Schema.ConnectorConfigurationAuthentication]] = (fun: Schema.ConnectorConfigurationAuthentication) => Option(fun)
		given putSchemaConnectorConfigurationNetwork: Conversion[Schema.ConnectorConfigurationNetwork, Option[Schema.ConnectorConfigurationNetwork]] = (fun: Schema.ConnectorConfigurationNetwork) => Option(fun)
		given putSchemaConnectorConfigurationAsset: Conversion[Schema.ConnectorConfigurationAsset, Option[Schema.ConnectorConfigurationAsset]] = (fun: Schema.ConnectorConfigurationAsset) => Option(fun)
		given putSchemaConnectorConfigurationUsernamePassword: Conversion[Schema.ConnectorConfigurationUsernamePassword, Option[Schema.ConnectorConfigurationUsernamePassword]] = (fun: Schema.ConnectorConfigurationUsernamePassword) => Option(fun)
		given putSchemaConnectorConfigurationSecret: Conversion[Schema.ConnectorConfigurationSecret, Option[Schema.ConnectorConfigurationSecret]] = (fun: Schema.ConnectorConfigurationSecret) => Option(fun)
		given putSchemaConnectorConfigurationSecretSecretTypeEnum: Conversion[Schema.ConnectorConfigurationSecret.SecretTypeEnum, Option[Schema.ConnectorConfigurationSecret.SecretTypeEnum]] = (fun: Schema.ConnectorConfigurationSecret.SecretTypeEnum) => Option(fun)
		given putSchemaConnectorConfigurationPrivateServiceConnect: Conversion[Schema.ConnectorConfigurationPrivateServiceConnect, Option[Schema.ConnectorConfigurationPrivateServiceConnect]] = (fun: Schema.ConnectorConfigurationPrivateServiceConnect) => Option(fun)
		given putListSchemaConnection: Conversion[List[Schema.Connection], Option[List[Schema.Connection]]] = (fun: List[Schema.Connection]) => Option(fun)
		given putSchemaGetPolicyOptions: Conversion[Schema.GetPolicyOptions, Option[Schema.GetPolicyOptions]] = (fun: Schema.GetPolicyOptions) => Option(fun)
		given putListSchemaBinding: Conversion[List[Schema.Binding], Option[List[Schema.Binding]]] = (fun: List[Schema.Binding]) => Option(fun)
		given putListSchemaAuditConfig: Conversion[List[Schema.AuditConfig], Option[List[Schema.AuditConfig]]] = (fun: List[Schema.AuditConfig]) => Option(fun)
		given putListString: Conversion[List[String], Option[List[String]]] = (fun: List[String]) => Option(fun)
		given putSchemaExpr: Conversion[Schema.Expr, Option[Schema.Expr]] = (fun: Schema.Expr) => Option(fun)
		given putListSchemaAuditLogConfig: Conversion[List[Schema.AuditLogConfig], Option[List[Schema.AuditLogConfig]]] = (fun: List[Schema.AuditLogConfig]) => Option(fun)
		given putSchemaAuditLogConfigLogTypeEnum: Conversion[Schema.AuditLogConfig.LogTypeEnum, Option[Schema.AuditLogConfig.LogTypeEnum]] = (fun: Schema.AuditLogConfig.LogTypeEnum) => Option(fun)
		given putSchemaPolicy: Conversion[Schema.Policy, Option[Schema.Policy]] = (fun: Schema.Policy) => Option(fun)
	}
	object OptionDefault {
		given getString: Conversion[Option[String], String] = (fun: Option[String]) => fun.getOrElse("")
	}
}
