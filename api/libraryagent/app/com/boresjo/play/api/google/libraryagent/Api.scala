package com.boresjo.play.api.google.libraryagent

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
		"""https://www.googleapis.com/auth/cloud-platform""" /* See, edit, configure, and delete your Google Cloud data and see the email address for your Google Account. */
	)

	private val BASE_URL = "https://libraryagent.googleapis.com/"

	object shelves {
		/** Gets a shelf. Returns NOT_FOUND if the shelf does not exist. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleExampleLibraryagentV1Shelf]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleExampleLibraryagentV1Shelf])
		}
		object get {
			def apply(shelvesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/shelves/${shelvesId}").addQueryStringParameters("name" -> name.toString))
			given Conversion[get, Future[Schema.GoogleExampleLibraryagentV1Shelf]] = (fun: get) => fun.apply()
		}
		/** Lists shelves. The order is unspecified but deterministic. Newly created shelves will not necessarily be added to the end of this list. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleExampleLibraryagentV1ListShelvesResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleExampleLibraryagentV1ListShelvesResponse])
		}
		object list {
			def apply(pageToken: String, pageSize: Int)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/shelves").addQueryStringParameters("pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString))
			given Conversion[list, Future[Schema.GoogleExampleLibraryagentV1ListShelvesResponse]] = (fun: list) => fun.apply()
		}
		object books {
			/** Return a book to the library. Returns the book if it is returned to the library successfully. Returns error if the book does not belong to the library or the users didn't borrow before. */
			class `return`(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleExampleLibraryagentV1Book]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("POST")).map(_.json.as[Schema.GoogleExampleLibraryagentV1Book])
			}
			object `return` {
				def apply(shelvesId :PlayApi, booksId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): `return` = new `return`(ws.url(BASE_URL + s"v1/shelves/${shelvesId}/books/${booksId}:return").addQueryStringParameters("name" -> name.toString))
				given Conversion[`return`, Future[Schema.GoogleExampleLibraryagentV1Book]] = (fun: `return`) => fun.apply()
			}
			/** Gets a book. Returns NOT_FOUND if the book does not exist. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleExampleLibraryagentV1Book]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleExampleLibraryagentV1Book])
			}
			object get {
				def apply(shelvesId :PlayApi, booksId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/shelves/${shelvesId}/books/${booksId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.GoogleExampleLibraryagentV1Book]] = (fun: get) => fun.apply()
			}
			/** Lists books in a shelf. The order is unspecified but deterministic. Newly created books will not necessarily be added to the end of this list. Returns NOT_FOUND if the shelf does not exist. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleExampleLibraryagentV1ListBooksResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleExampleLibraryagentV1ListBooksResponse])
			}
			object list {
				def apply(shelvesId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/shelves/${shelvesId}/books").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.GoogleExampleLibraryagentV1ListBooksResponse]] = (fun: list) => fun.apply()
			}
			/** Borrow a book from the library. Returns the book if it is borrowed successfully. Returns NOT_FOUND if the book does not exist in the library. Returns quota exceeded error if the amount of books borrowed exceeds allocation quota in any dimensions. */
			class borrow(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleExampleLibraryagentV1Book]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("POST")).map(_.json.as[Schema.GoogleExampleLibraryagentV1Book])
			}
			object borrow {
				def apply(shelvesId :PlayApi, booksId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): borrow = new borrow(ws.url(BASE_URL + s"v1/shelves/${shelvesId}/books/${booksId}:borrow").addQueryStringParameters("name" -> name.toString))
				given Conversion[borrow, Future[Schema.GoogleExampleLibraryagentV1Book]] = (fun: borrow) => fun.apply()
			}
		}
	}
}
