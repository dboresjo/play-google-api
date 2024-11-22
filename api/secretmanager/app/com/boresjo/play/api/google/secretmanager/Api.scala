package com.boresjo.play.api.google.secretmanager

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://secretmanager.googleapis.com/"

	object projects {
		object locations {
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListLocationsResponse]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListLocationsResponse])
			}
			object list {
				def apply(projectsId :PlayApi, name: String, filter: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations").addQueryStringParameters("name" -> name.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListLocationsResponse]] = (fun: list) => fun.apply()
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Location]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Location])
			}
			object get {
				def apply(projectsId :PlayApi, locationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.Location]] = (fun: get) => fun.apply()
			}
			object secrets {
				class testIamPermissions(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withTestIamPermissionsRequest(body: Schema.TestIamPermissionsRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.TestIamPermissionsResponse])
				}
				object testIamPermissions {
					def apply(projectsId :PlayApi, locationsId :PlayApi, secretsId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): testIamPermissions = new testIamPermissions(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/secrets/${secretsId}:testIamPermissions").addQueryStringParameters("resource" -> resource.toString))
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					/** Optional. Etag of the Secret. The request succeeds if it matches the etag of the currently stored secret object. If the etag is omitted, the request succeeds. */
					def withEtag(etag: String) = new delete(req.addQueryStringParameters("etag" -> etag.toString))
					def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, secretsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/secrets/${secretsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Secret]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Secret])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, secretsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/secrets/${secretsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.Secret]] = (fun: get) => fun.apply()
				}
				class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withSecret(body: Schema.Secret) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Secret])
				}
				object patch {
					def apply(projectsId :PlayApi, locationsId :PlayApi, secretsId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/secrets/${secretsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListSecretsResponse]) {
					/** Optional. The maximum number of results to be returned in a single page. If set to 0, the server decides the number of results to return. If the number is greater than 25000, it is capped at 25000.<br>Format: int32 */
					def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
					/** Optional. Pagination token, returned earlier via ListSecretsResponse.next_page_token. */
					def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
					/** Optional. Filter string, adhering to the rules in [List-operation filtering](https://cloud.google.com/secret-manager/docs/filtering). List only secrets matching the filter. If filter is empty, all secrets are listed. */
					def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListSecretsResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/secrets").addQueryStringParameters("parent" -> parent.toString))
					given Conversion[list, Future[Schema.ListSecretsResponse]] = (fun: list) => fun.apply()
				}
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withSecret(body: Schema.Secret) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Secret])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, secretId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/secrets").addQueryStringParameters("parent" -> parent.toString, "secretId" -> secretId.toString))
				}
				class setIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withSetIamPolicyRequest(body: Schema.SetIamPolicyRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
				}
				object setIamPolicy {
					def apply(projectsId :PlayApi, locationsId :PlayApi, secretsId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): setIamPolicy = new setIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/secrets/${secretsId}:setIamPolicy").addQueryStringParameters("resource" -> resource.toString))
				}
				class addVersion(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withAddSecretVersionRequest(body: Schema.AddSecretVersionRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.SecretVersion])
				}
				object addVersion {
					def apply(projectsId :PlayApi, locationsId :PlayApi, secretsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): addVersion = new addVersion(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/secrets/${secretsId}:addVersion").addQueryStringParameters("parent" -> parent.toString))
				}
				class getIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Policy]) {
					/** Optional. The maximum policy version that will be used to format the policy. Valid values are 0, 1, and 3. Requests specifying an invalid value will be rejected. Requests for policies with any conditional role bindings must specify version 3. Policies with no conditional role bindings may specify any valid value or leave the field unset. The policy in the response might use the policy version that you specified, or it might use a lower policy version. For example, if you specify version 3, but the policy has no conditional role bindings, the response uses version 1. To learn which resources support conditions in their IAM policies, see the [IAM documentation](https://cloud.google.com/iam/help/conditions/resource-policies).<br>Format: int32 */
					def withOptionsRequestedPolicyVersion(optionsRequestedPolicyVersion: Int) = new getIamPolicy(req.addQueryStringParameters("options.requestedPolicyVersion" -> optionsRequestedPolicyVersion.toString))
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Policy])
				}
				object getIamPolicy {
					def apply(projectsId :PlayApi, locationsId :PlayApi, secretsId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): getIamPolicy = new getIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/secrets/${secretsId}:getIamPolicy").addQueryStringParameters("resource" -> resource.toString))
					given Conversion[getIamPolicy, Future[Schema.Policy]] = (fun: getIamPolicy) => fun.apply()
				}
				object versions {
					class enable(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withEnableSecretVersionRequest(body: Schema.EnableSecretVersionRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.SecretVersion])
					}
					object enable {
						def apply(projectsId :PlayApi, locationsId :PlayApi, secretsId :PlayApi, versionsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): enable = new enable(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/secrets/${secretsId}/versions/${versionsId}:enable").addQueryStringParameters("name" -> name.toString))
					}
					class destroy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withDestroySecretVersionRequest(body: Schema.DestroySecretVersionRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.SecretVersion])
					}
					object destroy {
						def apply(projectsId :PlayApi, locationsId :PlayApi, secretsId :PlayApi, versionsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): destroy = new destroy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/secrets/${secretsId}/versions/${versionsId}:destroy").addQueryStringParameters("name" -> name.toString))
					}
					class disable(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withDisableSecretVersionRequest(body: Schema.DisableSecretVersionRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.SecretVersion])
					}
					object disable {
						def apply(projectsId :PlayApi, locationsId :PlayApi, secretsId :PlayApi, versionsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): disable = new disable(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/secrets/${secretsId}/versions/${versionsId}:disable").addQueryStringParameters("name" -> name.toString))
					}
					class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.SecretVersion]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.SecretVersion])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, secretsId :PlayApi, versionsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/secrets/${secretsId}/versions/${versionsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.SecretVersion]] = (fun: get) => fun.apply()
					}
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListSecretVersionsResponse]) {
						/** Optional. The maximum number of results to be returned in a single page. If set to 0, the server decides the number of results to return. If the number is greater than 25000, it is capped at 25000.<br>Format: int32 */
						def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
						/** Optional. Pagination token, returned earlier via ListSecretVersionsResponse.next_page_token][]. */
						def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
						/** Optional. Filter string, adhering to the rules in [List-operation filtering](https://cloud.google.com/secret-manager/docs/filtering). List only secret versions matching the filter. If filter is empty, all secret versions are listed. */
						def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListSecretVersionsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, secretsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/secrets/${secretsId}/versions").addQueryStringParameters("parent" -> parent.toString))
						given Conversion[list, Future[Schema.ListSecretVersionsResponse]] = (fun: list) => fun.apply()
					}
					class access(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.AccessSecretVersionResponse]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.AccessSecretVersionResponse])
					}
					object access {
						def apply(projectsId :PlayApi, locationsId :PlayApi, secretsId :PlayApi, versionsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): access = new access(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/secrets/${secretsId}/versions/${versionsId}:access").addQueryStringParameters("name" -> name.toString))
						given Conversion[access, Future[Schema.AccessSecretVersionResponse]] = (fun: access) => fun.apply()
					}
				}
			}
		}
		object secrets {
			class testIamPermissions(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withTestIamPermissionsRequest(body: Schema.TestIamPermissionsRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.TestIamPermissionsResponse])
			}
			object testIamPermissions {
				def apply(projectsId :PlayApi, secretsId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): testIamPermissions = new testIamPermissions(ws.url(BASE_URL + s"v1/projects/${projectsId}/secrets/${secretsId}:testIamPermissions").addQueryStringParameters("resource" -> resource.toString))
			}
			class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
				/** Optional. Etag of the Secret. The request succeeds if it matches the etag of the currently stored secret object. If the etag is omitted, the request succeeds. */
				def withEtag(etag: String) = new delete(req.addQueryStringParameters("etag" -> etag.toString))
				def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
			}
			object delete {
				def apply(projectsId :PlayApi, secretsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/secrets/${secretsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Secret]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Secret])
			}
			object get {
				def apply(projectsId :PlayApi, secretsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/secrets/${secretsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.Secret]] = (fun: get) => fun.apply()
			}
			class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withSecret(body: Schema.Secret) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Secret])
			}
			object patch {
				def apply(projectsId :PlayApi, secretsId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/secrets/${secretsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListSecretsResponse]) {
				/** Optional. The maximum number of results to be returned in a single page. If set to 0, the server decides the number of results to return. If the number is greater than 25000, it is capped at 25000.<br>Format: int32 */
				def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
				/** Optional. Pagination token, returned earlier via ListSecretsResponse.next_page_token. */
				def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
				/** Optional. Filter string, adhering to the rules in [List-operation filtering](https://cloud.google.com/secret-manager/docs/filtering). List only secrets matching the filter. If filter is empty, all secrets are listed. */
				def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListSecretsResponse])
			}
			object list {
				def apply(projectsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/secrets").addQueryStringParameters("parent" -> parent.toString))
				given Conversion[list, Future[Schema.ListSecretsResponse]] = (fun: list) => fun.apply()
			}
			class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withSecret(body: Schema.Secret) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Secret])
			}
			object create {
				def apply(projectsId :PlayApi, parent: String, secretId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/secrets").addQueryStringParameters("parent" -> parent.toString, "secretId" -> secretId.toString))
			}
			class setIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withSetIamPolicyRequest(body: Schema.SetIamPolicyRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
			}
			object setIamPolicy {
				def apply(projectsId :PlayApi, secretsId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): setIamPolicy = new setIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/secrets/${secretsId}:setIamPolicy").addQueryStringParameters("resource" -> resource.toString))
			}
			class addVersion(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withAddSecretVersionRequest(body: Schema.AddSecretVersionRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.SecretVersion])
			}
			object addVersion {
				def apply(projectsId :PlayApi, secretsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): addVersion = new addVersion(ws.url(BASE_URL + s"v1/projects/${projectsId}/secrets/${secretsId}:addVersion").addQueryStringParameters("parent" -> parent.toString))
			}
			class getIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Policy]) {
				/** Optional. The maximum policy version that will be used to format the policy. Valid values are 0, 1, and 3. Requests specifying an invalid value will be rejected. Requests for policies with any conditional role bindings must specify version 3. Policies with no conditional role bindings may specify any valid value or leave the field unset. The policy in the response might use the policy version that you specified, or it might use a lower policy version. For example, if you specify version 3, but the policy has no conditional role bindings, the response uses version 1. To learn which resources support conditions in their IAM policies, see the [IAM documentation](https://cloud.google.com/iam/help/conditions/resource-policies).<br>Format: int32 */
				def withOptionsRequestedPolicyVersion(optionsRequestedPolicyVersion: Int) = new getIamPolicy(req.addQueryStringParameters("options.requestedPolicyVersion" -> optionsRequestedPolicyVersion.toString))
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Policy])
			}
			object getIamPolicy {
				def apply(projectsId :PlayApi, secretsId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): getIamPolicy = new getIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/secrets/${secretsId}:getIamPolicy").addQueryStringParameters("resource" -> resource.toString))
				given Conversion[getIamPolicy, Future[Schema.Policy]] = (fun: getIamPolicy) => fun.apply()
			}
			object versions {
				class enable(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withEnableSecretVersionRequest(body: Schema.EnableSecretVersionRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.SecretVersion])
				}
				object enable {
					def apply(projectsId :PlayApi, secretsId :PlayApi, versionsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): enable = new enable(ws.url(BASE_URL + s"v1/projects/${projectsId}/secrets/${secretsId}/versions/${versionsId}:enable").addQueryStringParameters("name" -> name.toString))
				}
				class destroy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withDestroySecretVersionRequest(body: Schema.DestroySecretVersionRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.SecretVersion])
				}
				object destroy {
					def apply(projectsId :PlayApi, secretsId :PlayApi, versionsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): destroy = new destroy(ws.url(BASE_URL + s"v1/projects/${projectsId}/secrets/${secretsId}/versions/${versionsId}:destroy").addQueryStringParameters("name" -> name.toString))
				}
				class disable(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withDisableSecretVersionRequest(body: Schema.DisableSecretVersionRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.SecretVersion])
				}
				object disable {
					def apply(projectsId :PlayApi, secretsId :PlayApi, versionsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): disable = new disable(ws.url(BASE_URL + s"v1/projects/${projectsId}/secrets/${secretsId}/versions/${versionsId}:disable").addQueryStringParameters("name" -> name.toString))
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.SecretVersion]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.SecretVersion])
				}
				object get {
					def apply(projectsId :PlayApi, secretsId :PlayApi, versionsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/secrets/${secretsId}/versions/${versionsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.SecretVersion]] = (fun: get) => fun.apply()
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListSecretVersionsResponse]) {
					/** Optional. The maximum number of results to be returned in a single page. If set to 0, the server decides the number of results to return. If the number is greater than 25000, it is capped at 25000.<br>Format: int32 */
					def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
					/** Optional. Pagination token, returned earlier via ListSecretVersionsResponse.next_page_token][]. */
					def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
					/** Optional. Filter string, adhering to the rules in [List-operation filtering](https://cloud.google.com/secret-manager/docs/filtering). List only secret versions matching the filter. If filter is empty, all secret versions are listed. */
					def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListSecretVersionsResponse])
				}
				object list {
					def apply(projectsId :PlayApi, secretsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/secrets/${secretsId}/versions").addQueryStringParameters("parent" -> parent.toString))
					given Conversion[list, Future[Schema.ListSecretVersionsResponse]] = (fun: list) => fun.apply()
				}
				class access(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.AccessSecretVersionResponse]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.AccessSecretVersionResponse])
				}
				object access {
					def apply(projectsId :PlayApi, secretsId :PlayApi, versionsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): access = new access(ws.url(BASE_URL + s"v1/projects/${projectsId}/secrets/${secretsId}/versions/${versionsId}:access").addQueryStringParameters("name" -> name.toString))
					given Conversion[access, Future[Schema.AccessSecretVersionResponse]] = (fun: access) => fun.apply()
				}
			}
		}
	}
}
