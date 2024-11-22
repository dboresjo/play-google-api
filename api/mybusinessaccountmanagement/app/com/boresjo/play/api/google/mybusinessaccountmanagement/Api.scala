package com.boresjo.play.api.google.mybusinessaccountmanagement

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	val scopes = Seq()

	private val BASE_URL = "https://mybusinessaccountmanagement.googleapis.com/"

	object locations {
		/** Moves a location from an account that the user owns to another account that the same user administers. The user must be an owner of the account the location is currently associated with and must also be at least a manager of the destination account. */
		class transfer(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq()
			/** Perform the request */
			def withTransferLocationRequest(body: Schema.TransferLocationRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Empty])
		}
		object transfer {
			def apply(locationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): transfer = new transfer(ws.url(BASE_URL + s"v1/locations/${locationsId}:transfer").addQueryStringParameters("name" -> name.toString))
		}
		object admins {
			/** Lists all of the admins for the specified location. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListLocationAdminsResponse]) {
				val scopes = Seq()
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListLocationAdminsResponse])
			}
			object list {
				def apply(locationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/locations/${locationsId}/admins").addQueryStringParameters("parent" -> parent.toString))
				given Conversion[list, Future[Schema.ListLocationAdminsResponse]] = (fun: list) => fun.apply()
			}
			/** Invites the specified user to become an administrator for the specified location. The invitee must accept the invitation in order to be granted access to the location. See AcceptInvitation to programmatically accept an invitation. */
			class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq()
				/** Perform the request */
				def withAdmin(body: Schema.Admin) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Admin])
			}
			object create {
				def apply(locationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/locations/${locationsId}/admins").addQueryStringParameters("parent" -> parent.toString))
			}
			/** Removes the specified admin as a manager of the specified location. */
			class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
				val scopes = Seq()
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
			}
			object delete {
				def apply(locationsId :PlayApi, adminsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/locations/${locationsId}/admins/${adminsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
			}
			/** Updates the Admin for the specified location. Only the AdminRole of the Admin can be updated. */
			class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq()
				/** Perform the request */
				def withAdmin(body: Schema.Admin) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Admin])
			}
			object patch {
				def apply(locationsId :PlayApi, adminsId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/locations/${locationsId}/admins/${adminsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
			}
		}
	}
	object accounts {
		/** Creates an account with the specified name and type under the given parent. - Personal accounts and Organizations cannot be created. - User Groups cannot be created with a Personal account as primary owner. - Location Groups cannot be created with a primary owner of a Personal account if the Personal account is in an Organization. - Location Groups cannot own Location Groups. */
		class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq()
			/** Perform the request */
			def withAccount(body: Schema.Account) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Account])
		}
		object create {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/accounts").addQueryStringParameters())
		}
		/** Gets the specified account. Returns `NOT_FOUND` if the account does not exist or if the caller does not have access rights to it. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Account]) {
			val scopes = Seq()
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Account])
		}
		object get {
			def apply(accountsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/accounts/${accountsId}").addQueryStringParameters("name" -> name.toString))
			given Conversion[get, Future[Schema.Account]] = (fun: get) => fun.apply()
		}
		/** Updates the specified business account. Personal accounts cannot be updated using this method. */
		class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq()
			/** Optional. If true, the request is validated without actually updating the account. */
			def withValidateOnly(validateOnly: Boolean) = new patch(req.addQueryStringParameters("validateOnly" -> validateOnly.toString))
			/** Perform the request */
			def withAccount(body: Schema.Account) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Account])
		}
		object patch {
			def apply(accountsId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/accounts/${accountsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
		}
		/** Lists all of the accounts for the authenticated user. This includes all accounts that the user owns, as well as any accounts for which the user has management rights. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListAccountsResponse]) {
			val scopes = Seq()
			/** Optional. The resource name of the account for which the list of directly accessible accounts is to be retrieved. This only makes sense for Organizations and User Groups. If empty, will return `ListAccounts` for the authenticated user. `accounts/{account_id}`. */
			def withParentAccount(parentAccount: String) = new list(req.addQueryStringParameters("parentAccount" -> parentAccount.toString))
			/** Optional. How many accounts to fetch per page. The default and maximum is 20.<br>Format: int32 */
			def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
			/** Optional. If specified, the next page of accounts is retrieved. The `pageToken` is returned when a call to `accounts.list` returns more results than can fit into the requested page size. */
			def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
			/** Optional. A filter constraining the accounts to return. The response includes only entries that match the filter. If `filter` is empty, then no constraints are applied and all accounts (paginated) are retrieved for the requested account. For example, a request with the filter `type=USER_GROUP` will only return user groups. The `type` field is the only supported filter. */
			def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListAccountsResponse])
		}
		object list {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/accounts").addQueryStringParameters())
			given Conversion[list, Future[Schema.ListAccountsResponse]] = (fun: list) => fun.apply()
		}
		object admins {
			/** Lists the admins for the specified account. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListAccountAdminsResponse]) {
				val scopes = Seq()
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListAccountAdminsResponse])
			}
			object list {
				def apply(accountsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/accounts/${accountsId}/admins").addQueryStringParameters("parent" -> parent.toString))
				given Conversion[list, Future[Schema.ListAccountAdminsResponse]] = (fun: list) => fun.apply()
			}
			/** Invites the specified user to become an administrator for the specified account. The invitee must accept the invitation in order to be granted access to the account. See AcceptInvitation to programmatically accept an invitation. */
			class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq()
				/** Perform the request */
				def withAdmin(body: Schema.Admin) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Admin])
			}
			object create {
				def apply(accountsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/accounts/${accountsId}/admins").addQueryStringParameters("parent" -> parent.toString))
			}
			/** Removes the specified admin from the specified account. */
			class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
				val scopes = Seq()
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
			}
			object delete {
				def apply(accountsId :PlayApi, adminsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/accounts/${accountsId}/admins/${adminsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
			}
			/** Updates the Admin for the specified Account Admin. */
			class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq()
				/** Perform the request */
				def withAdmin(body: Schema.Admin) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Admin])
			}
			object patch {
				def apply(accountsId :PlayApi, adminsId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/accounts/${accountsId}/admins/${adminsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
			}
		}
		object invitations {
			/** Accepts the specified invitation. */
			class accept(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq()
				/** Perform the request */
				def withAcceptInvitationRequest(body: Schema.AcceptInvitationRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Empty])
			}
			object accept {
				def apply(accountsId :PlayApi, invitationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): accept = new accept(ws.url(BASE_URL + s"v1/accounts/${accountsId}/invitations/${invitationsId}:accept").addQueryStringParameters("name" -> name.toString))
			}
			/** Declines the specified invitation. */
			class decline(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq()
				/** Perform the request */
				def withDeclineInvitationRequest(body: Schema.DeclineInvitationRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Empty])
			}
			object decline {
				def apply(accountsId :PlayApi, invitationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): decline = new decline(ws.url(BASE_URL + s"v1/accounts/${accountsId}/invitations/${invitationsId}:decline").addQueryStringParameters("name" -> name.toString))
			}
			/** Lists pending invitations for the specified account. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListInvitationsResponse]) {
				val scopes = Seq()
				/** Optional. Filtering the response is supported via the Invitation.target_type field. */
				def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListInvitationsResponse])
			}
			object list {
				def apply(accountsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/accounts/${accountsId}/invitations").addQueryStringParameters("parent" -> parent.toString))
				given Conversion[list, Future[Schema.ListInvitationsResponse]] = (fun: list) => fun.apply()
			}
		}
	}
}
