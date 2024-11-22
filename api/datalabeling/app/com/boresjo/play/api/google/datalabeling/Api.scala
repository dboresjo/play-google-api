package com.boresjo.play.api.google.datalabeling

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://datalabeling.googleapis.com/"

	object projects {
		object operations {
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningListOperationsResponse]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleLongrunningListOperationsResponse])
			}
			object list {
				def apply(projectsId :PlayApi, name: String, filter: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/operations").addQueryStringParameters("name" -> name.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.GoogleLongrunningListOperationsResponse]] = (fun: list) => fun.apply()
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningOperation]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleLongrunningOperation])
			}
			object get {
				def apply(projectsId :PlayApi, operationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.GoogleLongrunningOperation]] = (fun: get) => fun.apply()
			}
			class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
				def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
			}
			object delete {
				def apply(projectsId :PlayApi, operationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
			}
			class cancel(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleProtobufEmpty])
			}
			object cancel {
				def apply(projectsId :PlayApi, operationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): cancel = new cancel(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/operations/${operationsId}:cancel").addQueryStringParameters("name" -> name.toString))
				given Conversion[cancel, Future[Schema.GoogleProtobufEmpty]] = (fun: cancel) => fun.apply()
			}
		}
		object annotationSpecSets {
			class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGoogleCloudDatalabelingV1beta1CreateAnnotationSpecSetRequest(body: Schema.GoogleCloudDatalabelingV1beta1CreateAnnotationSpecSetRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudDatalabelingV1beta1AnnotationSpecSet])
			}
			object create {
				def apply(projectsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/annotationSpecSets").addQueryStringParameters("parent" -> parent.toString))
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDatalabelingV1beta1AnnotationSpecSet]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDatalabelingV1beta1AnnotationSpecSet])
			}
			object get {
				def apply(projectsId :PlayApi, annotationSpecSetsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/annotationSpecSets/${annotationSpecSetsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.GoogleCloudDatalabelingV1beta1AnnotationSpecSet]] = (fun: get) => fun.apply()
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDatalabelingV1beta1ListAnnotationSpecSetsResponse]) {
				/** Optional. Filter is not supported at this moment. */
				def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
				/** Optional. Requested page size. Server may return fewer results than requested. Default value is 100.<br>Format: int32 */
				def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
				/** Optional. A token identifying a page of results for the server to return. Typically obtained by ListAnnotationSpecSetsResponse.next_page_token of the previous [DataLabelingService.ListAnnotationSpecSets] call. Return first page if empty. */
				def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDatalabelingV1beta1ListAnnotationSpecSetsResponse])
			}
			object list {
				def apply(projectsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/annotationSpecSets").addQueryStringParameters("parent" -> parent.toString))
				given Conversion[list, Future[Schema.GoogleCloudDatalabelingV1beta1ListAnnotationSpecSetsResponse]] = (fun: list) => fun.apply()
			}
			class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
				def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
			}
			object delete {
				def apply(projectsId :PlayApi, annotationSpecSetsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/annotationSpecSets/${annotationSpecSetsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
			}
		}
		object datasets {
			class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGoogleCloudDatalabelingV1beta1CreateDatasetRequest(body: Schema.GoogleCloudDatalabelingV1beta1CreateDatasetRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudDatalabelingV1beta1Dataset])
			}
			object create {
				def apply(projectsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/datasets").addQueryStringParameters("parent" -> parent.toString))
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDatalabelingV1beta1Dataset]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDatalabelingV1beta1Dataset])
			}
			object get {
				def apply(projectsId :PlayApi, datasetsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/datasets/${datasetsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.GoogleCloudDatalabelingV1beta1Dataset]] = (fun: get) => fun.apply()
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDatalabelingV1beta1ListDatasetsResponse]) {
				/** Optional. Filter on dataset is not supported at this moment. */
				def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
				/** Optional. Requested page size. Server may return fewer results than requested. Default value is 100.<br>Format: int32 */
				def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
				/** Optional. A token identifying a page of results for the server to return. Typically obtained by ListDatasetsResponse.next_page_token of the previous [DataLabelingService.ListDatasets] call. Returns the first page if empty. */
				def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDatalabelingV1beta1ListDatasetsResponse])
			}
			object list {
				def apply(projectsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/datasets").addQueryStringParameters("parent" -> parent.toString))
				given Conversion[list, Future[Schema.GoogleCloudDatalabelingV1beta1ListDatasetsResponse]] = (fun: list) => fun.apply()
			}
			class exportData(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGoogleCloudDatalabelingV1beta1ExportDataRequest(body: Schema.GoogleCloudDatalabelingV1beta1ExportDataRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
			}
			object exportData {
				def apply(projectsId :PlayApi, datasetsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): exportData = new exportData(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/datasets/${datasetsId}:exportData").addQueryStringParameters("name" -> name.toString))
			}
			class importData(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGoogleCloudDatalabelingV1beta1ImportDataRequest(body: Schema.GoogleCloudDatalabelingV1beta1ImportDataRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
			}
			object importData {
				def apply(projectsId :PlayApi, datasetsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): importData = new importData(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/datasets/${datasetsId}:importData").addQueryStringParameters("name" -> name.toString))
			}
			class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
				def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
			}
			object delete {
				def apply(projectsId :PlayApi, datasetsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/datasets/${datasetsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
			}
			object text {
				class label(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudDatalabelingV1beta1LabelTextRequest(body: Schema.GoogleCloudDatalabelingV1beta1LabelTextRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
				}
				object label {
					def apply(projectsId :PlayApi, datasetsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): label = new label(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/datasets/${datasetsId}/text:label").addQueryStringParameters("parent" -> parent.toString))
				}
			}
			object evaluations {
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDatalabelingV1beta1Evaluation]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDatalabelingV1beta1Evaluation])
				}
				object get {
					def apply(projectsId :PlayApi, datasetsId :PlayApi, evaluationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/datasets/${datasetsId}/evaluations/${evaluationsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleCloudDatalabelingV1beta1Evaluation]] = (fun: get) => fun.apply()
				}
				object exampleComparisons {
					class search(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleCloudDatalabelingV1beta1SearchExampleComparisonsRequest(body: Schema.GoogleCloudDatalabelingV1beta1SearchExampleComparisonsRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudDatalabelingV1beta1SearchExampleComparisonsResponse])
					}
					object search {
						def apply(projectsId :PlayApi, datasetsId :PlayApi, evaluationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): search = new search(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/datasets/${datasetsId}/evaluations/${evaluationsId}/exampleComparisons:search").addQueryStringParameters("parent" -> parent.toString))
					}
				}
			}
			object video {
				class label(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudDatalabelingV1beta1LabelVideoRequest(body: Schema.GoogleCloudDatalabelingV1beta1LabelVideoRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
				}
				object label {
					def apply(projectsId :PlayApi, datasetsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): label = new label(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/datasets/${datasetsId}/video:label").addQueryStringParameters("parent" -> parent.toString))
				}
			}
			object dataItems {
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDatalabelingV1beta1DataItem]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDatalabelingV1beta1DataItem])
				}
				object get {
					def apply(projectsId :PlayApi, datasetsId :PlayApi, dataItemsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/datasets/${datasetsId}/dataItems/${dataItemsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleCloudDatalabelingV1beta1DataItem]] = (fun: get) => fun.apply()
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDatalabelingV1beta1ListDataItemsResponse]) {
					/** Optional. Filter is not supported at this moment. */
					def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
					/** Optional. Requested page size. Server may return fewer results than requested. Default value is 100.<br>Format: int32 */
					def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
					/** Optional. A token identifying a page of results for the server to return. Typically obtained by ListDataItemsResponse.next_page_token of the previous [DataLabelingService.ListDataItems] call. Return first page if empty. */
					def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDatalabelingV1beta1ListDataItemsResponse])
				}
				object list {
					def apply(projectsId :PlayApi, datasetsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/datasets/${datasetsId}/dataItems").addQueryStringParameters("parent" -> parent.toString))
					given Conversion[list, Future[Schema.GoogleCloudDatalabelingV1beta1ListDataItemsResponse]] = (fun: list) => fun.apply()
				}
			}
			object annotatedDatasets {
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
					def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
				}
				object delete {
					def apply(projectsId :PlayApi, datasetsId :PlayApi, annotatedDatasetsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/datasets/${datasetsId}/annotatedDatasets/${annotatedDatasetsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDatalabelingV1beta1AnnotatedDataset]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDatalabelingV1beta1AnnotatedDataset])
				}
				object get {
					def apply(projectsId :PlayApi, datasetsId :PlayApi, annotatedDatasetsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/datasets/${datasetsId}/annotatedDatasets/${annotatedDatasetsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleCloudDatalabelingV1beta1AnnotatedDataset]] = (fun: get) => fun.apply()
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDatalabelingV1beta1ListAnnotatedDatasetsResponse]) {
					/** Optional. Filter is not supported at this moment. */
					def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
					/** Optional. Requested page size. Server may return fewer results than requested. Default value is 100.<br>Format: int32 */
					def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
					/** Optional. A token identifying a page of results for the server to return. Typically obtained by ListAnnotatedDatasetsResponse.next_page_token of the previous [DataLabelingService.ListAnnotatedDatasets] call. Return first page if empty. */
					def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDatalabelingV1beta1ListAnnotatedDatasetsResponse])
				}
				object list {
					def apply(projectsId :PlayApi, datasetsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/datasets/${datasetsId}/annotatedDatasets").addQueryStringParameters("parent" -> parent.toString))
					given Conversion[list, Future[Schema.GoogleCloudDatalabelingV1beta1ListAnnotatedDatasetsResponse]] = (fun: list) => fun.apply()
				}
				object feedbackThreads {
					class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDatalabelingV1beta1FeedbackThread]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDatalabelingV1beta1FeedbackThread])
					}
					object get {
						def apply(projectsId :PlayApi, datasetsId :PlayApi, annotatedDatasetsId :PlayApi, feedbackThreadsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/datasets/${datasetsId}/annotatedDatasets/${annotatedDatasetsId}/feedbackThreads/${feedbackThreadsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.GoogleCloudDatalabelingV1beta1FeedbackThread]] = (fun: get) => fun.apply()
					}
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDatalabelingV1beta1ListFeedbackThreadsResponse]) {
						/** Optional. Requested page size. Server may return fewer results than requested. Default value is 100.<br>Format: int32 */
						def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
						/** Optional. A token identifying a page of results for the server to return. Typically obtained by ListFeedbackThreads.next_page_token of the previous [DataLabelingService.ListFeedbackThreads] call. Return first page if empty. */
						def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDatalabelingV1beta1ListFeedbackThreadsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, datasetsId :PlayApi, annotatedDatasetsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/datasets/${datasetsId}/annotatedDatasets/${annotatedDatasetsId}/feedbackThreads").addQueryStringParameters("parent" -> parent.toString))
						given Conversion[list, Future[Schema.GoogleCloudDatalabelingV1beta1ListFeedbackThreadsResponse]] = (fun: list) => fun.apply()
					}
					class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
						def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
					}
					object delete {
						def apply(projectsId :PlayApi, datasetsId :PlayApi, annotatedDatasetsId :PlayApi, feedbackThreadsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/datasets/${datasetsId}/annotatedDatasets/${annotatedDatasetsId}/feedbackThreads/${feedbackThreadsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
					}
					object feedbackMessages {
						class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
							def withGoogleCloudDatalabelingV1beta1FeedbackMessage(body: Schema.GoogleCloudDatalabelingV1beta1FeedbackMessage) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
						}
						object create {
							def apply(projectsId :PlayApi, datasetsId :PlayApi, annotatedDatasetsId :PlayApi, feedbackThreadsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/datasets/${datasetsId}/annotatedDatasets/${annotatedDatasetsId}/feedbackThreads/${feedbackThreadsId}/feedbackMessages").addQueryStringParameters("parent" -> parent.toString))
						}
						class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDatalabelingV1beta1FeedbackMessage]) {
							def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDatalabelingV1beta1FeedbackMessage])
						}
						object get {
							def apply(projectsId :PlayApi, datasetsId :PlayApi, annotatedDatasetsId :PlayApi, feedbackThreadsId :PlayApi, feedbackMessagesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/datasets/${datasetsId}/annotatedDatasets/${annotatedDatasetsId}/feedbackThreads/${feedbackThreadsId}/feedbackMessages/${feedbackMessagesId}").addQueryStringParameters("name" -> name.toString))
							given Conversion[get, Future[Schema.GoogleCloudDatalabelingV1beta1FeedbackMessage]] = (fun: get) => fun.apply()
						}
						class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDatalabelingV1beta1ListFeedbackMessagesResponse]) {
							/** Optional. Requested page size. Server may return fewer results than requested. Default value is 100.<br>Format: int32 */
							def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
							/** Optional. A token identifying a page of results for the server to return. Typically obtained by ListFeedbackMessages.next_page_token of the previous [DataLabelingService.ListFeedbackMessages] call. Return first page if empty. */
							def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
							def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDatalabelingV1beta1ListFeedbackMessagesResponse])
						}
						object list {
							def apply(projectsId :PlayApi, datasetsId :PlayApi, annotatedDatasetsId :PlayApi, feedbackThreadsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/datasets/${datasetsId}/annotatedDatasets/${annotatedDatasetsId}/feedbackThreads/${feedbackThreadsId}/feedbackMessages").addQueryStringParameters("parent" -> parent.toString))
							given Conversion[list, Future[Schema.GoogleCloudDatalabelingV1beta1ListFeedbackMessagesResponse]] = (fun: list) => fun.apply()
						}
						class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
							def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
						}
						object delete {
							def apply(projectsId :PlayApi, datasetsId :PlayApi, annotatedDatasetsId :PlayApi, feedbackThreadsId :PlayApi, feedbackMessagesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/datasets/${datasetsId}/annotatedDatasets/${annotatedDatasetsId}/feedbackThreads/${feedbackThreadsId}/feedbackMessages/${feedbackMessagesId}").addQueryStringParameters("name" -> name.toString))
							given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
						}
					}
				}
				object dataItems {
					class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDatalabelingV1beta1DataItem]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDatalabelingV1beta1DataItem])
					}
					object get {
						def apply(projectsId :PlayApi, datasetsId :PlayApi, annotatedDatasetsId :PlayApi, dataItemsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/datasets/${datasetsId}/annotatedDatasets/${annotatedDatasetsId}/dataItems/${dataItemsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.GoogleCloudDatalabelingV1beta1DataItem]] = (fun: get) => fun.apply()
					}
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDatalabelingV1beta1ListDataItemsResponse]) {
						/** Optional. Filter is not supported at this moment. */
						def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
						/** Optional. Requested page size. Server may return fewer results than requested. Default value is 100.<br>Format: int32 */
						def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
						/** Optional. A token identifying a page of results for the server to return. Typically obtained by ListDataItemsResponse.next_page_token of the previous [DataLabelingService.ListDataItems] call. Return first page if empty. */
						def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDatalabelingV1beta1ListDataItemsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, datasetsId :PlayApi, annotatedDatasetsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/datasets/${datasetsId}/annotatedDatasets/${annotatedDatasetsId}/dataItems").addQueryStringParameters("parent" -> parent.toString))
						given Conversion[list, Future[Schema.GoogleCloudDatalabelingV1beta1ListDataItemsResponse]] = (fun: list) => fun.apply()
					}
				}
				object examples {
					class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDatalabelingV1beta1Example]) {
						/** Optional. An expression for filtering Examples. Filter by annotation_spec.display_name is supported. Format "annotation_spec.display_name = {display_name}" */
						def withFilter(filter: String) = new get(req.addQueryStringParameters("filter" -> filter.toString))
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDatalabelingV1beta1Example])
					}
					object get {
						def apply(projectsId :PlayApi, datasetsId :PlayApi, annotatedDatasetsId :PlayApi, examplesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/datasets/${datasetsId}/annotatedDatasets/${annotatedDatasetsId}/examples/${examplesId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.GoogleCloudDatalabelingV1beta1Example]] = (fun: get) => fun.apply()
					}
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDatalabelingV1beta1ListExamplesResponse]) {
						/** Optional. An expression for filtering Examples. For annotated datasets that have annotation spec set, filter by annotation_spec.display_name is supported. Format "annotation_spec.display_name = {display_name}" */
						def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
						/** Optional. Requested page size. Server may return fewer results than requested. Default value is 100.<br>Format: int32 */
						def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
						/** Optional. A token identifying a page of results for the server to return. Typically obtained by ListExamplesResponse.next_page_token of the previous [DataLabelingService.ListExamples] call. Return first page if empty. */
						def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDatalabelingV1beta1ListExamplesResponse])
					}
					object list {
						def apply(projectsId :PlayApi, datasetsId :PlayApi, annotatedDatasetsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/datasets/${datasetsId}/annotatedDatasets/${annotatedDatasetsId}/examples").addQueryStringParameters("parent" -> parent.toString))
						given Conversion[list, Future[Schema.GoogleCloudDatalabelingV1beta1ListExamplesResponse]] = (fun: list) => fun.apply()
					}
				}
			}
			object image {
				class label(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudDatalabelingV1beta1LabelImageRequest(body: Schema.GoogleCloudDatalabelingV1beta1LabelImageRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
				}
				object label {
					def apply(projectsId :PlayApi, datasetsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): label = new label(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/datasets/${datasetsId}/image:label").addQueryStringParameters("parent" -> parent.toString))
				}
			}
		}
		object evaluations {
			class search(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDatalabelingV1beta1SearchEvaluationsResponse]) {
				/** Optional. To search evaluations, you can filter by the following: &#42; evaluation_job.evaluation_job_id (the last part of EvaluationJob.name) &#42; evaluation_job.model_id (the {model_name} portion of EvaluationJob.modelVersion) &#42; evaluation_job.evaluation_job_run_time_start (Minimum threshold for the evaluationJobRunTime that created the evaluation) &#42; evaluation_job.evaluation_job_run_time_end (Maximum threshold for the evaluationJobRunTime that created the evaluation) &#42; evaluation_job.job_state (EvaluationJob.state) &#42; annotation_spec.display_name (the Evaluation contains a metric for the annotation spec with this displayName) To filter by multiple critiera, use the `AND` operator or the `OR` operator. The following examples shows a string that filters by several critiera: "evaluation_job.evaluation_job_id = {evaluation_job_id} AND evaluation_job.model_id = {model_name} AND evaluation_job.evaluation_job_run_time_start = {timestamp_1} AND evaluation_job.evaluation_job_run_time_end = {timestamp_2} AND annotation_spec.display_name = {display_name}" */
				def withFilter(filter: String) = new search(req.addQueryStringParameters("filter" -> filter.toString))
				/** Optional. Requested page size. Server may return fewer results than requested. Default value is 100.<br>Format: int32 */
				def withPageSize(pageSize: Int) = new search(req.addQueryStringParameters("pageSize" -> pageSize.toString))
				/** Optional. A token identifying a page of results for the server to return. Typically obtained by the nextPageToken of the response to a previous search request. If you don't specify this field, the API call requests the first page of the search. */
				def withPageToken(pageToken: String) = new search(req.addQueryStringParameters("pageToken" -> pageToken.toString))
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDatalabelingV1beta1SearchEvaluationsResponse])
			}
			object search {
				def apply(projectsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): search = new search(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/evaluations:search").addQueryStringParameters("parent" -> parent.toString))
				given Conversion[search, Future[Schema.GoogleCloudDatalabelingV1beta1SearchEvaluationsResponse]] = (fun: search) => fun.apply()
			}
		}
		object instructions {
			class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGoogleCloudDatalabelingV1beta1CreateInstructionRequest(body: Schema.GoogleCloudDatalabelingV1beta1CreateInstructionRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
			}
			object create {
				def apply(projectsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/instructions").addQueryStringParameters("parent" -> parent.toString))
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDatalabelingV1beta1Instruction]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDatalabelingV1beta1Instruction])
			}
			object get {
				def apply(projectsId :PlayApi, instructionsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/instructions/${instructionsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.GoogleCloudDatalabelingV1beta1Instruction]] = (fun: get) => fun.apply()
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDatalabelingV1beta1ListInstructionsResponse]) {
				/** Optional. Filter is not supported at this moment. */
				def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
				/** Optional. Requested page size. Server may return fewer results than requested. Default value is 100.<br>Format: int32 */
				def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
				/** Optional. A token identifying a page of results for the server to return. Typically obtained by ListInstructionsResponse.next_page_token of the previous [DataLabelingService.ListInstructions] call. Return first page if empty. */
				def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDatalabelingV1beta1ListInstructionsResponse])
			}
			object list {
				def apply(projectsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/instructions").addQueryStringParameters("parent" -> parent.toString))
				given Conversion[list, Future[Schema.GoogleCloudDatalabelingV1beta1ListInstructionsResponse]] = (fun: list) => fun.apply()
			}
			class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
				def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
			}
			object delete {
				def apply(projectsId :PlayApi, instructionsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/instructions/${instructionsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
			}
		}
		object evaluationJobs {
			class resume(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGoogleCloudDatalabelingV1beta1ResumeEvaluationJobRequest(body: Schema.GoogleCloudDatalabelingV1beta1ResumeEvaluationJobRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleProtobufEmpty])
			}
			object resume {
				def apply(projectsId :PlayApi, evaluationJobsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): resume = new resume(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/evaluationJobs/${evaluationJobsId}:resume").addQueryStringParameters("name" -> name.toString))
			}
			class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
				def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
			}
			object delete {
				def apply(projectsId :PlayApi, evaluationJobsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/evaluationJobs/${evaluationJobsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDatalabelingV1beta1EvaluationJob]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDatalabelingV1beta1EvaluationJob])
			}
			object get {
				def apply(projectsId :PlayApi, evaluationJobsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/evaluationJobs/${evaluationJobsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.GoogleCloudDatalabelingV1beta1EvaluationJob]] = (fun: get) => fun.apply()
			}
			class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				/** Optional. Mask for which fields to update. You can only provide the following fields: &#42; `evaluationJobConfig.humanAnnotationConfig.instruction` &#42; `evaluationJobConfig.exampleCount` &#42; `evaluationJobConfig.exampleSamplePercentage` You can provide more than one of these fields by separating them with commas.<br>Format: google-fieldmask */
				def withUpdateMask(updateMask: String) = new patch(req.addQueryStringParameters("updateMask" -> updateMask.toString))
				def withGoogleCloudDatalabelingV1beta1EvaluationJob(body: Schema.GoogleCloudDatalabelingV1beta1EvaluationJob) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudDatalabelingV1beta1EvaluationJob])
			}
			object patch {
				def apply(projectsId :PlayApi, evaluationJobsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/evaluationJobs/${evaluationJobsId}").addQueryStringParameters("name" -> name.toString))
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDatalabelingV1beta1ListEvaluationJobsResponse]) {
				/** Optional. You can filter the jobs to list by model_id (also known as model_name, as described in EvaluationJob.modelVersion) or by evaluation job state (as described in EvaluationJob.state). To filter by both criteria, use the `AND` operator or the `OR` operator. For example, you can use the following string for your filter: "evaluation_job.model_id = {model_name} AND evaluation_job.state = {evaluation_job_state}" */
				def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
				/** Optional. Requested page size. Server may return fewer results than requested. Default value is 100.<br>Format: int32 */
				def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
				/** Optional. A token identifying a page of results for the server to return. Typically obtained by the nextPageToken in the response to the previous request. The request returns the first page if this is empty. */
				def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDatalabelingV1beta1ListEvaluationJobsResponse])
			}
			object list {
				def apply(projectsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/evaluationJobs").addQueryStringParameters("parent" -> parent.toString))
				given Conversion[list, Future[Schema.GoogleCloudDatalabelingV1beta1ListEvaluationJobsResponse]] = (fun: list) => fun.apply()
			}
			class pause(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGoogleCloudDatalabelingV1beta1PauseEvaluationJobRequest(body: Schema.GoogleCloudDatalabelingV1beta1PauseEvaluationJobRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleProtobufEmpty])
			}
			object pause {
				def apply(projectsId :PlayApi, evaluationJobsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): pause = new pause(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/evaluationJobs/${evaluationJobsId}:pause").addQueryStringParameters("name" -> name.toString))
			}
			class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGoogleCloudDatalabelingV1beta1CreateEvaluationJobRequest(body: Schema.GoogleCloudDatalabelingV1beta1CreateEvaluationJobRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudDatalabelingV1beta1EvaluationJob])
			}
			object create {
				def apply(projectsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/evaluationJobs").addQueryStringParameters("parent" -> parent.toString))
			}
		}
	}
}
