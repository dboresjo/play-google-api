package com.boresjo.play.api.google.tagmanager

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	case class ListAccountsResponse(
	  /** List of GTM Accounts that a user has access to. */
		account: Option[List[Schema.Account]] = None,
	  /** Continuation token for fetching the next page of results. */
		nextPageToken: Option[String] = None
	)
	
	case class Account(
	  /** GTM Account's API relative path. */
		path: Option[String] = None,
	  /** The Account ID uniquely identifies the GTM Account. */
		accountId: Option[String] = None,
	  /** Account display name. @mutable tagmanager.accounts.create @mutable tagmanager.accounts.update */
		name: Option[String] = None,
	  /** Whether the account shares data anonymously with Google and others. This flag enables benchmarking by sharing your data in an anonymous form. Google will remove all identifiable information about your website, combine the data with hundreds of other anonymous sites and report aggregate trends in the benchmarking service. @mutable tagmanager.accounts.create @mutable tagmanager.accounts.update */
		shareData: Option[Boolean] = None,
	  /** The fingerprint of the GTM Account as computed at storage time. This value is recomputed whenever the account is modified. */
		fingerprint: Option[String] = None,
	  /** Auto generated link to the tag manager UI */
		tagManagerUrl: Option[String] = None,
	  /** Read-only Account feature set */
		features: Option[Schema.AccountFeatures] = None
	)
	
	case class AccountFeatures(
	  /** Whether this Account supports user permissions managed by GTM. */
		supportUserPermissions: Option[Boolean] = None,
	  /** Whether this Account supports multiple Containers. */
		supportMultipleContainers: Option[Boolean] = None
	)
	
	case class UserPermission(
	  /** GTM UserPermission's API relative path. */
		path: Option[String] = None,
	  /** The Account ID uniquely identifies the GTM Account. */
		accountId: Option[String] = None,
	  /** User's email address. @mutable tagmanager.accounts.permissions.create */
		emailAddress: Option[String] = None,
	  /** GTM Account access permissions. @mutable tagmanager.accounts.permissions.create @mutable tagmanager.accounts.permissions.update */
		accountAccess: Option[Schema.AccountAccess] = None,
	  /** GTM Container access permissions. @mutable tagmanager.accounts.permissions.create @mutable tagmanager.accounts.permissions.update */
		containerAccess: Option[List[Schema.ContainerAccess]] = None
	)
	
	object AccountAccess {
		enum PermissionEnum extends Enum[PermissionEnum] { case accountPermissionUnspecified, noAccess, user, admin }
	}
	case class AccountAccess(
	  /** Whether the user has no access, user access, or admin access to an account. @mutable tagmanager.accounts.permissions.create @mutable tagmanager.accounts.permissions.update */
		permission: Option[Schema.AccountAccess.PermissionEnum] = None
	)
	
	object ContainerAccess {
		enum PermissionEnum extends Enum[PermissionEnum] { case containerPermissionUnspecified, noAccess, read, edit, approve, publish }
	}
	case class ContainerAccess(
	  /** GTM Container ID. @mutable tagmanager.accounts.permissions.create @mutable tagmanager.accounts.permissions.update */
		containerId: Option[String] = None,
	  /** List of Container permissions. @mutable tagmanager.accounts.permissions.create @mutable tagmanager.accounts.permissions.update */
		permission: Option[Schema.ContainerAccess.PermissionEnum] = None
	)
	
	case class ListUserPermissionsResponse(
	  /** All GTM UserPermissions of a GTM Account. */
		userPermission: Option[List[Schema.UserPermission]] = None,
	  /** Continuation token for fetching the next page of results. */
		nextPageToken: Option[String] = None
	)
	
	object Container {
		enum UsageContextEnum extends Enum[UsageContextEnum] { case usageContextUnspecified, web, android, ios, androidSdk5, iosSdk5, amp, server }
	}
	case class Container(
	  /** GTM Container's API relative path. */
		path: Option[String] = None,
	  /** GTM Account ID. */
		accountId: Option[String] = None,
	  /** The Container ID uniquely identifies the GTM Container. */
		containerId: Option[String] = None,
	  /** Container display name. @mutable tagmanager.accounts.containers.create @mutable tagmanager.accounts.containers.update */
		name: Option[String] = None,
	  /** List of domain names associated with the Container. @mutable tagmanager.accounts.containers.create @mutable tagmanager.accounts.containers.update */
		domainName: Option[List[String]] = None,
	  /** Container Public ID. */
		publicId: Option[String] = None,
	  /** All Tag IDs that refer to this Container. */
		tagIds: Option[List[String]] = None,
	  /** Read-only Container feature set. */
		features: Option[Schema.ContainerFeatures] = None,
	  /** Container Notes. @mutable tagmanager.accounts.containers.create @mutable tagmanager.accounts.containers.update */
		notes: Option[String] = None,
	  /** List of Usage Contexts for the Container. Valid values include: web, android, or ios. @mutable tagmanager.accounts.containers.create @mutable tagmanager.accounts.containers.update */
		usageContext: Option[List[Schema.Container.UsageContextEnum]] = None,
	  /** The fingerprint of the GTM Container as computed at storage time. This value is recomputed whenever the account is modified. */
		fingerprint: Option[String] = None,
	  /** Auto generated link to the tag manager UI */
		tagManagerUrl: Option[String] = None,
	  /** List of server-side container URLs for the Container. If multiple URLs are provided, all URL paths must match. @mutable tagmanager.accounts.containers.create @mutable tagmanager.accounts.containers.update */
		taggingServerUrls: Option[List[String]] = None
	)
	
	case class ContainerFeatures(
	  /** Whether this Container supports user permissions managed by GTM. */
		supportUserPermissions: Option[Boolean] = None,
	  /** Whether this Container supports environments. */
		supportEnvironments: Option[Boolean] = None,
	  /** Whether this Container supports workspaces. */
		supportWorkspaces: Option[Boolean] = None,
	  /** Whether this Container supports Google tag config. */
		supportGtagConfigs: Option[Boolean] = None,
	  /** Whether this Container supports built-in variables */
		supportBuiltInVariables: Option[Boolean] = None,
	  /** Whether this Container supports clients. */
		supportClients: Option[Boolean] = None,
	  /** Whether this Container supports folders. */
		supportFolders: Option[Boolean] = None,
	  /** Whether this Container supports tags. */
		supportTags: Option[Boolean] = None,
	  /** Whether this Container supports templates. */
		supportTemplates: Option[Boolean] = None,
	  /** Whether this Container supports triggers. */
		supportTriggers: Option[Boolean] = None,
	  /** Whether this Container supports variables. */
		supportVariables: Option[Boolean] = None,
	  /** Whether this Container supports Container versions. */
		supportVersions: Option[Boolean] = None,
	  /** Whether this Container supports zones. */
		supportZones: Option[Boolean] = None,
	  /** Whether this Container supports transformations. */
		supportTransformations: Option[Boolean] = None
	)
	
	case class ListContainersResponse(
	  /** All Containers of a GTM Account. */
		container: Option[List[Schema.Container]] = None,
	  /** Continuation token for fetching the next page of results. */
		nextPageToken: Option[String] = None
	)
	
	case class GetContainerSnippetResponse(
	  /** Tagging snippet for a Container. */
		snippet: Option[String] = None
	)
	
	case class Destination(
	  /** Destination's API relative path. */
		path: Option[String] = None,
	  /** GTM Account ID. */
		accountId: Option[String] = None,
	  /** GTM Container ID. */
		containerId: Option[String] = None,
	  /** The Destination link ID uniquely identifies the Destination. */
		destinationLinkId: Option[String] = None,
	  /** Destination ID. */
		destinationId: Option[String] = None,
	  /** Destination display name. */
		name: Option[String] = None,
	  /** The fingerprint of the Google Tag Destination as computed at storage time. This value is recomputed whenever the destination is modified. */
		fingerprint: Option[String] = None,
	  /** Auto generated link to the tag manager UI. */
		tagManagerUrl: Option[String] = None
	)
	
	case class ListDestinationsResponse(
	  /** All Destinations linked to a GTM Container. */
		destination: Option[List[Schema.Destination]] = None,
	  /** Continuation token for fetching the next page of results. */
		nextPageToken: Option[String] = None
	)
	
	case class Workspace(
	  /** GTM Workspace's API relative path. */
		path: Option[String] = None,
	  /** GTM Account ID. */
		accountId: Option[String] = None,
	  /** GTM Container ID. */
		containerId: Option[String] = None,
	  /** The Workspace ID uniquely identifies the GTM Workspace. */
		workspaceId: Option[String] = None,
	  /** Workspace display name. @mutable tagmanager.accounts.containers.workspaces.create @mutable tagmanager.accounts.containers.workspaces.update */
		name: Option[String] = None,
	  /** Workspace description. @mutable tagmanager.accounts.containers.workspaces.create @mutable tagmanager.accounts.containers.workspaces.update */
		description: Option[String] = None,
	  /** The fingerprint of the GTM Workspace as computed at storage time. This value is recomputed whenever the workspace is modified. */
		fingerprint: Option[String] = None,
	  /** Auto generated link to the tag manager UI */
		tagManagerUrl: Option[String] = None
	)
	
	case class ListWorkspacesResponse(
	  /** All Workspaces of a GTM Container. */
		workspace: Option[List[Schema.Workspace]] = None,
	  /** Continuation token for fetching the next page of results. */
		nextPageToken: Option[String] = None
	)
	
	case class SyncWorkspaceResponse(
	  /** Indicates whether synchronization caused a merge conflict or sync error. */
		syncStatus: Option[Schema.SyncStatus] = None,
	  /** The merge conflict after sync. If this field is not empty, the sync is still treated as successful. But a version cannot be created until all conflicts are resolved. */
		mergeConflict: Option[List[Schema.MergeConflict]] = None
	)
	
	case class SyncStatus(
	  /** Synchornization operation detected a merge conflict. */
		mergeConflict: Option[Boolean] = None,
	  /** An error occurred during the synchronization operation. */
		syncError: Option[Boolean] = None
	)
	
	case class MergeConflict(
	  /** The workspace entity that has conflicting changes compared to the base version. If an entity is deleted in a workspace, it will still appear with a deleted change status. */
		entityInWorkspace: Option[Schema.Entity] = None,
	  /** The base version entity (since the latest sync operation) that has conflicting changes compared to the workspace. If this field is missing, it means the workspace entity is deleted from the base version. */
		entityInBaseVersion: Option[Schema.Entity] = None
	)
	
	object Entity {
		enum ChangeStatusEnum extends Enum[ChangeStatusEnum] { case changeStatusUnspecified, none, added, deleted, updated }
	}
	case class Entity(
	  /** The tag being represented by the entity. */
		tag: Option[Schema.Tag] = None,
	  /** The trigger being represented by the entity. */
		trigger: Option[Schema.Trigger] = None,
	  /** The variable being represented by the entity. */
		variable: Option[Schema.Variable] = None,
	  /** The folder being represented by the entity. */
		folder: Option[Schema.Folder] = None,
	  /** The client being represented by the entity. */
		client: Option[Schema.Client] = None,
	  /** The transformation being represented by the entity. */
		transformation: Option[Schema.Transformation] = None,
	  /** The zone being represented by the entity. */
		zone: Option[Schema.Zone] = None,
	  /** The custom template being represented by the entity. */
		customTemplate: Option[Schema.CustomTemplate] = None,
	  /** The built in variable being represented by the entity. */
		builtInVariable: Option[Schema.BuiltInVariable] = None,
	  /** The gtag config being represented by the entity. */
		gtagConfig: Option[Schema.GtagConfig] = None,
	  /** Represents how the entity has been changed in the workspace. */
		changeStatus: Option[Schema.Entity.ChangeStatusEnum] = None
	)
	
	object Tag {
		enum TagFiringOptionEnum extends Enum[TagFiringOptionEnum] { case tagFiringOptionUnspecified, unlimited, oncePerEvent, oncePerLoad }
	}
	case class Tag(
	  /** GTM Tag's API relative path. */
		path: Option[String] = None,
	  /** GTM Account ID. */
		accountId: Option[String] = None,
	  /** GTM Container ID. */
		containerId: Option[String] = None,
	  /** GTM Workspace ID. */
		workspaceId: Option[String] = None,
	  /** The Tag ID uniquely identifies the GTM Tag. */
		tagId: Option[String] = None,
	  /** Tag display name. @mutable tagmanager.accounts.containers.workspaces.tags.create @mutable tagmanager.accounts.containers.workspaces.tags.update */
		name: Option[String] = None,
	  /** GTM Tag Type. @mutable tagmanager.accounts.containers.workspaces.tags.create @mutable tagmanager.accounts.containers.workspaces.tags.update */
		`type`: Option[String] = None,
	  /** Firing rule IDs. A tag will fire when any of the listed rules are true and all of its blockingRuleIds (if any specified) are false. @mutable tagmanager.accounts.containers.workspaces.tags.create @mutable tagmanager.accounts.containers.workspaces.tags.update */
		firingRuleId: Option[List[String]] = None,
	  /** Blocking rule IDs. If any of the listed rules evaluate to true, the tag will not fire. @mutable tagmanager.accounts.containers.workspaces.tags.create @mutable tagmanager.accounts.containers.workspaces.tags.update */
		blockingRuleId: Option[List[String]] = None,
	  /** If set to true, this tag will only fire in the live environment (e.g. not in preview or debug mode). @mutable tagmanager.accounts.containers.workspaces.tags.create @mutable tagmanager.accounts.containers.workspaces.tags.update */
		liveOnly: Option[Boolean] = None,
	  /** User defined numeric priority of the tag. Tags are fired asynchronously in order of priority. Tags with higher numeric value fire first. A tag's priority can be a positive or negative value. The default value is 0. @mutable tagmanager.accounts.containers.workspaces.tags.create @mutable tagmanager.accounts.containers.workspaces.tags.update */
		priority: Option[Schema.Parameter] = None,
	  /** User notes on how to apply this tag in the container. @mutable tagmanager.accounts.containers.workspaces.tags.create @mutable tagmanager.accounts.containers.workspaces.tags.update */
		notes: Option[String] = None,
	  /** The start timestamp in milliseconds to schedule a tag. @mutable tagmanager.accounts.containers.workspaces.tags.create @mutable tagmanager.accounts.containers.workspaces.tags.update */
		scheduleStartMs: Option[String] = None,
	  /** The end timestamp in milliseconds to schedule a tag. @mutable tagmanager.accounts.containers.workspaces.tags.create @mutable tagmanager.accounts.containers.workspaces.tags.update */
		scheduleEndMs: Option[String] = None,
	  /** The tag's parameters. @mutable tagmanager.accounts.containers.workspaces.tags.create @mutable tagmanager.accounts.containers.workspaces.tags.update */
		parameter: Option[List[Schema.Parameter]] = None,
	  /** The fingerprint of the GTM Tag as computed at storage time. This value is recomputed whenever the tag is modified. */
		fingerprint: Option[String] = None,
	  /** Firing trigger IDs. A tag will fire when any of the listed triggers are true and all of its blockingTriggerIds (if any specified) are false. @mutable tagmanager.accounts.containers.workspaces.tags.create @mutable tagmanager.accounts.containers.workspaces.tags.update */
		firingTriggerId: Option[List[String]] = None,
	  /** Blocking trigger IDs. If any of the listed triggers evaluate to true, the tag will not fire. @mutable tagmanager.accounts.containers.workspaces.tags.create @mutable tagmanager.accounts.containers.workspaces.tags.update */
		blockingTriggerId: Option[List[String]] = None,
	  /** The list of setup tags. Currently we only allow one. */
		setupTag: Option[List[Schema.SetupTag]] = None,
	  /** The list of teardown tags. Currently we only allow one. */
		teardownTag: Option[List[Schema.TeardownTag]] = None,
	  /** Parent folder id. */
		parentFolderId: Option[String] = None,
	  /** Option to fire this tag. */
		tagFiringOption: Option[Schema.Tag.TagFiringOptionEnum] = None,
	  /** Auto generated link to the tag manager UI */
		tagManagerUrl: Option[String] = None,
	  /** Indicates whether the tag is paused, which prevents the tag from firing. @mutable tagmanager.accounts.containers.workspaces.tags.create @mutable tagmanager.accounts.containers.workspaces.tags.update */
		paused: Option[Boolean] = None,
	  /** A map of key-value pairs of tag metadata to be included in the event data for tag monitoring. Notes: - This parameter must be type MAP. - Each parameter in the map are type TEMPLATE, however cannot contain variable references. @mutable tagmanager.accounts.containers.workspaces.tags.create @mutable tagmanager.accounts.containers.workspaces.tags.update */
		monitoringMetadata: Option[Schema.Parameter] = None,
	  /** If non-empty, then the tag display name will be included in the monitoring metadata map using the key specified. @mutable tagmanager.accounts.containers.workspaces.tags.create @mutable tagmanager.accounts.containers.workspaces.tags.update */
		monitoringMetadataTagNameKey: Option[String] = None,
	  /** Consent settings of a tag. @mutable tagmanager.accounts.containers.workspaces.tags.create @mutable tagmanager.accounts.containers.workspaces.tags.update */
		consentSettings: Option[Schema.TagConsentSetting] = None
	)
	
	object Parameter {
		enum TypeEnum extends Enum[TypeEnum] { case typeUnspecified, template, integer, `boolean`, list, map, triggerReference, tagReference }
	}
	case class Parameter(
	  /** The parameter type. Valid values are: - boolean: The value represents a boolean, represented as 'true' or 'false' - integer: The value represents a 64-bit signed integer value, in base 10 - list: A list of parameters should be specified - map: A map of parameters should be specified - template: The value represents any text; this can include variable references (even variable references that might return non-string types) - trigger_reference: The value represents a trigger, represented as the trigger id - tag_reference: The value represents a tag, represented as the tag name @mutable tagmanager.accounts.containers.workspaces.variables.create @mutable tagmanager.accounts.containers.workspaces.variables.update @mutable tagmanager.accounts.containers.workspaces.triggers.create @mutable tagmanager.accounts.containers.workspaces.triggers.update @mutable tagmanager.accounts.containers.workspaces.tags.create @mutable tagmanager.accounts.containers.workspaces.tags.update */
		`type`: Option[Schema.Parameter.TypeEnum] = None,
	  /** The named key that uniquely identifies a parameter. Required for top-level parameters, as well as map values. Ignored for list values. @mutable tagmanager.accounts.containers.workspaces.variables.create @mutable tagmanager.accounts.containers.workspaces.variables.update @mutable tagmanager.accounts.containers.workspaces.triggers.create @mutable tagmanager.accounts.containers.workspaces.triggers.update @mutable tagmanager.accounts.containers.workspaces.tags.create @mutable tagmanager.accounts.containers.workspaces.tags.update */
		key: Option[String] = None,
	  /** A parameter's value (may contain variable references such as "{{myVariable}}") as appropriate to the specified type. @mutable tagmanager.accounts.containers.workspaces.variables.create @mutable tagmanager.accounts.containers.workspaces.variables.update @mutable tagmanager.accounts.containers.workspaces.triggers.create @mutable tagmanager.accounts.containers.workspaces.triggers.update @mutable tagmanager.accounts.containers.workspaces.tags.create @mutable tagmanager.accounts.containers.workspaces.tags.update */
		value: Option[String] = None,
	  /** This list parameter's parameters (keys will be ignored). @mutable tagmanager.accounts.containers.workspaces.variables.create @mutable tagmanager.accounts.containers.workspaces.variables.update @mutable tagmanager.accounts.containers.workspaces.triggers.create @mutable tagmanager.accounts.containers.workspaces.triggers.update @mutable tagmanager.accounts.containers.workspaces.tags.create @mutable tagmanager.accounts.containers.workspaces.tags.update */
		list: Option[List[Schema.Parameter]] = None,
	  /** This map parameter's parameters (must have keys; keys must be unique). @mutable tagmanager.accounts.containers.workspaces.variables.create @mutable tagmanager.accounts.containers.workspaces.variables.update @mutable tagmanager.accounts.containers.workspaces.triggers.create @mutable tagmanager.accounts.containers.workspaces.triggers.update @mutable tagmanager.accounts.containers.workspaces.tags.create @mutable tagmanager.accounts.containers.workspaces.tags.update */
		map: Option[List[Schema.Parameter]] = None,
	  /** Whether or not a reference type parameter is strongly or weakly referenced. Only used by Transformations. @mutable tagmanager.accounts.containers.workspaces.transformations.create @mutable tagmanager.accounts.containers.workspaces.transformations.update */
		isWeakReference: Option[Boolean] = None
	)
	
	case class SetupTag(
	  /** The name of the setup tag. */
		tagName: Option[String] = None,
	  /** If true, fire the main tag if and only if the setup tag fires successfully. If false, fire the main tag regardless of setup tag firing status. */
		stopOnSetupFailure: Option[Boolean] = None
	)
	
	case class TeardownTag(
	  /** The name of the teardown tag. */
		tagName: Option[String] = None,
	  /** If true, fire the teardown tag if and only if the main tag fires successfully. If false, fire the teardown tag regardless of main tag firing status. */
		stopTeardownOnFailure: Option[Boolean] = None
	)
	
	object TagConsentSetting {
		enum ConsentStatusEnum extends Enum[ConsentStatusEnum] { case notSet, notNeeded, needed }
	}
	case class TagConsentSetting(
	  /** The tag's consent status. If set to NEEDED, the runtime will check that the consent types specified by the consent_type field have been granted. */
		consentStatus: Option[Schema.TagConsentSetting.ConsentStatusEnum] = None,
	  /** The type of consents to check for during tag firing if in the consent NEEDED state. This parameter must be of type LIST where each list item is of type STRING. */
		consentType: Option[Schema.Parameter] = None
	)
	
	object Trigger {
		enum TypeEnum extends Enum[TypeEnum] { case eventTypeUnspecified, pageview, domReady, windowLoaded, customEvent, triggerGroup, init, consentInit, serverPageview, always, firebaseAppException, firebaseAppUpdate, firebaseCampaign, firebaseFirstOpen, firebaseInAppPurchase, firebaseNotificationDismiss, firebaseNotificationForeground, firebaseNotificationOpen, firebaseNotificationReceive, firebaseOsUpdate, firebaseSessionStart, firebaseUserEngagement, formSubmission, click, linkClick, jsError, historyChange, timer, ampClick, ampTimer, ampScroll, ampVisibility, youTubeVideo, scrollDepth, elementVisibility }
	}
	case class Trigger(
	  /** GTM Trigger's API relative path. */
		path: Option[String] = None,
	  /** GTM Account ID. */
		accountId: Option[String] = None,
	  /** GTM Container ID. */
		containerId: Option[String] = None,
	  /** GTM Workspace ID. */
		workspaceId: Option[String] = None,
	  /** The Trigger ID uniquely identifies the GTM Trigger. */
		triggerId: Option[String] = None,
	  /** Trigger display name. @mutable tagmanager.accounts.containers.workspaces.triggers.create @mutable tagmanager.accounts.containers.workspaces.triggers.update */
		name: Option[String] = None,
	  /** Defines the data layer event that causes this trigger. @mutable tagmanager.accounts.containers.workspaces.triggers.create @mutable tagmanager.accounts.containers.workspaces.triggers.update */
		`type`: Option[Schema.Trigger.TypeEnum] = None,
	  /** Used in the case of custom event, which is fired iff all Conditions are true. @mutable tagmanager.accounts.containers.workspaces.triggers.create @mutable tagmanager.accounts.containers.workspaces.triggers.update */
		customEventFilter: Option[List[Schema.Condition]] = None,
	  /** The trigger will only fire iff all Conditions are true. @mutable tagmanager.accounts.containers.workspaces.triggers.create @mutable tagmanager.accounts.containers.workspaces.triggers.update */
		filter: Option[List[Schema.Condition]] = None,
	  /** Used in the case of auto event tracking. @mutable tagmanager.accounts.containers.workspaces.triggers.create @mutable tagmanager.accounts.containers.workspaces.triggers.update */
		autoEventFilter: Option[List[Schema.Condition]] = None,
	  /** Whether or not we should delay the form submissions or link opening until all of the tags have fired (by preventing the default action and later simulating the default action). Only valid for Form Submission and Link Click triggers. @mutable tagmanager.accounts.containers.workspaces.triggers.create @mutable tagmanager.accounts.containers.workspaces.triggers.update */
		waitForTags: Option[Schema.Parameter] = None,
	  /** Whether or not we should only fire tags if the form submit or link click event is not cancelled by some other event handler (e.g. because of validation). Only valid for Form Submission and Link Click triggers. @mutable tagmanager.accounts.containers.workspaces.triggers.create @mutable tagmanager.accounts.containers.workspaces.triggers.update */
		checkValidation: Option[Schema.Parameter] = None,
	  /** How long to wait (in milliseconds) for tags to fire when 'waits_for_tags' above evaluates to true. Only valid for Form Submission and Link Click triggers. @mutable tagmanager.accounts.containers.workspaces.triggers.create @mutable tagmanager.accounts.containers.workspaces.triggers.update */
		waitForTagsTimeout: Option[Schema.Parameter] = None,
	  /** Globally unique id of the trigger that auto-generates this (a Form Submit, Link Click or Timer listener) if any. Used to make incompatible auto-events work together with trigger filtering based on trigger ids. This value is populated during output generation since the tags implied by triggers don't exist until then. Only valid for Form Submit, Link Click and Timer triggers. @mutable tagmanager.accounts.containers.workspaces.triggers.create @mutable tagmanager.accounts.containers.workspaces.triggers.update */
		uniqueTriggerId: Option[Schema.Parameter] = None,
	  /** Name of the GTM event that is fired. Only valid for Timer triggers. @mutable tagmanager.accounts.containers.workspaces.triggers.create @mutable tagmanager.accounts.containers.workspaces.triggers.update */
		eventName: Option[Schema.Parameter] = None,
	  /** Time between triggering recurring Timer Events (in milliseconds). Only valid for Timer triggers. @mutable tagmanager.accounts.containers.workspaces.triggers.create @mutable tagmanager.accounts.containers.workspaces.triggers.update */
		interval: Option[Schema.Parameter] = None,
	  /** Limit of the number of GTM events this Timer Trigger will fire. If no limit is set, we will continue to fire GTM events until the user leaves the page. Only valid for Timer triggers. @mutable tagmanager.accounts.containers.workspaces.triggers.create @mutable tagmanager.accounts.containers.workspaces.triggers.update */
		limit: Option[Schema.Parameter] = None,
	  /** The fingerprint of the GTM Trigger as computed at storage time. This value is recomputed whenever the trigger is modified. */
		fingerprint: Option[String] = None,
	  /** Parent folder id. */
		parentFolderId: Option[String] = None,
	  /** A click trigger CSS selector (i.e. "a", "button" etc.). Only valid for AMP Click trigger. @mutable tagmanager.accounts.containers.workspaces.triggers.create @mutable tagmanager.accounts.containers.workspaces.triggers.update */
		selector: Option[Schema.Parameter] = None,
	  /** Time between Timer Events to fire (in seconds). Only valid for AMP Timer trigger. @mutable tagmanager.accounts.containers.workspaces.triggers.create @mutable tagmanager.accounts.containers.workspaces.triggers.update */
		intervalSeconds: Option[Schema.Parameter] = None,
	  /** Max time to fire Timer Events (in seconds). Only valid for AMP Timer trigger. @mutable tagmanager.accounts.containers.workspaces.triggers.create @mutable tagmanager.accounts.containers.workspaces.triggers.update */
		maxTimerLengthSeconds: Option[Schema.Parameter] = None,
	  /** List of integer percentage values for scroll triggers. The trigger will fire when each percentage is reached when the view is scrolled vertically. Only valid for AMP scroll triggers. @mutable tagmanager.accounts.containers.workspaces.triggers.create @mutable tagmanager.accounts.containers.workspaces.triggers.update */
		verticalScrollPercentageList: Option[Schema.Parameter] = None,
	  /** List of integer percentage values for scroll triggers. The trigger will fire when each percentage is reached when the view is scrolled horizontally. Only valid for AMP scroll triggers. @mutable tagmanager.accounts.containers.workspaces.triggers.create @mutable tagmanager.accounts.containers.workspaces.triggers.update */
		horizontalScrollPercentageList: Option[Schema.Parameter] = None,
	  /** A visibility trigger CSS selector (i.e. "#id"). Only valid for AMP Visibility trigger. @mutable tagmanager.accounts.containers.workspaces.triggers.create @mutable tagmanager.accounts.containers.workspaces.triggers.update */
		visibilitySelector: Option[Schema.Parameter] = None,
	  /** A visibility trigger minimum percent visibility. Only valid for AMP Visibility trigger. @mutable tagmanager.accounts.containers.workspaces.triggers.create @mutable tagmanager.accounts.containers.workspaces.triggers.update */
		visiblePercentageMin: Option[Schema.Parameter] = None,
	  /** A visibility trigger maximum percent visibility. Only valid for AMP Visibility trigger. @mutable tagmanager.accounts.containers.workspaces.triggers.create @mutable tagmanager.accounts.containers.workspaces.triggers.update */
		visiblePercentageMax: Option[Schema.Parameter] = None,
	  /** A visibility trigger minimum continuous visible time (in milliseconds). Only valid for AMP Visibility trigger. @mutable tagmanager.accounts.containers.workspaces.triggers.create @mutable tagmanager.accounts.containers.workspaces.triggers.update */
		continuousTimeMinMilliseconds: Option[Schema.Parameter] = None,
	  /** A visibility trigger minimum total visible time (in milliseconds). Only valid for AMP Visibility trigger. @mutable tagmanager.accounts.containers.workspaces.triggers.create @mutable tagmanager.accounts.containers.workspaces.triggers.update */
		totalTimeMinMilliseconds: Option[Schema.Parameter] = None,
	  /** Auto generated link to the tag manager UI */
		tagManagerUrl: Option[String] = None,
	  /** User notes on how to apply this trigger in the container. @mutable tagmanager.accounts.containers.workspaces.triggers.create @mutable tagmanager.accounts.containers.workspaces.triggers.update */
		notes: Option[String] = None,
	  /** Additional parameters. @mutable tagmanager.accounts.containers.workspaces.triggers.create @mutable tagmanager.accounts.containers.workspaces.triggers.update */
		parameter: Option[List[Schema.Parameter]] = None
	)
	
	object Condition {
		enum TypeEnum extends Enum[TypeEnum] { case conditionTypeUnspecified, equals, contains, startsWith, endsWith, matchRegex, greater, greaterOrEquals, less, lessOrEquals, cssSelector, urlMatches }
	}
	case class Condition(
	  /** The type of operator for this condition. @mutable tagmanager.accounts.containers.workspaces.triggers.create @mutable tagmanager.accounts.containers.workspaces.triggers.update */
		`type`: Option[Schema.Condition.TypeEnum] = None,
	  /** A list of named parameters (key/value), depending on the condition's type. Notes: - For binary operators, include parameters named arg0 and arg1 for specifying the left and right operands, respectively. - At this time, the left operand (arg0) must be a reference to a variable. - For case-insensitive Regex matching, include a boolean parameter named ignore_case that is set to true. If not specified or set to any other value, the matching will be case sensitive. - To negate an operator, include a boolean parameter named negate boolean parameter that is set to true. @mutable tagmanager.accounts.containers.workspaces.triggers.create @mutable tagmanager.accounts.containers.workspaces.triggers.update */
		parameter: Option[List[Schema.Parameter]] = None
	)
	
	case class Variable(
	  /** GTM Variable's API relative path. */
		path: Option[String] = None,
	  /** GTM Account ID. */
		accountId: Option[String] = None,
	  /** GTM Container ID. */
		containerId: Option[String] = None,
	  /** GTM Workspace ID. */
		workspaceId: Option[String] = None,
	  /** The Variable ID uniquely identifies the GTM Variable. */
		variableId: Option[String] = None,
	  /** Variable display name. @mutable tagmanager.accounts.containers.workspaces.variables.create @mutable tagmanager.accounts.containers.workspaces.variables.update */
		name: Option[String] = None,
	  /** GTM Variable Type. @mutable tagmanager.accounts.containers.workspaces.variables.create @mutable tagmanager.accounts.containers.workspaces.variables.update */
		`type`: Option[String] = None,
	  /** User notes on how to apply this variable in the container. @mutable tagmanager.accounts.containers.workspaces.variables.create @mutable tagmanager.accounts.containers.workspaces.variables.update */
		notes: Option[String] = None,
	  /** The start timestamp in milliseconds to schedule a variable. @mutable tagmanager.accounts.containers.workspaces.variables.create @mutable tagmanager.accounts.containers.workspaces.variables.update */
		scheduleStartMs: Option[String] = None,
	  /** The end timestamp in milliseconds to schedule a variable. @mutable tagmanager.accounts.containers.workspaces.variables.create @mutable tagmanager.accounts.containers.workspaces.variables.update */
		scheduleEndMs: Option[String] = None,
	  /** The variable's parameters. @mutable tagmanager.accounts.containers.workspaces.variables.create @mutable tagmanager.accounts.containers.workspaces.variables.update */
		parameter: Option[List[Schema.Parameter]] = None,
	  /** For mobile containers only: A list of trigger IDs for enabling conditional variables; the variable is enabled if one of the enabling triggers is true while all the disabling triggers are false. Treated as an unordered set. @mutable tagmanager.accounts.containers.workspaces.variables.create @mutable tagmanager.accounts.containers.workspaces.variables.update */
		enablingTriggerId: Option[List[String]] = None,
	  /** For mobile containers only: A list of trigger IDs for disabling conditional variables; the variable is enabled if one of the enabling trigger is true while all the disabling trigger are false. Treated as an unordered set. @mutable tagmanager.accounts.containers.workspaces.variables.create @mutable tagmanager.accounts.containers.workspaces.variables.update */
		disablingTriggerId: Option[List[String]] = None,
	  /** The fingerprint of the GTM Variable as computed at storage time. This value is recomputed whenever the variable is modified. */
		fingerprint: Option[String] = None,
	  /** Parent folder id. */
		parentFolderId: Option[String] = None,
	  /** Auto generated link to the tag manager UI */
		tagManagerUrl: Option[String] = None,
	  /** Option to convert a variable value to other value. */
		formatValue: Option[Schema.VariableFormatValue] = None
	)
	
	object VariableFormatValue {
		enum CaseConversionTypeEnum extends Enum[CaseConversionTypeEnum] { case none, lowercase, uppercase }
	}
	case class VariableFormatValue(
	  /** The option to convert a string-type variable value to either lowercase or uppercase. */
		caseConversionType: Option[Schema.VariableFormatValue.CaseConversionTypeEnum] = None,
	  /** The value to convert if a variable value is null. */
		convertNullToValue: Option[Schema.Parameter] = None,
	  /** The value to convert if a variable value is undefined. */
		convertUndefinedToValue: Option[Schema.Parameter] = None,
	  /** The value to convert if a variable value is true. */
		convertTrueToValue: Option[Schema.Parameter] = None,
	  /** The value to convert if a variable value is false. */
		convertFalseToValue: Option[Schema.Parameter] = None
	)
	
	case class Folder(
	  /** GTM Folder's API relative path. */
		path: Option[String] = None,
	  /** GTM Account ID. */
		accountId: Option[String] = None,
	  /** GTM Container ID. */
		containerId: Option[String] = None,
	  /** GTM Workspace ID. */
		workspaceId: Option[String] = None,
	  /** The Folder ID uniquely identifies the GTM Folder. */
		folderId: Option[String] = None,
	  /** Folder display name. @mutable tagmanager.accounts.containers.workspaces.folders.create @mutable tagmanager.accounts.containers.workspaces.folders.update */
		name: Option[String] = None,
	  /** The fingerprint of the GTM Folder as computed at storage time. This value is recomputed whenever the folder is modified. */
		fingerprint: Option[String] = None,
	  /** Auto generated link to the tag manager UI */
		tagManagerUrl: Option[String] = None,
	  /** User notes on how to apply this folder in the container. @mutable tagmanager.accounts.containers.workspaces.folders.create @mutable tagmanager.accounts.containers.workspaces.folders.update */
		notes: Option[String] = None
	)
	
	case class Client(
	  /** GTM client's API relative path. */
		path: Option[String] = None,
	  /** GTM Account ID. */
		accountId: Option[String] = None,
	  /** GTM Container ID. */
		containerId: Option[String] = None,
	  /** GTM Workspace ID. */
		workspaceId: Option[String] = None,
	  /** The Client ID uniquely identifies the GTM client. */
		clientId: Option[String] = None,
	  /** Client display name. @mutable tagmanager.accounts.containers.workspaces.clients.create @mutable tagmanager.accounts.containers.workspaces.clients.update */
		name: Option[String] = None,
	  /** Client type. @mutable tagmanager.accounts.containers.workspaces.clients.create @mutable tagmanager.accounts.containers.workspaces.clients.update */
		`type`: Option[String] = None,
	  /** The client's parameters. @mutable tagmanager.accounts.containers.workspaces.clients.create @mutable tagmanager.accounts.containers.workspaces.clients.update */
		parameter: Option[List[Schema.Parameter]] = None,
	  /** Priority determines relative firing order. @mutable tagmanager.accounts.containers.workspaces.clients.create @mutable tagmanager.accounts.containers.workspaces.clients.update */
		priority: Option[Int] = None,
	  /** The fingerprint of the GTM Client as computed at storage time. This value is recomputed whenever the client is modified. */
		fingerprint: Option[String] = None,
	  /** Auto generated link to the tag manager UI */
		tagManagerUrl: Option[String] = None,
	  /** Parent folder id. */
		parentFolderId: Option[String] = None,
	  /** User notes on how to apply this tag in the container. @mutable tagmanager.accounts.containers.workspaces.tags.create @mutable tagmanager.accounts.containers.workspaces.tags.update */
		notes: Option[String] = None
	)
	
	case class Transformation(
	  /** GTM transformation's API relative path. */
		path: Option[String] = None,
	  /** GTM Account ID. */
		accountId: Option[String] = None,
	  /** GTM Container ID. */
		containerId: Option[String] = None,
	  /** GTM Workspace ID. */
		workspaceId: Option[String] = None,
	  /** The Transformation ID uniquely identifies the GTM transformation. */
		transformationId: Option[String] = None,
	  /** Transformation display name. @mutable tagmanager.accounts.containers.workspaces.transformations.create @mutable tagmanager.accounts.containers.workspaces.transformations.update */
		name: Option[String] = None,
	  /** Transformation type. @mutable tagmanager.accounts.containers.workspaces.transformations.create @mutable tagmanager.accounts.containers.workspaces.transformations.update */
		`type`: Option[String] = None,
	  /** The transformation's parameters. @mutable tagmanager.accounts.containers.workspaces.transformations.create @mutable tagmanager.accounts.containers.workspaces.transformations.update */
		parameter: Option[List[Schema.Parameter]] = None,
	  /** The fingerprint of the GTM Transformation as computed at storage time. This value is recomputed whenever the transformation is modified. */
		fingerprint: Option[String] = None,
	  /** Auto generated link to the tag manager UI */
		tagManagerUrl: Option[String] = None,
	  /** Parent folder id. */
		parentFolderId: Option[String] = None,
	  /** User notes on how to apply this transformation in the container. @mutable tagmanager.accounts.containers.workspaces.transformations.create @mutable tagmanager.accounts.containers.workspaces.transformations.update */
		notes: Option[String] = None
	)
	
	case class Zone(
	  /** GTM Zone's API relative path. */
		path: Option[String] = None,
	  /** GTM Account ID. */
		accountId: Option[String] = None,
	  /** GTM Container ID. */
		containerId: Option[String] = None,
	  /** GTM Workspace ID. */
		workspaceId: Option[String] = None,
	  /** The Zone ID uniquely identifies the GTM Zone. */
		zoneId: Option[String] = None,
	  /** Zone display name. */
		name: Option[String] = None,
	  /** The fingerprint of the GTM Zone as computed at storage time. This value is recomputed whenever the zone is modified. */
		fingerprint: Option[String] = None,
	  /** Auto generated link to the tag manager UI */
		tagManagerUrl: Option[String] = None,
	  /** User notes on how to apply this zone in the container. */
		notes: Option[String] = None,
	  /** Containers that are children of this Zone. */
		childContainer: Option[List[Schema.ZoneChildContainer]] = None,
	  /** This Zone's boundary. */
		boundary: Option[Schema.ZoneBoundary] = None,
	  /** This Zone's type restrictions. */
		typeRestriction: Option[Schema.ZoneTypeRestriction] = None
	)
	
	case class ZoneChildContainer(
	  /** The child container's public id. */
		publicId: Option[String] = None,
	  /** The zone's nickname for the child container. */
		nickname: Option[String] = None
	)
	
	case class ZoneBoundary(
	  /** The conditions that, when conjoined, make up the boundary. */
		condition: Option[List[Schema.Condition]] = None,
	  /** Custom evaluation trigger IDs. A zone will evaluate its boundary conditions when any of the listed triggers are true. */
		customEvaluationTriggerId: Option[List[String]] = None
	)
	
	case class ZoneTypeRestriction(
	  /** True if type restrictions have been enabled for this Zone. */
		enable: Option[Boolean] = None,
	  /** List of type public ids that have been whitelisted for use in this Zone. */
		whitelistedTypeId: Option[List[String]] = None
	)
	
	case class CustomTemplate(
	  /** GTM Custom Template's API relative path. */
		path: Option[String] = None,
	  /** GTM Account ID. */
		accountId: Option[String] = None,
	  /** GTM Container ID. */
		containerId: Option[String] = None,
	  /** GTM Workspace ID. */
		workspaceId: Option[String] = None,
	  /** The Custom Template ID uniquely identifies the GTM custom template. */
		templateId: Option[String] = None,
	  /** Custom Template display name. */
		name: Option[String] = None,
	  /** The fingerprint of the GTM Custom Template as computed at storage time. This value is recomputed whenever the template is modified. */
		fingerprint: Option[String] = None,
	  /** Auto generated link to the tag manager UI */
		tagManagerUrl: Option[String] = None,
	  /** The custom template in text format. */
		templateData: Option[String] = None,
	  /** A reference to the Community Template Gallery entry. */
		galleryReference: Option[Schema.GalleryReference] = None
	)
	
	case class GalleryReference(
	  /** The name of the host for the community gallery template. */
		host: Option[String] = None,
	  /** The name of the owner for the community gallery template. */
		owner: Option[String] = None,
	  /** The name of the repository for the community gallery template. */
		repository: Option[String] = None,
	  /** The version of the community gallery template. */
		version: Option[String] = None,
	  /** If a user has manually edited the community gallery template. */
		isModified: Option[Boolean] = None,
	  /** The signature of the community gallery template as computed at import time. This value is recomputed whenever the template is updated from the gallery. */
		signature: Option[String] = None
	)
	
	object BuiltInVariable {
		enum TypeEnum extends Enum[TypeEnum] { case builtInVariableTypeUnspecified, pageUrl, pageHostname, pagePath, referrer, event, clickElement, clickClasses, clickId, clickTarget, clickUrl, clickText, firstPartyServingUrl, formElement, formClasses, formId, formTarget, formUrl, formText, errorMessage, errorUrl, errorLine, newHistoryUrl, oldHistoryUrl, newHistoryFragment, oldHistoryFragment, newHistoryState, oldHistoryState, historySource, containerVersion, debugMode, randomNumber, containerId, appId, appName, appVersionCode, appVersionName, language, osVersion, platform, sdkVersion, deviceName, resolution, advertiserId, advertisingTrackingEnabled, htmlId, environmentName, ampBrowserLanguage, ampCanonicalPath, ampCanonicalUrl, ampCanonicalHost, ampReferrer, ampTitle, ampClientId, ampClientTimezone, ampClientTimestamp, ampClientScreenWidth, ampClientScreenHeight, ampClientScrollX, ampClientScrollY, ampClientMaxScrollX, ampClientMaxScrollY, ampTotalEngagedTime, ampPageViewId, ampPageLoadTime, ampPageDownloadTime, ampGtmEvent, eventName, firebaseEventParameterCampaign, firebaseEventParameterCampaignAclid, firebaseEventParameterCampaignAnid, firebaseEventParameterCampaignClickTimestamp, firebaseEventParameterCampaignContent, firebaseEventParameterCampaignCp1, firebaseEventParameterCampaignGclid, firebaseEventParameterCampaignSource, firebaseEventParameterCampaignTerm, firebaseEventParameterCurrency, firebaseEventParameterDynamicLinkAcceptTime, firebaseEventParameterDynamicLinkLinkid, firebaseEventParameterNotificationMessageDeviceTime, firebaseEventParameterNotificationMessageId, firebaseEventParameterNotificationMessageName, firebaseEventParameterNotificationMessageTime, firebaseEventParameterNotificationTopic, firebaseEventParameterPreviousAppVersion, firebaseEventParameterPreviousOsVersion, firebaseEventParameterPrice, firebaseEventParameterProductId, firebaseEventParameterQuantity, firebaseEventParameterValue, videoProvider, videoUrl, videoTitle, videoDuration, videoPercent, videoVisible, videoStatus, videoCurrentTime, scrollDepthThreshold, scrollDepthUnits, scrollDepthDirection, elementVisibilityRatio, elementVisibilityTime, elementVisibilityFirstTime, elementVisibilityRecentTime, requestPath, requestMethod, clientName, queryString, serverPageLocationUrl, serverPageLocationPath, serverPageLocationHostname, visitorRegion }
	}
	case class BuiltInVariable(
	  /** GTM BuiltInVariable's API relative path. */
		path: Option[String] = None,
	  /** GTM Account ID. */
		accountId: Option[String] = None,
	  /** GTM Container ID. */
		containerId: Option[String] = None,
	  /** GTM Workspace ID. */
		workspaceId: Option[String] = None,
	  /** Type of built-in variable. @required.tagmanager.accounts.containers.workspaces.built_in_variable.update @mutable tagmanager.accounts.containers.workspaces.built_in_variable.update */
		`type`: Option[Schema.BuiltInVariable.TypeEnum] = None,
	  /** Name of the built-in variable to be used to refer to the built-in variable. */
		name: Option[String] = None
	)
	
	case class GtagConfig(
	  /** Google tag config's API relative path. */
		path: Option[String] = None,
	  /** Google tag account ID. */
		accountId: Option[String] = None,
	  /** Google tag container ID. */
		containerId: Option[String] = None,
	  /** Google tag workspace ID. Only used by GTM containers. Set to 0 otherwise. */
		workspaceId: Option[String] = None,
	  /** The ID uniquely identifies the Google tag config. */
		gtagConfigId: Option[String] = None,
	  /** Google tag config type. @required tagmanager.accounts.containers.workspaces.gtag_config.create @required tagmanager.accounts.containers.workspaces.gtag_config.update @mutable tagmanager.accounts.containers.workspaces.gtag_config.create @mutable tagmanager.accounts.containers.workspaces.gtag_config.update */
		`type`: Option[String] = None,
	  /** The Google tag config's parameters. @mutable tagmanager.accounts.containers.workspaces.gtag_config.create @mutable tagmanager.accounts.containers.workspaces.gtag_config.update */
		parameter: Option[List[Schema.Parameter]] = None,
	  /** The fingerprint of the Google tag config as computed at storage time. This value is recomputed whenever the config is modified. */
		fingerprint: Option[String] = None,
	  /** Auto generated link to the tag manager UI */
		tagManagerUrl: Option[String] = None
	)
	
	case class GetWorkspaceStatusResponse(
	  /** Entities that have been changed in the workspace. */
		workspaceChange: Option[List[Schema.Entity]] = None,
	  /** The merge conflict after sync. */
		mergeConflict: Option[List[Schema.MergeConflict]] = None
	)
	
	case class QuickPreviewResponse(
	  /** The quick previewed container version. */
		containerVersion: Option[Schema.ContainerVersion] = None,
	  /** Whether quick previewing failed when syncing the workspace to the latest container version. */
		syncStatus: Option[Schema.SyncStatus] = None,
	  /** Were there compiler errors or not. */
		compilerError: Option[Boolean] = None
	)
	
	case class ContainerVersion(
	  /** GTM Container Version's API relative path. */
		path: Option[String] = None,
	  /** GTM Account ID. */
		accountId: Option[String] = None,
	  /** GTM Container ID. */
		containerId: Option[String] = None,
	  /** The Container Version ID uniquely identifies the GTM Container Version. */
		containerVersionId: Option[String] = None,
	  /** Container version display name. @mutable tagmanager.accounts.containers.versions.update */
		name: Option[String] = None,
	  /** A value of true indicates this container version has been deleted. */
		deleted: Option[Boolean] = None,
	  /** Container version description. @mutable tagmanager.accounts.containers.versions.update */
		description: Option[String] = None,
	  /** The container that this version was taken from. */
		container: Option[Schema.Container] = None,
	  /** The tags in the container that this version was taken from. */
		tag: Option[List[Schema.Tag]] = None,
	  /** The triggers in the container that this version was taken from. */
		trigger: Option[List[Schema.Trigger]] = None,
	  /** The variables in the container that this version was taken from. */
		variable: Option[List[Schema.Variable]] = None,
	  /** The folders in the container that this version was taken from. */
		folder: Option[List[Schema.Folder]] = None,
	  /** The built-in variables in the container that this version was taken from. */
		builtInVariable: Option[List[Schema.BuiltInVariable]] = None,
	  /** The fingerprint of the GTM Container Version as computed at storage time. This value is recomputed whenever the container version is modified. */
		fingerprint: Option[String] = None,
	  /** Auto generated link to the tag manager UI */
		tagManagerUrl: Option[String] = None,
	  /** The zones in the container that this version was taken from. */
		zone: Option[List[Schema.Zone]] = None,
	  /** The custom templates in the container that this version was taken from. */
		customTemplate: Option[List[Schema.CustomTemplate]] = None,
	  /** The clients in the container that this version was taken from. */
		client: Option[List[Schema.Client]] = None,
	  /** The Google tag configs in the container that this version was taken from. */
		gtagConfig: Option[List[Schema.GtagConfig]] = None,
	  /** The transformations in the container that this version was taken from. */
		transformation: Option[List[Schema.Transformation]] = None
	)
	
	case class CreateContainerVersionRequestVersionOptions(
	  /** The name of the container version to be created. */
		name: Option[String] = None,
	  /** The notes of the container version to be created. */
		notes: Option[String] = None
	)
	
	case class CreateContainerVersionResponse(
	  /** The container version created. */
		containerVersion: Option[Schema.ContainerVersion] = None,
	  /** Whether version creation failed when syncing the workspace to the latest container version. */
		syncStatus: Option[Schema.SyncStatus] = None,
	  /** Compiler errors or not. */
		compilerError: Option[Boolean] = None,
	  /** Auto generated workspace path created as a result of version creation. This field should only be populated if the created version was not a quick preview. */
		newWorkspacePath: Option[String] = None
	)
	
	case class PublishContainerVersionResponse(
	  /** The container version created. */
		containerVersion: Option[Schema.ContainerVersion] = None,
	  /** Compiler errors or not. */
		compilerError: Option[Boolean] = None
	)
	
	case class ListContainerVersionsResponse(
	  /** All container version headers of a GTM Container. */
		containerVersionHeader: Option[List[Schema.ContainerVersionHeader]] = None,
	  /** Continuation token for fetching the next page of results. */
		nextPageToken: Option[String] = None
	)
	
	case class ContainerVersionHeader(
	  /** GTM Container Version's API relative path. */
		path: Option[String] = None,
	  /** GTM Account ID. */
		accountId: Option[String] = None,
	  /** GTM Container ID. */
		containerId: Option[String] = None,
	  /** The Container Version ID uniquely identifies the GTM Container Version. */
		containerVersionId: Option[String] = None,
	  /** Container version display name. */
		name: Option[String] = None,
	  /** Number of macros in the container version. */
		numMacros: Option[String] = None,
	  /** Number of rules in the container version. */
		numRules: Option[String] = None,
	  /** Number of tags in the container version. */
		numTags: Option[String] = None,
	  /** Number of triggers in the container version. */
		numTriggers: Option[String] = None,
	  /** A value of true indicates this container version has been deleted. */
		deleted: Option[Boolean] = None,
	  /** Number of variables in the container version. */
		numVariables: Option[String] = None,
	  /** Number of zones in the container version. */
		numZones: Option[String] = None,
	  /** Number of custom templates in the container version. */
		numCustomTemplates: Option[String] = None,
	  /** Number of clients in the container version. */
		numClients: Option[String] = None,
	  /** Number of Google tag configs in the container version. */
		numGtagConfigs: Option[String] = None,
	  /** Number of transformations in the container version. */
		numTransformations: Option[String] = None
	)
	
	case class ListVariablesResponse(
	  /** All GTM Variables of a GTM Container. */
		variable: Option[List[Schema.Variable]] = None,
	  /** Continuation token for fetching the next page of results. */
		nextPageToken: Option[String] = None
	)
	
	case class RevertVariableResponse(
	  /** Variable as it appears in the latest container version since the last workspace synchronization operation. If no variable is present, that means the variable was deleted in the latest container version. */
		variable: Option[Schema.Variable] = None
	)
	
	case class CreateBuiltInVariableResponse(
	  /** List of created built-in variables. */
		builtInVariable: Option[List[Schema.BuiltInVariable]] = None
	)
	
	case class ListEnabledBuiltInVariablesResponse(
	  /** All GTM BuiltInVariables of a GTM container. */
		builtInVariable: Option[List[Schema.BuiltInVariable]] = None,
	  /** Continuation token for fetching the next page of results. */
		nextPageToken: Option[String] = None
	)
	
	case class RevertBuiltInVariableResponse(
	  /** Whether the built-in variable is enabled after reversion. */
		enabled: Option[Boolean] = None
	)
	
	case class ListTriggersResponse(
	  /** All GTM Triggers of a GTM Container. */
		trigger: Option[List[Schema.Trigger]] = None,
	  /** Continuation token for fetching the next page of results. */
		nextPageToken: Option[String] = None
	)
	
	case class RevertTriggerResponse(
	  /** Trigger as it appears in the latest container version since the last workspace synchronization operation. If no trigger is present, that means the trigger was deleted in the latest container version. */
		trigger: Option[Schema.Trigger] = None
	)
	
	case class ListTagsResponse(
	  /** All GTM Tags of a GTM Container. */
		tag: Option[List[Schema.Tag]] = None,
	  /** Continuation token for fetching the next page of results. */
		nextPageToken: Option[String] = None
	)
	
	case class RevertTagResponse(
	  /** Tag as it appears in the latest container version since the last workspace synchronization operation. If no tag is present, that means the tag was deleted in the latest container version. */
		tag: Option[Schema.Tag] = None
	)
	
	case class ListGtagConfigResponse(
	  /** All Google tag configs in a Container. */
		gtagConfig: Option[List[Schema.GtagConfig]] = None,
	  /** Continuation token for fetching the next page of results. */
		nextPageToken: Option[String] = None
	)
	
	case class ListTemplatesResponse(
	  /** All GTM Custom Templates of a GTM Container. */
		template: Option[List[Schema.CustomTemplate]] = None,
	  /** Continuation token for fetching the next page of results. */
		nextPageToken: Option[String] = None
	)
	
	case class RevertTemplateResponse(
	  /** Template as it appears in the latest container version since the last workspace synchronization operation. If no template is present, that means the template was deleted in the latest container version. */
		template: Option[Schema.CustomTemplate] = None
	)
	
	case class ListFoldersResponse(
	  /** All GTM Folders of a GTM Container. */
		folder: Option[List[Schema.Folder]] = None,
	  /** Continuation token for fetching the next page of results. */
		nextPageToken: Option[String] = None
	)
	
	case class FolderEntities(
	  /** The list of tags inside the folder. */
		tag: Option[List[Schema.Tag]] = None,
	  /** The list of variables inside the folder. */
		variable: Option[List[Schema.Variable]] = None,
	  /** The list of triggers inside the folder. */
		trigger: Option[List[Schema.Trigger]] = None,
	  /** Continuation token for fetching the next page of results. */
		nextPageToken: Option[String] = None
	)
	
	case class RevertFolderResponse(
	  /** Folder as it appears in the latest container version since the last workspace synchronization operation. If no folder is present, that means the folder was deleted in the latest container version. */
		folder: Option[Schema.Folder] = None
	)
	
	object Environment {
		enum TypeEnum extends Enum[TypeEnum] { case user, live, latest, workspace }
	}
	case class Environment(
	  /** GTM Environment's API relative path. */
		path: Option[String] = None,
	  /** GTM Account ID. */
		accountId: Option[String] = None,
	  /** GTM Container ID. */
		containerId: Option[String] = None,
	  /** GTM Environment ID uniquely identifies the GTM Environment. */
		environmentId: Option[String] = None,
	  /** The type of this environment. */
		`type`: Option[Schema.Environment.TypeEnum] = None,
	  /** The fingerprint of the GTM environment as computed at storage time. This value is recomputed whenever the environment is modified. */
		fingerprint: Option[String] = None,
	  /** The environment display name. Can be set or changed only on USER type environments. @mutable tagmanager.accounts.containers.environments.create @mutable tagmanager.accounts.containers.environments.update */
		name: Option[String] = None,
	  /** The environment description. Can be set or changed only on USER type environments. @mutable tagmanager.accounts.containers.environments.create @mutable tagmanager.accounts.containers.environments.update */
		description: Option[String] = None,
	  /** Whether or not to enable debug by default for the environment. @mutable tagmanager.accounts.containers.environments.create @mutable tagmanager.accounts.containers.environments.update */
		enableDebug: Option[Boolean] = None,
	  /** Default preview page url for the environment. @mutable tagmanager.accounts.containers.environments.create @mutable tagmanager.accounts.containers.environments.update */
		url: Option[String] = None,
	  /** The environment authorization code. */
		authorizationCode: Option[String] = None,
	  /** The last update time-stamp for the authorization code. */
		authorizationTimestamp: Option[String] = None,
	  /** Represents a link to a container version. */
		containerVersionId: Option[String] = None,
	  /** Represents a link to a quick preview of a workspace. */
		workspaceId: Option[String] = None,
	  /** Auto generated link to the tag manager UI */
		tagManagerUrl: Option[String] = None
	)
	
	case class ListEnvironmentsResponse(
	  /** All Environments of a GTM Container. */
		environment: Option[List[Schema.Environment]] = None,
	  /** Continuation token for fetching the next page of results. */
		nextPageToken: Option[String] = None
	)
	
	case class ListZonesResponse(
	  /** All GTM Zones of a GTM Container. */
		zone: Option[List[Schema.Zone]] = None,
	  /** Continuation token for fetching the next page of results. */
		nextPageToken: Option[String] = None
	)
	
	case class RevertZoneResponse(
	  /** Zone as it appears in the latest container version since the last workspace synchronization operation. If no zone is present, that means the zone was deleted in the latest container version. */
		zone: Option[Schema.Zone] = None
	)
	
	case class ListClientsResponse(
	  /** All GTM Clients of a GTM Container. */
		client: Option[List[Schema.Client]] = None,
	  /** Continuation token for fetching the next page of results. */
		nextPageToken: Option[String] = None
	)
	
	case class RevertClientResponse(
	  /** Client as it appears in the latest container version since the last workspace synchronization operation. If no client is present, that means the client was deleted in the latest container version. */
		client: Option[Schema.Client] = None
	)
	
	case class ListTransformationsResponse(
	  /** All GTM Transformations of a GTM Container. */
		transformation: Option[List[Schema.Transformation]] = None,
	  /** Continuation token for fetching the next page of results. */
		nextPageToken: Option[String] = None
	)
	
	case class RevertTransformationResponse(
	  /** Transformation as it appears in the latest container version since the last workspace synchronization operation. If no transformation is present, that means the transformation was deleted in the latest container version. */
		transformation: Option[Schema.Transformation] = None
	)
}
