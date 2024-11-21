package com.boresjo.play.api.google.apigeeregistry

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Formats {
	given fmtListOperationsResponse: Format[Schema.ListOperationsResponse] = Json.format[Schema.ListOperationsResponse]
	given fmtOperation: Format[Schema.Operation] = Json.format[Schema.Operation]
	given fmtStatus: Format[Schema.Status] = Json.format[Schema.Status]
	given fmtEmpty: Format[Schema.Empty] = Json.format[Schema.Empty]
	given fmtCancelOperationRequest: Format[Schema.CancelOperationRequest] = Json.format[Schema.CancelOperationRequest]
	given fmtInstance: Format[Schema.Instance] = Json.format[Schema.Instance]
	given fmtInstanceStateEnum: Format[Schema.Instance.StateEnum] = JsonEnumFormat[Schema.Instance.StateEnum]
	given fmtConfig: Format[Schema.Config] = Json.format[Schema.Config]
	given fmtBuild: Format[Schema.Build] = Json.format[Schema.Build]
	given fmtListApisResponse: Format[Schema.ListApisResponse] = Json.format[Schema.ListApisResponse]
	given fmtApi: Format[Schema.Api] = Json.format[Schema.Api]
	given fmtListApiVersionsResponse: Format[Schema.ListApiVersionsResponse] = Json.format[Schema.ListApiVersionsResponse]
	given fmtApiVersion: Format[Schema.ApiVersion] = Json.format[Schema.ApiVersion]
	given fmtListApiSpecsResponse: Format[Schema.ListApiSpecsResponse] = Json.format[Schema.ListApiSpecsResponse]
	given fmtApiSpec: Format[Schema.ApiSpec] = Json.format[Schema.ApiSpec]
	given fmtHttpBody: Format[Schema.HttpBody] = Json.format[Schema.HttpBody]
	given fmtTagApiSpecRevisionRequest: Format[Schema.TagApiSpecRevisionRequest] = Json.format[Schema.TagApiSpecRevisionRequest]
	given fmtListApiSpecRevisionsResponse: Format[Schema.ListApiSpecRevisionsResponse] = Json.format[Schema.ListApiSpecRevisionsResponse]
	given fmtRollbackApiSpecRequest: Format[Schema.RollbackApiSpecRequest] = Json.format[Schema.RollbackApiSpecRequest]
	given fmtListApiDeploymentsResponse: Format[Schema.ListApiDeploymentsResponse] = Json.format[Schema.ListApiDeploymentsResponse]
	given fmtApiDeployment: Format[Schema.ApiDeployment] = Json.format[Schema.ApiDeployment]
	given fmtTagApiDeploymentRevisionRequest: Format[Schema.TagApiDeploymentRevisionRequest] = Json.format[Schema.TagApiDeploymentRevisionRequest]
	given fmtListApiDeploymentRevisionsResponse: Format[Schema.ListApiDeploymentRevisionsResponse] = Json.format[Schema.ListApiDeploymentRevisionsResponse]
	given fmtRollbackApiDeploymentRequest: Format[Schema.RollbackApiDeploymentRequest] = Json.format[Schema.RollbackApiDeploymentRequest]
	given fmtListArtifactsResponse: Format[Schema.ListArtifactsResponse] = Json.format[Schema.ListArtifactsResponse]
	given fmtArtifact: Format[Schema.Artifact] = Json.format[Schema.Artifact]
	given fmtListLocationsResponse: Format[Schema.ListLocationsResponse] = Json.format[Schema.ListLocationsResponse]
	given fmtLocation: Format[Schema.Location] = Json.format[Schema.Location]
	given fmtSetIamPolicyRequest: Format[Schema.SetIamPolicyRequest] = Json.format[Schema.SetIamPolicyRequest]
	given fmtPolicy: Format[Schema.Policy] = Json.format[Schema.Policy]
	given fmtBinding: Format[Schema.Binding] = Json.format[Schema.Binding]
	given fmtExpr: Format[Schema.Expr] = Json.format[Schema.Expr]
	given fmtTestIamPermissionsRequest: Format[Schema.TestIamPermissionsRequest] = Json.format[Schema.TestIamPermissionsRequest]
	given fmtTestIamPermissionsResponse: Format[Schema.TestIamPermissionsResponse] = Json.format[Schema.TestIamPermissionsResponse]
	given fmtOperationMetadata: Format[Schema.OperationMetadata] = Json.format[Schema.OperationMetadata]
}
