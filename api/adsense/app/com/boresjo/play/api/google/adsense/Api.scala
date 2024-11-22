package com.boresjo.play.api.google.adsense

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
		"""https://www.googleapis.com/auth/adsense""" /* View and manage your AdSense data */,
		"""https://www.googleapis.com/auth/adsense.readonly""" /* View your AdSense data */
	)

	private val BASE_URL = "https://adsense.googleapis.com/"

	object accounts {
		/** Gets the ad blocking recovery tag of an account. */
		class getAdBlockingRecoveryTag(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.AdBlockingRecoveryTag]) {
			val scopes = Seq("""https://www.googleapis.com/auth/adsense""", """https://www.googleapis.com/auth/adsense.readonly""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.AdBlockingRecoveryTag])
		}
		object getAdBlockingRecoveryTag {
			def apply(accountsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): getAdBlockingRecoveryTag = new getAdBlockingRecoveryTag(ws.url(BASE_URL + s"v2/accounts/${accountsId}/adBlockingRecoveryTag").addQueryStringParameters("name" -> name.toString))
			given Conversion[getAdBlockingRecoveryTag, Future[Schema.AdBlockingRecoveryTag]] = (fun: getAdBlockingRecoveryTag) => fun.apply()
		}
		/** Gets information about the selected AdSense account. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Account]) {
			val scopes = Seq("""https://www.googleapis.com/auth/adsense""", """https://www.googleapis.com/auth/adsense.readonly""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Account])
		}
		object get {
			def apply(accountsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/accounts/${accountsId}").addQueryStringParameters("name" -> name.toString))
			given Conversion[get, Future[Schema.Account]] = (fun: get) => fun.apply()
		}
		/** Lists all accounts directly managed by the given AdSense account. */
		class listChildAccounts(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListChildAccountsResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/adsense""", """https://www.googleapis.com/auth/adsense.readonly""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListChildAccountsResponse])
		}
		object listChildAccounts {
			def apply(accountsId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): listChildAccounts = new listChildAccounts(ws.url(BASE_URL + s"v2/accounts/${accountsId}:listChildAccounts").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
			given Conversion[listChildAccounts, Future[Schema.ListChildAccountsResponse]] = (fun: listChildAccounts) => fun.apply()
		}
		/** Lists all accounts available to this user. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListAccountsResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/adsense""", """https://www.googleapis.com/auth/adsense.readonly""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListAccountsResponse])
		}
		object list {
			def apply(pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/accounts").addQueryStringParameters("pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
			given Conversion[list, Future[Schema.ListAccountsResponse]] = (fun: list) => fun.apply()
		}
		object adclients {
			/** Lists all the ad clients available in an account. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListAdClientsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/adsense""", """https://www.googleapis.com/auth/adsense.readonly""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListAdClientsResponse])
			}
			object list {
				def apply(accountsId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/accounts/${accountsId}/adclients").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListAdClientsResponse]] = (fun: list) => fun.apply()
			}
			/** Gets the AdSense code for a given ad client. This returns what was previously known as the 'auto ad code'. This is only supported for ad clients with a product_code of AFC. For more information, see [About the AdSense code](https://support.google.com/adsense/answer/9274634). */
			class getAdcode(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.AdClientAdCode]) {
				val scopes = Seq("""https://www.googleapis.com/auth/adsense""", """https://www.googleapis.com/auth/adsense.readonly""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.AdClientAdCode])
			}
			object getAdcode {
				def apply(accountsId :PlayApi, adclientsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): getAdcode = new getAdcode(ws.url(BASE_URL + s"v2/accounts/${accountsId}/adclients/${adclientsId}/adcode").addQueryStringParameters("name" -> name.toString))
				given Conversion[getAdcode, Future[Schema.AdClientAdCode]] = (fun: getAdcode) => fun.apply()
			}
			/** Gets the ad client from the given resource name. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.AdClient]) {
				val scopes = Seq("""https://www.googleapis.com/auth/adsense""", """https://www.googleapis.com/auth/adsense.readonly""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.AdClient])
			}
			object get {
				def apply(accountsId :PlayApi, adclientsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/accounts/${accountsId}/adclients/${adclientsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.AdClient]] = (fun: get) => fun.apply()
			}
			object adunits {
				/** Creates an ad unit. This method can be called only by a restricted set of projects, which are usually owned by [AdSense for Platforms](https://developers.google.com/adsense/platforms/) publishers. Contact your account manager if you need to use this method. Note that ad units can only be created for ad clients with an "AFC" product code. For more info see the [AdClient resource](/adsense/management/reference/rest/v2/accounts.adclients). For now, this method can only be used to create `DISPLAY` ad units. See: https://support.google.com/adsense/answer/9183566 */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/adsense""")
					/** Perform the request */
					def withAdUnit(body: Schema.AdUnit) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.AdUnit])
				}
				object create {
					def apply(accountsId :PlayApi, adclientsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v2/accounts/${accountsId}/adclients/${adclientsId}/adunits").addQueryStringParameters("parent" -> parent.toString))
				}
				/** Lists all the custom channels available for an ad unit. */
				class listLinkedCustomChannels(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListLinkedCustomChannelsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/adsense""", """https://www.googleapis.com/auth/adsense.readonly""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListLinkedCustomChannelsResponse])
				}
				object listLinkedCustomChannels {
					def apply(accountsId :PlayApi, adclientsId :PlayApi, adunitsId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): listLinkedCustomChannels = new listLinkedCustomChannels(ws.url(BASE_URL + s"v2/accounts/${accountsId}/adclients/${adclientsId}/adunits/${adunitsId}:listLinkedCustomChannels").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[listLinkedCustomChannels, Future[Schema.ListLinkedCustomChannelsResponse]] = (fun: listLinkedCustomChannels) => fun.apply()
				}
				/** Updates an ad unit. This method can be called only by a restricted set of projects, which are usually owned by [AdSense for Platforms](https://developers.google.com/adsense/platforms/) publishers. Contact your account manager if you need to use this method. For now, this method can only be used to update `DISPLAY` ad units. See: https://support.google.com/adsense/answer/9183566 */
				class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/adsense""")
					/** Perform the request */
					def withAdUnit(body: Schema.AdUnit) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.AdUnit])
				}
				object patch {
					def apply(accountsId :PlayApi, adclientsId :PlayApi, adunitsId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v2/accounts/${accountsId}/adclients/${adclientsId}/adunits/${adunitsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
				}
				/** Lists all ad units under a specified account and ad client. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListAdUnitsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/adsense""", """https://www.googleapis.com/auth/adsense.readonly""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListAdUnitsResponse])
				}
				object list {
					def apply(accountsId :PlayApi, adclientsId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/accounts/${accountsId}/adclients/${adclientsId}/adunits").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListAdUnitsResponse]] = (fun: list) => fun.apply()
				}
				/** Gets the ad unit code for a given ad unit. For more information, see [About the AdSense code](https://support.google.com/adsense/answer/9274634) and [Where to place the ad code in your HTML](https://support.google.com/adsense/answer/9190028). */
				class getAdcode(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.AdUnitAdCode]) {
					val scopes = Seq("""https://www.googleapis.com/auth/adsense""", """https://www.googleapis.com/auth/adsense.readonly""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.AdUnitAdCode])
				}
				object getAdcode {
					def apply(accountsId :PlayApi, adclientsId :PlayApi, adunitsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): getAdcode = new getAdcode(ws.url(BASE_URL + s"v2/accounts/${accountsId}/adclients/${adclientsId}/adunits/${adunitsId}/adcode").addQueryStringParameters("name" -> name.toString))
					given Conversion[getAdcode, Future[Schema.AdUnitAdCode]] = (fun: getAdcode) => fun.apply()
				}
				/** Gets an ad unit from a specified account and ad client. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.AdUnit]) {
					val scopes = Seq("""https://www.googleapis.com/auth/adsense""", """https://www.googleapis.com/auth/adsense.readonly""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.AdUnit])
				}
				object get {
					def apply(accountsId :PlayApi, adclientsId :PlayApi, adunitsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/accounts/${accountsId}/adclients/${adclientsId}/adunits/${adunitsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.AdUnit]] = (fun: get) => fun.apply()
				}
			}
			object urlchannels {
				/** Gets information about the selected url channel. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.UrlChannel]) {
					val scopes = Seq("""https://www.googleapis.com/auth/adsense""", """https://www.googleapis.com/auth/adsense.readonly""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.UrlChannel])
				}
				object get {
					def apply(accountsId :PlayApi, adclientsId :PlayApi, urlchannelsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/accounts/${accountsId}/adclients/${adclientsId}/urlchannels/${urlchannelsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.UrlChannel]] = (fun: get) => fun.apply()
				}
				/** Lists active url channels. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListUrlChannelsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/adsense""", """https://www.googleapis.com/auth/adsense.readonly""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListUrlChannelsResponse])
				}
				object list {
					def apply(accountsId :PlayApi, adclientsId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/accounts/${accountsId}/adclients/${adclientsId}/urlchannels").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListUrlChannelsResponse]] = (fun: list) => fun.apply()
				}
			}
			object customchannels {
				/** Deletes a custom channel. This method can be called only by a restricted set of projects, which are usually owned by [AdSense for Platforms](https://developers.google.com/adsense/platforms/) publishers. Contact your account manager if you need to use this method. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					val scopes = Seq("""https://www.googleapis.com/auth/adsense""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
				}
				object delete {
					def apply(accountsId :PlayApi, adclientsId :PlayApi, customchannelsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v2/accounts/${accountsId}/adclients/${adclientsId}/customchannels/${customchannelsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
				}
				/** Gets information about the selected custom channel. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.CustomChannel]) {
					val scopes = Seq("""https://www.googleapis.com/auth/adsense""", """https://www.googleapis.com/auth/adsense.readonly""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.CustomChannel])
				}
				object get {
					def apply(accountsId :PlayApi, adclientsId :PlayApi, customchannelsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/accounts/${accountsId}/adclients/${adclientsId}/customchannels/${customchannelsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.CustomChannel]] = (fun: get) => fun.apply()
				}
				/** Updates a custom channel. This method can be called only by a restricted set of projects, which are usually owned by [AdSense for Platforms](https://developers.google.com/adsense/platforms/) publishers. Contact your account manager if you need to use this method. */
				class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/adsense""")
					/** Perform the request */
					def withCustomChannel(body: Schema.CustomChannel) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.CustomChannel])
				}
				object patch {
					def apply(accountsId :PlayApi, adclientsId :PlayApi, customchannelsId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v2/accounts/${accountsId}/adclients/${adclientsId}/customchannels/${customchannelsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
				}
				/** Lists all the custom channels available in an ad client. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListCustomChannelsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/adsense""", """https://www.googleapis.com/auth/adsense.readonly""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListCustomChannelsResponse])
				}
				object list {
					def apply(accountsId :PlayApi, adclientsId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/accounts/${accountsId}/adclients/${adclientsId}/customchannels").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListCustomChannelsResponse]] = (fun: list) => fun.apply()
				}
				/** Lists all the ad units available for a custom channel. */
				class listLinkedAdUnits(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListLinkedAdUnitsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/adsense""", """https://www.googleapis.com/auth/adsense.readonly""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListLinkedAdUnitsResponse])
				}
				object listLinkedAdUnits {
					def apply(accountsId :PlayApi, adclientsId :PlayApi, customchannelsId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): listLinkedAdUnits = new listLinkedAdUnits(ws.url(BASE_URL + s"v2/accounts/${accountsId}/adclients/${adclientsId}/customchannels/${customchannelsId}:listLinkedAdUnits").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[listLinkedAdUnits, Future[Schema.ListLinkedAdUnitsResponse]] = (fun: listLinkedAdUnits) => fun.apply()
				}
				/** Creates a custom channel. This method can be called only by a restricted set of projects, which are usually owned by [AdSense for Platforms](https://developers.google.com/adsense/platforms/) publishers. Contact your account manager if you need to use this method. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/adsense""")
					/** Perform the request */
					def withCustomChannel(body: Schema.CustomChannel) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.CustomChannel])
				}
				object create {
					def apply(accountsId :PlayApi, adclientsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v2/accounts/${accountsId}/adclients/${adclientsId}/customchannels").addQueryStringParameters("parent" -> parent.toString))
				}
			}
		}
		object reports {
			/** Gets the saved report from the given resource name. */
			class getSaved(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.SavedReport]) {
				val scopes = Seq("""https://www.googleapis.com/auth/adsense""", """https://www.googleapis.com/auth/adsense.readonly""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.SavedReport])
			}
			object getSaved {
				def apply(accountsId :PlayApi, reportsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): getSaved = new getSaved(ws.url(BASE_URL + s"v2/accounts/${accountsId}/reports/${reportsId}/saved").addQueryStringParameters("name" -> name.toString))
				given Conversion[getSaved, Future[Schema.SavedReport]] = (fun: getSaved) => fun.apply()
			}
			/** Generates an ad hoc report. */
			class generate(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ReportResult]) {
				val scopes = Seq("""https://www.googleapis.com/auth/adsense""", """https://www.googleapis.com/auth/adsense.readonly""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ReportResult])
			}
			object generate {
				def apply(accountsId :PlayApi, account: String, dimensions: String, metrics: String, filters: String, dateRange: String, startDateYear: Int, startDateMonth: Int, startDateDay: Int, endDateYear: Int, endDateMonth: Int, endDateDay: Int, orderBy: String, languageCode: String, currencyCode: String, limit: Int, reportingTimeZone: String)(using signer: RequestSigner, ec: ExecutionContext): generate = new generate(ws.url(BASE_URL + s"v2/accounts/${accountsId}/reports:generate").addQueryStringParameters("account" -> account.toString, "dimensions" -> dimensions.toString, "metrics" -> metrics.toString, "filters" -> filters.toString, "dateRange" -> dateRange.toString, "startDate.year" -> startDateYear.toString, "startDate.month" -> startDateMonth.toString, "startDate.day" -> startDateDay.toString, "endDate.year" -> endDateYear.toString, "endDate.month" -> endDateMonth.toString, "endDate.day" -> endDateDay.toString, "orderBy" -> orderBy.toString, "languageCode" -> languageCode.toString, "currencyCode" -> currencyCode.toString, "limit" -> limit.toString, "reportingTimeZone" -> reportingTimeZone.toString))
				given Conversion[generate, Future[Schema.ReportResult]] = (fun: generate) => fun.apply()
			}
			/** Generates a csv formatted ad hoc report. */
			class generateCsv(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.HttpBody]) {
				val scopes = Seq("""https://www.googleapis.com/auth/adsense""", """https://www.googleapis.com/auth/adsense.readonly""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.HttpBody])
			}
			object generateCsv {
				def apply(accountsId :PlayApi, account: String, dimensions: String, metrics: String, filters: String, dateRange: String, startDateYear: Int, startDateMonth: Int, startDateDay: Int, endDateYear: Int, endDateMonth: Int, endDateDay: Int, orderBy: String, languageCode: String, currencyCode: String, limit: Int, reportingTimeZone: String)(using signer: RequestSigner, ec: ExecutionContext): generateCsv = new generateCsv(ws.url(BASE_URL + s"v2/accounts/${accountsId}/reports:generateCsv").addQueryStringParameters("account" -> account.toString, "dimensions" -> dimensions.toString, "metrics" -> metrics.toString, "filters" -> filters.toString, "dateRange" -> dateRange.toString, "startDate.year" -> startDateYear.toString, "startDate.month" -> startDateMonth.toString, "startDate.day" -> startDateDay.toString, "endDate.year" -> endDateYear.toString, "endDate.month" -> endDateMonth.toString, "endDate.day" -> endDateDay.toString, "orderBy" -> orderBy.toString, "languageCode" -> languageCode.toString, "currencyCode" -> currencyCode.toString, "limit" -> limit.toString, "reportingTimeZone" -> reportingTimeZone.toString))
				given Conversion[generateCsv, Future[Schema.HttpBody]] = (fun: generateCsv) => fun.apply()
			}
			object saved {
				/** Generates a saved report. */
				class generate(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ReportResult]) {
					val scopes = Seq("""https://www.googleapis.com/auth/adsense""", """https://www.googleapis.com/auth/adsense.readonly""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ReportResult])
				}
				object generate {
					def apply(accountsId :PlayApi, reportsId :PlayApi, name: String, dateRange: String, startDateYear: Int, startDateMonth: Int, startDateDay: Int, endDateYear: Int, endDateMonth: Int, endDateDay: Int, languageCode: String, currencyCode: String, reportingTimeZone: String)(using signer: RequestSigner, ec: ExecutionContext): generate = new generate(ws.url(BASE_URL + s"v2/accounts/${accountsId}/reports/${reportsId}/saved:generate").addQueryStringParameters("name" -> name.toString, "dateRange" -> dateRange.toString, "startDate.year" -> startDateYear.toString, "startDate.month" -> startDateMonth.toString, "startDate.day" -> startDateDay.toString, "endDate.year" -> endDateYear.toString, "endDate.month" -> endDateMonth.toString, "endDate.day" -> endDateDay.toString, "languageCode" -> languageCode.toString, "currencyCode" -> currencyCode.toString, "reportingTimeZone" -> reportingTimeZone.toString))
					given Conversion[generate, Future[Schema.ReportResult]] = (fun: generate) => fun.apply()
				}
				/** Generates a csv formatted saved report. */
				class generateCsv(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.HttpBody]) {
					val scopes = Seq("""https://www.googleapis.com/auth/adsense""", """https://www.googleapis.com/auth/adsense.readonly""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.HttpBody])
				}
				object generateCsv {
					def apply(accountsId :PlayApi, reportsId :PlayApi, name: String, dateRange: String, startDateYear: Int, startDateMonth: Int, startDateDay: Int, endDateYear: Int, endDateMonth: Int, endDateDay: Int, languageCode: String, currencyCode: String, reportingTimeZone: String)(using signer: RequestSigner, ec: ExecutionContext): generateCsv = new generateCsv(ws.url(BASE_URL + s"v2/accounts/${accountsId}/reports/${reportsId}/saved:generateCsv").addQueryStringParameters("name" -> name.toString, "dateRange" -> dateRange.toString, "startDate.year" -> startDateYear.toString, "startDate.month" -> startDateMonth.toString, "startDate.day" -> startDateDay.toString, "endDate.year" -> endDateYear.toString, "endDate.month" -> endDateMonth.toString, "endDate.day" -> endDateDay.toString, "languageCode" -> languageCode.toString, "currencyCode" -> currencyCode.toString, "reportingTimeZone" -> reportingTimeZone.toString))
					given Conversion[generateCsv, Future[Schema.HttpBody]] = (fun: generateCsv) => fun.apply()
				}
				/** Lists saved reports. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListSavedReportsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/adsense""", """https://www.googleapis.com/auth/adsense.readonly""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListSavedReportsResponse])
				}
				object list {
					def apply(accountsId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/accounts/${accountsId}/reports/saved").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListSavedReportsResponse]] = (fun: list) => fun.apply()
				}
			}
		}
		object payments {
			/** Lists all the payments available for an account. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListPaymentsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/adsense""", """https://www.googleapis.com/auth/adsense.readonly""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListPaymentsResponse])
			}
			object list {
				def apply(accountsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/accounts/${accountsId}/payments").addQueryStringParameters("parent" -> parent.toString))
				given Conversion[list, Future[Schema.ListPaymentsResponse]] = (fun: list) => fun.apply()
			}
		}
		object sites {
			/** Gets information about the selected site. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Site]) {
				val scopes = Seq("""https://www.googleapis.com/auth/adsense""", """https://www.googleapis.com/auth/adsense.readonly""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Site])
			}
			object get {
				def apply(accountsId :PlayApi, sitesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/accounts/${accountsId}/sites/${sitesId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.Site]] = (fun: get) => fun.apply()
			}
			/** Lists all the sites available in an account. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListSitesResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/adsense""", """https://www.googleapis.com/auth/adsense.readonly""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListSitesResponse])
			}
			object list {
				def apply(accountsId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/accounts/${accountsId}/sites").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListSitesResponse]] = (fun: list) => fun.apply()
			}
		}
		object alerts {
			/** Lists all the alerts available in an account. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListAlertsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/adsense""", """https://www.googleapis.com/auth/adsense.readonly""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListAlertsResponse])
			}
			object list {
				def apply(accountsId :PlayApi, parent: String, languageCode: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/accounts/${accountsId}/alerts").addQueryStringParameters("parent" -> parent.toString, "languageCode" -> languageCode.toString))
				given Conversion[list, Future[Schema.ListAlertsResponse]] = (fun: list) => fun.apply()
			}
		}
		object policyIssues {
			/** Gets information about the selected policy issue. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.PolicyIssue]) {
				val scopes = Seq("""https://www.googleapis.com/auth/adsense""", """https://www.googleapis.com/auth/adsense.readonly""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.PolicyIssue])
			}
			object get {
				def apply(accountsId :PlayApi, policyIssuesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/accounts/${accountsId}/policyIssues/${policyIssuesId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.PolicyIssue]] = (fun: get) => fun.apply()
			}
			/** Lists all the policy issues where the specified account is involved, both directly and through any AFP child accounts. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListPolicyIssuesResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/adsense""", """https://www.googleapis.com/auth/adsense.readonly""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListPolicyIssuesResponse])
			}
			object list {
				def apply(accountsId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/accounts/${accountsId}/policyIssues").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListPolicyIssuesResponse]] = (fun: list) => fun.apply()
			}
		}
	}
}
