package com.boresjo.play.api.google.domains

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

	private val BASE_URL = "https://domains.googleapis.com/"

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
			object operations {
				/** Lists operations that match the specified filter in the request. If the server doesn't support this method, it returns `UNIMPLEMENTED`. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListOperationsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListOperationsResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, name: String, filter: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/operations").addQueryStringParameters("name" -> name.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListOperationsResponse]] = (fun: list) => fun.apply()
				}
				/** Gets the latest state of a long-running operation. Clients can use this method to poll the operation result at intervals as recommended by the API service. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Operation])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, operationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.Operation]] = (fun: get) => fun.apply()
				}
			}
			object registrations {
				/** Updates a `Registration`'s contact settings. Some changes require confirmation by the domain's registrant contact . Caution: Please consider carefully any changes to contact privacy settings when changing from `REDACTED_CONTACT_DATA` to `PUBLIC_CONTACT_DATA.` There may be a delay in reflecting updates you make to registrant contact information such that any changes you make to contact privacy (including from `REDACTED_CONTACT_DATA` to `PUBLIC_CONTACT_DATA`) will be applied without delay but changes to registrant contact information may take a limited time to be publicized. This means that changes to contact privacy from `REDACTED_CONTACT_DATA` to `PUBLIC_CONTACT_DATA` may make the previous registrant contact data public until the modified registrant contact details are published. */
				class configureContactSettings(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withConfigureContactSettingsRequest(body: Schema.ConfigureContactSettingsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object configureContactSettings {
					def apply(projectsId :PlayApi, locationsId :PlayApi, registrationsId :PlayApi, registration: String)(using signer: RequestSigner, ec: ExecutionContext): configureContactSettings = new configureContactSettings(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/registrations/${registrationsId}:configureContactSettings").addQueryStringParameters("registration" -> registration.toString))
				}
				/** Initiates the `Push Transfer` process to transfer the domain to another registrar. The process might complete instantly or might require confirmation or additional work. Check the emails sent to the email address of the registrant. The process is aborted after a timeout if it's not completed. This method is only supported for domains that have the `REQUIRE_PUSH_TRANSFER` property in the list of `domain_properties`. The domain must also be unlocked before it can be transferred to a different registrar. For more information, see [Transfer a registered domain to another registrar](https://cloud.google.com/domains/docs/transfer-domain-to-another-registrar). */
				class initiatePushTransfer(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withInitiatePushTransferRequest(body: Schema.InitiatePushTransferRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object initiatePushTransfer {
					def apply(projectsId :PlayApi, locationsId :PlayApi, registrationsId :PlayApi, registration: String)(using signer: RequestSigner, ec: ExecutionContext): initiatePushTransfer = new initiatePushTransfer(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/registrations/${registrationsId}:initiatePushTransfer").addQueryStringParameters("registration" -> registration.toString))
				}
				/** Sets the access control policy on the specified resource. Replaces any existing policy. Can return `NOT_FOUND`, `INVALID_ARGUMENT`, and `PERMISSION_DENIED` errors. */
				class setIamPolicy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withSetIamPolicyRequest(body: Schema.SetIamPolicyRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
				}
				object setIamPolicy {
					def apply(projectsId :PlayApi, locationsId :PlayApi, registrationsId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): setIamPolicy = new setIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/registrations/${registrationsId}:setIamPolicy").addQueryStringParameters("resource" -> resource.toString))
				}
				/** Updates a `Registration`'s DNS settings. */
				class configureDnsSettings(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withConfigureDnsSettingsRequest(body: Schema.ConfigureDnsSettingsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object configureDnsSettings {
					def apply(projectsId :PlayApi, locationsId :PlayApi, registrationsId :PlayApi, registration: String)(using signer: RequestSigner, ec: ExecutionContext): configureDnsSettings = new configureDnsSettings(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/registrations/${registrationsId}:configureDnsSettings").addQueryStringParameters("registration" -> registration.toString))
				}
				/** Resets the authorization code of the `Registration` to a new random string. You can call this method only after 60 days have elapsed since the initial domain registration. Domains that have the `REQUIRE_PUSH_TRANSFER` property in the list of `domain_properties` don't support authorization codes and must use the `InitiatePushTransfer` method to initiate the process to transfer the domain to a different registrar. */
				class resetAuthorizationCode(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withResetAuthorizationCodeRequest(body: Schema.ResetAuthorizationCodeRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.AuthorizationCode])
				}
				object resetAuthorizationCode {
					def apply(projectsId :PlayApi, locationsId :PlayApi, registrationsId :PlayApi, registration: String)(using signer: RequestSigner, ec: ExecutionContext): resetAuthorizationCode = new resetAuthorizationCode(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/registrations/${registrationsId}:resetAuthorizationCode").addQueryStringParameters("registration" -> registration.toString))
				}
				/** Deprecated: For more information, see [Cloud Domains feature deprecation](https://cloud.google.com/domains/docs/deprecations/feature-deprecations) Exports a `Registration` resource, such that it is no longer managed by Cloud Domains. When an active domain is successfully exported, you can continue to use the domain in [Google Domains](https://domains.google/) until it expires. The calling user becomes the domain's sole owner in Google Domains, and permissions for the domain are subsequently managed there. The domain does not renew automatically unless the new owner sets up billing in Google Domains. */
				class `export`(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withExportRegistrationRequest(body: Schema.ExportRegistrationRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object `export` {
					def apply(projectsId :PlayApi, locationsId :PlayApi, registrationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): `export` = new `export`(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/registrations/${registrationsId}:export").addQueryStringParameters("name" -> name.toString))
				}
				/** Deprecated: For more information, see [Cloud Domains feature deprecation](https://cloud.google.com/domains/docs/deprecations/feature-deprecations) Imports a domain name from [Google Domains](https://domains.google/) for use in Cloud Domains. To transfer a domain from another registrar, use the `TransferDomain` method instead. Since individual users can own domains in Google Domains, the calling user must have ownership permission on the domain. */
				class `import`(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withImportDomainRequest(body: Schema.ImportDomainRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object `import` {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): `import` = new `import`(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/registrations:import").addQueryStringParameters("parent" -> parent.toString))
				}
				/** Lists the deprecated domain and email forwarding configurations you set up in the deprecated Google Domains UI. The configuration is present only for domains with the `google_domains_redirects_data_available` set to `true` in the `Registration`'s `dns_settings`. A forwarding configuration might not work correctly if required DNS records are not present in the domain's authoritative DNS Zone. */
				class retrieveGoogleDomainsForwardingConfig(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.RetrieveGoogleDomainsForwardingConfigResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.RetrieveGoogleDomainsForwardingConfigResponse])
				}
				object retrieveGoogleDomainsForwardingConfig {
					def apply(projectsId :PlayApi, locationsId :PlayApi, registrationsId :PlayApi, registration: String)(using signer: RequestSigner, ec: ExecutionContext): retrieveGoogleDomainsForwardingConfig = new retrieveGoogleDomainsForwardingConfig(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/registrations/${registrationsId}:retrieveGoogleDomainsForwardingConfig").addQueryStringParameters("registration" -> registration.toString))
					given Conversion[retrieveGoogleDomainsForwardingConfig, Future[Schema.RetrieveGoogleDomainsForwardingConfigResponse]] = (fun: retrieveGoogleDomainsForwardingConfig) => fun.apply()
				}
				/** Gets the details of a `Registration` resource. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Registration]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Registration])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, registrationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/registrations/${registrationsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.Registration]] = (fun: get) => fun.apply()
				}
				/** Gets parameters needed to register a new domain name, including price and up-to-date availability. Use the returned values to call `RegisterDomain`. */
				class retrieveRegisterParameters(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.RetrieveRegisterParametersResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.RetrieveRegisterParametersResponse])
				}
				object retrieveRegisterParameters {
					def apply(projectsId :PlayApi, locationsId :PlayApi, location: String, domainName: String)(using signer: RequestSigner, ec: ExecutionContext): retrieveRegisterParameters = new retrieveRegisterParameters(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/registrations:retrieveRegisterParameters").addQueryStringParameters("location" -> location.toString, "domainName" -> domainName.toString))
					given Conversion[retrieveRegisterParameters, Future[Schema.RetrieveRegisterParametersResponse]] = (fun: retrieveRegisterParameters) => fun.apply()
				}
				/** Updates select fields of a `Registration` resource, notably `labels`. To update other fields, use the appropriate custom update method: &#42; To update management settings, see `ConfigureManagementSettings` &#42; To update DNS configuration, see `ConfigureDnsSettings` &#42; To update contact information, see `ConfigureContactSettings` */
				class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withRegistration(body: Schema.Registration) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Operation])
				}
				object patch {
					def apply(projectsId :PlayApi, locationsId :PlayApi, registrationsId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/registrations/${registrationsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
				}
				/** Deprecated: For more information, see [Cloud Domains feature deprecation](https://cloud.google.com/domains/docs/deprecations/feature-deprecations) Lists domain names from [Google Domains](https://domains.google/) that can be imported to Cloud Domains using the `ImportDomain` method. Since individual users can own domains in Google Domains, the list of domains returned depends on the individual user making the call. Domains already managed by Cloud Domains are not returned. */
				class retrieveImportableDomains(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.RetrieveImportableDomainsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.RetrieveImportableDomainsResponse])
				}
				object retrieveImportableDomains {
					def apply(projectsId :PlayApi, locationsId :PlayApi, location: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): retrieveImportableDomains = new retrieveImportableDomains(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/registrations:retrieveImportableDomains").addQueryStringParameters("location" -> location.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[retrieveImportableDomains, Future[Schema.RetrieveImportableDomainsResponse]] = (fun: retrieveImportableDomains) => fun.apply()
				}
				/** Updates a `Registration`'s management settings. */
				class configureManagementSettings(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withConfigureManagementSettingsRequest(body: Schema.ConfigureManagementSettingsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object configureManagementSettings {
					def apply(projectsId :PlayApi, locationsId :PlayApi, registrationsId :PlayApi, registration: String)(using signer: RequestSigner, ec: ExecutionContext): configureManagementSettings = new configureManagementSettings(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/registrations/${registrationsId}:configureManagementSettings").addQueryStringParameters("registration" -> registration.toString))
				}
				/** Returns permissions that a caller has on the specified resource. If the resource does not exist, this will return an empty set of permissions, not a `NOT_FOUND` error. Note: This operation is designed to be used for building permission-aware UIs and command-line tools, not for authorization checking. This operation may "fail open" without warning. */
				class testIamPermissions(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withTestIamPermissionsRequest(body: Schema.TestIamPermissionsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.TestIamPermissionsResponse])
				}
				object testIamPermissions {
					def apply(projectsId :PlayApi, locationsId :PlayApi, registrationsId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): testIamPermissions = new testIamPermissions(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/registrations/${registrationsId}:testIamPermissions").addQueryStringParameters("resource" -> resource.toString))
				}
				/** Registers a new domain name and creates a corresponding `Registration` resource. Call `RetrieveRegisterParameters` first to check availability of the domain name and determine parameters like price that are needed to build a call to this method. A successful call creates a `Registration` resource in state `REGISTRATION_PENDING`, which resolves to `ACTIVE` within 1-2 minutes, indicating that the domain was successfully registered. If the resource ends up in state `REGISTRATION_FAILED`, it indicates that the domain was not registered successfully, and you can safely delete the resource and retry registration. */
				class register(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withRegisterDomainRequest(body: Schema.RegisterDomainRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object register {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): register = new register(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/registrations:register").addQueryStringParameters("parent" -> parent.toString))
				}
				/** Searches for available domain names similar to the provided query. Availability results from this method are approximate; call `RetrieveRegisterParameters` on a domain before registering to confirm availability. */
				class searchDomains(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.SearchDomainsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.SearchDomainsResponse])
				}
				object searchDomains {
					def apply(projectsId :PlayApi, locationsId :PlayApi, location: String, query: String)(using signer: RequestSigner, ec: ExecutionContext): searchDomains = new searchDomains(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/registrations:searchDomains").addQueryStringParameters("location" -> location.toString, "query" -> query.toString))
					given Conversion[searchDomains, Future[Schema.SearchDomainsResponse]] = (fun: searchDomains) => fun.apply()
				}
				/** Deprecated: For more information, see [Cloud Domains feature deprecation](https://cloud.google.com/domains/docs/deprecations/feature-deprecations) Transfers a domain name from another registrar to Cloud Domains. For domains already managed by [Google Domains](https://domains.google/), use `ImportDomain` instead. Before calling this method, go to the domain's current registrar to unlock the domain for transfer and retrieve the domain's transfer authorization code. Then call `RetrieveTransferParameters` to confirm that the domain is unlocked and to get values needed to build a call to this method. A successful call creates a `Registration` resource in state `TRANSFER_PENDING`. It can take several days to complete the transfer process. The registrant can often speed up this process by approving the transfer through the current registrar, either by clicking a link in an email from the registrar or by visiting the registrar's website. A few minutes after transfer approval, the resource transitions to state `ACTIVE`, indicating that the transfer was successful. If the transfer is rejected or the request expires without being approved, the resource can end up in state `TRANSFER_FAILED`. If transfer fails, you can safely delete the resource and retry the transfer. */
				class transfer(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withTransferDomainRequest(body: Schema.TransferDomainRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object transfer {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): transfer = new transfer(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/registrations:transfer").addQueryStringParameters("parent" -> parent.toString))
				}
				/** Gets the authorization code of the `Registration` for the purpose of transferring the domain to another registrar. You can call this method only after 60 days have elapsed since the initial domain registration. Domains that have the `REQUIRE_PUSH_TRANSFER` property in the list of `domain_properties` don't support authorization codes and must use the `InitiatePushTransfer` method to initiate the process to transfer the domain to a different registrar. */
				class retrieveAuthorizationCode(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.AuthorizationCode]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.AuthorizationCode])
				}
				object retrieveAuthorizationCode {
					def apply(projectsId :PlayApi, locationsId :PlayApi, registrationsId :PlayApi, registration: String)(using signer: RequestSigner, ec: ExecutionContext): retrieveAuthorizationCode = new retrieveAuthorizationCode(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/registrations/${registrationsId}:retrieveAuthorizationCode").addQueryStringParameters("registration" -> registration.toString))
					given Conversion[retrieveAuthorizationCode, Future[Schema.AuthorizationCode]] = (fun: retrieveAuthorizationCode) => fun.apply()
				}
				/** Gets the access control policy for a resource. Returns an empty policy if the resource exists and does not have a policy set. */
				class getIamPolicy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Policy]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Optional. The maximum policy version that will be used to format the policy. Valid values are 0, 1, and 3. Requests specifying an invalid value will be rejected. Requests for policies with any conditional role bindings must specify version 3. Policies with no conditional role bindings may specify any valid value or leave the field unset. The policy in the response might use the policy version that you specified, or it might use a lower policy version. For example, if you specify version 3, but the policy has no conditional role bindings, the response uses version 1. To learn which resources support conditions in their IAM policies, see the [IAM documentation](https://cloud.google.com/iam/help/conditions/resource-policies).<br>Format: int32 */
					def withOptionsRequestedPolicyVersion(optionsRequestedPolicyVersion: Int) = new getIamPolicy(req.addQueryStringParameters("options.requestedPolicyVersion" -> optionsRequestedPolicyVersion.toString))
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Policy])
				}
				object getIamPolicy {
					def apply(projectsId :PlayApi, locationsId :PlayApi, registrationsId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): getIamPolicy = new getIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/registrations/${registrationsId}:getIamPolicy").addQueryStringParameters("resource" -> resource.toString))
					given Conversion[getIamPolicy, Future[Schema.Policy]] = (fun: getIamPolicy) => fun.apply()
				}
				/** Lists the DNS records from the Google Domains DNS zone for domains that use the deprecated `google_domains_dns` in the `Registration`'s `dns_settings`. */
				class retrieveGoogleDomainsDnsRecords(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.RetrieveGoogleDomainsDnsRecordsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Optional. Maximum number of results to return.<br>Format: int32 */
					def withPageSize(pageSize: Int) = new retrieveGoogleDomainsDnsRecords(req.addQueryStringParameters("pageSize" -> pageSize.toString))
					/** Optional. When set to the `next_page_token` from a prior response, provides the next page of results. */
					def withPageToken(pageToken: String) = new retrieveGoogleDomainsDnsRecords(req.addQueryStringParameters("pageToken" -> pageToken.toString))
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.RetrieveGoogleDomainsDnsRecordsResponse])
				}
				object retrieveGoogleDomainsDnsRecords {
					def apply(projectsId :PlayApi, locationsId :PlayApi, registrationsId :PlayApi, registration: String)(using signer: RequestSigner, ec: ExecutionContext): retrieveGoogleDomainsDnsRecords = new retrieveGoogleDomainsDnsRecords(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/registrations/${registrationsId}:retrieveGoogleDomainsDnsRecords").addQueryStringParameters("registration" -> registration.toString))
					given Conversion[retrieveGoogleDomainsDnsRecords, Future[Schema.RetrieveGoogleDomainsDnsRecordsResponse]] = (fun: retrieveGoogleDomainsDnsRecords) => fun.apply()
				}
				/** Renews a recently expired domain. This method can only be called on domains that expired in the previous 30 days. After the renewal, the new expiration time of the domain is one year after the old expiration time and you are charged a `yearly_price` for the renewal. */
				class renewDomain(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withRenewDomainRequest(body: Schema.RenewDomainRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object renewDomain {
					def apply(projectsId :PlayApi, locationsId :PlayApi, registrationsId :PlayApi, registration: String)(using signer: RequestSigner, ec: ExecutionContext): renewDomain = new renewDomain(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/registrations/${registrationsId}:renewDomain").addQueryStringParameters("registration" -> registration.toString))
				}
				/** Deletes a `Registration` resource. This method works on any `Registration` resource using [Subscription or Commitment billing](/domains/pricing#billing-models), provided that the resource was created at least 1 day in the past. When an active registration is successfully deleted, you can continue to use the domain in [Google Domains](https://domains.google/) until it expires. The calling user becomes the domain's sole owner in Google Domains, and permissions for the domain are subsequently managed there. The domain does not renew automatically unless the new owner sets up billing in Google Domains. After January 2024 you will only be able to delete `Registration` resources when `state` is one of: `EXPORTED`, `EXPIRED`,`REGISTRATION_FAILED` or `TRANSFER_FAILED`. See [Cloud Domains feature deprecation](https://cloud.google.com/domains/docs/deprecations/feature-deprecations) for more details. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Operation])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, registrationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/registrations/${registrationsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Operation]] = (fun: delete) => fun.apply()
				}
				/** Lists the `Registration` resources in a project. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListRegistrationsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListRegistrationsResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, pageSize: Int, pageToken: String, filter: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/registrations").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "filter" -> filter.toString))
					given Conversion[list, Future[Schema.ListRegistrationsResponse]] = (fun: list) => fun.apply()
				}
				/** Deprecated: For more information, see [Cloud Domains feature deprecation](https://cloud.google.com/domains/docs/deprecations/feature-deprecations) Gets parameters needed to transfer a domain name from another registrar to Cloud Domains. For domains already managed by [Google Domains](https://domains.google/), use `ImportDomain` instead. Use the returned values to call `TransferDomain`. */
				class retrieveTransferParameters(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.RetrieveTransferParametersResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.RetrieveTransferParametersResponse])
				}
				object retrieveTransferParameters {
					def apply(projectsId :PlayApi, locationsId :PlayApi, location: String, domainName: String)(using signer: RequestSigner, ec: ExecutionContext): retrieveTransferParameters = new retrieveTransferParameters(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/registrations:retrieveTransferParameters").addQueryStringParameters("location" -> location.toString, "domainName" -> domainName.toString))
					given Conversion[retrieveTransferParameters, Future[Schema.RetrieveTransferParametersResponse]] = (fun: retrieveTransferParameters) => fun.apply()
				}
			}
		}
	}
}
