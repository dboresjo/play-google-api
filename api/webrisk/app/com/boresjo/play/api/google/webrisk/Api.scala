package com.boresjo.play.api.google.webrisk

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

	private val BASE_URL = "https://webrisk.googleapis.com/"

	object threatLists {
		/** Gets the most recent threat list diffs. These diffs should be applied to a local database of hashes to keep it up-to-date. If the local database is empty or excessively out-of-date, a complete snapshot of the database will be returned. This Method only updates a single ThreatList at a time. To update multiple ThreatList databases, this method needs to be called once for each list. */
		class computeDiff(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudWebriskV1ComputeThreatListDiffResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudWebriskV1ComputeThreatListDiffResponse])
		}
		object computeDiff {
			def apply(constraintsSupportedCompressions: String, constraintsMaxDiffEntries: Int, threatType: String, versionToken: String, constraintsMaxDatabaseEntries: Int)(using signer: RequestSigner, ec: ExecutionContext): computeDiff = new computeDiff(ws.url(BASE_URL + s"v1/threatLists:computeDiff").addQueryStringParameters("constraints.supportedCompressions" -> constraintsSupportedCompressions.toString, "constraints.maxDiffEntries" -> constraintsMaxDiffEntries.toString, "threatType" -> threatType.toString, "versionToken" -> versionToken.toString, "constraints.maxDatabaseEntries" -> constraintsMaxDatabaseEntries.toString))
			given Conversion[computeDiff, Future[Schema.GoogleCloudWebriskV1ComputeThreatListDiffResponse]] = (fun: computeDiff) => fun.apply()
		}
	}
	object hashes {
		/** Gets the full hashes that match the requested hash prefix. This is used after a hash prefix is looked up in a threatList and there is a match. The client side threatList only holds partial hashes so the client must query this method to determine if there is a full hash match of a threat. */
		class search(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudWebriskV1SearchHashesResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudWebriskV1SearchHashesResponse])
		}
		object search {
			def apply(threatTypes: String, hashPrefix: String)(using signer: RequestSigner, ec: ExecutionContext): search = new search(ws.url(BASE_URL + s"v1/hashes:search").addQueryStringParameters("threatTypes" -> threatTypes.toString, "hashPrefix" -> hashPrefix.toString))
			given Conversion[search, Future[Schema.GoogleCloudWebriskV1SearchHashesResponse]] = (fun: search) => fun.apply()
		}
	}
	object uris {
		/** This method is used to check whether a URI is on a given threatList. Multiple threatLists may be searched in a single query. The response will list all requested threatLists the URI was found to match. If the URI is not found on any of the requested ThreatList an empty response will be returned. */
		class search(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudWebriskV1SearchUrisResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudWebriskV1SearchUrisResponse])
		}
		object search {
			def apply(threatTypes: String, uri: String)(using signer: RequestSigner, ec: ExecutionContext): search = new search(ws.url(BASE_URL + s"v1/uris:search").addQueryStringParameters("threatTypes" -> threatTypes.toString, "uri" -> uri.toString))
			given Conversion[search, Future[Schema.GoogleCloudWebriskV1SearchUrisResponse]] = (fun: search) => fun.apply()
		}
	}
	object projects {
		object submissions {
			/** Creates a Submission of a URI suspected of containing phishing content to be reviewed. If the result verifies the existence of malicious phishing content, the site will be added to the [Google's Social Engineering lists](https://support.google.com/webmasters/answer/6350487/) in order to protect users that could get exposed to this threat in the future. Only allowlisted projects can use this method during Early Access. Please reach out to Sales or your customer engineer to obtain access. */
			class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withGoogleCloudWebriskV1Submission(body: Schema.GoogleCloudWebriskV1Submission) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudWebriskV1Submission])
			}
			object create {
				def apply(projectsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/submissions").addQueryStringParameters("parent" -> parent.toString))
			}
		}
		object operations {
			/** Deletes a long-running operation. This method indicates that the client is no longer interested in the operation result. It does not cancel the operation. If the server doesn't support this method, it returns `google.rpc.Code.UNIMPLEMENTED`. */
			class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
				val scopes = Seq()
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
			}
			object delete {
				def apply(projectsId :PlayApi, operationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
			}
			/** Starts asynchronous cancellation on a long-running operation. The server makes a best effort to cancel the operation, but success is not guaranteed. If the server doesn't support this method, it returns `google.rpc.Code.UNIMPLEMENTED`. Clients can use Operations.GetOperation or other methods to check whether the cancellation succeeded or whether the operation completed despite cancellation. On successful cancellation, the operation is not deleted; instead, it becomes an operation with an Operation.error value with a google.rpc.Status.code of 1, corresponding to `Code.CANCELLED`. */
			class cancel(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq()
				/** Perform the request */
				def withGoogleLongrunningCancelOperationRequest(body: Schema.GoogleLongrunningCancelOperationRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleProtobufEmpty])
			}
			object cancel {
				def apply(projectsId :PlayApi, operationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): cancel = new cancel(ws.url(BASE_URL + s"v1/projects/${projectsId}/operations/${operationsId}:cancel").addQueryStringParameters("name" -> name.toString))
			}
			/** Gets the latest state of a long-running operation. Clients can use this method to poll the operation result at intervals as recommended by the API service. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningOperation]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleLongrunningOperation])
			}
			object get {
				def apply(projectsId :PlayApi, operationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.GoogleLongrunningOperation]] = (fun: get) => fun.apply()
			}
			/** Lists operations that match the specified filter in the request. If the server doesn't support this method, it returns `UNIMPLEMENTED`. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningListOperationsResponse]) {
				val scopes = Seq()
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleLongrunningListOperationsResponse])
			}
			object list {
				def apply(projectsId :PlayApi, pageToken: String, pageSize: Int, filter: String, name: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/operations").addQueryStringParameters("pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString, "filter" -> filter.toString, "name" -> name.toString))
				given Conversion[list, Future[Schema.GoogleLongrunningListOperationsResponse]] = (fun: list) => fun.apply()
			}
		}
	}
}
