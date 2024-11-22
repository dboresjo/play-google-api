package com.boresjo.play.api.google.prod_tt_sasportal

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
		"""https://www.googleapis.com/auth/sasportal""" /* Read, create, update, and delete your SAS Portal data. */,
		"""https://www.googleapis.com/auth/cloud-platform""" /* See, edit, configure, and delete your Google Cloud data and see the email address for your Google Account. */
	)

	private val BASE_URL = "https://prod-tt-sasportal.googleapis.com/"

	object nodes {
		/** Returns a requested node. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.SasPortalNode]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/sasportal""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.SasPortalNode])
		}
		object get {
			def apply(nodesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1alpha1/nodes/${nodesId}").addQueryStringParameters("name" -> name.toString))
			given Conversion[get, Future[Schema.SasPortalNode]] = (fun: get) => fun.apply()
		}
		object nodes {
			/** Moves a node under another node or customer. */
			class move(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/sasportal""")
				/** Perform the request */
				def withSasPortalMoveNodeRequest(body: Schema.SasPortalMoveNodeRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.SasPortalOperation])
			}
			object move {
				def apply(nodesId :PlayApi, nodesId1 :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): move = new move(ws.url(BASE_URL + s"v1alpha1/nodes/${nodesId}/nodes/${nodesId1}:move").addQueryStringParameters("name" -> name.toString))
			}
			/** Creates a new node. */
			class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/sasportal""")
				/** Perform the request */
				def withSasPortalNode(body: Schema.SasPortalNode) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.SasPortalNode])
			}
			object create {
				def apply(nodesId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1alpha1/nodes/${nodesId}/nodes").addQueryStringParameters("parent" -> parent.toString))
			}
			/** Deletes a node. */
			class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.SasPortalEmpty]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/sasportal""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.SasPortalEmpty])
			}
			object delete {
				def apply(nodesId :PlayApi, nodesId1 :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1alpha1/nodes/${nodesId}/nodes/${nodesId1}").addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.SasPortalEmpty]] = (fun: delete) => fun.apply()
			}
			/** Returns a requested node. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.SasPortalNode]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/sasportal""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.SasPortalNode])
			}
			object get {
				def apply(nodesId :PlayApi, nodesId1 :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1alpha1/nodes/${nodesId}/nodes/${nodesId1}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.SasPortalNode]] = (fun: get) => fun.apply()
			}
			/** Updates an existing node. */
			class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/sasportal""")
				/** Perform the request */
				def withSasPortalNode(body: Schema.SasPortalNode) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.SasPortalNode])
			}
			object patch {
				def apply(nodesId :PlayApi, nodesId1 :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1alpha1/nodes/${nodesId}/nodes/${nodesId1}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
			}
			/** Lists nodes. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.SasPortalListNodesResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/sasportal""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.SasPortalListNodesResponse])
			}
			object list {
				def apply(nodesId :PlayApi, pageToken: String, filter: String, parent: String, pageSize: Int)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1alpha1/nodes/${nodesId}/nodes").addQueryStringParameters("pageToken" -> pageToken.toString, "filter" -> filter.toString, "parent" -> parent.toString, "pageSize" -> pageSize.toString))
				given Conversion[list, Future[Schema.SasPortalListNodesResponse]] = (fun: list) => fun.apply()
			}
			object nodes {
				/** Creates a new node. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/sasportal""")
					/** Perform the request */
					def withSasPortalNode(body: Schema.SasPortalNode) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.SasPortalNode])
				}
				object create {
					def apply(nodesId :PlayApi, nodesId1 :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1alpha1/nodes/${nodesId}/nodes/${nodesId1}/nodes").addQueryStringParameters("parent" -> parent.toString))
				}
				/** Lists nodes. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.SasPortalListNodesResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/sasportal""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.SasPortalListNodesResponse])
				}
				object list {
					def apply(nodesId :PlayApi, nodesId1 :PlayApi, pageSize: Int, parent: String, filter: String, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1alpha1/nodes/${nodesId}/nodes/${nodesId1}/nodes").addQueryStringParameters("pageSize" -> pageSize.toString, "parent" -> parent.toString, "filter" -> filter.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.SasPortalListNodesResponse]] = (fun: list) => fun.apply()
				}
			}
			object deployments {
				/** Creates a new deployment. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/sasportal""")
					/** Perform the request */
					def withSasPortalDeployment(body: Schema.SasPortalDeployment) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.SasPortalDeployment])
				}
				object create {
					def apply(nodesId :PlayApi, nodesId1 :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1alpha1/nodes/${nodesId}/nodes/${nodesId1}/deployments").addQueryStringParameters("parent" -> parent.toString))
				}
				/** Lists deployments. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.SasPortalListDeploymentsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/sasportal""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.SasPortalListDeploymentsResponse])
				}
				object list {
					def apply(nodesId :PlayApi, nodesId1 :PlayApi, pageSize: Int, pageToken: String, parent: String, filter: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1alpha1/nodes/${nodesId}/nodes/${nodesId1}/deployments").addQueryStringParameters("pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "parent" -> parent.toString, "filter" -> filter.toString))
					given Conversion[list, Future[Schema.SasPortalListDeploymentsResponse]] = (fun: list) => fun.apply()
				}
			}
			object devices {
				/** Lists devices under a node or customer. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.SasPortalListDevicesResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/sasportal""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.SasPortalListDevicesResponse])
				}
				object list {
					def apply(nodesId :PlayApi, nodesId1 :PlayApi, pageSize: Int, filter: String, pageToken: String, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1alpha1/nodes/${nodesId}/nodes/${nodesId1}/devices").addQueryStringParameters("pageSize" -> pageSize.toString, "filter" -> filter.toString, "pageToken" -> pageToken.toString, "parent" -> parent.toString))
					given Conversion[list, Future[Schema.SasPortalListDevicesResponse]] = (fun: list) => fun.apply()
				}
				/** Creates a device under a node or customer. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/sasportal""")
					/** Perform the request */
					def withSasPortalDevice(body: Schema.SasPortalDevice) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.SasPortalDevice])
				}
				object create {
					def apply(nodesId :PlayApi, nodesId1 :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1alpha1/nodes/${nodesId}/nodes/${nodesId1}/devices").addQueryStringParameters("parent" -> parent.toString))
				}
				/** Creates a signed device under a node or customer. */
				class createSigned(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/sasportal""")
					/** Perform the request */
					def withSasPortalCreateSignedDeviceRequest(body: Schema.SasPortalCreateSignedDeviceRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.SasPortalDevice])
				}
				object createSigned {
					def apply(nodesId :PlayApi, nodesId1 :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): createSigned = new createSigned(ws.url(BASE_URL + s"v1alpha1/nodes/${nodesId}/nodes/${nodesId1}/devices:createSigned").addQueryStringParameters("parent" -> parent.toString))
				}
			}
		}
		object devices {
			/** Updates a signed device. */
			class updateSigned(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/sasportal""")
				/** Perform the request */
				def withSasPortalUpdateSignedDeviceRequest(body: Schema.SasPortalUpdateSignedDeviceRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.SasPortalDevice])
			}
			object updateSigned {
				def apply(nodesId :PlayApi, devicesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): updateSigned = new updateSigned(ws.url(BASE_URL + s"v1alpha1/nodes/${nodesId}/devices/${devicesId}:updateSigned").addQueryStringParameters("name" -> name.toString))
			}
			/** Moves a device under another node or customer. */
			class move(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/sasportal""")
				/** Perform the request */
				def withSasPortalMoveDeviceRequest(body: Schema.SasPortalMoveDeviceRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.SasPortalOperation])
			}
			object move {
				def apply(nodesId :PlayApi, devicesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): move = new move(ws.url(BASE_URL + s"v1alpha1/nodes/${nodesId}/devices/${devicesId}:move").addQueryStringParameters("name" -> name.toString))
			}
			/** Creates a device under a node or customer. */
			class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/sasportal""")
				/** Perform the request */
				def withSasPortalDevice(body: Schema.SasPortalDevice) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.SasPortalDevice])
			}
			object create {
				def apply(nodesId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1alpha1/nodes/${nodesId}/devices").addQueryStringParameters("parent" -> parent.toString))
			}
			/** Signs a device. */
			class signDevice(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/sasportal""")
				/** Perform the request */
				def withSasPortalSignDeviceRequest(body: Schema.SasPortalSignDeviceRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.SasPortalEmpty])
			}
			object signDevice {
				def apply(nodesId :PlayApi, devicesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): signDevice = new signDevice(ws.url(BASE_URL + s"v1alpha1/nodes/${nodesId}/devices/${devicesId}:signDevice").addQueryStringParameters("name" -> name.toString))
			}
			/** Creates a signed device under a node or customer. */
			class createSigned(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/sasportal""")
				/** Perform the request */
				def withSasPortalCreateSignedDeviceRequest(body: Schema.SasPortalCreateSignedDeviceRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.SasPortalDevice])
			}
			object createSigned {
				def apply(nodesId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): createSigned = new createSigned(ws.url(BASE_URL + s"v1alpha1/nodes/${nodesId}/devices:createSigned").addQueryStringParameters("parent" -> parent.toString))
			}
			/** Deletes a device. */
			class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.SasPortalEmpty]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/sasportal""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.SasPortalEmpty])
			}
			object delete {
				def apply(nodesId :PlayApi, devicesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1alpha1/nodes/${nodesId}/devices/${devicesId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.SasPortalEmpty]] = (fun: delete) => fun.apply()
			}
			/** Gets details about a device. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.SasPortalDevice]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/sasportal""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.SasPortalDevice])
			}
			object get {
				def apply(nodesId :PlayApi, devicesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1alpha1/nodes/${nodesId}/devices/${devicesId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.SasPortalDevice]] = (fun: get) => fun.apply()
			}
			/** Updates a device. */
			class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/sasportal""")
				/** Perform the request */
				def withSasPortalDevice(body: Schema.SasPortalDevice) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.SasPortalDevice])
			}
			object patch {
				def apply(nodesId :PlayApi, devicesId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1alpha1/nodes/${nodesId}/devices/${devicesId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
			}
			/** Lists devices under a node or customer. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.SasPortalListDevicesResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/sasportal""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.SasPortalListDevicesResponse])
			}
			object list {
				def apply(nodesId :PlayApi, pageToken: String, filter: String, pageSize: Int, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1alpha1/nodes/${nodesId}/devices").addQueryStringParameters("pageToken" -> pageToken.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "parent" -> parent.toString))
				given Conversion[list, Future[Schema.SasPortalListDevicesResponse]] = (fun: list) => fun.apply()
			}
		}
		object deployments {
			/** Moves a deployment under another node or customer. */
			class move(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/sasportal""")
				/** Perform the request */
				def withSasPortalMoveDeploymentRequest(body: Schema.SasPortalMoveDeploymentRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.SasPortalOperation])
			}
			object move {
				def apply(nodesId :PlayApi, deploymentsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): move = new move(ws.url(BASE_URL + s"v1alpha1/nodes/${nodesId}/deployments/${deploymentsId}:move").addQueryStringParameters("name" -> name.toString))
			}
			/** Deletes a deployment. */
			class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.SasPortalEmpty]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/sasportal""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.SasPortalEmpty])
			}
			object delete {
				def apply(nodesId :PlayApi, deploymentsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1alpha1/nodes/${nodesId}/deployments/${deploymentsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.SasPortalEmpty]] = (fun: delete) => fun.apply()
			}
			/** Returns a requested deployment. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.SasPortalDeployment]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/sasportal""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.SasPortalDeployment])
			}
			object get {
				def apply(nodesId :PlayApi, deploymentsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1alpha1/nodes/${nodesId}/deployments/${deploymentsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.SasPortalDeployment]] = (fun: get) => fun.apply()
			}
			/** Updates an existing deployment. */
			class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/sasportal""")
				/** Perform the request */
				def withSasPortalDeployment(body: Schema.SasPortalDeployment) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.SasPortalDeployment])
			}
			object patch {
				def apply(nodesId :PlayApi, deploymentsId :PlayApi, updateMask: String, name: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1alpha1/nodes/${nodesId}/deployments/${deploymentsId}").addQueryStringParameters("updateMask" -> updateMask.toString, "name" -> name.toString))
			}
			/** Lists deployments. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.SasPortalListDeploymentsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/sasportal""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.SasPortalListDeploymentsResponse])
			}
			object list {
				def apply(nodesId :PlayApi, parent: String, pageToken: String, pageSize: Int, filter: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1alpha1/nodes/${nodesId}/deployments").addQueryStringParameters("parent" -> parent.toString, "pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString, "filter" -> filter.toString))
				given Conversion[list, Future[Schema.SasPortalListDeploymentsResponse]] = (fun: list) => fun.apply()
			}
			object devices {
				/** Lists devices under a node or customer. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.SasPortalListDevicesResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/sasportal""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.SasPortalListDevicesResponse])
				}
				object list {
					def apply(nodesId :PlayApi, deploymentsId :PlayApi, filter: String, parent: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1alpha1/nodes/${nodesId}/deployments/${deploymentsId}/devices").addQueryStringParameters("filter" -> filter.toString, "parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.SasPortalListDevicesResponse]] = (fun: list) => fun.apply()
				}
				/** Creates a device under a node or customer. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/sasportal""")
					/** Perform the request */
					def withSasPortalDevice(body: Schema.SasPortalDevice) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.SasPortalDevice])
				}
				object create {
					def apply(nodesId :PlayApi, deploymentsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1alpha1/nodes/${nodesId}/deployments/${deploymentsId}/devices").addQueryStringParameters("parent" -> parent.toString))
				}
				/** Creates a signed device under a node or customer. */
				class createSigned(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/sasportal""")
					/** Perform the request */
					def withSasPortalCreateSignedDeviceRequest(body: Schema.SasPortalCreateSignedDeviceRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.SasPortalDevice])
				}
				object createSigned {
					def apply(nodesId :PlayApi, deploymentsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): createSigned = new createSigned(ws.url(BASE_URL + s"v1alpha1/nodes/${nodesId}/deployments/${deploymentsId}/devices:createSigned").addQueryStringParameters("parent" -> parent.toString))
				}
			}
		}
	}
	object customers {
		/** Returns a list of SAS deployments associated with current GCP project. Includes whether SAS analytics has been enabled or not. */
		class listGcpProjectDeployments(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.SasPortalListGcpProjectDeploymentsResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/sasportal""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.SasPortalListGcpProjectDeploymentsResponse])
		}
		object listGcpProjectDeployments {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): listGcpProjectDeployments = new listGcpProjectDeployments(ws.url(BASE_URL + s"v1alpha1/customers:listGcpProjectDeployments").addQueryStringParameters())
			given Conversion[listGcpProjectDeployments, Future[Schema.SasPortalListGcpProjectDeploymentsResponse]] = (fun: listGcpProjectDeployments) => fun.apply()
		}
		/** Returns a list of legacy organizations. */
		class listLegacyOrganizations(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.SasPortalListLegacyOrganizationsResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/sasportal""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.SasPortalListLegacyOrganizationsResponse])
		}
		object listLegacyOrganizations {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): listLegacyOrganizations = new listLegacyOrganizations(ws.url(BASE_URL + s"v1alpha1/customers:listLegacyOrganizations").addQueryStringParameters())
			given Conversion[listLegacyOrganizations, Future[Schema.SasPortalListLegacyOrganizationsResponse]] = (fun: listLegacyOrganizations) => fun.apply()
		}
		/** Returns a requested customer. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.SasPortalCustomer]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/sasportal""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.SasPortalCustomer])
		}
		object get {
			def apply(customersId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1alpha1/customers/${customersId}").addQueryStringParameters("name" -> name.toString))
			given Conversion[get, Future[Schema.SasPortalCustomer]] = (fun: get) => fun.apply()
		}
		/** Returns a list of requested customers. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.SasPortalListCustomersResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/sasportal""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.SasPortalListCustomersResponse])
		}
		object list {
			def apply(pageToken: String, pageSize: Int)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1alpha1/customers").addQueryStringParameters("pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString))
			given Conversion[list, Future[Schema.SasPortalListCustomersResponse]] = (fun: list) => fun.apply()
		}
		/** Migrates a SAS organization to the cloud. This will create GCP projects for each deployment and associate them. The SAS Organization is linked to the gcp project that called the command. go/sas-legacy-customer-migration */
		class migrateOrganization(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/sasportal""")
			/** Perform the request */
			def withSasPortalMigrateOrganizationRequest(body: Schema.SasPortalMigrateOrganizationRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.SasPortalOperation])
		}
		object migrateOrganization {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): migrateOrganization = new migrateOrganization(ws.url(BASE_URL + s"v1alpha1/customers:migrateOrganization").addQueryStringParameters())
		}
		/** Setups the a GCP Project to receive SAS Analytics messages via GCP Pub/Sub with a subscription to BigQuery. All the Pub/Sub topics and BigQuery tables are created automatically as part of this service. */
		class setupSasAnalytics(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/sasportal""")
			/** Perform the request */
			def withSasPortalSetupSasAnalyticsRequest(body: Schema.SasPortalSetupSasAnalyticsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.SasPortalOperation])
		}
		object setupSasAnalytics {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): setupSasAnalytics = new setupSasAnalytics(ws.url(BASE_URL + s"v1alpha1/customers:setupSasAnalytics").addQueryStringParameters())
		}
		/** Creates a new SAS deployment through the GCP workflow. Creates a SAS organization if an organization match is not found. */
		class provisionDeployment(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/sasportal""")
			/** Perform the request */
			def withSasPortalProvisionDeploymentRequest(body: Schema.SasPortalProvisionDeploymentRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.SasPortalProvisionDeploymentResponse])
		}
		object provisionDeployment {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): provisionDeployment = new provisionDeployment(ws.url(BASE_URL + s"v1alpha1/customers:provisionDeployment").addQueryStringParameters())
		}
		/** Updates an existing customer. */
		class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/sasportal""")
			/** Perform the request */
			def withSasPortalCustomer(body: Schema.SasPortalCustomer) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.SasPortalCustomer])
		}
		object patch {
			def apply(customersId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1alpha1/customers/${customersId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
		}
		object nodes {
			/** Moves a node under another node or customer. */
			class move(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/sasportal""")
				/** Perform the request */
				def withSasPortalMoveNodeRequest(body: Schema.SasPortalMoveNodeRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.SasPortalOperation])
			}
			object move {
				def apply(customersId :PlayApi, nodesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): move = new move(ws.url(BASE_URL + s"v1alpha1/customers/${customersId}/nodes/${nodesId}:move").addQueryStringParameters("name" -> name.toString))
			}
			/** Creates a new node. */
			class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/sasportal""")
				/** Perform the request */
				def withSasPortalNode(body: Schema.SasPortalNode) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.SasPortalNode])
			}
			object create {
				def apply(customersId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1alpha1/customers/${customersId}/nodes").addQueryStringParameters("parent" -> parent.toString))
			}
			/** Deletes a node. */
			class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.SasPortalEmpty]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/sasportal""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.SasPortalEmpty])
			}
			object delete {
				def apply(customersId :PlayApi, nodesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1alpha1/customers/${customersId}/nodes/${nodesId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.SasPortalEmpty]] = (fun: delete) => fun.apply()
			}
			/** Returns a requested node. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.SasPortalNode]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/sasportal""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.SasPortalNode])
			}
			object get {
				def apply(customersId :PlayApi, nodesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1alpha1/customers/${customersId}/nodes/${nodesId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.SasPortalNode]] = (fun: get) => fun.apply()
			}
			/** Updates an existing node. */
			class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/sasportal""")
				/** Perform the request */
				def withSasPortalNode(body: Schema.SasPortalNode) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.SasPortalNode])
			}
			object patch {
				def apply(customersId :PlayApi, nodesId :PlayApi, updateMask: String, name: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1alpha1/customers/${customersId}/nodes/${nodesId}").addQueryStringParameters("updateMask" -> updateMask.toString, "name" -> name.toString))
			}
			/** Lists nodes. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.SasPortalListNodesResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/sasportal""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.SasPortalListNodesResponse])
			}
			object list {
				def apply(customersId :PlayApi, filter: String, parent: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1alpha1/customers/${customersId}/nodes").addQueryStringParameters("filter" -> filter.toString, "parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.SasPortalListNodesResponse]] = (fun: list) => fun.apply()
			}
			object nodes {
				/** Creates a new node. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/sasportal""")
					/** Perform the request */
					def withSasPortalNode(body: Schema.SasPortalNode) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.SasPortalNode])
				}
				object create {
					def apply(customersId :PlayApi, nodesId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1alpha1/customers/${customersId}/nodes/${nodesId}/nodes").addQueryStringParameters("parent" -> parent.toString))
				}
				/** Lists nodes. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.SasPortalListNodesResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/sasportal""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.SasPortalListNodesResponse])
				}
				object list {
					def apply(customersId :PlayApi, nodesId :PlayApi, filter: String, pageToken: String, parent: String, pageSize: Int)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1alpha1/customers/${customersId}/nodes/${nodesId}/nodes").addQueryStringParameters("filter" -> filter.toString, "pageToken" -> pageToken.toString, "parent" -> parent.toString, "pageSize" -> pageSize.toString))
					given Conversion[list, Future[Schema.SasPortalListNodesResponse]] = (fun: list) => fun.apply()
				}
			}
			object deployments {
				/** Lists deployments. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.SasPortalListDeploymentsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/sasportal""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.SasPortalListDeploymentsResponse])
				}
				object list {
					def apply(customersId :PlayApi, nodesId :PlayApi, pageToken: String, pageSize: Int, parent: String, filter: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1alpha1/customers/${customersId}/nodes/${nodesId}/deployments").addQueryStringParameters("pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString, "parent" -> parent.toString, "filter" -> filter.toString))
					given Conversion[list, Future[Schema.SasPortalListDeploymentsResponse]] = (fun: list) => fun.apply()
				}
				/** Creates a new deployment. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/sasportal""")
					/** Perform the request */
					def withSasPortalDeployment(body: Schema.SasPortalDeployment) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.SasPortalDeployment])
				}
				object create {
					def apply(customersId :PlayApi, nodesId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1alpha1/customers/${customersId}/nodes/${nodesId}/deployments").addQueryStringParameters("parent" -> parent.toString))
				}
			}
			object devices {
				/** Creates a signed device under a node or customer. */
				class createSigned(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/sasportal""")
					/** Perform the request */
					def withSasPortalCreateSignedDeviceRequest(body: Schema.SasPortalCreateSignedDeviceRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.SasPortalDevice])
				}
				object createSigned {
					def apply(customersId :PlayApi, nodesId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): createSigned = new createSigned(ws.url(BASE_URL + s"v1alpha1/customers/${customersId}/nodes/${nodesId}/devices:createSigned").addQueryStringParameters("parent" -> parent.toString))
				}
				/** Lists devices under a node or customer. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.SasPortalListDevicesResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/sasportal""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.SasPortalListDevicesResponse])
				}
				object list {
					def apply(customersId :PlayApi, nodesId :PlayApi, pageToken: String, pageSize: Int, parent: String, filter: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1alpha1/customers/${customersId}/nodes/${nodesId}/devices").addQueryStringParameters("pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString, "parent" -> parent.toString, "filter" -> filter.toString))
					given Conversion[list, Future[Schema.SasPortalListDevicesResponse]] = (fun: list) => fun.apply()
				}
				/** Creates a device under a node or customer. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/sasportal""")
					/** Perform the request */
					def withSasPortalDevice(body: Schema.SasPortalDevice) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.SasPortalDevice])
				}
				object create {
					def apply(customersId :PlayApi, nodesId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1alpha1/customers/${customersId}/nodes/${nodesId}/devices").addQueryStringParameters("parent" -> parent.toString))
				}
			}
		}
		object deployments {
			/** Moves a deployment under another node or customer. */
			class move(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/sasportal""")
				/** Perform the request */
				def withSasPortalMoveDeploymentRequest(body: Schema.SasPortalMoveDeploymentRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.SasPortalOperation])
			}
			object move {
				def apply(customersId :PlayApi, deploymentsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): move = new move(ws.url(BASE_URL + s"v1alpha1/customers/${customersId}/deployments/${deploymentsId}:move").addQueryStringParameters("name" -> name.toString))
			}
			/** Creates a new deployment. */
			class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/sasportal""")
				/** Perform the request */
				def withSasPortalDeployment(body: Schema.SasPortalDeployment) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.SasPortalDeployment])
			}
			object create {
				def apply(customersId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1alpha1/customers/${customersId}/deployments").addQueryStringParameters("parent" -> parent.toString))
			}
			/** Deletes a deployment. */
			class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.SasPortalEmpty]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/sasportal""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.SasPortalEmpty])
			}
			object delete {
				def apply(customersId :PlayApi, deploymentsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1alpha1/customers/${customersId}/deployments/${deploymentsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.SasPortalEmpty]] = (fun: delete) => fun.apply()
			}
			/** Returns a requested deployment. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.SasPortalDeployment]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/sasportal""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.SasPortalDeployment])
			}
			object get {
				def apply(customersId :PlayApi, deploymentsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1alpha1/customers/${customersId}/deployments/${deploymentsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.SasPortalDeployment]] = (fun: get) => fun.apply()
			}
			/** Updates an existing deployment. */
			class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/sasportal""")
				/** Perform the request */
				def withSasPortalDeployment(body: Schema.SasPortalDeployment) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.SasPortalDeployment])
			}
			object patch {
				def apply(customersId :PlayApi, deploymentsId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1alpha1/customers/${customersId}/deployments/${deploymentsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
			}
			/** Lists deployments. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.SasPortalListDeploymentsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/sasportal""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.SasPortalListDeploymentsResponse])
			}
			object list {
				def apply(customersId :PlayApi, pageToken: String, parent: String, filter: String, pageSize: Int)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1alpha1/customers/${customersId}/deployments").addQueryStringParameters("pageToken" -> pageToken.toString, "parent" -> parent.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString))
				given Conversion[list, Future[Schema.SasPortalListDeploymentsResponse]] = (fun: list) => fun.apply()
			}
			object devices {
				/** Lists devices under a node or customer. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.SasPortalListDevicesResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/sasportal""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.SasPortalListDevicesResponse])
				}
				object list {
					def apply(customersId :PlayApi, deploymentsId :PlayApi, pageToken: String, pageSize: Int, filter: String, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1alpha1/customers/${customersId}/deployments/${deploymentsId}/devices").addQueryStringParameters("pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString, "filter" -> filter.toString, "parent" -> parent.toString))
					given Conversion[list, Future[Schema.SasPortalListDevicesResponse]] = (fun: list) => fun.apply()
				}
				/** Creates a signed device under a node or customer. */
				class createSigned(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/sasportal""")
					/** Perform the request */
					def withSasPortalCreateSignedDeviceRequest(body: Schema.SasPortalCreateSignedDeviceRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.SasPortalDevice])
				}
				object createSigned {
					def apply(customersId :PlayApi, deploymentsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): createSigned = new createSigned(ws.url(BASE_URL + s"v1alpha1/customers/${customersId}/deployments/${deploymentsId}/devices:createSigned").addQueryStringParameters("parent" -> parent.toString))
				}
				/** Creates a device under a node or customer. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/sasportal""")
					/** Perform the request */
					def withSasPortalDevice(body: Schema.SasPortalDevice) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.SasPortalDevice])
				}
				object create {
					def apply(customersId :PlayApi, deploymentsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1alpha1/customers/${customersId}/deployments/${deploymentsId}/devices").addQueryStringParameters("parent" -> parent.toString))
				}
			}
		}
		object devices {
			/** Updates a signed device. */
			class updateSigned(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/sasportal""")
				/** Perform the request */
				def withSasPortalUpdateSignedDeviceRequest(body: Schema.SasPortalUpdateSignedDeviceRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.SasPortalDevice])
			}
			object updateSigned {
				def apply(customersId :PlayApi, devicesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): updateSigned = new updateSigned(ws.url(BASE_URL + s"v1alpha1/customers/${customersId}/devices/${devicesId}:updateSigned").addQueryStringParameters("name" -> name.toString))
			}
			/** Moves a device under another node or customer. */
			class move(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/sasportal""")
				/** Perform the request */
				def withSasPortalMoveDeviceRequest(body: Schema.SasPortalMoveDeviceRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.SasPortalOperation])
			}
			object move {
				def apply(customersId :PlayApi, devicesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): move = new move(ws.url(BASE_URL + s"v1alpha1/customers/${customersId}/devices/${devicesId}:move").addQueryStringParameters("name" -> name.toString))
			}
			/** Creates a device under a node or customer. */
			class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/sasportal""")
				/** Perform the request */
				def withSasPortalDevice(body: Schema.SasPortalDevice) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.SasPortalDevice])
			}
			object create {
				def apply(customersId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1alpha1/customers/${customersId}/devices").addQueryStringParameters("parent" -> parent.toString))
			}
			/** Signs a device. */
			class signDevice(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/sasportal""")
				/** Perform the request */
				def withSasPortalSignDeviceRequest(body: Schema.SasPortalSignDeviceRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.SasPortalEmpty])
			}
			object signDevice {
				def apply(customersId :PlayApi, devicesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): signDevice = new signDevice(ws.url(BASE_URL + s"v1alpha1/customers/${customersId}/devices/${devicesId}:signDevice").addQueryStringParameters("name" -> name.toString))
			}
			/** Creates a signed device under a node or customer. */
			class createSigned(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/sasportal""")
				/** Perform the request */
				def withSasPortalCreateSignedDeviceRequest(body: Schema.SasPortalCreateSignedDeviceRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.SasPortalDevice])
			}
			object createSigned {
				def apply(customersId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): createSigned = new createSigned(ws.url(BASE_URL + s"v1alpha1/customers/${customersId}/devices:createSigned").addQueryStringParameters("parent" -> parent.toString))
			}
			/** Deletes a device. */
			class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.SasPortalEmpty]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/sasportal""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.SasPortalEmpty])
			}
			object delete {
				def apply(customersId :PlayApi, devicesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1alpha1/customers/${customersId}/devices/${devicesId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.SasPortalEmpty]] = (fun: delete) => fun.apply()
			}
			/** Gets details about a device. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.SasPortalDevice]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/sasportal""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.SasPortalDevice])
			}
			object get {
				def apply(customersId :PlayApi, devicesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1alpha1/customers/${customersId}/devices/${devicesId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.SasPortalDevice]] = (fun: get) => fun.apply()
			}
			/** Updates a device. */
			class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/sasportal""")
				/** Perform the request */
				def withSasPortalDevice(body: Schema.SasPortalDevice) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.SasPortalDevice])
			}
			object patch {
				def apply(customersId :PlayApi, devicesId :PlayApi, updateMask: String, name: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1alpha1/customers/${customersId}/devices/${devicesId}").addQueryStringParameters("updateMask" -> updateMask.toString, "name" -> name.toString))
			}
			/** Lists devices under a node or customer. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.SasPortalListDevicesResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/sasportal""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.SasPortalListDevicesResponse])
			}
			object list {
				def apply(customersId :PlayApi, pageToken: String, pageSize: Int, parent: String, filter: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1alpha1/customers/${customersId}/devices").addQueryStringParameters("pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString, "parent" -> parent.toString, "filter" -> filter.toString))
				given Conversion[list, Future[Schema.SasPortalListDevicesResponse]] = (fun: list) => fun.apply()
			}
		}
	}
	object installer {
		/** Validates the identity of a Certified Professional Installer (CPI). */
		class validate(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/sasportal""")
			/** Perform the request */
			def withSasPortalValidateInstallerRequest(body: Schema.SasPortalValidateInstallerRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.SasPortalValidateInstallerResponse])
		}
		object validate {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): validate = new validate(ws.url(BASE_URL + s"v1alpha1/installer:validate").addQueryStringParameters())
		}
		/** Generates a secret to be used with the ValidateInstaller. */
		class generateSecret(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/sasportal""")
			/** Perform the request */
			def withSasPortalGenerateSecretRequest(body: Schema.SasPortalGenerateSecretRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.SasPortalGenerateSecretResponse])
		}
		object generateSecret {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): generateSecret = new generateSecret(ws.url(BASE_URL + s"v1alpha1/installer:generateSecret").addQueryStringParameters())
		}
	}
	object deployments {
		/** Returns a requested deployment. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.SasPortalDeployment]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/sasportal""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.SasPortalDeployment])
		}
		object get {
			def apply(deploymentsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1alpha1/deployments/${deploymentsId}").addQueryStringParameters("name" -> name.toString))
			given Conversion[get, Future[Schema.SasPortalDeployment]] = (fun: get) => fun.apply()
		}
		object devices {
			/** Updates a signed device. */
			class updateSigned(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/sasportal""")
				/** Perform the request */
				def withSasPortalUpdateSignedDeviceRequest(body: Schema.SasPortalUpdateSignedDeviceRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.SasPortalDevice])
			}
			object updateSigned {
				def apply(deploymentsId :PlayApi, devicesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): updateSigned = new updateSigned(ws.url(BASE_URL + s"v1alpha1/deployments/${deploymentsId}/devices/${devicesId}:updateSigned").addQueryStringParameters("name" -> name.toString))
			}
			/** Moves a device under another node or customer. */
			class move(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/sasportal""")
				/** Perform the request */
				def withSasPortalMoveDeviceRequest(body: Schema.SasPortalMoveDeviceRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.SasPortalOperation])
			}
			object move {
				def apply(deploymentsId :PlayApi, devicesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): move = new move(ws.url(BASE_URL + s"v1alpha1/deployments/${deploymentsId}/devices/${devicesId}:move").addQueryStringParameters("name" -> name.toString))
			}
			/** Signs a device. */
			class signDevice(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/sasportal""")
				/** Perform the request */
				def withSasPortalSignDeviceRequest(body: Schema.SasPortalSignDeviceRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.SasPortalEmpty])
			}
			object signDevice {
				def apply(deploymentsId :PlayApi, devicesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): signDevice = new signDevice(ws.url(BASE_URL + s"v1alpha1/deployments/${deploymentsId}/devices/${devicesId}:signDevice").addQueryStringParameters("name" -> name.toString))
			}
			/** Deletes a device. */
			class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.SasPortalEmpty]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/sasportal""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.SasPortalEmpty])
			}
			object delete {
				def apply(deploymentsId :PlayApi, devicesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1alpha1/deployments/${deploymentsId}/devices/${devicesId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.SasPortalEmpty]] = (fun: delete) => fun.apply()
			}
			/** Gets details about a device. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.SasPortalDevice]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/sasportal""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.SasPortalDevice])
			}
			object get {
				def apply(deploymentsId :PlayApi, devicesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1alpha1/deployments/${deploymentsId}/devices/${devicesId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.SasPortalDevice]] = (fun: get) => fun.apply()
			}
			/** Updates a device. */
			class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/sasportal""")
				/** Perform the request */
				def withSasPortalDevice(body: Schema.SasPortalDevice) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.SasPortalDevice])
			}
			object patch {
				def apply(deploymentsId :PlayApi, devicesId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1alpha1/deployments/${deploymentsId}/devices/${devicesId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
			}
		}
	}
	object policies {
		/** Sets the access control policy on the specified resource. Replaces any existing policy. */
		class set(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/sasportal""")
			/** Perform the request */
			def withSasPortalSetPolicyRequest(body: Schema.SasPortalSetPolicyRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.SasPortalPolicy])
		}
		object set {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): set = new set(ws.url(BASE_URL + s"v1alpha1/policies:set").addQueryStringParameters())
		}
		/** Gets the access control policy for a resource. Returns an empty policy if the resource exists and does not have a policy set. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/sasportal""")
			/** Perform the request */
			def withSasPortalGetPolicyRequest(body: Schema.SasPortalGetPolicyRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.SasPortalPolicy])
		}
		object get {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1alpha1/policies:get").addQueryStringParameters())
		}
		/** Returns permissions that a caller has on the specified resource. */
		class test(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/sasportal""")
			/** Perform the request */
			def withSasPortalTestPermissionsRequest(body: Schema.SasPortalTestPermissionsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.SasPortalTestPermissionsResponse])
		}
		object test {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): test = new test(ws.url(BASE_URL + s"v1alpha1/policies:test").addQueryStringParameters())
		}
	}
}
