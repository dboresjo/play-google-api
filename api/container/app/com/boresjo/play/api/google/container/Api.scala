package com.boresjo.play.api.google.container

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

	private val BASE_URL = "https://container.googleapis.com/"

	object projects {
		object locations {
			/** Returns configuration info about the Google Kubernetes Engine service. */
			class getServerConfig(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ServerConfig]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ServerConfig])
			}
			object getServerConfig {
				def apply(projectsId :PlayApi, locationsId :PlayApi, name: String, projectId: String, zone: String)(using signer: RequestSigner, ec: ExecutionContext): getServerConfig = new getServerConfig(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/serverConfig").addQueryStringParameters("name" -> name.toString, "projectId" -> projectId.toString, "zone" -> zone.toString))
				given Conversion[getServerConfig, Future[Schema.ServerConfig]] = (fun: getServerConfig) => fun.apply()
			}
			object clusters {
				/** Enables or disables Network Policy for a cluster. */
				class setNetworkPolicy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withSetNetworkPolicyRequest(body: Schema.SetNetworkPolicyRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object setNetworkPolicy {
					def apply(projectsId :PlayApi, locationsId :PlayApi, clustersId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): setNetworkPolicy = new setNetworkPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/clusters/${clustersId}:setNetworkPolicy").addQueryStringParameters("name" -> name.toString))
				}
				/** Sets the monitoring service for a specific cluster. */
				class setMonitoring(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withSetMonitoringServiceRequest(body: Schema.SetMonitoringServiceRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object setMonitoring {
					def apply(projectsId :PlayApi, locationsId :PlayApi, clustersId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): setMonitoring = new setMonitoring(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/clusters/${clustersId}:setMonitoring").addQueryStringParameters("name" -> name.toString))
				}
				/** Starts master IP rotation. */
				class startIpRotation(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withStartIPRotationRequest(body: Schema.StartIPRotationRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object startIpRotation {
					def apply(projectsId :PlayApi, locationsId :PlayApi, clustersId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): startIpRotation = new startIpRotation(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/clusters/${clustersId}:startIpRotation").addQueryStringParameters("name" -> name.toString))
				}
				/** Sets the logging service for a specific cluster. */
				class setLogging(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withSetLoggingServiceRequest(body: Schema.SetLoggingServiceRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object setLogging {
					def apply(projectsId :PlayApi, locationsId :PlayApi, clustersId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): setLogging = new setLogging(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/clusters/${clustersId}:setLogging").addQueryStringParameters("name" -> name.toString))
				}
				/** Sets the maintenance policy for a cluster. */
				class setMaintenancePolicy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withSetMaintenancePolicyRequest(body: Schema.SetMaintenancePolicyRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object setMaintenancePolicy {
					def apply(projectsId :PlayApi, locationsId :PlayApi, clustersId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): setMaintenancePolicy = new setMaintenancePolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/clusters/${clustersId}:setMaintenancePolicy").addQueryStringParameters("name" -> name.toString))
				}
				/** Completes master IP rotation. */
				class completeIpRotation(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withCompleteIPRotationRequest(body: Schema.CompleteIPRotationRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object completeIpRotation {
					def apply(projectsId :PlayApi, locationsId :PlayApi, clustersId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): completeIpRotation = new completeIpRotation(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/clusters/${clustersId}:completeIpRotation").addQueryStringParameters("name" -> name.toString))
				}
				/** Deletes the cluster, including the Kubernetes endpoint and all worker nodes. Firewalls and routes that were configured during cluster creation are also deleted. Other Google Compute Engine resources that might be in use by the cluster, such as load balancer resources, are not deleted if they weren't present when the cluster was initially created. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Operation])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, clustersId :PlayApi, name: String, projectId: String, zone: String, clusterId: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/clusters/${clustersId}").addQueryStringParameters("name" -> name.toString, "projectId" -> projectId.toString, "zone" -> zone.toString, "clusterId" -> clusterId.toString))
					given Conversion[delete, Future[Schema.Operation]] = (fun: delete) => fun.apply()
				}
				/** Sets the locations for a specific cluster. Deprecated. Use [projects.locations.clusters.update](https://cloud.google.com/kubernetes-engine/docs/reference/rest/v1/projects.locations.clusters/update) instead. */
				class setLocations(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withSetLocationsRequest(body: Schema.SetLocationsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object setLocations {
					def apply(projectsId :PlayApi, locationsId :PlayApi, clustersId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): setLocations = new setLocations(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/clusters/${clustersId}:setLocations").addQueryStringParameters("name" -> name.toString))
				}
				/** Updates the settings of a specific cluster. */
				class update(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withUpdateClusterRequest(body: Schema.UpdateClusterRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.Operation])
				}
				object update {
					def apply(projectsId :PlayApi, locationsId :PlayApi, clustersId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/clusters/${clustersId}").addQueryStringParameters("name" -> name.toString))
				}
				/** Gets the public component of the cluster signing keys in JSON Web Key format. */
				class getJwks(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GetJSONWebKeysResponse]) {
					val scopes = Seq()
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GetJSONWebKeysResponse])
				}
				object getJwks {
					def apply(projectsId :PlayApi, locationsId :PlayApi, clustersId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): getJwks = new getJwks(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/clusters/${clustersId}/jwks").addQueryStringParameters("parent" -> parent.toString))
					given Conversion[getJwks, Future[Schema.GetJSONWebKeysResponse]] = (fun: getJwks) => fun.apply()
				}
				/** Lists all clusters owned by a project in either the specified zone or all zones. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListClustersResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListClustersResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, projectId: String, zone: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/clusters").addQueryStringParameters("parent" -> parent.toString, "projectId" -> projectId.toString, "zone" -> zone.toString))
					given Conversion[list, Future[Schema.ListClustersResponse]] = (fun: list) => fun.apply()
				}
				/** Sets labels on a cluster. */
				class setResourceLabels(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withSetLabelsRequest(body: Schema.SetLabelsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object setResourceLabels {
					def apply(projectsId :PlayApi, locationsId :PlayApi, clustersId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): setResourceLabels = new setResourceLabels(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/clusters/${clustersId}:setResourceLabels").addQueryStringParameters("name" -> name.toString))
				}
				/** Checks the cluster compatibility with Autopilot mode, and returns a list of compatibility issues. */
				class checkAutopilotCompatibility(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.CheckAutopilotCompatibilityResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.CheckAutopilotCompatibilityResponse])
				}
				object checkAutopilotCompatibility {
					def apply(projectsId :PlayApi, locationsId :PlayApi, clustersId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): checkAutopilotCompatibility = new checkAutopilotCompatibility(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/clusters/${clustersId}:checkAutopilotCompatibility").addQueryStringParameters("name" -> name.toString))
					given Conversion[checkAutopilotCompatibility, Future[Schema.CheckAutopilotCompatibilityResponse]] = (fun: checkAutopilotCompatibility) => fun.apply()
				}
				/** Sets master auth materials. Currently supports changing the admin password or a specific cluster, either via password generation or explicitly setting the password. */
				class setMasterAuth(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withSetMasterAuthRequest(body: Schema.SetMasterAuthRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object setMasterAuth {
					def apply(projectsId :PlayApi, locationsId :PlayApi, clustersId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): setMasterAuth = new setMasterAuth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/clusters/${clustersId}:setMasterAuth").addQueryStringParameters("name" -> name.toString))
				}
				/** Creates a cluster, consisting of the specified number and type of Google Compute Engine instances. By default, the cluster is created in the project's [default network](https://cloud.google.com/compute/docs/networks-and-firewalls#networks). One firewall is added for the cluster. After cluster creation, the Kubelet creates routes for each node to allow the containers on that node to communicate with all other instances in the cluster. Finally, an entry is added to the project's global metadata indicating which CIDR range the cluster is using. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withCreateClusterRequest(body: Schema.CreateClusterRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/clusters").addQueryStringParameters("parent" -> parent.toString))
				}
				/** Sets the addons for a specific cluster. */
				class setAddons(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withSetAddonsConfigRequest(body: Schema.SetAddonsConfigRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object setAddons {
					def apply(projectsId :PlayApi, locationsId :PlayApi, clustersId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): setAddons = new setAddons(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/clusters/${clustersId}:setAddons").addQueryStringParameters("name" -> name.toString))
				}
				/** Updates the master for a specific cluster. */
				class updateMaster(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withUpdateMasterRequest(body: Schema.UpdateMasterRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object updateMaster {
					def apply(projectsId :PlayApi, locationsId :PlayApi, clustersId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): updateMaster = new updateMaster(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/clusters/${clustersId}:updateMaster").addQueryStringParameters("name" -> name.toString))
				}
				/** Gets the details of a specific cluster. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Cluster]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Cluster])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, clustersId :PlayApi, name: String, projectId: String, zone: String, clusterId: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/clusters/${clustersId}").addQueryStringParameters("name" -> name.toString, "projectId" -> projectId.toString, "zone" -> zone.toString, "clusterId" -> clusterId.toString))
					given Conversion[get, Future[Schema.Cluster]] = (fun: get) => fun.apply()
				}
				/** Enables or disables the ABAC authorization mechanism on a cluster. */
				class setLegacyAbac(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withSetLegacyAbacRequest(body: Schema.SetLegacyAbacRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object setLegacyAbac {
					def apply(projectsId :PlayApi, locationsId :PlayApi, clustersId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): setLegacyAbac = new setLegacyAbac(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/clusters/${clustersId}:setLegacyAbac").addQueryStringParameters("name" -> name.toString))
				}
				object well_known {
					/** Gets the OIDC discovery document for the cluster. See the [OpenID Connect Discovery 1.0 specification](https://openid.net/specs/openid-connect-discovery-1_0.html) for details. */
					class getOpenid_configuration(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GetOpenIDConfigResponse]) {
						val scopes = Seq()
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GetOpenIDConfigResponse])
					}
					object getOpenid_configuration {
						def apply(projectsId :PlayApi, locationsId :PlayApi, clustersId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): getOpenid_configuration = new getOpenid_configuration(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/clusters/${clustersId}/.well-known/openid-configuration").addQueryStringParameters("parent" -> parent.toString))
						given Conversion[getOpenid_configuration, Future[Schema.GetOpenIDConfigResponse]] = (fun: getOpenid_configuration) => fun.apply()
					}
				}
				object nodePools {
					/** Creates a node pool for a cluster. */
					class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withCreateNodePoolRequest(body: Schema.CreateNodePoolRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
					}
					object create {
						def apply(projectsId :PlayApi, locationsId :PlayApi, clustersId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/clusters/${clustersId}/nodePools").addQueryStringParameters("parent" -> parent.toString))
					}
					/** Deletes a node pool from a cluster. */
					class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Operation])
					}
					object delete {
						def apply(projectsId :PlayApi, locationsId :PlayApi, clustersId :PlayApi, nodePoolsId :PlayApi, name: String, projectId: String, zone: String, clusterId: String, nodePoolId: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/clusters/${clustersId}/nodePools/${nodePoolsId}").addQueryStringParameters("name" -> name.toString, "projectId" -> projectId.toString, "zone" -> zone.toString, "clusterId" -> clusterId.toString, "nodePoolId" -> nodePoolId.toString))
						given Conversion[delete, Future[Schema.Operation]] = (fun: delete) => fun.apply()
					}
					/** Sets the NodeManagement options for a node pool. */
					class setManagement(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withSetNodePoolManagementRequest(body: Schema.SetNodePoolManagementRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
					}
					object setManagement {
						def apply(projectsId :PlayApi, locationsId :PlayApi, clustersId :PlayApi, nodePoolsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): setManagement = new setManagement(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/clusters/${clustersId}/nodePools/${nodePoolsId}:setManagement").addQueryStringParameters("name" -> name.toString))
					}
					/** Retrieves the requested node pool. */
					class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.NodePool]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.NodePool])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, clustersId :PlayApi, nodePoolsId :PlayApi, name: String, projectId: String, zone: String, clusterId: String, nodePoolId: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/clusters/${clustersId}/nodePools/${nodePoolsId}").addQueryStringParameters("name" -> name.toString, "projectId" -> projectId.toString, "zone" -> zone.toString, "clusterId" -> clusterId.toString, "nodePoolId" -> nodePoolId.toString))
						given Conversion[get, Future[Schema.NodePool]] = (fun: get) => fun.apply()
					}
					/** Updates the version and/or image type for the specified node pool. */
					class update(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withUpdateNodePoolRequest(body: Schema.UpdateNodePoolRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.Operation])
					}
					object update {
						def apply(projectsId :PlayApi, locationsId :PlayApi, clustersId :PlayApi, nodePoolsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/clusters/${clustersId}/nodePools/${nodePoolsId}").addQueryStringParameters("name" -> name.toString))
					}
					/** Sets the autoscaling settings for the specified node pool. */
					class setAutoscaling(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withSetNodePoolAutoscalingRequest(body: Schema.SetNodePoolAutoscalingRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
					}
					object setAutoscaling {
						def apply(projectsId :PlayApi, locationsId :PlayApi, clustersId :PlayApi, nodePoolsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): setAutoscaling = new setAutoscaling(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/clusters/${clustersId}/nodePools/${nodePoolsId}:setAutoscaling").addQueryStringParameters("name" -> name.toString))
					}
					/** Rolls back a previously Aborted or Failed NodePool upgrade. This makes no changes if the last upgrade successfully completed. */
					class rollback(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withRollbackNodePoolUpgradeRequest(body: Schema.RollbackNodePoolUpgradeRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
					}
					object rollback {
						def apply(projectsId :PlayApi, locationsId :PlayApi, clustersId :PlayApi, nodePoolsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): rollback = new rollback(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/clusters/${clustersId}/nodePools/${nodePoolsId}:rollback").addQueryStringParameters("name" -> name.toString))
					}
					/** CompleteNodePoolUpgrade will signal an on-going node pool upgrade to complete. */
					class completeUpgrade(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withCompleteNodePoolUpgradeRequest(body: Schema.CompleteNodePoolUpgradeRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Empty])
					}
					object completeUpgrade {
						def apply(projectsId :PlayApi, locationsId :PlayApi, clustersId :PlayApi, nodePoolsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): completeUpgrade = new completeUpgrade(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/clusters/${clustersId}/nodePools/${nodePoolsId}:completeUpgrade").addQueryStringParameters("name" -> name.toString))
					}
					/** Lists the node pools for a cluster. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListNodePoolsResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListNodePoolsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, clustersId :PlayApi, parent: String, projectId: String, zone: String, clusterId: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/clusters/${clustersId}/nodePools").addQueryStringParameters("parent" -> parent.toString, "projectId" -> projectId.toString, "zone" -> zone.toString, "clusterId" -> clusterId.toString))
						given Conversion[list, Future[Schema.ListNodePoolsResponse]] = (fun: list) => fun.apply()
					}
					/** Sets the size for a specific node pool. The new size will be used for all replicas, including future replicas created by modifying NodePool.locations. */
					class setSize(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withSetNodePoolSizeRequest(body: Schema.SetNodePoolSizeRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
					}
					object setSize {
						def apply(projectsId :PlayApi, locationsId :PlayApi, clustersId :PlayApi, nodePoolsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): setSize = new setSize(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/clusters/${clustersId}/nodePools/${nodePoolsId}:setSize").addQueryStringParameters("name" -> name.toString))
					}
				}
			}
			object operations {
				/** Lists all operations in a project in a specific zone or all zones. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListOperationsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListOperationsResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, projectId: String, zone: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/operations").addQueryStringParameters("parent" -> parent.toString, "projectId" -> projectId.toString, "zone" -> zone.toString))
					given Conversion[list, Future[Schema.ListOperationsResponse]] = (fun: list) => fun.apply()
				}
				/** Gets the specified operation. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Operation])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, operationsId :PlayApi, name: String, projectId: String, zone: String, operationId: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/operations/${operationsId}").addQueryStringParameters("name" -> name.toString, "projectId" -> projectId.toString, "zone" -> zone.toString, "operationId" -> operationId.toString))
					given Conversion[get, Future[Schema.Operation]] = (fun: get) => fun.apply()
				}
				/** Cancels the specified operation. */
				class cancel(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withCancelOperationRequest(body: Schema.CancelOperationRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Empty])
				}
				object cancel {
					def apply(projectsId :PlayApi, locationsId :PlayApi, operationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): cancel = new cancel(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/operations/${operationsId}:cancel").addQueryStringParameters("name" -> name.toString))
				}
			}
		}
		object zones {
			/** Returns configuration info about the Google Kubernetes Engine service. */
			class getServerconfig(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ServerConfig]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ServerConfig])
			}
			object getServerconfig {
				def apply(projectId: String, zone: String, name: String)(using signer: RequestSigner, ec: ExecutionContext): getServerconfig = new getServerconfig(ws.url(BASE_URL + s"v1/projects/${projectId}/zones/${zone}/serverconfig").addQueryStringParameters("name" -> name.toString))
				given Conversion[getServerconfig, Future[Schema.ServerConfig]] = (fun: getServerconfig) => fun.apply()
			}
			object clusters {
				/** Sets the logging service for a specific cluster. */
				class logging(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withSetLoggingServiceRequest(body: Schema.SetLoggingServiceRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object logging {
					def apply(projectId: String, zone: String, clusterId: String)(using signer: RequestSigner, ec: ExecutionContext): logging = new logging(ws.url(BASE_URL + s"v1/projects/${projectId}/zones/${zone}/clusters/${clusterId}/logging").addQueryStringParameters())
				}
				/** Updates the master for a specific cluster. */
				class master(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withUpdateMasterRequest(body: Schema.UpdateMasterRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object master {
					def apply(projectId: String, zone: String, clusterId: String)(using signer: RequestSigner, ec: ExecutionContext): master = new master(ws.url(BASE_URL + s"v1/projects/${projectId}/zones/${zone}/clusters/${clusterId}/master").addQueryStringParameters())
				}
				/** Sets the monitoring service for a specific cluster. */
				class monitoring(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withSetMonitoringServiceRequest(body: Schema.SetMonitoringServiceRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object monitoring {
					def apply(projectId: String, zone: String, clusterId: String)(using signer: RequestSigner, ec: ExecutionContext): monitoring = new monitoring(ws.url(BASE_URL + s"v1/projects/${projectId}/zones/${zone}/clusters/${clusterId}/monitoring").addQueryStringParameters())
				}
				/** Completes master IP rotation. */
				class completeIpRotation(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withCompleteIPRotationRequest(body: Schema.CompleteIPRotationRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object completeIpRotation {
					def apply(projectId: String, zone: String, clusterId: String)(using signer: RequestSigner, ec: ExecutionContext): completeIpRotation = new completeIpRotation(ws.url(BASE_URL + s"v1/projects/${projectId}/zones/${zone}/clusters/${clusterId}:completeIpRotation").addQueryStringParameters())
				}
				/** Deletes the cluster, including the Kubernetes endpoint and all worker nodes. Firewalls and routes that were configured during cluster creation are also deleted. Other Google Compute Engine resources that might be in use by the cluster, such as load balancer resources, are not deleted if they weren't present when the cluster was initially created. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Operation])
				}
				object delete {
					def apply(projectId: String, zone: String, clusterId: String, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectId}/zones/${zone}/clusters/${clusterId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Operation]] = (fun: delete) => fun.apply()
				}
				/** Gets the details of a specific cluster. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Cluster]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Cluster])
				}
				object get {
					def apply(projectId: String, zone: String, clusterId: String, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectId}/zones/${zone}/clusters/${clusterId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.Cluster]] = (fun: get) => fun.apply()
				}
				/** Updates the settings of a specific cluster. */
				class update(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withUpdateClusterRequest(body: Schema.UpdateClusterRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.Operation])
				}
				object update {
					def apply(projectId: String, zone: String, clusterId: String)(using signer: RequestSigner, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"v1/projects/${projectId}/zones/${zone}/clusters/${clusterId}").addQueryStringParameters())
				}
				/** Sets the addons for a specific cluster. */
				class addons(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withSetAddonsConfigRequest(body: Schema.SetAddonsConfigRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object addons {
					def apply(projectId: String, zone: String, clusterId: String)(using signer: RequestSigner, ec: ExecutionContext): addons = new addons(ws.url(BASE_URL + s"v1/projects/${projectId}/zones/${zone}/clusters/${clusterId}/addons").addQueryStringParameters())
				}
				/** Lists all clusters owned by a project in either the specified zone or all zones. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListClustersResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListClustersResponse])
				}
				object list {
					def apply(projectId: String, zone: String, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectId}/zones/${zone}/clusters").addQueryStringParameters("parent" -> parent.toString))
					given Conversion[list, Future[Schema.ListClustersResponse]] = (fun: list) => fun.apply()
				}
				/** Sets the locations for a specific cluster. Deprecated. Use [projects.locations.clusters.update](https://cloud.google.com/kubernetes-engine/docs/reference/rest/v1/projects.locations.clusters/update) instead. */
				class locations(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withSetLocationsRequest(body: Schema.SetLocationsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object locations {
					def apply(projectId: String, zone: String, clusterId: String)(using signer: RequestSigner, ec: ExecutionContext): locations = new locations(ws.url(BASE_URL + s"v1/projects/${projectId}/zones/${zone}/clusters/${clusterId}/locations").addQueryStringParameters())
				}
				/** Enables or disables Network Policy for a cluster. */
				class setNetworkPolicy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withSetNetworkPolicyRequest(body: Schema.SetNetworkPolicyRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object setNetworkPolicy {
					def apply(projectId: String, zone: String, clusterId: String)(using signer: RequestSigner, ec: ExecutionContext): setNetworkPolicy = new setNetworkPolicy(ws.url(BASE_URL + s"v1/projects/${projectId}/zones/${zone}/clusters/${clusterId}:setNetworkPolicy").addQueryStringParameters())
				}
				/** Sets master auth materials. Currently supports changing the admin password or a specific cluster, either via password generation or explicitly setting the password. */
				class setMasterAuth(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withSetMasterAuthRequest(body: Schema.SetMasterAuthRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object setMasterAuth {
					def apply(projectId: String, zone: String, clusterId: String)(using signer: RequestSigner, ec: ExecutionContext): setMasterAuth = new setMasterAuth(ws.url(BASE_URL + s"v1/projects/${projectId}/zones/${zone}/clusters/${clusterId}:setMasterAuth").addQueryStringParameters())
				}
				/** Creates a cluster, consisting of the specified number and type of Google Compute Engine instances. By default, the cluster is created in the project's [default network](https://cloud.google.com/compute/docs/networks-and-firewalls#networks). One firewall is added for the cluster. After cluster creation, the Kubelet creates routes for each node to allow the containers on that node to communicate with all other instances in the cluster. Finally, an entry is added to the project's global metadata indicating which CIDR range the cluster is using. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withCreateClusterRequest(body: Schema.CreateClusterRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object create {
					def apply(projectId: String, zone: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectId}/zones/${zone}/clusters").addQueryStringParameters())
				}
				/** Starts master IP rotation. */
				class startIpRotation(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withStartIPRotationRequest(body: Schema.StartIPRotationRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object startIpRotation {
					def apply(projectId: String, zone: String, clusterId: String)(using signer: RequestSigner, ec: ExecutionContext): startIpRotation = new startIpRotation(ws.url(BASE_URL + s"v1/projects/${projectId}/zones/${zone}/clusters/${clusterId}:startIpRotation").addQueryStringParameters())
				}
				/** Enables or disables the ABAC authorization mechanism on a cluster. */
				class legacyAbac(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withSetLegacyAbacRequest(body: Schema.SetLegacyAbacRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object legacyAbac {
					def apply(projectId: String, zone: String, clusterId: String)(using signer: RequestSigner, ec: ExecutionContext): legacyAbac = new legacyAbac(ws.url(BASE_URL + s"v1/projects/${projectId}/zones/${zone}/clusters/${clusterId}/legacyAbac").addQueryStringParameters())
				}
				/** Sets the maintenance policy for a cluster. */
				class setMaintenancePolicy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withSetMaintenancePolicyRequest(body: Schema.SetMaintenancePolicyRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object setMaintenancePolicy {
					def apply(projectId: String, zone: String, clusterId: String)(using signer: RequestSigner, ec: ExecutionContext): setMaintenancePolicy = new setMaintenancePolicy(ws.url(BASE_URL + s"v1/projects/${projectId}/zones/${zone}/clusters/${clusterId}:setMaintenancePolicy").addQueryStringParameters())
				}
				/** Sets labels on a cluster. */
				class resourceLabels(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withSetLabelsRequest(body: Schema.SetLabelsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object resourceLabels {
					def apply(projectId: String, zone: String, clusterId: String)(using signer: RequestSigner, ec: ExecutionContext): resourceLabels = new resourceLabels(ws.url(BASE_URL + s"v1/projects/${projectId}/zones/${zone}/clusters/${clusterId}/resourceLabels").addQueryStringParameters())
				}
				object nodePools {
					/** Creates a node pool for a cluster. */
					class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withCreateNodePoolRequest(body: Schema.CreateNodePoolRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
					}
					object create {
						def apply(projectId: String, zone: String, clusterId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectId}/zones/${zone}/clusters/${clusterId}/nodePools").addQueryStringParameters())
					}
					/** Sets the autoscaling settings for the specified node pool. */
					class autoscaling(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withSetNodePoolAutoscalingRequest(body: Schema.SetNodePoolAutoscalingRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
					}
					object autoscaling {
						def apply(projectId: String, zone: String, clusterId: String, nodePoolId: String)(using signer: RequestSigner, ec: ExecutionContext): autoscaling = new autoscaling(ws.url(BASE_URL + s"v1/projects/${projectId}/zones/${zone}/clusters/${clusterId}/nodePools/${nodePoolId}/autoscaling").addQueryStringParameters())
					}
					/** Rolls back a previously Aborted or Failed NodePool upgrade. This makes no changes if the last upgrade successfully completed. */
					class rollback(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withRollbackNodePoolUpgradeRequest(body: Schema.RollbackNodePoolUpgradeRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
					}
					object rollback {
						def apply(projectId: String, zone: String, clusterId: String, nodePoolId: String)(using signer: RequestSigner, ec: ExecutionContext): rollback = new rollback(ws.url(BASE_URL + s"v1/projects/${projectId}/zones/${zone}/clusters/${clusterId}/nodePools/${nodePoolId}:rollback").addQueryStringParameters())
					}
					/** Deletes a node pool from a cluster. */
					class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Operation])
					}
					object delete {
						def apply(projectId: String, zone: String, clusterId: String, nodePoolId: String, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectId}/zones/${zone}/clusters/${clusterId}/nodePools/${nodePoolId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[delete, Future[Schema.Operation]] = (fun: delete) => fun.apply()
					}
					/** Sets the NodeManagement options for a node pool. */
					class setManagement(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withSetNodePoolManagementRequest(body: Schema.SetNodePoolManagementRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
					}
					object setManagement {
						def apply(projectId: String, zone: String, clusterId: String, nodePoolId: String)(using signer: RequestSigner, ec: ExecutionContext): setManagement = new setManagement(ws.url(BASE_URL + s"v1/projects/${projectId}/zones/${zone}/clusters/${clusterId}/nodePools/${nodePoolId}/setManagement").addQueryStringParameters())
					}
					/** Retrieves the requested node pool. */
					class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.NodePool]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.NodePool])
					}
					object get {
						def apply(projectId: String, zone: String, clusterId: String, nodePoolId: String, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectId}/zones/${zone}/clusters/${clusterId}/nodePools/${nodePoolId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.NodePool]] = (fun: get) => fun.apply()
					}
					/** Updates the version and/or image type for the specified node pool. */
					class update(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withUpdateNodePoolRequest(body: Schema.UpdateNodePoolRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
					}
					object update {
						def apply(projectId: String, zone: String, clusterId: String, nodePoolId: String)(using signer: RequestSigner, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"v1/projects/${projectId}/zones/${zone}/clusters/${clusterId}/nodePools/${nodePoolId}/update").addQueryStringParameters())
					}
					/** Lists the node pools for a cluster. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListNodePoolsResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListNodePoolsResponse])
					}
					object list {
						def apply(projectId: String, zone: String, clusterId: String, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectId}/zones/${zone}/clusters/${clusterId}/nodePools").addQueryStringParameters("parent" -> parent.toString))
						given Conversion[list, Future[Schema.ListNodePoolsResponse]] = (fun: list) => fun.apply()
					}
					/** Sets the size for a specific node pool. The new size will be used for all replicas, including future replicas created by modifying NodePool.locations. */
					class setSize(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withSetNodePoolSizeRequest(body: Schema.SetNodePoolSizeRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
					}
					object setSize {
						def apply(projectId: String, zone: String, clusterId: String, nodePoolId: String)(using signer: RequestSigner, ec: ExecutionContext): setSize = new setSize(ws.url(BASE_URL + s"v1/projects/${projectId}/zones/${zone}/clusters/${clusterId}/nodePools/${nodePoolId}/setSize").addQueryStringParameters())
					}
				}
			}
			object operations {
				/** Lists all operations in a project in a specific zone or all zones. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListOperationsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListOperationsResponse])
				}
				object list {
					def apply(projectId: String, zone: String, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectId}/zones/${zone}/operations").addQueryStringParameters("parent" -> parent.toString))
					given Conversion[list, Future[Schema.ListOperationsResponse]] = (fun: list) => fun.apply()
				}
				/** Gets the specified operation. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Operation])
				}
				object get {
					def apply(projectId: String, zone: String, operationId: String, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectId}/zones/${zone}/operations/${operationId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.Operation]] = (fun: get) => fun.apply()
				}
				/** Cancels the specified operation. */
				class cancel(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withCancelOperationRequest(body: Schema.CancelOperationRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Empty])
				}
				object cancel {
					def apply(projectId: String, zone: String, operationId: String)(using signer: RequestSigner, ec: ExecutionContext): cancel = new cancel(ws.url(BASE_URL + s"v1/projects/${projectId}/zones/${zone}/operations/${operationId}:cancel").addQueryStringParameters())
				}
			}
		}
		object aggregated {
			object usableSubnetworks {
				/** Lists subnetworks that are usable for creating clusters in a project. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListUsableSubnetworksResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListUsableSubnetworksResponse])
				}
				object list {
					def apply(projectsId :PlayApi, parent: String, filter: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/aggregated/usableSubnetworks").addQueryStringParameters("parent" -> parent.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListUsableSubnetworksResponse]] = (fun: list) => fun.apply()
				}
			}
		}
	}
}
