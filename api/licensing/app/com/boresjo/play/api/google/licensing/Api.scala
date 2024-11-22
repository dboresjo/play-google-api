package com.boresjo.play.api.google.licensing

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
		"""https://www.googleapis.com/auth/apps.licensing""" /* View and manage G Suite licenses for your domain */
	)

	private val BASE_URL = "https://licensing.googleapis.com/"

	object licenseAssignments {
		/** Assign a license. */
		class insert(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/apps.licensing""")
			/** Perform the request */
			def withLicenseAssignmentInsert(body: Schema.LicenseAssignmentInsert) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.LicenseAssignment])
		}
		object insert {
			def apply(productId: String, skuId: String)(using signer: RequestSigner, ec: ExecutionContext): insert = new insert(ws.url(BASE_URL + s"apps/licensing/v1/product/${productId}/sku/${skuId}/user").addQueryStringParameters())
		}
		/** List all users assigned licenses for a specific product SKU. */
		class listForProductAndSku(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.LicenseAssignmentList]) {
			val scopes = Seq("""https://www.googleapis.com/auth/apps.licensing""")
			/** The `maxResults` query string determines how many entries are returned on each page of a large response. This is an optional parameter. The value must be a positive number.<br>Format: uint32 */
			def withMaxResults(maxResults: Int) = new listForProductAndSku(req.addQueryStringParameters("maxResults" -> maxResults.toString))
			/** Token to fetch the next page of data. The `maxResults` query string is related to the `pageToken` since `maxResults` determines how many entries are returned on each page. This is an optional query string. If not specified, the server returns the first page. */
			def withPageToken(pageToken: String) = new listForProductAndSku(req.addQueryStringParameters("pageToken" -> pageToken.toString))
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.LicenseAssignmentList])
		}
		object listForProductAndSku {
			def apply(productId: String, skuId: String, customerId: String)(using signer: RequestSigner, ec: ExecutionContext): listForProductAndSku = new listForProductAndSku(ws.url(BASE_URL + s"apps/licensing/v1/product/${productId}/sku/${skuId}/users").addQueryStringParameters("customerId" -> customerId.toString))
			given Conversion[listForProductAndSku, Future[Schema.LicenseAssignmentList]] = (fun: listForProductAndSku) => fun.apply()
		}
		/** List all users assigned licenses for a specific product SKU. */
		class listForProduct(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.LicenseAssignmentList]) {
			val scopes = Seq("""https://www.googleapis.com/auth/apps.licensing""")
			/** The `maxResults` query string determines how many entries are returned on each page of a large response. This is an optional parameter. The value must be a positive number.<br>Format: uint32 */
			def withMaxResults(maxResults: Int) = new listForProduct(req.addQueryStringParameters("maxResults" -> maxResults.toString))
			/** Token to fetch the next page of data. The `maxResults` query string is related to the `pageToken` since `maxResults` determines how many entries are returned on each page. This is an optional query string. If not specified, the server returns the first page. */
			def withPageToken(pageToken: String) = new listForProduct(req.addQueryStringParameters("pageToken" -> pageToken.toString))
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.LicenseAssignmentList])
		}
		object listForProduct {
			def apply(productId: String, customerId: String)(using signer: RequestSigner, ec: ExecutionContext): listForProduct = new listForProduct(ws.url(BASE_URL + s"apps/licensing/v1/product/${productId}/users").addQueryStringParameters("customerId" -> customerId.toString))
			given Conversion[listForProduct, Future[Schema.LicenseAssignmentList]] = (fun: listForProduct) => fun.apply()
		}
		/** Revoke a license. */
		class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
			val scopes = Seq("""https://www.googleapis.com/auth/apps.licensing""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
		}
		object delete {
			def apply(productId: String, skuId: String, userId: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"apps/licensing/v1/product/${productId}/sku/${skuId}/user/${userId}").addQueryStringParameters())
			given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
		}
		/** Get a specific user's license by product SKU. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.LicenseAssignment]) {
			val scopes = Seq("""https://www.googleapis.com/auth/apps.licensing""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.LicenseAssignment])
		}
		object get {
			def apply(productId: String, skuId: String, userId: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"apps/licensing/v1/product/${productId}/sku/${skuId}/user/${userId}").addQueryStringParameters())
			given Conversion[get, Future[Schema.LicenseAssignment]] = (fun: get) => fun.apply()
		}
		/** Reassign a user's product SKU with a different SKU in the same product. */
		class update(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/apps.licensing""")
			/** Perform the request */
			def withLicenseAssignment(body: Schema.LicenseAssignment) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.LicenseAssignment])
		}
		object update {
			def apply(productId: String, skuId: String, userId: String)(using signer: RequestSigner, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"apps/licensing/v1/product/${productId}/sku/${skuId}/user/${userId}").addQueryStringParameters())
		}
		/** Reassign a user's product SKU with a different SKU in the same product. This method supports patch semantics. */
		class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/apps.licensing""")
			/** Perform the request */
			def withLicenseAssignment(body: Schema.LicenseAssignment) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.LicenseAssignment])
		}
		object patch {
			def apply(productId: String, skuId: String, userId: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"apps/licensing/v1/product/${productId}/sku/${skuId}/user/${userId}").addQueryStringParameters())
		}
	}
}
