package com.boresjo.play.api.google.firebasehosting

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Formats {
	given fmtListOperationsResponse: Format[Schema.ListOperationsResponse] = Json.format[Schema.ListOperationsResponse]
	given fmtOperation: Format[Schema.Operation] = Json.format[Schema.Operation]
	given fmtStatus: Format[Schema.Status] = Json.format[Schema.Status]
	given fmtEmpty: Format[Schema.Empty] = Json.format[Schema.Empty]
	given fmtCancelOperationRequest: Format[Schema.CancelOperationRequest] = Json.format[Schema.CancelOperationRequest]
	given fmtCustomDomainMetadata: Format[Schema.CustomDomainMetadata] = Json.format[Schema.CustomDomainMetadata]
	given fmtCustomDomainMetadataHostStateEnum: Format[Schema.CustomDomainMetadata.HostStateEnum] = JsonEnumFormat[Schema.CustomDomainMetadata.HostStateEnum]
	given fmtCustomDomainMetadataOwnershipStateEnum: Format[Schema.CustomDomainMetadata.OwnershipStateEnum] = JsonEnumFormat[Schema.CustomDomainMetadata.OwnershipStateEnum]
	given fmtCustomDomainMetadataCertStateEnum: Format[Schema.CustomDomainMetadata.CertStateEnum] = JsonEnumFormat[Schema.CustomDomainMetadata.CertStateEnum]
	given fmtLiveMigrationStep: Format[Schema.LiveMigrationStep] = Json.format[Schema.LiveMigrationStep]
	given fmtDnsUpdates: Format[Schema.DnsUpdates] = Json.format[Schema.DnsUpdates]
	given fmtLiveMigrationStepStateEnum: Format[Schema.LiveMigrationStep.StateEnum] = JsonEnumFormat[Schema.LiveMigrationStep.StateEnum]
	given fmtCertVerification: Format[Schema.CertVerification] = Json.format[Schema.CertVerification]
	given fmtHttpUpdate: Format[Schema.HttpUpdate] = Json.format[Schema.HttpUpdate]
	given fmtDnsRecordSet: Format[Schema.DnsRecordSet] = Json.format[Schema.DnsRecordSet]
	given fmtDnsRecord: Format[Schema.DnsRecord] = Json.format[Schema.DnsRecord]
	given fmtDnsRecordTypeEnum: Format[Schema.DnsRecord.TypeEnum] = JsonEnumFormat[Schema.DnsRecord.TypeEnum]
	given fmtDnsRecordRequiredActionEnum: Format[Schema.DnsRecord.RequiredActionEnum] = JsonEnumFormat[Schema.DnsRecord.RequiredActionEnum]
}
