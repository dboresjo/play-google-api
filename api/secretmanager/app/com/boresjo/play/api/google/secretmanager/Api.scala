package com.boresjo.play.api.google.secretmanager

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

	private val BASE_URL = "https://secretmanager.googleapis.com/"

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
			object secrets {
				/** Returns permissions that a caller has for the specified secret. If the secret does not exist, this call returns an empty set of permissions, not a NOT_FOUND error. Note: This operation is designed to be used for building permission-aware UIs and command-line tools, not for authorization checking. This operation may "fail open" without warning. */
				class testIamPermissions(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withTestIamPermissionsRequest(body: Schema.TestIamPermissionsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.TestIamPermissionsResponse])
				}
				object testIamPermissions {
					def apply(projectsId :PlayApi, locationsId :PlayApi, secretsId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): testIamPermissions = new testIamPermissions(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/secrets/${secretsId}:testIamPermissions").addQueryStringParameters("resource" -> resource.toString))
				}
				/** Deletes a Secret. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Optional. Etag of the Secret. The request succeeds if it matches the etag of the currently stored secret object. If the etag is omitted, the request succeeds. */
					def withEtag(etag: String) = new delete(req.addQueryStringParameters("etag" -> etag.toString))
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, secretsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/secrets/${secretsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
				}
				/** Gets metadata for a given Secret. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Secret]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Secret])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, secretsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/secrets/${secretsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.Secret]] = (fun: get) => fun.apply()
				}
				/** Updates metadata of an existing Secret. */
				class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withSecret(body: Schema.Secret) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Secret])
				}
				object patch {
					def apply(projectsId :PlayApi, locationsId :PlayApi, secretsId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/secrets/${secretsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
				}
				/** Lists Secrets. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListSecretsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Optional. The maximum number of results to be returned in a single page. If set to 0, the server decides the number of results to return. If the number is greater than 25000, it is capped at 25000.<br>Format: int32 */
					def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
					/** Optional. Pagination token, returned earlier via ListSecretsResponse.next_page_token. */
					def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
					/** Optional. Filter string, adhering to the rules in [List-operation filtering](https://cloud.google.com/secret-manager/docs/filtering). List only secrets matching the filter. If filter is empty, all secrets are listed. */
					def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListSecretsResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/secrets").addQueryStringParameters("parent" -> parent.toString))
					given Conversion[list, Future[Schema.ListSecretsResponse]] = (fun: list) => fun.apply()
				}
				/** Creates a new Secret containing no SecretVersions. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withSecret(body: Schema.Secret) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Secret])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, secretId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/secrets").addQueryStringParameters("parent" -> parent.toString, "secretId" -> secretId.toString))
				}
				/** Sets the access control policy on the specified secret. Replaces any existing policy. Permissions on SecretVersions are enforced according to the policy set on the associated Secret. */
				class setIamPolicy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withSetIamPolicyRequest(body: Schema.SetIamPolicyRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
				}
				object setIamPolicy {
					def apply(projectsId :PlayApi, locationsId :PlayApi, secretsId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): setIamPolicy = new setIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/secrets/${secretsId}:setIamPolicy").addQueryStringParameters("resource" -> resource.toString))
				}
				/** Creates a new SecretVersion containing secret data and attaches it to an existing Secret. */
				class addVersion(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withAddSecretVersionRequest(body: Schema.AddSecretVersionRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.SecretVersion])
				}
				object addVersion {
					def apply(projectsId :PlayApi, locationsId :PlayApi, secretsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): addVersion = new addVersion(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/secrets/${secretsId}:addVersion").addQueryStringParameters("parent" -> parent.toString))
				}
				/** Gets the access control policy for a secret. Returns empty policy if the secret exists and does not have a policy set. */
				class getIamPolicy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Policy]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Optional. The maximum policy version that will be used to format the policy. Valid values are 0, 1, and 3. Requests specifying an invalid value will be rejected. Requests for policies with any conditional role bindings must specify version 3. Policies with no conditional role bindings may specify any valid value or leave the field unset. The policy in the response might use the policy version that you specified, or it might use a lower policy version. For example, if you specify version 3, but the policy has no conditional role bindings, the response uses version 1. To learn which resources support conditions in their IAM policies, see the [IAM documentation](https://cloud.google.com/iam/help/conditions/resource-policies).<br>Format: int32 */
					def withOptionsRequestedPolicyVersion(optionsRequestedPolicyVersion: Int) = new getIamPolicy(req.addQueryStringParameters("options.requestedPolicyVersion" -> optionsRequestedPolicyVersion.toString))
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Policy])
				}
				object getIamPolicy {
					def apply(projectsId :PlayApi, locationsId :PlayApi, secretsId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): getIamPolicy = new getIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/secrets/${secretsId}:getIamPolicy").addQueryStringParameters("resource" -> resource.toString))
					given Conversion[getIamPolicy, Future[Schema.Policy]] = (fun: getIamPolicy) => fun.apply()
				}
				object versions {
					/** Enables a SecretVersion. Sets the state of the SecretVersion to ENABLED. */
					class enable(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withEnableSecretVersionRequest(body: Schema.EnableSecretVersionRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.SecretVersion])
					}
					object enable {
						def apply(projectsId :PlayApi, locationsId :PlayApi, secretsId :PlayApi, versionsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): enable = new enable(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/secrets/${secretsId}/versions/${versionsId}:enable").addQueryStringParameters("name" -> name.toString))
					}
					/** Destroys a SecretVersion. Sets the state of the SecretVersion to DESTROYED and irrevocably destroys the secret data. */
					class destroy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withDestroySecretVersionRequest(body: Schema.DestroySecretVersionRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.SecretVersion])
					}
					object destroy {
						def apply(projectsId :PlayApi, locationsId :PlayApi, secretsId :PlayApi, versionsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): destroy = new destroy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/secrets/${secretsId}/versions/${versionsId}:destroy").addQueryStringParameters("name" -> name.toString))
					}
					/** Disables a SecretVersion. Sets the state of the SecretVersion to DISABLED. */
					class disable(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withDisableSecretVersionRequest(body: Schema.DisableSecretVersionRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.SecretVersion])
					}
					object disable {
						def apply(projectsId :PlayApi, locationsId :PlayApi, secretsId :PlayApi, versionsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): disable = new disable(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/secrets/${secretsId}/versions/${versionsId}:disable").addQueryStringParameters("name" -> name.toString))
					}
					/** Gets metadata for a SecretVersion. `projects/&#42;/secrets/&#42;/versions/latest` is an alias to the most recently created SecretVersion. */
					class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.SecretVersion]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.SecretVersion])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, secretsId :PlayApi, versionsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/secrets/${secretsId}/versions/${versionsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.SecretVersion]] = (fun: get) => fun.apply()
					}
					/** Lists SecretVersions. This call does not return secret data. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListSecretVersionsResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Optional. The maximum number of results to be returned in a single page. If set to 0, the server decides the number of results to return. If the number is greater than 25000, it is capped at 25000.<br>Format: int32 */
						def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
						/** Optional. Pagination token, returned earlier via ListSecretVersionsResponse.next_page_token][]. */
						def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
						/** Optional. Filter string, adhering to the rules in [List-operation filtering](https://cloud.google.com/secret-manager/docs/filtering). List only secret versions matching the filter. If filter is empty, all secret versions are listed. */
						def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListSecretVersionsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, secretsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/secrets/${secretsId}/versions").addQueryStringParameters("parent" -> parent.toString))
						given Conversion[list, Future[Schema.ListSecretVersionsResponse]] = (fun: list) => fun.apply()
					}
					/** Accesses a SecretVersion. This call returns the secret data. `projects/&#42;/secrets/&#42;/versions/latest` is an alias to the most recently created SecretVersion. */
					class access(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.AccessSecretVersionResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.AccessSecretVersionResponse])
					}
					object access {
						def apply(projectsId :PlayApi, locationsId :PlayApi, secretsId :PlayApi, versionsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): access = new access(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/secrets/${secretsId}/versions/${versionsId}:access").addQueryStringParameters("name" -> name.toString))
						given Conversion[access, Future[Schema.AccessSecretVersionResponse]] = (fun: access) => fun.apply()
					}
				}
			}
		}
		object secrets {
			/** Returns permissions that a caller has for the specified secret. If the secret does not exist, this call returns an empty set of permissions, not a NOT_FOUND error. Note: This operation is designed to be used for building permission-aware UIs and command-line tools, not for authorization checking. This operation may "fail open" without warning. */
			class testIamPermissions(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withTestIamPermissionsRequest(body: Schema.TestIamPermissionsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.TestIamPermissionsResponse])
			}
			object testIamPermissions {
				def apply(projectsId :PlayApi, secretsId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): testIamPermissions = new testIamPermissions(ws.url(BASE_URL + s"v1/projects/${projectsId}/secrets/${secretsId}:testIamPermissions").addQueryStringParameters("resource" -> resource.toString))
			}
			/** Deletes a Secret. */
			class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Optional. Etag of the Secret. The request succeeds if it matches the etag of the currently stored secret object. If the etag is omitted, the request succeeds. */
				def withEtag(etag: String) = new delete(req.addQueryStringParameters("etag" -> etag.toString))
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
			}
			object delete {
				def apply(projectsId :PlayApi, secretsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/secrets/${secretsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
			}
			/** Gets metadata for a given Secret. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Secret]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Secret])
			}
			object get {
				def apply(projectsId :PlayApi, secretsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/secrets/${secretsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.Secret]] = (fun: get) => fun.apply()
			}
			/** Updates metadata of an existing Secret. */
			class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withSecret(body: Schema.Secret) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Secret])
			}
			object patch {
				def apply(projectsId :PlayApi, secretsId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/secrets/${secretsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
			}
			/** Lists Secrets. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListSecretsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Optional. The maximum number of results to be returned in a single page. If set to 0, the server decides the number of results to return. If the number is greater than 25000, it is capped at 25000.<br>Format: int32 */
				def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
				/** Optional. Pagination token, returned earlier via ListSecretsResponse.next_page_token. */
				def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
				/** Optional. Filter string, adhering to the rules in [List-operation filtering](https://cloud.google.com/secret-manager/docs/filtering). List only secrets matching the filter. If filter is empty, all secrets are listed. */
				def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListSecretsResponse])
			}
			object list {
				def apply(projectsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/secrets").addQueryStringParameters("parent" -> parent.toString))
				given Conversion[list, Future[Schema.ListSecretsResponse]] = (fun: list) => fun.apply()
			}
			/** Creates a new Secret containing no SecretVersions. */
			class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withSecret(body: Schema.Secret) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Secret])
			}
			object create {
				def apply(projectsId :PlayApi, parent: String, secretId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/secrets").addQueryStringParameters("parent" -> parent.toString, "secretId" -> secretId.toString))
			}
			/** Sets the access control policy on the specified secret. Replaces any existing policy. Permissions on SecretVersions are enforced according to the policy set on the associated Secret. */
			class setIamPolicy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withSetIamPolicyRequest(body: Schema.SetIamPolicyRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
			}
			object setIamPolicy {
				def apply(projectsId :PlayApi, secretsId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): setIamPolicy = new setIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/secrets/${secretsId}:setIamPolicy").addQueryStringParameters("resource" -> resource.toString))
			}
			/** Creates a new SecretVersion containing secret data and attaches it to an existing Secret. */
			class addVersion(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withAddSecretVersionRequest(body: Schema.AddSecretVersionRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.SecretVersion])
			}
			object addVersion {
				def apply(projectsId :PlayApi, secretsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): addVersion = new addVersion(ws.url(BASE_URL + s"v1/projects/${projectsId}/secrets/${secretsId}:addVersion").addQueryStringParameters("parent" -> parent.toString))
			}
			/** Gets the access control policy for a secret. Returns empty policy if the secret exists and does not have a policy set. */
			class getIamPolicy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Policy]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Optional. The maximum policy version that will be used to format the policy. Valid values are 0, 1, and 3. Requests specifying an invalid value will be rejected. Requests for policies with any conditional role bindings must specify version 3. Policies with no conditional role bindings may specify any valid value or leave the field unset. The policy in the response might use the policy version that you specified, or it might use a lower policy version. For example, if you specify version 3, but the policy has no conditional role bindings, the response uses version 1. To learn which resources support conditions in their IAM policies, see the [IAM documentation](https://cloud.google.com/iam/help/conditions/resource-policies).<br>Format: int32 */
				def withOptionsRequestedPolicyVersion(optionsRequestedPolicyVersion: Int) = new getIamPolicy(req.addQueryStringParameters("options.requestedPolicyVersion" -> optionsRequestedPolicyVersion.toString))
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Policy])
			}
			object getIamPolicy {
				def apply(projectsId :PlayApi, secretsId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): getIamPolicy = new getIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/secrets/${secretsId}:getIamPolicy").addQueryStringParameters("resource" -> resource.toString))
				given Conversion[getIamPolicy, Future[Schema.Policy]] = (fun: getIamPolicy) => fun.apply()
			}
			object versions {
				/** Enables a SecretVersion. Sets the state of the SecretVersion to ENABLED. */
				class enable(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withEnableSecretVersionRequest(body: Schema.EnableSecretVersionRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.SecretVersion])
				}
				object enable {
					def apply(projectsId :PlayApi, secretsId :PlayApi, versionsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): enable = new enable(ws.url(BASE_URL + s"v1/projects/${projectsId}/secrets/${secretsId}/versions/${versionsId}:enable").addQueryStringParameters("name" -> name.toString))
				}
				/** Destroys a SecretVersion. Sets the state of the SecretVersion to DESTROYED and irrevocably destroys the secret data. */
				class destroy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withDestroySecretVersionRequest(body: Schema.DestroySecretVersionRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.SecretVersion])
				}
				object destroy {
					def apply(projectsId :PlayApi, secretsId :PlayApi, versionsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): destroy = new destroy(ws.url(BASE_URL + s"v1/projects/${projectsId}/secrets/${secretsId}/versions/${versionsId}:destroy").addQueryStringParameters("name" -> name.toString))
				}
				/** Disables a SecretVersion. Sets the state of the SecretVersion to DISABLED. */
				class disable(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withDisableSecretVersionRequest(body: Schema.DisableSecretVersionRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.SecretVersion])
				}
				object disable {
					def apply(projectsId :PlayApi, secretsId :PlayApi, versionsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): disable = new disable(ws.url(BASE_URL + s"v1/projects/${projectsId}/secrets/${secretsId}/versions/${versionsId}:disable").addQueryStringParameters("name" -> name.toString))
				}
				/** Gets metadata for a SecretVersion. `projects/&#42;/secrets/&#42;/versions/latest` is an alias to the most recently created SecretVersion. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.SecretVersion]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.SecretVersion])
				}
				object get {
					def apply(projectsId :PlayApi, secretsId :PlayApi, versionsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/secrets/${secretsId}/versions/${versionsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.SecretVersion]] = (fun: get) => fun.apply()
				}
				/** Lists SecretVersions. This call does not return secret data. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListSecretVersionsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Optional. The maximum number of results to be returned in a single page. If set to 0, the server decides the number of results to return. If the number is greater than 25000, it is capped at 25000.<br>Format: int32 */
					def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
					/** Optional. Pagination token, returned earlier via ListSecretVersionsResponse.next_page_token][]. */
					def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
					/** Optional. Filter string, adhering to the rules in [List-operation filtering](https://cloud.google.com/secret-manager/docs/filtering). List only secret versions matching the filter. If filter is empty, all secret versions are listed. */
					def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListSecretVersionsResponse])
				}
				object list {
					def apply(projectsId :PlayApi, secretsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/secrets/${secretsId}/versions").addQueryStringParameters("parent" -> parent.toString))
					given Conversion[list, Future[Schema.ListSecretVersionsResponse]] = (fun: list) => fun.apply()
				}
				/** Accesses a SecretVersion. This call returns the secret data. `projects/&#42;/secrets/&#42;/versions/latest` is an alias to the most recently created SecretVersion. */
				class access(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.AccessSecretVersionResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.AccessSecretVersionResponse])
				}
				object access {
					def apply(projectsId :PlayApi, secretsId :PlayApi, versionsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): access = new access(ws.url(BASE_URL + s"v1/projects/${projectsId}/secrets/${secretsId}/versions/${versionsId}:access").addQueryStringParameters("name" -> name.toString))
					given Conversion[access, Future[Schema.AccessSecretVersionResponse]] = (fun: access) => fun.apply()
				}
			}
		}
	}
}
