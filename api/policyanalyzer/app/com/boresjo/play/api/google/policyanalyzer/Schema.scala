package com.boresjo.play.api.google.policyanalyzer

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	case class GoogleCloudPolicyanalyzerV1ObservationPeriod(
	  /** The observation start time. The time in this timestamp is always `07:00:00Z`. */
		startTime: Option[String] = None,
	  /** The observation end time. The time in this timestamp is always `07:00:00Z`. */
		endTime: Option[String] = None
	)
	
	case class GoogleCloudPolicyanalyzerV1QueryActivityResponse(
	  /** The set of activities that match the filter included in the request. */
		activities: Option[List[Schema.GoogleCloudPolicyanalyzerV1Activity]] = None,
	  /** If there might be more results than those appearing in this response, then `nextPageToken` is included. To get the next set of results, call this method again using the value of `nextPageToken` as `pageToken`. */
		nextPageToken: Option[String] = None
	)
	
	case class GoogleCloudPolicyanalyzerV1Activity(
	  /** The type of the activity. */
		activityType: Option[String] = None,
	  /** A struct of custom fields to explain the activity. */
		activity: Option[Map[String, JsValue]] = None,
	  /** The full resource name that identifies the resource. For examples of full resource names for Google Cloud services, see https://cloud.google.com/iam/help/troubleshooter/full-resource-names. */
		fullResourceName: Option[String] = None,
	  /** The data observation period to build the activity. */
		observationPeriod: Option[Schema.GoogleCloudPolicyanalyzerV1ObservationPeriod] = None
	)
}
