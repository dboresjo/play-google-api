package com.boresjo.play.api.google.cloudasset

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

	private val BASE_URL = "https://cloudasset.googleapis.com/"

	object operations {
		/** Gets the latest state of a long-running operation. Clients can use this method to poll the operation result at intervals as recommended by the API service. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Operation])
		}
		object get {
			def apply(v1Id :PlayApi, v1Id1 :PlayApi, operationsId :PlayApi, operationsId1 :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/${v1Id}/${v1Id1}/operations/${operationsId}/${operationsId1}").addQueryStringParameters("name" -> name.toString))
			given Conversion[get, Future[Schema.Operation]] = (fun: get) => fun.apply()
		}
	}
	object v1 {
		/** Exports assets with time and resource types to a given Cloud Storage location/BigQuery table. For Cloud Storage location destinations, the output format is newline-delimited JSON. Each line represents a google.cloud.asset.v1.Asset in the JSON format; for BigQuery table destinations, the output table stores the fields in asset Protobuf as columns. This API implements the google.longrunning.Operation API, which allows you to keep track of the export. We recommend intervals of at least 2 seconds with exponential retry to poll the export operation result. For regular-size resource parent, the export operation usually finishes within 5 minutes. */
		class exportAssets(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def withExportAssetsRequest(body: Schema.ExportAssetsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
		}
		object exportAssets {
			def apply(v1Id :PlayApi, v1Id1 :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): exportAssets = new exportAssets(ws.url(BASE_URL + s"v1/${v1Id}/${v1Id1}:exportAssets").addQueryStringParameters("parent" -> parent.toString))
		}
		/** Analyzes IAM policies asynchronously to answer which identities have what accesses on which resources, and writes the analysis results to a Google Cloud Storage or a BigQuery destination. For Cloud Storage destination, the output format is the JSON format that represents a AnalyzeIamPolicyResponse. This method implements the google.longrunning.Operation, which allows you to track the operation status. We recommend intervals of at least 2 seconds with exponential backoff retry to poll the operation result. The metadata contains the metadata for the long-running operation. */
		class analyzeIamPolicyLongrunning(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def withAnalyzeIamPolicyLongrunningRequest(body: Schema.AnalyzeIamPolicyLongrunningRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
		}
		object analyzeIamPolicyLongrunning {
			def apply(v1Id :PlayApi, v1Id1 :PlayApi, scope: String)(using signer: RequestSigner, ec: ExecutionContext): analyzeIamPolicyLongrunning = new analyzeIamPolicyLongrunning(ws.url(BASE_URL + s"v1/${v1Id}/${v1Id1}:analyzeIamPolicyLongrunning").addQueryStringParameters("scope" -> scope.toString))
		}
		/** Analyzes organization policies under a scope. */
		class analyzeOrgPolicies(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.AnalyzeOrgPoliciesResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.AnalyzeOrgPoliciesResponse])
		}
		object analyzeOrgPolicies {
			def apply(v1Id :PlayApi, v1Id1 :PlayApi, scope: String, constraint: String, filter: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): analyzeOrgPolicies = new analyzeOrgPolicies(ws.url(BASE_URL + s"v1/${v1Id}/${v1Id1}:analyzeOrgPolicies").addQueryStringParameters("scope" -> scope.toString, "constraint" -> constraint.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
			given Conversion[analyzeOrgPolicies, Future[Schema.AnalyzeOrgPoliciesResponse]] = (fun: analyzeOrgPolicies) => fun.apply()
		}
		/** Analyzes IAM policies to answer which identities have what accesses on which resources. */
		class analyzeIamPolicy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.AnalyzeIamPolicyResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Optional. The roles to appear in result. */
			def withAnalysisQueryAccessSelectorRoles(analysisQueryAccessSelectorRoles: String) = new analyzeIamPolicy(req.addQueryStringParameters("analysisQuery.accessSelector.roles" -> analysisQueryAccessSelectorRoles.toString))
			/** Optional. The permissions to appear in result. */
			def withAnalysisQueryAccessSelectorPermissions(analysisQueryAccessSelectorPermissions: String) = new analyzeIamPolicy(req.addQueryStringParameters("analysisQuery.accessSelector.permissions" -> analysisQueryAccessSelectorPermissions.toString))
			/** Optional. If true, the identities section of the result will expand any Google groups appearing in an IAM policy binding. If IamPolicyAnalysisQuery.identity_selector is specified, the identity in the result will be determined by the selector, and this flag is not allowed to set. If true, the default max expansion per group is 1000 for AssetService.AnalyzeIamPolicy][]. Default is false. */
			def withAnalysisQueryOptionsExpandGroups(analysisQueryOptionsExpandGroups: Boolean) = new analyzeIamPolicy(req.addQueryStringParameters("analysisQuery.options.expandGroups" -> analysisQueryOptionsExpandGroups.toString))
			/** Optional. If true, the access section of result will expand any roles appearing in IAM policy bindings to include their permissions. If IamPolicyAnalysisQuery.access_selector is specified, the access section of the result will be determined by the selector, and this flag is not allowed to set. Default is false. */
			def withAnalysisQueryOptionsExpandRoles(analysisQueryOptionsExpandRoles: Boolean) = new analyzeIamPolicy(req.addQueryStringParameters("analysisQuery.options.expandRoles" -> analysisQueryOptionsExpandRoles.toString))
			/** Optional. If true and IamPolicyAnalysisQuery.resource_selector is not specified, the resource section of the result will expand any resource attached to an IAM policy to include resources lower in the resource hierarchy. For example, if the request analyzes for which resources user A has permission P, and the results include an IAM policy with P on a Google Cloud folder, the results will also include resources in that folder with permission P. If true and IamPolicyAnalysisQuery.resource_selector is specified, the resource section of the result will expand the specified resource to include resources lower in the resource hierarchy. Only project or lower resources are supported. Folder and organization resources cannot be used together with this option. For example, if the request analyzes for which users have permission P on a Google Cloud project with this option enabled, the results will include all users who have permission P on that project or any lower resource. If true, the default max expansion per resource is 1000 for AssetService.AnalyzeIamPolicy][] and 100000 for AssetService.AnalyzeIamPolicyLongrunning][]. Default is false. */
			def withAnalysisQueryOptionsExpandResources(analysisQueryOptionsExpandResources: Boolean) = new analyzeIamPolicy(req.addQueryStringParameters("analysisQuery.options.expandResources" -> analysisQueryOptionsExpandResources.toString))
			/** Optional. If true, the result will output the relevant parent/child relationships between resources. Default is false. */
			def withAnalysisQueryOptionsOutputResourceEdges(analysisQueryOptionsOutputResourceEdges: Boolean) = new analyzeIamPolicy(req.addQueryStringParameters("analysisQuery.options.outputResourceEdges" -> analysisQueryOptionsOutputResourceEdges.toString))
			/** Optional. If true, the result will output the relevant membership relationships between groups and other groups, and between groups and principals. Default is false. */
			def withAnalysisQueryOptionsOutputGroupEdges(analysisQueryOptionsOutputGroupEdges: Boolean) = new analyzeIamPolicy(req.addQueryStringParameters("analysisQuery.options.outputGroupEdges" -> analysisQueryOptionsOutputGroupEdges.toString))
			/** Optional. If true, the response will include access analysis from identities to resources via service account impersonation. This is a very expensive operation, because many derived queries will be executed. We highly recommend you use AssetService.AnalyzeIamPolicyLongrunning RPC instead. For example, if the request analyzes for which resources user A has permission P, and there's an IAM policy states user A has iam.serviceAccounts.getAccessToken permission to a service account SA, and there's another IAM policy states service account SA has permission P to a Google Cloud folder F, then user A potentially has access to the Google Cloud folder F. And those advanced analysis results will be included in AnalyzeIamPolicyResponse.service_account_impersonation_analysis. Another example, if the request analyzes for who has permission P to a Google Cloud folder F, and there's an IAM policy states user A has iam.serviceAccounts.actAs permission to a service account SA, and there's another IAM policy states service account SA has permission P to the Google Cloud folder F, then user A potentially has access to the Google Cloud folder F. And those advanced analysis results will be included in AnalyzeIamPolicyResponse.service_account_impersonation_analysis. Only the following permissions are considered in this analysis: &#42; `iam.serviceAccounts.actAs` &#42; `iam.serviceAccounts.signBlob` &#42; `iam.serviceAccounts.signJwt` &#42; `iam.serviceAccounts.getAccessToken` &#42; `iam.serviceAccounts.getOpenIdToken` &#42; `iam.serviceAccounts.implicitDelegation` Default is false. */
			def withAnalysisQueryOptionsAnalyzeServiceAccountImpersonation(analysisQueryOptionsAnalyzeServiceAccountImpersonation: Boolean) = new analyzeIamPolicy(req.addQueryStringParameters("analysisQuery.options.analyzeServiceAccountImpersonation" -> analysisQueryOptionsAnalyzeServiceAccountImpersonation.toString))
			/** Optional. The name of a saved query, which must be in the format of: &#42; projects/project_number/savedQueries/saved_query_id &#42; folders/folder_number/savedQueries/saved_query_id &#42; organizations/organization_number/savedQueries/saved_query_id If both `analysis_query` and `saved_analysis_query` are provided, they will be merged together with the `saved_analysis_query` as base and the `analysis_query` as overrides. For more details of the merge behavior, refer to the [MergeFrom](https://developers.google.com/protocol-buffers/docs/reference/cpp/google.protobuf.message#Message.MergeFrom.details) page. Note that you cannot override primitive fields with default value, such as 0 or empty string, etc., because we use proto3, which doesn't support field presence yet. */
			def withSavedAnalysisQuery(savedAnalysisQuery: String) = new analyzeIamPolicy(req.addQueryStringParameters("savedAnalysisQuery" -> savedAnalysisQuery.toString))
			/** Optional. Amount of time executable has to complete. See JSON representation of [Duration](https://developers.google.com/protocol-buffers/docs/proto3#json). If this field is set with a value less than the RPC deadline, and the execution of your query hasn't finished in the specified execution timeout, you will get a response with partial result. Otherwise, your query's execution will continue until the RPC deadline. If it's not finished until then, you will get a DEADLINE_EXCEEDED error. Default is empty.<br>Format: google-duration */
			def withExecutionTimeout(executionTimeout: String) = new analyzeIamPolicy(req.addQueryStringParameters("executionTimeout" -> executionTimeout.toString))
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.AnalyzeIamPolicyResponse])
		}
		object analyzeIamPolicy {
			def apply(v1Id :PlayApi, v1Id1 :PlayApi, scope: String, analysisQueryResourceSelectorFullResourceName: String, analysisQueryIdentitySelectorIdentity: String, analysisQueryConditionContextAccessTime: String)(using signer: RequestSigner, ec: ExecutionContext): analyzeIamPolicy = new analyzeIamPolicy(ws.url(BASE_URL + s"v1/${v1Id}/${v1Id1}:analyzeIamPolicy").addQueryStringParameters("scope" -> scope.toString, "analysisQuery.resourceSelector.fullResourceName" -> analysisQueryResourceSelectorFullResourceName.toString, "analysisQuery.identitySelector.identity" -> analysisQueryIdentitySelectorIdentity.toString, "analysisQuery.conditionContext.accessTime" -> analysisQueryConditionContextAccessTime.toString))
			given Conversion[analyzeIamPolicy, Future[Schema.AnalyzeIamPolicyResponse]] = (fun: analyzeIamPolicy) => fun.apply()
		}
		/** Analyzes organization policies governed assets (Google Cloud resources or policies) under a scope. This RPC supports custom constraints and the following canned constraints: &#42; constraints/ainotebooks.accessMode &#42; constraints/ainotebooks.disableFileDownloads &#42; constraints/ainotebooks.disableRootAccess &#42; constraints/ainotebooks.disableTerminal &#42; constraints/ainotebooks.environmentOptions &#42; constraints/ainotebooks.requireAutoUpgradeSchedule &#42; constraints/ainotebooks.restrictVpcNetworks &#42; constraints/compute.disableGuestAttributesAccess &#42; constraints/compute.disableInstanceDataAccessApis &#42; constraints/compute.disableNestedVirtualization &#42; constraints/compute.disableSerialPortAccess &#42; constraints/compute.disableSerialPortLogging &#42; constraints/compute.disableVpcExternalIpv6 &#42; constraints/compute.requireOsLogin &#42; constraints/compute.requireShieldedVm &#42; constraints/compute.restrictLoadBalancerCreationForTypes &#42; constraints/compute.restrictProtocolForwardingCreationForTypes &#42; constraints/compute.restrictXpnProjectLienRemoval &#42; constraints/compute.setNewProjectDefaultToZonalDNSOnly &#42; constraints/compute.skipDefaultNetworkCreation &#42; constraints/compute.trustedImageProjects &#42; constraints/compute.vmCanIpForward &#42; constraints/compute.vmExternalIpAccess &#42; constraints/gcp.detailedAuditLoggingMode &#42; constraints/gcp.resourceLocations &#42; constraints/iam.allowedPolicyMemberDomains &#42; constraints/iam.automaticIamGrantsForDefaultServiceAccounts &#42; constraints/iam.disableServiceAccountCreation &#42; constraints/iam.disableServiceAccountKeyCreation &#42; constraints/iam.disableServiceAccountKeyUpload &#42; constraints/iam.restrictCrossProjectServiceAccountLienRemoval &#42; constraints/iam.serviceAccountKeyExpiryHours &#42; constraints/resourcemanager.accessBoundaries &#42; constraints/resourcemanager.allowedExportDestinations &#42; constraints/sql.restrictAuthorizedNetworks &#42; constraints/sql.restrictNoncompliantDiagnosticDataAccess &#42; constraints/sql.restrictNoncompliantResourceCreation &#42; constraints/sql.restrictPublicIp &#42; constraints/storage.publicAccessPrevention &#42; constraints/storage.restrictAuthTypes &#42; constraints/storage.uniformBucketLevelAccess This RPC only returns either resources of types [supported by search APIs](https://cloud.google.com/asset-inventory/docs/supported-asset-types) or IAM policies. */
		class analyzeOrgPolicyGovernedAssets(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.AnalyzeOrgPolicyGovernedAssetsResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.AnalyzeOrgPolicyGovernedAssetsResponse])
		}
		object analyzeOrgPolicyGovernedAssets {
			def apply(v1Id :PlayApi, v1Id1 :PlayApi, scope: String, constraint: String, filter: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): analyzeOrgPolicyGovernedAssets = new analyzeOrgPolicyGovernedAssets(ws.url(BASE_URL + s"v1/${v1Id}/${v1Id1}:analyzeOrgPolicyGovernedAssets").addQueryStringParameters("scope" -> scope.toString, "constraint" -> constraint.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
			given Conversion[analyzeOrgPolicyGovernedAssets, Future[Schema.AnalyzeOrgPolicyGovernedAssetsResponse]] = (fun: analyzeOrgPolicyGovernedAssets) => fun.apply()
		}
		/** Issue a job that queries assets using a SQL statement compatible with [BigQuery SQL](https://cloud.google.com/bigquery/docs/introduction-sql). If the query execution finishes within timeout and there's no pagination, the full query results will be returned in the `QueryAssetsResponse`. Otherwise, full query results can be obtained by issuing extra requests with the `job_reference` from the a previous `QueryAssets` call. Note, the query result has approximately 10 GB limitation enforced by [BigQuery](https://cloud.google.com/bigquery/docs/best-practices-performance-output). Queries return larger results will result in errors. */
		class queryAssets(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def withQueryAssetsRequest(body: Schema.QueryAssetsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.QueryAssetsResponse])
		}
		object queryAssets {
			def apply(v1Id :PlayApi, v1Id1 :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): queryAssets = new queryAssets(ws.url(BASE_URL + s"v1/${v1Id}/${v1Id1}:queryAssets").addQueryStringParameters("parent" -> parent.toString))
		}
		/** Analyzes organization policies governed containers (projects, folders or organization) under a scope. */
		class analyzeOrgPolicyGovernedContainers(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.AnalyzeOrgPolicyGovernedContainersResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.AnalyzeOrgPolicyGovernedContainersResponse])
		}
		object analyzeOrgPolicyGovernedContainers {
			def apply(v1Id :PlayApi, v1Id1 :PlayApi, scope: String, constraint: String, filter: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): analyzeOrgPolicyGovernedContainers = new analyzeOrgPolicyGovernedContainers(ws.url(BASE_URL + s"v1/${v1Id}/${v1Id1}:analyzeOrgPolicyGovernedContainers").addQueryStringParameters("scope" -> scope.toString, "constraint" -> constraint.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
			given Conversion[analyzeOrgPolicyGovernedContainers, Future[Schema.AnalyzeOrgPolicyGovernedContainersResponse]] = (fun: analyzeOrgPolicyGovernedContainers) => fun.apply()
		}
		/** Searches all IAM policies within the specified scope, such as a project, folder, or organization. The caller must be granted the `cloudasset.assets.searchAllIamPolicies` permission on the desired scope, otherwise the request will be rejected. */
		class searchAllIamPolicies(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.SearchAllIamPoliciesResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Optional. The query statement. See [how to construct a query](https://cloud.google.com/asset-inventory/docs/searching-iam-policies#how_to_construct_a_query) for more information. If not specified or empty, it will search all the IAM policies within the specified `scope`. Note that the query string is compared against each IAM policy binding, including its principals, roles, and IAM conditions. The returned IAM policies will only contain the bindings that match your query. To learn more about the IAM policy structure, see the [IAM policy documentation](https://cloud.google.com/iam/help/allow-policies/structure). Examples: &#42; `policy:amy@gmail.com` to find IAM policy bindings that specify user "amy@gmail.com". &#42; `policy:roles/compute.admin` to find IAM policy bindings that specify the Compute Admin role. &#42; `policy:comp&#42;` to find IAM policy bindings that contain "comp" as a prefix of any word in the binding. &#42; `policy.role.permissions:storage.buckets.update` to find IAM policy bindings that specify a role containing "storage.buckets.update" permission. Note that if callers don't have `iam.roles.get` access to a role's included permissions, policy bindings that specify this role will be dropped from the search results. &#42; `policy.role.permissions:upd&#42;` to find IAM policy bindings that specify a role containing "upd" as a prefix of any word in the role permission. Note that if callers don't have `iam.roles.get` access to a role's included permissions, policy bindings that specify this role will be dropped from the search results. &#42; `resource:organizations/123456` to find IAM policy bindings that are set on "organizations/123456". &#42; `resource=//cloudresourcemanager.googleapis.com/projects/myproject` to find IAM policy bindings that are set on the project named "myproject". &#42; `Important` to find IAM policy bindings that contain "Important" as a word in any of the searchable fields (except for the included permissions). &#42; `resource:(instance1 OR instance2) policy:amy` to find IAM policy bindings that are set on resources "instance1" or "instance2" and also specify user "amy". &#42; `roles:roles/compute.admin` to find IAM policy bindings that specify the Compute Admin role. &#42; `memberTypes:user` to find IAM policy bindings that contain the principal type "user". */
			def withQuery(query: String) = new searchAllIamPolicies(req.addQueryStringParameters("query" -> query.toString))
			/** Optional. The page size for search result pagination. Page size is capped at 500 even if a larger value is given. If set to zero or a negative value, server will pick an appropriate default. Returned results may be fewer than requested. When this happens, there could be more results as long as `next_page_token` is returned.<br>Format: int32 */
			def withPageSize(pageSize: Int) = new searchAllIamPolicies(req.addQueryStringParameters("pageSize" -> pageSize.toString))
			/** Optional. If present, retrieve the next batch of results from the preceding call to this method. `page_token` must be the value of `next_page_token` from the previous response. The values of all other method parameters must be identical to those in the previous call. */
			def withPageToken(pageToken: String) = new searchAllIamPolicies(req.addQueryStringParameters("pageToken" -> pageToken.toString))
			/** Optional. A list of asset types that the IAM policies are attached to. If empty, it will search the IAM policies that are attached to all the asset types [supported by search APIs](https://cloud.google.com/asset-inventory/docs/supported-asset-types) Regular expressions are also supported. For example: &#42; "compute.googleapis.com.&#42;" snapshots IAM policies attached to asset type starts with "compute.googleapis.com". &#42; ".&#42;Instance" snapshots IAM policies attached to asset type ends with "Instance". &#42; ".&#42;Instance.&#42;" snapshots IAM policies attached to asset type contains "Instance". See [RE2](https://github.com/google/re2/wiki/Syntax) for all supported regular expression syntax. If the regular expression does not match any supported asset type, an INVALID_ARGUMENT error will be returned. */
			def withAssetTypes(assetTypes: String) = new searchAllIamPolicies(req.addQueryStringParameters("assetTypes" -> assetTypes.toString))
			/** Optional. A comma-separated list of fields specifying the sorting order of the results. The default order is ascending. Add " DESC" after the field name to indicate descending order. Redundant space characters are ignored. Example: "assetType DESC, resource". Only singular primitive fields in the response are sortable: &#42; resource &#42; assetType &#42; project All the other fields such as repeated fields (e.g., `folders`) and non-primitive fields (e.g., `policy`) are not supported. */
			def withOrderBy(orderBy: String) = new searchAllIamPolicies(req.addQueryStringParameters("orderBy" -> orderBy.toString))
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.SearchAllIamPoliciesResponse])
		}
		object searchAllIamPolicies {
			def apply(v1Id :PlayApi, v1Id1 :PlayApi, scope: String)(using signer: RequestSigner, ec: ExecutionContext): searchAllIamPolicies = new searchAllIamPolicies(ws.url(BASE_URL + s"v1/${v1Id}/${v1Id1}:searchAllIamPolicies").addQueryStringParameters("scope" -> scope.toString))
			given Conversion[searchAllIamPolicies, Future[Schema.SearchAllIamPoliciesResponse]] = (fun: searchAllIamPolicies) => fun.apply()
		}
		/** Batch gets the update history of assets that overlap a time window. For IAM_POLICY content, this API outputs history when the asset and its attached IAM POLICY both exist. This can create gaps in the output history. Otherwise, this API outputs history with asset in both non-delete or deleted status. If a specified asset does not exist, this API returns an INVALID_ARGUMENT error. */
		class batchGetAssetsHistory(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.BatchGetAssetsHistoryResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Optional. The content type.<br>Possible values:<br>CONTENT_TYPE_UNSPECIFIED: Unspecified content type.<br>RESOURCE: Resource metadata.<br>IAM_POLICY: The actual IAM policy set on a resource.<br>ORG_POLICY: The organization policy set on an asset.<br>ACCESS_POLICY: The Access Context Manager policy set on an asset.<br>OS_INVENTORY: The runtime OS Inventory information.<br>RELATIONSHIP: The related resources. */
			def withContentType(contentType: String) = new batchGetAssetsHistory(req.addQueryStringParameters("contentType" -> contentType.toString))
			/** Optional. A list of relationship types to output, for example: `INSTANCE_TO_INSTANCEGROUP`. This field should only be specified if content_type=RELATIONSHIP. &#42; If specified: it outputs specified relationships' history on the [asset_names]. It returns an error if any of the [relationship_types] doesn't belong to the supported relationship types of the [asset_names] or if any of the [asset_names]'s types doesn't belong to the source types of the [relationship_types]. &#42; Otherwise: it outputs the supported relationships' history on the [asset_names] or returns an error if any of the [asset_names]'s types has no relationship support. See [Introduction to Cloud Asset Inventory](https://cloud.google.com/asset-inventory/docs/overview) for all supported asset types and relationship types. */
			def withRelationshipTypes(relationshipTypes: String) = new batchGetAssetsHistory(req.addQueryStringParameters("relationshipTypes" -> relationshipTypes.toString))
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.BatchGetAssetsHistoryResponse])
		}
		object batchGetAssetsHistory {
			def apply(v1Id :PlayApi, v1Id1 :PlayApi, parent: String, assetNames: String, readTimeWindowStartTime: String, readTimeWindowEndTime: String)(using signer: RequestSigner, ec: ExecutionContext): batchGetAssetsHistory = new batchGetAssetsHistory(ws.url(BASE_URL + s"v1/${v1Id}/${v1Id1}:batchGetAssetsHistory").addQueryStringParameters("parent" -> parent.toString, "assetNames" -> assetNames.toString, "readTimeWindow.startTime" -> readTimeWindowStartTime.toString, "readTimeWindow.endTime" -> readTimeWindowEndTime.toString))
			given Conversion[batchGetAssetsHistory, Future[Schema.BatchGetAssetsHistoryResponse]] = (fun: batchGetAssetsHistory) => fun.apply()
		}
		/** Analyze moving a resource to a specified destination without kicking off the actual move. The analysis is best effort depending on the user's permissions of viewing different hierarchical policies and configurations. The policies and configuration are subject to change before the actual resource migration takes place. */
		class analyzeMove(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.AnalyzeMoveResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.AnalyzeMoveResponse])
		}
		object analyzeMove {
			def apply(v1Id :PlayApi, v1Id1 :PlayApi, resource: String, destinationParent: String, view: String)(using signer: RequestSigner, ec: ExecutionContext): analyzeMove = new analyzeMove(ws.url(BASE_URL + s"v1/${v1Id}/${v1Id1}:analyzeMove").addQueryStringParameters("resource" -> resource.toString, "destinationParent" -> destinationParent.toString, "view" -> view.toString))
			given Conversion[analyzeMove, Future[Schema.AnalyzeMoveResponse]] = (fun: analyzeMove) => fun.apply()
		}
		/** Searches all Google Cloud resources within the specified scope, such as a project, folder, or organization. The caller must be granted the `cloudasset.assets.searchAllResources` permission on the desired scope, otherwise the request will be rejected. */
		class searchAllResources(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.SearchAllResourcesResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Optional. The query statement. See [how to construct a query](https://cloud.google.com/asset-inventory/docs/searching-resources#how_to_construct_a_query) for more information. If not specified or empty, it will search all the resources within the specified `scope`. Examples: &#42; `name:Important` to find Google Cloud resources whose name contains `Important` as a word. &#42; `name=Important` to find the Google Cloud resource whose name is exactly `Important`. &#42; `displayName:Impor&#42;` to find Google Cloud resources whose display name contains `Impor` as a prefix of any word in the field. &#42; `location:us-west&#42;` to find Google Cloud resources whose location contains both `us` and `west` as prefixes. &#42; `labels:prod` to find Google Cloud resources whose labels contain `prod` as a key or value. &#42; `labels.env:prod` to find Google Cloud resources that have a label `env` and its value is `prod`. &#42; `labels.env:&#42;` to find Google Cloud resources that have a label `env`. &#42; `tagKeys:env` to find Google Cloud resources that have directly attached tags where the [`TagKey.namespacedName`](https://cloud.google.com/resource-manager/reference/rest/v3/tagKeys#resource:-tagkey) contains `env`. &#42; `tagValues:prod&#42;` to find Google Cloud resources that have directly attached tags where the [`TagValue.namespacedName`](https://cloud.google.com/resource-manager/reference/rest/v3/tagValues#resource:-tagvalue) contains a word prefixed by `prod`. &#42; `tagValueIds=tagValues/123` to find Google Cloud resources that have directly attached tags where the [`TagValue.name`](https://cloud.google.com/resource-manager/reference/rest/v3/tagValues#resource:-tagvalue) is exactly `tagValues/123`. &#42; `effectiveTagKeys:env` to find Google Cloud resources that have directly attached or inherited tags where the [`TagKey.namespacedName`](https://cloud.google.com/resource-manager/reference/rest/v3/tagKeys#resource:-tagkey) contains `env`. &#42; `effectiveTagValues:prod&#42;` to find Google Cloud resources that have directly attached or inherited tags where the [`TagValue.namespacedName`](https://cloud.google.com/resource-manager/reference/rest/v3/tagValues#resource:-tagvalue) contains a word prefixed by `prod`. &#42; `effectiveTagValueIds=tagValues/123` to find Google Cloud resources that have directly attached or inherited tags where the [`TagValue.name`](https://cloud.google.com/resource-manager/reference/rest/v3/tagValues#resource:-tagvalue) is exactly `tagValues/123`. &#42; `kmsKey:key` to find Google Cloud resources encrypted with a customer-managed encryption key whose name contains `key` as a word. This field is deprecated. Use the `kmsKeys` field to retrieve Cloud KMS key information. &#42; `kmsKeys:key` to find Google Cloud resources encrypted with customer-managed encryption keys whose name contains the word `key`. &#42; `relationships:instance-group-1` to find Google Cloud resources that have relationships with `instance-group-1` in the related resource name. &#42; `relationships:INSTANCE_TO_INSTANCEGROUP` to find Compute Engine instances that have relationships of type `INSTANCE_TO_INSTANCEGROUP`. &#42; `relationships.INSTANCE_TO_INSTANCEGROUP:instance-group-1` to find Compute Engine instances that have relationships with `instance-group-1` in the Compute Engine instance group resource name, for relationship type `INSTANCE_TO_INSTANCEGROUP`. &#42; `sccSecurityMarks.key=value` to find Cloud resources that are attached with security marks whose key is `key` and value is `value`. &#42; `sccSecurityMarks.key:&#42;` to find Cloud resources that are attached with security marks whose key is `key`. &#42; `state:ACTIVE` to find Google Cloud resources whose state contains `ACTIVE` as a word. &#42; `NOT state:ACTIVE` to find Google Cloud resources whose state doesn't contain `ACTIVE` as a word. &#42; `createTime<1609459200` to find Google Cloud resources that were created before `2021-01-01 00:00:00 UTC`. `1609459200` is the epoch timestamp of `2021-01-01 00:00:00 UTC` in seconds. &#42; `updateTime>1609459200` to find Google Cloud resources that were updated after `2021-01-01 00:00:00 UTC`. `1609459200` is the epoch timestamp of `2021-01-01 00:00:00 UTC` in seconds. &#42; `Important` to find Google Cloud resources that contain `Important` as a word in any of the searchable fields. &#42; `Impor&#42;` to find Google Cloud resources that contain `Impor` as a prefix of any word in any of the searchable fields. &#42; `Important location:(us-west1 OR global)` to find Google Cloud resources that contain `Important` as a word in any of the searchable fields and are also located in the `us-west1` region or the `global` location. */
			def withQuery(query: String) = new searchAllResources(req.addQueryStringParameters("query" -> query.toString))
			/** Optional. A list of asset types that this request searches for. If empty, it will search all the asset types [supported by search APIs](https://cloud.google.com/asset-inventory/docs/supported-asset-types). Regular expressions are also supported. For example: &#42; "compute.googleapis.com.&#42;" snapshots resources whose asset type starts with "compute.googleapis.com". &#42; ".&#42;Instance" snapshots resources whose asset type ends with "Instance". &#42; ".&#42;Instance.&#42;" snapshots resources whose asset type contains "Instance". See [RE2](https://github.com/google/re2/wiki/Syntax) for all supported regular expression syntax. If the regular expression does not match any supported asset type, an INVALID_ARGUMENT error will be returned. */
			def withAssetTypes(assetTypes: String) = new searchAllResources(req.addQueryStringParameters("assetTypes" -> assetTypes.toString))
			/** Optional. The page size for search result pagination. Page size is capped at 500 even if a larger value is given. If set to zero or a negative value, server will pick an appropriate default. Returned results may be fewer than requested. When this happens, there could be more results as long as `next_page_token` is returned.<br>Format: int32 */
			def withPageSize(pageSize: Int) = new searchAllResources(req.addQueryStringParameters("pageSize" -> pageSize.toString))
			/** Optional. If present, then retrieve the next batch of results from the preceding call to this method. `page_token` must be the value of `next_page_token` from the previous response. The values of all other method parameters, must be identical to those in the previous call. */
			def withPageToken(pageToken: String) = new searchAllResources(req.addQueryStringParameters("pageToken" -> pageToken.toString))
			/** Optional. A comma-separated list of fields specifying the sorting order of the results. The default order is ascending. Add " DESC" after the field name to indicate descending order. Redundant space characters are ignored. Example: "location DESC, name". Only the following fields in the response are sortable: &#42; name &#42; assetType &#42; project &#42; displayName &#42; description &#42; location &#42; createTime &#42; updateTime &#42; state &#42; parentFullResourceName &#42; parentAssetType */
			def withOrderBy(orderBy: String) = new searchAllResources(req.addQueryStringParameters("orderBy" -> orderBy.toString))
			/** Optional. A comma-separated list of fields that you want returned in the results. The following fields are returned by default if not specified: &#42; `name` &#42; `assetType` &#42; `project` &#42; `folders` &#42; `organization` &#42; `displayName` &#42; `description` &#42; `location` &#42; `labels` &#42; `tags` &#42; `effectiveTags` &#42; `networkTags` &#42; `kmsKeys` &#42; `createTime` &#42; `updateTime` &#42; `state` &#42; `additionalAttributes` &#42; `parentFullResourceName` &#42; `parentAssetType` Some fields of large size, such as `versionedResources`, `attachedResources`, `effectiveTags` etc., are not returned by default, but you can specify them in the `read_mask` parameter if you want to include them. If `"&#42;"` is specified, all [available fields](https://cloud.google.com/asset-inventory/docs/reference/rest/v1/TopLevel/searchAllResources#resourcesearchresult) are returned. Examples: `"name,location"`, `"name,versionedResources"`, `"&#42;"`. Any invalid field path will trigger INVALID_ARGUMENT error.<br>Format: google-fieldmask */
			def withReadMask(readMask: String) = new searchAllResources(req.addQueryStringParameters("readMask" -> readMask.toString))
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.SearchAllResourcesResponse])
		}
		object searchAllResources {
			def apply(v1Id :PlayApi, v1Id1 :PlayApi, scope: String)(using signer: RequestSigner, ec: ExecutionContext): searchAllResources = new searchAllResources(ws.url(BASE_URL + s"v1/${v1Id}/${v1Id1}:searchAllResources").addQueryStringParameters("scope" -> scope.toString))
			given Conversion[searchAllResources, Future[Schema.SearchAllResourcesResponse]] = (fun: searchAllResources) => fun.apply()
		}
	}
	object assets {
		/** Lists assets with time and resource types and returns paged results in response. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListAssetsResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListAssetsResponse])
		}
		object list {
			def apply(v1Id :PlayApi, v1Id1 :PlayApi, parent: String, readTime: String, assetTypes: String, contentType: String, pageSize: Int, pageToken: String, relationshipTypes: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/${v1Id}/${v1Id1}/assets").addQueryStringParameters("parent" -> parent.toString, "readTime" -> readTime.toString, "assetTypes" -> assetTypes.toString, "contentType" -> contentType.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "relationshipTypes" -> relationshipTypes.toString))
			given Conversion[list, Future[Schema.ListAssetsResponse]] = (fun: list) => fun.apply()
		}
	}
	object feeds {
		/** Creates a feed in a parent project/folder/organization to listen to its asset updates. */
		class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def withCreateFeedRequest(body: Schema.CreateFeedRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Feed])
		}
		object create {
			def apply(v1Id :PlayApi, v1Id1 :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/${v1Id}/${v1Id1}/feeds").addQueryStringParameters("parent" -> parent.toString))
		}
		/** Deletes an asset feed. */
		class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
		}
		object delete {
			def apply(v1Id :PlayApi, v1Id1 :PlayApi, feedsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/${v1Id}/${v1Id1}/feeds/${feedsId}").addQueryStringParameters("name" -> name.toString))
			given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
		}
		/** Gets details about an asset feed. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Feed]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Feed])
		}
		object get {
			def apply(v1Id :PlayApi, v1Id1 :PlayApi, feedsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/${v1Id}/${v1Id1}/feeds/${feedsId}").addQueryStringParameters("name" -> name.toString))
			given Conversion[get, Future[Schema.Feed]] = (fun: get) => fun.apply()
		}
		/** Updates an asset feed configuration. */
		class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def withUpdateFeedRequest(body: Schema.UpdateFeedRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Feed])
		}
		object patch {
			def apply(v1Id :PlayApi, v1Id1 :PlayApi, feedsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/${v1Id}/${v1Id1}/feeds/${feedsId}").addQueryStringParameters("name" -> name.toString))
		}
		/** Lists all asset feeds in a parent project/folder/organization. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListFeedsResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListFeedsResponse])
		}
		object list {
			def apply(v1Id :PlayApi, v1Id1 :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/${v1Id}/${v1Id1}/feeds").addQueryStringParameters("parent" -> parent.toString))
			given Conversion[list, Future[Schema.ListFeedsResponse]] = (fun: list) => fun.apply()
		}
	}
	object savedQueries {
		/** Creates a saved query in a parent project/folder/organization. */
		class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def withSavedQuery(body: Schema.SavedQuery) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.SavedQuery])
		}
		object create {
			def apply(v1Id :PlayApi, v1Id1 :PlayApi, parent: String, savedQueryId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/${v1Id}/${v1Id1}/savedQueries").addQueryStringParameters("parent" -> parent.toString, "savedQueryId" -> savedQueryId.toString))
		}
		/** Deletes a saved query. */
		class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
		}
		object delete {
			def apply(v1Id :PlayApi, v1Id1 :PlayApi, savedQueriesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/${v1Id}/${v1Id1}/savedQueries/${savedQueriesId}").addQueryStringParameters("name" -> name.toString))
			given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
		}
		/** Gets details about a saved query. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.SavedQuery]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.SavedQuery])
		}
		object get {
			def apply(v1Id :PlayApi, v1Id1 :PlayApi, savedQueriesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/${v1Id}/${v1Id1}/savedQueries/${savedQueriesId}").addQueryStringParameters("name" -> name.toString))
			given Conversion[get, Future[Schema.SavedQuery]] = (fun: get) => fun.apply()
		}
		/** Updates a saved query. */
		class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def withSavedQuery(body: Schema.SavedQuery) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.SavedQuery])
		}
		object patch {
			def apply(v1Id :PlayApi, v1Id1 :PlayApi, savedQueriesId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/${v1Id}/${v1Id1}/savedQueries/${savedQueriesId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
		}
		/** Lists all saved queries in a parent project/folder/organization. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListSavedQueriesResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Optional. The expression to filter resources. The expression is a list of zero or more restrictions combined via logical operators `AND` and `OR`. When `AND` and `OR` are both used in the expression, parentheses must be appropriately used to group the combinations. The expression may also contain regular expressions. See https://google.aip.dev/160 for more information on the grammar. */
			def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
			/** Optional. The maximum number of saved queries to return per page. The service may return fewer than this value. If unspecified, at most 50 will be returned. The maximum value is 1000; values above 1000 will be coerced to 1000.<br>Format: int32 */
			def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
			/** Optional. A page token, received from a previous `ListSavedQueries` call. Provide this to retrieve the subsequent page. When paginating, all other parameters provided to `ListSavedQueries` must match the call that provided the page token. */
			def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListSavedQueriesResponse])
		}
		object list {
			def apply(v1Id :PlayApi, v1Id1 :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/${v1Id}/${v1Id1}/savedQueries").addQueryStringParameters("parent" -> parent.toString))
			given Conversion[list, Future[Schema.ListSavedQueriesResponse]] = (fun: list) => fun.apply()
		}
	}
	object effectiveIamPolicies {
		/** Gets effective IAM policies for a batch of resources. */
		class batchGet(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.BatchGetEffectiveIamPoliciesResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.BatchGetEffectiveIamPoliciesResponse])
		}
		object batchGet {
			def apply(v1Id :PlayApi, v1Id1 :PlayApi, scope: String, names: String)(using signer: RequestSigner, ec: ExecutionContext): batchGet = new batchGet(ws.url(BASE_URL + s"v1/${v1Id}/${v1Id1}/effectiveIamPolicies:batchGet").addQueryStringParameters("scope" -> scope.toString, "names" -> names.toString))
			given Conversion[batchGet, Future[Schema.BatchGetEffectiveIamPoliciesResponse]] = (fun: batchGet) => fun.apply()
		}
	}
}
