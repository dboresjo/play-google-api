package com.boresjo.play.api.google.mybusinessbusinessinformation

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://mybusinessbusinessinformation.googleapis.com/"

	object attributes {
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListAttributeMetadataResponse]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.ListAttributeMetadataResponse])
		}
		object list {
			def apply(parent: String, categoryName: String, regionCode: String, languageCode: String, showAll: Boolean, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/attributes")).addQueryStringParameters("parent" -> parent.toString, "categoryName" -> categoryName.toString, "regionCode" -> regionCode.toString, "languageCode" -> languageCode.toString, "showAll" -> showAll.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
			given Conversion[list, Future[Schema.ListAttributeMetadataResponse]] = (fun: list) => fun.apply()
		}
	}
	object locations {
		class updateAttributes(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withAttributes(body: Schema.Attributes) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.Attributes])
		}
		object updateAttributes {
			def apply(locationsId :PlayApi, name: String, attributeMask: String)(using auth: AuthToken, ec: ExecutionContext): updateAttributes = new updateAttributes(auth(ws.url(BASE_URL + s"v1/locations/${locationsId}/attributes")).addQueryStringParameters("name" -> name.toString, "attributeMask" -> attributeMask.toString))
		}
		class getAttributes(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Attributes]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.Attributes])
		}
		object getAttributes {
			def apply(locationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): getAttributes = new getAttributes(auth(ws.url(BASE_URL + s"v1/locations/${locationsId}/attributes")).addQueryStringParameters("name" -> name.toString))
			given Conversion[getAttributes, Future[Schema.Attributes]] = (fun: getAttributes) => fun.apply()
		}
		class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
			def apply() = req.execute("DELETE").map(_.json.as[Schema.Empty])
		}
		object delete {
			def apply(locationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/locations/${locationsId}")).addQueryStringParameters("name" -> name.toString))
			given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
		}
		class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			/** Optional. If true, the request is validated without actually updating the location. When this field is set, we will only return validation errors if there were any. The response will be empty if no errors were found. */
			def withValidateOnly(validateOnly: Boolean) = new patch(req.addQueryStringParameters("validateOnly" -> validateOnly.toString))
			def withLocation(body: Schema.Location) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.Location])
		}
		object patch {
			def apply(locationsId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"v1/locations/${locationsId}")).addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
		}
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Location]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.Location])
		}
		object get {
			def apply(locationsId :PlayApi, name: String, readMask: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/locations/${locationsId}")).addQueryStringParameters("name" -> name.toString, "readMask" -> readMask.toString))
			given Conversion[get, Future[Schema.Location]] = (fun: get) => fun.apply()
		}
		class getGoogleUpdated(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleUpdatedLocation]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.GoogleUpdatedLocation])
		}
		object getGoogleUpdated {
			def apply(locationsId :PlayApi, name: String, readMask: String)(using auth: AuthToken, ec: ExecutionContext): getGoogleUpdated = new getGoogleUpdated(auth(ws.url(BASE_URL + s"v1/locations/${locationsId}:getGoogleUpdated")).addQueryStringParameters("name" -> name.toString, "readMask" -> readMask.toString))
			given Conversion[getGoogleUpdated, Future[Schema.GoogleUpdatedLocation]] = (fun: getGoogleUpdated) => fun.apply()
		}
		object attributes {
			class getGoogleUpdated(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Attributes]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.Attributes])
			}
			object getGoogleUpdated {
				def apply(locationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): getGoogleUpdated = new getGoogleUpdated(auth(ws.url(BASE_URL + s"v1/locations/${locationsId}/attributes:getGoogleUpdated")).addQueryStringParameters("name" -> name.toString))
				given Conversion[getGoogleUpdated, Future[Schema.Attributes]] = (fun: getGoogleUpdated) => fun.apply()
			}
		}
	}
	object categories {
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListCategoriesResponse]) {
			/** Optional. Filter string from user. The only field that supported is `displayName`. Eg: `filter=displayName=foo`. */
			def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
			/** Optional. How many categories to fetch per page. Default is 100, minimum is 1, and maximum page size is 100.<br>Format: int32 */
			def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
			/** Optional. If specified, the next page of categories will be fetched. */
			def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
			def apply() = req.execute("GET").map(_.json.as[Schema.ListCategoriesResponse])
		}
		object list {
			def apply(regionCode: String, languageCode: String, view: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/categories")).addQueryStringParameters("regionCode" -> regionCode.toString, "languageCode" -> languageCode.toString, "view" -> view.toString))
			given Conversion[list, Future[Schema.ListCategoriesResponse]] = (fun: list) => fun.apply()
		}
		class batchGet(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.BatchGetCategoriesResponse]) {
			/** Optional. The ISO 3166-1 alpha-2 country code used to infer non-standard language. */
			def withRegionCode(regionCode: String) = new batchGet(req.addQueryStringParameters("regionCode" -> regionCode.toString))
			def apply() = req.execute("GET").map(_.json.as[Schema.BatchGetCategoriesResponse])
		}
		object batchGet {
			def apply(names: String, languageCode: String, view: String)(using auth: AuthToken, ec: ExecutionContext): batchGet = new batchGet(auth(ws.url(BASE_URL + s"v1/categories:batchGet")).addQueryStringParameters("names" -> names.toString, "languageCode" -> languageCode.toString, "view" -> view.toString))
			given Conversion[batchGet, Future[Schema.BatchGetCategoriesResponse]] = (fun: batchGet) => fun.apply()
		}
	}
	object chains {
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Chain]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.Chain])
		}
		object get {
			def apply(chainsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/chains/${chainsId}")).addQueryStringParameters("name" -> name.toString))
			given Conversion[get, Future[Schema.Chain]] = (fun: get) => fun.apply()
		}
		class search(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.SearchChainsResponse]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.SearchChainsResponse])
		}
		object search {
			def apply(chainName: String, pageSize: Int)(using auth: AuthToken, ec: ExecutionContext): search = new search(auth(ws.url(BASE_URL + s"v1/chains:search")).addQueryStringParameters("chainName" -> chainName.toString, "pageSize" -> pageSize.toString))
			given Conversion[search, Future[Schema.SearchChainsResponse]] = (fun: search) => fun.apply()
		}
	}
	object googleLocations {
		class search(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withSearchGoogleLocationsRequest(body: Schema.SearchGoogleLocationsRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.SearchGoogleLocationsResponse])
		}
		object search {
			def apply()(using auth: AuthToken, ec: ExecutionContext): search = new search(auth(ws.url(BASE_URL + s"v1/googleLocations:search")).addQueryStringParameters())
		}
	}
	object accounts {
		object locations {
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListLocationsResponse]) {
				/** Optional. How many locations to fetch per page. Default value is 10 if not set. Minimum is 1, and maximum page size is 100.<br>Format: int32 */
				def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
				/** Optional. If specified, it fetches the next `page` of locations. The page token is returned by previous calls to `ListLocations` when there were more locations than could fit in the requested page size. */
				def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
				/** Optional. A filter constraining the locations to return. The response includes only entries that match the filter. If `filter` is empty, then constraints are applied and all locations (paginated) are retrieved for the requested account. For more information about valid fields and example usage, see [Work with Location Data Guide](https://developers.google.com/my-business/content/location-data#filter_results_when_you_list_locations). */
				def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
				/** Optional. Sorting order for the request. Multiple fields should be comma-separated, following SQL syntax. The default sorting order is ascending. To specify descending order, a suffix " desc" should be added. Valid fields to order_by are title and store_code. For example: "title, store_code desc" or "title" or "store_code desc" */
				def withOrderBy(orderBy: String) = new list(req.addQueryStringParameters("orderBy" -> orderBy.toString))
				def apply() = req.execute("GET").map(_.json.as[Schema.ListLocationsResponse])
			}
			object list {
				def apply(accountsId :PlayApi, parent: String, readMask: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/accounts/${accountsId}/locations")).addQueryStringParameters("parent" -> parent.toString, "readMask" -> readMask.toString))
				given Conversion[list, Future[Schema.ListLocationsResponse]] = (fun: list) => fun.apply()
			}
			class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				/** Optional. If true, the request is validated without actually creating the location. */
				def withValidateOnly(validateOnly: Boolean) = new create(req.addQueryStringParameters("validateOnly" -> validateOnly.toString))
				/** Optional. A unique request ID for the server to detect duplicated requests. We recommend using UUIDs. Max length is 50 characters. */
				def withRequestId(requestId: String) = new create(req.addQueryStringParameters("requestId" -> requestId.toString))
				def withLocation(body: Schema.Location) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Location])
			}
			object create {
				def apply(accountsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/accounts/${accountsId}/locations")).addQueryStringParameters("parent" -> parent.toString))
			}
		}
	}
}
