package com.boresjo.play.api.google.dataportability

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	case class ResetAuthorizationRequest(
	
	)
	
	case class RetryPortabilityArchiveRequest(
	
	)
	
	case class InitiatePortabilityArchiveRequest(
	  /** The resources from which you're exporting data. These values have a 1:1 correspondence with the OAuth scopes. */
		resources: Option[List[String]] = None
	)
	
	case class RetryPortabilityArchiveResponse(
	  /** The archive job ID that is initiated by the retry endpoint. This can be used to get the state of the new job. */
		archiveJobId: Option[String] = None
	)
	
	case class InitiatePortabilityArchiveResponse(
	  /** The archive job ID that is initiated in the API. This can be used to get the state of the job. */
		archiveJobId: Option[String] = None
	)
	
	case class Empty(
	
	)
	
	object PortabilityArchiveState {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, IN_PROGRESS, COMPLETE, FAILED, CANCELLED }
	}
	case class PortabilityArchiveState(
	  /** The resource name of ArchiveJob's PortabilityArchiveState singleton. The format is: archiveJobs/{archive_job}/portabilityArchiveState. archive_job is the job ID provided in the request. */
		name: Option[String] = None,
	  /** Resource that represents the state of the Archive job. */
		state: Option[Schema.PortabilityArchiveState.StateEnum] = None,
	  /** If the state is complete, this method returns the signed URLs of the objects in the Cloud Storage bucket. */
		urls: Option[List[String]] = None
	)
}
