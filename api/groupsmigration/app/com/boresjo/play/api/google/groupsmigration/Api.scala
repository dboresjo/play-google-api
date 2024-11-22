package com.boresjo.play.api.google.groupsmigration

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
		"""https://www.googleapis.com/auth/apps.groups.migration""" /* Upload messages to any Google group in your domain */
	)

	private val BASE_URL = "https://groupsmigration.googleapis.com/"

	object archive {
		/** Inserts a new mail into the archive of the Google group. */
		class insert(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Groups]) {
			val scopes = Seq("""https://www.googleapis.com/auth/apps.groups.migration""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("POST")).map(_.json.as[Schema.Groups])
		}
		object insert {
			def apply(groupId: String)(using signer: RequestSigner, ec: ExecutionContext): insert = new insert(ws.url(BASE_URL + s"groups/v1/groups/${groupId}/archive").addQueryStringParameters())
			given Conversion[insert, Future[Schema.Groups]] = (fun: insert) => fun.apply()
		}
	}
}
