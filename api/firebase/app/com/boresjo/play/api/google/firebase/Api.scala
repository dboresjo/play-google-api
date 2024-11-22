package com.boresjo.play.api.google.firebase

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	val scopes = Seq(
		"""https://www.googleapis.com/auth/cloud-platform""" /* See, edit, configure, and delete your Google Cloud data and see the email address for your Google Account. */,
		"""https://www.googleapis.com/auth/cloud-platform.read-only""" /* View your data across Google Cloud services and see the email address of your Google Account */,
		"""https://www.googleapis.com/auth/firebase""" /* View and administer all your Firebase data and settings */,
		"""https://www.googleapis.com/auth/firebase.readonly""" /* View all your Firebase data and settings */
	)

	private val BASE_URL = "https://firebase.googleapis.com/"

	object operations {
		/** Gets the latest state of a long-running operation. Clients can use this method to poll the operation result at intervals as recommended by the API service. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/firebase""", """https://www.googleapis.com/auth/firebase.readonly""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Operation])
		}
		object get {
			def apply(operationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1beta1/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
			given Conversion[get, Future[Schema.Operation]] = (fun: get) => fun.apply()
		}
	}
	object projects {
		/** Lists all available Apps for the specified FirebaseProject. This is a convenience method. Typically, interaction with an App should be done using the platform-specific service, but some tool use-cases require a summary of all known Apps (such as for App selector interfaces). */
		class searchApps(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.SearchFirebaseAppsResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/firebase""", """https://www.googleapis.com/auth/firebase.readonly""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.SearchFirebaseAppsResponse])
		}
		object searchApps {
			def apply(projectsId :PlayApi, parent: String, pageToken: String, pageSize: Int, filter: String, showDeleted: Boolean)(using signer: RequestSigner, ec: ExecutionContext): searchApps = new searchApps(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}:searchApps").addQueryStringParameters("parent" -> parent.toString, "pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString, "filter" -> filter.toString, "showDeleted" -> showDeleted.toString))
			given Conversion[searchApps, Future[Schema.SearchFirebaseAppsResponse]] = (fun: searchApps) => fun.apply()
		}
		/** Adds Firebase resources and enables Firebase services in the specified existing [Google Cloud `Project`](https://cloud.google.com/resource-manager/reference/rest/v1/projects). Since a FirebaseProject is actually also a Google Cloud `Project`, a `FirebaseProject` has the same underlying Google Cloud identifiers (`projectNumber` and `projectId`). This allows for easy interop with Google APIs. The result of this call is an [`Operation`](../../v1beta1/operations). Poll the `Operation` to track the provisioning process by calling GetOperation until [`done`](../../v1beta1/operations#Operation.FIELDS.done) is `true`. When `done` is `true`, the `Operation` has either succeeded or failed. If the `Operation` succeeded, its [`response`](../../v1beta1/operations#Operation.FIELDS.response) is set to a FirebaseProject; if the `Operation` failed, its [`error`](../../v1beta1/operations#Operation.FIELDS.error) is set to a google.rpc.Status. The `Operation` is automatically deleted after completion, so there is no need to call DeleteOperation. This method does not modify any billing account information on the underlying Google Cloud `Project`. To call `AddFirebase`, a project member or service account must have the following permissions (the IAM roles of Editor and Owner contain these permissions): `firebase.projects.update`, `resourcemanager.projects.get`, `serviceusage.services.enable`, and `serviceusage.services.get`. */
		class addFirebase(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/firebase""")
			/** Perform the request */
			def withAddFirebaseRequest(body: Schema.AddFirebaseRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
		}
		object addFirebase {
			def apply(projectsId :PlayApi, project: String)(using signer: RequestSigner, ec: ExecutionContext): addFirebase = new addFirebase(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}:addFirebase").addQueryStringParameters("project" -> project.toString))
		}
		/** Unlinks the specified FirebaseProject from its Google Analytics account. This call removes the association of the specified `FirebaseProject` with its current Google Analytics property. However, this call does not delete the Google Analytics resources, such as the Google Analytics property or any data streams. These resources may be re-associated later to the `FirebaseProject` by calling [`AddGoogleAnalytics`](../../v1beta1/projects/addGoogleAnalytics) and specifying the same `analyticsPropertyId`. For Android Apps and iOS Apps, this call re-links data streams with their corresponding apps. However, for Web Apps, this call provisions a &#42;new&#42; data stream for each Web App. To call `RemoveAnalytics`, a project member must be an Owner for the `FirebaseProject`. */
		class removeAnalytics(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/firebase""")
			/** Perform the request */
			def withRemoveAnalyticsRequest(body: Schema.RemoveAnalyticsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Empty])
		}
		object removeAnalytics {
			def apply(projectsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): removeAnalytics = new removeAnalytics(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}:removeAnalytics").addQueryStringParameters("parent" -> parent.toString))
		}
		/** Updates the attributes of the specified FirebaseProject. All [query parameters](#query-parameters) are required. */
		class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/firebase""")
			/** Perform the request */
			def withFirebaseProject(body: Schema.FirebaseProject) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.FirebaseProject])
		}
		object patch {
			def apply(projectsId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
		}
		/** Gets the configuration artifact associated with the specified FirebaseProject, which can be used by servers to simplify initialization. Typically, this configuration is used with the Firebase Admin SDK [initializeApp](https://firebase.google.com/docs/admin/setup#initialize_the_sdk) command. */
		class getAdminSdkConfig(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.AdminSdkConfig]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/firebase""", """https://www.googleapis.com/auth/firebase.readonly""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.AdminSdkConfig])
		}
		object getAdminSdkConfig {
			def apply(projectsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): getAdminSdkConfig = new getAdminSdkConfig(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/adminSdkConfig").addQueryStringParameters("name" -> name.toString))
			given Conversion[getAdminSdkConfig, Future[Schema.AdminSdkConfig]] = (fun: getAdminSdkConfig) => fun.apply()
		}
		/** Lists each FirebaseProject accessible to the caller. The elements are returned in no particular order, but they will be a consistent view of the Projects when additional requests are made with a `pageToken`. This method is eventually consistent with Project mutations, which means newly provisioned Projects and recent modifications to existing Projects might not be reflected in the set of Projects. The list will include only ACTIVE Projects. Use GetFirebaseProject for consistent reads as well as for additional Project details. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListFirebaseProjectsResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/firebase""", """https://www.googleapis.com/auth/firebase.readonly""")
			/** Optional. Controls whether Projects in the DELETED state should be returned in the response. If not specified, only `ACTIVE` Projects will be returned. */
			def withShowDeleted(showDeleted: Boolean) = new list(req.addQueryStringParameters("showDeleted" -> showDeleted.toString))
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListFirebaseProjectsResponse])
		}
		object list {
			def apply(pageToken: String, pageSize: Int)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1beta1/projects").addQueryStringParameters("pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString))
			given Conversion[list, Future[Schema.ListFirebaseProjectsResponse]] = (fun: list) => fun.apply()
		}
		/** Links the specified FirebaseProject with an existing [Google Analytics account](http://www.google.com/analytics/). Using this call, you can either: - Specify an `analyticsAccountId` to provision a new Google Analytics property within the specified account and associate the new property with the `FirebaseProject`. - Specify an existing `analyticsPropertyId` to associate the property with the `FirebaseProject`. Note that when you call `AddGoogleAnalytics`: 1. The first check determines if any existing data streams in the Google Analytics property correspond to any existing Firebase Apps in the `FirebaseProject` (based on the `packageName` or `bundleId` associated with the data stream). Then, as applicable, the data streams and apps are linked. Note that this auto-linking only applies to `AndroidApps` and `IosApps`. 2. If no corresponding data streams are found for the Firebase Apps, new data streams are provisioned in the Google Analytics property for each of the Firebase Apps. Note that a new data stream is always provisioned for a Web App even if it was previously associated with a data stream in the Analytics property. Learn more about the hierarchy and structure of Google Analytics accounts in the [Analytics documentation](https://support.google.com/analytics/answer/9303323). The result of this call is an [`Operation`](../../v1beta1/operations). Poll the `Operation` to track the provisioning process by calling GetOperation until [`done`](../../v1beta1/operations#Operation.FIELDS.done) is `true`. When `done` is `true`, the `Operation` has either succeeded or failed. If the `Operation` succeeded, its [`response`](../../v1beta1/operations#Operation.FIELDS.response) is set to an AnalyticsDetails; if the `Operation` failed, its [`error`](../../v1beta1/operations#Operation.FIELDS.error) is set to a google.rpc.Status. To call `AddGoogleAnalytics`, a project member must be an Owner for the existing `FirebaseProject` and have the [`Edit` permission](https://support.google.com/analytics/answer/2884495) for the Google Analytics account. If the `FirebaseProject` already has Google Analytics enabled, and you call `AddGoogleAnalytics` using an `analyticsPropertyId` that's different from the currently associated property, then the call will fail. Analytics may have already been enabled in the Firebase console or by specifying `timeZone` and `regionCode` in the call to [`AddFirebase`](../../v1beta1/projects/addFirebase). */
		class addGoogleAnalytics(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/firebase""")
			/** Perform the request */
			def withAddGoogleAnalyticsRequest(body: Schema.AddGoogleAnalyticsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
		}
		object addGoogleAnalytics {
			def apply(projectsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): addGoogleAnalytics = new addGoogleAnalytics(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}:addGoogleAnalytics").addQueryStringParameters("parent" -> parent.toString))
		}
		/** Gets the Google Analytics details currently associated with the specified FirebaseProject. If the `FirebaseProject` is not yet linked to Google Analytics, then the response to `GetAnalyticsDetails` is `NOT_FOUND`. */
		class getAnalyticsDetails(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.AnalyticsDetails]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/firebase""", """https://www.googleapis.com/auth/firebase.readonly""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.AnalyticsDetails])
		}
		object getAnalyticsDetails {
			def apply(projectsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): getAnalyticsDetails = new getAnalyticsDetails(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/analyticsDetails").addQueryStringParameters("name" -> name.toString))
			given Conversion[getAnalyticsDetails, Future[Schema.AnalyticsDetails]] = (fun: getAnalyticsDetails) => fun.apply()
		}
		/** Gets the specified FirebaseProject. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.FirebaseProject]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/firebase""", """https://www.googleapis.com/auth/firebase.readonly""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.FirebaseProject])
		}
		object get {
			def apply(projectsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}").addQueryStringParameters("name" -> name.toString))
			given Conversion[get, Future[Schema.FirebaseProject]] = (fun: get) => fun.apply()
		}
		object webApps {
			/** Restores the specified WebApp to the FirebaseProject. */
			class undelete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/firebase""")
				/** Perform the request */
				def withUndeleteWebAppRequest(body: Schema.UndeleteWebAppRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
			}
			object undelete {
				def apply(projectsId :PlayApi, webAppsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): undelete = new undelete(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/webApps/${webAppsId}:undelete").addQueryStringParameters("name" -> name.toString))
			}
			/** Requests the creation of a new WebApp in the specified FirebaseProject. The result of this call is an `Operation` which can be used to track the provisioning process. The `Operation` is automatically deleted after completion, so there is no need to call `DeleteOperation`. */
			class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/firebase""")
				/** Perform the request */
				def withWebApp(body: Schema.WebApp) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
			}
			object create {
				def apply(projectsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/webApps").addQueryStringParameters("parent" -> parent.toString))
			}
			/** Removes the specified WebApp from the FirebaseProject. */
			class remove(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/firebase""")
				/** Perform the request */
				def withRemoveWebAppRequest(body: Schema.RemoveWebAppRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
			}
			object remove {
				def apply(projectsId :PlayApi, webAppsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): remove = new remove(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/webApps/${webAppsId}:remove").addQueryStringParameters("name" -> name.toString))
			}
			/** Gets the specified WebApp. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.WebApp]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/firebase""", """https://www.googleapis.com/auth/firebase.readonly""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.WebApp])
			}
			object get {
				def apply(projectsId :PlayApi, webAppsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/webApps/${webAppsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.WebApp]] = (fun: get) => fun.apply()
			}
			/** Updates the attributes of the specified WebApp. */
			class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/firebase""")
				/** Perform the request */
				def withWebApp(body: Schema.WebApp) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.WebApp])
			}
			object patch {
				def apply(projectsId :PlayApi, webAppsId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/webApps/${webAppsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
			}
			/** Gets the configuration artifact associated with the specified WebApp. */
			class getConfig(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.WebAppConfig]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/firebase""", """https://www.googleapis.com/auth/firebase.readonly""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.WebAppConfig])
			}
			object getConfig {
				def apply(projectsId :PlayApi, webAppsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): getConfig = new getConfig(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/webApps/${webAppsId}/config").addQueryStringParameters("name" -> name.toString))
				given Conversion[getConfig, Future[Schema.WebAppConfig]] = (fun: getConfig) => fun.apply()
			}
			/** Lists each WebApp associated with the specified FirebaseProject. The elements are returned in no particular order, but will be a consistent view of the Apps when additional requests are made with a `pageToken`. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListWebAppsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/firebase""", """https://www.googleapis.com/auth/firebase.readonly""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListWebAppsResponse])
			}
			object list {
				def apply(projectsId :PlayApi, parent: String, pageToken: String, pageSize: Int, showDeleted: Boolean)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/webApps").addQueryStringParameters("parent" -> parent.toString, "pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString, "showDeleted" -> showDeleted.toString))
				given Conversion[list, Future[Schema.ListWebAppsResponse]] = (fun: list) => fun.apply()
			}
		}
		object androidApps {
			/** Restores the specified AndroidApp to the FirebaseProject. */
			class undelete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/firebase""")
				/** Perform the request */
				def withUndeleteAndroidAppRequest(body: Schema.UndeleteAndroidAppRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
			}
			object undelete {
				def apply(projectsId :PlayApi, androidAppsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): undelete = new undelete(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/androidApps/${androidAppsId}:undelete").addQueryStringParameters("name" -> name.toString))
			}
			/** Requests the creation of a new AndroidApp in the specified FirebaseProject. The result of this call is an `Operation` which can be used to track the provisioning process. The `Operation` is automatically deleted after completion, so there is no need to call `DeleteOperation`. */
			class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/firebase""")
				/** Perform the request */
				def withAndroidApp(body: Schema.AndroidApp) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
			}
			object create {
				def apply(projectsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/androidApps").addQueryStringParameters("parent" -> parent.toString))
			}
			/** Removes the specified AndroidApp from the FirebaseProject. */
			class remove(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/firebase""")
				/** Perform the request */
				def withRemoveAndroidAppRequest(body: Schema.RemoveAndroidAppRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
			}
			object remove {
				def apply(projectsId :PlayApi, androidAppsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): remove = new remove(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/androidApps/${androidAppsId}:remove").addQueryStringParameters("name" -> name.toString))
			}
			/** Gets the specified AndroidApp. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.AndroidApp]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/firebase""", """https://www.googleapis.com/auth/firebase.readonly""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.AndroidApp])
			}
			object get {
				def apply(projectsId :PlayApi, androidAppsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/androidApps/${androidAppsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.AndroidApp]] = (fun: get) => fun.apply()
			}
			/** Updates the attributes of the specified AndroidApp. */
			class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/firebase""")
				/** Perform the request */
				def withAndroidApp(body: Schema.AndroidApp) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.AndroidApp])
			}
			object patch {
				def apply(projectsId :PlayApi, androidAppsId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/androidApps/${androidAppsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
			}
			/** Gets the configuration artifact associated with the specified AndroidApp. */
			class getConfig(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.AndroidAppConfig]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/firebase""", """https://www.googleapis.com/auth/firebase.readonly""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.AndroidAppConfig])
			}
			object getConfig {
				def apply(projectsId :PlayApi, androidAppsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): getConfig = new getConfig(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/androidApps/${androidAppsId}/config").addQueryStringParameters("name" -> name.toString))
				given Conversion[getConfig, Future[Schema.AndroidAppConfig]] = (fun: getConfig) => fun.apply()
			}
			/** Lists each AndroidApp associated with the specified FirebaseProject. The elements are returned in no particular order, but will be a consistent view of the Apps when additional requests are made with a `pageToken`. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListAndroidAppsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/firebase""", """https://www.googleapis.com/auth/firebase.readonly""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListAndroidAppsResponse])
			}
			object list {
				def apply(projectsId :PlayApi, parent: String, pageToken: String, pageSize: Int, showDeleted: Boolean)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/androidApps").addQueryStringParameters("parent" -> parent.toString, "pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString, "showDeleted" -> showDeleted.toString))
				given Conversion[list, Future[Schema.ListAndroidAppsResponse]] = (fun: list) => fun.apply()
			}
			object sha {
				/** Lists the SHA-1 and SHA-256 certificates for the specified AndroidApp. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListShaCertificatesResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/firebase""", """https://www.googleapis.com/auth/firebase.readonly""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListShaCertificatesResponse])
				}
				object list {
					def apply(projectsId :PlayApi, androidAppsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/androidApps/${androidAppsId}/sha").addQueryStringParameters("parent" -> parent.toString))
					given Conversion[list, Future[Schema.ListShaCertificatesResponse]] = (fun: list) => fun.apply()
				}
				/** Adds a ShaCertificate to the specified AndroidApp. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/firebase""")
					/** Perform the request */
					def withShaCertificate(body: Schema.ShaCertificate) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.ShaCertificate])
				}
				object create {
					def apply(projectsId :PlayApi, androidAppsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/androidApps/${androidAppsId}/sha").addQueryStringParameters("parent" -> parent.toString))
				}
				/** Removes a ShaCertificate from the specified AndroidApp. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/firebase""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
				}
				object delete {
					def apply(projectsId :PlayApi, androidAppsId :PlayApi, shaId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/androidApps/${androidAppsId}/sha/${shaId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
				}
			}
		}
		object availableLocations {
			/** &#42;&#42;DECOMMISSIONED.&#42;&#42; &#42;&#42;If called, this endpoint will return a 404 error.&#42;&#42; _Instead, use the applicable resource-specific REST API (or associated documentation, as needed) to determine valid locations for each resource used in your Project._ Lists the valid ["locations for default Google Cloud resources"](https://firebase.google.com/docs/projects/locations#default-cloud-location) for the specified Project (including a FirebaseProject). One of these locations can be selected as the Project's location for default Google Cloud resources, which is the geographical location where the Project's resources associated with Google App Engine (such as the default Cloud Firestore instance) will be provisioned by default. However, if the location for default Google Cloud resources has already been set for the Project, then this setting cannot be changed. This call checks for any possible [location restrictions](https://cloud.google.com/resource-manager/docs/organization-policy/defining-locations) for the specified Project and, thus, might return a subset of all possible locations. To list all locations (regardless of any restrictions), call the endpoint without specifying a unique project identifier (that is, `/v1beta1/{parent=projects/-}/listAvailableLocations`). To call `ListAvailableLocations` with a specified project, a member must be at minimum a Viewer of the Project. Calls without a specified project do not require any specific project permissions. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListAvailableLocationsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/firebase""", """https://www.googleapis.com/auth/firebase.readonly""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListAvailableLocationsResponse])
			}
			object list {
				def apply(projectsId :PlayApi, parent: String, pageToken: String, pageSize: Int)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/availableLocations").addQueryStringParameters("parent" -> parent.toString, "pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString))
				given Conversion[list, Future[Schema.ListAvailableLocationsResponse]] = (fun: list) => fun.apply()
			}
		}
		object defaultLocation {
			/** &#42;&#42;DECOMMISSIONED.&#42;&#42; &#42;&#42;If called, this endpoint will return a 404 error.&#42;&#42; _Instead, use the applicable resource-specific REST API to set the location for each resource used in your Project._ Sets the ["location for default Google Cloud resources"](https://firebase.google.com/docs/projects/locations#default-cloud-location) for the specified FirebaseProject. This method creates a Google App Engine application with a [default Cloud Storage bucket](https://cloud.google.com/appengine/docs/standard/python/googlecloudstorageclient/setting-up-cloud-storage#activating_a_cloud_storage_bucket), located in the specified [`locationId`](#body.request_body.FIELDS.location_id). This location must be one of the available [App Engine locations](https://cloud.google.com/about/locations#region). After the location for default Google Cloud resources is finalized, or if it was already set, it cannot be changed. The location for default Google Cloud resources for the specified `FirebaseProject` might already be set because either the underlying Google Cloud `Project` already has an App Engine application or `FinalizeDefaultLocation` was previously called with a specified `locationId`. The result of this call is an [`Operation`](../../v1beta1/operations), which can be used to track the provisioning process. The [`response`](../../v1beta1/operations#Operation.FIELDS.response) type of the `Operation` is google.protobuf.Empty. The `Operation` can be polled by its `name` using GetOperation until `done` is true. When `done` is true, the `Operation` has either succeeded or failed. If the `Operation` has succeeded, its [`response`](../../v1beta1/operations#Operation.FIELDS.response) will be set to a google.protobuf.Empty; if the `Operation` has failed, its `error` will be set to a google.rpc.Status. The `Operation` is automatically deleted after completion, so there is no need to call DeleteOperation. All fields listed in the [request body](#request-body) are required. To call `FinalizeDefaultLocation`, a member must be an Owner of the Project. */
			class _finalize(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/firebase""")
				/** Perform the request */
				def withFinalizeDefaultLocationRequest(body: Schema.FinalizeDefaultLocationRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
			}
			object _finalize {
				def apply(projectsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): _finalize = new _finalize(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/defaultLocation:finalize").addQueryStringParameters("parent" -> parent.toString))
			}
		}
		object iosApps {
			/** Restores the specified IosApp to the FirebaseProject. */
			class undelete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/firebase""")
				/** Perform the request */
				def withUndeleteIosAppRequest(body: Schema.UndeleteIosAppRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
			}
			object undelete {
				def apply(projectsId :PlayApi, iosAppsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): undelete = new undelete(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/iosApps/${iosAppsId}:undelete").addQueryStringParameters("name" -> name.toString))
			}
			/** Requests the creation of a new IosApp in the specified FirebaseProject. The result of this call is an `Operation` which can be used to track the provisioning process. The `Operation` is automatically deleted after completion, so there is no need to call `DeleteOperation`. */
			class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/firebase""")
				/** Perform the request */
				def withIosApp(body: Schema.IosApp) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
			}
			object create {
				def apply(projectsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/iosApps").addQueryStringParameters("parent" -> parent.toString))
			}
			/** Removes the specified IosApp from the FirebaseProject. */
			class remove(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/firebase""")
				/** Perform the request */
				def withRemoveIosAppRequest(body: Schema.RemoveIosAppRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
			}
			object remove {
				def apply(projectsId :PlayApi, iosAppsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): remove = new remove(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/iosApps/${iosAppsId}:remove").addQueryStringParameters("name" -> name.toString))
			}
			/** Gets the specified IosApp. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.IosApp]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/firebase""", """https://www.googleapis.com/auth/firebase.readonly""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.IosApp])
			}
			object get {
				def apply(projectsId :PlayApi, iosAppsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/iosApps/${iosAppsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.IosApp]] = (fun: get) => fun.apply()
			}
			/** Updates the attributes of the specified IosApp. */
			class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/firebase""")
				/** Perform the request */
				def withIosApp(body: Schema.IosApp) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.IosApp])
			}
			object patch {
				def apply(projectsId :PlayApi, iosAppsId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/iosApps/${iosAppsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
			}
			/** Gets the configuration artifact associated with the specified IosApp. */
			class getConfig(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.IosAppConfig]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/firebase""", """https://www.googleapis.com/auth/firebase.readonly""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.IosAppConfig])
			}
			object getConfig {
				def apply(projectsId :PlayApi, iosAppsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): getConfig = new getConfig(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/iosApps/${iosAppsId}/config").addQueryStringParameters("name" -> name.toString))
				given Conversion[getConfig, Future[Schema.IosAppConfig]] = (fun: getConfig) => fun.apply()
			}
			/** Lists each IosApp associated with the specified FirebaseProject. The elements are returned in no particular order, but will be a consistent view of the Apps when additional requests are made with a `pageToken`. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListIosAppsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/firebase""", """https://www.googleapis.com/auth/firebase.readonly""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListIosAppsResponse])
			}
			object list {
				def apply(projectsId :PlayApi, parent: String, pageToken: String, pageSize: Int, showDeleted: Boolean)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/iosApps").addQueryStringParameters("parent" -> parent.toString, "pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString, "showDeleted" -> showDeleted.toString))
				given Conversion[list, Future[Schema.ListIosAppsResponse]] = (fun: list) => fun.apply()
			}
		}
	}
	object availableProjects {
		/** Lists each [Google Cloud `Project`](https://cloud.google.com/resource-manager/reference/rest/v1/projects) that can have Firebase resources added and Firebase services enabled. A Project will only be listed if: - The caller has sufficient [Google IAM](https://cloud.google.com/iam) permissions to call AddFirebase. - The Project is not already a FirebaseProject. - The Project is not in an Organization which has policies that prevent Firebase resources from being added.  */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListAvailableProjectsResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/firebase""", """https://www.googleapis.com/auth/firebase.readonly""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListAvailableProjectsResponse])
		}
		object list {
			def apply(pageToken: String, pageSize: Int)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1beta1/availableProjects").addQueryStringParameters("pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString))
			given Conversion[list, Future[Schema.ListAvailableProjectsResponse]] = (fun: list) => fun.apply()
		}
	}
}
