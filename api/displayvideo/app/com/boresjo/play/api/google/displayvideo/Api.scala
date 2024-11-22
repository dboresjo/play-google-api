package com.boresjo.play.api.google.displayvideo

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://displayvideo.googleapis.com/"

	object advertisers {
		class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withAdvertiser(body: Schema.Advertiser) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Advertiser])
		}
		object create {
			def apply()(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v3/advertisers").addQueryStringParameters())
		}
		class listAssignedTargetingOptions(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.BulkListAdvertiserAssignedTargetingOptionsResponse]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.BulkListAdvertiserAssignedTargetingOptionsResponse])
		}
		object listAssignedTargetingOptions {
			def apply(advertisersId :PlayApi, advertiserId: String, pageSize: Int, pageToken: String, orderBy: String, filter: String)(using auth: AuthToken, ec: ExecutionContext): listAssignedTargetingOptions = new listAssignedTargetingOptions(ws.url(BASE_URL + s"v3/advertisers/${advertisersId}:listAssignedTargetingOptions").addQueryStringParameters("advertiserId" -> advertiserId.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "orderBy" -> orderBy.toString, "filter" -> filter.toString))
			given Conversion[listAssignedTargetingOptions, Future[Schema.BulkListAdvertiserAssignedTargetingOptionsResponse]] = (fun: listAssignedTargetingOptions) => fun.apply()
		}
		class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
			def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
		}
		object delete {
			def apply(advertisersId :PlayApi, advertiserId: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v3/advertisers/${advertisersId}").addQueryStringParameters("advertiserId" -> advertiserId.toString))
			given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
		}
		class audit(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.AuditAdvertiserResponse]) {
			/** Optional. The specific fields to return. If no mask is specified, all fields in the response proto will be filled. Valid values are: &#42; usedLineItemsCount &#42; usedInsertionOrdersCount &#42; usedCampaignsCount &#42; channelsCount &#42; negativelyTargetedChannelsCount &#42; negativeKeywordListsCount &#42; adGroupCriteriaCount &#42; campaignCriteriaCount<br>Format: google-fieldmask */
			def withReadMask(readMask: String) = new audit(req.addQueryStringParameters("readMask" -> readMask.toString))
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.AuditAdvertiserResponse])
		}
		object audit {
			def apply(advertisersId :PlayApi, advertiserId: String)(using auth: AuthToken, ec: ExecutionContext): audit = new audit(ws.url(BASE_URL + s"v3/advertisers/${advertisersId}:audit").addQueryStringParameters("advertiserId" -> advertiserId.toString))
			given Conversion[audit, Future[Schema.AuditAdvertiserResponse]] = (fun: audit) => fun.apply()
		}
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Advertiser]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Advertiser])
		}
		object get {
			def apply(advertisersId :PlayApi, advertiserId: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v3/advertisers/${advertisersId}").addQueryStringParameters("advertiserId" -> advertiserId.toString))
			given Conversion[get, Future[Schema.Advertiser]] = (fun: get) => fun.apply()
		}
		class editAssignedTargetingOptions(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withBulkEditAdvertiserAssignedTargetingOptionsRequest(body: Schema.BulkEditAdvertiserAssignedTargetingOptionsRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.BulkEditAdvertiserAssignedTargetingOptionsResponse])
		}
		object editAssignedTargetingOptions {
			def apply(advertisersId :PlayApi, advertiserId: String)(using auth: AuthToken, ec: ExecutionContext): editAssignedTargetingOptions = new editAssignedTargetingOptions(ws.url(BASE_URL + s"v3/advertisers/${advertisersId}:editAssignedTargetingOptions").addQueryStringParameters("advertiserId" -> advertiserId.toString))
		}
		class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withAdvertiser(body: Schema.Advertiser) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Advertiser])
		}
		object patch {
			def apply(advertisersId :PlayApi, advertiserId: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v3/advertisers/${advertisersId}").addQueryStringParameters("advertiserId" -> advertiserId.toString, "updateMask" -> updateMask.toString))
		}
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListAdvertisersResponse]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListAdvertisersResponse])
		}
		object list {
			def apply(partnerId: String, pageSize: Int, pageToken: String, orderBy: String, filter: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v3/advertisers").addQueryStringParameters("partnerId" -> partnerId.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "orderBy" -> orderBy.toString, "filter" -> filter.toString))
			given Conversion[list, Future[Schema.ListAdvertisersResponse]] = (fun: list) => fun.apply()
		}
		object assets {
			class upload(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withCreateAssetRequest(body: Schema.CreateAssetRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.CreateAssetResponse])
			}
			object upload {
				def apply(advertisersId :PlayApi, advertiserId: String)(using auth: AuthToken, ec: ExecutionContext): upload = new upload(ws.url(BASE_URL + s"v3/advertisers/${advertisersId}/assets").addQueryStringParameters("advertiserId" -> advertiserId.toString))
			}
		}
		object lineItems {
			class duplicate(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withDuplicateLineItemRequest(body: Schema.DuplicateLineItemRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.DuplicateLineItemResponse])
			}
			object duplicate {
				def apply(advertisersId :PlayApi, lineItemsId :PlayApi, advertiserId: String, lineItemId: String)(using auth: AuthToken, ec: ExecutionContext): duplicate = new duplicate(ws.url(BASE_URL + s"v3/advertisers/${advertisersId}/lineItems/${lineItemsId}:duplicate").addQueryStringParameters("advertiserId" -> advertiserId.toString, "lineItemId" -> lineItemId.toString))
			}
			class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withLineItem(body: Schema.LineItem) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.LineItem])
			}
			object create {
				def apply(advertisersId :PlayApi, advertiserId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v3/advertisers/${advertisersId}/lineItems").addQueryStringParameters("advertiserId" -> advertiserId.toString))
			}
			class bulkListAssignedTargetingOptions(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.BulkListAssignedTargetingOptionsResponse]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.BulkListAssignedTargetingOptionsResponse])
			}
			object bulkListAssignedTargetingOptions {
				def apply(advertisersId :PlayApi, advertiserId: String, lineItemIds: String, pageSize: Int, pageToken: String, orderBy: String, filter: String)(using auth: AuthToken, ec: ExecutionContext): bulkListAssignedTargetingOptions = new bulkListAssignedTargetingOptions(ws.url(BASE_URL + s"v3/advertisers/${advertisersId}/lineItems:bulkListAssignedTargetingOptions").addQueryStringParameters("advertiserId" -> advertiserId.toString, "lineItemIds" -> lineItemIds.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "orderBy" -> orderBy.toString, "filter" -> filter.toString))
				given Conversion[bulkListAssignedTargetingOptions, Future[Schema.BulkListAssignedTargetingOptionsResponse]] = (fun: bulkListAssignedTargetingOptions) => fun.apply()
			}
			class bulkUpdate(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withBulkUpdateLineItemsRequest(body: Schema.BulkUpdateLineItemsRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.BulkUpdateLineItemsResponse])
			}
			object bulkUpdate {
				def apply(advertisersId :PlayApi, advertiserId: String)(using auth: AuthToken, ec: ExecutionContext): bulkUpdate = new bulkUpdate(ws.url(BASE_URL + s"v3/advertisers/${advertisersId}/lineItems:bulkUpdate").addQueryStringParameters("advertiserId" -> advertiserId.toString))
			}
			class bulkEditAssignedTargetingOptions(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withBulkEditAssignedTargetingOptionsRequest(body: Schema.BulkEditAssignedTargetingOptionsRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.BulkEditAssignedTargetingOptionsResponse])
			}
			object bulkEditAssignedTargetingOptions {
				def apply(advertisersId :PlayApi, advertiserId: String)(using auth: AuthToken, ec: ExecutionContext): bulkEditAssignedTargetingOptions = new bulkEditAssignedTargetingOptions(ws.url(BASE_URL + s"v3/advertisers/${advertisersId}/lineItems:bulkEditAssignedTargetingOptions").addQueryStringParameters("advertiserId" -> advertiserId.toString))
			}
			class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
				def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
			}
			object delete {
				def apply(advertisersId :PlayApi, lineItemsId :PlayApi, advertiserId: String, lineItemId: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v3/advertisers/${advertisersId}/lineItems/${lineItemsId}").addQueryStringParameters("advertiserId" -> advertiserId.toString, "lineItemId" -> lineItemId.toString))
				given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
			}
			class generateDefault(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGenerateDefaultLineItemRequest(body: Schema.GenerateDefaultLineItemRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.LineItem])
			}
			object generateDefault {
				def apply(advertisersId :PlayApi, advertiserId: String)(using auth: AuthToken, ec: ExecutionContext): generateDefault = new generateDefault(ws.url(BASE_URL + s"v3/advertisers/${advertisersId}/lineItems:generateDefault").addQueryStringParameters("advertiserId" -> advertiserId.toString))
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.LineItem]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.LineItem])
			}
			object get {
				def apply(advertisersId :PlayApi, lineItemsId :PlayApi, advertiserId: String, lineItemId: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v3/advertisers/${advertisersId}/lineItems/${lineItemsId}").addQueryStringParameters("advertiserId" -> advertiserId.toString, "lineItemId" -> lineItemId.toString))
				given Conversion[get, Future[Schema.LineItem]] = (fun: get) => fun.apply()
			}
			class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withLineItem(body: Schema.LineItem) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.LineItem])
			}
			object patch {
				def apply(advertisersId :PlayApi, lineItemsId :PlayApi, advertiserId: String, lineItemId: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v3/advertisers/${advertisersId}/lineItems/${lineItemsId}").addQueryStringParameters("advertiserId" -> advertiserId.toString, "lineItemId" -> lineItemId.toString, "updateMask" -> updateMask.toString))
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListLineItemsResponse]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListLineItemsResponse])
			}
			object list {
				def apply(advertisersId :PlayApi, advertiserId: String, pageSize: Int, pageToken: String, orderBy: String, filter: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v3/advertisers/${advertisersId}/lineItems").addQueryStringParameters("advertiserId" -> advertiserId.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "orderBy" -> orderBy.toString, "filter" -> filter.toString))
				given Conversion[list, Future[Schema.ListLineItemsResponse]] = (fun: list) => fun.apply()
			}
			object targetingTypes {
				object assignedTargetingOptions {
					class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.AssignedTargetingOption]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.AssignedTargetingOption])
					}
					object get {
						def apply(advertisersId :PlayApi, lineItemsId :PlayApi, targetingTypesId :PlayApi, assignedTargetingOptionsId :PlayApi, advertiserId: String, lineItemId: String, targetingType: String, assignedTargetingOptionId: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v3/advertisers/${advertisersId}/lineItems/${lineItemsId}/targetingTypes/${targetingTypesId}/assignedTargetingOptions/${assignedTargetingOptionsId}").addQueryStringParameters("advertiserId" -> advertiserId.toString, "lineItemId" -> lineItemId.toString, "targetingType" -> targetingType.toString, "assignedTargetingOptionId" -> assignedTargetingOptionId.toString))
						given Conversion[get, Future[Schema.AssignedTargetingOption]] = (fun: get) => fun.apply()
					}
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListLineItemAssignedTargetingOptionsResponse]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListLineItemAssignedTargetingOptionsResponse])
					}
					object list {
						def apply(advertisersId :PlayApi, lineItemsId :PlayApi, targetingTypesId :PlayApi, advertiserId: String, lineItemId: String, targetingType: String, pageSize: Int, pageToken: String, orderBy: String, filter: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v3/advertisers/${advertisersId}/lineItems/${lineItemsId}/targetingTypes/${targetingTypesId}/assignedTargetingOptions").addQueryStringParameters("advertiserId" -> advertiserId.toString, "lineItemId" -> lineItemId.toString, "targetingType" -> targetingType.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "orderBy" -> orderBy.toString, "filter" -> filter.toString))
						given Conversion[list, Future[Schema.ListLineItemAssignedTargetingOptionsResponse]] = (fun: list) => fun.apply()
					}
					class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withAssignedTargetingOption(body: Schema.AssignedTargetingOption) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.AssignedTargetingOption])
					}
					object create {
						def apply(advertisersId :PlayApi, lineItemsId :PlayApi, targetingTypesId :PlayApi, advertiserId: String, lineItemId: String, targetingType: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v3/advertisers/${advertisersId}/lineItems/${lineItemsId}/targetingTypes/${targetingTypesId}/assignedTargetingOptions").addQueryStringParameters("advertiserId" -> advertiserId.toString, "lineItemId" -> lineItemId.toString, "targetingType" -> targetingType.toString))
					}
					class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
						def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
					}
					object delete {
						def apply(advertisersId :PlayApi, lineItemsId :PlayApi, targetingTypesId :PlayApi, assignedTargetingOptionsId :PlayApi, advertiserId: String, lineItemId: String, targetingType: String, assignedTargetingOptionId: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v3/advertisers/${advertisersId}/lineItems/${lineItemsId}/targetingTypes/${targetingTypesId}/assignedTargetingOptions/${assignedTargetingOptionsId}").addQueryStringParameters("advertiserId" -> advertiserId.toString, "lineItemId" -> lineItemId.toString, "targetingType" -> targetingType.toString, "assignedTargetingOptionId" -> assignedTargetingOptionId.toString))
						given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
					}
				}
			}
		}
		object adGroupAds {
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.AdGroupAd]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.AdGroupAd])
			}
			object get {
				def apply(advertisersId :PlayApi, adGroupAdsId :PlayApi, advertiserId: String, adGroupAdId: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v3/advertisers/${advertisersId}/adGroupAds/${adGroupAdsId}").addQueryStringParameters("advertiserId" -> advertiserId.toString, "adGroupAdId" -> adGroupAdId.toString))
				given Conversion[get, Future[Schema.AdGroupAd]] = (fun: get) => fun.apply()
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListAdGroupAdsResponse]) {
				/** Optional. Requested page size. Must be between `1` and `100`. If unspecified will default to `100`. Returns error code `INVALID_ARGUMENT` if an invalid value is specified.<br>Format: int32 */
				def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
				/** Optional. A token identifying a page of results the server should return. Typically, this is the value of next_page_token returned from the previous call to `ListAdGroupAds` method. If not specified, the first page of results will be returned. */
				def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
				/** Optional. Field by which to sort the list. Acceptable values are: &#42; `displayName` (default) &#42; `entityStatus` The default sorting order is ascending. To specify descending order for a field, a suffix "desc" should be added to the field name. Example: `displayName desc`. */
				def withOrderBy(orderBy: String) = new list(req.addQueryStringParameters("orderBy" -> orderBy.toString))
				/** Optional. Allows filtering by custom ad group ad fields. Supported syntax: &#42; Filter expressions are made up of one or more restrictions. &#42; Restrictions can be combined by `AND` and `OR`. A sequence of restrictions implicitly uses `AND`. &#42; A restriction has the form of `{field} {operator} {value}`. &#42; All fields must use the `EQUALS (=)` operator. Supported fields: &#42; `adGroupId` &#42; `displayName` &#42; `entityStatus` &#42; `adGroupAdId` Examples: &#42; All ad group ads under an ad group: `adGroupId="1234"` &#42; All ad group ads under an ad group with an entityStatus of `ENTITY_STATUS_ACTIVE` or `ENTITY_STATUS_PAUSED`: `(entityStatus="ENTITY_STATUS_ACTIVE" OR entityStatus="ENTITY_STATUS_PAUSED") AND adGroupId="12345"` The length of this field should be no more than 500 characters. Reference our [filter `LIST` requests](/display-video/api/guides/how-tos/filters) guide for more information. */
				def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListAdGroupAdsResponse])
			}
			object list {
				def apply(advertisersId :PlayApi, advertiserId: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v3/advertisers/${advertisersId}/adGroupAds").addQueryStringParameters("advertiserId" -> advertiserId.toString))
				given Conversion[list, Future[Schema.ListAdGroupAdsResponse]] = (fun: list) => fun.apply()
			}
		}
		object adGroups {
			class bulkListAdGroupAssignedTargetingOptions(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.BulkListAdGroupAssignedTargetingOptionsResponse]) {
				/** Optional. Requested page size. The size must be an integer between `1` and `5000`. If unspecified, the default is `5000`. Returns error code `INVALID_ARGUMENT` if an invalid value is specified.<br>Format: int32 */
				def withPageSize(pageSize: Int) = new bulkListAdGroupAssignedTargetingOptions(req.addQueryStringParameters("pageSize" -> pageSize.toString))
				/** Optional. A token that lets the client fetch the next page of results. Typically, this is the value of next_page_token returned from the previous call to the `BulkListAdGroupAssignedTargetingOptions` method. If not specified, the first page of results will be returned. */
				def withPageToken(pageToken: String) = new bulkListAdGroupAssignedTargetingOptions(req.addQueryStringParameters("pageToken" -> pageToken.toString))
				/** Optional. Field by which to sort the list. Acceptable values are: &#42; `adGroupId` (default) &#42; `assignedTargetingOption.targetingType` The default sorting order is ascending. To specify descending order for a field, a suffix "desc" should be added to the field name. Example: `targetingType desc`. */
				def withOrderBy(orderBy: String) = new bulkListAdGroupAssignedTargetingOptions(req.addQueryStringParameters("orderBy" -> orderBy.toString))
				/** Optional. Allows filtering by assigned targeting option fields. Supported syntax: &#42; Filter expressions are made up of one or more restrictions. &#42; Restrictions can be combined by the logical operator `OR`. &#42; A restriction has the form of `{field} {operator} {value}`. &#42; All fields must use the `EQUALS (=)` operator. Supported fields: &#42; `targetingType` Examples: &#42; `AssignedTargetingOption` resources of targeting type `TARGETING_TYPE_YOUTUBE_VIDEO` or `TARGETING_TYPE_YOUTUBE_CHANNEL`: `targetingType="TARGETING_TYPE_YOUTUBE_VIDEO" OR targetingType="TARGETING_TYPE_YOUTUBE_CHANNEL"` The length of this field should be no more than 500 characters. Reference our [filter `LIST` requests](/display-video/api/guides/how-tos/filters) guide for more information. */
				def withFilter(filter: String) = new bulkListAdGroupAssignedTargetingOptions(req.addQueryStringParameters("filter" -> filter.toString))
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.BulkListAdGroupAssignedTargetingOptionsResponse])
			}
			object bulkListAdGroupAssignedTargetingOptions {
				def apply(advertisersId :PlayApi, advertiserId: String, adGroupIds: String)(using auth: AuthToken, ec: ExecutionContext): bulkListAdGroupAssignedTargetingOptions = new bulkListAdGroupAssignedTargetingOptions(ws.url(BASE_URL + s"v3/advertisers/${advertisersId}/adGroups:bulkListAdGroupAssignedTargetingOptions").addQueryStringParameters("advertiserId" -> advertiserId.toString, "adGroupIds" -> adGroupIds.toString))
				given Conversion[bulkListAdGroupAssignedTargetingOptions, Future[Schema.BulkListAdGroupAssignedTargetingOptionsResponse]] = (fun: bulkListAdGroupAssignedTargetingOptions) => fun.apply()
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.AdGroup]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.AdGroup])
			}
			object get {
				def apply(advertisersId :PlayApi, adGroupsId :PlayApi, advertiserId: String, adGroupId: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v3/advertisers/${advertisersId}/adGroups/${adGroupsId}").addQueryStringParameters("advertiserId" -> advertiserId.toString, "adGroupId" -> adGroupId.toString))
				given Conversion[get, Future[Schema.AdGroup]] = (fun: get) => fun.apply()
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListAdGroupsResponse]) {
				/** Optional. Requested page size. Must be between `1` and `200`. If unspecified will default to `100`. Returns error code `INVALID_ARGUMENT` if an invalid value is specified.<br>Format: int32 */
				def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
				/** Optional. A token identifying a page of results the server should return. Typically, this is the value of next_page_token returned from the previous call to `ListAdGroups` method. If not specified, the first page of results will be returned. */
				def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
				/** Optional. Field by which to sort the list. Acceptable values are: &#42; `displayName` (default) &#42; `entityStatus` The default sorting order is ascending. To specify descending order for a field, a suffix "desc" should be added to the field name. Example: `displayName desc`. */
				def withOrderBy(orderBy: String) = new list(req.addQueryStringParameters("orderBy" -> orderBy.toString))
				/** Optional. Allows filtering by custom ad group fields. Supported syntax: &#42; Filter expressions are made up of one or more restrictions. &#42; Restrictions can be combined by `AND` and `OR`. A sequence of restrictions implicitly uses `AND`. &#42; A restriction has the form of `{field} {operator} {value}`. &#42; All fields must use the `EQUALS (=)` operator. Supported properties: &#42; `adGroupId` &#42; `displayName` &#42; `entityStatus` &#42; `lineItemId` &#42; `adGroupFormat` Examples: &#42; All ad groups under an line item: `lineItemId="1234"` &#42; All `ENTITY_STATUS_ACTIVE` or `ENTITY_STATUS_PAUSED` `AD_GROUP_FORMAT_IN_STREAM` ad groups under an advertiser: `(entityStatus="ENTITY_STATUS_ACTIVE" OR entityStatus="ENTITY_STATUS_PAUSED") AND adGroupFormat="AD_GROUP_FORMAT_IN_STREAM"` The length of this field should be no more than 500 characters. Reference our [filter `LIST` requests](/display-video/api/guides/how-tos/filters) guide for more information. */
				def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListAdGroupsResponse])
			}
			object list {
				def apply(advertisersId :PlayApi, advertiserId: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v3/advertisers/${advertisersId}/adGroups").addQueryStringParameters("advertiserId" -> advertiserId.toString))
				given Conversion[list, Future[Schema.ListAdGroupsResponse]] = (fun: list) => fun.apply()
			}
			object targetingTypes {
				object assignedTargetingOptions {
					class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.AssignedTargetingOption]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.AssignedTargetingOption])
					}
					object get {
						def apply(advertisersId :PlayApi, adGroupsId :PlayApi, targetingTypesId :PlayApi, assignedTargetingOptionsId :PlayApi, advertiserId: String, adGroupId: String, targetingType: String, assignedTargetingOptionId: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v3/advertisers/${advertisersId}/adGroups/${adGroupsId}/targetingTypes/${targetingTypesId}/assignedTargetingOptions/${assignedTargetingOptionsId}").addQueryStringParameters("advertiserId" -> advertiserId.toString, "adGroupId" -> adGroupId.toString, "targetingType" -> targetingType.toString, "assignedTargetingOptionId" -> assignedTargetingOptionId.toString))
						given Conversion[get, Future[Schema.AssignedTargetingOption]] = (fun: get) => fun.apply()
					}
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListAdGroupAssignedTargetingOptionsResponse]) {
						/** Optional. Requested page size. Must be between `1` and `5000`. If unspecified will default to `100`. Returns error code `INVALID_ARGUMENT` if an invalid value is specified.<br>Format: int32 */
						def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
						/** Optional. A token identifying a page of results the server should return. Typically, this is the value of next_page_token returned from the previous call to `ListAdGroupAssignedTargetingOptions` method. If not specified, the first page of results will be returned. */
						def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
						/** Optional. Field by which to sort the list. Acceptable values are: &#42; `assignedTargetingOptionId` (default) The default sorting order is ascending. To specify descending order for a field, a suffix "desc" should be added to the field name. Example: `assignedTargetingOptionId desc`. */
						def withOrderBy(orderBy: String) = new list(req.addQueryStringParameters("orderBy" -> orderBy.toString))
						/** Optional. Allows filtering by assigned targeting option fields. Supported syntax: &#42; Filter expressions are made up of one or more restrictions. &#42; Restrictions can be combined by the logical operator `OR`. &#42; A restriction has the form of `{field} {operator} {value}`. &#42; All fields must use the `EQUALS (=)` operator. Supported fields: &#42; `assignedTargetingOptionId` Examples: &#42; `AssignedTargetingOption` resources with ID 1 or 2: `assignedTargetingOptionId="1" OR assignedTargetingOptionId="2"` The length of this field should be no more than 500 characters. Reference our [filter `LIST` requests](/display-video/api/guides/how-tos/filters) guide for more information. */
						def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListAdGroupAssignedTargetingOptionsResponse])
					}
					object list {
						def apply(advertisersId :PlayApi, adGroupsId :PlayApi, targetingTypesId :PlayApi, advertiserId: String, adGroupId: String, targetingType: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v3/advertisers/${advertisersId}/adGroups/${adGroupsId}/targetingTypes/${targetingTypesId}/assignedTargetingOptions").addQueryStringParameters("advertiserId" -> advertiserId.toString, "adGroupId" -> adGroupId.toString, "targetingType" -> targetingType.toString))
						given Conversion[list, Future[Schema.ListAdGroupAssignedTargetingOptionsResponse]] = (fun: list) => fun.apply()
					}
				}
			}
		}
		object channels {
			class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withChannel(body: Schema.Channel) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Channel])
			}
			object create {
				def apply(advertisersId :PlayApi, advertiserId: String, partnerId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v3/advertisers/${advertisersId}/channels").addQueryStringParameters("advertiserId" -> advertiserId.toString, "partnerId" -> partnerId.toString))
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Channel]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Channel])
			}
			object get {
				def apply(advertisersId :PlayApi, channelsId :PlayApi, advertiserId: String, channelId: String, partnerId: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v3/advertisers/${advertisersId}/channels/${channelsId}").addQueryStringParameters("advertiserId" -> advertiserId.toString, "channelId" -> channelId.toString, "partnerId" -> partnerId.toString))
				given Conversion[get, Future[Schema.Channel]] = (fun: get) => fun.apply()
			}
			class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withChannel(body: Schema.Channel) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Channel])
			}
			object patch {
				def apply(advertisersId :PlayApi, advertiserId: String, channelId: String, partnerId: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v3/advertisers/${advertisersId}/channels/${channelId}").addQueryStringParameters("advertiserId" -> advertiserId.toString, "partnerId" -> partnerId.toString, "updateMask" -> updateMask.toString))
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListChannelsResponse]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListChannelsResponse])
			}
			object list {
				def apply(advertisersId :PlayApi, advertiserId: String, partnerId: String, pageSize: Int, pageToken: String, orderBy: String, filter: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v3/advertisers/${advertisersId}/channels").addQueryStringParameters("advertiserId" -> advertiserId.toString, "partnerId" -> partnerId.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "orderBy" -> orderBy.toString, "filter" -> filter.toString))
				given Conversion[list, Future[Schema.ListChannelsResponse]] = (fun: list) => fun.apply()
			}
			object sites {
				class replace(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withReplaceSitesRequest(body: Schema.ReplaceSitesRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.ReplaceSitesResponse])
				}
				object replace {
					def apply(channelsId :PlayApi, advertiserId: String, channelId: String)(using auth: AuthToken, ec: ExecutionContext): replace = new replace(ws.url(BASE_URL + s"v3/advertisers/${advertiserId}/channels/${channelsId}/sites:replace").addQueryStringParameters("channelId" -> channelId.toString))
				}
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withSite(body: Schema.Site) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Site])
				}
				object create {
					def apply(channelsId :PlayApi, advertiserId: String, channelId: String, partnerId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v3/advertisers/${advertiserId}/channels/${channelsId}/sites").addQueryStringParameters("channelId" -> channelId.toString, "partnerId" -> partnerId.toString))
				}
				class bulkEdit(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withBulkEditSitesRequest(body: Schema.BulkEditSitesRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.BulkEditSitesResponse])
				}
				object bulkEdit {
					def apply(channelsId :PlayApi, advertiserId: String, channelId: String)(using auth: AuthToken, ec: ExecutionContext): bulkEdit = new bulkEdit(ws.url(BASE_URL + s"v3/advertisers/${advertiserId}/channels/${channelsId}/sites:bulkEdit").addQueryStringParameters("channelId" -> channelId.toString))
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
				}
				object delete {
					def apply(channelsId :PlayApi, sitesId :PlayApi, advertiserId: String, channelId: String, urlOrAppId: String, partnerId: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v3/advertisers/${advertiserId}/channels/${channelsId}/sites/${sitesId}").addQueryStringParameters("channelId" -> channelId.toString, "urlOrAppId" -> urlOrAppId.toString, "partnerId" -> partnerId.toString))
					given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListSitesResponse]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListSitesResponse])
				}
				object list {
					def apply(advertisersId :PlayApi, channelsId :PlayApi, advertiserId: String, channelId: String, partnerId: String, pageSize: Int, pageToken: String, orderBy: String, filter: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v3/advertisers/${advertisersId}/channels/${channelsId}/sites").addQueryStringParameters("advertiserId" -> advertiserId.toString, "channelId" -> channelId.toString, "partnerId" -> partnerId.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "orderBy" -> orderBy.toString, "filter" -> filter.toString))
					given Conversion[list, Future[Schema.ListSitesResponse]] = (fun: list) => fun.apply()
				}
			}
		}
		object invoices {
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListInvoicesResponse]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListInvoicesResponse])
			}
			object list {
				def apply(advertisersId :PlayApi, advertiserId: String, issueMonth: String, loiSapinInvoiceType: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v3/advertisers/${advertisersId}/invoices").addQueryStringParameters("advertiserId" -> advertiserId.toString, "issueMonth" -> issueMonth.toString, "loiSapinInvoiceType" -> loiSapinInvoiceType.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListInvoicesResponse]] = (fun: list) => fun.apply()
			}
			class lookupInvoiceCurrency(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.LookupInvoiceCurrencyResponse]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.LookupInvoiceCurrencyResponse])
			}
			object lookupInvoiceCurrency {
				def apply(advertisersId :PlayApi, advertiserId: String, invoiceMonth: String)(using auth: AuthToken, ec: ExecutionContext): lookupInvoiceCurrency = new lookupInvoiceCurrency(ws.url(BASE_URL + s"v3/advertisers/${advertisersId}/invoices:lookupInvoiceCurrency").addQueryStringParameters("advertiserId" -> advertiserId.toString, "invoiceMonth" -> invoiceMonth.toString))
				given Conversion[lookupInvoiceCurrency, Future[Schema.LookupInvoiceCurrencyResponse]] = (fun: lookupInvoiceCurrency) => fun.apply()
			}
		}
		object negativeKeywordLists {
			class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withNegativeKeywordList(body: Schema.NegativeKeywordList) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.NegativeKeywordList])
			}
			object create {
				def apply(advertisersId :PlayApi, advertiserId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v3/advertisers/${advertisersId}/negativeKeywordLists").addQueryStringParameters("advertiserId" -> advertiserId.toString))
			}
			class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
				def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
			}
			object delete {
				def apply(advertisersId :PlayApi, negativeKeywordListsId :PlayApi, advertiserId: String, negativeKeywordListId: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v3/advertisers/${advertisersId}/negativeKeywordLists/${negativeKeywordListsId}").addQueryStringParameters("advertiserId" -> advertiserId.toString, "negativeKeywordListId" -> negativeKeywordListId.toString))
				given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.NegativeKeywordList]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.NegativeKeywordList])
			}
			object get {
				def apply(advertisersId :PlayApi, negativeKeywordListsId :PlayApi, advertiserId: String, negativeKeywordListId: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v3/advertisers/${advertisersId}/negativeKeywordLists/${negativeKeywordListsId}").addQueryStringParameters("advertiserId" -> advertiserId.toString, "negativeKeywordListId" -> negativeKeywordListId.toString))
				given Conversion[get, Future[Schema.NegativeKeywordList]] = (fun: get) => fun.apply()
			}
			class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withNegativeKeywordList(body: Schema.NegativeKeywordList) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.NegativeKeywordList])
			}
			object patch {
				def apply(advertisersId :PlayApi, advertiserId: String, negativeKeywordListId: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v3/advertisers/${advertisersId}/negativeKeywordLists/${negativeKeywordListId}").addQueryStringParameters("advertiserId" -> advertiserId.toString, "updateMask" -> updateMask.toString))
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListNegativeKeywordListsResponse]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListNegativeKeywordListsResponse])
			}
			object list {
				def apply(advertisersId :PlayApi, advertiserId: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v3/advertisers/${advertisersId}/negativeKeywordLists").addQueryStringParameters("advertiserId" -> advertiserId.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListNegativeKeywordListsResponse]] = (fun: list) => fun.apply()
			}
			object negativeKeywords {
				class replace(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withReplaceNegativeKeywordsRequest(body: Schema.ReplaceNegativeKeywordsRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.ReplaceNegativeKeywordsResponse])
				}
				object replace {
					def apply(negativeKeywordListsId :PlayApi, advertiserId: String, negativeKeywordListId: String)(using auth: AuthToken, ec: ExecutionContext): replace = new replace(ws.url(BASE_URL + s"v3/advertisers/${advertiserId}/negativeKeywordLists/${negativeKeywordListsId}/negativeKeywords:replace").addQueryStringParameters("negativeKeywordListId" -> negativeKeywordListId.toString))
				}
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withNegativeKeyword(body: Schema.NegativeKeyword) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.NegativeKeyword])
				}
				object create {
					def apply(negativeKeywordListsId :PlayApi, advertiserId: String, negativeKeywordListId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v3/advertisers/${advertiserId}/negativeKeywordLists/${negativeKeywordListsId}/negativeKeywords").addQueryStringParameters("negativeKeywordListId" -> negativeKeywordListId.toString))
				}
				class bulkEdit(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withBulkEditNegativeKeywordsRequest(body: Schema.BulkEditNegativeKeywordsRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.BulkEditNegativeKeywordsResponse])
				}
				object bulkEdit {
					def apply(negativeKeywordListsId :PlayApi, advertiserId: String, negativeKeywordListId: String)(using auth: AuthToken, ec: ExecutionContext): bulkEdit = new bulkEdit(ws.url(BASE_URL + s"v3/advertisers/${advertiserId}/negativeKeywordLists/${negativeKeywordListsId}/negativeKeywords:bulkEdit").addQueryStringParameters("negativeKeywordListId" -> negativeKeywordListId.toString))
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
				}
				object delete {
					def apply(negativeKeywordListsId :PlayApi, negativeKeywordsId :PlayApi, advertiserId: String, negativeKeywordListId: String, keywordValue: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v3/advertisers/${advertiserId}/negativeKeywordLists/${negativeKeywordListsId}/negativeKeywords/${negativeKeywordsId}").addQueryStringParameters("negativeKeywordListId" -> negativeKeywordListId.toString, "keywordValue" -> keywordValue.toString))
					given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListNegativeKeywordsResponse]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListNegativeKeywordsResponse])
				}
				object list {
					def apply(advertisersId :PlayApi, negativeKeywordListsId :PlayApi, advertiserId: String, negativeKeywordListId: String, pageSize: Int, pageToken: String, orderBy: String, filter: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v3/advertisers/${advertisersId}/negativeKeywordLists/${negativeKeywordListsId}/negativeKeywords").addQueryStringParameters("advertiserId" -> advertiserId.toString, "negativeKeywordListId" -> negativeKeywordListId.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "orderBy" -> orderBy.toString, "filter" -> filter.toString))
					given Conversion[list, Future[Schema.ListNegativeKeywordsResponse]] = (fun: list) => fun.apply()
				}
			}
		}
		object creatives {
			class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withCreative(body: Schema.Creative) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Creative])
			}
			object create {
				def apply(advertisersId :PlayApi, advertiserId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v3/advertisers/${advertisersId}/creatives").addQueryStringParameters("advertiserId" -> advertiserId.toString))
			}
			class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
				def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
			}
			object delete {
				def apply(advertisersId :PlayApi, creativesId :PlayApi, advertiserId: String, creativeId: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v3/advertisers/${advertisersId}/creatives/${creativesId}").addQueryStringParameters("advertiserId" -> advertiserId.toString, "creativeId" -> creativeId.toString))
				given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Creative]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Creative])
			}
			object get {
				def apply(advertisersId :PlayApi, creativesId :PlayApi, advertiserId: String, creativeId: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v3/advertisers/${advertisersId}/creatives/${creativesId}").addQueryStringParameters("advertiserId" -> advertiserId.toString, "creativeId" -> creativeId.toString))
				given Conversion[get, Future[Schema.Creative]] = (fun: get) => fun.apply()
			}
			class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withCreative(body: Schema.Creative) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Creative])
			}
			object patch {
				def apply(advertisersId :PlayApi, creativesId :PlayApi, advertiserId: String, creativeId: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v3/advertisers/${advertisersId}/creatives/${creativesId}").addQueryStringParameters("advertiserId" -> advertiserId.toString, "creativeId" -> creativeId.toString, "updateMask" -> updateMask.toString))
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListCreativesResponse]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListCreativesResponse])
			}
			object list {
				def apply(advertisersId :PlayApi, advertiserId: String, pageSize: Int, pageToken: String, orderBy: String, filter: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v3/advertisers/${advertisersId}/creatives").addQueryStringParameters("advertiserId" -> advertiserId.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "orderBy" -> orderBy.toString, "filter" -> filter.toString))
				given Conversion[list, Future[Schema.ListCreativesResponse]] = (fun: list) => fun.apply()
			}
		}
		object insertionOrders {
			class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withInsertionOrder(body: Schema.InsertionOrder) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.InsertionOrder])
			}
			object create {
				def apply(advertisersId :PlayApi, advertiserId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v3/advertisers/${advertisersId}/insertionOrders").addQueryStringParameters("advertiserId" -> advertiserId.toString))
			}
			class listAssignedTargetingOptions(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.BulkListInsertionOrderAssignedTargetingOptionsResponse]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.BulkListInsertionOrderAssignedTargetingOptionsResponse])
			}
			object listAssignedTargetingOptions {
				def apply(advertisersId :PlayApi, insertionOrdersId :PlayApi, advertiserId: String, insertionOrderId: String, pageSize: Int, pageToken: String, orderBy: String, filter: String)(using auth: AuthToken, ec: ExecutionContext): listAssignedTargetingOptions = new listAssignedTargetingOptions(ws.url(BASE_URL + s"v3/advertisers/${advertisersId}/insertionOrders/${insertionOrdersId}:listAssignedTargetingOptions").addQueryStringParameters("advertiserId" -> advertiserId.toString, "insertionOrderId" -> insertionOrderId.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "orderBy" -> orderBy.toString, "filter" -> filter.toString))
				given Conversion[listAssignedTargetingOptions, Future[Schema.BulkListInsertionOrderAssignedTargetingOptionsResponse]] = (fun: listAssignedTargetingOptions) => fun.apply()
			}
			class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
				def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
			}
			object delete {
				def apply(advertisersId :PlayApi, insertionOrdersId :PlayApi, advertiserId: String, insertionOrderId: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v3/advertisers/${advertisersId}/insertionOrders/${insertionOrdersId}").addQueryStringParameters("advertiserId" -> advertiserId.toString, "insertionOrderId" -> insertionOrderId.toString))
				given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.InsertionOrder]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.InsertionOrder])
			}
			object get {
				def apply(advertisersId :PlayApi, insertionOrdersId :PlayApi, advertiserId: String, insertionOrderId: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v3/advertisers/${advertisersId}/insertionOrders/${insertionOrdersId}").addQueryStringParameters("advertiserId" -> advertiserId.toString, "insertionOrderId" -> insertionOrderId.toString))
				given Conversion[get, Future[Schema.InsertionOrder]] = (fun: get) => fun.apply()
			}
			class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withInsertionOrder(body: Schema.InsertionOrder) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.InsertionOrder])
			}
			object patch {
				def apply(advertisersId :PlayApi, insertionOrdersId :PlayApi, advertiserId: String, insertionOrderId: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v3/advertisers/${advertisersId}/insertionOrders/${insertionOrdersId}").addQueryStringParameters("advertiserId" -> advertiserId.toString, "insertionOrderId" -> insertionOrderId.toString, "updateMask" -> updateMask.toString))
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListInsertionOrdersResponse]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListInsertionOrdersResponse])
			}
			object list {
				def apply(advertisersId :PlayApi, advertiserId: String, pageSize: Int, pageToken: String, orderBy: String, filter: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v3/advertisers/${advertisersId}/insertionOrders").addQueryStringParameters("advertiserId" -> advertiserId.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "orderBy" -> orderBy.toString, "filter" -> filter.toString))
				given Conversion[list, Future[Schema.ListInsertionOrdersResponse]] = (fun: list) => fun.apply()
			}
			object targetingTypes {
				object assignedTargetingOptions {
					class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.AssignedTargetingOption]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.AssignedTargetingOption])
					}
					object get {
						def apply(advertisersId :PlayApi, insertionOrdersId :PlayApi, targetingTypesId :PlayApi, assignedTargetingOptionsId :PlayApi, advertiserId: String, insertionOrderId: String, targetingType: String, assignedTargetingOptionId: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v3/advertisers/${advertisersId}/insertionOrders/${insertionOrdersId}/targetingTypes/${targetingTypesId}/assignedTargetingOptions/${assignedTargetingOptionsId}").addQueryStringParameters("advertiserId" -> advertiserId.toString, "insertionOrderId" -> insertionOrderId.toString, "targetingType" -> targetingType.toString, "assignedTargetingOptionId" -> assignedTargetingOptionId.toString))
						given Conversion[get, Future[Schema.AssignedTargetingOption]] = (fun: get) => fun.apply()
					}
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListInsertionOrderAssignedTargetingOptionsResponse]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListInsertionOrderAssignedTargetingOptionsResponse])
					}
					object list {
						def apply(advertisersId :PlayApi, insertionOrdersId :PlayApi, targetingTypesId :PlayApi, advertiserId: String, insertionOrderId: String, targetingType: String, pageSize: Int, pageToken: String, orderBy: String, filter: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v3/advertisers/${advertisersId}/insertionOrders/${insertionOrdersId}/targetingTypes/${targetingTypesId}/assignedTargetingOptions").addQueryStringParameters("advertiserId" -> advertiserId.toString, "insertionOrderId" -> insertionOrderId.toString, "targetingType" -> targetingType.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "orderBy" -> orderBy.toString, "filter" -> filter.toString))
						given Conversion[list, Future[Schema.ListInsertionOrderAssignedTargetingOptionsResponse]] = (fun: list) => fun.apply()
					}
					class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withAssignedTargetingOption(body: Schema.AssignedTargetingOption) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.AssignedTargetingOption])
					}
					object create {
						def apply(advertisersId :PlayApi, insertionOrdersId :PlayApi, targetingTypesId :PlayApi, advertiserId: String, insertionOrderId: String, targetingType: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v3/advertisers/${advertisersId}/insertionOrders/${insertionOrdersId}/targetingTypes/${targetingTypesId}/assignedTargetingOptions").addQueryStringParameters("advertiserId" -> advertiserId.toString, "insertionOrderId" -> insertionOrderId.toString, "targetingType" -> targetingType.toString))
					}
					class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
						def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
					}
					object delete {
						def apply(advertisersId :PlayApi, insertionOrdersId :PlayApi, targetingTypesId :PlayApi, assignedTargetingOptionsId :PlayApi, advertiserId: String, insertionOrderId: String, targetingType: String, assignedTargetingOptionId: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v3/advertisers/${advertisersId}/insertionOrders/${insertionOrdersId}/targetingTypes/${targetingTypesId}/assignedTargetingOptions/${assignedTargetingOptionsId}").addQueryStringParameters("advertiserId" -> advertiserId.toString, "insertionOrderId" -> insertionOrderId.toString, "targetingType" -> targetingType.toString, "assignedTargetingOptionId" -> assignedTargetingOptionId.toString))
						given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
					}
				}
			}
		}
		object locationLists {
			class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withLocationList(body: Schema.LocationList) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.LocationList])
			}
			object create {
				def apply(advertisersId :PlayApi, advertiserId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v3/advertisers/${advertisersId}/locationLists").addQueryStringParameters("advertiserId" -> advertiserId.toString))
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.LocationList]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.LocationList])
			}
			object get {
				def apply(advertisersId :PlayApi, locationListsId :PlayApi, advertiserId: String, locationListId: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v3/advertisers/${advertisersId}/locationLists/${locationListsId}").addQueryStringParameters("advertiserId" -> advertiserId.toString, "locationListId" -> locationListId.toString))
				given Conversion[get, Future[Schema.LocationList]] = (fun: get) => fun.apply()
			}
			class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withLocationList(body: Schema.LocationList) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.LocationList])
			}
			object patch {
				def apply(advertisersId :PlayApi, advertiserId: String, locationListId: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v3/advertisers/${advertisersId}/locationLists/${locationListId}").addQueryStringParameters("advertiserId" -> advertiserId.toString, "updateMask" -> updateMask.toString))
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListLocationListsResponse]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListLocationListsResponse])
			}
			object list {
				def apply(advertisersId :PlayApi, advertiserId: String, pageSize: Int, pageToken: String, orderBy: String, filter: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v3/advertisers/${advertisersId}/locationLists").addQueryStringParameters("advertiserId" -> advertiserId.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "orderBy" -> orderBy.toString, "filter" -> filter.toString))
				given Conversion[list, Future[Schema.ListLocationListsResponse]] = (fun: list) => fun.apply()
			}
			object assignedLocations {
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListAssignedLocationsResponse]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListAssignedLocationsResponse])
				}
				object list {
					def apply(advertiserId: String, locationListId: String, pageSize: Int, pageToken: String, orderBy: String, filter: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v3/advertisers/${advertiserId}/locationLists/${locationListId}/assignedLocations").addQueryStringParameters("pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "orderBy" -> orderBy.toString, "filter" -> filter.toString))
					given Conversion[list, Future[Schema.ListAssignedLocationsResponse]] = (fun: list) => fun.apply()
				}
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withAssignedLocation(body: Schema.AssignedLocation) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.AssignedLocation])
				}
				object create {
					def apply(advertiserId: String, locationListId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v3/advertisers/${advertiserId}/locationLists/${locationListId}/assignedLocations").addQueryStringParameters())
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
				}
				object delete {
					def apply(assignedLocationsId :PlayApi, advertiserId: String, locationListId: String, assignedLocationId: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v3/advertisers/${advertiserId}/locationLists/${locationListId}/assignedLocations/${assignedLocationsId}").addQueryStringParameters("assignedLocationId" -> assignedLocationId.toString))
					given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
				}
				class bulkEdit(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withBulkEditAssignedLocationsRequest(body: Schema.BulkEditAssignedLocationsRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.BulkEditAssignedLocationsResponse])
				}
				object bulkEdit {
					def apply(locationListsId :PlayApi, advertiserId: String, locationListId: String)(using auth: AuthToken, ec: ExecutionContext): bulkEdit = new bulkEdit(ws.url(BASE_URL + s"v3/advertisers/${advertiserId}/locationLists/${locationListsId}/assignedLocations:bulkEdit").addQueryStringParameters("locationListId" -> locationListId.toString))
				}
			}
		}
		object campaigns {
			class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withCampaign(body: Schema.Campaign) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Campaign])
			}
			object create {
				def apply(advertisersId :PlayApi, advertiserId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v3/advertisers/${advertisersId}/campaigns").addQueryStringParameters("advertiserId" -> advertiserId.toString))
			}
			class listAssignedTargetingOptions(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.BulkListCampaignAssignedTargetingOptionsResponse]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.BulkListCampaignAssignedTargetingOptionsResponse])
			}
			object listAssignedTargetingOptions {
				def apply(advertisersId :PlayApi, campaignsId :PlayApi, advertiserId: String, campaignId: String, pageSize: Int, pageToken: String, orderBy: String, filter: String)(using auth: AuthToken, ec: ExecutionContext): listAssignedTargetingOptions = new listAssignedTargetingOptions(ws.url(BASE_URL + s"v3/advertisers/${advertisersId}/campaigns/${campaignsId}:listAssignedTargetingOptions").addQueryStringParameters("advertiserId" -> advertiserId.toString, "campaignId" -> campaignId.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "orderBy" -> orderBy.toString, "filter" -> filter.toString))
				given Conversion[listAssignedTargetingOptions, Future[Schema.BulkListCampaignAssignedTargetingOptionsResponse]] = (fun: listAssignedTargetingOptions) => fun.apply()
			}
			class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
				def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
			}
			object delete {
				def apply(advertisersId :PlayApi, campaignsId :PlayApi, advertiserId: String, campaignId: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v3/advertisers/${advertisersId}/campaigns/${campaignsId}").addQueryStringParameters("advertiserId" -> advertiserId.toString, "campaignId" -> campaignId.toString))
				given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Campaign]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Campaign])
			}
			object get {
				def apply(advertisersId :PlayApi, campaignsId :PlayApi, advertiserId: String, campaignId: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v3/advertisers/${advertisersId}/campaigns/${campaignsId}").addQueryStringParameters("advertiserId" -> advertiserId.toString, "campaignId" -> campaignId.toString))
				given Conversion[get, Future[Schema.Campaign]] = (fun: get) => fun.apply()
			}
			class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withCampaign(body: Schema.Campaign) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Campaign])
			}
			object patch {
				def apply(advertisersId :PlayApi, campaignsId :PlayApi, advertiserId: String, campaignId: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v3/advertisers/${advertisersId}/campaigns/${campaignsId}").addQueryStringParameters("advertiserId" -> advertiserId.toString, "campaignId" -> campaignId.toString, "updateMask" -> updateMask.toString))
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListCampaignsResponse]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListCampaignsResponse])
			}
			object list {
				def apply(advertisersId :PlayApi, advertiserId: String, pageSize: Int, pageToken: String, orderBy: String, filter: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v3/advertisers/${advertisersId}/campaigns").addQueryStringParameters("advertiserId" -> advertiserId.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "orderBy" -> orderBy.toString, "filter" -> filter.toString))
				given Conversion[list, Future[Schema.ListCampaignsResponse]] = (fun: list) => fun.apply()
			}
			object targetingTypes {
				object assignedTargetingOptions {
					class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.AssignedTargetingOption]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.AssignedTargetingOption])
					}
					object get {
						def apply(advertisersId :PlayApi, campaignsId :PlayApi, targetingTypesId :PlayApi, assignedTargetingOptionsId :PlayApi, advertiserId: String, campaignId: String, targetingType: String, assignedTargetingOptionId: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v3/advertisers/${advertisersId}/campaigns/${campaignsId}/targetingTypes/${targetingTypesId}/assignedTargetingOptions/${assignedTargetingOptionsId}").addQueryStringParameters("advertiserId" -> advertiserId.toString, "campaignId" -> campaignId.toString, "targetingType" -> targetingType.toString, "assignedTargetingOptionId" -> assignedTargetingOptionId.toString))
						given Conversion[get, Future[Schema.AssignedTargetingOption]] = (fun: get) => fun.apply()
					}
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListCampaignAssignedTargetingOptionsResponse]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListCampaignAssignedTargetingOptionsResponse])
					}
					object list {
						def apply(advertisersId :PlayApi, campaignsId :PlayApi, targetingTypesId :PlayApi, advertiserId: String, campaignId: String, targetingType: String, pageSize: Int, pageToken: String, orderBy: String, filter: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v3/advertisers/${advertisersId}/campaigns/${campaignsId}/targetingTypes/${targetingTypesId}/assignedTargetingOptions").addQueryStringParameters("advertiserId" -> advertiserId.toString, "campaignId" -> campaignId.toString, "targetingType" -> targetingType.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "orderBy" -> orderBy.toString, "filter" -> filter.toString))
						given Conversion[list, Future[Schema.ListCampaignAssignedTargetingOptionsResponse]] = (fun: list) => fun.apply()
					}
				}
			}
		}
		object targetingTypes {
			object assignedTargetingOptions {
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.AssignedTargetingOption]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.AssignedTargetingOption])
				}
				object get {
					def apply(advertisersId :PlayApi, targetingTypesId :PlayApi, assignedTargetingOptionsId :PlayApi, advertiserId: String, targetingType: String, assignedTargetingOptionId: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v3/advertisers/${advertisersId}/targetingTypes/${targetingTypesId}/assignedTargetingOptions/${assignedTargetingOptionsId}").addQueryStringParameters("advertiserId" -> advertiserId.toString, "targetingType" -> targetingType.toString, "assignedTargetingOptionId" -> assignedTargetingOptionId.toString))
					given Conversion[get, Future[Schema.AssignedTargetingOption]] = (fun: get) => fun.apply()
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListAdvertiserAssignedTargetingOptionsResponse]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListAdvertiserAssignedTargetingOptionsResponse])
				}
				object list {
					def apply(advertisersId :PlayApi, targetingTypesId :PlayApi, advertiserId: String, targetingType: String, pageSize: Int, pageToken: String, orderBy: String, filter: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v3/advertisers/${advertisersId}/targetingTypes/${targetingTypesId}/assignedTargetingOptions").addQueryStringParameters("advertiserId" -> advertiserId.toString, "targetingType" -> targetingType.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "orderBy" -> orderBy.toString, "filter" -> filter.toString))
					given Conversion[list, Future[Schema.ListAdvertiserAssignedTargetingOptionsResponse]] = (fun: list) => fun.apply()
				}
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withAssignedTargetingOption(body: Schema.AssignedTargetingOption) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.AssignedTargetingOption])
				}
				object create {
					def apply(advertisersId :PlayApi, targetingTypesId :PlayApi, advertiserId: String, targetingType: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v3/advertisers/${advertisersId}/targetingTypes/${targetingTypesId}/assignedTargetingOptions").addQueryStringParameters("advertiserId" -> advertiserId.toString, "targetingType" -> targetingType.toString))
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
				}
				object delete {
					def apply(advertisersId :PlayApi, targetingTypesId :PlayApi, assignedTargetingOptionsId :PlayApi, advertiserId: String, targetingType: String, assignedTargetingOptionId: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v3/advertisers/${advertisersId}/targetingTypes/${targetingTypesId}/assignedTargetingOptions/${assignedTargetingOptionsId}").addQueryStringParameters("advertiserId" -> advertiserId.toString, "targetingType" -> targetingType.toString, "assignedTargetingOptionId" -> assignedTargetingOptionId.toString))
					given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
				}
			}
		}
	}
	object combinedAudiences {
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.CombinedAudience]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.CombinedAudience])
		}
		object get {
			def apply(combinedAudiencesId :PlayApi, combinedAudienceId: String, partnerId: String, advertiserId: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v3/combinedAudiences/${combinedAudiencesId}").addQueryStringParameters("combinedAudienceId" -> combinedAudienceId.toString, "partnerId" -> partnerId.toString, "advertiserId" -> advertiserId.toString))
			given Conversion[get, Future[Schema.CombinedAudience]] = (fun: get) => fun.apply()
		}
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListCombinedAudiencesResponse]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListCombinedAudiencesResponse])
		}
		object list {
			def apply(partnerId: String, advertiserId: String, pageSize: Int, pageToken: String, orderBy: String, filter: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v3/combinedAudiences").addQueryStringParameters("partnerId" -> partnerId.toString, "advertiserId" -> advertiserId.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "orderBy" -> orderBy.toString, "filter" -> filter.toString))
			given Conversion[list, Future[Schema.ListCombinedAudiencesResponse]] = (fun: list) => fun.apply()
		}
	}
	object customBiddingAlgorithms {
		class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withCustomBiddingAlgorithm(body: Schema.CustomBiddingAlgorithm) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.CustomBiddingAlgorithm])
		}
		object create {
			def apply()(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v3/customBiddingAlgorithms").addQueryStringParameters())
		}
		class uploadScript(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.CustomBiddingScriptRef]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.CustomBiddingScriptRef])
		}
		object uploadScript {
			def apply(customBiddingAlgorithmsId :PlayApi, customBiddingAlgorithmId: String, partnerId: String, advertiserId: String)(using auth: AuthToken, ec: ExecutionContext): uploadScript = new uploadScript(ws.url(BASE_URL + s"v3/customBiddingAlgorithms/${customBiddingAlgorithmsId}:uploadScript").addQueryStringParameters("customBiddingAlgorithmId" -> customBiddingAlgorithmId.toString, "partnerId" -> partnerId.toString, "advertiserId" -> advertiserId.toString))
			given Conversion[uploadScript, Future[Schema.CustomBiddingScriptRef]] = (fun: uploadScript) => fun.apply()
		}
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListCustomBiddingAlgorithmsResponse]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListCustomBiddingAlgorithmsResponse])
		}
		object list {
			def apply(partnerId: String, advertiserId: String, pageSize: Int, pageToken: String, orderBy: String, filter: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v3/customBiddingAlgorithms").addQueryStringParameters("partnerId" -> partnerId.toString, "advertiserId" -> advertiserId.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "orderBy" -> orderBy.toString, "filter" -> filter.toString))
			given Conversion[list, Future[Schema.ListCustomBiddingAlgorithmsResponse]] = (fun: list) => fun.apply()
		}
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.CustomBiddingAlgorithm]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.CustomBiddingAlgorithm])
		}
		object get {
			def apply(customBiddingAlgorithmsId :PlayApi, customBiddingAlgorithmId: String, partnerId: String, advertiserId: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v3/customBiddingAlgorithms/${customBiddingAlgorithmsId}").addQueryStringParameters("customBiddingAlgorithmId" -> customBiddingAlgorithmId.toString, "partnerId" -> partnerId.toString, "advertiserId" -> advertiserId.toString))
			given Conversion[get, Future[Schema.CustomBiddingAlgorithm]] = (fun: get) => fun.apply()
		}
		class uploadRules(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.CustomBiddingAlgorithmRulesRef]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.CustomBiddingAlgorithmRulesRef])
		}
		object uploadRules {
			def apply(customBiddingAlgorithmsId :PlayApi, customBiddingAlgorithmId: String, partnerId: String, advertiserId: String)(using auth: AuthToken, ec: ExecutionContext): uploadRules = new uploadRules(ws.url(BASE_URL + s"v3/customBiddingAlgorithms/${customBiddingAlgorithmsId}:uploadRules").addQueryStringParameters("customBiddingAlgorithmId" -> customBiddingAlgorithmId.toString, "partnerId" -> partnerId.toString, "advertiserId" -> advertiserId.toString))
			given Conversion[uploadRules, Future[Schema.CustomBiddingAlgorithmRulesRef]] = (fun: uploadRules) => fun.apply()
		}
		class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withCustomBiddingAlgorithm(body: Schema.CustomBiddingAlgorithm) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.CustomBiddingAlgorithm])
		}
		object patch {
			def apply(customBiddingAlgorithmsId :PlayApi, customBiddingAlgorithmId: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v3/customBiddingAlgorithms/${customBiddingAlgorithmsId}").addQueryStringParameters("customBiddingAlgorithmId" -> customBiddingAlgorithmId.toString, "updateMask" -> updateMask.toString))
		}
		object rules {
			class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withCustomBiddingAlgorithmRules(body: Schema.CustomBiddingAlgorithmRules) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.CustomBiddingAlgorithmRules])
			}
			object create {
				def apply(customBiddingAlgorithmsId :PlayApi, customBiddingAlgorithmId: String, partnerId: String, advertiserId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v3/customBiddingAlgorithms/${customBiddingAlgorithmsId}/rules").addQueryStringParameters("customBiddingAlgorithmId" -> customBiddingAlgorithmId.toString, "partnerId" -> partnerId.toString, "advertiserId" -> advertiserId.toString))
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.CustomBiddingAlgorithmRules]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.CustomBiddingAlgorithmRules])
			}
			object get {
				def apply(customBiddingAlgorithmsId :PlayApi, rulesId :PlayApi, customBiddingAlgorithmId: String, customBiddingAlgorithmRulesId: String, partnerId: String, advertiserId: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v3/customBiddingAlgorithms/${customBiddingAlgorithmsId}/rules/${rulesId}").addQueryStringParameters("customBiddingAlgorithmId" -> customBiddingAlgorithmId.toString, "customBiddingAlgorithmRulesId" -> customBiddingAlgorithmRulesId.toString, "partnerId" -> partnerId.toString, "advertiserId" -> advertiserId.toString))
				given Conversion[get, Future[Schema.CustomBiddingAlgorithmRules]] = (fun: get) => fun.apply()
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListCustomBiddingAlgorithmRulesResponse]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListCustomBiddingAlgorithmRulesResponse])
			}
			object list {
				def apply(customBiddingAlgorithmsId :PlayApi, customBiddingAlgorithmId: String, partnerId: String, advertiserId: String, pageSize: Int, pageToken: String, orderBy: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v3/customBiddingAlgorithms/${customBiddingAlgorithmsId}/rules").addQueryStringParameters("customBiddingAlgorithmId" -> customBiddingAlgorithmId.toString, "partnerId" -> partnerId.toString, "advertiserId" -> advertiserId.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "orderBy" -> orderBy.toString))
				given Conversion[list, Future[Schema.ListCustomBiddingAlgorithmRulesResponse]] = (fun: list) => fun.apply()
			}
		}
		object scripts {
			class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withCustomBiddingScript(body: Schema.CustomBiddingScript) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.CustomBiddingScript])
			}
			object create {
				def apply(customBiddingAlgorithmsId :PlayApi, customBiddingAlgorithmId: String, partnerId: String, advertiserId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v3/customBiddingAlgorithms/${customBiddingAlgorithmsId}/scripts").addQueryStringParameters("customBiddingAlgorithmId" -> customBiddingAlgorithmId.toString, "partnerId" -> partnerId.toString, "advertiserId" -> advertiserId.toString))
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.CustomBiddingScript]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.CustomBiddingScript])
			}
			object get {
				def apply(customBiddingAlgorithmsId :PlayApi, scriptsId :PlayApi, customBiddingAlgorithmId: String, customBiddingScriptId: String, partnerId: String, advertiserId: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v3/customBiddingAlgorithms/${customBiddingAlgorithmsId}/scripts/${scriptsId}").addQueryStringParameters("customBiddingAlgorithmId" -> customBiddingAlgorithmId.toString, "customBiddingScriptId" -> customBiddingScriptId.toString, "partnerId" -> partnerId.toString, "advertiserId" -> advertiserId.toString))
				given Conversion[get, Future[Schema.CustomBiddingScript]] = (fun: get) => fun.apply()
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListCustomBiddingScriptsResponse]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListCustomBiddingScriptsResponse])
			}
			object list {
				def apply(customBiddingAlgorithmsId :PlayApi, customBiddingAlgorithmId: String, partnerId: String, advertiserId: String, pageSize: Int, pageToken: String, orderBy: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v3/customBiddingAlgorithms/${customBiddingAlgorithmsId}/scripts").addQueryStringParameters("customBiddingAlgorithmId" -> customBiddingAlgorithmId.toString, "partnerId" -> partnerId.toString, "advertiserId" -> advertiserId.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "orderBy" -> orderBy.toString))
				given Conversion[list, Future[Schema.ListCustomBiddingScriptsResponse]] = (fun: list) => fun.apply()
			}
		}
	}
	object customLists {
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.CustomList]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.CustomList])
		}
		object get {
			def apply(customListsId :PlayApi, customListId: String, advertiserId: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v3/customLists/${customListsId}").addQueryStringParameters("customListId" -> customListId.toString, "advertiserId" -> advertiserId.toString))
			given Conversion[get, Future[Schema.CustomList]] = (fun: get) => fun.apply()
		}
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListCustomListsResponse]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListCustomListsResponse])
		}
		object list {
			def apply(advertiserId: String, pageSize: Int, pageToken: String, orderBy: String, filter: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v3/customLists").addQueryStringParameters("advertiserId" -> advertiserId.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "orderBy" -> orderBy.toString, "filter" -> filter.toString))
			given Conversion[list, Future[Schema.ListCustomListsResponse]] = (fun: list) => fun.apply()
		}
	}
	object firstAndThirdPartyAudiences {
		class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withFirstAndThirdPartyAudience(body: Schema.FirstAndThirdPartyAudience) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.FirstAndThirdPartyAudience])
		}
		object create {
			def apply(advertiserId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v3/firstAndThirdPartyAudiences").addQueryStringParameters("advertiserId" -> advertiserId.toString))
		}
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.FirstAndThirdPartyAudience]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.FirstAndThirdPartyAudience])
		}
		object get {
			def apply(firstAndThirdPartyAudiencesId :PlayApi, firstAndThirdPartyAudienceId: String, partnerId: String, advertiserId: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v3/firstAndThirdPartyAudiences/${firstAndThirdPartyAudiencesId}").addQueryStringParameters("firstAndThirdPartyAudienceId" -> firstAndThirdPartyAudienceId.toString, "partnerId" -> partnerId.toString, "advertiserId" -> advertiserId.toString))
			given Conversion[get, Future[Schema.FirstAndThirdPartyAudience]] = (fun: get) => fun.apply()
		}
		class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withFirstAndThirdPartyAudience(body: Schema.FirstAndThirdPartyAudience) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.FirstAndThirdPartyAudience])
		}
		object patch {
			def apply(firstAndThirdPartyAudiencesId :PlayApi, firstAndThirdPartyAudienceId: String, updateMask: String, advertiserId: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v3/firstAndThirdPartyAudiences/${firstAndThirdPartyAudiencesId}").addQueryStringParameters("firstAndThirdPartyAudienceId" -> firstAndThirdPartyAudienceId.toString, "updateMask" -> updateMask.toString, "advertiserId" -> advertiserId.toString))
		}
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListFirstAndThirdPartyAudiencesResponse]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListFirstAndThirdPartyAudiencesResponse])
		}
		object list {
			def apply(partnerId: String, advertiserId: String, pageSize: Int, pageToken: String, orderBy: String, filter: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v3/firstAndThirdPartyAudiences").addQueryStringParameters("partnerId" -> partnerId.toString, "advertiserId" -> advertiserId.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "orderBy" -> orderBy.toString, "filter" -> filter.toString))
			given Conversion[list, Future[Schema.ListFirstAndThirdPartyAudiencesResponse]] = (fun: list) => fun.apply()
		}
		class editCustomerMatchMembers(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withEditCustomerMatchMembersRequest(body: Schema.EditCustomerMatchMembersRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.EditCustomerMatchMembersResponse])
		}
		object editCustomerMatchMembers {
			def apply(firstAndThirdPartyAudiencesId :PlayApi, firstAndThirdPartyAudienceId: String)(using auth: AuthToken, ec: ExecutionContext): editCustomerMatchMembers = new editCustomerMatchMembers(ws.url(BASE_URL + s"v3/firstAndThirdPartyAudiences/${firstAndThirdPartyAudiencesId}:editCustomerMatchMembers").addQueryStringParameters("firstAndThirdPartyAudienceId" -> firstAndThirdPartyAudienceId.toString))
		}
	}
	object floodlightGroups {
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.FloodlightGroup]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.FloodlightGroup])
		}
		object get {
			def apply(floodlightGroupsId :PlayApi, floodlightGroupId: String, partnerId: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v3/floodlightGroups/${floodlightGroupsId}").addQueryStringParameters("floodlightGroupId" -> floodlightGroupId.toString, "partnerId" -> partnerId.toString))
			given Conversion[get, Future[Schema.FloodlightGroup]] = (fun: get) => fun.apply()
		}
		class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withFloodlightGroup(body: Schema.FloodlightGroup) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.FloodlightGroup])
		}
		object patch {
			def apply(floodlightGroupId: String, partnerId: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v3/floodlightGroups/${floodlightGroupId}").addQueryStringParameters("partnerId" -> partnerId.toString, "updateMask" -> updateMask.toString))
		}
		object floodlightActivities {
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.FloodlightActivity]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.FloodlightActivity])
			}
			object get {
				def apply(floodlightGroupsId :PlayApi, floodlightActivitiesId :PlayApi, floodlightGroupId: String, floodlightActivityId: String, partnerId: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v3/floodlightGroups/${floodlightGroupsId}/floodlightActivities/${floodlightActivitiesId}").addQueryStringParameters("floodlightGroupId" -> floodlightGroupId.toString, "floodlightActivityId" -> floodlightActivityId.toString, "partnerId" -> partnerId.toString))
				given Conversion[get, Future[Schema.FloodlightActivity]] = (fun: get) => fun.apply()
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListFloodlightActivitiesResponse]) {
				/** Optional. Requested page size. Must be between `1` and `100`. If unspecified will default to `100`. Returns error code `INVALID_ARGUMENT` if an invalid value is specified.<br>Format: int32 */
				def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
				/** Optional. A token identifying a page of results the server should return. Typically, this is the value of next_page_token returned from the previous call to `ListFloodlightActivities` method. If not specified, the first page of results will be returned. */
				def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
				/** Optional. Field by which to sort the list. Acceptable values are: &#42; `displayName` (default) &#42; `floodlightActivityId` The default sorting order is ascending. To specify descending order for a field, a suffix "desc" should be added to the field name. Example: `displayName desc`. */
				def withOrderBy(orderBy: String) = new list(req.addQueryStringParameters("orderBy" -> orderBy.toString))
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListFloodlightActivitiesResponse])
			}
			object list {
				def apply(floodlightGroupsId :PlayApi, floodlightGroupId: String, partnerId: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v3/floodlightGroups/${floodlightGroupsId}/floodlightActivities").addQueryStringParameters("floodlightGroupId" -> floodlightGroupId.toString, "partnerId" -> partnerId.toString))
				given Conversion[list, Future[Schema.ListFloodlightActivitiesResponse]] = (fun: list) => fun.apply()
			}
		}
	}
	object googleAudiences {
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleAudience]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleAudience])
		}
		object get {
			def apply(googleAudiencesId :PlayApi, googleAudienceId: String, partnerId: String, advertiserId: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v3/googleAudiences/${googleAudiencesId}").addQueryStringParameters("googleAudienceId" -> googleAudienceId.toString, "partnerId" -> partnerId.toString, "advertiserId" -> advertiserId.toString))
			given Conversion[get, Future[Schema.GoogleAudience]] = (fun: get) => fun.apply()
		}
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListGoogleAudiencesResponse]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListGoogleAudiencesResponse])
		}
		object list {
			def apply(partnerId: String, advertiserId: String, pageSize: Int, pageToken: String, orderBy: String, filter: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v3/googleAudiences").addQueryStringParameters("partnerId" -> partnerId.toString, "advertiserId" -> advertiserId.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "orderBy" -> orderBy.toString, "filter" -> filter.toString))
			given Conversion[list, Future[Schema.ListGoogleAudiencesResponse]] = (fun: list) => fun.apply()
		}
	}
	object guaranteedOrders {
		class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withGuaranteedOrder(body: Schema.GuaranteedOrder) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GuaranteedOrder])
		}
		object create {
			def apply(partnerId: String, advertiserId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v3/guaranteedOrders").addQueryStringParameters("partnerId" -> partnerId.toString, "advertiserId" -> advertiserId.toString))
		}
		class editGuaranteedOrderReadAccessors(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withEditGuaranteedOrderReadAccessorsRequest(body: Schema.EditGuaranteedOrderReadAccessorsRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.EditGuaranteedOrderReadAccessorsResponse])
		}
		object editGuaranteedOrderReadAccessors {
			def apply(guaranteedOrdersId :PlayApi, guaranteedOrderId: String)(using auth: AuthToken, ec: ExecutionContext): editGuaranteedOrderReadAccessors = new editGuaranteedOrderReadAccessors(ws.url(BASE_URL + s"v3/guaranteedOrders/${guaranteedOrdersId}:editGuaranteedOrderReadAccessors").addQueryStringParameters("guaranteedOrderId" -> guaranteedOrderId.toString))
		}
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GuaranteedOrder]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GuaranteedOrder])
		}
		object get {
			def apply(guaranteedOrdersId :PlayApi, guaranteedOrderId: String, partnerId: String, advertiserId: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v3/guaranteedOrders/${guaranteedOrdersId}").addQueryStringParameters("guaranteedOrderId" -> guaranteedOrderId.toString, "partnerId" -> partnerId.toString, "advertiserId" -> advertiserId.toString))
			given Conversion[get, Future[Schema.GuaranteedOrder]] = (fun: get) => fun.apply()
		}
		class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withGuaranteedOrder(body: Schema.GuaranteedOrder) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GuaranteedOrder])
		}
		object patch {
			def apply(guaranteedOrdersId :PlayApi, guaranteedOrderId: String, updateMask: String, partnerId: String, advertiserId: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v3/guaranteedOrders/${guaranteedOrdersId}").addQueryStringParameters("guaranteedOrderId" -> guaranteedOrderId.toString, "updateMask" -> updateMask.toString, "partnerId" -> partnerId.toString, "advertiserId" -> advertiserId.toString))
		}
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListGuaranteedOrdersResponse]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListGuaranteedOrdersResponse])
		}
		object list {
			def apply(partnerId: String, advertiserId: String, pageSize: Int, pageToken: String, orderBy: String, filter: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v3/guaranteedOrders").addQueryStringParameters("partnerId" -> partnerId.toString, "advertiserId" -> advertiserId.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "orderBy" -> orderBy.toString, "filter" -> filter.toString))
			given Conversion[list, Future[Schema.ListGuaranteedOrdersResponse]] = (fun: list) => fun.apply()
		}
	}
	object inventorySourceGroups {
		class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withInventorySourceGroup(body: Schema.InventorySourceGroup) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.InventorySourceGroup])
		}
		object create {
			def apply(partnerId: String, advertiserId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v3/inventorySourceGroups").addQueryStringParameters("partnerId" -> partnerId.toString, "advertiserId" -> advertiserId.toString))
		}
		class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
			def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
		}
		object delete {
			def apply(inventorySourceGroupsId :PlayApi, inventorySourceGroupId: String, partnerId: String, advertiserId: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v3/inventorySourceGroups/${inventorySourceGroupsId}").addQueryStringParameters("inventorySourceGroupId" -> inventorySourceGroupId.toString, "partnerId" -> partnerId.toString, "advertiserId" -> advertiserId.toString))
			given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
		}
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.InventorySourceGroup]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.InventorySourceGroup])
		}
		object get {
			def apply(inventorySourceGroupsId :PlayApi, inventorySourceGroupId: String, partnerId: String, advertiserId: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v3/inventorySourceGroups/${inventorySourceGroupsId}").addQueryStringParameters("inventorySourceGroupId" -> inventorySourceGroupId.toString, "partnerId" -> partnerId.toString, "advertiserId" -> advertiserId.toString))
			given Conversion[get, Future[Schema.InventorySourceGroup]] = (fun: get) => fun.apply()
		}
		class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withInventorySourceGroup(body: Schema.InventorySourceGroup) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.InventorySourceGroup])
		}
		object patch {
			def apply(inventorySourceGroupId: String, partnerId: String, advertiserId: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v3/inventorySourceGroups/${inventorySourceGroupId}").addQueryStringParameters("partnerId" -> partnerId.toString, "advertiserId" -> advertiserId.toString, "updateMask" -> updateMask.toString))
		}
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListInventorySourceGroupsResponse]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListInventorySourceGroupsResponse])
		}
		object list {
			def apply(partnerId: String, advertiserId: String, pageSize: Int, pageToken: String, orderBy: String, filter: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v3/inventorySourceGroups").addQueryStringParameters("partnerId" -> partnerId.toString, "advertiserId" -> advertiserId.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "orderBy" -> orderBy.toString, "filter" -> filter.toString))
			given Conversion[list, Future[Schema.ListInventorySourceGroupsResponse]] = (fun: list) => fun.apply()
		}
		object assignedInventorySources {
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListAssignedInventorySourcesResponse]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListAssignedInventorySourcesResponse])
			}
			object list {
				def apply(inventorySourceGroupsId :PlayApi, inventorySourceGroupId: String, partnerId: String, advertiserId: String, pageSize: Int, pageToken: String, orderBy: String, filter: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v3/inventorySourceGroups/${inventorySourceGroupsId}/assignedInventorySources").addQueryStringParameters("inventorySourceGroupId" -> inventorySourceGroupId.toString, "partnerId" -> partnerId.toString, "advertiserId" -> advertiserId.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "orderBy" -> orderBy.toString, "filter" -> filter.toString))
				given Conversion[list, Future[Schema.ListAssignedInventorySourcesResponse]] = (fun: list) => fun.apply()
			}
			class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withAssignedInventorySource(body: Schema.AssignedInventorySource) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.AssignedInventorySource])
			}
			object create {
				def apply(inventorySourceGroupsId :PlayApi, inventorySourceGroupId: String, partnerId: String, advertiserId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v3/inventorySourceGroups/${inventorySourceGroupsId}/assignedInventorySources").addQueryStringParameters("inventorySourceGroupId" -> inventorySourceGroupId.toString, "partnerId" -> partnerId.toString, "advertiserId" -> advertiserId.toString))
			}
			class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
				def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
			}
			object delete {
				def apply(inventorySourceGroupsId :PlayApi, assignedInventorySourcesId :PlayApi, inventorySourceGroupId: String, assignedInventorySourceId: String, partnerId: String, advertiserId: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v3/inventorySourceGroups/${inventorySourceGroupsId}/assignedInventorySources/${assignedInventorySourcesId}").addQueryStringParameters("inventorySourceGroupId" -> inventorySourceGroupId.toString, "assignedInventorySourceId" -> assignedInventorySourceId.toString, "partnerId" -> partnerId.toString, "advertiserId" -> advertiserId.toString))
				given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
			}
			class bulkEdit(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withBulkEditAssignedInventorySourcesRequest(body: Schema.BulkEditAssignedInventorySourcesRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.BulkEditAssignedInventorySourcesResponse])
			}
			object bulkEdit {
				def apply(inventorySourceGroupsId :PlayApi, inventorySourceGroupId: String)(using auth: AuthToken, ec: ExecutionContext): bulkEdit = new bulkEdit(ws.url(BASE_URL + s"v3/inventorySourceGroups/${inventorySourceGroupsId}/assignedInventorySources:bulkEdit").addQueryStringParameters("inventorySourceGroupId" -> inventorySourceGroupId.toString))
			}
		}
	}
	object inventorySources {
		class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withInventorySource(body: Schema.InventorySource) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.InventorySource])
		}
		object create {
			def apply(partnerId: String, advertiserId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v3/inventorySources").addQueryStringParameters("partnerId" -> partnerId.toString, "advertiserId" -> advertiserId.toString))
		}
		class editInventorySourceReadWriteAccessors(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withEditInventorySourceReadWriteAccessorsRequest(body: Schema.EditInventorySourceReadWriteAccessorsRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.InventorySourceAccessors])
		}
		object editInventorySourceReadWriteAccessors {
			def apply(inventorySourcesId :PlayApi, inventorySourceId: String)(using auth: AuthToken, ec: ExecutionContext): editInventorySourceReadWriteAccessors = new editInventorySourceReadWriteAccessors(ws.url(BASE_URL + s"v3/inventorySources/${inventorySourcesId}:editInventorySourceReadWriteAccessors").addQueryStringParameters("inventorySourceId" -> inventorySourceId.toString))
		}
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.InventorySource]) {
			/** Optional. The ID of the DV360 advertiser to which the fetched inventory source is permissioned. If the user only has access to the advertiser and not the parent partner, use this field to specify the relevant advertiser.<br>Format: int64 */
			def withAdvertiserId(advertiserId: String) = new get(req.addQueryStringParameters("advertiserId" -> advertiserId.toString))
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.InventorySource])
		}
		object get {
			def apply(inventorySourcesId :PlayApi, inventorySourceId: String, partnerId: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v3/inventorySources/${inventorySourcesId}").addQueryStringParameters("inventorySourceId" -> inventorySourceId.toString, "partnerId" -> partnerId.toString))
			given Conversion[get, Future[Schema.InventorySource]] = (fun: get) => fun.apply()
		}
		class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withInventorySource(body: Schema.InventorySource) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.InventorySource])
		}
		object patch {
			def apply(inventorySourcesId :PlayApi, inventorySourceId: String, updateMask: String, partnerId: String, advertiserId: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v3/inventorySources/${inventorySourcesId}").addQueryStringParameters("inventorySourceId" -> inventorySourceId.toString, "updateMask" -> updateMask.toString, "partnerId" -> partnerId.toString, "advertiserId" -> advertiserId.toString))
		}
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListInventorySourcesResponse]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListInventorySourcesResponse])
		}
		object list {
			def apply(partnerId: String, advertiserId: String, pageSize: Int, pageToken: String, orderBy: String, filter: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v3/inventorySources").addQueryStringParameters("partnerId" -> partnerId.toString, "advertiserId" -> advertiserId.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "orderBy" -> orderBy.toString, "filter" -> filter.toString))
			given Conversion[list, Future[Schema.ListInventorySourcesResponse]] = (fun: list) => fun.apply()
		}
	}
	object partners {
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Partner]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Partner])
		}
		object get {
			def apply(partnersId :PlayApi, partnerId: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v3/partners/${partnersId}").addQueryStringParameters("partnerId" -> partnerId.toString))
			given Conversion[get, Future[Schema.Partner]] = (fun: get) => fun.apply()
		}
		class editAssignedTargetingOptions(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withBulkEditPartnerAssignedTargetingOptionsRequest(body: Schema.BulkEditPartnerAssignedTargetingOptionsRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.BulkEditPartnerAssignedTargetingOptionsResponse])
		}
		object editAssignedTargetingOptions {
			def apply(partnersId :PlayApi, partnerId: String)(using auth: AuthToken, ec: ExecutionContext): editAssignedTargetingOptions = new editAssignedTargetingOptions(ws.url(BASE_URL + s"v3/partners/${partnersId}:editAssignedTargetingOptions").addQueryStringParameters("partnerId" -> partnerId.toString))
		}
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListPartnersResponse]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListPartnersResponse])
		}
		object list {
			def apply(pageSize: Int, pageToken: String, orderBy: String, filter: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v3/partners").addQueryStringParameters("pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "orderBy" -> orderBy.toString, "filter" -> filter.toString))
			given Conversion[list, Future[Schema.ListPartnersResponse]] = (fun: list) => fun.apply()
		}
		object channels {
			class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withChannel(body: Schema.Channel) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Channel])
			}
			object create {
				def apply(partnersId :PlayApi, partnerId: String, advertiserId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v3/partners/${partnersId}/channels").addQueryStringParameters("partnerId" -> partnerId.toString, "advertiserId" -> advertiserId.toString))
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Channel]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Channel])
			}
			object get {
				def apply(partnersId :PlayApi, channelsId :PlayApi, partnerId: String, channelId: String, advertiserId: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v3/partners/${partnersId}/channels/${channelsId}").addQueryStringParameters("partnerId" -> partnerId.toString, "channelId" -> channelId.toString, "advertiserId" -> advertiserId.toString))
				given Conversion[get, Future[Schema.Channel]] = (fun: get) => fun.apply()
			}
			class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withChannel(body: Schema.Channel) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Channel])
			}
			object patch {
				def apply(partnersId :PlayApi, partnerId: String, channelId: String, advertiserId: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v3/partners/${partnersId}/channels/${channelId}").addQueryStringParameters("partnerId" -> partnerId.toString, "advertiserId" -> advertiserId.toString, "updateMask" -> updateMask.toString))
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListChannelsResponse]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListChannelsResponse])
			}
			object list {
				def apply(partnersId :PlayApi, partnerId: String, advertiserId: String, pageSize: Int, pageToken: String, orderBy: String, filter: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v3/partners/${partnersId}/channels").addQueryStringParameters("partnerId" -> partnerId.toString, "advertiserId" -> advertiserId.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "orderBy" -> orderBy.toString, "filter" -> filter.toString))
				given Conversion[list, Future[Schema.ListChannelsResponse]] = (fun: list) => fun.apply()
			}
			object sites {
				class replace(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withReplaceSitesRequest(body: Schema.ReplaceSitesRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.ReplaceSitesResponse])
				}
				object replace {
					def apply(channelsId :PlayApi, partnerId: String, channelId: String)(using auth: AuthToken, ec: ExecutionContext): replace = new replace(ws.url(BASE_URL + s"v3/partners/${partnerId}/channels/${channelsId}/sites:replace").addQueryStringParameters("channelId" -> channelId.toString))
				}
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withSite(body: Schema.Site) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Site])
				}
				object create {
					def apply(channelsId :PlayApi, partnerId: String, channelId: String, advertiserId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v3/partners/${partnerId}/channels/${channelsId}/sites").addQueryStringParameters("channelId" -> channelId.toString, "advertiserId" -> advertiserId.toString))
				}
				class bulkEdit(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withBulkEditSitesRequest(body: Schema.BulkEditSitesRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.BulkEditSitesResponse])
				}
				object bulkEdit {
					def apply(channelsId :PlayApi, partnerId: String, channelId: String)(using auth: AuthToken, ec: ExecutionContext): bulkEdit = new bulkEdit(ws.url(BASE_URL + s"v3/partners/${partnerId}/channels/${channelsId}/sites:bulkEdit").addQueryStringParameters("channelId" -> channelId.toString))
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
				}
				object delete {
					def apply(channelsId :PlayApi, sitesId :PlayApi, partnerId: String, channelId: String, urlOrAppId: String, advertiserId: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v3/partners/${partnerId}/channels/${channelsId}/sites/${sitesId}").addQueryStringParameters("channelId" -> channelId.toString, "urlOrAppId" -> urlOrAppId.toString, "advertiserId" -> advertiserId.toString))
					given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListSitesResponse]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListSitesResponse])
				}
				object list {
					def apply(partnersId :PlayApi, channelsId :PlayApi, partnerId: String, channelId: String, advertiserId: String, pageSize: Int, pageToken: String, orderBy: String, filter: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v3/partners/${partnersId}/channels/${channelsId}/sites").addQueryStringParameters("partnerId" -> partnerId.toString, "channelId" -> channelId.toString, "advertiserId" -> advertiserId.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "orderBy" -> orderBy.toString, "filter" -> filter.toString))
					given Conversion[list, Future[Schema.ListSitesResponse]] = (fun: list) => fun.apply()
				}
			}
		}
		object targetingTypes {
			object assignedTargetingOptions {
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.AssignedTargetingOption]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.AssignedTargetingOption])
				}
				object get {
					def apply(partnersId :PlayApi, targetingTypesId :PlayApi, assignedTargetingOptionsId :PlayApi, partnerId: String, targetingType: String, assignedTargetingOptionId: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v3/partners/${partnersId}/targetingTypes/${targetingTypesId}/assignedTargetingOptions/${assignedTargetingOptionsId}").addQueryStringParameters("partnerId" -> partnerId.toString, "targetingType" -> targetingType.toString, "assignedTargetingOptionId" -> assignedTargetingOptionId.toString))
					given Conversion[get, Future[Schema.AssignedTargetingOption]] = (fun: get) => fun.apply()
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListPartnerAssignedTargetingOptionsResponse]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListPartnerAssignedTargetingOptionsResponse])
				}
				object list {
					def apply(partnersId :PlayApi, targetingTypesId :PlayApi, partnerId: String, targetingType: String, pageSize: Int, pageToken: String, orderBy: String, filter: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v3/partners/${partnersId}/targetingTypes/${targetingTypesId}/assignedTargetingOptions").addQueryStringParameters("partnerId" -> partnerId.toString, "targetingType" -> targetingType.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "orderBy" -> orderBy.toString, "filter" -> filter.toString))
					given Conversion[list, Future[Schema.ListPartnerAssignedTargetingOptionsResponse]] = (fun: list) => fun.apply()
				}
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withAssignedTargetingOption(body: Schema.AssignedTargetingOption) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.AssignedTargetingOption])
				}
				object create {
					def apply(partnersId :PlayApi, targetingTypesId :PlayApi, partnerId: String, targetingType: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v3/partners/${partnersId}/targetingTypes/${targetingTypesId}/assignedTargetingOptions").addQueryStringParameters("partnerId" -> partnerId.toString, "targetingType" -> targetingType.toString))
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
				}
				object delete {
					def apply(partnersId :PlayApi, targetingTypesId :PlayApi, assignedTargetingOptionsId :PlayApi, partnerId: String, targetingType: String, assignedTargetingOptionId: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v3/partners/${partnersId}/targetingTypes/${targetingTypesId}/assignedTargetingOptions/${assignedTargetingOptionsId}").addQueryStringParameters("partnerId" -> partnerId.toString, "targetingType" -> targetingType.toString, "assignedTargetingOptionId" -> assignedTargetingOptionId.toString))
					given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
				}
			}
		}
	}
	object sdfdownloadtasks {
		class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withCreateSdfDownloadTaskRequest(body: Schema.CreateSdfDownloadTaskRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
		}
		object create {
			def apply()(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v3/sdfdownloadtasks").addQueryStringParameters())
		}
		object operations {
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Operation])
			}
			object get {
				def apply(operationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v3/sdfdownloadtasks/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.Operation]] = (fun: get) => fun.apply()
			}
		}
	}
	object targetingTypes {
		object targetingOptions {
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.TargetingOption]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.TargetingOption])
			}
			object get {
				def apply(targetingTypesId :PlayApi, targetingOptionsId :PlayApi, targetingType: String, targetingOptionId: String, advertiserId: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v3/targetingTypes/${targetingTypesId}/targetingOptions/${targetingOptionsId}").addQueryStringParameters("targetingType" -> targetingType.toString, "targetingOptionId" -> targetingOptionId.toString, "advertiserId" -> advertiserId.toString))
				given Conversion[get, Future[Schema.TargetingOption]] = (fun: get) => fun.apply()
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListTargetingOptionsResponse]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListTargetingOptionsResponse])
			}
			object list {
				def apply(targetingTypesId :PlayApi, targetingType: String, advertiserId: String, pageSize: Int, pageToken: String, orderBy: String, filter: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v3/targetingTypes/${targetingTypesId}/targetingOptions").addQueryStringParameters("targetingType" -> targetingType.toString, "advertiserId" -> advertiserId.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "orderBy" -> orderBy.toString, "filter" -> filter.toString))
				given Conversion[list, Future[Schema.ListTargetingOptionsResponse]] = (fun: list) => fun.apply()
			}
			class search(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withSearchTargetingOptionsRequest(body: Schema.SearchTargetingOptionsRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.SearchTargetingOptionsResponse])
			}
			object search {
				def apply(targetingTypesId :PlayApi, targetingType: String)(using auth: AuthToken, ec: ExecutionContext): search = new search(ws.url(BASE_URL + s"v3/targetingTypes/${targetingTypesId}/targetingOptions:search").addQueryStringParameters("targetingType" -> targetingType.toString))
			}
		}
	}
	object users {
		class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
			def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
		}
		object delete {
			def apply(usersId :PlayApi, userId: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v3/users/${usersId}").addQueryStringParameters("userId" -> userId.toString))
			given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
		}
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.User]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.User])
		}
		object get {
			def apply(usersId :PlayApi, userId: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v3/users/${usersId}").addQueryStringParameters("userId" -> userId.toString))
			given Conversion[get, Future[Schema.User]] = (fun: get) => fun.apply()
		}
		class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withUser(body: Schema.User) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.User])
		}
		object patch {
			def apply(usersId :PlayApi, userId: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v3/users/${usersId}").addQueryStringParameters("userId" -> userId.toString, "updateMask" -> updateMask.toString))
		}
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListUsersResponse]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListUsersResponse])
		}
		object list {
			def apply(pageSize: Int, pageToken: String, orderBy: String, filter: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v3/users").addQueryStringParameters("pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "orderBy" -> orderBy.toString, "filter" -> filter.toString))
			given Conversion[list, Future[Schema.ListUsersResponse]] = (fun: list) => fun.apply()
		}
		class bulkEditAssignedUserRoles(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withBulkEditAssignedUserRolesRequest(body: Schema.BulkEditAssignedUserRolesRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.BulkEditAssignedUserRolesResponse])
		}
		object bulkEditAssignedUserRoles {
			def apply(usersId :PlayApi, userId: String)(using auth: AuthToken, ec: ExecutionContext): bulkEditAssignedUserRoles = new bulkEditAssignedUserRoles(ws.url(BASE_URL + s"v3/users/${usersId}:bulkEditAssignedUserRoles").addQueryStringParameters("userId" -> userId.toString))
		}
		class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withUser(body: Schema.User) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.User])
		}
		object create {
			def apply()(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v3/users").addQueryStringParameters())
		}
	}
	object media {
		class upload(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withGoogleBytestreamMedia(body: Schema.GoogleBytestreamMedia) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleBytestreamMedia])
		}
		object upload {
			def apply(mediaId :PlayApi, resourceName: String)(using auth: AuthToken, ec: ExecutionContext): upload = new upload(ws.url(BASE_URL + s"media/${mediaId}").addQueryStringParameters("resourceName" -> resourceName.toString))
		}
		class download(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleBytestreamMedia]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleBytestreamMedia])
		}
		object download {
			def apply(downloadId :PlayApi, resourceName: String)(using auth: AuthToken, ec: ExecutionContext): download = new download(ws.url(BASE_URL + s"download/${downloadId}").addQueryStringParameters("resourceName" -> resourceName.toString))
			given Conversion[download, Future[Schema.GoogleBytestreamMedia]] = (fun: download) => fun.apply()
		}
	}
}
