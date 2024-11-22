package com.boresjo.play.api.google.recommender

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
		"""https://www.googleapis.com/auth/cloud-platform""" /* See, edit, configure, and delete your Google Cloud data and see the email address for your Google Account. */
	)

	private val BASE_URL = "https://recommender.googleapis.com/"

	object folders {
		object locations {
			object recommenders {
				object recommendations {
					/** Marks the Recommendation State as Claimed. Users can use this method to indicate to the Recommender API that they are starting to apply the recommendation themselves. This stops the recommendation content from being updated. Associated insights are frozen and placed in the ACCEPTED state. MarkRecommendationClaimed can be applied to recommendations in CLAIMED, SUCCEEDED, FAILED, or ACTIVE state. Requires the recommender.&#42;.update IAM permission for the specified recommender. */
					class markClaimed(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withGoogleCloudRecommenderV1MarkRecommendationClaimedRequest(body: Schema.GoogleCloudRecommenderV1MarkRecommendationClaimedRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudRecommenderV1Recommendation])
					}
					object markClaimed {
						def apply(foldersId :PlayApi, locationsId :PlayApi, recommendersId :PlayApi, recommendationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): markClaimed = new markClaimed(ws.url(BASE_URL + s"v1/folders/${foldersId}/locations/${locationsId}/recommenders/${recommendersId}/recommendations/${recommendationsId}:markClaimed").addQueryStringParameters("name" -> name.toString))
					}
					/** Marks the Recommendation State as Succeeded. Users can use this method to indicate to the Recommender API that they have applied the recommendation themselves, and the operation was successful. This stops the recommendation content from being updated. Associated insights are frozen and placed in the ACCEPTED state. MarkRecommendationSucceeded can be applied to recommendations in ACTIVE, CLAIMED, SUCCEEDED, or FAILED state. Requires the recommender.&#42;.update IAM permission for the specified recommender. */
					class markSucceeded(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withGoogleCloudRecommenderV1MarkRecommendationSucceededRequest(body: Schema.GoogleCloudRecommenderV1MarkRecommendationSucceededRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudRecommenderV1Recommendation])
					}
					object markSucceeded {
						def apply(foldersId :PlayApi, locationsId :PlayApi, recommendersId :PlayApi, recommendationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): markSucceeded = new markSucceeded(ws.url(BASE_URL + s"v1/folders/${foldersId}/locations/${locationsId}/recommenders/${recommendersId}/recommendations/${recommendationsId}:markSucceeded").addQueryStringParameters("name" -> name.toString))
					}
					/** Mark the Recommendation State as Dismissed. Users can use this method to indicate to the Recommender API that an ACTIVE recommendation has to be marked back as DISMISSED. MarkRecommendationDismissed can be applied to recommendations in ACTIVE state. Requires the recommender.&#42;.update IAM permission for the specified recommender. */
					class markDismissed(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withGoogleCloudRecommenderV1MarkRecommendationDismissedRequest(body: Schema.GoogleCloudRecommenderV1MarkRecommendationDismissedRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudRecommenderV1Recommendation])
					}
					object markDismissed {
						def apply(foldersId :PlayApi, locationsId :PlayApi, recommendersId :PlayApi, recommendationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): markDismissed = new markDismissed(ws.url(BASE_URL + s"v1/folders/${foldersId}/locations/${locationsId}/recommenders/${recommendersId}/recommendations/${recommendationsId}:markDismissed").addQueryStringParameters("name" -> name.toString))
					}
					/** Marks the Recommendation State as Failed. Users can use this method to indicate to the Recommender API that they have applied the recommendation themselves, and the operation failed. This stops the recommendation content from being updated. Associated insights are frozen and placed in the ACCEPTED state. MarkRecommendationFailed can be applied to recommendations in ACTIVE, CLAIMED, SUCCEEDED, or FAILED state. Requires the recommender.&#42;.update IAM permission for the specified recommender. */
					class markFailed(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withGoogleCloudRecommenderV1MarkRecommendationFailedRequest(body: Schema.GoogleCloudRecommenderV1MarkRecommendationFailedRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudRecommenderV1Recommendation])
					}
					object markFailed {
						def apply(foldersId :PlayApi, locationsId :PlayApi, recommendersId :PlayApi, recommendationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): markFailed = new markFailed(ws.url(BASE_URL + s"v1/folders/${foldersId}/locations/${locationsId}/recommenders/${recommendersId}/recommendations/${recommendationsId}:markFailed").addQueryStringParameters("name" -> name.toString))
					}
					/** Gets the requested recommendation. Requires the recommender.&#42;.get IAM permission for the specified recommender. */
					class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudRecommenderV1Recommendation]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudRecommenderV1Recommendation])
					}
					object get {
						def apply(foldersId :PlayApi, locationsId :PlayApi, recommendersId :PlayApi, recommendationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/folders/${foldersId}/locations/${locationsId}/recommenders/${recommendersId}/recommendations/${recommendationsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.GoogleCloudRecommenderV1Recommendation]] = (fun: get) => fun.apply()
					}
					/** Lists recommendations for the specified Cloud Resource. Requires the recommender.&#42;.list IAM permission for the specified recommender. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudRecommenderV1ListRecommendationsResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Optional. The maximum number of results to return from this request. Non-positive values are ignored. If not specified, the server will determine the number of results to return.<br>Format: int32 */
						def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
						/** Optional. If present, retrieves the next batch of results from the preceding call to this method. `page_token` must be the value of `next_page_token` from the previous response. The values of other method parameters must be identical to those in the previous call. */
						def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudRecommenderV1ListRecommendationsResponse])
					}
					object list {
						def apply(foldersId :PlayApi, locationsId :PlayApi, recommendersId :PlayApi, parent: String, filter: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/folders/${foldersId}/locations/${locationsId}/recommenders/${recommendersId}/recommendations").addQueryStringParameters("parent" -> parent.toString, "filter" -> filter.toString))
						given Conversion[list, Future[Schema.GoogleCloudRecommenderV1ListRecommendationsResponse]] = (fun: list) => fun.apply()
					}
				}
			}
			object insightTypes {
				object insights {
					/** Lists insights for the specified Cloud Resource. Requires the recommender.&#42;.list IAM permission for the specified insight type. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudRecommenderV1ListInsightsResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Optional. If present, retrieves the next batch of results from the preceding call to this method. `page_token` must be the value of `next_page_token` from the previous response. The values of other method parameters must be identical to those in the previous call. */
						def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
						/** Optional. The maximum number of results to return from this request. Non-positive values are ignored. If not specified, the server will determine the number of results to return.<br>Format: int32 */
						def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
						/** Optional. Filter expression to restrict the insights returned. Supported filter fields: &#42; `stateInfo.state` &#42; `insightSubtype` &#42; `severity` &#42; `targetResources` Examples: &#42; `stateInfo.state = ACTIVE OR stateInfo.state = DISMISSED` &#42; `insightSubtype = PERMISSIONS_USAGE` &#42; `severity = CRITICAL OR severity = HIGH` &#42; `targetResources : //compute.googleapis.com/projects/1234/zones/us-central1-a/instances/instance-1` &#42; `stateInfo.state = ACTIVE AND (severity = CRITICAL OR severity = HIGH)` The max allowed filter length is 500 characters. (These expressions are based on the filter language described at https://google.aip.dev/160) */
						def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudRecommenderV1ListInsightsResponse])
					}
					object list {
						def apply(foldersId :PlayApi, locationsId :PlayApi, insightTypesId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/folders/${foldersId}/locations/${locationsId}/insightTypes/${insightTypesId}/insights").addQueryStringParameters("parent" -> parent.toString))
						given Conversion[list, Future[Schema.GoogleCloudRecommenderV1ListInsightsResponse]] = (fun: list) => fun.apply()
					}
					/** Marks the Insight State as Accepted. Users can use this method to indicate to the Recommender API that they have applied some action based on the insight. This stops the insight content from being updated. MarkInsightAccepted can be applied to insights in ACTIVE state. Requires the recommender.&#42;.update IAM permission for the specified insight. */
					class markAccepted(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withGoogleCloudRecommenderV1MarkInsightAcceptedRequest(body: Schema.GoogleCloudRecommenderV1MarkInsightAcceptedRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudRecommenderV1Insight])
					}
					object markAccepted {
						def apply(foldersId :PlayApi, locationsId :PlayApi, insightTypesId :PlayApi, insightsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): markAccepted = new markAccepted(ws.url(BASE_URL + s"v1/folders/${foldersId}/locations/${locationsId}/insightTypes/${insightTypesId}/insights/${insightsId}:markAccepted").addQueryStringParameters("name" -> name.toString))
					}
					/** Gets the requested insight. Requires the recommender.&#42;.get IAM permission for the specified insight type. */
					class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudRecommenderV1Insight]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudRecommenderV1Insight])
					}
					object get {
						def apply(foldersId :PlayApi, locationsId :PlayApi, insightTypesId :PlayApi, insightsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/folders/${foldersId}/locations/${locationsId}/insightTypes/${insightTypesId}/insights/${insightsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.GoogleCloudRecommenderV1Insight]] = (fun: get) => fun.apply()
					}
				}
			}
		}
	}
	object organizations {
		object locations {
			object insightTypes {
				/** Gets the requested InsightTypeConfig. There is only one instance of the config for each InsightType. */
				class getConfig(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudRecommenderV1InsightTypeConfig]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudRecommenderV1InsightTypeConfig])
				}
				object getConfig {
					def apply(organizationsId :PlayApi, locationsId :PlayApi, insightTypesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): getConfig = new getConfig(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/locations/${locationsId}/insightTypes/${insightTypesId}/config").addQueryStringParameters("name" -> name.toString))
					given Conversion[getConfig, Future[Schema.GoogleCloudRecommenderV1InsightTypeConfig]] = (fun: getConfig) => fun.apply()
				}
				/** Updates an InsightTypeConfig change. This will create a new revision of the config. */
				class updateConfig(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGoogleCloudRecommenderV1InsightTypeConfig(body: Schema.GoogleCloudRecommenderV1InsightTypeConfig) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudRecommenderV1InsightTypeConfig])
				}
				object updateConfig {
					def apply(organizationsId :PlayApi, locationsId :PlayApi, insightTypesId :PlayApi, validateOnly: Boolean, updateMask: String, name: String)(using signer: RequestSigner, ec: ExecutionContext): updateConfig = new updateConfig(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/locations/${locationsId}/insightTypes/${insightTypesId}/config").addQueryStringParameters("validateOnly" -> validateOnly.toString, "updateMask" -> updateMask.toString, "name" -> name.toString))
				}
				object insights {
					/** Marks the Insight State as Accepted. Users can use this method to indicate to the Recommender API that they have applied some action based on the insight. This stops the insight content from being updated. MarkInsightAccepted can be applied to insights in ACTIVE state. Requires the recommender.&#42;.update IAM permission for the specified insight. */
					class markAccepted(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withGoogleCloudRecommenderV1MarkInsightAcceptedRequest(body: Schema.GoogleCloudRecommenderV1MarkInsightAcceptedRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudRecommenderV1Insight])
					}
					object markAccepted {
						def apply(organizationsId :PlayApi, locationsId :PlayApi, insightTypesId :PlayApi, insightsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): markAccepted = new markAccepted(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/locations/${locationsId}/insightTypes/${insightTypesId}/insights/${insightsId}:markAccepted").addQueryStringParameters("name" -> name.toString))
					}
					/** Lists insights for the specified Cloud Resource. Requires the recommender.&#42;.list IAM permission for the specified insight type. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudRecommenderV1ListInsightsResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Optional. The maximum number of results to return from this request. Non-positive values are ignored. If not specified, the server will determine the number of results to return.<br>Format: int32 */
						def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
						/** Optional. Filter expression to restrict the insights returned. Supported filter fields: &#42; `stateInfo.state` &#42; `insightSubtype` &#42; `severity` &#42; `targetResources` Examples: &#42; `stateInfo.state = ACTIVE OR stateInfo.state = DISMISSED` &#42; `insightSubtype = PERMISSIONS_USAGE` &#42; `severity = CRITICAL OR severity = HIGH` &#42; `targetResources : //compute.googleapis.com/projects/1234/zones/us-central1-a/instances/instance-1` &#42; `stateInfo.state = ACTIVE AND (severity = CRITICAL OR severity = HIGH)` The max allowed filter length is 500 characters. (These expressions are based on the filter language described at https://google.aip.dev/160) */
						def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
						/** Optional. If present, retrieves the next batch of results from the preceding call to this method. `page_token` must be the value of `next_page_token` from the previous response. The values of other method parameters must be identical to those in the previous call. */
						def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudRecommenderV1ListInsightsResponse])
					}
					object list {
						def apply(organizationsId :PlayApi, locationsId :PlayApi, insightTypesId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/locations/${locationsId}/insightTypes/${insightTypesId}/insights").addQueryStringParameters("parent" -> parent.toString))
						given Conversion[list, Future[Schema.GoogleCloudRecommenderV1ListInsightsResponse]] = (fun: list) => fun.apply()
					}
					/** Gets the requested insight. Requires the recommender.&#42;.get IAM permission for the specified insight type. */
					class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudRecommenderV1Insight]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudRecommenderV1Insight])
					}
					object get {
						def apply(organizationsId :PlayApi, locationsId :PlayApi, insightTypesId :PlayApi, insightsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/locations/${locationsId}/insightTypes/${insightTypesId}/insights/${insightsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.GoogleCloudRecommenderV1Insight]] = (fun: get) => fun.apply()
					}
				}
			}
			object recommenders {
				/** Updates a Recommender Config. This will create a new revision of the config. */
				class updateConfig(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGoogleCloudRecommenderV1RecommenderConfig(body: Schema.GoogleCloudRecommenderV1RecommenderConfig) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudRecommenderV1RecommenderConfig])
				}
				object updateConfig {
					def apply(organizationsId :PlayApi, locationsId :PlayApi, recommendersId :PlayApi, validateOnly: Boolean, updateMask: String, name: String)(using signer: RequestSigner, ec: ExecutionContext): updateConfig = new updateConfig(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/locations/${locationsId}/recommenders/${recommendersId}/config").addQueryStringParameters("validateOnly" -> validateOnly.toString, "updateMask" -> updateMask.toString, "name" -> name.toString))
				}
				/** Gets the requested Recommender Config. There is only one instance of the config for each Recommender. */
				class getConfig(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudRecommenderV1RecommenderConfig]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudRecommenderV1RecommenderConfig])
				}
				object getConfig {
					def apply(organizationsId :PlayApi, locationsId :PlayApi, recommendersId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): getConfig = new getConfig(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/locations/${locationsId}/recommenders/${recommendersId}/config").addQueryStringParameters("name" -> name.toString))
					given Conversion[getConfig, Future[Schema.GoogleCloudRecommenderV1RecommenderConfig]] = (fun: getConfig) => fun.apply()
				}
				object recommendations {
					/** Marks the Recommendation State as Claimed. Users can use this method to indicate to the Recommender API that they are starting to apply the recommendation themselves. This stops the recommendation content from being updated. Associated insights are frozen and placed in the ACCEPTED state. MarkRecommendationClaimed can be applied to recommendations in CLAIMED, SUCCEEDED, FAILED, or ACTIVE state. Requires the recommender.&#42;.update IAM permission for the specified recommender. */
					class markClaimed(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withGoogleCloudRecommenderV1MarkRecommendationClaimedRequest(body: Schema.GoogleCloudRecommenderV1MarkRecommendationClaimedRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudRecommenderV1Recommendation])
					}
					object markClaimed {
						def apply(organizationsId :PlayApi, locationsId :PlayApi, recommendersId :PlayApi, recommendationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): markClaimed = new markClaimed(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/locations/${locationsId}/recommenders/${recommendersId}/recommendations/${recommendationsId}:markClaimed").addQueryStringParameters("name" -> name.toString))
					}
					/** Marks the Recommendation State as Succeeded. Users can use this method to indicate to the Recommender API that they have applied the recommendation themselves, and the operation was successful. This stops the recommendation content from being updated. Associated insights are frozen and placed in the ACCEPTED state. MarkRecommendationSucceeded can be applied to recommendations in ACTIVE, CLAIMED, SUCCEEDED, or FAILED state. Requires the recommender.&#42;.update IAM permission for the specified recommender. */
					class markSucceeded(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withGoogleCloudRecommenderV1MarkRecommendationSucceededRequest(body: Schema.GoogleCloudRecommenderV1MarkRecommendationSucceededRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudRecommenderV1Recommendation])
					}
					object markSucceeded {
						def apply(organizationsId :PlayApi, locationsId :PlayApi, recommendersId :PlayApi, recommendationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): markSucceeded = new markSucceeded(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/locations/${locationsId}/recommenders/${recommendersId}/recommendations/${recommendationsId}:markSucceeded").addQueryStringParameters("name" -> name.toString))
					}
					/** Mark the Recommendation State as Dismissed. Users can use this method to indicate to the Recommender API that an ACTIVE recommendation has to be marked back as DISMISSED. MarkRecommendationDismissed can be applied to recommendations in ACTIVE state. Requires the recommender.&#42;.update IAM permission for the specified recommender. */
					class markDismissed(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withGoogleCloudRecommenderV1MarkRecommendationDismissedRequest(body: Schema.GoogleCloudRecommenderV1MarkRecommendationDismissedRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudRecommenderV1Recommendation])
					}
					object markDismissed {
						def apply(organizationsId :PlayApi, locationsId :PlayApi, recommendersId :PlayApi, recommendationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): markDismissed = new markDismissed(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/locations/${locationsId}/recommenders/${recommendersId}/recommendations/${recommendationsId}:markDismissed").addQueryStringParameters("name" -> name.toString))
					}
					/** Marks the Recommendation State as Failed. Users can use this method to indicate to the Recommender API that they have applied the recommendation themselves, and the operation failed. This stops the recommendation content from being updated. Associated insights are frozen and placed in the ACCEPTED state. MarkRecommendationFailed can be applied to recommendations in ACTIVE, CLAIMED, SUCCEEDED, or FAILED state. Requires the recommender.&#42;.update IAM permission for the specified recommender. */
					class markFailed(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withGoogleCloudRecommenderV1MarkRecommendationFailedRequest(body: Schema.GoogleCloudRecommenderV1MarkRecommendationFailedRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudRecommenderV1Recommendation])
					}
					object markFailed {
						def apply(organizationsId :PlayApi, locationsId :PlayApi, recommendersId :PlayApi, recommendationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): markFailed = new markFailed(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/locations/${locationsId}/recommenders/${recommendersId}/recommendations/${recommendationsId}:markFailed").addQueryStringParameters("name" -> name.toString))
					}
					/** Gets the requested recommendation. Requires the recommender.&#42;.get IAM permission for the specified recommender. */
					class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudRecommenderV1Recommendation]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudRecommenderV1Recommendation])
					}
					object get {
						def apply(organizationsId :PlayApi, locationsId :PlayApi, recommendersId :PlayApi, recommendationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/locations/${locationsId}/recommenders/${recommendersId}/recommendations/${recommendationsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.GoogleCloudRecommenderV1Recommendation]] = (fun: get) => fun.apply()
					}
					/** Lists recommendations for the specified Cloud Resource. Requires the recommender.&#42;.list IAM permission for the specified recommender. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudRecommenderV1ListRecommendationsResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Optional. If present, retrieves the next batch of results from the preceding call to this method. `page_token` must be the value of `next_page_token` from the previous response. The values of other method parameters must be identical to those in the previous call. */
						def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
						/** Optional. The maximum number of results to return from this request. Non-positive values are ignored. If not specified, the server will determine the number of results to return.<br>Format: int32 */
						def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudRecommenderV1ListRecommendationsResponse])
					}
					object list {
						def apply(organizationsId :PlayApi, locationsId :PlayApi, recommendersId :PlayApi, parent: String, filter: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/locations/${locationsId}/recommenders/${recommendersId}/recommendations").addQueryStringParameters("parent" -> parent.toString, "filter" -> filter.toString))
						given Conversion[list, Future[Schema.GoogleCloudRecommenderV1ListRecommendationsResponse]] = (fun: list) => fun.apply()
					}
				}
			}
		}
	}
	object billingAccounts {
		object locations {
			object insightTypes {
				/** Updates an InsightTypeConfig change. This will create a new revision of the config. */
				class updateConfig(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGoogleCloudRecommenderV1InsightTypeConfig(body: Schema.GoogleCloudRecommenderV1InsightTypeConfig) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudRecommenderV1InsightTypeConfig])
				}
				object updateConfig {
					def apply(billingAccountsId :PlayApi, locationsId :PlayApi, insightTypesId :PlayApi, name: String, updateMask: String, validateOnly: Boolean)(using signer: RequestSigner, ec: ExecutionContext): updateConfig = new updateConfig(ws.url(BASE_URL + s"v1/billingAccounts/${billingAccountsId}/locations/${locationsId}/insightTypes/${insightTypesId}/config").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString, "validateOnly" -> validateOnly.toString))
				}
				/** Gets the requested InsightTypeConfig. There is only one instance of the config for each InsightType. */
				class getConfig(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudRecommenderV1InsightTypeConfig]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudRecommenderV1InsightTypeConfig])
				}
				object getConfig {
					def apply(billingAccountsId :PlayApi, locationsId :PlayApi, insightTypesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): getConfig = new getConfig(ws.url(BASE_URL + s"v1/billingAccounts/${billingAccountsId}/locations/${locationsId}/insightTypes/${insightTypesId}/config").addQueryStringParameters("name" -> name.toString))
					given Conversion[getConfig, Future[Schema.GoogleCloudRecommenderV1InsightTypeConfig]] = (fun: getConfig) => fun.apply()
				}
				object insights {
					/** Marks the Insight State as Accepted. Users can use this method to indicate to the Recommender API that they have applied some action based on the insight. This stops the insight content from being updated. MarkInsightAccepted can be applied to insights in ACTIVE state. Requires the recommender.&#42;.update IAM permission for the specified insight. */
					class markAccepted(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withGoogleCloudRecommenderV1MarkInsightAcceptedRequest(body: Schema.GoogleCloudRecommenderV1MarkInsightAcceptedRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudRecommenderV1Insight])
					}
					object markAccepted {
						def apply(billingAccountsId :PlayApi, locationsId :PlayApi, insightTypesId :PlayApi, insightsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): markAccepted = new markAccepted(ws.url(BASE_URL + s"v1/billingAccounts/${billingAccountsId}/locations/${locationsId}/insightTypes/${insightTypesId}/insights/${insightsId}:markAccepted").addQueryStringParameters("name" -> name.toString))
					}
					/** Lists insights for the specified Cloud Resource. Requires the recommender.&#42;.list IAM permission for the specified insight type. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudRecommenderV1ListInsightsResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Optional. The maximum number of results to return from this request. Non-positive values are ignored. If not specified, the server will determine the number of results to return.<br>Format: int32 */
						def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
						/** Optional. Filter expression to restrict the insights returned. Supported filter fields: &#42; `stateInfo.state` &#42; `insightSubtype` &#42; `severity` &#42; `targetResources` Examples: &#42; `stateInfo.state = ACTIVE OR stateInfo.state = DISMISSED` &#42; `insightSubtype = PERMISSIONS_USAGE` &#42; `severity = CRITICAL OR severity = HIGH` &#42; `targetResources : //compute.googleapis.com/projects/1234/zones/us-central1-a/instances/instance-1` &#42; `stateInfo.state = ACTIVE AND (severity = CRITICAL OR severity = HIGH)` The max allowed filter length is 500 characters. (These expressions are based on the filter language described at https://google.aip.dev/160) */
						def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
						/** Optional. If present, retrieves the next batch of results from the preceding call to this method. `page_token` must be the value of `next_page_token` from the previous response. The values of other method parameters must be identical to those in the previous call. */
						def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudRecommenderV1ListInsightsResponse])
					}
					object list {
						def apply(billingAccountsId :PlayApi, locationsId :PlayApi, insightTypesId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/billingAccounts/${billingAccountsId}/locations/${locationsId}/insightTypes/${insightTypesId}/insights").addQueryStringParameters("parent" -> parent.toString))
						given Conversion[list, Future[Schema.GoogleCloudRecommenderV1ListInsightsResponse]] = (fun: list) => fun.apply()
					}
					/** Gets the requested insight. Requires the recommender.&#42;.get IAM permission for the specified insight type. */
					class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudRecommenderV1Insight]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudRecommenderV1Insight])
					}
					object get {
						def apply(billingAccountsId :PlayApi, locationsId :PlayApi, insightTypesId :PlayApi, insightsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/billingAccounts/${billingAccountsId}/locations/${locationsId}/insightTypes/${insightTypesId}/insights/${insightsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.GoogleCloudRecommenderV1Insight]] = (fun: get) => fun.apply()
					}
				}
			}
			object recommenders {
				/** Updates a Recommender Config. This will create a new revision of the config. */
				class updateConfig(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGoogleCloudRecommenderV1RecommenderConfig(body: Schema.GoogleCloudRecommenderV1RecommenderConfig) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudRecommenderV1RecommenderConfig])
				}
				object updateConfig {
					def apply(billingAccountsId :PlayApi, locationsId :PlayApi, recommendersId :PlayApi, updateMask: String, validateOnly: Boolean, name: String)(using signer: RequestSigner, ec: ExecutionContext): updateConfig = new updateConfig(ws.url(BASE_URL + s"v1/billingAccounts/${billingAccountsId}/locations/${locationsId}/recommenders/${recommendersId}/config").addQueryStringParameters("updateMask" -> updateMask.toString, "validateOnly" -> validateOnly.toString, "name" -> name.toString))
				}
				/** Gets the requested Recommender Config. There is only one instance of the config for each Recommender. */
				class getConfig(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudRecommenderV1RecommenderConfig]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudRecommenderV1RecommenderConfig])
				}
				object getConfig {
					def apply(billingAccountsId :PlayApi, locationsId :PlayApi, recommendersId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): getConfig = new getConfig(ws.url(BASE_URL + s"v1/billingAccounts/${billingAccountsId}/locations/${locationsId}/recommenders/${recommendersId}/config").addQueryStringParameters("name" -> name.toString))
					given Conversion[getConfig, Future[Schema.GoogleCloudRecommenderV1RecommenderConfig]] = (fun: getConfig) => fun.apply()
				}
				object recommendations {
					/** Marks the Recommendation State as Claimed. Users can use this method to indicate to the Recommender API that they are starting to apply the recommendation themselves. This stops the recommendation content from being updated. Associated insights are frozen and placed in the ACCEPTED state. MarkRecommendationClaimed can be applied to recommendations in CLAIMED, SUCCEEDED, FAILED, or ACTIVE state. Requires the recommender.&#42;.update IAM permission for the specified recommender. */
					class markClaimed(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withGoogleCloudRecommenderV1MarkRecommendationClaimedRequest(body: Schema.GoogleCloudRecommenderV1MarkRecommendationClaimedRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudRecommenderV1Recommendation])
					}
					object markClaimed {
						def apply(billingAccountsId :PlayApi, locationsId :PlayApi, recommendersId :PlayApi, recommendationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): markClaimed = new markClaimed(ws.url(BASE_URL + s"v1/billingAccounts/${billingAccountsId}/locations/${locationsId}/recommenders/${recommendersId}/recommendations/${recommendationsId}:markClaimed").addQueryStringParameters("name" -> name.toString))
					}
					/** Marks the Recommendation State as Succeeded. Users can use this method to indicate to the Recommender API that they have applied the recommendation themselves, and the operation was successful. This stops the recommendation content from being updated. Associated insights are frozen and placed in the ACCEPTED state. MarkRecommendationSucceeded can be applied to recommendations in ACTIVE, CLAIMED, SUCCEEDED, or FAILED state. Requires the recommender.&#42;.update IAM permission for the specified recommender. */
					class markSucceeded(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withGoogleCloudRecommenderV1MarkRecommendationSucceededRequest(body: Schema.GoogleCloudRecommenderV1MarkRecommendationSucceededRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudRecommenderV1Recommendation])
					}
					object markSucceeded {
						def apply(billingAccountsId :PlayApi, locationsId :PlayApi, recommendersId :PlayApi, recommendationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): markSucceeded = new markSucceeded(ws.url(BASE_URL + s"v1/billingAccounts/${billingAccountsId}/locations/${locationsId}/recommenders/${recommendersId}/recommendations/${recommendationsId}:markSucceeded").addQueryStringParameters("name" -> name.toString))
					}
					/** Mark the Recommendation State as Dismissed. Users can use this method to indicate to the Recommender API that an ACTIVE recommendation has to be marked back as DISMISSED. MarkRecommendationDismissed can be applied to recommendations in ACTIVE state. Requires the recommender.&#42;.update IAM permission for the specified recommender. */
					class markDismissed(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withGoogleCloudRecommenderV1MarkRecommendationDismissedRequest(body: Schema.GoogleCloudRecommenderV1MarkRecommendationDismissedRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudRecommenderV1Recommendation])
					}
					object markDismissed {
						def apply(billingAccountsId :PlayApi, locationsId :PlayApi, recommendersId :PlayApi, recommendationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): markDismissed = new markDismissed(ws.url(BASE_URL + s"v1/billingAccounts/${billingAccountsId}/locations/${locationsId}/recommenders/${recommendersId}/recommendations/${recommendationsId}:markDismissed").addQueryStringParameters("name" -> name.toString))
					}
					/** Marks the Recommendation State as Failed. Users can use this method to indicate to the Recommender API that they have applied the recommendation themselves, and the operation failed. This stops the recommendation content from being updated. Associated insights are frozen and placed in the ACCEPTED state. MarkRecommendationFailed can be applied to recommendations in ACTIVE, CLAIMED, SUCCEEDED, or FAILED state. Requires the recommender.&#42;.update IAM permission for the specified recommender. */
					class markFailed(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withGoogleCloudRecommenderV1MarkRecommendationFailedRequest(body: Schema.GoogleCloudRecommenderV1MarkRecommendationFailedRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudRecommenderV1Recommendation])
					}
					object markFailed {
						def apply(billingAccountsId :PlayApi, locationsId :PlayApi, recommendersId :PlayApi, recommendationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): markFailed = new markFailed(ws.url(BASE_URL + s"v1/billingAccounts/${billingAccountsId}/locations/${locationsId}/recommenders/${recommendersId}/recommendations/${recommendationsId}:markFailed").addQueryStringParameters("name" -> name.toString))
					}
					/** Gets the requested recommendation. Requires the recommender.&#42;.get IAM permission for the specified recommender. */
					class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudRecommenderV1Recommendation]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudRecommenderV1Recommendation])
					}
					object get {
						def apply(billingAccountsId :PlayApi, locationsId :PlayApi, recommendersId :PlayApi, recommendationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/billingAccounts/${billingAccountsId}/locations/${locationsId}/recommenders/${recommendersId}/recommendations/${recommendationsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.GoogleCloudRecommenderV1Recommendation]] = (fun: get) => fun.apply()
					}
					/** Lists recommendations for the specified Cloud Resource. Requires the recommender.&#42;.list IAM permission for the specified recommender. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudRecommenderV1ListRecommendationsResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Optional. If present, retrieves the next batch of results from the preceding call to this method. `page_token` must be the value of `next_page_token` from the previous response. The values of other method parameters must be identical to those in the previous call. */
						def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
						/** Optional. The maximum number of results to return from this request. Non-positive values are ignored. If not specified, the server will determine the number of results to return.<br>Format: int32 */
						def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudRecommenderV1ListRecommendationsResponse])
					}
					object list {
						def apply(billingAccountsId :PlayApi, locationsId :PlayApi, recommendersId :PlayApi, filter: String, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/billingAccounts/${billingAccountsId}/locations/${locationsId}/recommenders/${recommendersId}/recommendations").addQueryStringParameters("filter" -> filter.toString, "parent" -> parent.toString))
						given Conversion[list, Future[Schema.GoogleCloudRecommenderV1ListRecommendationsResponse]] = (fun: list) => fun.apply()
					}
				}
			}
		}
	}
	object projects {
		object locations {
			object insightTypes {
				/** Updates an InsightTypeConfig change. This will create a new revision of the config. */
				class updateConfig(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGoogleCloudRecommenderV1InsightTypeConfig(body: Schema.GoogleCloudRecommenderV1InsightTypeConfig) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudRecommenderV1InsightTypeConfig])
				}
				object updateConfig {
					def apply(projectsId :PlayApi, locationsId :PlayApi, insightTypesId :PlayApi, updateMask: String, name: String, validateOnly: Boolean)(using signer: RequestSigner, ec: ExecutionContext): updateConfig = new updateConfig(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/insightTypes/${insightTypesId}/config").addQueryStringParameters("updateMask" -> updateMask.toString, "name" -> name.toString, "validateOnly" -> validateOnly.toString))
				}
				/** Gets the requested InsightTypeConfig. There is only one instance of the config for each InsightType. */
				class getConfig(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudRecommenderV1InsightTypeConfig]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudRecommenderV1InsightTypeConfig])
				}
				object getConfig {
					def apply(projectsId :PlayApi, locationsId :PlayApi, insightTypesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): getConfig = new getConfig(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/insightTypes/${insightTypesId}/config").addQueryStringParameters("name" -> name.toString))
					given Conversion[getConfig, Future[Schema.GoogleCloudRecommenderV1InsightTypeConfig]] = (fun: getConfig) => fun.apply()
				}
				object insights {
					/** Lists insights for the specified Cloud Resource. Requires the recommender.&#42;.list IAM permission for the specified insight type. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudRecommenderV1ListInsightsResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Optional. Filter expression to restrict the insights returned. Supported filter fields: &#42; `stateInfo.state` &#42; `insightSubtype` &#42; `severity` &#42; `targetResources` Examples: &#42; `stateInfo.state = ACTIVE OR stateInfo.state = DISMISSED` &#42; `insightSubtype = PERMISSIONS_USAGE` &#42; `severity = CRITICAL OR severity = HIGH` &#42; `targetResources : //compute.googleapis.com/projects/1234/zones/us-central1-a/instances/instance-1` &#42; `stateInfo.state = ACTIVE AND (severity = CRITICAL OR severity = HIGH)` The max allowed filter length is 500 characters. (These expressions are based on the filter language described at https://google.aip.dev/160) */
						def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
						/** Optional. If present, retrieves the next batch of results from the preceding call to this method. `page_token` must be the value of `next_page_token` from the previous response. The values of other method parameters must be identical to those in the previous call. */
						def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
						/** Optional. The maximum number of results to return from this request. Non-positive values are ignored. If not specified, the server will determine the number of results to return.<br>Format: int32 */
						def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudRecommenderV1ListInsightsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, insightTypesId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/insightTypes/${insightTypesId}/insights").addQueryStringParameters("parent" -> parent.toString))
						given Conversion[list, Future[Schema.GoogleCloudRecommenderV1ListInsightsResponse]] = (fun: list) => fun.apply()
					}
					/** Marks the Insight State as Accepted. Users can use this method to indicate to the Recommender API that they have applied some action based on the insight. This stops the insight content from being updated. MarkInsightAccepted can be applied to insights in ACTIVE state. Requires the recommender.&#42;.update IAM permission for the specified insight. */
					class markAccepted(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withGoogleCloudRecommenderV1MarkInsightAcceptedRequest(body: Schema.GoogleCloudRecommenderV1MarkInsightAcceptedRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudRecommenderV1Insight])
					}
					object markAccepted {
						def apply(projectsId :PlayApi, locationsId :PlayApi, insightTypesId :PlayApi, insightsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): markAccepted = new markAccepted(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/insightTypes/${insightTypesId}/insights/${insightsId}:markAccepted").addQueryStringParameters("name" -> name.toString))
					}
					/** Gets the requested insight. Requires the recommender.&#42;.get IAM permission for the specified insight type. */
					class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudRecommenderV1Insight]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudRecommenderV1Insight])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, insightTypesId :PlayApi, insightsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/insightTypes/${insightTypesId}/insights/${insightsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.GoogleCloudRecommenderV1Insight]] = (fun: get) => fun.apply()
					}
				}
			}
			object recommenders {
				/** Updates a Recommender Config. This will create a new revision of the config. */
				class updateConfig(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGoogleCloudRecommenderV1RecommenderConfig(body: Schema.GoogleCloudRecommenderV1RecommenderConfig) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudRecommenderV1RecommenderConfig])
				}
				object updateConfig {
					def apply(projectsId :PlayApi, locationsId :PlayApi, recommendersId :PlayApi, name: String, updateMask: String, validateOnly: Boolean)(using signer: RequestSigner, ec: ExecutionContext): updateConfig = new updateConfig(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/recommenders/${recommendersId}/config").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString, "validateOnly" -> validateOnly.toString))
				}
				/** Gets the requested Recommender Config. There is only one instance of the config for each Recommender. */
				class getConfig(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudRecommenderV1RecommenderConfig]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudRecommenderV1RecommenderConfig])
				}
				object getConfig {
					def apply(projectsId :PlayApi, locationsId :PlayApi, recommendersId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): getConfig = new getConfig(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/recommenders/${recommendersId}/config").addQueryStringParameters("name" -> name.toString))
					given Conversion[getConfig, Future[Schema.GoogleCloudRecommenderV1RecommenderConfig]] = (fun: getConfig) => fun.apply()
				}
				object recommendations {
					/** Marks the Recommendation State as Claimed. Users can use this method to indicate to the Recommender API that they are starting to apply the recommendation themselves. This stops the recommendation content from being updated. Associated insights are frozen and placed in the ACCEPTED state. MarkRecommendationClaimed can be applied to recommendations in CLAIMED, SUCCEEDED, FAILED, or ACTIVE state. Requires the recommender.&#42;.update IAM permission for the specified recommender. */
					class markClaimed(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withGoogleCloudRecommenderV1MarkRecommendationClaimedRequest(body: Schema.GoogleCloudRecommenderV1MarkRecommendationClaimedRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudRecommenderV1Recommendation])
					}
					object markClaimed {
						def apply(projectsId :PlayApi, locationsId :PlayApi, recommendersId :PlayApi, recommendationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): markClaimed = new markClaimed(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/recommenders/${recommendersId}/recommendations/${recommendationsId}:markClaimed").addQueryStringParameters("name" -> name.toString))
					}
					/** Marks the Recommendation State as Succeeded. Users can use this method to indicate to the Recommender API that they have applied the recommendation themselves, and the operation was successful. This stops the recommendation content from being updated. Associated insights are frozen and placed in the ACCEPTED state. MarkRecommendationSucceeded can be applied to recommendations in ACTIVE, CLAIMED, SUCCEEDED, or FAILED state. Requires the recommender.&#42;.update IAM permission for the specified recommender. */
					class markSucceeded(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withGoogleCloudRecommenderV1MarkRecommendationSucceededRequest(body: Schema.GoogleCloudRecommenderV1MarkRecommendationSucceededRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudRecommenderV1Recommendation])
					}
					object markSucceeded {
						def apply(projectsId :PlayApi, locationsId :PlayApi, recommendersId :PlayApi, recommendationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): markSucceeded = new markSucceeded(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/recommenders/${recommendersId}/recommendations/${recommendationsId}:markSucceeded").addQueryStringParameters("name" -> name.toString))
					}
					/** Mark the Recommendation State as Dismissed. Users can use this method to indicate to the Recommender API that an ACTIVE recommendation has to be marked back as DISMISSED. MarkRecommendationDismissed can be applied to recommendations in ACTIVE state. Requires the recommender.&#42;.update IAM permission for the specified recommender. */
					class markDismissed(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withGoogleCloudRecommenderV1MarkRecommendationDismissedRequest(body: Schema.GoogleCloudRecommenderV1MarkRecommendationDismissedRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudRecommenderV1Recommendation])
					}
					object markDismissed {
						def apply(projectsId :PlayApi, locationsId :PlayApi, recommendersId :PlayApi, recommendationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): markDismissed = new markDismissed(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/recommenders/${recommendersId}/recommendations/${recommendationsId}:markDismissed").addQueryStringParameters("name" -> name.toString))
					}
					/** Marks the Recommendation State as Failed. Users can use this method to indicate to the Recommender API that they have applied the recommendation themselves, and the operation failed. This stops the recommendation content from being updated. Associated insights are frozen and placed in the ACCEPTED state. MarkRecommendationFailed can be applied to recommendations in ACTIVE, CLAIMED, SUCCEEDED, or FAILED state. Requires the recommender.&#42;.update IAM permission for the specified recommender. */
					class markFailed(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withGoogleCloudRecommenderV1MarkRecommendationFailedRequest(body: Schema.GoogleCloudRecommenderV1MarkRecommendationFailedRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudRecommenderV1Recommendation])
					}
					object markFailed {
						def apply(projectsId :PlayApi, locationsId :PlayApi, recommendersId :PlayApi, recommendationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): markFailed = new markFailed(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/recommenders/${recommendersId}/recommendations/${recommendationsId}:markFailed").addQueryStringParameters("name" -> name.toString))
					}
					/** Gets the requested recommendation. Requires the recommender.&#42;.get IAM permission for the specified recommender. */
					class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudRecommenderV1Recommendation]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudRecommenderV1Recommendation])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, recommendersId :PlayApi, recommendationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/recommenders/${recommendersId}/recommendations/${recommendationsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.GoogleCloudRecommenderV1Recommendation]] = (fun: get) => fun.apply()
					}
					/** Lists recommendations for the specified Cloud Resource. Requires the recommender.&#42;.list IAM permission for the specified recommender. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudRecommenderV1ListRecommendationsResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Optional. The maximum number of results to return from this request. Non-positive values are ignored. If not specified, the server will determine the number of results to return.<br>Format: int32 */
						def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
						/** Optional. If present, retrieves the next batch of results from the preceding call to this method. `page_token` must be the value of `next_page_token` from the previous response. The values of other method parameters must be identical to those in the previous call. */
						def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudRecommenderV1ListRecommendationsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, recommendersId :PlayApi, parent: String, filter: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/recommenders/${recommendersId}/recommendations").addQueryStringParameters("parent" -> parent.toString, "filter" -> filter.toString))
						given Conversion[list, Future[Schema.GoogleCloudRecommenderV1ListRecommendationsResponse]] = (fun: list) => fun.apply()
					}
				}
			}
		}
	}
}
