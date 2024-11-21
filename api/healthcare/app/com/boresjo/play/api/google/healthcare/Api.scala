package com.boresjo.play.api.google.healthcare

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://healthcare.googleapis.com/"

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
			object datasets {
				class testIamPermissions(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withTestIamPermissionsRequest(body: Schema.TestIamPermissionsRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.TestIamPermissionsResponse])
				}
				object testIamPermissions {
					def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): testIamPermissions = new testIamPermissions(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}:testIamPermissions")).addQueryStringParameters("resource" -> resource.toString))
				}
				class deidentify(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withDeidentifyDatasetRequest(body: Schema.DeidentifyDatasetRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Operation])
				}
				object deidentify {
					def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, sourceDataset: String)(using auth: AuthToken, ec: ExecutionContext): deidentify = new deidentify(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}:deidentify")).addQueryStringParameters("sourceDataset" -> sourceDataset.toString))
				}
				class getIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Policy]) {
					/** Optional. The maximum policy version that will be used to format the policy. Valid values are 0, 1, and 3. Requests specifying an invalid value will be rejected. Requests for policies with any conditional role bindings must specify version 3. Policies with no conditional role bindings may specify any valid value or leave the field unset. The policy in the response might use the policy version that you specified, or it might use a lower policy version. For example, if you specify version 3, but the policy has no conditional role bindings, the response uses version 1. To learn which resources support conditions in their IAM policies, see the [IAM documentation](https://cloud.google.com/iam/help/conditions/resource-policies).<br>Format: int32 */
					def withOptionsRequestedPolicyVersion(optionsRequestedPolicyVersion: Int) = new getIamPolicy(req.addQueryStringParameters("options.requestedPolicyVersion" -> optionsRequestedPolicyVersion.toString))
					def apply() = req.execute("GET").map(_.json.as[Schema.Policy])
				}
				object getIamPolicy {
					def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): getIamPolicy = new getIamPolicy(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}:getIamPolicy")).addQueryStringParameters("resource" -> resource.toString))
					given Conversion[getIamPolicy, Future[Schema.Policy]] = (fun: getIamPolicy) => fun.apply()
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					def apply() = req.execute("DELETE").map(_.json.as[Schema.Empty])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Dataset]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.Dataset])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.Dataset]] = (fun: get) => fun.apply()
				}
				class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withDataset(body: Schema.Dataset) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.Dataset])
				}
				object patch {
					def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}")).addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListDatasetsResponse]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.ListDatasetsResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets")).addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListDatasetsResponse]] = (fun: list) => fun.apply()
				}
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withDataset(body: Schema.Dataset) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Operation])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, datasetId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets")).addQueryStringParameters("parent" -> parent.toString, "datasetId" -> datasetId.toString))
				}
				class setIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withSetIamPolicyRequest(body: Schema.SetIamPolicyRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Policy])
				}
				object setIamPolicy {
					def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): setIamPolicy = new setIamPolicy(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}:setIamPolicy")).addQueryStringParameters("resource" -> resource.toString))
				}
				object dataMapperWorkspaces {
					class setIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withSetIamPolicyRequest(body: Schema.SetIamPolicyRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Policy])
					}
					object setIamPolicy {
						def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, dataMapperWorkspacesId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): setIamPolicy = new setIamPolicy(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/dataMapperWorkspaces/${dataMapperWorkspacesId}:setIamPolicy")).addQueryStringParameters("resource" -> resource.toString))
					}
					class getIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Policy]) {
						/** Optional. The maximum policy version that will be used to format the policy. Valid values are 0, 1, and 3. Requests specifying an invalid value will be rejected. Requests for policies with any conditional role bindings must specify version 3. Policies with no conditional role bindings may specify any valid value or leave the field unset. The policy in the response might use the policy version that you specified, or it might use a lower policy version. For example, if you specify version 3, but the policy has no conditional role bindings, the response uses version 1. To learn which resources support conditions in their IAM policies, see the [IAM documentation](https://cloud.google.com/iam/help/conditions/resource-policies).<br>Format: int32 */
						def withOptionsRequestedPolicyVersion(optionsRequestedPolicyVersion: Int) = new getIamPolicy(req.addQueryStringParameters("options.requestedPolicyVersion" -> optionsRequestedPolicyVersion.toString))
						def apply() = req.execute("GET").map(_.json.as[Schema.Policy])
					}
					object getIamPolicy {
						def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, dataMapperWorkspacesId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): getIamPolicy = new getIamPolicy(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/dataMapperWorkspaces/${dataMapperWorkspacesId}:getIamPolicy")).addQueryStringParameters("resource" -> resource.toString))
						given Conversion[getIamPolicy, Future[Schema.Policy]] = (fun: getIamPolicy) => fun.apply()
					}
					class testIamPermissions(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withTestIamPermissionsRequest(body: Schema.TestIamPermissionsRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.TestIamPermissionsResponse])
					}
					object testIamPermissions {
						def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, dataMapperWorkspacesId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): testIamPermissions = new testIamPermissions(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/dataMapperWorkspaces/${dataMapperWorkspacesId}:testIamPermissions")).addQueryStringParameters("resource" -> resource.toString))
					}
				}
				object consentStores {
					class testIamPermissions(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withTestIamPermissionsRequest(body: Schema.TestIamPermissionsRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.TestIamPermissionsResponse])
					}
					object testIamPermissions {
						def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, consentStoresId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): testIamPermissions = new testIamPermissions(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/consentStores/${consentStoresId}:testIamPermissions")).addQueryStringParameters("resource" -> resource.toString))
					}
					class queryAccessibleData(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withQueryAccessibleDataRequest(body: Schema.QueryAccessibleDataRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Operation])
					}
					object queryAccessibleData {
						def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, consentStoresId :PlayApi, consentStore: String)(using auth: AuthToken, ec: ExecutionContext): queryAccessibleData = new queryAccessibleData(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/consentStores/${consentStoresId}:queryAccessibleData")).addQueryStringParameters("consentStore" -> consentStore.toString))
					}
					class getIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Policy]) {
						/** Optional. The maximum policy version that will be used to format the policy. Valid values are 0, 1, and 3. Requests specifying an invalid value will be rejected. Requests for policies with any conditional role bindings must specify version 3. Policies with no conditional role bindings may specify any valid value or leave the field unset. The policy in the response might use the policy version that you specified, or it might use a lower policy version. For example, if you specify version 3, but the policy has no conditional role bindings, the response uses version 1. To learn which resources support conditions in their IAM policies, see the [IAM documentation](https://cloud.google.com/iam/help/conditions/resource-policies).<br>Format: int32 */
						def withOptionsRequestedPolicyVersion(optionsRequestedPolicyVersion: Int) = new getIamPolicy(req.addQueryStringParameters("options.requestedPolicyVersion" -> optionsRequestedPolicyVersion.toString))
						def apply() = req.execute("GET").map(_.json.as[Schema.Policy])
					}
					object getIamPolicy {
						def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, consentStoresId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): getIamPolicy = new getIamPolicy(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/consentStores/${consentStoresId}:getIamPolicy")).addQueryStringParameters("resource" -> resource.toString))
						given Conversion[getIamPolicy, Future[Schema.Policy]] = (fun: getIamPolicy) => fun.apply()
					}
					class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
						def apply() = req.execute("DELETE").map(_.json.as[Schema.Empty])
					}
					object delete {
						def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, consentStoresId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/consentStores/${consentStoresId}")).addQueryStringParameters("name" -> name.toString))
						given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
					}
					class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withConsentStore(body: Schema.ConsentStore) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.ConsentStore])
					}
					object patch {
						def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, consentStoresId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/consentStores/${consentStoresId}")).addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
					}
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListConsentStoresResponse]) {
						/** Optional. Limit on the number of consent stores to return in a single response. If not specified, 100 is used. May not be larger than 1000.<br>Format: int32 */
						def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
						/** Optional. Token to retrieve the next page of results, or empty to get the first page. */
						def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
						/** Optional. Restricts the stores returned to those matching a filter. Only filtering on labels is supported. For example, `filter=labels.key=value`. */
						def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
						def apply() = req.execute("GET").map(_.json.as[Schema.ListConsentStoresResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/consentStores")).addQueryStringParameters("parent" -> parent.toString))
						given Conversion[list, Future[Schema.ListConsentStoresResponse]] = (fun: list) => fun.apply()
					}
					class checkDataAccess(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withCheckDataAccessRequest(body: Schema.CheckDataAccessRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.CheckDataAccessResponse])
					}
					object checkDataAccess {
						def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, consentStoresId :PlayApi, consentStore: String)(using auth: AuthToken, ec: ExecutionContext): checkDataAccess = new checkDataAccess(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/consentStores/${consentStoresId}:checkDataAccess")).addQueryStringParameters("consentStore" -> consentStore.toString))
					}
					class evaluateUserConsents(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withEvaluateUserConsentsRequest(body: Schema.EvaluateUserConsentsRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.EvaluateUserConsentsResponse])
					}
					object evaluateUserConsents {
						def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, consentStoresId :PlayApi, consentStore: String)(using auth: AuthToken, ec: ExecutionContext): evaluateUserConsents = new evaluateUserConsents(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/consentStores/${consentStoresId}:evaluateUserConsents")).addQueryStringParameters("consentStore" -> consentStore.toString))
					}
					class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withConsentStore(body: Schema.ConsentStore) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.ConsentStore])
					}
					object create {
						def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, parent: String, consentStoreId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/consentStores")).addQueryStringParameters("parent" -> parent.toString, "consentStoreId" -> consentStoreId.toString))
					}
					class setIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withSetIamPolicyRequest(body: Schema.SetIamPolicyRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Policy])
					}
					object setIamPolicy {
						def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, consentStoresId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): setIamPolicy = new setIamPolicy(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/consentStores/${consentStoresId}:setIamPolicy")).addQueryStringParameters("resource" -> resource.toString))
					}
					class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ConsentStore]) {
						def apply() = req.execute("GET").map(_.json.as[Schema.ConsentStore])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, consentStoresId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/consentStores/${consentStoresId}")).addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.ConsentStore]] = (fun: get) => fun.apply()
					}
					object consents {
						class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
							def withConsent(body: Schema.Consent) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Consent])
						}
						object create {
							def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, consentStoresId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/consentStores/${consentStoresId}/consents")).addQueryStringParameters("parent" -> parent.toString))
						}
						class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
							def apply() = req.execute("DELETE").map(_.json.as[Schema.Empty])
						}
						object delete {
							def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, consentStoresId :PlayApi, consentsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/consentStores/${consentStoresId}/consents/${consentsId}")).addQueryStringParameters("name" -> name.toString))
							given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
						}
						class deleteRevision(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
							def apply() = req.execute("DELETE").map(_.json.as[Schema.Empty])
						}
						object deleteRevision {
							def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, consentStoresId :PlayApi, consentsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): deleteRevision = new deleteRevision(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/consentStores/${consentStoresId}/consents/${consentsId}:deleteRevision")).addQueryStringParameters("name" -> name.toString))
							given Conversion[deleteRevision, Future[Schema.Empty]] = (fun: deleteRevision) => fun.apply()
						}
						class activate(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
							def withActivateConsentRequest(body: Schema.ActivateConsentRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Consent])
						}
						object activate {
							def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, consentStoresId :PlayApi, consentsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): activate = new activate(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/consentStores/${consentStoresId}/consents/${consentsId}:activate")).addQueryStringParameters("name" -> name.toString))
						}
						class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
							def withConsent(body: Schema.Consent) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.Consent])
						}
						object patch {
							def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, consentStoresId :PlayApi, consentsId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/consentStores/${consentStoresId}/consents/${consentsId}")).addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
						}
						class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListConsentsResponse]) {
							/** Optional. Limit on the number of Consents to return in a single response. If not specified, 100 is used. May not be larger than 1000.<br>Format: int32 */
							def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
							/** Optional. The next_page_token value returned from the previous List request, if any. */
							def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
							/** Optional. Restricts the Consents returned to those matching a filter. The following syntax is available: &#42; A string field value can be written as text inside quotation marks, for example `"query text"`. The only valid relational operation for text fields is equality (`=`), where text is searched within the field, rather than having the field be equal to the text. For example, `"Comment = great"` returns messages with `great` in the comment field. &#42; A number field value can be written as an integer, a decimal, or an exponential. The valid relational operators for number fields are the equality operator (`=`), along with the less than/greater than operators (`<`, `<=`, `>`, `>=`). Note that there is no inequality (`!=`) operator. You can prepend the `NOT` operator to an expression to negate it. &#42; A date field value must be written in `yyyy-mm-dd` form. Fields with date and time use the RFC3339 time format. Leading zeros are required for one-digit months and days. The valid relational operators for date fields are the equality operator (`=`) , along with the less than/greater than operators (`<`, `<=`, `>`, `>=`). Note that there is no inequality (`!=`) operator. You can prepend the `NOT` operator to an expression to negate it. &#42; Multiple field query expressions can be combined in one query by adding `AND` or `OR` operators between the expressions. If a boolean operator appears within a quoted string, it is not treated as special, it's just another part of the character string to be matched. You can prepend the `NOT` operator to an expression to negate it. The fields available for filtering are: - user_id. For example, `filter='user_id="user123"'`. - consent_artifact - state - revision_create_time - metadata. For example, `filter=Metadata(\"testkey\")=\"value\"` or `filter=HasMetadata(\"testkey\")`. */
							def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
							def apply() = req.execute("GET").map(_.json.as[Schema.ListConsentsResponse])
						}
						object list {
							def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, consentStoresId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/consentStores/${consentStoresId}/consents")).addQueryStringParameters("parent" -> parent.toString))
							given Conversion[list, Future[Schema.ListConsentsResponse]] = (fun: list) => fun.apply()
						}
						class revoke(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
							def withRevokeConsentRequest(body: Schema.RevokeConsentRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Consent])
						}
						object revoke {
							def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, consentStoresId :PlayApi, consentsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): revoke = new revoke(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/consentStores/${consentStoresId}/consents/${consentsId}:revoke")).addQueryStringParameters("name" -> name.toString))
						}
						class reject(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
							def withRejectConsentRequest(body: Schema.RejectConsentRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Consent])
						}
						object reject {
							def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, consentStoresId :PlayApi, consentsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): reject = new reject(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/consentStores/${consentStoresId}/consents/${consentsId}:reject")).addQueryStringParameters("name" -> name.toString))
						}
						class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Consent]) {
							def apply() = req.execute("GET").map(_.json.as[Schema.Consent])
						}
						object get {
							def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, consentStoresId :PlayApi, consentsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/consentStores/${consentStoresId}/consents/${consentsId}")).addQueryStringParameters("name" -> name.toString))
							given Conversion[get, Future[Schema.Consent]] = (fun: get) => fun.apply()
						}
						class listRevisions(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListConsentRevisionsResponse]) {
							/** Optional. Limit on the number of revisions to return in a single response. If not specified, 100 is used. May not be larger than 1000.<br>Format: int32 */
							def withPageSize(pageSize: Int) = new listRevisions(req.addQueryStringParameters("pageSize" -> pageSize.toString))
							/** Optional. Token to retrieve the next page of results or empty if there are no more results in the list. */
							def withPageToken(pageToken: String) = new listRevisions(req.addQueryStringParameters("pageToken" -> pageToken.toString))
							/** Optional. Restricts the revisions returned to those matching a filter. The following syntax is available: &#42; A string field value can be written as text inside quotation marks, for example `"query text"`. The only valid relational operation for text fields is equality (`=`), where text is searched within the field, rather than having the field be equal to the text. For example, `"Comment = great"` returns messages with `great` in the comment field. &#42; A number field value can be written as an integer, a decimal, or an exponential. The valid relational operators for number fields are the equality operator (`=`), along with the less than/greater than operators (`<`, `<=`, `>`, `>=`). Note that there is no inequality (`!=`) operator. You can prepend the `NOT` operator to an expression to negate it. &#42; A date field value must be written in `yyyy-mm-dd` form. Fields with date and time use the RFC3339 time format. Leading zeros are required for one-digit months and days. The valid relational operators for date fields are the equality operator (`=`) , along with the less than/greater than operators (`<`, `<=`, `>`, `>=`). Note that there is no inequality (`!=`) operator. You can prepend the `NOT` operator to an expression to negate it. &#42; Multiple field query expressions can be combined in one query by adding `AND` or `OR` operators between the expressions. If a boolean operator appears within a quoted string, it is not treated as special, it's just another part of the character string to be matched. You can prepend the `NOT` operator to an expression to negate it. Fields available for filtering are: - user_id. For example, `filter='user_id="user123"'`. - consent_artifact - state - revision_create_time - metadata. For example, `filter=Metadata(\"testkey\")=\"value\"` or `filter=HasMetadata(\"testkey\")`. */
							def withFilter(filter: String) = new listRevisions(req.addQueryStringParameters("filter" -> filter.toString))
							def apply() = req.execute("GET").map(_.json.as[Schema.ListConsentRevisionsResponse])
						}
						object listRevisions {
							def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, consentStoresId :PlayApi, consentsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): listRevisions = new listRevisions(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/consentStores/${consentStoresId}/consents/${consentsId}:listRevisions")).addQueryStringParameters("name" -> name.toString))
							given Conversion[listRevisions, Future[Schema.ListConsentRevisionsResponse]] = (fun: listRevisions) => fun.apply()
						}
					}
					object attributeDefinitions {
						class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
							def withAttributeDefinition(body: Schema.AttributeDefinition) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.AttributeDefinition])
						}
						object create {
							def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, consentStoresId :PlayApi, parent: String, attributeDefinitionId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/consentStores/${consentStoresId}/attributeDefinitions")).addQueryStringParameters("parent" -> parent.toString, "attributeDefinitionId" -> attributeDefinitionId.toString))
						}
						class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
							def apply() = req.execute("DELETE").map(_.json.as[Schema.Empty])
						}
						object delete {
							def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, consentStoresId :PlayApi, attributeDefinitionsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/consentStores/${consentStoresId}/attributeDefinitions/${attributeDefinitionsId}")).addQueryStringParameters("name" -> name.toString))
							given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
						}
						class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.AttributeDefinition]) {
							def apply() = req.execute("GET").map(_.json.as[Schema.AttributeDefinition])
						}
						object get {
							def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, consentStoresId :PlayApi, attributeDefinitionsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/consentStores/${consentStoresId}/attributeDefinitions/${attributeDefinitionsId}")).addQueryStringParameters("name" -> name.toString))
							given Conversion[get, Future[Schema.AttributeDefinition]] = (fun: get) => fun.apply()
						}
						class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
							def withAttributeDefinition(body: Schema.AttributeDefinition) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.AttributeDefinition])
						}
						object patch {
							def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, consentStoresId :PlayApi, attributeDefinitionsId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/consentStores/${consentStoresId}/attributeDefinitions/${attributeDefinitionsId}")).addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
						}
						class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListAttributeDefinitionsResponse]) {
							/** Optional. Limit on the number of Attribute definitions to return in a single response. If not specified, 100 is used. May not be larger than 1000.<br>Format: int32 */
							def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
							/** Optional. Token to retrieve the next page of results or empty to get the first page. */
							def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
							/** Optional. Restricts the attributes returned to those matching a filter. The only field available for filtering is `category`. For example, `filter=category=\"REQUEST\"`. */
							def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
							def apply() = req.execute("GET").map(_.json.as[Schema.ListAttributeDefinitionsResponse])
						}
						object list {
							def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, consentStoresId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/consentStores/${consentStoresId}/attributeDefinitions")).addQueryStringParameters("parent" -> parent.toString))
							given Conversion[list, Future[Schema.ListAttributeDefinitionsResponse]] = (fun: list) => fun.apply()
						}
					}
					object userDataMappings {
						class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
							def withUserDataMapping(body: Schema.UserDataMapping) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.UserDataMapping])
						}
						object create {
							def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, consentStoresId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/consentStores/${consentStoresId}/userDataMappings")).addQueryStringParameters("parent" -> parent.toString))
						}
						class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
							def apply() = req.execute("DELETE").map(_.json.as[Schema.Empty])
						}
						object delete {
							def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, consentStoresId :PlayApi, userDataMappingsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/consentStores/${consentStoresId}/userDataMappings/${userDataMappingsId}")).addQueryStringParameters("name" -> name.toString))
							given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
						}
						class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.UserDataMapping]) {
							def apply() = req.execute("GET").map(_.json.as[Schema.UserDataMapping])
						}
						object get {
							def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, consentStoresId :PlayApi, userDataMappingsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/consentStores/${consentStoresId}/userDataMappings/${userDataMappingsId}")).addQueryStringParameters("name" -> name.toString))
							given Conversion[get, Future[Schema.UserDataMapping]] = (fun: get) => fun.apply()
						}
						class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
							def withUserDataMapping(body: Schema.UserDataMapping) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.UserDataMapping])
						}
						object patch {
							def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, consentStoresId :PlayApi, userDataMappingsId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/consentStores/${consentStoresId}/userDataMappings/${userDataMappingsId}")).addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
						}
						class archive(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
							def withArchiveUserDataMappingRequest(body: Schema.ArchiveUserDataMappingRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.ArchiveUserDataMappingResponse])
						}
						object archive {
							def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, consentStoresId :PlayApi, userDataMappingsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): archive = new archive(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/consentStores/${consentStoresId}/userDataMappings/${userDataMappingsId}:archive")).addQueryStringParameters("name" -> name.toString))
						}
						class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListUserDataMappingsResponse]) {
							/** Optional. Limit on the number of User data mappings to return in a single response. If not specified, 100 is used. May not be larger than 1000.<br>Format: int32 */
							def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
							/** Optional. Token to retrieve the next page of results, or empty to get the first page. */
							def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
							/** Optional. Restricts the User data mappings returned to those matching a filter. The following syntax is available: &#42; A string field value can be written as text inside quotation marks, for example `"query text"`. The only valid relational operation for text fields is equality (`=`), where text is searched within the field, rather than having the field be equal to the text. For example, `"Comment = great"` returns messages with `great` in the comment field. &#42; A number field value can be written as an integer, a decimal, or an exponential. The valid relational operators for number fields are the equality operator (`=`), along with the less than/greater than operators (`<`, `<=`, `>`, `>=`). Note that there is no inequality (`!=`) operator. You can prepend the `NOT` operator to an expression to negate it. &#42; A date field value must be written in `yyyy-mm-dd` form. Fields with date and time use the RFC3339 time format. Leading zeros are required for one-digit months and days. The valid relational operators for date fields are the equality operator (`=`) , along with the less than/greater than operators (`<`, `<=`, `>`, `>=`). Note that there is no inequality (`!=`) operator. You can prepend the `NOT` operator to an expression to negate it. &#42; Multiple field query expressions can be combined in one query by adding `AND` or `OR` operators between the expressions. If a boolean operator appears within a quoted string, it is not treated as special, it's just another part of the character string to be matched. You can prepend the `NOT` operator to an expression to negate it. The fields available for filtering are: - data_id - user_id. For example, `filter=user_id=\"user123\"`. - archived - archive_time */
							def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
							def apply() = req.execute("GET").map(_.json.as[Schema.ListUserDataMappingsResponse])
						}
						object list {
							def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, consentStoresId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/consentStores/${consentStoresId}/userDataMappings")).addQueryStringParameters("parent" -> parent.toString))
							given Conversion[list, Future[Schema.ListUserDataMappingsResponse]] = (fun: list) => fun.apply()
						}
					}
					object consentArtifacts {
						class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
							def withConsentArtifact(body: Schema.ConsentArtifact) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.ConsentArtifact])
						}
						object create {
							def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, consentStoresId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/consentStores/${consentStoresId}/consentArtifacts")).addQueryStringParameters("parent" -> parent.toString))
						}
						class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ConsentArtifact]) {
							def apply() = req.execute("GET").map(_.json.as[Schema.ConsentArtifact])
						}
						object get {
							def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, consentStoresId :PlayApi, consentArtifactsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/consentStores/${consentStoresId}/consentArtifacts/${consentArtifactsId}")).addQueryStringParameters("name" -> name.toString))
							given Conversion[get, Future[Schema.ConsentArtifact]] = (fun: get) => fun.apply()
						}
						class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
							def apply() = req.execute("DELETE").map(_.json.as[Schema.Empty])
						}
						object delete {
							def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, consentStoresId :PlayApi, consentArtifactsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/consentStores/${consentStoresId}/consentArtifacts/${consentArtifactsId}")).addQueryStringParameters("name" -> name.toString))
							given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
						}
						class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListConsentArtifactsResponse]) {
							/** Optional. Limit on the number of consent artifacts to return in a single response. If not specified, 100 is used. May not be larger than 1000.<br>Format: int32 */
							def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
							/** Optional. The next_page_token value returned from the previous List request, if any. */
							def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
							/** Optional. Restricts the artifacts returned to those matching a filter. The following syntax is available: &#42; A string field value can be written as text inside quotation marks, for example `"query text"`. The only valid relational operation for text fields is equality (`=`), where text is searched within the field, rather than having the field be equal to the text. For example, `"Comment = great"` returns messages with `great` in the comment field. &#42; A number field value can be written as an integer, a decimal, or an exponential. The valid relational operators for number fields are the equality operator (`=`), along with the less than/greater than operators (`<`, `<=`, `>`, `>=`). Note that there is no inequality (`!=`) operator. You can prepend the `NOT` operator to an expression to negate it. &#42; A date field value must be written in `yyyy-mm-dd` form. Fields with date and time use the RFC3339 time format. Leading zeros are required for one-digit months and days. The valid relational operators for date fields are the equality operator (`=`) , along with the less than/greater than operators (`<`, `<=`, `>`, `>=`). Note that there is no inequality (`!=`) operator. You can prepend the `NOT` operator to an expression to negate it. &#42; Multiple field query expressions can be combined in one query by adding `AND` or `OR` operators between the expressions. If a boolean operator appears within a quoted string, it is not treated as special, it's just another part of the character string to be matched. You can prepend the `NOT` operator to an expression to negate it. The fields available for filtering are: - user_id. For example, `filter=user_id=\"user123\"`. - consent_content_version - metadata. For example, `filter=Metadata(\"testkey\")=\"value\"` or `filter=HasMetadata(\"testkey\")`. */
							def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
							def apply() = req.execute("GET").map(_.json.as[Schema.ListConsentArtifactsResponse])
						}
						object list {
							def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, consentStoresId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/consentStores/${consentStoresId}/consentArtifacts")).addQueryStringParameters("parent" -> parent.toString))
							given Conversion[list, Future[Schema.ListConsentArtifactsResponse]] = (fun: list) => fun.apply()
						}
					}
				}
				object fhirStores {
					class testIamPermissions(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withTestIamPermissionsRequest(body: Schema.TestIamPermissionsRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.TestIamPermissionsResponse])
					}
					object testIamPermissions {
						def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, fhirStoresId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): testIamPermissions = new testIamPermissions(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/fhirStores/${fhirStoresId}:testIamPermissions")).addQueryStringParameters("resource" -> resource.toString))
					}
					class deidentify(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withDeidentifyFhirStoreRequest(body: Schema.DeidentifyFhirStoreRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Operation])
					}
					object deidentify {
						def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, fhirStoresId :PlayApi, sourceStore: String)(using auth: AuthToken, ec: ExecutionContext): deidentify = new deidentify(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/fhirStores/${fhirStoresId}:deidentify")).addQueryStringParameters("sourceStore" -> sourceStore.toString))
					}
					class `export`(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withExportResourcesRequest(body: Schema.ExportResourcesRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Operation])
					}
					object `export` {
						def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, fhirStoresId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): `export` = new `export`(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/fhirStores/${fhirStoresId}:export")).addQueryStringParameters("name" -> name.toString))
					}
					class rollback(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withRollbackFhirResourcesRequest(body: Schema.RollbackFhirResourcesRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Operation])
					}
					object rollback {
						def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, fhirStoresId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): rollback = new rollback(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/fhirStores/${fhirStoresId}:rollback")).addQueryStringParameters("name" -> name.toString))
					}
					class `import`(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withImportResourcesRequest(body: Schema.ImportResourcesRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Operation])
					}
					object `import` {
						def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, fhirStoresId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): `import` = new `import`(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/fhirStores/${fhirStoresId}:import")).addQueryStringParameters("name" -> name.toString))
					}
					class getIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Policy]) {
						/** Optional. The maximum policy version that will be used to format the policy. Valid values are 0, 1, and 3. Requests specifying an invalid value will be rejected. Requests for policies with any conditional role bindings must specify version 3. Policies with no conditional role bindings may specify any valid value or leave the field unset. The policy in the response might use the policy version that you specified, or it might use a lower policy version. For example, if you specify version 3, but the policy has no conditional role bindings, the response uses version 1. To learn which resources support conditions in their IAM policies, see the [IAM documentation](https://cloud.google.com/iam/help/conditions/resource-policies).<br>Format: int32 */
						def withOptionsRequestedPolicyVersion(optionsRequestedPolicyVersion: Int) = new getIamPolicy(req.addQueryStringParameters("options.requestedPolicyVersion" -> optionsRequestedPolicyVersion.toString))
						def apply() = req.execute("GET").map(_.json.as[Schema.Policy])
					}
					object getIamPolicy {
						def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, fhirStoresId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): getIamPolicy = new getIamPolicy(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/fhirStores/${fhirStoresId}:getIamPolicy")).addQueryStringParameters("resource" -> resource.toString))
						given Conversion[getIamPolicy, Future[Schema.Policy]] = (fun: getIamPolicy) => fun.apply()
					}
					class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
						def apply() = req.execute("DELETE").map(_.json.as[Schema.Empty])
					}
					object delete {
						def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, fhirStoresId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/fhirStores/${fhirStoresId}")).addQueryStringParameters("name" -> name.toString))
						given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
					}
					class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.FhirStore]) {
						def apply() = req.execute("GET").map(_.json.as[Schema.FhirStore])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, fhirStoresId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/fhirStores/${fhirStoresId}")).addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.FhirStore]] = (fun: get) => fun.apply()
					}
					class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withFhirStore(body: Schema.FhirStore) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.FhirStore])
					}
					object patch {
						def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, fhirStoresId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/fhirStores/${fhirStoresId}")).addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
					}
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListFhirStoresResponse]) {
						def apply() = req.execute("GET").map(_.json.as[Schema.ListFhirStoresResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, parent: String, pageSize: Int, pageToken: String, filter: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/fhirStores")).addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "filter" -> filter.toString))
						given Conversion[list, Future[Schema.ListFhirStoresResponse]] = (fun: list) => fun.apply()
					}
					class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withFhirStore(body: Schema.FhirStore) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.FhirStore])
					}
					object create {
						def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, parent: String, fhirStoreId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/fhirStores")).addQueryStringParameters("parent" -> parent.toString, "fhirStoreId" -> fhirStoreId.toString))
					}
					class setIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withSetIamPolicyRequest(body: Schema.SetIamPolicyRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Policy])
					}
					object setIamPolicy {
						def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, fhirStoresId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): setIamPolicy = new setIamPolicy(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/fhirStores/${fhirStoresId}:setIamPolicy")).addQueryStringParameters("resource" -> resource.toString))
					}
					class getFHIRStoreMetrics(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.FhirStoreMetrics]) {
						def apply() = req.execute("GET").map(_.json.as[Schema.FhirStoreMetrics])
					}
					object getFHIRStoreMetrics {
						def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, fhirStoresId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): getFHIRStoreMetrics = new getFHIRStoreMetrics(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/fhirStores/${fhirStoresId}:getFHIRStoreMetrics")).addQueryStringParameters("name" -> name.toString))
						given Conversion[getFHIRStoreMetrics, Future[Schema.FhirStoreMetrics]] = (fun: getFHIRStoreMetrics) => fun.apply()
					}
					object fhir {
						class Binary_vread(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.HttpBody]) {
							def apply() = req.execute("GET").map(_.json.as[Schema.HttpBody])
						}
						object Binary_vread {
							def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, fhirStoresId :PlayApi, BinaryId :PlayApi, _historyId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): Binary_vread = new Binary_vread(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/fhirStores/${fhirStoresId}/fhir/Binary/${BinaryId}/_history/${_historyId}")).addQueryStringParameters("name" -> name.toString))
							given Conversion[Binary_vread, Future[Schema.HttpBody]] = (fun: Binary_vread) => fun.apply()
						}
						class history(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.HttpBody]) {
							def apply() = req.execute("GET").map(_.json.as[Schema.HttpBody])
						}
						object history {
							def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, fhirStoresId :PlayApi, fhirId :PlayApi, fhirId1 :PlayApi, name: String, _count: Int, _since: String, _at: String, _page_token: String)(using auth: AuthToken, ec: ExecutionContext): history = new history(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/fhirStores/${fhirStoresId}/fhir/${fhirId}/${fhirId1}/_history")).addQueryStringParameters("name" -> name.toString, "_count" -> _count.toString, "_since" -> _since.toString, "_at" -> _at.toString, "_page_token" -> _page_token.toString))
							given Conversion[history, Future[Schema.HttpBody]] = (fun: history) => fun.apply()
						}
						class conditionalUpdate(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
							def withHttpBody(body: Schema.HttpBody) = req.withBody(Json.toJson(body)).execute("PUT").map(_.json.as[Schema.HttpBody])
						}
						object conditionalUpdate {
							def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, fhirStoresId :PlayApi, fhirId :PlayApi, parent: String, `type`: String)(using auth: AuthToken, ec: ExecutionContext): conditionalUpdate = new conditionalUpdate(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/fhirStores/${fhirStoresId}/fhir/${fhirId}")).addQueryStringParameters("parent" -> parent.toString, "type" -> `type`.toString))
						}
						class Resource_validate(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
							def withHttpBody(body: Schema.HttpBody) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.HttpBody])
						}
						object Resource_validate {
							def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, fhirStoresId :PlayApi, fhirId :PlayApi, parent: String, `type`: String, profile: String)(using auth: AuthToken, ec: ExecutionContext): Resource_validate = new Resource_validate(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/fhirStores/${fhirStoresId}/fhir/${fhirId}/$$validate")).addQueryStringParameters("parent" -> parent.toString, "type" -> `type`.toString, "profile" -> profile.toString))
						}
						class conditionalDelete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
							def apply() = req.execute("DELETE").map(_.json.as[Schema.Empty])
						}
						object conditionalDelete {
							def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, fhirStoresId :PlayApi, fhirId :PlayApi, parent: String, `type`: String)(using auth: AuthToken, ec: ExecutionContext): conditionalDelete = new conditionalDelete(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/fhirStores/${fhirStoresId}/fhir/${fhirId}")).addQueryStringParameters("parent" -> parent.toString, "type" -> `type`.toString))
							given Conversion[conditionalDelete, Future[Schema.Empty]] = (fun: conditionalDelete) => fun.apply()
						}
						class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.HttpBody]) {
							def apply() = req.execute("DELETE").map(_.json.as[Schema.HttpBody])
						}
						object delete {
							def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, fhirStoresId :PlayApi, fhirId :PlayApi, fhirId1 :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/fhirStores/${fhirStoresId}/fhir/${fhirId}/${fhirId1}")).addQueryStringParameters("name" -> name.toString))
							given Conversion[delete, Future[Schema.HttpBody]] = (fun: delete) => fun.apply()
						}
						class conditionalPatch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
							def withHttpBody(body: Schema.HttpBody) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.HttpBody])
						}
						object conditionalPatch {
							def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, fhirStoresId :PlayApi, fhirId :PlayApi, parent: String, `type`: String)(using auth: AuthToken, ec: ExecutionContext): conditionalPatch = new conditionalPatch(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/fhirStores/${fhirStoresId}/fhir/${fhirId}")).addQueryStringParameters("parent" -> parent.toString, "type" -> `type`.toString))
						}
						class vread(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.HttpBody]) {
							def apply() = req.execute("GET").map(_.json.as[Schema.HttpBody])
						}
						object vread {
							def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, fhirStoresId :PlayApi, fhirId :PlayApi, fhirId1 :PlayApi, _historyId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): vread = new vread(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/fhirStores/${fhirStoresId}/fhir/${fhirId}/${fhirId1}/_history/${_historyId}")).addQueryStringParameters("name" -> name.toString))
							given Conversion[vread, Future[Schema.HttpBody]] = (fun: vread) => fun.apply()
						}
						class Resource_purge(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
							def apply() = req.execute("DELETE").map(_.json.as[Schema.Empty])
						}
						object Resource_purge {
							def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, fhirStoresId :PlayApi, fhirId :PlayApi, fhirId1 :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): Resource_purge = new Resource_purge(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/fhirStores/${fhirStoresId}/fhir/${fhirId}/${fhirId1}/$$purge")).addQueryStringParameters("name" -> name.toString))
							given Conversion[Resource_purge, Future[Schema.Empty]] = (fun: Resource_purge) => fun.apply()
						}
						class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
							def withHttpBody(body: Schema.HttpBody) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.HttpBody])
						}
						object patch {
							def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, fhirStoresId :PlayApi, fhirId :PlayApi, fhirId1 :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/fhirStores/${fhirStoresId}/fhir/${fhirId}/${fhirId1}")).addQueryStringParameters("name" -> name.toString))
						}
						class search(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
							def withSearchResourcesRequest(body: Schema.SearchResourcesRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.HttpBody])
						}
						object search {
							def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, fhirStoresId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): search = new search(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/fhirStores/${fhirStoresId}/fhir/_search")).addQueryStringParameters("parent" -> parent.toString))
						}
						class read(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.HttpBody]) {
							def apply() = req.execute("GET").map(_.json.as[Schema.HttpBody])
						}
						object read {
							def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, fhirStoresId :PlayApi, fhirId :PlayApi, fhirId1 :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): read = new read(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/fhirStores/${fhirStoresId}/fhir/${fhirId}/${fhirId1}")).addQueryStringParameters("name" -> name.toString))
							given Conversion[read, Future[Schema.HttpBody]] = (fun: read) => fun.apply()
						}
						class capabilities(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.HttpBody]) {
							def apply() = req.execute("GET").map(_.json.as[Schema.HttpBody])
						}
						object capabilities {
							def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, fhirStoresId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): capabilities = new capabilities(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/fhirStores/${fhirStoresId}/fhir/metadata")).addQueryStringParameters("name" -> name.toString))
							given Conversion[capabilities, Future[Schema.HttpBody]] = (fun: capabilities) => fun.apply()
						}
						class Binary_update(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
							def withHttpBody(body: Schema.HttpBody) = req.withBody(Json.toJson(body)).execute("PUT").map(_.json.as[Schema.HttpBody])
						}
						object Binary_update {
							def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, fhirStoresId :PlayApi, BinaryId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): Binary_update = new Binary_update(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/fhirStores/${fhirStoresId}/fhir/Binary/${BinaryId}")).addQueryStringParameters("name" -> name.toString))
						}
						class executeBundle(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
							def withHttpBody(body: Schema.HttpBody) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.HttpBody])
						}
						object executeBundle {
							def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, fhirStoresId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): executeBundle = new executeBundle(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/fhirStores/${fhirStoresId}/fhir")).addQueryStringParameters("parent" -> parent.toString))
						}
						class Binary_read(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.HttpBody]) {
							def apply() = req.execute("GET").map(_.json.as[Schema.HttpBody])
						}
						object Binary_read {
							def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, fhirStoresId :PlayApi, BinaryId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): Binary_read = new Binary_read(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/fhirStores/${fhirStoresId}/fhir/Binary/${BinaryId}")).addQueryStringParameters("name" -> name.toString))
							given Conversion[Binary_read, Future[Schema.HttpBody]] = (fun: Binary_read) => fun.apply()
						}
						class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
							def withHttpBody(body: Schema.HttpBody) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.HttpBody])
						}
						object create {
							def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, fhirStoresId :PlayApi, fhirId :PlayApi, parent: String, `type`: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/fhirStores/${fhirStoresId}/fhir/${fhirId}")).addQueryStringParameters("parent" -> parent.toString, "type" -> `type`.toString))
						}
						class Binary_create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
							def withHttpBody(body: Schema.HttpBody) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.HttpBody])
						}
						object Binary_create {
							def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, fhirStoresId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): Binary_create = new Binary_create(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/fhirStores/${fhirStoresId}/fhir/Binary")).addQueryStringParameters("parent" -> parent.toString))
						}
						class Patient_everything(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.HttpBody]) {
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
							def apply() = req.execute("GET").map(_.json.as[Schema.HttpBody])
						}
						object Patient_everything {
							def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, fhirStoresId :PlayApi, PatientId :PlayApi, name: String, _page_token: String)(using auth: AuthToken, ec: ExecutionContext): Patient_everything = new Patient_everything(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/fhirStores/${fhirStoresId}/fhir/Patient/${PatientId}/$$everything")).addQueryStringParameters("name" -> name.toString, "_page_token" -> _page_token.toString))
							given Conversion[Patient_everything, Future[Schema.HttpBody]] = (fun: Patient_everything) => fun.apply()
						}
						class update(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
							def withHttpBody(body: Schema.HttpBody) = req.withBody(Json.toJson(body)).execute("PUT").map(_.json.as[Schema.HttpBody])
						}
						object update {
							def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, fhirStoresId :PlayApi, fhirId :PlayApi, fhirId1 :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): update = new update(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/fhirStores/${fhirStoresId}/fhir/${fhirId}/${fhirId1}")).addQueryStringParameters("name" -> name.toString))
						}
						class search_type(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
							def withSearchResourcesRequest(body: Schema.SearchResourcesRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.HttpBody])
						}
						object search_type {
							def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, fhirStoresId :PlayApi, parent: String, resourceType: String)(using auth: AuthToken, ec: ExecutionContext): search_type = new search_type(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/fhirStores/${fhirStoresId}/fhir/${resourceType}/_search")).addQueryStringParameters("parent" -> parent.toString))
						}
					}
				}
				object hl7V2Stores {
					class `export`(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withExportMessagesRequest(body: Schema.ExportMessagesRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Operation])
					}
					object `export` {
						def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, hl7V2StoresId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): `export` = new `export`(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/hl7V2Stores/${hl7V2StoresId}:export")).addQueryStringParameters("name" -> name.toString))
					}
					class rollback(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withRollbackHl7V2MessagesRequest(body: Schema.RollbackHl7V2MessagesRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Operation])
					}
					object rollback {
						def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, hl7V2StoresId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): rollback = new rollback(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/hl7V2Stores/${hl7V2StoresId}:rollback")).addQueryStringParameters("name" -> name.toString))
					}
					class `import`(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withImportMessagesRequest(body: Schema.ImportMessagesRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Operation])
					}
					object `import` {
						def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, hl7V2StoresId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): `import` = new `import`(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/hl7V2Stores/${hl7V2StoresId}:import")).addQueryStringParameters("name" -> name.toString))
					}
					class getIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Policy]) {
						/** Optional. The maximum policy version that will be used to format the policy. Valid values are 0, 1, and 3. Requests specifying an invalid value will be rejected. Requests for policies with any conditional role bindings must specify version 3. Policies with no conditional role bindings may specify any valid value or leave the field unset. The policy in the response might use the policy version that you specified, or it might use a lower policy version. For example, if you specify version 3, but the policy has no conditional role bindings, the response uses version 1. To learn which resources support conditions in their IAM policies, see the [IAM documentation](https://cloud.google.com/iam/help/conditions/resource-policies).<br>Format: int32 */
						def withOptionsRequestedPolicyVersion(optionsRequestedPolicyVersion: Int) = new getIamPolicy(req.addQueryStringParameters("options.requestedPolicyVersion" -> optionsRequestedPolicyVersion.toString))
						def apply() = req.execute("GET").map(_.json.as[Schema.Policy])
					}
					object getIamPolicy {
						def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, hl7V2StoresId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): getIamPolicy = new getIamPolicy(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/hl7V2Stores/${hl7V2StoresId}:getIamPolicy")).addQueryStringParameters("resource" -> resource.toString))
						given Conversion[getIamPolicy, Future[Schema.Policy]] = (fun: getIamPolicy) => fun.apply()
					}
					class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
						def apply() = req.execute("DELETE").map(_.json.as[Schema.Empty])
					}
					object delete {
						def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, hl7V2StoresId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/hl7V2Stores/${hl7V2StoresId}")).addQueryStringParameters("name" -> name.toString))
						given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
					}
					class getHL7v2StoreMetrics(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Hl7V2StoreMetrics]) {
						def apply() = req.execute("GET").map(_.json.as[Schema.Hl7V2StoreMetrics])
					}
					object getHL7v2StoreMetrics {
						def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, hl7V2StoresId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): getHL7v2StoreMetrics = new getHL7v2StoreMetrics(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/hl7V2Stores/${hl7V2StoresId}:getHL7v2StoreMetrics")).addQueryStringParameters("name" -> name.toString))
						given Conversion[getHL7v2StoreMetrics, Future[Schema.Hl7V2StoreMetrics]] = (fun: getHL7v2StoreMetrics) => fun.apply()
					}
					class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Hl7V2Store]) {
						def apply() = req.execute("GET").map(_.json.as[Schema.Hl7V2Store])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, hl7V2StoresId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/hl7V2Stores/${hl7V2StoresId}")).addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.Hl7V2Store]] = (fun: get) => fun.apply()
					}
					class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withHl7V2Store(body: Schema.Hl7V2Store) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.Hl7V2Store])
					}
					object patch {
						def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, hl7V2StoresId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/hl7V2Stores/${hl7V2StoresId}")).addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
					}
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListHl7V2StoresResponse]) {
						def apply() = req.execute("GET").map(_.json.as[Schema.ListHl7V2StoresResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, parent: String, pageSize: Int, pageToken: String, filter: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/hl7V2Stores")).addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "filter" -> filter.toString))
						given Conversion[list, Future[Schema.ListHl7V2StoresResponse]] = (fun: list) => fun.apply()
					}
					class testIamPermissions(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withTestIamPermissionsRequest(body: Schema.TestIamPermissionsRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.TestIamPermissionsResponse])
					}
					object testIamPermissions {
						def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, hl7V2StoresId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): testIamPermissions = new testIamPermissions(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/hl7V2Stores/${hl7V2StoresId}:testIamPermissions")).addQueryStringParameters("resource" -> resource.toString))
					}
					class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withHl7V2Store(body: Schema.Hl7V2Store) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Hl7V2Store])
					}
					object create {
						def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, parent: String, hl7V2StoreId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/hl7V2Stores")).addQueryStringParameters("parent" -> parent.toString, "hl7V2StoreId" -> hl7V2StoreId.toString))
					}
					class setIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withSetIamPolicyRequest(body: Schema.SetIamPolicyRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Policy])
					}
					object setIamPolicy {
						def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, hl7V2StoresId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): setIamPolicy = new setIamPolicy(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/hl7V2Stores/${hl7V2StoresId}:setIamPolicy")).addQueryStringParameters("resource" -> resource.toString))
					}
					object messages {
						class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
							def withCreateMessageRequest(body: Schema.CreateMessageRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Message])
						}
						object create {
							def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, hl7V2StoresId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/hl7V2Stores/${hl7V2StoresId}/messages")).addQueryStringParameters("parent" -> parent.toString))
						}
						class ingest(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
							def withIngestMessageRequest(body: Schema.IngestMessageRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.IngestMessageResponse])
						}
						object ingest {
							def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, hl7V2StoresId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): ingest = new ingest(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/hl7V2Stores/${hl7V2StoresId}/messages:ingest")).addQueryStringParameters("parent" -> parent.toString))
						}
						class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
							def apply() = req.execute("DELETE").map(_.json.as[Schema.Empty])
						}
						object delete {
							def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, hl7V2StoresId :PlayApi, messagesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/hl7V2Stores/${hl7V2StoresId}/messages/${messagesId}")).addQueryStringParameters("name" -> name.toString))
							given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
						}
						class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Message]) {
							def apply() = req.execute("GET").map(_.json.as[Schema.Message])
						}
						object get {
							def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, hl7V2StoresId :PlayApi, messagesId :PlayApi, name: String, view: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/hl7V2Stores/${hl7V2StoresId}/messages/${messagesId}")).addQueryStringParameters("name" -> name.toString, "view" -> view.toString))
							given Conversion[get, Future[Schema.Message]] = (fun: get) => fun.apply()
						}
						class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
							def withMessage(body: Schema.Message) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.Message])
						}
						object patch {
							def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, hl7V2StoresId :PlayApi, messagesId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/hl7V2Stores/${hl7V2StoresId}/messages/${messagesId}")).addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
						}
						class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListMessagesResponse]) {
							def apply() = req.execute("GET").map(_.json.as[Schema.ListMessagesResponse])
						}
						object list {
							def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, hl7V2StoresId :PlayApi, parent: String, pageSize: Int, pageToken: String, filter: String, orderBy: String, view: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/hl7V2Stores/${hl7V2StoresId}/messages")).addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "filter" -> filter.toString, "orderBy" -> orderBy.toString, "view" -> view.toString))
							given Conversion[list, Future[Schema.ListMessagesResponse]] = (fun: list) => fun.apply()
						}
					}
				}
				object operations {
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListOperationsResponse]) {
						def apply() = req.execute("GET").map(_.json.as[Schema.ListOperationsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, name: String, filter: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/operations")).addQueryStringParameters("name" -> name.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
						given Conversion[list, Future[Schema.ListOperationsResponse]] = (fun: list) => fun.apply()
					}
					class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
						def apply() = req.execute("GET").map(_.json.as[Schema.Operation])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, operationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/operations/${operationsId}")).addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.Operation]] = (fun: get) => fun.apply()
					}
					class cancel(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withCancelOperationRequest(body: Schema.CancelOperationRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Empty])
					}
					object cancel {
						def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, operationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): cancel = new cancel(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/operations/${operationsId}:cancel")).addQueryStringParameters("name" -> name.toString))
					}
				}
				object dicomStores {
					class testIamPermissions(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withTestIamPermissionsRequest(body: Schema.TestIamPermissionsRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.TestIamPermissionsResponse])
					}
					object testIamPermissions {
						def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, dicomStoresId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): testIamPermissions = new testIamPermissions(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/dicomStores/${dicomStoresId}:testIamPermissions")).addQueryStringParameters("resource" -> resource.toString))
					}
					class deidentify(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withDeidentifyDicomStoreRequest(body: Schema.DeidentifyDicomStoreRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Operation])
					}
					object deidentify {
						def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, dicomStoresId :PlayApi, sourceStore: String)(using auth: AuthToken, ec: ExecutionContext): deidentify = new deidentify(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/dicomStores/${dicomStoresId}:deidentify")).addQueryStringParameters("sourceStore" -> sourceStore.toString))
					}
					class `export`(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withExportDicomDataRequest(body: Schema.ExportDicomDataRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Operation])
					}
					object `export` {
						def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, dicomStoresId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): `export` = new `export`(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/dicomStores/${dicomStoresId}:export")).addQueryStringParameters("name" -> name.toString))
					}
					class `import`(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withImportDicomDataRequest(body: Schema.ImportDicomDataRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Operation])
					}
					object `import` {
						def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, dicomStoresId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): `import` = new `import`(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/dicomStores/${dicomStoresId}:import")).addQueryStringParameters("name" -> name.toString))
					}
					class getIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Policy]) {
						/** Optional. The maximum policy version that will be used to format the policy. Valid values are 0, 1, and 3. Requests specifying an invalid value will be rejected. Requests for policies with any conditional role bindings must specify version 3. Policies with no conditional role bindings may specify any valid value or leave the field unset. The policy in the response might use the policy version that you specified, or it might use a lower policy version. For example, if you specify version 3, but the policy has no conditional role bindings, the response uses version 1. To learn which resources support conditions in their IAM policies, see the [IAM documentation](https://cloud.google.com/iam/help/conditions/resource-policies).<br>Format: int32 */
						def withOptionsRequestedPolicyVersion(optionsRequestedPolicyVersion: Int) = new getIamPolicy(req.addQueryStringParameters("options.requestedPolicyVersion" -> optionsRequestedPolicyVersion.toString))
						def apply() = req.execute("GET").map(_.json.as[Schema.Policy])
					}
					object getIamPolicy {
						def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, dicomStoresId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): getIamPolicy = new getIamPolicy(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/dicomStores/${dicomStoresId}:getIamPolicy")).addQueryStringParameters("resource" -> resource.toString))
						given Conversion[getIamPolicy, Future[Schema.Policy]] = (fun: getIamPolicy) => fun.apply()
					}
					class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
						def apply() = req.execute("DELETE").map(_.json.as[Schema.Empty])
					}
					object delete {
						def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, dicomStoresId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/dicomStores/${dicomStoresId}")).addQueryStringParameters("name" -> name.toString))
						given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
					}
					class searchForStudies(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.HttpBody]) {
						def apply() = req.execute("GET").map(_.json.as[Schema.HttpBody])
					}
					object searchForStudies {
						def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, dicomStoresId :PlayApi, parent: String, dicomWebPath: String)(using auth: AuthToken, ec: ExecutionContext): searchForStudies = new searchForStudies(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/dicomStores/${dicomStoresId}/dicomWeb/studies")).addQueryStringParameters("parent" -> parent.toString, "dicomWebPath" -> dicomWebPath.toString))
						given Conversion[searchForStudies, Future[Schema.HttpBody]] = (fun: searchForStudies) => fun.apply()
					}
					class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.DicomStore]) {
						def apply() = req.execute("GET").map(_.json.as[Schema.DicomStore])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, dicomStoresId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/dicomStores/${dicomStoresId}")).addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.DicomStore]] = (fun: get) => fun.apply()
					}
					class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withDicomStore(body: Schema.DicomStore) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.DicomStore])
					}
					object patch {
						def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, dicomStoresId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/dicomStores/${dicomStoresId}")).addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
					}
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListDicomStoresResponse]) {
						def apply() = req.execute("GET").map(_.json.as[Schema.ListDicomStoresResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, parent: String, pageSize: Int, pageToken: String, filter: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/dicomStores")).addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "filter" -> filter.toString))
						given Conversion[list, Future[Schema.ListDicomStoresResponse]] = (fun: list) => fun.apply()
					}
					class setBlobStorageSettings(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withSetBlobStorageSettingsRequest(body: Schema.SetBlobStorageSettingsRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Operation])
					}
					object setBlobStorageSettings {
						def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, dicomStoresId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): setBlobStorageSettings = new setBlobStorageSettings(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/dicomStores/${dicomStoresId}:setBlobStorageSettings")).addQueryStringParameters("resource" -> resource.toString))
					}
					class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withDicomStore(body: Schema.DicomStore) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.DicomStore])
					}
					object create {
						def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, parent: String, dicomStoreId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/dicomStores")).addQueryStringParameters("parent" -> parent.toString, "dicomStoreId" -> dicomStoreId.toString))
					}
					class setIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withSetIamPolicyRequest(body: Schema.SetIamPolicyRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Policy])
					}
					object setIamPolicy {
						def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, dicomStoresId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): setIamPolicy = new setIamPolicy(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/dicomStores/${dicomStoresId}:setIamPolicy")).addQueryStringParameters("resource" -> resource.toString))
					}
					class searchForInstances(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.HttpBody]) {
						def apply() = req.execute("GET").map(_.json.as[Schema.HttpBody])
					}
					object searchForInstances {
						def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, dicomStoresId :PlayApi, parent: String, dicomWebPath: String)(using auth: AuthToken, ec: ExecutionContext): searchForInstances = new searchForInstances(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/dicomStores/${dicomStoresId}/dicomWeb/instances")).addQueryStringParameters("parent" -> parent.toString, "dicomWebPath" -> dicomWebPath.toString))
						given Conversion[searchForInstances, Future[Schema.HttpBody]] = (fun: searchForInstances) => fun.apply()
					}
					class searchForSeries(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.HttpBody]) {
						def apply() = req.execute("GET").map(_.json.as[Schema.HttpBody])
					}
					object searchForSeries {
						def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, dicomStoresId :PlayApi, parent: String, dicomWebPath: String)(using auth: AuthToken, ec: ExecutionContext): searchForSeries = new searchForSeries(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/dicomStores/${dicomStoresId}/dicomWeb/series")).addQueryStringParameters("parent" -> parent.toString, "dicomWebPath" -> dicomWebPath.toString))
						given Conversion[searchForSeries, Future[Schema.HttpBody]] = (fun: searchForSeries) => fun.apply()
					}
					class getDICOMStoreMetrics(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.DicomStoreMetrics]) {
						def apply() = req.execute("GET").map(_.json.as[Schema.DicomStoreMetrics])
					}
					object getDICOMStoreMetrics {
						def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, dicomStoresId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): getDICOMStoreMetrics = new getDICOMStoreMetrics(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/dicomStores/${dicomStoresId}:getDICOMStoreMetrics")).addQueryStringParameters("name" -> name.toString))
						given Conversion[getDICOMStoreMetrics, Future[Schema.DicomStoreMetrics]] = (fun: getDICOMStoreMetrics) => fun.apply()
					}
					class storeInstances(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						/** Required. The path of the StoreInstances DICOMweb request. For example, `studies/[{study_uid}]`. Note that the `study_uid` is optional. */
						def withDicomWebPath(dicomWebPath: String) = new storeInstances(req.addQueryStringParameters("dicomWebPath" -> dicomWebPath.toString))
						def withHttpBody(body: Schema.HttpBody) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.HttpBody])
					}
					object storeInstances {
						def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, dicomStoresId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): storeInstances = new storeInstances(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/dicomStores/${dicomStoresId}/dicomWeb/studies")).addQueryStringParameters("parent" -> parent.toString))
					}
					object dicomWeb {
						object studies {
							class setBlobStorageSettings(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
								def withSetBlobStorageSettingsRequest(body: Schema.SetBlobStorageSettingsRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Operation])
							}
							object setBlobStorageSettings {
								def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, dicomStoresId :PlayApi, studiesId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): setBlobStorageSettings = new setBlobStorageSettings(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/dicomStores/${dicomStoresId}/dicomWeb/studies/${studiesId}:setBlobStorageSettings")).addQueryStringParameters("resource" -> resource.toString))
							}
							class getStudyMetrics(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.StudyMetrics]) {
								def apply() = req.execute("GET").map(_.json.as[Schema.StudyMetrics])
							}
							object getStudyMetrics {
								def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, dicomStoresId :PlayApi, studiesId :PlayApi, study: String)(using auth: AuthToken, ec: ExecutionContext): getStudyMetrics = new getStudyMetrics(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/dicomStores/${dicomStoresId}/dicomWeb/studies/${studiesId}:getStudyMetrics")).addQueryStringParameters("study" -> study.toString))
								given Conversion[getStudyMetrics, Future[Schema.StudyMetrics]] = (fun: getStudyMetrics) => fun.apply()
							}
							object series {
								class getSeriesMetrics(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.SeriesMetrics]) {
									def apply() = req.execute("GET").map(_.json.as[Schema.SeriesMetrics])
								}
								object getSeriesMetrics {
									def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, dicomStoresId :PlayApi, studiesId :PlayApi, seriesId :PlayApi, series: String)(using auth: AuthToken, ec: ExecutionContext): getSeriesMetrics = new getSeriesMetrics(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/dicomStores/${dicomStoresId}/dicomWeb/studies/${studiesId}/series/${seriesId}:getSeriesMetrics")).addQueryStringParameters("series" -> series.toString))
									given Conversion[getSeriesMetrics, Future[Schema.SeriesMetrics]] = (fun: getSeriesMetrics) => fun.apply()
								}
								object instances {
									class getStorageInfo(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.StorageInfo]) {
										def apply() = req.execute("GET").map(_.json.as[Schema.StorageInfo])
									}
									object getStorageInfo {
										def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, dicomStoresId :PlayApi, studiesId :PlayApi, seriesId :PlayApi, instancesId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): getStorageInfo = new getStorageInfo(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/dicomStores/${dicomStoresId}/dicomWeb/studies/${studiesId}/series/${seriesId}/instances/${instancesId}:getStorageInfo")).addQueryStringParameters("resource" -> resource.toString))
										given Conversion[getStorageInfo, Future[Schema.StorageInfo]] = (fun: getStorageInfo) => fun.apply()
									}
								}
							}
						}
					}
					object studies {
						class retrieveStudy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.HttpBody]) {
							def apply() = req.execute("GET").map(_.json.as[Schema.HttpBody])
						}
						object retrieveStudy {
							def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, dicomStoresId :PlayApi, studiesId :PlayApi, parent: String, dicomWebPath: String)(using auth: AuthToken, ec: ExecutionContext): retrieveStudy = new retrieveStudy(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/dicomStores/${dicomStoresId}/dicomWeb/studies/${studiesId}")).addQueryStringParameters("parent" -> parent.toString, "dicomWebPath" -> dicomWebPath.toString))
							given Conversion[retrieveStudy, Future[Schema.HttpBody]] = (fun: retrieveStudy) => fun.apply()
						}
						class storeInstances(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
							/** Required. The path of the StoreInstances DICOMweb request. For example, `studies/[{study_uid}]`. Note that the `study_uid` is optional. */
							def withDicomWebPath(dicomWebPath: String) = new storeInstances(req.addQueryStringParameters("dicomWebPath" -> dicomWebPath.toString))
							def withHttpBody(body: Schema.HttpBody) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.HttpBody])
						}
						object storeInstances {
							def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, dicomStoresId :PlayApi, studiesId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): storeInstances = new storeInstances(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/dicomStores/${dicomStoresId}/dicomWeb/studies/${studiesId}")).addQueryStringParameters("parent" -> parent.toString))
						}
						class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
							def apply() = req.execute("DELETE").map(_.json.as[Schema.Operation])
						}
						object delete {
							def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, dicomStoresId :PlayApi, studiesId :PlayApi, parent: String, dicomWebPath: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/dicomStores/${dicomStoresId}/dicomWeb/studies/${studiesId}")).addQueryStringParameters("parent" -> parent.toString, "dicomWebPath" -> dicomWebPath.toString))
							given Conversion[delete, Future[Schema.Operation]] = (fun: delete) => fun.apply()
						}
						class retrieveMetadata(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.HttpBody]) {
							def apply() = req.execute("GET").map(_.json.as[Schema.HttpBody])
						}
						object retrieveMetadata {
							def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, dicomStoresId :PlayApi, studiesId :PlayApi, parent: String, dicomWebPath: String)(using auth: AuthToken, ec: ExecutionContext): retrieveMetadata = new retrieveMetadata(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/dicomStores/${dicomStoresId}/dicomWeb/studies/${studiesId}/metadata")).addQueryStringParameters("parent" -> parent.toString, "dicomWebPath" -> dicomWebPath.toString))
							given Conversion[retrieveMetadata, Future[Schema.HttpBody]] = (fun: retrieveMetadata) => fun.apply()
						}
						class searchForInstances(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.HttpBody]) {
							def apply() = req.execute("GET").map(_.json.as[Schema.HttpBody])
						}
						object searchForInstances {
							def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, dicomStoresId :PlayApi, studiesId :PlayApi, parent: String, dicomWebPath: String)(using auth: AuthToken, ec: ExecutionContext): searchForInstances = new searchForInstances(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/dicomStores/${dicomStoresId}/dicomWeb/studies/${studiesId}/instances")).addQueryStringParameters("parent" -> parent.toString, "dicomWebPath" -> dicomWebPath.toString))
							given Conversion[searchForInstances, Future[Schema.HttpBody]] = (fun: searchForInstances) => fun.apply()
						}
						class searchForSeries(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.HttpBody]) {
							def apply() = req.execute("GET").map(_.json.as[Schema.HttpBody])
						}
						object searchForSeries {
							def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, dicomStoresId :PlayApi, studiesId :PlayApi, parent: String, dicomWebPath: String)(using auth: AuthToken, ec: ExecutionContext): searchForSeries = new searchForSeries(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/dicomStores/${dicomStoresId}/dicomWeb/studies/${studiesId}/series")).addQueryStringParameters("parent" -> parent.toString, "dicomWebPath" -> dicomWebPath.toString))
							given Conversion[searchForSeries, Future[Schema.HttpBody]] = (fun: searchForSeries) => fun.apply()
						}
						object series {
							class searchForInstances(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.HttpBody]) {
								def apply() = req.execute("GET").map(_.json.as[Schema.HttpBody])
							}
							object searchForInstances {
								def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, dicomStoresId :PlayApi, studiesId :PlayApi, seriesId :PlayApi, parent: String, dicomWebPath: String)(using auth: AuthToken, ec: ExecutionContext): searchForInstances = new searchForInstances(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/dicomStores/${dicomStoresId}/dicomWeb/studies/${studiesId}/series/${seriesId}/instances")).addQueryStringParameters("parent" -> parent.toString, "dicomWebPath" -> dicomWebPath.toString))
								given Conversion[searchForInstances, Future[Schema.HttpBody]] = (fun: searchForInstances) => fun.apply()
							}
							class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
								def apply() = req.execute("DELETE").map(_.json.as[Schema.Operation])
							}
							object delete {
								def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, dicomStoresId :PlayApi, studiesId :PlayApi, seriesId :PlayApi, parent: String, dicomWebPath: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/dicomStores/${dicomStoresId}/dicomWeb/studies/${studiesId}/series/${seriesId}")).addQueryStringParameters("parent" -> parent.toString, "dicomWebPath" -> dicomWebPath.toString))
								given Conversion[delete, Future[Schema.Operation]] = (fun: delete) => fun.apply()
							}
							class retrieveSeries(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.HttpBody]) {
								def apply() = req.execute("GET").map(_.json.as[Schema.HttpBody])
							}
							object retrieveSeries {
								def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, dicomStoresId :PlayApi, studiesId :PlayApi, seriesId :PlayApi, parent: String, dicomWebPath: String)(using auth: AuthToken, ec: ExecutionContext): retrieveSeries = new retrieveSeries(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/dicomStores/${dicomStoresId}/dicomWeb/studies/${studiesId}/series/${seriesId}")).addQueryStringParameters("parent" -> parent.toString, "dicomWebPath" -> dicomWebPath.toString))
								given Conversion[retrieveSeries, Future[Schema.HttpBody]] = (fun: retrieveSeries) => fun.apply()
							}
							class retrieveMetadata(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.HttpBody]) {
								def apply() = req.execute("GET").map(_.json.as[Schema.HttpBody])
							}
							object retrieveMetadata {
								def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, dicomStoresId :PlayApi, studiesId :PlayApi, seriesId :PlayApi, parent: String, dicomWebPath: String)(using auth: AuthToken, ec: ExecutionContext): retrieveMetadata = new retrieveMetadata(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/dicomStores/${dicomStoresId}/dicomWeb/studies/${studiesId}/series/${seriesId}/metadata")).addQueryStringParameters("parent" -> parent.toString, "dicomWebPath" -> dicomWebPath.toString))
								given Conversion[retrieveMetadata, Future[Schema.HttpBody]] = (fun: retrieveMetadata) => fun.apply()
							}
							object instances {
								class retrieveRendered(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.HttpBody]) {
									def apply() = req.execute("GET").map(_.json.as[Schema.HttpBody])
								}
								object retrieveRendered {
									def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, dicomStoresId :PlayApi, studiesId :PlayApi, seriesId :PlayApi, instancesId :PlayApi, parent: String, dicomWebPath: String)(using auth: AuthToken, ec: ExecutionContext): retrieveRendered = new retrieveRendered(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/dicomStores/${dicomStoresId}/dicomWeb/studies/${studiesId}/series/${seriesId}/instances/${instancesId}/rendered")).addQueryStringParameters("parent" -> parent.toString, "dicomWebPath" -> dicomWebPath.toString))
									given Conversion[retrieveRendered, Future[Schema.HttpBody]] = (fun: retrieveRendered) => fun.apply()
								}
								class retrieveInstance(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.HttpBody]) {
									def apply() = req.execute("GET").map(_.json.as[Schema.HttpBody])
								}
								object retrieveInstance {
									def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, dicomStoresId :PlayApi, studiesId :PlayApi, seriesId :PlayApi, instancesId :PlayApi, parent: String, dicomWebPath: String)(using auth: AuthToken, ec: ExecutionContext): retrieveInstance = new retrieveInstance(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/dicomStores/${dicomStoresId}/dicomWeb/studies/${studiesId}/series/${seriesId}/instances/${instancesId}")).addQueryStringParameters("parent" -> parent.toString, "dicomWebPath" -> dicomWebPath.toString))
									given Conversion[retrieveInstance, Future[Schema.HttpBody]] = (fun: retrieveInstance) => fun.apply()
								}
								class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
									def apply() = req.execute("DELETE").map(_.json.as[Schema.Empty])
								}
								object delete {
									def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, dicomStoresId :PlayApi, studiesId :PlayApi, seriesId :PlayApi, instancesId :PlayApi, parent: String, dicomWebPath: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/dicomStores/${dicomStoresId}/dicomWeb/studies/${studiesId}/series/${seriesId}/instances/${instancesId}")).addQueryStringParameters("parent" -> parent.toString, "dicomWebPath" -> dicomWebPath.toString))
									given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
								}
								class retrieveMetadata(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.HttpBody]) {
									def apply() = req.execute("GET").map(_.json.as[Schema.HttpBody])
								}
								object retrieveMetadata {
									def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, dicomStoresId :PlayApi, studiesId :PlayApi, seriesId :PlayApi, instancesId :PlayApi, parent: String, dicomWebPath: String)(using auth: AuthToken, ec: ExecutionContext): retrieveMetadata = new retrieveMetadata(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/dicomStores/${dicomStoresId}/dicomWeb/studies/${studiesId}/series/${seriesId}/instances/${instancesId}/metadata")).addQueryStringParameters("parent" -> parent.toString, "dicomWebPath" -> dicomWebPath.toString))
									given Conversion[retrieveMetadata, Future[Schema.HttpBody]] = (fun: retrieveMetadata) => fun.apply()
								}
								object frames {
									class retrieveFrames(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.HttpBody]) {
										def apply() = req.execute("GET").map(_.json.as[Schema.HttpBody])
									}
									object retrieveFrames {
										def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, dicomStoresId :PlayApi, studiesId :PlayApi, seriesId :PlayApi, instancesId :PlayApi, framesId :PlayApi, parent: String, dicomWebPath: String)(using auth: AuthToken, ec: ExecutionContext): retrieveFrames = new retrieveFrames(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/dicomStores/${dicomStoresId}/dicomWeb/studies/${studiesId}/series/${seriesId}/instances/${instancesId}/frames/${framesId}")).addQueryStringParameters("parent" -> parent.toString, "dicomWebPath" -> dicomWebPath.toString))
										given Conversion[retrieveFrames, Future[Schema.HttpBody]] = (fun: retrieveFrames) => fun.apply()
									}
									class retrieveRendered(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.HttpBody]) {
										def apply() = req.execute("GET").map(_.json.as[Schema.HttpBody])
									}
									object retrieveRendered {
										def apply(projectsId :PlayApi, locationsId :PlayApi, datasetsId :PlayApi, dicomStoresId :PlayApi, studiesId :PlayApi, seriesId :PlayApi, instancesId :PlayApi, framesId :PlayApi, parent: String, dicomWebPath: String)(using auth: AuthToken, ec: ExecutionContext): retrieveRendered = new retrieveRendered(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/datasets/${datasetsId}/dicomStores/${dicomStoresId}/dicomWeb/studies/${studiesId}/series/${seriesId}/instances/${instancesId}/frames/${framesId}/rendered")).addQueryStringParameters("parent" -> parent.toString, "dicomWebPath" -> dicomWebPath.toString))
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
					class analyzeEntities(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withAnalyzeEntitiesRequest(body: Schema.AnalyzeEntitiesRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.AnalyzeEntitiesResponse])
					}
					object analyzeEntities {
						def apply(projectsId :PlayApi, locationsId :PlayApi, nlpService: String)(using auth: AuthToken, ec: ExecutionContext): analyzeEntities = new analyzeEntities(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/services/nlp:analyzeEntities")).addQueryStringParameters("nlpService" -> nlpService.toString))
					}
				}
			}
		}
	}
}
