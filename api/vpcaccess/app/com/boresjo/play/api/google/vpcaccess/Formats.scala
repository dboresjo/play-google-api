package com.boresjo.play.api.google.vpcaccess

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Formats {
	given fmtListLocationsResponse: Format[Schema.ListLocationsResponse] = Json.format[Schema.ListLocationsResponse]
	given fmtLocation: Format[Schema.Location] = Json.format[Schema.Location]
	given fmtListOperationsResponse: Format[Schema.ListOperationsResponse] = Json.format[Schema.ListOperationsResponse]
	given fmtOperation: Format[Schema.Operation] = Json.format[Schema.Operation]
	given fmtStatus: Format[Schema.Status] = Json.format[Schema.Status]
	given fmtConnector: Format[Schema.Connector] = Json.format[Schema.Connector]
	given fmtConnectorStateEnum: Format[Schema.Connector.StateEnum] = JsonEnumFormat[Schema.Connector.StateEnum]
	given fmtSubnet: Format[Schema.Subnet] = Json.format[Schema.Subnet]
	given fmtListConnectorsResponse: Format[Schema.ListConnectorsResponse] = Json.format[Schema.ListConnectorsResponse]
	given fmtOperationMetadataV1Alpha1: Format[Schema.OperationMetadataV1Alpha1] = Json.format[Schema.OperationMetadataV1Alpha1]
	given fmtOperationMetadataV1Beta1: Format[Schema.OperationMetadataV1Beta1] = Json.format[Schema.OperationMetadataV1Beta1]
	given fmtOperationMetadata: Format[Schema.OperationMetadata] = Json.format[Schema.OperationMetadata]
}
