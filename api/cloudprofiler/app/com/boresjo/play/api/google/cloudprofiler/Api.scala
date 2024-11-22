package com.boresjo.play.api.google.cloudprofiler

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
		"""https://www.googleapis.com/auth/cloud-platform""" /* See, edit, configure, and delete your Google Cloud data and see the email address for your Google Account. */,
		"""https://www.googleapis.com/auth/monitoring""" /* View and write monitoring data for all of your Google and third-party Cloud and API projects */,
		"""https://www.googleapis.com/auth/monitoring.write""" /* Publish metric data to your Google Cloud projects */
	)

	private val BASE_URL = "https://cloudprofiler.googleapis.com/"

	object projects {
		object profiles {
			/** CreateProfile creates a new profile resource in the online mode. _Direct use of this API is discouraged, please use a [supported profiler agent](https://cloud.google.com/profiler/docs/about-profiler#profiling_agent) instead for profile collection._ The server ensures that the new profiles are created at a constant rate per deployment, so the creation request may hang for some time until the next profile session is available. The request may fail with ABORTED error if the creation is not available within ~1m, the response will indicate the duration of the backoff the client should take before attempting creating a profile again. The backoff duration is returned in google.rpc.RetryInfo extension on the response status. To a gRPC client, the extension will be return as a binary-serialized proto in the trailing metadata item named "google.rpc.retryinfo-bin".  */
			class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/monitoring""", """https://www.googleapis.com/auth/monitoring.write""")
				/** Perform the request */
				def withCreateProfileRequest(body: Schema.CreateProfileRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Profile])
			}
			object create {
				def apply(projectsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v2/projects/${projectsId}/profiles").addQueryStringParameters("parent" -> parent.toString))
			}
			/** CreateOfflineProfile creates a new profile resource in the offline mode. The client provides the profile to create along with the profile bytes, the server records it. _Direct use of this API is discouraged, please use a [supported profiler agent](https://cloud.google.com/profiler/docs/about-profiler#profiling_agent) instead for profile collection._ */
			class createOffline(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/monitoring""", """https://www.googleapis.com/auth/monitoring.write""")
				/** Perform the request */
				def withProfile(body: Schema.Profile) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Profile])
			}
			object createOffline {
				def apply(projectsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): createOffline = new createOffline(ws.url(BASE_URL + s"v2/projects/${projectsId}/profiles:createOffline").addQueryStringParameters("parent" -> parent.toString))
			}
			/** UpdateProfile updates the profile bytes and labels on the profile resource created in the online mode. Updating the bytes for profiles created in the offline mode is currently not supported: the profile content must be provided at the time of the profile creation. _Direct use of this API is discouraged, please use a [supported profiler agent](https://cloud.google.com/profiler/docs/about-profiler#profiling_agent) instead for profile collection._ */
			class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/monitoring""", """https://www.googleapis.com/auth/monitoring.write""")
				/** Perform the request */
				def withProfile(body: Schema.Profile) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Profile])
			}
			object patch {
				def apply(projectsId :PlayApi, profilesId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v2/projects/${projectsId}/profiles/${profilesId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
			}
			/** Lists profiles which have been collected so far and for which the caller has permission to view. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListProfilesResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/monitoring""", """https://www.googleapis.com/auth/monitoring.write""")
				/** Optional. The maximum number of items to return. Default page_size is 1000. Max limit is 1000.<br>Format: int32 */
				def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
				/** Optional. The token to continue pagination and get profiles from a particular page. When paginating, all other parameters provided to `ListProfiles` must match the call that provided the page token. */
				def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListProfilesResponse])
			}
			object list {
				def apply(projectsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/projects/${projectsId}/profiles").addQueryStringParameters("parent" -> parent.toString))
				given Conversion[list, Future[Schema.ListProfilesResponse]] = (fun: list) => fun.apply()
			}
		}
	}
}
