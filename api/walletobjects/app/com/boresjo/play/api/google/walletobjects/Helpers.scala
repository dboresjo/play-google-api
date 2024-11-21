package com.boresjo.play.api.google.walletobjects

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Helpers {
	import Schema.*
	object AutoOption {
		given putString: Conversion[String, Option[String]] = (fun: String) => Option(fun)
		given putSchemaTicketCost: Conversion[Schema.TicketCost, Option[Schema.TicketCost]] = (fun: Schema.TicketCost) => Option(fun)
		given putSchemaLoyaltyPointsBalance: Conversion[Schema.LoyaltyPointsBalance, Option[Schema.LoyaltyPointsBalance]] = (fun: Schema.LoyaltyPointsBalance) => Option(fun)
		given putSchemaLocalizedString: Conversion[Schema.LocalizedString, Option[Schema.LocalizedString]] = (fun: Schema.LocalizedString) => Option(fun)
		given putBigDecimal: Conversion[BigDecimal, Option[BigDecimal]] = (fun: BigDecimal) => Option(fun)
		given putSchemaOfferClass: Conversion[Schema.OfferClass, Option[Schema.OfferClass]] = (fun: Schema.OfferClass) => Option(fun)
		given putListSchemaIssuer: Conversion[List[Schema.Issuer], Option[List[Schema.Issuer]]] = (fun: List[Schema.Issuer]) => Option(fun)
		given putSchemaEventTicketClass: Conversion[Schema.EventTicketClass, Option[Schema.EventTicketClass]] = (fun: Schema.EventTicketClass) => Option(fun)
		given putSchemaEventSeat: Conversion[Schema.EventSeat, Option[Schema.EventSeat]] = (fun: Schema.EventSeat) => Option(fun)
		given putSchemaPassConstraints: Conversion[Schema.PassConstraints, Option[Schema.PassConstraints]] = (fun: Schema.PassConstraints) => Option(fun)
		given putListSchemaTextModuleData: Conversion[List[Schema.TextModuleData], Option[List[Schema.TextModuleData]]] = (fun: List[Schema.TextModuleData]) => Option(fun)
		given putListString: Conversion[List[String], Option[List[String]]] = (fun: List[String]) => Option(fun)
		given putSchemaAppLinkData: Conversion[Schema.AppLinkData, Option[Schema.AppLinkData]] = (fun: Schema.AppLinkData) => Option(fun)
		given putListSchemaValueAddedModuleData: Conversion[List[Schema.ValueAddedModuleData], Option[List[Schema.ValueAddedModuleData]]] = (fun: List[Schema.ValueAddedModuleData]) => Option(fun)
		given putBoolean: Conversion[Boolean, Option[Boolean]] = (fun: Boolean) => Option(fun)
		given putSchemaSaveRestrictions: Conversion[Schema.SaveRestrictions, Option[Schema.SaveRestrictions]] = (fun: Schema.SaveRestrictions) => Option(fun)
		given putListSchemaImageModuleData: Conversion[List[Schema.ImageModuleData], Option[List[Schema.ImageModuleData]]] = (fun: List[Schema.ImageModuleData]) => Option(fun)
		given putSchemaTimeInterval: Conversion[Schema.TimeInterval, Option[Schema.TimeInterval]] = (fun: Schema.TimeInterval) => Option(fun)
		given putListSchemaLatLongPoint: Conversion[List[Schema.LatLongPoint], Option[List[Schema.LatLongPoint]]] = (fun: List[Schema.LatLongPoint]) => Option(fun)
		given putSchemaRotatingBarcode: Conversion[Schema.RotatingBarcode, Option[Schema.RotatingBarcode]] = (fun: Schema.RotatingBarcode) => Option(fun)
		given putSchemaImage: Conversion[Schema.Image, Option[Schema.Image]] = (fun: Schema.Image) => Option(fun)
		given putSchemaInfoModuleData: Conversion[Schema.InfoModuleData, Option[Schema.InfoModuleData]] = (fun: Schema.InfoModuleData) => Option(fun)
		given putSchemaGroupingInfo: Conversion[Schema.GroupingInfo, Option[Schema.GroupingInfo]] = (fun: Schema.GroupingInfo) => Option(fun)
		given putSchemaEventTicketObjectStateEnum: Conversion[Schema.EventTicketObject.StateEnum, Option[Schema.EventTicketObject.StateEnum]] = (fun: Schema.EventTicketObject.StateEnum) => Option(fun)
		given putSchemaLinksModuleData: Conversion[Schema.LinksModuleData, Option[Schema.LinksModuleData]] = (fun: Schema.LinksModuleData) => Option(fun)
		given putListSchemaMessage: Conversion[List[Schema.Message], Option[List[Schema.Message]]] = (fun: List[Schema.Message]) => Option(fun)
		given putSchemaEventTicketObjectNotifyPreferenceEnum: Conversion[Schema.EventTicketObject.NotifyPreferenceEnum, Option[Schema.EventTicketObject.NotifyPreferenceEnum]] = (fun: Schema.EventTicketObject.NotifyPreferenceEnum) => Option(fun)
		given putSchemaMoney: Conversion[Schema.Money, Option[Schema.Money]] = (fun: Schema.Money) => Option(fun)
		given putSchemaEventReservationInfo: Conversion[Schema.EventReservationInfo, Option[Schema.EventReservationInfo]] = (fun: Schema.EventReservationInfo) => Option(fun)
		given putSchemaBarcode: Conversion[Schema.Barcode, Option[Schema.Barcode]] = (fun: Schema.Barcode) => Option(fun)
		given putListSchemaDetailsItemInfo: Conversion[List[Schema.DetailsItemInfo], Option[List[Schema.DetailsItemInfo]]] = (fun: List[Schema.DetailsItemInfo]) => Option(fun)
		given putSchemaGiftCardClass: Conversion[Schema.GiftCardClass, Option[Schema.GiftCardClass]] = (fun: Schema.GiftCardClass) => Option(fun)
		given putSchemaLoyaltyPoints: Conversion[Schema.LoyaltyPoints, Option[Schema.LoyaltyPoints]] = (fun: Schema.LoyaltyPoints) => Option(fun)
		given putSchemaLoyaltyObjectNotifyPreferenceEnum: Conversion[Schema.LoyaltyObject.NotifyPreferenceEnum, Option[Schema.LoyaltyObject.NotifyPreferenceEnum]] = (fun: Schema.LoyaltyObject.NotifyPreferenceEnum) => Option(fun)
		given putSchemaLoyaltyClass: Conversion[Schema.LoyaltyClass, Option[Schema.LoyaltyClass]] = (fun: Schema.LoyaltyClass) => Option(fun)
		given putSchemaLoyaltyObjectStateEnum: Conversion[Schema.LoyaltyObject.StateEnum, Option[Schema.LoyaltyObject.StateEnum]] = (fun: Schema.LoyaltyObject.StateEnum) => Option(fun)
		given putSchemaUri: Conversion[Schema.Uri, Option[Schema.Uri]] = (fun: Schema.Uri) => Option(fun)
		given putSchemaGiftCardClassMultipleDevicesAndHoldersAllowedStatusEnum: Conversion[Schema.GiftCardClass.MultipleDevicesAndHoldersAllowedStatusEnum, Option[Schema.GiftCardClass.MultipleDevicesAndHoldersAllowedStatusEnum]] = (fun: Schema.GiftCardClass.MultipleDevicesAndHoldersAllowedStatusEnum) => Option(fun)
		given putSchemaClassTemplateInfo: Conversion[Schema.ClassTemplateInfo, Option[Schema.ClassTemplateInfo]] = (fun: Schema.ClassTemplateInfo) => Option(fun)
		given putSchemaSecurityAnimation: Conversion[Schema.SecurityAnimation, Option[Schema.SecurityAnimation]] = (fun: Schema.SecurityAnimation) => Option(fun)
		given putSchemaCallbackOptions: Conversion[Schema.CallbackOptions, Option[Schema.CallbackOptions]] = (fun: Schema.CallbackOptions) => Option(fun)
		given putSchemaReview: Conversion[Schema.Review, Option[Schema.Review]] = (fun: Schema.Review) => Option(fun)
		given putSchemaGiftCardClassViewUnlockRequirementEnum: Conversion[Schema.GiftCardClass.ViewUnlockRequirementEnum, Option[Schema.GiftCardClass.ViewUnlockRequirementEnum]] = (fun: Schema.GiftCardClass.ViewUnlockRequirementEnum) => Option(fun)
		given putSchemaGiftCardClassNotifyPreferenceEnum: Conversion[Schema.GiftCardClass.NotifyPreferenceEnum, Option[Schema.GiftCardClass.NotifyPreferenceEnum]] = (fun: Schema.GiftCardClass.NotifyPreferenceEnum) => Option(fun)
		given putSchemaGiftCardClassReviewStatusEnum: Conversion[Schema.GiftCardClass.ReviewStatusEnum, Option[Schema.GiftCardClass.ReviewStatusEnum]] = (fun: Schema.GiftCardClass.ReviewStatusEnum) => Option(fun)
		given putSchemaGenericObject: Conversion[Schema.GenericObject, Option[Schema.GenericObject]] = (fun: Schema.GenericObject) => Option(fun)
		given putListSchemaLoyaltyClass: Conversion[List[Schema.LoyaltyClass], Option[List[Schema.LoyaltyClass]]] = (fun: List[Schema.LoyaltyClass]) => Option(fun)
		given putSchemaPagination: Conversion[Schema.Pagination, Option[Schema.Pagination]] = (fun: Schema.Pagination) => Option(fun)
		given putSchemaFlightObject: Conversion[Schema.FlightObject, Option[Schema.FlightObject]] = (fun: Schema.FlightObject) => Option(fun)
		given putSchemaEventTicketClassViewUnlockRequirementEnum: Conversion[Schema.EventTicketClass.ViewUnlockRequirementEnum, Option[Schema.EventTicketClass.ViewUnlockRequirementEnum]] = (fun: Schema.EventTicketClass.ViewUnlockRequirementEnum) => Option(fun)
		given putSchemaEventTicketClassSeatLabelEnum: Conversion[Schema.EventTicketClass.SeatLabelEnum, Option[Schema.EventTicketClass.SeatLabelEnum]] = (fun: Schema.EventTicketClass.SeatLabelEnum) => Option(fun)
		given putSchemaEventTicketClassNotifyPreferenceEnum: Conversion[Schema.EventTicketClass.NotifyPreferenceEnum, Option[Schema.EventTicketClass.NotifyPreferenceEnum]] = (fun: Schema.EventTicketClass.NotifyPreferenceEnum) => Option(fun)
		given putSchemaEventDateTime: Conversion[Schema.EventDateTime, Option[Schema.EventDateTime]] = (fun: Schema.EventDateTime) => Option(fun)
		given putSchemaEventTicketClassRowLabelEnum: Conversion[Schema.EventTicketClass.RowLabelEnum, Option[Schema.EventTicketClass.RowLabelEnum]] = (fun: Schema.EventTicketClass.RowLabelEnum) => Option(fun)
		given putSchemaEventTicketClassConfirmationCodeLabelEnum: Conversion[Schema.EventTicketClass.ConfirmationCodeLabelEnum, Option[Schema.EventTicketClass.ConfirmationCodeLabelEnum]] = (fun: Schema.EventTicketClass.ConfirmationCodeLabelEnum) => Option(fun)
		given putSchemaEventTicketClassGateLabelEnum: Conversion[Schema.EventTicketClass.GateLabelEnum, Option[Schema.EventTicketClass.GateLabelEnum]] = (fun: Schema.EventTicketClass.GateLabelEnum) => Option(fun)
		given putSchemaEventVenue: Conversion[Schema.EventVenue, Option[Schema.EventVenue]] = (fun: Schema.EventVenue) => Option(fun)
		given putSchemaEventTicketClassReviewStatusEnum: Conversion[Schema.EventTicketClass.ReviewStatusEnum, Option[Schema.EventTicketClass.ReviewStatusEnum]] = (fun: Schema.EventTicketClass.ReviewStatusEnum) => Option(fun)
		given putSchemaEventTicketClassMultipleDevicesAndHoldersAllowedStatusEnum: Conversion[Schema.EventTicketClass.MultipleDevicesAndHoldersAllowedStatusEnum, Option[Schema.EventTicketClass.MultipleDevicesAndHoldersAllowedStatusEnum]] = (fun: Schema.EventTicketClass.MultipleDevicesAndHoldersAllowedStatusEnum) => Option(fun)
		given putSchemaEventTicketClassSectionLabelEnum: Conversion[Schema.EventTicketClass.SectionLabelEnum, Option[Schema.EventTicketClass.SectionLabelEnum]] = (fun: Schema.EventTicketClass.SectionLabelEnum) => Option(fun)
		given putSchemaEventTicketObject: Conversion[Schema.EventTicketObject, Option[Schema.EventTicketObject]] = (fun: Schema.EventTicketObject) => Option(fun)
		given putSchemaModuleViewConstraints: Conversion[Schema.ModuleViewConstraints, Option[Schema.ModuleViewConstraints]] = (fun: Schema.ModuleViewConstraints) => Option(fun)
		given putInt: Conversion[Int, Option[Int]] = (fun: Int) => Option(fun)
		given putListSchemaTicketLeg: Conversion[List[Schema.TicketLeg], Option[List[Schema.TicketLeg]]] = (fun: List[Schema.TicketLeg]) => Option(fun)
		given putSchemaPurchaseDetails: Conversion[Schema.PurchaseDetails, Option[Schema.PurchaseDetails]] = (fun: Schema.PurchaseDetails) => Option(fun)
		given putSchemaActivationStatus: Conversion[Schema.ActivationStatus, Option[Schema.ActivationStatus]] = (fun: Schema.ActivationStatus) => Option(fun)
		given putSchemaTicketLeg: Conversion[Schema.TicketLeg, Option[Schema.TicketLeg]] = (fun: Schema.TicketLeg) => Option(fun)
		given putSchemaDeviceContext: Conversion[Schema.DeviceContext, Option[Schema.DeviceContext]] = (fun: Schema.DeviceContext) => Option(fun)
		given putSchemaTransitObjectPassengerTypeEnum: Conversion[Schema.TransitObject.PassengerTypeEnum, Option[Schema.TransitObject.PassengerTypeEnum]] = (fun: Schema.TransitObject.PassengerTypeEnum) => Option(fun)
		given putSchemaTransitClass: Conversion[Schema.TransitClass, Option[Schema.TransitClass]] = (fun: Schema.TransitClass) => Option(fun)
		given putSchemaTransitObjectTripTypeEnum: Conversion[Schema.TransitObject.TripTypeEnum, Option[Schema.TransitObject.TripTypeEnum]] = (fun: Schema.TransitObject.TripTypeEnum) => Option(fun)
		given putSchemaTransitObjectConcessionCategoryEnum: Conversion[Schema.TransitObject.ConcessionCategoryEnum, Option[Schema.TransitObject.ConcessionCategoryEnum]] = (fun: Schema.TransitObject.ConcessionCategoryEnum) => Option(fun)
		given putSchemaTransitObjectTicketStatusEnum: Conversion[Schema.TransitObject.TicketStatusEnum, Option[Schema.TransitObject.TicketStatusEnum]] = (fun: Schema.TransitObject.TicketStatusEnum) => Option(fun)
		given putSchemaTransitObjectStateEnum: Conversion[Schema.TransitObject.StateEnum, Option[Schema.TransitObject.StateEnum]] = (fun: Schema.TransitObject.StateEnum) => Option(fun)
		given putSchemaTransitObjectNotifyPreferenceEnum: Conversion[Schema.TransitObject.NotifyPreferenceEnum, Option[Schema.TransitObject.NotifyPreferenceEnum]] = (fun: Schema.TransitObject.NotifyPreferenceEnum) => Option(fun)
		given putSchemaTicketRestrictions: Conversion[Schema.TicketRestrictions, Option[Schema.TicketRestrictions]] = (fun: Schema.TicketRestrictions) => Option(fun)
		given putListSchemaFieldReference: Conversion[List[Schema.FieldReference], Option[List[Schema.FieldReference]]] = (fun: List[Schema.FieldReference]) => Option(fun)
		given putListSchemaOfferObject: Conversion[List[Schema.OfferObject], Option[List[Schema.OfferObject]]] = (fun: List[Schema.OfferObject]) => Option(fun)
		given putSchemaImageUri: Conversion[Schema.ImageUri, Option[Schema.ImageUri]] = (fun: Schema.ImageUri) => Option(fun)
		given putListSchemaCompositeMedia: Conversion[List[Schema.CompositeMedia], Option[List[Schema.CompositeMedia]]] = (fun: List[Schema.CompositeMedia]) => Option(fun)
		given putSchemaDiffVersionResponse: Conversion[Schema.DiffVersionResponse, Option[Schema.DiffVersionResponse]] = (fun: Schema.DiffVersionResponse) => Option(fun)
		given putSchemaContentTypeInfo: Conversion[Schema.ContentTypeInfo, Option[Schema.ContentTypeInfo]] = (fun: Schema.ContentTypeInfo) => Option(fun)
		given putSchemaDiffChecksumsResponse: Conversion[Schema.DiffChecksumsResponse, Option[Schema.DiffChecksumsResponse]] = (fun: Schema.DiffChecksumsResponse) => Option(fun)
		given putSchemaMediaReferenceTypeEnum: Conversion[Schema.Media.ReferenceTypeEnum, Option[Schema.Media.ReferenceTypeEnum]] = (fun: Schema.Media.ReferenceTypeEnum) => Option(fun)
		given putSchemaDiffDownloadResponse: Conversion[Schema.DiffDownloadResponse, Option[Schema.DiffDownloadResponse]] = (fun: Schema.DiffDownloadResponse) => Option(fun)
		given putSchemaBlobstore2Info: Conversion[Schema.Blobstore2Info, Option[Schema.Blobstore2Info]] = (fun: Schema.Blobstore2Info) => Option(fun)
		given putSchemaDownloadParameters: Conversion[Schema.DownloadParameters, Option[Schema.DownloadParameters]] = (fun: Schema.DownloadParameters) => Option(fun)
		given putSchemaDiffUploadRequest: Conversion[Schema.DiffUploadRequest, Option[Schema.DiffUploadRequest]] = (fun: Schema.DiffUploadRequest) => Option(fun)
		given putSchemaDiffUploadResponse: Conversion[Schema.DiffUploadResponse, Option[Schema.DiffUploadResponse]] = (fun: Schema.DiffUploadResponse) => Option(fun)
		given putSchemaObjectId: Conversion[Schema.ObjectId, Option[Schema.ObjectId]] = (fun: Schema.ObjectId) => Option(fun)
		given putSchemaDiscoverableProgramStateEnum: Conversion[Schema.DiscoverableProgram.StateEnum, Option[Schema.DiscoverableProgram.StateEnum]] = (fun: Schema.DiscoverableProgram.StateEnum) => Option(fun)
		given putSchemaDiscoverableProgramMerchantSignupInfo: Conversion[Schema.DiscoverableProgramMerchantSignupInfo, Option[Schema.DiscoverableProgramMerchantSignupInfo]] = (fun: Schema.DiscoverableProgramMerchantSignupInfo) => Option(fun)
		given putSchemaDiscoverableProgramMerchantSigninInfo: Conversion[Schema.DiscoverableProgramMerchantSigninInfo, Option[Schema.DiscoverableProgramMerchantSigninInfo]] = (fun: Schema.DiscoverableProgramMerchantSigninInfo) => Option(fun)
		given putListSchemaTransitClass: Conversion[List[Schema.TransitClass], Option[List[Schema.TransitClass]]] = (fun: List[Schema.TransitClass]) => Option(fun)
		given putSchemaFieldSelector: Conversion[Schema.FieldSelector, Option[Schema.FieldSelector]] = (fun: Schema.FieldSelector) => Option(fun)
		given putListSchemaTicketSeat: Conversion[List[Schema.TicketSeat], Option[List[Schema.TicketSeat]]] = (fun: List[Schema.TicketSeat]) => Option(fun)
		given putSchemaTicketSeat: Conversion[Schema.TicketSeat, Option[Schema.TicketSeat]] = (fun: Schema.TicketSeat) => Option(fun)
		given putSchemaGiftCardObject: Conversion[Schema.GiftCardObject, Option[Schema.GiftCardObject]] = (fun: Schema.GiftCardObject) => Option(fun)
		given putListSchemaFlightClass: Conversion[List[Schema.FlightClass], Option[List[Schema.FlightClass]]] = (fun: List[Schema.FlightClass]) => Option(fun)
		given putListSchemaFlightObject: Conversion[List[Schema.FlightObject], Option[List[Schema.FlightObject]]] = (fun: List[Schema.FlightObject]) => Option(fun)
		given putListSchemaLoyaltyObject: Conversion[List[Schema.LoyaltyObject], Option[List[Schema.LoyaltyObject]]] = (fun: List[Schema.LoyaltyObject]) => Option(fun)
		given putListSchemaEventTicketObject: Conversion[List[Schema.EventTicketObject], Option[List[Schema.EventTicketObject]]] = (fun: List[Schema.EventTicketObject]) => Option(fun)
		given putListSchemaGenericClass: Conversion[List[Schema.GenericClass], Option[List[Schema.GenericClass]]] = (fun: List[Schema.GenericClass]) => Option(fun)
		given putListSchemaOfferClass: Conversion[List[Schema.OfferClass], Option[List[Schema.OfferClass]]] = (fun: List[Schema.OfferClass]) => Option(fun)
		given putListSchemaGiftCardObject: Conversion[List[Schema.GiftCardObject], Option[List[Schema.GiftCardObject]]] = (fun: List[Schema.GiftCardObject]) => Option(fun)
		given putListSchemaEventTicketClass: Conversion[List[Schema.EventTicketClass], Option[List[Schema.EventTicketClass]]] = (fun: List[Schema.EventTicketClass]) => Option(fun)
		given putListSchemaGiftCardClass: Conversion[List[Schema.GiftCardClass], Option[List[Schema.GiftCardClass]]] = (fun: List[Schema.GiftCardClass]) => Option(fun)
		given putListSchemaTransitObject: Conversion[List[Schema.TransitObject], Option[List[Schema.TransitObject]]] = (fun: List[Schema.TransitObject]) => Option(fun)
		given putListSchemaGenericObject: Conversion[List[Schema.GenericObject], Option[List[Schema.GenericObject]]] = (fun: List[Schema.GenericObject]) => Option(fun)
		given putSchemaTicketSeatFareClassEnum: Conversion[Schema.TicketSeat.FareClassEnum, Option[Schema.TicketSeat.FareClassEnum]] = (fun: Schema.TicketSeat.FareClassEnum) => Option(fun)
		given putSchemaTemplateItem: Conversion[Schema.TemplateItem, Option[Schema.TemplateItem]] = (fun: Schema.TemplateItem) => Option(fun)
		given putListSchemaLabelValueRow: Conversion[List[Schema.LabelValueRow], Option[List[Schema.LabelValueRow]]] = (fun: List[Schema.LabelValueRow]) => Option(fun)
		given putSchemaBoardingAndSeatingInfoBoardingDoorEnum: Conversion[Schema.BoardingAndSeatingInfo.BoardingDoorEnum, Option[Schema.BoardingAndSeatingInfo.BoardingDoorEnum]] = (fun: Schema.BoardingAndSeatingInfo.BoardingDoorEnum) => Option(fun)
		given putSchemaDetailsTemplateOverride: Conversion[Schema.DetailsTemplateOverride, Option[Schema.DetailsTemplateOverride]] = (fun: Schema.DetailsTemplateOverride) => Option(fun)
		given putSchemaCardTemplateOverride: Conversion[Schema.CardTemplateOverride, Option[Schema.CardTemplateOverride]] = (fun: Schema.CardTemplateOverride) => Option(fun)
		given putSchemaCardBarcodeSectionDetails: Conversion[Schema.CardBarcodeSectionDetails, Option[Schema.CardBarcodeSectionDetails]] = (fun: Schema.CardBarcodeSectionDetails) => Option(fun)
		given putSchemaListTemplateOverride: Conversion[Schema.ListTemplateOverride, Option[Schema.ListTemplateOverride]] = (fun: Schema.ListTemplateOverride) => Option(fun)
		given putSchemaMessageMessageTypeEnum: Conversion[Schema.Message.MessageTypeEnum, Option[Schema.Message.MessageTypeEnum]] = (fun: Schema.Message.MessageTypeEnum) => Option(fun)
		given putSchemaLoyaltyObject: Conversion[Schema.LoyaltyObject, Option[Schema.LoyaltyObject]] = (fun: Schema.LoyaltyObject) => Option(fun)
		given putSchemaDiscoverableProgram: Conversion[Schema.DiscoverableProgram, Option[Schema.DiscoverableProgram]] = (fun: Schema.DiscoverableProgram) => Option(fun)
		given putSchemaLoyaltyClassViewUnlockRequirementEnum: Conversion[Schema.LoyaltyClass.ViewUnlockRequirementEnum, Option[Schema.LoyaltyClass.ViewUnlockRequirementEnum]] = (fun: Schema.LoyaltyClass.ViewUnlockRequirementEnum) => Option(fun)
		given putSchemaLoyaltyClassReviewStatusEnum: Conversion[Schema.LoyaltyClass.ReviewStatusEnum, Option[Schema.LoyaltyClass.ReviewStatusEnum]] = (fun: Schema.LoyaltyClass.ReviewStatusEnum) => Option(fun)
		given putSchemaLoyaltyClassNotifyPreferenceEnum: Conversion[Schema.LoyaltyClass.NotifyPreferenceEnum, Option[Schema.LoyaltyClass.NotifyPreferenceEnum]] = (fun: Schema.LoyaltyClass.NotifyPreferenceEnum) => Option(fun)
		given putSchemaLoyaltyClassMultipleDevicesAndHoldersAllowedStatusEnum: Conversion[Schema.LoyaltyClass.MultipleDevicesAndHoldersAllowedStatusEnum, Option[Schema.LoyaltyClass.MultipleDevicesAndHoldersAllowedStatusEnum]] = (fun: Schema.LoyaltyClass.MultipleDevicesAndHoldersAllowedStatusEnum) => Option(fun)
		given putSchemaCardRowOneItem: Conversion[Schema.CardRowOneItem, Option[Schema.CardRowOneItem]] = (fun: Schema.CardRowOneItem) => Option(fun)
		given putSchemaCardRowTwoItems: Conversion[Schema.CardRowTwoItems, Option[Schema.CardRowTwoItems]] = (fun: Schema.CardRowTwoItems) => Option(fun)
		given putSchemaCardRowThreeItems: Conversion[Schema.CardRowThreeItems, Option[Schema.CardRowThreeItems]] = (fun: Schema.CardRowThreeItems) => Option(fun)
		given putSchemaEventDateTimeDoorsOpenLabelEnum: Conversion[Schema.EventDateTime.DoorsOpenLabelEnum, Option[Schema.EventDateTime.DoorsOpenLabelEnum]] = (fun: Schema.EventDateTime.DoorsOpenLabelEnum) => Option(fun)
		given putListSchemaIssuerToUserInfo: Conversion[List[Schema.IssuerToUserInfo], Option[List[Schema.IssuerToUserInfo]]] = (fun: List[Schema.IssuerToUserInfo]) => Option(fun)
		given putSchemaOfferObject: Conversion[Schema.OfferObject, Option[Schema.OfferObject]] = (fun: Schema.OfferObject) => Option(fun)
		given putSchemaCompositeMedia: Conversion[Schema.CompositeMedia, Option[Schema.CompositeMedia]] = (fun: Schema.CompositeMedia) => Option(fun)
		given putSchemaIssuerContactInfo: Conversion[Schema.IssuerContactInfo, Option[Schema.IssuerContactInfo]] = (fun: Schema.IssuerContactInfo) => Option(fun)
		given putSchemaSmartTapMerchantData: Conversion[Schema.SmartTapMerchantData, Option[Schema.SmartTapMerchantData]] = (fun: Schema.SmartTapMerchantData) => Option(fun)
		given putSchemaFlightClass: Conversion[Schema.FlightClass, Option[Schema.FlightClass]] = (fun: Schema.FlightClass) => Option(fun)
		given putSchemaGenericClass: Conversion[Schema.GenericClass, Option[Schema.GenericClass]] = (fun: Schema.GenericClass) => Option(fun)
		given putSchemaGenericClassMultipleDevicesAndHoldersAllowedStatusEnum: Conversion[Schema.GenericClass.MultipleDevicesAndHoldersAllowedStatusEnum, Option[Schema.GenericClass.MultipleDevicesAndHoldersAllowedStatusEnum]] = (fun: Schema.GenericClass.MultipleDevicesAndHoldersAllowedStatusEnum) => Option(fun)
		given putSchemaGenericClassViewUnlockRequirementEnum: Conversion[Schema.GenericClass.ViewUnlockRequirementEnum, Option[Schema.GenericClass.ViewUnlockRequirementEnum]] = (fun: Schema.GenericClass.ViewUnlockRequirementEnum) => Option(fun)
		given putListSchemaCardRowTemplateInfo: Conversion[List[Schema.CardRowTemplateInfo], Option[List[Schema.CardRowTemplateInfo]]] = (fun: List[Schema.CardRowTemplateInfo]) => Option(fun)
		given putSchemaFirstRowOptionTransitOptionEnum: Conversion[Schema.FirstRowOption.TransitOptionEnum, Option[Schema.FirstRowOption.TransitOptionEnum]] = (fun: Schema.FirstRowOption.TransitOptionEnum) => Option(fun)
		given putSchemaMediaRequestInfoNotificationTypeEnum: Conversion[Schema.MediaRequestInfo.NotificationTypeEnum, Option[Schema.MediaRequestInfo.NotificationTypeEnum]] = (fun: Schema.MediaRequestInfo.NotificationTypeEnum) => Option(fun)
		given putSchemaDateTime: Conversion[Schema.DateTime, Option[Schema.DateTime]] = (fun: Schema.DateTime) => Option(fun)
		given putSchemaMedia: Conversion[Schema.Media, Option[Schema.Media]] = (fun: Schema.Media) => Option(fun)
		given putSchemaMediaRequestInfo: Conversion[Schema.MediaRequestInfo, Option[Schema.MediaRequestInfo]] = (fun: Schema.MediaRequestInfo) => Option(fun)
		given putSchemaTemplateItemPredefinedItemEnum: Conversion[Schema.TemplateItem.PredefinedItemEnum, Option[Schema.TemplateItem.PredefinedItemEnum]] = (fun: Schema.TemplateItem.PredefinedItemEnum) => Option(fun)
		given putSchemaFlightObjectStateEnum: Conversion[Schema.FlightObject.StateEnum, Option[Schema.FlightObject.StateEnum]] = (fun: Schema.FlightObject.StateEnum) => Option(fun)
		given putSchemaReservationInfo: Conversion[Schema.ReservationInfo, Option[Schema.ReservationInfo]] = (fun: Schema.ReservationInfo) => Option(fun)
		given putSchemaBoardingAndSeatingInfo: Conversion[Schema.BoardingAndSeatingInfo, Option[Schema.BoardingAndSeatingInfo]] = (fun: Schema.BoardingAndSeatingInfo) => Option(fun)
		given putSchemaFlightObjectNotifyPreferenceEnum: Conversion[Schema.FlightObject.NotifyPreferenceEnum, Option[Schema.FlightObject.NotifyPreferenceEnum]] = (fun: Schema.FlightObject.NotifyPreferenceEnum) => Option(fun)
		given putSchemaOfferObjectNotifyPreferenceEnum: Conversion[Schema.OfferObject.NotifyPreferenceEnum, Option[Schema.OfferObject.NotifyPreferenceEnum]] = (fun: Schema.OfferObject.NotifyPreferenceEnum) => Option(fun)
		given putSchemaOfferObjectStateEnum: Conversion[Schema.OfferObject.StateEnum, Option[Schema.OfferObject.StateEnum]] = (fun: Schema.OfferObject.StateEnum) => Option(fun)
		given putSchemaTransitObject: Conversion[Schema.TransitObject, Option[Schema.TransitObject]] = (fun: Schema.TransitObject) => Option(fun)
		given putListSchemaRotatingBarcodeTotpDetailsTotpParameters: Conversion[List[Schema.RotatingBarcodeTotpDetailsTotpParameters], Option[List[Schema.RotatingBarcodeTotpDetailsTotpParameters]]] = (fun: List[Schema.RotatingBarcodeTotpDetailsTotpParameters]) => Option(fun)
		given putSchemaRotatingBarcodeTotpDetailsAlgorithmEnum: Conversion[Schema.RotatingBarcodeTotpDetails.AlgorithmEnum, Option[Schema.RotatingBarcodeTotpDetails.AlgorithmEnum]] = (fun: Schema.RotatingBarcodeTotpDetails.AlgorithmEnum) => Option(fun)
		given putSchemaSecurityAnimationAnimationTypeEnum: Conversion[Schema.SecurityAnimation.AnimationTypeEnum, Option[Schema.SecurityAnimation.AnimationTypeEnum]] = (fun: Schema.SecurityAnimation.AnimationTypeEnum) => Option(fun)
		given putSchemaRotatingBarcodeTypeEnum: Conversion[Schema.RotatingBarcode.TypeEnum, Option[Schema.RotatingBarcode.TypeEnum]] = (fun: Schema.RotatingBarcode.TypeEnum) => Option(fun)
		given putSchemaRotatingBarcodeTotpDetails: Conversion[Schema.RotatingBarcodeTotpDetails, Option[Schema.RotatingBarcodeTotpDetails]] = (fun: Schema.RotatingBarcodeTotpDetails) => Option(fun)
		given putSchemaRotatingBarcodeValues: Conversion[Schema.RotatingBarcodeValues, Option[Schema.RotatingBarcodeValues]] = (fun: Schema.RotatingBarcodeValues) => Option(fun)
		given putSchemaRotatingBarcodeRenderEncodingEnum: Conversion[Schema.RotatingBarcode.RenderEncodingEnum, Option[Schema.RotatingBarcode.RenderEncodingEnum]] = (fun: Schema.RotatingBarcode.RenderEncodingEnum) => Option(fun)
		given putSchemaBarcodeSectionDetail: Conversion[Schema.BarcodeSectionDetail, Option[Schema.BarcodeSectionDetail]] = (fun: Schema.BarcodeSectionDetail) => Option(fun)
		given putSchemaBoardingAndSeatingPolicy: Conversion[Schema.BoardingAndSeatingPolicy, Option[Schema.BoardingAndSeatingPolicy]] = (fun: Schema.BoardingAndSeatingPolicy) => Option(fun)
		given putSchemaFlightClassNotifyPreferenceEnum: Conversion[Schema.FlightClass.NotifyPreferenceEnum, Option[Schema.FlightClass.NotifyPreferenceEnum]] = (fun: Schema.FlightClass.NotifyPreferenceEnum) => Option(fun)
		given putSchemaFlightHeader: Conversion[Schema.FlightHeader, Option[Schema.FlightHeader]] = (fun: Schema.FlightHeader) => Option(fun)
		given putSchemaFlightClassFlightStatusEnum: Conversion[Schema.FlightClass.FlightStatusEnum, Option[Schema.FlightClass.FlightStatusEnum]] = (fun: Schema.FlightClass.FlightStatusEnum) => Option(fun)
		given putSchemaFlightClassMultipleDevicesAndHoldersAllowedStatusEnum: Conversion[Schema.FlightClass.MultipleDevicesAndHoldersAllowedStatusEnum, Option[Schema.FlightClass.MultipleDevicesAndHoldersAllowedStatusEnum]] = (fun: Schema.FlightClass.MultipleDevicesAndHoldersAllowedStatusEnum) => Option(fun)
		given putSchemaAirportInfo: Conversion[Schema.AirportInfo, Option[Schema.AirportInfo]] = (fun: Schema.AirportInfo) => Option(fun)
		given putSchemaFlightClassViewUnlockRequirementEnum: Conversion[Schema.FlightClass.ViewUnlockRequirementEnum, Option[Schema.FlightClass.ViewUnlockRequirementEnum]] = (fun: Schema.FlightClass.ViewUnlockRequirementEnum) => Option(fun)
		given putSchemaFlightClassReviewStatusEnum: Conversion[Schema.FlightClass.ReviewStatusEnum, Option[Schema.FlightClass.ReviewStatusEnum]] = (fun: Schema.FlightClass.ReviewStatusEnum) => Option(fun)
		given putListSchemaPassConstraintsNfcConstraintEnum: Conversion[List[Schema.PassConstraints.NfcConstraintEnum], Option[List[Schema.PassConstraints.NfcConstraintEnum]]] = (fun: List[Schema.PassConstraints.NfcConstraintEnum]) => Option(fun)
		given putSchemaPassConstraintsScreenshotEligibilityEnum: Conversion[Schema.PassConstraints.ScreenshotEligibilityEnum, Option[Schema.PassConstraints.ScreenshotEligibilityEnum]] = (fun: Schema.PassConstraints.ScreenshotEligibilityEnum) => Option(fun)
		given putSchemaIssuerToUserInfoActionEnum: Conversion[Schema.IssuerToUserInfo.ActionEnum, Option[Schema.IssuerToUserInfo.ActionEnum]] = (fun: Schema.IssuerToUserInfo.ActionEnum) => Option(fun)
		given putSchemaSignUpInfo: Conversion[Schema.SignUpInfo, Option[Schema.SignUpInfo]] = (fun: Schema.SignUpInfo) => Option(fun)
		given putSchemaUpcomingNotification: Conversion[Schema.UpcomingNotification, Option[Schema.UpcomingNotification]] = (fun: Schema.UpcomingNotification) => Option(fun)
		given putSchemaExpiryNotification: Conversion[Schema.ExpiryNotification, Option[Schema.ExpiryNotification]] = (fun: Schema.ExpiryNotification) => Option(fun)
		given putSchemaPermissionRoleEnum: Conversion[Schema.Permission.RoleEnum, Option[Schema.Permission.RoleEnum]] = (fun: Schema.Permission.RoleEnum) => Option(fun)
		given putSchemaOfferClassRedemptionChannelEnum: Conversion[Schema.OfferClass.RedemptionChannelEnum, Option[Schema.OfferClass.RedemptionChannelEnum]] = (fun: Schema.OfferClass.RedemptionChannelEnum) => Option(fun)
		given putSchemaOfferClassViewUnlockRequirementEnum: Conversion[Schema.OfferClass.ViewUnlockRequirementEnum, Option[Schema.OfferClass.ViewUnlockRequirementEnum]] = (fun: Schema.OfferClass.ViewUnlockRequirementEnum) => Option(fun)
		given putSchemaOfferClassReviewStatusEnum: Conversion[Schema.OfferClass.ReviewStatusEnum, Option[Schema.OfferClass.ReviewStatusEnum]] = (fun: Schema.OfferClass.ReviewStatusEnum) => Option(fun)
		given putSchemaOfferClassNotifyPreferenceEnum: Conversion[Schema.OfferClass.NotifyPreferenceEnum, Option[Schema.OfferClass.NotifyPreferenceEnum]] = (fun: Schema.OfferClass.NotifyPreferenceEnum) => Option(fun)
		given putSchemaOfferClassMultipleDevicesAndHoldersAllowedStatusEnum: Conversion[Schema.OfferClass.MultipleDevicesAndHoldersAllowedStatusEnum, Option[Schema.OfferClass.MultipleDevicesAndHoldersAllowedStatusEnum]] = (fun: Schema.OfferClass.MultipleDevicesAndHoldersAllowedStatusEnum) => Option(fun)
		given putListSchemaDiscoverableProgramMerchantSignupInfoSignupSharedDatasEnum: Conversion[List[Schema.DiscoverableProgramMerchantSignupInfo.SignupSharedDatasEnum], Option[List[Schema.DiscoverableProgramMerchantSignupInfo.SignupSharedDatasEnum]]] = (fun: List[Schema.DiscoverableProgramMerchantSignupInfo.SignupSharedDatasEnum]) => Option(fun)
		given putSchemaActivationStatusStateEnum: Conversion[Schema.ActivationStatus.StateEnum, Option[Schema.ActivationStatus.StateEnum]] = (fun: Schema.ActivationStatus.StateEnum) => Option(fun)
		given putSchemaGiftCardObjectNotifyPreferenceEnum: Conversion[Schema.GiftCardObject.NotifyPreferenceEnum, Option[Schema.GiftCardObject.NotifyPreferenceEnum]] = (fun: Schema.GiftCardObject.NotifyPreferenceEnum) => Option(fun)
		given putSchemaGiftCardObjectStateEnum: Conversion[Schema.GiftCardObject.StateEnum, Option[Schema.GiftCardObject.StateEnum]] = (fun: Schema.GiftCardObject.StateEnum) => Option(fun)
		given putSchemaFirstRowOption: Conversion[Schema.FirstRowOption, Option[Schema.FirstRowOption]] = (fun: Schema.FirstRowOption) => Option(fun)
		given putListSchemaTranslatedString: Conversion[List[Schema.TranslatedString], Option[List[Schema.TranslatedString]]] = (fun: List[Schema.TranslatedString]) => Option(fun)
		given putSchemaTranslatedString: Conversion[Schema.TranslatedString, Option[Schema.TranslatedString]] = (fun: Schema.TranslatedString) => Option(fun)
		given putSchemaAppLinkDataAppLinkInfo: Conversion[Schema.AppLinkDataAppLinkInfo, Option[Schema.AppLinkDataAppLinkInfo]] = (fun: Schema.AppLinkDataAppLinkInfo) => Option(fun)
		given putSchemaBoardingAndSeatingPolicySeatClassPolicyEnum: Conversion[Schema.BoardingAndSeatingPolicy.SeatClassPolicyEnum, Option[Schema.BoardingAndSeatingPolicy.SeatClassPolicyEnum]] = (fun: Schema.BoardingAndSeatingPolicy.SeatClassPolicyEnum) => Option(fun)
		given putSchemaBoardingAndSeatingPolicyBoardingPolicyEnum: Conversion[Schema.BoardingAndSeatingPolicy.BoardingPolicyEnum, Option[Schema.BoardingAndSeatingPolicy.BoardingPolicyEnum]] = (fun: Schema.BoardingAndSeatingPolicy.BoardingPolicyEnum) => Option(fun)
		given putSchemaAppLinkDataAppLinkInfoAppTarget: Conversion[Schema.AppLinkDataAppLinkInfoAppTarget, Option[Schema.AppLinkDataAppLinkInfoAppTarget]] = (fun: Schema.AppLinkDataAppLinkInfoAppTarget) => Option(fun)
		given putSchemaFlightCarrier: Conversion[Schema.FlightCarrier, Option[Schema.FlightCarrier]] = (fun: Schema.FlightCarrier) => Option(fun)
		given putSchemaFrequentFlyerInfo: Conversion[Schema.FrequentFlyerInfo, Option[Schema.FrequentFlyerInfo]] = (fun: Schema.FrequentFlyerInfo) => Option(fun)
		given putSchemaMessage: Conversion[Schema.Message, Option[Schema.Message]] = (fun: Schema.Message) => Option(fun)
		given putListSchemaPermission: Conversion[List[Schema.Permission], Option[List[Schema.Permission]]] = (fun: List[Schema.Permission]) => Option(fun)
		given putSchemaGenericObjectStateEnum: Conversion[Schema.GenericObject.StateEnum, Option[Schema.GenericObject.StateEnum]] = (fun: Schema.GenericObject.StateEnum) => Option(fun)
		given putSchemaNotifications: Conversion[Schema.Notifications, Option[Schema.Notifications]] = (fun: Schema.Notifications) => Option(fun)
		given putSchemaGenericObjectGenericTypeEnum: Conversion[Schema.GenericObject.GenericTypeEnum, Option[Schema.GenericObject.GenericTypeEnum]] = (fun: Schema.GenericObject.GenericTypeEnum) => Option(fun)
		given putSchemaFieldReferenceDateFormatEnum: Conversion[Schema.FieldReference.DateFormatEnum, Option[Schema.FieldReference.DateFormatEnum]] = (fun: Schema.FieldReference.DateFormatEnum) => Option(fun)
		given putSchemaTransitClassTransitTypeEnum: Conversion[Schema.TransitClass.TransitTypeEnum, Option[Schema.TransitClass.TransitTypeEnum]] = (fun: Schema.TransitClass.TransitTypeEnum) => Option(fun)
		given putSchemaTransitClassNotifyPreferenceEnum: Conversion[Schema.TransitClass.NotifyPreferenceEnum, Option[Schema.TransitClass.NotifyPreferenceEnum]] = (fun: Schema.TransitClass.NotifyPreferenceEnum) => Option(fun)
		given putSchemaTransitClassReviewStatusEnum: Conversion[Schema.TransitClass.ReviewStatusEnum, Option[Schema.TransitClass.ReviewStatusEnum]] = (fun: Schema.TransitClass.ReviewStatusEnum) => Option(fun)
		given putSchemaTransitClassMultipleDevicesAndHoldersAllowedStatusEnum: Conversion[Schema.TransitClass.MultipleDevicesAndHoldersAllowedStatusEnum, Option[Schema.TransitClass.MultipleDevicesAndHoldersAllowedStatusEnum]] = (fun: Schema.TransitClass.MultipleDevicesAndHoldersAllowedStatusEnum) => Option(fun)
		given putSchemaTransitClassViewUnlockRequirementEnum: Conversion[Schema.TransitClass.ViewUnlockRequirementEnum, Option[Schema.TransitClass.ViewUnlockRequirementEnum]] = (fun: Schema.TransitClass.ViewUnlockRequirementEnum) => Option(fun)
		given putSchemaActivationOptions: Conversion[Schema.ActivationOptions, Option[Schema.ActivationOptions]] = (fun: Schema.ActivationOptions) => Option(fun)
		given putListSchemaLabelValue: Conversion[List[Schema.LabelValue], Option[List[Schema.LabelValue]]] = (fun: List[Schema.LabelValue]) => Option(fun)
		given putListSchemaUri: Conversion[List[Schema.Uri], Option[List[Schema.Uri]]] = (fun: List[Schema.Uri]) => Option(fun)
		given putSchemaResources: Conversion[Schema.Resources, Option[Schema.Resources]] = (fun: Schema.Resources) => Option(fun)
		given putSchemaCompositeMediaReferenceTypeEnum: Conversion[Schema.CompositeMedia.ReferenceTypeEnum, Option[Schema.CompositeMedia.ReferenceTypeEnum]] = (fun: Schema.CompositeMedia.ReferenceTypeEnum) => Option(fun)
		given putSchemaModifyLinkedOfferObjects: Conversion[Schema.ModifyLinkedOfferObjects, Option[Schema.ModifyLinkedOfferObjects]] = (fun: Schema.ModifyLinkedOfferObjects) => Option(fun)
		given putListSchemaAuthenticationKey: Conversion[List[Schema.AuthenticationKey], Option[List[Schema.AuthenticationKey]]] = (fun: List[Schema.AuthenticationKey]) => Option(fun)
		given putSchemaBarcodeTypeEnum: Conversion[Schema.Barcode.TypeEnum, Option[Schema.Barcode.TypeEnum]] = (fun: Schema.Barcode.TypeEnum) => Option(fun)
		given putSchemaBarcodeRenderEncodingEnum: Conversion[Schema.Barcode.RenderEncodingEnum, Option[Schema.Barcode.RenderEncodingEnum]] = (fun: Schema.Barcode.RenderEncodingEnum) => Option(fun)
	}
	object OptionDefault {
		given getString: Conversion[Option[String], String] = (fun: Option[String]) => fun.getOrElse("")
	}
}
