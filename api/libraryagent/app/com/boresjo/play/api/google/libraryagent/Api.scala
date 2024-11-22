package com.boresjo.play.api.google.libraryagent

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://libraryagent.googleapis.com/"

	object shelves {
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleExampleLibraryagentV1Shelf]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleExampleLibraryagentV1Shelf])
		}
		object get {
			def apply(shelvesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/shelves/${shelvesId}").addQueryStringParameters("name" -> name.toString))
			given Conversion[get, Future[Schema.GoogleExampleLibraryagentV1Shelf]] = (fun: get) => fun.apply()
		}
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleExampleLibraryagentV1ListShelvesResponse]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleExampleLibraryagentV1ListShelvesResponse])
		}
		object list {
			def apply(pageToken: String, pageSize: Int)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/shelves").addQueryStringParameters("pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString))
			given Conversion[list, Future[Schema.GoogleExampleLibraryagentV1ListShelvesResponse]] = (fun: list) => fun.apply()
		}
		object books {
			class `return`(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleExampleLibraryagentV1Book]) {
				def apply() = auth.exec(req,_.execute("POST")).map(_.json.as[Schema.GoogleExampleLibraryagentV1Book])
			}
			object `return` {
				def apply(shelvesId :PlayApi, booksId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): `return` = new `return`(ws.url(BASE_URL + s"v1/shelves/${shelvesId}/books/${booksId}:return").addQueryStringParameters("name" -> name.toString))
				given Conversion[`return`, Future[Schema.GoogleExampleLibraryagentV1Book]] = (fun: `return`) => fun.apply()
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleExampleLibraryagentV1Book]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleExampleLibraryagentV1Book])
			}
			object get {
				def apply(shelvesId :PlayApi, booksId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/shelves/${shelvesId}/books/${booksId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.GoogleExampleLibraryagentV1Book]] = (fun: get) => fun.apply()
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleExampleLibraryagentV1ListBooksResponse]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleExampleLibraryagentV1ListBooksResponse])
			}
			object list {
				def apply(shelvesId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/shelves/${shelvesId}/books").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.GoogleExampleLibraryagentV1ListBooksResponse]] = (fun: list) => fun.apply()
			}
			class borrow(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleExampleLibraryagentV1Book]) {
				def apply() = auth.exec(req,_.execute("POST")).map(_.json.as[Schema.GoogleExampleLibraryagentV1Book])
			}
			object borrow {
				def apply(shelvesId :PlayApi, booksId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): borrow = new borrow(ws.url(BASE_URL + s"v1/shelves/${shelvesId}/books/${booksId}:borrow").addQueryStringParameters("name" -> name.toString))
				given Conversion[borrow, Future[Schema.GoogleExampleLibraryagentV1Book]] = (fun: borrow) => fun.apply()
			}
		}
	}
}
