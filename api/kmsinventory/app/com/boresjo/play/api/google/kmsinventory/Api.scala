package com.boresjo.play.api.google.kmsinventory

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://kmsinventory.googleapis.com/"

	object projects {
		object cryptoKeys {
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudKmsInventoryV1ListCryptoKeysResponse]) {
				/** Optional. The maximum number of keys to return. The service may return fewer than this value. If unspecified, at most 1000 keys will be returned. The maximum value is 1000; values above 1000 will be coerced to 1000.<br>Format: int32 */
				def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
				/** Optional. Pass this into a subsequent request in order to receive the next page of results. */
				def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
				def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudKmsInventoryV1ListCryptoKeysResponse])
			}
			object list {
				def apply(projectsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/cryptoKeys")).addQueryStringParameters("parent" -> parent.toString))
				given Conversion[list, Future[Schema.GoogleCloudKmsInventoryV1ListCryptoKeysResponse]] = (fun: list) => fun.apply()
			}
		}
		object locations {
			object keyRings {
				object cryptoKeys {
					class getProtectedResourcesSummary(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudKmsInventoryV1ProtectedResourcesSummary]) {
						def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudKmsInventoryV1ProtectedResourcesSummary])
					}
					object getProtectedResourcesSummary {
						def apply(projectsId :PlayApi, locationsId :PlayApi, keyRingsId :PlayApi, cryptoKeysId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): getProtectedResourcesSummary = new getProtectedResourcesSummary(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/keyRings/${keyRingsId}/cryptoKeys/${cryptoKeysId}/protectedResourcesSummary")).addQueryStringParameters("name" -> name.toString))
						given Conversion[getProtectedResourcesSummary, Future[Schema.GoogleCloudKmsInventoryV1ProtectedResourcesSummary]] = (fun: getProtectedResourcesSummary) => fun.apply()
					}
				}
			}
		}
	}
	object organizations {
		object protectedResources {
			class search(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudKmsInventoryV1SearchProtectedResourcesResponse]) {
				/** Optional. A list of resource types that this request searches for. If empty, it will search all the [trackable resource types](https://cloud.google.com/kms/docs/view-key-usage#tracked-resource-types). Regular expressions are also supported. For example: &#42; `compute.googleapis.com.&#42;` snapshots resources whose type starts with `compute.googleapis.com`. &#42; `.&#42;Image` snapshots resources whose type ends with `Image`. &#42; `.&#42;Image.&#42;` snapshots resources whose type contains `Image`. See [RE2](https://github.com/google/re2/wiki/Syntax) for all supported regular expression syntax. If the regular expression does not match any supported resource type, an INVALID_ARGUMENT error will be returned. */
				def withResourceTypes(resourceTypes: String) = new search(req.addQueryStringParameters("resourceTypes" -> resourceTypes.toString))
				def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudKmsInventoryV1SearchProtectedResourcesResponse])
			}
			object search {
				def apply(organizationsId :PlayApi, pageToken: String, cryptoKey: String, scope: String, pageSize: Int)(using auth: AuthToken, ec: ExecutionContext): search = new search(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/protectedResources:search")).addQueryStringParameters("pageToken" -> pageToken.toString, "cryptoKey" -> cryptoKey.toString, "scope" -> scope.toString, "pageSize" -> pageSize.toString))
				given Conversion[search, Future[Schema.GoogleCloudKmsInventoryV1SearchProtectedResourcesResponse]] = (fun: search) => fun.apply()
			}
		}
	}
}
