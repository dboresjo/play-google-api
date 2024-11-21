package com.boresjo.play.api.google.storage

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	object Bucket {
		case class BillingItem(
		  /** When set to true, Requester Pays is enabled for this bucket. */
			requesterPays: Option[Boolean] = None
		)
		
		case class CorsItem(
		  /** The value, in seconds, to return in the  Access-Control-Max-Age header used in preflight responses. */
			maxAgeSeconds: Option[Int] = None,
		  /** The list of HTTP methods on which to include CORS response headers, (GET, OPTIONS, POST, etc) Note: "&#42;" is permitted in the list of methods, and means "any method". */
			method: Option[List[String]] = None,
		  /** The list of Origins eligible to receive CORS response headers. Note: "&#42;" is permitted in the list of origins, and means "any Origin". */
			origin: Option[List[String]] = None,
		  /** The list of HTTP headers other than the simple response headers to give permission for the user-agent to share across domains. */
			responseHeader: Option[List[String]] = None
		)
		
		case class CustomPlacementConfigItem(
		  /** The list of regional locations in which data is placed. */
			dataLocations: Option[List[String]] = None
		)
		
		case class EncryptionItem(
		  /** A Cloud KMS key that will be used to encrypt objects inserted into this bucket, if no encryption method is specified. */
			defaultKmsKeyName: Option[String] = None
		)
		
		case class HierarchicalNamespaceItem(
		  /** When set to true, hierarchical namespace is enabled for this bucket. */
			enabled: Option[Boolean] = None
		)
		
		object IamConfigurationItem {
			case class BucketPolicyOnlyItem(
			  /** If set, access is controlled only by bucket-level or above IAM policies. */
				enabled: Option[Boolean] = None,
			  /** The deadline for changing iamConfiguration.bucketPolicyOnly.enabled from true to false in RFC 3339 format. iamConfiguration.bucketPolicyOnly.enabled may be changed from true to false until the locked time, after which the field is immutable. */
				lockedTime: Option[String] = None
			)
			
			case class UniformBucketLevelAccessItem(
			  /** If set, access is controlled only by bucket-level or above IAM policies. */
				enabled: Option[Boolean] = None,
			  /** The deadline for changing iamConfiguration.uniformBucketLevelAccess.enabled from true to false in RFC 3339  format. iamConfiguration.uniformBucketLevelAccess.enabled may be changed from true to false until the locked time, after which the field is immutable. */
				lockedTime: Option[String] = None
			)
		}
		case class IamConfigurationItem(
		  /** The bucket's uniform bucket-level access configuration. The feature was formerly known as Bucket Policy Only. For backward compatibility, this field will be populated with identical information as the uniformBucketLevelAccess field. We recommend using the uniformBucketLevelAccess field to enable and disable the feature. */
			bucketPolicyOnly: Option[Schema.Bucket.IamConfigurationItem.BucketPolicyOnlyItem] = None,
		  /** The bucket's uniform bucket-level access configuration. */
			uniformBucketLevelAccess: Option[Schema.Bucket.IamConfigurationItem.UniformBucketLevelAccessItem] = None,
		  /** The bucket's Public Access Prevention configuration. Currently, 'inherited' and 'enforced' are supported. */
			publicAccessPrevention: Option[String] = None
		)
		
		object IpFilterItem {
			case class PublicNetworkSourceItem(
			  /** The list of public IPv4, IPv6 cidr ranges that are allowed to access the bucket. */
				allowedIpCidrRanges: Option[List[String]] = None
			)
			
			case class VpcNetworkSourcesItem(
			  /** Name of the network. Format: projects/{PROJECT_ID}/global/networks/{NETWORK_NAME} */
				network: Option[String] = None,
			  /** The list of IPv4, IPv6 cidr ranges subnetworks that are allowed to access the bucket. */
				allowedIpCidrRanges: Option[List[String]] = None
			)
		}
		case class IpFilterItem(
		  /** The mode of the IP filter. Valid values are 'Enabled' and 'Disabled'. */
			mode: Option[String] = None,
		  /** The public network source of the bucket's IP filter. */
			publicNetworkSource: Option[Schema.Bucket.IpFilterItem.PublicNetworkSourceItem] = None,
		  /** The list of [VPC network](https://cloud.google.com/vpc/docs/vpc) sources of the bucket's IP filter. */
			vpcNetworkSources: Option[List[Schema.Bucket.IpFilterItem.VpcNetworkSourcesItem]] = None
		)
		
		object LifecycleItem {
			object RuleItem {
				case class ActionItem(
				  /** Target storage class. Required iff the type of the action is SetStorageClass. */
					storageClass: Option[String] = None,
				  /** Type of the action. Currently, only Delete, SetStorageClass, and AbortIncompleteMultipartUpload are supported. */
					`type`: Option[String] = None
				)
				
				case class ConditionItem(
				  /** Age of an object (in days). This condition is satisfied when an object reaches the specified age. */
					age: Option[Int] = None,
				  /** A date in RFC 3339 format with only the date part (for instance, "2013-01-15"). This condition is satisfied when an object is created before midnight of the specified date in UTC. */
					createdBefore: Option[String] = None,
				  /** A date in RFC 3339 format with only the date part (for instance, "2013-01-15"). This condition is satisfied when the custom time on an object is before this date in UTC. */
					customTimeBefore: Option[String] = None,
				  /** Number of days elapsed since the user-specified timestamp set on an object. The condition is satisfied if the days elapsed is at least this number. If no custom timestamp is specified on an object, the condition does not apply. */
					daysSinceCustomTime: Option[Int] = None,
				  /** Number of days elapsed since the noncurrent timestamp of an object. The condition is satisfied if the days elapsed is at least this number. This condition is relevant only for versioned objects. The value of the field must be a nonnegative integer. If it's zero, the object version will become eligible for Lifecycle action as soon as it becomes noncurrent. */
					daysSinceNoncurrentTime: Option[Int] = None,
				  /** Relevant only for versioned objects. If the value is true, this condition matches live objects; if the value is false, it matches archived objects. */
					isLive: Option[Boolean] = None,
				  /** A regular expression that satisfies the RE2 syntax. This condition is satisfied when the name of the object matches the RE2 pattern. Note: This feature is currently in the "Early Access" launch stage and is only available to a whitelisted set of users; that means that this feature may be changed in backward-incompatible ways and that it is not guaranteed to be released. */
					matchesPattern: Option[String] = None,
				  /** List of object name prefixes. This condition will be satisfied when at least one of the prefixes exactly matches the beginning of the object name. */
					matchesPrefix: Option[List[String]] = None,
				  /** List of object name suffixes. This condition will be satisfied when at least one of the suffixes exactly matches the end of the object name. */
					matchesSuffix: Option[List[String]] = None,
				  /** Objects having any of the storage classes specified by this condition will be matched. Values include MULTI_REGIONAL, REGIONAL, NEARLINE, COLDLINE, ARCHIVE, STANDARD, and DURABLE_REDUCED_AVAILABILITY. */
					matchesStorageClass: Option[List[String]] = None,
				  /** A date in RFC 3339 format with only the date part (for instance, "2013-01-15"). This condition is satisfied when the noncurrent time on an object is before this date in UTC. This condition is relevant only for versioned objects. */
					noncurrentTimeBefore: Option[String] = None,
				  /** Relevant only for versioned objects. If the value is N, this condition is satisfied when there are at least N versions (including the live version) newer than this version of the object. */
					numNewerVersions: Option[Int] = None
				)
			}
			case class RuleItem(
			  /** The action to take. */
				action: Option[Schema.Bucket.LifecycleItem.RuleItem.ActionItem] = None,
			  /** The condition(s) under which the action will be taken. */
				condition: Option[Schema.Bucket.LifecycleItem.RuleItem.ConditionItem] = None
			)
		}
		case class LifecycleItem(
		  /** A lifecycle management rule, which is made of an action to take and the condition(s) under which the action will be taken. */
			rule: Option[List[Schema.Bucket.LifecycleItem.RuleItem]] = None
		)
		
		case class AutoclassItem(
		  /** Whether or not Autoclass is enabled on this bucket */
			enabled: Option[Boolean] = None,
		  /** A date and time in RFC 3339 format representing the instant at which "enabled" was last toggled. */
			toggleTime: Option[String] = None,
		  /** The storage class that objects in the bucket eventually transition to if they are not read for a certain length of time. Valid values are NEARLINE and ARCHIVE. */
			terminalStorageClass: Option[String] = None,
		  /** A date and time in RFC 3339 format representing the time of the most recent update to "terminalStorageClass". */
			terminalStorageClassUpdateTime: Option[String] = None
		)
		
		case class LoggingItem(
		  /** The destination bucket where the current bucket's logs should be placed. */
			logBucket: Option[String] = None,
		  /** A prefix for log object names. */
			logObjectPrefix: Option[String] = None
		)
		
		case class OwnerItem(
		  /** The entity, in the form project-owner-projectId. */
			entity: Option[String] = None,
		  /** The ID for the entity. */
			entityId: Option[String] = None
		)
		
		case class RetentionPolicyItem(
		  /** Server-determined value that indicates the time from which policy was enforced and effective. This value is in RFC 3339 format. */
			effectiveTime: Option[String] = None,
		  /** Once locked, an object retention policy cannot be modified. */
			isLocked: Option[Boolean] = None,
		  /** The duration in seconds that objects need to be retained. Retention duration must be greater than zero and less than 100 years. Note that enforcement of retention periods less than a day is not guaranteed. Such periods should only be used for testing purposes. */
			retentionPeriod: Option[String] = None
		)
		
		case class ObjectRetentionItem(
		  /** The bucket's object retention mode. Can be Enabled. */
			mode: Option[String] = None
		)
		
		case class SoftDeletePolicyItem(
		  /** The duration in seconds that soft-deleted objects in the bucket will be retained and cannot be permanently deleted. */
			retentionDurationSeconds: Option[String] = None,
		  /** Server-determined value that indicates the time from which the policy, or one with a greater retention, was effective. This value is in RFC 3339 format. */
			effectiveTime: Option[String] = None
		)
		
		case class VersioningItem(
		  /** While set to true, versioning is fully enabled for this bucket. */
			enabled: Option[Boolean] = None
		)
		
		case class WebsiteItem(
		  /** If the requested object path is missing, the service will ensure the path has a trailing '/', append this suffix, and attempt to retrieve the resulting object. This allows the creation of index.html objects to represent directory pages. */
			mainPageSuffix: Option[String] = None,
		  /** If the requested object path is missing, and any mainPageSuffix object is missing, if applicable, the service will return the named object from this bucket as the content for a 404 Not Found result. */
			notFoundPage: Option[String] = None
		)
	}
	case class Bucket(
	  /** Access controls on the bucket. */
		acl: Option[List[Schema.BucketAccessControl]] = None,
	  /** The bucket's billing configuration. */
		billing: Option[Schema.Bucket.BillingItem] = None,
	  /** The bucket's Cross-Origin Resource Sharing (CORS) configuration. */
		cors: Option[List[Schema.Bucket.CorsItem]] = None,
	  /** The bucket's custom placement configuration for Custom Dual Regions. */
		customPlacementConfig: Option[Schema.Bucket.CustomPlacementConfigItem] = None,
	  /** The default value for event-based hold on newly created objects in this bucket. Event-based hold is a way to retain objects indefinitely until an event occurs, signified by the hold's release. After being released, such objects will be subject to bucket-level retention (if any). One sample use case of this flag is for banks to hold loan documents for at least 3 years after loan is paid in full. Here, bucket-level retention is 3 years and the event is loan being paid in full. In this example, these objects will be held intact for any number of years until the event has occurred (event-based hold on the object is released) and then 3 more years after that. That means retention duration of the objects begins from the moment event-based hold transitioned from true to false. Objects under event-based hold cannot be deleted, overwritten or archived until the hold is removed. */
		defaultEventBasedHold: Option[Boolean] = None,
	  /** Default access controls to apply to new objects when no ACL is provided. */
		defaultObjectAcl: Option[List[Schema.ObjectAccessControl]] = None,
	  /** Encryption configuration for a bucket. */
		encryption: Option[Schema.Bucket.EncryptionItem] = None,
	  /** HTTP 1.1 Entity tag for the bucket. */
		etag: Option[String] = None,
	  /** The bucket's hierarchical namespace configuration. */
		hierarchicalNamespace: Option[Schema.Bucket.HierarchicalNamespaceItem] = None,
	  /** The bucket's IAM configuration. */
		iamConfiguration: Option[Schema.Bucket.IamConfigurationItem] = None,
	  /** The ID of the bucket. For buckets, the id and name properties are the same. */
		id: Option[String] = None,
	  /** The bucket's IP filter configuration. Specifies the network sources that are allowed to access the operations on the bucket, as well as its underlying objects. Only enforced when the mode is set to 'Enabled'. */
		ipFilter: Option[Schema.Bucket.IpFilterItem] = None,
	  /** The kind of item this is. For buckets, this is always storage#bucket. */
		kind: Option[String] = Some("""storage#bucket"""),
	  /** User-provided labels, in key/value pairs. */
		labels: Option[Map[String, String]] = None,
	  /** The bucket's lifecycle configuration. See [Lifecycle Management](https://cloud.google.com/storage/docs/lifecycle) for more information. */
		lifecycle: Option[Schema.Bucket.LifecycleItem] = None,
	  /** The bucket's Autoclass configuration. */
		autoclass: Option[Schema.Bucket.AutoclassItem] = None,
	  /** The location of the bucket. Object data for objects in the bucket resides in physical storage within this region. Defaults to US. See the [Developer's Guide](https://cloud.google.com/storage/docs/locations) for the authoritative list. */
		location: Option[String] = None,
	  /** The type of the bucket location. */
		locationType: Option[String] = None,
	  /** The bucket's logging configuration, which defines the destination bucket and optional name prefix for the current bucket's logs. */
		logging: Option[Schema.Bucket.LoggingItem] = None,
	  /** The generation of this bucket. */
		generation: Option[String] = None,
	  /** The metadata generation of this bucket. */
		metageneration: Option[String] = None,
	  /** The name of the bucket. */
		name: Option[String] = None,
	  /** The owner of the bucket. This is always the project team's owner group. */
		owner: Option[Schema.Bucket.OwnerItem] = None,
	  /** The project number of the project the bucket belongs to. */
		projectNumber: Option[String] = None,
	  /** The bucket's retention policy. The retention policy enforces a minimum retention time for all objects contained in the bucket, based on their creation time. Any attempt to overwrite or delete objects younger than the retention period will result in a PERMISSION_DENIED error. An unlocked retention policy can be modified or removed from the bucket via a storage.buckets.update operation. A locked retention policy cannot be removed or shortened in duration for the lifetime of the bucket. Attempting to remove or decrease period of a locked retention policy will result in a PERMISSION_DENIED error. */
		retentionPolicy: Option[Schema.Bucket.RetentionPolicyItem] = None,
	  /** The bucket's object retention config. */
		objectRetention: Option[Schema.Bucket.ObjectRetentionItem] = None,
	  /** The Recovery Point Objective (RPO) of this bucket. Set to ASYNC_TURBO to turn on Turbo Replication on a bucket. */
		rpo: Option[String] = None,
	  /** The URI of this bucket. */
		selfLink: Option[String] = None,
	  /** The bucket's soft delete policy, which defines the period of time that soft-deleted objects will be retained, and cannot be permanently deleted. */
		softDeletePolicy: Option[Schema.Bucket.SoftDeletePolicyItem] = None,
	  /** The bucket's default storage class, used whenever no storageClass is specified for a newly-created object. This defines how objects in the bucket are stored and determines the SLA and the cost of storage. Values include MULTI_REGIONAL, REGIONAL, STANDARD, NEARLINE, COLDLINE, ARCHIVE, and DURABLE_REDUCED_AVAILABILITY. If this value is not specified when the bucket is created, it will default to STANDARD. For more information, see [Storage Classes](https://cloud.google.com/storage/docs/storage-classes). */
		storageClass: Option[String] = None,
	  /** The creation time of the bucket in RFC 3339 format. */
		timeCreated: Option[String] = None,
	  /** The modification time of the bucket in RFC 3339 format. */
		updated: Option[String] = None,
	  /** The soft delete time of the bucket in RFC 3339 format. */
		softDeleteTime: Option[String] = None,
	  /** The hard delete time of the bucket in RFC 3339 format. */
		hardDeleteTime: Option[String] = None,
	  /** The bucket's versioning configuration. */
		versioning: Option[Schema.Bucket.VersioningItem] = None,
	  /** The bucket's website configuration, controlling how the service behaves when accessing bucket contents as a web site. See the [Static Website Examples](https://cloud.google.com/storage/docs/static-website) for more information. */
		website: Option[Schema.Bucket.WebsiteItem] = None,
	  /** Reserved for future use. */
		satisfiesPZS: Option[Boolean] = None,
	  /** Reserved for future use. */
		satisfiesPZI: Option[Boolean] = None
	)
	
	case class AdvanceRelocateBucketOperationRequest(
	  /** Specifies the duration after which the relocation will revert to the sync stage if the relocation hasn't succeeded. Optional, if not supplied, a default value of 12h will be used. */
		ttl: Option[String] = None,
	  /** Specifies the time when the relocation will revert to the sync stage if the relocation hasn't succeeded. */
		expireTime: Option[String] = None
	)
	
	case class AnywhereCache(
	  /** The kind of item this is. For Anywhere Cache, this is always storage#anywhereCache. */
		kind: Option[String] = Some("""storage#anywhereCache"""),
	  /** The ID of the resource, including the project number, bucket name and anywhere cache ID. */
		id: Option[String] = None,
	  /** The link to this cache instance. */
		selfLink: Option[String] = None,
	  /** The name of the bucket containing this cache instance. */
		bucket: Option[String] = None,
	  /** The ID of the Anywhere cache instance. */
		anywhereCacheId: Option[String] = None,
	  /** The zone in which the cache instance is running. For example, us-central1-a. */
		zone: Option[String] = None,
	  /** The current state of the cache instance. */
		state: Option[String] = None,
	  /** The creation time of the cache instance in RFC 3339 format. */
		createTime: Option[String] = None,
	  /** The modification time of the cache instance metadata in RFC 3339 format. */
		updateTime: Option[String] = None,
	  /** The TTL of all cache entries in whole seconds. e.g., "7200s".  */
		ttl: Option[String] = None,
	  /** The cache-level entry admission policy. */
		admissionPolicy: Option[String] = None,
	  /** True if the cache instance has an active Update long-running operation. */
		pendingUpdate: Option[Boolean] = None
	)
	
	case class AnywhereCaches(
	  /** The kind of item this is. For lists of Anywhere Caches, this is always storage#anywhereCaches. */
		kind: Option[String] = Some("""storage#anywhereCaches"""),
	  /** The continuation token, used to page through large result sets. Provide this value in a subsequent request to return the next page of results. */
		nextPageToken: Option[String] = None,
	  /** The list of items. */
		items: Option[List[Schema.AnywhereCache]] = None
	)
	
	object BucketAccessControl {
		case class ProjectTeamItem(
		  /** The project number. */
			projectNumber: Option[String] = None,
		  /** The team. */
			team: Option[String] = None
		)
	}
	case class BucketAccessControl(
	  /** The name of the bucket. */
		bucket: Option[String] = None,
	  /** The domain associated with the entity, if any. */
		domain: Option[String] = None,
	  /** The email address associated with the entity, if any. */
		email: Option[String] = None,
	  /** The entity holding the permission, in one of the following forms: 
	- user-userId 
	- user-email 
	- group-groupId 
	- group-email 
	- domain-domain 
	- project-team-projectId 
	- allUsers 
	- allAuthenticatedUsers Examples: 
	- The user liz@example.com would be user-liz@example.com. 
	- The group example@googlegroups.com would be group-example@googlegroups.com. 
	- To refer to all members of the Google Apps for Business domain example.com, the entity would be domain-example.com. */
		entity: Option[String] = None,
	  /** The ID for the entity, if any. */
		entityId: Option[String] = None,
	  /** HTTP 1.1 Entity tag for the access-control entry. */
		etag: Option[String] = None,
	  /** The ID of the access-control entry. */
		id: Option[String] = None,
	  /** The kind of item this is. For bucket access control entries, this is always storage#bucketAccessControl. */
		kind: Option[String] = Some("""storage#bucketAccessControl"""),
	  /** The project team associated with the entity, if any. */
		projectTeam: Option[Schema.BucketAccessControl.ProjectTeamItem] = None,
	  /** The access permission for the entity. */
		role: Option[String] = None,
	  /** The link to this access-control entry. */
		selfLink: Option[String] = None
	)
	
	case class BucketAccessControls(
	  /** The list of items. */
		items: Option[List[Schema.BucketAccessControl]] = None,
	  /** The kind of item this is. For lists of bucket access control entries, this is always storage#bucketAccessControls. */
		kind: Option[String] = Some("""storage#bucketAccessControls""")
	)
	
	object BucketStorageLayout {
		case class CustomPlacementConfigItem(
		  /** The list of regional locations in which data is placed. */
			dataLocations: Option[List[String]] = None
		)
		
		case class HierarchicalNamespaceItem(
		  /** When set to true, hierarchical namespace is enabled for this bucket. */
			enabled: Option[Boolean] = None
		)
	}
	case class BucketStorageLayout(
	  /** The name of the bucket. */
		bucket: Option[String] = None,
	  /** The bucket's custom placement configuration for Custom Dual Regions. */
		customPlacementConfig: Option[Schema.BucketStorageLayout.CustomPlacementConfigItem] = None,
	  /** The bucket's hierarchical namespace configuration. */
		hierarchicalNamespace: Option[Schema.BucketStorageLayout.HierarchicalNamespaceItem] = None,
	  /** The kind of item this is. For storage layout, this is always storage#storageLayout. */
		kind: Option[String] = Some("""storage#storageLayout"""),
	  /** The location of the bucket. */
		location: Option[String] = None,
	  /** The type of the bucket location. */
		locationType: Option[String] = None
	)
	
	case class Buckets(
	  /** The list of items. */
		items: Option[List[Schema.Bucket]] = None,
	  /** The kind of item this is. For lists of buckets, this is always storage#buckets. */
		kind: Option[String] = Some("""storage#buckets"""),
	  /** The continuation token, used to page through large result sets. Provide this value in a subsequent request to return the next page of results. */
		nextPageToken: Option[String] = None
	)
	
	case class Channel(
	  /** The address where notifications are delivered for this channel. */
		address: Option[String] = None,
	  /** Date and time of notification channel expiration, expressed as a Unix timestamp, in milliseconds. Optional. */
		expiration: Option[String] = None,
	  /** A UUID or similar unique string that identifies this channel. */
		id: Option[String] = None,
	  /** Identifies this as a notification channel used to watch for changes to a resource, which is "api#channel". */
		kind: Option[String] = Some("""api#channel"""),
	  /** Additional parameters controlling delivery channel behavior. Optional. */
		params: Option[Map[String, String]] = None,
	  /** A Boolean value to indicate whether payload is wanted. Optional. */
		payload: Option[Boolean] = None,
	  /** An opaque ID that identifies the resource being watched on this channel. Stable across different API versions. */
		resourceId: Option[String] = None,
	  /** A version-specific identifier for the watched resource. */
		resourceUri: Option[String] = None,
	  /** An arbitrary string delivered to the target address with each notification delivered over this channel. Optional. */
		token: Option[String] = None,
	  /** The type of delivery mechanism used for this channel. */
		`type`: Option[String] = None
	)
	
	object ComposeRequest {
		object SourceObjectsItem {
			case class ObjectPreconditionsItem(
			  /** Only perform the composition if the generation of the source object that would be used matches this value. If this value and a generation are both specified, they must be the same value or the call will fail. */
				ifGenerationMatch: Option[String] = None
			)
		}
		case class SourceObjectsItem(
		  /** The generation of this object to use as the source. */
			generation: Option[String] = None,
		  /** The source object's name. All source objects must reside in the same bucket. */
			name: Option[String] = None,
		  /** Conditions that must be met for this operation to execute. */
			objectPreconditions: Option[Schema.ComposeRequest.SourceObjectsItem.ObjectPreconditionsItem] = None
		)
	}
	case class ComposeRequest(
	  /** Properties of the resulting object. */
		destination: Option[Schema.Object] = None,
	  /** The kind of item this is. */
		kind: Option[String] = Some("""storage#composeRequest"""),
	  /** The list of source objects that will be concatenated into a single object. */
		sourceObjects: Option[List[Schema.ComposeRequest.SourceObjectsItem]] = None
	)
	
	object Folder {
		case class PendingRenameInfoItem(
		  /** The ID of the rename folder operation. */
			operationId: Option[String] = None
		)
	}
	case class Folder(
	  /** The name of the bucket containing this folder. */
		bucket: Option[String] = None,
	  /** The ID of the folder, including the bucket name, folder name. */
		id: Option[String] = None,
	  /** The kind of item this is. For folders, this is always storage#folder. */
		kind: Option[String] = Some("""storage#folder"""),
	  /** The version of the metadata for this folder. Used for preconditions and for detecting changes in metadata. */
		metageneration: Option[String] = None,
	  /** The name of the folder. Required if not specified by URL parameter. */
		name: Option[String] = None,
	  /** The link to this folder. */
		selfLink: Option[String] = None,
	  /** The creation time of the folder in RFC 3339 format. */
		createTime: Option[String] = None,
	  /** The modification time of the folder metadata in RFC 3339 format. */
		updateTime: Option[String] = None,
	  /** Only present if the folder is part of an ongoing rename folder operation. Contains information which can be used to query the operation status. */
		pendingRenameInfo: Option[Schema.Folder.PendingRenameInfoItem] = None
	)
	
	case class Folders(
	  /** The list of items. */
		items: Option[List[Schema.Folder]] = None,
	  /** The kind of item this is. For lists of folders, this is always storage#folders. */
		kind: Option[String] = Some("""storage#folders"""),
	  /** The continuation token, used to page through large result sets. Provide this value in a subsequent request to return the next page of results. */
		nextPageToken: Option[String] = None
	)
	
	case class Expr(
	  /** An optional description of the expression. This is a longer text which describes the expression, e.g. when hovered over it in a UI. */
		description: Option[String] = None,
	  /** Textual representation of an expression in Common Expression Language syntax. The application context of the containing message determines which well-known feature set of CEL is supported. */
		expression: Option[String] = None,
	  /** An optional string indicating the location of the expression for error reporting, e.g. a file name and a position in the file. */
		location: Option[String] = None,
	  /** An optional title for the expression, i.e. a short string describing its purpose. This can be used e.g. in UIs which allow to enter the expression. */
		title: Option[String] = None
	)
	
	case class GoogleLongrunningOperation(
	  /** If the value is "false", it means the operation is still in progress. If "true", the operation is completed, and either "error" or "response" is available. */
		done: Option[Boolean] = None,
	  /** The error result of the operation in case of failure or cancellation. */
		error: Option[Schema.GoogleRpcStatus] = None,
	  /** Service-specific metadata associated with the operation. It typically contains progress information and common metadata such as create time. Some services might not provide such metadata. Any method that returns a long-running operation should document the metadata type, if any. */
		metadata: Option[Map[String, JsValue]] = None,
	  /** The server-assigned name, which is only unique within the same service that originally returns it. If you use the default HTTP mapping, the "name" should be a resource name ending with "operations/{operationId}". */
		name: Option[String] = None,
	  /** The normal response of the operation in case of success. If the original method returns no data on success, such as "Delete", the response is google.protobuf.Empty. If the original method is standard Get/Create/Update, the response should be the resource. For other methods, the response should have the type "XxxResponse", where "Xxx" is the original method name. For example, if the original method name is "TakeSnapshot()", the inferred response type is "TakeSnapshotResponse". */
		response: Option[Map[String, JsValue]] = None,
	  /** The link to this long running operation. */
		selfLink: Option[String] = None,
	  /** The kind of item this is. For operations, this is always storage#operation. */
		kind: Option[String] = Some("""storage#operation""")
	)
	
	case class GoogleLongrunningListOperationsResponse(
	  /** The continuation token, used to page through large result sets. Provide this value in a subsequent request to return the next page of results. */
		nextPageToken: Option[String] = None,
	  /** A list of operations that matches the specified filter in the request. */
		operations: Option[List[Schema.GoogleLongrunningOperation]] = None,
	  /** The kind of item this is. For lists of operations, this is always storage#operations. */
		kind: Option[String] = Some("""storage#operations""")
	)
	
	case class GoogleRpcStatus(
	  /** The status code, which should be an enum value of google.rpc.Code. */
		code: Option[Int] = None,
	  /** A list of messages that carry the error details. There is a common set of message types for APIs to use. */
		details: Option[List[Map[String, JsValue]]] = None,
	  /** A developer-facing error message, which should be in English. */
		message: Option[String] = None
	)
	
	case class HmacKey(
	  /** The kind of item this is. For HMAC keys, this is always storage#hmacKey. */
		kind: Option[String] = Some("""storage#hmacKey"""),
	  /** Key metadata. */
		metadata: Option[Schema.HmacKeyMetadata] = None,
	  /** HMAC secret key material. */
		secret: Option[String] = None
	)
	
	case class HmacKeyMetadata(
	  /** The ID of the HMAC Key. */
		accessId: Option[String] = None,
	  /** HTTP 1.1 Entity tag for the HMAC key. */
		etag: Option[String] = None,
	  /** The ID of the HMAC key, including the Project ID and the Access ID. */
		id: Option[String] = None,
	  /** The kind of item this is. For HMAC Key metadata, this is always storage#hmacKeyMetadata. */
		kind: Option[String] = Some("""storage#hmacKeyMetadata"""),
	  /** Project ID owning the service account to which the key authenticates. */
		projectId: Option[String] = None,
	  /** The link to this resource. */
		selfLink: Option[String] = None,
	  /** The email address of the key's associated service account. */
		serviceAccountEmail: Option[String] = None,
	  /** The state of the key. Can be one of ACTIVE, INACTIVE, or DELETED. */
		state: Option[String] = None,
	  /** The creation time of the HMAC key in RFC 3339 format. */
		timeCreated: Option[String] = None,
	  /** The last modification time of the HMAC key metadata in RFC 3339 format. */
		updated: Option[String] = None
	)
	
	case class HmacKeysMetadata(
	  /** The list of items. */
		items: Option[List[Schema.HmacKeyMetadata]] = None,
	  /** The kind of item this is. For lists of hmacKeys, this is always storage#hmacKeysMetadata. */
		kind: Option[String] = Some("""storage#hmacKeysMetadata"""),
	  /** The continuation token, used to page through large result sets. Provide this value in a subsequent request to return the next page of results. */
		nextPageToken: Option[String] = None
	)
	
	case class ManagedFolder(
	  /** The name of the bucket containing this managed folder. */
		bucket: Option[String] = None,
	  /** The ID of the managed folder, including the bucket name and managed folder name. */
		id: Option[String] = None,
	  /** The kind of item this is. For managed folders, this is always storage#managedFolder. */
		kind: Option[String] = Some("""storage#managedFolder"""),
	  /** The version of the metadata for this managed folder. Used for preconditions and for detecting changes in metadata. */
		metageneration: Option[String] = None,
	  /** The name of the managed folder. Required if not specified by URL parameter. */
		name: Option[String] = None,
	  /** The link to this managed folder. */
		selfLink: Option[String] = None,
	  /** The creation time of the managed folder in RFC 3339 format. */
		createTime: Option[String] = None,
	  /** The last update time of the managed folder metadata in RFC 3339 format. */
		updateTime: Option[String] = None
	)
	
	case class ManagedFolders(
	  /** The list of items. */
		items: Option[List[Schema.ManagedFolder]] = None,
	  /** The kind of item this is. For lists of managed folders, this is always storage#managedFolders. */
		kind: Option[String] = Some("""storage#managedFolders"""),
	  /** The continuation token, used to page through large result sets. Provide this value in a subsequent request to return the next page of results. */
		nextPageToken: Option[String] = None
	)
	
	case class Notification(
	  /** An optional list of additional attributes to attach to each Cloud PubSub message published for this notification subscription. */
		custom_attributes: Option[Map[String, String]] = None,
	  /** HTTP 1.1 Entity tag for this subscription notification. */
		etag: Option[String] = None,
	  /** If present, only send notifications about listed event types. If empty, sent notifications for all event types. */
		event_types: Option[List[String]] = None,
	  /** The ID of the notification. */
		id: Option[String] = None,
	  /** The kind of item this is. For notifications, this is always storage#notification. */
		kind: Option[String] = Some("""storage#notification"""),
	  /** If present, only apply this notification configuration to object names that begin with this prefix. */
		object_name_prefix: Option[String] = None,
	  /** The desired content of the Payload. */
		payload_format: Option[String] = Some("""JSON_API_V1"""),
	  /** The canonical URL of this notification. */
		selfLink: Option[String] = None,
	  /** The Cloud PubSub topic to which this subscription publishes. Formatted as: '//pubsub.googleapis.com/projects/{project-identifier}/topics/{my-topic}' */
		topic: Option[String] = None
	)
	
	case class Notifications(
	  /** The list of items. */
		items: Option[List[Schema.Notification]] = None,
	  /** The kind of item this is. For lists of notifications, this is always storage#notifications. */
		kind: Option[String] = Some("""storage#notifications""")
	)
	
	object Object {
		case class CustomerEncryptionItem(
		  /** The encryption algorithm. */
			encryptionAlgorithm: Option[String] = None,
		  /** SHA256 hash value of the encryption key. */
			keySha256: Option[String] = None
		)
		
		case class OwnerItem(
		  /** The entity, in the form user-userId. */
			entity: Option[String] = None,
		  /** The ID for the entity. */
			entityId: Option[String] = None
		)
		
		case class RetentionItem(
		  /** A time in RFC 3339 format until which object retention protects this object. */
			retainUntilTime: Option[String] = None,
		  /** The bucket's object retention mode, can only be Unlocked or Locked. */
			mode: Option[String] = None
		)
	}
	case class Object(
	  /** Access controls on the object. */
		acl: Option[List[Schema.ObjectAccessControl]] = None,
	  /** The name of the bucket containing this object. */
		bucket: Option[String] = None,
	  /** Cache-Control directive for the object data. If omitted, and the object is accessible to all anonymous users, the default will be public, max-age=3600. */
		cacheControl: Option[String] = None,
	  /** Number of underlying components that make up this object. Components are accumulated by compose operations. */
		componentCount: Option[Int] = None,
	  /** Content-Disposition of the object data. */
		contentDisposition: Option[String] = None,
	  /** Content-Encoding of the object data. */
		contentEncoding: Option[String] = None,
	  /** Content-Language of the object data. */
		contentLanguage: Option[String] = None,
	  /** Content-Type of the object data. If an object is stored without a Content-Type, it is served as application/octet-stream. */
		contentType: Option[String] = None,
	  /** CRC32c checksum, as described in RFC 4960, Appendix B; encoded using base64 in big-endian byte order. For more information about using the CRC32c checksum, see [Data Validation and Change Detection](https://cloud.google.com/storage/docs/data-validation). */
		crc32c: Option[String] = None,
	  /** A timestamp in RFC 3339 format specified by the user for an object. */
		customTime: Option[String] = None,
	  /** Metadata of customer-supplied encryption key, if the object is encrypted by such a key. */
		customerEncryption: Option[Schema.Object.CustomerEncryptionItem] = None,
	  /** HTTP 1.1 Entity tag for the object. */
		etag: Option[String] = None,
	  /** Whether an object is under event-based hold. Event-based hold is a way to retain objects until an event occurs, which is signified by the hold's release (i.e. this value is set to false). After being released (set to false), such objects will be subject to bucket-level retention (if any). One sample use case of this flag is for banks to hold loan documents for at least 3 years after loan is paid in full. Here, bucket-level retention is 3 years and the event is the loan being paid in full. In this example, these objects will be held intact for any number of years until the event has occurred (event-based hold on the object is released) and then 3 more years after that. That means retention duration of the objects begins from the moment event-based hold transitioned from true to false. */
		eventBasedHold: Option[Boolean] = None,
	  /** The content generation of this object. Used for object versioning. */
		generation: Option[String] = None,
	  /** The ID of the object, including the bucket name, object name, and generation number. */
		id: Option[String] = None,
	  /** The kind of item this is. For objects, this is always storage#object. */
		kind: Option[String] = Some("""storage#object"""),
	  /** Not currently supported. Specifying the parameter causes the request to fail with status code 400 - Bad Request. */
		kmsKeyName: Option[String] = None,
	  /** MD5 hash of the data; encoded using base64. For more information about using the MD5 hash, see [Data Validation and Change Detection](https://cloud.google.com/storage/docs/data-validation). */
		md5Hash: Option[String] = None,
	  /** Media download link. */
		mediaLink: Option[String] = None,
	  /** User-provided metadata, in key/value pairs. */
		metadata: Option[Map[String, String]] = None,
	  /** Restore token used to differentiate deleted objects with the same name and generation. This field is only returned for deleted objects in hierarchical namespace buckets. */
		restoreToken: Option[String] = None,
	  /** The version of the metadata for this object at this generation. Used for preconditions and for detecting changes in metadata. A metageneration number is only meaningful in the context of a particular generation of a particular object. */
		metageneration: Option[String] = None,
	  /** The name of the object. Required if not specified by URL parameter. */
		name: Option[String] = None,
	  /** The owner of the object. This will always be the uploader of the object. */
		owner: Option[Schema.Object.OwnerItem] = None,
	  /** A server-determined value that specifies the earliest time that the object's retention period expires. This value is in RFC 3339 format. Note 1: This field is not provided for objects with an active event-based hold, since retention expiration is unknown until the hold is removed. Note 2: This value can be provided even when temporary hold is set (so that the user can reason about policy without having to first unset the temporary hold). */
		retentionExpirationTime: Option[String] = None,
	  /** A collection of object level retention parameters. */
		retention: Option[Schema.Object.RetentionItem] = None,
	  /** The link to this object. */
		selfLink: Option[String] = None,
	  /** Content-Length of the data in bytes. */
		size: Option[String] = None,
	  /** Storage class of the object. */
		storageClass: Option[String] = None,
	  /** Whether an object is under temporary hold. While this flag is set to true, the object is protected against deletion and overwrites. A common use case of this flag is regulatory investigations where objects need to be retained while the investigation is ongoing. Note that unlike event-based hold, temporary hold does not impact retention expiration time of an object. */
		temporaryHold: Option[Boolean] = None,
	  /** The creation time of the object in RFC 3339 format. */
		timeCreated: Option[String] = None,
	  /** The time at which the object became noncurrent in RFC 3339 format. Will be returned if and only if this version of the object has been deleted. */
		timeDeleted: Option[String] = None,
	  /** The time at which the object became soft-deleted in RFC 3339 format. */
		softDeleteTime: Option[String] = None,
	  /** This is the time (in the future) when the soft-deleted object will no longer be restorable. It is equal to the soft delete time plus the current soft delete retention duration of the bucket. */
		hardDeleteTime: Option[String] = None,
	  /** The time at which the object's storage class was last changed. When the object is initially created, it will be set to timeCreated. */
		timeStorageClassUpdated: Option[String] = None,
	  /** The modification time of the object metadata in RFC 3339 format. Set initially to object creation time and then updated whenever any metadata of the object changes. This includes changes made by a requester, such as modifying custom metadata, as well as changes made by Cloud Storage on behalf of a requester, such as changing the storage class based on an Object Lifecycle Configuration. */
		updated: Option[String] = None
	)
	
	object ObjectAccessControl {
		case class ProjectTeamItem(
		  /** The project number. */
			projectNumber: Option[String] = None,
		  /** The team. */
			team: Option[String] = None
		)
	}
	case class ObjectAccessControl(
	  /** The name of the bucket. */
		bucket: Option[String] = None,
	  /** The domain associated with the entity, if any. */
		domain: Option[String] = None,
	  /** The email address associated with the entity, if any. */
		email: Option[String] = None,
	  /** The entity holding the permission, in one of the following forms: 
	- user-userId 
	- user-email 
	- group-groupId 
	- group-email 
	- domain-domain 
	- project-team-projectId 
	- allUsers 
	- allAuthenticatedUsers Examples: 
	- The user liz@example.com would be user-liz@example.com. 
	- The group example@googlegroups.com would be group-example@googlegroups.com. 
	- To refer to all members of the Google Apps for Business domain example.com, the entity would be domain-example.com. */
		entity: Option[String] = None,
	  /** The ID for the entity, if any. */
		entityId: Option[String] = None,
	  /** HTTP 1.1 Entity tag for the access-control entry. */
		etag: Option[String] = None,
	  /** The content generation of the object, if applied to an object. */
		generation: Option[String] = None,
	  /** The ID of the access-control entry. */
		id: Option[String] = None,
	  /** The kind of item this is. For object access control entries, this is always storage#objectAccessControl. */
		kind: Option[String] = Some("""storage#objectAccessControl"""),
	  /** The name of the object, if applied to an object. */
		`object`: Option[String] = None,
	  /** The project team associated with the entity, if any. */
		projectTeam: Option[Schema.ObjectAccessControl.ProjectTeamItem] = None,
	  /** The access permission for the entity. */
		role: Option[String] = None,
	  /** The link to this access-control entry. */
		selfLink: Option[String] = None
	)
	
	case class ObjectAccessControls(
	  /** The list of items. */
		items: Option[List[Schema.ObjectAccessControl]] = None,
	  /** The kind of item this is. For lists of object access control entries, this is always storage#objectAccessControls. */
		kind: Option[String] = Some("""storage#objectAccessControls""")
	)
	
	case class Objects(
	  /** The list of items. */
		items: Option[List[Schema.Object]] = None,
	  /** The kind of item this is. For lists of objects, this is always storage#objects. */
		kind: Option[String] = Some("""storage#objects"""),
	  /** The continuation token, used to page through large result sets. Provide this value in a subsequent request to return the next page of results. */
		nextPageToken: Option[String] = None,
	  /** The list of prefixes of objects matching-but-not-listed up to and including the requested delimiter. */
		prefixes: Option[List[String]] = None
	)
	
	object Policy {
		case class BindingsItem(
		  /** The condition that is associated with this binding. NOTE: an unsatisfied condition will not allow user access via current binding. Different bindings, including their conditions, are examined independently. */
			condition: Option[Schema.Expr] = None,
		  /** A collection of identifiers for members who may assume the provided role. Recognized identifiers are as follows:  
		- allUsers — A special identifier that represents anyone on the internet; with or without a Google account.  
		- allAuthenticatedUsers — A special identifier that represents anyone who is authenticated with a Google account or a service account.  
		- user:emailid — An email address that represents a specific account. For example, user:alice@gmail.com or user:joe@example.com.  
		- serviceAccount:emailid — An email address that represents a service account. For example,  serviceAccount:my-other-app@appspot.gserviceaccount.com .  
		- group:emailid — An email address that represents a Google group. For example, group:admins@example.com.  
		- domain:domain — A Google Apps domain name that represents all the users of that domain. For example, domain:google.com or domain:example.com.  
		- projectOwner:projectid — Owners of the given project. For example, projectOwner:my-example-project  
		- projectEditor:projectid — Editors of the given project. For example, projectEditor:my-example-project  
		- projectViewer:projectid — Viewers of the given project. For example, projectViewer:my-example-project */
			members: Option[List[String]] = None,
		  /** The role to which members belong. Two types of roles are supported: new IAM roles, which grant permissions that do not map directly to those provided by ACLs, and legacy IAM roles, which do map directly to ACL permissions. All roles are of the format roles/storage.specificRole.
		The new IAM roles are:  
		- roles/storage.admin — Full control of Google Cloud Storage resources.  
		- roles/storage.objectViewer — Read-Only access to Google Cloud Storage objects.  
		- roles/storage.objectCreator — Access to create objects in Google Cloud Storage.  
		- roles/storage.objectAdmin — Full control of Google Cloud Storage objects.   The legacy IAM roles are:  
		- roles/storage.legacyObjectReader — Read-only access to objects without listing. Equivalent to an ACL entry on an object with the READER role.  
		- roles/storage.legacyObjectOwner — Read/write access to existing objects without listing. Equivalent to an ACL entry on an object with the OWNER role.  
		- roles/storage.legacyBucketReader — Read access to buckets with object listing. Equivalent to an ACL entry on a bucket with the READER role.  
		- roles/storage.legacyBucketWriter — Read access to buckets with object listing/creation/deletion. Equivalent to an ACL entry on a bucket with the WRITER role.  
		- roles/storage.legacyBucketOwner — Read and write access to existing buckets with object listing/creation/deletion. Equivalent to an ACL entry on a bucket with the OWNER role. */
			role: Option[String] = None
		)
	}
	case class Policy(
	  /** An association between a role, which comes with a set of permissions, and members who may assume that role. */
		bindings: Option[List[Schema.Policy.BindingsItem]] = None,
	  /** HTTP 1.1  Entity tag for the policy. */
		etag: Option[String] = None,
	  /** The kind of item this is. For policies, this is always storage#policy. This field is ignored on input. */
		kind: Option[String] = Some("""storage#policy"""),
	  /** The ID of the resource to which this policy belongs. Will be of the form projects/_/buckets/bucket for buckets, projects/_/buckets/bucket/objects/object for objects, and projects/_/buckets/bucket/managedFolders/managedFolder. A specific generation may be specified by appending #generationNumber to the end of the object name, e.g. projects/_/buckets/my-bucket/objects/data.txt#17. The current generation can be denoted with #0. This field is ignored on input. */
		resourceId: Option[String] = None,
	  /** The IAM policy format version. */
		version: Option[Int] = None
	)
	
	object RelocateBucketRequest {
		case class DestinationCustomPlacementConfigItem(
		  /** The list of regional locations in which data is placed. */
			dataLocations: Option[List[String]] = None
		)
	}
	case class RelocateBucketRequest(
	  /** The new location the bucket will be relocated to. */
		destinationLocation: Option[String] = None,
	  /** The bucket's new custom placement configuration if relocating to a Custom Dual Region. */
		destinationCustomPlacementConfig: Option[Schema.RelocateBucketRequest.DestinationCustomPlacementConfigItem] = None,
	  /** If true, validate the operation, but do not actually relocate the bucket. */
		validateOnly: Option[Boolean] = None
	)
	
	case class RewriteResponse(
	  /** true if the copy is finished; otherwise, false if the copy is in progress. This property is always present in the response. */
		done: Option[Boolean] = None,
	  /** The kind of item this is. */
		kind: Option[String] = Some("""storage#rewriteResponse"""),
	  /** The total size of the object being copied in bytes. This property is always present in the response. */
		objectSize: Option[String] = None,
	  /** A resource containing the metadata for the copied-to object. This property is present in the response only when copying completes. */
		resource: Option[Schema.Object] = None,
	  /** A token to use in subsequent requests to continue copying data. This token is present in the response only when there is more data to copy. */
		rewriteToken: Option[String] = None,
	  /** The total bytes written so far, which can be used to provide a waiting user with a progress indicator. This property is always present in the response. */
		totalBytesRewritten: Option[String] = None
	)
	
	case class ServiceAccount(
	  /** The ID of the notification. */
		email_address: Option[String] = None,
	  /** The kind of item this is. For notifications, this is always storage#notification. */
		kind: Option[String] = Some("""storage#serviceAccount""")
	)
	
	case class TestIamPermissionsResponse(
	  /** The kind of item this is. */
		kind: Option[String] = Some("""storage#testIamPermissionsResponse"""),
	  /** The permissions held by the caller. Permissions are always of the format storage.resource.capability, where resource is one of buckets, objects, or managedFolders. The supported permissions are as follows:  
	- storage.buckets.delete — Delete bucket.  
	- storage.buckets.get — Read bucket metadata.  
	- storage.buckets.getIamPolicy — Read bucket IAM policy.  
	- storage.buckets.create — Create bucket.  
	- storage.buckets.list — List buckets.  
	- storage.buckets.setIamPolicy — Update bucket IAM policy.  
	- storage.buckets.update — Update bucket metadata.  
	- storage.objects.delete — Delete object.  
	- storage.objects.get — Read object data and metadata.  
	- storage.objects.getIamPolicy — Read object IAM policy.  
	- storage.objects.create — Create object.  
	- storage.objects.list — List objects.  
	- storage.objects.setIamPolicy — Update object IAM policy.  
	- storage.objects.update — Update object metadata. 
	- storage.managedFolders.delete — Delete managed folder.  
	- storage.managedFolders.get — Read managed folder metadata.  
	- storage.managedFolders.getIamPolicy — Read managed folder IAM policy.  
	- storage.managedFolders.create — Create managed folder.  
	- storage.managedFolders.list — List managed folders.  
	- storage.managedFolders.setIamPolicy — Update managed folder IAM policy. */
		permissions: Option[List[String]] = None
	)
	
	case class BulkRestoreObjectsRequest(
	  /** If false (default), the restore will not overwrite live objects with the same name at the destination. This means some deleted objects may be skipped. If true, live objects will be overwritten resulting in a noncurrent object (if versioning is enabled). If versioning is not enabled, overwriting the object will result in a soft-deleted object. In either case, if a noncurrent object already exists with the same name, a live version can be written without issue. */
		allowOverwrite: Option[Boolean] = None,
	  /** Restores only the objects that were soft-deleted after this time. */
		softDeletedAfterTime: Option[String] = None,
	  /** Restores only the objects that were soft-deleted before this time. */
		softDeletedBeforeTime: Option[String] = None,
	  /** Restores only the objects matching any of the specified glob(s). If this parameter is not specified, all objects will be restored within the specified time range. */
		matchGlobs: Option[List[String]] = None,
	  /** If true, copies the source object's ACL; otherwise, uses the bucket's default object ACL. The default is false. */
		copySourceAcl: Option[Boolean] = None
	)
}
