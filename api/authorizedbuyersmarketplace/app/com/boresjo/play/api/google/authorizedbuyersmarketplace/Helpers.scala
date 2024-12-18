package com.boresjo.play.api.google.authorizedbuyersmarketplace

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Helpers {
	import Schema.*
	object AutoOption {
		given putString: Conversion[String, Option[String]] = (fun: String) => Option(fun)
		given putListString: Conversion[List[String], Option[List[String]]] = (fun: List[String]) => Option(fun)
		given putListSchemaMediaPlanner: Conversion[List[Schema.MediaPlanner], Option[List[Schema.MediaPlanner]]] = (fun: List[Schema.MediaPlanner]) => Option(fun)
		given putListSchemaAuctionPackage: Conversion[List[Schema.AuctionPackage], Option[List[Schema.AuctionPackage]]] = (fun: List[Schema.AuctionPackage]) => Option(fun)
		given putSchemaClientUserStateEnum: Conversion[Schema.ClientUser.StateEnum, Option[Schema.ClientUser.StateEnum]] = (fun: Schema.ClientUser.StateEnum) => Option(fun)
		given putListSchemaClientUser: Conversion[List[Schema.ClientUser], Option[List[Schema.ClientUser]]] = (fun: List[Schema.ClientUser]) => Option(fun)
		given putSchemaClientRoleEnum: Conversion[Schema.Client.RoleEnum, Option[Schema.Client.RoleEnum]] = (fun: Schema.Client.RoleEnum) => Option(fun)
		given putSchemaClientStateEnum: Conversion[Schema.Client.StateEnum, Option[Schema.Client.StateEnum]] = (fun: Schema.Client.StateEnum) => Option(fun)
		given putBoolean: Conversion[Boolean, Option[Boolean]] = (fun: Boolean) => Option(fun)
		given putListSchemaClient: Conversion[List[Schema.Client], Option[List[Schema.Client]]] = (fun: List[Schema.Client]) => Option(fun)
		given putSchemaMediaPlanner: Conversion[Schema.MediaPlanner, Option[Schema.MediaPlanner]] = (fun: Schema.MediaPlanner) => Option(fun)
		given putSchemaProgrammaticGuaranteedTerms: Conversion[Schema.ProgrammaticGuaranteedTerms, Option[Schema.ProgrammaticGuaranteedTerms]] = (fun: Schema.ProgrammaticGuaranteedTerms) => Option(fun)
		given putSchemaPreferredDealTerms: Conversion[Schema.PreferredDealTerms, Option[Schema.PreferredDealTerms]] = (fun: Schema.PreferredDealTerms) => Option(fun)
		given putSchemaPrivateAuctionTerms: Conversion[Schema.PrivateAuctionTerms, Option[Schema.PrivateAuctionTerms]] = (fun: Schema.PrivateAuctionTerms) => Option(fun)
		given putSchemaDealDealTypeEnum: Conversion[Schema.Deal.DealTypeEnum, Option[Schema.Deal.DealTypeEnum]] = (fun: Schema.Deal.DealTypeEnum) => Option(fun)
		given putSchemaMoney: Conversion[Schema.Money, Option[Schema.Money]] = (fun: Schema.Money) => Option(fun)
		given putSchemaTimeZone: Conversion[Schema.TimeZone, Option[Schema.TimeZone]] = (fun: Schema.TimeZone) => Option(fun)
		given putSchemaMarketplaceTargeting: Conversion[Schema.MarketplaceTargeting, Option[Schema.MarketplaceTargeting]] = (fun: Schema.MarketplaceTargeting) => Option(fun)
		given putSchemaCreativeRequirements: Conversion[Schema.CreativeRequirements, Option[Schema.CreativeRequirements]] = (fun: Schema.CreativeRequirements) => Option(fun)
		given putSchemaDeliveryControl: Conversion[Schema.DeliveryControl, Option[Schema.DeliveryControl]] = (fun: Schema.DeliveryControl) => Option(fun)
		given putSchemaPrice: Conversion[Schema.Price, Option[Schema.Price]] = (fun: Schema.Price) => Option(fun)
		given putSchemaProgrammaticGuaranteedTermsReservationTypeEnum: Conversion[Schema.ProgrammaticGuaranteedTerms.ReservationTypeEnum, Option[Schema.ProgrammaticGuaranteedTerms.ReservationTypeEnum]] = (fun: Schema.ProgrammaticGuaranteedTerms.ReservationTypeEnum) => Option(fun)
		given putSchemaPriceTypeEnum: Conversion[Schema.Price.TypeEnum, Option[Schema.Price.TypeEnum]] = (fun: Schema.Price.TypeEnum) => Option(fun)
		given putInt: Conversion[Int, Option[Int]] = (fun: Int) => Option(fun)
		given putSchemaCriteriaTargeting: Conversion[Schema.CriteriaTargeting, Option[Schema.CriteriaTargeting]] = (fun: Schema.CriteriaTargeting) => Option(fun)
		given putSchemaInventorySizeTargeting: Conversion[Schema.InventorySizeTargeting, Option[Schema.InventorySizeTargeting]] = (fun: Schema.InventorySizeTargeting) => Option(fun)
		given putSchemaTechnologyTargeting: Conversion[Schema.TechnologyTargeting, Option[Schema.TechnologyTargeting]] = (fun: Schema.TechnologyTargeting) => Option(fun)
		given putSchemaPlacementTargeting: Conversion[Schema.PlacementTargeting, Option[Schema.PlacementTargeting]] = (fun: Schema.PlacementTargeting) => Option(fun)
		given putSchemaVideoTargeting: Conversion[Schema.VideoTargeting, Option[Schema.VideoTargeting]] = (fun: Schema.VideoTargeting) => Option(fun)
		given putSchemaDayPartTargeting: Conversion[Schema.DayPartTargeting, Option[Schema.DayPartTargeting]] = (fun: Schema.DayPartTargeting) => Option(fun)
		given putSchemaInventoryTypeTargeting: Conversion[Schema.InventoryTypeTargeting, Option[Schema.InventoryTypeTargeting]] = (fun: Schema.InventoryTypeTargeting) => Option(fun)
		given putListSchemaAdSize: Conversion[List[Schema.AdSize], Option[List[Schema.AdSize]]] = (fun: List[Schema.AdSize]) => Option(fun)
		given putSchemaAdSizeTypeEnum: Conversion[Schema.AdSize.TypeEnum, Option[Schema.AdSize.TypeEnum]] = (fun: Schema.AdSize.TypeEnum) => Option(fun)
		given putSchemaOperatingSystemTargeting: Conversion[Schema.OperatingSystemTargeting, Option[Schema.OperatingSystemTargeting]] = (fun: Schema.OperatingSystemTargeting) => Option(fun)
		given putSchemaUriTargeting: Conversion[Schema.UriTargeting, Option[Schema.UriTargeting]] = (fun: Schema.UriTargeting) => Option(fun)
		given putSchemaMobileApplicationTargeting: Conversion[Schema.MobileApplicationTargeting, Option[Schema.MobileApplicationTargeting]] = (fun: Schema.MobileApplicationTargeting) => Option(fun)
		given putSchemaFirstPartyMobileApplicationTargeting: Conversion[Schema.FirstPartyMobileApplicationTargeting, Option[Schema.FirstPartyMobileApplicationTargeting]] = (fun: Schema.FirstPartyMobileApplicationTargeting) => Option(fun)
		given putListSchemaVideoTargetingTargetedPositionTypesEnum: Conversion[List[Schema.VideoTargeting.TargetedPositionTypesEnum], Option[List[Schema.VideoTargeting.TargetedPositionTypesEnum]]] = (fun: List[Schema.VideoTargeting.TargetedPositionTypesEnum]) => Option(fun)
		given putListSchemaVideoTargetingExcludedPositionTypesEnum: Conversion[List[Schema.VideoTargeting.ExcludedPositionTypesEnum], Option[List[Schema.VideoTargeting.ExcludedPositionTypesEnum]]] = (fun: List[Schema.VideoTargeting.ExcludedPositionTypesEnum]) => Option(fun)
		given putSchemaDayPartTargetingTimeZoneTypeEnum: Conversion[Schema.DayPartTargeting.TimeZoneTypeEnum, Option[Schema.DayPartTargeting.TimeZoneTypeEnum]] = (fun: Schema.DayPartTargeting.TimeZoneTypeEnum) => Option(fun)
		given putListSchemaDayPart: Conversion[List[Schema.DayPart], Option[List[Schema.DayPart]]] = (fun: List[Schema.DayPart]) => Option(fun)
		given putSchemaDayPartDayOfWeekEnum: Conversion[Schema.DayPart.DayOfWeekEnum, Option[Schema.DayPart.DayOfWeekEnum]] = (fun: Schema.DayPart.DayOfWeekEnum) => Option(fun)
		given putSchemaTimeOfDay: Conversion[Schema.TimeOfDay, Option[Schema.TimeOfDay]] = (fun: Schema.TimeOfDay) => Option(fun)
		given putListSchemaInventoryTypeTargetingInventoryTypesEnum: Conversion[List[Schema.InventoryTypeTargeting.InventoryTypesEnum], Option[List[Schema.InventoryTypeTargeting.InventoryTypesEnum]]] = (fun: List[Schema.InventoryTypeTargeting.InventoryTypesEnum]) => Option(fun)
		given putSchemaCreativeRequirementsCreativePreApprovalPolicyEnum: Conversion[Schema.CreativeRequirements.CreativePreApprovalPolicyEnum, Option[Schema.CreativeRequirements.CreativePreApprovalPolicyEnum]] = (fun: Schema.CreativeRequirements.CreativePreApprovalPolicyEnum) => Option(fun)
		given putSchemaCreativeRequirementsCreativeSafeFrameCompatibilityEnum: Conversion[Schema.CreativeRequirements.CreativeSafeFrameCompatibilityEnum, Option[Schema.CreativeRequirements.CreativeSafeFrameCompatibilityEnum]] = (fun: Schema.CreativeRequirements.CreativeSafeFrameCompatibilityEnum) => Option(fun)
		given putSchemaCreativeRequirementsProgrammaticCreativeSourceEnum: Conversion[Schema.CreativeRequirements.ProgrammaticCreativeSourceEnum, Option[Schema.CreativeRequirements.ProgrammaticCreativeSourceEnum]] = (fun: Schema.CreativeRequirements.ProgrammaticCreativeSourceEnum) => Option(fun)
		given putSchemaCreativeRequirementsCreativeFormatEnum: Conversion[Schema.CreativeRequirements.CreativeFormatEnum, Option[Schema.CreativeRequirements.CreativeFormatEnum]] = (fun: Schema.CreativeRequirements.CreativeFormatEnum) => Option(fun)
		given putSchemaCreativeRequirementsSkippableAdTypeEnum: Conversion[Schema.CreativeRequirements.SkippableAdTypeEnum, Option[Schema.CreativeRequirements.SkippableAdTypeEnum]] = (fun: Schema.CreativeRequirements.SkippableAdTypeEnum) => Option(fun)
		given putSchemaDeliveryControlDeliveryRateTypeEnum: Conversion[Schema.DeliveryControl.DeliveryRateTypeEnum, Option[Schema.DeliveryControl.DeliveryRateTypeEnum]] = (fun: Schema.DeliveryControl.DeliveryRateTypeEnum) => Option(fun)
		given putListSchemaFrequencyCap: Conversion[List[Schema.FrequencyCap], Option[List[Schema.FrequencyCap]]] = (fun: List[Schema.FrequencyCap]) => Option(fun)
		given putSchemaDeliveryControlRoadblockingTypeEnum: Conversion[Schema.DeliveryControl.RoadblockingTypeEnum, Option[Schema.DeliveryControl.RoadblockingTypeEnum]] = (fun: Schema.DeliveryControl.RoadblockingTypeEnum) => Option(fun)
		given putSchemaDeliveryControlCompanionDeliveryTypeEnum: Conversion[Schema.DeliveryControl.CompanionDeliveryTypeEnum, Option[Schema.DeliveryControl.CompanionDeliveryTypeEnum]] = (fun: Schema.DeliveryControl.CompanionDeliveryTypeEnum) => Option(fun)
		given putSchemaDeliveryControlCreativeRotationTypeEnum: Conversion[Schema.DeliveryControl.CreativeRotationTypeEnum, Option[Schema.DeliveryControl.CreativeRotationTypeEnum]] = (fun: Schema.DeliveryControl.CreativeRotationTypeEnum) => Option(fun)
		given putSchemaFrequencyCapTimeUnitTypeEnum: Conversion[Schema.FrequencyCap.TimeUnitTypeEnum, Option[Schema.FrequencyCap.TimeUnitTypeEnum]] = (fun: Schema.FrequencyCap.TimeUnitTypeEnum) => Option(fun)
		given putListSchemaUpdateDealRequest: Conversion[List[Schema.UpdateDealRequest], Option[List[Schema.UpdateDealRequest]]] = (fun: List[Schema.UpdateDealRequest]) => Option(fun)
		given putSchemaDeal: Conversion[Schema.Deal, Option[Schema.Deal]] = (fun: Schema.Deal) => Option(fun)
		given putListSchemaDeal: Conversion[List[Schema.Deal], Option[List[Schema.Deal]]] = (fun: List[Schema.Deal]) => Option(fun)
		given putSchemaFinalizedDealDealServingStatusEnum: Conversion[Schema.FinalizedDeal.DealServingStatusEnum, Option[Schema.FinalizedDeal.DealServingStatusEnum]] = (fun: Schema.FinalizedDeal.DealServingStatusEnum) => Option(fun)
		given putSchemaDealPausingInfo: Conversion[Schema.DealPausingInfo, Option[Schema.DealPausingInfo]] = (fun: Schema.DealPausingInfo) => Option(fun)
		given putSchemaRtbMetrics: Conversion[Schema.RtbMetrics, Option[Schema.RtbMetrics]] = (fun: Schema.RtbMetrics) => Option(fun)
		given putSchemaDealPausingInfoPauseRoleEnum: Conversion[Schema.DealPausingInfo.PauseRoleEnum, Option[Schema.DealPausingInfo.PauseRoleEnum]] = (fun: Schema.DealPausingInfo.PauseRoleEnum) => Option(fun)
		given putBigDecimal: Conversion[BigDecimal, Option[BigDecimal]] = (fun: BigDecimal) => Option(fun)
		given putListSchemaFinalizedDeal: Conversion[List[Schema.FinalizedDeal], Option[List[Schema.FinalizedDeal]]] = (fun: List[Schema.FinalizedDeal]) => Option(fun)
		given putSchemaProposalDealTypeEnum: Conversion[Schema.Proposal.DealTypeEnum, Option[Schema.Proposal.DealTypeEnum]] = (fun: Schema.Proposal.DealTypeEnum) => Option(fun)
		given putSchemaProposalStateEnum: Conversion[Schema.Proposal.StateEnum, Option[Schema.Proposal.StateEnum]] = (fun: Schema.Proposal.StateEnum) => Option(fun)
		given putSchemaProposalOriginatorRoleEnum: Conversion[Schema.Proposal.OriginatorRoleEnum, Option[Schema.Proposal.OriginatorRoleEnum]] = (fun: Schema.Proposal.OriginatorRoleEnum) => Option(fun)
		given putSchemaPrivateData: Conversion[Schema.PrivateData, Option[Schema.PrivateData]] = (fun: Schema.PrivateData) => Option(fun)
		given putListSchemaContact: Conversion[List[Schema.Contact], Option[List[Schema.Contact]]] = (fun: List[Schema.Contact]) => Option(fun)
		given putSchemaProposalLastUpdaterOrCommentorRoleEnum: Conversion[Schema.Proposal.LastUpdaterOrCommentorRoleEnum, Option[Schema.Proposal.LastUpdaterOrCommentorRoleEnum]] = (fun: Schema.Proposal.LastUpdaterOrCommentorRoleEnum) => Option(fun)
		given putListSchemaNote: Conversion[List[Schema.Note], Option[List[Schema.Note]]] = (fun: List[Schema.Note]) => Option(fun)
		given putSchemaNoteCreatorRoleEnum: Conversion[Schema.Note.CreatorRoleEnum, Option[Schema.Note.CreatorRoleEnum]] = (fun: Schema.Note.CreatorRoleEnum) => Option(fun)
		given putListSchemaProposal: Conversion[List[Schema.Proposal], Option[List[Schema.Proposal]]] = (fun: List[Schema.Proposal]) => Option(fun)
		given putSchemaNote: Conversion[Schema.Note, Option[Schema.Note]] = (fun: Schema.Note) => Option(fun)
		given putListSchemaPublisherProfileMobileApplication: Conversion[List[Schema.PublisherProfileMobileApplication], Option[List[Schema.PublisherProfileMobileApplication]]] = (fun: List[Schema.PublisherProfileMobileApplication]) => Option(fun)
		given putSchemaPublisherProfileMobileApplicationAppStoreEnum: Conversion[Schema.PublisherProfileMobileApplication.AppStoreEnum, Option[Schema.PublisherProfileMobileApplication.AppStoreEnum]] = (fun: Schema.PublisherProfileMobileApplication.AppStoreEnum) => Option(fun)
		given putListSchemaPublisherProfile: Conversion[List[Schema.PublisherProfile], Option[List[Schema.PublisherProfile]]] = (fun: List[Schema.PublisherProfile]) => Option(fun)
	}
	object OptionDefault {
		given getString: Conversion[Option[String], String] = (fun: Option[String]) => fun.getOrElse("")
	}
}
