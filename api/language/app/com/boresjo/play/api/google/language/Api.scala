package com.boresjo.play.api.google.language

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
		"""https://www.googleapis.com/auth/cloud-platform""" /* See, edit, configure, and delete your Google Cloud data and see the email address for your Google Account. */,
		"""https://www.googleapis.com/auth/cloud-language""" /* Apply machine learning models to reveal the structure and meaning of text */
	)

	private val BASE_URL = "https://language.googleapis.com/"

	object documents {
		/** Classifies a document into categories. */
		class classifyText(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-language""", """https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def withClassifyTextRequest(body: Schema.ClassifyTextRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.ClassifyTextResponse])
		}
		object classifyText {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): classifyText = new classifyText(ws.url(BASE_URL + s"v2/documents:classifyText").addQueryStringParameters())
		}
		/** Finds named entities (currently proper names and common nouns) in the text along with entity types, probability, mentions for each entity, and other properties. */
		class analyzeEntities(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-language""", """https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def withAnalyzeEntitiesRequest(body: Schema.AnalyzeEntitiesRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.AnalyzeEntitiesResponse])
		}
		object analyzeEntities {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): analyzeEntities = new analyzeEntities(ws.url(BASE_URL + s"v2/documents:analyzeEntities").addQueryStringParameters())
		}
		/** A convenience method that provides all features in one call. */
		class annotateText(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-language""", """https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def withAnnotateTextRequest(body: Schema.AnnotateTextRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.AnnotateTextResponse])
		}
		object annotateText {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): annotateText = new annotateText(ws.url(BASE_URL + s"v2/documents:annotateText").addQueryStringParameters())
		}
		/** Analyzes the sentiment of the provided text. */
		class analyzeSentiment(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-language""", """https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def withAnalyzeSentimentRequest(body: Schema.AnalyzeSentimentRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.AnalyzeSentimentResponse])
		}
		object analyzeSentiment {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): analyzeSentiment = new analyzeSentiment(ws.url(BASE_URL + s"v2/documents:analyzeSentiment").addQueryStringParameters())
		}
		/** Moderates a document for harmful and sensitive categories. */
		class moderateText(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-language""", """https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def withModerateTextRequest(body: Schema.ModerateTextRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.ModerateTextResponse])
		}
		object moderateText {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): moderateText = new moderateText(ws.url(BASE_URL + s"v2/documents:moderateText").addQueryStringParameters())
		}
	}
}
