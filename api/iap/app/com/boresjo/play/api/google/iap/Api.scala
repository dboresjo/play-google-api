package com.boresjo.play.api.google.iap

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

	private val BASE_URL = "https://iap.googleapis.com/"

	object v1 {
		/** Returns permissions that a caller has on the Identity-Aware Proxy protected resource. More information about managing access via IAP can be found at: https://cloud.google.com/iap/docs/managing-access#managing_access_via_the_api */
		class testIamPermissions(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def withTestIamPermissionsRequest(body: Schema.TestIamPermissionsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.TestIamPermissionsResponse])
		}
		object testIamPermissions {
			def apply(v1Id :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): testIamPermissions = new testIamPermissions(ws.url(BASE_URL + s"v1/${v1Id}:testIamPermissions").addQueryStringParameters("resource" -> resource.toString))
		}
		/** Sets the access control policy for an Identity-Aware Proxy protected resource. Replaces any existing policy. More information about managing access via IAP can be found at: https://cloud.google.com/iap/docs/managing-access#managing_access_via_the_api */
		class setIamPolicy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def withSetIamPolicyRequest(body: Schema.SetIamPolicyRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
		}
		object setIamPolicy {
			def apply(v1Id :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): setIamPolicy = new setIamPolicy(ws.url(BASE_URL + s"v1/${v1Id}:setIamPolicy").addQueryStringParameters("resource" -> resource.toString))
		}
		/** Gets the access control policy for an Identity-Aware Proxy protected resource. More information about managing access via IAP can be found at: https://cloud.google.com/iap/docs/managing-access#managing_access_via_the_api */
		class getIamPolicy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def withGetIamPolicyRequest(body: Schema.GetIamPolicyRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
		}
		object getIamPolicy {
			def apply(v1Id :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): getIamPolicy = new getIamPolicy(ws.url(BASE_URL + s"v1/${v1Id}:getIamPolicy").addQueryStringParameters("resource" -> resource.toString))
		}
		/** Gets the IAP settings on a particular IAP protected resource. */
		class getIapSettings(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.IapSettings]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.IapSettings])
		}
		object getIapSettings {
			def apply(v1Id :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): getIapSettings = new getIapSettings(ws.url(BASE_URL + s"v1/${v1Id}:iapSettings").addQueryStringParameters("name" -> name.toString))
			given Conversion[getIapSettings, Future[Schema.IapSettings]] = (fun: getIapSettings) => fun.apply()
		}
		/** Updates the IAP settings on a particular IAP protected resource. It replaces all fields unless the `update_mask` is set. */
		class updateIapSettings(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def withIapSettings(body: Schema.IapSettings) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.IapSettings])
		}
		object updateIapSettings {
			def apply(v1Id :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): updateIapSettings = new updateIapSettings(ws.url(BASE_URL + s"v1/${v1Id}:iapSettings").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
		}
		/** Validates that a given CEL expression conforms to IAP restrictions. */
		class validateAttributeExpression(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ValidateIapAttributeExpressionResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("POST")).map(_.json.as[Schema.ValidateIapAttributeExpressionResponse])
		}
		object validateAttributeExpression {
			def apply(v1Id :PlayApi, name: String, expression: String)(using signer: RequestSigner, ec: ExecutionContext): validateAttributeExpression = new validateAttributeExpression(ws.url(BASE_URL + s"v1/${v1Id}:validateAttributeExpression").addQueryStringParameters("name" -> name.toString, "expression" -> expression.toString))
			given Conversion[validateAttributeExpression, Future[Schema.ValidateIapAttributeExpressionResponse]] = (fun: validateAttributeExpression) => fun.apply()
		}
	}
	object projects {
		object iap_tunnel {
			object locations {
				object destGroups {
					/** Creates a new TunnelDestGroup. */
					class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withTunnelDestGroup(body: Schema.TunnelDestGroup) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.TunnelDestGroup])
					}
					object create {
						def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, tunnelDestGroupId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/iap_tunnel/locations/${locationsId}/destGroups").addQueryStringParameters("parent" -> parent.toString, "tunnelDestGroupId" -> tunnelDestGroupId.toString))
					}
					/** Deletes a TunnelDestGroup. */
					class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
					}
					object delete {
						def apply(projectsId :PlayApi, locationsId :PlayApi, destGroupsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/iap_tunnel/locations/${locationsId}/destGroups/${destGroupsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
					}
					/** Retrieves an existing TunnelDestGroup. */
					class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.TunnelDestGroup]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.TunnelDestGroup])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, destGroupsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/iap_tunnel/locations/${locationsId}/destGroups/${destGroupsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.TunnelDestGroup]] = (fun: get) => fun.apply()
					}
					/** Updates a TunnelDestGroup. */
					class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withTunnelDestGroup(body: Schema.TunnelDestGroup) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.TunnelDestGroup])
					}
					object patch {
						def apply(projectsId :PlayApi, locationsId :PlayApi, destGroupsId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/iap_tunnel/locations/${locationsId}/destGroups/${destGroupsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
					}
					/** Lists the existing TunnelDestGroups. To group across all locations, use a `-` as the location ID. For example: `/v1/projects/123/iap_tunnel/locations/-/destGroups` */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListTunnelDestGroupsResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListTunnelDestGroupsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/iap_tunnel/locations/${locationsId}/destGroups").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
						given Conversion[list, Future[Schema.ListTunnelDestGroupsResponse]] = (fun: list) => fun.apply()
					}
				}
			}
		}
		object brands {
			/** Lists the existing brands for the project. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListBrandsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListBrandsResponse])
			}
			object list {
				def apply(projectsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/brands").addQueryStringParameters("parent" -> parent.toString))
				given Conversion[list, Future[Schema.ListBrandsResponse]] = (fun: list) => fun.apply()
			}
			/** Constructs a new OAuth brand for the project if one does not exist. The created brand is "internal only", meaning that OAuth clients created under it only accept requests from users who belong to the same Google Workspace organization as the project. The brand is created in an un-reviewed status. NOTE: The "internal only" status can be manually changed in the Google Cloud Console. Requires that a brand does not already exist for the project, and that the specified support email is owned by the caller. */
			class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withBrand(body: Schema.Brand) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Brand])
			}
			object create {
				def apply(projectsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/brands").addQueryStringParameters("parent" -> parent.toString))
			}
			/** Retrieves the OAuth brand of the project. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Brand]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Brand])
			}
			object get {
				def apply(projectsId :PlayApi, brandsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/brands/${brandsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.Brand]] = (fun: get) => fun.apply()
			}
			object identityAwareProxyClients {
				/** Creates an Identity Aware Proxy (IAP) OAuth client. The client is owned by IAP. Requires that the brand for the project exists and that it is set for internal-only use. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withIdentityAwareProxyClient(body: Schema.IdentityAwareProxyClient) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.IdentityAwareProxyClient])
				}
				object create {
					def apply(projectsId :PlayApi, brandsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/brands/${brandsId}/identityAwareProxyClients").addQueryStringParameters("parent" -> parent.toString))
				}
				/** Deletes an Identity Aware Proxy (IAP) OAuth client. Useful for removing obsolete clients, managing the number of clients in a given project, and cleaning up after tests. Requires that the client is owned by IAP. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
				}
				object delete {
					def apply(projectsId :PlayApi, brandsId :PlayApi, identityAwareProxyClientsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/brands/${brandsId}/identityAwareProxyClients/${identityAwareProxyClientsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
				}
				/** Resets an Identity Aware Proxy (IAP) OAuth client secret. Useful if the secret was compromised. Requires that the client is owned by IAP. */
				class resetSecret(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withResetIdentityAwareProxyClientSecretRequest(body: Schema.ResetIdentityAwareProxyClientSecretRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.IdentityAwareProxyClient])
				}
				object resetSecret {
					def apply(projectsId :PlayApi, brandsId :PlayApi, identityAwareProxyClientsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): resetSecret = new resetSecret(ws.url(BASE_URL + s"v1/projects/${projectsId}/brands/${brandsId}/identityAwareProxyClients/${identityAwareProxyClientsId}:resetSecret").addQueryStringParameters("name" -> name.toString))
				}
				/** Retrieves an Identity Aware Proxy (IAP) OAuth client. Requires that the client is owned by IAP. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.IdentityAwareProxyClient]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.IdentityAwareProxyClient])
				}
				object get {
					def apply(projectsId :PlayApi, brandsId :PlayApi, identityAwareProxyClientsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/brands/${brandsId}/identityAwareProxyClients/${identityAwareProxyClientsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.IdentityAwareProxyClient]] = (fun: get) => fun.apply()
				}
				/** Lists the existing clients for the brand. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListIdentityAwareProxyClientsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListIdentityAwareProxyClientsResponse])
				}
				object list {
					def apply(projectsId :PlayApi, brandsId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/brands/${brandsId}/identityAwareProxyClients").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListIdentityAwareProxyClientsResponse]] = (fun: list) => fun.apply()
				}
			}
		}
	}
}
