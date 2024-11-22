package com.boresjo.play.api.google.containeranalysis

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://containeranalysis.googleapis.com/"

	object projects {
		object locations {
			object occurrences {
				class testIamPermissions(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withTestIamPermissionsRequest(body: Schema.TestIamPermissionsRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.TestIamPermissionsResponse])
				}
				object testIamPermissions {
					def apply(projectsId :PlayApi, locationsId :PlayApi, occurrencesId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): testIamPermissions = new testIamPermissions(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/occurrences/${occurrencesId}:testIamPermissions").addQueryStringParameters("resource" -> resource.toString))
				}
				class getVulnerabilitySummary(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.VulnerabilityOccurrencesSummary]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.VulnerabilityOccurrencesSummary])
				}
				object getVulnerabilitySummary {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, filter: String)(using auth: AuthToken, ec: ExecutionContext): getVulnerabilitySummary = new getVulnerabilitySummary(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/occurrences:vulnerabilitySummary").addQueryStringParameters("parent" -> parent.toString, "filter" -> filter.toString))
					given Conversion[getVulnerabilitySummary, Future[Schema.VulnerabilityOccurrencesSummary]] = (fun: getVulnerabilitySummary) => fun.apply()
				}
				class getIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGetIamPolicyRequest(body: Schema.GetIamPolicyRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
				}
				object getIamPolicy {
					def apply(projectsId :PlayApi, locationsId :PlayApi, occurrencesId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): getIamPolicy = new getIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/occurrences/${occurrencesId}:getIamPolicy").addQueryStringParameters("resource" -> resource.toString))
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, occurrencesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/occurrences/${occurrencesId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
				}
				class batchCreate(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withBatchCreateOccurrencesRequest(body: Schema.BatchCreateOccurrencesRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.BatchCreateOccurrencesResponse])
				}
				object batchCreate {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): batchCreate = new batchCreate(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/occurrences:batchCreate").addQueryStringParameters("parent" -> parent.toString))
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Occurrence]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Occurrence])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, occurrencesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/occurrences/${occurrencesId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.Occurrence]] = (fun: get) => fun.apply()
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListOccurrencesResponse]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListOccurrencesResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, pageToken: String, filter: String, pageSize: Int)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/occurrences").addQueryStringParameters("parent" -> parent.toString, "pageToken" -> pageToken.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString))
					given Conversion[list, Future[Schema.ListOccurrencesResponse]] = (fun: list) => fun.apply()
				}
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withOccurrence(body: Schema.Occurrence) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Occurrence])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/occurrences").addQueryStringParameters("parent" -> parent.toString))
				}
				class setIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withSetIamPolicyRequest(body: Schema.SetIamPolicyRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
				}
				object setIamPolicy {
					def apply(projectsId :PlayApi, locationsId :PlayApi, occurrencesId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): setIamPolicy = new setIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/occurrences/${occurrencesId}:setIamPolicy").addQueryStringParameters("resource" -> resource.toString))
				}
				class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withOccurrence(body: Schema.Occurrence) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Occurrence])
				}
				object patch {
					def apply(projectsId :PlayApi, locationsId :PlayApi, occurrencesId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/occurrences/${occurrencesId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
				}
				class getNotes(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Note]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Note])
				}
				object getNotes {
					def apply(projectsId :PlayApi, locationsId :PlayApi, occurrencesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): getNotes = new getNotes(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/occurrences/${occurrencesId}/notes").addQueryStringParameters("name" -> name.toString))
					given Conversion[getNotes, Future[Schema.Note]] = (fun: getNotes) => fun.apply()
				}
			}
			object notes {
				class testIamPermissions(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withTestIamPermissionsRequest(body: Schema.TestIamPermissionsRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.TestIamPermissionsResponse])
				}
				object testIamPermissions {
					def apply(projectsId :PlayApi, locationsId :PlayApi, notesId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): testIamPermissions = new testIamPermissions(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/notes/${notesId}:testIamPermissions").addQueryStringParameters("resource" -> resource.toString))
				}
				class getIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGetIamPolicyRequest(body: Schema.GetIamPolicyRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
				}
				object getIamPolicy {
					def apply(projectsId :PlayApi, locationsId :PlayApi, notesId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): getIamPolicy = new getIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/notes/${notesId}:getIamPolicy").addQueryStringParameters("resource" -> resource.toString))
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, notesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/notes/${notesId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
				}
				class batchCreate(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withBatchCreateNotesRequest(body: Schema.BatchCreateNotesRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.BatchCreateNotesResponse])
				}
				object batchCreate {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): batchCreate = new batchCreate(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/notes:batchCreate").addQueryStringParameters("parent" -> parent.toString))
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Note]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Note])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, notesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/notes/${notesId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.Note]] = (fun: get) => fun.apply()
				}
				class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withNote(body: Schema.Note) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Note])
				}
				object patch {
					def apply(projectsId :PlayApi, locationsId :PlayApi, notesId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/notes/${notesId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListNotesResponse]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListNotesResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, filter: String, pageSize: Int, parent: String, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/notes").addQueryStringParameters("filter" -> filter.toString, "pageSize" -> pageSize.toString, "parent" -> parent.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListNotesResponse]] = (fun: list) => fun.apply()
				}
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withNote(body: Schema.Note) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Note])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, noteId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/notes").addQueryStringParameters("parent" -> parent.toString, "noteId" -> noteId.toString))
				}
				class setIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withSetIamPolicyRequest(body: Schema.SetIamPolicyRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
				}
				object setIamPolicy {
					def apply(projectsId :PlayApi, locationsId :PlayApi, notesId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): setIamPolicy = new setIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/notes/${notesId}:setIamPolicy").addQueryStringParameters("resource" -> resource.toString))
				}
				object occurrences {
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListNoteOccurrencesResponse]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListNoteOccurrencesResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, notesId :PlayApi, pageSize: Int, filter: String, name: String, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/notes/${notesId}/occurrences").addQueryStringParameters("pageSize" -> pageSize.toString, "filter" -> filter.toString, "name" -> name.toString, "pageToken" -> pageToken.toString))
						given Conversion[list, Future[Schema.ListNoteOccurrencesResponse]] = (fun: list) => fun.apply()
					}
				}
			}
			object resources {
				class exportSBOM(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withExportSBOMRequest(body: Schema.ExportSBOMRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.ExportSBOMResponse])
				}
				object exportSBOM {
					def apply(projectsId :PlayApi, locationsId :PlayApi, resourcesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): exportSBOM = new exportSBOM(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/resources/${resourcesId}:exportSBOM").addQueryStringParameters("name" -> name.toString))
				}
			}
		}
		object occurrences {
			class testIamPermissions(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withTestIamPermissionsRequest(body: Schema.TestIamPermissionsRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.TestIamPermissionsResponse])
			}
			object testIamPermissions {
				def apply(projectsId :PlayApi, occurrencesId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): testIamPermissions = new testIamPermissions(ws.url(BASE_URL + s"v1/projects/${projectsId}/occurrences/${occurrencesId}:testIamPermissions").addQueryStringParameters("resource" -> resource.toString))
			}
			class getVulnerabilitySummary(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.VulnerabilityOccurrencesSummary]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.VulnerabilityOccurrencesSummary])
			}
			object getVulnerabilitySummary {
				def apply(projectsId :PlayApi, parent: String, filter: String)(using auth: AuthToken, ec: ExecutionContext): getVulnerabilitySummary = new getVulnerabilitySummary(ws.url(BASE_URL + s"v1/projects/${projectsId}/occurrences:vulnerabilitySummary").addQueryStringParameters("parent" -> parent.toString, "filter" -> filter.toString))
				given Conversion[getVulnerabilitySummary, Future[Schema.VulnerabilityOccurrencesSummary]] = (fun: getVulnerabilitySummary) => fun.apply()
			}
			class getIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGetIamPolicyRequest(body: Schema.GetIamPolicyRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
			}
			object getIamPolicy {
				def apply(projectsId :PlayApi, occurrencesId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): getIamPolicy = new getIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/occurrences/${occurrencesId}:getIamPolicy").addQueryStringParameters("resource" -> resource.toString))
			}
			class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
				def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
			}
			object delete {
				def apply(projectsId :PlayApi, occurrencesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/occurrences/${occurrencesId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
			}
			class batchCreate(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withBatchCreateOccurrencesRequest(body: Schema.BatchCreateOccurrencesRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.BatchCreateOccurrencesResponse])
			}
			object batchCreate {
				def apply(projectsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): batchCreate = new batchCreate(ws.url(BASE_URL + s"v1/projects/${projectsId}/occurrences:batchCreate").addQueryStringParameters("parent" -> parent.toString))
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Occurrence]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Occurrence])
			}
			object get {
				def apply(projectsId :PlayApi, occurrencesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/occurrences/${occurrencesId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.Occurrence]] = (fun: get) => fun.apply()
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListOccurrencesResponse]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListOccurrencesResponse])
			}
			object list {
				def apply(projectsId :PlayApi, pageToken: String, filter: String, parent: String, pageSize: Int)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/occurrences").addQueryStringParameters("pageToken" -> pageToken.toString, "filter" -> filter.toString, "parent" -> parent.toString, "pageSize" -> pageSize.toString))
				given Conversion[list, Future[Schema.ListOccurrencesResponse]] = (fun: list) => fun.apply()
			}
			class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withOccurrence(body: Schema.Occurrence) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Occurrence])
			}
			object create {
				def apply(projectsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/occurrences").addQueryStringParameters("parent" -> parent.toString))
			}
			class setIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withSetIamPolicyRequest(body: Schema.SetIamPolicyRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
			}
			object setIamPolicy {
				def apply(projectsId :PlayApi, occurrencesId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): setIamPolicy = new setIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/occurrences/${occurrencesId}:setIamPolicy").addQueryStringParameters("resource" -> resource.toString))
			}
			class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withOccurrence(body: Schema.Occurrence) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Occurrence])
			}
			object patch {
				def apply(projectsId :PlayApi, occurrencesId :PlayApi, updateMask: String, name: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/occurrences/${occurrencesId}").addQueryStringParameters("updateMask" -> updateMask.toString, "name" -> name.toString))
			}
			class getNotes(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Note]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Note])
			}
			object getNotes {
				def apply(projectsId :PlayApi, occurrencesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): getNotes = new getNotes(ws.url(BASE_URL + s"v1/projects/${projectsId}/occurrences/${occurrencesId}/notes").addQueryStringParameters("name" -> name.toString))
				given Conversion[getNotes, Future[Schema.Note]] = (fun: getNotes) => fun.apply()
			}
		}
		object notes {
			class testIamPermissions(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withTestIamPermissionsRequest(body: Schema.TestIamPermissionsRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.TestIamPermissionsResponse])
			}
			object testIamPermissions {
				def apply(projectsId :PlayApi, notesId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): testIamPermissions = new testIamPermissions(ws.url(BASE_URL + s"v1/projects/${projectsId}/notes/${notesId}:testIamPermissions").addQueryStringParameters("resource" -> resource.toString))
			}
			class getIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGetIamPolicyRequest(body: Schema.GetIamPolicyRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
			}
			object getIamPolicy {
				def apply(projectsId :PlayApi, notesId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): getIamPolicy = new getIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/notes/${notesId}:getIamPolicy").addQueryStringParameters("resource" -> resource.toString))
			}
			class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
				def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
			}
			object delete {
				def apply(projectsId :PlayApi, notesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/notes/${notesId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
			}
			class batchCreate(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withBatchCreateNotesRequest(body: Schema.BatchCreateNotesRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.BatchCreateNotesResponse])
			}
			object batchCreate {
				def apply(projectsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): batchCreate = new batchCreate(ws.url(BASE_URL + s"v1/projects/${projectsId}/notes:batchCreate").addQueryStringParameters("parent" -> parent.toString))
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Note]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Note])
			}
			object get {
				def apply(projectsId :PlayApi, notesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/notes/${notesId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.Note]] = (fun: get) => fun.apply()
			}
			class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withNote(body: Schema.Note) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Note])
			}
			object patch {
				def apply(projectsId :PlayApi, notesId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/notes/${notesId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListNotesResponse]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListNotesResponse])
			}
			object list {
				def apply(projectsId :PlayApi, filter: String, pageSize: Int, parent: String, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/notes").addQueryStringParameters("filter" -> filter.toString, "pageSize" -> pageSize.toString, "parent" -> parent.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListNotesResponse]] = (fun: list) => fun.apply()
			}
			class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withNote(body: Schema.Note) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Note])
			}
			object create {
				def apply(projectsId :PlayApi, parent: String, noteId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/notes").addQueryStringParameters("parent" -> parent.toString, "noteId" -> noteId.toString))
			}
			class setIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withSetIamPolicyRequest(body: Schema.SetIamPolicyRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
			}
			object setIamPolicy {
				def apply(projectsId :PlayApi, notesId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): setIamPolicy = new setIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/notes/${notesId}:setIamPolicy").addQueryStringParameters("resource" -> resource.toString))
			}
			object occurrences {
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListNoteOccurrencesResponse]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListNoteOccurrencesResponse])
				}
				object list {
					def apply(projectsId :PlayApi, notesId :PlayApi, name: String, pageToken: String, pageSize: Int, filter: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/notes/${notesId}/occurrences").addQueryStringParameters("name" -> name.toString, "pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString, "filter" -> filter.toString))
					given Conversion[list, Future[Schema.ListNoteOccurrencesResponse]] = (fun: list) => fun.apply()
				}
			}
		}
		object resources {
			class exportSBOM(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withExportSBOMRequest(body: Schema.ExportSBOMRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.ExportSBOMResponse])
			}
			object exportSBOM {
				def apply(projectsId :PlayApi, resourcesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): exportSBOM = new exportSBOM(ws.url(BASE_URL + s"v1/projects/${projectsId}/resources/${resourcesId}:exportSBOM").addQueryStringParameters("name" -> name.toString))
			}
		}
	}
}
