package com.boresjo.play.api.google.androidmanagement

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
		"""https://www.googleapis.com/auth/androidmanagement""" /* Manage Android devices and apps for your customers */
	)

	private val BASE_URL = "https://androidmanagement.googleapis.com/"

	object signupUrls {
		/** Creates an enterprise signup URL. */
		class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.SignupUrl]) {
			val scopes = Seq("""https://www.googleapis.com/auth/androidmanagement""")
			/** Optional. Email address used to prefill the admin field of the enterprise signup form. This value is a hint only and can be altered by the user. */
			def withAdminEmail(adminEmail: String) = new create(req.addQueryStringParameters("adminEmail" -> adminEmail.toString))
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("POST")).map(_.json.as[Schema.SignupUrl])
		}
		object create {
			def apply(projectId: String, callbackUrl: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/signupUrls").addQueryStringParameters("projectId" -> projectId.toString, "callbackUrl" -> callbackUrl.toString))
			given Conversion[create, Future[Schema.SignupUrl]] = (fun: create) => fun.apply()
		}
	}
	object enterprises {
		/** Creates an enterprise. This is the last step in the enterprise signup flow. See also: SigninDetail */
		class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/androidmanagement""")
			/** Perform the request */
			def withEnterprise(body: Schema.Enterprise) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Enterprise])
		}
		object create {
			def apply(projectId: String, signupUrlName: String, enterpriseToken: String, agreementAccepted: Boolean)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/enterprises").addQueryStringParameters("projectId" -> projectId.toString, "signupUrlName" -> signupUrlName.toString, "enterpriseToken" -> enterpriseToken.toString, "agreementAccepted" -> agreementAccepted.toString))
		}
		/** Permanently deletes an enterprise and all accounts and data associated with it. Warning: this will result in a cascaded deletion of all AM API devices associated with the deleted enterprise. Only available for EMM-managed enterprises. */
		class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
			val scopes = Seq("""https://www.googleapis.com/auth/androidmanagement""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
		}
		object delete {
			def apply(enterprisesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/enterprises/${enterprisesId}").addQueryStringParameters("name" -> name.toString))
			given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
		}
		/** Gets an enterprise. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Enterprise]) {
			val scopes = Seq("""https://www.googleapis.com/auth/androidmanagement""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Enterprise])
		}
		object get {
			def apply(enterprisesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/enterprises/${enterprisesId}").addQueryStringParameters("name" -> name.toString))
			given Conversion[get, Future[Schema.Enterprise]] = (fun: get) => fun.apply()
		}
		/** Updates an enterprise. See also: SigninDetail */
		class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/androidmanagement""")
			/** Perform the request */
			def withEnterprise(body: Schema.Enterprise) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Enterprise])
		}
		object patch {
			def apply(enterprisesId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/enterprises/${enterprisesId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
		}
		/** Lists EMM-managed enterprises. Only BASIC fields are returned. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListEnterprisesResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/androidmanagement""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListEnterprisesResponse])
		}
		object list {
			def apply(projectId: String, pageSize: Int, pageToken: String, view: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/enterprises").addQueryStringParameters("projectId" -> projectId.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "view" -> view.toString))
			given Conversion[list, Future[Schema.ListEnterprisesResponse]] = (fun: list) => fun.apply()
		}
		object webApps {
			/** Creates a web app. */
			class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/androidmanagement""")
				/** Perform the request */
				def withWebApp(body: Schema.WebApp) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.WebApp])
			}
			object create {
				def apply(enterprisesId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/enterprises/${enterprisesId}/webApps").addQueryStringParameters("parent" -> parent.toString))
			}
			/** Deletes a web app. */
			class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
				val scopes = Seq("""https://www.googleapis.com/auth/androidmanagement""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
			}
			object delete {
				def apply(enterprisesId :PlayApi, webAppsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/enterprises/${enterprisesId}/webApps/${webAppsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
			}
			/** Gets a web app. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.WebApp]) {
				val scopes = Seq("""https://www.googleapis.com/auth/androidmanagement""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.WebApp])
			}
			object get {
				def apply(enterprisesId :PlayApi, webAppsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/enterprises/${enterprisesId}/webApps/${webAppsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.WebApp]] = (fun: get) => fun.apply()
			}
			/** Updates a web app. */
			class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/androidmanagement""")
				/** Perform the request */
				def withWebApp(body: Schema.WebApp) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.WebApp])
			}
			object patch {
				def apply(enterprisesId :PlayApi, webAppsId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/enterprises/${enterprisesId}/webApps/${webAppsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
			}
			/** Lists web apps for a given enterprise. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListWebAppsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/androidmanagement""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListWebAppsResponse])
			}
			object list {
				def apply(enterprisesId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/enterprises/${enterprisesId}/webApps").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListWebAppsResponse]] = (fun: list) => fun.apply()
			}
		}
		object enrollmentTokens {
			/** Creates an enrollment token for a given enterprise. It's up to the caller's responsibility to manage the lifecycle of newly created tokens and deleting them when they're not intended to be used anymore. */
			class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/androidmanagement""")
				/** Perform the request */
				def withEnrollmentToken(body: Schema.EnrollmentToken) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.EnrollmentToken])
			}
			object create {
				def apply(enterprisesId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/enterprises/${enterprisesId}/enrollmentTokens").addQueryStringParameters("parent" -> parent.toString))
			}
			/** Deletes an enrollment token. This operation invalidates the token, preventing its future use. */
			class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
				val scopes = Seq("""https://www.googleapis.com/auth/androidmanagement""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
			}
			object delete {
				def apply(enterprisesId :PlayApi, enrollmentTokensId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/enterprises/${enterprisesId}/enrollmentTokens/${enrollmentTokensId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
			}
			/** Gets an active, unexpired enrollment token. A partial view of the enrollment token is returned. Only the following fields are populated: name, expirationTimestamp, allowPersonalUsage, value, qrCode. This method is meant to help manage active enrollment tokens lifecycle. For security reasons, it's recommended to delete active enrollment tokens as soon as they're not intended to be used anymore. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.EnrollmentToken]) {
				val scopes = Seq("""https://www.googleapis.com/auth/androidmanagement""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.EnrollmentToken])
			}
			object get {
				def apply(enterprisesId :PlayApi, enrollmentTokensId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/enterprises/${enterprisesId}/enrollmentTokens/${enrollmentTokensId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.EnrollmentToken]] = (fun: get) => fun.apply()
			}
			/** Lists active, unexpired enrollment tokens for a given enterprise. The list items contain only a partial view of EnrollmentToken object. Only the following fields are populated: name, expirationTimestamp, allowPersonalUsage, value, qrCode. This method is meant to help manage active enrollment tokens lifecycle. For security reasons, it's recommended to delete active enrollment tokens as soon as they're not intended to be used anymore. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListEnrollmentTokensResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/androidmanagement""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListEnrollmentTokensResponse])
			}
			object list {
				def apply(enterprisesId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/enterprises/${enterprisesId}/enrollmentTokens").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListEnrollmentTokensResponse]] = (fun: list) => fun.apply()
			}
		}
		object webTokens {
			/** Creates a web token to access an embeddable managed Google Play web UI for a given enterprise. */
			class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/androidmanagement""")
				/** Perform the request */
				def withWebToken(body: Schema.WebToken) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.WebToken])
			}
			object create {
				def apply(enterprisesId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/enterprises/${enterprisesId}/webTokens").addQueryStringParameters("parent" -> parent.toString))
			}
		}
		object devices {
			/** Deletes a device. This operation wipes the device. Deleted devices do not show up in enterprises.devices.list calls and a 404 is returned from enterprises.devices.get. */
			class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
				val scopes = Seq("""https://www.googleapis.com/auth/androidmanagement""")
				/** Optional flags that control the device wiping behavior.<br>Possible values:<br>WIPE_DATA_FLAG_UNSPECIFIED: This value is ignored.<br>PRESERVE_RESET_PROTECTION_DATA: Preserve the factory reset protection data on the device.<br>WIPE_EXTERNAL_STORAGE: Additionally wipe the device's external storage (such as SD cards). */
				def withWipeDataFlags(wipeDataFlags: String) = new delete(req.addQueryStringParameters("wipeDataFlags" -> wipeDataFlags.toString))
				/** Optional. A short message displayed to the user before wiping the work profile on personal devices. This has no effect on company owned devices. The maximum message length is 200 characters. */
				def withWipeReasonMessage(wipeReasonMessage: String) = new delete(req.addQueryStringParameters("wipeReasonMessage" -> wipeReasonMessage.toString))
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
			}
			object delete {
				def apply(enterprisesId :PlayApi, devicesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/enterprises/${enterprisesId}/devices/${devicesId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
			}
			/** Issues a command to a device. The Operation resource returned contains a Command in its metadata field. Use the get operation method to get the status of the command. */
			class issueCommand(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/androidmanagement""")
				/** Perform the request */
				def withCommand(body: Schema.Command) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
			}
			object issueCommand {
				def apply(enterprisesId :PlayApi, devicesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): issueCommand = new issueCommand(ws.url(BASE_URL + s"v1/enterprises/${enterprisesId}/devices/${devicesId}:issueCommand").addQueryStringParameters("name" -> name.toString))
			}
			/** Gets a device. Deleted devices will respond with a 404 error. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Device]) {
				val scopes = Seq("""https://www.googleapis.com/auth/androidmanagement""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Device])
			}
			object get {
				def apply(enterprisesId :PlayApi, devicesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/enterprises/${enterprisesId}/devices/${devicesId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.Device]] = (fun: get) => fun.apply()
			}
			/** Updates a device. */
			class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/androidmanagement""")
				/** Perform the request */
				def withDevice(body: Schema.Device) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Device])
			}
			object patch {
				def apply(enterprisesId :PlayApi, devicesId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/enterprises/${enterprisesId}/devices/${devicesId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
			}
			/** Lists devices for a given enterprise. Deleted devices are not returned in the response. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListDevicesResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/androidmanagement""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListDevicesResponse])
			}
			object list {
				def apply(enterprisesId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/enterprises/${enterprisesId}/devices").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListDevicesResponse]] = (fun: list) => fun.apply()
			}
			object operations {
				/** Lists operations that match the specified filter in the request. If the server doesn't support this method, it returns UNIMPLEMENTED. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListOperationsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/androidmanagement""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListOperationsResponse])
				}
				object list {
					def apply(enterprisesId :PlayApi, devicesId :PlayApi, name: String, filter: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/enterprises/${enterprisesId}/devices/${devicesId}/operations").addQueryStringParameters("name" -> name.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListOperationsResponse]] = (fun: list) => fun.apply()
				}
				/** Gets the latest state of a long-running operation. Clients can use this method to poll the operation result at intervals as recommended by the API service. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
					val scopes = Seq("""https://www.googleapis.com/auth/androidmanagement""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Operation])
				}
				object get {
					def apply(enterprisesId :PlayApi, devicesId :PlayApi, operationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/enterprises/${enterprisesId}/devices/${devicesId}/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.Operation]] = (fun: get) => fun.apply()
				}
				/** Starts asynchronous cancellation on a long-running operation. The server makes a best effort to cancel the operation, but success is not guaranteed. If the server doesn't support this method, it returns google.rpc.Code.UNIMPLEMENTED. Clients can use Operations.GetOperation or other methods to check whether the cancellation succeeded or whether the operation completed despite cancellation. On successful cancellation, the operation is not deleted; instead, it becomes an operation with an Operation.error value with a google.rpc.Status.code of 1, corresponding to Code.CANCELLED. */
				class cancel(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					val scopes = Seq("""https://www.googleapis.com/auth/androidmanagement""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("POST")).map(_.json.as[Schema.Empty])
				}
				object cancel {
					def apply(enterprisesId :PlayApi, devicesId :PlayApi, operationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): cancel = new cancel(ws.url(BASE_URL + s"v1/enterprises/${enterprisesId}/devices/${devicesId}/operations/${operationsId}:cancel").addQueryStringParameters("name" -> name.toString))
					given Conversion[cancel, Future[Schema.Empty]] = (fun: cancel) => fun.apply()
				}
			}
		}
		object migrationTokens {
			/** Creates a migration token, to migrate an existing device from being managed by the EMM's Device Policy Controller (DPC) to being managed by the Android Management API. See the guide (https://developers.google.com/android/management/dpc-migration) for more details. */
			class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/androidmanagement""")
				/** Perform the request */
				def withMigrationToken(body: Schema.MigrationToken) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.MigrationToken])
			}
			object create {
				def apply(enterprisesId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/enterprises/${enterprisesId}/migrationTokens").addQueryStringParameters("parent" -> parent.toString))
			}
			/** Gets a migration token. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.MigrationToken]) {
				val scopes = Seq("""https://www.googleapis.com/auth/androidmanagement""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.MigrationToken])
			}
			object get {
				def apply(enterprisesId :PlayApi, migrationTokensId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/enterprises/${enterprisesId}/migrationTokens/${migrationTokensId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.MigrationToken]] = (fun: get) => fun.apply()
			}
			/** Lists migration tokens. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListMigrationTokensResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/androidmanagement""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListMigrationTokensResponse])
			}
			object list {
				def apply(enterprisesId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/enterprises/${enterprisesId}/migrationTokens").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListMigrationTokensResponse]] = (fun: list) => fun.apply()
			}
		}
		object applications {
			/** Gets info about an application. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Application]) {
				val scopes = Seq("""https://www.googleapis.com/auth/androidmanagement""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Application])
			}
			object get {
				def apply(enterprisesId :PlayApi, applicationsId :PlayApi, name: String, languageCode: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/enterprises/${enterprisesId}/applications/${applicationsId}").addQueryStringParameters("name" -> name.toString, "languageCode" -> languageCode.toString))
				given Conversion[get, Future[Schema.Application]] = (fun: get) => fun.apply()
			}
		}
		object policies {
			/** Gets a policy. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Policy]) {
				val scopes = Seq("""https://www.googleapis.com/auth/androidmanagement""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Policy])
			}
			object get {
				def apply(enterprisesId :PlayApi, policiesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/enterprises/${enterprisesId}/policies/${policiesId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.Policy]] = (fun: get) => fun.apply()
			}
			/** Lists policies for a given enterprise. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListPoliciesResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/androidmanagement""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListPoliciesResponse])
			}
			object list {
				def apply(enterprisesId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/enterprises/${enterprisesId}/policies").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListPoliciesResponse]] = (fun: list) => fun.apply()
			}
			/** Updates or creates a policy. */
			class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/androidmanagement""")
				/** Perform the request */
				def withPolicy(body: Schema.Policy) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Policy])
			}
			object patch {
				def apply(enterprisesId :PlayApi, policiesId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/enterprises/${enterprisesId}/policies/${policiesId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
			}
			/** Deletes a policy. This operation is only permitted if no devices are currently referencing the policy. */
			class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
				val scopes = Seq("""https://www.googleapis.com/auth/androidmanagement""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
			}
			object delete {
				def apply(enterprisesId :PlayApi, policiesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/enterprises/${enterprisesId}/policies/${policiesId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
			}
		}
	}
	object provisioningInfo {
		/** Get the device provisioning information by the identifier provided in the sign-in url. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ProvisioningInfo]) {
			val scopes = Seq("""https://www.googleapis.com/auth/androidmanagement""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ProvisioningInfo])
		}
		object get {
			def apply(provisioningInfoId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/provisioningInfo/${provisioningInfoId}").addQueryStringParameters("name" -> name.toString))
			given Conversion[get, Future[Schema.ProvisioningInfo]] = (fun: get) => fun.apply()
		}
	}
}
