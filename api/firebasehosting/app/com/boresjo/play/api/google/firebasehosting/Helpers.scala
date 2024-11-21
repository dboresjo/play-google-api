package com.boresjo.play.api.google.firebasehosting

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Helpers {
	import Schema.*
	object AutoOption {
		given putListSchemaOperation: Conversion[List[Schema.Operation], Option[List[Schema.Operation]]] = (fun: List[Schema.Operation]) => Option(fun)
		given putString: Conversion[String, Option[String]] = (fun: String) => Option(fun)
		given putMapStringJsValue: Conversion[Map[String, JsValue], Option[Map[String, JsValue]]] = (fun: Map[String, JsValue]) => Option(fun)
		given putBoolean: Conversion[Boolean, Option[Boolean]] = (fun: Boolean) => Option(fun)
		given putSchemaStatus: Conversion[Schema.Status, Option[Schema.Status]] = (fun: Schema.Status) => Option(fun)
		given putInt: Conversion[Int, Option[Int]] = (fun: Int) => Option(fun)
		given putListMapStringJsValue: Conversion[List[Map[String, JsValue]], Option[List[Map[String, JsValue]]]] = (fun: List[Map[String, JsValue]]) => Option(fun)
		given putSchemaCustomDomainMetadataHostStateEnum: Conversion[Schema.CustomDomainMetadata.HostStateEnum, Option[Schema.CustomDomainMetadata.HostStateEnum]] = (fun: Schema.CustomDomainMetadata.HostStateEnum) => Option(fun)
		given putSchemaCustomDomainMetadataOwnershipStateEnum: Conversion[Schema.CustomDomainMetadata.OwnershipStateEnum, Option[Schema.CustomDomainMetadata.OwnershipStateEnum]] = (fun: Schema.CustomDomainMetadata.OwnershipStateEnum) => Option(fun)
		given putSchemaCustomDomainMetadataCertStateEnum: Conversion[Schema.CustomDomainMetadata.CertStateEnum, Option[Schema.CustomDomainMetadata.CertStateEnum]] = (fun: Schema.CustomDomainMetadata.CertStateEnum) => Option(fun)
		given putListSchemaLiveMigrationStep: Conversion[List[Schema.LiveMigrationStep], Option[List[Schema.LiveMigrationStep]]] = (fun: List[Schema.LiveMigrationStep]) => Option(fun)
		given putSchemaDnsUpdates: Conversion[Schema.DnsUpdates, Option[Schema.DnsUpdates]] = (fun: Schema.DnsUpdates) => Option(fun)
		given putListSchemaStatus: Conversion[List[Schema.Status], Option[List[Schema.Status]]] = (fun: List[Schema.Status]) => Option(fun)
		given putSchemaLiveMigrationStepStateEnum: Conversion[Schema.LiveMigrationStep.StateEnum, Option[Schema.LiveMigrationStep.StateEnum]] = (fun: Schema.LiveMigrationStep.StateEnum) => Option(fun)
		given putSchemaCertVerification: Conversion[Schema.CertVerification, Option[Schema.CertVerification]] = (fun: Schema.CertVerification) => Option(fun)
		given putSchemaHttpUpdate: Conversion[Schema.HttpUpdate, Option[Schema.HttpUpdate]] = (fun: Schema.HttpUpdate) => Option(fun)
		given putListSchemaDnsRecordSet: Conversion[List[Schema.DnsRecordSet], Option[List[Schema.DnsRecordSet]]] = (fun: List[Schema.DnsRecordSet]) => Option(fun)
		given putListSchemaDnsRecord: Conversion[List[Schema.DnsRecord], Option[List[Schema.DnsRecord]]] = (fun: List[Schema.DnsRecord]) => Option(fun)
		given putSchemaDnsRecordTypeEnum: Conversion[Schema.DnsRecord.TypeEnum, Option[Schema.DnsRecord.TypeEnum]] = (fun: Schema.DnsRecord.TypeEnum) => Option(fun)
		given putSchemaDnsRecordRequiredActionEnum: Conversion[Schema.DnsRecord.RequiredActionEnum, Option[Schema.DnsRecord.RequiredActionEnum]] = (fun: Schema.DnsRecord.RequiredActionEnum) => Option(fun)
	}
	object OptionDefault {
		given getString: Conversion[Option[String], String] = (fun: Option[String]) => fun.getOrElse("")
	}
}
