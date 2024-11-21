package com.boresjo.play.api.google.language

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://language.googleapis.com/"

	object documents {
		class classifyText(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withClassifyTextRequest(body: Schema.ClassifyTextRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.ClassifyTextResponse])
		}
		object classifyText {
			def apply()(using auth: AuthToken, ec: ExecutionContext): classifyText = new classifyText(auth(ws.url(BASE_URL + s"v2/documents:classifyText")).addQueryStringParameters())
		}
		class analyzeEntities(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withAnalyzeEntitiesRequest(body: Schema.AnalyzeEntitiesRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.AnalyzeEntitiesResponse])
		}
		object analyzeEntities {
			def apply()(using auth: AuthToken, ec: ExecutionContext): analyzeEntities = new analyzeEntities(auth(ws.url(BASE_URL + s"v2/documents:analyzeEntities")).addQueryStringParameters())
		}
		class annotateText(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withAnnotateTextRequest(body: Schema.AnnotateTextRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.AnnotateTextResponse])
		}
		object annotateText {
			def apply()(using auth: AuthToken, ec: ExecutionContext): annotateText = new annotateText(auth(ws.url(BASE_URL + s"v2/documents:annotateText")).addQueryStringParameters())
		}
		class analyzeSentiment(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withAnalyzeSentimentRequest(body: Schema.AnalyzeSentimentRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.AnalyzeSentimentResponse])
		}
		object analyzeSentiment {
			def apply()(using auth: AuthToken, ec: ExecutionContext): analyzeSentiment = new analyzeSentiment(auth(ws.url(BASE_URL + s"v2/documents:analyzeSentiment")).addQueryStringParameters())
		}
		class moderateText(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withModerateTextRequest(body: Schema.ModerateTextRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.ModerateTextResponse])
		}
		object moderateText {
			def apply()(using auth: AuthToken, ec: ExecutionContext): moderateText = new moderateText(auth(ws.url(BASE_URL + s"v2/documents:moderateText")).addQueryStringParameters())
		}
	}
}
