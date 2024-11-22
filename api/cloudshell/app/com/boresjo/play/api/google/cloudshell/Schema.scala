package com.boresjo.play.api.google.cloudshell

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	case class ListOperationsResponse(
	  /** A list of operations that matches the specified filter in the request. */
		operations: Option[List[Schema.Operation]] = None,
	  /** The standard List next-page token. */
		nextPageToken: Option[String] = None
	)
	
	case class Operation(
	  /** The server-assigned name, which is only unique within the same service that originally returns it. If you use the default HTTP mapping, the `name` should be a resource name ending with `operations/{unique_id}`. */
		name: Option[String] = None,
	  /** Service-specific metadata associated with the operation. It typically contains progress information and common metadata such as create time. Some services might not provide such metadata. Any method that returns a long-running operation should document the metadata type, if any. */
		metadata: Option[Map[String, JsValue]] = None,
	  /** If the value is `false`, it means the operation is still in progress. If `true`, the operation is completed, and either `error` or `response` is available. */
		done: Option[Boolean] = None,
	  /** The error result of the operation in case of failure or cancellation. */
		error: Option[Schema.Status] = None,
	  /** The normal, successful response of the operation. If the original method returns no data on success, such as `Delete`, the response is `google.protobuf.Empty`. If the original method is standard `Get`/`Create`/`Update`, the response should be the resource. For other methods, the response should have the type `XxxResponse`, where `Xxx` is the original method name. For example, if the original method name is `TakeSnapshot()`, the inferred response type is `TakeSnapshotResponse`. */
		response: Option[Map[String, JsValue]] = None
	)
	
	case class Status(
	  /** The status code, which should be an enum value of google.rpc.Code. */
		code: Option[Int] = None,
	  /** A developer-facing error message, which should be in English. Any user-facing error message should be localized and sent in the google.rpc.Status.details field, or localized by the client. */
		message: Option[String] = None,
	  /** A list of messages that carry the error details. There is a common set of message types for APIs to use. */
		details: Option[List[Map[String, JsValue]]] = None
	)
	
	case class Empty(
	
	)
	
	case class CancelOperationRequest(
	
	)
	
	object Environment {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, SUSPENDED, PENDING, RUNNING, DELETING }
	}
	case class Environment(
	  /** Immutable. Full name of this resource, in the format `users/{owner_email}/environments/{environment_id}`. `{owner_email}` is the email address of the user to whom this environment belongs, and `{environment_id}` is the identifier of this environment. For example, `users/someone@example.com/environments/default`. */
		name: Option[String] = None,
	  /** Output only. The environment's identifier, unique among the user's environments. */
		id: Option[String] = None,
	  /** Required. Immutable. Full path to the Docker image used to run this environment, e.g. "gcr.io/dev-con/cloud-devshell:latest". */
		dockerImage: Option[String] = None,
	  /** Output only. Current execution state of this environment. */
		state: Option[Schema.Environment.StateEnum] = None,
	  /** Output only. Host to which clients can connect to initiate HTTPS or WSS connections with the environment. */
		webHost: Option[String] = None,
	  /** Output only. Username that clients should use when initiating SSH sessions with the environment. */
		sshUsername: Option[String] = None,
	  /** Output only. Host to which clients can connect to initiate SSH sessions with the environment. */
		sshHost: Option[String] = None,
	  /** Output only. Port to which clients can connect to initiate SSH sessions with the environment. */
		sshPort: Option[Int] = None,
	  /** Output only. Public keys associated with the environment. Clients can connect to this environment via SSH only if they possess a private key corresponding to at least one of these public keys. Keys can be added to or removed from the environment using the AddPublicKey and RemovePublicKey methods. */
		publicKeys: Option[List[String]] = None
	)
	
	case class StartEnvironmentRequest(
	  /** The initial access token passed to the environment. If this is present and valid, the environment will be pre-authenticated with gcloud so that the user can run gcloud commands in Cloud Shell without having to log in. This code can be updated later by calling AuthorizeEnvironment. */
		accessToken: Option[String] = None,
	  /** Public keys that should be added to the environment before it is started. */
		publicKeys: Option[List[String]] = None
	)
	
	case class AuthorizeEnvironmentRequest(
	  /** The OAuth access token that should be sent to the environment. */
		accessToken: Option[String] = None,
	  /** The OAuth ID token that should be sent to the environment. */
		idToken: Option[String] = None,
	  /** The time when the credentials expire. If not set, defaults to one hour from when the server received the request. */
		expireTime: Option[String] = None
	)
	
	case class AddPublicKeyRequest(
	  /** Key that should be added to the environment. Supported formats are `ssh-dss` (see RFC4253), `ssh-rsa` (see RFC4253), `ecdsa-sha2-nistp256` (see RFC5656), `ecdsa-sha2-nistp384` (see RFC5656) and `ecdsa-sha2-nistp521` (see RFC5656). It should be structured as <format> <content>, where <content> part is encoded with Base64. */
		key: Option[String] = None
	)
	
	case class RemovePublicKeyRequest(
	  /** Key that should be removed from the environment. */
		key: Option[String] = None
	)
	
	case class AddPublicKeyMetadata(
	
	)
	
	case class AddPublicKeyResponse(
	  /** Key that was added to the environment. */
		key: Option[String] = None
	)
	
	case class AuthorizeEnvironmentMetadata(
	
	)
	
	case class AuthorizeEnvironmentResponse(
	
	)
	
	case class CreateEnvironmentMetadata(
	
	)
	
	case class DeleteEnvironmentMetadata(
	
	)
	
	case class RemovePublicKeyMetadata(
	
	)
	
	case class RemovePublicKeyResponse(
	
	)
	
	object StartEnvironmentMetadata {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, STARTING, UNARCHIVING_DISK, AWAITING_COMPUTE_RESOURCES, FINISHED }
	}
	case class StartEnvironmentMetadata(
	  /** Current state of the environment being started. */
		state: Option[Schema.StartEnvironmentMetadata.StateEnum] = None
	)
	
	case class StartEnvironmentResponse(
	  /** Environment that was started. */
		environment: Option[Schema.Environment] = None
	)
}
