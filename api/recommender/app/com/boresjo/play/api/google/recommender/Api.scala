package com.boresjo.play.api.google.recommender

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://recommender.googleapis.com/"

	object folders {
		object locations {
			object recommenders {
				object recommendations {
					class markClaimed(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleCloudRecommenderV1MarkRecommendationClaimedRequest(body: Schema.GoogleCloudRecommenderV1MarkRecommendationClaimedRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleCloudRecommenderV1Recommendation])
					}
					object markClaimed {
						def apply(foldersId :PlayApi, locationsId :PlayApi, recommendersId :PlayApi, recommendationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): markClaimed = new markClaimed(auth(ws.url(BASE_URL + s"v1/folders/${foldersId}/locations/${locationsId}/recommenders/${recommendersId}/recommendations/${recommendationsId}:markClaimed")).addQueryStringParameters("name" -> name.toString))
					}
					class markSucceeded(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleCloudRecommenderV1MarkRecommendationSucceededRequest(body: Schema.GoogleCloudRecommenderV1MarkRecommendationSucceededRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleCloudRecommenderV1Recommendation])
					}
					object markSucceeded {
						def apply(foldersId :PlayApi, locationsId :PlayApi, recommendersId :PlayApi, recommendationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): markSucceeded = new markSucceeded(auth(ws.url(BASE_URL + s"v1/folders/${foldersId}/locations/${locationsId}/recommenders/${recommendersId}/recommendations/${recommendationsId}:markSucceeded")).addQueryStringParameters("name" -> name.toString))
					}
					class markDismissed(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleCloudRecommenderV1MarkRecommendationDismissedRequest(body: Schema.GoogleCloudRecommenderV1MarkRecommendationDismissedRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleCloudRecommenderV1Recommendation])
					}
					object markDismissed {
						def apply(foldersId :PlayApi, locationsId :PlayApi, recommendersId :PlayApi, recommendationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): markDismissed = new markDismissed(auth(ws.url(BASE_URL + s"v1/folders/${foldersId}/locations/${locationsId}/recommenders/${recommendersId}/recommendations/${recommendationsId}:markDismissed")).addQueryStringParameters("name" -> name.toString))
					}
					class markFailed(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleCloudRecommenderV1MarkRecommendationFailedRequest(body: Schema.GoogleCloudRecommenderV1MarkRecommendationFailedRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleCloudRecommenderV1Recommendation])
					}
					object markFailed {
						def apply(foldersId :PlayApi, locationsId :PlayApi, recommendersId :PlayApi, recommendationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): markFailed = new markFailed(auth(ws.url(BASE_URL + s"v1/folders/${foldersId}/locations/${locationsId}/recommenders/${recommendersId}/recommendations/${recommendationsId}:markFailed")).addQueryStringParameters("name" -> name.toString))
					}
					class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudRecommenderV1Recommendation]) {
						def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudRecommenderV1Recommendation])
					}
					object get {
						def apply(foldersId :PlayApi, locationsId :PlayApi, recommendersId :PlayApi, recommendationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/folders/${foldersId}/locations/${locationsId}/recommenders/${recommendersId}/recommendations/${recommendationsId}")).addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.GoogleCloudRecommenderV1Recommendation]] = (fun: get) => fun.apply()
					}
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudRecommenderV1ListRecommendationsResponse]) {
						/** Optional. The maximum number of results to return from this request. Non-positive values are ignored. If not specified, the server will determine the number of results to return.<br>Format: int32 */
						def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
						/** Optional. If present, retrieves the next batch of results from the preceding call to this method. `page_token` must be the value of `next_page_token` from the previous response. The values of other method parameters must be identical to those in the previous call. */
						def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
						def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudRecommenderV1ListRecommendationsResponse])
					}
					object list {
						def apply(foldersId :PlayApi, locationsId :PlayApi, recommendersId :PlayApi, parent: String, filter: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/folders/${foldersId}/locations/${locationsId}/recommenders/${recommendersId}/recommendations")).addQueryStringParameters("parent" -> parent.toString, "filter" -> filter.toString))
						given Conversion[list, Future[Schema.GoogleCloudRecommenderV1ListRecommendationsResponse]] = (fun: list) => fun.apply()
					}
				}
			}
			object insightTypes {
				object insights {
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudRecommenderV1ListInsightsResponse]) {
						/** Optional. If present, retrieves the next batch of results from the preceding call to this method. `page_token` must be the value of `next_page_token` from the previous response. The values of other method parameters must be identical to those in the previous call. */
						def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
						/** Optional. The maximum number of results to return from this request. Non-positive values are ignored. If not specified, the server will determine the number of results to return.<br>Format: int32 */
						def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
						/** Optional. Filter expression to restrict the insights returned. Supported filter fields: &#42; `stateInfo.state` &#42; `insightSubtype` &#42; `severity` &#42; `targetResources` Examples: &#42; `stateInfo.state = ACTIVE OR stateInfo.state = DISMISSED` &#42; `insightSubtype = PERMISSIONS_USAGE` &#42; `severity = CRITICAL OR severity = HIGH` &#42; `targetResources : //compute.googleapis.com/projects/1234/zones/us-central1-a/instances/instance-1` &#42; `stateInfo.state = ACTIVE AND (severity = CRITICAL OR severity = HIGH)` The max allowed filter length is 500 characters. (These expressions are based on the filter language described at https://google.aip.dev/160) */
						def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
						def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudRecommenderV1ListInsightsResponse])
					}
					object list {
						def apply(foldersId :PlayApi, locationsId :PlayApi, insightTypesId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/folders/${foldersId}/locations/${locationsId}/insightTypes/${insightTypesId}/insights")).addQueryStringParameters("parent" -> parent.toString))
						given Conversion[list, Future[Schema.GoogleCloudRecommenderV1ListInsightsResponse]] = (fun: list) => fun.apply()
					}
					class markAccepted(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleCloudRecommenderV1MarkInsightAcceptedRequest(body: Schema.GoogleCloudRecommenderV1MarkInsightAcceptedRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleCloudRecommenderV1Insight])
					}
					object markAccepted {
						def apply(foldersId :PlayApi, locationsId :PlayApi, insightTypesId :PlayApi, insightsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): markAccepted = new markAccepted(auth(ws.url(BASE_URL + s"v1/folders/${foldersId}/locations/${locationsId}/insightTypes/${insightTypesId}/insights/${insightsId}:markAccepted")).addQueryStringParameters("name" -> name.toString))
					}
					class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudRecommenderV1Insight]) {
						def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudRecommenderV1Insight])
					}
					object get {
						def apply(foldersId :PlayApi, locationsId :PlayApi, insightTypesId :PlayApi, insightsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/folders/${foldersId}/locations/${locationsId}/insightTypes/${insightTypesId}/insights/${insightsId}")).addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.GoogleCloudRecommenderV1Insight]] = (fun: get) => fun.apply()
					}
				}
			}
		}
	}
	object organizations {
		object locations {
			object insightTypes {
				class getConfig(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudRecommenderV1InsightTypeConfig]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudRecommenderV1InsightTypeConfig])
				}
				object getConfig {
					def apply(organizationsId :PlayApi, locationsId :PlayApi, insightTypesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): getConfig = new getConfig(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/locations/${locationsId}/insightTypes/${insightTypesId}/config")).addQueryStringParameters("name" -> name.toString))
					given Conversion[getConfig, Future[Schema.GoogleCloudRecommenderV1InsightTypeConfig]] = (fun: getConfig) => fun.apply()
				}
				class updateConfig(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudRecommenderV1InsightTypeConfig(body: Schema.GoogleCloudRecommenderV1InsightTypeConfig) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.GoogleCloudRecommenderV1InsightTypeConfig])
				}
				object updateConfig {
					def apply(organizationsId :PlayApi, locationsId :PlayApi, insightTypesId :PlayApi, validateOnly: Boolean, updateMask: String, name: String)(using auth: AuthToken, ec: ExecutionContext): updateConfig = new updateConfig(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/locations/${locationsId}/insightTypes/${insightTypesId}/config")).addQueryStringParameters("validateOnly" -> validateOnly.toString, "updateMask" -> updateMask.toString, "name" -> name.toString))
				}
				object insights {
					class markAccepted(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleCloudRecommenderV1MarkInsightAcceptedRequest(body: Schema.GoogleCloudRecommenderV1MarkInsightAcceptedRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleCloudRecommenderV1Insight])
					}
					object markAccepted {
						def apply(organizationsId :PlayApi, locationsId :PlayApi, insightTypesId :PlayApi, insightsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): markAccepted = new markAccepted(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/locations/${locationsId}/insightTypes/${insightTypesId}/insights/${insightsId}:markAccepted")).addQueryStringParameters("name" -> name.toString))
					}
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudRecommenderV1ListInsightsResponse]) {
						/** Optional. The maximum number of results to return from this request. Non-positive values are ignored. If not specified, the server will determine the number of results to return.<br>Format: int32 */
						def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
						/** Optional. Filter expression to restrict the insights returned. Supported filter fields: &#42; `stateInfo.state` &#42; `insightSubtype` &#42; `severity` &#42; `targetResources` Examples: &#42; `stateInfo.state = ACTIVE OR stateInfo.state = DISMISSED` &#42; `insightSubtype = PERMISSIONS_USAGE` &#42; `severity = CRITICAL OR severity = HIGH` &#42; `targetResources : //compute.googleapis.com/projects/1234/zones/us-central1-a/instances/instance-1` &#42; `stateInfo.state = ACTIVE AND (severity = CRITICAL OR severity = HIGH)` The max allowed filter length is 500 characters. (These expressions are based on the filter language described at https://google.aip.dev/160) */
						def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
						/** Optional. If present, retrieves the next batch of results from the preceding call to this method. `page_token` must be the value of `next_page_token` from the previous response. The values of other method parameters must be identical to those in the previous call. */
						def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
						def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudRecommenderV1ListInsightsResponse])
					}
					object list {
						def apply(organizationsId :PlayApi, locationsId :PlayApi, insightTypesId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/locations/${locationsId}/insightTypes/${insightTypesId}/insights")).addQueryStringParameters("parent" -> parent.toString))
						given Conversion[list, Future[Schema.GoogleCloudRecommenderV1ListInsightsResponse]] = (fun: list) => fun.apply()
					}
					class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudRecommenderV1Insight]) {
						def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudRecommenderV1Insight])
					}
					object get {
						def apply(organizationsId :PlayApi, locationsId :PlayApi, insightTypesId :PlayApi, insightsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/locations/${locationsId}/insightTypes/${insightTypesId}/insights/${insightsId}")).addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.GoogleCloudRecommenderV1Insight]] = (fun: get) => fun.apply()
					}
				}
			}
			object recommenders {
				class updateConfig(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudRecommenderV1RecommenderConfig(body: Schema.GoogleCloudRecommenderV1RecommenderConfig) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.GoogleCloudRecommenderV1RecommenderConfig])
				}
				object updateConfig {
					def apply(organizationsId :PlayApi, locationsId :PlayApi, recommendersId :PlayApi, validateOnly: Boolean, updateMask: String, name: String)(using auth: AuthToken, ec: ExecutionContext): updateConfig = new updateConfig(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/locations/${locationsId}/recommenders/${recommendersId}/config")).addQueryStringParameters("validateOnly" -> validateOnly.toString, "updateMask" -> updateMask.toString, "name" -> name.toString))
				}
				class getConfig(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudRecommenderV1RecommenderConfig]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudRecommenderV1RecommenderConfig])
				}
				object getConfig {
					def apply(organizationsId :PlayApi, locationsId :PlayApi, recommendersId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): getConfig = new getConfig(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/locations/${locationsId}/recommenders/${recommendersId}/config")).addQueryStringParameters("name" -> name.toString))
					given Conversion[getConfig, Future[Schema.GoogleCloudRecommenderV1RecommenderConfig]] = (fun: getConfig) => fun.apply()
				}
				object recommendations {
					class markClaimed(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleCloudRecommenderV1MarkRecommendationClaimedRequest(body: Schema.GoogleCloudRecommenderV1MarkRecommendationClaimedRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleCloudRecommenderV1Recommendation])
					}
					object markClaimed {
						def apply(organizationsId :PlayApi, locationsId :PlayApi, recommendersId :PlayApi, recommendationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): markClaimed = new markClaimed(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/locations/${locationsId}/recommenders/${recommendersId}/recommendations/${recommendationsId}:markClaimed")).addQueryStringParameters("name" -> name.toString))
					}
					class markSucceeded(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleCloudRecommenderV1MarkRecommendationSucceededRequest(body: Schema.GoogleCloudRecommenderV1MarkRecommendationSucceededRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleCloudRecommenderV1Recommendation])
					}
					object markSucceeded {
						def apply(organizationsId :PlayApi, locationsId :PlayApi, recommendersId :PlayApi, recommendationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): markSucceeded = new markSucceeded(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/locations/${locationsId}/recommenders/${recommendersId}/recommendations/${recommendationsId}:markSucceeded")).addQueryStringParameters("name" -> name.toString))
					}
					class markDismissed(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleCloudRecommenderV1MarkRecommendationDismissedRequest(body: Schema.GoogleCloudRecommenderV1MarkRecommendationDismissedRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleCloudRecommenderV1Recommendation])
					}
					object markDismissed {
						def apply(organizationsId :PlayApi, locationsId :PlayApi, recommendersId :PlayApi, recommendationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): markDismissed = new markDismissed(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/locations/${locationsId}/recommenders/${recommendersId}/recommendations/${recommendationsId}:markDismissed")).addQueryStringParameters("name" -> name.toString))
					}
					class markFailed(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleCloudRecommenderV1MarkRecommendationFailedRequest(body: Schema.GoogleCloudRecommenderV1MarkRecommendationFailedRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleCloudRecommenderV1Recommendation])
					}
					object markFailed {
						def apply(organizationsId :PlayApi, locationsId :PlayApi, recommendersId :PlayApi, recommendationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): markFailed = new markFailed(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/locations/${locationsId}/recommenders/${recommendersId}/recommendations/${recommendationsId}:markFailed")).addQueryStringParameters("name" -> name.toString))
					}
					class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudRecommenderV1Recommendation]) {
						def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudRecommenderV1Recommendation])
					}
					object get {
						def apply(organizationsId :PlayApi, locationsId :PlayApi, recommendersId :PlayApi, recommendationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/locations/${locationsId}/recommenders/${recommendersId}/recommendations/${recommendationsId}")).addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.GoogleCloudRecommenderV1Recommendation]] = (fun: get) => fun.apply()
					}
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudRecommenderV1ListRecommendationsResponse]) {
						/** Optional. If present, retrieves the next batch of results from the preceding call to this method. `page_token` must be the value of `next_page_token` from the previous response. The values of other method parameters must be identical to those in the previous call. */
						def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
						/** Optional. The maximum number of results to return from this request. Non-positive values are ignored. If not specified, the server will determine the number of results to return.<br>Format: int32 */
						def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
						def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudRecommenderV1ListRecommendationsResponse])
					}
					object list {
						def apply(organizationsId :PlayApi, locationsId :PlayApi, recommendersId :PlayApi, parent: String, filter: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/locations/${locationsId}/recommenders/${recommendersId}/recommendations")).addQueryStringParameters("parent" -> parent.toString, "filter" -> filter.toString))
						given Conversion[list, Future[Schema.GoogleCloudRecommenderV1ListRecommendationsResponse]] = (fun: list) => fun.apply()
					}
				}
			}
		}
	}
	object billingAccounts {
		object locations {
			object insightTypes {
				class updateConfig(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudRecommenderV1InsightTypeConfig(body: Schema.GoogleCloudRecommenderV1InsightTypeConfig) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.GoogleCloudRecommenderV1InsightTypeConfig])
				}
				object updateConfig {
					def apply(billingAccountsId :PlayApi, locationsId :PlayApi, insightTypesId :PlayApi, name: String, updateMask: String, validateOnly: Boolean)(using auth: AuthToken, ec: ExecutionContext): updateConfig = new updateConfig(auth(ws.url(BASE_URL + s"v1/billingAccounts/${billingAccountsId}/locations/${locationsId}/insightTypes/${insightTypesId}/config")).addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString, "validateOnly" -> validateOnly.toString))
				}
				class getConfig(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudRecommenderV1InsightTypeConfig]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudRecommenderV1InsightTypeConfig])
				}
				object getConfig {
					def apply(billingAccountsId :PlayApi, locationsId :PlayApi, insightTypesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): getConfig = new getConfig(auth(ws.url(BASE_URL + s"v1/billingAccounts/${billingAccountsId}/locations/${locationsId}/insightTypes/${insightTypesId}/config")).addQueryStringParameters("name" -> name.toString))
					given Conversion[getConfig, Future[Schema.GoogleCloudRecommenderV1InsightTypeConfig]] = (fun: getConfig) => fun.apply()
				}
				object insights {
					class markAccepted(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleCloudRecommenderV1MarkInsightAcceptedRequest(body: Schema.GoogleCloudRecommenderV1MarkInsightAcceptedRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleCloudRecommenderV1Insight])
					}
					object markAccepted {
						def apply(billingAccountsId :PlayApi, locationsId :PlayApi, insightTypesId :PlayApi, insightsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): markAccepted = new markAccepted(auth(ws.url(BASE_URL + s"v1/billingAccounts/${billingAccountsId}/locations/${locationsId}/insightTypes/${insightTypesId}/insights/${insightsId}:markAccepted")).addQueryStringParameters("name" -> name.toString))
					}
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudRecommenderV1ListInsightsResponse]) {
						/** Optional. The maximum number of results to return from this request. Non-positive values are ignored. If not specified, the server will determine the number of results to return.<br>Format: int32 */
						def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
						/** Optional. Filter expression to restrict the insights returned. Supported filter fields: &#42; `stateInfo.state` &#42; `insightSubtype` &#42; `severity` &#42; `targetResources` Examples: &#42; `stateInfo.state = ACTIVE OR stateInfo.state = DISMISSED` &#42; `insightSubtype = PERMISSIONS_USAGE` &#42; `severity = CRITICAL OR severity = HIGH` &#42; `targetResources : //compute.googleapis.com/projects/1234/zones/us-central1-a/instances/instance-1` &#42; `stateInfo.state = ACTIVE AND (severity = CRITICAL OR severity = HIGH)` The max allowed filter length is 500 characters. (These expressions are based on the filter language described at https://google.aip.dev/160) */
						def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
						/** Optional. If present, retrieves the next batch of results from the preceding call to this method. `page_token` must be the value of `next_page_token` from the previous response. The values of other method parameters must be identical to those in the previous call. */
						def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
						def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudRecommenderV1ListInsightsResponse])
					}
					object list {
						def apply(billingAccountsId :PlayApi, locationsId :PlayApi, insightTypesId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/billingAccounts/${billingAccountsId}/locations/${locationsId}/insightTypes/${insightTypesId}/insights")).addQueryStringParameters("parent" -> parent.toString))
						given Conversion[list, Future[Schema.GoogleCloudRecommenderV1ListInsightsResponse]] = (fun: list) => fun.apply()
					}
					class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudRecommenderV1Insight]) {
						def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudRecommenderV1Insight])
					}
					object get {
						def apply(billingAccountsId :PlayApi, locationsId :PlayApi, insightTypesId :PlayApi, insightsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/billingAccounts/${billingAccountsId}/locations/${locationsId}/insightTypes/${insightTypesId}/insights/${insightsId}")).addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.GoogleCloudRecommenderV1Insight]] = (fun: get) => fun.apply()
					}
				}
			}
			object recommenders {
				class updateConfig(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudRecommenderV1RecommenderConfig(body: Schema.GoogleCloudRecommenderV1RecommenderConfig) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.GoogleCloudRecommenderV1RecommenderConfig])
				}
				object updateConfig {
					def apply(billingAccountsId :PlayApi, locationsId :PlayApi, recommendersId :PlayApi, updateMask: String, validateOnly: Boolean, name: String)(using auth: AuthToken, ec: ExecutionContext): updateConfig = new updateConfig(auth(ws.url(BASE_URL + s"v1/billingAccounts/${billingAccountsId}/locations/${locationsId}/recommenders/${recommendersId}/config")).addQueryStringParameters("updateMask" -> updateMask.toString, "validateOnly" -> validateOnly.toString, "name" -> name.toString))
				}
				class getConfig(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudRecommenderV1RecommenderConfig]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudRecommenderV1RecommenderConfig])
				}
				object getConfig {
					def apply(billingAccountsId :PlayApi, locationsId :PlayApi, recommendersId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): getConfig = new getConfig(auth(ws.url(BASE_URL + s"v1/billingAccounts/${billingAccountsId}/locations/${locationsId}/recommenders/${recommendersId}/config")).addQueryStringParameters("name" -> name.toString))
					given Conversion[getConfig, Future[Schema.GoogleCloudRecommenderV1RecommenderConfig]] = (fun: getConfig) => fun.apply()
				}
				object recommendations {
					class markClaimed(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleCloudRecommenderV1MarkRecommendationClaimedRequest(body: Schema.GoogleCloudRecommenderV1MarkRecommendationClaimedRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleCloudRecommenderV1Recommendation])
					}
					object markClaimed {
						def apply(billingAccountsId :PlayApi, locationsId :PlayApi, recommendersId :PlayApi, recommendationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): markClaimed = new markClaimed(auth(ws.url(BASE_URL + s"v1/billingAccounts/${billingAccountsId}/locations/${locationsId}/recommenders/${recommendersId}/recommendations/${recommendationsId}:markClaimed")).addQueryStringParameters("name" -> name.toString))
					}
					class markSucceeded(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleCloudRecommenderV1MarkRecommendationSucceededRequest(body: Schema.GoogleCloudRecommenderV1MarkRecommendationSucceededRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleCloudRecommenderV1Recommendation])
					}
					object markSucceeded {
						def apply(billingAccountsId :PlayApi, locationsId :PlayApi, recommendersId :PlayApi, recommendationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): markSucceeded = new markSucceeded(auth(ws.url(BASE_URL + s"v1/billingAccounts/${billingAccountsId}/locations/${locationsId}/recommenders/${recommendersId}/recommendations/${recommendationsId}:markSucceeded")).addQueryStringParameters("name" -> name.toString))
					}
					class markDismissed(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleCloudRecommenderV1MarkRecommendationDismissedRequest(body: Schema.GoogleCloudRecommenderV1MarkRecommendationDismissedRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleCloudRecommenderV1Recommendation])
					}
					object markDismissed {
						def apply(billingAccountsId :PlayApi, locationsId :PlayApi, recommendersId :PlayApi, recommendationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): markDismissed = new markDismissed(auth(ws.url(BASE_URL + s"v1/billingAccounts/${billingAccountsId}/locations/${locationsId}/recommenders/${recommendersId}/recommendations/${recommendationsId}:markDismissed")).addQueryStringParameters("name" -> name.toString))
					}
					class markFailed(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleCloudRecommenderV1MarkRecommendationFailedRequest(body: Schema.GoogleCloudRecommenderV1MarkRecommendationFailedRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleCloudRecommenderV1Recommendation])
					}
					object markFailed {
						def apply(billingAccountsId :PlayApi, locationsId :PlayApi, recommendersId :PlayApi, recommendationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): markFailed = new markFailed(auth(ws.url(BASE_URL + s"v1/billingAccounts/${billingAccountsId}/locations/${locationsId}/recommenders/${recommendersId}/recommendations/${recommendationsId}:markFailed")).addQueryStringParameters("name" -> name.toString))
					}
					class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudRecommenderV1Recommendation]) {
						def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudRecommenderV1Recommendation])
					}
					object get {
						def apply(billingAccountsId :PlayApi, locationsId :PlayApi, recommendersId :PlayApi, recommendationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/billingAccounts/${billingAccountsId}/locations/${locationsId}/recommenders/${recommendersId}/recommendations/${recommendationsId}")).addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.GoogleCloudRecommenderV1Recommendation]] = (fun: get) => fun.apply()
					}
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudRecommenderV1ListRecommendationsResponse]) {
						/** Optional. If present, retrieves the next batch of results from the preceding call to this method. `page_token` must be the value of `next_page_token` from the previous response. The values of other method parameters must be identical to those in the previous call. */
						def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
						/** Optional. The maximum number of results to return from this request. Non-positive values are ignored. If not specified, the server will determine the number of results to return.<br>Format: int32 */
						def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
						def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudRecommenderV1ListRecommendationsResponse])
					}
					object list {
						def apply(billingAccountsId :PlayApi, locationsId :PlayApi, recommendersId :PlayApi, filter: String, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/billingAccounts/${billingAccountsId}/locations/${locationsId}/recommenders/${recommendersId}/recommendations")).addQueryStringParameters("filter" -> filter.toString, "parent" -> parent.toString))
						given Conversion[list, Future[Schema.GoogleCloudRecommenderV1ListRecommendationsResponse]] = (fun: list) => fun.apply()
					}
				}
			}
		}
	}
	object projects {
		object locations {
			object insightTypes {
				class updateConfig(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudRecommenderV1InsightTypeConfig(body: Schema.GoogleCloudRecommenderV1InsightTypeConfig) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.GoogleCloudRecommenderV1InsightTypeConfig])
				}
				object updateConfig {
					def apply(projectsId :PlayApi, locationsId :PlayApi, insightTypesId :PlayApi, updateMask: String, name: String, validateOnly: Boolean)(using auth: AuthToken, ec: ExecutionContext): updateConfig = new updateConfig(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/insightTypes/${insightTypesId}/config")).addQueryStringParameters("updateMask" -> updateMask.toString, "name" -> name.toString, "validateOnly" -> validateOnly.toString))
				}
				class getConfig(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudRecommenderV1InsightTypeConfig]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudRecommenderV1InsightTypeConfig])
				}
				object getConfig {
					def apply(projectsId :PlayApi, locationsId :PlayApi, insightTypesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): getConfig = new getConfig(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/insightTypes/${insightTypesId}/config")).addQueryStringParameters("name" -> name.toString))
					given Conversion[getConfig, Future[Schema.GoogleCloudRecommenderV1InsightTypeConfig]] = (fun: getConfig) => fun.apply()
				}
				object insights {
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudRecommenderV1ListInsightsResponse]) {
						/** Optional. Filter expression to restrict the insights returned. Supported filter fields: &#42; `stateInfo.state` &#42; `insightSubtype` &#42; `severity` &#42; `targetResources` Examples: &#42; `stateInfo.state = ACTIVE OR stateInfo.state = DISMISSED` &#42; `insightSubtype = PERMISSIONS_USAGE` &#42; `severity = CRITICAL OR severity = HIGH` &#42; `targetResources : //compute.googleapis.com/projects/1234/zones/us-central1-a/instances/instance-1` &#42; `stateInfo.state = ACTIVE AND (severity = CRITICAL OR severity = HIGH)` The max allowed filter length is 500 characters. (These expressions are based on the filter language described at https://google.aip.dev/160) */
						def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
						/** Optional. If present, retrieves the next batch of results from the preceding call to this method. `page_token` must be the value of `next_page_token` from the previous response. The values of other method parameters must be identical to those in the previous call. */
						def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
						/** Optional. The maximum number of results to return from this request. Non-positive values are ignored. If not specified, the server will determine the number of results to return.<br>Format: int32 */
						def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
						def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudRecommenderV1ListInsightsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, insightTypesId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/insightTypes/${insightTypesId}/insights")).addQueryStringParameters("parent" -> parent.toString))
						given Conversion[list, Future[Schema.GoogleCloudRecommenderV1ListInsightsResponse]] = (fun: list) => fun.apply()
					}
					class markAccepted(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleCloudRecommenderV1MarkInsightAcceptedRequest(body: Schema.GoogleCloudRecommenderV1MarkInsightAcceptedRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleCloudRecommenderV1Insight])
					}
					object markAccepted {
						def apply(projectsId :PlayApi, locationsId :PlayApi, insightTypesId :PlayApi, insightsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): markAccepted = new markAccepted(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/insightTypes/${insightTypesId}/insights/${insightsId}:markAccepted")).addQueryStringParameters("name" -> name.toString))
					}
					class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudRecommenderV1Insight]) {
						def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudRecommenderV1Insight])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, insightTypesId :PlayApi, insightsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/insightTypes/${insightTypesId}/insights/${insightsId}")).addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.GoogleCloudRecommenderV1Insight]] = (fun: get) => fun.apply()
					}
				}
			}
			object recommenders {
				class updateConfig(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudRecommenderV1RecommenderConfig(body: Schema.GoogleCloudRecommenderV1RecommenderConfig) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.GoogleCloudRecommenderV1RecommenderConfig])
				}
				object updateConfig {
					def apply(projectsId :PlayApi, locationsId :PlayApi, recommendersId :PlayApi, name: String, updateMask: String, validateOnly: Boolean)(using auth: AuthToken, ec: ExecutionContext): updateConfig = new updateConfig(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/recommenders/${recommendersId}/config")).addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString, "validateOnly" -> validateOnly.toString))
				}
				class getConfig(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudRecommenderV1RecommenderConfig]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudRecommenderV1RecommenderConfig])
				}
				object getConfig {
					def apply(projectsId :PlayApi, locationsId :PlayApi, recommendersId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): getConfig = new getConfig(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/recommenders/${recommendersId}/config")).addQueryStringParameters("name" -> name.toString))
					given Conversion[getConfig, Future[Schema.GoogleCloudRecommenderV1RecommenderConfig]] = (fun: getConfig) => fun.apply()
				}
				object recommendations {
					class markClaimed(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleCloudRecommenderV1MarkRecommendationClaimedRequest(body: Schema.GoogleCloudRecommenderV1MarkRecommendationClaimedRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleCloudRecommenderV1Recommendation])
					}
					object markClaimed {
						def apply(projectsId :PlayApi, locationsId :PlayApi, recommendersId :PlayApi, recommendationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): markClaimed = new markClaimed(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/recommenders/${recommendersId}/recommendations/${recommendationsId}:markClaimed")).addQueryStringParameters("name" -> name.toString))
					}
					class markSucceeded(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleCloudRecommenderV1MarkRecommendationSucceededRequest(body: Schema.GoogleCloudRecommenderV1MarkRecommendationSucceededRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleCloudRecommenderV1Recommendation])
					}
					object markSucceeded {
						def apply(projectsId :PlayApi, locationsId :PlayApi, recommendersId :PlayApi, recommendationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): markSucceeded = new markSucceeded(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/recommenders/${recommendersId}/recommendations/${recommendationsId}:markSucceeded")).addQueryStringParameters("name" -> name.toString))
					}
					class markDismissed(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleCloudRecommenderV1MarkRecommendationDismissedRequest(body: Schema.GoogleCloudRecommenderV1MarkRecommendationDismissedRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleCloudRecommenderV1Recommendation])
					}
					object markDismissed {
						def apply(projectsId :PlayApi, locationsId :PlayApi, recommendersId :PlayApi, recommendationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): markDismissed = new markDismissed(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/recommenders/${recommendersId}/recommendations/${recommendationsId}:markDismissed")).addQueryStringParameters("name" -> name.toString))
					}
					class markFailed(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleCloudRecommenderV1MarkRecommendationFailedRequest(body: Schema.GoogleCloudRecommenderV1MarkRecommendationFailedRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleCloudRecommenderV1Recommendation])
					}
					object markFailed {
						def apply(projectsId :PlayApi, locationsId :PlayApi, recommendersId :PlayApi, recommendationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): markFailed = new markFailed(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/recommenders/${recommendersId}/recommendations/${recommendationsId}:markFailed")).addQueryStringParameters("name" -> name.toString))
					}
					class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudRecommenderV1Recommendation]) {
						def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudRecommenderV1Recommendation])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, recommendersId :PlayApi, recommendationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/recommenders/${recommendersId}/recommendations/${recommendationsId}")).addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.GoogleCloudRecommenderV1Recommendation]] = (fun: get) => fun.apply()
					}
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudRecommenderV1ListRecommendationsResponse]) {
						/** Optional. The maximum number of results to return from this request. Non-positive values are ignored. If not specified, the server will determine the number of results to return.<br>Format: int32 */
						def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
						/** Optional. If present, retrieves the next batch of results from the preceding call to this method. `page_token` must be the value of `next_page_token` from the previous response. The values of other method parameters must be identical to those in the previous call. */
						def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
						def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudRecommenderV1ListRecommendationsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, recommendersId :PlayApi, parent: String, filter: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/recommenders/${recommendersId}/recommendations")).addQueryStringParameters("parent" -> parent.toString, "filter" -> filter.toString))
						given Conversion[list, Future[Schema.GoogleCloudRecommenderV1ListRecommendationsResponse]] = (fun: list) => fun.apply()
					}
				}
			}
		}
	}
}
