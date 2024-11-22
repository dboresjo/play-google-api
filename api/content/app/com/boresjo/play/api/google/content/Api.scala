package com.boresjo.play.api.google.content

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

	private val BASE_URL = "https://shoppingcontent.googleapis.com/content/v2.1/"

	object accounts {
		/** Creates a Merchant Center sub-account. */
		class insert(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/content""")
			/** Perform the request */
			def withAccount(body: Schema.Account) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Account])
		}
		object insert {
			def apply(merchantId: String)(using signer: RequestSigner, ec: ExecutionContext): insert = new insert(ws.url(BASE_URL + s"${merchantId}/accounts").addQueryStringParameters())
		}
		/** Claims the website of a Merchant Center sub-account. Merchant accounts with approved third-party CSSs aren't required to claim a website. */
		class claimwebsite(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.AccountsClaimWebsiteResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/content""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("POST")).map(_.json.as[Schema.AccountsClaimWebsiteResponse])
		}
		object claimwebsite {
			def apply(merchantId: String, accountId: String, overwrite: Boolean)(using signer: RequestSigner, ec: ExecutionContext): claimwebsite = new claimwebsite(ws.url(BASE_URL + s"${merchantId}/accounts/${accountId}/claimwebsite").addQueryStringParameters("overwrite" -> overwrite.toString))
			given Conversion[claimwebsite, Future[Schema.AccountsClaimWebsiteResponse]] = (fun: claimwebsite) => fun.apply()
		}
		/** Updates labels that are assigned to the Merchant Center account by CSS user. */
		class updatelabels(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/content""")
			/** Perform the request */
			def withAccountsUpdateLabelsRequest(body: Schema.AccountsUpdateLabelsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.AccountsUpdateLabelsResponse])
		}
		object updatelabels {
			def apply(merchantId: String, accountId: String)(using signer: RequestSigner, ec: ExecutionContext): updatelabels = new updatelabels(ws.url(BASE_URL + s"${merchantId}/accounts/${accountId}/updatelabels").addQueryStringParameters())
		}
		/** Request verification code to start phone verification. */
		class requestphoneverification(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/content""")
			/** Perform the request */
			def withRequestPhoneVerificationRequest(body: Schema.RequestPhoneVerificationRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.RequestPhoneVerificationResponse])
		}
		object requestphoneverification {
			def apply(merchantId: String, accountId: String)(using signer: RequestSigner, ec: ExecutionContext): requestphoneverification = new requestphoneverification(ws.url(BASE_URL + s"${merchantId}/accounts/${accountId}/requestphoneverification").addQueryStringParameters())
		}
		/** Retrieves, inserts, updates, and deletes multiple Merchant Center (sub-)accounts in a single request. */
		class custombatch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/content""")
			/** Perform the request */
			def withAccountsCustomBatchRequest(body: Schema.AccountsCustomBatchRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.AccountsCustomBatchResponse])
		}
		object custombatch {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): custombatch = new custombatch(ws.url(BASE_URL + s"accounts/batch").addQueryStringParameters())
		}
		/** Returns information about the authenticated user. */
		class authinfo(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.AccountsAuthInfoResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/content""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.AccountsAuthInfoResponse])
		}
		object authinfo {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): authinfo = new authinfo(ws.url(BASE_URL + s"accounts/authinfo").addQueryStringParameters())
			given Conversion[authinfo, Future[Schema.AccountsAuthInfoResponse]] = (fun: authinfo) => fun.apply()
		}
		/** Retrieves a Merchant Center account. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Account]) {
			val scopes = Seq("""https://www.googleapis.com/auth/content""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Account])
		}
		object get {
			def apply(merchantId: String, accountId: String, view: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"${merchantId}/accounts/${accountId}").addQueryStringParameters("view" -> view.toString))
			given Conversion[get, Future[Schema.Account]] = (fun: get) => fun.apply()
		}
		/** Updates a Merchant Center account. Any fields that are not provided are deleted from the resource. */
		class update(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/content""")
			/** Perform the request */
			def withAccount(body: Schema.Account) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.Account])
		}
		object update {
			def apply(merchantId: String, accountId: String)(using signer: RequestSigner, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"${merchantId}/accounts/${accountId}").addQueryStringParameters())
		}
		/** Returns the list of accounts linked to your Merchant Center account. */
		class listlinks(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.AccountsListLinksResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/content""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.AccountsListLinksResponse])
		}
		object listlinks {
			def apply(merchantId: String, accountId: String, maxResults: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): listlinks = new listlinks(ws.url(BASE_URL + s"${merchantId}/accounts/${accountId}/listlinks").addQueryStringParameters("maxResults" -> maxResults.toString, "pageToken" -> pageToken.toString))
			given Conversion[listlinks, Future[Schema.AccountsListLinksResponse]] = (fun: listlinks) => fun.apply()
		}
		/** Performs an action on a link between two Merchant Center accounts, namely accountId and linkedAccountId. */
		class link(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/content""")
			/** Perform the request */
			def withAccountsLinkRequest(body: Schema.AccountsLinkRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.AccountsLinkResponse])
		}
		object link {
			def apply(merchantId: String, accountId: String)(using signer: RequestSigner, ec: ExecutionContext): link = new link(ws.url(BASE_URL + s"${merchantId}/accounts/${accountId}/link").addQueryStringParameters())
		}
		/** Deletes a Merchant Center sub-account. */
		class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Unit]) {
			val scopes = Seq("""https://www.googleapis.com/auth/content""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_ => ())
		}
		object delete {
			def apply(merchantId: String, accountId: String, force: Boolean)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"${merchantId}/accounts/${accountId}").addQueryStringParameters("force" -> force.toString))
			given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
		}
		/** Lists the sub-accounts in your Merchant Center account. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.AccountsListResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/content""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.AccountsListResponse])
		}
		object list {
			def apply(merchantId: String, maxResults: Int, pageToken: String, view: String, label: String, name: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"${merchantId}/accounts").addQueryStringParameters("maxResults" -> maxResults.toString, "pageToken" -> pageToken.toString, "view" -> view.toString, "label" -> label.toString, "name" -> name.toString))
			given Conversion[list, Future[Schema.AccountsListResponse]] = (fun: list) => fun.apply()
		}
		/** Validates verification code to verify phone number for the account. If successful this will overwrite the value of `accounts.businessinformation.phoneNumber`. Only verified phone number will replace an existing verified phone number. */
		class verifyphonenumber(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/content""")
			/** Perform the request */
			def withVerifyPhoneNumberRequest(body: Schema.VerifyPhoneNumberRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.VerifyPhoneNumberResponse])
		}
		object verifyphonenumber {
			def apply(merchantId: String, accountId: String)(using signer: RequestSigner, ec: ExecutionContext): verifyphonenumber = new verifyphonenumber(ws.url(BASE_URL + s"${merchantId}/accounts/${accountId}/verifyphonenumber").addQueryStringParameters())
		}
		object labels {
			/** Lists the labels assigned to an account. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListAccountLabelsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/content""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListAccountLabelsResponse])
			}
			object list {
				def apply(accountId: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"accounts/${accountId}/labels").addQueryStringParameters("pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListAccountLabelsResponse]] = (fun: list) => fun.apply()
			}
			/** Creates a new label, not assigned to any account. */
			class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/content""")
				/** Perform the request */
				def withAccountLabel(body: Schema.AccountLabel) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.AccountLabel])
			}
			object create {
				def apply(accountId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"accounts/${accountId}/labels").addQueryStringParameters())
			}
			/** Updates a label. */
			class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/content""")
				/** Perform the request */
				def withAccountLabel(body: Schema.AccountLabel) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.AccountLabel])
			}
			object patch {
				def apply(accountId: String, labelId: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"accounts/${accountId}/labels/${labelId}").addQueryStringParameters())
			}
			/** Deletes a label and removes it from all accounts to which it was assigned. */
			class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Unit]) {
				val scopes = Seq("""https://www.googleapis.com/auth/content""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_ => ())
			}
			object delete {
				def apply(accountId: String, labelId: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"accounts/${accountId}/labels/${labelId}").addQueryStringParameters())
				given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
			}
		}
		object credentials {
			/** Uploads credentials for the Merchant Center account. If credentials already exist for this Merchant Center account and purpose, this method updates them. */
			class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/content""")
				/** Perform the request */
				def withAccountCredentials(body: Schema.AccountCredentials) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.AccountCredentials])
			}
			object create {
				def apply(accountId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"accounts/${accountId}/credentials").addQueryStringParameters())
			}
		}
		object returncarrier {
			/** Links return carrier to a merchant account. */
			class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/content""")
				/** Perform the request */
				def withAccountReturnCarrier(body: Schema.AccountReturnCarrier) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.AccountReturnCarrier])
			}
			object create {
				def apply(accountId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"accounts/${accountId}/returncarrier").addQueryStringParameters())
			}
			/** Updates a return carrier in the merchant account. */
			class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/content""")
				/** Perform the request */
				def withAccountReturnCarrier(body: Schema.AccountReturnCarrier) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.AccountReturnCarrier])
			}
			object patch {
				def apply(accountId: String, carrierAccountId: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"accounts/${accountId}/returncarrier/${carrierAccountId}").addQueryStringParameters())
			}
			/** Delete a return carrier in the merchant account. */
			class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Unit]) {
				val scopes = Seq("""https://www.googleapis.com/auth/content""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_ => ())
			}
			object delete {
				def apply(accountId: String, carrierAccountId: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"accounts/${accountId}/returncarrier/${carrierAccountId}").addQueryStringParameters())
				given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
			}
			/** Lists available return carriers in the merchant account. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListAccountReturnCarrierResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/content""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListAccountReturnCarrierResponse])
			}
			object list {
				def apply(accountId: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"accounts/${accountId}/returncarrier").addQueryStringParameters())
				given Conversion[list, Future[Schema.ListAccountReturnCarrierResponse]] = (fun: list) => fun.apply()
			}
		}
	}
	object accountstatuses {
		/** Retrieves multiple Merchant Center account statuses in a single request. */
		class custombatch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/content""")
			/** Perform the request */
			def withAccountstatusesCustomBatchRequest(body: Schema.AccountstatusesCustomBatchRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.AccountstatusesCustomBatchResponse])
		}
		object custombatch {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): custombatch = new custombatch(ws.url(BASE_URL + s"accountstatuses/batch").addQueryStringParameters())
		}
		/** Retrieves the status of a Merchant Center account. No itemLevelIssues are returned for multi-client accounts. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.AccountStatus]) {
			val scopes = Seq("""https://www.googleapis.com/auth/content""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.AccountStatus])
		}
		object get {
			def apply(merchantId: String, accountId: String, destinations: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"${merchantId}/accountstatuses/${accountId}").addQueryStringParameters("destinations" -> destinations.toString))
			given Conversion[get, Future[Schema.AccountStatus]] = (fun: get) => fun.apply()
		}
		/** Lists the statuses of the sub-accounts in your Merchant Center account. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.AccountstatusesListResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/content""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.AccountstatusesListResponse])
		}
		object list {
			def apply(merchantId: String, maxResults: Int, pageToken: String, destinations: String, name: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"${merchantId}/accountstatuses").addQueryStringParameters("maxResults" -> maxResults.toString, "pageToken" -> pageToken.toString, "destinations" -> destinations.toString, "name" -> name.toString))
			given Conversion[list, Future[Schema.AccountstatusesListResponse]] = (fun: list) => fun.apply()
		}
	}
	object accounttax {
		/** Retrieves and updates tax settings of multiple accounts in a single request. */
		class custombatch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/content""")
			/** Perform the request */
			def withAccounttaxCustomBatchRequest(body: Schema.AccounttaxCustomBatchRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.AccounttaxCustomBatchResponse])
		}
		object custombatch {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): custombatch = new custombatch(ws.url(BASE_URL + s"accounttax/batch").addQueryStringParameters())
		}
		/** Retrieves the tax settings of the account. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.AccountTax]) {
			val scopes = Seq("""https://www.googleapis.com/auth/content""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.AccountTax])
		}
		object get {
			def apply(merchantId: String, accountId: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"${merchantId}/accounttax/${accountId}").addQueryStringParameters())
			given Conversion[get, Future[Schema.AccountTax]] = (fun: get) => fun.apply()
		}
		/** Lists the tax settings of the sub-accounts in your Merchant Center account. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.AccounttaxListResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/content""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.AccounttaxListResponse])
		}
		object list {
			def apply(merchantId: String, maxResults: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"${merchantId}/accounttax").addQueryStringParameters("maxResults" -> maxResults.toString, "pageToken" -> pageToken.toString))
			given Conversion[list, Future[Schema.AccounttaxListResponse]] = (fun: list) => fun.apply()
		}
		/** Updates the tax settings of the account. Any fields that are not provided are deleted from the resource. */
		class update(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/content""")
			/** Perform the request */
			def withAccountTax(body: Schema.AccountTax) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.AccountTax])
		}
		object update {
			def apply(merchantId: String, accountId: String)(using signer: RequestSigner, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"${merchantId}/accounttax/${accountId}").addQueryStringParameters())
		}
	}
	object datafeeds {
		/** Registers a datafeed configuration with your Merchant Center account. */
		class insert(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/content""")
			/** Perform the request */
			def withDatafeed(body: Schema.Datafeed) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Datafeed])
		}
		object insert {
			def apply(merchantId: String)(using signer: RequestSigner, ec: ExecutionContext): insert = new insert(ws.url(BASE_URL + s"${merchantId}/datafeeds").addQueryStringParameters())
		}
		/** Invokes a fetch for the datafeed in your Merchant Center account. If you need to call this method more than once per day, we recommend you use the [Products service](https://developers.google.com/shopping-content/reference/rest/v2.1/products) to update your product data. */
		class fetchnow(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.DatafeedsFetchNowResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/content""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("POST")).map(_.json.as[Schema.DatafeedsFetchNowResponse])
		}
		object fetchnow {
			def apply(merchantId: String, datafeedId: String)(using signer: RequestSigner, ec: ExecutionContext): fetchnow = new fetchnow(ws.url(BASE_URL + s"${merchantId}/datafeeds/${datafeedId}/fetchNow").addQueryStringParameters())
			given Conversion[fetchnow, Future[Schema.DatafeedsFetchNowResponse]] = (fun: fetchnow) => fun.apply()
		}
		/** Deletes, fetches, gets, inserts and updates multiple datafeeds in a single request. */
		class custombatch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/content""")
			/** Perform the request */
			def withDatafeedsCustomBatchRequest(body: Schema.DatafeedsCustomBatchRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.DatafeedsCustomBatchResponse])
		}
		object custombatch {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): custombatch = new custombatch(ws.url(BASE_URL + s"datafeeds/batch").addQueryStringParameters())
		}
		/** Deletes a datafeed configuration from your Merchant Center account. */
		class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Unit]) {
			val scopes = Seq("""https://www.googleapis.com/auth/content""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_ => ())
		}
		object delete {
			def apply(merchantId: String, datafeedId: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"${merchantId}/datafeeds/${datafeedId}").addQueryStringParameters())
			given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
		}
		/** Retrieves a datafeed configuration from your Merchant Center account. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Datafeed]) {
			val scopes = Seq("""https://www.googleapis.com/auth/content""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Datafeed])
		}
		object get {
			def apply(merchantId: String, datafeedId: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"${merchantId}/datafeeds/${datafeedId}").addQueryStringParameters())
			given Conversion[get, Future[Schema.Datafeed]] = (fun: get) => fun.apply()
		}
		/** Updates a datafeed configuration of your Merchant Center account. Any fields that are not provided are deleted from the resource. */
		class update(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/content""")
			/** Perform the request */
			def withDatafeed(body: Schema.Datafeed) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.Datafeed])
		}
		object update {
			def apply(merchantId: String, datafeedId: String)(using signer: RequestSigner, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"${merchantId}/datafeeds/${datafeedId}").addQueryStringParameters())
		}
		/** Lists the configurations for datafeeds in your Merchant Center account. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.DatafeedsListResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/content""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.DatafeedsListResponse])
		}
		object list {
			def apply(merchantId: String, maxResults: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"${merchantId}/datafeeds").addQueryStringParameters("maxResults" -> maxResults.toString, "pageToken" -> pageToken.toString))
			given Conversion[list, Future[Schema.DatafeedsListResponse]] = (fun: list) => fun.apply()
		}
	}
	object datafeedstatuses {
		/** Gets multiple Merchant Center datafeed statuses in a single request. */
		class custombatch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/content""")
			/** Perform the request */
			def withDatafeedstatusesCustomBatchRequest(body: Schema.DatafeedstatusesCustomBatchRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.DatafeedstatusesCustomBatchResponse])
		}
		object custombatch {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): custombatch = new custombatch(ws.url(BASE_URL + s"datafeedstatuses/batch").addQueryStringParameters())
		}
		/** Retrieves the status of a datafeed from your Merchant Center account. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.DatafeedStatus]) {
			val scopes = Seq("""https://www.googleapis.com/auth/content""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.DatafeedStatus])
		}
		object get {
			def apply(merchantId: String, datafeedId: String, country: String, feedLabel: String, language: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"${merchantId}/datafeedstatuses/${datafeedId}").addQueryStringParameters("country" -> country.toString, "feedLabel" -> feedLabel.toString, "language" -> language.toString))
			given Conversion[get, Future[Schema.DatafeedStatus]] = (fun: get) => fun.apply()
		}
		/** Lists the statuses of the datafeeds in your Merchant Center account. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.DatafeedstatusesListResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/content""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.DatafeedstatusesListResponse])
		}
		object list {
			def apply(merchantId: String, maxResults: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"${merchantId}/datafeedstatuses").addQueryStringParameters("maxResults" -> maxResults.toString, "pageToken" -> pageToken.toString))
			given Conversion[list, Future[Schema.DatafeedstatusesListResponse]] = (fun: list) => fun.apply()
		}
	}
	object liasettings {
		/** Sets the omnichannel experience for the specified country. Only supported for merchants whose POS data provider is trusted to enable the corresponding experience. For more context, see these help articles [about LFP](https://support.google.com/merchants/answer/7676652) and [how to get started](https://support.google.com/merchants/answer/7676578) with it. */
		class setomnichannelexperience(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.LiaOmnichannelExperience]) {
			val scopes = Seq("""https://www.googleapis.com/auth/content""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("POST")).map(_.json.as[Schema.LiaOmnichannelExperience])
		}
		object setomnichannelexperience {
			def apply(merchantId: String, accountId: String, country: String, lsfType: String, pickupTypes: String)(using signer: RequestSigner, ec: ExecutionContext): setomnichannelexperience = new setomnichannelexperience(ws.url(BASE_URL + s"${merchantId}/liasettings/${accountId}/setomnichannelexperience").addQueryStringParameters("country" -> country.toString, "lsfType" -> lsfType.toString, "pickupTypes" -> pickupTypes.toString))
			given Conversion[setomnichannelexperience, Future[Schema.LiaOmnichannelExperience]] = (fun: setomnichannelexperience) => fun.apply()
		}
		/** Requests inventory validation for the specified country. */
		class requestinventoryverification(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.LiasettingsRequestInventoryVerificationResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/content""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("POST")).map(_.json.as[Schema.LiasettingsRequestInventoryVerificationResponse])
		}
		object requestinventoryverification {
			def apply(merchantId: String, accountId: String, country: String)(using signer: RequestSigner, ec: ExecutionContext): requestinventoryverification = new requestinventoryverification(ws.url(BASE_URL + s"${merchantId}/liasettings/${accountId}/requestinventoryverification/${country}").addQueryStringParameters())
			given Conversion[requestinventoryverification, Future[Schema.LiasettingsRequestInventoryVerificationResponse]] = (fun: requestinventoryverification) => fun.apply()
		}
		/** Retrieves the list of accessible Business Profiles. */
		class getaccessiblegmbaccounts(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.LiasettingsGetAccessibleGmbAccountsResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/content""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.LiasettingsGetAccessibleGmbAccountsResponse])
		}
		object getaccessiblegmbaccounts {
			def apply(merchantId: String, accountId: String)(using signer: RequestSigner, ec: ExecutionContext): getaccessiblegmbaccounts = new getaccessiblegmbaccounts(ws.url(BASE_URL + s"${merchantId}/liasettings/${accountId}/accessiblegmbaccounts").addQueryStringParameters())
			given Conversion[getaccessiblegmbaccounts, Future[Schema.LiasettingsGetAccessibleGmbAccountsResponse]] = (fun: getaccessiblegmbaccounts) => fun.apply()
		}
		/** Retrieves and/or updates the LIA settings of multiple accounts in a single request. */
		class custombatch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/content""")
			/** Perform the request */
			def withLiasettingsCustomBatchRequest(body: Schema.LiasettingsCustomBatchRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.LiasettingsCustomBatchResponse])
		}
		object custombatch {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): custombatch = new custombatch(ws.url(BASE_URL + s"liasettings/batch").addQueryStringParameters())
		}
		/** Requests access to a specified Business Profile. */
		class requestgmbaccess(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.LiasettingsRequestGmbAccessResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/content""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("POST")).map(_.json.as[Schema.LiasettingsRequestGmbAccessResponse])
		}
		object requestgmbaccess {
			def apply(merchantId: String, accountId: String, gmbEmail: String)(using signer: RequestSigner, ec: ExecutionContext): requestgmbaccess = new requestgmbaccess(ws.url(BASE_URL + s"${merchantId}/liasettings/${accountId}/requestgmbaccess").addQueryStringParameters("gmbEmail" -> gmbEmail.toString))
			given Conversion[requestgmbaccess, Future[Schema.LiasettingsRequestGmbAccessResponse]] = (fun: requestgmbaccess) => fun.apply()
		}
		/** Retrieves the list of POS data providers that have active settings for the all eiligible countries. */
		class listposdataproviders(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.LiasettingsListPosDataProvidersResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/content""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.LiasettingsListPosDataProvidersResponse])
		}
		object listposdataproviders {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): listposdataproviders = new listposdataproviders(ws.url(BASE_URL + s"liasettings/posdataproviders").addQueryStringParameters())
			given Conversion[listposdataproviders, Future[Schema.LiasettingsListPosDataProvidersResponse]] = (fun: listposdataproviders) => fun.apply()
		}
		/** Retrieves the LIA settings of the account. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.LiaSettings]) {
			val scopes = Seq("""https://www.googleapis.com/auth/content""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.LiaSettings])
		}
		object get {
			def apply(merchantId: String, accountId: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"${merchantId}/liasettings/${accountId}").addQueryStringParameters())
			given Conversion[get, Future[Schema.LiaSettings]] = (fun: get) => fun.apply()
		}
		/** Updates the LIA settings of the account. Any fields that are not provided are deleted from the resource. */
		class update(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/content""")
			/** Perform the request */
			def withLiaSettings(body: Schema.LiaSettings) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.LiaSettings])
		}
		object update {
			def apply(merchantId: String, accountId: String)(using signer: RequestSigner, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"${merchantId}/liasettings/${accountId}").addQueryStringParameters())
		}
		/** Lists the LIA settings of the sub-accounts in your Merchant Center account. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.LiasettingsListResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/content""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.LiasettingsListResponse])
		}
		object list {
			def apply(merchantId: String, maxResults: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"${merchantId}/liasettings").addQueryStringParameters("maxResults" -> maxResults.toString, "pageToken" -> pageToken.toString))
			given Conversion[list, Future[Schema.LiasettingsListResponse]] = (fun: list) => fun.apply()
		}
		/** Sets the POS data provider for the specified country. */
		class setposdataprovider(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.LiasettingsSetPosDataProviderResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/content""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("POST")).map(_.json.as[Schema.LiasettingsSetPosDataProviderResponse])
		}
		object setposdataprovider {
			def apply(merchantId: String, accountId: String, country: String, posDataProviderId: String, posExternalAccountId: String)(using signer: RequestSigner, ec: ExecutionContext): setposdataprovider = new setposdataprovider(ws.url(BASE_URL + s"${merchantId}/liasettings/${accountId}/setposdataprovider").addQueryStringParameters("country" -> country.toString, "posDataProviderId" -> posDataProviderId.toString, "posExternalAccountId" -> posExternalAccountId.toString))
			given Conversion[setposdataprovider, Future[Schema.LiasettingsSetPosDataProviderResponse]] = (fun: setposdataprovider) => fun.apply()
		}
		/** Sets the inventory verification contract for the specified country. */
		class setinventoryverificationcontact(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.LiasettingsSetInventoryVerificationContactResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/content""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("POST")).map(_.json.as[Schema.LiasettingsSetInventoryVerificationContactResponse])
		}
		object setinventoryverificationcontact {
			def apply(merchantId: String, accountId: String, country: String, language: String, contactName: String, contactEmail: String)(using signer: RequestSigner, ec: ExecutionContext): setinventoryverificationcontact = new setinventoryverificationcontact(ws.url(BASE_URL + s"${merchantId}/liasettings/${accountId}/setinventoryverificationcontact").addQueryStringParameters("country" -> country.toString, "language" -> language.toString, "contactName" -> contactName.toString, "contactEmail" -> contactEmail.toString))
			given Conversion[setinventoryverificationcontact, Future[Schema.LiasettingsSetInventoryVerificationContactResponse]] = (fun: setinventoryverificationcontact) => fun.apply()
		}
	}
	object localinventory {
		/** Updates local inventory for multiple products or stores in a single request. */
		class custombatch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/content""")
			/** Perform the request */
			def withLocalinventoryCustomBatchRequest(body: Schema.LocalinventoryCustomBatchRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.LocalinventoryCustomBatchResponse])
		}
		object custombatch {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): custombatch = new custombatch(ws.url(BASE_URL + s"localinventory/batch").addQueryStringParameters())
		}
		/** Updates the local inventory of a product in your Merchant Center account. */
		class insert(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/content""")
			/** Perform the request */
			def withLocalInventory(body: Schema.LocalInventory) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.LocalInventory])
		}
		object insert {
			def apply(merchantId: String, productId: String)(using signer: RequestSigner, ec: ExecutionContext): insert = new insert(ws.url(BASE_URL + s"${merchantId}/products/${productId}/localinventory").addQueryStringParameters())
		}
	}
	object pos {
		/** Submit inventory for the given merchant. */
		class inventory(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/content""")
			/** Perform the request */
			def withPosInventoryRequest(body: Schema.PosInventoryRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.PosInventoryResponse])
		}
		object inventory {
			def apply(merchantId: String, targetMerchantId: String)(using signer: RequestSigner, ec: ExecutionContext): inventory = new inventory(ws.url(BASE_URL + s"${merchantId}/pos/${targetMerchantId}/inventory").addQueryStringParameters())
		}
		/** Creates a store for the given merchant. */
		class insert(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/content""")
			/** Perform the request */
			def withPosStore(body: Schema.PosStore) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.PosStore])
		}
		object insert {
			def apply(merchantId: String, targetMerchantId: String)(using signer: RequestSigner, ec: ExecutionContext): insert = new insert(ws.url(BASE_URL + s"${merchantId}/pos/${targetMerchantId}/store").addQueryStringParameters())
		}
		/** Submit a sale event for the given merchant. */
		class sale(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/content""")
			/** Perform the request */
			def withPosSaleRequest(body: Schema.PosSaleRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.PosSaleResponse])
		}
		object sale {
			def apply(merchantId: String, targetMerchantId: String)(using signer: RequestSigner, ec: ExecutionContext): sale = new sale(ws.url(BASE_URL + s"${merchantId}/pos/${targetMerchantId}/sale").addQueryStringParameters())
		}
		/** Batches multiple POS-related calls in a single request. */
		class custombatch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/content""")
			/** Perform the request */
			def withPosCustomBatchRequest(body: Schema.PosCustomBatchRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.PosCustomBatchResponse])
		}
		object custombatch {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): custombatch = new custombatch(ws.url(BASE_URL + s"pos/batch").addQueryStringParameters())
		}
		/** Deletes a store for the given merchant. */
		class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Unit]) {
			val scopes = Seq("""https://www.googleapis.com/auth/content""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_ => ())
		}
		object delete {
			def apply(merchantId: String, targetMerchantId: String, storeCode: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"${merchantId}/pos/${targetMerchantId}/store/${storeCode}").addQueryStringParameters())
			given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
		}
		/** Retrieves information about the given store. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.PosStore]) {
			val scopes = Seq("""https://www.googleapis.com/auth/content""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.PosStore])
		}
		object get {
			def apply(merchantId: String, targetMerchantId: String, storeCode: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"${merchantId}/pos/${targetMerchantId}/store/${storeCode}").addQueryStringParameters())
			given Conversion[get, Future[Schema.PosStore]] = (fun: get) => fun.apply()
		}
		/** Lists the stores of the target merchant. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.PosListResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/content""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.PosListResponse])
		}
		object list {
			def apply(merchantId: String, targetMerchantId: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"${merchantId}/pos/${targetMerchantId}/store").addQueryStringParameters())
			given Conversion[list, Future[Schema.PosListResponse]] = (fun: list) => fun.apply()
		}
	}
	object products {
		/** Uploads a product to your Merchant Center account. If an item with the same channel, contentLanguage, offerId, and targetCountry already exists, this method updates that entry. */
		class insert(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/content""")
			/** Perform the request */
			def withProduct(body: Schema.Product) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Product])
		}
		object insert {
			def apply(merchantId: String, feedId: String)(using signer: RequestSigner, ec: ExecutionContext): insert = new insert(ws.url(BASE_URL + s"${merchantId}/products").addQueryStringParameters("feedId" -> feedId.toString))
		}
		/** Retrieves, inserts, and deletes multiple products in a single request. */
		class custombatch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/content""")
			/** Perform the request */
			def withProductsCustomBatchRequest(body: Schema.ProductsCustomBatchRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.ProductsCustomBatchResponse])
		}
		object custombatch {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): custombatch = new custombatch(ws.url(BASE_URL + s"products/batch").addQueryStringParameters())
		}
		/** Deletes a product from your Merchant Center account. */
		class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Unit]) {
			val scopes = Seq("""https://www.googleapis.com/auth/content""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_ => ())
		}
		object delete {
			def apply(merchantId: String, productId: String, feedId: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"${merchantId}/products/${productId}").addQueryStringParameters("feedId" -> feedId.toString))
			given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
		}
		/** Retrieves a product from your Merchant Center account. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Product]) {
			val scopes = Seq("""https://www.googleapis.com/auth/content""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Product])
		}
		object get {
			def apply(merchantId: String, productId: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"${merchantId}/products/${productId}").addQueryStringParameters())
			given Conversion[get, Future[Schema.Product]] = (fun: get) => fun.apply()
		}
		/** Updates an existing product in your Merchant Center account. Only updates attributes provided in the request. */
		class update(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/content""")
			/** Perform the request */
			def withProduct(body: Schema.Product) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Product])
		}
		object update {
			def apply(merchantId: String, productId: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"${merchantId}/products/${productId}").addQueryStringParameters("updateMask" -> updateMask.toString))
		}
		/** Lists the products in your Merchant Center account. The response might contain fewer items than specified by maxResults. Rely on nextPageToken to determine if there are more items to be requested. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ProductsListResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/content""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ProductsListResponse])
		}
		object list {
			def apply(merchantId: String, maxResults: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"${merchantId}/products").addQueryStringParameters("maxResults" -> maxResults.toString, "pageToken" -> pageToken.toString))
			given Conversion[list, Future[Schema.ProductsListResponse]] = (fun: list) => fun.apply()
		}
	}
	object productstatuses {
		/** Gets the statuses of multiple products in a single request. */
		class custombatch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/content""")
			/** Perform the request */
			def withProductstatusesCustomBatchRequest(body: Schema.ProductstatusesCustomBatchRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.ProductstatusesCustomBatchResponse])
		}
		object custombatch {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): custombatch = new custombatch(ws.url(BASE_URL + s"productstatuses/batch").addQueryStringParameters())
		}
		/** Gets the status of a product from your Merchant Center account. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ProductStatus]) {
			val scopes = Seq("""https://www.googleapis.com/auth/content""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ProductStatus])
		}
		object get {
			def apply(merchantId: String, productId: String, destinations: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"${merchantId}/productstatuses/${productId}").addQueryStringParameters("destinations" -> destinations.toString))
			given Conversion[get, Future[Schema.ProductStatus]] = (fun: get) => fun.apply()
		}
		/** Lists the statuses of the products in your Merchant Center account. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ProductstatusesListResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/content""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ProductstatusesListResponse])
		}
		object list {
			def apply(merchantId: String, maxResults: Int, pageToken: String, destinations: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"${merchantId}/productstatuses").addQueryStringParameters("maxResults" -> maxResults.toString, "pageToken" -> pageToken.toString, "destinations" -> destinations.toString))
			given Conversion[list, Future[Schema.ProductstatusesListResponse]] = (fun: list) => fun.apply()
		}
	}
	object pubsubnotificationsettings {
		/** Retrieves a Merchant Center account's pubsub notification settings. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.PubsubNotificationSettings]) {
			val scopes = Seq("""https://www.googleapis.com/auth/content""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.PubsubNotificationSettings])
		}
		object get {
			def apply(merchantId: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"${merchantId}/pubsubnotificationsettings").addQueryStringParameters())
			given Conversion[get, Future[Schema.PubsubNotificationSettings]] = (fun: get) => fun.apply()
		}
		/** Register a Merchant Center account for pubsub notifications. Note that cloud topic name shouldn't be provided as part of the request. */
		class update(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/content""")
			/** Perform the request */
			def withPubsubNotificationSettings(body: Schema.PubsubNotificationSettings) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.PubsubNotificationSettings])
		}
		object update {
			def apply(merchantId: String)(using signer: RequestSigner, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"${merchantId}/pubsubnotificationsettings").addQueryStringParameters())
		}
	}
	object regionalinventory {
		/** Updates regional inventory for multiple products or regions in a single request. */
		class custombatch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/content""")
			/** Perform the request */
			def withRegionalinventoryCustomBatchRequest(body: Schema.RegionalinventoryCustomBatchRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.RegionalinventoryCustomBatchResponse])
		}
		object custombatch {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): custombatch = new custombatch(ws.url(BASE_URL + s"regionalinventory/batch").addQueryStringParameters())
		}
		/** Updates the regional inventory of a product in your Merchant Center account. If a regional inventory with the same region ID already exists, this method updates that entry. */
		class insert(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/content""")
			/** Perform the request */
			def withRegionalInventory(body: Schema.RegionalInventory) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.RegionalInventory])
		}
		object insert {
			def apply(merchantId: String, productId: String)(using signer: RequestSigner, ec: ExecutionContext): insert = new insert(ws.url(BASE_URL + s"${merchantId}/products/${productId}/regionalinventory").addQueryStringParameters())
		}
	}
	object returnaddress {
		/** Inserts a return address for the Merchant Center account. */
		class insert(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/content""")
			/** Perform the request */
			def withReturnAddress(body: Schema.ReturnAddress) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.ReturnAddress])
		}
		object insert {
			def apply(merchantId: String)(using signer: RequestSigner, ec: ExecutionContext): insert = new insert(ws.url(BASE_URL + s"${merchantId}/returnaddress").addQueryStringParameters())
		}
		/** Batches multiple return address related calls in a single request. */
		class custombatch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/content""")
			/** Perform the request */
			def withReturnaddressCustomBatchRequest(body: Schema.ReturnaddressCustomBatchRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.ReturnaddressCustomBatchResponse])
		}
		object custombatch {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): custombatch = new custombatch(ws.url(BASE_URL + s"returnaddress/batch").addQueryStringParameters())
		}
		/** Deletes a return address for the given Merchant Center account. */
		class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Unit]) {
			val scopes = Seq("""https://www.googleapis.com/auth/content""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_ => ())
		}
		object delete {
			def apply(merchantId: String, returnAddressId: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"${merchantId}/returnaddress/${returnAddressId}").addQueryStringParameters())
			given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
		}
		/** Gets a return address of the Merchant Center account. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ReturnAddress]) {
			val scopes = Seq("""https://www.googleapis.com/auth/content""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ReturnAddress])
		}
		object get {
			def apply(merchantId: String, returnAddressId: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"${merchantId}/returnaddress/${returnAddressId}").addQueryStringParameters())
			given Conversion[get, Future[Schema.ReturnAddress]] = (fun: get) => fun.apply()
		}
		/** Lists the return addresses of the Merchant Center account. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ReturnaddressListResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/content""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ReturnaddressListResponse])
		}
		object list {
			def apply(merchantId: String, maxResults: Int, pageToken: String, country: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"${merchantId}/returnaddress").addQueryStringParameters("maxResults" -> maxResults.toString, "pageToken" -> pageToken.toString, "country" -> country.toString))
			given Conversion[list, Future[Schema.ReturnaddressListResponse]] = (fun: list) => fun.apply()
		}
	}
	object returnpolicy {
		/** Inserts a return policy for the Merchant Center account. */
		class insert(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/content""")
			/** Perform the request */
			def withReturnPolicy(body: Schema.ReturnPolicy) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.ReturnPolicy])
		}
		object insert {
			def apply(merchantId: String)(using signer: RequestSigner, ec: ExecutionContext): insert = new insert(ws.url(BASE_URL + s"${merchantId}/returnpolicy").addQueryStringParameters())
		}
		/** Batches multiple return policy related calls in a single request. */
		class custombatch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/content""")
			/** Perform the request */
			def withReturnpolicyCustomBatchRequest(body: Schema.ReturnpolicyCustomBatchRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.ReturnpolicyCustomBatchResponse])
		}
		object custombatch {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): custombatch = new custombatch(ws.url(BASE_URL + s"returnpolicy/batch").addQueryStringParameters())
		}
		/** Deletes a return policy for the given Merchant Center account. */
		class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Unit]) {
			val scopes = Seq("""https://www.googleapis.com/auth/content""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_ => ())
		}
		object delete {
			def apply(merchantId: String, returnPolicyId: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"${merchantId}/returnpolicy/${returnPolicyId}").addQueryStringParameters())
			given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
		}
		/** Gets a return policy of the Merchant Center account. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ReturnPolicy]) {
			val scopes = Seq("""https://www.googleapis.com/auth/content""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ReturnPolicy])
		}
		object get {
			def apply(merchantId: String, returnPolicyId: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"${merchantId}/returnpolicy/${returnPolicyId}").addQueryStringParameters())
			given Conversion[get, Future[Schema.ReturnPolicy]] = (fun: get) => fun.apply()
		}
		/** Lists the return policies of the Merchant Center account. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ReturnpolicyListResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/content""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ReturnpolicyListResponse])
		}
		object list {
			def apply(merchantId: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"${merchantId}/returnpolicy").addQueryStringParameters())
			given Conversion[list, Future[Schema.ReturnpolicyListResponse]] = (fun: list) => fun.apply()
		}
	}
	object shippingsettings {
		/** Retrieves supported holidays for an account. */
		class getsupportedholidays(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ShippingsettingsGetSupportedHolidaysResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/content""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ShippingsettingsGetSupportedHolidaysResponse])
		}
		object getsupportedholidays {
			def apply(merchantId: String)(using signer: RequestSigner, ec: ExecutionContext): getsupportedholidays = new getsupportedholidays(ws.url(BASE_URL + s"${merchantId}/supportedHolidays").addQueryStringParameters())
			given Conversion[getsupportedholidays, Future[Schema.ShippingsettingsGetSupportedHolidaysResponse]] = (fun: getsupportedholidays) => fun.apply()
		}
		/** Retrieves supported pickup services for an account. */
		class getsupportedpickupservices(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ShippingsettingsGetSupportedPickupServicesResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/content""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ShippingsettingsGetSupportedPickupServicesResponse])
		}
		object getsupportedpickupservices {
			def apply(merchantId: String)(using signer: RequestSigner, ec: ExecutionContext): getsupportedpickupservices = new getsupportedpickupservices(ws.url(BASE_URL + s"${merchantId}/supportedPickupServices").addQueryStringParameters())
			given Conversion[getsupportedpickupservices, Future[Schema.ShippingsettingsGetSupportedPickupServicesResponse]] = (fun: getsupportedpickupservices) => fun.apply()
		}
		/** Retrieves and updates the shipping settings of multiple accounts in a single request. */
		class custombatch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/content""")
			/** Perform the request */
			def withShippingsettingsCustomBatchRequest(body: Schema.ShippingsettingsCustomBatchRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.ShippingsettingsCustomBatchResponse])
		}
		object custombatch {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): custombatch = new custombatch(ws.url(BASE_URL + s"shippingsettings/batch").addQueryStringParameters())
		}
		/** Retrieves supported carriers and carrier services for an account. */
		class getsupportedcarriers(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ShippingsettingsGetSupportedCarriersResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/content""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ShippingsettingsGetSupportedCarriersResponse])
		}
		object getsupportedcarriers {
			def apply(merchantId: String)(using signer: RequestSigner, ec: ExecutionContext): getsupportedcarriers = new getsupportedcarriers(ws.url(BASE_URL + s"${merchantId}/supportedCarriers").addQueryStringParameters())
			given Conversion[getsupportedcarriers, Future[Schema.ShippingsettingsGetSupportedCarriersResponse]] = (fun: getsupportedcarriers) => fun.apply()
		}
		/** Retrieves the shipping settings of the account. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ShippingSettings]) {
			val scopes = Seq("""https://www.googleapis.com/auth/content""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ShippingSettings])
		}
		object get {
			def apply(merchantId: String, accountId: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"${merchantId}/shippingsettings/${accountId}").addQueryStringParameters())
			given Conversion[get, Future[Schema.ShippingSettings]] = (fun: get) => fun.apply()
		}
		/** Updates the shipping settings of the account. Any fields that are not provided are deleted from the resource. */
		class update(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/content""")
			/** Perform the request */
			def withShippingSettings(body: Schema.ShippingSettings) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.ShippingSettings])
		}
		object update {
			def apply(merchantId: String, accountId: String)(using signer: RequestSigner, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"${merchantId}/shippingsettings/${accountId}").addQueryStringParameters())
		}
		/** Lists the shipping settings of the sub-accounts in your Merchant Center account. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ShippingsettingsListResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/content""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ShippingsettingsListResponse])
		}
		object list {
			def apply(merchantId: String, maxResults: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"${merchantId}/shippingsettings").addQueryStringParameters("maxResults" -> maxResults.toString, "pageToken" -> pageToken.toString))
			given Conversion[list, Future[Schema.ShippingsettingsListResponse]] = (fun: list) => fun.apply()
		}
	}
	object collections {
		/** Retrieves a collection from your Merchant Center account. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Collection]) {
			val scopes = Seq("""https://www.googleapis.com/auth/content""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Collection])
		}
		object get {
			def apply(merchantId: String, collectionId: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"${merchantId}/collections/${collectionId}").addQueryStringParameters())
			given Conversion[get, Future[Schema.Collection]] = (fun: get) => fun.apply()
		}
		/** Lists the collections in your Merchant Center account. The response might contain fewer items than specified by page_size. Rely on next_page_token to determine if there are more items to be requested. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListCollectionsResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/content""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListCollectionsResponse])
		}
		object list {
			def apply(merchantId: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"${merchantId}/collections").addQueryStringParameters("pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
			given Conversion[list, Future[Schema.ListCollectionsResponse]] = (fun: list) => fun.apply()
		}
		/** Uploads a collection to your Merchant Center account. If a collection with the same collectionId already exists, this method updates that entry. In each update, the collection is completely replaced by the fields in the body of the update request. */
		class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/content""")
			/** Perform the request */
			def withCollection(body: Schema.Collection) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Collection])
		}
		object create {
			def apply(merchantId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"${merchantId}/collections").addQueryStringParameters())
		}
		/** Deletes a collection from your Merchant Center account. */
		class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Unit]) {
			val scopes = Seq("""https://www.googleapis.com/auth/content""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_ => ())
		}
		object delete {
			def apply(merchantId: String, collectionId: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"${merchantId}/collections/${collectionId}").addQueryStringParameters())
			given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
		}
	}
	object quotas {
		/** Lists the daily call quota and usage per method for your Merchant Center account. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListMethodQuotasResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/content""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListMethodQuotasResponse])
		}
		object list {
			def apply(merchantId: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"${merchantId}/quotas").addQueryStringParameters("pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
			given Conversion[list, Future[Schema.ListMethodQuotasResponse]] = (fun: list) => fun.apply()
		}
	}
	object collectionstatuses {
		/** Gets the status of a collection from your Merchant Center account. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.CollectionStatus]) {
			val scopes = Seq("""https://www.googleapis.com/auth/content""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.CollectionStatus])
		}
		object get {
			def apply(merchantId: String, collectionId: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"${merchantId}/collectionstatuses/${collectionId}").addQueryStringParameters())
			given Conversion[get, Future[Schema.CollectionStatus]] = (fun: get) => fun.apply()
		}
		/** Lists the statuses of the collections in your Merchant Center account. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListCollectionStatusesResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/content""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListCollectionStatusesResponse])
		}
		object list {
			def apply(merchantId: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"${merchantId}/collectionstatuses").addQueryStringParameters("pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
			given Conversion[list, Future[Schema.ListCollectionStatusesResponse]] = (fun: list) => fun.apply()
		}
	}
	object conversionsources {
		/** Re-enables an archived conversion source. */
		class undelete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/content""")
			/** Perform the request */
			def withUndeleteConversionSourceRequest(body: Schema.UndeleteConversionSourceRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_ => ())
		}
		object undelete {
			def apply(merchantId: String, conversionSourceId: String)(using signer: RequestSigner, ec: ExecutionContext): undelete = new undelete(ws.url(BASE_URL + s"${merchantId}/conversionsources/${conversionSourceId}:undelete").addQueryStringParameters())
		}
		/** Creates a new conversion source. */
		class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/content""")
			/** Perform the request */
			def withConversionSource(body: Schema.ConversionSource) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.ConversionSource])
		}
		object create {
			def apply(merchantId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"${merchantId}/conversionsources").addQueryStringParameters())
		}
		/** Archives an existing conversion source. It will be recoverable for 30 days. This archiving behavior is not typical in the Content API and unique to this service. */
		class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Unit]) {
			val scopes = Seq("""https://www.googleapis.com/auth/content""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_ => ())
		}
		object delete {
			def apply(merchantId: String, conversionSourceId: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"${merchantId}/conversionsources/${conversionSourceId}").addQueryStringParameters())
			given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
		}
		/** Fetches a conversion source. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ConversionSource]) {
			val scopes = Seq("""https://www.googleapis.com/auth/content""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ConversionSource])
		}
		object get {
			def apply(merchantId: String, conversionSourceId: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"${merchantId}/conversionsources/${conversionSourceId}").addQueryStringParameters())
			given Conversion[get, Future[Schema.ConversionSource]] = (fun: get) => fun.apply()
		}
		/** Updates information of an existing conversion source. */
		class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/content""")
			/** Optional. List of fields being updated.<br>Format: google-fieldmask */
			def withUpdateMask(updateMask: String) = new patch(req.addQueryStringParameters("updateMask" -> updateMask.toString))
			/** Perform the request */
			def withConversionSource(body: Schema.ConversionSource) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.ConversionSource])
		}
		object patch {
			def apply(merchantId: String, conversionSourceId: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"${merchantId}/conversionsources/${conversionSourceId}").addQueryStringParameters())
		}
		/** Retrieves the list of conversion sources the caller has access to. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListConversionSourcesResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/content""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListConversionSourcesResponse])
		}
		object list {
			def apply(merchantId: String, pageSize: Int, pageToken: String, showDeleted: Boolean)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"${merchantId}/conversionsources").addQueryStringParameters("pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "showDeleted" -> showDeleted.toString))
			given Conversion[list, Future[Schema.ListConversionSourcesResponse]] = (fun: list) => fun.apply()
		}
	}
	object freelistingsprogram {
		/** Retrieves the status and review eligibility for the free listing program. Returns errors and warnings if they require action to resolve, will become disapprovals, or impact impressions. Use `accountstatuses` to view all issues for an account. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.FreeListingsProgramStatus]) {
			val scopes = Seq("""https://www.googleapis.com/auth/content""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.FreeListingsProgramStatus])
		}
		object get {
			def apply(merchantId: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"${merchantId}/freelistingsprogram").addQueryStringParameters())
			given Conversion[get, Future[Schema.FreeListingsProgramStatus]] = (fun: get) => fun.apply()
		}
		/** Requests a review of free listings in a specific region. This method deprecated. Use the `MerchantSupportService` to view product and account issues and request a review. */
		class requestreview(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/content""")
			/** Perform the request */
			def withRequestReviewFreeListingsRequest(body: Schema.RequestReviewFreeListingsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_ => ())
		}
		object requestreview {
			def apply(merchantId: String)(using signer: RequestSigner, ec: ExecutionContext): requestreview = new requestreview(ws.url(BASE_URL + s"${merchantId}/freelistingsprogram/requestreview").addQueryStringParameters())
		}
		object checkoutsettings {
			/** Gets Checkout settings for the given merchant. This includes information about review state, enrollment state and URL settings. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.CheckoutSettings]) {
				val scopes = Seq("""https://www.googleapis.com/auth/content""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.CheckoutSettings])
			}
			object get {
				def apply(merchantId: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"${merchantId}/freelistingsprogram/checkoutsettings").addQueryStringParameters())
				given Conversion[get, Future[Schema.CheckoutSettings]] = (fun: get) => fun.apply()
			}
			/** Enrolls merchant in `Checkout` program. */
			class insert(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/content""")
				/** Perform the request */
				def withInsertCheckoutSettingsRequest(body: Schema.InsertCheckoutSettingsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.CheckoutSettings])
			}
			object insert {
				def apply(merchantId: String)(using signer: RequestSigner, ec: ExecutionContext): insert = new insert(ws.url(BASE_URL + s"${merchantId}/freelistingsprogram/checkoutsettings").addQueryStringParameters())
			}
			/** Deletes `Checkout` settings and unenrolls merchant from `Checkout` program. */
			class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Unit]) {
				val scopes = Seq("""https://www.googleapis.com/auth/content""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_ => ())
			}
			object delete {
				def apply(merchantId: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"${merchantId}/freelistingsprogram/checkoutsettings").addQueryStringParameters())
				given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
			}
		}
	}
	object shoppingadsprogram {
		/** Retrieves the status and review eligibility for the Shopping Ads program. Returns errors and warnings if they require action to resolve, will become disapprovals, or impact impressions. Use `accountstatuses` to view all issues for an account. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ShoppingAdsProgramStatus]) {
			val scopes = Seq("""https://www.googleapis.com/auth/content""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ShoppingAdsProgramStatus])
		}
		object get {
			def apply(merchantId: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"${merchantId}/shoppingadsprogram").addQueryStringParameters())
			given Conversion[get, Future[Schema.ShoppingAdsProgramStatus]] = (fun: get) => fun.apply()
		}
		/** Requests a review of Shopping ads in a specific region. This method deprecated. Use the `MerchantSupportService` to view product and account issues and request a review. */
		class requestreview(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/content""")
			/** Perform the request */
			def withRequestReviewShoppingAdsRequest(body: Schema.RequestReviewShoppingAdsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_ => ())
		}
		object requestreview {
			def apply(merchantId: String)(using signer: RequestSigner, ec: ExecutionContext): requestreview = new requestreview(ws.url(BASE_URL + s"${merchantId}/shoppingadsprogram/requestreview").addQueryStringParameters())
		}
	}
	object csses {
		/** Lists CSS domains affiliated with a CSS group. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListCssesResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/content""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListCssesResponse])
		}
		object list {
			def apply(cssGroupId: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"${cssGroupId}/csses").addQueryStringParameters("pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
			given Conversion[list, Future[Schema.ListCssesResponse]] = (fun: list) => fun.apply()
		}
		/** Retrieves a single CSS domain by ID. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Css]) {
			val scopes = Seq("""https://www.googleapis.com/auth/content""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Css])
		}
		object get {
			def apply(cssGroupId: String, cssDomainId: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"${cssGroupId}/csses/${cssDomainId}").addQueryStringParameters())
			given Conversion[get, Future[Schema.Css]] = (fun: get) => fun.apply()
		}
		/** Updates labels that are assigned to a CSS domain by its CSS group. */
		class updatelabels(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/content""")
			/** Perform the request */
			def withLabelIds(body: Schema.LabelIds) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Css])
		}
		object updatelabels {
			def apply(cssGroupId: String, cssDomainId: String)(using signer: RequestSigner, ec: ExecutionContext): updatelabels = new updatelabels(ws.url(BASE_URL + s"${cssGroupId}/csses/${cssDomainId}/updatelabels").addQueryStringParameters())
		}
	}
	object reports {
		/** Retrieves merchant performance metrics matching the search query and optionally segmented by selected dimensions. */
		class search(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/content""")
			/** Perform the request */
			def withSearchRequest(body: Schema.SearchRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.SearchResponse])
		}
		object search {
			def apply(merchantId: String)(using signer: RequestSigner, ec: ExecutionContext): search = new search(ws.url(BASE_URL + s"${merchantId}/reports/search").addQueryStringParameters())
		}
	}
	object merchantsupport {
		/** Provide a list of merchant's issues with a support content and available actions. This content and actions are meant to be rendered and shown in third-party applications. */
		class renderaccountissues(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/content""")
			/** Optional. The [IETF BCP-47](https://tools.ietf.org/html/bcp47) language code used to localize support content. If not set, the result will be in default language `en-US`. */
			def withLanguageCode(languageCode: String) = new renderaccountissues(req.addQueryStringParameters("languageCode" -> languageCode.toString))
			/** Optional. The [IANA](https://www.iana.org/time-zones) timezone used to localize times in support content. For example 'America/Los_Angeles'. If not set, results will use as a default UTC. */
			def withTimeZone(timeZone: String) = new renderaccountissues(req.addQueryStringParameters("timeZone" -> timeZone.toString))
			/** Perform the request */
			def withRenderAccountIssuesRequestPayload(body: Schema.RenderAccountIssuesRequestPayload) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.RenderAccountIssuesResponse])
		}
		object renderaccountissues {
			def apply(merchantId: String)(using signer: RequestSigner, ec: ExecutionContext): renderaccountissues = new renderaccountissues(ws.url(BASE_URL + s"${merchantId}/merchantsupport/renderaccountissues").addQueryStringParameters())
		}
		/** Provide a list of issues for merchant's product with a support content and available actions. This content and actions are meant to be rendered and shown in third-party applications. */
		class renderproductissues(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/content""")
			/** Optional. The [IETF BCP-47](https://tools.ietf.org/html/bcp47) language code used to localize support content. If not set, the result will be in default language `en-US`. */
			def withLanguageCode(languageCode: String) = new renderproductissues(req.addQueryStringParameters("languageCode" -> languageCode.toString))
			/** Optional. The [IANA](https://www.iana.org/time-zones) timezone used to localize times in support content. For example 'America/Los_Angeles'. If not set, results will use as a default UTC. */
			def withTimeZone(timeZone: String) = new renderproductissues(req.addQueryStringParameters("timeZone" -> timeZone.toString))
			/** Perform the request */
			def withRenderProductIssuesRequestPayload(body: Schema.RenderProductIssuesRequestPayload) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.RenderProductIssuesResponse])
		}
		object renderproductissues {
			def apply(merchantId: String, productId: String)(using signer: RequestSigner, ec: ExecutionContext): renderproductissues = new renderproductissues(ws.url(BASE_URL + s"${merchantId}/merchantsupport/renderproductissues/${productId}").addQueryStringParameters())
		}
		/** Start an action. The action can be requested by merchants in third-party application. Before merchants can request the action, the third-party application needs to show them action specific content and display a user input form. The action can be successfully started only once all `required` inputs are provided. If any `required` input is missing, or invalid value was provided, the service will return 400 error. Validation errors will contain Ids for all problematic field together with translated, human readable error messages that can be shown to the user. */
		class triggeraction(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/content""")
			/** Optional. Language code [IETF BCP 47 syntax](https://tools.ietf.org/html/bcp47) used to localize the response. If not set, the result will be in default language `en-US`. */
			def withLanguageCode(languageCode: String) = new triggeraction(req.addQueryStringParameters("languageCode" -> languageCode.toString))
			/** Perform the request */
			def withTriggerActionPayload(body: Schema.TriggerActionPayload) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.TriggerActionResponse])
		}
		object triggeraction {
			def apply(merchantId: String)(using signer: RequestSigner, ec: ExecutionContext): triggeraction = new triggeraction(ws.url(BASE_URL + s"${merchantId}/merchantsupport/triggeraction").addQueryStringParameters())
		}
	}
	object regions {
		/** Creates a region definition in your Merchant Center account. */
		class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/content""")
			/** Perform the request */
			def withRegion(body: Schema.Region) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Region])
		}
		object create {
			def apply(merchantId: String, regionId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"${merchantId}/regions").addQueryStringParameters("regionId" -> regionId.toString))
		}
		/** Deletes a region definition from your Merchant Center account. */
		class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Unit]) {
			val scopes = Seq("""https://www.googleapis.com/auth/content""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_ => ())
		}
		object delete {
			def apply(merchantId: String, regionId: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"${merchantId}/regions/${regionId}").addQueryStringParameters())
			given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
		}
		/** Retrieves a region defined in your Merchant Center account. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Region]) {
			val scopes = Seq("""https://www.googleapis.com/auth/content""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Region])
		}
		object get {
			def apply(merchantId: String, regionId: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"${merchantId}/regions/${regionId}").addQueryStringParameters())
			given Conversion[get, Future[Schema.Region]] = (fun: get) => fun.apply()
		}
		/** Updates a region definition in your Merchant Center account. */
		class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/content""")
			/** Optional. The comma-separated field mask indicating the fields to update. Example: `"displayName,postalCodeArea.regionCode"`.<br>Format: google-fieldmask */
			def withUpdateMask(updateMask: String) = new patch(req.addQueryStringParameters("updateMask" -> updateMask.toString))
			/** Perform the request */
			def withRegion(body: Schema.Region) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Region])
		}
		object patch {
			def apply(merchantId: String, regionId: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"${merchantId}/regions/${regionId}").addQueryStringParameters())
		}
		/** Lists the regions in your Merchant Center account. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListRegionsResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/content""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListRegionsResponse])
		}
		object list {
			def apply(merchantId: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"${merchantId}/regions").addQueryStringParameters("pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
			given Conversion[list, Future[Schema.ListRegionsResponse]] = (fun: list) => fun.apply()
		}
	}
	object promotions {
		/** Inserts a promotion for your Merchant Center account. If the promotion already exists, then it updates the promotion instead. To [end or delete] (https://developers.google.com/shopping-content/guides/promotions#end_a_promotion) a promotion update the time period of the promotion to a time that has already passed. */
		class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/content""")
			/** Perform the request */
			def withPromotion(body: Schema.Promotion) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Promotion])
		}
		object create {
			def apply(merchantId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"${merchantId}/promotions").addQueryStringParameters())
		}
		/** Retrieves a promotion from your Merchant Center account. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Promotion]) {
			val scopes = Seq("""https://www.googleapis.com/auth/content""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Promotion])
		}
		object get {
			def apply(merchantId: String, id: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"${merchantId}/promotions/${id}").addQueryStringParameters())
			given Conversion[get, Future[Schema.Promotion]] = (fun: get) => fun.apply()
		}
		/** List all promotions from your Merchant Center account. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListPromotionResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/content""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListPromotionResponse])
		}
		object list {
			def apply(merchantId: String, pageSize: Int, pageToken: String, countryCode: String, languageCode: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"${merchantId}/promotions").addQueryStringParameters("pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "countryCode" -> countryCode.toString, "languageCode" -> languageCode.toString))
			given Conversion[list, Future[Schema.ListPromotionResponse]] = (fun: list) => fun.apply()
		}
	}
	object recommendations {
		/** Generates recommendations for a merchant. */
		class generate(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GenerateRecommendationsResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/content""")
			/** Optional. Language code of the client. If not set, the result will be in default language (English). This language code affects all fields prefixed with "localized". This should be set to ISO 639-1 country code. List of currently verified supported language code: en, fr, cs, da, de, es, it, nl, no, pl, pt, pt, fi, sv, vi, tr, th, ko, zh-CN, zh-TW, ja, id, hi */
			def withLanguageCode(languageCode: String) = new generate(req.addQueryStringParameters("languageCode" -> languageCode.toString))
			/** Optional. List of allowed tags. Tags are a set of predefined strings that describe the category that individual recommendation types belong to. User can specify zero or more tags in this field to indicate what categories of recommendations they want to receive. Current list of supported tags: - TREND */
			def withAllowedTag(allowedTag: String) = new generate(req.addQueryStringParameters("allowedTag" -> allowedTag.toString))
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GenerateRecommendationsResponse])
		}
		object generate {
			def apply(merchantId: String)(using signer: RequestSigner, ec: ExecutionContext): generate = new generate(ws.url(BASE_URL + s"${merchantId}/recommendations/generate").addQueryStringParameters())
			given Conversion[generate, Future[Schema.GenerateRecommendationsResponse]] = (fun: generate) => fun.apply()
		}
		/** Reports an interaction on a recommendation for a merchant. */
		class reportInteraction(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/content""")
			/** Perform the request */
			def withReportInteractionRequest(body: Schema.ReportInteractionRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_ => ())
		}
		object reportInteraction {
			def apply(merchantId: String)(using signer: RequestSigner, ec: ExecutionContext): reportInteraction = new reportInteraction(ws.url(BASE_URL + s"${merchantId}/recommendations/reportInteraction").addQueryStringParameters())
		}
	}
	object returnpolicyonline {
		/** Creates a new return policy. */
		class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/content""")
			/** Perform the request */
			def withReturnPolicyOnline(body: Schema.ReturnPolicyOnline) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.ReturnPolicyOnline])
		}
		object create {
			def apply(merchantId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"${merchantId}/returnpolicyonline").addQueryStringParameters())
		}
		/** Deletes an existing return policy. */
		class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Unit]) {
			val scopes = Seq("""https://www.googleapis.com/auth/content""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_ => ())
		}
		object delete {
			def apply(merchantId: String, returnPolicyId: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"${merchantId}/returnpolicyonline/${returnPolicyId}").addQueryStringParameters())
			given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
		}
		/** Gets an existing return policy. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ReturnPolicyOnline]) {
			val scopes = Seq("""https://www.googleapis.com/auth/content""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ReturnPolicyOnline])
		}
		object get {
			def apply(merchantId: String, returnPolicyId: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"${merchantId}/returnpolicyonline/${returnPolicyId}").addQueryStringParameters())
			given Conversion[get, Future[Schema.ReturnPolicyOnline]] = (fun: get) => fun.apply()
		}
		/** Updates an existing return policy. */
		class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/content""")
			/** Perform the request */
			def withReturnPolicyOnline(body: Schema.ReturnPolicyOnline) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.ReturnPolicyOnline])
		}
		object patch {
			def apply(merchantId: String, returnPolicyId: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"${merchantId}/returnpolicyonline/${returnPolicyId}").addQueryStringParameters())
		}
		/** Lists all existing return policies. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListReturnPolicyOnlineResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/content""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListReturnPolicyOnlineResponse])
		}
		object list {
			def apply(merchantId: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"${merchantId}/returnpolicyonline").addQueryStringParameters())
			given Conversion[list, Future[Schema.ListReturnPolicyOnlineResponse]] = (fun: list) => fun.apply()
		}
	}
	object ordertrackingsignals {
		/** Creates new order tracking signal. */
		class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/content""")
			/** Perform the request */
			def withOrderTrackingSignal(body: Schema.OrderTrackingSignal) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.OrderTrackingSignal])
		}
		object create {
			def apply(merchantId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"${merchantId}/ordertrackingsignals").addQueryStringParameters())
		}
	}
	object productdeliverytime {
		/** Creates or updates the delivery time of a product. */
		class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/content""")
			/** Perform the request */
			def withProductDeliveryTime(body: Schema.ProductDeliveryTime) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.ProductDeliveryTime])
		}
		object create {
			def apply(merchantId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"${merchantId}/productdeliverytime").addQueryStringParameters())
		}
		/** Gets `productDeliveryTime` by `productId`. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ProductDeliveryTime]) {
			val scopes = Seq("""https://www.googleapis.com/auth/content""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ProductDeliveryTime])
		}
		object get {
			def apply(merchantId: String, productId: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"${merchantId}/productdeliverytime/${productId}").addQueryStringParameters())
			given Conversion[get, Future[Schema.ProductDeliveryTime]] = (fun: get) => fun.apply()
		}
		/** Deletes the delivery time of a product. */
		class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Unit]) {
			val scopes = Seq("""https://www.googleapis.com/auth/content""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_ => ())
		}
		object delete {
			def apply(merchantId: String, productId: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"${merchantId}/productdeliverytime/${productId}").addQueryStringParameters())
			given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
		}
	}
}
