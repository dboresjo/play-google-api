package com.boresjo.play.api.google.discovery

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

	private val BASE_URL = "https://www.googleapis.com/discovery/v1/"

	object apis {
		/** Retrieve the list of APIs supported at this endpoint. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.DirectoryList]) {
			val scopes = Seq()
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.DirectoryList])
		}
		object list {
			def apply(name: String, preferred: Boolean)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"apis").addQueryStringParameters("name" -> name.toString, "preferred" -> preferred.toString))
			given Conversion[list, Future[Schema.DirectoryList]] = (fun: list) => fun.apply()
		}
		/** Retrieve the description of a particular version of an api. */
		class getRest(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.RestDescription]) {
			val scopes = Seq()
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.RestDescription])
		}
		object getRest {
			def apply(api: String, version: String)(using signer: RequestSigner, ec: ExecutionContext): getRest = new getRest(ws.url(BASE_URL + s"apis/${api}/${version}/rest").addQueryStringParameters())
			given Conversion[getRest, Future[Schema.RestDescription]] = (fun: getRest) => fun.apply()
		}
	}
}
