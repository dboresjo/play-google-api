package com.boresjo.play.api.google.iap

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://iap.googleapis.com/"

	object v1 {
		class testIamPermissions(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withTestIamPermissionsRequest(body: Schema.TestIamPermissionsRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.TestIamPermissionsResponse])
		}
		object testIamPermissions {
			def apply(v1Id :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): testIamPermissions = new testIamPermissions(auth(ws.url(BASE_URL + s"v1/${v1Id}:testIamPermissions")).addQueryStringParameters("resource" -> resource.toString))
		}
		class setIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withSetIamPolicyRequest(body: Schema.SetIamPolicyRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Policy])
		}
		object setIamPolicy {
			def apply(v1Id :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): setIamPolicy = new setIamPolicy(auth(ws.url(BASE_URL + s"v1/${v1Id}:setIamPolicy")).addQueryStringParameters("resource" -> resource.toString))
		}
		class getIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withGetIamPolicyRequest(body: Schema.GetIamPolicyRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Policy])
		}
		object getIamPolicy {
			def apply(v1Id :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): getIamPolicy = new getIamPolicy(auth(ws.url(BASE_URL + s"v1/${v1Id}:getIamPolicy")).addQueryStringParameters("resource" -> resource.toString))
		}
		class getIapSettings(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.IapSettings]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.IapSettings])
		}
		object getIapSettings {
			def apply(v1Id :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): getIapSettings = new getIapSettings(auth(ws.url(BASE_URL + s"v1/${v1Id}:iapSettings")).addQueryStringParameters("name" -> name.toString))
			given Conversion[getIapSettings, Future[Schema.IapSettings]] = (fun: getIapSettings) => fun.apply()
		}
		class updateIapSettings(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withIapSettings(body: Schema.IapSettings) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.IapSettings])
		}
		object updateIapSettings {
			def apply(v1Id :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): updateIapSettings = new updateIapSettings(auth(ws.url(BASE_URL + s"v1/${v1Id}:iapSettings")).addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
		}
		class validateAttributeExpression(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ValidateIapAttributeExpressionResponse]) {
			def apply() = req.execute("POST").map(_.json.as[Schema.ValidateIapAttributeExpressionResponse])
		}
		object validateAttributeExpression {
			def apply(v1Id :PlayApi, name: String, expression: String)(using auth: AuthToken, ec: ExecutionContext): validateAttributeExpression = new validateAttributeExpression(auth(ws.url(BASE_URL + s"v1/${v1Id}:validateAttributeExpression")).addQueryStringParameters("name" -> name.toString, "expression" -> expression.toString))
			given Conversion[validateAttributeExpression, Future[Schema.ValidateIapAttributeExpressionResponse]] = (fun: validateAttributeExpression) => fun.apply()
		}
	}
	object projects {
		object iap_tunnel {
			object locations {
				object destGroups {
					class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withTunnelDestGroup(body: Schema.TunnelDestGroup) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.TunnelDestGroup])
					}
					object create {
						def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, tunnelDestGroupId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/iap_tunnel/locations/${locationsId}/destGroups")).addQueryStringParameters("parent" -> parent.toString, "tunnelDestGroupId" -> tunnelDestGroupId.toString))
					}
					class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
						def apply() = req.execute("DELETE").map(_.json.as[Schema.Empty])
					}
					object delete {
						def apply(projectsId :PlayApi, locationsId :PlayApi, destGroupsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/iap_tunnel/locations/${locationsId}/destGroups/${destGroupsId}")).addQueryStringParameters("name" -> name.toString))
						given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
					}
					class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.TunnelDestGroup]) {
						def apply() = req.execute("GET").map(_.json.as[Schema.TunnelDestGroup])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, destGroupsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/iap_tunnel/locations/${locationsId}/destGroups/${destGroupsId}")).addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.TunnelDestGroup]] = (fun: get) => fun.apply()
					}
					class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withTunnelDestGroup(body: Schema.TunnelDestGroup) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.TunnelDestGroup])
					}
					object patch {
						def apply(projectsId :PlayApi, locationsId :PlayApi, destGroupsId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/iap_tunnel/locations/${locationsId}/destGroups/${destGroupsId}")).addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
					}
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListTunnelDestGroupsResponse]) {
						def apply() = req.execute("GET").map(_.json.as[Schema.ListTunnelDestGroupsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/iap_tunnel/locations/${locationsId}/destGroups")).addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
						given Conversion[list, Future[Schema.ListTunnelDestGroupsResponse]] = (fun: list) => fun.apply()
					}
				}
			}
		}
		object brands {
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListBrandsResponse]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.ListBrandsResponse])
			}
			object list {
				def apply(projectsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/brands")).addQueryStringParameters("parent" -> parent.toString))
				given Conversion[list, Future[Schema.ListBrandsResponse]] = (fun: list) => fun.apply()
			}
			class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withBrand(body: Schema.Brand) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Brand])
			}
			object create {
				def apply(projectsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/brands")).addQueryStringParameters("parent" -> parent.toString))
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Brand]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.Brand])
			}
			object get {
				def apply(projectsId :PlayApi, brandsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/brands/${brandsId}")).addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.Brand]] = (fun: get) => fun.apply()
			}
			object identityAwareProxyClients {
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withIdentityAwareProxyClient(body: Schema.IdentityAwareProxyClient) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.IdentityAwareProxyClient])
				}
				object create {
					def apply(projectsId :PlayApi, brandsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/brands/${brandsId}/identityAwareProxyClients")).addQueryStringParameters("parent" -> parent.toString))
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					def apply() = req.execute("DELETE").map(_.json.as[Schema.Empty])
				}
				object delete {
					def apply(projectsId :PlayApi, brandsId :PlayApi, identityAwareProxyClientsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/brands/${brandsId}/identityAwareProxyClients/${identityAwareProxyClientsId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
				}
				class resetSecret(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withResetIdentityAwareProxyClientSecretRequest(body: Schema.ResetIdentityAwareProxyClientSecretRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.IdentityAwareProxyClient])
				}
				object resetSecret {
					def apply(projectsId :PlayApi, brandsId :PlayApi, identityAwareProxyClientsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): resetSecret = new resetSecret(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/brands/${brandsId}/identityAwareProxyClients/${identityAwareProxyClientsId}:resetSecret")).addQueryStringParameters("name" -> name.toString))
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.IdentityAwareProxyClient]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.IdentityAwareProxyClient])
				}
				object get {
					def apply(projectsId :PlayApi, brandsId :PlayApi, identityAwareProxyClientsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/brands/${brandsId}/identityAwareProxyClients/${identityAwareProxyClientsId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.IdentityAwareProxyClient]] = (fun: get) => fun.apply()
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListIdentityAwareProxyClientsResponse]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.ListIdentityAwareProxyClientsResponse])
				}
				object list {
					def apply(projectsId :PlayApi, brandsId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/brands/${brandsId}/identityAwareProxyClients")).addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListIdentityAwareProxyClientsResponse]] = (fun: list) => fun.apply()
				}
			}
		}
	}
}
