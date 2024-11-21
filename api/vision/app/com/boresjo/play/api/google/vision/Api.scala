package com.boresjo.play.api.google.vision

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://vision.googleapis.com/"

	object operations {
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListOperationsResponse]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.ListOperationsResponse])
		}
		object list {
			def apply(name: String, filter: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/operations")).addQueryStringParameters("name" -> name.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
			given Conversion[list, Future[Schema.ListOperationsResponse]] = (fun: list) => fun.apply()
		}
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.Operation])
		}
		object get {
			def apply(operationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/operations/${operationsId}")).addQueryStringParameters("name" -> name.toString))
			given Conversion[get, Future[Schema.Operation]] = (fun: get) => fun.apply()
		}
		class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
			def apply() = req.execute("DELETE").map(_.json.as[Schema.Empty])
		}
		object delete {
			def apply(operationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/operations/${operationsId}")).addQueryStringParameters("name" -> name.toString))
			given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
		}
		class cancel(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withCancelOperationRequest(body: Schema.CancelOperationRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Empty])
		}
		object cancel {
			def apply(operationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): cancel = new cancel(auth(ws.url(BASE_URL + s"v1/operations/${operationsId}:cancel")).addQueryStringParameters("name" -> name.toString))
		}
	}
	object projects {
		object operations {
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.Operation])
			}
			object get {
				def apply(projectsId :PlayApi, operationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/operations/${operationsId}")).addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.Operation]] = (fun: get) => fun.apply()
			}
		}
		object locations {
			object operations {
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.Operation])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, operationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/operations/${operationsId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.Operation]] = (fun: get) => fun.apply()
				}
			}
			object products {
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withProduct(body: Schema.Product) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Product])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, productId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/products")).addQueryStringParameters("parent" -> parent.toString, "productId" -> productId.toString))
				}
				class purge(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withPurgeProductsRequest(body: Schema.PurgeProductsRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Operation])
				}
				object purge {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): purge = new purge(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/products:purge")).addQueryStringParameters("parent" -> parent.toString))
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					def apply() = req.execute("DELETE").map(_.json.as[Schema.Empty])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, productsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/products/${productsId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Product]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.Product])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, productsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/products/${productsId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.Product]] = (fun: get) => fun.apply()
				}
				class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withProduct(body: Schema.Product) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.Product])
				}
				object patch {
					def apply(projectsId :PlayApi, locationsId :PlayApi, productsId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/products/${productsId}")).addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListProductsResponse]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.ListProductsResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/products")).addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListProductsResponse]] = (fun: list) => fun.apply()
				}
				object referenceImages {
					class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withReferenceImage(body: Schema.ReferenceImage) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.ReferenceImage])
					}
					object create {
						def apply(projectsId :PlayApi, locationsId :PlayApi, productsId :PlayApi, parent: String, referenceImageId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/products/${productsId}/referenceImages")).addQueryStringParameters("parent" -> parent.toString, "referenceImageId" -> referenceImageId.toString))
					}
					class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
						def apply() = req.execute("DELETE").map(_.json.as[Schema.Empty])
					}
					object delete {
						def apply(projectsId :PlayApi, locationsId :PlayApi, productsId :PlayApi, referenceImagesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/products/${productsId}/referenceImages/${referenceImagesId}")).addQueryStringParameters("name" -> name.toString))
						given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
					}
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListReferenceImagesResponse]) {
						def apply() = req.execute("GET").map(_.json.as[Schema.ListReferenceImagesResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, productsId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/products/${productsId}/referenceImages")).addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
						given Conversion[list, Future[Schema.ListReferenceImagesResponse]] = (fun: list) => fun.apply()
					}
					class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ReferenceImage]) {
						def apply() = req.execute("GET").map(_.json.as[Schema.ReferenceImage])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, productsId :PlayApi, referenceImagesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/products/${productsId}/referenceImages/${referenceImagesId}")).addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.ReferenceImage]] = (fun: get) => fun.apply()
					}
				}
			}
			object productSets {
				class addProduct(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withAddProductToProductSetRequest(body: Schema.AddProductToProductSetRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Empty])
				}
				object addProduct {
					def apply(projectsId :PlayApi, locationsId :PlayApi, productSetsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): addProduct = new addProduct(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/productSets/${productSetsId}:addProduct")).addQueryStringParameters("name" -> name.toString))
				}
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withProductSet(body: Schema.ProductSet) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.ProductSet])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, productSetId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/productSets")).addQueryStringParameters("parent" -> parent.toString, "productSetId" -> productSetId.toString))
				}
				class `import`(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withImportProductSetsRequest(body: Schema.ImportProductSetsRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Operation])
				}
				object `import` {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): `import` = new `import`(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/productSets:import")).addQueryStringParameters("parent" -> parent.toString))
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					def apply() = req.execute("DELETE").map(_.json.as[Schema.Empty])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, productSetsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/productSets/${productSetsId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ProductSet]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.ProductSet])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, productSetsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/productSets/${productSetsId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.ProductSet]] = (fun: get) => fun.apply()
				}
				class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withProductSet(body: Schema.ProductSet) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.ProductSet])
				}
				object patch {
					def apply(projectsId :PlayApi, locationsId :PlayApi, productSetsId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/productSets/${productSetsId}")).addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListProductSetsResponse]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.ListProductSetsResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/productSets")).addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListProductSetsResponse]] = (fun: list) => fun.apply()
				}
				class removeProduct(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withRemoveProductFromProductSetRequest(body: Schema.RemoveProductFromProductSetRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Empty])
				}
				object removeProduct {
					def apply(projectsId :PlayApi, locationsId :PlayApi, productSetsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): removeProduct = new removeProduct(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/productSets/${productSetsId}:removeProduct")).addQueryStringParameters("name" -> name.toString))
				}
				object products {
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListProductsInProductSetResponse]) {
						def apply() = req.execute("GET").map(_.json.as[Schema.ListProductsInProductSetResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, productSetsId :PlayApi, name: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/productSets/${productSetsId}/products")).addQueryStringParameters("name" -> name.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
						given Conversion[list, Future[Schema.ListProductsInProductSetResponse]] = (fun: list) => fun.apply()
					}
				}
			}
			object files {
				class annotate(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					/** Optional. Target project and location to make a call. Format: `projects/{project-id}/locations/{location-id}`. If no parent is specified, a region will be chosen automatically. Supported location-ids: `us`: USA country only, `asia`: East asia areas, like Japan, Taiwan, `eu`: The European Union. Example: `projects/project-A/locations/eu`. */
					def withParent(parent: String) = new annotate(req.addQueryStringParameters("parent" -> parent.toString))
					def withBatchAnnotateFilesRequest(body: Schema.BatchAnnotateFilesRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.BatchAnnotateFilesResponse])
				}
				object annotate {
					def apply(projectsId :PlayApi, locationsId :PlayApi)(using auth: AuthToken, ec: ExecutionContext): annotate = new annotate(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/files:annotate")).addQueryStringParameters())
				}
				class asyncBatchAnnotate(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					/** Optional. Target project and location to make a call. Format: `projects/{project-id}/locations/{location-id}`. If no parent is specified, a region will be chosen automatically. Supported location-ids: `us`: USA country only, `asia`: East asia areas, like Japan, Taiwan, `eu`: The European Union. Example: `projects/project-A/locations/eu`. */
					def withParent(parent: String) = new asyncBatchAnnotate(req.addQueryStringParameters("parent" -> parent.toString))
					def withAsyncBatchAnnotateFilesRequest(body: Schema.AsyncBatchAnnotateFilesRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Operation])
				}
				object asyncBatchAnnotate {
					def apply(projectsId :PlayApi, locationsId :PlayApi)(using auth: AuthToken, ec: ExecutionContext): asyncBatchAnnotate = new asyncBatchAnnotate(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/files:asyncBatchAnnotate")).addQueryStringParameters())
				}
			}
			object images {
				class annotate(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					/** Optional. Target project and location to make a call. Format: `projects/{project-id}/locations/{location-id}`. If no parent is specified, a region will be chosen automatically. Supported location-ids: `us`: USA country only, `asia`: East asia areas, like Japan, Taiwan, `eu`: The European Union. Example: `projects/project-A/locations/eu`. */
					def withParent(parent: String) = new annotate(req.addQueryStringParameters("parent" -> parent.toString))
					def withBatchAnnotateImagesRequest(body: Schema.BatchAnnotateImagesRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.BatchAnnotateImagesResponse])
				}
				object annotate {
					def apply(projectsId :PlayApi, locationsId :PlayApi)(using auth: AuthToken, ec: ExecutionContext): annotate = new annotate(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/images:annotate")).addQueryStringParameters())
				}
				class asyncBatchAnnotate(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					/** Optional. Target project and location to make a call. Format: `projects/{project-id}/locations/{location-id}`. If no parent is specified, a region will be chosen automatically. Supported location-ids: `us`: USA country only, `asia`: East asia areas, like Japan, Taiwan, `eu`: The European Union. Example: `projects/project-A/locations/eu`. */
					def withParent(parent: String) = new asyncBatchAnnotate(req.addQueryStringParameters("parent" -> parent.toString))
					def withAsyncBatchAnnotateImagesRequest(body: Schema.AsyncBatchAnnotateImagesRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Operation])
				}
				object asyncBatchAnnotate {
					def apply(projectsId :PlayApi, locationsId :PlayApi)(using auth: AuthToken, ec: ExecutionContext): asyncBatchAnnotate = new asyncBatchAnnotate(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/images:asyncBatchAnnotate")).addQueryStringParameters())
				}
			}
		}
		object images {
			class annotate(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				/** Optional. Target project and location to make a call. Format: `projects/{project-id}/locations/{location-id}`. If no parent is specified, a region will be chosen automatically. Supported location-ids: `us`: USA country only, `asia`: East asia areas, like Japan, Taiwan, `eu`: The European Union. Example: `projects/project-A/locations/eu`. */
				def withParent(parent: String) = new annotate(req.addQueryStringParameters("parent" -> parent.toString))
				def withBatchAnnotateImagesRequest(body: Schema.BatchAnnotateImagesRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.BatchAnnotateImagesResponse])
			}
			object annotate {
				def apply(projectsId :PlayApi)(using auth: AuthToken, ec: ExecutionContext): annotate = new annotate(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/images:annotate")).addQueryStringParameters())
			}
			class asyncBatchAnnotate(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				/** Optional. Target project and location to make a call. Format: `projects/{project-id}/locations/{location-id}`. If no parent is specified, a region will be chosen automatically. Supported location-ids: `us`: USA country only, `asia`: East asia areas, like Japan, Taiwan, `eu`: The European Union. Example: `projects/project-A/locations/eu`. */
				def withParent(parent: String) = new asyncBatchAnnotate(req.addQueryStringParameters("parent" -> parent.toString))
				def withAsyncBatchAnnotateImagesRequest(body: Schema.AsyncBatchAnnotateImagesRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Operation])
			}
			object asyncBatchAnnotate {
				def apply(projectsId :PlayApi)(using auth: AuthToken, ec: ExecutionContext): asyncBatchAnnotate = new asyncBatchAnnotate(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/images:asyncBatchAnnotate")).addQueryStringParameters())
			}
		}
		object files {
			class annotate(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				/** Optional. Target project and location to make a call. Format: `projects/{project-id}/locations/{location-id}`. If no parent is specified, a region will be chosen automatically. Supported location-ids: `us`: USA country only, `asia`: East asia areas, like Japan, Taiwan, `eu`: The European Union. Example: `projects/project-A/locations/eu`. */
				def withParent(parent: String) = new annotate(req.addQueryStringParameters("parent" -> parent.toString))
				def withBatchAnnotateFilesRequest(body: Schema.BatchAnnotateFilesRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.BatchAnnotateFilesResponse])
			}
			object annotate {
				def apply(projectsId :PlayApi)(using auth: AuthToken, ec: ExecutionContext): annotate = new annotate(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/files:annotate")).addQueryStringParameters())
			}
			class asyncBatchAnnotate(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				/** Optional. Target project and location to make a call. Format: `projects/{project-id}/locations/{location-id}`. If no parent is specified, a region will be chosen automatically. Supported location-ids: `us`: USA country only, `asia`: East asia areas, like Japan, Taiwan, `eu`: The European Union. Example: `projects/project-A/locations/eu`. */
				def withParent(parent: String) = new asyncBatchAnnotate(req.addQueryStringParameters("parent" -> parent.toString))
				def withAsyncBatchAnnotateFilesRequest(body: Schema.AsyncBatchAnnotateFilesRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Operation])
			}
			object asyncBatchAnnotate {
				def apply(projectsId :PlayApi)(using auth: AuthToken, ec: ExecutionContext): asyncBatchAnnotate = new asyncBatchAnnotate(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/files:asyncBatchAnnotate")).addQueryStringParameters())
			}
		}
	}
	object locations {
		object operations {
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.Operation])
			}
			object get {
				def apply(locationsId :PlayApi, operationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/locations/${locationsId}/operations/${operationsId}")).addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.Operation]] = (fun: get) => fun.apply()
			}
		}
	}
	object images {
		class annotate(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withBatchAnnotateImagesRequest(body: Schema.BatchAnnotateImagesRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.BatchAnnotateImagesResponse])
		}
		object annotate {
			def apply()(using auth: AuthToken, ec: ExecutionContext): annotate = new annotate(auth(ws.url(BASE_URL + s"v1/images:annotate")).addQueryStringParameters())
		}
		class asyncBatchAnnotate(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withAsyncBatchAnnotateImagesRequest(body: Schema.AsyncBatchAnnotateImagesRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Operation])
		}
		object asyncBatchAnnotate {
			def apply()(using auth: AuthToken, ec: ExecutionContext): asyncBatchAnnotate = new asyncBatchAnnotate(auth(ws.url(BASE_URL + s"v1/images:asyncBatchAnnotate")).addQueryStringParameters())
		}
	}
	object files {
		class annotate(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withBatchAnnotateFilesRequest(body: Schema.BatchAnnotateFilesRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.BatchAnnotateFilesResponse])
		}
		object annotate {
			def apply()(using auth: AuthToken, ec: ExecutionContext): annotate = new annotate(auth(ws.url(BASE_URL + s"v1/files:annotate")).addQueryStringParameters())
		}
		class asyncBatchAnnotate(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withAsyncBatchAnnotateFilesRequest(body: Schema.AsyncBatchAnnotateFilesRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Operation])
		}
		object asyncBatchAnnotate {
			def apply()(using auth: AuthToken, ec: ExecutionContext): asyncBatchAnnotate = new asyncBatchAnnotate(auth(ws.url(BASE_URL + s"v1/files:asyncBatchAnnotate")).addQueryStringParameters())
		}
	}
}
