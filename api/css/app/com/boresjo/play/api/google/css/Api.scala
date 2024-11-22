package com.boresjo.play.api.google.css

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
		"""https://www.googleapis.com/auth/content""" /* Manage your product listings and accounts for Google Shopping */
	)

	private val BASE_URL = "https://css.googleapis.com/"

	object accounts {
		/** Updates labels assigned to CSS/MC accounts by a CSS domain. */
		class updateLabels(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/content""")
			/** Perform the request */
			def withUpdateAccountLabelsRequest(body: Schema.UpdateAccountLabelsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Account])
		}
		object updateLabels {
			def apply(accountsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): updateLabels = new updateLabels(ws.url(BASE_URL + s"v1/accounts/${accountsId}:updateLabels").addQueryStringParameters("name" -> name.toString))
		}
		/** Lists all the accounts under the specified CSS account ID, and optionally filters by label ID and account name. */
		class listChildAccounts(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListChildAccountsResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/content""")
			/** Optional. The maximum number of accounts to return. The service may return fewer than this value. If unspecified, at most 50 accounts will be returned. The maximum value is 1000; values above 1000 will be coerced to 1000.<br>Format: int32 */
			def withPageSize(pageSize: Int) = new listChildAccounts(req.addQueryStringParameters("pageSize" -> pageSize.toString))
			/** Optional. A page token, received from a previous `ListChildAccounts` call. Provide this to retrieve the subsequent page. When paginating, all other parameters provided to `ListChildAccounts` must match the call that provided the page token. */
			def withPageToken(pageToken: String) = new listChildAccounts(req.addQueryStringParameters("pageToken" -> pageToken.toString))
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListChildAccountsResponse])
		}
		object listChildAccounts {
			def apply(accountsId :PlayApi, parent: String, labelId: String, fullName: String)(using signer: RequestSigner, ec: ExecutionContext): listChildAccounts = new listChildAccounts(ws.url(BASE_URL + s"v1/accounts/${accountsId}:listChildAccounts").addQueryStringParameters("parent" -> parent.toString, "labelId" -> labelId.toString, "fullName" -> fullName.toString))
			given Conversion[listChildAccounts, Future[Schema.ListChildAccountsResponse]] = (fun: listChildAccounts) => fun.apply()
		}
		/** Retrieves a single CSS/MC account by ID. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Account]) {
			val scopes = Seq("""https://www.googleapis.com/auth/content""")
			/** Optional. Only required when retrieving MC account information. The CSS domain that is the parent resource of the MC account. Format: accounts/{account} */
			def withParent(parent: String) = new get(req.addQueryStringParameters("parent" -> parent.toString))
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Account])
		}
		object get {
			def apply(accountsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/accounts/${accountsId}").addQueryStringParameters("name" -> name.toString))
			given Conversion[get, Future[Schema.Account]] = (fun: get) => fun.apply()
		}
		object cssProductInputs {
			/** Uploads a CssProductInput to your CSS Center account. If an input with the same contentLanguage, identity, feedLabel and feedId already exists, this method replaces that entry. After inserting, updating, or deleting a CSS Product input, it may take several minutes before the processed CSS Product can be retrieved. */
			class insert(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/content""")
				/** Perform the request */
				def withCssProductInput(body: Schema.CssProductInput) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.CssProductInput])
			}
			object insert {
				def apply(accountsId :PlayApi, parent: String, feedId: String)(using signer: RequestSigner, ec: ExecutionContext): insert = new insert(ws.url(BASE_URL + s"v1/accounts/${accountsId}/cssProductInputs:insert").addQueryStringParameters("parent" -> parent.toString, "feedId" -> feedId.toString))
			}
			/** Deletes a CSS Product input from your CSS Center account. After a delete it may take several minutes until the input is no longer available. */
			class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
				val scopes = Seq("""https://www.googleapis.com/auth/content""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
			}
			object delete {
				def apply(accountsId :PlayApi, cssProductInputsId :PlayApi, name: String, supplementalFeedId: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/accounts/${accountsId}/cssProductInputs/${cssProductInputsId}").addQueryStringParameters("name" -> name.toString, "supplementalFeedId" -> supplementalFeedId.toString))
				given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
			}
		}
		object labels {
			/** Lists the labels owned by an account. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListAccountLabelsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/content""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListAccountLabelsResponse])
			}
			object list {
				def apply(accountsId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/accounts/${accountsId}/labels").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListAccountLabelsResponse]] = (fun: list) => fun.apply()
			}
			/** Creates a new label, not assigned to any account. */
			class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/content""")
				/** Perform the request */
				def withAccountLabel(body: Schema.AccountLabel) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.AccountLabel])
			}
			object create {
				def apply(accountsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/accounts/${accountsId}/labels").addQueryStringParameters("parent" -> parent.toString))
			}
			/** Updates a label. */
			class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/content""")
				/** Perform the request */
				def withAccountLabel(body: Schema.AccountLabel) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.AccountLabel])
			}
			object patch {
				def apply(accountsId :PlayApi, labelsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/accounts/${accountsId}/labels/${labelsId}").addQueryStringParameters("name" -> name.toString))
			}
			/** Deletes a label and removes it from all accounts to which it was assigned. */
			class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
				val scopes = Seq("""https://www.googleapis.com/auth/content""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
			}
			object delete {
				def apply(accountsId :PlayApi, labelsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/accounts/${accountsId}/labels/${labelsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
			}
		}
		object cssProducts {
			/** Retrieves the processed CSS Product from your CSS Center account. After inserting, updating, or deleting a product input, it may take several minutes before the updated final product can be retrieved. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.CssProduct]) {
				val scopes = Seq("""https://www.googleapis.com/auth/content""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.CssProduct])
			}
			object get {
				def apply(accountsId :PlayApi, cssProductsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/accounts/${accountsId}/cssProducts/${cssProductsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.CssProduct]] = (fun: get) => fun.apply()
			}
			/** Lists the processed CSS Products in your CSS Center account. The response might contain fewer items than specified by pageSize. Rely on pageToken to determine if there are more items to be requested. After inserting, updating, or deleting a CSS product input, it may take several minutes before the updated processed CSS product can be retrieved. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListCssProductsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/content""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListCssProductsResponse])
			}
			object list {
				def apply(accountsId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/accounts/${accountsId}/cssProducts").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListCssProductsResponse]] = (fun: list) => fun.apply()
			}
		}
	}
}
