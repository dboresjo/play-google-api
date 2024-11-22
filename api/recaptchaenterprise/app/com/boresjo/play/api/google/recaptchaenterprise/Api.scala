package com.boresjo.play.api.google.recaptchaenterprise

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://recaptchaenterprise.googleapis.com/"

	object projects {
		object relatedaccountgroups {
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudRecaptchaenterpriseV1ListRelatedAccountGroupsResponse]) {
				/** Optional. A page token, received from a previous `ListRelatedAccountGroups` call. Provide this to retrieve the subsequent page. When paginating, all other parameters provided to `ListRelatedAccountGroups` must match the call that provided the page token. */
				def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
				/** Optional. The maximum number of groups to return. The service might return fewer than this value. If unspecified, at most 50 groups are returned. The maximum value is 1000; values above 1000 are coerced to 1000.<br>Format: int32 */
				def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudRecaptchaenterpriseV1ListRelatedAccountGroupsResponse])
			}
			object list {
				def apply(projectsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/relatedaccountgroups").addQueryStringParameters("parent" -> parent.toString))
				given Conversion[list, Future[Schema.GoogleCloudRecaptchaenterpriseV1ListRelatedAccountGroupsResponse]] = (fun: list) => fun.apply()
			}
			object memberships {
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudRecaptchaenterpriseV1ListRelatedAccountGroupMembershipsResponse]) {
					/** Optional. A page token, received from a previous `ListRelatedAccountGroupMemberships` call. When paginating, all other parameters provided to `ListRelatedAccountGroupMemberships` must match the call that provided the page token. */
					def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
					/** Optional. The maximum number of accounts to return. The service might return fewer than this value. If unspecified, at most 50 accounts are returned. The maximum value is 1000; values above 1000 are coerced to 1000.<br>Format: int32 */
					def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudRecaptchaenterpriseV1ListRelatedAccountGroupMembershipsResponse])
				}
				object list {
					def apply(projectsId :PlayApi, relatedaccountgroupsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/relatedaccountgroups/${relatedaccountgroupsId}/memberships").addQueryStringParameters("parent" -> parent.toString))
					given Conversion[list, Future[Schema.GoogleCloudRecaptchaenterpriseV1ListRelatedAccountGroupMembershipsResponse]] = (fun: list) => fun.apply()
				}
			}
		}
		object assessments {
			class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGoogleCloudRecaptchaenterpriseV1Assessment(body: Schema.GoogleCloudRecaptchaenterpriseV1Assessment) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudRecaptchaenterpriseV1Assessment])
			}
			object create {
				def apply(projectsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/assessments").addQueryStringParameters("parent" -> parent.toString))
			}
			class annotate(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGoogleCloudRecaptchaenterpriseV1AnnotateAssessmentRequest(body: Schema.GoogleCloudRecaptchaenterpriseV1AnnotateAssessmentRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudRecaptchaenterpriseV1AnnotateAssessmentResponse])
			}
			object annotate {
				def apply(projectsId :PlayApi, assessmentsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): annotate = new annotate(ws.url(BASE_URL + s"v1/projects/${projectsId}/assessments/${assessmentsId}:annotate").addQueryStringParameters("name" -> name.toString))
			}
		}
		object relatedaccountgroupmemberships {
			class search(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGoogleCloudRecaptchaenterpriseV1SearchRelatedAccountGroupMembershipsRequest(body: Schema.GoogleCloudRecaptchaenterpriseV1SearchRelatedAccountGroupMembershipsRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudRecaptchaenterpriseV1SearchRelatedAccountGroupMembershipsResponse])
			}
			object search {
				def apply(projectsId :PlayApi, project: String)(using auth: AuthToken, ec: ExecutionContext): search = new search(ws.url(BASE_URL + s"v1/projects/${projectsId}/relatedaccountgroupmemberships:search").addQueryStringParameters("project" -> project.toString))
			}
		}
		object firewallpolicies {
			class reorder(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGoogleCloudRecaptchaenterpriseV1ReorderFirewallPoliciesRequest(body: Schema.GoogleCloudRecaptchaenterpriseV1ReorderFirewallPoliciesRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudRecaptchaenterpriseV1ReorderFirewallPoliciesResponse])
			}
			object reorder {
				def apply(projectsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): reorder = new reorder(ws.url(BASE_URL + s"v1/projects/${projectsId}/firewallpolicies:reorder").addQueryStringParameters("parent" -> parent.toString))
			}
			class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGoogleCloudRecaptchaenterpriseV1FirewallPolicy(body: Schema.GoogleCloudRecaptchaenterpriseV1FirewallPolicy) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudRecaptchaenterpriseV1FirewallPolicy])
			}
			object create {
				def apply(projectsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/firewallpolicies").addQueryStringParameters("parent" -> parent.toString))
			}
			class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
				def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
			}
			object delete {
				def apply(projectsId :PlayApi, firewallpoliciesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/firewallpolicies/${firewallpoliciesId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudRecaptchaenterpriseV1FirewallPolicy]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudRecaptchaenterpriseV1FirewallPolicy])
			}
			object get {
				def apply(projectsId :PlayApi, firewallpoliciesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/firewallpolicies/${firewallpoliciesId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.GoogleCloudRecaptchaenterpriseV1FirewallPolicy]] = (fun: get) => fun.apply()
			}
			class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				/** Optional. The mask to control which fields of the policy get updated. If the mask is not present, all fields are updated.<br>Format: google-fieldmask */
				def withUpdateMask(updateMask: String) = new patch(req.addQueryStringParameters("updateMask" -> updateMask.toString))
				def withGoogleCloudRecaptchaenterpriseV1FirewallPolicy(body: Schema.GoogleCloudRecaptchaenterpriseV1FirewallPolicy) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudRecaptchaenterpriseV1FirewallPolicy])
			}
			object patch {
				def apply(projectsId :PlayApi, firewallpoliciesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/firewallpolicies/${firewallpoliciesId}").addQueryStringParameters("name" -> name.toString))
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudRecaptchaenterpriseV1ListFirewallPoliciesResponse]) {
				/** Optional. The next_page_token value returned from a previous. ListFirewallPoliciesRequest, if any. */
				def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
				/** Optional. The maximum number of policies to return. Default is 10. Max limit is 1000.<br>Format: int32 */
				def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudRecaptchaenterpriseV1ListFirewallPoliciesResponse])
			}
			object list {
				def apply(projectsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/firewallpolicies").addQueryStringParameters("parent" -> parent.toString))
				given Conversion[list, Future[Schema.GoogleCloudRecaptchaenterpriseV1ListFirewallPoliciesResponse]] = (fun: list) => fun.apply()
			}
		}
		object keys {
			class listIpOverrides(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudRecaptchaenterpriseV1ListIpOverridesResponse]) {
				/** Optional. The maximum number of overrides to return. Default is 10. Max limit is 100. If the number of overrides is less than the page_size, all overrides are returned. If the page size is more than 100, it is coerced to 100.<br>Format: int32 */
				def withPageSize(pageSize: Int) = new listIpOverrides(req.addQueryStringParameters("pageSize" -> pageSize.toString))
				/** Optional. The next_page_token value returned from a previous ListIpOverridesRequest, if any. */
				def withPageToken(pageToken: String) = new listIpOverrides(req.addQueryStringParameters("pageToken" -> pageToken.toString))
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudRecaptchaenterpriseV1ListIpOverridesResponse])
			}
			object listIpOverrides {
				def apply(projectsId :PlayApi, keysId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): listIpOverrides = new listIpOverrides(ws.url(BASE_URL + s"v1/projects/${projectsId}/keys/${keysId}:listIpOverrides").addQueryStringParameters("parent" -> parent.toString))
				given Conversion[listIpOverrides, Future[Schema.GoogleCloudRecaptchaenterpriseV1ListIpOverridesResponse]] = (fun: listIpOverrides) => fun.apply()
			}
			class retrieveLegacySecretKey(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudRecaptchaenterpriseV1RetrieveLegacySecretKeyResponse]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudRecaptchaenterpriseV1RetrieveLegacySecretKeyResponse])
			}
			object retrieveLegacySecretKey {
				def apply(projectsId :PlayApi, keysId :PlayApi, key: String)(using auth: AuthToken, ec: ExecutionContext): retrieveLegacySecretKey = new retrieveLegacySecretKey(ws.url(BASE_URL + s"v1/projects/${projectsId}/keys/${keysId}:retrieveLegacySecretKey").addQueryStringParameters("key" -> key.toString))
				given Conversion[retrieveLegacySecretKey, Future[Schema.GoogleCloudRecaptchaenterpriseV1RetrieveLegacySecretKeyResponse]] = (fun: retrieveLegacySecretKey) => fun.apply()
			}
			class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGoogleCloudRecaptchaenterpriseV1Key(body: Schema.GoogleCloudRecaptchaenterpriseV1Key) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudRecaptchaenterpriseV1Key])
			}
			object create {
				def apply(projectsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/keys").addQueryStringParameters("parent" -> parent.toString))
			}
			class migrate(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGoogleCloudRecaptchaenterpriseV1MigrateKeyRequest(body: Schema.GoogleCloudRecaptchaenterpriseV1MigrateKeyRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudRecaptchaenterpriseV1Key])
			}
			object migrate {
				def apply(projectsId :PlayApi, keysId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): migrate = new migrate(ws.url(BASE_URL + s"v1/projects/${projectsId}/keys/${keysId}:migrate").addQueryStringParameters("name" -> name.toString))
			}
			class removeIpOverride(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGoogleCloudRecaptchaenterpriseV1RemoveIpOverrideRequest(body: Schema.GoogleCloudRecaptchaenterpriseV1RemoveIpOverrideRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudRecaptchaenterpriseV1RemoveIpOverrideResponse])
			}
			object removeIpOverride {
				def apply(projectsId :PlayApi, keysId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): removeIpOverride = new removeIpOverride(ws.url(BASE_URL + s"v1/projects/${projectsId}/keys/${keysId}:removeIpOverride").addQueryStringParameters("name" -> name.toString))
			}
			class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
				def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
			}
			object delete {
				def apply(projectsId :PlayApi, keysId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/keys/${keysId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
			}
			class getMetrics(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudRecaptchaenterpriseV1Metrics]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudRecaptchaenterpriseV1Metrics])
			}
			object getMetrics {
				def apply(projectsId :PlayApi, keysId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): getMetrics = new getMetrics(ws.url(BASE_URL + s"v1/projects/${projectsId}/keys/${keysId}/metrics").addQueryStringParameters("name" -> name.toString))
				given Conversion[getMetrics, Future[Schema.GoogleCloudRecaptchaenterpriseV1Metrics]] = (fun: getMetrics) => fun.apply()
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudRecaptchaenterpriseV1Key]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudRecaptchaenterpriseV1Key])
			}
			object get {
				def apply(projectsId :PlayApi, keysId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/keys/${keysId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.GoogleCloudRecaptchaenterpriseV1Key]] = (fun: get) => fun.apply()
			}
			class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				/** Optional. The mask to control which fields of the key get updated. If the mask is not present, all fields are updated.<br>Format: google-fieldmask */
				def withUpdateMask(updateMask: String) = new patch(req.addQueryStringParameters("updateMask" -> updateMask.toString))
				def withGoogleCloudRecaptchaenterpriseV1Key(body: Schema.GoogleCloudRecaptchaenterpriseV1Key) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudRecaptchaenterpriseV1Key])
			}
			object patch {
				def apply(projectsId :PlayApi, keysId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/keys/${keysId}").addQueryStringParameters("name" -> name.toString))
			}
			class addIpOverride(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGoogleCloudRecaptchaenterpriseV1AddIpOverrideRequest(body: Schema.GoogleCloudRecaptchaenterpriseV1AddIpOverrideRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudRecaptchaenterpriseV1AddIpOverrideResponse])
			}
			object addIpOverride {
				def apply(projectsId :PlayApi, keysId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): addIpOverride = new addIpOverride(ws.url(BASE_URL + s"v1/projects/${projectsId}/keys/${keysId}:addIpOverride").addQueryStringParameters("name" -> name.toString))
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudRecaptchaenterpriseV1ListKeysResponse]) {
				/** Optional. The maximum number of keys to return. Default is 10. Max limit is 1000.<br>Format: int32 */
				def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
				/** Optional. The next_page_token value returned from a previous. ListKeysRequest, if any. */
				def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudRecaptchaenterpriseV1ListKeysResponse])
			}
			object list {
				def apply(projectsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/keys").addQueryStringParameters("parent" -> parent.toString))
				given Conversion[list, Future[Schema.GoogleCloudRecaptchaenterpriseV1ListKeysResponse]] = (fun: list) => fun.apply()
			}
		}
	}
}
