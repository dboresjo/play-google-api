package com.boresjo.play.api.google.apigee

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

	private val BASE_URL = "https://apigee.googleapis.com/"

	object hybrid {
		object issuers {
			/** Lists hybrid services and its trusted issuers service account ids. This api is authenticated and unauthorized(allow all the users) and used by runtime authn-authz service to query control plane's issuer service account ids. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1ListHybridIssuersResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudApigeeV1ListHybridIssuersResponse])
			}
			object list {
				def apply(name: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/hybrid/issuers").addQueryStringParameters("name" -> name.toString))
				given Conversion[list, Future[Schema.GoogleCloudApigeeV1ListHybridIssuersResponse]] = (fun: list) => fun.apply()
			}
		}
	}
	object organizations {
		/** Sets the permissions required to allow the Synchronizer to download environment data from the control plane. You must call this API to enable proper functioning of hybrid. Pass the ETag when calling `setSyncAuthorization` to ensure that you are updating the correct version. To get an ETag, call [getSyncAuthorization](getSyncAuthorization). If you don't pass the ETag in the call to `setSyncAuthorization`, then the existing authorization is overwritten indiscriminately. For more information, see [Configure the Synchronizer](https://cloud.google.com/apigee/docs/hybrid/latest/synchronizer-access). &#42;&#42;Note&#42;&#42;: Available to Apigee hybrid only. */
		class setSyncAuthorization(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def withGoogleCloudApigeeV1SyncAuthorization(body: Schema.GoogleCloudApigeeV1SyncAuthorization) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudApigeeV1SyncAuthorization])
		}
		object setSyncAuthorization {
			def apply(organizationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): setSyncAuthorization = new setSyncAuthorization(ws.url(BASE_URL + s"v1/organizations/${organizationsId}:setSyncAuthorization").addQueryStringParameters("name" -> name.toString))
		}
		/** Get runtime config for an organization. */
		class getRuntimeConfig(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1RuntimeConfig]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudApigeeV1RuntimeConfig])
		}
		object getRuntimeConfig {
			def apply(organizationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): getRuntimeConfig = new getRuntimeConfig(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/runtimeConfig").addQueryStringParameters("name" -> name.toString))
			given Conversion[getRuntimeConfig, Future[Schema.GoogleCloudApigeeV1RuntimeConfig]] = (fun: getRuntimeConfig) => fun.apply()
		}
		/** Gets the deployed ingress configuration for an organization. */
		class getDeployedIngressConfig(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1IngressConfig]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudApigeeV1IngressConfig])
		}
		object getDeployedIngressConfig {
			def apply(organizationsId :PlayApi, view: String, name: String)(using signer: RequestSigner, ec: ExecutionContext): getDeployedIngressConfig = new getDeployedIngressConfig(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/deployedIngressConfig").addQueryStringParameters("view" -> view.toString, "name" -> name.toString))
			given Conversion[getDeployedIngressConfig, Future[Schema.GoogleCloudApigeeV1IngressConfig]] = (fun: getDeployedIngressConfig) => fun.apply()
		}
		/** Gets the project ID and region for an Apigee organization. */
		class getProjectMapping(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1OrganizationProjectMapping]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudApigeeV1OrganizationProjectMapping])
		}
		object getProjectMapping {
			def apply(organizationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): getProjectMapping = new getProjectMapping(ws.url(BASE_URL + s"v1/organizations/${organizationsId}:getProjectMapping").addQueryStringParameters("name" -> name.toString))
			given Conversion[getProjectMapping, Future[Schema.GoogleCloudApigeeV1OrganizationProjectMapping]] = (fun: getProjectMapping) => fun.apply()
		}
		/** Updates the permissions required to allow Apigee runtime-plane components access to the control plane. Currently, the permissions required are to: 1. Allow runtime components to publish analytics data to the control plane. &#42;&#42;Note&#42;&#42;: Available to Apigee hybrid only. */
		class updateControlPlaneAccess(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def withGoogleCloudApigeeV1ControlPlaneAccess(body: Schema.GoogleCloudApigeeV1ControlPlaneAccess) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleLongrunningOperation])
		}
		object updateControlPlaneAccess {
			def apply(organizationsId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): updateControlPlaneAccess = new updateControlPlaneAccess(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/controlPlaneAccess").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
		}
		/** UpdateSecuritySettings updates the current security settings for API Security. */
		class updateSecuritySettings(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Optional. The list of fields to update. Allowed fields are: - ml_retraining_feedback_enabled<br>Format: google-fieldmask */
			def withUpdateMask(updateMask: String) = new updateSecuritySettings(req.addQueryStringParameters("updateMask" -> updateMask.toString))
			/** Perform the request */
			def withGoogleCloudApigeeV1SecuritySettings(body: Schema.GoogleCloudApigeeV1SecuritySettings) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudApigeeV1SecuritySettings])
		}
		object updateSecuritySettings {
			def apply(organizationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): updateSecuritySettings = new updateSecuritySettings(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/securitySettings").addQueryStringParameters("name" -> name.toString))
		}
		/** GetSecuritySettings gets the security settings for API Security. */
		class getSecuritySettings(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1SecuritySettings]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudApigeeV1SecuritySettings])
		}
		object getSecuritySettings {
			def apply(organizationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): getSecuritySettings = new getSecuritySettings(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/securitySettings").addQueryStringParameters("name" -> name.toString))
			given Conversion[getSecuritySettings, Future[Schema.GoogleCloudApigeeV1SecuritySettings]] = (fun: getSecuritySettings) => fun.apply()
		}
		/** Creates an Apigee organization. See [Create an Apigee organization](https://cloud.google.com/apigee/docs/api-platform/get-started/create-org). */
		class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def withGoogleCloudApigeeV1Organization(body: Schema.GoogleCloudApigeeV1Organization) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
		}
		object create {
			def apply(parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/organizations").addQueryStringParameters("parent" -> parent.toString))
		}
		/** Lists the service accounts allowed to access Apigee control plane directly for limited functionality. &#42;&#42;Note&#42;&#42;: Available to Apigee hybrid only. */
		class getControlPlaneAccess(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1ControlPlaneAccess]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudApigeeV1ControlPlaneAccess])
		}
		object getControlPlaneAccess {
			def apply(organizationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): getControlPlaneAccess = new getControlPlaneAccess(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/controlPlaneAccess").addQueryStringParameters("name" -> name.toString))
			given Conversion[getControlPlaneAccess, Future[Schema.GoogleCloudApigeeV1ControlPlaneAccess]] = (fun: getControlPlaneAccess) => fun.apply()
		}
		/** Configures the add-ons for the Apigee organization. The existing add-on configuration will be fully replaced. */
		class setAddons(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def withGoogleCloudApigeeV1SetAddonsRequest(body: Schema.GoogleCloudApigeeV1SetAddonsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
		}
		object setAddons {
			def apply(organizationsId :PlayApi, org: String)(using signer: RequestSigner, ec: ExecutionContext): setAddons = new setAddons(ws.url(BASE_URL + s"v1/organizations/${organizationsId}:setAddons").addQueryStringParameters("org" -> org.toString))
		}
		/** Lists the service accounts with the permissions required to allow the Synchronizer to download environment data from the control plane. An ETag is returned in the response to `getSyncAuthorization`. Pass that ETag when calling [setSyncAuthorization](setSyncAuthorization) to ensure that you are updating the correct version. If you don't pass the ETag in the call to `setSyncAuthorization`, then the existing authorization is overwritten indiscriminately. For more information, see [Configure the Synchronizer](https://cloud.google.com/apigee/docs/hybrid/latest/synchronizer-access). &#42;&#42;Note&#42;&#42;: Available to Apigee hybrid only. */
		class getSyncAuthorization(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def withGoogleCloudApigeeV1GetSyncAuthorizationRequest(body: Schema.GoogleCloudApigeeV1GetSyncAuthorizationRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudApigeeV1SyncAuthorization])
		}
		object getSyncAuthorization {
			def apply(organizationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): getSyncAuthorization = new getSyncAuthorization(ws.url(BASE_URL + s"v1/organizations/${organizationsId}:getSyncAuthorization").addQueryStringParameters("name" -> name.toString))
		}
		/** Delete an Apigee organization. For organizations with BillingType EVALUATION, an immediate deletion is performed. For paid organizations (Subscription or Pay-as-you-go), a soft-deletion is performed. The organization can be restored within the soft-deletion period, which is specified using the `retention` field in the request or by filing a support ticket with Apigee. During the data retention period specified in the request, the Apigee organization cannot be recreated in the same Google Cloud project. &#42;&#42;IMPORTANT: The default data retention setting for this operation is 7 days. To permanently delete the organization in 24 hours, set the retention parameter to `MINIMUM`.&#42;&#42; */
		class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningOperation]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Optional. This setting is applicable only for organizations that are soft-deleted (i.e., BillingType is not EVALUATION). It controls how long Organization data will be retained after the initial delete operation completes. During this period, the Organization may be restored to its last known state. After this period, the Organization will no longer be able to be restored. &#42;&#42;Note: During the data retention period specified using this field, the Apigee organization cannot be recreated in the same Google Cloud project.&#42;&#42;<br>Possible values:<br>DELETION_RETENTION_UNSPECIFIED: Default data retention setting of seven days will be applied.<br>MINIMUM: Organization data will be retained for the minimum period of 24 hours. */
			def withRetention(retention: String) = new delete(req.addQueryStringParameters("retention" -> retention.toString))
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleLongrunningOperation])
		}
		object delete {
			def apply(organizationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/organizations/${organizationsId}").addQueryStringParameters("name" -> name.toString))
			given Conversion[delete, Future[Schema.GoogleLongrunningOperation]] = (fun: delete) => fun.apply()
		}
		/** Gets the profile for an Apigee organization. See [Understanding organizations](https://cloud.google.com/apigee/docs/api-platform/fundamentals/organization-structure). */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1Organization]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudApigeeV1Organization])
		}
		object get {
			def apply(organizationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/organizations/${organizationsId}").addQueryStringParameters("name" -> name.toString))
			given Conversion[get, Future[Schema.GoogleCloudApigeeV1Organization]] = (fun: get) => fun.apply()
		}
		/** Updates the properties for an Apigee organization. No other fields in the organization profile will be updated. */
		class update(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def withGoogleCloudApigeeV1Organization(body: Schema.GoogleCloudApigeeV1Organization) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.GoogleCloudApigeeV1Organization])
		}
		object update {
			def apply(organizationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"v1/organizations/${organizationsId}").addQueryStringParameters("name" -> name.toString))
		}
		/** Lists the Apigee organizations and associated Google Cloud projects that you have permission to access. See [Understanding organizations](https://cloud.google.com/apigee/docs/api-platform/fundamentals/organization-structure). */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1ListOrganizationsResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudApigeeV1ListOrganizationsResponse])
		}
		object list {
			def apply(parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/organizations").addQueryStringParameters("parent" -> parent.toString))
			given Conversion[list, Future[Schema.GoogleCloudApigeeV1ListOrganizationsResponse]] = (fun: list) => fun.apply()
		}
		object securityProfiles {
			/** CreateSecurityProfile create a new custom security profile. */
			class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withGoogleCloudApigeeV1SecurityProfile(body: Schema.GoogleCloudApigeeV1SecurityProfile) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudApigeeV1SecurityProfile])
			}
			object create {
				def apply(organizationsId :PlayApi, securityProfileId: String, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/securityProfiles").addQueryStringParameters("securityProfileId" -> securityProfileId.toString, "parent" -> parent.toString))
			}
			/** DeleteSecurityProfile delete a profile with all its revisions. */
			class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
			}
			object delete {
				def apply(organizationsId :PlayApi, securityProfilesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/securityProfiles/${securityProfilesId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
			}
			/** UpdateSecurityProfile update the metadata of security profile. */
			class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withGoogleCloudApigeeV1SecurityProfile(body: Schema.GoogleCloudApigeeV1SecurityProfile) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudApigeeV1SecurityProfile])
			}
			object patch {
				def apply(organizationsId :PlayApi, securityProfilesId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/securityProfiles/${securityProfilesId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
			}
			/** ListSecurityProfiles lists all the security profiles associated with the org including attached and unattached profiles. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1ListSecurityProfilesResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudApigeeV1ListSecurityProfilesResponse])
			}
			object list {
				def apply(organizationsId :PlayApi, pageSize: Int, parent: String, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/securityProfiles").addQueryStringParameters("pageSize" -> pageSize.toString, "parent" -> parent.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.GoogleCloudApigeeV1ListSecurityProfilesResponse]] = (fun: list) => fun.apply()
			}
			/** GetSecurityProfile gets the specified security profile. Returns NOT_FOUND if security profile is not present for the specified organization. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1SecurityProfile]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Required. Security profile in the following format: `organizations/{org}/securityProfiles/{profile}'. Profile may optionally contain revision ID. If revision ID is not provided, the response will contain latest revision by default. Example: organizations/testOrg/securityProfiles/testProfile@5 */
				def withName(name: String) = new get(req.addQueryStringParameters("name" -> name.toString))
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudApigeeV1SecurityProfile])
			}
			object get {
				def apply(organizationsId :PlayApi, securityProfilesId :PlayApi)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/securityProfiles/${securityProfilesId}").addQueryStringParameters())
				given Conversion[get, Future[Schema.GoogleCloudApigeeV1SecurityProfile]] = (fun: get) => fun.apply()
			}
			/** ListSecurityProfileRevisions lists all the revisions of the security profile. */
			class listRevisions(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1ListSecurityProfileRevisionsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudApigeeV1ListSecurityProfileRevisionsResponse])
			}
			object listRevisions {
				def apply(organizationsId :PlayApi, securityProfilesId :PlayApi, pageToken: String, name: String, pageSize: Int)(using signer: RequestSigner, ec: ExecutionContext): listRevisions = new listRevisions(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/securityProfiles/${securityProfilesId}:listRevisions").addQueryStringParameters("pageToken" -> pageToken.toString, "name" -> name.toString, "pageSize" -> pageSize.toString))
				given Conversion[listRevisions, Future[Schema.GoogleCloudApigeeV1ListSecurityProfileRevisionsResponse]] = (fun: listRevisions) => fun.apply()
			}
			object environments {
				/** ComputeEnvironmentScores calculates scores for requested time range for the specified security profile and environment. */
				class computeEnvironmentScores(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGoogleCloudApigeeV1ComputeEnvironmentScoresRequest(body: Schema.GoogleCloudApigeeV1ComputeEnvironmentScoresRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudApigeeV1ComputeEnvironmentScoresResponse])
				}
				object computeEnvironmentScores {
					def apply(organizationsId :PlayApi, securityProfilesId :PlayApi, environmentsId :PlayApi, profileEnvironment: String)(using signer: RequestSigner, ec: ExecutionContext): computeEnvironmentScores = new computeEnvironmentScores(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/securityProfiles/${securityProfilesId}/environments/${environmentsId}:computeEnvironmentScores").addQueryStringParameters("profileEnvironment" -> profileEnvironment.toString))
				}
				/** CreateSecurityProfileEnvironmentAssociation creates profile environment association i.e. attaches environment to security profile. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGoogleCloudApigeeV1SecurityProfileEnvironmentAssociation(body: Schema.GoogleCloudApigeeV1SecurityProfileEnvironmentAssociation) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudApigeeV1SecurityProfileEnvironmentAssociation])
				}
				object create {
					def apply(organizationsId :PlayApi, securityProfilesId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/securityProfiles/${securityProfilesId}/environments").addQueryStringParameters("parent" -> parent.toString))
				}
				/** DeleteSecurityProfileEnvironmentAssociation removes profile environment association i.e. detaches environment from security profile. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
				}
				object delete {
					def apply(organizationsId :PlayApi, securityProfilesId :PlayApi, environmentsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/securityProfiles/${securityProfilesId}/environments/${environmentsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
				}
			}
		}
		object apis {
			/** Creates an API proxy. The API proxy created will not be accessible at runtime until it is deployed to an environment. Create a new API proxy by setting the `name` query parameter to the name of the API proxy. Import an API proxy configuration bundle stored in zip format on your local machine to your organization by doing the following: &#42; Set the `name` query parameter to the name of the API proxy. &#42; Set the `action` query parameter to `import`. &#42; Set the `Content-Type` header to `multipart/form-data`. &#42; Pass as a file the name of API proxy configuration bundle stored in zip format on your local machine using the `file` form field. &#42;&#42;Note&#42;&#42;: To validate the API proxy configuration bundle only without importing it, set the `action` query parameter to `validate`. When importing an API proxy configuration bundle, if the API proxy does not exist, it will be created. If the API proxy exists, then a new revision is created. Invalid API proxy configurations are rejected, and a list of validation errors is returned to the client. */
			class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withGoogleApiHttpBody(body: Schema.GoogleApiHttpBody) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudApigeeV1ApiProxyRevision])
			}
			object create {
				def apply(organizationsId :PlayApi, validate: Boolean, name: String, parent: String, action: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/apis").addQueryStringParameters("validate" -> validate.toString, "name" -> name.toString, "parent" -> parent.toString, "action" -> action.toString))
			}
			/** Deletes an API proxy and all associated endpoints, policies, resources, and revisions. The API proxy must be undeployed before you can delete it. */
			class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1ApiProxy]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleCloudApigeeV1ApiProxy])
			}
			object delete {
				def apply(organizationsId :PlayApi, apisId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/apis/${apisId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.GoogleCloudApigeeV1ApiProxy]] = (fun: delete) => fun.apply()
			}
			/** Gets an API proxy including a list of existing revisions. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1ApiProxy]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudApigeeV1ApiProxy])
			}
			object get {
				def apply(organizationsId :PlayApi, apisId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/apis/${apisId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.GoogleCloudApigeeV1ApiProxy]] = (fun: get) => fun.apply()
			}
			/** Updates an existing API proxy. */
			class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withGoogleCloudApigeeV1ApiProxy(body: Schema.GoogleCloudApigeeV1ApiProxy) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudApigeeV1ApiProxy])
			}
			object patch {
				def apply(organizationsId :PlayApi, apisId :PlayApi, updateMask: String, name: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/apis/${apisId}").addQueryStringParameters("updateMask" -> updateMask.toString, "name" -> name.toString))
			}
			/** Lists the names of all API proxies in an organization. The names returned correspond to the names defined in the configuration files for each API proxy. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1ListApiProxiesResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudApigeeV1ListApiProxiesResponse])
			}
			object list {
				def apply(organizationsId :PlayApi, includeRevisions: Boolean, parent: String, includeMetaData: Boolean)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/apis").addQueryStringParameters("includeRevisions" -> includeRevisions.toString, "parent" -> parent.toString, "includeMetaData" -> includeMetaData.toString))
				given Conversion[list, Future[Schema.GoogleCloudApigeeV1ListApiProxiesResponse]] = (fun: list) => fun.apply()
			}
			object keyvaluemaps {
				/** Deletes a key value map from an API proxy. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1KeyValueMap]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleCloudApigeeV1KeyValueMap])
				}
				object delete {
					def apply(organizationsId :PlayApi, apisId :PlayApi, keyvaluemapsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/apis/${apisId}/keyvaluemaps/${keyvaluemapsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.GoogleCloudApigeeV1KeyValueMap]] = (fun: delete) => fun.apply()
				}
				/** Creates a key value map in an API proxy. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGoogleCloudApigeeV1KeyValueMap(body: Schema.GoogleCloudApigeeV1KeyValueMap) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudApigeeV1KeyValueMap])
				}
				object create {
					def apply(organizationsId :PlayApi, apisId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/apis/${apisId}/keyvaluemaps").addQueryStringParameters("parent" -> parent.toString))
				}
				object entries {
					/** Creates key value entries in a key value map scoped to an organization, environment, or API proxy. &#42;&#42;Note&#42;&#42;: Supported for Apigee hybrid 1.8.x and higher. */
					class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withGoogleCloudApigeeV1KeyValueEntry(body: Schema.GoogleCloudApigeeV1KeyValueEntry) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudApigeeV1KeyValueEntry])
					}
					object create {
						def apply(organizationsId :PlayApi, apisId :PlayApi, keyvaluemapsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/apis/${apisId}/keyvaluemaps/${keyvaluemapsId}/entries").addQueryStringParameters("parent" -> parent.toString))
					}
					/** Deletes a key value entry from a key value map scoped to an organization, environment, or API proxy. &#42;&#42;Notes:&#42;&#42; &#42; After you delete the key value entry, the policy consuming the entry will continue to function with its cached values for a few minutes. This is expected behavior. &#42; Supported for Apigee hybrid 1.8.x and higher. */
					class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1KeyValueEntry]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleCloudApigeeV1KeyValueEntry])
					}
					object delete {
						def apply(organizationsId :PlayApi, apisId :PlayApi, keyvaluemapsId :PlayApi, entriesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/apis/${apisId}/keyvaluemaps/${keyvaluemapsId}/entries/${entriesId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[delete, Future[Schema.GoogleCloudApigeeV1KeyValueEntry]] = (fun: delete) => fun.apply()
					}
					/** Get the key value entry value for a key value map scoped to an organization, environment, or API proxy. &#42;&#42;Note&#42;&#42;: Supported for Apigee hybrid 1.8.x and higher. */
					class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1KeyValueEntry]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudApigeeV1KeyValueEntry])
					}
					object get {
						def apply(organizationsId :PlayApi, apisId :PlayApi, keyvaluemapsId :PlayApi, entriesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/apis/${apisId}/keyvaluemaps/${keyvaluemapsId}/entries/${entriesId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.GoogleCloudApigeeV1KeyValueEntry]] = (fun: get) => fun.apply()
					}
					/** Update key value entry scoped to an organization, environment, or API proxy for an existing key. */
					class update(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withGoogleCloudApigeeV1KeyValueEntry(body: Schema.GoogleCloudApigeeV1KeyValueEntry) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.GoogleCloudApigeeV1KeyValueEntry])
					}
					object update {
						def apply(organizationsId :PlayApi, apisId :PlayApi, keyvaluemapsId :PlayApi, entriesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/apis/${apisId}/keyvaluemaps/${keyvaluemapsId}/entries/${entriesId}").addQueryStringParameters("name" -> name.toString))
					}
					/** Lists key value entries for key values maps scoped to an organization, environment, or API proxy. &#42;&#42;Note&#42;&#42;: Supported for Apigee hybrid 1.8.x and higher. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1ListKeyValueEntriesResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Optional. Maximum number of key value entries to return. If unspecified, at most 100 entries will be returned.<br>Format: int32 */
						def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
						/** Optional. Page token. If provides, must be a valid key value entry returned from a previous call that can be used to retrieve the next page. */
						def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudApigeeV1ListKeyValueEntriesResponse])
					}
					object list {
						def apply(organizationsId :PlayApi, apisId :PlayApi, keyvaluemapsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/apis/${apisId}/keyvaluemaps/${keyvaluemapsId}/entries").addQueryStringParameters("parent" -> parent.toString))
						given Conversion[list, Future[Schema.GoogleCloudApigeeV1ListKeyValueEntriesResponse]] = (fun: list) => fun.apply()
					}
				}
			}
			object deployments {
				/** Lists all deployments of an API proxy. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1ListDeploymentsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudApigeeV1ListDeploymentsResponse])
				}
				object list {
					def apply(organizationsId :PlayApi, apisId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/apis/${apisId}/deployments").addQueryStringParameters("parent" -> parent.toString))
					given Conversion[list, Future[Schema.GoogleCloudApigeeV1ListDeploymentsResponse]] = (fun: list) => fun.apply()
				}
			}
			object revisions {
				/** Deletes an API proxy revision and all policies, resources, endpoints, and revisions associated with it. The API proxy revision must be undeployed before you can delete it. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1ApiProxyRevision]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleCloudApigeeV1ApiProxyRevision])
				}
				object delete {
					def apply(organizationsId :PlayApi, apisId :PlayApi, revisionsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/apis/${apisId}/revisions/${revisionsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.GoogleCloudApigeeV1ApiProxyRevision]] = (fun: delete) => fun.apply()
				}
				/** Gets an API proxy revision. To download the API proxy configuration bundle for the specified revision as a zip file, set the `format` query parameter to `bundle`. If you are using curl, specify `-o filename.zip` to save the output to a file; otherwise, it displays to `stdout`. Then, develop the API proxy configuration locally and upload the updated API proxy configuration revision, as described in [updateApiProxyRevision](updateApiProxyRevision). */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleApiHttpBody]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleApiHttpBody])
				}
				object get {
					def apply(organizationsId :PlayApi, apisId :PlayApi, revisionsId :PlayApi, name: String, format: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/apis/${apisId}/revisions/${revisionsId}").addQueryStringParameters("name" -> name.toString, "format" -> format.toString))
					given Conversion[get, Future[Schema.GoogleApiHttpBody]] = (fun: get) => fun.apply()
				}
				/** Updates an existing API proxy revision by uploading the API proxy configuration bundle as a zip file from your local machine. You can update only API proxy revisions that have never been deployed. After deployment, an API proxy revision becomes immutable, even if it is undeployed. Set the `Content-Type` header to either `multipart/form-data` or `application/octet-stream`. */
				class updateApiProxyRevision(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGoogleApiHttpBody(body: Schema.GoogleApiHttpBody) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudApigeeV1ApiProxyRevision])
				}
				object updateApiProxyRevision {
					def apply(organizationsId :PlayApi, apisId :PlayApi, revisionsId :PlayApi, validate: Boolean, name: String)(using signer: RequestSigner, ec: ExecutionContext): updateApiProxyRevision = new updateApiProxyRevision(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/apis/${apisId}/revisions/${revisionsId}").addQueryStringParameters("validate" -> validate.toString, "name" -> name.toString))
				}
				object deployments {
					/** Lists all deployments of an API proxy revision. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1ListDeploymentsResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudApigeeV1ListDeploymentsResponse])
					}
					object list {
						def apply(organizationsId :PlayApi, apisId :PlayApi, revisionsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/apis/${apisId}/revisions/${revisionsId}/deployments").addQueryStringParameters("parent" -> parent.toString))
						given Conversion[list, Future[Schema.GoogleCloudApigeeV1ListDeploymentsResponse]] = (fun: list) => fun.apply()
					}
				}
			}
		}
		object endpointAttachments {
			/** Creates an endpoint attachment. &#42;&#42;Note:&#42;&#42; Not supported for Apigee hybrid. */
			class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withGoogleCloudApigeeV1EndpointAttachment(body: Schema.GoogleCloudApigeeV1EndpointAttachment) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
			}
			object create {
				def apply(organizationsId :PlayApi, parent: String, endpointAttachmentId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/endpointAttachments").addQueryStringParameters("parent" -> parent.toString, "endpointAttachmentId" -> endpointAttachmentId.toString))
			}
			/** Gets the endpoint attachment. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1EndpointAttachment]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudApigeeV1EndpointAttachment])
			}
			object get {
				def apply(organizationsId :PlayApi, endpointAttachmentsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/endpointAttachments/${endpointAttachmentsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.GoogleCloudApigeeV1EndpointAttachment]] = (fun: get) => fun.apply()
			}
			/** Lists the endpoint attachments in an organization. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1ListEndpointAttachmentsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Optional. Maximum number of endpoint attachments to return. If unspecified, at most 25 attachments will be returned.<br>Format: int32 */
				def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
				/** Optional. Page token, returned from a previous `ListEndpointAttachments` call, that you can use to retrieve the next page. */
				def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudApigeeV1ListEndpointAttachmentsResponse])
			}
			object list {
				def apply(organizationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/endpointAttachments").addQueryStringParameters("parent" -> parent.toString))
				given Conversion[list, Future[Schema.GoogleCloudApigeeV1ListEndpointAttachmentsResponse]] = (fun: list) => fun.apply()
			}
			/** Deletes an endpoint attachment. */
			class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningOperation]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleLongrunningOperation])
			}
			object delete {
				def apply(organizationsId :PlayApi, endpointAttachmentsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/endpointAttachments/${endpointAttachmentsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.GoogleLongrunningOperation]] = (fun: delete) => fun.apply()
			}
		}
		object developers {
			/** Sets the status of a developer. A developer is `active` by default. If you set a developer's status to `inactive`, the API keys assigned to the developer apps are no longer valid even though the API keys are set to `approved`. Inactive developers can still sign in to the developer portal and create apps; however, any new API keys generated during app creation won't work. To set the status of a developer, set the `action` query parameter to `active` or `inactive`, and the `Content-Type` header to `application/octet-stream`. If successful, the API call returns the following HTTP status code: `204 No Content` */
			class setDeveloperStatus(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("POST")).map(_.json.as[Schema.GoogleProtobufEmpty])
			}
			object setDeveloperStatus {
				def apply(organizationsId :PlayApi, developersId :PlayApi, name: String, action: String)(using signer: RequestSigner, ec: ExecutionContext): setDeveloperStatus = new setDeveloperStatus(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/developers/${developersId}").addQueryStringParameters("name" -> name.toString, "action" -> action.toString))
				given Conversion[setDeveloperStatus, Future[Schema.GoogleProtobufEmpty]] = (fun: setDeveloperStatus) => fun.apply()
			}
			/** Updates the monetization configuration for the developer. */
			class updateMonetizationConfig(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withGoogleCloudApigeeV1DeveloperMonetizationConfig(body: Schema.GoogleCloudApigeeV1DeveloperMonetizationConfig) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.GoogleCloudApigeeV1DeveloperMonetizationConfig])
			}
			object updateMonetizationConfig {
				def apply(organizationsId :PlayApi, developersId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): updateMonetizationConfig = new updateMonetizationConfig(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/developers/${developersId}/monetizationConfig").addQueryStringParameters("name" -> name.toString))
			}
			/** Creates a developer. Once created, the developer can register an app and obtain an API key. At creation time, a developer is set as `active`. To change the developer status, use the SetDeveloperStatus API. */
			class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withGoogleCloudApigeeV1Developer(body: Schema.GoogleCloudApigeeV1Developer) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudApigeeV1Developer])
			}
			object create {
				def apply(organizationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/developers").addQueryStringParameters("parent" -> parent.toString))
			}
			/** Updates developer attributes. This API replaces the existing attributes with those specified in the request. Add new attributes, and include or exclude any existing attributes that you want to retain or remove, respectively. The custom attribute limit is 18. &#42;&#42;Note&#42;&#42;: OAuth access tokens and Key Management Service (KMS) entities (apps, developers, and API products) are cached for 180 seconds (default). Any custom attributes associated with these entities are cached for at least 180 seconds after the entity is accessed at runtime. Therefore, an `ExpiresIn` element on the OAuthV2 policy won't be able to expire an access token in less than 180 seconds. */
			class attributes(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withGoogleCloudApigeeV1Attributes(body: Schema.GoogleCloudApigeeV1Attributes) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudApigeeV1Attributes])
			}
			object attributes {
				def apply(organizationsId :PlayApi, developersId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): attributes = new attributes(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/developers/${developersId}/attributes").addQueryStringParameters("parent" -> parent.toString))
				/** Deletes a developer attribute. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1Attribute]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleCloudApigeeV1Attribute])
				}
				object delete {
					def apply(organizationsId :PlayApi, developersId :PlayApi, attributesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/developers/${developersId}/attributes/${attributesId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.GoogleCloudApigeeV1Attribute]] = (fun: delete) => fun.apply()
				}
				/** Updates a developer attribute. &#42;&#42;Note&#42;&#42;: OAuth access tokens and Key Management Service (KMS) entities (apps, developers, and API products) are cached for 180 seconds (default). Any custom attributes associated with these entities are cached for at least 180 seconds after the entity is accessed at runtime. Therefore, an `ExpiresIn` element on the OAuthV2 policy won't be able to expire an access token in less than 180 seconds. */
				class updateDeveloperAttribute(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGoogleCloudApigeeV1Attribute(body: Schema.GoogleCloudApigeeV1Attribute) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudApigeeV1Attribute])
				}
				object updateDeveloperAttribute {
					def apply(organizationsId :PlayApi, developersId :PlayApi, attributesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): updateDeveloperAttribute = new updateDeveloperAttribute(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/developers/${developersId}/attributes/${attributesId}").addQueryStringParameters("name" -> name.toString))
				}
				/** Returns a list of all developer attributes. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1Attributes]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudApigeeV1Attributes])
				}
				object list {
					def apply(organizationsId :PlayApi, developersId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/developers/${developersId}/attributes").addQueryStringParameters("parent" -> parent.toString))
					given Conversion[list, Future[Schema.GoogleCloudApigeeV1Attributes]] = (fun: list) => fun.apply()
				}
				/** Returns the value of the specified developer attribute. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1Attribute]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudApigeeV1Attribute])
				}
				object get {
					def apply(organizationsId :PlayApi, developersId :PlayApi, attributesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/developers/${developersId}/attributes/${attributesId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleCloudApigeeV1Attribute]] = (fun: get) => fun.apply()
				}
			}
			/** Deletes a developer. All apps and API keys associated with the developer are also removed. &#42;&#42;Warning&#42;&#42;: This API will permanently delete the developer and related artifacts. To avoid permanently deleting developers and their artifacts, set the developer status to `inactive` using the SetDeveloperStatus API. &#42;&#42;Note&#42;&#42;: The delete operation is asynchronous. The developer app is deleted immediately, but its associated resources, such as apps and API keys, may take anywhere from a few seconds to a few minutes to be deleted. */
			class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1Developer]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleCloudApigeeV1Developer])
			}
			object delete {
				def apply(organizationsId :PlayApi, developersId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/developers/${developersId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.GoogleCloudApigeeV1Developer]] = (fun: delete) => fun.apply()
			}
			/** Gets the monetization configuration for the developer. */
			class getMonetizationConfig(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1DeveloperMonetizationConfig]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudApigeeV1DeveloperMonetizationConfig])
			}
			object getMonetizationConfig {
				def apply(organizationsId :PlayApi, developersId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): getMonetizationConfig = new getMonetizationConfig(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/developers/${developersId}/monetizationConfig").addQueryStringParameters("name" -> name.toString))
				given Conversion[getMonetizationConfig, Future[Schema.GoogleCloudApigeeV1DeveloperMonetizationConfig]] = (fun: getMonetizationConfig) => fun.apply()
			}
			/** Returns the developer details, including the developer's name, email address, apps, and other information. &#42;&#42;Note&#42;&#42;: The response includes only the first 100 developer apps. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1Developer]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudApigeeV1Developer])
			}
			object get {
				def apply(organizationsId :PlayApi, developersId :PlayApi, name: String, action: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/developers/${developersId}").addQueryStringParameters("name" -> name.toString, "action" -> action.toString))
				given Conversion[get, Future[Schema.GoogleCloudApigeeV1Developer]] = (fun: get) => fun.apply()
			}
			/** Updates a developer. This API replaces the existing developer details with those specified in the request. Include or exclude any existing details that you want to retain or delete, respectively. The custom attribute limit is 18. &#42;&#42;Note&#42;&#42;: OAuth access tokens and Key Management Service (KMS) entities (apps, developers, and API products) are cached for 180 seconds (current default). Any custom attributes associated with these entities are cached for at least 180 seconds after the entity is accessed at runtime. Therefore, an `ExpiresIn` element on the OAuthV2 policy won't be able to expire an access token in less than 180 seconds. */
			class update(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withGoogleCloudApigeeV1Developer(body: Schema.GoogleCloudApigeeV1Developer) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.GoogleCloudApigeeV1Developer])
			}
			object update {
				def apply(organizationsId :PlayApi, developersId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/developers/${developersId}").addQueryStringParameters("name" -> name.toString))
			}
			/** Gets the account balance for the developer. */
			class getBalance(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1DeveloperBalance]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudApigeeV1DeveloperBalance])
			}
			object getBalance {
				def apply(organizationsId :PlayApi, developersId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): getBalance = new getBalance(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/developers/${developersId}/balance").addQueryStringParameters("name" -> name.toString))
				given Conversion[getBalance, Future[Schema.GoogleCloudApigeeV1DeveloperBalance]] = (fun: getBalance) => fun.apply()
			}
			/** Lists all developers in an organization by email address. By default, the response does not include company developers. Set the `includeCompany` query parameter to `true` to include company developers. &#42;&#42;Note&#42;&#42;: A maximum of 1000 developers are returned in the response. You paginate the list of developers returned using the `startKey` and `count` query parameters. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1ListOfDevelopersResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Optional. List of IDs to include, separated by commas. */
				def withIds(ids: String) = new list(req.addQueryStringParameters("ids" -> ids.toString))
				/** Optional. Number of developers to return in the API call. Use with the `startKey` parameter to provide more targeted filtering. The limit is 1000.<br>Format: int64 */
				def withCount(count: String) = new list(req.addQueryStringParameters("count" -> count.toString))
				/** Optional. List only Developers that are associated with the app. Note that start_key, count are not applicable for this filter criteria. */
				def withApp(app: String) = new list(req.addQueryStringParameters("app" -> app.toString))
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudApigeeV1ListOfDevelopersResponse])
			}
			object list {
				def apply(organizationsId :PlayApi, includeCompany: Boolean, expand: Boolean, parent: String, startKey: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/developers").addQueryStringParameters("includeCompany" -> includeCompany.toString, "expand" -> expand.toString, "parent" -> parent.toString, "startKey" -> startKey.toString))
				given Conversion[list, Future[Schema.GoogleCloudApigeeV1ListOfDevelopersResponse]] = (fun: list) => fun.apply()
			}
			object balance {
				/** Credits the account balance for the developer. */
				class credit(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGoogleCloudApigeeV1CreditDeveloperBalanceRequest(body: Schema.GoogleCloudApigeeV1CreditDeveloperBalanceRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudApigeeV1DeveloperBalance])
				}
				object credit {
					def apply(organizationsId :PlayApi, developersId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): credit = new credit(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/developers/${developersId}/balance:credit").addQueryStringParameters("name" -> name.toString))
				}
				/** Adjust the prepaid balance for the developer. This API will be used in scenarios where the developer has been under-charged or over-charged. */
				class adjust(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGoogleCloudApigeeV1AdjustDeveloperBalanceRequest(body: Schema.GoogleCloudApigeeV1AdjustDeveloperBalanceRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudApigeeV1DeveloperBalance])
				}
				object adjust {
					def apply(organizationsId :PlayApi, developersId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): adjust = new adjust(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/developers/${developersId}/balance:adjust").addQueryStringParameters("name" -> name.toString))
				}
			}
			object subscriptions {
				/** Creates a subscription to an API product.  */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGoogleCloudApigeeV1DeveloperSubscription(body: Schema.GoogleCloudApigeeV1DeveloperSubscription) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudApigeeV1DeveloperSubscription])
				}
				object create {
					def apply(organizationsId :PlayApi, developersId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/developers/${developersId}/subscriptions").addQueryStringParameters("parent" -> parent.toString))
				}
				/** Expires an API product subscription immediately. */
				class expire(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGoogleCloudApigeeV1ExpireDeveloperSubscriptionRequest(body: Schema.GoogleCloudApigeeV1ExpireDeveloperSubscriptionRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudApigeeV1DeveloperSubscription])
				}
				object expire {
					def apply(organizationsId :PlayApi, developersId :PlayApi, subscriptionsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): expire = new expire(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/developers/${developersId}/subscriptions/${subscriptionsId}:expire").addQueryStringParameters("name" -> name.toString))
				}
				/** Gets details for an API product subscription. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1DeveloperSubscription]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudApigeeV1DeveloperSubscription])
				}
				object get {
					def apply(organizationsId :PlayApi, developersId :PlayApi, subscriptionsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/developers/${developersId}/subscriptions/${subscriptionsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleCloudApigeeV1DeveloperSubscription]] = (fun: get) => fun.apply()
				}
				/** Lists all API product subscriptions for a developer. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1ListDeveloperSubscriptionsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudApigeeV1ListDeveloperSubscriptionsResponse])
				}
				object list {
					def apply(organizationsId :PlayApi, developersId :PlayApi, count: Int, startKey: String, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/developers/${developersId}/subscriptions").addQueryStringParameters("count" -> count.toString, "startKey" -> startKey.toString, "parent" -> parent.toString))
					given Conversion[list, Future[Schema.GoogleCloudApigeeV1ListDeveloperSubscriptionsResponse]] = (fun: list) => fun.apply()
				}
			}
			object apps {
				/** Creates an app associated with a developer. This API associates the developer app with the specified API product and auto-generates an API key for the app to use in calls to API proxies inside that API product. The `name` is the unique ID of the app that you can use in API calls. The `DisplayName` (set as an attribute) appears in the UI. If you don't set the `DisplayName` attribute, the `name` appears in the UI. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGoogleCloudApigeeV1DeveloperApp(body: Schema.GoogleCloudApigeeV1DeveloperApp) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudApigeeV1DeveloperApp])
				}
				object create {
					def apply(organizationsId :PlayApi, developersId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/developers/${developersId}/apps").addQueryStringParameters("parent" -> parent.toString))
				}
				/** Updates attributes for a developer app. This API replaces the current attributes with those specified in the request. */
				class attributes(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGoogleCloudApigeeV1Attributes(body: Schema.GoogleCloudApigeeV1Attributes) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudApigeeV1Attributes])
				}
				object attributes {
					def apply(organizationsId :PlayApi, developersId :PlayApi, appsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): attributes = new attributes(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/developers/${developersId}/apps/${appsId}/attributes").addQueryStringParameters("name" -> name.toString))
					/** Returns a list of all developer app attributes. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1Attributes]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudApigeeV1Attributes])
					}
					object list {
						def apply(organizationsId :PlayApi, developersId :PlayApi, appsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/developers/${developersId}/apps/${appsId}/attributes").addQueryStringParameters("parent" -> parent.toString))
						given Conversion[list, Future[Schema.GoogleCloudApigeeV1Attributes]] = (fun: list) => fun.apply()
					}
					/** Returns a developer app attribute. */
					class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1Attribute]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudApigeeV1Attribute])
					}
					object get {
						def apply(organizationsId :PlayApi, developersId :PlayApi, appsId :PlayApi, attributesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/developers/${developersId}/apps/${appsId}/attributes/${attributesId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.GoogleCloudApigeeV1Attribute]] = (fun: get) => fun.apply()
					}
					/** Deletes a developer app attribute. */
					class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1Attribute]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleCloudApigeeV1Attribute])
					}
					object delete {
						def apply(organizationsId :PlayApi, developersId :PlayApi, appsId :PlayApi, attributesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/developers/${developersId}/apps/${appsId}/attributes/${attributesId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[delete, Future[Schema.GoogleCloudApigeeV1Attribute]] = (fun: delete) => fun.apply()
					}
					/** Updates a developer app attribute. &#42;&#42;Note&#42;&#42;: OAuth access tokens and Key Management Service (KMS) entities (apps, developers, and API products) are cached for 180 seconds (current default). Any custom attributes associated with these entities are cached for at least 180 seconds after the entity is accessed at runtime. Therefore, an `ExpiresIn` element on the OAuthV2 policy won't be able to expire an access token in less than 180 seconds. */
					class updateDeveloperAppAttribute(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withGoogleCloudApigeeV1Attribute(body: Schema.GoogleCloudApigeeV1Attribute) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudApigeeV1Attribute])
					}
					object updateDeveloperAppAttribute {
						def apply(organizationsId :PlayApi, developersId :PlayApi, appsId :PlayApi, attributesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): updateDeveloperAppAttribute = new updateDeveloperAppAttribute(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/developers/${developersId}/apps/${appsId}/attributes/${attributesId}").addQueryStringParameters("name" -> name.toString))
					}
				}
				/** Returns the details for a developer app. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1DeveloperApp]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudApigeeV1DeveloperApp])
				}
				object get {
					def apply(organizationsId :PlayApi, developersId :PlayApi, appsId :PlayApi, entity: String, query: String, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/developers/${developersId}/apps/${appsId}").addQueryStringParameters("entity" -> entity.toString, "query" -> query.toString, "name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleCloudApigeeV1DeveloperApp]] = (fun: get) => fun.apply()
				}
				/** Updates the details for a developer app. In addition, you can add an API product to a developer app and automatically generate an API key for the app to use when calling APIs in the API product. If you want to use an existing API key for the API product, add the API product to the API key using the UpdateDeveloperAppKey API. Using this API, you cannot update the following: &#42; App name as it is the primary key used to identify the app and cannot be changed. &#42; Scopes associated with the app. Instead, use the ReplaceDeveloperAppKey API. This API replaces the existing attributes with those specified in the request. Include or exclude any existing attributes that you want to retain or delete, respectively. */
				class update(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGoogleCloudApigeeV1DeveloperApp(body: Schema.GoogleCloudApigeeV1DeveloperApp) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.GoogleCloudApigeeV1DeveloperApp])
				}
				object update {
					def apply(organizationsId :PlayApi, developersId :PlayApi, appsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/developers/${developersId}/apps/${appsId}").addQueryStringParameters("name" -> name.toString))
				}
				/** Manages access to a developer app by enabling you to: &#42; Approve or revoke a developer app &#42; Generate a new consumer key and secret for a developer app To approve or revoke a developer app, set the `action` query parameter to `approve` or `revoke`, respectively, and the `Content-Type` header to `application/octet-stream`. If a developer app is revoked, none of its API keys are valid for API calls even though the keys are still approved. If successful, the API call returns the following HTTP status code: `204 No Content` To generate a new consumer key and secret for a developer app, pass the new key/secret details. Rather than replace an existing key, this API generates a new key. In this case, multiple key pairs may be associated with a single developer app. Each key pair has an independent status (`approve` or `revoke`) and expiration time. Any approved, non-expired key can be used in an API call. For example, if you're using API key rotation, you can generate new keys with expiration times that overlap keys that are going to expire. You might also generate a new consumer key/secret if the security of the original key/secret is compromised. The `keyExpiresIn` property defines the expiration time for the API key in milliseconds. If you don't set this property or set it to `-1`, the API key never expires. &#42;&#42;Notes&#42;&#42;: &#42; When generating a new key/secret, this API replaces the existing attributes, notes, and callback URLs with those specified in the request. Include or exclude any existing information that you want to retain or delete, respectively. &#42; To migrate existing consumer keys and secrets to hybrid from another system, see the CreateDeveloperAppKey API. */
				class generateKeyPairOrUpdateDeveloperAppStatus(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGoogleCloudApigeeV1DeveloperApp(body: Schema.GoogleCloudApigeeV1DeveloperApp) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudApigeeV1DeveloperApp])
				}
				object generateKeyPairOrUpdateDeveloperAppStatus {
					def apply(organizationsId :PlayApi, developersId :PlayApi, appsId :PlayApi, action: String, name: String)(using signer: RequestSigner, ec: ExecutionContext): generateKeyPairOrUpdateDeveloperAppStatus = new generateKeyPairOrUpdateDeveloperAppStatus(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/developers/${developersId}/apps/${appsId}").addQueryStringParameters("action" -> action.toString, "name" -> name.toString))
				}
				/** Deletes a developer app. &#42;&#42;Note&#42;&#42;: The delete operation is asynchronous. The developer app is deleted immediately, but its associated resources, such as app keys or access tokens, may take anywhere from a few seconds to a few minutes to be deleted. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1DeveloperApp]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleCloudApigeeV1DeveloperApp])
				}
				object delete {
					def apply(organizationsId :PlayApi, developersId :PlayApi, appsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/developers/${developersId}/apps/${appsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.GoogleCloudApigeeV1DeveloperApp]] = (fun: delete) => fun.apply()
				}
				/** Lists all apps created by a developer in an Apigee organization. Optionally, you can request an expanded view of the developer apps. A maximum of 100 developer apps are returned per API call. You can paginate the list of deveoper apps returned using the `startKey` and `count` query parameters. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1ListDeveloperAppsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Optional. Specifies whether to expand the results in shallow mode. Set to `true` to expand the results in shallow mode. */
					def withShallowExpand(shallowExpand: Boolean) = new list(req.addQueryStringParameters("shallowExpand" -> shallowExpand.toString))
					/** Optional. Specifies whether to expand the results. Set to `true` to expand the results. This query parameter is not valid if you use the `count` or `startKey` query parameters. */
					def withExpand(expand: Boolean) = new list(req.addQueryStringParameters("expand" -> expand.toString))
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudApigeeV1ListDeveloperAppsResponse])
				}
				object list {
					def apply(organizationsId :PlayApi, developersId :PlayApi, count: String, startKey: String, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/developers/${developersId}/apps").addQueryStringParameters("count" -> count.toString, "startKey" -> startKey.toString, "parent" -> parent.toString))
					given Conversion[list, Future[Schema.GoogleCloudApigeeV1ListDeveloperAppsResponse]] = (fun: list) => fun.apply()
				}
				object keys {
					/** Creates a custom consumer key and secret for a developer app. This is particularly useful if you want to migrate existing consumer keys and secrets to Apigee from another system. Consumer keys and secrets can contain letters, numbers, underscores, and hyphens. No other special characters are allowed. To avoid service disruptions, a consumer key and secret should not exceed 2 KBs each. &#42;&#42;Note&#42;&#42;: When creating the consumer key and secret, an association to API products will not be made. Therefore, you should not specify the associated API products in your request. Instead, use the UpdateDeveloperAppKey API to make the association after the consumer key and secret are created. If a consumer key and secret already exist, you can keep them or delete them using the DeleteDeveloperAppKey API. &#42;&#42;Note&#42;&#42;: All keys start out with status=approved, even if status=revoked is passed when the key is created. To revoke a key, use the UpdateDeveloperAppKey API. */
					class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withGoogleCloudApigeeV1DeveloperAppKey(body: Schema.GoogleCloudApigeeV1DeveloperAppKey) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudApigeeV1DeveloperAppKey])
					}
					object create {
						def apply(organizationsId :PlayApi, developersId :PlayApi, appsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/developers/${developersId}/apps/${appsId}/keys").addQueryStringParameters("parent" -> parent.toString))
						/** Creates a custom consumer key and secret for a developer app. This is particularly useful if you want to migrate existing consumer keys and secrets to Apigee from another system. Consumer keys and secrets can contain letters, numbers, underscores, and hyphens. No other special characters are allowed. To avoid service disruptions, a consumer key and secret should not exceed 2 KBs each. &#42;&#42;Note&#42;&#42;: When creating the consumer key and secret, an association to API products will not be made. Therefore, you should not specify the associated API products in your request. Instead, use the UpdateDeveloperAppKey API to make the association after the consumer key and secret are created. If a consumer key and secret already exist, you can keep them or delete them using the DeleteDeveloperAppKey API. &#42;&#42;Note&#42;&#42;: All keys start out with status=approved, even if status=revoked is passed when the key is created. To revoke a key, use the UpdateDeveloperAppKey API. */
						class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def withGoogleCloudApigeeV1DeveloperAppKey(body: Schema.GoogleCloudApigeeV1DeveloperAppKey) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudApigeeV1DeveloperAppKey])
						}
						object create {
							def apply(organizationsId :PlayApi, developersId :PlayApi, appsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/developers/${developersId}/apps/${appsId}/keys/create").addQueryStringParameters("parent" -> parent.toString))
						}
					}
					/** Updates the scope of an app. This API replaces the existing scopes with those specified in the request. Include or exclude any existing scopes that you want to retain or delete, respectively. The specified scopes must already be defined for the API products associated with the app. This API sets the `scopes` element under the `apiProducts` element in the attributes of the app. */
					class replaceDeveloperAppKey(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withGoogleCloudApigeeV1DeveloperAppKey(body: Schema.GoogleCloudApigeeV1DeveloperAppKey) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.GoogleCloudApigeeV1DeveloperAppKey])
					}
					object replaceDeveloperAppKey {
						def apply(organizationsId :PlayApi, developersId :PlayApi, appsId :PlayApi, keysId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): replaceDeveloperAppKey = new replaceDeveloperAppKey(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/developers/${developersId}/apps/${appsId}/keys/${keysId}").addQueryStringParameters("name" -> name.toString))
					}
					/** Gets details for a consumer key for a developer app, including the key and secret value, associated API products, and other information. */
					class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1DeveloperAppKey]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudApigeeV1DeveloperAppKey])
					}
					object get {
						def apply(organizationsId :PlayApi, developersId :PlayApi, appsId :PlayApi, keysId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/developers/${developersId}/apps/${appsId}/keys/${keysId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.GoogleCloudApigeeV1DeveloperAppKey]] = (fun: get) => fun.apply()
					}
					/** Adds an API product to a developer app key, enabling the app that holds the key to access the API resources bundled in the API product. In addition, you can add attributes to a developer app key. This API replaces the existing attributes with those specified in the request. Include or exclude any existing attributes that you want to retain or delete, respectively. You can use the same key to access all API products associated with the app. */
					class updateDeveloperAppKey(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withGoogleCloudApigeeV1DeveloperAppKey(body: Schema.GoogleCloudApigeeV1DeveloperAppKey) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudApigeeV1DeveloperAppKey])
					}
					object updateDeveloperAppKey {
						def apply(organizationsId :PlayApi, developersId :PlayApi, appsId :PlayApi, keysId :PlayApi, name: String, action: String)(using signer: RequestSigner, ec: ExecutionContext): updateDeveloperAppKey = new updateDeveloperAppKey(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/developers/${developersId}/apps/${appsId}/keys/${keysId}").addQueryStringParameters("name" -> name.toString, "action" -> action.toString))
					}
					/** Deletes an app's consumer key and removes all API products associated with the app. After the consumer key is deleted, it cannot be used to access any APIs. &#42;&#42;Note&#42;&#42;: After you delete a consumer key, you may want to: 1. Create a new consumer key and secret for the developer app using the CreateDeveloperAppKey API, and subsequently add an API product to the key using the UpdateDeveloperAppKey API. 2. Delete the developer app, if it is no longer required. */
					class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1DeveloperAppKey]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleCloudApigeeV1DeveloperAppKey])
					}
					object delete {
						def apply(organizationsId :PlayApi, developersId :PlayApi, appsId :PlayApi, keysId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/developers/${developersId}/apps/${appsId}/keys/${keysId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[delete, Future[Schema.GoogleCloudApigeeV1DeveloperAppKey]] = (fun: delete) => fun.apply()
					}
					object apiproducts {
						/** Approves or revokes the consumer key for an API product. After a consumer key is approved, the app can use it to access APIs. A consumer key that is revoked or pending cannot be used to access an API. Any access tokens associated with a revoked consumer key will remain active. However, Apigee checks the status of the consumer key and if set to `revoked` will not allow access to the API. */
						class updateDeveloperAppKeyApiProduct(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("POST")).map(_.json.as[Schema.GoogleProtobufEmpty])
						}
						object updateDeveloperAppKeyApiProduct {
							def apply(organizationsId :PlayApi, developersId :PlayApi, appsId :PlayApi, keysId :PlayApi, apiproductsId :PlayApi, name: String, action: String)(using signer: RequestSigner, ec: ExecutionContext): updateDeveloperAppKeyApiProduct = new updateDeveloperAppKeyApiProduct(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/developers/${developersId}/apps/${appsId}/keys/${keysId}/apiproducts/${apiproductsId}").addQueryStringParameters("name" -> name.toString, "action" -> action.toString))
							given Conversion[updateDeveloperAppKeyApiProduct, Future[Schema.GoogleProtobufEmpty]] = (fun: updateDeveloperAppKeyApiProduct) => fun.apply()
						}
						/** Removes an API product from an app's consumer key. After the API product is removed, the app cannot access the API resources defined in that API product. &#42;&#42;Note&#42;&#42;: The consumer key is not removed, only its association with the API product. */
						class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1DeveloperAppKey]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleCloudApigeeV1DeveloperAppKey])
						}
						object delete {
							def apply(organizationsId :PlayApi, developersId :PlayApi, appsId :PlayApi, keysId :PlayApi, apiproductsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/developers/${developersId}/apps/${appsId}/keys/${keysId}/apiproducts/${apiproductsId}").addQueryStringParameters("name" -> name.toString))
							given Conversion[delete, Future[Schema.GoogleCloudApigeeV1DeveloperAppKey]] = (fun: delete) => fun.apply()
						}
					}
				}
			}
		}
		object environments {
			/** Tests the permissions of a user on an environment, and returns a subset of permissions that the user has on the environment. If the environment does not exist, an empty permission set is returned (a NOT_FOUND error is not returned). */
			class testIamPermissions(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withGoogleIamV1TestIamPermissionsRequest(body: Schema.GoogleIamV1TestIamPermissionsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleIamV1TestIamPermissionsResponse])
			}
			object testIamPermissions {
				def apply(organizationsId :PlayApi, environmentsId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): testIamPermissions = new testIamPermissions(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}:testIamPermissions").addQueryStringParameters("resource" -> resource.toString))
			}
			/** Gets the deployed configuration for an environment. */
			class getDeployedConfig(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1EnvironmentConfig]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudApigeeV1EnvironmentConfig])
			}
			object getDeployedConfig {
				def apply(organizationsId :PlayApi, environmentsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): getDeployedConfig = new getDeployedConfig(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/deployedConfig").addQueryStringParameters("name" -> name.toString))
				given Conversion[getDeployedConfig, Future[Schema.GoogleCloudApigeeV1EnvironmentConfig]] = (fun: getDeployedConfig) => fun.apply()
			}
			/** Gets the add-ons config of an environment. */
			class getAddonsConfig(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1AddonsConfig]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudApigeeV1AddonsConfig])
			}
			object getAddonsConfig {
				def apply(organizationsId :PlayApi, environmentsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): getAddonsConfig = new getAddonsConfig(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/addonsConfig").addQueryStringParameters("name" -> name.toString))
				given Conversion[getAddonsConfig, Future[Schema.GoogleCloudApigeeV1AddonsConfig]] = (fun: getAddonsConfig) => fun.apply()
			}
			/** Deletes a subscription for the environment's Pub/Sub topic. */
			class unsubscribe(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withGoogleCloudApigeeV1Subscription(body: Schema.GoogleCloudApigeeV1Subscription) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleProtobufEmpty])
			}
			object unsubscribe {
				def apply(organizationsId :PlayApi, environmentsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): unsubscribe = new unsubscribe(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}:unsubscribe").addQueryStringParameters("parent" -> parent.toString))
			}
			/** Updates the debug mask singleton resource for an environment. */
			class updateDebugmask(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withGoogleCloudApigeeV1DebugMask(body: Schema.GoogleCloudApigeeV1DebugMask) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudApigeeV1DebugMask])
			}
			object updateDebugmask {
				def apply(organizationsId :PlayApi, environmentsId :PlayApi, name: String, replaceRepeatedFields: Boolean, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): updateDebugmask = new updateDebugmask(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/debugmask").addQueryStringParameters("name" -> name.toString, "replaceRepeatedFields" -> replaceRepeatedFields.toString, "updateMask" -> updateMask.toString))
			}
			/** Updates properties for an Apigee environment with patch semantics using a field mask. &#42;&#42;Note:&#42;&#42; Not supported for Apigee hybrid. */
			class modifyEnvironment(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withGoogleCloudApigeeV1Environment(body: Schema.GoogleCloudApigeeV1Environment) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleLongrunningOperation])
			}
			object modifyEnvironment {
				def apply(organizationsId :PlayApi, environmentsId :PlayApi, updateMask: String, name: String)(using signer: RequestSigner, ec: ExecutionContext): modifyEnvironment = new modifyEnvironment(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}").addQueryStringParameters("updateMask" -> updateMask.toString, "name" -> name.toString))
			}
			/** Updates an existing environment. When updating properties, you must pass all existing properties to the API, even if they are not being changed. If you omit properties from the payload, the properties are removed. To get the current list of properties for the environment, use the [Get Environment API](get). &#42;&#42;Note&#42;&#42;: Both `PUT` and `POST` methods are supported for updating an existing environment. */
			class updateEnvironment(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withGoogleCloudApigeeV1Environment(body: Schema.GoogleCloudApigeeV1Environment) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudApigeeV1Environment])
			}
			object updateEnvironment {
				def apply(organizationsId :PlayApi, environmentsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): updateEnvironment = new updateEnvironment(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}").addQueryStringParameters("name" -> name.toString))
			}
			/** Creates an environment in an organization. */
			class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Optional. Name of the environment. */
				def withName(name: String) = new create(req.addQueryStringParameters("name" -> name.toString))
				/** Perform the request */
				def withGoogleCloudApigeeV1Environment(body: Schema.GoogleCloudApigeeV1Environment) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
			}
			object create {
				def apply(organizationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments").addQueryStringParameters("parent" -> parent.toString))
			}
			/** Sets the IAM policy on an environment, if the policy already exists it will be replaced. For more information, see [Manage users, roles, and permissions using the API](https://cloud.google.com/apigee/docs/api-platform/system-administration/manage-users-roles). You must have the `apigee.environments.setIamPolicy` permission to call this API. */
			class setIamPolicy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withGoogleIamV1SetIamPolicyRequest(body: Schema.GoogleIamV1SetIamPolicyRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleIamV1Policy])
			}
			object setIamPolicy {
				def apply(organizationsId :PlayApi, environmentsId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): setIamPolicy = new setIamPolicy(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}:setIamPolicy").addQueryStringParameters("resource" -> resource.toString))
			}
			/** Creates a subscription for the environment's Pub/Sub topic. The server will assign a random name for this subscription. The "name" and "push_config" must &#42;not&#42; be specified. */
			class subscribe(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1Subscription]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("POST")).map(_.json.as[Schema.GoogleCloudApigeeV1Subscription])
			}
			object subscribe {
				def apply(organizationsId :PlayApi, environmentsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): subscribe = new subscribe(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}:subscribe").addQueryStringParameters("parent" -> parent.toString))
				given Conversion[subscribe, Future[Schema.GoogleCloudApigeeV1Subscription]] = (fun: subscribe) => fun.apply()
			}
			/** Get distributed trace configuration in an environment. */
			class getTraceConfig(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1TraceConfig]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudApigeeV1TraceConfig])
			}
			object getTraceConfig {
				def apply(organizationsId :PlayApi, environmentsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): getTraceConfig = new getTraceConfig(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/traceConfig").addQueryStringParameters("name" -> name.toString))
				given Conversion[getTraceConfig, Future[Schema.GoogleCloudApigeeV1TraceConfig]] = (fun: getTraceConfig) => fun.apply()
			}
			/** Updates the trace configurations in an environment. Note that the repeated fields have replace semantics when included in the field mask and that they will be overwritten by the value of the fields in the request body. */
			class updateTraceConfig(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withGoogleCloudApigeeV1TraceConfig(body: Schema.GoogleCloudApigeeV1TraceConfig) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudApigeeV1TraceConfig])
			}
			object updateTraceConfig {
				def apply(organizationsId :PlayApi, environmentsId :PlayApi, updateMask: String, name: String)(using signer: RequestSigner, ec: ExecutionContext): updateTraceConfig = new updateTraceConfig(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/traceConfig").addQueryStringParameters("updateMask" -> updateMask.toString, "name" -> name.toString))
			}
			/** Gets the IAM policy on an environment. For more information, see [Manage users, roles, and permissions using the API](https://cloud.google.com/apigee/docs/api-platform/system-administration/manage-users-roles). You must have the `apigee.environments.getIamPolicy` permission to call this API. */
			class getIamPolicy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleIamV1Policy]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Optional. The maximum policy version that will be used to format the policy. Valid values are 0, 1, and 3. Requests specifying an invalid value will be rejected. Requests for policies with any conditional role bindings must specify version 3. Policies with no conditional role bindings may specify any valid value or leave the field unset. The policy in the response might use the policy version that you specified, or it might use a lower policy version. For example, if you specify version 3, but the policy has no conditional role bindings, the response uses version 1. To learn which resources support conditions in their IAM policies, see the [IAM documentation](https://cloud.google.com/iam/help/conditions/resource-policies).<br>Format: int32 */
				def withOptionsRequestedPolicyVersion(optionsRequestedPolicyVersion: Int) = new getIamPolicy(req.addQueryStringParameters("options.requestedPolicyVersion" -> optionsRequestedPolicyVersion.toString))
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleIamV1Policy])
			}
			object getIamPolicy {
				def apply(organizationsId :PlayApi, environmentsId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): getIamPolicy = new getIamPolicy(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}:getIamPolicy").addQueryStringParameters("resource" -> resource.toString))
				given Conversion[getIamPolicy, Future[Schema.GoogleIamV1Policy]] = (fun: getIamPolicy) => fun.apply()
			}
			/** Gets the debug mask singleton resource for an environment. */
			class getDebugmask(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1DebugMask]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudApigeeV1DebugMask])
			}
			object getDebugmask {
				def apply(organizationsId :PlayApi, environmentsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): getDebugmask = new getDebugmask(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/debugmask").addQueryStringParameters("name" -> name.toString))
				given Conversion[getDebugmask, Future[Schema.GoogleCloudApigeeV1DebugMask]] = (fun: getDebugmask) => fun.apply()
			}
			/** Deletes an environment from an organization. &#42;&#42;Warning: You must delete all key value maps and key value entries before you delete an environment.&#42;&#42; Otherwise, if you re-create the environment the key value map entry operations will encounter encryption/decryption discrepancies. */
			class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningOperation]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleLongrunningOperation])
			}
			object delete {
				def apply(organizationsId :PlayApi, environmentsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.GoogleLongrunningOperation]] = (fun: delete) => fun.apply()
			}
			/** Gets the API Security runtime configuration for an environment. This named ApiSecurityRuntimeConfig to prevent conflicts with ApiSecurityConfig from addon config. */
			class getApiSecurityRuntimeConfig(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1ApiSecurityRuntimeConfig]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudApigeeV1ApiSecurityRuntimeConfig])
			}
			object getApiSecurityRuntimeConfig {
				def apply(organizationsId :PlayApi, environmentsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): getApiSecurityRuntimeConfig = new getApiSecurityRuntimeConfig(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/apiSecurityRuntimeConfig").addQueryStringParameters("name" -> name.toString))
				given Conversion[getApiSecurityRuntimeConfig, Future[Schema.GoogleCloudApigeeV1ApiSecurityRuntimeConfig]] = (fun: getApiSecurityRuntimeConfig) => fun.apply()
			}
			/** GetSecurityActionConfig returns the current SecurityActions configuration. */
			class getSecurityActionsConfig(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1SecurityActionsConfig]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudApigeeV1SecurityActionsConfig])
			}
			object getSecurityActionsConfig {
				def apply(organizationsId :PlayApi, environmentsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): getSecurityActionsConfig = new getSecurityActionsConfig(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/securityActionsConfig").addQueryStringParameters("name" -> name.toString))
				given Conversion[getSecurityActionsConfig, Future[Schema.GoogleCloudApigeeV1SecurityActionsConfig]] = (fun: getSecurityActionsConfig) => fun.apply()
			}
			/** Gets environment details. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1Environment]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudApigeeV1Environment])
			}
			object get {
				def apply(organizationsId :PlayApi, environmentsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.GoogleCloudApigeeV1Environment]] = (fun: get) => fun.apply()
			}
			/** Updates an existing environment. When updating properties, you must pass all existing properties to the API, even if they are not being changed. If you omit properties from the payload, the properties are removed. To get the current list of properties for the environment, use the [Get Environment API](get). &#42;&#42;Note&#42;&#42;: Both `PUT` and `POST` methods are supported for updating an existing environment. */
			class update(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withGoogleCloudApigeeV1Environment(body: Schema.GoogleCloudApigeeV1Environment) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.GoogleCloudApigeeV1Environment])
			}
			object update {
				def apply(organizationsId :PlayApi, environmentsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}").addQueryStringParameters("name" -> name.toString))
			}
			/** UpdateSecurityActionConfig updates the current SecurityActions configuration. This method is used to enable/disable the feature at the environment level. */
			class updateSecurityActionsConfig(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withGoogleCloudApigeeV1SecurityActionsConfig(body: Schema.GoogleCloudApigeeV1SecurityActionsConfig) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudApigeeV1SecurityActionsConfig])
			}
			object updateSecurityActionsConfig {
				def apply(organizationsId :PlayApi, environmentsId :PlayApi, updateMask: String, name: String)(using signer: RequestSigner, ec: ExecutionContext): updateSecurityActionsConfig = new updateSecurityActionsConfig(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/securityActionsConfig").addQueryStringParameters("updateMask" -> updateMask.toString, "name" -> name.toString))
			}
			object stats {
				/** Retrieve metrics grouped by dimensions. The types of metrics you can retrieve include traffic, message counts, API call latency, response size, and cache hits and counts. Dimensions let you view metrics in meaningful groups. You can optionally pass dimensions as path parameters to the `stats` API. If dimensions are not specified, the metrics are computed on the entire set of data for the given time range. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1Stats]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudApigeeV1Stats])
				}
				object get {
					def apply(organizationsId :PlayApi, environmentsId :PlayApi, statsId :PlayApi, sortby: String, filter: String, topk: String, aggTable: String, sonar: Boolean, tzo: String, limit: String, name: String, sort: String, timeUnit: String, offset: String, select: String, realtime: Boolean, tsAscending: Boolean, timeRange: String, accuracy: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/stats/${statsId}").addQueryStringParameters("sortby" -> sortby.toString, "filter" -> filter.toString, "topk" -> topk.toString, "aggTable" -> aggTable.toString, "sonar" -> sonar.toString, "tzo" -> tzo.toString, "limit" -> limit.toString, "name" -> name.toString, "sort" -> sort.toString, "timeUnit" -> timeUnit.toString, "offset" -> offset.toString, "select" -> select.toString, "realtime" -> realtime.toString, "tsAscending" -> tsAscending.toString, "timeRange" -> timeRange.toString, "accuracy" -> accuracy.toString))
					given Conversion[get, Future[Schema.GoogleCloudApigeeV1Stats]] = (fun: get) => fun.apply()
				}
			}
			object queries {
				/** After the query is completed, use this API to retrieve the results. If the request succeeds, and there is a non-zero result set, the result is downloaded to the client as a zipped JSON file. The name of the downloaded file will be: OfflineQueryResult-.zip Example: `OfflineQueryResult-9cfc0d85-0f30-46d6-ae6f-318d0cb961bd.zip` */
				class getResult(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleApiHttpBody]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleApiHttpBody])
				}
				object getResult {
					def apply(organizationsId :PlayApi, environmentsId :PlayApi, queriesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): getResult = new getResult(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/queries/${queriesId}/result").addQueryStringParameters("name" -> name.toString))
					given Conversion[getResult, Future[Schema.GoogleApiHttpBody]] = (fun: getResult) => fun.apply()
				}
				/** Submit a query to be processed in the background. If the submission of the query succeeds, the API returns a 201 status and an ID that refer to the query. In addition to the HTTP status 201, the `state` of "enqueued" means that the request succeeded. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGoogleCloudApigeeV1Query(body: Schema.GoogleCloudApigeeV1Query) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudApigeeV1AsyncQuery])
				}
				object create {
					def apply(organizationsId :PlayApi, environmentsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/queries").addQueryStringParameters("parent" -> parent.toString))
				}
				/** After the query is completed, use this API to retrieve the results. If the request succeeds, and there is a non-zero result set, the result is sent to the client as a list of urls to JSON files. */
				class getResulturl(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1GetAsyncQueryResultUrlResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudApigeeV1GetAsyncQueryResultUrlResponse])
				}
				object getResulturl {
					def apply(organizationsId :PlayApi, environmentsId :PlayApi, queriesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): getResulturl = new getResulturl(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/queries/${queriesId}/resulturl").addQueryStringParameters("name" -> name.toString))
					given Conversion[getResulturl, Future[Schema.GoogleCloudApigeeV1GetAsyncQueryResultUrlResponse]] = (fun: getResulturl) => fun.apply()
				}
				/** Get query status If the query is still in progress, the `state` is set to "running" After the query has completed successfully, `state` is set to "completed" */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1AsyncQuery]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudApigeeV1AsyncQuery])
				}
				object get {
					def apply(organizationsId :PlayApi, environmentsId :PlayApi, queriesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/queries/${queriesId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleCloudApigeeV1AsyncQuery]] = (fun: get) => fun.apply()
				}
				/** Return a list of Asynchronous Queries */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1ListAsyncQueriesResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudApigeeV1ListAsyncQueriesResponse])
				}
				object list {
					def apply(organizationsId :PlayApi, environmentsId :PlayApi, status: String, submittedBy: String, to: String, from: String, inclQueriesWithoutReport: String, dataset: String, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/queries").addQueryStringParameters("status" -> status.toString, "submittedBy" -> submittedBy.toString, "to" -> to.toString, "from" -> from.toString, "inclQueriesWithoutReport" -> inclQueriesWithoutReport.toString, "dataset" -> dataset.toString, "parent" -> parent.toString))
					given Conversion[list, Future[Schema.GoogleCloudApigeeV1ListAsyncQueriesResponse]] = (fun: list) => fun.apply()
				}
			}
			object securityStats {
				/** Retrieve security statistics as a collection of time series. */
				class queryTimeSeriesStats(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGoogleCloudApigeeV1QueryTimeSeriesStatsRequest(body: Schema.GoogleCloudApigeeV1QueryTimeSeriesStatsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudApigeeV1QueryTimeSeriesStatsResponse])
				}
				object queryTimeSeriesStats {
					def apply(organizationsId :PlayApi, environmentsId :PlayApi, orgenv: String)(using signer: RequestSigner, ec: ExecutionContext): queryTimeSeriesStats = new queryTimeSeriesStats(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/securityStats:queryTimeSeriesStats").addQueryStringParameters("orgenv" -> orgenv.toString))
				}
				/** Retrieve security statistics as tabular rows. */
				class queryTabularStats(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGoogleCloudApigeeV1QueryTabularStatsRequest(body: Schema.GoogleCloudApigeeV1QueryTabularStatsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudApigeeV1QueryTabularStatsResponse])
				}
				object queryTabularStats {
					def apply(organizationsId :PlayApi, environmentsId :PlayApi, orgenv: String)(using signer: RequestSigner, ec: ExecutionContext): queryTabularStats = new queryTabularStats(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/securityStats:queryTabularStats").addQueryStringParameters("orgenv" -> orgenv.toString))
				}
			}
			object securityIncidents {
				/** GetSecurityIncident gets the specified security incident. Returns NOT_FOUND if security incident is not present for the specified organization and environment. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1SecurityIncident]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudApigeeV1SecurityIncident])
				}
				object get {
					def apply(organizationsId :PlayApi, environmentsId :PlayApi, securityIncidentsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/securityIncidents/${securityIncidentsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleCloudApigeeV1SecurityIncident]] = (fun: get) => fun.apply()
				}
				/** BatchUpdateSecurityIncident updates multiple existing security incidents. */
				class batchUpdate(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Optional. The parent resource shared by all security incidents being updated. If this is set, the parent field in the UpdateSecurityIncidentRequest messages must either be empty or match this field. */
					def withParent(parent: String) = new batchUpdate(req.addQueryStringParameters("parent" -> parent.toString))
					/** Perform the request */
					def withGoogleCloudApigeeV1BatchUpdateSecurityIncidentsRequest(body: Schema.GoogleCloudApigeeV1BatchUpdateSecurityIncidentsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudApigeeV1BatchUpdateSecurityIncidentsResponse])
				}
				object batchUpdate {
					def apply(organizationsId :PlayApi, environmentsId :PlayApi)(using signer: RequestSigner, ec: ExecutionContext): batchUpdate = new batchUpdate(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/securityIncidents:batchUpdate").addQueryStringParameters())
				}
				/** ListSecurityIncidents lists all the security incident associated with the environment. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1ListSecurityIncidentsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Optional. A page token, received from a previous `ListSecurityIncident` call. Provide this to retrieve the subsequent page. */
					def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
					/** Optional. The maximum number of incidents to return. The service may return fewer than this value. If unspecified, at most 50 incidents will be returned.<br>Format: int32 */
					def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudApigeeV1ListSecurityIncidentsResponse])
				}
				object list {
					def apply(organizationsId :PlayApi, environmentsId :PlayApi, parent: String, filter: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/securityIncidents").addQueryStringParameters("parent" -> parent.toString, "filter" -> filter.toString))
					given Conversion[list, Future[Schema.GoogleCloudApigeeV1ListSecurityIncidentsResponse]] = (fun: list) => fun.apply()
				}
				/** UpdateSecurityIncidents updates an existing security incident. */
				class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGoogleCloudApigeeV1SecurityIncident(body: Schema.GoogleCloudApigeeV1SecurityIncident) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudApigeeV1SecurityIncident])
				}
				object patch {
					def apply(organizationsId :PlayApi, environmentsId :PlayApi, securityIncidentsId :PlayApi, updateMask: String, name: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/securityIncidents/${securityIncidentsId}").addQueryStringParameters("updateMask" -> updateMask.toString, "name" -> name.toString))
				}
			}
			object archiveDeployments {
				/** Generates a signed URL for downloading the original zip file used to create an Archive Deployment. The URL is only valid for a limited period and should be used within minutes after generation. Each call returns a new upload URL. */
				class generateDownloadUrl(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGoogleCloudApigeeV1GenerateDownloadUrlRequest(body: Schema.GoogleCloudApigeeV1GenerateDownloadUrlRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudApigeeV1GenerateDownloadUrlResponse])
				}
				object generateDownloadUrl {
					def apply(organizationsId :PlayApi, environmentsId :PlayApi, archiveDeploymentsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): generateDownloadUrl = new generateDownloadUrl(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/archiveDeployments/${archiveDeploymentsId}:generateDownloadUrl").addQueryStringParameters("name" -> name.toString))
				}
				/** Creates a new ArchiveDeployment. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGoogleCloudApigeeV1ArchiveDeployment(body: Schema.GoogleCloudApigeeV1ArchiveDeployment) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
				}
				object create {
					def apply(organizationsId :PlayApi, environmentsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/archiveDeployments").addQueryStringParameters("parent" -> parent.toString))
				}
				/** Generates a signed URL for uploading an Archive zip file to Google Cloud Storage. Once the upload is complete, the signed URL should be passed to CreateArchiveDeployment. When uploading to the generated signed URL, please follow these restrictions: &#42; Source file type should be a zip file. &#42; Source file size should not exceed 1GB limit. &#42; No credentials should be attached - the signed URLs provide access to the target bucket using internal service identity; if credentials were attached, the identity from the credentials would be used, but that identity does not have permissions to upload files to the URL. When making a HTTP PUT request, these two headers need to be specified: &#42; `content-type: application/zip` &#42; `x-goog-content-length-range: 0,1073741824` And this header SHOULD NOT be specified: &#42; `Authorization: Bearer YOUR_TOKEN` */
				class generateUploadUrl(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGoogleCloudApigeeV1GenerateUploadUrlRequest(body: Schema.GoogleCloudApigeeV1GenerateUploadUrlRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudApigeeV1GenerateUploadUrlResponse])
				}
				object generateUploadUrl {
					def apply(organizationsId :PlayApi, environmentsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): generateUploadUrl = new generateUploadUrl(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/archiveDeployments:generateUploadUrl").addQueryStringParameters("parent" -> parent.toString))
				}
				/** Deletes an archive deployment. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
				}
				object delete {
					def apply(organizationsId :PlayApi, environmentsId :PlayApi, archiveDeploymentsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/archiveDeployments/${archiveDeploymentsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
				}
				/** Gets the specified ArchiveDeployment. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1ArchiveDeployment]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudApigeeV1ArchiveDeployment])
				}
				object get {
					def apply(organizationsId :PlayApi, environmentsId :PlayApi, archiveDeploymentsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/archiveDeployments/${archiveDeploymentsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleCloudApigeeV1ArchiveDeployment]] = (fun: get) => fun.apply()
				}
				/** Updates an existing ArchiveDeployment. Labels can modified but most of the other fields are not modifiable. */
				class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGoogleCloudApigeeV1ArchiveDeployment(body: Schema.GoogleCloudApigeeV1ArchiveDeployment) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudApigeeV1ArchiveDeployment])
				}
				object patch {
					def apply(organizationsId :PlayApi, environmentsId :PlayApi, archiveDeploymentsId :PlayApi, updateMask: String, name: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/archiveDeployments/${archiveDeploymentsId}").addQueryStringParameters("updateMask" -> updateMask.toString, "name" -> name.toString))
				}
				/** Lists the ArchiveDeployments in the specified Environment. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1ListArchiveDeploymentsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Optional. Maximum number of Archive Deployments to return. If unspecified, at most 25 deployments will be returned.<br>Format: int32 */
					def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
					/** Optional. An optional query used to return a subset of Archive Deployments using the semantics defined in https://google.aip.dev/160. */
					def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
					/** Optional. Page token, returned from a previous ListArchiveDeployments call, that you can use to retrieve the next page. */
					def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudApigeeV1ListArchiveDeploymentsResponse])
				}
				object list {
					def apply(organizationsId :PlayApi, environmentsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/archiveDeployments").addQueryStringParameters("parent" -> parent.toString))
					given Conversion[list, Future[Schema.GoogleCloudApigeeV1ListArchiveDeploymentsResponse]] = (fun: list) => fun.apply()
				}
			}
			object optimizedStats {
				/** Similar to GetStats except that the response is less verbose. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1OptimizedStats]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudApigeeV1OptimizedStats])
				}
				object get {
					def apply(organizationsId :PlayApi, environmentsId :PlayApi, optimizedStatsId :PlayApi, timeUnit: String, sonar: Boolean, name: String, timeRange: String, sort: String, offset: String, realtime: Boolean, accuracy: String, filter: String, tsAscending: Boolean, topk: String, aggTable: String, select: String, limit: String, sortby: String, tzo: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/optimizedStats/${optimizedStatsId}").addQueryStringParameters("timeUnit" -> timeUnit.toString, "sonar" -> sonar.toString, "name" -> name.toString, "timeRange" -> timeRange.toString, "sort" -> sort.toString, "offset" -> offset.toString, "realtime" -> realtime.toString, "accuracy" -> accuracy.toString, "filter" -> filter.toString, "tsAscending" -> tsAscending.toString, "topk" -> topk.toString, "aggTable" -> aggTable.toString, "select" -> select.toString, "limit" -> limit.toString, "sortby" -> sortby.toString, "tzo" -> tzo.toString))
					given Conversion[get, Future[Schema.GoogleCloudApigeeV1OptimizedStats]] = (fun: get) => fun.apply()
				}
			}
			object addonsConfig {
				/** Updates an add-on enablement status of an environment. */
				class setAddonEnablement(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGoogleCloudApigeeV1SetAddonEnablementRequest(body: Schema.GoogleCloudApigeeV1SetAddonEnablementRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
				}
				object setAddonEnablement {
					def apply(organizationsId :PlayApi, environmentsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): setAddonEnablement = new setAddonEnablement(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/addonsConfig:setAddonEnablement").addQueryStringParameters("name" -> name.toString))
				}
			}
			object targetservers {
				/** Gets a TargetServer resource. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1TargetServer]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudApigeeV1TargetServer])
				}
				object get {
					def apply(organizationsId :PlayApi, environmentsId :PlayApi, targetserversId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/targetservers/${targetserversId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleCloudApigeeV1TargetServer]] = (fun: get) => fun.apply()
				}
				/** Updates an existing TargetServer. Note that this operation has PUT semantics; it will replace the entirety of the existing TargetServer with the resource in the request body. */
				class update(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGoogleCloudApigeeV1TargetServer(body: Schema.GoogleCloudApigeeV1TargetServer) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.GoogleCloudApigeeV1TargetServer])
				}
				object update {
					def apply(organizationsId :PlayApi, environmentsId :PlayApi, targetserversId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/targetservers/${targetserversId}").addQueryStringParameters("name" -> name.toString))
				}
				/** Deletes a TargetServer from an environment. Returns the deleted TargetServer resource. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1TargetServer]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleCloudApigeeV1TargetServer])
				}
				object delete {
					def apply(organizationsId :PlayApi, environmentsId :PlayApi, targetserversId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/targetservers/${targetserversId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.GoogleCloudApigeeV1TargetServer]] = (fun: delete) => fun.apply()
				}
				/** Creates a TargetServer in the specified environment. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Optional. The ID to give the TargetServer. This will overwrite the value in TargetServer. */
					def withName(name: String) = new create(req.addQueryStringParameters("name" -> name.toString))
					/** Perform the request */
					def withGoogleCloudApigeeV1TargetServer(body: Schema.GoogleCloudApigeeV1TargetServer) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudApigeeV1TargetServer])
				}
				object create {
					def apply(organizationsId :PlayApi, environmentsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/targetservers").addQueryStringParameters("parent" -> parent.toString))
				}
			}
			object keyvaluemaps {
				/** Deletes a key value map from an environment. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1KeyValueMap]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleCloudApigeeV1KeyValueMap])
				}
				object delete {
					def apply(organizationsId :PlayApi, environmentsId :PlayApi, keyvaluemapsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/keyvaluemaps/${keyvaluemapsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.GoogleCloudApigeeV1KeyValueMap]] = (fun: delete) => fun.apply()
				}
				/** Creates a key value map in an environment. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGoogleCloudApigeeV1KeyValueMap(body: Schema.GoogleCloudApigeeV1KeyValueMap) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudApigeeV1KeyValueMap])
				}
				object create {
					def apply(organizationsId :PlayApi, environmentsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/keyvaluemaps").addQueryStringParameters("parent" -> parent.toString))
				}
				object entries {
					/** Creates key value entries in a key value map scoped to an organization, environment, or API proxy. &#42;&#42;Note&#42;&#42;: Supported for Apigee hybrid 1.8.x and higher. */
					class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withGoogleCloudApigeeV1KeyValueEntry(body: Schema.GoogleCloudApigeeV1KeyValueEntry) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudApigeeV1KeyValueEntry])
					}
					object create {
						def apply(organizationsId :PlayApi, environmentsId :PlayApi, keyvaluemapsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/keyvaluemaps/${keyvaluemapsId}/entries").addQueryStringParameters("parent" -> parent.toString))
					}
					/** Deletes a key value entry from a key value map scoped to an organization, environment, or API proxy. &#42;&#42;Notes:&#42;&#42; &#42; After you delete the key value entry, the policy consuming the entry will continue to function with its cached values for a few minutes. This is expected behavior. &#42; Supported for Apigee hybrid 1.8.x and higher. */
					class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1KeyValueEntry]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleCloudApigeeV1KeyValueEntry])
					}
					object delete {
						def apply(organizationsId :PlayApi, environmentsId :PlayApi, keyvaluemapsId :PlayApi, entriesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/keyvaluemaps/${keyvaluemapsId}/entries/${entriesId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[delete, Future[Schema.GoogleCloudApigeeV1KeyValueEntry]] = (fun: delete) => fun.apply()
					}
					/** Get the key value entry value for a key value map scoped to an organization, environment, or API proxy. &#42;&#42;Note&#42;&#42;: Supported for Apigee hybrid 1.8.x and higher. */
					class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1KeyValueEntry]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudApigeeV1KeyValueEntry])
					}
					object get {
						def apply(organizationsId :PlayApi, environmentsId :PlayApi, keyvaluemapsId :PlayApi, entriesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/keyvaluemaps/${keyvaluemapsId}/entries/${entriesId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.GoogleCloudApigeeV1KeyValueEntry]] = (fun: get) => fun.apply()
					}
					/** Update key value entry scoped to an organization, environment, or API proxy for an existing key. */
					class update(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withGoogleCloudApigeeV1KeyValueEntry(body: Schema.GoogleCloudApigeeV1KeyValueEntry) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.GoogleCloudApigeeV1KeyValueEntry])
					}
					object update {
						def apply(organizationsId :PlayApi, environmentsId :PlayApi, keyvaluemapsId :PlayApi, entriesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/keyvaluemaps/${keyvaluemapsId}/entries/${entriesId}").addQueryStringParameters("name" -> name.toString))
					}
					/** Lists key value entries for key values maps scoped to an organization, environment, or API proxy. &#42;&#42;Note&#42;&#42;: Supported for Apigee hybrid 1.8.x and higher. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1ListKeyValueEntriesResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Optional. Page token. If provides, must be a valid key value entry returned from a previous call that can be used to retrieve the next page. */
						def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
						/** Optional. Maximum number of key value entries to return. If unspecified, at most 100 entries will be returned.<br>Format: int32 */
						def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudApigeeV1ListKeyValueEntriesResponse])
					}
					object list {
						def apply(organizationsId :PlayApi, environmentsId :PlayApi, keyvaluemapsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/keyvaluemaps/${keyvaluemapsId}/entries").addQueryStringParameters("parent" -> parent.toString))
						given Conversion[list, Future[Schema.GoogleCloudApigeeV1ListKeyValueEntriesResponse]] = (fun: list) => fun.apply()
					}
				}
			}
			object securityReports {
				/** After the query is completed, use this API to retrieve the results as file. If the request succeeds, and there is a non-zero result set, the result is downloaded to the client as a zipped JSON file. The name of the downloaded file will be: OfflineQueryResult-.zip Example: `OfflineQueryResult-9cfc0d85-0f30-46d6-ae6f-318d0cb961bd.zip` */
				class getResult(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleApiHttpBody]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleApiHttpBody])
				}
				object getResult {
					def apply(organizationsId :PlayApi, environmentsId :PlayApi, securityReportsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): getResult = new getResult(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/securityReports/${securityReportsId}/result").addQueryStringParameters("name" -> name.toString))
					given Conversion[getResult, Future[Schema.GoogleApiHttpBody]] = (fun: getResult) => fun.apply()
				}
				/** Submit a report request to be processed in the background. If the submission succeeds, the API returns a 200 status and an ID that refer to the report request. In addition to the HTTP status 200, the `state` of "enqueued" means that the request succeeded. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGoogleCloudApigeeV1SecurityReportQuery(body: Schema.GoogleCloudApigeeV1SecurityReportQuery) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudApigeeV1SecurityReport])
				}
				object create {
					def apply(organizationsId :PlayApi, environmentsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/securityReports").addQueryStringParameters("parent" -> parent.toString))
				}
				/** After the query is completed, use this API to view the query result when result size is small. */
				class getResultView(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1SecurityReportResultView]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudApigeeV1SecurityReportResultView])
				}
				object getResultView {
					def apply(organizationsId :PlayApi, environmentsId :PlayApi, securityReportsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): getResultView = new getResultView(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/securityReports/${securityReportsId}/resultView").addQueryStringParameters("name" -> name.toString))
					given Conversion[getResultView, Future[Schema.GoogleCloudApigeeV1SecurityReportResultView]] = (fun: getResultView) => fun.apply()
				}
				/** Get security report status If the query is still in progress, the `state` is set to "running" After the query has completed successfully, `state` is set to "completed" */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1SecurityReport]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudApigeeV1SecurityReport])
				}
				object get {
					def apply(organizationsId :PlayApi, environmentsId :PlayApi, securityReportsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/securityReports/${securityReportsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleCloudApigeeV1SecurityReport]] = (fun: get) => fun.apply()
				}
				/** Return a list of Security Reports */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1ListSecurityReportsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudApigeeV1ListSecurityReportsResponse])
				}
				object list {
					def apply(organizationsId :PlayApi, environmentsId :PlayApi, dataset: String, status: String, to: String, from: String, submittedBy: String, parent: String, pageToken: String, pageSize: Int)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/securityReports").addQueryStringParameters("dataset" -> dataset.toString, "status" -> status.toString, "to" -> to.toString, "from" -> from.toString, "submittedBy" -> submittedBy.toString, "parent" -> parent.toString, "pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString))
					given Conversion[list, Future[Schema.GoogleCloudApigeeV1ListSecurityReportsResponse]] = (fun: list) => fun.apply()
				}
			}
			object apis {
				object deployments {
					/** Lists all deployments of an API proxy in an environment. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1ListDeploymentsResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudApigeeV1ListDeploymentsResponse])
					}
					object list {
						def apply(organizationsId :PlayApi, environmentsId :PlayApi, apisId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/apis/${apisId}/deployments").addQueryStringParameters("parent" -> parent.toString))
						given Conversion[list, Future[Schema.GoogleCloudApigeeV1ListDeploymentsResponse]] = (fun: list) => fun.apply()
					}
				}
				object revisions {
					/** Gets the deployment of an API proxy revision and actual state reported by runtime pods. */
					class getDeployments(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1Deployment]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudApigeeV1Deployment])
					}
					object getDeployments {
						def apply(organizationsId :PlayApi, environmentsId :PlayApi, apisId :PlayApi, revisionsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): getDeployments = new getDeployments(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/apis/${apisId}/revisions/${revisionsId}/deployments").addQueryStringParameters("name" -> name.toString))
						given Conversion[getDeployments, Future[Schema.GoogleCloudApigeeV1Deployment]] = (fun: getDeployments) => fun.apply()
					}
					/** Undeploys an API proxy revision from an environment. For a request path `organizations/{org}/environments/{env}/apis/{api}/revisions/{rev}/deployments`, two permissions are required: &#42; `apigee.deployments.delete` on the resource `organizations/{org}/environments/{env}` &#42; `apigee.proxyrevisions.undeploy` on the resource `organizations/{org}/apis/{api}/revisions/{rev}` */
					class undeploy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
					}
					object undeploy {
						def apply(organizationsId :PlayApi, environmentsId :PlayApi, apisId :PlayApi, revisionsId :PlayApi, name: String, sequencedRollout: Boolean)(using signer: RequestSigner, ec: ExecutionContext): undeploy = new undeploy(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/apis/${apisId}/revisions/${revisionsId}/deployments").addQueryStringParameters("name" -> name.toString, "sequencedRollout" -> sequencedRollout.toString))
						given Conversion[undeploy, Future[Schema.GoogleProtobufEmpty]] = (fun: undeploy) => fun.apply()
					}
					/** Deploys a revision of an API proxy. If another revision of the same API proxy revision is currently deployed, set the `override` parameter to `true` to have this revision replace the currently deployed revision. You cannot invoke an API proxy until it has been deployed to an environment. After you deploy an API proxy revision, you cannot edit it. To edit the API proxy, you must create and deploy a new revision. For a request path `organizations/{org}/environments/{env}/apis/{api}/revisions/{rev}/deployments`, two permissions are required: &#42; `apigee.deployments.create` on the resource `organizations/{org}/environments/{env}` &#42; `apigee.proxyrevisions.deploy` on the resource `organizations/{org}/apis/{api}/revisions/{rev}`  */
					class deploy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1Deployment]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("POST")).map(_.json.as[Schema.GoogleCloudApigeeV1Deployment])
					}
					object deploy {
						def apply(organizationsId :PlayApi, environmentsId :PlayApi, apisId :PlayApi, revisionsId :PlayApi, sequencedRollout: Boolean, `override`: Boolean, name: String, serviceAccount: String)(using signer: RequestSigner, ec: ExecutionContext): deploy = new deploy(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/apis/${apisId}/revisions/${revisionsId}/deployments").addQueryStringParameters("sequencedRollout" -> sequencedRollout.toString, "override" -> `override`.toString, "name" -> name.toString, "serviceAccount" -> serviceAccount.toString))
						given Conversion[deploy, Future[Schema.GoogleCloudApigeeV1Deployment]] = (fun: deploy) => fun.apply()
					}
					object debugsessions {
						/** Creates a debug session for a deployed API Proxy revision. */
						class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Optional. The time in seconds after which this DebugSession should end. A timeout specified in DebugSession will overwrite this value.<br>Format: int64 */
							def withTimeout(timeout: String) = new create(req.addQueryStringParameters("timeout" -> timeout.toString))
							/** Perform the request */
							def withGoogleCloudApigeeV1DebugSession(body: Schema.GoogleCloudApigeeV1DebugSession) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudApigeeV1DebugSession])
						}
						object create {
							def apply(organizationsId :PlayApi, environmentsId :PlayApi, apisId :PlayApi, revisionsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/apis/${apisId}/revisions/${revisionsId}/debugsessions").addQueryStringParameters("parent" -> parent.toString))
						}
						/** Deletes the data from a debug session. This does not cancel the debug session or prevent further data from being collected if the session is still active in runtime pods. */
						class deleteData(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
						}
						object deleteData {
							def apply(organizationsId :PlayApi, environmentsId :PlayApi, apisId :PlayApi, revisionsId :PlayApi, debugsessionsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): deleteData = new deleteData(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/apis/${apisId}/revisions/${revisionsId}/debugsessions/${debugsessionsId}/data").addQueryStringParameters("name" -> name.toString))
							given Conversion[deleteData, Future[Schema.GoogleProtobufEmpty]] = (fun: deleteData) => fun.apply()
						}
						/** Retrieves a debug session. */
						class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1DebugSession]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudApigeeV1DebugSession])
						}
						object get {
							def apply(organizationsId :PlayApi, environmentsId :PlayApi, apisId :PlayApi, revisionsId :PlayApi, debugsessionsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/apis/${apisId}/revisions/${revisionsId}/debugsessions/${debugsessionsId}").addQueryStringParameters("name" -> name.toString))
							given Conversion[get, Future[Schema.GoogleCloudApigeeV1DebugSession]] = (fun: get) => fun.apply()
						}
						/** Lists debug sessions that are currently active in the given API Proxy revision. */
						class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1ListDebugSessionsResponse]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudApigeeV1ListDebugSessionsResponse])
						}
						object list {
							def apply(organizationsId :PlayApi, environmentsId :PlayApi, apisId :PlayApi, revisionsId :PlayApi, pageToken: String, parent: String, pageSize: Int)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/apis/${apisId}/revisions/${revisionsId}/debugsessions").addQueryStringParameters("pageToken" -> pageToken.toString, "parent" -> parent.toString, "pageSize" -> pageSize.toString))
							given Conversion[list, Future[Schema.GoogleCloudApigeeV1ListDebugSessionsResponse]] = (fun: list) => fun.apply()
						}
						object data {
							/** Gets the debug data from a transaction. */
							class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1DebugSessionTransaction]) {
								val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
								/** Perform the request */
								def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudApigeeV1DebugSessionTransaction])
							}
							object get {
								def apply(organizationsId :PlayApi, environmentsId :PlayApi, apisId :PlayApi, revisionsId :PlayApi, debugsessionsId :PlayApi, dataId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/apis/${apisId}/revisions/${revisionsId}/debugsessions/${debugsessionsId}/data/${dataId}").addQueryStringParameters("name" -> name.toString))
								given Conversion[get, Future[Schema.GoogleCloudApigeeV1DebugSessionTransaction]] = (fun: get) => fun.apply()
							}
						}
					}
					object deployments {
						/** Generates a report for a dry run analysis of a DeployApiProxy request without committing the deployment. In addition to the standard validations performed when adding deployments, additional analysis will be done to detect possible traffic routing changes that would result from this deployment being created. Any potential routing conflicts or unsafe changes will be reported in the response. This routing analysis is not performed for a non-dry-run DeployApiProxy request. For a request path `organizations/{org}/environments/{env}/apis/{api}/revisions/{rev}/deployments:generateDeployChangeReport`, two permissions are required: &#42; `apigee.deployments.create` on the resource `organizations/{org}/environments/{env}` &#42; `apigee.proxyrevisions.deploy` on the resource `organizations/{org}/apis/{api}/revisions/{rev}` */
						class generateDeployChangeReport(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1DeploymentChangeReport]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("POST")).map(_.json.as[Schema.GoogleCloudApigeeV1DeploymentChangeReport])
						}
						object generateDeployChangeReport {
							def apply(organizationsId :PlayApi, environmentsId :PlayApi, apisId :PlayApi, revisionsId :PlayApi, `override`: Boolean, name: String)(using signer: RequestSigner, ec: ExecutionContext): generateDeployChangeReport = new generateDeployChangeReport(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/apis/${apisId}/revisions/${revisionsId}/deployments:generateDeployChangeReport").addQueryStringParameters("override" -> `override`.toString, "name" -> name.toString))
							given Conversion[generateDeployChangeReport, Future[Schema.GoogleCloudApigeeV1DeploymentChangeReport]] = (fun: generateDeployChangeReport) => fun.apply()
						}
						/** Generates a report for a dry run analysis of an UndeployApiProxy request without committing the undeploy. In addition to the standard validations performed when removing deployments, additional analysis will be done to detect possible traffic routing changes that would result from this deployment being removed. Any potential routing conflicts or unsafe changes will be reported in the response. This routing analysis is not performed for a non-dry-run UndeployApiProxy request. For a request path `organizations/{org}/environments/{env}/apis/{api}/revisions/{rev}/deployments:generateUndeployChangeReport`, two permissions are required: &#42; `apigee.deployments.delete` on the resource `organizations/{org}/environments/{env}` &#42; `apigee.proxyrevisions.undeploy` on the resource `organizations/{org}/apis/{api}/revisions/{rev}` */
						class generateUndeployChangeReport(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1DeploymentChangeReport]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("POST")).map(_.json.as[Schema.GoogleCloudApigeeV1DeploymentChangeReport])
						}
						object generateUndeployChangeReport {
							def apply(organizationsId :PlayApi, environmentsId :PlayApi, apisId :PlayApi, revisionsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): generateUndeployChangeReport = new generateUndeployChangeReport(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/apis/${apisId}/revisions/${revisionsId}/deployments:generateUndeployChangeReport").addQueryStringParameters("name" -> name.toString))
							given Conversion[generateUndeployChangeReport, Future[Schema.GoogleCloudApigeeV1DeploymentChangeReport]] = (fun: generateUndeployChangeReport) => fun.apply()
						}
					}
				}
			}
			object flowhooks {
				/** Detaches a shared flow from a flow hook. */
				class detachSharedFlowFromFlowHook(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1FlowHook]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleCloudApigeeV1FlowHook])
				}
				object detachSharedFlowFromFlowHook {
					def apply(organizationsId :PlayApi, environmentsId :PlayApi, flowhooksId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): detachSharedFlowFromFlowHook = new detachSharedFlowFromFlowHook(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/flowhooks/${flowhooksId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[detachSharedFlowFromFlowHook, Future[Schema.GoogleCloudApigeeV1FlowHook]] = (fun: detachSharedFlowFromFlowHook) => fun.apply()
				}
				/** Returns the name of the shared flow attached to the specified flow hook. If there's no shared flow attached to the flow hook, the API does not return an error; it simply does not return a name in the response. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1FlowHook]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudApigeeV1FlowHook])
				}
				object get {
					def apply(organizationsId :PlayApi, environmentsId :PlayApi, flowhooksId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/flowhooks/${flowhooksId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleCloudApigeeV1FlowHook]] = (fun: get) => fun.apply()
				}
				/** Attaches a shared flow to a flow hook. */
				class attachSharedFlowToFlowHook(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGoogleCloudApigeeV1FlowHook(body: Schema.GoogleCloudApigeeV1FlowHook) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.GoogleCloudApigeeV1FlowHook])
				}
				object attachSharedFlowToFlowHook {
					def apply(organizationsId :PlayApi, environmentsId :PlayApi, flowhooksId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): attachSharedFlowToFlowHook = new attachSharedFlowToFlowHook(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/flowhooks/${flowhooksId}").addQueryStringParameters("name" -> name.toString))
				}
			}
			object sharedflows {
				object revisions {
					/** Deploys a revision of a shared flow. If another revision of the same shared flow is currently deployed, set the `override` parameter to `true` to have this revision replace the currently deployed revision. You cannot use a shared flow until it has been deployed to an environment. For a request path `organizations/{org}/environments/{env}/sharedflows/{sf}/revisions/{rev}/deployments`, two permissions are required: &#42; `apigee.deployments.create` on the resource `organizations/{org}/environments/{env}` &#42; `apigee.sharedflowrevisions.deploy` on the resource `organizations/{org}/sharedflows/{sf}/revisions/{rev}` */
					class deploy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1Deployment]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("POST")).map(_.json.as[Schema.GoogleCloudApigeeV1Deployment])
					}
					object deploy {
						def apply(organizationsId :PlayApi, environmentsId :PlayApi, sharedflowsId :PlayApi, revisionsId :PlayApi, name: String, serviceAccount: String, `override`: Boolean)(using signer: RequestSigner, ec: ExecutionContext): deploy = new deploy(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/sharedflows/${sharedflowsId}/revisions/${revisionsId}/deployments").addQueryStringParameters("name" -> name.toString, "serviceAccount" -> serviceAccount.toString, "override" -> `override`.toString))
						given Conversion[deploy, Future[Schema.GoogleCloudApigeeV1Deployment]] = (fun: deploy) => fun.apply()
					}
					/** Undeploys a shared flow revision from an environment. For a request path `organizations/{org}/environments/{env}/sharedflows/{sf}/revisions/{rev}/deployments`, two permissions are required: &#42; `apigee.deployments.delete` on the resource `organizations/{org}/environments/{env}` &#42; `apigee.sharedflowrevisions.undeploy` on the resource `organizations/{org}/sharedflows/{sf}/revisions/{rev}` */
					class undeploy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
					}
					object undeploy {
						def apply(organizationsId :PlayApi, environmentsId :PlayApi, sharedflowsId :PlayApi, revisionsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): undeploy = new undeploy(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/sharedflows/${sharedflowsId}/revisions/${revisionsId}/deployments").addQueryStringParameters("name" -> name.toString))
						given Conversion[undeploy, Future[Schema.GoogleProtobufEmpty]] = (fun: undeploy) => fun.apply()
					}
					/** Gets the deployment of a shared flow revision and actual state reported by runtime pods. */
					class getDeployments(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1Deployment]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudApigeeV1Deployment])
					}
					object getDeployments {
						def apply(organizationsId :PlayApi, environmentsId :PlayApi, sharedflowsId :PlayApi, revisionsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): getDeployments = new getDeployments(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/sharedflows/${sharedflowsId}/revisions/${revisionsId}/deployments").addQueryStringParameters("name" -> name.toString))
						given Conversion[getDeployments, Future[Schema.GoogleCloudApigeeV1Deployment]] = (fun: getDeployments) => fun.apply()
					}
				}
				object deployments {
					/** Lists all deployments of a shared flow in an environment. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1ListDeploymentsResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudApigeeV1ListDeploymentsResponse])
					}
					object list {
						def apply(organizationsId :PlayApi, environmentsId :PlayApi, sharedflowsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/sharedflows/${sharedflowsId}/deployments").addQueryStringParameters("parent" -> parent.toString))
						given Conversion[list, Future[Schema.GoogleCloudApigeeV1ListDeploymentsResponse]] = (fun: list) => fun.apply()
					}
				}
			}
			object resourcefiles {
				/** Lists all resource files, optionally filtering by type. For more information about resource files, see [Resource files](https://cloud.google.com/apigee/docs/api-platform/develop/resource-files). */
				class listEnvironmentResources(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1ListEnvironmentResourcesResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Optional. Type of resource files to list. {{ resource_file_type }} */
					def withType(`type`: String) = new listEnvironmentResources(req.addQueryStringParameters("type" -> `type`.toString))
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudApigeeV1ListEnvironmentResourcesResponse])
				}
				object listEnvironmentResources {
					def apply(organizationsId :PlayApi, environmentsId :PlayApi, `type` :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): listEnvironmentResources = new listEnvironmentResources(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/resourcefiles/${`type`}").addQueryStringParameters("parent" -> parent.toString))
					given Conversion[listEnvironmentResources, Future[Schema.GoogleCloudApigeeV1ListEnvironmentResourcesResponse]] = (fun: listEnvironmentResources) => fun.apply()
				}
				/** Creates a resource file. Specify the `Content-Type` as `application/octet-stream` or `multipart/form-data`. For more information about resource files, see [Resource files](https://cloud.google.com/apigee/docs/api-platform/develop/resource-files). */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGoogleApiHttpBody(body: Schema.GoogleApiHttpBody) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudApigeeV1ResourceFile])
				}
				object create {
					def apply(organizationsId :PlayApi, environmentsId :PlayApi, `type`: String, parent: String, name: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/resourcefiles").addQueryStringParameters("type" -> `type`.toString, "parent" -> parent.toString, "name" -> name.toString))
				}
				/** Deletes a resource file. For more information about resource files, see [Resource files](https://cloud.google.com/apigee/docs/api-platform/develop/resource-files). */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1ResourceFile]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleCloudApigeeV1ResourceFile])
				}
				object delete {
					def apply(organizationsId :PlayApi, environmentsId :PlayApi, `type`: String, name: String, parent: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/resourcefiles/${`type`}/${name}").addQueryStringParameters("parent" -> parent.toString))
					given Conversion[delete, Future[Schema.GoogleCloudApigeeV1ResourceFile]] = (fun: delete) => fun.apply()
				}
				/** Gets the contents of a resource file. For more information about resource files, see [Resource files](https://cloud.google.com/apigee/docs/api-platform/develop/resource-files). */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleApiHttpBody]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleApiHttpBody])
				}
				object get {
					def apply(organizationsId :PlayApi, environmentsId :PlayApi, `type`: String, parent: String, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/resourcefiles/${`type`}/${name}").addQueryStringParameters("parent" -> parent.toString))
					given Conversion[get, Future[Schema.GoogleApiHttpBody]] = (fun: get) => fun.apply()
				}
				/** Updates a resource file. Specify the `Content-Type` as `application/octet-stream` or `multipart/form-data`. For more information about resource files, see [Resource files](https://cloud.google.com/apigee/docs/api-platform/develop/resource-files). */
				class update(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGoogleApiHttpBody(body: Schema.GoogleApiHttpBody) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.GoogleCloudApigeeV1ResourceFile])
				}
				object update {
					def apply(organizationsId :PlayApi, environmentsId :PlayApi, name: String, parent: String, `type`: String)(using signer: RequestSigner, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/resourcefiles/${`type`}/${name}").addQueryStringParameters("parent" -> parent.toString))
				}
				/** Lists all resource files, optionally filtering by type. For more information about resource files, see [Resource files](https://cloud.google.com/apigee/docs/api-platform/develop/resource-files). */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1ListEnvironmentResourcesResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Optional. Type of resource files to list. {{ resource_file_type }} */
					def withType(`type`: String) = new list(req.addQueryStringParameters("type" -> `type`.toString))
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudApigeeV1ListEnvironmentResourcesResponse])
				}
				object list {
					def apply(organizationsId :PlayApi, environmentsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/resourcefiles").addQueryStringParameters("parent" -> parent.toString))
					given Conversion[list, Future[Schema.GoogleCloudApigeeV1ListEnvironmentResourcesResponse]] = (fun: list) => fun.apply()
				}
			}
			object references {
				/** Updates an existing Reference. Note that this operation has PUT semantics; it will replace the entirety of the existing Reference with the resource in the request body. */
				class update(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGoogleCloudApigeeV1Reference(body: Schema.GoogleCloudApigeeV1Reference) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.GoogleCloudApigeeV1Reference])
				}
				object update {
					def apply(organizationsId :PlayApi, environmentsId :PlayApi, referencesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/references/${referencesId}").addQueryStringParameters("name" -> name.toString))
				}
				/** Creates a Reference in the specified environment. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGoogleCloudApigeeV1Reference(body: Schema.GoogleCloudApigeeV1Reference) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudApigeeV1Reference])
				}
				object create {
					def apply(organizationsId :PlayApi, environmentsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/references").addQueryStringParameters("parent" -> parent.toString))
				}
				/** Gets a Reference resource. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1Reference]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudApigeeV1Reference])
				}
				object get {
					def apply(organizationsId :PlayApi, environmentsId :PlayApi, referencesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/references/${referencesId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleCloudApigeeV1Reference]] = (fun: get) => fun.apply()
				}
				/** Deletes a Reference from an environment. Returns the deleted Reference resource. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1Reference]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleCloudApigeeV1Reference])
				}
				object delete {
					def apply(organizationsId :PlayApi, environmentsId :PlayApi, referencesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/references/${referencesId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.GoogleCloudApigeeV1Reference]] = (fun: delete) => fun.apply()
				}
			}
			object securityActions {
				/** Enable a SecurityAction. The `state` of the SecurityAction after enabling is `ENABLED`. `EnableSecurityAction` can be called on SecurityActions in the state `DISABLED`; SecurityActions in a different state (including `ENABLED) return an error. */
				class enable(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGoogleCloudApigeeV1EnableSecurityActionRequest(body: Schema.GoogleCloudApigeeV1EnableSecurityActionRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudApigeeV1SecurityAction])
				}
				object enable {
					def apply(organizationsId :PlayApi, environmentsId :PlayApi, securityActionsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): enable = new enable(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/securityActions/${securityActionsId}:enable").addQueryStringParameters("name" -> name.toString))
				}
				/** CreateSecurityAction creates a SecurityAction. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGoogleCloudApigeeV1SecurityAction(body: Schema.GoogleCloudApigeeV1SecurityAction) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudApigeeV1SecurityAction])
				}
				object create {
					def apply(organizationsId :PlayApi, environmentsId :PlayApi, parent: String, securityActionId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/securityActions").addQueryStringParameters("parent" -> parent.toString, "securityActionId" -> securityActionId.toString))
				}
				/** Disable a SecurityAction. The `state` of the SecurityAction after disabling is `DISABLED`. `DisableSecurityAction` can be called on SecurityActions in the state `ENABLED`; SecurityActions in a different state (including `DISABLED`) return an error. */
				class disable(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGoogleCloudApigeeV1DisableSecurityActionRequest(body: Schema.GoogleCloudApigeeV1DisableSecurityActionRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudApigeeV1SecurityAction])
				}
				object disable {
					def apply(organizationsId :PlayApi, environmentsId :PlayApi, securityActionsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): disable = new disable(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/securityActions/${securityActionsId}:disable").addQueryStringParameters("name" -> name.toString))
				}
				/** Get a SecurityAction by name. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1SecurityAction]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudApigeeV1SecurityAction])
				}
				object get {
					def apply(organizationsId :PlayApi, environmentsId :PlayApi, securityActionsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/securityActions/${securityActionsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleCloudApigeeV1SecurityAction]] = (fun: get) => fun.apply()
				}
				/** Returns a list of SecurityActions. This returns both enabled and disabled actions. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1ListSecurityActionsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudApigeeV1ListSecurityActionsResponse])
				}
				object list {
					def apply(organizationsId :PlayApi, environmentsId :PlayApi, filter: String, parent: String, pageToken: String, pageSize: Int)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/securityActions").addQueryStringParameters("filter" -> filter.toString, "parent" -> parent.toString, "pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString))
					given Conversion[list, Future[Schema.GoogleCloudApigeeV1ListSecurityActionsResponse]] = (fun: list) => fun.apply()
				}
			}
			object caches {
				/** Deletes a cache. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
				}
				object delete {
					def apply(organizationsId :PlayApi, environmentsId :PlayApi, cachesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/caches/${cachesId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
				}
			}
			object keystores {
				/** Gets a keystore or truststore. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1Keystore]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudApigeeV1Keystore])
				}
				object get {
					def apply(organizationsId :PlayApi, environmentsId :PlayApi, keystoresId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/keystores/${keystoresId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleCloudApigeeV1Keystore]] = (fun: get) => fun.apply()
				}
				/** Creates a keystore or truststore. - Keystore: Contains certificates and their associated keys. - Truststore: Contains trusted certificates used to validate a server's certificate. These certificates are typically self-signed certificates or certificates that are not signed by a trusted CA. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Optional. Name of the keystore. Overrides the value in Keystore. */
					def withName(name: String) = new create(req.addQueryStringParameters("name" -> name.toString))
					/** Perform the request */
					def withGoogleCloudApigeeV1Keystore(body: Schema.GoogleCloudApigeeV1Keystore) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudApigeeV1Keystore])
				}
				object create {
					def apply(organizationsId :PlayApi, environmentsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/keystores").addQueryStringParameters("parent" -> parent.toString))
				}
				/** Deletes a keystore or truststore. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1Keystore]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleCloudApigeeV1Keystore])
				}
				object delete {
					def apply(organizationsId :PlayApi, environmentsId :PlayApi, keystoresId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/keystores/${keystoresId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.GoogleCloudApigeeV1Keystore]] = (fun: delete) => fun.apply()
				}
				object aliases {
					/** Creates an alias from a key/certificate pair. The structure of the request is controlled by the `format` query parameter: - `keycertfile` - Separate PEM-encoded key and certificate files are uploaded. Set `Content-Type: multipart/form-data` and include the `keyFile`, `certFile`, and `password` (if keys are encrypted) fields in the request body. If uploading to a truststore, omit `keyFile`. - `pkcs12` - A PKCS12 file is uploaded. Set `Content-Type: multipart/form-data`, provide the file in the `file` field, and include the `password` field if the file is encrypted in the request body. - `selfsignedcert` - A new private key and certificate are generated. Set `Content-Type: application/json` and include CertificateGenerationSpec in the request body. */
					class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withGoogleApiHttpBody(body: Schema.GoogleApiHttpBody) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudApigeeV1Alias])
					}
					object create {
						def apply(organizationsId :PlayApi, environmentsId :PlayApi, keystoresId :PlayApi, ignoreNewlineValidation: Boolean, ignoreExpiryValidation: Boolean, format: String, alias: String, _password: String, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/keystores/${keystoresId}/aliases").addQueryStringParameters("ignoreNewlineValidation" -> ignoreNewlineValidation.toString, "ignoreExpiryValidation" -> ignoreExpiryValidation.toString, "format" -> format.toString, "alias" -> alias.toString, "_password" -> _password.toString, "parent" -> parent.toString))
					}
					/** Generates a PKCS #10 Certificate Signing Request for the private key in an alias. */
					class csr(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleApiHttpBody]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleApiHttpBody])
					}
					object csr {
						def apply(organizationsId :PlayApi, environmentsId :PlayApi, keystoresId :PlayApi, aliasesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): csr = new csr(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/keystores/${keystoresId}/aliases/${aliasesId}/csr").addQueryStringParameters("name" -> name.toString))
						given Conversion[csr, Future[Schema.GoogleApiHttpBody]] = (fun: csr) => fun.apply()
					}
					/** Deletes an alias. */
					class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1Alias]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleCloudApigeeV1Alias])
					}
					object delete {
						def apply(organizationsId :PlayApi, environmentsId :PlayApi, keystoresId :PlayApi, aliasesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/keystores/${keystoresId}/aliases/${aliasesId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[delete, Future[Schema.GoogleCloudApigeeV1Alias]] = (fun: delete) => fun.apply()
					}
					/** Gets an alias. */
					class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1Alias]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudApigeeV1Alias])
					}
					object get {
						def apply(organizationsId :PlayApi, environmentsId :PlayApi, keystoresId :PlayApi, aliasesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/keystores/${keystoresId}/aliases/${aliasesId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.GoogleCloudApigeeV1Alias]] = (fun: get) => fun.apply()
					}
					/** Updates the certificate in an alias. */
					class update(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withGoogleApiHttpBody(body: Schema.GoogleApiHttpBody) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.GoogleCloudApigeeV1Alias])
					}
					object update {
						def apply(organizationsId :PlayApi, environmentsId :PlayApi, keystoresId :PlayApi, aliasesId :PlayApi, ignoreNewlineValidation: Boolean, name: String, ignoreExpiryValidation: Boolean)(using signer: RequestSigner, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/keystores/${keystoresId}/aliases/${aliasesId}").addQueryStringParameters("ignoreNewlineValidation" -> ignoreNewlineValidation.toString, "name" -> name.toString, "ignoreExpiryValidation" -> ignoreExpiryValidation.toString))
					}
					/** Gets the certificate from an alias in PEM-encoded form. */
					class getCertificate(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleApiHttpBody]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleApiHttpBody])
					}
					object getCertificate {
						def apply(organizationsId :PlayApi, environmentsId :PlayApi, keystoresId :PlayApi, aliasesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): getCertificate = new getCertificate(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/keystores/${keystoresId}/aliases/${aliasesId}/certificate").addQueryStringParameters("name" -> name.toString))
						given Conversion[getCertificate, Future[Schema.GoogleApiHttpBody]] = (fun: getCertificate) => fun.apply()
					}
				}
			}
			object traceConfig {
				object overrides {
					/** Creates a trace configuration override. The response contains a system-generated UUID, that can be used to view, update, or delete the configuration override. Use the List API to view the existing trace configuration overrides. */
					class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withGoogleCloudApigeeV1TraceConfigOverride(body: Schema.GoogleCloudApigeeV1TraceConfigOverride) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudApigeeV1TraceConfigOverride])
					}
					object create {
						def apply(organizationsId :PlayApi, environmentsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/traceConfig/overrides").addQueryStringParameters("parent" -> parent.toString))
					}
					/** Deletes a distributed trace configuration override. */
					class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
					}
					object delete {
						def apply(organizationsId :PlayApi, environmentsId :PlayApi, overridesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/traceConfig/overrides/${overridesId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
					}
					/** Gets a trace configuration override. */
					class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1TraceConfigOverride]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudApigeeV1TraceConfigOverride])
					}
					object get {
						def apply(organizationsId :PlayApi, environmentsId :PlayApi, overridesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/traceConfig/overrides/${overridesId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.GoogleCloudApigeeV1TraceConfigOverride]] = (fun: get) => fun.apply()
					}
					/** Updates a distributed trace configuration override. Note that the repeated fields have replace semantics when included in the field mask and that they will be overwritten by the value of the fields in the request body. */
					class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withGoogleCloudApigeeV1TraceConfigOverride(body: Schema.GoogleCloudApigeeV1TraceConfigOverride) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudApigeeV1TraceConfigOverride])
					}
					object patch {
						def apply(organizationsId :PlayApi, environmentsId :PlayApi, overridesId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/traceConfig/overrides/${overridesId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
					}
					/** Lists all of the distributed trace configuration overrides in an environment. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1ListTraceConfigOverridesResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudApigeeV1ListTraceConfigOverridesResponse])
					}
					object list {
						def apply(organizationsId :PlayApi, environmentsId :PlayApi, pageToken: String, parent: String, pageSize: Int)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/traceConfig/overrides").addQueryStringParameters("pageToken" -> pageToken.toString, "parent" -> parent.toString, "pageSize" -> pageSize.toString))
						given Conversion[list, Future[Schema.GoogleCloudApigeeV1ListTraceConfigOverridesResponse]] = (fun: list) => fun.apply()
					}
				}
			}
			object analytics {
				object admin {
					/** Gets a list of metrics and dimensions that can be used to create analytics queries and reports. Each schema element contains the name of the field, its associated type, and a flag indicating whether it is a standard or custom field. */
					class getSchemav2(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1Schema]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudApigeeV1Schema])
					}
					object getSchemav2 {
						def apply(organizationsId :PlayApi, environmentsId :PlayApi, disableCache: Boolean, `type`: String, name: String)(using signer: RequestSigner, ec: ExecutionContext): getSchemav2 = new getSchemav2(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/analytics/admin/schemav2").addQueryStringParameters("disableCache" -> disableCache.toString, "type" -> `type`.toString, "name" -> name.toString))
						given Conversion[getSchemav2, Future[Schema.GoogleCloudApigeeV1Schema]] = (fun: getSchemav2) => fun.apply()
					}
				}
				object exports {
					/** Gets the details and status of an analytics export job. If the export job is still in progress, its `state` is set to "running". After the export job has completed successfully, its `state` is set to "completed". If the export job fails, its `state` is set to `failed`. */
					class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1Export]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudApigeeV1Export])
					}
					object get {
						def apply(organizationsId :PlayApi, environmentsId :PlayApi, exportsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/analytics/exports/${exportsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.GoogleCloudApigeeV1Export]] = (fun: get) => fun.apply()
					}
					/** Lists the details and status of all analytics export jobs belonging to the parent organization and environment. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1ListExportsResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudApigeeV1ListExportsResponse])
					}
					object list {
						def apply(organizationsId :PlayApi, environmentsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/analytics/exports").addQueryStringParameters("parent" -> parent.toString))
						given Conversion[list, Future[Schema.GoogleCloudApigeeV1ListExportsResponse]] = (fun: list) => fun.apply()
					}
					/** Submit a data export job to be processed in the background. If the request is successful, the API returns a 201 status, a URI that can be used to retrieve the status of the export job, and the `state` value of "enqueued". */
					class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withGoogleCloudApigeeV1ExportRequest(body: Schema.GoogleCloudApigeeV1ExportRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudApigeeV1Export])
					}
					object create {
						def apply(organizationsId :PlayApi, environmentsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/analytics/exports").addQueryStringParameters("parent" -> parent.toString))
					}
				}
			}
			object deployments {
				/** Tests the permissions of a user on a deployment, and returns a subset of permissions that the user has on the deployment. If the deployment does not exist, an empty permission set is returned (a NOT_FOUND error is not returned). */
				class testIamPermissions(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGoogleIamV1TestIamPermissionsRequest(body: Schema.GoogleIamV1TestIamPermissionsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleIamV1TestIamPermissionsResponse])
				}
				object testIamPermissions {
					def apply(organizationsId :PlayApi, environmentsId :PlayApi, deploymentsId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): testIamPermissions = new testIamPermissions(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/deployments/${deploymentsId}:testIamPermissions").addQueryStringParameters("resource" -> resource.toString))
				}
				/** Sets the IAM policy on a deployment, if the policy already exists it will be replaced. For more information, see [Manage users, roles, and permissions using the API](https://cloud.google.com/apigee/docs/api-platform/system-administration/manage-users-roles). You must have the `apigee.deployments.setIamPolicy` permission to call this API. */
				class setIamPolicy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGoogleIamV1SetIamPolicyRequest(body: Schema.GoogleIamV1SetIamPolicyRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleIamV1Policy])
				}
				object setIamPolicy {
					def apply(organizationsId :PlayApi, environmentsId :PlayApi, deploymentsId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): setIamPolicy = new setIamPolicy(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/deployments/${deploymentsId}:setIamPolicy").addQueryStringParameters("resource" -> resource.toString))
				}
				/** Gets the IAM policy on a deployment. For more information, see [Manage users, roles, and permissions using the API](https://cloud.google.com/apigee/docs/api-platform/system-administration/manage-users-roles). You must have the `apigee.deployments.getIamPolicy` permission to call this API. */
				class getIamPolicy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleIamV1Policy]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Optional. The maximum policy version that will be used to format the policy. Valid values are 0, 1, and 3. Requests specifying an invalid value will be rejected. Requests for policies with any conditional role bindings must specify version 3. Policies with no conditional role bindings may specify any valid value or leave the field unset. The policy in the response might use the policy version that you specified, or it might use a lower policy version. For example, if you specify version 3, but the policy has no conditional role bindings, the response uses version 1. To learn which resources support conditions in their IAM policies, see the [IAM documentation](https://cloud.google.com/iam/help/conditions/resource-policies).<br>Format: int32 */
					def withOptionsRequestedPolicyVersion(optionsRequestedPolicyVersion: Int) = new getIamPolicy(req.addQueryStringParameters("options.requestedPolicyVersion" -> optionsRequestedPolicyVersion.toString))
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleIamV1Policy])
				}
				object getIamPolicy {
					def apply(organizationsId :PlayApi, environmentsId :PlayApi, deploymentsId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): getIamPolicy = new getIamPolicy(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/deployments/${deploymentsId}:getIamPolicy").addQueryStringParameters("resource" -> resource.toString))
					given Conversion[getIamPolicy, Future[Schema.GoogleIamV1Policy]] = (fun: getIamPolicy) => fun.apply()
				}
				/** Gets a particular deployment of Api proxy or a shared flow in an environment */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1Deployment]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudApigeeV1Deployment])
				}
				object get {
					def apply(organizationsId :PlayApi, environmentsId :PlayApi, deploymentsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/deployments/${deploymentsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleCloudApigeeV1Deployment]] = (fun: get) => fun.apply()
				}
				/** Lists all deployments of API proxies or shared flows in an environment. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1ListDeploymentsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Optional. Flag that specifies whether to return shared flow or API proxy deployments. Set to `true` to return shared flow deployments; set to `false` to return API proxy deployments. Defaults to `false`. */
					def withSharedFlows(sharedFlows: Boolean) = new list(req.addQueryStringParameters("sharedFlows" -> sharedFlows.toString))
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudApigeeV1ListDeploymentsResponse])
				}
				object list {
					def apply(organizationsId :PlayApi, environmentsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/environments/${environmentsId}/deployments").addQueryStringParameters("parent" -> parent.toString))
					given Conversion[list, Future[Schema.GoogleCloudApigeeV1ListDeploymentsResponse]] = (fun: list) => fun.apply()
				}
			}
		}
		object operations {
			/** Lists operations that match the specified filter in the request. If the server doesn't support this method, it returns `UNIMPLEMENTED`. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningListOperationsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleLongrunningListOperationsResponse])
			}
			object list {
				def apply(organizationsId :PlayApi, pageSize: Int, name: String, filter: String, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/operations").addQueryStringParameters("pageSize" -> pageSize.toString, "name" -> name.toString, "filter" -> filter.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.GoogleLongrunningListOperationsResponse]] = (fun: list) => fun.apply()
			}
			/** Gets the latest state of a long-running operation. Clients can use this method to poll the operation result at intervals as recommended by the API service. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningOperation]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleLongrunningOperation])
			}
			object get {
				def apply(organizationsId :PlayApi, operationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.GoogleLongrunningOperation]] = (fun: get) => fun.apply()
			}
		}
		object apiproducts {
			/** Creates an API product in an organization. You create API products after you have proxied backend services using API proxies. An API product is a collection of API resources combined with quota settings and metadata that you can use to deliver customized and productized API bundles to your developer community. This metadata can include: - Scope - Environments - API proxies - Extensible profile API products enable you repackage APIs on the fly, without having to do any additional coding or configuration. Apigee recommends that you start with a simple API product including only required elements. You then provision credentials to apps to enable them to start testing your APIs. After you have authentication and authorization working against a simple API product, you can iterate to create finer-grained API products, defining different sets of API resources for each API product. &#42;&#42;WARNING:&#42;&#42; - If you don't specify an API proxy in the request body, &#42;any&#42; app associated with the product can make calls to &#42;any&#42; API in your entire organization. - If you don't specify an environment in the request body, the product allows access to all environments. For more information, see What is an API product? */
			class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withGoogleCloudApigeeV1ApiProduct(body: Schema.GoogleCloudApigeeV1ApiProduct) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudApigeeV1ApiProduct])
			}
			object create {
				def apply(organizationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/apiproducts").addQueryStringParameters("parent" -> parent.toString))
			}
			/** Updates or creates API product attributes. This API &#42;&#42;replaces&#42;&#42; the current list of attributes with the attributes specified in the request body. In this way, you can update existing attributes, add new attributes, or delete existing attributes by omitting them from the request body. &#42;&#42;Note&#42;&#42;: OAuth access tokens and Key Management Service (KMS) entities (apps, developers, and API products) are cached for 180 seconds (current default). Any custom attributes associated with entities also get cached for at least 180 seconds after entity is accessed during runtime. In this case, the `ExpiresIn` element on the OAuthV2 policy won't be able to expire an access token in less than 180 seconds. */
			class attributes(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withGoogleCloudApigeeV1Attributes(body: Schema.GoogleCloudApigeeV1Attributes) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudApigeeV1Attributes])
			}
			object attributes {
				def apply(organizationsId :PlayApi, apiproductsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): attributes = new attributes(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/apiproducts/${apiproductsId}/attributes").addQueryStringParameters("name" -> name.toString))
				/** Updates the value of an API product attribute. &#42;&#42;Note&#42;&#42;: OAuth access tokens and Key Management Service (KMS) entities (apps, developers, and API products) are cached for 180 seconds (current default). Any custom attributes associated with entities also get cached for at least 180 seconds after entity is accessed during runtime. In this case, the `ExpiresIn` element on the OAuthV2 policy won't be able to expire an access token in less than 180 seconds. */
				class updateApiProductAttribute(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGoogleCloudApigeeV1Attribute(body: Schema.GoogleCloudApigeeV1Attribute) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudApigeeV1Attribute])
				}
				object updateApiProductAttribute {
					def apply(organizationsId :PlayApi, apiproductsId :PlayApi, attributesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): updateApiProductAttribute = new updateApiProductAttribute(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/apiproducts/${apiproductsId}/attributes/${attributesId}").addQueryStringParameters("name" -> name.toString))
				}
				/** Lists all API product attributes. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1Attributes]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudApigeeV1Attributes])
				}
				object list {
					def apply(organizationsId :PlayApi, apiproductsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/apiproducts/${apiproductsId}/attributes").addQueryStringParameters("parent" -> parent.toString))
					given Conversion[list, Future[Schema.GoogleCloudApigeeV1Attributes]] = (fun: list) => fun.apply()
				}
				/** Gets the value of an API product attribute. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1Attribute]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudApigeeV1Attribute])
				}
				object get {
					def apply(organizationsId :PlayApi, apiproductsId :PlayApi, attributesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/apiproducts/${apiproductsId}/attributes/${attributesId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleCloudApigeeV1Attribute]] = (fun: get) => fun.apply()
				}
				/** Deletes an API product attribute. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1Attribute]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleCloudApigeeV1Attribute])
				}
				object delete {
					def apply(organizationsId :PlayApi, apiproductsId :PlayApi, attributesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/apiproducts/${apiproductsId}/attributes/${attributesId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.GoogleCloudApigeeV1Attribute]] = (fun: delete) => fun.apply()
				}
			}
			/** Deletes an API product from an organization. Deleting an API product causes app requests to the resource URIs defined in the API product to fail. Ensure that you create a new API product to serve existing apps, unless your intention is to disable access to the resources defined in the API product. The API product name required in the request URL is the internal name of the product, not the display name. While they may be the same, it depends on whether the API product was created via the UI or the API. View the list of API products to verify the internal name. */
			class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1ApiProduct]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleCloudApigeeV1ApiProduct])
			}
			object delete {
				def apply(organizationsId :PlayApi, apiproductsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/apiproducts/${apiproductsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.GoogleCloudApigeeV1ApiProduct]] = (fun: delete) => fun.apply()
			}
			/** Gets configuration details for an API product. The API product name required in the request URL is the internal name of the product, not the display name. While they may be the same, it depends on whether the API product was created via the UI or the API. View the list of API products to verify the internal name. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1ApiProduct]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudApigeeV1ApiProduct])
			}
			object get {
				def apply(organizationsId :PlayApi, apiproductsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/apiproducts/${apiproductsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.GoogleCloudApigeeV1ApiProduct]] = (fun: get) => fun.apply()
			}
			/** Updates an existing API product. You must include all required values, whether or not you are updating them, as well as any optional values that you are updating. The API product name required in the request URL is the internal name of the product, not the display name. While they may be the same, it depends on whether the API product was created via UI or API. View the list of API products to identify their internal names. */
			class update(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withGoogleCloudApigeeV1ApiProduct(body: Schema.GoogleCloudApigeeV1ApiProduct) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.GoogleCloudApigeeV1ApiProduct])
			}
			object update {
				def apply(organizationsId :PlayApi, apiproductsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/apiproducts/${apiproductsId}").addQueryStringParameters("name" -> name.toString))
			}
			/** Lists all API product names for an organization. Filter the list by passing an `attributename` and `attibutevalue`. The maximum number of API products returned is 1000. You can paginate the list of API products returned using the `startKey` and `count` query parameters. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1ListApiProductsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudApigeeV1ListApiProductsResponse])
			}
			object list {
				def apply(organizationsId :PlayApi, startKey: String, attributename: String, attributevalue: String, count: String, expand: Boolean, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/apiproducts").addQueryStringParameters("startKey" -> startKey.toString, "attributename" -> attributename.toString, "attributevalue" -> attributevalue.toString, "count" -> count.toString, "expand" -> expand.toString, "parent" -> parent.toString))
				given Conversion[list, Future[Schema.GoogleCloudApigeeV1ListApiProductsResponse]] = (fun: list) => fun.apply()
			}
			object rateplans {
				/** Create a rate plan that is associated with an API product in an organization. Using rate plans, API product owners can monetize their API products by configuring one or more of the following: - Billing frequency - Initial setup fees for using an API product - Payment funding model (postpaid only) - Fixed recurring or consumption-based charges for using an API product - Revenue sharing with developer partners An API product can have multiple rate plans associated with it but &#42;only one&#42; rate plan can be active at any point of time. &#42;&#42;Note: From the developer's perspective, they purchase API products not rate plans. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGoogleCloudApigeeV1RatePlan(body: Schema.GoogleCloudApigeeV1RatePlan) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudApigeeV1RatePlan])
				}
				object create {
					def apply(organizationsId :PlayApi, apiproductsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/apiproducts/${apiproductsId}/rateplans").addQueryStringParameters("parent" -> parent.toString))
				}
				/** Deletes a rate plan. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1RatePlan]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleCloudApigeeV1RatePlan])
				}
				object delete {
					def apply(organizationsId :PlayApi, apiproductsId :PlayApi, rateplansId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/apiproducts/${apiproductsId}/rateplans/${rateplansId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.GoogleCloudApigeeV1RatePlan]] = (fun: delete) => fun.apply()
				}
				/** Gets the details of a rate plan. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1RatePlan]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudApigeeV1RatePlan])
				}
				object get {
					def apply(organizationsId :PlayApi, apiproductsId :PlayApi, rateplansId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/apiproducts/${apiproductsId}/rateplans/${rateplansId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleCloudApigeeV1RatePlan]] = (fun: get) => fun.apply()
				}
				/** Updates an existing rate plan. */
				class update(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGoogleCloudApigeeV1RatePlan(body: Schema.GoogleCloudApigeeV1RatePlan) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.GoogleCloudApigeeV1RatePlan])
				}
				object update {
					def apply(organizationsId :PlayApi, apiproductsId :PlayApi, rateplansId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/apiproducts/${apiproductsId}/rateplans/${rateplansId}").addQueryStringParameters("name" -> name.toString))
				}
				/** Lists all the rate plans for an API product. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1ListRatePlansResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudApigeeV1ListRatePlansResponse])
				}
				object list {
					def apply(organizationsId :PlayApi, apiproductsId :PlayApi, orderBy: String, startKey: String, expand: Boolean, parent: String, state: String, count: Int)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/apiproducts/${apiproductsId}/rateplans").addQueryStringParameters("orderBy" -> orderBy.toString, "startKey" -> startKey.toString, "expand" -> expand.toString, "parent" -> parent.toString, "state" -> state.toString, "count" -> count.toString))
					given Conversion[list, Future[Schema.GoogleCloudApigeeV1ListRatePlansResponse]] = (fun: list) => fun.apply()
				}
			}
		}
		object hostStats {
			/** Retrieve metrics grouped by dimensions in host level. The types of metrics you can retrieve include traffic, message counts, API call latency, response size, and cache hits and counts. Dimensions let you view metrics in meaningful groups. You can optionally pass dimensions as path parameters to the `stats` API. If dimensions are not specified, the metrics are computed on the entire set of data for the given time range. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1Stats]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudApigeeV1Stats])
			}
			object get {
				def apply(organizationsId :PlayApi, hostStatsId :PlayApi, envgroupHostname: String, timeUnit: String, sort: String, limit: String, realtime: Boolean, offset: String, tzo: String, timeRange: String, sortby: String, select: String, name: String, accuracy: String, filter: String, tsAscending: Boolean, topk: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/hostStats/${hostStatsId}").addQueryStringParameters("envgroupHostname" -> envgroupHostname.toString, "timeUnit" -> timeUnit.toString, "sort" -> sort.toString, "limit" -> limit.toString, "realtime" -> realtime.toString, "offset" -> offset.toString, "tzo" -> tzo.toString, "timeRange" -> timeRange.toString, "sortby" -> sortby.toString, "select" -> select.toString, "name" -> name.toString, "accuracy" -> accuracy.toString, "filter" -> filter.toString, "tsAscending" -> tsAscending.toString, "topk" -> topk.toString))
				given Conversion[get, Future[Schema.GoogleCloudApigeeV1Stats]] = (fun: get) => fun.apply()
			}
		}
		object appgroups {
			/** Creates an AppGroup. Once created, user can register apps under the AppGroup to obtain secret key and password. At creation time, the AppGroup's state is set as `active`. */
			class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withGoogleCloudApigeeV1AppGroup(body: Schema.GoogleCloudApigeeV1AppGroup) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudApigeeV1AppGroup])
			}
			object create {
				def apply(organizationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/appgroups").addQueryStringParameters("parent" -> parent.toString))
			}
			/** Deletes an AppGroup. All app and API keys associations with the AppGroup are also removed. &#42;&#42;Warning&#42;&#42;: This API will permanently delete the AppGroup and related artifacts. &#42;&#42;Note&#42;&#42;: The delete operation is asynchronous. The AppGroup app is deleted immediately, but its associated resources, such as apps and API keys, may take anywhere from a few seconds to a few minutes to be deleted. */
			class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1AppGroup]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleCloudApigeeV1AppGroup])
			}
			object delete {
				def apply(organizationsId :PlayApi, appgroupsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/appgroups/${appgroupsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.GoogleCloudApigeeV1AppGroup]] = (fun: delete) => fun.apply()
			}
			/** Returns the AppGroup details for the provided AppGroup name in the request URI. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1AppGroup]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudApigeeV1AppGroup])
			}
			object get {
				def apply(organizationsId :PlayApi, appgroupsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/appgroups/${appgroupsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.GoogleCloudApigeeV1AppGroup]] = (fun: get) => fun.apply()
			}
			/** Updates an AppGroup. This API replaces the existing AppGroup details with those specified in the request. Include or exclude any existing details that you want to retain or delete, respectively. Note that the state of the AppGroup should be updated using `action`, and not via AppGroup. */
			class update(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withGoogleCloudApigeeV1AppGroup(body: Schema.GoogleCloudApigeeV1AppGroup) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.GoogleCloudApigeeV1AppGroup])
			}
			object update {
				def apply(organizationsId :PlayApi, appgroupsId :PlayApi, action: String, name: String)(using signer: RequestSigner, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/appgroups/${appgroupsId}").addQueryStringParameters("action" -> action.toString, "name" -> name.toString))
			}
			/** Lists all AppGroups in an organization. A maximum of 1000 AppGroups are returned in the response if PageSize is not specified, or if the PageSize is greater than 1000. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1ListAppGroupsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudApigeeV1ListAppGroupsResponse])
			}
			object list {
				def apply(organizationsId :PlayApi, parent: String, pageSize: Int, filter: String, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/appgroups").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "filter" -> filter.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.GoogleCloudApigeeV1ListAppGroupsResponse]] = (fun: list) => fun.apply()
			}
			object apps {
				/** Creates an app and associates it with an AppGroup. This API associates the AppGroup app with the specified API product and auto-generates an API key for the app to use in calls to API proxies inside that API product. The `name` is the unique ID of the app that you can use in API calls. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGoogleCloudApigeeV1AppGroupApp(body: Schema.GoogleCloudApigeeV1AppGroupApp) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudApigeeV1AppGroupApp])
				}
				object create {
					def apply(organizationsId :PlayApi, appgroupsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/appgroups/${appgroupsId}/apps").addQueryStringParameters("parent" -> parent.toString))
				}
				/** Deletes an AppGroup app. &#42;&#42;Note&#42;&#42;: The delete operation is asynchronous. The AppGroup app is deleted immediately, but its associated resources, such as app keys or access tokens, may take anywhere from a few seconds to a few minutes to be deleted. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1AppGroupApp]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleCloudApigeeV1AppGroupApp])
				}
				object delete {
					def apply(organizationsId :PlayApi, appgroupsId :PlayApi, appsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/appgroups/${appgroupsId}/apps/${appsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.GoogleCloudApigeeV1AppGroupApp]] = (fun: delete) => fun.apply()
				}
				/** Returns the details for an AppGroup app. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1AppGroupApp]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudApigeeV1AppGroupApp])
				}
				object get {
					def apply(organizationsId :PlayApi, appgroupsId :PlayApi, appsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/appgroups/${appgroupsId}/apps/${appsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleCloudApigeeV1AppGroupApp]] = (fun: get) => fun.apply()
				}
				/** Updates the details for an AppGroup app. In addition, you can add an API product to an AppGroup app and automatically generate an API key for the app to use when calling APIs in the API product. If you want to use an existing API key for the API product, add the API product to the API key using the UpdateAppGroupAppKey API. Using this API, you cannot update the app name, as it is the primary key used to identify the app and cannot be changed. This API replaces the existing attributes with those specified in the request. Include or exclude any existing attributes that you want to retain or delete, respectively. */
				class update(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGoogleCloudApigeeV1AppGroupApp(body: Schema.GoogleCloudApigeeV1AppGroupApp) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.GoogleCloudApigeeV1AppGroupApp])
				}
				object update {
					def apply(organizationsId :PlayApi, appgroupsId :PlayApi, appsId :PlayApi, name: String, action: String)(using signer: RequestSigner, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/appgroups/${appgroupsId}/apps/${appsId}").addQueryStringParameters("name" -> name.toString, "action" -> action.toString))
				}
				/** Lists all apps created by an AppGroup in an Apigee organization. Optionally, you can request an expanded view of the AppGroup apps. Lists all AppGroupApps in an AppGroup. A maximum of 1000 AppGroup apps are returned in the response if PageSize is not specified, or if the PageSize is greater than 1000. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1ListAppGroupAppsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Optional. Page token. If provides, must be a valid AppGroup app returned from a previous call that can be used to retrieve the next page. */
					def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
					/** Optional. Maximum number entries to return. If unspecified, at most 1000 entries will be returned.<br>Format: int32 */
					def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudApigeeV1ListAppGroupAppsResponse])
				}
				object list {
					def apply(organizationsId :PlayApi, appgroupsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/appgroups/${appgroupsId}/apps").addQueryStringParameters("parent" -> parent.toString))
					given Conversion[list, Future[Schema.GoogleCloudApigeeV1ListAppGroupAppsResponse]] = (fun: list) => fun.apply()
				}
				object keys {
					/** Adds an API product to an AppGroupAppKey, enabling the app that holds the key to access the API resources bundled in the API product. In addition, you can add attributes to the AppGroupAppKey. This API replaces the existing attributes with those specified in the request. Include or exclude any existing attributes that you want to retain or delete, respectively. You can use the same key to access all API products associated with the app. */
					class updateAppGroupAppKey(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withGoogleCloudApigeeV1UpdateAppGroupAppKeyRequest(body: Schema.GoogleCloudApigeeV1UpdateAppGroupAppKeyRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudApigeeV1AppGroupAppKey])
					}
					object updateAppGroupAppKey {
						def apply(organizationsId :PlayApi, appgroupsId :PlayApi, appsId :PlayApi, keysId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): updateAppGroupAppKey = new updateAppGroupAppKey(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/appgroups/${appgroupsId}/apps/${appsId}/keys/${keysId}").addQueryStringParameters("name" -> name.toString))
					}
					/** Creates a custom consumer key and secret for a AppGroup app. This is particularly useful if you want to migrate existing consumer keys and secrets to Apigee from another system. Consumer keys and secrets can contain letters, numbers, underscores, and hyphens. No other special characters are allowed. To avoid service disruptions, a consumer key and secret should not exceed 2 KBs each. &#42;&#42;Note&#42;&#42;: When creating the consumer key and secret, an association to API products will not be made. Therefore, you should not specify the associated API products in your request. Instead, use the ProductizeAppGroupAppKey API to make the association after the consumer key and secret are created. If a consumer key and secret already exist, you can keep them or delete them using the DeleteAppGroupAppKey API. */
					class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withGoogleCloudApigeeV1AppGroupAppKey(body: Schema.GoogleCloudApigeeV1AppGroupAppKey) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudApigeeV1AppGroupAppKey])
					}
					object create {
						def apply(organizationsId :PlayApi, appgroupsId :PlayApi, appsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/appgroups/${appgroupsId}/apps/${appsId}/keys").addQueryStringParameters("parent" -> parent.toString))
					}
					/** Deletes an app's consumer key and removes all API products associated with the app. After the consumer key is deleted, it cannot be used to access any APIs. */
					class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1AppGroupAppKey]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleCloudApigeeV1AppGroupAppKey])
					}
					object delete {
						def apply(organizationsId :PlayApi, appgroupsId :PlayApi, appsId :PlayApi, keysId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/appgroups/${appgroupsId}/apps/${appsId}/keys/${keysId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[delete, Future[Schema.GoogleCloudApigeeV1AppGroupAppKey]] = (fun: delete) => fun.apply()
					}
					/** Gets details for a consumer key for a AppGroup app, including the key and secret value, associated API products, and other information. */
					class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1AppGroupAppKey]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudApigeeV1AppGroupAppKey])
					}
					object get {
						def apply(organizationsId :PlayApi, appgroupsId :PlayApi, appsId :PlayApi, keysId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/appgroups/${appgroupsId}/apps/${appsId}/keys/${keysId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.GoogleCloudApigeeV1AppGroupAppKey]] = (fun: get) => fun.apply()
					}
					object apiproducts {
						/** Removes an API product from an app's consumer key. After the API product is removed, the app cannot access the API resources defined in that API product. &#42;&#42;Note&#42;&#42;: The consumer key is not removed, only its association with the API product. */
						class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1AppGroupAppKey]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleCloudApigeeV1AppGroupAppKey])
						}
						object delete {
							def apply(organizationsId :PlayApi, appgroupsId :PlayApi, appsId :PlayApi, keysId :PlayApi, apiproductsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/appgroups/${appgroupsId}/apps/${appsId}/keys/${keysId}/apiproducts/${apiproductsId}").addQueryStringParameters("name" -> name.toString))
							given Conversion[delete, Future[Schema.GoogleCloudApigeeV1AppGroupAppKey]] = (fun: delete) => fun.apply()
						}
						/** Approves or revokes the consumer key for an API product. After a consumer key is approved, the app can use it to access APIs. A consumer key that is revoked or pending cannot be used to access an API. Any access tokens associated with a revoked consumer key will remain active. However, Apigee checks the status of the consumer key and if set to `revoked` will not allow access to the API. */
						class updateAppGroupAppKeyApiProduct(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("POST")).map(_.json.as[Schema.GoogleProtobufEmpty])
						}
						object updateAppGroupAppKeyApiProduct {
							def apply(organizationsId :PlayApi, appgroupsId :PlayApi, appsId :PlayApi, keysId :PlayApi, apiproductsId :PlayApi, name: String, action: String)(using signer: RequestSigner, ec: ExecutionContext): updateAppGroupAppKeyApiProduct = new updateAppGroupAppKeyApiProduct(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/appgroups/${appgroupsId}/apps/${appsId}/keys/${keysId}/apiproducts/${apiproductsId}").addQueryStringParameters("name" -> name.toString, "action" -> action.toString))
							given Conversion[updateAppGroupAppKeyApiProduct, Future[Schema.GoogleProtobufEmpty]] = (fun: updateAppGroupAppKeyApiProduct) => fun.apply()
						}
					}
				}
			}
		}
		object keyvaluemaps {
			/** Deletes a key value map from an organization. */
			class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1KeyValueMap]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleCloudApigeeV1KeyValueMap])
			}
			object delete {
				def apply(organizationsId :PlayApi, keyvaluemapsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/keyvaluemaps/${keyvaluemapsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.GoogleCloudApigeeV1KeyValueMap]] = (fun: delete) => fun.apply()
			}
			/** Creates a key value map in an organization. */
			class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withGoogleCloudApigeeV1KeyValueMap(body: Schema.GoogleCloudApigeeV1KeyValueMap) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudApigeeV1KeyValueMap])
			}
			object create {
				def apply(organizationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/keyvaluemaps").addQueryStringParameters("parent" -> parent.toString))
			}
			object entries {
				/** Creates key value entries in a key value map scoped to an organization, environment, or API proxy. &#42;&#42;Note&#42;&#42;: Supported for Apigee hybrid 1.8.x and higher. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGoogleCloudApigeeV1KeyValueEntry(body: Schema.GoogleCloudApigeeV1KeyValueEntry) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudApigeeV1KeyValueEntry])
				}
				object create {
					def apply(organizationsId :PlayApi, keyvaluemapsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/keyvaluemaps/${keyvaluemapsId}/entries").addQueryStringParameters("parent" -> parent.toString))
				}
				/** Deletes a key value entry from a key value map scoped to an organization, environment, or API proxy. &#42;&#42;Notes:&#42;&#42; &#42; After you delete the key value entry, the policy consuming the entry will continue to function with its cached values for a few minutes. This is expected behavior. &#42; Supported for Apigee hybrid 1.8.x and higher. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1KeyValueEntry]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleCloudApigeeV1KeyValueEntry])
				}
				object delete {
					def apply(organizationsId :PlayApi, keyvaluemapsId :PlayApi, entriesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/keyvaluemaps/${keyvaluemapsId}/entries/${entriesId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.GoogleCloudApigeeV1KeyValueEntry]] = (fun: delete) => fun.apply()
				}
				/** Get the key value entry value for a key value map scoped to an organization, environment, or API proxy. &#42;&#42;Note&#42;&#42;: Supported for Apigee hybrid 1.8.x and higher. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1KeyValueEntry]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudApigeeV1KeyValueEntry])
				}
				object get {
					def apply(organizationsId :PlayApi, keyvaluemapsId :PlayApi, entriesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/keyvaluemaps/${keyvaluemapsId}/entries/${entriesId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleCloudApigeeV1KeyValueEntry]] = (fun: get) => fun.apply()
				}
				/** Update key value entry scoped to an organization, environment, or API proxy for an existing key. */
				class update(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGoogleCloudApigeeV1KeyValueEntry(body: Schema.GoogleCloudApigeeV1KeyValueEntry) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.GoogleCloudApigeeV1KeyValueEntry])
				}
				object update {
					def apply(organizationsId :PlayApi, keyvaluemapsId :PlayApi, entriesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/keyvaluemaps/${keyvaluemapsId}/entries/${entriesId}").addQueryStringParameters("name" -> name.toString))
				}
				/** Lists key value entries for key values maps scoped to an organization, environment, or API proxy. &#42;&#42;Note&#42;&#42;: Supported for Apigee hybrid 1.8.x and higher. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1ListKeyValueEntriesResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Optional. Maximum number of key value entries to return. If unspecified, at most 100 entries will be returned.<br>Format: int32 */
					def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
					/** Optional. Page token. If provides, must be a valid key value entry returned from a previous call that can be used to retrieve the next page. */
					def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudApigeeV1ListKeyValueEntriesResponse])
				}
				object list {
					def apply(organizationsId :PlayApi, keyvaluemapsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/keyvaluemaps/${keyvaluemapsId}/entries").addQueryStringParameters("parent" -> parent.toString))
					given Conversion[list, Future[Schema.GoogleCloudApigeeV1ListKeyValueEntriesResponse]] = (fun: list) => fun.apply()
				}
			}
		}
		object datacollectors {
			/** Creates a new data collector. */
			class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withGoogleCloudApigeeV1DataCollector(body: Schema.GoogleCloudApigeeV1DataCollector) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudApigeeV1DataCollector])
			}
			object create {
				def apply(organizationsId :PlayApi, parent: String, dataCollectorId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/datacollectors").addQueryStringParameters("parent" -> parent.toString, "dataCollectorId" -> dataCollectorId.toString))
			}
			/** Deletes a data collector. */
			class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
			}
			object delete {
				def apply(organizationsId :PlayApi, datacollectorsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/datacollectors/${datacollectorsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
			}
			/** Gets a data collector. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1DataCollector]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudApigeeV1DataCollector])
			}
			object get {
				def apply(organizationsId :PlayApi, datacollectorsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/datacollectors/${datacollectorsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.GoogleCloudApigeeV1DataCollector]] = (fun: get) => fun.apply()
			}
			/** Updates a data collector. */
			class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withGoogleCloudApigeeV1DataCollector(body: Schema.GoogleCloudApigeeV1DataCollector) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudApigeeV1DataCollector])
			}
			object patch {
				def apply(organizationsId :PlayApi, datacollectorsId :PlayApi, updateMask: String, name: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/datacollectors/${datacollectorsId}").addQueryStringParameters("updateMask" -> updateMask.toString, "name" -> name.toString))
			}
			/** Lists all data collectors. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1ListDataCollectorsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudApigeeV1ListDataCollectorsResponse])
			}
			object list {
				def apply(organizationsId :PlayApi, parent: String, pageToken: String, pageSize: Int)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/datacollectors").addQueryStringParameters("parent" -> parent.toString, "pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString))
				given Conversion[list, Future[Schema.GoogleCloudApigeeV1ListDataCollectorsResponse]] = (fun: list) => fun.apply()
			}
		}
		object securityProfilesV2 {
			/** Create a security profile v2. */
			class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withGoogleCloudApigeeV1SecurityProfileV2(body: Schema.GoogleCloudApigeeV1SecurityProfileV2) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudApigeeV1SecurityProfileV2])
			}
			object create {
				def apply(organizationsId :PlayApi, securityProfileV2Id: String, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/securityProfilesV2").addQueryStringParameters("securityProfileV2Id" -> securityProfileV2Id.toString, "parent" -> parent.toString))
			}
			/** Delete a security profile v2. */
			class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
			}
			object delete {
				def apply(organizationsId :PlayApi, securityProfilesV2Id :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/securityProfilesV2/${securityProfilesV2Id}").addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
			}
			/** Get a security profile v2. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1SecurityProfileV2]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudApigeeV1SecurityProfileV2])
			}
			object get {
				def apply(organizationsId :PlayApi, securityProfilesV2Id :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/securityProfilesV2/${securityProfilesV2Id}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.GoogleCloudApigeeV1SecurityProfileV2]] = (fun: get) => fun.apply()
			}
			/** Update a security profile V2. */
			class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Optional. The list of fields to update. Valid fields to update are `description` and `profileAssessmentConfigs`.<br>Format: google-fieldmask */
				def withUpdateMask(updateMask: String) = new patch(req.addQueryStringParameters("updateMask" -> updateMask.toString))
				/** Perform the request */
				def withGoogleCloudApigeeV1SecurityProfileV2(body: Schema.GoogleCloudApigeeV1SecurityProfileV2) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudApigeeV1SecurityProfileV2])
			}
			object patch {
				def apply(organizationsId :PlayApi, securityProfilesV2Id :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/securityProfilesV2/${securityProfilesV2Id}").addQueryStringParameters("name" -> name.toString))
			}
			/** List security profiles v2. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1ListSecurityProfilesV2Response]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Optional. The maximum number of profiles to return<br>Format: int32 */
				def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
				/** Optional. A page token, received from a previous `ListSecurityProfilesV2` call. Provide this to retrieve the subsequent page. */
				def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudApigeeV1ListSecurityProfilesV2Response])
			}
			object list {
				def apply(organizationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/securityProfilesV2").addQueryStringParameters("parent" -> parent.toString))
				given Conversion[list, Future[Schema.GoogleCloudApigeeV1ListSecurityProfilesV2Response]] = (fun: list) => fun.apply()
			}
		}
		object optimizedHostStats {
			/** Similar to GetHostStats except that the response is less verbose. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1OptimizedStats]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudApigeeV1OptimizedStats])
			}
			object get {
				def apply(organizationsId :PlayApi, optimizedHostStatsId :PlayApi, envgroupHostname: String, accuracy: String, name: String, topk: String, tsAscending: Boolean, offset: String, filter: String, timeRange: String, tzo: String, sort: String, timeUnit: String, limit: String, select: String, sortby: String, realtime: Boolean)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/optimizedHostStats/${optimizedHostStatsId}").addQueryStringParameters("envgroupHostname" -> envgroupHostname.toString, "accuracy" -> accuracy.toString, "name" -> name.toString, "topk" -> topk.toString, "tsAscending" -> tsAscending.toString, "offset" -> offset.toString, "filter" -> filter.toString, "timeRange" -> timeRange.toString, "tzo" -> tzo.toString, "sort" -> sort.toString, "timeUnit" -> timeUnit.toString, "limit" -> limit.toString, "select" -> select.toString, "sortby" -> sortby.toString, "realtime" -> realtime.toString))
				given Conversion[get, Future[Schema.GoogleCloudApigeeV1OptimizedStats]] = (fun: get) => fun.apply()
			}
		}
		object instances {
			/** Creates an Apigee runtime instance. The instance is accessible from the authorized network configured on the organization. &#42;&#42;Note:&#42;&#42; Not supported for Apigee hybrid. */
			class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withGoogleCloudApigeeV1Instance(body: Schema.GoogleCloudApigeeV1Instance) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
			}
			object create {
				def apply(organizationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/instances").addQueryStringParameters("parent" -> parent.toString))
			}
			/** Reports the latest status for a runtime instance. */
			class reportStatus(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withGoogleCloudApigeeV1ReportInstanceStatusRequest(body: Schema.GoogleCloudApigeeV1ReportInstanceStatusRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudApigeeV1ReportInstanceStatusResponse])
			}
			object reportStatus {
				def apply(organizationsId :PlayApi, instancesId :PlayApi, instance: String)(using signer: RequestSigner, ec: ExecutionContext): reportStatus = new reportStatus(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/instances/${instancesId}:reportStatus").addQueryStringParameters("instance" -> instance.toString))
			}
			/** Deletes an Apigee runtime instance. The instance stops serving requests and the runtime data is deleted. &#42;&#42;Note:&#42;&#42; Not supported for Apigee hybrid. */
			class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningOperation]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleLongrunningOperation])
			}
			object delete {
				def apply(organizationsId :PlayApi, instancesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/instances/${instancesId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.GoogleLongrunningOperation]] = (fun: delete) => fun.apply()
			}
			/** Gets the details for an Apigee runtime instance. &#42;&#42;Note:&#42;&#42; Not supported for Apigee hybrid. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1Instance]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudApigeeV1Instance])
			}
			object get {
				def apply(organizationsId :PlayApi, instancesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/instances/${instancesId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.GoogleCloudApigeeV1Instance]] = (fun: get) => fun.apply()
			}
			/** Lists all Apigee runtime instances for the organization. &#42;&#42;Note:&#42;&#42; Not supported for Apigee hybrid. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1ListInstancesResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudApigeeV1ListInstancesResponse])
			}
			object list {
				def apply(organizationsId :PlayApi, pageToken: String, pageSize: Int, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/instances").addQueryStringParameters("pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString, "parent" -> parent.toString))
				given Conversion[list, Future[Schema.GoogleCloudApigeeV1ListInstancesResponse]] = (fun: list) => fun.apply()
			}
			/** Updates an Apigee runtime instance. You can update the fields described in NodeConfig. No other fields will be updated. &#42;&#42;Note:&#42;&#42; Not supported for Apigee hybrid. */
			class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withGoogleCloudApigeeV1Instance(body: Schema.GoogleCloudApigeeV1Instance) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleLongrunningOperation])
			}
			object patch {
				def apply(organizationsId :PlayApi, instancesId :PlayApi, updateMask: String, name: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/instances/${instancesId}").addQueryStringParameters("updateMask" -> updateMask.toString, "name" -> name.toString))
			}
			object attachments {
				/** Lists all attachments to an instance. &#42;&#42;Note:&#42;&#42; Not supported for Apigee hybrid. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1ListInstanceAttachmentsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudApigeeV1ListInstanceAttachmentsResponse])
				}
				object list {
					def apply(organizationsId :PlayApi, instancesId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/instances/${instancesId}/attachments").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.GoogleCloudApigeeV1ListInstanceAttachmentsResponse]] = (fun: list) => fun.apply()
				}
				/** Gets an attachment. &#42;&#42;Note:&#42;&#42; Not supported for Apigee hybrid. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1InstanceAttachment]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudApigeeV1InstanceAttachment])
				}
				object get {
					def apply(organizationsId :PlayApi, instancesId :PlayApi, attachmentsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/instances/${instancesId}/attachments/${attachmentsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleCloudApigeeV1InstanceAttachment]] = (fun: get) => fun.apply()
				}
				/** Creates a new attachment of an environment to an instance. &#42;&#42;Note:&#42;&#42; Not supported for Apigee hybrid. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGoogleCloudApigeeV1InstanceAttachment(body: Schema.GoogleCloudApigeeV1InstanceAttachment) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
				}
				object create {
					def apply(organizationsId :PlayApi, instancesId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/instances/${instancesId}/attachments").addQueryStringParameters("parent" -> parent.toString))
				}
				/** Deletes an attachment. &#42;&#42;Note:&#42;&#42; Not supported for Apigee hybrid. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningOperation]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleLongrunningOperation])
				}
				object delete {
					def apply(organizationsId :PlayApi, instancesId :PlayApi, attachmentsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/instances/${instancesId}/attachments/${attachmentsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.GoogleLongrunningOperation]] = (fun: delete) => fun.apply()
				}
			}
			object natAddresses {
				/** Creates a NAT address. The address is created in the RESERVED state and a static external IP address will be provisioned. At this time, the instance will not use this IP address for Internet egress traffic. The address can be activated for use once any required firewall IP whitelisting has been completed. &#42;&#42;Note:&#42;&#42; Not supported for Apigee hybrid. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGoogleCloudApigeeV1NatAddress(body: Schema.GoogleCloudApigeeV1NatAddress) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
				}
				object create {
					def apply(organizationsId :PlayApi, instancesId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/instances/${instancesId}/natAddresses").addQueryStringParameters("parent" -> parent.toString))
				}
				/** Deletes the NAT address. Connections that are actively using the address are drained before it is removed. &#42;&#42;Note:&#42;&#42; Not supported for Apigee hybrid. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningOperation]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleLongrunningOperation])
				}
				object delete {
					def apply(organizationsId :PlayApi, instancesId :PlayApi, natAddressesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/instances/${instancesId}/natAddresses/${natAddressesId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.GoogleLongrunningOperation]] = (fun: delete) => fun.apply()
				}
				/** Gets the details of a NAT address. &#42;&#42;Note:&#42;&#42; Not supported for Apigee hybrid. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1NatAddress]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudApigeeV1NatAddress])
				}
				object get {
					def apply(organizationsId :PlayApi, instancesId :PlayApi, natAddressesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/instances/${instancesId}/natAddresses/${natAddressesId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleCloudApigeeV1NatAddress]] = (fun: get) => fun.apply()
				}
				/** Activates the NAT address. The Apigee instance can now use this for Internet egress traffic. &#42;&#42;Note:&#42;&#42; Not supported for Apigee hybrid. */
				class activate(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGoogleCloudApigeeV1ActivateNatAddressRequest(body: Schema.GoogleCloudApigeeV1ActivateNatAddressRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
				}
				object activate {
					def apply(organizationsId :PlayApi, instancesId :PlayApi, natAddressesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): activate = new activate(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/instances/${instancesId}/natAddresses/${natAddressesId}:activate").addQueryStringParameters("name" -> name.toString))
				}
				/** Lists the NAT addresses for an Apigee instance. &#42;&#42;Note:&#42;&#42; Not supported for Apigee hybrid. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1ListNatAddressesResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudApigeeV1ListNatAddressesResponse])
				}
				object list {
					def apply(organizationsId :PlayApi, instancesId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/instances/${instancesId}/natAddresses").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.GoogleCloudApigeeV1ListNatAddressesResponse]] = (fun: list) => fun.apply()
				}
			}
			object canaryevaluations {
				/** Gets a CanaryEvaluation for an organization. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1CanaryEvaluation]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudApigeeV1CanaryEvaluation])
				}
				object get {
					def apply(organizationsId :PlayApi, instancesId :PlayApi, canaryevaluationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/instances/${instancesId}/canaryevaluations/${canaryevaluationsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleCloudApigeeV1CanaryEvaluation]] = (fun: get) => fun.apply()
				}
				/** Creates a new canary evaluation for an organization. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGoogleCloudApigeeV1CanaryEvaluation(body: Schema.GoogleCloudApigeeV1CanaryEvaluation) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
				}
				object create {
					def apply(organizationsId :PlayApi, instancesId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/instances/${instancesId}/canaryevaluations").addQueryStringParameters("parent" -> parent.toString))
				}
			}
		}
		object sharedflows {
			/** Uploads a ZIP-formatted shared flow configuration bundle to an organization. If the shared flow already exists, this creates a new revision of it. If the shared flow does not exist, this creates it. Once imported, the shared flow revision must be deployed before it can be accessed at runtime. The size limit of a shared flow bundle is 15 MB. */
			class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withGoogleApiHttpBody(body: Schema.GoogleApiHttpBody) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudApigeeV1SharedFlowRevision])
			}
			object create {
				def apply(organizationsId :PlayApi, name: String, parent: String, action: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/sharedflows").addQueryStringParameters("name" -> name.toString, "parent" -> parent.toString, "action" -> action.toString))
			}
			/** Deletes a shared flow and all it's revisions. The shared flow must be undeployed before you can delete it. */
			class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1SharedFlow]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleCloudApigeeV1SharedFlow])
			}
			object delete {
				def apply(organizationsId :PlayApi, sharedflowsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/sharedflows/${sharedflowsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.GoogleCloudApigeeV1SharedFlow]] = (fun: delete) => fun.apply()
			}
			/** Gets a shared flow by name, including a list of its revisions. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1SharedFlow]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudApigeeV1SharedFlow])
			}
			object get {
				def apply(organizationsId :PlayApi, sharedflowsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/sharedflows/${sharedflowsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.GoogleCloudApigeeV1SharedFlow]] = (fun: get) => fun.apply()
			}
			/** Lists all shared flows in the organization. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1ListSharedFlowsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudApigeeV1ListSharedFlowsResponse])
			}
			object list {
				def apply(organizationsId :PlayApi, includeMetaData: Boolean, parent: String, includeRevisions: Boolean)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/sharedflows").addQueryStringParameters("includeMetaData" -> includeMetaData.toString, "parent" -> parent.toString, "includeRevisions" -> includeRevisions.toString))
				given Conversion[list, Future[Schema.GoogleCloudApigeeV1ListSharedFlowsResponse]] = (fun: list) => fun.apply()
			}
			object deployments {
				/** Lists all deployments of a shared flow. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1ListDeploymentsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudApigeeV1ListDeploymentsResponse])
				}
				object list {
					def apply(organizationsId :PlayApi, sharedflowsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/sharedflows/${sharedflowsId}/deployments").addQueryStringParameters("parent" -> parent.toString))
					given Conversion[list, Future[Schema.GoogleCloudApigeeV1ListDeploymentsResponse]] = (fun: list) => fun.apply()
				}
			}
			object revisions {
				/** Updates a shared flow revision. This operation is only allowed on revisions which have never been deployed. After deployment a revision becomes immutable, even if it becomes undeployed. The payload is a ZIP-formatted shared flow. Content type must be either multipart/form-data or application/octet-stream. */
				class updateSharedFlowRevision(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGoogleApiHttpBody(body: Schema.GoogleApiHttpBody) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudApigeeV1SharedFlowRevision])
				}
				object updateSharedFlowRevision {
					def apply(organizationsId :PlayApi, sharedflowsId :PlayApi, revisionsId :PlayApi, validate: Boolean, name: String)(using signer: RequestSigner, ec: ExecutionContext): updateSharedFlowRevision = new updateSharedFlowRevision(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/sharedflows/${sharedflowsId}/revisions/${revisionsId}").addQueryStringParameters("validate" -> validate.toString, "name" -> name.toString))
				}
				/** Gets a revision of a shared flow. To download the shared flow configuration bundle for the specified revision as a zip file, set the `format` query parameter to `bundle`. If you are using curl, specify `-o filename.zip` to save the output to a file; otherwise, it displays to `stdout`. Then, develop the shared flow configuration locally and upload the updated sharedFlow configuration revision, as described in [updateSharedFlowRevision](updateSharedFlowRevision). */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleApiHttpBody]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleApiHttpBody])
				}
				object get {
					def apply(organizationsId :PlayApi, sharedflowsId :PlayApi, revisionsId :PlayApi, format: String, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/sharedflows/${sharedflowsId}/revisions/${revisionsId}").addQueryStringParameters("format" -> format.toString, "name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleApiHttpBody]] = (fun: get) => fun.apply()
				}
				/** Deletes a shared flow and all associated policies, resources, and revisions. You must undeploy the shared flow before deleting it. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1SharedFlowRevision]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleCloudApigeeV1SharedFlowRevision])
				}
				object delete {
					def apply(organizationsId :PlayApi, sharedflowsId :PlayApi, revisionsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/sharedflows/${sharedflowsId}/revisions/${revisionsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.GoogleCloudApigeeV1SharedFlowRevision]] = (fun: delete) => fun.apply()
				}
				object deployments {
					/** Lists all deployments of a shared flow revision. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1ListDeploymentsResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudApigeeV1ListDeploymentsResponse])
					}
					object list {
						def apply(organizationsId :PlayApi, sharedflowsId :PlayApi, revisionsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/sharedflows/${sharedflowsId}/revisions/${revisionsId}/deployments").addQueryStringParameters("parent" -> parent.toString))
						given Conversion[list, Future[Schema.GoogleCloudApigeeV1ListDeploymentsResponse]] = (fun: list) => fun.apply()
					}
				}
			}
		}
		object reports {
			/** Creates a Custom Report for an Organization. A Custom Report provides Apigee Customers to create custom dashboards in addition to the standard dashboards which are provided. The Custom Report in its simplest form contains specifications about metrics, dimensions and filters. It is important to note that the custom report by itself does not provide an executable entity. The Edge UI converts the custom report definition into an analytics query and displays the result in a chart. */
			class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withGoogleCloudApigeeV1CustomReport(body: Schema.GoogleCloudApigeeV1CustomReport) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudApigeeV1CustomReport])
			}
			object create {
				def apply(organizationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/reports").addQueryStringParameters("parent" -> parent.toString))
			}
			/** Deletes an existing custom report definition */
			class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1DeleteCustomReportResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleCloudApigeeV1DeleteCustomReportResponse])
			}
			object delete {
				def apply(organizationsId :PlayApi, reportsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/reports/${reportsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.GoogleCloudApigeeV1DeleteCustomReportResponse]] = (fun: delete) => fun.apply()
			}
			/** Retrieve a custom report definition. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1CustomReport]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudApigeeV1CustomReport])
			}
			object get {
				def apply(organizationsId :PlayApi, reportsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/reports/${reportsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.GoogleCloudApigeeV1CustomReport]] = (fun: get) => fun.apply()
			}
			/** Update an existing custom report definition */
			class update(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withGoogleCloudApigeeV1CustomReport(body: Schema.GoogleCloudApigeeV1CustomReport) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.GoogleCloudApigeeV1CustomReport])
			}
			object update {
				def apply(organizationsId :PlayApi, reportsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/reports/${reportsId}").addQueryStringParameters("name" -> name.toString))
			}
			/** Return a list of Custom Reports */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1ListCustomReportsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudApigeeV1ListCustomReportsResponse])
			}
			object list {
				def apply(organizationsId :PlayApi, parent: String, expand: Boolean)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/reports").addQueryStringParameters("parent" -> parent.toString, "expand" -> expand.toString))
				given Conversion[list, Future[Schema.GoogleCloudApigeeV1ListCustomReportsResponse]] = (fun: list) => fun.apply()
			}
		}
		object analytics {
			object datastores {
				/** Test if Datastore configuration is correct. This includes checking if credentials provided by customer have required permissions in target destination storage */
				class test(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGoogleCloudApigeeV1Datastore(body: Schema.GoogleCloudApigeeV1Datastore) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudApigeeV1TestDatastoreResponse])
				}
				object test {
					def apply(organizationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): test = new test(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/analytics/datastores:test").addQueryStringParameters("parent" -> parent.toString))
				}
				/** Create a Datastore for an org */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGoogleCloudApigeeV1Datastore(body: Schema.GoogleCloudApigeeV1Datastore) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudApigeeV1Datastore])
				}
				object create {
					def apply(organizationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/analytics/datastores").addQueryStringParameters("parent" -> parent.toString))
				}
				/** Delete a Datastore from an org. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
				}
				object delete {
					def apply(organizationsId :PlayApi, datastoresId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/analytics/datastores/${datastoresId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
				}
				/** Get a Datastore */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1Datastore]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudApigeeV1Datastore])
				}
				object get {
					def apply(organizationsId :PlayApi, datastoresId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/analytics/datastores/${datastoresId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleCloudApigeeV1Datastore]] = (fun: get) => fun.apply()
				}
				/** Update a Datastore */
				class update(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGoogleCloudApigeeV1Datastore(body: Schema.GoogleCloudApigeeV1Datastore) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.GoogleCloudApigeeV1Datastore])
				}
				object update {
					def apply(organizationsId :PlayApi, datastoresId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/analytics/datastores/${datastoresId}").addQueryStringParameters("name" -> name.toString))
				}
				/** List Datastores */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1ListDatastoresResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Optional. TargetType is used to fetch all Datastores that match the type */
					def withTargetType(targetType: String) = new list(req.addQueryStringParameters("targetType" -> targetType.toString))
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudApigeeV1ListDatastoresResponse])
				}
				object list {
					def apply(organizationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/analytics/datastores").addQueryStringParameters("parent" -> parent.toString))
					given Conversion[list, Future[Schema.GoogleCloudApigeeV1ListDatastoresResponse]] = (fun: list) => fun.apply()
				}
			}
		}
		object securityAssessmentResults {
			/** Compute RAV2 security scores for a set of resources. */
			class batchCompute(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withGoogleCloudApigeeV1BatchComputeSecurityAssessmentResultsRequest(body: Schema.GoogleCloudApigeeV1BatchComputeSecurityAssessmentResultsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudApigeeV1BatchComputeSecurityAssessmentResultsResponse])
			}
			object batchCompute {
				def apply(organizationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): batchCompute = new batchCompute(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/securityAssessmentResults:batchCompute").addQueryStringParameters("name" -> name.toString))
			}
		}
		object envgroups {
			/** Creates a new environment group. */
			class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Optional. ID of the environment group. Overrides any ID in the environment_group resource. */
				def withName(name: String) = new create(req.addQueryStringParameters("name" -> name.toString))
				/** Perform the request */
				def withGoogleCloudApigeeV1EnvironmentGroup(body: Schema.GoogleCloudApigeeV1EnvironmentGroup) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
			}
			object create {
				def apply(organizationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/envgroups").addQueryStringParameters("parent" -> parent.toString))
			}
			/** Gets the deployed ingress configuration for an environment group. */
			class getDeployedIngressConfig(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1EnvironmentGroupConfig]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudApigeeV1EnvironmentGroupConfig])
			}
			object getDeployedIngressConfig {
				def apply(organizationsId :PlayApi, envgroupsId :PlayApi, view: String, name: String)(using signer: RequestSigner, ec: ExecutionContext): getDeployedIngressConfig = new getDeployedIngressConfig(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/envgroups/${envgroupsId}/deployedIngressConfig").addQueryStringParameters("view" -> view.toString, "name" -> name.toString))
				given Conversion[getDeployedIngressConfig, Future[Schema.GoogleCloudApigeeV1EnvironmentGroupConfig]] = (fun: getDeployedIngressConfig) => fun.apply()
			}
			/** Deletes an environment group. */
			class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningOperation]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleLongrunningOperation])
			}
			object delete {
				def apply(organizationsId :PlayApi, envgroupsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/envgroups/${envgroupsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.GoogleLongrunningOperation]] = (fun: delete) => fun.apply()
			}
			/** Gets an environment group. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1EnvironmentGroup]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudApigeeV1EnvironmentGroup])
			}
			object get {
				def apply(organizationsId :PlayApi, envgroupsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/envgroups/${envgroupsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.GoogleCloudApigeeV1EnvironmentGroup]] = (fun: get) => fun.apply()
			}
			/** Updates an environment group. */
			class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Optional. List of fields to be updated.<br>Format: google-fieldmask */
				def withUpdateMask(updateMask: String) = new patch(req.addQueryStringParameters("updateMask" -> updateMask.toString))
				/** Perform the request */
				def withGoogleCloudApigeeV1EnvironmentGroup(body: Schema.GoogleCloudApigeeV1EnvironmentGroup) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleLongrunningOperation])
			}
			object patch {
				def apply(organizationsId :PlayApi, envgroupsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/envgroups/${envgroupsId}").addQueryStringParameters("name" -> name.toString))
			}
			/** Lists all environment groups. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1ListEnvironmentGroupsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudApigeeV1ListEnvironmentGroupsResponse])
			}
			object list {
				def apply(organizationsId :PlayApi, pageToken: String, pageSize: Int, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/envgroups").addQueryStringParameters("pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString, "parent" -> parent.toString))
				given Conversion[list, Future[Schema.GoogleCloudApigeeV1ListEnvironmentGroupsResponse]] = (fun: list) => fun.apply()
			}
			object attachments {
				/** Deletes an environment group attachment. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningOperation]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleLongrunningOperation])
				}
				object delete {
					def apply(organizationsId :PlayApi, envgroupsId :PlayApi, attachmentsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/envgroups/${envgroupsId}/attachments/${attachmentsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.GoogleLongrunningOperation]] = (fun: delete) => fun.apply()
				}
				/** Lists all attachments of an environment group. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1ListEnvironmentGroupAttachmentsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudApigeeV1ListEnvironmentGroupAttachmentsResponse])
				}
				object list {
					def apply(organizationsId :PlayApi, envgroupsId :PlayApi, parent: String, pageToken: String, pageSize: Int)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/envgroups/${envgroupsId}/attachments").addQueryStringParameters("parent" -> parent.toString, "pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString))
					given Conversion[list, Future[Schema.GoogleCloudApigeeV1ListEnvironmentGroupAttachmentsResponse]] = (fun: list) => fun.apply()
				}
				/** Gets an environment group attachment. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1EnvironmentGroupAttachment]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudApigeeV1EnvironmentGroupAttachment])
				}
				object get {
					def apply(organizationsId :PlayApi, envgroupsId :PlayApi, attachmentsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/envgroups/${envgroupsId}/attachments/${attachmentsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleCloudApigeeV1EnvironmentGroupAttachment]] = (fun: get) => fun.apply()
				}
				/** Creates a new attachment of an environment to an environment group. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGoogleCloudApigeeV1EnvironmentGroupAttachment(body: Schema.GoogleCloudApigeeV1EnvironmentGroupAttachment) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
				}
				object create {
					def apply(organizationsId :PlayApi, envgroupsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/envgroups/${envgroupsId}/attachments").addQueryStringParameters("parent" -> parent.toString))
				}
			}
		}
		object sites {
			object apidocs {
				/** Updates the documentation for the specified catalog item. Note that the documentation file contents will not be populated in the return message. */
				class updateDocumentation(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGoogleCloudApigeeV1ApiDocDocumentation(body: Schema.GoogleCloudApigeeV1ApiDocDocumentation) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudApigeeV1ApiDocDocumentationResponse])
				}
				object updateDocumentation {
					def apply(organizationsId :PlayApi, sitesId :PlayApi, apidocsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): updateDocumentation = new updateDocumentation(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/sites/${sitesId}/apidocs/${apidocsId}/documentation").addQueryStringParameters("name" -> name.toString))
				}
				/** Creates a new catalog item. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGoogleCloudApigeeV1ApiDoc(body: Schema.GoogleCloudApigeeV1ApiDoc) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudApigeeV1ApiDocResponse])
				}
				object create {
					def apply(organizationsId :PlayApi, sitesId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/sites/${sitesId}/apidocs").addQueryStringParameters("parent" -> parent.toString))
				}
				/** Gets the documentation for the specified catalog item. */
				class getDocumentation(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1ApiDocDocumentationResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudApigeeV1ApiDocDocumentationResponse])
				}
				object getDocumentation {
					def apply(organizationsId :PlayApi, sitesId :PlayApi, apidocsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): getDocumentation = new getDocumentation(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/sites/${sitesId}/apidocs/${apidocsId}/documentation").addQueryStringParameters("name" -> name.toString))
					given Conversion[getDocumentation, Future[Schema.GoogleCloudApigeeV1ApiDocDocumentationResponse]] = (fun: getDocumentation) => fun.apply()
				}
				/** Deletes a catalog item. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1DeleteResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleCloudApigeeV1DeleteResponse])
				}
				object delete {
					def apply(organizationsId :PlayApi, sitesId :PlayApi, apidocsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/sites/${sitesId}/apidocs/${apidocsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.GoogleCloudApigeeV1DeleteResponse]] = (fun: delete) => fun.apply()
				}
				/** Gets a catalog item. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1ApiDocResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudApigeeV1ApiDocResponse])
				}
				object get {
					def apply(organizationsId :PlayApi, sitesId :PlayApi, apidocsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/sites/${sitesId}/apidocs/${apidocsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleCloudApigeeV1ApiDocResponse]] = (fun: get) => fun.apply()
				}
				/** Updates a catalog item. */
				class update(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGoogleCloudApigeeV1ApiDoc(body: Schema.GoogleCloudApigeeV1ApiDoc) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.GoogleCloudApigeeV1ApiDocResponse])
				}
				object update {
					def apply(organizationsId :PlayApi, sitesId :PlayApi, apidocsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/sites/${sitesId}/apidocs/${apidocsId}").addQueryStringParameters("name" -> name.toString))
				}
				/** Returns the catalog items associated with a portal. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1ListApiDocsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Optional. A page token, received from a previous `ListApiDocs` call. Provide this to retrieve the subsequent page. */
					def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
					/** Optional. The maximum number of items to return. The service may return fewer than this value. If unspecified, at most 25 books will be returned. The maximum value is 100; values above 100 will be coerced to 100.<br>Format: int32 */
					def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudApigeeV1ListApiDocsResponse])
				}
				object list {
					def apply(organizationsId :PlayApi, sitesId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/sites/${sitesId}/apidocs").addQueryStringParameters("parent" -> parent.toString))
					given Conversion[list, Future[Schema.GoogleCloudApigeeV1ListApiDocsResponse]] = (fun: list) => fun.apply()
				}
			}
			object apicategories {
				/** Creates a new API category. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGoogleCloudApigeeV1ApiCategory(body: Schema.GoogleCloudApigeeV1ApiCategory) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudApigeeV1ApiCategoryResponse])
				}
				object create {
					def apply(organizationsId :PlayApi, sitesId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/sites/${sitesId}/apicategories").addQueryStringParameters("parent" -> parent.toString))
				}
				/** Deletes an API category. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1DeleteResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleCloudApigeeV1DeleteResponse])
				}
				object delete {
					def apply(organizationsId :PlayApi, sitesId :PlayApi, apicategoriesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/sites/${sitesId}/apicategories/${apicategoriesId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.GoogleCloudApigeeV1DeleteResponse]] = (fun: delete) => fun.apply()
				}
				/** Gets an API category. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1ApiCategoryResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudApigeeV1ApiCategoryResponse])
				}
				object get {
					def apply(organizationsId :PlayApi, sitesId :PlayApi, apicategoriesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/sites/${sitesId}/apicategories/${apicategoriesId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleCloudApigeeV1ApiCategoryResponse]] = (fun: get) => fun.apply()
				}
				/** Updates an API category. */
				class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGoogleCloudApigeeV1ApiCategory(body: Schema.GoogleCloudApigeeV1ApiCategory) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudApigeeV1ApiCategoryResponse])
				}
				object patch {
					def apply(organizationsId :PlayApi, sitesId :PlayApi, apicategoriesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/sites/${sitesId}/apicategories/${apicategoriesId}").addQueryStringParameters("name" -> name.toString))
				}
				/** Returns the API categories associated with a portal. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1ListApiCategoriesResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudApigeeV1ListApiCategoriesResponse])
				}
				object list {
					def apply(organizationsId :PlayApi, sitesId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/sites/${sitesId}/apicategories").addQueryStringParameters("parent" -> parent.toString))
					given Conversion[list, Future[Schema.GoogleCloudApigeeV1ListApiCategoriesResponse]] = (fun: list) => fun.apply()
				}
			}
		}
		object deployments {
			/** Lists all deployments of API proxies or shared flows. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1ListDeploymentsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Optional. Flag that specifies whether to return shared flow or API proxy deployments. Set to `true` to return shared flow deployments; set to `false` to return API proxy deployments. Defaults to `false`. */
				def withSharedFlows(sharedFlows: Boolean) = new list(req.addQueryStringParameters("sharedFlows" -> sharedFlows.toString))
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudApigeeV1ListDeploymentsResponse])
			}
			object list {
				def apply(organizationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/deployments").addQueryStringParameters("parent" -> parent.toString))
				given Conversion[list, Future[Schema.GoogleCloudApigeeV1ListDeploymentsResponse]] = (fun: list) => fun.apply()
			}
		}
		object hostSecurityReports {
			/** After the query is completed, use this API to retrieve the results. If the request succeeds, and there is a non-zero result set, the result is downloaded to the client as a zipped JSON file. The name of the downloaded file will be: OfflineQueryResult-.zip Example: `OfflineQueryResult-9cfc0d85-0f30-46d6-ae6f-318d0cb961bd.zip` */
			class getResult(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleApiHttpBody]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleApiHttpBody])
			}
			object getResult {
				def apply(organizationsId :PlayApi, hostSecurityReportsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): getResult = new getResult(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/hostSecurityReports/${hostSecurityReportsId}/result").addQueryStringParameters("name" -> name.toString))
				given Conversion[getResult, Future[Schema.GoogleApiHttpBody]] = (fun: getResult) => fun.apply()
			}
			/** Submit a query at host level to be processed in the background. If the submission of the query succeeds, the API returns a 201 status and an ID that refer to the query. In addition to the HTTP status 201, the `state` of "enqueued" means that the request succeeded. */
			class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withGoogleCloudApigeeV1SecurityReportQuery(body: Schema.GoogleCloudApigeeV1SecurityReportQuery) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudApigeeV1SecurityReport])
			}
			object create {
				def apply(organizationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/hostSecurityReports").addQueryStringParameters("parent" -> parent.toString))
			}
			/** After the query is completed, use this API to view the query result when result size is small. */
			class getResultView(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1SecurityReportResultView]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudApigeeV1SecurityReportResultView])
			}
			object getResultView {
				def apply(organizationsId :PlayApi, hostSecurityReportsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): getResultView = new getResultView(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/hostSecurityReports/${hostSecurityReportsId}/resultView").addQueryStringParameters("name" -> name.toString))
				given Conversion[getResultView, Future[Schema.GoogleCloudApigeeV1SecurityReportResultView]] = (fun: getResultView) => fun.apply()
			}
			/** Get status of a query submitted at host level. If the query is still in progress, the `state` is set to "running" After the query has completed successfully, `state` is set to "completed" */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1SecurityReport]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudApigeeV1SecurityReport])
			}
			object get {
				def apply(organizationsId :PlayApi, hostSecurityReportsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/hostSecurityReports/${hostSecurityReportsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.GoogleCloudApigeeV1SecurityReport]] = (fun: get) => fun.apply()
			}
			/** Return a list of Security Reports at host level. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1ListSecurityReportsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudApigeeV1ListSecurityReportsResponse])
			}
			object list {
				def apply(organizationsId :PlayApi, pageSize: Int, submittedBy: String, pageToken: String, envgroupHostname: String, to: String, from: String, parent: String, status: String, dataset: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/hostSecurityReports").addQueryStringParameters("pageSize" -> pageSize.toString, "submittedBy" -> submittedBy.toString, "pageToken" -> pageToken.toString, "envgroupHostname" -> envgroupHostname.toString, "to" -> to.toString, "from" -> from.toString, "parent" -> parent.toString, "status" -> status.toString, "dataset" -> dataset.toString))
				given Conversion[list, Future[Schema.GoogleCloudApigeeV1ListSecurityReportsResponse]] = (fun: list) => fun.apply()
			}
		}
		object hostQueries {
			/** After the query is completed, use this API to retrieve the results. If the request succeeds, and there is a non-zero result set, the result is downloaded to the client as a zipped JSON file. The name of the downloaded file will be: OfflineQueryResult-.zip Example: `OfflineQueryResult-9cfc0d85-0f30-46d6-ae6f-318d0cb961bd.zip` */
			class getResult(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleApiHttpBody]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleApiHttpBody])
			}
			object getResult {
				def apply(organizationsId :PlayApi, hostQueriesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): getResult = new getResult(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/hostQueries/${hostQueriesId}/result").addQueryStringParameters("name" -> name.toString))
				given Conversion[getResult, Future[Schema.GoogleApiHttpBody]] = (fun: getResult) => fun.apply()
			}
			/** Submit a query at host level to be processed in the background. If the submission of the query succeeds, the API returns a 201 status and an ID that refer to the query. In addition to the HTTP status 201, the `state` of "enqueued" means that the request succeeded. */
			class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withGoogleCloudApigeeV1Query(body: Schema.GoogleCloudApigeeV1Query) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudApigeeV1AsyncQuery])
			}
			object create {
				def apply(organizationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/hostQueries").addQueryStringParameters("parent" -> parent.toString))
			}
			/**  */
			class getResultView(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1AsyncQueryResultView]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudApigeeV1AsyncQueryResultView])
			}
			object getResultView {
				def apply(organizationsId :PlayApi, hostQueriesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): getResultView = new getResultView(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/hostQueries/${hostQueriesId}/resultView").addQueryStringParameters("name" -> name.toString))
				given Conversion[getResultView, Future[Schema.GoogleCloudApigeeV1AsyncQueryResultView]] = (fun: getResultView) => fun.apply()
			}
			/** Get status of a query submitted at host level. If the query is still in progress, the `state` is set to "running" After the query has completed successfully, `state` is set to "completed" */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1AsyncQuery]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudApigeeV1AsyncQuery])
			}
			object get {
				def apply(organizationsId :PlayApi, hostQueriesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/hostQueries/${hostQueriesId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.GoogleCloudApigeeV1AsyncQuery]] = (fun: get) => fun.apply()
			}
			/** Return a list of Asynchronous Queries at host level. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1ListAsyncQueriesResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudApigeeV1ListAsyncQueriesResponse])
			}
			object list {
				def apply(organizationsId :PlayApi, envgroupHostname: String, submittedBy: String, from: String, to: String, parent: String, status: String, inclQueriesWithoutReport: String, dataset: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/hostQueries").addQueryStringParameters("envgroupHostname" -> envgroupHostname.toString, "submittedBy" -> submittedBy.toString, "from" -> from.toString, "to" -> to.toString, "parent" -> parent.toString, "status" -> status.toString, "inclQueriesWithoutReport" -> inclQueriesWithoutReport.toString, "dataset" -> dataset.toString))
				given Conversion[list, Future[Schema.GoogleCloudApigeeV1ListAsyncQueriesResponse]] = (fun: list) => fun.apply()
			}
		}
		object apps {
			/** Gets the app profile for the specified app ID. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1App]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudApigeeV1App])
			}
			object get {
				def apply(organizationsId :PlayApi, appsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/apps/${appsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.GoogleCloudApigeeV1App]] = (fun: get) => fun.apply()
			}
			/** Lists IDs of apps within an organization that have the specified app status (approved or revoked) or are of the specified app type (developer or company). */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudApigeeV1ListAppsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
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
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudApigeeV1ListAppsResponse])
			}
			object list {
				def apply(organizationsId :PlayApi, startKey: String, parent: String, apiProduct: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/apps").addQueryStringParameters("startKey" -> startKey.toString, "parent" -> parent.toString, "apiProduct" -> apiProduct.toString))
				given Conversion[list, Future[Schema.GoogleCloudApigeeV1ListAppsResponse]] = (fun: list) => fun.apply()
			}
		}
	}
	object projects {
		/** Provisions a new Apigee organization with a functioning runtime. This is the standard way to create trial organizations for a free Apigee trial. */
		class provisionOrganization(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def withGoogleCloudApigeeV1ProvisionOrganizationRequest(body: Schema.GoogleCloudApigeeV1ProvisionOrganizationRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
		}
		object provisionOrganization {
			def apply(projectsId :PlayApi, project: String)(using signer: RequestSigner, ec: ExecutionContext): provisionOrganization = new provisionOrganization(ws.url(BASE_URL + s"v1/projects/${projectsId}:provisionOrganization").addQueryStringParameters("project" -> project.toString))
		}
	}
}
