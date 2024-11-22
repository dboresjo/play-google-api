package com.boresjo.play.api.google.mybusinessaccountmanagement

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://mybusinessaccountmanagement.googleapis.com/"

	object locations {
		class transfer(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withTransferLocationRequest(body: Schema.TransferLocationRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Empty])
		}
		object transfer {
			def apply(locationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): transfer = new transfer(ws.url(BASE_URL + s"v1/locations/${locationsId}:transfer").addQueryStringParameters("name" -> name.toString))
		}
		object admins {
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListLocationAdminsResponse]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListLocationAdminsResponse])
			}
			object list {
				def apply(locationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/locations/${locationsId}/admins").addQueryStringParameters("parent" -> parent.toString))
				given Conversion[list, Future[Schema.ListLocationAdminsResponse]] = (fun: list) => fun.apply()
			}
			class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withAdmin(body: Schema.Admin) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Admin])
			}
			object create {
				def apply(locationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/locations/${locationsId}/admins").addQueryStringParameters("parent" -> parent.toString))
			}
			class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
				def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
			}
			object delete {
				def apply(locationsId :PlayApi, adminsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/locations/${locationsId}/admins/${adminsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
			}
			class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withAdmin(body: Schema.Admin) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Admin])
			}
			object patch {
				def apply(locationsId :PlayApi, adminsId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/locations/${locationsId}/admins/${adminsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
			}
		}
	}
	object accounts {
		class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withAccount(body: Schema.Account) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Account])
		}
		object create {
			def apply()(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/accounts").addQueryStringParameters())
		}
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Account]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Account])
		}
		object get {
			def apply(accountsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/accounts/${accountsId}").addQueryStringParameters("name" -> name.toString))
			given Conversion[get, Future[Schema.Account]] = (fun: get) => fun.apply()
		}
		class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			/** Optional. If true, the request is validated without actually updating the account. */
			def withValidateOnly(validateOnly: Boolean) = new patch(req.addQueryStringParameters("validateOnly" -> validateOnly.toString))
			def withAccount(body: Schema.Account) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Account])
		}
		object patch {
			def apply(accountsId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/accounts/${accountsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
		}
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListAccountsResponse]) {
			/** Optional. The resource name of the account for which the list of directly accessible accounts is to be retrieved. This only makes sense for Organizations and User Groups. If empty, will return `ListAccounts` for the authenticated user. `accounts/{account_id}`. */
			def withParentAccount(parentAccount: String) = new list(req.addQueryStringParameters("parentAccount" -> parentAccount.toString))
			/** Optional. How many accounts to fetch per page. The default and maximum is 20.<br>Format: int32 */
			def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
			/** Optional. If specified, the next page of accounts is retrieved. The `pageToken` is returned when a call to `accounts.list` returns more results than can fit into the requested page size. */
			def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
			/** Optional. A filter constraining the accounts to return. The response includes only entries that match the filter. If `filter` is empty, then no constraints are applied and all accounts (paginated) are retrieved for the requested account. For example, a request with the filter `type=USER_GROUP` will only return user groups. The `type` field is the only supported filter. */
			def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListAccountsResponse])
		}
		object list {
			def apply()(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/accounts").addQueryStringParameters())
			given Conversion[list, Future[Schema.ListAccountsResponse]] = (fun: list) => fun.apply()
		}
		object admins {
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListAccountAdminsResponse]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListAccountAdminsResponse])
			}
			object list {
				def apply(accountsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/accounts/${accountsId}/admins").addQueryStringParameters("parent" -> parent.toString))
				given Conversion[list, Future[Schema.ListAccountAdminsResponse]] = (fun: list) => fun.apply()
			}
			class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withAdmin(body: Schema.Admin) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Admin])
			}
			object create {
				def apply(accountsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/accounts/${accountsId}/admins").addQueryStringParameters("parent" -> parent.toString))
			}
			class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
				def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
			}
			object delete {
				def apply(accountsId :PlayApi, adminsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/accounts/${accountsId}/admins/${adminsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
			}
			class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withAdmin(body: Schema.Admin) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Admin])
			}
			object patch {
				def apply(accountsId :PlayApi, adminsId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/accounts/${accountsId}/admins/${adminsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
			}
		}
		object invitations {
			class accept(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withAcceptInvitationRequest(body: Schema.AcceptInvitationRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Empty])
			}
			object accept {
				def apply(accountsId :PlayApi, invitationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): accept = new accept(ws.url(BASE_URL + s"v1/accounts/${accountsId}/invitations/${invitationsId}:accept").addQueryStringParameters("name" -> name.toString))
			}
			class decline(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withDeclineInvitationRequest(body: Schema.DeclineInvitationRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Empty])
			}
			object decline {
				def apply(accountsId :PlayApi, invitationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): decline = new decline(ws.url(BASE_URL + s"v1/accounts/${accountsId}/invitations/${invitationsId}:decline").addQueryStringParameters("name" -> name.toString))
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListInvitationsResponse]) {
				/** Optional. Filtering the response is supported via the Invitation.target_type field. */
				def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListInvitationsResponse])
			}
			object list {
				def apply(accountsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/accounts/${accountsId}/invitations").addQueryStringParameters("parent" -> parent.toString))
				given Conversion[list, Future[Schema.ListInvitationsResponse]] = (fun: list) => fun.apply()
			}
		}
	}
}
