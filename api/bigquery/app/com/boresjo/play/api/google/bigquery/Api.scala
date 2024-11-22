package com.boresjo.play.api.google.bigquery

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
		"""https://www.googleapis.com/auth/bigquery""" /* View and manage your data in Google BigQuery and see the email address for your Google Account */,
		"""https://www.googleapis.com/auth/bigquery.insertdata""" /* Insert data into Google BigQuery */,
		"""https://www.googleapis.com/auth/cloud-platform""" /* See, edit, configure, and delete your Google Cloud data and see the email address for your Google Account. */,
		"""https://www.googleapis.com/auth/cloud-platform.read-only""" /* View your data across Google Cloud services and see the email address of your Google Account */,
		"""https://www.googleapis.com/auth/devstorage.full_control""" /* Manage your data and permissions in Cloud Storage and see the email address for your Google Account */,
		"""https://www.googleapis.com/auth/devstorage.read_only""" /* View your data in Google Cloud Storage */,
		"""https://www.googleapis.com/auth/devstorage.read_write""" /* Manage your data in Cloud Storage and see the email address of your Google Account */
	)

	private val BASE_URL = "https://bigquery.googleapis.com/bigquery/v2/"

	object datasets {
		/** Undeletes a dataset which is within time travel window based on datasetId. If a time is specified, the dataset version deleted at that time is undeleted, else the last live version is undeleted. */
		class undelete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/bigquery""", """https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def withUndeleteDatasetRequest(body: Schema.UndeleteDatasetRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Dataset])
		}
		object undelete {
			def apply(projectsId :PlayApi, datasetsId :PlayApi, datasetId: String, projectId: String)(using signer: RequestSigner, ec: ExecutionContext): undelete = new undelete(ws.url(BASE_URL + s"projects/${projectsId}/datasets/${datasetsId}:undelete").addQueryStringParameters("datasetId" -> datasetId.toString, "projectId" -> projectId.toString))
		}
		/** Creates a new empty dataset. */
		class insert(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/bigquery""", """https://www.googleapis.com/auth/cloud-platform""")
			/** Optional. The version of the provided access policy schema. Valid values are 0, 1, and 3. Requests specifying an invalid value will be rejected. This version refers to the schema version of the access policy and not the version of access policy. This field's value can be equal or more than the access policy schema provided in the request. For example, &#42; Requests with conditional access policy binding in datasets must specify version 3. &#42; But dataset with no conditional role bindings in access policy may specify any valid value or leave the field unset. If unset or if 0 or 1 value is used for dataset with conditional bindings, request will be rejected. This field will be maped to IAM Policy version (https://cloud.google.com/iam/docs/policies#versions) and will be used to set policy in IAM.<br>Format: int32 */
			def withAccessPolicyVersion(accessPolicyVersion: Int) = new insert(req.addQueryStringParameters("accessPolicyVersion" -> accessPolicyVersion.toString))
			/** Perform the request */
			def withDataset(body: Schema.Dataset) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Dataset])
		}
		object insert {
			def apply(projectsId :PlayApi, projectId: String)(using signer: RequestSigner, ec: ExecutionContext): insert = new insert(ws.url(BASE_URL + s"projects/${projectsId}/datasets").addQueryStringParameters("projectId" -> projectId.toString))
		}
		/** Deletes the dataset specified by the datasetId value. Before you can delete a dataset, you must delete all its tables, either manually or by specifying deleteContents. Immediately after deletion, you can create another dataset with the same name. */
		class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Unit]) {
			val scopes = Seq("""https://www.googleapis.com/auth/bigquery""", """https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_ => ())
		}
		object delete {
			def apply(projectsId :PlayApi, datasetsId :PlayApi, datasetId: String, deleteContents: Boolean, projectId: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"projects/${projectsId}/datasets/${datasetsId}").addQueryStringParameters("datasetId" -> datasetId.toString, "deleteContents" -> deleteContents.toString, "projectId" -> projectId.toString))
			given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
		}
		/** Returns the dataset specified by datasetID. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Dataset]) {
			val scopes = Seq("""https://www.googleapis.com/auth/bigquery""", """https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""")
			/** Optional. The version of the access policy schema to fetch. Valid values are 0, 1, and 3. Requests specifying an invalid value will be rejected. Requests for conditional access policy binding in datasets must specify version 3. Dataset with no conditional role bindings in access policy may specify any valid value or leave the field unset. This field will be maped to [IAM Policy version] (https://cloud.google.com/iam/docs/policies#versions) and will be used to fetch policy from IAM. If unset or if 0 or 1 value is used for dataset with conditional bindings, access entry with condition will have role string appended by 'withcond' string followed by a hash value. For example : { "access": [ { "role": "roles/bigquery.dataViewer_with_conditionalbinding_7a34awqsda", "userByEmail": "user@example.com", } ] } Please refer https://cloud.google.com/iam/docs/troubleshooting-withcond for more details.<br>Format: int32 */
			def withAccessPolicyVersion(accessPolicyVersion: Int) = new get(req.addQueryStringParameters("accessPolicyVersion" -> accessPolicyVersion.toString))
			/** Optional. Specifies the view that determines which dataset information is returned. By default, metadata and ACL information are returned.<br>Possible values:<br>DATASET_VIEW_UNSPECIFIED: The default value. Default to the FULL view.<br>METADATA: Includes metadata information for the dataset, such as location, etag, lastModifiedTime, etc.<br>ACL: Includes ACL information for the dataset, which defines dataset access for one or more entities.<br>FULL: Includes both dataset metadata and ACL information. */
			def withDatasetView(datasetView: String) = new get(req.addQueryStringParameters("datasetView" -> datasetView.toString))
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Dataset])
		}
		object get {
			def apply(projectsId :PlayApi, datasetsId :PlayApi, datasetId: String, projectId: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"projects/${projectsId}/datasets/${datasetsId}").addQueryStringParameters("datasetId" -> datasetId.toString, "projectId" -> projectId.toString))
			given Conversion[get, Future[Schema.Dataset]] = (fun: get) => fun.apply()
		}
		/** Updates information in an existing dataset. The update method replaces the entire dataset resource, whereas the patch method only replaces fields that are provided in the submitted dataset resource. */
		class update(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/bigquery""", """https://www.googleapis.com/auth/cloud-platform""")
			/** Optional. The version of the provided access policy schema. Valid values are 0, 1, and 3. Requests specifying an invalid value will be rejected. This version refers to the schema version of the access policy and not the version of access policy. This field's value can be equal or more than the access policy schema provided in the request. For example, &#42; Operations updating conditional access policy binding in datasets must specify version 3. Some of the operations are : - Adding a new access policy entry with condition. - Removing an access policy entry with condition. - Updating an access policy entry with condition. &#42; But dataset with no conditional role bindings in access policy may specify any valid value or leave the field unset. If unset or if 0 or 1 value is used for dataset with conditional bindings, request will be rejected. This field will be maped to IAM Policy version (https://cloud.google.com/iam/docs/policies#versions) and will be used to set policy in IAM.<br>Format: int32 */
			def withAccessPolicyVersion(accessPolicyVersion: Int) = new update(req.addQueryStringParameters("accessPolicyVersion" -> accessPolicyVersion.toString))
			/** Perform the request */
			def withDataset(body: Schema.Dataset) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.Dataset])
		}
		object update {
			def apply(projectsId :PlayApi, datasetsId :PlayApi, datasetId: String, projectId: String)(using signer: RequestSigner, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"projects/${projectsId}/datasets/${datasetsId}").addQueryStringParameters("datasetId" -> datasetId.toString, "projectId" -> projectId.toString))
		}
		/** Updates information in an existing dataset. The update method replaces the entire dataset resource, whereas the patch method only replaces fields that are provided in the submitted dataset resource. This method supports RFC5789 patch semantics. */
		class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/bigquery""", """https://www.googleapis.com/auth/cloud-platform""")
			/** Optional. The version of the provided access policy schema. Valid values are 0, 1, and 3. Requests specifying an invalid value will be rejected. This version refers to the schema version of the access policy and not the version of access policy. This field's value can be equal or more than the access policy schema provided in the request. For example, &#42; Operations updating conditional access policy binding in datasets must specify version 3. Some of the operations are : - Adding a new access policy entry with condition. - Removing an access policy entry with condition. - Updating an access policy entry with condition. &#42; But dataset with no conditional role bindings in access policy may specify any valid value or leave the field unset. If unset or if 0 or 1 value is used for dataset with conditional bindings, request will be rejected. This field will be maped to IAM Policy version (https://cloud.google.com/iam/docs/policies#versions) and will be used to set policy in IAM.<br>Format: int32 */
			def withAccessPolicyVersion(accessPolicyVersion: Int) = new patch(req.addQueryStringParameters("accessPolicyVersion" -> accessPolicyVersion.toString))
			/** Perform the request */
			def withDataset(body: Schema.Dataset) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Dataset])
		}
		object patch {
			def apply(projectsId :PlayApi, datasetsId :PlayApi, datasetId: String, projectId: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"projects/${projectsId}/datasets/${datasetsId}").addQueryStringParameters("datasetId" -> datasetId.toString, "projectId" -> projectId.toString))
		}
		/** Lists all datasets in the specified project to which the user has been granted the READER dataset role. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.DatasetList]) {
			val scopes = Seq("""https://www.googleapis.com/auth/bigquery""", """https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.DatasetList])
		}
		object list {
			def apply(projectsId :PlayApi, all: Boolean, filter: String, maxResults: Int, pageToken: String, projectId: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"projects/${projectsId}/datasets").addQueryStringParameters("all" -> all.toString, "filter" -> filter.toString, "maxResults" -> maxResults.toString, "pageToken" -> pageToken.toString, "projectId" -> projectId.toString))
			given Conversion[list, Future[Schema.DatasetList]] = (fun: list) => fun.apply()
		}
	}
	object jobs {
		/** Requests that a job be cancelled. This call will return immediately, and the client will need to poll for the job status to see if the cancel completed successfully. Cancelled jobs may still incur costs. */
		class cancel(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.JobCancelResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/bigquery""", """https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("POST")).map(_.json.as[Schema.JobCancelResponse])
		}
		object cancel {
			def apply(projectsId :PlayApi, jobsId :PlayApi, jobId: String, location: String, projectId: String)(using signer: RequestSigner, ec: ExecutionContext): cancel = new cancel(ws.url(BASE_URL + s"projects/${projectsId}/jobs/${jobsId}/cancel").addQueryStringParameters("jobId" -> jobId.toString, "location" -> location.toString, "projectId" -> projectId.toString))
			given Conversion[cancel, Future[Schema.JobCancelResponse]] = (fun: cancel) => fun.apply()
		}
		/** Starts a new asynchronous job. This API has two different kinds of endpoint URIs, as this method supports a variety of use cases. &#42; The &#42;Metadata&#42; URI is used for most interactions, as it accepts the job configuration directly. &#42; The &#42;Upload&#42; URI is ONLY for the case when you're sending both a load job configuration and a data stream together. In this case, the Upload URI accepts the job configuration and the data as two distinct multipart MIME parts. */
		class insert(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/bigquery""", """https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/devstorage.full_control""", """https://www.googleapis.com/auth/devstorage.read_only""", """https://www.googleapis.com/auth/devstorage.read_write""")
			/** Perform the request */
			def withJob(body: Schema.Job) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Job])
		}
		object insert {
			def apply(projectsId :PlayApi, projectId: String)(using signer: RequestSigner, ec: ExecutionContext): insert = new insert(ws.url(BASE_URL + s"projects/${projectsId}/jobs").addQueryStringParameters("projectId" -> projectId.toString))
		}
		/** Runs a BigQuery SQL query synchronously and returns query results if the query completes within a specified timeout. */
		class query(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/bigquery""", """https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""")
			/** Perform the request */
			def withQueryRequest(body: Schema.QueryRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.QueryResponse])
		}
		object query {
			def apply(projectsId :PlayApi, projectId: String)(using signer: RequestSigner, ec: ExecutionContext): query = new query(ws.url(BASE_URL + s"projects/${projectsId}/queries").addQueryStringParameters("projectId" -> projectId.toString))
		}
		/** Requests the deletion of the metadata of a job. This call returns when the job's metadata is deleted. */
		class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Unit]) {
			val scopes = Seq("""https://www.googleapis.com/auth/bigquery""", """https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_ => ())
		}
		object delete {
			def apply(projectsId :PlayApi, jobsId :PlayApi, jobId: String, location: String, projectId: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"projects/${projectsId}/jobs/${jobsId}/delete").addQueryStringParameters("jobId" -> jobId.toString, "location" -> location.toString, "projectId" -> projectId.toString))
			given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
		}
		/** Returns information about a specific job. Job information is available for a six month period after creation. Requires that you're the person who ran the job, or have the Is Owner project role. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Job]) {
			val scopes = Seq("""https://www.googleapis.com/auth/bigquery""", """https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Job])
		}
		object get {
			def apply(projectsId :PlayApi, jobsId :PlayApi, jobId: String, location: String, projectId: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"projects/${projectsId}/jobs/${jobsId}").addQueryStringParameters("jobId" -> jobId.toString, "location" -> location.toString, "projectId" -> projectId.toString))
			given Conversion[get, Future[Schema.Job]] = (fun: get) => fun.apply()
		}
		/** Lists all jobs that you started in the specified project. Job information is available for a six month period after creation. The job list is sorted in reverse chronological order, by job creation time. Requires the Can View project role, or the Is Owner project role if you set the allUsers property. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.JobList]) {
			val scopes = Seq("""https://www.googleapis.com/auth/bigquery""", """https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.JobList])
		}
		object list {
			def apply(projectsId :PlayApi, allUsers: Boolean, maxCreationTime: String, maxResults: Int, minCreationTime: String, pageToken: String, parentJobId: String, projectId: String, projection: String, stateFilter: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"projects/${projectsId}/jobs").addQueryStringParameters("allUsers" -> allUsers.toString, "maxCreationTime" -> maxCreationTime.toString, "maxResults" -> maxResults.toString, "minCreationTime" -> minCreationTime.toString, "pageToken" -> pageToken.toString, "parentJobId" -> parentJobId.toString, "projectId" -> projectId.toString, "projection" -> projection.toString, "stateFilter" -> stateFilter.toString))
			given Conversion[list, Future[Schema.JobList]] = (fun: list) => fun.apply()
		}
		/** RPC to get the results of a query job. */
		class getQueryResults(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GetQueryResultsResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/bigquery""", """https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""")
			/** Optional. Output timestamp as usec int64. Default is false. */
			def withFormatOptionsUseInt64Timestamp(formatOptionsUseInt64Timestamp: Boolean) = new getQueryResults(req.addQueryStringParameters("formatOptions.useInt64Timestamp" -> formatOptionsUseInt64Timestamp.toString))
			/** Optional: Specifies the maximum amount of time, in milliseconds, that the client is willing to wait for the query to complete. By default, this limit is 10 seconds (10,000 milliseconds). If the query is complete, the jobComplete field in the response is true. If the query has not yet completed, jobComplete is false. You can request a longer timeout period in the timeoutMs field. However, the call is not guaranteed to wait for the specified timeout; it typically returns after around 200 seconds (200,000 milliseconds), even if the query is not complete. If jobComplete is false, you can continue to wait for the query to complete by calling the getQueryResults method until the jobComplete field in the getQueryResults response is true.<br>Format: uint32 */
			def withTimeoutMs(timeoutMs: Int) = new getQueryResults(req.addQueryStringParameters("timeoutMs" -> timeoutMs.toString))
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GetQueryResultsResponse])
		}
		object getQueryResults {
			def apply(projectsId :PlayApi, queriesId :PlayApi, jobId: String, location: String, maxResults: Int, pageToken: String, projectId: String, startIndex: String)(using signer: RequestSigner, ec: ExecutionContext): getQueryResults = new getQueryResults(ws.url(BASE_URL + s"projects/${projectsId}/queries/${queriesId}").addQueryStringParameters("jobId" -> jobId.toString, "location" -> location.toString, "maxResults" -> maxResults.toString, "pageToken" -> pageToken.toString, "projectId" -> projectId.toString, "startIndex" -> startIndex.toString))
			given Conversion[getQueryResults, Future[Schema.GetQueryResultsResponse]] = (fun: getQueryResults) => fun.apply()
		}
	}
	object models {
		/** Deletes the model specified by modelId from the dataset. */
		class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Unit]) {
			val scopes = Seq("""https://www.googleapis.com/auth/bigquery""", """https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_ => ())
		}
		object delete {
			def apply(projectsId :PlayApi, datasetsId :PlayApi, modelsId :PlayApi, datasetId: String, modelId: String, projectId: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"projects/${projectsId}/datasets/${datasetsId}/models/${modelsId}").addQueryStringParameters("datasetId" -> datasetId.toString, "modelId" -> modelId.toString, "projectId" -> projectId.toString))
			given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
		}
		/** Gets the specified model resource by model ID. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Model]) {
			val scopes = Seq("""https://www.googleapis.com/auth/bigquery""", """https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Model])
		}
		object get {
			def apply(projectsId :PlayApi, datasetsId :PlayApi, modelsId :PlayApi, datasetId: String, modelId: String, projectId: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"projects/${projectsId}/datasets/${datasetsId}/models/${modelsId}").addQueryStringParameters("datasetId" -> datasetId.toString, "modelId" -> modelId.toString, "projectId" -> projectId.toString))
			given Conversion[get, Future[Schema.Model]] = (fun: get) => fun.apply()
		}
		/** Lists all models in the specified dataset. Requires the READER dataset role. After retrieving the list of models, you can get information about a particular model by calling the models.get method. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListModelsResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/bigquery""", """https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListModelsResponse])
		}
		object list {
			def apply(projectsId :PlayApi, datasetsId :PlayApi, datasetId: String, maxResults: Int, pageToken: String, projectId: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"projects/${projectsId}/datasets/${datasetsId}/models").addQueryStringParameters("datasetId" -> datasetId.toString, "maxResults" -> maxResults.toString, "pageToken" -> pageToken.toString, "projectId" -> projectId.toString))
			given Conversion[list, Future[Schema.ListModelsResponse]] = (fun: list) => fun.apply()
		}
		/** Patch specific fields in the specified model. */
		class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/bigquery""", """https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def withModel(body: Schema.Model) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Model])
		}
		object patch {
			def apply(projectsId :PlayApi, datasetsId :PlayApi, modelsId :PlayApi, datasetId: String, modelId: String, projectId: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"projects/${projectsId}/datasets/${datasetsId}/models/${modelsId}").addQueryStringParameters("datasetId" -> datasetId.toString, "modelId" -> modelId.toString, "projectId" -> projectId.toString))
		}
	}
	object projects {
		/** RPC to get the service account for a project used for interactions with Google Cloud KMS */
		class getServiceAccount(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GetServiceAccountResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/bigquery""", """https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GetServiceAccountResponse])
		}
		object getServiceAccount {
			def apply(projectsId :PlayApi, projectId: String)(using signer: RequestSigner, ec: ExecutionContext): getServiceAccount = new getServiceAccount(ws.url(BASE_URL + s"projects/${projectsId}/serviceAccount").addQueryStringParameters("projectId" -> projectId.toString))
			given Conversion[getServiceAccount, Future[Schema.GetServiceAccountResponse]] = (fun: getServiceAccount) => fun.apply()
		}
		/** RPC to list projects to which the user has been granted any project role. Users of this method are encouraged to consider the [Resource Manager](https://cloud.google.com/resource-manager/docs/) API, which provides the underlying data for this method and has more capabilities. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ProjectList]) {
			val scopes = Seq("""https://www.googleapis.com/auth/bigquery""", """https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ProjectList])
		}
		object list {
			def apply(maxResults: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"projects").addQueryStringParameters("maxResults" -> maxResults.toString, "pageToken" -> pageToken.toString))
			given Conversion[list, Future[Schema.ProjectList]] = (fun: list) => fun.apply()
		}
	}
	object routines {
		/** Creates a new routine in the dataset. */
		class insert(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/bigquery""", """https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def withRoutine(body: Schema.Routine) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Routine])
		}
		object insert {
			def apply(projectsId :PlayApi, datasetsId :PlayApi, datasetId: String, projectId: String)(using signer: RequestSigner, ec: ExecutionContext): insert = new insert(ws.url(BASE_URL + s"projects/${projectsId}/datasets/${datasetsId}/routines").addQueryStringParameters("datasetId" -> datasetId.toString, "projectId" -> projectId.toString))
		}
		/** Sets the access control policy on the specified resource. Replaces any existing policy. Can return `NOT_FOUND`, `INVALID_ARGUMENT`, and `PERMISSION_DENIED` errors. */
		class setIamPolicy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/bigquery""", """https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def withSetIamPolicyRequest(body: Schema.SetIamPolicyRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
		}
		object setIamPolicy {
			def apply(projectsId :PlayApi, datasetsId :PlayApi, routinesId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): setIamPolicy = new setIamPolicy(ws.url(BASE_URL + s"projects/${projectsId}/datasets/${datasetsId}/routines/${routinesId}:setIamPolicy").addQueryStringParameters("resource" -> resource.toString))
		}
		/** Gets the access control policy for a resource. Returns an empty policy if the resource exists and does not have a policy set. */
		class getIamPolicy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/bigquery""", """https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""")
			/** Perform the request */
			def withGetIamPolicyRequest(body: Schema.GetIamPolicyRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
		}
		object getIamPolicy {
			def apply(projectsId :PlayApi, datasetsId :PlayApi, routinesId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): getIamPolicy = new getIamPolicy(ws.url(BASE_URL + s"projects/${projectsId}/datasets/${datasetsId}/routines/${routinesId}:getIamPolicy").addQueryStringParameters("resource" -> resource.toString))
		}
		/** Deletes the routine specified by routineId from the dataset. */
		class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Unit]) {
			val scopes = Seq("""https://www.googleapis.com/auth/bigquery""", """https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_ => ())
		}
		object delete {
			def apply(projectsId :PlayApi, datasetsId :PlayApi, routinesId :PlayApi, datasetId: String, projectId: String, routineId: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"projects/${projectsId}/datasets/${datasetsId}/routines/${routinesId}").addQueryStringParameters("datasetId" -> datasetId.toString, "projectId" -> projectId.toString, "routineId" -> routineId.toString))
			given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
		}
		/** Gets the specified routine resource by routine ID. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Routine]) {
			val scopes = Seq("""https://www.googleapis.com/auth/bigquery""", """https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Routine])
		}
		object get {
			def apply(projectsId :PlayApi, datasetsId :PlayApi, routinesId :PlayApi, datasetId: String, projectId: String, readMask: String, routineId: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"projects/${projectsId}/datasets/${datasetsId}/routines/${routinesId}").addQueryStringParameters("datasetId" -> datasetId.toString, "projectId" -> projectId.toString, "readMask" -> readMask.toString, "routineId" -> routineId.toString))
			given Conversion[get, Future[Schema.Routine]] = (fun: get) => fun.apply()
		}
		/** Updates information in an existing routine. The update method replaces the entire Routine resource. */
		class update(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/bigquery""", """https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def withRoutine(body: Schema.Routine) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.Routine])
		}
		object update {
			def apply(projectsId :PlayApi, datasetsId :PlayApi, routinesId :PlayApi, datasetId: String, projectId: String, routineId: String)(using signer: RequestSigner, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"projects/${projectsId}/datasets/${datasetsId}/routines/${routinesId}").addQueryStringParameters("datasetId" -> datasetId.toString, "projectId" -> projectId.toString, "routineId" -> routineId.toString))
		}
		/** Lists all routines in the specified dataset. Requires the READER dataset role. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListRoutinesResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/bigquery""", """https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListRoutinesResponse])
		}
		object list {
			def apply(projectsId :PlayApi, datasetsId :PlayApi, datasetId: String, filter: String, maxResults: Int, pageToken: String, projectId: String, readMask: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"projects/${projectsId}/datasets/${datasetsId}/routines").addQueryStringParameters("datasetId" -> datasetId.toString, "filter" -> filter.toString, "maxResults" -> maxResults.toString, "pageToken" -> pageToken.toString, "projectId" -> projectId.toString, "readMask" -> readMask.toString))
			given Conversion[list, Future[Schema.ListRoutinesResponse]] = (fun: list) => fun.apply()
		}
	}
	object rowAccessPolicies {
		/** Gets the access control policy for a resource. Returns an empty policy if the resource exists and does not have a policy set. */
		class getIamPolicy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/bigquery""", """https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""")
			/** Perform the request */
			def withGetIamPolicyRequest(body: Schema.GetIamPolicyRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
		}
		object getIamPolicy {
			def apply(projectsId :PlayApi, datasetsId :PlayApi, tablesId :PlayApi, rowAccessPoliciesId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): getIamPolicy = new getIamPolicy(ws.url(BASE_URL + s"projects/${projectsId}/datasets/${datasetsId}/tables/${tablesId}/rowAccessPolicies/${rowAccessPoliciesId}:getIamPolicy").addQueryStringParameters("resource" -> resource.toString))
		}
		/** Lists all row access policies on the specified table. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListRowAccessPoliciesResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/bigquery""", """https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListRowAccessPoliciesResponse])
		}
		object list {
			def apply(projectsId :PlayApi, datasetsId :PlayApi, tablesId :PlayApi, datasetId: String, pageSize: Int, pageToken: String, projectId: String, tableId: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"projects/${projectsId}/datasets/${datasetsId}/tables/${tablesId}/rowAccessPolicies").addQueryStringParameters("datasetId" -> datasetId.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "projectId" -> projectId.toString, "tableId" -> tableId.toString))
			given Conversion[list, Future[Schema.ListRowAccessPoliciesResponse]] = (fun: list) => fun.apply()
		}
		/** Returns permissions that a caller has on the specified resource. If the resource does not exist, this will return an empty set of permissions, not a `NOT_FOUND` error. Note: This operation is designed to be used for building permission-aware UIs and command-line tools, not for authorization checking. This operation may "fail open" without warning. */
		class testIamPermissions(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/bigquery""", """https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""")
			/** Perform the request */
			def withTestIamPermissionsRequest(body: Schema.TestIamPermissionsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.TestIamPermissionsResponse])
		}
		object testIamPermissions {
			def apply(projectsId :PlayApi, datasetsId :PlayApi, tablesId :PlayApi, rowAccessPoliciesId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): testIamPermissions = new testIamPermissions(ws.url(BASE_URL + s"projects/${projectsId}/datasets/${datasetsId}/tables/${tablesId}/rowAccessPolicies/${rowAccessPoliciesId}:testIamPermissions").addQueryStringParameters("resource" -> resource.toString))
		}
	}
	object tabledata {
		/** Streams data into BigQuery one record at a time without needing to run a load job. */
		class insertAll(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/bigquery""", """https://www.googleapis.com/auth/bigquery.insertdata""", """https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def withTableDataInsertAllRequest(body: Schema.TableDataInsertAllRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.TableDataInsertAllResponse])
		}
		object insertAll {
			def apply(projectsId :PlayApi, datasetsId :PlayApi, tablesId :PlayApi, datasetId: String, projectId: String, tableId: String)(using signer: RequestSigner, ec: ExecutionContext): insertAll = new insertAll(ws.url(BASE_URL + s"projects/${projectsId}/datasets/${datasetsId}/tables/${tablesId}/insertAll").addQueryStringParameters("datasetId" -> datasetId.toString, "projectId" -> projectId.toString, "tableId" -> tableId.toString))
		}
		/** List the content of a table in rows. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.TableDataList]) {
			val scopes = Seq("""https://www.googleapis.com/auth/bigquery""", """https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""")
			/** Optional. Output timestamp as usec int64. Default is false. */
			def withFormatOptionsUseInt64Timestamp(formatOptionsUseInt64Timestamp: Boolean) = new list(req.addQueryStringParameters("formatOptions.useInt64Timestamp" -> formatOptionsUseInt64Timestamp.toString))
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.TableDataList])
		}
		object list {
			def apply(projectsId :PlayApi, datasetsId :PlayApi, tablesId :PlayApi, datasetId: String, maxResults: Int, pageToken: String, projectId: String, selectedFields: String, startIndex: String, tableId: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"projects/${projectsId}/datasets/${datasetsId}/tables/${tablesId}/data").addQueryStringParameters("datasetId" -> datasetId.toString, "maxResults" -> maxResults.toString, "pageToken" -> pageToken.toString, "projectId" -> projectId.toString, "selectedFields" -> selectedFields.toString, "startIndex" -> startIndex.toString, "tableId" -> tableId.toString))
			given Conversion[list, Future[Schema.TableDataList]] = (fun: list) => fun.apply()
		}
	}
	object tables {
		/** Returns permissions that a caller has on the specified resource. If the resource does not exist, this will return an empty set of permissions, not a `NOT_FOUND` error. Note: This operation is designed to be used for building permission-aware UIs and command-line tools, not for authorization checking. This operation may "fail open" without warning. */
		class testIamPermissions(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/bigquery""", """https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""")
			/** Perform the request */
			def withTestIamPermissionsRequest(body: Schema.TestIamPermissionsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.TestIamPermissionsResponse])
		}
		object testIamPermissions {
			def apply(projectsId :PlayApi, datasetsId :PlayApi, tablesId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): testIamPermissions = new testIamPermissions(ws.url(BASE_URL + s"projects/${projectsId}/datasets/${datasetsId}/tables/${tablesId}:testIamPermissions").addQueryStringParameters("resource" -> resource.toString))
		}
		/** Creates a new, empty table in the dataset. */
		class insert(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/bigquery""", """https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def withTable(body: Schema.Table) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Table])
		}
		object insert {
			def apply(projectsId :PlayApi, datasetsId :PlayApi, datasetId: String, projectId: String)(using signer: RequestSigner, ec: ExecutionContext): insert = new insert(ws.url(BASE_URL + s"projects/${projectsId}/datasets/${datasetsId}/tables").addQueryStringParameters("datasetId" -> datasetId.toString, "projectId" -> projectId.toString))
		}
		/** Sets the access control policy on the specified resource. Replaces any existing policy. Can return `NOT_FOUND`, `INVALID_ARGUMENT`, and `PERMISSION_DENIED` errors. */
		class setIamPolicy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/bigquery""", """https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def withSetIamPolicyRequest(body: Schema.SetIamPolicyRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
		}
		object setIamPolicy {
			def apply(projectsId :PlayApi, datasetsId :PlayApi, tablesId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): setIamPolicy = new setIamPolicy(ws.url(BASE_URL + s"projects/${projectsId}/datasets/${datasetsId}/tables/${tablesId}:setIamPolicy").addQueryStringParameters("resource" -> resource.toString))
		}
		/** Gets the access control policy for a resource. Returns an empty policy if the resource exists and does not have a policy set. */
		class getIamPolicy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/bigquery""", """https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""")
			/** Perform the request */
			def withGetIamPolicyRequest(body: Schema.GetIamPolicyRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
		}
		object getIamPolicy {
			def apply(projectsId :PlayApi, datasetsId :PlayApi, tablesId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): getIamPolicy = new getIamPolicy(ws.url(BASE_URL + s"projects/${projectsId}/datasets/${datasetsId}/tables/${tablesId}:getIamPolicy").addQueryStringParameters("resource" -> resource.toString))
		}
		/** Deletes the table specified by tableId from the dataset. If the table contains data, all the data will be deleted. */
		class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Unit]) {
			val scopes = Seq("""https://www.googleapis.com/auth/bigquery""", """https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_ => ())
		}
		object delete {
			def apply(projectsId :PlayApi, datasetsId :PlayApi, tablesId :PlayApi, datasetId: String, projectId: String, tableId: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"projects/${projectsId}/datasets/${datasetsId}/tables/${tablesId}").addQueryStringParameters("datasetId" -> datasetId.toString, "projectId" -> projectId.toString, "tableId" -> tableId.toString))
			given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
		}
		/** Gets the specified table resource by table ID. This method does not return the data in the table, it only returns the table resource, which describes the structure of this table. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Table]) {
			val scopes = Seq("""https://www.googleapis.com/auth/bigquery""", """https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""")
			/** Optional. Specifies the view that determines which table information is returned. By default, basic table information and storage statistics (STORAGE_STATS) are returned.<br>Possible values:<br>TABLE_METADATA_VIEW_UNSPECIFIED: The default value. Default to the STORAGE_STATS view.<br>BASIC: Includes basic table information including schema and partitioning specification. This view does not include storage statistics such as numRows or numBytes. This view is significantly more efficient and should be used to support high query rates.<br>STORAGE_STATS: Includes all information in the BASIC view as well as storage statistics (numBytes, numLongTermBytes, numRows and lastModifiedTime).<br>FULL: Includes all table information, including storage statistics. It returns same information as STORAGE_STATS view, but may contain additional information in the future. */
			def withView(view: String) = new get(req.addQueryStringParameters("view" -> view.toString))
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Table])
		}
		object get {
			def apply(projectsId :PlayApi, datasetsId :PlayApi, tablesId :PlayApi, datasetId: String, projectId: String, selectedFields: String, tableId: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"projects/${projectsId}/datasets/${datasetsId}/tables/${tablesId}").addQueryStringParameters("datasetId" -> datasetId.toString, "projectId" -> projectId.toString, "selectedFields" -> selectedFields.toString, "tableId" -> tableId.toString))
			given Conversion[get, Future[Schema.Table]] = (fun: get) => fun.apply()
		}
		/** Updates information in an existing table. The update method replaces the entire Table resource, whereas the patch method only replaces fields that are provided in the submitted Table resource. */
		class update(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/bigquery""", """https://www.googleapis.com/auth/cloud-platform""")
			/** Optional.  When true will autodetect schema, else will keep original schema */
			def withAutodetect_schema(autodetect_schema: Boolean) = new update(req.addQueryStringParameters("autodetect_schema" -> autodetect_schema.toString))
			/** Perform the request */
			def withTable(body: Schema.Table) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.Table])
		}
		object update {
			def apply(projectsId :PlayApi, datasetsId :PlayApi, tablesId :PlayApi, datasetId: String, projectId: String, tableId: String)(using signer: RequestSigner, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"projects/${projectsId}/datasets/${datasetsId}/tables/${tablesId}").addQueryStringParameters("datasetId" -> datasetId.toString, "projectId" -> projectId.toString, "tableId" -> tableId.toString))
		}
		/** Updates information in an existing table. The update method replaces the entire table resource, whereas the patch method only replaces fields that are provided in the submitted table resource. This method supports RFC5789 patch semantics. */
		class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/bigquery""", """https://www.googleapis.com/auth/cloud-platform""")
			/** Optional.  When true will autodetect schema, else will keep original schema */
			def withAutodetect_schema(autodetect_schema: Boolean) = new patch(req.addQueryStringParameters("autodetect_schema" -> autodetect_schema.toString))
			/** Perform the request */
			def withTable(body: Schema.Table) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Table])
		}
		object patch {
			def apply(projectsId :PlayApi, datasetsId :PlayApi, tablesId :PlayApi, datasetId: String, projectId: String, tableId: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"projects/${projectsId}/datasets/${datasetsId}/tables/${tablesId}").addQueryStringParameters("datasetId" -> datasetId.toString, "projectId" -> projectId.toString, "tableId" -> tableId.toString))
		}
		/** Lists all tables in the specified dataset. Requires the READER dataset role. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.TableList]) {
			val scopes = Seq("""https://www.googleapis.com/auth/bigquery""", """https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.TableList])
		}
		object list {
			def apply(projectsId :PlayApi, datasetsId :PlayApi, datasetId: String, maxResults: Int, pageToken: String, projectId: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"projects/${projectsId}/datasets/${datasetsId}/tables").addQueryStringParameters("datasetId" -> datasetId.toString, "maxResults" -> maxResults.toString, "pageToken" -> pageToken.toString, "projectId" -> projectId.toString))
			given Conversion[list, Future[Schema.TableList]] = (fun: list) => fun.apply()
		}
	}
}
