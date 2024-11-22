package com.boresjo.play.api.google.firebase

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
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
	
	object AndroidApp {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, ACTIVE, DELETED }
	}
	case class AndroidApp(
	  /** The resource name of the AndroidApp, in the format: projects/ PROJECT_IDENTIFIER/androidApps/APP_ID &#42; PROJECT_IDENTIFIER: the parent Project's [`ProjectNumber`](../projects#FirebaseProject.FIELDS.project_number) &#42;&#42;&#42;(recommended)&#42;&#42;&#42; or its [`ProjectId`](../projects#FirebaseProject.FIELDS.project_id). Learn more about using project identifiers in Google's [AIP 2510 standard](https://google.aip.dev/cloud/2510). Note that the value for PROJECT_IDENTIFIER in any response body will be the `ProjectId`. &#42; APP_ID: the globally unique, Firebase-assigned identifier for the App (see [`appId`](../projects.androidApps#AndroidApp.FIELDS.app_id)). */
		name: Option[String] = None,
	  /** Output only. Immutable. The globally unique, Firebase-assigned identifier for the `AndroidApp`. This identifier should be treated as an opaque token, as the data format is not specified. */
		appId: Option[String] = None,
	  /** The user-assigned display name for the `AndroidApp`. */
		displayName: Option[String] = None,
	  /** Output only. Immutable. A user-assigned unique identifier of the parent FirebaseProject for the `AndroidApp`. */
		projectId: Option[String] = None,
	  /** Immutable. The canonical package name of the Android app as would appear in the Google Play Developer Console. */
		packageName: Option[String] = None,
	  /** The globally unique, Google-assigned identifier (UID) for the Firebase API key associated with the `AndroidApp`. Be aware that this value is the UID of the API key, _not_ the [`keyString`](https://cloud.google.com/api-keys/docs/reference/rest/v2/projects.locations.keys#Key.FIELDS.key_string) of the API key. The `keyString` is the value that can be found in the App's [configuration artifact](../../rest/v1beta1/projects.androidApps/getConfig). If `api_key_id` is not set in requests to [`androidApps.Create`](../../rest/v1beta1/projects.androidApps/create), then Firebase automatically associates an `api_key_id` with the `AndroidApp`. This auto-associated key may be an existing valid key or, if no valid key exists, a new one will be provisioned. In patch requests, `api_key_id` cannot be set to an empty value, and the new UID must have no restrictions or only have restrictions that are valid for the associated `AndroidApp`. We recommend using the [Google Cloud Console](https://console.cloud.google.com/apis/credentials) to manage API keys. */
		apiKeyId: Option[String] = None,
	  /** Output only. The lifecycle state of the App. */
		state: Option[Schema.AndroidApp.StateEnum] = None,
	  /** The SHA1 certificate hashes for the AndroidApp. */
		sha1Hashes: Option[List[String]] = None,
	  /** The SHA256 certificate hashes for the AndroidApp. */
		sha256Hashes: Option[List[String]] = None,
	  /** Output only. If the App has been removed from the Project, this is the timestamp of when the App is considered expired and will be permanently deleted. After this time, the App cannot be undeleted (that is, restored to the Project). This value is only provided if the App is in the `DELETED` state. */
		expireTime: Option[String] = None,
	  /** This checksum is computed by the server based on the value of other fields, and it may be sent with update requests to ensure the client has an up-to-date value before proceeding. Learn more about `etag` in Google's [AIP-154 standard](https://google.aip.dev/154#declarative-friendly-resources). This etag is strongly validated. */
		etag: Option[String] = None
	)
	
	case class ListAndroidAppsResponse(
	  /** List of each `AndroidApp` associated with the specified `FirebaseProject`. */
		apps: Option[List[Schema.AndroidApp]] = None,
	  /** If the result list is too large to fit in a single response, then a token is returned. If the string is empty, then this response is the last page of results. This token can be used in a subsequent call to `ListAndroidApps` to find the next group of Apps. Page tokens are short-lived and should not be persisted. */
		nextPageToken: Option[String] = None
	)
	
	case class RemoveAndroidAppRequest(
	  /** If set to true, and the App is not found, the request will succeed but no action will be taken on the server. */
		allowMissing: Option[Boolean] = None,
	  /** If set to true, the request is only validated. The App will _not_ be removed. */
		validateOnly: Option[Boolean] = None,
	  /** Checksum provided in the AndroidApp resource. If provided, this checksum ensures that the client has an up-to-date value before proceeding. */
		etag: Option[String] = None,
	  /** Determines whether to _immediately_ delete the AndroidApp. If set to true, the App is immediately deleted from the Project and cannot be undeleted (that is, restored to the Project). If not set, defaults to false, which means the App will be set to expire in 30 days. Within the 30 days, the App may be restored to the Project using UndeleteAndroidApp. */
		immediate: Option[Boolean] = None
	)
	
	case class UndeleteAndroidAppRequest(
	  /** If set to true, the request is only validated. The App will _not_ be undeleted. */
		validateOnly: Option[Boolean] = None,
	  /** Checksum provided in the AndroidApp resource. If provided, this checksum ensures that the client has an up-to-date value before proceeding. */
		etag: Option[String] = None
	)
	
	case class AndroidAppConfig(
	  /** The filename that the configuration artifact for the `AndroidApp` is typically saved as. For example: `google-services.json` */
		configFilename: Option[String] = None,
	  /** The contents of the JSON configuration file. */
		configFileContents: Option[String] = None
	)
	
	case class ListShaCertificatesResponse(
	  /** The list of each `ShaCertificate` associated with the `AndroidApp`. */
		certificates: Option[List[Schema.ShaCertificate]] = None
	)
	
	object ShaCertificate {
		enum CertTypeEnum extends Enum[CertTypeEnum] { case SHA_CERTIFICATE_TYPE_UNSPECIFIED, SHA_1, SHA_256 }
	}
	case class ShaCertificate(
	  /** The resource name of the ShaCertificate for the AndroidApp, in the format: projects/PROJECT_IDENTIFIER/androidApps/APP_ID/sha/SHA_HASH &#42; PROJECT_IDENTIFIER: the parent Project's [`ProjectNumber`](../projects#FirebaseProject.FIELDS.project_number) &#42;&#42;&#42;(recommended)&#42;&#42;&#42; or its [`ProjectId`](../projects#FirebaseProject.FIELDS.project_id). Learn more about using project identifiers in Google's [AIP 2510 standard](https://google.aip.dev/cloud/2510). Note that the value for PROJECT_IDENTIFIER in any response body will be the `ProjectId`. &#42; APP_ID: the globally unique, Firebase-assigned identifier for the App (see [`appId`](../projects.androidApps#AndroidApp.FIELDS.app_id)). &#42; SHA_HASH: the certificate hash for the App (see [`shaHash`](../projects.androidApps.sha#ShaCertificate.FIELDS.sha_hash)). */
		name: Option[String] = None,
	  /** The certificate hash for the `AndroidApp`. */
		shaHash: Option[String] = None,
	  /** The type of SHA certificate encoded in the hash. */
		certType: Option[Schema.ShaCertificate.CertTypeEnum] = None
	)
	
	case class Empty(
	
	)
	
	case class AddGoogleAnalyticsRequest(
	  /** The ID for the existing [Google Analytics account](http://www.google.com/analytics/) that you want to link with the `FirebaseProject`. Specifying this field will provision a new Google Analytics property in your Google Analytics account and associate the new property with the `FirebaseProject`. */
		analyticsAccountId: Option[String] = None,
	  /** The ID for the existing Google Analytics property that you want to associate with the `FirebaseProject`. */
		analyticsPropertyId: Option[String] = None
	)
	
	case class AnalyticsDetails(
	  /** The Analytics Property object associated with the specified `FirebaseProject`. This object contains the details of the Google Analytics property associated with the Project. */
		analyticsProperty: Option[Schema.AnalyticsProperty] = None,
	  /**  - For `AndroidApps` and `IosApps`: a map of `app` to `streamId` for each Firebase App in the specified `FirebaseProject`. Each `app` and `streamId` appears only once. - For `WebApps`: a map of `app` to `streamId` and `measurementId` for each `WebApp` in the specified `FirebaseProject`. Each `app`, `streamId`, and `measurementId` appears only once. */
		streamMappings: Option[List[Schema.StreamMapping]] = None
	)
	
	case class AnalyticsProperty(
	  /** The globally unique, Google-assigned identifier of the Google Analytics property associated with the specified `FirebaseProject`. If you called [`AddGoogleAnalytics`](../../v1beta1/projects/addGoogleAnalytics) to link the `FirebaseProject` with a Google Analytics account, the value in this `id` field is the same as the ID of the property either specified or provisioned with that call to `AddGoogleAnalytics`. */
		id: Option[String] = None,
	  /** The display name of the Google Analytics property associated with the specified `FirebaseProject`. */
		displayName: Option[String] = None,
	  /** Output only. The ID of the [Google Analytics account](https://www.google.com/analytics/) for the Google Analytics property associated with the specified FirebaseProject. */
		analyticsAccountId: Option[String] = None
	)
	
	case class StreamMapping(
	  /** The resource name of the Firebase App associated with the Google Analytics data stream, in the format: projects/PROJECT_IDENTIFIER/androidApps/APP_ID or projects/PROJECT_IDENTIFIER/iosApps/APP_ID or projects/PROJECT_IDENTIFIER /webApps/APP_ID Refer to the `FirebaseProject` [`name`](../projects#FirebaseProject.FIELDS.name) field for details about PROJECT_IDENTIFIER values. */
		app: Option[String] = None,
	  /** The unique Google-assigned identifier of the Google Analytics data stream associated with the Firebase App. Learn more about Google Analytics data streams in the [Analytics documentation](https://support.google.com/analytics/answer/9303323). */
		streamId: Option[String] = None,
	  /** Applicable for Firebase Web Apps only. The unique Google-assigned identifier of the Google Analytics web stream associated with the Firebase Web App. Firebase SDKs use this ID to interact with Google Analytics APIs. Learn more about this ID and Google Analytics web streams in the [Analytics documentation](https://support.google.com/analytics/answer/9304153). */
		measurementId: Option[String] = None
	)
	
	case class RemoveAnalyticsRequest(
	  /** Optional. The ID of the Google Analytics property associated with the specified `FirebaseProject`. - If not set, then the Google Analytics property that is currently associated with the specified `FirebaseProject` is removed. - If set, and the specified `FirebaseProject` is currently associated with a &#42;different&#42; Google Analytics property, then the response is a `412 Precondition Failed` error.  */
		analyticsPropertyId: Option[String] = None
	)
	
	object IosApp {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, ACTIVE, DELETED }
	}
	case class IosApp(
	  /** The resource name of the IosApp, in the format: projects/PROJECT_IDENTIFIER /iosApps/APP_ID &#42; PROJECT_IDENTIFIER: the parent Project's [`ProjectNumber`](../projects#FirebaseProject.FIELDS.project_number) &#42;&#42;&#42;(recommended)&#42;&#42;&#42; or its [`ProjectId`](../projects#FirebaseProject.FIELDS.project_id). Learn more about using project identifiers in Google's [AIP 2510 standard](https://google.aip.dev/cloud/2510). Note that the value for PROJECT_IDENTIFIER in any response body will be the `ProjectId`. &#42; APP_ID: the globally unique, Firebase-assigned identifier for the App (see [`appId`](../projects.iosApps#IosApp.FIELDS.app_id)). */
		name: Option[String] = None,
	  /** Output only. Immutable. The globally unique, Firebase-assigned identifier for the `IosApp`. This identifier should be treated as an opaque token, as the data format is not specified. */
		appId: Option[String] = None,
	  /** The user-assigned display name for the `IosApp`. */
		displayName: Option[String] = None,
	  /** Output only. Immutable. A user-assigned unique identifier of the parent FirebaseProject for the `IosApp`. */
		projectId: Option[String] = None,
	  /** Immutable. The canonical bundle ID of the iOS app as it would appear in the iOS AppStore. */
		bundleId: Option[String] = None,
	  /** The automatically generated Apple ID assigned to the iOS app by Apple in the iOS App Store. */
		appStoreId: Option[String] = None,
	  /** The Apple Developer Team ID associated with the App in the App Store. */
		teamId: Option[String] = None,
	  /** The globally unique, Google-assigned identifier (UID) for the Firebase API key associated with the `IosApp`. Be aware that this value is the UID of the API key, _not_ the [`keyString`](https://cloud.google.com/api-keys/docs/reference/rest/v2/projects.locations.keys#Key.FIELDS.key_string) of the API key. The `keyString` is the value that can be found in the App's [configuration artifact](../../rest/v1beta1/projects.iosApps/getConfig). If `api_key_id` is not set in requests to [`iosApps.Create`](../../rest/v1beta1/projects.iosApps/create), then Firebase automatically associates an `api_key_id` with the `IosApp`. This auto-associated key may be an existing valid key or, if no valid key exists, a new one will be provisioned. In patch requests, `api_key_id` cannot be set to an empty value, and the new UID must have no restrictions or only have restrictions that are valid for the associated `IosApp`. We recommend using the [Google Cloud Console](https://console.cloud.google.com/apis/credentials) to manage API keys. */
		apiKeyId: Option[String] = None,
	  /** Output only. The lifecycle state of the App. */
		state: Option[Schema.IosApp.StateEnum] = None,
	  /** Output only. If the App has been removed from the Project, this is the timestamp of when the App is considered expired and will be permanently deleted. After this time, the App cannot be undeleted (that is, restored to the Project). This value is only provided if the App is in the `DELETED` state. */
		expireTime: Option[String] = None,
	  /** This checksum is computed by the server based on the value of other fields, and it may be sent with update requests to ensure the client has an up-to-date value before proceeding. Learn more about `etag` in Google's [AIP-154 standard](https://google.aip.dev/154#declarative-friendly-resources). This etag is strongly validated. */
		etag: Option[String] = None
	)
	
	case class ListIosAppsResponse(
	  /** List of each `IosApp` associated with the specified `FirebaseProject`. */
		apps: Option[List[Schema.IosApp]] = None,
	  /** If the result list is too large to fit in a single response, then a token is returned. If the string is empty, then this response is the last page of results. This token can be used in a subsequent call to `ListIosApps` to find the next group of Apps. Page tokens are short-lived and should not be persisted. */
		nextPageToken: Option[String] = None
	)
	
	case class RemoveIosAppRequest(
	  /** If set to true, and the App is not found, the request will succeed but no action will be taken on the server. */
		allowMissing: Option[Boolean] = None,
	  /** If set to true, the request is only validated. The App will _not_ be removed. */
		validateOnly: Option[Boolean] = None,
	  /** Checksum provided in the IosApp resource. If provided, this checksum ensures that the client has an up-to-date value before proceeding. */
		etag: Option[String] = None,
	  /** Determines whether to _immediately_ delete the IosApp. If set to true, the App is immediately deleted from the Project and cannot be undeleted (that is, restored to the Project). If not set, defaults to false, which means the App will be set to expire in 30 days. Within the 30 days, the App may be restored to the Project using UndeleteIosApp */
		immediate: Option[Boolean] = None
	)
	
	case class UndeleteIosAppRequest(
	  /** If set to true, the request is only validated. The App will _not_ be undeleted. */
		validateOnly: Option[Boolean] = None,
	  /** Checksum provided in the IosApp resource. If provided, this checksum ensures that the client has an up-to-date value before proceeding. */
		etag: Option[String] = None
	)
	
	case class IosAppConfig(
	  /** The filename that the configuration artifact for the `IosApp` is typically saved as. For example: `GoogleService-Info.plist` */
		configFilename: Option[String] = None,
	  /** The content of the XML configuration file. */
		configFileContents: Option[String] = None
	)
	
	object FirebaseProject {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, ACTIVE, DELETED }
	}
	case class FirebaseProject(
	  /** The resource name of the Project, in the format: projects/PROJECT_IDENTIFIER PROJECT_IDENTIFIER: the Project's [`ProjectNumber`](../projects#FirebaseProject.FIELDS.project_number) &#42;&#42;&#42;(recommended)&#42;&#42;&#42; or its [`ProjectId`](../projects#FirebaseProject.FIELDS.project_id). Learn more about using project identifiers in Google's [AIP 2510 standard](https://google.aip.dev/cloud/2510). Note that the value for PROJECT_IDENTIFIER in any response body will be the `ProjectId`. */
		name: Option[String] = None,
	  /** Output only. Immutable. A user-assigned unique identifier for the Project. This identifier may appear in URLs or names for some Firebase resources associated with the Project, but it should generally be treated as a convenience alias to reference the Project. */
		projectId: Option[String] = None,
	  /** Output only. Immutable. The globally unique, Google-assigned canonical identifier for the Project. Use this identifier when configuring integrations and/or making API calls to Firebase or third-party services. */
		projectNumber: Option[String] = None,
	  /** The user-assigned display name of the Project. */
		displayName: Option[String] = None,
	  /** Output only. &#42;&#42;DEPRECATED.&#42;&#42; _Auto-provisioning of these resources is changing, so this object no longer reliably provides information about the Project. Instead, retrieve information about each resource directly from its resource-specific API._ The default Firebase resources associated with the Project. */
		resources: Option[Schema.DefaultResources] = None,
	  /** Output only. The lifecycle state of the Project. */
		state: Option[Schema.FirebaseProject.StateEnum] = None,
	  /** A set of user-defined annotations for the FirebaseProject. Learn more about annotations in Google's [AIP-128 standard](https://google.aip.dev/128#annotations). These annotations are intended solely for developers and client-side tools. Firebase services will not mutate this annotations set. */
		annotations: Option[Map[String, String]] = None,
	  /** This checksum is computed by the server based on the value of other fields, and it may be sent with update requests to ensure the client has an up-to-date value before proceeding. Learn more about `etag` in Google's [AIP-154 standard](https://google.aip.dev/154#declarative-friendly-resources). This etag is strongly validated. */
		etag: Option[String] = None
	)
	
	case class DefaultResources(
	  /** Output only. &#42;&#42;DEPRECATED.&#42;&#42; _Instead, find the name of the default Firebase Hosting site using [ListSites](https://firebase.google.com/docs/reference/hosting/rest/v1beta1/projects.sites/list) within the Firebase Hosting REST API. If the default Hosting site for the Project has not yet been provisioned, the return might not contain a default site._ The name of the default Firebase Hosting site, in the format: PROJECT_ID Though rare, your `projectId` might already be used as the name for an existing Hosting site in another project (learn more about creating non-default, [additional sites](https://firebase.google.com/docs/hosting/multisites)). In these cases, your `projectId` is appended with a hyphen then five alphanumeric characters to create your default Hosting site name. For example, if your `projectId` is `myproject123`, your default Hosting site name might be: `myproject123-a5c16` */
		hostingSite: Option[String] = None,
	  /** Output only. &#42;&#42;DEPRECATED.&#42;&#42; _Instead, find the name of the default Realtime Database instance using the [list endpoint](https://firebase.google.com/docs/reference/rest/database/database-management/rest/v1beta/projects.locations.instances/list) within the Firebase Realtime Database REST API. If the default Realtime Database instance for a Project has not yet been provisioned, the return might not contain a default instance._ The default Firebase Realtime Database instance name, in the format: PROJECT_ID Though rare, your `projectId` might already be used as the name for an existing Realtime Database instance in another project (learn more about [database sharding](https://firebase.google.com/docs/database/usage/sharding)). In these cases, your `projectId` is appended with a hyphen then five alphanumeric characters to create your default Realtime Database instance name. For example, if your `projectId` is `myproject123`, your default database instance name might be: `myproject123-a5c16` */
		realtimeDatabaseInstance: Option[String] = None,
	  /** Output only. &#42;&#42;DEPRECATED.&#42;&#42; _Instead, find the name of the default Cloud Storage for Firebase bucket using the [list endpoint](https://firebase.google.com/docs/reference/rest/storage/rest/v1beta/projects.buckets/list) within the Cloud Storage for Firebase REST API. If the default bucket for the Project has not yet been provisioned, the return might not contain a default bucket._ The name of the default Cloud Storage for Firebase bucket, in one of the following formats: &#42; If provisioned _before_ October 30, 2024: PROJECT_ID.firebasestorage.app &#42; If provisioned _on or after_ October 30, 2024: PROJECT_ID.firebasestorage.app */
		storageBucket: Option[String] = None,
	  /** Output only. &#42;&#42;DEPRECATED.&#42;&#42; _Instead, use product-specific REST APIs to find the location of each resource in a Project. This field may not be populated, especially for newly provisioned projects after October 30, 2024._ The ID of the Project's ["location for default Google Cloud resources"](https://firebase.google.com/docs/projects/locations#default-cloud-location), which are resources associated with Google App Engine. The location is one of the available [Google App Engine locations](https://cloud.google.com/about/locations#region). This field is omitted if the location for default Google Cloud resources has not been set. */
		locationId: Option[String] = None
	)
	
	case class ListFirebaseProjectsResponse(
	  /** One page of the list of Projects that are accessible to the caller. */
		results: Option[List[Schema.FirebaseProject]] = None,
	  /** If the result list is too large to fit in a single response, then a token is returned. If the string is empty, then this response is the last page of results. This token can be used in a subsequent calls to `ListFirebaseProjects` to find the next group of Projects. Page tokens are short-lived and should not be persisted. */
		nextPageToken: Option[String] = None
	)
	
	case class AddFirebaseRequest(
	  /** &#42;&#42;DEPRECATED.&#42;&#42; _Instead, use product-specific REST APIs to work with the location of each resource in a Project. This field may be ignored, especially for newly provisioned projects after October 30, 2024._ The ID of the Project's ["location for default Google Cloud resources"](https://firebase.google.com/docs/projects/locations#default-cloud-location), which are resources associated with Google App Engine. The location must be one of the available [Google App Engine locations](https://cloud.google.com/about/locations#region). */
		locationId: Option[String] = None
	)
	
	case class ListAvailableProjectsResponse(
	  /** The list of Google Cloud `Projects` which can have Firebase resources added to them. */
		projectInfo: Option[List[Schema.ProjectInfo]] = None,
	  /** If the result list is too large to fit in a single response, then a token is returned. If the string is empty, then this response is the last page of results. This token can be used in a subsequent calls to `ListAvailableProjects` to find the next group of Projects. Page tokens are short-lived and should not be persisted. */
		nextPageToken: Option[String] = None
	)
	
	case class ProjectInfo(
	  /** The resource name of the Google Cloud `Project` to which Firebase resources can be added, in the format: projects/PROJECT_IDENTIFIER Refer to the `FirebaseProject` [`name`](../projects#FirebaseProject.FIELDS.name) field for details about PROJECT_IDENTIFIER values. */
		project: Option[String] = None,
	  /** The user-assigned display name of the Google Cloud `Project`, for example: `My App`. */
		displayName: Option[String] = None,
	  /** &#42;&#42;DEPRECATED&#42;&#42; _Instead, use product-specific REST APIs to work with the location of each resource in a Project. This field may not be populated, especially for newly provisioned projects after October 30, 2024._ The ID of the Project's ["location for default Google Cloud resources"](https://firebase.google.com/docs/projects/locations#default-cloud-location). The location is one of the available [Google App Engine locations](https://cloud.google.com/about/locations#region). Not all Projects will have this field populated. If it is not populated, it means that the Project does not yet have a location for default Google Cloud resources. */
		locationId: Option[String] = None
	)
	
	case class ListAvailableLocationsResponse(
	  /** One page of results from a call to `ListAvailableLocations`. */
		locations: Option[List[Schema.Location]] = None,
	  /** If the result list is too large to fit in a single response, then a token is returned. If the string is empty, then this response is the last page of results and all available locations have been listed. This token can be used in a subsequent call to `ListAvailableLocations` to find more locations. Page tokens are short-lived and should not be persisted. */
		nextPageToken: Option[String] = None
	)
	
	object Location {
		enum TypeEnum extends Enum[TypeEnum] { case LOCATION_TYPE_UNSPECIFIED, REGIONAL, MULTI_REGIONAL }
		enum FeaturesEnum extends Enum[FeaturesEnum] { case LOCATION_FEATURE_UNSPECIFIED, FIRESTORE, DEFAULT_STORAGE, FUNCTIONS }
	}
	case class Location(
	  /** The ID of the Project's location for default Google Cloud resources. It will be one of the available [Google App Engine locations](https://cloud.google.com/about/locations#region). */
		locationId: Option[String] = None,
	  /** Indicates whether the location for default Google Cloud resources is a [regional or multi-regional location](https://firebase.google.com/docs/projects/locations#types) for data replication. */
		`type`: Option[Schema.Location.TypeEnum] = None,
	  /** Products and services that are available in the location for default Google Cloud resources. */
		features: Option[List[Schema.Location.FeaturesEnum]] = None
	)
	
	case class FinalizeDefaultLocationRequest(
	  /** &#42;&#42;DEPRECATED&#42;&#42; The ID of the Project's ["location for default Google Cloud resources"](https://firebase.google.com/docs/projects/locations#default-cloud-location), which are resources associated with Google App Engine. The location must be one of the available [Google App Engine locations](https://cloud.google.com/about/locations#region). */
		locationId: Option[String] = None
	)
	
	case class AdminSdkConfig(
	  /** Immutable. A user-assigned unique identifier for the `FirebaseProject`. This identifier may appear in URLs or names for some Firebase resources associated with the Project, but it should generally be treated as a convenience alias to reference the Project. */
		projectId: Option[String] = None,
	  /** &#42;&#42;DEPRECATED.&#42;&#42; _Instead, find the URL of the default Realtime Database instance using the [list endpoint](https://firebase.google.com/docs/reference/rest/database/database-management/rest/v1beta/projects.locations.instances/list) within the Firebase Realtime Database REST API. If the default instance for the Project has not yet been provisioned, the return might not contain a default instance. Note that the config that's generated for the Firebase console or the Firebase CLI uses the Realtime Database endpoint to populate this value for that config._ The URL of the default Firebase Realtime Database instance. */
		databaseURL: Option[String] = None,
	  /** &#42;&#42;DEPRECATED.&#42;&#42; _Instead, find the name of the default Cloud Storage for Firebase bucket using the [list endpoint](https://firebase.google.com/docs/reference/rest/storage/rest/v1beta/projects.buckets/list) within the Cloud Storage for Firebase REST API. If the default bucket for the Project has not yet been provisioned, the return might not contain a default bucket. Note that the config that's generated for the Firebase console or the Firebase CLI uses the Cloud Storage for Firebase endpoint to populate this value for that config._ The name of the default Cloud Storage for Firebase bucket. */
		storageBucket: Option[String] = None,
	  /** &#42;&#42;DEPRECATED.&#42;&#42; _Instead, use product-specific REST APIs to find the location of each resource in a Project. This field may not be populated, especially for newly provisioned projects after October 30, 2024._ The ID of the Project's ["location for default Google Cloud resources"](https://firebase.google.com/docs/projects/locations#default-cloud-location), which are resources associated with Google App Engine. The location is one of the available [App Engine locations](https://cloud.google.com/about/locations#region). This field is omitted if the location for default Google Cloud resources has not been set. */
		locationId: Option[String] = None
	)
	
	case class SearchFirebaseAppsResponse(
	  /** One page of results from a call to `SearchFirebaseApps`. */
		apps: Option[List[Schema.FirebaseAppInfo]] = None,
	  /** If the result list is too large to fit in a single response, then a token is returned. This token can be used in a subsequent calls to `SearchFirebaseApps` to find the next group of Apps. Page tokens are short-lived and should not be persisted. */
		nextPageToken: Option[String] = None
	)
	
	object FirebaseAppInfo {
		enum PlatformEnum extends Enum[PlatformEnum] { case PLATFORM_UNSPECIFIED, IOS, ANDROID, WEB }
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, ACTIVE, DELETED }
	}
	case class FirebaseAppInfo(
	  /** The resource name of the Firebase App, in the format: projects/PROJECT_ID /iosApps/APP_ID or projects/PROJECT_ID/androidApps/APP_ID or projects/ PROJECT_ID/webApps/APP_ID */
		name: Option[String] = None,
	  /** The user-assigned display name of the Firebase App. */
		displayName: Option[String] = None,
	  /** The platform of the Firebase App. */
		platform: Option[Schema.FirebaseAppInfo.PlatformEnum] = None,
	  /** Output only. Immutable. The globally unique, Firebase-assigned identifier for the `WebApp`. This identifier should be treated as an opaque token, as the data format is not specified. */
		appId: Option[String] = None,
	  /** Output only. Immutable. The platform-specific identifier of the App. &#42;Note:&#42; For most use cases, use `appId`, which is the canonical, globally unique identifier for referencing an App. This string is derived from a native identifier for each platform: `packageName` for an `AndroidApp`, `bundleId` for an `IosApp`, and `webId` for a `WebApp`. Its contents should be treated as opaque, as the native identifier format may change as platforms evolve. This string is only unique within a `FirebaseProject` and its associated Apps. */
		namespace: Option[String] = None,
	  /** The globally unique, Google-assigned identifier (UID) for the Firebase API key associated with the App. Be aware that this value is the UID of the API key, _not_ the [`keyString`](https://cloud.google.com/api-keys/docs/reference/rest/v2/projects.locations.keys#Key.FIELDS.key_string) of the API key. The `keyString` is the value that can be found in the App's configuration artifact ([`AndroidApp`](../../rest/v1beta1/projects.androidApps/getConfig) | [`IosApp`](../../rest/v1beta1/projects.iosApps/getConfig) | [`WebApp`](../../rest/v1beta1/projects.webApps/getConfig)). If `api_key_id` is not set in requests to create the App ([`AndroidApp`](../../rest/v1beta1/projects.androidApps/create) | [`IosApp`](../../rest/v1beta1/projects.iosApps/create) | [`WebApp`](../../rest/v1beta1/projects.webApps/create)), then Firebase automatically associates an `api_key_id` with the App. This auto-associated key may be an existing valid key or, if no valid key exists, a new one will be provisioned. */
		apiKeyId: Option[String] = None,
	  /** Output only. The lifecycle state of the App. */
		state: Option[Schema.FirebaseAppInfo.StateEnum] = None,
	  /** Output only. If the App has been removed from the Project, this is the timestamp of when the App is considered expired and will be permanently deleted. After this time, the App cannot be undeleted (that is, restored to the Project). This value is only provided if the App is in the `DELETED` state. */
		expireTime: Option[String] = None
	)
	
	object WebApp {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, ACTIVE, DELETED }
	}
	case class WebApp(
	  /** The resource name of the WebApp, in the format: projects/PROJECT_IDENTIFIER /webApps/APP_ID &#42; PROJECT_IDENTIFIER: the parent Project's [`ProjectNumber`](../projects#FirebaseProject.FIELDS.project_number) &#42;&#42;&#42;(recommended)&#42;&#42;&#42; or its [`ProjectId`](../projects#FirebaseProject.FIELDS.project_id). Learn more about using project identifiers in Google's [AIP 2510 standard](https://google.aip.dev/cloud/2510). Note that the value for PROJECT_IDENTIFIER in any response body will be the `ProjectId`. &#42; APP_ID: the globally unique, Firebase-assigned identifier for the App (see [`appId`](../projects.webApps#WebApp.FIELDS.app_id)). */
		name: Option[String] = None,
	  /** Output only. Immutable. The globally unique, Firebase-assigned identifier for the `WebApp`. This identifier should be treated as an opaque token, as the data format is not specified. */
		appId: Option[String] = None,
	  /** The user-assigned display name for the `WebApp`. */
		displayName: Option[String] = None,
	  /** Output only. Immutable. A user-assigned unique identifier of the parent FirebaseProject for the `WebApp`. */
		projectId: Option[String] = None,
	  /** The URLs where the `WebApp` is hosted. */
		appUrls: Option[List[String]] = None,
	  /** Output only. Immutable. A unique, Firebase-assigned identifier for the `WebApp`. This identifier is only used to populate the `namespace` value for the `WebApp`. For most use cases, use `appId` to identify or reference the App. The `webId` value is only unique within a `FirebaseProject` and its associated Apps. */
		webId: Option[String] = None,
	  /** The globally unique, Google-assigned identifier (UID) for the Firebase API key associated with the `WebApp`. Be aware that this value is the UID of the API key, _not_ the [`keyString`](https://cloud.google.com/api-keys/docs/reference/rest/v2/projects.locations.keys#Key.FIELDS.key_string) of the API key. The `keyString` is the value that can be found in the App's [configuration artifact](../../rest/v1beta1/projects.webApps/getConfig). If `api_key_id` is not set in requests to [`webApps.Create`](../../rest/v1beta1/projects.webApps/create), then Firebase automatically associates an `api_key_id` with the `WebApp`. This auto-associated key may be an existing valid key or, if no valid key exists, a new one will be provisioned. In patch requests, `api_key_id` cannot be set to an empty value, and the new UID must have no restrictions or only have restrictions that are valid for the associated `WebApp`. We recommend using the [Google Cloud Console](https://console.cloud.google.com/apis/credentials) to manage API keys. */
		apiKeyId: Option[String] = None,
	  /** Output only. The lifecycle state of the App. */
		state: Option[Schema.WebApp.StateEnum] = None,
	  /** Output only. If the App has been removed from the Project, this is the timestamp of when the App is considered expired and will be permanently deleted. After this time, the App cannot be undeleted (that is, restored to the Project). This value is only provided if the App is in the `DELETED` state. */
		expireTime: Option[String] = None,
	  /** This checksum is computed by the server based on the value of other fields, and it may be sent with update requests to ensure the client has an up-to-date value before proceeding. Learn more about `etag` in Google's [AIP-154 standard](https://google.aip.dev/154#declarative-friendly-resources). This etag is strongly validated. */
		etag: Option[String] = None
	)
	
	case class ListWebAppsResponse(
	  /** List of each `WebApp` associated with the specified `FirebaseProject`. */
		apps: Option[List[Schema.WebApp]] = None,
	  /** If the result list is too large to fit in a single response, then a token is returned. If the string is empty, then this response is the last page of results. This token can be used in a subsequent call to `ListWebApps` to find the next group of Apps. Page tokens are short-lived and should not be persisted. */
		nextPageToken: Option[String] = None
	)
	
	case class RemoveWebAppRequest(
	  /** If set to true, and the App is not found, the request will succeed but no action will be taken on the server. */
		allowMissing: Option[Boolean] = None,
	  /** If set to true, the request is only validated. The App will _not_ be removed. */
		validateOnly: Option[Boolean] = None,
	  /** Checksum provided in the WebApp resource. If provided, this checksum ensures that the client has an up-to-date value before proceeding. */
		etag: Option[String] = None,
	  /** Determines whether to _immediately_ delete the WebApp. If set to true, the App is immediately deleted from the Project and cannot be undeleted (that is, restored to the Project). If not set, defaults to false, which means the App will be set to expire in 30 days. Within the 30 days, the App may be restored to the Project using UndeleteWebApp */
		immediate: Option[Boolean] = None
	)
	
	case class UndeleteWebAppRequest(
	  /** If set to true, the request is only validated. The App will _not_ be undeleted. */
		validateOnly: Option[Boolean] = None,
	  /** Checksum provided in the WebApp resource. If provided, this checksum ensures that the client has an up-to-date value before proceeding. */
		etag: Option[String] = None
	)
	
	case class WebAppConfig(
	  /** Immutable. A user-assigned unique identifier for the `FirebaseProject`. */
		projectId: Option[String] = None,
	  /** Immutable. The globally unique, Firebase-assigned identifier for the `WebApp`. */
		appId: Option[String] = None,
	  /** &#42;&#42;DEPRECATED.&#42;&#42; _Instead, find the URL of the default Realtime Database instance using the [list endpoint](https://firebase.google.com/docs/reference/rest/database/database-management/rest/v1beta/projects.locations.instances/list) within the Firebase Realtime Database REST API. If the default instance for the Project has not yet been provisioned, the return might not contain a default instance. Note that the config that's generated for the Firebase console or the Firebase CLI uses the Realtime Database endpoint to populate this value for that config._ The URL of the default Firebase Realtime Database instance. */
		databaseURL: Option[String] = None,
	  /** &#42;&#42;DEPRECATED.&#42;&#42; _Instead, find the name of the default Cloud Storage for Firebase bucket using the [list endpoint](https://firebase.google.com/docs/reference/rest/storage/rest/v1beta/projects.buckets/list) within the Cloud Storage for Firebase REST API. If the default bucket for the Project has not yet been provisioned, the return might not contain a default bucket. Note that the config that's generated for the Firebase console or the Firebase CLI uses the Cloud Storage for Firebase endpoint to populate this value for that config._ The name of the default Cloud Storage for Firebase bucket. */
		storageBucket: Option[String] = None,
	  /** &#42;&#42;DEPRECATED.&#42;&#42; _Instead, use product-specific REST APIs to find the location of each resource in a Project. This field may not be populated, especially for newly provisioned projects after October 30, 2024._ The ID of the Project's ["location for default Google Cloud resources"](https://firebase.google.com/docs/projects/locations#default-cloud-location), which are resources associated with Google App Engine. The location is one of the available [App Engine locations](https://cloud.google.com/about/locations#region). This field is omitted if the location for default Google Cloud resources has not been set. */
		locationId: Option[String] = None,
	  /** The [`keyString`](https://cloud.google.com/api-keys/docs/reference/rest/v2/projects.locations.keys#Key.FIELDS.key_string) of the API key associated with the `WebApp`. Note that this value is _not_ the [`apiKeyId`](../projects.webApps#WebApp.FIELDS.api_key_id) (the UID) of the API key associated with the `WebApp`. */
		apiKey: Option[String] = None,
	  /** The domain Firebase Auth configures for OAuth redirects, in the format: PROJECT_ID.firebaseapp.com */
		authDomain: Option[String] = None,
	  /** The sender ID for use with Firebase Cloud Messaging. */
		messagingSenderId: Option[String] = None,
	  /** The unique Google-assigned identifier of the Google Analytics web stream associated with the `WebApp`. Firebase SDKs use this ID to interact with Google Analytics APIs. This field is only present if the `WebApp` is linked to a web stream in a Google Analytics App + Web property. Learn more about this ID and Google Analytics web streams in the [Analytics documentation](https://support.google.com/analytics/answer/9304153). To generate a `measurementId` and link the `WebApp` with a Google Analytics web stream, call [`AddGoogleAnalytics`](../../v1beta1/projects/addGoogleAnalytics). For apps using the Firebase JavaScript SDK v7.20.0 and later, Firebase dynamically fetches the `measurementId` when your app initializes Analytics. Having this ID in your config object is optional, but it does serve as a fallback in the rare case that the dynamic fetch fails. */
		measurementId: Option[String] = None,
	  /** Output only. Immutable. The globally unique, Google-assigned canonical identifier for the Project. Use this identifier when configuring integrations and/or making API calls to Google Cloud or third-party services. */
		projectNumber: Option[String] = None,
	  /** Optional. Duplicate field for the URL of the default Realtime Database instances (if the default instance has been provisioned). If the request asks for the V2 config format, this field will be populated instead of `realtime_database_instance_uri`. */
		realtimeDatabaseUrl: Option[String] = None,
	  /** Version of the config specification. */
		version: Option[String] = None
	)
	
	case class StatusProto(
	  /** Numeric code drawn from the space specified below. Often, this is the canonical error space, and code is drawn from google3/util/task/codes.proto */
		code: Option[Int] = None,
	  /** The following are usually only present when code != 0 Space to which this status belongs */
		space: Option[String] = None,
	  /** Detail message */
		message: Option[String] = None,
	  /** The canonical error code (see codes.proto) that most closely corresponds to this status. This may be missing, and in the common case of the generic space, it definitely will be. */
		canonicalCode: Option[Int] = None,
	  /** message_set associates an arbitrary proto message with the status. */
		messageSet: Option[Schema.MessageSet] = None
	)
	
	case class MessageSet(
	
	)
	
	case class ProductMetadata(
	  /** List of warnings related to the associated operation. */
		warningMessages: Option[List[String]] = None
	)
	
	case class OperationMetadata(
	
	)
}
