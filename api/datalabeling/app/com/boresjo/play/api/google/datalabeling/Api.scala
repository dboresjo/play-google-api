package com.boresjo.play.api.google.datalabeling

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

	private val BASE_URL = "https://datalabeling.googleapis.com/"

	object projects {
		object operations {
			/** Lists operations that match the specified filter in the request. If the server doesn't support this method, it returns `UNIMPLEMENTED`. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningListOperationsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleLongrunningListOperationsResponse])
			}
			object list {
				def apply(projectsId :PlayApi, name: String, filter: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/operations").addQueryStringParameters("name" -> name.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.GoogleLongrunningListOperationsResponse]] = (fun: list) => fun.apply()
			}
			/** Gets the latest state of a long-running operation. Clients can use this method to poll the operation result at intervals as recommended by the API service. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningOperation]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleLongrunningOperation])
			}
			object get {
				def apply(projectsId :PlayApi, operationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.GoogleLongrunningOperation]] = (fun: get) => fun.apply()
			}
			/** Deletes a long-running operation. This method indicates that the client is no longer interested in the operation result. It does not cancel the operation. If the server doesn't support this method, it returns `google.rpc.Code.UNIMPLEMENTED`. */
			class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
			}
			object delete {
				def apply(projectsId :PlayApi, operationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
			}
			/** Starts asynchronous cancellation on a long-running operation. The server makes a best effort to cancel the operation, but success is not guaranteed. If the server doesn't support this method, it returns `google.rpc.Code.UNIMPLEMENTED`. Clients can use Operations.GetOperation or other methods to check whether the cancellation succeeded or whether the operation completed despite cancellation. On successful cancellation, the operation is not deleted; instead, it becomes an operation with an Operation.error value with a google.rpc.Status.code of 1, corresponding to `Code.CANCELLED`. */
			class cancel(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleProtobufEmpty])
			}
			object cancel {
				def apply(projectsId :PlayApi, operationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): cancel = new cancel(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/operations/${operationsId}:cancel").addQueryStringParameters("name" -> name.toString))
				given Conversion[cancel, Future[Schema.GoogleProtobufEmpty]] = (fun: cancel) => fun.apply()
			}
		}
		object annotationSpecSets {
			/** Creates an annotation spec set by providing a set of labels. */
			class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withGoogleCloudDatalabelingV1beta1CreateAnnotationSpecSetRequest(body: Schema.GoogleCloudDatalabelingV1beta1CreateAnnotationSpecSetRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudDatalabelingV1beta1AnnotationSpecSet])
			}
			object create {
				def apply(projectsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/annotationSpecSets").addQueryStringParameters("parent" -> parent.toString))
			}
			/** Gets an annotation spec set by resource name. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDatalabelingV1beta1AnnotationSpecSet]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDatalabelingV1beta1AnnotationSpecSet])
			}
			object get {
				def apply(projectsId :PlayApi, annotationSpecSetsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/annotationSpecSets/${annotationSpecSetsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.GoogleCloudDatalabelingV1beta1AnnotationSpecSet]] = (fun: get) => fun.apply()
			}
			/** Lists annotation spec sets for a project. Pagination is supported. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDatalabelingV1beta1ListAnnotationSpecSetsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Optional. Filter is not supported at this moment. */
				def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
				/** Optional. Requested page size. Server may return fewer results than requested. Default value is 100.<br>Format: int32 */
				def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
				/** Optional. A token identifying a page of results for the server to return. Typically obtained by ListAnnotationSpecSetsResponse.next_page_token of the previous [DataLabelingService.ListAnnotationSpecSets] call. Return first page if empty. */
				def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDatalabelingV1beta1ListAnnotationSpecSetsResponse])
			}
			object list {
				def apply(projectsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/annotationSpecSets").addQueryStringParameters("parent" -> parent.toString))
				given Conversion[list, Future[Schema.GoogleCloudDatalabelingV1beta1ListAnnotationSpecSetsResponse]] = (fun: list) => fun.apply()
			}
			/** Deletes an annotation spec set by resource name. */
			class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
			}
			object delete {
				def apply(projectsId :PlayApi, annotationSpecSetsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/annotationSpecSets/${annotationSpecSetsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
			}
		}
		object datasets {
			/** Creates dataset. If success return a Dataset resource. */
			class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withGoogleCloudDatalabelingV1beta1CreateDatasetRequest(body: Schema.GoogleCloudDatalabelingV1beta1CreateDatasetRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudDatalabelingV1beta1Dataset])
			}
			object create {
				def apply(projectsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/datasets").addQueryStringParameters("parent" -> parent.toString))
			}
			/** Gets dataset by resource name. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDatalabelingV1beta1Dataset]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDatalabelingV1beta1Dataset])
			}
			object get {
				def apply(projectsId :PlayApi, datasetsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/datasets/${datasetsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.GoogleCloudDatalabelingV1beta1Dataset]] = (fun: get) => fun.apply()
			}
			/** Lists datasets under a project. Pagination is supported. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDatalabelingV1beta1ListDatasetsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Optional. Filter on dataset is not supported at this moment. */
				def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
				/** Optional. Requested page size. Server may return fewer results than requested. Default value is 100.<br>Format: int32 */
				def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
				/** Optional. A token identifying a page of results for the server to return. Typically obtained by ListDatasetsResponse.next_page_token of the previous [DataLabelingService.ListDatasets] call. Returns the first page if empty. */
				def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDatalabelingV1beta1ListDatasetsResponse])
			}
			object list {
				def apply(projectsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/datasets").addQueryStringParameters("parent" -> parent.toString))
				given Conversion[list, Future[Schema.GoogleCloudDatalabelingV1beta1ListDatasetsResponse]] = (fun: list) => fun.apply()
			}
			/** Exports data and annotations from dataset. */
			class exportData(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withGoogleCloudDatalabelingV1beta1ExportDataRequest(body: Schema.GoogleCloudDatalabelingV1beta1ExportDataRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
			}
			object exportData {
				def apply(projectsId :PlayApi, datasetsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): exportData = new exportData(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/datasets/${datasetsId}:exportData").addQueryStringParameters("name" -> name.toString))
			}
			/** Imports data into dataset based on source locations defined in request. It can be called multiple times for the same dataset. Each dataset can only have one long running operation running on it. For example, no labeling task (also long running operation) can be started while importing is still ongoing. Vice versa. */
			class importData(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withGoogleCloudDatalabelingV1beta1ImportDataRequest(body: Schema.GoogleCloudDatalabelingV1beta1ImportDataRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
			}
			object importData {
				def apply(projectsId :PlayApi, datasetsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): importData = new importData(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/datasets/${datasetsId}:importData").addQueryStringParameters("name" -> name.toString))
			}
			/** Deletes a dataset by resource name. */
			class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
			}
			object delete {
				def apply(projectsId :PlayApi, datasetsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/datasets/${datasetsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
			}
			object text {
				/** Starts a labeling task for text. The type of text labeling task is configured by feature in the request. */
				class label(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGoogleCloudDatalabelingV1beta1LabelTextRequest(body: Schema.GoogleCloudDatalabelingV1beta1LabelTextRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
				}
				object label {
					def apply(projectsId :PlayApi, datasetsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): label = new label(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/datasets/${datasetsId}/text:label").addQueryStringParameters("parent" -> parent.toString))
				}
			}
			object evaluations {
				/** Gets an evaluation by resource name (to search, use projects.evaluations.search). */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDatalabelingV1beta1Evaluation]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDatalabelingV1beta1Evaluation])
				}
				object get {
					def apply(projectsId :PlayApi, datasetsId :PlayApi, evaluationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/datasets/${datasetsId}/evaluations/${evaluationsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleCloudDatalabelingV1beta1Evaluation]] = (fun: get) => fun.apply()
				}
				object exampleComparisons {
					/** Searches example comparisons from an evaluation. The return format is a list of example comparisons that show ground truth and prediction(s) for a single input. Search by providing an evaluation ID. */
					class search(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withGoogleCloudDatalabelingV1beta1SearchExampleComparisonsRequest(body: Schema.GoogleCloudDatalabelingV1beta1SearchExampleComparisonsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudDatalabelingV1beta1SearchExampleComparisonsResponse])
					}
					object search {
						def apply(projectsId :PlayApi, datasetsId :PlayApi, evaluationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): search = new search(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/datasets/${datasetsId}/evaluations/${evaluationsId}/exampleComparisons:search").addQueryStringParameters("parent" -> parent.toString))
					}
				}
			}
			object video {
				/** Starts a labeling task for video. The type of video labeling task is configured by feature in the request. */
				class label(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGoogleCloudDatalabelingV1beta1LabelVideoRequest(body: Schema.GoogleCloudDatalabelingV1beta1LabelVideoRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
				}
				object label {
					def apply(projectsId :PlayApi, datasetsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): label = new label(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/datasets/${datasetsId}/video:label").addQueryStringParameters("parent" -> parent.toString))
				}
			}
			object dataItems {
				/** Gets a data item in a dataset by resource name. This API can be called after data are imported into dataset. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDatalabelingV1beta1DataItem]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDatalabelingV1beta1DataItem])
				}
				object get {
					def apply(projectsId :PlayApi, datasetsId :PlayApi, dataItemsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/datasets/${datasetsId}/dataItems/${dataItemsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleCloudDatalabelingV1beta1DataItem]] = (fun: get) => fun.apply()
				}
				/** Lists data items in a dataset. This API can be called after data are imported into dataset. Pagination is supported. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDatalabelingV1beta1ListDataItemsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Optional. Filter is not supported at this moment. */
					def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
					/** Optional. Requested page size. Server may return fewer results than requested. Default value is 100.<br>Format: int32 */
					def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
					/** Optional. A token identifying a page of results for the server to return. Typically obtained by ListDataItemsResponse.next_page_token of the previous [DataLabelingService.ListDataItems] call. Return first page if empty. */
					def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDatalabelingV1beta1ListDataItemsResponse])
				}
				object list {
					def apply(projectsId :PlayApi, datasetsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/datasets/${datasetsId}/dataItems").addQueryStringParameters("parent" -> parent.toString))
					given Conversion[list, Future[Schema.GoogleCloudDatalabelingV1beta1ListDataItemsResponse]] = (fun: list) => fun.apply()
				}
			}
			object annotatedDatasets {
				/** Deletes an annotated dataset by resource name. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
				}
				object delete {
					def apply(projectsId :PlayApi, datasetsId :PlayApi, annotatedDatasetsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/datasets/${datasetsId}/annotatedDatasets/${annotatedDatasetsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
				}
				/** Gets an annotated dataset by resource name. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDatalabelingV1beta1AnnotatedDataset]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDatalabelingV1beta1AnnotatedDataset])
				}
				object get {
					def apply(projectsId :PlayApi, datasetsId :PlayApi, annotatedDatasetsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/datasets/${datasetsId}/annotatedDatasets/${annotatedDatasetsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleCloudDatalabelingV1beta1AnnotatedDataset]] = (fun: get) => fun.apply()
				}
				/** Lists annotated datasets for a dataset. Pagination is supported. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDatalabelingV1beta1ListAnnotatedDatasetsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Optional. Filter is not supported at this moment. */
					def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
					/** Optional. Requested page size. Server may return fewer results than requested. Default value is 100.<br>Format: int32 */
					def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
					/** Optional. A token identifying a page of results for the server to return. Typically obtained by ListAnnotatedDatasetsResponse.next_page_token of the previous [DataLabelingService.ListAnnotatedDatasets] call. Return first page if empty. */
					def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDatalabelingV1beta1ListAnnotatedDatasetsResponse])
				}
				object list {
					def apply(projectsId :PlayApi, datasetsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/datasets/${datasetsId}/annotatedDatasets").addQueryStringParameters("parent" -> parent.toString))
					given Conversion[list, Future[Schema.GoogleCloudDatalabelingV1beta1ListAnnotatedDatasetsResponse]] = (fun: list) => fun.apply()
				}
				object feedbackThreads {
					/** Get a FeedbackThread object. */
					class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDatalabelingV1beta1FeedbackThread]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDatalabelingV1beta1FeedbackThread])
					}
					object get {
						def apply(projectsId :PlayApi, datasetsId :PlayApi, annotatedDatasetsId :PlayApi, feedbackThreadsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/datasets/${datasetsId}/annotatedDatasets/${annotatedDatasetsId}/feedbackThreads/${feedbackThreadsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.GoogleCloudDatalabelingV1beta1FeedbackThread]] = (fun: get) => fun.apply()
					}
					/** List FeedbackThreads with pagination. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDatalabelingV1beta1ListFeedbackThreadsResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Optional. Requested page size. Server may return fewer results than requested. Default value is 100.<br>Format: int32 */
						def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
						/** Optional. A token identifying a page of results for the server to return. Typically obtained by ListFeedbackThreads.next_page_token of the previous [DataLabelingService.ListFeedbackThreads] call. Return first page if empty. */
						def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDatalabelingV1beta1ListFeedbackThreadsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, datasetsId :PlayApi, annotatedDatasetsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/datasets/${datasetsId}/annotatedDatasets/${annotatedDatasetsId}/feedbackThreads").addQueryStringParameters("parent" -> parent.toString))
						given Conversion[list, Future[Schema.GoogleCloudDatalabelingV1beta1ListFeedbackThreadsResponse]] = (fun: list) => fun.apply()
					}
					/** Delete a FeedbackThread. */
					class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
					}
					object delete {
						def apply(projectsId :PlayApi, datasetsId :PlayApi, annotatedDatasetsId :PlayApi, feedbackThreadsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/datasets/${datasetsId}/annotatedDatasets/${annotatedDatasetsId}/feedbackThreads/${feedbackThreadsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
					}
					object feedbackMessages {
						/** Create a FeedbackMessage object. */
						class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def withGoogleCloudDatalabelingV1beta1FeedbackMessage(body: Schema.GoogleCloudDatalabelingV1beta1FeedbackMessage) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
						}
						object create {
							def apply(projectsId :PlayApi, datasetsId :PlayApi, annotatedDatasetsId :PlayApi, feedbackThreadsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/datasets/${datasetsId}/annotatedDatasets/${annotatedDatasetsId}/feedbackThreads/${feedbackThreadsId}/feedbackMessages").addQueryStringParameters("parent" -> parent.toString))
						}
						/** Get a FeedbackMessage object. */
						class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDatalabelingV1beta1FeedbackMessage]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDatalabelingV1beta1FeedbackMessage])
						}
						object get {
							def apply(projectsId :PlayApi, datasetsId :PlayApi, annotatedDatasetsId :PlayApi, feedbackThreadsId :PlayApi, feedbackMessagesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/datasets/${datasetsId}/annotatedDatasets/${annotatedDatasetsId}/feedbackThreads/${feedbackThreadsId}/feedbackMessages/${feedbackMessagesId}").addQueryStringParameters("name" -> name.toString))
							given Conversion[get, Future[Schema.GoogleCloudDatalabelingV1beta1FeedbackMessage]] = (fun: get) => fun.apply()
						}
						/** List FeedbackMessages with pagination. */
						class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDatalabelingV1beta1ListFeedbackMessagesResponse]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Optional. Requested page size. Server may return fewer results than requested. Default value is 100.<br>Format: int32 */
							def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
							/** Optional. A token identifying a page of results for the server to return. Typically obtained by ListFeedbackMessages.next_page_token of the previous [DataLabelingService.ListFeedbackMessages] call. Return first page if empty. */
							def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDatalabelingV1beta1ListFeedbackMessagesResponse])
						}
						object list {
							def apply(projectsId :PlayApi, datasetsId :PlayApi, annotatedDatasetsId :PlayApi, feedbackThreadsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/datasets/${datasetsId}/annotatedDatasets/${annotatedDatasetsId}/feedbackThreads/${feedbackThreadsId}/feedbackMessages").addQueryStringParameters("parent" -> parent.toString))
							given Conversion[list, Future[Schema.GoogleCloudDatalabelingV1beta1ListFeedbackMessagesResponse]] = (fun: list) => fun.apply()
						}
						/** Delete a FeedbackMessage. */
						class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
						}
						object delete {
							def apply(projectsId :PlayApi, datasetsId :PlayApi, annotatedDatasetsId :PlayApi, feedbackThreadsId :PlayApi, feedbackMessagesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/datasets/${datasetsId}/annotatedDatasets/${annotatedDatasetsId}/feedbackThreads/${feedbackThreadsId}/feedbackMessages/${feedbackMessagesId}").addQueryStringParameters("name" -> name.toString))
							given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
						}
					}
				}
				object dataItems {
					/** Gets a data item in a dataset by resource name. This API can be called after data are imported into dataset. */
					class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDatalabelingV1beta1DataItem]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDatalabelingV1beta1DataItem])
					}
					object get {
						def apply(projectsId :PlayApi, datasetsId :PlayApi, annotatedDatasetsId :PlayApi, dataItemsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/datasets/${datasetsId}/annotatedDatasets/${annotatedDatasetsId}/dataItems/${dataItemsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.GoogleCloudDatalabelingV1beta1DataItem]] = (fun: get) => fun.apply()
					}
					/** Lists data items in a dataset. This API can be called after data are imported into dataset. Pagination is supported. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDatalabelingV1beta1ListDataItemsResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Optional. Filter is not supported at this moment. */
						def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
						/** Optional. Requested page size. Server may return fewer results than requested. Default value is 100.<br>Format: int32 */
						def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
						/** Optional. A token identifying a page of results for the server to return. Typically obtained by ListDataItemsResponse.next_page_token of the previous [DataLabelingService.ListDataItems] call. Return first page if empty. */
						def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDatalabelingV1beta1ListDataItemsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, datasetsId :PlayApi, annotatedDatasetsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/datasets/${datasetsId}/annotatedDatasets/${annotatedDatasetsId}/dataItems").addQueryStringParameters("parent" -> parent.toString))
						given Conversion[list, Future[Schema.GoogleCloudDatalabelingV1beta1ListDataItemsResponse]] = (fun: list) => fun.apply()
					}
				}
				object examples {
					/** Gets an example by resource name, including both data and annotation. */
					class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDatalabelingV1beta1Example]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Optional. An expression for filtering Examples. Filter by annotation_spec.display_name is supported. Format "annotation_spec.display_name = {display_name}" */
						def withFilter(filter: String) = new get(req.addQueryStringParameters("filter" -> filter.toString))
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDatalabelingV1beta1Example])
					}
					object get {
						def apply(projectsId :PlayApi, datasetsId :PlayApi, annotatedDatasetsId :PlayApi, examplesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/datasets/${datasetsId}/annotatedDatasets/${annotatedDatasetsId}/examples/${examplesId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.GoogleCloudDatalabelingV1beta1Example]] = (fun: get) => fun.apply()
					}
					/** Lists examples in an annotated dataset. Pagination is supported. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDatalabelingV1beta1ListExamplesResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Optional. An expression for filtering Examples. For annotated datasets that have annotation spec set, filter by annotation_spec.display_name is supported. Format "annotation_spec.display_name = {display_name}" */
						def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
						/** Optional. Requested page size. Server may return fewer results than requested. Default value is 100.<br>Format: int32 */
						def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
						/** Optional. A token identifying a page of results for the server to return. Typically obtained by ListExamplesResponse.next_page_token of the previous [DataLabelingService.ListExamples] call. Return first page if empty. */
						def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDatalabelingV1beta1ListExamplesResponse])
					}
					object list {
						def apply(projectsId :PlayApi, datasetsId :PlayApi, annotatedDatasetsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/datasets/${datasetsId}/annotatedDatasets/${annotatedDatasetsId}/examples").addQueryStringParameters("parent" -> parent.toString))
						given Conversion[list, Future[Schema.GoogleCloudDatalabelingV1beta1ListExamplesResponse]] = (fun: list) => fun.apply()
					}
				}
			}
			object image {
				/** Starts a labeling task for image. The type of image labeling task is configured by feature in the request. */
				class label(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGoogleCloudDatalabelingV1beta1LabelImageRequest(body: Schema.GoogleCloudDatalabelingV1beta1LabelImageRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
				}
				object label {
					def apply(projectsId :PlayApi, datasetsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): label = new label(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/datasets/${datasetsId}/image:label").addQueryStringParameters("parent" -> parent.toString))
				}
			}
		}
		object evaluations {
			/** Searches evaluations within a project. */
			class search(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDatalabelingV1beta1SearchEvaluationsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Optional. To search evaluations, you can filter by the following: &#42; evaluation_job.evaluation_job_id (the last part of EvaluationJob.name) &#42; evaluation_job.model_id (the {model_name} portion of EvaluationJob.modelVersion) &#42; evaluation_job.evaluation_job_run_time_start (Minimum threshold for the evaluationJobRunTime that created the evaluation) &#42; evaluation_job.evaluation_job_run_time_end (Maximum threshold for the evaluationJobRunTime that created the evaluation) &#42; evaluation_job.job_state (EvaluationJob.state) &#42; annotation_spec.display_name (the Evaluation contains a metric for the annotation spec with this displayName) To filter by multiple critiera, use the `AND` operator or the `OR` operator. The following examples shows a string that filters by several critiera: "evaluation_job.evaluation_job_id = {evaluation_job_id} AND evaluation_job.model_id = {model_name} AND evaluation_job.evaluation_job_run_time_start = {timestamp_1} AND evaluation_job.evaluation_job_run_time_end = {timestamp_2} AND annotation_spec.display_name = {display_name}" */
				def withFilter(filter: String) = new search(req.addQueryStringParameters("filter" -> filter.toString))
				/** Optional. Requested page size. Server may return fewer results than requested. Default value is 100.<br>Format: int32 */
				def withPageSize(pageSize: Int) = new search(req.addQueryStringParameters("pageSize" -> pageSize.toString))
				/** Optional. A token identifying a page of results for the server to return. Typically obtained by the nextPageToken of the response to a previous search request. If you don't specify this field, the API call requests the first page of the search. */
				def withPageToken(pageToken: String) = new search(req.addQueryStringParameters("pageToken" -> pageToken.toString))
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDatalabelingV1beta1SearchEvaluationsResponse])
			}
			object search {
				def apply(projectsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): search = new search(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/evaluations:search").addQueryStringParameters("parent" -> parent.toString))
				given Conversion[search, Future[Schema.GoogleCloudDatalabelingV1beta1SearchEvaluationsResponse]] = (fun: search) => fun.apply()
			}
		}
		object instructions {
			/** Creates an instruction for how data should be labeled. */
			class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withGoogleCloudDatalabelingV1beta1CreateInstructionRequest(body: Schema.GoogleCloudDatalabelingV1beta1CreateInstructionRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
			}
			object create {
				def apply(projectsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/instructions").addQueryStringParameters("parent" -> parent.toString))
			}
			/** Gets an instruction by resource name. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDatalabelingV1beta1Instruction]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDatalabelingV1beta1Instruction])
			}
			object get {
				def apply(projectsId :PlayApi, instructionsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/instructions/${instructionsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.GoogleCloudDatalabelingV1beta1Instruction]] = (fun: get) => fun.apply()
			}
			/** Lists instructions for a project. Pagination is supported. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDatalabelingV1beta1ListInstructionsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Optional. Filter is not supported at this moment. */
				def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
				/** Optional. Requested page size. Server may return fewer results than requested. Default value is 100.<br>Format: int32 */
				def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
				/** Optional. A token identifying a page of results for the server to return. Typically obtained by ListInstructionsResponse.next_page_token of the previous [DataLabelingService.ListInstructions] call. Return first page if empty. */
				def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDatalabelingV1beta1ListInstructionsResponse])
			}
			object list {
				def apply(projectsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/instructions").addQueryStringParameters("parent" -> parent.toString))
				given Conversion[list, Future[Schema.GoogleCloudDatalabelingV1beta1ListInstructionsResponse]] = (fun: list) => fun.apply()
			}
			/** Deletes an instruction object by resource name. */
			class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
			}
			object delete {
				def apply(projectsId :PlayApi, instructionsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/instructions/${instructionsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
			}
		}
		object evaluationJobs {
			/** Resumes a paused evaluation job. A deleted evaluation job can't be resumed. Resuming a running or scheduled evaluation job is a no-op. */
			class resume(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withGoogleCloudDatalabelingV1beta1ResumeEvaluationJobRequest(body: Schema.GoogleCloudDatalabelingV1beta1ResumeEvaluationJobRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleProtobufEmpty])
			}
			object resume {
				def apply(projectsId :PlayApi, evaluationJobsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): resume = new resume(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/evaluationJobs/${evaluationJobsId}:resume").addQueryStringParameters("name" -> name.toString))
			}
			/** Stops and deletes an evaluation job. */
			class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
			}
			object delete {
				def apply(projectsId :PlayApi, evaluationJobsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/evaluationJobs/${evaluationJobsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
			}
			/** Gets an evaluation job by resource name. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDatalabelingV1beta1EvaluationJob]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDatalabelingV1beta1EvaluationJob])
			}
			object get {
				def apply(projectsId :PlayApi, evaluationJobsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/evaluationJobs/${evaluationJobsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.GoogleCloudDatalabelingV1beta1EvaluationJob]] = (fun: get) => fun.apply()
			}
			/** Updates an evaluation job. You can only update certain fields of the job's EvaluationJobConfig: `humanAnnotationConfig.instruction`, `exampleCount`, and `exampleSamplePercentage`. If you want to change any other aspect of the evaluation job, you must delete the job and create a new one. */
			class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Optional. Mask for which fields to update. You can only provide the following fields: &#42; `evaluationJobConfig.humanAnnotationConfig.instruction` &#42; `evaluationJobConfig.exampleCount` &#42; `evaluationJobConfig.exampleSamplePercentage` You can provide more than one of these fields by separating them with commas.<br>Format: google-fieldmask */
				def withUpdateMask(updateMask: String) = new patch(req.addQueryStringParameters("updateMask" -> updateMask.toString))
				/** Perform the request */
				def withGoogleCloudDatalabelingV1beta1EvaluationJob(body: Schema.GoogleCloudDatalabelingV1beta1EvaluationJob) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudDatalabelingV1beta1EvaluationJob])
			}
			object patch {
				def apply(projectsId :PlayApi, evaluationJobsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/evaluationJobs/${evaluationJobsId}").addQueryStringParameters("name" -> name.toString))
			}
			/** Lists all evaluation jobs within a project with possible filters. Pagination is supported. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDatalabelingV1beta1ListEvaluationJobsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Optional. You can filter the jobs to list by model_id (also known as model_name, as described in EvaluationJob.modelVersion) or by evaluation job state (as described in EvaluationJob.state). To filter by both criteria, use the `AND` operator or the `OR` operator. For example, you can use the following string for your filter: "evaluation_job.model_id = {model_name} AND evaluation_job.state = {evaluation_job_state}" */
				def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
				/** Optional. Requested page size. Server may return fewer results than requested. Default value is 100.<br>Format: int32 */
				def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
				/** Optional. A token identifying a page of results for the server to return. Typically obtained by the nextPageToken in the response to the previous request. The request returns the first page if this is empty. */
				def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDatalabelingV1beta1ListEvaluationJobsResponse])
			}
			object list {
				def apply(projectsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/evaluationJobs").addQueryStringParameters("parent" -> parent.toString))
				given Conversion[list, Future[Schema.GoogleCloudDatalabelingV1beta1ListEvaluationJobsResponse]] = (fun: list) => fun.apply()
			}
			/** Pauses an evaluation job. Pausing an evaluation job that is already in a `PAUSED` state is a no-op. */
			class pause(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withGoogleCloudDatalabelingV1beta1PauseEvaluationJobRequest(body: Schema.GoogleCloudDatalabelingV1beta1PauseEvaluationJobRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleProtobufEmpty])
			}
			object pause {
				def apply(projectsId :PlayApi, evaluationJobsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): pause = new pause(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/evaluationJobs/${evaluationJobsId}:pause").addQueryStringParameters("name" -> name.toString))
			}
			/** Creates an evaluation job. */
			class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withGoogleCloudDatalabelingV1beta1CreateEvaluationJobRequest(body: Schema.GoogleCloudDatalabelingV1beta1CreateEvaluationJobRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudDatalabelingV1beta1EvaluationJob])
			}
			object create {
				def apply(projectsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/evaluationJobs").addQueryStringParameters("parent" -> parent.toString))
			}
		}
	}
}
