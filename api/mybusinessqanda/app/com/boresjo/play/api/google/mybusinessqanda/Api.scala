package com.boresjo.play.api.google.mybusinessqanda

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://mybusinessqanda.googleapis.com/"

	object locations {
		object questions {
			class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withQuestion(body: Schema.Question) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Question])
			}
			object create {
				def apply(locationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/locations/${locationsId}/questions").addQueryStringParameters("parent" -> parent.toString))
			}
			class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
				def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
			}
			object delete {
				def apply(locationsId :PlayApi, questionsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/locations/${locationsId}/questions/${questionsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
			}
			class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withQuestion(body: Schema.Question) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Question])
			}
			object patch {
				def apply(locationsId :PlayApi, questionsId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/locations/${locationsId}/questions/${questionsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListQuestionsResponse]) {
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
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListQuestionsResponse])
			}
			object list {
				def apply(locationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/locations/${locationsId}/questions").addQueryStringParameters("parent" -> parent.toString))
				given Conversion[list, Future[Schema.ListQuestionsResponse]] = (fun: list) => fun.apply()
			}
			object answers {
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListAnswersResponse]) {
					/** Optional. How many answers to fetch per page. The default and maximum `page_size` values are 10.<br>Format: int32 */
					def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
					/** Optional. If specified, the next page of answers is retrieved. */
					def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
					/** Optional. The order to return the answers. Valid options include 'update_time desc' and 'upvote_count desc', which will return the answers sorted descendingly by the requested field. The default sort order is 'update_time desc'. */
					def withOrderBy(orderBy: String) = new list(req.addQueryStringParameters("orderBy" -> orderBy.toString))
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListAnswersResponse])
				}
				object list {
					def apply(locationsId :PlayApi, questionsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/locations/${locationsId}/questions/${questionsId}/answers").addQueryStringParameters("parent" -> parent.toString))
					given Conversion[list, Future[Schema.ListAnswersResponse]] = (fun: list) => fun.apply()
				}
				class upsert(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withUpsertAnswerRequest(body: Schema.UpsertAnswerRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Answer])
				}
				object upsert {
					def apply(locationsId :PlayApi, questionsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): upsert = new upsert(ws.url(BASE_URL + s"v1/locations/${locationsId}/questions/${questionsId}/answers:upsert").addQueryStringParameters("parent" -> parent.toString))
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
				}
				object delete {
					def apply(locationsId :PlayApi, questionsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/locations/${locationsId}/questions/${questionsId}/answers:delete").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
				}
			}
		}
	}
}
