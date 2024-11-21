package com.boresjo.play.api.google.prod_tt_sasportal

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://prod-tt-sasportal.googleapis.com/"

	object nodes {
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.SasPortalNode]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.SasPortalNode])
		}
		object get {
			def apply(nodesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1alpha1/nodes/${nodesId}")).addQueryStringParameters("name" -> name.toString))
			given Conversion[get, Future[Schema.SasPortalNode]] = (fun: get) => fun.apply()
		}
		object nodes {
			class move(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withSasPortalMoveNodeRequest(body: Schema.SasPortalMoveNodeRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.SasPortalOperation])
			}
			object move {
				def apply(nodesId :PlayApi, nodesId1 :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): move = new move(auth(ws.url(BASE_URL + s"v1alpha1/nodes/${nodesId}/nodes/${nodesId1}:move")).addQueryStringParameters("name" -> name.toString))
			}
			class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withSasPortalNode(body: Schema.SasPortalNode) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.SasPortalNode])
			}
			object create {
				def apply(nodesId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1alpha1/nodes/${nodesId}/nodes")).addQueryStringParameters("parent" -> parent.toString))
			}
			class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.SasPortalEmpty]) {
				def apply() = req.execute("DELETE").map(_.json.as[Schema.SasPortalEmpty])
			}
			object delete {
				def apply(nodesId :PlayApi, nodesId1 :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1alpha1/nodes/${nodesId}/nodes/${nodesId1}")).addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.SasPortalEmpty]] = (fun: delete) => fun.apply()
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.SasPortalNode]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.SasPortalNode])
			}
			object get {
				def apply(nodesId :PlayApi, nodesId1 :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1alpha1/nodes/${nodesId}/nodes/${nodesId1}")).addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.SasPortalNode]] = (fun: get) => fun.apply()
			}
			class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withSasPortalNode(body: Schema.SasPortalNode) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.SasPortalNode])
			}
			object patch {
				def apply(nodesId :PlayApi, nodesId1 :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"v1alpha1/nodes/${nodesId}/nodes/${nodesId1}")).addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.SasPortalListNodesResponse]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.SasPortalListNodesResponse])
			}
			object list {
				def apply(nodesId :PlayApi, pageToken: String, filter: String, parent: String, pageSize: Int)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1alpha1/nodes/${nodesId}/nodes")).addQueryStringParameters("pageToken" -> pageToken.toString, "filter" -> filter.toString, "parent" -> parent.toString, "pageSize" -> pageSize.toString))
				given Conversion[list, Future[Schema.SasPortalListNodesResponse]] = (fun: list) => fun.apply()
			}
			object nodes {
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withSasPortalNode(body: Schema.SasPortalNode) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.SasPortalNode])
				}
				object create {
					def apply(nodesId :PlayApi, nodesId1 :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1alpha1/nodes/${nodesId}/nodes/${nodesId1}/nodes")).addQueryStringParameters("parent" -> parent.toString))
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.SasPortalListNodesResponse]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.SasPortalListNodesResponse])
				}
				object list {
					def apply(nodesId :PlayApi, nodesId1 :PlayApi, pageSize: Int, parent: String, filter: String, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1alpha1/nodes/${nodesId}/nodes/${nodesId1}/nodes")).addQueryStringParameters("pageSize" -> pageSize.toString, "parent" -> parent.toString, "filter" -> filter.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.SasPortalListNodesResponse]] = (fun: list) => fun.apply()
				}
			}
			object deployments {
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withSasPortalDeployment(body: Schema.SasPortalDeployment) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.SasPortalDeployment])
				}
				object create {
					def apply(nodesId :PlayApi, nodesId1 :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1alpha1/nodes/${nodesId}/nodes/${nodesId1}/deployments")).addQueryStringParameters("parent" -> parent.toString))
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.SasPortalListDeploymentsResponse]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.SasPortalListDeploymentsResponse])
				}
				object list {
					def apply(nodesId :PlayApi, nodesId1 :PlayApi, pageSize: Int, pageToken: String, parent: String, filter: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1alpha1/nodes/${nodesId}/nodes/${nodesId1}/deployments")).addQueryStringParameters("pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "parent" -> parent.toString, "filter" -> filter.toString))
					given Conversion[list, Future[Schema.SasPortalListDeploymentsResponse]] = (fun: list) => fun.apply()
				}
			}
			object devices {
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.SasPortalListDevicesResponse]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.SasPortalListDevicesResponse])
				}
				object list {
					def apply(nodesId :PlayApi, nodesId1 :PlayApi, pageSize: Int, filter: String, pageToken: String, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1alpha1/nodes/${nodesId}/nodes/${nodesId1}/devices")).addQueryStringParameters("pageSize" -> pageSize.toString, "filter" -> filter.toString, "pageToken" -> pageToken.toString, "parent" -> parent.toString))
					given Conversion[list, Future[Schema.SasPortalListDevicesResponse]] = (fun: list) => fun.apply()
				}
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withSasPortalDevice(body: Schema.SasPortalDevice) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.SasPortalDevice])
				}
				object create {
					def apply(nodesId :PlayApi, nodesId1 :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1alpha1/nodes/${nodesId}/nodes/${nodesId1}/devices")).addQueryStringParameters("parent" -> parent.toString))
				}
				class createSigned(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withSasPortalCreateSignedDeviceRequest(body: Schema.SasPortalCreateSignedDeviceRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.SasPortalDevice])
				}
				object createSigned {
					def apply(nodesId :PlayApi, nodesId1 :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): createSigned = new createSigned(auth(ws.url(BASE_URL + s"v1alpha1/nodes/${nodesId}/nodes/${nodesId1}/devices:createSigned")).addQueryStringParameters("parent" -> parent.toString))
				}
			}
		}
		object devices {
			class updateSigned(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withSasPortalUpdateSignedDeviceRequest(body: Schema.SasPortalUpdateSignedDeviceRequest) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.SasPortalDevice])
			}
			object updateSigned {
				def apply(nodesId :PlayApi, devicesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): updateSigned = new updateSigned(auth(ws.url(BASE_URL + s"v1alpha1/nodes/${nodesId}/devices/${devicesId}:updateSigned")).addQueryStringParameters("name" -> name.toString))
			}
			class move(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withSasPortalMoveDeviceRequest(body: Schema.SasPortalMoveDeviceRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.SasPortalOperation])
			}
			object move {
				def apply(nodesId :PlayApi, devicesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): move = new move(auth(ws.url(BASE_URL + s"v1alpha1/nodes/${nodesId}/devices/${devicesId}:move")).addQueryStringParameters("name" -> name.toString))
			}
			class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withSasPortalDevice(body: Schema.SasPortalDevice) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.SasPortalDevice])
			}
			object create {
				def apply(nodesId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1alpha1/nodes/${nodesId}/devices")).addQueryStringParameters("parent" -> parent.toString))
			}
			class signDevice(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withSasPortalSignDeviceRequest(body: Schema.SasPortalSignDeviceRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.SasPortalEmpty])
			}
			object signDevice {
				def apply(nodesId :PlayApi, devicesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): signDevice = new signDevice(auth(ws.url(BASE_URL + s"v1alpha1/nodes/${nodesId}/devices/${devicesId}:signDevice")).addQueryStringParameters("name" -> name.toString))
			}
			class createSigned(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withSasPortalCreateSignedDeviceRequest(body: Schema.SasPortalCreateSignedDeviceRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.SasPortalDevice])
			}
			object createSigned {
				def apply(nodesId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): createSigned = new createSigned(auth(ws.url(BASE_URL + s"v1alpha1/nodes/${nodesId}/devices:createSigned")).addQueryStringParameters("parent" -> parent.toString))
			}
			class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.SasPortalEmpty]) {
				def apply() = req.execute("DELETE").map(_.json.as[Schema.SasPortalEmpty])
			}
			object delete {
				def apply(nodesId :PlayApi, devicesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1alpha1/nodes/${nodesId}/devices/${devicesId}")).addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.SasPortalEmpty]] = (fun: delete) => fun.apply()
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.SasPortalDevice]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.SasPortalDevice])
			}
			object get {
				def apply(nodesId :PlayApi, devicesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1alpha1/nodes/${nodesId}/devices/${devicesId}")).addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.SasPortalDevice]] = (fun: get) => fun.apply()
			}
			class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withSasPortalDevice(body: Schema.SasPortalDevice) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.SasPortalDevice])
			}
			object patch {
				def apply(nodesId :PlayApi, devicesId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"v1alpha1/nodes/${nodesId}/devices/${devicesId}")).addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.SasPortalListDevicesResponse]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.SasPortalListDevicesResponse])
			}
			object list {
				def apply(nodesId :PlayApi, pageToken: String, filter: String, pageSize: Int, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1alpha1/nodes/${nodesId}/devices")).addQueryStringParameters("pageToken" -> pageToken.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "parent" -> parent.toString))
				given Conversion[list, Future[Schema.SasPortalListDevicesResponse]] = (fun: list) => fun.apply()
			}
		}
		object deployments {
			class move(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withSasPortalMoveDeploymentRequest(body: Schema.SasPortalMoveDeploymentRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.SasPortalOperation])
			}
			object move {
				def apply(nodesId :PlayApi, deploymentsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): move = new move(auth(ws.url(BASE_URL + s"v1alpha1/nodes/${nodesId}/deployments/${deploymentsId}:move")).addQueryStringParameters("name" -> name.toString))
			}
			class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.SasPortalEmpty]) {
				def apply() = req.execute("DELETE").map(_.json.as[Schema.SasPortalEmpty])
			}
			object delete {
				def apply(nodesId :PlayApi, deploymentsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1alpha1/nodes/${nodesId}/deployments/${deploymentsId}")).addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.SasPortalEmpty]] = (fun: delete) => fun.apply()
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.SasPortalDeployment]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.SasPortalDeployment])
			}
			object get {
				def apply(nodesId :PlayApi, deploymentsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1alpha1/nodes/${nodesId}/deployments/${deploymentsId}")).addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.SasPortalDeployment]] = (fun: get) => fun.apply()
			}
			class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withSasPortalDeployment(body: Schema.SasPortalDeployment) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.SasPortalDeployment])
			}
			object patch {
				def apply(nodesId :PlayApi, deploymentsId :PlayApi, updateMask: String, name: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"v1alpha1/nodes/${nodesId}/deployments/${deploymentsId}")).addQueryStringParameters("updateMask" -> updateMask.toString, "name" -> name.toString))
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.SasPortalListDeploymentsResponse]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.SasPortalListDeploymentsResponse])
			}
			object list {
				def apply(nodesId :PlayApi, parent: String, pageToken: String, pageSize: Int, filter: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1alpha1/nodes/${nodesId}/deployments")).addQueryStringParameters("parent" -> parent.toString, "pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString, "filter" -> filter.toString))
				given Conversion[list, Future[Schema.SasPortalListDeploymentsResponse]] = (fun: list) => fun.apply()
			}
			object devices {
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.SasPortalListDevicesResponse]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.SasPortalListDevicesResponse])
				}
				object list {
					def apply(nodesId :PlayApi, deploymentsId :PlayApi, filter: String, parent: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1alpha1/nodes/${nodesId}/deployments/${deploymentsId}/devices")).addQueryStringParameters("filter" -> filter.toString, "parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.SasPortalListDevicesResponse]] = (fun: list) => fun.apply()
				}
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withSasPortalDevice(body: Schema.SasPortalDevice) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.SasPortalDevice])
				}
				object create {
					def apply(nodesId :PlayApi, deploymentsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1alpha1/nodes/${nodesId}/deployments/${deploymentsId}/devices")).addQueryStringParameters("parent" -> parent.toString))
				}
				class createSigned(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withSasPortalCreateSignedDeviceRequest(body: Schema.SasPortalCreateSignedDeviceRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.SasPortalDevice])
				}
				object createSigned {
					def apply(nodesId :PlayApi, deploymentsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): createSigned = new createSigned(auth(ws.url(BASE_URL + s"v1alpha1/nodes/${nodesId}/deployments/${deploymentsId}/devices:createSigned")).addQueryStringParameters("parent" -> parent.toString))
				}
			}
		}
	}
	object customers {
		class listGcpProjectDeployments(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.SasPortalListGcpProjectDeploymentsResponse]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.SasPortalListGcpProjectDeploymentsResponse])
		}
		object listGcpProjectDeployments {
			def apply()(using auth: AuthToken, ec: ExecutionContext): listGcpProjectDeployments = new listGcpProjectDeployments(auth(ws.url(BASE_URL + s"v1alpha1/customers:listGcpProjectDeployments")).addQueryStringParameters())
			given Conversion[listGcpProjectDeployments, Future[Schema.SasPortalListGcpProjectDeploymentsResponse]] = (fun: listGcpProjectDeployments) => fun.apply()
		}
		class listLegacyOrganizations(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.SasPortalListLegacyOrganizationsResponse]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.SasPortalListLegacyOrganizationsResponse])
		}
		object listLegacyOrganizations {
			def apply()(using auth: AuthToken, ec: ExecutionContext): listLegacyOrganizations = new listLegacyOrganizations(auth(ws.url(BASE_URL + s"v1alpha1/customers:listLegacyOrganizations")).addQueryStringParameters())
			given Conversion[listLegacyOrganizations, Future[Schema.SasPortalListLegacyOrganizationsResponse]] = (fun: listLegacyOrganizations) => fun.apply()
		}
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.SasPortalCustomer]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.SasPortalCustomer])
		}
		object get {
			def apply(customersId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1alpha1/customers/${customersId}")).addQueryStringParameters("name" -> name.toString))
			given Conversion[get, Future[Schema.SasPortalCustomer]] = (fun: get) => fun.apply()
		}
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.SasPortalListCustomersResponse]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.SasPortalListCustomersResponse])
		}
		object list {
			def apply(pageToken: String, pageSize: Int)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1alpha1/customers")).addQueryStringParameters("pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString))
			given Conversion[list, Future[Schema.SasPortalListCustomersResponse]] = (fun: list) => fun.apply()
		}
		class migrateOrganization(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withSasPortalMigrateOrganizationRequest(body: Schema.SasPortalMigrateOrganizationRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.SasPortalOperation])
		}
		object migrateOrganization {
			def apply()(using auth: AuthToken, ec: ExecutionContext): migrateOrganization = new migrateOrganization(auth(ws.url(BASE_URL + s"v1alpha1/customers:migrateOrganization")).addQueryStringParameters())
		}
		class setupSasAnalytics(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withSasPortalSetupSasAnalyticsRequest(body: Schema.SasPortalSetupSasAnalyticsRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.SasPortalOperation])
		}
		object setupSasAnalytics {
			def apply()(using auth: AuthToken, ec: ExecutionContext): setupSasAnalytics = new setupSasAnalytics(auth(ws.url(BASE_URL + s"v1alpha1/customers:setupSasAnalytics")).addQueryStringParameters())
		}
		class provisionDeployment(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withSasPortalProvisionDeploymentRequest(body: Schema.SasPortalProvisionDeploymentRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.SasPortalProvisionDeploymentResponse])
		}
		object provisionDeployment {
			def apply()(using auth: AuthToken, ec: ExecutionContext): provisionDeployment = new provisionDeployment(auth(ws.url(BASE_URL + s"v1alpha1/customers:provisionDeployment")).addQueryStringParameters())
		}
		class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withSasPortalCustomer(body: Schema.SasPortalCustomer) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.SasPortalCustomer])
		}
		object patch {
			def apply(customersId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"v1alpha1/customers/${customersId}")).addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
		}
		object nodes {
			class move(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withSasPortalMoveNodeRequest(body: Schema.SasPortalMoveNodeRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.SasPortalOperation])
			}
			object move {
				def apply(customersId :PlayApi, nodesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): move = new move(auth(ws.url(BASE_URL + s"v1alpha1/customers/${customersId}/nodes/${nodesId}:move")).addQueryStringParameters("name" -> name.toString))
			}
			class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withSasPortalNode(body: Schema.SasPortalNode) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.SasPortalNode])
			}
			object create {
				def apply(customersId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1alpha1/customers/${customersId}/nodes")).addQueryStringParameters("parent" -> parent.toString))
			}
			class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.SasPortalEmpty]) {
				def apply() = req.execute("DELETE").map(_.json.as[Schema.SasPortalEmpty])
			}
			object delete {
				def apply(customersId :PlayApi, nodesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1alpha1/customers/${customersId}/nodes/${nodesId}")).addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.SasPortalEmpty]] = (fun: delete) => fun.apply()
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.SasPortalNode]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.SasPortalNode])
			}
			object get {
				def apply(customersId :PlayApi, nodesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1alpha1/customers/${customersId}/nodes/${nodesId}")).addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.SasPortalNode]] = (fun: get) => fun.apply()
			}
			class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withSasPortalNode(body: Schema.SasPortalNode) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.SasPortalNode])
			}
			object patch {
				def apply(customersId :PlayApi, nodesId :PlayApi, updateMask: String, name: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"v1alpha1/customers/${customersId}/nodes/${nodesId}")).addQueryStringParameters("updateMask" -> updateMask.toString, "name" -> name.toString))
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.SasPortalListNodesResponse]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.SasPortalListNodesResponse])
			}
			object list {
				def apply(customersId :PlayApi, filter: String, parent: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1alpha1/customers/${customersId}/nodes")).addQueryStringParameters("filter" -> filter.toString, "parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.SasPortalListNodesResponse]] = (fun: list) => fun.apply()
			}
			object nodes {
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withSasPortalNode(body: Schema.SasPortalNode) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.SasPortalNode])
				}
				object create {
					def apply(customersId :PlayApi, nodesId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1alpha1/customers/${customersId}/nodes/${nodesId}/nodes")).addQueryStringParameters("parent" -> parent.toString))
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.SasPortalListNodesResponse]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.SasPortalListNodesResponse])
				}
				object list {
					def apply(customersId :PlayApi, nodesId :PlayApi, filter: String, pageToken: String, parent: String, pageSize: Int)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1alpha1/customers/${customersId}/nodes/${nodesId}/nodes")).addQueryStringParameters("filter" -> filter.toString, "pageToken" -> pageToken.toString, "parent" -> parent.toString, "pageSize" -> pageSize.toString))
					given Conversion[list, Future[Schema.SasPortalListNodesResponse]] = (fun: list) => fun.apply()
				}
			}
			object deployments {
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.SasPortalListDeploymentsResponse]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.SasPortalListDeploymentsResponse])
				}
				object list {
					def apply(customersId :PlayApi, nodesId :PlayApi, pageToken: String, pageSize: Int, parent: String, filter: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1alpha1/customers/${customersId}/nodes/${nodesId}/deployments")).addQueryStringParameters("pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString, "parent" -> parent.toString, "filter" -> filter.toString))
					given Conversion[list, Future[Schema.SasPortalListDeploymentsResponse]] = (fun: list) => fun.apply()
				}
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withSasPortalDeployment(body: Schema.SasPortalDeployment) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.SasPortalDeployment])
				}
				object create {
					def apply(customersId :PlayApi, nodesId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1alpha1/customers/${customersId}/nodes/${nodesId}/deployments")).addQueryStringParameters("parent" -> parent.toString))
				}
			}
			object devices {
				class createSigned(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withSasPortalCreateSignedDeviceRequest(body: Schema.SasPortalCreateSignedDeviceRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.SasPortalDevice])
				}
				object createSigned {
					def apply(customersId :PlayApi, nodesId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): createSigned = new createSigned(auth(ws.url(BASE_URL + s"v1alpha1/customers/${customersId}/nodes/${nodesId}/devices:createSigned")).addQueryStringParameters("parent" -> parent.toString))
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.SasPortalListDevicesResponse]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.SasPortalListDevicesResponse])
				}
				object list {
					def apply(customersId :PlayApi, nodesId :PlayApi, pageToken: String, pageSize: Int, parent: String, filter: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1alpha1/customers/${customersId}/nodes/${nodesId}/devices")).addQueryStringParameters("pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString, "parent" -> parent.toString, "filter" -> filter.toString))
					given Conversion[list, Future[Schema.SasPortalListDevicesResponse]] = (fun: list) => fun.apply()
				}
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withSasPortalDevice(body: Schema.SasPortalDevice) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.SasPortalDevice])
				}
				object create {
					def apply(customersId :PlayApi, nodesId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1alpha1/customers/${customersId}/nodes/${nodesId}/devices")).addQueryStringParameters("parent" -> parent.toString))
				}
			}
		}
		object deployments {
			class move(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withSasPortalMoveDeploymentRequest(body: Schema.SasPortalMoveDeploymentRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.SasPortalOperation])
			}
			object move {
				def apply(customersId :PlayApi, deploymentsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): move = new move(auth(ws.url(BASE_URL + s"v1alpha1/customers/${customersId}/deployments/${deploymentsId}:move")).addQueryStringParameters("name" -> name.toString))
			}
			class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withSasPortalDeployment(body: Schema.SasPortalDeployment) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.SasPortalDeployment])
			}
			object create {
				def apply(customersId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1alpha1/customers/${customersId}/deployments")).addQueryStringParameters("parent" -> parent.toString))
			}
			class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.SasPortalEmpty]) {
				def apply() = req.execute("DELETE").map(_.json.as[Schema.SasPortalEmpty])
			}
			object delete {
				def apply(customersId :PlayApi, deploymentsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1alpha1/customers/${customersId}/deployments/${deploymentsId}")).addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.SasPortalEmpty]] = (fun: delete) => fun.apply()
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.SasPortalDeployment]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.SasPortalDeployment])
			}
			object get {
				def apply(customersId :PlayApi, deploymentsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1alpha1/customers/${customersId}/deployments/${deploymentsId}")).addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.SasPortalDeployment]] = (fun: get) => fun.apply()
			}
			class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withSasPortalDeployment(body: Schema.SasPortalDeployment) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.SasPortalDeployment])
			}
			object patch {
				def apply(customersId :PlayApi, deploymentsId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"v1alpha1/customers/${customersId}/deployments/${deploymentsId}")).addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.SasPortalListDeploymentsResponse]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.SasPortalListDeploymentsResponse])
			}
			object list {
				def apply(customersId :PlayApi, pageToken: String, parent: String, filter: String, pageSize: Int)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1alpha1/customers/${customersId}/deployments")).addQueryStringParameters("pageToken" -> pageToken.toString, "parent" -> parent.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString))
				given Conversion[list, Future[Schema.SasPortalListDeploymentsResponse]] = (fun: list) => fun.apply()
			}
			object devices {
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.SasPortalListDevicesResponse]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.SasPortalListDevicesResponse])
				}
				object list {
					def apply(customersId :PlayApi, deploymentsId :PlayApi, pageToken: String, pageSize: Int, filter: String, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1alpha1/customers/${customersId}/deployments/${deploymentsId}/devices")).addQueryStringParameters("pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString, "filter" -> filter.toString, "parent" -> parent.toString))
					given Conversion[list, Future[Schema.SasPortalListDevicesResponse]] = (fun: list) => fun.apply()
				}
				class createSigned(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withSasPortalCreateSignedDeviceRequest(body: Schema.SasPortalCreateSignedDeviceRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.SasPortalDevice])
				}
				object createSigned {
					def apply(customersId :PlayApi, deploymentsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): createSigned = new createSigned(auth(ws.url(BASE_URL + s"v1alpha1/customers/${customersId}/deployments/${deploymentsId}/devices:createSigned")).addQueryStringParameters("parent" -> parent.toString))
				}
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withSasPortalDevice(body: Schema.SasPortalDevice) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.SasPortalDevice])
				}
				object create {
					def apply(customersId :PlayApi, deploymentsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1alpha1/customers/${customersId}/deployments/${deploymentsId}/devices")).addQueryStringParameters("parent" -> parent.toString))
				}
			}
		}
		object devices {
			class updateSigned(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withSasPortalUpdateSignedDeviceRequest(body: Schema.SasPortalUpdateSignedDeviceRequest) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.SasPortalDevice])
			}
			object updateSigned {
				def apply(customersId :PlayApi, devicesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): updateSigned = new updateSigned(auth(ws.url(BASE_URL + s"v1alpha1/customers/${customersId}/devices/${devicesId}:updateSigned")).addQueryStringParameters("name" -> name.toString))
			}
			class move(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withSasPortalMoveDeviceRequest(body: Schema.SasPortalMoveDeviceRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.SasPortalOperation])
			}
			object move {
				def apply(customersId :PlayApi, devicesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): move = new move(auth(ws.url(BASE_URL + s"v1alpha1/customers/${customersId}/devices/${devicesId}:move")).addQueryStringParameters("name" -> name.toString))
			}
			class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withSasPortalDevice(body: Schema.SasPortalDevice) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.SasPortalDevice])
			}
			object create {
				def apply(customersId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1alpha1/customers/${customersId}/devices")).addQueryStringParameters("parent" -> parent.toString))
			}
			class signDevice(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withSasPortalSignDeviceRequest(body: Schema.SasPortalSignDeviceRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.SasPortalEmpty])
			}
			object signDevice {
				def apply(customersId :PlayApi, devicesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): signDevice = new signDevice(auth(ws.url(BASE_URL + s"v1alpha1/customers/${customersId}/devices/${devicesId}:signDevice")).addQueryStringParameters("name" -> name.toString))
			}
			class createSigned(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withSasPortalCreateSignedDeviceRequest(body: Schema.SasPortalCreateSignedDeviceRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.SasPortalDevice])
			}
			object createSigned {
				def apply(customersId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): createSigned = new createSigned(auth(ws.url(BASE_URL + s"v1alpha1/customers/${customersId}/devices:createSigned")).addQueryStringParameters("parent" -> parent.toString))
			}
			class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.SasPortalEmpty]) {
				def apply() = req.execute("DELETE").map(_.json.as[Schema.SasPortalEmpty])
			}
			object delete {
				def apply(customersId :PlayApi, devicesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1alpha1/customers/${customersId}/devices/${devicesId}")).addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.SasPortalEmpty]] = (fun: delete) => fun.apply()
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.SasPortalDevice]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.SasPortalDevice])
			}
			object get {
				def apply(customersId :PlayApi, devicesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1alpha1/customers/${customersId}/devices/${devicesId}")).addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.SasPortalDevice]] = (fun: get) => fun.apply()
			}
			class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withSasPortalDevice(body: Schema.SasPortalDevice) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.SasPortalDevice])
			}
			object patch {
				def apply(customersId :PlayApi, devicesId :PlayApi, updateMask: String, name: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"v1alpha1/customers/${customersId}/devices/${devicesId}")).addQueryStringParameters("updateMask" -> updateMask.toString, "name" -> name.toString))
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.SasPortalListDevicesResponse]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.SasPortalListDevicesResponse])
			}
			object list {
				def apply(customersId :PlayApi, pageToken: String, pageSize: Int, parent: String, filter: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1alpha1/customers/${customersId}/devices")).addQueryStringParameters("pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString, "parent" -> parent.toString, "filter" -> filter.toString))
				given Conversion[list, Future[Schema.SasPortalListDevicesResponse]] = (fun: list) => fun.apply()
			}
		}
	}
	object installer {
		class validate(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withSasPortalValidateInstallerRequest(body: Schema.SasPortalValidateInstallerRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.SasPortalValidateInstallerResponse])
		}
		object validate {
			def apply()(using auth: AuthToken, ec: ExecutionContext): validate = new validate(auth(ws.url(BASE_URL + s"v1alpha1/installer:validate")).addQueryStringParameters())
		}
		class generateSecret(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withSasPortalGenerateSecretRequest(body: Schema.SasPortalGenerateSecretRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.SasPortalGenerateSecretResponse])
		}
		object generateSecret {
			def apply()(using auth: AuthToken, ec: ExecutionContext): generateSecret = new generateSecret(auth(ws.url(BASE_URL + s"v1alpha1/installer:generateSecret")).addQueryStringParameters())
		}
	}
	object deployments {
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.SasPortalDeployment]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.SasPortalDeployment])
		}
		object get {
			def apply(deploymentsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1alpha1/deployments/${deploymentsId}")).addQueryStringParameters("name" -> name.toString))
			given Conversion[get, Future[Schema.SasPortalDeployment]] = (fun: get) => fun.apply()
		}
		object devices {
			class updateSigned(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withSasPortalUpdateSignedDeviceRequest(body: Schema.SasPortalUpdateSignedDeviceRequest) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.SasPortalDevice])
			}
			object updateSigned {
				def apply(deploymentsId :PlayApi, devicesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): updateSigned = new updateSigned(auth(ws.url(BASE_URL + s"v1alpha1/deployments/${deploymentsId}/devices/${devicesId}:updateSigned")).addQueryStringParameters("name" -> name.toString))
			}
			class move(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withSasPortalMoveDeviceRequest(body: Schema.SasPortalMoveDeviceRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.SasPortalOperation])
			}
			object move {
				def apply(deploymentsId :PlayApi, devicesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): move = new move(auth(ws.url(BASE_URL + s"v1alpha1/deployments/${deploymentsId}/devices/${devicesId}:move")).addQueryStringParameters("name" -> name.toString))
			}
			class signDevice(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withSasPortalSignDeviceRequest(body: Schema.SasPortalSignDeviceRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.SasPortalEmpty])
			}
			object signDevice {
				def apply(deploymentsId :PlayApi, devicesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): signDevice = new signDevice(auth(ws.url(BASE_URL + s"v1alpha1/deployments/${deploymentsId}/devices/${devicesId}:signDevice")).addQueryStringParameters("name" -> name.toString))
			}
			class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.SasPortalEmpty]) {
				def apply() = req.execute("DELETE").map(_.json.as[Schema.SasPortalEmpty])
			}
			object delete {
				def apply(deploymentsId :PlayApi, devicesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1alpha1/deployments/${deploymentsId}/devices/${devicesId}")).addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.SasPortalEmpty]] = (fun: delete) => fun.apply()
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.SasPortalDevice]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.SasPortalDevice])
			}
			object get {
				def apply(deploymentsId :PlayApi, devicesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1alpha1/deployments/${deploymentsId}/devices/${devicesId}")).addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.SasPortalDevice]] = (fun: get) => fun.apply()
			}
			class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withSasPortalDevice(body: Schema.SasPortalDevice) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.SasPortalDevice])
			}
			object patch {
				def apply(deploymentsId :PlayApi, devicesId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"v1alpha1/deployments/${deploymentsId}/devices/${devicesId}")).addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
			}
		}
	}
	object policies {
		class set(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withSasPortalSetPolicyRequest(body: Schema.SasPortalSetPolicyRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.SasPortalPolicy])
		}
		object set {
			def apply()(using auth: AuthToken, ec: ExecutionContext): set = new set(auth(ws.url(BASE_URL + s"v1alpha1/policies:set")).addQueryStringParameters())
		}
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withSasPortalGetPolicyRequest(body: Schema.SasPortalGetPolicyRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.SasPortalPolicy])
		}
		object get {
			def apply()(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1alpha1/policies:get")).addQueryStringParameters())
		}
		class test(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withSasPortalTestPermissionsRequest(body: Schema.SasPortalTestPermissionsRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.SasPortalTestPermissionsResponse])
		}
		object test {
			def apply()(using auth: AuthToken, ec: ExecutionContext): test = new test(auth(ws.url(BASE_URL + s"v1alpha1/policies:test")).addQueryStringParameters())
		}
	}
}
