package com.boresjo.play.api.google.licensing

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://licensing.googleapis.com/"

	object licenseAssignments {
		class insert(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withLicenseAssignmentInsert(body: Schema.LicenseAssignmentInsert) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.LicenseAssignment])
		}
		object insert {
			def apply(productId: String, skuId: String)(using auth: AuthToken, ec: ExecutionContext): insert = new insert(auth(ws.url(BASE_URL + s"apps/licensing/v1/product/${productId}/sku/${skuId}/user")).addQueryStringParameters())
		}
		class listForProductAndSku(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.LicenseAssignmentList]) {
			/** The `maxResults` query string determines how many entries are returned on each page of a large response. This is an optional parameter. The value must be a positive number.<br>Format: uint32 */
			def withMaxResults(maxResults: Int) = new listForProductAndSku(req.addQueryStringParameters("maxResults" -> maxResults.toString))
			/** Token to fetch the next page of data. The `maxResults` query string is related to the `pageToken` since `maxResults` determines how many entries are returned on each page. This is an optional query string. If not specified, the server returns the first page. */
			def withPageToken(pageToken: String) = new listForProductAndSku(req.addQueryStringParameters("pageToken" -> pageToken.toString))
			def apply() = req.execute("GET").map(_.json.as[Schema.LicenseAssignmentList])
		}
		object listForProductAndSku {
			def apply(productId: String, skuId: String, customerId: String)(using auth: AuthToken, ec: ExecutionContext): listForProductAndSku = new listForProductAndSku(auth(ws.url(BASE_URL + s"apps/licensing/v1/product/${productId}/sku/${skuId}/users")).addQueryStringParameters("customerId" -> customerId.toString))
			given Conversion[listForProductAndSku, Future[Schema.LicenseAssignmentList]] = (fun: listForProductAndSku) => fun.apply()
		}
		class listForProduct(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.LicenseAssignmentList]) {
			/** The `maxResults` query string determines how many entries are returned on each page of a large response. This is an optional parameter. The value must be a positive number.<br>Format: uint32 */
			def withMaxResults(maxResults: Int) = new listForProduct(req.addQueryStringParameters("maxResults" -> maxResults.toString))
			/** Token to fetch the next page of data. The `maxResults` query string is related to the `pageToken` since `maxResults` determines how many entries are returned on each page. This is an optional query string. If not specified, the server returns the first page. */
			def withPageToken(pageToken: String) = new listForProduct(req.addQueryStringParameters("pageToken" -> pageToken.toString))
			def apply() = req.execute("GET").map(_.json.as[Schema.LicenseAssignmentList])
		}
		object listForProduct {
			def apply(productId: String, customerId: String)(using auth: AuthToken, ec: ExecutionContext): listForProduct = new listForProduct(auth(ws.url(BASE_URL + s"apps/licensing/v1/product/${productId}/users")).addQueryStringParameters("customerId" -> customerId.toString))
			given Conversion[listForProduct, Future[Schema.LicenseAssignmentList]] = (fun: listForProduct) => fun.apply()
		}
		class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
			def apply() = req.execute("DELETE").map(_.json.as[Schema.Empty])
		}
		object delete {
			def apply(productId: String, skuId: String, userId: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"apps/licensing/v1/product/${productId}/sku/${skuId}/user/${userId}")).addQueryStringParameters())
			given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
		}
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.LicenseAssignment]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.LicenseAssignment])
		}
		object get {
			def apply(productId: String, skuId: String, userId: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"apps/licensing/v1/product/${productId}/sku/${skuId}/user/${userId}")).addQueryStringParameters())
			given Conversion[get, Future[Schema.LicenseAssignment]] = (fun: get) => fun.apply()
		}
		class update(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withLicenseAssignment(body: Schema.LicenseAssignment) = req.withBody(Json.toJson(body)).execute("PUT").map(_.json.as[Schema.LicenseAssignment])
		}
		object update {
			def apply(productId: String, skuId: String, userId: String)(using auth: AuthToken, ec: ExecutionContext): update = new update(auth(ws.url(BASE_URL + s"apps/licensing/v1/product/${productId}/sku/${skuId}/user/${userId}")).addQueryStringParameters())
		}
		class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withLicenseAssignment(body: Schema.LicenseAssignment) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.LicenseAssignment])
		}
		object patch {
			def apply(productId: String, skuId: String, userId: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"apps/licensing/v1/product/${productId}/sku/${skuId}/user/${userId}")).addQueryStringParameters())
		}
	}
}
