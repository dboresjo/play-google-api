package com.boresjo.play.api.google.cloudkms

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
		"""https://www.googleapis.com/auth/cloud-platform""" /* See, edit, configure, and delete your Google Cloud data and see the email address for your Google Account. */,
		"""https://www.googleapis.com/auth/cloudkms""" /* View and manage your keys and secrets stored in Cloud Key Management Service */
	)

	private val BASE_URL = "https://cloudkms.googleapis.com/"

	object folders {
		/** Updates the AutokeyConfig for a folder. The caller must have both `cloudkms.autokeyConfigs.update` permission on the parent folder and `cloudkms.cryptoKeys.setIamPolicy` permission on the provided key project. A KeyHandle creation in the folder's descendant projects will use this configuration to determine where to create the resulting CryptoKey. */
		class updateAutokeyConfig(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloudkms""")
			/** Perform the request */
			def withAutokeyConfig(body: Schema.AutokeyConfig) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.AutokeyConfig])
		}
		object updateAutokeyConfig {
			def apply(foldersId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): updateAutokeyConfig = new updateAutokeyConfig(ws.url(BASE_URL + s"v1/folders/${foldersId}/autokeyConfig").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
		}
		/** Returns the AutokeyConfig for a folder. */
		class getAutokeyConfig(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.AutokeyConfig]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloudkms""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.AutokeyConfig])
		}
		object getAutokeyConfig {
			def apply(foldersId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): getAutokeyConfig = new getAutokeyConfig(ws.url(BASE_URL + s"v1/folders/${foldersId}/autokeyConfig").addQueryStringParameters("name" -> name.toString))
			given Conversion[getAutokeyConfig, Future[Schema.AutokeyConfig]] = (fun: getAutokeyConfig) => fun.apply()
		}
	}
	object projects {
		/** Returns the effective Cloud KMS Autokey configuration for a given project. */
		class showEffectiveAutokeyConfig(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ShowEffectiveAutokeyConfigResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloudkms""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ShowEffectiveAutokeyConfigResponse])
		}
		object showEffectiveAutokeyConfig {
			def apply(projectsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): showEffectiveAutokeyConfig = new showEffectiveAutokeyConfig(ws.url(BASE_URL + s"v1/projects/${projectsId}:showEffectiveAutokeyConfig").addQueryStringParameters("parent" -> parent.toString))
			given Conversion[showEffectiveAutokeyConfig, Future[Schema.ShowEffectiveAutokeyConfigResponse]] = (fun: showEffectiveAutokeyConfig) => fun.apply()
		}
		object locations {
			/** Updates the EkmConfig singleton resource for a given project and location. */
			class updateEkmConfig(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloudkms""")
				/** Perform the request */
				def withEkmConfig(body: Schema.EkmConfig) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.EkmConfig])
			}
			object updateEkmConfig {
				def apply(projectsId :PlayApi, locationsId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): updateEkmConfig = new updateEkmConfig(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/ekmConfig").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
			}
			/** Generate random bytes using the Cloud KMS randomness source in the provided location. */
			class generateRandomBytes(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloudkms""")
				/** Perform the request */
				def withGenerateRandomBytesRequest(body: Schema.GenerateRandomBytesRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GenerateRandomBytesResponse])
			}
			object generateRandomBytes {
				def apply(projectsId :PlayApi, locationsId :PlayApi, location: String)(using signer: RequestSigner, ec: ExecutionContext): generateRandomBytes = new generateRandomBytes(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}:generateRandomBytes").addQueryStringParameters("location" -> location.toString))
			}
			/** Returns the EkmConfig singleton resource for a given project and location. */
			class getEkmConfig(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.EkmConfig]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloudkms""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.EkmConfig])
			}
			object getEkmConfig {
				def apply(projectsId :PlayApi, locationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): getEkmConfig = new getEkmConfig(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/ekmConfig").addQueryStringParameters("name" -> name.toString))
				given Conversion[getEkmConfig, Future[Schema.EkmConfig]] = (fun: getEkmConfig) => fun.apply()
			}
			/** Gets information about a location. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Location]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloudkms""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Location])
			}
			object get {
				def apply(projectsId :PlayApi, locationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.Location]] = (fun: get) => fun.apply()
			}
			/** Lists information about the supported locations for this service. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListLocationsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloudkms""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListLocationsResponse])
			}
			object list {
				def apply(projectsId :PlayApi, name: String, filter: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations").addQueryStringParameters("name" -> name.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListLocationsResponse]] = (fun: list) => fun.apply()
			}
			object keyRings {
				/** Returns permissions that a caller has on the specified resource. If the resource does not exist, this will return an empty set of permissions, not a `NOT_FOUND` error. Note: This operation is designed to be used for building permission-aware UIs and command-line tools, not for authorization checking. This operation may "fail open" without warning. */
				class testIamPermissions(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloudkms""")
					/** Perform the request */
					def withTestIamPermissionsRequest(body: Schema.TestIamPermissionsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.TestIamPermissionsResponse])
				}
				object testIamPermissions {
					def apply(projectsId :PlayApi, locationsId :PlayApi, keyRingsId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): testIamPermissions = new testIamPermissions(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/keyRings/${keyRingsId}:testIamPermissions").addQueryStringParameters("resource" -> resource.toString))
				}
				/** Gets the access control policy for a resource. Returns an empty policy if the resource exists and does not have a policy set. */
				class getIamPolicy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Policy]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloudkms""")
					/** Optional. The maximum policy version that will be used to format the policy. Valid values are 0, 1, and 3. Requests specifying an invalid value will be rejected. Requests for policies with any conditional role bindings must specify version 3. Policies with no conditional role bindings may specify any valid value or leave the field unset. The policy in the response might use the policy version that you specified, or it might use a lower policy version. For example, if you specify version 3, but the policy has no conditional role bindings, the response uses version 1. To learn which resources support conditions in their IAM policies, see the [IAM documentation](https://cloud.google.com/iam/help/conditions/resource-policies).<br>Format: int32 */
					def withOptionsRequestedPolicyVersion(optionsRequestedPolicyVersion: Int) = new getIamPolicy(req.addQueryStringParameters("options.requestedPolicyVersion" -> optionsRequestedPolicyVersion.toString))
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Policy])
				}
				object getIamPolicy {
					def apply(projectsId :PlayApi, locationsId :PlayApi, keyRingsId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): getIamPolicy = new getIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/keyRings/${keyRingsId}:getIamPolicy").addQueryStringParameters("resource" -> resource.toString))
					given Conversion[getIamPolicy, Future[Schema.Policy]] = (fun: getIamPolicy) => fun.apply()
				}
				/** Returns metadata for a given KeyRing. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.KeyRing]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloudkms""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.KeyRing])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, keyRingsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/keyRings/${keyRingsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.KeyRing]] = (fun: get) => fun.apply()
				}
				/** Lists KeyRings. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListKeyRingsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloudkms""")
					/** Optional. Optional limit on the number of KeyRings to include in the response. Further KeyRings can subsequently be obtained by including the ListKeyRingsResponse.next_page_token in a subsequent request. If unspecified, the server will pick an appropriate default.<br>Format: int32 */
					def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
					/** Optional. Optional pagination token, returned earlier via ListKeyRingsResponse.next_page_token. */
					def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
					/** Optional. Only include resources that match the filter in the response. For more information, see [Sorting and filtering list results](https://cloud.google.com/kms/docs/sorting-and-filtering). */
					def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
					/** Optional. Specify how the results should be sorted. If not specified, the results will be sorted in the default order. For more information, see [Sorting and filtering list results](https://cloud.google.com/kms/docs/sorting-and-filtering). */
					def withOrderBy(orderBy: String) = new list(req.addQueryStringParameters("orderBy" -> orderBy.toString))
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListKeyRingsResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/keyRings").addQueryStringParameters("parent" -> parent.toString))
					given Conversion[list, Future[Schema.ListKeyRingsResponse]] = (fun: list) => fun.apply()
				}
				/** Create a new KeyRing in a given Project and Location. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloudkms""")
					/** Perform the request */
					def withKeyRing(body: Schema.KeyRing) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.KeyRing])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, keyRingId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/keyRings").addQueryStringParameters("parent" -> parent.toString, "keyRingId" -> keyRingId.toString))
				}
				/** Sets the access control policy on the specified resource. Replaces any existing policy. Can return `NOT_FOUND`, `INVALID_ARGUMENT`, and `PERMISSION_DENIED` errors. */
				class setIamPolicy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloudkms""")
					/** Perform the request */
					def withSetIamPolicyRequest(body: Schema.SetIamPolicyRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
				}
				object setIamPolicy {
					def apply(projectsId :PlayApi, locationsId :PlayApi, keyRingsId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): setIamPolicy = new setIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/keyRings/${keyRingsId}:setIamPolicy").addQueryStringParameters("resource" -> resource.toString))
				}
				object cryptoKeys {
					/** Returns permissions that a caller has on the specified resource. If the resource does not exist, this will return an empty set of permissions, not a `NOT_FOUND` error. Note: This operation is designed to be used for building permission-aware UIs and command-line tools, not for authorization checking. This operation may "fail open" without warning. */
					class testIamPermissions(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloudkms""")
						/** Perform the request */
						def withTestIamPermissionsRequest(body: Schema.TestIamPermissionsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.TestIamPermissionsResponse])
					}
					object testIamPermissions {
						def apply(projectsId :PlayApi, locationsId :PlayApi, keyRingsId :PlayApi, cryptoKeysId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): testIamPermissions = new testIamPermissions(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/keyRings/${keyRingsId}/cryptoKeys/${cryptoKeysId}:testIamPermissions").addQueryStringParameters("resource" -> resource.toString))
					}
					/** Encrypts data, so that it can only be recovered by a call to Decrypt. The CryptoKey.purpose must be ENCRYPT_DECRYPT. */
					class encrypt(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloudkms""")
						/** Perform the request */
						def withEncryptRequest(body: Schema.EncryptRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.EncryptResponse])
					}
					object encrypt {
						def apply(projectsId :PlayApi, locationsId :PlayApi, keyRingsId :PlayApi, cryptoKeysId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): encrypt = new encrypt(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/keyRings/${keyRingsId}/cryptoKeys/${cryptoKeysId}:encrypt").addQueryStringParameters("name" -> name.toString))
					}
					/** Decrypts data that was protected by Encrypt. The CryptoKey.purpose must be ENCRYPT_DECRYPT. */
					class decrypt(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloudkms""")
						/** Perform the request */
						def withDecryptRequest(body: Schema.DecryptRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.DecryptResponse])
					}
					object decrypt {
						def apply(projectsId :PlayApi, locationsId :PlayApi, keyRingsId :PlayApi, cryptoKeysId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): decrypt = new decrypt(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/keyRings/${keyRingsId}/cryptoKeys/${cryptoKeysId}:decrypt").addQueryStringParameters("name" -> name.toString))
					}
					/** Gets the access control policy for a resource. Returns an empty policy if the resource exists and does not have a policy set. */
					class getIamPolicy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Policy]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloudkms""")
						/** Optional. The maximum policy version that will be used to format the policy. Valid values are 0, 1, and 3. Requests specifying an invalid value will be rejected. Requests for policies with any conditional role bindings must specify version 3. Policies with no conditional role bindings may specify any valid value or leave the field unset. The policy in the response might use the policy version that you specified, or it might use a lower policy version. For example, if you specify version 3, but the policy has no conditional role bindings, the response uses version 1. To learn which resources support conditions in their IAM policies, see the [IAM documentation](https://cloud.google.com/iam/help/conditions/resource-policies).<br>Format: int32 */
						def withOptionsRequestedPolicyVersion(optionsRequestedPolicyVersion: Int) = new getIamPolicy(req.addQueryStringParameters("options.requestedPolicyVersion" -> optionsRequestedPolicyVersion.toString))
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Policy])
					}
					object getIamPolicy {
						def apply(projectsId :PlayApi, locationsId :PlayApi, keyRingsId :PlayApi, cryptoKeysId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): getIamPolicy = new getIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/keyRings/${keyRingsId}/cryptoKeys/${cryptoKeysId}:getIamPolicy").addQueryStringParameters("resource" -> resource.toString))
						given Conversion[getIamPolicy, Future[Schema.Policy]] = (fun: getIamPolicy) => fun.apply()
					}
					/** Returns metadata for a given CryptoKey, as well as its primary CryptoKeyVersion. */
					class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.CryptoKey]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloudkms""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.CryptoKey])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, keyRingsId :PlayApi, cryptoKeysId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/keyRings/${keyRingsId}/cryptoKeys/${cryptoKeysId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.CryptoKey]] = (fun: get) => fun.apply()
					}
					/** Update a CryptoKey. */
					class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloudkms""")
						/** Perform the request */
						def withCryptoKey(body: Schema.CryptoKey) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.CryptoKey])
					}
					object patch {
						def apply(projectsId :PlayApi, locationsId :PlayApi, keyRingsId :PlayApi, cryptoKeysId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/keyRings/${keyRingsId}/cryptoKeys/${cryptoKeysId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
					}
					/** Lists CryptoKeys. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListCryptoKeysResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloudkms""")
						/** Optional. Optional limit on the number of CryptoKeys to include in the response. Further CryptoKeys can subsequently be obtained by including the ListCryptoKeysResponse.next_page_token in a subsequent request. If unspecified, the server will pick an appropriate default.<br>Format: int32 */
						def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
						/** Optional. Optional pagination token, returned earlier via ListCryptoKeysResponse.next_page_token. */
						def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
						/** Optional. Only include resources that match the filter in the response. For more information, see [Sorting and filtering list results](https://cloud.google.com/kms/docs/sorting-and-filtering). */
						def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
						/** Optional. Specify how the results should be sorted. If not specified, the results will be sorted in the default order. For more information, see [Sorting and filtering list results](https://cloud.google.com/kms/docs/sorting-and-filtering). */
						def withOrderBy(orderBy: String) = new list(req.addQueryStringParameters("orderBy" -> orderBy.toString))
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListCryptoKeysResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, keyRingsId :PlayApi, parent: String, versionView: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/keyRings/${keyRingsId}/cryptoKeys").addQueryStringParameters("parent" -> parent.toString, "versionView" -> versionView.toString))
						given Conversion[list, Future[Schema.ListCryptoKeysResponse]] = (fun: list) => fun.apply()
					}
					/** Create a new CryptoKey within a KeyRing. CryptoKey.purpose and CryptoKey.version_template.algorithm are required. */
					class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloudkms""")
						/** Perform the request */
						def withCryptoKey(body: Schema.CryptoKey) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.CryptoKey])
					}
					object create {
						def apply(projectsId :PlayApi, locationsId :PlayApi, keyRingsId :PlayApi, parent: String, cryptoKeyId: String, skipInitialVersionCreation: Boolean)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/keyRings/${keyRingsId}/cryptoKeys").addQueryStringParameters("parent" -> parent.toString, "cryptoKeyId" -> cryptoKeyId.toString, "skipInitialVersionCreation" -> skipInitialVersionCreation.toString))
					}
					/** Sets the access control policy on the specified resource. Replaces any existing policy. Can return `NOT_FOUND`, `INVALID_ARGUMENT`, and `PERMISSION_DENIED` errors. */
					class setIamPolicy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloudkms""")
						/** Perform the request */
						def withSetIamPolicyRequest(body: Schema.SetIamPolicyRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
					}
					object setIamPolicy {
						def apply(projectsId :PlayApi, locationsId :PlayApi, keyRingsId :PlayApi, cryptoKeysId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): setIamPolicy = new setIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/keyRings/${keyRingsId}/cryptoKeys/${cryptoKeysId}:setIamPolicy").addQueryStringParameters("resource" -> resource.toString))
					}
					/** Update the version of a CryptoKey that will be used in Encrypt. Returns an error if called on a key whose purpose is not ENCRYPT_DECRYPT. */
					class updatePrimaryVersion(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloudkms""")
						/** Perform the request */
						def withUpdateCryptoKeyPrimaryVersionRequest(body: Schema.UpdateCryptoKeyPrimaryVersionRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.CryptoKey])
					}
					object updatePrimaryVersion {
						def apply(projectsId :PlayApi, locationsId :PlayApi, keyRingsId :PlayApi, cryptoKeysId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): updatePrimaryVersion = new updatePrimaryVersion(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/keyRings/${keyRingsId}/cryptoKeys/${cryptoKeysId}:updatePrimaryVersion").addQueryStringParameters("name" -> name.toString))
					}
					object cryptoKeyVersions {
						/** Returns the public key for the given CryptoKeyVersion. The CryptoKey.purpose must be ASYMMETRIC_SIGN or ASYMMETRIC_DECRYPT. */
						class getPublicKey(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.PublicKey]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloudkms""")
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.PublicKey])
						}
						object getPublicKey {
							def apply(projectsId :PlayApi, locationsId :PlayApi, keyRingsId :PlayApi, cryptoKeysId :PlayApi, cryptoKeyVersionsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): getPublicKey = new getPublicKey(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/keyRings/${keyRingsId}/cryptoKeys/${cryptoKeysId}/cryptoKeyVersions/${cryptoKeyVersionsId}/publicKey").addQueryStringParameters("name" -> name.toString))
							given Conversion[getPublicKey, Future[Schema.PublicKey]] = (fun: getPublicKey) => fun.apply()
						}
						/** Signs data using a CryptoKeyVersion with CryptoKey.purpose ASYMMETRIC_SIGN, producing a signature that can be verified with the public key retrieved from GetPublicKey. */
						class asymmetricSign(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloudkms""")
							/** Perform the request */
							def withAsymmetricSignRequest(body: Schema.AsymmetricSignRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.AsymmetricSignResponse])
						}
						object asymmetricSign {
							def apply(projectsId :PlayApi, locationsId :PlayApi, keyRingsId :PlayApi, cryptoKeysId :PlayApi, cryptoKeyVersionsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): asymmetricSign = new asymmetricSign(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/keyRings/${keyRingsId}/cryptoKeys/${cryptoKeysId}/cryptoKeyVersions/${cryptoKeyVersionsId}:asymmetricSign").addQueryStringParameters("name" -> name.toString))
						}
						/** Restore a CryptoKeyVersion in the DESTROY_SCHEDULED state. Upon restoration of the CryptoKeyVersion, state will be set to DISABLED, and destroy_time will be cleared. */
						class restore(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloudkms""")
							/** Perform the request */
							def withRestoreCryptoKeyVersionRequest(body: Schema.RestoreCryptoKeyVersionRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.CryptoKeyVersion])
						}
						object restore {
							def apply(projectsId :PlayApi, locationsId :PlayApi, keyRingsId :PlayApi, cryptoKeysId :PlayApi, cryptoKeyVersionsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): restore = new restore(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/keyRings/${keyRingsId}/cryptoKeys/${cryptoKeysId}/cryptoKeyVersions/${cryptoKeyVersionsId}:restore").addQueryStringParameters("name" -> name.toString))
						}
						/** Create a new CryptoKeyVersion in a CryptoKey. The server will assign the next sequential id. If unset, state will be set to ENABLED. */
						class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloudkms""")
							/** Perform the request */
							def withCryptoKeyVersion(body: Schema.CryptoKeyVersion) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.CryptoKeyVersion])
						}
						object create {
							def apply(projectsId :PlayApi, locationsId :PlayApi, keyRingsId :PlayApi, cryptoKeysId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/keyRings/${keyRingsId}/cryptoKeys/${cryptoKeysId}/cryptoKeyVersions").addQueryStringParameters("parent" -> parent.toString))
						}
						/** Decrypts data that was originally encrypted using a raw cryptographic mechanism. The CryptoKey.purpose must be RAW_ENCRYPT_DECRYPT. */
						class rawDecrypt(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloudkms""")
							/** Perform the request */
							def withRawDecryptRequest(body: Schema.RawDecryptRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.RawDecryptResponse])
						}
						object rawDecrypt {
							def apply(projectsId :PlayApi, locationsId :PlayApi, keyRingsId :PlayApi, cryptoKeysId :PlayApi, cryptoKeyVersionsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): rawDecrypt = new rawDecrypt(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/keyRings/${keyRingsId}/cryptoKeys/${cryptoKeysId}/cryptoKeyVersions/${cryptoKeyVersionsId}:rawDecrypt").addQueryStringParameters("name" -> name.toString))
						}
						/** Verifies MAC tag using a CryptoKeyVersion with CryptoKey.purpose MAC, and returns a response that indicates whether or not the verification was successful. */
						class macVerify(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloudkms""")
							/** Perform the request */
							def withMacVerifyRequest(body: Schema.MacVerifyRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.MacVerifyResponse])
						}
						object macVerify {
							def apply(projectsId :PlayApi, locationsId :PlayApi, keyRingsId :PlayApi, cryptoKeysId :PlayApi, cryptoKeyVersionsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): macVerify = new macVerify(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/keyRings/${keyRingsId}/cryptoKeys/${cryptoKeysId}/cryptoKeyVersions/${cryptoKeyVersionsId}:macVerify").addQueryStringParameters("name" -> name.toString))
						}
						/** Returns metadata for a given CryptoKeyVersion. */
						class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.CryptoKeyVersion]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloudkms""")
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.CryptoKeyVersion])
						}
						object get {
							def apply(projectsId :PlayApi, locationsId :PlayApi, keyRingsId :PlayApi, cryptoKeysId :PlayApi, cryptoKeyVersionsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/keyRings/${keyRingsId}/cryptoKeys/${cryptoKeysId}/cryptoKeyVersions/${cryptoKeyVersionsId}").addQueryStringParameters("name" -> name.toString))
							given Conversion[get, Future[Schema.CryptoKeyVersion]] = (fun: get) => fun.apply()
						}
						/** Update a CryptoKeyVersion's metadata. state may be changed between ENABLED and DISABLED using this method. See DestroyCryptoKeyVersion and RestoreCryptoKeyVersion to move between other states. */
						class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloudkms""")
							/** Perform the request */
							def withCryptoKeyVersion(body: Schema.CryptoKeyVersion) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.CryptoKeyVersion])
						}
						object patch {
							def apply(projectsId :PlayApi, locationsId :PlayApi, keyRingsId :PlayApi, cryptoKeysId :PlayApi, cryptoKeyVersionsId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/keyRings/${keyRingsId}/cryptoKeys/${cryptoKeysId}/cryptoKeyVersions/${cryptoKeyVersionsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
						}
						/** Lists CryptoKeyVersions. */
						class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListCryptoKeyVersionsResponse]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloudkms""")
							/** Optional. Optional limit on the number of CryptoKeyVersions to include in the response. Further CryptoKeyVersions can subsequently be obtained by including the ListCryptoKeyVersionsResponse.next_page_token in a subsequent request. If unspecified, the server will pick an appropriate default.<br>Format: int32 */
							def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
							/** Optional. Optional pagination token, returned earlier via ListCryptoKeyVersionsResponse.next_page_token. */
							def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
							/** Optional. Only include resources that match the filter in the response. For more information, see [Sorting and filtering list results](https://cloud.google.com/kms/docs/sorting-and-filtering). */
							def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
							/** Optional. Specify how the results should be sorted. If not specified, the results will be sorted in the default order. For more information, see [Sorting and filtering list results](https://cloud.google.com/kms/docs/sorting-and-filtering). */
							def withOrderBy(orderBy: String) = new list(req.addQueryStringParameters("orderBy" -> orderBy.toString))
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListCryptoKeyVersionsResponse])
						}
						object list {
							def apply(projectsId :PlayApi, locationsId :PlayApi, keyRingsId :PlayApi, cryptoKeysId :PlayApi, parent: String, view: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/keyRings/${keyRingsId}/cryptoKeys/${cryptoKeysId}/cryptoKeyVersions").addQueryStringParameters("parent" -> parent.toString, "view" -> view.toString))
							given Conversion[list, Future[Schema.ListCryptoKeyVersionsResponse]] = (fun: list) => fun.apply()
						}
						/** Import wrapped key material into a CryptoKeyVersion. All requests must specify a CryptoKey. If a CryptoKeyVersion is additionally specified in the request, key material will be reimported into that version. Otherwise, a new version will be created, and will be assigned the next sequential id within the CryptoKey. */
						class `import`(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloudkms""")
							/** Perform the request */
							def withImportCryptoKeyVersionRequest(body: Schema.ImportCryptoKeyVersionRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.CryptoKeyVersion])
						}
						object `import` {
							def apply(projectsId :PlayApi, locationsId :PlayApi, keyRingsId :PlayApi, cryptoKeysId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): `import` = new `import`(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/keyRings/${keyRingsId}/cryptoKeys/${cryptoKeysId}/cryptoKeyVersions:import").addQueryStringParameters("parent" -> parent.toString))
						}
						/** Schedule a CryptoKeyVersion for destruction. Upon calling this method, CryptoKeyVersion.state will be set to DESTROY_SCHEDULED, and destroy_time will be set to the time destroy_scheduled_duration in the future. At that time, the state will automatically change to DESTROYED, and the key material will be irrevocably destroyed. Before the destroy_time is reached, RestoreCryptoKeyVersion may be called to reverse the process. */
						class destroy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloudkms""")
							/** Perform the request */
							def withDestroyCryptoKeyVersionRequest(body: Schema.DestroyCryptoKeyVersionRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.CryptoKeyVersion])
						}
						object destroy {
							def apply(projectsId :PlayApi, locationsId :PlayApi, keyRingsId :PlayApi, cryptoKeysId :PlayApi, cryptoKeyVersionsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): destroy = new destroy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/keyRings/${keyRingsId}/cryptoKeys/${cryptoKeysId}/cryptoKeyVersions/${cryptoKeyVersionsId}:destroy").addQueryStringParameters("name" -> name.toString))
						}
						/** Signs data using a CryptoKeyVersion with CryptoKey.purpose MAC, producing a tag that can be verified by another source with the same key. */
						class macSign(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloudkms""")
							/** Perform the request */
							def withMacSignRequest(body: Schema.MacSignRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.MacSignResponse])
						}
						object macSign {
							def apply(projectsId :PlayApi, locationsId :PlayApi, keyRingsId :PlayApi, cryptoKeysId :PlayApi, cryptoKeyVersionsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): macSign = new macSign(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/keyRings/${keyRingsId}/cryptoKeys/${cryptoKeysId}/cryptoKeyVersions/${cryptoKeyVersionsId}:macSign").addQueryStringParameters("name" -> name.toString))
						}
						/** Decrypts data that was encrypted with a public key retrieved from GetPublicKey corresponding to a CryptoKeyVersion with CryptoKey.purpose ASYMMETRIC_DECRYPT. */
						class asymmetricDecrypt(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloudkms""")
							/** Perform the request */
							def withAsymmetricDecryptRequest(body: Schema.AsymmetricDecryptRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.AsymmetricDecryptResponse])
						}
						object asymmetricDecrypt {
							def apply(projectsId :PlayApi, locationsId :PlayApi, keyRingsId :PlayApi, cryptoKeysId :PlayApi, cryptoKeyVersionsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): asymmetricDecrypt = new asymmetricDecrypt(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/keyRings/${keyRingsId}/cryptoKeys/${cryptoKeysId}/cryptoKeyVersions/${cryptoKeyVersionsId}:asymmetricDecrypt").addQueryStringParameters("name" -> name.toString))
						}
						/** Encrypts data using portable cryptographic primitives. Most users should choose Encrypt and Decrypt rather than their raw counterparts. The CryptoKey.purpose must be RAW_ENCRYPT_DECRYPT. */
						class rawEncrypt(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloudkms""")
							/** Perform the request */
							def withRawEncryptRequest(body: Schema.RawEncryptRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.RawEncryptResponse])
						}
						object rawEncrypt {
							def apply(projectsId :PlayApi, locationsId :PlayApi, keyRingsId :PlayApi, cryptoKeysId :PlayApi, cryptoKeyVersionsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): rawEncrypt = new rawEncrypt(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/keyRings/${keyRingsId}/cryptoKeys/${cryptoKeysId}/cryptoKeyVersions/${cryptoKeyVersionsId}:rawEncrypt").addQueryStringParameters("name" -> name.toString))
						}
					}
				}
				object importJobs {
					/** Returns permissions that a caller has on the specified resource. If the resource does not exist, this will return an empty set of permissions, not a `NOT_FOUND` error. Note: This operation is designed to be used for building permission-aware UIs and command-line tools, not for authorization checking. This operation may "fail open" without warning. */
					class testIamPermissions(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloudkms""")
						/** Perform the request */
						def withTestIamPermissionsRequest(body: Schema.TestIamPermissionsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.TestIamPermissionsResponse])
					}
					object testIamPermissions {
						def apply(projectsId :PlayApi, locationsId :PlayApi, keyRingsId :PlayApi, importJobsId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): testIamPermissions = new testIamPermissions(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/keyRings/${keyRingsId}/importJobs/${importJobsId}:testIamPermissions").addQueryStringParameters("resource" -> resource.toString))
					}
					/** Gets the access control policy for a resource. Returns an empty policy if the resource exists and does not have a policy set. */
					class getIamPolicy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Policy]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloudkms""")
						/** Optional. The maximum policy version that will be used to format the policy. Valid values are 0, 1, and 3. Requests specifying an invalid value will be rejected. Requests for policies with any conditional role bindings must specify version 3. Policies with no conditional role bindings may specify any valid value or leave the field unset. The policy in the response might use the policy version that you specified, or it might use a lower policy version. For example, if you specify version 3, but the policy has no conditional role bindings, the response uses version 1. To learn which resources support conditions in their IAM policies, see the [IAM documentation](https://cloud.google.com/iam/help/conditions/resource-policies).<br>Format: int32 */
						def withOptionsRequestedPolicyVersion(optionsRequestedPolicyVersion: Int) = new getIamPolicy(req.addQueryStringParameters("options.requestedPolicyVersion" -> optionsRequestedPolicyVersion.toString))
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Policy])
					}
					object getIamPolicy {
						def apply(projectsId :PlayApi, locationsId :PlayApi, keyRingsId :PlayApi, importJobsId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): getIamPolicy = new getIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/keyRings/${keyRingsId}/importJobs/${importJobsId}:getIamPolicy").addQueryStringParameters("resource" -> resource.toString))
						given Conversion[getIamPolicy, Future[Schema.Policy]] = (fun: getIamPolicy) => fun.apply()
					}
					/** Returns metadata for a given ImportJob. */
					class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ImportJob]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloudkms""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ImportJob])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, keyRingsId :PlayApi, importJobsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/keyRings/${keyRingsId}/importJobs/${importJobsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.ImportJob]] = (fun: get) => fun.apply()
					}
					/** Lists ImportJobs. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListImportJobsResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloudkms""")
						/** Optional. Optional limit on the number of ImportJobs to include in the response. Further ImportJobs can subsequently be obtained by including the ListImportJobsResponse.next_page_token in a subsequent request. If unspecified, the server will pick an appropriate default.<br>Format: int32 */
						def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
						/** Optional. Optional pagination token, returned earlier via ListImportJobsResponse.next_page_token. */
						def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
						/** Optional. Only include resources that match the filter in the response. For more information, see [Sorting and filtering list results](https://cloud.google.com/kms/docs/sorting-and-filtering). */
						def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
						/** Optional. Specify how the results should be sorted. If not specified, the results will be sorted in the default order. For more information, see [Sorting and filtering list results](https://cloud.google.com/kms/docs/sorting-and-filtering). */
						def withOrderBy(orderBy: String) = new list(req.addQueryStringParameters("orderBy" -> orderBy.toString))
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListImportJobsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, keyRingsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/keyRings/${keyRingsId}/importJobs").addQueryStringParameters("parent" -> parent.toString))
						given Conversion[list, Future[Schema.ListImportJobsResponse]] = (fun: list) => fun.apply()
					}
					/** Create a new ImportJob within a KeyRing. ImportJob.import_method is required. */
					class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloudkms""")
						/** Perform the request */
						def withImportJob(body: Schema.ImportJob) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.ImportJob])
					}
					object create {
						def apply(projectsId :PlayApi, locationsId :PlayApi, keyRingsId :PlayApi, parent: String, importJobId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/keyRings/${keyRingsId}/importJobs").addQueryStringParameters("parent" -> parent.toString, "importJobId" -> importJobId.toString))
					}
					/** Sets the access control policy on the specified resource. Replaces any existing policy. Can return `NOT_FOUND`, `INVALID_ARGUMENT`, and `PERMISSION_DENIED` errors. */
					class setIamPolicy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloudkms""")
						/** Perform the request */
						def withSetIamPolicyRequest(body: Schema.SetIamPolicyRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
					}
					object setIamPolicy {
						def apply(projectsId :PlayApi, locationsId :PlayApi, keyRingsId :PlayApi, importJobsId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): setIamPolicy = new setIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/keyRings/${keyRingsId}/importJobs/${importJobsId}:setIamPolicy").addQueryStringParameters("resource" -> resource.toString))
					}
				}
			}
			object operations {
				/** Gets the latest state of a long-running operation. Clients can use this method to poll the operation result at intervals as recommended by the API service. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloudkms""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Operation])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, operationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.Operation]] = (fun: get) => fun.apply()
				}
			}
			object ekmConnections {
				/** Returns permissions that a caller has on the specified resource. If the resource does not exist, this will return an empty set of permissions, not a `NOT_FOUND` error. Note: This operation is designed to be used for building permission-aware UIs and command-line tools, not for authorization checking. This operation may "fail open" without warning. */
				class testIamPermissions(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloudkms""")
					/** Perform the request */
					def withTestIamPermissionsRequest(body: Schema.TestIamPermissionsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.TestIamPermissionsResponse])
				}
				object testIamPermissions {
					def apply(projectsId :PlayApi, locationsId :PlayApi, ekmConnectionsId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): testIamPermissions = new testIamPermissions(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/ekmConnections/${ekmConnectionsId}:testIamPermissions").addQueryStringParameters("resource" -> resource.toString))
				}
				/** Gets the access control policy for a resource. Returns an empty policy if the resource exists and does not have a policy set. */
				class getIamPolicy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Policy]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloudkms""")
					/** Optional. The maximum policy version that will be used to format the policy. Valid values are 0, 1, and 3. Requests specifying an invalid value will be rejected. Requests for policies with any conditional role bindings must specify version 3. Policies with no conditional role bindings may specify any valid value or leave the field unset. The policy in the response might use the policy version that you specified, or it might use a lower policy version. For example, if you specify version 3, but the policy has no conditional role bindings, the response uses version 1. To learn which resources support conditions in their IAM policies, see the [IAM documentation](https://cloud.google.com/iam/help/conditions/resource-policies).<br>Format: int32 */
					def withOptionsRequestedPolicyVersion(optionsRequestedPolicyVersion: Int) = new getIamPolicy(req.addQueryStringParameters("options.requestedPolicyVersion" -> optionsRequestedPolicyVersion.toString))
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Policy])
				}
				object getIamPolicy {
					def apply(projectsId :PlayApi, locationsId :PlayApi, ekmConnectionsId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): getIamPolicy = new getIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/ekmConnections/${ekmConnectionsId}:getIamPolicy").addQueryStringParameters("resource" -> resource.toString))
					given Conversion[getIamPolicy, Future[Schema.Policy]] = (fun: getIamPolicy) => fun.apply()
				}
				/** Returns metadata for a given EkmConnection. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.EkmConnection]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloudkms""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.EkmConnection])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, ekmConnectionsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/ekmConnections/${ekmConnectionsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.EkmConnection]] = (fun: get) => fun.apply()
				}
				/** Updates an EkmConnection's metadata. */
				class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloudkms""")
					/** Perform the request */
					def withEkmConnection(body: Schema.EkmConnection) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.EkmConnection])
				}
				object patch {
					def apply(projectsId :PlayApi, locationsId :PlayApi, ekmConnectionsId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/ekmConnections/${ekmConnectionsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
				}
				/** Creates a new EkmConnection in a given Project and Location. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloudkms""")
					/** Perform the request */
					def withEkmConnection(body: Schema.EkmConnection) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.EkmConnection])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, ekmConnectionId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/ekmConnections").addQueryStringParameters("parent" -> parent.toString, "ekmConnectionId" -> ekmConnectionId.toString))
				}
				/** Sets the access control policy on the specified resource. Replaces any existing policy. Can return `NOT_FOUND`, `INVALID_ARGUMENT`, and `PERMISSION_DENIED` errors. */
				class setIamPolicy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloudkms""")
					/** Perform the request */
					def withSetIamPolicyRequest(body: Schema.SetIamPolicyRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
				}
				object setIamPolicy {
					def apply(projectsId :PlayApi, locationsId :PlayApi, ekmConnectionsId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): setIamPolicy = new setIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/ekmConnections/${ekmConnectionsId}:setIamPolicy").addQueryStringParameters("resource" -> resource.toString))
				}
				/** Lists EkmConnections. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListEkmConnectionsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloudkms""")
					/** Optional. Optional limit on the number of EkmConnections to include in the response. Further EkmConnections can subsequently be obtained by including the ListEkmConnectionsResponse.next_page_token in a subsequent request. If unspecified, the server will pick an appropriate default.<br>Format: int32 */
					def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
					/** Optional. Optional pagination token, returned earlier via ListEkmConnectionsResponse.next_page_token. */
					def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
					/** Optional. Only include resources that match the filter in the response. For more information, see [Sorting and filtering list results](https://cloud.google.com/kms/docs/sorting-and-filtering). */
					def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
					/** Optional. Specify how the results should be sorted. If not specified, the results will be sorted in the default order. For more information, see [Sorting and filtering list results](https://cloud.google.com/kms/docs/sorting-and-filtering). */
					def withOrderBy(orderBy: String) = new list(req.addQueryStringParameters("orderBy" -> orderBy.toString))
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListEkmConnectionsResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/ekmConnections").addQueryStringParameters("parent" -> parent.toString))
					given Conversion[list, Future[Schema.ListEkmConnectionsResponse]] = (fun: list) => fun.apply()
				}
				/** Verifies that Cloud KMS can successfully connect to the external key manager specified by an EkmConnection. If there is an error connecting to the EKM, this method returns a FAILED_PRECONDITION status containing structured information as described at https://cloud.google.com/kms/docs/reference/ekm_errors. */
				class verifyConnectivity(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.VerifyConnectivityResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloudkms""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.VerifyConnectivityResponse])
				}
				object verifyConnectivity {
					def apply(projectsId :PlayApi, locationsId :PlayApi, ekmConnectionsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): verifyConnectivity = new verifyConnectivity(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/ekmConnections/${ekmConnectionsId}:verifyConnectivity").addQueryStringParameters("name" -> name.toString))
					given Conversion[verifyConnectivity, Future[Schema.VerifyConnectivityResponse]] = (fun: verifyConnectivity) => fun.apply()
				}
			}
			object ekmConfig {
				/** Sets the access control policy on the specified resource. Replaces any existing policy. Can return `NOT_FOUND`, `INVALID_ARGUMENT`, and `PERMISSION_DENIED` errors. */
				class setIamPolicy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloudkms""")
					/** Perform the request */
					def withSetIamPolicyRequest(body: Schema.SetIamPolicyRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
				}
				object setIamPolicy {
					def apply(projectsId :PlayApi, locationsId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): setIamPolicy = new setIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/ekmConfig:setIamPolicy").addQueryStringParameters("resource" -> resource.toString))
				}
				/** Gets the access control policy for a resource. Returns an empty policy if the resource exists and does not have a policy set. */
				class getIamPolicy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Policy]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloudkms""")
					/** Optional. The maximum policy version that will be used to format the policy. Valid values are 0, 1, and 3. Requests specifying an invalid value will be rejected. Requests for policies with any conditional role bindings must specify version 3. Policies with no conditional role bindings may specify any valid value or leave the field unset. The policy in the response might use the policy version that you specified, or it might use a lower policy version. For example, if you specify version 3, but the policy has no conditional role bindings, the response uses version 1. To learn which resources support conditions in their IAM policies, see the [IAM documentation](https://cloud.google.com/iam/help/conditions/resource-policies).<br>Format: int32 */
					def withOptionsRequestedPolicyVersion(optionsRequestedPolicyVersion: Int) = new getIamPolicy(req.addQueryStringParameters("options.requestedPolicyVersion" -> optionsRequestedPolicyVersion.toString))
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Policy])
				}
				object getIamPolicy {
					def apply(projectsId :PlayApi, locationsId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): getIamPolicy = new getIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/ekmConfig:getIamPolicy").addQueryStringParameters("resource" -> resource.toString))
					given Conversion[getIamPolicy, Future[Schema.Policy]] = (fun: getIamPolicy) => fun.apply()
				}
				/** Returns permissions that a caller has on the specified resource. If the resource does not exist, this will return an empty set of permissions, not a `NOT_FOUND` error. Note: This operation is designed to be used for building permission-aware UIs and command-line tools, not for authorization checking. This operation may "fail open" without warning. */
				class testIamPermissions(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloudkms""")
					/** Perform the request */
					def withTestIamPermissionsRequest(body: Schema.TestIamPermissionsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.TestIamPermissionsResponse])
				}
				object testIamPermissions {
					def apply(projectsId :PlayApi, locationsId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): testIamPermissions = new testIamPermissions(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/ekmConfig:testIamPermissions").addQueryStringParameters("resource" -> resource.toString))
				}
			}
			object keyHandles {
				/** Creates a new KeyHandle, triggering the provisioning of a new CryptoKey for CMEK use with the given resource type in the configured key project and the same location. GetOperation should be used to resolve the resulting long-running operation and get the resulting KeyHandle and CryptoKey. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloudkms""")
					/** Optional. Id of the KeyHandle. Must be unique to the resource project and location. If not provided by the caller, a new UUID is used. */
					def withKeyHandleId(keyHandleId: String) = new create(req.addQueryStringParameters("keyHandleId" -> keyHandleId.toString))
					/** Perform the request */
					def withKeyHandle(body: Schema.KeyHandle) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/keyHandles").addQueryStringParameters("parent" -> parent.toString))
				}
				/** Returns the KeyHandle. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.KeyHandle]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloudkms""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.KeyHandle])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, keyHandlesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/keyHandles/${keyHandlesId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.KeyHandle]] = (fun: get) => fun.apply()
				}
				/** Lists KeyHandles. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListKeyHandlesResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloudkms""")
					/** Optional. Optional limit on the number of KeyHandles to include in the response. The service may return fewer than this value. Further KeyHandles can subsequently be obtained by including the ListKeyHandlesResponse.next_page_token in a subsequent request. If unspecified, at most 100 KeyHandles will be returned.<br>Format: int32 */
					def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
					/** Optional. Optional pagination token, returned earlier via ListKeyHandlesResponse.next_page_token. */
					def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
					/** Optional. Filter to apply when listing KeyHandles, e.g. `resource_type_selector="{SERVICE}.googleapis.com/{TYPE}"`. */
					def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListKeyHandlesResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/keyHandles").addQueryStringParameters("parent" -> parent.toString))
					given Conversion[list, Future[Schema.ListKeyHandlesResponse]] = (fun: list) => fun.apply()
				}
			}
		}
	}
}
