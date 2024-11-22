package com.boresjo.play.api.google.walletobjects

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	case class PurchaseDetails(
	  /** The confirmation code for the purchase. This may be the same for multiple different tickets and is used to group tickets together. */
		confirmationCode: Option[String] = None,
	  /** Receipt number/identifier for tracking the ticket purchase via the body that sold the ticket. */
		purchaseReceiptNumber: Option[String] = None,
	  /** ID of the account used to purchase the ticket. */
		accountId: Option[String] = None,
	  /** The cost of the ticket. */
		ticketCost: Option[Schema.TicketCost] = None,
	  /** The purchase date/time of the ticket. This is an ISO 8601 extended format date/time, with or without an offset. Time may be specified up to nanosecond precision. Offsets may be specified with seconds precision (even though offset seconds is not part of ISO 8601). For example: `1985-04-12T23:20:50.52Z` would be 20 minutes and 50.52 seconds after the 23rd hour of April 12th, 1985 in UTC. `1985-04-12T19:20:50.52-04:00` would be 20 minutes and 50.52 seconds after the 19th hour of April 12th, 1985, 4 hours before UTC (same instant in time as the above example). If the event were in New York, this would be the equivalent of Eastern Daylight Time (EDT). Remember that offset varies in regions that observe Daylight Saving Time (or Summer Time), depending on the time of the year. `1985-04-12T19:20:50.52` would be 20 minutes and 50.52 seconds after the 19th hour of April 12th, 1985 with no offset information. Without offset information, some rich features may not be available. */
		purchaseDateTime: Option[String] = None
	)
	
	case class LoyaltyPoints(
	  /** The account holder's loyalty point balance, such as "500" or "$10.00". Recommended maximum length is 7 characters. This is a required field of `loyaltyPoints` and `secondaryLoyaltyPoints`. */
		balance: Option[Schema.LoyaltyPointsBalance] = None,
	  /** The loyalty points label, such as "Points". Recommended maximum length is 9 characters. */
		label: Option[String] = None,
	  /** Translated strings for the label. Recommended maximum length is 9 characters. */
		localizedLabel: Option[Schema.LocalizedString] = None
	)
	
	case class LatLongPoint(
	  /** The longitude specified in the range -180.0 through +180.0, both inclusive. Values outside these bounds will be rejected. */
		longitude: Option[BigDecimal] = None,
	  /** Identifies what kind of resource this is. Value: the fixed string `"walletobjects#latLongPoint"`. */
		kind: Option[String] = None,
	  /** The latitude specified as any value in the range of -90.0 through +90.0, both inclusive. Values outside these bounds will be rejected. */
		latitude: Option[BigDecimal] = None
	)
	
	case class OfferClassAddMessageResponse(
	  /** The updated OfferClass resource. */
		resource: Option[Schema.OfferClass] = None
	)
	
	case class ObjectId(
	  /** The name of the object. */
		objectName: Option[String] = None,
	  /** Generation of the object. Generations are monotonically increasing across writes, allowing them to be be compared to determine which generation is newer. If this is omitted in a request, then you are requesting the live object. See http://go/bigstore-versions */
		generation: Option[String] = None,
	  /** The name of the bucket to which this object belongs. */
		bucketName: Option[String] = None
	)
	
	case class AirportInfo(
	  /** Optional field that overrides the airport city name defined by IATA. By default, Google takes the `airportIataCode` provided and maps it to the official airport city name defined by IATA. Official IATA airport city names can be found at IATA airport city names website. For example, for the airport IATA code "LTN", IATA website tells us that the corresponding airport city is "London". If this field is not populated, Google would display "London". However, populating this field with a custom name (eg: "London Luton") would override it. */
		airportNameOverride: Option[Schema.LocalizedString] = None,
	  /** Terminal name. Eg: "INTL" or "I" */
		terminal: Option[String] = None,
	  /** Identifies what kind of resource this is. Value: the fixed string `"walletobjects#airportInfo"`. */
		kind: Option[String] = None,
	  /** Three character IATA airport code. This is a required field for `origin` and `destination`. Eg: "SFO" */
		airportIataCode: Option[String] = None,
	  /** A name of the gate. Eg: "B59" or "59" */
		gate: Option[String] = None
	)
	
	case class IssuerListResponse(
	  /** Resources corresponding to the list request. */
		resources: Option[List[Schema.Issuer]] = None
	)
	
	case class Review(
		comments: Option[String] = None
	)
	
	case class EventTicketClassAddMessageResponse(
	  /** The updated EventTicketClass resource. */
		resource: Option[Schema.EventTicketClass] = None
	)
	
	object EventTicketObject {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, ACTIVE, active, COMPLETED, completed, EXPIRED, expired, INACTIVE, inactive }
		enum NotifyPreferenceEnum extends Enum[NotifyPreferenceEnum] { case NOTIFICATION_SETTINGS_FOR_UPDATES_UNSPECIFIED, NOTIFY_ON_UPDATE }
	}
	case class EventTicketObject(
	  /** The background color for the card. If not set the dominant color of the hero image is used, and if no hero image is set, the dominant color of the logo is used. The format is #rrggbb where rrggbb is a hex RGB triplet, such as `#ffcc00`. You can also use the shorthand version of the RGB triplet which is #rgb, such as `#fc0`. */
		hexBackgroundColor: Option[String] = None,
	  /** The type of the ticket, such as "Adult" or "Child", or "VIP" or "Standard". */
		ticketType: Option[Schema.LocalizedString] = None,
	  /** Seating details for this ticket. */
		seatInfo: Option[Schema.EventSeat] = None,
	  /** Pass constraints for the object. Includes limiting NFC and screenshot behaviors. */
		passConstraints: Option[Schema.PassConstraints] = None,
	  /** Text module data. If text module data is also defined on the class, both will be displayed. The maximum number of these fields displayed is 10 from the object and 10 from the class. */
		textModulesData: Option[List[Schema.TextModuleData]] = None,
	  /** Name of the ticket holder, if the ticket is assigned to a person. E.g. "John Doe" or "Jane Doe". */
		ticketHolderName: Option[String] = None,
	  /** The number of the ticket. This can be a unique identifier across all tickets in an issuer's system, all tickets for the event (e.g. XYZ1234512345), or all tickets in the order (1, 2, 3, etc.). */
		ticketNumber: Option[String] = None,
	  /** linked_object_ids are a list of other objects such as event ticket, loyalty, offer, generic, giftcard, transit and boarding pass that should be automatically attached to this event ticket object. If a user had saved this event ticket, then these linked_object_ids would be automatically pushed to the user's wallet (unless they turned off the setting to receive such linked passes). Make sure that objects present in linked_object_ids are already inserted - if not, calls would fail. Once linked, the linked objects cannot be unlinked. You cannot link objects belonging to another issuer. There is a limit to the number of objects that can be linked to a single object. After the limit is reached, new linked objects in the call will be ignored silently. Object IDs should follow the format issuer ID. identifier where the former is issued by Google and the latter is chosen by you. */
		linkedObjectIds: Option[List[String]] = None,
	  /** Optional app or website link that will be displayed as a button on the front of the pass. If AppLinkData is provided for the corresponding class only object AppLinkData will be displayed. */
		appLinkData: Option[Schema.AppLinkData] = None,
	  /** Optional value added module data. Maximum of ten on the object. */
		valueAddedModuleData: Option[List[Schema.ValueAddedModuleData]] = None,
	  /** A copy of the inherited fields of the parent class. These fields are retrieved during a GET. */
		classReference: Option[Schema.EventTicketClass] = None,
	  /** Whether this object is currently linked to a single device. This field is set by the platform when a user saves the object, linking it to their device. Intended for use by select partners. Contact support for additional information. */
		hasLinkedDevice: Option[Boolean] = None,
	  /** Restrictions on the object that needs to be verified before the user tries to save the pass. Note that this restrictions will only be applied during save time. If the restrictions changed after a user saves the pass, the new restrictions will not be applied to an already saved pass. */
		saveRestrictions: Option[Schema.SaveRestrictions] = None,
	  /** Image module data. The maximum number of these fields displayed is 1 from object level and 1 for class object level. */
		imageModulesData: Option[List[Schema.ImageModuleData]] = None,
	  /** The time period this object will be `active` and object can be used. An object's state will be changed to `expired` when this time period has passed. */
		validTimeInterval: Option[Schema.TimeInterval] = None,
	  /** Note: This field is currently not supported to trigger geo notifications. */
		locations: Option[List[Schema.LatLongPoint]] = None,
	  /** The rotating barcode type and value. */
		rotatingBarcode: Option[Schema.RotatingBarcode] = None,
	  /** Deprecated */
		version: Option[String] = None,
	  /** Optional banner image displayed on the front of the card. If none is present, hero image of the class, if present, will be displayed. If hero image of the class is also not present, nothing will be displayed. */
		heroImage: Option[Schema.Image] = None,
	  /** Deprecated. Use textModulesData instead. */
		infoModuleData: Option[Schema.InfoModuleData] = None,
	  /** Indicates if the object has users. This field is set by the platform. */
		hasUsers: Option[Boolean] = None,
	  /** Information that controls how passes are grouped together. */
		groupingInfo: Option[Schema.GroupingInfo] = None,
	  /** Required. The state of the object. This field is used to determine how an object is displayed in the app. For example, an `inactive` object is moved to the "Expired passes" section. */
		state: Option[Schema.EventTicketObject.StateEnum] = None,
	  /** Indicates if notifications should explicitly be suppressed. If this field is set to true, regardless of the `messages` field, expiration notifications to the user will be suppressed. By default, this field is set to false. Currently, this can only be set for offers. */
		disableExpirationNotification: Option[Boolean] = None,
	  /** Identifies what kind of resource this is. Value: the fixed string `"walletobjects#eventTicketObject"`. */
		kind: Option[String] = None,
	  /** Links module data. If links module data is also defined on the class, both will be displayed. */
		linksModuleData: Option[Schema.LinksModuleData] = None,
	  /** An array of messages displayed in the app. All users of this object will receive its associated messages. The maximum number of these fields is 10. */
		messages: Option[List[Schema.Message]] = None,
	  /** Whether or not field updates to this object should trigger notifications. When set to NOTIFY, we will attempt to trigger a field update notification to users. These notifications will only be sent to users if the field is part of an allowlist. If set to DO_NOT_NOTIFY or NOTIFICATION_SETTINGS_UNSPECIFIED, no notification will be triggered. This setting is ephemeral and needs to be set with each PATCH or UPDATE request, otherwise a notification will not be triggered. */
		notifyPreference: Option[Schema.EventTicketObject.NotifyPreferenceEnum] = None,
	  /** A list of offer objects linked to this event ticket. The offer objects must already exist. Offer object IDs should follow the format issuer ID. identifier where the former is issued by Google and latter is chosen by you. */
		linkedOfferIds: Option[List[String]] = None,
	  /** Required. The unique identifier for an object. This ID must be unique across all objects from an issuer. This value should follow the format issuer ID.identifier where the former is issued by Google and latter is chosen by you. The unique identifier should only include alphanumeric characters, '.', '_', or '-'. */
		id: Option[String] = None,
	  /** Required. The class associated with this object. The class must be of the same type as this object, must already exist, and must be approved. Class IDs should follow the format issuer ID.identifier where the former is issued by Google and latter is chosen by you. */
		classId: Option[String] = None,
	  /** The value that will be transmitted to a Smart Tap certified terminal over NFC for this object. The class level fields `enableSmartTap` and `redemptionIssuers` must also be set up correctly in order for the pass to support Smart Tap. Only ASCII characters are supported. */
		smartTapRedemptionValue: Option[String] = None,
	  /** The face value of the ticket, matching what would be printed on a physical version of the ticket. */
		faceValue: Option[Schema.Money] = None,
	  /** Reservation details for this ticket. This is expected to be shared amongst all tickets that were purchased in the same order. */
		reservationInfo: Option[Schema.EventReservationInfo] = None,
	  /** The barcode type and value. */
		barcode: Option[Schema.Barcode] = None
	)
	
	case class DetailsTemplateOverride(
	  /** Information for the "nth" item displayed in the details list. */
		detailsItemInfos: Option[List[Schema.DetailsItemInfo]] = None
	)
	
	case class GiftCardClassAddMessageResponse(
	  /** The updated GiftCardClass resource. */
		resource: Option[Schema.GiftCardClass] = None
	)
	
	object LoyaltyObject {
		enum NotifyPreferenceEnum extends Enum[NotifyPreferenceEnum] { case NOTIFICATION_SETTINGS_FOR_UPDATES_UNSPECIFIED, NOTIFY_ON_UPDATE }
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, ACTIVE, active, COMPLETED, completed, EXPIRED, expired, INACTIVE, inactive }
	}
	case class LoyaltyObject(
	  /** The rotating barcode type and value. */
		rotatingBarcode: Option[Schema.RotatingBarcode] = None,
	  /** Note: This field is currently not supported to trigger geo notifications. */
		locations: Option[List[Schema.LatLongPoint]] = None,
	  /** Image module data. The maximum number of these fields displayed is 1 from object level and 1 for class object level. */
		imageModulesData: Option[List[Schema.ImageModuleData]] = None,
	  /** Indicates if notifications should explicitly be suppressed. If this field is set to true, regardless of the `messages` field, expiration notifications to the user will be suppressed. By default, this field is set to false. Currently, this can only be set for offers. */
		disableExpirationNotification: Option[Boolean] = None,
	  /** Links module data. If links module data is also defined on the class, both will be displayed. */
		linksModuleData: Option[Schema.LinksModuleData] = None,
	  /** Information that controls how passes are grouped together. */
		groupingInfo: Option[Schema.GroupingInfo] = None,
	  /** The barcode type and value. */
		barcode: Option[Schema.Barcode] = None,
	  /** Identifies what kind of resource this is. Value: the fixed string `"walletobjects#loyaltyObject"`. */
		kind: Option[String] = None,
	  /** The loyalty account identifier. Recommended maximum length is 20 characters. */
		accountId: Option[String] = None,
	  /** The time period this object will be `active` and object can be used. An object's state will be changed to `expired` when this time period has passed. */
		validTimeInterval: Option[Schema.TimeInterval] = None,
	  /** The value that will be transmitted to a Smart Tap certified terminal over NFC for this object. The class level fields `enableSmartTap` and `redemptionIssuers` must also be set up correctly in order for the pass to support Smart Tap. Only ASCII characters are supported. If this value is not set but the class level fields `enableSmartTap` and `redemptionIssuers` are set up correctly, the `barcode.value` or the `accountId` fields are used as fallback if present. */
		smartTapRedemptionValue: Option[String] = None,
	  /** Indicates if the object has users. This field is set by the platform. */
		hasUsers: Option[Boolean] = None,
	  /** Deprecated */
		version: Option[String] = None,
	  /** Required. The unique identifier for an object. This ID must be unique across all objects from an issuer. This value should follow the format issuer ID.identifier where the former is issued by Google and latter is chosen by you. The unique identifier should only include alphanumeric characters, '.', '_', or '-'. */
		id: Option[String] = None,
	  /** Whether this object is currently linked to a single device. This field is set by the platform when a user saves the object, linking it to their device. Intended for use by select partners. Contact support for additional information. */
		hasLinkedDevice: Option[Boolean] = None,
	  /** Pass constraints for the object. Includes limiting NFC and screenshot behaviors. */
		passConstraints: Option[Schema.PassConstraints] = None,
	  /** linked_object_ids are a list of other objects such as event ticket, loyalty, offer, generic, giftcard, transit and boarding pass that should be automatically attached to this loyalty object. If a user had saved this loyalty card, then these linked_object_ids would be automatically pushed to the user's wallet (unless they turned off the setting to receive such linked passes). Make sure that objects present in linked_object_ids are already inserted - if not, calls would fail. Once linked, the linked objects cannot be unlinked. You cannot link objects belonging to another issuer. There is a limit to the number of objects that can be linked to a single object. After the limit is reached, new linked objects in the call will be ignored silently. Object IDs should follow the format issuer ID. identifier where the former is issued by Google and the latter is chosen by you. */
		linkedObjectIds: Option[List[String]] = None,
	  /** Restrictions on the object that needs to be verified before the user tries to save the pass. Note that this restrictions will only be applied during save time. If the restrictions changed after a user saves the pass, the new restrictions will not be applied to an already saved pass. */
		saveRestrictions: Option[Schema.SaveRestrictions] = None,
	  /** An array of messages displayed in the app. All users of this object will receive its associated messages. The maximum number of these fields is 10. */
		messages: Option[List[Schema.Message]] = None,
	  /** The secondary loyalty reward points label, balance, and type. Shown in addition to the primary loyalty points. */
		secondaryLoyaltyPoints: Option[Schema.LoyaltyPoints] = None,
	  /** Required. The class associated with this object. The class must be of the same type as this object, must already exist, and must be approved. Class IDs should follow the format issuer ID.identifier where the former is issued by Google and latter is chosen by you. */
		classId: Option[String] = None,
	  /** Optional banner image displayed on the front of the card. If none is present, hero image of the class, if present, will be displayed. If hero image of the class is also not present, nothing will be displayed. */
		heroImage: Option[Schema.Image] = None,
	  /** Deprecated. Use textModulesData instead. */
		infoModuleData: Option[Schema.InfoModuleData] = None,
	  /** Whether or not field updates to this object should trigger notifications. When set to NOTIFY, we will attempt to trigger a field update notification to users. These notifications will only be sent to users if the field is part of an allowlist. If set to DO_NOT_NOTIFY or NOTIFICATION_SETTINGS_UNSPECIFIED, no notification will be triggered. This setting is ephemeral and needs to be set with each PATCH or UPDATE request, otherwise a notification will not be triggered. */
		notifyPreference: Option[Schema.LoyaltyObject.NotifyPreferenceEnum] = None,
	  /** Optional app or website link that will be displayed as a button on the front of the pass. If AppLinkData is provided for the corresponding class only object AppLinkData will be displayed. */
		appLinkData: Option[Schema.AppLinkData] = None,
	  /** Optional value added module data. Maximum of ten on the object. */
		valueAddedModuleData: Option[List[Schema.ValueAddedModuleData]] = None,
	  /** The loyalty account holder name, such as "John Smith." Recommended maximum length is 20 characters to ensure full string is displayed on smaller screens. */
		accountName: Option[String] = None,
	  /** A copy of the inherited fields of the parent class. These fields are retrieved during a GET. */
		classReference: Option[Schema.LoyaltyClass] = None,
	  /** A list of offer objects linked to this loyalty card. The offer objects must already exist. Offer object IDs should follow the format issuer ID. identifier where the former is issued by Google and latter is chosen by you. */
		linkedOfferIds: Option[List[String]] = None,
	  /** Required. The state of the object. This field is used to determine how an object is displayed in the app. For example, an `inactive` object is moved to the "Expired passes" section. */
		state: Option[Schema.LoyaltyObject.StateEnum] = None,
	  /** The loyalty reward points label, balance, and type. */
		loyaltyPoints: Option[Schema.LoyaltyPoints] = None,
	  /** Text module data. If text module data is also defined on the class, both will be displayed. The maximum number of these fields displayed is 10 from the object and 10 from the class. */
		textModulesData: Option[List[Schema.TextModuleData]] = None
	)
	
	case class TextModuleData(
	  /** The ID associated with a text module. This field is here to enable ease of management of text modules and referencing them in template overrides. The ID should only include alphanumeric characters, '_', or '-'. It can not include dots, as dots are used to separate fields within FieldReference.fieldPaths in template overrides. */
		id: Option[String] = None,
	  /** The header of the Text Module. Recommended maximum length is 35 characters to ensure full string is displayed on smaller screens. */
		header: Option[String] = None,
	  /** The body of the Text Module, which is defined as an uninterrupted string. Recommended maximum length is 500 characters to ensure full string is displayed on smaller screens. */
		body: Option[String] = None,
	  /** Translated strings for the body. Recommended maximum length is 500 characters to ensure full string is displayed on smaller screens. */
		localizedBody: Option[Schema.LocalizedString] = None,
	  /** Translated strings for the header. Recommended maximum length is 35 characters to ensure full string is displayed on smaller screens. */
		localizedHeader: Option[Schema.LocalizedString] = None
	)
	
	object GiftCardClass {
		enum MultipleDevicesAndHoldersAllowedStatusEnum extends Enum[MultipleDevicesAndHoldersAllowedStatusEnum] { case STATUS_UNSPECIFIED, MULTIPLE_HOLDERS, ONE_USER_ALL_DEVICES, ONE_USER_ONE_DEVICE, multipleHolders, oneUserAllDevices, oneUserOneDevice }
		enum ViewUnlockRequirementEnum extends Enum[ViewUnlockRequirementEnum] { case VIEW_UNLOCK_REQUIREMENT_UNSPECIFIED, UNLOCK_NOT_REQUIRED, UNLOCK_REQUIRED_TO_VIEW }
		enum NotifyPreferenceEnum extends Enum[NotifyPreferenceEnum] { case NOTIFICATION_SETTINGS_FOR_UPDATES_UNSPECIFIED, NOTIFY_ON_UPDATE }
		enum ReviewStatusEnum extends Enum[ReviewStatusEnum] { case REVIEW_STATUS_UNSPECIFIED, UNDER_REVIEW, underReview, APPROVED, approved, REJECTED, rejected, DRAFT, draft }
	}
	case class GiftCardClass(
	  /** Note: This field is currently not supported to trigger geo notifications. */
		locations: Option[List[Schema.LatLongPoint]] = None,
	  /** The URI of your application's home page. Populating the URI in this field results in the exact same behavior as populating an URI in linksModuleData (when an object is rendered, a link to the homepage is shown in what would usually be thought of as the linksModuleData section of the object). */
		homepageUri: Option[Schema.Uri] = None,
	  /** Merchant name, such as "Adam's Apparel". The app may display an ellipsis after the first 20 characters to ensure full string is displayed on smaller screens. */
		merchantName: Option[String] = None,
	  /** Identifies whether this class supports Smart Tap. The `redemptionIssuers` and object level `smartTapRedemptionLevel` fields must also be set up correctly in order for a pass to support Smart Tap. */
		enableSmartTap: Option[Boolean] = None,
	  /** Deprecated. Use textModulesData instead. */
		infoModuleData: Option[Schema.InfoModuleData] = None,
	  /** Deprecated */
		version: Option[String] = None,
	  /** Identifies whether multiple users and devices will save the same object referencing this class. */
		multipleDevicesAndHoldersAllowedStatus: Option[Schema.GiftCardClass.MultipleDevicesAndHoldersAllowedStatusEnum] = None,
	  /** Translated strings for the issuer_name. Recommended maximum length is 20 characters to ensure full string is displayed on smaller screens. */
		localizedIssuerName: Option[Schema.LocalizedString] = None,
	  /** Country code used to display the card's country (when the user is not in that country), as well as to display localized content when content is not available in the user's locale. */
		countryCode: Option[String] = None,
	  /** The label to display for the card number, such as "Card Number". */
		cardNumberLabel: Option[String] = None,
	  /** Translated strings for the event_number_label. */
		localizedEventNumberLabel: Option[Schema.LocalizedString] = None,
	  /** Text module data. If text module data is also defined on the class, both will be displayed. The maximum number of these fields displayed is 10 from the object and 10 from the class. */
		textModulesData: Option[List[Schema.TextModuleData]] = None,
	  /** Determines whether the merchant supports gift card redemption using barcode. If true, app displays a barcode for the gift card on the Gift card details screen. If false, a barcode is not displayed. */
		allowBarcodeRedemption: Option[Boolean] = None,
	  /** Translated strings for the merchant_name. The app may display an ellipsis after the first 20 characters to ensure full string is displayed on smaller screens. */
		localizedMerchantName: Option[Schema.LocalizedString] = None,
	  /** Template information about how the class should be displayed. If unset, Google will fallback to a default set of fields to display. */
		classTemplateInfo: Option[Schema.ClassTemplateInfo] = None,
	  /** Deprecated. Use `multipleDevicesAndHoldersAllowedStatus` instead. */
		allowMultipleUsersPerObject: Option[Boolean] = None,
	  /** Links module data. If links module data is also defined on the object, both will be displayed. */
		linksModuleData: Option[Schema.LinksModuleData] = None,
	  /** The label to display for the PIN, such as "4-digit PIN". */
		pinLabel: Option[String] = None,
	  /** Optional app or website link that will be displayed as a button on the front of the pass. If AppLinkData is provided for the corresponding object that will be used instead. */
		appLinkData: Option[Schema.AppLinkData] = None,
	  /** Optional information about the security animation. If this is set a security animation will be rendered on pass details. */
		securityAnimation: Option[Schema.SecurityAnimation] = None,
	  /** Required. The issuer name. Recommended maximum length is 20 characters to ensure full string is displayed on smaller screens. */
		issuerName: Option[String] = None,
	  /** Optional value added module data. Maximum of ten on the class. For a pass only ten will be displayed, prioritizing those from the object. */
		valueAddedModuleData: Option[List[Schema.ValueAddedModuleData]] = None,
	  /** Callback options to be used to call the issuer back for every save/delete of an object for this class by the end-user. All objects of this class are eligible for the callback. */
		callbackOptions: Option[Schema.CallbackOptions] = None,
	  /** Image module data. The maximum number of these fields displayed is 1 from object level and 1 for class object level. */
		imageModulesData: Option[List[Schema.ImageModuleData]] = None,
	  /** Identifies what kind of resource this is. Value: the fixed string `"walletobjects#giftCardClass"`. */
		kind: Option[String] = None,
	  /** Optional banner image displayed on the front of the card. If none is present, nothing will be displayed. The image will display at 100% width. */
		heroImage: Option[Schema.Image] = None,
	  /** Identifies which redemption issuers can redeem the pass over Smart Tap. Redemption issuers are identified by their issuer ID. Redemption issuers must have at least one Smart Tap key configured. The `enableSmartTap` and object level `smartTapRedemptionLevel` fields must also be set up correctly in order for a pass to support Smart Tap. */
		redemptionIssuers: Option[List[String]] = None,
	  /** The review comments set by the platform when a class is marked `approved` or `rejected`. */
		review: Option[Schema.Review] = None,
	  /** Deprecated. */
		wordMark: Option[Schema.Image] = None,
	  /** View Unlock Requirement options for the gift card. */
		viewUnlockRequirement: Option[Schema.GiftCardClass.ViewUnlockRequirementEnum] = None,
	  /** Required. The unique identifier for a class. This ID must be unique across all classes from an issuer. This value should follow the format issuer ID. identifier where the former is issued by Google and latter is chosen by you. Your unique identifier should only include alphanumeric characters, '.', '_', or '-'. */
		id: Option[String] = None,
	  /** The background color for the card. If not set the dominant color of the hero image is used, and if no hero image is set, the dominant color of the logo is used. The format is #rrggbb where rrggbb is a hex RGB triplet, such as `#ffcc00`. You can also use the shorthand version of the RGB triplet which is #rgb, such as `#fc0`. */
		hexBackgroundColor: Option[String] = None,
	  /** An array of messages displayed in the app. All users of this object will receive its associated messages. The maximum number of these fields is 10. */
		messages: Option[List[Schema.Message]] = None,
	  /** The label to display for event number, such as "Target Event #". */
		eventNumberLabel: Option[String] = None,
	  /** Whether or not field updates to this class should trigger notifications. When set to NOTIFY, we will attempt to trigger a field update notification to users. These notifications will only be sent to users if the field is part of an allowlist. If not specified, no notification will be triggered. This setting is ephemeral and needs to be set with each PATCH or UPDATE request, otherwise a notification will not be triggered. */
		notifyPreference: Option[Schema.GiftCardClass.NotifyPreferenceEnum] = None,
	  /** The wide logo of the gift card program or company. When provided, this will be used in place of the program logo in the top left of the card view. */
		wideProgramLogo: Option[Schema.Image] = None,
	  /** The logo of the gift card program or company. This logo is displayed in both the details and list views of the app. */
		programLogo: Option[Schema.Image] = None,
	  /** Translated strings for the pin_label. */
		localizedPinLabel: Option[Schema.LocalizedString] = None,
	  /** Required. The status of the class. This field can be set to `draft` or `underReview` using the insert, patch, or update API calls. Once the review state is changed from `draft` it may not be changed back to `draft`. You should keep this field to `draft` when the class is under development. A `draft` class cannot be used to create any object. You should set this field to `underReview` when you believe the class is ready for use. The platform will automatically set this field to `approved` and it can be immediately used to create or migrate objects. When updating an already `approved` class you should keep setting this field to `underReview`. */
		reviewStatus: Option[Schema.GiftCardClass.ReviewStatusEnum] = None,
	  /** Translated strings for the card_number_label. */
		localizedCardNumberLabel: Option[Schema.LocalizedString] = None
	)
	
	case class GenericObjectAddMessageResponse(
	  /** The updated GenericObject resource. */
		resource: Option[Schema.GenericObject] = None
	)
	
	case class EventReservationInfo(
	  /** The confirmation code of the event reservation. This may also take the form of an "order number", "confirmation number", "reservation number", or other equivalent. */
		confirmationCode: Option[String] = None,
	  /** Identifies what kind of resource this is. Value: the fixed string `"walletobjects#eventReservationInfo"`. */
		kind: Option[String] = None
	)
	
	case class LoyaltyClassListResponse(
	  /** Resources corresponding to the list request. */
		resources: Option[List[Schema.LoyaltyClass]] = None,
	  /** Pagination of the response. */
		pagination: Option[Schema.Pagination] = None
	)
	
	case class TicketRestrictions(
	  /** Restrictions about times this ticket may be used. */
		timeRestrictions: Option[Schema.LocalizedString] = None,
	  /** More details about the above `routeRestrictions`. */
		routeRestrictionsDetails: Option[Schema.LocalizedString] = None,
	  /** Restrictions about routes that may be taken. For example, this may be the string "Reserved CrossCountry trains only". */
		routeRestrictions: Option[Schema.LocalizedString] = None,
	  /** Extra restrictions that don't fall under the "route" or "time" categories. */
		otherRestrictions: Option[Schema.LocalizedString] = None
	)
	
	case class ContentTypeInfo(
	  /** The content type of the file derived from the file extension of the URL path. The URL path is assumed to represent a file name (which is typically only true for agents that are providing a REST API). */
		fromUrlPath: Option[String] = None,
	  /** The content type of the file derived from the file extension of the original file name used by the client. */
		fromFileName: Option[String] = None,
	  /** The content type of the file as specified in the request headers, multipart headers, or RUPIO start request. */
		fromHeader: Option[String] = None,
	  /** Scotty's best guess of what the content type of the file is. */
		bestGuess: Option[String] = None,
	  /** The content type of the file derived by looking at specific bytes (i.e. "magic bytes") of the actual file. */
		fromBytes: Option[String] = None
	)
	
	case class FlightObjectAddMessageResponse(
	  /** The updated FlightObject resource. */
		resource: Option[Schema.FlightObject] = None
	)
	
	object EventTicketClass {
		enum ViewUnlockRequirementEnum extends Enum[ViewUnlockRequirementEnum] { case VIEW_UNLOCK_REQUIREMENT_UNSPECIFIED, UNLOCK_NOT_REQUIRED, UNLOCK_REQUIRED_TO_VIEW }
		enum SeatLabelEnum extends Enum[SeatLabelEnum] { case SEAT_LABEL_UNSPECIFIED, SEAT, seat }
		enum NotifyPreferenceEnum extends Enum[NotifyPreferenceEnum] { case NOTIFICATION_SETTINGS_FOR_UPDATES_UNSPECIFIED, NOTIFY_ON_UPDATE }
		enum RowLabelEnum extends Enum[RowLabelEnum] { case ROW_LABEL_UNSPECIFIED, ROW, row }
		enum ConfirmationCodeLabelEnum extends Enum[ConfirmationCodeLabelEnum] { case CONFIRMATION_CODE_LABEL_UNSPECIFIED, CONFIRMATION_CODE, confirmationCode, CONFIRMATION_NUMBER, confirmationNumber, ORDER_NUMBER, orderNumber, RESERVATION_NUMBER, reservationNumber }
		enum GateLabelEnum extends Enum[GateLabelEnum] { case GATE_LABEL_UNSPECIFIED, GATE, gate, DOOR, door, ENTRANCE, entrance }
		enum ReviewStatusEnum extends Enum[ReviewStatusEnum] { case REVIEW_STATUS_UNSPECIFIED, UNDER_REVIEW, underReview, APPROVED, approved, REJECTED, rejected, DRAFT, draft }
		enum MultipleDevicesAndHoldersAllowedStatusEnum extends Enum[MultipleDevicesAndHoldersAllowedStatusEnum] { case STATUS_UNSPECIFIED, MULTIPLE_HOLDERS, ONE_USER_ALL_DEVICES, ONE_USER_ONE_DEVICE, multipleHolders, oneUserAllDevices, oneUserOneDevice }
		enum SectionLabelEnum extends Enum[SectionLabelEnum] { case SECTION_LABEL_UNSPECIFIED, SECTION, section, THEATER, theater }
	}
	case class EventTicketClass(
	  /** Note: This field is currently not supported to trigger geo notifications. */
		locations: Option[List[Schema.LatLongPoint]] = None,
	  /** A custom label to use for the row value (`eventTicketObject.seatInfo.row`) on the card detail view. This should only be used if the default "Row" label or one of the `rowLabel` options is not sufficient. Both `rowLabel` and `customRowLabel` may not be set. If neither is set, the label will default to "Row", localized. If the row field is unset, this label will not be used. */
		customRowLabel: Option[Schema.LocalizedString] = None,
	  /** Callback options to be used to call the issuer back for every save/delete of an object for this class by the end-user. All objects of this class are eligible for the callback. */
		callbackOptions: Option[Schema.CallbackOptions] = None,
	  /** Deprecated. Use `multipleDevicesAndHoldersAllowedStatus` instead. */
		allowMultipleUsersPerObject: Option[Boolean] = None,
	  /** View Unlock Requirement options for the event ticket. */
		viewUnlockRequirement: Option[Schema.EventTicketClass.ViewUnlockRequirementEnum] = None,
	  /** The label to use for the seat value (`eventTicketObject.seatInfo.seat`) on the card detail view. Each available option maps to a set of localized strings, so that translations are shown to the user based on their locale. Both `seatLabel` and `customSeatLabel` may not be set. If neither is set, the label will default to "Seat", localized. If the seat field is unset, this label will not be used. */
		seatLabel: Option[Schema.EventTicketClass.SeatLabelEnum] = None,
	  /** Links module data. If links module data is also defined on the object, both will be displayed. */
		linksModuleData: Option[Schema.LinksModuleData] = None,
	  /** Whether or not field updates to this class should trigger notifications. When set to NOTIFY, we will attempt to trigger a field update notification to users. These notifications will only be sent to users if the field is part of an allowlist. If not specified, no notification will be triggered. This setting is ephemeral and needs to be set with each PATCH or UPDATE request, otherwise a notification will not be triggered. */
		notifyPreference: Option[Schema.EventTicketClass.NotifyPreferenceEnum] = None,
	  /** The date & time information of the event. */
		dateTime: Option[Schema.EventDateTime] = None,
	  /** Text module data. If text module data is also defined on the class, both will be displayed. The maximum number of these fields displayed is 10 from the object and 10 from the class. */
		textModulesData: Option[List[Schema.TextModuleData]] = None,
	  /** The wide logo of the ticket. When provided, this will be used in place of the logo in the top left of the card view. */
		wideLogo: Option[Schema.Image] = None,
	  /** Optional value added module data. Maximum of ten on the class. For a pass only ten will be displayed, prioritizing those from the object. */
		valueAddedModuleData: Option[List[Schema.ValueAddedModuleData]] = None,
	  /** The label to use for the row value (`eventTicketObject.seatInfo.row`) on the card detail view. Each available option maps to a set of localized strings, so that translations are shown to the user based on their locale. Both `rowLabel` and `customRowLabel` may not be set. If neither is set, the label will default to "Row", localized. If the row field is unset, this label will not be used. */
		rowLabel: Option[Schema.EventTicketClass.RowLabelEnum] = None,
	  /** Required. The issuer name. Recommended maximum length is 20 characters to ensure full string is displayed on smaller screens. */
		issuerName: Option[String] = None,
	  /** The label to use for the confirmation code value (`eventTicketObject.reservationInfo.confirmationCode`) on the card detail view. Each available option maps to a set of localized strings, so that translations are shown to the user based on their locale. Both `confirmationCodeLabel` and `customConfirmationCodeLabel` may not be set. If neither is set, the label will default to "Confirmation Code", localized. If the confirmation code field is unset, this label will not be used. */
		confirmationCodeLabel: Option[Schema.EventTicketClass.ConfirmationCodeLabelEnum] = None,
	  /** Identifies which redemption issuers can redeem the pass over Smart Tap. Redemption issuers are identified by their issuer ID. Redemption issuers must have at least one Smart Tap key configured. The `enableSmartTap` and object level `smartTapRedemptionLevel` fields must also be set up correctly in order for a pass to support Smart Tap. */
		redemptionIssuers: Option[List[String]] = None,
	  /** The label to use for the gate value (`eventTicketObject.seatInfo.gate`) on the card detail view. Each available option maps to a set of localized strings, so that translations are shown to the user based on their locale. Both `gateLabel` and `customGateLabel` may not be set. If neither is set, the label will default to "Gate", localized. If the gate field is unset, this label will not be used. */
		gateLabel: Option[Schema.EventTicketClass.GateLabelEnum] = None,
	  /** A custom label to use for the gate value (`eventTicketObject.seatInfo.gate`) on the card detail view. This should only be used if the default "Gate" label or one of the `gateLabel` options is not sufficient. Both `gateLabel` and `customGateLabel` may not be set. If neither is set, the label will default to "Gate", localized. If the gate field is unset, this label will not be used. */
		customGateLabel: Option[Schema.LocalizedString] = None,
	  /** A custom label to use for the confirmation code value (`eventTicketObject.reservationInfo.confirmationCode`) on the card detail view. This should only be used if the default "Confirmation Code" label or one of the `confirmationCodeLabel` options is not sufficient. Both `confirmationCodeLabel` and `customConfirmationCodeLabel` may not be set. If neither is set, the label will default to "Confirmation Code", localized. If the confirmation code field is unset, this label will not be used. */
		customConfirmationCodeLabel: Option[Schema.LocalizedString] = None,
	  /** A custom label to use for the seat value (`eventTicketObject.seatInfo.seat`) on the card detail view. This should only be used if the default "Seat" label or one of the `seatLabel` options is not sufficient. Both `seatLabel` and `customSeatLabel` may not be set. If neither is set, the label will default to "Seat", localized. If the seat field is unset, this label will not be used. */
		customSeatLabel: Option[Schema.LocalizedString] = None,
	  /** The fine print, terms, or conditions of the ticket. */
		finePrint: Option[Schema.LocalizedString] = None,
	  /** Required. The name of the event, such as "LA Dodgers at SF Giants". */
		eventName: Option[Schema.LocalizedString] = None,
	  /** Image module data. The maximum number of these fields displayed is 1 from object level and 1 for class object level. */
		imageModulesData: Option[List[Schema.ImageModuleData]] = None,
	  /** Identifies what kind of resource this is. Value: the fixed string `"walletobjects#eventTicketClass"`. */
		kind: Option[String] = None,
	  /** The background color for the card. If not set the dominant color of the hero image is used, and if no hero image is set, the dominant color of the logo is used. The format is #rrggbb where rrggbb is a hex RGB triplet, such as `#ffcc00`. You can also use the shorthand version of the RGB triplet which is #rgb, such as `#fc0`. */
		hexBackgroundColor: Option[String] = None,
	  /** Event venue details. */
		venue: Option[Schema.EventVenue] = None,
	  /** An array of messages displayed in the app. All users of this object will receive its associated messages. The maximum number of these fields is 10. */
		messages: Option[List[Schema.Message]] = None,
	  /** The ID of the event. This ID should be unique for every event in an account. It is used to group tickets together if the user has saved multiple tickets for the same event. It can be at most 64 characters. If provided, the grouping will be stable. Be wary of unintentional collision to avoid grouping tickets that should not be grouped. If you use only one class per event, you can simply set this to the `classId` (with or without the issuer ID portion). If not provided, the platform will attempt to use other data to group tickets (potentially unstable). */
		eventId: Option[String] = None,
	  /** The URI of your application's home page. Populating the URI in this field results in the exact same behavior as populating an URI in linksModuleData (when an object is rendered, a link to the homepage is shown in what would usually be thought of as the linksModuleData section of the object). */
		homepageUri: Option[Schema.Uri] = None,
	  /** Required. The status of the class. This field can be set to `draft` or `underReview` using the insert, patch, or update API calls. Once the review state is changed from `draft` it may not be changed back to `draft`. You should keep this field to `draft` when the class is under development. A `draft` class cannot be used to create any object. You should set this field to `underReview` when you believe the class is ready for use. The platform will automatically set this field to `approved` and it can be immediately used to create or migrate objects. When updating an already `approved` class you should keep setting this field to `underReview`. */
		reviewStatus: Option[Schema.EventTicketClass.ReviewStatusEnum] = None,
	  /** Optional banner image displayed on the front of the card. If none is present, nothing will be displayed. The image will display at 100% width. */
		heroImage: Option[Schema.Image] = None,
	  /** Identifies whether multiple users and devices will save the same object referencing this class. */
		multipleDevicesAndHoldersAllowedStatus: Option[Schema.EventTicketClass.MultipleDevicesAndHoldersAllowedStatusEnum] = None,
	  /** The label to use for the section value (`eventTicketObject.seatInfo.section`) on the card detail view. Each available option maps to a set of localized strings, so that translations are shown to the user based on their locale. Both `sectionLabel` and `customSectionLabel` may not be set. If neither is set, the label will default to "Section", localized. If the section field is unset, this label will not be used. */
		sectionLabel: Option[Schema.EventTicketClass.SectionLabelEnum] = None,
	  /** The review comments set by the platform when a class is marked `approved` or `rejected`. */
		review: Option[Schema.Review] = None,
	  /** Required. The unique identifier for a class. This ID must be unique across all classes from an issuer. This value should follow the format issuer ID. identifier where the former is issued by Google and latter is chosen by you. Your unique identifier should only include alphanumeric characters, '.', '_', or '-'. */
		id: Option[String] = None,
	  /** Deprecated. Use textModulesData instead. */
		infoModuleData: Option[Schema.InfoModuleData] = None,
	  /** Translated strings for the issuer_name. Recommended maximum length is 20 characters to ensure full string is displayed on smaller screens. */
		localizedIssuerName: Option[Schema.LocalizedString] = None,
	  /** A custom label to use for the section value (`eventTicketObject.seatInfo.section`) on the card detail view. This should only be used if the default "Section" label or one of the `sectionLabel` options is not sufficient. Both `sectionLabel` and `customSectionLabel` may not be set. If neither is set, the label will default to "Section", localized. If the section field is unset, this label will not be used. */
		customSectionLabel: Option[Schema.LocalizedString] = None,
	  /** Optional app or website link that will be displayed as a button on the front of the pass. If AppLinkData is provided for the corresponding object that will be used instead. */
		appLinkData: Option[Schema.AppLinkData] = None,
	  /** Deprecated. */
		wordMark: Option[Schema.Image] = None,
	  /** The logo image of the ticket. This image is displayed in the card detail view of the app. */
		logo: Option[Schema.Image] = None,
	  /** Identifies whether this class supports Smart Tap. The `redemptionIssuers` and object level `smartTapRedemptionLevel` fields must also be set up correctly in order for a pass to support Smart Tap. */
		enableSmartTap: Option[Boolean] = None,
	  /** Optional information about the security animation. If this is set a security animation will be rendered on pass details. */
		securityAnimation: Option[Schema.SecurityAnimation] = None,
	  /** Deprecated */
		version: Option[String] = None,
	  /** Template information about how the class should be displayed. If unset, Google will fallback to a default set of fields to display. */
		classTemplateInfo: Option[Schema.ClassTemplateInfo] = None,
	  /** Country code used to display the card's country (when the user is not in that country), as well as to display localized content when content is not available in the user's locale. */
		countryCode: Option[String] = None
	)
	
	case class EventTicketObjectAddMessageResponse(
	  /** The updated EventTicketObject resource. */
		resource: Option[Schema.EventTicketObject] = None
	)
	
	case class Uri(
	  /** Identifies what kind of resource this is. Value: the fixed string `"walletobjects#uri"`. */
		kind: Option[String] = None,
	  /** The URI's title appearing in the app as text. Recommended maximum is 20 characters to ensure full string is displayed on smaller screens. Note that in some contexts this text is not used, such as when `description` is part of an image. */
		description: Option[String] = None,
	  /** The ID associated with a uri. This field is here to enable ease of management of uris. */
		id: Option[String] = None,
	  /** The location of a web page, image, or other resource. URIs in the `LinksModuleData` module can have different prefixes indicating the type of URI (a link to a web page, a link to a map, a telephone number, or an email address). URIs must have a scheme. */
		uri: Option[String] = None,
	  /** Translated strings for the description. Recommended maximum is 20 characters to ensure full string is displayed on smaller screens. */
		localizedDescription: Option[Schema.LocalizedString] = None
	)
	
	case class ValueAddedModuleData(
	  /** Header to be displayed on the module. Character limit is 60 and longer strings will be truncated. */
		header: Option[Schema.LocalizedString] = None,
	  /** Constraints that all must be met for the module to be shown. */
		viewConstraints: Option[Schema.ModuleViewConstraints] = None,
	  /** Body to be displayed on the module. Character limit is 50 and longer strings will be truncated. */
		body: Option[Schema.LocalizedString] = None,
	  /** URI that the module leads to on click. This can be a web link or a deep link as mentioned in https://developer.android.com/training/app-links/deep-linking. */
		uri: Option[String] = None,
	  /** The index for sorting the modules. Modules with a lower sort index are shown before modules with a higher sort index. If unspecified, the sort index is assumed to be INT_MAX. For two modules with the same index, the sorting behavior is undefined. */
		sortIndex: Option[Int] = None,
	  /** Image to be displayed on the module. Recommended image ratio is 1:1. Images will be resized to fit this ratio. */
		image: Option[Schema.Image] = None
	)
	
	object TransitObject {
		enum PassengerTypeEnum extends Enum[PassengerTypeEnum] { case PASSENGER_TYPE_UNSPECIFIED, SINGLE_PASSENGER, singlePassenger, MULTIPLE_PASSENGERS, multiplePassengers }
		enum TripTypeEnum extends Enum[TripTypeEnum] { case TRIP_TYPE_UNSPECIFIED, ROUND_TRIP, roundTrip, ONE_WAY, oneWay }
		enum ConcessionCategoryEnum extends Enum[ConcessionCategoryEnum] { case CONCESSION_CATEGORY_UNSPECIFIED, ADULT, adult, CHILD, child, SENIOR, senior }
		enum TicketStatusEnum extends Enum[TicketStatusEnum] { case TICKET_STATUS_UNSPECIFIED, USED, used, REFUNDED, refunded, EXCHANGED, exchanged }
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, ACTIVE, active, COMPLETED, completed, EXPIRED, expired, INACTIVE, inactive }
		enum NotifyPreferenceEnum extends Enum[NotifyPreferenceEnum] { case NOTIFICATION_SETTINGS_FOR_UPDATES_UNSPECIFIED, NOTIFY_ON_UPDATE }
	}
	case class TransitObject(
	  /** The number of the ticket. This is a unique identifier for the ticket in the transit operator's system. */
		ticketNumber: Option[String] = None,
	  /** Note: This field is currently not supported to trigger geo notifications. */
		locations: Option[List[Schema.LatLongPoint]] = None,
	  /** The value that will be transmitted to a Smart Tap certified terminal over NFC for this object. The class level fields `enableSmartTap` and `redemptionIssuers` must also be set up correctly in order for the pass to support Smart Tap. Only ASCII characters are supported. */
		smartTapRedemptionValue: Option[String] = None,
	  /** Each ticket may contain one or more legs. Each leg contains departure and arrival information along with boarding and seating information. If only one leg is to be specified then use the `ticketLeg` field instead. Both `ticketLeg` and `ticketLegs` may not be set. */
		ticketLegs: Option[List[Schema.TicketLeg]] = None,
	  /** Purchase details for this ticket. */
		purchaseDetails: Option[Schema.PurchaseDetails] = None,
	  /** Deprecated */
		version: Option[String] = None,
	  /** Required. The class associated with this object. The class must be of the same type as this object, must already exist, and must be approved. Class IDs should follow the format issuer ID.identifier where the former is issued by Google and latter is chosen by you. */
		classId: Option[String] = None,
	  /** The time period this object will be `active` and object can be used. An object's state will be changed to `expired` when this time period has passed. */
		validTimeInterval: Option[Schema.TimeInterval] = None,
	  /** The activation status for the object. Required if the class has `activationOptions` set. */
		activationStatus: Option[Schema.ActivationStatus] = None,
	  /** This id is used to group tickets together if the user has saved multiple tickets for the same trip. */
		tripId: Option[String] = None,
	  /** A custom status to use for the ticket status value when `ticketStatus` does not provide the right option. Both `ticketStatus` and `customTicketStatus` may not be set. */
		customTicketStatus: Option[Schema.LocalizedString] = None,
	  /** A single ticket leg contains departure and arrival information along with boarding and seating information. If more than one leg is to be specified then use the `ticketLegs` field instead. Both `ticketLeg` and `ticketLegs` may not be set. */
		ticketLeg: Option[Schema.TicketLeg] = None,
	  /** Restrictions on the object that needs to be verified before the user tries to save the pass. Note that this restrictions will only be applied during save time. If the restrictions changed after a user saves the pass, the new restrictions will not be applied to an already saved pass. */
		saveRestrictions: Option[Schema.SaveRestrictions] = None,
	  /** Device context associated with the object. */
		deviceContext: Option[Schema.DeviceContext] = None,
	  /** Text module data. If text module data is also defined on the class, both will be displayed. The maximum number of these fields displayed is 10 from the object and 10 from the class. */
		textModulesData: Option[List[Schema.TextModuleData]] = None,
	  /** The number of passengers. */
		passengerType: Option[Schema.TransitObject.PassengerTypeEnum] = None,
	  /** A copy of the inherited fields of the parent class. These fields are retrieved during a GET. */
		classReference: Option[Schema.TransitClass] = None,
	  /** Required. The unique identifier for an object. This ID must be unique across all objects from an issuer. This value should follow the format issuer ID.identifier where the former is issued by Google and latter is chosen by you. The unique identifier should only include alphanumeric characters, '.', '_', or '-'. */
		id: Option[String] = None,
	  /** The rotating barcode type and value. */
		rotatingBarcode: Option[Schema.RotatingBarcode] = None,
	  /** Required. The type of trip this transit object represents. Used to determine the pass title and/or which symbol to use between the origin and destination. */
		tripType: Option[Schema.TransitObject.TripTypeEnum] = None,
	  /** The concession category for the ticket. */
		concessionCategory: Option[Schema.TransitObject.ConcessionCategoryEnum] = None,
	  /** linked_object_ids are a list of other objects such as event ticket, loyalty, offer, generic, giftcard, transit and boarding pass that should be automatically attached to this transit object. If a user had saved this transit card, then these linked_object_ids would be automatically pushed to the user's wallet (unless they turned off the setting to receive such linked passes). Make sure that objects present in linked_object_ids are already inserted - if not, calls would fail. Once linked, the linked objects cannot be unlinked. You cannot link objects belonging to another issuer. There is a limit to the number of objects that can be linked to a single object. After the limit is reached, new linked objects in the call will be ignored silently. Object IDs should follow the format issuer ID. identifier where the former is issued by Google and the latter is chosen by you. */
		linkedObjectIds: Option[List[String]] = None,
	  /** Indicates if the object has users. This field is set by the platform. */
		hasUsers: Option[Boolean] = None,
	  /** An array of messages displayed in the app. All users of this object will receive its associated messages. The maximum number of these fields is 10. */
		messages: Option[List[Schema.Message]] = None,
	  /** Deprecated. Use textModulesData instead. */
		infoModuleData: Option[Schema.InfoModuleData] = None,
	  /** Pass constraints for the object. Includes limiting NFC and screenshot behaviors. */
		passConstraints: Option[Schema.PassConstraints] = None,
	  /** Links module data. If links module data is also defined on the class, both will be displayed. */
		linksModuleData: Option[Schema.LinksModuleData] = None,
	  /** A custom concession category to use when `concessionCategory` does not provide the right option. Both `concessionCategory` and `customConcessionCategory` may not be set. */
		customConcessionCategory: Option[Schema.LocalizedString] = None,
	  /** The status of the ticket. For states which affect display, use the `state` field instead. */
		ticketStatus: Option[Schema.TransitObject.TicketStatusEnum] = None,
	  /** The barcode type and value. */
		barcode: Option[Schema.Barcode] = None,
	  /** Information that controls how passes are grouped together. */
		groupingInfo: Option[Schema.GroupingInfo] = None,
	  /** Optional value added module data. Maximum of ten on the object. */
		valueAddedModuleData: Option[List[Schema.ValueAddedModuleData]] = None,
	  /** Required. The state of the object. This field is used to determine how an object is displayed in the app. For example, an `inactive` object is moved to the "Expired passes" section. */
		state: Option[Schema.TransitObject.StateEnum] = None,
	  /** Optional banner image displayed on the front of the card. If none is present, hero image of the class, if present, will be displayed. If hero image of the class is also not present, nothing will be displayed. */
		heroImage: Option[Schema.Image] = None,
	  /** Whether or not field updates to this object should trigger notifications. When set to NOTIFY, we will attempt to trigger a field update notification to users. These notifications will only be sent to users if the field is part of an allowlist. If set to DO_NOT_NOTIFY or NOTIFICATION_SETTINGS_UNSPECIFIED, no notification will be triggered. This setting is ephemeral and needs to be set with each PATCH or UPDATE request, otherwise a notification will not be triggered. */
		notifyPreference: Option[Schema.TransitObject.NotifyPreferenceEnum] = None,
	  /** The name(s) of the passengers the ticket is assigned to. The above `passengerType` field is meant to give Google context on this field. */
		passengerNames: Option[String] = None,
	  /** Optional app or website link that will be displayed as a button on the front of the pass. If AppLinkData is provided for the corresponding class only object AppLinkData will be displayed. */
		appLinkData: Option[Schema.AppLinkData] = None,
	  /** The background color for the card. If not set the dominant color of the hero image is used, and if no hero image is set, the dominant color of the logo is used. The format is #rrggbb where rrggbb is a hex RGB triplet, such as `#ffcc00`. You can also use the shorthand version of the RGB triplet which is #rgb, such as `#fc0`. */
		hexBackgroundColor: Option[String] = None,
	  /** Information about what kind of restrictions there are on using this ticket. For example, which days of the week it must be used, or which routes are allowed to be taken. */
		ticketRestrictions: Option[Schema.TicketRestrictions] = None,
	  /** Whether this object is currently linked to a single device. This field is set by the platform when a user saves the object, linking it to their device. Intended for use by select partners. Contact support for additional information. */
		hasLinkedDevice: Option[Boolean] = None,
	  /** Indicates if notifications should explicitly be suppressed. If this field is set to true, regardless of the `messages` field, expiration notifications to the user will be suppressed. By default, this field is set to false. Currently, this can only be set for offers. */
		disableExpirationNotification: Option[Boolean] = None,
	  /** Image module data. The maximum number of these fields displayed is 1 from object level and 1 for class object level. */
		imageModulesData: Option[List[Schema.ImageModuleData]] = None
	)
	
	case class Pagination(
	  /** Number of results returned in this page. */
		resultsPerPage: Option[Int] = None,
	  /** Page token to send to fetch the next page. */
		nextPageToken: Option[String] = None,
	  /** Identifies what kind of resource this is. Value: the fixed string `"walletobjects#pagination"`. */
		kind: Option[String] = None
	)
	
	case class FieldSelector(
	  /** If more than one reference is supplied, then the first one that references a non-empty field will be displayed. */
		fields: Option[List[Schema.FieldReference]] = None
	)
	
	case class OfferObjectListResponse(
	  /** Pagination of the response. */
		pagination: Option[Schema.Pagination] = None,
	  /** Resources corresponding to the list request. */
		resources: Option[List[Schema.OfferObject]] = None
	)
	
	case class TransitObjectUploadRotatingBarcodeValuesResponse(
	
	)
	
	case class Image(
	  /** The URI for the image. */
		sourceUri: Option[Schema.ImageUri] = None,
	  /** Description of the image used for accessibility. */
		contentDescription: Option[Schema.LocalizedString] = None,
	  /** Identifies what kind of resource this is. Value: the fixed string `"walletobjects#image"`. */
		kind: Option[String] = None
	)
	
	case class EventSeat(
	  /** The row of the seat, such as "1", E", "BB", or "A5". This field is localizable so you may translate words or use different alphabets for the characters in an identifier. */
		row: Option[Schema.LocalizedString] = None,
	  /** The section of the seat, such as "121". This field is localizable so you may translate words or use different alphabets for the characters in an identifier. */
		section: Option[Schema.LocalizedString] = None,
	  /** The gate the ticket holder should enter to get to their seat, such as "A" or "West". This field is localizable so you may translate words or use different alphabets for the characters in an identifier. */
		gate: Option[Schema.LocalizedString] = None,
	  /** Identifies what kind of resource this is. Value: the fixed string `"walletobjects#eventSeat"`. */
		kind: Option[String] = None,
	  /** The seat number, such as "1", "2", "3", or any other seat identifier. This field is localizable so you may translate words or use different alphabets for the characters in an identifier. */
		seat: Option[Schema.LocalizedString] = None
	)
	
	case class ImageUri(
	  /** Additional information about the image, which is unused and retained only for backward compatibility. */
		description: Option[String] = None,
	  /** Translated strings for the description, which are unused and retained only for backward compatibility. */
		localizedDescription: Option[Schema.LocalizedString] = None,
	  /** The location of the image. URIs must have a scheme. */
		uri: Option[String] = None
	)
	
	object Media {
		enum ReferenceTypeEnum extends Enum[ReferenceTypeEnum] { case PATH, BLOB_REF, INLINE, GET_MEDIA, COMPOSITE_MEDIA, BIGSTORE_REF, DIFF_VERSION_RESPONSE, DIFF_CHECKSUMS_RESPONSE, DIFF_DOWNLOAD_RESPONSE, DIFF_UPLOAD_REQUEST, DIFF_UPLOAD_RESPONSE, COSMO_BINARY_REFERENCE, ARBITRARY_BYTES }
	}
	case class Media(
	  /** A unique fingerprint/version id for the media data */
		token: Option[String] = None,
	  /** A composite media composed of one or more media objects, set if reference_type is COMPOSITE_MEDIA. The media length field must be set to the sum of the lengths of all composite media objects. Note: All composite media must have length specified. */
		compositeMedia: Option[List[Schema.CompositeMedia]] = None,
	  /** Set if reference_type is DIFF_VERSION_RESPONSE. */
		diffVersionResponse: Option[Schema.DiffVersionResponse] = None,
	  /** Deprecated, use one of explicit hash type fields instead. These two hash related fields will only be populated on Scotty based media uploads and will contain the content of the hash group in the NotificationRequest: http://cs/#google3/blobstore2/api/scotty/service/proto/upload_listener.proto&q=class:Hash Hex encoded hash value of the uploaded media. */
		hash: Option[String] = None,
	  /** For Scotty Uploads: Scotty-provided hashes for uploads For Scotty Downloads: (WARNING: DO NOT USE WITHOUT PERMISSION FROM THE SCOTTY TEAM.) A Hash provided by the agent to be used to verify the data being downloaded. Currently only supported for inline payloads. Further, only crc32c_hash is currently supported. */
		crc32cHash: Option[Int] = None,
	  /** Deprecated, use one of explicit hash type fields instead. Algorithm used for calculating the hash. As of 2011/01/21, "MD5" is the only possible value for this field. New values may be added at any time. */
		algorithm: Option[String] = None,
	  /** Scotty-provided SHA256 hash for an upload. */
		sha256Hash: Option[String] = None,
	  /** Media id to forward to the operation GetMedia. Can be set if reference_type is GET_MEDIA. */
		mediaId: Option[String] = None,
	  /** Extended content type information provided for Scotty uploads. */
		contentTypeInfo: Option[Schema.ContentTypeInfo] = None,
	  /** Size of the data, in bytes */
		length: Option[String] = None,
	  /** Time at which the media data was last updated, in milliseconds since UNIX epoch */
		timestamp: Option[String] = None,
	  /** Scotty-provided MD5 hash for an upload. */
		md5Hash: Option[String] = None,
	  /** Set if reference_type is DIFF_CHECKSUMS_RESPONSE. */
		diffChecksumsResponse: Option[Schema.DiffChecksumsResponse] = None,
	  /** A binary data reference for a media download. Serves as a technology-agnostic binary reference in some Google infrastructure. This value is a serialized storage_cosmo.BinaryReference proto. Storing it as bytes is a hack to get around the fact that the cosmo proto (as well as others it includes) doesn't support JavaScript. This prevents us from including the actual type of this field. */
		cosmoBinaryReference: Option[String] = None,
	  /** Blobstore v1 reference, set if reference_type is BLOBSTORE_REF This should be the byte representation of a blobstore.BlobRef. Since Blobstore is deprecating v1, use blobstore2_info instead. For now, any v2 blob will also be represented in this field as v1 BlobRef. */
		blobRef: Option[String] = None,
	  /** For Scotty uploads only. If a user sends a hash code and the backend has requested that Scotty verify the upload against the client hash, Scotty will perform the check on behalf of the backend and will reject it if the hashes don't match. This is set to true if Scotty performed this verification. */
		hashVerified: Option[Boolean] = None,
	  /** Describes what the field reference contains. */
		referenceType: Option[Schema.Media.ReferenceTypeEnum] = None,
	  /** Path to the data, set if reference_type is PATH */
		path: Option[String] = None,
	  /** Set if reference_type is DIFF_DOWNLOAD_RESPONSE. */
		diffDownloadResponse: Option[Schema.DiffDownloadResponse] = None,
	  /** Blobstore v2 info, set if reference_type is BLOBSTORE_REF and it refers to a v2 blob. */
		blobstore2Info: Option[Schema.Blobstore2Info] = None,
	  /** Use object_id instead. */
		bigstoreObjectRef: Option[String] = None,
	  /** Parameters for a media download. */
		downloadParameters: Option[Schema.DownloadParameters] = None,
	  /** Set if reference_type is DIFF_UPLOAD_REQUEST. */
		diffUploadRequest: Option[Schema.DiffUploadRequest] = None,
	  /** Original file name */
		filename: Option[String] = None,
	  /** Media data, set if reference_type is INLINE */
		inline: Option[String] = None,
	  /** Set if reference_type is DIFF_UPLOAD_RESPONSE. */
		diffUploadResponse: Option[Schema.DiffUploadResponse] = None,
	  /** Reference to a TI Blob, set if reference_type is BIGSTORE_REF. */
		objectId: Option[Schema.ObjectId] = None,
	  /** MIME type of the data */
		contentType: Option[String] = None,
	  /** |is_potential_retry| is set false only when Scotty is certain that it has not sent the request before. When a client resumes an upload, this field must be set true in agent calls, because Scotty cannot be certain that it has never sent the request before due to potential failure in the session state persistence. */
		isPotentialRetry: Option[Boolean] = None,
	  /** Scotty-provided SHA1 hash for an upload. */
		sha1Hash: Option[String] = None
	)
	
	object DiscoverableProgram {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, TRUSTED_TESTERS, trustedTesters, LIVE, live, DISABLED, disabled }
	}
	case class DiscoverableProgram(
	  /** Visibility state of the discoverable program. */
		state: Option[Schema.DiscoverableProgram.StateEnum] = None,
	  /** Information about the ability to signup and add a valuable for this program through a merchant site. Used when MERCHANT_HOSTED_SIGNUP is enabled. */
		merchantSignupInfo: Option[Schema.DiscoverableProgramMerchantSignupInfo] = None,
	  /** Information about the ability to signin and add a valuable for this program through a merchant site. Used when MERCHANT_HOSTED_SIGNIN is enabled. */
		merchantSigninInfo: Option[Schema.DiscoverableProgramMerchantSigninInfo] = None
	)
	
	case class TransitClassListResponse(
	  /** Pagination of the response. */
		pagination: Option[Schema.Pagination] = None,
	  /** Resources corresponding to the list request. */
		resources: Option[List[Schema.TransitClass]] = None
	)
	
	case class BarcodeSectionDetail(
	  /** A reference to an existing text-based or image field to display. */
		fieldSelector: Option[Schema.FieldSelector] = None
	)
	
	case class TicketLeg(
	  /** The origin station code. This is required if `destinationStationCode` is present or if `originName` is not present. */
		originStationCode: Option[String] = None,
	  /** Short description/name of the fare for this leg of travel. Eg "Anytime Single Use". */
		fareName: Option[Schema.LocalizedString] = None,
	  /** The destination name. */
		destinationName: Option[Schema.LocalizedString] = None,
	  /** The destination station code. */
		destinationStationCode: Option[String] = None,
	  /** The name of the transit operator that is operating this leg of a trip. */
		transitOperatorName: Option[Schema.LocalizedString] = None,
	  /** Terminus station or destination of the train/bus/etc. */
		transitTerminusName: Option[Schema.LocalizedString] = None,
	  /** The name of the origin station. This is required if `desinationName` is present or if `originStationCode` is not present. */
		originName: Option[Schema.LocalizedString] = None,
	  /** The zone of boarding within the platform. */
		zone: Option[String] = None,
	  /** The reserved seat for the passenger(s). If only one seat is to be specified then use the `ticketSeat` field instead. Both `ticketSeat` and `ticketSeats` may not be set. */
		ticketSeats: Option[List[Schema.TicketSeat]] = None,
	  /** The date/time of departure. This is required if there is no validity time interval set on the transit object. This is an ISO 8601 extended format date/time, with or without an offset. Time may be specified up to nanosecond precision. Offsets may be specified with seconds precision (even though offset seconds is not part of ISO 8601). For example: `1985-04-12T23:20:50.52Z` would be 20 minutes and 50.52 seconds after the 23rd hour of April 12th, 1985 in UTC. `1985-04-12T19:20:50.52-04:00` would be 20 minutes and 50.52 seconds after the 19th hour of April 12th, 1985, 4 hours before UTC (same instant in time as the above example). If the event were in New York, this would be the equivalent of Eastern Daylight Time (EDT). Remember that offset varies in regions that observe Daylight Saving Time (or Summer Time), depending on the time of the year. `1985-04-12T19:20:50.52` would be 20 minutes and 50.52 seconds after the 19th hour of April 12th, 1985 with no offset information. The portion of the date/time without the offset is considered the "local date/time". This should be the local date/time at the origin station. For example, if the departure occurs at the 20th hour of June 5th, 2018 at the origin station, the local date/time portion should be `2018-06-05T20:00:00`. If the local date/time at the origin station is 4 hours before UTC, an offset of `-04:00` may be appended. Without offset information, some rich features may not be available. */
		departureDateTime: Option[String] = None,
	  /** The reserved seat for the passenger(s). If more than one seat is to be specified then use the `ticketSeats` field instead. Both `ticketSeat` and `ticketSeats` may not be set. */
		ticketSeat: Option[Schema.TicketSeat] = None,
	  /** The date/time of arrival. This is an ISO 8601 extended format date/time, with or without an offset. Time may be specified up to nanosecond precision. Offsets may be specified with seconds precision (even though offset seconds is not part of ISO 8601). For example: `1985-04-12T23:20:50.52Z` would be 20 minutes and 50.52 seconds after the 23rd hour of April 12th, 1985 in UTC. `1985-04-12T19:20:50.52-04:00` would be 20 minutes and 50.52 seconds after the 19th hour of April 12th, 1985, 4 hours before UTC (same instant in time as the above example). If the event were in New York, this would be the equivalent of Eastern Daylight Time (EDT). Remember that offset varies in regions that observe Daylight Saving Time (or Summer Time), depending on the time of the year. `1985-04-12T19:20:50.52` would be 20 minutes and 50.52 seconds after the 19th hour of April 12th, 1985 with no offset information. The portion of the date/time without the offset is considered the "local date/time". This should be the local date/time at the destination station. For example, if the event occurs at the 20th hour of June 5th, 2018 at the destination station, the local date/time portion should be `2018-06-05T20:00:00`. If the local date/time at the destination station is 4 hours before UTC, an offset of `-04:00` may be appended. Without offset information, some rich features may not be available. */
		arrivalDateTime: Option[String] = None,
	  /** The train or ship name/number that the passsenger needs to board. */
		carriage: Option[String] = None,
	  /** The platform or gate where the passenger can board the carriage. */
		platform: Option[String] = None
	)
	
	case class GiftCardObjectAddMessageResponse(
	  /** The updated GiftCardObject resource. */
		resource: Option[Schema.GiftCardObject] = None
	)
	
	case class FlightClassListResponse(
	  /** Resources corresponding to the list request. */
		resources: Option[List[Schema.FlightClass]] = None,
	  /** Pagination of the response. */
		pagination: Option[Schema.Pagination] = None
	)
	
	case class FlightObjectListResponse(
	  /** Resources corresponding to the list request. */
		resources: Option[List[Schema.FlightObject]] = None,
	  /** Pagination of the response. */
		pagination: Option[Schema.Pagination] = None
	)
	
	case class Resources(
	  /** A list of loyalty objects. */
		loyaltyObjects: Option[List[Schema.LoyaltyObject]] = None,
	  /** A list of event ticket objects. */
		eventTicketObjects: Option[List[Schema.EventTicketObject]] = None,
	  /** A list of generic classes. */
		genericClasses: Option[List[Schema.GenericClass]] = None,
	  /** A list of loyalty classes. */
		loyaltyClasses: Option[List[Schema.LoyaltyClass]] = None,
	  /** A list of transit classes. */
		transitClasses: Option[List[Schema.TransitClass]] = None,
	  /** A list of offer classes. */
		offerClasses: Option[List[Schema.OfferClass]] = None,
	  /** A list of flight classes. */
		flightClasses: Option[List[Schema.FlightClass]] = None,
	  /** A list of flight objects. */
		flightObjects: Option[List[Schema.FlightObject]] = None,
	  /** A list of gift card objects. */
		giftCardObjects: Option[List[Schema.GiftCardObject]] = None,
	  /** A list of event ticket classes. */
		eventTicketClasses: Option[List[Schema.EventTicketClass]] = None,
	  /** A list of gift card classes. */
		giftCardClasses: Option[List[Schema.GiftCardClass]] = None,
	  /** A list of offer objects. */
		offerObjects: Option[List[Schema.OfferObject]] = None,
	  /** A list of transit objects. */
		transitObjects: Option[List[Schema.TransitObject]] = None,
	  /** A list of generic objects. */
		genericObjects: Option[List[Schema.GenericObject]] = None
	)
	
	object TicketSeat {
		enum FareClassEnum extends Enum[FareClassEnum] { case FARE_CLASS_UNSPECIFIED, ECONOMY, economy, FIRST, first, BUSINESS, business }
	}
	case class TicketSeat(
	  /** The identifier of the train car or coach in which the ticketed seat is located. Eg. "10" */
		coach: Option[String] = None,
	  /** The passenger's seat assignment. Eg. "no specific seat". To be used when there is no specific identifier to use in `seat`. */
		seatAssignment: Option[Schema.LocalizedString] = None,
	  /** The identifier of where the ticketed seat is located. Eg. "42". If there is no specific identifier, use `seatAssigment` instead. */
		seat: Option[String] = None,
	  /** The fare class of the ticketed seat. */
		fareClass: Option[Schema.TicketSeat.FareClassEnum] = None,
	  /** A custome fare class to be used if no `fareClass` applies. Both `fareClass` and `customFareClass` may not be set. */
		customFareClass: Option[Schema.LocalizedString] = None
	)
	
	case class CardRowTwoItems(
	  /** The item to be displayed at the end of the row. This item will be aligned to the right. */
		endItem: Option[Schema.TemplateItem] = None,
	  /** The item to be displayed at the start of the row. This item will be aligned to the left. */
		startItem: Option[Schema.TemplateItem] = None
	)
	
	case class InfoModuleData(
		showLastUpdateTime: Option[Boolean] = None,
	  /** A list of collections of labels and values. These will be displayed one after the other in a singular column. */
		labelValueRows: Option[List[Schema.LabelValueRow]] = None
	)
	
	object BoardingAndSeatingInfo {
		enum BoardingDoorEnum extends Enum[BoardingDoorEnum] { case BOARDING_DOOR_UNSPECIFIED, FRONT, front, BACK, back }
	}
	case class BoardingAndSeatingInfo(
	  /** The sequence number on the boarding pass. This usually matches the sequence in which the passengers checked in. Airline might use the number for manual boarding and bag tags. eg: "49" */
		sequenceNumber: Option[String] = None,
	  /** The value of passenger seat. If there is no specific identifier, use `seatAssignment` instead. eg: "25A" */
		seatNumber: Option[String] = None,
	  /** Set this field only if this flight boards through more than one door or bridge and you want to explicitly print the door location on the boarding pass. Most airlines route their passengers to the right door or bridge by refering to doors/bridges by the `seatClass`. In those cases `boardingDoor` should not be set. */
		boardingDoor: Option[Schema.BoardingAndSeatingInfo.BoardingDoorEnum] = None,
	  /** The value of boarding group (or zone) this passenger shall board with. eg: "B" The label for this value will be determined by the `boardingPolicy` field in the `flightClass` referenced by this object. */
		boardingGroup: Option[String] = None,
	  /** The value of boarding position. eg: "76" */
		boardingPosition: Option[String] = None,
	  /** The passenger's seat assignment. To be used when there is no specific identifier to use in `seatNumber`. eg: "assigned at gate" */
		seatAssignment: Option[Schema.LocalizedString] = None,
	  /** The value of the seat class. eg: "Economy" or "Economy Plus" */
		seatClass: Option[String] = None,
	  /** Identifies what kind of resource this is. Value: the fixed string `"walletobjects#boardingAndSeatingInfo"`. */
		kind: Option[String] = None,
	  /** A small image shown above the boarding barcode. Airlines can use it to communicate any special boarding privileges. In the event the security program logo is also set, this image might be rendered alongside the logo for that security program. */
		boardingPrivilegeImage: Option[Schema.Image] = None
	)
	
	case class ClassTemplateInfo(
	  /** Override for the details view (beneath the card view). */
		detailsTemplateOverride: Option[Schema.DetailsTemplateOverride] = None,
	  /** Override for the card view. */
		cardTemplateOverride: Option[Schema.CardTemplateOverride] = None,
	  /** Specifies extra information to be displayed above and below the barcode. */
		cardBarcodeSectionDetails: Option[Schema.CardBarcodeSectionDetails] = None,
	  /** Override for the passes list view. */
		listTemplateOverride: Option[Schema.ListTemplateOverride] = None
	)
	
	case class Money(
	  /** The unit of money amount in micros. For example, $1 USD would be represented as 1000000 micros. */
		micros: Option[String] = None,
	  /** Identifies what kind of resource this is. Value: the fixed string `"walletobjects#money"`. */
		kind: Option[String] = None,
	  /** The currency code, such as "USD" or "EUR." */
		currencyCode: Option[String] = None
	)
	
	object Message {
		enum MessageTypeEnum extends Enum[MessageTypeEnum] { case MESSAGE_TYPE_UNSPECIFIED, TEXT, text, EXPIRATION_NOTIFICATION, expirationNotification, TEXT_AND_NOTIFY }
	}
	case class Message(
	  /** The ID associated with a message. This field is here to enable ease of management of messages. Notice ID values could possibly duplicate across multiple messages in the same class/instance, and care must be taken to select a reasonable ID for each message. */
		id: Option[String] = None,
	  /** The period of time that the message will be displayed to users. You can define both a `startTime` and `endTime` for each message. A message is displayed immediately after a Wallet Object is inserted unless a `startTime` is set. The message will appear in a list of messages indefinitely if `endTime` is not provided. */
		displayInterval: Option[Schema.TimeInterval] = None,
	  /** Identifies what kind of resource this is. Value: the fixed string `"walletobjects#walletObjectMessage"`. */
		kind: Option[String] = None,
	  /** The message header. */
		header: Option[String] = None,
	  /** The message body. */
		body: Option[String] = None,
	  /** The message type. */
		messageType: Option[Schema.Message.MessageTypeEnum] = None,
	  /** Translated strings for the message body. */
		localizedBody: Option[Schema.LocalizedString] = None,
	  /** Translated strings for the message header. */
		localizedHeader: Option[Schema.LocalizedString] = None
	)
	
	case class LoyaltyObjectAddMessageResponse(
	  /** The updated LoyaltyObject resource. */
		resource: Option[Schema.LoyaltyObject] = None
	)
	
	object LoyaltyClass {
		enum ViewUnlockRequirementEnum extends Enum[ViewUnlockRequirementEnum] { case VIEW_UNLOCK_REQUIREMENT_UNSPECIFIED, UNLOCK_NOT_REQUIRED, UNLOCK_REQUIRED_TO_VIEW }
		enum ReviewStatusEnum extends Enum[ReviewStatusEnum] { case REVIEW_STATUS_UNSPECIFIED, UNDER_REVIEW, underReview, APPROVED, approved, REJECTED, rejected, DRAFT, draft }
		enum NotifyPreferenceEnum extends Enum[NotifyPreferenceEnum] { case NOTIFICATION_SETTINGS_FOR_UPDATES_UNSPECIFIED, NOTIFY_ON_UPDATE }
		enum MultipleDevicesAndHoldersAllowedStatusEnum extends Enum[MultipleDevicesAndHoldersAllowedStatusEnum] { case STATUS_UNSPECIFIED, MULTIPLE_HOLDERS, ONE_USER_ALL_DEVICES, ONE_USER_ONE_DEVICE, multipleHolders, oneUserAllDevices, oneUserOneDevice }
	}
	case class LoyaltyClass(
	  /** The account name label, such as "Member Name." Recommended maximum length is 15 characters to ensure full string is displayed on smaller screens. */
		accountNameLabel: Option[String] = None,
	  /** The background color for the card. If not set the dominant color of the hero image is used, and if no hero image is set, the dominant color of the logo is used. The format is #rrggbb where rrggbb is a hex RGB triplet, such as `#ffcc00`. You can also use the shorthand version of the RGB triplet which is #rgb, such as `#fc0`. */
		hexBackgroundColor: Option[String] = None,
	  /** Translated strings for the program_name. The app may display an ellipsis after the first 20 characters to ensure full string is displayed on smaller screens. */
		localizedProgramName: Option[Schema.LocalizedString] = None,
	  /** Deprecated. Use `multipleDevicesAndHoldersAllowedStatus` instead. */
		allowMultipleUsersPerObject: Option[Boolean] = None,
	  /** Identifies which redemption issuers can redeem the pass over Smart Tap. Redemption issuers are identified by their issuer ID. Redemption issuers must have at least one Smart Tap key configured. The `enableSmartTap` and one of object level `smartTapRedemptionValue`, barcode.value`, or `accountId` fields must also be set up correctly in order for a pass to support Smart Tap. */
		redemptionIssuers: Option[List[String]] = None,
	  /** Identifies what kind of resource this is. Value: the fixed string `"walletobjects#loyaltyClass"`. */
		kind: Option[String] = None,
	  /** The review comments set by the platform when a class is marked `approved` or `rejected`. */
		review: Option[Schema.Review] = None,
	  /** Identifies whether this class supports Smart Tap. The `redemptionIssuers` and one of object level `smartTapRedemptionLevel`, barcode.value`, or `accountId` fields must also be set up correctly in order for a pass to support Smart Tap. */
		enableSmartTap: Option[Boolean] = None,
	  /** Required. The issuer name. Recommended maximum length is 20 characters to ensure full string is displayed on smaller screens. */
		issuerName: Option[String] = None,
	  /** Translated strings for the account_name_label. Recommended maximum length is 15 characters to ensure full string is displayed on smaller screens. */
		localizedAccountNameLabel: Option[Schema.LocalizedString] = None,
	  /** Deprecated */
		version: Option[String] = None,
	  /** Information about how the class may be discovered and instantiated from within the Google Pay app. */
		discoverableProgram: Option[Schema.DiscoverableProgram] = None,
	  /** Optional information about the security animation. If this is set a security animation will be rendered on pass details. */
		securityAnimation: Option[Schema.SecurityAnimation] = None,
	  /** Optional app or website link that will be displayed as a button on the front of the pass. If AppLinkData is provided for the corresponding object that will be used instead. */
		appLinkData: Option[Schema.AppLinkData] = None,
	  /** Translated strings for the rewards_tier_label. Recommended maximum length is 9 characters to ensure full string is displayed on smaller screens. */
		localizedRewardsTierLabel: Option[Schema.LocalizedString] = None,
	  /** Translated strings for the issuer_name. Recommended maximum length is 20 characters to ensure full string is displayed on smaller screens. */
		localizedIssuerName: Option[Schema.LocalizedString] = None,
	  /** Required. The unique identifier for a class. This ID must be unique across all classes from an issuer. This value should follow the format issuer ID. identifier where the former is issued by Google and latter is chosen by you. Your unique identifier should only include alphanumeric characters, '.', '_', or '-'. */
		id: Option[String] = None,
	  /** Image module data. The maximum number of these fields displayed is 1 from object level and 1 for class object level. */
		imageModulesData: Option[List[Schema.ImageModuleData]] = None,
	  /** Translated strings for the secondary_rewards_tier_label. */
		localizedSecondaryRewardsTierLabel: Option[Schema.LocalizedString] = None,
	  /** View Unlock Requirement options for the loyalty card. */
		viewUnlockRequirement: Option[Schema.LoyaltyClass.ViewUnlockRequirementEnum] = None,
	  /** Required. The status of the class. This field can be set to `draft` or `underReview` using the insert, patch, or update API calls. Once the review state is changed from `draft` it may not be changed back to `draft`. You should keep this field to `draft` when the class is under development. A `draft` class cannot be used to create any object. You should set this field to `underReview` when you believe the class is ready for use. The platform will automatically set this field to `approved` and it can be immediately used to create or migrate objects. When updating an already `approved` class you should keep setting this field to `underReview`. */
		reviewStatus: Option[Schema.LoyaltyClass.ReviewStatusEnum] = None,
	  /** Country code used to display the card's country (when the user is not in that country), as well as to display localized content when content is not available in the user's locale. */
		countryCode: Option[String] = None,
	  /** The secondary rewards tier label, such as "Rewards Tier." */
		secondaryRewardsTierLabel: Option[String] = None,
	  /** Optional value added module data. Maximum of ten on the class. For a pass only ten will be displayed, prioritizing those from the object. */
		valueAddedModuleData: Option[List[Schema.ValueAddedModuleData]] = None,
	  /** Whether or not field updates to this class should trigger notifications. When set to NOTIFY, we will attempt to trigger a field update notification to users. These notifications will only be sent to users if the field is part of an allowlist. If not specified, no notification will be triggered. This setting is ephemeral and needs to be set with each PATCH or UPDATE request, otherwise a notification will not be triggered. */
		notifyPreference: Option[Schema.LoyaltyClass.NotifyPreferenceEnum] = None,
	  /** Template information about how the class should be displayed. If unset, Google will fallback to a default set of fields to display. */
		classTemplateInfo: Option[Schema.ClassTemplateInfo] = None,
	  /** The wide logo of the loyalty program or company. When provided, this will be used in place of the program logo in the top left of the card view. */
		wideProgramLogo: Option[Schema.Image] = None,
	  /** The URI of your application's home page. Populating the URI in this field results in the exact same behavior as populating an URI in linksModuleData (when an object is rendered, a link to the homepage is shown in what would usually be thought of as the linksModuleData section of the object). */
		homepageUri: Option[Schema.Uri] = None,
	  /** Deprecated. Use textModulesData instead. */
		infoModuleData: Option[Schema.InfoModuleData] = None,
	  /** The rewards tier, such as "Gold" or "Platinum." Recommended maximum length is 7 characters to ensure full string is displayed on smaller screens. */
		rewardsTier: Option[String] = None,
	  /** Callback options to be used to call the issuer back for every save/delete of an object for this class by the end-user. All objects of this class are eligible for the callback. */
		callbackOptions: Option[Schema.CallbackOptions] = None,
	  /** Required. The program name, such as "Adam's Apparel". The app may display an ellipsis after the first 20 characters to ensure full string is displayed on smaller screens. */
		programName: Option[String] = None,
	  /** Optional banner image displayed on the front of the card. If none is present, nothing will be displayed. The image will display at 100% width. */
		heroImage: Option[Schema.Image] = None,
	  /** The account ID label, such as "Member ID." Recommended maximum length is 15 characters to ensure full string is displayed on smaller screens. */
		accountIdLabel: Option[String] = None,
	  /** The rewards tier label, such as "Rewards Tier." Recommended maximum length is 9 characters to ensure full string is displayed on smaller screens. */
		rewardsTierLabel: Option[String] = None,
	  /** Translated strings for the secondary_rewards_tier. */
		localizedSecondaryRewardsTier: Option[Schema.LocalizedString] = None,
	  /** Links module data. If links module data is also defined on the object, both will be displayed. */
		linksModuleData: Option[Schema.LinksModuleData] = None,
	  /** Text module data. If text module data is also defined on the class, both will be displayed. The maximum number of these fields displayed is 10 from the object and 10 from the class. */
		textModulesData: Option[List[Schema.TextModuleData]] = None,
	  /** Deprecated. */
		wordMark: Option[Schema.Image] = None,
	  /** Translated strings for the rewards_tier. Recommended maximum length is 7 characters to ensure full string is displayed on smaller screens. */
		localizedRewardsTier: Option[Schema.LocalizedString] = None,
	  /** Translated strings for the account_id_label. Recommended maximum length is 15 characters to ensure full string is displayed on smaller screens. */
		localizedAccountIdLabel: Option[Schema.LocalizedString] = None,
	  /** Note: This field is currently not supported to trigger geo notifications. */
		locations: Option[List[Schema.LatLongPoint]] = None,
	  /** The secondary rewards tier, such as "Gold" or "Platinum." */
		secondaryRewardsTier: Option[String] = None,
	  /** An array of messages displayed in the app. All users of this object will receive its associated messages. The maximum number of these fields is 10. */
		messages: Option[List[Schema.Message]] = None,
	  /** Required. The logo of the loyalty program or company. This logo is displayed in both the details and list views of the app. */
		programLogo: Option[Schema.Image] = None,
	  /** Identifies whether multiple users and devices will save the same object referencing this class. */
		multipleDevicesAndHoldersAllowedStatus: Option[Schema.LoyaltyClass.MultipleDevicesAndHoldersAllowedStatusEnum] = None
	)
	
	case class GenericObjectListResponse(
	  /** Pagination of the response. */
		pagination: Option[Schema.Pagination] = None,
	  /** Resources corresponding to the list request. */
		resources: Option[List[Schema.GenericObject]] = None
	)
	
	case class CardRowTemplateInfo(
	  /** Template for a row containing one item. Exactly one of "one_item", "two_items", "three_items" must be set. */
		oneItem: Option[Schema.CardRowOneItem] = None,
	  /** Template for a row containing two items. Exactly one of "one_item", "two_items", "three_items" must be set. */
		twoItems: Option[Schema.CardRowTwoItems] = None,
	  /** Template for a row containing three items. Exactly one of "one_item", "two_items", "three_items" must be set. */
		threeItems: Option[Schema.CardRowThreeItems] = None
	)
	
	object EventDateTime {
		enum DoorsOpenLabelEnum extends Enum[DoorsOpenLabelEnum] { case DOORS_OPEN_LABEL_UNSPECIFIED, DOORS_OPEN, doorsOpen, GATES_OPEN, gatesOpen }
	}
	case class EventDateTime(
	  /** The date/time when the event starts. If the event spans multiple days, it should be the start date/time on the first day. This is an ISO 8601 extended format date/time, with or without an offset. Time may be specified up to nanosecond precision. Offsets may be specified with seconds precision (even though offset seconds is not part of ISO 8601). For example: `1985-04-12T23:20:50.52Z` would be 20 minutes and 50.52 seconds after the 23rd hour of April 12th, 1985 in UTC. `1985-04-12T19:20:50.52-04:00` would be 20 minutes and 50.52 seconds after the 19th hour of April 12th, 1985, 4 hours before UTC (same instant in time as the above example). If the event were in New York, this would be the equivalent of Eastern Daylight Time (EDT). Remember that offset varies in regions that observe Daylight Saving Time (or Summer Time), depending on the time of the year. `1985-04-12T19:20:50.52` would be 20 minutes and 50.52 seconds after the 19th hour of April 12th, 1985 with no offset information. The portion of the date/time without the offset is considered the "local date/time". This should be the local date/time at the venue. For example, if the event occurs at the 20th hour of June 5th, 2018 at the venue, the local date/time portion should be `2018-06-05T20:00:00`. If the local date/time at the venue is 4 hours before UTC, an offset of `-04:00` may be appended. Without offset information, some rich features may not be available. */
		start: Option[String] = None,
	  /** The label to use for the doors open value (`doorsOpen`) on the card detail view. Each available option maps to a set of localized strings, so that translations are shown to the user based on their locale. Both `doorsOpenLabel` and `customDoorsOpenLabel` may not be set. If neither is set, the label will default to "Doors Open", localized. If the doors open field is unset, this label will not be used. */
		doorsOpenLabel: Option[Schema.EventDateTime.DoorsOpenLabelEnum] = None,
	  /** The date/time when the event ends. If the event spans multiple days, it should be the end date/time on the last day. This is an ISO 8601 extended format date/time, with or without an offset. Time may be specified up to nanosecond precision. Offsets may be specified with seconds precision (even though offset seconds is not part of ISO 8601). For example: `1985-04-12T23:20:50.52Z` would be 20 minutes and 50.52 seconds after the 23rd hour of April 12th, 1985 in UTC. `1985-04-12T19:20:50.52-04:00` would be 20 minutes and 50.52 seconds after the 19th hour of April 12th, 1985, 4 hours before UTC (same instant in time as the above example). If the event were in New York, this would be the equivalent of Eastern Daylight Time (EDT). Remember that offset varies in regions that observe Daylight Saving Time (or Summer Time), depending on the time of the year. `1985-04-12T19:20:50.52` would be 20 minutes and 50.52 seconds after the 19th hour of April 12th, 1985 with no offset information. The portion of the date/time without the offset is considered the "local date/time". This should be the local date/time at the venue. For example, if the event occurs at the 20th hour of June 5th, 2018 at the venue, the local date/time portion should be `2018-06-05T20:00:00`. If the local date/time at the venue is 4 hours before UTC, an offset of `-04:00` may be appended. Without offset information, some rich features may not be available. */
		end: Option[String] = None,
	  /** Identifies what kind of resource this is. Value: the fixed string `"walletobjects#eventDateTime"`. */
		kind: Option[String] = None,
	  /** The date/time when the doors open at the venue. This is an ISO 8601 extended format date/time, with or without an offset. Time may be specified up to nanosecond precision. Offsets may be specified with seconds precision (even though offset seconds is not part of ISO 8601). For example: `1985-04-12T23:20:50.52Z` would be 20 minutes and 50.52 seconds after the 23rd hour of April 12th, 1985 in UTC. `1985-04-12T19:20:50.52-04:00` would be 20 minutes and 50.52 seconds after the 19th hour of April 12th, 1985, 4 hours before UTC (same instant in time as the above example). If the event were in New York, this would be the equivalent of Eastern Daylight Time (EDT). Remember that offset varies in regions that observe Daylight Saving Time (or Summer Time), depending on the time of the year. `1985-04-12T19:20:50.52` would be 20 minutes and 50.52 seconds after the 19th hour of April 12th, 1985 with no offset information. The portion of the date/time without the offset is considered the "local date/time". This should be the local date/time at the venue. For example, if the event occurs at the 20th hour of June 5th, 2018 at the venue, the local date/time portion should be `2018-06-05T20:00:00`. If the local date/time at the venue is 4 hours before UTC, an offset of `-04:00` may be appended. Without offset information, some rich features may not be available. */
		doorsOpen: Option[String] = None,
	  /** A custom label to use for the doors open value (`doorsOpen`) on the card detail view. This should only be used if the default "Doors Open" label or one of the `doorsOpenLabel` options is not sufficient. Both `doorsOpenLabel` and `customDoorsOpenLabel` may not be set. If neither is set, the label will default to "Doors Open", localized. If the doors open field is unset, this label will not be used. */
		customDoorsOpenLabel: Option[Schema.LocalizedString] = None
	)
	
	case class SmartTap(
	  /** Communication from merchant to user. */
		infos: Option[List[Schema.IssuerToUserInfo]] = None,
	  /** Smart Tap merchant ID of who engaged in the Smart Tap interaction. */
		merchantId: Option[String] = None,
	  /** Identifies what kind of resource this is. Value: the fixed string `"walletobjects#smartTap"`. */
		kind: Option[String] = None,
	  /** The unique identifier for a smart tap. This value should follow the format issuer ID.identifier where the former is issued by Google and latter is the Smart Tap id. The Smart Tap id is a Base64 encoded string which represents the id which was generated by the Google Pay app. */
		id: Option[String] = None
	)
	
	case class OfferObjectAddMessageResponse(
	  /** The updated OfferObject resource. */
		resource: Option[Schema.OfferObject] = None
	)
	
	case class DiffDownloadResponse(
	  /** The original object location. */
		objectLocation: Option[Schema.CompositeMedia] = None
	)
	
	case class Issuer(
	  /** The account name of the issuer. */
		name: Option[String] = None,
	  /** The unique identifier for an issuer account. This is automatically generated when the issuer is inserted. */
		issuerId: Option[String] = None,
	  /** Allows the issuer to provide their callback settings. */
		callbackOptions: Option[Schema.CallbackOptions] = None,
	  /** Issuer contact information. */
		contactInfo: Option[Schema.IssuerContactInfo] = None,
	  /** URL for the issuer's home page. */
		homepageUrl: Option[String] = None,
	  /** Available only to Smart Tap enabled partners. Contact support for additional guidance. */
		smartTapMerchantData: Option[Schema.SmartTapMerchantData] = None
	)
	
	case class FlightClassAddMessageResponse(
	  /** The updated FlightClass resource. */
		resource: Option[Schema.FlightClass] = None
	)
	
	case class GenericClassAddMessageResponse(
	  /** The updated EventTicketClass resource. */
		resource: Option[Schema.GenericClass] = None
	)
	
	object GenericClass {
		enum MultipleDevicesAndHoldersAllowedStatusEnum extends Enum[MultipleDevicesAndHoldersAllowedStatusEnum] { case STATUS_UNSPECIFIED, MULTIPLE_HOLDERS, ONE_USER_ALL_DEVICES, ONE_USER_ONE_DEVICE, multipleHolders, oneUserAllDevices, oneUserOneDevice }
		enum ViewUnlockRequirementEnum extends Enum[ViewUnlockRequirementEnum] { case VIEW_UNLOCK_REQUIREMENT_UNSPECIFIED, UNLOCK_NOT_REQUIRED, UNLOCK_REQUIRED_TO_VIEW }
	}
	case class GenericClass(
	  /** Identifies which redemption issuers can redeem the pass over Smart Tap. Redemption issuers are identified by their issuer ID. Redemption issuers must have at least one Smart Tap key configured. The `enableSmartTap` and object level `smartTapRedemptionLevel` fields must also be set up correctly in order for a pass to support Smart Tap. */
		redemptionIssuers: Option[List[String]] = None,
	  /** Optional value added module data. Maximum of ten on the class. For a pass only ten will be displayed, prioritizing those from the object. */
		valueAddedModuleData: Option[List[Schema.ValueAddedModuleData]] = None,
	  /** Optional app or website link that will be displayed as a button on the front of the pass. If AppLinkData is provided for the corresponding object that will be used instead. */
		appLinkData: Option[Schema.AppLinkData] = None,
	  /** Callback options to be used to call the issuer back for every save/delete of an object for this class by the end-user. All objects of this class are eligible for the callback. */
		callbackOptions: Option[Schema.CallbackOptions] = None,
	  /** Identifies whether multiple users and devices will save the same object referencing this class. */
		multipleDevicesAndHoldersAllowedStatus: Option[Schema.GenericClass.MultipleDevicesAndHoldersAllowedStatusEnum] = None,
	  /** Available only to Smart Tap enabled partners. Contact support for additional guidance. */
		enableSmartTap: Option[Boolean] = None,
	  /** Optional information about the security animation. If this is set a security animation will be rendered on pass details. */
		securityAnimation: Option[Schema.SecurityAnimation] = None,
	  /** View Unlock Requirement options for the generic pass. */
		viewUnlockRequirement: Option[Schema.GenericClass.ViewUnlockRequirementEnum] = None,
	  /** An array of messages displayed in the app. All users of this object will receive its associated messages. The maximum number of these fields is 10. */
		messages: Option[List[Schema.Message]] = None,
	  /** Text module data. If `textModulesData` is also defined on the object, both will be displayed. The maximum number of these fields displayed is 10 from class and 10 from object. */
		textModulesData: Option[List[Schema.TextModuleData]] = None,
	  /** Template information about how the class should be displayed. If unset, Google will fallback to a default set of fields to display. */
		classTemplateInfo: Option[Schema.ClassTemplateInfo] = None,
	  /** Links module data. If `linksModuleData` is also defined on the object, both will be displayed. The maximum number of these fields displayed is 10 from class and 10 from object. */
		linksModuleData: Option[Schema.LinksModuleData] = None,
	  /** Image module data. If `imageModulesData` is also defined on the object, both will be displayed. Only one of the image from class and one from object level will be rendered when both set. */
		imageModulesData: Option[List[Schema.ImageModuleData]] = None,
	  /** Required. The unique identifier for the class. This ID must be unique across all from an issuer. This value needs to follow the format `issuerID.identifier` where `issuerID` is issued by Google and `identifier` is chosen by you. The unique identifier can only include alphanumeric characters, `.`, `_`, or `-`. */
		id: Option[String] = None
	)
	
	case class CardTemplateOverride(
	  /** Template information for rows in the card view. At most three rows are allowed to be specified. */
		cardRowTemplateInfos: Option[List[Schema.CardRowTemplateInfo]] = None
	)
	
	case class DeviceContext(
	  /** If set, redemption information will only be returned to the given device upon activation of the object. This should not be used as a stable identifier to trace a user's device. It can change across different passes for the same device or even across different activations for the same device. When setting this, callers must also set has_linked_device on the object being activated. */
		deviceToken: Option[String] = None
	)
	
	case class EventVenue(
	  /** The name of the venue, such as "AT&T Park". This is required. */
		name: Option[Schema.LocalizedString] = None,
	  /** Identifies what kind of resource this is. Value: the fixed string `"walletobjects#eventVenue"`. */
		kind: Option[String] = None,
	  /** The address of the venue, such as "24 Willie Mays Plaza\nSan Francisco, CA 94107". Address lines are separated by line feed (`\n`) characters. This is required. */
		address: Option[Schema.LocalizedString] = None
	)
	
	case class CardRowOneItem(
	  /** The item to be displayed in the row. This item will be automatically centered. */
		item: Option[Schema.TemplateItem] = None
	)
	
	case class GenericClassListResponse(
	  /** Resources corresponding to the list request. */
		resources: Option[List[Schema.GenericClass]] = None,
	  /** Pagination of the response. */
		pagination: Option[Schema.Pagination] = None
	)
	
	object FirstRowOption {
		enum TransitOptionEnum extends Enum[TransitOptionEnum] { case TRANSIT_OPTION_UNSPECIFIED, ORIGIN_AND_DESTINATION_NAMES, originAndDestinationNames, ORIGIN_AND_DESTINATION_CODES, originAndDestinationCodes, ORIGIN_NAME, originName }
	}
	case class FirstRowOption(
	  /** A reference to the field to be displayed in the first row. */
		fieldOption: Option[Schema.FieldSelector] = None,
		transitOption: Option[Schema.FirstRowOption.TransitOptionEnum] = None
	)
	
	case class DiffChecksumsResponse(
	  /** The object version of the object the checksums are being returned for. */
		objectVersion: Option[String] = None,
	  /** If set, calculate the checksums based on the contents and return them to the caller. */
		objectLocation: Option[Schema.CompositeMedia] = None,
	  /** Exactly one of these fields must be populated. If checksums_location is filled, the server will return the corresponding contents to the user. If object_location is filled, the server will calculate the checksums based on the content there and return that to the user. For details on the format of the checksums, see http://go/scotty-diff-protocol. */
		checksumsLocation: Option[Schema.CompositeMedia] = None,
	  /** The chunk size of checksums. Must be a multiple of 256KB. */
		chunkSizeBytes: Option[String] = None,
	  /** The total size of the server object. */
		objectSizeBytes: Option[String] = None
	)
	
	object MediaRequestInfo {
		enum NotificationTypeEnum extends Enum[NotificationTypeEnum] { case START, PROGRESS, END, RESPONSE_SENT, ERROR }
	}
	case class MediaRequestInfo(
	  /** Data to be copied to backend requests. Custom data is returned to Scotty in the agent_state field, which Scotty will then provide in subsequent upload notifications. */
		customData: Option[String] = None,
	  /** Set if the http request info is diff encoded. The value of this field is the version number of the base revision. This is corresponding to Apiary's mediaDiffObjectVersion (//depot/google3/java/com/google/api/server/media/variable/DiffObjectVersionVariable.java). See go/esf-scotty-diff-upload for more information. */
		diffObjectVersion: Option[String] = None,
	  /** The total size of the file. */
		totalBytes: Option[String] = None,
	  /** The existence of the final_status field indicates that this is the last call to the agent for this request_id. http://google3/uploader/agent/scotty_agent.proto?l=737&rcl=347601929 */
		finalStatus: Option[Int] = None,
	  /** The number of current bytes uploaded or downloaded. */
		currentBytes: Option[String] = None,
	  /** Whether the total bytes field contains an estimated data. */
		totalBytesIsEstimated: Option[Boolean] = None,
	  /** The partition of the Scotty server handling this request. type is uploader_service.RequestReceivedParamsServingInfo LINT.IfChange(request_received_params_serving_info_annotations) LINT.ThenChange() */
		requestReceivedParamsServingInfo: Option[String] = None,
	  /** The Scotty request ID. */
		requestId: Option[String] = None,
	  /** The type of notification received from Scotty. */
		notificationType: Option[Schema.MediaRequestInfo.NotificationTypeEnum] = None
	)
	
	case class TimeInterval(
	  /** Start time of the interval. Offset is not required. If an offset is provided and `end` time is set, `end` must also include an offset. */
		start: Option[Schema.DateTime] = None,
	  /** Identifies what kind of resource this is. Value: the fixed string `"walletobjects#timeInterval"`. */
		kind: Option[String] = None,
	  /** End time of the interval. Offset is not required. If an offset is provided and `start` time is set, `start` must also include an offset. */
		end: Option[Schema.DateTime] = None
	)
	
	case class TransitObjectUploadRotatingBarcodeValuesRequest(
	  /** A reference to the rotating barcode values payload that was uploaded. */
		blob: Option[Schema.Media] = None,
	  /** Extra information about the uploaded media. */
		mediaRequestInfo: Option[Schema.MediaRequestInfo] = None
	)
	
	case class LabelValue(
	  /** The label for a specific row and column. Recommended maximum is 15 characters for a two-column layout and 30 characters for a one-column layout. */
		label: Option[String] = None,
	  /** Translated strings for the label. Recommended maximum is 15 characters for a two-column layout and 30 characters for a one-column layout. */
		localizedLabel: Option[Schema.LocalizedString] = None,
	  /** Translated strings for the value. Recommended maximum is 15 characters for a two-column layout and 30 characters for a one-column layout. */
		localizedValue: Option[Schema.LocalizedString] = None,
	  /** The value for a specific row and column. Recommended maximum is 15 characters for a two-column layout and 30 characters for a one-column layout. */
		value: Option[String] = None
	)
	
	case class AppLinkDataAppLinkInfoAppTarget(
	  /** Package name for AppTarget. For example: com.google.android.gm */
		packageName: Option[String] = None,
	  /** URI for AppTarget. The description on the URI must be set. Prefer setting package field instead, if this target is defined for your application. */
		targetUri: Option[Schema.Uri] = None
	)
	
	case class UpcomingNotification(
	  /** Indicates if the object needs to have upcoming notification enabled. */
		enableNotification: Option[Boolean] = None
	)
	
	case class DiscoverableProgramMerchantSigninInfo(
	  /** The URL to direct the user to for the merchant's signin site. */
		signinWebsite: Option[Schema.Uri] = None
	)
	
	object TemplateItem {
		enum PredefinedItemEnum extends Enum[PredefinedItemEnum] { case PREDEFINED_ITEM_UNSPECIFIED, FREQUENT_FLYER_PROGRAM_NAME_AND_NUMBER, frequentFlyerProgramNameAndNumber, FLIGHT_NUMBER_AND_OPERATING_FLIGHT_NUMBER, flightNumberAndOperatingFlightNumber }
	}
	case class TemplateItem(
	  /** A reference to a field to display. If both `firstValue` and `secondValue` are populated, they will both appear as one item with a slash between them. For example, values A and B would be shown as "A / B". */
		firstValue: Option[Schema.FieldSelector] = None,
	  /** A predefined item to display. Only one of `firstValue` or `predefinedItem` may be set. */
		predefinedItem: Option[Schema.TemplateItem.PredefinedItemEnum] = None,
	  /** A reference to a field to display. This may only be populated if the `firstValue` field is populated. */
		secondValue: Option[Schema.FieldSelector] = None
	)
	
	case class JwtResource(
	  /** A string representing a JWT of the format described at https://developers.google.com/wallet/reference/rest/v1/Jwt */
		jwt: Option[String] = None
	)
	
	object FlightObject {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, ACTIVE, active, COMPLETED, completed, EXPIRED, expired, INACTIVE, inactive }
		enum NotifyPreferenceEnum extends Enum[NotifyPreferenceEnum] { case NOTIFICATION_SETTINGS_FOR_UPDATES_UNSPECIFIED, NOTIFY_ON_UPDATE }
	}
	case class FlightObject(
	  /** Deprecated. Use textModulesData instead. */
		infoModuleData: Option[Schema.InfoModuleData] = None,
	  /** Whether this object is currently linked to a single device. This field is set by the platform when a user saves the object, linking it to their device. Intended for use by select partners. Contact support for additional information. */
		hasLinkedDevice: Option[Boolean] = None,
	  /** Required. The state of the object. This field is used to determine how an object is displayed in the app. For example, an `inactive` object is moved to the "Expired passes" section. */
		state: Option[Schema.FlightObject.StateEnum] = None,
	  /** Note: This field is currently not supported to trigger geo notifications. */
		locations: Option[List[Schema.LatLongPoint]] = None,
	  /** Required. The class associated with this object. The class must be of the same type as this object, must already exist, and must be approved. Class IDs should follow the format issuer ID.identifier where the former is issued by Google and latter is chosen by you. */
		classId: Option[String] = None,
	  /** Links module data. If links module data is also defined on the class, both will be displayed. */
		linksModuleData: Option[Schema.LinksModuleData] = None,
	  /** The value that will be transmitted to a Smart Tap certified terminal over NFC for this object. The class level fields `enableSmartTap` and `redemptionIssuers` must also be set up correctly in order for the pass to support Smart Tap. Only ASCII characters are supported. */
		smartTapRedemptionValue: Option[String] = None,
	  /** An image for the security program that applies to the passenger. */
		securityProgramLogo: Option[Schema.Image] = None,
	  /** The barcode type and value. */
		barcode: Option[Schema.Barcode] = None,
	  /** A copy of the inherited fields of the parent class. These fields are retrieved during a GET. */
		classReference: Option[Schema.FlightClass] = None,
	  /** The rotating barcode type and value. */
		rotatingBarcode: Option[Schema.RotatingBarcode] = None,
	  /** Optional value added module data. Maximum of ten on the object. */
		valueAddedModuleData: Option[List[Schema.ValueAddedModuleData]] = None,
	  /** Required. The unique identifier for an object. This ID must be unique across all objects from an issuer. This value should follow the format issuer ID.identifier where the former is issued by Google and latter is chosen by you. The unique identifier should only include alphanumeric characters, '.', '_', or '-'. */
		id: Option[String] = None,
	  /** Required. Information about flight reservation. */
		reservationInfo: Option[Schema.ReservationInfo] = None,
	  /** Identifies what kind of resource this is. Value: the fixed string `"walletobjects#flightObject"`. */
		kind: Option[String] = None,
	  /** Optional app or website link that will be displayed as a button on the front of the pass. If AppLinkData is provided for the corresponding class only object AppLinkData will be displayed. */
		appLinkData: Option[Schema.AppLinkData] = None,
	  /** Passenger specific information about boarding and seating. */
		boardingAndSeatingInfo: Option[Schema.BoardingAndSeatingInfo] = None,
	  /** Optional banner image displayed on the front of the card. If none is present, hero image of the class, if present, will be displayed. If hero image of the class is also not present, nothing will be displayed. */
		heroImage: Option[Schema.Image] = None,
	  /** Deprecated */
		version: Option[String] = None,
	  /** Required. Passenger name as it would appear on the boarding pass. eg: "Dave M Gahan" or "Gahan/Dave" or "GAHAN/DAVEM" */
		passengerName: Option[String] = None,
	  /** Text module data. If text module data is also defined on the class, both will be displayed. The maximum number of these fields displayed is 10 from the object and 10 from the class. */
		textModulesData: Option[List[Schema.TextModuleData]] = None,
	  /** Indicates if the object has users. This field is set by the platform. */
		hasUsers: Option[Boolean] = None,
	  /** Whether or not field updates to this object should trigger notifications. When set to NOTIFY, we will attempt to trigger a field update notification to users. These notifications will only be sent to users if the field is part of an allowlist. If set to DO_NOT_NOTIFY or NOTIFICATION_SETTINGS_UNSPECIFIED, no notification will be triggered. This setting is ephemeral and needs to be set with each PATCH or UPDATE request, otherwise a notification will not be triggered. */
		notifyPreference: Option[Schema.FlightObject.NotifyPreferenceEnum] = None,
	  /** Information that controls how passes are grouped together. */
		groupingInfo: Option[Schema.GroupingInfo] = None,
	  /** The background color for the card. If not set the dominant color of the hero image is used, and if no hero image is set, the dominant color of the logo is used. The format is #rrggbb where rrggbb is a hex RGB triplet, such as `#ffcc00`. You can also use the shorthand version of the RGB triplet which is #rgb, such as `#fc0`. */
		hexBackgroundColor: Option[String] = None,
	  /** An array of messages displayed in the app. All users of this object will receive its associated messages. The maximum number of these fields is 10. */
		messages: Option[List[Schema.Message]] = None,
	  /** Pass constraints for the object. Includes limiting NFC and screenshot behaviors. */
		passConstraints: Option[Schema.PassConstraints] = None,
	  /** Image module data. The maximum number of these fields displayed is 1 from object level and 1 for class object level. */
		imageModulesData: Option[List[Schema.ImageModuleData]] = None,
	  /** The time period this object will be `active` and object can be used. An object's state will be changed to `expired` when this time period has passed. */
		validTimeInterval: Option[Schema.TimeInterval] = None,
	  /** Indicates if notifications should explicitly be suppressed. If this field is set to true, regardless of the `messages` field, expiration notifications to the user will be suppressed. By default, this field is set to false. Currently, this can only be set for Flights. */
		disableExpirationNotification: Option[Boolean] = None,
	  /** Restrictions on the object that needs to be verified before the user tries to save the pass. Note that this restrictions will only be applied during save time. If the restrictions changed after a user saves the pass, the new restrictions will not be applied to an already saved pass. */
		saveRestrictions: Option[Schema.SaveRestrictions] = None,
	  /** linked_object_ids are a list of other objects such as event ticket, loyalty, offer, generic, giftcard, transit and boarding pass that should be automatically attached to this flight object. If a user had saved this boarding pass, then these linked_object_ids would be automatically pushed to the user's wallet (unless they turned off the setting to receive such linked passes). Make sure that objects present in linked_object_ids are already inserted - if not, calls would fail. Once linked, the linked objects cannot be unlinked. You cannot link objects belonging to another issuer. There is a limit to the number of objects that can be linked to a single object. After the limit is reached, new linked objects in the call will be ignored silently. Object IDs should follow the format issuer ID. identifier where the former is issued by Google and the latter is chosen by you. */
		linkedObjectIds: Option[List[String]] = None
	)
	
	object OfferObject {
		enum NotifyPreferenceEnum extends Enum[NotifyPreferenceEnum] { case NOTIFICATION_SETTINGS_FOR_UPDATES_UNSPECIFIED, NOTIFY_ON_UPDATE }
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, ACTIVE, active, COMPLETED, completed, EXPIRED, expired, INACTIVE, inactive }
	}
	case class OfferObject(
	  /** Identifies what kind of resource this is. Value: the fixed string `"walletobjects#offerObject"`. */
		kind: Option[String] = None,
	  /** Information that controls how passes are grouped together. */
		groupingInfo: Option[Schema.GroupingInfo] = None,
	  /** Required. The unique identifier for an object. This ID must be unique across all objects from an issuer. This value should follow the format issuer ID.identifier where the former is issued by Google and latter is chosen by you. The unique identifier should only include alphanumeric characters, '.', '_', or '-'. */
		id: Option[String] = None,
	  /** The time period this object will be `active` and object can be used. An object's state will be changed to `expired` when this time period has passed. */
		validTimeInterval: Option[Schema.TimeInterval] = None,
	  /** Optional app or website link that will be displayed as a button on the front of the pass. If AppLinkData is provided for the corresponding class only object AppLinkData will be displayed. */
		appLinkData: Option[Schema.AppLinkData] = None,
	  /** Optional banner image displayed on the front of the card. If none is present, hero image of the class, if present, will be displayed. If hero image of the class is also not present, nothing will be displayed. */
		heroImage: Option[Schema.Image] = None,
	  /** Indicates if notifications should explicitly be suppressed. If this field is set to true, regardless of the `messages` field, expiration notifications to the user will be suppressed. By default, this field is set to false. Currently, this can only be set for offers. */
		disableExpirationNotification: Option[Boolean] = None,
	  /** Whether or not field updates to this object should trigger notifications. When set to NOTIFY, we will attempt to trigger a field update notification to users. These notifications will only be sent to users if the field is part of an allowlist. If set to DO_NOT_NOTIFY or NOTIFICATION_SETTINGS_UNSPECIFIED, no notification will be triggered. This setting is ephemeral and needs to be set with each PATCH or UPDATE request, otherwise a notification will not be triggered. */
		notifyPreference: Option[Schema.OfferObject.NotifyPreferenceEnum] = None,
	  /** Text module data. If text module data is also defined on the class, both will be displayed. The maximum number of these fields displayed is 10 from the object and 10 from the class. */
		textModulesData: Option[List[Schema.TextModuleData]] = None,
	  /** Required. The class associated with this object. The class must be of the same type as this object, must already exist, and must be approved. Class IDs should follow the format issuer ID.identifier where the former is issued by Google and latter is chosen by you. */
		classId: Option[String] = None,
	  /** Restrictions on the object that needs to be verified before the user tries to save the pass. Note that this restrictions will only be applied during save time. If the restrictions changed after a user saves the pass, the new restrictions will not be applied to an already saved pass. */
		saveRestrictions: Option[Schema.SaveRestrictions] = None,
	  /** The rotating barcode type and value. */
		rotatingBarcode: Option[Schema.RotatingBarcode] = None,
	  /** Note: This field is currently not supported to trigger geo notifications. */
		locations: Option[List[Schema.LatLongPoint]] = None,
	  /** A copy of the inherited fields of the parent class. These fields are retrieved during a GET. */
		classReference: Option[Schema.OfferClass] = None,
	  /** Links module data. If links module data is also defined on the class, both will be displayed. */
		linksModuleData: Option[Schema.LinksModuleData] = None,
	  /** Indicates if the object has users. This field is set by the platform. */
		hasUsers: Option[Boolean] = None,
	  /** Pass constraints for the object. Includes limiting NFC and screenshot behaviors. */
		passConstraints: Option[Schema.PassConstraints] = None,
	  /** linked_object_ids are a list of other objects such as event ticket, loyalty, offer, generic, giftcard, transit and boarding pass that should be automatically attached to this offer object. If a user had saved this offer, then these linked_object_ids would be automatically pushed to the user's wallet (unless they turned off the setting to receive such linked passes). Make sure that objects present in linked_object_ids are already inserted - if not, calls would fail. Once linked, the linked objects cannot be unlinked. You cannot link objects belonging to another issuer. There is a limit to the number of objects that can be linked to a single object. After the limit is reached, new linked objects in the call will be ignored silently. Object IDs should follow the format issuer ID.identifier where the former is issued by Google and the latter is chosen by you. */
		linkedObjectIds: Option[List[String]] = None,
	  /** An array of messages displayed in the app. All users of this object will receive its associated messages. The maximum number of these fields is 10. */
		messages: Option[List[Schema.Message]] = None,
	  /** Required. The state of the object. This field is used to determine how an object is displayed in the app. For example, an `inactive` object is moved to the "Expired passes" section. */
		state: Option[Schema.OfferObject.StateEnum] = None,
	  /** Deprecated. Use textModulesData instead. */
		infoModuleData: Option[Schema.InfoModuleData] = None,
	  /** The value that will be transmitted to a Smart Tap certified terminal over NFC for this object. The class level fields `enableSmartTap` and `redemptionIssuers` must also be set up correctly in order for the pass to support Smart Tap. Only ASCII characters are supported. */
		smartTapRedemptionValue: Option[String] = None,
	  /** Optional value added module data. Maximum of ten on the object. */
		valueAddedModuleData: Option[List[Schema.ValueAddedModuleData]] = None,
	  /** The barcode type and value. */
		barcode: Option[Schema.Barcode] = None,
	  /** Image module data. The maximum number of these fields displayed is 1 from object level and 1 for class object level. */
		imageModulesData: Option[List[Schema.ImageModuleData]] = None,
	  /** Whether this object is currently linked to a single device. This field is set by the platform when a user saves the object, linking it to their device. Intended for use by select partners. Contact support for additional information. */
		hasLinkedDevice: Option[Boolean] = None,
	  /** Deprecated */
		version: Option[String] = None
	)
	
	case class EventTicketClassListResponse(
	  /** Pagination of the response. */
		pagination: Option[Schema.Pagination] = None,
	  /** Resources corresponding to the list request. */
		resources: Option[List[Schema.EventTicketClass]] = None
	)
	
	case class TransitObjectAddMessageResponse(
	  /** The updated TransitObject resource. */
		resource: Option[Schema.TransitObject] = None
	)
	
	object RotatingBarcodeTotpDetails {
		enum AlgorithmEnum extends Enum[AlgorithmEnum] { case TOTP_ALGORITHM_UNSPECIFIED, TOTP_SHA1 }
	}
	case class RotatingBarcodeTotpDetails(
	  /** The TOTP parameters for each of the {totp_value_&#42;} substitutions. The TotpParameters at index n is used for the {totp_value_n} substitution. */
		parameters: Option[List[Schema.RotatingBarcodeTotpDetailsTotpParameters]] = None,
	  /** The time interval used for the TOTP value generation, in milliseconds. */
		periodMillis: Option[String] = None,
	  /** The TOTP algorithm used to generate the OTP. */
		algorithm: Option[Schema.RotatingBarcodeTotpDetails.AlgorithmEnum] = None
	)
	
	object SecurityAnimation {
		enum AnimationTypeEnum extends Enum[AnimationTypeEnum] { case ANIMATION_UNSPECIFIED, FOIL_SHIMMER, foilShimmer }
	}
	case class SecurityAnimation(
	  /** Type of animation. */
		animationType: Option[Schema.SecurityAnimation.AnimationTypeEnum] = None
	)
	
	case class CallbackOptions(
	  /** The HTTPS url configured by the merchant. The URL should be hosted on HTTPS and robots.txt should allow the URL path to be accessible by UserAgent:Googlebot. */
		url: Option[String] = None,
	  /** URL for the merchant endpoint that would be called to request updates. The URL should be hosted on HTTPS and robots.txt should allow the URL path to be accessible by UserAgent:Googlebot. Deprecated. */
		updateRequestUrl: Option[String] = None
	)
	
	case class RotatingBarcodeValues(
	  /** Required. The amount of time each barcode is valid for. */
		periodMillis: Option[String] = None,
	  /** Required. The values to encode in the barcode. At least one value is required. */
		values: Option[List[String]] = None,
	  /** Required. The date/time the first barcode is valid from. Barcodes will be rotated through using period_millis defined on the object's RotatingBarcodeValueInfo. This is an ISO 8601 extended format date/time, with an offset. Time may be specified up to nanosecond precision. Offsets may be specified with seconds precision (even though offset seconds is not part of ISO 8601). For example: `1985-04-12T23:20:50.52Z` would be 20 minutes and 50.52 seconds after the 23rd hour of April 12th, 1985 in UTC. `1985-04-12T19:20:50.52-04:00` would be 20 minutes and 50.52 seconds after the 19th hour of April 12th, 1985, 4 hours before UTC (same instant in time as the above example). If the event were in New York, this would be the equivalent of Eastern Daylight Time (EDT). Remember that offset varies in regions that observe Daylight Saving Time (or Summer Time), depending on the time of the year. */
		startDateTime: Option[String] = None
	)
	
	case class DownloadParameters(
	  /** Determining whether or not Apiary should skip the inclusion of any Content-Range header on its response to Scotty. */
		ignoreRange: Option[Boolean] = None,
	  /** A boolean to be returned in the response to Scotty. Allows/disallows gzip encoding of the payload content when the server thinks it's advantageous (hence, does not guarantee compression) which allows Scotty to GZip the response to the client. */
		allowGzipCompression: Option[Boolean] = None
	)
	
	case class TransitObjectListResponse(
	  /** Pagination of the response. */
		pagination: Option[Schema.Pagination] = None,
	  /** Resources corresponding to the list request. */
		resources: Option[List[Schema.TransitObject]] = None
	)
	
	object RotatingBarcode {
		enum TypeEnum extends Enum[TypeEnum] { case BARCODE_TYPE_UNSPECIFIED, AZTEC, aztec, CODE_39, code39, CODE_128, code128, CODABAR, codabar, DATA_MATRIX, dataMatrix, EAN_8, ean8, EAN_13, ean13, EAN13, ITF_14, itf14, PDF_417, pdf417, PDF417, QR_CODE, qrCode, qrcode, UPC_A, upcA, TEXT_ONLY, textOnly }
		enum RenderEncodingEnum extends Enum[RenderEncodingEnum] { case RENDER_ENCODING_UNSPECIFIED, UTF_8 }
	}
	case class RotatingBarcode(
	  /** Optional text that will be shown when the barcode is hidden behind a click action. This happens in cases where a pass has Smart Tap enabled. If not specified, a default is chosen by Google. */
		showCodeText: Option[Schema.LocalizedString] = None,
	  /** The type of this barcode. */
		`type`: Option[Schema.RotatingBarcode.TypeEnum] = None,
	  /** Details used to evaluate the {totp_value_n} substitutions. */
		totpDetails: Option[Schema.RotatingBarcodeTotpDetails] = None,
	  /** Input only. NOTE: This feature is only available for the transit vertical. Optional set of initial rotating barcode values. This allows a small subset of barcodes to be included with the object. Further rotating barcode values must be uploaded with the UploadRotatingBarcodeValues endpoint. */
		initialRotatingBarcodeValues: Option[Schema.RotatingBarcodeValues] = None,
	  /** An optional text that will override the default text that shows under the barcode. This field is intended for a human readable equivalent of the barcode value, used when the barcode cannot be scanned. */
		alternateText: Option[String] = None,
	  /** String encoded barcode value. This string supports the following substitutions: &#42; {totp_value_n}: Replaced with the TOTP value (see TotpDetails.parameters). &#42; {totp_timestamp_millis}: Replaced with the timestamp (millis since epoch) at which the barcode was generated. &#42; {totp_timestamp_seconds}: Replaced with the timestamp (seconds since epoch) at which the barcode was generated. */
		valuePattern: Option[String] = None,
	  /** The render encoding for the barcode. When specified, barcode is rendered in the given encoding. Otherwise best known encoding is chosen by Google. */
		renderEncoding: Option[Schema.RotatingBarcode.RenderEncodingEnum] = None
	)
	
	case class DiffVersionResponse(
	  /** The version of the object stored at the server. */
		objectVersion: Option[String] = None,
	  /** The total size of the server object. */
		objectSizeBytes: Option[String] = None
	)
	
	case class CardBarcodeSectionDetails(
	  /** Optional information to display above the barcode. If `secondTopDetail` is defined, this will be displayed to the start side of this detail section. */
		firstTopDetail: Option[Schema.BarcodeSectionDetail] = None,
	  /** Optional second piece of information to display above the barcode. If `firstTopDetail` is defined, this will be displayed to the end side of this detail section. */
		secondTopDetail: Option[Schema.BarcodeSectionDetail] = None,
	  /** Optional information to display below the barcode. */
		firstBottomDetail: Option[Schema.BarcodeSectionDetail] = None
	)
	
	case class RotatingBarcodeTotpDetailsTotpParameters(
	  /** The length of the TOTP value in decimal digits. */
		valueLength: Option[Int] = None,
	  /** The secret key used for the TOTP value generation, encoded as a Base16 string. */
		key: Option[String] = None
	)
	
	object FlightClass {
		enum NotifyPreferenceEnum extends Enum[NotifyPreferenceEnum] { case NOTIFICATION_SETTINGS_FOR_UPDATES_UNSPECIFIED, NOTIFY_ON_UPDATE }
		enum FlightStatusEnum extends Enum[FlightStatusEnum] { case FLIGHT_STATUS_UNSPECIFIED, SCHEDULED, scheduled, ACTIVE, active, LANDED, landed, CANCELLED, cancelled, REDIRECTED, redirected, DIVERTED, diverted }
		enum MultipleDevicesAndHoldersAllowedStatusEnum extends Enum[MultipleDevicesAndHoldersAllowedStatusEnum] { case STATUS_UNSPECIFIED, MULTIPLE_HOLDERS, ONE_USER_ALL_DEVICES, ONE_USER_ONE_DEVICE, multipleHolders, oneUserAllDevices, oneUserOneDevice }
		enum ViewUnlockRequirementEnum extends Enum[ViewUnlockRequirementEnum] { case VIEW_UNLOCK_REQUIREMENT_UNSPECIFIED, UNLOCK_NOT_REQUIRED, UNLOCK_REQUIRED_TO_VIEW }
		enum ReviewStatusEnum extends Enum[ReviewStatusEnum] { case REVIEW_STATUS_UNSPECIFIED, UNDER_REVIEW, underReview, APPROVED, approved, REJECTED, rejected, DRAFT, draft }
	}
	case class FlightClass(
	  /** Image module data. The maximum number of these fields displayed is 1 from object level and 1 for class object level. */
		imageModulesData: Option[List[Schema.ImageModuleData]] = None,
	  /** The URI of your application's home page. Populating the URI in this field results in the exact same behavior as populating an URI in linksModuleData (when an object is rendered, a link to the homepage is shown in what would usually be thought of as the linksModuleData section of the object). */
		homepageUri: Option[Schema.Uri] = None,
	  /** Policies for boarding and seating. These will inform which labels will be shown to users. */
		boardingAndSeatingPolicy: Option[Schema.BoardingAndSeatingPolicy] = None,
	  /** Whether or not field updates to this class should trigger notifications. When set to NOTIFY, we will attempt to trigger a field update notification to users. These notifications will only be sent to users if the field is part of an allowlist. If not specified, no notification will be triggered. This setting is ephemeral and needs to be set with each PATCH or UPDATE request, otherwise a notification will not be triggered. */
		notifyPreference: Option[Schema.FlightClass.NotifyPreferenceEnum] = None,
	  /** An array of messages displayed in the app. All users of this object will receive its associated messages. The maximum number of these fields is 10. */
		messages: Option[List[Schema.Message]] = None,
	  /** Required. Information about the flight carrier and number. */
		flightHeader: Option[Schema.FlightHeader] = None,
	  /** The gate closing time as it would be printed on the boarding pass. Do not set this field if you do not want to print it in the boarding pass. This is an ISO 8601 extended format date/time without an offset. Time may be specified up to millisecond precision. eg: `2027-03-05T06:30:00` This should be the local date/time at the airport (not a UTC time). Google will reject the request if UTC offset is provided. Time zones will be calculated by Google based on departure airport. */
		localGateClosingDateTime: Option[String] = None,
	  /** Identifies which redemption issuers can redeem the pass over Smart Tap. Redemption issuers are identified by their issuer ID. Redemption issuers must have at least one Smart Tap key configured. The `enableSmartTap` and object level `smartTapRedemptionLevel` fields must also be set up correctly in order for a pass to support Smart Tap. */
		redemptionIssuers: Option[List[String]] = None,
	  /** Links module data. If links module data is also defined on the object, both will be displayed. */
		linksModuleData: Option[Schema.LinksModuleData] = None,
	  /** The boarding time as it would be printed on the boarding pass. This is an ISO 8601 extended format date/time without an offset. Time may be specified up to millisecond precision. eg: `2027-03-05T06:30:00` This should be the local date/time at the airport (not a UTC time). Google will reject the request if UTC offset is provided. Time zones will be calculated by Google based on departure airport. */
		localBoardingDateTime: Option[String] = None,
	  /** Callback options to be used to call the issuer back for every save/delete of an object for this class by the end-user. All objects of this class are eligible for the callback. */
		callbackOptions: Option[Schema.CallbackOptions] = None,
	  /** Deprecated. */
		wordMark: Option[Schema.Image] = None,
	  /** Deprecated. Use `multipleDevicesAndHoldersAllowedStatus` instead. */
		allowMultipleUsersPerObject: Option[Boolean] = None,
	  /** Identifies what kind of resource this is. Value: the fixed string `"walletobjects#flightClass"`. */
		kind: Option[String] = None,
	  /** Status of this flight. If unset, Google will compute status based on data from other sources, such as FlightStats, etc. Note: Google-computed status will not be returned in API responses. */
		flightStatus: Option[Schema.FlightClass.FlightStatusEnum] = None,
	  /** Optional banner image displayed on the front of the card. If none is present, nothing will be displayed. The image will display at 100% width. */
		heroImage: Option[Schema.Image] = None,
	  /** The review comments set by the platform when a class is marked `approved` or `rejected`. */
		review: Option[Schema.Review] = None,
	  /** Text module data. If text module data is also defined on the class, both will be displayed. The maximum number of these fields displayed is 10 from the object and 10 from the class. */
		textModulesData: Option[List[Schema.TextModuleData]] = None,
	  /** Identifies whether this class supports Smart Tap. The `redemptionIssuers` and object level `smartTapRedemptionLevel` fields must also be set up correctly in order for a pass to support Smart Tap. */
		enableSmartTap: Option[Boolean] = None,
	  /** Required. The issuer name. Recommended maximum length is 20 characters to ensure full string is displayed on smaller screens. */
		issuerName: Option[String] = None,
	  /** Identifies whether multiple users and devices will save the same object referencing this class. */
		multipleDevicesAndHoldersAllowedStatus: Option[Schema.FlightClass.MultipleDevicesAndHoldersAllowedStatusEnum] = None,
	  /** If this field is present, boarding passes served to a user's device will always be in this language. Represents the BCP 47 language tag. Example values are "en-US", "en-GB", "de", or "de-AT". */
		languageOverride: Option[String] = None,
	  /** Deprecated. Use textModulesData instead. */
		infoModuleData: Option[Schema.InfoModuleData] = None,
	  /** The background color for the card. If not set the dominant color of the hero image is used, and if no hero image is set, the dominant color of the logo is used. The format is #rrggbb where rrggbb is a hex RGB triplet, such as `#ffcc00`. You can also use the shorthand version of the RGB triplet which is #rgb, such as `#fc0`. */
		hexBackgroundColor: Option[String] = None,
	  /** The estimated time the aircraft plans to pull from the gate or the actual time the aircraft already pulled from the gate. Note: This is not the runway time. This field should be set if at least one of the below is true: - It differs from the scheduled time. Google will use it to calculate the delay. - The aircraft already pulled from the gate. Google will use it to inform the user when the flight actually departed. This is an ISO 8601 extended format date/time without an offset. Time may be specified up to millisecond precision. eg: `2027-03-05T06:30:00` This should be the local date/time at the airport (not a UTC time). Google will reject the request if UTC offset is provided. Time zones will be calculated by Google based on departure airport. */
		localEstimatedOrActualDepartureDateTime: Option[String] = None,
	  /** Required. Origin airport. */
		origin: Option[Schema.AirportInfo] = None,
	  /** The scheduled time the aircraft plans to reach the destination gate (not the runway). Note: This field should not change too close to the flight time. For updates to departure times (delays, etc), please set `localEstimatedOrActualArrivalDateTime`. This is an ISO 8601 extended format date/time without an offset. Time may be specified up to millisecond precision. eg: `2027-03-05T06:30:00` This should be the local date/time at the airport (not a UTC time). Google will reject the request if UTC offset is provided. Time zones will be calculated by Google based on arrival airport. */
		localScheduledArrivalDateTime: Option[String] = None,
	  /** The estimated time the aircraft plans to reach the destination gate (not the runway) or the actual time it reached the gate. This field should be set if at least one of the below is true: - It differs from the scheduled time. Google will use it to calculate the delay. - The aircraft already arrived at the gate. Google will use it to inform the user that the flight has arrived at the gate. This is an ISO 8601 extended format date/time without an offset. Time may be specified up to millisecond precision. eg: `2027-03-05T06:30:00` This should be the local date/time at the airport (not a UTC time). Google will reject the request if UTC offset is provided. Time zones will be calculated by Google based on arrival airport. */
		localEstimatedOrActualArrivalDateTime: Option[String] = None,
	  /** Optional value added module data. Maximum of ten on the class. For a pass only ten will be displayed, prioritizing those from the object. */
		valueAddedModuleData: Option[List[Schema.ValueAddedModuleData]] = None,
	  /** Deprecated */
		version: Option[String] = None,
	  /** Required. Destination airport. */
		destination: Option[Schema.AirportInfo] = None,
	  /** Required. The unique identifier for a class. This ID must be unique across all classes from an issuer. This value should follow the format issuer ID. identifier where the former is issued by Google and latter is chosen by you. Your unique identifier should only include alphanumeric characters, '.', '_', or '-'. */
		id: Option[String] = None,
	  /** Optional information about the security animation. If this is set a security animation will be rendered on pass details. */
		securityAnimation: Option[Schema.SecurityAnimation] = None,
	  /** Required. The scheduled date and time when the aircraft is expected to depart the gate (not the runway) Note: This field should not change too close to the departure time. For updates to departure times (delays, etc), please set `localEstimatedOrActualDepartureDateTime`. This is an ISO 8601 extended format date/time without an offset. Time may be specified up to millisecond precision. eg: `2027-03-05T06:30:00` This should be the local date/time at the airport (not a UTC time). Google will reject the request if UTC offset is provided. Time zones will be calculated by Google based on departure airport. */
		localScheduledDepartureDateTime: Option[String] = None,
	  /** Template information about how the class should be displayed. If unset, Google will fallback to a default set of fields to display. */
		classTemplateInfo: Option[Schema.ClassTemplateInfo] = None,
	  /** Translated strings for the issuer_name. Recommended maximum length is 20 characters to ensure full string is displayed on smaller screens. */
		localizedIssuerName: Option[Schema.LocalizedString] = None,
	  /** Country code used to display the card's country (when the user is not in that country), as well as to display localized content when content is not available in the user's locale. */
		countryCode: Option[String] = None,
	  /** View Unlock Requirement options for the boarding pass. */
		viewUnlockRequirement: Option[Schema.FlightClass.ViewUnlockRequirementEnum] = None,
	  /** Optional app or website link that will be displayed as a button on the front of the pass. If AppLinkData is provided for the corresponding object that will be used instead. */
		appLinkData: Option[Schema.AppLinkData] = None,
	  /** Note: This field is currently not supported to trigger geo notifications. */
		locations: Option[List[Schema.LatLongPoint]] = None,
	  /** Required. The status of the class. This field can be set to `draft` or `underReview` using the insert, patch, or update API calls. Once the review state is changed from `draft` it may not be changed back to `draft`. You should keep this field to `draft` when the class is under development. A `draft` class cannot be used to create any object. You should set this field to `underReview` when you believe the class is ready for use. The platform will automatically set this field to `approved` and it can be immediately used to create or migrate objects. When updating an already `approved` class you should keep setting this field to `underReview`. */
		reviewStatus: Option[Schema.FlightClass.ReviewStatusEnum] = None
	)
	
	case class GiftCardObjectListResponse(
	  /** Resources corresponding to the list request. */
		resources: Option[List[Schema.GiftCardObject]] = None,
	  /** Pagination of the response. */
		pagination: Option[Schema.Pagination] = None
	)
	
	object PassConstraints {
		enum NfcConstraintEnum extends Enum[NfcConstraintEnum] { case NFC_CONSTRAINT_UNSPECIFIED, BLOCK_PAYMENT, BLOCK_CLOSED_LOOP_TRANSIT }
		enum ScreenshotEligibilityEnum extends Enum[ScreenshotEligibilityEnum] { case SCREENSHOT_ELIGIBILITY_UNSPECIFIED, ELIGIBLE, INELIGIBLE }
	}
	case class PassConstraints(
	  /** The NFC constraints for the pass. */
		nfcConstraint: Option[List[Schema.PassConstraints.NfcConstraintEnum]] = None,
	  /** The screenshot eligibility for the pass. */
		screenshotEligibility: Option[Schema.PassConstraints.ScreenshotEligibilityEnum] = None
	)
	
	case class DetailsItemInfo(
	  /** The item to be displayed in the details list. */
		item: Option[Schema.TemplateItem] = None
	)
	
	case class LoyaltyClassAddMessageResponse(
	  /** The updated LoyaltyClass resource. */
		resource: Option[Schema.LoyaltyClass] = None
	)
	
	case class SignUpInfo(
	  /** ID of the class the user can sign up for. */
		classId: Option[String] = None
	)
	
	case class TransitClassAddMessageResponse(
	  /** The updated TransitClass resource. */
		resource: Option[Schema.TransitClass] = None
	)
	
	object IssuerToUserInfo {
		enum ActionEnum extends Enum[ActionEnum] { case ACTION_UNSPECIFIED, S2AP, s2ap, SIGN_UP, signUp }
	}
	case class IssuerToUserInfo(
		action: Option[Schema.IssuerToUserInfo.ActionEnum] = None,
	  /** Currently not used, consider deprecating. */
		url: Option[String] = None,
	  /** JSON web token for action S2AP. */
		value: Option[String] = None,
		signUpInfo: Option[Schema.SignUpInfo] = None
	)
	
	case class Notifications(
	  /** A notification would be triggered at a specific time before the card becomes usable. */
		upcomingNotification: Option[Schema.UpcomingNotification] = None,
	  /** A notification would be triggered at a specific time before the card expires. */
		expiryNotification: Option[Schema.ExpiryNotification] = None
	)
	
	case class GiftCardClassListResponse(
	  /** Resources corresponding to the list request. */
		resources: Option[List[Schema.GiftCardClass]] = None,
	  /** Pagination of the response. */
		pagination: Option[Schema.Pagination] = None
	)
	
	object Permission {
		enum RoleEnum extends Enum[RoleEnum] { case ROLE_UNSPECIFIED, OWNER, owner, READER, reader, WRITER, writer }
	}
	case class Permission(
	  /** The email address of the user, group, or service account to which this permission refers to. */
		emailAddress: Option[String] = None,
	  /** The role granted by this permission. */
		role: Option[Schema.Permission.RoleEnum] = None
	)
	
	object OfferClass {
		enum RedemptionChannelEnum extends Enum[RedemptionChannelEnum] { case REDEMPTION_CHANNEL_UNSPECIFIED, INSTORE, instore, ONLINE, online, BOTH, both, TEMPORARY_PRICE_REDUCTION, temporaryPriceReduction }
		enum ViewUnlockRequirementEnum extends Enum[ViewUnlockRequirementEnum] { case VIEW_UNLOCK_REQUIREMENT_UNSPECIFIED, UNLOCK_NOT_REQUIRED, UNLOCK_REQUIRED_TO_VIEW }
		enum ReviewStatusEnum extends Enum[ReviewStatusEnum] { case REVIEW_STATUS_UNSPECIFIED, UNDER_REVIEW, underReview, APPROVED, approved, REJECTED, rejected, DRAFT, draft }
		enum NotifyPreferenceEnum extends Enum[NotifyPreferenceEnum] { case NOTIFICATION_SETTINGS_FOR_UPDATES_UNSPECIFIED, NOTIFY_ON_UPDATE }
		enum MultipleDevicesAndHoldersAllowedStatusEnum extends Enum[MultipleDevicesAndHoldersAllowedStatusEnum] { case STATUS_UNSPECIFIED, MULTIPLE_HOLDERS, ONE_USER_ALL_DEVICES, ONE_USER_ONE_DEVICE, multipleHolders, oneUserAllDevices, oneUserOneDevice }
	}
	case class OfferClass(
	  /** Identifies which redemption issuers can redeem the pass over Smart Tap. Redemption issuers are identified by their issuer ID. Redemption issuers must have at least one Smart Tap key configured. The `enableSmartTap` and object level `smartTapRedemptionLevel` fields must also be set up correctly in order for a pass to support Smart Tap. */
		redemptionIssuers: Option[List[String]] = None,
	  /** Translated strings for the details. */
		localizedDetails: Option[Schema.LocalizedString] = None,
	  /** Deprecated */
		version: Option[String] = None,
	  /** Required. The title of the offer, such as "20% off any t-shirt." Recommended maximum length is 60 characters to ensure full string is displayed on smaller screens. */
		title: Option[String] = None,
	  /** The details of the offer. */
		details: Option[String] = None,
	  /** Required. The redemption channels applicable to this offer. */
		redemptionChannel: Option[Schema.OfferClass.RedemptionChannelEnum] = None,
	  /** Country code used to display the card's country (when the user is not in that country), as well as to display localized content when content is not available in the user's locale. */
		countryCode: Option[String] = None,
	  /** Translated strings for the short title. Recommended maximum length is 20 characters. */
		localizedShortTitle: Option[Schema.LocalizedString] = None,
	  /** Optional app or website link that will be displayed as a button on the front of the pass. If AppLinkData is provided for the corresponding object that will be used instead. */
		appLinkData: Option[Schema.AppLinkData] = None,
	  /** Links module data. If links module data is also defined on the object, both will be displayed. */
		linksModuleData: Option[Schema.LinksModuleData] = None,
	  /** Deprecated. Use textModulesData instead. */
		infoModuleData: Option[Schema.InfoModuleData] = None,
	  /** Optional banner image displayed on the front of the card. If none is present, nothing will be displayed. The image will display at 100% width. */
		heroImage: Option[Schema.Image] = None,
	  /** A shortened version of the title of the offer, such as "20% off," shown to users as a quick reference to the offer contents. Recommended maximum length is 20 characters. */
		shortTitle: Option[String] = None,
	  /** Identifies what kind of resource this is. Value: the fixed string `"walletobjects#offerClass"`. */
		kind: Option[String] = None,
	  /** View Unlock Requirement options for the offer. */
		viewUnlockRequirement: Option[Schema.OfferClass.ViewUnlockRequirementEnum] = None,
	  /** Required. The issuer name. Recommended maximum length is 20 characters to ensure full string is displayed on smaller screens. */
		issuerName: Option[String] = None,
	  /** Optional information about the security animation. If this is set a security animation will be rendered on pass details. */
		securityAnimation: Option[Schema.SecurityAnimation] = None,
	  /** Required. The status of the class. This field can be set to `draft` or The status of the class. This field can be set to `draft` or `underReview` using the insert, patch, or update API calls. Once the review state is changed from `draft` it may not be changed back to `draft`. You should keep this field to `draft` when the class is under development. A `draft` class cannot be used to create any object. You should set this field to `underReview` when you believe the class is ready for use. The platform will automatically set this field to `approved` and it can be immediately used to create or migrate objects. When updating an already `approved` class you should keep setting this field to `underReview`. */
		reviewStatus: Option[Schema.OfferClass.ReviewStatusEnum] = None,
	  /** Translated strings for the issuer_name. Recommended maximum length is 20 characters to ensure full string is displayed on smaller screens. */
		localizedIssuerName: Option[Schema.LocalizedString] = None,
	  /** Optional value added module data. Maximum of ten on the class. For a pass only ten will be displayed, prioritizing those from the object. */
		valueAddedModuleData: Option[List[Schema.ValueAddedModuleData]] = None,
	  /** The fine print or terms of the offer, such as "20% off any t-shirt at Adam's Apparel." */
		finePrint: Option[String] = None,
	  /** Image module data. The maximum number of these fields displayed is 1 from object level and 1 for class object level. */
		imageModulesData: Option[List[Schema.ImageModuleData]] = None,
	  /** Template information about how the class should be displayed. If unset, Google will fallback to a default set of fields to display. */
		classTemplateInfo: Option[Schema.ClassTemplateInfo] = None,
	  /** The title image of the offer. This image is displayed in both the details and list views of the app. */
		titleImage: Option[Schema.Image] = None,
	  /** Deprecated. Use `multipleDevicesAndHoldersAllowedStatus` instead. */
		allowMultipleUsersPerObject: Option[Boolean] = None,
	  /** Whether or not field updates to this class should trigger notifications. When set to NOTIFY, we will attempt to trigger a field update notification to users. These notifications will only be sent to users if the field is part of an allowlist. If not specified, no notification will be triggered. This setting is ephemeral and needs to be set with each PATCH or UPDATE request, otherwise a notification will not be triggered. */
		notifyPreference: Option[Schema.OfferClass.NotifyPreferenceEnum] = None,
	  /** Callback options to be used to call the issuer back for every save/delete of an object for this class by the end-user. All objects of this class are eligible for the callback. */
		callbackOptions: Option[Schema.CallbackOptions] = None,
	  /** Deprecated. */
		wordMark: Option[Schema.Image] = None,
	  /** Text module data. If text module data is also defined on the class, both will be displayed. The maximum number of these fields displayed is 10 from the object and 10 from the class. */
		textModulesData: Option[List[Schema.TextModuleData]] = None,
	  /** The wide title image of the offer. When provided, this will be used in place of the title image in the top left of the card view. */
		wideTitleImage: Option[Schema.Image] = None,
	  /** Identifies whether this class supports Smart Tap. The `redemptionIssuers` and object level `smartTapRedemptionLevel` fields must also be set up correctly in order for a pass to support Smart Tap. */
		enableSmartTap: Option[Boolean] = None,
	  /** The review comments set by the platform when a class is marked `approved` or `rejected`. */
		review: Option[Schema.Review] = None,
	  /** The help link for the offer, such as `http://myownpersonaldomain.com/help` */
		helpUri: Option[Schema.Uri] = None,
	  /** An array of messages displayed in the app. All users of this object will receive its associated messages. The maximum number of these fields is 10. */
		messages: Option[List[Schema.Message]] = None,
	  /** Translated strings for the fine_print. */
		localizedFinePrint: Option[Schema.LocalizedString] = None,
	  /** Required. The unique identifier for a class. This ID must be unique across all classes from an issuer. This value should follow the format issuer ID. identifier where the former is issued by Google and latter is chosen by you. Your unique identifier should only include alphanumeric characters, '.', '_', or '-'. */
		id: Option[String] = None,
	  /** Translated strings for the title. Recommended maximum length is 60 characters to ensure full string is displayed on smaller screens. */
		localizedTitle: Option[Schema.LocalizedString] = None,
	  /** Identifies whether multiple users and devices will save the same object referencing this class. */
		multipleDevicesAndHoldersAllowedStatus: Option[Schema.OfferClass.MultipleDevicesAndHoldersAllowedStatusEnum] = None,
	  /** Translated strings for the provider. Recommended maximum length is 12 characters to ensure full string is displayed on smaller screens. */
		localizedProvider: Option[Schema.LocalizedString] = None,
	  /** The URI of your application's home page. Populating the URI in this field results in the exact same behavior as populating an URI in linksModuleData (when an object is rendered, a link to the homepage is shown in what would usually be thought of as the linksModuleData section of the object). */
		homepageUri: Option[Schema.Uri] = None,
	  /** Required. The offer provider (either the aggregator name or merchant name). Recommended maximum length is 12 characters to ensure full string is displayed on smaller screens. */
		provider: Option[String] = None,
	  /** Note: This field is currently not supported to trigger geo notifications. */
		locations: Option[List[Schema.LatLongPoint]] = None,
	  /** The background color for the card. If not set the dominant color of the hero image is used, and if no hero image is set, the dominant color of the logo is used. The format is #rrggbb where rrggbb is a hex RGB triplet, such as `#ffcc00`. You can also use the shorthand version of the RGB triplet which is #rgb, such as `#fc0`. */
		hexBackgroundColor: Option[String] = None
	)
	
	object DiscoverableProgramMerchantSignupInfo {
		enum SignupSharedDatasEnum extends Enum[SignupSharedDatasEnum] { case SHARED_DATA_TYPE_UNSPECIFIED, FIRST_NAME, LAST_NAME, STREET_ADDRESS, ADDRESS_LINE_1, ADDRESS_LINE_2, ADDRESS_LINE_3, CITY, STATE, ZIPCODE, COUNTRY, EMAIL, PHONE }
	}
	case class DiscoverableProgramMerchantSignupInfo(
	  /**  User data that is sent in a POST request to the signup website URL. This information is encoded and then shared so that the merchant's website can prefill fields used to enroll the user for the discoverable program. */
		signupSharedDatas: Option[List[Schema.DiscoverableProgramMerchantSignupInfo.SignupSharedDatasEnum]] = None,
	  /** The URL to direct the user to for the merchant's signup site. */
		signupWebsite: Option[Schema.Uri] = None
	)
	
	case class GroupingInfo(
	  /** Optional grouping ID for grouping the passes with the same ID visually together. Grouping with different types of passes is allowed. */
		groupingId: Option[String] = None,
	  /** Optional index for sorting the passes when they are grouped with other passes. Passes with lower sort index are shown before passes with higher sort index. If unspecified, the value is assumed to be INT_MAX. For two passes with the same sort index, the sorting behavior is undefined. */
		sortIndex: Option[Int] = None
	)
	
	case class TicketCost(
	  /** A message describing any kind of discount that was applied. */
		discountMessage: Option[Schema.LocalizedString] = None,
	  /** The face value of the ticket. */
		faceValue: Option[Schema.Money] = None,
	  /** The actual purchase price of the ticket, after tax and/or discounts. */
		purchasePrice: Option[Schema.Money] = None
	)
	
	case class DiffUploadRequest(
	  /** The object version of the object that is the base version the incoming diff script will be applied to. This field will always be filled in. */
		objectVersion: Option[String] = None,
	  /** The location of the checksums for the new object. Agents must clone the object located here, as the upload server will delete the contents once a response is received. For details on the format of the checksums, see http://go/scotty-diff-protocol. */
		checksumsInfo: Option[Schema.CompositeMedia] = None,
	  /** The location of the new object. Agents must clone the object located here, as the upload server will delete the contents once a response is received. */
		objectInfo: Option[Schema.CompositeMedia] = None
	)
	
	object ActivationStatus {
		enum StateEnum extends Enum[StateEnum] { case UNKNOWN_STATE, NOT_ACTIVATED, not_activated, ACTIVATED, activated }
	}
	case class ActivationStatus(
		state: Option[Schema.ActivationStatus.StateEnum] = None
	)
	
	object GiftCardObject {
		enum NotifyPreferenceEnum extends Enum[NotifyPreferenceEnum] { case NOTIFICATION_SETTINGS_FOR_UPDATES_UNSPECIFIED, NOTIFY_ON_UPDATE }
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, ACTIVE, active, COMPLETED, completed, EXPIRED, expired, INACTIVE, inactive }
	}
	case class GiftCardObject(
	  /** Whether or not field updates to this object should trigger notifications. When set to NOTIFY, we will attempt to trigger a field update notification to users. These notifications will only be sent to users if the field is part of an allowlist. If set to DO_NOT_NOTIFY or NOTIFICATION_SETTINGS_UNSPECIFIED, no notification will be triggered. This setting is ephemeral and needs to be set with each PATCH or UPDATE request, otherwise a notification will not be triggered. */
		notifyPreference: Option[Schema.GiftCardObject.NotifyPreferenceEnum] = None,
	  /** Optional app or website link that will be displayed as a button on the front of the pass. If AppLinkData is provided for the corresponding class only object AppLinkData will be displayed. */
		appLinkData: Option[Schema.AppLinkData] = None,
	  /** Image module data. The maximum number of these fields displayed is 1 from object level and 1 for class object level. */
		imageModulesData: Option[List[Schema.ImageModuleData]] = None,
	  /** Text module data. If text module data is also defined on the class, both will be displayed. The maximum number of these fields displayed is 10 from the object and 10 from the class. */
		textModulesData: Option[List[Schema.TextModuleData]] = None,
	  /** Indicates if notifications should explicitly be suppressed. If this field is set to true, regardless of the `messages` field, expiration notifications to the user will be suppressed. By default, this field is set to false. Currently, this can only be set for offers. */
		disableExpirationNotification: Option[Boolean] = None,
	  /** Deprecated. Use textModulesData instead. */
		infoModuleData: Option[Schema.InfoModuleData] = None,
	  /** The card's event number, an optional field used by some gift cards. */
		eventNumber: Option[String] = None,
	  /** The card's PIN. */
		pin: Option[String] = None,
	  /** The time period this object will be `active` and object can be used. An object's state will be changed to `expired` when this time period has passed. */
		validTimeInterval: Option[Schema.TimeInterval] = None,
	  /** A copy of the inherited fields of the parent class. These fields are retrieved during a GET. */
		classReference: Option[Schema.GiftCardClass] = None,
	  /** The value that will be transmitted to a Smart Tap certified terminal over NFC for this object. The class level fields `enableSmartTap` and `redemptionIssuers` must also be set up correctly in order for the pass to support Smart Tap. Only ASCII characters are supported. */
		smartTapRedemptionValue: Option[String] = None,
	  /** The card's monetary balance. */
		balance: Option[Schema.Money] = None,
	  /** The rotating barcode type and value. */
		rotatingBarcode: Option[Schema.RotatingBarcode] = None,
	  /** Required. The state of the object. This field is used to determine how an object is displayed in the app. For example, an `inactive` object is moved to the "Expired passes" section. */
		state: Option[Schema.GiftCardObject.StateEnum] = None,
	  /** Restrictions on the object that needs to be verified before the user tries to save the pass. Note that this restrictions will only be applied during save time. If the restrictions changed after a user saves the pass, the new restrictions will not be applied to an already saved pass. */
		saveRestrictions: Option[Schema.SaveRestrictions] = None,
	  /** Deprecated */
		version: Option[String] = None,
	  /** Pass constraints for the object. Includes limiting NFC and screenshot behaviors. */
		passConstraints: Option[Schema.PassConstraints] = None,
	  /** Links module data. If links module data is also defined on the class, both will be displayed. */
		linksModuleData: Option[Schema.LinksModuleData] = None,
	  /** linked_object_ids are a list of other objects such as event ticket, loyalty, offer, generic, giftcard, transit and boarding pass that should be automatically attached to this giftcard object. If a user had saved this gift card, then these linked_object_ids would be automatically pushed to the user's wallet (unless they turned off the setting to receive such linked passes). Make sure that objects present in linked_object_ids are already inserted - if not, calls would fail. Once linked, the linked objects cannot be unlinked. You cannot link objects belonging to another issuer. There is a limit to the number of objects that can be linked to a single object. After the limit is reached, new linked objects in the call will be ignored silently. Object IDs should follow the format issuer ID. identifier where the former is issued by Google and the latter is chosen by you. */
		linkedObjectIds: Option[List[String]] = None,
	  /** The barcode type and value. */
		barcode: Option[Schema.Barcode] = None,
	  /** Whether this object is currently linked to a single device. This field is set by the platform when a user saves the object, linking it to their device. Intended for use by select partners. Contact support for additional information. */
		hasLinkedDevice: Option[Boolean] = None,
	  /** The date and time when the balance was last updated. Offset is required. If balance is updated and this property is not provided, system will default to the current time. */
		balanceUpdateTime: Option[Schema.DateTime] = None,
	  /** An array of messages displayed in the app. All users of this object will receive its associated messages. The maximum number of these fields is 10. */
		messages: Option[List[Schema.Message]] = None,
	  /** Note: This field is currently not supported to trigger geo notifications. */
		locations: Option[List[Schema.LatLongPoint]] = None,
	  /** Information that controls how passes are grouped together. */
		groupingInfo: Option[Schema.GroupingInfo] = None,
	  /** Required. The unique identifier for an object. This ID must be unique across all objects from an issuer. This value should follow the format issuer ID.identifier where the former is issued by Google and latter is chosen by you. The unique identifier should only include alphanumeric characters, '.', '_', or '-'. */
		id: Option[String] = None,
	  /** Indicates if the object has users. This field is set by the platform. */
		hasUsers: Option[Boolean] = None,
	  /** Optional banner image displayed on the front of the card. If none is present, hero image of the class, if present, will be displayed. If hero image of the class is also not present, nothing will be displayed. */
		heroImage: Option[Schema.Image] = None,
	  /** Required. The class associated with this object. The class must be of the same type as this object, must already exist, and must be approved. Class IDs should follow the format issuer ID.identifier where the former is issued by Google and latter is chosen by you. */
		classId: Option[String] = None,
	  /** Identifies what kind of resource this is. Value: the fixed string `"walletobjects#giftCardObject"`. */
		kind: Option[String] = None,
	  /** Optional value added module data. Maximum of ten on the object. */
		valueAddedModuleData: Option[List[Schema.ValueAddedModuleData]] = None,
	  /** Required. The card's number. */
		cardNumber: Option[String] = None
	)
	
	case class ListTemplateOverride(
	  /** An unused/deprecated field. Setting it will have no effect on what the user sees. */
		thirdRowOption: Option[Schema.FieldSelector] = None,
	  /** Specifies from a predefined set of options or from a reference to the field what will be displayed in the first row. To set this override, set the FirstRowOption.fieldOption to the FieldSelector of your choice. */
		firstRowOption: Option[Schema.FirstRowOption] = None,
	  /** A reference to the field to be displayed in the second row. This option is only displayed if there are not multiple user objects in a group. If there is a group, the second row will always display a field shared by all objects. To set this override, please set secondRowOption to the FieldSelector of you choice. */
		secondRowOption: Option[Schema.FieldSelector] = None
	)
	
	case class SaveRestrictions(
	  /** Restrict the save of the referencing object to the given email address only. This is the hex output of SHA256 sum of the email address, all lowercase and without any notations like "." or "+", except "@". For example, for example@example.com, this value will be 31c5543c1734d25c7206f5fd591525d0295bec6fe84ff82f946a34fe970a1e66 and for Example@example.com, this value will be bc34f262c93ad7122763684ccea6f07fb7f5d8a2d11e60ce15a6f43fe70ce632 If email address of the logged-in user who tries to save this pass does not match with the defined value here, users won't be allowed to save this pass. They will instead be prompted with an error to contact the issuer. This information should be gathered from the user with an explicit consent via Sign in with Google integration https://developers.google.com/identity/authentication. Please contact with support before using Save Restrictions. */
		restrictToEmailSha256: Option[String] = None
	)
	
	case class LocalizedString(
	  /** Contains the translations for the string. */
		translatedValues: Option[List[Schema.TranslatedString]] = None,
	  /** Contains the string to be displayed if no appropriate translation is available. */
		defaultValue: Option[Schema.TranslatedString] = None,
	  /** Identifies what kind of resource this is. Value: the fixed string `"walletobjects#localizedString"`. */
		kind: Option[String] = None
	)
	
	case class AppLinkData(
	  /** Optional information about the partner web link. */
		webAppLinkInfo: Option[Schema.AppLinkDataAppLinkInfo] = None,
	  /** Optional display text for the app link button. Character limit is 30. */
		displayText: Option[Schema.LocalizedString] = None,
	  /** Optional information about the partner app link. */
		androidAppLinkInfo: Option[Schema.AppLinkDataAppLinkInfo] = None,
	  /** Deprecated. Links to open iOS apps are not supported. */
		iosAppLinkInfo: Option[Schema.AppLinkDataAppLinkInfo] = None
	)
	
	case class ModuleViewConstraints(
	  /** The period of time that the module will be displayed to users. Can define both a `startTime` and `endTime`. The module is displayed immediately after insertion unless a `startTime` is set. The module is displayed indefinitely if `endTime` is not set. */
		displayInterval: Option[Schema.TimeInterval] = None
	)
	
	case class Blobstore2Info(
	  /** Read handle passed from Bigstore -> Scotty for a GCS download. This is a signed, serialized blobstore2.ReadHandle proto which must never be set outside of Bigstore, and is not applicable to non-GCS media downloads. */
		downloadReadHandle: Option[String] = None,
	  /** Metadata passed from Blobstore -> Scotty for a new GCS upload. This is a signed, serialized blobstore2.BlobMetadataContainer proto which must never be consumed outside of Bigstore, and is not applicable to non-GCS media uploads. */
		uploadMetadataContainer: Option[String] = None,
	  /** The blob id, e.g., /blobstore/prod/playground/scotty */
		blobId: Option[String] = None,
	  /** The blob read token. Needed to read blobs that have not been replicated. Might not be available until the final call. */
		readToken: Option[String] = None,
	  /** The blob generation id. */
		blobGeneration: Option[String] = None
	)
	
	case class OfferClassListResponse(
	  /** Pagination of the response. */
		pagination: Option[Schema.Pagination] = None,
	  /** Resources corresponding to the list request. */
		resources: Option[List[Schema.OfferClass]] = None
	)
	
	object BoardingAndSeatingPolicy {
		enum SeatClassPolicyEnum extends Enum[SeatClassPolicyEnum] { case SEAT_CLASS_POLICY_UNSPECIFIED, CABIN_BASED, cabinBased, CLASS_BASED, classBased, TIER_BASED, tierBased, SEAT_CLASS_POLICY_OTHER, seatClassPolicyOther }
		enum BoardingPolicyEnum extends Enum[BoardingPolicyEnum] { case BOARDING_POLICY_UNSPECIFIED, ZONE_BASED, zoneBased, GROUP_BASED, groupBased, BOARDING_POLICY_OTHER, boardingPolicyOther }
	}
	case class BoardingAndSeatingPolicy(
	  /** Seating policy which dictates how we display the seat class. If unset, Google will default to `cabinBased`. */
		seatClassPolicy: Option[Schema.BoardingAndSeatingPolicy.SeatClassPolicyEnum] = None,
	  /** Indicates the policy the airline uses for boarding. If unset, Google will default to `zoneBased`. */
		boardingPolicy: Option[Schema.BoardingAndSeatingPolicy.BoardingPolicyEnum] = None,
	  /** Identifies what kind of resource this is. Value: the fixed string `"walletobjects#boardingAndSeatingPolicy"`. */
		kind: Option[String] = None
	)
	
	case class ImageModuleData(
	  /** The ID associated with an image module. This field is here to enable ease of management of image modules. */
		id: Option[String] = None,
	  /** A 100% width image. */
		mainImage: Option[Schema.Image] = None
	)
	
	case class EventTicketObjectListResponse(
	  /** Resources corresponding to the list request. */
		resources: Option[List[Schema.EventTicketObject]] = None,
	  /** Pagination of the response. */
		pagination: Option[Schema.Pagination] = None
	)
	
	case class AppLinkDataAppLinkInfo(
	  /** Deprecated. Title isn't supported in the app link module. */
		title: Option[Schema.LocalizedString] = None,
	  /** Deprecated. Description isn't supported in the app link module. */
		description: Option[Schema.LocalizedString] = None,
	  /** Target to follow when opening the app link on clients. It will be used by partners to open their app or webpage. */
		appTarget: Option[Schema.AppLinkDataAppLinkInfoAppTarget] = None,
	  /** Deprecated. Image isn't supported in the app link module. */
		appLogoImage: Option[Schema.Image] = None
	)
	
	case class FlightHeader(
	  /** The flight number without IATA carrier code. This field should contain only digits. This is a required property of `flightHeader`. eg: "123" */
		flightNumber: Option[String] = None,
	  /** Override value to use for flight number. The default value used for display purposes is carrier + flight_number. If a different value needs to be shown to passengers, use this field to override the default behavior. eg: "XX1234 / YY576" */
		flightNumberDisplayOverride: Option[String] = None,
	  /** Identifies what kind of resource this is. Value: the fixed string `"walletobjects#flightHeader"`. */
		kind: Option[String] = None,
	  /** Information about operating airline carrier. */
		operatingCarrier: Option[Schema.FlightCarrier] = None,
	  /** Information about airline carrier. This is a required property of `flightHeader`. */
		carrier: Option[Schema.FlightCarrier] = None,
	  /** The flight number used by the operating carrier without IATA carrier code. This field should contain only digits. eg: "234" */
		operatingFlightNumber: Option[String] = None
	)
	
	case class ReservationInfo(
	  /** Identifies what kind of resource this is. Value: the fixed string `"walletobjects#reservationInfo"`. */
		kind: Option[String] = None,
	  /** Frequent flyer membership information. */
		frequentFlyerInfo: Option[Schema.FrequentFlyerInfo] = None,
	  /** Confirmation code needed to check into this flight. This is the number that the passenger would enter into a kiosk at the airport to look up the flight and print a boarding pass. */
		confirmationCode: Option[String] = None,
	  /** E-ticket number. */
		eticketNumber: Option[String] = None
	)
	
	case class AddMessageRequest(
		message: Option[Schema.Message] = None
	)
	
	case class DiffUploadResponse(
	  /** The object version of the object at the server. Must be included in the end notification response. The version in the end notification response must correspond to the new version of the object that is now stored at the server, after the upload. */
		objectVersion: Option[String] = None,
	  /** The location of the original file for a diff upload request. Must be filled in if responding to an upload start notification. */
		originalObject: Option[Schema.CompositeMedia] = None
	)
	
	case class ModifyLinkedOfferObjects(
	  /** The linked offer object ids to add to the object. */
		addLinkedOfferObjectIds: Option[List[String]] = None,
	  /** The linked offer object ids to remove from the object. */
		removeLinkedOfferObjectIds: Option[List[String]] = None
	)
	
	case class CardRowThreeItems(
	  /** The item to be displayed in the middle of the row. This item will be centered between the start and end items. */
		middleItem: Option[Schema.TemplateItem] = None,
	  /** The item to be displayed at the start of the row. This item will be aligned to the left. */
		startItem: Option[Schema.TemplateItem] = None,
	  /** The item to be displayed at the end of the row. This item will be aligned to the right. */
		endItem: Option[Schema.TemplateItem] = None
	)
	
	case class Permissions(
	  /** ID of the issuer the list of permissions refer to. */
		issuerId: Option[String] = None,
	  /** The complete list of permissions for the issuer account. */
		permissions: Option[List[Schema.Permission]] = None
	)
	
	object GenericObject {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, ACTIVE, active, COMPLETED, completed, EXPIRED, expired, INACTIVE, inactive }
		enum GenericTypeEnum extends Enum[GenericTypeEnum] { case GENERIC_TYPE_UNSPECIFIED, GENERIC_SEASON_PASS, GENERIC_UTILITY_BILLS, GENERIC_PARKING_PASS, GENERIC_VOUCHER, GENERIC_GYM_MEMBERSHIP, GENERIC_LIBRARY_MEMBERSHIP, GENERIC_RESERVATIONS, GENERIC_AUTO_INSURANCE, GENERIC_HOME_INSURANCE, GENERIC_ENTRY_TICKET, GENERIC_RECEIPT, GENERIC_LOYALTY_CARD, GENERIC_OTHER }
	}
	case class GenericObject(
	  /** Optional value added module data. Maximum of ten on the object. */
		valueAddedModuleData: Option[List[Schema.ValueAddedModuleData]] = None,
	  /** Required. The unique identifier for an object. This ID must be unique across all objects from an issuer. This value needs to follow the format `issuerID.identifier` where `issuerID` is issued by Google and `identifier` is chosen by you. The unique identifier can only include alphanumeric characters, `.`, `_`, or `-`. */
		id: Option[String] = None,
	  /** The logo image of the pass. This image is displayed in the card detail view in upper left, and also on the list/thumbnail view. If the logo is not present, the first letter of `cardTitle` would be shown as logo. */
		logo: Option[Schema.Image] = None,
	  /** Pass constraints for the object. Includes limiting NFC and screenshot behaviors. */
		passConstraints: Option[Schema.PassConstraints] = None,
	  /** Optional app or website link that will be displayed as a button on the front of the pass. If AppLinkData is provided for the corresponding class only object AppLinkData will be displayed. */
		appLinkData: Option[Schema.AppLinkData] = None,
	  /** linked_object_ids are a list of other objects such as event ticket, loyalty, offer, generic, giftcard, transit and boarding pass that should be automatically attached to this generic object. If a user had saved this generic card, then these linked_object_ids would be automatically pushed to the user's wallet (unless they turned off the setting to receive such linked passes). Make sure that objects present in linked_object_ids are already inserted - if not, calls would fail. Once linked, the linked objects cannot be unlinked. You cannot link objects belonging to another issuer. There is a limit to the number of objects that can be linked to a single object. After the limit is reached, new linked objects in the call will be ignored silently. Object IDs should follow the format issuer ID. identifier where the former is issued by Google and the latter is chosen by you. */
		linkedObjectIds: Option[List[String]] = None,
	  /** The state of the object. This field is used to determine how an object is displayed in the app. For example, an `inactive` object is moved to the "Expired passes" section. If this is not provided, the object would be considered `ACTIVE`. */
		state: Option[Schema.GenericObject.StateEnum] = None,
	  /** The time period this object will be considered valid or usable. When the time period is passed, the object will be considered expired, which will affect the rendering on user's devices. */
		validTimeInterval: Option[Schema.TimeInterval] = None,
	  /** Banner image displayed on the front of the card if present. The image will be displayed at 100% width. */
		heroImage: Option[Schema.Image] = None,
	  /** Required. The class associated with this object. The class must be of the same type as this object, must already exist, and must be approved. Class IDs should follow the format `issuerID.identifier` where `issuerID` is issued by Google and `identifier` is chosen by you. */
		classId: Option[String] = None,
	  /** The wide logo of the pass. When provided, this will be used in place of the logo in the top left of the card view. */
		wideLogo: Option[Schema.Image] = None,
	  /** The rotating barcode settings/details. */
		rotatingBarcode: Option[Schema.RotatingBarcode] = None,
	  /** Text module data. If `textModulesData` is also defined on the class, both will be displayed. The maximum number of these fields displayed is 10 from class and 10 from object. */
		textModulesData: Option[List[Schema.TextModuleData]] = None,
	  /** The barcode type and value. If pass does not have a barcode, we can allow the issuer to set Barcode.alternate_text and display just that. */
		barcode: Option[Schema.Barcode] = None,
	  /** The notification settings that are enabled for this object. */
		notifications: Option[Schema.Notifications] = None,
	  /** Links module data. If `linksModuleData` is also defined on the class, both will be displayed. The maximum number of these fields displayed is 10 from class and 10 from object. */
		linksModuleData: Option[Schema.LinksModuleData] = None,
	  /** Image module data. Only one of the image from class and one from object level will be rendered when both set. */
		imageModulesData: Option[List[Schema.ImageModuleData]] = None,
	  /** Required. The title of the pass, such as "50% off coupon" or "Library card" or "Voucher". This field is required and appears in the title row of the pass detail view. */
		header: Option[Schema.LocalizedString] = None,
	  /** Indicates if the object has users. This field is set by the platform. */
		hasUsers: Option[Boolean] = None,
	  /** The title label of the pass, such as location where this pass can be used. Appears right above the title in the title row in the pass detail view. */
		subheader: Option[Schema.LocalizedString] = None,
	  /** Required. The header of the pass. This is usually the Business name such as "XXX Gym", "AAA Insurance". This field is required and appears in the header row at the very top of the pass. */
		cardTitle: Option[Schema.LocalizedString] = None,
	  /** The value that will be transmitted to a Smart Tap certified terminal over NFC for this object. The class level fields `enableSmartTap` and `redemptionIssuers` must also be set up correctly in order for the pass to support Smart Tap. Only ASCII characters are supported. */
		smartTapRedemptionValue: Option[String] = None,
	  /** Restrictions on the object that needs to be verified before the user tries to save the pass. Note that this restrictions will only be applied during save time. If the restrictions changed after a user saves the pass, the new restrictions will not be applied to an already saved pass. */
		saveRestrictions: Option[Schema.SaveRestrictions] = None,
	  /** Information that controls how passes are grouped together. */
		groupingInfo: Option[Schema.GroupingInfo] = None,
	  /** The background color for the card. If not set, the dominant color of the hero image is used, and if no hero image is set, the dominant color of the logo is used and if logo is not set, a color would be chosen by Google. */
		hexBackgroundColor: Option[String] = None,
	  /** Specify which `GenericType` the card belongs to. */
		genericType: Option[Schema.GenericObject.GenericTypeEnum] = None,
	  /** An array of messages displayed in the app. All users of this object will receive its associated messages. The maximum number of these fields is 10. */
		messages: Option[List[Schema.Message]] = None
	)
	
	object FieldReference {
		enum DateFormatEnum extends Enum[DateFormatEnum] { case DATE_FORMAT_UNSPECIFIED, DATE_TIME, dateTime, DATE_ONLY, dateOnly, TIME_ONLY, timeOnly, DATE_TIME_YEAR, dateTimeYear, DATE_YEAR, dateYear, YEAR_MONTH, YEAR_MONTH_DAY }
	}
	case class FieldReference(
	  /** Path to the field being referenced, prefixed with "object" or "class" and separated with dots. For example, it may be the string "object.purchaseDetails.purchasePrice". */
		fieldPath: Option[String] = None,
	  /** Only valid if the `fieldPath` references a date field. Chooses how the date field will be formatted and displayed in the UI. */
		dateFormat: Option[Schema.FieldReference.DateFormatEnum] = None
	)
	
	case class TranslatedString(
	  /** The UTF-8 encoded translated string. */
		value: Option[String] = None,
	  /** Represents the BCP 47 language tag. Example values are "en-US", "en-GB", "de", or "de-AT". */
		language: Option[String] = None,
	  /** Identifies what kind of resource this is. Value: the fixed string `"walletobjects#translatedString"`. */
		kind: Option[String] = None
	)
	
	case class ActivationOptions(
	  /** HTTPS URL that supports REST semantics. Would be used for requesting activation from partners for given valuable, triggered by the users. */
		activationUrl: Option[String] = None,
	  /** Flag to allow users to make activation call from different device. This allows client to render the activation button enabled even if the activationStatus is ACTIVATED but the requested device is different than the current device. */
		allowReactivation: Option[Boolean] = None
	)
	
	object TransitClass {
		enum TransitTypeEnum extends Enum[TransitTypeEnum] { case TRANSIT_TYPE_UNSPECIFIED, BUS, bus, RAIL, rail, TRAM, tram, FERRY, ferry, OTHER, other }
		enum NotifyPreferenceEnum extends Enum[NotifyPreferenceEnum] { case NOTIFICATION_SETTINGS_FOR_UPDATES_UNSPECIFIED, NOTIFY_ON_UPDATE }
		enum ReviewStatusEnum extends Enum[ReviewStatusEnum] { case REVIEW_STATUS_UNSPECIFIED, UNDER_REVIEW, underReview, APPROVED, approved, REJECTED, rejected, DRAFT, draft }
		enum MultipleDevicesAndHoldersAllowedStatusEnum extends Enum[MultipleDevicesAndHoldersAllowedStatusEnum] { case STATUS_UNSPECIFIED, MULTIPLE_HOLDERS, ONE_USER_ALL_DEVICES, ONE_USER_ONE_DEVICE, multipleHolders, oneUserAllDevices, oneUserOneDevice }
		enum ViewUnlockRequirementEnum extends Enum[ViewUnlockRequirementEnum] { case VIEW_UNLOCK_REQUIREMENT_UNSPECIFIED, UNLOCK_NOT_REQUIRED, UNLOCK_REQUIRED_TO_VIEW }
	}
	case class TransitClass(
	  /** Text module data. If text module data is also defined on the class, both will be displayed. The maximum number of these fields displayed is 10 from the object and 10 from the class. */
		textModulesData: Option[List[Schema.TextModuleData]] = None,
	  /** If this field is present, transit tickets served to a user's device will always be in this language. Represents the BCP 47 language tag. Example values are "en-US", "en-GB", "de", or "de-AT". */
		languageOverride: Option[String] = None,
	  /** A custom label to use for the ticket number value (`transitObject.ticketNumber`). */
		customTicketNumberLabel: Option[Schema.LocalizedString] = None,
	  /** A custom label to use for the route restrictions value (`transitObject.ticketRestrictions.routeRestrictions`). */
		customRouteRestrictionsLabel: Option[Schema.LocalizedString] = None,
	  /** Deprecated. */
		wordMark: Option[Schema.Image] = None,
	  /** A custom label to use for the transit fare name value (`transitObject.ticketLeg.fareName`). */
		customFareNameLabel: Option[Schema.LocalizedString] = None,
	  /** The wide logo of the ticket. When provided, this will be used in place of the logo in the top left of the card view. */
		wideLogo: Option[Schema.Image] = None,
	  /** Callback options to be used to call the issuer back for every save/delete of an object for this class by the end-user. All objects of this class are eligible for the callback. */
		callbackOptions: Option[Schema.CallbackOptions] = None,
	  /** Required. The unique identifier for a class. This ID must be unique across all classes from an issuer. This value should follow the format issuer ID. identifier where the former is issued by Google and latter is chosen by you. Your unique identifier should only include alphanumeric characters, '.', '_', or '-'. */
		id: Option[String] = None,
	  /** Controls the display of the single-leg itinerary for this class. By default, an itinerary will only display for multi-leg trips. */
		enableSingleLegItinerary: Option[Boolean] = None,
	  /** Deprecated */
		version: Option[String] = None,
	  /** Optional value added module data. Maximum of ten on the class. For a pass only ten will be displayed, prioritizing those from the object. */
		valueAddedModuleData: Option[List[Schema.ValueAddedModuleData]] = None,
	  /** Required. The issuer name. Recommended maximum length is 20 characters to ensure full string is displayed on smaller screens. */
		issuerName: Option[String] = None,
	  /** Identifies whether this class supports Smart Tap. The `redemptionIssuers` and object level `smartTapRedemptionLevel` fields must also be set up correctly in order for a pass to support Smart Tap. */
		enableSmartTap: Option[Boolean] = None,
	  /** A custom label to use for the transit concession category value (`transitObject.concessionCategory`). */
		customConcessionCategoryLabel: Option[Schema.LocalizedString] = None,
	  /** A custom label to use for the coach value (`transitObject.ticketLeg.ticketSeat.coach`). */
		customCoachLabel: Option[Schema.LocalizedString] = None,
	  /** A custom label to use for the transit terminus name value (`transitObject.ticketLeg.transitTerminusName`). */
		customTransitTerminusNameLabel: Option[Schema.LocalizedString] = None,
	  /** Required. The type of transit this class represents, such as "bus". */
		transitType: Option[Schema.TransitClass.TransitTypeEnum] = None,
	  /** Translated strings for the issuer_name. Recommended maximum length is 20 characters to ensure full string is displayed on smaller screens. */
		localizedIssuerName: Option[Schema.LocalizedString] = None,
	  /** Optional banner image displayed on the front of the card. If none is present, nothing will be displayed. The image will display at 100% width. */
		heroImage: Option[Schema.Image] = None,
	  /** Identifies which redemption issuers can redeem the pass over Smart Tap. Redemption issuers are identified by their issuer ID. Redemption issuers must have at least one Smart Tap key configured. The `enableSmartTap` and object level `smartTapRedemptionLevel` fields must also be set up correctly in order for a pass to support Smart Tap. */
		redemptionIssuers: Option[List[String]] = None,
	  /** Whether or not field updates to this class should trigger notifications. When set to NOTIFY, we will attempt to trigger a field update notification to users. These notifications will only be sent to users if the field is part of an allowlist. If set to DO_NOT_NOTIFY or NOTIFICATION_SETTINGS_UNSPECIFIED, no notification will be triggered. This setting is ephemeral and needs to be set with each PATCH or UPDATE request, otherwise a notification will not be triggered. */
		notifyPreference: Option[Schema.TransitClass.NotifyPreferenceEnum] = None,
	  /** Required. The logo image of the ticket. This image is displayed in the card detail view of the app. */
		logo: Option[Schema.Image] = None,
	  /** Required. The status of the class. This field can be set to `draft` or `underReview` using the insert, patch, or update API calls. Once the review state is changed from `draft` it may not be changed back to `draft`. You should keep this field to `draft` when the class is under development. A `draft` class cannot be used to create any object. You should set this field to `underReview` when you believe the class is ready for use. The platform will automatically set this field to `approved` and it can be immediately used to create or migrate objects. When updating an already `approved` class you should keep setting this field to `underReview`. */
		reviewStatus: Option[Schema.TransitClass.ReviewStatusEnum] = None,
	  /** A custom label to use for the time restrictions details value (`transitObject.ticketRestrictions.timeRestrictions`). */
		customTimeRestrictionsLabel: Option[Schema.LocalizedString] = None,
	  /** The background color for the card. If not set the dominant color of the hero image is used, and if no hero image is set, the dominant color of the logo is used. The format is #rrggbb where rrggbb is a hex RGB triplet, such as `#ffcc00`. You can also use the shorthand version of the RGB triplet which is #rgb, such as `#fc0`. */
		hexBackgroundColor: Option[String] = None,
	  /** A custom label to use for the other restrictions value (`transitObject.ticketRestrictions.otherRestrictions`). */
		customOtherRestrictionsLabel: Option[Schema.LocalizedString] = None,
	  /** A custom label to use for the boarding platform value (`transitObject.ticketLeg.platform`). */
		customPlatformLabel: Option[Schema.LocalizedString] = None,
	  /** A custom label to use for the route restrictions details value (`transitObject.ticketRestrictions.routeRestrictionsDetails`). */
		customRouteRestrictionsDetailsLabel: Option[Schema.LocalizedString] = None,
	  /** A custom label to use for the purchase face value (`transitObject.purchaseDetails.ticketCost.faceValue`). */
		customPurchaseFaceValueLabel: Option[Schema.LocalizedString] = None,
	  /** Identifies whether multiple users and devices will save the same object referencing this class. */
		multipleDevicesAndHoldersAllowedStatus: Option[Schema.TransitClass.MultipleDevicesAndHoldersAllowedStatusEnum] = None,
	  /** View Unlock Requirement options for the transit ticket. */
		viewUnlockRequirement: Option[Schema.TransitClass.ViewUnlockRequirementEnum] = None,
	  /** An array of messages displayed in the app. All users of this object will receive its associated messages. The maximum number of these fields is 10. */
		messages: Option[List[Schema.Message]] = None,
	  /** Optional app or website link that will be displayed as a button on the front of the pass. If AppLinkData is provided for the corresponding object that will be used instead. */
		appLinkData: Option[Schema.AppLinkData] = None,
	  /** Deprecated. Use textModulesData instead. */
		infoModuleData: Option[Schema.InfoModuleData] = None,
	  /** Deprecated. Use `multipleDevicesAndHoldersAllowedStatus` instead. */
		allowMultipleUsersPerObject: Option[Boolean] = None,
	  /** A custom label to use for the seat location value (`transitObject.ticketLeg.ticketSeat.seat`). */
		customSeatLabel: Option[Schema.LocalizedString] = None,
	  /** Image module data. The maximum number of these fields displayed is 1 from object level and 1 for class object level. */
		imageModulesData: Option[List[Schema.ImageModuleData]] = None,
	  /** A custom label to use for the transit discount message value (`transitObject.purchaseDetails.ticketCost.discountMessage`). */
		customDiscountMessageLabel: Option[Schema.LocalizedString] = None,
	  /** Note: This field is currently not supported to trigger geo notifications. */
		locations: Option[List[Schema.LatLongPoint]] = None,
	  /** Activation options for an activatable ticket. */
		activationOptions: Option[Schema.ActivationOptions] = None,
	  /** Country code used to display the card's country (when the user is not in that country), as well as to display localized content when content is not available in the user's locale. */
		countryCode: Option[String] = None,
	  /** The URI of your application's home page. Populating the URI in this field results in the exact same behavior as populating an URI in linksModuleData (when an object is rendered, a link to the homepage is shown in what would usually be thought of as the linksModuleData section of the object). */
		homepageUri: Option[Schema.Uri] = None,
	  /** A custom label to use for the fare class value (`transitObject.ticketLeg.ticketSeat.fareClass`). */
		customFareClassLabel: Option[Schema.LocalizedString] = None,
	  /** A custom label to use for the carriage value (`transitObject.ticketLeg.carriage`). */
		customCarriageLabel: Option[Schema.LocalizedString] = None,
	  /** Optional information about the security animation. If this is set a security animation will be rendered on pass details. */
		securityAnimation: Option[Schema.SecurityAnimation] = None,
	  /** Links module data. If links module data is also defined on the object, both will be displayed. */
		linksModuleData: Option[Schema.LinksModuleData] = None,
	  /** A custom label to use for the confirmation code value (`transitObject.purchaseDetails.confirmationCode`). */
		customConfirmationCodeLabel: Option[Schema.LocalizedString] = None,
	  /** A custom label to use for the boarding zone value (`transitObject.ticketLeg.zone`). */
		customZoneLabel: Option[Schema.LocalizedString] = None,
	  /** A custom label to use for the purchase price value (`transitObject.purchaseDetails.ticketCost.purchasePrice`). */
		customPurchasePriceLabel: Option[Schema.LocalizedString] = None,
	  /** The name of the transit operator. */
		transitOperatorName: Option[Schema.LocalizedString] = None,
	  /** A custom label to use for the purchase receipt number value (`transitObject.purchaseDetails.purchaseReceiptNumber`). */
		customPurchaseReceiptNumberLabel: Option[Schema.LocalizedString] = None,
	  /** The review comments set by the platform when a class is marked `approved` or `rejected`. */
		review: Option[Schema.Review] = None,
	  /** Template information about how the class should be displayed. If unset, Google will fallback to a default set of fields to display. */
		classTemplateInfo: Option[Schema.ClassTemplateInfo] = None,
	  /** Watermark image to display on the user's device. */
		watermark: Option[Schema.Image] = None
	)
	
	case class LabelValueRow(
	  /** A list of labels and values. These will be displayed in a singular column, one after the other, not in multiple columns, despite the field name. */
		columns: Option[List[Schema.LabelValue]] = None
	)
	
	case class AuthenticationKey(
	  /** Available only to Smart Tap enabled partners. Contact support for additional guidance. */
		publicKeyPem: Option[String] = None,
	  /** Available only to Smart Tap enabled partners. Contact support for additional guidance. */
		id: Option[Int] = None
	)
	
	case class LinksModuleData(
	  /** The list of URIs. */
		uris: Option[List[Schema.Uri]] = None
	)
	
	case class LoyaltyObjectListResponse(
	  /** Pagination of the response. */
		pagination: Option[Schema.Pagination] = None,
	  /** Resources corresponding to the list request. */
		resources: Option[List[Schema.LoyaltyObject]] = None
	)
	
	case class DateTime(
	  /** An ISO 8601 extended format date/time. Offset may or may not be required (refer to the parent field's documentation). Time may be specified up to nanosecond precision. Offsets may be specified with seconds precision (even though offset seconds is not part of ISO 8601). For example: `1985-04-12T23:20:50.52Z` would be 20 minutes and 50.52 seconds after the 23rd hour of April 12th, 1985 in UTC. `1985-04-12T19:20:50.52-04:00` would be 20 minutes and 50.52 seconds after the 19th hour of April 12th, 1985, 4 hours before UTC (same instant in time as the above example). If the date/time is intended for a physical location in New York, this would be the equivalent of Eastern Daylight Time (EDT). Remember that offset varies in regions that observe Daylight Saving Time (or Summer Time), depending on the time of the year. `1985-04-12T19:20:50.52` would be 20 minutes and 50.52 seconds after the 19th hour of April 12th, 1985 with no offset information. Providing an offset makes this an absolute instant in time around the world. The date/time will be adjusted based on the user's time zone. For example, a time of `2018-06-19T18:30:00-04:00` will be 18:30:00 for a user in New York and 15:30:00 for a user in Los Angeles. Omitting the offset makes this a local date/time, representing several instants in time around the world. The date/time will always be in the user's current time zone. For example, a time of `2018-06-19T18:30:00` will be 18:30:00 for a user in New York and also 18:30:00 for a user in Los Angeles. This is useful when the same local date/time should apply to many physical locations across several time zones. */
		date: Option[String] = None
	)
	
	case class JwtInsertResponse(
	  /** A URI that, when opened, will allow the end user to save the object(s) identified in the JWT to their Google account. */
		saveUri: Option[String] = None,
	  /** Data that corresponds to the ids of the provided classes and objects in the JWT. resources will only include the non-empty arrays (i.e. if the JWT only includes eventTicketObjects, then that is the only field that will be present in resources). */
		resources: Option[Schema.Resources] = None
	)
	
	case class FrequentFlyerInfo(
	  /** Identifies what kind of resource this is. Value: the fixed string `"walletobjects#frequentFlyerInfo"`. */
		kind: Option[String] = None,
	  /** Frequent flyer program name. eg: "Lufthansa Miles & More" */
		frequentFlyerProgramName: Option[Schema.LocalizedString] = None,
	  /** Frequent flyer number. Required for each nested object of kind `walletobjects#frequentFlyerInfo`. */
		frequentFlyerNumber: Option[String] = None
	)
	
	object CompositeMedia {
		enum ReferenceTypeEnum extends Enum[ReferenceTypeEnum] { case PATH, BLOB_REF, INLINE, BIGSTORE_REF, COSMO_BINARY_REFERENCE }
	}
	case class CompositeMedia(
	  /** Size of the data, in bytes */
		length: Option[String] = None,
	  /** Blobstore v2 info, set if reference_type is BLOBSTORE_REF and it refers to a v2 blob. */
		blobstore2Info: Option[Schema.Blobstore2Info] = None,
	  /** MD5 hash for the payload. */
		md5Hash: Option[String] = None,
	  /** Describes what the field reference contains. */
		referenceType: Option[Schema.CompositeMedia.ReferenceTypeEnum] = None,
	  /** A binary data reference for a media download. Serves as a technology-agnostic binary reference in some Google infrastructure. This value is a serialized storage_cosmo.BinaryReference proto. Storing it as bytes is a hack to get around the fact that the cosmo proto (as well as others it includes) doesn't support JavaScript. This prevents us from including the actual type of this field. */
		cosmoBinaryReference: Option[String] = None,
	  /** SHA-1 hash for the payload. */
		sha1Hash: Option[String] = None,
	  /** Reference to a TI Blob, set if reference_type is BIGSTORE_REF. */
		objectId: Option[Schema.ObjectId] = None,
	  /** Blobstore v1 reference, set if reference_type is BLOBSTORE_REF This should be the byte representation of a blobstore.BlobRef. Since Blobstore is deprecating v1, use blobstore2_info instead. For now, any v2 blob will also be represented in this field as v1 BlobRef. */
		blobRef: Option[String] = None,
	  /** Path to the data, set if reference_type is PATH */
		path: Option[String] = None,
	  /** Media data, set if reference_type is INLINE */
		inline: Option[String] = None,
	  /** crc32.c hash for the payload. */
		crc32cHash: Option[Int] = None
	)
	
	case class LoyaltyPointsBalance(
	  /** The money form of a balance. Only one of these subtypes (string, int, double, money) should be populated. */
		money: Option[Schema.Money] = None,
	  /** The integer form of a balance. Only one of these subtypes (string, int, double, money) should be populated. */
		`int`: Option[Int] = None,
	  /** The string form of a balance. Only one of these subtypes (string, int, double, money) should be populated. */
		string: Option[String] = None,
	  /** The double form of a balance. Only one of these subtypes (string, int, double, money) should be populated. */
		`double`: Option[BigDecimal] = None
	)
	
	case class IssuerContactInfo(
	  /** Email addresses which will receive alerts. */
		alertsEmails: Option[List[String]] = None,
	  /** The primary contact name. */
		name: Option[String] = None,
	  /** The primary contact email address. */
		email: Option[String] = None,
	  /** The primary contact phone number. */
		phone: Option[String] = None
	)
	
	case class ModifyLinkedOfferObjectsRequest(
	  /** The linked offer object ids to add or remove from the object. */
		linkedOfferObjectIds: Option[Schema.ModifyLinkedOfferObjects] = None
	)
	
	case class ExpiryNotification(
	  /** Indicates if the object needs to have expiry notification enabled. */
		enableNotification: Option[Boolean] = None
	)
	
	case class SmartTapMerchantData(
	  /** Available only to Smart Tap enabled partners. Contact support for additional guidance. */
		authenticationKeys: Option[List[Schema.AuthenticationKey]] = None,
	  /** Available only to Smart Tap enabled partners. Contact support for additional guidance. */
		smartTapMerchantId: Option[String] = None
	)
	
	object Barcode {
		enum TypeEnum extends Enum[TypeEnum] { case BARCODE_TYPE_UNSPECIFIED, AZTEC, aztec, CODE_39, code39, CODE_128, code128, CODABAR, codabar, DATA_MATRIX, dataMatrix, EAN_8, ean8, EAN_13, ean13, EAN13, ITF_14, itf14, PDF_417, pdf417, PDF417, QR_CODE, qrCode, qrcode, UPC_A, upcA, TEXT_ONLY, textOnly }
		enum RenderEncodingEnum extends Enum[RenderEncodingEnum] { case RENDER_ENCODING_UNSPECIFIED, UTF_8 }
	}
	case class Barcode(
	  /** The type of barcode. */
		`type`: Option[Schema.Barcode.TypeEnum] = None,
	  /** The value encoded in the barcode. */
		value: Option[String] = None,
	  /** Optional text that will be shown when the barcode is hidden behind a click action. This happens in cases where a pass has Smart Tap enabled. If not specified, a default is chosen by Google. */
		showCodeText: Option[Schema.LocalizedString] = None,
	  /** The render encoding for the barcode. When specified, barcode is rendered in the given encoding. Otherwise best known encoding is chosen by Google. */
		renderEncoding: Option[Schema.Barcode.RenderEncodingEnum] = None,
	  /** An optional text that will override the default text that shows under the barcode. This field is intended for a human readable equivalent of the barcode value, used when the barcode cannot be scanned. */
		alternateText: Option[String] = None,
	  /** Identifies what kind of resource this is. Value: the fixed string `"walletobjects#barcode"`. */
		kind: Option[String] = None
	)
	
	case class FlightCarrier(
	  /** The wide logo of the airline. When provided, this will be used in place of the airline logo in the top left of the card view. */
		wideAirlineLogo: Option[Schema.Image] = None,
	  /** A localized name of the airline specified by carrierIataCode. If unset, `issuer_name` or `localized_issuer_name` from `FlightClass` will be used for display purposes. eg: "Swiss Air" for "LX" */
		airlineName: Option[Schema.LocalizedString] = None,
	  /** A logo for the airline described by carrierIataCode and localizedAirlineName. This logo will be rendered at the top of the detailed card view. */
		airlineLogo: Option[Schema.Image] = None,
	  /** Three character ICAO airline code of the marketing carrier (as opposed to operating carrier). Exactly one of this or `carrierIataCode` needs to be provided for `carrier` and `operatingCarrier`. eg: "EZY" for Easy Jet */
		carrierIcaoCode: Option[String] = None,
	  /** Two character IATA airline code of the marketing carrier (as opposed to operating carrier). Exactly one of this or `carrierIcaoCode` needs to be provided for `carrier` and `operatingCarrier`. eg: "LX" for Swiss Air */
		carrierIataCode: Option[String] = None,
	  /** A logo for the airline alliance, displayed below the QR code that the passenger scans to board. */
		airlineAllianceLogo: Option[Schema.Image] = None,
	  /** Identifies what kind of resource this is. Value: the fixed string `"walletobjects#flightCarrier"`. */
		kind: Option[String] = None
	)
}
