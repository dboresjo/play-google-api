package com.boresjo.play.api.google.gmail

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Formats {
	given fmtDraft: Format[Schema.Draft] = Json.format[Schema.Draft]
	given fmtMessage: Format[Schema.Message] = Json.format[Schema.Message]
	given fmtMessagePart: Format[Schema.MessagePart] = Json.format[Schema.MessagePart]
	given fmtMessagePartHeader: Format[Schema.MessagePartHeader] = Json.format[Schema.MessagePartHeader]
	given fmtMessagePartBody: Format[Schema.MessagePartBody] = Json.format[Schema.MessagePartBody]
	given fmtListDraftsResponse: Format[Schema.ListDraftsResponse] = Json.format[Schema.ListDraftsResponse]
	given fmtListHistoryResponse: Format[Schema.ListHistoryResponse] = Json.format[Schema.ListHistoryResponse]
	given fmtHistory: Format[Schema.History] = Json.format[Schema.History]
	given fmtHistoryMessageAdded: Format[Schema.HistoryMessageAdded] = Json.format[Schema.HistoryMessageAdded]
	given fmtHistoryMessageDeleted: Format[Schema.HistoryMessageDeleted] = Json.format[Schema.HistoryMessageDeleted]
	given fmtHistoryLabelAdded: Format[Schema.HistoryLabelAdded] = Json.format[Schema.HistoryLabelAdded]
	given fmtHistoryLabelRemoved: Format[Schema.HistoryLabelRemoved] = Json.format[Schema.HistoryLabelRemoved]
	given fmtBatchDeleteMessagesRequest: Format[Schema.BatchDeleteMessagesRequest] = Json.format[Schema.BatchDeleteMessagesRequest]
	given fmtListMessagesResponse: Format[Schema.ListMessagesResponse] = Json.format[Schema.ListMessagesResponse]
	given fmtModifyMessageRequest: Format[Schema.ModifyMessageRequest] = Json.format[Schema.ModifyMessageRequest]
	given fmtBatchModifyMessagesRequest: Format[Schema.BatchModifyMessagesRequest] = Json.format[Schema.BatchModifyMessagesRequest]
	given fmtLabel: Format[Schema.Label] = Json.format[Schema.Label]
	given fmtLabelMessageListVisibilityEnum: Format[Schema.Label.MessageListVisibilityEnum] = JsonEnumFormat[Schema.Label.MessageListVisibilityEnum]
	given fmtLabelLabelListVisibilityEnum: Format[Schema.Label.LabelListVisibilityEnum] = JsonEnumFormat[Schema.Label.LabelListVisibilityEnum]
	given fmtLabelTypeEnum: Format[Schema.Label.TypeEnum] = JsonEnumFormat[Schema.Label.TypeEnum]
	given fmtLabelColor: Format[Schema.LabelColor] = Json.format[Schema.LabelColor]
	given fmtListLabelsResponse: Format[Schema.ListLabelsResponse] = Json.format[Schema.ListLabelsResponse]
	given fmtProfile: Format[Schema.Profile] = Json.format[Schema.Profile]
	given fmtWatchRequest: Format[Schema.WatchRequest] = Json.format[Schema.WatchRequest]
	given fmtWatchRequestLabelFilterActionEnum: Format[Schema.WatchRequest.LabelFilterActionEnum] = JsonEnumFormat[Schema.WatchRequest.LabelFilterActionEnum]
	given fmtWatchRequestLabelFilterBehaviorEnum: Format[Schema.WatchRequest.LabelFilterBehaviorEnum] = JsonEnumFormat[Schema.WatchRequest.LabelFilterBehaviorEnum]
	given fmtWatchResponse: Format[Schema.WatchResponse] = Json.format[Schema.WatchResponse]
	given fmtThread: Format[Schema.Thread] = Json.format[Schema.Thread]
	given fmtListThreadsResponse: Format[Schema.ListThreadsResponse] = Json.format[Schema.ListThreadsResponse]
	given fmtModifyThreadRequest: Format[Schema.ModifyThreadRequest] = Json.format[Schema.ModifyThreadRequest]
	given fmtListSendAsResponse: Format[Schema.ListSendAsResponse] = Json.format[Schema.ListSendAsResponse]
	given fmtSendAs: Format[Schema.SendAs] = Json.format[Schema.SendAs]
	given fmtSmtpMsa: Format[Schema.SmtpMsa] = Json.format[Schema.SmtpMsa]
	given fmtSendAsVerificationStatusEnum: Format[Schema.SendAs.VerificationStatusEnum] = JsonEnumFormat[Schema.SendAs.VerificationStatusEnum]
	given fmtSmtpMsaSecurityModeEnum: Format[Schema.SmtpMsa.SecurityModeEnum] = JsonEnumFormat[Schema.SmtpMsa.SecurityModeEnum]
	given fmtListSmimeInfoResponse: Format[Schema.ListSmimeInfoResponse] = Json.format[Schema.ListSmimeInfoResponse]
	given fmtSmimeInfo: Format[Schema.SmimeInfo] = Json.format[Schema.SmimeInfo]
	given fmtCseIdentity: Format[Schema.CseIdentity] = Json.format[Schema.CseIdentity]
	given fmtSignAndEncryptKeyPairs: Format[Schema.SignAndEncryptKeyPairs] = Json.format[Schema.SignAndEncryptKeyPairs]
	given fmtCseKeyPair: Format[Schema.CseKeyPair] = Json.format[Schema.CseKeyPair]
	given fmtCseKeyPairEnablementStateEnum: Format[Schema.CseKeyPair.EnablementStateEnum] = JsonEnumFormat[Schema.CseKeyPair.EnablementStateEnum]
	given fmtCsePrivateKeyMetadata: Format[Schema.CsePrivateKeyMetadata] = Json.format[Schema.CsePrivateKeyMetadata]
	given fmtKaclsKeyMetadata: Format[Schema.KaclsKeyMetadata] = Json.format[Schema.KaclsKeyMetadata]
	given fmtHardwareKeyMetadata: Format[Schema.HardwareKeyMetadata] = Json.format[Schema.HardwareKeyMetadata]
	given fmtDisableCseKeyPairRequest: Format[Schema.DisableCseKeyPairRequest] = Json.format[Schema.DisableCseKeyPairRequest]
	given fmtEnableCseKeyPairRequest: Format[Schema.EnableCseKeyPairRequest] = Json.format[Schema.EnableCseKeyPairRequest]
	given fmtListCseIdentitiesResponse: Format[Schema.ListCseIdentitiesResponse] = Json.format[Schema.ListCseIdentitiesResponse]
	given fmtListCseKeyPairsResponse: Format[Schema.ListCseKeyPairsResponse] = Json.format[Schema.ListCseKeyPairsResponse]
	given fmtObliterateCseKeyPairRequest: Format[Schema.ObliterateCseKeyPairRequest] = Json.format[Schema.ObliterateCseKeyPairRequest]
	given fmtListFiltersResponse: Format[Schema.ListFiltersResponse] = Json.format[Schema.ListFiltersResponse]
	given fmtFilter: Format[Schema.Filter] = Json.format[Schema.Filter]
	given fmtFilterCriteria: Format[Schema.FilterCriteria] = Json.format[Schema.FilterCriteria]
	given fmtFilterAction: Format[Schema.FilterAction] = Json.format[Schema.FilterAction]
	given fmtFilterCriteriaSizeComparisonEnum: Format[Schema.FilterCriteria.SizeComparisonEnum] = JsonEnumFormat[Schema.FilterCriteria.SizeComparisonEnum]
	given fmtImapSettings: Format[Schema.ImapSettings] = Json.format[Schema.ImapSettings]
	given fmtImapSettingsExpungeBehaviorEnum: Format[Schema.ImapSettings.ExpungeBehaviorEnum] = JsonEnumFormat[Schema.ImapSettings.ExpungeBehaviorEnum]
	given fmtPopSettings: Format[Schema.PopSettings] = Json.format[Schema.PopSettings]
	given fmtPopSettingsAccessWindowEnum: Format[Schema.PopSettings.AccessWindowEnum] = JsonEnumFormat[Schema.PopSettings.AccessWindowEnum]
	given fmtPopSettingsDispositionEnum: Format[Schema.PopSettings.DispositionEnum] = JsonEnumFormat[Schema.PopSettings.DispositionEnum]
	given fmtVacationSettings: Format[Schema.VacationSettings] = Json.format[Schema.VacationSettings]
	given fmtLanguageSettings: Format[Schema.LanguageSettings] = Json.format[Schema.LanguageSettings]
	given fmtListForwardingAddressesResponse: Format[Schema.ListForwardingAddressesResponse] = Json.format[Schema.ListForwardingAddressesResponse]
	given fmtForwardingAddress: Format[Schema.ForwardingAddress] = Json.format[Schema.ForwardingAddress]
	given fmtForwardingAddressVerificationStatusEnum: Format[Schema.ForwardingAddress.VerificationStatusEnum] = JsonEnumFormat[Schema.ForwardingAddress.VerificationStatusEnum]
	given fmtAutoForwarding: Format[Schema.AutoForwarding] = Json.format[Schema.AutoForwarding]
	given fmtAutoForwardingDispositionEnum: Format[Schema.AutoForwarding.DispositionEnum] = JsonEnumFormat[Schema.AutoForwarding.DispositionEnum]
	given fmtListDelegatesResponse: Format[Schema.ListDelegatesResponse] = Json.format[Schema.ListDelegatesResponse]
	given fmtDelegate: Format[Schema.Delegate] = Json.format[Schema.Delegate]
	given fmtDelegateVerificationStatusEnum: Format[Schema.Delegate.VerificationStatusEnum] = JsonEnumFormat[Schema.Delegate.VerificationStatusEnum]
}