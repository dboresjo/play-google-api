package com.boresjo.play.api.google.authorizedbuyersmarketplace

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	case class AuctionPackage(
	  /** Immutable. The unique identifier for the auction package. Format: `buyers/{accountId}/auctionPackages/{auctionPackageId}` The auction_package_id part of name is sent in the BidRequest to all RTB bidders and is returned as deal_id by the bidder in the BidResponse. */
		name: Option[String] = None,
	  /** Output only. The buyer that created this auction package. Format: `buyers/{buyerAccountId}` */
		creator: Option[String] = None,
	  /** The display_name assigned to the auction package. */
		displayName: Option[String] = None,
	  /** Output only. A description of the auction package. */
		description: Option[String] = None,
	  /** Output only. Time the auction package was created. */
		createTime: Option[String] = None,
	  /** Output only. Time the auction package was last updated. This value is only increased when this auction package is updated but never when a buyer subscribed. */
		updateTime: Option[String] = None,
	  /** Output only. When calling as a buyer, the list of clients of the current buyer that are subscribed to the AuctionPackage. When calling as a bidder, the list of clients that are subscribed to the AuctionPackage owned by the bidder or its buyers. Format: `buyers/{buyerAccountId}/clients/{clientAccountId}` */
		subscribedClients: Option[List[String]] = None,
	  /** Output only. The list of buyers that are subscribed to the AuctionPackage. This field is only populated when calling as a bidder. Format: `buyers/{buyerAccountId}` */
		subscribedBuyers: Option[List[String]] = None,
	  /** Output only. The list of media planners that are subscribed to the AuctionPackage. This field is only populated when calling as a bidder. */
		subscribedMediaPlanners: Option[List[Schema.MediaPlanner]] = None,
	  /** Output only. If set, this field contains the list of DSP specific seat ids set by media planners that are eligible to transact on this deal. The seat ID is in the calling DSP's namespace. */
		eligibleSeatIds: Option[List[String]] = None
	)
	
	case class MediaPlanner(
	  /** Output only. Account ID of the media planner. */
		accountId: Option[String] = None
	)
	
	case class ListAuctionPackagesResponse(
	  /** The list of auction packages. */
		auctionPackages: Option[List[Schema.AuctionPackage]] = None,
	  /** Continuation token for fetching the next page of results. Pass this value in the ListAuctionPackagesRequest.pageToken field in the subsequent call to the `ListAuctionPackages` method to retrieve the next page of results. */
		nextPageToken: Option[String] = None
	)
	
	case class SubscribeAuctionPackageRequest(
	
	)
	
	case class UnsubscribeAuctionPackageRequest(
	
	)
	
	case class SubscribeClientsRequest(
	  /** Optional. A list of client buyers to subscribe to the auction package, with client buyer in the format `buyers/{accountId}/clients/{clientAccountId}`. The current buyer will be subscribed to the auction package regardless of the list contents if not already. */
		clients: Option[List[String]] = None
	)
	
	case class UnsubscribeClientsRequest(
	  /** Optional. A list of client buyers to unsubscribe from the auction package, with client buyer in the format `buyers/{accountId}/clients/{clientAccountId}`. */
		clients: Option[List[String]] = None
	)
	
	object ClientUser {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, INVITED, ACTIVE, INACTIVE }
	}
	case class ClientUser(
	  /** Output only. The resource name of the client user. Format: `buyers/{accountId}/clients/{clientAccountId}/users/{userId}` */
		name: Option[String] = None,
	  /** Output only. The state of the client user. */
		state: Option[Schema.ClientUser.StateEnum] = None,
	  /** Required. The client user's email address that has to be unique across all users for the same client. */
		email: Option[String] = None
	)
	
	case class ListClientUsersResponse(
	  /** The returned list of client users. */
		clientUsers: Option[List[Schema.ClientUser]] = None,
	  /** A token to retrieve the next page of results. Pass this value in the ListClientUsersRequest.pageToken field in the subsequent call to the list method to retrieve the next page of results. */
		nextPageToken: Option[String] = None
	)
	
	case class Empty(
	
	)
	
	case class ActivateClientUserRequest(
	
	)
	
	case class DeactivateClientUserRequest(
	
	)
	
	object Client {
		enum RoleEnum extends Enum[RoleEnum] { case CLIENT_ROLE_UNSPECIFIED, CLIENT_DEAL_VIEWER, CLIENT_DEAL_NEGOTIATOR, CLIENT_DEAL_APPROVER }
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, ACTIVE, INACTIVE }
	}
	case class Client(
	  /** Output only. The resource name of the client. Format: `buyers/{accountId}/clients/{clientAccountId}` */
		name: Option[String] = None,
	  /** Required. The role assigned to the client. Each role implies a set of permissions granted to the client. */
		role: Option[Schema.Client.RoleEnum] = None,
	  /** Output only. The state of the client. */
		state: Option[Schema.Client.StateEnum] = None,
	  /** Required. Display name shown to publishers. Must be unique for clients without partnerClientId specified. Maximum length of 255 characters is allowed. */
		displayName: Option[String] = None,
	  /** Whether the client will be visible to sellers. */
		sellerVisible: Option[Boolean] = None,
	  /** Arbitrary unique identifier provided by the buyer. This field can be used to associate a client with an identifier in the namespace of the buyer, lookup clients by that identifier and verify whether an Authorized Buyers account of the client already exists. If present, must be unique across all the clients. */
		partnerClientId: Option[String] = None
	)
	
	case class ListClientsResponse(
	  /** The returned list of clients. */
		clients: Option[List[Schema.Client]] = None,
	  /** A token to retrieve the next page of results. Pass this value in the ListClientsRequest.pageToken field in the subsequent call to the list method to retrieve the next page of results. */
		nextPageToken: Option[String] = None
	)
	
	case class ActivateClientRequest(
	
	)
	
	case class DeactivateClientRequest(
	
	)
	
	object Deal {
		enum DealTypeEnum extends Enum[DealTypeEnum] { case DEAL_TYPE_UNSPECIFIED, PREFERRED_DEAL, PRIVATE_AUCTION, PROGRAMMATIC_GUARANTEED }
	}
	case class Deal(
	  /** Output only. Refers to a buyer in Real-time Bidding API's Buyer resource. Format: `buyers/{buyerAccountId}` */
		buyer: Option[String] = None,
	  /** Output only. Refers to a Client. Format: `buyers/{buyerAccountId}/clients/{clientAccountid}` */
		client: Option[String] = None,
	  /** Output only. Refers to a buyer in Real-time Bidding API's Buyer resource. This field represents a media planner (For example, agency or big advertiser). */
		mediaPlanner: Option[Schema.MediaPlanner] = None,
	  /** The terms for programmatic guaranteed deals. */
		programmaticGuaranteedTerms: Option[Schema.ProgrammaticGuaranteedTerms] = None,
	  /** The terms for preferred deals. */
		preferredDealTerms: Option[Schema.PreferredDealTerms] = None,
	  /** The terms for private auction deals. */
		privateAuctionTerms: Option[Schema.PrivateAuctionTerms] = None,
	  /** Immutable. The unique identifier of the deal. Auto-generated by the server when a deal is created. Format: buyers/{accountId}/proposals/{proposalId}/deals/{dealId} */
		name: Option[String] = None,
	  /** Output only. The time of the deal creation. */
		createTime: Option[String] = None,
	  /** Output only. The time when the deal was last updated. */
		updateTime: Option[String] = None,
	  /** Output only. The revision number for the proposal and is the same value as proposal.proposal_revision. Each update to deal causes the proposal revision number to auto-increment. The buyer keeps track of the last revision number they know of and pass it in when making an update. If the head revision number on the server has since incremented, then an ABORTED error is returned during the update operation to let the buyer know that a subsequent update was made. */
		proposalRevision: Option[String] = None,
	  /** Output only. The name of the deal. Maximum length of 255 unicode characters is allowed. Control characters are not allowed. Buyers cannot update this field. Note: Not to be confused with name, which is a unique identifier of the deal. */
		displayName: Option[String] = None,
	  /** Output only. When the client field is populated, this field refers to the buyer who creates and manages the client buyer and gets billed on behalf of the client buyer; when the buyer field is populated, this field is the same value as buyer; when the deal belongs to a media planner account, this field will be empty. Format : `buyers/{buyerAccountId}` */
		billedBuyer: Option[String] = None,
	  /** Immutable. Reference to the seller on the deal. Format: `buyers/{buyerAccountId}/publisherProfiles/{publisherProfileId}` */
		publisherProfile: Option[String] = None,
	  /** Output only. Type of deal. */
		dealType: Option[Schema.Deal.DealTypeEnum] = None,
	  /** Specified by buyers in request for proposal (RFP) to notify publisher the total estimated spend for the proposal. Publishers will receive this information and send back proposed deals accordingly. */
		estimatedGrossSpend: Option[Schema.Money] = None,
	  /** Output only. Time zone of the seller used to mark the boundaries of a day for daypart targeting and CPD billing. */
		sellerTimeZone: Option[Schema.TimeZone] = None,
	  /** Output only. Free text description for the deal terms. */
		description: Option[String] = None,
	  /** Proposed flight start time of the deal. This will generally be stored in the granularity of one second since deal serving starts at seconds boundary. Any time specified with more granularity (for example, in milliseconds) will be truncated towards the start of time in seconds. */
		flightStartTime: Option[String] = None,
	  /** Proposed flight end time of the deal. This will generally be stored in a granularity of a second. A value is not necessary for Private Auction deals. */
		flightEndTime: Option[String] = None,
	  /** Specifies the subset of inventory targeted by the deal. Can be updated by the buyer before the deal is finalized. */
		targeting: Option[Schema.MarketplaceTargeting] = None,
	  /** Output only. Metadata about the creatives of this deal. */
		creativeRequirements: Option[Schema.CreativeRequirements] = None,
	  /** Output only. Specifies the pacing set by the publisher. */
		deliveryControl: Option[Schema.DeliveryControl] = None,
	  /** Output only. If set, this field contains the list of DSP specific seat ids set by media planners that are eligible to transact on this deal. The seat ID is in the calling DSP's namespace. */
		eligibleSeatIds: Option[List[String]] = None
	)
	
	object ProgrammaticGuaranteedTerms {
		enum ReservationTypeEnum extends Enum[ReservationTypeEnum] { case RESERVATION_TYPE_UNSPECIFIED, STANDARD, SPONSORSHIP }
	}
	case class ProgrammaticGuaranteedTerms(
	  /** Count of guaranteed looks. For CPD deals, buyer changes to guaranteed_looks will be ignored. */
		guaranteedLooks: Option[String] = None,
	  /** Fixed price for the deal. */
		fixedPrice: Option[Schema.Price] = None,
	  /** Daily minimum looks for CPD deal types. For CPD deals, buyer should negotiate on this field instead of guaranteed_looks. */
		minimumDailyLooks: Option[String] = None,
	  /** The reservation type for a Programmatic Guaranteed deal. This indicates whether the number of impressions is fixed, or a percent of available impressions. If not specified, the default reservation type is STANDARD. */
		reservationType: Option[Schema.ProgrammaticGuaranteedTerms.ReservationTypeEnum] = None,
	  /** The lifetime impression cap for CPM Sponsorship deals. Deal will stop serving when cap is reached. */
		impressionCap: Option[String] = None,
	  /** For sponsorship deals, this is the percentage of the seller's eligible impressions that the deal will serve until the cap is reached. Valid value is within range 0~100. */
		percentShareOfVoice: Option[String] = None
	)
	
	object Price {
		enum TypeEnum extends Enum[TypeEnum] { case TYPE_UNSPECIFIED, CPM, CPD }
	}
	case class Price(
	  /** The pricing type for the deal. */
		`type`: Option[Schema.Price.TypeEnum] = None,
	  /** The actual price with currency specified. */
		amount: Option[Schema.Money] = None
	)
	
	case class Money(
	  /** The three-letter currency code defined in ISO 4217. */
		currencyCode: Option[String] = None,
	  /** The whole units of the amount. For example if `currencyCode` is `"USD"`, then 1 unit is one US dollar. */
		units: Option[String] = None,
	  /** Number of nano (10^-9) units of the amount. The value must be between -999,999,999 and +999,999,999 inclusive. If `units` is positive, `nanos` must be positive or zero. If `units` is zero, `nanos` can be positive, zero, or negative. If `units` is negative, `nanos` must be negative or zero. For example $-1.75 is represented as `units`=-1 and `nanos`=-750,000,000. */
		nanos: Option[Int] = None
	)
	
	case class PreferredDealTerms(
	  /** Fixed price for the deal. */
		fixedPrice: Option[Schema.Price] = None
	)
	
	case class PrivateAuctionTerms(
	  /** The minimum price buyer has to bid to compete in the private auction. */
		floorPrice: Option[Schema.Price] = None,
	  /** Output only. True if open auction buyers are allowed to compete with invited buyers in this private auction. */
		openAuctionAllowed: Option[Boolean] = None
	)
	
	case class TimeZone(
	  /** IANA Time Zone Database time zone, e.g. "America/New_York". */
		id: Option[String] = None,
	  /** Optional. IANA Time Zone Database version number, e.g. "2019a". */
		version: Option[String] = None
	)
	
	case class MarketplaceTargeting(
	  /** Output only. Geo criteria IDs to be included/excluded. */
		geoTargeting: Option[Schema.CriteriaTargeting] = None,
	  /** Output only. Inventory sizes to be included/excluded. */
		inventorySizeTargeting: Option[Schema.InventorySizeTargeting] = None,
	  /** Output only. Technology targeting information, for example, operating system, device category. */
		technologyTargeting: Option[Schema.TechnologyTargeting] = None,
	  /** Output only. Placement targeting information, for example, URL, mobile applications. */
		placementTargeting: Option[Schema.PlacementTargeting] = None,
	  /** Output only. Video targeting information. */
		videoTargeting: Option[Schema.VideoTargeting] = None,
	  /** Buyer user list targeting information. User lists can be uploaded using https://developers.google.com/authorized-buyers/rtb/bulk-uploader. */
		userListTargeting: Option[Schema.CriteriaTargeting] = None,
	  /** Daypart targeting information. */
		daypartTargeting: Option[Schema.DayPartTargeting] = None,
	  /** Output only. Inventory type targeting information. */
		inventoryTypeTargeting: Option[Schema.InventoryTypeTargeting] = None,
	  /** Output only. The verticals included or excluded as defined in https://developers.google.com/authorized-buyers/rtb/downloads/publisher-verticals */
		verticalTargeting: Option[Schema.CriteriaTargeting] = None,
	  /** Output only. The sensitive content category label IDs excluded. Refer to this file https://storage.googleapis.com/adx-rtb-dictionaries/content-labels.txt for category IDs. */
		excludedSensitiveCategoryIds: Option[List[String]] = None
	)
	
	case class CriteriaTargeting(
	  /** A list of numeric IDs to be included. */
		targetedCriteriaIds: Option[List[String]] = None,
	  /** A list of numeric IDs to be excluded. */
		excludedCriteriaIds: Option[List[String]] = None
	)
	
	case class InventorySizeTargeting(
	  /** A list of inventory sizes to be included. */
		targetedInventorySizes: Option[List[Schema.AdSize]] = None,
	  /** A list of inventory sizes to be excluded. */
		excludedInventorySizes: Option[List[Schema.AdSize]] = None
	)
	
	object AdSize {
		enum TypeEnum extends Enum[TypeEnum] { case TYPE_UNSPECIFIED, PIXEL, INTERSTITIAL, NATIVE, FLUID }
	}
	case class AdSize(
	  /** The width of the ad slot in pixels. This field will be present only when size type is `PIXEL`. */
		width: Option[String] = None,
	  /** The height of the ad slot in pixels. This field will be present only when size type is `PIXEL`. */
		height: Option[String] = None,
	  /** The type of the ad slot size. */
		`type`: Option[Schema.AdSize.TypeEnum] = None
	)
	
	case class TechnologyTargeting(
	  /** IDs of device categories to be included/excluded. */
		deviceCategoryTargeting: Option[Schema.CriteriaTargeting] = None,
	  /** IDs of device capabilities to be included/excluded. */
		deviceCapabilityTargeting: Option[Schema.CriteriaTargeting] = None,
	  /** Operating system related targeting information. */
		operatingSystemTargeting: Option[Schema.OperatingSystemTargeting] = None
	)
	
	case class OperatingSystemTargeting(
	  /** IDs of operating systems to be included/excluded. */
		operatingSystemCriteria: Option[Schema.CriteriaTargeting] = None,
	  /** IDs of operating system versions to be included/excluded. */
		operatingSystemVersionCriteria: Option[Schema.CriteriaTargeting] = None
	)
	
	case class PlacementTargeting(
	  /** URLs to be included/excluded. */
		uriTargeting: Option[Schema.UriTargeting] = None,
	  /** Mobile application targeting information in a deal. This doesn't apply to Auction Packages. */
		mobileApplicationTargeting: Option[Schema.MobileApplicationTargeting] = None
	)
	
	case class UriTargeting(
	  /** A list of URLs to be included. */
		targetedUris: Option[List[String]] = None,
	  /** A list of URLs to be excluded. */
		excludedUris: Option[List[String]] = None
	)
	
	case class MobileApplicationTargeting(
	  /** Publisher owned apps to be targeted or excluded by the publisher to display the ads in. */
		firstPartyTargeting: Option[Schema.FirstPartyMobileApplicationTargeting] = None
	)
	
	case class FirstPartyMobileApplicationTargeting(
	  /** A list of application IDs to be included. */
		targetedAppIds: Option[List[String]] = None,
	  /** A list of application IDs to be excluded. */
		excludedAppIds: Option[List[String]] = None
	)
	
	object VideoTargeting {
		enum TargetedPositionTypesEnum extends Enum[TargetedPositionTypesEnum] { case POSITION_TYPE_UNSPECIFIED, PREROLL, MIDROLL, POSTROLL }
		enum ExcludedPositionTypesEnum extends Enum[ExcludedPositionTypesEnum] { case POSITION_TYPE_UNSPECIFIED, PREROLL, MIDROLL, POSTROLL }
	}
	case class VideoTargeting(
	  /** A list of video positions to be included. When this field is populated, the excluded_position_types field must be empty. */
		targetedPositionTypes: Option[List[Schema.VideoTargeting.TargetedPositionTypesEnum]] = None,
	  /** A list of video positions to be excluded. When this field is populated, the targeted_position_types field must be empty. */
		excludedPositionTypes: Option[List[Schema.VideoTargeting.ExcludedPositionTypesEnum]] = None
	)
	
	object DayPartTargeting {
		enum TimeZoneTypeEnum extends Enum[TimeZoneTypeEnum] { case TIME_ZONE_TYPE_UNSPECIFIED, SELLER, USER }
	}
	case class DayPartTargeting(
	  /** The time zone type of the day parts */
		timeZoneType: Option[Schema.DayPartTargeting.TimeZoneTypeEnum] = None,
	  /** The targeted weekdays and times */
		dayParts: Option[List[Schema.DayPart]] = None
	)
	
	object DayPart {
		enum DayOfWeekEnum extends Enum[DayOfWeekEnum] { case DAY_OF_WEEK_UNSPECIFIED, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY }
	}
	case class DayPart(
	  /** Day of week for the period. */
		dayOfWeek: Option[Schema.DayPart.DayOfWeekEnum] = None,
	  /** Hours in 24 hour time between 0 and 24, inclusive. Note: 24 is logically equivalent to 0, but is supported since in some cases there may need to be differentiation made between midnight on one day and midnight on the next day. Accepted values for minutes are [0, 15, 30, 45]. 0 is the only acceptable minute value for hour 24. Seconds and nanos are ignored. */
		startTime: Option[Schema.TimeOfDay] = None,
	  /** Hours in 24 hour time between 0 and 24, inclusive. Note: 24 is logically equivalent to 0, but is supported since in some cases there may need to be differentiation made between midnight on one day and midnight on the next day. Accepted values for minutes are [0, 15, 30, 45]. 0 is the only acceptable minute value for hour 24. Seconds and nanos are ignored. */
		endTime: Option[Schema.TimeOfDay] = None
	)
	
	case class TimeOfDay(
	  /** Hours of a day in 24 hour format. Must be greater than or equal to 0 and typically must be less than or equal to 23. An API may choose to allow the value "24:00:00" for scenarios like business closing time. */
		hours: Option[Int] = None,
	  /** Minutes of an hour. Must be greater than or equal to 0 and less than or equal to 59. */
		minutes: Option[Int] = None,
	  /** Seconds of a minute. Must be greater than or equal to 0 and typically must be less than or equal to 59. An API may allow the value 60 if it allows leap-seconds. */
		seconds: Option[Int] = None,
	  /** Fractions of seconds, in nanoseconds. Must be greater than or equal to 0 and less than or equal to 999,999,999. */
		nanos: Option[Int] = None
	)
	
	object InventoryTypeTargeting {
		enum InventoryTypesEnum extends Enum[InventoryTypesEnum] { case INVENTORY_TYPE_UNSPECIFIED, BROWSER, MOBILE_APP, VIDEO_PLAYER }
	}
	case class InventoryTypeTargeting(
	  /** The list of targeted inventory types for the bid request. */
		inventoryTypes: Option[List[Schema.InventoryTypeTargeting.InventoryTypesEnum]] = None
	)
	
	object CreativeRequirements {
		enum CreativePreApprovalPolicyEnum extends Enum[CreativePreApprovalPolicyEnum] { case CREATIVE_PRE_APPROVAL_POLICY_UNSPECIFIED, SELLER_PRE_APPROVAL_REQUIRED, SELLER_PRE_APPROVAL_NOT_REQUIRED }
		enum CreativeSafeFrameCompatibilityEnum extends Enum[CreativeSafeFrameCompatibilityEnum] { case CREATIVE_SAFE_FRAME_COMPATIBILITY_UNSPECIFIED, COMPATIBLE, INCOMPATIBLE }
		enum ProgrammaticCreativeSourceEnum extends Enum[ProgrammaticCreativeSourceEnum] { case PROGRAMMATIC_CREATIVE_SOURCE_UNSPECIFIED, ADVERTISER, PUBLISHER }
		enum CreativeFormatEnum extends Enum[CreativeFormatEnum] { case CREATIVE_FORMAT_UNSPECIFIED, DISPLAY, VIDEO, AUDIO }
		enum SkippableAdTypeEnum extends Enum[SkippableAdTypeEnum] { case SKIPPABLE_AD_TYPE_UNSPECIFIED, SKIPPABLE, INSTREAM_SELECT, NOT_SKIPPABLE, ANY }
	}
	case class CreativeRequirements(
	  /** Output only. Specifies the creative pre-approval policy. */
		creativePreApprovalPolicy: Option[Schema.CreativeRequirements.CreativePreApprovalPolicyEnum] = None,
	  /** Output only. Specifies whether the creative is safeFrame compatible. */
		creativeSafeFrameCompatibility: Option[Schema.CreativeRequirements.CreativeSafeFrameCompatibilityEnum] = None,
	  /** Output only. Specifies the creative source for programmatic deals. PUBLISHER means creative is provided by seller and ADVERTISER means creative is provided by the buyer. */
		programmaticCreativeSource: Option[Schema.CreativeRequirements.ProgrammaticCreativeSourceEnum] = None,
	  /** Output only. The format of the creative, only applicable for programmatic guaranteed and preferred deals. */
		creativeFormat: Option[Schema.CreativeRequirements.CreativeFormatEnum] = None,
	  /** Output only. Skippable video ads allow viewers to skip ads after 5 seconds. Only applicable for deals with video creatives. */
		skippableAdType: Option[Schema.CreativeRequirements.SkippableAdTypeEnum] = None,
	  /** Output only. The max duration of the video creative in milliseconds. only applicable for deals with video creatives. */
		maxAdDurationMs: Option[String] = None
	)
	
	object DeliveryControl {
		enum DeliveryRateTypeEnum extends Enum[DeliveryRateTypeEnum] { case DELIVERY_RATE_TYPE_UNSPECIFIED, EVENLY, FRONT_LOADED, AS_FAST_AS_POSSIBLE }
		enum RoadblockingTypeEnum extends Enum[RoadblockingTypeEnum] { case ROADBLOCKING_TYPE_UNSPECIFIED, ONLY_ONE, ONE_OR_MORE, AS_MANY_AS_POSSIBLE, ALL_ROADBLOCK, CREATIVE_SET }
		enum CompanionDeliveryTypeEnum extends Enum[CompanionDeliveryTypeEnum] { case COMPANION_DELIVERY_TYPE_UNSPECIFIED, DELIVERY_OPTIONAL, DELIVERY_AT_LEAST_ONE, DELIVERY_ALL }
		enum CreativeRotationTypeEnum extends Enum[CreativeRotationTypeEnum] { case CREATIVE_ROTATION_TYPE_UNSPECIFIED, ROTATION_EVEN, ROTATION_OPTIMIZED, ROTATION_MANUAL, ROTATION_SEQUENTIAL }
	}
	case class DeliveryControl(
	  /** Output only. Specifies how the impression delivery will be paced. */
		deliveryRateType: Option[Schema.DeliveryControl.DeliveryRateTypeEnum] = None,
	  /** Output only. Specifies any frequency caps. Cannot be filtered within ListDealsRequest. */
		frequencyCap: Option[List[Schema.FrequencyCap]] = None,
	  /** Output only. Specifies the roadblocking type in display creatives. */
		roadblockingType: Option[Schema.DeliveryControl.RoadblockingTypeEnum] = None,
	  /** Output only. Specifies roadblocking in a main companion lineitem. */
		companionDeliveryType: Option[Schema.DeliveryControl.CompanionDeliveryTypeEnum] = None,
	  /** Output only. Specifies strategy to use for selecting a creative when multiple creatives of the same size are available. */
		creativeRotationType: Option[Schema.DeliveryControl.CreativeRotationTypeEnum] = None
	)
	
	object FrequencyCap {
		enum TimeUnitTypeEnum extends Enum[TimeUnitTypeEnum] { case TIME_UNIT_TYPE_UNSPECIFIED, MINUTE, HOUR, DAY, WEEK, MONTH, LIFETIME, POD, STREAM }
	}
	case class FrequencyCap(
	  /** The maximum number of impressions that can be served to a user within the specified time period. */
		maxImpressions: Option[Int] = None,
	  /** The amount of time, in the units specified by time_unit_type. Defines the amount of time over which impressions per user are counted and capped. */
		timeUnitsCount: Option[Int] = None,
	  /** The time unit. Along with num_time_units defines the amount of time over which impressions per user are counted and capped. */
		timeUnitType: Option[Schema.FrequencyCap.TimeUnitTypeEnum] = None
	)
	
	case class BatchUpdateDealsRequest(
	  /** Required. List of request messages to update deals. */
		requests: Option[List[Schema.UpdateDealRequest]] = None
	)
	
	case class UpdateDealRequest(
	  /** Required. The deal to update. The deal's `name` field is used to identify the deal to be updated. Note: proposal_revision will have to be provided within the resource or else an error will be thrown. Format: buyers/{accountId}/proposals/{proposalId}/deals/{dealId} */
		deal: Option[Schema.Deal] = None,
	  /** List of fields to be updated. If empty or unspecified, the service will update all fields populated in the update request excluding the output only fields and primitive fields with default value. Note that explicit field mask is required in order to reset a primitive field back to its default value, for example, false for boolean fields, 0 for integer fields. A special field mask consisting of a single path "&#42;" can be used to indicate full replacement(the equivalent of PUT method), updatable fields unset or unspecified in the input will be cleared or set to default value. Output only fields will be ignored regardless of the value of updateMask. */
		updateMask: Option[String] = None
	)
	
	case class BatchUpdateDealsResponse(
	  /** Deals updated. */
		deals: Option[List[Schema.Deal]] = None
	)
	
	case class ListDealsResponse(
	  /** The list of deals. */
		deals: Option[List[Schema.Deal]] = None,
	  /** Token to fetch the next page of results. */
		nextPageToken: Option[String] = None
	)
	
	object FinalizedDeal {
		enum DealServingStatusEnum extends Enum[DealServingStatusEnum] { case DEAL_SERVING_STATUS_UNSPECIFIED, ACTIVE, ENDED, PAUSED_BY_BUYER, PAUSED_BY_SELLER }
	}
	case class FinalizedDeal(
	  /** The resource name of the finalized deal. Format: `buyers/{accountId}/finalizedDeals/{finalizedDealId}` */
		name: Option[String] = None,
	  /** A copy of the Deal made upon finalization. During renegotiation, this will reflect the last finalized deal before renegotiation was initiated. */
		deal: Option[Schema.Deal] = None,
	  /** Serving status of the deal. */
		dealServingStatus: Option[Schema.FinalizedDeal.DealServingStatusEnum] = None,
	  /** Information related to deal pausing for the deal. */
		dealPausingInfo: Option[Schema.DealPausingInfo] = None,
	  /** Real-time bidding metrics for this deal. */
		rtbMetrics: Option[Schema.RtbMetrics] = None,
	  /** Whether the Programmatic Guaranteed deal is ready for serving. */
		readyToServe: Option[Boolean] = None
	)
	
	object DealPausingInfo {
		enum PauseRoleEnum extends Enum[PauseRoleEnum] { case BUYER_SELLER_ROLE_UNSPECIFIED, BUYER, SELLER }
	}
	case class DealPausingInfo(
	  /** Whether pausing is consented between buyer and seller for the deal. */
		pausingConsented: Option[Boolean] = None,
	  /** The party that first paused the deal; unspecified for active deals. */
		pauseRole: Option[Schema.DealPausingInfo.PauseRoleEnum] = None,
	  /** The reason for the pausing of the deal; empty for active deals. */
		pauseReason: Option[String] = None
	)
	
	case class RtbMetrics(
	  /** Bid requests in last 7 days. */
		bidRequests7Days: Option[String] = None,
	  /** Bids in last 7 days. */
		bids7Days: Option[String] = None,
	  /** Ad impressions in last 7 days. */
		adImpressions7Days: Option[String] = None,
	  /** Bid rate in last 7 days, calculated by (bids / bid requests). */
		bidRate7Days: Option[BigDecimal] = None,
	  /** Filtered bid rate in last 7 days, calculated by (filtered bids / bids). */
		filteredBidRate7Days: Option[BigDecimal] = None,
	  /** Must bid rate for current month. */
		mustBidRateCurrentMonth: Option[BigDecimal] = None
	)
	
	case class ListFinalizedDealsResponse(
	  /** The list of finalized deals. */
		finalizedDeals: Option[List[Schema.FinalizedDeal]] = None,
	  /** Token to fetch the next page of results. */
		nextPageToken: Option[String] = None
	)
	
	case class PauseFinalizedDealRequest(
	  /** The reason to pause the finalized deal, will be displayed to the seller. Maximum length is 1000 characters. */
		reason: Option[String] = None
	)
	
	case class ResumeFinalizedDealRequest(
	
	)
	
	case class AddCreativeRequest(
	  /** Name of the creative to add to the finalized deal, in the format `buyers/{buyerAccountId}/creatives/{creativeId}`. See creative.name. */
		creative: Option[String] = None
	)
	
	case class SetReadyToServeRequest(
	
	)
	
	object Proposal {
		enum DealTypeEnum extends Enum[DealTypeEnum] { case DEAL_TYPE_UNSPECIFIED, PREFERRED_DEAL, PRIVATE_AUCTION, PROGRAMMATIC_GUARANTEED }
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, BUYER_REVIEW_REQUESTED, SELLER_REVIEW_REQUESTED, BUYER_ACCEPTANCE_REQUESTED, FINALIZED, TERMINATED }
		enum OriginatorRoleEnum extends Enum[OriginatorRoleEnum] { case BUYER_SELLER_ROLE_UNSPECIFIED, BUYER, SELLER }
		enum LastUpdaterOrCommentorRoleEnum extends Enum[LastUpdaterOrCommentorRoleEnum] { case BUYER_SELLER_ROLE_UNSPECIFIED, BUYER, SELLER }
	}
	case class Proposal(
	  /** Output only. Refers to a buyer in The Realtime-bidding API. Format: `buyers/{buyerAccountId}` */
		buyer: Option[String] = None,
	  /** Output only. Refers to a Client. Format: `buyers/{buyerAccountId}/clients/{clientAccountid}` */
		client: Option[String] = None,
	  /** Immutable. The name of the proposal serving as a unique identifier. Format: buyers/{accountId}/proposals/{proposalId} */
		name: Option[String] = None,
	  /** Output only. The time when the proposal was last revised. */
		updateTime: Option[String] = None,
	  /** Output only. The revision number for the proposal. Each update to the proposal or deal causes the proposal revision number to auto-increment. The buyer keeps track of the last revision number they know of and pass it in when making an update. If the head revision number on the server has since incremented, then an ABORTED error is returned during the update operation to let the buyer know that a subsequent update was made. */
		proposalRevision: Option[String] = None,
	  /** Output only. Type of deal the proposal contains. */
		dealType: Option[Schema.Proposal.DealTypeEnum] = None,
	  /** Output only. The descriptive name for the proposal. Maximum length of 255 unicode characters is allowed. Control characters are not allowed. Buyers cannot update this field. Note: Not to be confused with name, which is a unique identifier of the proposal. */
		displayName: Option[String] = None,
	  /** Output only. Indicates the state of the proposal. */
		state: Option[Schema.Proposal.StateEnum] = None,
	  /** Output only. True if the proposal was previously finalized and is now being renegotiated. */
		isRenegotiating: Option[Boolean] = None,
	  /** Output only. Indicates whether the buyer/seller created the proposal. */
		originatorRole: Option[Schema.Proposal.OriginatorRoleEnum] = None,
	  /** Immutable. Reference to the seller on the proposal. Format: `buyers/{buyerAccountId}/publisherProfiles/{publisherProfileId}` Note: This field may be set only when creating the resource. Modifying this field while updating the resource will result in an error. */
		publisherProfile: Option[String] = None,
	  /** Buyer private data (hidden from seller). */
		buyerPrivateData: Option[Schema.PrivateData] = None,
	  /** Output only. When the client field is populated, this field refers to the buyer who creates and manages the client buyer and gets billed on behalf of the client buyer; when the buyer field is populated, this field is the same value as buyer. Format : `buyers/{buyerAccountId}` */
		billedBuyer: Option[String] = None,
	  /** Output only. Contact information for the seller. */
		sellerContacts: Option[List[Schema.Contact]] = None,
	  /** Contact information for the buyer. */
		buyerContacts: Option[List[Schema.Contact]] = None,
	  /** Output only. The role of the last user that either updated the proposal or left a comment. */
		lastUpdaterOrCommentorRole: Option[Schema.Proposal.LastUpdaterOrCommentorRoleEnum] = None,
	  /** Output only. The terms and conditions associated with this proposal. Accepting a proposal implies acceptance of this field. This is created by the seller, the buyer can only view it. */
		termsAndConditions: Option[String] = None,
	  /** Whether pausing is allowed for the proposal. This is a negotiable term between buyers and publishers. */
		pausingConsented: Option[Boolean] = None,
	  /** A list of notes from the buyer and the seller attached to this proposal. */
		notes: Option[List[Schema.Note]] = None
	)
	
	case class PrivateData(
	  /** A buyer specified reference ID. This can be queried in the list operations (max-length: 1024 unicode code units). */
		referenceId: Option[String] = None
	)
	
	case class Contact(
	  /** Email address for the contact. */
		email: Option[String] = None,
	  /** The display_name of the contact. */
		displayName: Option[String] = None
	)
	
	object Note {
		enum CreatorRoleEnum extends Enum[CreatorRoleEnum] { case BUYER_SELLER_ROLE_UNSPECIFIED, BUYER, SELLER }
	}
	case class Note(
	  /** Output only. When this note was created. */
		createTime: Option[String] = None,
	  /** Output only. The role who created the note. */
		creatorRole: Option[Schema.Note.CreatorRoleEnum] = None,
	  /** The text of the note. Maximum length is 1024 characters. */
		note: Option[String] = None
	)
	
	case class ListProposalsResponse(
	  /** The list of proposals. */
		proposals: Option[List[Schema.Proposal]] = None,
	  /** Continuation token for fetching the next page of results. */
		nextPageToken: Option[String] = None
	)
	
	case class CancelNegotiationRequest(
	
	)
	
	case class AcceptProposalRequest(
	  /** The last known client revision number of the proposal. */
		proposalRevision: Option[String] = None
	)
	
	case class AddNoteRequest(
	  /** The note to add. */
		note: Option[Schema.Note] = None
	)
	
	case class SendRfpRequest(
	  /** The terms for programmatic guaranteed deals. */
		programmaticGuaranteedTerms: Option[Schema.ProgrammaticGuaranteedTerms] = None,
	  /** The terms for preferred deals. */
		preferredDealTerms: Option[Schema.PreferredDealTerms] = None,
	  /** Required. The display name of the proposal being created by this RFP. */
		displayName: Option[String] = None,
	  /** If the current buyer is sending the RFP on behalf of its client, use this field to specify the name of the client in the format: `buyers/{accountId}/clients/{clientAccountid}`. */
		client: Option[String] = None,
	  /** Required. The profile of the publisher who will receive this RFP in the format: `buyers/{accountId}/publisherProfiles/{publisherProfileId}`. */
		publisherProfile: Option[String] = None,
	  /** Contact information for the buyer. */
		buyerContacts: Option[List[Schema.Contact]] = None,
	  /** A message that is sent to the publisher. Maximum length is 1024 characters. */
		note: Option[String] = None,
	  /** Geo criteria IDs to be targeted. Refer to Geo tables. */
		geoTargeting: Option[Schema.CriteriaTargeting] = None,
	  /** Inventory sizes to be targeted. */
		inventorySizeTargeting: Option[Schema.InventorySizeTargeting] = None,
	  /** Specified by buyers in request for proposal (RFP) to notify publisher the total estimated spend for the proposal. Publishers will receive this information and send back proposed deals accordingly. */
		estimatedGrossSpend: Option[Schema.Money] = None,
	  /** Required. Proposed flight start time of the RFP. A timestamp in RFC3339 UTC "Zulu" format. Note that the specified value will be truncated to a granularity of one second. */
		flightStartTime: Option[String] = None,
	  /** Required. Proposed flight end time of the RFP. A timestamp in RFC3339 UTC "Zulu" format. Note that the specified value will be truncated to a granularity of one second. */
		flightEndTime: Option[String] = None
	)
	
	case class PublisherProfile(
	  /** Name of the publisher profile. Format: `buyers/{buyer}/publisherProfiles/{publisher_profile}` */
		name: Option[String] = None,
	  /** Display name of the publisher profile. Can be used to filter the response of the publisherProfiles.list method. */
		displayName: Option[String] = None,
	  /** The list of domains represented in this publisher profile. Empty if this is a parent profile. These are top private domains, meaning that these will not contain a string like "photos.google.co.uk/123", but will instead contain "google.co.uk". Can be used to filter the response of the publisherProfiles.list method. */
		domains: Option[List[String]] = None,
	  /** The list of apps represented in this publisher profile. Empty if this is a parent profile. */
		mobileApps: Option[List[Schema.PublisherProfileMobileApplication]] = None,
	  /** A Google public URL to the logo for this publisher profile. The logo is stored as a PNG, JPG, or GIF image. */
		logoUrl: Option[String] = None,
	  /** Contact information for direct reservation deals. This is free text entered by the publisher and may include information like names, phone numbers and email addresses. */
		directDealsContact: Option[String] = None,
	  /** Contact information for programmatic deals. This is free text entered by the publisher and may include information like names, phone numbers and email addresses. */
		programmaticDealsContact: Option[String] = None,
	  /** URL to additional marketing and sales materials. */
		mediaKitUrl: Option[String] = None,
	  /** URL to a sample content page. */
		samplePageUrl: Option[String] = None,
	  /** Overview of the publisher. */
		overview: Option[String] = None,
	  /** Statement explaining what's unique about publisher's business, and why buyers should partner with the publisher. */
		pitchStatement: Option[String] = None,
	  /** Up to three key metrics and rankings. For example, "#1 Mobile News Site for 20 Straight Months". */
		topHeadlines: Option[List[String]] = None,
	  /** Description on the publisher's audience. */
		audienceDescription: Option[String] = None,
	  /** Indicates if this profile is the parent profile of the seller. A parent profile represents all the inventory from the seller, as opposed to child profile that is created to brand a portion of inventory. One seller has only one parent publisher profile, and can have multiple child profiles. See https://support.google.com/admanager/answer/6035806 for details. Can be used to filter the response of the publisherProfiles.list method by setting the filter to "is_parent: true". */
		isParent: Option[Boolean] = None,
	  /** A unique identifying code for the seller. This value is the same for all of the seller's parent and child publisher profiles. Can be used to filter the response of the publisherProfiles.list method. */
		publisherCode: Option[String] = None
	)
	
	object PublisherProfileMobileApplication {
		enum AppStoreEnum extends Enum[AppStoreEnum] { case APP_STORE_TYPE_UNSPECIFIED, APPLE_ITUNES, GOOGLE_PLAY, ROKU, AMAZON_FIRE_TV, PLAYSTATION, XBOX, SAMSUNG_TV, AMAZON, OPPO, SAMSUNG, VIVO, XIAOMI, LG_TV }
	}
	case class PublisherProfileMobileApplication(
	  /** The external ID for the app from its app store. Can be used to filter the response of the publisherProfiles.list method. */
		externalAppId: Option[String] = None,
	  /** The name of the app. */
		name: Option[String] = None,
	  /** The app store the app belongs to. Can be used to filter the response of the publisherProfiles.list method. */
		appStore: Option[Schema.PublisherProfileMobileApplication.AppStoreEnum] = None
	)
	
	case class ListPublisherProfilesResponse(
	  /** The list of matching publisher profiles. */
		publisherProfiles: Option[List[Schema.PublisherProfile]] = None,
	  /** Token to fetch the next page of results. */
		nextPageToken: Option[String] = None
	)
}
