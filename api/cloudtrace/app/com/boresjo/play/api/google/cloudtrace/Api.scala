package com.boresjo.play.api.google.cloudtrace

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://cloudtrace.googleapis.com/"

	object projects {
		object traces {
			class batchWrite(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withBatchWriteSpansRequest(body: Schema.BatchWriteSpansRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Empty])
			}
			object batchWrite {
				def apply(projectsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): batchWrite = new batchWrite(ws.url(BASE_URL + s"v2/projects/${projectsId}/traces:batchWrite").addQueryStringParameters("name" -> name.toString))
			}
			object spans {
				class createSpan(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withSpan(body: Schema.Span) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Span])
				}
				object createSpan {
					def apply(projectsId :PlayApi, tracesId :PlayApi, spansId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): createSpan = new createSpan(ws.url(BASE_URL + s"v2/projects/${projectsId}/traces/${tracesId}/spans/${spansId}").addQueryStringParameters("name" -> name.toString))
				}
			}
		}
	}
}
