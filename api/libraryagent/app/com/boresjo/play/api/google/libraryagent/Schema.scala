package com.boresjo.play.api.google.libraryagent

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	case class GoogleExampleLibraryagentV1Shelf(
	  /** The theme of the shelf */
		theme: Option[String] = None,
	  /** Output only. The resource name of the shelf. Shelf names have the form `shelves/{shelf_id}`. The name is ignored when creating a shelf. */
		name: Option[String] = None
	)
	
	case class GoogleExampleLibraryagentV1ListBooksResponse(
	  /** The list of books. */
		books: Option[List[Schema.GoogleExampleLibraryagentV1Book]] = None,
	  /** A token to retrieve next page of results. Pass this value in the ListBooksRequest.page_token field in the subsequent call to `ListBooks` method to retrieve the next page of results. */
		nextPageToken: Option[String] = None
	)
	
	case class GoogleExampleLibraryagentV1ListShelvesResponse(
	  /** The list of shelves. */
		shelves: Option[List[Schema.GoogleExampleLibraryagentV1Shelf]] = None,
	  /** A token to retrieve next page of results. Pass this value in the ListShelvesRequest.page_token field in the subsequent call to `ListShelves` method to retrieve the next page of results. */
		nextPageToken: Option[String] = None
	)
	
	case class GoogleExampleLibraryagentV1Book(
	  /** Value indicating whether the book has been read. */
		read: Option[Boolean] = None,
	  /** The title of the book. */
		title: Option[String] = None,
	  /** The name of the book author. */
		author: Option[String] = None,
	  /** The resource name of the book. Book names have the form `shelves/{shelf_id}/books/{book_id}`. The name is ignored when creating a book. */
		name: Option[String] = None
	)
}
