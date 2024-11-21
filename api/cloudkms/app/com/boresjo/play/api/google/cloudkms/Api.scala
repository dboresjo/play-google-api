package com.boresjo.play.api.google.cloudkms

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://cloudkms.googleapis.com/"

	object folders {
		class updateAutokeyConfig(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withAutokeyConfig(body: Schema.AutokeyConfig) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.AutokeyConfig])
		}
		object updateAutokeyConfig {
			def apply(foldersId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): updateAutokeyConfig = new updateAutokeyConfig(auth(ws.url(BASE_URL + s"v1/folders/${foldersId}/autokeyConfig")).addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
		}
		class getAutokeyConfig(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.AutokeyConfig]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.AutokeyConfig])
		}
		object getAutokeyConfig {
			def apply(foldersId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): getAutokeyConfig = new getAutokeyConfig(auth(ws.url(BASE_URL + s"v1/folders/${foldersId}/autokeyConfig")).addQueryStringParameters("name" -> name.toString))
			given Conversion[getAutokeyConfig, Future[Schema.AutokeyConfig]] = (fun: getAutokeyConfig) => fun.apply()
		}
	}
	object projects {
		class showEffectiveAutokeyConfig(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ShowEffectiveAutokeyConfigResponse]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.ShowEffectiveAutokeyConfigResponse])
		}
		object showEffectiveAutokeyConfig {
			def apply(projectsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): showEffectiveAutokeyConfig = new showEffectiveAutokeyConfig(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}:showEffectiveAutokeyConfig")).addQueryStringParameters("parent" -> parent.toString))
			given Conversion[showEffectiveAutokeyConfig, Future[Schema.ShowEffectiveAutokeyConfigResponse]] = (fun: showEffectiveAutokeyConfig) => fun.apply()
		}
		object locations {
			class updateEkmConfig(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withEkmConfig(body: Schema.EkmConfig) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.EkmConfig])
			}
			object updateEkmConfig {
				def apply(projectsId :PlayApi, locationsId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): updateEkmConfig = new updateEkmConfig(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/ekmConfig")).addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
			}
			class generateRandomBytes(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGenerateRandomBytesRequest(body: Schema.GenerateRandomBytesRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GenerateRandomBytesResponse])
			}
			object generateRandomBytes {
				def apply(projectsId :PlayApi, locationsId :PlayApi, location: String)(using auth: AuthToken, ec: ExecutionContext): generateRandomBytes = new generateRandomBytes(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}:generateRandomBytes")).addQueryStringParameters("location" -> location.toString))
			}
			class getEkmConfig(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.EkmConfig]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.EkmConfig])
			}
			object getEkmConfig {
				def apply(projectsId :PlayApi, locationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): getEkmConfig = new getEkmConfig(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/ekmConfig")).addQueryStringParameters("name" -> name.toString))
				given Conversion[getEkmConfig, Future[Schema.EkmConfig]] = (fun: getEkmConfig) => fun.apply()
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Location]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.Location])
			}
			object get {
				def apply(projectsId :PlayApi, locationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}")).addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.Location]] = (fun: get) => fun.apply()
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListLocationsResponse]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.ListLocationsResponse])
			}
			object list {
				def apply(projectsId :PlayApi, name: String, filter: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations")).addQueryStringParameters("name" -> name.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListLocationsResponse]] = (fun: list) => fun.apply()
			}
			object keyRings {
				class testIamPermissions(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withTestIamPermissionsRequest(body: Schema.TestIamPermissionsRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.TestIamPermissionsResponse])
				}
				object testIamPermissions {
					def apply(projectsId :PlayApi, locationsId :PlayApi, keyRingsId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): testIamPermissions = new testIamPermissions(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/keyRings/${keyRingsId}:testIamPermissions")).addQueryStringParameters("resource" -> resource.toString))
				}
				class getIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Policy]) {
					/** Optional. The maximum policy version that will be used to format the policy. Valid values are 0, 1, and 3. Requests specifying an invalid value will be rejected. Requests for policies with any conditional role bindings must specify version 3. Policies with no conditional role bindings may specify any valid value or leave the field unset. The policy in the response might use the policy version that you specified, or it might use a lower policy version. For example, if you specify version 3, but the policy has no conditional role bindings, the response uses version 1. To learn which resources support conditions in their IAM policies, see the [IAM documentation](https://cloud.google.com/iam/help/conditions/resource-policies).<br>Format: int32 */
					def withOptionsRequestedPolicyVersion(optionsRequestedPolicyVersion: Int) = new getIamPolicy(req.addQueryStringParameters("options.requestedPolicyVersion" -> optionsRequestedPolicyVersion.toString))
					def apply() = req.execute("GET").map(_.json.as[Schema.Policy])
				}
				object getIamPolicy {
					def apply(projectsId :PlayApi, locationsId :PlayApi, keyRingsId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): getIamPolicy = new getIamPolicy(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/keyRings/${keyRingsId}:getIamPolicy")).addQueryStringParameters("resource" -> resource.toString))
					given Conversion[getIamPolicy, Future[Schema.Policy]] = (fun: getIamPolicy) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.KeyRing]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.KeyRing])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, keyRingsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/keyRings/${keyRingsId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.KeyRing]] = (fun: get) => fun.apply()
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListKeyRingsResponse]) {
					/** Optional. Optional limit on the number of KeyRings to include in the response. Further KeyRings can subsequently be obtained by including the ListKeyRingsResponse.next_page_token in a subsequent request. If unspecified, the server will pick an appropriate default.<br>Format: int32 */
					def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
					/** Optional. Optional pagination token, returned earlier via ListKeyRingsResponse.next_page_token. */
					def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
					/** Optional. Only include resources that match the filter in the response. For more information, see [Sorting and filtering list results](https://cloud.google.com/kms/docs/sorting-and-filtering). */
					def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
					/** Optional. Specify how the results should be sorted. If not specified, the results will be sorted in the default order. For more information, see [Sorting and filtering list results](https://cloud.google.com/kms/docs/sorting-and-filtering). */
					def withOrderBy(orderBy: String) = new list(req.addQueryStringParameters("orderBy" -> orderBy.toString))
					def apply() = req.execute("GET").map(_.json.as[Schema.ListKeyRingsResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/keyRings")).addQueryStringParameters("parent" -> parent.toString))
					given Conversion[list, Future[Schema.ListKeyRingsResponse]] = (fun: list) => fun.apply()
				}
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withKeyRing(body: Schema.KeyRing) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.KeyRing])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, keyRingId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/keyRings")).addQueryStringParameters("parent" -> parent.toString, "keyRingId" -> keyRingId.toString))
				}
				class setIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withSetIamPolicyRequest(body: Schema.SetIamPolicyRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Policy])
				}
				object setIamPolicy {
					def apply(projectsId :PlayApi, locationsId :PlayApi, keyRingsId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): setIamPolicy = new setIamPolicy(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/keyRings/${keyRingsId}:setIamPolicy")).addQueryStringParameters("resource" -> resource.toString))
				}
				object cryptoKeys {
					class testIamPermissions(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withTestIamPermissionsRequest(body: Schema.TestIamPermissionsRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.TestIamPermissionsResponse])
					}
					object testIamPermissions {
						def apply(projectsId :PlayApi, locationsId :PlayApi, keyRingsId :PlayApi, cryptoKeysId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): testIamPermissions = new testIamPermissions(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/keyRings/${keyRingsId}/cryptoKeys/${cryptoKeysId}:testIamPermissions")).addQueryStringParameters("resource" -> resource.toString))
					}
					class encrypt(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withEncryptRequest(body: Schema.EncryptRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.EncryptResponse])
					}
					object encrypt {
						def apply(projectsId :PlayApi, locationsId :PlayApi, keyRingsId :PlayApi, cryptoKeysId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): encrypt = new encrypt(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/keyRings/${keyRingsId}/cryptoKeys/${cryptoKeysId}:encrypt")).addQueryStringParameters("name" -> name.toString))
					}
					class decrypt(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withDecryptRequest(body: Schema.DecryptRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.DecryptResponse])
					}
					object decrypt {
						def apply(projectsId :PlayApi, locationsId :PlayApi, keyRingsId :PlayApi, cryptoKeysId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): decrypt = new decrypt(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/keyRings/${keyRingsId}/cryptoKeys/${cryptoKeysId}:decrypt")).addQueryStringParameters("name" -> name.toString))
					}
					class getIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Policy]) {
						/** Optional. The maximum policy version that will be used to format the policy. Valid values are 0, 1, and 3. Requests specifying an invalid value will be rejected. Requests for policies with any conditional role bindings must specify version 3. Policies with no conditional role bindings may specify any valid value or leave the field unset. The policy in the response might use the policy version that you specified, or it might use a lower policy version. For example, if you specify version 3, but the policy has no conditional role bindings, the response uses version 1. To learn which resources support conditions in their IAM policies, see the [IAM documentation](https://cloud.google.com/iam/help/conditions/resource-policies).<br>Format: int32 */
						def withOptionsRequestedPolicyVersion(optionsRequestedPolicyVersion: Int) = new getIamPolicy(req.addQueryStringParameters("options.requestedPolicyVersion" -> optionsRequestedPolicyVersion.toString))
						def apply() = req.execute("GET").map(_.json.as[Schema.Policy])
					}
					object getIamPolicy {
						def apply(projectsId :PlayApi, locationsId :PlayApi, keyRingsId :PlayApi, cryptoKeysId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): getIamPolicy = new getIamPolicy(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/keyRings/${keyRingsId}/cryptoKeys/${cryptoKeysId}:getIamPolicy")).addQueryStringParameters("resource" -> resource.toString))
						given Conversion[getIamPolicy, Future[Schema.Policy]] = (fun: getIamPolicy) => fun.apply()
					}
					class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.CryptoKey]) {
						def apply() = req.execute("GET").map(_.json.as[Schema.CryptoKey])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, keyRingsId :PlayApi, cryptoKeysId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/keyRings/${keyRingsId}/cryptoKeys/${cryptoKeysId}")).addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.CryptoKey]] = (fun: get) => fun.apply()
					}
					class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withCryptoKey(body: Schema.CryptoKey) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.CryptoKey])
					}
					object patch {
						def apply(projectsId :PlayApi, locationsId :PlayApi, keyRingsId :PlayApi, cryptoKeysId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/keyRings/${keyRingsId}/cryptoKeys/${cryptoKeysId}")).addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
					}
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListCryptoKeysResponse]) {
						/** Optional. Optional limit on the number of CryptoKeys to include in the response. Further CryptoKeys can subsequently be obtained by including the ListCryptoKeysResponse.next_page_token in a subsequent request. If unspecified, the server will pick an appropriate default.<br>Format: int32 */
						def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
						/** Optional. Optional pagination token, returned earlier via ListCryptoKeysResponse.next_page_token. */
						def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
						/** Optional. Only include resources that match the filter in the response. For more information, see [Sorting and filtering list results](https://cloud.google.com/kms/docs/sorting-and-filtering). */
						def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
						/** Optional. Specify how the results should be sorted. If not specified, the results will be sorted in the default order. For more information, see [Sorting and filtering list results](https://cloud.google.com/kms/docs/sorting-and-filtering). */
						def withOrderBy(orderBy: String) = new list(req.addQueryStringParameters("orderBy" -> orderBy.toString))
						def apply() = req.execute("GET").map(_.json.as[Schema.ListCryptoKeysResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, keyRingsId :PlayApi, parent: String, versionView: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/keyRings/${keyRingsId}/cryptoKeys")).addQueryStringParameters("parent" -> parent.toString, "versionView" -> versionView.toString))
						given Conversion[list, Future[Schema.ListCryptoKeysResponse]] = (fun: list) => fun.apply()
					}
					class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withCryptoKey(body: Schema.CryptoKey) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.CryptoKey])
					}
					object create {
						def apply(projectsId :PlayApi, locationsId :PlayApi, keyRingsId :PlayApi, parent: String, cryptoKeyId: String, skipInitialVersionCreation: Boolean)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/keyRings/${keyRingsId}/cryptoKeys")).addQueryStringParameters("parent" -> parent.toString, "cryptoKeyId" -> cryptoKeyId.toString, "skipInitialVersionCreation" -> skipInitialVersionCreation.toString))
					}
					class setIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withSetIamPolicyRequest(body: Schema.SetIamPolicyRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Policy])
					}
					object setIamPolicy {
						def apply(projectsId :PlayApi, locationsId :PlayApi, keyRingsId :PlayApi, cryptoKeysId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): setIamPolicy = new setIamPolicy(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/keyRings/${keyRingsId}/cryptoKeys/${cryptoKeysId}:setIamPolicy")).addQueryStringParameters("resource" -> resource.toString))
					}
					class updatePrimaryVersion(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withUpdateCryptoKeyPrimaryVersionRequest(body: Schema.UpdateCryptoKeyPrimaryVersionRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.CryptoKey])
					}
					object updatePrimaryVersion {
						def apply(projectsId :PlayApi, locationsId :PlayApi, keyRingsId :PlayApi, cryptoKeysId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): updatePrimaryVersion = new updatePrimaryVersion(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/keyRings/${keyRingsId}/cryptoKeys/${cryptoKeysId}:updatePrimaryVersion")).addQueryStringParameters("name" -> name.toString))
					}
					object cryptoKeyVersions {
						class getPublicKey(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.PublicKey]) {
							def apply() = req.execute("GET").map(_.json.as[Schema.PublicKey])
						}
						object getPublicKey {
							def apply(projectsId :PlayApi, locationsId :PlayApi, keyRingsId :PlayApi, cryptoKeysId :PlayApi, cryptoKeyVersionsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): getPublicKey = new getPublicKey(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/keyRings/${keyRingsId}/cryptoKeys/${cryptoKeysId}/cryptoKeyVersions/${cryptoKeyVersionsId}/publicKey")).addQueryStringParameters("name" -> name.toString))
							given Conversion[getPublicKey, Future[Schema.PublicKey]] = (fun: getPublicKey) => fun.apply()
						}
						class asymmetricSign(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
							def withAsymmetricSignRequest(body: Schema.AsymmetricSignRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.AsymmetricSignResponse])
						}
						object asymmetricSign {
							def apply(projectsId :PlayApi, locationsId :PlayApi, keyRingsId :PlayApi, cryptoKeysId :PlayApi, cryptoKeyVersionsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): asymmetricSign = new asymmetricSign(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/keyRings/${keyRingsId}/cryptoKeys/${cryptoKeysId}/cryptoKeyVersions/${cryptoKeyVersionsId}:asymmetricSign")).addQueryStringParameters("name" -> name.toString))
						}
						class restore(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
							def withRestoreCryptoKeyVersionRequest(body: Schema.RestoreCryptoKeyVersionRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.CryptoKeyVersion])
						}
						object restore {
							def apply(projectsId :PlayApi, locationsId :PlayApi, keyRingsId :PlayApi, cryptoKeysId :PlayApi, cryptoKeyVersionsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): restore = new restore(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/keyRings/${keyRingsId}/cryptoKeys/${cryptoKeysId}/cryptoKeyVersions/${cryptoKeyVersionsId}:restore")).addQueryStringParameters("name" -> name.toString))
						}
						class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
							def withCryptoKeyVersion(body: Schema.CryptoKeyVersion) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.CryptoKeyVersion])
						}
						object create {
							def apply(projectsId :PlayApi, locationsId :PlayApi, keyRingsId :PlayApi, cryptoKeysId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/keyRings/${keyRingsId}/cryptoKeys/${cryptoKeysId}/cryptoKeyVersions")).addQueryStringParameters("parent" -> parent.toString))
						}
						class rawDecrypt(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
							def withRawDecryptRequest(body: Schema.RawDecryptRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.RawDecryptResponse])
						}
						object rawDecrypt {
							def apply(projectsId :PlayApi, locationsId :PlayApi, keyRingsId :PlayApi, cryptoKeysId :PlayApi, cryptoKeyVersionsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): rawDecrypt = new rawDecrypt(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/keyRings/${keyRingsId}/cryptoKeys/${cryptoKeysId}/cryptoKeyVersions/${cryptoKeyVersionsId}:rawDecrypt")).addQueryStringParameters("name" -> name.toString))
						}
						class macVerify(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
							def withMacVerifyRequest(body: Schema.MacVerifyRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.MacVerifyResponse])
						}
						object macVerify {
							def apply(projectsId :PlayApi, locationsId :PlayApi, keyRingsId :PlayApi, cryptoKeysId :PlayApi, cryptoKeyVersionsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): macVerify = new macVerify(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/keyRings/${keyRingsId}/cryptoKeys/${cryptoKeysId}/cryptoKeyVersions/${cryptoKeyVersionsId}:macVerify")).addQueryStringParameters("name" -> name.toString))
						}
						class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.CryptoKeyVersion]) {
							def apply() = req.execute("GET").map(_.json.as[Schema.CryptoKeyVersion])
						}
						object get {
							def apply(projectsId :PlayApi, locationsId :PlayApi, keyRingsId :PlayApi, cryptoKeysId :PlayApi, cryptoKeyVersionsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/keyRings/${keyRingsId}/cryptoKeys/${cryptoKeysId}/cryptoKeyVersions/${cryptoKeyVersionsId}")).addQueryStringParameters("name" -> name.toString))
							given Conversion[get, Future[Schema.CryptoKeyVersion]] = (fun: get) => fun.apply()
						}
						class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
							def withCryptoKeyVersion(body: Schema.CryptoKeyVersion) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.CryptoKeyVersion])
						}
						object patch {
							def apply(projectsId :PlayApi, locationsId :PlayApi, keyRingsId :PlayApi, cryptoKeysId :PlayApi, cryptoKeyVersionsId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/keyRings/${keyRingsId}/cryptoKeys/${cryptoKeysId}/cryptoKeyVersions/${cryptoKeyVersionsId}")).addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
						}
						class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListCryptoKeyVersionsResponse]) {
							/** Optional. Optional limit on the number of CryptoKeyVersions to include in the response. Further CryptoKeyVersions can subsequently be obtained by including the ListCryptoKeyVersionsResponse.next_page_token in a subsequent request. If unspecified, the server will pick an appropriate default.<br>Format: int32 */
							def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
							/** Optional. Optional pagination token, returned earlier via ListCryptoKeyVersionsResponse.next_page_token. */
							def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
							/** Optional. Only include resources that match the filter in the response. For more information, see [Sorting and filtering list results](https://cloud.google.com/kms/docs/sorting-and-filtering). */
							def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
							/** Optional. Specify how the results should be sorted. If not specified, the results will be sorted in the default order. For more information, see [Sorting and filtering list results](https://cloud.google.com/kms/docs/sorting-and-filtering). */
							def withOrderBy(orderBy: String) = new list(req.addQueryStringParameters("orderBy" -> orderBy.toString))
							def apply() = req.execute("GET").map(_.json.as[Schema.ListCryptoKeyVersionsResponse])
						}
						object list {
							def apply(projectsId :PlayApi, locationsId :PlayApi, keyRingsId :PlayApi, cryptoKeysId :PlayApi, parent: String, view: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/keyRings/${keyRingsId}/cryptoKeys/${cryptoKeysId}/cryptoKeyVersions")).addQueryStringParameters("parent" -> parent.toString, "view" -> view.toString))
							given Conversion[list, Future[Schema.ListCryptoKeyVersionsResponse]] = (fun: list) => fun.apply()
						}
						class `import`(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
							def withImportCryptoKeyVersionRequest(body: Schema.ImportCryptoKeyVersionRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.CryptoKeyVersion])
						}
						object `import` {
							def apply(projectsId :PlayApi, locationsId :PlayApi, keyRingsId :PlayApi, cryptoKeysId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): `import` = new `import`(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/keyRings/${keyRingsId}/cryptoKeys/${cryptoKeysId}/cryptoKeyVersions:import")).addQueryStringParameters("parent" -> parent.toString))
						}
						class destroy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
							def withDestroyCryptoKeyVersionRequest(body: Schema.DestroyCryptoKeyVersionRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.CryptoKeyVersion])
						}
						object destroy {
							def apply(projectsId :PlayApi, locationsId :PlayApi, keyRingsId :PlayApi, cryptoKeysId :PlayApi, cryptoKeyVersionsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): destroy = new destroy(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/keyRings/${keyRingsId}/cryptoKeys/${cryptoKeysId}/cryptoKeyVersions/${cryptoKeyVersionsId}:destroy")).addQueryStringParameters("name" -> name.toString))
						}
						class macSign(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
							def withMacSignRequest(body: Schema.MacSignRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.MacSignResponse])
						}
						object macSign {
							def apply(projectsId :PlayApi, locationsId :PlayApi, keyRingsId :PlayApi, cryptoKeysId :PlayApi, cryptoKeyVersionsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): macSign = new macSign(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/keyRings/${keyRingsId}/cryptoKeys/${cryptoKeysId}/cryptoKeyVersions/${cryptoKeyVersionsId}:macSign")).addQueryStringParameters("name" -> name.toString))
						}
						class asymmetricDecrypt(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
							def withAsymmetricDecryptRequest(body: Schema.AsymmetricDecryptRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.AsymmetricDecryptResponse])
						}
						object asymmetricDecrypt {
							def apply(projectsId :PlayApi, locationsId :PlayApi, keyRingsId :PlayApi, cryptoKeysId :PlayApi, cryptoKeyVersionsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): asymmetricDecrypt = new asymmetricDecrypt(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/keyRings/${keyRingsId}/cryptoKeys/${cryptoKeysId}/cryptoKeyVersions/${cryptoKeyVersionsId}:asymmetricDecrypt")).addQueryStringParameters("name" -> name.toString))
						}
						class rawEncrypt(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
							def withRawEncryptRequest(body: Schema.RawEncryptRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.RawEncryptResponse])
						}
						object rawEncrypt {
							def apply(projectsId :PlayApi, locationsId :PlayApi, keyRingsId :PlayApi, cryptoKeysId :PlayApi, cryptoKeyVersionsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): rawEncrypt = new rawEncrypt(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/keyRings/${keyRingsId}/cryptoKeys/${cryptoKeysId}/cryptoKeyVersions/${cryptoKeyVersionsId}:rawEncrypt")).addQueryStringParameters("name" -> name.toString))
						}
					}
				}
				object importJobs {
					class testIamPermissions(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withTestIamPermissionsRequest(body: Schema.TestIamPermissionsRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.TestIamPermissionsResponse])
					}
					object testIamPermissions {
						def apply(projectsId :PlayApi, locationsId :PlayApi, keyRingsId :PlayApi, importJobsId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): testIamPermissions = new testIamPermissions(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/keyRings/${keyRingsId}/importJobs/${importJobsId}:testIamPermissions")).addQueryStringParameters("resource" -> resource.toString))
					}
					class getIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Policy]) {
						/** Optional. The maximum policy version that will be used to format the policy. Valid values are 0, 1, and 3. Requests specifying an invalid value will be rejected. Requests for policies with any conditional role bindings must specify version 3. Policies with no conditional role bindings may specify any valid value or leave the field unset. The policy in the response might use the policy version that you specified, or it might use a lower policy version. For example, if you specify version 3, but the policy has no conditional role bindings, the response uses version 1. To learn which resources support conditions in their IAM policies, see the [IAM documentation](https://cloud.google.com/iam/help/conditions/resource-policies).<br>Format: int32 */
						def withOptionsRequestedPolicyVersion(optionsRequestedPolicyVersion: Int) = new getIamPolicy(req.addQueryStringParameters("options.requestedPolicyVersion" -> optionsRequestedPolicyVersion.toString))
						def apply() = req.execute("GET").map(_.json.as[Schema.Policy])
					}
					object getIamPolicy {
						def apply(projectsId :PlayApi, locationsId :PlayApi, keyRingsId :PlayApi, importJobsId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): getIamPolicy = new getIamPolicy(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/keyRings/${keyRingsId}/importJobs/${importJobsId}:getIamPolicy")).addQueryStringParameters("resource" -> resource.toString))
						given Conversion[getIamPolicy, Future[Schema.Policy]] = (fun: getIamPolicy) => fun.apply()
					}
					class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ImportJob]) {
						def apply() = req.execute("GET").map(_.json.as[Schema.ImportJob])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, keyRingsId :PlayApi, importJobsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/keyRings/${keyRingsId}/importJobs/${importJobsId}")).addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.ImportJob]] = (fun: get) => fun.apply()
					}
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListImportJobsResponse]) {
						/** Optional. Optional limit on the number of ImportJobs to include in the response. Further ImportJobs can subsequently be obtained by including the ListImportJobsResponse.next_page_token in a subsequent request. If unspecified, the server will pick an appropriate default.<br>Format: int32 */
						def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
						/** Optional. Optional pagination token, returned earlier via ListImportJobsResponse.next_page_token. */
						def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
						/** Optional. Only include resources that match the filter in the response. For more information, see [Sorting and filtering list results](https://cloud.google.com/kms/docs/sorting-and-filtering). */
						def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
						/** Optional. Specify how the results should be sorted. If not specified, the results will be sorted in the default order. For more information, see [Sorting and filtering list results](https://cloud.google.com/kms/docs/sorting-and-filtering). */
						def withOrderBy(orderBy: String) = new list(req.addQueryStringParameters("orderBy" -> orderBy.toString))
						def apply() = req.execute("GET").map(_.json.as[Schema.ListImportJobsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, keyRingsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/keyRings/${keyRingsId}/importJobs")).addQueryStringParameters("parent" -> parent.toString))
						given Conversion[list, Future[Schema.ListImportJobsResponse]] = (fun: list) => fun.apply()
					}
					class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withImportJob(body: Schema.ImportJob) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.ImportJob])
					}
					object create {
						def apply(projectsId :PlayApi, locationsId :PlayApi, keyRingsId :PlayApi, parent: String, importJobId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/keyRings/${keyRingsId}/importJobs")).addQueryStringParameters("parent" -> parent.toString, "importJobId" -> importJobId.toString))
					}
					class setIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withSetIamPolicyRequest(body: Schema.SetIamPolicyRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Policy])
					}
					object setIamPolicy {
						def apply(projectsId :PlayApi, locationsId :PlayApi, keyRingsId :PlayApi, importJobsId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): setIamPolicy = new setIamPolicy(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/keyRings/${keyRingsId}/importJobs/${importJobsId}:setIamPolicy")).addQueryStringParameters("resource" -> resource.toString))
					}
				}
			}
			object operations {
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.Operation])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, operationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/operations/${operationsId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.Operation]] = (fun: get) => fun.apply()
				}
			}
			object ekmConnections {
				class testIamPermissions(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withTestIamPermissionsRequest(body: Schema.TestIamPermissionsRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.TestIamPermissionsResponse])
				}
				object testIamPermissions {
					def apply(projectsId :PlayApi, locationsId :PlayApi, ekmConnectionsId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): testIamPermissions = new testIamPermissions(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/ekmConnections/${ekmConnectionsId}:testIamPermissions")).addQueryStringParameters("resource" -> resource.toString))
				}
				class getIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Policy]) {
					/** Optional. The maximum policy version that will be used to format the policy. Valid values are 0, 1, and 3. Requests specifying an invalid value will be rejected. Requests for policies with any conditional role bindings must specify version 3. Policies with no conditional role bindings may specify any valid value or leave the field unset. The policy in the response might use the policy version that you specified, or it might use a lower policy version. For example, if you specify version 3, but the policy has no conditional role bindings, the response uses version 1. To learn which resources support conditions in their IAM policies, see the [IAM documentation](https://cloud.google.com/iam/help/conditions/resource-policies).<br>Format: int32 */
					def withOptionsRequestedPolicyVersion(optionsRequestedPolicyVersion: Int) = new getIamPolicy(req.addQueryStringParameters("options.requestedPolicyVersion" -> optionsRequestedPolicyVersion.toString))
					def apply() = req.execute("GET").map(_.json.as[Schema.Policy])
				}
				object getIamPolicy {
					def apply(projectsId :PlayApi, locationsId :PlayApi, ekmConnectionsId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): getIamPolicy = new getIamPolicy(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/ekmConnections/${ekmConnectionsId}:getIamPolicy")).addQueryStringParameters("resource" -> resource.toString))
					given Conversion[getIamPolicy, Future[Schema.Policy]] = (fun: getIamPolicy) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.EkmConnection]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.EkmConnection])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, ekmConnectionsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/ekmConnections/${ekmConnectionsId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.EkmConnection]] = (fun: get) => fun.apply()
				}
				class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withEkmConnection(body: Schema.EkmConnection) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.EkmConnection])
				}
				object patch {
					def apply(projectsId :PlayApi, locationsId :PlayApi, ekmConnectionsId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/ekmConnections/${ekmConnectionsId}")).addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
				}
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withEkmConnection(body: Schema.EkmConnection) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.EkmConnection])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, ekmConnectionId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/ekmConnections")).addQueryStringParameters("parent" -> parent.toString, "ekmConnectionId" -> ekmConnectionId.toString))
				}
				class setIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withSetIamPolicyRequest(body: Schema.SetIamPolicyRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Policy])
				}
				object setIamPolicy {
					def apply(projectsId :PlayApi, locationsId :PlayApi, ekmConnectionsId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): setIamPolicy = new setIamPolicy(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/ekmConnections/${ekmConnectionsId}:setIamPolicy")).addQueryStringParameters("resource" -> resource.toString))
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListEkmConnectionsResponse]) {
					/** Optional. Optional limit on the number of EkmConnections to include in the response. Further EkmConnections can subsequently be obtained by including the ListEkmConnectionsResponse.next_page_token in a subsequent request. If unspecified, the server will pick an appropriate default.<br>Format: int32 */
					def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
					/** Optional. Optional pagination token, returned earlier via ListEkmConnectionsResponse.next_page_token. */
					def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
					/** Optional. Only include resources that match the filter in the response. For more information, see [Sorting and filtering list results](https://cloud.google.com/kms/docs/sorting-and-filtering). */
					def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
					/** Optional. Specify how the results should be sorted. If not specified, the results will be sorted in the default order. For more information, see [Sorting and filtering list results](https://cloud.google.com/kms/docs/sorting-and-filtering). */
					def withOrderBy(orderBy: String) = new list(req.addQueryStringParameters("orderBy" -> orderBy.toString))
					def apply() = req.execute("GET").map(_.json.as[Schema.ListEkmConnectionsResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/ekmConnections")).addQueryStringParameters("parent" -> parent.toString))
					given Conversion[list, Future[Schema.ListEkmConnectionsResponse]] = (fun: list) => fun.apply()
				}
				class verifyConnectivity(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.VerifyConnectivityResponse]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.VerifyConnectivityResponse])
				}
				object verifyConnectivity {
					def apply(projectsId :PlayApi, locationsId :PlayApi, ekmConnectionsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): verifyConnectivity = new verifyConnectivity(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/ekmConnections/${ekmConnectionsId}:verifyConnectivity")).addQueryStringParameters("name" -> name.toString))
					given Conversion[verifyConnectivity, Future[Schema.VerifyConnectivityResponse]] = (fun: verifyConnectivity) => fun.apply()
				}
			}
			object ekmConfig {
				class setIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withSetIamPolicyRequest(body: Schema.SetIamPolicyRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Policy])
				}
				object setIamPolicy {
					def apply(projectsId :PlayApi, locationsId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): setIamPolicy = new setIamPolicy(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/ekmConfig:setIamPolicy")).addQueryStringParameters("resource" -> resource.toString))
				}
				class getIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Policy]) {
					/** Optional. The maximum policy version that will be used to format the policy. Valid values are 0, 1, and 3. Requests specifying an invalid value will be rejected. Requests for policies with any conditional role bindings must specify version 3. Policies with no conditional role bindings may specify any valid value or leave the field unset. The policy in the response might use the policy version that you specified, or it might use a lower policy version. For example, if you specify version 3, but the policy has no conditional role bindings, the response uses version 1. To learn which resources support conditions in their IAM policies, see the [IAM documentation](https://cloud.google.com/iam/help/conditions/resource-policies).<br>Format: int32 */
					def withOptionsRequestedPolicyVersion(optionsRequestedPolicyVersion: Int) = new getIamPolicy(req.addQueryStringParameters("options.requestedPolicyVersion" -> optionsRequestedPolicyVersion.toString))
					def apply() = req.execute("GET").map(_.json.as[Schema.Policy])
				}
				object getIamPolicy {
					def apply(projectsId :PlayApi, locationsId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): getIamPolicy = new getIamPolicy(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/ekmConfig:getIamPolicy")).addQueryStringParameters("resource" -> resource.toString))
					given Conversion[getIamPolicy, Future[Schema.Policy]] = (fun: getIamPolicy) => fun.apply()
				}
				class testIamPermissions(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withTestIamPermissionsRequest(body: Schema.TestIamPermissionsRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.TestIamPermissionsResponse])
				}
				object testIamPermissions {
					def apply(projectsId :PlayApi, locationsId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): testIamPermissions = new testIamPermissions(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/ekmConfig:testIamPermissions")).addQueryStringParameters("resource" -> resource.toString))
				}
			}
			object keyHandles {
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					/** Optional. Id of the KeyHandle. Must be unique to the resource project and location. If not provided by the caller, a new UUID is used. */
					def withKeyHandleId(keyHandleId: String) = new create(req.addQueryStringParameters("keyHandleId" -> keyHandleId.toString))
					def withKeyHandle(body: Schema.KeyHandle) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Operation])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/keyHandles")).addQueryStringParameters("parent" -> parent.toString))
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.KeyHandle]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.KeyHandle])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, keyHandlesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/keyHandles/${keyHandlesId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.KeyHandle]] = (fun: get) => fun.apply()
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListKeyHandlesResponse]) {
					/** Optional. Optional limit on the number of KeyHandles to include in the response. The service may return fewer than this value. Further KeyHandles can subsequently be obtained by including the ListKeyHandlesResponse.next_page_token in a subsequent request. If unspecified, at most 100 KeyHandles will be returned.<br>Format: int32 */
					def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
					/** Optional. Optional pagination token, returned earlier via ListKeyHandlesResponse.next_page_token. */
					def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
					/** Optional. Filter to apply when listing KeyHandles, e.g. `resource_type_selector="{SERVICE}.googleapis.com/{TYPE}"`. */
					def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
					def apply() = req.execute("GET").map(_.json.as[Schema.ListKeyHandlesResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/keyHandles")).addQueryStringParameters("parent" -> parent.toString))
					given Conversion[list, Future[Schema.ListKeyHandlesResponse]] = (fun: list) => fun.apply()
				}
			}
		}
	}
}
