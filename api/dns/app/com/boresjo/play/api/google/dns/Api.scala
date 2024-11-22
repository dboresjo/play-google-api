package com.boresjo.play.api.google.dns

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://dns.googleapis.com/"

	object resourceRecordSets {
		class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			/** For mutating operation requests only. An optional identifier specified by the client. Must be unique for operation resources in the Operations collection. */
			def withClientOperationId(clientOperationId: String) = new create(req.addQueryStringParameters("clientOperationId" -> clientOperationId.toString))
			def withResourceRecordSet(body: Schema.ResourceRecordSet) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.ResourceRecordSet])
		}
		object create {
			def apply(project: String, managedZone: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"dns/v1/projects/${project}/managedZones/${managedZone}/rrsets").addQueryStringParameters())
		}
		class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ResourceRecordSetsDeleteResponse]) {
			/** For mutating operation requests only. An optional identifier specified by the client. Must be unique for operation resources in the Operations collection. */
			def withClientOperationId(clientOperationId: String) = new delete(req.addQueryStringParameters("clientOperationId" -> clientOperationId.toString))
			def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.ResourceRecordSetsDeleteResponse])
		}
		object delete {
			def apply(project: String, managedZone: String, name: String, `type`: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"dns/v1/projects/${project}/managedZones/${managedZone}/rrsets/${name}/${`type`}").addQueryStringParameters())
			given Conversion[delete, Future[Schema.ResourceRecordSetsDeleteResponse]] = (fun: delete) => fun.apply()
		}
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ResourceRecordSet]) {
			/** For mutating operation requests only. An optional identifier specified by the client. Must be unique for operation resources in the Operations collection. */
			def withClientOperationId(clientOperationId: String) = new get(req.addQueryStringParameters("clientOperationId" -> clientOperationId.toString))
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ResourceRecordSet])
		}
		object get {
			def apply(project: String, managedZone: String, name: String, `type`: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"dns/v1/projects/${project}/managedZones/${managedZone}/rrsets/${name}/${`type`}").addQueryStringParameters())
			given Conversion[get, Future[Schema.ResourceRecordSet]] = (fun: get) => fun.apply()
		}
		class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			/** For mutating operation requests only. An optional identifier specified by the client. Must be unique for operation resources in the Operations collection. */
			def withClientOperationId(clientOperationId: String) = new patch(req.addQueryStringParameters("clientOperationId" -> clientOperationId.toString))
			def withResourceRecordSet(body: Schema.ResourceRecordSet) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.ResourceRecordSet])
		}
		object patch {
			def apply(project: String, managedZone: String, name: String, `type`: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"dns/v1/projects/${project}/managedZones/${managedZone}/rrsets/${name}/${`type`}").addQueryStringParameters())
		}
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ResourceRecordSetsListResponse]) {
			/** Optional. Maximum number of results to be returned. If unspecified, the server decides how many results to return.<br>Format: int32 */
			def withMaxResults(maxResults: Int) = new list(req.addQueryStringParameters("maxResults" -> maxResults.toString))
			/** Optional. A tag returned by a previous list request that was truncated. Use this parameter to continue a previous list request. */
			def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ResourceRecordSetsListResponse])
		}
		object list {
			def apply(project: String, managedZone: String, name: String, `type`: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"dns/v1/projects/${project}/managedZones/${managedZone}/rrsets").addQueryStringParameters("name" -> name.toString, "type" -> `type`.toString))
			given Conversion[list, Future[Schema.ResourceRecordSetsListResponse]] = (fun: list) => fun.apply()
		}
	}
	object changes {
		class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			/** For mutating operation requests only. An optional identifier specified by the client. Must be unique for operation resources in the Operations collection. */
			def withClientOperationId(clientOperationId: String) = new create(req.addQueryStringParameters("clientOperationId" -> clientOperationId.toString))
			def withChange(body: Schema.Change) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Change])
		}
		object create {
			def apply(project: String, managedZone: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"dns/v1/projects/${project}/managedZones/${managedZone}/changes").addQueryStringParameters())
		}
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Change]) {
			/** For mutating operation requests only. An optional identifier specified by the client. Must be unique for operation resources in the Operations collection. */
			def withClientOperationId(clientOperationId: String) = new get(req.addQueryStringParameters("clientOperationId" -> clientOperationId.toString))
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Change])
		}
		object get {
			def apply(project: String, managedZone: String, changeId: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"dns/v1/projects/${project}/managedZones/${managedZone}/changes/${changeId}").addQueryStringParameters())
			given Conversion[get, Future[Schema.Change]] = (fun: get) => fun.apply()
		}
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ChangesListResponse]) {
			/** Optional. Maximum number of results to be returned. If unspecified, the server decides how many results to return.<br>Format: int32 */
			def withMaxResults(maxResults: Int) = new list(req.addQueryStringParameters("maxResults" -> maxResults.toString))
			/** Optional. A tag returned by a previous list request that was truncated. Use this parameter to continue a previous list request. */
			def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ChangesListResponse])
		}
		object list {
			def apply(project: String, managedZone: String, sortBy: String, sortOrder: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"dns/v1/projects/${project}/managedZones/${managedZone}/changes").addQueryStringParameters("sortBy" -> sortBy.toString, "sortOrder" -> sortOrder.toString))
			given Conversion[list, Future[Schema.ChangesListResponse]] = (fun: list) => fun.apply()
		}
	}
	object dnsKeys {
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.DnsKey]) {
			/** For mutating operation requests only. An optional identifier specified by the client. Must be unique for operation resources in the Operations collection. */
			def withClientOperationId(clientOperationId: String) = new get(req.addQueryStringParameters("clientOperationId" -> clientOperationId.toString))
			/** An optional comma-separated list of digest types to compute and display for key signing keys. If omitted, the recommended digest type is computed and displayed. */
			def withDigestType(digestType: String) = new get(req.addQueryStringParameters("digestType" -> digestType.toString))
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.DnsKey])
		}
		object get {
			def apply(project: String, managedZone: String, dnsKeyId: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"dns/v1/projects/${project}/managedZones/${managedZone}/dnsKeys/${dnsKeyId}").addQueryStringParameters())
			given Conversion[get, Future[Schema.DnsKey]] = (fun: get) => fun.apply()
		}
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.DnsKeysListResponse]) {
			/** Optional. Maximum number of results to be returned. If unspecified, the server decides how many results to return.<br>Format: int32 */
			def withMaxResults(maxResults: Int) = new list(req.addQueryStringParameters("maxResults" -> maxResults.toString))
			/** Optional. A tag returned by a previous list request that was truncated. Use this parameter to continue a previous list request. */
			def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
			/** An optional comma-separated list of digest types to compute and display for key signing keys. If omitted, the recommended digest type is computed and displayed. */
			def withDigestType(digestType: String) = new list(req.addQueryStringParameters("digestType" -> digestType.toString))
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.DnsKeysListResponse])
		}
		object list {
			def apply(project: String, managedZone: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"dns/v1/projects/${project}/managedZones/${managedZone}/dnsKeys").addQueryStringParameters())
			given Conversion[list, Future[Schema.DnsKeysListResponse]] = (fun: list) => fun.apply()
		}
	}
	object projects {
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Project]) {
			/** For mutating operation requests only. An optional identifier specified by the client. Must be unique for operation resources in the Operations collection. */
			def withClientOperationId(clientOperationId: String) = new get(req.addQueryStringParameters("clientOperationId" -> clientOperationId.toString))
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Project])
		}
		object get {
			def apply(project: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"dns/v1/projects/${project}").addQueryStringParameters())
			given Conversion[get, Future[Schema.Project]] = (fun: get) => fun.apply()
		}
	}
	object managedZoneOperations {
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
			/** For mutating operation requests only. An optional identifier specified by the client. Must be unique for operation resources in the Operations collection. */
			def withClientOperationId(clientOperationId: String) = new get(req.addQueryStringParameters("clientOperationId" -> clientOperationId.toString))
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Operation])
		}
		object get {
			def apply(project: String, managedZone: String, operation: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"dns/v1/projects/${project}/managedZones/${managedZone}/operations/${operation}").addQueryStringParameters())
			given Conversion[get, Future[Schema.Operation]] = (fun: get) => fun.apply()
		}
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ManagedZoneOperationsListResponse]) {
			/** Optional. Maximum number of results to be returned. If unspecified, the server decides how many results to return.<br>Format: int32 */
			def withMaxResults(maxResults: Int) = new list(req.addQueryStringParameters("maxResults" -> maxResults.toString))
			/** Optional. A tag returned by a previous list request that was truncated. Use this parameter to continue a previous list request. */
			def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ManagedZoneOperationsListResponse])
		}
		object list {
			def apply(project: String, managedZone: String, sortBy: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"dns/v1/projects/${project}/managedZones/${managedZone}/operations").addQueryStringParameters("sortBy" -> sortBy.toString))
			given Conversion[list, Future[Schema.ManagedZoneOperationsListResponse]] = (fun: list) => fun.apply()
		}
	}
	object managedZones {
		class testIamPermissions(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withGoogleIamV1TestIamPermissionsRequest(body: Schema.GoogleIamV1TestIamPermissionsRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleIamV1TestIamPermissionsResponse])
		}
		object testIamPermissions {
			def apply(projectsId :PlayApi, managedZonesId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): testIamPermissions = new testIamPermissions(ws.url(BASE_URL + s"dns/v1/projects/${projectsId}/managedZones/${managedZonesId}:testIamPermissions").addQueryStringParameters("resource" -> resource.toString))
		}
		class getIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withGoogleIamV1GetIamPolicyRequest(body: Schema.GoogleIamV1GetIamPolicyRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleIamV1Policy])
		}
		object getIamPolicy {
			def apply(projectsId :PlayApi, managedZonesId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): getIamPolicy = new getIamPolicy(ws.url(BASE_URL + s"dns/v1/projects/${projectsId}/managedZones/${managedZonesId}:getIamPolicy").addQueryStringParameters("resource" -> resource.toString))
		}
		class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Unit]) {
			/** For mutating operation requests only. An optional identifier specified by the client. Must be unique for operation resources in the Operations collection. */
			def withClientOperationId(clientOperationId: String) = new delete(req.addQueryStringParameters("clientOperationId" -> clientOperationId.toString))
			def apply() = auth.exec(req,_.execute("DELETE")).map(_ => ())
		}
		object delete {
			def apply(project: String, managedZone: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"dns/v1/projects/${project}/managedZones/${managedZone}").addQueryStringParameters())
			given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
		}
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ManagedZone]) {
			/** For mutating operation requests only. An optional identifier specified by the client. Must be unique for operation resources in the Operations collection. */
			def withClientOperationId(clientOperationId: String) = new get(req.addQueryStringParameters("clientOperationId" -> clientOperationId.toString))
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ManagedZone])
		}
		object get {
			def apply(project: String, managedZone: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"dns/v1/projects/${project}/managedZones/${managedZone}").addQueryStringParameters())
			given Conversion[get, Future[Schema.ManagedZone]] = (fun: get) => fun.apply()
		}
		class update(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			/** For mutating operation requests only. An optional identifier specified by the client. Must be unique for operation resources in the Operations collection. */
			def withClientOperationId(clientOperationId: String) = new update(req.addQueryStringParameters("clientOperationId" -> clientOperationId.toString))
			def withManagedZone(body: Schema.ManagedZone) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.Operation])
		}
		object update {
			def apply(project: String, managedZone: String)(using auth: AuthToken, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"dns/v1/projects/${project}/managedZones/${managedZone}").addQueryStringParameters())
		}
		class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			/** For mutating operation requests only. An optional identifier specified by the client. Must be unique for operation resources in the Operations collection. */
			def withClientOperationId(clientOperationId: String) = new patch(req.addQueryStringParameters("clientOperationId" -> clientOperationId.toString))
			def withManagedZone(body: Schema.ManagedZone) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Operation])
		}
		object patch {
			def apply(project: String, managedZone: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"dns/v1/projects/${project}/managedZones/${managedZone}").addQueryStringParameters())
		}
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ManagedZonesListResponse]) {
			/** Optional. Maximum number of results to be returned. If unspecified, the server decides how many results to return.<br>Format: int32 */
			def withMaxResults(maxResults: Int) = new list(req.addQueryStringParameters("maxResults" -> maxResults.toString))
			/** Optional. A tag returned by a previous list request that was truncated. Use this parameter to continue a previous list request. */
			def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ManagedZonesListResponse])
		}
		object list {
			def apply(project: String, dnsName: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"dns/v1/projects/${project}/managedZones").addQueryStringParameters("dnsName" -> dnsName.toString))
			given Conversion[list, Future[Schema.ManagedZonesListResponse]] = (fun: list) => fun.apply()
		}
		class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			/** For mutating operation requests only. An optional identifier specified by the client. Must be unique for operation resources in the Operations collection. */
			def withClientOperationId(clientOperationId: String) = new create(req.addQueryStringParameters("clientOperationId" -> clientOperationId.toString))
			def withManagedZone(body: Schema.ManagedZone) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.ManagedZone])
		}
		object create {
			def apply(project: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"dns/v1/projects/${project}/managedZones").addQueryStringParameters())
		}
		class setIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withGoogleIamV1SetIamPolicyRequest(body: Schema.GoogleIamV1SetIamPolicyRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleIamV1Policy])
		}
		object setIamPolicy {
			def apply(projectsId :PlayApi, managedZonesId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): setIamPolicy = new setIamPolicy(ws.url(BASE_URL + s"dns/v1/projects/${projectsId}/managedZones/${managedZonesId}:setIamPolicy").addQueryStringParameters("resource" -> resource.toString))
		}
	}
	object policies {
		class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			/** For mutating operation requests only. An optional identifier specified by the client. Must be unique for operation resources in the Operations collection. */
			def withClientOperationId(clientOperationId: String) = new create(req.addQueryStringParameters("clientOperationId" -> clientOperationId.toString))
			def withPolicy(body: Schema.Policy) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
		}
		object create {
			def apply(project: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"dns/v1/projects/${project}/policies").addQueryStringParameters())
		}
		class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Unit]) {
			/** For mutating operation requests only. An optional identifier specified by the client. Must be unique for operation resources in the Operations collection. */
			def withClientOperationId(clientOperationId: String) = new delete(req.addQueryStringParameters("clientOperationId" -> clientOperationId.toString))
			def apply() = auth.exec(req,_.execute("DELETE")).map(_ => ())
		}
		object delete {
			def apply(project: String, policy: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"dns/v1/projects/${project}/policies/${policy}").addQueryStringParameters())
			given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
		}
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Policy]) {
			/** For mutating operation requests only. An optional identifier specified by the client. Must be unique for operation resources in the Operations collection. */
			def withClientOperationId(clientOperationId: String) = new get(req.addQueryStringParameters("clientOperationId" -> clientOperationId.toString))
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Policy])
		}
		object get {
			def apply(project: String, policy: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"dns/v1/projects/${project}/policies/${policy}").addQueryStringParameters())
			given Conversion[get, Future[Schema.Policy]] = (fun: get) => fun.apply()
		}
		class update(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			/** For mutating operation requests only. An optional identifier specified by the client. Must be unique for operation resources in the Operations collection. */
			def withClientOperationId(clientOperationId: String) = new update(req.addQueryStringParameters("clientOperationId" -> clientOperationId.toString))
			def withPolicy(body: Schema.Policy) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.PoliciesUpdateResponse])
		}
		object update {
			def apply(project: String, policy: String)(using auth: AuthToken, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"dns/v1/projects/${project}/policies/${policy}").addQueryStringParameters())
		}
		class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			/** For mutating operation requests only. An optional identifier specified by the client. Must be unique for operation resources in the Operations collection. */
			def withClientOperationId(clientOperationId: String) = new patch(req.addQueryStringParameters("clientOperationId" -> clientOperationId.toString))
			def withPolicy(body: Schema.Policy) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.PoliciesPatchResponse])
		}
		object patch {
			def apply(project: String, policy: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"dns/v1/projects/${project}/policies/${policy}").addQueryStringParameters())
		}
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.PoliciesListResponse]) {
			/** Optional. Maximum number of results to be returned. If unspecified, the server decides how many results to return.<br>Format: int32 */
			def withMaxResults(maxResults: Int) = new list(req.addQueryStringParameters("maxResults" -> maxResults.toString))
			/** Optional. A tag returned by a previous list request that was truncated. Use this parameter to continue a previous list request. */
			def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.PoliciesListResponse])
		}
		object list {
			def apply(project: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"dns/v1/projects/${project}/policies").addQueryStringParameters())
			given Conversion[list, Future[Schema.PoliciesListResponse]] = (fun: list) => fun.apply()
		}
	}
	object responsePolicies {
		class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			/** For mutating operation requests only. An optional identifier specified by the client. Must be unique for operation resources in the Operations collection. */
			def withClientOperationId(clientOperationId: String) = new create(req.addQueryStringParameters("clientOperationId" -> clientOperationId.toString))
			def withResponsePolicy(body: Schema.ResponsePolicy) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.ResponsePolicy])
		}
		object create {
			def apply(project: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"dns/v1/projects/${project}/responsePolicies").addQueryStringParameters())
		}
		class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Unit]) {
			/** For mutating operation requests only. An optional identifier specified by the client. Must be unique for operation resources in the Operations collection. */
			def withClientOperationId(clientOperationId: String) = new delete(req.addQueryStringParameters("clientOperationId" -> clientOperationId.toString))
			def apply() = auth.exec(req,_.execute("DELETE")).map(_ => ())
		}
		object delete {
			def apply(project: String, responsePolicy: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"dns/v1/projects/${project}/responsePolicies/${responsePolicy}").addQueryStringParameters())
			given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
		}
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ResponsePolicy]) {
			/** For mutating operation requests only. An optional identifier specified by the client. Must be unique for operation resources in the Operations collection. */
			def withClientOperationId(clientOperationId: String) = new get(req.addQueryStringParameters("clientOperationId" -> clientOperationId.toString))
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ResponsePolicy])
		}
		object get {
			def apply(project: String, responsePolicy: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"dns/v1/projects/${project}/responsePolicies/${responsePolicy}").addQueryStringParameters())
			given Conversion[get, Future[Schema.ResponsePolicy]] = (fun: get) => fun.apply()
		}
		class update(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			/** For mutating operation requests only. An optional identifier specified by the client. Must be unique for operation resources in the Operations collection. */
			def withClientOperationId(clientOperationId: String) = new update(req.addQueryStringParameters("clientOperationId" -> clientOperationId.toString))
			def withResponsePolicy(body: Schema.ResponsePolicy) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.ResponsePoliciesUpdateResponse])
		}
		object update {
			def apply(project: String, responsePolicy: String)(using auth: AuthToken, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"dns/v1/projects/${project}/responsePolicies/${responsePolicy}").addQueryStringParameters())
		}
		class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			/** For mutating operation requests only. An optional identifier specified by the client. Must be unique for operation resources in the Operations collection. */
			def withClientOperationId(clientOperationId: String) = new patch(req.addQueryStringParameters("clientOperationId" -> clientOperationId.toString))
			def withResponsePolicy(body: Schema.ResponsePolicy) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.ResponsePoliciesPatchResponse])
		}
		object patch {
			def apply(project: String, responsePolicy: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"dns/v1/projects/${project}/responsePolicies/${responsePolicy}").addQueryStringParameters())
		}
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ResponsePoliciesListResponse]) {
			/** Optional. Maximum number of results to be returned. If unspecified, the server decides how many results to return.<br>Format: int32 */
			def withMaxResults(maxResults: Int) = new list(req.addQueryStringParameters("maxResults" -> maxResults.toString))
			/** Optional. A tag returned by a previous list request that was truncated. Use this parameter to continue a previous list request. */
			def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ResponsePoliciesListResponse])
		}
		object list {
			def apply(project: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"dns/v1/projects/${project}/responsePolicies").addQueryStringParameters())
			given Conversion[list, Future[Schema.ResponsePoliciesListResponse]] = (fun: list) => fun.apply()
		}
	}
	object responsePolicyRules {
		class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			/** For mutating operation requests only. An optional identifier specified by the client. Must be unique for operation resources in the Operations collection. */
			def withClientOperationId(clientOperationId: String) = new create(req.addQueryStringParameters("clientOperationId" -> clientOperationId.toString))
			def withResponsePolicyRule(body: Schema.ResponsePolicyRule) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.ResponsePolicyRule])
		}
		object create {
			def apply(project: String, responsePolicy: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"dns/v1/projects/${project}/responsePolicies/${responsePolicy}/rules").addQueryStringParameters())
		}
		class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Unit]) {
			/** For mutating operation requests only. An optional identifier specified by the client. Must be unique for operation resources in the Operations collection. */
			def withClientOperationId(clientOperationId: String) = new delete(req.addQueryStringParameters("clientOperationId" -> clientOperationId.toString))
			def apply() = auth.exec(req,_.execute("DELETE")).map(_ => ())
		}
		object delete {
			def apply(project: String, responsePolicy: String, responsePolicyRule: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"dns/v1/projects/${project}/responsePolicies/${responsePolicy}/rules/${responsePolicyRule}").addQueryStringParameters())
			given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
		}
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ResponsePolicyRule]) {
			/** For mutating operation requests only. An optional identifier specified by the client. Must be unique for operation resources in the Operations collection. */
			def withClientOperationId(clientOperationId: String) = new get(req.addQueryStringParameters("clientOperationId" -> clientOperationId.toString))
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ResponsePolicyRule])
		}
		object get {
			def apply(project: String, responsePolicy: String, responsePolicyRule: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"dns/v1/projects/${project}/responsePolicies/${responsePolicy}/rules/${responsePolicyRule}").addQueryStringParameters())
			given Conversion[get, Future[Schema.ResponsePolicyRule]] = (fun: get) => fun.apply()
		}
		class update(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			/** For mutating operation requests only. An optional identifier specified by the client. Must be unique for operation resources in the Operations collection. */
			def withClientOperationId(clientOperationId: String) = new update(req.addQueryStringParameters("clientOperationId" -> clientOperationId.toString))
			def withResponsePolicyRule(body: Schema.ResponsePolicyRule) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.ResponsePolicyRulesUpdateResponse])
		}
		object update {
			def apply(project: String, responsePolicy: String, responsePolicyRule: String)(using auth: AuthToken, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"dns/v1/projects/${project}/responsePolicies/${responsePolicy}/rules/${responsePolicyRule}").addQueryStringParameters())
		}
		class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			/** For mutating operation requests only. An optional identifier specified by the client. Must be unique for operation resources in the Operations collection. */
			def withClientOperationId(clientOperationId: String) = new patch(req.addQueryStringParameters("clientOperationId" -> clientOperationId.toString))
			def withResponsePolicyRule(body: Schema.ResponsePolicyRule) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.ResponsePolicyRulesPatchResponse])
		}
		object patch {
			def apply(project: String, responsePolicy: String, responsePolicyRule: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"dns/v1/projects/${project}/responsePolicies/${responsePolicy}/rules/${responsePolicyRule}").addQueryStringParameters())
		}
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ResponsePolicyRulesListResponse]) {
			/** Optional. Maximum number of results to be returned. If unspecified, the server decides how many results to return.<br>Format: int32 */
			def withMaxResults(maxResults: Int) = new list(req.addQueryStringParameters("maxResults" -> maxResults.toString))
			/** Optional. A tag returned by a previous list request that was truncated. Use this parameter to continue a previous list request. */
			def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ResponsePolicyRulesListResponse])
		}
		object list {
			def apply(project: String, responsePolicy: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"dns/v1/projects/${project}/responsePolicies/${responsePolicy}/rules").addQueryStringParameters())
			given Conversion[list, Future[Schema.ResponsePolicyRulesListResponse]] = (fun: list) => fun.apply()
		}
	}
}
