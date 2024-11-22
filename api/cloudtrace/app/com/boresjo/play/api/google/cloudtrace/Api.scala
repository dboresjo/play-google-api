package com.boresjo.play.api.google.cloudtrace

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
		"""https://www.googleapis.com/auth/trace.append""" /* Write Trace data for a project or application */
	)

	private val BASE_URL = "https://cloudtrace.googleapis.com/"

	object projects {
		object traces {
			/** Batch writes new spans to new or existing traces. You cannot update existing spans. */
			class batchWrite(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/trace.append""")
				/** Perform the request */
				def withBatchWriteSpansRequest(body: Schema.BatchWriteSpansRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Empty])
			}
			object batchWrite {
				def apply(projectsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): batchWrite = new batchWrite(ws.url(BASE_URL + s"v2/projects/${projectsId}/traces:batchWrite").addQueryStringParameters("name" -> name.toString))
			}
			object spans {
				/** Creates a new span. */
				class createSpan(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/trace.append""")
					/** Perform the request */
					def withSpan(body: Schema.Span) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Span])
				}
				object createSpan {
					def apply(projectsId :PlayApi, tracesId :PlayApi, spansId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): createSpan = new createSpan(ws.url(BASE_URL + s"v2/projects/${projectsId}/traces/${tracesId}/spans/${spansId}").addQueryStringParameters("name" -> name.toString))
				}
			}
		}
	}
}
