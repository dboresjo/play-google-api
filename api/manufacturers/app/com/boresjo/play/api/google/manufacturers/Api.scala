package com.boresjo.play.api.google.manufacturers

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
		"""https://www.googleapis.com/auth/manufacturercenter""" /* Manage your product listings for Google Manufacturer Center */
	)

	private val BASE_URL = "https://manufacturers.googleapis.com/"

	object accounts {
		object products {
			/** Lists all the products in a Manufacturer Center account. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListProductsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/manufacturercenter""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListProductsResponse])
			}
			object list {
				def apply(accountsId :PlayApi, parent: String, pageSize: Int, pageToken: String, include: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/accounts/${accountsId}/products").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "include" -> include.toString))
				given Conversion[list, Future[Schema.ListProductsResponse]] = (fun: list) => fun.apply()
			}
			/** Gets the product from a Manufacturer Center account, including product issues. A recently updated product takes around 15 minutes to process. Changes are only visible after it has been processed. While some issues may be available once the product has been processed, other issues may take days to appear. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Product]) {
				val scopes = Seq("""https://www.googleapis.com/auth/manufacturercenter""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Product])
			}
			object get {
				def apply(accountsId :PlayApi, productsId :PlayApi, parent: String, name: String, include: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/accounts/${accountsId}/products/${productsId}").addQueryStringParameters("parent" -> parent.toString, "name" -> name.toString, "include" -> include.toString))
				given Conversion[get, Future[Schema.Product]] = (fun: get) => fun.apply()
			}
			/** Inserts or updates the attributes of the product in a Manufacturer Center account. Creates a product with the provided attributes. If the product already exists, then all attributes are replaced with the new ones. The checks at upload time are minimal. All required attributes need to be present for a product to be valid. Issues may show up later after the API has accepted a new upload for a product and it is possible to overwrite an existing valid product with an invalid product. To detect this, you should retrieve the product and check it for issues once the new version is available. Uploaded attributes first need to be processed before they can be retrieved. Until then, new products will be unavailable, and retrieval of previously uploaded products will return the original state of the product. */
			class update(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/manufacturercenter""")
				/** Perform the request */
				def withAttributes(body: Schema.Attributes) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.Empty])
			}
			object update {
				def apply(accountsId :PlayApi, productsId :PlayApi, parent: String, name: String)(using signer: RequestSigner, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"v1/accounts/${accountsId}/products/${productsId}").addQueryStringParameters("parent" -> parent.toString, "name" -> name.toString))
			}
			/** Deletes the product from a Manufacturer Center account. */
			class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
				val scopes = Seq("""https://www.googleapis.com/auth/manufacturercenter""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
			}
			object delete {
				def apply(accountsId :PlayApi, productsId :PlayApi, parent: String, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/accounts/${accountsId}/products/${productsId}").addQueryStringParameters("parent" -> parent.toString, "name" -> name.toString))
				given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
			}
		}
		object languages {
			object productCertifications {
				/** Updates (or creates if allow_missing = true) a product certification which links certifications with products. This method can only be called by certification bodies. */
				class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/manufacturercenter""")
					/** Optional. The list of fields to update according to aip.dev/134. However, only full update is supported as of right now. Therefore, it can be either ignored or set to "&#42;". Setting any other values will returns UNIMPLEMENTED error.<br>Format: google-fieldmask */
					def withUpdateMask(updateMask: String) = new patch(req.addQueryStringParameters("updateMask" -> updateMask.toString))
					/** Perform the request */
					def withProductCertification(body: Schema.ProductCertification) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.ProductCertification])
				}
				object patch {
					def apply(accountsId :PlayApi, languagesId :PlayApi, productCertificationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/accounts/${accountsId}/languages/${languagesId}/productCertifications/${productCertificationsId}").addQueryStringParameters("name" -> name.toString))
				}
				/** Lists product certifications from a specified certification body. This method can only be called by certification bodies. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListProductCertificationsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/manufacturercenter""")
					/** Optional. The maximum number of product certifications to return. The service may return fewer than this value. If unspecified, at most 50 product certifications will be returned. The maximum value is 1000; values above 1000 will be coerced to 1000.<br>Format: int32 */
					def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
					/** Optional. A page token, received from a previous `ListProductCertifications` call. Provide this to retrieve the subsequent page. When paginating, all other parameters provided to `ListProductCertifications` must match the call that provided the page token. Required if requesting the second or higher page. */
					def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListProductCertificationsResponse])
				}
				object list {
					def apply(accountsId :PlayApi, languagesId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/accounts/${accountsId}/languages/${languagesId}/productCertifications").addQueryStringParameters("parent" -> parent.toString))
					given Conversion[list, Future[Schema.ListProductCertificationsResponse]] = (fun: list) => fun.apply()
				}
				/** Gets a product certification by its name. This method can only be called by certification bodies. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ProductCertification]) {
					val scopes = Seq("""https://www.googleapis.com/auth/manufacturercenter""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ProductCertification])
				}
				object get {
					def apply(accountsId :PlayApi, languagesId :PlayApi, productCertificationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/accounts/${accountsId}/languages/${languagesId}/productCertifications/${productCertificationsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.ProductCertification]] = (fun: get) => fun.apply()
				}
				/** Deletes a product certification by its name. This method can only be called by certification bodies. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					val scopes = Seq("""https://www.googleapis.com/auth/manufacturercenter""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
				}
				object delete {
					def apply(accountsId :PlayApi, languagesId :PlayApi, productCertificationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/accounts/${accountsId}/languages/${languagesId}/productCertifications/${productCertificationsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
				}
			}
		}
	}
}
