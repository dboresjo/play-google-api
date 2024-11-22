package com.boresjo.play.api.google.cloudshell

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

	private val BASE_URL = "https://cloudshell.googleapis.com/"

	object operations {
		/** Lists operations that match the specified filter in the request. If the server doesn't support this method, it returns `UNIMPLEMENTED`. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListOperationsResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListOperationsResponse])
		}
		object list {
			def apply(name: String, filter: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/operations").addQueryStringParameters("name" -> name.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
			given Conversion[list, Future[Schema.ListOperationsResponse]] = (fun: list) => fun.apply()
		}
		/** Gets the latest state of a long-running operation. Clients can use this method to poll the operation result at intervals as recommended by the API service. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Operation])
		}
		object get {
			def apply(operationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
			given Conversion[get, Future[Schema.Operation]] = (fun: get) => fun.apply()
		}
		/** Deletes a long-running operation. This method indicates that the client is no longer interested in the operation result. It does not cancel the operation. If the server doesn't support this method, it returns `google.rpc.Code.UNIMPLEMENTED`. */
		class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
		}
		object delete {
			def apply(operationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
			given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
		}
		/** Starts asynchronous cancellation on a long-running operation. The server makes a best effort to cancel the operation, but success is not guaranteed. If the server doesn't support this method, it returns `google.rpc.Code.UNIMPLEMENTED`. Clients can use Operations.GetOperation or other methods to check whether the cancellation succeeded or whether the operation completed despite cancellation. On successful cancellation, the operation is not deleted; instead, it becomes an operation with an Operation.error value with a google.rpc.Status.code of 1, corresponding to `Code.CANCELLED`. */
		class cancel(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def withCancelOperationRequest(body: Schema.CancelOperationRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Empty])
		}
		object cancel {
			def apply(operationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): cancel = new cancel(ws.url(BASE_URL + s"v1/operations/${operationsId}:cancel").addQueryStringParameters("name" -> name.toString))
		}
	}
	object users {
		object environments {
			/** Sends OAuth credentials to a running environment on behalf of a user. When this completes, the environment will be authorized to run various Google Cloud command line tools without requiring the user to manually authenticate. */
			class authorize(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withAuthorizeEnvironmentRequest(body: Schema.AuthorizeEnvironmentRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
			}
			object authorize {
				def apply(usersId :PlayApi, environmentsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): authorize = new authorize(ws.url(BASE_URL + s"v1/users/${usersId}/environments/${environmentsId}:authorize").addQueryStringParameters("name" -> name.toString))
			}
			/** Removes a public SSH key from an environment. Clients will no longer be able to connect to the environment using the corresponding private key. If a key with the same content is not present, this will error with NOT_FOUND. */
			class removePublicKey(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withRemovePublicKeyRequest(body: Schema.RemovePublicKeyRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
			}
			object removePublicKey {
				def apply(usersId :PlayApi, environmentsId :PlayApi, environment: String)(using signer: RequestSigner, ec: ExecutionContext): removePublicKey = new removePublicKey(ws.url(BASE_URL + s"v1/users/${usersId}/environments/${environmentsId}:removePublicKey").addQueryStringParameters("environment" -> environment.toString))
			}
			/** Gets an environment. Returns NOT_FOUND if the environment does not exist. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Environment]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Environment])
			}
			object get {
				def apply(usersId :PlayApi, environmentsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/users/${usersId}/environments/${environmentsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.Environment]] = (fun: get) => fun.apply()
			}
			/** Adds a public SSH key to an environment, allowing clients with the corresponding private key to connect to that environment via SSH. If a key with the same content already exists, this will error with ALREADY_EXISTS. */
			class addPublicKey(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withAddPublicKeyRequest(body: Schema.AddPublicKeyRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
			}
			object addPublicKey {
				def apply(usersId :PlayApi, environmentsId :PlayApi, environment: String)(using signer: RequestSigner, ec: ExecutionContext): addPublicKey = new addPublicKey(ws.url(BASE_URL + s"v1/users/${usersId}/environments/${environmentsId}:addPublicKey").addQueryStringParameters("environment" -> environment.toString))
			}
			/** Starts an existing environment, allowing clients to connect to it. The returned operation will contain an instance of StartEnvironmentMetadata in its metadata field. Users can wait for the environment to start by polling this operation via GetOperation. Once the environment has finished starting and is ready to accept connections, the operation will contain a StartEnvironmentResponse in its response field. */
			class start(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withStartEnvironmentRequest(body: Schema.StartEnvironmentRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
			}
			object start {
				def apply(usersId :PlayApi, environmentsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): start = new start(ws.url(BASE_URL + s"v1/users/${usersId}/environments/${environmentsId}:start").addQueryStringParameters("name" -> name.toString))
			}
		}
	}
}
