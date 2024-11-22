package com.boresjo.play.api.google.css

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://css.googleapis.com/"

	object accounts {
		class updateLabels(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withUpdateAccountLabelsRequest(body: Schema.UpdateAccountLabelsRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Account])
		}
		object updateLabels {
			def apply(accountsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): updateLabels = new updateLabels(ws.url(BASE_URL + s"v1/accounts/${accountsId}:updateLabels").addQueryStringParameters("name" -> name.toString))
		}
		class listChildAccounts(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListChildAccountsResponse]) {
			/** Optional. The maximum number of accounts to return. The service may return fewer than this value. If unspecified, at most 50 accounts will be returned. The maximum value is 1000; values above 1000 will be coerced to 1000.<br>Format: int32 */
			def withPageSize(pageSize: Int) = new listChildAccounts(req.addQueryStringParameters("pageSize" -> pageSize.toString))
			/** Optional. A page token, received from a previous `ListChildAccounts` call. Provide this to retrieve the subsequent page. When paginating, all other parameters provided to `ListChildAccounts` must match the call that provided the page token. */
			def withPageToken(pageToken: String) = new listChildAccounts(req.addQueryStringParameters("pageToken" -> pageToken.toString))
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListChildAccountsResponse])
		}
		object listChildAccounts {
			def apply(accountsId :PlayApi, parent: String, labelId: String, fullName: String)(using auth: AuthToken, ec: ExecutionContext): listChildAccounts = new listChildAccounts(ws.url(BASE_URL + s"v1/accounts/${accountsId}:listChildAccounts").addQueryStringParameters("parent" -> parent.toString, "labelId" -> labelId.toString, "fullName" -> fullName.toString))
			given Conversion[listChildAccounts, Future[Schema.ListChildAccountsResponse]] = (fun: listChildAccounts) => fun.apply()
		}
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Account]) {
			/** Optional. Only required when retrieving MC account information. The CSS domain that is the parent resource of the MC account. Format: accounts/{account} */
			def withParent(parent: String) = new get(req.addQueryStringParameters("parent" -> parent.toString))
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Account])
		}
		object get {
			def apply(accountsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/accounts/${accountsId}").addQueryStringParameters("name" -> name.toString))
			given Conversion[get, Future[Schema.Account]] = (fun: get) => fun.apply()
		}
		object cssProductInputs {
			class insert(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withCssProductInput(body: Schema.CssProductInput) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.CssProductInput])
			}
			object insert {
				def apply(accountsId :PlayApi, parent: String, feedId: String)(using auth: AuthToken, ec: ExecutionContext): insert = new insert(ws.url(BASE_URL + s"v1/accounts/${accountsId}/cssProductInputs:insert").addQueryStringParameters("parent" -> parent.toString, "feedId" -> feedId.toString))
			}
			class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
				def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
			}
			object delete {
				def apply(accountsId :PlayApi, cssProductInputsId :PlayApi, name: String, supplementalFeedId: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/accounts/${accountsId}/cssProductInputs/${cssProductInputsId}").addQueryStringParameters("name" -> name.toString, "supplementalFeedId" -> supplementalFeedId.toString))
				given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
			}
		}
		object labels {
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListAccountLabelsResponse]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListAccountLabelsResponse])
			}
			object list {
				def apply(accountsId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/accounts/${accountsId}/labels").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListAccountLabelsResponse]] = (fun: list) => fun.apply()
			}
			class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withAccountLabel(body: Schema.AccountLabel) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.AccountLabel])
			}
			object create {
				def apply(accountsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/accounts/${accountsId}/labels").addQueryStringParameters("parent" -> parent.toString))
			}
			class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withAccountLabel(body: Schema.AccountLabel) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.AccountLabel])
			}
			object patch {
				def apply(accountsId :PlayApi, labelsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/accounts/${accountsId}/labels/${labelsId}").addQueryStringParameters("name" -> name.toString))
			}
			class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
				def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
			}
			object delete {
				def apply(accountsId :PlayApi, labelsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/accounts/${accountsId}/labels/${labelsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
			}
		}
		object cssProducts {
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.CssProduct]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.CssProduct])
			}
			object get {
				def apply(accountsId :PlayApi, cssProductsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/accounts/${accountsId}/cssProducts/${cssProductsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.CssProduct]] = (fun: get) => fun.apply()
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListCssProductsResponse]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListCssProductsResponse])
			}
			object list {
				def apply(accountsId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/accounts/${accountsId}/cssProducts").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListCssProductsResponse]] = (fun: list) => fun.apply()
			}
		}
	}
}
