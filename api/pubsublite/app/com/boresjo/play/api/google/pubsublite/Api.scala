package com.boresjo.play.api.google.pubsublite

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://pubsublite.googleapis.com/"

	object admin {
		object projects {
			object locations {
				object operations {
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListOperationsResponse]) {
						def apply() = req.execute("GET").map(_.json.as[Schema.ListOperationsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, name: String, filter: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/admin/projects/${projectsId}/locations/${locationsId}/operations")).addQueryStringParameters("name" -> name.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
						given Conversion[list, Future[Schema.ListOperationsResponse]] = (fun: list) => fun.apply()
					}
					class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
						def apply() = req.execute("GET").map(_.json.as[Schema.Operation])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, operationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/admin/projects/${projectsId}/locations/${locationsId}/operations/${operationsId}")).addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.Operation]] = (fun: get) => fun.apply()
					}
					class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
						def apply() = req.execute("DELETE").map(_.json.as[Schema.Empty])
					}
					object delete {
						def apply(projectsId :PlayApi, locationsId :PlayApi, operationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/admin/projects/${projectsId}/locations/${locationsId}/operations/${operationsId}")).addQueryStringParameters("name" -> name.toString))
						given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
					}
					class cancel(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withCancelOperationRequest(body: Schema.CancelOperationRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Empty])
					}
					object cancel {
						def apply(projectsId :PlayApi, locationsId :PlayApi, operationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): cancel = new cancel(auth(ws.url(BASE_URL + s"v1/admin/projects/${projectsId}/locations/${locationsId}/operations/${operationsId}:cancel")).addQueryStringParameters("name" -> name.toString))
					}
				}
				object topics {
					class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withTopic(body: Schema.Topic) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Topic])
					}
					object create {
						def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, topicId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/admin/projects/${projectsId}/locations/${locationsId}/topics")).addQueryStringParameters("parent" -> parent.toString, "topicId" -> topicId.toString))
					}
					class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
						def apply() = req.execute("DELETE").map(_.json.as[Schema.Empty])
					}
					object delete {
						def apply(projectsId :PlayApi, locationsId :PlayApi, topicsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/admin/projects/${projectsId}/locations/${locationsId}/topics/${topicsId}")).addQueryStringParameters("name" -> name.toString))
						given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
					}
					class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Topic]) {
						def apply() = req.execute("GET").map(_.json.as[Schema.Topic])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, topicsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/admin/projects/${projectsId}/locations/${locationsId}/topics/${topicsId}")).addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.Topic]] = (fun: get) => fun.apply()
					}
					class getPartitions(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.TopicPartitions]) {
						def apply() = req.execute("GET").map(_.json.as[Schema.TopicPartitions])
					}
					object getPartitions {
						def apply(projectsId :PlayApi, locationsId :PlayApi, topicsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): getPartitions = new getPartitions(auth(ws.url(BASE_URL + s"v1/admin/projects/${projectsId}/locations/${locationsId}/topics/${topicsId}/partitions")).addQueryStringParameters("name" -> name.toString))
						given Conversion[getPartitions, Future[Schema.TopicPartitions]] = (fun: getPartitions) => fun.apply()
					}
					class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withTopic(body: Schema.Topic) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.Topic])
					}
					object patch {
						def apply(projectsId :PlayApi, locationsId :PlayApi, topicsId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"v1/admin/projects/${projectsId}/locations/${locationsId}/topics/${topicsId}")).addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
					}
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListTopicsResponse]) {
						def apply() = req.execute("GET").map(_.json.as[Schema.ListTopicsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/admin/projects/${projectsId}/locations/${locationsId}/topics")).addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
						given Conversion[list, Future[Schema.ListTopicsResponse]] = (fun: list) => fun.apply()
					}
					object subscriptions {
						class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListTopicSubscriptionsResponse]) {
							def apply() = req.execute("GET").map(_.json.as[Schema.ListTopicSubscriptionsResponse])
						}
						object list {
							def apply(projectsId :PlayApi, locationsId :PlayApi, topicsId :PlayApi, name: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/admin/projects/${projectsId}/locations/${locationsId}/topics/${topicsId}/subscriptions")).addQueryStringParameters("name" -> name.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
							given Conversion[list, Future[Schema.ListTopicSubscriptionsResponse]] = (fun: list) => fun.apply()
						}
					}
				}
				object subscriptions {
					class seek(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withSeekSubscriptionRequest(body: Schema.SeekSubscriptionRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Operation])
					}
					object seek {
						def apply(projectsId :PlayApi, locationsId :PlayApi, subscriptionsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): seek = new seek(auth(ws.url(BASE_URL + s"v1/admin/projects/${projectsId}/locations/${locationsId}/subscriptions/${subscriptionsId}:seek")).addQueryStringParameters("name" -> name.toString))
					}
					class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withSubscription(body: Schema.Subscription) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Subscription])
					}
					object create {
						def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, subscriptionId: String, skipBacklog: Boolean)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/admin/projects/${projectsId}/locations/${locationsId}/subscriptions")).addQueryStringParameters("parent" -> parent.toString, "subscriptionId" -> subscriptionId.toString, "skipBacklog" -> skipBacklog.toString))
					}
					class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
						def apply() = req.execute("DELETE").map(_.json.as[Schema.Empty])
					}
					object delete {
						def apply(projectsId :PlayApi, locationsId :PlayApi, subscriptionsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/admin/projects/${projectsId}/locations/${locationsId}/subscriptions/${subscriptionsId}")).addQueryStringParameters("name" -> name.toString))
						given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
					}
					class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Subscription]) {
						def apply() = req.execute("GET").map(_.json.as[Schema.Subscription])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, subscriptionsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/admin/projects/${projectsId}/locations/${locationsId}/subscriptions/${subscriptionsId}")).addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.Subscription]] = (fun: get) => fun.apply()
					}
					class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withSubscription(body: Schema.Subscription) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.Subscription])
					}
					object patch {
						def apply(projectsId :PlayApi, locationsId :PlayApi, subscriptionsId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"v1/admin/projects/${projectsId}/locations/${locationsId}/subscriptions/${subscriptionsId}")).addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
					}
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListSubscriptionsResponse]) {
						def apply() = req.execute("GET").map(_.json.as[Schema.ListSubscriptionsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/admin/projects/${projectsId}/locations/${locationsId}/subscriptions")).addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
						given Conversion[list, Future[Schema.ListSubscriptionsResponse]] = (fun: list) => fun.apply()
					}
				}
				object reservations {
					class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withReservation(body: Schema.Reservation) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Reservation])
					}
					object create {
						def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, reservationId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/admin/projects/${projectsId}/locations/${locationsId}/reservations")).addQueryStringParameters("parent" -> parent.toString, "reservationId" -> reservationId.toString))
					}
					class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
						def apply() = req.execute("DELETE").map(_.json.as[Schema.Empty])
					}
					object delete {
						def apply(projectsId :PlayApi, locationsId :PlayApi, reservationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/admin/projects/${projectsId}/locations/${locationsId}/reservations/${reservationsId}")).addQueryStringParameters("name" -> name.toString))
						given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
					}
					class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Reservation]) {
						def apply() = req.execute("GET").map(_.json.as[Schema.Reservation])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, reservationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/admin/projects/${projectsId}/locations/${locationsId}/reservations/${reservationsId}")).addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.Reservation]] = (fun: get) => fun.apply()
					}
					class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withReservation(body: Schema.Reservation) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.Reservation])
					}
					object patch {
						def apply(projectsId :PlayApi, locationsId :PlayApi, reservationsId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"v1/admin/projects/${projectsId}/locations/${locationsId}/reservations/${reservationsId}")).addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
					}
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListReservationsResponse]) {
						def apply() = req.execute("GET").map(_.json.as[Schema.ListReservationsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/admin/projects/${projectsId}/locations/${locationsId}/reservations")).addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
						given Conversion[list, Future[Schema.ListReservationsResponse]] = (fun: list) => fun.apply()
					}
					object topics {
						class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListReservationTopicsResponse]) {
							def apply() = req.execute("GET").map(_.json.as[Schema.ListReservationTopicsResponse])
						}
						object list {
							def apply(projectsId :PlayApi, locationsId :PlayApi, reservationsId :PlayApi, name: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/admin/projects/${projectsId}/locations/${locationsId}/reservations/${reservationsId}/topics")).addQueryStringParameters("name" -> name.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
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
					class commitCursor(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withCommitCursorRequest(body: Schema.CommitCursorRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.CommitCursorResponse])
					}
					object commitCursor {
						def apply(projectsId :PlayApi, locationsId :PlayApi, subscriptionsId :PlayApi, subscription: String)(using auth: AuthToken, ec: ExecutionContext): commitCursor = new commitCursor(auth(ws.url(BASE_URL + s"v1/cursor/projects/${projectsId}/locations/${locationsId}/subscriptions/${subscriptionsId}:commitCursor")).addQueryStringParameters("subscription" -> subscription.toString))
					}
					object cursors {
						class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListPartitionCursorsResponse]) {
							def apply() = req.execute("GET").map(_.json.as[Schema.ListPartitionCursorsResponse])
						}
						object list {
							def apply(projectsId :PlayApi, locationsId :PlayApi, subscriptionsId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/cursor/projects/${projectsId}/locations/${locationsId}/subscriptions/${subscriptionsId}/cursors")).addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
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
					class computeMessageStats(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withComputeMessageStatsRequest(body: Schema.ComputeMessageStatsRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.ComputeMessageStatsResponse])
					}
					object computeMessageStats {
						def apply(projectsId :PlayApi, locationsId :PlayApi, topicsId :PlayApi, topic: String)(using auth: AuthToken, ec: ExecutionContext): computeMessageStats = new computeMessageStats(auth(ws.url(BASE_URL + s"v1/topicStats/projects/${projectsId}/locations/${locationsId}/topics/${topicsId}:computeMessageStats")).addQueryStringParameters("topic" -> topic.toString))
					}
					class computeHeadCursor(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withComputeHeadCursorRequest(body: Schema.ComputeHeadCursorRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.ComputeHeadCursorResponse])
					}
					object computeHeadCursor {
						def apply(projectsId :PlayApi, locationsId :PlayApi, topicsId :PlayApi, topic: String)(using auth: AuthToken, ec: ExecutionContext): computeHeadCursor = new computeHeadCursor(auth(ws.url(BASE_URL + s"v1/topicStats/projects/${projectsId}/locations/${locationsId}/topics/${topicsId}:computeHeadCursor")).addQueryStringParameters("topic" -> topic.toString))
					}
					class computeTimeCursor(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withComputeTimeCursorRequest(body: Schema.ComputeTimeCursorRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.ComputeTimeCursorResponse])
					}
					object computeTimeCursor {
						def apply(projectsId :PlayApi, locationsId :PlayApi, topicsId :PlayApi, topic: String)(using auth: AuthToken, ec: ExecutionContext): computeTimeCursor = new computeTimeCursor(auth(ws.url(BASE_URL + s"v1/topicStats/projects/${projectsId}/locations/${locationsId}/topics/${topicsId}:computeTimeCursor")).addQueryStringParameters("topic" -> topic.toString))
					}
				}
			}
		}
	}
}
