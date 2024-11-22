package com.boresjo.play.api.google.bigtableadmin

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
		"""https://www.googleapis.com/auth/bigtable.admin""" /* Administer your Cloud Bigtable tables and clusters */,
		"""https://www.googleapis.com/auth/bigtable.admin.cluster""" /* Administer your Cloud Bigtable clusters */,
		"""https://www.googleapis.com/auth/bigtable.admin.instance""" /* Administer your Cloud Bigtable clusters */,
		"""https://www.googleapis.com/auth/bigtable.admin.table""" /* Administer your Cloud Bigtable tables */,
		"""https://www.googleapis.com/auth/cloud-bigtable.admin""" /* Administer your Cloud Bigtable tables and clusters */,
		"""https://www.googleapis.com/auth/cloud-bigtable.admin.cluster""" /* Administer your Cloud Bigtable clusters */,
		"""https://www.googleapis.com/auth/cloud-bigtable.admin.table""" /* Administer your Cloud Bigtable tables */,
		"""https://www.googleapis.com/auth/cloud-platform""" /* See, edit, configure, and delete your Google Cloud data and see the email address for your Google Account. */,
		"""https://www.googleapis.com/auth/cloud-platform.read-only""" /* View your data across Google Cloud services and see the email address of your Google Account */
	)

	private val BASE_URL = "https://bigtableadmin.googleapis.com/"

	object operations {
		/** Gets the latest state of a long-running operation. Clients can use this method to poll the operation result at intervals as recommended by the API service. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
			val scopes = Seq("""https://www.googleapis.com/auth/bigtable.admin""", """https://www.googleapis.com/auth/bigtable.admin.cluster""", """https://www.googleapis.com/auth/bigtable.admin.instance""", """https://www.googleapis.com/auth/cloud-bigtable.admin""", """https://www.googleapis.com/auth/cloud-bigtable.admin.cluster""", """https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Operation])
		}
		object get {
			def apply(operationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
			given Conversion[get, Future[Schema.Operation]] = (fun: get) => fun.apply()
		}
		object projects {
			object operations {
				/** Lists operations that match the specified filter in the request. If the server doesn't support this method, it returns `UNIMPLEMENTED`. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListOperationsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/bigtable.admin""", """https://www.googleapis.com/auth/bigtable.admin.cluster""", """https://www.googleapis.com/auth/bigtable.admin.instance""", """https://www.googleapis.com/auth/cloud-bigtable.admin""", """https://www.googleapis.com/auth/cloud-bigtable.admin.cluster""", """https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListOperationsResponse])
				}
				object list {
					def apply(projectsId :PlayApi, name: String, filter: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/operations/projects/${projectsId}/operations").addQueryStringParameters("name" -> name.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListOperationsResponse]] = (fun: list) => fun.apply()
				}
			}
		}
	}
	object projects {
		object instances {
			/** Returns permissions that the caller has on the specified instance resource. */
			class testIamPermissions(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/bigtable.admin""", """https://www.googleapis.com/auth/bigtable.admin.cluster""", """https://www.googleapis.com/auth/bigtable.admin.instance""", """https://www.googleapis.com/auth/cloud-bigtable.admin""", """https://www.googleapis.com/auth/cloud-bigtable.admin.cluster""", """https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withTestIamPermissionsRequest(body: Schema.TestIamPermissionsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.TestIamPermissionsResponse])
			}
			object testIamPermissions {
				def apply(projectsId :PlayApi, instancesId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): testIamPermissions = new testIamPermissions(ws.url(BASE_URL + s"v2/projects/${projectsId}/instances/${instancesId}:testIamPermissions").addQueryStringParameters("resource" -> resource.toString))
			}
			/** Gets the access control policy for an instance resource. Returns an empty policy if an instance exists but does not have a policy set. */
			class getIamPolicy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/bigtable.admin""", """https://www.googleapis.com/auth/bigtable.admin.cluster""", """https://www.googleapis.com/auth/bigtable.admin.instance""", """https://www.googleapis.com/auth/cloud-bigtable.admin""", """https://www.googleapis.com/auth/cloud-bigtable.admin.cluster""", """https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withGetIamPolicyRequest(body: Schema.GetIamPolicyRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
			}
			object getIamPolicy {
				def apply(projectsId :PlayApi, instancesId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): getIamPolicy = new getIamPolicy(ws.url(BASE_URL + s"v2/projects/${projectsId}/instances/${instancesId}:getIamPolicy").addQueryStringParameters("resource" -> resource.toString))
			}
			/** Delete an instance from a project. */
			class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
				val scopes = Seq("""https://www.googleapis.com/auth/bigtable.admin""", """https://www.googleapis.com/auth/bigtable.admin.cluster""", """https://www.googleapis.com/auth/bigtable.admin.instance""", """https://www.googleapis.com/auth/cloud-bigtable.admin""", """https://www.googleapis.com/auth/cloud-bigtable.admin.cluster""", """https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
			}
			object delete {
				def apply(projectsId :PlayApi, instancesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v2/projects/${projectsId}/instances/${instancesId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
			}
			/** Gets information about an instance. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Instance]) {
				val scopes = Seq("""https://www.googleapis.com/auth/bigtable.admin""", """https://www.googleapis.com/auth/bigtable.admin.cluster""", """https://www.googleapis.com/auth/bigtable.admin.instance""", """https://www.googleapis.com/auth/cloud-bigtable.admin""", """https://www.googleapis.com/auth/cloud-bigtable.admin.cluster""", """https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Instance])
			}
			object get {
				def apply(projectsId :PlayApi, instancesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/projects/${projectsId}/instances/${instancesId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.Instance]] = (fun: get) => fun.apply()
			}
			/** Lists information about instances in a project. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListInstancesResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/bigtable.admin""", """https://www.googleapis.com/auth/bigtable.admin.cluster""", """https://www.googleapis.com/auth/bigtable.admin.instance""", """https://www.googleapis.com/auth/cloud-bigtable.admin""", """https://www.googleapis.com/auth/cloud-bigtable.admin.cluster""", """https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListInstancesResponse])
			}
			object list {
				def apply(projectsId :PlayApi, parent: String, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/projects/${projectsId}/instances").addQueryStringParameters("parent" -> parent.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListInstancesResponse]] = (fun: list) => fun.apply()
			}
			/** Create an instance within a project. Note that exactly one of Cluster.serve_nodes and Cluster.cluster_config.cluster_autoscaling_config can be set. If serve_nodes is set to non-zero, then the cluster is manually scaled. If cluster_config.cluster_autoscaling_config is non-empty, then autoscaling is enabled. */
			class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/bigtable.admin""", """https://www.googleapis.com/auth/bigtable.admin.cluster""", """https://www.googleapis.com/auth/bigtable.admin.instance""", """https://www.googleapis.com/auth/cloud-bigtable.admin""", """https://www.googleapis.com/auth/cloud-bigtable.admin.cluster""", """https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withCreateInstanceRequest(body: Schema.CreateInstanceRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
			}
			object create {
				def apply(projectsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v2/projects/${projectsId}/instances").addQueryStringParameters("parent" -> parent.toString))
			}
			/** Sets the access control policy on an instance resource. Replaces any existing policy. */
			class setIamPolicy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/bigtable.admin""", """https://www.googleapis.com/auth/bigtable.admin.cluster""", """https://www.googleapis.com/auth/bigtable.admin.instance""", """https://www.googleapis.com/auth/cloud-bigtable.admin""", """https://www.googleapis.com/auth/cloud-bigtable.admin.cluster""", """https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withSetIamPolicyRequest(body: Schema.SetIamPolicyRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
			}
			object setIamPolicy {
				def apply(projectsId :PlayApi, instancesId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): setIamPolicy = new setIamPolicy(ws.url(BASE_URL + s"v2/projects/${projectsId}/instances/${instancesId}:setIamPolicy").addQueryStringParameters("resource" -> resource.toString))
			}
			/** Updates an instance within a project. This method updates only the display name and type for an Instance. To update other Instance properties, such as labels, use PartialUpdateInstance. */
			class update(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/bigtable.admin""", """https://www.googleapis.com/auth/bigtable.admin.cluster""", """https://www.googleapis.com/auth/bigtable.admin.instance""", """https://www.googleapis.com/auth/cloud-bigtable.admin""", """https://www.googleapis.com/auth/cloud-bigtable.admin.cluster""", """https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withInstance(body: Schema.Instance) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.Instance])
			}
			object update {
				def apply(projectsId :PlayApi, instancesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"v2/projects/${projectsId}/instances/${instancesId}").addQueryStringParameters("name" -> name.toString))
			}
			/** Partially updates an instance within a project. This method can modify all fields of an Instance and is the preferred way to update an Instance. */
			class partialUpdateInstance(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/bigtable.admin""", """https://www.googleapis.com/auth/bigtable.admin.cluster""", """https://www.googleapis.com/auth/bigtable.admin.instance""", """https://www.googleapis.com/auth/cloud-bigtable.admin""", """https://www.googleapis.com/auth/cloud-bigtable.admin.cluster""", """https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withInstance(body: Schema.Instance) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Operation])
			}
			object partialUpdateInstance {
				def apply(projectsId :PlayApi, instancesId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): partialUpdateInstance = new partialUpdateInstance(ws.url(BASE_URL + s"v2/projects/${projectsId}/instances/${instancesId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
			}
			object clusters {
				/** Partially updates a cluster within a project. This method is the preferred way to update a Cluster. To enable and update autoscaling, set cluster_config.cluster_autoscaling_config. When autoscaling is enabled, serve_nodes is treated as an OUTPUT_ONLY field, meaning that updates to it are ignored. Note that an update cannot simultaneously set serve_nodes to non-zero and cluster_config.cluster_autoscaling_config to non-empty, and also specify both in the update_mask. To disable autoscaling, clear cluster_config.cluster_autoscaling_config, and explicitly set a serve_node count via the update_mask. */
				class partialUpdateCluster(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/bigtable.admin""", """https://www.googleapis.com/auth/bigtable.admin.cluster""", """https://www.googleapis.com/auth/bigtable.admin.instance""", """https://www.googleapis.com/auth/cloud-bigtable.admin""", """https://www.googleapis.com/auth/cloud-bigtable.admin.cluster""", """https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withCluster(body: Schema.Cluster) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Operation])
				}
				object partialUpdateCluster {
					def apply(projectsId :PlayApi, instancesId :PlayApi, clustersId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): partialUpdateCluster = new partialUpdateCluster(ws.url(BASE_URL + s"v2/projects/${projectsId}/instances/${instancesId}/clusters/${clustersId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
				}
				/** Creates a cluster within an instance. Note that exactly one of Cluster.serve_nodes and Cluster.cluster_config.cluster_autoscaling_config can be set. If serve_nodes is set to non-zero, then the cluster is manually scaled. If cluster_config.cluster_autoscaling_config is non-empty, then autoscaling is enabled. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/bigtable.admin""", """https://www.googleapis.com/auth/bigtable.admin.cluster""", """https://www.googleapis.com/auth/bigtable.admin.instance""", """https://www.googleapis.com/auth/cloud-bigtable.admin""", """https://www.googleapis.com/auth/cloud-bigtable.admin.cluster""", """https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withCluster(body: Schema.Cluster) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object create {
					def apply(projectsId :PlayApi, instancesId :PlayApi, parent: String, clusterId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v2/projects/${projectsId}/instances/${instancesId}/clusters").addQueryStringParameters("parent" -> parent.toString, "clusterId" -> clusterId.toString))
				}
				/** Deletes a cluster from an instance. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					val scopes = Seq("""https://www.googleapis.com/auth/bigtable.admin""", """https://www.googleapis.com/auth/bigtable.admin.cluster""", """https://www.googleapis.com/auth/bigtable.admin.instance""", """https://www.googleapis.com/auth/cloud-bigtable.admin""", """https://www.googleapis.com/auth/cloud-bigtable.admin.cluster""", """https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
				}
				object delete {
					def apply(projectsId :PlayApi, instancesId :PlayApi, clustersId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v2/projects/${projectsId}/instances/${instancesId}/clusters/${clustersId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
				}
				/** Gets information about a cluster. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Cluster]) {
					val scopes = Seq("""https://www.googleapis.com/auth/bigtable.admin""", """https://www.googleapis.com/auth/bigtable.admin.cluster""", """https://www.googleapis.com/auth/bigtable.admin.instance""", """https://www.googleapis.com/auth/cloud-bigtable.admin""", """https://www.googleapis.com/auth/cloud-bigtable.admin.cluster""", """https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Cluster])
				}
				object get {
					def apply(projectsId :PlayApi, instancesId :PlayApi, clustersId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/projects/${projectsId}/instances/${instancesId}/clusters/${clustersId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.Cluster]] = (fun: get) => fun.apply()
				}
				/** Lists information about clusters in an instance. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListClustersResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/bigtable.admin""", """https://www.googleapis.com/auth/bigtable.admin.cluster""", """https://www.googleapis.com/auth/bigtable.admin.instance""", """https://www.googleapis.com/auth/cloud-bigtable.admin""", """https://www.googleapis.com/auth/cloud-bigtable.admin.cluster""", """https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListClustersResponse])
				}
				object list {
					def apply(projectsId :PlayApi, instancesId :PlayApi, parent: String, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/projects/${projectsId}/instances/${instancesId}/clusters").addQueryStringParameters("parent" -> parent.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListClustersResponse]] = (fun: list) => fun.apply()
				}
				/** Updates a cluster within an instance. Note that UpdateCluster does not support updating cluster_config.cluster_autoscaling_config. In order to update it, you must use PartialUpdateCluster. */
				class update(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/bigtable.admin""", """https://www.googleapis.com/auth/bigtable.admin.cluster""", """https://www.googleapis.com/auth/bigtable.admin.instance""", """https://www.googleapis.com/auth/cloud-bigtable.admin""", """https://www.googleapis.com/auth/cloud-bigtable.admin.cluster""", """https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withCluster(body: Schema.Cluster) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.Operation])
				}
				object update {
					def apply(projectsId :PlayApi, instancesId :PlayApi, clustersId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"v2/projects/${projectsId}/instances/${instancesId}/clusters/${clustersId}").addQueryStringParameters("name" -> name.toString))
				}
				object backups {
					/** Returns permissions that the caller has on the specified Bigtable resource. */
					class testIamPermissions(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/bigtable.admin""", """https://www.googleapis.com/auth/bigtable.admin.table""", """https://www.googleapis.com/auth/cloud-bigtable.admin""", """https://www.googleapis.com/auth/cloud-bigtable.admin.table""", """https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withTestIamPermissionsRequest(body: Schema.TestIamPermissionsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.TestIamPermissionsResponse])
					}
					object testIamPermissions {
						def apply(projectsId :PlayApi, instancesId :PlayApi, clustersId :PlayApi, backupsId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): testIamPermissions = new testIamPermissions(ws.url(BASE_URL + s"v2/projects/${projectsId}/instances/${instancesId}/clusters/${clustersId}/backups/${backupsId}:testIamPermissions").addQueryStringParameters("resource" -> resource.toString))
					}
					/** Gets the access control policy for a Bigtable resource. Returns an empty policy if the resource exists but does not have a policy set. */
					class getIamPolicy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/bigtable.admin""", """https://www.googleapis.com/auth/bigtable.admin.table""", """https://www.googleapis.com/auth/cloud-bigtable.admin""", """https://www.googleapis.com/auth/cloud-bigtable.admin.table""", """https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withGetIamPolicyRequest(body: Schema.GetIamPolicyRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
					}
					object getIamPolicy {
						def apply(projectsId :PlayApi, instancesId :PlayApi, clustersId :PlayApi, backupsId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): getIamPolicy = new getIamPolicy(ws.url(BASE_URL + s"v2/projects/${projectsId}/instances/${instancesId}/clusters/${clustersId}/backups/${backupsId}:getIamPolicy").addQueryStringParameters("resource" -> resource.toString))
					}
					/** Deletes a pending or completed Cloud Bigtable backup. */
					class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
						val scopes = Seq("""https://www.googleapis.com/auth/bigtable.admin""", """https://www.googleapis.com/auth/bigtable.admin.table""", """https://www.googleapis.com/auth/cloud-bigtable.admin""", """https://www.googleapis.com/auth/cloud-bigtable.admin.table""", """https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
					}
					object delete {
						def apply(projectsId :PlayApi, instancesId :PlayApi, clustersId :PlayApi, backupsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v2/projects/${projectsId}/instances/${instancesId}/clusters/${clustersId}/backups/${backupsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
					}
					/** Copy a Cloud Bigtable backup to a new backup in the destination cluster located in the destination instance and project. */
					class copy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/bigtable.admin""", """https://www.googleapis.com/auth/bigtable.admin.table""", """https://www.googleapis.com/auth/cloud-bigtable.admin""", """https://www.googleapis.com/auth/cloud-bigtable.admin.table""", """https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withCopyBackupRequest(body: Schema.CopyBackupRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
					}
					object copy {
						def apply(projectsId :PlayApi, instancesId :PlayApi, clustersId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): copy = new copy(ws.url(BASE_URL + s"v2/projects/${projectsId}/instances/${instancesId}/clusters/${clustersId}/backups:copy").addQueryStringParameters("parent" -> parent.toString))
					}
					/** Gets metadata on a pending or completed Cloud Bigtable Backup. */
					class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Backup]) {
						val scopes = Seq("""https://www.googleapis.com/auth/bigtable.admin""", """https://www.googleapis.com/auth/bigtable.admin.table""", """https://www.googleapis.com/auth/cloud-bigtable.admin""", """https://www.googleapis.com/auth/cloud-bigtable.admin.table""", """https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Backup])
					}
					object get {
						def apply(projectsId :PlayApi, instancesId :PlayApi, clustersId :PlayApi, backupsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/projects/${projectsId}/instances/${instancesId}/clusters/${clustersId}/backups/${backupsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.Backup]] = (fun: get) => fun.apply()
					}
					/** Updates a pending or completed Cloud Bigtable Backup. */
					class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/bigtable.admin""", """https://www.googleapis.com/auth/bigtable.admin.table""", """https://www.googleapis.com/auth/cloud-bigtable.admin""", """https://www.googleapis.com/auth/cloud-bigtable.admin.table""", """https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withBackup(body: Schema.Backup) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Backup])
					}
					object patch {
						def apply(projectsId :PlayApi, instancesId :PlayApi, clustersId :PlayApi, backupsId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v2/projects/${projectsId}/instances/${instancesId}/clusters/${clustersId}/backups/${backupsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
					}
					/** Lists Cloud Bigtable backups. Returns both completed and pending backups. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListBackupsResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/bigtable.admin""", """https://www.googleapis.com/auth/bigtable.admin.table""", """https://www.googleapis.com/auth/cloud-bigtable.admin""", """https://www.googleapis.com/auth/cloud-bigtable.admin.table""", """https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListBackupsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, instancesId :PlayApi, clustersId :PlayApi, parent: String, filter: String, orderBy: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/projects/${projectsId}/instances/${instancesId}/clusters/${clustersId}/backups").addQueryStringParameters("parent" -> parent.toString, "filter" -> filter.toString, "orderBy" -> orderBy.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
						given Conversion[list, Future[Schema.ListBackupsResponse]] = (fun: list) => fun.apply()
					}
					/** Starts creating a new Cloud Bigtable Backup. The returned backup long-running operation can be used to track creation of the backup. The metadata field type is CreateBackupMetadata. The response field type is Backup, if successful. Cancelling the returned operation will stop the creation and delete the backup. */
					class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/bigtable.admin""", """https://www.googleapis.com/auth/bigtable.admin.table""", """https://www.googleapis.com/auth/cloud-bigtable.admin""", """https://www.googleapis.com/auth/cloud-bigtable.admin.table""", """https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withBackup(body: Schema.Backup) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
					}
					object create {
						def apply(projectsId :PlayApi, instancesId :PlayApi, clustersId :PlayApi, parent: String, backupId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v2/projects/${projectsId}/instances/${instancesId}/clusters/${clustersId}/backups").addQueryStringParameters("parent" -> parent.toString, "backupId" -> backupId.toString))
					}
					/** Sets the access control policy on a Bigtable resource. Replaces any existing policy. */
					class setIamPolicy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/bigtable.admin""", """https://www.googleapis.com/auth/bigtable.admin.table""", """https://www.googleapis.com/auth/cloud-bigtable.admin""", """https://www.googleapis.com/auth/cloud-bigtable.admin.table""", """https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withSetIamPolicyRequest(body: Schema.SetIamPolicyRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
					}
					object setIamPolicy {
						def apply(projectsId :PlayApi, instancesId :PlayApi, clustersId :PlayApi, backupsId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): setIamPolicy = new setIamPolicy(ws.url(BASE_URL + s"v2/projects/${projectsId}/instances/${instancesId}/clusters/${clustersId}/backups/${backupsId}:setIamPolicy").addQueryStringParameters("resource" -> resource.toString))
					}
				}
				object hotTablets {
					/** Lists hot tablets in a cluster, within the time range provided. Hot tablets are ordered based on CPU usage. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListHotTabletsResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/bigtable.admin""", """https://www.googleapis.com/auth/bigtable.admin.cluster""", """https://www.googleapis.com/auth/bigtable.admin.instance""", """https://www.googleapis.com/auth/cloud-bigtable.admin""", """https://www.googleapis.com/auth/cloud-bigtable.admin.cluster""", """https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListHotTabletsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, instancesId :PlayApi, clustersId :PlayApi, parent: String, startTime: String, endTime: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/projects/${projectsId}/instances/${instancesId}/clusters/${clustersId}/hotTablets").addQueryStringParameters("parent" -> parent.toString, "startTime" -> startTime.toString, "endTime" -> endTime.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
						given Conversion[list, Future[Schema.ListHotTabletsResponse]] = (fun: list) => fun.apply()
					}
				}
			}
			object appProfiles {
				/** Creates an app profile within an instance. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/bigtable.admin""", """https://www.googleapis.com/auth/bigtable.admin.cluster""", """https://www.googleapis.com/auth/bigtable.admin.instance""", """https://www.googleapis.com/auth/cloud-bigtable.admin""", """https://www.googleapis.com/auth/cloud-bigtable.admin.cluster""", """https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withAppProfile(body: Schema.AppProfile) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.AppProfile])
				}
				object create {
					def apply(projectsId :PlayApi, instancesId :PlayApi, parent: String, appProfileId: String, ignoreWarnings: Boolean)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v2/projects/${projectsId}/instances/${instancesId}/appProfiles").addQueryStringParameters("parent" -> parent.toString, "appProfileId" -> appProfileId.toString, "ignoreWarnings" -> ignoreWarnings.toString))
				}
				/** Deletes an app profile from an instance. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					val scopes = Seq("""https://www.googleapis.com/auth/bigtable.admin""", """https://www.googleapis.com/auth/bigtable.admin.cluster""", """https://www.googleapis.com/auth/bigtable.admin.instance""", """https://www.googleapis.com/auth/cloud-bigtable.admin""", """https://www.googleapis.com/auth/cloud-bigtable.admin.cluster""", """https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
				}
				object delete {
					def apply(projectsId :PlayApi, instancesId :PlayApi, appProfilesId :PlayApi, name: String, ignoreWarnings: Boolean)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v2/projects/${projectsId}/instances/${instancesId}/appProfiles/${appProfilesId}").addQueryStringParameters("name" -> name.toString, "ignoreWarnings" -> ignoreWarnings.toString))
					given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
				}
				/** Gets information about an app profile. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.AppProfile]) {
					val scopes = Seq("""https://www.googleapis.com/auth/bigtable.admin""", """https://www.googleapis.com/auth/bigtable.admin.cluster""", """https://www.googleapis.com/auth/bigtable.admin.instance""", """https://www.googleapis.com/auth/cloud-bigtable.admin""", """https://www.googleapis.com/auth/cloud-bigtable.admin.cluster""", """https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.AppProfile])
				}
				object get {
					def apply(projectsId :PlayApi, instancesId :PlayApi, appProfilesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/projects/${projectsId}/instances/${instancesId}/appProfiles/${appProfilesId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.AppProfile]] = (fun: get) => fun.apply()
				}
				/** Updates an app profile within an instance. */
				class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/bigtable.admin""", """https://www.googleapis.com/auth/bigtable.admin.cluster""", """https://www.googleapis.com/auth/bigtable.admin.instance""", """https://www.googleapis.com/auth/cloud-bigtable.admin""", """https://www.googleapis.com/auth/cloud-bigtable.admin.cluster""", """https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withAppProfile(body: Schema.AppProfile) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Operation])
				}
				object patch {
					def apply(projectsId :PlayApi, instancesId :PlayApi, appProfilesId :PlayApi, name: String, updateMask: String, ignoreWarnings: Boolean)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v2/projects/${projectsId}/instances/${instancesId}/appProfiles/${appProfilesId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString, "ignoreWarnings" -> ignoreWarnings.toString))
				}
				/** Lists information about app profiles in an instance. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListAppProfilesResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/bigtable.admin""", """https://www.googleapis.com/auth/bigtable.admin.cluster""", """https://www.googleapis.com/auth/bigtable.admin.instance""", """https://www.googleapis.com/auth/cloud-bigtable.admin""", """https://www.googleapis.com/auth/cloud-bigtable.admin.cluster""", """https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListAppProfilesResponse])
				}
				object list {
					def apply(projectsId :PlayApi, instancesId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/projects/${projectsId}/instances/${instancesId}/appProfiles").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListAppProfilesResponse]] = (fun: list) => fun.apply()
				}
			}
			object tables {
				/** Returns permissions that the caller has on the specified Bigtable resource. */
				class testIamPermissions(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/bigtable.admin""", """https://www.googleapis.com/auth/bigtable.admin.table""", """https://www.googleapis.com/auth/cloud-bigtable.admin""", """https://www.googleapis.com/auth/cloud-bigtable.admin.table""", """https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withTestIamPermissionsRequest(body: Schema.TestIamPermissionsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.TestIamPermissionsResponse])
				}
				object testIamPermissions {
					def apply(projectsId :PlayApi, instancesId :PlayApi, tablesId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): testIamPermissions = new testIamPermissions(ws.url(BASE_URL + s"v2/projects/${projectsId}/instances/${instancesId}/tables/${tablesId}:testIamPermissions").addQueryStringParameters("resource" -> resource.toString))
				}
				/** Restores a specified table which was accidentally deleted. */
				class undelete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/bigtable.admin""", """https://www.googleapis.com/auth/bigtable.admin.table""", """https://www.googleapis.com/auth/cloud-bigtable.admin""", """https://www.googleapis.com/auth/cloud-bigtable.admin.table""", """https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withUndeleteTableRequest(body: Schema.UndeleteTableRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object undelete {
					def apply(projectsId :PlayApi, instancesId :PlayApi, tablesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): undelete = new undelete(ws.url(BASE_URL + s"v2/projects/${projectsId}/instances/${instancesId}/tables/${tablesId}:undelete").addQueryStringParameters("name" -> name.toString))
				}
				/** Create a new table by restoring from a completed backup. The returned table long-running operation can be used to track the progress of the operation, and to cancel it. The metadata field type is RestoreTableMetadata. The response type is Table, if successful. */
				class restore(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/bigtable.admin""", """https://www.googleapis.com/auth/bigtable.admin.table""", """https://www.googleapis.com/auth/cloud-bigtable.admin""", """https://www.googleapis.com/auth/cloud-bigtable.admin.table""", """https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withRestoreTableRequest(body: Schema.RestoreTableRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object restore {
					def apply(projectsId :PlayApi, instancesId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): restore = new restore(ws.url(BASE_URL + s"v2/projects/${projectsId}/instances/${instancesId}/tables:restore").addQueryStringParameters("parent" -> parent.toString))
				}
				/** Checks replication consistency based on a consistency token, that is, if replication has caught up based on the conditions specified in the token and the check request. */
				class checkConsistency(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/bigtable.admin""", """https://www.googleapis.com/auth/bigtable.admin.table""", """https://www.googleapis.com/auth/cloud-bigtable.admin""", """https://www.googleapis.com/auth/cloud-bigtable.admin.table""", """https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withCheckConsistencyRequest(body: Schema.CheckConsistencyRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.CheckConsistencyResponse])
				}
				object checkConsistency {
					def apply(projectsId :PlayApi, instancesId :PlayApi, tablesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): checkConsistency = new checkConsistency(ws.url(BASE_URL + s"v2/projects/${projectsId}/instances/${instancesId}/tables/${tablesId}:checkConsistency").addQueryStringParameters("name" -> name.toString))
				}
				/** Gets the access control policy for a Bigtable resource. Returns an empty policy if the resource exists but does not have a policy set. */
				class getIamPolicy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/bigtable.admin""", """https://www.googleapis.com/auth/bigtable.admin.table""", """https://www.googleapis.com/auth/cloud-bigtable.admin""", """https://www.googleapis.com/auth/cloud-bigtable.admin.table""", """https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGetIamPolicyRequest(body: Schema.GetIamPolicyRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
				}
				object getIamPolicy {
					def apply(projectsId :PlayApi, instancesId :PlayApi, tablesId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): getIamPolicy = new getIamPolicy(ws.url(BASE_URL + s"v2/projects/${projectsId}/instances/${instancesId}/tables/${tablesId}:getIamPolicy").addQueryStringParameters("resource" -> resource.toString))
				}
				/** Permanently deletes a specified table and all of its data. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					val scopes = Seq("""https://www.googleapis.com/auth/bigtable.admin""", """https://www.googleapis.com/auth/bigtable.admin.table""", """https://www.googleapis.com/auth/cloud-bigtable.admin""", """https://www.googleapis.com/auth/cloud-bigtable.admin.table""", """https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
				}
				object delete {
					def apply(projectsId :PlayApi, instancesId :PlayApi, tablesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v2/projects/${projectsId}/instances/${instancesId}/tables/${tablesId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
				}
				/** Gets metadata information about the specified table. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Table]) {
					val scopes = Seq("""https://www.googleapis.com/auth/bigtable.admin""", """https://www.googleapis.com/auth/bigtable.admin.table""", """https://www.googleapis.com/auth/cloud-bigtable.admin""", """https://www.googleapis.com/auth/cloud-bigtable.admin.table""", """https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Table])
				}
				object get {
					def apply(projectsId :PlayApi, instancesId :PlayApi, tablesId :PlayApi, name: String, view: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/projects/${projectsId}/instances/${instancesId}/tables/${tablesId}").addQueryStringParameters("name" -> name.toString, "view" -> view.toString))
					given Conversion[get, Future[Schema.Table]] = (fun: get) => fun.apply()
				}
				/** Generates a consistency token for a Table, which can be used in CheckConsistency to check whether mutations to the table that finished before this call started have been replicated. The tokens will be available for 90 days. */
				class generateConsistencyToken(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/bigtable.admin""", """https://www.googleapis.com/auth/bigtable.admin.table""", """https://www.googleapis.com/auth/cloud-bigtable.admin""", """https://www.googleapis.com/auth/cloud-bigtable.admin.table""", """https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGenerateConsistencyTokenRequest(body: Schema.GenerateConsistencyTokenRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GenerateConsistencyTokenResponse])
				}
				object generateConsistencyToken {
					def apply(projectsId :PlayApi, instancesId :PlayApi, tablesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): generateConsistencyToken = new generateConsistencyToken(ws.url(BASE_URL + s"v2/projects/${projectsId}/instances/${instancesId}/tables/${tablesId}:generateConsistencyToken").addQueryStringParameters("name" -> name.toString))
				}
				/** Updates a specified table. */
				class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/bigtable.admin""", """https://www.googleapis.com/auth/bigtable.admin.table""", """https://www.googleapis.com/auth/cloud-bigtable.admin""", """https://www.googleapis.com/auth/cloud-bigtable.admin.table""", """https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withTable(body: Schema.Table) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Operation])
				}
				object patch {
					def apply(projectsId :PlayApi, instancesId :PlayApi, tablesId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v2/projects/${projectsId}/instances/${instancesId}/tables/${tablesId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
				}
				/** Permanently drop/delete a row range from a specified table. The request can specify whether to delete all rows in a table, or only those that match a particular prefix. Note that row key prefixes used here are treated as service data. For more information about how service data is handled, see the [Google Cloud Privacy Notice](https://cloud.google.com/terms/cloud-privacy-notice). */
				class dropRowRange(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/bigtable.admin""", """https://www.googleapis.com/auth/bigtable.admin.table""", """https://www.googleapis.com/auth/cloud-bigtable.admin""", """https://www.googleapis.com/auth/cloud-bigtable.admin.table""", """https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withDropRowRangeRequest(body: Schema.DropRowRangeRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Empty])
				}
				object dropRowRange {
					def apply(projectsId :PlayApi, instancesId :PlayApi, tablesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): dropRowRange = new dropRowRange(ws.url(BASE_URL + s"v2/projects/${projectsId}/instances/${instancesId}/tables/${tablesId}:dropRowRange").addQueryStringParameters("name" -> name.toString))
				}
				/** Lists all tables served from a specified instance. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListTablesResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/bigtable.admin""", """https://www.googleapis.com/auth/bigtable.admin.table""", """https://www.googleapis.com/auth/cloud-bigtable.admin""", """https://www.googleapis.com/auth/cloud-bigtable.admin.table""", """https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListTablesResponse])
				}
				object list {
					def apply(projectsId :PlayApi, instancesId :PlayApi, parent: String, view: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/projects/${projectsId}/instances/${instancesId}/tables").addQueryStringParameters("parent" -> parent.toString, "view" -> view.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListTablesResponse]] = (fun: list) => fun.apply()
				}
				/** Creates a new table in the specified instance. The table can be created with a full set of initial column families, specified in the request. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/bigtable.admin""", """https://www.googleapis.com/auth/bigtable.admin.table""", """https://www.googleapis.com/auth/cloud-bigtable.admin""", """https://www.googleapis.com/auth/cloud-bigtable.admin.table""", """https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withCreateTableRequest(body: Schema.CreateTableRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Table])
				}
				object create {
					def apply(projectsId :PlayApi, instancesId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v2/projects/${projectsId}/instances/${instancesId}/tables").addQueryStringParameters("parent" -> parent.toString))
				}
				/** Sets the access control policy on a Bigtable resource. Replaces any existing policy. */
				class setIamPolicy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/bigtable.admin""", """https://www.googleapis.com/auth/bigtable.admin.table""", """https://www.googleapis.com/auth/cloud-bigtable.admin""", """https://www.googleapis.com/auth/cloud-bigtable.admin.table""", """https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withSetIamPolicyRequest(body: Schema.SetIamPolicyRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
				}
				object setIamPolicy {
					def apply(projectsId :PlayApi, instancesId :PlayApi, tablesId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): setIamPolicy = new setIamPolicy(ws.url(BASE_URL + s"v2/projects/${projectsId}/instances/${instancesId}/tables/${tablesId}:setIamPolicy").addQueryStringParameters("resource" -> resource.toString))
				}
				/** Performs a series of column family modifications on the specified table. Either all or none of the modifications will occur before this method returns, but data requests received prior to that point may see a table where only some modifications have taken effect. */
				class modifyColumnFamilies(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/bigtable.admin""", """https://www.googleapis.com/auth/bigtable.admin.table""", """https://www.googleapis.com/auth/cloud-bigtable.admin""", """https://www.googleapis.com/auth/cloud-bigtable.admin.table""", """https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withModifyColumnFamiliesRequest(body: Schema.ModifyColumnFamiliesRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Table])
				}
				object modifyColumnFamilies {
					def apply(projectsId :PlayApi, instancesId :PlayApi, tablesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): modifyColumnFamilies = new modifyColumnFamilies(ws.url(BASE_URL + s"v2/projects/${projectsId}/instances/${instancesId}/tables/${tablesId}:modifyColumnFamilies").addQueryStringParameters("name" -> name.toString))
				}
				object authorizedViews {
					/** Returns permissions that the caller has on the specified Bigtable resource. */
					class testIamPermissions(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/bigtable.admin""", """https://www.googleapis.com/auth/bigtable.admin.table""", """https://www.googleapis.com/auth/cloud-bigtable.admin""", """https://www.googleapis.com/auth/cloud-bigtable.admin.table""", """https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withTestIamPermissionsRequest(body: Schema.TestIamPermissionsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.TestIamPermissionsResponse])
					}
					object testIamPermissions {
						def apply(projectsId :PlayApi, instancesId :PlayApi, tablesId :PlayApi, authorizedViewsId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): testIamPermissions = new testIamPermissions(ws.url(BASE_URL + s"v2/projects/${projectsId}/instances/${instancesId}/tables/${tablesId}/authorizedViews/${authorizedViewsId}:testIamPermissions").addQueryStringParameters("resource" -> resource.toString))
					}
					/** Gets the access control policy for a Bigtable resource. Returns an empty policy if the resource exists but does not have a policy set. */
					class getIamPolicy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/bigtable.admin""", """https://www.googleapis.com/auth/bigtable.admin.table""", """https://www.googleapis.com/auth/cloud-bigtable.admin""", """https://www.googleapis.com/auth/cloud-bigtable.admin.table""", """https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withGetIamPolicyRequest(body: Schema.GetIamPolicyRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
					}
					object getIamPolicy {
						def apply(projectsId :PlayApi, instancesId :PlayApi, tablesId :PlayApi, authorizedViewsId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): getIamPolicy = new getIamPolicy(ws.url(BASE_URL + s"v2/projects/${projectsId}/instances/${instancesId}/tables/${tablesId}/authorizedViews/${authorizedViewsId}:getIamPolicy").addQueryStringParameters("resource" -> resource.toString))
					}
					/** Permanently deletes a specified AuthorizedView. */
					class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
						val scopes = Seq("""https://www.googleapis.com/auth/bigtable.admin""", """https://www.googleapis.com/auth/bigtable.admin.table""", """https://www.googleapis.com/auth/cloud-bigtable.admin""", """https://www.googleapis.com/auth/cloud-bigtable.admin.table""", """https://www.googleapis.com/auth/cloud-platform""")
						/** Optional. The current etag of the AuthorizedView. If an etag is provided and does not match the current etag of the AuthorizedView, deletion will be blocked and an ABORTED error will be returned. */
						def withEtag(etag: String) = new delete(req.addQueryStringParameters("etag" -> etag.toString))
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
					}
					object delete {
						def apply(projectsId :PlayApi, instancesId :PlayApi, tablesId :PlayApi, authorizedViewsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v2/projects/${projectsId}/instances/${instancesId}/tables/${tablesId}/authorizedViews/${authorizedViewsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
					}
					/** Gets information from a specified AuthorizedView. */
					class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.AuthorizedView]) {
						val scopes = Seq("""https://www.googleapis.com/auth/bigtable.admin""", """https://www.googleapis.com/auth/bigtable.admin.table""", """https://www.googleapis.com/auth/cloud-bigtable.admin""", """https://www.googleapis.com/auth/cloud-bigtable.admin.table""", """https://www.googleapis.com/auth/cloud-platform""")
						/** Optional. The resource_view to be applied to the returned AuthorizedView's fields. Default to BASIC.<br>Possible values:<br>RESPONSE_VIEW_UNSPECIFIED: Uses the default view for each method as documented in the request.<br>NAME_ONLY: Only populates `name`.<br>BASIC: Only populates the AuthorizedView's basic metadata. This includes: name, deletion_protection, etag.<br>FULL: Populates every fields. */
						def withView(view: String) = new get(req.addQueryStringParameters("view" -> view.toString))
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.AuthorizedView])
					}
					object get {
						def apply(projectsId :PlayApi, instancesId :PlayApi, tablesId :PlayApi, authorizedViewsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/projects/${projectsId}/instances/${instancesId}/tables/${tablesId}/authorizedViews/${authorizedViewsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.AuthorizedView]] = (fun: get) => fun.apply()
					}
					/** Updates an AuthorizedView in a table. */
					class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/bigtable.admin""", """https://www.googleapis.com/auth/bigtable.admin.table""", """https://www.googleapis.com/auth/cloud-bigtable.admin""", """https://www.googleapis.com/auth/cloud-bigtable.admin.table""", """https://www.googleapis.com/auth/cloud-platform""")
						/** Optional. The list of fields to update. A mask specifying which fields in the AuthorizedView resource should be updated. This mask is relative to the AuthorizedView resource, not to the request message. A field will be overwritten if it is in the mask. If empty, all fields set in the request will be overwritten. A special value `&#42;` means to overwrite all fields (including fields not set in the request).<br>Format: google-fieldmask */
						def withUpdateMask(updateMask: String) = new patch(req.addQueryStringParameters("updateMask" -> updateMask.toString))
						/** Optional. If true, ignore the safety checks when updating the AuthorizedView. */
						def withIgnoreWarnings(ignoreWarnings: Boolean) = new patch(req.addQueryStringParameters("ignoreWarnings" -> ignoreWarnings.toString))
						/** Perform the request */
						def withAuthorizedView(body: Schema.AuthorizedView) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Operation])
					}
					object patch {
						def apply(projectsId :PlayApi, instancesId :PlayApi, tablesId :PlayApi, authorizedViewsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v2/projects/${projectsId}/instances/${instancesId}/tables/${tablesId}/authorizedViews/${authorizedViewsId}").addQueryStringParameters("name" -> name.toString))
					}
					/** Lists all AuthorizedViews from a specific table. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListAuthorizedViewsResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/bigtable.admin""", """https://www.googleapis.com/auth/bigtable.admin.table""", """https://www.googleapis.com/auth/cloud-bigtable.admin""", """https://www.googleapis.com/auth/cloud-bigtable.admin.table""", """https://www.googleapis.com/auth/cloud-platform""")
						/** Optional. Maximum number of results per page. A page_size of zero lets the server choose the number of items to return. A page_size which is strictly positive will return at most that many items. A negative page_size will cause an error. Following the first request, subsequent paginated calls are not required to pass a page_size. If a page_size is set in subsequent calls, it must match the page_size given in the first request.<br>Format: int32 */
						def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
						/** Optional. The value of `next_page_token` returned by a previous call. */
						def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
						/** Optional. The resource_view to be applied to the returned AuthorizedViews' fields. Default to NAME_ONLY.<br>Possible values:<br>RESPONSE_VIEW_UNSPECIFIED: Uses the default view for each method as documented in the request.<br>NAME_ONLY: Only populates `name`.<br>BASIC: Only populates the AuthorizedView's basic metadata. This includes: name, deletion_protection, etag.<br>FULL: Populates every fields. */
						def withView(view: String) = new list(req.addQueryStringParameters("view" -> view.toString))
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListAuthorizedViewsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, instancesId :PlayApi, tablesId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/projects/${projectsId}/instances/${instancesId}/tables/${tablesId}/authorizedViews").addQueryStringParameters("parent" -> parent.toString))
						given Conversion[list, Future[Schema.ListAuthorizedViewsResponse]] = (fun: list) => fun.apply()
					}
					/** Creates a new AuthorizedView in a table. */
					class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/bigtable.admin""", """https://www.googleapis.com/auth/bigtable.admin.table""", """https://www.googleapis.com/auth/cloud-bigtable.admin""", """https://www.googleapis.com/auth/cloud-bigtable.admin.table""", """https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withAuthorizedView(body: Schema.AuthorizedView) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
					}
					object create {
						def apply(projectsId :PlayApi, instancesId :PlayApi, tablesId :PlayApi, parent: String, authorizedViewId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v2/projects/${projectsId}/instances/${instancesId}/tables/${tablesId}/authorizedViews").addQueryStringParameters("parent" -> parent.toString, "authorizedViewId" -> authorizedViewId.toString))
					}
					/** Sets the access control policy on a Bigtable resource. Replaces any existing policy. */
					class setIamPolicy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/bigtable.admin""", """https://www.googleapis.com/auth/bigtable.admin.table""", """https://www.googleapis.com/auth/cloud-bigtable.admin""", """https://www.googleapis.com/auth/cloud-bigtable.admin.table""", """https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withSetIamPolicyRequest(body: Schema.SetIamPolicyRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
					}
					object setIamPolicy {
						def apply(projectsId :PlayApi, instancesId :PlayApi, tablesId :PlayApi, authorizedViewsId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): setIamPolicy = new setIamPolicy(ws.url(BASE_URL + s"v2/projects/${projectsId}/instances/${instancesId}/tables/${tablesId}/authorizedViews/${authorizedViewsId}:setIamPolicy").addQueryStringParameters("resource" -> resource.toString))
					}
				}
			}
		}
		object locations {
			/** Lists information about the supported locations for this service. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListLocationsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/bigtable.admin""", """https://www.googleapis.com/auth/bigtable.admin.cluster""", """https://www.googleapis.com/auth/bigtable.admin.instance""", """https://www.googleapis.com/auth/cloud-bigtable.admin""", """https://www.googleapis.com/auth/cloud-bigtable.admin.cluster""", """https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListLocationsResponse])
			}
			object list {
				def apply(projectsId :PlayApi, name: String, filter: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations").addQueryStringParameters("name" -> name.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListLocationsResponse]] = (fun: list) => fun.apply()
			}
		}
	}
}
