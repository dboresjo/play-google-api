package com.boresjo.play.api.google.discovery

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://www.googleapis.com/discovery/v1/"

	object apis {
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.DirectoryList]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.DirectoryList])
		}
		object list {
			def apply(name: String, preferred: Boolean)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"apis")).addQueryStringParameters("name" -> name.toString, "preferred" -> preferred.toString))
			given Conversion[list, Future[Schema.DirectoryList]] = (fun: list) => fun.apply()
		}
		class getRest(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.RestDescription]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.RestDescription])
		}
		object getRest {
			def apply(api: String, version: String)(using auth: AuthToken, ec: ExecutionContext): getRest = new getRest(auth(ws.url(BASE_URL + s"apis/${api}/${version}/rest")).addQueryStringParameters())
			given Conversion[getRest, Future[Schema.RestDescription]] = (fun: getRest) => fun.apply()
		}
	}
}
