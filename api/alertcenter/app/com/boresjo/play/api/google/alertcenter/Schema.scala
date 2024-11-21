package com.boresjo.play.api.google.alertcenter

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	case class ListAlertsResponse(
	  /** The list of alerts. */
		alerts: Option[List[Schema.Alert]] = None,
	  /** The token for the next page. If not empty, indicates that there may be more alerts that match the listing request; this value can be used in a subsequent ListAlertsRequest to get alerts continuing from last result of the current list call. */
		nextPageToken: Option[String] = None
	)
	
	case class Alert(
	  /** Output only. The unique identifier of the Google Workspace account of the customer. */
		customerId: Option[String] = None,
	  /** Output only. The unique identifier for the alert. */
		alertId: Option[String] = None,
	  /** Output only. The time this alert was created. */
		createTime: Option[String] = None,
	  /** Required. The time the event that caused this alert was started or detected. */
		startTime: Option[String] = None,
	  /** Optional. The time the event that caused this alert ceased being active. If provided, the end time must not be earlier than the start time. If not provided, it indicates an ongoing alert. */
		endTime: Option[String] = None,
	  /** Required. The type of the alert. This is output only after alert is created. For a list of available alert types see [Google Workspace Alert types](https://developers.google.com/admin-sdk/alertcenter/reference/alert-types). */
		`type`: Option[String] = None,
	  /** Required. A unique identifier for the system that reported the alert. This is output only after alert is created. Supported sources are any of the following: &#42; Google Operations &#42; Mobile device management &#42; Gmail phishing &#42; Data Loss Prevention &#42; Domain wide takeout &#42; State sponsored attack &#42; Google identity &#42; Apps outage */
		source: Option[String] = None,
	  /** Optional. The data associated with this alert, for example google.apps.alertcenter.type.DeviceCompromised. */
		data: Option[Map[String, JsValue]] = None,
	  /** Output only. An optional [Security Investigation Tool](https://support.google.com/a/answer/7575955) query for this alert. */
		securityInvestigationToolLink: Option[String] = None,
	  /** Output only. `True` if this alert is marked for deletion. */
		deleted: Option[Boolean] = None,
	  /** Output only. The metadata associated with this alert. */
		metadata: Option[Schema.AlertMetadata] = None,
	  /** Output only. The time this alert was last updated. */
		updateTime: Option[String] = None,
	  /** Optional. `etag` is used for optimistic concurrency control as a way to help prevent simultaneous updates of an alert from overwriting each other. It is strongly suggested that systems make use of the `etag` in the read-modify-write cycle to perform alert updates in order to avoid race conditions: An `etag` is returned in the response which contains alerts, and systems are expected to put that etag in the request to update alert to ensure that their change will be applied to the same version of the alert. If no `etag` is provided in the call to update alert, then the existing alert is overwritten blindly. */
		etag: Option[String] = None
	)
	
	case class AlertMetadata(
	  /** Output only. The unique identifier of the Google Workspace account of the customer. */
		customerId: Option[String] = None,
	  /** Output only. The alert identifier. */
		alertId: Option[String] = None,
	  /** The current status of the alert. The supported values are the following: &#42; NOT_STARTED &#42; IN_PROGRESS &#42; CLOSED */
		status: Option[String] = None,
	  /** The email address of the user assigned to the alert. */
		assignee: Option[String] = None,
	  /** Output only. The time this metadata was last updated. */
		updateTime: Option[String] = None,
	  /** The severity value of the alert. Alert Center will set this field at alert creation time, default's to an empty string when it could not be determined. The supported values for update actions on this field are the following: &#42; HIGH &#42; MEDIUM &#42; LOW */
		severity: Option[String] = None,
	  /** Optional. `etag` is used for optimistic concurrency control as a way to help prevent simultaneous updates of an alert metadata from overwriting each other. It is strongly suggested that systems make use of the `etag` in the read-modify-write cycle to perform metadata updates in order to avoid race conditions: An `etag` is returned in the response which contains alert metadata, and systems are expected to put that etag in the request to update alert metadata to ensure that their change will be applied to the same version of the alert metadata. If no `etag` is provided in the call to update alert metadata, then the existing alert metadata is overwritten blindly. */
		etag: Option[String] = None
	)
	
	case class Empty(
	
	)
	
	case class UndeleteAlertRequest(
	  /** Optional. The unique identifier of the Google Workspace account of the customer the alert is associated with. The `customer_id` must have the initial "C" stripped (for example, `046psxkn`). Inferred from the caller identity if not provided. [Find your customer ID](https://support.google.com/cloudidentity/answer/10070793). */
		customerId: Option[String] = None
	)
	
	object AlertFeedback {
		enum TypeEnum extends Enum[TypeEnum] { case ALERT_FEEDBACK_TYPE_UNSPECIFIED, NOT_USEFUL, SOMEWHAT_USEFUL, VERY_USEFUL }
	}
	case class AlertFeedback(
	  /** Output only. The unique identifier of the Google Workspace account of the customer. */
		customerId: Option[String] = None,
	  /** Output only. The alert identifier. */
		alertId: Option[String] = None,
	  /** Output only. The unique identifier for the feedback. */
		feedbackId: Option[String] = None,
	  /** Output only. The time this feedback was created. */
		createTime: Option[String] = None,
	  /** Required. The type of the feedback. */
		`type`: Option[Schema.AlertFeedback.TypeEnum] = None,
	  /** Output only. The email of the user that provided the feedback. */
		email: Option[String] = None
	)
	
	case class ListAlertFeedbackResponse(
	  /** The list of alert feedback. Feedback entries for each alert are ordered by creation time descending. */
		feedback: Option[List[Schema.AlertFeedback]] = None
	)
	
	case class Settings(
	  /** The list of notifications. */
		notifications: Option[List[Schema.Notification]] = None
	)
	
	case class Notification(
	  /** A Google Cloud Pub/sub topic destination. */
		cloudPubsubTopic: Option[Schema.CloudPubsubTopic] = None
	)
	
	object CloudPubsubTopic {
		enum PayloadFormatEnum extends Enum[PayloadFormatEnum] { case PAYLOAD_FORMAT_UNSPECIFIED, JSON }
	}
	case class CloudPubsubTopic(
	  /** The `name` field of a Cloud Pubsub [Topic] (https://cloud.google.com/pubsub/docs/reference/rest/v1/projects.topics#Topic). */
		topicName: Option[String] = None,
	  /** Optional. The format of the payload that would be sent. If not specified the format will be JSON. */
		payloadFormat: Option[Schema.CloudPubsubTopic.PayloadFormatEnum] = None
	)
	
	case class BatchDeleteAlertsRequest(
	  /** Optional. The unique identifier of the Google Workspace account of the customer the alerts are associated with. The `customer_id` must have the initial "C" stripped (for example, `046psxkn`). Inferred from the caller identity if not provided. [Find your customer ID](https://support.google.com/cloudidentity/answer/10070793). */
		customerId: Option[String] = None,
	  /** Required. The list of alert IDs to delete. */
		alertId: Option[List[String]] = None
	)
	
	case class BatchDeleteAlertsResponse(
	  /** The successful list of alert IDs. */
		successAlertIds: Option[List[String]] = None,
	  /** The status details for each failed `alert_id`. */
		failedAlertStatus: Option[Map[String, Schema.Status]] = None
	)
	
	case class Status(
	  /** The status code, which should be an enum value of google.rpc.Code. */
		code: Option[Int] = None,
	  /** A developer-facing error message, which should be in English. Any user-facing error message should be localized and sent in the google.rpc.Status.details field, or localized by the client. */
		message: Option[String] = None,
	  /** A list of messages that carry the error details. There is a common set of message types for APIs to use. */
		details: Option[List[Map[String, JsValue]]] = None
	)
	
	case class BatchUndeleteAlertsRequest(
	  /** Optional. The unique identifier of the Google Workspace account of the customer the alerts are associated with. The `customer_id` must have the initial "C" stripped (for example, `046psxkn`). Inferred from the caller identity if not provided. [Find your customer ID](https://support.google.com/cloudidentity/answer/10070793). */
		customerId: Option[String] = None,
	  /** Required. The list of alert IDs to undelete. */
		alertId: Option[List[String]] = None
	)
	
	case class BatchUndeleteAlertsResponse(
	  /** The successful list of alert IDs. */
		successAlertIds: Option[List[String]] = None,
	  /** The status details for each failed `alert_id`. */
		failedAlertStatus: Option[Map[String, Schema.Status]] = None
	)
	
	case class BadWhitelist(
	  /** The domain ID. */
		domainId: Option[Schema.DomainId] = None,
	  /** The entity whose actions triggered a Gmail phishing alert. */
		maliciousEntity: Option[Schema.MaliciousEntity] = None,
	  /** The list of messages contained by this alert. */
		messages: Option[List[Schema.GmailMessageInfo]] = None,
	  /** The source IP address of the malicious email, for example, `127.0.0.1`. */
		sourceIp: Option[String] = None
	)
	
	case class DomainId(
	  /** The primary domain for the customer. */
		customerPrimaryDomain: Option[String] = None
	)
	
	case class MaliciousEntity(
	  /** The sender email address. */
		fromHeader: Option[String] = None,
	  /** The header from display name. */
		displayName: Option[String] = None,
	  /** The actor who triggered a gmail phishing alert. */
		entity: Option[Schema.User] = None
	)
	
	case class User(
	  /** Email address of the user. */
		emailAddress: Option[String] = None,
	  /** Display name of the user. */
		displayName: Option[String] = None
	)
	
	case class GmailMessageInfo(
	  /** The message ID. */
		messageId: Option[String] = None,
	  /** The hash of the message body text. */
		md5HashMessageBody: Option[String] = None,
	  /** The snippet of the message body text (only available for reported emails). */
		messageBodySnippet: Option[String] = None,
	  /** The MD5 Hash of email's subject (only available for reported emails). */
		md5HashSubject: Option[String] = None,
	  /** The email subject text (only available for reported emails). */
		subjectText: Option[String] = None,
	  /** The `SHA256` hash of email's attachment and all MIME parts. */
		attachmentsSha256Hash: Option[List[String]] = None,
	  /** The recipient of this email. */
		recipient: Option[String] = None,
	  /** The date of the event related to this email. */
		date: Option[String] = None,
	  /** The sent time of the email. */
		sentTime: Option[String] = None
	)
	
	case class DeviceCompromised(
	  /** The email of the user this alert was created for. */
		email: Option[String] = None,
	  /** Required. The list of security events. */
		events: Option[List[Schema.DeviceCompromisedSecurityDetail]] = None
	)
	
	case class DeviceCompromisedSecurityDetail(
	  /** Required. The device ID. */
		deviceId: Option[String] = None,
	  /** The serial number of the device. */
		serialNumber: Option[String] = None,
	  /** The type of the device. */
		deviceType: Option[String] = None,
	  /** The model of the device. */
		deviceModel: Option[String] = None,
	  /** The device resource ID. */
		resourceId: Option[String] = None,
	  /** Required for iOS, empty for others. */
		iosVendorId: Option[String] = None,
	  /** The device compromised state. Possible values are "`Compromised`" or "`Not Compromised`". */
		deviceCompromisedState: Option[String] = None
	)
	
	case class GoogleOperations(
	  /** A one-line incident description. */
		title: Option[String] = None,
	  /** A detailed, freeform incident description. */
		description: Option[String] = None,
	  /** The list of emails which correspond to the users directly affected by the incident. */
		affectedUserEmails: Option[List[String]] = None,
	  /** Optional. Application-specific data for an incident, provided when the Google Workspace application which reported the incident cannot be completely restored to a valid state. */
		attachmentData: Option[Schema.Attachment] = None,
	  /** A header to display above the incident message. Typically used to attach a localized notice on the timeline for followup comms translations. */
		header: Option[String] = None,
	  /** Customer domain for email template personalization. */
		domain: Option[String] = None
	)
	
	case class Attachment(
	  /** A CSV file attachment. */
		csv: Option[Schema.Csv] = None
	)
	
	case class Csv(
	  /** The list of headers for data columns in a CSV file. */
		headers: Option[List[String]] = None,
	  /** The list of data rows in a CSV file, as string arrays rather than as a single comma-separated string. */
		dataRows: Option[List[Schema.CsvRow]] = None
	)
	
	case class CsvRow(
	  /** The data entries in a CSV file row, as a string array rather than a single comma-separated string. */
		entries: Option[List[String]] = None
	)
	
	object MailPhishing {
		enum SystemActionTypeEnum extends Enum[SystemActionTypeEnum] { case SYSTEM_ACTION_TYPE_UNSPECIFIED, NO_OPERATION, REMOVED_FROM_INBOX }
	}
	case class MailPhishing(
	  /** The domain ID. */
		domainId: Option[Schema.DomainId] = None,
	  /** The entity whose actions triggered a Gmail phishing alert. */
		maliciousEntity: Option[Schema.MaliciousEntity] = None,
	  /** The list of messages contained by this alert. */
		messages: Option[List[Schema.GmailMessageInfo]] = None,
	  /** If `true`, the email originated from within the organization. */
		isInternal: Option[Boolean] = None,
	  /** System actions on the messages. */
		systemActionType: Option[Schema.MailPhishing.SystemActionTypeEnum] = None
	)
	
	case class PhishingSpike(
	  /** The domain ID. */
		domainId: Option[Schema.DomainId] = None,
	  /** The entity whose actions triggered a Gmail phishing alert. */
		maliciousEntity: Option[Schema.MaliciousEntity] = None,
	  /** The list of messages contained by this alert. */
		messages: Option[List[Schema.GmailMessageInfo]] = None,
	  /** If `true`, the email originated from within the organization. */
		isInternal: Option[Boolean] = None
	)
	
	case class SuspiciousActivity(
	  /** The email of the user this alert was created for. */
		email: Option[String] = None,
	  /** Required. The list of security events. */
		events: Option[List[Schema.SuspiciousActivitySecurityDetail]] = None
	)
	
	case class SuspiciousActivitySecurityDetail(
	  /** Required. The device ID. */
		deviceId: Option[String] = None,
	  /** The serial number of the device. */
		serialNumber: Option[String] = None,
	  /** The type of the device. */
		deviceType: Option[String] = None,
	  /** The model of the device. */
		deviceModel: Option[String] = None,
	  /** The device resource ID. */
		resourceId: Option[String] = None,
	  /** Required for iOS, empty for others. */
		iosVendorId: Option[String] = None,
	  /** The device property which was changed. */
		deviceProperty: Option[String] = None,
	  /** The old value of the device property before the change. */
		oldValue: Option[String] = None,
	  /** The new value of the device property after the change. */
		newValue: Option[String] = None
	)
	
	case class DomainWideTakeoutInitiated(
	  /** The takeout request ID. */
		takeoutRequestId: Option[String] = None,
	  /** The email of the admin who initiated the takeout. */
		email: Option[String] = None
	)
	
	case class StateSponsoredAttack(
	  /** The email of the user this incident was created for. */
		email: Option[String] = None
	)
	
	case class AccountWarning(
	  /** Required. The email of the user that this event belongs to. */
		email: Option[String] = None,
	  /** Optional. Details of the login action associated with the warning event. This is only available for: &#42; Suspicious login &#42; Suspicious login (less secure app) &#42; Suspicious programmatic login &#42; User suspended (suspicious activity) */
		loginDetails: Option[Schema.LoginDetails] = None
	)
	
	case class LoginDetails(
	  /** Optional. The successful login time that is associated with the warning event. This isn't present for blocked login attempts. */
		loginTime: Option[String] = None,
	  /** Optional. The human-readable IP address (for example, `11.22.33.44`) that is associated with the warning event. */
		ipAddress: Option[String] = None
	)
	
	case class AppMakerSqlSetupNotification(
	  /** List of applications with requests for default SQL set up. */
		requestInfo: Option[List[Schema.RequestInfo]] = None
	)
	
	case class RequestInfo(
	  /** Required. The application that requires the SQL setup. */
		appKey: Option[String] = None,
	  /** List of app developers who triggered notifications for above application. */
		appDeveloperEmail: Option[List[String]] = None,
	  /** Required. Number of requests sent for this application to set up default SQL instance. */
		numberOfRequests: Option[String] = None
	)
	
	case class ActivityRule(
	  /** Rule name. */
		name: Option[String] = None,
	  /** Alert display name. */
		displayName: Option[String] = None,
	  /** Description of the rule. */
		description: Option[String] = None,
	  /** Rule window size. Possible values are 1 hour or 24 hours. */
		windowSize: Option[String] = None,
	  /** Alert threshold is for example “COUNT > 5”. */
		threshold: Option[String] = None,
	  /** Rule create timestamp. */
		createTime: Option[String] = None,
	  /** The timestamp of the last update to the rule. */
		updateTime: Option[String] = None,
	  /** The trigger sources for this rule. &#42; GMAIL_EVENTS &#42; DEVICE_EVENTS &#42; USER_EVENTS */
		triggerSource: Option[String] = None,
	  /** List of alert IDs superseded by this alert. It is used to indicate that this alert is essentially extension of superseded alerts and we found the relationship after creating these alerts. */
		supersededAlerts: Option[List[String]] = None,
	  /** Alert ID superseding this alert. It is used to indicate that superseding alert is essentially extension of this alert and we found the relationship after creating both alerts. */
		supersedingAlert: Option[String] = None,
	  /** List of action names associated with the rule threshold. */
		actionNames: Option[List[String]] = None,
	  /** Query that is used to get the data from the associated source. */
		query: Option[String] = None
	)
	
	case class ReportingRule(
	  /** Any other associated alert details, for example, AlertConfiguration. */
		alertDetails: Option[String] = None,
	  /** Rule name */
		name: Option[String] = None,
	  /** Alert Rule query Sample Query query { condition { filter { expected_application_id: 777491262838 expected_event_name: "indexable_content_change" filter_op: IN } } conjunction_operator: OR } */
		query: Option[String] = None
	)
	
	case class AppSettingsChanged(
	  /** Any other associated alert details, for example, AlertConfiguration. */
		alertDetails: Option[String] = None,
	  /** Rule name */
		name: Option[String] = None
	)
	
	case class UserChanges(
	  /** Rule name */
		name: Option[String] = None
	)
	
	case class DlpRuleViolation(
	  /** Details about the violated DLP rule. Admins can use the predefined detectors provided by Google Cloud DLP https://cloud.google.com/dlp/ when setting up a DLP rule. Matched Cloud DLP detectors in this violation if any will be captured in the MatchInfo.predefined_detector. */
		ruleViolationInfo: Option[Schema.RuleViolationInfo] = None
	)
	
	object RuleViolationInfo {
		enum DataSourceEnum extends Enum[DataSourceEnum] { case DATA_SOURCE_UNSPECIFIED, DRIVE, CHROME, CHAT }
		enum TriggerEnum extends Enum[TriggerEnum] { case TRIGGER_UNSPECIFIED, DRIVE_SHARE, CHROME_FILE_DOWNLOAD, CHROME_FILE_UPLOAD, CHROME_WEB_CONTENT_UPLOAD, CHAT_MESSAGE_SENT, CHAT_ATTACHMENT_UPLOADED, CHROME_PAGE_PRINT, CHROME_URL_VISITED }
		enum TriggeredActionTypesEnum extends Enum[TriggeredActionTypesEnum] { case ACTION_TYPE_UNSPECIFIED, DRIVE_BLOCK_EXTERNAL_SHARING, DRIVE_WARN_ON_EXTERNAL_SHARING, DRIVE_RESTRICT_DOWNLOAD_PRINT_COPY, DRIVE_APPLY_DRIVE_LABELS, CHROME_BLOCK_FILE_DOWNLOAD, CHROME_WARN_FILE_DOWNLOAD, CHROME_BLOCK_FILE_UPLOAD, CHROME_WARN_FILE_UPLOAD, CHROME_BLOCK_WEB_CONTENT_UPLOAD, CHROME_WARN_WEB_CONTENT_UPLOAD, CHROME_BLOCK_PAGE_PRINT, CHROME_WARN_PAGE_PRINT, CHROME_BLOCK_URL_VISITED, CHROME_WARN_URL_VISITED, CHROME_STORE_CONTENT, DELETE_WEBPROTECT_EVIDENCE, CHAT_BLOCK_CONTENT, CHAT_WARN_USER, ALERT, RULE_ACTIVATE, RULE_DEACTIVATE }
		enum SuppressedActionTypesEnum extends Enum[SuppressedActionTypesEnum] { case ACTION_TYPE_UNSPECIFIED, DRIVE_BLOCK_EXTERNAL_SHARING, DRIVE_WARN_ON_EXTERNAL_SHARING, DRIVE_RESTRICT_DOWNLOAD_PRINT_COPY, DRIVE_APPLY_DRIVE_LABELS, CHROME_BLOCK_FILE_DOWNLOAD, CHROME_WARN_FILE_DOWNLOAD, CHROME_BLOCK_FILE_UPLOAD, CHROME_WARN_FILE_UPLOAD, CHROME_BLOCK_WEB_CONTENT_UPLOAD, CHROME_WARN_WEB_CONTENT_UPLOAD, CHROME_BLOCK_PAGE_PRINT, CHROME_WARN_PAGE_PRINT, CHROME_BLOCK_URL_VISITED, CHROME_WARN_URL_VISITED, CHROME_STORE_CONTENT, DELETE_WEBPROTECT_EVIDENCE, CHAT_BLOCK_CONTENT, CHAT_WARN_USER, ALERT, RULE_ACTIVATE, RULE_DEACTIVATE }
		enum EventTypeEnum extends Enum[EventTypeEnum] { case EVENT_TYPE_UNSPECIFIED, ACCESS_BLOCKED, SHARING_BLOCKED }
	}
	case class RuleViolationInfo(
	  /** Details of the violated rule. */
		ruleInfo: Option[Schema.RuleInfo] = None,
	  /** Source of the data. */
		dataSource: Option[Schema.RuleViolationInfo.DataSourceEnum] = None,
	  /** Trigger of the rule. */
		trigger: Option[Schema.RuleViolationInfo.TriggerEnum] = None,
	  /** Email of the user who caused the violation. Value could be empty if not applicable, for example, a violation found by drive continuous scan. */
		triggeringUserEmail: Option[String] = None,
	  /** Resource recipients. For Drive, they are grantees that the Drive file was shared with at the time of rule triggering. Valid values include user emails, group emails, domains, or 'anyone' if the file was publicly accessible. If the file was private the recipients list will be empty. For Gmail, they are emails of the users or groups that the Gmail message was sent to. */
		recipients: Option[List[String]] = None,
	  /** Details of the resource which violated the rule. */
		resourceInfo: Option[Schema.ResourceInfo] = None,
	  /** List of matches that were found in the resource content. */
		matchInfo: Option[List[Schema.MatchInfo]] = None,
	  /** Actions applied as a consequence of the rule being triggered. */
		triggeredActionTypes: Option[List[Schema.RuleViolationInfo.TriggeredActionTypesEnum]] = None,
	  /** Metadata related to the triggered actions. */
		triggeredActionInfo: Option[List[Schema.ActionInfo]] = None,
	  /** Actions suppressed due to other actions with higher priority. */
		suppressedActionTypes: Option[List[Schema.RuleViolationInfo.SuppressedActionTypesEnum]] = None,
	  /** Event associated with this alert after applying the rule. */
		eventType: Option[Schema.RuleViolationInfo.EventTypeEnum] = None
	)
	
	case class RuleInfo(
	  /** Resource name that uniquely identifies the rule. */
		resourceName: Option[String] = None,
	  /** User provided name of the rule. */
		displayName: Option[String] = None
	)
	
	case class ResourceInfo(
	  /** Drive file ID. */
		documentId: Option[String] = None,
	  /** Chat message ID. */
		chatMessageId: Option[String] = None,
	  /** Chat attachment ID. */
		chatAttachmentId: Option[String] = None,
	  /** Title of the resource, for example email subject, or document title. */
		resourceTitle: Option[String] = None,
	  /** Id to identify a device. For example, for Android devices, this is the "Android Device Id" and for Chrome OS devices, it's the "Device Virtual Id". */
		deviceId: Option[String] = None
	)
	
	case class MatchInfo(
	  /** For matched detector defined by administrators. */
		userDefinedDetector: Option[Schema.UserDefinedDetectorInfo] = None,
	  /** For matched detector predefined by Google. */
		predefinedDetector: Option[Schema.PredefinedDetectorInfo] = None
	)
	
	case class UserDefinedDetectorInfo(
	  /** Resource name that uniquely identifies the detector. */
		resourceName: Option[String] = None,
	  /** Display name of the detector. */
		displayName: Option[String] = None
	)
	
	case class PredefinedDetectorInfo(
	  /** Name that uniquely identifies the detector. */
		detectorName: Option[String] = None
	)
	
	case class ActionInfo(
	
	)
	
	object VoiceMisconfiguration {
		enum EntityTypeEnum extends Enum[EntityTypeEnum] { case ENTITY_TYPE_UNSPECIFIED, AUTO_ATTENDANT, RING_GROUP }
	}
	case class VoiceMisconfiguration(
	  /** Name of the entity whose configuration is now invalid. */
		entityName: Option[String] = None,
	  /** Type of the entity whose configuration is now invalid. */
		entityType: Option[Schema.VoiceMisconfiguration.EntityTypeEnum] = None,
	  /** Issue(s) with sending to voicemail. */
		voicemailMisconfiguration: Option[Schema.VoicemailMisconfiguration] = None,
	  /** Issue(s) with transferring or forwarding to an external entity. */
		transferMisconfiguration: Option[Schema.TransferMisconfiguration] = None,
	  /** Issue(s) with members of a ring group. */
		membersMisconfiguration: Option[Schema.TransferMisconfiguration] = None,
	  /** Link that the admin can follow to fix the issue. */
		fixUri: Option[String] = None
	)
	
	case class VoicemailMisconfiguration(
	  /** Issue(s) with voicemail recipients. */
		errors: Option[List[Schema.VoicemailRecipientError]] = None
	)
	
	object VoicemailRecipientError {
		enum InvalidReasonEnum extends Enum[InvalidReasonEnum] { case EMAIL_INVALID_REASON_UNSPECIFIED, OUT_OF_QUOTA, RECIPIENT_DELETED }
	}
	case class VoicemailRecipientError(
	  /** Reason for the error. */
		invalidReason: Option[Schema.VoicemailRecipientError.InvalidReasonEnum] = None,
	  /** Email address of the invalid recipient. This may be unavailable if the recipient was deleted. */
		email: Option[String] = None
	)
	
	case class TransferMisconfiguration(
	  /** Details for each invalid transfer or forward. */
		errors: Option[List[Schema.TransferError]] = None
	)
	
	object TransferError {
		enum EntityTypeEnum extends Enum[EntityTypeEnum] { case TRANSFER_ENTITY_TYPE_UNSPECIFIED, TRANSFER_AUTO_ATTENDANT, TRANSFER_RING_GROUP, TRANSFER_USER }
		enum InvalidReasonEnum extends Enum[InvalidReasonEnum] { case TRANSFER_INVALID_REASON_UNSPECIFIED, TRANSFER_TARGET_DELETED, UNLICENSED, SUSPENDED, NO_PHONE_NUMBER }
	}
	case class TransferError(
	  /** Type of entity being transferred to. For ring group members, this should always be USER. */
		entityType: Option[Schema.TransferError.EntityTypeEnum] = None,
	  /** Reason for the error. */
		invalidReason: Option[Schema.TransferError.InvalidReasonEnum] = None,
	  /** Ring group or auto attendant ID. Not set for users. */
		id: Option[String] = None,
	  /** User's full name, or the ring group / auto attendant name. This may be unavailable if the entity was deleted. */
		name: Option[String] = None,
	  /** User's email address. This may be unavailable if the entity was deleted. */
		email: Option[String] = None
	)
	
	object AccountSuspensionWarning {
		enum StateEnum extends Enum[StateEnum] { case ACCOUNT_SUSPENSION_WARNING_STATE_UNSPECIFIED, WARNING, SUSPENDED, APPEAL_APPROVED, APPEAL_SUBMITTED }
	}
	case class AccountSuspensionWarning(
	  /** Account suspension warning state. */
		state: Option[Schema.AccountSuspensionWarning.StateEnum] = None,
	  /** Details about why an account is being suspended. */
		suspensionDetails: Option[List[Schema.AccountSuspensionDetails]] = None,
	  /** The amount of time remaining to appeal an imminent suspension. After this window has elapsed, the account will be suspended. Only populated if the account suspension is in WARNING state. */
		appealWindow: Option[String] = None
	)
	
	object AccountSuspensionDetails {
		enum AbuseReasonEnum extends Enum[AbuseReasonEnum] { case ACCOUNT_SUSPENSION_ABUSE_REASON_UNSPECIFIED, TOS_VIOLATION, SPAM, PHISHING, TRAFFIC_PUMPING, FRAUD, NUMBER_HARVESTING, PAYMENTS_FRAUD, UNWANTED_CONTENT }
	}
	case class AccountSuspensionDetails(
	  /** The reason why this account is receiving an account suspension warning. */
		abuseReason: Option[Schema.AccountSuspensionDetails.AbuseReasonEnum] = None,
	  /** The name of the product being abused. This is restricted to only the following values: "Gmail" "Google Workspace" "Payments" "Voice" "YouTube" "Other" */
		productName: Option[String] = None
	)
	
	object AppsOutage {
		enum StatusEnum extends Enum[StatusEnum] { case STATUS_UNSPECIFIED, NEW, ONGOING, RESOLVED, FALSE_POSITIVE, PARTIALLY_RESOLVED, MERGED, DOWNGRADED }
	}
	case class AppsOutage(
	  /** List of products impacted by the outage. */
		products: Option[List[String]] = None,
	  /** Timestamp by which the next update is expected to arrive. */
		nextUpdateTime: Option[String] = None,
	  /** Timestamp when the outage is expected to be resolved, or has confirmed resolution. Provided only when known. */
		resolutionTime: Option[String] = None,
	  /** Link to the outage event in Google Workspace Status Dashboard */
		dashboardUri: Option[String] = None,
	  /** Current outage status. */
		status: Option[Schema.AppsOutage.StatusEnum] = None,
	  /** Incident tracking ID. */
		incidentTrackingId: Option[String] = None,
	  /** Indicates new alert details under which the outage is communicated. Only populated when Status is MERGED. */
		mergeInfo: Option[Schema.MergeInfo] = None
	)
	
	case class MergeInfo(
	  /** The new tracking ID from the parent incident. */
		newIncidentTrackingId: Option[String] = None,
	  /** Optional. New alert ID. Reference the [google.apps.alertcenter.Alert] with this ID for the current state. */
		newAlertId: Option[String] = None
	)
	
	object AccessApproval {
		enum JustificationReasonEnum extends Enum[JustificationReasonEnum] { case JUSTIFICATION_UNSPECIFIED, CUSTOMER_INITIATED_SUPPORT, GOOGLE_INITIATED_REVIEW, GOOGLE_INITIATED_SERVICE, THIRD_PARTY_DATA_REQUEST, GOOGLE_RESPONSE_TO_PRODUCTION_ALERT }
	}
	case class AccessApproval(
	  /** ID of the Access Approvals request. This is a helpful field when requesting support from Google. */
		requestId: Option[String] = None,
	  /** Scope of access, also known as a resource. This is further narrowed down by the product field. */
		scope: Option[String] = None,
	  /** Support tickets related to this Access Approvals request. Populated if there is an associated case number. */
		tickets: Option[List[Schema.SupportTicket]] = None,
	  /** Office location of Google staff requesting access such as "US". */
		officeLocation: Option[String] = None,
	  /** Justification for data access based on justification enums. */
		justificationReason: Option[List[Schema.AccessApproval.JustificationReasonEnum]] = None,
	  /** Products within scope of the Access Approvals request. */
		products: Option[List[String]] = None
	)
	
	case class SupportTicket(
	  /** Support ticket ID */
		ticketId: Option[String] = None,
	  /** Link to support ticket */
		ticketUrl: Option[String] = None
	)
	
	case class MandatoryServiceAnnouncement(
	  /** One line summary of the announcement */
		title: Option[String] = None,
	  /** Detailed, freeform text describing the announcement */
		description: Option[String] = None
	)
	
	case class SensitiveAdminAction(
	  /** The time at which event occurred */
		eventTime: Option[String] = None,
	  /** Email of person who performed the action */
		actorEmail: Option[String] = None,
	  /** Event occurred when primary admin changed in customer's account */
		primaryAdminChangedEvent: Option[Schema.PrimaryAdminChangedEvent] = None,
	  /** Event occurred when SSO Profile created in customer's account */
		ssoProfileCreatedEvent: Option[Schema.SSOProfileCreatedEvent] = None,
	  /** Event occurred when SSO Profile updated in customer's account */
		ssoProfileUpdatedEvent: Option[Schema.SSOProfileUpdatedEvent] = None,
	  /** Event occurred when SSO Profile deleted in customer's account */
		ssoProfileDeletedEvent: Option[Schema.SSOProfileDeletedEvent] = None,
	  /** Event occurred when password was reset for super admin in customer's account */
		superAdminPasswordResetEvent: Option[Schema.SuperAdminPasswordResetEvent] = None
	)
	
	case class PrimaryAdminChangedEvent(
	  /** domain in which actioned occurred */
		domain: Option[String] = None,
	  /** Email of person who was the primary admin before the action */
		previousAdminEmail: Option[String] = None,
	  /** Email of person who is the primary admin after the action */
		updatedAdminEmail: Option[String] = None
	)
	
	case class SSOProfileCreatedEvent(
	  /** sso profile name which got created */
		inboundSsoProfileName: Option[String] = None
	)
	
	case class SSOProfileUpdatedEvent(
	  /** sso profile name which got updated */
		inboundSsoProfileName: Option[String] = None,
	  /** changes made to sso profile */
		inboundSsoProfileChanges: Option[String] = None
	)
	
	case class SSOProfileDeletedEvent(
	  /** sso profile name which got deleted */
		inboundSsoProfileName: Option[String] = None
	)
	
	case class SuperAdminPasswordResetEvent(
	  /** email of person whose password was reset */
		userEmail: Option[String] = None
	)
	
	case class ApnsCertificateExpirationInfo(
	  /** The expiration date of the APNS certificate. */
		expirationTime: Option[String] = None,
	  /** The Apple ID used to create the certificate. It may be blank if admins didn't enter it. */
		appleId: Option[String] = None,
	  /** The UID of the certificate. */
		uid: Option[String] = None
	)
	
	object AbuseDetected {
		enum VariationTypeEnum extends Enum[VariationTypeEnum] { case ABUSE_DETECTED_VARIATION_TYPE_UNSPECIFIED, DRIVE_ABUSIVE_CONTENT, LIMITED_DISABLE }
	}
	case class AbuseDetected(
	  /** Unique identifier of each sub alert that is onboarded. */
		subAlertId: Option[String] = None,
	  /** Product that the abuse is originating from. */
		product: Option[String] = None,
	  /** List of abusive users/entities to be displayed in a table in the alert. */
		additionalDetails: Option[Schema.EntityList] = None,
	  /** Variation of AbuseDetected alerts. The variation_type determines the texts displayed the alert details. This differs from sub_alert_id because each sub alert can have multiple variation_types, representing different stages of the alert. */
		variationType: Option[Schema.AbuseDetected.VariationTypeEnum] = None
	)
	
	case class EntityList(
	  /** Name of the key detail used to display this entity list. */
		name: Option[String] = None,
	  /** Headers of the values in entities. If no value is defined in Entity, this field should be empty. */
		headers: Option[List[String]] = None,
	  /** List of entities affected by the alert. */
		entities: Option[List[Schema.Entity]] = None
	)
	
	case class Entity(
	  /** Human-readable name of this entity, such as an email address, file ID, or device name. */
		name: Option[String] = None,
	  /** Link to a Security Investigation Tool search based on this entity, if available. */
		link: Option[String] = None,
	  /** Extra values beyond name. The order of values should align with headers in EntityList. */
		values: Option[List[String]] = None
	)
	
	case class DeviceManagementRule(
	  /** The email of the user this alert was created for. */
		email: Option[String] = None,
	  /** Required. The device ID. */
		deviceId: Option[String] = None,
	  /** The serial number of the device. */
		serialNumber: Option[String] = None,
	  /** The type of the device. */
		deviceType: Option[String] = None,
	  /** The model of the device. */
		deviceModel: Option[String] = None,
	  /** The device resource ID. */
		resourceId: Option[String] = None,
	  /** Required for iOS, empty for others. */
		iosVendorId: Option[String] = None,
	  /** Action taken as result of the rule */
		ruleAction: Option[String] = None,
	  /** Obfuscated ID of the owner of the device */
		ownerId: Option[String] = None,
	  /** ID of the rule that triggered the alert */
		id: Option[String] = None
	)
}
