package com.boresjo.play.api.google.apigee

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://apigee.googleapis.com/"

	object hybrid {
		object issuers {
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1ListHybridIssuersResponse]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudApigeeV1ListHybridIssuersResponse])
			}
			object list {
				def apply(name: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/hybrid/issuers")).addQueryStringParameters("name" -> name.toString))
				given Conversion[list, Future[Schema.GoogleCloudApigeeV1ListHybridIssuersResponse]] = (fun: list) => fun.apply()
			}
		}
	}
	object organizations {
		class setSyncAuthorization(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withGoogleCloudApigeeV1SyncAuthorization(body: Schema.GoogleCloudApigeeV1SyncAuthorization) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleCloudApigeeV1SyncAuthorization])
		}
		object setSyncAuthorization {
			def apply(organizationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): setSyncAuthorization = new setSyncAuthorization(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}:setSyncAuthorization")).addQueryStringParameters("name" -> name.toString))
		}
		class getRuntimeConfig(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1RuntimeConfig]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudApigeeV1RuntimeConfig])
		}
		object getRuntimeConfig {
			def apply(organizationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): getRuntimeConfig = new getRuntimeConfig(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/runtimeConfig")).addQueryStringParameters("name" -> name.toString))
			given Conversion[getRuntimeConfig, Future[Schema.GoogleCloudApigeeV1RuntimeConfig]] = (fun: getRuntimeConfig) => fun.apply()
		}
		class getDeployedIngressConfig(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1IngressConfig]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudApigeeV1IngressConfig])
		}
		object getDeployedIngressConfig {
			def apply(organizationsId :PlayApi, view: String, name: String)(using auth: AuthToken, ec: ExecutionContext): getDeployedIngressConfig = new getDeployedIngressConfig(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/deployedIngressConfig")).addQueryStringParameters("view" -> view.toString, "name" -> name.toString))
			given Conversion[getDeployedIngressConfig, Future[Schema.GoogleCloudApigeeV1IngressConfig]] = (fun: getDeployedIngressConfig) => fun.apply()
		}
		class getProjectMapping(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1OrganizationProjectMapping]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudApigeeV1OrganizationProjectMapping])
		}
		object getProjectMapping {
			def apply(organizationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): getProjectMapping = new getProjectMapping(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}:getProjectMapping")).addQueryStringParameters("name" -> name.toString))
			given Conversion[getProjectMapping, Future[Schema.GoogleCloudApigeeV1OrganizationProjectMapping]] = (fun: getProjectMapping) => fun.apply()
		}
		class updateControlPlaneAccess(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withGoogleCloudApigeeV1ControlPlaneAccess(body: Schema.GoogleCloudApigeeV1ControlPlaneAccess) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.GoogleLongrunningOperation])
		}
		object updateControlPlaneAccess {
			def apply(organizationsId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): updateControlPlaneAccess = new updateControlPlaneAccess(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/controlPlaneAccess")).addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
		}
		class updateSecuritySettings(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			/** Optional. The list of fields to update. Allowed fields are: - ml_retraining_feedback_enabled<br>Format: google-fieldmask */
			def withUpdateMask(updateMask: String) = new updateSecuritySettings(req.addQueryStringParameters("updateMask" -> updateMask.toString))
			def withGoogleCloudApigeeV1SecuritySettings(body: Schema.GoogleCloudApigeeV1SecuritySettings) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.GoogleCloudApigeeV1SecuritySettings])
		}
		object updateSecuritySettings {
			def apply(organizationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): updateSecuritySettings = new updateSecuritySettings(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/securitySettings")).addQueryStringParameters("name" -> name.toString))
		}
		class getSecuritySettings(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1SecuritySettings]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudApigeeV1SecuritySettings])
		}
		object getSecuritySettings {
			def apply(organizationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): getSecuritySettings = new getSecuritySettings(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/securitySettings")).addQueryStringParameters("name" -> name.toString))
			given Conversion[getSecuritySettings, Future[Schema.GoogleCloudApigeeV1SecuritySettings]] = (fun: getSecuritySettings) => fun.apply()
		}
		class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withGoogleCloudApigeeV1Organization(body: Schema.GoogleCloudApigeeV1Organization) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleLongrunningOperation])
		}
		object create {
			def apply(parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/organizations")).addQueryStringParameters("parent" -> parent.toString))
		}
		class getControlPlaneAccess(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1ControlPlaneAccess]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudApigeeV1ControlPlaneAccess])
		}
		object getControlPlaneAccess {
			def apply(organizationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): getControlPlaneAccess = new getControlPlaneAccess(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/controlPlaneAccess")).addQueryStringParameters("name" -> name.toString))
			given Conversion[getControlPlaneAccess, Future[Schema.GoogleCloudApigeeV1ControlPlaneAccess]] = (fun: getControlPlaneAccess) => fun.apply()
		}
		class setAddons(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withGoogleCloudApigeeV1SetAddonsRequest(body: Schema.GoogleCloudApigeeV1SetAddonsRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleLongrunningOperation])
		}
		object setAddons {
			def apply(organizationsId :PlayApi, org: String)(using auth: AuthToken, ec: ExecutionContext): setAddons = new setAddons(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}:setAddons")).addQueryStringParameters("org" -> org.toString))
		}
		class getSyncAuthorization(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withGoogleCloudApigeeV1GetSyncAuthorizationRequest(body: Schema.GoogleCloudApigeeV1GetSyncAuthorizationRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleCloudApigeeV1SyncAuthorization])
		}
		object getSyncAuthorization {
			def apply(organizationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): getSyncAuthorization = new getSyncAuthorization(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}:getSyncAuthorization")).addQueryStringParameters("name" -> name.toString))
		}
		class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningOperation]) {
			/** Optional. This setting is applicable only for organizations that are soft-deleted (i.e., BillingType is not EVALUATION). It controls how long Organization data will be retained after the initial delete operation completes. During this period, the Organization may be restored to its last known state. After this period, the Organization will no longer be able to be restored. &#42;&#42;Note: During the data retention period specified using this field, the Apigee organization cannot be recreated in the same Google Cloud project.&#42;&#42;<br>Possible values:<br>DELETION_RETENTION_UNSPECIFIED: Default data retention setting of seven days will be applied.<br>MINIMUM: Organization data will be retained for the minimum period of 24 hours. */
			def withRetention(retention: String) = new delete(req.addQueryStringParameters("retention" -> retention.toString))
			def apply() = req.execute("DELETE").map(_.json.as[Schema.GoogleLongrunningOperation])
		}
		object delete {
			def apply(organizationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}")).addQueryStringParameters("name" -> name.toString))
			given Conversion[delete, Future[Schema.GoogleLongrunningOperation]] = (fun: delete) => fun.apply()
		}
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1Organization]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudApigeeV1Organization])
		}
		object get {
			def apply(organizationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}")).addQueryStringParameters("name" -> name.toString))
			given Conversion[get, Future[Schema.GoogleCloudApigeeV1Organization]] = (fun: get) => fun.apply()
		}
		class update(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withGoogleCloudApigeeV1Organization(body: Schema.GoogleCloudApigeeV1Organization) = req.withBody(Json.toJson(body)).execute("PUT").map(_.json.as[Schema.GoogleCloudApigeeV1Organization])
		}
		object update {
			def apply(organizationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): update = new update(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}")).addQueryStringParameters("name" -> name.toString))
		}
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1ListOrganizationsResponse]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudApigeeV1ListOrganizationsResponse])
		}
		object list {
			def apply(parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/organizations")).addQueryStringParameters("parent" -> parent.toString))
			given Conversion[list, Future[Schema.GoogleCloudApigeeV1ListOrganizationsResponse]] = (fun: list) => fun.apply()
		}
		object securityProfiles {
			class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGoogleCloudApigeeV1SecurityProfile(body: Schema.GoogleCloudApigeeV1SecurityProfile) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleCloudApigeeV1SecurityProfile])
			}
			object create {
				def apply(organizationsId :PlayApi, securityProfileId: String, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/securityProfiles")).addQueryStringParameters("securityProfileId" -> securityProfileId.toString, "parent" -> parent.toString))
			}
			class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
				def apply() = req.execute("DELETE").map(_.json.as[Schema.GoogleProtobufEmpty])
			}
			object delete {
				def apply(organizationsId :PlayApi, securityProfilesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/securityProfiles/${securityProfilesId}")).addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
			}
			class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGoogleCloudApigeeV1SecurityProfile(body: Schema.GoogleCloudApigeeV1SecurityProfile) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.GoogleCloudApigeeV1SecurityProfile])
			}
			object patch {
				def apply(organizationsId :PlayApi, securityProfilesId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/securityProfiles/${securityProfilesId}")).addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1ListSecurityProfilesResponse]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudApigeeV1ListSecurityProfilesResponse])
			}
			object list {
				def apply(organizationsId :PlayApi, pageSize: Int, parent: String, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/securityProfiles")).addQueryStringParameters("pageSize" -> pageSize.toString, "parent" -> parent.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.GoogleCloudApigeeV1ListSecurityProfilesResponse]] = (fun: list) => fun.apply()
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1SecurityProfile]) {
				/** Required. Security profile in the following format: `organizations/{org}/securityProfiles/{profile}'. Profile may optionally contain revision ID. If revision ID is not provided, the response will contain latest revision by default. Example: organizations/testOrg/securityProfiles/testProfile@5 */
				def withName(name: String) = new get(req.addQueryStringParameters("name" -> name.toString))
				def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudApigeeV1SecurityProfile])
			}
			object get {
				def apply(organizationsId :PlayApi, securityProfilesId :PlayApi)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/securityProfiles/${securityProfilesId}")).addQueryStringParameters())
				given Conversion[get, Future[Schema.GoogleCloudApigeeV1SecurityProfile]] = (fun: get) => fun.apply()
			}
			class listRevisions(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1ListSecurityProfileRevisionsResponse]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudApigeeV1ListSecurityProfileRevisionsResponse])
			}
			object listRevisions {
				def apply(organizationsId :PlayApi, securityProfilesId :PlayApi, pageToken: String, name: String, pageSize: Int)(using auth: AuthToken, ec: ExecutionContext): listRevisions = new listRevisions(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/securityProfiles/${securityProfilesId}:listRevisions")).addQueryStringParameters("pageToken" -> pageToken.toString, "name" -> name.toString, "pageSize" -> pageSize.toString))
				given Conversion[listRevisions, Future[Schema.GoogleCloudApigeeV1ListSecurityProfileRevisionsResponse]] = (fun: listRevisions) => fun.apply()
			}
			object environments {
				class computeEnvironmentScores(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudApigeeV1ComputeEnvironmentScoresRequest(body: Schema.GoogleCloudApigeeV1ComputeEnvironmentScoresRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleCloudApigeeV1ComputeEnvironmentScoresResponse])
				}
				object computeEnvironmentScores {
					def apply(organizationsId :PlayApi, securityProfilesId :PlayApi, environmentsId :PlayApi, profileEnvironment: String)(using auth: AuthToken, ec: ExecutionContext): computeEnvironmentScores = new computeEnvironmentScores(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/securityProfiles/${securityProfilesId}/environments/${environmentsId}:computeEnvironmentScores")).addQueryStringParameters("profileEnvironment" -> profileEnvironment.toString))
				}
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudApigeeV1SecurityProfileEnvironmentAssociation(body: Schema.GoogleCloudApigeeV1SecurityProfileEnvironmentAssociation) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleCloudApigeeV1SecurityProfileEnvironmentAssociation])
				}
				object create {
					def apply(organizationsId :PlayApi, securityProfilesId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/securityProfiles/${securityProfilesId}/environments")).addQueryStringParameters("parent" -> parent.toString))
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
					def apply() = req.execute("DELETE").map(_.json.as[Schema.GoogleProtobufEmpty])
				}
				object delete {
					def apply(organizationsId :PlayApi, securityProfilesId :PlayApi, environmentsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/securityProfiles/${securityProfilesId}/environments/${environmentsId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
				}
			}
		}
		object apis {
			class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGoogleApiHttpBody(body: Schema.GoogleApiHttpBody) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleCloudApigeeV1ApiProxyRevision])
			}
			object create {
				def apply(organizationsId :PlayApi, validate: Boolean, name: String, parent: String, action: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/apis")).addQueryStringParameters("validate" -> validate.toString, "name" -> name.toString, "parent" -> parent.toString, "action" -> action.toString))
			}
			class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1ApiProxy]) {
				def apply() = req.execute("DELETE").map(_.json.as[Schema.GoogleCloudApigeeV1ApiProxy])
			}
			object delete {
				def apply(organizationsId :PlayApi, apisId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/apis/${apisId}")).addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.GoogleCloudApigeeV1ApiProxy]] = (fun: delete) => fun.apply()
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1ApiProxy]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudApigeeV1ApiProxy])
			}
			object get {
				def apply(organizationsId :PlayApi, apisId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/apis/${apisId}")).addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.GoogleCloudApigeeV1ApiProxy]] = (fun: get) => fun.apply()
			}
			class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGoogleCloudApigeeV1ApiProxy(body: Schema.GoogleCloudApigeeV1ApiProxy) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.GoogleCloudApigeeV1ApiProxy])
			}
			object patch {
				def apply(organizationsId :PlayApi, apisId :PlayApi, updateMask: String, name: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/apis/${apisId}")).addQueryStringParameters("updateMask" -> updateMask.toString, "name" -> name.toString))
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1ListApiProxiesResponse]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudApigeeV1ListApiProxiesResponse])
			}
			object list {
				def apply(organizationsId :PlayApi, includeRevisions: Boolean, parent: String, includeMetaData: Boolean)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/apis")).addQueryStringParameters("includeRevisions" -> includeRevisions.toString, "parent" -> parent.toString, "includeMetaData" -> includeMetaData.toString))
				given Conversion[list, Future[Schema.GoogleCloudApigeeV1ListApiProxiesResponse]] = (fun: list) => fun.apply()
			}
			object keyvaluemaps {
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1KeyValueMap]) {
					def apply() = req.execute("DELETE").map(_.json.as[Schema.GoogleCloudApigeeV1KeyValueMap])
				}
				object delete {
					def apply(organizationsId :PlayApi, apisId :PlayApi, keyvaluemapsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/apis/${apisId}/keyvaluemaps/${keyvaluemapsId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.GoogleCloudApigeeV1KeyValueMap]] = (fun: delete) => fun.apply()
				}
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudApigeeV1KeyValueMap(body: Schema.GoogleCloudApigeeV1KeyValueMap) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleCloudApigeeV1KeyValueMap])
				}
				object create {
					def apply(organizationsId :PlayApi, apisId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/apis/${apisId}/keyvaluemaps")).addQueryStringParameters("parent" -> parent.toString))
				}
				object entries {
					class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleCloudApigeeV1KeyValueEntry(body: Schema.GoogleCloudApigeeV1KeyValueEntry) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleCloudApigeeV1KeyValueEntry])
					}
					object create {
						def apply(organizationsId :PlayApi, apisId :PlayApi, keyvaluemapsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/apis/${apisId}/keyvaluemaps/${keyvaluemapsId}/entries")).addQueryStringParameters("parent" -> parent.toString))
					}
					class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1KeyValueEntry]) {
						def apply() = req.execute("DELETE").map(_.json.as[Schema.GoogleCloudApigeeV1KeyValueEntry])
					}
					object delete {
						def apply(organizationsId :PlayApi, apisId :PlayApi, keyvaluemapsId :PlayApi, entriesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/apis/${apisId}/keyvaluemaps/${keyvaluemapsId}/entries/${entriesId}")).addQueryStringParameters("name" -> name.toString))
						given Conversion[delete, Future[Schema.GoogleCloudApigeeV1KeyValueEntry]] = (fun: delete) => fun.apply()
					}
					class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1KeyValueEntry]) {
						def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudApigeeV1KeyValueEntry])
					}
					object get {
						def apply(organizationsId :PlayApi, apisId :PlayApi, keyvaluemapsId :PlayApi, entriesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/apis/${apisId}/keyvaluemaps/${keyvaluemapsId}/entries/${entriesId}")).addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.GoogleCloudApigeeV1KeyValueEntry]] = (fun: get) => fun.apply()
					}
					class update(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleCloudApigeeV1KeyValueEntry(body: Schema.GoogleCloudApigeeV1KeyValueEntry) = req.withBody(Json.toJson(body)).execute("PUT").map(_.json.as[Schema.GoogleCloudApigeeV1KeyValueEntry])
					}
					object update {
						def apply(organizationsId :PlayApi, apisId :PlayApi, keyvaluemapsId :PlayApi, entriesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): update = new update(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/apis/${apisId}/keyvaluemaps/${keyvaluemapsId}/entries/${entriesId}")).addQueryStringParameters("name" -> name.toString))
					}
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1ListKeyValueEntriesResponse]) {
						/** Optional. Maximum number of key value entries to return. If unspecified, at most 100 entries will be returned.<br>Format: int32 */
						def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
						/** Optional. Page token. If provides, must be a valid key value entry returned from a previous call that can be used to retrieve the next page. */
						def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
						def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudApigeeV1ListKeyValueEntriesResponse])
					}
					object list {
						def apply(organizationsId :PlayApi, apisId :PlayApi, keyvaluemapsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/apis/${apisId}/keyvaluemaps/${keyvaluemapsId}/entries")).addQueryStringParameters("parent" -> parent.toString))
						given Conversion[list, Future[Schema.GoogleCloudApigeeV1ListKeyValueEntriesResponse]] = (fun: list) => fun.apply()
					}
				}
			}
			object deployments {
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1ListDeploymentsResponse]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudApigeeV1ListDeploymentsResponse])
				}
				object list {
					def apply(organizationsId :PlayApi, apisId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/apis/${apisId}/deployments")).addQueryStringParameters("parent" -> parent.toString))
					given Conversion[list, Future[Schema.GoogleCloudApigeeV1ListDeploymentsResponse]] = (fun: list) => fun.apply()
				}
			}
			object revisions {
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1ApiProxyRevision]) {
					def apply() = req.execute("DELETE").map(_.json.as[Schema.GoogleCloudApigeeV1ApiProxyRevision])
				}
				object delete {
					def apply(organizationsId :PlayApi, apisId :PlayApi, revisionsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/apis/${apisId}/revisions/${revisionsId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.GoogleCloudApigeeV1ApiProxyRevision]] = (fun: delete) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleApiHttpBody]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.GoogleApiHttpBody])
				}
				object get {
					def apply(organizationsId :PlayApi, apisId :PlayApi, revisionsId :PlayApi, name: String, format: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/apis/${apisId}/revisions/${revisionsId}")).addQueryStringParameters("name" -> name.toString, "format" -> format.toString))
					given Conversion[get, Future[Schema.GoogleApiHttpBody]] = (fun: get) => fun.apply()
				}
				class updateApiProxyRevision(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleApiHttpBody(body: Schema.GoogleApiHttpBody) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleCloudApigeeV1ApiProxyRevision])
				}
				object updateApiProxyRevision {
					def apply(organizationsId :PlayApi, apisId :PlayApi, revisionsId :PlayApi, validate: Boolean, name: String)(using auth: AuthToken, ec: ExecutionContext): updateApiProxyRevision = new updateApiProxyRevision(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/apis/${apisId}/revisions/${revisionsId}")).addQueryStringParameters("validate" -> validate.toString, "name" -> name.toString))
				}
				object deployments {
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1ListDeploymentsResponse]) {
						def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudApigeeV1ListDeploymentsResponse])
					}
					object list {
						def apply(organizationsId :PlayApi, apisId :PlayApi, revisionsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/apis/${apisId}/revisions/${revisionsId}/deployments")).addQueryStringParameters("parent" -> parent.toString))
						given Conversion[list, Future[Schema.GoogleCloudApigeeV1ListDeploymentsResponse]] = (fun: list) => fun.apply()
					}
				}
			}
		}
		object endpointAttachments {
			class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGoogleCloudApigeeV1EndpointAttachment(body: Schema.GoogleCloudApigeeV1EndpointAttachment) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleLongrunningOperation])
			}
			object create {
				def apply(organizationsId :PlayApi, parent: String, endpointAttachmentId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/endpointAttachments")).addQueryStringParameters("parent" -> parent.toString, "endpointAttachmentId" -> endpointAttachmentId.toString))
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1EndpointAttachment]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudApigeeV1EndpointAttachment])
			}
			object get {
				def apply(organizationsId :PlayApi, endpointAttachmentsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/endpointAttachments/${endpointAttachmentsId}")).addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.GoogleCloudApigeeV1EndpointAttachment]] = (fun: get) => fun.apply()
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1ListEndpointAttachmentsResponse]) {
				/** Optional. Maximum number of endpoint attachments to return. If unspecified, at most 25 attachments will be returned.<br>Format: int32 */
				def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
				/** Optional. Page token, returned from a previous `ListEndpointAttachments` call, that you can use to retrieve the next page. */
				def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
				def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudApigeeV1ListEndpointAttachmentsResponse])
			}
			object list {
				def apply(organizationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/endpointAttachments")).addQueryStringParameters("parent" -> parent.toString))
				given Conversion[list, Future[Schema.GoogleCloudApigeeV1ListEndpointAttachmentsResponse]] = (fun: list) => fun.apply()
			}
			class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningOperation]) {
				def apply() = req.execute("DELETE").map(_.json.as[Schema.GoogleLongrunningOperation])
			}
			object delete {
				def apply(organizationsId :PlayApi, endpointAttachmentsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/endpointAttachments/${endpointAttachmentsId}")).addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.GoogleLongrunningOperation]] = (fun: delete) => fun.apply()
			}
		}
		object developers {
			class setDeveloperStatus(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
				def apply() = req.execute("POST").map(_.json.as[Schema.GoogleProtobufEmpty])
			}
			object setDeveloperStatus {
				def apply(organizationsId :PlayApi, developersId :PlayApi, name: String, action: String)(using auth: AuthToken, ec: ExecutionContext): setDeveloperStatus = new setDeveloperStatus(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/developers/${developersId}")).addQueryStringParameters("name" -> name.toString, "action" -> action.toString))
				given Conversion[setDeveloperStatus, Future[Schema.GoogleProtobufEmpty]] = (fun: setDeveloperStatus) => fun.apply()
			}
			class updateMonetizationConfig(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGoogleCloudApigeeV1DeveloperMonetizationConfig(body: Schema.GoogleCloudApigeeV1DeveloperMonetizationConfig) = req.withBody(Json.toJson(body)).execute("PUT").map(_.json.as[Schema.GoogleCloudApigeeV1DeveloperMonetizationConfig])
			}
			object updateMonetizationConfig {
				def apply(organizationsId :PlayApi, developersId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): updateMonetizationConfig = new updateMonetizationConfig(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/developers/${developersId}/monetizationConfig")).addQueryStringParameters("name" -> name.toString))
			}
			class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGoogleCloudApigeeV1Developer(body: Schema.GoogleCloudApigeeV1Developer) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleCloudApigeeV1Developer])
			}
			object create {
				def apply(organizationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/developers")).addQueryStringParameters("parent" -> parent.toString))
			}
			class attributes(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGoogleCloudApigeeV1Attributes(body: Schema.GoogleCloudApigeeV1Attributes) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleCloudApigeeV1Attributes])
			}
			object attributes {
				def apply(organizationsId :PlayApi, developersId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): attributes = new attributes(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/developers/${developersId}/attributes")).addQueryStringParameters("parent" -> parent.toString))
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1Attribute]) {
					def apply() = req.execute("DELETE").map(_.json.as[Schema.GoogleCloudApigeeV1Attribute])
				}
				object delete {
					def apply(organizationsId :PlayApi, developersId :PlayApi, attributesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/developers/${developersId}/attributes/${attributesId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.GoogleCloudApigeeV1Attribute]] = (fun: delete) => fun.apply()
				}
				class updateDeveloperAttribute(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudApigeeV1Attribute(body: Schema.GoogleCloudApigeeV1Attribute) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleCloudApigeeV1Attribute])
				}
				object updateDeveloperAttribute {
					def apply(organizationsId :PlayApi, developersId :PlayApi, attributesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): updateDeveloperAttribute = new updateDeveloperAttribute(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/developers/${developersId}/attributes/${attributesId}")).addQueryStringParameters("name" -> name.toString))
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1Attributes]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudApigeeV1Attributes])
				}
				object list {
					def apply(organizationsId :PlayApi, developersId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/developers/${developersId}/attributes")).addQueryStringParameters("parent" -> parent.toString))
					given Conversion[list, Future[Schema.GoogleCloudApigeeV1Attributes]] = (fun: list) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1Attribute]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudApigeeV1Attribute])
				}
				object get {
					def apply(organizationsId :PlayApi, developersId :PlayApi, attributesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/developers/${developersId}/attributes/${attributesId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleCloudApigeeV1Attribute]] = (fun: get) => fun.apply()
				}
			}
			class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1Developer]) {
				def apply() = req.execute("DELETE").map(_.json.as[Schema.GoogleCloudApigeeV1Developer])
			}
			object delete {
				def apply(organizationsId :PlayApi, developersId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/developers/${developersId}")).addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.GoogleCloudApigeeV1Developer]] = (fun: delete) => fun.apply()
			}
			class getMonetizationConfig(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1DeveloperMonetizationConfig]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudApigeeV1DeveloperMonetizationConfig])
			}
			object getMonetizationConfig {
				def apply(organizationsId :PlayApi, developersId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): getMonetizationConfig = new getMonetizationConfig(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/developers/${developersId}/monetizationConfig")).addQueryStringParameters("name" -> name.toString))
				given Conversion[getMonetizationConfig, Future[Schema.GoogleCloudApigeeV1DeveloperMonetizationConfig]] = (fun: getMonetizationConfig) => fun.apply()
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1Developer]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudApigeeV1Developer])
			}
			object get {
				def apply(organizationsId :PlayApi, developersId :PlayApi, name: String, action: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/developers/${developersId}")).addQueryStringParameters("name" -> name.toString, "action" -> action.toString))
				given Conversion[get, Future[Schema.GoogleCloudApigeeV1Developer]] = (fun: get) => fun.apply()
			}
			class update(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGoogleCloudApigeeV1Developer(body: Schema.GoogleCloudApigeeV1Developer) = req.withBody(Json.toJson(body)).execute("PUT").map(_.json.as[Schema.GoogleCloudApigeeV1Developer])
			}
			object update {
				def apply(organizationsId :PlayApi, developersId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): update = new update(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/developers/${developersId}")).addQueryStringParameters("name" -> name.toString))
			}
			class getBalance(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1DeveloperBalance]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudApigeeV1DeveloperBalance])
			}
			object getBalance {
				def apply(organizationsId :PlayApi, developersId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): getBalance = new getBalance(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/developers/${developersId}/balance")).addQueryStringParameters("name" -> name.toString))
				given Conversion[getBalance, Future[Schema.GoogleCloudApigeeV1DeveloperBalance]] = (fun: getBalance) => fun.apply()
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1ListOfDevelopersResponse]) {
				/** Optional. List of IDs to include, separated by commas. */
				def withIds(ids: String) = new list(req.addQueryStringParameters("ids" -> ids.toString))
				/** Optional. Number of developers to return in the API call. Use with the `startKey` parameter to provide more targeted filtering. The limit is 1000.<br>Format: int64 */
				def withCount(count: String) = new list(req.addQueryStringParameters("count" -> count.toString))
				/** Optional. List only Developers that are associated with the app. Note that start_key, count are not applicable for this filter criteria. */
				def withApp(app: String) = new list(req.addQueryStringParameters("app" -> app.toString))
				def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudApigeeV1ListOfDevelopersResponse])
			}
			object list {
				def apply(organizationsId :PlayApi, includeCompany: Boolean, expand: Boolean, parent: String, startKey: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/developers")).addQueryStringParameters("includeCompany" -> includeCompany.toString, "expand" -> expand.toString, "parent" -> parent.toString, "startKey" -> startKey.toString))
				given Conversion[list, Future[Schema.GoogleCloudApigeeV1ListOfDevelopersResponse]] = (fun: list) => fun.apply()
			}
			object balance {
				class credit(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudApigeeV1CreditDeveloperBalanceRequest(body: Schema.GoogleCloudApigeeV1CreditDeveloperBalanceRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleCloudApigeeV1DeveloperBalance])
				}
				object credit {
					def apply(organizationsId :PlayApi, developersId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): credit = new credit(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/developers/${developersId}/balance:credit")).addQueryStringParameters("name" -> name.toString))
				}
				class adjust(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudApigeeV1AdjustDeveloperBalanceRequest(body: Schema.GoogleCloudApigeeV1AdjustDeveloperBalanceRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleCloudApigeeV1DeveloperBalance])
				}
				object adjust {
					def apply(organizationsId :PlayApi, developersId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): adjust = new adjust(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/developers/${developersId}/balance:adjust")).addQueryStringParameters("name" -> name.toString))
				}
			}
			object subscriptions {
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudApigeeV1DeveloperSubscription(body: Schema.GoogleCloudApigeeV1DeveloperSubscription) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleCloudApigeeV1DeveloperSubscription])
				}
				object create {
					def apply(organizationsId :PlayApi, developersId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/developers/${developersId}/subscriptions")).addQueryStringParameters("parent" -> parent.toString))
				}
				class expire(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudApigeeV1ExpireDeveloperSubscriptionRequest(body: Schema.GoogleCloudApigeeV1ExpireDeveloperSubscriptionRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleCloudApigeeV1DeveloperSubscription])
				}
				object expire {
					def apply(organizationsId :PlayApi, developersId :PlayApi, subscriptionsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): expire = new expire(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/developers/${developersId}/subscriptions/${subscriptionsId}:expire")).addQueryStringParameters("name" -> name.toString))
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1DeveloperSubscription]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudApigeeV1DeveloperSubscription])
				}
				object get {
					def apply(organizationsId :PlayApi, developersId :PlayApi, subscriptionsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/developers/${developersId}/subscriptions/${subscriptionsId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleCloudApigeeV1DeveloperSubscription]] = (fun: get) => fun.apply()
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1ListDeveloperSubscriptionsResponse]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudApigeeV1ListDeveloperSubscriptionsResponse])
				}
				object list {
					def apply(organizationsId :PlayApi, developersId :PlayApi, count: Int, startKey: String, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/developers/${developersId}/subscriptions")).addQueryStringParameters("count" -> count.toString, "startKey" -> startKey.toString, "parent" -> parent.toString))
					given Conversion[list, Future[Schema.GoogleCloudApigeeV1ListDeveloperSubscriptionsResponse]] = (fun: list) => fun.apply()
				}
			}
			object apps {
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudApigeeV1DeveloperApp(body: Schema.GoogleCloudApigeeV1DeveloperApp) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleCloudApigeeV1DeveloperApp])
				}
				object create {
					def apply(organizationsId :PlayApi, developersId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/developers/${developersId}/apps")).addQueryStringParameters("parent" -> parent.toString))
				}
				class attributes(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudApigeeV1Attributes(body: Schema.GoogleCloudApigeeV1Attributes) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleCloudApigeeV1Attributes])
				}
				object attributes {
					def apply(organizationsId :PlayApi, developersId :PlayApi, appsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): attributes = new attributes(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/developers/${developersId}/apps/${appsId}/attributes")).addQueryStringParameters("name" -> name.toString))
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1Attributes]) {
						def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudApigeeV1Attributes])
					}
					object list {
						def apply(organizationsId :PlayApi, developersId :PlayApi, appsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/developers/${developersId}/apps/${appsId}/attributes")).addQueryStringParameters("parent" -> parent.toString))
						given Conversion[list, Future[Schema.GoogleCloudApigeeV1Attributes]] = (fun: list) => fun.apply()
					}
					class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1Attribute]) {
						def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudApigeeV1Attribute])
					}
					object get {
						def apply(organizationsId :PlayApi, developersId :PlayApi, appsId :PlayApi, attributesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/developers/${developersId}/apps/${appsId}/attributes/${attributesId}")).addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.GoogleCloudApigeeV1Attribute]] = (fun: get) => fun.apply()
					}
					class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1Attribute]) {
						def apply() = req.execute("DELETE").map(_.json.as[Schema.GoogleCloudApigeeV1Attribute])
					}
					object delete {
						def apply(organizationsId :PlayApi, developersId :PlayApi, appsId :PlayApi, attributesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/developers/${developersId}/apps/${appsId}/attributes/${attributesId}")).addQueryStringParameters("name" -> name.toString))
						given Conversion[delete, Future[Schema.GoogleCloudApigeeV1Attribute]] = (fun: delete) => fun.apply()
					}
					class updateDeveloperAppAttribute(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleCloudApigeeV1Attribute(body: Schema.GoogleCloudApigeeV1Attribute) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleCloudApigeeV1Attribute])
					}
					object updateDeveloperAppAttribute {
						def apply(organizationsId :PlayApi, developersId :PlayApi, appsId :PlayApi, attributesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): updateDeveloperAppAttribute = new updateDeveloperAppAttribute(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/developers/${developersId}/apps/${appsId}/attributes/${attributesId}")).addQueryStringParameters("name" -> name.toString))
					}
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1DeveloperApp]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudApigeeV1DeveloperApp])
				}
				object get {
					def apply(organizationsId :PlayApi, developersId :PlayApi, appsId :PlayApi, entity: String, query: String, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/developers/${developersId}/apps/${appsId}")).addQueryStringParameters("entity" -> entity.toString, "query" -> query.toString, "name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleCloudApigeeV1DeveloperApp]] = (fun: get) => fun.apply()
				}
				class update(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudApigeeV1DeveloperApp(body: Schema.GoogleCloudApigeeV1DeveloperApp) = req.withBody(Json.toJson(body)).execute("PUT").map(_.json.as[Schema.GoogleCloudApigeeV1DeveloperApp])
				}
				object update {
					def apply(organizationsId :PlayApi, developersId :PlayApi, appsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): update = new update(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/developers/${developersId}/apps/${appsId}")).addQueryStringParameters("name" -> name.toString))
				}
				class generateKeyPairOrUpdateDeveloperAppStatus(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudApigeeV1DeveloperApp(body: Schema.GoogleCloudApigeeV1DeveloperApp) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleCloudApigeeV1DeveloperApp])
				}
				object generateKeyPairOrUpdateDeveloperAppStatus {
					def apply(organizationsId :PlayApi, developersId :PlayApi, appsId :PlayApi, action: String, name: String)(using auth: AuthToken, ec: ExecutionContext): generateKeyPairOrUpdateDeveloperAppStatus = new generateKeyPairOrUpdateDeveloperAppStatus(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/developers/${developersId}/apps/${appsId}")).addQueryStringParameters("action" -> action.toString, "name" -> name.toString))
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1DeveloperApp]) {
					def apply() = req.execute("DELETE").map(_.json.as[Schema.GoogleCloudApigeeV1DeveloperApp])
				}
				object delete {
					def apply(organizationsId :PlayApi, developersId :PlayApi, appsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/developers/${developersId}/apps/${appsId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.GoogleCloudApigeeV1DeveloperApp]] = (fun: delete) => fun.apply()
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1ListDeveloperAppsResponse]) {
					/** Optional. Specifies whether to expand the results in shallow mode. Set to `true` to expand the results in shallow mode. */
					def withShallowExpand(shallowExpand: Boolean) = new list(req.addQueryStringParameters("shallowExpand" -> shallowExpand.toString))
					/** Optional. Specifies whether to expand the results. Set to `true` to expand the results. This query parameter is not valid if you use the `count` or `startKey` query parameters. */
					def withExpand(expand: Boolean) = new list(req.addQueryStringParameters("expand" -> expand.toString))
					def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudApigeeV1ListDeveloperAppsResponse])
				}
				object list {
					def apply(organizationsId :PlayApi, developersId :PlayApi, count: String, startKey: String, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/developers/${developersId}/apps")).addQueryStringParameters("count" -> count.toString, "startKey" -> startKey.toString, "parent" -> parent.toString))
					given Conversion[list, Future[Schema.GoogleCloudApigeeV1ListDeveloperAppsResponse]] = (fun: list) => fun.apply()
				}
				object keys {
					class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleCloudApigeeV1DeveloperAppKey(body: Schema.GoogleCloudApigeeV1DeveloperAppKey) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleCloudApigeeV1DeveloperAppKey])
					}
					object create {
						def apply(organizationsId :PlayApi, developersId :PlayApi, appsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/developers/${developersId}/apps/${appsId}/keys")).addQueryStringParameters("parent" -> parent.toString))
						class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
							def withGoogleCloudApigeeV1DeveloperAppKey(body: Schema.GoogleCloudApigeeV1DeveloperAppKey) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleCloudApigeeV1DeveloperAppKey])
						}
						object create {
							def apply(organizationsId :PlayApi, developersId :PlayApi, appsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/developers/${developersId}/apps/${appsId}/keys/create")).addQueryStringParameters("parent" -> parent.toString))
						}
					}
					class replaceDeveloperAppKey(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleCloudApigeeV1DeveloperAppKey(body: Schema.GoogleCloudApigeeV1DeveloperAppKey) = req.withBody(Json.toJson(body)).execute("PUT").map(_.json.as[Schema.GoogleCloudApigeeV1DeveloperAppKey])
					}
					object replaceDeveloperAppKey {
						def apply(organizationsId :PlayApi, developersId :PlayApi, appsId :PlayApi, keysId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): replaceDeveloperAppKey = new replaceDeveloperAppKey(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/developers/${developersId}/apps/${appsId}/keys/${keysId}")).addQueryStringParameters("name" -> name.toString))
					}
					class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1DeveloperAppKey]) {
						def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudApigeeV1DeveloperAppKey])
					}
					object get {
						def apply(organizationsId :PlayApi, developersId :PlayApi, appsId :PlayApi, keysId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/developers/${developersId}/apps/${appsId}/keys/${keysId}")).addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.GoogleCloudApigeeV1DeveloperAppKey]] = (fun: get) => fun.apply()
					}
					class updateDeveloperAppKey(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleCloudApigeeV1DeveloperAppKey(body: Schema.GoogleCloudApigeeV1DeveloperAppKey) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleCloudApigeeV1DeveloperAppKey])
					}
					object updateDeveloperAppKey {
						def apply(organizationsId :PlayApi, developersId :PlayApi, appsId :PlayApi, keysId :PlayApi, name: String, action: String)(using auth: AuthToken, ec: ExecutionContext): updateDeveloperAppKey = new updateDeveloperAppKey(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/developers/${developersId}/apps/${appsId}/keys/${keysId}")).addQueryStringParameters("name" -> name.toString, "action" -> action.toString))
					}
					class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1DeveloperAppKey]) {
						def apply() = req.execute("DELETE").map(_.json.as[Schema.GoogleCloudApigeeV1DeveloperAppKey])
					}
					object delete {
						def apply(organizationsId :PlayApi, developersId :PlayApi, appsId :PlayApi, keysId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/developers/${developersId}/apps/${appsId}/keys/${keysId}")).addQueryStringParameters("name" -> name.toString))
						given Conversion[delete, Future[Schema.GoogleCloudApigeeV1DeveloperAppKey]] = (fun: delete) => fun.apply()
					}
					object apiproducts {
						class updateDeveloperAppKeyApiProduct(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
							def apply() = req.execute("POST").map(_.json.as[Schema.GoogleProtobufEmpty])
						}
						object updateDeveloperAppKeyApiProduct {
							def apply(organizationsId :PlayApi, developersId :PlayApi, appsId :PlayApi, keysId :PlayApi, apiproductsId :PlayApi, name: String, action: String)(using auth: AuthToken, ec: ExecutionContext): updateDeveloperAppKeyApiProduct = new updateDeveloperAppKeyApiProduct(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/developers/${developersId}/apps/${appsId}/keys/${keysId}/apiproducts/${apiproductsId}")).addQueryStringParameters("name" -> name.toString, "action" -> action.toString))
							given Conversion[updateDeveloperAppKeyApiProduct, Future[Schema.GoogleProtobufEmpty]] = (fun: updateDeveloperAppKeyApiProduct) => fun.apply()
						}
						class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1DeveloperAppKey]) {
							def apply() = req.execute("DELETE").map(_.json.as[Schema.GoogleCloudApigeeV1DeveloperAppKey])
						}
						object delete {
							def apply(organizationsId :PlayApi, developersId :PlayApi, appsId :PlayApi, keysId :PlayApi, apiproductsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/developers/${developersId}/apps/${appsId}/keys/${keysId}/apiproducts/${apiproductsId}")).addQueryStringParameters("name" -> name.toString))
							given Conversion[delete, Future[Schema.GoogleCloudApigeeV1DeveloperAppKey]] = (fun: delete) => fun.apply()
						}
					}
				}
			}
		}
		object environments {
			class testIamPermissions(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGoogleIamV1TestIamPermissionsRequest(body: Schema.GoogleIamV1TestIamPermissionsRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleIamV1TestIamPermissionsResponse])
			}
			object testIamPermissions {
				def apply(organizationsId :PlayApi, environmentsId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): testIamPermissions = new testIamPermissions(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}:testIamPermissions")).addQueryStringParameters("resource" -> resource.toString))
			}
			class getDeployedConfig(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1EnvironmentConfig]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudApigeeV1EnvironmentConfig])
			}
			object getDeployedConfig {
				def apply(organizationsId :PlayApi, environmentsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): getDeployedConfig = new getDeployedConfig(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/deployedConfig")).addQueryStringParameters("name" -> name.toString))
				given Conversion[getDeployedConfig, Future[Schema.GoogleCloudApigeeV1EnvironmentConfig]] = (fun: getDeployedConfig) => fun.apply()
			}
			class getAddonsConfig(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1AddonsConfig]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudApigeeV1AddonsConfig])
			}
			object getAddonsConfig {
				def apply(organizationsId :PlayApi, environmentsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): getAddonsConfig = new getAddonsConfig(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/addonsConfig")).addQueryStringParameters("name" -> name.toString))
				given Conversion[getAddonsConfig, Future[Schema.GoogleCloudApigeeV1AddonsConfig]] = (fun: getAddonsConfig) => fun.apply()
			}
			class unsubscribe(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGoogleCloudApigeeV1Subscription(body: Schema.GoogleCloudApigeeV1Subscription) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleProtobufEmpty])
			}
			object unsubscribe {
				def apply(organizationsId :PlayApi, environmentsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): unsubscribe = new unsubscribe(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}:unsubscribe")).addQueryStringParameters("parent" -> parent.toString))
			}
			class updateDebugmask(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGoogleCloudApigeeV1DebugMask(body: Schema.GoogleCloudApigeeV1DebugMask) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.GoogleCloudApigeeV1DebugMask])
			}
			object updateDebugmask {
				def apply(organizationsId :PlayApi, environmentsId :PlayApi, name: String, replaceRepeatedFields: Boolean, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): updateDebugmask = new updateDebugmask(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/debugmask")).addQueryStringParameters("name" -> name.toString, "replaceRepeatedFields" -> replaceRepeatedFields.toString, "updateMask" -> updateMask.toString))
			}
			class modifyEnvironment(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGoogleCloudApigeeV1Environment(body: Schema.GoogleCloudApigeeV1Environment) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.GoogleLongrunningOperation])
			}
			object modifyEnvironment {
				def apply(organizationsId :PlayApi, environmentsId :PlayApi, updateMask: String, name: String)(using auth: AuthToken, ec: ExecutionContext): modifyEnvironment = new modifyEnvironment(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}")).addQueryStringParameters("updateMask" -> updateMask.toString, "name" -> name.toString))
			}
			class updateEnvironment(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGoogleCloudApigeeV1Environment(body: Schema.GoogleCloudApigeeV1Environment) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleCloudApigeeV1Environment])
			}
			object updateEnvironment {
				def apply(organizationsId :PlayApi, environmentsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): updateEnvironment = new updateEnvironment(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}")).addQueryStringParameters("name" -> name.toString))
			}
			class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				/** Optional. Name of the environment. */
				def withName(name: String) = new create(req.addQueryStringParameters("name" -> name.toString))
				def withGoogleCloudApigeeV1Environment(body: Schema.GoogleCloudApigeeV1Environment) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleLongrunningOperation])
			}
			object create {
				def apply(organizationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments")).addQueryStringParameters("parent" -> parent.toString))
			}
			class setIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGoogleIamV1SetIamPolicyRequest(body: Schema.GoogleIamV1SetIamPolicyRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleIamV1Policy])
			}
			object setIamPolicy {
				def apply(organizationsId :PlayApi, environmentsId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): setIamPolicy = new setIamPolicy(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}:setIamPolicy")).addQueryStringParameters("resource" -> resource.toString))
			}
			class subscribe(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1Subscription]) {
				def apply() = req.execute("POST").map(_.json.as[Schema.GoogleCloudApigeeV1Subscription])
			}
			object subscribe {
				def apply(organizationsId :PlayApi, environmentsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): subscribe = new subscribe(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}:subscribe")).addQueryStringParameters("parent" -> parent.toString))
				given Conversion[subscribe, Future[Schema.GoogleCloudApigeeV1Subscription]] = (fun: subscribe) => fun.apply()
			}
			class getTraceConfig(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1TraceConfig]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudApigeeV1TraceConfig])
			}
			object getTraceConfig {
				def apply(organizationsId :PlayApi, environmentsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): getTraceConfig = new getTraceConfig(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/traceConfig")).addQueryStringParameters("name" -> name.toString))
				given Conversion[getTraceConfig, Future[Schema.GoogleCloudApigeeV1TraceConfig]] = (fun: getTraceConfig) => fun.apply()
			}
			class updateTraceConfig(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGoogleCloudApigeeV1TraceConfig(body: Schema.GoogleCloudApigeeV1TraceConfig) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.GoogleCloudApigeeV1TraceConfig])
			}
			object updateTraceConfig {
				def apply(organizationsId :PlayApi, environmentsId :PlayApi, updateMask: String, name: String)(using auth: AuthToken, ec: ExecutionContext): updateTraceConfig = new updateTraceConfig(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/traceConfig")).addQueryStringParameters("updateMask" -> updateMask.toString, "name" -> name.toString))
			}
			class getIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleIamV1Policy]) {
				/** Optional. The maximum policy version that will be used to format the policy. Valid values are 0, 1, and 3. Requests specifying an invalid value will be rejected. Requests for policies with any conditional role bindings must specify version 3. Policies with no conditional role bindings may specify any valid value or leave the field unset. The policy in the response might use the policy version that you specified, or it might use a lower policy version. For example, if you specify version 3, but the policy has no conditional role bindings, the response uses version 1. To learn which resources support conditions in their IAM policies, see the [IAM documentation](https://cloud.google.com/iam/help/conditions/resource-policies).<br>Format: int32 */
				def withOptionsRequestedPolicyVersion(optionsRequestedPolicyVersion: Int) = new getIamPolicy(req.addQueryStringParameters("options.requestedPolicyVersion" -> optionsRequestedPolicyVersion.toString))
				def apply() = req.execute("GET").map(_.json.as[Schema.GoogleIamV1Policy])
			}
			object getIamPolicy {
				def apply(organizationsId :PlayApi, environmentsId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): getIamPolicy = new getIamPolicy(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}:getIamPolicy")).addQueryStringParameters("resource" -> resource.toString))
				given Conversion[getIamPolicy, Future[Schema.GoogleIamV1Policy]] = (fun: getIamPolicy) => fun.apply()
			}
			class getDebugmask(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1DebugMask]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudApigeeV1DebugMask])
			}
			object getDebugmask {
				def apply(organizationsId :PlayApi, environmentsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): getDebugmask = new getDebugmask(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/debugmask")).addQueryStringParameters("name" -> name.toString))
				given Conversion[getDebugmask, Future[Schema.GoogleCloudApigeeV1DebugMask]] = (fun: getDebugmask) => fun.apply()
			}
			class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningOperation]) {
				def apply() = req.execute("DELETE").map(_.json.as[Schema.GoogleLongrunningOperation])
			}
			object delete {
				def apply(organizationsId :PlayApi, environmentsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}")).addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.GoogleLongrunningOperation]] = (fun: delete) => fun.apply()
			}
			class getApiSecurityRuntimeConfig(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1ApiSecurityRuntimeConfig]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudApigeeV1ApiSecurityRuntimeConfig])
			}
			object getApiSecurityRuntimeConfig {
				def apply(organizationsId :PlayApi, environmentsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): getApiSecurityRuntimeConfig = new getApiSecurityRuntimeConfig(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/apiSecurityRuntimeConfig")).addQueryStringParameters("name" -> name.toString))
				given Conversion[getApiSecurityRuntimeConfig, Future[Schema.GoogleCloudApigeeV1ApiSecurityRuntimeConfig]] = (fun: getApiSecurityRuntimeConfig) => fun.apply()
			}
			class getSecurityActionsConfig(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1SecurityActionsConfig]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudApigeeV1SecurityActionsConfig])
			}
			object getSecurityActionsConfig {
				def apply(organizationsId :PlayApi, environmentsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): getSecurityActionsConfig = new getSecurityActionsConfig(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/securityActionsConfig")).addQueryStringParameters("name" -> name.toString))
				given Conversion[getSecurityActionsConfig, Future[Schema.GoogleCloudApigeeV1SecurityActionsConfig]] = (fun: getSecurityActionsConfig) => fun.apply()
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1Environment]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudApigeeV1Environment])
			}
			object get {
				def apply(organizationsId :PlayApi, environmentsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}")).addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.GoogleCloudApigeeV1Environment]] = (fun: get) => fun.apply()
			}
			class update(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGoogleCloudApigeeV1Environment(body: Schema.GoogleCloudApigeeV1Environment) = req.withBody(Json.toJson(body)).execute("PUT").map(_.json.as[Schema.GoogleCloudApigeeV1Environment])
			}
			object update {
				def apply(organizationsId :PlayApi, environmentsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): update = new update(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}")).addQueryStringParameters("name" -> name.toString))
			}
			class updateSecurityActionsConfig(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGoogleCloudApigeeV1SecurityActionsConfig(body: Schema.GoogleCloudApigeeV1SecurityActionsConfig) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.GoogleCloudApigeeV1SecurityActionsConfig])
			}
			object updateSecurityActionsConfig {
				def apply(organizationsId :PlayApi, environmentsId :PlayApi, updateMask: String, name: String)(using auth: AuthToken, ec: ExecutionContext): updateSecurityActionsConfig = new updateSecurityActionsConfig(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/securityActionsConfig")).addQueryStringParameters("updateMask" -> updateMask.toString, "name" -> name.toString))
			}
			object stats {
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1Stats]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudApigeeV1Stats])
				}
				object get {
					def apply(organizationsId :PlayApi, environmentsId :PlayApi, statsId :PlayApi, sortby: String, filter: String, topk: String, aggTable: String, sonar: Boolean, tzo: String, limit: String, name: String, sort: String, timeUnit: String, offset: String, select: String, realtime: Boolean, tsAscending: Boolean, timeRange: String, accuracy: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/stats/${statsId}")).addQueryStringParameters("sortby" -> sortby.toString, "filter" -> filter.toString, "topk" -> topk.toString, "aggTable" -> aggTable.toString, "sonar" -> sonar.toString, "tzo" -> tzo.toString, "limit" -> limit.toString, "name" -> name.toString, "sort" -> sort.toString, "timeUnit" -> timeUnit.toString, "offset" -> offset.toString, "select" -> select.toString, "realtime" -> realtime.toString, "tsAscending" -> tsAscending.toString, "timeRange" -> timeRange.toString, "accuracy" -> accuracy.toString))
					given Conversion[get, Future[Schema.GoogleCloudApigeeV1Stats]] = (fun: get) => fun.apply()
				}
			}
			object queries {
				class getResult(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleApiHttpBody]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.GoogleApiHttpBody])
				}
				object getResult {
					def apply(organizationsId :PlayApi, environmentsId :PlayApi, queriesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): getResult = new getResult(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/queries/${queriesId}/result")).addQueryStringParameters("name" -> name.toString))
					given Conversion[getResult, Future[Schema.GoogleApiHttpBody]] = (fun: getResult) => fun.apply()
				}
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudApigeeV1Query(body: Schema.GoogleCloudApigeeV1Query) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleCloudApigeeV1AsyncQuery])
				}
				object create {
					def apply(organizationsId :PlayApi, environmentsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/queries")).addQueryStringParameters("parent" -> parent.toString))
				}
				class getResulturl(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1GetAsyncQueryResultUrlResponse]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudApigeeV1GetAsyncQueryResultUrlResponse])
				}
				object getResulturl {
					def apply(organizationsId :PlayApi, environmentsId :PlayApi, queriesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): getResulturl = new getResulturl(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/queries/${queriesId}/resulturl")).addQueryStringParameters("name" -> name.toString))
					given Conversion[getResulturl, Future[Schema.GoogleCloudApigeeV1GetAsyncQueryResultUrlResponse]] = (fun: getResulturl) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1AsyncQuery]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudApigeeV1AsyncQuery])
				}
				object get {
					def apply(organizationsId :PlayApi, environmentsId :PlayApi, queriesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/queries/${queriesId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleCloudApigeeV1AsyncQuery]] = (fun: get) => fun.apply()
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1ListAsyncQueriesResponse]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudApigeeV1ListAsyncQueriesResponse])
				}
				object list {
					def apply(organizationsId :PlayApi, environmentsId :PlayApi, status: String, submittedBy: String, to: String, from: String, inclQueriesWithoutReport: String, dataset: String, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/queries")).addQueryStringParameters("status" -> status.toString, "submittedBy" -> submittedBy.toString, "to" -> to.toString, "from" -> from.toString, "inclQueriesWithoutReport" -> inclQueriesWithoutReport.toString, "dataset" -> dataset.toString, "parent" -> parent.toString))
					given Conversion[list, Future[Schema.GoogleCloudApigeeV1ListAsyncQueriesResponse]] = (fun: list) => fun.apply()
				}
			}
			object securityStats {
				class queryTimeSeriesStats(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudApigeeV1QueryTimeSeriesStatsRequest(body: Schema.GoogleCloudApigeeV1QueryTimeSeriesStatsRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleCloudApigeeV1QueryTimeSeriesStatsResponse])
				}
				object queryTimeSeriesStats {
					def apply(organizationsId :PlayApi, environmentsId :PlayApi, orgenv: String)(using auth: AuthToken, ec: ExecutionContext): queryTimeSeriesStats = new queryTimeSeriesStats(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/securityStats:queryTimeSeriesStats")).addQueryStringParameters("orgenv" -> orgenv.toString))
				}
				class queryTabularStats(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudApigeeV1QueryTabularStatsRequest(body: Schema.GoogleCloudApigeeV1QueryTabularStatsRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleCloudApigeeV1QueryTabularStatsResponse])
				}
				object queryTabularStats {
					def apply(organizationsId :PlayApi, environmentsId :PlayApi, orgenv: String)(using auth: AuthToken, ec: ExecutionContext): queryTabularStats = new queryTabularStats(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/securityStats:queryTabularStats")).addQueryStringParameters("orgenv" -> orgenv.toString))
				}
			}
			object securityIncidents {
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1SecurityIncident]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudApigeeV1SecurityIncident])
				}
				object get {
					def apply(organizationsId :PlayApi, environmentsId :PlayApi, securityIncidentsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/securityIncidents/${securityIncidentsId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleCloudApigeeV1SecurityIncident]] = (fun: get) => fun.apply()
				}
				class batchUpdate(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					/** Optional. The parent resource shared by all security incidents being updated. If this is set, the parent field in the UpdateSecurityIncidentRequest messages must either be empty or match this field. */
					def withParent(parent: String) = new batchUpdate(req.addQueryStringParameters("parent" -> parent.toString))
					def withGoogleCloudApigeeV1BatchUpdateSecurityIncidentsRequest(body: Schema.GoogleCloudApigeeV1BatchUpdateSecurityIncidentsRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleCloudApigeeV1BatchUpdateSecurityIncidentsResponse])
				}
				object batchUpdate {
					def apply(organizationsId :PlayApi, environmentsId :PlayApi)(using auth: AuthToken, ec: ExecutionContext): batchUpdate = new batchUpdate(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/securityIncidents:batchUpdate")).addQueryStringParameters())
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1ListSecurityIncidentsResponse]) {
					/** Optional. A page token, received from a previous `ListSecurityIncident` call. Provide this to retrieve the subsequent page. */
					def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
					/** Optional. The maximum number of incidents to return. The service may return fewer than this value. If unspecified, at most 50 incidents will be returned.<br>Format: int32 */
					def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
					def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudApigeeV1ListSecurityIncidentsResponse])
				}
				object list {
					def apply(organizationsId :PlayApi, environmentsId :PlayApi, parent: String, filter: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/securityIncidents")).addQueryStringParameters("parent" -> parent.toString, "filter" -> filter.toString))
					given Conversion[list, Future[Schema.GoogleCloudApigeeV1ListSecurityIncidentsResponse]] = (fun: list) => fun.apply()
				}
				class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudApigeeV1SecurityIncident(body: Schema.GoogleCloudApigeeV1SecurityIncident) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.GoogleCloudApigeeV1SecurityIncident])
				}
				object patch {
					def apply(organizationsId :PlayApi, environmentsId :PlayApi, securityIncidentsId :PlayApi, updateMask: String, name: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/securityIncidents/${securityIncidentsId}")).addQueryStringParameters("updateMask" -> updateMask.toString, "name" -> name.toString))
				}
			}
			object archiveDeployments {
				class generateDownloadUrl(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudApigeeV1GenerateDownloadUrlRequest(body: Schema.GoogleCloudApigeeV1GenerateDownloadUrlRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleCloudApigeeV1GenerateDownloadUrlResponse])
				}
				object generateDownloadUrl {
					def apply(organizationsId :PlayApi, environmentsId :PlayApi, archiveDeploymentsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): generateDownloadUrl = new generateDownloadUrl(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/archiveDeployments/${archiveDeploymentsId}:generateDownloadUrl")).addQueryStringParameters("name" -> name.toString))
				}
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudApigeeV1ArchiveDeployment(body: Schema.GoogleCloudApigeeV1ArchiveDeployment) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleLongrunningOperation])
				}
				object create {
					def apply(organizationsId :PlayApi, environmentsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/archiveDeployments")).addQueryStringParameters("parent" -> parent.toString))
				}
				class generateUploadUrl(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudApigeeV1GenerateUploadUrlRequest(body: Schema.GoogleCloudApigeeV1GenerateUploadUrlRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleCloudApigeeV1GenerateUploadUrlResponse])
				}
				object generateUploadUrl {
					def apply(organizationsId :PlayApi, environmentsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): generateUploadUrl = new generateUploadUrl(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/archiveDeployments:generateUploadUrl")).addQueryStringParameters("parent" -> parent.toString))
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
					def apply() = req.execute("DELETE").map(_.json.as[Schema.GoogleProtobufEmpty])
				}
				object delete {
					def apply(organizationsId :PlayApi, environmentsId :PlayApi, archiveDeploymentsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/archiveDeployments/${archiveDeploymentsId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1ArchiveDeployment]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudApigeeV1ArchiveDeployment])
				}
				object get {
					def apply(organizationsId :PlayApi, environmentsId :PlayApi, archiveDeploymentsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/archiveDeployments/${archiveDeploymentsId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleCloudApigeeV1ArchiveDeployment]] = (fun: get) => fun.apply()
				}
				class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudApigeeV1ArchiveDeployment(body: Schema.GoogleCloudApigeeV1ArchiveDeployment) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.GoogleCloudApigeeV1ArchiveDeployment])
				}
				object patch {
					def apply(organizationsId :PlayApi, environmentsId :PlayApi, archiveDeploymentsId :PlayApi, updateMask: String, name: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/archiveDeployments/${archiveDeploymentsId}")).addQueryStringParameters("updateMask" -> updateMask.toString, "name" -> name.toString))
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1ListArchiveDeploymentsResponse]) {
					/** Optional. Maximum number of Archive Deployments to return. If unspecified, at most 25 deployments will be returned.<br>Format: int32 */
					def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
					/** Optional. An optional query used to return a subset of Archive Deployments using the semantics defined in https://google.aip.dev/160. */
					def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
					/** Optional. Page token, returned from a previous ListArchiveDeployments call, that you can use to retrieve the next page. */
					def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
					def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudApigeeV1ListArchiveDeploymentsResponse])
				}
				object list {
					def apply(organizationsId :PlayApi, environmentsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/archiveDeployments")).addQueryStringParameters("parent" -> parent.toString))
					given Conversion[list, Future[Schema.GoogleCloudApigeeV1ListArchiveDeploymentsResponse]] = (fun: list) => fun.apply()
				}
			}
			object optimizedStats {
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1OptimizedStats]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudApigeeV1OptimizedStats])
				}
				object get {
					def apply(organizationsId :PlayApi, environmentsId :PlayApi, optimizedStatsId :PlayApi, timeUnit: String, sonar: Boolean, name: String, timeRange: String, sort: String, offset: String, realtime: Boolean, accuracy: String, filter: String, tsAscending: Boolean, topk: String, aggTable: String, select: String, limit: String, sortby: String, tzo: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/optimizedStats/${optimizedStatsId}")).addQueryStringParameters("timeUnit" -> timeUnit.toString, "sonar" -> sonar.toString, "name" -> name.toString, "timeRange" -> timeRange.toString, "sort" -> sort.toString, "offset" -> offset.toString, "realtime" -> realtime.toString, "accuracy" -> accuracy.toString, "filter" -> filter.toString, "tsAscending" -> tsAscending.toString, "topk" -> topk.toString, "aggTable" -> aggTable.toString, "select" -> select.toString, "limit" -> limit.toString, "sortby" -> sortby.toString, "tzo" -> tzo.toString))
					given Conversion[get, Future[Schema.GoogleCloudApigeeV1OptimizedStats]] = (fun: get) => fun.apply()
				}
			}
			object addonsConfig {
				class setAddonEnablement(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudApigeeV1SetAddonEnablementRequest(body: Schema.GoogleCloudApigeeV1SetAddonEnablementRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleLongrunningOperation])
				}
				object setAddonEnablement {
					def apply(organizationsId :PlayApi, environmentsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): setAddonEnablement = new setAddonEnablement(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/addonsConfig:setAddonEnablement")).addQueryStringParameters("name" -> name.toString))
				}
			}
			object targetservers {
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1TargetServer]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudApigeeV1TargetServer])
				}
				object get {
					def apply(organizationsId :PlayApi, environmentsId :PlayApi, targetserversId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/targetservers/${targetserversId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleCloudApigeeV1TargetServer]] = (fun: get) => fun.apply()
				}
				class update(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudApigeeV1TargetServer(body: Schema.GoogleCloudApigeeV1TargetServer) = req.withBody(Json.toJson(body)).execute("PUT").map(_.json.as[Schema.GoogleCloudApigeeV1TargetServer])
				}
				object update {
					def apply(organizationsId :PlayApi, environmentsId :PlayApi, targetserversId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): update = new update(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/targetservers/${targetserversId}")).addQueryStringParameters("name" -> name.toString))
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1TargetServer]) {
					def apply() = req.execute("DELETE").map(_.json.as[Schema.GoogleCloudApigeeV1TargetServer])
				}
				object delete {
					def apply(organizationsId :PlayApi, environmentsId :PlayApi, targetserversId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/targetservers/${targetserversId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.GoogleCloudApigeeV1TargetServer]] = (fun: delete) => fun.apply()
				}
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					/** Optional. The ID to give the TargetServer. This will overwrite the value in TargetServer. */
					def withName(name: String) = new create(req.addQueryStringParameters("name" -> name.toString))
					def withGoogleCloudApigeeV1TargetServer(body: Schema.GoogleCloudApigeeV1TargetServer) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleCloudApigeeV1TargetServer])
				}
				object create {
					def apply(organizationsId :PlayApi, environmentsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/targetservers")).addQueryStringParameters("parent" -> parent.toString))
				}
			}
			object keyvaluemaps {
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1KeyValueMap]) {
					def apply() = req.execute("DELETE").map(_.json.as[Schema.GoogleCloudApigeeV1KeyValueMap])
				}
				object delete {
					def apply(organizationsId :PlayApi, environmentsId :PlayApi, keyvaluemapsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/keyvaluemaps/${keyvaluemapsId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.GoogleCloudApigeeV1KeyValueMap]] = (fun: delete) => fun.apply()
				}
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudApigeeV1KeyValueMap(body: Schema.GoogleCloudApigeeV1KeyValueMap) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleCloudApigeeV1KeyValueMap])
				}
				object create {
					def apply(organizationsId :PlayApi, environmentsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/keyvaluemaps")).addQueryStringParameters("parent" -> parent.toString))
				}
				object entries {
					class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleCloudApigeeV1KeyValueEntry(body: Schema.GoogleCloudApigeeV1KeyValueEntry) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleCloudApigeeV1KeyValueEntry])
					}
					object create {
						def apply(organizationsId :PlayApi, environmentsId :PlayApi, keyvaluemapsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/keyvaluemaps/${keyvaluemapsId}/entries")).addQueryStringParameters("parent" -> parent.toString))
					}
					class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1KeyValueEntry]) {
						def apply() = req.execute("DELETE").map(_.json.as[Schema.GoogleCloudApigeeV1KeyValueEntry])
					}
					object delete {
						def apply(organizationsId :PlayApi, environmentsId :PlayApi, keyvaluemapsId :PlayApi, entriesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/keyvaluemaps/${keyvaluemapsId}/entries/${entriesId}")).addQueryStringParameters("name" -> name.toString))
						given Conversion[delete, Future[Schema.GoogleCloudApigeeV1KeyValueEntry]] = (fun: delete) => fun.apply()
					}
					class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1KeyValueEntry]) {
						def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudApigeeV1KeyValueEntry])
					}
					object get {
						def apply(organizationsId :PlayApi, environmentsId :PlayApi, keyvaluemapsId :PlayApi, entriesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/keyvaluemaps/${keyvaluemapsId}/entries/${entriesId}")).addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.GoogleCloudApigeeV1KeyValueEntry]] = (fun: get) => fun.apply()
					}
					class update(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleCloudApigeeV1KeyValueEntry(body: Schema.GoogleCloudApigeeV1KeyValueEntry) = req.withBody(Json.toJson(body)).execute("PUT").map(_.json.as[Schema.GoogleCloudApigeeV1KeyValueEntry])
					}
					object update {
						def apply(organizationsId :PlayApi, environmentsId :PlayApi, keyvaluemapsId :PlayApi, entriesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): update = new update(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/keyvaluemaps/${keyvaluemapsId}/entries/${entriesId}")).addQueryStringParameters("name" -> name.toString))
					}
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1ListKeyValueEntriesResponse]) {
						/** Optional. Page token. If provides, must be a valid key value entry returned from a previous call that can be used to retrieve the next page. */
						def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
						/** Optional. Maximum number of key value entries to return. If unspecified, at most 100 entries will be returned.<br>Format: int32 */
						def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
						def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudApigeeV1ListKeyValueEntriesResponse])
					}
					object list {
						def apply(organizationsId :PlayApi, environmentsId :PlayApi, keyvaluemapsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/keyvaluemaps/${keyvaluemapsId}/entries")).addQueryStringParameters("parent" -> parent.toString))
						given Conversion[list, Future[Schema.GoogleCloudApigeeV1ListKeyValueEntriesResponse]] = (fun: list) => fun.apply()
					}
				}
			}
			object securityReports {
				class getResult(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleApiHttpBody]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.GoogleApiHttpBody])
				}
				object getResult {
					def apply(organizationsId :PlayApi, environmentsId :PlayApi, securityReportsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): getResult = new getResult(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/securityReports/${securityReportsId}/result")).addQueryStringParameters("name" -> name.toString))
					given Conversion[getResult, Future[Schema.GoogleApiHttpBody]] = (fun: getResult) => fun.apply()
				}
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudApigeeV1SecurityReportQuery(body: Schema.GoogleCloudApigeeV1SecurityReportQuery) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleCloudApigeeV1SecurityReport])
				}
				object create {
					def apply(organizationsId :PlayApi, environmentsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/securityReports")).addQueryStringParameters("parent" -> parent.toString))
				}
				class getResultView(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1SecurityReportResultView]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudApigeeV1SecurityReportResultView])
				}
				object getResultView {
					def apply(organizationsId :PlayApi, environmentsId :PlayApi, securityReportsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): getResultView = new getResultView(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/securityReports/${securityReportsId}/resultView")).addQueryStringParameters("name" -> name.toString))
					given Conversion[getResultView, Future[Schema.GoogleCloudApigeeV1SecurityReportResultView]] = (fun: getResultView) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1SecurityReport]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudApigeeV1SecurityReport])
				}
				object get {
					def apply(organizationsId :PlayApi, environmentsId :PlayApi, securityReportsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/securityReports/${securityReportsId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleCloudApigeeV1SecurityReport]] = (fun: get) => fun.apply()
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1ListSecurityReportsResponse]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudApigeeV1ListSecurityReportsResponse])
				}
				object list {
					def apply(organizationsId :PlayApi, environmentsId :PlayApi, dataset: String, status: String, to: String, from: String, submittedBy: String, parent: String, pageToken: String, pageSize: Int)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/securityReports")).addQueryStringParameters("dataset" -> dataset.toString, "status" -> status.toString, "to" -> to.toString, "from" -> from.toString, "submittedBy" -> submittedBy.toString, "parent" -> parent.toString, "pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString))
					given Conversion[list, Future[Schema.GoogleCloudApigeeV1ListSecurityReportsResponse]] = (fun: list) => fun.apply()
				}
			}
			object apis {
				object deployments {
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1ListDeploymentsResponse]) {
						def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudApigeeV1ListDeploymentsResponse])
					}
					object list {
						def apply(organizationsId :PlayApi, environmentsId :PlayApi, apisId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/apis/${apisId}/deployments")).addQueryStringParameters("parent" -> parent.toString))
						given Conversion[list, Future[Schema.GoogleCloudApigeeV1ListDeploymentsResponse]] = (fun: list) => fun.apply()
					}
				}
				object revisions {
					class getDeployments(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1Deployment]) {
						def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudApigeeV1Deployment])
					}
					object getDeployments {
						def apply(organizationsId :PlayApi, environmentsId :PlayApi, apisId :PlayApi, revisionsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): getDeployments = new getDeployments(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/apis/${apisId}/revisions/${revisionsId}/deployments")).addQueryStringParameters("name" -> name.toString))
						given Conversion[getDeployments, Future[Schema.GoogleCloudApigeeV1Deployment]] = (fun: getDeployments) => fun.apply()
					}
					class undeploy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
						def apply() = req.execute("DELETE").map(_.json.as[Schema.GoogleProtobufEmpty])
					}
					object undeploy {
						def apply(organizationsId :PlayApi, environmentsId :PlayApi, apisId :PlayApi, revisionsId :PlayApi, name: String, sequencedRollout: Boolean)(using auth: AuthToken, ec: ExecutionContext): undeploy = new undeploy(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/apis/${apisId}/revisions/${revisionsId}/deployments")).addQueryStringParameters("name" -> name.toString, "sequencedRollout" -> sequencedRollout.toString))
						given Conversion[undeploy, Future[Schema.GoogleProtobufEmpty]] = (fun: undeploy) => fun.apply()
					}
					class deploy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1Deployment]) {
						def apply() = req.execute("POST").map(_.json.as[Schema.GoogleCloudApigeeV1Deployment])
					}
					object deploy {
						def apply(organizationsId :PlayApi, environmentsId :PlayApi, apisId :PlayApi, revisionsId :PlayApi, sequencedRollout: Boolean, `override`: Boolean, name: String, serviceAccount: String)(using auth: AuthToken, ec: ExecutionContext): deploy = new deploy(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/apis/${apisId}/revisions/${revisionsId}/deployments")).addQueryStringParameters("sequencedRollout" -> sequencedRollout.toString, "override" -> `override`.toString, "name" -> name.toString, "serviceAccount" -> serviceAccount.toString))
						given Conversion[deploy, Future[Schema.GoogleCloudApigeeV1Deployment]] = (fun: deploy) => fun.apply()
					}
					object debugsessions {
						class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
							/** Optional. The time in seconds after which this DebugSession should end. A timeout specified in DebugSession will overwrite this value.<br>Format: int64 */
							def withTimeout(timeout: String) = new create(req.addQueryStringParameters("timeout" -> timeout.toString))
							def withGoogleCloudApigeeV1DebugSession(body: Schema.GoogleCloudApigeeV1DebugSession) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleCloudApigeeV1DebugSession])
						}
						object create {
							def apply(organizationsId :PlayApi, environmentsId :PlayApi, apisId :PlayApi, revisionsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/apis/${apisId}/revisions/${revisionsId}/debugsessions")).addQueryStringParameters("parent" -> parent.toString))
						}
						class deleteData(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
							def apply() = req.execute("DELETE").map(_.json.as[Schema.GoogleProtobufEmpty])
						}
						object deleteData {
							def apply(organizationsId :PlayApi, environmentsId :PlayApi, apisId :PlayApi, revisionsId :PlayApi, debugsessionsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): deleteData = new deleteData(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/apis/${apisId}/revisions/${revisionsId}/debugsessions/${debugsessionsId}/data")).addQueryStringParameters("name" -> name.toString))
							given Conversion[deleteData, Future[Schema.GoogleProtobufEmpty]] = (fun: deleteData) => fun.apply()
						}
						class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1DebugSession]) {
							def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudApigeeV1DebugSession])
						}
						object get {
							def apply(organizationsId :PlayApi, environmentsId :PlayApi, apisId :PlayApi, revisionsId :PlayApi, debugsessionsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/apis/${apisId}/revisions/${revisionsId}/debugsessions/${debugsessionsId}")).addQueryStringParameters("name" -> name.toString))
							given Conversion[get, Future[Schema.GoogleCloudApigeeV1DebugSession]] = (fun: get) => fun.apply()
						}
						class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1ListDebugSessionsResponse]) {
							def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudApigeeV1ListDebugSessionsResponse])
						}
						object list {
							def apply(organizationsId :PlayApi, environmentsId :PlayApi, apisId :PlayApi, revisionsId :PlayApi, pageToken: String, parent: String, pageSize: Int)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/apis/${apisId}/revisions/${revisionsId}/debugsessions")).addQueryStringParameters("pageToken" -> pageToken.toString, "parent" -> parent.toString, "pageSize" -> pageSize.toString))
							given Conversion[list, Future[Schema.GoogleCloudApigeeV1ListDebugSessionsResponse]] = (fun: list) => fun.apply()
						}
						object data {
							class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1DebugSessionTransaction]) {
								def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudApigeeV1DebugSessionTransaction])
							}
							object get {
								def apply(organizationsId :PlayApi, environmentsId :PlayApi, apisId :PlayApi, revisionsId :PlayApi, debugsessionsId :PlayApi, dataId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/apis/${apisId}/revisions/${revisionsId}/debugsessions/${debugsessionsId}/data/${dataId}")).addQueryStringParameters("name" -> name.toString))
								given Conversion[get, Future[Schema.GoogleCloudApigeeV1DebugSessionTransaction]] = (fun: get) => fun.apply()
							}
						}
					}
					object deployments {
						class generateDeployChangeReport(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1DeploymentChangeReport]) {
							def apply() = req.execute("POST").map(_.json.as[Schema.GoogleCloudApigeeV1DeploymentChangeReport])
						}
						object generateDeployChangeReport {
							def apply(organizationsId :PlayApi, environmentsId :PlayApi, apisId :PlayApi, revisionsId :PlayApi, `override`: Boolean, name: String)(using auth: AuthToken, ec: ExecutionContext): generateDeployChangeReport = new generateDeployChangeReport(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/apis/${apisId}/revisions/${revisionsId}/deployments:generateDeployChangeReport")).addQueryStringParameters("override" -> `override`.toString, "name" -> name.toString))
							given Conversion[generateDeployChangeReport, Future[Schema.GoogleCloudApigeeV1DeploymentChangeReport]] = (fun: generateDeployChangeReport) => fun.apply()
						}
						class generateUndeployChangeReport(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1DeploymentChangeReport]) {
							def apply() = req.execute("POST").map(_.json.as[Schema.GoogleCloudApigeeV1DeploymentChangeReport])
						}
						object generateUndeployChangeReport {
							def apply(organizationsId :PlayApi, environmentsId :PlayApi, apisId :PlayApi, revisionsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): generateUndeployChangeReport = new generateUndeployChangeReport(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/apis/${apisId}/revisions/${revisionsId}/deployments:generateUndeployChangeReport")).addQueryStringParameters("name" -> name.toString))
							given Conversion[generateUndeployChangeReport, Future[Schema.GoogleCloudApigeeV1DeploymentChangeReport]] = (fun: generateUndeployChangeReport) => fun.apply()
						}
					}
				}
			}
			object flowhooks {
				class detachSharedFlowFromFlowHook(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1FlowHook]) {
					def apply() = req.execute("DELETE").map(_.json.as[Schema.GoogleCloudApigeeV1FlowHook])
				}
				object detachSharedFlowFromFlowHook {
					def apply(organizationsId :PlayApi, environmentsId :PlayApi, flowhooksId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): detachSharedFlowFromFlowHook = new detachSharedFlowFromFlowHook(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/flowhooks/${flowhooksId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[detachSharedFlowFromFlowHook, Future[Schema.GoogleCloudApigeeV1FlowHook]] = (fun: detachSharedFlowFromFlowHook) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1FlowHook]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudApigeeV1FlowHook])
				}
				object get {
					def apply(organizationsId :PlayApi, environmentsId :PlayApi, flowhooksId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/flowhooks/${flowhooksId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleCloudApigeeV1FlowHook]] = (fun: get) => fun.apply()
				}
				class attachSharedFlowToFlowHook(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudApigeeV1FlowHook(body: Schema.GoogleCloudApigeeV1FlowHook) = req.withBody(Json.toJson(body)).execute("PUT").map(_.json.as[Schema.GoogleCloudApigeeV1FlowHook])
				}
				object attachSharedFlowToFlowHook {
					def apply(organizationsId :PlayApi, environmentsId :PlayApi, flowhooksId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): attachSharedFlowToFlowHook = new attachSharedFlowToFlowHook(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/flowhooks/${flowhooksId}")).addQueryStringParameters("name" -> name.toString))
				}
			}
			object sharedflows {
				object revisions {
					class deploy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1Deployment]) {
						def apply() = req.execute("POST").map(_.json.as[Schema.GoogleCloudApigeeV1Deployment])
					}
					object deploy {
						def apply(organizationsId :PlayApi, environmentsId :PlayApi, sharedflowsId :PlayApi, revisionsId :PlayApi, name: String, serviceAccount: String, `override`: Boolean)(using auth: AuthToken, ec: ExecutionContext): deploy = new deploy(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/sharedflows/${sharedflowsId}/revisions/${revisionsId}/deployments")).addQueryStringParameters("name" -> name.toString, "serviceAccount" -> serviceAccount.toString, "override" -> `override`.toString))
						given Conversion[deploy, Future[Schema.GoogleCloudApigeeV1Deployment]] = (fun: deploy) => fun.apply()
					}
					class undeploy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
						def apply() = req.execute("DELETE").map(_.json.as[Schema.GoogleProtobufEmpty])
					}
					object undeploy {
						def apply(organizationsId :PlayApi, environmentsId :PlayApi, sharedflowsId :PlayApi, revisionsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): undeploy = new undeploy(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/sharedflows/${sharedflowsId}/revisions/${revisionsId}/deployments")).addQueryStringParameters("name" -> name.toString))
						given Conversion[undeploy, Future[Schema.GoogleProtobufEmpty]] = (fun: undeploy) => fun.apply()
					}
					class getDeployments(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1Deployment]) {
						def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudApigeeV1Deployment])
					}
					object getDeployments {
						def apply(organizationsId :PlayApi, environmentsId :PlayApi, sharedflowsId :PlayApi, revisionsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): getDeployments = new getDeployments(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/sharedflows/${sharedflowsId}/revisions/${revisionsId}/deployments")).addQueryStringParameters("name" -> name.toString))
						given Conversion[getDeployments, Future[Schema.GoogleCloudApigeeV1Deployment]] = (fun: getDeployments) => fun.apply()
					}
				}
				object deployments {
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1ListDeploymentsResponse]) {
						def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudApigeeV1ListDeploymentsResponse])
					}
					object list {
						def apply(organizationsId :PlayApi, environmentsId :PlayApi, sharedflowsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/sharedflows/${sharedflowsId}/deployments")).addQueryStringParameters("parent" -> parent.toString))
						given Conversion[list, Future[Schema.GoogleCloudApigeeV1ListDeploymentsResponse]] = (fun: list) => fun.apply()
					}
				}
			}
			object resourcefiles {
				class listEnvironmentResources(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1ListEnvironmentResourcesResponse]) {
					/** Optional. Type of resource files to list. {{ resource_file_type }} */
					def withType(`type`: String) = new listEnvironmentResources(req.addQueryStringParameters("type" -> `type`.toString))
					def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudApigeeV1ListEnvironmentResourcesResponse])
				}
				object listEnvironmentResources {
					def apply(organizationsId :PlayApi, environmentsId :PlayApi, `type` :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): listEnvironmentResources = new listEnvironmentResources(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/resourcefiles/${`type`}")).addQueryStringParameters("parent" -> parent.toString))
					given Conversion[listEnvironmentResources, Future[Schema.GoogleCloudApigeeV1ListEnvironmentResourcesResponse]] = (fun: listEnvironmentResources) => fun.apply()
				}
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleApiHttpBody(body: Schema.GoogleApiHttpBody) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleCloudApigeeV1ResourceFile])
				}
				object create {
					def apply(organizationsId :PlayApi, environmentsId :PlayApi, `type`: String, parent: String, name: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/resourcefiles")).addQueryStringParameters("type" -> `type`.toString, "parent" -> parent.toString, "name" -> name.toString))
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1ResourceFile]) {
					def apply() = req.execute("DELETE").map(_.json.as[Schema.GoogleCloudApigeeV1ResourceFile])
				}
				object delete {
					def apply(organizationsId :PlayApi, environmentsId :PlayApi, `type`: String, name: String, parent: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/resourcefiles/${`type`}/${name}")).addQueryStringParameters("parent" -> parent.toString))
					given Conversion[delete, Future[Schema.GoogleCloudApigeeV1ResourceFile]] = (fun: delete) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleApiHttpBody]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.GoogleApiHttpBody])
				}
				object get {
					def apply(organizationsId :PlayApi, environmentsId :PlayApi, `type`: String, parent: String, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/resourcefiles/${`type`}/${name}")).addQueryStringParameters("parent" -> parent.toString))
					given Conversion[get, Future[Schema.GoogleApiHttpBody]] = (fun: get) => fun.apply()
				}
				class update(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleApiHttpBody(body: Schema.GoogleApiHttpBody) = req.withBody(Json.toJson(body)).execute("PUT").map(_.json.as[Schema.GoogleCloudApigeeV1ResourceFile])
				}
				object update {
					def apply(organizationsId :PlayApi, environmentsId :PlayApi, name: String, parent: String, `type`: String)(using auth: AuthToken, ec: ExecutionContext): update = new update(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/resourcefiles/${`type`}/${name}")).addQueryStringParameters("parent" -> parent.toString))
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1ListEnvironmentResourcesResponse]) {
					/** Optional. Type of resource files to list. {{ resource_file_type }} */
					def withType(`type`: String) = new list(req.addQueryStringParameters("type" -> `type`.toString))
					def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudApigeeV1ListEnvironmentResourcesResponse])
				}
				object list {
					def apply(organizationsId :PlayApi, environmentsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/resourcefiles")).addQueryStringParameters("parent" -> parent.toString))
					given Conversion[list, Future[Schema.GoogleCloudApigeeV1ListEnvironmentResourcesResponse]] = (fun: list) => fun.apply()
				}
			}
			object references {
				class update(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudApigeeV1Reference(body: Schema.GoogleCloudApigeeV1Reference) = req.withBody(Json.toJson(body)).execute("PUT").map(_.json.as[Schema.GoogleCloudApigeeV1Reference])
				}
				object update {
					def apply(organizationsId :PlayApi, environmentsId :PlayApi, referencesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): update = new update(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/references/${referencesId}")).addQueryStringParameters("name" -> name.toString))
				}
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudApigeeV1Reference(body: Schema.GoogleCloudApigeeV1Reference) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleCloudApigeeV1Reference])
				}
				object create {
					def apply(organizationsId :PlayApi, environmentsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/references")).addQueryStringParameters("parent" -> parent.toString))
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1Reference]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudApigeeV1Reference])
				}
				object get {
					def apply(organizationsId :PlayApi, environmentsId :PlayApi, referencesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/references/${referencesId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleCloudApigeeV1Reference]] = (fun: get) => fun.apply()
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1Reference]) {
					def apply() = req.execute("DELETE").map(_.json.as[Schema.GoogleCloudApigeeV1Reference])
				}
				object delete {
					def apply(organizationsId :PlayApi, environmentsId :PlayApi, referencesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/references/${referencesId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.GoogleCloudApigeeV1Reference]] = (fun: delete) => fun.apply()
				}
			}
			object securityActions {
				class enable(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudApigeeV1EnableSecurityActionRequest(body: Schema.GoogleCloudApigeeV1EnableSecurityActionRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleCloudApigeeV1SecurityAction])
				}
				object enable {
					def apply(organizationsId :PlayApi, environmentsId :PlayApi, securityActionsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): enable = new enable(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/securityActions/${securityActionsId}:enable")).addQueryStringParameters("name" -> name.toString))
				}
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudApigeeV1SecurityAction(body: Schema.GoogleCloudApigeeV1SecurityAction) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleCloudApigeeV1SecurityAction])
				}
				object create {
					def apply(organizationsId :PlayApi, environmentsId :PlayApi, parent: String, securityActionId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/securityActions")).addQueryStringParameters("parent" -> parent.toString, "securityActionId" -> securityActionId.toString))
				}
				class disable(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudApigeeV1DisableSecurityActionRequest(body: Schema.GoogleCloudApigeeV1DisableSecurityActionRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleCloudApigeeV1SecurityAction])
				}
				object disable {
					def apply(organizationsId :PlayApi, environmentsId :PlayApi, securityActionsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): disable = new disable(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/securityActions/${securityActionsId}:disable")).addQueryStringParameters("name" -> name.toString))
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1SecurityAction]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudApigeeV1SecurityAction])
				}
				object get {
					def apply(organizationsId :PlayApi, environmentsId :PlayApi, securityActionsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/securityActions/${securityActionsId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleCloudApigeeV1SecurityAction]] = (fun: get) => fun.apply()
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1ListSecurityActionsResponse]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudApigeeV1ListSecurityActionsResponse])
				}
				object list {
					def apply(organizationsId :PlayApi, environmentsId :PlayApi, filter: String, parent: String, pageToken: String, pageSize: Int)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/securityActions")).addQueryStringParameters("filter" -> filter.toString, "parent" -> parent.toString, "pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString))
					given Conversion[list, Future[Schema.GoogleCloudApigeeV1ListSecurityActionsResponse]] = (fun: list) => fun.apply()
				}
			}
			object caches {
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
					def apply() = req.execute("DELETE").map(_.json.as[Schema.GoogleProtobufEmpty])
				}
				object delete {
					def apply(organizationsId :PlayApi, environmentsId :PlayApi, cachesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/caches/${cachesId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
				}
			}
			object keystores {
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1Keystore]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudApigeeV1Keystore])
				}
				object get {
					def apply(organizationsId :PlayApi, environmentsId :PlayApi, keystoresId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/keystores/${keystoresId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleCloudApigeeV1Keystore]] = (fun: get) => fun.apply()
				}
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					/** Optional. Name of the keystore. Overrides the value in Keystore. */
					def withName(name: String) = new create(req.addQueryStringParameters("name" -> name.toString))
					def withGoogleCloudApigeeV1Keystore(body: Schema.GoogleCloudApigeeV1Keystore) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleCloudApigeeV1Keystore])
				}
				object create {
					def apply(organizationsId :PlayApi, environmentsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/keystores")).addQueryStringParameters("parent" -> parent.toString))
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1Keystore]) {
					def apply() = req.execute("DELETE").map(_.json.as[Schema.GoogleCloudApigeeV1Keystore])
				}
				object delete {
					def apply(organizationsId :PlayApi, environmentsId :PlayApi, keystoresId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/keystores/${keystoresId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.GoogleCloudApigeeV1Keystore]] = (fun: delete) => fun.apply()
				}
				object aliases {
					class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleApiHttpBody(body: Schema.GoogleApiHttpBody) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleCloudApigeeV1Alias])
					}
					object create {
						def apply(organizationsId :PlayApi, environmentsId :PlayApi, keystoresId :PlayApi, ignoreNewlineValidation: Boolean, ignoreExpiryValidation: Boolean, format: String, alias: String, _password: String, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/keystores/${keystoresId}/aliases")).addQueryStringParameters("ignoreNewlineValidation" -> ignoreNewlineValidation.toString, "ignoreExpiryValidation" -> ignoreExpiryValidation.toString, "format" -> format.toString, "alias" -> alias.toString, "_password" -> _password.toString, "parent" -> parent.toString))
					}
					class csr(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleApiHttpBody]) {
						def apply() = req.execute("GET").map(_.json.as[Schema.GoogleApiHttpBody])
					}
					object csr {
						def apply(organizationsId :PlayApi, environmentsId :PlayApi, keystoresId :PlayApi, aliasesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): csr = new csr(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/keystores/${keystoresId}/aliases/${aliasesId}/csr")).addQueryStringParameters("name" -> name.toString))
						given Conversion[csr, Future[Schema.GoogleApiHttpBody]] = (fun: csr) => fun.apply()
					}
					class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1Alias]) {
						def apply() = req.execute("DELETE").map(_.json.as[Schema.GoogleCloudApigeeV1Alias])
					}
					object delete {
						def apply(organizationsId :PlayApi, environmentsId :PlayApi, keystoresId :PlayApi, aliasesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/keystores/${keystoresId}/aliases/${aliasesId}")).addQueryStringParameters("name" -> name.toString))
						given Conversion[delete, Future[Schema.GoogleCloudApigeeV1Alias]] = (fun: delete) => fun.apply()
					}
					class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1Alias]) {
						def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudApigeeV1Alias])
					}
					object get {
						def apply(organizationsId :PlayApi, environmentsId :PlayApi, keystoresId :PlayApi, aliasesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/keystores/${keystoresId}/aliases/${aliasesId}")).addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.GoogleCloudApigeeV1Alias]] = (fun: get) => fun.apply()
					}
					class update(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleApiHttpBody(body: Schema.GoogleApiHttpBody) = req.withBody(Json.toJson(body)).execute("PUT").map(_.json.as[Schema.GoogleCloudApigeeV1Alias])
					}
					object update {
						def apply(organizationsId :PlayApi, environmentsId :PlayApi, keystoresId :PlayApi, aliasesId :PlayApi, ignoreNewlineValidation: Boolean, name: String, ignoreExpiryValidation: Boolean)(using auth: AuthToken, ec: ExecutionContext): update = new update(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/keystores/${keystoresId}/aliases/${aliasesId}")).addQueryStringParameters("ignoreNewlineValidation" -> ignoreNewlineValidation.toString, "name" -> name.toString, "ignoreExpiryValidation" -> ignoreExpiryValidation.toString))
					}
					class getCertificate(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleApiHttpBody]) {
						def apply() = req.execute("GET").map(_.json.as[Schema.GoogleApiHttpBody])
					}
					object getCertificate {
						def apply(organizationsId :PlayApi, environmentsId :PlayApi, keystoresId :PlayApi, aliasesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): getCertificate = new getCertificate(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/keystores/${keystoresId}/aliases/${aliasesId}/certificate")).addQueryStringParameters("name" -> name.toString))
						given Conversion[getCertificate, Future[Schema.GoogleApiHttpBody]] = (fun: getCertificate) => fun.apply()
					}
				}
			}
			object traceConfig {
				object overrides {
					class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleCloudApigeeV1TraceConfigOverride(body: Schema.GoogleCloudApigeeV1TraceConfigOverride) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleCloudApigeeV1TraceConfigOverride])
					}
					object create {
						def apply(organizationsId :PlayApi, environmentsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/traceConfig/overrides")).addQueryStringParameters("parent" -> parent.toString))
					}
					class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
						def apply() = req.execute("DELETE").map(_.json.as[Schema.GoogleProtobufEmpty])
					}
					object delete {
						def apply(organizationsId :PlayApi, environmentsId :PlayApi, overridesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/traceConfig/overrides/${overridesId}")).addQueryStringParameters("name" -> name.toString))
						given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
					}
					class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1TraceConfigOverride]) {
						def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudApigeeV1TraceConfigOverride])
					}
					object get {
						def apply(organizationsId :PlayApi, environmentsId :PlayApi, overridesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/traceConfig/overrides/${overridesId}")).addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.GoogleCloudApigeeV1TraceConfigOverride]] = (fun: get) => fun.apply()
					}
					class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleCloudApigeeV1TraceConfigOverride(body: Schema.GoogleCloudApigeeV1TraceConfigOverride) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.GoogleCloudApigeeV1TraceConfigOverride])
					}
					object patch {
						def apply(organizationsId :PlayApi, environmentsId :PlayApi, overridesId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/traceConfig/overrides/${overridesId}")).addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
					}
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1ListTraceConfigOverridesResponse]) {
						def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudApigeeV1ListTraceConfigOverridesResponse])
					}
					object list {
						def apply(organizationsId :PlayApi, environmentsId :PlayApi, pageToken: String, parent: String, pageSize: Int)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/traceConfig/overrides")).addQueryStringParameters("pageToken" -> pageToken.toString, "parent" -> parent.toString, "pageSize" -> pageSize.toString))
						given Conversion[list, Future[Schema.GoogleCloudApigeeV1ListTraceConfigOverridesResponse]] = (fun: list) => fun.apply()
					}
				}
			}
			object analytics {
				object admin {
					class getSchemav2(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1Schema]) {
						def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudApigeeV1Schema])
					}
					object getSchemav2 {
						def apply(organizationsId :PlayApi, environmentsId :PlayApi, disableCache: Boolean, `type`: String, name: String)(using auth: AuthToken, ec: ExecutionContext): getSchemav2 = new getSchemav2(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/analytics/admin/schemav2")).addQueryStringParameters("disableCache" -> disableCache.toString, "type" -> `type`.toString, "name" -> name.toString))
						given Conversion[getSchemav2, Future[Schema.GoogleCloudApigeeV1Schema]] = (fun: getSchemav2) => fun.apply()
					}
				}
				object exports {
					class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1Export]) {
						def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudApigeeV1Export])
					}
					object get {
						def apply(organizationsId :PlayApi, environmentsId :PlayApi, exportsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/analytics/exports/${exportsId}")).addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.GoogleCloudApigeeV1Export]] = (fun: get) => fun.apply()
					}
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1ListExportsResponse]) {
						def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudApigeeV1ListExportsResponse])
					}
					object list {
						def apply(organizationsId :PlayApi, environmentsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/analytics/exports")).addQueryStringParameters("parent" -> parent.toString))
						given Conversion[list, Future[Schema.GoogleCloudApigeeV1ListExportsResponse]] = (fun: list) => fun.apply()
					}
					class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleCloudApigeeV1ExportRequest(body: Schema.GoogleCloudApigeeV1ExportRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleCloudApigeeV1Export])
					}
					object create {
						def apply(organizationsId :PlayApi, environmentsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/analytics/exports")).addQueryStringParameters("parent" -> parent.toString))
					}
				}
			}
			object deployments {
				class testIamPermissions(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleIamV1TestIamPermissionsRequest(body: Schema.GoogleIamV1TestIamPermissionsRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleIamV1TestIamPermissionsResponse])
				}
				object testIamPermissions {
					def apply(organizationsId :PlayApi, environmentsId :PlayApi, deploymentsId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): testIamPermissions = new testIamPermissions(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/deployments/${deploymentsId}:testIamPermissions")).addQueryStringParameters("resource" -> resource.toString))
				}
				class setIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleIamV1SetIamPolicyRequest(body: Schema.GoogleIamV1SetIamPolicyRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleIamV1Policy])
				}
				object setIamPolicy {
					def apply(organizationsId :PlayApi, environmentsId :PlayApi, deploymentsId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): setIamPolicy = new setIamPolicy(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/deployments/${deploymentsId}:setIamPolicy")).addQueryStringParameters("resource" -> resource.toString))
				}
				class getIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleIamV1Policy]) {
					/** Optional. The maximum policy version that will be used to format the policy. Valid values are 0, 1, and 3. Requests specifying an invalid value will be rejected. Requests for policies with any conditional role bindings must specify version 3. Policies with no conditional role bindings may specify any valid value or leave the field unset. The policy in the response might use the policy version that you specified, or it might use a lower policy version. For example, if you specify version 3, but the policy has no conditional role bindings, the response uses version 1. To learn which resources support conditions in their IAM policies, see the [IAM documentation](https://cloud.google.com/iam/help/conditions/resource-policies).<br>Format: int32 */
					def withOptionsRequestedPolicyVersion(optionsRequestedPolicyVersion: Int) = new getIamPolicy(req.addQueryStringParameters("options.requestedPolicyVersion" -> optionsRequestedPolicyVersion.toString))
					def apply() = req.execute("GET").map(_.json.as[Schema.GoogleIamV1Policy])
				}
				object getIamPolicy {
					def apply(organizationsId :PlayApi, environmentsId :PlayApi, deploymentsId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): getIamPolicy = new getIamPolicy(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/deployments/${deploymentsId}:getIamPolicy")).addQueryStringParameters("resource" -> resource.toString))
					given Conversion[getIamPolicy, Future[Schema.GoogleIamV1Policy]] = (fun: getIamPolicy) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1Deployment]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudApigeeV1Deployment])
				}
				object get {
					def apply(organizationsId :PlayApi, environmentsId :PlayApi, deploymentsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/deployments/${deploymentsId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleCloudApigeeV1Deployment]] = (fun: get) => fun.apply()
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1ListDeploymentsResponse]) {
					/** Optional. Flag that specifies whether to return shared flow or API proxy deployments. Set to `true` to return shared flow deployments; set to `false` to return API proxy deployments. Defaults to `false`. */
					def withSharedFlows(sharedFlows: Boolean) = new list(req.addQueryStringParameters("sharedFlows" -> sharedFlows.toString))
					def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudApigeeV1ListDeploymentsResponse])
				}
				object list {
					def apply(organizationsId :PlayApi, environmentsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/deployments")).addQueryStringParameters("parent" -> parent.toString))
					given Conversion[list, Future[Schema.GoogleCloudApigeeV1ListDeploymentsResponse]] = (fun: list) => fun.apply()
				}
			}
		}
		object operations {
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningListOperationsResponse]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.GoogleLongrunningListOperationsResponse])
			}
			object list {
				def apply(organizationsId :PlayApi, pageSize: Int, name: String, filter: String, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/operations")).addQueryStringParameters("pageSize" -> pageSize.toString, "name" -> name.toString, "filter" -> filter.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.GoogleLongrunningListOperationsResponse]] = (fun: list) => fun.apply()
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningOperation]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.GoogleLongrunningOperation])
			}
			object get {
				def apply(organizationsId :PlayApi, operationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/operations/${operationsId}")).addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.GoogleLongrunningOperation]] = (fun: get) => fun.apply()
			}
		}
		object apiproducts {
			class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGoogleCloudApigeeV1ApiProduct(body: Schema.GoogleCloudApigeeV1ApiProduct) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleCloudApigeeV1ApiProduct])
			}
			object create {
				def apply(organizationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/apiproducts")).addQueryStringParameters("parent" -> parent.toString))
			}
			class attributes(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGoogleCloudApigeeV1Attributes(body: Schema.GoogleCloudApigeeV1Attributes) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleCloudApigeeV1Attributes])
			}
			object attributes {
				def apply(organizationsId :PlayApi, apiproductsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): attributes = new attributes(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/apiproducts/${apiproductsId}/attributes")).addQueryStringParameters("name" -> name.toString))
				class updateApiProductAttribute(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudApigeeV1Attribute(body: Schema.GoogleCloudApigeeV1Attribute) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleCloudApigeeV1Attribute])
				}
				object updateApiProductAttribute {
					def apply(organizationsId :PlayApi, apiproductsId :PlayApi, attributesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): updateApiProductAttribute = new updateApiProductAttribute(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/apiproducts/${apiproductsId}/attributes/${attributesId}")).addQueryStringParameters("name" -> name.toString))
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1Attributes]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudApigeeV1Attributes])
				}
				object list {
					def apply(organizationsId :PlayApi, apiproductsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/apiproducts/${apiproductsId}/attributes")).addQueryStringParameters("parent" -> parent.toString))
					given Conversion[list, Future[Schema.GoogleCloudApigeeV1Attributes]] = (fun: list) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1Attribute]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudApigeeV1Attribute])
				}
				object get {
					def apply(organizationsId :PlayApi, apiproductsId :PlayApi, attributesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/apiproducts/${apiproductsId}/attributes/${attributesId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleCloudApigeeV1Attribute]] = (fun: get) => fun.apply()
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1Attribute]) {
					def apply() = req.execute("DELETE").map(_.json.as[Schema.GoogleCloudApigeeV1Attribute])
				}
				object delete {
					def apply(organizationsId :PlayApi, apiproductsId :PlayApi, attributesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/apiproducts/${apiproductsId}/attributes/${attributesId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.GoogleCloudApigeeV1Attribute]] = (fun: delete) => fun.apply()
				}
			}
			class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1ApiProduct]) {
				def apply() = req.execute("DELETE").map(_.json.as[Schema.GoogleCloudApigeeV1ApiProduct])
			}
			object delete {
				def apply(organizationsId :PlayApi, apiproductsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/apiproducts/${apiproductsId}")).addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.GoogleCloudApigeeV1ApiProduct]] = (fun: delete) => fun.apply()
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1ApiProduct]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudApigeeV1ApiProduct])
			}
			object get {
				def apply(organizationsId :PlayApi, apiproductsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/apiproducts/${apiproductsId}")).addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.GoogleCloudApigeeV1ApiProduct]] = (fun: get) => fun.apply()
			}
			class update(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGoogleCloudApigeeV1ApiProduct(body: Schema.GoogleCloudApigeeV1ApiProduct) = req.withBody(Json.toJson(body)).execute("PUT").map(_.json.as[Schema.GoogleCloudApigeeV1ApiProduct])
			}
			object update {
				def apply(organizationsId :PlayApi, apiproductsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): update = new update(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/apiproducts/${apiproductsId}")).addQueryStringParameters("name" -> name.toString))
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1ListApiProductsResponse]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudApigeeV1ListApiProductsResponse])
			}
			object list {
				def apply(organizationsId :PlayApi, startKey: String, attributename: String, attributevalue: String, count: String, expand: Boolean, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/apiproducts")).addQueryStringParameters("startKey" -> startKey.toString, "attributename" -> attributename.toString, "attributevalue" -> attributevalue.toString, "count" -> count.toString, "expand" -> expand.toString, "parent" -> parent.toString))
				given Conversion[list, Future[Schema.GoogleCloudApigeeV1ListApiProductsResponse]] = (fun: list) => fun.apply()
			}
			object rateplans {
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudApigeeV1RatePlan(body: Schema.GoogleCloudApigeeV1RatePlan) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleCloudApigeeV1RatePlan])
				}
				object create {
					def apply(organizationsId :PlayApi, apiproductsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/apiproducts/${apiproductsId}/rateplans")).addQueryStringParameters("parent" -> parent.toString))
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1RatePlan]) {
					def apply() = req.execute("DELETE").map(_.json.as[Schema.GoogleCloudApigeeV1RatePlan])
				}
				object delete {
					def apply(organizationsId :PlayApi, apiproductsId :PlayApi, rateplansId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/apiproducts/${apiproductsId}/rateplans/${rateplansId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.GoogleCloudApigeeV1RatePlan]] = (fun: delete) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1RatePlan]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudApigeeV1RatePlan])
				}
				object get {
					def apply(organizationsId :PlayApi, apiproductsId :PlayApi, rateplansId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/apiproducts/${apiproductsId}/rateplans/${rateplansId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleCloudApigeeV1RatePlan]] = (fun: get) => fun.apply()
				}
				class update(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudApigeeV1RatePlan(body: Schema.GoogleCloudApigeeV1RatePlan) = req.withBody(Json.toJson(body)).execute("PUT").map(_.json.as[Schema.GoogleCloudApigeeV1RatePlan])
				}
				object update {
					def apply(organizationsId :PlayApi, apiproductsId :PlayApi, rateplansId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): update = new update(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/apiproducts/${apiproductsId}/rateplans/${rateplansId}")).addQueryStringParameters("name" -> name.toString))
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1ListRatePlansResponse]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudApigeeV1ListRatePlansResponse])
				}
				object list {
					def apply(organizationsId :PlayApi, apiproductsId :PlayApi, orderBy: String, startKey: String, expand: Boolean, parent: String, state: String, count: Int)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/apiproducts/${apiproductsId}/rateplans")).addQueryStringParameters("orderBy" -> orderBy.toString, "startKey" -> startKey.toString, "expand" -> expand.toString, "parent" -> parent.toString, "state" -> state.toString, "count" -> count.toString))
					given Conversion[list, Future[Schema.GoogleCloudApigeeV1ListRatePlansResponse]] = (fun: list) => fun.apply()
				}
			}
		}
		object hostStats {
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1Stats]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudApigeeV1Stats])
			}
			object get {
				def apply(organizationsId :PlayApi, hostStatsId :PlayApi, envgroupHostname: String, timeUnit: String, sort: String, limit: String, realtime: Boolean, offset: String, tzo: String, timeRange: String, sortby: String, select: String, name: String, accuracy: String, filter: String, tsAscending: Boolean, topk: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/hostStats/${hostStatsId}")).addQueryStringParameters("envgroupHostname" -> envgroupHostname.toString, "timeUnit" -> timeUnit.toString, "sort" -> sort.toString, "limit" -> limit.toString, "realtime" -> realtime.toString, "offset" -> offset.toString, "tzo" -> tzo.toString, "timeRange" -> timeRange.toString, "sortby" -> sortby.toString, "select" -> select.toString, "name" -> name.toString, "accuracy" -> accuracy.toString, "filter" -> filter.toString, "tsAscending" -> tsAscending.toString, "topk" -> topk.toString))
				given Conversion[get, Future[Schema.GoogleCloudApigeeV1Stats]] = (fun: get) => fun.apply()
			}
		}
		object appgroups {
			class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGoogleCloudApigeeV1AppGroup(body: Schema.GoogleCloudApigeeV1AppGroup) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleCloudApigeeV1AppGroup])
			}
			object create {
				def apply(organizationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/appgroups")).addQueryStringParameters("parent" -> parent.toString))
			}
			class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1AppGroup]) {
				def apply() = req.execute("DELETE").map(_.json.as[Schema.GoogleCloudApigeeV1AppGroup])
			}
			object delete {
				def apply(organizationsId :PlayApi, appgroupsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/appgroups/${appgroupsId}")).addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.GoogleCloudApigeeV1AppGroup]] = (fun: delete) => fun.apply()
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1AppGroup]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudApigeeV1AppGroup])
			}
			object get {
				def apply(organizationsId :PlayApi, appgroupsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/appgroups/${appgroupsId}")).addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.GoogleCloudApigeeV1AppGroup]] = (fun: get) => fun.apply()
			}
			class update(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGoogleCloudApigeeV1AppGroup(body: Schema.GoogleCloudApigeeV1AppGroup) = req.withBody(Json.toJson(body)).execute("PUT").map(_.json.as[Schema.GoogleCloudApigeeV1AppGroup])
			}
			object update {
				def apply(organizationsId :PlayApi, appgroupsId :PlayApi, action: String, name: String)(using auth: AuthToken, ec: ExecutionContext): update = new update(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/appgroups/${appgroupsId}")).addQueryStringParameters("action" -> action.toString, "name" -> name.toString))
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1ListAppGroupsResponse]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudApigeeV1ListAppGroupsResponse])
			}
			object list {
				def apply(organizationsId :PlayApi, parent: String, pageSize: Int, filter: String, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/appgroups")).addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "filter" -> filter.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.GoogleCloudApigeeV1ListAppGroupsResponse]] = (fun: list) => fun.apply()
			}
			object apps {
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudApigeeV1AppGroupApp(body: Schema.GoogleCloudApigeeV1AppGroupApp) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleCloudApigeeV1AppGroupApp])
				}
				object create {
					def apply(organizationsId :PlayApi, appgroupsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/appgroups/${appgroupsId}/apps")).addQueryStringParameters("parent" -> parent.toString))
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1AppGroupApp]) {
					def apply() = req.execute("DELETE").map(_.json.as[Schema.GoogleCloudApigeeV1AppGroupApp])
				}
				object delete {
					def apply(organizationsId :PlayApi, appgroupsId :PlayApi, appsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/appgroups/${appgroupsId}/apps/${appsId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.GoogleCloudApigeeV1AppGroupApp]] = (fun: delete) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1AppGroupApp]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudApigeeV1AppGroupApp])
				}
				object get {
					def apply(organizationsId :PlayApi, appgroupsId :PlayApi, appsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/appgroups/${appgroupsId}/apps/${appsId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleCloudApigeeV1AppGroupApp]] = (fun: get) => fun.apply()
				}
				class update(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudApigeeV1AppGroupApp(body: Schema.GoogleCloudApigeeV1AppGroupApp) = req.withBody(Json.toJson(body)).execute("PUT").map(_.json.as[Schema.GoogleCloudApigeeV1AppGroupApp])
				}
				object update {
					def apply(organizationsId :PlayApi, appgroupsId :PlayApi, appsId :PlayApi, name: String, action: String)(using auth: AuthToken, ec: ExecutionContext): update = new update(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/appgroups/${appgroupsId}/apps/${appsId}")).addQueryStringParameters("name" -> name.toString, "action" -> action.toString))
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1ListAppGroupAppsResponse]) {
					/** Optional. Page token. If provides, must be a valid AppGroup app returned from a previous call that can be used to retrieve the next page. */
					def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
					/** Optional. Maximum number entries to return. If unspecified, at most 1000 entries will be returned.<br>Format: int32 */
					def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
					def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudApigeeV1ListAppGroupAppsResponse])
				}
				object list {
					def apply(organizationsId :PlayApi, appgroupsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/appgroups/${appgroupsId}/apps")).addQueryStringParameters("parent" -> parent.toString))
					given Conversion[list, Future[Schema.GoogleCloudApigeeV1ListAppGroupAppsResponse]] = (fun: list) => fun.apply()
				}
				object keys {
					class updateAppGroupAppKey(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleCloudApigeeV1UpdateAppGroupAppKeyRequest(body: Schema.GoogleCloudApigeeV1UpdateAppGroupAppKeyRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleCloudApigeeV1AppGroupAppKey])
					}
					object updateAppGroupAppKey {
						def apply(organizationsId :PlayApi, appgroupsId :PlayApi, appsId :PlayApi, keysId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): updateAppGroupAppKey = new updateAppGroupAppKey(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/appgroups/${appgroupsId}/apps/${appsId}/keys/${keysId}")).addQueryStringParameters("name" -> name.toString))
					}
					class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleCloudApigeeV1AppGroupAppKey(body: Schema.GoogleCloudApigeeV1AppGroupAppKey) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleCloudApigeeV1AppGroupAppKey])
					}
					object create {
						def apply(organizationsId :PlayApi, appgroupsId :PlayApi, appsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/appgroups/${appgroupsId}/apps/${appsId}/keys")).addQueryStringParameters("parent" -> parent.toString))
					}
					class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1AppGroupAppKey]) {
						def apply() = req.execute("DELETE").map(_.json.as[Schema.GoogleCloudApigeeV1AppGroupAppKey])
					}
					object delete {
						def apply(organizationsId :PlayApi, appgroupsId :PlayApi, appsId :PlayApi, keysId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/appgroups/${appgroupsId}/apps/${appsId}/keys/${keysId}")).addQueryStringParameters("name" -> name.toString))
						given Conversion[delete, Future[Schema.GoogleCloudApigeeV1AppGroupAppKey]] = (fun: delete) => fun.apply()
					}
					class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1AppGroupAppKey]) {
						def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudApigeeV1AppGroupAppKey])
					}
					object get {
						def apply(organizationsId :PlayApi, appgroupsId :PlayApi, appsId :PlayApi, keysId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/appgroups/${appgroupsId}/apps/${appsId}/keys/${keysId}")).addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.GoogleCloudApigeeV1AppGroupAppKey]] = (fun: get) => fun.apply()
					}
					object apiproducts {
						class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1AppGroupAppKey]) {
							def apply() = req.execute("DELETE").map(_.json.as[Schema.GoogleCloudApigeeV1AppGroupAppKey])
						}
						object delete {
							def apply(organizationsId :PlayApi, appgroupsId :PlayApi, appsId :PlayApi, keysId :PlayApi, apiproductsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/appgroups/${appgroupsId}/apps/${appsId}/keys/${keysId}/apiproducts/${apiproductsId}")).addQueryStringParameters("name" -> name.toString))
							given Conversion[delete, Future[Schema.GoogleCloudApigeeV1AppGroupAppKey]] = (fun: delete) => fun.apply()
						}
						class updateAppGroupAppKeyApiProduct(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
							def apply() = req.execute("POST").map(_.json.as[Schema.GoogleProtobufEmpty])
						}
						object updateAppGroupAppKeyApiProduct {
							def apply(organizationsId :PlayApi, appgroupsId :PlayApi, appsId :PlayApi, keysId :PlayApi, apiproductsId :PlayApi, name: String, action: String)(using auth: AuthToken, ec: ExecutionContext): updateAppGroupAppKeyApiProduct = new updateAppGroupAppKeyApiProduct(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/appgroups/${appgroupsId}/apps/${appsId}/keys/${keysId}/apiproducts/${apiproductsId}")).addQueryStringParameters("name" -> name.toString, "action" -> action.toString))
							given Conversion[updateAppGroupAppKeyApiProduct, Future[Schema.GoogleProtobufEmpty]] = (fun: updateAppGroupAppKeyApiProduct) => fun.apply()
						}
					}
				}
			}
		}
		object keyvaluemaps {
			class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1KeyValueMap]) {
				def apply() = req.execute("DELETE").map(_.json.as[Schema.GoogleCloudApigeeV1KeyValueMap])
			}
			object delete {
				def apply(organizationsId :PlayApi, keyvaluemapsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/keyvaluemaps/${keyvaluemapsId}")).addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.GoogleCloudApigeeV1KeyValueMap]] = (fun: delete) => fun.apply()
			}
			class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGoogleCloudApigeeV1KeyValueMap(body: Schema.GoogleCloudApigeeV1KeyValueMap) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleCloudApigeeV1KeyValueMap])
			}
			object create {
				def apply(organizationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/keyvaluemaps")).addQueryStringParameters("parent" -> parent.toString))
			}
			object entries {
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudApigeeV1KeyValueEntry(body: Schema.GoogleCloudApigeeV1KeyValueEntry) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleCloudApigeeV1KeyValueEntry])
				}
				object create {
					def apply(organizationsId :PlayApi, keyvaluemapsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/keyvaluemaps/${keyvaluemapsId}/entries")).addQueryStringParameters("parent" -> parent.toString))
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1KeyValueEntry]) {
					def apply() = req.execute("DELETE").map(_.json.as[Schema.GoogleCloudApigeeV1KeyValueEntry])
				}
				object delete {
					def apply(organizationsId :PlayApi, keyvaluemapsId :PlayApi, entriesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/keyvaluemaps/${keyvaluemapsId}/entries/${entriesId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.GoogleCloudApigeeV1KeyValueEntry]] = (fun: delete) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1KeyValueEntry]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudApigeeV1KeyValueEntry])
				}
				object get {
					def apply(organizationsId :PlayApi, keyvaluemapsId :PlayApi, entriesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/keyvaluemaps/${keyvaluemapsId}/entries/${entriesId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleCloudApigeeV1KeyValueEntry]] = (fun: get) => fun.apply()
				}
				class update(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudApigeeV1KeyValueEntry(body: Schema.GoogleCloudApigeeV1KeyValueEntry) = req.withBody(Json.toJson(body)).execute("PUT").map(_.json.as[Schema.GoogleCloudApigeeV1KeyValueEntry])
				}
				object update {
					def apply(organizationsId :PlayApi, keyvaluemapsId :PlayApi, entriesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): update = new update(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/keyvaluemaps/${keyvaluemapsId}/entries/${entriesId}")).addQueryStringParameters("name" -> name.toString))
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1ListKeyValueEntriesResponse]) {
					/** Optional. Maximum number of key value entries to return. If unspecified, at most 100 entries will be returned.<br>Format: int32 */
					def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
					/** Optional. Page token. If provides, must be a valid key value entry returned from a previous call that can be used to retrieve the next page. */
					def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
					def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudApigeeV1ListKeyValueEntriesResponse])
				}
				object list {
					def apply(organizationsId :PlayApi, keyvaluemapsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/keyvaluemaps/${keyvaluemapsId}/entries")).addQueryStringParameters("parent" -> parent.toString))
					given Conversion[list, Future[Schema.GoogleCloudApigeeV1ListKeyValueEntriesResponse]] = (fun: list) => fun.apply()
				}
			}
		}
		object datacollectors {
			class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGoogleCloudApigeeV1DataCollector(body: Schema.GoogleCloudApigeeV1DataCollector) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleCloudApigeeV1DataCollector])
			}
			object create {
				def apply(organizationsId :PlayApi, parent: String, dataCollectorId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/datacollectors")).addQueryStringParameters("parent" -> parent.toString, "dataCollectorId" -> dataCollectorId.toString))
			}
			class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
				def apply() = req.execute("DELETE").map(_.json.as[Schema.GoogleProtobufEmpty])
			}
			object delete {
				def apply(organizationsId :PlayApi, datacollectorsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/datacollectors/${datacollectorsId}")).addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1DataCollector]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudApigeeV1DataCollector])
			}
			object get {
				def apply(organizationsId :PlayApi, datacollectorsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/datacollectors/${datacollectorsId}")).addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.GoogleCloudApigeeV1DataCollector]] = (fun: get) => fun.apply()
			}
			class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGoogleCloudApigeeV1DataCollector(body: Schema.GoogleCloudApigeeV1DataCollector) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.GoogleCloudApigeeV1DataCollector])
			}
			object patch {
				def apply(organizationsId :PlayApi, datacollectorsId :PlayApi, updateMask: String, name: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/datacollectors/${datacollectorsId}")).addQueryStringParameters("updateMask" -> updateMask.toString, "name" -> name.toString))
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1ListDataCollectorsResponse]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudApigeeV1ListDataCollectorsResponse])
			}
			object list {
				def apply(organizationsId :PlayApi, parent: String, pageToken: String, pageSize: Int)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/datacollectors")).addQueryStringParameters("parent" -> parent.toString, "pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString))
				given Conversion[list, Future[Schema.GoogleCloudApigeeV1ListDataCollectorsResponse]] = (fun: list) => fun.apply()
			}
		}
		object securityProfilesV2 {
			class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGoogleCloudApigeeV1SecurityProfileV2(body: Schema.GoogleCloudApigeeV1SecurityProfileV2) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleCloudApigeeV1SecurityProfileV2])
			}
			object create {
				def apply(organizationsId :PlayApi, securityProfileV2Id: String, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/securityProfilesV2")).addQueryStringParameters("securityProfileV2Id" -> securityProfileV2Id.toString, "parent" -> parent.toString))
			}
			class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
				def apply() = req.execute("DELETE").map(_.json.as[Schema.GoogleProtobufEmpty])
			}
			object delete {
				def apply(organizationsId :PlayApi, securityProfilesV2Id :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/securityProfilesV2/${securityProfilesV2Id}")).addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1SecurityProfileV2]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudApigeeV1SecurityProfileV2])
			}
			object get {
				def apply(organizationsId :PlayApi, securityProfilesV2Id :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/securityProfilesV2/${securityProfilesV2Id}")).addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.GoogleCloudApigeeV1SecurityProfileV2]] = (fun: get) => fun.apply()
			}
			class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				/** Optional. The list of fields to update. Valid fields to update are `description` and `profileAssessmentConfigs`.<br>Format: google-fieldmask */
				def withUpdateMask(updateMask: String) = new patch(req.addQueryStringParameters("updateMask" -> updateMask.toString))
				def withGoogleCloudApigeeV1SecurityProfileV2(body: Schema.GoogleCloudApigeeV1SecurityProfileV2) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.GoogleCloudApigeeV1SecurityProfileV2])
			}
			object patch {
				def apply(organizationsId :PlayApi, securityProfilesV2Id :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/securityProfilesV2/${securityProfilesV2Id}")).addQueryStringParameters("name" -> name.toString))
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1ListSecurityProfilesV2Response]) {
				/** Optional. The maximum number of profiles to return<br>Format: int32 */
				def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
				/** Optional. A page token, received from a previous `ListSecurityProfilesV2` call. Provide this to retrieve the subsequent page. */
				def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
				def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudApigeeV1ListSecurityProfilesV2Response])
			}
			object list {
				def apply(organizationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/securityProfilesV2")).addQueryStringParameters("parent" -> parent.toString))
				given Conversion[list, Future[Schema.GoogleCloudApigeeV1ListSecurityProfilesV2Response]] = (fun: list) => fun.apply()
			}
		}
		object optimizedHostStats {
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1OptimizedStats]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudApigeeV1OptimizedStats])
			}
			object get {
				def apply(organizationsId :PlayApi, optimizedHostStatsId :PlayApi, envgroupHostname: String, accuracy: String, name: String, topk: String, tsAscending: Boolean, offset: String, filter: String, timeRange: String, tzo: String, sort: String, timeUnit: String, limit: String, select: String, sortby: String, realtime: Boolean)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/optimizedHostStats/${optimizedHostStatsId}")).addQueryStringParameters("envgroupHostname" -> envgroupHostname.toString, "accuracy" -> accuracy.toString, "name" -> name.toString, "topk" -> topk.toString, "tsAscending" -> tsAscending.toString, "offset" -> offset.toString, "filter" -> filter.toString, "timeRange" -> timeRange.toString, "tzo" -> tzo.toString, "sort" -> sort.toString, "timeUnit" -> timeUnit.toString, "limit" -> limit.toString, "select" -> select.toString, "sortby" -> sortby.toString, "realtime" -> realtime.toString))
				given Conversion[get, Future[Schema.GoogleCloudApigeeV1OptimizedStats]] = (fun: get) => fun.apply()
			}
		}
		object instances {
			class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGoogleCloudApigeeV1Instance(body: Schema.GoogleCloudApigeeV1Instance) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleLongrunningOperation])
			}
			object create {
				def apply(organizationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/instances")).addQueryStringParameters("parent" -> parent.toString))
			}
			class reportStatus(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGoogleCloudApigeeV1ReportInstanceStatusRequest(body: Schema.GoogleCloudApigeeV1ReportInstanceStatusRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleCloudApigeeV1ReportInstanceStatusResponse])
			}
			object reportStatus {
				def apply(organizationsId :PlayApi, instancesId :PlayApi, instance: String)(using auth: AuthToken, ec: ExecutionContext): reportStatus = new reportStatus(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/instances/${instancesId}:reportStatus")).addQueryStringParameters("instance" -> instance.toString))
			}
			class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningOperation]) {
				def apply() = req.execute("DELETE").map(_.json.as[Schema.GoogleLongrunningOperation])
			}
			object delete {
				def apply(organizationsId :PlayApi, instancesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/instances/${instancesId}")).addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.GoogleLongrunningOperation]] = (fun: delete) => fun.apply()
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1Instance]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudApigeeV1Instance])
			}
			object get {
				def apply(organizationsId :PlayApi, instancesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/instances/${instancesId}")).addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.GoogleCloudApigeeV1Instance]] = (fun: get) => fun.apply()
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1ListInstancesResponse]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudApigeeV1ListInstancesResponse])
			}
			object list {
				def apply(organizationsId :PlayApi, pageToken: String, pageSize: Int, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/instances")).addQueryStringParameters("pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString, "parent" -> parent.toString))
				given Conversion[list, Future[Schema.GoogleCloudApigeeV1ListInstancesResponse]] = (fun: list) => fun.apply()
			}
			class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGoogleCloudApigeeV1Instance(body: Schema.GoogleCloudApigeeV1Instance) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.GoogleLongrunningOperation])
			}
			object patch {
				def apply(organizationsId :PlayApi, instancesId :PlayApi, updateMask: String, name: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/instances/${instancesId}")).addQueryStringParameters("updateMask" -> updateMask.toString, "name" -> name.toString))
			}
			object attachments {
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1ListInstanceAttachmentsResponse]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudApigeeV1ListInstanceAttachmentsResponse])
				}
				object list {
					def apply(organizationsId :PlayApi, instancesId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/instances/${instancesId}/attachments")).addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.GoogleCloudApigeeV1ListInstanceAttachmentsResponse]] = (fun: list) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1InstanceAttachment]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudApigeeV1InstanceAttachment])
				}
				object get {
					def apply(organizationsId :PlayApi, instancesId :PlayApi, attachmentsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/instances/${instancesId}/attachments/${attachmentsId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleCloudApigeeV1InstanceAttachment]] = (fun: get) => fun.apply()
				}
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudApigeeV1InstanceAttachment(body: Schema.GoogleCloudApigeeV1InstanceAttachment) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleLongrunningOperation])
				}
				object create {
					def apply(organizationsId :PlayApi, instancesId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/instances/${instancesId}/attachments")).addQueryStringParameters("parent" -> parent.toString))
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningOperation]) {
					def apply() = req.execute("DELETE").map(_.json.as[Schema.GoogleLongrunningOperation])
				}
				object delete {
					def apply(organizationsId :PlayApi, instancesId :PlayApi, attachmentsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/instances/${instancesId}/attachments/${attachmentsId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.GoogleLongrunningOperation]] = (fun: delete) => fun.apply()
				}
			}
			object natAddresses {
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudApigeeV1NatAddress(body: Schema.GoogleCloudApigeeV1NatAddress) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleLongrunningOperation])
				}
				object create {
					def apply(organizationsId :PlayApi, instancesId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/instances/${instancesId}/natAddresses")).addQueryStringParameters("parent" -> parent.toString))
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningOperation]) {
					def apply() = req.execute("DELETE").map(_.json.as[Schema.GoogleLongrunningOperation])
				}
				object delete {
					def apply(organizationsId :PlayApi, instancesId :PlayApi, natAddressesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/instances/${instancesId}/natAddresses/${natAddressesId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.GoogleLongrunningOperation]] = (fun: delete) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1NatAddress]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudApigeeV1NatAddress])
				}
				object get {
					def apply(organizationsId :PlayApi, instancesId :PlayApi, natAddressesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/instances/${instancesId}/natAddresses/${natAddressesId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleCloudApigeeV1NatAddress]] = (fun: get) => fun.apply()
				}
				class activate(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudApigeeV1ActivateNatAddressRequest(body: Schema.GoogleCloudApigeeV1ActivateNatAddressRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleLongrunningOperation])
				}
				object activate {
					def apply(organizationsId :PlayApi, instancesId :PlayApi, natAddressesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): activate = new activate(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/instances/${instancesId}/natAddresses/${natAddressesId}:activate")).addQueryStringParameters("name" -> name.toString))
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1ListNatAddressesResponse]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudApigeeV1ListNatAddressesResponse])
				}
				object list {
					def apply(organizationsId :PlayApi, instancesId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/instances/${instancesId}/natAddresses")).addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.GoogleCloudApigeeV1ListNatAddressesResponse]] = (fun: list) => fun.apply()
				}
			}
			object canaryevaluations {
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1CanaryEvaluation]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudApigeeV1CanaryEvaluation])
				}
				object get {
					def apply(organizationsId :PlayApi, instancesId :PlayApi, canaryevaluationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/instances/${instancesId}/canaryevaluations/${canaryevaluationsId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleCloudApigeeV1CanaryEvaluation]] = (fun: get) => fun.apply()
				}
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudApigeeV1CanaryEvaluation(body: Schema.GoogleCloudApigeeV1CanaryEvaluation) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleLongrunningOperation])
				}
				object create {
					def apply(organizationsId :PlayApi, instancesId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/instances/${instancesId}/canaryevaluations")).addQueryStringParameters("parent" -> parent.toString))
				}
			}
		}
		object sharedflows {
			class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGoogleApiHttpBody(body: Schema.GoogleApiHttpBody) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleCloudApigeeV1SharedFlowRevision])
			}
			object create {
				def apply(organizationsId :PlayApi, name: String, parent: String, action: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/sharedflows")).addQueryStringParameters("name" -> name.toString, "parent" -> parent.toString, "action" -> action.toString))
			}
			class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1SharedFlow]) {
				def apply() = req.execute("DELETE").map(_.json.as[Schema.GoogleCloudApigeeV1SharedFlow])
			}
			object delete {
				def apply(organizationsId :PlayApi, sharedflowsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/sharedflows/${sharedflowsId}")).addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.GoogleCloudApigeeV1SharedFlow]] = (fun: delete) => fun.apply()
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1SharedFlow]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudApigeeV1SharedFlow])
			}
			object get {
				def apply(organizationsId :PlayApi, sharedflowsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/sharedflows/${sharedflowsId}")).addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.GoogleCloudApigeeV1SharedFlow]] = (fun: get) => fun.apply()
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1ListSharedFlowsResponse]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudApigeeV1ListSharedFlowsResponse])
			}
			object list {
				def apply(organizationsId :PlayApi, includeMetaData: Boolean, parent: String, includeRevisions: Boolean)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/sharedflows")).addQueryStringParameters("includeMetaData" -> includeMetaData.toString, "parent" -> parent.toString, "includeRevisions" -> includeRevisions.toString))
				given Conversion[list, Future[Schema.GoogleCloudApigeeV1ListSharedFlowsResponse]] = (fun: list) => fun.apply()
			}
			object deployments {
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1ListDeploymentsResponse]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudApigeeV1ListDeploymentsResponse])
				}
				object list {
					def apply(organizationsId :PlayApi, sharedflowsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/sharedflows/${sharedflowsId}/deployments")).addQueryStringParameters("parent" -> parent.toString))
					given Conversion[list, Future[Schema.GoogleCloudApigeeV1ListDeploymentsResponse]] = (fun: list) => fun.apply()
				}
			}
			object revisions {
				class updateSharedFlowRevision(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleApiHttpBody(body: Schema.GoogleApiHttpBody) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleCloudApigeeV1SharedFlowRevision])
				}
				object updateSharedFlowRevision {
					def apply(organizationsId :PlayApi, sharedflowsId :PlayApi, revisionsId :PlayApi, validate: Boolean, name: String)(using auth: AuthToken, ec: ExecutionContext): updateSharedFlowRevision = new updateSharedFlowRevision(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/sharedflows/${sharedflowsId}/revisions/${revisionsId}")).addQueryStringParameters("validate" -> validate.toString, "name" -> name.toString))
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleApiHttpBody]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.GoogleApiHttpBody])
				}
				object get {
					def apply(organizationsId :PlayApi, sharedflowsId :PlayApi, revisionsId :PlayApi, format: String, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/sharedflows/${sharedflowsId}/revisions/${revisionsId}")).addQueryStringParameters("format" -> format.toString, "name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleApiHttpBody]] = (fun: get) => fun.apply()
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1SharedFlowRevision]) {
					def apply() = req.execute("DELETE").map(_.json.as[Schema.GoogleCloudApigeeV1SharedFlowRevision])
				}
				object delete {
					def apply(organizationsId :PlayApi, sharedflowsId :PlayApi, revisionsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/sharedflows/${sharedflowsId}/revisions/${revisionsId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.GoogleCloudApigeeV1SharedFlowRevision]] = (fun: delete) => fun.apply()
				}
				object deployments {
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1ListDeploymentsResponse]) {
						def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudApigeeV1ListDeploymentsResponse])
					}
					object list {
						def apply(organizationsId :PlayApi, sharedflowsId :PlayApi, revisionsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/sharedflows/${sharedflowsId}/revisions/${revisionsId}/deployments")).addQueryStringParameters("parent" -> parent.toString))
						given Conversion[list, Future[Schema.GoogleCloudApigeeV1ListDeploymentsResponse]] = (fun: list) => fun.apply()
					}
				}
			}
		}
		object reports {
			class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGoogleCloudApigeeV1CustomReport(body: Schema.GoogleCloudApigeeV1CustomReport) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleCloudApigeeV1CustomReport])
			}
			object create {
				def apply(organizationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/reports")).addQueryStringParameters("parent" -> parent.toString))
			}
			class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1DeleteCustomReportResponse]) {
				def apply() = req.execute("DELETE").map(_.json.as[Schema.GoogleCloudApigeeV1DeleteCustomReportResponse])
			}
			object delete {
				def apply(organizationsId :PlayApi, reportsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/reports/${reportsId}")).addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.GoogleCloudApigeeV1DeleteCustomReportResponse]] = (fun: delete) => fun.apply()
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1CustomReport]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudApigeeV1CustomReport])
			}
			object get {
				def apply(organizationsId :PlayApi, reportsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/reports/${reportsId}")).addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.GoogleCloudApigeeV1CustomReport]] = (fun: get) => fun.apply()
			}
			class update(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGoogleCloudApigeeV1CustomReport(body: Schema.GoogleCloudApigeeV1CustomReport) = req.withBody(Json.toJson(body)).execute("PUT").map(_.json.as[Schema.GoogleCloudApigeeV1CustomReport])
			}
			object update {
				def apply(organizationsId :PlayApi, reportsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): update = new update(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/reports/${reportsId}")).addQueryStringParameters("name" -> name.toString))
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1ListCustomReportsResponse]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudApigeeV1ListCustomReportsResponse])
			}
			object list {
				def apply(organizationsId :PlayApi, parent: String, expand: Boolean)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/reports")).addQueryStringParameters("parent" -> parent.toString, "expand" -> expand.toString))
				given Conversion[list, Future[Schema.GoogleCloudApigeeV1ListCustomReportsResponse]] = (fun: list) => fun.apply()
			}
		}
		object analytics {
			object datastores {
				class test(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudApigeeV1Datastore(body: Schema.GoogleCloudApigeeV1Datastore) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleCloudApigeeV1TestDatastoreResponse])
				}
				object test {
					def apply(organizationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): test = new test(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/analytics/datastores:test")).addQueryStringParameters("parent" -> parent.toString))
				}
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudApigeeV1Datastore(body: Schema.GoogleCloudApigeeV1Datastore) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleCloudApigeeV1Datastore])
				}
				object create {
					def apply(organizationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/analytics/datastores")).addQueryStringParameters("parent" -> parent.toString))
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
					def apply() = req.execute("DELETE").map(_.json.as[Schema.GoogleProtobufEmpty])
				}
				object delete {
					def apply(organizationsId :PlayApi, datastoresId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/analytics/datastores/${datastoresId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1Datastore]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudApigeeV1Datastore])
				}
				object get {
					def apply(organizationsId :PlayApi, datastoresId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/analytics/datastores/${datastoresId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleCloudApigeeV1Datastore]] = (fun: get) => fun.apply()
				}
				class update(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudApigeeV1Datastore(body: Schema.GoogleCloudApigeeV1Datastore) = req.withBody(Json.toJson(body)).execute("PUT").map(_.json.as[Schema.GoogleCloudApigeeV1Datastore])
				}
				object update {
					def apply(organizationsId :PlayApi, datastoresId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): update = new update(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/analytics/datastores/${datastoresId}")).addQueryStringParameters("name" -> name.toString))
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1ListDatastoresResponse]) {
					/** Optional. TargetType is used to fetch all Datastores that match the type */
					def withTargetType(targetType: String) = new list(req.addQueryStringParameters("targetType" -> targetType.toString))
					def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudApigeeV1ListDatastoresResponse])
				}
				object list {
					def apply(organizationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/analytics/datastores")).addQueryStringParameters("parent" -> parent.toString))
					given Conversion[list, Future[Schema.GoogleCloudApigeeV1ListDatastoresResponse]] = (fun: list) => fun.apply()
				}
			}
		}
		object securityAssessmentResults {
			class batchCompute(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGoogleCloudApigeeV1BatchComputeSecurityAssessmentResultsRequest(body: Schema.GoogleCloudApigeeV1BatchComputeSecurityAssessmentResultsRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleCloudApigeeV1BatchComputeSecurityAssessmentResultsResponse])
			}
			object batchCompute {
				def apply(organizationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): batchCompute = new batchCompute(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/securityAssessmentResults:batchCompute")).addQueryStringParameters("name" -> name.toString))
			}
		}
		object envgroups {
			class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				/** Optional. ID of the environment group. Overrides any ID in the environment_group resource. */
				def withName(name: String) = new create(req.addQueryStringParameters("name" -> name.toString))
				def withGoogleCloudApigeeV1EnvironmentGroup(body: Schema.GoogleCloudApigeeV1EnvironmentGroup) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleLongrunningOperation])
			}
			object create {
				def apply(organizationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/envgroups")).addQueryStringParameters("parent" -> parent.toString))
			}
			class getDeployedIngressConfig(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1EnvironmentGroupConfig]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudApigeeV1EnvironmentGroupConfig])
			}
			object getDeployedIngressConfig {
				def apply(organizationsId :PlayApi, envgroupsId :PlayApi, view: String, name: String)(using auth: AuthToken, ec: ExecutionContext): getDeployedIngressConfig = new getDeployedIngressConfig(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/envgroups/${envgroupsId}/deployedIngressConfig")).addQueryStringParameters("view" -> view.toString, "name" -> name.toString))
				given Conversion[getDeployedIngressConfig, Future[Schema.GoogleCloudApigeeV1EnvironmentGroupConfig]] = (fun: getDeployedIngressConfig) => fun.apply()
			}
			class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningOperation]) {
				def apply() = req.execute("DELETE").map(_.json.as[Schema.GoogleLongrunningOperation])
			}
			object delete {
				def apply(organizationsId :PlayApi, envgroupsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/envgroups/${envgroupsId}")).addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.GoogleLongrunningOperation]] = (fun: delete) => fun.apply()
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1EnvironmentGroup]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudApigeeV1EnvironmentGroup])
			}
			object get {
				def apply(organizationsId :PlayApi, envgroupsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/envgroups/${envgroupsId}")).addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.GoogleCloudApigeeV1EnvironmentGroup]] = (fun: get) => fun.apply()
			}
			class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				/** Optional. List of fields to be updated.<br>Format: google-fieldmask */
				def withUpdateMask(updateMask: String) = new patch(req.addQueryStringParameters("updateMask" -> updateMask.toString))
				def withGoogleCloudApigeeV1EnvironmentGroup(body: Schema.GoogleCloudApigeeV1EnvironmentGroup) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.GoogleLongrunningOperation])
			}
			object patch {
				def apply(organizationsId :PlayApi, envgroupsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/envgroups/${envgroupsId}")).addQueryStringParameters("name" -> name.toString))
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1ListEnvironmentGroupsResponse]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudApigeeV1ListEnvironmentGroupsResponse])
			}
			object list {
				def apply(organizationsId :PlayApi, pageToken: String, pageSize: Int, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/envgroups")).addQueryStringParameters("pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString, "parent" -> parent.toString))
				given Conversion[list, Future[Schema.GoogleCloudApigeeV1ListEnvironmentGroupsResponse]] = (fun: list) => fun.apply()
			}
			object attachments {
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningOperation]) {
					def apply() = req.execute("DELETE").map(_.json.as[Schema.GoogleLongrunningOperation])
				}
				object delete {
					def apply(organizationsId :PlayApi, envgroupsId :PlayApi, attachmentsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/envgroups/${envgroupsId}/attachments/${attachmentsId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.GoogleLongrunningOperation]] = (fun: delete) => fun.apply()
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1ListEnvironmentGroupAttachmentsResponse]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudApigeeV1ListEnvironmentGroupAttachmentsResponse])
				}
				object list {
					def apply(organizationsId :PlayApi, envgroupsId :PlayApi, parent: String, pageToken: String, pageSize: Int)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/envgroups/${envgroupsId}/attachments")).addQueryStringParameters("parent" -> parent.toString, "pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString))
					given Conversion[list, Future[Schema.GoogleCloudApigeeV1ListEnvironmentGroupAttachmentsResponse]] = (fun: list) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1EnvironmentGroupAttachment]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudApigeeV1EnvironmentGroupAttachment])
				}
				object get {
					def apply(organizationsId :PlayApi, envgroupsId :PlayApi, attachmentsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/envgroups/${envgroupsId}/attachments/${attachmentsId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleCloudApigeeV1EnvironmentGroupAttachment]] = (fun: get) => fun.apply()
				}
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudApigeeV1EnvironmentGroupAttachment(body: Schema.GoogleCloudApigeeV1EnvironmentGroupAttachment) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleLongrunningOperation])
				}
				object create {
					def apply(organizationsId :PlayApi, envgroupsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/envgroups/${envgroupsId}/attachments")).addQueryStringParameters("parent" -> parent.toString))
				}
			}
		}
		object sites {
			object apidocs {
				class updateDocumentation(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudApigeeV1ApiDocDocumentation(body: Schema.GoogleCloudApigeeV1ApiDocDocumentation) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.GoogleCloudApigeeV1ApiDocDocumentationResponse])
				}
				object updateDocumentation {
					def apply(organizationsId :PlayApi, sitesId :PlayApi, apidocsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): updateDocumentation = new updateDocumentation(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/sites/${sitesId}/apidocs/${apidocsId}/documentation")).addQueryStringParameters("name" -> name.toString))
				}
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudApigeeV1ApiDoc(body: Schema.GoogleCloudApigeeV1ApiDoc) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleCloudApigeeV1ApiDocResponse])
				}
				object create {
					def apply(organizationsId :PlayApi, sitesId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/sites/${sitesId}/apidocs")).addQueryStringParameters("parent" -> parent.toString))
				}
				class getDocumentation(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1ApiDocDocumentationResponse]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudApigeeV1ApiDocDocumentationResponse])
				}
				object getDocumentation {
					def apply(organizationsId :PlayApi, sitesId :PlayApi, apidocsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): getDocumentation = new getDocumentation(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/sites/${sitesId}/apidocs/${apidocsId}/documentation")).addQueryStringParameters("name" -> name.toString))
					given Conversion[getDocumentation, Future[Schema.GoogleCloudApigeeV1ApiDocDocumentationResponse]] = (fun: getDocumentation) => fun.apply()
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1DeleteResponse]) {
					def apply() = req.execute("DELETE").map(_.json.as[Schema.GoogleCloudApigeeV1DeleteResponse])
				}
				object delete {
					def apply(organizationsId :PlayApi, sitesId :PlayApi, apidocsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/sites/${sitesId}/apidocs/${apidocsId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.GoogleCloudApigeeV1DeleteResponse]] = (fun: delete) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1ApiDocResponse]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudApigeeV1ApiDocResponse])
				}
				object get {
					def apply(organizationsId :PlayApi, sitesId :PlayApi, apidocsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/sites/${sitesId}/apidocs/${apidocsId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleCloudApigeeV1ApiDocResponse]] = (fun: get) => fun.apply()
				}
				class update(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudApigeeV1ApiDoc(body: Schema.GoogleCloudApigeeV1ApiDoc) = req.withBody(Json.toJson(body)).execute("PUT").map(_.json.as[Schema.GoogleCloudApigeeV1ApiDocResponse])
				}
				object update {
					def apply(organizationsId :PlayApi, sitesId :PlayApi, apidocsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): update = new update(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/sites/${sitesId}/apidocs/${apidocsId}")).addQueryStringParameters("name" -> name.toString))
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1ListApiDocsResponse]) {
					/** Optional. A page token, received from a previous `ListApiDocs` call. Provide this to retrieve the subsequent page. */
					def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
					/** Optional. The maximum number of items to return. The service may return fewer than this value. If unspecified, at most 25 books will be returned. The maximum value is 100; values above 100 will be coerced to 100.<br>Format: int32 */
					def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
					def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudApigeeV1ListApiDocsResponse])
				}
				object list {
					def apply(organizationsId :PlayApi, sitesId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/sites/${sitesId}/apidocs")).addQueryStringParameters("parent" -> parent.toString))
					given Conversion[list, Future[Schema.GoogleCloudApigeeV1ListApiDocsResponse]] = (fun: list) => fun.apply()
				}
			}
			object apicategories {
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudApigeeV1ApiCategory(body: Schema.GoogleCloudApigeeV1ApiCategory) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleCloudApigeeV1ApiCategoryResponse])
				}
				object create {
					def apply(organizationsId :PlayApi, sitesId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/sites/${sitesId}/apicategories")).addQueryStringParameters("parent" -> parent.toString))
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1DeleteResponse]) {
					def apply() = req.execute("DELETE").map(_.json.as[Schema.GoogleCloudApigeeV1DeleteResponse])
				}
				object delete {
					def apply(organizationsId :PlayApi, sitesId :PlayApi, apicategoriesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/sites/${sitesId}/apicategories/${apicategoriesId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.GoogleCloudApigeeV1DeleteResponse]] = (fun: delete) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1ApiCategoryResponse]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudApigeeV1ApiCategoryResponse])
				}
				object get {
					def apply(organizationsId :PlayApi, sitesId :PlayApi, apicategoriesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/sites/${sitesId}/apicategories/${apicategoriesId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleCloudApigeeV1ApiCategoryResponse]] = (fun: get) => fun.apply()
				}
				class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudApigeeV1ApiCategory(body: Schema.GoogleCloudApigeeV1ApiCategory) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.GoogleCloudApigeeV1ApiCategoryResponse])
				}
				object patch {
					def apply(organizationsId :PlayApi, sitesId :PlayApi, apicategoriesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/sites/${sitesId}/apicategories/${apicategoriesId}")).addQueryStringParameters("name" -> name.toString))
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1ListApiCategoriesResponse]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudApigeeV1ListApiCategoriesResponse])
				}
				object list {
					def apply(organizationsId :PlayApi, sitesId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/sites/${sitesId}/apicategories")).addQueryStringParameters("parent" -> parent.toString))
					given Conversion[list, Future[Schema.GoogleCloudApigeeV1ListApiCategoriesResponse]] = (fun: list) => fun.apply()
				}
			}
		}
		object deployments {
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1ListDeploymentsResponse]) {
				/** Optional. Flag that specifies whether to return shared flow or API proxy deployments. Set to `true` to return shared flow deployments; set to `false` to return API proxy deployments. Defaults to `false`. */
				def withSharedFlows(sharedFlows: Boolean) = new list(req.addQueryStringParameters("sharedFlows" -> sharedFlows.toString))
				def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudApigeeV1ListDeploymentsResponse])
			}
			object list {
				def apply(organizationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/deployments")).addQueryStringParameters("parent" -> parent.toString))
				given Conversion[list, Future[Schema.GoogleCloudApigeeV1ListDeploymentsResponse]] = (fun: list) => fun.apply()
			}
		}
		object hostSecurityReports {
			class getResult(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleApiHttpBody]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.GoogleApiHttpBody])
			}
			object getResult {
				def apply(organizationsId :PlayApi, hostSecurityReportsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): getResult = new getResult(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/hostSecurityReports/${hostSecurityReportsId}/result")).addQueryStringParameters("name" -> name.toString))
				given Conversion[getResult, Future[Schema.GoogleApiHttpBody]] = (fun: getResult) => fun.apply()
			}
			class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGoogleCloudApigeeV1SecurityReportQuery(body: Schema.GoogleCloudApigeeV1SecurityReportQuery) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleCloudApigeeV1SecurityReport])
			}
			object create {
				def apply(organizationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/hostSecurityReports")).addQueryStringParameters("parent" -> parent.toString))
			}
			class getResultView(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1SecurityReportResultView]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudApigeeV1SecurityReportResultView])
			}
			object getResultView {
				def apply(organizationsId :PlayApi, hostSecurityReportsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): getResultView = new getResultView(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/hostSecurityReports/${hostSecurityReportsId}/resultView")).addQueryStringParameters("name" -> name.toString))
				given Conversion[getResultView, Future[Schema.GoogleCloudApigeeV1SecurityReportResultView]] = (fun: getResultView) => fun.apply()
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1SecurityReport]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudApigeeV1SecurityReport])
			}
			object get {
				def apply(organizationsId :PlayApi, hostSecurityReportsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/hostSecurityReports/${hostSecurityReportsId}")).addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.GoogleCloudApigeeV1SecurityReport]] = (fun: get) => fun.apply()
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1ListSecurityReportsResponse]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudApigeeV1ListSecurityReportsResponse])
			}
			object list {
				def apply(organizationsId :PlayApi, pageSize: Int, submittedBy: String, pageToken: String, envgroupHostname: String, to: String, from: String, parent: String, status: String, dataset: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/hostSecurityReports")).addQueryStringParameters("pageSize" -> pageSize.toString, "submittedBy" -> submittedBy.toString, "pageToken" -> pageToken.toString, "envgroupHostname" -> envgroupHostname.toString, "to" -> to.toString, "from" -> from.toString, "parent" -> parent.toString, "status" -> status.toString, "dataset" -> dataset.toString))
				given Conversion[list, Future[Schema.GoogleCloudApigeeV1ListSecurityReportsResponse]] = (fun: list) => fun.apply()
			}
		}
		object hostQueries {
			class getResult(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleApiHttpBody]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.GoogleApiHttpBody])
			}
			object getResult {
				def apply(organizationsId :PlayApi, hostQueriesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): getResult = new getResult(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/hostQueries/${hostQueriesId}/result")).addQueryStringParameters("name" -> name.toString))
				given Conversion[getResult, Future[Schema.GoogleApiHttpBody]] = (fun: getResult) => fun.apply()
			}
			class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGoogleCloudApigeeV1Query(body: Schema.GoogleCloudApigeeV1Query) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleCloudApigeeV1AsyncQuery])
			}
			object create {
				def apply(organizationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/hostQueries")).addQueryStringParameters("parent" -> parent.toString))
			}
			class getResultView(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1AsyncQueryResultView]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudApigeeV1AsyncQueryResultView])
			}
			object getResultView {
				def apply(organizationsId :PlayApi, hostQueriesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): getResultView = new getResultView(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/hostQueries/${hostQueriesId}/resultView")).addQueryStringParameters("name" -> name.toString))
				given Conversion[getResultView, Future[Schema.GoogleCloudApigeeV1AsyncQueryResultView]] = (fun: getResultView) => fun.apply()
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1AsyncQuery]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudApigeeV1AsyncQuery])
			}
			object get {
				def apply(organizationsId :PlayApi, hostQueriesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/hostQueries/${hostQueriesId}")).addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.GoogleCloudApigeeV1AsyncQuery]] = (fun: get) => fun.apply()
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1ListAsyncQueriesResponse]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudApigeeV1ListAsyncQueriesResponse])
			}
			object list {
				def apply(organizationsId :PlayApi, envgroupHostname: String, submittedBy: String, from: String, to: String, parent: String, status: String, inclQueriesWithoutReport: String, dataset: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/hostQueries")).addQueryStringParameters("envgroupHostname" -> envgroupHostname.toString, "submittedBy" -> submittedBy.toString, "from" -> from.toString, "to" -> to.toString, "parent" -> parent.toString, "status" -> status.toString, "inclQueriesWithoutReport" -> inclQueriesWithoutReport.toString, "dataset" -> dataset.toString))
				given Conversion[list, Future[Schema.GoogleCloudApigeeV1ListAsyncQueriesResponse]] = (fun: list) => fun.apply()
			}
		}
		object apps {
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1App]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudApigeeV1App])
			}
			object get {
				def apply(organizationsId :PlayApi, appsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/apps/${appsId}")).addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.GoogleCloudApigeeV1App]] = (fun: get) => fun.apply()
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1ListAppsResponse]) {
				/** Optional. Flag that specifies whether to include credentials in the response. */
				def withIncludeCred(includeCred: Boolean) = new list(req.addQueryStringParameters("includeCred" -> includeCred.toString))
				/** Optional. Comma-separated list of app IDs on which to filter. */
				def withIds(ids: String) = new list(req.addQueryStringParameters("ids" -> ids.toString))
				/** Optional. 'apptype' is no longer available. Use a 'filter' instead. */
				def withApptype(apptype: String) = new list(req.addQueryStringParameters("apptype" -> apptype.toString))
				/** Optional. Maximum number of app IDs to return. Defaults to 10000.<br>Format: int64 */
				def withRows(rows: String) = new list(req.addQueryStringParameters("rows" -> rows.toString))
				/** Optional. Count of apps a single page can have in the response. If unspecified, at most 100 apps will be returned. The maximum value is 100; values above 100 will be coerced to 100. "page_size" is supported from ver 1.10.0 and above.<br>Format: int32 */
				def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
				/** Optional. Key status of the app. Valid values include `approved` or `revoked`. Defaults to `approved`. */
				def withKeyStatus(keyStatus: String) = new list(req.addQueryStringParameters("keyStatus" -> keyStatus.toString))
				/** Optional. The starting index record for listing the developers. "page_token" is supported from ver 1.10.0 and above. */
				def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
				/** Optional. Filter by the status of the app. Valid values are `approved` or `revoked`. Defaults to `approved`. */
				def withStatus(status: String) = new list(req.addQueryStringParameters("status" -> status.toString))
				/** Optional. Flag that specifies whether to return an expanded list of apps for the organization. Defaults to `false`. */
				def withExpand(expand: Boolean) = new list(req.addQueryStringParameters("expand" -> expand.toString))
				/** Optional. The filter expression to be used to get the list of apps, where filtering can be done on developerEmail, apiProduct, consumerKey, status, appId, appName, appType and appGroup. Examples: "developerEmail=foo@bar.com", "appType=AppGroup", or "appType=Developer" "filter" is supported from ver 1.10.0 and above. */
				def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
				def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudApigeeV1ListAppsResponse])
			}
			object list {
				def apply(organizationsId :PlayApi, startKey: String, parent: String, apiProduct: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/apps")).addQueryStringParameters("startKey" -> startKey.toString, "parent" -> parent.toString, "apiProduct" -> apiProduct.toString))
				given Conversion[list, Future[Schema.GoogleCloudApigeeV1ListAppsResponse]] = (fun: list) => fun.apply()
			}
		}
	}
	object projects {
		class provisionOrganization(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withGoogleCloudApigeeV1ProvisionOrganizationRequest(body: Schema.GoogleCloudApigeeV1ProvisionOrganizationRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleLongrunningOperation])
		}
		object provisionOrganization {
			def apply(projectsId :PlayApi, project: String)(using auth: AuthToken, ec: ExecutionContext): provisionOrganization = new provisionOrganization(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}:provisionOrganization")).addQueryStringParameters("project" -> project.toString))
		}
	}
}
