package com.boresjo.play.api.google.accessapproval

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
		"""https://www.googleapis.com/auth/cloud-platform""" /* See, edit, configure, and delete your Google Cloud data and see the email address for your Google Account. */
	)

	private val BASE_URL = "https://accessapproval.googleapis.com/"

	object projects {
		/** Retrieves the service account that is used by Access Approval to access KMS keys for signing approved approval requests. */
		class getServiceAccount(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.AccessApprovalServiceAccount]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.AccessApprovalServiceAccount])
		}
		object getServiceAccount {
			def apply(projectsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): getServiceAccount = new getServiceAccount(ws.url(BASE_URL + s"v1/projects/${projectsId}/serviceAccount").addQueryStringParameters("name" -> name.toString))
			given Conversion[getServiceAccount, Future[Schema.AccessApprovalServiceAccount]] = (fun: getServiceAccount) => fun.apply()
		}
		/** Gets the settings associated with a project, folder, or organization. */
		class getAccessApprovalSettings(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.AccessApprovalSettings]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.AccessApprovalSettings])
		}
		object getAccessApprovalSettings {
			def apply(projectsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): getAccessApprovalSettings = new getAccessApprovalSettings(ws.url(BASE_URL + s"v1/projects/${projectsId}/accessApprovalSettings").addQueryStringParameters("name" -> name.toString))
			given Conversion[getAccessApprovalSettings, Future[Schema.AccessApprovalSettings]] = (fun: getAccessApprovalSettings) => fun.apply()
		}
		/** Deletes the settings associated with a project, folder, or organization. This will have the effect of disabling Access Approval for the project, folder, or organization, but only if all ancestors also have Access Approval disabled. If Access Approval is enabled at a higher level of the hierarchy, then Access Approval will still be enabled at this level as the settings are inherited. */
		class deleteAccessApprovalSettings(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
		}
		object deleteAccessApprovalSettings {
			def apply(projectsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): deleteAccessApprovalSettings = new deleteAccessApprovalSettings(ws.url(BASE_URL + s"v1/projects/${projectsId}/accessApprovalSettings").addQueryStringParameters("name" -> name.toString))
			given Conversion[deleteAccessApprovalSettings, Future[Schema.Empty]] = (fun: deleteAccessApprovalSettings) => fun.apply()
		}
		/** Updates the settings associated with a project, folder, or organization. Settings to update are determined by the value of field_mask. */
		class updateAccessApprovalSettings(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def withAccessApprovalSettings(body: Schema.AccessApprovalSettings) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.AccessApprovalSettings])
		}
		object updateAccessApprovalSettings {
			def apply(projectsId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): updateAccessApprovalSettings = new updateAccessApprovalSettings(ws.url(BASE_URL + s"v1/projects/${projectsId}/accessApprovalSettings").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
		}
		object approvalRequests {
			/** Approves a request and returns the updated ApprovalRequest. Returns NOT_FOUND if the request does not exist. Returns FAILED_PRECONDITION if the request exists but is not in a pending state. */
			class approve(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withApproveApprovalRequestMessage(body: Schema.ApproveApprovalRequestMessage) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.ApprovalRequest])
			}
			object approve {
				def apply(projectsId :PlayApi, approvalRequestsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): approve = new approve(ws.url(BASE_URL + s"v1/projects/${projectsId}/approvalRequests/${approvalRequestsId}:approve").addQueryStringParameters("name" -> name.toString))
			}
			/** Invalidates an existing ApprovalRequest. Returns the updated ApprovalRequest. NOTE: This does not deny access to the resource if another request has been made and approved. It only invalidates a single approval. Returns FAILED_PRECONDITION if the request exists but is not in an approved state. */
			class invalidate(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withInvalidateApprovalRequestMessage(body: Schema.InvalidateApprovalRequestMessage) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.ApprovalRequest])
			}
			object invalidate {
				def apply(projectsId :PlayApi, approvalRequestsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): invalidate = new invalidate(ws.url(BASE_URL + s"v1/projects/${projectsId}/approvalRequests/${approvalRequestsId}:invalidate").addQueryStringParameters("name" -> name.toString))
			}
			/** Dismisses a request. Returns the updated ApprovalRequest. NOTE: This does not deny access to the resource if another request has been made and approved. It is equivalent in effect to ignoring the request altogether. Returns NOT_FOUND if the request does not exist. Returns FAILED_PRECONDITION if the request exists but is not in a pending state. */
			class dismiss(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withDismissApprovalRequestMessage(body: Schema.DismissApprovalRequestMessage) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.ApprovalRequest])
			}
			object dismiss {
				def apply(projectsId :PlayApi, approvalRequestsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): dismiss = new dismiss(ws.url(BASE_URL + s"v1/projects/${projectsId}/approvalRequests/${approvalRequestsId}:dismiss").addQueryStringParameters("name" -> name.toString))
			}
			/** Gets an approval request. Returns NOT_FOUND if the request does not exist. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ApprovalRequest]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ApprovalRequest])
			}
			object get {
				def apply(projectsId :PlayApi, approvalRequestsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/approvalRequests/${approvalRequestsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.ApprovalRequest]] = (fun: get) => fun.apply()
			}
			/** Lists approval requests associated with a project, folder, or organization. Approval requests can be filtered by state (pending, active, dismissed). The order is reverse chronological. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListApprovalRequestsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListApprovalRequestsResponse])
			}
			object list {
				def apply(projectsId :PlayApi, parent: String, filter: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/approvalRequests").addQueryStringParameters("parent" -> parent.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListApprovalRequestsResponse]] = (fun: list) => fun.apply()
			}
		}
	}
	object folders {
		/** Retrieves the service account that is used by Access Approval to access KMS keys for signing approved approval requests. */
		class getServiceAccount(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.AccessApprovalServiceAccount]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.AccessApprovalServiceAccount])
		}
		object getServiceAccount {
			def apply(foldersId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): getServiceAccount = new getServiceAccount(ws.url(BASE_URL + s"v1/folders/${foldersId}/serviceAccount").addQueryStringParameters("name" -> name.toString))
			given Conversion[getServiceAccount, Future[Schema.AccessApprovalServiceAccount]] = (fun: getServiceAccount) => fun.apply()
		}
		/** Gets the settings associated with a project, folder, or organization. */
		class getAccessApprovalSettings(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.AccessApprovalSettings]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.AccessApprovalSettings])
		}
		object getAccessApprovalSettings {
			def apply(foldersId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): getAccessApprovalSettings = new getAccessApprovalSettings(ws.url(BASE_URL + s"v1/folders/${foldersId}/accessApprovalSettings").addQueryStringParameters("name" -> name.toString))
			given Conversion[getAccessApprovalSettings, Future[Schema.AccessApprovalSettings]] = (fun: getAccessApprovalSettings) => fun.apply()
		}
		/** Deletes the settings associated with a project, folder, or organization. This will have the effect of disabling Access Approval for the project, folder, or organization, but only if all ancestors also have Access Approval disabled. If Access Approval is enabled at a higher level of the hierarchy, then Access Approval will still be enabled at this level as the settings are inherited. */
		class deleteAccessApprovalSettings(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
		}
		object deleteAccessApprovalSettings {
			def apply(foldersId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): deleteAccessApprovalSettings = new deleteAccessApprovalSettings(ws.url(BASE_URL + s"v1/folders/${foldersId}/accessApprovalSettings").addQueryStringParameters("name" -> name.toString))
			given Conversion[deleteAccessApprovalSettings, Future[Schema.Empty]] = (fun: deleteAccessApprovalSettings) => fun.apply()
		}
		/** Updates the settings associated with a project, folder, or organization. Settings to update are determined by the value of field_mask. */
		class updateAccessApprovalSettings(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def withAccessApprovalSettings(body: Schema.AccessApprovalSettings) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.AccessApprovalSettings])
		}
		object updateAccessApprovalSettings {
			def apply(foldersId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): updateAccessApprovalSettings = new updateAccessApprovalSettings(ws.url(BASE_URL + s"v1/folders/${foldersId}/accessApprovalSettings").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
		}
		object approvalRequests {
			/** Approves a request and returns the updated ApprovalRequest. Returns NOT_FOUND if the request does not exist. Returns FAILED_PRECONDITION if the request exists but is not in a pending state. */
			class approve(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withApproveApprovalRequestMessage(body: Schema.ApproveApprovalRequestMessage) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.ApprovalRequest])
			}
			object approve {
				def apply(foldersId :PlayApi, approvalRequestsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): approve = new approve(ws.url(BASE_URL + s"v1/folders/${foldersId}/approvalRequests/${approvalRequestsId}:approve").addQueryStringParameters("name" -> name.toString))
			}
			/** Invalidates an existing ApprovalRequest. Returns the updated ApprovalRequest. NOTE: This does not deny access to the resource if another request has been made and approved. It only invalidates a single approval. Returns FAILED_PRECONDITION if the request exists but is not in an approved state. */
			class invalidate(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withInvalidateApprovalRequestMessage(body: Schema.InvalidateApprovalRequestMessage) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.ApprovalRequest])
			}
			object invalidate {
				def apply(foldersId :PlayApi, approvalRequestsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): invalidate = new invalidate(ws.url(BASE_URL + s"v1/folders/${foldersId}/approvalRequests/${approvalRequestsId}:invalidate").addQueryStringParameters("name" -> name.toString))
			}
			/** Dismisses a request. Returns the updated ApprovalRequest. NOTE: This does not deny access to the resource if another request has been made and approved. It is equivalent in effect to ignoring the request altogether. Returns NOT_FOUND if the request does not exist. Returns FAILED_PRECONDITION if the request exists but is not in a pending state. */
			class dismiss(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withDismissApprovalRequestMessage(body: Schema.DismissApprovalRequestMessage) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.ApprovalRequest])
			}
			object dismiss {
				def apply(foldersId :PlayApi, approvalRequestsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): dismiss = new dismiss(ws.url(BASE_URL + s"v1/folders/${foldersId}/approvalRequests/${approvalRequestsId}:dismiss").addQueryStringParameters("name" -> name.toString))
			}
			/** Gets an approval request. Returns NOT_FOUND if the request does not exist. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ApprovalRequest]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ApprovalRequest])
			}
			object get {
				def apply(foldersId :PlayApi, approvalRequestsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/folders/${foldersId}/approvalRequests/${approvalRequestsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.ApprovalRequest]] = (fun: get) => fun.apply()
			}
			/** Lists approval requests associated with a project, folder, or organization. Approval requests can be filtered by state (pending, active, dismissed). The order is reverse chronological. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListApprovalRequestsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListApprovalRequestsResponse])
			}
			object list {
				def apply(foldersId :PlayApi, parent: String, filter: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/folders/${foldersId}/approvalRequests").addQueryStringParameters("parent" -> parent.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListApprovalRequestsResponse]] = (fun: list) => fun.apply()
			}
		}
	}
	object organizations {
		/** Retrieves the service account that is used by Access Approval to access KMS keys for signing approved approval requests. */
		class getServiceAccount(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.AccessApprovalServiceAccount]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.AccessApprovalServiceAccount])
		}
		object getServiceAccount {
			def apply(organizationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): getServiceAccount = new getServiceAccount(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/serviceAccount").addQueryStringParameters("name" -> name.toString))
			given Conversion[getServiceAccount, Future[Schema.AccessApprovalServiceAccount]] = (fun: getServiceAccount) => fun.apply()
		}
		/** Gets the settings associated with a project, folder, or organization. */
		class getAccessApprovalSettings(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.AccessApprovalSettings]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.AccessApprovalSettings])
		}
		object getAccessApprovalSettings {
			def apply(organizationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): getAccessApprovalSettings = new getAccessApprovalSettings(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/accessApprovalSettings").addQueryStringParameters("name" -> name.toString))
			given Conversion[getAccessApprovalSettings, Future[Schema.AccessApprovalSettings]] = (fun: getAccessApprovalSettings) => fun.apply()
		}
		/** Deletes the settings associated with a project, folder, or organization. This will have the effect of disabling Access Approval for the project, folder, or organization, but only if all ancestors also have Access Approval disabled. If Access Approval is enabled at a higher level of the hierarchy, then Access Approval will still be enabled at this level as the settings are inherited. */
		class deleteAccessApprovalSettings(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
		}
		object deleteAccessApprovalSettings {
			def apply(organizationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): deleteAccessApprovalSettings = new deleteAccessApprovalSettings(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/accessApprovalSettings").addQueryStringParameters("name" -> name.toString))
			given Conversion[deleteAccessApprovalSettings, Future[Schema.Empty]] = (fun: deleteAccessApprovalSettings) => fun.apply()
		}
		/** Updates the settings associated with a project, folder, or organization. Settings to update are determined by the value of field_mask. */
		class updateAccessApprovalSettings(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def withAccessApprovalSettings(body: Schema.AccessApprovalSettings) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.AccessApprovalSettings])
		}
		object updateAccessApprovalSettings {
			def apply(organizationsId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): updateAccessApprovalSettings = new updateAccessApprovalSettings(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/accessApprovalSettings").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
		}
		object approvalRequests {
			/** Approves a request and returns the updated ApprovalRequest. Returns NOT_FOUND if the request does not exist. Returns FAILED_PRECONDITION if the request exists but is not in a pending state. */
			class approve(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withApproveApprovalRequestMessage(body: Schema.ApproveApprovalRequestMessage) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.ApprovalRequest])
			}
			object approve {
				def apply(organizationsId :PlayApi, approvalRequestsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): approve = new approve(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/approvalRequests/${approvalRequestsId}:approve").addQueryStringParameters("name" -> name.toString))
			}
			/** Invalidates an existing ApprovalRequest. Returns the updated ApprovalRequest. NOTE: This does not deny access to the resource if another request has been made and approved. It only invalidates a single approval. Returns FAILED_PRECONDITION if the request exists but is not in an approved state. */
			class invalidate(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withInvalidateApprovalRequestMessage(body: Schema.InvalidateApprovalRequestMessage) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.ApprovalRequest])
			}
			object invalidate {
				def apply(organizationsId :PlayApi, approvalRequestsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): invalidate = new invalidate(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/approvalRequests/${approvalRequestsId}:invalidate").addQueryStringParameters("name" -> name.toString))
			}
			/** Dismisses a request. Returns the updated ApprovalRequest. NOTE: This does not deny access to the resource if another request has been made and approved. It is equivalent in effect to ignoring the request altogether. Returns NOT_FOUND if the request does not exist. Returns FAILED_PRECONDITION if the request exists but is not in a pending state. */
			class dismiss(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withDismissApprovalRequestMessage(body: Schema.DismissApprovalRequestMessage) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.ApprovalRequest])
			}
			object dismiss {
				def apply(organizationsId :PlayApi, approvalRequestsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): dismiss = new dismiss(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/approvalRequests/${approvalRequestsId}:dismiss").addQueryStringParameters("name" -> name.toString))
			}
			/** Gets an approval request. Returns NOT_FOUND if the request does not exist. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ApprovalRequest]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ApprovalRequest])
			}
			object get {
				def apply(organizationsId :PlayApi, approvalRequestsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/approvalRequests/${approvalRequestsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.ApprovalRequest]] = (fun: get) => fun.apply()
			}
			/** Lists approval requests associated with a project, folder, or organization. Approval requests can be filtered by state (pending, active, dismissed). The order is reverse chronological. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListApprovalRequestsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListApprovalRequestsResponse])
			}
			object list {
				def apply(organizationsId :PlayApi, parent: String, filter: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/approvalRequests").addQueryStringParameters("parent" -> parent.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListApprovalRequestsResponse]] = (fun: list) => fun.apply()
			}
		}
	}
}
