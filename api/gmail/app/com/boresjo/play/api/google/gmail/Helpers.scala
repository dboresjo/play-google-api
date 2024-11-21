package com.boresjo.play.api.google.gmail

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Helpers {
	import Schema.*
	object AutoOption {
		given putString: Conversion[String, Option[String]] = (fun: String) => Option(fun)
		given putSchemaMessage: Conversion[Schema.Message, Option[Schema.Message]] = (fun: Schema.Message) => Option(fun)
		given putListString: Conversion[List[String], Option[List[String]]] = (fun: List[String]) => Option(fun)
		given putSchemaMessagePart: Conversion[Schema.MessagePart, Option[Schema.MessagePart]] = (fun: Schema.MessagePart) => Option(fun)
		given putInt: Conversion[Int, Option[Int]] = (fun: Int) => Option(fun)
		given putListSchemaMessagePartHeader: Conversion[List[Schema.MessagePartHeader], Option[List[Schema.MessagePartHeader]]] = (fun: List[Schema.MessagePartHeader]) => Option(fun)
		given putSchemaMessagePartBody: Conversion[Schema.MessagePartBody, Option[Schema.MessagePartBody]] = (fun: Schema.MessagePartBody) => Option(fun)
		given putListSchemaMessagePart: Conversion[List[Schema.MessagePart], Option[List[Schema.MessagePart]]] = (fun: List[Schema.MessagePart]) => Option(fun)
		given putListSchemaDraft: Conversion[List[Schema.Draft], Option[List[Schema.Draft]]] = (fun: List[Schema.Draft]) => Option(fun)
		given putListSchemaHistory: Conversion[List[Schema.History], Option[List[Schema.History]]] = (fun: List[Schema.History]) => Option(fun)
		given putListSchemaMessage: Conversion[List[Schema.Message], Option[List[Schema.Message]]] = (fun: List[Schema.Message]) => Option(fun)
		given putListSchemaHistoryMessageAdded: Conversion[List[Schema.HistoryMessageAdded], Option[List[Schema.HistoryMessageAdded]]] = (fun: List[Schema.HistoryMessageAdded]) => Option(fun)
		given putListSchemaHistoryMessageDeleted: Conversion[List[Schema.HistoryMessageDeleted], Option[List[Schema.HistoryMessageDeleted]]] = (fun: List[Schema.HistoryMessageDeleted]) => Option(fun)
		given putListSchemaHistoryLabelAdded: Conversion[List[Schema.HistoryLabelAdded], Option[List[Schema.HistoryLabelAdded]]] = (fun: List[Schema.HistoryLabelAdded]) => Option(fun)
		given putListSchemaHistoryLabelRemoved: Conversion[List[Schema.HistoryLabelRemoved], Option[List[Schema.HistoryLabelRemoved]]] = (fun: List[Schema.HistoryLabelRemoved]) => Option(fun)
		given putSchemaLabelMessageListVisibilityEnum: Conversion[Schema.Label.MessageListVisibilityEnum, Option[Schema.Label.MessageListVisibilityEnum]] = (fun: Schema.Label.MessageListVisibilityEnum) => Option(fun)
		given putSchemaLabelLabelListVisibilityEnum: Conversion[Schema.Label.LabelListVisibilityEnum, Option[Schema.Label.LabelListVisibilityEnum]] = (fun: Schema.Label.LabelListVisibilityEnum) => Option(fun)
		given putSchemaLabelTypeEnum: Conversion[Schema.Label.TypeEnum, Option[Schema.Label.TypeEnum]] = (fun: Schema.Label.TypeEnum) => Option(fun)
		given putSchemaLabelColor: Conversion[Schema.LabelColor, Option[Schema.LabelColor]] = (fun: Schema.LabelColor) => Option(fun)
		given putListSchemaLabel: Conversion[List[Schema.Label], Option[List[Schema.Label]]] = (fun: List[Schema.Label]) => Option(fun)
		given putSchemaWatchRequestLabelFilterActionEnum: Conversion[Schema.WatchRequest.LabelFilterActionEnum, Option[Schema.WatchRequest.LabelFilterActionEnum]] = (fun: Schema.WatchRequest.LabelFilterActionEnum) => Option(fun)
		given putSchemaWatchRequestLabelFilterBehaviorEnum: Conversion[Schema.WatchRequest.LabelFilterBehaviorEnum, Option[Schema.WatchRequest.LabelFilterBehaviorEnum]] = (fun: Schema.WatchRequest.LabelFilterBehaviorEnum) => Option(fun)
		given putListSchemaThread: Conversion[List[Schema.Thread], Option[List[Schema.Thread]]] = (fun: List[Schema.Thread]) => Option(fun)
		given putListSchemaSendAs: Conversion[List[Schema.SendAs], Option[List[Schema.SendAs]]] = (fun: List[Schema.SendAs]) => Option(fun)
		given putBoolean: Conversion[Boolean, Option[Boolean]] = (fun: Boolean) => Option(fun)
		given putSchemaSmtpMsa: Conversion[Schema.SmtpMsa, Option[Schema.SmtpMsa]] = (fun: Schema.SmtpMsa) => Option(fun)
		given putSchemaSendAsVerificationStatusEnum: Conversion[Schema.SendAs.VerificationStatusEnum, Option[Schema.SendAs.VerificationStatusEnum]] = (fun: Schema.SendAs.VerificationStatusEnum) => Option(fun)
		given putSchemaSmtpMsaSecurityModeEnum: Conversion[Schema.SmtpMsa.SecurityModeEnum, Option[Schema.SmtpMsa.SecurityModeEnum]] = (fun: Schema.SmtpMsa.SecurityModeEnum) => Option(fun)
		given putListSchemaSmimeInfo: Conversion[List[Schema.SmimeInfo], Option[List[Schema.SmimeInfo]]] = (fun: List[Schema.SmimeInfo]) => Option(fun)
		given putSchemaSignAndEncryptKeyPairs: Conversion[Schema.SignAndEncryptKeyPairs, Option[Schema.SignAndEncryptKeyPairs]] = (fun: Schema.SignAndEncryptKeyPairs) => Option(fun)
		given putSchemaCseKeyPairEnablementStateEnum: Conversion[Schema.CseKeyPair.EnablementStateEnum, Option[Schema.CseKeyPair.EnablementStateEnum]] = (fun: Schema.CseKeyPair.EnablementStateEnum) => Option(fun)
		given putListSchemaCsePrivateKeyMetadata: Conversion[List[Schema.CsePrivateKeyMetadata], Option[List[Schema.CsePrivateKeyMetadata]]] = (fun: List[Schema.CsePrivateKeyMetadata]) => Option(fun)
		given putSchemaKaclsKeyMetadata: Conversion[Schema.KaclsKeyMetadata, Option[Schema.KaclsKeyMetadata]] = (fun: Schema.KaclsKeyMetadata) => Option(fun)
		given putSchemaHardwareKeyMetadata: Conversion[Schema.HardwareKeyMetadata, Option[Schema.HardwareKeyMetadata]] = (fun: Schema.HardwareKeyMetadata) => Option(fun)
		given putListSchemaCseIdentity: Conversion[List[Schema.CseIdentity], Option[List[Schema.CseIdentity]]] = (fun: List[Schema.CseIdentity]) => Option(fun)
		given putListSchemaCseKeyPair: Conversion[List[Schema.CseKeyPair], Option[List[Schema.CseKeyPair]]] = (fun: List[Schema.CseKeyPair]) => Option(fun)
		given putListSchemaFilter: Conversion[List[Schema.Filter], Option[List[Schema.Filter]]] = (fun: List[Schema.Filter]) => Option(fun)
		given putSchemaFilterCriteria: Conversion[Schema.FilterCriteria, Option[Schema.FilterCriteria]] = (fun: Schema.FilterCriteria) => Option(fun)
		given putSchemaFilterAction: Conversion[Schema.FilterAction, Option[Schema.FilterAction]] = (fun: Schema.FilterAction) => Option(fun)
		given putSchemaFilterCriteriaSizeComparisonEnum: Conversion[Schema.FilterCriteria.SizeComparisonEnum, Option[Schema.FilterCriteria.SizeComparisonEnum]] = (fun: Schema.FilterCriteria.SizeComparisonEnum) => Option(fun)
		given putSchemaImapSettingsExpungeBehaviorEnum: Conversion[Schema.ImapSettings.ExpungeBehaviorEnum, Option[Schema.ImapSettings.ExpungeBehaviorEnum]] = (fun: Schema.ImapSettings.ExpungeBehaviorEnum) => Option(fun)
		given putSchemaPopSettingsAccessWindowEnum: Conversion[Schema.PopSettings.AccessWindowEnum, Option[Schema.PopSettings.AccessWindowEnum]] = (fun: Schema.PopSettings.AccessWindowEnum) => Option(fun)
		given putSchemaPopSettingsDispositionEnum: Conversion[Schema.PopSettings.DispositionEnum, Option[Schema.PopSettings.DispositionEnum]] = (fun: Schema.PopSettings.DispositionEnum) => Option(fun)
		given putListSchemaForwardingAddress: Conversion[List[Schema.ForwardingAddress], Option[List[Schema.ForwardingAddress]]] = (fun: List[Schema.ForwardingAddress]) => Option(fun)
		given putSchemaForwardingAddressVerificationStatusEnum: Conversion[Schema.ForwardingAddress.VerificationStatusEnum, Option[Schema.ForwardingAddress.VerificationStatusEnum]] = (fun: Schema.ForwardingAddress.VerificationStatusEnum) => Option(fun)
		given putSchemaAutoForwardingDispositionEnum: Conversion[Schema.AutoForwarding.DispositionEnum, Option[Schema.AutoForwarding.DispositionEnum]] = (fun: Schema.AutoForwarding.DispositionEnum) => Option(fun)
		given putListSchemaDelegate: Conversion[List[Schema.Delegate], Option[List[Schema.Delegate]]] = (fun: List[Schema.Delegate]) => Option(fun)
		given putSchemaDelegateVerificationStatusEnum: Conversion[Schema.Delegate.VerificationStatusEnum, Option[Schema.Delegate.VerificationStatusEnum]] = (fun: Schema.Delegate.VerificationStatusEnum) => Option(fun)
	}
	object OptionDefault {
		given getString: Conversion[Option[String], String] = (fun: Option[String]) => fun.getOrElse("")
	}
}
