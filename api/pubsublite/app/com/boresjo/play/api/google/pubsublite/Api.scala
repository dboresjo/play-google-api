package com.boresjo.play.api.google.pubsublite

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

	private val BASE_URL = "https://pubsublite.googleapis.com/"

	object admin {
		object projects {
			object locations {
				object operations {
					/** Lists operations that match the specified filter in the request. If the server doesn't support this method, it returns `UNIMPLEMENTED`. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListOperationsResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListOperationsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, name: String, filter: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/admin/projects/${projectsId}/locations/${locationsId}/operations").addQueryStringParameters("name" -> name.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
						given Conversion[list, Future[Schema.ListOperationsResponse]] = (fun: list) => fun.apply()
					}
					/** Gets the latest state of a long-running operation. Clients can use this method to poll the operation result at intervals as recommended by the API service. */
					class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Operation])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, operationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/admin/projects/${projectsId}/locations/${locationsId}/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.Operation]] = (fun: get) => fun.apply()
					}
					/** Deletes a long-running operation. This method indicates that the client is no longer interested in the operation result. It does not cancel the operation. If the server doesn't support this method, it returns `google.rpc.Code.UNIMPLEMENTED`. */
					class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
					}
					object delete {
						def apply(projectsId :PlayApi, locationsId :PlayApi, operationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/admin/projects/${projectsId}/locations/${locationsId}/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
					}
					/** Starts asynchronous cancellation on a long-running operation. The server makes a best effort to cancel the operation, but success is not guaranteed. If the server doesn't support this method, it returns `google.rpc.Code.UNIMPLEMENTED`. Clients can use Operations.GetOperation or other methods to check whether the cancellation succeeded or whether the operation completed despite cancellation. On successful cancellation, the operation is not deleted; instead, it becomes an operation with an Operation.error value with a google.rpc.Status.code of 1, corresponding to `Code.CANCELLED`. */
					class cancel(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withCancelOperationRequest(body: Schema.CancelOperationRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Empty])
					}
					object cancel {
						def apply(projectsId :PlayApi, locationsId :PlayApi, operationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): cancel = new cancel(ws.url(BASE_URL + s"v1/admin/projects/${projectsId}/locations/${locationsId}/operations/${operationsId}:cancel").addQueryStringParameters("name" -> name.toString))
					}
				}
				object topics {
					/** Creates a new topic. */
					class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withTopic(body: Schema.Topic) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Topic])
					}
					object create {
						def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, topicId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/admin/projects/${projectsId}/locations/${locationsId}/topics").addQueryStringParameters("parent" -> parent.toString, "topicId" -> topicId.toString))
					}
					/** Deletes the specified topic. */
					class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
					}
					object delete {
						def apply(projectsId :PlayApi, locationsId :PlayApi, topicsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/admin/projects/${projectsId}/locations/${locationsId}/topics/${topicsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
					}
					/** Returns the topic configuration. */
					class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Topic]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Topic])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, topicsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/admin/projects/${projectsId}/locations/${locationsId}/topics/${topicsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.Topic]] = (fun: get) => fun.apply()
					}
					/** Returns the partition information for the requested topic. */
					class getPartitions(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.TopicPartitions]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.TopicPartitions])
					}
					object getPartitions {
						def apply(projectsId :PlayApi, locationsId :PlayApi, topicsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): getPartitions = new getPartitions(ws.url(BASE_URL + s"v1/admin/projects/${projectsId}/locations/${locationsId}/topics/${topicsId}/partitions").addQueryStringParameters("name" -> name.toString))
						given Conversion[getPartitions, Future[Schema.TopicPartitions]] = (fun: getPartitions) => fun.apply()
					}
					/** Updates properties of the specified topic. */
					class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withTopic(body: Schema.Topic) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Topic])
					}
					object patch {
						def apply(projectsId :PlayApi, locationsId :PlayApi, topicsId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/admin/projects/${projectsId}/locations/${locationsId}/topics/${topicsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
					}
					/** Returns the list of topics for the given project. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListTopicsResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListTopicsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/admin/projects/${projectsId}/locations/${locationsId}/topics").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
						given Conversion[list, Future[Schema.ListTopicsResponse]] = (fun: list) => fun.apply()
					}
					object subscriptions {
						/** Lists the subscriptions attached to the specified topic. */
						class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListTopicSubscriptionsResponse]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListTopicSubscriptionsResponse])
						}
						object list {
							def apply(projectsId :PlayApi, locationsId :PlayApi, topicsId :PlayApi, name: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/admin/projects/${projectsId}/locations/${locationsId}/topics/${topicsId}/subscriptions").addQueryStringParameters("name" -> name.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
							given Conversion[list, Future[Schema.ListTopicSubscriptionsResponse]] = (fun: list) => fun.apply()
						}
					}
				}
				object subscriptions {
					/** Performs an out-of-band seek for a subscription to a specified target, which may be timestamps or named positions within the message backlog. Seek translates these targets to cursors for each partition and orchestrates subscribers to start consuming messages from these seek cursors. If an operation is returned, the seek has been registered and subscribers will eventually receive messages from the seek cursors (i.e. eventual consistency), as long as they are using a minimum supported client library version and not a system that tracks cursors independently of Pub/Sub Lite (e.g. Apache Beam, Dataflow, Spark). The seek operation will fail for unsupported clients. If clients would like to know when subscribers react to the seek (or not), they can poll the operation. The seek operation will succeed and complete once subscribers are ready to receive messages from the seek cursors for all partitions of the topic. This means that the seek operation will not complete until all subscribers come online. If the previous seek operation has not yet completed, it will be aborted and the new invocation of seek will supersede it. */
					class seek(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withSeekSubscriptionRequest(body: Schema.SeekSubscriptionRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
					}
					object seek {
						def apply(projectsId :PlayApi, locationsId :PlayApi, subscriptionsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): seek = new seek(ws.url(BASE_URL + s"v1/admin/projects/${projectsId}/locations/${locationsId}/subscriptions/${subscriptionsId}:seek").addQueryStringParameters("name" -> name.toString))
					}
					/** Creates a new subscription. */
					class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withSubscription(body: Schema.Subscription) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Subscription])
					}
					object create {
						def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, subscriptionId: String, skipBacklog: Boolean)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/admin/projects/${projectsId}/locations/${locationsId}/subscriptions").addQueryStringParameters("parent" -> parent.toString, "subscriptionId" -> subscriptionId.toString, "skipBacklog" -> skipBacklog.toString))
					}
					/** Deletes the specified subscription. */
					class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
					}
					object delete {
						def apply(projectsId :PlayApi, locationsId :PlayApi, subscriptionsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/admin/projects/${projectsId}/locations/${locationsId}/subscriptions/${subscriptionsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
					}
					/** Returns the subscription configuration. */
					class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Subscription]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Subscription])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, subscriptionsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/admin/projects/${projectsId}/locations/${locationsId}/subscriptions/${subscriptionsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.Subscription]] = (fun: get) => fun.apply()
					}
					/** Updates properties of the specified subscription. */
					class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withSubscription(body: Schema.Subscription) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Subscription])
					}
					object patch {
						def apply(projectsId :PlayApi, locationsId :PlayApi, subscriptionsId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/admin/projects/${projectsId}/locations/${locationsId}/subscriptions/${subscriptionsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
					}
					/** Returns the list of subscriptions for the given project. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListSubscriptionsResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListSubscriptionsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/admin/projects/${projectsId}/locations/${locationsId}/subscriptions").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
						given Conversion[list, Future[Schema.ListSubscriptionsResponse]] = (fun: list) => fun.apply()
					}
				}
				object reservations {
					/** Creates a new reservation. */
					class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withReservation(body: Schema.Reservation) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Reservation])
					}
					object create {
						def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, reservationId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/admin/projects/${projectsId}/locations/${locationsId}/reservations").addQueryStringParameters("parent" -> parent.toString, "reservationId" -> reservationId.toString))
					}
					/** Deletes the specified reservation. */
					class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
					}
					object delete {
						def apply(projectsId :PlayApi, locationsId :PlayApi, reservationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/admin/projects/${projectsId}/locations/${locationsId}/reservations/${reservationsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
					}
					/** Returns the reservation configuration. */
					class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Reservation]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Reservation])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, reservationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/admin/projects/${projectsId}/locations/${locationsId}/reservations/${reservationsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.Reservation]] = (fun: get) => fun.apply()
					}
					/** Updates properties of the specified reservation. */
					class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withReservation(body: Schema.Reservation) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Reservation])
					}
					object patch {
						def apply(projectsId :PlayApi, locationsId :PlayApi, reservationsId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/admin/projects/${projectsId}/locations/${locationsId}/reservations/${reservationsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
					}
					/** Returns the list of reservations for the given project. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListReservationsResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListReservationsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/admin/projects/${projectsId}/locations/${locationsId}/reservations").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
						given Conversion[list, Future[Schema.ListReservationsResponse]] = (fun: list) => fun.apply()
					}
					object topics {
						/** Lists the topics attached to the specified reservation. */
						class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListReservationTopicsResponse]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListReservationTopicsResponse])
						}
						object list {
							def apply(projectsId :PlayApi, locationsId :PlayApi, reservationsId :PlayApi, name: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/admin/projects/${projectsId}/locations/${locationsId}/reservations/${reservationsId}/topics").addQueryStringParameters("name" -> name.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
							given Conversion[list, Future[Schema.ListReservationTopicsResponse]] = (fun: list) => fun.apply()
						}
					}
				}
			}
		}
	}
	object cursor {
		object projects {
			object locations {
				object subscriptions {
					/** Updates the committed cursor. */
					class commitCursor(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withCommitCursorRequest(body: Schema.CommitCursorRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.CommitCursorResponse])
					}
					object commitCursor {
						def apply(projectsId :PlayApi, locationsId :PlayApi, subscriptionsId :PlayApi, subscription: String)(using signer: RequestSigner, ec: ExecutionContext): commitCursor = new commitCursor(ws.url(BASE_URL + s"v1/cursor/projects/${projectsId}/locations/${locationsId}/subscriptions/${subscriptionsId}:commitCursor").addQueryStringParameters("subscription" -> subscription.toString))
					}
					object cursors {
						/** Returns all committed cursor information for a subscription. */
						class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListPartitionCursorsResponse]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListPartitionCursorsResponse])
						}
						object list {
							def apply(projectsId :PlayApi, locationsId :PlayApi, subscriptionsId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/cursor/projects/${projectsId}/locations/${locationsId}/subscriptions/${subscriptionsId}/cursors").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
							given Conversion[list, Future[Schema.ListPartitionCursorsResponse]] = (fun: list) => fun.apply()
						}
					}
				}
			}
		}
	}
	object topicStats {
		object projects {
			object locations {
				object topics {
					/** Compute statistics about a range of messages in a given topic and partition. */
					class computeMessageStats(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withComputeMessageStatsRequest(body: Schema.ComputeMessageStatsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.ComputeMessageStatsResponse])
					}
					object computeMessageStats {
						def apply(projectsId :PlayApi, locationsId :PlayApi, topicsId :PlayApi, topic: String)(using signer: RequestSigner, ec: ExecutionContext): computeMessageStats = new computeMessageStats(ws.url(BASE_URL + s"v1/topicStats/projects/${projectsId}/locations/${locationsId}/topics/${topicsId}:computeMessageStats").addQueryStringParameters("topic" -> topic.toString))
					}
					/** Compute the head cursor for the partition. The head cursor's offset is guaranteed to be less than or equal to all messages which have not yet been acknowledged as published, and greater than the offset of any message whose publish has already been acknowledged. It is zero if there have never been messages in the partition. */
					class computeHeadCursor(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withComputeHeadCursorRequest(body: Schema.ComputeHeadCursorRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.ComputeHeadCursorResponse])
					}
					object computeHeadCursor {
						def apply(projectsId :PlayApi, locationsId :PlayApi, topicsId :PlayApi, topic: String)(using signer: RequestSigner, ec: ExecutionContext): computeHeadCursor = new computeHeadCursor(ws.url(BASE_URL + s"v1/topicStats/projects/${projectsId}/locations/${locationsId}/topics/${topicsId}:computeHeadCursor").addQueryStringParameters("topic" -> topic.toString))
					}
					/** Compute the corresponding cursor for a publish or event time in a topic partition. */
					class computeTimeCursor(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withComputeTimeCursorRequest(body: Schema.ComputeTimeCursorRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.ComputeTimeCursorResponse])
					}
					object computeTimeCursor {
						def apply(projectsId :PlayApi, locationsId :PlayApi, topicsId :PlayApi, topic: String)(using signer: RequestSigner, ec: ExecutionContext): computeTimeCursor = new computeTimeCursor(ws.url(BASE_URL + s"v1/topicStats/projects/${projectsId}/locations/${locationsId}/topics/${topicsId}:computeTimeCursor").addQueryStringParameters("topic" -> topic.toString))
					}
				}
			}
		}
	}
}
