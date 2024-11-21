package com.boresjo.play.api.google.licensing

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	case class Empty(
	
	)
	
	case class LicenseAssignment(
	  /** Identifies the resource as a LicenseAssignment, which is `licensing#licenseAssignment`. */
		kind: Option[String] = Some("""licensing#licenseAssignment"""),
	  /** ETag of the resource. */
		etags: Option[String] = None,
	  /** A product's unique identifier. For more information about products in this version of the API, see Product and SKU IDs. */
		productId: Option[String] = None,
	  /** The user's current primary email address. If the user's email address changes, use the new email address in your API requests. Since a `userId` is subject to change, do not use a `userId` value as a key for persistent data. This key could break if the current user's email address changes. If the `userId` is suspended, the license status changes. */
		userId: Option[String] = None,
	  /** Link to this page. */
		selfLink: Option[String] = None,
	  /** A product SKU's unique identifier. For more information about available SKUs in this version of the API, see Products and SKUs. */
		skuId: Option[String] = None,
	  /** Display Name of the sku of the product. */
		skuName: Option[String] = None,
	  /** Display Name of the product. */
		productName: Option[String] = None
	)
	
	case class LicenseAssignmentInsert(
	  /** Email id of the user */
		userId: Option[String] = None
	)
	
	case class LicenseAssignmentList(
	  /** Identifies the resource as a collection of LicenseAssignments. */
		kind: Option[String] = Some("""licensing#licenseAssignmentList"""),
	  /** ETag of the resource. */
		etag: Option[String] = None,
	  /** The LicenseAssignments in this page of results. */
		items: Option[List[Schema.LicenseAssignment]] = None,
	  /** The token that you must submit in a subsequent request to retrieve additional license results matching your query parameters. The `maxResults` query string is related to the `nextPageToken` since `maxResults` determines how many entries are returned on each next page. */
		nextPageToken: Option[String] = None
	)
}
