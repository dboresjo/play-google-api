package com.boresjo.play.api.google.mybusinessbusinessinformation

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	val scopes = Seq()

	private val BASE_URL = "https://mybusinessbusinessinformation.googleapis.com/"

	object attributes {
		/** Returns the list of attributes that would be available for a location with the given primary category and country. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListAttributeMetadataResponse]) {
			val scopes = Seq()
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListAttributeMetadataResponse])
		}
		object list {
			def apply(parent: String, categoryName: String, regionCode: String, languageCode: String, showAll: Boolean, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/attributes").addQueryStringParameters("parent" -> parent.toString, "categoryName" -> categoryName.toString, "regionCode" -> regionCode.toString, "languageCode" -> languageCode.toString, "showAll" -> showAll.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
			given Conversion[list, Future[Schema.ListAttributeMetadataResponse]] = (fun: list) => fun.apply()
		}
	}
	object locations {
		/** Update attributes for a given location. */
		class updateAttributes(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq()
			/** Perform the request */
			def withAttributes(body: Schema.Attributes) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Attributes])
		}
		object updateAttributes {
			def apply(locationsId :PlayApi, name: String, attributeMask: String)(using signer: RequestSigner, ec: ExecutionContext): updateAttributes = new updateAttributes(ws.url(BASE_URL + s"v1/locations/${locationsId}/attributes").addQueryStringParameters("name" -> name.toString, "attributeMask" -> attributeMask.toString))
		}
		/** Looks up all the attributes set for a given location. */
		class getAttributes(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Attributes]) {
			val scopes = Seq()
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Attributes])
		}
		object getAttributes {
			def apply(locationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): getAttributes = new getAttributes(ws.url(BASE_URL + s"v1/locations/${locationsId}/attributes").addQueryStringParameters("name" -> name.toString))
			given Conversion[getAttributes, Future[Schema.Attributes]] = (fun: getAttributes) => fun.apply()
		}
		/** Deletes a location. If this location cannot be deleted using the API and it is marked so in the `google.mybusiness.businessinformation.v1.LocationState`, use the [Google Business Profile](https://business.google.com/manage/) website. */
		class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
			val scopes = Seq()
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
		}
		object delete {
			def apply(locationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/locations/${locationsId}").addQueryStringParameters("name" -> name.toString))
			given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
		}
		/** Updates the specified location. */
		class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq()
			/** Optional. If true, the request is validated without actually updating the location. When this field is set, we will only return validation errors if there were any. The response will be empty if no errors were found. */
			def withValidateOnly(validateOnly: Boolean) = new patch(req.addQueryStringParameters("validateOnly" -> validateOnly.toString))
			/** Perform the request */
			def withLocation(body: Schema.Location) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Location])
		}
		object patch {
			def apply(locationsId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/locations/${locationsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
		}
		/** Returns the specified location. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Location]) {
			val scopes = Seq()
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Location])
		}
		object get {
			def apply(locationsId :PlayApi, name: String, readMask: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/locations/${locationsId}").addQueryStringParameters("name" -> name.toString, "readMask" -> readMask.toString))
			given Conversion[get, Future[Schema.Location]] = (fun: get) => fun.apply()
		}
		/** Gets the Google-updated version of the specified location. */
		class getGoogleUpdated(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleUpdatedLocation]) {
			val scopes = Seq()
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleUpdatedLocation])
		}
		object getGoogleUpdated {
			def apply(locationsId :PlayApi, name: String, readMask: String)(using signer: RequestSigner, ec: ExecutionContext): getGoogleUpdated = new getGoogleUpdated(ws.url(BASE_URL + s"v1/locations/${locationsId}:getGoogleUpdated").addQueryStringParameters("name" -> name.toString, "readMask" -> readMask.toString))
			given Conversion[getGoogleUpdated, Future[Schema.GoogleUpdatedLocation]] = (fun: getGoogleUpdated) => fun.apply()
		}
		object attributes {
			/** Gets the Google-updated version of the specified location. */
			class getGoogleUpdated(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Attributes]) {
				val scopes = Seq()
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Attributes])
			}
			object getGoogleUpdated {
				def apply(locationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): getGoogleUpdated = new getGoogleUpdated(ws.url(BASE_URL + s"v1/locations/${locationsId}/attributes:getGoogleUpdated").addQueryStringParameters("name" -> name.toString))
				given Conversion[getGoogleUpdated, Future[Schema.Attributes]] = (fun: getGoogleUpdated) => fun.apply()
			}
		}
	}
	object categories {
		/** Returns a list of business categories. Search will match the category name but not the category ID. Search only matches the front of a category name (that is, 'food' may return 'Food Court' but not 'Fast Food Restaurant'). */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListCategoriesResponse]) {
			val scopes = Seq()
			/** Optional. Filter string from user. The only field that supported is `displayName`. Eg: `filter=displayName=foo`. */
			def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
			/** Optional. How many categories to fetch per page. Default is 100, minimum is 1, and maximum page size is 100.<br>Format: int32 */
			def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
			/** Optional. If specified, the next page of categories will be fetched. */
			def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListCategoriesResponse])
		}
		object list {
			def apply(regionCode: String, languageCode: String, view: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/categories").addQueryStringParameters("regionCode" -> regionCode.toString, "languageCode" -> languageCode.toString, "view" -> view.toString))
			given Conversion[list, Future[Schema.ListCategoriesResponse]] = (fun: list) => fun.apply()
		}
		/** Returns a list of business categories for the provided language and GConcept ids. */
		class batchGet(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.BatchGetCategoriesResponse]) {
			val scopes = Seq()
			/** Optional. The ISO 3166-1 alpha-2 country code used to infer non-standard language. */
			def withRegionCode(regionCode: String) = new batchGet(req.addQueryStringParameters("regionCode" -> regionCode.toString))
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.BatchGetCategoriesResponse])
		}
		object batchGet {
			def apply(names: String, languageCode: String, view: String)(using signer: RequestSigner, ec: ExecutionContext): batchGet = new batchGet(ws.url(BASE_URL + s"v1/categories:batchGet").addQueryStringParameters("names" -> names.toString, "languageCode" -> languageCode.toString, "view" -> view.toString))
			given Conversion[batchGet, Future[Schema.BatchGetCategoriesResponse]] = (fun: batchGet) => fun.apply()
		}
	}
	object chains {
		/** Gets the specified chain. Returns `NOT_FOUND` if the chain does not exist. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Chain]) {
			val scopes = Seq()
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Chain])
		}
		object get {
			def apply(chainsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/chains/${chainsId}").addQueryStringParameters("name" -> name.toString))
			given Conversion[get, Future[Schema.Chain]] = (fun: get) => fun.apply()
		}
		/** Searches the chain based on chain name. */
		class search(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.SearchChainsResponse]) {
			val scopes = Seq()
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.SearchChainsResponse])
		}
		object search {
			def apply(chainName: String, pageSize: Int)(using signer: RequestSigner, ec: ExecutionContext): search = new search(ws.url(BASE_URL + s"v1/chains:search").addQueryStringParameters("chainName" -> chainName.toString, "pageSize" -> pageSize.toString))
			given Conversion[search, Future[Schema.SearchChainsResponse]] = (fun: search) => fun.apply()
		}
	}
	object googleLocations {
		/** Search all of the possible locations that are a match to the specified request. */
		class search(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq()
			/** Perform the request */
			def withSearchGoogleLocationsRequest(body: Schema.SearchGoogleLocationsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.SearchGoogleLocationsResponse])
		}
		object search {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): search = new search(ws.url(BASE_URL + s"v1/googleLocations:search").addQueryStringParameters())
		}
	}
	object accounts {
		object locations {
			/** Lists the locations for the specified account. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListLocationsResponse]) {
				val scopes = Seq()
				/** Optional. How many locations to fetch per page. Default value is 10 if not set. Minimum is 1, and maximum page size is 100.<br>Format: int32 */
				def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
				/** Optional. If specified, it fetches the next `page` of locations. The page token is returned by previous calls to `ListLocations` when there were more locations than could fit in the requested page size. */
				def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
				/** Optional. A filter constraining the locations to return. The response includes only entries that match the filter. If `filter` is empty, then constraints are applied and all locations (paginated) are retrieved for the requested account. For more information about valid fields and example usage, see [Work with Location Data Guide](https://developers.google.com/my-business/content/location-data#filter_results_when_you_list_locations). */
				def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
				/** Optional. Sorting order for the request. Multiple fields should be comma-separated, following SQL syntax. The default sorting order is ascending. To specify descending order, a suffix " desc" should be added. Valid fields to order_by are title and store_code. For example: "title, store_code desc" or "title" or "store_code desc" */
				def withOrderBy(orderBy: String) = new list(req.addQueryStringParameters("orderBy" -> orderBy.toString))
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListLocationsResponse])
			}
			object list {
				def apply(accountsId :PlayApi, parent: String, readMask: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/accounts/${accountsId}/locations").addQueryStringParameters("parent" -> parent.toString, "readMask" -> readMask.toString))
				given Conversion[list, Future[Schema.ListLocationsResponse]] = (fun: list) => fun.apply()
			}
			/** Creates a new Location that will be owned by the logged in user. */
			class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq()
				/** Optional. If true, the request is validated without actually creating the location. */
				def withValidateOnly(validateOnly: Boolean) = new create(req.addQueryStringParameters("validateOnly" -> validateOnly.toString))
				/** Optional. A unique request ID for the server to detect duplicated requests. We recommend using UUIDs. Max length is 50 characters. */
				def withRequestId(requestId: String) = new create(req.addQueryStringParameters("requestId" -> requestId.toString))
				/** Perform the request */
				def withLocation(body: Schema.Location) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Location])
			}
			object create {
				def apply(accountsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/accounts/${accountsId}/locations").addQueryStringParameters("parent" -> parent.toString))
			}
		}
	}
}
