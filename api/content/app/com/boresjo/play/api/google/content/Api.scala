package com.boresjo.play.api.google.content

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://shoppingcontent.googleapis.com/content/v2.1/"

	object accounts {
		class insert(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withAccount(body: Schema.Account) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Account])
		}
		object insert {
			def apply(merchantId: String)(using auth: AuthToken, ec: ExecutionContext): insert = new insert(ws.url(BASE_URL + s"${merchantId}/accounts").addQueryStringParameters())
		}
		class claimwebsite(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.AccountsClaimWebsiteResponse]) {
			def apply() = auth.exec(req,_.execute("POST")).map(_.json.as[Schema.AccountsClaimWebsiteResponse])
		}
		object claimwebsite {
			def apply(merchantId: String, accountId: String, overwrite: Boolean)(using auth: AuthToken, ec: ExecutionContext): claimwebsite = new claimwebsite(ws.url(BASE_URL + s"${merchantId}/accounts/${accountId}/claimwebsite").addQueryStringParameters("overwrite" -> overwrite.toString))
			given Conversion[claimwebsite, Future[Schema.AccountsClaimWebsiteResponse]] = (fun: claimwebsite) => fun.apply()
		}
		class updatelabels(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withAccountsUpdateLabelsRequest(body: Schema.AccountsUpdateLabelsRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.AccountsUpdateLabelsResponse])
		}
		object updatelabels {
			def apply(merchantId: String, accountId: String)(using auth: AuthToken, ec: ExecutionContext): updatelabels = new updatelabels(ws.url(BASE_URL + s"${merchantId}/accounts/${accountId}/updatelabels").addQueryStringParameters())
		}
		class requestphoneverification(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withRequestPhoneVerificationRequest(body: Schema.RequestPhoneVerificationRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.RequestPhoneVerificationResponse])
		}
		object requestphoneverification {
			def apply(merchantId: String, accountId: String)(using auth: AuthToken, ec: ExecutionContext): requestphoneverification = new requestphoneverification(ws.url(BASE_URL + s"${merchantId}/accounts/${accountId}/requestphoneverification").addQueryStringParameters())
		}
		class custombatch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withAccountsCustomBatchRequest(body: Schema.AccountsCustomBatchRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.AccountsCustomBatchResponse])
		}
		object custombatch {
			def apply()(using auth: AuthToken, ec: ExecutionContext): custombatch = new custombatch(ws.url(BASE_URL + s"accounts/batch").addQueryStringParameters())
		}
		class authinfo(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.AccountsAuthInfoResponse]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.AccountsAuthInfoResponse])
		}
		object authinfo {
			def apply()(using auth: AuthToken, ec: ExecutionContext): authinfo = new authinfo(ws.url(BASE_URL + s"accounts/authinfo").addQueryStringParameters())
			given Conversion[authinfo, Future[Schema.AccountsAuthInfoResponse]] = (fun: authinfo) => fun.apply()
		}
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Account]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Account])
		}
		object get {
			def apply(merchantId: String, accountId: String, view: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"${merchantId}/accounts/${accountId}").addQueryStringParameters("view" -> view.toString))
			given Conversion[get, Future[Schema.Account]] = (fun: get) => fun.apply()
		}
		class update(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withAccount(body: Schema.Account) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.Account])
		}
		object update {
			def apply(merchantId: String, accountId: String)(using auth: AuthToken, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"${merchantId}/accounts/${accountId}").addQueryStringParameters())
		}
		class listlinks(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.AccountsListLinksResponse]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.AccountsListLinksResponse])
		}
		object listlinks {
			def apply(merchantId: String, accountId: String, maxResults: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): listlinks = new listlinks(ws.url(BASE_URL + s"${merchantId}/accounts/${accountId}/listlinks").addQueryStringParameters("maxResults" -> maxResults.toString, "pageToken" -> pageToken.toString))
			given Conversion[listlinks, Future[Schema.AccountsListLinksResponse]] = (fun: listlinks) => fun.apply()
		}
		class link(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withAccountsLinkRequest(body: Schema.AccountsLinkRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.AccountsLinkResponse])
		}
		object link {
			def apply(merchantId: String, accountId: String)(using auth: AuthToken, ec: ExecutionContext): link = new link(ws.url(BASE_URL + s"${merchantId}/accounts/${accountId}/link").addQueryStringParameters())
		}
		class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Unit]) {
			def apply() = auth.exec(req,_.execute("DELETE")).map(_ => ())
		}
		object delete {
			def apply(merchantId: String, accountId: String, force: Boolean)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"${merchantId}/accounts/${accountId}").addQueryStringParameters("force" -> force.toString))
			given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
		}
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.AccountsListResponse]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.AccountsListResponse])
		}
		object list {
			def apply(merchantId: String, maxResults: Int, pageToken: String, view: String, label: String, name: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"${merchantId}/accounts").addQueryStringParameters("maxResults" -> maxResults.toString, "pageToken" -> pageToken.toString, "view" -> view.toString, "label" -> label.toString, "name" -> name.toString))
			given Conversion[list, Future[Schema.AccountsListResponse]] = (fun: list) => fun.apply()
		}
		class verifyphonenumber(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withVerifyPhoneNumberRequest(body: Schema.VerifyPhoneNumberRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.VerifyPhoneNumberResponse])
		}
		object verifyphonenumber {
			def apply(merchantId: String, accountId: String)(using auth: AuthToken, ec: ExecutionContext): verifyphonenumber = new verifyphonenumber(ws.url(BASE_URL + s"${merchantId}/accounts/${accountId}/verifyphonenumber").addQueryStringParameters())
		}
		object labels {
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListAccountLabelsResponse]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListAccountLabelsResponse])
			}
			object list {
				def apply(accountId: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"accounts/${accountId}/labels").addQueryStringParameters("pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListAccountLabelsResponse]] = (fun: list) => fun.apply()
			}
			class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withAccountLabel(body: Schema.AccountLabel) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.AccountLabel])
			}
			object create {
				def apply(accountId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"accounts/${accountId}/labels").addQueryStringParameters())
			}
			class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withAccountLabel(body: Schema.AccountLabel) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.AccountLabel])
			}
			object patch {
				def apply(accountId: String, labelId: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"accounts/${accountId}/labels/${labelId}").addQueryStringParameters())
			}
			class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Unit]) {
				def apply() = auth.exec(req,_.execute("DELETE")).map(_ => ())
			}
			object delete {
				def apply(accountId: String, labelId: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"accounts/${accountId}/labels/${labelId}").addQueryStringParameters())
				given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
			}
		}
		object credentials {
			class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withAccountCredentials(body: Schema.AccountCredentials) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.AccountCredentials])
			}
			object create {
				def apply(accountId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"accounts/${accountId}/credentials").addQueryStringParameters())
			}
		}
		object returncarrier {
			class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withAccountReturnCarrier(body: Schema.AccountReturnCarrier) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.AccountReturnCarrier])
			}
			object create {
				def apply(accountId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"accounts/${accountId}/returncarrier").addQueryStringParameters())
			}
			class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withAccountReturnCarrier(body: Schema.AccountReturnCarrier) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.AccountReturnCarrier])
			}
			object patch {
				def apply(accountId: String, carrierAccountId: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"accounts/${accountId}/returncarrier/${carrierAccountId}").addQueryStringParameters())
			}
			class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Unit]) {
				def apply() = auth.exec(req,_.execute("DELETE")).map(_ => ())
			}
			object delete {
				def apply(accountId: String, carrierAccountId: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"accounts/${accountId}/returncarrier/${carrierAccountId}").addQueryStringParameters())
				given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListAccountReturnCarrierResponse]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListAccountReturnCarrierResponse])
			}
			object list {
				def apply(accountId: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"accounts/${accountId}/returncarrier").addQueryStringParameters())
				given Conversion[list, Future[Schema.ListAccountReturnCarrierResponse]] = (fun: list) => fun.apply()
			}
		}
	}
	object accountstatuses {
		class custombatch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withAccountstatusesCustomBatchRequest(body: Schema.AccountstatusesCustomBatchRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.AccountstatusesCustomBatchResponse])
		}
		object custombatch {
			def apply()(using auth: AuthToken, ec: ExecutionContext): custombatch = new custombatch(ws.url(BASE_URL + s"accountstatuses/batch").addQueryStringParameters())
		}
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.AccountStatus]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.AccountStatus])
		}
		object get {
			def apply(merchantId: String, accountId: String, destinations: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"${merchantId}/accountstatuses/${accountId}").addQueryStringParameters("destinations" -> destinations.toString))
			given Conversion[get, Future[Schema.AccountStatus]] = (fun: get) => fun.apply()
		}
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.AccountstatusesListResponse]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.AccountstatusesListResponse])
		}
		object list {
			def apply(merchantId: String, maxResults: Int, pageToken: String, destinations: String, name: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"${merchantId}/accountstatuses").addQueryStringParameters("maxResults" -> maxResults.toString, "pageToken" -> pageToken.toString, "destinations" -> destinations.toString, "name" -> name.toString))
			given Conversion[list, Future[Schema.AccountstatusesListResponse]] = (fun: list) => fun.apply()
		}
	}
	object accounttax {
		class custombatch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withAccounttaxCustomBatchRequest(body: Schema.AccounttaxCustomBatchRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.AccounttaxCustomBatchResponse])
		}
		object custombatch {
			def apply()(using auth: AuthToken, ec: ExecutionContext): custombatch = new custombatch(ws.url(BASE_URL + s"accounttax/batch").addQueryStringParameters())
		}
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.AccountTax]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.AccountTax])
		}
		object get {
			def apply(merchantId: String, accountId: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"${merchantId}/accounttax/${accountId}").addQueryStringParameters())
			given Conversion[get, Future[Schema.AccountTax]] = (fun: get) => fun.apply()
		}
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.AccounttaxListResponse]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.AccounttaxListResponse])
		}
		object list {
			def apply(merchantId: String, maxResults: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"${merchantId}/accounttax").addQueryStringParameters("maxResults" -> maxResults.toString, "pageToken" -> pageToken.toString))
			given Conversion[list, Future[Schema.AccounttaxListResponse]] = (fun: list) => fun.apply()
		}
		class update(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withAccountTax(body: Schema.AccountTax) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.AccountTax])
		}
		object update {
			def apply(merchantId: String, accountId: String)(using auth: AuthToken, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"${merchantId}/accounttax/${accountId}").addQueryStringParameters())
		}
	}
	object datafeeds {
		class insert(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withDatafeed(body: Schema.Datafeed) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Datafeed])
		}
		object insert {
			def apply(merchantId: String)(using auth: AuthToken, ec: ExecutionContext): insert = new insert(ws.url(BASE_URL + s"${merchantId}/datafeeds").addQueryStringParameters())
		}
		class fetchnow(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.DatafeedsFetchNowResponse]) {
			def apply() = auth.exec(req,_.execute("POST")).map(_.json.as[Schema.DatafeedsFetchNowResponse])
		}
		object fetchnow {
			def apply(merchantId: String, datafeedId: String)(using auth: AuthToken, ec: ExecutionContext): fetchnow = new fetchnow(ws.url(BASE_URL + s"${merchantId}/datafeeds/${datafeedId}/fetchNow").addQueryStringParameters())
			given Conversion[fetchnow, Future[Schema.DatafeedsFetchNowResponse]] = (fun: fetchnow) => fun.apply()
		}
		class custombatch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withDatafeedsCustomBatchRequest(body: Schema.DatafeedsCustomBatchRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.DatafeedsCustomBatchResponse])
		}
		object custombatch {
			def apply()(using auth: AuthToken, ec: ExecutionContext): custombatch = new custombatch(ws.url(BASE_URL + s"datafeeds/batch").addQueryStringParameters())
		}
		class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Unit]) {
			def apply() = auth.exec(req,_.execute("DELETE")).map(_ => ())
		}
		object delete {
			def apply(merchantId: String, datafeedId: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"${merchantId}/datafeeds/${datafeedId}").addQueryStringParameters())
			given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
		}
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Datafeed]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Datafeed])
		}
		object get {
			def apply(merchantId: String, datafeedId: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"${merchantId}/datafeeds/${datafeedId}").addQueryStringParameters())
			given Conversion[get, Future[Schema.Datafeed]] = (fun: get) => fun.apply()
		}
		class update(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withDatafeed(body: Schema.Datafeed) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.Datafeed])
		}
		object update {
			def apply(merchantId: String, datafeedId: String)(using auth: AuthToken, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"${merchantId}/datafeeds/${datafeedId}").addQueryStringParameters())
		}
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.DatafeedsListResponse]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.DatafeedsListResponse])
		}
		object list {
			def apply(merchantId: String, maxResults: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"${merchantId}/datafeeds").addQueryStringParameters("maxResults" -> maxResults.toString, "pageToken" -> pageToken.toString))
			given Conversion[list, Future[Schema.DatafeedsListResponse]] = (fun: list) => fun.apply()
		}
	}
	object datafeedstatuses {
		class custombatch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withDatafeedstatusesCustomBatchRequest(body: Schema.DatafeedstatusesCustomBatchRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.DatafeedstatusesCustomBatchResponse])
		}
		object custombatch {
			def apply()(using auth: AuthToken, ec: ExecutionContext): custombatch = new custombatch(ws.url(BASE_URL + s"datafeedstatuses/batch").addQueryStringParameters())
		}
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.DatafeedStatus]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.DatafeedStatus])
		}
		object get {
			def apply(merchantId: String, datafeedId: String, country: String, feedLabel: String, language: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"${merchantId}/datafeedstatuses/${datafeedId}").addQueryStringParameters("country" -> country.toString, "feedLabel" -> feedLabel.toString, "language" -> language.toString))
			given Conversion[get, Future[Schema.DatafeedStatus]] = (fun: get) => fun.apply()
		}
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.DatafeedstatusesListResponse]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.DatafeedstatusesListResponse])
		}
		object list {
			def apply(merchantId: String, maxResults: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"${merchantId}/datafeedstatuses").addQueryStringParameters("maxResults" -> maxResults.toString, "pageToken" -> pageToken.toString))
			given Conversion[list, Future[Schema.DatafeedstatusesListResponse]] = (fun: list) => fun.apply()
		}
	}
	object liasettings {
		class setomnichannelexperience(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.LiaOmnichannelExperience]) {
			def apply() = auth.exec(req,_.execute("POST")).map(_.json.as[Schema.LiaOmnichannelExperience])
		}
		object setomnichannelexperience {
			def apply(merchantId: String, accountId: String, country: String, lsfType: String, pickupTypes: String)(using auth: AuthToken, ec: ExecutionContext): setomnichannelexperience = new setomnichannelexperience(ws.url(BASE_URL + s"${merchantId}/liasettings/${accountId}/setomnichannelexperience").addQueryStringParameters("country" -> country.toString, "lsfType" -> lsfType.toString, "pickupTypes" -> pickupTypes.toString))
			given Conversion[setomnichannelexperience, Future[Schema.LiaOmnichannelExperience]] = (fun: setomnichannelexperience) => fun.apply()
		}
		class requestinventoryverification(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.LiasettingsRequestInventoryVerificationResponse]) {
			def apply() = auth.exec(req,_.execute("POST")).map(_.json.as[Schema.LiasettingsRequestInventoryVerificationResponse])
		}
		object requestinventoryverification {
			def apply(merchantId: String, accountId: String, country: String)(using auth: AuthToken, ec: ExecutionContext): requestinventoryverification = new requestinventoryverification(ws.url(BASE_URL + s"${merchantId}/liasettings/${accountId}/requestinventoryverification/${country}").addQueryStringParameters())
			given Conversion[requestinventoryverification, Future[Schema.LiasettingsRequestInventoryVerificationResponse]] = (fun: requestinventoryverification) => fun.apply()
		}
		class getaccessiblegmbaccounts(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.LiasettingsGetAccessibleGmbAccountsResponse]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.LiasettingsGetAccessibleGmbAccountsResponse])
		}
		object getaccessiblegmbaccounts {
			def apply(merchantId: String, accountId: String)(using auth: AuthToken, ec: ExecutionContext): getaccessiblegmbaccounts = new getaccessiblegmbaccounts(ws.url(BASE_URL + s"${merchantId}/liasettings/${accountId}/accessiblegmbaccounts").addQueryStringParameters())
			given Conversion[getaccessiblegmbaccounts, Future[Schema.LiasettingsGetAccessibleGmbAccountsResponse]] = (fun: getaccessiblegmbaccounts) => fun.apply()
		}
		class custombatch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withLiasettingsCustomBatchRequest(body: Schema.LiasettingsCustomBatchRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.LiasettingsCustomBatchResponse])
		}
		object custombatch {
			def apply()(using auth: AuthToken, ec: ExecutionContext): custombatch = new custombatch(ws.url(BASE_URL + s"liasettings/batch").addQueryStringParameters())
		}
		class requestgmbaccess(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.LiasettingsRequestGmbAccessResponse]) {
			def apply() = auth.exec(req,_.execute("POST")).map(_.json.as[Schema.LiasettingsRequestGmbAccessResponse])
		}
		object requestgmbaccess {
			def apply(merchantId: String, accountId: String, gmbEmail: String)(using auth: AuthToken, ec: ExecutionContext): requestgmbaccess = new requestgmbaccess(ws.url(BASE_URL + s"${merchantId}/liasettings/${accountId}/requestgmbaccess").addQueryStringParameters("gmbEmail" -> gmbEmail.toString))
			given Conversion[requestgmbaccess, Future[Schema.LiasettingsRequestGmbAccessResponse]] = (fun: requestgmbaccess) => fun.apply()
		}
		class listposdataproviders(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.LiasettingsListPosDataProvidersResponse]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.LiasettingsListPosDataProvidersResponse])
		}
		object listposdataproviders {
			def apply()(using auth: AuthToken, ec: ExecutionContext): listposdataproviders = new listposdataproviders(ws.url(BASE_URL + s"liasettings/posdataproviders").addQueryStringParameters())
			given Conversion[listposdataproviders, Future[Schema.LiasettingsListPosDataProvidersResponse]] = (fun: listposdataproviders) => fun.apply()
		}
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.LiaSettings]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.LiaSettings])
		}
		object get {
			def apply(merchantId: String, accountId: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"${merchantId}/liasettings/${accountId}").addQueryStringParameters())
			given Conversion[get, Future[Schema.LiaSettings]] = (fun: get) => fun.apply()
		}
		class update(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withLiaSettings(body: Schema.LiaSettings) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.LiaSettings])
		}
		object update {
			def apply(merchantId: String, accountId: String)(using auth: AuthToken, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"${merchantId}/liasettings/${accountId}").addQueryStringParameters())
		}
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.LiasettingsListResponse]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.LiasettingsListResponse])
		}
		object list {
			def apply(merchantId: String, maxResults: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"${merchantId}/liasettings").addQueryStringParameters("maxResults" -> maxResults.toString, "pageToken" -> pageToken.toString))
			given Conversion[list, Future[Schema.LiasettingsListResponse]] = (fun: list) => fun.apply()
		}
		class setposdataprovider(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.LiasettingsSetPosDataProviderResponse]) {
			def apply() = auth.exec(req,_.execute("POST")).map(_.json.as[Schema.LiasettingsSetPosDataProviderResponse])
		}
		object setposdataprovider {
			def apply(merchantId: String, accountId: String, country: String, posDataProviderId: String, posExternalAccountId: String)(using auth: AuthToken, ec: ExecutionContext): setposdataprovider = new setposdataprovider(ws.url(BASE_URL + s"${merchantId}/liasettings/${accountId}/setposdataprovider").addQueryStringParameters("country" -> country.toString, "posDataProviderId" -> posDataProviderId.toString, "posExternalAccountId" -> posExternalAccountId.toString))
			given Conversion[setposdataprovider, Future[Schema.LiasettingsSetPosDataProviderResponse]] = (fun: setposdataprovider) => fun.apply()
		}
		class setinventoryverificationcontact(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.LiasettingsSetInventoryVerificationContactResponse]) {
			def apply() = auth.exec(req,_.execute("POST")).map(_.json.as[Schema.LiasettingsSetInventoryVerificationContactResponse])
		}
		object setinventoryverificationcontact {
			def apply(merchantId: String, accountId: String, country: String, language: String, contactName: String, contactEmail: String)(using auth: AuthToken, ec: ExecutionContext): setinventoryverificationcontact = new setinventoryverificationcontact(ws.url(BASE_URL + s"${merchantId}/liasettings/${accountId}/setinventoryverificationcontact").addQueryStringParameters("country" -> country.toString, "language" -> language.toString, "contactName" -> contactName.toString, "contactEmail" -> contactEmail.toString))
			given Conversion[setinventoryverificationcontact, Future[Schema.LiasettingsSetInventoryVerificationContactResponse]] = (fun: setinventoryverificationcontact) => fun.apply()
		}
	}
	object localinventory {
		class custombatch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withLocalinventoryCustomBatchRequest(body: Schema.LocalinventoryCustomBatchRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.LocalinventoryCustomBatchResponse])
		}
		object custombatch {
			def apply()(using auth: AuthToken, ec: ExecutionContext): custombatch = new custombatch(ws.url(BASE_URL + s"localinventory/batch").addQueryStringParameters())
		}
		class insert(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withLocalInventory(body: Schema.LocalInventory) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.LocalInventory])
		}
		object insert {
			def apply(merchantId: String, productId: String)(using auth: AuthToken, ec: ExecutionContext): insert = new insert(ws.url(BASE_URL + s"${merchantId}/products/${productId}/localinventory").addQueryStringParameters())
		}
	}
	object pos {
		class inventory(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withPosInventoryRequest(body: Schema.PosInventoryRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.PosInventoryResponse])
		}
		object inventory {
			def apply(merchantId: String, targetMerchantId: String)(using auth: AuthToken, ec: ExecutionContext): inventory = new inventory(ws.url(BASE_URL + s"${merchantId}/pos/${targetMerchantId}/inventory").addQueryStringParameters())
		}
		class insert(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withPosStore(body: Schema.PosStore) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.PosStore])
		}
		object insert {
			def apply(merchantId: String, targetMerchantId: String)(using auth: AuthToken, ec: ExecutionContext): insert = new insert(ws.url(BASE_URL + s"${merchantId}/pos/${targetMerchantId}/store").addQueryStringParameters())
		}
		class sale(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withPosSaleRequest(body: Schema.PosSaleRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.PosSaleResponse])
		}
		object sale {
			def apply(merchantId: String, targetMerchantId: String)(using auth: AuthToken, ec: ExecutionContext): sale = new sale(ws.url(BASE_URL + s"${merchantId}/pos/${targetMerchantId}/sale").addQueryStringParameters())
		}
		class custombatch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withPosCustomBatchRequest(body: Schema.PosCustomBatchRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.PosCustomBatchResponse])
		}
		object custombatch {
			def apply()(using auth: AuthToken, ec: ExecutionContext): custombatch = new custombatch(ws.url(BASE_URL + s"pos/batch").addQueryStringParameters())
		}
		class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Unit]) {
			def apply() = auth.exec(req,_.execute("DELETE")).map(_ => ())
		}
		object delete {
			def apply(merchantId: String, targetMerchantId: String, storeCode: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"${merchantId}/pos/${targetMerchantId}/store/${storeCode}").addQueryStringParameters())
			given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
		}
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.PosStore]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.PosStore])
		}
		object get {
			def apply(merchantId: String, targetMerchantId: String, storeCode: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"${merchantId}/pos/${targetMerchantId}/store/${storeCode}").addQueryStringParameters())
			given Conversion[get, Future[Schema.PosStore]] = (fun: get) => fun.apply()
		}
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.PosListResponse]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.PosListResponse])
		}
		object list {
			def apply(merchantId: String, targetMerchantId: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"${merchantId}/pos/${targetMerchantId}/store").addQueryStringParameters())
			given Conversion[list, Future[Schema.PosListResponse]] = (fun: list) => fun.apply()
		}
	}
	object products {
		class insert(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withProduct(body: Schema.Product) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Product])
		}
		object insert {
			def apply(merchantId: String, feedId: String)(using auth: AuthToken, ec: ExecutionContext): insert = new insert(ws.url(BASE_URL + s"${merchantId}/products").addQueryStringParameters("feedId" -> feedId.toString))
		}
		class custombatch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withProductsCustomBatchRequest(body: Schema.ProductsCustomBatchRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.ProductsCustomBatchResponse])
		}
		object custombatch {
			def apply()(using auth: AuthToken, ec: ExecutionContext): custombatch = new custombatch(ws.url(BASE_URL + s"products/batch").addQueryStringParameters())
		}
		class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Unit]) {
			def apply() = auth.exec(req,_.execute("DELETE")).map(_ => ())
		}
		object delete {
			def apply(merchantId: String, productId: String, feedId: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"${merchantId}/products/${productId}").addQueryStringParameters("feedId" -> feedId.toString))
			given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
		}
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Product]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Product])
		}
		object get {
			def apply(merchantId: String, productId: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"${merchantId}/products/${productId}").addQueryStringParameters())
			given Conversion[get, Future[Schema.Product]] = (fun: get) => fun.apply()
		}
		class update(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withProduct(body: Schema.Product) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Product])
		}
		object update {
			def apply(merchantId: String, productId: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"${merchantId}/products/${productId}").addQueryStringParameters("updateMask" -> updateMask.toString))
		}
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ProductsListResponse]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ProductsListResponse])
		}
		object list {
			def apply(merchantId: String, maxResults: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"${merchantId}/products").addQueryStringParameters("maxResults" -> maxResults.toString, "pageToken" -> pageToken.toString))
			given Conversion[list, Future[Schema.ProductsListResponse]] = (fun: list) => fun.apply()
		}
	}
	object productstatuses {
		class custombatch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withProductstatusesCustomBatchRequest(body: Schema.ProductstatusesCustomBatchRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.ProductstatusesCustomBatchResponse])
		}
		object custombatch {
			def apply()(using auth: AuthToken, ec: ExecutionContext): custombatch = new custombatch(ws.url(BASE_URL + s"productstatuses/batch").addQueryStringParameters())
		}
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ProductStatus]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ProductStatus])
		}
		object get {
			def apply(merchantId: String, productId: String, destinations: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"${merchantId}/productstatuses/${productId}").addQueryStringParameters("destinations" -> destinations.toString))
			given Conversion[get, Future[Schema.ProductStatus]] = (fun: get) => fun.apply()
		}
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ProductstatusesListResponse]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ProductstatusesListResponse])
		}
		object list {
			def apply(merchantId: String, maxResults: Int, pageToken: String, destinations: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"${merchantId}/productstatuses").addQueryStringParameters("maxResults" -> maxResults.toString, "pageToken" -> pageToken.toString, "destinations" -> destinations.toString))
			given Conversion[list, Future[Schema.ProductstatusesListResponse]] = (fun: list) => fun.apply()
		}
	}
	object pubsubnotificationsettings {
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.PubsubNotificationSettings]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.PubsubNotificationSettings])
		}
		object get {
			def apply(merchantId: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"${merchantId}/pubsubnotificationsettings").addQueryStringParameters())
			given Conversion[get, Future[Schema.PubsubNotificationSettings]] = (fun: get) => fun.apply()
		}
		class update(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withPubsubNotificationSettings(body: Schema.PubsubNotificationSettings) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.PubsubNotificationSettings])
		}
		object update {
			def apply(merchantId: String)(using auth: AuthToken, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"${merchantId}/pubsubnotificationsettings").addQueryStringParameters())
		}
	}
	object regionalinventory {
		class custombatch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withRegionalinventoryCustomBatchRequest(body: Schema.RegionalinventoryCustomBatchRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.RegionalinventoryCustomBatchResponse])
		}
		object custombatch {
			def apply()(using auth: AuthToken, ec: ExecutionContext): custombatch = new custombatch(ws.url(BASE_URL + s"regionalinventory/batch").addQueryStringParameters())
		}
		class insert(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withRegionalInventory(body: Schema.RegionalInventory) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.RegionalInventory])
		}
		object insert {
			def apply(merchantId: String, productId: String)(using auth: AuthToken, ec: ExecutionContext): insert = new insert(ws.url(BASE_URL + s"${merchantId}/products/${productId}/regionalinventory").addQueryStringParameters())
		}
	}
	object returnaddress {
		class insert(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withReturnAddress(body: Schema.ReturnAddress) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.ReturnAddress])
		}
		object insert {
			def apply(merchantId: String)(using auth: AuthToken, ec: ExecutionContext): insert = new insert(ws.url(BASE_URL + s"${merchantId}/returnaddress").addQueryStringParameters())
		}
		class custombatch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withReturnaddressCustomBatchRequest(body: Schema.ReturnaddressCustomBatchRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.ReturnaddressCustomBatchResponse])
		}
		object custombatch {
			def apply()(using auth: AuthToken, ec: ExecutionContext): custombatch = new custombatch(ws.url(BASE_URL + s"returnaddress/batch").addQueryStringParameters())
		}
		class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Unit]) {
			def apply() = auth.exec(req,_.execute("DELETE")).map(_ => ())
		}
		object delete {
			def apply(merchantId: String, returnAddressId: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"${merchantId}/returnaddress/${returnAddressId}").addQueryStringParameters())
			given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
		}
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ReturnAddress]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ReturnAddress])
		}
		object get {
			def apply(merchantId: String, returnAddressId: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"${merchantId}/returnaddress/${returnAddressId}").addQueryStringParameters())
			given Conversion[get, Future[Schema.ReturnAddress]] = (fun: get) => fun.apply()
		}
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ReturnaddressListResponse]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ReturnaddressListResponse])
		}
		object list {
			def apply(merchantId: String, maxResults: Int, pageToken: String, country: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"${merchantId}/returnaddress").addQueryStringParameters("maxResults" -> maxResults.toString, "pageToken" -> pageToken.toString, "country" -> country.toString))
			given Conversion[list, Future[Schema.ReturnaddressListResponse]] = (fun: list) => fun.apply()
		}
	}
	object returnpolicy {
		class insert(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withReturnPolicy(body: Schema.ReturnPolicy) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.ReturnPolicy])
		}
		object insert {
			def apply(merchantId: String)(using auth: AuthToken, ec: ExecutionContext): insert = new insert(ws.url(BASE_URL + s"${merchantId}/returnpolicy").addQueryStringParameters())
		}
		class custombatch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withReturnpolicyCustomBatchRequest(body: Schema.ReturnpolicyCustomBatchRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.ReturnpolicyCustomBatchResponse])
		}
		object custombatch {
			def apply()(using auth: AuthToken, ec: ExecutionContext): custombatch = new custombatch(ws.url(BASE_URL + s"returnpolicy/batch").addQueryStringParameters())
		}
		class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Unit]) {
			def apply() = auth.exec(req,_.execute("DELETE")).map(_ => ())
		}
		object delete {
			def apply(merchantId: String, returnPolicyId: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"${merchantId}/returnpolicy/${returnPolicyId}").addQueryStringParameters())
			given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
		}
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ReturnPolicy]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ReturnPolicy])
		}
		object get {
			def apply(merchantId: String, returnPolicyId: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"${merchantId}/returnpolicy/${returnPolicyId}").addQueryStringParameters())
			given Conversion[get, Future[Schema.ReturnPolicy]] = (fun: get) => fun.apply()
		}
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ReturnpolicyListResponse]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ReturnpolicyListResponse])
		}
		object list {
			def apply(merchantId: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"${merchantId}/returnpolicy").addQueryStringParameters())
			given Conversion[list, Future[Schema.ReturnpolicyListResponse]] = (fun: list) => fun.apply()
		}
	}
	object shippingsettings {
		class getsupportedholidays(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ShippingsettingsGetSupportedHolidaysResponse]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ShippingsettingsGetSupportedHolidaysResponse])
		}
		object getsupportedholidays {
			def apply(merchantId: String)(using auth: AuthToken, ec: ExecutionContext): getsupportedholidays = new getsupportedholidays(ws.url(BASE_URL + s"${merchantId}/supportedHolidays").addQueryStringParameters())
			given Conversion[getsupportedholidays, Future[Schema.ShippingsettingsGetSupportedHolidaysResponse]] = (fun: getsupportedholidays) => fun.apply()
		}
		class getsupportedpickupservices(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ShippingsettingsGetSupportedPickupServicesResponse]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ShippingsettingsGetSupportedPickupServicesResponse])
		}
		object getsupportedpickupservices {
			def apply(merchantId: String)(using auth: AuthToken, ec: ExecutionContext): getsupportedpickupservices = new getsupportedpickupservices(ws.url(BASE_URL + s"${merchantId}/supportedPickupServices").addQueryStringParameters())
			given Conversion[getsupportedpickupservices, Future[Schema.ShippingsettingsGetSupportedPickupServicesResponse]] = (fun: getsupportedpickupservices) => fun.apply()
		}
		class custombatch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withShippingsettingsCustomBatchRequest(body: Schema.ShippingsettingsCustomBatchRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.ShippingsettingsCustomBatchResponse])
		}
		object custombatch {
			def apply()(using auth: AuthToken, ec: ExecutionContext): custombatch = new custombatch(ws.url(BASE_URL + s"shippingsettings/batch").addQueryStringParameters())
		}
		class getsupportedcarriers(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ShippingsettingsGetSupportedCarriersResponse]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ShippingsettingsGetSupportedCarriersResponse])
		}
		object getsupportedcarriers {
			def apply(merchantId: String)(using auth: AuthToken, ec: ExecutionContext): getsupportedcarriers = new getsupportedcarriers(ws.url(BASE_URL + s"${merchantId}/supportedCarriers").addQueryStringParameters())
			given Conversion[getsupportedcarriers, Future[Schema.ShippingsettingsGetSupportedCarriersResponse]] = (fun: getsupportedcarriers) => fun.apply()
		}
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ShippingSettings]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ShippingSettings])
		}
		object get {
			def apply(merchantId: String, accountId: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"${merchantId}/shippingsettings/${accountId}").addQueryStringParameters())
			given Conversion[get, Future[Schema.ShippingSettings]] = (fun: get) => fun.apply()
		}
		class update(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withShippingSettings(body: Schema.ShippingSettings) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.ShippingSettings])
		}
		object update {
			def apply(merchantId: String, accountId: String)(using auth: AuthToken, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"${merchantId}/shippingsettings/${accountId}").addQueryStringParameters())
		}
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ShippingsettingsListResponse]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ShippingsettingsListResponse])
		}
		object list {
			def apply(merchantId: String, maxResults: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"${merchantId}/shippingsettings").addQueryStringParameters("maxResults" -> maxResults.toString, "pageToken" -> pageToken.toString))
			given Conversion[list, Future[Schema.ShippingsettingsListResponse]] = (fun: list) => fun.apply()
		}
	}
	object collections {
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Collection]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Collection])
		}
		object get {
			def apply(merchantId: String, collectionId: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"${merchantId}/collections/${collectionId}").addQueryStringParameters())
			given Conversion[get, Future[Schema.Collection]] = (fun: get) => fun.apply()
		}
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListCollectionsResponse]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListCollectionsResponse])
		}
		object list {
			def apply(merchantId: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"${merchantId}/collections").addQueryStringParameters("pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
			given Conversion[list, Future[Schema.ListCollectionsResponse]] = (fun: list) => fun.apply()
		}
		class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withCollection(body: Schema.Collection) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Collection])
		}
		object create {
			def apply(merchantId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"${merchantId}/collections").addQueryStringParameters())
		}
		class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Unit]) {
			def apply() = auth.exec(req,_.execute("DELETE")).map(_ => ())
		}
		object delete {
			def apply(merchantId: String, collectionId: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"${merchantId}/collections/${collectionId}").addQueryStringParameters())
			given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
		}
	}
	object quotas {
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListMethodQuotasResponse]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListMethodQuotasResponse])
		}
		object list {
			def apply(merchantId: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"${merchantId}/quotas").addQueryStringParameters("pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
			given Conversion[list, Future[Schema.ListMethodQuotasResponse]] = (fun: list) => fun.apply()
		}
	}
	object collectionstatuses {
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.CollectionStatus]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.CollectionStatus])
		}
		object get {
			def apply(merchantId: String, collectionId: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"${merchantId}/collectionstatuses/${collectionId}").addQueryStringParameters())
			given Conversion[get, Future[Schema.CollectionStatus]] = (fun: get) => fun.apply()
		}
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListCollectionStatusesResponse]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListCollectionStatusesResponse])
		}
		object list {
			def apply(merchantId: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"${merchantId}/collectionstatuses").addQueryStringParameters("pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
			given Conversion[list, Future[Schema.ListCollectionStatusesResponse]] = (fun: list) => fun.apply()
		}
	}
	object conversionsources {
		class undelete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withUndeleteConversionSourceRequest(body: Schema.UndeleteConversionSourceRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_ => ())
		}
		object undelete {
			def apply(merchantId: String, conversionSourceId: String)(using auth: AuthToken, ec: ExecutionContext): undelete = new undelete(ws.url(BASE_URL + s"${merchantId}/conversionsources/${conversionSourceId}:undelete").addQueryStringParameters())
		}
		class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withConversionSource(body: Schema.ConversionSource) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.ConversionSource])
		}
		object create {
			def apply(merchantId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"${merchantId}/conversionsources").addQueryStringParameters())
		}
		class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Unit]) {
			def apply() = auth.exec(req,_.execute("DELETE")).map(_ => ())
		}
		object delete {
			def apply(merchantId: String, conversionSourceId: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"${merchantId}/conversionsources/${conversionSourceId}").addQueryStringParameters())
			given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
		}
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ConversionSource]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ConversionSource])
		}
		object get {
			def apply(merchantId: String, conversionSourceId: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"${merchantId}/conversionsources/${conversionSourceId}").addQueryStringParameters())
			given Conversion[get, Future[Schema.ConversionSource]] = (fun: get) => fun.apply()
		}
		class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			/** Optional. List of fields being updated.<br>Format: google-fieldmask */
			def withUpdateMask(updateMask: String) = new patch(req.addQueryStringParameters("updateMask" -> updateMask.toString))
			def withConversionSource(body: Schema.ConversionSource) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.ConversionSource])
		}
		object patch {
			def apply(merchantId: String, conversionSourceId: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"${merchantId}/conversionsources/${conversionSourceId}").addQueryStringParameters())
		}
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListConversionSourcesResponse]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListConversionSourcesResponse])
		}
		object list {
			def apply(merchantId: String, pageSize: Int, pageToken: String, showDeleted: Boolean)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"${merchantId}/conversionsources").addQueryStringParameters("pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "showDeleted" -> showDeleted.toString))
			given Conversion[list, Future[Schema.ListConversionSourcesResponse]] = (fun: list) => fun.apply()
		}
	}
	object freelistingsprogram {
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.FreeListingsProgramStatus]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.FreeListingsProgramStatus])
		}
		object get {
			def apply(merchantId: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"${merchantId}/freelistingsprogram").addQueryStringParameters())
			given Conversion[get, Future[Schema.FreeListingsProgramStatus]] = (fun: get) => fun.apply()
		}
		class requestreview(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withRequestReviewFreeListingsRequest(body: Schema.RequestReviewFreeListingsRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_ => ())
		}
		object requestreview {
			def apply(merchantId: String)(using auth: AuthToken, ec: ExecutionContext): requestreview = new requestreview(ws.url(BASE_URL + s"${merchantId}/freelistingsprogram/requestreview").addQueryStringParameters())
		}
		object checkoutsettings {
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.CheckoutSettings]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.CheckoutSettings])
			}
			object get {
				def apply(merchantId: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"${merchantId}/freelistingsprogram/checkoutsettings").addQueryStringParameters())
				given Conversion[get, Future[Schema.CheckoutSettings]] = (fun: get) => fun.apply()
			}
			class insert(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withInsertCheckoutSettingsRequest(body: Schema.InsertCheckoutSettingsRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.CheckoutSettings])
			}
			object insert {
				def apply(merchantId: String)(using auth: AuthToken, ec: ExecutionContext): insert = new insert(ws.url(BASE_URL + s"${merchantId}/freelistingsprogram/checkoutsettings").addQueryStringParameters())
			}
			class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Unit]) {
				def apply() = auth.exec(req,_.execute("DELETE")).map(_ => ())
			}
			object delete {
				def apply(merchantId: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"${merchantId}/freelistingsprogram/checkoutsettings").addQueryStringParameters())
				given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
			}
		}
	}
	object shoppingadsprogram {
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ShoppingAdsProgramStatus]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ShoppingAdsProgramStatus])
		}
		object get {
			def apply(merchantId: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"${merchantId}/shoppingadsprogram").addQueryStringParameters())
			given Conversion[get, Future[Schema.ShoppingAdsProgramStatus]] = (fun: get) => fun.apply()
		}
		class requestreview(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withRequestReviewShoppingAdsRequest(body: Schema.RequestReviewShoppingAdsRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_ => ())
		}
		object requestreview {
			def apply(merchantId: String)(using auth: AuthToken, ec: ExecutionContext): requestreview = new requestreview(ws.url(BASE_URL + s"${merchantId}/shoppingadsprogram/requestreview").addQueryStringParameters())
		}
	}
	object csses {
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListCssesResponse]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListCssesResponse])
		}
		object list {
			def apply(cssGroupId: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"${cssGroupId}/csses").addQueryStringParameters("pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
			given Conversion[list, Future[Schema.ListCssesResponse]] = (fun: list) => fun.apply()
		}
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Css]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Css])
		}
		object get {
			def apply(cssGroupId: String, cssDomainId: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"${cssGroupId}/csses/${cssDomainId}").addQueryStringParameters())
			given Conversion[get, Future[Schema.Css]] = (fun: get) => fun.apply()
		}
		class updatelabels(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withLabelIds(body: Schema.LabelIds) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Css])
		}
		object updatelabels {
			def apply(cssGroupId: String, cssDomainId: String)(using auth: AuthToken, ec: ExecutionContext): updatelabels = new updatelabels(ws.url(BASE_URL + s"${cssGroupId}/csses/${cssDomainId}/updatelabels").addQueryStringParameters())
		}
	}
	object reports {
		class search(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withSearchRequest(body: Schema.SearchRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.SearchResponse])
		}
		object search {
			def apply(merchantId: String)(using auth: AuthToken, ec: ExecutionContext): search = new search(ws.url(BASE_URL + s"${merchantId}/reports/search").addQueryStringParameters())
		}
	}
	object merchantsupport {
		class renderaccountissues(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			/** Optional. The [IETF BCP-47](https://tools.ietf.org/html/bcp47) language code used to localize support content. If not set, the result will be in default language `en-US`. */
			def withLanguageCode(languageCode: String) = new renderaccountissues(req.addQueryStringParameters("languageCode" -> languageCode.toString))
			/** Optional. The [IANA](https://www.iana.org/time-zones) timezone used to localize times in support content. For example 'America/Los_Angeles'. If not set, results will use as a default UTC. */
			def withTimeZone(timeZone: String) = new renderaccountissues(req.addQueryStringParameters("timeZone" -> timeZone.toString))
			def withRenderAccountIssuesRequestPayload(body: Schema.RenderAccountIssuesRequestPayload) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.RenderAccountIssuesResponse])
		}
		object renderaccountissues {
			def apply(merchantId: String)(using auth: AuthToken, ec: ExecutionContext): renderaccountissues = new renderaccountissues(ws.url(BASE_URL + s"${merchantId}/merchantsupport/renderaccountissues").addQueryStringParameters())
		}
		class renderproductissues(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			/** Optional. The [IETF BCP-47](https://tools.ietf.org/html/bcp47) language code used to localize support content. If not set, the result will be in default language `en-US`. */
			def withLanguageCode(languageCode: String) = new renderproductissues(req.addQueryStringParameters("languageCode" -> languageCode.toString))
			/** Optional. The [IANA](https://www.iana.org/time-zones) timezone used to localize times in support content. For example 'America/Los_Angeles'. If not set, results will use as a default UTC. */
			def withTimeZone(timeZone: String) = new renderproductissues(req.addQueryStringParameters("timeZone" -> timeZone.toString))
			def withRenderProductIssuesRequestPayload(body: Schema.RenderProductIssuesRequestPayload) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.RenderProductIssuesResponse])
		}
		object renderproductissues {
			def apply(merchantId: String, productId: String)(using auth: AuthToken, ec: ExecutionContext): renderproductissues = new renderproductissues(ws.url(BASE_URL + s"${merchantId}/merchantsupport/renderproductissues/${productId}").addQueryStringParameters())
		}
		class triggeraction(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			/** Optional. Language code [IETF BCP 47 syntax](https://tools.ietf.org/html/bcp47) used to localize the response. If not set, the result will be in default language `en-US`. */
			def withLanguageCode(languageCode: String) = new triggeraction(req.addQueryStringParameters("languageCode" -> languageCode.toString))
			def withTriggerActionPayload(body: Schema.TriggerActionPayload) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.TriggerActionResponse])
		}
		object triggeraction {
			def apply(merchantId: String)(using auth: AuthToken, ec: ExecutionContext): triggeraction = new triggeraction(ws.url(BASE_URL + s"${merchantId}/merchantsupport/triggeraction").addQueryStringParameters())
		}
	}
	object regions {
		class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withRegion(body: Schema.Region) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Region])
		}
		object create {
			def apply(merchantId: String, regionId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"${merchantId}/regions").addQueryStringParameters("regionId" -> regionId.toString))
		}
		class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Unit]) {
			def apply() = auth.exec(req,_.execute("DELETE")).map(_ => ())
		}
		object delete {
			def apply(merchantId: String, regionId: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"${merchantId}/regions/${regionId}").addQueryStringParameters())
			given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
		}
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Region]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Region])
		}
		object get {
			def apply(merchantId: String, regionId: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"${merchantId}/regions/${regionId}").addQueryStringParameters())
			given Conversion[get, Future[Schema.Region]] = (fun: get) => fun.apply()
		}
		class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			/** Optional. The comma-separated field mask indicating the fields to update. Example: `"displayName,postalCodeArea.regionCode"`.<br>Format: google-fieldmask */
			def withUpdateMask(updateMask: String) = new patch(req.addQueryStringParameters("updateMask" -> updateMask.toString))
			def withRegion(body: Schema.Region) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Region])
		}
		object patch {
			def apply(merchantId: String, regionId: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"${merchantId}/regions/${regionId}").addQueryStringParameters())
		}
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListRegionsResponse]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListRegionsResponse])
		}
		object list {
			def apply(merchantId: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"${merchantId}/regions").addQueryStringParameters("pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
			given Conversion[list, Future[Schema.ListRegionsResponse]] = (fun: list) => fun.apply()
		}
	}
	object promotions {
		class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withPromotion(body: Schema.Promotion) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Promotion])
		}
		object create {
			def apply(merchantId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"${merchantId}/promotions").addQueryStringParameters())
		}
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Promotion]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Promotion])
		}
		object get {
			def apply(merchantId: String, id: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"${merchantId}/promotions/${id}").addQueryStringParameters())
			given Conversion[get, Future[Schema.Promotion]] = (fun: get) => fun.apply()
		}
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListPromotionResponse]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListPromotionResponse])
		}
		object list {
			def apply(merchantId: String, pageSize: Int, pageToken: String, countryCode: String, languageCode: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"${merchantId}/promotions").addQueryStringParameters("pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "countryCode" -> countryCode.toString, "languageCode" -> languageCode.toString))
			given Conversion[list, Future[Schema.ListPromotionResponse]] = (fun: list) => fun.apply()
		}
	}
	object recommendations {
		class generate(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GenerateRecommendationsResponse]) {
			/** Optional. Language code of the client. If not set, the result will be in default language (English). This language code affects all fields prefixed with "localized". This should be set to ISO 639-1 country code. List of currently verified supported language code: en, fr, cs, da, de, es, it, nl, no, pl, pt, pt, fi, sv, vi, tr, th, ko, zh-CN, zh-TW, ja, id, hi */
			def withLanguageCode(languageCode: String) = new generate(req.addQueryStringParameters("languageCode" -> languageCode.toString))
			/** Optional. List of allowed tags. Tags are a set of predefined strings that describe the category that individual recommendation types belong to. User can specify zero or more tags in this field to indicate what categories of recommendations they want to receive. Current list of supported tags: - TREND */
			def withAllowedTag(allowedTag: String) = new generate(req.addQueryStringParameters("allowedTag" -> allowedTag.toString))
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GenerateRecommendationsResponse])
		}
		object generate {
			def apply(merchantId: String)(using auth: AuthToken, ec: ExecutionContext): generate = new generate(ws.url(BASE_URL + s"${merchantId}/recommendations/generate").addQueryStringParameters())
			given Conversion[generate, Future[Schema.GenerateRecommendationsResponse]] = (fun: generate) => fun.apply()
		}
		class reportInteraction(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withReportInteractionRequest(body: Schema.ReportInteractionRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_ => ())
		}
		object reportInteraction {
			def apply(merchantId: String)(using auth: AuthToken, ec: ExecutionContext): reportInteraction = new reportInteraction(ws.url(BASE_URL + s"${merchantId}/recommendations/reportInteraction").addQueryStringParameters())
		}
	}
	object returnpolicyonline {
		class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withReturnPolicyOnline(body: Schema.ReturnPolicyOnline) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.ReturnPolicyOnline])
		}
		object create {
			def apply(merchantId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"${merchantId}/returnpolicyonline").addQueryStringParameters())
		}
		class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Unit]) {
			def apply() = auth.exec(req,_.execute("DELETE")).map(_ => ())
		}
		object delete {
			def apply(merchantId: String, returnPolicyId: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"${merchantId}/returnpolicyonline/${returnPolicyId}").addQueryStringParameters())
			given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
		}
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ReturnPolicyOnline]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ReturnPolicyOnline])
		}
		object get {
			def apply(merchantId: String, returnPolicyId: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"${merchantId}/returnpolicyonline/${returnPolicyId}").addQueryStringParameters())
			given Conversion[get, Future[Schema.ReturnPolicyOnline]] = (fun: get) => fun.apply()
		}
		class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withReturnPolicyOnline(body: Schema.ReturnPolicyOnline) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.ReturnPolicyOnline])
		}
		object patch {
			def apply(merchantId: String, returnPolicyId: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"${merchantId}/returnpolicyonline/${returnPolicyId}").addQueryStringParameters())
		}
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListReturnPolicyOnlineResponse]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListReturnPolicyOnlineResponse])
		}
		object list {
			def apply(merchantId: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"${merchantId}/returnpolicyonline").addQueryStringParameters())
			given Conversion[list, Future[Schema.ListReturnPolicyOnlineResponse]] = (fun: list) => fun.apply()
		}
	}
	object ordertrackingsignals {
		class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withOrderTrackingSignal(body: Schema.OrderTrackingSignal) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.OrderTrackingSignal])
		}
		object create {
			def apply(merchantId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"${merchantId}/ordertrackingsignals").addQueryStringParameters())
		}
	}
	object productdeliverytime {
		class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withProductDeliveryTime(body: Schema.ProductDeliveryTime) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.ProductDeliveryTime])
		}
		object create {
			def apply(merchantId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"${merchantId}/productdeliverytime").addQueryStringParameters())
		}
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ProductDeliveryTime]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ProductDeliveryTime])
		}
		object get {
			def apply(merchantId: String, productId: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"${merchantId}/productdeliverytime/${productId}").addQueryStringParameters())
			given Conversion[get, Future[Schema.ProductDeliveryTime]] = (fun: get) => fun.apply()
		}
		class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Unit]) {
			def apply() = auth.exec(req,_.execute("DELETE")).map(_ => ())
		}
		object delete {
			def apply(merchantId: String, productId: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"${merchantId}/productdeliverytime/${productId}").addQueryStringParameters())
			given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
		}
	}
}
