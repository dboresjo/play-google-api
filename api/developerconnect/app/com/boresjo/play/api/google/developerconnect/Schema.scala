package com.boresjo.play.api.google.developerconnect

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
	
	case class ListConnectionsResponse(
	  /** The list of Connection */
		connections: Option[List[Schema.Connection]] = None,
	  /** A token identifying a page of results the server should return. */
		nextPageToken: Option[String] = None,
	  /** Locations that could not be reached. */
		unreachable: Option[List[String]] = None
	)
	
	case class Connection(
	  /** Configuration for connections to github.com. */
		githubConfig: Option[Schema.GitHubConfig] = None,
	  /** Configuration for connections to an instance of GitHub Enterprise. */
		githubEnterpriseConfig: Option[Schema.GitHubEnterpriseConfig] = None,
	  /** Configuration for connections to gitlab.com. */
		gitlabConfig: Option[Schema.GitLabConfig] = None,
	  /** Configuration for connections to an instance of GitLab Enterprise. */
		gitlabEnterpriseConfig: Option[Schema.GitLabEnterpriseConfig] = None,
	  /** Identifier. The resource name of the connection, in the format `projects/{project}/locations/{location}/connections/{connection_id}`. */
		name: Option[String] = None,
	  /** Output only. [Output only] Create timestamp */
		createTime: Option[String] = None,
	  /** Output only. [Output only] Update timestamp */
		updateTime: Option[String] = None,
	  /** Output only. [Output only] Delete timestamp */
		deleteTime: Option[String] = None,
	  /** Optional. Labels as key value pairs */
		labels: Option[Map[String, String]] = None,
	  /** Output only. Installation state of the Connection. */
		installationState: Option[Schema.InstallationState] = None,
	  /** Optional. If disabled is set to true, functionality is disabled for this connection. Repository based API methods and webhooks processing for repositories in this connection will be disabled. */
		disabled: Option[Boolean] = None,
	  /** Output only. Set to true when the connection is being set up or updated in the background. */
		reconciling: Option[Boolean] = None,
	  /** Optional. Allows clients to store small amounts of arbitrary data. */
		annotations: Option[Map[String, String]] = None,
	  /** Optional. This checksum is computed by the server based on the value of other fields, and may be sent on update and delete requests to ensure the client has an up-to-date value before proceeding. */
		etag: Option[String] = None,
	  /** Output only. A system-assigned unique identifier for a the GitRepositoryLink. */
		uid: Option[String] = None,
	  /** Optional. The crypto key configuration. This field is used by the Customer-Managed Encryption Keys (CMEK) feature. */
		cryptoKeyConfig: Option[Schema.CryptoKeyConfig] = None
	)
	
	object GitHubConfig {
		enum GithubAppEnum extends Enum[GithubAppEnum] { case GIT_HUB_APP_UNSPECIFIED, DEVELOPER_CONNECT, FIREBASE }
	}
	case class GitHubConfig(
	  /** Required. Immutable. The GitHub Application that was installed to the GitHub user or organization. */
		githubApp: Option[Schema.GitHubConfig.GithubAppEnum] = None,
	  /** Optional. OAuth credential of the account that authorized the GitHub App. It is recommended to use a robot account instead of a human user account. The OAuth token must be tied to the GitHub App of this config. */
		authorizerCredential: Option[Schema.OAuthCredential] = None,
	  /** Optional. GitHub App installation id. */
		appInstallationId: Option[String] = None,
	  /** Output only. The URI to navigate to in order to manage the installation associated with this GitHubConfig. */
		installationUri: Option[String] = None
	)
	
	case class OAuthCredential(
	  /** Required. A SecretManager resource containing the OAuth token that authorizes the connection. Format: `projects/&#42;/secrets/&#42;/versions/&#42;`. */
		oauthTokenSecretVersion: Option[String] = None,
	  /** Output only. The username associated with this token. */
		username: Option[String] = None
	)
	
	case class GitHubEnterpriseConfig(
	  /** Required. The URI of the GitHub Enterprise host this connection is for. */
		hostUri: Option[String] = None,
	  /** Optional. ID of the GitHub App created from the manifest. */
		appId: Option[String] = None,
	  /** Output only. The URL-friendly name of the GitHub App. */
		appSlug: Option[String] = None,
	  /** Optional. SecretManager resource containing the private key of the GitHub App, formatted as `projects/&#42;/secrets/&#42;/versions/&#42;`. */
		privateKeySecretVersion: Option[String] = None,
	  /** Optional. SecretManager resource containing the webhook secret of the GitHub App, formatted as `projects/&#42;/secrets/&#42;/versions/&#42;`. */
		webhookSecretSecretVersion: Option[String] = None,
	  /** Optional. ID of the installation of the GitHub App. */
		appInstallationId: Option[String] = None,
	  /** Output only. The URI to navigate to in order to manage the installation associated with this GitHubEnterpriseConfig. */
		installationUri: Option[String] = None,
	  /** Optional. Configuration for using Service Directory to privately connect to a GitHub Enterprise server. This should only be set if the GitHub Enterprise server is hosted on-premises and not reachable by public internet. If this field is left empty, calls to the GitHub Enterprise server will be made over the public internet. */
		serviceDirectoryConfig: Option[Schema.ServiceDirectoryConfig] = None,
	  /** Output only. GitHub Enterprise version installed at the host_uri. */
		serverVersion: Option[String] = None,
	  /** Optional. SSL certificate to use for requests to GitHub Enterprise. */
		sslCaCertificate: Option[String] = None
	)
	
	case class ServiceDirectoryConfig(
	  /** Required. The Service Directory service name. Format: projects/{project}/locations/{location}/namespaces/{namespace}/services/{service}. */
		service: Option[String] = None
	)
	
	case class GitLabConfig(
	  /** Required. Immutable. SecretManager resource containing the webhook secret of a GitLab project, formatted as `projects/&#42;/secrets/&#42;/versions/&#42;`. This is used to validate webhooks. */
		webhookSecretSecretVersion: Option[String] = None,
	  /** Required. A GitLab personal access token with the minimum `read_api` scope access and a minimum role of `reporter`. The GitLab Projects visible to this Personal Access Token will control which Projects Developer Connect has access to. */
		readAuthorizerCredential: Option[Schema.UserCredential] = None,
	  /** Required. A GitLab personal access token with the minimum `api` scope access and a minimum role of `maintainer`. The GitLab Projects visible to this Personal Access Token will control which Projects Developer Connect has access to. */
		authorizerCredential: Option[Schema.UserCredential] = None
	)
	
	case class UserCredential(
	  /** Required. A SecretManager resource containing the user token that authorizes the Developer Connect connection. Format: `projects/&#42;/secrets/&#42;/versions/&#42;`. */
		userTokenSecretVersion: Option[String] = None,
	  /** Output only. The username associated with this token. */
		username: Option[String] = None
	)
	
	case class GitLabEnterpriseConfig(
	  /** Required. The URI of the GitLab Enterprise host this connection is for. */
		hostUri: Option[String] = None,
	  /** Required. Immutable. SecretManager resource containing the webhook secret of a GitLab project, formatted as `projects/&#42;/secrets/&#42;/versions/&#42;`. This is used to validate webhooks. */
		webhookSecretSecretVersion: Option[String] = None,
	  /** Required. A GitLab personal access token with the minimum `read_api` scope access and a minimum role of `reporter`. The GitLab Projects visible to this Personal Access Token will control which Projects Developer Connect has access to. */
		readAuthorizerCredential: Option[Schema.UserCredential] = None,
	  /** Required. A GitLab personal access token with the minimum `api` scope access and a minimum role of `maintainer`. The GitLab Projects visible to this Personal Access Token will control which Projects Developer Connect has access to. */
		authorizerCredential: Option[Schema.UserCredential] = None,
	  /** Optional. Configuration for using Service Directory to privately connect to a GitLab Enterprise instance. This should only be set if the GitLab Enterprise server is hosted on-premises and not reachable by public internet. If this field is left empty, calls to the GitLab Enterprise server will be made over the public internet. */
		serviceDirectoryConfig: Option[Schema.ServiceDirectoryConfig] = None,
	  /** Optional. SSL Certificate Authority certificate to use for requests to GitLab Enterprise instance. */
		sslCaCertificate: Option[String] = None,
	  /** Output only. Version of the GitLab Enterprise server running on the `host_uri`. */
		serverVersion: Option[String] = None
	)
	
	object InstallationState {
		enum StageEnum extends Enum[StageEnum] { case STAGE_UNSPECIFIED, PENDING_CREATE_APP, PENDING_USER_OAUTH, PENDING_INSTALL_APP, COMPLETE }
	}
	case class InstallationState(
	  /** Output only. Current step of the installation process. */
		stage: Option[Schema.InstallationState.StageEnum] = None,
	  /** Output only. Message of what the user should do next to continue the installation. Empty string if the installation is already complete. */
		message: Option[String] = None,
	  /** Output only. Link to follow for next action. Empty string if the installation is already complete. */
		actionUri: Option[String] = None
	)
	
	case class CryptoKeyConfig(
	  /** Required. The name of the key which is used to encrypt/decrypt customer data. For key in Cloud KMS, the key should be in the format of `projects/&#42;/locations/&#42;/keyRings/&#42;/cryptoKeys/&#42;`. */
		keyReference: Option[String] = None
	)
	
	case class GitRepositoryLink(
	  /** Identifier. Resource name of the repository, in the format `projects/&#42;/locations/&#42;/connections/&#42;/gitRepositoryLinks/&#42;`. */
		name: Option[String] = None,
	  /** Required. Git Clone URI. */
		cloneUri: Option[String] = None,
	  /** Output only. [Output only] Create timestamp */
		createTime: Option[String] = None,
	  /** Output only. [Output only] Update timestamp */
		updateTime: Option[String] = None,
	  /** Output only. [Output only] Delete timestamp */
		deleteTime: Option[String] = None,
	  /** Optional. Labels as key value pairs */
		labels: Option[Map[String, String]] = None,
	  /** Optional. This checksum is computed by the server based on the value of other fields, and may be sent on update and delete requests to ensure the client has an up-to-date value before proceeding. */
		etag: Option[String] = None,
	  /** Output only. Set to true when the connection is being set up or updated in the background. */
		reconciling: Option[Boolean] = None,
	  /** Optional. Allows clients to store small amounts of arbitrary data. */
		annotations: Option[Map[String, String]] = None,
	  /** Output only. A system-assigned unique identifier for a the GitRepositoryLink. */
		uid: Option[String] = None,
	  /** Output only. External ID of the webhook created for the repository. */
		webhookId: Option[String] = None
	)
	
	case class ListGitRepositoryLinksResponse(
	  /** The list of GitRepositoryLinks */
		gitRepositoryLinks: Option[List[Schema.GitRepositoryLink]] = None,
	  /** A token identifying a page of results the server should return. */
		nextPageToken: Option[String] = None,
	  /** Locations that could not be reached. */
		unreachable: Option[List[String]] = None
	)
	
	case class FetchReadWriteTokenRequest(
	
	)
	
	case class FetchReadWriteTokenResponse(
	  /** The token content. */
		token: Option[String] = None,
	  /** Expiration timestamp. Can be empty if unknown or non-expiring. */
		expirationTime: Option[String] = None,
	  /** The git_username to specify when making a git clone with the token. For example, for GitHub GitRepositoryLinks, this would be "x-access-token" */
		gitUsername: Option[String] = None
	)
	
	case class FetchReadTokenRequest(
	
	)
	
	case class FetchReadTokenResponse(
	  /** The token content. */
		token: Option[String] = None,
	  /** Expiration timestamp. Can be empty if unknown or non-expiring. */
		expirationTime: Option[String] = None,
	  /** The git_username to specify when making a git clone with the token. For example, for GitHub GitRepositoryLinks, this would be "x-access-token" */
		gitUsername: Option[String] = None
	)
	
	case class FetchLinkableGitRepositoriesResponse(
	  /** The git repositories that can be linked to the connection. */
		linkableGitRepositories: Option[List[Schema.LinkableGitRepository]] = None,
	  /** A token identifying a page of results the server should return. */
		nextPageToken: Option[String] = None
	)
	
	case class LinkableGitRepository(
	  /** The clone uri of the repository. */
		cloneUri: Option[String] = None
	)
	
	case class FetchGitHubInstallationsResponse(
	  /** List of installations available to the OAuth user (for github.com) or all the installations (for GitHub enterprise). */
		installations: Option[List[Schema.Installation]] = None
	)
	
	case class Installation(
	  /** ID of the installation in GitHub. */
		id: Option[String] = None,
	  /** Name of the GitHub user or organization that owns this installation. */
		name: Option[String] = None,
	  /** Either "user" or "organization". */
		`type`: Option[String] = None
	)
	
	case class FetchGitRefsResponse(
	  /** Name of the refs fetched. */
		refNames: Option[List[String]] = None,
	  /** A token identifying a page of results the server should return. */
		nextPageToken: Option[String] = None
	)
	
	case class ProcessGitHubEnterpriseWebhookRequest(
	  /** Required. HTTP request body. */
		body: Option[Schema.HttpBody] = None
	)
	
	case class HttpBody(
	  /** The HTTP Content-Type header value specifying the content type of the body. */
		contentType: Option[String] = None,
	  /** The HTTP request/response body as raw binary. */
		data: Option[String] = None,
	  /** Application specific response metadata. Must be set in the first response for streaming APIs. */
		extensions: Option[List[Map[String, JsValue]]] = None
	)
	
	case class ProcessGitLabEnterpriseWebhookRequest(
	  /** Required. HTTP request body. */
		body: Option[Schema.HttpBody] = None
	)
	
	case class ProcessGitLabWebhookRequest(
	  /** Required. HTTP request body. */
		body: Option[Schema.HttpBody] = None
	)
	
	case class ListLocationsResponse(
	  /** A list of locations that matches the specified filter in the request. */
		locations: Option[List[Schema.Location]] = None,
	  /** The standard List next-page token. */
		nextPageToken: Option[String] = None
	)
	
	case class Location(
	  /** Resource name for the location, which may vary between implementations. For example: `"projects/example-project/locations/us-east1"` */
		name: Option[String] = None,
	  /** The canonical id for this location. For example: `"us-east1"`. */
		locationId: Option[String] = None,
	  /** The friendly name for this location, typically a nearby city name. For example, "Tokyo". */
		displayName: Option[String] = None,
	  /** Cross-service attributes for the location. For example {"cloud.googleapis.com/region": "us-east1"} */
		labels: Option[Map[String, String]] = None,
	  /** Service-specific metadata. For example the available capacity at the given location. */
		metadata: Option[Map[String, JsValue]] = None
	)
	
	case class OperationMetadata(
	  /** Output only. The time the operation was created. */
		createTime: Option[String] = None,
	  /** Output only. The time the operation finished running. */
		endTime: Option[String] = None,
	  /** Output only. Server-defined resource path for the target of the operation. */
		target: Option[String] = None,
	  /** Output only. Name of the verb executed by the operation. */
		verb: Option[String] = None,
	  /** Output only. Human-readable status of the operation, if any. */
		statusMessage: Option[String] = None,
	  /** Output only. Identifies whether the user has requested cancellation of the operation. Operations that have been cancelled successfully have google.longrunning.Operation.error value with a google.rpc.Status.code of 1, corresponding to `Code.CANCELLED`. */
		requestedCancellation: Option[Boolean] = None,
	  /** Output only. API version used to start the operation. */
		apiVersion: Option[String] = None
	)
}
