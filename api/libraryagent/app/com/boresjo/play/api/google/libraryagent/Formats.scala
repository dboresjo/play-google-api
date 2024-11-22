package com.boresjo.play.api.google.libraryagent

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Formats {
	given fmtGoogleExampleLibraryagentV1Shelf: Format[Schema.GoogleExampleLibraryagentV1Shelf] = Json.format[Schema.GoogleExampleLibraryagentV1Shelf]
	given fmtGoogleExampleLibraryagentV1ListBooksResponse: Format[Schema.GoogleExampleLibraryagentV1ListBooksResponse] = Json.format[Schema.GoogleExampleLibraryagentV1ListBooksResponse]
	given fmtGoogleExampleLibraryagentV1Book: Format[Schema.GoogleExampleLibraryagentV1Book] = Json.format[Schema.GoogleExampleLibraryagentV1Book]
	given fmtGoogleExampleLibraryagentV1ListShelvesResponse: Format[Schema.GoogleExampleLibraryagentV1ListShelvesResponse] = Json.format[Schema.GoogleExampleLibraryagentV1ListShelvesResponse]
}
