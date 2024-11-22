package com.boresjo.play.api.google.dns

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
		"""https://www.googleapis.com/auth/cloud-platform""" /* See, edit, configure, and delete your Google Cloud data and see the email address for your Google Account. */,
		"""https://www.googleapis.com/auth/cloud-platform.read-only""" /* View your data across Google Cloud services and see the email address of your Google Account */,
		"""https://www.googleapis.com/auth/ndev.clouddns.readonly""" /* View your DNS records hosted by Google Cloud DNS */,
		"""https://www.googleapis.com/auth/ndev.clouddns.readwrite""" /* View and manage your DNS records hosted by Google Cloud DNS */
	)

	private val BASE_URL = "https://dns.googleapis.com/"

	object resourceRecordSets {
		/** Creates a new ResourceRecordSet. */
		class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/ndev.clouddns.readwrite""")
			/** For mutating operation requests only. An optional identifier specified by the client. Must be unique for operation resources in the Operations collection. */
			def withClientOperationId(clientOperationId: String) = new create(req.addQueryStringParameters("clientOperationId" -> clientOperationId.toString))
			/** Perform the request */
			def withResourceRecordSet(body: Schema.ResourceRecordSet) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.ResourceRecordSet])
		}
		object create {
			def apply(project: String, managedZone: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"dns/v1/projects/${project}/managedZones/${managedZone}/rrsets").addQueryStringParameters())
		}
		/** Deletes a previously created ResourceRecordSet. */
		class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ResourceRecordSetsDeleteResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/ndev.clouddns.readwrite""")
			/** For mutating operation requests only. An optional identifier specified by the client. Must be unique for operation resources in the Operations collection. */
			def withClientOperationId(clientOperationId: String) = new delete(req.addQueryStringParameters("clientOperationId" -> clientOperationId.toString))
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.ResourceRecordSetsDeleteResponse])
		}
		object delete {
			def apply(project: String, managedZone: String, name: String, `type`: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"dns/v1/projects/${project}/managedZones/${managedZone}/rrsets/${name}/${`type`}").addQueryStringParameters())
			given Conversion[delete, Future[Schema.ResourceRecordSetsDeleteResponse]] = (fun: delete) => fun.apply()
		}
		/** Fetches the representation of an existing ResourceRecordSet. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ResourceRecordSet]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/ndev.clouddns.readonly""", """https://www.googleapis.com/auth/ndev.clouddns.readwrite""")
			/** For mutating operation requests only. An optional identifier specified by the client. Must be unique for operation resources in the Operations collection. */
			def withClientOperationId(clientOperationId: String) = new get(req.addQueryStringParameters("clientOperationId" -> clientOperationId.toString))
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ResourceRecordSet])
		}
		object get {
			def apply(project: String, managedZone: String, name: String, `type`: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"dns/v1/projects/${project}/managedZones/${managedZone}/rrsets/${name}/${`type`}").addQueryStringParameters())
			given Conversion[get, Future[Schema.ResourceRecordSet]] = (fun: get) => fun.apply()
		}
		/** Applies a partial update to an existing ResourceRecordSet. */
		class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/ndev.clouddns.readwrite""")
			/** For mutating operation requests only. An optional identifier specified by the client. Must be unique for operation resources in the Operations collection. */
			def withClientOperationId(clientOperationId: String) = new patch(req.addQueryStringParameters("clientOperationId" -> clientOperationId.toString))
			/** Perform the request */
			def withResourceRecordSet(body: Schema.ResourceRecordSet) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.ResourceRecordSet])
		}
		object patch {
			def apply(project: String, managedZone: String, name: String, `type`: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"dns/v1/projects/${project}/managedZones/${managedZone}/rrsets/${name}/${`type`}").addQueryStringParameters())
		}
		/** Enumerates ResourceRecordSets that you have created but not yet deleted. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ResourceRecordSetsListResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/ndev.clouddns.readonly""", """https://www.googleapis.com/auth/ndev.clouddns.readwrite""")
			/** Optional. Maximum number of results to be returned. If unspecified, the server decides how many results to return.<br>Format: int32 */
			def withMaxResults(maxResults: Int) = new list(req.addQueryStringParameters("maxResults" -> maxResults.toString))
			/** Optional. A tag returned by a previous list request that was truncated. Use this parameter to continue a previous list request. */
			def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ResourceRecordSetsListResponse])
		}
		object list {
			def apply(project: String, managedZone: String, name: String, `type`: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"dns/v1/projects/${project}/managedZones/${managedZone}/rrsets").addQueryStringParameters("name" -> name.toString, "type" -> `type`.toString))
			given Conversion[list, Future[Schema.ResourceRecordSetsListResponse]] = (fun: list) => fun.apply()
		}
	}
	object changes {
		/** Atomically updates the ResourceRecordSet collection. */
		class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/ndev.clouddns.readwrite""")
			/** For mutating operation requests only. An optional identifier specified by the client. Must be unique for operation resources in the Operations collection. */
			def withClientOperationId(clientOperationId: String) = new create(req.addQueryStringParameters("clientOperationId" -> clientOperationId.toString))
			/** Perform the request */
			def withChange(body: Schema.Change) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Change])
		}
		object create {
			def apply(project: String, managedZone: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"dns/v1/projects/${project}/managedZones/${managedZone}/changes").addQueryStringParameters())
		}
		/** Fetches the representation of an existing Change. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Change]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/ndev.clouddns.readonly""", """https://www.googleapis.com/auth/ndev.clouddns.readwrite""")
			/** For mutating operation requests only. An optional identifier specified by the client. Must be unique for operation resources in the Operations collection. */
			def withClientOperationId(clientOperationId: String) = new get(req.addQueryStringParameters("clientOperationId" -> clientOperationId.toString))
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Change])
		}
		object get {
			def apply(project: String, managedZone: String, changeId: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"dns/v1/projects/${project}/managedZones/${managedZone}/changes/${changeId}").addQueryStringParameters())
			given Conversion[get, Future[Schema.Change]] = (fun: get) => fun.apply()
		}
		/** Enumerates Changes to a ResourceRecordSet collection. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ChangesListResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/ndev.clouddns.readonly""", """https://www.googleapis.com/auth/ndev.clouddns.readwrite""")
			/** Optional. Maximum number of results to be returned. If unspecified, the server decides how many results to return.<br>Format: int32 */
			def withMaxResults(maxResults: Int) = new list(req.addQueryStringParameters("maxResults" -> maxResults.toString))
			/** Optional. A tag returned by a previous list request that was truncated. Use this parameter to continue a previous list request. */
			def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ChangesListResponse])
		}
		object list {
			def apply(project: String, managedZone: String, sortBy: String, sortOrder: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"dns/v1/projects/${project}/managedZones/${managedZone}/changes").addQueryStringParameters("sortBy" -> sortBy.toString, "sortOrder" -> sortOrder.toString))
			given Conversion[list, Future[Schema.ChangesListResponse]] = (fun: list) => fun.apply()
		}
	}
	object dnsKeys {
		/** Fetches the representation of an existing DnsKey. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.DnsKey]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/ndev.clouddns.readonly""", """https://www.googleapis.com/auth/ndev.clouddns.readwrite""")
			/** For mutating operation requests only. An optional identifier specified by the client. Must be unique for operation resources in the Operations collection. */
			def withClientOperationId(clientOperationId: String) = new get(req.addQueryStringParameters("clientOperationId" -> clientOperationId.toString))
			/** An optional comma-separated list of digest types to compute and display for key signing keys. If omitted, the recommended digest type is computed and displayed. */
			def withDigestType(digestType: String) = new get(req.addQueryStringParameters("digestType" -> digestType.toString))
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.DnsKey])
		}
		object get {
			def apply(project: String, managedZone: String, dnsKeyId: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"dns/v1/projects/${project}/managedZones/${managedZone}/dnsKeys/${dnsKeyId}").addQueryStringParameters())
			given Conversion[get, Future[Schema.DnsKey]] = (fun: get) => fun.apply()
		}
		/** Enumerates DnsKeys to a ResourceRecordSet collection. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.DnsKeysListResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/ndev.clouddns.readonly""", """https://www.googleapis.com/auth/ndev.clouddns.readwrite""")
			/** Optional. Maximum number of results to be returned. If unspecified, the server decides how many results to return.<br>Format: int32 */
			def withMaxResults(maxResults: Int) = new list(req.addQueryStringParameters("maxResults" -> maxResults.toString))
			/** Optional. A tag returned by a previous list request that was truncated. Use this parameter to continue a previous list request. */
			def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
			/** An optional comma-separated list of digest types to compute and display for key signing keys. If omitted, the recommended digest type is computed and displayed. */
			def withDigestType(digestType: String) = new list(req.addQueryStringParameters("digestType" -> digestType.toString))
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.DnsKeysListResponse])
		}
		object list {
			def apply(project: String, managedZone: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"dns/v1/projects/${project}/managedZones/${managedZone}/dnsKeys").addQueryStringParameters())
			given Conversion[list, Future[Schema.DnsKeysListResponse]] = (fun: list) => fun.apply()
		}
	}
	object projects {
		/** Fetches the representation of an existing Project. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Project]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/ndev.clouddns.readonly""", """https://www.googleapis.com/auth/ndev.clouddns.readwrite""")
			/** For mutating operation requests only. An optional identifier specified by the client. Must be unique for operation resources in the Operations collection. */
			def withClientOperationId(clientOperationId: String) = new get(req.addQueryStringParameters("clientOperationId" -> clientOperationId.toString))
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Project])
		}
		object get {
			def apply(project: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"dns/v1/projects/${project}").addQueryStringParameters())
			given Conversion[get, Future[Schema.Project]] = (fun: get) => fun.apply()
		}
	}
	object managedZoneOperations {
		/** Fetches the representation of an existing Operation. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/ndev.clouddns.readonly""", """https://www.googleapis.com/auth/ndev.clouddns.readwrite""")
			/** For mutating operation requests only. An optional identifier specified by the client. Must be unique for operation resources in the Operations collection. */
			def withClientOperationId(clientOperationId: String) = new get(req.addQueryStringParameters("clientOperationId" -> clientOperationId.toString))
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Operation])
		}
		object get {
			def apply(project: String, managedZone: String, operation: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"dns/v1/projects/${project}/managedZones/${managedZone}/operations/${operation}").addQueryStringParameters())
			given Conversion[get, Future[Schema.Operation]] = (fun: get) => fun.apply()
		}
		/** Enumerates Operations for the given ManagedZone. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ManagedZoneOperationsListResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/ndev.clouddns.readonly""", """https://www.googleapis.com/auth/ndev.clouddns.readwrite""")
			/** Optional. Maximum number of results to be returned. If unspecified, the server decides how many results to return.<br>Format: int32 */
			def withMaxResults(maxResults: Int) = new list(req.addQueryStringParameters("maxResults" -> maxResults.toString))
			/** Optional. A tag returned by a previous list request that was truncated. Use this parameter to continue a previous list request. */
			def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ManagedZoneOperationsListResponse])
		}
		object list {
			def apply(project: String, managedZone: String, sortBy: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"dns/v1/projects/${project}/managedZones/${managedZone}/operations").addQueryStringParameters("sortBy" -> sortBy.toString))
			given Conversion[list, Future[Schema.ManagedZoneOperationsListResponse]] = (fun: list) => fun.apply()
		}
	}
	object managedZones {
		/** Returns permissions that a caller has on the specified resource. If the resource does not exist, this returns an empty set of permissions, not a `NOT_FOUND` error. Note: This operation is designed to be used for building permission-aware UIs and command-line tools, not for authorization checking. This operation may "fail open" without warning. */
		class testIamPermissions(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/ndev.clouddns.readonly""", """https://www.googleapis.com/auth/ndev.clouddns.readwrite""")
			/** Perform the request */
			def withGoogleIamV1TestIamPermissionsRequest(body: Schema.GoogleIamV1TestIamPermissionsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleIamV1TestIamPermissionsResponse])
		}
		object testIamPermissions {
			def apply(projectsId :PlayApi, managedZonesId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): testIamPermissions = new testIamPermissions(ws.url(BASE_URL + s"dns/v1/projects/${projectsId}/managedZones/${managedZonesId}:testIamPermissions").addQueryStringParameters("resource" -> resource.toString))
		}
		/** Gets the access control policy for a resource. Returns an empty policy if the resource exists and does not have a policy set. */
		class getIamPolicy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/ndev.clouddns.readonly""", """https://www.googleapis.com/auth/ndev.clouddns.readwrite""")
			/** Perform the request */
			def withGoogleIamV1GetIamPolicyRequest(body: Schema.GoogleIamV1GetIamPolicyRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleIamV1Policy])
		}
		object getIamPolicy {
			def apply(projectsId :PlayApi, managedZonesId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): getIamPolicy = new getIamPolicy(ws.url(BASE_URL + s"dns/v1/projects/${projectsId}/managedZones/${managedZonesId}:getIamPolicy").addQueryStringParameters("resource" -> resource.toString))
		}
		/** Deletes a previously created ManagedZone. */
		class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Unit]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/ndev.clouddns.readwrite""")
			/** For mutating operation requests only. An optional identifier specified by the client. Must be unique for operation resources in the Operations collection. */
			def withClientOperationId(clientOperationId: String) = new delete(req.addQueryStringParameters("clientOperationId" -> clientOperationId.toString))
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_ => ())
		}
		object delete {
			def apply(project: String, managedZone: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"dns/v1/projects/${project}/managedZones/${managedZone}").addQueryStringParameters())
			given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
		}
		/** Fetches the representation of an existing ManagedZone. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ManagedZone]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/ndev.clouddns.readonly""", """https://www.googleapis.com/auth/ndev.clouddns.readwrite""")
			/** For mutating operation requests only. An optional identifier specified by the client. Must be unique for operation resources in the Operations collection. */
			def withClientOperationId(clientOperationId: String) = new get(req.addQueryStringParameters("clientOperationId" -> clientOperationId.toString))
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ManagedZone])
		}
		object get {
			def apply(project: String, managedZone: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"dns/v1/projects/${project}/managedZones/${managedZone}").addQueryStringParameters())
			given Conversion[get, Future[Schema.ManagedZone]] = (fun: get) => fun.apply()
		}
		/** Updates an existing ManagedZone. */
		class update(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/ndev.clouddns.readwrite""")
			/** For mutating operation requests only. An optional identifier specified by the client. Must be unique for operation resources in the Operations collection. */
			def withClientOperationId(clientOperationId: String) = new update(req.addQueryStringParameters("clientOperationId" -> clientOperationId.toString))
			/** Perform the request */
			def withManagedZone(body: Schema.ManagedZone) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.Operation])
		}
		object update {
			def apply(project: String, managedZone: String)(using signer: RequestSigner, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"dns/v1/projects/${project}/managedZones/${managedZone}").addQueryStringParameters())
		}
		/** Applies a partial update to an existing ManagedZone. */
		class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/ndev.clouddns.readwrite""")
			/** For mutating operation requests only. An optional identifier specified by the client. Must be unique for operation resources in the Operations collection. */
			def withClientOperationId(clientOperationId: String) = new patch(req.addQueryStringParameters("clientOperationId" -> clientOperationId.toString))
			/** Perform the request */
			def withManagedZone(body: Schema.ManagedZone) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Operation])
		}
		object patch {
			def apply(project: String, managedZone: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"dns/v1/projects/${project}/managedZones/${managedZone}").addQueryStringParameters())
		}
		/** Enumerates ManagedZones that have been created but not yet deleted. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ManagedZonesListResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/ndev.clouddns.readonly""", """https://www.googleapis.com/auth/ndev.clouddns.readwrite""")
			/** Optional. Maximum number of results to be returned. If unspecified, the server decides how many results to return.<br>Format: int32 */
			def withMaxResults(maxResults: Int) = new list(req.addQueryStringParameters("maxResults" -> maxResults.toString))
			/** Optional. A tag returned by a previous list request that was truncated. Use this parameter to continue a previous list request. */
			def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ManagedZonesListResponse])
		}
		object list {
			def apply(project: String, dnsName: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"dns/v1/projects/${project}/managedZones").addQueryStringParameters("dnsName" -> dnsName.toString))
			given Conversion[list, Future[Schema.ManagedZonesListResponse]] = (fun: list) => fun.apply()
		}
		/** Creates a new ManagedZone. */
		class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/ndev.clouddns.readwrite""")
			/** For mutating operation requests only. An optional identifier specified by the client. Must be unique for operation resources in the Operations collection. */
			def withClientOperationId(clientOperationId: String) = new create(req.addQueryStringParameters("clientOperationId" -> clientOperationId.toString))
			/** Perform the request */
			def withManagedZone(body: Schema.ManagedZone) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.ManagedZone])
		}
		object create {
			def apply(project: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"dns/v1/projects/${project}/managedZones").addQueryStringParameters())
		}
		/** Sets the access control policy on the specified resource. Replaces any existing policy. Can return `NOT_FOUND`, `INVALID_ARGUMENT`, and `PERMISSION_DENIED` errors. */
		class setIamPolicy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/ndev.clouddns.readwrite""")
			/** Perform the request */
			def withGoogleIamV1SetIamPolicyRequest(body: Schema.GoogleIamV1SetIamPolicyRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleIamV1Policy])
		}
		object setIamPolicy {
			def apply(projectsId :PlayApi, managedZonesId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): setIamPolicy = new setIamPolicy(ws.url(BASE_URL + s"dns/v1/projects/${projectsId}/managedZones/${managedZonesId}:setIamPolicy").addQueryStringParameters("resource" -> resource.toString))
		}
	}
	object policies {
		/** Creates a new Policy. */
		class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/ndev.clouddns.readwrite""")
			/** For mutating operation requests only. An optional identifier specified by the client. Must be unique for operation resources in the Operations collection. */
			def withClientOperationId(clientOperationId: String) = new create(req.addQueryStringParameters("clientOperationId" -> clientOperationId.toString))
			/** Perform the request */
			def withPolicy(body: Schema.Policy) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
		}
		object create {
			def apply(project: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"dns/v1/projects/${project}/policies").addQueryStringParameters())
		}
		/** Deletes a previously created Policy. Fails if the policy is still being referenced by a network. */
		class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Unit]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/ndev.clouddns.readwrite""")
			/** For mutating operation requests only. An optional identifier specified by the client. Must be unique for operation resources in the Operations collection. */
			def withClientOperationId(clientOperationId: String) = new delete(req.addQueryStringParameters("clientOperationId" -> clientOperationId.toString))
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_ => ())
		}
		object delete {
			def apply(project: String, policy: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"dns/v1/projects/${project}/policies/${policy}").addQueryStringParameters())
			given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
		}
		/** Fetches the representation of an existing Policy. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Policy]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/ndev.clouddns.readonly""", """https://www.googleapis.com/auth/ndev.clouddns.readwrite""")
			/** For mutating operation requests only. An optional identifier specified by the client. Must be unique for operation resources in the Operations collection. */
			def withClientOperationId(clientOperationId: String) = new get(req.addQueryStringParameters("clientOperationId" -> clientOperationId.toString))
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Policy])
		}
		object get {
			def apply(project: String, policy: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"dns/v1/projects/${project}/policies/${policy}").addQueryStringParameters())
			given Conversion[get, Future[Schema.Policy]] = (fun: get) => fun.apply()
		}
		/** Updates an existing Policy. */
		class update(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/ndev.clouddns.readwrite""")
			/** For mutating operation requests only. An optional identifier specified by the client. Must be unique for operation resources in the Operations collection. */
			def withClientOperationId(clientOperationId: String) = new update(req.addQueryStringParameters("clientOperationId" -> clientOperationId.toString))
			/** Perform the request */
			def withPolicy(body: Schema.Policy) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.PoliciesUpdateResponse])
		}
		object update {
			def apply(project: String, policy: String)(using signer: RequestSigner, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"dns/v1/projects/${project}/policies/${policy}").addQueryStringParameters())
		}
		/** Applies a partial update to an existing Policy. */
		class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/ndev.clouddns.readwrite""")
			/** For mutating operation requests only. An optional identifier specified by the client. Must be unique for operation resources in the Operations collection. */
			def withClientOperationId(clientOperationId: String) = new patch(req.addQueryStringParameters("clientOperationId" -> clientOperationId.toString))
			/** Perform the request */
			def withPolicy(body: Schema.Policy) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.PoliciesPatchResponse])
		}
		object patch {
			def apply(project: String, policy: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"dns/v1/projects/${project}/policies/${policy}").addQueryStringParameters())
		}
		/** Enumerates all Policies associated with a project. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.PoliciesListResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/ndev.clouddns.readonly""", """https://www.googleapis.com/auth/ndev.clouddns.readwrite""")
			/** Optional. Maximum number of results to be returned. If unspecified, the server decides how many results to return.<br>Format: int32 */
			def withMaxResults(maxResults: Int) = new list(req.addQueryStringParameters("maxResults" -> maxResults.toString))
			/** Optional. A tag returned by a previous list request that was truncated. Use this parameter to continue a previous list request. */
			def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.PoliciesListResponse])
		}
		object list {
			def apply(project: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"dns/v1/projects/${project}/policies").addQueryStringParameters())
			given Conversion[list, Future[Schema.PoliciesListResponse]] = (fun: list) => fun.apply()
		}
	}
	object responsePolicies {
		/** Creates a new Response Policy */
		class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/ndev.clouddns.readwrite""")
			/** For mutating operation requests only. An optional identifier specified by the client. Must be unique for operation resources in the Operations collection. */
			def withClientOperationId(clientOperationId: String) = new create(req.addQueryStringParameters("clientOperationId" -> clientOperationId.toString))
			/** Perform the request */
			def withResponsePolicy(body: Schema.ResponsePolicy) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.ResponsePolicy])
		}
		object create {
			def apply(project: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"dns/v1/projects/${project}/responsePolicies").addQueryStringParameters())
		}
		/** Deletes a previously created Response Policy. Fails if the response policy is non-empty or still being referenced by a network. */
		class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Unit]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/ndev.clouddns.readwrite""")
			/** For mutating operation requests only. An optional identifier specified by the client. Must be unique for operation resources in the Operations collection. */
			def withClientOperationId(clientOperationId: String) = new delete(req.addQueryStringParameters("clientOperationId" -> clientOperationId.toString))
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_ => ())
		}
		object delete {
			def apply(project: String, responsePolicy: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"dns/v1/projects/${project}/responsePolicies/${responsePolicy}").addQueryStringParameters())
			given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
		}
		/** Fetches the representation of an existing Response Policy. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ResponsePolicy]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/ndev.clouddns.readonly""", """https://www.googleapis.com/auth/ndev.clouddns.readwrite""")
			/** For mutating operation requests only. An optional identifier specified by the client. Must be unique for operation resources in the Operations collection. */
			def withClientOperationId(clientOperationId: String) = new get(req.addQueryStringParameters("clientOperationId" -> clientOperationId.toString))
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ResponsePolicy])
		}
		object get {
			def apply(project: String, responsePolicy: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"dns/v1/projects/${project}/responsePolicies/${responsePolicy}").addQueryStringParameters())
			given Conversion[get, Future[Schema.ResponsePolicy]] = (fun: get) => fun.apply()
		}
		/** Updates an existing Response Policy. */
		class update(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/ndev.clouddns.readwrite""")
			/** For mutating operation requests only. An optional identifier specified by the client. Must be unique for operation resources in the Operations collection. */
			def withClientOperationId(clientOperationId: String) = new update(req.addQueryStringParameters("clientOperationId" -> clientOperationId.toString))
			/** Perform the request */
			def withResponsePolicy(body: Schema.ResponsePolicy) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.ResponsePoliciesUpdateResponse])
		}
		object update {
			def apply(project: String, responsePolicy: String)(using signer: RequestSigner, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"dns/v1/projects/${project}/responsePolicies/${responsePolicy}").addQueryStringParameters())
		}
		/** Applies a partial update to an existing Response Policy. */
		class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/ndev.clouddns.readwrite""")
			/** For mutating operation requests only. An optional identifier specified by the client. Must be unique for operation resources in the Operations collection. */
			def withClientOperationId(clientOperationId: String) = new patch(req.addQueryStringParameters("clientOperationId" -> clientOperationId.toString))
			/** Perform the request */
			def withResponsePolicy(body: Schema.ResponsePolicy) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.ResponsePoliciesPatchResponse])
		}
		object patch {
			def apply(project: String, responsePolicy: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"dns/v1/projects/${project}/responsePolicies/${responsePolicy}").addQueryStringParameters())
		}
		/** Enumerates all Response Policies associated with a project. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ResponsePoliciesListResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/ndev.clouddns.readonly""", """https://www.googleapis.com/auth/ndev.clouddns.readwrite""")
			/** Optional. Maximum number of results to be returned. If unspecified, the server decides how many results to return.<br>Format: int32 */
			def withMaxResults(maxResults: Int) = new list(req.addQueryStringParameters("maxResults" -> maxResults.toString))
			/** Optional. A tag returned by a previous list request that was truncated. Use this parameter to continue a previous list request. */
			def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ResponsePoliciesListResponse])
		}
		object list {
			def apply(project: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"dns/v1/projects/${project}/responsePolicies").addQueryStringParameters())
			given Conversion[list, Future[Schema.ResponsePoliciesListResponse]] = (fun: list) => fun.apply()
		}
	}
	object responsePolicyRules {
		/** Creates a new Response Policy Rule. */
		class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/ndev.clouddns.readwrite""")
			/** For mutating operation requests only. An optional identifier specified by the client. Must be unique for operation resources in the Operations collection. */
			def withClientOperationId(clientOperationId: String) = new create(req.addQueryStringParameters("clientOperationId" -> clientOperationId.toString))
			/** Perform the request */
			def withResponsePolicyRule(body: Schema.ResponsePolicyRule) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.ResponsePolicyRule])
		}
		object create {
			def apply(project: String, responsePolicy: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"dns/v1/projects/${project}/responsePolicies/${responsePolicy}/rules").addQueryStringParameters())
		}
		/** Deletes a previously created Response Policy Rule. */
		class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Unit]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/ndev.clouddns.readwrite""")
			/** For mutating operation requests only. An optional identifier specified by the client. Must be unique for operation resources in the Operations collection. */
			def withClientOperationId(clientOperationId: String) = new delete(req.addQueryStringParameters("clientOperationId" -> clientOperationId.toString))
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_ => ())
		}
		object delete {
			def apply(project: String, responsePolicy: String, responsePolicyRule: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"dns/v1/projects/${project}/responsePolicies/${responsePolicy}/rules/${responsePolicyRule}").addQueryStringParameters())
			given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
		}
		/** Fetches the representation of an existing Response Policy Rule. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ResponsePolicyRule]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/ndev.clouddns.readonly""", """https://www.googleapis.com/auth/ndev.clouddns.readwrite""")
			/** For mutating operation requests only. An optional identifier specified by the client. Must be unique for operation resources in the Operations collection. */
			def withClientOperationId(clientOperationId: String) = new get(req.addQueryStringParameters("clientOperationId" -> clientOperationId.toString))
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ResponsePolicyRule])
		}
		object get {
			def apply(project: String, responsePolicy: String, responsePolicyRule: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"dns/v1/projects/${project}/responsePolicies/${responsePolicy}/rules/${responsePolicyRule}").addQueryStringParameters())
			given Conversion[get, Future[Schema.ResponsePolicyRule]] = (fun: get) => fun.apply()
		}
		/** Updates an existing Response Policy Rule. */
		class update(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/ndev.clouddns.readwrite""")
			/** For mutating operation requests only. An optional identifier specified by the client. Must be unique for operation resources in the Operations collection. */
			def withClientOperationId(clientOperationId: String) = new update(req.addQueryStringParameters("clientOperationId" -> clientOperationId.toString))
			/** Perform the request */
			def withResponsePolicyRule(body: Schema.ResponsePolicyRule) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.ResponsePolicyRulesUpdateResponse])
		}
		object update {
			def apply(project: String, responsePolicy: String, responsePolicyRule: String)(using signer: RequestSigner, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"dns/v1/projects/${project}/responsePolicies/${responsePolicy}/rules/${responsePolicyRule}").addQueryStringParameters())
		}
		/** Applies a partial update to an existing Response Policy Rule. */
		class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/ndev.clouddns.readwrite""")
			/** For mutating operation requests only. An optional identifier specified by the client. Must be unique for operation resources in the Operations collection. */
			def withClientOperationId(clientOperationId: String) = new patch(req.addQueryStringParameters("clientOperationId" -> clientOperationId.toString))
			/** Perform the request */
			def withResponsePolicyRule(body: Schema.ResponsePolicyRule) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.ResponsePolicyRulesPatchResponse])
		}
		object patch {
			def apply(project: String, responsePolicy: String, responsePolicyRule: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"dns/v1/projects/${project}/responsePolicies/${responsePolicy}/rules/${responsePolicyRule}").addQueryStringParameters())
		}
		/** Enumerates all Response Policy Rules associated with a project. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ResponsePolicyRulesListResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/ndev.clouddns.readonly""", """https://www.googleapis.com/auth/ndev.clouddns.readwrite""")
			/** Optional. Maximum number of results to be returned. If unspecified, the server decides how many results to return.<br>Format: int32 */
			def withMaxResults(maxResults: Int) = new list(req.addQueryStringParameters("maxResults" -> maxResults.toString))
			/** Optional. A tag returned by a previous list request that was truncated. Use this parameter to continue a previous list request. */
			def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ResponsePolicyRulesListResponse])
		}
		object list {
			def apply(project: String, responsePolicy: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"dns/v1/projects/${project}/responsePolicies/${responsePolicy}/rules").addQueryStringParameters())
			given Conversion[list, Future[Schema.ResponsePolicyRulesListResponse]] = (fun: list) => fun.apply()
		}
	}
}
