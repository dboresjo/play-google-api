package com.boresjo.play.api.google.contentwarehouse

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://contentwarehouse.googleapis.com/"

	object projects {
		class fetchAcl(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withGoogleCloudContentwarehouseV1FetchAclRequest(body: Schema.GoogleCloudContentwarehouseV1FetchAclRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudContentwarehouseV1FetchAclResponse])
		}
		object fetchAcl {
			def apply(projectsId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): fetchAcl = new fetchAcl(ws.url(BASE_URL + s"v1/projects/${projectsId}:fetchAcl").addQueryStringParameters("resource" -> resource.toString))
		}
		class setAcl(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withGoogleCloudContentwarehouseV1SetAclRequest(body: Schema.GoogleCloudContentwarehouseV1SetAclRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudContentwarehouseV1SetAclResponse])
		}
		object setAcl {
			def apply(projectsId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): setAcl = new setAcl(ws.url(BASE_URL + s"v1/projects/${projectsId}:setAcl").addQueryStringParameters("resource" -> resource.toString))
		}
		object locations {
			class runPipeline(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGoogleCloudContentwarehouseV1RunPipelineRequest(body: Schema.GoogleCloudContentwarehouseV1RunPipelineRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
			}
			object runPipeline {
				def apply(projectsId :PlayApi, locationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): runPipeline = new runPipeline(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}:runPipeline").addQueryStringParameters("name" -> name.toString))
			}
			class getStatus(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudContentwarehouseV1ProjectStatus]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudContentwarehouseV1ProjectStatus])
			}
			object getStatus {
				def apply(projectsId :PlayApi, locationsId :PlayApi, location: String)(using auth: AuthToken, ec: ExecutionContext): getStatus = new getStatus(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}:getStatus").addQueryStringParameters("location" -> location.toString))
				given Conversion[getStatus, Future[Schema.GoogleCloudContentwarehouseV1ProjectStatus]] = (fun: getStatus) => fun.apply()
			}
			class initialize(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGoogleCloudContentwarehouseV1InitializeProjectRequest(body: Schema.GoogleCloudContentwarehouseV1InitializeProjectRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
			}
			object initialize {
				def apply(projectsId :PlayApi, locationsId :PlayApi, location: String)(using auth: AuthToken, ec: ExecutionContext): initialize = new initialize(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}:initialize").addQueryStringParameters("location" -> location.toString))
			}
			object ruleSets {
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudContentwarehouseV1RuleSet(body: Schema.GoogleCloudContentwarehouseV1RuleSet) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudContentwarehouseV1RuleSet])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/ruleSets").addQueryStringParameters("parent" -> parent.toString))
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
					def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, ruleSetsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/ruleSets/${ruleSetsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudContentwarehouseV1RuleSet]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudContentwarehouseV1RuleSet])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, ruleSetsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/ruleSets/${ruleSetsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleCloudContentwarehouseV1RuleSet]] = (fun: get) => fun.apply()
				}
				class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudContentwarehouseV1UpdateRuleSetRequest(body: Schema.GoogleCloudContentwarehouseV1UpdateRuleSetRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudContentwarehouseV1RuleSet])
				}
				object patch {
					def apply(projectsId :PlayApi, locationsId :PlayApi, ruleSetsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/ruleSets/${ruleSetsId}").addQueryStringParameters("name" -> name.toString))
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudContentwarehouseV1ListRuleSetsResponse]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudContentwarehouseV1ListRuleSetsResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/ruleSets").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.GoogleCloudContentwarehouseV1ListRuleSetsResponse]] = (fun: list) => fun.apply()
				}
			}
			object operations {
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningOperation]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleLongrunningOperation])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, operationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleLongrunningOperation]] = (fun: get) => fun.apply()
				}
			}
			object synonymSets {
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudContentwarehouseV1SynonymSet(body: Schema.GoogleCloudContentwarehouseV1SynonymSet) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudContentwarehouseV1SynonymSet])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/synonymSets").addQueryStringParameters("parent" -> parent.toString))
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
					def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, synonymSetsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/synonymSets/${synonymSetsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudContentwarehouseV1SynonymSet]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudContentwarehouseV1SynonymSet])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, synonymSetsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/synonymSets/${synonymSetsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleCloudContentwarehouseV1SynonymSet]] = (fun: get) => fun.apply()
				}
				class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudContentwarehouseV1SynonymSet(body: Schema.GoogleCloudContentwarehouseV1SynonymSet) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudContentwarehouseV1SynonymSet])
				}
				object patch {
					def apply(projectsId :PlayApi, locationsId :PlayApi, synonymSetsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/synonymSets/${synonymSetsId}").addQueryStringParameters("name" -> name.toString))
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudContentwarehouseV1ListSynonymSetsResponse]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudContentwarehouseV1ListSynonymSetsResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/synonymSets").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.GoogleCloudContentwarehouseV1ListSynonymSetsResponse]] = (fun: list) => fun.apply()
				}
			}
			object documentSchemas {
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudContentwarehouseV1DocumentSchema(body: Schema.GoogleCloudContentwarehouseV1DocumentSchema) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudContentwarehouseV1DocumentSchema])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/documentSchemas").addQueryStringParameters("parent" -> parent.toString))
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
					def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, documentSchemasId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/documentSchemas/${documentSchemasId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudContentwarehouseV1DocumentSchema]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudContentwarehouseV1DocumentSchema])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, documentSchemasId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/documentSchemas/${documentSchemasId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleCloudContentwarehouseV1DocumentSchema]] = (fun: get) => fun.apply()
				}
				class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudContentwarehouseV1UpdateDocumentSchemaRequest(body: Schema.GoogleCloudContentwarehouseV1UpdateDocumentSchemaRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudContentwarehouseV1DocumentSchema])
				}
				object patch {
					def apply(projectsId :PlayApi, locationsId :PlayApi, documentSchemasId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/documentSchemas/${documentSchemasId}").addQueryStringParameters("name" -> name.toString))
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudContentwarehouseV1ListDocumentSchemasResponse]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudContentwarehouseV1ListDocumentSchemasResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/documentSchemas").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.GoogleCloudContentwarehouseV1ListDocumentSchemasResponse]] = (fun: list) => fun.apply()
				}
			}
			object documents {
				class fetchAcl(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudContentwarehouseV1FetchAclRequest(body: Schema.GoogleCloudContentwarehouseV1FetchAclRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudContentwarehouseV1FetchAclResponse])
				}
				object fetchAcl {
					def apply(projectsId :PlayApi, locationsId :PlayApi, documentsId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): fetchAcl = new fetchAcl(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/documents/${documentsId}:fetchAcl").addQueryStringParameters("resource" -> resource.toString))
				}
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudContentwarehouseV1CreateDocumentRequest(body: Schema.GoogleCloudContentwarehouseV1CreateDocumentRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudContentwarehouseV1CreateDocumentResponse])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/documents").addQueryStringParameters("parent" -> parent.toString))
				}
				class lock(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudContentwarehouseV1LockDocumentRequest(body: Schema.GoogleCloudContentwarehouseV1LockDocumentRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudContentwarehouseV1Document])
				}
				object lock {
					def apply(projectsId :PlayApi, locationsId :PlayApi, documentsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): lock = new lock(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/documents/${documentsId}:lock").addQueryStringParameters("name" -> name.toString))
				}
				class linkedTargets(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudContentwarehouseV1ListLinkedTargetsRequest(body: Schema.GoogleCloudContentwarehouseV1ListLinkedTargetsRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudContentwarehouseV1ListLinkedTargetsResponse])
				}
				object linkedTargets {
					def apply(projectsId :PlayApi, locationsId :PlayApi, documentsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): linkedTargets = new linkedTargets(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/documents/${documentsId}/linkedTargets").addQueryStringParameters("parent" -> parent.toString))
				}
				class setAcl(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudContentwarehouseV1SetAclRequest(body: Schema.GoogleCloudContentwarehouseV1SetAclRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudContentwarehouseV1SetAclResponse])
				}
				object setAcl {
					def apply(projectsId :PlayApi, locationsId :PlayApi, documentsId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): setAcl = new setAcl(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/documents/${documentsId}:setAcl").addQueryStringParameters("resource" -> resource.toString))
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudContentwarehouseV1DeleteDocumentRequest(body: Schema.GoogleCloudContentwarehouseV1DeleteDocumentRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleProtobufEmpty])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, documentsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/documents/${documentsId}:delete").addQueryStringParameters("name" -> name.toString))
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudContentwarehouseV1GetDocumentRequest(body: Schema.GoogleCloudContentwarehouseV1GetDocumentRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudContentwarehouseV1Document])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, documentsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/documents/${documentsId}:get").addQueryStringParameters("name" -> name.toString))
				}
				class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudContentwarehouseV1UpdateDocumentRequest(body: Schema.GoogleCloudContentwarehouseV1UpdateDocumentRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudContentwarehouseV1UpdateDocumentResponse])
				}
				object patch {
					def apply(projectsId :PlayApi, locationsId :PlayApi, documentsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/documents/${documentsId}").addQueryStringParameters("name" -> name.toString))
				}
				class search(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudContentwarehouseV1SearchDocumentsRequest(body: Schema.GoogleCloudContentwarehouseV1SearchDocumentsRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudContentwarehouseV1SearchDocumentsResponse])
				}
				object search {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): search = new search(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/documents:search").addQueryStringParameters("parent" -> parent.toString))
				}
				class linkedSources(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudContentwarehouseV1ListLinkedSourcesRequest(body: Schema.GoogleCloudContentwarehouseV1ListLinkedSourcesRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudContentwarehouseV1ListLinkedSourcesResponse])
				}
				object linkedSources {
					def apply(projectsId :PlayApi, locationsId :PlayApi, documentsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): linkedSources = new linkedSources(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/documents/${documentsId}/linkedSources").addQueryStringParameters("parent" -> parent.toString))
				}
				object referenceId {
					class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleCloudContentwarehouseV1DeleteDocumentRequest(body: Schema.GoogleCloudContentwarehouseV1DeleteDocumentRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleProtobufEmpty])
					}
					object delete {
						def apply(projectsId :PlayApi, locationsId :PlayApi, referenceIdId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/documents/referenceId/${referenceIdId}:delete").addQueryStringParameters("name" -> name.toString))
					}
					class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleCloudContentwarehouseV1UpdateDocumentRequest(body: Schema.GoogleCloudContentwarehouseV1UpdateDocumentRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudContentwarehouseV1UpdateDocumentResponse])
					}
					object patch {
						def apply(projectsId :PlayApi, locationsId :PlayApi, referenceIdId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/documents/referenceId/${referenceIdId}").addQueryStringParameters("name" -> name.toString))
					}
					class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleCloudContentwarehouseV1GetDocumentRequest(body: Schema.GoogleCloudContentwarehouseV1GetDocumentRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudContentwarehouseV1Document])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, referenceIdId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/documents/referenceId/${referenceIdId}:get").addQueryStringParameters("name" -> name.toString))
					}
				}
				object documentLinks {
					class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleCloudContentwarehouseV1DeleteDocumentLinkRequest(body: Schema.GoogleCloudContentwarehouseV1DeleteDocumentLinkRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleProtobufEmpty])
					}
					object delete {
						def apply(projectsId :PlayApi, locationsId :PlayApi, documentsId :PlayApi, documentLinksId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/documents/${documentsId}/documentLinks/${documentLinksId}:delete").addQueryStringParameters("name" -> name.toString))
					}
					class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleCloudContentwarehouseV1CreateDocumentLinkRequest(body: Schema.GoogleCloudContentwarehouseV1CreateDocumentLinkRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudContentwarehouseV1DocumentLink])
					}
					object create {
						def apply(projectsId :PlayApi, locationsId :PlayApi, documentsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/documents/${documentsId}/documentLinks").addQueryStringParameters("parent" -> parent.toString))
					}
				}
			}
		}
	}
}
