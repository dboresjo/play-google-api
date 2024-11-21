package com.boresjo.play.api.google.cloudprofiler

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	object CreateProfileRequest {
		enum ProfileTypeEnum extends Enum[ProfileTypeEnum] { case PROFILE_TYPE_UNSPECIFIED, CPU, WALL, HEAP, THREADS, CONTENTION, PEAK_HEAP, HEAP_ALLOC }
	}
	case class CreateProfileRequest(
	  /** Deployment details. */
		deployment: Option[Schema.Deployment] = None,
	  /** One or more profile types that the agent is capable of providing. */
		profileType: Option[List[Schema.CreateProfileRequest.ProfileTypeEnum]] = None
	)
	
	case class Deployment(
	  /** Project ID is the ID of a cloud project. Validation regex: `^a-z{4,61}[a-z0-9]$`. */
		projectId: Option[String] = None,
	  /** Target is the service name used to group related deployments: &#42; Service name for App Engine Flex / Standard. &#42; Cluster and container name for GKE. &#42; User-specified string for direct Compute Engine profiling (e.g. Java). &#42; Job name for Dataflow. Validation regex: `^[a-z0-9]([-a-z0-9_.]{0,253}[a-z0-9])?$`. */
		target: Option[String] = None,
	  /** Labels identify the deployment within the user universe and same target. Validation regex for label names: `^[a-z0-9]([a-z0-9-]{0,61}[a-z0-9])?$`. Value for an individual label must be <= 512 bytes, the total size of all label names and values must be <= 1024 bytes. Label named "language" can be used to record the programming language of the profiled deployment. The standard choices for the value include "java", "go", "python", "ruby", "nodejs", "php", "dotnet". For deployments running on Google Cloud Platform, "zone" or "region" label should be present describing the deployment location. An example of a zone is "us-central1-a", an example of a region is "us-central1" or "us-central". */
		labels: Option[Map[String, String]] = None
	)
	
	object Profile {
		enum ProfileTypeEnum extends Enum[ProfileTypeEnum] { case PROFILE_TYPE_UNSPECIFIED, CPU, WALL, HEAP, THREADS, CONTENTION, PEAK_HEAP, HEAP_ALLOC }
	}
	case class Profile(
	  /** Output only. Opaque, server-assigned, unique ID for this profile. */
		name: Option[String] = None,
	  /** Type of profile. For offline mode, this must be specified when creating the profile. For online mode it is assigned and returned by the server. */
		profileType: Option[Schema.Profile.ProfileTypeEnum] = None,
	  /** Deployment this profile corresponds to. */
		deployment: Option[Schema.Deployment] = None,
	  /** Duration of the profiling session. Input (for the offline mode) or output (for the online mode). The field represents requested profiling duration. It may slightly differ from the effective profiling duration, which is recorded in the profile data, in case the profiling can't be stopped immediately (e.g. in case stopping the profiling is handled asynchronously). */
		duration: Option[String] = None,
	  /** Input only. Profile bytes, as a gzip compressed serialized proto, the format is https://github.com/google/pprof/blob/master/proto/profile.proto. */
		profileBytes: Option[String] = None,
	  /** Input only. Labels associated to this specific profile. These labels will get merged with the deployment labels for the final data set. See documentation on deployment labels for validation rules and limits. */
		labels: Option[Map[String, String]] = None,
	  /** Output only. Start time for the profile. This output is only present in response from the ListProfiles method. */
		startTime: Option[String] = None
	)
	
	case class ListProfilesResponse(
	  /** List of profiles fetched. */
		profiles: Option[List[Schema.Profile]] = None,
	  /** Token to receive the next page of results. This field maybe empty if there are no more profiles to fetch. */
		nextPageToken: Option[String] = None,
	  /** Number of profiles that were skipped in the current page since they were not able to be fetched successfully. This should typically be zero. A non-zero value may indicate a transient failure, in which case if the number is too high for your use case, the call may be retried. */
		skippedProfiles: Option[Int] = None
	)
}
