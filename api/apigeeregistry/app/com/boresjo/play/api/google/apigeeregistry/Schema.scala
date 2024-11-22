package com.boresjo.play.api.google.apigeeregistry

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
	
	object Instance {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, INACTIVE, CREATING, ACTIVE, UPDATING, DELETING, FAILED }
	}
	case class Instance(
	  /** Format: `projects/&#42;/locations/&#42;/instance`. Currently only `locations/global` is supported. */
		name: Option[String] = None,
	  /** Output only. Creation timestamp. */
		createTime: Option[String] = None,
	  /** Output only. Last update timestamp. */
		updateTime: Option[String] = None,
	  /** Output only. The current state of the Instance. */
		state: Option[Schema.Instance.StateEnum] = None,
	  /** Output only. Extra information of Instance.State if the state is `FAILED`. */
		stateMessage: Option[String] = None,
	  /** Required. Config of the Instance. */
		config: Option[Schema.Config] = None,
	  /** Output only. Build info of the Instance if it's in `ACTIVE` state. */
		build: Option[Schema.Build] = None
	)
	
	case class Config(
	  /** Output only. The GCP location where the Instance resides. */
		location: Option[String] = None,
	  /** Required. The Customer Managed Encryption Key (CMEK) used for data encryption. The CMEK name should follow the format of `projects/([^/]+)/locations/([^/]+)/keyRings/([^/]+)/cryptoKeys/([^/]+)`, where the `location` must match InstanceConfig.location. */
		cmekKeyName: Option[String] = None
	)
	
	case class Build(
	  /** Output only. Path of the open source repository: github.com/apigee/registry. */
		repo: Option[String] = None,
	  /** Output only. Commit ID of the latest commit in the build. */
		commitId: Option[String] = None,
	  /** Output only. Commit time of the latest commit in the build. */
		commitTime: Option[String] = None
	)
	
	case class ListApisResponse(
	  /** The APIs from the specified publisher. */
		apis: Option[List[Schema.Api]] = None,
	  /** A token, which can be sent as `page_token` to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None
	)
	
	case class Api(
	  /** Resource name. */
		name: Option[String] = None,
	  /** Human-meaningful name. */
		displayName: Option[String] = None,
	  /** A detailed description. */
		description: Option[String] = None,
	  /** Output only. Creation timestamp. */
		createTime: Option[String] = None,
	  /** Output only. Last update timestamp. */
		updateTime: Option[String] = None,
	  /** A user-definable description of the availability of this service. Format: free-form, but we expect single words that describe availability, e.g., "NONE", "TESTING", "PREVIEW", "GENERAL", "DEPRECATED", "SHUTDOWN". */
		availability: Option[String] = None,
	  /** The recommended version of the API. Format: `projects/{project}/locations/{location}/apis/{api}/versions/{version}` */
		recommendedVersion: Option[String] = None,
	  /** The recommended deployment of the API. Format: `projects/{project}/locations/{location}/apis/{api}/deployments/{deployment}` */
		recommendedDeployment: Option[String] = None,
	  /** Labels attach identifying metadata to resources. Identifying metadata can be used to filter list operations. Label keys and values can be no longer than 64 characters (Unicode codepoints), can only contain lowercase letters, numeric characters, underscores, and dashes. International characters are allowed. No more than 64 user labels can be associated with one resource (System labels are excluded). See https://goo.gl/xmQnxf for more information and examples of labels. System reserved label keys are prefixed with `apigeeregistry.googleapis.com/` and cannot be changed. */
		labels: Option[Map[String, String]] = None,
	  /** Annotations attach non-identifying metadata to resources. Annotation keys and values are less restricted than those of labels, but should be generally used for small values of broad interest. Larger, topic- specific metadata should be stored in Artifacts. */
		annotations: Option[Map[String, String]] = None
	)
	
	case class ListApiVersionsResponse(
	  /** The versions from the specified publisher. */
		apiVersions: Option[List[Schema.ApiVersion]] = None,
	  /** A token, which can be sent as `page_token` to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None
	)
	
	case class ApiVersion(
	  /** Resource name. */
		name: Option[String] = None,
	  /** Human-meaningful name. */
		displayName: Option[String] = None,
	  /** A detailed description. */
		description: Option[String] = None,
	  /** Output only. Creation timestamp. */
		createTime: Option[String] = None,
	  /** Output only. Last update timestamp. */
		updateTime: Option[String] = None,
	  /** A user-definable description of the lifecycle phase of this API version. Format: free-form, but we expect single words that describe API maturity, e.g., "CONCEPT", "DESIGN", "DEVELOPMENT", "STAGING", "PRODUCTION", "DEPRECATED", "RETIRED". */
		state: Option[String] = None,
	  /** Labels attach identifying metadata to resources. Identifying metadata can be used to filter list operations. Label keys and values can be no longer than 64 characters (Unicode codepoints), can only contain lowercase letters, numeric characters, underscores and dashes. International characters are allowed. No more than 64 user labels can be associated with one resource (System labels are excluded). See https://goo.gl/xmQnxf for more information and examples of labels. System reserved label keys are prefixed with `apigeeregistry.googleapis.com/` and cannot be changed. */
		labels: Option[Map[String, String]] = None,
	  /** Annotations attach non-identifying metadata to resources. Annotation keys and values are less restricted than those of labels, but should be generally used for small values of broad interest. Larger, topic- specific metadata should be stored in Artifacts. */
		annotations: Option[Map[String, String]] = None,
	  /** The primary spec for this version. Format: projects/{project}/locations/{location}/apis/{api}/versions/{version}/specs/{spec} */
		primarySpec: Option[String] = None
	)
	
	case class ListApiSpecsResponse(
	  /** The specs from the specified publisher. */
		apiSpecs: Option[List[Schema.ApiSpec]] = None,
	  /** A token, which can be sent as `page_token` to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None
	)
	
	case class ApiSpec(
	  /** Resource name. */
		name: Option[String] = None,
	  /** A possibly-hierarchical name used to refer to the spec from other specs. */
		filename: Option[String] = None,
	  /** A detailed description. */
		description: Option[String] = None,
	  /** Output only. Immutable. The revision ID of the spec. A new revision is committed whenever the spec contents are changed. The format is an 8-character hexadecimal string. */
		revisionId: Option[String] = None,
	  /** Output only. Creation timestamp; when the spec resource was created. */
		createTime: Option[String] = None,
	  /** Output only. Revision creation timestamp; when the represented revision was created. */
		revisionCreateTime: Option[String] = None,
	  /** Output only. Last update timestamp: when the represented revision was last modified. */
		revisionUpdateTime: Option[String] = None,
	  /** A style (format) descriptor for this spec that is specified as a [Media Type](https://en.wikipedia.org/wiki/Media_type). Possible values include `application/vnd.apigee.proto`, `application/vnd.apigee.openapi`, and `application/vnd.apigee.graphql`, with possible suffixes representing compression types. These hypothetical names are defined in the vendor tree defined in RFC6838 (https://tools.ietf.org/html/rfc6838) and are not final. Content types can specify compression. Currently only GZip compression is supported (indicated with "+gzip"). */
		mimeType: Option[String] = None,
	  /** Output only. The size of the spec file in bytes. If the spec is gzipped, this is the size of the uncompressed spec. */
		sizeBytes: Option[Int] = None,
	  /** Output only. A SHA-256 hash of the spec's contents. If the spec is gzipped, this is the hash of the uncompressed spec. */
		hash: Option[String] = None,
	  /** The original source URI of the spec (if one exists). This is an external location that can be used for reference purposes but which may not be authoritative since this external resource may change after the spec is retrieved. */
		sourceUri: Option[String] = None,
	  /** Input only. The contents of the spec. Provided by API callers when specs are created or updated. To access the contents of a spec, use GetApiSpecContents. */
		contents: Option[String] = None,
	  /** Labels attach identifying metadata to resources. Identifying metadata can be used to filter list operations. Label keys and values can be no longer than 64 characters (Unicode codepoints), can only contain lowercase letters, numeric characters, underscores and dashes. International characters are allowed. No more than 64 user labels can be associated with one resource (System labels are excluded). See https://goo.gl/xmQnxf for more information and examples of labels. System reserved label keys are prefixed with `apigeeregistry.googleapis.com/` and cannot be changed. */
		labels: Option[Map[String, String]] = None,
	  /** Annotations attach non-identifying metadata to resources. Annotation keys and values are less restricted than those of labels, but should be generally used for small values of broad interest. Larger, topic- specific metadata should be stored in Artifacts. */
		annotations: Option[Map[String, String]] = None
	)
	
	case class HttpBody(
	  /** The HTTP Content-Type header value specifying the content type of the body. */
		contentType: Option[String] = None,
	  /** The HTTP request/response body as raw binary. */
		data: Option[String] = None,
	  /** Application specific response metadata. Must be set in the first response for streaming APIs. */
		extensions: Option[List[Map[String, JsValue]]] = None
	)
	
	case class TagApiSpecRevisionRequest(
	  /** Required. The tag to apply. The tag should be at most 40 characters, and match `a-z{3,39}`. */
		tag: Option[String] = None
	)
	
	case class ListApiSpecRevisionsResponse(
	  /** The revisions of the spec. */
		apiSpecs: Option[List[Schema.ApiSpec]] = None,
	  /** A token that can be sent as `page_token` to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None
	)
	
	case class RollbackApiSpecRequest(
	  /** Required. The revision ID to roll back to. It must be a revision of the same spec. Example: `c7cfa2a8` */
		revisionId: Option[String] = None
	)
	
	case class ListApiDeploymentsResponse(
	  /** The deployments from the specified publisher. */
		apiDeployments: Option[List[Schema.ApiDeployment]] = None,
	  /** A token, which can be sent as `page_token` to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None
	)
	
	case class ApiDeployment(
	  /** Resource name. */
		name: Option[String] = None,
	  /** Human-meaningful name. */
		displayName: Option[String] = None,
	  /** A detailed description. */
		description: Option[String] = None,
	  /** Output only. Immutable. The revision ID of the deployment. A new revision is committed whenever the deployment contents are changed. The format is an 8-character hexadecimal string. */
		revisionId: Option[String] = None,
	  /** Output only. Creation timestamp; when the deployment resource was created. */
		createTime: Option[String] = None,
	  /** Output only. Revision creation timestamp; when the represented revision was created. */
		revisionCreateTime: Option[String] = None,
	  /** Output only. Last update timestamp: when the represented revision was last modified. */
		revisionUpdateTime: Option[String] = None,
	  /** The full resource name (including revision ID) of the spec of the API being served by the deployment. Changes to this value will update the revision. Format: `projects/{project}/locations/{location}/apis/{api}/versions/{version}/specs/{spec@revision}` */
		apiSpecRevision: Option[String] = None,
	  /** The address where the deployment is serving. Changes to this value will update the revision. */
		endpointUri: Option[String] = None,
	  /** The address of the external channel of the API (e.g., the Developer Portal). Changes to this value will not affect the revision. */
		externalChannelUri: Option[String] = None,
	  /** Text briefly identifying the intended audience of the API. Changes to this value will not affect the revision. */
		intendedAudience: Option[String] = None,
	  /** Text briefly describing how to access the endpoint. Changes to this value will not affect the revision. */
		accessGuidance: Option[String] = None,
	  /** Labels attach identifying metadata to resources. Identifying metadata can be used to filter list operations. Label keys and values can be no longer than 64 characters (Unicode codepoints), can only contain lowercase letters, numeric characters, underscores and dashes. International characters are allowed. No more than 64 user labels can be associated with one resource (System labels are excluded). See https://goo.gl/xmQnxf for more information and examples of labels. System reserved label keys are prefixed with `apigeeregistry.googleapis.com/` and cannot be changed. */
		labels: Option[Map[String, String]] = None,
	  /** Annotations attach non-identifying metadata to resources. Annotation keys and values are less restricted than those of labels, but should be generally used for small values of broad interest. Larger, topic- specific metadata should be stored in Artifacts. */
		annotations: Option[Map[String, String]] = None
	)
	
	case class TagApiDeploymentRevisionRequest(
	  /** Required. The tag to apply. The tag should be at most 40 characters, and match `a-z{3,39}`. */
		tag: Option[String] = None
	)
	
	case class ListApiDeploymentRevisionsResponse(
	  /** The revisions of the deployment. */
		apiDeployments: Option[List[Schema.ApiDeployment]] = None,
	  /** A token that can be sent as `page_token` to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None
	)
	
	case class RollbackApiDeploymentRequest(
	  /** Required. The revision ID to roll back to. It must be a revision of the same deployment. Example: `c7cfa2a8` */
		revisionId: Option[String] = None
	)
	
	case class ListArtifactsResponse(
	  /** The artifacts from the specified publisher. */
		artifacts: Option[List[Schema.Artifact]] = None,
	  /** A token, which can be sent as `page_token` to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None
	)
	
	case class Artifact(
	  /** Resource name. */
		name: Option[String] = None,
	  /** Output only. Creation timestamp. */
		createTime: Option[String] = None,
	  /** Output only. Last update timestamp. */
		updateTime: Option[String] = None,
	  /** A content type specifier for the artifact. Content type specifiers are Media Types (https://en.wikipedia.org/wiki/Media_type) with a possible "schema" parameter that specifies a schema for the stored information. Content types can specify compression. Currently only GZip compression is supported (indicated with "+gzip"). */
		mimeType: Option[String] = None,
	  /** Output only. The size of the artifact in bytes. If the artifact is gzipped, this is the size of the uncompressed artifact. */
		sizeBytes: Option[Int] = None,
	  /** Output only. A SHA-256 hash of the artifact's contents. If the artifact is gzipped, this is the hash of the uncompressed artifact. */
		hash: Option[String] = None,
	  /** Input only. The contents of the artifact. Provided by API callers when artifacts are created or replaced. To access the contents of an artifact, use GetArtifactContents. */
		contents: Option[String] = None,
	  /** Labels attach identifying metadata to resources. Identifying metadata can be used to filter list operations. Label keys and values can be no longer than 64 characters (Unicode codepoints), can only contain lowercase letters, numeric characters, underscores and dashes. International characters are allowed. No more than 64 user labels can be associated with one resource (System labels are excluded). See https://goo.gl/xmQnxf for more information and examples of labels. System reserved label keys are prefixed with "registry.googleapis.com/" and cannot be changed. */
		labels: Option[Map[String, String]] = None,
	  /** Annotations attach non-identifying metadata to resources. Annotation keys and values are less restricted than those of labels, but should be generally used for small values of broad interest. Larger, topic- specific metadata should be stored in Artifacts. */
		annotations: Option[Map[String, String]] = None
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
	
	case class SetIamPolicyRequest(
	  /** REQUIRED: The complete policy to be applied to the `resource`. The size of the policy is limited to a few 10s of KB. An empty policy is a valid policy but certain Google Cloud services (such as Projects) might reject them. */
		policy: Option[Schema.Policy] = None
	)
	
	case class Policy(
	  /** Specifies the format of the policy. Valid values are `0`, `1`, and `3`. Requests that specify an invalid value are rejected. Any operation that affects conditional role bindings must specify version `3`. This requirement applies to the following operations: &#42; Getting a policy that includes a conditional role binding &#42; Adding a conditional role binding to a policy &#42; Changing a conditional role binding in a policy &#42; Removing any role binding, with or without a condition, from a policy that includes conditions &#42;&#42;Important:&#42;&#42; If you use IAM Conditions, you must include the `etag` field whenever you call `setIamPolicy`. If you omit this field, then IAM allows you to overwrite a version `3` policy with a version `1` policy, and all of the conditions in the version `3` policy are lost. If a policy does not include any conditions, operations on that policy may specify any valid version or leave the field unset. To learn which resources support conditions in their IAM policies, see the [IAM documentation](https://cloud.google.com/iam/help/conditions/resource-policies). */
		version: Option[Int] = None,
	  /** Associates a list of `members`, or principals, with a `role`. Optionally, may specify a `condition` that determines how and when the `bindings` are applied. Each of the `bindings` must contain at least one principal. The `bindings` in a `Policy` can refer to up to 1,500 principals; up to 250 of these principals can be Google groups. Each occurrence of a principal counts towards these limits. For example, if the `bindings` grant 50 different roles to `user:alice@example.com`, and not to any other principal, then you can add another 1,450 principals to the `bindings` in the `Policy`. */
		bindings: Option[List[Schema.Binding]] = None,
	  /** `etag` is used for optimistic concurrency control as a way to help prevent simultaneous updates of a policy from overwriting each other. It is strongly suggested that systems make use of the `etag` in the read-modify-write cycle to perform policy updates in order to avoid race conditions: An `etag` is returned in the response to `getIamPolicy`, and systems are expected to put that etag in the request to `setIamPolicy` to ensure that their change will be applied to the same version of the policy. &#42;&#42;Important:&#42;&#42; If you use IAM Conditions, you must include the `etag` field whenever you call `setIamPolicy`. If you omit this field, then IAM allows you to overwrite a version `3` policy with a version `1` policy, and all of the conditions in the version `3` policy are lost. */
		etag: Option[String] = None
	)
	
	case class Binding(
	  /** Role that is assigned to the list of `members`, or principals. For example, `roles/viewer`, `roles/editor`, or `roles/owner`. */
		role: Option[String] = None,
	  /** Specifies the principals requesting access for a Google Cloud resource. `members` can have the following values: &#42; `allUsers`: A special identifier that represents anyone who is on the internet; with or without a Google account. &#42; `allAuthenticatedUsers`: A special identifier that represents anyone who is authenticated with a Google account or a service account. Does not include identities that come from external identity providers (IdPs) through identity federation. &#42; `user:{emailid}`: An email address that represents a specific Google account. For example, `alice@example.com` . &#42; `serviceAccount:{emailid}`: An email address that represents a Google service account. For example, `my-other-app@appspot.gserviceaccount.com`. &#42; `serviceAccount:{projectid}.svc.id.goog[{namespace}/{kubernetes-sa}]`: An identifier for a [Kubernetes service account](https://cloud.google.com/kubernetes-engine/docs/how-to/kubernetes-service-accounts). For example, `my-project.svc.id.goog[my-namespace/my-kubernetes-sa]`. &#42; `group:{emailid}`: An email address that represents a Google group. For example, `admins@example.com`. &#42; `domain:{domain}`: The G Suite domain (primary) that represents all the users of that domain. For example, `google.com` or `example.com`. &#42; `deleted:user:{emailid}?uid={uniqueid}`: An email address (plus unique identifier) representing a user that has been recently deleted. For example, `alice@example.com?uid=123456789012345678901`. If the user is recovered, this value reverts to `user:{emailid}` and the recovered user retains the role in the binding. &#42; `deleted:serviceAccount:{emailid}?uid={uniqueid}`: An email address (plus unique identifier) representing a service account that has been recently deleted. For example, `my-other-app@appspot.gserviceaccount.com?uid=123456789012345678901`. If the service account is undeleted, this value reverts to `serviceAccount:{emailid}` and the undeleted service account retains the role in the binding. &#42; `deleted:group:{emailid}?uid={uniqueid}`: An email address (plus unique identifier) representing a Google group that has been recently deleted. For example, `admins@example.com?uid=123456789012345678901`. If the group is recovered, this value reverts to `group:{emailid}` and the recovered group retains the role in the binding. */
		members: Option[List[String]] = None,
	  /** The condition that is associated with this binding. If the condition evaluates to `true`, then this binding applies to the current request. If the condition evaluates to `false`, then this binding does not apply to the current request. However, a different role binding might grant the same role to one or more of the principals in this binding. To learn which resources support conditions in their IAM policies, see the [IAM documentation](https://cloud.google.com/iam/help/conditions/resource-policies). */
		condition: Option[Schema.Expr] = None
	)
	
	case class Expr(
	  /** Textual representation of an expression in Common Expression Language syntax. */
		expression: Option[String] = None,
	  /** Optional. Title for the expression, i.e. a short string describing its purpose. This can be used e.g. in UIs which allow to enter the expression. */
		title: Option[String] = None,
	  /** Optional. Description of the expression. This is a longer text which describes the expression, e.g. when hovered over it in a UI. */
		description: Option[String] = None,
	  /** Optional. String indicating the location of the expression for error reporting, e.g. a file name and a position in the file. */
		location: Option[String] = None
	)
	
	case class TestIamPermissionsRequest(
	  /** The set of permissions to check for the `resource`. Permissions with wildcards (such as `&#42;` or `storage.&#42;`) are not allowed. For more information see [IAM Overview](https://cloud.google.com/iam/docs/overview#permissions). */
		permissions: Option[List[String]] = None
	)
	
	case class TestIamPermissionsResponse(
	  /** A subset of `TestPermissionsRequest.permissions` that the caller is allowed. */
		permissions: Option[List[String]] = None
	)
	
	case class OperationMetadata(
	  /** The time the operation was created. */
		createTime: Option[String] = None,
	  /** The time the operation finished running. */
		endTime: Option[String] = None,
	  /** Server-defined resource path for the target of the operation. */
		target: Option[String] = None,
	  /** Name of the verb executed by the operation. */
		verb: Option[String] = None,
	  /** Human-readable status of the operation, if any. */
		statusMessage: Option[String] = None,
	  /** Identifies whether the user has requested cancellation of the operation. Operations that have successfully been cancelled have Operation.error value with a google.rpc.Status.code of 1, corresponding to `Code.CANCELLED`. */
		cancellationRequested: Option[Boolean] = None,
	  /** API version used to start the operation. */
		apiVersion: Option[String] = None
	)
}
