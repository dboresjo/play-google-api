package com.boresjo.play.api.google.alertcenter

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Helpers {
	import Schema.*
	object AutoOption {
		given putListSchemaAlert: Conversion[List[Schema.Alert], Option[List[Schema.Alert]]] = (fun: List[Schema.Alert]) => Option(fun)
		given putString: Conversion[String, Option[String]] = (fun: String) => Option(fun)
		given putMapStringJsValue: Conversion[Map[String, JsValue], Option[Map[String, JsValue]]] = (fun: Map[String, JsValue]) => Option(fun)
		given putBoolean: Conversion[Boolean, Option[Boolean]] = (fun: Boolean) => Option(fun)
		given putSchemaAlertMetadata: Conversion[Schema.AlertMetadata, Option[Schema.AlertMetadata]] = (fun: Schema.AlertMetadata) => Option(fun)
		given putSchemaAlertFeedbackTypeEnum: Conversion[Schema.AlertFeedback.TypeEnum, Option[Schema.AlertFeedback.TypeEnum]] = (fun: Schema.AlertFeedback.TypeEnum) => Option(fun)
		given putListSchemaAlertFeedback: Conversion[List[Schema.AlertFeedback], Option[List[Schema.AlertFeedback]]] = (fun: List[Schema.AlertFeedback]) => Option(fun)
		given putListSchemaNotification: Conversion[List[Schema.Notification], Option[List[Schema.Notification]]] = (fun: List[Schema.Notification]) => Option(fun)
		given putSchemaCloudPubsubTopic: Conversion[Schema.CloudPubsubTopic, Option[Schema.CloudPubsubTopic]] = (fun: Schema.CloudPubsubTopic) => Option(fun)
		given putSchemaCloudPubsubTopicPayloadFormatEnum: Conversion[Schema.CloudPubsubTopic.PayloadFormatEnum, Option[Schema.CloudPubsubTopic.PayloadFormatEnum]] = (fun: Schema.CloudPubsubTopic.PayloadFormatEnum) => Option(fun)
		given putListString: Conversion[List[String], Option[List[String]]] = (fun: List[String]) => Option(fun)
		given putMapStringSchemaStatus: Conversion[Map[String, Schema.Status], Option[Map[String, Schema.Status]]] = (fun: Map[String, Schema.Status]) => Option(fun)
		given putInt: Conversion[Int, Option[Int]] = (fun: Int) => Option(fun)
		given putListMapStringJsValue: Conversion[List[Map[String, JsValue]], Option[List[Map[String, JsValue]]]] = (fun: List[Map[String, JsValue]]) => Option(fun)
		given putSchemaDomainId: Conversion[Schema.DomainId, Option[Schema.DomainId]] = (fun: Schema.DomainId) => Option(fun)
		given putSchemaMaliciousEntity: Conversion[Schema.MaliciousEntity, Option[Schema.MaliciousEntity]] = (fun: Schema.MaliciousEntity) => Option(fun)
		given putListSchemaGmailMessageInfo: Conversion[List[Schema.GmailMessageInfo], Option[List[Schema.GmailMessageInfo]]] = (fun: List[Schema.GmailMessageInfo]) => Option(fun)
		given putSchemaUser: Conversion[Schema.User, Option[Schema.User]] = (fun: Schema.User) => Option(fun)
		given putListSchemaDeviceCompromisedSecurityDetail: Conversion[List[Schema.DeviceCompromisedSecurityDetail], Option[List[Schema.DeviceCompromisedSecurityDetail]]] = (fun: List[Schema.DeviceCompromisedSecurityDetail]) => Option(fun)
		given putSchemaAttachment: Conversion[Schema.Attachment, Option[Schema.Attachment]] = (fun: Schema.Attachment) => Option(fun)
		given putSchemaCsv: Conversion[Schema.Csv, Option[Schema.Csv]] = (fun: Schema.Csv) => Option(fun)
		given putListSchemaCsvRow: Conversion[List[Schema.CsvRow], Option[List[Schema.CsvRow]]] = (fun: List[Schema.CsvRow]) => Option(fun)
		given putSchemaMailPhishingSystemActionTypeEnum: Conversion[Schema.MailPhishing.SystemActionTypeEnum, Option[Schema.MailPhishing.SystemActionTypeEnum]] = (fun: Schema.MailPhishing.SystemActionTypeEnum) => Option(fun)
		given putListSchemaSuspiciousActivitySecurityDetail: Conversion[List[Schema.SuspiciousActivitySecurityDetail], Option[List[Schema.SuspiciousActivitySecurityDetail]]] = (fun: List[Schema.SuspiciousActivitySecurityDetail]) => Option(fun)
		given putSchemaLoginDetails: Conversion[Schema.LoginDetails, Option[Schema.LoginDetails]] = (fun: Schema.LoginDetails) => Option(fun)
		given putListSchemaRequestInfo: Conversion[List[Schema.RequestInfo], Option[List[Schema.RequestInfo]]] = (fun: List[Schema.RequestInfo]) => Option(fun)
		given putSchemaRuleViolationInfo: Conversion[Schema.RuleViolationInfo, Option[Schema.RuleViolationInfo]] = (fun: Schema.RuleViolationInfo) => Option(fun)
		given putSchemaRuleInfo: Conversion[Schema.RuleInfo, Option[Schema.RuleInfo]] = (fun: Schema.RuleInfo) => Option(fun)
		given putSchemaRuleViolationInfoDataSourceEnum: Conversion[Schema.RuleViolationInfo.DataSourceEnum, Option[Schema.RuleViolationInfo.DataSourceEnum]] = (fun: Schema.RuleViolationInfo.DataSourceEnum) => Option(fun)
		given putSchemaRuleViolationInfoTriggerEnum: Conversion[Schema.RuleViolationInfo.TriggerEnum, Option[Schema.RuleViolationInfo.TriggerEnum]] = (fun: Schema.RuleViolationInfo.TriggerEnum) => Option(fun)
		given putSchemaResourceInfo: Conversion[Schema.ResourceInfo, Option[Schema.ResourceInfo]] = (fun: Schema.ResourceInfo) => Option(fun)
		given putListSchemaMatchInfo: Conversion[List[Schema.MatchInfo], Option[List[Schema.MatchInfo]]] = (fun: List[Schema.MatchInfo]) => Option(fun)
		given putListSchemaRuleViolationInfoTriggeredActionTypesEnum: Conversion[List[Schema.RuleViolationInfo.TriggeredActionTypesEnum], Option[List[Schema.RuleViolationInfo.TriggeredActionTypesEnum]]] = (fun: List[Schema.RuleViolationInfo.TriggeredActionTypesEnum]) => Option(fun)
		given putListSchemaActionInfo: Conversion[List[Schema.ActionInfo], Option[List[Schema.ActionInfo]]] = (fun: List[Schema.ActionInfo]) => Option(fun)
		given putListSchemaRuleViolationInfoSuppressedActionTypesEnum: Conversion[List[Schema.RuleViolationInfo.SuppressedActionTypesEnum], Option[List[Schema.RuleViolationInfo.SuppressedActionTypesEnum]]] = (fun: List[Schema.RuleViolationInfo.SuppressedActionTypesEnum]) => Option(fun)
		given putSchemaRuleViolationInfoEventTypeEnum: Conversion[Schema.RuleViolationInfo.EventTypeEnum, Option[Schema.RuleViolationInfo.EventTypeEnum]] = (fun: Schema.RuleViolationInfo.EventTypeEnum) => Option(fun)
		given putSchemaUserDefinedDetectorInfo: Conversion[Schema.UserDefinedDetectorInfo, Option[Schema.UserDefinedDetectorInfo]] = (fun: Schema.UserDefinedDetectorInfo) => Option(fun)
		given putSchemaPredefinedDetectorInfo: Conversion[Schema.PredefinedDetectorInfo, Option[Schema.PredefinedDetectorInfo]] = (fun: Schema.PredefinedDetectorInfo) => Option(fun)
		given putSchemaVoiceMisconfigurationEntityTypeEnum: Conversion[Schema.VoiceMisconfiguration.EntityTypeEnum, Option[Schema.VoiceMisconfiguration.EntityTypeEnum]] = (fun: Schema.VoiceMisconfiguration.EntityTypeEnum) => Option(fun)
		given putSchemaVoicemailMisconfiguration: Conversion[Schema.VoicemailMisconfiguration, Option[Schema.VoicemailMisconfiguration]] = (fun: Schema.VoicemailMisconfiguration) => Option(fun)
		given putSchemaTransferMisconfiguration: Conversion[Schema.TransferMisconfiguration, Option[Schema.TransferMisconfiguration]] = (fun: Schema.TransferMisconfiguration) => Option(fun)
		given putListSchemaVoicemailRecipientError: Conversion[List[Schema.VoicemailRecipientError], Option[List[Schema.VoicemailRecipientError]]] = (fun: List[Schema.VoicemailRecipientError]) => Option(fun)
		given putSchemaVoicemailRecipientErrorInvalidReasonEnum: Conversion[Schema.VoicemailRecipientError.InvalidReasonEnum, Option[Schema.VoicemailRecipientError.InvalidReasonEnum]] = (fun: Schema.VoicemailRecipientError.InvalidReasonEnum) => Option(fun)
		given putListSchemaTransferError: Conversion[List[Schema.TransferError], Option[List[Schema.TransferError]]] = (fun: List[Schema.TransferError]) => Option(fun)
		given putSchemaTransferErrorEntityTypeEnum: Conversion[Schema.TransferError.EntityTypeEnum, Option[Schema.TransferError.EntityTypeEnum]] = (fun: Schema.TransferError.EntityTypeEnum) => Option(fun)
		given putSchemaTransferErrorInvalidReasonEnum: Conversion[Schema.TransferError.InvalidReasonEnum, Option[Schema.TransferError.InvalidReasonEnum]] = (fun: Schema.TransferError.InvalidReasonEnum) => Option(fun)
		given putSchemaAccountSuspensionWarningStateEnum: Conversion[Schema.AccountSuspensionWarning.StateEnum, Option[Schema.AccountSuspensionWarning.StateEnum]] = (fun: Schema.AccountSuspensionWarning.StateEnum) => Option(fun)
		given putListSchemaAccountSuspensionDetails: Conversion[List[Schema.AccountSuspensionDetails], Option[List[Schema.AccountSuspensionDetails]]] = (fun: List[Schema.AccountSuspensionDetails]) => Option(fun)
		given putSchemaAccountSuspensionDetailsAbuseReasonEnum: Conversion[Schema.AccountSuspensionDetails.AbuseReasonEnum, Option[Schema.AccountSuspensionDetails.AbuseReasonEnum]] = (fun: Schema.AccountSuspensionDetails.AbuseReasonEnum) => Option(fun)
		given putSchemaAppsOutageStatusEnum: Conversion[Schema.AppsOutage.StatusEnum, Option[Schema.AppsOutage.StatusEnum]] = (fun: Schema.AppsOutage.StatusEnum) => Option(fun)
		given putSchemaMergeInfo: Conversion[Schema.MergeInfo, Option[Schema.MergeInfo]] = (fun: Schema.MergeInfo) => Option(fun)
		given putListSchemaSupportTicket: Conversion[List[Schema.SupportTicket], Option[List[Schema.SupportTicket]]] = (fun: List[Schema.SupportTicket]) => Option(fun)
		given putListSchemaAccessApprovalJustificationReasonEnum: Conversion[List[Schema.AccessApproval.JustificationReasonEnum], Option[List[Schema.AccessApproval.JustificationReasonEnum]]] = (fun: List[Schema.AccessApproval.JustificationReasonEnum]) => Option(fun)
		given putSchemaPrimaryAdminChangedEvent: Conversion[Schema.PrimaryAdminChangedEvent, Option[Schema.PrimaryAdminChangedEvent]] = (fun: Schema.PrimaryAdminChangedEvent) => Option(fun)
		given putSchemaSSOProfileCreatedEvent: Conversion[Schema.SSOProfileCreatedEvent, Option[Schema.SSOProfileCreatedEvent]] = (fun: Schema.SSOProfileCreatedEvent) => Option(fun)
		given putSchemaSSOProfileUpdatedEvent: Conversion[Schema.SSOProfileUpdatedEvent, Option[Schema.SSOProfileUpdatedEvent]] = (fun: Schema.SSOProfileUpdatedEvent) => Option(fun)
		given putSchemaSSOProfileDeletedEvent: Conversion[Schema.SSOProfileDeletedEvent, Option[Schema.SSOProfileDeletedEvent]] = (fun: Schema.SSOProfileDeletedEvent) => Option(fun)
		given putSchemaSuperAdminPasswordResetEvent: Conversion[Schema.SuperAdminPasswordResetEvent, Option[Schema.SuperAdminPasswordResetEvent]] = (fun: Schema.SuperAdminPasswordResetEvent) => Option(fun)
		given putSchemaEntityList: Conversion[Schema.EntityList, Option[Schema.EntityList]] = (fun: Schema.EntityList) => Option(fun)
		given putSchemaAbuseDetectedVariationTypeEnum: Conversion[Schema.AbuseDetected.VariationTypeEnum, Option[Schema.AbuseDetected.VariationTypeEnum]] = (fun: Schema.AbuseDetected.VariationTypeEnum) => Option(fun)
		given putListSchemaEntity: Conversion[List[Schema.Entity], Option[List[Schema.Entity]]] = (fun: List[Schema.Entity]) => Option(fun)
	}
	object OptionDefault {
		given getString: Conversion[Option[String], String] = (fun: Option[String]) => fun.getOrElse("")
	}
}
