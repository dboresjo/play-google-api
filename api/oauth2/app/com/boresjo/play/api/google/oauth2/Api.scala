package com.boresjo.play.api.google.oauth2

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
		"""openid""" /* Associate you with your personal info on Google */,
		"""https://www.googleapis.com/auth/userinfo.email""" /* See your primary Google Account email address */,
		"""https://www.googleapis.com/auth/userinfo.profile""" /* See your personal info, including any personal info you've made publicly available */
	)

	private val BASE_URL = "https://www.googleapis.com/"

	object userinfo {
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Userinfo]) {
			val scopes = Seq("""openid""", """https://www.googleapis.com/auth/userinfo.email""", """https://www.googleapis.com/auth/userinfo.profile""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Userinfo])
		}
		object get {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"oauth2/v2/userinfo").addQueryStringParameters())
			given Conversion[get, Future[Schema.Userinfo]] = (fun: get) => fun.apply()
		}
		object v2 {
			object me {
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Userinfo]) {
					val scopes = Seq("""openid""", """https://www.googleapis.com/auth/userinfo.email""", """https://www.googleapis.com/auth/userinfo.profile""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Userinfo])
				}
				object get {
					def apply()(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"userinfo/v2/me").addQueryStringParameters())
					given Conversion[get, Future[Schema.Userinfo]] = (fun: get) => fun.apply()
				}
			}
		}
	}
}
