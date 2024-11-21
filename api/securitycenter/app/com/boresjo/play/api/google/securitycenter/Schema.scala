package com.boresjo.play.api.google.securitycenter

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
	
	object BulkMuteFindingsRequest {
		enum MuteStateEnum extends Enum[MuteStateEnum] { case MUTE_STATE_UNSPECIFIED, MUTED, UNDEFINED }
	}
	case class BulkMuteFindingsRequest(
	  /** Expression that identifies findings that should be updated. The expression is a list of zero or more restrictions combined via logical operators `AND` and `OR`. Parentheses are supported, and `OR` has higher precedence than `AND`. Restrictions have the form ` ` and may have a `-` character in front of them to indicate negation. The fields map to those defined in the corresponding resource. The supported operators are: &#42; `=` for all value types. &#42; `>`, `<`, `>=`, `<=` for integer values. &#42; `:`, meaning substring matching, for strings. The supported value types are: &#42; string literals in quotes. &#42; integer literals without quotes. &#42; boolean literals `true` and `false` without quotes. */
		filter: Option[String] = None,
	  /** This can be a mute configuration name or any identifier for mute/unmute of findings based on the filter. */
		muteAnnotation: Option[String] = None,
	  /** Optional. All findings matching the given filter will have their mute state set to this value. The default value is `MUTED`. Setting this to `UNDEFINED` will clear the mute state on all matching findings. */
		muteState: Option[Schema.BulkMuteFindingsRequest.MuteStateEnum] = None
	)
	
	object GoogleCloudSecuritycenterV1SecurityHealthAnalyticsCustomModule {
		enum EnablementStateEnum extends Enum[EnablementStateEnum] { case ENABLEMENT_STATE_UNSPECIFIED, ENABLED, DISABLED, INHERITED }
		enum CloudProviderEnum extends Enum[CloudProviderEnum] { case CLOUD_PROVIDER_UNSPECIFIED, GOOGLE_CLOUD_PLATFORM, AMAZON_WEB_SERVICES, MICROSOFT_AZURE }
	}
	case class GoogleCloudSecuritycenterV1SecurityHealthAnalyticsCustomModule(
	  /** Immutable. The resource name of the custom module. Its format is "organizations/{organization}/securityHealthAnalyticsSettings/customModules/{customModule}", or "folders/{folder}/securityHealthAnalyticsSettings/customModules/{customModule}", or "projects/{project}/securityHealthAnalyticsSettings/customModules/{customModule}" The id {customModule} is server-generated and is not user settable. It will be a numeric id containing 1-20 digits. */
		name: Option[String] = None,
	  /** The display name of the Security Health Analytics custom module. This display name becomes the finding category for all findings that are returned by this custom module. The display name must be between 1 and 128 characters, start with a lowercase letter, and contain alphanumeric characters or underscores only. */
		displayName: Option[String] = None,
	  /** The enablement state of the custom module. */
		enablementState: Option[Schema.GoogleCloudSecuritycenterV1SecurityHealthAnalyticsCustomModule.EnablementStateEnum] = None,
	  /** Output only. The time at which the custom module was last updated. */
		updateTime: Option[String] = None,
	  /** Output only. The editor that last updated the custom module. */
		lastEditor: Option[String] = None,
	  /** Output only. If empty, indicates that the custom module was created in the organization, folder, or project in which you are viewing the custom module. Otherwise, `ancestor_module` specifies the organization or folder from which the custom module is inherited. */
		ancestorModule: Option[String] = None,
	  /** The user specified custom configuration for the module. */
		customConfig: Option[Schema.GoogleCloudSecuritycenterV1CustomConfig] = None,
	  /** The cloud provider of the custom module. */
		cloudProvider: Option[Schema.GoogleCloudSecuritycenterV1SecurityHealthAnalyticsCustomModule.CloudProviderEnum] = None
	)
	
	object GoogleCloudSecuritycenterV1CustomConfig {
		enum SeverityEnum extends Enum[SeverityEnum] { case SEVERITY_UNSPECIFIED, CRITICAL, HIGH, MEDIUM, LOW }
	}
	case class GoogleCloudSecuritycenterV1CustomConfig(
	  /** The CEL expression to evaluate to produce findings. When the expression evaluates to true against a resource, a finding is generated. */
		predicate: Option[Schema.Expr] = None,
	  /** Custom output properties. */
		customOutput: Option[Schema.GoogleCloudSecuritycenterV1CustomOutputSpec] = None,
	  /** The resource types that the custom module operates on. Each custom module can specify up to 5 resource types. */
		resourceSelector: Option[Schema.GoogleCloudSecuritycenterV1ResourceSelector] = None,
	  /** The severity to assign to findings generated by the module. */
		severity: Option[Schema.GoogleCloudSecuritycenterV1CustomConfig.SeverityEnum] = None,
	  /** Text that describes the vulnerability or misconfiguration that the custom module detects. This explanation is returned with each finding instance to help investigators understand the detected issue. The text must be enclosed in quotation marks. */
		description: Option[String] = None,
	  /** An explanation of the recommended steps that security teams can take to resolve the detected issue. This explanation is returned with each finding generated by this module in the `nextSteps` property of the finding JSON. */
		recommendation: Option[String] = None
	)
	
	case class Expr(
	  /** Textual representation of an expression in Common Expression Language syntax. */
		expression: Option[String] = None,
	  /** Optional. Title for the expression, i.e. a short string describing its purpose. This can be used e.g. in UIs which allow to enter the expression. */
		title: Option[String] = None,
	  /** Optional. Description of the expression. This is a longer text which describes the expression, e.g. when hovered over it in a UI. */
		description: Option[String] = None,
	  /** Optional. String indicating the location of the expression for error reporting, e.g. a file name and a position in the file. */
		location: Option[String] = None
	)
	
	case class GoogleCloudSecuritycenterV1CustomOutputSpec(
	  /** A list of custom output properties to add to the finding. */
		properties: Option[List[Schema.GoogleCloudSecuritycenterV1Property]] = None
	)
	
	case class GoogleCloudSecuritycenterV1Property(
	  /** Name of the property for the custom output. */
		name: Option[String] = None,
	  /** The CEL expression for the custom output. A resource property can be specified to return the value of the property or a text string enclosed in quotation marks. */
		valueExpression: Option[Schema.Expr] = None
	)
	
	case class GoogleCloudSecuritycenterV1ResourceSelector(
	  /** The resource types to run the detector on. */
		resourceTypes: Option[List[String]] = None
	)
	
	case class Source(
	  /** The relative resource name of this source. See: https://cloud.google.com/apis/design/resource_names#relative_resource_name Example: "organizations/{organization_id}/sources/{source_id}" */
		name: Option[String] = None,
	  /** The source's display name. A source's display name must be unique amongst its siblings, for example, two sources with the same parent can't share the same display name. The display name must have a length between 1 and 64 characters (inclusive). */
		displayName: Option[String] = None,
	  /** The description of the source (max of 1024 characters). Example: "Web Security Scanner is a web security scanner for common vulnerabilities in App Engine applications. It can automatically scan and detect four common vulnerabilities, including cross-site-scripting (XSS), Flash injection, mixed content (HTTP in HTTPS), and outdated or insecure libraries." */
		description: Option[String] = None,
	  /** The canonical name of the finding source. It's either "organizations/{organization_id}/sources/{source_id}", "folders/{folder_id}/sources/{source_id}", or "projects/{project_number}/sources/{source_id}", depending on the closest CRM ancestor of the resource associated with the finding. */
		canonicalName: Option[String] = None
	)
	
	object Finding {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, ACTIVE, INACTIVE }
		enum SeverityEnum extends Enum[SeverityEnum] { case SEVERITY_UNSPECIFIED, CRITICAL, HIGH, MEDIUM, LOW }
		enum MuteEnum extends Enum[MuteEnum] { case MUTE_UNSPECIFIED, MUTED, UNMUTED, UNDEFINED }
		enum FindingClassEnum extends Enum[FindingClassEnum] { case FINDING_CLASS_UNSPECIFIED, THREAT, VULNERABILITY, MISCONFIGURATION, OBSERVATION, SCC_ERROR, POSTURE_VIOLATION, TOXIC_COMBINATION, SENSITIVE_DATA_RISK }
	}
	case class Finding(
	  /** The [relative resource name](https://cloud.google.com/apis/design/resource_names#relative_resource_name) of the finding. Example: "organizations/{organization_id}/sources/{source_id}/findings/{finding_id}", "folders/{folder_id}/sources/{source_id}/findings/{finding_id}", "projects/{project_id}/sources/{source_id}/findings/{finding_id}". */
		name: Option[String] = None,
	  /** The relative resource name of the source the finding belongs to. See: https://cloud.google.com/apis/design/resource_names#relative_resource_name This field is immutable after creation time. For example: "organizations/{organization_id}/sources/{source_id}" */
		parent: Option[String] = None,
	  /** For findings on Google Cloud resources, the full resource name of the Google Cloud resource this finding is for. See: https://cloud.google.com/apis/design/resource_names#full_resource_name When the finding is for a non-Google Cloud resource, the resourceName can be a customer or partner defined string. This field is immutable after creation time. */
		resourceName: Option[String] = None,
	  /** The state of the finding. */
		state: Option[Schema.Finding.StateEnum] = None,
	  /** The additional taxonomy group within findings from a given source. This field is immutable after creation time. Example: "XSS_FLASH_INJECTION" */
		category: Option[String] = None,
	  /** The URI that, if available, points to a web page outside of Security Command Center where additional information about the finding can be found. This field is guaranteed to be either empty or a well formed URL. */
		externalUri: Option[String] = None,
	  /** Source specific properties. These properties are managed by the source that writes the finding. The key names in the source_properties map must be between 1 and 255 characters, and must start with a letter and contain alphanumeric characters or underscores only. */
		sourceProperties: Option[Map[String, JsValue]] = None,
	  /** Output only. User specified security marks. These marks are entirely managed by the user and come from the SecurityMarks resource that belongs to the finding. */
		securityMarks: Option[Schema.SecurityMarks] = None,
	  /** The time the finding was first detected. If an existing finding is updated, then this is the time the update occurred. For example, if the finding represents an open firewall, this property captures the time the detector believes the firewall became open. The accuracy is determined by the detector. If the finding is later resolved, then this time reflects when the finding was resolved. This must not be set to a value greater than the current timestamp. */
		eventTime: Option[String] = None,
	  /** The time at which the finding was created in Security Command Center. */
		createTime: Option[String] = None,
	  /** The severity of the finding. This field is managed by the source that writes the finding. */
		severity: Option[Schema.Finding.SeverityEnum] = None,
	  /** The canonical name of the finding. It's either "organizations/{organization_id}/sources/{source_id}/findings/{finding_id}", "folders/{folder_id}/sources/{source_id}/findings/{finding_id}" or "projects/{project_number}/sources/{source_id}/findings/{finding_id}", depending on the closest CRM ancestor of the resource associated with the finding. */
		canonicalName: Option[String] = None,
	  /** Indicates the mute state of a finding (either muted, unmuted or undefined). Unlike other attributes of a finding, a finding provider shouldn't set the value of mute. */
		mute: Option[Schema.Finding.MuteEnum] = None,
	  /** The class of the finding. */
		findingClass: Option[Schema.Finding.FindingClassEnum] = None,
	  /** Represents what's commonly known as an &#42;indicator of compromise&#42; (IoC) in computer forensics. This is an artifact observed on a network or in an operating system that, with high confidence, indicates a computer intrusion. For more information, see [Indicator of compromise](https://en.wikipedia.org/wiki/Indicator_of_compromise). */
		indicator: Option[Schema.Indicator] = None,
	  /** Represents vulnerability-specific fields like CVE and CVSS scores. CVE stands for Common Vulnerabilities and Exposures (https://cve.mitre.org/about/) */
		vulnerability: Option[Schema.Vulnerability] = None,
	  /** Output only. The most recent time this finding was muted or unmuted. */
		muteUpdateTime: Option[String] = None,
	  /** Output only. Third party SIEM/SOAR fields within SCC, contains external system information and external system finding fields. */
		externalSystems: Option[Map[String, Schema.GoogleCloudSecuritycenterV1ExternalSystem]] = None,
	  /** MITRE ATT&CK tactics and techniques related to this finding. See: https://attack.mitre.org */
		mitreAttack: Option[Schema.MitreAttack] = None,
	  /** Access details associated with the finding, such as more information on the caller, which method was accessed, and from where. */
		access: Option[Schema.Access] = None,
	  /** Contains information about the IP connection associated with the finding. */
		connections: Option[List[Schema.Connection]] = None,
	  /** Records additional information about the mute operation, for example, the [mute configuration](/security-command-center/docs/how-to-mute-findings) that muted the finding and the user who muted the finding. */
		muteInitiator: Option[String] = None,
	  /** Output only. The mute information regarding this finding. */
		muteInfo: Option[Schema.MuteInfo] = None,
	  /** Represents operating system processes associated with the Finding. */
		processes: Option[List[Schema.Process]] = None,
	  /** Output only. Map containing the points of contact for the given finding. The key represents the type of contact, while the value contains a list of all the contacts that pertain. Please refer to: https://cloud.google.com/resource-manager/docs/managing-notification-contacts#notification-categories { "security": { "contacts": [ { "email": "person1@company.com" }, { "email": "person2@company.com" } ] } } */
		contacts: Option[Map[String, Schema.ContactDetails]] = None,
	  /** Contains compliance information for security standards associated to the finding. */
		compliances: Option[List[Schema.Compliance]] = None,
	  /** Output only. The human readable display name of the finding source such as "Event Threat Detection" or "Security Health Analytics". */
		parentDisplayName: Option[String] = None,
	  /** Contains more details about the finding. */
		description: Option[String] = None,
	  /** Represents exfiltrations associated with the finding. */
		exfiltration: Option[Schema.Exfiltration] = None,
	  /** Represents IAM bindings associated with the finding. */
		iamBindings: Option[List[Schema.IamBinding]] = None,
	  /** Steps to address the finding. */
		nextSteps: Option[String] = None,
	  /** Unique identifier of the module which generated the finding. Example: folders/598186756061/securityHealthAnalyticsSettings/customModules/56799441161885 */
		moduleName: Option[String] = None,
	  /** Containers associated with the finding. This field provides information for both Kubernetes and non-Kubernetes containers. */
		containers: Option[List[Schema.Container]] = None,
	  /** Kubernetes resources associated with the finding. */
		kubernetes: Option[Schema.Kubernetes] = None,
	  /** Database associated with the finding. */
		database: Option[Schema.Database] = None,
	  /** The results of an attack path simulation relevant to this finding. */
		attackExposure: Option[Schema.AttackExposure] = None,
	  /** File associated with the finding. */
		files: Option[List[Schema.File]] = None,
	  /** Cloud Data Loss Prevention (Cloud DLP) inspection results that are associated with the finding. */
		cloudDlpInspection: Option[Schema.CloudDlpInspection] = None,
	  /** Cloud DLP data profile that is associated with the finding. */
		cloudDlpDataProfile: Option[Schema.CloudDlpDataProfile] = None,
	  /** Signature of the kernel rootkit. */
		kernelRootkit: Option[Schema.KernelRootkit] = None,
	  /** Contains information about the org policies associated with the finding. */
		orgPolicies: Option[List[Schema.OrgPolicy]] = None,
	  /** Represents an application associated with the finding. */
		application: Option[Schema.Application] = None,
	  /** Fields related to Backup and DR findings. */
		backupDisasterRecovery: Option[Schema.BackupDisasterRecovery] = None,
	  /** The security posture associated with the finding. */
		securityPosture: Option[Schema.SecurityPosture] = None,
	  /** Log entries that are relevant to the finding. */
		logEntries: Option[List[Schema.LogEntry]] = None,
	  /** The load balancers associated with the finding. */
		loadBalancers: Option[List[Schema.LoadBalancer]] = None,
	  /** Fields related to Cloud Armor findings. */
		cloudArmor: Option[Schema.CloudArmor] = None,
	  /** Notebook associated with the finding. */
		notebook: Option[Schema.Notebook] = None,
	  /** Contains details about a group of security issues that, when the issues occur together, represent a greater risk than when the issues occur independently. A group of such issues is referred to as a toxic combination. This field cannot be updated. Its value is ignored in all update requests. */
		toxicCombination: Option[Schema.ToxicCombination] = None,
	  /** Contains details about groups of which this finding is a member. A group is a collection of findings that are related in some way. This field cannot be updated. Its value is ignored in all update requests. */
		groupMemberships: Option[List[Schema.GroupMembership]] = None,
	  /** Disk associated with the finding. */
		disk: Option[Schema.Disk] = None,
	  /** Data access events associated with the finding. */
		dataAccessEvents: Option[List[Schema.DataAccessEvent]] = None,
	  /** Data flow events associated with the finding. */
		dataFlowEvents: Option[List[Schema.DataFlowEvent]] = None
	)
	
	case class SecurityMarks(
	  /** The relative resource name of the SecurityMarks. See: https://cloud.google.com/apis/design/resource_names#relative_resource_name Examples: "organizations/{organization_id}/assets/{asset_id}/securityMarks" "organizations/{organization_id}/sources/{source_id}/findings/{finding_id}/securityMarks". */
		name: Option[String] = None,
	  /** Mutable user specified security marks belonging to the parent resource. Constraints are as follows: &#42; Keys and values are treated as case insensitive &#42; Keys must be between 1 - 256 characters (inclusive) &#42; Keys must be letters, numbers, underscores, or dashes &#42; Values have leading and trailing whitespace trimmed, remaining characters must be between 1 - 4096 characters (inclusive) */
		marks: Option[Map[String, String]] = None,
	  /** The canonical name of the marks. Examples: "organizations/{organization_id}/assets/{asset_id}/securityMarks" "folders/{folder_id}/assets/{asset_id}/securityMarks" "projects/{project_number}/assets/{asset_id}/securityMarks" "organizations/{organization_id}/sources/{source_id}/findings/{finding_id}/securityMarks" "folders/{folder_id}/sources/{source_id}/findings/{finding_id}/securityMarks" "projects/{project_number}/sources/{source_id}/findings/{finding_id}/securityMarks" */
		canonicalName: Option[String] = None
	)
	
	case class Indicator(
	  /** The list of IP addresses that are associated with the finding. */
		ipAddresses: Option[List[String]] = None,
	  /** List of domains associated to the Finding. */
		domains: Option[List[String]] = None,
	  /** The list of matched signatures indicating that the given process is present in the environment. */
		signatures: Option[List[Schema.ProcessSignature]] = None,
	  /** The list of URIs associated to the Findings. */
		uris: Option[List[String]] = None
	)
	
	object ProcessSignature {
		enum SignatureTypeEnum extends Enum[SignatureTypeEnum] { case SIGNATURE_TYPE_UNSPECIFIED, SIGNATURE_TYPE_PROCESS, SIGNATURE_TYPE_FILE }
	}
	case class ProcessSignature(
	  /** Signature indicating that a binary family was matched. */
		memoryHashSignature: Option[Schema.MemoryHashSignature] = None,
	  /** Signature indicating that a YARA rule was matched. */
		yaraRuleSignature: Option[Schema.YaraRuleSignature] = None,
	  /** Describes the type of resource associated with the signature. */
		signatureType: Option[Schema.ProcessSignature.SignatureTypeEnum] = None
	)
	
	case class MemoryHashSignature(
	  /** The binary family. */
		binaryFamily: Option[String] = None,
	  /** The list of memory hash detections contributing to the binary family match. */
		detections: Option[List[Schema.Detection]] = None
	)
	
	case class Detection(
	  /** The name of the binary associated with the memory hash signature detection. */
		binary: Option[String] = None,
	  /** The percentage of memory page hashes in the signature that were matched. */
		percentPagesMatched: Option[BigDecimal] = None
	)
	
	case class YaraRuleSignature(
	  /** The name of the YARA rule. */
		yaraRule: Option[String] = None
	)
	
	case class Vulnerability(
	  /** CVE stands for Common Vulnerabilities and Exposures (https://cve.mitre.org/about/) */
		cve: Option[Schema.Cve] = None,
	  /** The offending package is relevant to the finding. */
		offendingPackage: Option[Schema.Package] = None,
	  /** The fixed package is relevant to the finding. */
		fixedPackage: Option[Schema.Package] = None,
	  /** The security bulletin is relevant to this finding. */
		securityBulletin: Option[Schema.SecurityBulletin] = None
	)
	
	object Cve {
		enum ImpactEnum extends Enum[ImpactEnum] { case RISK_RATING_UNSPECIFIED, LOW, MEDIUM, HIGH, CRITICAL }
		enum ExploitationActivityEnum extends Enum[ExploitationActivityEnum] { case EXPLOITATION_ACTIVITY_UNSPECIFIED, WIDE, CONFIRMED, AVAILABLE, ANTICIPATED, NO_KNOWN }
	}
	case class Cve(
	  /** The unique identifier for the vulnerability. e.g. CVE-2021-34527 */
		id: Option[String] = None,
	  /** Additional information about the CVE. e.g. https://cve.mitre.org/cgi-bin/cvename.cgi?name=CVE-2021-34527 */
		references: Option[List[Schema.Reference]] = None,
	  /** Describe Common Vulnerability Scoring System specified at https://www.first.org/cvss/v3.1/specification-document */
		cvssv3: Option[Schema.Cvssv3] = None,
	  /** Whether upstream fix is available for the CVE. */
		upstreamFixAvailable: Option[Boolean] = None,
	  /** The potential impact of the vulnerability if it was to be exploited. */
		impact: Option[Schema.Cve.ImpactEnum] = None,
	  /** The exploitation activity of the vulnerability in the wild. */
		exploitationActivity: Option[Schema.Cve.ExploitationActivityEnum] = None,
	  /** Whether or not the vulnerability has been observed in the wild. */
		observedInTheWild: Option[Boolean] = None,
	  /** Whether or not the vulnerability was zero day when the finding was published. */
		zeroDay: Option[Boolean] = None,
	  /** Date the first publicly available exploit or PoC was released. */
		exploitReleaseDate: Option[String] = None,
	  /** Date of the earliest known exploitation. */
		firstExploitationDate: Option[String] = None
	)
	
	case class Reference(
	  /** Source of the reference e.g. NVD */
		source: Option[String] = None,
	  /** Uri for the mentioned source e.g. https://cve.mitre.org/cgi-bin/cvename.cgi?name=CVE-2021-34527. */
		uri: Option[String] = None
	)
	
	object Cvssv3 {
		enum AttackVectorEnum extends Enum[AttackVectorEnum] { case ATTACK_VECTOR_UNSPECIFIED, ATTACK_VECTOR_NETWORK, ATTACK_VECTOR_ADJACENT, ATTACK_VECTOR_LOCAL, ATTACK_VECTOR_PHYSICAL }
		enum AttackComplexityEnum extends Enum[AttackComplexityEnum] { case ATTACK_COMPLEXITY_UNSPECIFIED, ATTACK_COMPLEXITY_LOW, ATTACK_COMPLEXITY_HIGH }
		enum PrivilegesRequiredEnum extends Enum[PrivilegesRequiredEnum] { case PRIVILEGES_REQUIRED_UNSPECIFIED, PRIVILEGES_REQUIRED_NONE, PRIVILEGES_REQUIRED_LOW, PRIVILEGES_REQUIRED_HIGH }
		enum UserInteractionEnum extends Enum[UserInteractionEnum] { case USER_INTERACTION_UNSPECIFIED, USER_INTERACTION_NONE, USER_INTERACTION_REQUIRED }
		enum ScopeEnum extends Enum[ScopeEnum] { case SCOPE_UNSPECIFIED, SCOPE_UNCHANGED, SCOPE_CHANGED }
		enum ConfidentialityImpactEnum extends Enum[ConfidentialityImpactEnum] { case IMPACT_UNSPECIFIED, IMPACT_HIGH, IMPACT_LOW, IMPACT_NONE }
		enum IntegrityImpactEnum extends Enum[IntegrityImpactEnum] { case IMPACT_UNSPECIFIED, IMPACT_HIGH, IMPACT_LOW, IMPACT_NONE }
		enum AvailabilityImpactEnum extends Enum[AvailabilityImpactEnum] { case IMPACT_UNSPECIFIED, IMPACT_HIGH, IMPACT_LOW, IMPACT_NONE }
	}
	case class Cvssv3(
	  /** The base score is a function of the base metric scores. */
		baseScore: Option[BigDecimal] = None,
	  /** Base Metrics Represents the intrinsic characteristics of a vulnerability that are constant over time and across user environments. This metric reflects the context by which vulnerability exploitation is possible. */
		attackVector: Option[Schema.Cvssv3.AttackVectorEnum] = None,
	  /** This metric describes the conditions beyond the attacker's control that must exist in order to exploit the vulnerability. */
		attackComplexity: Option[Schema.Cvssv3.AttackComplexityEnum] = None,
	  /** This metric describes the level of privileges an attacker must possess before successfully exploiting the vulnerability. */
		privilegesRequired: Option[Schema.Cvssv3.PrivilegesRequiredEnum] = None,
	  /** This metric captures the requirement for a human user, other than the attacker, to participate in the successful compromise of the vulnerable component. */
		userInteraction: Option[Schema.Cvssv3.UserInteractionEnum] = None,
	  /** The Scope metric captures whether a vulnerability in one vulnerable component impacts resources in components beyond its security scope. */
		scope: Option[Schema.Cvssv3.ScopeEnum] = None,
	  /** This metric measures the impact to the confidentiality of the information resources managed by a software component due to a successfully exploited vulnerability. */
		confidentialityImpact: Option[Schema.Cvssv3.ConfidentialityImpactEnum] = None,
	  /** This metric measures the impact to integrity of a successfully exploited vulnerability. */
		integrityImpact: Option[Schema.Cvssv3.IntegrityImpactEnum] = None,
	  /** This metric measures the impact to the availability of the impacted component resulting from a successfully exploited vulnerability. */
		availabilityImpact: Option[Schema.Cvssv3.AvailabilityImpactEnum] = None
	)
	
	case class Package(
	  /** The name of the package where the vulnerability was detected. */
		packageName: Option[String] = None,
	  /** The CPE URI where the vulnerability was detected. */
		cpeUri: Option[String] = None,
	  /** Type of package, for example, os, maven, or go. */
		packageType: Option[String] = None,
	  /** The version of the package. */
		packageVersion: Option[String] = None
	)
	
	case class SecurityBulletin(
	  /** ID of the bulletin corresponding to the vulnerability. */
		bulletinId: Option[String] = None,
	  /** Submission time of this Security Bulletin. */
		submissionTime: Option[String] = None,
	  /** This represents a version that the cluster receiving this notification should be upgraded to, based on its current version. For example, 1.15.0 */
		suggestedUpgradeVersion: Option[String] = None
	)
	
	case class GoogleCloudSecuritycenterV1ExternalSystem(
	  /** Full resource name of the external system, for example: "organizations/1234/sources/5678/findings/123456/externalSystems/jira", "folders/1234/sources/5678/findings/123456/externalSystems/jira", "projects/1234/sources/5678/findings/123456/externalSystems/jira" */
		name: Option[String] = None,
	  /** References primary/secondary etc assignees in the external system. */
		assignees: Option[List[String]] = None,
	  /** The identifier that's used to track the finding's corresponding case in the external system. */
		externalUid: Option[String] = None,
	  /** The most recent status of the finding's corresponding case, as reported by the external system. */
		status: Option[String] = None,
	  /** The time when the case was last updated, as reported by the external system. */
		externalSystemUpdateTime: Option[String] = None,
	  /** The link to the finding's corresponding case in the external system. */
		caseUri: Option[String] = None,
	  /** The priority of the finding's corresponding case in the external system. */
		casePriority: Option[String] = None,
	  /** The SLA of the finding's corresponding case in the external system. */
		caseSla: Option[String] = None,
	  /** The time when the case was created, as reported by the external system. */
		caseCreateTime: Option[String] = None,
	  /** The time when the case was closed, as reported by the external system. */
		caseCloseTime: Option[String] = None,
	  /** Information about the ticket, if any, that is being used to track the resolution of the issue that is identified by this finding. */
		ticketInfo: Option[Schema.TicketInfo] = None
	)
	
	case class TicketInfo(
	  /** The identifier of the ticket in the ticket system. */
		id: Option[String] = None,
	  /** The assignee of the ticket in the ticket system. */
		assignee: Option[String] = None,
	  /** The description of the ticket in the ticket system. */
		description: Option[String] = None,
	  /** The link to the ticket in the ticket system. */
		uri: Option[String] = None,
	  /** The latest status of the ticket, as reported by the ticket system. */
		status: Option[String] = None,
	  /** The time when the ticket was last updated, as reported by the ticket system. */
		updateTime: Option[String] = None
	)
	
	object MitreAttack {
		enum PrimaryTacticEnum extends Enum[PrimaryTacticEnum] { case TACTIC_UNSPECIFIED, RECONNAISSANCE, RESOURCE_DEVELOPMENT, INITIAL_ACCESS, EXECUTION, PERSISTENCE, PRIVILEGE_ESCALATION, DEFENSE_EVASION, CREDENTIAL_ACCESS, DISCOVERY, LATERAL_MOVEMENT, COLLECTION, COMMAND_AND_CONTROL, EXFILTRATION, IMPACT }
		enum PrimaryTechniquesEnum extends Enum[PrimaryTechniquesEnum] { case TECHNIQUE_UNSPECIFIED, MASQUERADING, MATCH_LEGITIMATE_NAME_OR_LOCATION, BOOT_OR_LOGON_INITIALIZATION_SCRIPTS, STARTUP_ITEMS, NETWORK_SERVICE_DISCOVERY, PROCESS_DISCOVERY, COMMAND_AND_SCRIPTING_INTERPRETER, UNIX_SHELL, PYTHON, EXPLOITATION_FOR_PRIVILEGE_ESCALATION, PERMISSION_GROUPS_DISCOVERY, CLOUD_GROUPS, INDICATOR_REMOVAL_FILE_DELETION, APPLICATION_LAYER_PROTOCOL, DNS, SOFTWARE_DEPLOYMENT_TOOLS, VALID_ACCOUNTS, DEFAULT_ACCOUNTS, LOCAL_ACCOUNTS, CLOUD_ACCOUNTS, PROXY, EXTERNAL_PROXY, MULTI_HOP_PROXY, ACCOUNT_MANIPULATION, ADDITIONAL_CLOUD_CREDENTIALS, SSH_AUTHORIZED_KEYS, ADDITIONAL_CONTAINER_CLUSTER_ROLES, INGRESS_TOOL_TRANSFER, NATIVE_API, BRUTE_FORCE, SHARED_MODULES, ACCESS_TOKEN_MANIPULATION, TOKEN_IMPERSONATION_OR_THEFT, EXPLOIT_PUBLIC_FACING_APPLICATION, DOMAIN_POLICY_MODIFICATION, DATA_DESTRUCTION, SERVICE_STOP, INHIBIT_SYSTEM_RECOVERY, RESOURCE_HIJACKING, NETWORK_DENIAL_OF_SERVICE, CLOUD_SERVICE_DISCOVERY, STEAL_APPLICATION_ACCESS_TOKEN, ACCOUNT_ACCESS_REMOVAL, STEAL_WEB_SESSION_COOKIE, CREATE_OR_MODIFY_SYSTEM_PROCESS, EVENT_TRIGGERED_EXECUTION, ABUSE_ELEVATION_CONTROL_MECHANISM, UNSECURED_CREDENTIALS, MODIFY_AUTHENTICATION_PROCESS, IMPAIR_DEFENSES, DISABLE_OR_MODIFY_TOOLS, EXFILTRATION_OVER_WEB_SERVICE, EXFILTRATION_TO_CLOUD_STORAGE, DYNAMIC_RESOLUTION, LATERAL_TOOL_TRANSFER, MODIFY_CLOUD_COMPUTE_INFRASTRUCTURE, CREATE_SNAPSHOT, CLOUD_INFRASTRUCTURE_DISCOVERY, OBTAIN_CAPABILITIES, ACTIVE_SCANNING, SCANNING_IP_BLOCKS, CONTAINER_ADMINISTRATION_COMMAND, DEPLOY_CONTAINER, ESCAPE_TO_HOST, CONTAINER_AND_RESOURCE_DISCOVERY, STEAL_OR_FORGE_AUTHENTICATION_CERTIFICATES }
		enum AdditionalTacticsEnum extends Enum[AdditionalTacticsEnum] { case TACTIC_UNSPECIFIED, RECONNAISSANCE, RESOURCE_DEVELOPMENT, INITIAL_ACCESS, EXECUTION, PERSISTENCE, PRIVILEGE_ESCALATION, DEFENSE_EVASION, CREDENTIAL_ACCESS, DISCOVERY, LATERAL_MOVEMENT, COLLECTION, COMMAND_AND_CONTROL, EXFILTRATION, IMPACT }
		enum AdditionalTechniquesEnum extends Enum[AdditionalTechniquesEnum] { case TECHNIQUE_UNSPECIFIED, MASQUERADING, MATCH_LEGITIMATE_NAME_OR_LOCATION, BOOT_OR_LOGON_INITIALIZATION_SCRIPTS, STARTUP_ITEMS, NETWORK_SERVICE_DISCOVERY, PROCESS_DISCOVERY, COMMAND_AND_SCRIPTING_INTERPRETER, UNIX_SHELL, PYTHON, EXPLOITATION_FOR_PRIVILEGE_ESCALATION, PERMISSION_GROUPS_DISCOVERY, CLOUD_GROUPS, INDICATOR_REMOVAL_FILE_DELETION, APPLICATION_LAYER_PROTOCOL, DNS, SOFTWARE_DEPLOYMENT_TOOLS, VALID_ACCOUNTS, DEFAULT_ACCOUNTS, LOCAL_ACCOUNTS, CLOUD_ACCOUNTS, PROXY, EXTERNAL_PROXY, MULTI_HOP_PROXY, ACCOUNT_MANIPULATION, ADDITIONAL_CLOUD_CREDENTIALS, SSH_AUTHORIZED_KEYS, ADDITIONAL_CONTAINER_CLUSTER_ROLES, INGRESS_TOOL_TRANSFER, NATIVE_API, BRUTE_FORCE, SHARED_MODULES, ACCESS_TOKEN_MANIPULATION, TOKEN_IMPERSONATION_OR_THEFT, EXPLOIT_PUBLIC_FACING_APPLICATION, DOMAIN_POLICY_MODIFICATION, DATA_DESTRUCTION, SERVICE_STOP, INHIBIT_SYSTEM_RECOVERY, RESOURCE_HIJACKING, NETWORK_DENIAL_OF_SERVICE, CLOUD_SERVICE_DISCOVERY, STEAL_APPLICATION_ACCESS_TOKEN, ACCOUNT_ACCESS_REMOVAL, STEAL_WEB_SESSION_COOKIE, CREATE_OR_MODIFY_SYSTEM_PROCESS, EVENT_TRIGGERED_EXECUTION, ABUSE_ELEVATION_CONTROL_MECHANISM, UNSECURED_CREDENTIALS, MODIFY_AUTHENTICATION_PROCESS, IMPAIR_DEFENSES, DISABLE_OR_MODIFY_TOOLS, EXFILTRATION_OVER_WEB_SERVICE, EXFILTRATION_TO_CLOUD_STORAGE, DYNAMIC_RESOLUTION, LATERAL_TOOL_TRANSFER, MODIFY_CLOUD_COMPUTE_INFRASTRUCTURE, CREATE_SNAPSHOT, CLOUD_INFRASTRUCTURE_DISCOVERY, OBTAIN_CAPABILITIES, ACTIVE_SCANNING, SCANNING_IP_BLOCKS, CONTAINER_ADMINISTRATION_COMMAND, DEPLOY_CONTAINER, ESCAPE_TO_HOST, CONTAINER_AND_RESOURCE_DISCOVERY, STEAL_OR_FORGE_AUTHENTICATION_CERTIFICATES }
	}
	case class MitreAttack(
	  /** The MITRE ATT&CK tactic most closely represented by this finding, if any. */
		primaryTactic: Option[Schema.MitreAttack.PrimaryTacticEnum] = None,
	  /** The MITRE ATT&CK technique most closely represented by this finding, if any. primary_techniques is a repeated field because there are multiple levels of MITRE ATT&CK techniques. If the technique most closely represented by this finding is a sub-technique (e.g. `SCANNING_IP_BLOCKS`), both the sub-technique and its parent technique(s) will be listed (e.g. `SCANNING_IP_BLOCKS`, `ACTIVE_SCANNING`). */
		primaryTechniques: Option[List[Schema.MitreAttack.PrimaryTechniquesEnum]] = None,
	  /** Additional MITRE ATT&CK tactics related to this finding, if any. */
		additionalTactics: Option[List[Schema.MitreAttack.AdditionalTacticsEnum]] = None,
	  /** Additional MITRE ATT&CK techniques related to this finding, if any, along with any of their respective parent techniques. */
		additionalTechniques: Option[List[Schema.MitreAttack.AdditionalTechniquesEnum]] = None,
	  /** The MITRE ATT&CK version referenced by the above fields. E.g. "8". */
		version: Option[String] = None
	)
	
	case class Access(
	  /** Associated email, such as "foo@google.com". The email address of the authenticated user or a service account acting on behalf of a third party principal making the request. For third party identity callers, the `principal_subject` field is populated instead of this field. For privacy reasons, the principal email address is sometimes redacted. For more information, see [Caller identities in audit logs](https://cloud.google.com/logging/docs/audit#user-id). */
		principalEmail: Option[String] = None,
	  /** Caller's IP address, such as "1.1.1.1". */
		callerIp: Option[String] = None,
	  /** The caller IP's geolocation, which identifies where the call came from. */
		callerIpGeo: Option[Schema.Geolocation] = None,
	  /** Type of user agent associated with the finding. For example, an operating system shell or an embedded or standalone application. */
		userAgentFamily: Option[String] = None,
	  /** The caller's user agent string associated with the finding. */
		userAgent: Option[String] = None,
	  /** This is the API service that the service account made a call to, e.g. "iam.googleapis.com" */
		serviceName: Option[String] = None,
	  /** The method that the service account called, e.g. "SetIamPolicy". */
		methodName: Option[String] = None,
	  /** A string that represents the principal_subject that is associated with the identity. Unlike `principal_email`, `principal_subject` supports principals that aren't associated with email addresses, such as third party principals. For most identities, the format is `principal://iam.googleapis.com/{identity pool name}/subject/{subject}`. Some GKE identities, such as GKE_WORKLOAD, FREEFORM, and GKE_HUB_WORKLOAD, still use the legacy format `serviceAccount:{identity pool name}[{subject}]`. */
		principalSubject: Option[String] = None,
	  /** The name of the service account key that was used to create or exchange credentials when authenticating the service account that made the request. This is a scheme-less URI full resource name. For example: "//iam.googleapis.com/projects/{PROJECT_ID}/serviceAccounts/{ACCOUNT}/keys/{key}".  */
		serviceAccountKeyName: Option[String] = None,
	  /** The identity delegation history of an authenticated service account that made the request. The `serviceAccountDelegationInfo[]` object contains information about the real authorities that try to access Google Cloud resources by delegating on a service account. When multiple authorities are present, they are guaranteed to be sorted based on the original ordering of the identity delegation events. */
		serviceAccountDelegationInfo: Option[List[Schema.ServiceAccountDelegationInfo]] = None,
	  /** A string that represents a username. The username provided depends on the type of the finding and is likely not an IAM principal. For example, this can be a system username if the finding is related to a virtual machine, or it can be an application login username. */
		userName: Option[String] = None
	)
	
	case class Geolocation(
	  /** A CLDR. */
		regionCode: Option[String] = None
	)
	
	case class ServiceAccountDelegationInfo(
	  /** The email address of a Google account. */
		principalEmail: Option[String] = None,
	  /** A string representing the principal_subject associated with the identity. As compared to `principal_email`, supports principals that aren't associated with email addresses, such as third party principals. For most identities, the format will be `principal://iam.googleapis.com/{identity pool name}/subjects/{subject}` except for some GKE identities (GKE_WORKLOAD, FREEFORM, GKE_HUB_WORKLOAD) that are still in the legacy format `serviceAccount:{identity pool name}[{subject}]` */
		principalSubject: Option[String] = None
	)
	
	object Connection {
		enum ProtocolEnum extends Enum[ProtocolEnum] { case PROTOCOL_UNSPECIFIED, ICMP, TCP, UDP, GRE, ESP }
	}
	case class Connection(
	  /** Destination IP address. Not present for sockets that are listening and not connected. */
		destinationIp: Option[String] = None,
	  /** Destination port. Not present for sockets that are listening and not connected. */
		destinationPort: Option[Int] = None,
	  /** Source IP address. */
		sourceIp: Option[String] = None,
	  /** Source port. */
		sourcePort: Option[Int] = None,
	  /** IANA Internet Protocol Number such as TCP(6) and UDP(17). */
		protocol: Option[Schema.Connection.ProtocolEnum] = None
	)
	
	case class MuteInfo(
	  /** If set, the static mute applied to this finding. Static mutes override dynamic mutes. If unset, there is no static mute. */
		staticMute: Option[Schema.StaticMute] = None,
	  /** The list of dynamic mute rules that currently match the finding. */
		dynamicMuteRecords: Option[List[Schema.DynamicMuteRecord]] = None
	)
	
	object StaticMute {
		enum StateEnum extends Enum[StateEnum] { case MUTE_UNSPECIFIED, MUTED, UNMUTED, UNDEFINED }
	}
	case class StaticMute(
	  /** The static mute state. If the value is `MUTED` or `UNMUTED`, then the finding's overall mute state will have the same value. */
		state: Option[Schema.StaticMute.StateEnum] = None,
	  /** When the static mute was applied. */
		applyTime: Option[String] = None
	)
	
	case class DynamicMuteRecord(
	  /** The relative resource name of the mute rule, represented by a mute config, that created this record, for example `organizations/123/muteConfigs/mymuteconfig` or `organizations/123/locations/global/muteConfigs/mymuteconfig`. */
		muteConfig: Option[String] = None,
	  /** When the dynamic mute rule first matched the finding. */
		matchTime: Option[String] = None
	)
	
	case class Process(
	  /** The process name, as displayed in utilities like `top` and `ps`. This name can be accessed through `/proc/[pid]/comm` and changed with `prctl(PR_SET_NAME)`. */
		name: Option[String] = None,
	  /** File information for the process executable. */
		binary: Option[Schema.File] = None,
	  /** File information for libraries loaded by the process. */
		libraries: Option[List[Schema.File]] = None,
	  /** When the process represents the invocation of a script, `binary` provides information about the interpreter, while `script` provides information about the script file provided to the interpreter. */
		script: Option[Schema.File] = None,
	  /** Process arguments as JSON encoded strings. */
		args: Option[List[String]] = None,
	  /** True if `args` is incomplete. */
		argumentsTruncated: Option[Boolean] = None,
	  /** Process environment variables. */
		envVariables: Option[List[Schema.EnvironmentVariable]] = None,
	  /** True if `env_variables` is incomplete. */
		envVariablesTruncated: Option[Boolean] = None,
	  /** The process ID. */
		pid: Option[String] = None,
	  /** The parent process ID. */
		parentPid: Option[String] = None
	)
	
	case class File(
	  /** Absolute path of the file as a JSON encoded string. */
		path: Option[String] = None,
	  /** Size of the file in bytes. */
		size: Option[String] = None,
	  /** SHA256 hash of the first hashed_size bytes of the file encoded as a hex string. If hashed_size == size, sha256 represents the SHA256 hash of the entire file. */
		sha256: Option[String] = None,
	  /** The length in bytes of the file prefix that was hashed. If hashed_size == size, any hashes reported represent the entire file. */
		hashedSize: Option[String] = None,
	  /** True when the hash covers only a prefix of the file. */
		partiallyHashed: Option[Boolean] = None,
	  /** Prefix of the file contents as a JSON-encoded string. */
		contents: Option[String] = None,
	  /** Path of the file in terms of underlying disk/partition identifiers. */
		diskPath: Option[Schema.DiskPath] = None
	)
	
	case class DiskPath(
	  /** UUID of the partition (format https://wiki.archlinux.org/title/persistent_block_device_naming#by-uuid) */
		partitionUuid: Option[String] = None,
	  /** Relative path of the file in the partition as a JSON encoded string. Example: /home/user1/executable_file.sh */
		relativePath: Option[String] = None
	)
	
	case class EnvironmentVariable(
	  /** Environment variable name as a JSON encoded string. */
		name: Option[String] = None,
	  /** Environment variable value as a JSON encoded string. */
		`val`: Option[String] = None
	)
	
	case class ContactDetails(
	  /** A list of contacts */
		contacts: Option[List[Schema.Contact]] = None
	)
	
	case class Contact(
	  /** An email address. For example, "`person123@company.com`". */
		email: Option[String] = None
	)
	
	case class Compliance(
	  /** Industry-wide compliance standards or benchmarks, such as CIS, PCI, and OWASP. */
		standard: Option[String] = None,
	  /** Version of the standard or benchmark, for example, 1.1 */
		version: Option[String] = None,
	  /** Policies within the standard or benchmark, for example, A.12.4.1 */
		ids: Option[List[String]] = None
	)
	
	case class Exfiltration(
	  /** If there are multiple sources, then the data is considered "joined" between them. For instance, BigQuery can join multiple tables, and each table would be considered a source. */
		sources: Option[List[Schema.ExfilResource]] = None,
	  /** If there are multiple targets, each target would get a complete copy of the "joined" source data. */
		targets: Option[List[Schema.ExfilResource]] = None,
	  /** Total exfiltrated bytes processed for the entire job. */
		totalExfiltratedBytes: Option[String] = None
	)
	
	case class ExfilResource(
	  /** The resource's [full resource name](https://cloud.google.com/apis/design/resource_names#full_resource_name). */
		name: Option[String] = None,
	  /** Subcomponents of the asset that was exfiltrated, like URIs used during exfiltration, table names, databases, and filenames. For example, multiple tables might have been exfiltrated from the same Cloud SQL instance, or multiple files might have been exfiltrated from the same Cloud Storage bucket. */
		components: Option[List[String]] = None
	)
	
	object IamBinding {
		enum ActionEnum extends Enum[ActionEnum] { case ACTION_UNSPECIFIED, ADD, REMOVE }
	}
	case class IamBinding(
	  /** The action that was performed on a Binding. */
		action: Option[Schema.IamBinding.ActionEnum] = None,
	  /** Role that is assigned to "members". For example, "roles/viewer", "roles/editor", or "roles/owner". */
		role: Option[String] = None,
	  /** A single identity requesting access for a Cloud Platform resource, for example, "foo@google.com". */
		member: Option[String] = None
	)
	
	case class Container(
	  /** Name of the container. */
		name: Option[String] = None,
	  /** Container image URI provided when configuring a pod or container. This string can identify a container image version using mutable tags. */
		uri: Option[String] = None,
	  /** Optional container image ID, if provided by the container runtime. Uniquely identifies the container image launched using a container image digest. */
		imageId: Option[String] = None,
	  /** Container labels, as provided by the container runtime. */
		labels: Option[List[Schema.Label]] = None,
	  /** The time that the container was created. */
		createTime: Option[String] = None
	)
	
	case class Label(
	  /** Name of the label. */
		name: Option[String] = None,
	  /** Value that corresponds to the label's name. */
		value: Option[String] = None
	)
	
	case class Kubernetes(
	  /** Kubernetes [Pods](https://cloud.google.com/kubernetes-engine/docs/concepts/pod) associated with the finding. This field contains Pod records for each container that is owned by a Pod. */
		pods: Option[List[Schema.Pod]] = None,
	  /** Provides Kubernetes [node](https://cloud.google.com/kubernetes-engine/docs/concepts/cluster-architecture#nodes) information. */
		nodes: Option[List[Schema.Node]] = None,
	  /** GKE [node pools](https://cloud.google.com/kubernetes-engine/docs/concepts/node-pools) associated with the finding. This field contains node pool information for each node, when it is available. */
		nodePools: Option[List[Schema.NodePool]] = None,
	  /** Provides Kubernetes role information for findings that involve [Roles or ClusterRoles](https://cloud.google.com/kubernetes-engine/docs/how-to/role-based-access-control). */
		roles: Option[List[Schema.Role]] = None,
	  /** Provides Kubernetes role binding information for findings that involve [RoleBindings or ClusterRoleBindings](https://cloud.google.com/kubernetes-engine/docs/how-to/role-based-access-control). */
		bindings: Option[List[Schema.GoogleCloudSecuritycenterV1Binding]] = None,
	  /** Provides information on any Kubernetes access reviews (privilege checks) relevant to the finding. */
		accessReviews: Option[List[Schema.AccessReview]] = None,
	  /** Kubernetes objects related to the finding. */
		objects: Option[List[Schema.Object]] = None
	)
	
	case class Pod(
	  /** Kubernetes Pod namespace. */
		ns: Option[String] = None,
	  /** Kubernetes Pod name. */
		name: Option[String] = None,
	  /** Pod labels. For Kubernetes containers, these are applied to the container. */
		labels: Option[List[Schema.Label]] = None,
	  /** Pod containers associated with this finding, if any. */
		containers: Option[List[Schema.Container]] = None
	)
	
	case class Node(
	  /** [Full resource name](https://google.aip.dev/122#full-resource-names) of the Compute Engine VM running the cluster node. */
		name: Option[String] = None
	)
	
	case class NodePool(
	  /** Kubernetes node pool name. */
		name: Option[String] = None,
	  /** Nodes associated with the finding. */
		nodes: Option[List[Schema.Node]] = None
	)
	
	object Role {
		enum KindEnum extends Enum[KindEnum] { case KIND_UNSPECIFIED, ROLE, CLUSTER_ROLE }
	}
	case class Role(
	  /** Role type. */
		kind: Option[Schema.Role.KindEnum] = None,
	  /** Role namespace. */
		ns: Option[String] = None,
	  /** Role name. */
		name: Option[String] = None
	)
	
	case class GoogleCloudSecuritycenterV1Binding(
	  /** Namespace for the binding. */
		ns: Option[String] = None,
	  /** Name for the binding. */
		name: Option[String] = None,
	  /** The Role or ClusterRole referenced by the binding. */
		role: Option[Schema.Role] = None,
	  /** Represents one or more subjects that are bound to the role. Not always available for PATCH requests. */
		subjects: Option[List[Schema.Subject]] = None
	)
	
	object Subject {
		enum KindEnum extends Enum[KindEnum] { case AUTH_TYPE_UNSPECIFIED, USER, SERVICEACCOUNT, GROUP }
	}
	case class Subject(
	  /** Authentication type for the subject. */
		kind: Option[Schema.Subject.KindEnum] = None,
	  /** Namespace for the subject. */
		ns: Option[String] = None,
	  /** Name for the subject. */
		name: Option[String] = None
	)
	
	case class AccessReview(
	  /** The API group of the resource. "&#42;" means all. */
		group: Option[String] = None,
	  /** Namespace of the action being requested. Currently, there is no distinction between no namespace and all namespaces. Both are represented by "" (empty). */
		ns: Option[String] = None,
	  /** The name of the resource being requested. Empty means all. */
		name: Option[String] = None,
	  /** The optional resource type requested. "&#42;" means all. */
		resource: Option[String] = None,
	  /** The optional subresource type. */
		subresource: Option[String] = None,
	  /** A Kubernetes resource API verb, like get, list, watch, create, update, delete, proxy. "&#42;" means all. */
		verb: Option[String] = None,
	  /** The API version of the resource. "&#42;" means all. */
		version: Option[String] = None
	)
	
	case class Object(
	  /** Kubernetes object group, such as "policy.k8s.io/v1". */
		group: Option[String] = None,
	  /** Kubernetes object kind, such as "Namespace". */
		kind: Option[String] = None,
	  /** Kubernetes object namespace. Must be a valid DNS label. Named "ns" to avoid collision with C++ namespace keyword. For details see https://kubernetes.io/docs/tasks/administer-cluster/namespaces/. */
		ns: Option[String] = None,
	  /** Kubernetes object name. For details see https://kubernetes.io/docs/concepts/overview/working-with-objects/names/. */
		name: Option[String] = None,
	  /** Pod containers associated with this finding, if any. */
		containers: Option[List[Schema.Container]] = None
	)
	
	case class Database(
	  /** Some database resources may not have the [full resource name](https://google.aip.dev/122#full-resource-names) populated because these resource types are not yet supported by Cloud Asset Inventory (e.g. Cloud SQL databases). In these cases only the display name will be provided. The [full resource name](https://google.aip.dev/122#full-resource-names) of the database that the user connected to, if it is supported by Cloud Asset Inventory. */
		name: Option[String] = None,
	  /** The human-readable name of the database that the user connected to. */
		displayName: Option[String] = None,
	  /** The username used to connect to the database. The username might not be an IAM principal and does not have a set format. */
		userName: Option[String] = None,
	  /** The SQL statement that is associated with the database access. */
		query: Option[String] = None,
	  /** The target usernames, roles, or groups of an SQL privilege grant, which is not an IAM policy change. */
		grantees: Option[List[String]] = None,
	  /** The version of the database, for example, POSTGRES_14. See [the complete list](https://cloud.google.com/sql/docs/mysql/admin-api/rest/v1/SqlDatabaseVersion). */
		version: Option[String] = None
	)
	
	object AttackExposure {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, CALCULATED, NOT_CALCULATED }
	}
	case class AttackExposure(
	  /** A number between 0 (inclusive) and infinity that represents how important this finding is to remediate. The higher the score, the more important it is to remediate. */
		score: Option[BigDecimal] = None,
	  /** The most recent time the attack exposure was updated on this finding. */
		latestCalculationTime: Option[String] = None,
	  /** The resource name of the attack path simulation result that contains the details regarding this attack exposure score. Example: `organizations/123/simulations/456/attackExposureResults/789` */
		attackExposureResult: Option[String] = None,
	  /** What state this AttackExposure is in. This captures whether or not an attack exposure has been calculated or not. */
		state: Option[Schema.AttackExposure.StateEnum] = None,
	  /** The number of high value resources that are exposed as a result of this finding. */
		exposedHighValueResourcesCount: Option[Int] = None,
	  /** The number of medium value resources that are exposed as a result of this finding. */
		exposedMediumValueResourcesCount: Option[Int] = None,
	  /** The number of high value resources that are exposed as a result of this finding. */
		exposedLowValueResourcesCount: Option[Int] = None
	)
	
	case class CloudDlpInspection(
	  /** Name of the inspection job, for example, `projects/123/locations/europe/dlpJobs/i-8383929`. */
		inspectJob: Option[String] = None,
	  /** The type of information (or &#42;[infoType](https://cloud.google.com/dlp/docs/infotypes-reference)&#42;) found, for example, `EMAIL_ADDRESS` or `STREET_ADDRESS`. */
		infoType: Option[String] = None,
	  /** The number of times Cloud DLP found this infoType within this job and resource. */
		infoTypeCount: Option[String] = None,
	  /** Whether Cloud DLP scanned the complete resource or a sampled subset. */
		fullScan: Option[Boolean] = None
	)
	
	object CloudDlpDataProfile {
		enum ParentTypeEnum extends Enum[ParentTypeEnum] { case PARENT_TYPE_UNSPECIFIED, ORGANIZATION, PROJECT }
	}
	case class CloudDlpDataProfile(
	  /** Name of the data profile, for example, `projects/123/locations/europe/tableProfiles/8383929`. */
		dataProfile: Option[String] = None,
	  /** The resource hierarchy level at which the data profile was generated. */
		parentType: Option[Schema.CloudDlpDataProfile.ParentTypeEnum] = None
	)
	
	case class KernelRootkit(
	  /** Rootkit name, when available. */
		name: Option[String] = None,
	  /** True if unexpected modifications of kernel code memory are present. */
		unexpectedCodeModification: Option[Boolean] = None,
	  /** True if unexpected modifications of kernel read-only data memory are present. */
		unexpectedReadOnlyDataModification: Option[Boolean] = None,
	  /** True if `ftrace` points are present with callbacks pointing to regions that are not in the expected kernel or module code range. */
		unexpectedFtraceHandler: Option[Boolean] = None,
	  /** True if `kprobe` points are present with callbacks pointing to regions that are not in the expected kernel or module code range. */
		unexpectedKprobeHandler: Option[Boolean] = None,
	  /** True if kernel code pages that are not in the expected kernel or module code regions are present. */
		unexpectedKernelCodePages: Option[Boolean] = None,
	  /** True if system call handlers that are are not in the expected kernel or module code regions are present. */
		unexpectedSystemCallHandler: Option[Boolean] = None,
	  /** True if interrupt handlers that are are not in the expected kernel or module code regions are present. */
		unexpectedInterruptHandler: Option[Boolean] = None,
	  /** True if unexpected processes in the scheduler run queue are present. Such processes are in the run queue, but not in the process task list. */
		unexpectedProcessesInRunqueue: Option[Boolean] = None
	)
	
	case class OrgPolicy(
	  /** The resource name of the org policy. Example: "organizations/{organization_id}/policies/{constraint_name}" */
		name: Option[String] = None
	)
	
	case class Application(
	  /** The base URI that identifies the network location of the application in which the vulnerability was detected. For example, `http://example.com`. */
		baseUri: Option[String] = None,
	  /** The full URI with payload that can be used to reproduce the vulnerability. For example, `http://example.com?p=aMmYgI6H`. */
		fullUri: Option[String] = None
	)
	
	case class BackupDisasterRecovery(
	  /** The name of a Backup and DR template which comprises one or more backup policies. See the [Backup and DR documentation](https://cloud.google.com/backup-disaster-recovery/docs/concepts/backup-plan#temp) for more information. For example, `snap-ov`. */
		backupTemplate: Option[String] = None,
	  /** The names of Backup and DR policies that are associated with a template and that define when to run a backup, how frequently to run a backup, and how long to retain the backup image. For example, `onvaults`. */
		policies: Option[List[String]] = None,
	  /** The name of a Backup and DR host, which is managed by the backup and recovery appliance and known to the management console. The host can be of type Generic (for example, Compute Engine, SQL Server, Oracle DB, SMB file system, etc.), vCenter, or an ESX server. See the [Backup and DR documentation on hosts](https://cloud.google.com/backup-disaster-recovery/docs/configuration/manage-hosts-and-their-applications) for more information. For example, `centos7-01`. */
		host: Option[String] = None,
	  /** The names of Backup and DR applications. An application is a VM, database, or file system on a managed host monitored by a backup and recovery appliance. For example, `centos7-01-vol00`, `centos7-01-vol01`, `centos7-01-vol02`. */
		applications: Option[List[String]] = None,
	  /** The name of the Backup and DR storage pool that the backup and recovery appliance is storing data in. The storage pool could be of type Cloud, Primary, Snapshot, or OnVault. See the [Backup and DR documentation on storage pools](https://cloud.google.com/backup-disaster-recovery/docs/concepts/storage-pools). For example, `DiskPoolOne`. */
		storagePool: Option[String] = None,
	  /** The names of Backup and DR advanced policy options of a policy applying to an application. See the [Backup and DR documentation on policy options](https://cloud.google.com/backup-disaster-recovery/docs/create-plan/policy-settings). For example, `skipofflineappsincongrp, nounmap`. */
		policyOptions: Option[List[String]] = None,
	  /** The name of the Backup and DR resource profile that specifies the storage media for backups of application and VM data. See the [Backup and DR documentation on profiles](https://cloud.google.com/backup-disaster-recovery/docs/concepts/backup-plan#profile). For example, `GCP`. */
		profile: Option[String] = None,
	  /** The name of the Backup and DR appliance that captures, moves, and manages the lifecycle of backup data. For example, `backup-server-57137`. */
		appliance: Option[String] = None,
	  /** The backup type of the Backup and DR image. For example, `Snapshot`, `Remote Snapshot`, `OnVault`. */
		backupType: Option[String] = None,
	  /** The timestamp at which the Backup and DR backup was created. */
		backupCreateTime: Option[String] = None
	)
	
	case class SecurityPosture(
	  /** Name of the posture, for example, `CIS-Posture`. */
		name: Option[String] = None,
	  /** The version of the posture, for example, `c7cfa2a8`. */
		revisionId: Option[String] = None,
	  /** The project, folder, or organization on which the posture is deployed, for example, `projects/{project_number}`. */
		postureDeploymentResource: Option[String] = None,
	  /** The name of the posture deployment, for example, `organizations/{org_id}/posturedeployments/{posture_deployment_id}`. */
		postureDeployment: Option[String] = None,
	  /** The name of the updated policy, for example, `projects/{project_id}/policies/{constraint_name}`. */
		changedPolicy: Option[String] = None,
	  /** The name of the updated policyset, for example, `cis-policyset`. */
		policySet: Option[String] = None,
	  /** The ID of the updated policy, for example, `compute-policy-1`. */
		policy: Option[String] = None,
	  /** The details about a change in an updated policy that violates the deployed posture. */
		policyDriftDetails: Option[List[Schema.PolicyDriftDetails]] = None
	)
	
	case class PolicyDriftDetails(
	  /** The name of the updated field, for example constraint.implementation.policy_rules[0].enforce */
		field: Option[String] = None,
	  /** The value of this field that was configured in a posture, for example, `true` or `allowed_values={"projects/29831892"}`. */
		expectedValue: Option[String] = None,
	  /** The detected value that violates the deployed posture, for example, `false` or `allowed_values={"projects/22831892"}`. */
		detectedValue: Option[String] = None
	)
	
	case class LogEntry(
	  /** An individual entry in a log stored in Cloud Logging. */
		cloudLoggingEntry: Option[Schema.CloudLoggingEntry] = None
	)
	
	case class CloudLoggingEntry(
	  /** A unique identifier for the log entry. */
		insertId: Option[String] = None,
	  /** The type of the log (part of `log_name`. `log_name` is the resource name of the log to which this log entry belongs). For example: `cloudresourcemanager.googleapis.com/activity`. Note that this field is not URL-encoded, unlike the `LOG_ID` field in `LogEntry`. */
		logId: Option[String] = None,
	  /** The organization, folder, or project of the monitored resource that produced this log entry. */
		resourceContainer: Option[String] = None,
	  /** The time the event described by the log entry occurred. */
		timestamp: Option[String] = None
	)
	
	case class LoadBalancer(
	  /** The name of the load balancer associated with the finding. */
		name: Option[String] = None
	)
	
	case class CloudArmor(
	  /** Information about the [Google Cloud Armor security policy](https://cloud.google.com/armor/docs/security-policy-overview) relevant to the finding. */
		securityPolicy: Option[Schema.SecurityPolicy] = None,
	  /** Information about incoming requests evaluated by [Google Cloud Armor security policies](https://cloud.google.com/armor/docs/security-policy-overview). */
		requests: Option[Schema.Requests] = None,
	  /** Information about potential Layer 7 DDoS attacks identified by [Google Cloud Armor Adaptive Protection](https://cloud.google.com/armor/docs/adaptive-protection-overview). */
		adaptiveProtection: Option[Schema.AdaptiveProtection] = None,
	  /** Information about DDoS attack volume and classification. */
		attack: Option[Schema.Attack] = None,
	  /** Distinguish between volumetric & protocol DDoS attack and application layer attacks. For example, "L3_4" for Layer 3 and Layer 4 DDoS attacks, or "L_7" for Layer 7 DDoS attacks. */
		threatVector: Option[String] = None,
	  /** Duration of attack from the start until the current moment (updated every 5 minutes). */
		duration: Option[String] = None
	)
	
	case class SecurityPolicy(
	  /** The name of the Google Cloud Armor security policy, for example, "my-security-policy". */
		name: Option[String] = None,
	  /** The type of Google Cloud Armor security policy for example, 'backend security policy', 'edge security policy', 'network edge security policy', or 'always-on DDoS protection'. */
		`type`: Option[String] = None,
	  /** Whether or not the associated rule or policy is in preview mode. */
		preview: Option[Boolean] = None
	)
	
	case class Requests(
	  /** For 'Increasing deny ratio', the ratio is the denied traffic divided by the allowed traffic. For 'Allowed traffic spike', the ratio is the allowed traffic in the short term divided by allowed traffic in the long term. */
		ratio: Option[BigDecimal] = None,
	  /** Allowed RPS (requests per second) in the short term. */
		shortTermAllowed: Option[Int] = None,
	  /** Allowed RPS (requests per second) over the long term. */
		longTermAllowed: Option[Int] = None,
	  /** Denied RPS (requests per second) over the long term. */
		longTermDenied: Option[Int] = None
	)
	
	case class AdaptiveProtection(
	  /** A score of 0 means that there is low confidence that the detected event is an actual attack. A score of 1 means that there is high confidence that the detected event is an attack. See the [Adaptive Protection documentation](https://cloud.google.com/armor/docs/adaptive-protection-overview#configure-alert-tuning) for further explanation. */
		confidence: Option[BigDecimal] = None
	)
	
	case class Attack(
	  /** Total PPS (packets per second) volume of attack. */
		volumePps: Option[Int] = None,
	  /** Total BPS (bytes per second) volume of attack. */
		volumeBps: Option[Int] = None,
	  /** Type of attack, for example, 'SYN-flood', 'NTP-udp', or 'CHARGEN-udp'. */
		classification: Option[String] = None
	)
	
	case class Notebook(
	  /** The name of the notebook. */
		name: Option[String] = None,
	  /** The source notebook service, for example, "Colab Enterprise". */
		service: Option[String] = None,
	  /** The user ID of the latest author to modify the notebook. */
		lastAuthor: Option[String] = None,
	  /** The most recent time the notebook was updated. */
		notebookUpdateTime: Option[String] = None
	)
	
	case class ToxicCombination(
	  /** The [Attack exposure score](https://cloud.google.com/security-command-center/docs/attack-exposure-learn#attack_exposure_scores) of this toxic combination. The score is a measure of how much this toxic combination exposes one or more high-value resources to potential attack. */
		attackExposureScore: Option[BigDecimal] = None,
	  /** List of resource names of findings associated with this toxic combination. For example, `organizations/123/sources/456/findings/789`. */
		relatedFindings: Option[List[String]] = None
	)
	
	object GroupMembership {
		enum GroupTypeEnum extends Enum[GroupTypeEnum] { case GROUP_TYPE_UNSPECIFIED, GROUP_TYPE_TOXIC_COMBINATION }
	}
	case class GroupMembership(
	  /** Type of group. */
		groupType: Option[Schema.GroupMembership.GroupTypeEnum] = None,
	  /** ID of the group. */
		groupId: Option[String] = None
	)
	
	case class Disk(
	  /** The name of the disk, for example, "https://www.googleapis.com/compute/v1/projects/project-id/zones/zone-id/disks/disk-id". */
		name: Option[String] = None
	)
	
	object DataAccessEvent {
		enum OperationEnum extends Enum[OperationEnum] { case OPERATION_UNSPECIFIED, READ, MOVE, COPY }
	}
	case class DataAccessEvent(
	  /** Unique identifier for data access event. */
		eventId: Option[String] = None,
	  /** The email address of the principal that accessed the data. The principal could be a user account, service account, Google group, or other. */
		principalEmail: Option[String] = None,
	  /** The operation performed by the principal to access the data. */
		operation: Option[Schema.DataAccessEvent.OperationEnum] = None,
	  /** Timestamp of data access event. */
		eventTime: Option[String] = None
	)
	
	object DataFlowEvent {
		enum OperationEnum extends Enum[OperationEnum] { case OPERATION_UNSPECIFIED, READ, MOVE, COPY }
	}
	case class DataFlowEvent(
	  /** Unique identifier for data flow event. */
		eventId: Option[String] = None,
	  /** The email address of the principal that initiated the data flow event. The principal could be a user account, service account, Google group, or other. */
		principalEmail: Option[String] = None,
	  /** The operation performed by the principal for the data flow event. */
		operation: Option[Schema.DataFlowEvent.OperationEnum] = None,
	  /** Non-compliant location of the principal or the data destination. */
		violatedLocation: Option[String] = None,
	  /** Timestamp of data flow event. */
		eventTime: Option[String] = None
	)
	
	object GoogleCloudSecuritycenterV1MuteConfig {
		enum TypeEnum extends Enum[TypeEnum] { case MUTE_CONFIG_TYPE_UNSPECIFIED, STATIC, DYNAMIC }
	}
	case class GoogleCloudSecuritycenterV1MuteConfig(
	  /** This field will be ignored if provided on config creation. Format `organizations/{organization}/muteConfigs/{mute_config}` `folders/{folder}/muteConfigs/{mute_config}` `projects/{project}/muteConfigs/{mute_config}` `organizations/{organization}/locations/global/muteConfigs/{mute_config}` `folders/{folder}/locations/global/muteConfigs/{mute_config}` `projects/{project}/locations/global/muteConfigs/{mute_config}` */
		name: Option[String] = None,
	  /** The human readable name to be displayed for the mute config. */
		displayName: Option[String] = None,
	  /** A description of the mute config. */
		description: Option[String] = None,
	  /** Required. An expression that defines the filter to apply across create/update events of findings. While creating a filter string, be mindful of the scope in which the mute configuration is being created. E.g., If a filter contains project = X but is created under the project = Y scope, it might not match any findings. The following field and operator combinations are supported: &#42; severity: `=`, `:` &#42; category: `=`, `:` &#42; resource.name: `=`, `:` &#42; resource.project_name: `=`, `:` &#42; resource.project_display_name: `=`, `:` &#42; resource.folders.resource_folder: `=`, `:` &#42; resource.parent_name: `=`, `:` &#42; resource.parent_display_name: `=`, `:` &#42; resource.type: `=`, `:` &#42; finding_class: `=`, `:` &#42; indicator.ip_addresses: `=`, `:` &#42; indicator.domains: `=`, `:` */
		filter: Option[String] = None,
	  /** Output only. The time at which the mute config was created. This field is set by the server and will be ignored if provided on config creation. */
		createTime: Option[String] = None,
	  /** Output only. The most recent time at which the mute config was updated. This field is set by the server and will be ignored if provided on config creation or update. */
		updateTime: Option[String] = None,
	  /** Output only. Email address of the user who last edited the mute config. This field is set by the server and will be ignored if provided on config creation or update. */
		mostRecentEditor: Option[String] = None,
	  /** Optional. The type of the mute config, which determines what type of mute state the config affects. The static mute state takes precedence over the dynamic mute state. Immutable after creation. STATIC by default if not set during creation. */
		`type`: Option[Schema.GoogleCloudSecuritycenterV1MuteConfig.TypeEnum] = None,
	  /** Optional. The expiry of the mute config. Only applicable for dynamic configs. If the expiry is set, when the config expires, it is removed from all findings. */
		expiryTime: Option[String] = None
	)
	
	case class NotificationConfig(
	  /** The relative resource name of this notification config. See: https://cloud.google.com/apis/design/resource_names#relative_resource_name Example: "organizations/{organization_id}/notificationConfigs/notify_public_bucket", "folders/{folder_id}/notificationConfigs/notify_public_bucket", or "projects/{project_id}/notificationConfigs/notify_public_bucket". */
		name: Option[String] = None,
	  /** The description of the notification config (max of 1024 characters). */
		description: Option[String] = None,
	  /** The Pub/Sub topic to send notifications to. Its format is "projects/[project_id]/topics/[topic]". */
		pubsubTopic: Option[String] = None,
	  /** Output only. The service account that needs "pubsub.topics.publish" permission to publish to the Pub/Sub topic. */
		serviceAccount: Option[String] = None,
	  /** The config for triggering streaming-based notifications. */
		streamingConfig: Option[Schema.StreamingConfig] = None
	)
	
	case class StreamingConfig(
	  /** Expression that defines the filter to apply across create/update events of assets or findings as specified by the event type. The expression is a list of zero or more restrictions combined via logical operators `AND` and `OR`. Parentheses are supported, and `OR` has higher precedence than `AND`. Restrictions have the form ` ` and may have a `-` character in front of them to indicate negation. The fields map to those defined in the corresponding resource. The supported operators are: &#42; `=` for all value types. &#42; `>`, `<`, `>=`, `<=` for integer values. &#42; `:`, meaning substring matching, for strings. The supported value types are: &#42; string literals in quotes. &#42; integer literals without quotes. &#42; boolean literals `true` and `false` without quotes. */
		filter: Option[String] = None
	)
	
	object Simulation {
		enum CloudProviderEnum extends Enum[CloudProviderEnum] { case CLOUD_PROVIDER_UNSPECIFIED, GOOGLE_CLOUD_PLATFORM, AMAZON_WEB_SERVICES, MICROSOFT_AZURE }
	}
	case class Simulation(
	  /** Full resource name of the Simulation: `organizations/123/simulations/456` */
		name: Option[String] = None,
	  /** Output only. Time simulation was created */
		createTime: Option[String] = None,
	  /** Resource value configurations' metadata used in this simulation. Maximum of 100. */
		resourceValueConfigsMetadata: Option[List[Schema.ResourceValueConfigMetadata]] = None,
	  /** Indicates which cloud provider was used in this simulation. */
		cloudProvider: Option[Schema.Simulation.CloudProviderEnum] = None
	)
	
	case class ResourceValueConfigMetadata(
	  /** Resource value config name */
		name: Option[String] = None
	)
	
	object ValuedResource {
		enum ResourceValueEnum extends Enum[ResourceValueEnum] { case RESOURCE_VALUE_UNSPECIFIED, RESOURCE_VALUE_LOW, RESOURCE_VALUE_MEDIUM, RESOURCE_VALUE_HIGH }
	}
	case class ValuedResource(
	  /** Valued resource name, for example, e.g.: `organizations/123/simulations/456/valuedResources/789` */
		name: Option[String] = None,
	  /** The [full resource name](https://cloud.google.com/apis/design/resource_names#full_resource_name) of the valued resource. */
		resource: Option[String] = None,
	  /** The [resource type](https://cloud.google.com/asset-inventory/docs/supported-asset-types) of the valued resource. */
		resourceType: Option[String] = None,
	  /** Human-readable name of the valued resource. */
		displayName: Option[String] = None,
	  /** How valuable this resource is. */
		resourceValue: Option[Schema.ValuedResource.ResourceValueEnum] = None,
	  /** Exposed score for this valued resource. A value of 0 means no exposure was detected exposure. */
		exposedScore: Option[BigDecimal] = None,
	  /** List of resource value configurations' metadata used to determine the value of this resource. Maximum of 100. */
		resourceValueConfigsUsed: Option[List[Schema.ResourceValueConfigMetadata]] = None
	)
	
	case class GoogleCloudSecuritycenterV1BigQueryExport(
	  /** The relative resource name of this export. See: https://cloud.google.com/apis/design/resource_names#relative_resource_name. Example format: "organizations/{organization_id}/bigQueryExports/{export_id}" Example format: "folders/{folder_id}/bigQueryExports/{export_id}" Example format: "projects/{project_id}/bigQueryExports/{export_id}" This field is provided in responses, and is ignored when provided in create requests. */
		name: Option[String] = None,
	  /** The description of the export (max of 1024 characters). */
		description: Option[String] = None,
	  /** Expression that defines the filter to apply across create/update events of findings. The expression is a list of zero or more restrictions combined via logical operators `AND` and `OR`. Parentheses are supported, and `OR` has higher precedence than `AND`. Restrictions have the form ` ` and may have a `-` character in front of them to indicate negation. The fields map to those defined in the corresponding resource. The supported operators are: &#42; `=` for all value types. &#42; `>`, `<`, `>=`, `<=` for integer values. &#42; `:`, meaning substring matching, for strings. The supported value types are: &#42; string literals in quotes. &#42; integer literals without quotes. &#42; boolean literals `true` and `false` without quotes. */
		filter: Option[String] = None,
	  /** The dataset to write findings' updates to. Its format is "projects/[project_id]/datasets/[bigquery_dataset_id]". BigQuery Dataset unique ID must contain only letters (a-z, A-Z), numbers (0-9), or underscores (_). */
		dataset: Option[String] = None,
	  /** Output only. The time at which the BigQuery export was created. This field is set by the server and will be ignored if provided on export on creation. */
		createTime: Option[String] = None,
	  /** Output only. The most recent time at which the BigQuery export was updated. This field is set by the server and will be ignored if provided on export creation or update. */
		updateTime: Option[String] = None,
	  /** Output only. Email address of the user who last edited the BigQuery export. This field is set by the server and will be ignored if provided on export creation or update. */
		mostRecentEditor: Option[String] = None,
	  /** Output only. The service account that needs permission to create table and upload data to the BigQuery dataset. */
		principal: Option[String] = None
	)
	
	case class GetIamPolicyRequest(
	  /** OPTIONAL: A `GetPolicyOptions` object for specifying options to `GetIamPolicy`. */
		options: Option[Schema.GetPolicyOptions] = None
	)
	
	case class GetPolicyOptions(
	  /** Optional. The maximum policy version that will be used to format the policy. Valid values are 0, 1, and 3. Requests specifying an invalid value will be rejected. Requests for policies with any conditional role bindings must specify version 3. Policies with no conditional role bindings may specify any valid value or leave the field unset. The policy in the response might use the policy version that you specified, or it might use a lower policy version. For example, if you specify version 3, but the policy has no conditional role bindings, the response uses version 1. To learn which resources support conditions in their IAM policies, see the [IAM documentation](https://cloud.google.com/iam/help/conditions/resource-policies). */
		requestedPolicyVersion: Option[Int] = None
	)
	
	case class Policy(
	  /** Specifies the format of the policy. Valid values are `0`, `1`, and `3`. Requests that specify an invalid value are rejected. Any operation that affects conditional role bindings must specify version `3`. This requirement applies to the following operations: &#42; Getting a policy that includes a conditional role binding &#42; Adding a conditional role binding to a policy &#42; Changing a conditional role binding in a policy &#42; Removing any role binding, with or without a condition, from a policy that includes conditions &#42;&#42;Important:&#42;&#42; If you use IAM Conditions, you must include the `etag` field whenever you call `setIamPolicy`. If you omit this field, then IAM allows you to overwrite a version `3` policy with a version `1` policy, and all of the conditions in the version `3` policy are lost. If a policy does not include any conditions, operations on that policy may specify any valid version or leave the field unset. To learn which resources support conditions in their IAM policies, see the [IAM documentation](https://cloud.google.com/iam/help/conditions/resource-policies). */
		version: Option[Int] = None,
	  /** Associates a list of `members`, or principals, with a `role`. Optionally, may specify a `condition` that determines how and when the `bindings` are applied. Each of the `bindings` must contain at least one principal. The `bindings` in a `Policy` can refer to up to 1,500 principals; up to 250 of these principals can be Google groups. Each occurrence of a principal counts towards these limits. For example, if the `bindings` grant 50 different roles to `user:alice@example.com`, and not to any other principal, then you can add another 1,450 principals to the `bindings` in the `Policy`. */
		bindings: Option[List[Schema.Binding]] = None,
	  /** Specifies cloud audit logging configuration for this policy. */
		auditConfigs: Option[List[Schema.AuditConfig]] = None,
	  /** `etag` is used for optimistic concurrency control as a way to help prevent simultaneous updates of a policy from overwriting each other. It is strongly suggested that systems make use of the `etag` in the read-modify-write cycle to perform policy updates in order to avoid race conditions: An `etag` is returned in the response to `getIamPolicy`, and systems are expected to put that etag in the request to `setIamPolicy` to ensure that their change will be applied to the same version of the policy. &#42;&#42;Important:&#42;&#42; If you use IAM Conditions, you must include the `etag` field whenever you call `setIamPolicy`. If you omit this field, then IAM allows you to overwrite a version `3` policy with a version `1` policy, and all of the conditions in the version `3` policy are lost. */
		etag: Option[String] = None
	)
	
	case class Binding(
	  /** Role that is assigned to the list of `members`, or principals. For example, `roles/viewer`, `roles/editor`, or `roles/owner`. For an overview of the IAM roles and permissions, see the [IAM documentation](https://cloud.google.com/iam/docs/roles-overview). For a list of the available pre-defined roles, see [here](https://cloud.google.com/iam/docs/understanding-roles). */
		role: Option[String] = None,
	  /** Specifies the principals requesting access for a Google Cloud resource. `members` can have the following values: &#42; `allUsers`: A special identifier that represents anyone who is on the internet; with or without a Google account. &#42; `allAuthenticatedUsers`: A special identifier that represents anyone who is authenticated with a Google account or a service account. Does not include identities that come from external identity providers (IdPs) through identity federation. &#42; `user:{emailid}`: An email address that represents a specific Google account. For example, `alice@example.com` . &#42; `serviceAccount:{emailid}`: An email address that represents a Google service account. For example, `my-other-app@appspot.gserviceaccount.com`. &#42; `serviceAccount:{projectid}.svc.id.goog[{namespace}/{kubernetes-sa}]`: An identifier for a [Kubernetes service account](https://cloud.google.com/kubernetes-engine/docs/how-to/kubernetes-service-accounts). For example, `my-project.svc.id.goog[my-namespace/my-kubernetes-sa]`. &#42; `group:{emailid}`: An email address that represents a Google group. For example, `admins@example.com`. &#42; `domain:{domain}`: The G Suite domain (primary) that represents all the users of that domain. For example, `google.com` or `example.com`. &#42; `principal://iam.googleapis.com/locations/global/workforcePools/{pool_id}/subject/{subject_attribute_value}`: A single identity in a workforce identity pool. &#42; `principalSet://iam.googleapis.com/locations/global/workforcePools/{pool_id}/group/{group_id}`: All workforce identities in a group. &#42; `principalSet://iam.googleapis.com/locations/global/workforcePools/{pool_id}/attribute.{attribute_name}/{attribute_value}`: All workforce identities with a specific attribute value. &#42; `principalSet://iam.googleapis.com/locations/global/workforcePools/{pool_id}/&#42;`: All identities in a workforce identity pool. &#42; `principal://iam.googleapis.com/projects/{project_number}/locations/global/workloadIdentityPools/{pool_id}/subject/{subject_attribute_value}`: A single identity in a workload identity pool. &#42; `principalSet://iam.googleapis.com/projects/{project_number}/locations/global/workloadIdentityPools/{pool_id}/group/{group_id}`: A workload identity pool group. &#42; `principalSet://iam.googleapis.com/projects/{project_number}/locations/global/workloadIdentityPools/{pool_id}/attribute.{attribute_name}/{attribute_value}`: All identities in a workload identity pool with a certain attribute. &#42; `principalSet://iam.googleapis.com/projects/{project_number}/locations/global/workloadIdentityPools/{pool_id}/&#42;`: All identities in a workload identity pool. &#42; `deleted:user:{emailid}?uid={uniqueid}`: An email address (plus unique identifier) representing a user that has been recently deleted. For example, `alice@example.com?uid=123456789012345678901`. If the user is recovered, this value reverts to `user:{emailid}` and the recovered user retains the role in the binding. &#42; `deleted:serviceAccount:{emailid}?uid={uniqueid}`: An email address (plus unique identifier) representing a service account that has been recently deleted. For example, `my-other-app@appspot.gserviceaccount.com?uid=123456789012345678901`. If the service account is undeleted, this value reverts to `serviceAccount:{emailid}` and the undeleted service account retains the role in the binding. &#42; `deleted:group:{emailid}?uid={uniqueid}`: An email address (plus unique identifier) representing a Google group that has been recently deleted. For example, `admins@example.com?uid=123456789012345678901`. If the group is recovered, this value reverts to `group:{emailid}` and the recovered group retains the role in the binding. &#42; `deleted:principal://iam.googleapis.com/locations/global/workforcePools/{pool_id}/subject/{subject_attribute_value}`: Deleted single identity in a workforce identity pool. For example, `deleted:principal://iam.googleapis.com/locations/global/workforcePools/my-pool-id/subject/my-subject-attribute-value`. */
		members: Option[List[String]] = None,
	  /** The condition that is associated with this binding. If the condition evaluates to `true`, then this binding applies to the current request. If the condition evaluates to `false`, then this binding does not apply to the current request. However, a different role binding might grant the same role to one or more of the principals in this binding. To learn which resources support conditions in their IAM policies, see the [IAM documentation](https://cloud.google.com/iam/help/conditions/resource-policies). */
		condition: Option[Schema.Expr] = None
	)
	
	case class AuditConfig(
	  /** Specifies a service that will be enabled for audit logging. For example, `storage.googleapis.com`, `cloudsql.googleapis.com`. `allServices` is a special value that covers all services. */
		service: Option[String] = None,
	  /** The configuration for logging of each type of permission. */
		auditLogConfigs: Option[List[Schema.AuditLogConfig]] = None
	)
	
	object AuditLogConfig {
		enum LogTypeEnum extends Enum[LogTypeEnum] { case LOG_TYPE_UNSPECIFIED, ADMIN_READ, DATA_WRITE, DATA_READ }
	}
	case class AuditLogConfig(
	  /** The log type that this config enables. */
		logType: Option[Schema.AuditLogConfig.LogTypeEnum] = None,
	  /** Specifies the identities that do not cause logging for this type of permission. Follows the same format of Binding.members. */
		exemptedMembers: Option[List[String]] = None
	)
	
	case class OrganizationSettings(
	  /** The relative resource name of the settings. See: https://cloud.google.com/apis/design/resource_names#relative_resource_name Example: "organizations/{organization_id}/organizationSettings". */
		name: Option[String] = None,
	  /** A flag that indicates if Asset Discovery should be enabled. If the flag is set to `true`, then discovery of assets will occur. If it is set to `false`, all historical assets will remain, but discovery of future assets will not occur. */
		enableAssetDiscovery: Option[Boolean] = None,
	  /** The configuration used for Asset Discovery runs. */
		assetDiscoveryConfig: Option[Schema.AssetDiscoveryConfig] = None
	)
	
	object AssetDiscoveryConfig {
		enum InclusionModeEnum extends Enum[InclusionModeEnum] { case INCLUSION_MODE_UNSPECIFIED, INCLUDE_ONLY, EXCLUDE }
	}
	case class AssetDiscoveryConfig(
	  /** The project ids to use for filtering asset discovery. */
		projectIds: Option[List[String]] = None,
	  /** The mode to use for filtering asset discovery. */
		inclusionMode: Option[Schema.AssetDiscoveryConfig.InclusionModeEnum] = None,
	  /** The folder ids to use for filtering asset discovery. It consists of only digits, e.g., 756619654966. */
		folderIds: Option[List[String]] = None
	)
	
	object GoogleCloudSecuritycenterV1EffectiveSecurityHealthAnalyticsCustomModule {
		enum EnablementStateEnum extends Enum[EnablementStateEnum] { case ENABLEMENT_STATE_UNSPECIFIED, ENABLED, DISABLED }
		enum CloudProviderEnum extends Enum[CloudProviderEnum] { case CLOUD_PROVIDER_UNSPECIFIED, GOOGLE_CLOUD_PLATFORM, AMAZON_WEB_SERVICES, MICROSOFT_AZURE }
	}
	case class GoogleCloudSecuritycenterV1EffectiveSecurityHealthAnalyticsCustomModule(
	  /** Output only. The resource name of the custom module. Its format is "organizations/{organization}/securityHealthAnalyticsSettings/effectiveCustomModules/{customModule}", or "folders/{folder}/securityHealthAnalyticsSettings/effectiveCustomModules/{customModule}", or "projects/{project}/securityHealthAnalyticsSettings/effectiveCustomModules/{customModule}" */
		name: Option[String] = None,
	  /** Output only. The user-specified configuration for the module. */
		customConfig: Option[Schema.GoogleCloudSecuritycenterV1CustomConfig] = None,
	  /** Output only. The effective state of enablement for the module at the given level of the hierarchy. */
		enablementState: Option[Schema.GoogleCloudSecuritycenterV1EffectiveSecurityHealthAnalyticsCustomModule.EnablementStateEnum] = None,
	  /** Output only. The display name for the custom module. The name must be between 1 and 128 characters, start with a lowercase letter, and contain alphanumeric characters or underscores only. */
		displayName: Option[String] = None,
	  /** The cloud provider of the custom module. */
		cloudProvider: Option[Schema.GoogleCloudSecuritycenterV1EffectiveSecurityHealthAnalyticsCustomModule.CloudProviderEnum] = None
	)
	
	case class GroupAssetsRequest(
	  /** Expression that defines the filter to apply across assets. The expression is a list of zero or more restrictions combined via logical operators `AND` and `OR`. Parentheses are supported, and `OR` has higher precedence than `AND`. Restrictions have the form ` ` and may have a `-` character in front of them to indicate negation. The fields map to those defined in the Asset resource. Examples include: &#42; name &#42; security_center_properties.resource_name &#42; resource_properties.a_property &#42; security_marks.marks.marka The supported operators are: &#42; `=` for all value types. &#42; `>`, `<`, `>=`, `<=` for integer values. &#42; `:`, meaning substring matching, for strings. The supported value types are: &#42; string literals in quotes. &#42; integer literals without quotes. &#42; boolean literals `true` and `false` without quotes. The following field and operator combinations are supported: &#42; name: `=` &#42; update_time: `=`, `>`, `<`, `>=`, `<=` Usage: This should be milliseconds since epoch or an RFC3339 string. Examples: `update_time = "2019-06-10T16:07:18-07:00"` `update_time = 1560208038000` &#42; create_time: `=`, `>`, `<`, `>=`, `<=` Usage: This should be milliseconds since epoch or an RFC3339 string. Examples: `create_time = "2019-06-10T16:07:18-07:00"` `create_time = 1560208038000` &#42; iam_policy.policy_blob: `=`, `:` &#42; resource_properties: `=`, `:`, `>`, `<`, `>=`, `<=` &#42; security_marks.marks: `=`, `:` &#42; security_center_properties.resource_name: `=`, `:` &#42; security_center_properties.resource_display_name: `=`, `:` &#42; security_center_properties.resource_type: `=`, `:` &#42; security_center_properties.resource_parent: `=`, `:` &#42; security_center_properties.resource_parent_display_name: `=`, `:` &#42; security_center_properties.resource_project: `=`, `:` &#42; security_center_properties.resource_project_display_name: `=`, `:` &#42; security_center_properties.resource_owners: `=`, `:` For example, `resource_properties.size = 100` is a valid filter string. Use a partial match on the empty string to filter based on a property existing: `resource_properties.my_property : ""` Use a negated partial match on the empty string to filter based on a property not existing: `-resource_properties.my_property : ""` */
		filter: Option[String] = None,
	  /** Required. Expression that defines what assets fields to use for grouping. The string value should follow SQL syntax: comma separated list of fields. For example: "security_center_properties.resource_project,security_center_properties.project". The following fields are supported when compare_duration is not set: &#42; security_center_properties.resource_project &#42; security_center_properties.resource_project_display_name &#42; security_center_properties.resource_type &#42; security_center_properties.resource_parent &#42; security_center_properties.resource_parent_display_name The following fields are supported when compare_duration is set: &#42; security_center_properties.resource_type &#42; security_center_properties.resource_project_display_name &#42; security_center_properties.resource_parent_display_name */
		groupBy: Option[String] = None,
	  /** When compare_duration is set, the GroupResult's "state_change" property is updated to indicate whether the asset was added, removed, or remained present during the compare_duration period of time that precedes the read_time. This is the time between (read_time - compare_duration) and read_time. The state change value is derived based on the presence of the asset at the two points in time. Intermediate state changes between the two times don't affect the result. For example, the results aren't affected if the asset is removed and re-created again. Possible "state_change" values when compare_duration is specified: &#42; "ADDED": indicates that the asset was not present at the start of compare_duration, but present at reference_time. &#42; "REMOVED": indicates that the asset was present at the start of compare_duration, but not present at reference_time. &#42; "ACTIVE": indicates that the asset was present at both the start and the end of the time period defined by compare_duration and reference_time. If compare_duration is not specified, then the only possible state_change is "UNUSED", which will be the state_change set for all assets present at read_time. If this field is set then `state_change` must be a specified field in `group_by`. */
		compareDuration: Option[String] = None,
	  /** Time used as a reference point when filtering assets. The filter is limited to assets existing at the supplied time and their values are those at that specific time. Absence of this field will default to the API's version of NOW. */
		readTime: Option[String] = None,
	  /** The value returned by the last `GroupAssetsResponse`; indicates that this is a continuation of a prior `GroupAssets` call, and that the system should return the next page of data. */
		pageToken: Option[String] = None,
	  /** The maximum number of results to return in a single response. Default is 10, minimum is 1, maximum is 1000. */
		pageSize: Option[Int] = None
	)
	
	case class GroupAssetsResponse(
	  /** Group results. There exists an element for each existing unique combination of property/values. The element contains a count for the number of times those specific property/values appear. */
		groupByResults: Option[List[Schema.GroupResult]] = None,
	  /** Time used for executing the groupBy request. */
		readTime: Option[String] = None,
	  /** Token to retrieve the next page of results, or empty if there are no more results. */
		nextPageToken: Option[String] = None,
	  /** The total number of results matching the query. */
		totalSize: Option[Int] = None
	)
	
	case class GroupResult(
	  /** Properties matching the groupBy fields in the request. */
		properties: Option[Map[String, JsValue]] = None,
	  /** Total count of resources for the given properties. */
		count: Option[String] = None
	)
	
	case class GroupFindingsRequest(
	  /** Expression that defines the filter to apply across findings. The expression is a list of one or more restrictions combined via logical operators `AND` and `OR`. Parentheses are supported, and `OR` has higher precedence than `AND`. Restrictions have the form ` ` and may have a `-` character in front of them to indicate negation. Examples include: &#42; name &#42; source_properties.a_property &#42; security_marks.marks.marka The supported operators are: &#42; `=` for all value types. &#42; `>`, `<`, `>=`, `<=` for integer values. &#42; `:`, meaning substring matching, for strings. The supported value types are: &#42; string literals in quotes. &#42; integer literals without quotes. &#42; boolean literals `true` and `false` without quotes. The following field and operator combinations are supported: &#42; name: `=` &#42; parent: `=`, `:` &#42; resource_name: `=`, `:` &#42; state: `=`, `:` &#42; category: `=`, `:` &#42; external_uri: `=`, `:` &#42; event_time: `=`, `>`, `<`, `>=`, `<=` Usage: This should be milliseconds since epoch or an RFC3339 string. Examples: `event_time = "2019-06-10T16:07:18-07:00"` `event_time = 1560208038000` &#42; severity: `=`, `:` &#42; workflow_state: `=`, `:` &#42; security_marks.marks: `=`, `:` &#42; source_properties: `=`, `:`, `>`, `<`, `>=`, `<=` For example, `source_properties.size = 100` is a valid filter string. Use a partial match on the empty string to filter based on a property existing: `source_properties.my_property : ""` Use a negated partial match on the empty string to filter based on a property not existing: `-source_properties.my_property : ""` &#42; resource: &#42; resource.name: `=`, `:` &#42; resource.parent_name: `=`, `:` &#42; resource.parent_display_name: `=`, `:` &#42; resource.project_name: `=`, `:` &#42; resource.project_display_name: `=`, `:` &#42; resource.type: `=`, `:` */
		filter: Option[String] = None,
	  /** Required. Expression that defines what assets fields to use for grouping (including `state_change`). The string value should follow SQL syntax: comma separated list of fields. For example: "parent,resource_name". The following fields are supported when compare_duration is set: &#42; state_change */
		groupBy: Option[String] = None,
	  /** Time used as a reference point when filtering findings. The filter is limited to findings existing at the supplied time and their values are those at that specific time. Absence of this field will default to the API's version of NOW. */
		readTime: Option[String] = None,
	  /** When compare_duration is set, the GroupResult's "state_change" attribute is updated to indicate whether the finding had its state changed, the finding's state remained unchanged, or if the finding was added during the compare_duration period of time that precedes the read_time. This is the time between (read_time - compare_duration) and read_time. The state_change value is derived based on the presence and state of the finding at the two points in time. Intermediate state changes between the two times don't affect the result. For example, the results aren't affected if the finding is made inactive and then active again. Possible "state_change" values when compare_duration is specified: &#42; "CHANGED": indicates that the finding was present and matched the given filter at the start of compare_duration, but changed its state at read_time. &#42; "UNCHANGED": indicates that the finding was present and matched the given filter at the start of compare_duration and did not change state at read_time. &#42; "ADDED": indicates that the finding did not match the given filter or was not present at the start of compare_duration, but was present at read_time. &#42; "REMOVED": indicates that the finding was present and matched the filter at the start of compare_duration, but did not match the filter at read_time. If compare_duration is not specified, then the only possible state_change is "UNUSED", which will be the state_change set for all findings present at read_time. If this field is set then `state_change` must be a specified field in `group_by`. */
		compareDuration: Option[String] = None,
	  /** The value returned by the last `GroupFindingsResponse`; indicates that this is a continuation of a prior `GroupFindings` call, and that the system should return the next page of data. */
		pageToken: Option[String] = None,
	  /** The maximum number of results to return in a single response. Default is 10, minimum is 1, maximum is 1000. */
		pageSize: Option[Int] = None
	)
	
	case class GroupFindingsResponse(
	  /** Group results. There exists an element for each existing unique combination of property/values. The element contains a count for the number of times those specific property/values appear. */
		groupByResults: Option[List[Schema.GroupResult]] = None,
	  /** Time used for executing the groupBy request. */
		readTime: Option[String] = None,
	  /** Token to retrieve the next page of results, or empty if there are no more results. */
		nextPageToken: Option[String] = None,
	  /** The total number of results matching the query. */
		totalSize: Option[Int] = None
	)
	
	case class ListAssetsResponse(
	  /** Assets matching the list request. */
		listAssetsResults: Option[List[Schema.ListAssetsResult]] = None,
	  /** Time used for executing the list request. */
		readTime: Option[String] = None,
	  /** Token to retrieve the next page of results, or empty if there are no more results. */
		nextPageToken: Option[String] = None,
	  /** The total number of assets matching the query. */
		totalSize: Option[Int] = None
	)
	
	object ListAssetsResult {
		enum StateChangeEnum extends Enum[StateChangeEnum] { case UNUSED, ADDED, REMOVED, ACTIVE }
	}
	case class ListAssetsResult(
	  /** Asset matching the search request. */
		asset: Option[Schema.Asset] = None,
	  /** State change of the asset between the points in time. */
		stateChange: Option[Schema.ListAssetsResult.StateChangeEnum] = None
	)
	
	case class Asset(
	  /** The relative resource name of this asset. See: https://cloud.google.com/apis/design/resource_names#relative_resource_name Example: "organizations/{organization_id}/assets/{asset_id}". */
		name: Option[String] = None,
	  /** Security Command Center managed properties. These properties are managed by Security Command Center and cannot be modified by the user. */
		securityCenterProperties: Option[Schema.SecurityCenterProperties] = None,
	  /** Resource managed properties. These properties are managed and defined by the Google Cloud resource and cannot be modified by the user. */
		resourceProperties: Option[Map[String, JsValue]] = None,
	  /** User specified security marks. These marks are entirely managed by the user and come from the SecurityMarks resource that belongs to the asset. */
		securityMarks: Option[Schema.SecurityMarks] = None,
	  /** The time at which the asset was created in Security Command Center. */
		createTime: Option[String] = None,
	  /** The time at which the asset was last updated or added in Cloud SCC. */
		updateTime: Option[String] = None,
	  /** Cloud IAM Policy information associated with the Google Cloud resource described by the Security Command Center asset. This information is managed and defined by the Google Cloud resource and cannot be modified by the user. */
		iamPolicy: Option[Schema.IamPolicy] = None,
	  /** The canonical name of the resource. It's either "organizations/{organization_id}/assets/{asset_id}", "folders/{folder_id}/assets/{asset_id}" or "projects/{project_number}/assets/{asset_id}", depending on the closest CRM ancestor of the resource. */
		canonicalName: Option[String] = None
	)
	
	case class SecurityCenterProperties(
	  /** The full resource name of the Google Cloud resource this asset represents. This field is immutable after create time. See: https://cloud.google.com/apis/design/resource_names#full_resource_name */
		resourceName: Option[String] = None,
	  /** The type of the Google Cloud resource. Examples include: APPLICATION, PROJECT, and ORGANIZATION. This is a case insensitive field defined by Security Command Center and/or the producer of the resource and is immutable after create time. */
		resourceType: Option[String] = None,
	  /** The full resource name of the immediate parent of the resource. See: https://cloud.google.com/apis/design/resource_names#full_resource_name */
		resourceParent: Option[String] = None,
	  /** The full resource name of the project the resource belongs to. See: https://cloud.google.com/apis/design/resource_names#full_resource_name */
		resourceProject: Option[String] = None,
	  /** Owners of the Google Cloud resource. */
		resourceOwners: Option[List[String]] = None,
	  /** The user defined display name for this resource. */
		resourceDisplayName: Option[String] = None,
	  /** The user defined display name for the parent of this resource. */
		resourceParentDisplayName: Option[String] = None,
	  /** The user defined display name for the project of this resource. */
		resourceProjectDisplayName: Option[String] = None,
	  /** Contains a Folder message for each folder in the assets ancestry. The first folder is the deepest nested folder, and the last folder is the folder directly under the Organization. */
		folders: Option[List[Schema.Folder]] = None
	)
	
	case class Folder(
	  /** Full resource name of this folder. See: https://cloud.google.com/apis/design/resource_names#full_resource_name */
		resourceFolder: Option[String] = None,
	  /** The user defined display name for this folder. */
		resourceFolderDisplayName: Option[String] = None
	)
	
	case class IamPolicy(
	  /** The JSON representation of the Policy associated with the asset. See https://cloud.google.com/iam/reference/rest/v1/Policy for format details. */
		policyBlob: Option[String] = None
	)
	
	case class ListDescendantSecurityHealthAnalyticsCustomModulesResponse(
	  /** Custom modules belonging to the requested parent and its descendants. */
		securityHealthAnalyticsCustomModules: Option[List[Schema.GoogleCloudSecuritycenterV1SecurityHealthAnalyticsCustomModule]] = None,
	  /** If not empty, indicates that there may be more custom modules to be returned. */
		nextPageToken: Option[String] = None
	)
	
	case class ListFindingsResponse(
	  /** Findings matching the list request. */
		listFindingsResults: Option[List[Schema.ListFindingsResult]] = None,
	  /** Time used for executing the list request. */
		readTime: Option[String] = None,
	  /** Token to retrieve the next page of results, or empty if there are no more results. */
		nextPageToken: Option[String] = None,
	  /** The total number of findings matching the query. */
		totalSize: Option[Int] = None
	)
	
	object ListFindingsResult {
		enum StateChangeEnum extends Enum[StateChangeEnum] { case UNUSED, CHANGED, UNCHANGED, ADDED, REMOVED }
	}
	case class ListFindingsResult(
	  /** Finding matching the search request. */
		finding: Option[Schema.Finding] = None,
	  /** State change of the finding between the points in time. */
		stateChange: Option[Schema.ListFindingsResult.StateChangeEnum] = None,
	  /** Output only. Resource that is associated with this finding. */
		resource: Option[Schema.Resource] = None
	)
	
	object Resource {
		enum CloudProviderEnum extends Enum[CloudProviderEnum] { case CLOUD_PROVIDER_UNSPECIFIED, GOOGLE_CLOUD_PLATFORM, AMAZON_WEB_SERVICES, MICROSOFT_AZURE }
	}
	case class Resource(
	  /** The full resource name of the resource. See: https://cloud.google.com/apis/design/resource_names#full_resource_name */
		name: Option[String] = None,
	  /** The human readable name of the resource. */
		displayName: Option[String] = None,
	  /** The full resource type of the resource. */
		`type`: Option[String] = None,
	  /** The full resource name of project that the resource belongs to. */
		projectName: Option[String] = None,
	  /** The project ID that the resource belongs to. */
		projectDisplayName: Option[String] = None,
	  /** The full resource name of resource's parent. */
		parentName: Option[String] = None,
	  /** The human readable name of resource's parent. */
		parentDisplayName: Option[String] = None,
	  /** Contains a Folder message for each folder in the assets ancestry. The first folder is the deepest nested folder, and the last folder is the folder directly under the Organization. */
		folders: Option[List[Schema.Folder]] = None,
	  /** Indicates which cloud provider the finding is from. */
		cloudProvider: Option[Schema.Resource.CloudProviderEnum] = None,
	  /** Indicates which organization / tenant the finding is for. */
		organization: Option[String] = None,
	  /** The service or resource provider associated with the resource. */
		service: Option[String] = None,
	  /** The region or location of the service (if applicable). */
		location: Option[String] = None,
	  /** The AWS metadata associated with the finding. */
		awsMetadata: Option[Schema.AwsMetadata] = None,
	  /** The Azure metadata associated with the finding. */
		azureMetadata: Option[Schema.AzureMetadata] = None,
	  /** Provides the path to the resource within the resource hierarchy. */
		resourcePath: Option[Schema.ResourcePath] = None,
	  /** A string representation of the resource path. For Google Cloud, it has the format of `org/{organization_id}/folder/{folder_id}/folder/{folder_id}/project/{project_id}` where there can be any number of folders. For AWS, it has the format of `org/{organization_id}/ou/{organizational_unit_id}/ou/{organizational_unit_id}/account/{account_id}` where there can be any number of organizational units. For Azure, it has the format of `mg/{management_group_id}/mg/{management_group_id}/subscription/{subscription_id}/rg/{resource_group_name}` where there can be any number of management groups. */
		resourcePathString: Option[String] = None
	)
	
	case class AwsMetadata(
	  /** The AWS organization associated with the resource. */
		organization: Option[Schema.AwsOrganization] = None,
	  /** A list of AWS organizational units associated with the resource, ordered from lowest level (closest to the account) to highest level. */
		organizationalUnits: Option[List[Schema.AwsOrganizationalUnit]] = None,
	  /** The AWS account associated with the resource. */
		account: Option[Schema.AwsAccount] = None
	)
	
	case class AwsOrganization(
	  /** The unique identifier (ID) for the organization. The regex pattern for an organization ID string requires "o-" followed by from 10 to 32 lowercase letters or digits. */
		id: Option[String] = None
	)
	
	case class AwsOrganizationalUnit(
	  /** The unique identifier (ID) associated with this OU. The regex pattern for an organizational unit ID string requires "ou-" followed by from 4 to 32 lowercase letters or digits (the ID of the root that contains the OU). This string is followed by a second "-" dash and from 8 to 32 additional lowercase letters or digits. For example, "ou-ab12-cd34ef56". */
		id: Option[String] = None,
	  /** The friendly name of the OU. */
		name: Option[String] = None
	)
	
	case class AwsAccount(
	  /** The unique identifier (ID) of the account, containing exactly 12 digits. */
		id: Option[String] = None,
	  /** The friendly name of this account. */
		name: Option[String] = None
	)
	
	case class AzureMetadata(
	  /** A list of Azure management groups associated with the resource, ordered from lowest level (closest to the subscription) to highest level. */
		managementGroups: Option[List[Schema.AzureManagementGroup]] = None,
	  /** The Azure subscription associated with the resource. */
		subscription: Option[Schema.AzureSubscription] = None,
	  /** The Azure resource group associated with the resource. */
		resourceGroup: Option[Schema.AzureResourceGroup] = None,
	  /** The Azure Entra tenant associated with the resource. */
		tenant: Option[Schema.AzureTenant] = None
	)
	
	case class AzureManagementGroup(
	  /** The UUID of the Azure management group, for example, `20000000-0001-0000-0000-000000000000`. */
		id: Option[String] = None,
	  /** The display name of the Azure management group. */
		displayName: Option[String] = None
	)
	
	case class AzureSubscription(
	  /** The UUID of the Azure subscription, for example, `291bba3f-e0a5-47bc-a099-3bdcb2a50a05`. */
		id: Option[String] = None,
	  /** The display name of the Azure subscription. */
		displayName: Option[String] = None
	)
	
	case class AzureResourceGroup(
	  /** The ID of the Azure resource group. */
		id: Option[String] = None,
	  /** The name of the Azure resource group. This is not a UUID. */
		name: Option[String] = None
	)
	
	case class AzureTenant(
	  /** The ID of the Microsoft Entra tenant, for example, "a11aaa11-aa11-1aa1-11aa-1aaa11a". */
		id: Option[String] = None,
	  /** The display name of the Azure tenant. */
		displayName: Option[String] = None
	)
	
	case class ResourcePath(
	  /** The list of nodes that make the up resource path, ordered from lowest level to highest level. */
		nodes: Option[List[Schema.ResourcePathNode]] = None
	)
	
	object ResourcePathNode {
		enum NodeTypeEnum extends Enum[NodeTypeEnum] { case RESOURCE_PATH_NODE_TYPE_UNSPECIFIED, GCP_ORGANIZATION, GCP_FOLDER, GCP_PROJECT, AWS_ORGANIZATION, AWS_ORGANIZATIONAL_UNIT, AWS_ACCOUNT, AZURE_MANAGEMENT_GROUP, AZURE_SUBSCRIPTION, AZURE_RESOURCE_GROUP }
	}
	case class ResourcePathNode(
	  /** The type of resource this node represents. */
		nodeType: Option[Schema.ResourcePathNode.NodeTypeEnum] = None,
	  /** The ID of the resource this node represents. */
		id: Option[String] = None,
	  /** The display name of the resource this node represents. */
		displayName: Option[String] = None
	)
	
	case class ListMuteConfigsResponse(
	  /** The mute configs from the specified parent. */
		muteConfigs: Option[List[Schema.GoogleCloudSecuritycenterV1MuteConfig]] = None,
	  /** A token, which can be sent as `page_token` to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None
	)
	
	case class ListNotificationConfigsResponse(
	  /** Notification configs belonging to the requested parent. */
		notificationConfigs: Option[List[Schema.NotificationConfig]] = None,
	  /** Token to retrieve the next page of results, or empty if there are no more results. */
		nextPageToken: Option[String] = None
	)
	
	case class ListEffectiveSecurityHealthAnalyticsCustomModulesResponse(
	  /** Effective custom modules belonging to the requested parent. */
		effectiveSecurityHealthAnalyticsCustomModules: Option[List[Schema.GoogleCloudSecuritycenterV1EffectiveSecurityHealthAnalyticsCustomModule]] = None,
	  /** If not empty, indicates that there may be more effective custom modules to be returned. */
		nextPageToken: Option[String] = None
	)
	
	case class ListSecurityHealthAnalyticsCustomModulesResponse(
	  /** Custom modules belonging to the requested parent. */
		securityHealthAnalyticsCustomModules: Option[List[Schema.GoogleCloudSecuritycenterV1SecurityHealthAnalyticsCustomModule]] = None,
	  /** If not empty, indicates that there may be more custom modules to be returned. */
		nextPageToken: Option[String] = None
	)
	
	case class ListSourcesResponse(
	  /** Sources belonging to the requested parent. */
		sources: Option[List[Schema.Source]] = None,
	  /** Token to retrieve the next page of results, or empty if there are no more results. */
		nextPageToken: Option[String] = None
	)
	
	case class RunAssetDiscoveryRequest(
	
	)
	
	object SetFindingStateRequest {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, ACTIVE, INACTIVE }
	}
	case class SetFindingStateRequest(
	  /** Required. The desired State of the finding. */
		state: Option[Schema.SetFindingStateRequest.StateEnum] = None,
	  /** Optional. The time at which the updated state takes effect. If unset, defaults to the request time. */
		startTime: Option[String] = None
	)
	
	object SetMuteRequest {
		enum MuteEnum extends Enum[MuteEnum] { case MUTE_UNSPECIFIED, MUTED, UNMUTED, UNDEFINED }
	}
	case class SetMuteRequest(
	  /** Required. The desired state of the Mute. */
		mute: Option[Schema.SetMuteRequest.MuteEnum] = None
	)
	
	case class SetIamPolicyRequest(
	  /** REQUIRED: The complete policy to be applied to the `resource`. The size of the policy is limited to a few 10s of KB. An empty policy is a valid policy but certain Google Cloud services (such as Projects) might reject them. */
		policy: Option[Schema.Policy] = None,
	  /** OPTIONAL: A FieldMask specifying which fields of the policy to modify. Only the fields in the mask will be modified. If no mask is provided, the following default mask is used: `paths: "bindings, etag"` */
		updateMask: Option[String] = None
	)
	
	case class TestIamPermissionsRequest(
	  /** The set of permissions to check for the `resource`. Permissions with wildcards (such as `&#42;` or `storage.&#42;`) are not allowed. For more information see [IAM Overview](https://cloud.google.com/iam/docs/overview#permissions). */
		permissions: Option[List[String]] = None
	)
	
	case class TestIamPermissionsResponse(
	  /** A subset of `TestPermissionsRequest.permissions` that the caller is allowed. */
		permissions: Option[List[String]] = None
	)
	
	case class SimulateSecurityHealthAnalyticsCustomModuleRequest(
	  /** Required. The custom configuration that you need to test. */
		customConfig: Option[Schema.GoogleCloudSecuritycenterV1CustomConfig] = None,
	  /** Required. Resource data to simulate custom module against. */
		resource: Option[Schema.SimulatedResource] = None
	)
	
	case class SimulatedResource(
	  /** Required. The type of the resource, for example, `compute.googleapis.com/Disk`. */
		resourceType: Option[String] = None,
	  /** Optional. A representation of the Google Cloud resource. Should match the Google Cloud resource JSON format. */
		resourceData: Option[Map[String, JsValue]] = None,
	  /** Optional. A representation of the IAM policy. */
		iamPolicyData: Option[Schema.Policy] = None
	)
	
	case class SimulateSecurityHealthAnalyticsCustomModuleResponse(
	  /** Result for test case in the corresponding request. */
		result: Option[Schema.SimulatedResult] = None
	)
	
	case class SimulatedResult(
	  /** Finding that would be published for the test case, if a violation is detected. */
		finding: Option[Schema.Finding] = None,
	  /** Indicates that the test case does not trigger any violation. */
		noViolation: Option[Schema.Empty] = None,
	  /** Error encountered during the test. */
		error: Option[Schema.Status] = None
	)
	
	case class ListBigQueryExportsResponse(
	  /** The BigQuery exports from the specified parent. */
		bigQueryExports: Option[List[Schema.GoogleCloudSecuritycenterV1BigQueryExport]] = None,
	  /** A token, which can be sent as `page_token` to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None
	)
	
	object EventThreatDetectionCustomModule {
		enum EnablementStateEnum extends Enum[EnablementStateEnum] { case ENABLEMENT_STATE_UNSPECIFIED, ENABLED, DISABLED, INHERITED }
		enum CloudProviderEnum extends Enum[CloudProviderEnum] { case CLOUD_PROVIDER_UNSPECIFIED, GOOGLE_CLOUD_PLATFORM, AMAZON_WEB_SERVICES, MICROSOFT_AZURE }
	}
	case class EventThreatDetectionCustomModule(
	  /** Immutable. The resource name of the Event Threat Detection custom module. Its format is: &#42; `organizations/{organization}/eventThreatDetectionSettings/customModules/{module}`. &#42; `folders/{folder}/eventThreatDetectionSettings/customModules/{module}`. &#42; `projects/{project}/eventThreatDetectionSettings/customModules/{module}`. */
		name: Option[String] = None,
	  /** Config for the module. For the resident module, its config value is defined at this level. For the inherited module, its config value is inherited from the ancestor module. */
		config: Option[Map[String, JsValue]] = None,
	  /** Output only. The closest ancestor module that this module inherits the enablement state from. The format is the same as the EventThreatDetectionCustomModule resource name. */
		ancestorModule: Option[String] = None,
	  /** The state of enablement for the module at the given level of the hierarchy. */
		enablementState: Option[Schema.EventThreatDetectionCustomModule.EnablementStateEnum] = None,
	  /** Type for the module. e.g. CONFIGURABLE_BAD_IP. */
		`type`: Option[String] = None,
	  /** The human readable name to be displayed for the module. */
		displayName: Option[String] = None,
	  /** The description for the module. */
		description: Option[String] = None,
	  /** Output only. The time the module was last updated. */
		updateTime: Option[String] = None,
	  /** Output only. The editor the module was last updated by. */
		lastEditor: Option[String] = None,
	  /** The cloud provider of the custom module. */
		cloudProvider: Option[Schema.EventThreatDetectionCustomModule.CloudProviderEnum] = None
	)
	
	case class ListDescendantEventThreatDetectionCustomModulesResponse(
	  /** Custom modules belonging to the requested parent. */
		eventThreatDetectionCustomModules: Option[List[Schema.EventThreatDetectionCustomModule]] = None,
	  /** A token, which can be sent as `page_token` to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None
	)
	
	case class ListEventThreatDetectionCustomModulesResponse(
	  /** Custom modules belonging to the requested parent. */
		eventThreatDetectionCustomModules: Option[List[Schema.EventThreatDetectionCustomModule]] = None,
	  /** A token, which can be sent as `page_token` to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None
	)
	
	case class ValidateEventThreatDetectionCustomModuleRequest(
	  /** Required. The raw text of the module's contents. Used to generate error messages. */
		rawText: Option[String] = None,
	  /** Required. The type of the module (e.g. CONFIGURABLE_BAD_IP). */
		`type`: Option[String] = None
	)
	
	case class ValidateEventThreatDetectionCustomModuleResponse(
	  /** A list of errors returned by the validator. If the list is empty, there were no errors. */
		errors: Option[Schema.CustomModuleValidationErrors] = None
	)
	
	case class CustomModuleValidationErrors(
		errors: Option[List[Schema.CustomModuleValidationError]] = None
	)
	
	case class CustomModuleValidationError(
	  /** A description of the error, suitable for human consumption. Required. */
		description: Option[String] = None,
	  /** The path, in RFC 8901 JSON Pointer format, to the field that failed validation. This may be left empty if no specific field is affected. */
		fieldPath: Option[String] = None,
	  /** The initial position of the error in the uploaded text version of the module. This field may be omitted if no specific position applies, or if one could not be computed. */
		start: Option[Schema.Position] = None,
	  /** The end position of the error in the uploaded text version of the module. This field may be omitted if no specific position applies, or if one could not be computed.. */
		end: Option[Schema.Position] = None
	)
	
	case class Position(
		lineNumber: Option[Int] = None,
		columnNumber: Option[Int] = None
	)
	
	object EffectiveEventThreatDetectionCustomModule {
		enum EnablementStateEnum extends Enum[EnablementStateEnum] { case ENABLEMENT_STATE_UNSPECIFIED, ENABLED, DISABLED }
		enum CloudProviderEnum extends Enum[CloudProviderEnum] { case CLOUD_PROVIDER_UNSPECIFIED, GOOGLE_CLOUD_PLATFORM, AMAZON_WEB_SERVICES, MICROSOFT_AZURE }
	}
	case class EffectiveEventThreatDetectionCustomModule(
	  /** Output only. The resource name of the effective ETD custom module. Its format is: &#42; `organizations/{organization}/eventThreatDetectionSettings/effectiveCustomModules/{module}`. &#42; `folders/{folder}/eventThreatDetectionSettings/effectiveCustomModules/{module}`. &#42; `projects/{project}/eventThreatDetectionSettings/effectiveCustomModules/{module}`. */
		name: Option[String] = None,
	  /** Output only. Config for the effective module. */
		config: Option[Map[String, JsValue]] = None,
	  /** Output only. The effective state of enablement for the module at the given level of the hierarchy. */
		enablementState: Option[Schema.EffectiveEventThreatDetectionCustomModule.EnablementStateEnum] = None,
	  /** Output only. Type for the module. e.g. CONFIGURABLE_BAD_IP. */
		`type`: Option[String] = None,
	  /** Output only. The human readable name to be displayed for the module. */
		displayName: Option[String] = None,
	  /** Output only. The description for the module. */
		description: Option[String] = None,
	  /** The cloud provider of the custom module. */
		cloudProvider: Option[Schema.EffectiveEventThreatDetectionCustomModule.CloudProviderEnum] = None
	)
	
	case class ListEffectiveEventThreatDetectionCustomModulesResponse(
	  /** Effective custom modules belonging to the requested parent. */
		effectiveEventThreatDetectionCustomModules: Option[List[Schema.EffectiveEventThreatDetectionCustomModule]] = None,
	  /** A token, which can be sent as `page_token` to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None
	)
	
	case class BatchCreateResourceValueConfigsRequest(
	  /** Required. The resource value configs to be created. */
		requests: Option[List[Schema.CreateResourceValueConfigRequest]] = None
	)
	
	case class CreateResourceValueConfigRequest(
	  /** Required. Resource name of the new ResourceValueConfig's parent. */
		parent: Option[String] = None,
	  /** Required. The resource value config being created. */
		resourceValueConfig: Option[Schema.GoogleCloudSecuritycenterV1ResourceValueConfig] = None
	)
	
	object GoogleCloudSecuritycenterV1ResourceValueConfig {
		enum ResourceValueEnum extends Enum[ResourceValueEnum] { case RESOURCE_VALUE_UNSPECIFIED, HIGH, MEDIUM, LOW, NONE }
		enum CloudProviderEnum extends Enum[CloudProviderEnum] { case CLOUD_PROVIDER_UNSPECIFIED, GOOGLE_CLOUD_PLATFORM, AMAZON_WEB_SERVICES, MICROSOFT_AZURE }
	}
	case class GoogleCloudSecuritycenterV1ResourceValueConfig(
	  /** Name for the resource value configuration */
		name: Option[String] = None,
	  /** Required. Resource value level this expression represents */
		resourceValue: Option[Schema.GoogleCloudSecuritycenterV1ResourceValueConfig.ResourceValueEnum] = None,
	  /** Required. Tag values combined with `AND` to check against. For Google Cloud resources, they are tag value IDs in the form of "tagValues/123". Example: `[ "tagValues/123", "tagValues/456", "tagValues/789" ]` https://cloud.google.com/resource-manager/docs/tags/tags-creating-and-managing */
		tagValues: Option[List[String]] = None,
	  /** Apply resource_value only to resources that match resource_type. resource_type will be checked with `AND` of other resources. For example, "storage.googleapis.com/Bucket" with resource_value "HIGH" will apply "HIGH" value only to "storage.googleapis.com/Bucket" resources. */
		resourceType: Option[String] = None,
	  /** Project or folder to scope this configuration to. For example, "project/456" would apply this configuration only to resources in "project/456" scope will be checked with `AND` of other resources. */
		scope: Option[String] = None,
	  /** List of resource labels to search for, evaluated with `AND`. For example, `"resource_labels_selector": {"key": "value", "env": "prod"}` will match resources with labels "key": "value" `AND` "env": "prod" https://cloud.google.com/resource-manager/docs/creating-managing-labels */
		resourceLabelsSelector: Option[Map[String, String]] = None,
	  /** Description of the resource value configuration. */
		description: Option[String] = None,
	  /** Output only. Timestamp this resource value configuration was created. */
		createTime: Option[String] = None,
	  /** Output only. Timestamp this resource value configuration was last updated. */
		updateTime: Option[String] = None,
	  /** Cloud provider this configuration applies to */
		cloudProvider: Option[Schema.GoogleCloudSecuritycenterV1ResourceValueConfig.CloudProviderEnum] = None,
	  /** A mapping of the sensitivity on Sensitive Data Protection finding to resource values. This mapping can only be used in combination with a resource_type that is related to BigQuery, e.g. "bigquery.googleapis.com/Dataset". */
		sensitiveDataProtectionMapping: Option[Schema.GoogleCloudSecuritycenterV1SensitiveDataProtectionMapping] = None
	)
	
	object GoogleCloudSecuritycenterV1SensitiveDataProtectionMapping {
		enum HighSensitivityMappingEnum extends Enum[HighSensitivityMappingEnum] { case RESOURCE_VALUE_UNSPECIFIED, HIGH, MEDIUM, LOW, NONE }
		enum MediumSensitivityMappingEnum extends Enum[MediumSensitivityMappingEnum] { case RESOURCE_VALUE_UNSPECIFIED, HIGH, MEDIUM, LOW, NONE }
	}
	case class GoogleCloudSecuritycenterV1SensitiveDataProtectionMapping(
	  /** Resource value mapping for high-sensitivity Sensitive Data Protection findings */
		highSensitivityMapping: Option[Schema.GoogleCloudSecuritycenterV1SensitiveDataProtectionMapping.HighSensitivityMappingEnum] = None,
	  /** Resource value mapping for medium-sensitivity Sensitive Data Protection findings */
		mediumSensitivityMapping: Option[Schema.GoogleCloudSecuritycenterV1SensitiveDataProtectionMapping.MediumSensitivityMappingEnum] = None
	)
	
	case class BatchCreateResourceValueConfigsResponse(
	  /** The resource value configs created */
		resourceValueConfigs: Option[List[Schema.GoogleCloudSecuritycenterV1ResourceValueConfig]] = None
	)
	
	case class ListResourceValueConfigsResponse(
	  /** The resource value configs from the specified parent. */
		resourceValueConfigs: Option[List[Schema.GoogleCloudSecuritycenterV1ResourceValueConfig]] = None,
	  /** A token, which can be sent as `page_token` to retrieve the next page. If this field is empty, there are no subsequent pages. */
		nextPageToken: Option[String] = None
	)
	
	case class ListValuedResourcesResponse(
	  /** The valued resources that the attack path simulation identified. */
		valuedResources: Option[List[Schema.ValuedResource]] = None,
	  /** Token to retrieve the next page of results, or empty if there are no more results. */
		nextPageToken: Option[String] = None,
	  /** The estimated total number of results matching the query. */
		totalSize: Option[Int] = None
	)
	
	case class ListAttackPathsResponse(
	  /** The attack paths that the attack path simulation identified. */
		attackPaths: Option[List[Schema.AttackPath]] = None,
	  /** Token to retrieve the next page of results, or empty if there are no more results. */
		nextPageToken: Option[String] = None
	)
	
	case class AttackPath(
	  /** The attack path name, for example, `organizations/12/simulation/34/valuedResources/56/attackPaths/78` */
		name: Option[String] = None,
	  /** A list of nodes that exist in this attack path. */
		pathNodes: Option[List[Schema.AttackPathNode]] = None,
	  /** A list of the edges between nodes in this attack path. */
		edges: Option[List[Schema.AttackPathEdge]] = None
	)
	
	case class AttackPathNode(
	  /** The name of the resource at this point in the attack path. The format of the name follows the Cloud Asset Inventory [resource name format](https://cloud.google.com/asset-inventory/docs/resource-name-format) */
		resource: Option[String] = None,
	  /** The [supported resource type](https://cloud.google.com/asset-inventory/docs/supported-asset-types) */
		resourceType: Option[String] = None,
	  /** Human-readable name of this resource. */
		displayName: Option[String] = None,
	  /** The findings associated with this node in the attack path. */
		associatedFindings: Option[List[Schema.PathNodeAssociatedFinding]] = None,
	  /** Unique id of the attack path node. */
		uuid: Option[String] = None,
	  /** A list of attack step nodes that exist in this attack path node. */
		attackSteps: Option[List[Schema.AttackStepNode]] = None
	)
	
	case class PathNodeAssociatedFinding(
	  /** Canonical name of the associated findings. Example: `organizations/123/sources/456/findings/789` */
		canonicalFinding: Option[String] = None,
	  /** The additional taxonomy group within findings from a given source. */
		findingCategory: Option[String] = None,
	  /** Full resource name of the finding. */
		name: Option[String] = None
	)
	
	object AttackStepNode {
		enum TypeEnum extends Enum[TypeEnum] { case NODE_TYPE_UNSPECIFIED, NODE_TYPE_AND, NODE_TYPE_OR, NODE_TYPE_DEFENSE, NODE_TYPE_ATTACKER }
	}
	case class AttackStepNode(
	  /** Unique ID for one Node */
		uuid: Option[String] = None,
	  /** Attack step type. Can be either AND, OR or DEFENSE */
		`type`: Option[Schema.AttackStepNode.TypeEnum] = None,
	  /** User friendly name of the attack step */
		displayName: Option[String] = None,
	  /** Attack step labels for metadata */
		labels: Option[Map[String, String]] = None,
	  /** Attack step description */
		description: Option[String] = None
	)
	
	case class AttackPathEdge(
	  /** The attack node uuid of the source node. */
		source: Option[String] = None,
	  /** The attack node uuid of the destination node. */
		destination: Option[String] = None
	)
	
	object GoogleCloudSecuritycenterV1beta1RunAssetDiscoveryResponse {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, COMPLETED, SUPERSEDED, TERMINATED }
	}
	case class GoogleCloudSecuritycenterV1beta1RunAssetDiscoveryResponse(
	  /** The state of an asset discovery run. */
		state: Option[Schema.GoogleCloudSecuritycenterV1beta1RunAssetDiscoveryResponse.StateEnum] = None,
	  /** The duration between asset discovery run start and end */
		duration: Option[String] = None
	)
	
	case class GoogleCloudSecuritycenterV1BulkMuteFindingsResponse(
	
	)
	
	case class GoogleCloudSecuritycenterV1NotificationMessage(
	  /** Name of the notification config that generated current notification. */
		notificationConfigName: Option[String] = None,
	  /** If it's a Finding based notification config, this field will be populated. */
		finding: Option[Schema.Finding] = None,
	  /** The Cloud resource tied to this notification's Finding. */
		resource: Option[Schema.GoogleCloudSecuritycenterV1Resource] = None
	)
	
	object GoogleCloudSecuritycenterV1Resource {
		enum CloudProviderEnum extends Enum[CloudProviderEnum] { case CLOUD_PROVIDER_UNSPECIFIED, GOOGLE_CLOUD_PLATFORM, AMAZON_WEB_SERVICES, MICROSOFT_AZURE }
	}
	case class GoogleCloudSecuritycenterV1Resource(
	  /** The full resource name of the resource. See: https://cloud.google.com/apis/design/resource_names#full_resource_name */
		name: Option[String] = None,
	  /** The human readable name of the resource. */
		displayName: Option[String] = None,
	  /** The full resource type of the resource. */
		`type`: Option[String] = None,
	  /** The full resource name of project that the resource belongs to. */
		project: Option[String] = None,
	  /** The project ID that the resource belongs to. */
		projectDisplayName: Option[String] = None,
	  /** The full resource name of resource's parent. */
		parent: Option[String] = None,
	  /** The human readable name of resource's parent. */
		parentDisplayName: Option[String] = None,
	  /** Output only. Contains a Folder message for each folder in the assets ancestry. The first folder is the deepest nested folder, and the last folder is the folder directly under the Organization. */
		folders: Option[List[Schema.Folder]] = None,
	  /** Indicates which cloud provider the resource resides in. */
		cloudProvider: Option[Schema.GoogleCloudSecuritycenterV1Resource.CloudProviderEnum] = None,
	  /** Indicates which organization or tenant in the cloud provider the finding applies to. */
		organization: Option[String] = None,
	  /** The parent service or product from which the resource is provided, for example, GKE or SNS. */
		service: Option[String] = None,
	  /** The region or location of the service (if applicable). */
		location: Option[String] = None,
	  /** The AWS metadata associated with the finding. */
		awsMetadata: Option[Schema.AwsMetadata] = None,
	  /** The Azure metadata associated with the finding. */
		azureMetadata: Option[Schema.AzureMetadata] = None,
	  /** Provides the path to the resource within the resource hierarchy. */
		resourcePath: Option[Schema.ResourcePath] = None,
	  /** A string representation of the resource path. For Google Cloud, it has the format of `organizations/{organization_id}/folders/{folder_id}/folders/{folder_id}/projects/{project_id}` where there can be any number of folders. For AWS, it has the format of `org/{organization_id}/ou/{organizational_unit_id}/ou/{organizational_unit_id}/account/{account_id}` where there can be any number of organizational units. For Azure, it has the format of `mg/{management_group_id}/mg/{management_group_id}/subscription/{subscription_id}/rg/{resource_group_name}` where there can be any number of management groups. */
		resourcePathString: Option[String] = None
	)
	
	object GoogleCloudSecuritycenterV1RunAssetDiscoveryResponse {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, COMPLETED, SUPERSEDED, TERMINATED }
	}
	case class GoogleCloudSecuritycenterV1RunAssetDiscoveryResponse(
	  /** The state of an asset discovery run. */
		state: Option[Schema.GoogleCloudSecuritycenterV1RunAssetDiscoveryResponse.StateEnum] = None,
	  /** The duration between asset discovery run start and end */
		duration: Option[String] = None
	)
	
	case class GoogleCloudSecuritycenterV2BulkMuteFindingsResponse(
	
	)
	
	case class GoogleCloudSecuritycenterV2ExternalSystem(
	  /** Full resource name of the external system. The following list shows some examples: + `organizations/1234/sources/5678/findings/123456/externalSystems/jira` + `organizations/1234/sources/5678/locations/us/findings/123456/externalSystems/jira` + `folders/1234/sources/5678/findings/123456/externalSystems/jira` + `folders/1234/sources/5678/locations/us/findings/123456/externalSystems/jira` + `projects/1234/sources/5678/findings/123456/externalSystems/jira` + `projects/1234/sources/5678/locations/us/findings/123456/externalSystems/jira` */
		name: Option[String] = None,
	  /** References primary/secondary etc assignees in the external system. */
		assignees: Option[List[String]] = None,
	  /** The identifier that's used to track the finding's corresponding case in the external system. */
		externalUid: Option[String] = None,
	  /** The most recent status of the finding's corresponding case, as reported by the external system. */
		status: Option[String] = None,
	  /** The time when the case was last updated, as reported by the external system. */
		externalSystemUpdateTime: Option[String] = None,
	  /** The link to the finding's corresponding case in the external system. */
		caseUri: Option[String] = None,
	  /** The priority of the finding's corresponding case in the external system. */
		casePriority: Option[String] = None,
	  /** The SLA of the finding's corresponding case in the external system. */
		caseSla: Option[String] = None,
	  /** The time when the case was created, as reported by the external system. */
		caseCreateTime: Option[String] = None,
	  /** The time when the case was closed, as reported by the external system. */
		caseCloseTime: Option[String] = None,
	  /** Information about the ticket, if any, that is being used to track the resolution of the issue that is identified by this finding. */
		ticketInfo: Option[Schema.GoogleCloudSecuritycenterV2TicketInfo] = None
	)
	
	case class GoogleCloudSecuritycenterV2TicketInfo(
	  /** The identifier of the ticket in the ticket system. */
		id: Option[String] = None,
	  /** The assignee of the ticket in the ticket system. */
		assignee: Option[String] = None,
	  /** The description of the ticket in the ticket system. */
		description: Option[String] = None,
	  /** The link to the ticket in the ticket system. */
		uri: Option[String] = None,
	  /** The latest status of the ticket, as reported by the ticket system. */
		status: Option[String] = None,
	  /** The time when the ticket was last updated, as reported by the ticket system. */
		updateTime: Option[String] = None
	)
	
	object GoogleCloudSecuritycenterV2MuteConfig {
		enum TypeEnum extends Enum[TypeEnum] { case MUTE_CONFIG_TYPE_UNSPECIFIED, STATIC, DYNAMIC }
	}
	case class GoogleCloudSecuritycenterV2MuteConfig(
	  /** Identifier. This field will be ignored if provided on config creation. The following list shows some examples of the format: + `organizations/{organization}/muteConfigs/{mute_config}` + `organizations/{organization}locations/{location}//muteConfigs/{mute_config}` + `folders/{folder}/muteConfigs/{mute_config}` + `folders/{folder}/locations/{location}/muteConfigs/{mute_config}` + `projects/{project}/muteConfigs/{mute_config}` + `projects/{project}/locations/{location}/muteConfigs/{mute_config}` */
		name: Option[String] = None,
	  /** A description of the mute config. */
		description: Option[String] = None,
	  /** Required. An expression that defines the filter to apply across create/update events of findings. While creating a filter string, be mindful of the scope in which the mute configuration is being created. E.g., If a filter contains project = X but is created under the project = Y scope, it might not match any findings. The following field and operator combinations are supported: &#42; severity: `=`, `:` &#42; category: `=`, `:` &#42; resource.name: `=`, `:` &#42; resource.project_name: `=`, `:` &#42; resource.project_display_name: `=`, `:` &#42; resource.folders.resource_folder: `=`, `:` &#42; resource.parent_name: `=`, `:` &#42; resource.parent_display_name: `=`, `:` &#42; resource.type: `=`, `:` &#42; finding_class: `=`, `:` &#42; indicator.ip_addresses: `=`, `:` &#42; indicator.domains: `=`, `:` */
		filter: Option[String] = None,
	  /** Output only. The time at which the mute config was created. This field is set by the server and will be ignored if provided on config creation. */
		createTime: Option[String] = None,
	  /** Output only. The most recent time at which the mute config was updated. This field is set by the server and will be ignored if provided on config creation or update. */
		updateTime: Option[String] = None,
	  /** Output only. Email address of the user who last edited the mute config. This field is set by the server and will be ignored if provided on config creation or update. */
		mostRecentEditor: Option[String] = None,
	  /** Required. The type of the mute config, which determines what type of mute state the config affects. Immutable after creation. */
		`type`: Option[Schema.GoogleCloudSecuritycenterV2MuteConfig.TypeEnum] = None,
	  /** Optional. The expiry of the mute config. Only applicable for dynamic configs. If the expiry is set, when the config expires, it is removed from all findings. */
		expiryTime: Option[String] = None
	)
	
	case class GoogleCloudSecuritycenterV2BigQueryExport(
	  /** Identifier. The relative resource name of this export. See: https://cloud.google.com/apis/design/resource_names#relative_resource_name. The following list shows some examples: + `organizations/{organization_id}/locations/{location_id}/bigQueryExports/{export_id}` + `folders/{folder_id}/locations/{location_id}/bigQueryExports/{export_id}` + `projects/{project_id}/locations/{location_id}/bigQueryExports/{export_id}` This field is provided in responses, and is ignored when provided in create requests. */
		name: Option[String] = None,
	  /** The description of the export (max of 1024 characters). */
		description: Option[String] = None,
	  /** Expression that defines the filter to apply across create/update events of findings. The expression is a list of zero or more restrictions combined via logical operators `AND` and `OR`. Parentheses are supported, and `OR` has higher precedence than `AND`. Restrictions have the form ` ` and may have a `-` character in front of them to indicate negation. The fields map to those defined in the corresponding resource. The supported operators are: &#42; `=` for all value types. &#42; `>`, `<`, `>=`, `<=` for integer values. &#42; `:`, meaning substring matching, for strings. The supported value types are: &#42; string literals in quotes. &#42; integer literals without quotes. &#42; boolean literals `true` and `false` without quotes. */
		filter: Option[String] = None,
	  /** The dataset to write findings' updates to. Its format is "projects/[project_id]/datasets/[bigquery_dataset_id]". BigQuery dataset unique ID must contain only letters (a-z, A-Z), numbers (0-9), or underscores (_). */
		dataset: Option[String] = None,
	  /** Output only. The time at which the BigQuery export was created. This field is set by the server and will be ignored if provided on export on creation. */
		createTime: Option[String] = None,
	  /** Output only. The most recent time at which the BigQuery export was updated. This field is set by the server and will be ignored if provided on export creation or update. */
		updateTime: Option[String] = None,
	  /** Output only. Email address of the user who last edited the BigQuery export. This field is set by the server and will be ignored if provided on export creation or update. */
		mostRecentEditor: Option[String] = None,
	  /** Output only. The service account that needs permission to create table and upload data to the BigQuery dataset. */
		principal: Option[String] = None
	)
	
	case class GoogleCloudSecuritycenterV2NotificationMessage(
	  /** Name of the notification config that generated current notification. */
		notificationConfigName: Option[String] = None,
	  /** If it's a Finding based notification config, this field will be populated. */
		finding: Option[Schema.GoogleCloudSecuritycenterV2Finding] = None,
	  /** The Cloud resource tied to this notification's Finding. */
		resource: Option[Schema.GoogleCloudSecuritycenterV2Resource] = None
	)
	
	object GoogleCloudSecuritycenterV2Finding {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, ACTIVE, INACTIVE }
		enum SeverityEnum extends Enum[SeverityEnum] { case SEVERITY_UNSPECIFIED, CRITICAL, HIGH, MEDIUM, LOW }
		enum MuteEnum extends Enum[MuteEnum] { case MUTE_UNSPECIFIED, MUTED, UNMUTED, UNDEFINED }
		enum FindingClassEnum extends Enum[FindingClassEnum] { case FINDING_CLASS_UNSPECIFIED, THREAT, VULNERABILITY, MISCONFIGURATION, OBSERVATION, SCC_ERROR, POSTURE_VIOLATION, TOXIC_COMBINATION, SENSITIVE_DATA_RISK }
	}
	case class GoogleCloudSecuritycenterV2Finding(
	  /** The [relative resource name](https://cloud.google.com/apis/design/resource_names#relative_resource_name) of the finding. The following list shows some examples: + `organizations/{organization_id}/sources/{source_id}/findings/{finding_id}` + `organizations/{organization_id}/sources/{source_id}/locations/{location_id}/findings/{finding_id}` + `folders/{folder_id}/sources/{source_id}/findings/{finding_id}` + `folders/{folder_id}/sources/{source_id}/locations/{location_id}/findings/{finding_id}` + `projects/{project_id}/sources/{source_id}/findings/{finding_id}` + `projects/{project_id}/sources/{source_id}/locations/{location_id}/findings/{finding_id}` */
		name: Option[String] = None,
	  /** Output only. The canonical name of the finding. The following list shows some examples: + `organizations/{organization_id}/sources/{source_id}/findings/{finding_id}` + `organizations/{organization_id}/sources/{source_id}/locations/{location_id}/findings/{finding_id}` + `folders/{folder_id}/sources/{source_id}/findings/{finding_id}` + `folders/{folder_id}/sources/{source_id}/locations/{location_id}/findings/{finding_id}` + `projects/{project_id}/sources/{source_id}/findings/{finding_id}` + `projects/{project_id}/sources/{source_id}/locations/{location_id}/findings/{finding_id}` The prefix is the closest CRM ancestor of the resource associated with the finding. */
		canonicalName: Option[String] = None,
	  /** The relative resource name of the source and location the finding belongs to. See: https://cloud.google.com/apis/design/resource_names#relative_resource_name This field is immutable after creation time. The following list shows some examples: + `organizations/{organization_id}/sources/{source_id}` + `folders/{folders_id}/sources/{source_id}` + `projects/{projects_id}/sources/{source_id}` + `organizations/{organization_id}/sources/{source_id}/locations/{location_id}` + `folders/{folders_id}/sources/{source_id}/locations/{location_id}` + `projects/{projects_id}/sources/{source_id}/locations/{location_id}` */
		parent: Option[String] = None,
	  /** Immutable. For findings on Google Cloud resources, the full resource name of the Google Cloud resource this finding is for. See: https://cloud.google.com/apis/design/resource_names#full_resource_name When the finding is for a non-Google Cloud resource, the resourceName can be a customer or partner defined string. */
		resourceName: Option[String] = None,
	  /** Output only. The state of the finding. */
		state: Option[Schema.GoogleCloudSecuritycenterV2Finding.StateEnum] = None,
	  /** Immutable. The additional taxonomy group within findings from a given source. Example: "XSS_FLASH_INJECTION" */
		category: Option[String] = None,
	  /** The URI that, if available, points to a web page outside of Security Command Center where additional information about the finding can be found. This field is guaranteed to be either empty or a well formed URL. */
		externalUri: Option[String] = None,
	  /** Source specific properties. These properties are managed by the source that writes the finding. The key names in the source_properties map must be between 1 and 255 characters, and must start with a letter and contain alphanumeric characters or underscores only. */
		sourceProperties: Option[Map[String, JsValue]] = None,
	  /** Output only. User specified security marks. These marks are entirely managed by the user and come from the SecurityMarks resource that belongs to the finding. */
		securityMarks: Option[Schema.GoogleCloudSecuritycenterV2SecurityMarks] = None,
	  /** The time the finding was first detected. If an existing finding is updated, then this is the time the update occurred. For example, if the finding represents an open firewall, this property captures the time the detector believes the firewall became open. The accuracy is determined by the detector. If the finding is later resolved, then this time reflects when the finding was resolved. This must not be set to a value greater than the current timestamp. */
		eventTime: Option[String] = None,
	  /** Output only. The time at which the finding was created in Security Command Center. */
		createTime: Option[String] = None,
	  /** The severity of the finding. This field is managed by the source that writes the finding. */
		severity: Option[Schema.GoogleCloudSecuritycenterV2Finding.SeverityEnum] = None,
	  /** Indicates the mute state of a finding (either muted, unmuted or undefined). Unlike other attributes of a finding, a finding provider shouldn't set the value of mute. */
		mute: Option[Schema.GoogleCloudSecuritycenterV2Finding.MuteEnum] = None,
	  /** Output only. The mute information regarding this finding. */
		muteInfo: Option[Schema.GoogleCloudSecuritycenterV2MuteInfo] = None,
	  /** The class of the finding. */
		findingClass: Option[Schema.GoogleCloudSecuritycenterV2Finding.FindingClassEnum] = None,
	  /** Represents what's commonly known as an &#42;indicator of compromise&#42; (IoC) in computer forensics. This is an artifact observed on a network or in an operating system that, with high confidence, indicates a computer intrusion. For more information, see [Indicator of compromise](https://en.wikipedia.org/wiki/Indicator_of_compromise). */
		indicator: Option[Schema.GoogleCloudSecuritycenterV2Indicator] = None,
	  /** Represents vulnerability-specific fields like CVE and CVSS scores. CVE stands for Common Vulnerabilities and Exposures (https://cve.mitre.org/about/) */
		vulnerability: Option[Schema.GoogleCloudSecuritycenterV2Vulnerability] = None,
	  /** Output only. The most recent time this finding was muted or unmuted. */
		muteUpdateTime: Option[String] = None,
	  /** Output only. Third party SIEM/SOAR fields within SCC, contains external system information and external system finding fields. */
		externalSystems: Option[Map[String, Schema.GoogleCloudSecuritycenterV2ExternalSystem]] = None,
	  /** MITRE ATT&CK tactics and techniques related to this finding. See: https://attack.mitre.org */
		mitreAttack: Option[Schema.GoogleCloudSecuritycenterV2MitreAttack] = None,
	  /** Access details associated with the finding, such as more information on the caller, which method was accessed, and from where. */
		access: Option[Schema.GoogleCloudSecuritycenterV2Access] = None,
	  /** Contains information about the IP connection associated with the finding. */
		connections: Option[List[Schema.GoogleCloudSecuritycenterV2Connection]] = None,
	  /** Records additional information about the mute operation, for example, the [mute configuration](https://cloud.google.com/security-command-center/docs/how-to-mute-findings) that muted the finding and the user who muted the finding. */
		muteInitiator: Option[String] = None,
	  /** Represents operating system processes associated with the Finding. */
		processes: Option[List[Schema.GoogleCloudSecuritycenterV2Process]] = None,
	  /** Output only. Map containing the points of contact for the given finding. The key represents the type of contact, while the value contains a list of all the contacts that pertain. Please refer to: https://cloud.google.com/resource-manager/docs/managing-notification-contacts#notification-categories { "security": { "contacts": [ { "email": "person1@company.com" }, { "email": "person2@company.com" } ] } } */
		contacts: Option[Map[String, Schema.GoogleCloudSecuritycenterV2ContactDetails]] = None,
	  /** Contains compliance information for security standards associated to the finding. */
		compliances: Option[List[Schema.GoogleCloudSecuritycenterV2Compliance]] = None,
	  /** Output only. The human readable display name of the finding source such as "Event Threat Detection" or "Security Health Analytics". */
		parentDisplayName: Option[String] = None,
	  /** Contains more details about the finding. */
		description: Option[String] = None,
	  /** Represents exfiltrations associated with the finding. */
		exfiltration: Option[Schema.GoogleCloudSecuritycenterV2Exfiltration] = None,
	  /** Represents IAM bindings associated with the finding. */
		iamBindings: Option[List[Schema.GoogleCloudSecuritycenterV2IamBinding]] = None,
	  /** Steps to address the finding. */
		nextSteps: Option[String] = None,
	  /** Unique identifier of the module which generated the finding. Example: folders/598186756061/securityHealthAnalyticsSettings/customModules/56799441161885 */
		moduleName: Option[String] = None,
	  /** Containers associated with the finding. This field provides information for both Kubernetes and non-Kubernetes containers. */
		containers: Option[List[Schema.GoogleCloudSecuritycenterV2Container]] = None,
	  /** Kubernetes resources associated with the finding. */
		kubernetes: Option[Schema.GoogleCloudSecuritycenterV2Kubernetes] = None,
	  /** Database associated with the finding. */
		database: Option[Schema.GoogleCloudSecuritycenterV2Database] = None,
	  /** The results of an attack path simulation relevant to this finding. */
		attackExposure: Option[Schema.GoogleCloudSecuritycenterV2AttackExposure] = None,
	  /** File associated with the finding. */
		files: Option[List[Schema.GoogleCloudSecuritycenterV2File]] = None,
	  /** Cloud Data Loss Prevention (Cloud DLP) inspection results that are associated with the finding. */
		cloudDlpInspection: Option[Schema.GoogleCloudSecuritycenterV2CloudDlpInspection] = None,
	  /** Cloud DLP data profile that is associated with the finding. */
		cloudDlpDataProfile: Option[Schema.GoogleCloudSecuritycenterV2CloudDlpDataProfile] = None,
	  /** Signature of the kernel rootkit. */
		kernelRootkit: Option[Schema.GoogleCloudSecuritycenterV2KernelRootkit] = None,
	  /** Contains information about the org policies associated with the finding. */
		orgPolicies: Option[List[Schema.GoogleCloudSecuritycenterV2OrgPolicy]] = None,
	  /** Represents an application associated with the finding. */
		application: Option[Schema.GoogleCloudSecuritycenterV2Application] = None,
	  /** Fields related to Backup and DR findings. */
		backupDisasterRecovery: Option[Schema.GoogleCloudSecuritycenterV2BackupDisasterRecovery] = None,
	  /** The security posture associated with the finding. */
		securityPosture: Option[Schema.GoogleCloudSecuritycenterV2SecurityPosture] = None,
	  /** Log entries that are relevant to the finding. */
		logEntries: Option[List[Schema.GoogleCloudSecuritycenterV2LogEntry]] = None,
	  /** The load balancers associated with the finding. */
		loadBalancers: Option[List[Schema.GoogleCloudSecuritycenterV2LoadBalancer]] = None,
	  /** Fields related to Cloud Armor findings. */
		cloudArmor: Option[Schema.GoogleCloudSecuritycenterV2CloudArmor] = None,
	  /** Notebook associated with the finding. */
		notebook: Option[Schema.GoogleCloudSecuritycenterV2Notebook] = None,
	  /** Contains details about a group of security issues that, when the issues occur together, represent a greater risk than when the issues occur independently. A group of such issues is referred to as a toxic combination. This field cannot be updated. Its value is ignored in all update requests. */
		toxicCombination: Option[Schema.GoogleCloudSecuritycenterV2ToxicCombination] = None,
	  /** Contains details about groups of which this finding is a member. A group is a collection of findings that are related in some way. This field cannot be updated. Its value is ignored in all update requests. */
		groupMemberships: Option[List[Schema.GoogleCloudSecuritycenterV2GroupMembership]] = None,
	  /** Disk associated with the finding. */
		disk: Option[Schema.GoogleCloudSecuritycenterV2Disk] = None,
	  /** Data access events associated with the finding. */
		dataAccessEvents: Option[List[Schema.GoogleCloudSecuritycenterV2DataAccessEvent]] = None,
	  /** Data flow events associated with the finding. */
		dataFlowEvents: Option[List[Schema.GoogleCloudSecuritycenterV2DataFlowEvent]] = None
	)
	
	case class GoogleCloudSecuritycenterV2SecurityMarks(
	  /** The relative resource name of the SecurityMarks. See: https://cloud.google.com/apis/design/resource_names#relative_resource_name The following list shows some examples: + `organizations/{organization_id}/assets/{asset_id}/securityMarks` + `organizations/{organization_id}/sources/{source_id}/findings/{finding_id}/securityMarks` + `organizations/{organization_id}/sources/{source_id}/locations/{location}/findings/{finding_id}/securityMarks` */
		name: Option[String] = None,
	  /** Mutable user specified security marks belonging to the parent resource. Constraints are as follows: &#42; Keys and values are treated as case insensitive &#42; Keys must be between 1 - 256 characters (inclusive) &#42; Keys must be letters, numbers, underscores, or dashes &#42; Values have leading and trailing whitespace trimmed, remaining characters must be between 1 - 4096 characters (inclusive) */
		marks: Option[Map[String, String]] = None,
	  /** The canonical name of the marks. The following list shows some examples: + `organizations/{organization_id}/assets/{asset_id}/securityMarks` + `organizations/{organization_id}/sources/{source_id}/findings/{finding_id}/securityMarks` + `organizations/{organization_id}/sources/{source_id}/locations/{location}/findings/{finding_id}/securityMarks` + `folders/{folder_id}/assets/{asset_id}/securityMarks` + `folders/{folder_id}/sources/{source_id}/findings/{finding_id}/securityMarks` + `folders/{folder_id}/sources/{source_id}/locations/{location}/findings/{finding_id}/securityMarks` + `projects/{project_number}/assets/{asset_id}/securityMarks` + `projects/{project_number}/sources/{source_id}/findings/{finding_id}/securityMarks` + `projects/{project_number}/sources/{source_id}/locations/{location}/findings/{finding_id}/securityMarks` */
		canonicalName: Option[String] = None
	)
	
	case class GoogleCloudSecuritycenterV2MuteInfo(
	  /** If set, the static mute applied to this finding. Static mutes override dynamic mutes. If unset, there is no static mute. */
		staticMute: Option[Schema.GoogleCloudSecuritycenterV2StaticMute] = None,
	  /** The list of dynamic mute rules that currently match the finding. */
		dynamicMuteRecords: Option[List[Schema.GoogleCloudSecuritycenterV2DynamicMuteRecord]] = None
	)
	
	object GoogleCloudSecuritycenterV2StaticMute {
		enum StateEnum extends Enum[StateEnum] { case MUTE_UNSPECIFIED, MUTED, UNMUTED, UNDEFINED }
	}
	case class GoogleCloudSecuritycenterV2StaticMute(
	  /** The static mute state. If the value is `MUTED` or `UNMUTED`, then the finding's overall mute state will have the same value. */
		state: Option[Schema.GoogleCloudSecuritycenterV2StaticMute.StateEnum] = None,
	  /** When the static mute was applied. */
		applyTime: Option[String] = None
	)
	
	case class GoogleCloudSecuritycenterV2DynamicMuteRecord(
	  /** The relative resource name of the mute rule, represented by a mute config, that created this record, for example `organizations/123/muteConfigs/mymuteconfig` or `organizations/123/locations/global/muteConfigs/mymuteconfig`. */
		muteConfig: Option[String] = None,
	  /** When the dynamic mute rule first matched the finding. */
		matchTime: Option[String] = None
	)
	
	case class GoogleCloudSecuritycenterV2Indicator(
	  /** The list of IP addresses that are associated with the finding. */
		ipAddresses: Option[List[String]] = None,
	  /** List of domains associated to the Finding. */
		domains: Option[List[String]] = None,
	  /** The list of matched signatures indicating that the given process is present in the environment. */
		signatures: Option[List[Schema.GoogleCloudSecuritycenterV2ProcessSignature]] = None,
	  /** The list of URIs associated to the Findings. */
		uris: Option[List[String]] = None
	)
	
	object GoogleCloudSecuritycenterV2ProcessSignature {
		enum SignatureTypeEnum extends Enum[SignatureTypeEnum] { case SIGNATURE_TYPE_UNSPECIFIED, SIGNATURE_TYPE_PROCESS, SIGNATURE_TYPE_FILE }
	}
	case class GoogleCloudSecuritycenterV2ProcessSignature(
	  /** Signature indicating that a binary family was matched. */
		memoryHashSignature: Option[Schema.GoogleCloudSecuritycenterV2MemoryHashSignature] = None,
	  /** Signature indicating that a YARA rule was matched. */
		yaraRuleSignature: Option[Schema.GoogleCloudSecuritycenterV2YaraRuleSignature] = None,
	  /** Describes the type of resource associated with the signature. */
		signatureType: Option[Schema.GoogleCloudSecuritycenterV2ProcessSignature.SignatureTypeEnum] = None
	)
	
	case class GoogleCloudSecuritycenterV2MemoryHashSignature(
	  /** The binary family. */
		binaryFamily: Option[String] = None,
	  /** The list of memory hash detections contributing to the binary family match. */
		detections: Option[List[Schema.GoogleCloudSecuritycenterV2Detection]] = None
	)
	
	case class GoogleCloudSecuritycenterV2Detection(
	  /** The name of the binary associated with the memory hash signature detection. */
		binary: Option[String] = None,
	  /** The percentage of memory page hashes in the signature that were matched. */
		percentPagesMatched: Option[BigDecimal] = None
	)
	
	case class GoogleCloudSecuritycenterV2YaraRuleSignature(
	  /** The name of the YARA rule. */
		yaraRule: Option[String] = None
	)
	
	case class GoogleCloudSecuritycenterV2Vulnerability(
	  /** CVE stands for Common Vulnerabilities and Exposures (https://cve.mitre.org/about/) */
		cve: Option[Schema.GoogleCloudSecuritycenterV2Cve] = None,
	  /** The offending package is relevant to the finding. */
		offendingPackage: Option[Schema.GoogleCloudSecuritycenterV2Package] = None,
	  /** The fixed package is relevant to the finding. */
		fixedPackage: Option[Schema.GoogleCloudSecuritycenterV2Package] = None,
	  /** The security bulletin is relevant to this finding. */
		securityBulletin: Option[Schema.GoogleCloudSecuritycenterV2SecurityBulletin] = None
	)
	
	object GoogleCloudSecuritycenterV2Cve {
		enum ImpactEnum extends Enum[ImpactEnum] { case RISK_RATING_UNSPECIFIED, LOW, MEDIUM, HIGH, CRITICAL }
		enum ExploitationActivityEnum extends Enum[ExploitationActivityEnum] { case EXPLOITATION_ACTIVITY_UNSPECIFIED, WIDE, CONFIRMED, AVAILABLE, ANTICIPATED, NO_KNOWN }
	}
	case class GoogleCloudSecuritycenterV2Cve(
	  /** The unique identifier for the vulnerability. e.g. CVE-2021-34527 */
		id: Option[String] = None,
	  /** Additional information about the CVE. e.g. https://cve.mitre.org/cgi-bin/cvename.cgi?name=CVE-2021-34527 */
		references: Option[List[Schema.GoogleCloudSecuritycenterV2Reference]] = None,
	  /** Describe Common Vulnerability Scoring System specified at https://www.first.org/cvss/v3.1/specification-document */
		cvssv3: Option[Schema.GoogleCloudSecuritycenterV2Cvssv3] = None,
	  /** Whether upstream fix is available for the CVE. */
		upstreamFixAvailable: Option[Boolean] = None,
	  /** The potential impact of the vulnerability if it was to be exploited. */
		impact: Option[Schema.GoogleCloudSecuritycenterV2Cve.ImpactEnum] = None,
	  /** The exploitation activity of the vulnerability in the wild. */
		exploitationActivity: Option[Schema.GoogleCloudSecuritycenterV2Cve.ExploitationActivityEnum] = None,
	  /** Whether or not the vulnerability has been observed in the wild. */
		observedInTheWild: Option[Boolean] = None,
	  /** Whether or not the vulnerability was zero day when the finding was published. */
		zeroDay: Option[Boolean] = None,
	  /** Date the first publicly available exploit or PoC was released. */
		exploitReleaseDate: Option[String] = None,
	  /** Date of the earliest known exploitation. */
		firstExploitationDate: Option[String] = None
	)
	
	case class GoogleCloudSecuritycenterV2Reference(
	  /** Source of the reference e.g. NVD */
		source: Option[String] = None,
	  /** Uri for the mentioned source e.g. https://cve.mitre.org/cgi-bin/cvename.cgi?name=CVE-2021-34527. */
		uri: Option[String] = None
	)
	
	object GoogleCloudSecuritycenterV2Cvssv3 {
		enum AttackVectorEnum extends Enum[AttackVectorEnum] { case ATTACK_VECTOR_UNSPECIFIED, ATTACK_VECTOR_NETWORK, ATTACK_VECTOR_ADJACENT, ATTACK_VECTOR_LOCAL, ATTACK_VECTOR_PHYSICAL }
		enum AttackComplexityEnum extends Enum[AttackComplexityEnum] { case ATTACK_COMPLEXITY_UNSPECIFIED, ATTACK_COMPLEXITY_LOW, ATTACK_COMPLEXITY_HIGH }
		enum PrivilegesRequiredEnum extends Enum[PrivilegesRequiredEnum] { case PRIVILEGES_REQUIRED_UNSPECIFIED, PRIVILEGES_REQUIRED_NONE, PRIVILEGES_REQUIRED_LOW, PRIVILEGES_REQUIRED_HIGH }
		enum UserInteractionEnum extends Enum[UserInteractionEnum] { case USER_INTERACTION_UNSPECIFIED, USER_INTERACTION_NONE, USER_INTERACTION_REQUIRED }
		enum ScopeEnum extends Enum[ScopeEnum] { case SCOPE_UNSPECIFIED, SCOPE_UNCHANGED, SCOPE_CHANGED }
		enum ConfidentialityImpactEnum extends Enum[ConfidentialityImpactEnum] { case IMPACT_UNSPECIFIED, IMPACT_HIGH, IMPACT_LOW, IMPACT_NONE }
		enum IntegrityImpactEnum extends Enum[IntegrityImpactEnum] { case IMPACT_UNSPECIFIED, IMPACT_HIGH, IMPACT_LOW, IMPACT_NONE }
		enum AvailabilityImpactEnum extends Enum[AvailabilityImpactEnum] { case IMPACT_UNSPECIFIED, IMPACT_HIGH, IMPACT_LOW, IMPACT_NONE }
	}
	case class GoogleCloudSecuritycenterV2Cvssv3(
	  /** The base score is a function of the base metric scores. */
		baseScore: Option[BigDecimal] = None,
	  /** Base Metrics Represents the intrinsic characteristics of a vulnerability that are constant over time and across user environments. This metric reflects the context by which vulnerability exploitation is possible. */
		attackVector: Option[Schema.GoogleCloudSecuritycenterV2Cvssv3.AttackVectorEnum] = None,
	  /** This metric describes the conditions beyond the attacker's control that must exist in order to exploit the vulnerability. */
		attackComplexity: Option[Schema.GoogleCloudSecuritycenterV2Cvssv3.AttackComplexityEnum] = None,
	  /** This metric describes the level of privileges an attacker must possess before successfully exploiting the vulnerability. */
		privilegesRequired: Option[Schema.GoogleCloudSecuritycenterV2Cvssv3.PrivilegesRequiredEnum] = None,
	  /** This metric captures the requirement for a human user, other than the attacker, to participate in the successful compromise of the vulnerable component. */
		userInteraction: Option[Schema.GoogleCloudSecuritycenterV2Cvssv3.UserInteractionEnum] = None,
	  /** The Scope metric captures whether a vulnerability in one vulnerable component impacts resources in components beyond its security scope. */
		scope: Option[Schema.GoogleCloudSecuritycenterV2Cvssv3.ScopeEnum] = None,
	  /** This metric measures the impact to the confidentiality of the information resources managed by a software component due to a successfully exploited vulnerability. */
		confidentialityImpact: Option[Schema.GoogleCloudSecuritycenterV2Cvssv3.ConfidentialityImpactEnum] = None,
	  /** This metric measures the impact to integrity of a successfully exploited vulnerability. */
		integrityImpact: Option[Schema.GoogleCloudSecuritycenterV2Cvssv3.IntegrityImpactEnum] = None,
	  /** This metric measures the impact to the availability of the impacted component resulting from a successfully exploited vulnerability. */
		availabilityImpact: Option[Schema.GoogleCloudSecuritycenterV2Cvssv3.AvailabilityImpactEnum] = None
	)
	
	case class GoogleCloudSecuritycenterV2Package(
	  /** The name of the package where the vulnerability was detected. */
		packageName: Option[String] = None,
	  /** The CPE URI where the vulnerability was detected. */
		cpeUri: Option[String] = None,
	  /** Type of package, for example, os, maven, or go. */
		packageType: Option[String] = None,
	  /** The version of the package. */
		packageVersion: Option[String] = None
	)
	
	case class GoogleCloudSecuritycenterV2SecurityBulletin(
	  /** ID of the bulletin corresponding to the vulnerability. */
		bulletinId: Option[String] = None,
	  /** Submission time of this Security Bulletin. */
		submissionTime: Option[String] = None,
	  /** This represents a version that the cluster receiving this notification should be upgraded to, based on its current version. For example, 1.15.0 */
		suggestedUpgradeVersion: Option[String] = None
	)
	
	object GoogleCloudSecuritycenterV2MitreAttack {
		enum PrimaryTacticEnum extends Enum[PrimaryTacticEnum] { case TACTIC_UNSPECIFIED, RECONNAISSANCE, RESOURCE_DEVELOPMENT, INITIAL_ACCESS, EXECUTION, PERSISTENCE, PRIVILEGE_ESCALATION, DEFENSE_EVASION, CREDENTIAL_ACCESS, DISCOVERY, LATERAL_MOVEMENT, COLLECTION, COMMAND_AND_CONTROL, EXFILTRATION, IMPACT }
		enum PrimaryTechniquesEnum extends Enum[PrimaryTechniquesEnum] { case TECHNIQUE_UNSPECIFIED, MASQUERADING, MATCH_LEGITIMATE_NAME_OR_LOCATION, BOOT_OR_LOGON_INITIALIZATION_SCRIPTS, STARTUP_ITEMS, NETWORK_SERVICE_DISCOVERY, PROCESS_DISCOVERY, COMMAND_AND_SCRIPTING_INTERPRETER, UNIX_SHELL, PYTHON, EXPLOITATION_FOR_PRIVILEGE_ESCALATION, PERMISSION_GROUPS_DISCOVERY, CLOUD_GROUPS, INDICATOR_REMOVAL_FILE_DELETION, APPLICATION_LAYER_PROTOCOL, DNS, SOFTWARE_DEPLOYMENT_TOOLS, VALID_ACCOUNTS, DEFAULT_ACCOUNTS, LOCAL_ACCOUNTS, CLOUD_ACCOUNTS, PROXY, EXTERNAL_PROXY, MULTI_HOP_PROXY, ACCOUNT_MANIPULATION, ADDITIONAL_CLOUD_CREDENTIALS, SSH_AUTHORIZED_KEYS, ADDITIONAL_CONTAINER_CLUSTER_ROLES, INGRESS_TOOL_TRANSFER, NATIVE_API, BRUTE_FORCE, SHARED_MODULES, ACCESS_TOKEN_MANIPULATION, TOKEN_IMPERSONATION_OR_THEFT, EXPLOIT_PUBLIC_FACING_APPLICATION, DOMAIN_POLICY_MODIFICATION, DATA_DESTRUCTION, SERVICE_STOP, INHIBIT_SYSTEM_RECOVERY, RESOURCE_HIJACKING, NETWORK_DENIAL_OF_SERVICE, CLOUD_SERVICE_DISCOVERY, STEAL_APPLICATION_ACCESS_TOKEN, ACCOUNT_ACCESS_REMOVAL, STEAL_WEB_SESSION_COOKIE, CREATE_OR_MODIFY_SYSTEM_PROCESS, EVENT_TRIGGERED_EXECUTION, ABUSE_ELEVATION_CONTROL_MECHANISM, UNSECURED_CREDENTIALS, MODIFY_AUTHENTICATION_PROCESS, IMPAIR_DEFENSES, DISABLE_OR_MODIFY_TOOLS, EXFILTRATION_OVER_WEB_SERVICE, EXFILTRATION_TO_CLOUD_STORAGE, DYNAMIC_RESOLUTION, LATERAL_TOOL_TRANSFER, MODIFY_CLOUD_COMPUTE_INFRASTRUCTURE, CREATE_SNAPSHOT, CLOUD_INFRASTRUCTURE_DISCOVERY, OBTAIN_CAPABILITIES, ACTIVE_SCANNING, SCANNING_IP_BLOCKS, CONTAINER_ADMINISTRATION_COMMAND, DEPLOY_CONTAINER, ESCAPE_TO_HOST, CONTAINER_AND_RESOURCE_DISCOVERY, STEAL_OR_FORGE_AUTHENTICATION_CERTIFICATES }
		enum AdditionalTacticsEnum extends Enum[AdditionalTacticsEnum] { case TACTIC_UNSPECIFIED, RECONNAISSANCE, RESOURCE_DEVELOPMENT, INITIAL_ACCESS, EXECUTION, PERSISTENCE, PRIVILEGE_ESCALATION, DEFENSE_EVASION, CREDENTIAL_ACCESS, DISCOVERY, LATERAL_MOVEMENT, COLLECTION, COMMAND_AND_CONTROL, EXFILTRATION, IMPACT }
		enum AdditionalTechniquesEnum extends Enum[AdditionalTechniquesEnum] { case TECHNIQUE_UNSPECIFIED, MASQUERADING, MATCH_LEGITIMATE_NAME_OR_LOCATION, BOOT_OR_LOGON_INITIALIZATION_SCRIPTS, STARTUP_ITEMS, NETWORK_SERVICE_DISCOVERY, PROCESS_DISCOVERY, COMMAND_AND_SCRIPTING_INTERPRETER, UNIX_SHELL, PYTHON, EXPLOITATION_FOR_PRIVILEGE_ESCALATION, PERMISSION_GROUPS_DISCOVERY, CLOUD_GROUPS, INDICATOR_REMOVAL_FILE_DELETION, APPLICATION_LAYER_PROTOCOL, DNS, SOFTWARE_DEPLOYMENT_TOOLS, VALID_ACCOUNTS, DEFAULT_ACCOUNTS, LOCAL_ACCOUNTS, CLOUD_ACCOUNTS, PROXY, EXTERNAL_PROXY, MULTI_HOP_PROXY, ACCOUNT_MANIPULATION, ADDITIONAL_CLOUD_CREDENTIALS, SSH_AUTHORIZED_KEYS, ADDITIONAL_CONTAINER_CLUSTER_ROLES, INGRESS_TOOL_TRANSFER, NATIVE_API, BRUTE_FORCE, SHARED_MODULES, ACCESS_TOKEN_MANIPULATION, TOKEN_IMPERSONATION_OR_THEFT, EXPLOIT_PUBLIC_FACING_APPLICATION, DOMAIN_POLICY_MODIFICATION, DATA_DESTRUCTION, SERVICE_STOP, INHIBIT_SYSTEM_RECOVERY, RESOURCE_HIJACKING, NETWORK_DENIAL_OF_SERVICE, CLOUD_SERVICE_DISCOVERY, STEAL_APPLICATION_ACCESS_TOKEN, ACCOUNT_ACCESS_REMOVAL, STEAL_WEB_SESSION_COOKIE, CREATE_OR_MODIFY_SYSTEM_PROCESS, EVENT_TRIGGERED_EXECUTION, ABUSE_ELEVATION_CONTROL_MECHANISM, UNSECURED_CREDENTIALS, MODIFY_AUTHENTICATION_PROCESS, IMPAIR_DEFENSES, DISABLE_OR_MODIFY_TOOLS, EXFILTRATION_OVER_WEB_SERVICE, EXFILTRATION_TO_CLOUD_STORAGE, DYNAMIC_RESOLUTION, LATERAL_TOOL_TRANSFER, MODIFY_CLOUD_COMPUTE_INFRASTRUCTURE, CREATE_SNAPSHOT, CLOUD_INFRASTRUCTURE_DISCOVERY, OBTAIN_CAPABILITIES, ACTIVE_SCANNING, SCANNING_IP_BLOCKS, CONTAINER_ADMINISTRATION_COMMAND, DEPLOY_CONTAINER, ESCAPE_TO_HOST, CONTAINER_AND_RESOURCE_DISCOVERY, STEAL_OR_FORGE_AUTHENTICATION_CERTIFICATES }
	}
	case class GoogleCloudSecuritycenterV2MitreAttack(
	  /** The MITRE ATT&CK tactic most closely represented by this finding, if any. */
		primaryTactic: Option[Schema.GoogleCloudSecuritycenterV2MitreAttack.PrimaryTacticEnum] = None,
	  /** The MITRE ATT&CK technique most closely represented by this finding, if any. primary_techniques is a repeated field because there are multiple levels of MITRE ATT&CK techniques. If the technique most closely represented by this finding is a sub-technique (e.g. `SCANNING_IP_BLOCKS`), both the sub-technique and its parent technique(s) will be listed (e.g. `SCANNING_IP_BLOCKS`, `ACTIVE_SCANNING`). */
		primaryTechniques: Option[List[Schema.GoogleCloudSecuritycenterV2MitreAttack.PrimaryTechniquesEnum]] = None,
	  /** Additional MITRE ATT&CK tactics related to this finding, if any. */
		additionalTactics: Option[List[Schema.GoogleCloudSecuritycenterV2MitreAttack.AdditionalTacticsEnum]] = None,
	  /** Additional MITRE ATT&CK techniques related to this finding, if any, along with any of their respective parent techniques. */
		additionalTechniques: Option[List[Schema.GoogleCloudSecuritycenterV2MitreAttack.AdditionalTechniquesEnum]] = None,
	  /** The MITRE ATT&CK version referenced by the above fields. E.g. "8". */
		version: Option[String] = None
	)
	
	case class GoogleCloudSecuritycenterV2Access(
	  /** Associated email, such as "foo@google.com". The email address of the authenticated user or a service account acting on behalf of a third party principal making the request. For third party identity callers, the `principal_subject` field is populated instead of this field. For privacy reasons, the principal email address is sometimes redacted. For more information, see [Caller identities in audit logs](https://cloud.google.com/logging/docs/audit#user-id). */
		principalEmail: Option[String] = None,
	  /** Caller's IP address, such as "1.1.1.1". */
		callerIp: Option[String] = None,
	  /** The caller IP's geolocation, which identifies where the call came from. */
		callerIpGeo: Option[Schema.GoogleCloudSecuritycenterV2Geolocation] = None,
	  /** Type of user agent associated with the finding. For example, an operating system shell or an embedded or standalone application. */
		userAgentFamily: Option[String] = None,
	  /** The caller's user agent string associated with the finding. */
		userAgent: Option[String] = None,
	  /** This is the API service that the service account made a call to, e.g. "iam.googleapis.com" */
		serviceName: Option[String] = None,
	  /** The method that the service account called, e.g. "SetIamPolicy". */
		methodName: Option[String] = None,
	  /** A string that represents the principal_subject that is associated with the identity. Unlike `principal_email`, `principal_subject` supports principals that aren't associated with email addresses, such as third party principals. For most identities, the format is `principal://iam.googleapis.com/{identity pool name}/subject/{subject}`. Some GKE identities, such as GKE_WORKLOAD, FREEFORM, and GKE_HUB_WORKLOAD, still use the legacy format `serviceAccount:{identity pool name}[{subject}]`. */
		principalSubject: Option[String] = None,
	  /** The name of the service account key that was used to create or exchange credentials when authenticating the service account that made the request. This is a scheme-less URI full resource name. For example: "//iam.googleapis.com/projects/{PROJECT_ID}/serviceAccounts/{ACCOUNT}/keys/{key}".  */
		serviceAccountKeyName: Option[String] = None,
	  /** The identity delegation history of an authenticated service account that made the request. The `serviceAccountDelegationInfo[]` object contains information about the real authorities that try to access Google Cloud resources by delegating on a service account. When multiple authorities are present, they are guaranteed to be sorted based on the original ordering of the identity delegation events. */
		serviceAccountDelegationInfo: Option[List[Schema.GoogleCloudSecuritycenterV2ServiceAccountDelegationInfo]] = None,
	  /** A string that represents a username. The username provided depends on the type of the finding and is likely not an IAM principal. For example, this can be a system username if the finding is related to a virtual machine, or it can be an application login username. */
		userName: Option[String] = None
	)
	
	case class GoogleCloudSecuritycenterV2Geolocation(
	  /** A CLDR. */
		regionCode: Option[String] = None
	)
	
	case class GoogleCloudSecuritycenterV2ServiceAccountDelegationInfo(
	  /** The email address of a Google account. */
		principalEmail: Option[String] = None,
	  /** A string representing the principal_subject associated with the identity. As compared to `principal_email`, supports principals that aren't associated with email addresses, such as third party principals. For most identities, the format will be `principal://iam.googleapis.com/{identity pool name}/subjects/{subject}` except for some GKE identities (GKE_WORKLOAD, FREEFORM, GKE_HUB_WORKLOAD) that are still in the legacy format `serviceAccount:{identity pool name}[{subject}]` */
		principalSubject: Option[String] = None
	)
	
	object GoogleCloudSecuritycenterV2Connection {
		enum ProtocolEnum extends Enum[ProtocolEnum] { case PROTOCOL_UNSPECIFIED, ICMP, TCP, UDP, GRE, ESP }
	}
	case class GoogleCloudSecuritycenterV2Connection(
	  /** Destination IP address. Not present for sockets that are listening and not connected. */
		destinationIp: Option[String] = None,
	  /** Destination port. Not present for sockets that are listening and not connected. */
		destinationPort: Option[Int] = None,
	  /** Source IP address. */
		sourceIp: Option[String] = None,
	  /** Source port. */
		sourcePort: Option[Int] = None,
	  /** IANA Internet Protocol Number such as TCP(6) and UDP(17). */
		protocol: Option[Schema.GoogleCloudSecuritycenterV2Connection.ProtocolEnum] = None
	)
	
	case class GoogleCloudSecuritycenterV2Process(
	  /** The process name, as displayed in utilities like `top` and `ps`. This name can be accessed through `/proc/[pid]/comm` and changed with `prctl(PR_SET_NAME)`. */
		name: Option[String] = None,
	  /** File information for the process executable. */
		binary: Option[Schema.GoogleCloudSecuritycenterV2File] = None,
	  /** File information for libraries loaded by the process. */
		libraries: Option[List[Schema.GoogleCloudSecuritycenterV2File]] = None,
	  /** When the process represents the invocation of a script, `binary` provides information about the interpreter, while `script` provides information about the script file provided to the interpreter. */
		script: Option[Schema.GoogleCloudSecuritycenterV2File] = None,
	  /** Process arguments as JSON encoded strings. */
		args: Option[List[String]] = None,
	  /** True if `args` is incomplete. */
		argumentsTruncated: Option[Boolean] = None,
	  /** Process environment variables. */
		envVariables: Option[List[Schema.GoogleCloudSecuritycenterV2EnvironmentVariable]] = None,
	  /** True if `env_variables` is incomplete. */
		envVariablesTruncated: Option[Boolean] = None,
	  /** The process ID. */
		pid: Option[String] = None,
	  /** The parent process ID. */
		parentPid: Option[String] = None
	)
	
	case class GoogleCloudSecuritycenterV2File(
	  /** Absolute path of the file as a JSON encoded string. */
		path: Option[String] = None,
	  /** Size of the file in bytes. */
		size: Option[String] = None,
	  /** SHA256 hash of the first hashed_size bytes of the file encoded as a hex string. If hashed_size == size, sha256 represents the SHA256 hash of the entire file. */
		sha256: Option[String] = None,
	  /** The length in bytes of the file prefix that was hashed. If hashed_size == size, any hashes reported represent the entire file. */
		hashedSize: Option[String] = None,
	  /** True when the hash covers only a prefix of the file. */
		partiallyHashed: Option[Boolean] = None,
	  /** Prefix of the file contents as a JSON-encoded string. */
		contents: Option[String] = None,
	  /** Path of the file in terms of underlying disk/partition identifiers. */
		diskPath: Option[Schema.GoogleCloudSecuritycenterV2DiskPath] = None
	)
	
	case class GoogleCloudSecuritycenterV2DiskPath(
	  /** UUID of the partition (format https://wiki.archlinux.org/title/persistent_block_device_naming#by-uuid) */
		partitionUuid: Option[String] = None,
	  /** Relative path of the file in the partition as a JSON encoded string. Example: /home/user1/executable_file.sh */
		relativePath: Option[String] = None
	)
	
	case class GoogleCloudSecuritycenterV2EnvironmentVariable(
	  /** Environment variable name as a JSON encoded string. */
		name: Option[String] = None,
	  /** Environment variable value as a JSON encoded string. */
		`val`: Option[String] = None
	)
	
	case class GoogleCloudSecuritycenterV2ContactDetails(
	  /** A list of contacts */
		contacts: Option[List[Schema.GoogleCloudSecuritycenterV2Contact]] = None
	)
	
	case class GoogleCloudSecuritycenterV2Contact(
	  /** An email address. For example, "`person123@company.com`". */
		email: Option[String] = None
	)
	
	case class GoogleCloudSecuritycenterV2Compliance(
	  /** Industry-wide compliance standards or benchmarks, such as CIS, PCI, and OWASP. */
		standard: Option[String] = None,
	  /** Version of the standard or benchmark, for example, 1.1 */
		version: Option[String] = None,
	  /** Policies within the standard or benchmark, for example, A.12.4.1 */
		ids: Option[List[String]] = None
	)
	
	case class GoogleCloudSecuritycenterV2Exfiltration(
	  /** If there are multiple sources, then the data is considered "joined" between them. For instance, BigQuery can join multiple tables, and each table would be considered a source. */
		sources: Option[List[Schema.GoogleCloudSecuritycenterV2ExfilResource]] = None,
	  /** If there are multiple targets, each target would get a complete copy of the "joined" source data. */
		targets: Option[List[Schema.GoogleCloudSecuritycenterV2ExfilResource]] = None,
	  /** Total exfiltrated bytes processed for the entire job. */
		totalExfiltratedBytes: Option[String] = None
	)
	
	case class GoogleCloudSecuritycenterV2ExfilResource(
	  /** The resource's [full resource name](https://cloud.google.com/apis/design/resource_names#full_resource_name). */
		name: Option[String] = None,
	  /** Subcomponents of the asset that was exfiltrated, like URIs used during exfiltration, table names, databases, and filenames. For example, multiple tables might have been exfiltrated from the same Cloud SQL instance, or multiple files might have been exfiltrated from the same Cloud Storage bucket. */
		components: Option[List[String]] = None
	)
	
	object GoogleCloudSecuritycenterV2IamBinding {
		enum ActionEnum extends Enum[ActionEnum] { case ACTION_UNSPECIFIED, ADD, REMOVE }
	}
	case class GoogleCloudSecuritycenterV2IamBinding(
	  /** The action that was performed on a Binding. */
		action: Option[Schema.GoogleCloudSecuritycenterV2IamBinding.ActionEnum] = None,
	  /** Role that is assigned to "members". For example, "roles/viewer", "roles/editor", or "roles/owner". */
		role: Option[String] = None,
	  /** A single identity requesting access for a Cloud Platform resource, for example, "foo@google.com". */
		member: Option[String] = None
	)
	
	case class GoogleCloudSecuritycenterV2Container(
	  /** Name of the container. */
		name: Option[String] = None,
	  /** Container image URI provided when configuring a pod or container. This string can identify a container image version using mutable tags. */
		uri: Option[String] = None,
	  /** Optional container image ID, if provided by the container runtime. Uniquely identifies the container image launched using a container image digest. */
		imageId: Option[String] = None,
	  /** Container labels, as provided by the container runtime. */
		labels: Option[List[Schema.GoogleCloudSecuritycenterV2Label]] = None,
	  /** The time that the container was created. */
		createTime: Option[String] = None
	)
	
	case class GoogleCloudSecuritycenterV2Label(
	  /** Name of the label. */
		name: Option[String] = None,
	  /** Value that corresponds to the label's name. */
		value: Option[String] = None
	)
	
	case class GoogleCloudSecuritycenterV2Kubernetes(
	  /** Kubernetes [Pods](https://cloud.google.com/kubernetes-engine/docs/concepts/pod) associated with the finding. This field contains Pod records for each container that is owned by a Pod. */
		pods: Option[List[Schema.GoogleCloudSecuritycenterV2Pod]] = None,
	  /** Provides Kubernetes [node](https://cloud.google.com/kubernetes-engine/docs/concepts/cluster-architecture#nodes) information. */
		nodes: Option[List[Schema.GoogleCloudSecuritycenterV2Node]] = None,
	  /** GKE [node pools](https://cloud.google.com/kubernetes-engine/docs/concepts/node-pools) associated with the finding. This field contains node pool information for each node, when it is available. */
		nodePools: Option[List[Schema.GoogleCloudSecuritycenterV2NodePool]] = None,
	  /** Provides Kubernetes role information for findings that involve [Roles or ClusterRoles](https://cloud.google.com/kubernetes-engine/docs/how-to/role-based-access-control). */
		roles: Option[List[Schema.GoogleCloudSecuritycenterV2Role]] = None,
	  /** Provides Kubernetes role binding information for findings that involve [RoleBindings or ClusterRoleBindings](https://cloud.google.com/kubernetes-engine/docs/how-to/role-based-access-control). */
		bindings: Option[List[Schema.GoogleCloudSecuritycenterV2Binding]] = None,
	  /** Provides information on any Kubernetes access reviews (privilege checks) relevant to the finding. */
		accessReviews: Option[List[Schema.GoogleCloudSecuritycenterV2AccessReview]] = None,
	  /** Kubernetes objects related to the finding. */
		objects: Option[List[Schema.GoogleCloudSecuritycenterV2Object]] = None
	)
	
	case class GoogleCloudSecuritycenterV2Pod(
	  /** Kubernetes Pod namespace. */
		ns: Option[String] = None,
	  /** Kubernetes Pod name. */
		name: Option[String] = None,
	  /** Pod labels. For Kubernetes containers, these are applied to the container. */
		labels: Option[List[Schema.GoogleCloudSecuritycenterV2Label]] = None,
	  /** Pod containers associated with this finding, if any. */
		containers: Option[List[Schema.GoogleCloudSecuritycenterV2Container]] = None
	)
	
	case class GoogleCloudSecuritycenterV2Node(
	  /** [Full resource name](https://google.aip.dev/122#full-resource-names) of the Compute Engine VM running the cluster node. */
		name: Option[String] = None
	)
	
	case class GoogleCloudSecuritycenterV2NodePool(
	  /** Kubernetes node pool name. */
		name: Option[String] = None,
	  /** Nodes associated with the finding. */
		nodes: Option[List[Schema.GoogleCloudSecuritycenterV2Node]] = None
	)
	
	object GoogleCloudSecuritycenterV2Role {
		enum KindEnum extends Enum[KindEnum] { case KIND_UNSPECIFIED, ROLE, CLUSTER_ROLE }
	}
	case class GoogleCloudSecuritycenterV2Role(
	  /** Role type. */
		kind: Option[Schema.GoogleCloudSecuritycenterV2Role.KindEnum] = None,
	  /** Role namespace. */
		ns: Option[String] = None,
	  /** Role name. */
		name: Option[String] = None
	)
	
	case class GoogleCloudSecuritycenterV2Binding(
	  /** Namespace for the binding. */
		ns: Option[String] = None,
	  /** Name for the binding. */
		name: Option[String] = None,
	  /** The Role or ClusterRole referenced by the binding. */
		role: Option[Schema.GoogleCloudSecuritycenterV2Role] = None,
	  /** Represents one or more subjects that are bound to the role. Not always available for PATCH requests. */
		subjects: Option[List[Schema.GoogleCloudSecuritycenterV2Subject]] = None
	)
	
	object GoogleCloudSecuritycenterV2Subject {
		enum KindEnum extends Enum[KindEnum] { case AUTH_TYPE_UNSPECIFIED, USER, SERVICEACCOUNT, GROUP }
	}
	case class GoogleCloudSecuritycenterV2Subject(
	  /** Authentication type for the subject. */
		kind: Option[Schema.GoogleCloudSecuritycenterV2Subject.KindEnum] = None,
	  /** Namespace for the subject. */
		ns: Option[String] = None,
	  /** Name for the subject. */
		name: Option[String] = None
	)
	
	case class GoogleCloudSecuritycenterV2AccessReview(
	  /** The API group of the resource. "&#42;" means all. */
		group: Option[String] = None,
	  /** Namespace of the action being requested. Currently, there is no distinction between no namespace and all namespaces. Both are represented by "" (empty). */
		ns: Option[String] = None,
	  /** The name of the resource being requested. Empty means all. */
		name: Option[String] = None,
	  /** The optional resource type requested. "&#42;" means all. */
		resource: Option[String] = None,
	  /** The optional subresource type. */
		subresource: Option[String] = None,
	  /** A Kubernetes resource API verb, like get, list, watch, create, update, delete, proxy. "&#42;" means all. */
		verb: Option[String] = None,
	  /** The API version of the resource. "&#42;" means all. */
		version: Option[String] = None
	)
	
	case class GoogleCloudSecuritycenterV2Object(
	  /** Kubernetes object group, such as "policy.k8s.io/v1". */
		group: Option[String] = None,
	  /** Kubernetes object kind, such as "Namespace". */
		kind: Option[String] = None,
	  /** Kubernetes object namespace. Must be a valid DNS label. Named "ns" to avoid collision with C++ namespace keyword. For details see https://kubernetes.io/docs/tasks/administer-cluster/namespaces/. */
		ns: Option[String] = None,
	  /** Kubernetes object name. For details see https://kubernetes.io/docs/concepts/overview/working-with-objects/names/. */
		name: Option[String] = None,
	  /** Pod containers associated with this finding, if any. */
		containers: Option[List[Schema.GoogleCloudSecuritycenterV2Container]] = None
	)
	
	case class GoogleCloudSecuritycenterV2Database(
	  /** Some database resources may not have the [full resource name](https://google.aip.dev/122#full-resource-names) populated because these resource types are not yet supported by Cloud Asset Inventory (e.g. Cloud SQL databases). In these cases only the display name will be provided. The [full resource name](https://google.aip.dev/122#full-resource-names) of the database that the user connected to, if it is supported by Cloud Asset Inventory. */
		name: Option[String] = None,
	  /** The human-readable name of the database that the user connected to. */
		displayName: Option[String] = None,
	  /** The username used to connect to the database. The username might not be an IAM principal and does not have a set format. */
		userName: Option[String] = None,
	  /** The SQL statement that is associated with the database access. */
		query: Option[String] = None,
	  /** The target usernames, roles, or groups of an SQL privilege grant, which is not an IAM policy change. */
		grantees: Option[List[String]] = None,
	  /** The version of the database, for example, POSTGRES_14. See [the complete list](https://cloud.google.com/sql/docs/mysql/admin-api/rest/v1/SqlDatabaseVersion). */
		version: Option[String] = None
	)
	
	object GoogleCloudSecuritycenterV2AttackExposure {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, CALCULATED, NOT_CALCULATED }
	}
	case class GoogleCloudSecuritycenterV2AttackExposure(
	  /** A number between 0 (inclusive) and infinity that represents how important this finding is to remediate. The higher the score, the more important it is to remediate. */
		score: Option[BigDecimal] = None,
	  /** The most recent time the attack exposure was updated on this finding. */
		latestCalculationTime: Option[String] = None,
	  /** The resource name of the attack path simulation result that contains the details regarding this attack exposure score. Example: `organizations/123/simulations/456/attackExposureResults/789` */
		attackExposureResult: Option[String] = None,
	  /** Output only. What state this AttackExposure is in. This captures whether or not an attack exposure has been calculated or not. */
		state: Option[Schema.GoogleCloudSecuritycenterV2AttackExposure.StateEnum] = None,
	  /** The number of high value resources that are exposed as a result of this finding. */
		exposedHighValueResourcesCount: Option[Int] = None,
	  /** The number of medium value resources that are exposed as a result of this finding. */
		exposedMediumValueResourcesCount: Option[Int] = None,
	  /** The number of high value resources that are exposed as a result of this finding. */
		exposedLowValueResourcesCount: Option[Int] = None
	)
	
	case class GoogleCloudSecuritycenterV2CloudDlpInspection(
	  /** Name of the inspection job, for example, `projects/123/locations/europe/dlpJobs/i-8383929`. */
		inspectJob: Option[String] = None,
	  /** The type of information (or &#42;[infoType](https://cloud.google.com/dlp/docs/infotypes-reference)&#42;) found, for example, `EMAIL_ADDRESS` or `STREET_ADDRESS`. */
		infoType: Option[String] = None,
	  /** The number of times Cloud DLP found this infoType within this job and resource. */
		infoTypeCount: Option[String] = None,
	  /** Whether Cloud DLP scanned the complete resource or a sampled subset. */
		fullScan: Option[Boolean] = None
	)
	
	object GoogleCloudSecuritycenterV2CloudDlpDataProfile {
		enum ParentTypeEnum extends Enum[ParentTypeEnum] { case PARENT_TYPE_UNSPECIFIED, ORGANIZATION, PROJECT }
	}
	case class GoogleCloudSecuritycenterV2CloudDlpDataProfile(
	  /** Name of the data profile, for example, `projects/123/locations/europe/tableProfiles/8383929`. */
		dataProfile: Option[String] = None,
	  /** The resource hierarchy level at which the data profile was generated. */
		parentType: Option[Schema.GoogleCloudSecuritycenterV2CloudDlpDataProfile.ParentTypeEnum] = None
	)
	
	case class GoogleCloudSecuritycenterV2KernelRootkit(
	  /** Rootkit name, when available. */
		name: Option[String] = None,
	  /** True if unexpected modifications of kernel code memory are present. */
		unexpectedCodeModification: Option[Boolean] = None,
	  /** True if unexpected modifications of kernel read-only data memory are present. */
		unexpectedReadOnlyDataModification: Option[Boolean] = None,
	  /** True if `ftrace` points are present with callbacks pointing to regions that are not in the expected kernel or module code range. */
		unexpectedFtraceHandler: Option[Boolean] = None,
	  /** True if `kprobe` points are present with callbacks pointing to regions that are not in the expected kernel or module code range. */
		unexpectedKprobeHandler: Option[Boolean] = None,
	  /** True if kernel code pages that are not in the expected kernel or module code regions are present. */
		unexpectedKernelCodePages: Option[Boolean] = None,
	  /** True if system call handlers that are are not in the expected kernel or module code regions are present. */
		unexpectedSystemCallHandler: Option[Boolean] = None,
	  /** True if interrupt handlers that are are not in the expected kernel or module code regions are present. */
		unexpectedInterruptHandler: Option[Boolean] = None,
	  /** True if unexpected processes in the scheduler run queue are present. Such processes are in the run queue, but not in the process task list. */
		unexpectedProcessesInRunqueue: Option[Boolean] = None
	)
	
	case class GoogleCloudSecuritycenterV2OrgPolicy(
	  /** The resource name of the org policy. Example: "organizations/{organization_id}/policies/{constraint_name}" */
		name: Option[String] = None
	)
	
	case class GoogleCloudSecuritycenterV2Application(
	  /** The base URI that identifies the network location of the application in which the vulnerability was detected. For example, `http://example.com`. */
		baseUri: Option[String] = None,
	  /** The full URI with payload that could be used to reproduce the vulnerability. For example, `http://example.com?p=aMmYgI6H`. */
		fullUri: Option[String] = None
	)
	
	case class GoogleCloudSecuritycenterV2BackupDisasterRecovery(
	  /** The name of a Backup and DR template which comprises one or more backup policies. See the [Backup and DR documentation](https://cloud.google.com/backup-disaster-recovery/docs/concepts/backup-plan#temp) for more information. For example, `snap-ov`. */
		backupTemplate: Option[String] = None,
	  /** The names of Backup and DR policies that are associated with a template and that define when to run a backup, how frequently to run a backup, and how long to retain the backup image. For example, `onvaults`. */
		policies: Option[List[String]] = None,
	  /** The name of a Backup and DR host, which is managed by the backup and recovery appliance and known to the management console. The host can be of type Generic (for example, Compute Engine, SQL Server, Oracle DB, SMB file system, etc.), vCenter, or an ESX server. See the [Backup and DR documentation on hosts](https://cloud.google.com/backup-disaster-recovery/docs/configuration/manage-hosts-and-their-applications) for more information. For example, `centos7-01`. */
		host: Option[String] = None,
	  /** The names of Backup and DR applications. An application is a VM, database, or file system on a managed host monitored by a backup and recovery appliance. For example, `centos7-01-vol00`, `centos7-01-vol01`, `centos7-01-vol02`. */
		applications: Option[List[String]] = None,
	  /** The name of the Backup and DR storage pool that the backup and recovery appliance is storing data in. The storage pool could be of type Cloud, Primary, Snapshot, or OnVault. See the [Backup and DR documentation on storage pools](https://cloud.google.com/backup-disaster-recovery/docs/concepts/storage-pools). For example, `DiskPoolOne`. */
		storagePool: Option[String] = None,
	  /** The names of Backup and DR advanced policy options of a policy applying to an application. See the [Backup and DR documentation on policy options](https://cloud.google.com/backup-disaster-recovery/docs/create-plan/policy-settings). For example, `skipofflineappsincongrp, nounmap`. */
		policyOptions: Option[List[String]] = None,
	  /** The name of the Backup and DR resource profile that specifies the storage media for backups of application and VM data. See the [Backup and DR documentation on profiles](https://cloud.google.com/backup-disaster-recovery/docs/concepts/backup-plan#profile). For example, `GCP`. */
		profile: Option[String] = None,
	  /** The name of the Backup and DR appliance that captures, moves, and manages the lifecycle of backup data. For example, `backup-server-57137`. */
		appliance: Option[String] = None,
	  /** The backup type of the Backup and DR image. For example, `Snapshot`, `Remote Snapshot`, `OnVault`. */
		backupType: Option[String] = None,
	  /** The timestamp at which the Backup and DR backup was created. */
		backupCreateTime: Option[String] = None
	)
	
	case class GoogleCloudSecuritycenterV2SecurityPosture(
	  /** Name of the posture, for example, `CIS-Posture`. */
		name: Option[String] = None,
	  /** The version of the posture, for example, `c7cfa2a8`. */
		revisionId: Option[String] = None,
	  /** The project, folder, or organization on which the posture is deployed, for example, `projects/{project_number}`. */
		postureDeploymentResource: Option[String] = None,
	  /** The name of the posture deployment, for example, `organizations/{org_id}/posturedeployments/{posture_deployment_id}`. */
		postureDeployment: Option[String] = None,
	  /** The name of the updated policy, for example, `projects/{project_id}/policies/{constraint_name}`. */
		changedPolicy: Option[String] = None,
	  /** The name of the updated policy set, for example, `cis-policyset`. */
		policySet: Option[String] = None,
	  /** The ID of the updated policy, for example, `compute-policy-1`. */
		policy: Option[String] = None,
	  /** The details about a change in an updated policy that violates the deployed posture. */
		policyDriftDetails: Option[List[Schema.GoogleCloudSecuritycenterV2PolicyDriftDetails]] = None
	)
	
	case class GoogleCloudSecuritycenterV2PolicyDriftDetails(
	  /** The name of the updated field, for example constraint.implementation.policy_rules[0].enforce */
		field: Option[String] = None,
	  /** The value of this field that was configured in a posture, for example, `true` or `allowed_values={"projects/29831892"}`. */
		expectedValue: Option[String] = None,
	  /** The detected value that violates the deployed posture, for example, `false` or `allowed_values={"projects/22831892"}`. */
		detectedValue: Option[String] = None
	)
	
	case class GoogleCloudSecuritycenterV2LogEntry(
	  /** An individual entry in a log stored in Cloud Logging. */
		cloudLoggingEntry: Option[Schema.GoogleCloudSecuritycenterV2CloudLoggingEntry] = None
	)
	
	case class GoogleCloudSecuritycenterV2CloudLoggingEntry(
	  /** A unique identifier for the log entry. */
		insertId: Option[String] = None,
	  /** The type of the log (part of `log_name`. `log_name` is the resource name of the log to which this log entry belongs). For example: `cloudresourcemanager.googleapis.com/activity` Note that this field is not URL-encoded, unlike in `LogEntry`. */
		logId: Option[String] = None,
	  /** The organization, folder, or project of the monitored resource that produced this log entry. */
		resourceContainer: Option[String] = None,
	  /** The time the event described by the log entry occurred. */
		timestamp: Option[String] = None
	)
	
	case class GoogleCloudSecuritycenterV2LoadBalancer(
	  /** The name of the load balancer associated with the finding. */
		name: Option[String] = None
	)
	
	case class GoogleCloudSecuritycenterV2CloudArmor(
	  /** Information about the [Google Cloud Armor security policy](https://cloud.google.com/armor/docs/security-policy-overview) relevant to the finding. */
		securityPolicy: Option[Schema.GoogleCloudSecuritycenterV2SecurityPolicy] = None,
	  /** Information about incoming requests evaluated by [Google Cloud Armor security policies](https://cloud.google.com/armor/docs/security-policy-overview). */
		requests: Option[Schema.GoogleCloudSecuritycenterV2Requests] = None,
	  /** Information about potential Layer 7 DDoS attacks identified by [Google Cloud Armor Adaptive Protection](https://cloud.google.com/armor/docs/adaptive-protection-overview). */
		adaptiveProtection: Option[Schema.GoogleCloudSecuritycenterV2AdaptiveProtection] = None,
	  /** Information about DDoS attack volume and classification. */
		attack: Option[Schema.GoogleCloudSecuritycenterV2Attack] = None,
	  /** Distinguish between volumetric & protocol DDoS attack and application layer attacks. For example, "L3_4" for Layer 3 and Layer 4 DDoS attacks, or "L_7" for Layer 7 DDoS attacks. */
		threatVector: Option[String] = None,
	  /** Duration of attack from the start until the current moment (updated every 5 minutes). */
		duration: Option[String] = None
	)
	
	case class GoogleCloudSecuritycenterV2SecurityPolicy(
	  /** The name of the Google Cloud Armor security policy, for example, "my-security-policy". */
		name: Option[String] = None,
	  /** The type of Google Cloud Armor security policy for example, 'backend security policy', 'edge security policy', 'network edge security policy', or 'always-on DDoS protection'. */
		`type`: Option[String] = None,
	  /** Whether or not the associated rule or policy is in preview mode. */
		preview: Option[Boolean] = None
	)
	
	case class GoogleCloudSecuritycenterV2Requests(
	  /** For 'Increasing deny ratio', the ratio is the denied traffic divided by the allowed traffic. For 'Allowed traffic spike', the ratio is the allowed traffic in the short term divided by allowed traffic in the long term. */
		ratio: Option[BigDecimal] = None,
	  /** Allowed RPS (requests per second) in the short term. */
		shortTermAllowed: Option[Int] = None,
	  /** Allowed RPS (requests per second) over the long term. */
		longTermAllowed: Option[Int] = None,
	  /** Denied RPS (requests per second) over the long term. */
		longTermDenied: Option[Int] = None
	)
	
	case class GoogleCloudSecuritycenterV2AdaptiveProtection(
	  /** A score of 0 means that there is low confidence that the detected event is an actual attack. A score of 1 means that there is high confidence that the detected event is an attack. See the [Adaptive Protection documentation](https://cloud.google.com/armor/docs/adaptive-protection-overview#configure-alert-tuning) for further explanation. */
		confidence: Option[BigDecimal] = None
	)
	
	case class GoogleCloudSecuritycenterV2Attack(
	  /** Total PPS (packets per second) volume of attack. */
		volumePps: Option[Int] = None,
	  /** Total BPS (bytes per second) volume of attack. */
		volumeBps: Option[Int] = None,
	  /** Type of attack, for example, 'SYN-flood', 'NTP-udp', or 'CHARGEN-udp'. */
		classification: Option[String] = None
	)
	
	case class GoogleCloudSecuritycenterV2Notebook(
	  /** The name of the notebook. */
		name: Option[String] = None,
	  /** The source notebook service, for example, "Colab Enterprise". */
		service: Option[String] = None,
	  /** The user ID of the latest author to modify the notebook. */
		lastAuthor: Option[String] = None,
	  /** The most recent time the notebook was updated. */
		notebookUpdateTime: Option[String] = None
	)
	
	case class GoogleCloudSecuritycenterV2ToxicCombination(
	  /** The [Attack exposure score](https://cloud.google.com/security-command-center/docs/attack-exposure-learn#attack_exposure_scores) of this toxic combination. The score is a measure of how much this toxic combination exposes one or more high-value resources to potential attack. */
		attackExposureScore: Option[BigDecimal] = None,
	  /** List of resource names of findings associated with this toxic combination. For example, `organizations/123/sources/456/findings/789`. */
		relatedFindings: Option[List[String]] = None
	)
	
	object GoogleCloudSecuritycenterV2GroupMembership {
		enum GroupTypeEnum extends Enum[GroupTypeEnum] { case GROUP_TYPE_UNSPECIFIED, GROUP_TYPE_TOXIC_COMBINATION }
	}
	case class GoogleCloudSecuritycenterV2GroupMembership(
	  /** Type of group. */
		groupType: Option[Schema.GoogleCloudSecuritycenterV2GroupMembership.GroupTypeEnum] = None,
	  /** ID of the group. */
		groupId: Option[String] = None
	)
	
	case class GoogleCloudSecuritycenterV2Disk(
	  /** The name of the disk, for example, "https://www.googleapis.com/compute/v1/projects/project-id/zones/zone-id/disks/disk-id". */
		name: Option[String] = None
	)
	
	object GoogleCloudSecuritycenterV2DataAccessEvent {
		enum OperationEnum extends Enum[OperationEnum] { case OPERATION_UNSPECIFIED, READ, MOVE, COPY }
	}
	case class GoogleCloudSecuritycenterV2DataAccessEvent(
	  /** Unique identifier for data access event. */
		eventId: Option[String] = None,
	  /** The email address of the principal that accessed the data. The principal could be a user account, service account, Google group, or other. */
		principalEmail: Option[String] = None,
	  /** The operation performed by the principal to access the data. */
		operation: Option[Schema.GoogleCloudSecuritycenterV2DataAccessEvent.OperationEnum] = None,
	  /** Timestamp of data access event. */
		eventTime: Option[String] = None
	)
	
	object GoogleCloudSecuritycenterV2DataFlowEvent {
		enum OperationEnum extends Enum[OperationEnum] { case OPERATION_UNSPECIFIED, READ, MOVE, COPY }
	}
	case class GoogleCloudSecuritycenterV2DataFlowEvent(
	  /** Unique identifier for data flow event. */
		eventId: Option[String] = None,
	  /** The email address of the principal that initiated the data flow event. The principal could be a user account, service account, Google group, or other. */
		principalEmail: Option[String] = None,
	  /** The operation performed by the principal for the data flow event. */
		operation: Option[Schema.GoogleCloudSecuritycenterV2DataFlowEvent.OperationEnum] = None,
	  /** Non-compliant location of the principal or the data destination. */
		violatedLocation: Option[String] = None,
	  /** Timestamp of data flow event. */
		eventTime: Option[String] = None
	)
	
	object GoogleCloudSecuritycenterV2Resource {
		enum CloudProviderEnum extends Enum[CloudProviderEnum] { case CLOUD_PROVIDER_UNSPECIFIED, GOOGLE_CLOUD_PLATFORM, AMAZON_WEB_SERVICES, MICROSOFT_AZURE }
	}
	case class GoogleCloudSecuritycenterV2Resource(
	  /** The full resource name of the resource. See: https://cloud.google.com/apis/design/resource_names#full_resource_name */
		name: Option[String] = None,
	  /** The human readable name of the resource. */
		displayName: Option[String] = None,
	  /** The full resource type of the resource. */
		`type`: Option[String] = None,
	  /** Indicates which cloud provider the finding is from. */
		cloudProvider: Option[Schema.GoogleCloudSecuritycenterV2Resource.CloudProviderEnum] = None,
	  /** The service or resource provider associated with the resource. */
		service: Option[String] = None,
	  /** The region or location of the service (if applicable). */
		location: Option[String] = None,
	  /** The GCP metadata associated with the finding. */
		gcpMetadata: Option[Schema.GcpMetadata] = None,
	  /** The AWS metadata associated with the finding. */
		awsMetadata: Option[Schema.GoogleCloudSecuritycenterV2AwsMetadata] = None,
	  /** The Azure metadata associated with the finding. */
		azureMetadata: Option[Schema.GoogleCloudSecuritycenterV2AzureMetadata] = None,
	  /** Provides the path to the resource within the resource hierarchy. */
		resourcePath: Option[Schema.GoogleCloudSecuritycenterV2ResourcePath] = None,
	  /** A string representation of the resource path. For Google Cloud, it has the format of `organizations/{organization_id}/folders/{folder_id}/folders/{folder_id}/projects/{project_id}` where there can be any number of folders. For AWS, it has the format of `org/{organization_id}/ou/{organizational_unit_id}/ou/{organizational_unit_id}/account/{account_id}` where there can be any number of organizational units. For Azure, it has the format of `mg/{management_group_id}/mg/{management_group_id}/subscription/{subscription_id}/rg/{resource_group_name}` where there can be any number of management groups. */
		resourcePathString: Option[String] = None
	)
	
	case class GcpMetadata(
	  /** The full resource name of project that the resource belongs to. */
		project: Option[String] = None,
	  /** The project ID that the resource belongs to. */
		projectDisplayName: Option[String] = None,
	  /** The full resource name of resource's parent. */
		parent: Option[String] = None,
	  /** The human readable name of resource's parent. */
		parentDisplayName: Option[String] = None,
	  /** Output only. Contains a Folder message for each folder in the assets ancestry. The first folder is the deepest nested folder, and the last folder is the folder directly under the Organization. */
		folders: Option[List[Schema.GoogleCloudSecuritycenterV2Folder]] = None,
	  /** The name of the organization that the resource belongs to. */
		organization: Option[String] = None
	)
	
	case class GoogleCloudSecuritycenterV2Folder(
	  /** Full resource name of this folder. See: https://cloud.google.com/apis/design/resource_names#full_resource_name */
		resourceFolder: Option[String] = None,
	  /** The user defined display name for this folder. */
		resourceFolderDisplayName: Option[String] = None
	)
	
	case class GoogleCloudSecuritycenterV2AwsMetadata(
	  /** The AWS organization associated with the resource. */
		organization: Option[Schema.GoogleCloudSecuritycenterV2AwsOrganization] = None,
	  /** A list of AWS organizational units associated with the resource, ordered from lowest level (closest to the account) to highest level. */
		organizationalUnits: Option[List[Schema.GoogleCloudSecuritycenterV2AwsOrganizationalUnit]] = None,
	  /** The AWS account associated with the resource. */
		account: Option[Schema.GoogleCloudSecuritycenterV2AwsAccount] = None
	)
	
	case class GoogleCloudSecuritycenterV2AwsOrganization(
	  /** The unique identifier (ID) for the organization. The regex pattern for an organization ID string requires "o-" followed by from 10 to 32 lowercase letters or digits. */
		id: Option[String] = None
	)
	
	case class GoogleCloudSecuritycenterV2AwsOrganizationalUnit(
	  /** The unique identifier (ID) associated with this OU. The regex pattern for an organizational unit ID string requires "ou-" followed by from 4 to 32 lowercase letters or digits (the ID of the root that contains the OU). This string is followed by a second "-" dash and from 8 to 32 additional lowercase letters or digits. For example, "ou-ab12-cd34ef56". */
		id: Option[String] = None,
	  /** The friendly name of the OU. */
		name: Option[String] = None
	)
	
	case class GoogleCloudSecuritycenterV2AwsAccount(
	  /** The unique identifier (ID) of the account, containing exactly 12 digits. */
		id: Option[String] = None,
	  /** The friendly name of this account. */
		name: Option[String] = None
	)
	
	case class GoogleCloudSecuritycenterV2AzureMetadata(
	  /** A list of Azure management groups associated with the resource, ordered from lowest level (closest to the subscription) to highest level. */
		managementGroups: Option[List[Schema.GoogleCloudSecuritycenterV2AzureManagementGroup]] = None,
	  /** The Azure subscription associated with the resource. */
		subscription: Option[Schema.GoogleCloudSecuritycenterV2AzureSubscription] = None,
	  /** The Azure resource group associated with the resource. */
		resourceGroup: Option[Schema.GoogleCloudSecuritycenterV2AzureResourceGroup] = None,
	  /** The Azure Entra tenant associated with the resource. */
		tenant: Option[Schema.GoogleCloudSecuritycenterV2AzureTenant] = None
	)
	
	case class GoogleCloudSecuritycenterV2AzureManagementGroup(
	  /** The UUID of the Azure management group, for example, `20000000-0001-0000-0000-000000000000`. */
		id: Option[String] = None,
	  /** The display name of the Azure management group. */
		displayName: Option[String] = None
	)
	
	case class GoogleCloudSecuritycenterV2AzureSubscription(
	  /** The UUID of the Azure subscription, for example, `291bba3f-e0a5-47bc-a099-3bdcb2a50a05`. */
		id: Option[String] = None,
	  /** The display name of the Azure subscription. */
		displayName: Option[String] = None
	)
	
	case class GoogleCloudSecuritycenterV2AzureResourceGroup(
	  /** The ID of the Azure resource group. */
		id: Option[String] = None,
	  /** The name of the Azure resource group. This is not a UUID. */
		name: Option[String] = None
	)
	
	case class GoogleCloudSecuritycenterV2AzureTenant(
	  /** The ID of the Microsoft Entra tenant, for example, "a11aaa11-aa11-1aa1-11aa-1aaa11a". */
		id: Option[String] = None,
	  /** The display name of the Azure tenant. */
		displayName: Option[String] = None
	)
	
	case class GoogleCloudSecuritycenterV2ResourcePath(
	  /** The list of nodes that make the up resource path, ordered from lowest level to highest level. */
		nodes: Option[List[Schema.GoogleCloudSecuritycenterV2ResourcePathNode]] = None
	)
	
	object GoogleCloudSecuritycenterV2ResourcePathNode {
		enum NodeTypeEnum extends Enum[NodeTypeEnum] { case RESOURCE_PATH_NODE_TYPE_UNSPECIFIED, GCP_ORGANIZATION, GCP_FOLDER, GCP_PROJECT, AWS_ORGANIZATION, AWS_ORGANIZATIONAL_UNIT, AWS_ACCOUNT, AZURE_MANAGEMENT_GROUP, AZURE_SUBSCRIPTION, AZURE_RESOURCE_GROUP }
	}
	case class GoogleCloudSecuritycenterV2ResourcePathNode(
	  /** The type of resource this node represents. */
		nodeType: Option[Schema.GoogleCloudSecuritycenterV2ResourcePathNode.NodeTypeEnum] = None,
	  /** The ID of the resource this node represents. */
		id: Option[String] = None,
	  /** The display name of the resource this node represents. */
		displayName: Option[String] = None
	)
	
	object GoogleCloudSecuritycenterV2ResourceValueConfig {
		enum ResourceValueEnum extends Enum[ResourceValueEnum] { case RESOURCE_VALUE_UNSPECIFIED, HIGH, MEDIUM, LOW, NONE }
		enum CloudProviderEnum extends Enum[CloudProviderEnum] { case CLOUD_PROVIDER_UNSPECIFIED, GOOGLE_CLOUD_PLATFORM, AMAZON_WEB_SERVICES, MICROSOFT_AZURE }
	}
	case class GoogleCloudSecuritycenterV2ResourceValueConfig(
	  /** Identifier. Name for the resource value configuration */
		name: Option[String] = None,
	  /** Resource value level this expression represents Only required when there is no Sensitive Data Protection mapping in the request */
		resourceValue: Option[Schema.GoogleCloudSecuritycenterV2ResourceValueConfig.ResourceValueEnum] = None,
	  /** Tag values combined with `AND` to check against. For Google Cloud resources, they are tag value IDs in the form of "tagValues/123". Example: `[ "tagValues/123", "tagValues/456", "tagValues/789" ]` https://cloud.google.com/resource-manager/docs/tags/tags-creating-and-managing */
		tagValues: Option[List[String]] = None,
	  /** Apply resource_value only to resources that match resource_type. resource_type will be checked with `AND` of other resources. For example, "storage.googleapis.com/Bucket" with resource_value "HIGH" will apply "HIGH" value only to "storage.googleapis.com/Bucket" resources. */
		resourceType: Option[String] = None,
	  /** Project or folder to scope this configuration to. For example, "project/456" would apply this configuration only to resources in "project/456" scope and will be checked with `AND` of other resources. */
		scope: Option[String] = None,
	  /** List of resource labels to search for, evaluated with `AND`. For example, "resource_labels_selector": {"key": "value", "env": "prod"} will match resources with labels "key": "value" `AND` "env": "prod" https://cloud.google.com/resource-manager/docs/creating-managing-labels */
		resourceLabelsSelector: Option[Map[String, String]] = None,
	  /** Description of the resource value configuration. */
		description: Option[String] = None,
	  /** Output only. Timestamp this resource value configuration was created. */
		createTime: Option[String] = None,
	  /** Output only. Timestamp this resource value configuration was last updated. */
		updateTime: Option[String] = None,
	  /** Cloud provider this configuration applies to */
		cloudProvider: Option[Schema.GoogleCloudSecuritycenterV2ResourceValueConfig.CloudProviderEnum] = None,
	  /** A mapping of the sensitivity on Sensitive Data Protection finding to resource values. This mapping can only be used in combination with a resource_type that is related to BigQuery, e.g. "bigquery.googleapis.com/Dataset". */
		sensitiveDataProtectionMapping: Option[Schema.GoogleCloudSecuritycenterV2SensitiveDataProtectionMapping] = None
	)
	
	object GoogleCloudSecuritycenterV2SensitiveDataProtectionMapping {
		enum HighSensitivityMappingEnum extends Enum[HighSensitivityMappingEnum] { case RESOURCE_VALUE_UNSPECIFIED, HIGH, MEDIUM, LOW, NONE }
		enum MediumSensitivityMappingEnum extends Enum[MediumSensitivityMappingEnum] { case RESOURCE_VALUE_UNSPECIFIED, HIGH, MEDIUM, LOW, NONE }
	}
	case class GoogleCloudSecuritycenterV2SensitiveDataProtectionMapping(
	  /** Resource value mapping for high-sensitivity Sensitive Data Protection findings */
		highSensitivityMapping: Option[Schema.GoogleCloudSecuritycenterV2SensitiveDataProtectionMapping.HighSensitivityMappingEnum] = None,
	  /** Resource value mapping for medium-sensitivity Sensitive Data Protection findings */
		mediumSensitivityMapping: Option[Schema.GoogleCloudSecuritycenterV2SensitiveDataProtectionMapping.MediumSensitivityMappingEnum] = None
	)
	
	object ComplianceSnapshot {
		enum CloudProviderEnum extends Enum[CloudProviderEnum] { case CLOUD_PROVIDER_UNSPECIFIED, GOOGLE_CLOUD_PLATFORM, AMAZON_WEB_SERVICES, MICROSOFT_AZURE }
	}
	case class ComplianceSnapshot(
	  /** The compliance snapshot name. Format: //sources//complianceSnapshots/ */
		name: Option[String] = None,
	  /** The cloud provider for the compliance snapshot. */
		cloudProvider: Option[Schema.ComplianceSnapshot.CloudProviderEnum] = None,
	  /** The category of Findings matching. */
		category: Option[String] = None,
	  /** The leaf container resource name that is closest to the snapshot. */
		leafContainerResource: Option[String] = None,
	  /** The compliance standard (ie CIS). */
		complianceStandard: Option[String] = None,
	  /** The compliance version (ie 1.3) in CIS 1.3. */
		complianceVersion: Option[String] = None,
	  /** Total count of findings for the given properties. */
		count: Option[String] = None,
	  /** The snapshot time of the snapshot. */
		snapshotTime: Option[String] = None
	)
	
	object VulnerabilitySnapshot {
		enum CloudProviderEnum extends Enum[CloudProviderEnum] { case CLOUD_PROVIDER_UNSPECIFIED, GOOGLE_CLOUD_PLATFORM, AMAZON_WEB_SERVICES, MICROSOFT_AZURE }
	}
	case class VulnerabilitySnapshot(
	  /** Identifier. The vulnerability snapshot name. Format: //locations//vulnerabilitySnapshots/ */
		name: Option[String] = None,
	  /** The cloud provider for the vulnerability snapshot. */
		cloudProvider: Option[Schema.VulnerabilitySnapshot.CloudProviderEnum] = None,
	  /** The time that the snapshot was taken. */
		snapshotTime: Option[String] = None,
	  /** The vulnerability count by severity. */
		findingCount: Option[Schema.VulnerabilityCountBySeverity] = None
	)
	
	case class VulnerabilityCountBySeverity(
	  /** Key is the Severity enum. */
		severityToFindingCount: Option[Map[String, String]] = None
	)
	
	case class GoogleCloudSecuritycenterV1p1beta1NotificationMessage(
	  /** Name of the notification config that generated current notification. */
		notificationConfigName: Option[String] = None,
	  /** If it's a Finding based notification config, this field will be populated. */
		finding: Option[Schema.GoogleCloudSecuritycenterV1p1beta1Finding] = None,
	  /** The Cloud resource tied to the notification. */
		resource: Option[Schema.GoogleCloudSecuritycenterV1p1beta1Resource] = None
	)
	
	object GoogleCloudSecuritycenterV1p1beta1Finding {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, ACTIVE, INACTIVE }
		enum SeverityEnum extends Enum[SeverityEnum] { case SEVERITY_UNSPECIFIED, CRITICAL, HIGH, MEDIUM, LOW }
	}
	case class GoogleCloudSecuritycenterV1p1beta1Finding(
	  /** The relative resource name of this finding. See: https://cloud.google.com/apis/design/resource_names#relative_resource_name Example: "organizations/{organization_id}/sources/{source_id}/findings/{finding_id}" */
		name: Option[String] = None,
	  /** The relative resource name of the source the finding belongs to. See: https://cloud.google.com/apis/design/resource_names#relative_resource_name This field is immutable after creation time. For example: "organizations/{organization_id}/sources/{source_id}" */
		parent: Option[String] = None,
	  /** For findings on Google Cloud resources, the full resource name of the Google Cloud resource this finding is for. See: https://cloud.google.com/apis/design/resource_names#full_resource_name When the finding is for a non-Google Cloud resource, the resourceName can be a customer or partner defined string. This field is immutable after creation time. */
		resourceName: Option[String] = None,
	  /** The state of the finding. */
		state: Option[Schema.GoogleCloudSecuritycenterV1p1beta1Finding.StateEnum] = None,
	  /** The additional taxonomy group within findings from a given source. This field is immutable after creation time. Example: "XSS_FLASH_INJECTION" */
		category: Option[String] = None,
	  /** The URI that, if available, points to a web page outside of Security Command Center where additional information about the finding can be found. This field is guaranteed to be either empty or a well formed URL. */
		externalUri: Option[String] = None,
	  /** Source specific properties. These properties are managed by the source that writes the finding. The key names in the source_properties map must be between 1 and 255 characters, and must start with a letter and contain alphanumeric characters or underscores only. */
		sourceProperties: Option[Map[String, JsValue]] = None,
	  /** Output only. User specified security marks. These marks are entirely managed by the user and come from the SecurityMarks resource that belongs to the finding. */
		securityMarks: Option[Schema.GoogleCloudSecuritycenterV1p1beta1SecurityMarks] = None,
	  /** The time at which the event took place, or when an update to the finding occurred. For example, if the finding represents an open firewall it would capture the time the detector believes the firewall became open. The accuracy is determined by the detector. If the finding were to be resolved afterward, this time would reflect when the finding was resolved. Must not be set to a value greater than the current timestamp. */
		eventTime: Option[String] = None,
	  /** The time at which the finding was created in Security Command Center. */
		createTime: Option[String] = None,
	  /** The severity of the finding. This field is managed by the source that writes the finding. */
		severity: Option[Schema.GoogleCloudSecuritycenterV1p1beta1Finding.SeverityEnum] = None,
	  /** The canonical name of the finding. It's either "organizations/{organization_id}/sources/{source_id}/findings/{finding_id}", "folders/{folder_id}/sources/{source_id}/findings/{finding_id}" or "projects/{project_number}/sources/{source_id}/findings/{finding_id}", depending on the closest CRM ancestor of the resource associated with the finding. */
		canonicalName: Option[String] = None
	)
	
	case class GoogleCloudSecuritycenterV1p1beta1SecurityMarks(
	  /** The relative resource name of the SecurityMarks. See: https://cloud.google.com/apis/design/resource_names#relative_resource_name Examples: "organizations/{organization_id}/assets/{asset_id}/securityMarks" "organizations/{organization_id}/sources/{source_id}/findings/{finding_id}/securityMarks". */
		name: Option[String] = None,
	  /** Mutable user specified security marks belonging to the parent resource. Constraints are as follows: &#42; Keys and values are treated as case insensitive &#42; Keys must be between 1 - 256 characters (inclusive) &#42; Keys must be letters, numbers, underscores, or dashes &#42; Values have leading and trailing whitespace trimmed, remaining characters must be between 1 - 4096 characters (inclusive) */
		marks: Option[Map[String, String]] = None,
	  /** The canonical name of the marks. Examples: "organizations/{organization_id}/assets/{asset_id}/securityMarks" "folders/{folder_id}/assets/{asset_id}/securityMarks" "projects/{project_number}/assets/{asset_id}/securityMarks" "organizations/{organization_id}/sources/{source_id}/findings/{finding_id}/securityMarks" "folders/{folder_id}/sources/{source_id}/findings/{finding_id}/securityMarks" "projects/{project_number}/sources/{source_id}/findings/{finding_id}/securityMarks" */
		canonicalName: Option[String] = None
	)
	
	case class GoogleCloudSecuritycenterV1p1beta1Resource(
	  /** The full resource name of the resource. See: https://cloud.google.com/apis/design/resource_names#full_resource_name */
		name: Option[String] = None,
	  /** The full resource name of project that the resource belongs to. */
		project: Option[String] = None,
	  /** The project id that the resource belongs to. */
		projectDisplayName: Option[String] = None,
	  /** The full resource name of resource's parent. */
		parent: Option[String] = None,
	  /** The human readable name of resource's parent. */
		parentDisplayName: Option[String] = None,
	  /** Output only. Contains a Folder message for each folder in the assets ancestry. The first folder is the deepest nested folder, and the last folder is the folder directly under the Organization. */
		folders: Option[List[Schema.GoogleCloudSecuritycenterV1p1beta1Folder]] = None
	)
	
	case class GoogleCloudSecuritycenterV1p1beta1Folder(
	  /** Full resource name of this folder. See: https://cloud.google.com/apis/design/resource_names#full_resource_name */
		resourceFolder: Option[String] = None,
	  /** The user defined display name for this folder. */
		resourceFolderDisplayName: Option[String] = None
	)
	
	object GoogleCloudSecuritycenterV1p1beta1RunAssetDiscoveryResponse {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, COMPLETED, SUPERSEDED, TERMINATED }
	}
	case class GoogleCloudSecuritycenterV1p1beta1RunAssetDiscoveryResponse(
	  /** The state of an asset discovery run. */
		state: Option[Schema.GoogleCloudSecuritycenterV1p1beta1RunAssetDiscoveryResponse.StateEnum] = None,
	  /** The duration between asset discovery run start and end */
		duration: Option[String] = None
	)
}
