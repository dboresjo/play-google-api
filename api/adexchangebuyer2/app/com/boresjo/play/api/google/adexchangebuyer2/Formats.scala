package com.boresjo.play.api.google.adexchangebuyer2

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Formats {
	given fmtCreative: Format[Schema.Creative] = Json.format[Schema.Creative]
	given fmtHtmlContent: Format[Schema.HtmlContent] = Json.format[Schema.HtmlContent]
	given fmtVideoContent: Format[Schema.VideoContent] = Json.format[Schema.VideoContent]
	given fmtNativeContent: Format[Schema.NativeContent] = Json.format[Schema.NativeContent]
	given fmtCreativeAttributesEnum: Format[Schema.Creative.AttributesEnum] = JsonEnumFormat[Schema.Creative.AttributesEnum]
	given fmtCreativeRestrictedCategoriesEnum: Format[Schema.Creative.RestrictedCategoriesEnum] = JsonEnumFormat[Schema.Creative.RestrictedCategoriesEnum]
	given fmtCreativeDealsStatusEnum: Format[Schema.Creative.DealsStatusEnum] = JsonEnumFormat[Schema.Creative.DealsStatusEnum]
	given fmtCreativeOpenAuctionStatusEnum: Format[Schema.Creative.OpenAuctionStatusEnum] = JsonEnumFormat[Schema.Creative.OpenAuctionStatusEnum]
	given fmtServingRestriction: Format[Schema.ServingRestriction] = Json.format[Schema.ServingRestriction]
	given fmtCorrection: Format[Schema.Correction] = Json.format[Schema.Correction]
	given fmtAdTechnologyProviders: Format[Schema.AdTechnologyProviders] = Json.format[Schema.AdTechnologyProviders]
	given fmtImage: Format[Schema.Image] = Json.format[Schema.Image]
	given fmtServingRestrictionStatusEnum: Format[Schema.ServingRestriction.StatusEnum] = JsonEnumFormat[Schema.ServingRestriction.StatusEnum]
	given fmtServingContext: Format[Schema.ServingContext] = Json.format[Schema.ServingContext]
	given fmtDisapproval: Format[Schema.Disapproval] = Json.format[Schema.Disapproval]
	given fmtServingContextAllEnum: Format[Schema.ServingContext.AllEnum] = JsonEnumFormat[Schema.ServingContext.AllEnum]
	given fmtLocationContext: Format[Schema.LocationContext] = Json.format[Schema.LocationContext]
	given fmtPlatformContext: Format[Schema.PlatformContext] = Json.format[Schema.PlatformContext]
	given fmtAuctionContext: Format[Schema.AuctionContext] = Json.format[Schema.AuctionContext]
	given fmtSecurityContext: Format[Schema.SecurityContext] = Json.format[Schema.SecurityContext]
	given fmtAppContext: Format[Schema.AppContext] = Json.format[Schema.AppContext]
	given fmtPlatformContextPlatformsEnum: Format[Schema.PlatformContext.PlatformsEnum] = JsonEnumFormat[Schema.PlatformContext.PlatformsEnum]
	given fmtAuctionContextAuctionTypesEnum: Format[Schema.AuctionContext.AuctionTypesEnum] = JsonEnumFormat[Schema.AuctionContext.AuctionTypesEnum]
	given fmtSecurityContextSecuritiesEnum: Format[Schema.SecurityContext.SecuritiesEnum] = JsonEnumFormat[Schema.SecurityContext.SecuritiesEnum]
	given fmtAppContextAppTypesEnum: Format[Schema.AppContext.AppTypesEnum] = JsonEnumFormat[Schema.AppContext.AppTypesEnum]
	given fmtDisapprovalReasonEnum: Format[Schema.Disapproval.ReasonEnum] = JsonEnumFormat[Schema.Disapproval.ReasonEnum]
	given fmtCorrectionTypeEnum: Format[Schema.Correction.TypeEnum] = JsonEnumFormat[Schema.Correction.TypeEnum]
	given fmtListCreativesResponse: Format[Schema.ListCreativesResponse] = Json.format[Schema.ListCreativesResponse]
	given fmtAddDealAssociationRequest: Format[Schema.AddDealAssociationRequest] = Json.format[Schema.AddDealAssociationRequest]
	given fmtCreativeDealAssociation: Format[Schema.CreativeDealAssociation] = Json.format[Schema.CreativeDealAssociation]
	given fmtEmpty: Format[Schema.Empty] = Json.format[Schema.Empty]
	given fmtRemoveDealAssociationRequest: Format[Schema.RemoveDealAssociationRequest] = Json.format[Schema.RemoveDealAssociationRequest]
	given fmtListDealAssociationsResponse: Format[Schema.ListDealAssociationsResponse] = Json.format[Schema.ListDealAssociationsResponse]
	given fmtWatchCreativeRequest: Format[Schema.WatchCreativeRequest] = Json.format[Schema.WatchCreativeRequest]
	given fmtStopWatchingCreativeRequest: Format[Schema.StopWatchingCreativeRequest] = Json.format[Schema.StopWatchingCreativeRequest]
	given fmtListImpressionMetricsResponse: Format[Schema.ListImpressionMetricsResponse] = Json.format[Schema.ListImpressionMetricsResponse]
	given fmtImpressionMetricsRow: Format[Schema.ImpressionMetricsRow] = Json.format[Schema.ImpressionMetricsRow]
	given fmtMetricValue: Format[Schema.MetricValue] = Json.format[Schema.MetricValue]
	given fmtRowDimensions: Format[Schema.RowDimensions] = Json.format[Schema.RowDimensions]
	given fmtTimeInterval: Format[Schema.TimeInterval] = Json.format[Schema.TimeInterval]
	given fmtListBidMetricsResponse: Format[Schema.ListBidMetricsResponse] = Json.format[Schema.ListBidMetricsResponse]
	given fmtBidMetricsRow: Format[Schema.BidMetricsRow] = Json.format[Schema.BidMetricsRow]
	given fmtListFilteredBidRequestsResponse: Format[Schema.ListFilteredBidRequestsResponse] = Json.format[Schema.ListFilteredBidRequestsResponse]
	given fmtCalloutStatusRow: Format[Schema.CalloutStatusRow] = Json.format[Schema.CalloutStatusRow]
	given fmtListBidResponseErrorsResponse: Format[Schema.ListBidResponseErrorsResponse] = Json.format[Schema.ListBidResponseErrorsResponse]
	given fmtListBidResponsesWithoutBidsResponse: Format[Schema.ListBidResponsesWithoutBidsResponse] = Json.format[Schema.ListBidResponsesWithoutBidsResponse]
	given fmtBidResponseWithoutBidsStatusRow: Format[Schema.BidResponseWithoutBidsStatusRow] = Json.format[Schema.BidResponseWithoutBidsStatusRow]
	given fmtBidResponseWithoutBidsStatusRowStatusEnum: Format[Schema.BidResponseWithoutBidsStatusRow.StatusEnum] = JsonEnumFormat[Schema.BidResponseWithoutBidsStatusRow.StatusEnum]
	given fmtListFilteredBidsResponse: Format[Schema.ListFilteredBidsResponse] = Json.format[Schema.ListFilteredBidsResponse]
	given fmtCreativeStatusRow: Format[Schema.CreativeStatusRow] = Json.format[Schema.CreativeStatusRow]
	given fmtListLosingBidsResponse: Format[Schema.ListLosingBidsResponse] = Json.format[Schema.ListLosingBidsResponse]
	given fmtListNonBillableWinningBidsResponse: Format[Schema.ListNonBillableWinningBidsResponse] = Json.format[Schema.ListNonBillableWinningBidsResponse]
	given fmtNonBillableWinningBidStatusRow: Format[Schema.NonBillableWinningBidStatusRow] = Json.format[Schema.NonBillableWinningBidStatusRow]
	given fmtNonBillableWinningBidStatusRowStatusEnum: Format[Schema.NonBillableWinningBidStatusRow.StatusEnum] = JsonEnumFormat[Schema.NonBillableWinningBidStatusRow.StatusEnum]
	given fmtListCreativeStatusBreakdownByDetailResponse: Format[Schema.ListCreativeStatusBreakdownByDetailResponse] = Json.format[Schema.ListCreativeStatusBreakdownByDetailResponse]
	given fmtFilteredBidDetailRow: Format[Schema.FilteredBidDetailRow] = Json.format[Schema.FilteredBidDetailRow]
	given fmtListCreativeStatusBreakdownByDetailResponseDetailTypeEnum: Format[Schema.ListCreativeStatusBreakdownByDetailResponse.DetailTypeEnum] = JsonEnumFormat[Schema.ListCreativeStatusBreakdownByDetailResponse.DetailTypeEnum]
	given fmtListCreativeStatusBreakdownByCreativeResponse: Format[Schema.ListCreativeStatusBreakdownByCreativeResponse] = Json.format[Schema.ListCreativeStatusBreakdownByCreativeResponse]
	given fmtFilteredBidCreativeRow: Format[Schema.FilteredBidCreativeRow] = Json.format[Schema.FilteredBidCreativeRow]
	given fmtClient: Format[Schema.Client] = Json.format[Schema.Client]
	given fmtClientRoleEnum: Format[Schema.Client.RoleEnum] = JsonEnumFormat[Schema.Client.RoleEnum]
	given fmtClientStatusEnum: Format[Schema.Client.StatusEnum] = JsonEnumFormat[Schema.Client.StatusEnum]
	given fmtClientEntityTypeEnum: Format[Schema.Client.EntityTypeEnum] = JsonEnumFormat[Schema.Client.EntityTypeEnum]
	given fmtListClientsResponse: Format[Schema.ListClientsResponse] = Json.format[Schema.ListClientsResponse]
	given fmtListClientUsersResponse: Format[Schema.ListClientUsersResponse] = Json.format[Schema.ListClientUsersResponse]
	given fmtClientUser: Format[Schema.ClientUser] = Json.format[Schema.ClientUser]
	given fmtClientUserStatusEnum: Format[Schema.ClientUser.StatusEnum] = JsonEnumFormat[Schema.ClientUser.StatusEnum]
	given fmtClientUserInvitation: Format[Schema.ClientUserInvitation] = Json.format[Schema.ClientUserInvitation]
	given fmtListClientUserInvitationsResponse: Format[Schema.ListClientUserInvitationsResponse] = Json.format[Schema.ListClientUserInvitationsResponse]
	given fmtFilterSet: Format[Schema.FilterSet] = Json.format[Schema.FilterSet]
	given fmtRelativeDateRange: Format[Schema.RelativeDateRange] = Json.format[Schema.RelativeDateRange]
	given fmtAbsoluteDateRange: Format[Schema.AbsoluteDateRange] = Json.format[Schema.AbsoluteDateRange]
	given fmtRealtimeTimeRange: Format[Schema.RealtimeTimeRange] = Json.format[Schema.RealtimeTimeRange]
	given fmtFilterSetTimeSeriesGranularityEnum: Format[Schema.FilterSet.TimeSeriesGranularityEnum] = JsonEnumFormat[Schema.FilterSet.TimeSeriesGranularityEnum]
	given fmtFilterSetFormatsEnum: Format[Schema.FilterSet.FormatsEnum] = JsonEnumFormat[Schema.FilterSet.FormatsEnum]
	given fmtFilterSetFormatEnum: Format[Schema.FilterSet.FormatEnum] = JsonEnumFormat[Schema.FilterSet.FormatEnum]
	given fmtFilterSetEnvironmentEnum: Format[Schema.FilterSet.EnvironmentEnum] = JsonEnumFormat[Schema.FilterSet.EnvironmentEnum]
	given fmtFilterSetPlatformsEnum: Format[Schema.FilterSet.PlatformsEnum] = JsonEnumFormat[Schema.FilterSet.PlatformsEnum]
	given fmtFilterSetBreakdownDimensionsEnum: Format[Schema.FilterSet.BreakdownDimensionsEnum] = JsonEnumFormat[Schema.FilterSet.BreakdownDimensionsEnum]
	given fmtDate: Format[Schema.Date] = Json.format[Schema.Date]
	given fmtListFilterSetsResponse: Format[Schema.ListFilterSetsResponse] = Json.format[Schema.ListFilterSetsResponse]
	given fmtProposal: Format[Schema.Proposal] = Json.format[Schema.Proposal]
	given fmtDeal: Format[Schema.Deal] = Json.format[Schema.Deal]
	given fmtProposalOriginatorRoleEnum: Format[Schema.Proposal.OriginatorRoleEnum] = JsonEnumFormat[Schema.Proposal.OriginatorRoleEnum]
	given fmtSeller: Format[Schema.Seller] = Json.format[Schema.Seller]
	given fmtBuyer: Format[Schema.Buyer] = Json.format[Schema.Buyer]
	given fmtProposalProposalStateEnum: Format[Schema.Proposal.ProposalStateEnum] = JsonEnumFormat[Schema.Proposal.ProposalStateEnum]
	given fmtPrivateData: Format[Schema.PrivateData] = Json.format[Schema.PrivateData]
	given fmtContactInformation: Format[Schema.ContactInformation] = Json.format[Schema.ContactInformation]
	given fmtProposalLastUpdaterOrCommentorRoleEnum: Format[Schema.Proposal.LastUpdaterOrCommentorRoleEnum] = JsonEnumFormat[Schema.Proposal.LastUpdaterOrCommentorRoleEnum]
	given fmtNote: Format[Schema.Note] = Json.format[Schema.Note]
	given fmtDealTerms: Format[Schema.DealTerms] = Json.format[Schema.DealTerms]
	given fmtTargetingCriteria: Format[Schema.TargetingCriteria] = Json.format[Schema.TargetingCriteria]
	given fmtMarketplaceTargeting: Format[Schema.MarketplaceTargeting] = Json.format[Schema.MarketplaceTargeting]
	given fmtCreativeRestrictions: Format[Schema.CreativeRestrictions] = Json.format[Schema.CreativeRestrictions]
	given fmtDealSyndicationProductEnum: Format[Schema.Deal.SyndicationProductEnum] = JsonEnumFormat[Schema.Deal.SyndicationProductEnum]
	given fmtDealCreativePreApprovalPolicyEnum: Format[Schema.Deal.CreativePreApprovalPolicyEnum] = JsonEnumFormat[Schema.Deal.CreativePreApprovalPolicyEnum]
	given fmtDealCreativeSafeFrameCompatibilityEnum: Format[Schema.Deal.CreativeSafeFrameCompatibilityEnum] = JsonEnumFormat[Schema.Deal.CreativeSafeFrameCompatibilityEnum]
	given fmtDealServingMetadata: Format[Schema.DealServingMetadata] = Json.format[Schema.DealServingMetadata]
	given fmtDealProgrammaticCreativeSourceEnum: Format[Schema.Deal.ProgrammaticCreativeSourceEnum] = JsonEnumFormat[Schema.Deal.ProgrammaticCreativeSourceEnum]
	given fmtDeliveryControl: Format[Schema.DeliveryControl] = Json.format[Schema.DeliveryControl]
	given fmtDealTermsBrandingTypeEnum: Format[Schema.DealTerms.BrandingTypeEnum] = JsonEnumFormat[Schema.DealTerms.BrandingTypeEnum]
	given fmtPrice: Format[Schema.Price] = Json.format[Schema.Price]
	given fmtGuaranteedFixedPriceTerms: Format[Schema.GuaranteedFixedPriceTerms] = Json.format[Schema.GuaranteedFixedPriceTerms]
	given fmtNonGuaranteedFixedPriceTerms: Format[Schema.NonGuaranteedFixedPriceTerms] = Json.format[Schema.NonGuaranteedFixedPriceTerms]
	given fmtNonGuaranteedAuctionTerms: Format[Schema.NonGuaranteedAuctionTerms] = Json.format[Schema.NonGuaranteedAuctionTerms]
	given fmtPricePricingTypeEnum: Format[Schema.Price.PricingTypeEnum] = JsonEnumFormat[Schema.Price.PricingTypeEnum]
	given fmtMoney: Format[Schema.Money] = Json.format[Schema.Money]
	given fmtPricePerBuyer: Format[Schema.PricePerBuyer] = Json.format[Schema.PricePerBuyer]
	given fmtGuaranteedFixedPriceTermsReservationTypeEnum: Format[Schema.GuaranteedFixedPriceTerms.ReservationTypeEnum] = JsonEnumFormat[Schema.GuaranteedFixedPriceTerms.ReservationTypeEnum]
	given fmtTargetingValue: Format[Schema.TargetingValue] = Json.format[Schema.TargetingValue]
	given fmtCreativeSize: Format[Schema.CreativeSize] = Json.format[Schema.CreativeSize]
	given fmtDayPartTargeting: Format[Schema.DayPartTargeting] = Json.format[Schema.DayPartTargeting]
	given fmtCreativeSizeCreativeSizeTypeEnum: Format[Schema.CreativeSize.CreativeSizeTypeEnum] = JsonEnumFormat[Schema.CreativeSize.CreativeSizeTypeEnum]
	given fmtSize: Format[Schema.Size] = Json.format[Schema.Size]
	given fmtCreativeSizeSkippableAdTypeEnum: Format[Schema.CreativeSize.SkippableAdTypeEnum] = JsonEnumFormat[Schema.CreativeSize.SkippableAdTypeEnum]
	given fmtCreativeSizeNativeTemplateEnum: Format[Schema.CreativeSize.NativeTemplateEnum] = JsonEnumFormat[Schema.CreativeSize.NativeTemplateEnum]
	given fmtCreativeSizeAllowedFormatsEnum: Format[Schema.CreativeSize.AllowedFormatsEnum] = JsonEnumFormat[Schema.CreativeSize.AllowedFormatsEnum]
	given fmtDayPartTargetingTimeZoneTypeEnum: Format[Schema.DayPartTargeting.TimeZoneTypeEnum] = JsonEnumFormat[Schema.DayPartTargeting.TimeZoneTypeEnum]
	given fmtDayPart: Format[Schema.DayPart] = Json.format[Schema.DayPart]
	given fmtDayPartDayOfWeekEnum: Format[Schema.DayPart.DayOfWeekEnum] = JsonEnumFormat[Schema.DayPart.DayOfWeekEnum]
	given fmtTimeOfDay: Format[Schema.TimeOfDay] = Json.format[Schema.TimeOfDay]
	given fmtCriteriaTargeting: Format[Schema.CriteriaTargeting] = Json.format[Schema.CriteriaTargeting]
	given fmtInventorySizeTargeting: Format[Schema.InventorySizeTargeting] = Json.format[Schema.InventorySizeTargeting]
	given fmtTechnologyTargeting: Format[Schema.TechnologyTargeting] = Json.format[Schema.TechnologyTargeting]
	given fmtPlacementTargeting: Format[Schema.PlacementTargeting] = Json.format[Schema.PlacementTargeting]
	given fmtVideoTargeting: Format[Schema.VideoTargeting] = Json.format[Schema.VideoTargeting]
	given fmtAdSize: Format[Schema.AdSize] = Json.format[Schema.AdSize]
	given fmtAdSizeSizeTypeEnum: Format[Schema.AdSize.SizeTypeEnum] = JsonEnumFormat[Schema.AdSize.SizeTypeEnum]
	given fmtOperatingSystemTargeting: Format[Schema.OperatingSystemTargeting] = Json.format[Schema.OperatingSystemTargeting]
	given fmtUrlTargeting: Format[Schema.UrlTargeting] = Json.format[Schema.UrlTargeting]
	given fmtMobileApplicationTargeting: Format[Schema.MobileApplicationTargeting] = Json.format[Schema.MobileApplicationTargeting]
	given fmtFirstPartyMobileApplicationTargeting: Format[Schema.FirstPartyMobileApplicationTargeting] = Json.format[Schema.FirstPartyMobileApplicationTargeting]
	given fmtVideoTargetingTargetedPositionTypesEnum: Format[Schema.VideoTargeting.TargetedPositionTypesEnum] = JsonEnumFormat[Schema.VideoTargeting.TargetedPositionTypesEnum]
	given fmtVideoTargetingExcludedPositionTypesEnum: Format[Schema.VideoTargeting.ExcludedPositionTypesEnum] = JsonEnumFormat[Schema.VideoTargeting.ExcludedPositionTypesEnum]
	given fmtCreativeRestrictionsCreativeFormatEnum: Format[Schema.CreativeRestrictions.CreativeFormatEnum] = JsonEnumFormat[Schema.CreativeRestrictions.CreativeFormatEnum]
	given fmtCreativeSpecification: Format[Schema.CreativeSpecification] = Json.format[Schema.CreativeSpecification]
	given fmtCreativeRestrictionsSkippableAdTypeEnum: Format[Schema.CreativeRestrictions.SkippableAdTypeEnum] = JsonEnumFormat[Schema.CreativeRestrictions.SkippableAdTypeEnum]
	given fmtDealPauseStatus: Format[Schema.DealPauseStatus] = Json.format[Schema.DealPauseStatus]
	given fmtDealPauseStatusFirstPausedByEnum: Format[Schema.DealPauseStatus.FirstPausedByEnum] = JsonEnumFormat[Schema.DealPauseStatus.FirstPausedByEnum]
	given fmtDeliveryControlDeliveryRateTypeEnum: Format[Schema.DeliveryControl.DeliveryRateTypeEnum] = JsonEnumFormat[Schema.DeliveryControl.DeliveryRateTypeEnum]
	given fmtFrequencyCap: Format[Schema.FrequencyCap] = Json.format[Schema.FrequencyCap]
	given fmtDeliveryControlCreativeBlockingLevelEnum: Format[Schema.DeliveryControl.CreativeBlockingLevelEnum] = JsonEnumFormat[Schema.DeliveryControl.CreativeBlockingLevelEnum]
	given fmtFrequencyCapTimeUnitTypeEnum: Format[Schema.FrequencyCap.TimeUnitTypeEnum] = JsonEnumFormat[Schema.FrequencyCap.TimeUnitTypeEnum]
	given fmtNoteCreatorRoleEnum: Format[Schema.Note.CreatorRoleEnum] = JsonEnumFormat[Schema.Note.CreatorRoleEnum]
	given fmtListProposalsResponse: Format[Schema.ListProposalsResponse] = Json.format[Schema.ListProposalsResponse]
	given fmtAddNoteRequest: Format[Schema.AddNoteRequest] = Json.format[Schema.AddNoteRequest]
	given fmtCancelNegotiationRequest: Format[Schema.CancelNegotiationRequest] = Json.format[Schema.CancelNegotiationRequest]
	given fmtAcceptProposalRequest: Format[Schema.AcceptProposalRequest] = Json.format[Schema.AcceptProposalRequest]
	given fmtCompleteSetupRequest: Format[Schema.CompleteSetupRequest] = Json.format[Schema.CompleteSetupRequest]
	given fmtPauseProposalRequest: Format[Schema.PauseProposalRequest] = Json.format[Schema.PauseProposalRequest]
	given fmtResumeProposalRequest: Format[Schema.ResumeProposalRequest] = Json.format[Schema.ResumeProposalRequest]
	given fmtPauseProposalDealsRequest: Format[Schema.PauseProposalDealsRequest] = Json.format[Schema.PauseProposalDealsRequest]
	given fmtResumeProposalDealsRequest: Format[Schema.ResumeProposalDealsRequest] = Json.format[Schema.ResumeProposalDealsRequest]
	given fmtProduct: Format[Schema.Product] = Json.format[Schema.Product]
	given fmtProductSyndicationProductEnum: Format[Schema.Product.SyndicationProductEnum] = JsonEnumFormat[Schema.Product.SyndicationProductEnum]
	given fmtListProductsResponse: Format[Schema.ListProductsResponse] = Json.format[Schema.ListProductsResponse]
	given fmtPublisherProfile: Format[Schema.PublisherProfile] = Json.format[Schema.PublisherProfile]
	given fmtPublisherProfileMobileApplication: Format[Schema.PublisherProfileMobileApplication] = Json.format[Schema.PublisherProfileMobileApplication]
	given fmtPublisherProfileMobileApplicationAppStoreEnum: Format[Schema.PublisherProfileMobileApplication.AppStoreEnum] = JsonEnumFormat[Schema.PublisherProfileMobileApplication.AppStoreEnum]
	given fmtListPublisherProfilesResponse: Format[Schema.ListPublisherProfilesResponse] = Json.format[Schema.ListPublisherProfilesResponse]
}
