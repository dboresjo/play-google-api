package com.boresjo.play.api.google.analyticsadmin

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://analyticsadmin.googleapis.com/"

	object accountSummaries {
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleAnalyticsAdminV1betaListAccountSummariesResponse]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.GoogleAnalyticsAdminV1betaListAccountSummariesResponse])
		}
		object list {
			def apply(pageToken: String, pageSize: Int)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1beta/accountSummaries")).addQueryStringParameters("pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString))
			given Conversion[list, Future[Schema.GoogleAnalyticsAdminV1betaListAccountSummariesResponse]] = (fun: list) => fun.apply()
		}
	}
	object accounts {
		class getDataSharingSettings(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleAnalyticsAdminV1betaDataSharingSettings]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.GoogleAnalyticsAdminV1betaDataSharingSettings])
		}
		object getDataSharingSettings {
			def apply(accountsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): getDataSharingSettings = new getDataSharingSettings(auth(ws.url(BASE_URL + s"v1beta/accounts/${accountsId}/dataSharingSettings")).addQueryStringParameters("name" -> name.toString))
			given Conversion[getDataSharingSettings, Future[Schema.GoogleAnalyticsAdminV1betaDataSharingSettings]] = (fun: getDataSharingSettings) => fun.apply()
		}
		class provisionAccountTicket(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withGoogleAnalyticsAdminV1betaProvisionAccountTicketRequest(body: Schema.GoogleAnalyticsAdminV1betaProvisionAccountTicketRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleAnalyticsAdminV1betaProvisionAccountTicketResponse])
		}
		object provisionAccountTicket {
			def apply()(using auth: AuthToken, ec: ExecutionContext): provisionAccountTicket = new provisionAccountTicket(auth(ws.url(BASE_URL + s"v1beta/accounts:provisionAccountTicket")).addQueryStringParameters())
		}
		class searchChangeHistoryEvents(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withGoogleAnalyticsAdminV1betaSearchChangeHistoryEventsRequest(body: Schema.GoogleAnalyticsAdminV1betaSearchChangeHistoryEventsRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleAnalyticsAdminV1betaSearchChangeHistoryEventsResponse])
		}
		object searchChangeHistoryEvents {
			def apply(accountsId :PlayApi, account: String)(using auth: AuthToken, ec: ExecutionContext): searchChangeHistoryEvents = new searchChangeHistoryEvents(auth(ws.url(BASE_URL + s"v1beta/accounts/${accountsId}:searchChangeHistoryEvents")).addQueryStringParameters("account" -> account.toString))
		}
		class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
			def apply() = req.execute("DELETE").map(_.json.as[Schema.GoogleProtobufEmpty])
		}
		object delete {
			def apply(accountsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1beta/accounts/${accountsId}")).addQueryStringParameters("name" -> name.toString))
			given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
		}
		class runAccessReport(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withGoogleAnalyticsAdminV1betaRunAccessReportRequest(body: Schema.GoogleAnalyticsAdminV1betaRunAccessReportRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleAnalyticsAdminV1betaRunAccessReportResponse])
		}
		object runAccessReport {
			def apply(accountsId :PlayApi, entity: String)(using auth: AuthToken, ec: ExecutionContext): runAccessReport = new runAccessReport(auth(ws.url(BASE_URL + s"v1beta/accounts/${accountsId}:runAccessReport")).addQueryStringParameters("entity" -> entity.toString))
		}
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleAnalyticsAdminV1betaAccount]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.GoogleAnalyticsAdminV1betaAccount])
		}
		object get {
			def apply(accountsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1beta/accounts/${accountsId}")).addQueryStringParameters("name" -> name.toString))
			given Conversion[get, Future[Schema.GoogleAnalyticsAdminV1betaAccount]] = (fun: get) => fun.apply()
		}
		class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withGoogleAnalyticsAdminV1betaAccount(body: Schema.GoogleAnalyticsAdminV1betaAccount) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.GoogleAnalyticsAdminV1betaAccount])
		}
		object patch {
			def apply(accountsId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"v1beta/accounts/${accountsId}")).addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
		}
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleAnalyticsAdminV1betaListAccountsResponse]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.GoogleAnalyticsAdminV1betaListAccountsResponse])
		}
		object list {
			def apply(showDeleted: Boolean, pageToken: String, pageSize: Int)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1beta/accounts")).addQueryStringParameters("showDeleted" -> showDeleted.toString, "pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString))
			given Conversion[list, Future[Schema.GoogleAnalyticsAdminV1betaListAccountsResponse]] = (fun: list) => fun.apply()
		}
	}
	object properties {
		class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleAnalyticsAdminV1betaProperty]) {
			def apply() = req.execute("DELETE").map(_.json.as[Schema.GoogleAnalyticsAdminV1betaProperty])
		}
		object delete {
			def apply(propertiesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1beta/properties/${propertiesId}")).addQueryStringParameters("name" -> name.toString))
			given Conversion[delete, Future[Schema.GoogleAnalyticsAdminV1betaProperty]] = (fun: delete) => fun.apply()
		}
		class runAccessReport(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withGoogleAnalyticsAdminV1betaRunAccessReportRequest(body: Schema.GoogleAnalyticsAdminV1betaRunAccessReportRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleAnalyticsAdminV1betaRunAccessReportResponse])
		}
		object runAccessReport {
			def apply(propertiesId :PlayApi, entity: String)(using auth: AuthToken, ec: ExecutionContext): runAccessReport = new runAccessReport(auth(ws.url(BASE_URL + s"v1beta/properties/${propertiesId}:runAccessReport")).addQueryStringParameters("entity" -> entity.toString))
		}
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleAnalyticsAdminV1betaProperty]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.GoogleAnalyticsAdminV1betaProperty])
		}
		object get {
			def apply(propertiesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1beta/properties/${propertiesId}")).addQueryStringParameters("name" -> name.toString))
			given Conversion[get, Future[Schema.GoogleAnalyticsAdminV1betaProperty]] = (fun: get) => fun.apply()
		}
		class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withGoogleAnalyticsAdminV1betaProperty(body: Schema.GoogleAnalyticsAdminV1betaProperty) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.GoogleAnalyticsAdminV1betaProperty])
		}
		object patch {
			def apply(propertiesId :PlayApi, updateMask: String, name: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"v1beta/properties/${propertiesId}")).addQueryStringParameters("updateMask" -> updateMask.toString, "name" -> name.toString))
		}
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleAnalyticsAdminV1betaListPropertiesResponse]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.GoogleAnalyticsAdminV1betaListPropertiesResponse])
		}
		object list {
			def apply(filter: String, pageToken: String, showDeleted: Boolean, pageSize: Int)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1beta/properties")).addQueryStringParameters("filter" -> filter.toString, "pageToken" -> pageToken.toString, "showDeleted" -> showDeleted.toString, "pageSize" -> pageSize.toString))
			given Conversion[list, Future[Schema.GoogleAnalyticsAdminV1betaListPropertiesResponse]] = (fun: list) => fun.apply()
		}
		class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withGoogleAnalyticsAdminV1betaProperty(body: Schema.GoogleAnalyticsAdminV1betaProperty) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleAnalyticsAdminV1betaProperty])
		}
		object create {
			def apply()(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1beta/properties")).addQueryStringParameters())
		}
		class getDataRetentionSettings(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleAnalyticsAdminV1betaDataRetentionSettings]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.GoogleAnalyticsAdminV1betaDataRetentionSettings])
		}
		object getDataRetentionSettings {
			def apply(propertiesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): getDataRetentionSettings = new getDataRetentionSettings(auth(ws.url(BASE_URL + s"v1beta/properties/${propertiesId}/dataRetentionSettings")).addQueryStringParameters("name" -> name.toString))
			given Conversion[getDataRetentionSettings, Future[Schema.GoogleAnalyticsAdminV1betaDataRetentionSettings]] = (fun: getDataRetentionSettings) => fun.apply()
		}
		class updateDataRetentionSettings(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withGoogleAnalyticsAdminV1betaDataRetentionSettings(body: Schema.GoogleAnalyticsAdminV1betaDataRetentionSettings) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.GoogleAnalyticsAdminV1betaDataRetentionSettings])
		}
		object updateDataRetentionSettings {
			def apply(propertiesId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): updateDataRetentionSettings = new updateDataRetentionSettings(auth(ws.url(BASE_URL + s"v1beta/properties/${propertiesId}/dataRetentionSettings")).addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
		}
		class acknowledgeUserDataCollection(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withGoogleAnalyticsAdminV1betaAcknowledgeUserDataCollectionRequest(body: Schema.GoogleAnalyticsAdminV1betaAcknowledgeUserDataCollectionRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleAnalyticsAdminV1betaAcknowledgeUserDataCollectionResponse])
		}
		object acknowledgeUserDataCollection {
			def apply(propertiesId :PlayApi, property: String)(using auth: AuthToken, ec: ExecutionContext): acknowledgeUserDataCollection = new acknowledgeUserDataCollection(auth(ws.url(BASE_URL + s"v1beta/properties/${propertiesId}:acknowledgeUserDataCollection")).addQueryStringParameters("property" -> property.toString))
		}
		object conversionEvents {
			class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGoogleAnalyticsAdminV1betaConversionEvent(body: Schema.GoogleAnalyticsAdminV1betaConversionEvent) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleAnalyticsAdminV1betaConversionEvent])
			}
			object create {
				def apply(propertiesId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1beta/properties/${propertiesId}/conversionEvents")).addQueryStringParameters("parent" -> parent.toString))
			}
			class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
				def apply() = req.execute("DELETE").map(_.json.as[Schema.GoogleProtobufEmpty])
			}
			object delete {
				def apply(propertiesId :PlayApi, conversionEventsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1beta/properties/${propertiesId}/conversionEvents/${conversionEventsId}")).addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleAnalyticsAdminV1betaConversionEvent]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.GoogleAnalyticsAdminV1betaConversionEvent])
			}
			object get {
				def apply(propertiesId :PlayApi, conversionEventsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1beta/properties/${propertiesId}/conversionEvents/${conversionEventsId}")).addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.GoogleAnalyticsAdminV1betaConversionEvent]] = (fun: get) => fun.apply()
			}
			class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGoogleAnalyticsAdminV1betaConversionEvent(body: Schema.GoogleAnalyticsAdminV1betaConversionEvent) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.GoogleAnalyticsAdminV1betaConversionEvent])
			}
			object patch {
				def apply(propertiesId :PlayApi, conversionEventsId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"v1beta/properties/${propertiesId}/conversionEvents/${conversionEventsId}")).addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleAnalyticsAdminV1betaListConversionEventsResponse]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.GoogleAnalyticsAdminV1betaListConversionEventsResponse])
			}
			object list {
				def apply(propertiesId :PlayApi, parent: String, pageToken: String, pageSize: Int)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1beta/properties/${propertiesId}/conversionEvents")).addQueryStringParameters("parent" -> parent.toString, "pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString))
				given Conversion[list, Future[Schema.GoogleAnalyticsAdminV1betaListConversionEventsResponse]] = (fun: list) => fun.apply()
			}
		}
		object keyEvents {
			class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGoogleAnalyticsAdminV1betaKeyEvent(body: Schema.GoogleAnalyticsAdminV1betaKeyEvent) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleAnalyticsAdminV1betaKeyEvent])
			}
			object create {
				def apply(propertiesId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1beta/properties/${propertiesId}/keyEvents")).addQueryStringParameters("parent" -> parent.toString))
			}
			class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
				def apply() = req.execute("DELETE").map(_.json.as[Schema.GoogleProtobufEmpty])
			}
			object delete {
				def apply(propertiesId :PlayApi, keyEventsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1beta/properties/${propertiesId}/keyEvents/${keyEventsId}")).addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleAnalyticsAdminV1betaKeyEvent]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.GoogleAnalyticsAdminV1betaKeyEvent])
			}
			object get {
				def apply(propertiesId :PlayApi, keyEventsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1beta/properties/${propertiesId}/keyEvents/${keyEventsId}")).addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.GoogleAnalyticsAdminV1betaKeyEvent]] = (fun: get) => fun.apply()
			}
			class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGoogleAnalyticsAdminV1betaKeyEvent(body: Schema.GoogleAnalyticsAdminV1betaKeyEvent) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.GoogleAnalyticsAdminV1betaKeyEvent])
			}
			object patch {
				def apply(propertiesId :PlayApi, keyEventsId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"v1beta/properties/${propertiesId}/keyEvents/${keyEventsId}")).addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleAnalyticsAdminV1betaListKeyEventsResponse]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.GoogleAnalyticsAdminV1betaListKeyEventsResponse])
			}
			object list {
				def apply(propertiesId :PlayApi, pageToken: String, pageSize: Int, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1beta/properties/${propertiesId}/keyEvents")).addQueryStringParameters("pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString, "parent" -> parent.toString))
				given Conversion[list, Future[Schema.GoogleAnalyticsAdminV1betaListKeyEventsResponse]] = (fun: list) => fun.apply()
			}
		}
		object googleAdsLinks {
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleAnalyticsAdminV1betaListGoogleAdsLinksResponse]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.GoogleAnalyticsAdminV1betaListGoogleAdsLinksResponse])
			}
			object list {
				def apply(propertiesId :PlayApi, parent: String, pageToken: String, pageSize: Int)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1beta/properties/${propertiesId}/googleAdsLinks")).addQueryStringParameters("parent" -> parent.toString, "pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString))
				given Conversion[list, Future[Schema.GoogleAnalyticsAdminV1betaListGoogleAdsLinksResponse]] = (fun: list) => fun.apply()
			}
			class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGoogleAnalyticsAdminV1betaGoogleAdsLink(body: Schema.GoogleAnalyticsAdminV1betaGoogleAdsLink) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.GoogleAnalyticsAdminV1betaGoogleAdsLink])
			}
			object patch {
				def apply(propertiesId :PlayApi, googleAdsLinksId :PlayApi, updateMask: String, name: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"v1beta/properties/${propertiesId}/googleAdsLinks/${googleAdsLinksId}")).addQueryStringParameters("updateMask" -> updateMask.toString, "name" -> name.toString))
			}
			class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
				def apply() = req.execute("DELETE").map(_.json.as[Schema.GoogleProtobufEmpty])
			}
			object delete {
				def apply(propertiesId :PlayApi, googleAdsLinksId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1beta/properties/${propertiesId}/googleAdsLinks/${googleAdsLinksId}")).addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
			}
			class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGoogleAnalyticsAdminV1betaGoogleAdsLink(body: Schema.GoogleAnalyticsAdminV1betaGoogleAdsLink) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleAnalyticsAdminV1betaGoogleAdsLink])
			}
			object create {
				def apply(propertiesId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1beta/properties/${propertiesId}/googleAdsLinks")).addQueryStringParameters("parent" -> parent.toString))
			}
		}
		object firebaseLinks {
			class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGoogleAnalyticsAdminV1betaFirebaseLink(body: Schema.GoogleAnalyticsAdminV1betaFirebaseLink) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleAnalyticsAdminV1betaFirebaseLink])
			}
			object create {
				def apply(propertiesId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1beta/properties/${propertiesId}/firebaseLinks")).addQueryStringParameters("parent" -> parent.toString))
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleAnalyticsAdminV1betaListFirebaseLinksResponse]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.GoogleAnalyticsAdminV1betaListFirebaseLinksResponse])
			}
			object list {
				def apply(propertiesId :PlayApi, parent: String, pageToken: String, pageSize: Int)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1beta/properties/${propertiesId}/firebaseLinks")).addQueryStringParameters("parent" -> parent.toString, "pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString))
				given Conversion[list, Future[Schema.GoogleAnalyticsAdminV1betaListFirebaseLinksResponse]] = (fun: list) => fun.apply()
			}
			class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
				def apply() = req.execute("DELETE").map(_.json.as[Schema.GoogleProtobufEmpty])
			}
			object delete {
				def apply(propertiesId :PlayApi, firebaseLinksId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1beta/properties/${propertiesId}/firebaseLinks/${firebaseLinksId}")).addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
			}
		}
		object customDimensions {
			class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGoogleAnalyticsAdminV1betaCustomDimension(body: Schema.GoogleAnalyticsAdminV1betaCustomDimension) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleAnalyticsAdminV1betaCustomDimension])
			}
			object create {
				def apply(propertiesId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1beta/properties/${propertiesId}/customDimensions")).addQueryStringParameters("parent" -> parent.toString))
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleAnalyticsAdminV1betaCustomDimension]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.GoogleAnalyticsAdminV1betaCustomDimension])
			}
			object get {
				def apply(propertiesId :PlayApi, customDimensionsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1beta/properties/${propertiesId}/customDimensions/${customDimensionsId}")).addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.GoogleAnalyticsAdminV1betaCustomDimension]] = (fun: get) => fun.apply()
			}
			class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGoogleAnalyticsAdminV1betaCustomDimension(body: Schema.GoogleAnalyticsAdminV1betaCustomDimension) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.GoogleAnalyticsAdminV1betaCustomDimension])
			}
			object patch {
				def apply(propertiesId :PlayApi, customDimensionsId :PlayApi, updateMask: String, name: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"v1beta/properties/${propertiesId}/customDimensions/${customDimensionsId}")).addQueryStringParameters("updateMask" -> updateMask.toString, "name" -> name.toString))
			}
			class archive(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGoogleAnalyticsAdminV1betaArchiveCustomDimensionRequest(body: Schema.GoogleAnalyticsAdminV1betaArchiveCustomDimensionRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleProtobufEmpty])
			}
			object archive {
				def apply(propertiesId :PlayApi, customDimensionsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): archive = new archive(auth(ws.url(BASE_URL + s"v1beta/properties/${propertiesId}/customDimensions/${customDimensionsId}:archive")).addQueryStringParameters("name" -> name.toString))
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleAnalyticsAdminV1betaListCustomDimensionsResponse]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.GoogleAnalyticsAdminV1betaListCustomDimensionsResponse])
			}
			object list {
				def apply(propertiesId :PlayApi, pageToken: String, pageSize: Int, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1beta/properties/${propertiesId}/customDimensions")).addQueryStringParameters("pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString, "parent" -> parent.toString))
				given Conversion[list, Future[Schema.GoogleAnalyticsAdminV1betaListCustomDimensionsResponse]] = (fun: list) => fun.apply()
			}
		}
		object customMetrics {
			class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGoogleAnalyticsAdminV1betaCustomMetric(body: Schema.GoogleAnalyticsAdminV1betaCustomMetric) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleAnalyticsAdminV1betaCustomMetric])
			}
			object create {
				def apply(propertiesId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1beta/properties/${propertiesId}/customMetrics")).addQueryStringParameters("parent" -> parent.toString))
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleAnalyticsAdminV1betaCustomMetric]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.GoogleAnalyticsAdminV1betaCustomMetric])
			}
			object get {
				def apply(propertiesId :PlayApi, customMetricsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1beta/properties/${propertiesId}/customMetrics/${customMetricsId}")).addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.GoogleAnalyticsAdminV1betaCustomMetric]] = (fun: get) => fun.apply()
			}
			class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGoogleAnalyticsAdminV1betaCustomMetric(body: Schema.GoogleAnalyticsAdminV1betaCustomMetric) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.GoogleAnalyticsAdminV1betaCustomMetric])
			}
			object patch {
				def apply(propertiesId :PlayApi, customMetricsId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"v1beta/properties/${propertiesId}/customMetrics/${customMetricsId}")).addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
			}
			class archive(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGoogleAnalyticsAdminV1betaArchiveCustomMetricRequest(body: Schema.GoogleAnalyticsAdminV1betaArchiveCustomMetricRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleProtobufEmpty])
			}
			object archive {
				def apply(propertiesId :PlayApi, customMetricsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): archive = new archive(auth(ws.url(BASE_URL + s"v1beta/properties/${propertiesId}/customMetrics/${customMetricsId}:archive")).addQueryStringParameters("name" -> name.toString))
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleAnalyticsAdminV1betaListCustomMetricsResponse]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.GoogleAnalyticsAdminV1betaListCustomMetricsResponse])
			}
			object list {
				def apply(propertiesId :PlayApi, pageToken: String, pageSize: Int, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1beta/properties/${propertiesId}/customMetrics")).addQueryStringParameters("pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString, "parent" -> parent.toString))
				given Conversion[list, Future[Schema.GoogleAnalyticsAdminV1betaListCustomMetricsResponse]] = (fun: list) => fun.apply()
			}
		}
		object dataStreams {
			class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGoogleAnalyticsAdminV1betaDataStream(body: Schema.GoogleAnalyticsAdminV1betaDataStream) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleAnalyticsAdminV1betaDataStream])
			}
			object create {
				def apply(propertiesId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1beta/properties/${propertiesId}/dataStreams")).addQueryStringParameters("parent" -> parent.toString))
			}
			class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
				def apply() = req.execute("DELETE").map(_.json.as[Schema.GoogleProtobufEmpty])
			}
			object delete {
				def apply(propertiesId :PlayApi, dataStreamsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1beta/properties/${propertiesId}/dataStreams/${dataStreamsId}")).addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleAnalyticsAdminV1betaDataStream]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.GoogleAnalyticsAdminV1betaDataStream])
			}
			object get {
				def apply(propertiesId :PlayApi, dataStreamsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1beta/properties/${propertiesId}/dataStreams/${dataStreamsId}")).addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.GoogleAnalyticsAdminV1betaDataStream]] = (fun: get) => fun.apply()
			}
			class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGoogleAnalyticsAdminV1betaDataStream(body: Schema.GoogleAnalyticsAdminV1betaDataStream) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.GoogleAnalyticsAdminV1betaDataStream])
			}
			object patch {
				def apply(propertiesId :PlayApi, dataStreamsId :PlayApi, updateMask: String, name: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"v1beta/properties/${propertiesId}/dataStreams/${dataStreamsId}")).addQueryStringParameters("updateMask" -> updateMask.toString, "name" -> name.toString))
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleAnalyticsAdminV1betaListDataStreamsResponse]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.GoogleAnalyticsAdminV1betaListDataStreamsResponse])
			}
			object list {
				def apply(propertiesId :PlayApi, pageSize: Int, pageToken: String, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1beta/properties/${propertiesId}/dataStreams")).addQueryStringParameters("pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "parent" -> parent.toString))
				given Conversion[list, Future[Schema.GoogleAnalyticsAdminV1betaListDataStreamsResponse]] = (fun: list) => fun.apply()
			}
			object measurementProtocolSecrets {
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleAnalyticsAdminV1betaMeasurementProtocolSecret(body: Schema.GoogleAnalyticsAdminV1betaMeasurementProtocolSecret) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleAnalyticsAdminV1betaMeasurementProtocolSecret])
				}
				object create {
					def apply(propertiesId :PlayApi, dataStreamsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1beta/properties/${propertiesId}/dataStreams/${dataStreamsId}/measurementProtocolSecrets")).addQueryStringParameters("parent" -> parent.toString))
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
					def apply() = req.execute("DELETE").map(_.json.as[Schema.GoogleProtobufEmpty])
				}
				object delete {
					def apply(propertiesId :PlayApi, dataStreamsId :PlayApi, measurementProtocolSecretsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1beta/properties/${propertiesId}/dataStreams/${dataStreamsId}/measurementProtocolSecrets/${measurementProtocolSecretsId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleAnalyticsAdminV1betaMeasurementProtocolSecret]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.GoogleAnalyticsAdminV1betaMeasurementProtocolSecret])
				}
				object get {
					def apply(propertiesId :PlayApi, dataStreamsId :PlayApi, measurementProtocolSecretsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1beta/properties/${propertiesId}/dataStreams/${dataStreamsId}/measurementProtocolSecrets/${measurementProtocolSecretsId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleAnalyticsAdminV1betaMeasurementProtocolSecret]] = (fun: get) => fun.apply()
				}
				class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleAnalyticsAdminV1betaMeasurementProtocolSecret(body: Schema.GoogleAnalyticsAdminV1betaMeasurementProtocolSecret) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.GoogleAnalyticsAdminV1betaMeasurementProtocolSecret])
				}
				object patch {
					def apply(propertiesId :PlayApi, dataStreamsId :PlayApi, measurementProtocolSecretsId :PlayApi, updateMask: String, name: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"v1beta/properties/${propertiesId}/dataStreams/${dataStreamsId}/measurementProtocolSecrets/${measurementProtocolSecretsId}")).addQueryStringParameters("updateMask" -> updateMask.toString, "name" -> name.toString))
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleAnalyticsAdminV1betaListMeasurementProtocolSecretsResponse]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.GoogleAnalyticsAdminV1betaListMeasurementProtocolSecretsResponse])
				}
				object list {
					def apply(propertiesId :PlayApi, dataStreamsId :PlayApi, pageToken: String, parent: String, pageSize: Int)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1beta/properties/${propertiesId}/dataStreams/${dataStreamsId}/measurementProtocolSecrets")).addQueryStringParameters("pageToken" -> pageToken.toString, "parent" -> parent.toString, "pageSize" -> pageSize.toString))
					given Conversion[list, Future[Schema.GoogleAnalyticsAdminV1betaListMeasurementProtocolSecretsResponse]] = (fun: list) => fun.apply()
				}
			}
		}
	}
}
