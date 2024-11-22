package com.boresjo.play.api.google.vision

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
		"""https://www.googleapis.com/auth/cloud-vision""" /* Apply machine learning models to understand and label images */
	)

	private val BASE_URL = "https://vision.googleapis.com/"

	object operations {
		/** Lists operations that match the specified filter in the request. If the server doesn't support this method, it returns `UNIMPLEMENTED`. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListOperationsResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-vision""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListOperationsResponse])
		}
		object list {
			def apply(name: String, filter: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/operations").addQueryStringParameters("name" -> name.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
			given Conversion[list, Future[Schema.ListOperationsResponse]] = (fun: list) => fun.apply()
		}
		/** Gets the latest state of a long-running operation. Clients can use this method to poll the operation result at intervals as recommended by the API service. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-vision""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Operation])
		}
		object get {
			def apply(operationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
			given Conversion[get, Future[Schema.Operation]] = (fun: get) => fun.apply()
		}
		/** Deletes a long-running operation. This method indicates that the client is no longer interested in the operation result. It does not cancel the operation. If the server doesn't support this method, it returns `google.rpc.Code.UNIMPLEMENTED`. */
		class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-vision""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
		}
		object delete {
			def apply(operationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
			given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
		}
		/** Starts asynchronous cancellation on a long-running operation. The server makes a best effort to cancel the operation, but success is not guaranteed. If the server doesn't support this method, it returns `google.rpc.Code.UNIMPLEMENTED`. Clients can use Operations.GetOperation or other methods to check whether the cancellation succeeded or whether the operation completed despite cancellation. On successful cancellation, the operation is not deleted; instead, it becomes an operation with an Operation.error value with a google.rpc.Status.code of 1, corresponding to `Code.CANCELLED`. */
		class cancel(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-vision""")
			/** Perform the request */
			def withCancelOperationRequest(body: Schema.CancelOperationRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Empty])
		}
		object cancel {
			def apply(operationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): cancel = new cancel(ws.url(BASE_URL + s"v1/operations/${operationsId}:cancel").addQueryStringParameters("name" -> name.toString))
		}
	}
	object projects {
		object operations {
			/** Gets the latest state of a long-running operation. Clients can use this method to poll the operation result at intervals as recommended by the API service. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-vision""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Operation])
			}
			object get {
				def apply(projectsId :PlayApi, operationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.Operation]] = (fun: get) => fun.apply()
			}
		}
		object locations {
			object operations {
				/** Gets the latest state of a long-running operation. Clients can use this method to poll the operation result at intervals as recommended by the API service. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-vision""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Operation])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, operationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.Operation]] = (fun: get) => fun.apply()
				}
			}
			object products {
				/** Creates and returns a new product resource. Possible errors: &#42; Returns INVALID_ARGUMENT if display_name is missing or longer than 4096 characters. &#42; Returns INVALID_ARGUMENT if description is longer than 4096 characters. &#42; Returns INVALID_ARGUMENT if product_category is missing or invalid. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-vision""")
					/** Perform the request */
					def withProduct(body: Schema.Product) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Product])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, productId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/products").addQueryStringParameters("parent" -> parent.toString, "productId" -> productId.toString))
				}
				/** Asynchronous API to delete all Products in a ProductSet or all Products that are in no ProductSet. If a Product is a member of the specified ProductSet in addition to other ProductSets, the Product will still be deleted. It is recommended to not delete the specified ProductSet until after this operation has completed. It is also recommended to not add any of the Products involved in the batch delete to a new ProductSet while this operation is running because those Products may still end up deleted. It's not possible to undo the PurgeProducts operation. Therefore, it is recommended to keep the csv files used in ImportProductSets (if that was how you originally built the Product Set) before starting PurgeProducts, in case you need to re-import the data after deletion. If the plan is to purge all of the Products from a ProductSet and then re-use the empty ProductSet to re-import new Products into the empty ProductSet, you must wait until the PurgeProducts operation has finished for that ProductSet. The google.longrunning.Operation API can be used to keep track of the progress and results of the request. `Operation.metadata` contains `BatchOperationMetadata`. (progress) */
				class purge(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-vision""")
					/** Perform the request */
					def withPurgeProductsRequest(body: Schema.PurgeProductsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object purge {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): purge = new purge(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/products:purge").addQueryStringParameters("parent" -> parent.toString))
				}
				/** Permanently deletes a product and its reference images. Metadata of the product and all its images will be deleted right away, but search queries against ProductSets containing the product may still work until all related caches are refreshed. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-vision""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, productsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/products/${productsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
				}
				/** Gets information associated with a Product. Possible errors: &#42; Returns NOT_FOUND if the Product does not exist. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Product]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-vision""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Product])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, productsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/products/${productsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.Product]] = (fun: get) => fun.apply()
				}
				/** Makes changes to a Product resource. Only the `display_name`, `description`, and `labels` fields can be updated right now. If labels are updated, the change will not be reflected in queries until the next index time. Possible errors: &#42; Returns NOT_FOUND if the Product does not exist. &#42; Returns INVALID_ARGUMENT if display_name is present in update_mask but is missing from the request or longer than 4096 characters. &#42; Returns INVALID_ARGUMENT if description is present in update_mask but is longer than 4096 characters. &#42; Returns INVALID_ARGUMENT if product_category is present in update_mask. */
				class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-vision""")
					/** Perform the request */
					def withProduct(body: Schema.Product) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Product])
				}
				object patch {
					def apply(projectsId :PlayApi, locationsId :PlayApi, productsId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/products/${productsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
				}
				/** Lists products in an unspecified order. Possible errors: &#42; Returns INVALID_ARGUMENT if page_size is greater than 100 or less than 1. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListProductsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-vision""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListProductsResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/products").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListProductsResponse]] = (fun: list) => fun.apply()
				}
				object referenceImages {
					/** Creates and returns a new ReferenceImage resource. The `bounding_poly` field is optional. If `bounding_poly` is not specified, the system will try to detect regions of interest in the image that are compatible with the product_category on the parent product. If it is specified, detection is ALWAYS skipped. The system converts polygons into non-rotated rectangles. Note that the pipeline will resize the image if the image resolution is too large to process (above 50MP). Possible errors: &#42; Returns INVALID_ARGUMENT if the image_uri is missing or longer than 4096 characters. &#42; Returns INVALID_ARGUMENT if the product does not exist. &#42; Returns INVALID_ARGUMENT if bounding_poly is not provided, and nothing compatible with the parent product's product_category is detected. &#42; Returns INVALID_ARGUMENT if bounding_poly contains more than 10 polygons. */
					class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-vision""")
						/** Perform the request */
						def withReferenceImage(body: Schema.ReferenceImage) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.ReferenceImage])
					}
					object create {
						def apply(projectsId :PlayApi, locationsId :PlayApi, productsId :PlayApi, parent: String, referenceImageId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/products/${productsId}/referenceImages").addQueryStringParameters("parent" -> parent.toString, "referenceImageId" -> referenceImageId.toString))
					}
					/** Permanently deletes a reference image. The image metadata will be deleted right away, but search queries against ProductSets containing the image may still work until all related caches are refreshed. The actual image files are not deleted from Google Cloud Storage. */
					class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-vision""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
					}
					object delete {
						def apply(projectsId :PlayApi, locationsId :PlayApi, productsId :PlayApi, referenceImagesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/products/${productsId}/referenceImages/${referenceImagesId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
					}
					/** Lists reference images. Possible errors: &#42; Returns NOT_FOUND if the parent product does not exist. &#42; Returns INVALID_ARGUMENT if the page_size is greater than 100, or less than 1. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListReferenceImagesResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-vision""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListReferenceImagesResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, productsId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/products/${productsId}/referenceImages").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
						given Conversion[list, Future[Schema.ListReferenceImagesResponse]] = (fun: list) => fun.apply()
					}
					/** Gets information associated with a ReferenceImage. Possible errors: &#42; Returns NOT_FOUND if the specified image does not exist. */
					class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ReferenceImage]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-vision""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ReferenceImage])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, productsId :PlayApi, referenceImagesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/products/${productsId}/referenceImages/${referenceImagesId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.ReferenceImage]] = (fun: get) => fun.apply()
					}
				}
			}
			object productSets {
				/** Adds a Product to the specified ProductSet. If the Product is already present, no change is made. One Product can be added to at most 100 ProductSets. Possible errors: &#42; Returns NOT_FOUND if the Product or the ProductSet doesn't exist. */
				class addProduct(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-vision""")
					/** Perform the request */
					def withAddProductToProductSetRequest(body: Schema.AddProductToProductSetRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Empty])
				}
				object addProduct {
					def apply(projectsId :PlayApi, locationsId :PlayApi, productSetsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): addProduct = new addProduct(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/productSets/${productSetsId}:addProduct").addQueryStringParameters("name" -> name.toString))
				}
				/** Creates and returns a new ProductSet resource. Possible errors: &#42; Returns INVALID_ARGUMENT if display_name is missing, or is longer than 4096 characters. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-vision""")
					/** Perform the request */
					def withProductSet(body: Schema.ProductSet) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.ProductSet])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, productSetId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/productSets").addQueryStringParameters("parent" -> parent.toString, "productSetId" -> productSetId.toString))
				}
				/** Asynchronous API that imports a list of reference images to specified product sets based on a list of image information. The google.longrunning.Operation API can be used to keep track of the progress and results of the request. `Operation.metadata` contains `BatchOperationMetadata`. (progress) `Operation.response` contains `ImportProductSetsResponse`. (results) The input source of this method is a csv file on Google Cloud Storage. For the format of the csv file please see ImportProductSetsGcsSource.csv_file_uri. */
				class `import`(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-vision""")
					/** Perform the request */
					def withImportProductSetsRequest(body: Schema.ImportProductSetsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object `import` {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): `import` = new `import`(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/productSets:import").addQueryStringParameters("parent" -> parent.toString))
				}
				/** Permanently deletes a ProductSet. Products and ReferenceImages in the ProductSet are not deleted. The actual image files are not deleted from Google Cloud Storage. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-vision""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, productSetsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/productSets/${productSetsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
				}
				/** Gets information associated with a ProductSet. Possible errors: &#42; Returns NOT_FOUND if the ProductSet does not exist. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ProductSet]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-vision""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ProductSet])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, productSetsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/productSets/${productSetsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.ProductSet]] = (fun: get) => fun.apply()
				}
				/** Makes changes to a ProductSet resource. Only display_name can be updated currently. Possible errors: &#42; Returns NOT_FOUND if the ProductSet does not exist. &#42; Returns INVALID_ARGUMENT if display_name is present in update_mask but missing from the request or longer than 4096 characters. */
				class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-vision""")
					/** Perform the request */
					def withProductSet(body: Schema.ProductSet) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.ProductSet])
				}
				object patch {
					def apply(projectsId :PlayApi, locationsId :PlayApi, productSetsId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/productSets/${productSetsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
				}
				/** Lists ProductSets in an unspecified order. Possible errors: &#42; Returns INVALID_ARGUMENT if page_size is greater than 100, or less than 1. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListProductSetsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-vision""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListProductSetsResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/productSets").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListProductSetsResponse]] = (fun: list) => fun.apply()
				}
				/** Removes a Product from the specified ProductSet. */
				class removeProduct(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-vision""")
					/** Perform the request */
					def withRemoveProductFromProductSetRequest(body: Schema.RemoveProductFromProductSetRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Empty])
				}
				object removeProduct {
					def apply(projectsId :PlayApi, locationsId :PlayApi, productSetsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): removeProduct = new removeProduct(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/productSets/${productSetsId}:removeProduct").addQueryStringParameters("name" -> name.toString))
				}
				object products {
					/** Lists the Products in a ProductSet, in an unspecified order. If the ProductSet does not exist, the products field of the response will be empty. Possible errors: &#42; Returns INVALID_ARGUMENT if page_size is greater than 100 or less than 1. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListProductsInProductSetResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-vision""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListProductsInProductSetResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, productSetsId :PlayApi, name: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/productSets/${productSetsId}/products").addQueryStringParameters("name" -> name.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
						given Conversion[list, Future[Schema.ListProductsInProductSetResponse]] = (fun: list) => fun.apply()
					}
				}
			}
			object files {
				/** Service that performs image detection and annotation for a batch of files. Now only "application/pdf", "image/tiff" and "image/gif" are supported. This service will extract at most 5 (customers can specify which 5 in AnnotateFileRequest.pages) frames (gif) or pages (pdf or tiff) from each file provided and perform detection and annotation for each image extracted. */
				class annotate(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-vision""")
					/** Optional. Target project and location to make a call. Format: `projects/{project-id}/locations/{location-id}`. If no parent is specified, a region will be chosen automatically. Supported location-ids: `us`: USA country only, `asia`: East asia areas, like Japan, Taiwan, `eu`: The European Union. Example: `projects/project-A/locations/eu`. */
					def withParent(parent: String) = new annotate(req.addQueryStringParameters("parent" -> parent.toString))
					/** Perform the request */
					def withBatchAnnotateFilesRequest(body: Schema.BatchAnnotateFilesRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.BatchAnnotateFilesResponse])
				}
				object annotate {
					def apply(projectsId :PlayApi, locationsId :PlayApi)(using signer: RequestSigner, ec: ExecutionContext): annotate = new annotate(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/files:annotate").addQueryStringParameters())
				}
				/** Run asynchronous image detection and annotation for a list of generic files, such as PDF files, which may contain multiple pages and multiple images per page. Progress and results can be retrieved through the `google.longrunning.Operations` interface. `Operation.metadata` contains `OperationMetadata` (metadata). `Operation.response` contains `AsyncBatchAnnotateFilesResponse` (results). */
				class asyncBatchAnnotate(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-vision""")
					/** Optional. Target project and location to make a call. Format: `projects/{project-id}/locations/{location-id}`. If no parent is specified, a region will be chosen automatically. Supported location-ids: `us`: USA country only, `asia`: East asia areas, like Japan, Taiwan, `eu`: The European Union. Example: `projects/project-A/locations/eu`. */
					def withParent(parent: String) = new asyncBatchAnnotate(req.addQueryStringParameters("parent" -> parent.toString))
					/** Perform the request */
					def withAsyncBatchAnnotateFilesRequest(body: Schema.AsyncBatchAnnotateFilesRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object asyncBatchAnnotate {
					def apply(projectsId :PlayApi, locationsId :PlayApi)(using signer: RequestSigner, ec: ExecutionContext): asyncBatchAnnotate = new asyncBatchAnnotate(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/files:asyncBatchAnnotate").addQueryStringParameters())
				}
			}
			object images {
				/** Run image detection and annotation for a batch of images. */
				class annotate(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-vision""")
					/** Optional. Target project and location to make a call. Format: `projects/{project-id}/locations/{location-id}`. If no parent is specified, a region will be chosen automatically. Supported location-ids: `us`: USA country only, `asia`: East asia areas, like Japan, Taiwan, `eu`: The European Union. Example: `projects/project-A/locations/eu`. */
					def withParent(parent: String) = new annotate(req.addQueryStringParameters("parent" -> parent.toString))
					/** Perform the request */
					def withBatchAnnotateImagesRequest(body: Schema.BatchAnnotateImagesRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.BatchAnnotateImagesResponse])
				}
				object annotate {
					def apply(projectsId :PlayApi, locationsId :PlayApi)(using signer: RequestSigner, ec: ExecutionContext): annotate = new annotate(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/images:annotate").addQueryStringParameters())
				}
				/** Run asynchronous image detection and annotation for a list of images. Progress and results can be retrieved through the `google.longrunning.Operations` interface. `Operation.metadata` contains `OperationMetadata` (metadata). `Operation.response` contains `AsyncBatchAnnotateImagesResponse` (results). This service will write image annotation outputs to json files in customer GCS bucket, each json file containing BatchAnnotateImagesResponse proto. */
				class asyncBatchAnnotate(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-vision""")
					/** Optional. Target project and location to make a call. Format: `projects/{project-id}/locations/{location-id}`. If no parent is specified, a region will be chosen automatically. Supported location-ids: `us`: USA country only, `asia`: East asia areas, like Japan, Taiwan, `eu`: The European Union. Example: `projects/project-A/locations/eu`. */
					def withParent(parent: String) = new asyncBatchAnnotate(req.addQueryStringParameters("parent" -> parent.toString))
					/** Perform the request */
					def withAsyncBatchAnnotateImagesRequest(body: Schema.AsyncBatchAnnotateImagesRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object asyncBatchAnnotate {
					def apply(projectsId :PlayApi, locationsId :PlayApi)(using signer: RequestSigner, ec: ExecutionContext): asyncBatchAnnotate = new asyncBatchAnnotate(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/images:asyncBatchAnnotate").addQueryStringParameters())
				}
			}
		}
		object images {
			/** Run image detection and annotation for a batch of images. */
			class annotate(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-vision""")
				/** Optional. Target project and location to make a call. Format: `projects/{project-id}/locations/{location-id}`. If no parent is specified, a region will be chosen automatically. Supported location-ids: `us`: USA country only, `asia`: East asia areas, like Japan, Taiwan, `eu`: The European Union. Example: `projects/project-A/locations/eu`. */
				def withParent(parent: String) = new annotate(req.addQueryStringParameters("parent" -> parent.toString))
				/** Perform the request */
				def withBatchAnnotateImagesRequest(body: Schema.BatchAnnotateImagesRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.BatchAnnotateImagesResponse])
			}
			object annotate {
				def apply(projectsId :PlayApi)(using signer: RequestSigner, ec: ExecutionContext): annotate = new annotate(ws.url(BASE_URL + s"v1/projects/${projectsId}/images:annotate").addQueryStringParameters())
			}
			/** Run asynchronous image detection and annotation for a list of images. Progress and results can be retrieved through the `google.longrunning.Operations` interface. `Operation.metadata` contains `OperationMetadata` (metadata). `Operation.response` contains `AsyncBatchAnnotateImagesResponse` (results). This service will write image annotation outputs to json files in customer GCS bucket, each json file containing BatchAnnotateImagesResponse proto. */
			class asyncBatchAnnotate(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-vision""")
				/** Optional. Target project and location to make a call. Format: `projects/{project-id}/locations/{location-id}`. If no parent is specified, a region will be chosen automatically. Supported location-ids: `us`: USA country only, `asia`: East asia areas, like Japan, Taiwan, `eu`: The European Union. Example: `projects/project-A/locations/eu`. */
				def withParent(parent: String) = new asyncBatchAnnotate(req.addQueryStringParameters("parent" -> parent.toString))
				/** Perform the request */
				def withAsyncBatchAnnotateImagesRequest(body: Schema.AsyncBatchAnnotateImagesRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
			}
			object asyncBatchAnnotate {
				def apply(projectsId :PlayApi)(using signer: RequestSigner, ec: ExecutionContext): asyncBatchAnnotate = new asyncBatchAnnotate(ws.url(BASE_URL + s"v1/projects/${projectsId}/images:asyncBatchAnnotate").addQueryStringParameters())
			}
		}
		object files {
			/** Service that performs image detection and annotation for a batch of files. Now only "application/pdf", "image/tiff" and "image/gif" are supported. This service will extract at most 5 (customers can specify which 5 in AnnotateFileRequest.pages) frames (gif) or pages (pdf or tiff) from each file provided and perform detection and annotation for each image extracted. */
			class annotate(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-vision""")
				/** Optional. Target project and location to make a call. Format: `projects/{project-id}/locations/{location-id}`. If no parent is specified, a region will be chosen automatically. Supported location-ids: `us`: USA country only, `asia`: East asia areas, like Japan, Taiwan, `eu`: The European Union. Example: `projects/project-A/locations/eu`. */
				def withParent(parent: String) = new annotate(req.addQueryStringParameters("parent" -> parent.toString))
				/** Perform the request */
				def withBatchAnnotateFilesRequest(body: Schema.BatchAnnotateFilesRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.BatchAnnotateFilesResponse])
			}
			object annotate {
				def apply(projectsId :PlayApi)(using signer: RequestSigner, ec: ExecutionContext): annotate = new annotate(ws.url(BASE_URL + s"v1/projects/${projectsId}/files:annotate").addQueryStringParameters())
			}
			/** Run asynchronous image detection and annotation for a list of generic files, such as PDF files, which may contain multiple pages and multiple images per page. Progress and results can be retrieved through the `google.longrunning.Operations` interface. `Operation.metadata` contains `OperationMetadata` (metadata). `Operation.response` contains `AsyncBatchAnnotateFilesResponse` (results). */
			class asyncBatchAnnotate(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-vision""")
				/** Optional. Target project and location to make a call. Format: `projects/{project-id}/locations/{location-id}`. If no parent is specified, a region will be chosen automatically. Supported location-ids: `us`: USA country only, `asia`: East asia areas, like Japan, Taiwan, `eu`: The European Union. Example: `projects/project-A/locations/eu`. */
				def withParent(parent: String) = new asyncBatchAnnotate(req.addQueryStringParameters("parent" -> parent.toString))
				/** Perform the request */
				def withAsyncBatchAnnotateFilesRequest(body: Schema.AsyncBatchAnnotateFilesRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
			}
			object asyncBatchAnnotate {
				def apply(projectsId :PlayApi)(using signer: RequestSigner, ec: ExecutionContext): asyncBatchAnnotate = new asyncBatchAnnotate(ws.url(BASE_URL + s"v1/projects/${projectsId}/files:asyncBatchAnnotate").addQueryStringParameters())
			}
		}
	}
	object locations {
		object operations {
			/** Gets the latest state of a long-running operation. Clients can use this method to poll the operation result at intervals as recommended by the API service. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-vision""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Operation])
			}
			object get {
				def apply(locationsId :PlayApi, operationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/locations/${locationsId}/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.Operation]] = (fun: get) => fun.apply()
			}
		}
	}
	object images {
		/** Run image detection and annotation for a batch of images. */
		class annotate(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-vision""")
			/** Perform the request */
			def withBatchAnnotateImagesRequest(body: Schema.BatchAnnotateImagesRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.BatchAnnotateImagesResponse])
		}
		object annotate {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): annotate = new annotate(ws.url(BASE_URL + s"v1/images:annotate").addQueryStringParameters())
		}
		/** Run asynchronous image detection and annotation for a list of images. Progress and results can be retrieved through the `google.longrunning.Operations` interface. `Operation.metadata` contains `OperationMetadata` (metadata). `Operation.response` contains `AsyncBatchAnnotateImagesResponse` (results). This service will write image annotation outputs to json files in customer GCS bucket, each json file containing BatchAnnotateImagesResponse proto. */
		class asyncBatchAnnotate(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-vision""")
			/** Perform the request */
			def withAsyncBatchAnnotateImagesRequest(body: Schema.AsyncBatchAnnotateImagesRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
		}
		object asyncBatchAnnotate {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): asyncBatchAnnotate = new asyncBatchAnnotate(ws.url(BASE_URL + s"v1/images:asyncBatchAnnotate").addQueryStringParameters())
		}
	}
	object files {
		/** Service that performs image detection and annotation for a batch of files. Now only "application/pdf", "image/tiff" and "image/gif" are supported. This service will extract at most 5 (customers can specify which 5 in AnnotateFileRequest.pages) frames (gif) or pages (pdf or tiff) from each file provided and perform detection and annotation for each image extracted. */
		class annotate(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-vision""")
			/** Perform the request */
			def withBatchAnnotateFilesRequest(body: Schema.BatchAnnotateFilesRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.BatchAnnotateFilesResponse])
		}
		object annotate {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): annotate = new annotate(ws.url(BASE_URL + s"v1/files:annotate").addQueryStringParameters())
		}
		/** Run asynchronous image detection and annotation for a list of generic files, such as PDF files, which may contain multiple pages and multiple images per page. Progress and results can be retrieved through the `google.longrunning.Operations` interface. `Operation.metadata` contains `OperationMetadata` (metadata). `Operation.response` contains `AsyncBatchAnnotateFilesResponse` (results). */
		class asyncBatchAnnotate(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-vision""")
			/** Perform the request */
			def withAsyncBatchAnnotateFilesRequest(body: Schema.AsyncBatchAnnotateFilesRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
		}
		object asyncBatchAnnotate {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): asyncBatchAnnotate = new asyncBatchAnnotate(ws.url(BASE_URL + s"v1/files:asyncBatchAnnotate").addQueryStringParameters())
		}
	}
}
