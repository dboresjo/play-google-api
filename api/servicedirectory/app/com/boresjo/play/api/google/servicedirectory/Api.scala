package com.boresjo.play.api.google.servicedirectory

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

	private val BASE_URL = "https://servicedirectory.googleapis.com/"

	object projects {
		object locations {
			/** Lists information about the supported locations for this service. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListLocationsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListLocationsResponse])
			}
			object list {
				def apply(projectsId :PlayApi, name: String, filter: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations").addQueryStringParameters("name" -> name.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListLocationsResponse]] = (fun: list) => fun.apply()
			}
			/** Gets information about a location. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Location]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Location])
			}
			object get {
				def apply(projectsId :PlayApi, locationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.Location]] = (fun: get) => fun.apply()
			}
			object namespaces {
				/** Tests IAM permissions for a resource (namespace or service only). */
				class testIamPermissions(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withTestIamPermissionsRequest(body: Schema.TestIamPermissionsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.TestIamPermissionsResponse])
				}
				object testIamPermissions {
					def apply(projectsId :PlayApi, locationsId :PlayApi, namespacesId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): testIamPermissions = new testIamPermissions(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/namespaces/${namespacesId}:testIamPermissions").addQueryStringParameters("resource" -> resource.toString))
				}
				/** Gets the IAM Policy for a resource (namespace or service only). */
				class getIamPolicy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGetIamPolicyRequest(body: Schema.GetIamPolicyRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
				}
				object getIamPolicy {
					def apply(projectsId :PlayApi, locationsId :PlayApi, namespacesId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): getIamPolicy = new getIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/namespaces/${namespacesId}:getIamPolicy").addQueryStringParameters("resource" -> resource.toString))
				}
				/** Deletes a namespace. This also deletes all services and endpoints in the namespace. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, namespacesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/namespaces/${namespacesId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
				}
				/** Gets a namespace. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Namespace]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Namespace])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, namespacesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/namespaces/${namespacesId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.Namespace]] = (fun: get) => fun.apply()
				}
				/** Updates a namespace. */
				class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withNamespace(body: Schema.Namespace) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Namespace])
				}
				object patch {
					def apply(projectsId :PlayApi, locationsId :PlayApi, namespacesId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/namespaces/${namespacesId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
				}
				/** Lists all namespaces. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListNamespacesResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Optional. The maximum number of items to return.<br>Format: int32 */
					def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
					/** Optional. The next_page_token value returned from a previous List request, if any. */
					def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
					/** Optional. The filter to list results by. General `filter` string syntax: ` ()` &#42; `` can be `name` or `labels.` for map field &#42; `` can be `<`, `>`, `<=`, `>=`, `!=`, `=`, `:`. Of which `:` means `HAS`, and is roughly the same as `=` &#42; `` must be the same data type as field &#42; `` can be `AND`, `OR`, `NOT` Examples of valid filters: &#42; `labels.owner` returns namespaces that have a label with the key `owner`, this is the same as `labels:owner` &#42; `labels.owner=sd` returns namespaces that have key/value `owner=sd` &#42; `name>projects/my-project/locations/us-east1/namespaces/namespace-c` returns namespaces that have name that is alphabetically later than the string, so "namespace-e" is returned but "namespace-a" is not &#42; `labels.owner!=sd AND labels.foo=bar` returns namespaces that have `owner` in label key but value is not `sd` AND have key/value `foo=bar` &#42; `doesnotexist.foo=bar` returns an empty list. Note that namespace doesn't have a field called "doesnotexist". Since the filter does not match any namespaces, it returns no results For more information about filtering, see [API Filtering](https://aip.dev/160). */
					def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
					/** Optional. The order to list results by. General `order_by` string syntax: ` () (,)` &#42; `` allows value: `name` &#42; `` ascending or descending order by ``. If this is left blank, `asc` is used Note that an empty `order_by` string results in default order, which is order by `name` in ascending order. */
					def withOrderBy(orderBy: String) = new list(req.addQueryStringParameters("orderBy" -> orderBy.toString))
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListNamespacesResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/namespaces").addQueryStringParameters("parent" -> parent.toString))
					given Conversion[list, Future[Schema.ListNamespacesResponse]] = (fun: list) => fun.apply()
				}
				/** Creates a namespace, and returns the new namespace. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withNamespace(body: Schema.Namespace) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Namespace])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, namespaceId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/namespaces").addQueryStringParameters("parent" -> parent.toString, "namespaceId" -> namespaceId.toString))
				}
				/** Sets the IAM Policy for a resource (namespace or service only). */
				class setIamPolicy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withSetIamPolicyRequest(body: Schema.SetIamPolicyRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
				}
				object setIamPolicy {
					def apply(projectsId :PlayApi, locationsId :PlayApi, namespacesId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): setIamPolicy = new setIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/namespaces/${namespacesId}:setIamPolicy").addQueryStringParameters("resource" -> resource.toString))
				}
				object services {
					/** Tests IAM permissions for a resource (namespace or service only). */
					class testIamPermissions(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withTestIamPermissionsRequest(body: Schema.TestIamPermissionsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.TestIamPermissionsResponse])
					}
					object testIamPermissions {
						def apply(projectsId :PlayApi, locationsId :PlayApi, namespacesId :PlayApi, servicesId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): testIamPermissions = new testIamPermissions(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/namespaces/${namespacesId}/services/${servicesId}:testIamPermissions").addQueryStringParameters("resource" -> resource.toString))
					}
					/** Gets the IAM Policy for a resource (namespace or service only). */
					class getIamPolicy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withGetIamPolicyRequest(body: Schema.GetIamPolicyRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
					}
					object getIamPolicy {
						def apply(projectsId :PlayApi, locationsId :PlayApi, namespacesId :PlayApi, servicesId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): getIamPolicy = new getIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/namespaces/${namespacesId}/services/${servicesId}:getIamPolicy").addQueryStringParameters("resource" -> resource.toString))
					}
					/** Gets a service. */
					class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Service]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Service])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, namespacesId :PlayApi, servicesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/namespaces/${namespacesId}/services/${servicesId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.Service]] = (fun: get) => fun.apply()
					}
					/** Updates a service. */
					class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withService(body: Schema.Service) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Service])
					}
					object patch {
						def apply(projectsId :PlayApi, locationsId :PlayApi, namespacesId :PlayApi, servicesId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/namespaces/${namespacesId}/services/${servicesId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
					}
					/** Lists all services belonging to a namespace. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListServicesResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Optional. The maximum number of items to return.<br>Format: int32 */
						def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
						/** Optional. The next_page_token value returned from a previous List request, if any. */
						def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
						/** Optional. The filter to list results by. General `filter` string syntax: ` ()` &#42; `` can be `name` or `annotations.` for map field &#42; `` can be `<`, `>`, `<=`, `>=`, `!=`, `=`, `:`. Of which `:` means `HAS`, and is roughly the same as `=` &#42; `` must be the same data type as field &#42; `` can be `AND`, `OR`, `NOT` Examples of valid filters: &#42; `annotations.owner` returns services that have a annotation with the key `owner`, this is the same as `annotations:owner` &#42; `annotations.protocol=gRPC` returns services that have key/value `protocol=gRPC` &#42; `name>projects/my-project/locations/us-east1/namespaces/my-namespace/services/service-c` returns services that have name that is alphabetically later than the string, so "service-e" is returned but "service-a" is not &#42; `annotations.owner!=sd AND annotations.foo=bar` returns services that have `owner` in annotation key but value is not `sd` AND have key/value `foo=bar` &#42; `doesnotexist.foo=bar` returns an empty list. Note that service doesn't have a field called "doesnotexist". Since the filter does not match any services, it returns no results For more information about filtering, see [API Filtering](https://aip.dev/160). */
						def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
						/** Optional. The order to list results by. General `order_by` string syntax: ` () (,)` &#42; `` allows value: `name` &#42; `` ascending or descending order by ``. If this is left blank, `asc` is used Note that an empty `order_by` string results in default order, which is order by `name` in ascending order. */
						def withOrderBy(orderBy: String) = new list(req.addQueryStringParameters("orderBy" -> orderBy.toString))
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListServicesResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, namespacesId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/namespaces/${namespacesId}/services").addQueryStringParameters("parent" -> parent.toString))
						given Conversion[list, Future[Schema.ListServicesResponse]] = (fun: list) => fun.apply()
					}
					/** Creates a service, and returns the new service. */
					class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withService(body: Schema.Service) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Service])
					}
					object create {
						def apply(projectsId :PlayApi, locationsId :PlayApi, namespacesId :PlayApi, parent: String, serviceId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/namespaces/${namespacesId}/services").addQueryStringParameters("parent" -> parent.toString, "serviceId" -> serviceId.toString))
					}
					/** Sets the IAM Policy for a resource (namespace or service only). */
					class setIamPolicy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withSetIamPolicyRequest(body: Schema.SetIamPolicyRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
					}
					object setIamPolicy {
						def apply(projectsId :PlayApi, locationsId :PlayApi, namespacesId :PlayApi, servicesId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): setIamPolicy = new setIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/namespaces/${namespacesId}/services/${servicesId}:setIamPolicy").addQueryStringParameters("resource" -> resource.toString))
					}
					/** Returns a service and its associated endpoints. Resolving a service is not considered an active developer method. */
					class resolve(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withResolveServiceRequest(body: Schema.ResolveServiceRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.ResolveServiceResponse])
					}
					object resolve {
						def apply(projectsId :PlayApi, locationsId :PlayApi, namespacesId :PlayApi, servicesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): resolve = new resolve(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/namespaces/${namespacesId}/services/${servicesId}:resolve").addQueryStringParameters("name" -> name.toString))
					}
					/** Deletes a service. This also deletes all endpoints associated with the service. */
					class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
					}
					object delete {
						def apply(projectsId :PlayApi, locationsId :PlayApi, namespacesId :PlayApi, servicesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/namespaces/${namespacesId}/services/${servicesId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
					}
					object endpoints {
						/** Creates an endpoint, and returns the new endpoint. */
						class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def withEndpoint(body: Schema.Endpoint) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Endpoint])
						}
						object create {
							def apply(projectsId :PlayApi, locationsId :PlayApi, namespacesId :PlayApi, servicesId :PlayApi, parent: String, endpointId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/namespaces/${namespacesId}/services/${servicesId}/endpoints").addQueryStringParameters("parent" -> parent.toString, "endpointId" -> endpointId.toString))
						}
						/** Deletes an endpoint. */
						class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
						}
						object delete {
							def apply(projectsId :PlayApi, locationsId :PlayApi, namespacesId :PlayApi, servicesId :PlayApi, endpointsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/namespaces/${namespacesId}/services/${servicesId}/endpoints/${endpointsId}").addQueryStringParameters("name" -> name.toString))
							given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
						}
						/** Gets an endpoint. */
						class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Endpoint]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Endpoint])
						}
						object get {
							def apply(projectsId :PlayApi, locationsId :PlayApi, namespacesId :PlayApi, servicesId :PlayApi, endpointsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/namespaces/${namespacesId}/services/${servicesId}/endpoints/${endpointsId}").addQueryStringParameters("name" -> name.toString))
							given Conversion[get, Future[Schema.Endpoint]] = (fun: get) => fun.apply()
						}
						/** Updates an endpoint. */
						class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def withEndpoint(body: Schema.Endpoint) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Endpoint])
						}
						object patch {
							def apply(projectsId :PlayApi, locationsId :PlayApi, namespacesId :PlayApi, servicesId :PlayApi, endpointsId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/namespaces/${namespacesId}/services/${servicesId}/endpoints/${endpointsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
						}
						/** Lists all endpoints. */
						class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListEndpointsResponse]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Optional. The maximum number of items to return.<br>Format: int32 */
							def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
							/** Optional. The next_page_token value returned from a previous List request, if any. */
							def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
							/** Optional. The filter to list results by. General `filter` string syntax: ` ()` &#42; `` can be `name`, `address`, `port`, or `annotations.` for map field &#42; `` can be `<`, `>`, `<=`, `>=`, `!=`, `=`, `:`. Of which `:` means `HAS`, and is roughly the same as `=` &#42; `` must be the same data type as field &#42; `` can be `AND`, `OR`, `NOT` Examples of valid filters: &#42; `annotations.owner` returns endpoints that have a annotation with the key `owner`, this is the same as `annotations:owner` &#42; `annotations.protocol=gRPC` returns endpoints that have key/value `protocol=gRPC` &#42; `address=192.108.1.105` returns endpoints that have this address &#42; `port>8080` returns endpoints that have port number larger than 8080 &#42; `name>projects/my-project/locations/us-east1/namespaces/my-namespace/services/my-service/endpoints/endpoint-c` returns endpoints that have name that is alphabetically later than the string, so "endpoint-e" is returned but "endpoint-a" is not &#42; `annotations.owner!=sd AND annotations.foo=bar` returns endpoints that have `owner` in annotation key but value is not `sd` AND have key/value `foo=bar` &#42; `doesnotexist.foo=bar` returns an empty list. Note that endpoint doesn't have a field called "doesnotexist". Since the filter does not match any endpoints, it returns no results For more information about filtering, see [API Filtering](https://aip.dev/160). */
							def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
							/** Optional. The order to list results by. General `order_by` string syntax: ` () (,)` &#42; `` allows values: `name`, `address`, `port` &#42; `` ascending or descending order by ``. If this is left blank, `asc` is used Note that an empty `order_by` string results in default order, which is order by `name` in ascending order. */
							def withOrderBy(orderBy: String) = new list(req.addQueryStringParameters("orderBy" -> orderBy.toString))
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListEndpointsResponse])
						}
						object list {
							def apply(projectsId :PlayApi, locationsId :PlayApi, namespacesId :PlayApi, servicesId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/namespaces/${namespacesId}/services/${servicesId}/endpoints").addQueryStringParameters("parent" -> parent.toString))
							given Conversion[list, Future[Schema.ListEndpointsResponse]] = (fun: list) => fun.apply()
						}
					}
				}
			}
		}
	}
}
