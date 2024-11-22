package com.boresjo.play.api.google.kmsinventory

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
		"""https://www.googleapis.com/auth/cloud-platform""" /* See, edit, configure, and delete your Google Cloud data and see the email address for your Google Account. */
	)

	private val BASE_URL = "https://kmsinventory.googleapis.com/"

	object projects {
		object cryptoKeys {
			/** Returns cryptographic keys managed by Cloud KMS in a given Cloud project. Note that this data is sourced from snapshots, meaning it may not completely reflect the actual state of key metadata at call time. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudKmsInventoryV1ListCryptoKeysResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Optional. The maximum number of keys to return. The service may return fewer than this value. If unspecified, at most 1000 keys will be returned. The maximum value is 1000; values above 1000 will be coerced to 1000.<br>Format: int32 */
				def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
				/** Optional. Pass this into a subsequent request in order to receive the next page of results. */
				def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudKmsInventoryV1ListCryptoKeysResponse])
			}
			object list {
				def apply(projectsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/cryptoKeys").addQueryStringParameters("parent" -> parent.toString))
				given Conversion[list, Future[Schema.GoogleCloudKmsInventoryV1ListCryptoKeysResponse]] = (fun: list) => fun.apply()
			}
		}
		object locations {
			object keyRings {
				object cryptoKeys {
					/** Returns aggregate information about the resources protected by the given Cloud KMS CryptoKey. Only resources within the same Cloud organization as the key will be returned. The project that holds the key must be part of an organization in order for this call to succeed. */
					class getProtectedResourcesSummary(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudKmsInventoryV1ProtectedResourcesSummary]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudKmsInventoryV1ProtectedResourcesSummary])
					}
					object getProtectedResourcesSummary {
						def apply(projectsId :PlayApi, locationsId :PlayApi, keyRingsId :PlayApi, cryptoKeysId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): getProtectedResourcesSummary = new getProtectedResourcesSummary(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/keyRings/${keyRingsId}/cryptoKeys/${cryptoKeysId}/protectedResourcesSummary").addQueryStringParameters("name" -> name.toString))
						given Conversion[getProtectedResourcesSummary, Future[Schema.GoogleCloudKmsInventoryV1ProtectedResourcesSummary]] = (fun: getProtectedResourcesSummary) => fun.apply()
					}
				}
			}
		}
	}
	object organizations {
		object protectedResources {
			/** Returns metadata about the resources protected by the given Cloud KMS CryptoKey in the given Cloud organization. */
			class search(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudKmsInventoryV1SearchProtectedResourcesResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Optional. A list of resource types that this request searches for. If empty, it will search all the [trackable resource types](https://cloud.google.com/kms/docs/view-key-usage#tracked-resource-types). Regular expressions are also supported. For example: &#42; `compute.googleapis.com.&#42;` snapshots resources whose type starts with `compute.googleapis.com`. &#42; `.&#42;Image` snapshots resources whose type ends with `Image`. &#42; `.&#42;Image.&#42;` snapshots resources whose type contains `Image`. See [RE2](https://github.com/google/re2/wiki/Syntax) for all supported regular expression syntax. If the regular expression does not match any supported resource type, an INVALID_ARGUMENT error will be returned. */
				def withResourceTypes(resourceTypes: String) = new search(req.addQueryStringParameters("resourceTypes" -> resourceTypes.toString))
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudKmsInventoryV1SearchProtectedResourcesResponse])
			}
			object search {
				def apply(organizationsId :PlayApi, pageToken: String, cryptoKey: String, scope: String, pageSize: Int)(using signer: RequestSigner, ec: ExecutionContext): search = new search(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/protectedResources:search").addQueryStringParameters("pageToken" -> pageToken.toString, "cryptoKey" -> cryptoKey.toString, "scope" -> scope.toString, "pageSize" -> pageSize.toString))
				given Conversion[search, Future[Schema.GoogleCloudKmsInventoryV1SearchProtectedResourcesResponse]] = (fun: search) => fun.apply()
			}
		}
	}
}
