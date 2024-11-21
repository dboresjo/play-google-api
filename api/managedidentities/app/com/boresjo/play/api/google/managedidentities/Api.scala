package com.boresjo.play.api.google.managedidentities

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://managedidentities.googleapis.com/"

	object projects {
		object locations {
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListLocationsResponse]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.ListLocationsResponse])
			}
			object list {
				def apply(projectsId :PlayApi, name: String, filter: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations")).addQueryStringParameters("name" -> name.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListLocationsResponse]] = (fun: list) => fun.apply()
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Location]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.Location])
			}
			object get {
				def apply(projectsId :PlayApi, locationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}")).addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.Location]] = (fun: get) => fun.apply()
			}
			object global {
				object operations {
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListOperationsResponse]) {
						def apply() = req.execute("GET").map(_.json.as[Schema.ListOperationsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, name: String, filter: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/global/operations")).addQueryStringParameters("name" -> name.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
						given Conversion[list, Future[Schema.ListOperationsResponse]] = (fun: list) => fun.apply()
					}
					class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
						def apply() = req.execute("GET").map(_.json.as[Schema.Operation])
					}
					object get {
						def apply(projectsId :PlayApi, operationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/global/operations/${operationsId}")).addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.Operation]] = (fun: get) => fun.apply()
					}
					class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
						def apply() = req.execute("DELETE").map(_.json.as[Schema.Empty])
					}
					object delete {
						def apply(projectsId :PlayApi, operationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/global/operations/${operationsId}")).addQueryStringParameters("name" -> name.toString))
						given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
					}
					class cancel(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withCancelOperationRequest(body: Schema.CancelOperationRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Empty])
					}
					object cancel {
						def apply(projectsId :PlayApi, operationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): cancel = new cancel(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/global/operations/${operationsId}:cancel")).addQueryStringParameters("name" -> name.toString))
					}
				}
				object domains {
					class testIamPermissions(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withTestIamPermissionsRequest(body: Schema.TestIamPermissionsRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.TestIamPermissionsResponse])
					}
					object testIamPermissions {
						def apply(projectsId :PlayApi, domainsId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): testIamPermissions = new testIamPermissions(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/global/domains/${domainsId}:testIamPermissions")).addQueryStringParameters("resource" -> resource.toString))
					}
					class reconfigureTrust(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withReconfigureTrustRequest(body: Schema.ReconfigureTrustRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Operation])
					}
					object reconfigureTrust {
						def apply(projectsId :PlayApi, domainsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): reconfigureTrust = new reconfigureTrust(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/global/domains/${domainsId}:reconfigureTrust")).addQueryStringParameters("name" -> name.toString))
					}
					class updateLdapssettings(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withLDAPSSettings(body: Schema.LDAPSSettings) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.Operation])
					}
					object updateLdapssettings {
						def apply(projectsId :PlayApi, domainsId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): updateLdapssettings = new updateLdapssettings(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/global/domains/${domainsId}/ldapssettings")).addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
					}
					class enableMigration(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withEnableMigrationRequest(body: Schema.EnableMigrationRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Operation])
					}
					object enableMigration {
						def apply(projectsId :PlayApi, domainsId :PlayApi, domain: String)(using auth: AuthToken, ec: ExecutionContext): enableMigration = new enableMigration(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/global/domains/${domainsId}:enableMigration")).addQueryStringParameters("domain" -> domain.toString))
					}
					class getIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Policy]) {
						/** Optional. The maximum policy version that will be used to format the policy. Valid values are 0, 1, and 3. Requests specifying an invalid value will be rejected. Requests for policies with any conditional role bindings must specify version 3. Policies with no conditional role bindings may specify any valid value or leave the field unset. The policy in the response might use the policy version that you specified, or it might use a lower policy version. For example, if you specify version 3, but the policy has no conditional role bindings, the response uses version 1. To learn which resources support conditions in their IAM policies, see the [IAM documentation](https://cloud.google.com/iam/help/conditions/resource-policies).<br>Format: int32 */
						def withOptionsRequestedPolicyVersion(optionsRequestedPolicyVersion: Int) = new getIamPolicy(req.addQueryStringParameters("options.requestedPolicyVersion" -> optionsRequestedPolicyVersion.toString))
						def apply() = req.execute("GET").map(_.json.as[Schema.Policy])
					}
					object getIamPolicy {
						def apply(projectsId :PlayApi, domainsId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): getIamPolicy = new getIamPolicy(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/global/domains/${domainsId}:getIamPolicy")).addQueryStringParameters("resource" -> resource.toString))
						given Conversion[getIamPolicy, Future[Schema.Policy]] = (fun: getIamPolicy) => fun.apply()
					}
					class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withDomain(body: Schema.Domain) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.Operation])
					}
					object patch {
						def apply(projectsId :PlayApi, domainsId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/global/domains/${domainsId}")).addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
					}
					class validateTrust(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withValidateTrustRequest(body: Schema.ValidateTrustRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Operation])
					}
					object validateTrust {
						def apply(projectsId :PlayApi, domainsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): validateTrust = new validateTrust(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/global/domains/${domainsId}:validateTrust")).addQueryStringParameters("name" -> name.toString))
					}
					class getLdapssettings(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.LDAPSSettings]) {
						def apply() = req.execute("GET").map(_.json.as[Schema.LDAPSSettings])
					}
					object getLdapssettings {
						def apply(projectsId :PlayApi, domainsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): getLdapssettings = new getLdapssettings(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/global/domains/${domainsId}/ldapssettings")).addQueryStringParameters("name" -> name.toString))
						given Conversion[getLdapssettings, Future[Schema.LDAPSSettings]] = (fun: getLdapssettings) => fun.apply()
					}
					class extendSchema(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withExtendSchemaRequest(body: Schema.ExtendSchemaRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Operation])
					}
					object extendSchema {
						def apply(projectsId :PlayApi, domainsId :PlayApi, domain: String)(using auth: AuthToken, ec: ExecutionContext): extendSchema = new extendSchema(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/global/domains/${domainsId}:extendSchema")).addQueryStringParameters("domain" -> domain.toString))
					}
					class detachTrust(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withDetachTrustRequest(body: Schema.DetachTrustRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Operation])
					}
					object detachTrust {
						def apply(projectsId :PlayApi, domainsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): detachTrust = new detachTrust(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/global/domains/${domainsId}:detachTrust")).addQueryStringParameters("name" -> name.toString))
					}
					class restore(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withRestoreDomainRequest(body: Schema.RestoreDomainRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Operation])
					}
					object restore {
						def apply(projectsId :PlayApi, domainsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): restore = new restore(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/global/domains/${domainsId}:restore")).addQueryStringParameters("name" -> name.toString))
					}
					class disableMigration(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withDisableMigrationRequest(body: Schema.DisableMigrationRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Operation])
					}
					object disableMigration {
						def apply(projectsId :PlayApi, domainsId :PlayApi, domain: String)(using auth: AuthToken, ec: ExecutionContext): disableMigration = new disableMigration(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/global/domains/${domainsId}:disableMigration")).addQueryStringParameters("domain" -> domain.toString))
					}
					class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withDomain(body: Schema.Domain) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Operation])
					}
					object create {
						def apply(projectsId :PlayApi, parent: String, domainName: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/global/domains")).addQueryStringParameters("parent" -> parent.toString, "domainName" -> domainName.toString))
					}
					class setIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withSetIamPolicyRequest(body: Schema.SetIamPolicyRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Policy])
					}
					object setIamPolicy {
						def apply(projectsId :PlayApi, domainsId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): setIamPolicy = new setIamPolicy(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/global/domains/${domainsId}:setIamPolicy")).addQueryStringParameters("resource" -> resource.toString))
					}
					class checkMigrationPermission(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withCheckMigrationPermissionRequest(body: Schema.CheckMigrationPermissionRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.CheckMigrationPermissionResponse])
					}
					object checkMigrationPermission {
						def apply(projectsId :PlayApi, domainsId :PlayApi, domain: String)(using auth: AuthToken, ec: ExecutionContext): checkMigrationPermission = new checkMigrationPermission(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/global/domains/${domainsId}:checkMigrationPermission")).addQueryStringParameters("domain" -> domain.toString))
					}
					class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
						def apply() = req.execute("DELETE").map(_.json.as[Schema.Operation])
					}
					object delete {
						def apply(projectsId :PlayApi, domainsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/global/domains/${domainsId}")).addQueryStringParameters("name" -> name.toString))
						given Conversion[delete, Future[Schema.Operation]] = (fun: delete) => fun.apply()
					}
					class domainJoinMachine(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withDomainJoinMachineRequest(body: Schema.DomainJoinMachineRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.DomainJoinMachineResponse])
					}
					object domainJoinMachine {
						def apply(projectsId :PlayApi, domainsId :PlayApi, domain: String)(using auth: AuthToken, ec: ExecutionContext): domainJoinMachine = new domainJoinMachine(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/global/domains/${domainsId}:domainJoinMachine")).addQueryStringParameters("domain" -> domain.toString))
					}
					class attachTrust(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withAttachTrustRequest(body: Schema.AttachTrustRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Operation])
					}
					object attachTrust {
						def apply(projectsId :PlayApi, domainsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): attachTrust = new attachTrust(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/global/domains/${domainsId}:attachTrust")).addQueryStringParameters("name" -> name.toString))
					}
					class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Domain]) {
						def apply() = req.execute("GET").map(_.json.as[Schema.Domain])
					}
					object get {
						def apply(projectsId :PlayApi, domainsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/global/domains/${domainsId}")).addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.Domain]] = (fun: get) => fun.apply()
					}
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListDomainsResponse]) {
						/** Optional. The maximum number of items to return. If not specified, a default value of 1000 will be used. Regardless of the page_size value, the response may include a partial list. Callers should rely on a response's next_page_token to determine if there are additional results to list.<br>Format: int32 */
						def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
						/** Optional. The `next_page_token` value returned from a previous ListDomainsRequest request, if any. */
						def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
						/** Optional. A filter specifying constraints of a list operation. For example, `Domain.fqdn="mydomain.myorginization"`. */
						def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
						/** Optional. Specifies the ordering of results. See [Sorting order](https://cloud.google.com/apis/design/design_patterns#sorting_order) for more information. */
						def withOrderBy(orderBy: String) = new list(req.addQueryStringParameters("orderBy" -> orderBy.toString))
						def apply() = req.execute("GET").map(_.json.as[Schema.ListDomainsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/global/domains")).addQueryStringParameters("parent" -> parent.toString))
						given Conversion[list, Future[Schema.ListDomainsResponse]] = (fun: list) => fun.apply()
					}
					class resetAdminPassword(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withResetAdminPasswordRequest(body: Schema.ResetAdminPasswordRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.ResetAdminPasswordResponse])
					}
					object resetAdminPassword {
						def apply(projectsId :PlayApi, domainsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): resetAdminPassword = new resetAdminPassword(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/global/domains/${domainsId}:resetAdminPassword")).addQueryStringParameters("name" -> name.toString))
					}
					object sqlIntegrations {
						class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListSqlIntegrationsResponse]) {
							/** Optional. The maximum number of items to return. If not specified, a default value of 1000 will be used by the service. Regardless of the page_size value, the response may include a partial list and a caller should only rely on response'ANIZATIONs next_page_token to determine if there are more instances left to be queried.<br>Format: int32 */
							def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
							/** Optional. The next_page_token value returned from a previous List request, if any. */
							def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
							/** Optional. Filter specifying constraints of a list operation. For example, `SqlIntegration.name="sql"`. */
							def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
							/** Optional. Specifies the ordering of results following syntax at https://cloud.google.com/apis/design/design_patterns#sorting_order. */
							def withOrderBy(orderBy: String) = new list(req.addQueryStringParameters("orderBy" -> orderBy.toString))
							def apply() = req.execute("GET").map(_.json.as[Schema.ListSqlIntegrationsResponse])
						}
						object list {
							def apply(projectsId :PlayApi, domainsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/global/domains/${domainsId}/sqlIntegrations")).addQueryStringParameters("parent" -> parent.toString))
							given Conversion[list, Future[Schema.ListSqlIntegrationsResponse]] = (fun: list) => fun.apply()
						}
						class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.SqlIntegration]) {
							def apply() = req.execute("GET").map(_.json.as[Schema.SqlIntegration])
						}
						object get {
							def apply(projectsId :PlayApi, domainsId :PlayApi, sqlIntegrationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/global/domains/${domainsId}/sqlIntegrations/${sqlIntegrationsId}")).addQueryStringParameters("name" -> name.toString))
							given Conversion[get, Future[Schema.SqlIntegration]] = (fun: get) => fun.apply()
						}
					}
					object backups {
						class testIamPermissions(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
							def withTestIamPermissionsRequest(body: Schema.TestIamPermissionsRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.TestIamPermissionsResponse])
						}
						object testIamPermissions {
							def apply(projectsId :PlayApi, domainsId :PlayApi, backupsId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): testIamPermissions = new testIamPermissions(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/global/domains/${domainsId}/backups/${backupsId}:testIamPermissions")).addQueryStringParameters("resource" -> resource.toString))
						}
						class getIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Policy]) {
							/** Optional. The maximum policy version that will be used to format the policy. Valid values are 0, 1, and 3. Requests specifying an invalid value will be rejected. Requests for policies with any conditional role bindings must specify version 3. Policies with no conditional role bindings may specify any valid value or leave the field unset. The policy in the response might use the policy version that you specified, or it might use a lower policy version. For example, if you specify version 3, but the policy has no conditional role bindings, the response uses version 1. To learn which resources support conditions in their IAM policies, see the [IAM documentation](https://cloud.google.com/iam/help/conditions/resource-policies).<br>Format: int32 */
							def withOptionsRequestedPolicyVersion(optionsRequestedPolicyVersion: Int) = new getIamPolicy(req.addQueryStringParameters("options.requestedPolicyVersion" -> optionsRequestedPolicyVersion.toString))
							def apply() = req.execute("GET").map(_.json.as[Schema.Policy])
						}
						object getIamPolicy {
							def apply(projectsId :PlayApi, domainsId :PlayApi, backupsId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): getIamPolicy = new getIamPolicy(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/global/domains/${domainsId}/backups/${backupsId}:getIamPolicy")).addQueryStringParameters("resource" -> resource.toString))
							given Conversion[getIamPolicy, Future[Schema.Policy]] = (fun: getIamPolicy) => fun.apply()
						}
						class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
							def apply() = req.execute("DELETE").map(_.json.as[Schema.Operation])
						}
						object delete {
							def apply(projectsId :PlayApi, domainsId :PlayApi, backupsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/global/domains/${domainsId}/backups/${backupsId}")).addQueryStringParameters("name" -> name.toString))
							given Conversion[delete, Future[Schema.Operation]] = (fun: delete) => fun.apply()
						}
						class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Backup]) {
							def apply() = req.execute("GET").map(_.json.as[Schema.Backup])
						}
						object get {
							def apply(projectsId :PlayApi, domainsId :PlayApi, backupsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/global/domains/${domainsId}/backups/${backupsId}")).addQueryStringParameters("name" -> name.toString))
							given Conversion[get, Future[Schema.Backup]] = (fun: get) => fun.apply()
						}
						class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
							def withBackup(body: Schema.Backup) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.Operation])
						}
						object patch {
							def apply(projectsId :PlayApi, domainsId :PlayApi, backupsId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/global/domains/${domainsId}/backups/${backupsId}")).addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
						}
						class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListBackupsResponse]) {
							/** Optional. The maximum number of items to return. If not specified, a default value of 1000 will be used by the service. Regardless of the page_size value, the response may include a partial list and a caller should only rely on response's next_page_token to determine if there are more instances left to be queried.<br>Format: int32 */
							def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
							/** Optional. The `next_page_token` value returned from a previous List request, if any. */
							def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
							/** Optional. Filter specifying constraints of a list operation. */
							def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
							/** Optional. Specifies the ordering of results following syntax at https://cloud.google.com/apis/design/design_patterns#sorting_order. */
							def withOrderBy(orderBy: String) = new list(req.addQueryStringParameters("orderBy" -> orderBy.toString))
							def apply() = req.execute("GET").map(_.json.as[Schema.ListBackupsResponse])
						}
						object list {
							def apply(projectsId :PlayApi, domainsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/global/domains/${domainsId}/backups")).addQueryStringParameters("parent" -> parent.toString))
							given Conversion[list, Future[Schema.ListBackupsResponse]] = (fun: list) => fun.apply()
						}
						class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
							def withBackup(body: Schema.Backup) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Operation])
						}
						object create {
							def apply(projectsId :PlayApi, domainsId :PlayApi, parent: String, backupId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/global/domains/${domainsId}/backups")).addQueryStringParameters("parent" -> parent.toString, "backupId" -> backupId.toString))
						}
						class setIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
							def withSetIamPolicyRequest(body: Schema.SetIamPolicyRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Policy])
						}
						object setIamPolicy {
							def apply(projectsId :PlayApi, domainsId :PlayApi, backupsId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): setIamPolicy = new setIamPolicy(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/global/domains/${domainsId}/backups/${backupsId}:setIamPolicy")).addQueryStringParameters("resource" -> resource.toString))
						}
					}
				}
				object peerings {
					class testIamPermissions(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withTestIamPermissionsRequest(body: Schema.TestIamPermissionsRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.TestIamPermissionsResponse])
					}
					object testIamPermissions {
						def apply(projectsId :PlayApi, peeringsId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): testIamPermissions = new testIamPermissions(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/global/peerings/${peeringsId}:testIamPermissions")).addQueryStringParameters("resource" -> resource.toString))
					}
					class getIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Policy]) {
						/** Optional. The maximum policy version that will be used to format the policy. Valid values are 0, 1, and 3. Requests specifying an invalid value will be rejected. Requests for policies with any conditional role bindings must specify version 3. Policies with no conditional role bindings may specify any valid value or leave the field unset. The policy in the response might use the policy version that you specified, or it might use a lower policy version. For example, if you specify version 3, but the policy has no conditional role bindings, the response uses version 1. To learn which resources support conditions in their IAM policies, see the [IAM documentation](https://cloud.google.com/iam/help/conditions/resource-policies).<br>Format: int32 */
						def withOptionsRequestedPolicyVersion(optionsRequestedPolicyVersion: Int) = new getIamPolicy(req.addQueryStringParameters("options.requestedPolicyVersion" -> optionsRequestedPolicyVersion.toString))
						def apply() = req.execute("GET").map(_.json.as[Schema.Policy])
					}
					object getIamPolicy {
						def apply(projectsId :PlayApi, peeringsId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): getIamPolicy = new getIamPolicy(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/global/peerings/${peeringsId}:getIamPolicy")).addQueryStringParameters("resource" -> resource.toString))
						given Conversion[getIamPolicy, Future[Schema.Policy]] = (fun: getIamPolicy) => fun.apply()
					}
					class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
						def apply() = req.execute("DELETE").map(_.json.as[Schema.Operation])
					}
					object delete {
						def apply(projectsId :PlayApi, peeringsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/global/peerings/${peeringsId}")).addQueryStringParameters("name" -> name.toString))
						given Conversion[delete, Future[Schema.Operation]] = (fun: delete) => fun.apply()
					}
					class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Peering]) {
						def apply() = req.execute("GET").map(_.json.as[Schema.Peering])
					}
					object get {
						def apply(projectsId :PlayApi, peeringsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/global/peerings/${peeringsId}")).addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.Peering]] = (fun: get) => fun.apply()
					}
					class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withPeering(body: Schema.Peering) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.Operation])
					}
					object patch {
						def apply(projectsId :PlayApi, peeringsId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/global/peerings/${peeringsId}")).addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
					}
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListPeeringsResponse]) {
						/** Optional. The maximum number of items to return. If not specified, a default value of 1000 will be used by the service. Regardless of the page_size value, the response may include a partial list and a caller should only rely on response's next_page_token to determine if there are more instances left to be queried.<br>Format: int32 */
						def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
						/** Optional. The next_page_token value returned from a previous List request, if any. */
						def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
						/** Optional. Filter specifying constraints of a list operation. For example, `peering.authorized_network="projects/myprojectid/global/networks/mynetwork"`. */
						def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
						/** Optional. Specifies the ordering of results following syntax at https://cloud.google.com/apis/design/design_patterns#sorting_order. */
						def withOrderBy(orderBy: String) = new list(req.addQueryStringParameters("orderBy" -> orderBy.toString))
						def apply() = req.execute("GET").map(_.json.as[Schema.ListPeeringsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/global/peerings")).addQueryStringParameters("parent" -> parent.toString))
						given Conversion[list, Future[Schema.ListPeeringsResponse]] = (fun: list) => fun.apply()
					}
					class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withPeering(body: Schema.Peering) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Operation])
					}
					object create {
						def apply(projectsId :PlayApi, parent: String, peeringId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/global/peerings")).addQueryStringParameters("parent" -> parent.toString, "peeringId" -> peeringId.toString))
					}
					class setIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withSetIamPolicyRequest(body: Schema.SetIamPolicyRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Policy])
					}
					object setIamPolicy {
						def apply(projectsId :PlayApi, peeringsId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): setIamPolicy = new setIamPolicy(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/global/peerings/${peeringsId}:setIamPolicy")).addQueryStringParameters("resource" -> resource.toString))
					}
				}
			}
		}
	}
}
