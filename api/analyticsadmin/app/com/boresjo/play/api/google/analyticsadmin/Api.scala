package com.boresjo.play.api.google.analyticsadmin

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
		"""https://www.googleapis.com/auth/analytics.readonly""" /* See and download your Google Analytics data */,
		"""https://www.googleapis.com/auth/analytics.edit""" /* Edit Google Analytics management entities */
	)

	private val BASE_URL = "https://analyticsadmin.googleapis.com/"

	object accountSummaries {
		/** Returns summaries of all accounts accessible by the caller. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleAnalyticsAdminV1betaListAccountSummariesResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/analytics.edit""", """https://www.googleapis.com/auth/analytics.readonly""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleAnalyticsAdminV1betaListAccountSummariesResponse])
		}
		object list {
			def apply(pageToken: String, pageSize: Int)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1beta/accountSummaries").addQueryStringParameters("pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString))
			given Conversion[list, Future[Schema.GoogleAnalyticsAdminV1betaListAccountSummariesResponse]] = (fun: list) => fun.apply()
		}
	}
	object accounts {
		/** Get data sharing settings on an account. Data sharing settings are singletons. */
		class getDataSharingSettings(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleAnalyticsAdminV1betaDataSharingSettings]) {
			val scopes = Seq("""https://www.googleapis.com/auth/analytics.edit""", """https://www.googleapis.com/auth/analytics.readonly""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleAnalyticsAdminV1betaDataSharingSettings])
		}
		object getDataSharingSettings {
			def apply(accountsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): getDataSharingSettings = new getDataSharingSettings(ws.url(BASE_URL + s"v1beta/accounts/${accountsId}/dataSharingSettings").addQueryStringParameters("name" -> name.toString))
			given Conversion[getDataSharingSettings, Future[Schema.GoogleAnalyticsAdminV1betaDataSharingSettings]] = (fun: getDataSharingSettings) => fun.apply()
		}
		/** Requests a ticket for creating an account. */
		class provisionAccountTicket(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/analytics.edit""")
			/** Perform the request */
			def withGoogleAnalyticsAdminV1betaProvisionAccountTicketRequest(body: Schema.GoogleAnalyticsAdminV1betaProvisionAccountTicketRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleAnalyticsAdminV1betaProvisionAccountTicketResponse])
		}
		object provisionAccountTicket {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): provisionAccountTicket = new provisionAccountTicket(ws.url(BASE_URL + s"v1beta/accounts:provisionAccountTicket").addQueryStringParameters())
		}
		/** Searches through all changes to an account or its children given the specified set of filters. */
		class searchChangeHistoryEvents(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/analytics.edit""")
			/** Perform the request */
			def withGoogleAnalyticsAdminV1betaSearchChangeHistoryEventsRequest(body: Schema.GoogleAnalyticsAdminV1betaSearchChangeHistoryEventsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleAnalyticsAdminV1betaSearchChangeHistoryEventsResponse])
		}
		object searchChangeHistoryEvents {
			def apply(accountsId :PlayApi, account: String)(using signer: RequestSigner, ec: ExecutionContext): searchChangeHistoryEvents = new searchChangeHistoryEvents(ws.url(BASE_URL + s"v1beta/accounts/${accountsId}:searchChangeHistoryEvents").addQueryStringParameters("account" -> account.toString))
		}
		/** Marks target Account as soft-deleted (ie: "trashed") and returns it. This API does not have a method to restore soft-deleted accounts. However, they can be restored using the Trash Can UI. If the accounts are not restored before the expiration time, the account and all child resources (eg: Properties, GoogleAdsLinks, Streams, AccessBindings) will be permanently purged. https://support.google.com/analytics/answer/6154772 Returns an error if the target is not found. */
		class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
			val scopes = Seq("""https://www.googleapis.com/auth/analytics.edit""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
		}
		object delete {
			def apply(accountsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1beta/accounts/${accountsId}").addQueryStringParameters("name" -> name.toString))
			given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
		}
		/** Returns a customized report of data access records. The report provides records of each time a user reads Google Analytics reporting data. Access records are retained for up to 2 years. Data Access Reports can be requested for a property. Reports may be requested for any property, but dimensions that aren't related to quota can only be requested on Google Analytics 360 properties. This method is only available to Administrators. These data access records include GA UI Reporting, GA UI Explorations, GA Data API, and other products like Firebase & Admob that can retrieve data from Google Analytics through a linkage. These records don't include property configuration changes like adding a stream or changing a property's time zone. For configuration change history, see [searchChangeHistoryEvents](https://developers.google.com/analytics/devguides/config/admin/v1/rest/v1alpha/accounts/searchChangeHistoryEvents). To give your feedback on this API, complete the [Google Analytics Access Reports feedback](https://docs.google.com/forms/d/e/1FAIpQLSdmEBUrMzAEdiEKk5TV5dEHvDUZDRlgWYdQdAeSdtR4hVjEhw/viewform) form. */
		class runAccessReport(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/analytics.edit""", """https://www.googleapis.com/auth/analytics.readonly""")
			/** Perform the request */
			def withGoogleAnalyticsAdminV1betaRunAccessReportRequest(body: Schema.GoogleAnalyticsAdminV1betaRunAccessReportRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleAnalyticsAdminV1betaRunAccessReportResponse])
		}
		object runAccessReport {
			def apply(accountsId :PlayApi, entity: String)(using signer: RequestSigner, ec: ExecutionContext): runAccessReport = new runAccessReport(ws.url(BASE_URL + s"v1beta/accounts/${accountsId}:runAccessReport").addQueryStringParameters("entity" -> entity.toString))
		}
		/** Lookup for a single Account. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleAnalyticsAdminV1betaAccount]) {
			val scopes = Seq("""https://www.googleapis.com/auth/analytics.edit""", """https://www.googleapis.com/auth/analytics.readonly""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleAnalyticsAdminV1betaAccount])
		}
		object get {
			def apply(accountsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1beta/accounts/${accountsId}").addQueryStringParameters("name" -> name.toString))
			given Conversion[get, Future[Schema.GoogleAnalyticsAdminV1betaAccount]] = (fun: get) => fun.apply()
		}
		/** Updates an account. */
		class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/analytics.edit""")
			/** Perform the request */
			def withGoogleAnalyticsAdminV1betaAccount(body: Schema.GoogleAnalyticsAdminV1betaAccount) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleAnalyticsAdminV1betaAccount])
		}
		object patch {
			def apply(accountsId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1beta/accounts/${accountsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
		}
		/** Returns all accounts accessible by the caller. Note that these accounts might not currently have GA properties. Soft-deleted (ie: "trashed") accounts are excluded by default. Returns an empty list if no relevant accounts are found. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleAnalyticsAdminV1betaListAccountsResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/analytics.edit""", """https://www.googleapis.com/auth/analytics.readonly""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleAnalyticsAdminV1betaListAccountsResponse])
		}
		object list {
			def apply(showDeleted: Boolean, pageToken: String, pageSize: Int)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1beta/accounts").addQueryStringParameters("showDeleted" -> showDeleted.toString, "pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString))
			given Conversion[list, Future[Schema.GoogleAnalyticsAdminV1betaListAccountsResponse]] = (fun: list) => fun.apply()
		}
	}
	object properties {
		/** Marks target Property as soft-deleted (ie: "trashed") and returns it. This API does not have a method to restore soft-deleted properties. However, they can be restored using the Trash Can UI. If the properties are not restored before the expiration time, the Property and all child resources (eg: GoogleAdsLinks, Streams, AccessBindings) will be permanently purged. https://support.google.com/analytics/answer/6154772 Returns an error if the target is not found. */
		class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleAnalyticsAdminV1betaProperty]) {
			val scopes = Seq("""https://www.googleapis.com/auth/analytics.edit""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleAnalyticsAdminV1betaProperty])
		}
		object delete {
			def apply(propertiesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1beta/properties/${propertiesId}").addQueryStringParameters("name" -> name.toString))
			given Conversion[delete, Future[Schema.GoogleAnalyticsAdminV1betaProperty]] = (fun: delete) => fun.apply()
		}
		/** Returns a customized report of data access records. The report provides records of each time a user reads Google Analytics reporting data. Access records are retained for up to 2 years. Data Access Reports can be requested for a property. Reports may be requested for any property, but dimensions that aren't related to quota can only be requested on Google Analytics 360 properties. This method is only available to Administrators. These data access records include GA UI Reporting, GA UI Explorations, GA Data API, and other products like Firebase & Admob that can retrieve data from Google Analytics through a linkage. These records don't include property configuration changes like adding a stream or changing a property's time zone. For configuration change history, see [searchChangeHistoryEvents](https://developers.google.com/analytics/devguides/config/admin/v1/rest/v1alpha/accounts/searchChangeHistoryEvents). To give your feedback on this API, complete the [Google Analytics Access Reports feedback](https://docs.google.com/forms/d/e/1FAIpQLSdmEBUrMzAEdiEKk5TV5dEHvDUZDRlgWYdQdAeSdtR4hVjEhw/viewform) form. */
		class runAccessReport(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/analytics.edit""", """https://www.googleapis.com/auth/analytics.readonly""")
			/** Perform the request */
			def withGoogleAnalyticsAdminV1betaRunAccessReportRequest(body: Schema.GoogleAnalyticsAdminV1betaRunAccessReportRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleAnalyticsAdminV1betaRunAccessReportResponse])
		}
		object runAccessReport {
			def apply(propertiesId :PlayApi, entity: String)(using signer: RequestSigner, ec: ExecutionContext): runAccessReport = new runAccessReport(ws.url(BASE_URL + s"v1beta/properties/${propertiesId}:runAccessReport").addQueryStringParameters("entity" -> entity.toString))
		}
		/** Lookup for a single GA Property. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleAnalyticsAdminV1betaProperty]) {
			val scopes = Seq("""https://www.googleapis.com/auth/analytics.edit""", """https://www.googleapis.com/auth/analytics.readonly""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleAnalyticsAdminV1betaProperty])
		}
		object get {
			def apply(propertiesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1beta/properties/${propertiesId}").addQueryStringParameters("name" -> name.toString))
			given Conversion[get, Future[Schema.GoogleAnalyticsAdminV1betaProperty]] = (fun: get) => fun.apply()
		}
		/** Updates a property. */
		class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/analytics.edit""")
			/** Perform the request */
			def withGoogleAnalyticsAdminV1betaProperty(body: Schema.GoogleAnalyticsAdminV1betaProperty) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleAnalyticsAdminV1betaProperty])
		}
		object patch {
			def apply(propertiesId :PlayApi, updateMask: String, name: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1beta/properties/${propertiesId}").addQueryStringParameters("updateMask" -> updateMask.toString, "name" -> name.toString))
		}
		/** Returns child Properties under the specified parent Account. Properties will be excluded if the caller does not have access. Soft-deleted (ie: "trashed") properties are excluded by default. Returns an empty list if no relevant properties are found. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleAnalyticsAdminV1betaListPropertiesResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/analytics.edit""", """https://www.googleapis.com/auth/analytics.readonly""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleAnalyticsAdminV1betaListPropertiesResponse])
		}
		object list {
			def apply(filter: String, pageToken: String, showDeleted: Boolean, pageSize: Int)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1beta/properties").addQueryStringParameters("filter" -> filter.toString, "pageToken" -> pageToken.toString, "showDeleted" -> showDeleted.toString, "pageSize" -> pageSize.toString))
			given Conversion[list, Future[Schema.GoogleAnalyticsAdminV1betaListPropertiesResponse]] = (fun: list) => fun.apply()
		}
		/** Creates a Google Analytics property with the specified location and attributes. */
		class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/analytics.edit""")
			/** Perform the request */
			def withGoogleAnalyticsAdminV1betaProperty(body: Schema.GoogleAnalyticsAdminV1betaProperty) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleAnalyticsAdminV1betaProperty])
		}
		object create {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1beta/properties").addQueryStringParameters())
		}
		/** Returns the singleton data retention settings for this property. */
		class getDataRetentionSettings(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleAnalyticsAdminV1betaDataRetentionSettings]) {
			val scopes = Seq("""https://www.googleapis.com/auth/analytics.edit""", """https://www.googleapis.com/auth/analytics.readonly""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleAnalyticsAdminV1betaDataRetentionSettings])
		}
		object getDataRetentionSettings {
			def apply(propertiesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): getDataRetentionSettings = new getDataRetentionSettings(ws.url(BASE_URL + s"v1beta/properties/${propertiesId}/dataRetentionSettings").addQueryStringParameters("name" -> name.toString))
			given Conversion[getDataRetentionSettings, Future[Schema.GoogleAnalyticsAdminV1betaDataRetentionSettings]] = (fun: getDataRetentionSettings) => fun.apply()
		}
		/** Updates the singleton data retention settings for this property. */
		class updateDataRetentionSettings(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/analytics.edit""")
			/** Perform the request */
			def withGoogleAnalyticsAdminV1betaDataRetentionSettings(body: Schema.GoogleAnalyticsAdminV1betaDataRetentionSettings) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleAnalyticsAdminV1betaDataRetentionSettings])
		}
		object updateDataRetentionSettings {
			def apply(propertiesId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): updateDataRetentionSettings = new updateDataRetentionSettings(ws.url(BASE_URL + s"v1beta/properties/${propertiesId}/dataRetentionSettings").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
		}
		/** Acknowledges the terms of user data collection for the specified property. This acknowledgement must be completed (either in the Google Analytics UI or through this API) before MeasurementProtocolSecret resources may be created. */
		class acknowledgeUserDataCollection(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/analytics.edit""")
			/** Perform the request */
			def withGoogleAnalyticsAdminV1betaAcknowledgeUserDataCollectionRequest(body: Schema.GoogleAnalyticsAdminV1betaAcknowledgeUserDataCollectionRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleAnalyticsAdminV1betaAcknowledgeUserDataCollectionResponse])
		}
		object acknowledgeUserDataCollection {
			def apply(propertiesId :PlayApi, property: String)(using signer: RequestSigner, ec: ExecutionContext): acknowledgeUserDataCollection = new acknowledgeUserDataCollection(ws.url(BASE_URL + s"v1beta/properties/${propertiesId}:acknowledgeUserDataCollection").addQueryStringParameters("property" -> property.toString))
		}
		object conversionEvents {
			/** Deprecated: Use `CreateKeyEvent` instead. Creates a conversion event with the specified attributes. */
			class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/analytics.edit""")
				/** Perform the request */
				def withGoogleAnalyticsAdminV1betaConversionEvent(body: Schema.GoogleAnalyticsAdminV1betaConversionEvent) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleAnalyticsAdminV1betaConversionEvent])
			}
			object create {
				def apply(propertiesId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1beta/properties/${propertiesId}/conversionEvents").addQueryStringParameters("parent" -> parent.toString))
			}
			/** Deprecated: Use `DeleteKeyEvent` instead. Deletes a conversion event in a property. */
			class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
				val scopes = Seq("""https://www.googleapis.com/auth/analytics.edit""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
			}
			object delete {
				def apply(propertiesId :PlayApi, conversionEventsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1beta/properties/${propertiesId}/conversionEvents/${conversionEventsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
			}
			/** Deprecated: Use `GetKeyEvent` instead. Retrieve a single conversion event. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleAnalyticsAdminV1betaConversionEvent]) {
				val scopes = Seq("""https://www.googleapis.com/auth/analytics.edit""", """https://www.googleapis.com/auth/analytics.readonly""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleAnalyticsAdminV1betaConversionEvent])
			}
			object get {
				def apply(propertiesId :PlayApi, conversionEventsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1beta/properties/${propertiesId}/conversionEvents/${conversionEventsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.GoogleAnalyticsAdminV1betaConversionEvent]] = (fun: get) => fun.apply()
			}
			/** Deprecated: Use `UpdateKeyEvent` instead. Updates a conversion event with the specified attributes. */
			class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/analytics.edit""")
				/** Perform the request */
				def withGoogleAnalyticsAdminV1betaConversionEvent(body: Schema.GoogleAnalyticsAdminV1betaConversionEvent) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleAnalyticsAdminV1betaConversionEvent])
			}
			object patch {
				def apply(propertiesId :PlayApi, conversionEventsId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1beta/properties/${propertiesId}/conversionEvents/${conversionEventsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
			}
			/** Deprecated: Use `ListKeyEvents` instead. Returns a list of conversion events in the specified parent property. Returns an empty list if no conversion events are found. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleAnalyticsAdminV1betaListConversionEventsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/analytics.edit""", """https://www.googleapis.com/auth/analytics.readonly""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleAnalyticsAdminV1betaListConversionEventsResponse])
			}
			object list {
				def apply(propertiesId :PlayApi, parent: String, pageToken: String, pageSize: Int)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1beta/properties/${propertiesId}/conversionEvents").addQueryStringParameters("parent" -> parent.toString, "pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString))
				given Conversion[list, Future[Schema.GoogleAnalyticsAdminV1betaListConversionEventsResponse]] = (fun: list) => fun.apply()
			}
		}
		object keyEvents {
			/** Creates a Key Event. */
			class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/analytics.edit""")
				/** Perform the request */
				def withGoogleAnalyticsAdminV1betaKeyEvent(body: Schema.GoogleAnalyticsAdminV1betaKeyEvent) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleAnalyticsAdminV1betaKeyEvent])
			}
			object create {
				def apply(propertiesId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1beta/properties/${propertiesId}/keyEvents").addQueryStringParameters("parent" -> parent.toString))
			}
			/** Deletes a Key Event. */
			class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
				val scopes = Seq("""https://www.googleapis.com/auth/analytics.edit""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
			}
			object delete {
				def apply(propertiesId :PlayApi, keyEventsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1beta/properties/${propertiesId}/keyEvents/${keyEventsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
			}
			/** Retrieve a single Key Event. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleAnalyticsAdminV1betaKeyEvent]) {
				val scopes = Seq("""https://www.googleapis.com/auth/analytics.edit""", """https://www.googleapis.com/auth/analytics.readonly""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleAnalyticsAdminV1betaKeyEvent])
			}
			object get {
				def apply(propertiesId :PlayApi, keyEventsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1beta/properties/${propertiesId}/keyEvents/${keyEventsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.GoogleAnalyticsAdminV1betaKeyEvent]] = (fun: get) => fun.apply()
			}
			/** Updates a Key Event. */
			class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/analytics.edit""")
				/** Perform the request */
				def withGoogleAnalyticsAdminV1betaKeyEvent(body: Schema.GoogleAnalyticsAdminV1betaKeyEvent) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleAnalyticsAdminV1betaKeyEvent])
			}
			object patch {
				def apply(propertiesId :PlayApi, keyEventsId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1beta/properties/${propertiesId}/keyEvents/${keyEventsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
			}
			/** Returns a list of Key Events in the specified parent property. Returns an empty list if no Key Events are found. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleAnalyticsAdminV1betaListKeyEventsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/analytics.edit""", """https://www.googleapis.com/auth/analytics.readonly""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleAnalyticsAdminV1betaListKeyEventsResponse])
			}
			object list {
				def apply(propertiesId :PlayApi, pageToken: String, pageSize: Int, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1beta/properties/${propertiesId}/keyEvents").addQueryStringParameters("pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString, "parent" -> parent.toString))
				given Conversion[list, Future[Schema.GoogleAnalyticsAdminV1betaListKeyEventsResponse]] = (fun: list) => fun.apply()
			}
		}
		object googleAdsLinks {
			/** Lists GoogleAdsLinks on a property. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleAnalyticsAdminV1betaListGoogleAdsLinksResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/analytics.edit""", """https://www.googleapis.com/auth/analytics.readonly""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleAnalyticsAdminV1betaListGoogleAdsLinksResponse])
			}
			object list {
				def apply(propertiesId :PlayApi, parent: String, pageToken: String, pageSize: Int)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1beta/properties/${propertiesId}/googleAdsLinks").addQueryStringParameters("parent" -> parent.toString, "pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString))
				given Conversion[list, Future[Schema.GoogleAnalyticsAdminV1betaListGoogleAdsLinksResponse]] = (fun: list) => fun.apply()
			}
			/** Updates a GoogleAdsLink on a property */
			class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/analytics.edit""")
				/** Perform the request */
				def withGoogleAnalyticsAdminV1betaGoogleAdsLink(body: Schema.GoogleAnalyticsAdminV1betaGoogleAdsLink) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleAnalyticsAdminV1betaGoogleAdsLink])
			}
			object patch {
				def apply(propertiesId :PlayApi, googleAdsLinksId :PlayApi, updateMask: String, name: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1beta/properties/${propertiesId}/googleAdsLinks/${googleAdsLinksId}").addQueryStringParameters("updateMask" -> updateMask.toString, "name" -> name.toString))
			}
			/** Deletes a GoogleAdsLink on a property */
			class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
				val scopes = Seq("""https://www.googleapis.com/auth/analytics.edit""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
			}
			object delete {
				def apply(propertiesId :PlayApi, googleAdsLinksId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1beta/properties/${propertiesId}/googleAdsLinks/${googleAdsLinksId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
			}
			/** Creates a GoogleAdsLink. */
			class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/analytics.edit""")
				/** Perform the request */
				def withGoogleAnalyticsAdminV1betaGoogleAdsLink(body: Schema.GoogleAnalyticsAdminV1betaGoogleAdsLink) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleAnalyticsAdminV1betaGoogleAdsLink])
			}
			object create {
				def apply(propertiesId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1beta/properties/${propertiesId}/googleAdsLinks").addQueryStringParameters("parent" -> parent.toString))
			}
		}
		object firebaseLinks {
			/** Creates a FirebaseLink. Properties can have at most one FirebaseLink. */
			class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/analytics.edit""")
				/** Perform the request */
				def withGoogleAnalyticsAdminV1betaFirebaseLink(body: Schema.GoogleAnalyticsAdminV1betaFirebaseLink) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleAnalyticsAdminV1betaFirebaseLink])
			}
			object create {
				def apply(propertiesId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1beta/properties/${propertiesId}/firebaseLinks").addQueryStringParameters("parent" -> parent.toString))
			}
			/** Lists FirebaseLinks on a property. Properties can have at most one FirebaseLink. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleAnalyticsAdminV1betaListFirebaseLinksResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/analytics.edit""", """https://www.googleapis.com/auth/analytics.readonly""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleAnalyticsAdminV1betaListFirebaseLinksResponse])
			}
			object list {
				def apply(propertiesId :PlayApi, parent: String, pageToken: String, pageSize: Int)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1beta/properties/${propertiesId}/firebaseLinks").addQueryStringParameters("parent" -> parent.toString, "pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString))
				given Conversion[list, Future[Schema.GoogleAnalyticsAdminV1betaListFirebaseLinksResponse]] = (fun: list) => fun.apply()
			}
			/** Deletes a FirebaseLink on a property */
			class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
				val scopes = Seq("""https://www.googleapis.com/auth/analytics.edit""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
			}
			object delete {
				def apply(propertiesId :PlayApi, firebaseLinksId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1beta/properties/${propertiesId}/firebaseLinks/${firebaseLinksId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
			}
		}
		object customDimensions {
			/** Creates a CustomDimension. */
			class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/analytics.edit""")
				/** Perform the request */
				def withGoogleAnalyticsAdminV1betaCustomDimension(body: Schema.GoogleAnalyticsAdminV1betaCustomDimension) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleAnalyticsAdminV1betaCustomDimension])
			}
			object create {
				def apply(propertiesId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1beta/properties/${propertiesId}/customDimensions").addQueryStringParameters("parent" -> parent.toString))
			}
			/** Lookup for a single CustomDimension. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleAnalyticsAdminV1betaCustomDimension]) {
				val scopes = Seq("""https://www.googleapis.com/auth/analytics.edit""", """https://www.googleapis.com/auth/analytics.readonly""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleAnalyticsAdminV1betaCustomDimension])
			}
			object get {
				def apply(propertiesId :PlayApi, customDimensionsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1beta/properties/${propertiesId}/customDimensions/${customDimensionsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.GoogleAnalyticsAdminV1betaCustomDimension]] = (fun: get) => fun.apply()
			}
			/** Updates a CustomDimension on a property. */
			class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/analytics.edit""")
				/** Perform the request */
				def withGoogleAnalyticsAdminV1betaCustomDimension(body: Schema.GoogleAnalyticsAdminV1betaCustomDimension) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleAnalyticsAdminV1betaCustomDimension])
			}
			object patch {
				def apply(propertiesId :PlayApi, customDimensionsId :PlayApi, updateMask: String, name: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1beta/properties/${propertiesId}/customDimensions/${customDimensionsId}").addQueryStringParameters("updateMask" -> updateMask.toString, "name" -> name.toString))
			}
			/** Archives a CustomDimension on a property. */
			class archive(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/analytics.edit""")
				/** Perform the request */
				def withGoogleAnalyticsAdminV1betaArchiveCustomDimensionRequest(body: Schema.GoogleAnalyticsAdminV1betaArchiveCustomDimensionRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleProtobufEmpty])
			}
			object archive {
				def apply(propertiesId :PlayApi, customDimensionsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): archive = new archive(ws.url(BASE_URL + s"v1beta/properties/${propertiesId}/customDimensions/${customDimensionsId}:archive").addQueryStringParameters("name" -> name.toString))
			}
			/** Lists CustomDimensions on a property. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleAnalyticsAdminV1betaListCustomDimensionsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/analytics.edit""", """https://www.googleapis.com/auth/analytics.readonly""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleAnalyticsAdminV1betaListCustomDimensionsResponse])
			}
			object list {
				def apply(propertiesId :PlayApi, pageToken: String, pageSize: Int, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1beta/properties/${propertiesId}/customDimensions").addQueryStringParameters("pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString, "parent" -> parent.toString))
				given Conversion[list, Future[Schema.GoogleAnalyticsAdminV1betaListCustomDimensionsResponse]] = (fun: list) => fun.apply()
			}
		}
		object customMetrics {
			/** Creates a CustomMetric. */
			class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/analytics.edit""")
				/** Perform the request */
				def withGoogleAnalyticsAdminV1betaCustomMetric(body: Schema.GoogleAnalyticsAdminV1betaCustomMetric) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleAnalyticsAdminV1betaCustomMetric])
			}
			object create {
				def apply(propertiesId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1beta/properties/${propertiesId}/customMetrics").addQueryStringParameters("parent" -> parent.toString))
			}
			/** Lookup for a single CustomMetric. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleAnalyticsAdminV1betaCustomMetric]) {
				val scopes = Seq("""https://www.googleapis.com/auth/analytics.edit""", """https://www.googleapis.com/auth/analytics.readonly""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleAnalyticsAdminV1betaCustomMetric])
			}
			object get {
				def apply(propertiesId :PlayApi, customMetricsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1beta/properties/${propertiesId}/customMetrics/${customMetricsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.GoogleAnalyticsAdminV1betaCustomMetric]] = (fun: get) => fun.apply()
			}
			/** Updates a CustomMetric on a property. */
			class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/analytics.edit""")
				/** Perform the request */
				def withGoogleAnalyticsAdminV1betaCustomMetric(body: Schema.GoogleAnalyticsAdminV1betaCustomMetric) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleAnalyticsAdminV1betaCustomMetric])
			}
			object patch {
				def apply(propertiesId :PlayApi, customMetricsId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1beta/properties/${propertiesId}/customMetrics/${customMetricsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
			}
			/** Archives a CustomMetric on a property. */
			class archive(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/analytics.edit""")
				/** Perform the request */
				def withGoogleAnalyticsAdminV1betaArchiveCustomMetricRequest(body: Schema.GoogleAnalyticsAdminV1betaArchiveCustomMetricRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleProtobufEmpty])
			}
			object archive {
				def apply(propertiesId :PlayApi, customMetricsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): archive = new archive(ws.url(BASE_URL + s"v1beta/properties/${propertiesId}/customMetrics/${customMetricsId}:archive").addQueryStringParameters("name" -> name.toString))
			}
			/** Lists CustomMetrics on a property. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleAnalyticsAdminV1betaListCustomMetricsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/analytics.edit""", """https://www.googleapis.com/auth/analytics.readonly""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleAnalyticsAdminV1betaListCustomMetricsResponse])
			}
			object list {
				def apply(propertiesId :PlayApi, pageToken: String, pageSize: Int, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1beta/properties/${propertiesId}/customMetrics").addQueryStringParameters("pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString, "parent" -> parent.toString))
				given Conversion[list, Future[Schema.GoogleAnalyticsAdminV1betaListCustomMetricsResponse]] = (fun: list) => fun.apply()
			}
		}
		object dataStreams {
			/** Creates a DataStream. */
			class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/analytics.edit""")
				/** Perform the request */
				def withGoogleAnalyticsAdminV1betaDataStream(body: Schema.GoogleAnalyticsAdminV1betaDataStream) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleAnalyticsAdminV1betaDataStream])
			}
			object create {
				def apply(propertiesId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1beta/properties/${propertiesId}/dataStreams").addQueryStringParameters("parent" -> parent.toString))
			}
			/** Deletes a DataStream on a property. */
			class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
				val scopes = Seq("""https://www.googleapis.com/auth/analytics.edit""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
			}
			object delete {
				def apply(propertiesId :PlayApi, dataStreamsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1beta/properties/${propertiesId}/dataStreams/${dataStreamsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
			}
			/** Lookup for a single DataStream. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleAnalyticsAdminV1betaDataStream]) {
				val scopes = Seq("""https://www.googleapis.com/auth/analytics.edit""", """https://www.googleapis.com/auth/analytics.readonly""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleAnalyticsAdminV1betaDataStream])
			}
			object get {
				def apply(propertiesId :PlayApi, dataStreamsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1beta/properties/${propertiesId}/dataStreams/${dataStreamsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.GoogleAnalyticsAdminV1betaDataStream]] = (fun: get) => fun.apply()
			}
			/** Updates a DataStream on a property. */
			class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/analytics.edit""")
				/** Perform the request */
				def withGoogleAnalyticsAdminV1betaDataStream(body: Schema.GoogleAnalyticsAdminV1betaDataStream) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleAnalyticsAdminV1betaDataStream])
			}
			object patch {
				def apply(propertiesId :PlayApi, dataStreamsId :PlayApi, updateMask: String, name: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1beta/properties/${propertiesId}/dataStreams/${dataStreamsId}").addQueryStringParameters("updateMask" -> updateMask.toString, "name" -> name.toString))
			}
			/** Lists DataStreams on a property. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleAnalyticsAdminV1betaListDataStreamsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/analytics.edit""", """https://www.googleapis.com/auth/analytics.readonly""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleAnalyticsAdminV1betaListDataStreamsResponse])
			}
			object list {
				def apply(propertiesId :PlayApi, pageSize: Int, pageToken: String, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1beta/properties/${propertiesId}/dataStreams").addQueryStringParameters("pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "parent" -> parent.toString))
				given Conversion[list, Future[Schema.GoogleAnalyticsAdminV1betaListDataStreamsResponse]] = (fun: list) => fun.apply()
			}
			object measurementProtocolSecrets {
				/** Creates a measurement protocol secret. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/analytics.edit""")
					/** Perform the request */
					def withGoogleAnalyticsAdminV1betaMeasurementProtocolSecret(body: Schema.GoogleAnalyticsAdminV1betaMeasurementProtocolSecret) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleAnalyticsAdminV1betaMeasurementProtocolSecret])
				}
				object create {
					def apply(propertiesId :PlayApi, dataStreamsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1beta/properties/${propertiesId}/dataStreams/${dataStreamsId}/measurementProtocolSecrets").addQueryStringParameters("parent" -> parent.toString))
				}
				/** Deletes target MeasurementProtocolSecret. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
					val scopes = Seq("""https://www.googleapis.com/auth/analytics.edit""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
				}
				object delete {
					def apply(propertiesId :PlayApi, dataStreamsId :PlayApi, measurementProtocolSecretsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1beta/properties/${propertiesId}/dataStreams/${dataStreamsId}/measurementProtocolSecrets/${measurementProtocolSecretsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
				}
				/** Lookup for a single MeasurementProtocolSecret. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleAnalyticsAdminV1betaMeasurementProtocolSecret]) {
					val scopes = Seq("""https://www.googleapis.com/auth/analytics.edit""", """https://www.googleapis.com/auth/analytics.readonly""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleAnalyticsAdminV1betaMeasurementProtocolSecret])
				}
				object get {
					def apply(propertiesId :PlayApi, dataStreamsId :PlayApi, measurementProtocolSecretsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1beta/properties/${propertiesId}/dataStreams/${dataStreamsId}/measurementProtocolSecrets/${measurementProtocolSecretsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleAnalyticsAdminV1betaMeasurementProtocolSecret]] = (fun: get) => fun.apply()
				}
				/** Updates a measurement protocol secret. */
				class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/analytics.edit""")
					/** Perform the request */
					def withGoogleAnalyticsAdminV1betaMeasurementProtocolSecret(body: Schema.GoogleAnalyticsAdminV1betaMeasurementProtocolSecret) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleAnalyticsAdminV1betaMeasurementProtocolSecret])
				}
				object patch {
					def apply(propertiesId :PlayApi, dataStreamsId :PlayApi, measurementProtocolSecretsId :PlayApi, updateMask: String, name: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1beta/properties/${propertiesId}/dataStreams/${dataStreamsId}/measurementProtocolSecrets/${measurementProtocolSecretsId}").addQueryStringParameters("updateMask" -> updateMask.toString, "name" -> name.toString))
				}
				/** Returns child MeasurementProtocolSecrets under the specified parent Property. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleAnalyticsAdminV1betaListMeasurementProtocolSecretsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/analytics.edit""", """https://www.googleapis.com/auth/analytics.readonly""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleAnalyticsAdminV1betaListMeasurementProtocolSecretsResponse])
				}
				object list {
					def apply(propertiesId :PlayApi, dataStreamsId :PlayApi, pageToken: String, parent: String, pageSize: Int)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1beta/properties/${propertiesId}/dataStreams/${dataStreamsId}/measurementProtocolSecrets").addQueryStringParameters("pageToken" -> pageToken.toString, "parent" -> parent.toString, "pageSize" -> pageSize.toString))
					given Conversion[list, Future[Schema.GoogleAnalyticsAdminV1betaListMeasurementProtocolSecretsResponse]] = (fun: list) => fun.apply()
				}
			}
		}
	}
}
