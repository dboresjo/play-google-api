package com.boresjo.play.api.google.accessapproval

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://accessapproval.googleapis.com/"

	object projects {
		class getServiceAccount(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.AccessApprovalServiceAccount]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.AccessApprovalServiceAccount])
		}
		object getServiceAccount {
			def apply(projectsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): getServiceAccount = new getServiceAccount(ws.url(BASE_URL + s"v1/projects/${projectsId}/serviceAccount").addQueryStringParameters("name" -> name.toString))
			given Conversion[getServiceAccount, Future[Schema.AccessApprovalServiceAccount]] = (fun: getServiceAccount) => fun.apply()
		}
		class getAccessApprovalSettings(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.AccessApprovalSettings]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.AccessApprovalSettings])
		}
		object getAccessApprovalSettings {
			def apply(projectsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): getAccessApprovalSettings = new getAccessApprovalSettings(ws.url(BASE_URL + s"v1/projects/${projectsId}/accessApprovalSettings").addQueryStringParameters("name" -> name.toString))
			given Conversion[getAccessApprovalSettings, Future[Schema.AccessApprovalSettings]] = (fun: getAccessApprovalSettings) => fun.apply()
		}
		class deleteAccessApprovalSettings(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
			def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
		}
		object deleteAccessApprovalSettings {
			def apply(projectsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): deleteAccessApprovalSettings = new deleteAccessApprovalSettings(ws.url(BASE_URL + s"v1/projects/${projectsId}/accessApprovalSettings").addQueryStringParameters("name" -> name.toString))
			given Conversion[deleteAccessApprovalSettings, Future[Schema.Empty]] = (fun: deleteAccessApprovalSettings) => fun.apply()
		}
		class updateAccessApprovalSettings(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withAccessApprovalSettings(body: Schema.AccessApprovalSettings) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.AccessApprovalSettings])
		}
		object updateAccessApprovalSettings {
			def apply(projectsId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): updateAccessApprovalSettings = new updateAccessApprovalSettings(ws.url(BASE_URL + s"v1/projects/${projectsId}/accessApprovalSettings").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
		}
		object approvalRequests {
			class approve(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withApproveApprovalRequestMessage(body: Schema.ApproveApprovalRequestMessage) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.ApprovalRequest])
			}
			object approve {
				def apply(projectsId :PlayApi, approvalRequestsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): approve = new approve(ws.url(BASE_URL + s"v1/projects/${projectsId}/approvalRequests/${approvalRequestsId}:approve").addQueryStringParameters("name" -> name.toString))
			}
			class invalidate(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withInvalidateApprovalRequestMessage(body: Schema.InvalidateApprovalRequestMessage) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.ApprovalRequest])
			}
			object invalidate {
				def apply(projectsId :PlayApi, approvalRequestsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): invalidate = new invalidate(ws.url(BASE_URL + s"v1/projects/${projectsId}/approvalRequests/${approvalRequestsId}:invalidate").addQueryStringParameters("name" -> name.toString))
			}
			class dismiss(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withDismissApprovalRequestMessage(body: Schema.DismissApprovalRequestMessage) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.ApprovalRequest])
			}
			object dismiss {
				def apply(projectsId :PlayApi, approvalRequestsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): dismiss = new dismiss(ws.url(BASE_URL + s"v1/projects/${projectsId}/approvalRequests/${approvalRequestsId}:dismiss").addQueryStringParameters("name" -> name.toString))
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ApprovalRequest]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ApprovalRequest])
			}
			object get {
				def apply(projectsId :PlayApi, approvalRequestsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/approvalRequests/${approvalRequestsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.ApprovalRequest]] = (fun: get) => fun.apply()
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListApprovalRequestsResponse]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListApprovalRequestsResponse])
			}
			object list {
				def apply(projectsId :PlayApi, parent: String, filter: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/approvalRequests").addQueryStringParameters("parent" -> parent.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListApprovalRequestsResponse]] = (fun: list) => fun.apply()
			}
		}
	}
	object folders {
		class getServiceAccount(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.AccessApprovalServiceAccount]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.AccessApprovalServiceAccount])
		}
		object getServiceAccount {
			def apply(foldersId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): getServiceAccount = new getServiceAccount(ws.url(BASE_URL + s"v1/folders/${foldersId}/serviceAccount").addQueryStringParameters("name" -> name.toString))
			given Conversion[getServiceAccount, Future[Schema.AccessApprovalServiceAccount]] = (fun: getServiceAccount) => fun.apply()
		}
		class getAccessApprovalSettings(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.AccessApprovalSettings]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.AccessApprovalSettings])
		}
		object getAccessApprovalSettings {
			def apply(foldersId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): getAccessApprovalSettings = new getAccessApprovalSettings(ws.url(BASE_URL + s"v1/folders/${foldersId}/accessApprovalSettings").addQueryStringParameters("name" -> name.toString))
			given Conversion[getAccessApprovalSettings, Future[Schema.AccessApprovalSettings]] = (fun: getAccessApprovalSettings) => fun.apply()
		}
		class deleteAccessApprovalSettings(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
			def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
		}
		object deleteAccessApprovalSettings {
			def apply(foldersId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): deleteAccessApprovalSettings = new deleteAccessApprovalSettings(ws.url(BASE_URL + s"v1/folders/${foldersId}/accessApprovalSettings").addQueryStringParameters("name" -> name.toString))
			given Conversion[deleteAccessApprovalSettings, Future[Schema.Empty]] = (fun: deleteAccessApprovalSettings) => fun.apply()
		}
		class updateAccessApprovalSettings(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withAccessApprovalSettings(body: Schema.AccessApprovalSettings) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.AccessApprovalSettings])
		}
		object updateAccessApprovalSettings {
			def apply(foldersId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): updateAccessApprovalSettings = new updateAccessApprovalSettings(ws.url(BASE_URL + s"v1/folders/${foldersId}/accessApprovalSettings").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
		}
		object approvalRequests {
			class approve(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withApproveApprovalRequestMessage(body: Schema.ApproveApprovalRequestMessage) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.ApprovalRequest])
			}
			object approve {
				def apply(foldersId :PlayApi, approvalRequestsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): approve = new approve(ws.url(BASE_URL + s"v1/folders/${foldersId}/approvalRequests/${approvalRequestsId}:approve").addQueryStringParameters("name" -> name.toString))
			}
			class invalidate(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withInvalidateApprovalRequestMessage(body: Schema.InvalidateApprovalRequestMessage) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.ApprovalRequest])
			}
			object invalidate {
				def apply(foldersId :PlayApi, approvalRequestsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): invalidate = new invalidate(ws.url(BASE_URL + s"v1/folders/${foldersId}/approvalRequests/${approvalRequestsId}:invalidate").addQueryStringParameters("name" -> name.toString))
			}
			class dismiss(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withDismissApprovalRequestMessage(body: Schema.DismissApprovalRequestMessage) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.ApprovalRequest])
			}
			object dismiss {
				def apply(foldersId :PlayApi, approvalRequestsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): dismiss = new dismiss(ws.url(BASE_URL + s"v1/folders/${foldersId}/approvalRequests/${approvalRequestsId}:dismiss").addQueryStringParameters("name" -> name.toString))
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ApprovalRequest]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ApprovalRequest])
			}
			object get {
				def apply(foldersId :PlayApi, approvalRequestsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/folders/${foldersId}/approvalRequests/${approvalRequestsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.ApprovalRequest]] = (fun: get) => fun.apply()
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListApprovalRequestsResponse]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListApprovalRequestsResponse])
			}
			object list {
				def apply(foldersId :PlayApi, parent: String, filter: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/folders/${foldersId}/approvalRequests").addQueryStringParameters("parent" -> parent.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListApprovalRequestsResponse]] = (fun: list) => fun.apply()
			}
		}
	}
	object organizations {
		class getServiceAccount(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.AccessApprovalServiceAccount]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.AccessApprovalServiceAccount])
		}
		object getServiceAccount {
			def apply(organizationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): getServiceAccount = new getServiceAccount(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/serviceAccount").addQueryStringParameters("name" -> name.toString))
			given Conversion[getServiceAccount, Future[Schema.AccessApprovalServiceAccount]] = (fun: getServiceAccount) => fun.apply()
		}
		class getAccessApprovalSettings(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.AccessApprovalSettings]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.AccessApprovalSettings])
		}
		object getAccessApprovalSettings {
			def apply(organizationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): getAccessApprovalSettings = new getAccessApprovalSettings(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/accessApprovalSettings").addQueryStringParameters("name" -> name.toString))
			given Conversion[getAccessApprovalSettings, Future[Schema.AccessApprovalSettings]] = (fun: getAccessApprovalSettings) => fun.apply()
		}
		class deleteAccessApprovalSettings(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
			def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
		}
		object deleteAccessApprovalSettings {
			def apply(organizationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): deleteAccessApprovalSettings = new deleteAccessApprovalSettings(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/accessApprovalSettings").addQueryStringParameters("name" -> name.toString))
			given Conversion[deleteAccessApprovalSettings, Future[Schema.Empty]] = (fun: deleteAccessApprovalSettings) => fun.apply()
		}
		class updateAccessApprovalSettings(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withAccessApprovalSettings(body: Schema.AccessApprovalSettings) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.AccessApprovalSettings])
		}
		object updateAccessApprovalSettings {
			def apply(organizationsId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): updateAccessApprovalSettings = new updateAccessApprovalSettings(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/accessApprovalSettings").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
		}
		object approvalRequests {
			class approve(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withApproveApprovalRequestMessage(body: Schema.ApproveApprovalRequestMessage) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.ApprovalRequest])
			}
			object approve {
				def apply(organizationsId :PlayApi, approvalRequestsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): approve = new approve(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/approvalRequests/${approvalRequestsId}:approve").addQueryStringParameters("name" -> name.toString))
			}
			class invalidate(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withInvalidateApprovalRequestMessage(body: Schema.InvalidateApprovalRequestMessage) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.ApprovalRequest])
			}
			object invalidate {
				def apply(organizationsId :PlayApi, approvalRequestsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): invalidate = new invalidate(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/approvalRequests/${approvalRequestsId}:invalidate").addQueryStringParameters("name" -> name.toString))
			}
			class dismiss(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withDismissApprovalRequestMessage(body: Schema.DismissApprovalRequestMessage) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.ApprovalRequest])
			}
			object dismiss {
				def apply(organizationsId :PlayApi, approvalRequestsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): dismiss = new dismiss(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/approvalRequests/${approvalRequestsId}:dismiss").addQueryStringParameters("name" -> name.toString))
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ApprovalRequest]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ApprovalRequest])
			}
			object get {
				def apply(organizationsId :PlayApi, approvalRequestsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/approvalRequests/${approvalRequestsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.ApprovalRequest]] = (fun: get) => fun.apply()
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListApprovalRequestsResponse]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListApprovalRequestsResponse])
			}
			object list {
				def apply(organizationsId :PlayApi, parent: String, filter: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/approvalRequests").addQueryStringParameters("parent" -> parent.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListApprovalRequestsResponse]] = (fun: list) => fun.apply()
			}
		}
	}
}
