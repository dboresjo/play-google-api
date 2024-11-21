package com.boresjo.play.api.google.mybusinessqanda

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Formats {
	given fmtListQuestionsResponse: Format[Schema.ListQuestionsResponse] = Json.format[Schema.ListQuestionsResponse]
	given fmtQuestion: Format[Schema.Question] = Json.format[Schema.Question]
	given fmtAuthor: Format[Schema.Author] = Json.format[Schema.Author]
	given fmtAnswer: Format[Schema.Answer] = Json.format[Schema.Answer]
	given fmtAuthorTypeEnum: Format[Schema.Author.TypeEnum] = JsonEnumFormat[Schema.Author.TypeEnum]
	given fmtListAnswersResponse: Format[Schema.ListAnswersResponse] = Json.format[Schema.ListAnswersResponse]
	given fmtEmpty: Format[Schema.Empty] = Json.format[Schema.Empty]
	given fmtUpsertAnswerRequest: Format[Schema.UpsertAnswerRequest] = Json.format[Schema.UpsertAnswerRequest]
}
