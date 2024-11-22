package com.boresjo.play.api.google.healthcare

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
		"""https://www.googleapis.com/auth/cloud-healthcare""" /* Read, write and manage healthcare data */,
		"""https://www.googleapis.com/auth/cloud-platform""" /* See, edit, configure, and delete your Google Cloud data and see the email address for your Google Account. */
	)

	private val BASE_URL = "https://healthcare.googleapis.com/"

	object projects {
		object locations {
			/** Lists information about the supported locations for this service. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListLocationsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-healthcare""", """https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListLocationsResponse])
			}
			object list {
				def apply(projectsId :PlayApi, name: String, filter: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations").addQueryStringParameters("name" -> name.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListLocationsResponse]] = (fun: list) => fun.apply()
			}
			/** Gets information about a location. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Location]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-healthcare""", """https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Location])
			}
			object get {
				def apply(projectsId :PlayApi, locationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.Location]] = (fun: get) => fun.apply()
			}
			object datasets {
				/** Returns permissions that a caller has on the specified resource. If the resource does not exist, this will return an empty set of permissions, not a `NOT_FOUND` error. Note: This operation is designed to be used for building permission-aware UIs and command-line tools, not for authorization checking. This operation may "fail open" without warning. */
				class testIamPermissions(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-healthcare""", """https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withTestIamPermissionsRequest(body: Schema.TestIamPermissionsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.TestIamPermissionsResponse])
				}
				object testIamPermissions {
					def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): testIamPermissions = new testIamPermissions(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}:testIamPermissions").addQueryStringParameters("resource" -> resource.toString))
				}
				/** Creates a new dataset containing de-identified data from the source dataset. The metadata field type is OperationMetadata. If the request is successful, the response field type is DeidentifySummary. If errors occur, error is set. The LRO result may still be successful if de-identification fails for some DICOM instances. The new de-identified dataset will not contain these failed resources. Failed resource totals are tracked in Operation.metadata. Error details are also logged to Cloud Logging. For more information, see [Viewing error logs in Cloud Logging](https://cloud.google.com/healthcare/docs/how-tos/logging). */
				class deidentify(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-healthcare""", """https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withDeidentifyDatasetRequest(body: Schema.DeidentifyDatasetRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object deidentify {
					def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, sourceDataset: String)(using signer: RequestSigner, ec: ExecutionContext): deidentify = new deidentify(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}:deidentify").addQueryStringParameters("sourceDataset" -> sourceDataset.toString))
				}
				/** Gets the access control policy for a resource. Returns an empty policy if the resource exists and does not have a policy set. */
				class getIamPolicy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Policy]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-healthcare""", """https://www.googleapis.com/auth/cloud-platform""")
					/** Optional. The maximum policy version that will be used to format the policy. Valid values are 0, 1, and 3. Requests specifying an invalid value will be rejected. Requests for policies with any conditional role bindings must specify version 3. Policies with no conditional role bindings may specify any valid value or leave the field unset. The policy in the response might use the policy version that you specified, or it might use a lower policy version. For example, if you specify version 3, but the policy has no conditional role bindings, the response uses version 1. To learn which resources support conditions in their IAM policies, see the [IAM documentation](https://cloud.google.com/iam/help/conditions/resource-policies).<br>Format: int32 */
					def withOptionsRequestedPolicyVersion(optionsRequestedPolicyVersion: Int) = new getIamPolicy(req.addQueryStringParameters("options.requestedPolicyVersion" -> optionsRequestedPolicyVersion.toString))
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Policy])
				}
				object getIamPolicy {
					def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): getIamPolicy = new getIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}:getIamPolicy").addQueryStringParameters("resource" -> resource.toString))
					given Conversion[getIamPolicy, Future[Schema.Policy]] = (fun: getIamPolicy) => fun.apply()
				}
				/** Deletes the specified health dataset and all data contained in the dataset. Deleting a dataset does not affect the sources from which the dataset was imported (if any). */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-healthcare""", """https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
				}
				/** Gets any metadata associated with a dataset. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Dataset]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-healthcare""", """https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Dataset])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.Dataset]] = (fun: get) => fun.apply()
				}
				/** Updates dataset metadata. */
				class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-healthcare""", """https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withDataset(body: Schema.Dataset) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Dataset])
				}
				object patch {
					def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
				}
				/** Lists the health datasets in the current project. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListDatasetsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-healthcare""", """https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListDatasetsResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListDatasetsResponse]] = (fun: list) => fun.apply()
				}
				/** Creates a new health dataset. Results are returned through the Operation interface which returns either an `Operation.response` which contains a Dataset or `Operation.error`. The metadata field type is OperationMetadata. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-healthcare""", """https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withDataset(body: Schema.Dataset) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, datasetId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets").addQueryStringParameters("parent" -> parent.toString, "datasetId" -> datasetId.toString))
				}
				/** Sets the access control policy on the specified resource. Replaces any existing policy. Can return `NOT_FOUND`, `INVALID_ARGUMENT`, and `PERMISSION_DENIED` errors. */
				class setIamPolicy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-healthcare""", """https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withSetIamPolicyRequest(body: Schema.SetIamPolicyRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
				}
				object setIamPolicy {
					def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): setIamPolicy = new setIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}:setIamPolicy").addQueryStringParameters("resource" -> resource.toString))
				}
				object dataMapperWorkspaces {
					/** Sets the access control policy on the specified resource. Replaces any existing policy. Can return `NOT_FOUND`, `INVALID_ARGUMENT`, and `PERMISSION_DENIED` errors. */
					class setIamPolicy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-healthcare""", """https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withSetIamPolicyRequest(body: Schema.SetIamPolicyRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
					}
					object setIamPolicy {
						def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, dataMapperWorkspacesId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): setIamPolicy = new setIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/dataMapperWorkspaces/${dataMapperWorkspacesId}:setIamPolicy").addQueryStringParameters("resource" -> resource.toString))
					}
					/** Gets the access control policy for a resource. Returns an empty policy if the resource exists and does not have a policy set. */
					class getIamPolicy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Policy]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-healthcare""", """https://www.googleapis.com/auth/cloud-platform""")
						/** Optional. The maximum policy version that will be used to format the policy. Valid values are 0, 1, and 3. Requests specifying an invalid value will be rejected. Requests for policies with any conditional role bindings must specify version 3. Policies with no conditional role bindings may specify any valid value or leave the field unset. The policy in the response might use the policy version that you specified, or it might use a lower policy version. For example, if you specify version 3, but the policy has no conditional role bindings, the response uses version 1. To learn which resources support conditions in their IAM policies, see the [IAM documentation](https://cloud.google.com/iam/help/conditions/resource-policies).<br>Format: int32 */
						def withOptionsRequestedPolicyVersion(optionsRequestedPolicyVersion: Int) = new getIamPolicy(req.addQueryStringParameters("options.requestedPolicyVersion" -> optionsRequestedPolicyVersion.toString))
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Policy])
					}
					object getIamPolicy {
						def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, dataMapperWorkspacesId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): getIamPolicy = new getIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/dataMapperWorkspaces/${dataMapperWorkspacesId}:getIamPolicy").addQueryStringParameters("resource" -> resource.toString))
						given Conversion[getIamPolicy, Future[Schema.Policy]] = (fun: getIamPolicy) => fun.apply()
					}
					/** Returns permissions that a caller has on the specified resource. If the resource does not exist, this will return an empty set of permissions, not a `NOT_FOUND` error. Note: This operation is designed to be used for building permission-aware UIs and command-line tools, not for authorization checking. This operation may "fail open" without warning. */
					class testIamPermissions(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-healthcare""", """https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withTestIamPermissionsRequest(body: Schema.TestIamPermissionsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.TestIamPermissionsResponse])
					}
					object testIamPermissions {
						def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, dataMapperWorkspacesId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): testIamPermissions = new testIamPermissions(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/dataMapperWorkspaces/${dataMapperWorkspacesId}:testIamPermissions").addQueryStringParameters("resource" -> resource.toString))
					}
				}
				object consentStores {
					/** Returns permissions that a caller has on the specified resource. If the resource does not exist, this will return an empty set of permissions, not a `NOT_FOUND` error. Note: This operation is designed to be used for building permission-aware UIs and command-line tools, not for authorization checking. This operation may "fail open" without warning. */
					class testIamPermissions(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-healthcare""", """https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withTestIamPermissionsRequest(body: Schema.TestIamPermissionsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.TestIamPermissionsResponse])
					}
					object testIamPermissions {
						def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, consentStoresId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): testIamPermissions = new testIamPermissions(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/consentStores/${consentStoresId}:testIamPermissions").addQueryStringParameters("resource" -> resource.toString))
					}
					/** Queries all data_ids that are consented for a specified use in the given consent store and writes them to a specified destination. The returned Operation includes a progress counter for the number of User data mappings processed. If the request is successful, a detailed response is returned of type QueryAccessibleDataResponse, contained in the response field when the operation finishes. The metadata field type is OperationMetadata. Errors are logged to Cloud Logging (see [Viewing error logs in Cloud Logging](https://cloud.google.com/healthcare/docs/how-tos/logging)). For example, the following sample log entry shows a `failed to evaluate consent policy` error that occurred during a QueryAccessibleData call to consent store `projects/{project_id}/locations/{location_id}/datasets/{dataset_id}/consentStores/{consent_store_id}`. ```json jsonPayload: { @type: "type.googleapis.com/google.cloud.healthcare.logging.QueryAccessibleDataLogEntry" error: { code: 9 message: "failed to evaluate consent policy" } resourceName: "projects/{project_id}/locations/{location_id}/datasets/{dataset_id}/consentStores/{consent_store_id}/consents/{consent_id}" } logName: "projects/{project_id}/logs/healthcare.googleapis.com%2Fquery_accessible_data" operation: { id: "projects/{project_id}/locations/{location_id}/datasets/{dataset_id}/operations/{operation_id}" producer: "healthcare.googleapis.com/QueryAccessibleData" } receiveTimestamp: "TIMESTAMP" resource: { labels: { consent_store_id: "{consent_store_id}" dataset_id: "{dataset_id}" location: "{location_id}" project_id: "{project_id}" } type: "healthcare_consent_store" } severity: "ERROR" timestamp: "TIMESTAMP" ``` */
					class queryAccessibleData(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-healthcare""", """https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withQueryAccessibleDataRequest(body: Schema.QueryAccessibleDataRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
					}
					object queryAccessibleData {
						def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, consentStoresId :PlayApi, consentStore: String)(using signer: RequestSigner, ec: ExecutionContext): queryAccessibleData = new queryAccessibleData(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/consentStores/${consentStoresId}:queryAccessibleData").addQueryStringParameters("consentStore" -> consentStore.toString))
					}
					/** Gets the access control policy for a resource. Returns an empty policy if the resource exists and does not have a policy set. */
					class getIamPolicy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Policy]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-healthcare""", """https://www.googleapis.com/auth/cloud-platform""")
						/** Optional. The maximum policy version that will be used to format the policy. Valid values are 0, 1, and 3. Requests specifying an invalid value will be rejected. Requests for policies with any conditional role bindings must specify version 3. Policies with no conditional role bindings may specify any valid value or leave the field unset. The policy in the response might use the policy version that you specified, or it might use a lower policy version. For example, if you specify version 3, but the policy has no conditional role bindings, the response uses version 1. To learn which resources support conditions in their IAM policies, see the [IAM documentation](https://cloud.google.com/iam/help/conditions/resource-policies).<br>Format: int32 */
						def withOptionsRequestedPolicyVersion(optionsRequestedPolicyVersion: Int) = new getIamPolicy(req.addQueryStringParameters("options.requestedPolicyVersion" -> optionsRequestedPolicyVersion.toString))
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Policy])
					}
					object getIamPolicy {
						def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, consentStoresId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): getIamPolicy = new getIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/consentStores/${consentStoresId}:getIamPolicy").addQueryStringParameters("resource" -> resource.toString))
						given Conversion[getIamPolicy, Future[Schema.Policy]] = (fun: getIamPolicy) => fun.apply()
					}
					/** Deletes the specified consent store and removes all the consent store's data. */
					class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-healthcare""", """https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
					}
					object delete {
						def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, consentStoresId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/consentStores/${consentStoresId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
					}
					/** Updates the specified consent store. */
					class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-healthcare""", """https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withConsentStore(body: Schema.ConsentStore) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.ConsentStore])
					}
					object patch {
						def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, consentStoresId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/consentStores/${consentStoresId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
					}
					/** Lists the consent stores in the specified dataset. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListConsentStoresResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-healthcare""", """https://www.googleapis.com/auth/cloud-platform""")
						/** Optional. Limit on the number of consent stores to return in a single response. If not specified, 100 is used. May not be larger than 1000.<br>Format: int32 */
						def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
						/** Optional. Token to retrieve the next page of results, or empty to get the first page. */
						def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
						/** Optional. Restricts the stores returned to those matching a filter. Only filtering on labels is supported. For example, `filter=labels.key=value`. */
						def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListConsentStoresResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/consentStores").addQueryStringParameters("parent" -> parent.toString))
						given Conversion[list, Future[Schema.ListConsentStoresResponse]] = (fun: list) => fun.apply()
					}
					/** Checks if a particular data_id of a User data mapping in the specified consent store is consented for the specified use. */
					class checkDataAccess(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-healthcare""", """https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withCheckDataAccessRequest(body: Schema.CheckDataAccessRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.CheckDataAccessResponse])
					}
					object checkDataAccess {
						def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, consentStoresId :PlayApi, consentStore: String)(using signer: RequestSigner, ec: ExecutionContext): checkDataAccess = new checkDataAccess(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/consentStores/${consentStoresId}:checkDataAccess").addQueryStringParameters("consentStore" -> consentStore.toString))
					}
					/** Evaluates the user's Consents for all matching User data mappings. Note: User data mappings are indexed asynchronously, which can cause a slight delay between the time mappings are created or updated and when they are included in EvaluateUserConsents results. */
					class evaluateUserConsents(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-healthcare""", """https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withEvaluateUserConsentsRequest(body: Schema.EvaluateUserConsentsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.EvaluateUserConsentsResponse])
					}
					object evaluateUserConsents {
						def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, consentStoresId :PlayApi, consentStore: String)(using signer: RequestSigner, ec: ExecutionContext): evaluateUserConsents = new evaluateUserConsents(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/consentStores/${consentStoresId}:evaluateUserConsents").addQueryStringParameters("consentStore" -> consentStore.toString))
					}
					/** Creates a new consent store in the parent dataset. Attempting to create a consent store with the same ID as an existing store fails with an ALREADY_EXISTS error. */
					class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-healthcare""", """https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withConsentStore(body: Schema.ConsentStore) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.ConsentStore])
					}
					object create {
						def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, parent: String, consentStoreId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/consentStores").addQueryStringParameters("parent" -> parent.toString, "consentStoreId" -> consentStoreId.toString))
					}
					/** Sets the access control policy on the specified resource. Replaces any existing policy. Can return `NOT_FOUND`, `INVALID_ARGUMENT`, and `PERMISSION_DENIED` errors. */
					class setIamPolicy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-healthcare""", """https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withSetIamPolicyRequest(body: Schema.SetIamPolicyRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
					}
					object setIamPolicy {
						def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, consentStoresId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): setIamPolicy = new setIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/consentStores/${consentStoresId}:setIamPolicy").addQueryStringParameters("resource" -> resource.toString))
					}
					/** Gets the specified consent store. */
					class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ConsentStore]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-healthcare""", """https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ConsentStore])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, consentStoresId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/consentStores/${consentStoresId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.ConsentStore]] = (fun: get) => fun.apply()
					}
					object consents {
						/** Creates a new Consent in the parent consent store. */
						class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-healthcare""", """https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def withConsent(body: Schema.Consent) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Consent])
						}
						object create {
							def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, consentStoresId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/consentStores/${consentStoresId}/consents").addQueryStringParameters("parent" -> parent.toString))
						}
						/** Deletes the Consent and its revisions. To keep a record of the Consent but mark it inactive, see [RevokeConsent]. To delete a revision of a Consent, see [DeleteConsentRevision]. This operation does not delete the related Consent artifact. */
						class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-healthcare""", """https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
						}
						object delete {
							def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, consentStoresId :PlayApi, consentsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/consentStores/${consentStoresId}/consents/${consentsId}").addQueryStringParameters("name" -> name.toString))
							given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
						}
						/** Deletes the specified revision of a Consent. An INVALID_ARGUMENT error occurs if the specified revision is the latest revision. */
						class deleteRevision(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-healthcare""", """https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
						}
						object deleteRevision {
							def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, consentStoresId :PlayApi, consentsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): deleteRevision = new deleteRevision(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/consentStores/${consentStoresId}/consents/${consentsId}:deleteRevision").addQueryStringParameters("name" -> name.toString))
							given Conversion[deleteRevision, Future[Schema.Empty]] = (fun: deleteRevision) => fun.apply()
						}
						/** Activates the latest revision of the specified Consent by committing a new revision with `state` updated to `ACTIVE`. If the latest revision of the specified Consent is in the `ACTIVE` state, no new revision is committed. A FAILED_PRECONDITION error occurs if the latest revision of the specified Consent is in the `REJECTED` or `REVOKED` state. */
						class activate(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-healthcare""", """https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def withActivateConsentRequest(body: Schema.ActivateConsentRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Consent])
						}
						object activate {
							def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, consentStoresId :PlayApi, consentsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): activate = new activate(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/consentStores/${consentStoresId}/consents/${consentsId}:activate").addQueryStringParameters("name" -> name.toString))
						}
						/** Updates the latest revision of the specified Consent by committing a new revision with the changes. A FAILED_PRECONDITION error occurs if the latest revision of the specified Consent is in the `REJECTED` or `REVOKED` state. */
						class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-healthcare""", """https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def withConsent(body: Schema.Consent) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Consent])
						}
						object patch {
							def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, consentStoresId :PlayApi, consentsId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/consentStores/${consentStoresId}/consents/${consentsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
						}
						/** Lists the Consent in the given consent store, returning each Consent's latest revision. */
						class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListConsentsResponse]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-healthcare""", """https://www.googleapis.com/auth/cloud-platform""")
							/** Optional. Limit on the number of Consents to return in a single response. If not specified, 100 is used. May not be larger than 1000.<br>Format: int32 */
							def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
							/** Optional. The next_page_token value returned from the previous List request, if any. */
							def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
							/** Optional. Restricts the Consents returned to those matching a filter. The following syntax is available: &#42; A string field value can be written as text inside quotation marks, for example `"query text"`. The only valid relational operation for text fields is equality (`=`), where text is searched within the field, rather than having the field be equal to the text. For example, `"Comment = great"` returns messages with `great` in the comment field. &#42; A number field value can be written as an integer, a decimal, or an exponential. The valid relational operators for number fields are the equality operator (`=`), along with the less than/greater than operators (`<`, `<=`, `>`, `>=`). Note that there is no inequality (`!=`) operator. You can prepend the `NOT` operator to an expression to negate it. &#42; A date field value must be written in `yyyy-mm-dd` form. Fields with date and time use the RFC3339 time format. Leading zeros are required for one-digit months and days. The valid relational operators for date fields are the equality operator (`=`) , along with the less than/greater than operators (`<`, `<=`, `>`, `>=`). Note that there is no inequality (`!=`) operator. You can prepend the `NOT` operator to an expression to negate it. &#42; Multiple field query expressions can be combined in one query by adding `AND` or `OR` operators between the expressions. If a boolean operator appears within a quoted string, it is not treated as special, it's just another part of the character string to be matched. You can prepend the `NOT` operator to an expression to negate it. The fields available for filtering are: - user_id. For example, `filter='user_id="user123"'`. - consent_artifact - state - revision_create_time - metadata. For example, `filter=Metadata(\"testkey\")=\"value\"` or `filter=HasMetadata(\"testkey\")`. */
							def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListConsentsResponse])
						}
						object list {
							def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, consentStoresId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/consentStores/${consentStoresId}/consents").addQueryStringParameters("parent" -> parent.toString))
							given Conversion[list, Future[Schema.ListConsentsResponse]] = (fun: list) => fun.apply()
						}
						/** Revokes the latest revision of the specified Consent by committing a new revision with `state` updated to `REVOKED`. If the latest revision of the specified Consent is in the `REVOKED` state, no new revision is committed. A FAILED_PRECONDITION error occurs if the latest revision of the given consent is in `DRAFT` or `REJECTED` state. */
						class revoke(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-healthcare""", """https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def withRevokeConsentRequest(body: Schema.RevokeConsentRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Consent])
						}
						object revoke {
							def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, consentStoresId :PlayApi, consentsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): revoke = new revoke(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/consentStores/${consentStoresId}/consents/${consentsId}:revoke").addQueryStringParameters("name" -> name.toString))
						}
						/** Rejects the latest revision of the specified Consent by committing a new revision with `state` updated to `REJECTED`. If the latest revision of the specified Consent is in the `REJECTED` state, no new revision is committed. A FAILED_PRECONDITION error occurs if the latest revision of the specified Consent is in the `ACTIVE` or `REVOKED` state. */
						class reject(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-healthcare""", """https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def withRejectConsentRequest(body: Schema.RejectConsentRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Consent])
						}
						object reject {
							def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, consentStoresId :PlayApi, consentsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): reject = new reject(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/consentStores/${consentStoresId}/consents/${consentsId}:reject").addQueryStringParameters("name" -> name.toString))
						}
						/** Gets the specified revision of a Consent, or the latest revision if `revision_id` is not specified in the resource name. */
						class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Consent]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-healthcare""", """https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Consent])
						}
						object get {
							def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, consentStoresId :PlayApi, consentsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/consentStores/${consentStoresId}/consents/${consentsId}").addQueryStringParameters("name" -> name.toString))
							given Conversion[get, Future[Schema.Consent]] = (fun: get) => fun.apply()
						}
						/** Lists the revisions of the specified Consent in reverse chronological order. */
						class listRevisions(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListConsentRevisionsResponse]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-healthcare""", """https://www.googleapis.com/auth/cloud-platform""")
							/** Optional. Limit on the number of revisions to return in a single response. If not specified, 100 is used. May not be larger than 1000.<br>Format: int32 */
							def withPageSize(pageSize: Int) = new listRevisions(req.addQueryStringParameters("pageSize" -> pageSize.toString))
							/** Optional. Token to retrieve the next page of results or empty if there are no more results in the list. */
							def withPageToken(pageToken: String) = new listRevisions(req.addQueryStringParameters("pageToken" -> pageToken.toString))
							/** Optional. Restricts the revisions returned to those matching a filter. The following syntax is available: &#42; A string field value can be written as text inside quotation marks, for example `"query text"`. The only valid relational operation for text fields is equality (`=`), where text is searched within the field, rather than having the field be equal to the text. For example, `"Comment = great"` returns messages with `great` in the comment field. &#42; A number field value can be written as an integer, a decimal, or an exponential. The valid relational operators for number fields are the equality operator (`=`), along with the less than/greater than operators (`<`, `<=`, `>`, `>=`). Note that there is no inequality (`!=`) operator. You can prepend the `NOT` operator to an expression to negate it. &#42; A date field value must be written in `yyyy-mm-dd` form. Fields with date and time use the RFC3339 time format. Leading zeros are required for one-digit months and days. The valid relational operators for date fields are the equality operator (`=`) , along with the less than/greater than operators (`<`, `<=`, `>`, `>=`). Note that there is no inequality (`!=`) operator. You can prepend the `NOT` operator to an expression to negate it. &#42; Multiple field query expressions can be combined in one query by adding `AND` or `OR` operators between the expressions. If a boolean operator appears within a quoted string, it is not treated as special, it's just another part of the character string to be matched. You can prepend the `NOT` operator to an expression to negate it. Fields available for filtering are: - user_id. For example, `filter='user_id="user123"'`. - consent_artifact - state - revision_create_time - metadata. For example, `filter=Metadata(\"testkey\")=\"value\"` or `filter=HasMetadata(\"testkey\")`. */
							def withFilter(filter: String) = new listRevisions(req.addQueryStringParameters("filter" -> filter.toString))
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListConsentRevisionsResponse])
						}
						object listRevisions {
							def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, consentStoresId :PlayApi, consentsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): listRevisions = new listRevisions(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/consentStores/${consentStoresId}/consents/${consentsId}:listRevisions").addQueryStringParameters("name" -> name.toString))
							given Conversion[listRevisions, Future[Schema.ListConsentRevisionsResponse]] = (fun: listRevisions) => fun.apply()
						}
					}
					object attributeDefinitions {
						/** Creates a new Attribute definition in the parent consent store. */
						class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-healthcare""", """https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def withAttributeDefinition(body: Schema.AttributeDefinition) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.AttributeDefinition])
						}
						object create {
							def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, consentStoresId :PlayApi, parent: String, attributeDefinitionId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/consentStores/${consentStoresId}/attributeDefinitions").addQueryStringParameters("parent" -> parent.toString, "attributeDefinitionId" -> attributeDefinitionId.toString))
						}
						/** Deletes the specified Attribute definition. Fails if the Attribute definition is referenced by any User data mapping, or the latest revision of any Consent. */
						class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-healthcare""", """https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
						}
						object delete {
							def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, consentStoresId :PlayApi, attributeDefinitionsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/consentStores/${consentStoresId}/attributeDefinitions/${attributeDefinitionsId}").addQueryStringParameters("name" -> name.toString))
							given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
						}
						/** Gets the specified Attribute definition. */
						class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.AttributeDefinition]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-healthcare""", """https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.AttributeDefinition])
						}
						object get {
							def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, consentStoresId :PlayApi, attributeDefinitionsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/consentStores/${consentStoresId}/attributeDefinitions/${attributeDefinitionsId}").addQueryStringParameters("name" -> name.toString))
							given Conversion[get, Future[Schema.AttributeDefinition]] = (fun: get) => fun.apply()
						}
						/** Updates the specified Attribute definition. */
						class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-healthcare""", """https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def withAttributeDefinition(body: Schema.AttributeDefinition) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.AttributeDefinition])
						}
						object patch {
							def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, consentStoresId :PlayApi, attributeDefinitionsId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/consentStores/${consentStoresId}/attributeDefinitions/${attributeDefinitionsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
						}
						/** Lists the Attribute definitions in the specified consent store. */
						class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListAttributeDefinitionsResponse]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-healthcare""", """https://www.googleapis.com/auth/cloud-platform""")
							/** Optional. Limit on the number of Attribute definitions to return in a single response. If not specified, 100 is used. May not be larger than 1000.<br>Format: int32 */
							def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
							/** Optional. Token to retrieve the next page of results or empty to get the first page. */
							def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
							/** Optional. Restricts the attributes returned to those matching a filter. The only field available for filtering is `category`. For example, `filter=category=\"REQUEST\"`. */
							def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListAttributeDefinitionsResponse])
						}
						object list {
							def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, consentStoresId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/consentStores/${consentStoresId}/attributeDefinitions").addQueryStringParameters("parent" -> parent.toString))
							given Conversion[list, Future[Schema.ListAttributeDefinitionsResponse]] = (fun: list) => fun.apply()
						}
					}
					object userDataMappings {
						/** Creates a new User data mapping in the parent consent store. */
						class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-healthcare""", """https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def withUserDataMapping(body: Schema.UserDataMapping) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.UserDataMapping])
						}
						object create {
							def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, consentStoresId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/consentStores/${consentStoresId}/userDataMappings").addQueryStringParameters("parent" -> parent.toString))
						}
						/** Deletes the specified User data mapping. */
						class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-healthcare""", """https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
						}
						object delete {
							def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, consentStoresId :PlayApi, userDataMappingsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/consentStores/${consentStoresId}/userDataMappings/${userDataMappingsId}").addQueryStringParameters("name" -> name.toString))
							given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
						}
						/** Gets the specified User data mapping. */
						class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.UserDataMapping]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-healthcare""", """https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.UserDataMapping])
						}
						object get {
							def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, consentStoresId :PlayApi, userDataMappingsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/consentStores/${consentStoresId}/userDataMappings/${userDataMappingsId}").addQueryStringParameters("name" -> name.toString))
							given Conversion[get, Future[Schema.UserDataMapping]] = (fun: get) => fun.apply()
						}
						/** Updates the specified User data mapping. */
						class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-healthcare""", """https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def withUserDataMapping(body: Schema.UserDataMapping) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.UserDataMapping])
						}
						object patch {
							def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, consentStoresId :PlayApi, userDataMappingsId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/consentStores/${consentStoresId}/userDataMappings/${userDataMappingsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
						}
						/** Archives the specified User data mapping. */
						class archive(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-healthcare""", """https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def withArchiveUserDataMappingRequest(body: Schema.ArchiveUserDataMappingRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.ArchiveUserDataMappingResponse])
						}
						object archive {
							def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, consentStoresId :PlayApi, userDataMappingsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): archive = new archive(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/consentStores/${consentStoresId}/userDataMappings/${userDataMappingsId}:archive").addQueryStringParameters("name" -> name.toString))
						}
						/** Lists the User data mappings in the specified consent store. */
						class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListUserDataMappingsResponse]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-healthcare""", """https://www.googleapis.com/auth/cloud-platform""")
							/** Optional. Limit on the number of User data mappings to return in a single response. If not specified, 100 is used. May not be larger than 1000.<br>Format: int32 */
							def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
							/** Optional. Token to retrieve the next page of results, or empty to get the first page. */
							def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
							/** Optional. Restricts the User data mappings returned to those matching a filter. The following syntax is available: &#42; A string field value can be written as text inside quotation marks, for example `"query text"`. The only valid relational operation for text fields is equality (`=`), where text is searched within the field, rather than having the field be equal to the text. For example, `"Comment = great"` returns messages with `great` in the comment field. &#42; A number field value can be written as an integer, a decimal, or an exponential. The valid relational operators for number fields are the equality operator (`=`), along with the less than/greater than operators (`<`, `<=`, `>`, `>=`). Note that there is no inequality (`!=`) operator. You can prepend the `NOT` operator to an expression to negate it. &#42; A date field value must be written in `yyyy-mm-dd` form. Fields with date and time use the RFC3339 time format. Leading zeros are required for one-digit months and days. The valid relational operators for date fields are the equality operator (`=`) , along with the less than/greater than operators (`<`, `<=`, `>`, `>=`). Note that there is no inequality (`!=`) operator. You can prepend the `NOT` operator to an expression to negate it. &#42; Multiple field query expressions can be combined in one query by adding `AND` or `OR` operators between the expressions. If a boolean operator appears within a quoted string, it is not treated as special, it's just another part of the character string to be matched. You can prepend the `NOT` operator to an expression to negate it. The fields available for filtering are: - data_id - user_id. For example, `filter=user_id=\"user123\"`. - archived - archive_time */
							def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListUserDataMappingsResponse])
						}
						object list {
							def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, consentStoresId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/consentStores/${consentStoresId}/userDataMappings").addQueryStringParameters("parent" -> parent.toString))
							given Conversion[list, Future[Schema.ListUserDataMappingsResponse]] = (fun: list) => fun.apply()
						}
					}
					object consentArtifacts {
						/** Creates a new Consent artifact in the parent consent store. */
						class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-healthcare""", """https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def withConsentArtifact(body: Schema.ConsentArtifact) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.ConsentArtifact])
						}
						object create {
							def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, consentStoresId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/consentStores/${consentStoresId}/consentArtifacts").addQueryStringParameters("parent" -> parent.toString))
						}
						/** Gets the specified Consent artifact. */
						class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ConsentArtifact]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-healthcare""", """https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ConsentArtifact])
						}
						object get {
							def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, consentStoresId :PlayApi, consentArtifactsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/consentStores/${consentStoresId}/consentArtifacts/${consentArtifactsId}").addQueryStringParameters("name" -> name.toString))
							given Conversion[get, Future[Schema.ConsentArtifact]] = (fun: get) => fun.apply()
						}
						/** Deletes the specified Consent artifact. Fails if the artifact is referenced by the latest revision of any Consent. */
						class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-healthcare""", """https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
						}
						object delete {
							def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, consentStoresId :PlayApi, consentArtifactsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/consentStores/${consentStoresId}/consentArtifacts/${consentArtifactsId}").addQueryStringParameters("name" -> name.toString))
							given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
						}
						/** Lists the Consent artifacts in the specified consent store. */
						class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListConsentArtifactsResponse]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-healthcare""", """https://www.googleapis.com/auth/cloud-platform""")
							/** Optional. Limit on the number of consent artifacts to return in a single response. If not specified, 100 is used. May not be larger than 1000.<br>Format: int32 */
							def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
							/** Optional. The next_page_token value returned from the previous List request, if any. */
							def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
							/** Optional. Restricts the artifacts returned to those matching a filter. The following syntax is available: &#42; A string field value can be written as text inside quotation marks, for example `"query text"`. The only valid relational operation for text fields is equality (`=`), where text is searched within the field, rather than having the field be equal to the text. For example, `"Comment = great"` returns messages with `great` in the comment field. &#42; A number field value can be written as an integer, a decimal, or an exponential. The valid relational operators for number fields are the equality operator (`=`), along with the less than/greater than operators (`<`, `<=`, `>`, `>=`). Note that there is no inequality (`!=`) operator. You can prepend the `NOT` operator to an expression to negate it. &#42; A date field value must be written in `yyyy-mm-dd` form. Fields with date and time use the RFC3339 time format. Leading zeros are required for one-digit months and days. The valid relational operators for date fields are the equality operator (`=`) , along with the less than/greater than operators (`<`, `<=`, `>`, `>=`). Note that there is no inequality (`!=`) operator. You can prepend the `NOT` operator to an expression to negate it. &#42; Multiple field query expressions can be combined in one query by adding `AND` or `OR` operators between the expressions. If a boolean operator appears within a quoted string, it is not treated as special, it's just another part of the character string to be matched. You can prepend the `NOT` operator to an expression to negate it. The fields available for filtering are: - user_id. For example, `filter=user_id=\"user123\"`. - consent_content_version - metadata. For example, `filter=Metadata(\"testkey\")=\"value\"` or `filter=HasMetadata(\"testkey\")`. */
							def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListConsentArtifactsResponse])
						}
						object list {
							def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, consentStoresId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/consentStores/${consentStoresId}/consentArtifacts").addQueryStringParameters("parent" -> parent.toString))
							given Conversion[list, Future[Schema.ListConsentArtifactsResponse]] = (fun: list) => fun.apply()
						}
					}
				}
				object fhirStores {
					/** Returns permissions that a caller has on the specified resource. If the resource does not exist, this will return an empty set of permissions, not a `NOT_FOUND` error. Note: This operation is designed to be used for building permission-aware UIs and command-line tools, not for authorization checking. This operation may "fail open" without warning. */
					class testIamPermissions(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-healthcare""", """https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withTestIamPermissionsRequest(body: Schema.TestIamPermissionsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.TestIamPermissionsResponse])
					}
					object testIamPermissions {
						def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, fhirStoresId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): testIamPermissions = new testIamPermissions(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/fhirStores/${fhirStoresId}:testIamPermissions").addQueryStringParameters("resource" -> resource.toString))
					}
					/** De-identifies data from the source store and writes it to the destination store. The metadata field type is OperationMetadata. If the request is successful, the response field type is DeidentifyFhirStoreSummary. If errors occur, error is set. Error details are also logged to Cloud Logging (see [Viewing error logs in Cloud Logging](https://cloud.google.com/healthcare/docs/how-tos/logging)). */
					class deidentify(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-healthcare""", """https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withDeidentifyFhirStoreRequest(body: Schema.DeidentifyFhirStoreRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
					}
					object deidentify {
						def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, fhirStoresId :PlayApi, sourceStore: String)(using signer: RequestSigner, ec: ExecutionContext): deidentify = new deidentify(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/fhirStores/${fhirStoresId}:deidentify").addQueryStringParameters("sourceStore" -> sourceStore.toString))
					}
					/** Export resources from the FHIR store to the specified destination. This method returns an Operation that can be used to track the status of the export by calling GetOperation. Immediate fatal errors appear in the error field, errors are also logged to Cloud Logging (see [Viewing error logs in Cloud Logging](https://cloud.google.com/healthcare/docs/how-tos/logging)). Otherwise, when the operation finishes, a detailed response of type ExportResourcesResponse is returned in the response field. The metadata field type for this operation is OperationMetadata. */
					class `export`(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-healthcare""", """https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withExportResourcesRequest(body: Schema.ExportResourcesRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
					}
					object `export` {
						def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, fhirStoresId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): `export` = new `export`(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/fhirStores/${fhirStoresId}:export").addQueryStringParameters("name" -> name.toString))
					}
					/** Rolls back resources from the FHIR store to the specified time. This method returns an Operation that can be used to track the status of the rollback by calling GetOperation. Immediate fatal errors appear in the error field, errors are also logged to Cloud Logging (see [Viewing error logs in Cloud Logging](https://cloud.google.com/healthcare/docs/how-tos/logging)). Otherwise, when the operation finishes, a detailed response of type RollbackFhirResourcesResponse is returned in the response field. The metadata field type for this operation is OperationMetadata. */
					class rollback(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-healthcare""", """https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withRollbackFhirResourcesRequest(body: Schema.RollbackFhirResourcesRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
					}
					object rollback {
						def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, fhirStoresId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): rollback = new rollback(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/fhirStores/${fhirStoresId}:rollback").addQueryStringParameters("name" -> name.toString))
					}
					/** Imports resources to the FHIR store by loading data from the specified sources. This method is optimized to load large quantities of data using import semantics that ignore some FHIR store configuration options and are not suitable for all use cases. It is primarily intended to load data into an empty FHIR store that is not being used by other clients. In cases where this method is not appropriate, consider using ExecuteBundle to load data. Every resource in the input must contain a client-supplied ID. Each resource is stored using the supplied ID regardless of the enable_update_create setting on the FHIR store. It is strongly advised not to include or encode any sensitive data such as patient identifiers in client-specified resource IDs. Those IDs are part of the FHIR resource path recorded in Cloud Audit Logs and Cloud Pub/Sub notifications. Those IDs can also be contained in reference fields within other resources. The import process does not enforce referential integrity, regardless of the disable_referential_integrity setting on the FHIR store. This allows the import of resources with arbitrary interdependencies without considering grouping or ordering, but if the input data contains invalid references or if some resources fail to be imported, the FHIR store might be left in a state that violates referential integrity. The import process does not trigger Pub/Sub notification or BigQuery streaming update, regardless of how those are configured on the FHIR store. If a resource with the specified ID already exists, the most recent version of the resource is overwritten without creating a new historical version, regardless of the disable_resource_versioning setting on the FHIR store. If transient failures occur during the import, it's possible that successfully imported resources will be overwritten more than once. The import operation is idempotent unless the input data contains multiple valid resources with the same ID but different contents. In that case, after the import completes, the store contains exactly one resource with that ID but there is no ordering guarantee on which version of the contents it will have. The operation result counters do not count duplicate IDs as an error and count one success for each resource in the input, which might result in a success count larger than the number of resources in the FHIR store. This often occurs when importing data organized in bundles produced by Patient-everything where each bundle contains its own copy of a resource such as Practitioner that might be referred to by many patients. If some resources fail to import, for example due to parsing errors, successfully imported resources are not rolled back. The location and format of the input data is specified by the parameters in ImportResourcesRequest. Note that if no format is specified, this method assumes the `BUNDLE` format. When using the `BUNDLE` format this method ignores the `Bundle.type` field, except that `history` bundles are rejected, and does not apply any of the bundle processing semantics for batch or transaction bundles. Unlike in ExecuteBundle, transaction bundles are not executed as a single transaction and bundle-internal references are not rewritten. The bundle is treated as a collection of resources to be written as provided in `Bundle.entry.resource`, ignoring `Bundle.entry.request`. As an example, this allows the import of `searchset` bundles produced by a FHIR search or Patient-everything operation. This method returns an Operation that can be used to track the status of the import by calling GetOperation. Immediate fatal errors appear in the error field, errors are also logged to Cloud Logging (see [Viewing error logs in Cloud Logging](https://cloud.google.com/healthcare/docs/how-tos/logging)). Otherwise, when the operation finishes, a detailed response of type ImportResourcesResponse is returned in the response field. The metadata field type for this operation is OperationMetadata. */
					class `import`(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-healthcare""", """https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withImportResourcesRequest(body: Schema.ImportResourcesRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
					}
					object `import` {
						def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, fhirStoresId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): `import` = new `import`(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/fhirStores/${fhirStoresId}:import").addQueryStringParameters("name" -> name.toString))
					}
					/** Gets the access control policy for a resource. Returns an empty policy if the resource exists and does not have a policy set. */
					class getIamPolicy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Policy]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-healthcare""", """https://www.googleapis.com/auth/cloud-platform""")
						/** Optional. The maximum policy version that will be used to format the policy. Valid values are 0, 1, and 3. Requests specifying an invalid value will be rejected. Requests for policies with any conditional role bindings must specify version 3. Policies with no conditional role bindings may specify any valid value or leave the field unset. The policy in the response might use the policy version that you specified, or it might use a lower policy version. For example, if you specify version 3, but the policy has no conditional role bindings, the response uses version 1. To learn which resources support conditions in their IAM policies, see the [IAM documentation](https://cloud.google.com/iam/help/conditions/resource-policies).<br>Format: int32 */
						def withOptionsRequestedPolicyVersion(optionsRequestedPolicyVersion: Int) = new getIamPolicy(req.addQueryStringParameters("options.requestedPolicyVersion" -> optionsRequestedPolicyVersion.toString))
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Policy])
					}
					object getIamPolicy {
						def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, fhirStoresId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): getIamPolicy = new getIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/fhirStores/${fhirStoresId}:getIamPolicy").addQueryStringParameters("resource" -> resource.toString))
						given Conversion[getIamPolicy, Future[Schema.Policy]] = (fun: getIamPolicy) => fun.apply()
					}
					/** Deletes the specified FHIR store and removes all resources within it. */
					class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-healthcare""", """https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
					}
					object delete {
						def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, fhirStoresId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/fhirStores/${fhirStoresId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
					}
					/** Gets the configuration of the specified FHIR store. */
					class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.FhirStore]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-healthcare""", """https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.FhirStore])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, fhirStoresId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/fhirStores/${fhirStoresId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.FhirStore]] = (fun: get) => fun.apply()
					}
					/** Updates the configuration of the specified FHIR store. */
					class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-healthcare""", """https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withFhirStore(body: Schema.FhirStore) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.FhirStore])
					}
					object patch {
						def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, fhirStoresId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/fhirStores/${fhirStoresId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
					}
					/** Lists the FHIR stores in the given dataset. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListFhirStoresResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-healthcare""", """https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListFhirStoresResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, parent: String, pageSize: Int, pageToken: String, filter: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/fhirStores").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "filter" -> filter.toString))
						given Conversion[list, Future[Schema.ListFhirStoresResponse]] = (fun: list) => fun.apply()
					}
					/** Creates a new FHIR store within the parent dataset. */
					class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-healthcare""", """https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withFhirStore(body: Schema.FhirStore) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.FhirStore])
					}
					object create {
						def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, parent: String, fhirStoreId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/fhirStores").addQueryStringParameters("parent" -> parent.toString, "fhirStoreId" -> fhirStoreId.toString))
					}
					/** Sets the access control policy on the specified resource. Replaces any existing policy. Can return `NOT_FOUND`, `INVALID_ARGUMENT`, and `PERMISSION_DENIED` errors. */
					class setIamPolicy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-healthcare""", """https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withSetIamPolicyRequest(body: Schema.SetIamPolicyRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
					}
					object setIamPolicy {
						def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, fhirStoresId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): setIamPolicy = new setIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/fhirStores/${fhirStoresId}:setIamPolicy").addQueryStringParameters("resource" -> resource.toString))
					}
					/** Gets metrics associated with the FHIR store. */
					class getFHIRStoreMetrics(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.FhirStoreMetrics]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-healthcare""", """https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.FhirStoreMetrics])
					}
					object getFHIRStoreMetrics {
						def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, fhirStoresId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): getFHIRStoreMetrics = new getFHIRStoreMetrics(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/fhirStores/${fhirStoresId}:getFHIRStoreMetrics").addQueryStringParameters("name" -> name.toString))
						given Conversion[getFHIRStoreMetrics, Future[Schema.FhirStoreMetrics]] = (fun: getFHIRStoreMetrics) => fun.apply()
					}
					object fhir {
						/** Gets the contents of a version (current or historical) of a FHIR Binary resource by version ID. This method can be used to retrieve a Binary resource version either by using the FHIR JSON mimetype as the value for the Accept header, or as a raw data stream. If the FHIR Accept type is used this method will return a Binary resource with the data base64-encoded, regardless of how the resource version was created. The resource data can be retrieved in base64-decoded form if the Accept type of the request matches the value of the resource version's `contentType` field. The definition of the Binary REST API can be found at https://hl7.org/fhir/binary.html#rest. */
						class Binary_vread(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.HttpBody]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-healthcare""", """https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.HttpBody])
						}
						object Binary_vread {
							def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, fhirStoresId :PlayApi, BinaryId :PlayApi, _historyId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): Binary_vread = new Binary_vread(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/fhirStores/${fhirStoresId}/fhir/Binary/${BinaryId}/_history/${_historyId}").addQueryStringParameters("name" -> name.toString))
							given Conversion[Binary_vread, Future[Schema.HttpBody]] = (fun: Binary_vread) => fun.apply()
						}
						/** Lists all the versions of a resource (including the current version and deleted versions) from the FHIR store. Implements the per-resource form of the FHIR standard history interaction ([DSTU2](http://hl7.org/implement/standards/fhir/DSTU2/http.html#history), [STU3](http://hl7.org/implement/standards/fhir/STU3/http.html#history), [R4](http://hl7.org/implement/standards/fhir/R4/http.html#history)). On success, the response body contains a JSON-encoded representation of a `Bundle` resource of type `history`, containing the version history sorted from most recent to oldest versions. Errors generated by the FHIR store contain a JSON-encoded `OperationOutcome` resource describing the reason for the error. If the request cannot be mapped to a valid API method on a FHIR store, a generic GCP error might be returned instead. For samples that show how to call `history`, see [Listing FHIR resource versions](https://cloud.google.com/healthcare/docs/how-tos/fhir-resources#listing_fhir_resource_versions). */
						class history(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.HttpBody]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-healthcare""", """https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.HttpBody])
						}
						object history {
							def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, fhirStoresId :PlayApi, fhirId :PlayApi, fhirId1 :PlayApi, name: String, _count: Int, _since: String, _at: String, _page_token: String)(using signer: RequestSigner, ec: ExecutionContext): history = new history(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/fhirStores/${fhirStoresId}/fhir/${fhirId}/${fhirId1}/_history").addQueryStringParameters("name" -> name.toString, "_count" -> _count.toString, "_since" -> _since.toString, "_at" -> _at.toString, "_page_token" -> _page_token.toString))
							given Conversion[history, Future[Schema.HttpBody]] = (fun: history) => fun.apply()
						}
						/** If a resource is found with the identifier specified in the query parameters, updates the entire contents of that resource. Implements the FHIR standard conditional update interaction, limited to searching by resource identifier. Search term for identifier should be in the pattern `identifier=system|value` or `identifier=value` - similar to the `search` method on resources with a specific identifier. If the search criteria identify more than one match, the request returns a `412 Precondition Failed` error. If the search criteria identify zero matches, and the supplied resource body contains an `id`, and the FHIR store has enable_update_create set, creates the resource with the client-specified ID. It is strongly advised not to include or encode any sensitive data such as patient identifiers in client-specified resource IDs. Those IDs are part of the FHIR resource path recorded in Cloud Audit Logs and Pub/Sub notifications. Those IDs can also be contained in reference fields within other resources. If the search criteria identify zero matches, and the supplied resource body does not contain an `id`, the resource is created with a server-assigned ID as per the create method. The request body must contain a JSON-encoded FHIR resource, and the request headers must contain `Content-Type: application/fhir+json`. On success, the response body contains a JSON-encoded representation of the updated resource, including the server-assigned version ID. Errors generated by the FHIR store contain a JSON-encoded `OperationOutcome` resource describing the reason for the error. If the request cannot be mapped to a valid API method on a FHIR store, a generic GCP error might be returned instead. For samples that show how to call `conditionalUpdate`, see [Conditionally updating a FHIR resource](https://cloud.google.com/healthcare/docs/how-tos/fhir-resources#conditionally_updating_a_fhir_resource). */
						class conditionalUpdate(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-healthcare""", """https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def withHttpBody(body: Schema.HttpBody) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.HttpBody])
						}
						object conditionalUpdate {
							def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, fhirStoresId :PlayApi, fhirId :PlayApi, parent: String, `type`: String)(using signer: RequestSigner, ec: ExecutionContext): conditionalUpdate = new conditionalUpdate(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/fhirStores/${fhirStoresId}/fhir/${fhirId}").addQueryStringParameters("parent" -> parent.toString, "type" -> `type`.toString))
						}
						/** Validates an input FHIR resource's conformance to its profiles and the profiles configured on the FHIR store. Implements the FHIR extended operation $validate ([DSTU2](http://hl7.org/implement/standards/fhir/DSTU2/resource-operations.html#validate), [STU3](http://hl7.org/implement/standards/fhir/STU3/resource-operations.html#validate), or [R4](http://hl7.org/implement/standards/fhir/R4/resource-operation-validate.html)). The request body must contain a JSON-encoded FHIR resource, and the request headers must contain `Content-Type: application/fhir+json`. The `Parameters` input syntax is not supported. The `profile` query parameter can be used to request that the resource only be validated against a specific profile. If a profile with the given URL cannot be found in the FHIR store then an error is returned. Errors generated by validation contain a JSON-encoded `OperationOutcome` resource describing the reason for the error. If the request cannot be mapped to a valid API method on a FHIR store, a generic GCP error might be returned instead. */
						class Resource_validate(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-healthcare""", """https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def withHttpBody(body: Schema.HttpBody) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.HttpBody])
						}
						object Resource_validate {
							def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, fhirStoresId :PlayApi, fhirId :PlayApi, parent: String, `type`: String, profile: String)(using signer: RequestSigner, ec: ExecutionContext): Resource_validate = new Resource_validate(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/fhirStores/${fhirStoresId}/fhir/${fhirId}/$$validate").addQueryStringParameters("parent" -> parent.toString, "type" -> `type`.toString, "profile" -> profile.toString))
						}
						/** Deletes a FHIR resource that match an identifier search query. Implements the FHIR standard conditional delete interaction, limited to searching by resource identifier. If multiple resources match, 412 Precondition Failed error will be returned. Search term for identifier should be in the pattern `identifier=system|value` or `identifier=value` - similar to the `search` method on resources with a specific identifier. Note: Unless resource versioning is disabled by setting the disable_resource_versioning flag on the FHIR store, the deleted resource is moved to a history repository that can still be retrieved through vread and related methods, unless they are removed by the purge method. For samples that show how to call `conditionalDelete`, see [Conditionally deleting a FHIR resource](https://cloud.google.com/healthcare/docs/how-tos/fhir-resources#conditionally_deleting_a_fhir_resource). */
						class conditionalDelete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-healthcare""", """https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
						}
						object conditionalDelete {
							def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, fhirStoresId :PlayApi, fhirId :PlayApi, parent: String, `type`: String)(using signer: RequestSigner, ec: ExecutionContext): conditionalDelete = new conditionalDelete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/fhirStores/${fhirStoresId}/fhir/${fhirId}").addQueryStringParameters("parent" -> parent.toString, "type" -> `type`.toString))
							given Conversion[conditionalDelete, Future[Schema.Empty]] = (fun: conditionalDelete) => fun.apply()
						}
						/** Deletes a FHIR resource. Implements the FHIR standard delete interaction ([DSTU2](http://hl7.org/implement/standards/fhir/DSTU2/http.html#delete), [STU3](http://hl7.org/implement/standards/fhir/STU3/http.html#delete), [R4](http://hl7.org/implement/standards/fhir/R4/http.html#delete)). Note: Unless resource versioning is disabled by setting the disable_resource_versioning flag on the FHIR store, the deleted resources will be moved to a history repository that can still be retrieved through vread and related methods, unless they are removed by the purge method. For samples that show how to call `delete`, see [Deleting a FHIR resource](https://cloud.google.com/healthcare/docs/how-tos/fhir-resources#deleting_a_fhir_resource). */
						class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.HttpBody]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-healthcare""", """https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.HttpBody])
						}
						object delete {
							def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, fhirStoresId :PlayApi, fhirId :PlayApi, fhirId1 :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/fhirStores/${fhirStoresId}/fhir/${fhirId}/${fhirId1}").addQueryStringParameters("name" -> name.toString))
							given Conversion[delete, Future[Schema.HttpBody]] = (fun: delete) => fun.apply()
						}
						/** If a resource is found with the identifier specified in the query parameters, updates part of that resource by applying the operations specified in a [JSON Patch](http://jsonpatch.com/) document. Implements the FHIR standard conditional patch interaction, limited to searching by resource identifier. DSTU2 doesn't define a conditional patch method, but the server supports it in the same way it supports STU3. Search term for identifier should be in the pattern `identifier=system|value` or `identifier=value` - similar to the `search` method on resources with a specific identifier. If the search criteria identify more than one match, the request returns a `412 Precondition Failed` error. The request body must contain a JSON Patch document, and the request headers must contain `Content-Type: application/json-patch+json`. On success, the response body contains a JSON-encoded representation of the updated resource, including the server-assigned version ID. Errors generated by the FHIR store contain a JSON-encoded `OperationOutcome` resource describing the reason for the error. If the request cannot be mapped to a valid API method on a FHIR store, a generic GCP error might be returned instead. For samples that show how to call `conditionalPatch`, see [Conditionally patching a FHIR resource](https://cloud.google.com/healthcare/docs/how-tos/fhir-resources#conditionally_patching_a_fhir_resource). */
						class conditionalPatch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-healthcare""", """https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def withHttpBody(body: Schema.HttpBody) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.HttpBody])
						}
						object conditionalPatch {
							def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, fhirStoresId :PlayApi, fhirId :PlayApi, parent: String, `type`: String)(using signer: RequestSigner, ec: ExecutionContext): conditionalPatch = new conditionalPatch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/fhirStores/${fhirStoresId}/fhir/${fhirId}").addQueryStringParameters("parent" -> parent.toString, "type" -> `type`.toString))
						}
						/** Gets the contents of a version (current or historical) of a FHIR resource by version ID. Implements the FHIR standard vread interaction ([DSTU2](http://hl7.org/implement/standards/fhir/DSTU2/http.html#vread), [STU3](http://hl7.org/implement/standards/fhir/STU3/http.html#vread), [R4](http://hl7.org/implement/standards/fhir/R4/http.html#vread)). On success, the response body contains a JSON-encoded representation of the resource. Errors generated by the FHIR store contain a JSON-encoded `OperationOutcome` resource describing the reason for the error. If the request cannot be mapped to a valid API method on a FHIR store, a generic GCP error might be returned instead. For samples that show how to call `vread`, see [Retrieving a FHIR resource version](https://cloud.google.com/healthcare/docs/how-tos/fhir-resources#retrieving_a_fhir_resource_version). */
						class vread(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.HttpBody]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-healthcare""", """https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.HttpBody])
						}
						object vread {
							def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, fhirStoresId :PlayApi, fhirId :PlayApi, fhirId1 :PlayApi, _historyId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): vread = new vread(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/fhirStores/${fhirStoresId}/fhir/${fhirId}/${fhirId1}/_history/${_historyId}").addQueryStringParameters("name" -> name.toString))
							given Conversion[vread, Future[Schema.HttpBody]] = (fun: vread) => fun.apply()
						}
						/** Deletes all the historical versions of a resource (excluding the current version) from the FHIR store. To remove all versions of a resource, first delete the current version and then call this method. This is not a FHIR standard operation. For samples that show how to call `Resource-purge`, see [Deleting historical versions of a FHIR resource](https://cloud.google.com/healthcare/docs/how-tos/fhir-resources#deleting_historical_versions_of_a_fhir_resource). */
						class Resource_purge(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-healthcare""", """https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
						}
						object Resource_purge {
							def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, fhirStoresId :PlayApi, fhirId :PlayApi, fhirId1 :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): Resource_purge = new Resource_purge(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/fhirStores/${fhirStoresId}/fhir/${fhirId}/${fhirId1}/$$purge").addQueryStringParameters("name" -> name.toString))
							given Conversion[Resource_purge, Future[Schema.Empty]] = (fun: Resource_purge) => fun.apply()
						}
						/** Updates part of an existing resource by applying the operations specified in a [JSON Patch](http://jsonpatch.com/) document. Implements the FHIR standard patch interaction ([STU3](http://hl7.org/implement/standards/fhir/STU3/http.html#patch), [R4](http://hl7.org/implement/standards/fhir/R4/http.html#patch)). DSTU2 doesn't define a patch method, but the server supports it in the same way it supports STU3. The request body must contain a JSON Patch document, and the request headers must contain `Content-Type: application/json-patch+json`. On success, the response body contains a JSON-encoded representation of the updated resource, including the server-assigned version ID. Errors generated by the FHIR store contain a JSON-encoded `OperationOutcome` resource describing the reason for the error. If the request cannot be mapped to a valid API method on a FHIR store, a generic GCP error might be returned instead. For samples that show how to call `patch`, see [Patching a FHIR resource](https://cloud.google.com/healthcare/docs/how-tos/fhir-resources#patching_a_fhir_resource). */
						class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-healthcare""", """https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def withHttpBody(body: Schema.HttpBody) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.HttpBody])
						}
						object patch {
							def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, fhirStoresId :PlayApi, fhirId :PlayApi, fhirId1 :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/fhirStores/${fhirStoresId}/fhir/${fhirId}/${fhirId1}").addQueryStringParameters("name" -> name.toString))
						}
						/** Searches for resources in the given FHIR store according to criteria specified as query parameters. Implements the FHIR standard search interaction ([DSTU2](http://hl7.org/implement/standards/fhir/DSTU2/http.html#search), [STU3](http://hl7.org/implement/standards/fhir/STU3/http.html#search), [R4](http://hl7.org/implement/standards/fhir/R4/http.html#search)) using the search semantics described in the FHIR Search specification ([DSTU2](http://hl7.org/implement/standards/fhir/DSTU2/search.html), [STU3](http://hl7.org/implement/standards/fhir/STU3/search.html), [R4](http://hl7.org/implement/standards/fhir/R4/search.html)). Supports four methods of search defined by the specification: &#42; `GET [base]?[parameters]` to search across all resources. &#42; `GET [base]/[type]?[parameters]` to search resources of a specified type. &#42; `POST [base]/_search?[parameters]` as an alternate form having the same semantics as the `GET` method across all resources. &#42; `POST [base]/[type]/_search?[parameters]` as an alternate form having the same semantics as the `GET` method for the specified type. The `GET` and `POST` methods do not support compartment searches. The `POST` method does not support `application/x-www-form-urlencoded` search parameters. On success, the response body contains a JSON-encoded representation of a `Bundle` resource of type `searchset`, containing the results of the search. Errors generated by the FHIR store contain a JSON-encoded `OperationOutcome` resource describing the reason for the error. If the request cannot be mapped to a valid API method on a FHIR store, a generic GCP error might be returned instead. The server's capability statement, retrieved through capabilities, indicates what search parameters are supported on each FHIR resource. A list of all search parameters defined by the specification can be found in the FHIR Search Parameter Registry ([STU3](http://hl7.org/implement/standards/fhir/STU3/searchparameter-registry.html), [R4](http://hl7.org/implement/standards/fhir/R4/searchparameter-registry.html)). FHIR search parameters for DSTU2 can be found on each resource's definition page. Supported search modifiers: `:missing`, `:exact`, `:contains`, `:text`, `:in`, `:not-in`, `:above`, `:below`, `:[type]`, `:not`, and `recurse` (DSTU2 and STU3) or `:iterate` (R4). Supported search result parameters: `_sort`, `_count`, `_include`, `_revinclude`, `_summary=text`, `_summary=data`, and `_elements`. The maximum number of search results returned defaults to 100, which can be overridden by the `_count` parameter up to a maximum limit of 1000. The server might return fewer resources than requested to prevent excessively large responses. If there are additional results, the returned `Bundle` contains a link of `relation` "next", which has a `_page_token` parameter for an opaque pagination token that can be used to retrieve the next page. Resources with a total size larger than 5MB or a field count larger than 50,000 might not be fully searchable as the server might trim its generated search index in those cases. Note: FHIR resources are indexed asynchronously, so there might be a slight delay between the time a resource is created or changed, and the time when the change reflects in search results. The only exception is resource identifier data, which is indexed synchronously as a special index. As a result, searching using resource identifier is not subject to indexing delay. To use the special synchronous index, the search term for identifier should be in the pattern `identifier=[system]|[value]` or `identifier=[value]`, and any of the following search result parameters can be used: &#42; `_count` &#42; `_include` &#42; `_revinclude` &#42; `_summary` &#42; `_elements` If your query contains any other search parameters, the standard asynchronous index will be used instead. Note that searching against the special index is optimized for resolving a small number of matches. The search isn't optimized if your identifier search criteria matches a large number (i.e. more than 2,000) of resources. For a search query that will match a large number of resources, you can avoiding using the special synchronous index by including an additional `_sort` parameter in your query. Use `_sort=-_lastUpdated` if you want to keep the default sorting order. Note: The special synchronous identifier index are currently disabled for DocumentReference and DocumentManifest searches. For samples and detailed information, see [Searching for FHIR resources](https://cloud.google.com/healthcare/docs/how-tos/fhir-search) and [Advanced FHIR search features](https://cloud.google.com/healthcare/docs/how-tos/fhir-advanced-search). */
						class search(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-healthcare""", """https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def withSearchResourcesRequest(body: Schema.SearchResourcesRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.HttpBody])
						}
						object search {
							def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, fhirStoresId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): search = new search(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/fhirStores/${fhirStoresId}/fhir/_search").addQueryStringParameters("parent" -> parent.toString))
						}
						/** Gets the contents of a FHIR resource. Implements the FHIR standard read interaction ([DSTU2](http://hl7.org/implement/standards/fhir/DSTU2/http.html#read), [STU3](http://hl7.org/implement/standards/fhir/STU3/http.html#read), [R4](http://hl7.org/implement/standards/fhir/R4/http.html#read)). Also supports the FHIR standard conditional read interaction ([DSTU2](http://hl7.org/implement/standards/fhir/DSTU2/http.html#cread), [STU3](http://hl7.org/implement/standards/fhir/STU3/http.html#cread), [R4](http://hl7.org/implement/standards/fhir/R4/http.html#cread)) specified by supplying an `If-Modified-Since` header with a date/time value or an `If-None-Match` header with an ETag value. On success, the response body contains a JSON-encoded representation of the resource. Errors generated by the FHIR store contain a JSON-encoded `OperationOutcome` resource describing the reason for the error. If the request cannot be mapped to a valid API method on a FHIR store, a generic GCP error might be returned instead. For samples that show how to call `read`, see [Getting a FHIR resource](https://cloud.google.com/healthcare/docs/how-tos/fhir-resources#getting_a_fhir_resource). */
						class read(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.HttpBody]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-healthcare""", """https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.HttpBody])
						}
						object read {
							def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, fhirStoresId :PlayApi, fhirId :PlayApi, fhirId1 :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): read = new read(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/fhirStores/${fhirStoresId}/fhir/${fhirId}/${fhirId1}").addQueryStringParameters("name" -> name.toString))
							given Conversion[read, Future[Schema.HttpBody]] = (fun: read) => fun.apply()
						}
						/** Gets the FHIR capability statement ([STU3](http://hl7.org/implement/standards/fhir/STU3/capabilitystatement.html), [R4](http://hl7.org/implement/standards/fhir/R4/capabilitystatement.html)), or the [conformance statement](http://hl7.org/implement/standards/fhir/DSTU2/conformance.html) in the DSTU2 case for the store, which contains a description of functionality supported by the server. Implements the FHIR standard capabilities interaction ([STU3](http://hl7.org/implement/standards/fhir/STU3/http.html#capabilities), [R4](http://hl7.org/implement/standards/fhir/R4/http.html#capabilities)), or the [conformance interaction](http://hl7.org/implement/standards/fhir/DSTU2/http.html#conformance) in the DSTU2 case. On success, the response body contains a JSON-encoded representation of a `CapabilityStatement` resource. */
						class capabilities(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.HttpBody]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-healthcare""", """https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.HttpBody])
						}
						object capabilities {
							def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, fhirStoresId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): capabilities = new capabilities(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/fhirStores/${fhirStoresId}/fhir/metadata").addQueryStringParameters("name" -> name.toString))
							given Conversion[capabilities, Future[Schema.HttpBody]] = (fun: capabilities) => fun.apply()
						}
						/** Updates the entire contents of a Binary resource. If the specified resource does not exist and the FHIR store has enable_update_create set, creates the resource with the client-specified ID. It is strongly advised not to include or encode any sensitive data such as patient identifiers in client-specified resource IDs. Those IDs are part of the FHIR resource path recorded in Cloud Audit Logs and Pub/Sub notifications. Those IDs can also be contained in reference fields within other resources. This method can be used to update a Binary resource either by using one of the accepted FHIR JSON content types, or as a raw data stream. If a resource is updated with this method using the FHIR content type this method's behavior is the same as `update`. If a resource type other than Binary is used in the request it will be treated in the same way as non-FHIR data. When a non-FHIR content type is used in the request, a Binary resource will be generated using the ID from the resource path, and the uploaded data will be stored in the `content` field (`DSTU2` and `STU3`), or the `data` field (`R4`). The Binary resource's `contentType` will be filled in using the value of the `Content-Type` header, and the `securityContext` field (not present in `DSTU2`) will be populated from the `X-Security-Context` header if it exists. At this time `securityContext` has no special behavior in the Cloud Healthcare API. Note: the limit on data ingested through this method is 2 GB. For best performance, use a non-FHIR data type instead of wrapping the data in a Binary resource. Some of the Healthcare API features, such as [exporting to BigQuery](https://cloud.google.com/healthcare-api/docs/how-tos/fhir-export-bigquery) or [Pub/Sub notifications](https://cloud.google.com/healthcare-api/docs/fhir-pubsub#behavior_when_a_fhir_resource_is_too_large_or_traffic_is_high) with full resource content, do not support Binary resources that are larger than 10 MB. In these cases the resource's `data` field will be omitted. Instead, the "http://hl7.org/fhir/StructureDefinition/data-absent-reason" extension will be present to indicate that including the data is `unsupported`. On success, an empty 200 OK response will be returned, or a 201 Created if the resource did not exit. The resource's ID and version are returned in the Location header. Using `Prefer: representation=resource` is not allowed for this method. The definition of the Binary REST API can be found at https://hl7.org/fhir/binary.html#rest. */
						class Binary_update(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-healthcare""", """https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def withHttpBody(body: Schema.HttpBody) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.HttpBody])
						}
						object Binary_update {
							def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, fhirStoresId :PlayApi, BinaryId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): Binary_update = new Binary_update(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/fhirStores/${fhirStoresId}/fhir/Binary/${BinaryId}").addQueryStringParameters("name" -> name.toString))
						}
						/** Executes all the requests in the given Bundle. Implements the FHIR standard batch/transaction interaction ([DSTU2](https://hl7.org/implement/standards/fhir/DSTU2/http.html#transaction), [STU3](https://hl7.org/implement/standards/fhir/STU3/http.html#transaction), [R4](https://hl7.org/implement/standards/fhir/R4/http.html#transaction)). Supports all interactions within a bundle, except search. This method accepts Bundles of type `batch` and `transaction`, processing them according to the batch processing rules ([DSTU2](https://hl7.org/implement/standards/fhir/DSTU2/http.html#2.1.0.16.1), [STU3](https://hl7.org/implement/standards/fhir/STU3/http.html#2.21.0.17.1), [R4](https://hl7.org/implement/standards/fhir/R4/http.html#brules)) and transaction processing rules ([DSTU2](https://hl7.org/implement/standards/fhir/DSTU2/http.html#2.1.0.16.2), [STU3](https://hl7.org/implement/standards/fhir/STU3/http.html#2.21.0.17.2), [R4](https://hl7.org/implement/standards/fhir/R4/http.html#trules)). The request body must contain a JSON-encoded FHIR `Bundle` resource, and the request headers must contain `Content-Type: application/fhir+json`. For a batch bundle or a successful transaction, the response body contains a JSON-encoded representation of a `Bundle` resource of type `batch-response` or `transaction-response` containing one entry for each entry in the request, with the outcome of processing the entry. In the case of an error for a transaction bundle, the response body contains a JSON-encoded `OperationOutcome` resource describing the reason for the error. If the request cannot be mapped to a valid API method on a FHIR store, a generic GCP error might be returned instead. This method checks permissions for each request in the bundle. The `executeBundle` permission is required to call this method, but you must also grant sufficient permissions to execute the individual requests in the bundle. For example, if the bundle contains a request to create a FHIR resource, the caller must also have been granted the `healthcare.fhirResources.create` permission. You can use audit logs to view the permissions for `executeBundle` and each request in the bundle. For more information, see [Viewing Cloud Audit logs](https://cloud.google.com/healthcare-api/docs/how-tos/audit-logging). For samples that show how to call `executeBundle`, see [Managing FHIR resources using FHIR bundles](https://cloud.google.com/healthcare/docs/how-tos/fhir-bundles). */
						class executeBundle(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-healthcare""", """https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def withHttpBody(body: Schema.HttpBody) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.HttpBody])
						}
						object executeBundle {
							def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, fhirStoresId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): executeBundle = new executeBundle(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/fhirStores/${fhirStoresId}/fhir").addQueryStringParameters("parent" -> parent.toString))
						}
						/** Gets the contents of a FHIR Binary resource. This method can be used to retrieve a Binary resource either by using the FHIR JSON mimetype as the value for the Accept header, or as a raw data stream. If the FHIR Accept type is used this method will return a Binary resource with the data base64-encoded, regardless of how the resource was created. The resource data can be retrieved in base64-decoded form if the Accept type of the request matches the value of the resource's `contentType` field. The definition of the Binary REST API can be found at https://hl7.org/fhir/binary.html#rest. */
						class Binary_read(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.HttpBody]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-healthcare""", """https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.HttpBody])
						}
						object Binary_read {
							def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, fhirStoresId :PlayApi, BinaryId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): Binary_read = new Binary_read(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/fhirStores/${fhirStoresId}/fhir/Binary/${BinaryId}").addQueryStringParameters("name" -> name.toString))
							given Conversion[Binary_read, Future[Schema.HttpBody]] = (fun: Binary_read) => fun.apply()
						}
						/** Creates a FHIR resource. Implements the FHIR standard create interaction ([DSTU2](http://hl7.org/implement/standards/fhir/DSTU2/http.html#create), [STU3](http://hl7.org/implement/standards/fhir/STU3/http.html#create), [R4](http://hl7.org/implement/standards/fhir/R4/http.html#create)), which creates a new resource with a server-assigned resource ID. Also supports the FHIR standard conditional create interaction ([DSTU2](https://hl7.org/implement/standards/fhir/DSTU2/http.html#ccreate), [STU3](https://hl7.org/implement/standards/fhir/STU3/http.html#ccreate), [R4](https://hl7.org/implement/standards/fhir/R4/http.html#ccreate)), specified by supplying an `If-None-Exist` header containing a FHIR search query, limited to searching by resource identifier. If no resources match this search query, the server processes the create operation as normal. When using conditional create, the search term for identifier should be in the pattern `identifier=system|value` or `identifier=value` - similar to the `search` method on resources with a specific identifier. The request body must contain a JSON-encoded FHIR resource, and the request headers must contain `Content-Type: application/fhir+json`. On success, the response body contains a JSON-encoded representation of the resource as it was created on the server, including the server-assigned resource ID and version ID. Errors generated by the FHIR store contain a JSON-encoded `OperationOutcome` resource describing the reason for the error. If the request cannot be mapped to a valid API method on a FHIR store, a generic GCP error might be returned instead. For samples that show how to call `create`, see [Creating a FHIR resource](https://cloud.google.com/healthcare/docs/how-tos/fhir-resources#creating_a_fhir_resource). */
						class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-healthcare""", """https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def withHttpBody(body: Schema.HttpBody) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.HttpBody])
						}
						object create {
							def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, fhirStoresId :PlayApi, fhirId :PlayApi, parent: String, `type`: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/fhirStores/${fhirStoresId}/fhir/${fhirId}").addQueryStringParameters("parent" -> parent.toString, "type" -> `type`.toString))
						}
						/** Creates a FHIR Binary resource. This method can be used to create a Binary resource either by using one of the accepted FHIR JSON content types, or as a raw data stream. If a resource is created with this method using the FHIR content type this method's behavior is the same as [`fhir.create`](https://cloud.google.com/healthcare-api/docs/reference/rest/v1/projects.locations.datasets.fhirStores.fhir/create). If a resource type other than Binary is used in the request it's treated in the same way as non-FHIR data (e.g., images, zip archives, pdf files, documents). When a non-FHIR content type is used in the request, a Binary resource will be generated, and the uploaded data will be stored in the `content` field (`DSTU2` and `STU3`), or the `data` field (`R4`). The Binary resource's `contentType` will be filled in using the value of the `Content-Type` header, and the `securityContext` field (not present in `DSTU2`) will be populated from the `X-Security-Context` header if it exists. At this time `securityContext` has no special behavior in the Cloud Healthcare API. Note: the limit on data ingested through this method is 1 GB. For best performance, use a non-FHIR data type instead of wrapping the data in a Binary resource. Some of the Healthcare API features, such as [exporting to BigQuery](https://cloud.google.com/healthcare-api/docs/how-tos/fhir-export-bigquery) or [Pub/Sub notifications](https://cloud.google.com/healthcare-api/docs/fhir-pubsub#behavior_when_a_fhir_resource_is_too_large_or_traffic_is_high) with full resource content, do not support Binary resources that are larger than 10 MB. In these cases the resource's `data` field will be omitted. Instead, the "http://hl7.org/fhir/StructureDefinition/data-absent-reason" extension will be present to indicate that including the data is `unsupported`. On success, an empty `201 Created` response is returned. The newly created resource's ID and version are returned in the Location header. Using `Prefer: representation=resource` is not allowed for this method. The definition of the Binary REST API can be found at https://hl7.org/fhir/binary.html#rest. */
						class Binary_create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-healthcare""", """https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def withHttpBody(body: Schema.HttpBody) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.HttpBody])
						}
						object Binary_create {
							def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, fhirStoresId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): Binary_create = new Binary_create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/fhirStores/${fhirStoresId}/fhir/Binary").addQueryStringParameters("parent" -> parent.toString))
						}
						/** Retrieves a Patient resource and resources related to that patient. Implements the FHIR extended operation Patient-everything ([DSTU2](http://hl7.org/implement/standards/fhir/DSTU2/patient-operations.html#everything), [STU3](http://hl7.org/implement/standards/fhir/STU3/patient-operations.html#everything), [R4](http://hl7.org/implement/standards/fhir/R4/patient-operations.html#everything)). On success, the response body contains a JSON-encoded representation of a `Bundle` resource of type `searchset`, containing the results of the operation. Errors generated by the FHIR store contain a JSON-encoded `OperationOutcome` resource describing the reason for the error. If the request cannot be mapped to a valid API method on a FHIR store, a generic GCP error might be returned instead. The resources in scope for the response are: &#42; The patient resource itself. &#42; All the resources directly referenced by the patient resource. &#42; Resources directly referencing the patient resource that meet the inclusion criteria. The inclusion criteria are based on the membership rules in the patient compartment definition ([DSTU2](http://hl7.org/fhir/DSTU2/compartment-patient.html), [STU3](http://www.hl7.org/fhir/stu3/compartmentdefinition-patient.html), [R4](http://hl7.org/fhir/R4/compartmentdefinition-patient.html)), which details the eligible resource types and referencing search parameters. For samples that show how to call `Patient-everything`, see [Getting all patient compartment resources](https://cloud.google.com/healthcare/docs/how-tos/fhir-resources#getting_all_patient_compartment_resources). */
						class Patient_everything(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.HttpBody]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-healthcare""", """https://www.googleapis.com/auth/cloud-platform""")
							/** Optional. The response includes records subsequent to the start date. The date uses the format YYYY-MM-DD. If no start date is provided, all records prior to the end date are in scope. */
							def withStart(start: String) = new Patient_everything(req.addQueryStringParameters("start" -> start.toString))
							/** Optional. The response includes records prior to the end date. The date uses the format YYYY-MM-DD. If no end date is provided, all records subsequent to the start date are in scope. */
							def withEnd(end: String) = new Patient_everything(req.addQueryStringParameters("end" -> end.toString))
							/** Optional. Maximum number of resources in a page. If not specified, 100 is used. May not be larger than 1000.<br>Format: int32 */
							def with_count(_count: Int) = new Patient_everything(req.addQueryStringParameters("_count" -> _count.toString))
							/** Optional. If provided, only resources updated after this time are returned. The time uses the format YYYY-MM-DDThh:mm:ss.sss+zz:zz. For example, `2015-02-07T13:28:17.239+02:00` or `2017-01-01T00:00:00Z`. The time must be specified to the second and include a time zone. */
							def with_since(_since: String) = new Patient_everything(req.addQueryStringParameters("_since" -> _since.toString))
							/** Optional. String of comma-delimited FHIR resource types. If provided, only resources of the specified resource type(s) are returned. Specifying multiple `_type` parameters isn't supported. For example, the result of `_type=Observation&_type=Encounter` is undefined. Use `_type=Observation,Encounter` instead. */
							def with_type(_type: String) = new Patient_everything(req.addQueryStringParameters("_type" -> _type.toString))
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.HttpBody])
						}
						object Patient_everything {
							def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, fhirStoresId :PlayApi, PatientId :PlayApi, name: String, _page_token: String)(using signer: RequestSigner, ec: ExecutionContext): Patient_everything = new Patient_everything(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/fhirStores/${fhirStoresId}/fhir/Patient/${PatientId}/$$everything").addQueryStringParameters("name" -> name.toString, "_page_token" -> _page_token.toString))
							given Conversion[Patient_everything, Future[Schema.HttpBody]] = (fun: Patient_everything) => fun.apply()
						}
						/** Updates the entire contents of a resource. Implements the FHIR standard update interaction ([DSTU2](http://hl7.org/implement/standards/fhir/DSTU2/http.html#update), [STU3](http://hl7.org/implement/standards/fhir/STU3/http.html#update), [R4](http://hl7.org/implement/standards/fhir/R4/http.html#update)). If the specified resource does not exist and the FHIR store has enable_update_create set, creates the resource with the client-specified ID. It is strongly advised not to include or encode any sensitive data such as patient identifiers in client-specified resource IDs. Those IDs are part of the FHIR resource path recorded in Cloud Audit Logs and Pub/Sub notifications. Those IDs can also be contained in reference fields within other resources. The request body must contain a JSON-encoded FHIR resource, and the request headers must contain `Content-Type: application/fhir+json`. The resource must contain an `id` element having an identical value to the ID in the REST path of the request. On success, the response body contains a JSON-encoded representation of the updated resource, including the server-assigned version ID. Errors generated by the FHIR store contain a JSON-encoded `OperationOutcome` resource describing the reason for the error. If the request cannot be mapped to a valid API method on a FHIR store, a generic GCP error might be returned instead. For samples that show how to call `update`, see [Updating a FHIR resource](https://cloud.google.com/healthcare/docs/how-tos/fhir-resources#updating_a_fhir_resource). */
						class update(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-healthcare""", """https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def withHttpBody(body: Schema.HttpBody) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.HttpBody])
						}
						object update {
							def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, fhirStoresId :PlayApi, fhirId :PlayApi, fhirId1 :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/fhirStores/${fhirStoresId}/fhir/${fhirId}/${fhirId1}").addQueryStringParameters("name" -> name.toString))
						}
						/** Searches for resources in the given FHIR store according to criteria specified as query parameters. Implements the FHIR standard search interaction ([DSTU2](http://hl7.org/implement/standards/fhir/DSTU2/http.html#search), [STU3](http://hl7.org/implement/standards/fhir/STU3/http.html#search), [R4](http://hl7.org/implement/standards/fhir/R4/http.html#search)) using the search semantics described in the FHIR Search specification ([DSTU2](http://hl7.org/implement/standards/fhir/DSTU2/search.html), [STU3](http://hl7.org/implement/standards/fhir/STU3/search.html), [R4](http://hl7.org/implement/standards/fhir/R4/search.html)). Supports four methods of search defined by the specification: &#42; `GET [base]?[parameters]` to search across all resources. &#42; `GET [base]/[type]?[parameters]` to search resources of a specified type. &#42; `POST [base]/_search?[parameters]` as an alternate form having the same semantics as the `GET` method across all resources. &#42; `POST [base]/[type]/_search?[parameters]` as an alternate form having the same semantics as the `GET` method for the specified type. The `GET` and `POST` methods do not support compartment searches. The `POST` method does not support `application/x-www-form-urlencoded` search parameters. On success, the response body contains a JSON-encoded representation of a `Bundle` resource of type `searchset`, containing the results of the search. Errors generated by the FHIR store contain a JSON-encoded `OperationOutcome` resource describing the reason for the error. If the request cannot be mapped to a valid API method on a FHIR store, a generic GCP error might be returned instead. The server's capability statement, retrieved through capabilities, indicates what search parameters are supported on each FHIR resource. A list of all search parameters defined by the specification can be found in the FHIR Search Parameter Registry ([STU3](http://hl7.org/implement/standards/fhir/STU3/searchparameter-registry.html), [R4](http://hl7.org/implement/standards/fhir/R4/searchparameter-registry.html)). FHIR search parameters for DSTU2 can be found on each resource's definition page. Supported search modifiers: `:missing`, `:exact`, `:contains`, `:text`, `:in`, `:not-in`, `:above`, `:below`, `:[type]`, `:not`, and `recurse` (DSTU2 and STU3) or `:iterate` (R4). Supported search result parameters: `_sort`, `_count`, `_include`, `_revinclude`, `_summary=text`, `_summary=data`, and `_elements`. The maximum number of search results returned defaults to 100, which can be overridden by the `_count` parameter up to a maximum limit of 1000. The server might return fewer resources than requested to prevent excessively large responses. If there are additional results, the returned `Bundle` contains a link of `relation` "next", which has a `_page_token` parameter for an opaque pagination token that can be used to retrieve the next page. Resources with a total size larger than 5MB or a field count larger than 50,000 might not be fully searchable as the server might trim its generated search index in those cases. Note: FHIR resources are indexed asynchronously, so there might be a slight delay between the time a resource is created or changed, and the time when the change reflects in search results. The only exception is resource identifier data, which is indexed synchronously as a special index. As a result, searching using resource identifier is not subject to indexing delay. To use the special synchronous index, the search term for identifier should be in the pattern `identifier=[system]|[value]` or `identifier=[value]`, and any of the following search result parameters can be used: &#42; `_count` &#42; `_include` &#42; `_revinclude` &#42; `_summary` &#42; `_elements` If your query contains any other search parameters, the standard asynchronous index will be used instead. Note that searching against the special index is optimized for resolving a small number of matches. The search isn't optimized if your identifier search criteria matches a large number (i.e. more than 2,000) of resources. For a search query that will match a large number of resources, you can avoiding using the special synchronous index by including an additional `_sort` parameter in your query. Use `_sort=-_lastUpdated` if you want to keep the default sorting order. Note: The special synchronous identifier index are currently disabled for DocumentReference and DocumentManifest searches. For samples and detailed information, see [Searching for FHIR resources](https://cloud.google.com/healthcare/docs/how-tos/fhir-search) and [Advanced FHIR search features](https://cloud.google.com/healthcare/docs/how-tos/fhir-advanced-search). */
						class search_type(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-healthcare""", """https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def withSearchResourcesRequest(body: Schema.SearchResourcesRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.HttpBody])
						}
						object search_type {
							def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, fhirStoresId :PlayApi, parent: String, resourceType: String)(using signer: RequestSigner, ec: ExecutionContext): search_type = new search_type(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/fhirStores/${fhirStoresId}/fhir/${resourceType}/_search").addQueryStringParameters("parent" -> parent.toString))
						}
					}
				}
				object hl7V2Stores {
					/** Exports the messages to a destination. To filter messages to be exported, define a filter using the start and end time, relative to the message generation time (MSH.7). This API returns an Operation that can be used to track the status of the job by calling GetOperation. Immediate fatal errors appear in the error field. Otherwise, when the operation finishes, a detailed response of type ExportMessagesResponse is returned in the response field. The metadata field type for this operation is OperationMetadata. */
					class `export`(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-healthcare""", """https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withExportMessagesRequest(body: Schema.ExportMessagesRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
					}
					object `export` {
						def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, hl7V2StoresId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): `export` = new `export`(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/hl7V2Stores/${hl7V2StoresId}:export").addQueryStringParameters("name" -> name.toString))
					}
					/** Rolls back messages from the HL7v2 store to the specified time. This method returns an Operation that can be used to track the status of the rollback by calling GetOperation. Immediate fatal errors appear in the error field, errors are also logged to Cloud Logging (see [Viewing error logs in Cloud Logging](https://cloud.google.com/healthcare/docs/how-tos/logging)). Otherwise, when the operation finishes, a detailed response of type RollbackHl7V2MessagesResponse is returned in the response field. The metadata field type for this operation is OperationMetadata. */
					class rollback(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-healthcare""", """https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withRollbackHl7V2MessagesRequest(body: Schema.RollbackHl7V2MessagesRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
					}
					object rollback {
						def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, hl7V2StoresId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): rollback = new rollback(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/hl7V2Stores/${hl7V2StoresId}:rollback").addQueryStringParameters("name" -> name.toString))
					}
					/** Import messages to the HL7v2 store by loading data from the specified sources. This method is optimized to load large quantities of data using import semantics that ignore some HL7v2 store configuration options and are not suitable for all use cases. It is primarily intended to load data into an empty HL7v2 store that is not being used by other clients. An existing message will be overwritten if a duplicate message is imported. A duplicate message is a message with the same raw bytes as a message that already exists in this HL7v2 store. When a message is overwritten, its labels will also be overwritten. The import operation is idempotent unless the input data contains multiple valid messages with the same raw bytes but different labels. In that case, after the import completes, the store contains exactly one message with those raw bytes but there is no ordering guarantee on which version of the labels it has. The operation result counters do not count duplicated raw bytes as an error and count one success for each message in the input, which might result in a success count larger than the number of messages in the HL7v2 store. If some messages fail to import, for example due to parsing errors, successfully imported messages are not rolled back. This method returns an Operation that can be used to track the status of the import by calling GetOperation. Immediate fatal errors appear in the error field, errors are also logged to Cloud Logging (see [Viewing error logs in Cloud Logging](https://cloud.google.com/healthcare/docs/how-tos/logging)). Otherwise, when the operation finishes, a response of type ImportMessagesResponse is returned in the response field. The metadata field type for this operation is OperationMetadata. */
					class `import`(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-healthcare""", """https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withImportMessagesRequest(body: Schema.ImportMessagesRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
					}
					object `import` {
						def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, hl7V2StoresId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): `import` = new `import`(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/hl7V2Stores/${hl7V2StoresId}:import").addQueryStringParameters("name" -> name.toString))
					}
					/** Gets the access control policy for a resource. Returns an empty policy if the resource exists and does not have a policy set. */
					class getIamPolicy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Policy]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-healthcare""", """https://www.googleapis.com/auth/cloud-platform""")
						/** Optional. The maximum policy version that will be used to format the policy. Valid values are 0, 1, and 3. Requests specifying an invalid value will be rejected. Requests for policies with any conditional role bindings must specify version 3. Policies with no conditional role bindings may specify any valid value or leave the field unset. The policy in the response might use the policy version that you specified, or it might use a lower policy version. For example, if you specify version 3, but the policy has no conditional role bindings, the response uses version 1. To learn which resources support conditions in their IAM policies, see the [IAM documentation](https://cloud.google.com/iam/help/conditions/resource-policies).<br>Format: int32 */
						def withOptionsRequestedPolicyVersion(optionsRequestedPolicyVersion: Int) = new getIamPolicy(req.addQueryStringParameters("options.requestedPolicyVersion" -> optionsRequestedPolicyVersion.toString))
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Policy])
					}
					object getIamPolicy {
						def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, hl7V2StoresId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): getIamPolicy = new getIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/hl7V2Stores/${hl7V2StoresId}:getIamPolicy").addQueryStringParameters("resource" -> resource.toString))
						given Conversion[getIamPolicy, Future[Schema.Policy]] = (fun: getIamPolicy) => fun.apply()
					}
					/** Deletes the specified HL7v2 store and removes all messages that it contains. */
					class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-healthcare""", """https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
					}
					object delete {
						def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, hl7V2StoresId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/hl7V2Stores/${hl7V2StoresId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
					}
					/** Gets metrics associated with the HL7v2 store. */
					class getHL7v2StoreMetrics(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Hl7V2StoreMetrics]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-healthcare""", """https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Hl7V2StoreMetrics])
					}
					object getHL7v2StoreMetrics {
						def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, hl7V2StoresId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): getHL7v2StoreMetrics = new getHL7v2StoreMetrics(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/hl7V2Stores/${hl7V2StoresId}:getHL7v2StoreMetrics").addQueryStringParameters("name" -> name.toString))
						given Conversion[getHL7v2StoreMetrics, Future[Schema.Hl7V2StoreMetrics]] = (fun: getHL7v2StoreMetrics) => fun.apply()
					}
					/** Gets the specified HL7v2 store. */
					class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Hl7V2Store]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-healthcare""", """https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Hl7V2Store])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, hl7V2StoresId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/hl7V2Stores/${hl7V2StoresId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.Hl7V2Store]] = (fun: get) => fun.apply()
					}
					/** Updates the HL7v2 store. */
					class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-healthcare""", """https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withHl7V2Store(body: Schema.Hl7V2Store) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Hl7V2Store])
					}
					object patch {
						def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, hl7V2StoresId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/hl7V2Stores/${hl7V2StoresId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
					}
					/** Lists the HL7v2 stores in the given dataset. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListHl7V2StoresResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-healthcare""", """https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListHl7V2StoresResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, parent: String, pageSize: Int, pageToken: String, filter: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/hl7V2Stores").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "filter" -> filter.toString))
						given Conversion[list, Future[Schema.ListHl7V2StoresResponse]] = (fun: list) => fun.apply()
					}
					/** Returns permissions that a caller has on the specified resource. If the resource does not exist, this will return an empty set of permissions, not a `NOT_FOUND` error. Note: This operation is designed to be used for building permission-aware UIs and command-line tools, not for authorization checking. This operation may "fail open" without warning. */
					class testIamPermissions(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-healthcare""", """https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withTestIamPermissionsRequest(body: Schema.TestIamPermissionsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.TestIamPermissionsResponse])
					}
					object testIamPermissions {
						def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, hl7V2StoresId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): testIamPermissions = new testIamPermissions(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/hl7V2Stores/${hl7V2StoresId}:testIamPermissions").addQueryStringParameters("resource" -> resource.toString))
					}
					/** Creates a new HL7v2 store within the parent dataset. */
					class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-healthcare""", """https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withHl7V2Store(body: Schema.Hl7V2Store) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Hl7V2Store])
					}
					object create {
						def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, parent: String, hl7V2StoreId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/hl7V2Stores").addQueryStringParameters("parent" -> parent.toString, "hl7V2StoreId" -> hl7V2StoreId.toString))
					}
					/** Sets the access control policy on the specified resource. Replaces any existing policy. Can return `NOT_FOUND`, `INVALID_ARGUMENT`, and `PERMISSION_DENIED` errors. */
					class setIamPolicy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-healthcare""", """https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withSetIamPolicyRequest(body: Schema.SetIamPolicyRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
					}
					object setIamPolicy {
						def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, hl7V2StoresId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): setIamPolicy = new setIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/hl7V2Stores/${hl7V2StoresId}:setIamPolicy").addQueryStringParameters("resource" -> resource.toString))
					}
					object messages {
						/** Parses and stores an HL7v2 message. This method triggers an asynchronous notification to any Pub/Sub topic configured in Hl7V2Store.Hl7V2NotificationConfig, if the filtering matches the message. If an MLLP adapter is configured to listen to a Pub/Sub topic, the adapter transmits the message when a notification is received. */
						class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-healthcare""", """https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def withCreateMessageRequest(body: Schema.CreateMessageRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Message])
						}
						object create {
							def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, hl7V2StoresId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/hl7V2Stores/${hl7V2StoresId}/messages").addQueryStringParameters("parent" -> parent.toString))
						}
						/** Parses and stores an HL7v2 message. This method triggers an asynchronous notification to any Pub/Sub topic configured in Hl7V2Store.Hl7V2NotificationConfig, if the filtering matches the message. If an MLLP adapter is configured to listen to a Pub/Sub topic, the adapter transmits the message when a notification is received. If the method is successful, it generates a response containing an HL7v2 acknowledgment (`ACK`) message. If the method encounters an error, it returns a negative acknowledgment (`NACK`) message. This behavior is suitable for replying to HL7v2 interface systems that expect these acknowledgments. */
						class ingest(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-healthcare""", """https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def withIngestMessageRequest(body: Schema.IngestMessageRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.IngestMessageResponse])
						}
						object ingest {
							def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, hl7V2StoresId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): ingest = new ingest(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/hl7V2Stores/${hl7V2StoresId}/messages:ingest").addQueryStringParameters("parent" -> parent.toString))
						}
						/** Deletes an HL7v2 message. */
						class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-healthcare""", """https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
						}
						object delete {
							def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, hl7V2StoresId :PlayApi, messagesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/hl7V2Stores/${hl7V2StoresId}/messages/${messagesId}").addQueryStringParameters("name" -> name.toString))
							given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
						}
						/** Gets an HL7v2 message. */
						class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Message]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-healthcare""", """https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Message])
						}
						object get {
							def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, hl7V2StoresId :PlayApi, messagesId :PlayApi, name: String, view: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/hl7V2Stores/${hl7V2StoresId}/messages/${messagesId}").addQueryStringParameters("name" -> name.toString, "view" -> view.toString))
							given Conversion[get, Future[Schema.Message]] = (fun: get) => fun.apply()
						}
						/** Update the message. The contents of the message in Message.data and data extracted from the contents such as Message.create_time cannot be altered. Only the Message.labels field is allowed to be updated. The labels in the request are merged with the existing set of labels. Existing labels with the same keys are updated. */
						class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-healthcare""", """https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def withMessage(body: Schema.Message) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Message])
						}
						object patch {
							def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, hl7V2StoresId :PlayApi, messagesId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/hl7V2Stores/${hl7V2StoresId}/messages/${messagesId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
						}
						/** Lists all the messages in the given HL7v2 store with support for filtering. Note: HL7v2 messages are indexed asynchronously, so there might be a slight delay between the time a message is created and when it can be found through a filter. */
						class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListMessagesResponse]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-healthcare""", """https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListMessagesResponse])
						}
						object list {
							def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, hl7V2StoresId :PlayApi, parent: String, pageSize: Int, pageToken: String, filter: String, orderBy: String, view: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/hl7V2Stores/${hl7V2StoresId}/messages").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "filter" -> filter.toString, "orderBy" -> orderBy.toString, "view" -> view.toString))
							given Conversion[list, Future[Schema.ListMessagesResponse]] = (fun: list) => fun.apply()
						}
					}
				}
				object operations {
					/** Lists operations that match the specified filter in the request. If the server doesn't support this method, it returns `UNIMPLEMENTED`. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListOperationsResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-healthcare""", """https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListOperationsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, name: String, filter: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/operations").addQueryStringParameters("name" -> name.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
						given Conversion[list, Future[Schema.ListOperationsResponse]] = (fun: list) => fun.apply()
					}
					/** Gets the latest state of a long-running operation. Clients can use this method to poll the operation result at intervals as recommended by the API service. */
					class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-healthcare""", """https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Operation])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, operationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.Operation]] = (fun: get) => fun.apply()
					}
					/** Starts asynchronous cancellation on a long-running operation. The server makes a best effort to cancel the operation, but success is not guaranteed. If the server doesn't support this method, it returns `google.rpc.Code.UNIMPLEMENTED`. Clients can use Operations.GetOperation or other methods to check whether the cancellation succeeded or whether the operation completed despite cancellation. On successful cancellation, the operation is not deleted; instead, it becomes an operation with an Operation.error value with a google.rpc.Status.code of 1, corresponding to `Code.CANCELLED`. */
					class cancel(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-healthcare""", """https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withCancelOperationRequest(body: Schema.CancelOperationRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Empty])
					}
					object cancel {
						def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, operationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): cancel = new cancel(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/operations/${operationsId}:cancel").addQueryStringParameters("name" -> name.toString))
					}
				}
				object dicomStores {
					/** Returns permissions that a caller has on the specified resource. If the resource does not exist, this will return an empty set of permissions, not a `NOT_FOUND` error. Note: This operation is designed to be used for building permission-aware UIs and command-line tools, not for authorization checking. This operation may "fail open" without warning. */
					class testIamPermissions(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-healthcare""", """https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withTestIamPermissionsRequest(body: Schema.TestIamPermissionsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.TestIamPermissionsResponse])
					}
					object testIamPermissions {
						def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, dicomStoresId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): testIamPermissions = new testIamPermissions(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/dicomStores/${dicomStoresId}:testIamPermissions").addQueryStringParameters("resource" -> resource.toString))
					}
					/** De-identifies data from the source store and writes it to the destination store. The metadata field type is OperationMetadata. If the request is successful, the response field type is DeidentifyDicomStoreSummary. If errors occur, error is set. The LRO result may still be successful if de-identification fails for some DICOM instances. The output DICOM store will not contain these failed resources. Failed resource totals are tracked in Operation.metadata. Error details are also logged to Cloud Logging (see [Viewing error logs in Cloud Logging](https://cloud.google.com/healthcare/docs/how-tos/logging)). */
					class deidentify(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-healthcare""", """https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withDeidentifyDicomStoreRequest(body: Schema.DeidentifyDicomStoreRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
					}
					object deidentify {
						def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, dicomStoresId :PlayApi, sourceStore: String)(using signer: RequestSigner, ec: ExecutionContext): deidentify = new deidentify(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/dicomStores/${dicomStoresId}:deidentify").addQueryStringParameters("sourceStore" -> sourceStore.toString))
					}
					/** Exports data to the specified destination by copying it from the DICOM store. Errors are also logged to Cloud Logging. For more information, see [Viewing error logs in Cloud Logging](https://cloud.google.com/healthcare/docs/how-tos/logging). The metadata field type is OperationMetadata. */
					class `export`(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-healthcare""", """https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withExportDicomDataRequest(body: Schema.ExportDicomDataRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
					}
					object `export` {
						def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, dicomStoresId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): `export` = new `export`(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/dicomStores/${dicomStoresId}:export").addQueryStringParameters("name" -> name.toString))
					}
					/** Imports data into the DICOM store by copying it from the specified source. Errors are logged to Cloud Logging. For more information, see [Viewing error logs in Cloud Logging](https://cloud.google.com/healthcare/docs/how-tos/logging). The metadata field type is OperationMetadata. */
					class `import`(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-healthcare""", """https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withImportDicomDataRequest(body: Schema.ImportDicomDataRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
					}
					object `import` {
						def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, dicomStoresId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): `import` = new `import`(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/dicomStores/${dicomStoresId}:import").addQueryStringParameters("name" -> name.toString))
					}
					/** Gets the access control policy for a resource. Returns an empty policy if the resource exists and does not have a policy set. */
					class getIamPolicy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Policy]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-healthcare""", """https://www.googleapis.com/auth/cloud-platform""")
						/** Optional. The maximum policy version that will be used to format the policy. Valid values are 0, 1, and 3. Requests specifying an invalid value will be rejected. Requests for policies with any conditional role bindings must specify version 3. Policies with no conditional role bindings may specify any valid value or leave the field unset. The policy in the response might use the policy version that you specified, or it might use a lower policy version. For example, if you specify version 3, but the policy has no conditional role bindings, the response uses version 1. To learn which resources support conditions in their IAM policies, see the [IAM documentation](https://cloud.google.com/iam/help/conditions/resource-policies).<br>Format: int32 */
						def withOptionsRequestedPolicyVersion(optionsRequestedPolicyVersion: Int) = new getIamPolicy(req.addQueryStringParameters("options.requestedPolicyVersion" -> optionsRequestedPolicyVersion.toString))
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Policy])
					}
					object getIamPolicy {
						def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, dicomStoresId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): getIamPolicy = new getIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/dicomStores/${dicomStoresId}:getIamPolicy").addQueryStringParameters("resource" -> resource.toString))
						given Conversion[getIamPolicy, Future[Schema.Policy]] = (fun: getIamPolicy) => fun.apply()
					}
					/** Deletes the specified DICOM store and removes all images that are contained within it. */
					class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-healthcare""", """https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
					}
					object delete {
						def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, dicomStoresId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/dicomStores/${dicomStoresId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
					}
					/** SearchForStudies returns a list of matching studies. See [Search Transaction] (http://dicom.nema.org/medical/dicom/current/output/html/part18.html#sect_10.6). For details on the implementation of SearchForStudies, see [Search transaction](https://cloud.google.com/healthcare/docs/dicom#search_transaction) in the Cloud Healthcare API conformance statement. For samples that show how to call SearchForStudies, see [Search for DICOM data](https://cloud.google.com/healthcare/docs/how-tos/dicomweb#search-dicom). */
					class searchForStudies(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.HttpBody]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-healthcare""", """https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.HttpBody])
					}
					object searchForStudies {
						def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, dicomStoresId :PlayApi, parent: String, dicomWebPath: String)(using signer: RequestSigner, ec: ExecutionContext): searchForStudies = new searchForStudies(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/dicomStores/${dicomStoresId}/dicomWeb/studies").addQueryStringParameters("parent" -> parent.toString, "dicomWebPath" -> dicomWebPath.toString))
						given Conversion[searchForStudies, Future[Schema.HttpBody]] = (fun: searchForStudies) => fun.apply()
					}
					/** Gets the specified DICOM store. */
					class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.DicomStore]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-healthcare""", """https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.DicomStore])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, dicomStoresId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/dicomStores/${dicomStoresId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.DicomStore]] = (fun: get) => fun.apply()
					}
					/** Updates the specified DICOM store. */
					class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-healthcare""", """https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withDicomStore(body: Schema.DicomStore) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.DicomStore])
					}
					object patch {
						def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, dicomStoresId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/dicomStores/${dicomStoresId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
					}
					/** Lists the DICOM stores in the given dataset. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListDicomStoresResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-healthcare""", """https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListDicomStoresResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, parent: String, pageSize: Int, pageToken: String, filter: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/dicomStores").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "filter" -> filter.toString))
						given Conversion[list, Future[Schema.ListDicomStoresResponse]] = (fun: list) => fun.apply()
					}
					/** SetBlobStorageSettings sets the blob storage settings of the specified resources. */
					class setBlobStorageSettings(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-healthcare""", """https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withSetBlobStorageSettingsRequest(body: Schema.SetBlobStorageSettingsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
					}
					object setBlobStorageSettings {
						def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, dicomStoresId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): setBlobStorageSettings = new setBlobStorageSettings(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/dicomStores/${dicomStoresId}:setBlobStorageSettings").addQueryStringParameters("resource" -> resource.toString))
					}
					/** Creates a new DICOM store within the parent dataset. */
					class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-healthcare""", """https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withDicomStore(body: Schema.DicomStore) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.DicomStore])
					}
					object create {
						def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, parent: String, dicomStoreId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/dicomStores").addQueryStringParameters("parent" -> parent.toString, "dicomStoreId" -> dicomStoreId.toString))
					}
					/** Sets the access control policy on the specified resource. Replaces any existing policy. Can return `NOT_FOUND`, `INVALID_ARGUMENT`, and `PERMISSION_DENIED` errors. */
					class setIamPolicy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-healthcare""", """https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withSetIamPolicyRequest(body: Schema.SetIamPolicyRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
					}
					object setIamPolicy {
						def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, dicomStoresId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): setIamPolicy = new setIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/dicomStores/${dicomStoresId}:setIamPolicy").addQueryStringParameters("resource" -> resource.toString))
					}
					/** SearchForInstances returns a list of matching instances. See [Search Transaction] (http://dicom.nema.org/medical/dicom/current/output/html/part18.html#sect_10.6). For details on the implementation of SearchForInstances, see [Search transaction](https://cloud.google.com/healthcare/docs/dicom#search_transaction) in the Cloud Healthcare API conformance statement. For samples that show how to call SearchForInstances, see [Search for DICOM data](https://cloud.google.com/healthcare/docs/how-tos/dicomweb#search-dicom). */
					class searchForInstances(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.HttpBody]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-healthcare""", """https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.HttpBody])
					}
					object searchForInstances {
						def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, dicomStoresId :PlayApi, parent: String, dicomWebPath: String)(using signer: RequestSigner, ec: ExecutionContext): searchForInstances = new searchForInstances(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/dicomStores/${dicomStoresId}/dicomWeb/instances").addQueryStringParameters("parent" -> parent.toString, "dicomWebPath" -> dicomWebPath.toString))
						given Conversion[searchForInstances, Future[Schema.HttpBody]] = (fun: searchForInstances) => fun.apply()
					}
					/** SearchForSeries returns a list of matching series. See [Search Transaction] (http://dicom.nema.org/medical/dicom/current/output/html/part18.html#sect_10.6). For details on the implementation of SearchForSeries, see [Search transaction](https://cloud.google.com/healthcare/docs/dicom#search_transaction) in the Cloud Healthcare API conformance statement. For samples that show how to call SearchForSeries, see [Search for DICOM data](https://cloud.google.com/healthcare/docs/how-tos/dicomweb#search-dicom). */
					class searchForSeries(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.HttpBody]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-healthcare""", """https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.HttpBody])
					}
					object searchForSeries {
						def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, dicomStoresId :PlayApi, parent: String, dicomWebPath: String)(using signer: RequestSigner, ec: ExecutionContext): searchForSeries = new searchForSeries(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/dicomStores/${dicomStoresId}/dicomWeb/series").addQueryStringParameters("parent" -> parent.toString, "dicomWebPath" -> dicomWebPath.toString))
						given Conversion[searchForSeries, Future[Schema.HttpBody]] = (fun: searchForSeries) => fun.apply()
					}
					/** Gets metrics associated with the DICOM store. */
					class getDICOMStoreMetrics(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.DicomStoreMetrics]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-healthcare""", """https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.DicomStoreMetrics])
					}
					object getDICOMStoreMetrics {
						def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, dicomStoresId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): getDICOMStoreMetrics = new getDICOMStoreMetrics(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/dicomStores/${dicomStoresId}:getDICOMStoreMetrics").addQueryStringParameters("name" -> name.toString))
						given Conversion[getDICOMStoreMetrics, Future[Schema.DicomStoreMetrics]] = (fun: getDICOMStoreMetrics) => fun.apply()
					}
					/** StoreInstances stores DICOM instances associated with study instance unique identifiers (SUID). See [Store Transaction] (http://dicom.nema.org/medical/dicom/current/output/html/part18.html#sect_10.5). For details on the implementation of StoreInstances, see [Store transaction](https://cloud.google.com/healthcare/docs/dicom#store_transaction) in the Cloud Healthcare API conformance statement. For samples that show how to call StoreInstances, see [Store DICOM data](https://cloud.google.com/healthcare/docs/how-tos/dicomweb#store-dicom). */
					class storeInstances(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-healthcare""", """https://www.googleapis.com/auth/cloud-platform""")
						/** Required. The path of the StoreInstances DICOMweb request. For example, `studies/[{study_uid}]`. Note that the `study_uid` is optional. */
						def withDicomWebPath(dicomWebPath: String) = new storeInstances(req.addQueryStringParameters("dicomWebPath" -> dicomWebPath.toString))
						/** Perform the request */
						def withHttpBody(body: Schema.HttpBody) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.HttpBody])
					}
					object storeInstances {
						def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, dicomStoresId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): storeInstances = new storeInstances(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/dicomStores/${dicomStoresId}/dicomWeb/studies").addQueryStringParameters("parent" -> parent.toString))
					}
					object dicomWeb {
						object studies {
							/** SetBlobStorageSettings sets the blob storage settings of the specified resources. */
							class setBlobStorageSettings(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
								val scopes = Seq("""https://www.googleapis.com/auth/cloud-healthcare""", """https://www.googleapis.com/auth/cloud-platform""")
								/** Perform the request */
								def withSetBlobStorageSettingsRequest(body: Schema.SetBlobStorageSettingsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
							}
							object setBlobStorageSettings {
								def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, dicomStoresId :PlayApi, studiesId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): setBlobStorageSettings = new setBlobStorageSettings(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/dicomStores/${dicomStoresId}/dicomWeb/studies/${studiesId}:setBlobStorageSettings").addQueryStringParameters("resource" -> resource.toString))
							}
							/** GetStudyMetrics returns metrics for a study. */
							class getStudyMetrics(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.StudyMetrics]) {
								val scopes = Seq("""https://www.googleapis.com/auth/cloud-healthcare""", """https://www.googleapis.com/auth/cloud-platform""")
								/** Perform the request */
								def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.StudyMetrics])
							}
							object getStudyMetrics {
								def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, dicomStoresId :PlayApi, studiesId :PlayApi, study: String)(using signer: RequestSigner, ec: ExecutionContext): getStudyMetrics = new getStudyMetrics(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/dicomStores/${dicomStoresId}/dicomWeb/studies/${studiesId}:getStudyMetrics").addQueryStringParameters("study" -> study.toString))
								given Conversion[getStudyMetrics, Future[Schema.StudyMetrics]] = (fun: getStudyMetrics) => fun.apply()
							}
							object series {
								/** GetSeriesMetrics returns metrics for a series. */
								class getSeriesMetrics(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.SeriesMetrics]) {
									val scopes = Seq("""https://www.googleapis.com/auth/cloud-healthcare""", """https://www.googleapis.com/auth/cloud-platform""")
									/** Perform the request */
									def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.SeriesMetrics])
								}
								object getSeriesMetrics {
									def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, dicomStoresId :PlayApi, studiesId :PlayApi, seriesId :PlayApi, series: String)(using signer: RequestSigner, ec: ExecutionContext): getSeriesMetrics = new getSeriesMetrics(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/dicomStores/${dicomStoresId}/dicomWeb/studies/${studiesId}/series/${seriesId}:getSeriesMetrics").addQueryStringParameters("series" -> series.toString))
									given Conversion[getSeriesMetrics, Future[Schema.SeriesMetrics]] = (fun: getSeriesMetrics) => fun.apply()
								}
								object instances {
									/** GetStorageInfo returns the storage info of the specified resource. */
									class getStorageInfo(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.StorageInfo]) {
										val scopes = Seq("""https://www.googleapis.com/auth/cloud-healthcare""", """https://www.googleapis.com/auth/cloud-platform""")
										/** Perform the request */
										def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.StorageInfo])
									}
									object getStorageInfo {
										def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, dicomStoresId :PlayApi, studiesId :PlayApi, seriesId :PlayApi, instancesId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): getStorageInfo = new getStorageInfo(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/dicomStores/${dicomStoresId}/dicomWeb/studies/${studiesId}/series/${seriesId}/instances/${instancesId}:getStorageInfo").addQueryStringParameters("resource" -> resource.toString))
										given Conversion[getStorageInfo, Future[Schema.StorageInfo]] = (fun: getStorageInfo) => fun.apply()
									}
								}
							}
						}
					}
					object studies {
						/** RetrieveStudy returns all instances within the given study. See [RetrieveTransaction] (http://dicom.nema.org/medical/dicom/current/output/html/part18.html#sect_10.4). For details on the implementation of RetrieveStudy, see [DICOM study/series/instances](https://cloud.google.com/healthcare/docs/dicom#dicom_studyseriesinstances) in the Cloud Healthcare API conformance statement. For samples that show how to call RetrieveStudy, see [Retrieve DICOM data](https://cloud.google.com/healthcare/docs/how-tos/dicomweb#retrieve-dicom). */
						class retrieveStudy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.HttpBody]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-healthcare""", """https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.HttpBody])
						}
						object retrieveStudy {
							def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, dicomStoresId :PlayApi, studiesId :PlayApi, parent: String, dicomWebPath: String)(using signer: RequestSigner, ec: ExecutionContext): retrieveStudy = new retrieveStudy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/dicomStores/${dicomStoresId}/dicomWeb/studies/${studiesId}").addQueryStringParameters("parent" -> parent.toString, "dicomWebPath" -> dicomWebPath.toString))
							given Conversion[retrieveStudy, Future[Schema.HttpBody]] = (fun: retrieveStudy) => fun.apply()
						}
						/** StoreInstances stores DICOM instances associated with study instance unique identifiers (SUID). See [Store Transaction] (http://dicom.nema.org/medical/dicom/current/output/html/part18.html#sect_10.5). For details on the implementation of StoreInstances, see [Store transaction](https://cloud.google.com/healthcare/docs/dicom#store_transaction) in the Cloud Healthcare API conformance statement. For samples that show how to call StoreInstances, see [Store DICOM data](https://cloud.google.com/healthcare/docs/how-tos/dicomweb#store-dicom). */
						class storeInstances(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-healthcare""", """https://www.googleapis.com/auth/cloud-platform""")
							/** Required. The path of the StoreInstances DICOMweb request. For example, `studies/[{study_uid}]`. Note that the `study_uid` is optional. */
							def withDicomWebPath(dicomWebPath: String) = new storeInstances(req.addQueryStringParameters("dicomWebPath" -> dicomWebPath.toString))
							/** Perform the request */
							def withHttpBody(body: Schema.HttpBody) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.HttpBody])
						}
						object storeInstances {
							def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, dicomStoresId :PlayApi, studiesId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): storeInstances = new storeInstances(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/dicomStores/${dicomStoresId}/dicomWeb/studies/${studiesId}").addQueryStringParameters("parent" -> parent.toString))
						}
						/** DeleteStudy deletes all instances within the given study. Delete requests are equivalent to the GET requests specified in the Retrieve transaction. The method returns an Operation which will be marked successful when the deletion is complete. Warning: Instances cannot be inserted into a study that is being deleted by an operation until the operation completes. For samples that show how to call DeleteStudy, see [Delete a study, series, or instance](https://cloud.google.com/healthcare/docs/how-tos/dicomweb#delete-dicom). */
						class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-healthcare""", """https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Operation])
						}
						object delete {
							def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, dicomStoresId :PlayApi, studiesId :PlayApi, parent: String, dicomWebPath: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/dicomStores/${dicomStoresId}/dicomWeb/studies/${studiesId}").addQueryStringParameters("parent" -> parent.toString, "dicomWebPath" -> dicomWebPath.toString))
							given Conversion[delete, Future[Schema.Operation]] = (fun: delete) => fun.apply()
						}
						/** RetrieveStudyMetadata returns instance associated with the given study presented as metadata with the bulk data removed. See [RetrieveTransaction] (http://dicom.nema.org/medical/dicom/current/output/html/part18.html#sect_10.4). For details on the implementation of RetrieveStudyMetadata, see [Metadata resources](https://cloud.google.com/healthcare/docs/dicom#metadata_resources) in the Cloud Healthcare API conformance statement. For samples that show how to call RetrieveStudyMetadata, see [Retrieve metadata](https://cloud.google.com/healthcare/docs/how-tos/dicomweb#retrieve-metadata). */
						class retrieveMetadata(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.HttpBody]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-healthcare""", """https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.HttpBody])
						}
						object retrieveMetadata {
							def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, dicomStoresId :PlayApi, studiesId :PlayApi, parent: String, dicomWebPath: String)(using signer: RequestSigner, ec: ExecutionContext): retrieveMetadata = new retrieveMetadata(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/dicomStores/${dicomStoresId}/dicomWeb/studies/${studiesId}/metadata").addQueryStringParameters("parent" -> parent.toString, "dicomWebPath" -> dicomWebPath.toString))
							given Conversion[retrieveMetadata, Future[Schema.HttpBody]] = (fun: retrieveMetadata) => fun.apply()
						}
						/** SearchForInstances returns a list of matching instances. See [Search Transaction] (http://dicom.nema.org/medical/dicom/current/output/html/part18.html#sect_10.6). For details on the implementation of SearchForInstances, see [Search transaction](https://cloud.google.com/healthcare/docs/dicom#search_transaction) in the Cloud Healthcare API conformance statement. For samples that show how to call SearchForInstances, see [Search for DICOM data](https://cloud.google.com/healthcare/docs/how-tos/dicomweb#search-dicom). */
						class searchForInstances(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.HttpBody]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-healthcare""", """https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.HttpBody])
						}
						object searchForInstances {
							def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, dicomStoresId :PlayApi, studiesId :PlayApi, parent: String, dicomWebPath: String)(using signer: RequestSigner, ec: ExecutionContext): searchForInstances = new searchForInstances(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/dicomStores/${dicomStoresId}/dicomWeb/studies/${studiesId}/instances").addQueryStringParameters("parent" -> parent.toString, "dicomWebPath" -> dicomWebPath.toString))
							given Conversion[searchForInstances, Future[Schema.HttpBody]] = (fun: searchForInstances) => fun.apply()
						}
						/** SearchForSeries returns a list of matching series. See [Search Transaction] (http://dicom.nema.org/medical/dicom/current/output/html/part18.html#sect_10.6). For details on the implementation of SearchForSeries, see [Search transaction](https://cloud.google.com/healthcare/docs/dicom#search_transaction) in the Cloud Healthcare API conformance statement. For samples that show how to call SearchForSeries, see [Search for DICOM data](https://cloud.google.com/healthcare/docs/how-tos/dicomweb#search-dicom). */
						class searchForSeries(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.HttpBody]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-healthcare""", """https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.HttpBody])
						}
						object searchForSeries {
							def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, dicomStoresId :PlayApi, studiesId :PlayApi, parent: String, dicomWebPath: String)(using signer: RequestSigner, ec: ExecutionContext): searchForSeries = new searchForSeries(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/dicomStores/${dicomStoresId}/dicomWeb/studies/${studiesId}/series").addQueryStringParameters("parent" -> parent.toString, "dicomWebPath" -> dicomWebPath.toString))
							given Conversion[searchForSeries, Future[Schema.HttpBody]] = (fun: searchForSeries) => fun.apply()
						}
						object series {
							/** SearchForInstances returns a list of matching instances. See [Search Transaction] (http://dicom.nema.org/medical/dicom/current/output/html/part18.html#sect_10.6). For details on the implementation of SearchForInstances, see [Search transaction](https://cloud.google.com/healthcare/docs/dicom#search_transaction) in the Cloud Healthcare API conformance statement. For samples that show how to call SearchForInstances, see [Search for DICOM data](https://cloud.google.com/healthcare/docs/how-tos/dicomweb#search-dicom). */
							class searchForInstances(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.HttpBody]) {
								val scopes = Seq("""https://www.googleapis.com/auth/cloud-healthcare""", """https://www.googleapis.com/auth/cloud-platform""")
								/** Perform the request */
								def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.HttpBody])
							}
							object searchForInstances {
								def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, dicomStoresId :PlayApi, studiesId :PlayApi, seriesId :PlayApi, parent: String, dicomWebPath: String)(using signer: RequestSigner, ec: ExecutionContext): searchForInstances = new searchForInstances(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/dicomStores/${dicomStoresId}/dicomWeb/studies/${studiesId}/series/${seriesId}/instances").addQueryStringParameters("parent" -> parent.toString, "dicomWebPath" -> dicomWebPath.toString))
								given Conversion[searchForInstances, Future[Schema.HttpBody]] = (fun: searchForInstances) => fun.apply()
							}
							/** DeleteSeries deletes all instances within the given study and series. Delete requests are equivalent to the GET requests specified in the Retrieve transaction. The method returns an Operation which will be marked successful when the deletion is complete. Warning: Instances cannot be inserted into a series that is being deleted by an operation until the operation completes. For samples that show how to call DeleteSeries, see [Delete a study, series, or instance](https://cloud.google.com/healthcare/docs/how-tos/dicomweb#delete-dicom). */
							class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
								val scopes = Seq("""https://www.googleapis.com/auth/cloud-healthcare""", """https://www.googleapis.com/auth/cloud-platform""")
								/** Perform the request */
								def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Operation])
							}
							object delete {
								def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, dicomStoresId :PlayApi, studiesId :PlayApi, seriesId :PlayApi, parent: String, dicomWebPath: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/dicomStores/${dicomStoresId}/dicomWeb/studies/${studiesId}/series/${seriesId}").addQueryStringParameters("parent" -> parent.toString, "dicomWebPath" -> dicomWebPath.toString))
								given Conversion[delete, Future[Schema.Operation]] = (fun: delete) => fun.apply()
							}
							/** RetrieveSeries returns all instances within the given study and series. See [RetrieveTransaction] (http://dicom.nema.org/medical/dicom/current/output/html/part18.html#sect_10.4). For details on the implementation of RetrieveSeries, see [DICOM study/series/instances](https://cloud.google.com/healthcare/docs/dicom#dicom_studyseriesinstances) in the Cloud Healthcare API conformance statement. For samples that show how to call RetrieveSeries, see [Retrieve DICOM data](https://cloud.google.com/healthcare/docs/how-tos/dicomweb#retrieve-dicom). */
							class retrieveSeries(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.HttpBody]) {
								val scopes = Seq("""https://www.googleapis.com/auth/cloud-healthcare""", """https://www.googleapis.com/auth/cloud-platform""")
								/** Perform the request */
								def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.HttpBody])
							}
							object retrieveSeries {
								def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, dicomStoresId :PlayApi, studiesId :PlayApi, seriesId :PlayApi, parent: String, dicomWebPath: String)(using signer: RequestSigner, ec: ExecutionContext): retrieveSeries = new retrieveSeries(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/dicomStores/${dicomStoresId}/dicomWeb/studies/${studiesId}/series/${seriesId}").addQueryStringParameters("parent" -> parent.toString, "dicomWebPath" -> dicomWebPath.toString))
								given Conversion[retrieveSeries, Future[Schema.HttpBody]] = (fun: retrieveSeries) => fun.apply()
							}
							/** RetrieveSeriesMetadata returns instance associated with the given study and series, presented as metadata with the bulk data removed. See [RetrieveTransaction] (http://dicom.nema.org/medical/dicom/current/output/html/part18.html#sect_10.4). For details on the implementation of RetrieveSeriesMetadata, see [Metadata resources](https://cloud.google.com/healthcare/docs/dicom#metadata_resources) in the Cloud Healthcare API conformance statement. For samples that show how to call RetrieveSeriesMetadata, see [Retrieve metadata](https://cloud.google.com/healthcare/docs/how-tos/dicomweb#retrieve-metadata). */
							class retrieveMetadata(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.HttpBody]) {
								val scopes = Seq("""https://www.googleapis.com/auth/cloud-healthcare""", """https://www.googleapis.com/auth/cloud-platform""")
								/** Perform the request */
								def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.HttpBody])
							}
							object retrieveMetadata {
								def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, dicomStoresId :PlayApi, studiesId :PlayApi, seriesId :PlayApi, parent: String, dicomWebPath: String)(using signer: RequestSigner, ec: ExecutionContext): retrieveMetadata = new retrieveMetadata(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/dicomStores/${dicomStoresId}/dicomWeb/studies/${studiesId}/series/${seriesId}/metadata").addQueryStringParameters("parent" -> parent.toString, "dicomWebPath" -> dicomWebPath.toString))
								given Conversion[retrieveMetadata, Future[Schema.HttpBody]] = (fun: retrieveMetadata) => fun.apply()
							}
							object instances {
								/** RetrieveRenderedInstance returns instance associated with the given study, series, and SOP Instance UID in an acceptable Rendered Media Type. See [RetrieveTransaction] (http://dicom.nema.org/medical/dicom/current/output/html/part18.html#sect_10.4). For details on the implementation of RetrieveRenderedInstance, see [Rendered resources](https://cloud.google.com/healthcare/docs/dicom#rendered_resources) in the Cloud Healthcare API conformance statement. For samples that show how to call RetrieveRenderedInstance, see [Retrieve consumer image formats](https://cloud.google.com/healthcare/docs/how-tos/dicomweb#retrieve-consumer). */
								class retrieveRendered(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.HttpBody]) {
									val scopes = Seq("""https://www.googleapis.com/auth/cloud-healthcare""", """https://www.googleapis.com/auth/cloud-platform""")
									/** Perform the request */
									def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.HttpBody])
								}
								object retrieveRendered {
									def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, dicomStoresId :PlayApi, studiesId :PlayApi, seriesId :PlayApi, instancesId :PlayApi, parent: String, dicomWebPath: String)(using signer: RequestSigner, ec: ExecutionContext): retrieveRendered = new retrieveRendered(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/dicomStores/${dicomStoresId}/dicomWeb/studies/${studiesId}/series/${seriesId}/instances/${instancesId}/rendered").addQueryStringParameters("parent" -> parent.toString, "dicomWebPath" -> dicomWebPath.toString))
									given Conversion[retrieveRendered, Future[Schema.HttpBody]] = (fun: retrieveRendered) => fun.apply()
								}
								/** RetrieveInstance returns instance associated with the given study, series, and SOP Instance UID. See [RetrieveTransaction] (http://dicom.nema.org/medical/dicom/current/output/html/part18.html#sect_10.4). For details on the implementation of RetrieveInstance, see [DICOM study/series/instances](https://cloud.google.com/healthcare/docs/dicom#dicom_studyseriesinstances) and [DICOM instances](https://cloud.google.com/healthcare/docs/dicom#dicom_instances) in the Cloud Healthcare API conformance statement. For samples that show how to call RetrieveInstance, see [Retrieve an instance](https://cloud.google.com/healthcare/docs/how-tos/dicomweb#retrieve-instance). */
								class retrieveInstance(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.HttpBody]) {
									val scopes = Seq("""https://www.googleapis.com/auth/cloud-healthcare""", """https://www.googleapis.com/auth/cloud-platform""")
									/** Perform the request */
									def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.HttpBody])
								}
								object retrieveInstance {
									def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, dicomStoresId :PlayApi, studiesId :PlayApi, seriesId :PlayApi, instancesId :PlayApi, parent: String, dicomWebPath: String)(using signer: RequestSigner, ec: ExecutionContext): retrieveInstance = new retrieveInstance(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/dicomStores/${dicomStoresId}/dicomWeb/studies/${studiesId}/series/${seriesId}/instances/${instancesId}").addQueryStringParameters("parent" -> parent.toString, "dicomWebPath" -> dicomWebPath.toString))
									given Conversion[retrieveInstance, Future[Schema.HttpBody]] = (fun: retrieveInstance) => fun.apply()
								}
								/** DeleteInstance deletes an instance associated with the given study, series, and SOP Instance UID. Delete requests are equivalent to the GET requests specified in the Retrieve transaction. Study and series search results can take a few seconds to be updated after an instance is deleted using DeleteInstance. For samples that show how to call DeleteInstance, see [Delete a study, series, or instance](https://cloud.google.com/healthcare/docs/how-tos/dicomweb#delete-dicom). */
								class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
									val scopes = Seq("""https://www.googleapis.com/auth/cloud-healthcare""", """https://www.googleapis.com/auth/cloud-platform""")
									/** Perform the request */
									def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
								}
								object delete {
									def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, dicomStoresId :PlayApi, studiesId :PlayApi, seriesId :PlayApi, instancesId :PlayApi, parent: String, dicomWebPath: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/dicomStores/${dicomStoresId}/dicomWeb/studies/${studiesId}/series/${seriesId}/instances/${instancesId}").addQueryStringParameters("parent" -> parent.toString, "dicomWebPath" -> dicomWebPath.toString))
									given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
								}
								/** RetrieveInstanceMetadata returns instance associated with the given study, series, and SOP Instance UID presented as metadata with the bulk data removed. See [RetrieveTransaction] (http://dicom.nema.org/medical/dicom/current/output/html/part18.html#sect_10.4). For details on the implementation of RetrieveInstanceMetadata, see [Metadata resources](https://cloud.google.com/healthcare/docs/dicom#metadata_resources) in the Cloud Healthcare API conformance statement. For samples that show how to call RetrieveInstanceMetadata, see [Retrieve metadata](https://cloud.google.com/healthcare/docs/how-tos/dicomweb#retrieve-metadata). */
								class retrieveMetadata(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.HttpBody]) {
									val scopes = Seq("""https://www.googleapis.com/auth/cloud-healthcare""", """https://www.googleapis.com/auth/cloud-platform""")
									/** Perform the request */
									def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.HttpBody])
								}
								object retrieveMetadata {
									def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, dicomStoresId :PlayApi, studiesId :PlayApi, seriesId :PlayApi, instancesId :PlayApi, parent: String, dicomWebPath: String)(using signer: RequestSigner, ec: ExecutionContext): retrieveMetadata = new retrieveMetadata(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/dicomStores/${dicomStoresId}/dicomWeb/studies/${studiesId}/series/${seriesId}/instances/${instancesId}/metadata").addQueryStringParameters("parent" -> parent.toString, "dicomWebPath" -> dicomWebPath.toString))
									given Conversion[retrieveMetadata, Future[Schema.HttpBody]] = (fun: retrieveMetadata) => fun.apply()
								}
								object frames {
									/** RetrieveFrames returns instances associated with the given study, series, SOP Instance UID and frame numbers. See [RetrieveTransaction] (http://dicom.nema.org/medical/dicom/current/output/html/part18.html#sect_10.4}. For details on the implementation of RetrieveFrames, see [DICOM frames](https://cloud.google.com/healthcare/docs/dicom#dicom_frames) in the Cloud Healthcare API conformance statement. For samples that show how to call RetrieveFrames, see [Retrieve DICOM data](https://cloud.google.com/healthcare/docs/how-tos/dicomweb#retrieve-dicom). */
									class retrieveFrames(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.HttpBody]) {
										val scopes = Seq("""https://www.googleapis.com/auth/cloud-healthcare""", """https://www.googleapis.com/auth/cloud-platform""")
										/** Perform the request */
										def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.HttpBody])
									}
									object retrieveFrames {
										def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, dicomStoresId :PlayApi, studiesId :PlayApi, seriesId :PlayApi, instancesId :PlayApi, framesId :PlayApi, parent: String, dicomWebPath: String)(using signer: RequestSigner, ec: ExecutionContext): retrieveFrames = new retrieveFrames(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/dicomStores/${dicomStoresId}/dicomWeb/studies/${studiesId}/series/${seriesId}/instances/${instancesId}/frames/${framesId}").addQueryStringParameters("parent" -> parent.toString, "dicomWebPath" -> dicomWebPath.toString))
										given Conversion[retrieveFrames, Future[Schema.HttpBody]] = (fun: retrieveFrames) => fun.apply()
									}
									/** RetrieveRenderedFrames returns instances associated with the given study, series, SOP Instance UID and frame numbers in an acceptable Rendered Media Type. See [RetrieveTransaction] (http://dicom.nema.org/medical/dicom/current/output/html/part18.html#sect_10.4). For details on the implementation of RetrieveRenderedFrames, see [Rendered resources](https://cloud.google.com/healthcare/docs/dicom#rendered_resources) in the Cloud Healthcare API conformance statement. For samples that show how to call RetrieveRenderedFrames, see [Retrieve consumer image formats](https://cloud.google.com/healthcare/docs/how-tos/dicomweb#retrieve-consumer). */
									class retrieveRendered(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.HttpBody]) {
										val scopes = Seq("""https://www.googleapis.com/auth/cloud-healthcare""", """https://www.googleapis.com/auth/cloud-platform""")
										/** Perform the request */
										def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.HttpBody])
									}
									object retrieveRendered {
										def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, dicomStoresId :PlayApi, studiesId :PlayApi, seriesId :PlayApi, instancesId :PlayApi, framesId :PlayApi, parent: String, dicomWebPath: String)(using signer: RequestSigner, ec: ExecutionContext): retrieveRendered = new retrieveRendered(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/dicomStores/${dicomStoresId}/dicomWeb/studies/${studiesId}/series/${seriesId}/instances/${instancesId}/frames/${framesId}/rendered").addQueryStringParameters("parent" -> parent.toString, "dicomWebPath" -> dicomWebPath.toString))
										given Conversion[retrieveRendered, Future[Schema.HttpBody]] = (fun: retrieveRendered) => fun.apply()
									}
								}
							}
						}
					}
				}
			}
			object services {
				object nlp {
					/** Analyze heathcare entity in a document. Its response includes the recognized entity mentions and the relationships between them. AnalyzeEntities uses context aware models to detect entities. */
					class analyzeEntities(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-healthcare""", """https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withAnalyzeEntitiesRequest(body: Schema.AnalyzeEntitiesRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.AnalyzeEntitiesResponse])
					}
					object analyzeEntities {
						def apply(projectsId :PlayApi, locationsId :PlayApi, nlpService: String)(using signer: RequestSigner, ec: ExecutionContext): analyzeEntities = new analyzeEntities(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/services/nlp:analyzeEntities").addQueryStringParameters("nlpService" -> nlpService.toString))
					}
				}
			}
		}
	}
}
