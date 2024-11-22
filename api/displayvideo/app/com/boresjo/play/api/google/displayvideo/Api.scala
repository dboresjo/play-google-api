package com.boresjo.play.api.google.displayvideo

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
		"""https://www.googleapis.com/auth/display-video""" /* Create, see, edit, and permanently delete your Display & Video 360 entities and reports */,
		"""https://www.googleapis.com/auth/display-video-mediaplanning""" /* Create, see, and edit Display & Video 360 Campaign entities and see billing invoices */,
		"""https://www.googleapis.com/auth/display-video-user-management""" /* Private Service: https://www.googleapis.com/auth/display-video-user-management */,
		"""https://www.googleapis.com/auth/doubleclickbidmanager""" /* View and manage your reports in DoubleClick Bid Manager */
	)

	private val BASE_URL = "https://displayvideo.googleapis.com/"

	object advertisers {
		/** Creates a new advertiser. Returns the newly created advertiser if successful. &#42;&#42;This method regularly experiences high latency.&#42;&#42; We recommend [increasing your default timeout](/display-video/api/guides/best-practices/timeouts#client_library_timeout) to avoid errors. */
		class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/display-video""")
			/** Perform the request */
			def withAdvertiser(body: Schema.Advertiser) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Advertiser])
		}
		object create {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v3/advertisers").addQueryStringParameters())
		}
		/** Lists assigned targeting options of an advertiser across targeting types. */
		class listAssignedTargetingOptions(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.BulkListAdvertiserAssignedTargetingOptionsResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/display-video""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.BulkListAdvertiserAssignedTargetingOptionsResponse])
		}
		object listAssignedTargetingOptions {
			def apply(advertisersId :PlayApi, advertiserId: String, pageSize: Int, pageToken: String, orderBy: String, filter: String)(using signer: RequestSigner, ec: ExecutionContext): listAssignedTargetingOptions = new listAssignedTargetingOptions(ws.url(BASE_URL + s"v3/advertisers/${advertisersId}:listAssignedTargetingOptions").addQueryStringParameters("advertiserId" -> advertiserId.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "orderBy" -> orderBy.toString, "filter" -> filter.toString))
			given Conversion[listAssignedTargetingOptions, Future[Schema.BulkListAdvertiserAssignedTargetingOptionsResponse]] = (fun: listAssignedTargetingOptions) => fun.apply()
		}
		/** Deletes an advertiser. Deleting an advertiser will delete all of its child resources, for example, campaigns, insertion orders and line items. A deleted advertiser cannot be recovered. */
		class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
			val scopes = Seq("""https://www.googleapis.com/auth/display-video""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
		}
		object delete {
			def apply(advertisersId :PlayApi, advertiserId: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v3/advertisers/${advertisersId}").addQueryStringParameters("advertiserId" -> advertiserId.toString))
			given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
		}
		/** Audits an advertiser. Returns the counts of used entities per resource type under the advertiser provided. Used entities count towards their respective resource limit. See https://support.google.com/displayvideo/answer/6071450. */
		class audit(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.AuditAdvertiserResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/display-video""")
			/** Optional. The specific fields to return. If no mask is specified, all fields in the response proto will be filled. Valid values are: &#42; usedLineItemsCount &#42; usedInsertionOrdersCount &#42; usedCampaignsCount &#42; channelsCount &#42; negativelyTargetedChannelsCount &#42; negativeKeywordListsCount &#42; adGroupCriteriaCount &#42; campaignCriteriaCount<br>Format: google-fieldmask */
			def withReadMask(readMask: String) = new audit(req.addQueryStringParameters("readMask" -> readMask.toString))
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.AuditAdvertiserResponse])
		}
		object audit {
			def apply(advertisersId :PlayApi, advertiserId: String)(using signer: RequestSigner, ec: ExecutionContext): audit = new audit(ws.url(BASE_URL + s"v3/advertisers/${advertisersId}:audit").addQueryStringParameters("advertiserId" -> advertiserId.toString))
			given Conversion[audit, Future[Schema.AuditAdvertiserResponse]] = (fun: audit) => fun.apply()
		}
		/** Gets an advertiser. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Advertiser]) {
			val scopes = Seq("""https://www.googleapis.com/auth/display-video""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Advertiser])
		}
		object get {
			def apply(advertisersId :PlayApi, advertiserId: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v3/advertisers/${advertisersId}").addQueryStringParameters("advertiserId" -> advertiserId.toString))
			given Conversion[get, Future[Schema.Advertiser]] = (fun: get) => fun.apply()
		}
		/** Edits targeting options under a single advertiser. The operation will delete the assigned targeting options provided in BulkEditAdvertiserAssignedTargetingOptionsRequest.delete_requests and then create the assigned targeting options provided in BulkEditAdvertiserAssignedTargetingOptionsRequest.create_requests . */
		class editAssignedTargetingOptions(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/display-video""")
			/** Perform the request */
			def withBulkEditAdvertiserAssignedTargetingOptionsRequest(body: Schema.BulkEditAdvertiserAssignedTargetingOptionsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.BulkEditAdvertiserAssignedTargetingOptionsResponse])
		}
		object editAssignedTargetingOptions {
			def apply(advertisersId :PlayApi, advertiserId: String)(using signer: RequestSigner, ec: ExecutionContext): editAssignedTargetingOptions = new editAssignedTargetingOptions(ws.url(BASE_URL + s"v3/advertisers/${advertisersId}:editAssignedTargetingOptions").addQueryStringParameters("advertiserId" -> advertiserId.toString))
		}
		/** Updates an existing advertiser. Returns the updated advertiser if successful. */
		class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/display-video""")
			/** Perform the request */
			def withAdvertiser(body: Schema.Advertiser) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Advertiser])
		}
		object patch {
			def apply(advertisersId :PlayApi, advertiserId: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v3/advertisers/${advertisersId}").addQueryStringParameters("advertiserId" -> advertiserId.toString, "updateMask" -> updateMask.toString))
		}
		/** Lists advertisers that are accessible to the current user. The order is defined by the order_by parameter. A single partner_id is required. Cross-partner listing is not supported. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListAdvertisersResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/display-video""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListAdvertisersResponse])
		}
		object list {
			def apply(partnerId: String, pageSize: Int, pageToken: String, orderBy: String, filter: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v3/advertisers").addQueryStringParameters("partnerId" -> partnerId.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "orderBy" -> orderBy.toString, "filter" -> filter.toString))
			given Conversion[list, Future[Schema.ListAdvertisersResponse]] = (fun: list) => fun.apply()
		}
		object assets {
			/** Uploads an asset. Returns the ID of the newly uploaded asset if successful. The asset file size should be no more than 10 MB for images, 200 MB for ZIP files, and 1 GB for videos. Must be used within the [multipart media upload process](/display-video/api/guides/how-tos/upload#multipart). Examples using provided client libraries can be found in our [Creating Creatives guide](/display-video/api/guides/creating-creatives/overview#upload_an_asset). */
			class upload(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/display-video""")
				/** Perform the request */
				def withCreateAssetRequest(body: Schema.CreateAssetRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.CreateAssetResponse])
			}
			object upload {
				def apply(advertisersId :PlayApi, advertiserId: String)(using signer: RequestSigner, ec: ExecutionContext): upload = new upload(ws.url(BASE_URL + s"v3/advertisers/${advertisersId}/assets").addQueryStringParameters("advertiserId" -> advertiserId.toString))
			}
		}
		object lineItems {
			/** Duplicates a line item. Returns the ID of the created line item if successful. YouTube & Partners line items cannot be created or updated using the API. &#42;&#42;This method regularly experiences high latency.&#42;&#42; We recommend [increasing your default timeout](/display-video/api/guides/best-practices/timeouts#client_library_timeout) to avoid errors. */
			class duplicate(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/display-video""")
				/** Perform the request */
				def withDuplicateLineItemRequest(body: Schema.DuplicateLineItemRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.DuplicateLineItemResponse])
			}
			object duplicate {
				def apply(advertisersId :PlayApi, lineItemsId :PlayApi, advertiserId: String, lineItemId: String)(using signer: RequestSigner, ec: ExecutionContext): duplicate = new duplicate(ws.url(BASE_URL + s"v3/advertisers/${advertisersId}/lineItems/${lineItemsId}:duplicate").addQueryStringParameters("advertiserId" -> advertiserId.toString, "lineItemId" -> lineItemId.toString))
			}
			/** Creates a new line item. Returns the newly created line item if successful. YouTube & Partners line items cannot be created or updated using the API. */
			class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/display-video""")
				/** Perform the request */
				def withLineItem(body: Schema.LineItem) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.LineItem])
			}
			object create {
				def apply(advertisersId :PlayApi, advertiserId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v3/advertisers/${advertisersId}/lineItems").addQueryStringParameters("advertiserId" -> advertiserId.toString))
			}
			/** Lists assigned targeting options for multiple line items across targeting types. */
			class bulkListAssignedTargetingOptions(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.BulkListAssignedTargetingOptionsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/display-video""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.BulkListAssignedTargetingOptionsResponse])
			}
			object bulkListAssignedTargetingOptions {
				def apply(advertisersId :PlayApi, advertiserId: String, lineItemIds: String, pageSize: Int, pageToken: String, orderBy: String, filter: String)(using signer: RequestSigner, ec: ExecutionContext): bulkListAssignedTargetingOptions = new bulkListAssignedTargetingOptions(ws.url(BASE_URL + s"v3/advertisers/${advertisersId}/lineItems:bulkListAssignedTargetingOptions").addQueryStringParameters("advertiserId" -> advertiserId.toString, "lineItemIds" -> lineItemIds.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "orderBy" -> orderBy.toString, "filter" -> filter.toString))
				given Conversion[bulkListAssignedTargetingOptions, Future[Schema.BulkListAssignedTargetingOptionsResponse]] = (fun: bulkListAssignedTargetingOptions) => fun.apply()
			}
			/** Updates multiple line items. Requests to this endpoint cannot be made concurrently with the following requests updating the same line item: &#42; BulkEditAssignedTargetingOptions &#42; UpdateLineItem &#42; assignedTargetingOptions.create &#42; assignedTargetingOptions.delete YouTube & Partners line items cannot be created or updated using the API. */
			class bulkUpdate(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/display-video""")
				/** Perform the request */
				def withBulkUpdateLineItemsRequest(body: Schema.BulkUpdateLineItemsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.BulkUpdateLineItemsResponse])
			}
			object bulkUpdate {
				def apply(advertisersId :PlayApi, advertiserId: String)(using signer: RequestSigner, ec: ExecutionContext): bulkUpdate = new bulkUpdate(ws.url(BASE_URL + s"v3/advertisers/${advertisersId}/lineItems:bulkUpdate").addQueryStringParameters("advertiserId" -> advertiserId.toString))
			}
			/** Bulk edits targeting options under multiple line items. The operation will delete the assigned targeting options provided in BulkEditAssignedTargetingOptionsRequest.delete_requests and then create the assigned targeting options provided in BulkEditAssignedTargetingOptionsRequest.create_requests. Requests to this endpoint cannot be made concurrently with the following requests updating the same line item: &#42; lineItems.bulkUpdate &#42; lineItems.patch &#42; assignedTargetingOptions.create &#42; assignedTargetingOptions.delete YouTube & Partners line items cannot be created or updated using the API. */
			class bulkEditAssignedTargetingOptions(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/display-video""")
				/** Perform the request */
				def withBulkEditAssignedTargetingOptionsRequest(body: Schema.BulkEditAssignedTargetingOptionsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.BulkEditAssignedTargetingOptionsResponse])
			}
			object bulkEditAssignedTargetingOptions {
				def apply(advertisersId :PlayApi, advertiserId: String)(using signer: RequestSigner, ec: ExecutionContext): bulkEditAssignedTargetingOptions = new bulkEditAssignedTargetingOptions(ws.url(BASE_URL + s"v3/advertisers/${advertisersId}/lineItems:bulkEditAssignedTargetingOptions").addQueryStringParameters("advertiserId" -> advertiserId.toString))
			}
			/** Deletes a line item. Returns error code `NOT_FOUND` if the line item does not exist. The line item should be archived first, i.e. set entity_status to `ENTITY_STATUS_ARCHIVED`, to be able to delete it. YouTube & Partners line items cannot be created or updated using the API. */
			class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
				val scopes = Seq("""https://www.googleapis.com/auth/display-video""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
			}
			object delete {
				def apply(advertisersId :PlayApi, lineItemsId :PlayApi, advertiserId: String, lineItemId: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v3/advertisers/${advertisersId}/lineItems/${lineItemsId}").addQueryStringParameters("advertiserId" -> advertiserId.toString, "lineItemId" -> lineItemId.toString))
				given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
			}
			/** Creates a new line item with settings (including targeting) inherited from the insertion order and an `ENTITY_STATUS_DRAFT` entity_status. Returns the newly created line item if successful. There are default values based on the three fields: &#42; The insertion order's insertion_order_type &#42; The insertion order's automation_type &#42; The given line_item_type YouTube & Partners line items cannot be created or updated using the API. */
			class generateDefault(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/display-video""")
				/** Perform the request */
				def withGenerateDefaultLineItemRequest(body: Schema.GenerateDefaultLineItemRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.LineItem])
			}
			object generateDefault {
				def apply(advertisersId :PlayApi, advertiserId: String)(using signer: RequestSigner, ec: ExecutionContext): generateDefault = new generateDefault(ws.url(BASE_URL + s"v3/advertisers/${advertisersId}/lineItems:generateDefault").addQueryStringParameters("advertiserId" -> advertiserId.toString))
			}
			/** Gets a line item. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.LineItem]) {
				val scopes = Seq("""https://www.googleapis.com/auth/display-video""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.LineItem])
			}
			object get {
				def apply(advertisersId :PlayApi, lineItemsId :PlayApi, advertiserId: String, lineItemId: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v3/advertisers/${advertisersId}/lineItems/${lineItemsId}").addQueryStringParameters("advertiserId" -> advertiserId.toString, "lineItemId" -> lineItemId.toString))
				given Conversion[get, Future[Schema.LineItem]] = (fun: get) => fun.apply()
			}
			/** Updates an existing line item. Returns the updated line item if successful. Requests to this endpoint cannot be made concurrently with the following requests updating the same line item: &#42; BulkEditAssignedTargetingOptions &#42; BulkUpdateLineItems &#42; assignedTargetingOptions.create &#42; assignedTargetingOptions.delete YouTube & Partners line items cannot be created or updated using the API. &#42;&#42;This method regularly experiences high latency.&#42;&#42; We recommend [increasing your default timeout](/display-video/api/guides/best-practices/timeouts#client_library_timeout) to avoid errors. */
			class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/display-video""")
				/** Perform the request */
				def withLineItem(body: Schema.LineItem) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.LineItem])
			}
			object patch {
				def apply(advertisersId :PlayApi, lineItemsId :PlayApi, advertiserId: String, lineItemId: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v3/advertisers/${advertisersId}/lineItems/${lineItemsId}").addQueryStringParameters("advertiserId" -> advertiserId.toString, "lineItemId" -> lineItemId.toString, "updateMask" -> updateMask.toString))
			}
			/** Lists line items in an advertiser. The order is defined by the order_by parameter. If a filter by entity_status is not specified, line items with `ENTITY_STATUS_ARCHIVED` will not be included in the results. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListLineItemsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/display-video""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListLineItemsResponse])
			}
			object list {
				def apply(advertisersId :PlayApi, advertiserId: String, pageSize: Int, pageToken: String, orderBy: String, filter: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v3/advertisers/${advertisersId}/lineItems").addQueryStringParameters("advertiserId" -> advertiserId.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "orderBy" -> orderBy.toString, "filter" -> filter.toString))
				given Conversion[list, Future[Schema.ListLineItemsResponse]] = (fun: list) => fun.apply()
			}
			object targetingTypes {
				object assignedTargetingOptions {
					/** Gets a single targeting option assigned to a line item. */
					class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.AssignedTargetingOption]) {
						val scopes = Seq("""https://www.googleapis.com/auth/display-video""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.AssignedTargetingOption])
					}
					object get {
						def apply(advertisersId :PlayApi, lineItemsId :PlayApi, targetingTypesId :PlayApi, assignedTargetingOptionsId :PlayApi, advertiserId: String, lineItemId: String, targetingType: String, assignedTargetingOptionId: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v3/advertisers/${advertisersId}/lineItems/${lineItemsId}/targetingTypes/${targetingTypesId}/assignedTargetingOptions/${assignedTargetingOptionsId}").addQueryStringParameters("advertiserId" -> advertiserId.toString, "lineItemId" -> lineItemId.toString, "targetingType" -> targetingType.toString, "assignedTargetingOptionId" -> assignedTargetingOptionId.toString))
						given Conversion[get, Future[Schema.AssignedTargetingOption]] = (fun: get) => fun.apply()
					}
					/** Lists the targeting options assigned to a line item. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListLineItemAssignedTargetingOptionsResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/display-video""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListLineItemAssignedTargetingOptionsResponse])
					}
					object list {
						def apply(advertisersId :PlayApi, lineItemsId :PlayApi, targetingTypesId :PlayApi, advertiserId: String, lineItemId: String, targetingType: String, pageSize: Int, pageToken: String, orderBy: String, filter: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v3/advertisers/${advertisersId}/lineItems/${lineItemsId}/targetingTypes/${targetingTypesId}/assignedTargetingOptions").addQueryStringParameters("advertiserId" -> advertiserId.toString, "lineItemId" -> lineItemId.toString, "targetingType" -> targetingType.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "orderBy" -> orderBy.toString, "filter" -> filter.toString))
						given Conversion[list, Future[Schema.ListLineItemAssignedTargetingOptionsResponse]] = (fun: list) => fun.apply()
					}
					/** Assigns a targeting option to a line item. Returns the assigned targeting option if successful. Requests to this endpoint cannot be made concurrently with the following requests updating the same line item: &#42; lineItems.bulkEditAssignedTargetingOptions &#42; lineItems.bulkUpdate &#42; lineItems.patch &#42; DeleteLineItemAssignedTargetingOption YouTube & Partners line items cannot be created or updated using the API. */
					class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/display-video""")
						/** Perform the request */
						def withAssignedTargetingOption(body: Schema.AssignedTargetingOption) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.AssignedTargetingOption])
					}
					object create {
						def apply(advertisersId :PlayApi, lineItemsId :PlayApi, targetingTypesId :PlayApi, advertiserId: String, lineItemId: String, targetingType: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v3/advertisers/${advertisersId}/lineItems/${lineItemsId}/targetingTypes/${targetingTypesId}/assignedTargetingOptions").addQueryStringParameters("advertiserId" -> advertiserId.toString, "lineItemId" -> lineItemId.toString, "targetingType" -> targetingType.toString))
					}
					/** Deletes an assigned targeting option from a line item. Requests to this endpoint cannot be made concurrently with the following requests updating the same line item: &#42; lineItems.bulkEditAssignedTargetingOptions &#42; lineItems.bulkUpdate &#42; lineItems.patch &#42; CreateLineItemAssignedTargetingOption YouTube & Partners line items cannot be created or updated using the API. */
					class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
						val scopes = Seq("""https://www.googleapis.com/auth/display-video""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
					}
					object delete {
						def apply(advertisersId :PlayApi, lineItemsId :PlayApi, targetingTypesId :PlayApi, assignedTargetingOptionsId :PlayApi, advertiserId: String, lineItemId: String, targetingType: String, assignedTargetingOptionId: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v3/advertisers/${advertisersId}/lineItems/${lineItemsId}/targetingTypes/${targetingTypesId}/assignedTargetingOptions/${assignedTargetingOptionsId}").addQueryStringParameters("advertiserId" -> advertiserId.toString, "lineItemId" -> lineItemId.toString, "targetingType" -> targetingType.toString, "assignedTargetingOptionId" -> assignedTargetingOptionId.toString))
						given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
					}
				}
			}
		}
		object adGroupAds {
			/** Gets an ad group ad. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.AdGroupAd]) {
				val scopes = Seq("""https://www.googleapis.com/auth/display-video""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.AdGroupAd])
			}
			object get {
				def apply(advertisersId :PlayApi, adGroupAdsId :PlayApi, advertiserId: String, adGroupAdId: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v3/advertisers/${advertisersId}/adGroupAds/${adGroupAdsId}").addQueryStringParameters("advertiserId" -> advertiserId.toString, "adGroupAdId" -> adGroupAdId.toString))
				given Conversion[get, Future[Schema.AdGroupAd]] = (fun: get) => fun.apply()
			}
			/** Lists ad group ads. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListAdGroupAdsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/display-video""")
				/** Optional. Requested page size. Must be between `1` and `100`. If unspecified will default to `100`. Returns error code `INVALID_ARGUMENT` if an invalid value is specified.<br>Format: int32 */
				def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
				/** Optional. A token identifying a page of results the server should return. Typically, this is the value of next_page_token returned from the previous call to `ListAdGroupAds` method. If not specified, the first page of results will be returned. */
				def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
				/** Optional. Field by which to sort the list. Acceptable values are: &#42; `displayName` (default) &#42; `entityStatus` The default sorting order is ascending. To specify descending order for a field, a suffix "desc" should be added to the field name. Example: `displayName desc`. */
				def withOrderBy(orderBy: String) = new list(req.addQueryStringParameters("orderBy" -> orderBy.toString))
				/** Optional. Allows filtering by custom ad group ad fields. Supported syntax: &#42; Filter expressions are made up of one or more restrictions. &#42; Restrictions can be combined by `AND` and `OR`. A sequence of restrictions implicitly uses `AND`. &#42; A restriction has the form of `{field} {operator} {value}`. &#42; All fields must use the `EQUALS (=)` operator. Supported fields: &#42; `adGroupId` &#42; `displayName` &#42; `entityStatus` &#42; `adGroupAdId` Examples: &#42; All ad group ads under an ad group: `adGroupId="1234"` &#42; All ad group ads under an ad group with an entityStatus of `ENTITY_STATUS_ACTIVE` or `ENTITY_STATUS_PAUSED`: `(entityStatus="ENTITY_STATUS_ACTIVE" OR entityStatus="ENTITY_STATUS_PAUSED") AND adGroupId="12345"` The length of this field should be no more than 500 characters. Reference our [filter `LIST` requests](/display-video/api/guides/how-tos/filters) guide for more information. */
				def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListAdGroupAdsResponse])
			}
			object list {
				def apply(advertisersId :PlayApi, advertiserId: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v3/advertisers/${advertisersId}/adGroupAds").addQueryStringParameters("advertiserId" -> advertiserId.toString))
				given Conversion[list, Future[Schema.ListAdGroupAdsResponse]] = (fun: list) => fun.apply()
			}
		}
		object adGroups {
			/** Lists assigned targeting options for multiple ad groups across targeting types. Inherited assigned targeting options are not included. */
			class bulkListAdGroupAssignedTargetingOptions(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.BulkListAdGroupAssignedTargetingOptionsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/display-video""")
				/** Optional. Requested page size. The size must be an integer between `1` and `5000`. If unspecified, the default is `5000`. Returns error code `INVALID_ARGUMENT` if an invalid value is specified.<br>Format: int32 */
				def withPageSize(pageSize: Int) = new bulkListAdGroupAssignedTargetingOptions(req.addQueryStringParameters("pageSize" -> pageSize.toString))
				/** Optional. A token that lets the client fetch the next page of results. Typically, this is the value of next_page_token returned from the previous call to the `BulkListAdGroupAssignedTargetingOptions` method. If not specified, the first page of results will be returned. */
				def withPageToken(pageToken: String) = new bulkListAdGroupAssignedTargetingOptions(req.addQueryStringParameters("pageToken" -> pageToken.toString))
				/** Optional. Field by which to sort the list. Acceptable values are: &#42; `adGroupId` (default) &#42; `assignedTargetingOption.targetingType` The default sorting order is ascending. To specify descending order for a field, a suffix "desc" should be added to the field name. Example: `targetingType desc`. */
				def withOrderBy(orderBy: String) = new bulkListAdGroupAssignedTargetingOptions(req.addQueryStringParameters("orderBy" -> orderBy.toString))
				/** Optional. Allows filtering by assigned targeting option fields. Supported syntax: &#42; Filter expressions are made up of one or more restrictions. &#42; Restrictions can be combined by the logical operator `OR`. &#42; A restriction has the form of `{field} {operator} {value}`. &#42; All fields must use the `EQUALS (=)` operator. Supported fields: &#42; `targetingType` Examples: &#42; `AssignedTargetingOption` resources of targeting type `TARGETING_TYPE_YOUTUBE_VIDEO` or `TARGETING_TYPE_YOUTUBE_CHANNEL`: `targetingType="TARGETING_TYPE_YOUTUBE_VIDEO" OR targetingType="TARGETING_TYPE_YOUTUBE_CHANNEL"` The length of this field should be no more than 500 characters. Reference our [filter `LIST` requests](/display-video/api/guides/how-tos/filters) guide for more information. */
				def withFilter(filter: String) = new bulkListAdGroupAssignedTargetingOptions(req.addQueryStringParameters("filter" -> filter.toString))
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.BulkListAdGroupAssignedTargetingOptionsResponse])
			}
			object bulkListAdGroupAssignedTargetingOptions {
				def apply(advertisersId :PlayApi, advertiserId: String, adGroupIds: String)(using signer: RequestSigner, ec: ExecutionContext): bulkListAdGroupAssignedTargetingOptions = new bulkListAdGroupAssignedTargetingOptions(ws.url(BASE_URL + s"v3/advertisers/${advertisersId}/adGroups:bulkListAdGroupAssignedTargetingOptions").addQueryStringParameters("advertiserId" -> advertiserId.toString, "adGroupIds" -> adGroupIds.toString))
				given Conversion[bulkListAdGroupAssignedTargetingOptions, Future[Schema.BulkListAdGroupAssignedTargetingOptionsResponse]] = (fun: bulkListAdGroupAssignedTargetingOptions) => fun.apply()
			}
			/** Gets an ad group. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.AdGroup]) {
				val scopes = Seq("""https://www.googleapis.com/auth/display-video""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.AdGroup])
			}
			object get {
				def apply(advertisersId :PlayApi, adGroupsId :PlayApi, advertiserId: String, adGroupId: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v3/advertisers/${advertisersId}/adGroups/${adGroupsId}").addQueryStringParameters("advertiserId" -> advertiserId.toString, "adGroupId" -> adGroupId.toString))
				given Conversion[get, Future[Schema.AdGroup]] = (fun: get) => fun.apply()
			}
			/** Lists ad groups. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListAdGroupsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/display-video""")
				/** Optional. Requested page size. Must be between `1` and `200`. If unspecified will default to `100`. Returns error code `INVALID_ARGUMENT` if an invalid value is specified.<br>Format: int32 */
				def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
				/** Optional. A token identifying a page of results the server should return. Typically, this is the value of next_page_token returned from the previous call to `ListAdGroups` method. If not specified, the first page of results will be returned. */
				def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
				/** Optional. Field by which to sort the list. Acceptable values are: &#42; `displayName` (default) &#42; `entityStatus` The default sorting order is ascending. To specify descending order for a field, a suffix "desc" should be added to the field name. Example: `displayName desc`. */
				def withOrderBy(orderBy: String) = new list(req.addQueryStringParameters("orderBy" -> orderBy.toString))
				/** Optional. Allows filtering by custom ad group fields. Supported syntax: &#42; Filter expressions are made up of one or more restrictions. &#42; Restrictions can be combined by `AND` and `OR`. A sequence of restrictions implicitly uses `AND`. &#42; A restriction has the form of `{field} {operator} {value}`. &#42; All fields must use the `EQUALS (=)` operator. Supported properties: &#42; `adGroupId` &#42; `displayName` &#42; `entityStatus` &#42; `lineItemId` &#42; `adGroupFormat` Examples: &#42; All ad groups under an line item: `lineItemId="1234"` &#42; All `ENTITY_STATUS_ACTIVE` or `ENTITY_STATUS_PAUSED` `AD_GROUP_FORMAT_IN_STREAM` ad groups under an advertiser: `(entityStatus="ENTITY_STATUS_ACTIVE" OR entityStatus="ENTITY_STATUS_PAUSED") AND adGroupFormat="AD_GROUP_FORMAT_IN_STREAM"` The length of this field should be no more than 500 characters. Reference our [filter `LIST` requests](/display-video/api/guides/how-tos/filters) guide for more information. */
				def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListAdGroupsResponse])
			}
			object list {
				def apply(advertisersId :PlayApi, advertiserId: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v3/advertisers/${advertisersId}/adGroups").addQueryStringParameters("advertiserId" -> advertiserId.toString))
				given Conversion[list, Future[Schema.ListAdGroupsResponse]] = (fun: list) => fun.apply()
			}
			object targetingTypes {
				object assignedTargetingOptions {
					/** Gets a single targeting option assigned to an ad group. Inherited assigned targeting options are not included. */
					class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.AssignedTargetingOption]) {
						val scopes = Seq("""https://www.googleapis.com/auth/display-video""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.AssignedTargetingOption])
					}
					object get {
						def apply(advertisersId :PlayApi, adGroupsId :PlayApi, targetingTypesId :PlayApi, assignedTargetingOptionsId :PlayApi, advertiserId: String, adGroupId: String, targetingType: String, assignedTargetingOptionId: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v3/advertisers/${advertisersId}/adGroups/${adGroupsId}/targetingTypes/${targetingTypesId}/assignedTargetingOptions/${assignedTargetingOptionsId}").addQueryStringParameters("advertiserId" -> advertiserId.toString, "adGroupId" -> adGroupId.toString, "targetingType" -> targetingType.toString, "assignedTargetingOptionId" -> assignedTargetingOptionId.toString))
						given Conversion[get, Future[Schema.AssignedTargetingOption]] = (fun: get) => fun.apply()
					}
					/** Lists the targeting options assigned to an ad group. Inherited assigned targeting options are not included. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListAdGroupAssignedTargetingOptionsResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/display-video""")
						/** Optional. Requested page size. Must be between `1` and `5000`. If unspecified will default to `100`. Returns error code `INVALID_ARGUMENT` if an invalid value is specified.<br>Format: int32 */
						def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
						/** Optional. A token identifying a page of results the server should return. Typically, this is the value of next_page_token returned from the previous call to `ListAdGroupAssignedTargetingOptions` method. If not specified, the first page of results will be returned. */
						def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
						/** Optional. Field by which to sort the list. Acceptable values are: &#42; `assignedTargetingOptionId` (default) The default sorting order is ascending. To specify descending order for a field, a suffix "desc" should be added to the field name. Example: `assignedTargetingOptionId desc`. */
						def withOrderBy(orderBy: String) = new list(req.addQueryStringParameters("orderBy" -> orderBy.toString))
						/** Optional. Allows filtering by assigned targeting option fields. Supported syntax: &#42; Filter expressions are made up of one or more restrictions. &#42; Restrictions can be combined by the logical operator `OR`. &#42; A restriction has the form of `{field} {operator} {value}`. &#42; All fields must use the `EQUALS (=)` operator. Supported fields: &#42; `assignedTargetingOptionId` Examples: &#42; `AssignedTargetingOption` resources with ID 1 or 2: `assignedTargetingOptionId="1" OR assignedTargetingOptionId="2"` The length of this field should be no more than 500 characters. Reference our [filter `LIST` requests](/display-video/api/guides/how-tos/filters) guide for more information. */
						def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListAdGroupAssignedTargetingOptionsResponse])
					}
					object list {
						def apply(advertisersId :PlayApi, adGroupsId :PlayApi, targetingTypesId :PlayApi, advertiserId: String, adGroupId: String, targetingType: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v3/advertisers/${advertisersId}/adGroups/${adGroupsId}/targetingTypes/${targetingTypesId}/assignedTargetingOptions").addQueryStringParameters("advertiserId" -> advertiserId.toString, "adGroupId" -> adGroupId.toString, "targetingType" -> targetingType.toString))
						given Conversion[list, Future[Schema.ListAdGroupAssignedTargetingOptionsResponse]] = (fun: list) => fun.apply()
					}
				}
			}
		}
		object channels {
			/** Creates a new channel. Returns the newly created channel if successful. */
			class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/display-video""")
				/** Perform the request */
				def withChannel(body: Schema.Channel) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Channel])
			}
			object create {
				def apply(advertisersId :PlayApi, advertiserId: String, partnerId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v3/advertisers/${advertisersId}/channels").addQueryStringParameters("advertiserId" -> advertiserId.toString, "partnerId" -> partnerId.toString))
			}
			/** Gets a channel for a partner or advertiser. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Channel]) {
				val scopes = Seq("""https://www.googleapis.com/auth/display-video""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Channel])
			}
			object get {
				def apply(advertisersId :PlayApi, channelsId :PlayApi, advertiserId: String, channelId: String, partnerId: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v3/advertisers/${advertisersId}/channels/${channelsId}").addQueryStringParameters("advertiserId" -> advertiserId.toString, "channelId" -> channelId.toString, "partnerId" -> partnerId.toString))
				given Conversion[get, Future[Schema.Channel]] = (fun: get) => fun.apply()
			}
			/** Updates a channel. Returns the updated channel if successful. */
			class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/display-video""")
				/** Perform the request */
				def withChannel(body: Schema.Channel) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Channel])
			}
			object patch {
				def apply(advertisersId :PlayApi, advertiserId: String, channelId: String, partnerId: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v3/advertisers/${advertisersId}/channels/${channelId}").addQueryStringParameters("advertiserId" -> advertiserId.toString, "partnerId" -> partnerId.toString, "updateMask" -> updateMask.toString))
			}
			/** Lists channels for a partner or advertiser. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListChannelsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/display-video""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListChannelsResponse])
			}
			object list {
				def apply(advertisersId :PlayApi, advertiserId: String, partnerId: String, pageSize: Int, pageToken: String, orderBy: String, filter: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v3/advertisers/${advertisersId}/channels").addQueryStringParameters("advertiserId" -> advertiserId.toString, "partnerId" -> partnerId.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "orderBy" -> orderBy.toString, "filter" -> filter.toString))
				given Conversion[list, Future[Schema.ListChannelsResponse]] = (fun: list) => fun.apply()
			}
			object sites {
				/** Replaces all of the sites under a single channel. The operation will replace the sites under a channel with the sites provided in ReplaceSitesRequest.new_sites. &#42;&#42;This method regularly experiences high latency.&#42;&#42; We recommend [increasing your default timeout](/display-video/api/guides/best-practices/timeouts#client_library_timeout) to avoid errors. */
				class replace(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/display-video""")
					/** Perform the request */
					def withReplaceSitesRequest(body: Schema.ReplaceSitesRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.ReplaceSitesResponse])
				}
				object replace {
					def apply(channelsId :PlayApi, advertiserId: String, channelId: String)(using signer: RequestSigner, ec: ExecutionContext): replace = new replace(ws.url(BASE_URL + s"v3/advertisers/${advertiserId}/channels/${channelsId}/sites:replace").addQueryStringParameters("channelId" -> channelId.toString))
				}
				/** Creates a site in a channel. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/display-video""")
					/** Perform the request */
					def withSite(body: Schema.Site) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Site])
				}
				object create {
					def apply(channelsId :PlayApi, advertiserId: String, channelId: String, partnerId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v3/advertisers/${advertiserId}/channels/${channelsId}/sites").addQueryStringParameters("channelId" -> channelId.toString, "partnerId" -> partnerId.toString))
				}
				/** Bulk edits sites under a single channel. The operation will delete the sites provided in BulkEditSitesRequest.deleted_sites and then create the sites provided in BulkEditSitesRequest.created_sites. */
				class bulkEdit(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/display-video""")
					/** Perform the request */
					def withBulkEditSitesRequest(body: Schema.BulkEditSitesRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.BulkEditSitesResponse])
				}
				object bulkEdit {
					def apply(channelsId :PlayApi, advertiserId: String, channelId: String)(using signer: RequestSigner, ec: ExecutionContext): bulkEdit = new bulkEdit(ws.url(BASE_URL + s"v3/advertisers/${advertiserId}/channels/${channelsId}/sites:bulkEdit").addQueryStringParameters("channelId" -> channelId.toString))
				}
				/** Deletes a site from a channel. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					val scopes = Seq("""https://www.googleapis.com/auth/display-video""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
				}
				object delete {
					def apply(channelsId :PlayApi, sitesId :PlayApi, advertiserId: String, channelId: String, urlOrAppId: String, partnerId: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v3/advertisers/${advertiserId}/channels/${channelsId}/sites/${sitesId}").addQueryStringParameters("channelId" -> channelId.toString, "urlOrAppId" -> urlOrAppId.toString, "partnerId" -> partnerId.toString))
					given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
				}
				/** Lists sites in a channel. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListSitesResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/display-video""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListSitesResponse])
				}
				object list {
					def apply(advertisersId :PlayApi, channelsId :PlayApi, advertiserId: String, channelId: String, partnerId: String, pageSize: Int, pageToken: String, orderBy: String, filter: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v3/advertisers/${advertisersId}/channels/${channelsId}/sites").addQueryStringParameters("advertiserId" -> advertiserId.toString, "channelId" -> channelId.toString, "partnerId" -> partnerId.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "orderBy" -> orderBy.toString, "filter" -> filter.toString))
					given Conversion[list, Future[Schema.ListSitesResponse]] = (fun: list) => fun.apply()
				}
			}
		}
		object invoices {
			/** Lists invoices posted for an advertiser in a given month. Invoices generated by billing profiles with a "Partner" invoice level are not retrievable through this method. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListInvoicesResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/display-video""", """https://www.googleapis.com/auth/display-video-mediaplanning""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListInvoicesResponse])
			}
			object list {
				def apply(advertisersId :PlayApi, advertiserId: String, issueMonth: String, loiSapinInvoiceType: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v3/advertisers/${advertisersId}/invoices").addQueryStringParameters("advertiserId" -> advertiserId.toString, "issueMonth" -> issueMonth.toString, "loiSapinInvoiceType" -> loiSapinInvoiceType.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListInvoicesResponse]] = (fun: list) => fun.apply()
			}
			/** Retrieves the invoice currency used by an advertiser in a given month. */
			class lookupInvoiceCurrency(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.LookupInvoiceCurrencyResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/display-video""", """https://www.googleapis.com/auth/display-video-mediaplanning""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.LookupInvoiceCurrencyResponse])
			}
			object lookupInvoiceCurrency {
				def apply(advertisersId :PlayApi, advertiserId: String, invoiceMonth: String)(using signer: RequestSigner, ec: ExecutionContext): lookupInvoiceCurrency = new lookupInvoiceCurrency(ws.url(BASE_URL + s"v3/advertisers/${advertisersId}/invoices:lookupInvoiceCurrency").addQueryStringParameters("advertiserId" -> advertiserId.toString, "invoiceMonth" -> invoiceMonth.toString))
				given Conversion[lookupInvoiceCurrency, Future[Schema.LookupInvoiceCurrencyResponse]] = (fun: lookupInvoiceCurrency) => fun.apply()
			}
		}
		object negativeKeywordLists {
			/** Creates a new negative keyword list. Returns the newly created negative keyword list if successful. */
			class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/display-video""")
				/** Perform the request */
				def withNegativeKeywordList(body: Schema.NegativeKeywordList) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.NegativeKeywordList])
			}
			object create {
				def apply(advertisersId :PlayApi, advertiserId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v3/advertisers/${advertisersId}/negativeKeywordLists").addQueryStringParameters("advertiserId" -> advertiserId.toString))
			}
			/** Deletes a negative keyword list given an advertiser ID and a negative keyword list ID. */
			class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
				val scopes = Seq("""https://www.googleapis.com/auth/display-video""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
			}
			object delete {
				def apply(advertisersId :PlayApi, negativeKeywordListsId :PlayApi, advertiserId: String, negativeKeywordListId: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v3/advertisers/${advertisersId}/negativeKeywordLists/${negativeKeywordListsId}").addQueryStringParameters("advertiserId" -> advertiserId.toString, "negativeKeywordListId" -> negativeKeywordListId.toString))
				given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
			}
			/** Gets a negative keyword list given an advertiser ID and a negative keyword list ID. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.NegativeKeywordList]) {
				val scopes = Seq("""https://www.googleapis.com/auth/display-video""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.NegativeKeywordList])
			}
			object get {
				def apply(advertisersId :PlayApi, negativeKeywordListsId :PlayApi, advertiserId: String, negativeKeywordListId: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v3/advertisers/${advertisersId}/negativeKeywordLists/${negativeKeywordListsId}").addQueryStringParameters("advertiserId" -> advertiserId.toString, "negativeKeywordListId" -> negativeKeywordListId.toString))
				given Conversion[get, Future[Schema.NegativeKeywordList]] = (fun: get) => fun.apply()
			}
			/** Updates a negative keyword list. Returns the updated negative keyword list if successful. */
			class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/display-video""")
				/** Perform the request */
				def withNegativeKeywordList(body: Schema.NegativeKeywordList) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.NegativeKeywordList])
			}
			object patch {
				def apply(advertisersId :PlayApi, advertiserId: String, negativeKeywordListId: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v3/advertisers/${advertisersId}/negativeKeywordLists/${negativeKeywordListId}").addQueryStringParameters("advertiserId" -> advertiserId.toString, "updateMask" -> updateMask.toString))
			}
			/** Lists negative keyword lists based on a given advertiser id. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListNegativeKeywordListsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/display-video""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListNegativeKeywordListsResponse])
			}
			object list {
				def apply(advertisersId :PlayApi, advertiserId: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v3/advertisers/${advertisersId}/negativeKeywordLists").addQueryStringParameters("advertiserId" -> advertiserId.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListNegativeKeywordListsResponse]] = (fun: list) => fun.apply()
			}
			object negativeKeywords {
				/** Replaces all negative keywords in a single negative keyword list. The operation will replace the keywords in a negative keyword list with keywords provided in ReplaceNegativeKeywordsRequest.new_negative_keywords. */
				class replace(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/display-video""")
					/** Perform the request */
					def withReplaceNegativeKeywordsRequest(body: Schema.ReplaceNegativeKeywordsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.ReplaceNegativeKeywordsResponse])
				}
				object replace {
					def apply(negativeKeywordListsId :PlayApi, advertiserId: String, negativeKeywordListId: String)(using signer: RequestSigner, ec: ExecutionContext): replace = new replace(ws.url(BASE_URL + s"v3/advertisers/${advertiserId}/negativeKeywordLists/${negativeKeywordListsId}/negativeKeywords:replace").addQueryStringParameters("negativeKeywordListId" -> negativeKeywordListId.toString))
				}
				/** Creates a negative keyword in a negative keyword list. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/display-video""")
					/** Perform the request */
					def withNegativeKeyword(body: Schema.NegativeKeyword) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.NegativeKeyword])
				}
				object create {
					def apply(negativeKeywordListsId :PlayApi, advertiserId: String, negativeKeywordListId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v3/advertisers/${advertiserId}/negativeKeywordLists/${negativeKeywordListsId}/negativeKeywords").addQueryStringParameters("negativeKeywordListId" -> negativeKeywordListId.toString))
				}
				/** Bulk edits negative keywords in a single negative keyword list. The operation will delete the negative keywords provided in BulkEditNegativeKeywordsRequest.deleted_negative_keywords and then create the negative keywords provided in BulkEditNegativeKeywordsRequest.created_negative_keywords. This operation is guaranteed to be atomic and will never result in a partial success or partial failure. */
				class bulkEdit(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/display-video""")
					/** Perform the request */
					def withBulkEditNegativeKeywordsRequest(body: Schema.BulkEditNegativeKeywordsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.BulkEditNegativeKeywordsResponse])
				}
				object bulkEdit {
					def apply(negativeKeywordListsId :PlayApi, advertiserId: String, negativeKeywordListId: String)(using signer: RequestSigner, ec: ExecutionContext): bulkEdit = new bulkEdit(ws.url(BASE_URL + s"v3/advertisers/${advertiserId}/negativeKeywordLists/${negativeKeywordListsId}/negativeKeywords:bulkEdit").addQueryStringParameters("negativeKeywordListId" -> negativeKeywordListId.toString))
				}
				/** Deletes a negative keyword from a negative keyword list. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					val scopes = Seq("""https://www.googleapis.com/auth/display-video""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
				}
				object delete {
					def apply(negativeKeywordListsId :PlayApi, negativeKeywordsId :PlayApi, advertiserId: String, negativeKeywordListId: String, keywordValue: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v3/advertisers/${advertiserId}/negativeKeywordLists/${negativeKeywordListsId}/negativeKeywords/${negativeKeywordsId}").addQueryStringParameters("negativeKeywordListId" -> negativeKeywordListId.toString, "keywordValue" -> keywordValue.toString))
					given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
				}
				/** Lists negative keywords in a negative keyword list. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListNegativeKeywordsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/display-video""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListNegativeKeywordsResponse])
				}
				object list {
					def apply(advertisersId :PlayApi, negativeKeywordListsId :PlayApi, advertiserId: String, negativeKeywordListId: String, pageSize: Int, pageToken: String, orderBy: String, filter: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v3/advertisers/${advertisersId}/negativeKeywordLists/${negativeKeywordListsId}/negativeKeywords").addQueryStringParameters("advertiserId" -> advertiserId.toString, "negativeKeywordListId" -> negativeKeywordListId.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "orderBy" -> orderBy.toString, "filter" -> filter.toString))
					given Conversion[list, Future[Schema.ListNegativeKeywordsResponse]] = (fun: list) => fun.apply()
				}
			}
		}
		object creatives {
			/** Creates a new creative. Returns the newly created creative if successful. A ["Standard" user role](//support.google.com/displayvideo/answer/2723011) or greater for the parent advertiser or partner is required to make this request. */
			class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/display-video""")
				/** Perform the request */
				def withCreative(body: Schema.Creative) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Creative])
			}
			object create {
				def apply(advertisersId :PlayApi, advertiserId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v3/advertisers/${advertisersId}/creatives").addQueryStringParameters("advertiserId" -> advertiserId.toString))
			}
			/** Deletes a creative. Returns error code `NOT_FOUND` if the creative does not exist. The creative should be archived first, i.e. set entity_status to `ENTITY_STATUS_ARCHIVED`, before it can be deleted. A ["Standard" user role](//support.google.com/displayvideo/answer/2723011) or greater for the parent advertiser or partner is required to make this request. */
			class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
				val scopes = Seq("""https://www.googleapis.com/auth/display-video""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
			}
			object delete {
				def apply(advertisersId :PlayApi, creativesId :PlayApi, advertiserId: String, creativeId: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v3/advertisers/${advertisersId}/creatives/${creativesId}").addQueryStringParameters("advertiserId" -> advertiserId.toString, "creativeId" -> creativeId.toString))
				given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
			}
			/** Gets a creative. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Creative]) {
				val scopes = Seq("""https://www.googleapis.com/auth/display-video""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Creative])
			}
			object get {
				def apply(advertisersId :PlayApi, creativesId :PlayApi, advertiserId: String, creativeId: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v3/advertisers/${advertisersId}/creatives/${creativesId}").addQueryStringParameters("advertiserId" -> advertiserId.toString, "creativeId" -> creativeId.toString))
				given Conversion[get, Future[Schema.Creative]] = (fun: get) => fun.apply()
			}
			/** Updates an existing creative. Returns the updated creative if successful. A ["Standard" user role](//support.google.com/displayvideo/answer/2723011) or greater for the parent advertiser or partner is required to make this request. */
			class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/display-video""")
				/** Perform the request */
				def withCreative(body: Schema.Creative) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Creative])
			}
			object patch {
				def apply(advertisersId :PlayApi, creativesId :PlayApi, advertiserId: String, creativeId: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v3/advertisers/${advertisersId}/creatives/${creativesId}").addQueryStringParameters("advertiserId" -> advertiserId.toString, "creativeId" -> creativeId.toString, "updateMask" -> updateMask.toString))
			}
			/** Lists creatives in an advertiser. The order is defined by the order_by parameter. If a filter by entity_status is not specified, creatives with `ENTITY_STATUS_ARCHIVED` will not be included in the results. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListCreativesResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/display-video""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListCreativesResponse])
			}
			object list {
				def apply(advertisersId :PlayApi, advertiserId: String, pageSize: Int, pageToken: String, orderBy: String, filter: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v3/advertisers/${advertisersId}/creatives").addQueryStringParameters("advertiserId" -> advertiserId.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "orderBy" -> orderBy.toString, "filter" -> filter.toString))
				given Conversion[list, Future[Schema.ListCreativesResponse]] = (fun: list) => fun.apply()
			}
		}
		object insertionOrders {
			/** Creates a new insertion order. Returns the newly created insertion order if successful. */
			class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/display-video""")
				/** Perform the request */
				def withInsertionOrder(body: Schema.InsertionOrder) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.InsertionOrder])
			}
			object create {
				def apply(advertisersId :PlayApi, advertiserId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v3/advertisers/${advertisersId}/insertionOrders").addQueryStringParameters("advertiserId" -> advertiserId.toString))
			}
			/** Lists assigned targeting options of an insertion order across targeting types. */
			class listAssignedTargetingOptions(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.BulkListInsertionOrderAssignedTargetingOptionsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/display-video""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.BulkListInsertionOrderAssignedTargetingOptionsResponse])
			}
			object listAssignedTargetingOptions {
				def apply(advertisersId :PlayApi, insertionOrdersId :PlayApi, advertiserId: String, insertionOrderId: String, pageSize: Int, pageToken: String, orderBy: String, filter: String)(using signer: RequestSigner, ec: ExecutionContext): listAssignedTargetingOptions = new listAssignedTargetingOptions(ws.url(BASE_URL + s"v3/advertisers/${advertisersId}/insertionOrders/${insertionOrdersId}:listAssignedTargetingOptions").addQueryStringParameters("advertiserId" -> advertiserId.toString, "insertionOrderId" -> insertionOrderId.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "orderBy" -> orderBy.toString, "filter" -> filter.toString))
				given Conversion[listAssignedTargetingOptions, Future[Schema.BulkListInsertionOrderAssignedTargetingOptionsResponse]] = (fun: listAssignedTargetingOptions) => fun.apply()
			}
			/** Deletes an insertion order. Returns error code `NOT_FOUND` if the insertion order does not exist. The insertion order should be archived first, i.e. set entity_status to `ENTITY_STATUS_ARCHIVED`, to be able to delete it. */
			class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
				val scopes = Seq("""https://www.googleapis.com/auth/display-video""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
			}
			object delete {
				def apply(advertisersId :PlayApi, insertionOrdersId :PlayApi, advertiserId: String, insertionOrderId: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v3/advertisers/${advertisersId}/insertionOrders/${insertionOrdersId}").addQueryStringParameters("advertiserId" -> advertiserId.toString, "insertionOrderId" -> insertionOrderId.toString))
				given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
			}
			/** Gets an insertion order. Returns error code `NOT_FOUND` if the insertion order does not exist. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.InsertionOrder]) {
				val scopes = Seq("""https://www.googleapis.com/auth/display-video""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.InsertionOrder])
			}
			object get {
				def apply(advertisersId :PlayApi, insertionOrdersId :PlayApi, advertiserId: String, insertionOrderId: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v3/advertisers/${advertisersId}/insertionOrders/${insertionOrdersId}").addQueryStringParameters("advertiserId" -> advertiserId.toString, "insertionOrderId" -> insertionOrderId.toString))
				given Conversion[get, Future[Schema.InsertionOrder]] = (fun: get) => fun.apply()
			}
			/** Updates an existing insertion order. Returns the updated insertion order if successful. */
			class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/display-video""")
				/** Perform the request */
				def withInsertionOrder(body: Schema.InsertionOrder) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.InsertionOrder])
			}
			object patch {
				def apply(advertisersId :PlayApi, insertionOrdersId :PlayApi, advertiserId: String, insertionOrderId: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v3/advertisers/${advertisersId}/insertionOrders/${insertionOrdersId}").addQueryStringParameters("advertiserId" -> advertiserId.toString, "insertionOrderId" -> insertionOrderId.toString, "updateMask" -> updateMask.toString))
			}
			/** Lists insertion orders in an advertiser. The order is defined by the order_by parameter. If a filter by entity_status is not specified, insertion orders with `ENTITY_STATUS_ARCHIVED` will not be included in the results. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListInsertionOrdersResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/display-video""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListInsertionOrdersResponse])
			}
			object list {
				def apply(advertisersId :PlayApi, advertiserId: String, pageSize: Int, pageToken: String, orderBy: String, filter: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v3/advertisers/${advertisersId}/insertionOrders").addQueryStringParameters("advertiserId" -> advertiserId.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "orderBy" -> orderBy.toString, "filter" -> filter.toString))
				given Conversion[list, Future[Schema.ListInsertionOrdersResponse]] = (fun: list) => fun.apply()
			}
			object targetingTypes {
				object assignedTargetingOptions {
					/** Gets a single targeting option assigned to an insertion order. */
					class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.AssignedTargetingOption]) {
						val scopes = Seq("""https://www.googleapis.com/auth/display-video""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.AssignedTargetingOption])
					}
					object get {
						def apply(advertisersId :PlayApi, insertionOrdersId :PlayApi, targetingTypesId :PlayApi, assignedTargetingOptionsId :PlayApi, advertiserId: String, insertionOrderId: String, targetingType: String, assignedTargetingOptionId: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v3/advertisers/${advertisersId}/insertionOrders/${insertionOrdersId}/targetingTypes/${targetingTypesId}/assignedTargetingOptions/${assignedTargetingOptionsId}").addQueryStringParameters("advertiserId" -> advertiserId.toString, "insertionOrderId" -> insertionOrderId.toString, "targetingType" -> targetingType.toString, "assignedTargetingOptionId" -> assignedTargetingOptionId.toString))
						given Conversion[get, Future[Schema.AssignedTargetingOption]] = (fun: get) => fun.apply()
					}
					/** Lists the targeting options assigned to an insertion order. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListInsertionOrderAssignedTargetingOptionsResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/display-video""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListInsertionOrderAssignedTargetingOptionsResponse])
					}
					object list {
						def apply(advertisersId :PlayApi, insertionOrdersId :PlayApi, targetingTypesId :PlayApi, advertiserId: String, insertionOrderId: String, targetingType: String, pageSize: Int, pageToken: String, orderBy: String, filter: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v3/advertisers/${advertisersId}/insertionOrders/${insertionOrdersId}/targetingTypes/${targetingTypesId}/assignedTargetingOptions").addQueryStringParameters("advertiserId" -> advertiserId.toString, "insertionOrderId" -> insertionOrderId.toString, "targetingType" -> targetingType.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "orderBy" -> orderBy.toString, "filter" -> filter.toString))
						given Conversion[list, Future[Schema.ListInsertionOrderAssignedTargetingOptionsResponse]] = (fun: list) => fun.apply()
					}
					/** Assigns a targeting option to an insertion order. Returns the assigned targeting option if successful. Supported targeting types: &#42; `TARGETING_TYPE_AGE_RANGE` &#42; `TARGETING_TYPE_BROWSER` &#42; `TARGETING_TYPE_CATEGORY` &#42; `TARGETING_TYPE_CHANNEL` &#42; `TARGETING_TYPE_DEVICE_MAKE_MODEL` &#42; `TARGETING_TYPE_DIGITAL_CONTENT_LABEL_EXCLUSION` &#42; `TARGETING_TYPE_ENVIRONMENT` &#42; `TARGETING_TYPE_GENDER` &#42; `TARGETING_TYPE_KEYWORD` &#42; `TARGETING_TYPE_LANGUAGE` &#42; `TARGETING_TYPE_NEGATIVE_KEYWORD_LIST` &#42; `TARGETING_TYPE_OPERATING_SYSTEM` &#42; `TARGETING_TYPE_PARENTAL_STATUS` &#42; `TARGETING_TYPE_SENSITIVE_CATEGORY_EXCLUSION` &#42; `TARGETING_TYPE_VIEWABILITY` */
					class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/display-video""")
						/** Perform the request */
						def withAssignedTargetingOption(body: Schema.AssignedTargetingOption) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.AssignedTargetingOption])
					}
					object create {
						def apply(advertisersId :PlayApi, insertionOrdersId :PlayApi, targetingTypesId :PlayApi, advertiserId: String, insertionOrderId: String, targetingType: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v3/advertisers/${advertisersId}/insertionOrders/${insertionOrdersId}/targetingTypes/${targetingTypesId}/assignedTargetingOptions").addQueryStringParameters("advertiserId" -> advertiserId.toString, "insertionOrderId" -> insertionOrderId.toString, "targetingType" -> targetingType.toString))
					}
					/** Deletes an assigned targeting option from an insertion order. Supported targeting types: &#42; `TARGETING_TYPE_AGE_RANGE` &#42; `TARGETING_TYPE_BROWSER` &#42; `TARGETING_TYPE_CATEGORY` &#42; `TARGETING_TYPE_CHANNEL` &#42; `TARGETING_TYPE_DEVICE_MAKE_MODEL` &#42; `TARGETING_TYPE_DIGITAL_CONTENT_LABEL_EXCLUSION` &#42; `TARGETING_TYPE_ENVIRONMENT` &#42; `TARGETING_TYPE_GENDER` &#42; `TARGETING_TYPE_KEYWORD` &#42; `TARGETING_TYPE_LANGUAGE` &#42; `TARGETING_TYPE_NEGATIVE_KEYWORD_LIST` &#42; `TARGETING_TYPE_OPERATING_SYSTEM` &#42; `TARGETING_TYPE_PARENTAL_STATUS` &#42; `TARGETING_TYPE_SENSITIVE_CATEGORY_EXCLUSION` &#42; `TARGETING_TYPE_VIEWABILITY` */
					class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
						val scopes = Seq("""https://www.googleapis.com/auth/display-video""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
					}
					object delete {
						def apply(advertisersId :PlayApi, insertionOrdersId :PlayApi, targetingTypesId :PlayApi, assignedTargetingOptionsId :PlayApi, advertiserId: String, insertionOrderId: String, targetingType: String, assignedTargetingOptionId: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v3/advertisers/${advertisersId}/insertionOrders/${insertionOrdersId}/targetingTypes/${targetingTypesId}/assignedTargetingOptions/${assignedTargetingOptionsId}").addQueryStringParameters("advertiserId" -> advertiserId.toString, "insertionOrderId" -> insertionOrderId.toString, "targetingType" -> targetingType.toString, "assignedTargetingOptionId" -> assignedTargetingOptionId.toString))
						given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
					}
				}
			}
		}
		object locationLists {
			/** Creates a new location list. Returns the newly created location list if successful. */
			class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/display-video""")
				/** Perform the request */
				def withLocationList(body: Schema.LocationList) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.LocationList])
			}
			object create {
				def apply(advertisersId :PlayApi, advertiserId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v3/advertisers/${advertisersId}/locationLists").addQueryStringParameters("advertiserId" -> advertiserId.toString))
			}
			/** Gets a location list. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.LocationList]) {
				val scopes = Seq("""https://www.googleapis.com/auth/display-video""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.LocationList])
			}
			object get {
				def apply(advertisersId :PlayApi, locationListsId :PlayApi, advertiserId: String, locationListId: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v3/advertisers/${advertisersId}/locationLists/${locationListsId}").addQueryStringParameters("advertiserId" -> advertiserId.toString, "locationListId" -> locationListId.toString))
				given Conversion[get, Future[Schema.LocationList]] = (fun: get) => fun.apply()
			}
			/** Updates a location list. Returns the updated location list if successful. */
			class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/display-video""")
				/** Perform the request */
				def withLocationList(body: Schema.LocationList) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.LocationList])
			}
			object patch {
				def apply(advertisersId :PlayApi, advertiserId: String, locationListId: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v3/advertisers/${advertisersId}/locationLists/${locationListId}").addQueryStringParameters("advertiserId" -> advertiserId.toString, "updateMask" -> updateMask.toString))
			}
			/** Lists location lists based on a given advertiser id. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListLocationListsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/display-video""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListLocationListsResponse])
			}
			object list {
				def apply(advertisersId :PlayApi, advertiserId: String, pageSize: Int, pageToken: String, orderBy: String, filter: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v3/advertisers/${advertisersId}/locationLists").addQueryStringParameters("advertiserId" -> advertiserId.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "orderBy" -> orderBy.toString, "filter" -> filter.toString))
				given Conversion[list, Future[Schema.ListLocationListsResponse]] = (fun: list) => fun.apply()
			}
			object assignedLocations {
				/** Lists locations assigned to a location list. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListAssignedLocationsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/display-video""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListAssignedLocationsResponse])
				}
				object list {
					def apply(advertiserId: String, locationListId: String, pageSize: Int, pageToken: String, orderBy: String, filter: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v3/advertisers/${advertiserId}/locationLists/${locationListId}/assignedLocations").addQueryStringParameters("pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "orderBy" -> orderBy.toString, "filter" -> filter.toString))
					given Conversion[list, Future[Schema.ListAssignedLocationsResponse]] = (fun: list) => fun.apply()
				}
				/** Creates an assignment between a location and a location list. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/display-video""")
					/** Perform the request */
					def withAssignedLocation(body: Schema.AssignedLocation) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.AssignedLocation])
				}
				object create {
					def apply(advertiserId: String, locationListId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v3/advertisers/${advertiserId}/locationLists/${locationListId}/assignedLocations").addQueryStringParameters())
				}
				/** Deletes the assignment between a location and a location list. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					val scopes = Seq("""https://www.googleapis.com/auth/display-video""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
				}
				object delete {
					def apply(assignedLocationsId :PlayApi, advertiserId: String, locationListId: String, assignedLocationId: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v3/advertisers/${advertiserId}/locationLists/${locationListId}/assignedLocations/${assignedLocationsId}").addQueryStringParameters("assignedLocationId" -> assignedLocationId.toString))
					given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
				}
				/** Bulk edits multiple assignments between locations and a single location list. The operation will delete the assigned locations provided in deletedAssignedLocations and then create the assigned locations provided in createdAssignedLocations. */
				class bulkEdit(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/display-video""")
					/** Perform the request */
					def withBulkEditAssignedLocationsRequest(body: Schema.BulkEditAssignedLocationsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.BulkEditAssignedLocationsResponse])
				}
				object bulkEdit {
					def apply(locationListsId :PlayApi, advertiserId: String, locationListId: String)(using signer: RequestSigner, ec: ExecutionContext): bulkEdit = new bulkEdit(ws.url(BASE_URL + s"v3/advertisers/${advertiserId}/locationLists/${locationListsId}/assignedLocations:bulkEdit").addQueryStringParameters("locationListId" -> locationListId.toString))
				}
			}
		}
		object campaigns {
			/** Creates a new campaign. Returns the newly created campaign if successful. */
			class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/display-video""", """https://www.googleapis.com/auth/display-video-mediaplanning""")
				/** Perform the request */
				def withCampaign(body: Schema.Campaign) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Campaign])
			}
			object create {
				def apply(advertisersId :PlayApi, advertiserId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v3/advertisers/${advertisersId}/campaigns").addQueryStringParameters("advertiserId" -> advertiserId.toString))
			}
			/** Lists assigned targeting options of a campaign across targeting types. */
			class listAssignedTargetingOptions(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.BulkListCampaignAssignedTargetingOptionsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/display-video""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.BulkListCampaignAssignedTargetingOptionsResponse])
			}
			object listAssignedTargetingOptions {
				def apply(advertisersId :PlayApi, campaignsId :PlayApi, advertiserId: String, campaignId: String, pageSize: Int, pageToken: String, orderBy: String, filter: String)(using signer: RequestSigner, ec: ExecutionContext): listAssignedTargetingOptions = new listAssignedTargetingOptions(ws.url(BASE_URL + s"v3/advertisers/${advertisersId}/campaigns/${campaignsId}:listAssignedTargetingOptions").addQueryStringParameters("advertiserId" -> advertiserId.toString, "campaignId" -> campaignId.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "orderBy" -> orderBy.toString, "filter" -> filter.toString))
				given Conversion[listAssignedTargetingOptions, Future[Schema.BulkListCampaignAssignedTargetingOptionsResponse]] = (fun: listAssignedTargetingOptions) => fun.apply()
			}
			/** Permanently deletes a campaign. A deleted campaign cannot be recovered. The campaign should be archived first, i.e. set entity_status to `ENTITY_STATUS_ARCHIVED`, to be able to delete it. &#42;&#42;This method regularly experiences high latency.&#42;&#42; We recommend [increasing your default timeout](/display-video/api/guides/best-practices/timeouts#client_library_timeout) to avoid errors. */
			class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
				val scopes = Seq("""https://www.googleapis.com/auth/display-video""", """https://www.googleapis.com/auth/display-video-mediaplanning""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
			}
			object delete {
				def apply(advertisersId :PlayApi, campaignsId :PlayApi, advertiserId: String, campaignId: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v3/advertisers/${advertisersId}/campaigns/${campaignsId}").addQueryStringParameters("advertiserId" -> advertiserId.toString, "campaignId" -> campaignId.toString))
				given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
			}
			/** Gets a campaign. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Campaign]) {
				val scopes = Seq("""https://www.googleapis.com/auth/display-video""", """https://www.googleapis.com/auth/display-video-mediaplanning""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Campaign])
			}
			object get {
				def apply(advertisersId :PlayApi, campaignsId :PlayApi, advertiserId: String, campaignId: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v3/advertisers/${advertisersId}/campaigns/${campaignsId}").addQueryStringParameters("advertiserId" -> advertiserId.toString, "campaignId" -> campaignId.toString))
				given Conversion[get, Future[Schema.Campaign]] = (fun: get) => fun.apply()
			}
			/** Updates an existing campaign. Returns the updated campaign if successful. */
			class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/display-video""", """https://www.googleapis.com/auth/display-video-mediaplanning""")
				/** Perform the request */
				def withCampaign(body: Schema.Campaign) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Campaign])
			}
			object patch {
				def apply(advertisersId :PlayApi, campaignsId :PlayApi, advertiserId: String, campaignId: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v3/advertisers/${advertisersId}/campaigns/${campaignsId}").addQueryStringParameters("advertiserId" -> advertiserId.toString, "campaignId" -> campaignId.toString, "updateMask" -> updateMask.toString))
			}
			/** Lists campaigns in an advertiser. The order is defined by the order_by parameter. If a filter by entity_status is not specified, campaigns with `ENTITY_STATUS_ARCHIVED` will not be included in the results. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListCampaignsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/display-video""", """https://www.googleapis.com/auth/display-video-mediaplanning""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListCampaignsResponse])
			}
			object list {
				def apply(advertisersId :PlayApi, advertiserId: String, pageSize: Int, pageToken: String, orderBy: String, filter: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v3/advertisers/${advertisersId}/campaigns").addQueryStringParameters("advertiserId" -> advertiserId.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "orderBy" -> orderBy.toString, "filter" -> filter.toString))
				given Conversion[list, Future[Schema.ListCampaignsResponse]] = (fun: list) => fun.apply()
			}
			object targetingTypes {
				object assignedTargetingOptions {
					/** Gets a single targeting option assigned to a campaign. */
					class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.AssignedTargetingOption]) {
						val scopes = Seq("""https://www.googleapis.com/auth/display-video""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.AssignedTargetingOption])
					}
					object get {
						def apply(advertisersId :PlayApi, campaignsId :PlayApi, targetingTypesId :PlayApi, assignedTargetingOptionsId :PlayApi, advertiserId: String, campaignId: String, targetingType: String, assignedTargetingOptionId: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v3/advertisers/${advertisersId}/campaigns/${campaignsId}/targetingTypes/${targetingTypesId}/assignedTargetingOptions/${assignedTargetingOptionsId}").addQueryStringParameters("advertiserId" -> advertiserId.toString, "campaignId" -> campaignId.toString, "targetingType" -> targetingType.toString, "assignedTargetingOptionId" -> assignedTargetingOptionId.toString))
						given Conversion[get, Future[Schema.AssignedTargetingOption]] = (fun: get) => fun.apply()
					}
					/** Lists the targeting options assigned to a campaign for a specified targeting type. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListCampaignAssignedTargetingOptionsResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/display-video""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListCampaignAssignedTargetingOptionsResponse])
					}
					object list {
						def apply(advertisersId :PlayApi, campaignsId :PlayApi, targetingTypesId :PlayApi, advertiserId: String, campaignId: String, targetingType: String, pageSize: Int, pageToken: String, orderBy: String, filter: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v3/advertisers/${advertisersId}/campaigns/${campaignsId}/targetingTypes/${targetingTypesId}/assignedTargetingOptions").addQueryStringParameters("advertiserId" -> advertiserId.toString, "campaignId" -> campaignId.toString, "targetingType" -> targetingType.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "orderBy" -> orderBy.toString, "filter" -> filter.toString))
						given Conversion[list, Future[Schema.ListCampaignAssignedTargetingOptionsResponse]] = (fun: list) => fun.apply()
					}
				}
			}
		}
		object targetingTypes {
			object assignedTargetingOptions {
				/** Gets a single targeting option assigned to an advertiser. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.AssignedTargetingOption]) {
					val scopes = Seq("""https://www.googleapis.com/auth/display-video""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.AssignedTargetingOption])
				}
				object get {
					def apply(advertisersId :PlayApi, targetingTypesId :PlayApi, assignedTargetingOptionsId :PlayApi, advertiserId: String, targetingType: String, assignedTargetingOptionId: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v3/advertisers/${advertisersId}/targetingTypes/${targetingTypesId}/assignedTargetingOptions/${assignedTargetingOptionsId}").addQueryStringParameters("advertiserId" -> advertiserId.toString, "targetingType" -> targetingType.toString, "assignedTargetingOptionId" -> assignedTargetingOptionId.toString))
					given Conversion[get, Future[Schema.AssignedTargetingOption]] = (fun: get) => fun.apply()
				}
				/** Lists the targeting options assigned to an advertiser. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListAdvertiserAssignedTargetingOptionsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/display-video""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListAdvertiserAssignedTargetingOptionsResponse])
				}
				object list {
					def apply(advertisersId :PlayApi, targetingTypesId :PlayApi, advertiserId: String, targetingType: String, pageSize: Int, pageToken: String, orderBy: String, filter: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v3/advertisers/${advertisersId}/targetingTypes/${targetingTypesId}/assignedTargetingOptions").addQueryStringParameters("advertiserId" -> advertiserId.toString, "targetingType" -> targetingType.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "orderBy" -> orderBy.toString, "filter" -> filter.toString))
					given Conversion[list, Future[Schema.ListAdvertiserAssignedTargetingOptionsResponse]] = (fun: list) => fun.apply()
				}
				/** Assigns a targeting option to an advertiser. Returns the assigned targeting option if successful. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/display-video""")
					/** Perform the request */
					def withAssignedTargetingOption(body: Schema.AssignedTargetingOption) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.AssignedTargetingOption])
				}
				object create {
					def apply(advertisersId :PlayApi, targetingTypesId :PlayApi, advertiserId: String, targetingType: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v3/advertisers/${advertisersId}/targetingTypes/${targetingTypesId}/assignedTargetingOptions").addQueryStringParameters("advertiserId" -> advertiserId.toString, "targetingType" -> targetingType.toString))
				}
				/** Deletes an assigned targeting option from an advertiser. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					val scopes = Seq("""https://www.googleapis.com/auth/display-video""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
				}
				object delete {
					def apply(advertisersId :PlayApi, targetingTypesId :PlayApi, assignedTargetingOptionsId :PlayApi, advertiserId: String, targetingType: String, assignedTargetingOptionId: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v3/advertisers/${advertisersId}/targetingTypes/${targetingTypesId}/assignedTargetingOptions/${assignedTargetingOptionsId}").addQueryStringParameters("advertiserId" -> advertiserId.toString, "targetingType" -> targetingType.toString, "assignedTargetingOptionId" -> assignedTargetingOptionId.toString))
					given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
				}
			}
		}
	}
	object combinedAudiences {
		/** Gets a combined audience. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.CombinedAudience]) {
			val scopes = Seq("""https://www.googleapis.com/auth/display-video""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.CombinedAudience])
		}
		object get {
			def apply(combinedAudiencesId :PlayApi, combinedAudienceId: String, partnerId: String, advertiserId: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v3/combinedAudiences/${combinedAudiencesId}").addQueryStringParameters("combinedAudienceId" -> combinedAudienceId.toString, "partnerId" -> partnerId.toString, "advertiserId" -> advertiserId.toString))
			given Conversion[get, Future[Schema.CombinedAudience]] = (fun: get) => fun.apply()
		}
		/** Lists combined audiences. The order is defined by the order_by parameter. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListCombinedAudiencesResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/display-video""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListCombinedAudiencesResponse])
		}
		object list {
			def apply(partnerId: String, advertiserId: String, pageSize: Int, pageToken: String, orderBy: String, filter: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v3/combinedAudiences").addQueryStringParameters("partnerId" -> partnerId.toString, "advertiserId" -> advertiserId.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "orderBy" -> orderBy.toString, "filter" -> filter.toString))
			given Conversion[list, Future[Schema.ListCombinedAudiencesResponse]] = (fun: list) => fun.apply()
		}
	}
	object customBiddingAlgorithms {
		/** Creates a new custom bidding algorithm. Returns the newly created custom bidding algorithm if successful. */
		class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/display-video""")
			/** Perform the request */
			def withCustomBiddingAlgorithm(body: Schema.CustomBiddingAlgorithm) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.CustomBiddingAlgorithm])
		}
		object create {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v3/customBiddingAlgorithms").addQueryStringParameters())
		}
		/** Creates a custom bidding script reference object for a script file. The resulting reference object provides a resource path to which the script file should be uploaded. This reference object should be included in when creating a new custom bidding script object. */
		class uploadScript(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.CustomBiddingScriptRef]) {
			val scopes = Seq("""https://www.googleapis.com/auth/display-video""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.CustomBiddingScriptRef])
		}
		object uploadScript {
			def apply(customBiddingAlgorithmsId :PlayApi, customBiddingAlgorithmId: String, partnerId: String, advertiserId: String)(using signer: RequestSigner, ec: ExecutionContext): uploadScript = new uploadScript(ws.url(BASE_URL + s"v3/customBiddingAlgorithms/${customBiddingAlgorithmsId}:uploadScript").addQueryStringParameters("customBiddingAlgorithmId" -> customBiddingAlgorithmId.toString, "partnerId" -> partnerId.toString, "advertiserId" -> advertiserId.toString))
			given Conversion[uploadScript, Future[Schema.CustomBiddingScriptRef]] = (fun: uploadScript) => fun.apply()
		}
		/** Lists custom bidding algorithms that are accessible to the current user and can be used in bidding stratgies. The order is defined by the order_by parameter. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListCustomBiddingAlgorithmsResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/display-video""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListCustomBiddingAlgorithmsResponse])
		}
		object list {
			def apply(partnerId: String, advertiserId: String, pageSize: Int, pageToken: String, orderBy: String, filter: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v3/customBiddingAlgorithms").addQueryStringParameters("partnerId" -> partnerId.toString, "advertiserId" -> advertiserId.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "orderBy" -> orderBy.toString, "filter" -> filter.toString))
			given Conversion[list, Future[Schema.ListCustomBiddingAlgorithmsResponse]] = (fun: list) => fun.apply()
		}
		/** Gets a custom bidding algorithm. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.CustomBiddingAlgorithm]) {
			val scopes = Seq("""https://www.googleapis.com/auth/display-video""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.CustomBiddingAlgorithm])
		}
		object get {
			def apply(customBiddingAlgorithmsId :PlayApi, customBiddingAlgorithmId: String, partnerId: String, advertiserId: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v3/customBiddingAlgorithms/${customBiddingAlgorithmsId}").addQueryStringParameters("customBiddingAlgorithmId" -> customBiddingAlgorithmId.toString, "partnerId" -> partnerId.toString, "advertiserId" -> advertiserId.toString))
			given Conversion[get, Future[Schema.CustomBiddingAlgorithm]] = (fun: get) => fun.apply()
		}
		/** Creates a rules reference object for an AlgorithmRules file. The resulting reference object provides a resource path where the AlgorithmRules file should be uploaded. This reference object should be included when creating a new CustomBiddingAlgorithmRules resource. */
		class uploadRules(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.CustomBiddingAlgorithmRulesRef]) {
			val scopes = Seq("""https://www.googleapis.com/auth/display-video""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.CustomBiddingAlgorithmRulesRef])
		}
		object uploadRules {
			def apply(customBiddingAlgorithmsId :PlayApi, customBiddingAlgorithmId: String, partnerId: String, advertiserId: String)(using signer: RequestSigner, ec: ExecutionContext): uploadRules = new uploadRules(ws.url(BASE_URL + s"v3/customBiddingAlgorithms/${customBiddingAlgorithmsId}:uploadRules").addQueryStringParameters("customBiddingAlgorithmId" -> customBiddingAlgorithmId.toString, "partnerId" -> partnerId.toString, "advertiserId" -> advertiserId.toString))
			given Conversion[uploadRules, Future[Schema.CustomBiddingAlgorithmRulesRef]] = (fun: uploadRules) => fun.apply()
		}
		/** Updates an existing custom bidding algorithm. Returns the updated custom bidding algorithm if successful. */
		class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/display-video""")
			/** Perform the request */
			def withCustomBiddingAlgorithm(body: Schema.CustomBiddingAlgorithm) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.CustomBiddingAlgorithm])
		}
		object patch {
			def apply(customBiddingAlgorithmsId :PlayApi, customBiddingAlgorithmId: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v3/customBiddingAlgorithms/${customBiddingAlgorithmsId}").addQueryStringParameters("customBiddingAlgorithmId" -> customBiddingAlgorithmId.toString, "updateMask" -> updateMask.toString))
		}
		object rules {
			/** Creates a new rules resource. Returns the newly created rules resource if successful. */
			class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/display-video""")
				/** Perform the request */
				def withCustomBiddingAlgorithmRules(body: Schema.CustomBiddingAlgorithmRules) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.CustomBiddingAlgorithmRules])
			}
			object create {
				def apply(customBiddingAlgorithmsId :PlayApi, customBiddingAlgorithmId: String, partnerId: String, advertiserId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v3/customBiddingAlgorithms/${customBiddingAlgorithmsId}/rules").addQueryStringParameters("customBiddingAlgorithmId" -> customBiddingAlgorithmId.toString, "partnerId" -> partnerId.toString, "advertiserId" -> advertiserId.toString))
			}
			/** Retrieves a rules resource. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.CustomBiddingAlgorithmRules]) {
				val scopes = Seq("""https://www.googleapis.com/auth/display-video""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.CustomBiddingAlgorithmRules])
			}
			object get {
				def apply(customBiddingAlgorithmsId :PlayApi, rulesId :PlayApi, customBiddingAlgorithmId: String, customBiddingAlgorithmRulesId: String, partnerId: String, advertiserId: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v3/customBiddingAlgorithms/${customBiddingAlgorithmsId}/rules/${rulesId}").addQueryStringParameters("customBiddingAlgorithmId" -> customBiddingAlgorithmId.toString, "customBiddingAlgorithmRulesId" -> customBiddingAlgorithmRulesId.toString, "partnerId" -> partnerId.toString, "advertiserId" -> advertiserId.toString))
				given Conversion[get, Future[Schema.CustomBiddingAlgorithmRules]] = (fun: get) => fun.apply()
			}
			/** Lists rules resources that belong to the given algorithm. The order is defined by the order_by parameter. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListCustomBiddingAlgorithmRulesResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/display-video""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListCustomBiddingAlgorithmRulesResponse])
			}
			object list {
				def apply(customBiddingAlgorithmsId :PlayApi, customBiddingAlgorithmId: String, partnerId: String, advertiserId: String, pageSize: Int, pageToken: String, orderBy: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v3/customBiddingAlgorithms/${customBiddingAlgorithmsId}/rules").addQueryStringParameters("customBiddingAlgorithmId" -> customBiddingAlgorithmId.toString, "partnerId" -> partnerId.toString, "advertiserId" -> advertiserId.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "orderBy" -> orderBy.toString))
				given Conversion[list, Future[Schema.ListCustomBiddingAlgorithmRulesResponse]] = (fun: list) => fun.apply()
			}
		}
		object scripts {
			/** Creates a new custom bidding script. Returns the newly created script if successful. */
			class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/display-video""")
				/** Perform the request */
				def withCustomBiddingScript(body: Schema.CustomBiddingScript) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.CustomBiddingScript])
			}
			object create {
				def apply(customBiddingAlgorithmsId :PlayApi, customBiddingAlgorithmId: String, partnerId: String, advertiserId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v3/customBiddingAlgorithms/${customBiddingAlgorithmsId}/scripts").addQueryStringParameters("customBiddingAlgorithmId" -> customBiddingAlgorithmId.toString, "partnerId" -> partnerId.toString, "advertiserId" -> advertiserId.toString))
			}
			/** Gets a custom bidding script. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.CustomBiddingScript]) {
				val scopes = Seq("""https://www.googleapis.com/auth/display-video""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.CustomBiddingScript])
			}
			object get {
				def apply(customBiddingAlgorithmsId :PlayApi, scriptsId :PlayApi, customBiddingAlgorithmId: String, customBiddingScriptId: String, partnerId: String, advertiserId: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v3/customBiddingAlgorithms/${customBiddingAlgorithmsId}/scripts/${scriptsId}").addQueryStringParameters("customBiddingAlgorithmId" -> customBiddingAlgorithmId.toString, "customBiddingScriptId" -> customBiddingScriptId.toString, "partnerId" -> partnerId.toString, "advertiserId" -> advertiserId.toString))
				given Conversion[get, Future[Schema.CustomBiddingScript]] = (fun: get) => fun.apply()
			}
			/** Lists custom bidding scripts that belong to the given algorithm. The order is defined by the order_by parameter. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListCustomBiddingScriptsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/display-video""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListCustomBiddingScriptsResponse])
			}
			object list {
				def apply(customBiddingAlgorithmsId :PlayApi, customBiddingAlgorithmId: String, partnerId: String, advertiserId: String, pageSize: Int, pageToken: String, orderBy: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v3/customBiddingAlgorithms/${customBiddingAlgorithmsId}/scripts").addQueryStringParameters("customBiddingAlgorithmId" -> customBiddingAlgorithmId.toString, "partnerId" -> partnerId.toString, "advertiserId" -> advertiserId.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "orderBy" -> orderBy.toString))
				given Conversion[list, Future[Schema.ListCustomBiddingScriptsResponse]] = (fun: list) => fun.apply()
			}
		}
	}
	object customLists {
		/** Gets a custom list. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.CustomList]) {
			val scopes = Seq("""https://www.googleapis.com/auth/display-video""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.CustomList])
		}
		object get {
			def apply(customListsId :PlayApi, customListId: String, advertiserId: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v3/customLists/${customListsId}").addQueryStringParameters("customListId" -> customListId.toString, "advertiserId" -> advertiserId.toString))
			given Conversion[get, Future[Schema.CustomList]] = (fun: get) => fun.apply()
		}
		/** Lists custom lists. The order is defined by the order_by parameter. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListCustomListsResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/display-video""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListCustomListsResponse])
		}
		object list {
			def apply(advertiserId: String, pageSize: Int, pageToken: String, orderBy: String, filter: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v3/customLists").addQueryStringParameters("advertiserId" -> advertiserId.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "orderBy" -> orderBy.toString, "filter" -> filter.toString))
			given Conversion[list, Future[Schema.ListCustomListsResponse]] = (fun: list) => fun.apply()
		}
	}
	object firstAndThirdPartyAudiences {
		/** Creates a FirstAndThirdPartyAudience. Only supported for the following audience_type: &#42; `CUSTOMER_MATCH_CONTACT_INFO` &#42; `CUSTOMER_MATCH_DEVICE_ID` */
		class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/display-video""")
			/** Perform the request */
			def withFirstAndThirdPartyAudience(body: Schema.FirstAndThirdPartyAudience) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.FirstAndThirdPartyAudience])
		}
		object create {
			def apply(advertiserId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v3/firstAndThirdPartyAudiences").addQueryStringParameters("advertiserId" -> advertiserId.toString))
		}
		/** Gets a first and third party audience. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.FirstAndThirdPartyAudience]) {
			val scopes = Seq("""https://www.googleapis.com/auth/display-video""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.FirstAndThirdPartyAudience])
		}
		object get {
			def apply(firstAndThirdPartyAudiencesId :PlayApi, firstAndThirdPartyAudienceId: String, partnerId: String, advertiserId: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v3/firstAndThirdPartyAudiences/${firstAndThirdPartyAudiencesId}").addQueryStringParameters("firstAndThirdPartyAudienceId" -> firstAndThirdPartyAudienceId.toString, "partnerId" -> partnerId.toString, "advertiserId" -> advertiserId.toString))
			given Conversion[get, Future[Schema.FirstAndThirdPartyAudience]] = (fun: get) => fun.apply()
		}
		/** Updates an existing FirstAndThirdPartyAudience. Only supported for the following audience_type: &#42; `CUSTOMER_MATCH_CONTACT_INFO` &#42; `CUSTOMER_MATCH_DEVICE_ID` */
		class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/display-video""")
			/** Perform the request */
			def withFirstAndThirdPartyAudience(body: Schema.FirstAndThirdPartyAudience) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.FirstAndThirdPartyAudience])
		}
		object patch {
			def apply(firstAndThirdPartyAudiencesId :PlayApi, firstAndThirdPartyAudienceId: String, updateMask: String, advertiserId: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v3/firstAndThirdPartyAudiences/${firstAndThirdPartyAudiencesId}").addQueryStringParameters("firstAndThirdPartyAudienceId" -> firstAndThirdPartyAudienceId.toString, "updateMask" -> updateMask.toString, "advertiserId" -> advertiserId.toString))
		}
		/** Lists first and third party audiences. The order is defined by the order_by parameter. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListFirstAndThirdPartyAudiencesResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/display-video""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListFirstAndThirdPartyAudiencesResponse])
		}
		object list {
			def apply(partnerId: String, advertiserId: String, pageSize: Int, pageToken: String, orderBy: String, filter: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v3/firstAndThirdPartyAudiences").addQueryStringParameters("partnerId" -> partnerId.toString, "advertiserId" -> advertiserId.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "orderBy" -> orderBy.toString, "filter" -> filter.toString))
			given Conversion[list, Future[Schema.ListFirstAndThirdPartyAudiencesResponse]] = (fun: list) => fun.apply()
		}
		/** Updates the member list of a Customer Match audience. Only supported for the following audience_type: &#42; `CUSTOMER_MATCH_CONTACT_INFO` &#42; `CUSTOMER_MATCH_DEVICE_ID` */
		class editCustomerMatchMembers(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/display-video""")
			/** Perform the request */
			def withEditCustomerMatchMembersRequest(body: Schema.EditCustomerMatchMembersRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.EditCustomerMatchMembersResponse])
		}
		object editCustomerMatchMembers {
			def apply(firstAndThirdPartyAudiencesId :PlayApi, firstAndThirdPartyAudienceId: String)(using signer: RequestSigner, ec: ExecutionContext): editCustomerMatchMembers = new editCustomerMatchMembers(ws.url(BASE_URL + s"v3/firstAndThirdPartyAudiences/${firstAndThirdPartyAudiencesId}:editCustomerMatchMembers").addQueryStringParameters("firstAndThirdPartyAudienceId" -> firstAndThirdPartyAudienceId.toString))
		}
	}
	object floodlightGroups {
		/** Gets a Floodlight group. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.FloodlightGroup]) {
			val scopes = Seq("""https://www.googleapis.com/auth/display-video""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.FloodlightGroup])
		}
		object get {
			def apply(floodlightGroupsId :PlayApi, floodlightGroupId: String, partnerId: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v3/floodlightGroups/${floodlightGroupsId}").addQueryStringParameters("floodlightGroupId" -> floodlightGroupId.toString, "partnerId" -> partnerId.toString))
			given Conversion[get, Future[Schema.FloodlightGroup]] = (fun: get) => fun.apply()
		}
		/** Updates an existing Floodlight group. Returns the updated Floodlight group if successful. */
		class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/display-video""")
			/** Perform the request */
			def withFloodlightGroup(body: Schema.FloodlightGroup) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.FloodlightGroup])
		}
		object patch {
			def apply(floodlightGroupId: String, partnerId: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v3/floodlightGroups/${floodlightGroupId}").addQueryStringParameters("partnerId" -> partnerId.toString, "updateMask" -> updateMask.toString))
		}
		object floodlightActivities {
			/** Gets a Floodlight activity. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.FloodlightActivity]) {
				val scopes = Seq("""https://www.googleapis.com/auth/display-video""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.FloodlightActivity])
			}
			object get {
				def apply(floodlightGroupsId :PlayApi, floodlightActivitiesId :PlayApi, floodlightGroupId: String, floodlightActivityId: String, partnerId: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v3/floodlightGroups/${floodlightGroupsId}/floodlightActivities/${floodlightActivitiesId}").addQueryStringParameters("floodlightGroupId" -> floodlightGroupId.toString, "floodlightActivityId" -> floodlightActivityId.toString, "partnerId" -> partnerId.toString))
				given Conversion[get, Future[Schema.FloodlightActivity]] = (fun: get) => fun.apply()
			}
			/** Lists Floodlight activities in a Floodlight group. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListFloodlightActivitiesResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/display-video""")
				/** Optional. Requested page size. Must be between `1` and `100`. If unspecified will default to `100`. Returns error code `INVALID_ARGUMENT` if an invalid value is specified.<br>Format: int32 */
				def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
				/** Optional. A token identifying a page of results the server should return. Typically, this is the value of next_page_token returned from the previous call to `ListFloodlightActivities` method. If not specified, the first page of results will be returned. */
				def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
				/** Optional. Field by which to sort the list. Acceptable values are: &#42; `displayName` (default) &#42; `floodlightActivityId` The default sorting order is ascending. To specify descending order for a field, a suffix "desc" should be added to the field name. Example: `displayName desc`. */
				def withOrderBy(orderBy: String) = new list(req.addQueryStringParameters("orderBy" -> orderBy.toString))
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListFloodlightActivitiesResponse])
			}
			object list {
				def apply(floodlightGroupsId :PlayApi, floodlightGroupId: String, partnerId: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v3/floodlightGroups/${floodlightGroupsId}/floodlightActivities").addQueryStringParameters("floodlightGroupId" -> floodlightGroupId.toString, "partnerId" -> partnerId.toString))
				given Conversion[list, Future[Schema.ListFloodlightActivitiesResponse]] = (fun: list) => fun.apply()
			}
		}
	}
	object googleAudiences {
		/** Gets a Google audience. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleAudience]) {
			val scopes = Seq("""https://www.googleapis.com/auth/display-video""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleAudience])
		}
		object get {
			def apply(googleAudiencesId :PlayApi, googleAudienceId: String, partnerId: String, advertiserId: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v3/googleAudiences/${googleAudiencesId}").addQueryStringParameters("googleAudienceId" -> googleAudienceId.toString, "partnerId" -> partnerId.toString, "advertiserId" -> advertiserId.toString))
			given Conversion[get, Future[Schema.GoogleAudience]] = (fun: get) => fun.apply()
		}
		/** Lists Google audiences. The order is defined by the order_by parameter. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListGoogleAudiencesResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/display-video""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListGoogleAudiencesResponse])
		}
		object list {
			def apply(partnerId: String, advertiserId: String, pageSize: Int, pageToken: String, orderBy: String, filter: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v3/googleAudiences").addQueryStringParameters("partnerId" -> partnerId.toString, "advertiserId" -> advertiserId.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "orderBy" -> orderBy.toString, "filter" -> filter.toString))
			given Conversion[list, Future[Schema.ListGoogleAudiencesResponse]] = (fun: list) => fun.apply()
		}
	}
	object guaranteedOrders {
		/** Creates a new guaranteed order. Returns the newly created guaranteed order if successful. */
		class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/display-video""")
			/** Perform the request */
			def withGuaranteedOrder(body: Schema.GuaranteedOrder) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GuaranteedOrder])
		}
		object create {
			def apply(partnerId: String, advertiserId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v3/guaranteedOrders").addQueryStringParameters("partnerId" -> partnerId.toString, "advertiserId" -> advertiserId.toString))
		}
		/** Edits read advertisers of a guaranteed order. */
		class editGuaranteedOrderReadAccessors(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/display-video""")
			/** Perform the request */
			def withEditGuaranteedOrderReadAccessorsRequest(body: Schema.EditGuaranteedOrderReadAccessorsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.EditGuaranteedOrderReadAccessorsResponse])
		}
		object editGuaranteedOrderReadAccessors {
			def apply(guaranteedOrdersId :PlayApi, guaranteedOrderId: String)(using signer: RequestSigner, ec: ExecutionContext): editGuaranteedOrderReadAccessors = new editGuaranteedOrderReadAccessors(ws.url(BASE_URL + s"v3/guaranteedOrders/${guaranteedOrdersId}:editGuaranteedOrderReadAccessors").addQueryStringParameters("guaranteedOrderId" -> guaranteedOrderId.toString))
		}
		/** Gets a guaranteed order. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GuaranteedOrder]) {
			val scopes = Seq("""https://www.googleapis.com/auth/display-video""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GuaranteedOrder])
		}
		object get {
			def apply(guaranteedOrdersId :PlayApi, guaranteedOrderId: String, partnerId: String, advertiserId: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v3/guaranteedOrders/${guaranteedOrdersId}").addQueryStringParameters("guaranteedOrderId" -> guaranteedOrderId.toString, "partnerId" -> partnerId.toString, "advertiserId" -> advertiserId.toString))
			given Conversion[get, Future[Schema.GuaranteedOrder]] = (fun: get) => fun.apply()
		}
		/** Updates an existing guaranteed order. Returns the updated guaranteed order if successful. */
		class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/display-video""")
			/** Perform the request */
			def withGuaranteedOrder(body: Schema.GuaranteedOrder) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GuaranteedOrder])
		}
		object patch {
			def apply(guaranteedOrdersId :PlayApi, guaranteedOrderId: String, updateMask: String, partnerId: String, advertiserId: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v3/guaranteedOrders/${guaranteedOrdersId}").addQueryStringParameters("guaranteedOrderId" -> guaranteedOrderId.toString, "updateMask" -> updateMask.toString, "partnerId" -> partnerId.toString, "advertiserId" -> advertiserId.toString))
		}
		/** Lists guaranteed orders that are accessible to the current user. The order is defined by the order_by parameter. If a filter by entity_status is not specified, guaranteed orders with entity status `ENTITY_STATUS_ARCHIVED` will not be included in the results. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListGuaranteedOrdersResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/display-video""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListGuaranteedOrdersResponse])
		}
		object list {
			def apply(partnerId: String, advertiserId: String, pageSize: Int, pageToken: String, orderBy: String, filter: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v3/guaranteedOrders").addQueryStringParameters("partnerId" -> partnerId.toString, "advertiserId" -> advertiserId.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "orderBy" -> orderBy.toString, "filter" -> filter.toString))
			given Conversion[list, Future[Schema.ListGuaranteedOrdersResponse]] = (fun: list) => fun.apply()
		}
	}
	object inventorySourceGroups {
		/** Creates a new inventory source group. Returns the newly created inventory source group if successful. */
		class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/display-video""")
			/** Perform the request */
			def withInventorySourceGroup(body: Schema.InventorySourceGroup) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.InventorySourceGroup])
		}
		object create {
			def apply(partnerId: String, advertiserId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v3/inventorySourceGroups").addQueryStringParameters("partnerId" -> partnerId.toString, "advertiserId" -> advertiserId.toString))
		}
		/** Deletes an inventory source group. */
		class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
			val scopes = Seq("""https://www.googleapis.com/auth/display-video""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
		}
		object delete {
			def apply(inventorySourceGroupsId :PlayApi, inventorySourceGroupId: String, partnerId: String, advertiserId: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v3/inventorySourceGroups/${inventorySourceGroupsId}").addQueryStringParameters("inventorySourceGroupId" -> inventorySourceGroupId.toString, "partnerId" -> partnerId.toString, "advertiserId" -> advertiserId.toString))
			given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
		}
		/** Gets an inventory source group. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.InventorySourceGroup]) {
			val scopes = Seq("""https://www.googleapis.com/auth/display-video""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.InventorySourceGroup])
		}
		object get {
			def apply(inventorySourceGroupsId :PlayApi, inventorySourceGroupId: String, partnerId: String, advertiserId: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v3/inventorySourceGroups/${inventorySourceGroupsId}").addQueryStringParameters("inventorySourceGroupId" -> inventorySourceGroupId.toString, "partnerId" -> partnerId.toString, "advertiserId" -> advertiserId.toString))
			given Conversion[get, Future[Schema.InventorySourceGroup]] = (fun: get) => fun.apply()
		}
		/** Updates an inventory source group. Returns the updated inventory source group if successful. */
		class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/display-video""")
			/** Perform the request */
			def withInventorySourceGroup(body: Schema.InventorySourceGroup) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.InventorySourceGroup])
		}
		object patch {
			def apply(inventorySourceGroupId: String, partnerId: String, advertiserId: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v3/inventorySourceGroups/${inventorySourceGroupId}").addQueryStringParameters("partnerId" -> partnerId.toString, "advertiserId" -> advertiserId.toString, "updateMask" -> updateMask.toString))
		}
		/** Lists inventory source groups that are accessible to the current user. The order is defined by the order_by parameter. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListInventorySourceGroupsResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/display-video""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListInventorySourceGroupsResponse])
		}
		object list {
			def apply(partnerId: String, advertiserId: String, pageSize: Int, pageToken: String, orderBy: String, filter: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v3/inventorySourceGroups").addQueryStringParameters("partnerId" -> partnerId.toString, "advertiserId" -> advertiserId.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "orderBy" -> orderBy.toString, "filter" -> filter.toString))
			given Conversion[list, Future[Schema.ListInventorySourceGroupsResponse]] = (fun: list) => fun.apply()
		}
		object assignedInventorySources {
			/** Lists inventory sources assigned to an inventory source group. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListAssignedInventorySourcesResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/display-video""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListAssignedInventorySourcesResponse])
			}
			object list {
				def apply(inventorySourceGroupsId :PlayApi, inventorySourceGroupId: String, partnerId: String, advertiserId: String, pageSize: Int, pageToken: String, orderBy: String, filter: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v3/inventorySourceGroups/${inventorySourceGroupsId}/assignedInventorySources").addQueryStringParameters("inventorySourceGroupId" -> inventorySourceGroupId.toString, "partnerId" -> partnerId.toString, "advertiserId" -> advertiserId.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "orderBy" -> orderBy.toString, "filter" -> filter.toString))
				given Conversion[list, Future[Schema.ListAssignedInventorySourcesResponse]] = (fun: list) => fun.apply()
			}
			/** Creates an assignment between an inventory source and an inventory source group. */
			class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/display-video""")
				/** Perform the request */
				def withAssignedInventorySource(body: Schema.AssignedInventorySource) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.AssignedInventorySource])
			}
			object create {
				def apply(inventorySourceGroupsId :PlayApi, inventorySourceGroupId: String, partnerId: String, advertiserId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v3/inventorySourceGroups/${inventorySourceGroupsId}/assignedInventorySources").addQueryStringParameters("inventorySourceGroupId" -> inventorySourceGroupId.toString, "partnerId" -> partnerId.toString, "advertiserId" -> advertiserId.toString))
			}
			/** Deletes the assignment between an inventory source and an inventory source group. */
			class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
				val scopes = Seq("""https://www.googleapis.com/auth/display-video""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
			}
			object delete {
				def apply(inventorySourceGroupsId :PlayApi, assignedInventorySourcesId :PlayApi, inventorySourceGroupId: String, assignedInventorySourceId: String, partnerId: String, advertiserId: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v3/inventorySourceGroups/${inventorySourceGroupsId}/assignedInventorySources/${assignedInventorySourcesId}").addQueryStringParameters("inventorySourceGroupId" -> inventorySourceGroupId.toString, "assignedInventorySourceId" -> assignedInventorySourceId.toString, "partnerId" -> partnerId.toString, "advertiserId" -> advertiserId.toString))
				given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
			}
			/** Bulk edits multiple assignments between inventory sources and a single inventory source group. The operation will delete the assigned inventory sources provided in BulkEditAssignedInventorySourcesRequest.deleted_assigned_inventory_sources and then create the assigned inventory sources provided in BulkEditAssignedInventorySourcesRequest.created_assigned_inventory_sources. */
			class bulkEdit(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/display-video""")
				/** Perform the request */
				def withBulkEditAssignedInventorySourcesRequest(body: Schema.BulkEditAssignedInventorySourcesRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.BulkEditAssignedInventorySourcesResponse])
			}
			object bulkEdit {
				def apply(inventorySourceGroupsId :PlayApi, inventorySourceGroupId: String)(using signer: RequestSigner, ec: ExecutionContext): bulkEdit = new bulkEdit(ws.url(BASE_URL + s"v3/inventorySourceGroups/${inventorySourceGroupsId}/assignedInventorySources:bulkEdit").addQueryStringParameters("inventorySourceGroupId" -> inventorySourceGroupId.toString))
			}
		}
	}
	object inventorySources {
		/** Creates a new inventory source. Returns the newly created inventory source if successful. */
		class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/display-video""")
			/** Perform the request */
			def withInventorySource(body: Schema.InventorySource) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.InventorySource])
		}
		object create {
			def apply(partnerId: String, advertiserId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v3/inventorySources").addQueryStringParameters("partnerId" -> partnerId.toString, "advertiserId" -> advertiserId.toString))
		}
		/** Edits read/write accessors of an inventory source. Returns the updated read_write_accessors for the inventory source. */
		class editInventorySourceReadWriteAccessors(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/display-video""")
			/** Perform the request */
			def withEditInventorySourceReadWriteAccessorsRequest(body: Schema.EditInventorySourceReadWriteAccessorsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.InventorySourceAccessors])
		}
		object editInventorySourceReadWriteAccessors {
			def apply(inventorySourcesId :PlayApi, inventorySourceId: String)(using signer: RequestSigner, ec: ExecutionContext): editInventorySourceReadWriteAccessors = new editInventorySourceReadWriteAccessors(ws.url(BASE_URL + s"v3/inventorySources/${inventorySourcesId}:editInventorySourceReadWriteAccessors").addQueryStringParameters("inventorySourceId" -> inventorySourceId.toString))
		}
		/** Gets an inventory source. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.InventorySource]) {
			val scopes = Seq("""https://www.googleapis.com/auth/display-video""")
			/** Optional. The ID of the DV360 advertiser to which the fetched inventory source is permissioned. If the user only has access to the advertiser and not the parent partner, use this field to specify the relevant advertiser.<br>Format: int64 */
			def withAdvertiserId(advertiserId: String) = new get(req.addQueryStringParameters("advertiserId" -> advertiserId.toString))
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.InventorySource])
		}
		object get {
			def apply(inventorySourcesId :PlayApi, inventorySourceId: String, partnerId: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v3/inventorySources/${inventorySourcesId}").addQueryStringParameters("inventorySourceId" -> inventorySourceId.toString, "partnerId" -> partnerId.toString))
			given Conversion[get, Future[Schema.InventorySource]] = (fun: get) => fun.apply()
		}
		/** Updates an existing inventory source. Returns the updated inventory source if successful. */
		class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/display-video""")
			/** Perform the request */
			def withInventorySource(body: Schema.InventorySource) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.InventorySource])
		}
		object patch {
			def apply(inventorySourcesId :PlayApi, inventorySourceId: String, updateMask: String, partnerId: String, advertiserId: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v3/inventorySources/${inventorySourcesId}").addQueryStringParameters("inventorySourceId" -> inventorySourceId.toString, "updateMask" -> updateMask.toString, "partnerId" -> partnerId.toString, "advertiserId" -> advertiserId.toString))
		}
		/** Lists inventory sources that are accessible to the current user. The order is defined by the order_by parameter. If a filter by entity_status is not specified, inventory sources with entity status `ENTITY_STATUS_ARCHIVED` will not be included in the results. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListInventorySourcesResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/display-video""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListInventorySourcesResponse])
		}
		object list {
			def apply(partnerId: String, advertiserId: String, pageSize: Int, pageToken: String, orderBy: String, filter: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v3/inventorySources").addQueryStringParameters("partnerId" -> partnerId.toString, "advertiserId" -> advertiserId.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "orderBy" -> orderBy.toString, "filter" -> filter.toString))
			given Conversion[list, Future[Schema.ListInventorySourcesResponse]] = (fun: list) => fun.apply()
		}
	}
	object partners {
		/** Gets a partner. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Partner]) {
			val scopes = Seq("""https://www.googleapis.com/auth/display-video""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Partner])
		}
		object get {
			def apply(partnersId :PlayApi, partnerId: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v3/partners/${partnersId}").addQueryStringParameters("partnerId" -> partnerId.toString))
			given Conversion[get, Future[Schema.Partner]] = (fun: get) => fun.apply()
		}
		/** Edits targeting options under a single partner. The operation will delete the assigned targeting options provided in BulkEditPartnerAssignedTargetingOptionsRequest.deleteRequests and then create the assigned targeting options provided in BulkEditPartnerAssignedTargetingOptionsRequest.createRequests . */
		class editAssignedTargetingOptions(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/display-video""")
			/** Perform the request */
			def withBulkEditPartnerAssignedTargetingOptionsRequest(body: Schema.BulkEditPartnerAssignedTargetingOptionsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.BulkEditPartnerAssignedTargetingOptionsResponse])
		}
		object editAssignedTargetingOptions {
			def apply(partnersId :PlayApi, partnerId: String)(using signer: RequestSigner, ec: ExecutionContext): editAssignedTargetingOptions = new editAssignedTargetingOptions(ws.url(BASE_URL + s"v3/partners/${partnersId}:editAssignedTargetingOptions").addQueryStringParameters("partnerId" -> partnerId.toString))
		}
		/** Lists partners that are accessible to the current user. The order is defined by the order_by parameter. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListPartnersResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/display-video""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListPartnersResponse])
		}
		object list {
			def apply(pageSize: Int, pageToken: String, orderBy: String, filter: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v3/partners").addQueryStringParameters("pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "orderBy" -> orderBy.toString, "filter" -> filter.toString))
			given Conversion[list, Future[Schema.ListPartnersResponse]] = (fun: list) => fun.apply()
		}
		object channels {
			/** Creates a new channel. Returns the newly created channel if successful. */
			class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/display-video""")
				/** Perform the request */
				def withChannel(body: Schema.Channel) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Channel])
			}
			object create {
				def apply(partnersId :PlayApi, partnerId: String, advertiserId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v3/partners/${partnersId}/channels").addQueryStringParameters("partnerId" -> partnerId.toString, "advertiserId" -> advertiserId.toString))
			}
			/** Gets a channel for a partner or advertiser. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Channel]) {
				val scopes = Seq("""https://www.googleapis.com/auth/display-video""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Channel])
			}
			object get {
				def apply(partnersId :PlayApi, channelsId :PlayApi, partnerId: String, channelId: String, advertiserId: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v3/partners/${partnersId}/channels/${channelsId}").addQueryStringParameters("partnerId" -> partnerId.toString, "channelId" -> channelId.toString, "advertiserId" -> advertiserId.toString))
				given Conversion[get, Future[Schema.Channel]] = (fun: get) => fun.apply()
			}
			/** Updates a channel. Returns the updated channel if successful. */
			class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/display-video""")
				/** Perform the request */
				def withChannel(body: Schema.Channel) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Channel])
			}
			object patch {
				def apply(partnersId :PlayApi, partnerId: String, channelId: String, advertiserId: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v3/partners/${partnersId}/channels/${channelId}").addQueryStringParameters("partnerId" -> partnerId.toString, "advertiserId" -> advertiserId.toString, "updateMask" -> updateMask.toString))
			}
			/** Lists channels for a partner or advertiser. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListChannelsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/display-video""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListChannelsResponse])
			}
			object list {
				def apply(partnersId :PlayApi, partnerId: String, advertiserId: String, pageSize: Int, pageToken: String, orderBy: String, filter: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v3/partners/${partnersId}/channels").addQueryStringParameters("partnerId" -> partnerId.toString, "advertiserId" -> advertiserId.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "orderBy" -> orderBy.toString, "filter" -> filter.toString))
				given Conversion[list, Future[Schema.ListChannelsResponse]] = (fun: list) => fun.apply()
			}
			object sites {
				/** Replaces all of the sites under a single channel. The operation will replace the sites under a channel with the sites provided in ReplaceSitesRequest.new_sites. &#42;&#42;This method regularly experiences high latency.&#42;&#42; We recommend [increasing your default timeout](/display-video/api/guides/best-practices/timeouts#client_library_timeout) to avoid errors. */
				class replace(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/display-video""")
					/** Perform the request */
					def withReplaceSitesRequest(body: Schema.ReplaceSitesRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.ReplaceSitesResponse])
				}
				object replace {
					def apply(channelsId :PlayApi, partnerId: String, channelId: String)(using signer: RequestSigner, ec: ExecutionContext): replace = new replace(ws.url(BASE_URL + s"v3/partners/${partnerId}/channels/${channelsId}/sites:replace").addQueryStringParameters("channelId" -> channelId.toString))
				}
				/** Creates a site in a channel. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/display-video""")
					/** Perform the request */
					def withSite(body: Schema.Site) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Site])
				}
				object create {
					def apply(channelsId :PlayApi, partnerId: String, channelId: String, advertiserId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v3/partners/${partnerId}/channels/${channelsId}/sites").addQueryStringParameters("channelId" -> channelId.toString, "advertiserId" -> advertiserId.toString))
				}
				/** Bulk edits sites under a single channel. The operation will delete the sites provided in BulkEditSitesRequest.deleted_sites and then create the sites provided in BulkEditSitesRequest.created_sites. */
				class bulkEdit(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/display-video""")
					/** Perform the request */
					def withBulkEditSitesRequest(body: Schema.BulkEditSitesRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.BulkEditSitesResponse])
				}
				object bulkEdit {
					def apply(channelsId :PlayApi, partnerId: String, channelId: String)(using signer: RequestSigner, ec: ExecutionContext): bulkEdit = new bulkEdit(ws.url(BASE_URL + s"v3/partners/${partnerId}/channels/${channelsId}/sites:bulkEdit").addQueryStringParameters("channelId" -> channelId.toString))
				}
				/** Deletes a site from a channel. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					val scopes = Seq("""https://www.googleapis.com/auth/display-video""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
				}
				object delete {
					def apply(channelsId :PlayApi, sitesId :PlayApi, partnerId: String, channelId: String, urlOrAppId: String, advertiserId: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v3/partners/${partnerId}/channels/${channelsId}/sites/${sitesId}").addQueryStringParameters("channelId" -> channelId.toString, "urlOrAppId" -> urlOrAppId.toString, "advertiserId" -> advertiserId.toString))
					given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
				}
				/** Lists sites in a channel. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListSitesResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/display-video""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListSitesResponse])
				}
				object list {
					def apply(partnersId :PlayApi, channelsId :PlayApi, partnerId: String, channelId: String, advertiserId: String, pageSize: Int, pageToken: String, orderBy: String, filter: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v3/partners/${partnersId}/channels/${channelsId}/sites").addQueryStringParameters("partnerId" -> partnerId.toString, "channelId" -> channelId.toString, "advertiserId" -> advertiserId.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "orderBy" -> orderBy.toString, "filter" -> filter.toString))
					given Conversion[list, Future[Schema.ListSitesResponse]] = (fun: list) => fun.apply()
				}
			}
		}
		object targetingTypes {
			object assignedTargetingOptions {
				/** Gets a single targeting option assigned to a partner. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.AssignedTargetingOption]) {
					val scopes = Seq("""https://www.googleapis.com/auth/display-video""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.AssignedTargetingOption])
				}
				object get {
					def apply(partnersId :PlayApi, targetingTypesId :PlayApi, assignedTargetingOptionsId :PlayApi, partnerId: String, targetingType: String, assignedTargetingOptionId: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v3/partners/${partnersId}/targetingTypes/${targetingTypesId}/assignedTargetingOptions/${assignedTargetingOptionsId}").addQueryStringParameters("partnerId" -> partnerId.toString, "targetingType" -> targetingType.toString, "assignedTargetingOptionId" -> assignedTargetingOptionId.toString))
					given Conversion[get, Future[Schema.AssignedTargetingOption]] = (fun: get) => fun.apply()
				}
				/** Lists the targeting options assigned to a partner. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListPartnerAssignedTargetingOptionsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/display-video""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListPartnerAssignedTargetingOptionsResponse])
				}
				object list {
					def apply(partnersId :PlayApi, targetingTypesId :PlayApi, partnerId: String, targetingType: String, pageSize: Int, pageToken: String, orderBy: String, filter: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v3/partners/${partnersId}/targetingTypes/${targetingTypesId}/assignedTargetingOptions").addQueryStringParameters("partnerId" -> partnerId.toString, "targetingType" -> targetingType.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "orderBy" -> orderBy.toString, "filter" -> filter.toString))
					given Conversion[list, Future[Schema.ListPartnerAssignedTargetingOptionsResponse]] = (fun: list) => fun.apply()
				}
				/** Assigns a targeting option to a partner. Returns the assigned targeting option if successful. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/display-video""")
					/** Perform the request */
					def withAssignedTargetingOption(body: Schema.AssignedTargetingOption) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.AssignedTargetingOption])
				}
				object create {
					def apply(partnersId :PlayApi, targetingTypesId :PlayApi, partnerId: String, targetingType: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v3/partners/${partnersId}/targetingTypes/${targetingTypesId}/assignedTargetingOptions").addQueryStringParameters("partnerId" -> partnerId.toString, "targetingType" -> targetingType.toString))
				}
				/** Deletes an assigned targeting option from a partner. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					val scopes = Seq("""https://www.googleapis.com/auth/display-video""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
				}
				object delete {
					def apply(partnersId :PlayApi, targetingTypesId :PlayApi, assignedTargetingOptionsId :PlayApi, partnerId: String, targetingType: String, assignedTargetingOptionId: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v3/partners/${partnersId}/targetingTypes/${targetingTypesId}/assignedTargetingOptions/${assignedTargetingOptionsId}").addQueryStringParameters("partnerId" -> partnerId.toString, "targetingType" -> targetingType.toString, "assignedTargetingOptionId" -> assignedTargetingOptionId.toString))
					given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
				}
			}
		}
	}
	object sdfdownloadtasks {
		/** Creates an SDF Download Task. Returns an Operation. An SDF Download Task is a long-running, asynchronous operation. The metadata type of this operation is SdfDownloadTaskMetadata. If the request is successful, the response type of the operation is SdfDownloadTask. The response will not include the download files, which must be retrieved with media.download. The state of operation can be retrieved with sdfdownloadtask.operations.get. Any errors can be found in the error.message. Note that error.details is expected to be empty. */
		class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/display-video""")
			/** Perform the request */
			def withCreateSdfDownloadTaskRequest(body: Schema.CreateSdfDownloadTaskRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
		}
		object create {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v3/sdfdownloadtasks").addQueryStringParameters())
		}
		object operations {
			/** Gets the latest state of an asynchronous SDF download task operation. Clients should poll this method at intervals of 30 seconds. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
				val scopes = Seq("""https://www.googleapis.com/auth/display-video""", """https://www.googleapis.com/auth/doubleclickbidmanager""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Operation])
			}
			object get {
				def apply(operationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v3/sdfdownloadtasks/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.Operation]] = (fun: get) => fun.apply()
			}
		}
	}
	object targetingTypes {
		object targetingOptions {
			/** Gets a single targeting option. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.TargetingOption]) {
				val scopes = Seq("""https://www.googleapis.com/auth/display-video""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.TargetingOption])
			}
			object get {
				def apply(targetingTypesId :PlayApi, targetingOptionsId :PlayApi, targetingType: String, targetingOptionId: String, advertiserId: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v3/targetingTypes/${targetingTypesId}/targetingOptions/${targetingOptionsId}").addQueryStringParameters("targetingType" -> targetingType.toString, "targetingOptionId" -> targetingOptionId.toString, "advertiserId" -> advertiserId.toString))
				given Conversion[get, Future[Schema.TargetingOption]] = (fun: get) => fun.apply()
			}
			/** Lists targeting options of a given type. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListTargetingOptionsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/display-video""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListTargetingOptionsResponse])
			}
			object list {
				def apply(targetingTypesId :PlayApi, targetingType: String, advertiserId: String, pageSize: Int, pageToken: String, orderBy: String, filter: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v3/targetingTypes/${targetingTypesId}/targetingOptions").addQueryStringParameters("targetingType" -> targetingType.toString, "advertiserId" -> advertiserId.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "orderBy" -> orderBy.toString, "filter" -> filter.toString))
				given Conversion[list, Future[Schema.ListTargetingOptionsResponse]] = (fun: list) => fun.apply()
			}
			/** Searches for targeting options of a given type based on the given search terms. */
			class search(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/display-video""")
				/** Perform the request */
				def withSearchTargetingOptionsRequest(body: Schema.SearchTargetingOptionsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.SearchTargetingOptionsResponse])
			}
			object search {
				def apply(targetingTypesId :PlayApi, targetingType: String)(using signer: RequestSigner, ec: ExecutionContext): search = new search(ws.url(BASE_URL + s"v3/targetingTypes/${targetingTypesId}/targetingOptions:search").addQueryStringParameters("targetingType" -> targetingType.toString))
			}
		}
	}
	object users {
		/** Deletes a user. This method has unique authentication requirements. Read the prerequisites in our [Managing Users guide](/display-video/api/guides/users/overview#prerequisites) before using this method. The "Try this method" feature does not work for this method. */
		class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
			val scopes = Seq("""https://www.googleapis.com/auth/display-video-user-management""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
		}
		object delete {
			def apply(usersId :PlayApi, userId: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v3/users/${usersId}").addQueryStringParameters("userId" -> userId.toString))
			given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
		}
		/** Gets a user. This method has unique authentication requirements. Read the prerequisites in our [Managing Users guide](/display-video/api/guides/users/overview#prerequisites) before using this method. The "Try this method" feature does not work for this method. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.User]) {
			val scopes = Seq("""https://www.googleapis.com/auth/display-video-user-management""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.User])
		}
		object get {
			def apply(usersId :PlayApi, userId: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v3/users/${usersId}").addQueryStringParameters("userId" -> userId.toString))
			given Conversion[get, Future[Schema.User]] = (fun: get) => fun.apply()
		}
		/** Updates an existing user. Returns the updated user if successful. This method has unique authentication requirements. Read the prerequisites in our [Managing Users guide](/display-video/api/guides/users/overview#prerequisites) before using this method. The "Try this method" feature does not work for this method. */
		class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/display-video-user-management""")
			/** Perform the request */
			def withUser(body: Schema.User) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.User])
		}
		object patch {
			def apply(usersId :PlayApi, userId: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v3/users/${usersId}").addQueryStringParameters("userId" -> userId.toString, "updateMask" -> updateMask.toString))
		}
		/** Lists users that are accessible to the current user. If two users have user roles on the same partner or advertiser, they can access each other. This method has unique authentication requirements. Read the prerequisites in our [Managing Users guide](/display-video/api/guides/users/overview#prerequisites) before using this method. The "Try this method" feature does not work for this method. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListUsersResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/display-video-user-management""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListUsersResponse])
		}
		object list {
			def apply(pageSize: Int, pageToken: String, orderBy: String, filter: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v3/users").addQueryStringParameters("pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "orderBy" -> orderBy.toString, "filter" -> filter.toString))
			given Conversion[list, Future[Schema.ListUsersResponse]] = (fun: list) => fun.apply()
		}
		/** Bulk edits user roles for a user. The operation will delete the assigned user roles provided in BulkEditAssignedUserRolesRequest.deletedAssignedUserRoles and then assign the user roles provided in BulkEditAssignedUserRolesRequest.createdAssignedUserRoles. This method has unique authentication requirements. Read the prerequisites in our [Managing Users guide](/display-video/api/guides/users/overview#prerequisites) before using this method. The "Try this method" feature does not work for this method. */
		class bulkEditAssignedUserRoles(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/display-video-user-management""")
			/** Perform the request */
			def withBulkEditAssignedUserRolesRequest(body: Schema.BulkEditAssignedUserRolesRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.BulkEditAssignedUserRolesResponse])
		}
		object bulkEditAssignedUserRoles {
			def apply(usersId :PlayApi, userId: String)(using signer: RequestSigner, ec: ExecutionContext): bulkEditAssignedUserRoles = new bulkEditAssignedUserRoles(ws.url(BASE_URL + s"v3/users/${usersId}:bulkEditAssignedUserRoles").addQueryStringParameters("userId" -> userId.toString))
		}
		/** Creates a new user. Returns the newly created user if successful. This method has unique authentication requirements. Read the prerequisites in our [Managing Users guide](/display-video/api/guides/users/overview#prerequisites) before using this method. The "Try this method" feature does not work for this method. */
		class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/display-video-user-management""")
			/** Perform the request */
			def withUser(body: Schema.User) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.User])
		}
		object create {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v3/users").addQueryStringParameters())
		}
	}
	object media {
		/** Uploads media. Upload is supported on the URI `/upload/media/{resource_name=&#42;&#42;}?upload_type=media.` &#42;&#42;Note&#42;&#42;: Upload requests will not be successful without including `upload_type=media` query string. */
		class upload(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/display-video""", """https://www.googleapis.com/auth/doubleclickbidmanager""")
			/** Perform the request */
			def withGoogleBytestreamMedia(body: Schema.GoogleBytestreamMedia) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleBytestreamMedia])
		}
		object upload {
			def apply(mediaId :PlayApi, resourceName: String)(using signer: RequestSigner, ec: ExecutionContext): upload = new upload(ws.url(BASE_URL + s"media/${mediaId}").addQueryStringParameters("resourceName" -> resourceName.toString))
		}
		/** Downloads media. Download is supported on the URI `/download/{resource_name=&#42;&#42;}?alt=media.` &#42;&#42;Note&#42;&#42;: Download requests will not be successful without including `alt=media` query string. */
		class download(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleBytestreamMedia]) {
			val scopes = Seq("""https://www.googleapis.com/auth/display-video""", """https://www.googleapis.com/auth/doubleclickbidmanager""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleBytestreamMedia])
		}
		object download {
			def apply(downloadId :PlayApi, resourceName: String)(using signer: RequestSigner, ec: ExecutionContext): download = new download(ws.url(BASE_URL + s"download/${downloadId}").addQueryStringParameters("resourceName" -> resourceName.toString))
			given Conversion[download, Future[Schema.GoogleBytestreamMedia]] = (fun: download) => fun.apply()
		}
	}
}
