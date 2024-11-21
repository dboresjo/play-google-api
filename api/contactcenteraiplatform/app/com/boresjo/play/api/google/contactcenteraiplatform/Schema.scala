package com.boresjo.play.api.google.contactcenteraiplatform

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	case class ListOperationsResponse(
	  /** A list of operations that matches the specified filter in the request. */
		operations: Option[List[Schema.Operation]] = None,
	  /** The standard List next-page token. */
		nextPageToken: Option[String] = None
	)
	
	case class Operation(
	  /** The server-assigned name, which is only unique within the same service that originally returns it. If you use the default HTTP mapping, the `name` should be a resource name ending with `operations/{unique_id}`. */
		name: Option[String] = None,
	  /** Service-specific metadata associated with the operation. It typically contains progress information and common metadata such as create time. Some services might not provide such metadata. Any method that returns a long-running operation should document the metadata type, if any. */
		metadata: Option[Map[String, JsValue]] = None,
	  /** If the value is `false`, it means the operation is still in progress. If `true`, the operation is completed, and either `error` or `response` is available. */
		done: Option[Boolean] = None,
	  /** The error result of the operation in case of failure or cancellation. */
		error: Option[Schema.Status] = None,
	  /** The normal, successful response of the operation. If the original method returns no data on success, such as `Delete`, the response is `google.protobuf.Empty`. If the original method is standard `Get`/`Create`/`Update`, the response should be the resource. For other methods, the response should have the type `XxxResponse`, where `Xxx` is the original method name. For example, if the original method name is `TakeSnapshot()`, the inferred response type is `TakeSnapshotResponse`. */
		response: Option[Map[String, JsValue]] = None
	)
	
	case class Status(
	  /** The status code, which should be an enum value of google.rpc.Code. */
		code: Option[Int] = None,
	  /** A developer-facing error message, which should be in English. Any user-facing error message should be localized and sent in the google.rpc.Status.details field, or localized by the client. */
		message: Option[String] = None,
	  /** A list of messages that carry the error details. There is a common set of message types for APIs to use. */
		details: Option[List[Map[String, JsValue]]] = None
	)
	
	case class Empty(
	
	)
	
	case class CancelOperationRequest(
	
	)
	
	case class ListContactCentersResponse(
	  /** The list of ContactCenter */
		contactCenters: Option[List[Schema.ContactCenter]] = None,
	  /** A token identifying a page of results the server should return. */
		nextPageToken: Option[String] = None,
	  /** Locations that could not be reached. */
		unreachable: Option[List[String]] = None
	)
	
	object ContactCenter {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, STATE_DEPLOYING, STATE_DEPLOYED, STATE_TERMINATING, STATE_FAILED, STATE_TERMINATING_FAILED, STATE_TERMINATED, STATE_IN_GRACE_PERIOD, STATE_FAILING_OVER, STATE_DEGRADED, STATE_REPAIRING }
	}
	case class ContactCenter(
	  /** name of resource */
		name: Option[String] = None,
	  /** Output only. [Output only] Create time stamp */
		createTime: Option[String] = None,
	  /** Output only. [Output only] Update time stamp */
		updateTime: Option[String] = None,
	  /** Labels as key value pairs */
		labels: Option[Map[String, String]] = None,
	  /** Output only. URIs to access the deployed ContactCenters. */
		uris: Option[Schema.URIs] = None,
	  /** Output only. The state of this contact center. */
		state: Option[Schema.ContactCenter.StateEnum] = None,
	  /** Required. Immutable. At least 2 and max 16 char long, must conform to [RFC 1035](https://www.ietf.org/rfc/rfc1035.txt). */
		customerDomainPrefix: Option[String] = None,
	  /** Required. A user friendly name for the ContactCenter. */
		displayName: Option[String] = None,
	  /** The configuration of this instance, it is currently immutable once created. */
		instanceConfig: Option[Schema.InstanceConfig] = None,
	  /** Optional. Params that sets up Google as IdP. */
		samlParams: Option[Schema.SAMLParams] = None,
	  /** Optional. Email address of the first admin user. */
		userEmail: Option[String] = None,
	  /** Optional. Whether to enable users to be created in the CCAIP-instance concurrently to having users in Cloud identity */
		ccaipManagedUsers: Option[Boolean] = None,
	  /** Optional. Info about the first admin user, such as given name and family name. */
		adminUser: Option[Schema.AdminUser] = None,
	  /** Immutable. The KMS key name to encrypt the user input (`ContactCenter`). */
		kmsKey: Option[String] = None,
	  /** Output only. TODO(b/283407860) Deprecate this field. */
		privateComponents: Option[List[String]] = None,
	  /** Optional. VPC-SC related networking configuration. */
		privateAccess: Option[Schema.PrivateAccess] = None,
	  /** Optional. Early release channel. */
		early: Option[Schema.Early] = None,
	  /** Optional. Normal release channel. */
		normal: Option[Schema.Normal] = None,
	  /** Optional. Critical release channel. */
		critical: Option[Schema.Critical] = None
	)
	
	case class URIs(
	  /** Root Uri of the ContactCenter. */
		rootUri: Option[String] = None,
	  /** Media Uri of the ContactCenter. */
		mediaUri: Option[String] = None,
	  /** Virtual Agent Streaming Service Uri of the ContactCenter. */
		virtualAgentStreamingServiceUri: Option[String] = None,
	  /** Chat Bot Uri of the ContactCenter */
		chatBotUri: Option[String] = None
	)
	
	object InstanceConfig {
		enum InstanceSizeEnum extends Enum[InstanceSizeEnum] { case INSTANCE_SIZE_UNSPECIFIED, STANDARD_SMALL, STANDARD_MEDIUM, STANDARD_LARGE, STANDARD_XLARGE, STANDARD_2XLARGE, STANDARD_3XLARGE, DEV_XSMALL, MULTIREGION_SMALL, MULTIREGION_MEDIUM, MULTIREGION_LARGE, MULTIREGION_XLARGE, MULTIREGION_2XLARGE, MULTIREGION_3XLARGE, DEV_SMALL, SANDBOX_SMALL, TRIAL_SMALL, TIME_LIMITED_TRIAL_SMALL }
	}
	case class InstanceConfig(
	  /** The instance size of this the instance configuration. */
		instanceSize: Option[Schema.InstanceConfig.InstanceSizeEnum] = None
	)
	
	object SAMLParams {
		enum AuthenticationContextsEnum extends Enum[AuthenticationContextsEnum] { case AUTHENTICATION_CONTEXT_UNSPECIFIED, INTERNET_PROTOCOL, INTERNET_PROTOCOL_PASSWORD, KERBEROS, MOBILE_ONE_FACTOR_UNREGISTERED, MOBILE_TWO_FACTOR_UNREGISTERED, MOBILE_ONE_FACTOR_CONTRACT, MOBILE_TWO_FACTOR_CONTRACT, PASSWORD, PASSWORD_PROTECTED_TRANSPORT, PREVIOUS_SESSION, PUBLIC_KEY_X509, PUBLIC_KEY_PGP, PUBLIC_KEY_SPKI, PUBLIC_KEY_XML_DIGITAL_SIGNATURE, SMARTCARD, SMARTCARD_PKI, SOFTWARE_PKI, TELEPHONY, TELEPHONY_NOMADIC, TELEPHONY_PERSONALIZED, TELEPHONY_AUTHENTICATED, SECURE_REMOTE_PASSWORD, SSL_TLS_CERTIFICATE_BASED, TIME_SYNC_TOKEN }
	}
	case class SAMLParams(
	  /** Single sign-on URL */
		ssoUri: Option[String] = None,
	  /** Entity id URL */
		entityId: Option[String] = None,
	  /** SAML certificate */
		certificate: Option[String] = None,
	  /** Email address of the first admin users. */
		userEmail: Option[String] = None,
	  /** IdP field that maps to the user’s email address */
		emailMapping: Option[String] = None,
	  /** Additional contexts used for authentication. */
		authenticationContexts: Option[List[Schema.SAMLParams.AuthenticationContextsEnum]] = None
	)
	
	case class AdminUser(
	  /** Optional. First/given name of the first admin user. */
		givenName: Option[String] = None,
	  /** Optional. Last/family name of the first admin user. */
		familyName: Option[String] = None
	)
	
	case class PrivateAccess(
	  /** List of ingress components that should not be accessed via the Internet. For more information see go/ccaip-private-path-v2. */
		ingressSettings: Option[List[Schema.Component]] = None,
	  /** List of egress components that should not be accessed via the Internet. For more information see go/ccaip-private-path-v2. */
		egressSettings: Option[List[Schema.Component]] = None,
	  /** Private service connect settings. */
		pscSetting: Option[Schema.PscSetting] = None
	)
	
	case class Component(
	  /** Name of the component. */
		name: Option[String] = None,
	  /** Associated service attachments. The service attachment names that will be used for sending private traffic to the CCAIP tenant project. Example service attachment name: "projects/${TENANT_PROJECT_ID}/regions/${REGION}/serviceAttachments/ingress-default". */
		serviceAttachmentNames: Option[List[String]] = None
	)
	
	case class PscSetting(
	  /** The list of project ids that are allowed to send traffic to the service attachment. This field should be filled only for the ingress components. */
		allowedConsumerProjectIds: Option[List[String]] = None,
	  /** Output only. The CCAIP tenant project ids. */
		producerProjectIds: Option[List[String]] = None
	)
	
	case class Early(
	
	)
	
	case class Normal(
	
	)
	
	case class Critical(
	  /** Required. Hours during which the instance should not be updated. */
		peakHours: Option[List[Schema.WeeklySchedule]] = None
	)
	
	object WeeklySchedule {
		enum DaysEnum extends Enum[DaysEnum] { case DAY_OF_WEEK_UNSPECIFIED, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY }
	}
	case class WeeklySchedule(
	  /** Required. Days of the week this schedule applies to. */
		days: Option[List[Schema.WeeklySchedule.DaysEnum]] = None,
	  /** Required. Daily start time of the schedule. */
		startTime: Option[Schema.TimeOfDay] = None,
	  /** Optional. Daily end time of the schedule. If `end_time` is before `start_time`, the schedule will be considered as ending on the next day. */
		endTime: Option[Schema.TimeOfDay] = None,
	  /** Optional. Duration of the schedule. */
		duration: Option[String] = None
	)
	
	case class TimeOfDay(
	  /** Hours of a day in 24 hour format. Must be greater than or equal to 0 and typically must be less than or equal to 23. An API may choose to allow the value "24:00:00" for scenarios like business closing time. */
		hours: Option[Int] = None,
	  /** Minutes of an hour. Must be greater than or equal to 0 and less than or equal to 59. */
		minutes: Option[Int] = None,
	  /** Seconds of a minute. Must be greater than or equal to 0 and typically must be less than or equal to 59. An API may allow the value 60 if it allows leap-seconds. */
		seconds: Option[Int] = None,
	  /** Fractions of seconds, in nanoseconds. Must be greater than or equal to 0 and less than or equal to 999,999,999. */
		nanos: Option[Int] = None
	)
	
	case class ContactCenterQuota(
	  /** Deprecated: Use the Quota fields instead. Reflects the count limit of contact centers on a billing account. */
		contactCenterCountLimit: Option[Int] = None,
	  /** Deprecated: Use the Quota fields instead. Reflects the count sum of contact centers on a billing account. */
		contactCenterCountSum: Option[Int] = None,
	  /** Quota details per contact center instance type. */
		quotas: Option[List[Schema.Quota]] = None
	)
	
	object Quota {
		enum ContactCenterInstanceSizeEnum extends Enum[ContactCenterInstanceSizeEnum] { case INSTANCE_SIZE_UNSPECIFIED, STANDARD_SMALL, STANDARD_MEDIUM, STANDARD_LARGE, STANDARD_XLARGE, STANDARD_2XLARGE, STANDARD_3XLARGE, DEV_XSMALL, MULTIREGION_SMALL, MULTIREGION_MEDIUM, MULTIREGION_LARGE, MULTIREGION_XLARGE, MULTIREGION_2XLARGE, MULTIREGION_3XLARGE, DEV_SMALL, SANDBOX_SMALL, TRIAL_SMALL, TIME_LIMITED_TRIAL_SMALL }
	}
	case class Quota(
	  /** Reflects the count limit of contact centers on a billing account. */
		contactCenterCountLimit: Option[Int] = None,
	  /** Reflects the count sum of contact centers on a billing account. */
		contactCenterCountSum: Option[Int] = None,
	  /** Contact center instance type. */
		contactCenterInstanceSize: Option[Schema.Quota.ContactCenterInstanceSizeEnum] = None
	)
	
	case class ListLocationsResponse(
	  /** A list of locations that matches the specified filter in the request. */
		locations: Option[List[Schema.Location]] = None,
	  /** The standard List next-page token. */
		nextPageToken: Option[String] = None
	)
	
	case class Location(
	  /** Resource name for the location, which may vary between implementations. For example: `"projects/example-project/locations/us-east1"` */
		name: Option[String] = None,
	  /** The canonical id for this location. For example: `"us-east1"`. */
		locationId: Option[String] = None,
	  /** The friendly name for this location, typically a nearby city name. For example, "Tokyo". */
		displayName: Option[String] = None,
	  /** Cross-service attributes for the location. For example {"cloud.googleapis.com/region": "us-east1"} */
		labels: Option[Map[String, String]] = None,
	  /** Service-specific metadata. For example the available capacity at the given location. */
		metadata: Option[Map[String, JsValue]] = None
	)
	
	case class GoogleCloudCommonOperationMetadata(
	  /** Output only. The time the operation was created. */
		createTime: Option[String] = None,
	  /** Output only. The time the operation finished running. */
		endTime: Option[String] = None,
	  /** Output only. Server-defined resource path for the target of the operation. */
		target: Option[String] = None,
	  /** Output only. Name of the verb executed by the operation. */
		verb: Option[String] = None,
	  /** Output only. Human-readable status of the operation, if any. */
		statusDetail: Option[String] = None,
	  /** Output only. Identifies whether the user has requested cancellation of the operation. Operations that have been cancelled successfully have Operation.error value with a google.rpc.Status.code of 1, corresponding to `Code.CANCELLED`. */
		cancelRequested: Option[Boolean] = None,
	  /** Output only. API version used to start the operation. */
		apiVersion: Option[String] = None
	)
	
	case class OperationMetadata(
	  /** Output only. The time the operation was created. */
		createTime: Option[String] = None,
	  /** Output only. The time the operation finished running. */
		endTime: Option[String] = None,
	  /** Output only. Server-defined resource path for the target of the operation. */
		target: Option[String] = None,
	  /** Output only. Name of the verb executed by the operation. */
		verb: Option[String] = None,
	  /** Output only. Human-readable status of the operation, if any. */
		statusMessage: Option[String] = None,
	  /** Output only. Identifies whether the user has requested cancellation of the operation. Operations that have been cancelled successfully have Operation.error value with a google.rpc.Status.code of 1, corresponding to `Code.CANCELLED`. */
		requestedCancellation: Option[Boolean] = None,
	  /** Output only. API version used to start the operation. */
		apiVersion: Option[String] = None,
	  /** Contact center information for this request */
		contactCenter: Option[Schema.ContactCenter] = None
	)
}
