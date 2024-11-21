package com.boresjo.play.api.google.servicedirectory

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://servicedirectory.googleapis.com/"

	object projects {
		object locations {
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListLocationsResponse]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.ListLocationsResponse])
			}
			object list {
				def apply(projectsId :PlayApi, name: String, filter: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations")).addQueryStringParameters("name" -> name.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListLocationsResponse]] = (fun: list) => fun.apply()
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Location]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.Location])
			}
			object get {
				def apply(projectsId :PlayApi, locationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}")).addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.Location]] = (fun: get) => fun.apply()
			}
			object namespaces {
				class testIamPermissions(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withTestIamPermissionsRequest(body: Schema.TestIamPermissionsRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.TestIamPermissionsResponse])
				}
				object testIamPermissions {
					def apply(projectsId :PlayApi, locationsId :PlayApi, namespacesId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): testIamPermissions = new testIamPermissions(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/namespaces/${namespacesId}:testIamPermissions")).addQueryStringParameters("resource" -> resource.toString))
				}
				class getIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGetIamPolicyRequest(body: Schema.GetIamPolicyRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Policy])
				}
				object getIamPolicy {
					def apply(projectsId :PlayApi, locationsId :PlayApi, namespacesId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): getIamPolicy = new getIamPolicy(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/namespaces/${namespacesId}:getIamPolicy")).addQueryStringParameters("resource" -> resource.toString))
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					def apply() = req.execute("DELETE").map(_.json.as[Schema.Empty])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, namespacesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/namespaces/${namespacesId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Namespace]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.Namespace])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, namespacesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/namespaces/${namespacesId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.Namespace]] = (fun: get) => fun.apply()
				}
				class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withNamespace(body: Schema.Namespace) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.Namespace])
				}
				object patch {
					def apply(projectsId :PlayApi, locationsId :PlayApi, namespacesId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/namespaces/${namespacesId}")).addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListNamespacesResponse]) {
					/** Optional. The maximum number of items to return.<br>Format: int32 */
					def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
					/** Optional. The next_page_token value returned from a previous List request, if any. */
					def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
					/** Optional. The filter to list results by. General `filter` string syntax: ` ()` &#42; `` can be `name` or `labels.` for map field &#42; `` can be `<`, `>`, `<=`, `>=`, `!=`, `=`, `:`. Of which `:` means `HAS`, and is roughly the same as `=` &#42; `` must be the same data type as field &#42; `` can be `AND`, `OR`, `NOT` Examples of valid filters: &#42; `labels.owner` returns namespaces that have a label with the key `owner`, this is the same as `labels:owner` &#42; `labels.owner=sd` returns namespaces that have key/value `owner=sd` &#42; `name>projects/my-project/locations/us-east1/namespaces/namespace-c` returns namespaces that have name that is alphabetically later than the string, so "namespace-e" is returned but "namespace-a" is not &#42; `labels.owner!=sd AND labels.foo=bar` returns namespaces that have `owner` in label key but value is not `sd` AND have key/value `foo=bar` &#42; `doesnotexist.foo=bar` returns an empty list. Note that namespace doesn't have a field called "doesnotexist". Since the filter does not match any namespaces, it returns no results For more information about filtering, see [API Filtering](https://aip.dev/160). */
					def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
					/** Optional. The order to list results by. General `order_by` string syntax: ` () (,)` &#42; `` allows value: `name` &#42; `` ascending or descending order by ``. If this is left blank, `asc` is used Note that an empty `order_by` string results in default order, which is order by `name` in ascending order. */
					def withOrderBy(orderBy: String) = new list(req.addQueryStringParameters("orderBy" -> orderBy.toString))
					def apply() = req.execute("GET").map(_.json.as[Schema.ListNamespacesResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/namespaces")).addQueryStringParameters("parent" -> parent.toString))
					given Conversion[list, Future[Schema.ListNamespacesResponse]] = (fun: list) => fun.apply()
				}
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withNamespace(body: Schema.Namespace) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Namespace])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, namespaceId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/namespaces")).addQueryStringParameters("parent" -> parent.toString, "namespaceId" -> namespaceId.toString))
				}
				class setIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withSetIamPolicyRequest(body: Schema.SetIamPolicyRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Policy])
				}
				object setIamPolicy {
					def apply(projectsId :PlayApi, locationsId :PlayApi, namespacesId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): setIamPolicy = new setIamPolicy(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/namespaces/${namespacesId}:setIamPolicy")).addQueryStringParameters("resource" -> resource.toString))
				}
				object services {
					class testIamPermissions(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withTestIamPermissionsRequest(body: Schema.TestIamPermissionsRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.TestIamPermissionsResponse])
					}
					object testIamPermissions {
						def apply(projectsId :PlayApi, locationsId :PlayApi, namespacesId :PlayApi, servicesId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): testIamPermissions = new testIamPermissions(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/namespaces/${namespacesId}/services/${servicesId}:testIamPermissions")).addQueryStringParameters("resource" -> resource.toString))
					}
					class getIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGetIamPolicyRequest(body: Schema.GetIamPolicyRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Policy])
					}
					object getIamPolicy {
						def apply(projectsId :PlayApi, locationsId :PlayApi, namespacesId :PlayApi, servicesId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): getIamPolicy = new getIamPolicy(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/namespaces/${namespacesId}/services/${servicesId}:getIamPolicy")).addQueryStringParameters("resource" -> resource.toString))
					}
					class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Service]) {
						def apply() = req.execute("GET").map(_.json.as[Schema.Service])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, namespacesId :PlayApi, servicesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/namespaces/${namespacesId}/services/${servicesId}")).addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.Service]] = (fun: get) => fun.apply()
					}
					class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withService(body: Schema.Service) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.Service])
					}
					object patch {
						def apply(projectsId :PlayApi, locationsId :PlayApi, namespacesId :PlayApi, servicesId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/namespaces/${namespacesId}/services/${servicesId}")).addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
					}
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListServicesResponse]) {
						/** Optional. The maximum number of items to return.<br>Format: int32 */
						def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
						/** Optional. The next_page_token value returned from a previous List request, if any. */
						def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
						/** Optional. The filter to list results by. General `filter` string syntax: ` ()` &#42; `` can be `name` or `annotations.` for map field &#42; `` can be `<`, `>`, `<=`, `>=`, `!=`, `=`, `:`. Of which `:` means `HAS`, and is roughly the same as `=` &#42; `` must be the same data type as field &#42; `` can be `AND`, `OR`, `NOT` Examples of valid filters: &#42; `annotations.owner` returns services that have a annotation with the key `owner`, this is the same as `annotations:owner` &#42; `annotations.protocol=gRPC` returns services that have key/value `protocol=gRPC` &#42; `name>projects/my-project/locations/us-east1/namespaces/my-namespace/services/service-c` returns services that have name that is alphabetically later than the string, so "service-e" is returned but "service-a" is not &#42; `annotations.owner!=sd AND annotations.foo=bar` returns services that have `owner` in annotation key but value is not `sd` AND have key/value `foo=bar` &#42; `doesnotexist.foo=bar` returns an empty list. Note that service doesn't have a field called "doesnotexist". Since the filter does not match any services, it returns no results For more information about filtering, see [API Filtering](https://aip.dev/160). */
						def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
						/** Optional. The order to list results by. General `order_by` string syntax: ` () (,)` &#42; `` allows value: `name` &#42; `` ascending or descending order by ``. If this is left blank, `asc` is used Note that an empty `order_by` string results in default order, which is order by `name` in ascending order. */
						def withOrderBy(orderBy: String) = new list(req.addQueryStringParameters("orderBy" -> orderBy.toString))
						def apply() = req.execute("GET").map(_.json.as[Schema.ListServicesResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, namespacesId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/namespaces/${namespacesId}/services")).addQueryStringParameters("parent" -> parent.toString))
						given Conversion[list, Future[Schema.ListServicesResponse]] = (fun: list) => fun.apply()
					}
					class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withService(body: Schema.Service) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Service])
					}
					object create {
						def apply(projectsId :PlayApi, locationsId :PlayApi, namespacesId :PlayApi, parent: String, serviceId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/namespaces/${namespacesId}/services")).addQueryStringParameters("parent" -> parent.toString, "serviceId" -> serviceId.toString))
					}
					class setIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withSetIamPolicyRequest(body: Schema.SetIamPolicyRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Policy])
					}
					object setIamPolicy {
						def apply(projectsId :PlayApi, locationsId :PlayApi, namespacesId :PlayApi, servicesId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): setIamPolicy = new setIamPolicy(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/namespaces/${namespacesId}/services/${servicesId}:setIamPolicy")).addQueryStringParameters("resource" -> resource.toString))
					}
					class resolve(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withResolveServiceRequest(body: Schema.ResolveServiceRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.ResolveServiceResponse])
					}
					object resolve {
						def apply(projectsId :PlayApi, locationsId :PlayApi, namespacesId :PlayApi, servicesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): resolve = new resolve(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/namespaces/${namespacesId}/services/${servicesId}:resolve")).addQueryStringParameters("name" -> name.toString))
					}
					class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
						def apply() = req.execute("DELETE").map(_.json.as[Schema.Empty])
					}
					object delete {
						def apply(projectsId :PlayApi, locationsId :PlayApi, namespacesId :PlayApi, servicesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/namespaces/${namespacesId}/services/${servicesId}")).addQueryStringParameters("name" -> name.toString))
						given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
					}
					object endpoints {
						class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
							def withEndpoint(body: Schema.Endpoint) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Endpoint])
						}
						object create {
							def apply(projectsId :PlayApi, locationsId :PlayApi, namespacesId :PlayApi, servicesId :PlayApi, parent: String, endpointId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/namespaces/${namespacesId}/services/${servicesId}/endpoints")).addQueryStringParameters("parent" -> parent.toString, "endpointId" -> endpointId.toString))
						}
						class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
							def apply() = req.execute("DELETE").map(_.json.as[Schema.Empty])
						}
						object delete {
							def apply(projectsId :PlayApi, locationsId :PlayApi, namespacesId :PlayApi, servicesId :PlayApi, endpointsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/namespaces/${namespacesId}/services/${servicesId}/endpoints/${endpointsId}")).addQueryStringParameters("name" -> name.toString))
							given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
						}
						class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Endpoint]) {
							def apply() = req.execute("GET").map(_.json.as[Schema.Endpoint])
						}
						object get {
							def apply(projectsId :PlayApi, locationsId :PlayApi, namespacesId :PlayApi, servicesId :PlayApi, endpointsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/namespaces/${namespacesId}/services/${servicesId}/endpoints/${endpointsId}")).addQueryStringParameters("name" -> name.toString))
							given Conversion[get, Future[Schema.Endpoint]] = (fun: get) => fun.apply()
						}
						class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
							def withEndpoint(body: Schema.Endpoint) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.Endpoint])
						}
						object patch {
							def apply(projectsId :PlayApi, locationsId :PlayApi, namespacesId :PlayApi, servicesId :PlayApi, endpointsId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/namespaces/${namespacesId}/services/${servicesId}/endpoints/${endpointsId}")).addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
						}
						class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListEndpointsResponse]) {
							/** Optional. The maximum number of items to return.<br>Format: int32 */
							def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
							/** Optional. The next_page_token value returned from a previous List request, if any. */
							def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
							/** Optional. The filter to list results by. General `filter` string syntax: ` ()` &#42; `` can be `name`, `address`, `port`, or `annotations.` for map field &#42; `` can be `<`, `>`, `<=`, `>=`, `!=`, `=`, `:`. Of which `:` means `HAS`, and is roughly the same as `=` &#42; `` must be the same data type as field &#42; `` can be `AND`, `OR`, `NOT` Examples of valid filters: &#42; `annotations.owner` returns endpoints that have a annotation with the key `owner`, this is the same as `annotations:owner` &#42; `annotations.protocol=gRPC` returns endpoints that have key/value `protocol=gRPC` &#42; `address=192.108.1.105` returns endpoints that have this address &#42; `port>8080` returns endpoints that have port number larger than 8080 &#42; `name>projects/my-project/locations/us-east1/namespaces/my-namespace/services/my-service/endpoints/endpoint-c` returns endpoints that have name that is alphabetically later than the string, so "endpoint-e" is returned but "endpoint-a" is not &#42; `annotations.owner!=sd AND annotations.foo=bar` returns endpoints that have `owner` in annotation key but value is not `sd` AND have key/value `foo=bar` &#42; `doesnotexist.foo=bar` returns an empty list. Note that endpoint doesn't have a field called "doesnotexist". Since the filter does not match any endpoints, it returns no results For more information about filtering, see [API Filtering](https://aip.dev/160). */
							def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
							/** Optional. The order to list results by. General `order_by` string syntax: ` () (,)` &#42; `` allows values: `name`, `address`, `port` &#42; `` ascending or descending order by ``. If this is left blank, `asc` is used Note that an empty `order_by` string results in default order, which is order by `name` in ascending order. */
							def withOrderBy(orderBy: String) = new list(req.addQueryStringParameters("orderBy" -> orderBy.toString))
							def apply() = req.execute("GET").map(_.json.as[Schema.ListEndpointsResponse])
						}
						object list {
							def apply(projectsId :PlayApi, locationsId :PlayApi, namespacesId :PlayApi, servicesId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/namespaces/${namespacesId}/services/${servicesId}/endpoints")).addQueryStringParameters("parent" -> parent.toString))
							given Conversion[list, Future[Schema.ListEndpointsResponse]] = (fun: list) => fun.apply()
						}
					}
				}
			}
		}
	}
}
