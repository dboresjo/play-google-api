package com.boresjo.play.api.google.bigqueryconnection

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Formats {
	given fmtConnection: Format[Schema.Connection] = Json.format[Schema.Connection]
	given fmtCloudSqlProperties: Format[Schema.CloudSqlProperties] = Json.format[Schema.CloudSqlProperties]
	given fmtAwsProperties: Format[Schema.AwsProperties] = Json.format[Schema.AwsProperties]
	given fmtAzureProperties: Format[Schema.AzureProperties] = Json.format[Schema.AzureProperties]
	given fmtCloudSpannerProperties: Format[Schema.CloudSpannerProperties] = Json.format[Schema.CloudSpannerProperties]
	given fmtCloudResourceProperties: Format[Schema.CloudResourceProperties] = Json.format[Schema.CloudResourceProperties]
	given fmtSparkProperties: Format[Schema.SparkProperties] = Json.format[Schema.SparkProperties]
	given fmtSalesforceDataCloudProperties: Format[Schema.SalesforceDataCloudProperties] = Json.format[Schema.SalesforceDataCloudProperties]
	given fmtConnectorConfiguration: Format[Schema.ConnectorConfiguration] = Json.format[Schema.ConnectorConfiguration]
	given fmtCloudSqlPropertiesTypeEnum: Format[Schema.CloudSqlProperties.TypeEnum] = JsonEnumFormat[Schema.CloudSqlProperties.TypeEnum]
	given fmtCloudSqlCredential: Format[Schema.CloudSqlCredential] = Json.format[Schema.CloudSqlCredential]
	given fmtAwsAccessRole: Format[Schema.AwsAccessRole] = Json.format[Schema.AwsAccessRole]
	given fmtMetastoreServiceConfig: Format[Schema.MetastoreServiceConfig] = Json.format[Schema.MetastoreServiceConfig]
	given fmtSparkHistoryServerConfig: Format[Schema.SparkHistoryServerConfig] = Json.format[Schema.SparkHistoryServerConfig]
	given fmtConnectorConfigurationEndpoint: Format[Schema.ConnectorConfigurationEndpoint] = Json.format[Schema.ConnectorConfigurationEndpoint]
	given fmtConnectorConfigurationAuthentication: Format[Schema.ConnectorConfigurationAuthentication] = Json.format[Schema.ConnectorConfigurationAuthentication]
	given fmtConnectorConfigurationNetwork: Format[Schema.ConnectorConfigurationNetwork] = Json.format[Schema.ConnectorConfigurationNetwork]
	given fmtConnectorConfigurationAsset: Format[Schema.ConnectorConfigurationAsset] = Json.format[Schema.ConnectorConfigurationAsset]
	given fmtConnectorConfigurationUsernamePassword: Format[Schema.ConnectorConfigurationUsernamePassword] = Json.format[Schema.ConnectorConfigurationUsernamePassword]
	given fmtConnectorConfigurationSecret: Format[Schema.ConnectorConfigurationSecret] = Json.format[Schema.ConnectorConfigurationSecret]
	given fmtConnectorConfigurationSecretSecretTypeEnum: Format[Schema.ConnectorConfigurationSecret.SecretTypeEnum] = JsonEnumFormat[Schema.ConnectorConfigurationSecret.SecretTypeEnum]
	given fmtConnectorConfigurationPrivateServiceConnect: Format[Schema.ConnectorConfigurationPrivateServiceConnect] = Json.format[Schema.ConnectorConfigurationPrivateServiceConnect]
	given fmtListConnectionsResponse: Format[Schema.ListConnectionsResponse] = Json.format[Schema.ListConnectionsResponse]
	given fmtEmpty: Format[Schema.Empty] = Json.format[Schema.Empty]
	given fmtGetIamPolicyRequest: Format[Schema.GetIamPolicyRequest] = Json.format[Schema.GetIamPolicyRequest]
	given fmtGetPolicyOptions: Format[Schema.GetPolicyOptions] = Json.format[Schema.GetPolicyOptions]
	given fmtPolicy: Format[Schema.Policy] = Json.format[Schema.Policy]
	given fmtBinding: Format[Schema.Binding] = Json.format[Schema.Binding]
	given fmtAuditConfig: Format[Schema.AuditConfig] = Json.format[Schema.AuditConfig]
	given fmtExpr: Format[Schema.Expr] = Json.format[Schema.Expr]
	given fmtAuditLogConfig: Format[Schema.AuditLogConfig] = Json.format[Schema.AuditLogConfig]
	given fmtAuditLogConfigLogTypeEnum: Format[Schema.AuditLogConfig.LogTypeEnum] = JsonEnumFormat[Schema.AuditLogConfig.LogTypeEnum]
	given fmtSetIamPolicyRequest: Format[Schema.SetIamPolicyRequest] = Json.format[Schema.SetIamPolicyRequest]
	given fmtTestIamPermissionsRequest: Format[Schema.TestIamPermissionsRequest] = Json.format[Schema.TestIamPermissionsRequest]
	given fmtTestIamPermissionsResponse: Format[Schema.TestIamPermissionsResponse] = Json.format[Schema.TestIamPermissionsResponse]
}
