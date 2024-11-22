package com.boresjo.play.api.google.adsense

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://adsense.googleapis.com/"

	object accounts {
		class getAdBlockingRecoveryTag(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.AdBlockingRecoveryTag]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.AdBlockingRecoveryTag])
		}
		object getAdBlockingRecoveryTag {
			def apply(accountsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): getAdBlockingRecoveryTag = new getAdBlockingRecoveryTag(ws.url(BASE_URL + s"v2/accounts/${accountsId}/adBlockingRecoveryTag").addQueryStringParameters("name" -> name.toString))
			given Conversion[getAdBlockingRecoveryTag, Future[Schema.AdBlockingRecoveryTag]] = (fun: getAdBlockingRecoveryTag) => fun.apply()
		}
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Account]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Account])
		}
		object get {
			def apply(accountsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/accounts/${accountsId}").addQueryStringParameters("name" -> name.toString))
			given Conversion[get, Future[Schema.Account]] = (fun: get) => fun.apply()
		}
		class listChildAccounts(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListChildAccountsResponse]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListChildAccountsResponse])
		}
		object listChildAccounts {
			def apply(accountsId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): listChildAccounts = new listChildAccounts(ws.url(BASE_URL + s"v2/accounts/${accountsId}:listChildAccounts").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
			given Conversion[listChildAccounts, Future[Schema.ListChildAccountsResponse]] = (fun: listChildAccounts) => fun.apply()
		}
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListAccountsResponse]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListAccountsResponse])
		}
		object list {
			def apply(pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/accounts").addQueryStringParameters("pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
			given Conversion[list, Future[Schema.ListAccountsResponse]] = (fun: list) => fun.apply()
		}
		object adclients {
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListAdClientsResponse]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListAdClientsResponse])
			}
			object list {
				def apply(accountsId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/accounts/${accountsId}/adclients").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListAdClientsResponse]] = (fun: list) => fun.apply()
			}
			class getAdcode(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.AdClientAdCode]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.AdClientAdCode])
			}
			object getAdcode {
				def apply(accountsId :PlayApi, adclientsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): getAdcode = new getAdcode(ws.url(BASE_URL + s"v2/accounts/${accountsId}/adclients/${adclientsId}/adcode").addQueryStringParameters("name" -> name.toString))
				given Conversion[getAdcode, Future[Schema.AdClientAdCode]] = (fun: getAdcode) => fun.apply()
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.AdClient]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.AdClient])
			}
			object get {
				def apply(accountsId :PlayApi, adclientsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/accounts/${accountsId}/adclients/${adclientsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.AdClient]] = (fun: get) => fun.apply()
			}
			object adunits {
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withAdUnit(body: Schema.AdUnit) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.AdUnit])
				}
				object create {
					def apply(accountsId :PlayApi, adclientsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v2/accounts/${accountsId}/adclients/${adclientsId}/adunits").addQueryStringParameters("parent" -> parent.toString))
				}
				class listLinkedCustomChannels(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListLinkedCustomChannelsResponse]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListLinkedCustomChannelsResponse])
				}
				object listLinkedCustomChannels {
					def apply(accountsId :PlayApi, adclientsId :PlayApi, adunitsId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): listLinkedCustomChannels = new listLinkedCustomChannels(ws.url(BASE_URL + s"v2/accounts/${accountsId}/adclients/${adclientsId}/adunits/${adunitsId}:listLinkedCustomChannels").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[listLinkedCustomChannels, Future[Schema.ListLinkedCustomChannelsResponse]] = (fun: listLinkedCustomChannels) => fun.apply()
				}
				class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withAdUnit(body: Schema.AdUnit) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.AdUnit])
				}
				object patch {
					def apply(accountsId :PlayApi, adclientsId :PlayApi, adunitsId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v2/accounts/${accountsId}/adclients/${adclientsId}/adunits/${adunitsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListAdUnitsResponse]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListAdUnitsResponse])
				}
				object list {
					def apply(accountsId :PlayApi, adclientsId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/accounts/${accountsId}/adclients/${adclientsId}/adunits").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListAdUnitsResponse]] = (fun: list) => fun.apply()
				}
				class getAdcode(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.AdUnitAdCode]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.AdUnitAdCode])
				}
				object getAdcode {
					def apply(accountsId :PlayApi, adclientsId :PlayApi, adunitsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): getAdcode = new getAdcode(ws.url(BASE_URL + s"v2/accounts/${accountsId}/adclients/${adclientsId}/adunits/${adunitsId}/adcode").addQueryStringParameters("name" -> name.toString))
					given Conversion[getAdcode, Future[Schema.AdUnitAdCode]] = (fun: getAdcode) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.AdUnit]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.AdUnit])
				}
				object get {
					def apply(accountsId :PlayApi, adclientsId :PlayApi, adunitsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/accounts/${accountsId}/adclients/${adclientsId}/adunits/${adunitsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.AdUnit]] = (fun: get) => fun.apply()
				}
			}
			object urlchannels {
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.UrlChannel]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.UrlChannel])
				}
				object get {
					def apply(accountsId :PlayApi, adclientsId :PlayApi, urlchannelsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/accounts/${accountsId}/adclients/${adclientsId}/urlchannels/${urlchannelsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.UrlChannel]] = (fun: get) => fun.apply()
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListUrlChannelsResponse]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListUrlChannelsResponse])
				}
				object list {
					def apply(accountsId :PlayApi, adclientsId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/accounts/${accountsId}/adclients/${adclientsId}/urlchannels").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListUrlChannelsResponse]] = (fun: list) => fun.apply()
				}
			}
			object customchannels {
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
				}
				object delete {
					def apply(accountsId :PlayApi, adclientsId :PlayApi, customchannelsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v2/accounts/${accountsId}/adclients/${adclientsId}/customchannels/${customchannelsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.CustomChannel]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.CustomChannel])
				}
				object get {
					def apply(accountsId :PlayApi, adclientsId :PlayApi, customchannelsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/accounts/${accountsId}/adclients/${adclientsId}/customchannels/${customchannelsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.CustomChannel]] = (fun: get) => fun.apply()
				}
				class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withCustomChannel(body: Schema.CustomChannel) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.CustomChannel])
				}
				object patch {
					def apply(accountsId :PlayApi, adclientsId :PlayApi, customchannelsId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v2/accounts/${accountsId}/adclients/${adclientsId}/customchannels/${customchannelsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListCustomChannelsResponse]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListCustomChannelsResponse])
				}
				object list {
					def apply(accountsId :PlayApi, adclientsId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/accounts/${accountsId}/adclients/${adclientsId}/customchannels").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListCustomChannelsResponse]] = (fun: list) => fun.apply()
				}
				class listLinkedAdUnits(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListLinkedAdUnitsResponse]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListLinkedAdUnitsResponse])
				}
				object listLinkedAdUnits {
					def apply(accountsId :PlayApi, adclientsId :PlayApi, customchannelsId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): listLinkedAdUnits = new listLinkedAdUnits(ws.url(BASE_URL + s"v2/accounts/${accountsId}/adclients/${adclientsId}/customchannels/${customchannelsId}:listLinkedAdUnits").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[listLinkedAdUnits, Future[Schema.ListLinkedAdUnitsResponse]] = (fun: listLinkedAdUnits) => fun.apply()
				}
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withCustomChannel(body: Schema.CustomChannel) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.CustomChannel])
				}
				object create {
					def apply(accountsId :PlayApi, adclientsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v2/accounts/${accountsId}/adclients/${adclientsId}/customchannels").addQueryStringParameters("parent" -> parent.toString))
				}
			}
		}
		object reports {
			class getSaved(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.SavedReport]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.SavedReport])
			}
			object getSaved {
				def apply(accountsId :PlayApi, reportsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): getSaved = new getSaved(ws.url(BASE_URL + s"v2/accounts/${accountsId}/reports/${reportsId}/saved").addQueryStringParameters("name" -> name.toString))
				given Conversion[getSaved, Future[Schema.SavedReport]] = (fun: getSaved) => fun.apply()
			}
			class generate(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ReportResult]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ReportResult])
			}
			object generate {
				def apply(accountsId :PlayApi, account: String, dimensions: String, metrics: String, filters: String, dateRange: String, startDateYear: Int, startDateMonth: Int, startDateDay: Int, endDateYear: Int, endDateMonth: Int, endDateDay: Int, orderBy: String, languageCode: String, currencyCode: String, limit: Int, reportingTimeZone: String)(using auth: AuthToken, ec: ExecutionContext): generate = new generate(ws.url(BASE_URL + s"v2/accounts/${accountsId}/reports:generate").addQueryStringParameters("account" -> account.toString, "dimensions" -> dimensions.toString, "metrics" -> metrics.toString, "filters" -> filters.toString, "dateRange" -> dateRange.toString, "startDate.year" -> startDateYear.toString, "startDate.month" -> startDateMonth.toString, "startDate.day" -> startDateDay.toString, "endDate.year" -> endDateYear.toString, "endDate.month" -> endDateMonth.toString, "endDate.day" -> endDateDay.toString, "orderBy" -> orderBy.toString, "languageCode" -> languageCode.toString, "currencyCode" -> currencyCode.toString, "limit" -> limit.toString, "reportingTimeZone" -> reportingTimeZone.toString))
				given Conversion[generate, Future[Schema.ReportResult]] = (fun: generate) => fun.apply()
			}
			class generateCsv(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.HttpBody]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.HttpBody])
			}
			object generateCsv {
				def apply(accountsId :PlayApi, account: String, dimensions: String, metrics: String, filters: String, dateRange: String, startDateYear: Int, startDateMonth: Int, startDateDay: Int, endDateYear: Int, endDateMonth: Int, endDateDay: Int, orderBy: String, languageCode: String, currencyCode: String, limit: Int, reportingTimeZone: String)(using auth: AuthToken, ec: ExecutionContext): generateCsv = new generateCsv(ws.url(BASE_URL + s"v2/accounts/${accountsId}/reports:generateCsv").addQueryStringParameters("account" -> account.toString, "dimensions" -> dimensions.toString, "metrics" -> metrics.toString, "filters" -> filters.toString, "dateRange" -> dateRange.toString, "startDate.year" -> startDateYear.toString, "startDate.month" -> startDateMonth.toString, "startDate.day" -> startDateDay.toString, "endDate.year" -> endDateYear.toString, "endDate.month" -> endDateMonth.toString, "endDate.day" -> endDateDay.toString, "orderBy" -> orderBy.toString, "languageCode" -> languageCode.toString, "currencyCode" -> currencyCode.toString, "limit" -> limit.toString, "reportingTimeZone" -> reportingTimeZone.toString))
				given Conversion[generateCsv, Future[Schema.HttpBody]] = (fun: generateCsv) => fun.apply()
			}
			object saved {
				class generate(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ReportResult]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ReportResult])
				}
				object generate {
					def apply(accountsId :PlayApi, reportsId :PlayApi, name: String, dateRange: String, startDateYear: Int, startDateMonth: Int, startDateDay: Int, endDateYear: Int, endDateMonth: Int, endDateDay: Int, languageCode: String, currencyCode: String, reportingTimeZone: String)(using auth: AuthToken, ec: ExecutionContext): generate = new generate(ws.url(BASE_URL + s"v2/accounts/${accountsId}/reports/${reportsId}/saved:generate").addQueryStringParameters("name" -> name.toString, "dateRange" -> dateRange.toString, "startDate.year" -> startDateYear.toString, "startDate.month" -> startDateMonth.toString, "startDate.day" -> startDateDay.toString, "endDate.year" -> endDateYear.toString, "endDate.month" -> endDateMonth.toString, "endDate.day" -> endDateDay.toString, "languageCode" -> languageCode.toString, "currencyCode" -> currencyCode.toString, "reportingTimeZone" -> reportingTimeZone.toString))
					given Conversion[generate, Future[Schema.ReportResult]] = (fun: generate) => fun.apply()
				}
				class generateCsv(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.HttpBody]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.HttpBody])
				}
				object generateCsv {
					def apply(accountsId :PlayApi, reportsId :PlayApi, name: String, dateRange: String, startDateYear: Int, startDateMonth: Int, startDateDay: Int, endDateYear: Int, endDateMonth: Int, endDateDay: Int, languageCode: String, currencyCode: String, reportingTimeZone: String)(using auth: AuthToken, ec: ExecutionContext): generateCsv = new generateCsv(ws.url(BASE_URL + s"v2/accounts/${accountsId}/reports/${reportsId}/saved:generateCsv").addQueryStringParameters("name" -> name.toString, "dateRange" -> dateRange.toString, "startDate.year" -> startDateYear.toString, "startDate.month" -> startDateMonth.toString, "startDate.day" -> startDateDay.toString, "endDate.year" -> endDateYear.toString, "endDate.month" -> endDateMonth.toString, "endDate.day" -> endDateDay.toString, "languageCode" -> languageCode.toString, "currencyCode" -> currencyCode.toString, "reportingTimeZone" -> reportingTimeZone.toString))
					given Conversion[generateCsv, Future[Schema.HttpBody]] = (fun: generateCsv) => fun.apply()
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListSavedReportsResponse]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListSavedReportsResponse])
				}
				object list {
					def apply(accountsId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/accounts/${accountsId}/reports/saved").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListSavedReportsResponse]] = (fun: list) => fun.apply()
				}
			}
		}
		object payments {
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListPaymentsResponse]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListPaymentsResponse])
			}
			object list {
				def apply(accountsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/accounts/${accountsId}/payments").addQueryStringParameters("parent" -> parent.toString))
				given Conversion[list, Future[Schema.ListPaymentsResponse]] = (fun: list) => fun.apply()
			}
		}
		object sites {
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Site]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Site])
			}
			object get {
				def apply(accountsId :PlayApi, sitesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/accounts/${accountsId}/sites/${sitesId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.Site]] = (fun: get) => fun.apply()
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListSitesResponse]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListSitesResponse])
			}
			object list {
				def apply(accountsId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/accounts/${accountsId}/sites").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListSitesResponse]] = (fun: list) => fun.apply()
			}
		}
		object alerts {
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListAlertsResponse]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListAlertsResponse])
			}
			object list {
				def apply(accountsId :PlayApi, parent: String, languageCode: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/accounts/${accountsId}/alerts").addQueryStringParameters("parent" -> parent.toString, "languageCode" -> languageCode.toString))
				given Conversion[list, Future[Schema.ListAlertsResponse]] = (fun: list) => fun.apply()
			}
		}
		object policyIssues {
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.PolicyIssue]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.PolicyIssue])
			}
			object get {
				def apply(accountsId :PlayApi, policyIssuesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/accounts/${accountsId}/policyIssues/${policyIssuesId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.PolicyIssue]] = (fun: get) => fun.apply()
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListPolicyIssuesResponse]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListPolicyIssuesResponse])
			}
			object list {
				def apply(accountsId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/accounts/${accountsId}/policyIssues").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListPolicyIssuesResponse]] = (fun: list) => fun.apply()
			}
		}
	}
}
