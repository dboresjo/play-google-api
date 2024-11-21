package com.boresjo.play.api.google.gmail

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	case class Draft(
	  /** The immutable ID of the draft. */
		id: Option[String] = None,
	  /** The message content of the draft. */
		message: Option[Schema.Message] = None
	)
	
	case class Message(
	  /** The immutable ID of the message. */
		id: Option[String] = None,
	  /** The ID of the thread the message belongs to. To add a message or draft to a thread, the following criteria must be met: 1. The requested `threadId` must be specified on the `Message` or `Draft.Message` you supply with your request. 2. The `References` and `In-Reply-To` headers must be set in compliance with the [RFC 2822](https://tools.ietf.org/html/rfc2822) standard. 3. The `Subject` headers must match.  */
		threadId: Option[String] = None,
	  /** List of IDs of labels applied to this message. */
		labelIds: Option[List[String]] = None,
	  /** A short part of the message text. */
		snippet: Option[String] = None,
	  /** The ID of the last history record that modified this message. */
		historyId: Option[String] = None,
	  /** The internal message creation timestamp (epoch ms), which determines ordering in the inbox. For normal SMTP-received email, this represents the time the message was originally accepted by Google, which is more reliable than the `Date` header. However, for API-migrated mail, it can be configured by client to be based on the `Date` header. */
		internalDate: Option[String] = None,
	  /** The parsed email structure in the message parts. */
		payload: Option[Schema.MessagePart] = None,
	  /** Estimated size in bytes of the message. */
		sizeEstimate: Option[Int] = None,
	  /** The entire email message in an RFC 2822 formatted and base64url encoded string. Returned in `messages.get` and `drafts.get` responses when the `format=RAW` parameter is supplied. */
		raw: Option[String] = None
	)
	
	case class MessagePart(
	  /** The immutable ID of the message part. */
		partId: Option[String] = None,
	  /** The MIME type of the message part. */
		mimeType: Option[String] = None,
	  /** The filename of the attachment. Only present if this message part represents an attachment. */
		filename: Option[String] = None,
	  /** List of headers on this message part. For the top-level message part, representing the entire message payload, it will contain the standard RFC 2822 email headers such as `To`, `From`, and `Subject`. */
		headers: Option[List[Schema.MessagePartHeader]] = None,
	  /** The message part body for this part, which may be empty for container MIME message parts. */
		body: Option[Schema.MessagePartBody] = None,
	  /** The child MIME message parts of this part. This only applies to container MIME message parts, for example `multipart/&#42;`. For non- container MIME message part types, such as `text/plain`, this field is empty. For more information, see RFC 1521. */
		parts: Option[List[Schema.MessagePart]] = None
	)
	
	case class MessagePartHeader(
	  /** The name of the header before the `:` separator. For example, `To`. */
		name: Option[String] = None,
	  /** The value of the header after the `:` separator. For example, `someuser@example.com`. */
		value: Option[String] = None
	)
	
	case class MessagePartBody(
	  /** When present, contains the ID of an external attachment that can be retrieved in a separate `messages.attachments.get` request. When not present, the entire content of the message part body is contained in the data field. */
		attachmentId: Option[String] = None,
	  /** Number of bytes for the message part data (encoding notwithstanding). */
		size: Option[Int] = None,
	  /** The body data of a MIME message part as a base64url encoded string. May be empty for MIME container types that have no message body or when the body data is sent as a separate attachment. An attachment ID is present if the body data is contained in a separate attachment. */
		data: Option[String] = None
	)
	
	case class ListDraftsResponse(
	  /** List of drafts. Note that the `Message` property in each `Draft` resource only contains an `id` and a `threadId`. The messages.get method can fetch additional message details. */
		drafts: Option[List[Schema.Draft]] = None,
	  /** Token to retrieve the next page of results in the list. */
		nextPageToken: Option[String] = None,
	  /** Estimated total number of results. */
		resultSizeEstimate: Option[Int] = None
	)
	
	case class ListHistoryResponse(
	  /** List of history records. Any `messages` contained in the response will typically only have `id` and `threadId` fields populated. */
		history: Option[List[Schema.History]] = None,
	  /** Page token to retrieve the next page of results in the list. */
		nextPageToken: Option[String] = None,
	  /** The ID of the mailbox's current history record. */
		historyId: Option[String] = None
	)
	
	case class History(
	  /** The mailbox sequence ID. */
		id: Option[String] = None,
	  /** List of messages changed in this history record. The fields for specific change types, such as `messagesAdded` may duplicate messages in this field. We recommend using the specific change-type fields instead of this. */
		messages: Option[List[Schema.Message]] = None,
	  /** Messages added to the mailbox in this history record. */
		messagesAdded: Option[List[Schema.HistoryMessageAdded]] = None,
	  /** Messages deleted (not Trashed) from the mailbox in this history record. */
		messagesDeleted: Option[List[Schema.HistoryMessageDeleted]] = None,
	  /** Labels added to messages in this history record. */
		labelsAdded: Option[List[Schema.HistoryLabelAdded]] = None,
	  /** Labels removed from messages in this history record. */
		labelsRemoved: Option[List[Schema.HistoryLabelRemoved]] = None
	)
	
	case class HistoryMessageAdded(
		message: Option[Schema.Message] = None
	)
	
	case class HistoryMessageDeleted(
		message: Option[Schema.Message] = None
	)
	
	case class HistoryLabelAdded(
		message: Option[Schema.Message] = None,
	  /** Label IDs added to the message. */
		labelIds: Option[List[String]] = None
	)
	
	case class HistoryLabelRemoved(
		message: Option[Schema.Message] = None,
	  /** Label IDs removed from the message. */
		labelIds: Option[List[String]] = None
	)
	
	case class BatchDeleteMessagesRequest(
	  /** The IDs of the messages to delete. */
		ids: Option[List[String]] = None
	)
	
	case class ListMessagesResponse(
	  /** List of messages. Note that each message resource contains only an `id` and a `threadId`. Additional message details can be fetched using the messages.get method. */
		messages: Option[List[Schema.Message]] = None,
	  /** Token to retrieve the next page of results in the list. */
		nextPageToken: Option[String] = None,
	  /** Estimated total number of results. */
		resultSizeEstimate: Option[Int] = None
	)
	
	case class ModifyMessageRequest(
	  /** A list of IDs of labels to add to this message. You can add up to 100 labels with each update. */
		addLabelIds: Option[List[String]] = None,
	  /** A list IDs of labels to remove from this message. You can remove up to 100 labels with each update. */
		removeLabelIds: Option[List[String]] = None
	)
	
	case class BatchModifyMessagesRequest(
	  /** The IDs of the messages to modify. There is a limit of 1000 ids per request. */
		ids: Option[List[String]] = None,
	  /** A list of label IDs to add to messages. */
		addLabelIds: Option[List[String]] = None,
	  /** A list of label IDs to remove from messages. */
		removeLabelIds: Option[List[String]] = None
	)
	
	object Label {
		enum MessageListVisibilityEnum extends Enum[MessageListVisibilityEnum] { case show, hide }
		enum LabelListVisibilityEnum extends Enum[LabelListVisibilityEnum] { case labelShow, labelShowIfUnread, labelHide }
		enum TypeEnum extends Enum[TypeEnum] { case system, user }
	}
	case class Label(
	  /** The immutable ID of the label. */
		id: Option[String] = None,
	  /** The display name of the label. */
		name: Option[String] = None,
	  /** The visibility of messages with this label in the message list in the Gmail web interface. */
		messageListVisibility: Option[Schema.Label.MessageListVisibilityEnum] = None,
	  /** The visibility of the label in the label list in the Gmail web interface. */
		labelListVisibility: Option[Schema.Label.LabelListVisibilityEnum] = None,
	  /** The owner type for the label. User labels are created by the user and can be modified and deleted by the user and can be applied to any message or thread. System labels are internally created and cannot be added, modified, or deleted. System labels may be able to be applied to or removed from messages and threads under some circumstances but this is not guaranteed. For example, users can apply and remove the `INBOX` and `UNREAD` labels from messages and threads, but cannot apply or remove the `DRAFTS` or `SENT` labels from messages or threads. */
		`type`: Option[Schema.Label.TypeEnum] = None,
	  /** The total number of messages with the label. */
		messagesTotal: Option[Int] = None,
	  /** The number of unread messages with the label. */
		messagesUnread: Option[Int] = None,
	  /** The total number of threads with the label. */
		threadsTotal: Option[Int] = None,
	  /** The number of unread threads with the label. */
		threadsUnread: Option[Int] = None,
	  /** The color to assign to the label. Color is only available for labels that have their `type` set to `user`. */
		color: Option[Schema.LabelColor] = None
	)
	
	case class LabelColor(
	  /** The text color of the label, represented as hex string. This field is required in order to set the color of a label. Only the following predefined set of color values are allowed: \#000000, #434343, #666666, #999999, #cccccc, #efefef, #f3f3f3, #ffffff, \#fb4c2f, #ffad47, #fad165, #16a766, #43d692, #4a86e8, #a479e2, #f691b3, \#f6c5be, #ffe6c7, #fef1d1, #b9e4d0, #c6f3de, #c9daf8, #e4d7f5, #fcdee8, \#efa093, #ffd6a2, #fce8b3, #89d3b2, #a0eac9, #a4c2f4, #d0bcf1, #fbc8d9, \#e66550, #ffbc6b, #fcda83, #44b984, #68dfa9, #6d9eeb, #b694e8, #f7a7c0, \#cc3a21, #eaa041, #f2c960, #149e60, #3dc789, #3c78d8, #8e63ce, #e07798, \#ac2b16, #cf8933, #d5ae49, #0b804b, #2a9c68, #285bac, #653e9b, #b65775, \#822111, #a46a21, #aa8831, #076239, #1a764d, #1c4587, #41236d, #83334c \#464646, #e7e7e7, #0d3472, #b6cff5, #0d3b44, #98d7e4, #3d188e, #e3d7ff, \#711a36, #fbd3e0, #8a1c0a, #f2b2a8, #7a2e0b, #ffc8af, #7a4706, #ffdeb5, \#594c05, #fbe983, #684e07, #fdedc1, #0b4f30, #b3efd3, #04502e, #a2dcc1, \#c2c2c2, #4986e7, #2da2bb, #b99aff, #994a64, #f691b2, #ff7537, #ffad46, \#662e37, #ebdbde, #cca6ac, #094228, #42d692, #16a765 */
		textColor: Option[String] = None,
	  /** The background color represented as hex string #RRGGBB (ex #000000). This field is required in order to set the color of a label. Only the following predefined set of color values are allowed: \#000000, #434343, #666666, #999999, #cccccc, #efefef, #f3f3f3, #ffffff, \#fb4c2f, #ffad47, #fad165, #16a766, #43d692, #4a86e8, #a479e2, #f691b3, \#f6c5be, #ffe6c7, #fef1d1, #b9e4d0, #c6f3de, #c9daf8, #e4d7f5, #fcdee8, \#efa093, #ffd6a2, #fce8b3, #89d3b2, #a0eac9, #a4c2f4, #d0bcf1, #fbc8d9, \#e66550, #ffbc6b, #fcda83, #44b984, #68dfa9, #6d9eeb, #b694e8, #f7a7c0, \#cc3a21, #eaa041, #f2c960, #149e60, #3dc789, #3c78d8, #8e63ce, #e07798, \#ac2b16, #cf8933, #d5ae49, #0b804b, #2a9c68, #285bac, #653e9b, #b65775, \#822111, #a46a21, #aa8831, #076239, #1a764d, #1c4587, #41236d, #83334c \#464646, #e7e7e7, #0d3472, #b6cff5, #0d3b44, #98d7e4, #3d188e, #e3d7ff, \#711a36, #fbd3e0, #8a1c0a, #f2b2a8, #7a2e0b, #ffc8af, #7a4706, #ffdeb5, \#594c05, #fbe983, #684e07, #fdedc1, #0b4f30, #b3efd3, #04502e, #a2dcc1, \#c2c2c2, #4986e7, #2da2bb, #b99aff, #994a64, #f691b2, #ff7537, #ffad46, \#662e37, #ebdbde, #cca6ac, #094228, #42d692, #16a765 */
		backgroundColor: Option[String] = None
	)
	
	case class ListLabelsResponse(
	  /** List of labels. Note that each label resource only contains an `id`, `name`, `messageListVisibility`, `labelListVisibility`, and `type`. The labels.get method can fetch additional label details. */
		labels: Option[List[Schema.Label]] = None
	)
	
	case class Profile(
	  /** The user's email address. */
		emailAddress: Option[String] = None,
	  /** The total number of messages in the mailbox. */
		messagesTotal: Option[Int] = None,
	  /** The total number of threads in the mailbox. */
		threadsTotal: Option[Int] = None,
	  /** The ID of the mailbox's current history record. */
		historyId: Option[String] = None
	)
	
	object WatchRequest {
		enum LabelFilterActionEnum extends Enum[LabelFilterActionEnum] { case include, exclude }
		enum LabelFilterBehaviorEnum extends Enum[LabelFilterBehaviorEnum] { case include, exclude }
	}
	case class WatchRequest(
	  /** List of label_ids to restrict notifications about. By default, if unspecified, all changes are pushed out. If specified then dictates which labels are required for a push notification to be generated. */
		labelIds: Option[List[String]] = None,
	  /** Filtering behavior of `labelIds list` specified. This field is deprecated because it caused incorrect behavior in some cases; use `label_filter_behavior` instead. */
		labelFilterAction: Option[Schema.WatchRequest.LabelFilterActionEnum] = None,
	  /** Filtering behavior of `labelIds list` specified. This field replaces `label_filter_action`; if set, `label_filter_action` is ignored. */
		labelFilterBehavior: Option[Schema.WatchRequest.LabelFilterBehaviorEnum] = None,
	  /** A fully qualified Google Cloud Pub/Sub API topic name to publish the events to. This topic name &#42;&#42;must&#42;&#42; already exist in Cloud Pub/Sub and you &#42;&#42;must&#42;&#42; have already granted gmail "publish" permission on it. For example, "projects/my-project-identifier/topics/my-topic-name" (using the Cloud Pub/Sub "v1" topic naming format). Note that the "my-project-identifier" portion must exactly match your Google developer project id (the one executing this watch request). */
		topicName: Option[String] = None
	)
	
	case class WatchResponse(
	  /** The ID of the mailbox's current history record. */
		historyId: Option[String] = None,
	  /** When Gmail will stop sending notifications for mailbox updates (epoch millis). Call `watch` again before this time to renew the watch. */
		expiration: Option[String] = None
	)
	
	case class Thread(
	  /** The unique ID of the thread. */
		id: Option[String] = None,
	  /** A short part of the message text. */
		snippet: Option[String] = None,
	  /** The ID of the last history record that modified this thread. */
		historyId: Option[String] = None,
	  /** The list of messages in the thread. */
		messages: Option[List[Schema.Message]] = None
	)
	
	case class ListThreadsResponse(
	  /** List of threads. Note that each thread resource does not contain a list of `messages`. The list of `messages` for a given thread can be fetched using the threads.get method. */
		threads: Option[List[Schema.Thread]] = None,
	  /** Page token to retrieve the next page of results in the list. */
		nextPageToken: Option[String] = None,
	  /** Estimated total number of results. */
		resultSizeEstimate: Option[Int] = None
	)
	
	case class ModifyThreadRequest(
	  /** A list of IDs of labels to add to this thread. You can add up to 100 labels with each update. */
		addLabelIds: Option[List[String]] = None,
	  /** A list of IDs of labels to remove from this thread. You can remove up to 100 labels with each update. */
		removeLabelIds: Option[List[String]] = None
	)
	
	case class ListSendAsResponse(
	  /** List of send-as aliases. */
		sendAs: Option[List[Schema.SendAs]] = None
	)
	
	object SendAs {
		enum VerificationStatusEnum extends Enum[VerificationStatusEnum] { case verificationStatusUnspecified, accepted, pending }
	}
	case class SendAs(
	  /** The email address that appears in the "From:" header for mail sent using this alias. This is read-only for all operations except create. */
		sendAsEmail: Option[String] = None,
	  /** A name that appears in the "From:" header for mail sent using this alias. For custom "from" addresses, when this is empty, Gmail will populate the "From:" header with the name that is used for the primary address associated with the account. If the admin has disabled the ability for users to update their name format, requests to update this field for the primary login will silently fail. */
		displayName: Option[String] = None,
	  /** An optional email address that is included in a "Reply-To:" header for mail sent using this alias. If this is empty, Gmail will not generate a "Reply-To:" header. */
		replyToAddress: Option[String] = None,
	  /** An optional HTML signature that is included in messages composed with this alias in the Gmail web UI. This signature is added to new emails only. */
		signature: Option[String] = None,
	  /** Whether this address is the primary address used to login to the account. Every Gmail account has exactly one primary address, and it cannot be deleted from the collection of send-as aliases. This field is read-only. */
		isPrimary: Option[Boolean] = None,
	  /** Whether this address is selected as the default "From:" address in situations such as composing a new message or sending a vacation auto-reply. Every Gmail account has exactly one default send-as address, so the only legal value that clients may write to this field is `true`. Changing this from `false` to `true` for an address will result in this field becoming `false` for the other previous default address. */
		isDefault: Option[Boolean] = None,
	  /** Whether Gmail should treat this address as an alias for the user's primary email address. This setting only applies to custom "from" aliases. */
		treatAsAlias: Option[Boolean] = None,
	  /** An optional SMTP service that will be used as an outbound relay for mail sent using this alias. If this is empty, outbound mail will be sent directly from Gmail's servers to the destination SMTP service. This setting only applies to custom "from" aliases. */
		smtpMsa: Option[Schema.SmtpMsa] = None,
	  /** Indicates whether this address has been verified for use as a send-as alias. Read-only. This setting only applies to custom "from" aliases. */
		verificationStatus: Option[Schema.SendAs.VerificationStatusEnum] = None
	)
	
	object SmtpMsa {
		enum SecurityModeEnum extends Enum[SecurityModeEnum] { case securityModeUnspecified, none, ssl, starttls }
	}
	case class SmtpMsa(
	  /** The hostname of the SMTP service. Required. */
		host: Option[String] = None,
	  /** The port of the SMTP service. Required. */
		port: Option[Int] = None,
	  /** The username that will be used for authentication with the SMTP service. This is a write-only field that can be specified in requests to create or update SendAs settings; it is never populated in responses. */
		username: Option[String] = None,
	  /** The password that will be used for authentication with the SMTP service. This is a write-only field that can be specified in requests to create or update SendAs settings; it is never populated in responses. */
		password: Option[String] = None,
	  /** The protocol that will be used to secure communication with the SMTP service. Required. */
		securityMode: Option[Schema.SmtpMsa.SecurityModeEnum] = None
	)
	
	case class ListSmimeInfoResponse(
	  /** List of SmimeInfo. */
		smimeInfo: Option[List[Schema.SmimeInfo]] = None
	)
	
	case class SmimeInfo(
	  /** The immutable ID for the SmimeInfo. */
		id: Option[String] = None,
	  /** The S/MIME certificate issuer's common name. */
		issuerCn: Option[String] = None,
	  /** Whether this SmimeInfo is the default one for this user's send-as address. */
		isDefault: Option[Boolean] = None,
	  /** When the certificate expires (in milliseconds since epoch). */
		expiration: Option[String] = None,
	  /** PEM formatted X509 concatenated certificate string (standard base64 encoding). Format used for returning key, which includes public key as well as certificate chain (not private key). */
		pem: Option[String] = None,
	  /** PKCS#12 format containing a single private/public key pair and certificate chain. This format is only accepted from client for creating a new SmimeInfo and is never returned, because the private key is not intended to be exported. PKCS#12 may be encrypted, in which case encryptedKeyPassword should be set appropriately. */
		pkcs12: Option[String] = None,
	  /** Encrypted key password, when key is encrypted. */
		encryptedKeyPassword: Option[String] = None
	)
	
	case class CseIdentity(
	  /** The email address for the sending identity. The email address must be the primary email address of the authenticated user. */
		emailAddress: Option[String] = None,
	  /** If a key pair is associated, the ID of the key pair, CseKeyPair. */
		primaryKeyPairId: Option[String] = None,
	  /** The configuration of a CSE identity that uses different key pairs for signing and encryption. */
		signAndEncryptKeyPairs: Option[Schema.SignAndEncryptKeyPairs] = None
	)
	
	case class SignAndEncryptKeyPairs(
	  /** The ID of the CseKeyPair that signs outgoing mail. */
		signingKeyPairId: Option[String] = None,
	  /** The ID of the CseKeyPair that encrypts signed outgoing mail. */
		encryptionKeyPairId: Option[String] = None
	)
	
	object CseKeyPair {
		enum EnablementStateEnum extends Enum[EnablementStateEnum] { case stateUnspecified, enabled, disabled }
	}
	case class CseKeyPair(
	  /** Output only. The immutable ID for the client-side encryption S/MIME key pair. */
		keyPairId: Option[String] = None,
	  /** Input only. The public key and its certificate chain. The chain must be in [PKCS#7](https://en.wikipedia.org/wiki/PKCS_7) format and use PEM encoding and ASCII armor. */
		pkcs7: Option[String] = None,
	  /** Output only. The public key and its certificate chain, in [PEM](https://en.wikipedia.org/wiki/Privacy-Enhanced_Mail) format. */
		pem: Option[String] = None,
	  /** Output only. The email address identities that are specified on the leaf certificate. */
		subjectEmailAddresses: Option[List[String]] = None,
	  /** Output only. The current state of the key pair. */
		enablementState: Option[Schema.CseKeyPair.EnablementStateEnum] = None,
	  /** Output only. If a key pair is set to `DISABLED`, the time that the key pair's state changed from `ENABLED` to `DISABLED`. This field is present only when the key pair is in state `DISABLED`. */
		disableTime: Option[String] = None,
	  /** Metadata for instances of this key pair's private key. */
		privateKeyMetadata: Option[List[Schema.CsePrivateKeyMetadata]] = None
	)
	
	case class CsePrivateKeyMetadata(
	  /** Output only. The immutable ID for the private key metadata instance. */
		privateKeyMetadataId: Option[String] = None,
	  /** Metadata for a private key instance managed by an external key access control list service. */
		kaclsKeyMetadata: Option[Schema.KaclsKeyMetadata] = None,
	  /** Metadata for hardware keys. */
		hardwareKeyMetadata: Option[Schema.HardwareKeyMetadata] = None
	)
	
	case class KaclsKeyMetadata(
	  /** The URI of the key access control list service that manages the private key. */
		kaclsUri: Option[String] = None,
	  /** Opaque data generated and used by the key access control list service. Maximum size: 8 KiB. */
		kaclsData: Option[String] = None
	)
	
	case class HardwareKeyMetadata(
	  /** Description about the hardware key. */
		description: Option[String] = None
	)
	
	case class DisableCseKeyPairRequest(
	
	)
	
	case class EnableCseKeyPairRequest(
	
	)
	
	case class ListCseIdentitiesResponse(
	  /** One page of the list of CSE identities configured for the user. */
		cseIdentities: Option[List[Schema.CseIdentity]] = None,
	  /** Pagination token to be passed to a subsequent ListCseIdentities call in order to retrieve the next page of identities. If this value is not returned or is the empty string, then no further pages remain. */
		nextPageToken: Option[String] = None
	)
	
	case class ListCseKeyPairsResponse(
	  /** One page of the list of CSE key pairs installed for the user. */
		cseKeyPairs: Option[List[Schema.CseKeyPair]] = None,
	  /** Pagination token to be passed to a subsequent ListCseKeyPairs call in order to retrieve the next page of key pairs. If this value is not returned, then no further pages remain. */
		nextPageToken: Option[String] = None
	)
	
	case class ObliterateCseKeyPairRequest(
	
	)
	
	case class ListFiltersResponse(
	  /** List of a user's filters. */
		filter: Option[List[Schema.Filter]] = None
	)
	
	case class Filter(
	  /** The server assigned ID of the filter. */
		id: Option[String] = None,
	  /** Matching criteria for the filter. */
		criteria: Option[Schema.FilterCriteria] = None,
	  /** Action that the filter performs. */
		action: Option[Schema.FilterAction] = None
	)
	
	object FilterCriteria {
		enum SizeComparisonEnum extends Enum[SizeComparisonEnum] { case unspecified, smaller, larger }
	}
	case class FilterCriteria(
	  /** The sender's display name or email address. */
		from: Option[String] = None,
	  /** The recipient's display name or email address. Includes recipients in the "to", "cc", and "bcc" header fields. You can use simply the local part of the email address. For example, "example" and "example@" both match "example@gmail.com". This field is case-insensitive. */
		to: Option[String] = None,
	  /** Case-insensitive phrase found in the message's subject. Trailing and leading whitespace are be trimmed and adjacent spaces are collapsed. */
		subject: Option[String] = None,
	  /** Only return messages matching the specified query. Supports the same query format as the Gmail search box. For example, `"from:someuser@example.com rfc822msgid: is:unread"`. */
		query: Option[String] = None,
	  /** Only return messages not matching the specified query. Supports the same query format as the Gmail search box. For example, `"from:someuser@example.com rfc822msgid: is:unread"`. */
		negatedQuery: Option[String] = None,
	  /** Whether the message has any attachment. */
		hasAttachment: Option[Boolean] = None,
	  /** Whether the response should exclude chats. */
		excludeChats: Option[Boolean] = None,
	  /** The size of the entire RFC822 message in bytes, including all headers and attachments. */
		size: Option[Int] = None,
	  /** How the message size in bytes should be in relation to the size field. */
		sizeComparison: Option[Schema.FilterCriteria.SizeComparisonEnum] = None
	)
	
	case class FilterAction(
	  /** List of labels to add to the message. */
		addLabelIds: Option[List[String]] = None,
	  /** List of labels to remove from the message. */
		removeLabelIds: Option[List[String]] = None,
	  /** Email address that the message should be forwarded to. */
		forward: Option[String] = None
	)
	
	object ImapSettings {
		enum ExpungeBehaviorEnum extends Enum[ExpungeBehaviorEnum] { case expungeBehaviorUnspecified, archive, trash, deleteForever }
	}
	case class ImapSettings(
	  /** Whether IMAP is enabled for the account. */
		enabled: Option[Boolean] = None,
	  /** If this value is true, Gmail will immediately expunge a message when it is marked as deleted in IMAP. Otherwise, Gmail will wait for an update from the client before expunging messages marked as deleted. */
		autoExpunge: Option[Boolean] = None,
	  /** The action that will be executed on a message when it is marked as deleted and expunged from the last visible IMAP folder. */
		expungeBehavior: Option[Schema.ImapSettings.ExpungeBehaviorEnum] = None,
	  /** An optional limit on the number of messages that an IMAP folder may contain. Legal values are 0, 1000, 2000, 5000 or 10000. A value of zero is interpreted to mean that there is no limit. */
		maxFolderSize: Option[Int] = None
	)
	
	object PopSettings {
		enum AccessWindowEnum extends Enum[AccessWindowEnum] { case accessWindowUnspecified, disabled, fromNowOn, allMail }
		enum DispositionEnum extends Enum[DispositionEnum] { case dispositionUnspecified, leaveInInbox, archive, trash, markRead }
	}
	case class PopSettings(
	  /** The range of messages which are accessible via POP. */
		accessWindow: Option[Schema.PopSettings.AccessWindowEnum] = None,
	  /** The action that will be executed on a message after it has been fetched via POP. */
		disposition: Option[Schema.PopSettings.DispositionEnum] = None
	)
	
	case class VacationSettings(
	  /** Flag that controls whether Gmail automatically replies to messages. */
		enableAutoReply: Option[Boolean] = None,
	  /** Optional text to prepend to the subject line in vacation responses. In order to enable auto-replies, either the response subject or the response body must be nonempty. */
		responseSubject: Option[String] = None,
	  /** Response body in plain text format. If both `response_body_plain_text` and `response_body_html` are specified, `response_body_html` will be used. */
		responseBodyPlainText: Option[String] = None,
	  /** Response body in HTML format. Gmail will sanitize the HTML before storing it. If both `response_body_plain_text` and `response_body_html` are specified, `response_body_html` will be used. */
		responseBodyHtml: Option[String] = None,
	  /** Flag that determines whether responses are sent to recipients who are not in the user's list of contacts. */
		restrictToContacts: Option[Boolean] = None,
	  /** Flag that determines whether responses are sent to recipients who are outside of the user's domain. This feature is only available for Google Workspace users. */
		restrictToDomain: Option[Boolean] = None,
	  /** An optional start time for sending auto-replies (epoch ms). When this is specified, Gmail will automatically reply only to messages that it receives after the start time. If both `startTime` and `endTime` are specified, `startTime` must precede `endTime`. */
		startTime: Option[String] = None,
	  /** An optional end time for sending auto-replies (epoch ms). When this is specified, Gmail will automatically reply only to messages that it receives before the end time. If both `startTime` and `endTime` are specified, `startTime` must precede `endTime`. */
		endTime: Option[String] = None
	)
	
	case class LanguageSettings(
	  /** The language to display Gmail in, formatted as an RFC 3066 Language Tag (for example `en-GB`, `fr` or `ja` for British English, French, or Japanese respectively). The set of languages supported by Gmail evolves over time, so please refer to the "Language" dropdown in the Gmail settings for all available options, as described in the language settings help article. A table of sample values is also provided in the Managing Language Settings guide Not all Gmail clients can display the same set of languages. In the case that a user's display language is not available for use on a particular client, said client automatically chooses to display in the closest supported variant (or a reasonable default). */
		displayLanguage: Option[String] = None
	)
	
	case class ListForwardingAddressesResponse(
	  /** List of addresses that may be used for forwarding. */
		forwardingAddresses: Option[List[Schema.ForwardingAddress]] = None
	)
	
	object ForwardingAddress {
		enum VerificationStatusEnum extends Enum[VerificationStatusEnum] { case verificationStatusUnspecified, accepted, pending }
	}
	case class ForwardingAddress(
	  /** An email address to which messages can be forwarded. */
		forwardingEmail: Option[String] = None,
	  /** Indicates whether this address has been verified and is usable for forwarding. Read-only. */
		verificationStatus: Option[Schema.ForwardingAddress.VerificationStatusEnum] = None
	)
	
	object AutoForwarding {
		enum DispositionEnum extends Enum[DispositionEnum] { case dispositionUnspecified, leaveInInbox, archive, trash, markRead }
	}
	case class AutoForwarding(
	  /** Whether all incoming mail is automatically forwarded to another address. */
		enabled: Option[Boolean] = None,
	  /** Email address to which all incoming messages are forwarded. This email address must be a verified member of the forwarding addresses. */
		emailAddress: Option[String] = None,
	  /** The state that a message should be left in after it has been forwarded. */
		disposition: Option[Schema.AutoForwarding.DispositionEnum] = None
	)
	
	case class ListDelegatesResponse(
	  /** List of the user's delegates (with any verification status). If an account doesn't have delegates, this field doesn't appear. */
		delegates: Option[List[Schema.Delegate]] = None
	)
	
	object Delegate {
		enum VerificationStatusEnum extends Enum[VerificationStatusEnum] { case verificationStatusUnspecified, accepted, pending, rejected, expired }
	}
	case class Delegate(
	  /** The email address of the delegate. */
		delegateEmail: Option[String] = None,
	  /** Indicates whether this address has been verified and can act as a delegate for the account. Read-only. */
		verificationStatus: Option[Schema.Delegate.VerificationStatusEnum] = None
	)
}
