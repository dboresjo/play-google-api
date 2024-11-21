package com.boresjo.play.api.google.policyanalyzer

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://policyanalyzer.googleapis.com/"

	object organizations {
		object locations {
			object activityTypes {
				object activities {
					class query(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudPolicyanalyzerV1QueryActivityResponse]) {
						/** Optional. If present, then retrieve the next batch of results from the preceding call to this method. `pageToken` must be the value of `nextPageToken` from the previous response. The values of other method parameters should be identical to those in the previous call. */
						def withPageToken(pageToken: String) = new query(req.addQueryStringParameters("pageToken" -> pageToken.toString))
						/** Optional. The maximum number of results to return from this request. Max limit is 1000. Non-positive values are ignored. The presence of `nextPageToken` in the response indicates that more results might be available.<br>Format: int32 */
						def withPageSize(pageSize: Int) = new query(req.addQueryStringParameters("pageSize" -> pageSize.toString))
						/** Optional. Filter expression to restrict the activities returned. For serviceAccountLastAuthentication activities, supported filters are: - `activities.full_resource_name {=} [STRING]` - `activities.fullResourceName {=} [STRING]` where `[STRING]` is the full resource name of the service account. For serviceAccountKeyLastAuthentication activities, supported filters are: - `activities.full_resource_name {=} [STRING]` - `activities.fullResourceName {=} [STRING]` where `[STRING]` is the full resource name of the service account key. */
						def withFilter(filter: String) = new query(req.addQueryStringParameters("filter" -> filter.toString))
						def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudPolicyanalyzerV1QueryActivityResponse])
					}
					object query {
						def apply(organizationsId :PlayApi, locationsId :PlayApi, activityTypesId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): query = new query(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/locations/${locationsId}/activityTypes/${activityTypesId}/activities:query")).addQueryStringParameters("parent" -> parent.toString))
						given Conversion[query, Future[Schema.GoogleCloudPolicyanalyzerV1QueryActivityResponse]] = (fun: query) => fun.apply()
					}
				}
			}
		}
	}
	object folders {
		object locations {
			object activityTypes {
				object activities {
					class query(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudPolicyanalyzerV1QueryActivityResponse]) {
						/** Optional. If present, then retrieve the next batch of results from the preceding call to this method. `pageToken` must be the value of `nextPageToken` from the previous response. The values of other method parameters should be identical to those in the previous call. */
						def withPageToken(pageToken: String) = new query(req.addQueryStringParameters("pageToken" -> pageToken.toString))
						/** Optional. Filter expression to restrict the activities returned. For serviceAccountLastAuthentication activities, supported filters are: - `activities.full_resource_name {=} [STRING]` - `activities.fullResourceName {=} [STRING]` where `[STRING]` is the full resource name of the service account. For serviceAccountKeyLastAuthentication activities, supported filters are: - `activities.full_resource_name {=} [STRING]` - `activities.fullResourceName {=} [STRING]` where `[STRING]` is the full resource name of the service account key. */
						def withFilter(filter: String) = new query(req.addQueryStringParameters("filter" -> filter.toString))
						/** Optional. The maximum number of results to return from this request. Max limit is 1000. Non-positive values are ignored. The presence of `nextPageToken` in the response indicates that more results might be available.<br>Format: int32 */
						def withPageSize(pageSize: Int) = new query(req.addQueryStringParameters("pageSize" -> pageSize.toString))
						def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudPolicyanalyzerV1QueryActivityResponse])
					}
					object query {
						def apply(foldersId :PlayApi, locationsId :PlayApi, activityTypesId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): query = new query(auth(ws.url(BASE_URL + s"v1/folders/${foldersId}/locations/${locationsId}/activityTypes/${activityTypesId}/activities:query")).addQueryStringParameters("parent" -> parent.toString))
						given Conversion[query, Future[Schema.GoogleCloudPolicyanalyzerV1QueryActivityResponse]] = (fun: query) => fun.apply()
					}
				}
			}
		}
	}
	object projects {
		object locations {
			object activityTypes {
				object activities {
					class query(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudPolicyanalyzerV1QueryActivityResponse]) {
						/** Optional. The maximum number of results to return from this request. Max limit is 1000. Non-positive values are ignored. The presence of `nextPageToken` in the response indicates that more results might be available.<br>Format: int32 */
						def withPageSize(pageSize: Int) = new query(req.addQueryStringParameters("pageSize" -> pageSize.toString))
						/** Optional. If present, then retrieve the next batch of results from the preceding call to this method. `pageToken` must be the value of `nextPageToken` from the previous response. The values of other method parameters should be identical to those in the previous call. */
						def withPageToken(pageToken: String) = new query(req.addQueryStringParameters("pageToken" -> pageToken.toString))
						/** Optional. Filter expression to restrict the activities returned. For serviceAccountLastAuthentication activities, supported filters are: - `activities.full_resource_name {=} [STRING]` - `activities.fullResourceName {=} [STRING]` where `[STRING]` is the full resource name of the service account. For serviceAccountKeyLastAuthentication activities, supported filters are: - `activities.full_resource_name {=} [STRING]` - `activities.fullResourceName {=} [STRING]` where `[STRING]` is the full resource name of the service account key. */
						def withFilter(filter: String) = new query(req.addQueryStringParameters("filter" -> filter.toString))
						def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudPolicyanalyzerV1QueryActivityResponse])
					}
					object query {
						def apply(projectsId :PlayApi, locationsId :PlayApi, activityTypesId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): query = new query(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/activityTypes/${activityTypesId}/activities:query")).addQueryStringParameters("parent" -> parent.toString))
						given Conversion[query, Future[Schema.GoogleCloudPolicyanalyzerV1QueryActivityResponse]] = (fun: query) => fun.apply()
					}
				}
			}
		}
	}
}
