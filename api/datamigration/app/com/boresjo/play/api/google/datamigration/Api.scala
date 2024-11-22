package com.boresjo.play.api.google.datamigration

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://datamigration.googleapis.com/"

	object projects {
		object locations {
			class fetchStaticIps(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.FetchStaticIpsResponse]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.FetchStaticIpsResponse])
			}
			object fetchStaticIps {
				def apply(projectsId :PlayApi, locationsId :PlayApi, name: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): fetchStaticIps = new fetchStaticIps(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}:fetchStaticIps").addQueryStringParameters("name" -> name.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[fetchStaticIps, Future[Schema.FetchStaticIpsResponse]] = (fun: fetchStaticIps) => fun.apply()
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Location]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Location])
			}
			object get {
				def apply(projectsId :PlayApi, locationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.Location]] = (fun: get) => fun.apply()
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListLocationsResponse]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListLocationsResponse])
			}
			object list {
				def apply(projectsId :PlayApi, name: String, filter: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations").addQueryStringParameters("name" -> name.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListLocationsResponse]] = (fun: list) => fun.apply()
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
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, operationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
				}
				class cancel(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withCancelOperationRequest(body: Schema.CancelOperationRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Empty])
				}
				object cancel {
					def apply(projectsId :PlayApi, locationsId :PlayApi, operationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): cancel = new cancel(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/operations/${operationsId}:cancel").addQueryStringParameters("name" -> name.toString))
				}
			}
			object conversionWorkspaces {
				class describeConversionWorkspaceRevisions(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.DescribeConversionWorkspaceRevisionsResponse]) {
					/** Optional. Optional filter to request a specific commit ID. */
					def withCommitId(commitId: String) = new describeConversionWorkspaceRevisions(req.addQueryStringParameters("commitId" -> commitId.toString))
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.DescribeConversionWorkspaceRevisionsResponse])
				}
				object describeConversionWorkspaceRevisions {
					def apply(projectsId :PlayApi, locationsId :PlayApi, conversionWorkspacesId :PlayApi, conversionWorkspace: String)(using auth: AuthToken, ec: ExecutionContext): describeConversionWorkspaceRevisions = new describeConversionWorkspaceRevisions(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/conversionWorkspaces/${conversionWorkspacesId}:describeConversionWorkspaceRevisions").addQueryStringParameters("conversionWorkspace" -> conversionWorkspace.toString))
					given Conversion[describeConversionWorkspaceRevisions, Future[Schema.DescribeConversionWorkspaceRevisionsResponse]] = (fun: describeConversionWorkspaceRevisions) => fun.apply()
				}
				class searchBackgroundJobs(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.SearchBackgroundJobsResponse]) {
					/** Optional. Whether or not to return just the most recent job per job type, */
					def withReturnMostRecentPerJobType(returnMostRecentPerJobType: Boolean) = new searchBackgroundJobs(req.addQueryStringParameters("returnMostRecentPerJobType" -> returnMostRecentPerJobType.toString))
					/** Optional. The maximum number of jobs to return. The service may return fewer than this value. If unspecified, at most 100 jobs are returned. The maximum value is 100; values above 100 are coerced to 100.<br>Format: int32 */
					def withMaxSize(maxSize: Int) = new searchBackgroundJobs(req.addQueryStringParameters("maxSize" -> maxSize.toString))
					/** Optional. If provided, only returns jobs that completed until (not including) the given timestamp.<br>Format: google-datetime */
					def withCompletedUntilTime(completedUntilTime: String) = new searchBackgroundJobs(req.addQueryStringParameters("completedUntilTime" -> completedUntilTime.toString))
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.SearchBackgroundJobsResponse])
				}
				object searchBackgroundJobs {
					def apply(projectsId :PlayApi, locationsId :PlayApi, conversionWorkspacesId :PlayApi, conversionWorkspace: String)(using auth: AuthToken, ec: ExecutionContext): searchBackgroundJobs = new searchBackgroundJobs(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/conversionWorkspaces/${conversionWorkspacesId}:searchBackgroundJobs").addQueryStringParameters("conversionWorkspace" -> conversionWorkspace.toString))
					given Conversion[searchBackgroundJobs, Future[Schema.SearchBackgroundJobsResponse]] = (fun: searchBackgroundJobs) => fun.apply()
				}
				class rollback(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withRollbackConversionWorkspaceRequest(body: Schema.RollbackConversionWorkspaceRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object rollback {
					def apply(projectsId :PlayApi, locationsId :PlayApi, conversionWorkspacesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): rollback = new rollback(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/conversionWorkspaces/${conversionWorkspacesId}:rollback").addQueryStringParameters("name" -> name.toString))
				}
				class apply(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withApplyConversionWorkspaceRequest(body: Schema.ApplyConversionWorkspaceRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object apply {
					def apply(projectsId :PlayApi, locationsId :PlayApi, conversionWorkspacesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): apply = new apply(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/conversionWorkspaces/${conversionWorkspacesId}:apply").addQueryStringParameters("name" -> name.toString))
				}
				class getIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Policy]) {
					/** Optional. The maximum policy version that will be used to format the policy. Valid values are 0, 1, and 3. Requests specifying an invalid value will be rejected. Requests for policies with any conditional role bindings must specify version 3. Policies with no conditional role bindings may specify any valid value or leave the field unset. The policy in the response might use the policy version that you specified, or it might use a lower policy version. For example, if you specify version 3, but the policy has no conditional role bindings, the response uses version 1. To learn which resources support conditions in their IAM policies, see the [IAM documentation](https://cloud.google.com/iam/help/conditions/resource-policies).<br>Format: int32 */
					def withOptionsRequestedPolicyVersion(optionsRequestedPolicyVersion: Int) = new getIamPolicy(req.addQueryStringParameters("options.requestedPolicyVersion" -> optionsRequestedPolicyVersion.toString))
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Policy])
				}
				object getIamPolicy {
					def apply(projectsId :PlayApi, locationsId :PlayApi, conversionWorkspacesId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): getIamPolicy = new getIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/conversionWorkspaces/${conversionWorkspacesId}:getIamPolicy").addQueryStringParameters("resource" -> resource.toString))
					given Conversion[getIamPolicy, Future[Schema.Policy]] = (fun: getIamPolicy) => fun.apply()
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
					def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Operation])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, conversionWorkspacesId :PlayApi, name: String, requestId: String, force: Boolean)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/conversionWorkspaces/${conversionWorkspacesId}").addQueryStringParameters("name" -> name.toString, "requestId" -> requestId.toString, "force" -> force.toString))
					given Conversion[delete, Future[Schema.Operation]] = (fun: delete) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ConversionWorkspace]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ConversionWorkspace])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, conversionWorkspacesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/conversionWorkspaces/${conversionWorkspacesId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.ConversionWorkspace]] = (fun: get) => fun.apply()
				}
				class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withConversionWorkspace(body: Schema.ConversionWorkspace) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Operation])
				}
				object patch {
					def apply(projectsId :PlayApi, locationsId :PlayApi, conversionWorkspacesId :PlayApi, name: String, updateMask: String, requestId: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/conversionWorkspaces/${conversionWorkspacesId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString, "requestId" -> requestId.toString))
				}
				class convert(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withConvertConversionWorkspaceRequest(body: Schema.ConvertConversionWorkspaceRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object convert {
					def apply(projectsId :PlayApi, locationsId :PlayApi, conversionWorkspacesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): convert = new convert(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/conversionWorkspaces/${conversionWorkspacesId}:convert").addQueryStringParameters("name" -> name.toString))
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListConversionWorkspacesResponse]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListConversionWorkspacesResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, pageSize: Int, pageToken: String, filter: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/conversionWorkspaces").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "filter" -> filter.toString))
					given Conversion[list, Future[Schema.ListConversionWorkspacesResponse]] = (fun: list) => fun.apply()
				}
				class testIamPermissions(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withTestIamPermissionsRequest(body: Schema.TestIamPermissionsRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.TestIamPermissionsResponse])
				}
				object testIamPermissions {
					def apply(projectsId :PlayApi, locationsId :PlayApi, conversionWorkspacesId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): testIamPermissions = new testIamPermissions(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/conversionWorkspaces/${conversionWorkspacesId}:testIamPermissions").addQueryStringParameters("resource" -> resource.toString))
				}
				class describeDatabaseEntities(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.DescribeDatabaseEntitiesResponse]) {
					/** Optional. The maximum number of entities to return. The service may return fewer entities than the value specifies.<br>Format: int32 */
					def withPageSize(pageSize: Int) = new describeDatabaseEntities(req.addQueryStringParameters("pageSize" -> pageSize.toString))
					/** Optional. The nextPageToken value received in the previous call to conversionWorkspace.describeDatabaseEntities, used in the subsequent request to retrieve the next page of results. On first call this should be left blank. When paginating, all other parameters provided to conversionWorkspace.describeDatabaseEntities must match the call that provided the page token. */
					def withPageToken(pageToken: String) = new describeDatabaseEntities(req.addQueryStringParameters("pageToken" -> pageToken.toString))
					/** Optional. Whether to retrieve the latest committed version of the entities or the latest version. This field is ignored if a specific commit_id is specified. */
					def withUncommitted(uncommitted: Boolean) = new describeDatabaseEntities(req.addQueryStringParameters("uncommitted" -> uncommitted.toString))
					/** Optional. Request a specific commit ID. If not specified, the entities from the latest commit are returned. */
					def withCommitId(commitId: String) = new describeDatabaseEntities(req.addQueryStringParameters("commitId" -> commitId.toString))
					/** Optional. Filter the returned entities based on AIP-160 standard. */
					def withFilter(filter: String) = new describeDatabaseEntities(req.addQueryStringParameters("filter" -> filter.toString))
					/** Optional. Results view based on AIP-157<br>Possible values:<br>DATABASE_ENTITY_VIEW_UNSPECIFIED: Unspecified view. Defaults to basic view.<br>DATABASE_ENTITY_VIEW_BASIC: Default view. Does not return DDLs or Issues.<br>DATABASE_ENTITY_VIEW_FULL: Return full entity details including mappings, ddl and issues.<br>DATABASE_ENTITY_VIEW_ROOT_SUMMARY: Top-most (Database, Schema) nodes which are returned contains summary details for their decendents such as the number of entities per type and issues rollups. When this view is used, only a single page of result is returned and the page_size property of the request is ignored. The returned page will only include the top-most node types. */
					def withView(view: String) = new describeDatabaseEntities(req.addQueryStringParameters("view" -> view.toString))
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.DescribeDatabaseEntitiesResponse])
				}
				object describeDatabaseEntities {
					def apply(projectsId :PlayApi, locationsId :PlayApi, conversionWorkspacesId :PlayApi, conversionWorkspace: String, tree: String)(using auth: AuthToken, ec: ExecutionContext): describeDatabaseEntities = new describeDatabaseEntities(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/conversionWorkspaces/${conversionWorkspacesId}:describeDatabaseEntities").addQueryStringParameters("conversionWorkspace" -> conversionWorkspace.toString, "tree" -> tree.toString))
					given Conversion[describeDatabaseEntities, Future[Schema.DescribeDatabaseEntitiesResponse]] = (fun: describeDatabaseEntities) => fun.apply()
				}
				class commit(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withCommitConversionWorkspaceRequest(body: Schema.CommitConversionWorkspaceRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object commit {
					def apply(projectsId :PlayApi, locationsId :PlayApi, conversionWorkspacesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): commit = new commit(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/conversionWorkspaces/${conversionWorkspacesId}:commit").addQueryStringParameters("name" -> name.toString))
				}
				class seed(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withSeedConversionWorkspaceRequest(body: Schema.SeedConversionWorkspaceRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object seed {
					def apply(projectsId :PlayApi, locationsId :PlayApi, conversionWorkspacesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): seed = new seed(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/conversionWorkspaces/${conversionWorkspacesId}:seed").addQueryStringParameters("name" -> name.toString))
				}
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withConversionWorkspace(body: Schema.ConversionWorkspace) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, conversionWorkspaceId: String, requestId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/conversionWorkspaces").addQueryStringParameters("parent" -> parent.toString, "conversionWorkspaceId" -> conversionWorkspaceId.toString, "requestId" -> requestId.toString))
				}
				class setIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withSetIamPolicyRequest(body: Schema.SetIamPolicyRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
				}
				object setIamPolicy {
					def apply(projectsId :PlayApi, locationsId :PlayApi, conversionWorkspacesId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): setIamPolicy = new setIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/conversionWorkspaces/${conversionWorkspacesId}:setIamPolicy").addQueryStringParameters("resource" -> resource.toString))
				}
				object mappingRules {
					class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withMappingRule(body: Schema.MappingRule) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.MappingRule])
					}
					object create {
						def apply(projectsId :PlayApi, locationsId :PlayApi, conversionWorkspacesId :PlayApi, parent: String, mappingRuleId: String, requestId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/conversionWorkspaces/${conversionWorkspacesId}/mappingRules").addQueryStringParameters("parent" -> parent.toString, "mappingRuleId" -> mappingRuleId.toString, "requestId" -> requestId.toString))
					}
					class `import`(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withImportMappingRulesRequest(body: Schema.ImportMappingRulesRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
					}
					object `import` {
						def apply(projectsId :PlayApi, locationsId :PlayApi, conversionWorkspacesId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): `import` = new `import`(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/conversionWorkspaces/${conversionWorkspacesId}/mappingRules:import").addQueryStringParameters("parent" -> parent.toString))
					}
					class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
						/** Optional. A unique ID used to identify the request. If the server receives two requests with the same ID, then the second request is ignored. It is recommended to always set this value to a UUID. The ID must contain only letters (a-z, A-Z), numbers (0-9), underscores (_), and hyphens (-). The maximum length is 40 characters. */
						def withRequestId(requestId: String) = new delete(req.addQueryStringParameters("requestId" -> requestId.toString))
						def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
					}
					object delete {
						def apply(projectsId :PlayApi, locationsId :PlayApi, conversionWorkspacesId :PlayApi, mappingRulesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/conversionWorkspaces/${conversionWorkspacesId}/mappingRules/${mappingRulesId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
					}
					class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.MappingRule]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.MappingRule])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, conversionWorkspacesId :PlayApi, mappingRulesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/conversionWorkspaces/${conversionWorkspacesId}/mappingRules/${mappingRulesId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.MappingRule]] = (fun: get) => fun.apply()
					}
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListMappingRulesResponse]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListMappingRulesResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, conversionWorkspacesId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/conversionWorkspaces/${conversionWorkspacesId}/mappingRules").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
						given Conversion[list, Future[Schema.ListMappingRulesResponse]] = (fun: list) => fun.apply()
					}
				}
			}
			object privateConnections {
				class testIamPermissions(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withTestIamPermissionsRequest(body: Schema.TestIamPermissionsRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.TestIamPermissionsResponse])
				}
				object testIamPermissions {
					def apply(projectsId :PlayApi, locationsId :PlayApi, privateConnectionsId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): testIamPermissions = new testIamPermissions(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/privateConnections/${privateConnectionsId}:testIamPermissions").addQueryStringParameters("resource" -> resource.toString))
				}
				class getIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Policy]) {
					/** Optional. The maximum policy version that will be used to format the policy. Valid values are 0, 1, and 3. Requests specifying an invalid value will be rejected. Requests for policies with any conditional role bindings must specify version 3. Policies with no conditional role bindings may specify any valid value or leave the field unset. The policy in the response might use the policy version that you specified, or it might use a lower policy version. For example, if you specify version 3, but the policy has no conditional role bindings, the response uses version 1. To learn which resources support conditions in their IAM policies, see the [IAM documentation](https://cloud.google.com/iam/help/conditions/resource-policies).<br>Format: int32 */
					def withOptionsRequestedPolicyVersion(optionsRequestedPolicyVersion: Int) = new getIamPolicy(req.addQueryStringParameters("options.requestedPolicyVersion" -> optionsRequestedPolicyVersion.toString))
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Policy])
				}
				object getIamPolicy {
					def apply(projectsId :PlayApi, locationsId :PlayApi, privateConnectionsId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): getIamPolicy = new getIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/privateConnections/${privateConnectionsId}:getIamPolicy").addQueryStringParameters("resource" -> resource.toString))
					given Conversion[getIamPolicy, Future[Schema.Policy]] = (fun: getIamPolicy) => fun.apply()
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
					/** Optional. A unique ID used to identify the request. If the server receives two requests with the same ID, then the second request is ignored. It is recommended to always set this value to a UUID. The ID must contain only letters (a-z, A-Z), numbers (0-9), underscores (_), and hyphens (-). The maximum length is 40 characters. */
					def withRequestId(requestId: String) = new delete(req.addQueryStringParameters("requestId" -> requestId.toString))
					def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Operation])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, privateConnectionsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/privateConnections/${privateConnectionsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Operation]] = (fun: delete) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.PrivateConnection]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.PrivateConnection])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, privateConnectionsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/privateConnections/${privateConnectionsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.PrivateConnection]] = (fun: get) => fun.apply()
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListPrivateConnectionsResponse]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListPrivateConnectionsResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, pageSize: Int, pageToken: String, filter: String, orderBy: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/privateConnections").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "filter" -> filter.toString, "orderBy" -> orderBy.toString))
					given Conversion[list, Future[Schema.ListPrivateConnectionsResponse]] = (fun: list) => fun.apply()
				}
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					/** Optional. A unique ID used to identify the request. If the server receives two requests with the same ID, then the second request is ignored. It is recommended to always set this value to a UUID. The ID must contain only letters (a-z, A-Z), numbers (0-9), underscores (_), and hyphens (-). The maximum length is 40 characters. */
					def withRequestId(requestId: String) = new create(req.addQueryStringParameters("requestId" -> requestId.toString))
					/** Optional. If set to true, will skip validations. */
					def withSkipValidation(skipValidation: Boolean) = new create(req.addQueryStringParameters("skipValidation" -> skipValidation.toString))
					def withPrivateConnection(body: Schema.PrivateConnection) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, privateConnectionId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/privateConnections").addQueryStringParameters("parent" -> parent.toString, "privateConnectionId" -> privateConnectionId.toString))
				}
				class setIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withSetIamPolicyRequest(body: Schema.SetIamPolicyRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
				}
				object setIamPolicy {
					def apply(projectsId :PlayApi, locationsId :PlayApi, privateConnectionsId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): setIamPolicy = new setIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/privateConnections/${privateConnectionsId}:setIamPolicy").addQueryStringParameters("resource" -> resource.toString))
				}
			}
			object migrationJobs {
				class testIamPermissions(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withTestIamPermissionsRequest(body: Schema.TestIamPermissionsRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.TestIamPermissionsResponse])
				}
				object testIamPermissions {
					def apply(projectsId :PlayApi, locationsId :PlayApi, migrationJobsId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): testIamPermissions = new testIamPermissions(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/migrationJobs/${migrationJobsId}:testIamPermissions").addQueryStringParameters("resource" -> resource.toString))
				}
				class resume(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withResumeMigrationJobRequest(body: Schema.ResumeMigrationJobRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object resume {
					def apply(projectsId :PlayApi, locationsId :PlayApi, migrationJobsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): resume = new resume(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/migrationJobs/${migrationJobsId}:resume").addQueryStringParameters("name" -> name.toString))
				}
				class stop(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withStopMigrationJobRequest(body: Schema.StopMigrationJobRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object stop {
					def apply(projectsId :PlayApi, locationsId :PlayApi, migrationJobsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): stop = new stop(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/migrationJobs/${migrationJobsId}:stop").addQueryStringParameters("name" -> name.toString))
				}
				class getIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Policy]) {
					/** Optional. The maximum policy version that will be used to format the policy. Valid values are 0, 1, and 3. Requests specifying an invalid value will be rejected. Requests for policies with any conditional role bindings must specify version 3. Policies with no conditional role bindings may specify any valid value or leave the field unset. The policy in the response might use the policy version that you specified, or it might use a lower policy version. For example, if you specify version 3, but the policy has no conditional role bindings, the response uses version 1. To learn which resources support conditions in their IAM policies, see the [IAM documentation](https://cloud.google.com/iam/help/conditions/resource-policies).<br>Format: int32 */
					def withOptionsRequestedPolicyVersion(optionsRequestedPolicyVersion: Int) = new getIamPolicy(req.addQueryStringParameters("options.requestedPolicyVersion" -> optionsRequestedPolicyVersion.toString))
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Policy])
				}
				object getIamPolicy {
					def apply(projectsId :PlayApi, locationsId :PlayApi, migrationJobsId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): getIamPolicy = new getIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/migrationJobs/${migrationJobsId}:getIamPolicy").addQueryStringParameters("resource" -> resource.toString))
					given Conversion[getIamPolicy, Future[Schema.Policy]] = (fun: getIamPolicy) => fun.apply()
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
					def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Operation])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, migrationJobsId :PlayApi, name: String, requestId: String, force: Boolean)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/migrationJobs/${migrationJobsId}").addQueryStringParameters("name" -> name.toString, "requestId" -> requestId.toString, "force" -> force.toString))
					given Conversion[delete, Future[Schema.Operation]] = (fun: delete) => fun.apply()
				}
				class demoteDestination(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withDemoteDestinationRequest(body: Schema.DemoteDestinationRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object demoteDestination {
					def apply(projectsId :PlayApi, locationsId :PlayApi, migrationJobsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): demoteDestination = new demoteDestination(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/migrationJobs/${migrationJobsId}:demoteDestination").addQueryStringParameters("name" -> name.toString))
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.MigrationJob]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.MigrationJob])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, migrationJobsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/migrationJobs/${migrationJobsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.MigrationJob]] = (fun: get) => fun.apply()
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListMigrationJobsResponse]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListMigrationJobsResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, pageSize: Int, pageToken: String, filter: String, orderBy: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/migrationJobs").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "filter" -> filter.toString, "orderBy" -> orderBy.toString))
					given Conversion[list, Future[Schema.ListMigrationJobsResponse]] = (fun: list) => fun.apply()
				}
				class verify(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withVerifyMigrationJobRequest(body: Schema.VerifyMigrationJobRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object verify {
					def apply(projectsId :PlayApi, locationsId :PlayApi, migrationJobsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): verify = new verify(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/migrationJobs/${migrationJobsId}:verify").addQueryStringParameters("name" -> name.toString))
				}
				class generateTcpProxyScript(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGenerateTcpProxyScriptRequest(body: Schema.GenerateTcpProxyScriptRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.TcpProxyScript])
				}
				object generateTcpProxyScript {
					def apply(projectsId :PlayApi, locationsId :PlayApi, migrationJobsId :PlayApi, migrationJob: String)(using auth: AuthToken, ec: ExecutionContext): generateTcpProxyScript = new generateTcpProxyScript(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/migrationJobs/${migrationJobsId}:generateTcpProxyScript").addQueryStringParameters("migrationJob" -> migrationJob.toString))
				}
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					/** Optional. A unique ID used to identify the request. If the server receives two requests with the same ID, then the second request is ignored. It is recommended to always set this value to a UUID. The ID must contain only letters (a-z, A-Z), numbers (0-9), underscores (_), and hyphens (-). The maximum length is 40 characters. */
					def withRequestId(requestId: String) = new create(req.addQueryStringParameters("requestId" -> requestId.toString))
					def withMigrationJob(body: Schema.MigrationJob) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, migrationJobId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/migrationJobs").addQueryStringParameters("parent" -> parent.toString, "migrationJobId" -> migrationJobId.toString))
				}
				class setIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withSetIamPolicyRequest(body: Schema.SetIamPolicyRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
				}
				object setIamPolicy {
					def apply(projectsId :PlayApi, locationsId :PlayApi, migrationJobsId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): setIamPolicy = new setIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/migrationJobs/${migrationJobsId}:setIamPolicy").addQueryStringParameters("resource" -> resource.toString))
				}
				class promote(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withPromoteMigrationJobRequest(body: Schema.PromoteMigrationJobRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object promote {
					def apply(projectsId :PlayApi, locationsId :PlayApi, migrationJobsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): promote = new promote(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/migrationJobs/${migrationJobsId}:promote").addQueryStringParameters("name" -> name.toString))
				}
				class generateSshScript(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGenerateSshScriptRequest(body: Schema.GenerateSshScriptRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.SshScript])
				}
				object generateSshScript {
					def apply(projectsId :PlayApi, locationsId :PlayApi, migrationJobsId :PlayApi, migrationJob: String)(using auth: AuthToken, ec: ExecutionContext): generateSshScript = new generateSshScript(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/migrationJobs/${migrationJobsId}:generateSshScript").addQueryStringParameters("migrationJob" -> migrationJob.toString))
				}
				class restart(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withRestartMigrationJobRequest(body: Schema.RestartMigrationJobRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object restart {
					def apply(projectsId :PlayApi, locationsId :PlayApi, migrationJobsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): restart = new restart(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/migrationJobs/${migrationJobsId}:restart").addQueryStringParameters("name" -> name.toString))
				}
				class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withMigrationJob(body: Schema.MigrationJob) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Operation])
				}
				object patch {
					def apply(projectsId :PlayApi, locationsId :PlayApi, migrationJobsId :PlayApi, name: String, updateMask: String, requestId: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/migrationJobs/${migrationJobsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString, "requestId" -> requestId.toString))
				}
				class start(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withStartMigrationJobRequest(body: Schema.StartMigrationJobRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object start {
					def apply(projectsId :PlayApi, locationsId :PlayApi, migrationJobsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): start = new start(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/migrationJobs/${migrationJobsId}:start").addQueryStringParameters("name" -> name.toString))
				}
				object objects {
					class setIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withSetIamPolicyRequest(body: Schema.SetIamPolicyRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
					}
					object setIamPolicy {
						def apply(projectsId :PlayApi, locationsId :PlayApi, migrationJobsId :PlayApi, objectsId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): setIamPolicy = new setIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/migrationJobs/${migrationJobsId}/objects/${objectsId}:setIamPolicy").addQueryStringParameters("resource" -> resource.toString))
					}
					class getIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Policy]) {
						/** Optional. The maximum policy version that will be used to format the policy. Valid values are 0, 1, and 3. Requests specifying an invalid value will be rejected. Requests for policies with any conditional role bindings must specify version 3. Policies with no conditional role bindings may specify any valid value or leave the field unset. The policy in the response might use the policy version that you specified, or it might use a lower policy version. For example, if you specify version 3, but the policy has no conditional role bindings, the response uses version 1. To learn which resources support conditions in their IAM policies, see the [IAM documentation](https://cloud.google.com/iam/help/conditions/resource-policies).<br>Format: int32 */
						def withOptionsRequestedPolicyVersion(optionsRequestedPolicyVersion: Int) = new getIamPolicy(req.addQueryStringParameters("options.requestedPolicyVersion" -> optionsRequestedPolicyVersion.toString))
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Policy])
					}
					object getIamPolicy {
						def apply(projectsId :PlayApi, locationsId :PlayApi, migrationJobsId :PlayApi, objectsId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): getIamPolicy = new getIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/migrationJobs/${migrationJobsId}/objects/${objectsId}:getIamPolicy").addQueryStringParameters("resource" -> resource.toString))
						given Conversion[getIamPolicy, Future[Schema.Policy]] = (fun: getIamPolicy) => fun.apply()
					}
					class testIamPermissions(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withTestIamPermissionsRequest(body: Schema.TestIamPermissionsRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.TestIamPermissionsResponse])
					}
					object testIamPermissions {
						def apply(projectsId :PlayApi, locationsId :PlayApi, migrationJobsId :PlayApi, objectsId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): testIamPermissions = new testIamPermissions(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/migrationJobs/${migrationJobsId}/objects/${objectsId}:testIamPermissions").addQueryStringParameters("resource" -> resource.toString))
					}
				}
			}
			object connectionProfiles {
				class testIamPermissions(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withTestIamPermissionsRequest(body: Schema.TestIamPermissionsRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.TestIamPermissionsResponse])
				}
				object testIamPermissions {
					def apply(projectsId :PlayApi, locationsId :PlayApi, connectionProfilesId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): testIamPermissions = new testIamPermissions(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/connectionProfiles/${connectionProfilesId}:testIamPermissions").addQueryStringParameters("resource" -> resource.toString))
				}
				class getIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Policy]) {
					/** Optional. The maximum policy version that will be used to format the policy. Valid values are 0, 1, and 3. Requests specifying an invalid value will be rejected. Requests for policies with any conditional role bindings must specify version 3. Policies with no conditional role bindings may specify any valid value or leave the field unset. The policy in the response might use the policy version that you specified, or it might use a lower policy version. For example, if you specify version 3, but the policy has no conditional role bindings, the response uses version 1. To learn which resources support conditions in their IAM policies, see the [IAM documentation](https://cloud.google.com/iam/help/conditions/resource-policies).<br>Format: int32 */
					def withOptionsRequestedPolicyVersion(optionsRequestedPolicyVersion: Int) = new getIamPolicy(req.addQueryStringParameters("options.requestedPolicyVersion" -> optionsRequestedPolicyVersion.toString))
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Policy])
				}
				object getIamPolicy {
					def apply(projectsId :PlayApi, locationsId :PlayApi, connectionProfilesId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): getIamPolicy = new getIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/connectionProfiles/${connectionProfilesId}:getIamPolicy").addQueryStringParameters("resource" -> resource.toString))
					given Conversion[getIamPolicy, Future[Schema.Policy]] = (fun: getIamPolicy) => fun.apply()
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
					def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Operation])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, connectionProfilesId :PlayApi, name: String, requestId: String, force: Boolean)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/connectionProfiles/${connectionProfilesId}").addQueryStringParameters("name" -> name.toString, "requestId" -> requestId.toString, "force" -> force.toString))
					given Conversion[delete, Future[Schema.Operation]] = (fun: delete) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ConnectionProfile]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ConnectionProfile])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, connectionProfilesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/connectionProfiles/${connectionProfilesId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.ConnectionProfile]] = (fun: get) => fun.apply()
				}
				class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					/** Optional. A unique ID used to identify the request. If the server receives two requests with the same ID, then the second request is ignored. It is recommended to always set this value to a UUID. The ID must contain only letters (a-z, A-Z), numbers (0-9), underscores (_), and hyphens (-). The maximum length is 40 characters. */
					def withRequestId(requestId: String) = new patch(req.addQueryStringParameters("requestId" -> requestId.toString))
					/** Optional. Only validate the connection profile, but don't update any resources. The default is false. Only supported for Oracle connection profiles. */
					def withValidateOnly(validateOnly: Boolean) = new patch(req.addQueryStringParameters("validateOnly" -> validateOnly.toString))
					/** Optional. Update the connection profile without validating it. The default is false. Only supported for Oracle connection profiles. */
					def withSkipValidation(skipValidation: Boolean) = new patch(req.addQueryStringParameters("skipValidation" -> skipValidation.toString))
					def withConnectionProfile(body: Schema.ConnectionProfile) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Operation])
				}
				object patch {
					def apply(projectsId :PlayApi, locationsId :PlayApi, connectionProfilesId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/connectionProfiles/${connectionProfilesId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListConnectionProfilesResponse]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListConnectionProfilesResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, pageSize: Int, pageToken: String, filter: String, orderBy: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/connectionProfiles").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "filter" -> filter.toString, "orderBy" -> orderBy.toString))
					given Conversion[list, Future[Schema.ListConnectionProfilesResponse]] = (fun: list) => fun.apply()
				}
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					/** Optional. A unique ID used to identify the request. If the server receives two requests with the same ID, then the second request is ignored. It is recommended to always set this value to a UUID. The ID must contain only letters (a-z, A-Z), numbers (0-9), underscores (_), and hyphens (-). The maximum length is 40 characters. */
					def withRequestId(requestId: String) = new create(req.addQueryStringParameters("requestId" -> requestId.toString))
					/** Optional. Only validate the connection profile, but don't create any resources. The default is false. Only supported for Oracle connection profiles. */
					def withValidateOnly(validateOnly: Boolean) = new create(req.addQueryStringParameters("validateOnly" -> validateOnly.toString))
					/** Optional. Create the connection profile without validating it. The default is false. Only supported for Oracle connection profiles. */
					def withSkipValidation(skipValidation: Boolean) = new create(req.addQueryStringParameters("skipValidation" -> skipValidation.toString))
					def withConnectionProfile(body: Schema.ConnectionProfile) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, connectionProfileId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/connectionProfiles").addQueryStringParameters("parent" -> parent.toString, "connectionProfileId" -> connectionProfileId.toString))
				}
				class setIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withSetIamPolicyRequest(body: Schema.SetIamPolicyRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
				}
				object setIamPolicy {
					def apply(projectsId :PlayApi, locationsId :PlayApi, connectionProfilesId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): setIamPolicy = new setIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/connectionProfiles/${connectionProfilesId}:setIamPolicy").addQueryStringParameters("resource" -> resource.toString))
				}
			}
		}
	}
}
