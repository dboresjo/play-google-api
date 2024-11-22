package com.boresjo.play.api.google.container

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://container.googleapis.com/"

	object projects {
		object locations {
			class getServerConfig(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ServerConfig]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ServerConfig])
			}
			object getServerConfig {
				def apply(projectsId :PlayApi, locationsId :PlayApi, name: String, projectId: String, zone: String)(using auth: AuthToken, ec: ExecutionContext): getServerConfig = new getServerConfig(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/serverConfig").addQueryStringParameters("name" -> name.toString, "projectId" -> projectId.toString, "zone" -> zone.toString))
				given Conversion[getServerConfig, Future[Schema.ServerConfig]] = (fun: getServerConfig) => fun.apply()
			}
			object clusters {
				class setNetworkPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withSetNetworkPolicyRequest(body: Schema.SetNetworkPolicyRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object setNetworkPolicy {
					def apply(projectsId :PlayApi, locationsId :PlayApi, clustersId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): setNetworkPolicy = new setNetworkPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/clusters/${clustersId}:setNetworkPolicy").addQueryStringParameters("name" -> name.toString))
				}
				class setMonitoring(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withSetMonitoringServiceRequest(body: Schema.SetMonitoringServiceRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object setMonitoring {
					def apply(projectsId :PlayApi, locationsId :PlayApi, clustersId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): setMonitoring = new setMonitoring(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/clusters/${clustersId}:setMonitoring").addQueryStringParameters("name" -> name.toString))
				}
				class startIpRotation(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withStartIPRotationRequest(body: Schema.StartIPRotationRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object startIpRotation {
					def apply(projectsId :PlayApi, locationsId :PlayApi, clustersId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): startIpRotation = new startIpRotation(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/clusters/${clustersId}:startIpRotation").addQueryStringParameters("name" -> name.toString))
				}
				class setLogging(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withSetLoggingServiceRequest(body: Schema.SetLoggingServiceRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object setLogging {
					def apply(projectsId :PlayApi, locationsId :PlayApi, clustersId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): setLogging = new setLogging(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/clusters/${clustersId}:setLogging").addQueryStringParameters("name" -> name.toString))
				}
				class setMaintenancePolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withSetMaintenancePolicyRequest(body: Schema.SetMaintenancePolicyRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object setMaintenancePolicy {
					def apply(projectsId :PlayApi, locationsId :PlayApi, clustersId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): setMaintenancePolicy = new setMaintenancePolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/clusters/${clustersId}:setMaintenancePolicy").addQueryStringParameters("name" -> name.toString))
				}
				class completeIpRotation(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withCompleteIPRotationRequest(body: Schema.CompleteIPRotationRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object completeIpRotation {
					def apply(projectsId :PlayApi, locationsId :PlayApi, clustersId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): completeIpRotation = new completeIpRotation(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/clusters/${clustersId}:completeIpRotation").addQueryStringParameters("name" -> name.toString))
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
					def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Operation])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, clustersId :PlayApi, name: String, projectId: String, zone: String, clusterId: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/clusters/${clustersId}").addQueryStringParameters("name" -> name.toString, "projectId" -> projectId.toString, "zone" -> zone.toString, "clusterId" -> clusterId.toString))
					given Conversion[delete, Future[Schema.Operation]] = (fun: delete) => fun.apply()
				}
				class setLocations(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withSetLocationsRequest(body: Schema.SetLocationsRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object setLocations {
					def apply(projectsId :PlayApi, locationsId :PlayApi, clustersId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): setLocations = new setLocations(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/clusters/${clustersId}:setLocations").addQueryStringParameters("name" -> name.toString))
				}
				class update(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withUpdateClusterRequest(body: Schema.UpdateClusterRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.Operation])
				}
				object update {
					def apply(projectsId :PlayApi, locationsId :PlayApi, clustersId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/clusters/${clustersId}").addQueryStringParameters("name" -> name.toString))
				}
				class getJwks(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GetJSONWebKeysResponse]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GetJSONWebKeysResponse])
				}
				object getJwks {
					def apply(projectsId :PlayApi, locationsId :PlayApi, clustersId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): getJwks = new getJwks(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/clusters/${clustersId}/jwks").addQueryStringParameters("parent" -> parent.toString))
					given Conversion[getJwks, Future[Schema.GetJSONWebKeysResponse]] = (fun: getJwks) => fun.apply()
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListClustersResponse]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListClustersResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, projectId: String, zone: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/clusters").addQueryStringParameters("parent" -> parent.toString, "projectId" -> projectId.toString, "zone" -> zone.toString))
					given Conversion[list, Future[Schema.ListClustersResponse]] = (fun: list) => fun.apply()
				}
				class setResourceLabels(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withSetLabelsRequest(body: Schema.SetLabelsRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object setResourceLabels {
					def apply(projectsId :PlayApi, locationsId :PlayApi, clustersId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): setResourceLabels = new setResourceLabels(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/clusters/${clustersId}:setResourceLabels").addQueryStringParameters("name" -> name.toString))
				}
				class checkAutopilotCompatibility(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.CheckAutopilotCompatibilityResponse]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.CheckAutopilotCompatibilityResponse])
				}
				object checkAutopilotCompatibility {
					def apply(projectsId :PlayApi, locationsId :PlayApi, clustersId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): checkAutopilotCompatibility = new checkAutopilotCompatibility(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/clusters/${clustersId}:checkAutopilotCompatibility").addQueryStringParameters("name" -> name.toString))
					given Conversion[checkAutopilotCompatibility, Future[Schema.CheckAutopilotCompatibilityResponse]] = (fun: checkAutopilotCompatibility) => fun.apply()
				}
				class setMasterAuth(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withSetMasterAuthRequest(body: Schema.SetMasterAuthRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object setMasterAuth {
					def apply(projectsId :PlayApi, locationsId :PlayApi, clustersId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): setMasterAuth = new setMasterAuth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/clusters/${clustersId}:setMasterAuth").addQueryStringParameters("name" -> name.toString))
				}
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withCreateClusterRequest(body: Schema.CreateClusterRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/clusters").addQueryStringParameters("parent" -> parent.toString))
				}
				class setAddons(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withSetAddonsConfigRequest(body: Schema.SetAddonsConfigRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object setAddons {
					def apply(projectsId :PlayApi, locationsId :PlayApi, clustersId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): setAddons = new setAddons(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/clusters/${clustersId}:setAddons").addQueryStringParameters("name" -> name.toString))
				}
				class updateMaster(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withUpdateMasterRequest(body: Schema.UpdateMasterRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object updateMaster {
					def apply(projectsId :PlayApi, locationsId :PlayApi, clustersId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): updateMaster = new updateMaster(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/clusters/${clustersId}:updateMaster").addQueryStringParameters("name" -> name.toString))
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Cluster]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Cluster])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, clustersId :PlayApi, name: String, projectId: String, zone: String, clusterId: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/clusters/${clustersId}").addQueryStringParameters("name" -> name.toString, "projectId" -> projectId.toString, "zone" -> zone.toString, "clusterId" -> clusterId.toString))
					given Conversion[get, Future[Schema.Cluster]] = (fun: get) => fun.apply()
				}
				class setLegacyAbac(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withSetLegacyAbacRequest(body: Schema.SetLegacyAbacRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object setLegacyAbac {
					def apply(projectsId :PlayApi, locationsId :PlayApi, clustersId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): setLegacyAbac = new setLegacyAbac(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/clusters/${clustersId}:setLegacyAbac").addQueryStringParameters("name" -> name.toString))
				}
				object well_known {
					class getOpenid_configuration(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GetOpenIDConfigResponse]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GetOpenIDConfigResponse])
					}
					object getOpenid_configuration {
						def apply(projectsId :PlayApi, locationsId :PlayApi, clustersId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): getOpenid_configuration = new getOpenid_configuration(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/clusters/${clustersId}/.well-known/openid-configuration").addQueryStringParameters("parent" -> parent.toString))
						given Conversion[getOpenid_configuration, Future[Schema.GetOpenIDConfigResponse]] = (fun: getOpenid_configuration) => fun.apply()
					}
				}
				object nodePools {
					class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withCreateNodePoolRequest(body: Schema.CreateNodePoolRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
					}
					object create {
						def apply(projectsId :PlayApi, locationsId :PlayApi, clustersId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/clusters/${clustersId}/nodePools").addQueryStringParameters("parent" -> parent.toString))
					}
					class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
						def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Operation])
					}
					object delete {
						def apply(projectsId :PlayApi, locationsId :PlayApi, clustersId :PlayApi, nodePoolsId :PlayApi, name: String, projectId: String, zone: String, clusterId: String, nodePoolId: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/clusters/${clustersId}/nodePools/${nodePoolsId}").addQueryStringParameters("name" -> name.toString, "projectId" -> projectId.toString, "zone" -> zone.toString, "clusterId" -> clusterId.toString, "nodePoolId" -> nodePoolId.toString))
						given Conversion[delete, Future[Schema.Operation]] = (fun: delete) => fun.apply()
					}
					class setManagement(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withSetNodePoolManagementRequest(body: Schema.SetNodePoolManagementRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
					}
					object setManagement {
						def apply(projectsId :PlayApi, locationsId :PlayApi, clustersId :PlayApi, nodePoolsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): setManagement = new setManagement(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/clusters/${clustersId}/nodePools/${nodePoolsId}:setManagement").addQueryStringParameters("name" -> name.toString))
					}
					class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.NodePool]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.NodePool])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, clustersId :PlayApi, nodePoolsId :PlayApi, name: String, projectId: String, zone: String, clusterId: String, nodePoolId: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/clusters/${clustersId}/nodePools/${nodePoolsId}").addQueryStringParameters("name" -> name.toString, "projectId" -> projectId.toString, "zone" -> zone.toString, "clusterId" -> clusterId.toString, "nodePoolId" -> nodePoolId.toString))
						given Conversion[get, Future[Schema.NodePool]] = (fun: get) => fun.apply()
					}
					class update(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withUpdateNodePoolRequest(body: Schema.UpdateNodePoolRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.Operation])
					}
					object update {
						def apply(projectsId :PlayApi, locationsId :PlayApi, clustersId :PlayApi, nodePoolsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/clusters/${clustersId}/nodePools/${nodePoolsId}").addQueryStringParameters("name" -> name.toString))
					}
					class setAutoscaling(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withSetNodePoolAutoscalingRequest(body: Schema.SetNodePoolAutoscalingRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
					}
					object setAutoscaling {
						def apply(projectsId :PlayApi, locationsId :PlayApi, clustersId :PlayApi, nodePoolsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): setAutoscaling = new setAutoscaling(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/clusters/${clustersId}/nodePools/${nodePoolsId}:setAutoscaling").addQueryStringParameters("name" -> name.toString))
					}
					class rollback(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withRollbackNodePoolUpgradeRequest(body: Schema.RollbackNodePoolUpgradeRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
					}
					object rollback {
						def apply(projectsId :PlayApi, locationsId :PlayApi, clustersId :PlayApi, nodePoolsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): rollback = new rollback(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/clusters/${clustersId}/nodePools/${nodePoolsId}:rollback").addQueryStringParameters("name" -> name.toString))
					}
					class completeUpgrade(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withCompleteNodePoolUpgradeRequest(body: Schema.CompleteNodePoolUpgradeRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Empty])
					}
					object completeUpgrade {
						def apply(projectsId :PlayApi, locationsId :PlayApi, clustersId :PlayApi, nodePoolsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): completeUpgrade = new completeUpgrade(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/clusters/${clustersId}/nodePools/${nodePoolsId}:completeUpgrade").addQueryStringParameters("name" -> name.toString))
					}
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListNodePoolsResponse]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListNodePoolsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, clustersId :PlayApi, parent: String, projectId: String, zone: String, clusterId: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/clusters/${clustersId}/nodePools").addQueryStringParameters("parent" -> parent.toString, "projectId" -> projectId.toString, "zone" -> zone.toString, "clusterId" -> clusterId.toString))
						given Conversion[list, Future[Schema.ListNodePoolsResponse]] = (fun: list) => fun.apply()
					}
					class setSize(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withSetNodePoolSizeRequest(body: Schema.SetNodePoolSizeRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
					}
					object setSize {
						def apply(projectsId :PlayApi, locationsId :PlayApi, clustersId :PlayApi, nodePoolsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): setSize = new setSize(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/clusters/${clustersId}/nodePools/${nodePoolsId}:setSize").addQueryStringParameters("name" -> name.toString))
					}
				}
			}
			object operations {
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListOperationsResponse]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListOperationsResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, projectId: String, zone: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/operations").addQueryStringParameters("parent" -> parent.toString, "projectId" -> projectId.toString, "zone" -> zone.toString))
					given Conversion[list, Future[Schema.ListOperationsResponse]] = (fun: list) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Operation])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, operationsId :PlayApi, name: String, projectId: String, zone: String, operationId: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/operations/${operationsId}").addQueryStringParameters("name" -> name.toString, "projectId" -> projectId.toString, "zone" -> zone.toString, "operationId" -> operationId.toString))
					given Conversion[get, Future[Schema.Operation]] = (fun: get) => fun.apply()
				}
				class cancel(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withCancelOperationRequest(body: Schema.CancelOperationRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Empty])
				}
				object cancel {
					def apply(projectsId :PlayApi, locationsId :PlayApi, operationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): cancel = new cancel(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/operations/${operationsId}:cancel").addQueryStringParameters("name" -> name.toString))
				}
			}
		}
		object zones {
			class getServerconfig(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ServerConfig]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ServerConfig])
			}
			object getServerconfig {
				def apply(projectId: String, zone: String, name: String)(using auth: AuthToken, ec: ExecutionContext): getServerconfig = new getServerconfig(ws.url(BASE_URL + s"v1/projects/${projectId}/zones/${zone}/serverconfig").addQueryStringParameters("name" -> name.toString))
				given Conversion[getServerconfig, Future[Schema.ServerConfig]] = (fun: getServerconfig) => fun.apply()
			}
			object clusters {
				class logging(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withSetLoggingServiceRequest(body: Schema.SetLoggingServiceRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object logging {
					def apply(projectId: String, zone: String, clusterId: String)(using auth: AuthToken, ec: ExecutionContext): logging = new logging(ws.url(BASE_URL + s"v1/projects/${projectId}/zones/${zone}/clusters/${clusterId}/logging").addQueryStringParameters())
				}
				class master(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withUpdateMasterRequest(body: Schema.UpdateMasterRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object master {
					def apply(projectId: String, zone: String, clusterId: String)(using auth: AuthToken, ec: ExecutionContext): master = new master(ws.url(BASE_URL + s"v1/projects/${projectId}/zones/${zone}/clusters/${clusterId}/master").addQueryStringParameters())
				}
				class monitoring(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withSetMonitoringServiceRequest(body: Schema.SetMonitoringServiceRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object monitoring {
					def apply(projectId: String, zone: String, clusterId: String)(using auth: AuthToken, ec: ExecutionContext): monitoring = new monitoring(ws.url(BASE_URL + s"v1/projects/${projectId}/zones/${zone}/clusters/${clusterId}/monitoring").addQueryStringParameters())
				}
				class completeIpRotation(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withCompleteIPRotationRequest(body: Schema.CompleteIPRotationRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object completeIpRotation {
					def apply(projectId: String, zone: String, clusterId: String)(using auth: AuthToken, ec: ExecutionContext): completeIpRotation = new completeIpRotation(ws.url(BASE_URL + s"v1/projects/${projectId}/zones/${zone}/clusters/${clusterId}:completeIpRotation").addQueryStringParameters())
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
					def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Operation])
				}
				object delete {
					def apply(projectId: String, zone: String, clusterId: String, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectId}/zones/${zone}/clusters/${clusterId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Operation]] = (fun: delete) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Cluster]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Cluster])
				}
				object get {
					def apply(projectId: String, zone: String, clusterId: String, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectId}/zones/${zone}/clusters/${clusterId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.Cluster]] = (fun: get) => fun.apply()
				}
				class update(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withUpdateClusterRequest(body: Schema.UpdateClusterRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.Operation])
				}
				object update {
					def apply(projectId: String, zone: String, clusterId: String)(using auth: AuthToken, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"v1/projects/${projectId}/zones/${zone}/clusters/${clusterId}").addQueryStringParameters())
				}
				class addons(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withSetAddonsConfigRequest(body: Schema.SetAddonsConfigRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object addons {
					def apply(projectId: String, zone: String, clusterId: String)(using auth: AuthToken, ec: ExecutionContext): addons = new addons(ws.url(BASE_URL + s"v1/projects/${projectId}/zones/${zone}/clusters/${clusterId}/addons").addQueryStringParameters())
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListClustersResponse]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListClustersResponse])
				}
				object list {
					def apply(projectId: String, zone: String, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectId}/zones/${zone}/clusters").addQueryStringParameters("parent" -> parent.toString))
					given Conversion[list, Future[Schema.ListClustersResponse]] = (fun: list) => fun.apply()
				}
				class locations(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withSetLocationsRequest(body: Schema.SetLocationsRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object locations {
					def apply(projectId: String, zone: String, clusterId: String)(using auth: AuthToken, ec: ExecutionContext): locations = new locations(ws.url(BASE_URL + s"v1/projects/${projectId}/zones/${zone}/clusters/${clusterId}/locations").addQueryStringParameters())
				}
				class setNetworkPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withSetNetworkPolicyRequest(body: Schema.SetNetworkPolicyRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object setNetworkPolicy {
					def apply(projectId: String, zone: String, clusterId: String)(using auth: AuthToken, ec: ExecutionContext): setNetworkPolicy = new setNetworkPolicy(ws.url(BASE_URL + s"v1/projects/${projectId}/zones/${zone}/clusters/${clusterId}:setNetworkPolicy").addQueryStringParameters())
				}
				class setMasterAuth(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withSetMasterAuthRequest(body: Schema.SetMasterAuthRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object setMasterAuth {
					def apply(projectId: String, zone: String, clusterId: String)(using auth: AuthToken, ec: ExecutionContext): setMasterAuth = new setMasterAuth(ws.url(BASE_URL + s"v1/projects/${projectId}/zones/${zone}/clusters/${clusterId}:setMasterAuth").addQueryStringParameters())
				}
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withCreateClusterRequest(body: Schema.CreateClusterRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object create {
					def apply(projectId: String, zone: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectId}/zones/${zone}/clusters").addQueryStringParameters())
				}
				class startIpRotation(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withStartIPRotationRequest(body: Schema.StartIPRotationRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object startIpRotation {
					def apply(projectId: String, zone: String, clusterId: String)(using auth: AuthToken, ec: ExecutionContext): startIpRotation = new startIpRotation(ws.url(BASE_URL + s"v1/projects/${projectId}/zones/${zone}/clusters/${clusterId}:startIpRotation").addQueryStringParameters())
				}
				class legacyAbac(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withSetLegacyAbacRequest(body: Schema.SetLegacyAbacRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object legacyAbac {
					def apply(projectId: String, zone: String, clusterId: String)(using auth: AuthToken, ec: ExecutionContext): legacyAbac = new legacyAbac(ws.url(BASE_URL + s"v1/projects/${projectId}/zones/${zone}/clusters/${clusterId}/legacyAbac").addQueryStringParameters())
				}
				class setMaintenancePolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withSetMaintenancePolicyRequest(body: Schema.SetMaintenancePolicyRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object setMaintenancePolicy {
					def apply(projectId: String, zone: String, clusterId: String)(using auth: AuthToken, ec: ExecutionContext): setMaintenancePolicy = new setMaintenancePolicy(ws.url(BASE_URL + s"v1/projects/${projectId}/zones/${zone}/clusters/${clusterId}:setMaintenancePolicy").addQueryStringParameters())
				}
				class resourceLabels(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withSetLabelsRequest(body: Schema.SetLabelsRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object resourceLabels {
					def apply(projectId: String, zone: String, clusterId: String)(using auth: AuthToken, ec: ExecutionContext): resourceLabels = new resourceLabels(ws.url(BASE_URL + s"v1/projects/${projectId}/zones/${zone}/clusters/${clusterId}/resourceLabels").addQueryStringParameters())
				}
				object nodePools {
					class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withCreateNodePoolRequest(body: Schema.CreateNodePoolRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
					}
					object create {
						def apply(projectId: String, zone: String, clusterId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectId}/zones/${zone}/clusters/${clusterId}/nodePools").addQueryStringParameters())
					}
					class autoscaling(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withSetNodePoolAutoscalingRequest(body: Schema.SetNodePoolAutoscalingRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
					}
					object autoscaling {
						def apply(projectId: String, zone: String, clusterId: String, nodePoolId: String)(using auth: AuthToken, ec: ExecutionContext): autoscaling = new autoscaling(ws.url(BASE_URL + s"v1/projects/${projectId}/zones/${zone}/clusters/${clusterId}/nodePools/${nodePoolId}/autoscaling").addQueryStringParameters())
					}
					class rollback(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withRollbackNodePoolUpgradeRequest(body: Schema.RollbackNodePoolUpgradeRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
					}
					object rollback {
						def apply(projectId: String, zone: String, clusterId: String, nodePoolId: String)(using auth: AuthToken, ec: ExecutionContext): rollback = new rollback(ws.url(BASE_URL + s"v1/projects/${projectId}/zones/${zone}/clusters/${clusterId}/nodePools/${nodePoolId}:rollback").addQueryStringParameters())
					}
					class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
						def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Operation])
					}
					object delete {
						def apply(projectId: String, zone: String, clusterId: String, nodePoolId: String, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectId}/zones/${zone}/clusters/${clusterId}/nodePools/${nodePoolId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[delete, Future[Schema.Operation]] = (fun: delete) => fun.apply()
					}
					class setManagement(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withSetNodePoolManagementRequest(body: Schema.SetNodePoolManagementRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
					}
					object setManagement {
						def apply(projectId: String, zone: String, clusterId: String, nodePoolId: String)(using auth: AuthToken, ec: ExecutionContext): setManagement = new setManagement(ws.url(BASE_URL + s"v1/projects/${projectId}/zones/${zone}/clusters/${clusterId}/nodePools/${nodePoolId}/setManagement").addQueryStringParameters())
					}
					class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.NodePool]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.NodePool])
					}
					object get {
						def apply(projectId: String, zone: String, clusterId: String, nodePoolId: String, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectId}/zones/${zone}/clusters/${clusterId}/nodePools/${nodePoolId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.NodePool]] = (fun: get) => fun.apply()
					}
					class update(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withUpdateNodePoolRequest(body: Schema.UpdateNodePoolRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
					}
					object update {
						def apply(projectId: String, zone: String, clusterId: String, nodePoolId: String)(using auth: AuthToken, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"v1/projects/${projectId}/zones/${zone}/clusters/${clusterId}/nodePools/${nodePoolId}/update").addQueryStringParameters())
					}
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListNodePoolsResponse]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListNodePoolsResponse])
					}
					object list {
						def apply(projectId: String, zone: String, clusterId: String, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectId}/zones/${zone}/clusters/${clusterId}/nodePools").addQueryStringParameters("parent" -> parent.toString))
						given Conversion[list, Future[Schema.ListNodePoolsResponse]] = (fun: list) => fun.apply()
					}
					class setSize(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withSetNodePoolSizeRequest(body: Schema.SetNodePoolSizeRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
					}
					object setSize {
						def apply(projectId: String, zone: String, clusterId: String, nodePoolId: String)(using auth: AuthToken, ec: ExecutionContext): setSize = new setSize(ws.url(BASE_URL + s"v1/projects/${projectId}/zones/${zone}/clusters/${clusterId}/nodePools/${nodePoolId}/setSize").addQueryStringParameters())
					}
				}
			}
			object operations {
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListOperationsResponse]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListOperationsResponse])
				}
				object list {
					def apply(projectId: String, zone: String, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectId}/zones/${zone}/operations").addQueryStringParameters("parent" -> parent.toString))
					given Conversion[list, Future[Schema.ListOperationsResponse]] = (fun: list) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Operation])
				}
				object get {
					def apply(projectId: String, zone: String, operationId: String, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectId}/zones/${zone}/operations/${operationId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.Operation]] = (fun: get) => fun.apply()
				}
				class cancel(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withCancelOperationRequest(body: Schema.CancelOperationRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Empty])
				}
				object cancel {
					def apply(projectId: String, zone: String, operationId: String)(using auth: AuthToken, ec: ExecutionContext): cancel = new cancel(ws.url(BASE_URL + s"v1/projects/${projectId}/zones/${zone}/operations/${operationId}:cancel").addQueryStringParameters())
				}
			}
		}
		object aggregated {
			object usableSubnetworks {
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListUsableSubnetworksResponse]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListUsableSubnetworksResponse])
				}
				object list {
					def apply(projectsId :PlayApi, parent: String, filter: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/aggregated/usableSubnetworks").addQueryStringParameters("parent" -> parent.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListUsableSubnetworksResponse]] = (fun: list) => fun.apply()
				}
			}
		}
	}
}
