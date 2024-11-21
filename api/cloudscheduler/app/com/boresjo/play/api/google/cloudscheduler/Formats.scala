package com.boresjo.play.api.google.cloudscheduler

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Formats {
	given fmtListLocationsResponse: Format[Schema.ListLocationsResponse] = Json.format[Schema.ListLocationsResponse]
	given fmtLocation: Format[Schema.Location] = Json.format[Schema.Location]
	given fmtListJobsResponse: Format[Schema.ListJobsResponse] = Json.format[Schema.ListJobsResponse]
	given fmtJob: Format[Schema.Job] = Json.format[Schema.Job]
	given fmtPubsubTarget: Format[Schema.PubsubTarget] = Json.format[Schema.PubsubTarget]
	given fmtAppEngineHttpTarget: Format[Schema.AppEngineHttpTarget] = Json.format[Schema.AppEngineHttpTarget]
	given fmtHttpTarget: Format[Schema.HttpTarget] = Json.format[Schema.HttpTarget]
	given fmtJobStateEnum: Format[Schema.Job.StateEnum] = JsonEnumFormat[Schema.Job.StateEnum]
	given fmtStatus: Format[Schema.Status] = Json.format[Schema.Status]
	given fmtRetryConfig: Format[Schema.RetryConfig] = Json.format[Schema.RetryConfig]
	given fmtAppEngineHttpTargetHttpMethodEnum: Format[Schema.AppEngineHttpTarget.HttpMethodEnum] = JsonEnumFormat[Schema.AppEngineHttpTarget.HttpMethodEnum]
	given fmtAppEngineRouting: Format[Schema.AppEngineRouting] = Json.format[Schema.AppEngineRouting]
	given fmtHttpTargetHttpMethodEnum: Format[Schema.HttpTarget.HttpMethodEnum] = JsonEnumFormat[Schema.HttpTarget.HttpMethodEnum]
	given fmtOAuthToken: Format[Schema.OAuthToken] = Json.format[Schema.OAuthToken]
	given fmtOidcToken: Format[Schema.OidcToken] = Json.format[Schema.OidcToken]
	given fmtEmpty: Format[Schema.Empty] = Json.format[Schema.Empty]
	given fmtPauseJobRequest: Format[Schema.PauseJobRequest] = Json.format[Schema.PauseJobRequest]
	given fmtResumeJobRequest: Format[Schema.ResumeJobRequest] = Json.format[Schema.ResumeJobRequest]
	given fmtRunJobRequest: Format[Schema.RunJobRequest] = Json.format[Schema.RunJobRequest]
	given fmtListOperationsResponse: Format[Schema.ListOperationsResponse] = Json.format[Schema.ListOperationsResponse]
	given fmtOperation: Format[Schema.Operation] = Json.format[Schema.Operation]
	given fmtCancelOperationRequest: Format[Schema.CancelOperationRequest] = Json.format[Schema.CancelOperationRequest]
	given fmtPubsubMessage: Format[Schema.PubsubMessage] = Json.format[Schema.PubsubMessage]
	given fmtOperationMetadata: Format[Schema.OperationMetadata] = Json.format[Schema.OperationMetadata]
}
