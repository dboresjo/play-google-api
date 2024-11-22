package com.boresjo.play.api.google.mybusinessplaceactions

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

	private val BASE_URL = "https://mybusinessplaceactions.googleapis.com/"

	object placeActionTypeMetadata {
		/** Returns the list of available place action types for a location or country. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListPlaceActionTypeMetadataResponse]) {
			val scopes = Seq()
			/** Optional. The IETF BCP-47 code of language to get display names in. If this language is not available, they will be provided in English. */
			def withLanguageCode(languageCode: String) = new list(req.addQueryStringParameters("languageCode" -> languageCode.toString))
			/** Optional. How many action types to include per page. Default is 10, minimum is 1.<br>Format: int32 */
			def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
			/** Optional. If specified, the next page of place action type metadata is retrieved. The `pageToken` is returned when a call to `placeActionTypeMetadata.list` returns more results than can fit into the requested page size. */
			def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
			/** Optional. A filter constraining the place action types to return metadata for. The response includes entries that match the filter. We support only the following filters: 1. location=XYZ where XYZ is a string indicating the resource name of a location, in the format `locations/{location_id}`. 2. region_code=XYZ where XYZ is a Unicode CLDR region code to find available action types. If no filter is provided, all place action types are returned. */
			def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListPlaceActionTypeMetadataResponse])
		}
		object list {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/placeActionTypeMetadata").addQueryStringParameters())
			given Conversion[list, Future[Schema.ListPlaceActionTypeMetadataResponse]] = (fun: list) => fun.apply()
		}
	}
	object locations {
		object placeActionLinks {
			/** Creates a place action link associated with the specified location, and returns it. The request is considered duplicate if the `parent`, `place_action_link.uri` and `place_action_link.place_action_type` are the same as a previous request. */
			class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq()
				/** Perform the request */
				def withPlaceActionLink(body: Schema.PlaceActionLink) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.PlaceActionLink])
			}
			object create {
				def apply(locationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/locations/${locationsId}/placeActionLinks").addQueryStringParameters("parent" -> parent.toString))
			}
			/** Deletes a place action link from the specified location. */
			class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
				val scopes = Seq()
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
			}
			object delete {
				def apply(locationsId :PlayApi, placeActionLinksId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/locations/${locationsId}/placeActionLinks/${placeActionLinksId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
			}
			/** Gets the specified place action link. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.PlaceActionLink]) {
				val scopes = Seq()
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.PlaceActionLink])
			}
			object get {
				def apply(locationsId :PlayApi, placeActionLinksId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/locations/${locationsId}/placeActionLinks/${placeActionLinksId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.PlaceActionLink]] = (fun: get) => fun.apply()
			}
			/** Updates the specified place action link and returns it. */
			class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq()
				/** Optional. The resource name, in the format `locations/{location_id}/placeActionLinks/{place_action_link_id}`. The name field will only be considered in UpdatePlaceActionLink and DeletePlaceActionLink requests for updating and deleting links respectively. However, it will be ignored in CreatePlaceActionLink request, where `place_action_link_id` will be assigned by the server on successful creation of a new link and returned as part of the response. */
				def withName(name: String) = new patch(req.addQueryStringParameters("name" -> name.toString))
				/** Perform the request */
				def withPlaceActionLink(body: Schema.PlaceActionLink) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.PlaceActionLink])
			}
			object patch {
				def apply(locationsId :PlayApi, placeActionLinksId :PlayApi, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/locations/${locationsId}/placeActionLinks/${placeActionLinksId}").addQueryStringParameters("updateMask" -> updateMask.toString))
			}
			/** Lists the place action links for the specified location. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListPlaceActionLinksResponse]) {
				val scopes = Seq()
				/** Optional. A filter constraining the place action links to return. The response includes entries that match the filter. We support only the following filter: 1. place_action_type=XYZ where XYZ is a valid PlaceActionType. */
				def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
				/** Optional. How many place action links to return per page. Default of 10. The minimum is 1.<br>Format: int32 */
				def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
				/** Optional. If specified, returns the next page of place action links. */
				def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListPlaceActionLinksResponse])
			}
			object list {
				def apply(locationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/locations/${locationsId}/placeActionLinks").addQueryStringParameters("parent" -> parent.toString))
				given Conversion[list, Future[Schema.ListPlaceActionLinksResponse]] = (fun: list) => fun.apply()
			}
		}
	}
}
