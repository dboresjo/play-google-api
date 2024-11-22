package com.boresjo.play.api.google.spanner

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
		"""https://www.googleapis.com/auth/spanner.admin""" /* Administer your Spanner databases */,
		"""https://www.googleapis.com/auth/spanner.data""" /* View and manage the contents of your Spanner databases */
	)

	private val BASE_URL = "https://spanner.googleapis.com/"

	object scans {
		/** Return available scans given a Database-specific resource name. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListScansResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/spanner.data""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListScansResponse])
		}
		object list {
			def apply(parent: String, view: String, filter: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/scans").addQueryStringParameters("parent" -> parent.toString, "view" -> view.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
			given Conversion[list, Future[Schema.ListScansResponse]] = (fun: list) => fun.apply()
		}
	}
	object projects {
		object instanceConfigs {
			/** Deletes the instance configuration. Deletion is only allowed when no instances are using the configuration. If any instances are using the configuration, returns `FAILED_PRECONDITION`. Only user-managed configurations can be deleted. Authorization requires `spanner.instanceConfigs.delete` permission on the resource name. */
			class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/spanner.admin""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
			}
			object delete {
				def apply(projectsId :PlayApi, instanceConfigsId :PlayApi, name: String, etag: String, validateOnly: Boolean)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/instanceConfigs/${instanceConfigsId}").addQueryStringParameters("name" -> name.toString, "etag" -> etag.toString, "validateOnly" -> validateOnly.toString))
				given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
			}
			/** Gets information about a particular instance configuration. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.InstanceConfig]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/spanner.admin""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.InstanceConfig])
			}
			object get {
				def apply(projectsId :PlayApi, instanceConfigsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/instanceConfigs/${instanceConfigsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.InstanceConfig]] = (fun: get) => fun.apply()
			}
			/** Updates an instance configuration. The returned long-running operation can be used to track the progress of updating the instance. If the named instance configuration does not exist, returns `NOT_FOUND`. Only user-managed configurations can be updated. Immediately after the request returns: &#42; The instance configuration's reconciling field is set to true. While the operation is pending: &#42; Cancelling the operation sets its metadata's cancel_time. The operation is guaranteed to succeed at undoing all changes, after which point it terminates with a `CANCELLED` status. &#42; All other attempts to modify the instance configuration are rejected. &#42; Reading the instance configuration via the API continues to give the pre-request values. Upon completion of the returned operation: &#42; Creating instances using the instance configuration uses the new values. &#42; The new values of the instance configuration are readable via the API. &#42; The instance configuration's reconciling field becomes false. The returned long-running operation will have a name of the format `/operations/` and can be used to track the instance configuration modification. The metadata field type is UpdateInstanceConfigMetadata. The response field type is InstanceConfig, if successful. Authorization requires `spanner.instanceConfigs.update` permission on the resource name. */
			class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/spanner.admin""")
				/** Perform the request */
				def withUpdateInstanceConfigRequest(body: Schema.UpdateInstanceConfigRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Operation])
			}
			object patch {
				def apply(projectsId :PlayApi, instanceConfigsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/instanceConfigs/${instanceConfigsId}").addQueryStringParameters("name" -> name.toString))
			}
			/** Lists the supported instance configurations for a given project. Returns both Google-managed configurations and user-managed configurations. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListInstanceConfigsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/spanner.admin""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListInstanceConfigsResponse])
			}
			object list {
				def apply(projectsId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/instanceConfigs").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListInstanceConfigsResponse]] = (fun: list) => fun.apply()
			}
			/** Creates an instance configuration and begins preparing it to be used. The returned long-running operation can be used to track the progress of preparing the new instance configuration. The instance configuration name is assigned by the caller. If the named instance configuration already exists, `CreateInstanceConfig` returns `ALREADY_EXISTS`. Immediately after the request returns: &#42; The instance configuration is readable via the API, with all requested attributes. The instance configuration's reconciling field is set to true. Its state is `CREATING`. While the operation is pending: &#42; Cancelling the operation renders the instance configuration immediately unreadable via the API. &#42; Except for deleting the creating resource, all other attempts to modify the instance configuration are rejected. Upon completion of the returned operation: &#42; Instances can be created using the instance configuration. &#42; The instance configuration's reconciling field becomes false. Its state becomes `READY`. The returned long-running operation will have a name of the format `/operations/` and can be used to track creation of the instance configuration. The metadata field type is CreateInstanceConfigMetadata. The response field type is InstanceConfig, if successful. Authorization requires `spanner.instanceConfigs.create` permission on the resource parent. */
			class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/spanner.admin""")
				/** Perform the request */
				def withCreateInstanceConfigRequest(body: Schema.CreateInstanceConfigRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
			}
			object create {
				def apply(projectsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/instanceConfigs").addQueryStringParameters("parent" -> parent.toString))
			}
			object operations {
				/** Lists operations that match the specified filter in the request. If the server doesn't support this method, it returns `UNIMPLEMENTED`. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListOperationsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/spanner.admin""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListOperationsResponse])
				}
				object list {
					def apply(projectsId :PlayApi, instanceConfigsId :PlayApi, name: String, filter: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/instanceConfigs/${instanceConfigsId}/operations").addQueryStringParameters("name" -> name.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListOperationsResponse]] = (fun: list) => fun.apply()
				}
				/** Gets the latest state of a long-running operation. Clients can use this method to poll the operation result at intervals as recommended by the API service. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/spanner.admin""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Operation])
				}
				object get {
					def apply(projectsId :PlayApi, instanceConfigsId :PlayApi, operationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/instanceConfigs/${instanceConfigsId}/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.Operation]] = (fun: get) => fun.apply()
				}
				/** Deletes a long-running operation. This method indicates that the client is no longer interested in the operation result. It does not cancel the operation. If the server doesn't support this method, it returns `google.rpc.Code.UNIMPLEMENTED`. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/spanner.admin""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
				}
				object delete {
					def apply(projectsId :PlayApi, instanceConfigsId :PlayApi, operationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/instanceConfigs/${instanceConfigsId}/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
				}
				/** Starts asynchronous cancellation on a long-running operation. The server makes a best effort to cancel the operation, but success is not guaranteed. If the server doesn't support this method, it returns `google.rpc.Code.UNIMPLEMENTED`. Clients can use Operations.GetOperation or other methods to check whether the cancellation succeeded or whether the operation completed despite cancellation. On successful cancellation, the operation is not deleted; instead, it becomes an operation with an Operation.error value with a google.rpc.Status.code of 1, corresponding to `Code.CANCELLED`. */
				class cancel(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/spanner.admin""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("POST")).map(_.json.as[Schema.Empty])
				}
				object cancel {
					def apply(projectsId :PlayApi, instanceConfigsId :PlayApi, operationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): cancel = new cancel(ws.url(BASE_URL + s"v1/projects/${projectsId}/instanceConfigs/${instanceConfigsId}/operations/${operationsId}:cancel").addQueryStringParameters("name" -> name.toString))
					given Conversion[cancel, Future[Schema.Empty]] = (fun: cancel) => fun.apply()
				}
			}
			object ssdCaches {
				object operations {
					/** Lists operations that match the specified filter in the request. If the server doesn't support this method, it returns `UNIMPLEMENTED`. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListOperationsResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/spanner.admin""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListOperationsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, instanceConfigsId :PlayApi, ssdCachesId :PlayApi, name: String, filter: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/instanceConfigs/${instanceConfigsId}/ssdCaches/${ssdCachesId}/operations").addQueryStringParameters("name" -> name.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
						given Conversion[list, Future[Schema.ListOperationsResponse]] = (fun: list) => fun.apply()
					}
					/** Gets the latest state of a long-running operation. Clients can use this method to poll the operation result at intervals as recommended by the API service. */
					class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/spanner.admin""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Operation])
					}
					object get {
						def apply(projectsId :PlayApi, instanceConfigsId :PlayApi, ssdCachesId :PlayApi, operationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/instanceConfigs/${instanceConfigsId}/ssdCaches/${ssdCachesId}/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.Operation]] = (fun: get) => fun.apply()
					}
					/** Deletes a long-running operation. This method indicates that the client is no longer interested in the operation result. It does not cancel the operation. If the server doesn't support this method, it returns `google.rpc.Code.UNIMPLEMENTED`. */
					class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/spanner.admin""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
					}
					object delete {
						def apply(projectsId :PlayApi, instanceConfigsId :PlayApi, ssdCachesId :PlayApi, operationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/instanceConfigs/${instanceConfigsId}/ssdCaches/${ssdCachesId}/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
					}
					/** Starts asynchronous cancellation on a long-running operation. The server makes a best effort to cancel the operation, but success is not guaranteed. If the server doesn't support this method, it returns `google.rpc.Code.UNIMPLEMENTED`. Clients can use Operations.GetOperation or other methods to check whether the cancellation succeeded or whether the operation completed despite cancellation. On successful cancellation, the operation is not deleted; instead, it becomes an operation with an Operation.error value with a google.rpc.Status.code of 1, corresponding to `Code.CANCELLED`. */
					class cancel(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/spanner.admin""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("POST")).map(_.json.as[Schema.Empty])
					}
					object cancel {
						def apply(projectsId :PlayApi, instanceConfigsId :PlayApi, ssdCachesId :PlayApi, operationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): cancel = new cancel(ws.url(BASE_URL + s"v1/projects/${projectsId}/instanceConfigs/${instanceConfigsId}/ssdCaches/${ssdCachesId}/operations/${operationsId}:cancel").addQueryStringParameters("name" -> name.toString))
						given Conversion[cancel, Future[Schema.Empty]] = (fun: cancel) => fun.apply()
					}
				}
			}
		}
		object instanceConfigOperations {
			/** Lists the user-managed instance configuration long-running operations in the given project. An instance configuration operation has a name of the form `projects//instanceConfigs//operations/`. The long-running operation metadata field type `metadata.type_url` describes the type of the metadata. Operations returned include those that have completed/failed/canceled within the last 7 days, and pending operations. Operations returned are ordered by `operation.metadata.value.start_time` in descending order starting from the most recently started operation. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListInstanceConfigOperationsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/spanner.admin""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListInstanceConfigOperationsResponse])
			}
			object list {
				def apply(projectsId :PlayApi, parent: String, filter: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/instanceConfigOperations").addQueryStringParameters("parent" -> parent.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListInstanceConfigOperationsResponse]] = (fun: list) => fun.apply()
			}
		}
		object instances {
			/** Returns permissions that the caller has on the specified instance resource. Attempting this RPC on a non-existent Cloud Spanner instance resource will result in a NOT_FOUND error if the user has `spanner.instances.list` permission on the containing Google Cloud Project. Otherwise returns an empty set of permissions. */
			class testIamPermissions(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/spanner.admin""")
				/** Perform the request */
				def withTestIamPermissionsRequest(body: Schema.TestIamPermissionsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.TestIamPermissionsResponse])
			}
			object testIamPermissions {
				def apply(projectsId :PlayApi, instancesId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): testIamPermissions = new testIamPermissions(ws.url(BASE_URL + s"v1/projects/${projectsId}/instances/${instancesId}:testIamPermissions").addQueryStringParameters("resource" -> resource.toString))
			}
			/** Gets the access control policy for an instance resource. Returns an empty policy if an instance exists but does not have a policy set. Authorization requires `spanner.instances.getIamPolicy` on resource. */
			class getIamPolicy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/spanner.admin""")
				/** Perform the request */
				def withGetIamPolicyRequest(body: Schema.GetIamPolicyRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
			}
			object getIamPolicy {
				def apply(projectsId :PlayApi, instancesId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): getIamPolicy = new getIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/instances/${instancesId}:getIamPolicy").addQueryStringParameters("resource" -> resource.toString))
			}
			/** Deletes an instance. Immediately upon completion of the request: &#42; Billing ceases for all of the instance's reserved resources. Soon afterward: &#42; The instance and &#42;all of its databases&#42; immediately and irrevocably disappear from the API. All data in the databases is permanently deleted. */
			class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/spanner.admin""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
			}
			object delete {
				def apply(projectsId :PlayApi, instancesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/instances/${instancesId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
			}
			/** Gets information about a particular instance. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Instance]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/spanner.admin""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Instance])
			}
			object get {
				def apply(projectsId :PlayApi, instancesId :PlayApi, name: String, fieldMask: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/instances/${instancesId}").addQueryStringParameters("name" -> name.toString, "fieldMask" -> fieldMask.toString))
				given Conversion[get, Future[Schema.Instance]] = (fun: get) => fun.apply()
			}
			/** Moves an instance to the target instance configuration. You can use the returned long-running operation to track the progress of moving the instance. `MoveInstance` returns `FAILED_PRECONDITION` if the instance meets any of the following criteria: &#42; Is undergoing a move to a different instance configuration &#42; Has backups &#42; Has an ongoing update &#42; Contains any CMEK-enabled databases &#42; Is a free trial instance While the operation is pending: &#42; All other attempts to modify the instance, including changes to its compute capacity, are rejected. &#42; The following database and backup admin operations are rejected: &#42; `DatabaseAdmin.CreateDatabase` &#42; `DatabaseAdmin.UpdateDatabaseDdl` (disabled if default_leader is specified in the request.) &#42; `DatabaseAdmin.RestoreDatabase` &#42; `DatabaseAdmin.CreateBackup` &#42; `DatabaseAdmin.CopyBackup` &#42; Both the source and target instance configurations are subject to hourly compute and storage charges. &#42; The instance might experience higher read-write latencies and a higher transaction abort rate. However, moving an instance doesn't cause any downtime. The returned long-running operation has a name of the format `/operations/` and can be used to track the move instance operation. The metadata field type is MoveInstanceMetadata. The response field type is Instance, if successful. Cancelling the operation sets its metadata's cancel_time. Cancellation is not immediate because it involves moving any data previously moved to the target instance configuration back to the original instance configuration. You can use this operation to track the progress of the cancellation. Upon successful completion of the cancellation, the operation terminates with `CANCELLED` status. If not cancelled, upon completion of the returned operation: &#42; The instance successfully moves to the target instance configuration. &#42; You are billed for compute and storage in target instance configuration. Authorization requires the `spanner.instances.update` permission on the resource instance. For more details, see [Move an instance](https://cloud.google.com/spanner/docs/move-instance). */
			class move(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/spanner.admin""")
				/** Perform the request */
				def withMoveInstanceRequest(body: Schema.MoveInstanceRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
			}
			object move {
				def apply(projectsId :PlayApi, instancesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): move = new move(ws.url(BASE_URL + s"v1/projects/${projectsId}/instances/${instancesId}:move").addQueryStringParameters("name" -> name.toString))
			}
			/** Creates an instance and begins preparing it to begin serving. The returned long-running operation can be used to track the progress of preparing the new instance. The instance name is assigned by the caller. If the named instance already exists, `CreateInstance` returns `ALREADY_EXISTS`. Immediately upon completion of this request: &#42; The instance is readable via the API, with all requested attributes but no allocated resources. Its state is `CREATING`. Until completion of the returned operation: &#42; Cancelling the operation renders the instance immediately unreadable via the API. &#42; The instance can be deleted. &#42; All other attempts to modify the instance are rejected. Upon completion of the returned operation: &#42; Billing for all successfully-allocated resources begins (some types may have lower than the requested levels). &#42; Databases can be created in the instance. &#42; The instance's allocated resource levels are readable via the API. &#42; The instance's state becomes `READY`. The returned long-running operation will have a name of the format `/operations/` and can be used to track creation of the instance. The metadata field type is CreateInstanceMetadata. The response field type is Instance, if successful. */
			class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/spanner.admin""")
				/** Perform the request */
				def withCreateInstanceRequest(body: Schema.CreateInstanceRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
			}
			object create {
				def apply(projectsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/instances").addQueryStringParameters("parent" -> parent.toString))
			}
			/** Sets the access control policy on an instance resource. Replaces any existing policy. Authorization requires `spanner.instances.setIamPolicy` on resource. */
			class setIamPolicy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/spanner.admin""")
				/** Perform the request */
				def withSetIamPolicyRequest(body: Schema.SetIamPolicyRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
			}
			object setIamPolicy {
				def apply(projectsId :PlayApi, instancesId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): setIamPolicy = new setIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/instances/${instancesId}:setIamPolicy").addQueryStringParameters("resource" -> resource.toString))
			}
			/** Updates an instance, and begins allocating or releasing resources as requested. The returned long-running operation can be used to track the progress of updating the instance. If the named instance does not exist, returns `NOT_FOUND`. Immediately upon completion of this request: &#42; For resource types for which a decrease in the instance's allocation has been requested, billing is based on the newly-requested level. Until completion of the returned operation: &#42; Cancelling the operation sets its metadata's cancel_time, and begins restoring resources to their pre-request values. The operation is guaranteed to succeed at undoing all resource changes, after which point it terminates with a `CANCELLED` status. &#42; All other attempts to modify the instance are rejected. &#42; Reading the instance via the API continues to give the pre-request resource levels. Upon completion of the returned operation: &#42; Billing begins for all successfully-allocated resources (some types may have lower than the requested levels). &#42; All newly-reserved resources are available for serving the instance's tables. &#42; The instance's new resource levels are readable via the API. The returned long-running operation will have a name of the format `/operations/` and can be used to track the instance modification. The metadata field type is UpdateInstanceMetadata. The response field type is Instance, if successful. Authorization requires `spanner.instances.update` permission on the resource name. */
			class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/spanner.admin""")
				/** Perform the request */
				def withUpdateInstanceRequest(body: Schema.UpdateInstanceRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Operation])
			}
			object patch {
				def apply(projectsId :PlayApi, instancesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/instances/${instancesId}").addQueryStringParameters("name" -> name.toString))
			}
			/** Lists all instances in the given project. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListInstancesResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/spanner.admin""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListInstancesResponse])
			}
			object list {
				def apply(projectsId :PlayApi, parent: String, pageSize: Int, pageToken: String, filter: String, instanceDeadline: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/instances").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "filter" -> filter.toString, "instanceDeadline" -> instanceDeadline.toString))
				given Conversion[list, Future[Schema.ListInstancesResponse]] = (fun: list) => fun.apply()
			}
			object backups {
				/** Returns permissions that the caller has on the specified database or backup resource. Attempting this RPC on a non-existent Cloud Spanner database will result in a NOT_FOUND error if the user has `spanner.databases.list` permission on the containing Cloud Spanner instance. Otherwise returns an empty set of permissions. Calling this method on a backup that does not exist will result in a NOT_FOUND error if the user has `spanner.backups.list` permission on the containing instance. Calling this method on a backup schedule that does not exist will result in a NOT_FOUND error if the user has `spanner.backupSchedules.list` permission on the containing database. */
				class testIamPermissions(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/spanner.admin""")
					/** Perform the request */
					def withTestIamPermissionsRequest(body: Schema.TestIamPermissionsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.TestIamPermissionsResponse])
				}
				object testIamPermissions {
					def apply(projectsId :PlayApi, instancesId :PlayApi, backupsId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): testIamPermissions = new testIamPermissions(ws.url(BASE_URL + s"v1/projects/${projectsId}/instances/${instancesId}/backups/${backupsId}:testIamPermissions").addQueryStringParameters("resource" -> resource.toString))
				}
				/** Gets the access control policy for a database or backup resource. Returns an empty policy if a database or backup exists but does not have a policy set. Authorization requires `spanner.databases.getIamPolicy` permission on resource. For backups, authorization requires `spanner.backups.getIamPolicy` permission on resource. For backup schedules, authorization requires `spanner.backupSchedules.getIamPolicy` permission on resource. */
				class getIamPolicy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/spanner.admin""")
					/** Perform the request */
					def withGetIamPolicyRequest(body: Schema.GetIamPolicyRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
				}
				object getIamPolicy {
					def apply(projectsId :PlayApi, instancesId :PlayApi, backupsId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): getIamPolicy = new getIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/instances/${instancesId}/backups/${backupsId}:getIamPolicy").addQueryStringParameters("resource" -> resource.toString))
				}
				/** Deletes a pending or completed Backup. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/spanner.admin""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
				}
				object delete {
					def apply(projectsId :PlayApi, instancesId :PlayApi, backupsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/instances/${instancesId}/backups/${backupsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
				}
				/** Starts copying a Cloud Spanner Backup. The returned backup long-running operation will have a name of the format `projects//instances//backups//operations/` and can be used to track copying of the backup. The operation is associated with the destination backup. The metadata field type is CopyBackupMetadata. The response field type is Backup, if successful. Cancelling the returned operation will stop the copying and delete the destination backup. Concurrent CopyBackup requests can run on the same source backup. */
				class copy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/spanner.admin""")
					/** Perform the request */
					def withCopyBackupRequest(body: Schema.CopyBackupRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object copy {
					def apply(projectsId :PlayApi, instancesId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): copy = new copy(ws.url(BASE_URL + s"v1/projects/${projectsId}/instances/${instancesId}/backups:copy").addQueryStringParameters("parent" -> parent.toString))
				}
				/** Gets metadata on a pending or completed Backup. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Backup]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/spanner.admin""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Backup])
				}
				object get {
					def apply(projectsId :PlayApi, instancesId :PlayApi, backupsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/instances/${instancesId}/backups/${backupsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.Backup]] = (fun: get) => fun.apply()
				}
				/** Updates a pending or completed Backup. */
				class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/spanner.admin""")
					/** Perform the request */
					def withBackup(body: Schema.Backup) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Backup])
				}
				object patch {
					def apply(projectsId :PlayApi, instancesId :PlayApi, backupsId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/instances/${instancesId}/backups/${backupsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
				}
				/** Lists completed and pending backups. Backups returned are ordered by `create_time` in descending order, starting from the most recent `create_time`. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListBackupsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/spanner.admin""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListBackupsResponse])
				}
				object list {
					def apply(projectsId :PlayApi, instancesId :PlayApi, parent: String, filter: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/instances/${instancesId}/backups").addQueryStringParameters("parent" -> parent.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListBackupsResponse]] = (fun: list) => fun.apply()
				}
				/** Starts creating a new Cloud Spanner Backup. The returned backup long-running operation will have a name of the format `projects//instances//backups//operations/` and can be used to track creation of the backup. The metadata field type is CreateBackupMetadata. The response field type is Backup, if successful. Cancelling the returned operation will stop the creation and delete the backup. There can be only one pending backup creation per database. Backup creation of different databases can run concurrently. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/spanner.admin""")
					/** Optional. The Cloud KMS key that will be used to protect the backup. This field should be set only when encryption_type is `CUSTOMER_MANAGED_ENCRYPTION`. Values are of the form `projects//locations//keyRings//cryptoKeys/`. */
					def withEncryptionConfigKmsKeyName(encryptionConfigKmsKeyName: String) = new create(req.addQueryStringParameters("encryptionConfig.kmsKeyName" -> encryptionConfigKmsKeyName.toString))
					/** Optional. Specifies the KMS configuration for the one or more keys used to protect the backup. Values are of the form `projects//locations//keyRings//cryptoKeys/`. The keys referenced by `kms_key_names` must fully cover all regions of the backup's instance configuration. Some examples: &#42; For regional (single-region) instance configurations, specify a regional location KMS key. &#42; For multi-region instance configurations of type `GOOGLE_MANAGED`, either specify a multi-region location KMS key or multiple regional location KMS keys that cover all regions in the instance configuration. &#42; For an instance configuration of type `USER_MANAGED`, specify only regional location KMS keys to cover each region in the instance configuration. Multi-region location KMS keys aren't supported for `USER_MANAGED` type instance configurations. */
					def withEncryptionConfigKmsKeyNames(encryptionConfigKmsKeyNames: String) = new create(req.addQueryStringParameters("encryptionConfig.kmsKeyNames" -> encryptionConfigKmsKeyNames.toString))
					/** Perform the request */
					def withBackup(body: Schema.Backup) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object create {
					def apply(projectsId :PlayApi, instancesId :PlayApi, parent: String, backupId: String, encryptionConfigEncryptionType: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/instances/${instancesId}/backups").addQueryStringParameters("parent" -> parent.toString, "backupId" -> backupId.toString, "encryptionConfig.encryptionType" -> encryptionConfigEncryptionType.toString))
				}
				/** Sets the access control policy on a database or backup resource. Replaces any existing policy. Authorization requires `spanner.databases.setIamPolicy` permission on resource. For backups, authorization requires `spanner.backups.setIamPolicy` permission on resource. For backup schedules, authorization requires `spanner.backupSchedules.setIamPolicy` permission on resource. */
				class setIamPolicy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/spanner.admin""")
					/** Perform the request */
					def withSetIamPolicyRequest(body: Schema.SetIamPolicyRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
				}
				object setIamPolicy {
					def apply(projectsId :PlayApi, instancesId :PlayApi, backupsId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): setIamPolicy = new setIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/instances/${instancesId}/backups/${backupsId}:setIamPolicy").addQueryStringParameters("resource" -> resource.toString))
				}
				object operations {
					/** Lists operations that match the specified filter in the request. If the server doesn't support this method, it returns `UNIMPLEMENTED`. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListOperationsResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/spanner.admin""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListOperationsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, instancesId :PlayApi, backupsId :PlayApi, name: String, filter: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/instances/${instancesId}/backups/${backupsId}/operations").addQueryStringParameters("name" -> name.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
						given Conversion[list, Future[Schema.ListOperationsResponse]] = (fun: list) => fun.apply()
					}
					/** Gets the latest state of a long-running operation. Clients can use this method to poll the operation result at intervals as recommended by the API service. */
					class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/spanner.admin""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Operation])
					}
					object get {
						def apply(projectsId :PlayApi, instancesId :PlayApi, backupsId :PlayApi, operationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/instances/${instancesId}/backups/${backupsId}/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.Operation]] = (fun: get) => fun.apply()
					}
					/** Deletes a long-running operation. This method indicates that the client is no longer interested in the operation result. It does not cancel the operation. If the server doesn't support this method, it returns `google.rpc.Code.UNIMPLEMENTED`. */
					class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/spanner.admin""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
					}
					object delete {
						def apply(projectsId :PlayApi, instancesId :PlayApi, backupsId :PlayApi, operationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/instances/${instancesId}/backups/${backupsId}/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
					}
					/** Starts asynchronous cancellation on a long-running operation. The server makes a best effort to cancel the operation, but success is not guaranteed. If the server doesn't support this method, it returns `google.rpc.Code.UNIMPLEMENTED`. Clients can use Operations.GetOperation or other methods to check whether the cancellation succeeded or whether the operation completed despite cancellation. On successful cancellation, the operation is not deleted; instead, it becomes an operation with an Operation.error value with a google.rpc.Status.code of 1, corresponding to `Code.CANCELLED`. */
					class cancel(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/spanner.admin""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("POST")).map(_.json.as[Schema.Empty])
					}
					object cancel {
						def apply(projectsId :PlayApi, instancesId :PlayApi, backupsId :PlayApi, operationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): cancel = new cancel(ws.url(BASE_URL + s"v1/projects/${projectsId}/instances/${instancesId}/backups/${backupsId}/operations/${operationsId}:cancel").addQueryStringParameters("name" -> name.toString))
						given Conversion[cancel, Future[Schema.Empty]] = (fun: cancel) => fun.apply()
					}
				}
			}
			object instancePartitions {
				/** Creates an instance partition and begins preparing it to be used. The returned long-running operation can be used to track the progress of preparing the new instance partition. The instance partition name is assigned by the caller. If the named instance partition already exists, `CreateInstancePartition` returns `ALREADY_EXISTS`. Immediately upon completion of this request: &#42; The instance partition is readable via the API, with all requested attributes but no allocated resources. Its state is `CREATING`. Until completion of the returned operation: &#42; Cancelling the operation renders the instance partition immediately unreadable via the API. &#42; The instance partition can be deleted. &#42; All other attempts to modify the instance partition are rejected. Upon completion of the returned operation: &#42; Billing for all successfully-allocated resources begins (some types may have lower than the requested levels). &#42; Databases can start using this instance partition. &#42; The instance partition's allocated resource levels are readable via the API. &#42; The instance partition's state becomes `READY`. The returned long-running operation will have a name of the format `/operations/` and can be used to track creation of the instance partition. The metadata field type is CreateInstancePartitionMetadata. The response field type is InstancePartition, if successful. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/spanner.admin""")
					/** Perform the request */
					def withCreateInstancePartitionRequest(body: Schema.CreateInstancePartitionRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object create {
					def apply(projectsId :PlayApi, instancesId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/instances/${instancesId}/instancePartitions").addQueryStringParameters("parent" -> parent.toString))
				}
				/** Deletes an existing instance partition. Requires that the instance partition is not used by any database or backup and is not the default instance partition of an instance. Authorization requires `spanner.instancePartitions.delete` permission on the resource name. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/spanner.admin""")
					/** Optional. If not empty, the API only deletes the instance partition when the etag provided matches the current status of the requested instance partition. Otherwise, deletes the instance partition without checking the current status of the requested instance partition. */
					def withEtag(etag: String) = new delete(req.addQueryStringParameters("etag" -> etag.toString))
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
				}
				object delete {
					def apply(projectsId :PlayApi, instancesId :PlayApi, instancePartitionsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/instances/${instancesId}/instancePartitions/${instancePartitionsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
				}
				/** Gets information about a particular instance partition. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.InstancePartition]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/spanner.admin""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.InstancePartition])
				}
				object get {
					def apply(projectsId :PlayApi, instancesId :PlayApi, instancePartitionsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/instances/${instancesId}/instancePartitions/${instancePartitionsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.InstancePartition]] = (fun: get) => fun.apply()
				}
				/** Updates an instance partition, and begins allocating or releasing resources as requested. The returned long-running operation can be used to track the progress of updating the instance partition. If the named instance partition does not exist, returns `NOT_FOUND`. Immediately upon completion of this request: &#42; For resource types for which a decrease in the instance partition's allocation has been requested, billing is based on the newly-requested level. Until completion of the returned operation: &#42; Cancelling the operation sets its metadata's cancel_time, and begins restoring resources to their pre-request values. The operation is guaranteed to succeed at undoing all resource changes, after which point it terminates with a `CANCELLED` status. &#42; All other attempts to modify the instance partition are rejected. &#42; Reading the instance partition via the API continues to give the pre-request resource levels. Upon completion of the returned operation: &#42; Billing begins for all successfully-allocated resources (some types may have lower than the requested levels). &#42; All newly-reserved resources are available for serving the instance partition's tables. &#42; The instance partition's new resource levels are readable via the API. The returned long-running operation will have a name of the format `/operations/` and can be used to track the instance partition modification. The metadata field type is UpdateInstancePartitionMetadata. The response field type is InstancePartition, if successful. Authorization requires `spanner.instancePartitions.update` permission on the resource name. */
				class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/spanner.admin""")
					/** Perform the request */
					def withUpdateInstancePartitionRequest(body: Schema.UpdateInstancePartitionRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Operation])
				}
				object patch {
					def apply(projectsId :PlayApi, instancesId :PlayApi, instancePartitionsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/instances/${instancesId}/instancePartitions/${instancePartitionsId}").addQueryStringParameters("name" -> name.toString))
				}
				/** Lists all instance partitions for the given instance. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListInstancePartitionsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/spanner.admin""")
					/** Optional. Deadline used while retrieving metadata for instance partitions. Instance partitions whose metadata cannot be retrieved within this deadline will be added to unreachable in ListInstancePartitionsResponse.<br>Format: google-datetime */
					def withInstancePartitionDeadline(instancePartitionDeadline: String) = new list(req.addQueryStringParameters("instancePartitionDeadline" -> instancePartitionDeadline.toString))
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListInstancePartitionsResponse])
				}
				object list {
					def apply(projectsId :PlayApi, instancesId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/instances/${instancesId}/instancePartitions").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListInstancePartitionsResponse]] = (fun: list) => fun.apply()
				}
				object operations {
					/** Lists operations that match the specified filter in the request. If the server doesn't support this method, it returns `UNIMPLEMENTED`. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListOperationsResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/spanner.admin""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListOperationsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, instancesId :PlayApi, instancePartitionsId :PlayApi, name: String, filter: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/instances/${instancesId}/instancePartitions/${instancePartitionsId}/operations").addQueryStringParameters("name" -> name.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
						given Conversion[list, Future[Schema.ListOperationsResponse]] = (fun: list) => fun.apply()
					}
					/** Gets the latest state of a long-running operation. Clients can use this method to poll the operation result at intervals as recommended by the API service. */
					class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/spanner.admin""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Operation])
					}
					object get {
						def apply(projectsId :PlayApi, instancesId :PlayApi, instancePartitionsId :PlayApi, operationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/instances/${instancesId}/instancePartitions/${instancePartitionsId}/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.Operation]] = (fun: get) => fun.apply()
					}
					/** Deletes a long-running operation. This method indicates that the client is no longer interested in the operation result. It does not cancel the operation. If the server doesn't support this method, it returns `google.rpc.Code.UNIMPLEMENTED`. */
					class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/spanner.admin""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
					}
					object delete {
						def apply(projectsId :PlayApi, instancesId :PlayApi, instancePartitionsId :PlayApi, operationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/instances/${instancesId}/instancePartitions/${instancePartitionsId}/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
					}
					/** Starts asynchronous cancellation on a long-running operation. The server makes a best effort to cancel the operation, but success is not guaranteed. If the server doesn't support this method, it returns `google.rpc.Code.UNIMPLEMENTED`. Clients can use Operations.GetOperation or other methods to check whether the cancellation succeeded or whether the operation completed despite cancellation. On successful cancellation, the operation is not deleted; instead, it becomes an operation with an Operation.error value with a google.rpc.Status.code of 1, corresponding to `Code.CANCELLED`. */
					class cancel(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/spanner.admin""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("POST")).map(_.json.as[Schema.Empty])
					}
					object cancel {
						def apply(projectsId :PlayApi, instancesId :PlayApi, instancePartitionsId :PlayApi, operationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): cancel = new cancel(ws.url(BASE_URL + s"v1/projects/${projectsId}/instances/${instancesId}/instancePartitions/${instancePartitionsId}/operations/${operationsId}:cancel").addQueryStringParameters("name" -> name.toString))
						given Conversion[cancel, Future[Schema.Empty]] = (fun: cancel) => fun.apply()
					}
				}
			}
			object databases {
				/** Returns permissions that the caller has on the specified database or backup resource. Attempting this RPC on a non-existent Cloud Spanner database will result in a NOT_FOUND error if the user has `spanner.databases.list` permission on the containing Cloud Spanner instance. Otherwise returns an empty set of permissions. Calling this method on a backup that does not exist will result in a NOT_FOUND error if the user has `spanner.backups.list` permission on the containing instance. Calling this method on a backup schedule that does not exist will result in a NOT_FOUND error if the user has `spanner.backupSchedules.list` permission on the containing database. */
				class testIamPermissions(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/spanner.admin""")
					/** Perform the request */
					def withTestIamPermissionsRequest(body: Schema.TestIamPermissionsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.TestIamPermissionsResponse])
				}
				object testIamPermissions {
					def apply(projectsId :PlayApi, instancesId :PlayApi, databasesId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): testIamPermissions = new testIamPermissions(ws.url(BASE_URL + s"v1/projects/${projectsId}/instances/${instancesId}/databases/${databasesId}:testIamPermissions").addQueryStringParameters("resource" -> resource.toString))
				}
				/** Returns the schema of a Cloud Spanner database as a list of formatted DDL statements. This method does not show pending schema updates, those may be queried using the Operations API. */
				class getDdl(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GetDatabaseDdlResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/spanner.admin""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GetDatabaseDdlResponse])
				}
				object getDdl {
					def apply(projectsId :PlayApi, instancesId :PlayApi, databasesId :PlayApi, database: String)(using signer: RequestSigner, ec: ExecutionContext): getDdl = new getDdl(ws.url(BASE_URL + s"v1/projects/${projectsId}/instances/${instancesId}/databases/${databasesId}/ddl").addQueryStringParameters("database" -> database.toString))
					given Conversion[getDdl, Future[Schema.GetDatabaseDdlResponse]] = (fun: getDdl) => fun.apply()
				}
				/** Create a new database by restoring from a completed backup. The new database must be in the same project and in an instance with the same instance configuration as the instance containing the backup. The returned database long-running operation has a name of the format `projects//instances//databases//operations/`, and can be used to track the progress of the operation, and to cancel it. The metadata field type is RestoreDatabaseMetadata. The response type is Database, if successful. Cancelling the returned operation will stop the restore and delete the database. There can be only one database being restored into an instance at a time. Once the restore operation completes, a new restore operation can be initiated, without waiting for the optimize operation associated with the first restore to complete. */
				class restore(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/spanner.admin""")
					/** Perform the request */
					def withRestoreDatabaseRequest(body: Schema.RestoreDatabaseRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object restore {
					def apply(projectsId :PlayApi, instancesId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): restore = new restore(ws.url(BASE_URL + s"v1/projects/${projectsId}/instances/${instancesId}/databases:restore").addQueryStringParameters("parent" -> parent.toString))
				}
				/** Drops (aka deletes) a Cloud Spanner database. Completed backups for the database will be retained according to their `expire_time`. Note: Cloud Spanner might continue to accept requests for a few seconds after the database has been deleted. */
				class dropDatabase(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/spanner.admin""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
				}
				object dropDatabase {
					def apply(projectsId :PlayApi, instancesId :PlayApi, databasesId :PlayApi, database: String)(using signer: RequestSigner, ec: ExecutionContext): dropDatabase = new dropDatabase(ws.url(BASE_URL + s"v1/projects/${projectsId}/instances/${instancesId}/databases/${databasesId}").addQueryStringParameters("database" -> database.toString))
					given Conversion[dropDatabase, Future[Schema.Empty]] = (fun: dropDatabase) => fun.apply()
				}
				/** Gets the access control policy for a database or backup resource. Returns an empty policy if a database or backup exists but does not have a policy set. Authorization requires `spanner.databases.getIamPolicy` permission on resource. For backups, authorization requires `spanner.backups.getIamPolicy` permission on resource. For backup schedules, authorization requires `spanner.backupSchedules.getIamPolicy` permission on resource. */
				class getIamPolicy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/spanner.admin""")
					/** Perform the request */
					def withGetIamPolicyRequest(body: Schema.GetIamPolicyRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
				}
				object getIamPolicy {
					def apply(projectsId :PlayApi, instancesId :PlayApi, databasesId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): getIamPolicy = new getIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/instances/${instancesId}/databases/${databasesId}:getIamPolicy").addQueryStringParameters("resource" -> resource.toString))
				}
				/** Request a specific scan with Database-specific data for Cloud Key Visualizer. */
				class getScans(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Scan]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/spanner.data""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Scan])
				}
				object getScans {
					def apply(projectsId :PlayApi, instancesId :PlayApi, databasesId :PlayApi, name: String, view: String, startTime: String, endTime: String)(using signer: RequestSigner, ec: ExecutionContext): getScans = new getScans(ws.url(BASE_URL + s"v1/projects/${projectsId}/instances/${instancesId}/databases/${databasesId}/scans").addQueryStringParameters("name" -> name.toString, "view" -> view.toString, "startTime" -> startTime.toString, "endTime" -> endTime.toString))
					given Conversion[getScans, Future[Schema.Scan]] = (fun: getScans) => fun.apply()
				}
				/** `ChangeQuorum` is strictly restricted to databases that use dual-region instance configurations. Initiates a background operation to change the quorum of a database from dual-region mode to single-region mode or vice versa. The returned long-running operation has a name of the format `projects//instances//databases//operations/` and can be used to track execution of the `ChangeQuorum`. The metadata field type is ChangeQuorumMetadata. Authorization requires `spanner.databases.changequorum` permission on the resource database. */
				class changequorum(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/spanner.admin""")
					/** Perform the request */
					def withChangeQuorumRequest(body: Schema.ChangeQuorumRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object changequorum {
					def apply(projectsId :PlayApi, instancesId :PlayApi, databasesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): changequorum = new changequorum(ws.url(BASE_URL + s"v1/projects/${projectsId}/instances/${instancesId}/databases/${databasesId}:changequorum").addQueryStringParameters("name" -> name.toString))
				}
				/** Gets the state of a Cloud Spanner database. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Database]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/spanner.admin""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Database])
				}
				object get {
					def apply(projectsId :PlayApi, instancesId :PlayApi, databasesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/instances/${instancesId}/databases/${databasesId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.Database]] = (fun: get) => fun.apply()
				}
				/** Updates the schema of a Cloud Spanner database by creating/altering/dropping tables, columns, indexes, etc. The returned long-running operation will have a name of the format `/operations/` and can be used to track execution of the schema change(s). The metadata field type is UpdateDatabaseDdlMetadata. The operation has no response. */
				class updateDdl(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/spanner.admin""")
					/** Perform the request */
					def withUpdateDatabaseDdlRequest(body: Schema.UpdateDatabaseDdlRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Operation])
				}
				object updateDdl {
					def apply(projectsId :PlayApi, instancesId :PlayApi, databasesId :PlayApi, database: String)(using signer: RequestSigner, ec: ExecutionContext): updateDdl = new updateDdl(ws.url(BASE_URL + s"v1/projects/${projectsId}/instances/${instancesId}/databases/${databasesId}/ddl").addQueryStringParameters("database" -> database.toString))
				}
				/** Updates a Cloud Spanner database. The returned long-running operation can be used to track the progress of updating the database. If the named database does not exist, returns `NOT_FOUND`. While the operation is pending: &#42; The database's reconciling field is set to true. &#42; Cancelling the operation is best-effort. If the cancellation succeeds, the operation metadata's cancel_time is set, the updates are reverted, and the operation terminates with a `CANCELLED` status. &#42; New UpdateDatabase requests will return a `FAILED_PRECONDITION` error until the pending operation is done (returns successfully or with error). &#42; Reading the database via the API continues to give the pre-request values. Upon completion of the returned operation: &#42; The new values are in effect and readable via the API. &#42; The database's reconciling field becomes false. The returned long-running operation will have a name of the format `projects//instances//databases//operations/` and can be used to track the database modification. The metadata field type is UpdateDatabaseMetadata. The response field type is Database, if successful. */
				class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/spanner.admin""")
					/** Perform the request */
					def withDatabase(body: Schema.Database) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Operation])
				}
				object patch {
					def apply(projectsId :PlayApi, instancesId :PlayApi, databasesId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/instances/${instancesId}/databases/${databasesId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
				}
				/** Lists Cloud Spanner databases. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListDatabasesResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/spanner.admin""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListDatabasesResponse])
				}
				object list {
					def apply(projectsId :PlayApi, instancesId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/instances/${instancesId}/databases").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListDatabasesResponse]] = (fun: list) => fun.apply()
				}
				/** Creates a new Spanner database and starts to prepare it for serving. The returned long-running operation will have a name of the format `/operations/` and can be used to track preparation of the database. The metadata field type is CreateDatabaseMetadata. The response field type is Database, if successful. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/spanner.admin""")
					/** Perform the request */
					def withCreateDatabaseRequest(body: Schema.CreateDatabaseRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object create {
					def apply(projectsId :PlayApi, instancesId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/instances/${instancesId}/databases").addQueryStringParameters("parent" -> parent.toString))
				}
				/** Sets the access control policy on a database or backup resource. Replaces any existing policy. Authorization requires `spanner.databases.setIamPolicy` permission on resource. For backups, authorization requires `spanner.backups.setIamPolicy` permission on resource. For backup schedules, authorization requires `spanner.backupSchedules.setIamPolicy` permission on resource. */
				class setIamPolicy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/spanner.admin""")
					/** Perform the request */
					def withSetIamPolicyRequest(body: Schema.SetIamPolicyRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
				}
				object setIamPolicy {
					def apply(projectsId :PlayApi, instancesId :PlayApi, databasesId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): setIamPolicy = new setIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/instances/${instancesId}/databases/${databasesId}:setIamPolicy").addQueryStringParameters("resource" -> resource.toString))
				}
				object operations {
					/** Lists operations that match the specified filter in the request. If the server doesn't support this method, it returns `UNIMPLEMENTED`. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListOperationsResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/spanner.admin""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListOperationsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, instancesId :PlayApi, databasesId :PlayApi, name: String, filter: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/instances/${instancesId}/databases/${databasesId}/operations").addQueryStringParameters("name" -> name.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
						given Conversion[list, Future[Schema.ListOperationsResponse]] = (fun: list) => fun.apply()
					}
					/** Gets the latest state of a long-running operation. Clients can use this method to poll the operation result at intervals as recommended by the API service. */
					class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/spanner.admin""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Operation])
					}
					object get {
						def apply(projectsId :PlayApi, instancesId :PlayApi, databasesId :PlayApi, operationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/instances/${instancesId}/databases/${databasesId}/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.Operation]] = (fun: get) => fun.apply()
					}
					/** Deletes a long-running operation. This method indicates that the client is no longer interested in the operation result. It does not cancel the operation. If the server doesn't support this method, it returns `google.rpc.Code.UNIMPLEMENTED`. */
					class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/spanner.admin""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
					}
					object delete {
						def apply(projectsId :PlayApi, instancesId :PlayApi, databasesId :PlayApi, operationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/instances/${instancesId}/databases/${databasesId}/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
					}
					/** Starts asynchronous cancellation on a long-running operation. The server makes a best effort to cancel the operation, but success is not guaranteed. If the server doesn't support this method, it returns `google.rpc.Code.UNIMPLEMENTED`. Clients can use Operations.GetOperation or other methods to check whether the cancellation succeeded or whether the operation completed despite cancellation. On successful cancellation, the operation is not deleted; instead, it becomes an operation with an Operation.error value with a google.rpc.Status.code of 1, corresponding to `Code.CANCELLED`. */
					class cancel(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/spanner.admin""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("POST")).map(_.json.as[Schema.Empty])
					}
					object cancel {
						def apply(projectsId :PlayApi, instancesId :PlayApi, databasesId :PlayApi, operationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): cancel = new cancel(ws.url(BASE_URL + s"v1/projects/${projectsId}/instances/${instancesId}/databases/${databasesId}/operations/${operationsId}:cancel").addQueryStringParameters("name" -> name.toString))
						given Conversion[cancel, Future[Schema.Empty]] = (fun: cancel) => fun.apply()
					}
				}
				object sessions {
					/** Reads rows from the database using key lookups and scans, as a simple key/value style alternative to ExecuteSql. This method cannot be used to return a result set larger than 10 MiB; if the read matches more data than that, the read fails with a `FAILED_PRECONDITION` error. Reads inside read-write transactions might return `ABORTED`. If this occurs, the application should restart the transaction from the beginning. See Transaction for more details. Larger result sets can be yielded in streaming fashion by calling StreamingRead instead. */
					class read(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/spanner.data""")
						/** Perform the request */
						def withReadRequest(body: Schema.ReadRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.ResultSet])
					}
					object read {
						def apply(projectsId :PlayApi, instancesId :PlayApi, databasesId :PlayApi, sessionsId :PlayApi, session: String)(using signer: RequestSigner, ec: ExecutionContext): read = new read(ws.url(BASE_URL + s"v1/projects/${projectsId}/instances/${instancesId}/databases/${databasesId}/sessions/${sessionsId}:read").addQueryStringParameters("session" -> session.toString))
					}
					/** Commits a transaction. The request includes the mutations to be applied to rows in the database. `Commit` might return an `ABORTED` error. This can occur at any time; commonly, the cause is conflicts with concurrent transactions. However, it can also happen for a variety of other reasons. If `Commit` returns `ABORTED`, the caller should re-attempt the transaction from the beginning, re-using the same session. On very rare occasions, `Commit` might return `UNKNOWN`. This can happen, for example, if the client job experiences a 1+ hour networking failure. At that point, Cloud Spanner has lost track of the transaction outcome and we recommend that you perform another read from the database to see the state of things as they are now. */
					class commit(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/spanner.data""")
						/** Perform the request */
						def withCommitRequest(body: Schema.CommitRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.CommitResponse])
					}
					object commit {
						def apply(projectsId :PlayApi, instancesId :PlayApi, databasesId :PlayApi, sessionsId :PlayApi, session: String)(using signer: RequestSigner, ec: ExecutionContext): commit = new commit(ws.url(BASE_URL + s"v1/projects/${projectsId}/instances/${instancesId}/databases/${databasesId}/sessions/${sessionsId}:commit").addQueryStringParameters("session" -> session.toString))
					}
					/** Creates a new session. A session can be used to perform transactions that read and/or modify data in a Cloud Spanner database. Sessions are meant to be reused for many consecutive transactions. Sessions can only execute one transaction at a time. To execute multiple concurrent read-write/write-only transactions, create multiple sessions. Note that standalone reads and queries use a transaction internally, and count toward the one transaction limit. Active sessions use additional server resources, so it is a good idea to delete idle and unneeded sessions. Aside from explicit deletes, Cloud Spanner may delete sessions for which no operations are sent for more than an hour. If a session is deleted, requests to it return `NOT_FOUND`. Idle sessions can be kept alive by sending a trivial SQL query periodically, e.g., `"SELECT 1"`. */
					class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/spanner.data""")
						/** Perform the request */
						def withCreateSessionRequest(body: Schema.CreateSessionRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Session])
					}
					object create {
						def apply(projectsId :PlayApi, instancesId :PlayApi, databasesId :PlayApi, database: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/instances/${instancesId}/databases/${databasesId}/sessions").addQueryStringParameters("database" -> database.toString))
					}
					/** Begins a new transaction. This step can often be skipped: Read, ExecuteSql and Commit can begin a new transaction as a side-effect. */
					class beginTransaction(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/spanner.data""")
						/** Perform the request */
						def withBeginTransactionRequest(body: Schema.BeginTransactionRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Transaction])
					}
					object beginTransaction {
						def apply(projectsId :PlayApi, instancesId :PlayApi, databasesId :PlayApi, sessionsId :PlayApi, session: String)(using signer: RequestSigner, ec: ExecutionContext): beginTransaction = new beginTransaction(ws.url(BASE_URL + s"v1/projects/${projectsId}/instances/${instancesId}/databases/${databasesId}/sessions/${sessionsId}:beginTransaction").addQueryStringParameters("session" -> session.toString))
					}
					/** Rolls back a transaction, releasing any locks it holds. It is a good idea to call this for any transaction that includes one or more Read or ExecuteSql requests and ultimately decides not to commit. `Rollback` returns `OK` if it successfully aborts the transaction, the transaction was already aborted, or the transaction is not found. `Rollback` never returns `ABORTED`. */
					class rollback(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/spanner.data""")
						/** Perform the request */
						def withRollbackRequest(body: Schema.RollbackRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Empty])
					}
					object rollback {
						def apply(projectsId :PlayApi, instancesId :PlayApi, databasesId :PlayApi, sessionsId :PlayApi, session: String)(using signer: RequestSigner, ec: ExecutionContext): rollback = new rollback(ws.url(BASE_URL + s"v1/projects/${projectsId}/instances/${instancesId}/databases/${databasesId}/sessions/${sessionsId}:rollback").addQueryStringParameters("session" -> session.toString))
					}
					/** Creates a set of partition tokens that can be used to execute a query operation in parallel. Each of the returned partition tokens can be used by ExecuteStreamingSql to specify a subset of the query result to read. The same session and read-only transaction must be used by the PartitionQueryRequest used to create the partition tokens and the ExecuteSqlRequests that use the partition tokens. Partition tokens become invalid when the session used to create them is deleted, is idle for too long, begins a new transaction, or becomes too old. When any of these happen, it is not possible to resume the query, and the whole operation must be restarted from the beginning. */
					class partitionQuery(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/spanner.data""")
						/** Perform the request */
						def withPartitionQueryRequest(body: Schema.PartitionQueryRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.PartitionResponse])
					}
					object partitionQuery {
						def apply(projectsId :PlayApi, instancesId :PlayApi, databasesId :PlayApi, sessionsId :PlayApi, session: String)(using signer: RequestSigner, ec: ExecutionContext): partitionQuery = new partitionQuery(ws.url(BASE_URL + s"v1/projects/${projectsId}/instances/${instancesId}/databases/${databasesId}/sessions/${sessionsId}:partitionQuery").addQueryStringParameters("session" -> session.toString))
					}
					/** Executes an SQL statement, returning all results in a single reply. This method cannot be used to return a result set larger than 10 MiB; if the query yields more data than that, the query fails with a `FAILED_PRECONDITION` error. Operations inside read-write transactions might return `ABORTED`. If this occurs, the application should restart the transaction from the beginning. See Transaction for more details. Larger result sets can be fetched in streaming fashion by calling ExecuteStreamingSql instead. */
					class executeSql(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/spanner.data""")
						/** Perform the request */
						def withExecuteSqlRequest(body: Schema.ExecuteSqlRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.ResultSet])
					}
					object executeSql {
						def apply(projectsId :PlayApi, instancesId :PlayApi, databasesId :PlayApi, sessionsId :PlayApi, session: String)(using signer: RequestSigner, ec: ExecutionContext): executeSql = new executeSql(ws.url(BASE_URL + s"v1/projects/${projectsId}/instances/${instancesId}/databases/${databasesId}/sessions/${sessionsId}:executeSql").addQueryStringParameters("session" -> session.toString))
					}
					/** Gets a session. Returns `NOT_FOUND` if the session does not exist. This is mainly useful for determining whether a session is still alive. */
					class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Session]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/spanner.data""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Session])
					}
					object get {
						def apply(projectsId :PlayApi, instancesId :PlayApi, databasesId :PlayApi, sessionsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/instances/${instancesId}/databases/${databasesId}/sessions/${sessionsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.Session]] = (fun: get) => fun.apply()
					}
					/** Like ExecuteSql, except returns the result set as a stream. Unlike ExecuteSql, there is no limit on the size of the returned result set. However, no individual row in the result set can exceed 100 MiB, and no column value can exceed 10 MiB. */
					class executeStreamingSql(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/spanner.data""")
						/** Perform the request */
						def withExecuteSqlRequest(body: Schema.ExecuteSqlRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.PartialResultSet])
					}
					object executeStreamingSql {
						def apply(projectsId :PlayApi, instancesId :PlayApi, databasesId :PlayApi, sessionsId :PlayApi, session: String)(using signer: RequestSigner, ec: ExecutionContext): executeStreamingSql = new executeStreamingSql(ws.url(BASE_URL + s"v1/projects/${projectsId}/instances/${instancesId}/databases/${databasesId}/sessions/${sessionsId}:executeStreamingSql").addQueryStringParameters("session" -> session.toString))
					}
					/** Lists all sessions in a given database. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListSessionsResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/spanner.data""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListSessionsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, instancesId :PlayApi, databasesId :PlayApi, database: String, pageSize: Int, pageToken: String, filter: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/instances/${instancesId}/databases/${databasesId}/sessions").addQueryStringParameters("database" -> database.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "filter" -> filter.toString))
						given Conversion[list, Future[Schema.ListSessionsResponse]] = (fun: list) => fun.apply()
					}
					/** Batches the supplied mutation groups in a collection of efficient transactions. All mutations in a group are committed atomically. However, mutations across groups can be committed non-atomically in an unspecified order and thus, they must be independent of each other. Partial failure is possible, i.e., some groups may have been committed successfully, while some may have failed. The results of individual batches are streamed into the response as the batches are applied. BatchWrite requests are not replay protected, meaning that each mutation group may be applied more than once. Replays of non-idempotent mutations may have undesirable effects. For example, replays of an insert mutation may produce an already exists error or if you use generated or commit timestamp-based keys, it may result in additional rows being added to the mutation's table. We recommend structuring your mutation groups to be idempotent to avoid this issue. */
					class batchWrite(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/spanner.data""")
						/** Perform the request */
						def withBatchWriteRequest(body: Schema.BatchWriteRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.BatchWriteResponse])
					}
					object batchWrite {
						def apply(projectsId :PlayApi, instancesId :PlayApi, databasesId :PlayApi, sessionsId :PlayApi, session: String)(using signer: RequestSigner, ec: ExecutionContext): batchWrite = new batchWrite(ws.url(BASE_URL + s"v1/projects/${projectsId}/instances/${instancesId}/databases/${databasesId}/sessions/${sessionsId}:batchWrite").addQueryStringParameters("session" -> session.toString))
					}
					/** Creates a set of partition tokens that can be used to execute a read operation in parallel. Each of the returned partition tokens can be used by StreamingRead to specify a subset of the read result to read. The same session and read-only transaction must be used by the PartitionReadRequest used to create the partition tokens and the ReadRequests that use the partition tokens. There are no ordering guarantees on rows returned among the returned partition tokens, or even within each individual StreamingRead call issued with a partition_token. Partition tokens become invalid when the session used to create them is deleted, is idle for too long, begins a new transaction, or becomes too old. When any of these happen, it is not possible to resume the read, and the whole operation must be restarted from the beginning. */
					class partitionRead(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/spanner.data""")
						/** Perform the request */
						def withPartitionReadRequest(body: Schema.PartitionReadRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.PartitionResponse])
					}
					object partitionRead {
						def apply(projectsId :PlayApi, instancesId :PlayApi, databasesId :PlayApi, sessionsId :PlayApi, session: String)(using signer: RequestSigner, ec: ExecutionContext): partitionRead = new partitionRead(ws.url(BASE_URL + s"v1/projects/${projectsId}/instances/${instancesId}/databases/${databasesId}/sessions/${sessionsId}:partitionRead").addQueryStringParameters("session" -> session.toString))
					}
					/** Executes a batch of SQL DML statements. This method allows many statements to be run with lower latency than submitting them sequentially with ExecuteSql. Statements are executed in sequential order. A request can succeed even if a statement fails. The ExecuteBatchDmlResponse.status field in the response provides information about the statement that failed. Clients must inspect this field to determine whether an error occurred. Execution stops after the first failed statement; the remaining statements are not executed. */
					class executeBatchDml(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/spanner.data""")
						/** Perform the request */
						def withExecuteBatchDmlRequest(body: Schema.ExecuteBatchDmlRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.ExecuteBatchDmlResponse])
					}
					object executeBatchDml {
						def apply(projectsId :PlayApi, instancesId :PlayApi, databasesId :PlayApi, sessionsId :PlayApi, session: String)(using signer: RequestSigner, ec: ExecutionContext): executeBatchDml = new executeBatchDml(ws.url(BASE_URL + s"v1/projects/${projectsId}/instances/${instancesId}/databases/${databasesId}/sessions/${sessionsId}:executeBatchDml").addQueryStringParameters("session" -> session.toString))
					}
					/** Ends a session, releasing server resources associated with it. This will asynchronously trigger cancellation of any operations that are running with this session. */
					class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/spanner.data""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
					}
					object delete {
						def apply(projectsId :PlayApi, instancesId :PlayApi, databasesId :PlayApi, sessionsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/instances/${instancesId}/databases/${databasesId}/sessions/${sessionsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
					}
					/** Creates multiple new sessions. This API can be used to initialize a session cache on the clients. See https://goo.gl/TgSFN2 for best practices on session cache management. */
					class batchCreate(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/spanner.data""")
						/** Perform the request */
						def withBatchCreateSessionsRequest(body: Schema.BatchCreateSessionsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.BatchCreateSessionsResponse])
					}
					object batchCreate {
						def apply(projectsId :PlayApi, instancesId :PlayApi, databasesId :PlayApi, database: String)(using signer: RequestSigner, ec: ExecutionContext): batchCreate = new batchCreate(ws.url(BASE_URL + s"v1/projects/${projectsId}/instances/${instancesId}/databases/${databasesId}/sessions:batchCreate").addQueryStringParameters("database" -> database.toString))
					}
					/** Like Read, except returns the result set as a stream. Unlike Read, there is no limit on the size of the returned result set. However, no individual row in the result set can exceed 100 MiB, and no column value can exceed 10 MiB. */
					class streamingRead(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/spanner.data""")
						/** Perform the request */
						def withReadRequest(body: Schema.ReadRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.PartialResultSet])
					}
					object streamingRead {
						def apply(projectsId :PlayApi, instancesId :PlayApi, databasesId :PlayApi, sessionsId :PlayApi, session: String)(using signer: RequestSigner, ec: ExecutionContext): streamingRead = new streamingRead(ws.url(BASE_URL + s"v1/projects/${projectsId}/instances/${instancesId}/databases/${databasesId}/sessions/${sessionsId}:streamingRead").addQueryStringParameters("session" -> session.toString))
					}
				}
				object databaseRoles {
					/** Returns permissions that the caller has on the specified database or backup resource. Attempting this RPC on a non-existent Cloud Spanner database will result in a NOT_FOUND error if the user has `spanner.databases.list` permission on the containing Cloud Spanner instance. Otherwise returns an empty set of permissions. Calling this method on a backup that does not exist will result in a NOT_FOUND error if the user has `spanner.backups.list` permission on the containing instance. Calling this method on a backup schedule that does not exist will result in a NOT_FOUND error if the user has `spanner.backupSchedules.list` permission on the containing database. */
					class testIamPermissions(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/spanner.admin""")
						/** Perform the request */
						def withTestIamPermissionsRequest(body: Schema.TestIamPermissionsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.TestIamPermissionsResponse])
					}
					object testIamPermissions {
						def apply(projectsId :PlayApi, instancesId :PlayApi, databasesId :PlayApi, databaseRolesId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): testIamPermissions = new testIamPermissions(ws.url(BASE_URL + s"v1/projects/${projectsId}/instances/${instancesId}/databases/${databasesId}/databaseRoles/${databaseRolesId}:testIamPermissions").addQueryStringParameters("resource" -> resource.toString))
					}
					/** Lists Cloud Spanner database roles. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListDatabaseRolesResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/spanner.admin""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListDatabaseRolesResponse])
					}
					object list {
						def apply(projectsId :PlayApi, instancesId :PlayApi, databasesId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/instances/${instancesId}/databases/${databasesId}/databaseRoles").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
						given Conversion[list, Future[Schema.ListDatabaseRolesResponse]] = (fun: list) => fun.apply()
					}
				}
				object backupSchedules {
					/** Returns permissions that the caller has on the specified database or backup resource. Attempting this RPC on a non-existent Cloud Spanner database will result in a NOT_FOUND error if the user has `spanner.databases.list` permission on the containing Cloud Spanner instance. Otherwise returns an empty set of permissions. Calling this method on a backup that does not exist will result in a NOT_FOUND error if the user has `spanner.backups.list` permission on the containing instance. Calling this method on a backup schedule that does not exist will result in a NOT_FOUND error if the user has `spanner.backupSchedules.list` permission on the containing database. */
					class testIamPermissions(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/spanner.admin""")
						/** Perform the request */
						def withTestIamPermissionsRequest(body: Schema.TestIamPermissionsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.TestIamPermissionsResponse])
					}
					object testIamPermissions {
						def apply(projectsId :PlayApi, instancesId :PlayApi, databasesId :PlayApi, backupSchedulesId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): testIamPermissions = new testIamPermissions(ws.url(BASE_URL + s"v1/projects/${projectsId}/instances/${instancesId}/databases/${databasesId}/backupSchedules/${backupSchedulesId}:testIamPermissions").addQueryStringParameters("resource" -> resource.toString))
					}
					/** Gets the access control policy for a database or backup resource. Returns an empty policy if a database or backup exists but does not have a policy set. Authorization requires `spanner.databases.getIamPolicy` permission on resource. For backups, authorization requires `spanner.backups.getIamPolicy` permission on resource. For backup schedules, authorization requires `spanner.backupSchedules.getIamPolicy` permission on resource. */
					class getIamPolicy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/spanner.admin""")
						/** Perform the request */
						def withGetIamPolicyRequest(body: Schema.GetIamPolicyRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
					}
					object getIamPolicy {
						def apply(projectsId :PlayApi, instancesId :PlayApi, databasesId :PlayApi, backupSchedulesId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): getIamPolicy = new getIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/instances/${instancesId}/databases/${databasesId}/backupSchedules/${backupSchedulesId}:getIamPolicy").addQueryStringParameters("resource" -> resource.toString))
					}
					/** Deletes a backup schedule. */
					class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/spanner.admin""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
					}
					object delete {
						def apply(projectsId :PlayApi, instancesId :PlayApi, databasesId :PlayApi, backupSchedulesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/instances/${instancesId}/databases/${databasesId}/backupSchedules/${backupSchedulesId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
					}
					/** Gets backup schedule for the input schedule name. */
					class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.BackupSchedule]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/spanner.admin""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.BackupSchedule])
					}
					object get {
						def apply(projectsId :PlayApi, instancesId :PlayApi, databasesId :PlayApi, backupSchedulesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/instances/${instancesId}/databases/${databasesId}/backupSchedules/${backupSchedulesId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.BackupSchedule]] = (fun: get) => fun.apply()
					}
					/** Updates a backup schedule. */
					class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/spanner.admin""")
						/** Perform the request */
						def withBackupSchedule(body: Schema.BackupSchedule) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.BackupSchedule])
					}
					object patch {
						def apply(projectsId :PlayApi, instancesId :PlayApi, databasesId :PlayApi, backupSchedulesId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/instances/${instancesId}/databases/${databasesId}/backupSchedules/${backupSchedulesId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
					}
					/** Lists all the backup schedules for the database. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListBackupSchedulesResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/spanner.admin""")
						/** Optional. Number of backup schedules to be returned in the response. If 0 or less, defaults to the server's maximum allowed page size.<br>Format: int32 */
						def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
						/** Optional. If non-empty, `page_token` should contain a next_page_token from a previous ListBackupSchedulesResponse to the same `parent`. */
						def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListBackupSchedulesResponse])
					}
					object list {
						def apply(projectsId :PlayApi, instancesId :PlayApi, databasesId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/instances/${instancesId}/databases/${databasesId}/backupSchedules").addQueryStringParameters("parent" -> parent.toString))
						given Conversion[list, Future[Schema.ListBackupSchedulesResponse]] = (fun: list) => fun.apply()
					}
					/** Creates a new backup schedule. */
					class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/spanner.admin""")
						/** Perform the request */
						def withBackupSchedule(body: Schema.BackupSchedule) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.BackupSchedule])
					}
					object create {
						def apply(projectsId :PlayApi, instancesId :PlayApi, databasesId :PlayApi, parent: String, backupScheduleId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/instances/${instancesId}/databases/${databasesId}/backupSchedules").addQueryStringParameters("parent" -> parent.toString, "backupScheduleId" -> backupScheduleId.toString))
					}
					/** Sets the access control policy on a database or backup resource. Replaces any existing policy. Authorization requires `spanner.databases.setIamPolicy` permission on resource. For backups, authorization requires `spanner.backups.setIamPolicy` permission on resource. For backup schedules, authorization requires `spanner.backupSchedules.setIamPolicy` permission on resource. */
					class setIamPolicy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/spanner.admin""")
						/** Perform the request */
						def withSetIamPolicyRequest(body: Schema.SetIamPolicyRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
					}
					object setIamPolicy {
						def apply(projectsId :PlayApi, instancesId :PlayApi, databasesId :PlayApi, backupSchedulesId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): setIamPolicy = new setIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/instances/${instancesId}/databases/${databasesId}/backupSchedules/${backupSchedulesId}:setIamPolicy").addQueryStringParameters("resource" -> resource.toString))
					}
				}
			}
			object databaseOperations {
				/** Lists database longrunning-operations. A database operation has a name of the form `projects//instances//databases//operations/`. The long-running operation metadata field type `metadata.type_url` describes the type of the metadata. Operations returned include those that have completed/failed/canceled within the last 7 days, and pending operations. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListDatabaseOperationsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/spanner.admin""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListDatabaseOperationsResponse])
				}
				object list {
					def apply(projectsId :PlayApi, instancesId :PlayApi, parent: String, filter: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/instances/${instancesId}/databaseOperations").addQueryStringParameters("parent" -> parent.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListDatabaseOperationsResponse]] = (fun: list) => fun.apply()
				}
			}
			object operations {
				/** Lists operations that match the specified filter in the request. If the server doesn't support this method, it returns `UNIMPLEMENTED`. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListOperationsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/spanner.admin""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListOperationsResponse])
				}
				object list {
					def apply(projectsId :PlayApi, instancesId :PlayApi, name: String, filter: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/instances/${instancesId}/operations").addQueryStringParameters("name" -> name.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListOperationsResponse]] = (fun: list) => fun.apply()
				}
				/** Gets the latest state of a long-running operation. Clients can use this method to poll the operation result at intervals as recommended by the API service. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/spanner.admin""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Operation])
				}
				object get {
					def apply(projectsId :PlayApi, instancesId :PlayApi, operationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/instances/${instancesId}/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.Operation]] = (fun: get) => fun.apply()
				}
				/** Deletes a long-running operation. This method indicates that the client is no longer interested in the operation result. It does not cancel the operation. If the server doesn't support this method, it returns `google.rpc.Code.UNIMPLEMENTED`. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/spanner.admin""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
				}
				object delete {
					def apply(projectsId :PlayApi, instancesId :PlayApi, operationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/instances/${instancesId}/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
				}
				/** Starts asynchronous cancellation on a long-running operation. The server makes a best effort to cancel the operation, but success is not guaranteed. If the server doesn't support this method, it returns `google.rpc.Code.UNIMPLEMENTED`. Clients can use Operations.GetOperation or other methods to check whether the cancellation succeeded or whether the operation completed despite cancellation. On successful cancellation, the operation is not deleted; instead, it becomes an operation with an Operation.error value with a google.rpc.Status.code of 1, corresponding to `Code.CANCELLED`. */
				class cancel(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/spanner.admin""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("POST")).map(_.json.as[Schema.Empty])
				}
				object cancel {
					def apply(projectsId :PlayApi, instancesId :PlayApi, operationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): cancel = new cancel(ws.url(BASE_URL + s"v1/projects/${projectsId}/instances/${instancesId}/operations/${operationsId}:cancel").addQueryStringParameters("name" -> name.toString))
					given Conversion[cancel, Future[Schema.Empty]] = (fun: cancel) => fun.apply()
				}
			}
			object backupOperations {
				/** Lists the backup long-running operations in the given instance. A backup operation has a name of the form `projects//instances//backups//operations/`. The long-running operation metadata field type `metadata.type_url` describes the type of the metadata. Operations returned include those that have completed/failed/canceled within the last 7 days, and pending operations. Operations returned are ordered by `operation.metadata.value.progress.start_time` in descending order starting from the most recently started operation. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListBackupOperationsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/spanner.admin""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListBackupOperationsResponse])
				}
				object list {
					def apply(projectsId :PlayApi, instancesId :PlayApi, parent: String, filter: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/instances/${instancesId}/backupOperations").addQueryStringParameters("parent" -> parent.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListBackupOperationsResponse]] = (fun: list) => fun.apply()
				}
			}
			object instancePartitionOperations {
				/** Lists instance partition long-running operations in the given instance. An instance partition operation has a name of the form `projects//instances//instancePartitions//operations/`. The long-running operation metadata field type `metadata.type_url` describes the type of the metadata. Operations returned include those that have completed/failed/canceled within the last 7 days, and pending operations. Operations returned are ordered by `operation.metadata.value.start_time` in descending order starting from the most recently started operation. Authorization requires `spanner.instancePartitionOperations.list` permission on the resource parent. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListInstancePartitionOperationsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/spanner.admin""")
					/** Optional. An expression that filters the list of returned operations. A filter expression consists of a field name, a comparison operator, and a value for filtering. The value must be a string, a number, or a boolean. The comparison operator must be one of: `<`, `>`, `<=`, `>=`, `!=`, `=`, or `:`. Colon `:` is the contains operator. Filter rules are not case sensitive. The following fields in the Operation are eligible for filtering: &#42; `name` - The name of the long-running operation &#42; `done` - False if the operation is in progress, else true. &#42; `metadata.@type` - the type of metadata. For example, the type string for CreateInstancePartitionMetadata is `type.googleapis.com/google.spanner.admin.instance.v1.CreateInstancePartitionMetadata`. &#42; `metadata.` - any field in metadata.value. `metadata.@type` must be specified first, if filtering on metadata fields. &#42; `error` - Error associated with the long-running operation. &#42; `response.@type` - the type of response. &#42; `response.` - any field in response.value. You can combine multiple expressions by enclosing each expression in parentheses. By default, expressions are combined with AND logic. However, you can specify AND, OR, and NOT logic explicitly. Here are a few examples: &#42; `done:true` - The operation is complete. &#42; `(metadata.@type=` \ `type.googleapis.com/google.spanner.admin.instance.v1.CreateInstancePartitionMetadata) AND` \ `(metadata.instance_partition.name:custom-instance-partition) AND` \ `(metadata.start_time < \"2021-03-28T14:50:00Z\") AND` \ `(error:&#42;)` - Return operations where: &#42; The operation's metadata type is CreateInstancePartitionMetadata. &#42; The instance partition name contains "custom-instance-partition". &#42; The operation started before 2021-03-28T14:50:00Z. &#42; The operation resulted in an error. */
					def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
					/** Optional. Number of operations to be returned in the response. If 0 or less, defaults to the server's maximum allowed page size.<br>Format: int32 */
					def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
					/** Optional. If non-empty, `page_token` should contain a next_page_token from a previous ListInstancePartitionOperationsResponse to the same `parent` and with the same `filter`. */
					def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
					/** Optional. Deadline used while retrieving metadata for instance partition operations. Instance partitions whose operation metadata cannot be retrieved within this deadline will be added to unreachable in ListInstancePartitionOperationsResponse.<br>Format: google-datetime */
					def withInstancePartitionDeadline(instancePartitionDeadline: String) = new list(req.addQueryStringParameters("instancePartitionDeadline" -> instancePartitionDeadline.toString))
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListInstancePartitionOperationsResponse])
				}
				object list {
					def apply(projectsId :PlayApi, instancesId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/instances/${instancesId}/instancePartitionOperations").addQueryStringParameters("parent" -> parent.toString))
					given Conversion[list, Future[Schema.ListInstancePartitionOperationsResponse]] = (fun: list) => fun.apply()
				}
			}
		}
	}
}
