package com.boresjo.play.api.google.androidmanagement

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://androidmanagement.googleapis.com/"

	object signupUrls {
		class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.SignupUrl]) {
			/** Optional. Email address used to prefill the admin field of the enterprise signup form. This value is a hint only and can be altered by the user. */
			def withAdminEmail(adminEmail: String) = new create(req.addQueryStringParameters("adminEmail" -> adminEmail.toString))
			def apply() = req.execute("POST").map(_.json.as[Schema.SignupUrl])
		}
		object create {
			def apply(projectId: String, callbackUrl: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/signupUrls")).addQueryStringParameters("projectId" -> projectId.toString, "callbackUrl" -> callbackUrl.toString))
			given Conversion[create, Future[Schema.SignupUrl]] = (fun: create) => fun.apply()
		}
	}
	object enterprises {
		class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withEnterprise(body: Schema.Enterprise) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Enterprise])
		}
		object create {
			def apply(projectId: String, signupUrlName: String, enterpriseToken: String, agreementAccepted: Boolean)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/enterprises")).addQueryStringParameters("projectId" -> projectId.toString, "signupUrlName" -> signupUrlName.toString, "enterpriseToken" -> enterpriseToken.toString, "agreementAccepted" -> agreementAccepted.toString))
		}
		class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
			def apply() = req.execute("DELETE").map(_.json.as[Schema.Empty])
		}
		object delete {
			def apply(enterprisesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/enterprises/${enterprisesId}")).addQueryStringParameters("name" -> name.toString))
			given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
		}
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Enterprise]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.Enterprise])
		}
		object get {
			def apply(enterprisesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/enterprises/${enterprisesId}")).addQueryStringParameters("name" -> name.toString))
			given Conversion[get, Future[Schema.Enterprise]] = (fun: get) => fun.apply()
		}
		class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withEnterprise(body: Schema.Enterprise) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.Enterprise])
		}
		object patch {
			def apply(enterprisesId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"v1/enterprises/${enterprisesId}")).addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
		}
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListEnterprisesResponse]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.ListEnterprisesResponse])
		}
		object list {
			def apply(projectId: String, pageSize: Int, pageToken: String, view: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/enterprises")).addQueryStringParameters("projectId" -> projectId.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "view" -> view.toString))
			given Conversion[list, Future[Schema.ListEnterprisesResponse]] = (fun: list) => fun.apply()
		}
		object webApps {
			class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withWebApp(body: Schema.WebApp) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.WebApp])
			}
			object create {
				def apply(enterprisesId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/enterprises/${enterprisesId}/webApps")).addQueryStringParameters("parent" -> parent.toString))
			}
			class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
				def apply() = req.execute("DELETE").map(_.json.as[Schema.Empty])
			}
			object delete {
				def apply(enterprisesId :PlayApi, webAppsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/enterprises/${enterprisesId}/webApps/${webAppsId}")).addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.WebApp]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.WebApp])
			}
			object get {
				def apply(enterprisesId :PlayApi, webAppsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/enterprises/${enterprisesId}/webApps/${webAppsId}")).addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.WebApp]] = (fun: get) => fun.apply()
			}
			class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withWebApp(body: Schema.WebApp) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.WebApp])
			}
			object patch {
				def apply(enterprisesId :PlayApi, webAppsId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"v1/enterprises/${enterprisesId}/webApps/${webAppsId}")).addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListWebAppsResponse]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.ListWebAppsResponse])
			}
			object list {
				def apply(enterprisesId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/enterprises/${enterprisesId}/webApps")).addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListWebAppsResponse]] = (fun: list) => fun.apply()
			}
		}
		object enrollmentTokens {
			class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withEnrollmentToken(body: Schema.EnrollmentToken) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.EnrollmentToken])
			}
			object create {
				def apply(enterprisesId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/enterprises/${enterprisesId}/enrollmentTokens")).addQueryStringParameters("parent" -> parent.toString))
			}
			class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
				def apply() = req.execute("DELETE").map(_.json.as[Schema.Empty])
			}
			object delete {
				def apply(enterprisesId :PlayApi, enrollmentTokensId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/enterprises/${enterprisesId}/enrollmentTokens/${enrollmentTokensId}")).addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.EnrollmentToken]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.EnrollmentToken])
			}
			object get {
				def apply(enterprisesId :PlayApi, enrollmentTokensId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/enterprises/${enterprisesId}/enrollmentTokens/${enrollmentTokensId}")).addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.EnrollmentToken]] = (fun: get) => fun.apply()
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListEnrollmentTokensResponse]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.ListEnrollmentTokensResponse])
			}
			object list {
				def apply(enterprisesId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/enterprises/${enterprisesId}/enrollmentTokens")).addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListEnrollmentTokensResponse]] = (fun: list) => fun.apply()
			}
		}
		object webTokens {
			class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withWebToken(body: Schema.WebToken) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.WebToken])
			}
			object create {
				def apply(enterprisesId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/enterprises/${enterprisesId}/webTokens")).addQueryStringParameters("parent" -> parent.toString))
			}
		}
		object devices {
			class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
				/** Optional flags that control the device wiping behavior.<br>Possible values:<br>WIPE_DATA_FLAG_UNSPECIFIED: This value is ignored.<br>PRESERVE_RESET_PROTECTION_DATA: Preserve the factory reset protection data on the device.<br>WIPE_EXTERNAL_STORAGE: Additionally wipe the device's external storage (such as SD cards). */
				def withWipeDataFlags(wipeDataFlags: String) = new delete(req.addQueryStringParameters("wipeDataFlags" -> wipeDataFlags.toString))
				/** Optional. A short message displayed to the user before wiping the work profile on personal devices. This has no effect on company owned devices. The maximum message length is 200 characters. */
				def withWipeReasonMessage(wipeReasonMessage: String) = new delete(req.addQueryStringParameters("wipeReasonMessage" -> wipeReasonMessage.toString))
				def apply() = req.execute("DELETE").map(_.json.as[Schema.Empty])
			}
			object delete {
				def apply(enterprisesId :PlayApi, devicesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/enterprises/${enterprisesId}/devices/${devicesId}")).addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
			}
			class issueCommand(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withCommand(body: Schema.Command) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Operation])
			}
			object issueCommand {
				def apply(enterprisesId :PlayApi, devicesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): issueCommand = new issueCommand(auth(ws.url(BASE_URL + s"v1/enterprises/${enterprisesId}/devices/${devicesId}:issueCommand")).addQueryStringParameters("name" -> name.toString))
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Device]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.Device])
			}
			object get {
				def apply(enterprisesId :PlayApi, devicesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/enterprises/${enterprisesId}/devices/${devicesId}")).addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.Device]] = (fun: get) => fun.apply()
			}
			class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withDevice(body: Schema.Device) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.Device])
			}
			object patch {
				def apply(enterprisesId :PlayApi, devicesId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"v1/enterprises/${enterprisesId}/devices/${devicesId}")).addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListDevicesResponse]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.ListDevicesResponse])
			}
			object list {
				def apply(enterprisesId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/enterprises/${enterprisesId}/devices")).addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListDevicesResponse]] = (fun: list) => fun.apply()
			}
			object operations {
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListOperationsResponse]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.ListOperationsResponse])
				}
				object list {
					def apply(enterprisesId :PlayApi, devicesId :PlayApi, name: String, filter: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/enterprises/${enterprisesId}/devices/${devicesId}/operations")).addQueryStringParameters("name" -> name.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListOperationsResponse]] = (fun: list) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.Operation])
				}
				object get {
					def apply(enterprisesId :PlayApi, devicesId :PlayApi, operationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/enterprises/${enterprisesId}/devices/${devicesId}/operations/${operationsId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.Operation]] = (fun: get) => fun.apply()
				}
				class cancel(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					def apply() = req.execute("POST").map(_.json.as[Schema.Empty])
				}
				object cancel {
					def apply(enterprisesId :PlayApi, devicesId :PlayApi, operationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): cancel = new cancel(auth(ws.url(BASE_URL + s"v1/enterprises/${enterprisesId}/devices/${devicesId}/operations/${operationsId}:cancel")).addQueryStringParameters("name" -> name.toString))
					given Conversion[cancel, Future[Schema.Empty]] = (fun: cancel) => fun.apply()
				}
			}
		}
		object migrationTokens {
			class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withMigrationToken(body: Schema.MigrationToken) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.MigrationToken])
			}
			object create {
				def apply(enterprisesId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/enterprises/${enterprisesId}/migrationTokens")).addQueryStringParameters("parent" -> parent.toString))
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.MigrationToken]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.MigrationToken])
			}
			object get {
				def apply(enterprisesId :PlayApi, migrationTokensId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/enterprises/${enterprisesId}/migrationTokens/${migrationTokensId}")).addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.MigrationToken]] = (fun: get) => fun.apply()
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListMigrationTokensResponse]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.ListMigrationTokensResponse])
			}
			object list {
				def apply(enterprisesId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/enterprises/${enterprisesId}/migrationTokens")).addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListMigrationTokensResponse]] = (fun: list) => fun.apply()
			}
		}
		object applications {
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Application]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.Application])
			}
			object get {
				def apply(enterprisesId :PlayApi, applicationsId :PlayApi, name: String, languageCode: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/enterprises/${enterprisesId}/applications/${applicationsId}")).addQueryStringParameters("name" -> name.toString, "languageCode" -> languageCode.toString))
				given Conversion[get, Future[Schema.Application]] = (fun: get) => fun.apply()
			}
		}
		object policies {
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Policy]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.Policy])
			}
			object get {
				def apply(enterprisesId :PlayApi, policiesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/enterprises/${enterprisesId}/policies/${policiesId}")).addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.Policy]] = (fun: get) => fun.apply()
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListPoliciesResponse]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.ListPoliciesResponse])
			}
			object list {
				def apply(enterprisesId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/enterprises/${enterprisesId}/policies")).addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListPoliciesResponse]] = (fun: list) => fun.apply()
			}
			class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withPolicy(body: Schema.Policy) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.Policy])
			}
			object patch {
				def apply(enterprisesId :PlayApi, policiesId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"v1/enterprises/${enterprisesId}/policies/${policiesId}")).addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
			}
			class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
				def apply() = req.execute("DELETE").map(_.json.as[Schema.Empty])
			}
			object delete {
				def apply(enterprisesId :PlayApi, policiesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/enterprises/${enterprisesId}/policies/${policiesId}")).addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
			}
		}
	}
	object provisioningInfo {
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ProvisioningInfo]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.ProvisioningInfo])
		}
		object get {
			def apply(provisioningInfoId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/provisioningInfo/${provisioningInfoId}")).addQueryStringParameters("name" -> name.toString))
			given Conversion[get, Future[Schema.ProvisioningInfo]] = (fun: get) => fun.apply()
		}
	}
}
