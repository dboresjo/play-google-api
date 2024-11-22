package com.boresjo.play.api.google.groupssettings

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
		"""https://www.googleapis.com/auth/apps.groups.settings""" /* View and manage the settings of a G Suite group */
	)

	private val BASE_URL = "https://www.googleapis.com/groups/v1/groups/"

	object groups {
		/** Gets one resource by id. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Groups]) {
			val scopes = Seq("""https://www.googleapis.com/auth/apps.groups.settings""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Groups])
		}
		object get {
			def apply(groupUniqueId: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"${groupUniqueId}").addQueryStringParameters())
			given Conversion[get, Future[Schema.Groups]] = (fun: get) => fun.apply()
		}
		/** Updates an existing resource. This method supports patch semantics. */
		class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/apps.groups.settings""")
			/** Perform the request */
			def withGroups(body: Schema.Groups) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Groups])
		}
		object patch {
			def apply(groupUniqueId: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"${groupUniqueId}").addQueryStringParameters())
		}
		/** Updates an existing resource. */
		class update(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/apps.groups.settings""")
			/** Perform the request */
			def withGroups(body: Schema.Groups) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.Groups])
		}
		object update {
			def apply(groupUniqueId: String)(using signer: RequestSigner, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"${groupUniqueId}").addQueryStringParameters())
		}
	}
}
