package com.boresjo.play.api.google.recaptchaenterprise

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

	private val BASE_URL = "https://recaptchaenterprise.googleapis.com/"

	object projects {
		object relatedaccountgroups {
			/** List groups of related accounts. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudRecaptchaenterpriseV1ListRelatedAccountGroupsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Optional. A page token, received from a previous `ListRelatedAccountGroups` call. Provide this to retrieve the subsequent page. When paginating, all other parameters provided to `ListRelatedAccountGroups` must match the call that provided the page token. */
				def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
				/** Optional. The maximum number of groups to return. The service might return fewer than this value. If unspecified, at most 50 groups are returned. The maximum value is 1000; values above 1000 are coerced to 1000.<br>Format: int32 */
				def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudRecaptchaenterpriseV1ListRelatedAccountGroupsResponse])
			}
			object list {
				def apply(projectsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/relatedaccountgroups").addQueryStringParameters("parent" -> parent.toString))
				given Conversion[list, Future[Schema.GoogleCloudRecaptchaenterpriseV1ListRelatedAccountGroupsResponse]] = (fun: list) => fun.apply()
			}
			object memberships {
				/** Get memberships in a group of related accounts. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudRecaptchaenterpriseV1ListRelatedAccountGroupMembershipsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Optional. A page token, received from a previous `ListRelatedAccountGroupMemberships` call. When paginating, all other parameters provided to `ListRelatedAccountGroupMemberships` must match the call that provided the page token. */
					def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
					/** Optional. The maximum number of accounts to return. The service might return fewer than this value. If unspecified, at most 50 accounts are returned. The maximum value is 1000; values above 1000 are coerced to 1000.<br>Format: int32 */
					def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudRecaptchaenterpriseV1ListRelatedAccountGroupMembershipsResponse])
				}
				object list {
					def apply(projectsId :PlayApi, relatedaccountgroupsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/relatedaccountgroups/${relatedaccountgroupsId}/memberships").addQueryStringParameters("parent" -> parent.toString))
					given Conversion[list, Future[Schema.GoogleCloudRecaptchaenterpriseV1ListRelatedAccountGroupMembershipsResponse]] = (fun: list) => fun.apply()
				}
			}
		}
		object assessments {
			/** Creates an Assessment of the likelihood an event is legitimate. */
			class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withGoogleCloudRecaptchaenterpriseV1Assessment(body: Schema.GoogleCloudRecaptchaenterpriseV1Assessment) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudRecaptchaenterpriseV1Assessment])
			}
			object create {
				def apply(projectsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/assessments").addQueryStringParameters("parent" -> parent.toString))
			}
			/** Annotates a previously created Assessment to provide additional information on whether the event turned out to be authentic or fraudulent. */
			class annotate(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withGoogleCloudRecaptchaenterpriseV1AnnotateAssessmentRequest(body: Schema.GoogleCloudRecaptchaenterpriseV1AnnotateAssessmentRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudRecaptchaenterpriseV1AnnotateAssessmentResponse])
			}
			object annotate {
				def apply(projectsId :PlayApi, assessmentsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): annotate = new annotate(ws.url(BASE_URL + s"v1/projects/${projectsId}/assessments/${assessmentsId}:annotate").addQueryStringParameters("name" -> name.toString))
			}
		}
		object relatedaccountgroupmemberships {
			/** Search group memberships related to a given account. */
			class search(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withGoogleCloudRecaptchaenterpriseV1SearchRelatedAccountGroupMembershipsRequest(body: Schema.GoogleCloudRecaptchaenterpriseV1SearchRelatedAccountGroupMembershipsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudRecaptchaenterpriseV1SearchRelatedAccountGroupMembershipsResponse])
			}
			object search {
				def apply(projectsId :PlayApi, project: String)(using signer: RequestSigner, ec: ExecutionContext): search = new search(ws.url(BASE_URL + s"v1/projects/${projectsId}/relatedaccountgroupmemberships:search").addQueryStringParameters("project" -> project.toString))
			}
		}
		object firewallpolicies {
			/** Reorders all firewall policies. */
			class reorder(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withGoogleCloudRecaptchaenterpriseV1ReorderFirewallPoliciesRequest(body: Schema.GoogleCloudRecaptchaenterpriseV1ReorderFirewallPoliciesRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudRecaptchaenterpriseV1ReorderFirewallPoliciesResponse])
			}
			object reorder {
				def apply(projectsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): reorder = new reorder(ws.url(BASE_URL + s"v1/projects/${projectsId}/firewallpolicies:reorder").addQueryStringParameters("parent" -> parent.toString))
			}
			/** Creates a new FirewallPolicy, specifying conditions at which reCAPTCHA Enterprise actions can be executed. A project may have a maximum of 1000 policies. */
			class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withGoogleCloudRecaptchaenterpriseV1FirewallPolicy(body: Schema.GoogleCloudRecaptchaenterpriseV1FirewallPolicy) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudRecaptchaenterpriseV1FirewallPolicy])
			}
			object create {
				def apply(projectsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/firewallpolicies").addQueryStringParameters("parent" -> parent.toString))
			}
			/** Deletes the specified firewall policy. */
			class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
			}
			object delete {
				def apply(projectsId :PlayApi, firewallpoliciesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/firewallpolicies/${firewallpoliciesId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
			}
			/** Returns the specified firewall policy. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudRecaptchaenterpriseV1FirewallPolicy]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudRecaptchaenterpriseV1FirewallPolicy])
			}
			object get {
				def apply(projectsId :PlayApi, firewallpoliciesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/firewallpolicies/${firewallpoliciesId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.GoogleCloudRecaptchaenterpriseV1FirewallPolicy]] = (fun: get) => fun.apply()
			}
			/** Updates the specified firewall policy. */
			class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Optional. The mask to control which fields of the policy get updated. If the mask is not present, all fields are updated.<br>Format: google-fieldmask */
				def withUpdateMask(updateMask: String) = new patch(req.addQueryStringParameters("updateMask" -> updateMask.toString))
				/** Perform the request */
				def withGoogleCloudRecaptchaenterpriseV1FirewallPolicy(body: Schema.GoogleCloudRecaptchaenterpriseV1FirewallPolicy) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudRecaptchaenterpriseV1FirewallPolicy])
			}
			object patch {
				def apply(projectsId :PlayApi, firewallpoliciesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/firewallpolicies/${firewallpoliciesId}").addQueryStringParameters("name" -> name.toString))
			}
			/** Returns the list of all firewall policies that belong to a project. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudRecaptchaenterpriseV1ListFirewallPoliciesResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Optional. The next_page_token value returned from a previous. ListFirewallPoliciesRequest, if any. */
				def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
				/** Optional. The maximum number of policies to return. Default is 10. Max limit is 1000.<br>Format: int32 */
				def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudRecaptchaenterpriseV1ListFirewallPoliciesResponse])
			}
			object list {
				def apply(projectsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/firewallpolicies").addQueryStringParameters("parent" -> parent.toString))
				given Conversion[list, Future[Schema.GoogleCloudRecaptchaenterpriseV1ListFirewallPoliciesResponse]] = (fun: list) => fun.apply()
			}
		}
		object keys {
			/** Lists all IP overrides for a key. */
			class listIpOverrides(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudRecaptchaenterpriseV1ListIpOverridesResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Optional. The maximum number of overrides to return. Default is 10. Max limit is 100. If the number of overrides is less than the page_size, all overrides are returned. If the page size is more than 100, it is coerced to 100.<br>Format: int32 */
				def withPageSize(pageSize: Int) = new listIpOverrides(req.addQueryStringParameters("pageSize" -> pageSize.toString))
				/** Optional. The next_page_token value returned from a previous ListIpOverridesRequest, if any. */
				def withPageToken(pageToken: String) = new listIpOverrides(req.addQueryStringParameters("pageToken" -> pageToken.toString))
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudRecaptchaenterpriseV1ListIpOverridesResponse])
			}
			object listIpOverrides {
				def apply(projectsId :PlayApi, keysId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): listIpOverrides = new listIpOverrides(ws.url(BASE_URL + s"v1/projects/${projectsId}/keys/${keysId}:listIpOverrides").addQueryStringParameters("parent" -> parent.toString))
				given Conversion[listIpOverrides, Future[Schema.GoogleCloudRecaptchaenterpriseV1ListIpOverridesResponse]] = (fun: listIpOverrides) => fun.apply()
			}
			/** Returns the secret key related to the specified public key. You must use the legacy secret key only in a 3rd party integration with legacy reCAPTCHA. */
			class retrieveLegacySecretKey(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudRecaptchaenterpriseV1RetrieveLegacySecretKeyResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudRecaptchaenterpriseV1RetrieveLegacySecretKeyResponse])
			}
			object retrieveLegacySecretKey {
				def apply(projectsId :PlayApi, keysId :PlayApi, key: String)(using signer: RequestSigner, ec: ExecutionContext): retrieveLegacySecretKey = new retrieveLegacySecretKey(ws.url(BASE_URL + s"v1/projects/${projectsId}/keys/${keysId}:retrieveLegacySecretKey").addQueryStringParameters("key" -> key.toString))
				given Conversion[retrieveLegacySecretKey, Future[Schema.GoogleCloudRecaptchaenterpriseV1RetrieveLegacySecretKeyResponse]] = (fun: retrieveLegacySecretKey) => fun.apply()
			}
			/** Creates a new reCAPTCHA Enterprise key. */
			class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withGoogleCloudRecaptchaenterpriseV1Key(body: Schema.GoogleCloudRecaptchaenterpriseV1Key) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudRecaptchaenterpriseV1Key])
			}
			object create {
				def apply(projectsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/keys").addQueryStringParameters("parent" -> parent.toString))
			}
			/** Migrates an existing key from reCAPTCHA to reCAPTCHA Enterprise. Once a key is migrated, it can be used from either product. SiteVerify requests are billed as CreateAssessment calls. You must be authenticated as one of the current owners of the reCAPTCHA Key, and your user must have the reCAPTCHA Enterprise Admin IAM role in the destination project. */
			class migrate(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withGoogleCloudRecaptchaenterpriseV1MigrateKeyRequest(body: Schema.GoogleCloudRecaptchaenterpriseV1MigrateKeyRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudRecaptchaenterpriseV1Key])
			}
			object migrate {
				def apply(projectsId :PlayApi, keysId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): migrate = new migrate(ws.url(BASE_URL + s"v1/projects/${projectsId}/keys/${keysId}:migrate").addQueryStringParameters("name" -> name.toString))
			}
			/** Removes an IP override from a key. The following restrictions hold: &#42; If the IP isn't found in an existing IP override, a `NOT_FOUND` error is returned. &#42; If the IP is found in an existing IP override, but the override type does not match, a `NOT_FOUND` error is returned. */
			class removeIpOverride(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withGoogleCloudRecaptchaenterpriseV1RemoveIpOverrideRequest(body: Schema.GoogleCloudRecaptchaenterpriseV1RemoveIpOverrideRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudRecaptchaenterpriseV1RemoveIpOverrideResponse])
			}
			object removeIpOverride {
				def apply(projectsId :PlayApi, keysId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): removeIpOverride = new removeIpOverride(ws.url(BASE_URL + s"v1/projects/${projectsId}/keys/${keysId}:removeIpOverride").addQueryStringParameters("name" -> name.toString))
			}
			/** Deletes the specified key. */
			class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
			}
			object delete {
				def apply(projectsId :PlayApi, keysId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/keys/${keysId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
			}
			/** Get some aggregated metrics for a Key. This data can be used to build dashboards. */
			class getMetrics(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudRecaptchaenterpriseV1Metrics]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudRecaptchaenterpriseV1Metrics])
			}
			object getMetrics {
				def apply(projectsId :PlayApi, keysId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): getMetrics = new getMetrics(ws.url(BASE_URL + s"v1/projects/${projectsId}/keys/${keysId}/metrics").addQueryStringParameters("name" -> name.toString))
				given Conversion[getMetrics, Future[Schema.GoogleCloudRecaptchaenterpriseV1Metrics]] = (fun: getMetrics) => fun.apply()
			}
			/** Returns the specified key. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudRecaptchaenterpriseV1Key]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudRecaptchaenterpriseV1Key])
			}
			object get {
				def apply(projectsId :PlayApi, keysId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/keys/${keysId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.GoogleCloudRecaptchaenterpriseV1Key]] = (fun: get) => fun.apply()
			}
			/** Updates the specified key. */
			class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Optional. The mask to control which fields of the key get updated. If the mask is not present, all fields are updated.<br>Format: google-fieldmask */
				def withUpdateMask(updateMask: String) = new patch(req.addQueryStringParameters("updateMask" -> updateMask.toString))
				/** Perform the request */
				def withGoogleCloudRecaptchaenterpriseV1Key(body: Schema.GoogleCloudRecaptchaenterpriseV1Key) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudRecaptchaenterpriseV1Key])
			}
			object patch {
				def apply(projectsId :PlayApi, keysId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/keys/${keysId}").addQueryStringParameters("name" -> name.toString))
			}
			/** Adds an IP override to a key. The following restrictions hold: &#42; The maximum number of IP overrides per key is 100. &#42; For any conflict (such as IP already exists or IP part of an existing IP range), an error is returned. */
			class addIpOverride(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withGoogleCloudRecaptchaenterpriseV1AddIpOverrideRequest(body: Schema.GoogleCloudRecaptchaenterpriseV1AddIpOverrideRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudRecaptchaenterpriseV1AddIpOverrideResponse])
			}
			object addIpOverride {
				def apply(projectsId :PlayApi, keysId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): addIpOverride = new addIpOverride(ws.url(BASE_URL + s"v1/projects/${projectsId}/keys/${keysId}:addIpOverride").addQueryStringParameters("name" -> name.toString))
			}
			/** Returns the list of all keys that belong to a project. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudRecaptchaenterpriseV1ListKeysResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Optional. The maximum number of keys to return. Default is 10. Max limit is 1000.<br>Format: int32 */
				def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
				/** Optional. The next_page_token value returned from a previous. ListKeysRequest, if any. */
				def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudRecaptchaenterpriseV1ListKeysResponse])
			}
			object list {
				def apply(projectsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/keys").addQueryStringParameters("parent" -> parent.toString))
				given Conversion[list, Future[Schema.GoogleCloudRecaptchaenterpriseV1ListKeysResponse]] = (fun: list) => fun.apply()
			}
		}
	}
}
