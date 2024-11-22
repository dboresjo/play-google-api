package com.boresjo.play.api.google.searchads360

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://searchads360.googleapis.com/"

	object customers {
		class listAccessibleCustomers(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleAdsSearchads360V0Services__ListAccessibleCustomersResponse]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleAdsSearchads360V0Services__ListAccessibleCustomersResponse])
		}
		object listAccessibleCustomers {
			def apply()(using auth: AuthToken, ec: ExecutionContext): listAccessibleCustomers = new listAccessibleCustomers(ws.url(BASE_URL + s"v0/customers:listAccessibleCustomers").addQueryStringParameters())
			given Conversion[listAccessibleCustomers, Future[Schema.GoogleAdsSearchads360V0Services__ListAccessibleCustomersResponse]] = (fun: listAccessibleCustomers) => fun.apply()
		}
		object customColumns {
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleAdsSearchads360V0Resources__CustomColumn]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleAdsSearchads360V0Resources__CustomColumn])
			}
			object get {
				def apply(customersId :PlayApi, customColumnsId :PlayApi, resourceName: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v0/customers/${customersId}/customColumns/${customColumnsId}").addQueryStringParameters("resourceName" -> resourceName.toString))
				given Conversion[get, Future[Schema.GoogleAdsSearchads360V0Resources__CustomColumn]] = (fun: get) => fun.apply()
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleAdsSearchads360V0Services__ListCustomColumnsResponse]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleAdsSearchads360V0Services__ListCustomColumnsResponse])
			}
			object list {
				def apply(customersId :PlayApi, customerId: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v0/customers/${customersId}/customColumns").addQueryStringParameters("customerId" -> customerId.toString))
				given Conversion[list, Future[Schema.GoogleAdsSearchads360V0Services__ListCustomColumnsResponse]] = (fun: list) => fun.apply()
			}
		}
		object searchAds360 {
			class search(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGoogleAdsSearchads360V0Services__SearchSearchAds360Request(body: Schema.GoogleAdsSearchads360V0Services__SearchSearchAds360Request) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleAdsSearchads360V0Services__SearchSearchAds360Response])
			}
			object search {
				def apply(customersId :PlayApi, customerId: String)(using auth: AuthToken, ec: ExecutionContext): search = new search(ws.url(BASE_URL + s"v0/customers/${customersId}/searchAds360:search").addQueryStringParameters("customerId" -> customerId.toString))
			}
		}
	}
	object searchAds360Fields {
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleAdsSearchads360V0Resources__SearchAds360Field]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleAdsSearchads360V0Resources__SearchAds360Field])
		}
		object get {
			def apply(searchAds360FieldsId :PlayApi, resourceName: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v0/searchAds360Fields/${searchAds360FieldsId}").addQueryStringParameters("resourceName" -> resourceName.toString))
			given Conversion[get, Future[Schema.GoogleAdsSearchads360V0Resources__SearchAds360Field]] = (fun: get) => fun.apply()
		}
		class search(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withGoogleAdsSearchads360V0Services__SearchSearchAds360FieldsRequest(body: Schema.GoogleAdsSearchads360V0Services__SearchSearchAds360FieldsRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleAdsSearchads360V0Services__SearchSearchAds360FieldsResponse])
		}
		object search {
			def apply()(using auth: AuthToken, ec: ExecutionContext): search = new search(ws.url(BASE_URL + s"v0/searchAds360Fields:search").addQueryStringParameters())
		}
	}
}
