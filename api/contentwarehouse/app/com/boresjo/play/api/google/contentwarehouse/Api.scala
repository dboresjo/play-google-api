package com.boresjo.play.api.google.contentwarehouse

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

	private val BASE_URL = "https://contentwarehouse.googleapis.com/"

	object projects {
		/** Gets the access control policy for a resource. Returns NOT_FOUND error if the resource does not exist. Returns an empty policy if the resource exists but does not have a policy set. */
		class fetchAcl(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def withGoogleCloudContentwarehouseV1FetchAclRequest(body: Schema.GoogleCloudContentwarehouseV1FetchAclRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudContentwarehouseV1FetchAclResponse])
		}
		object fetchAcl {
			def apply(projectsId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): fetchAcl = new fetchAcl(ws.url(BASE_URL + s"v1/projects/${projectsId}:fetchAcl").addQueryStringParameters("resource" -> resource.toString))
		}
		/** Sets the access control policy for a resource. Replaces any existing policy. */
		class setAcl(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def withGoogleCloudContentwarehouseV1SetAclRequest(body: Schema.GoogleCloudContentwarehouseV1SetAclRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudContentwarehouseV1SetAclResponse])
		}
		object setAcl {
			def apply(projectsId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): setAcl = new setAcl(ws.url(BASE_URL + s"v1/projects/${projectsId}:setAcl").addQueryStringParameters("resource" -> resource.toString))
		}
		object locations {
			/** Run a predefined pipeline. */
			class runPipeline(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withGoogleCloudContentwarehouseV1RunPipelineRequest(body: Schema.GoogleCloudContentwarehouseV1RunPipelineRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
			}
			object runPipeline {
				def apply(projectsId :PlayApi, locationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): runPipeline = new runPipeline(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}:runPipeline").addQueryStringParameters("name" -> name.toString))
			}
			/** Get the project status. */
			class getStatus(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudContentwarehouseV1ProjectStatus]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudContentwarehouseV1ProjectStatus])
			}
			object getStatus {
				def apply(projectsId :PlayApi, locationsId :PlayApi, location: String)(using signer: RequestSigner, ec: ExecutionContext): getStatus = new getStatus(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}:getStatus").addQueryStringParameters("location" -> location.toString))
				given Conversion[getStatus, Future[Schema.GoogleCloudContentwarehouseV1ProjectStatus]] = (fun: getStatus) => fun.apply()
			}
			/** Provisions resources for given tenant project. Returns a long running operation. */
			class initialize(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withGoogleCloudContentwarehouseV1InitializeProjectRequest(body: Schema.GoogleCloudContentwarehouseV1InitializeProjectRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
			}
			object initialize {
				def apply(projectsId :PlayApi, locationsId :PlayApi, location: String)(using signer: RequestSigner, ec: ExecutionContext): initialize = new initialize(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}:initialize").addQueryStringParameters("location" -> location.toString))
			}
			object ruleSets {
				/** Creates a ruleset. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGoogleCloudContentwarehouseV1RuleSet(body: Schema.GoogleCloudContentwarehouseV1RuleSet) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudContentwarehouseV1RuleSet])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/ruleSets").addQueryStringParameters("parent" -> parent.toString))
				}
				/** Deletes a ruleset. Returns NOT_FOUND if the document does not exist. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, ruleSetsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/ruleSets/${ruleSetsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
				}
				/** Gets a ruleset. Returns NOT_FOUND if the ruleset does not exist. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudContentwarehouseV1RuleSet]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudContentwarehouseV1RuleSet])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, ruleSetsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/ruleSets/${ruleSetsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleCloudContentwarehouseV1RuleSet]] = (fun: get) => fun.apply()
				}
				/** Updates a ruleset. Returns INVALID_ARGUMENT if the name of the ruleset is non-empty and does not equal the existing name. */
				class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGoogleCloudContentwarehouseV1UpdateRuleSetRequest(body: Schema.GoogleCloudContentwarehouseV1UpdateRuleSetRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudContentwarehouseV1RuleSet])
				}
				object patch {
					def apply(projectsId :PlayApi, locationsId :PlayApi, ruleSetsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/ruleSets/${ruleSetsId}").addQueryStringParameters("name" -> name.toString))
				}
				/** Lists rulesets. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudContentwarehouseV1ListRuleSetsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudContentwarehouseV1ListRuleSetsResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/ruleSets").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.GoogleCloudContentwarehouseV1ListRuleSetsResponse]] = (fun: list) => fun.apply()
				}
			}
			object operations {
				/** Gets the latest state of a long-running operation. Clients can use this method to poll the operation result at intervals as recommended by the API service. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningOperation]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleLongrunningOperation])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, operationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleLongrunningOperation]] = (fun: get) => fun.apply()
				}
			}
			object synonymSets {
				/** Creates a SynonymSet for a single context. Throws an ALREADY_EXISTS exception if a synonymset already exists for the context. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGoogleCloudContentwarehouseV1SynonymSet(body: Schema.GoogleCloudContentwarehouseV1SynonymSet) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudContentwarehouseV1SynonymSet])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/synonymSets").addQueryStringParameters("parent" -> parent.toString))
				}
				/** Deletes a SynonymSet for a given context. Throws a NOT_FOUND exception if the SynonymSet is not found. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, synonymSetsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/synonymSets/${synonymSetsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
				}
				/** Gets a SynonymSet for a particular context. Throws a NOT_FOUND exception if the Synonymset does not exist */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudContentwarehouseV1SynonymSet]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudContentwarehouseV1SynonymSet])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, synonymSetsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/synonymSets/${synonymSetsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleCloudContentwarehouseV1SynonymSet]] = (fun: get) => fun.apply()
				}
				/** Remove the existing SynonymSet for the context and replaces it with a new one. Throws a NOT_FOUND exception if the SynonymSet is not found. */
				class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGoogleCloudContentwarehouseV1SynonymSet(body: Schema.GoogleCloudContentwarehouseV1SynonymSet) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudContentwarehouseV1SynonymSet])
				}
				object patch {
					def apply(projectsId :PlayApi, locationsId :PlayApi, synonymSetsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/synonymSets/${synonymSetsId}").addQueryStringParameters("name" -> name.toString))
				}
				/** Returns all SynonymSets (for all contexts) for the specified location. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudContentwarehouseV1ListSynonymSetsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudContentwarehouseV1ListSynonymSetsResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/synonymSets").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.GoogleCloudContentwarehouseV1ListSynonymSetsResponse]] = (fun: list) => fun.apply()
				}
			}
			object documentSchemas {
				/** Creates a document schema. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGoogleCloudContentwarehouseV1DocumentSchema(body: Schema.GoogleCloudContentwarehouseV1DocumentSchema) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudContentwarehouseV1DocumentSchema])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/documentSchemas").addQueryStringParameters("parent" -> parent.toString))
				}
				/** Deletes a document schema. Returns NOT_FOUND if the document schema does not exist. Returns BAD_REQUEST if the document schema has documents depending on it. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, documentSchemasId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/documentSchemas/${documentSchemasId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
				}
				/** Gets a document schema. Returns NOT_FOUND if the document schema does not exist. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudContentwarehouseV1DocumentSchema]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudContentwarehouseV1DocumentSchema])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, documentSchemasId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/documentSchemas/${documentSchemasId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleCloudContentwarehouseV1DocumentSchema]] = (fun: get) => fun.apply()
				}
				/** Updates a Document Schema. Returns INVALID_ARGUMENT if the name of the Document Schema is non-empty and does not equal the existing name. Supports only appending new properties, adding new ENUM possible values, and updating the EnumTypeOptions.validation_check_disabled flag for ENUM possible values. Updating existing properties will result into INVALID_ARGUMENT. */
				class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGoogleCloudContentwarehouseV1UpdateDocumentSchemaRequest(body: Schema.GoogleCloudContentwarehouseV1UpdateDocumentSchemaRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudContentwarehouseV1DocumentSchema])
				}
				object patch {
					def apply(projectsId :PlayApi, locationsId :PlayApi, documentSchemasId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/documentSchemas/${documentSchemasId}").addQueryStringParameters("name" -> name.toString))
				}
				/** Lists document schemas. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudContentwarehouseV1ListDocumentSchemasResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudContentwarehouseV1ListDocumentSchemasResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/documentSchemas").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.GoogleCloudContentwarehouseV1ListDocumentSchemasResponse]] = (fun: list) => fun.apply()
				}
			}
			object documents {
				/** Gets the access control policy for a resource. Returns NOT_FOUND error if the resource does not exist. Returns an empty policy if the resource exists but does not have a policy set. */
				class fetchAcl(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGoogleCloudContentwarehouseV1FetchAclRequest(body: Schema.GoogleCloudContentwarehouseV1FetchAclRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudContentwarehouseV1FetchAclResponse])
				}
				object fetchAcl {
					def apply(projectsId :PlayApi, locationsId :PlayApi, documentsId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): fetchAcl = new fetchAcl(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/documents/${documentsId}:fetchAcl").addQueryStringParameters("resource" -> resource.toString))
				}
				/** Creates a document. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGoogleCloudContentwarehouseV1CreateDocumentRequest(body: Schema.GoogleCloudContentwarehouseV1CreateDocumentRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudContentwarehouseV1CreateDocumentResponse])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/documents").addQueryStringParameters("parent" -> parent.toString))
				}
				/** Lock the document so the document cannot be updated by other users. */
				class lock(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGoogleCloudContentwarehouseV1LockDocumentRequest(body: Schema.GoogleCloudContentwarehouseV1LockDocumentRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudContentwarehouseV1Document])
				}
				object lock {
					def apply(projectsId :PlayApi, locationsId :PlayApi, documentsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): lock = new lock(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/documents/${documentsId}:lock").addQueryStringParameters("name" -> name.toString))
				}
				/** Return all target document-links from the document. */
				class linkedTargets(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGoogleCloudContentwarehouseV1ListLinkedTargetsRequest(body: Schema.GoogleCloudContentwarehouseV1ListLinkedTargetsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudContentwarehouseV1ListLinkedTargetsResponse])
				}
				object linkedTargets {
					def apply(projectsId :PlayApi, locationsId :PlayApi, documentsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): linkedTargets = new linkedTargets(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/documents/${documentsId}/linkedTargets").addQueryStringParameters("parent" -> parent.toString))
				}
				/** Sets the access control policy for a resource. Replaces any existing policy. */
				class setAcl(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGoogleCloudContentwarehouseV1SetAclRequest(body: Schema.GoogleCloudContentwarehouseV1SetAclRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudContentwarehouseV1SetAclResponse])
				}
				object setAcl {
					def apply(projectsId :PlayApi, locationsId :PlayApi, documentsId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): setAcl = new setAcl(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/documents/${documentsId}:setAcl").addQueryStringParameters("resource" -> resource.toString))
				}
				/** Deletes a document. Returns NOT_FOUND if the document does not exist. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGoogleCloudContentwarehouseV1DeleteDocumentRequest(body: Schema.GoogleCloudContentwarehouseV1DeleteDocumentRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleProtobufEmpty])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, documentsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/documents/${documentsId}:delete").addQueryStringParameters("name" -> name.toString))
				}
				/** Gets a document. Returns NOT_FOUND if the document does not exist. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGoogleCloudContentwarehouseV1GetDocumentRequest(body: Schema.GoogleCloudContentwarehouseV1GetDocumentRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudContentwarehouseV1Document])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, documentsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/documents/${documentsId}:get").addQueryStringParameters("name" -> name.toString))
				}
				/** Updates a document. Returns INVALID_ARGUMENT if the name of the document is non-empty and does not equal the existing name. */
				class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGoogleCloudContentwarehouseV1UpdateDocumentRequest(body: Schema.GoogleCloudContentwarehouseV1UpdateDocumentRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudContentwarehouseV1UpdateDocumentResponse])
				}
				object patch {
					def apply(projectsId :PlayApi, locationsId :PlayApi, documentsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/documents/${documentsId}").addQueryStringParameters("name" -> name.toString))
				}
				/** Searches for documents using provided SearchDocumentsRequest. This call only returns documents that the caller has permission to search against. */
				class search(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGoogleCloudContentwarehouseV1SearchDocumentsRequest(body: Schema.GoogleCloudContentwarehouseV1SearchDocumentsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudContentwarehouseV1SearchDocumentsResponse])
				}
				object search {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): search = new search(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/documents:search").addQueryStringParameters("parent" -> parent.toString))
				}
				/** Return all source document-links from the document. */
				class linkedSources(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGoogleCloudContentwarehouseV1ListLinkedSourcesRequest(body: Schema.GoogleCloudContentwarehouseV1ListLinkedSourcesRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudContentwarehouseV1ListLinkedSourcesResponse])
				}
				object linkedSources {
					def apply(projectsId :PlayApi, locationsId :PlayApi, documentsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): linkedSources = new linkedSources(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/documents/${documentsId}/linkedSources").addQueryStringParameters("parent" -> parent.toString))
				}
				object referenceId {
					/** Deletes a document. Returns NOT_FOUND if the document does not exist. */
					class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withGoogleCloudContentwarehouseV1DeleteDocumentRequest(body: Schema.GoogleCloudContentwarehouseV1DeleteDocumentRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleProtobufEmpty])
					}
					object delete {
						def apply(projectsId :PlayApi, locationsId :PlayApi, referenceIdId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/documents/referenceId/${referenceIdId}:delete").addQueryStringParameters("name" -> name.toString))
					}
					/** Updates a document. Returns INVALID_ARGUMENT if the name of the document is non-empty and does not equal the existing name. */
					class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withGoogleCloudContentwarehouseV1UpdateDocumentRequest(body: Schema.GoogleCloudContentwarehouseV1UpdateDocumentRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudContentwarehouseV1UpdateDocumentResponse])
					}
					object patch {
						def apply(projectsId :PlayApi, locationsId :PlayApi, referenceIdId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/documents/referenceId/${referenceIdId}").addQueryStringParameters("name" -> name.toString))
					}
					/** Gets a document. Returns NOT_FOUND if the document does not exist. */
					class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withGoogleCloudContentwarehouseV1GetDocumentRequest(body: Schema.GoogleCloudContentwarehouseV1GetDocumentRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudContentwarehouseV1Document])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, referenceIdId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/documents/referenceId/${referenceIdId}:get").addQueryStringParameters("name" -> name.toString))
					}
				}
				object documentLinks {
					/** Remove the link between the source and target documents. */
					class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withGoogleCloudContentwarehouseV1DeleteDocumentLinkRequest(body: Schema.GoogleCloudContentwarehouseV1DeleteDocumentLinkRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleProtobufEmpty])
					}
					object delete {
						def apply(projectsId :PlayApi, locationsId :PlayApi, documentsId :PlayApi, documentLinksId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/documents/${documentsId}/documentLinks/${documentLinksId}:delete").addQueryStringParameters("name" -> name.toString))
					}
					/** Create a link between a source document and a target document. */
					class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withGoogleCloudContentwarehouseV1CreateDocumentLinkRequest(body: Schema.GoogleCloudContentwarehouseV1CreateDocumentLinkRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudContentwarehouseV1DocumentLink])
					}
					object create {
						def apply(projectsId :PlayApi, locationsId :PlayApi, documentsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/documents/${documentsId}/documentLinks").addQueryStringParameters("parent" -> parent.toString))
					}
				}
			}
		}
	}
}
