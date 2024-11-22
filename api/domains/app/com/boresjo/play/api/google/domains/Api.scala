package com.boresjo.play.api.google.domains

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://domains.googleapis.com/"

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
			object operations {
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListOperationsResponse]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListOperationsResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, name: String, filter: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/operations").addQueryStringParameters("name" -> name.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListOperationsResponse]] = (fun: list) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Operation])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, operationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.Operation]] = (fun: get) => fun.apply()
				}
			}
			object registrations {
				class configureContactSettings(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withConfigureContactSettingsRequest(body: Schema.ConfigureContactSettingsRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object configureContactSettings {
					def apply(projectsId :PlayApi, locationsId :PlayApi, registrationsId :PlayApi, registration: String)(using auth: AuthToken, ec: ExecutionContext): configureContactSettings = new configureContactSettings(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/registrations/${registrationsId}:configureContactSettings").addQueryStringParameters("registration" -> registration.toString))
				}
				class initiatePushTransfer(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withInitiatePushTransferRequest(body: Schema.InitiatePushTransferRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object initiatePushTransfer {
					def apply(projectsId :PlayApi, locationsId :PlayApi, registrationsId :PlayApi, registration: String)(using auth: AuthToken, ec: ExecutionContext): initiatePushTransfer = new initiatePushTransfer(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/registrations/${registrationsId}:initiatePushTransfer").addQueryStringParameters("registration" -> registration.toString))
				}
				class setIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withSetIamPolicyRequest(body: Schema.SetIamPolicyRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
				}
				object setIamPolicy {
					def apply(projectsId :PlayApi, locationsId :PlayApi, registrationsId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): setIamPolicy = new setIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/registrations/${registrationsId}:setIamPolicy").addQueryStringParameters("resource" -> resource.toString))
				}
				class configureDnsSettings(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withConfigureDnsSettingsRequest(body: Schema.ConfigureDnsSettingsRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object configureDnsSettings {
					def apply(projectsId :PlayApi, locationsId :PlayApi, registrationsId :PlayApi, registration: String)(using auth: AuthToken, ec: ExecutionContext): configureDnsSettings = new configureDnsSettings(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/registrations/${registrationsId}:configureDnsSettings").addQueryStringParameters("registration" -> registration.toString))
				}
				class resetAuthorizationCode(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withResetAuthorizationCodeRequest(body: Schema.ResetAuthorizationCodeRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.AuthorizationCode])
				}
				object resetAuthorizationCode {
					def apply(projectsId :PlayApi, locationsId :PlayApi, registrationsId :PlayApi, registration: String)(using auth: AuthToken, ec: ExecutionContext): resetAuthorizationCode = new resetAuthorizationCode(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/registrations/${registrationsId}:resetAuthorizationCode").addQueryStringParameters("registration" -> registration.toString))
				}
				class `export`(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withExportRegistrationRequest(body: Schema.ExportRegistrationRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object `export` {
					def apply(projectsId :PlayApi, locationsId :PlayApi, registrationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): `export` = new `export`(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/registrations/${registrationsId}:export").addQueryStringParameters("name" -> name.toString))
				}
				class `import`(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withImportDomainRequest(body: Schema.ImportDomainRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object `import` {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): `import` = new `import`(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/registrations:import").addQueryStringParameters("parent" -> parent.toString))
				}
				class retrieveGoogleDomainsForwardingConfig(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.RetrieveGoogleDomainsForwardingConfigResponse]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.RetrieveGoogleDomainsForwardingConfigResponse])
				}
				object retrieveGoogleDomainsForwardingConfig {
					def apply(projectsId :PlayApi, locationsId :PlayApi, registrationsId :PlayApi, registration: String)(using auth: AuthToken, ec: ExecutionContext): retrieveGoogleDomainsForwardingConfig = new retrieveGoogleDomainsForwardingConfig(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/registrations/${registrationsId}:retrieveGoogleDomainsForwardingConfig").addQueryStringParameters("registration" -> registration.toString))
					given Conversion[retrieveGoogleDomainsForwardingConfig, Future[Schema.RetrieveGoogleDomainsForwardingConfigResponse]] = (fun: retrieveGoogleDomainsForwardingConfig) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Registration]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Registration])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, registrationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/registrations/${registrationsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.Registration]] = (fun: get) => fun.apply()
				}
				class retrieveRegisterParameters(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.RetrieveRegisterParametersResponse]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.RetrieveRegisterParametersResponse])
				}
				object retrieveRegisterParameters {
					def apply(projectsId :PlayApi, locationsId :PlayApi, location: String, domainName: String)(using auth: AuthToken, ec: ExecutionContext): retrieveRegisterParameters = new retrieveRegisterParameters(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/registrations:retrieveRegisterParameters").addQueryStringParameters("location" -> location.toString, "domainName" -> domainName.toString))
					given Conversion[retrieveRegisterParameters, Future[Schema.RetrieveRegisterParametersResponse]] = (fun: retrieveRegisterParameters) => fun.apply()
				}
				class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withRegistration(body: Schema.Registration) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Operation])
				}
				object patch {
					def apply(projectsId :PlayApi, locationsId :PlayApi, registrationsId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/registrations/${registrationsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
				}
				class retrieveImportableDomains(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.RetrieveImportableDomainsResponse]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.RetrieveImportableDomainsResponse])
				}
				object retrieveImportableDomains {
					def apply(projectsId :PlayApi, locationsId :PlayApi, location: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): retrieveImportableDomains = new retrieveImportableDomains(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/registrations:retrieveImportableDomains").addQueryStringParameters("location" -> location.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[retrieveImportableDomains, Future[Schema.RetrieveImportableDomainsResponse]] = (fun: retrieveImportableDomains) => fun.apply()
				}
				class configureManagementSettings(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withConfigureManagementSettingsRequest(body: Schema.ConfigureManagementSettingsRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object configureManagementSettings {
					def apply(projectsId :PlayApi, locationsId :PlayApi, registrationsId :PlayApi, registration: String)(using auth: AuthToken, ec: ExecutionContext): configureManagementSettings = new configureManagementSettings(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/registrations/${registrationsId}:configureManagementSettings").addQueryStringParameters("registration" -> registration.toString))
				}
				class testIamPermissions(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withTestIamPermissionsRequest(body: Schema.TestIamPermissionsRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.TestIamPermissionsResponse])
				}
				object testIamPermissions {
					def apply(projectsId :PlayApi, locationsId :PlayApi, registrationsId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): testIamPermissions = new testIamPermissions(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/registrations/${registrationsId}:testIamPermissions").addQueryStringParameters("resource" -> resource.toString))
				}
				class register(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withRegisterDomainRequest(body: Schema.RegisterDomainRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object register {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): register = new register(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/registrations:register").addQueryStringParameters("parent" -> parent.toString))
				}
				class searchDomains(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.SearchDomainsResponse]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.SearchDomainsResponse])
				}
				object searchDomains {
					def apply(projectsId :PlayApi, locationsId :PlayApi, location: String, query: String)(using auth: AuthToken, ec: ExecutionContext): searchDomains = new searchDomains(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/registrations:searchDomains").addQueryStringParameters("location" -> location.toString, "query" -> query.toString))
					given Conversion[searchDomains, Future[Schema.SearchDomainsResponse]] = (fun: searchDomains) => fun.apply()
				}
				class transfer(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withTransferDomainRequest(body: Schema.TransferDomainRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object transfer {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): transfer = new transfer(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/registrations:transfer").addQueryStringParameters("parent" -> parent.toString))
				}
				class retrieveAuthorizationCode(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.AuthorizationCode]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.AuthorizationCode])
				}
				object retrieveAuthorizationCode {
					def apply(projectsId :PlayApi, locationsId :PlayApi, registrationsId :PlayApi, registration: String)(using auth: AuthToken, ec: ExecutionContext): retrieveAuthorizationCode = new retrieveAuthorizationCode(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/registrations/${registrationsId}:retrieveAuthorizationCode").addQueryStringParameters("registration" -> registration.toString))
					given Conversion[retrieveAuthorizationCode, Future[Schema.AuthorizationCode]] = (fun: retrieveAuthorizationCode) => fun.apply()
				}
				class getIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Policy]) {
					/** Optional. The maximum policy version that will be used to format the policy. Valid values are 0, 1, and 3. Requests specifying an invalid value will be rejected. Requests for policies with any conditional role bindings must specify version 3. Policies with no conditional role bindings may specify any valid value or leave the field unset. The policy in the response might use the policy version that you specified, or it might use a lower policy version. For example, if you specify version 3, but the policy has no conditional role bindings, the response uses version 1. To learn which resources support conditions in their IAM policies, see the [IAM documentation](https://cloud.google.com/iam/help/conditions/resource-policies).<br>Format: int32 */
					def withOptionsRequestedPolicyVersion(optionsRequestedPolicyVersion: Int) = new getIamPolicy(req.addQueryStringParameters("options.requestedPolicyVersion" -> optionsRequestedPolicyVersion.toString))
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Policy])
				}
				object getIamPolicy {
					def apply(projectsId :PlayApi, locationsId :PlayApi, registrationsId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): getIamPolicy = new getIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/registrations/${registrationsId}:getIamPolicy").addQueryStringParameters("resource" -> resource.toString))
					given Conversion[getIamPolicy, Future[Schema.Policy]] = (fun: getIamPolicy) => fun.apply()
				}
				class retrieveGoogleDomainsDnsRecords(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.RetrieveGoogleDomainsDnsRecordsResponse]) {
					/** Optional. Maximum number of results to return.<br>Format: int32 */
					def withPageSize(pageSize: Int) = new retrieveGoogleDomainsDnsRecords(req.addQueryStringParameters("pageSize" -> pageSize.toString))
					/** Optional. When set to the `next_page_token` from a prior response, provides the next page of results. */
					def withPageToken(pageToken: String) = new retrieveGoogleDomainsDnsRecords(req.addQueryStringParameters("pageToken" -> pageToken.toString))
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.RetrieveGoogleDomainsDnsRecordsResponse])
				}
				object retrieveGoogleDomainsDnsRecords {
					def apply(projectsId :PlayApi, locationsId :PlayApi, registrationsId :PlayApi, registration: String)(using auth: AuthToken, ec: ExecutionContext): retrieveGoogleDomainsDnsRecords = new retrieveGoogleDomainsDnsRecords(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/registrations/${registrationsId}:retrieveGoogleDomainsDnsRecords").addQueryStringParameters("registration" -> registration.toString))
					given Conversion[retrieveGoogleDomainsDnsRecords, Future[Schema.RetrieveGoogleDomainsDnsRecordsResponse]] = (fun: retrieveGoogleDomainsDnsRecords) => fun.apply()
				}
				class renewDomain(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withRenewDomainRequest(body: Schema.RenewDomainRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object renewDomain {
					def apply(projectsId :PlayApi, locationsId :PlayApi, registrationsId :PlayApi, registration: String)(using auth: AuthToken, ec: ExecutionContext): renewDomain = new renewDomain(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/registrations/${registrationsId}:renewDomain").addQueryStringParameters("registration" -> registration.toString))
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
					def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Operation])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, registrationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/registrations/${registrationsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Operation]] = (fun: delete) => fun.apply()
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListRegistrationsResponse]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListRegistrationsResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, pageSize: Int, pageToken: String, filter: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/registrations").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "filter" -> filter.toString))
					given Conversion[list, Future[Schema.ListRegistrationsResponse]] = (fun: list) => fun.apply()
				}
				class retrieveTransferParameters(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.RetrieveTransferParametersResponse]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.RetrieveTransferParametersResponse])
				}
				object retrieveTransferParameters {
					def apply(projectsId :PlayApi, locationsId :PlayApi, location: String, domainName: String)(using auth: AuthToken, ec: ExecutionContext): retrieveTransferParameters = new retrieveTransferParameters(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/registrations:retrieveTransferParameters").addQueryStringParameters("location" -> location.toString, "domainName" -> domainName.toString))
					given Conversion[retrieveTransferParameters, Future[Schema.RetrieveTransferParametersResponse]] = (fun: retrieveTransferParameters) => fun.apply()
				}
			}
		}
	}
}
