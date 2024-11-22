package com.boresjo.play.api.google.mybusinessqanda

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	val scopes = Seq()

	private val BASE_URL = "https://mybusinessqanda.googleapis.com/"

	object locations {
		object questions {
			/** Adds a question for the specified location. */
			class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq()
				/** Perform the request */
				def withQuestion(body: Schema.Question) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Question])
			}
			object create {
				def apply(locationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/locations/${locationsId}/questions").addQueryStringParameters("parent" -> parent.toString))
			}
			/** Deletes a specific question written by the current user. */
			class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
				val scopes = Seq()
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
			}
			object delete {
				def apply(locationsId :PlayApi, questionsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/locations/${locationsId}/questions/${questionsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
			}
			/** Updates a specific question written by the current user. */
			class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq()
				/** Perform the request */
				def withQuestion(body: Schema.Question) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Question])
			}
			object patch {
				def apply(locationsId :PlayApi, questionsId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/locations/${locationsId}/questions/${questionsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
			}
			/** Returns the paginated list of questions and some of its answers for a specified location. This operation is only valid if the specified location is verified. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListQuestionsResponse]) {
				val scopes = Seq()
				/** Optional. How many questions to fetch per page. The default and maximum `page_size` values are 10.<br>Format: int32 */
				def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
				/** Optional. If specified, the next page of questions is retrieved. */
				def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
				/** Optional. How many answers to fetch per question. The default and maximum `answers_per_question` values are 10.<br>Format: int32 */
				def withAnswersPerQuestion(answersPerQuestion: Int) = new list(req.addQueryStringParameters("answersPerQuestion" -> answersPerQuestion.toString))
				/** Optional. A filter constraining the questions to return. The only filter currently supported is "ignore_answered=true" */
				def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
				/** Optional. The order to return the questions. Valid options include 'update_time desc' and 'upvote_count desc', which will return the questions sorted descendingly by the requested field. The default sort order is 'update_time desc'. */
				def withOrderBy(orderBy: String) = new list(req.addQueryStringParameters("orderBy" -> orderBy.toString))
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListQuestionsResponse])
			}
			object list {
				def apply(locationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/locations/${locationsId}/questions").addQueryStringParameters("parent" -> parent.toString))
				given Conversion[list, Future[Schema.ListQuestionsResponse]] = (fun: list) => fun.apply()
			}
			object answers {
				/** Returns the paginated list of answers for a specified question. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListAnswersResponse]) {
					val scopes = Seq()
					/** Optional. How many answers to fetch per page. The default and maximum `page_size` values are 10.<br>Format: int32 */
					def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
					/** Optional. If specified, the next page of answers is retrieved. */
					def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
					/** Optional. The order to return the answers. Valid options include 'update_time desc' and 'upvote_count desc', which will return the answers sorted descendingly by the requested field. The default sort order is 'update_time desc'. */
					def withOrderBy(orderBy: String) = new list(req.addQueryStringParameters("orderBy" -> orderBy.toString))
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListAnswersResponse])
				}
				object list {
					def apply(locationsId :PlayApi, questionsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/locations/${locationsId}/questions/${questionsId}/answers").addQueryStringParameters("parent" -> parent.toString))
					given Conversion[list, Future[Schema.ListAnswersResponse]] = (fun: list) => fun.apply()
				}
				/** Creates an answer or updates the existing answer written by the user for the specified question. A user can only create one answer per question. */
				class upsert(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq()
					/** Perform the request */
					def withUpsertAnswerRequest(body: Schema.UpsertAnswerRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Answer])
				}
				object upsert {
					def apply(locationsId :PlayApi, questionsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): upsert = new upsert(ws.url(BASE_URL + s"v1/locations/${locationsId}/questions/${questionsId}/answers:upsert").addQueryStringParameters("parent" -> parent.toString))
				}
				/** Deletes the answer written by the current user to a question. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					val scopes = Seq()
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
				}
				object delete {
					def apply(locationsId :PlayApi, questionsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/locations/${locationsId}/questions/${questionsId}/answers:delete").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
				}
			}
		}
	}
}
