package com.boresjo.play.api.google.searchads360

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
		"""https://www.googleapis.com/auth/doubleclicksearch""" /* View and manage your advertising data in DoubleClick Search */
	)

	private val BASE_URL = "https://searchads360.googleapis.com/"

	object customers {
		/** Returns resource names of customers directly accessible by the user authenticating the call. List of thrown errors: [AuthenticationError]() [AuthorizationError]() [HeaderError]() [InternalError]() [QuotaError]() [RequestError]() */
		class listAccessibleCustomers(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleAdsSearchads360V0Services__ListAccessibleCustomersResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/doubleclicksearch""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleAdsSearchads360V0Services__ListAccessibleCustomersResponse])
		}
		object listAccessibleCustomers {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): listAccessibleCustomers = new listAccessibleCustomers(ws.url(BASE_URL + s"v0/customers:listAccessibleCustomers").addQueryStringParameters())
			given Conversion[listAccessibleCustomers, Future[Schema.GoogleAdsSearchads360V0Services__ListAccessibleCustomersResponse]] = (fun: listAccessibleCustomers) => fun.apply()
		}
		object customColumns {
			/** Returns the requested custom column in full detail. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleAdsSearchads360V0Resources__CustomColumn]) {
				val scopes = Seq("""https://www.googleapis.com/auth/doubleclicksearch""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleAdsSearchads360V0Resources__CustomColumn])
			}
			object get {
				def apply(customersId :PlayApi, customColumnsId :PlayApi, resourceName: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v0/customers/${customersId}/customColumns/${customColumnsId}").addQueryStringParameters("resourceName" -> resourceName.toString))
				given Conversion[get, Future[Schema.GoogleAdsSearchads360V0Resources__CustomColumn]] = (fun: get) => fun.apply()
			}
			/** Returns all the custom columns associated with the customer in full detail. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleAdsSearchads360V0Services__ListCustomColumnsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/doubleclicksearch""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleAdsSearchads360V0Services__ListCustomColumnsResponse])
			}
			object list {
				def apply(customersId :PlayApi, customerId: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v0/customers/${customersId}/customColumns").addQueryStringParameters("customerId" -> customerId.toString))
				given Conversion[list, Future[Schema.GoogleAdsSearchads360V0Services__ListCustomColumnsResponse]] = (fun: list) => fun.apply()
			}
		}
		object searchAds360 {
			/** Returns all rows that match the search query. List of thrown errors: [AuthenticationError]() [AuthorizationError]() [HeaderError]() [InternalError]() [QueryError]() [QuotaError]() [RequestError]() */
			class search(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/doubleclicksearch""")
				/** Perform the request */
				def withGoogleAdsSearchads360V0Services__SearchSearchAds360Request(body: Schema.GoogleAdsSearchads360V0Services__SearchSearchAds360Request) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleAdsSearchads360V0Services__SearchSearchAds360Response])
			}
			object search {
				def apply(customersId :PlayApi, customerId: String)(using signer: RequestSigner, ec: ExecutionContext): search = new search(ws.url(BASE_URL + s"v0/customers/${customersId}/searchAds360:search").addQueryStringParameters("customerId" -> customerId.toString))
			}
		}
	}
	object searchAds360Fields {
		/** Returns just the requested field. List of thrown errors: [AuthenticationError]() [AuthorizationError]() [HeaderError]() [InternalError]() [QuotaError]() [RequestError]() */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleAdsSearchads360V0Resources__SearchAds360Field]) {
			val scopes = Seq("""https://www.googleapis.com/auth/doubleclicksearch""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleAdsSearchads360V0Resources__SearchAds360Field])
		}
		object get {
			def apply(searchAds360FieldsId :PlayApi, resourceName: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v0/searchAds360Fields/${searchAds360FieldsId}").addQueryStringParameters("resourceName" -> resourceName.toString))
			given Conversion[get, Future[Schema.GoogleAdsSearchads360V0Resources__SearchAds360Field]] = (fun: get) => fun.apply()
		}
		/** Returns all fields that match the search [query](/search-ads/reporting/concepts/field-service#use_a_query_to_get_field_details). List of thrown errors: [AuthenticationError]() [AuthorizationError]() [HeaderError]() [InternalError]() [QueryError]() [QuotaError]() [RequestError]() */
		class search(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/doubleclicksearch""")
			/** Perform the request */
			def withGoogleAdsSearchads360V0Services__SearchSearchAds360FieldsRequest(body: Schema.GoogleAdsSearchads360V0Services__SearchSearchAds360FieldsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleAdsSearchads360V0Services__SearchSearchAds360FieldsResponse])
		}
		object search {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): search = new search(ws.url(BASE_URL + s"v0/searchAds360Fields:search").addQueryStringParameters())
		}
	}
}
