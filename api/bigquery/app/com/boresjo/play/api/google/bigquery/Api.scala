package com.boresjo.play.api.google.bigquery

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://bigquery.googleapis.com/bigquery/v2/"

	object datasets {
		class undelete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withUndeleteDatasetRequest(body: Schema.UndeleteDatasetRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Dataset])
		}
		object undelete {
			def apply(projectsId :PlayApi, datasetsId :PlayApi, datasetId: String, projectId: String)(using auth: AuthToken, ec: ExecutionContext): undelete = new undelete(ws.url(BASE_URL + s"projects/${projectsId}/datasets/${datasetsId}:undelete").addQueryStringParameters("datasetId" -> datasetId.toString, "projectId" -> projectId.toString))
		}
		class insert(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			/** Optional. The version of the provided access policy schema. Valid values are 0, 1, and 3. Requests specifying an invalid value will be rejected. This version refers to the schema version of the access policy and not the version of access policy. This field's value can be equal or more than the access policy schema provided in the request. For example, &#42; Requests with conditional access policy binding in datasets must specify version 3. &#42; But dataset with no conditional role bindings in access policy may specify any valid value or leave the field unset. If unset or if 0 or 1 value is used for dataset with conditional bindings, request will be rejected. This field will be maped to IAM Policy version (https://cloud.google.com/iam/docs/policies#versions) and will be used to set policy in IAM.<br>Format: int32 */
			def withAccessPolicyVersion(accessPolicyVersion: Int) = new insert(req.addQueryStringParameters("accessPolicyVersion" -> accessPolicyVersion.toString))
			def withDataset(body: Schema.Dataset) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Dataset])
		}
		object insert {
			def apply(projectsId :PlayApi, projectId: String)(using auth: AuthToken, ec: ExecutionContext): insert = new insert(ws.url(BASE_URL + s"projects/${projectsId}/datasets").addQueryStringParameters("projectId" -> projectId.toString))
		}
		class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Unit]) {
			def apply() = auth.exec(req,_.execute("DELETE")).map(_ => ())
		}
		object delete {
			def apply(projectsId :PlayApi, datasetsId :PlayApi, datasetId: String, deleteContents: Boolean, projectId: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"projects/${projectsId}/datasets/${datasetsId}").addQueryStringParameters("datasetId" -> datasetId.toString, "deleteContents" -> deleteContents.toString, "projectId" -> projectId.toString))
			given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
		}
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Dataset]) {
			/** Optional. The version of the access policy schema to fetch. Valid values are 0, 1, and 3. Requests specifying an invalid value will be rejected. Requests for conditional access policy binding in datasets must specify version 3. Dataset with no conditional role bindings in access policy may specify any valid value or leave the field unset. This field will be maped to [IAM Policy version] (https://cloud.google.com/iam/docs/policies#versions) and will be used to fetch policy from IAM. If unset or if 0 or 1 value is used for dataset with conditional bindings, access entry with condition will have role string appended by 'withcond' string followed by a hash value. For example : { "access": [ { "role": "roles/bigquery.dataViewer_with_conditionalbinding_7a34awqsda", "userByEmail": "user@example.com", } ] } Please refer https://cloud.google.com/iam/docs/troubleshooting-withcond for more details.<br>Format: int32 */
			def withAccessPolicyVersion(accessPolicyVersion: Int) = new get(req.addQueryStringParameters("accessPolicyVersion" -> accessPolicyVersion.toString))
			/** Optional. Specifies the view that determines which dataset information is returned. By default, metadata and ACL information are returned.<br>Possible values:<br>DATASET_VIEW_UNSPECIFIED: The default value. Default to the FULL view.<br>METADATA: Includes metadata information for the dataset, such as location, etag, lastModifiedTime, etc.<br>ACL: Includes ACL information for the dataset, which defines dataset access for one or more entities.<br>FULL: Includes both dataset metadata and ACL information. */
			def withDatasetView(datasetView: String) = new get(req.addQueryStringParameters("datasetView" -> datasetView.toString))
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Dataset])
		}
		object get {
			def apply(projectsId :PlayApi, datasetsId :PlayApi, datasetId: String, projectId: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"projects/${projectsId}/datasets/${datasetsId}").addQueryStringParameters("datasetId" -> datasetId.toString, "projectId" -> projectId.toString))
			given Conversion[get, Future[Schema.Dataset]] = (fun: get) => fun.apply()
		}
		class update(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			/** Optional. The version of the provided access policy schema. Valid values are 0, 1, and 3. Requests specifying an invalid value will be rejected. This version refers to the schema version of the access policy and not the version of access policy. This field's value can be equal or more than the access policy schema provided in the request. For example, &#42; Operations updating conditional access policy binding in datasets must specify version 3. Some of the operations are : - Adding a new access policy entry with condition. - Removing an access policy entry with condition. - Updating an access policy entry with condition. &#42; But dataset with no conditional role bindings in access policy may specify any valid value or leave the field unset. If unset or if 0 or 1 value is used for dataset with conditional bindings, request will be rejected. This field will be maped to IAM Policy version (https://cloud.google.com/iam/docs/policies#versions) and will be used to set policy in IAM.<br>Format: int32 */
			def withAccessPolicyVersion(accessPolicyVersion: Int) = new update(req.addQueryStringParameters("accessPolicyVersion" -> accessPolicyVersion.toString))
			def withDataset(body: Schema.Dataset) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.Dataset])
		}
		object update {
			def apply(projectsId :PlayApi, datasetsId :PlayApi, datasetId: String, projectId: String)(using auth: AuthToken, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"projects/${projectsId}/datasets/${datasetsId}").addQueryStringParameters("datasetId" -> datasetId.toString, "projectId" -> projectId.toString))
		}
		class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			/** Optional. The version of the provided access policy schema. Valid values are 0, 1, and 3. Requests specifying an invalid value will be rejected. This version refers to the schema version of the access policy and not the version of access policy. This field's value can be equal or more than the access policy schema provided in the request. For example, &#42; Operations updating conditional access policy binding in datasets must specify version 3. Some of the operations are : - Adding a new access policy entry with condition. - Removing an access policy entry with condition. - Updating an access policy entry with condition. &#42; But dataset with no conditional role bindings in access policy may specify any valid value or leave the field unset. If unset or if 0 or 1 value is used for dataset with conditional bindings, request will be rejected. This field will be maped to IAM Policy version (https://cloud.google.com/iam/docs/policies#versions) and will be used to set policy in IAM.<br>Format: int32 */
			def withAccessPolicyVersion(accessPolicyVersion: Int) = new patch(req.addQueryStringParameters("accessPolicyVersion" -> accessPolicyVersion.toString))
			def withDataset(body: Schema.Dataset) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Dataset])
		}
		object patch {
			def apply(projectsId :PlayApi, datasetsId :PlayApi, datasetId: String, projectId: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"projects/${projectsId}/datasets/${datasetsId}").addQueryStringParameters("datasetId" -> datasetId.toString, "projectId" -> projectId.toString))
		}
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.DatasetList]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.DatasetList])
		}
		object list {
			def apply(projectsId :PlayApi, all: Boolean, filter: String, maxResults: Int, pageToken: String, projectId: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"projects/${projectsId}/datasets").addQueryStringParameters("all" -> all.toString, "filter" -> filter.toString, "maxResults" -> maxResults.toString, "pageToken" -> pageToken.toString, "projectId" -> projectId.toString))
			given Conversion[list, Future[Schema.DatasetList]] = (fun: list) => fun.apply()
		}
	}
	object jobs {
		class cancel(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.JobCancelResponse]) {
			def apply() = auth.exec(req,_.execute("POST")).map(_.json.as[Schema.JobCancelResponse])
		}
		object cancel {
			def apply(projectsId :PlayApi, jobsId :PlayApi, jobId: String, location: String, projectId: String)(using auth: AuthToken, ec: ExecutionContext): cancel = new cancel(ws.url(BASE_URL + s"projects/${projectsId}/jobs/${jobsId}/cancel").addQueryStringParameters("jobId" -> jobId.toString, "location" -> location.toString, "projectId" -> projectId.toString))
			given Conversion[cancel, Future[Schema.JobCancelResponse]] = (fun: cancel) => fun.apply()
		}
		class insert(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withJob(body: Schema.Job) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Job])
		}
		object insert {
			def apply(projectsId :PlayApi, projectId: String)(using auth: AuthToken, ec: ExecutionContext): insert = new insert(ws.url(BASE_URL + s"projects/${projectsId}/jobs").addQueryStringParameters("projectId" -> projectId.toString))
		}
		class query(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withQueryRequest(body: Schema.QueryRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.QueryResponse])
		}
		object query {
			def apply(projectsId :PlayApi, projectId: String)(using auth: AuthToken, ec: ExecutionContext): query = new query(ws.url(BASE_URL + s"projects/${projectsId}/queries").addQueryStringParameters("projectId" -> projectId.toString))
		}
		class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Unit]) {
			def apply() = auth.exec(req,_.execute("DELETE")).map(_ => ())
		}
		object delete {
			def apply(projectsId :PlayApi, jobsId :PlayApi, jobId: String, location: String, projectId: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"projects/${projectsId}/jobs/${jobsId}/delete").addQueryStringParameters("jobId" -> jobId.toString, "location" -> location.toString, "projectId" -> projectId.toString))
			given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
		}
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Job]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Job])
		}
		object get {
			def apply(projectsId :PlayApi, jobsId :PlayApi, jobId: String, location: String, projectId: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"projects/${projectsId}/jobs/${jobsId}").addQueryStringParameters("jobId" -> jobId.toString, "location" -> location.toString, "projectId" -> projectId.toString))
			given Conversion[get, Future[Schema.Job]] = (fun: get) => fun.apply()
		}
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.JobList]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.JobList])
		}
		object list {
			def apply(projectsId :PlayApi, allUsers: Boolean, maxCreationTime: String, maxResults: Int, minCreationTime: String, pageToken: String, parentJobId: String, projectId: String, projection: String, stateFilter: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"projects/${projectsId}/jobs").addQueryStringParameters("allUsers" -> allUsers.toString, "maxCreationTime" -> maxCreationTime.toString, "maxResults" -> maxResults.toString, "minCreationTime" -> minCreationTime.toString, "pageToken" -> pageToken.toString, "parentJobId" -> parentJobId.toString, "projectId" -> projectId.toString, "projection" -> projection.toString, "stateFilter" -> stateFilter.toString))
			given Conversion[list, Future[Schema.JobList]] = (fun: list) => fun.apply()
		}
		class getQueryResults(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GetQueryResultsResponse]) {
			/** Optional. Output timestamp as usec int64. Default is false. */
			def withFormatOptionsUseInt64Timestamp(formatOptionsUseInt64Timestamp: Boolean) = new getQueryResults(req.addQueryStringParameters("formatOptions.useInt64Timestamp" -> formatOptionsUseInt64Timestamp.toString))
			/** Optional: Specifies the maximum amount of time, in milliseconds, that the client is willing to wait for the query to complete. By default, this limit is 10 seconds (10,000 milliseconds). If the query is complete, the jobComplete field in the response is true. If the query has not yet completed, jobComplete is false. You can request a longer timeout period in the timeoutMs field. However, the call is not guaranteed to wait for the specified timeout; it typically returns after around 200 seconds (200,000 milliseconds), even if the query is not complete. If jobComplete is false, you can continue to wait for the query to complete by calling the getQueryResults method until the jobComplete field in the getQueryResults response is true.<br>Format: uint32 */
			def withTimeoutMs(timeoutMs: Int) = new getQueryResults(req.addQueryStringParameters("timeoutMs" -> timeoutMs.toString))
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GetQueryResultsResponse])
		}
		object getQueryResults {
			def apply(projectsId :PlayApi, queriesId :PlayApi, jobId: String, location: String, maxResults: Int, pageToken: String, projectId: String, startIndex: String)(using auth: AuthToken, ec: ExecutionContext): getQueryResults = new getQueryResults(ws.url(BASE_URL + s"projects/${projectsId}/queries/${queriesId}").addQueryStringParameters("jobId" -> jobId.toString, "location" -> location.toString, "maxResults" -> maxResults.toString, "pageToken" -> pageToken.toString, "projectId" -> projectId.toString, "startIndex" -> startIndex.toString))
			given Conversion[getQueryResults, Future[Schema.GetQueryResultsResponse]] = (fun: getQueryResults) => fun.apply()
		}
	}
	object models {
		class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Unit]) {
			def apply() = auth.exec(req,_.execute("DELETE")).map(_ => ())
		}
		object delete {
			def apply(projectsId :PlayApi, datasetsId :PlayApi, modelsId :PlayApi, datasetId: String, modelId: String, projectId: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"projects/${projectsId}/datasets/${datasetsId}/models/${modelsId}").addQueryStringParameters("datasetId" -> datasetId.toString, "modelId" -> modelId.toString, "projectId" -> projectId.toString))
			given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
		}
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Model]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Model])
		}
		object get {
			def apply(projectsId :PlayApi, datasetsId :PlayApi, modelsId :PlayApi, datasetId: String, modelId: String, projectId: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"projects/${projectsId}/datasets/${datasetsId}/models/${modelsId}").addQueryStringParameters("datasetId" -> datasetId.toString, "modelId" -> modelId.toString, "projectId" -> projectId.toString))
			given Conversion[get, Future[Schema.Model]] = (fun: get) => fun.apply()
		}
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListModelsResponse]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListModelsResponse])
		}
		object list {
			def apply(projectsId :PlayApi, datasetsId :PlayApi, datasetId: String, maxResults: Int, pageToken: String, projectId: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"projects/${projectsId}/datasets/${datasetsId}/models").addQueryStringParameters("datasetId" -> datasetId.toString, "maxResults" -> maxResults.toString, "pageToken" -> pageToken.toString, "projectId" -> projectId.toString))
			given Conversion[list, Future[Schema.ListModelsResponse]] = (fun: list) => fun.apply()
		}
		class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withModel(body: Schema.Model) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Model])
		}
		object patch {
			def apply(projectsId :PlayApi, datasetsId :PlayApi, modelsId :PlayApi, datasetId: String, modelId: String, projectId: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"projects/${projectsId}/datasets/${datasetsId}/models/${modelsId}").addQueryStringParameters("datasetId" -> datasetId.toString, "modelId" -> modelId.toString, "projectId" -> projectId.toString))
		}
	}
	object projects {
		class getServiceAccount(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GetServiceAccountResponse]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GetServiceAccountResponse])
		}
		object getServiceAccount {
			def apply(projectsId :PlayApi, projectId: String)(using auth: AuthToken, ec: ExecutionContext): getServiceAccount = new getServiceAccount(ws.url(BASE_URL + s"projects/${projectsId}/serviceAccount").addQueryStringParameters("projectId" -> projectId.toString))
			given Conversion[getServiceAccount, Future[Schema.GetServiceAccountResponse]] = (fun: getServiceAccount) => fun.apply()
		}
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ProjectList]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ProjectList])
		}
		object list {
			def apply(maxResults: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"projects").addQueryStringParameters("maxResults" -> maxResults.toString, "pageToken" -> pageToken.toString))
			given Conversion[list, Future[Schema.ProjectList]] = (fun: list) => fun.apply()
		}
	}
	object routines {
		class insert(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withRoutine(body: Schema.Routine) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Routine])
		}
		object insert {
			def apply(projectsId :PlayApi, datasetsId :PlayApi, datasetId: String, projectId: String)(using auth: AuthToken, ec: ExecutionContext): insert = new insert(ws.url(BASE_URL + s"projects/${projectsId}/datasets/${datasetsId}/routines").addQueryStringParameters("datasetId" -> datasetId.toString, "projectId" -> projectId.toString))
		}
		class setIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withSetIamPolicyRequest(body: Schema.SetIamPolicyRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
		}
		object setIamPolicy {
			def apply(projectsId :PlayApi, datasetsId :PlayApi, routinesId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): setIamPolicy = new setIamPolicy(ws.url(BASE_URL + s"projects/${projectsId}/datasets/${datasetsId}/routines/${routinesId}:setIamPolicy").addQueryStringParameters("resource" -> resource.toString))
		}
		class getIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withGetIamPolicyRequest(body: Schema.GetIamPolicyRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
		}
		object getIamPolicy {
			def apply(projectsId :PlayApi, datasetsId :PlayApi, routinesId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): getIamPolicy = new getIamPolicy(ws.url(BASE_URL + s"projects/${projectsId}/datasets/${datasetsId}/routines/${routinesId}:getIamPolicy").addQueryStringParameters("resource" -> resource.toString))
		}
		class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Unit]) {
			def apply() = auth.exec(req,_.execute("DELETE")).map(_ => ())
		}
		object delete {
			def apply(projectsId :PlayApi, datasetsId :PlayApi, routinesId :PlayApi, datasetId: String, projectId: String, routineId: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"projects/${projectsId}/datasets/${datasetsId}/routines/${routinesId}").addQueryStringParameters("datasetId" -> datasetId.toString, "projectId" -> projectId.toString, "routineId" -> routineId.toString))
			given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
		}
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Routine]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Routine])
		}
		object get {
			def apply(projectsId :PlayApi, datasetsId :PlayApi, routinesId :PlayApi, datasetId: String, projectId: String, readMask: String, routineId: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"projects/${projectsId}/datasets/${datasetsId}/routines/${routinesId}").addQueryStringParameters("datasetId" -> datasetId.toString, "projectId" -> projectId.toString, "readMask" -> readMask.toString, "routineId" -> routineId.toString))
			given Conversion[get, Future[Schema.Routine]] = (fun: get) => fun.apply()
		}
		class update(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withRoutine(body: Schema.Routine) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.Routine])
		}
		object update {
			def apply(projectsId :PlayApi, datasetsId :PlayApi, routinesId :PlayApi, datasetId: String, projectId: String, routineId: String)(using auth: AuthToken, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"projects/${projectsId}/datasets/${datasetsId}/routines/${routinesId}").addQueryStringParameters("datasetId" -> datasetId.toString, "projectId" -> projectId.toString, "routineId" -> routineId.toString))
		}
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListRoutinesResponse]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListRoutinesResponse])
		}
		object list {
			def apply(projectsId :PlayApi, datasetsId :PlayApi, datasetId: String, filter: String, maxResults: Int, pageToken: String, projectId: String, readMask: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"projects/${projectsId}/datasets/${datasetsId}/routines").addQueryStringParameters("datasetId" -> datasetId.toString, "filter" -> filter.toString, "maxResults" -> maxResults.toString, "pageToken" -> pageToken.toString, "projectId" -> projectId.toString, "readMask" -> readMask.toString))
			given Conversion[list, Future[Schema.ListRoutinesResponse]] = (fun: list) => fun.apply()
		}
	}
	object rowAccessPolicies {
		class getIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withGetIamPolicyRequest(body: Schema.GetIamPolicyRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
		}
		object getIamPolicy {
			def apply(projectsId :PlayApi, datasetsId :PlayApi, tablesId :PlayApi, rowAccessPoliciesId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): getIamPolicy = new getIamPolicy(ws.url(BASE_URL + s"projects/${projectsId}/datasets/${datasetsId}/tables/${tablesId}/rowAccessPolicies/${rowAccessPoliciesId}:getIamPolicy").addQueryStringParameters("resource" -> resource.toString))
		}
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListRowAccessPoliciesResponse]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListRowAccessPoliciesResponse])
		}
		object list {
			def apply(projectsId :PlayApi, datasetsId :PlayApi, tablesId :PlayApi, datasetId: String, pageSize: Int, pageToken: String, projectId: String, tableId: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"projects/${projectsId}/datasets/${datasetsId}/tables/${tablesId}/rowAccessPolicies").addQueryStringParameters("datasetId" -> datasetId.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "projectId" -> projectId.toString, "tableId" -> tableId.toString))
			given Conversion[list, Future[Schema.ListRowAccessPoliciesResponse]] = (fun: list) => fun.apply()
		}
		class testIamPermissions(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withTestIamPermissionsRequest(body: Schema.TestIamPermissionsRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.TestIamPermissionsResponse])
		}
		object testIamPermissions {
			def apply(projectsId :PlayApi, datasetsId :PlayApi, tablesId :PlayApi, rowAccessPoliciesId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): testIamPermissions = new testIamPermissions(ws.url(BASE_URL + s"projects/${projectsId}/datasets/${datasetsId}/tables/${tablesId}/rowAccessPolicies/${rowAccessPoliciesId}:testIamPermissions").addQueryStringParameters("resource" -> resource.toString))
		}
	}
	object tabledata {
		class insertAll(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withTableDataInsertAllRequest(body: Schema.TableDataInsertAllRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.TableDataInsertAllResponse])
		}
		object insertAll {
			def apply(projectsId :PlayApi, datasetsId :PlayApi, tablesId :PlayApi, datasetId: String, projectId: String, tableId: String)(using auth: AuthToken, ec: ExecutionContext): insertAll = new insertAll(ws.url(BASE_URL + s"projects/${projectsId}/datasets/${datasetsId}/tables/${tablesId}/insertAll").addQueryStringParameters("datasetId" -> datasetId.toString, "projectId" -> projectId.toString, "tableId" -> tableId.toString))
		}
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.TableDataList]) {
			/** Optional. Output timestamp as usec int64. Default is false. */
			def withFormatOptionsUseInt64Timestamp(formatOptionsUseInt64Timestamp: Boolean) = new list(req.addQueryStringParameters("formatOptions.useInt64Timestamp" -> formatOptionsUseInt64Timestamp.toString))
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.TableDataList])
		}
		object list {
			def apply(projectsId :PlayApi, datasetsId :PlayApi, tablesId :PlayApi, datasetId: String, maxResults: Int, pageToken: String, projectId: String, selectedFields: String, startIndex: String, tableId: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"projects/${projectsId}/datasets/${datasetsId}/tables/${tablesId}/data").addQueryStringParameters("datasetId" -> datasetId.toString, "maxResults" -> maxResults.toString, "pageToken" -> pageToken.toString, "projectId" -> projectId.toString, "selectedFields" -> selectedFields.toString, "startIndex" -> startIndex.toString, "tableId" -> tableId.toString))
			given Conversion[list, Future[Schema.TableDataList]] = (fun: list) => fun.apply()
		}
	}
	object tables {
		class testIamPermissions(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withTestIamPermissionsRequest(body: Schema.TestIamPermissionsRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.TestIamPermissionsResponse])
		}
		object testIamPermissions {
			def apply(projectsId :PlayApi, datasetsId :PlayApi, tablesId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): testIamPermissions = new testIamPermissions(ws.url(BASE_URL + s"projects/${projectsId}/datasets/${datasetsId}/tables/${tablesId}:testIamPermissions").addQueryStringParameters("resource" -> resource.toString))
		}
		class insert(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withTable(body: Schema.Table) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Table])
		}
		object insert {
			def apply(projectsId :PlayApi, datasetsId :PlayApi, datasetId: String, projectId: String)(using auth: AuthToken, ec: ExecutionContext): insert = new insert(ws.url(BASE_URL + s"projects/${projectsId}/datasets/${datasetsId}/tables").addQueryStringParameters("datasetId" -> datasetId.toString, "projectId" -> projectId.toString))
		}
		class setIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withSetIamPolicyRequest(body: Schema.SetIamPolicyRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
		}
		object setIamPolicy {
			def apply(projectsId :PlayApi, datasetsId :PlayApi, tablesId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): setIamPolicy = new setIamPolicy(ws.url(BASE_URL + s"projects/${projectsId}/datasets/${datasetsId}/tables/${tablesId}:setIamPolicy").addQueryStringParameters("resource" -> resource.toString))
		}
		class getIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withGetIamPolicyRequest(body: Schema.GetIamPolicyRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
		}
		object getIamPolicy {
			def apply(projectsId :PlayApi, datasetsId :PlayApi, tablesId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): getIamPolicy = new getIamPolicy(ws.url(BASE_URL + s"projects/${projectsId}/datasets/${datasetsId}/tables/${tablesId}:getIamPolicy").addQueryStringParameters("resource" -> resource.toString))
		}
		class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Unit]) {
			def apply() = auth.exec(req,_.execute("DELETE")).map(_ => ())
		}
		object delete {
			def apply(projectsId :PlayApi, datasetsId :PlayApi, tablesId :PlayApi, datasetId: String, projectId: String, tableId: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"projects/${projectsId}/datasets/${datasetsId}/tables/${tablesId}").addQueryStringParameters("datasetId" -> datasetId.toString, "projectId" -> projectId.toString, "tableId" -> tableId.toString))
			given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
		}
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Table]) {
			/** Optional. Specifies the view that determines which table information is returned. By default, basic table information and storage statistics (STORAGE_STATS) are returned.<br>Possible values:<br>TABLE_METADATA_VIEW_UNSPECIFIED: The default value. Default to the STORAGE_STATS view.<br>BASIC: Includes basic table information including schema and partitioning specification. This view does not include storage statistics such as numRows or numBytes. This view is significantly more efficient and should be used to support high query rates.<br>STORAGE_STATS: Includes all information in the BASIC view as well as storage statistics (numBytes, numLongTermBytes, numRows and lastModifiedTime).<br>FULL: Includes all table information, including storage statistics. It returns same information as STORAGE_STATS view, but may contain additional information in the future. */
			def withView(view: String) = new get(req.addQueryStringParameters("view" -> view.toString))
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Table])
		}
		object get {
			def apply(projectsId :PlayApi, datasetsId :PlayApi, tablesId :PlayApi, datasetId: String, projectId: String, selectedFields: String, tableId: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"projects/${projectsId}/datasets/${datasetsId}/tables/${tablesId}").addQueryStringParameters("datasetId" -> datasetId.toString, "projectId" -> projectId.toString, "selectedFields" -> selectedFields.toString, "tableId" -> tableId.toString))
			given Conversion[get, Future[Schema.Table]] = (fun: get) => fun.apply()
		}
		class update(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			/** Optional.  When true will autodetect schema, else will keep original schema */
			def withAutodetect_schema(autodetect_schema: Boolean) = new update(req.addQueryStringParameters("autodetect_schema" -> autodetect_schema.toString))
			def withTable(body: Schema.Table) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.Table])
		}
		object update {
			def apply(projectsId :PlayApi, datasetsId :PlayApi, tablesId :PlayApi, datasetId: String, projectId: String, tableId: String)(using auth: AuthToken, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"projects/${projectsId}/datasets/${datasetsId}/tables/${tablesId}").addQueryStringParameters("datasetId" -> datasetId.toString, "projectId" -> projectId.toString, "tableId" -> tableId.toString))
		}
		class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			/** Optional.  When true will autodetect schema, else will keep original schema */
			def withAutodetect_schema(autodetect_schema: Boolean) = new patch(req.addQueryStringParameters("autodetect_schema" -> autodetect_schema.toString))
			def withTable(body: Schema.Table) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Table])
		}
		object patch {
			def apply(projectsId :PlayApi, datasetsId :PlayApi, tablesId :PlayApi, datasetId: String, projectId: String, tableId: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"projects/${projectsId}/datasets/${datasetsId}/tables/${tablesId}").addQueryStringParameters("datasetId" -> datasetId.toString, "projectId" -> projectId.toString, "tableId" -> tableId.toString))
		}
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.TableList]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.TableList])
		}
		object list {
			def apply(projectsId :PlayApi, datasetsId :PlayApi, datasetId: String, maxResults: Int, pageToken: String, projectId: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"projects/${projectsId}/datasets/${datasetsId}/tables").addQueryStringParameters("datasetId" -> datasetId.toString, "maxResults" -> maxResults.toString, "pageToken" -> pageToken.toString, "projectId" -> projectId.toString))
			given Conversion[list, Future[Schema.TableList]] = (fun: list) => fun.apply()
		}
	}
}
