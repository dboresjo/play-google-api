package com.boresjo.play.api.google.cloudsupport

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://cloudsupport.googleapis.com/"

	object cases {
		class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withCase(body: Schema.Case) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Case])
		}
		object create {
			def apply(v2Id :PlayApi, v2Id1 :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v2/${v2Id}/${v2Id1}/cases")).addQueryStringParameters("parent" -> parent.toString))
		}
		class escalate(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withEscalateCaseRequest(body: Schema.EscalateCaseRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Case])
		}
		object escalate {
			def apply(v2Id :PlayApi, v2Id1 :PlayApi, casesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): escalate = new escalate(auth(ws.url(BASE_URL + s"v2/${v2Id}/${v2Id1}/cases/${casesId}:escalate")).addQueryStringParameters("name" -> name.toString))
		}
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Case]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.Case])
		}
		object get {
			def apply(v2Id :PlayApi, v2Id1 :PlayApi, casesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v2/${v2Id}/${v2Id1}/cases/${casesId}")).addQueryStringParameters("name" -> name.toString))
			given Conversion[get, Future[Schema.Case]] = (fun: get) => fun.apply()
		}
		class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withCase(body: Schema.Case) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.Case])
		}
		object patch {
			def apply(v2Id :PlayApi, v2Id1 :PlayApi, casesId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"v2/${v2Id}/${v2Id1}/cases/${casesId}")).addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
		}
		class close(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withCloseCaseRequest(body: Schema.CloseCaseRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Case])
		}
		object close {
			def apply(v2Id :PlayApi, v2Id1 :PlayApi, casesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): close = new close(auth(ws.url(BASE_URL + s"v2/${v2Id}/${v2Id1}/cases/${casesId}:close")).addQueryStringParameters("name" -> name.toString))
		}
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListCasesResponse]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.ListCasesResponse])
		}
		object list {
			def apply(v2Id :PlayApi, v2Id1 :PlayApi, pageSize: Int, parent: String, filter: String, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v2/${v2Id}/${v2Id1}/cases")).addQueryStringParameters("pageSize" -> pageSize.toString, "parent" -> parent.toString, "filter" -> filter.toString, "pageToken" -> pageToken.toString))
			given Conversion[list, Future[Schema.ListCasesResponse]] = (fun: list) => fun.apply()
		}
		class search(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.SearchCasesResponse]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.SearchCasesResponse])
		}
		object search {
			def apply(v2Id :PlayApi, v2Id1 :PlayApi, parent: String, query: String, pageToken: String, pageSize: Int)(using auth: AuthToken, ec: ExecutionContext): search = new search(auth(ws.url(BASE_URL + s"v2/${v2Id}/${v2Id1}/cases:search")).addQueryStringParameters("parent" -> parent.toString, "query" -> query.toString, "pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString))
			given Conversion[search, Future[Schema.SearchCasesResponse]] = (fun: search) => fun.apply()
		}
		object attachments {
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListAttachmentsResponse]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.ListAttachmentsResponse])
			}
			object list {
				def apply(v2Id :PlayApi, v2Id1 :PlayApi, casesId :PlayApi, pageToken: String, parent: String, pageSize: Int)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v2/${v2Id}/${v2Id1}/cases/${casesId}/attachments")).addQueryStringParameters("pageToken" -> pageToken.toString, "parent" -> parent.toString, "pageSize" -> pageSize.toString))
				given Conversion[list, Future[Schema.ListAttachmentsResponse]] = (fun: list) => fun.apply()
			}
		}
		object comments {
			class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withComment(body: Schema.Comment) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Comment])
			}
			object create {
				def apply(v2Id :PlayApi, v2Id1 :PlayApi, casesId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v2/${v2Id}/${v2Id1}/cases/${casesId}/comments")).addQueryStringParameters("parent" -> parent.toString))
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListCommentsResponse]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.ListCommentsResponse])
			}
			object list {
				def apply(v2Id :PlayApi, v2Id1 :PlayApi, casesId :PlayApi, pageSize: Int, parent: String, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v2/${v2Id}/${v2Id1}/cases/${casesId}/comments")).addQueryStringParameters("pageSize" -> pageSize.toString, "parent" -> parent.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListCommentsResponse]] = (fun: list) => fun.apply()
			}
		}
	}
	object media {
		class download(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Media]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.Media])
		}
		object download {
			def apply(v2Id :PlayApi, v2Id1 :PlayApi, casesId :PlayApi, attachmentsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): download = new download(auth(ws.url(BASE_URL + s"v2/${v2Id}/${v2Id1}/cases/${casesId}/attachments/${attachmentsId}:download")).addQueryStringParameters("name" -> name.toString))
			given Conversion[download, Future[Schema.Media]] = (fun: download) => fun.apply()
		}
		class upload(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withCreateAttachmentRequest(body: Schema.CreateAttachmentRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Attachment])
		}
		object upload {
			def apply(v2Id :PlayApi, v2Id1 :PlayApi, casesId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): upload = new upload(auth(ws.url(BASE_URL + s"v2/${v2Id}/${v2Id1}/cases/${casesId}/attachments")).addQueryStringParameters("parent" -> parent.toString))
		}
	}
	object caseClassifications {
		class search(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.SearchCaseClassificationsResponse]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.SearchCaseClassificationsResponse])
		}
		object search {
			def apply(pageToken: String, query: String, pageSize: Int)(using auth: AuthToken, ec: ExecutionContext): search = new search(auth(ws.url(BASE_URL + s"v2/caseClassifications:search")).addQueryStringParameters("pageToken" -> pageToken.toString, "query" -> query.toString, "pageSize" -> pageSize.toString))
			given Conversion[search, Future[Schema.SearchCaseClassificationsResponse]] = (fun: search) => fun.apply()
		}
	}
}
