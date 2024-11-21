package com.boresjo.play.api.google.looker

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
		given putListSchemaInstance: Conversion[List[Schema.Instance], Option[List[Schema.Instance]]] = (fun: List[Schema.Instance]) => Option(fun)
		given putListString: Conversion[List[String], Option[List[String]]] = (fun: List[String]) => Option(fun)
		given putSchemaInstanceStateEnum: Conversion[Schema.Instance.StateEnum, Option[Schema.Instance.StateEnum]] = (fun: Schema.Instance.StateEnum) => Option(fun)
		given putSchemaInstancePlatformEditionEnum: Conversion[Schema.Instance.PlatformEditionEnum, Option[Schema.Instance.PlatformEditionEnum]] = (fun: Schema.Instance.PlatformEditionEnum) => Option(fun)
		given putSchemaPscConfig: Conversion[Schema.PscConfig, Option[Schema.PscConfig]] = (fun: Schema.PscConfig) => Option(fun)
		given putSchemaMaintenanceWindow: Conversion[Schema.MaintenanceWindow, Option[Schema.MaintenanceWindow]] = (fun: Schema.MaintenanceWindow) => Option(fun)
		given putSchemaDenyMaintenancePeriod: Conversion[Schema.DenyMaintenancePeriod, Option[Schema.DenyMaintenancePeriod]] = (fun: Schema.DenyMaintenancePeriod) => Option(fun)
		given putSchemaMaintenanceSchedule: Conversion[Schema.MaintenanceSchedule, Option[Schema.MaintenanceSchedule]] = (fun: Schema.MaintenanceSchedule) => Option(fun)
		given putSchemaUserMetadata: Conversion[Schema.UserMetadata, Option[Schema.UserMetadata]] = (fun: Schema.UserMetadata) => Option(fun)
		given putSchemaCustomDomain: Conversion[Schema.CustomDomain, Option[Schema.CustomDomain]] = (fun: Schema.CustomDomain) => Option(fun)
		given putSchemaEncryptionConfig: Conversion[Schema.EncryptionConfig, Option[Schema.EncryptionConfig]] = (fun: Schema.EncryptionConfig) => Option(fun)
		given putSchemaAdminSettings: Conversion[Schema.AdminSettings, Option[Schema.AdminSettings]] = (fun: Schema.AdminSettings) => Option(fun)
		given putSchemaOAuthConfig: Conversion[Schema.OAuthConfig, Option[Schema.OAuthConfig]] = (fun: Schema.OAuthConfig) => Option(fun)
		given putListSchemaServiceAttachment: Conversion[List[Schema.ServiceAttachment], Option[List[Schema.ServiceAttachment]]] = (fun: List[Schema.ServiceAttachment]) => Option(fun)
		given putSchemaServiceAttachmentConnectionStatusEnum: Conversion[Schema.ServiceAttachment.ConnectionStatusEnum, Option[Schema.ServiceAttachment.ConnectionStatusEnum]] = (fun: Schema.ServiceAttachment.ConnectionStatusEnum) => Option(fun)
		given putSchemaMaintenanceWindowDayOfWeekEnum: Conversion[Schema.MaintenanceWindow.DayOfWeekEnum, Option[Schema.MaintenanceWindow.DayOfWeekEnum]] = (fun: Schema.MaintenanceWindow.DayOfWeekEnum) => Option(fun)
		given putSchemaTimeOfDay: Conversion[Schema.TimeOfDay, Option[Schema.TimeOfDay]] = (fun: Schema.TimeOfDay) => Option(fun)
		given putSchemaDate: Conversion[Schema.Date, Option[Schema.Date]] = (fun: Schema.Date) => Option(fun)
		given putSchemaCustomDomainStateEnum: Conversion[Schema.CustomDomain.StateEnum, Option[Schema.CustomDomain.StateEnum]] = (fun: Schema.CustomDomain.StateEnum) => Option(fun)
		given putSchemaEncryptionConfigKmsKeyStateEnum: Conversion[Schema.EncryptionConfig.KmsKeyStateEnum, Option[Schema.EncryptionConfig.KmsKeyStateEnum]] = (fun: Schema.EncryptionConfig.KmsKeyStateEnum) => Option(fun)
		given putSchemaExportEncryptionConfig: Conversion[Schema.ExportEncryptionConfig, Option[Schema.ExportEncryptionConfig]] = (fun: Schema.ExportEncryptionConfig) => Option(fun)
		given putListSchemaLocation: Conversion[List[Schema.Location], Option[List[Schema.Location]]] = (fun: List[Schema.Location]) => Option(fun)
		given putMapStringString: Conversion[Map[String, String], Option[Map[String, String]]] = (fun: Map[String, String]) => Option(fun)
		given putSchemaPolicy: Conversion[Schema.Policy, Option[Schema.Policy]] = (fun: Schema.Policy) => Option(fun)
		given putListSchemaBinding: Conversion[List[Schema.Binding], Option[List[Schema.Binding]]] = (fun: List[Schema.Binding]) => Option(fun)
		given putListSchemaAuditConfig: Conversion[List[Schema.AuditConfig], Option[List[Schema.AuditConfig]]] = (fun: List[Schema.AuditConfig]) => Option(fun)
		given putSchemaExpr: Conversion[Schema.Expr, Option[Schema.Expr]] = (fun: Schema.Expr) => Option(fun)
		given putListSchemaAuditLogConfig: Conversion[List[Schema.AuditLogConfig], Option[List[Schema.AuditLogConfig]]] = (fun: List[Schema.AuditLogConfig]) => Option(fun)
		given putSchemaAuditLogConfigLogTypeEnum: Conversion[Schema.AuditLogConfig.LogTypeEnum, Option[Schema.AuditLogConfig.LogTypeEnum]] = (fun: Schema.AuditLogConfig.LogTypeEnum) => Option(fun)
		given putSchemaExportMetadataEncryptionKey: Conversion[Schema.ExportMetadataEncryptionKey, Option[Schema.ExportMetadataEncryptionKey]] = (fun: Schema.ExportMetadataEncryptionKey) => Option(fun)
		given putSchemaExportMetadataSourceEnum: Conversion[Schema.ExportMetadata.SourceEnum, Option[Schema.ExportMetadata.SourceEnum]] = (fun: Schema.ExportMetadata.SourceEnum) => Option(fun)
	}
	object OptionDefault {
		given getString: Conversion[Option[String], String] = (fun: Option[String]) => fun.getOrElse("")
	}
}
