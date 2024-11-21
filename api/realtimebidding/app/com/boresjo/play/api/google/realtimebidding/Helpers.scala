package com.boresjo.play.api.google.realtimebidding

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Helpers {
	import Schema.*
	object AutoOption {
		given putString: Conversion[String, Option[String]] = (fun: String) => Option(fun)
		given putBoolean: Conversion[Boolean, Option[Boolean]] = (fun: Boolean) => Option(fun)
		given putListSchemaBidder: Conversion[List[Schema.Bidder], Option[List[Schema.Bidder]]] = (fun: List[Schema.Bidder]) => Option(fun)
		given putSchemaEndpointTradingLocationEnum: Conversion[Schema.Endpoint.TradingLocationEnum, Option[Schema.Endpoint.TradingLocationEnum]] = (fun: Schema.Endpoint.TradingLocationEnum) => Option(fun)
		given putSchemaEndpointBidProtocolEnum: Conversion[Schema.Endpoint.BidProtocolEnum, Option[Schema.Endpoint.BidProtocolEnum]] = (fun: Schema.Endpoint.BidProtocolEnum) => Option(fun)
		given putListSchemaEndpoint: Conversion[List[Schema.Endpoint], Option[List[Schema.Endpoint]]] = (fun: List[Schema.Endpoint]) => Option(fun)
		given putListString: Conversion[List[String], Option[List[String]]] = (fun: List[String]) => Option(fun)
		given putListSchemaBuyer: Conversion[List[Schema.Buyer], Option[List[Schema.Buyer]]] = (fun: List[Schema.Buyer]) => Option(fun)
		given putListSchemaCreative: Conversion[List[Schema.Creative], Option[List[Schema.Creative]]] = (fun: List[Schema.Creative]) => Option(fun)
		given putSchemaHtmlContent: Conversion[Schema.HtmlContent, Option[Schema.HtmlContent]] = (fun: Schema.HtmlContent) => Option(fun)
		given putSchemaVideoContent: Conversion[Schema.VideoContent, Option[Schema.VideoContent]] = (fun: Schema.VideoContent) => Option(fun)
		given putSchemaNativeContent: Conversion[Schema.NativeContent, Option[Schema.NativeContent]] = (fun: Schema.NativeContent) => Option(fun)
		given putSchemaCreativeCreativeFormatEnum: Conversion[Schema.Creative.CreativeFormatEnum, Option[Schema.Creative.CreativeFormatEnum]] = (fun: Schema.Creative.CreativeFormatEnum) => Option(fun)
		given putListSchemaCreativeRestrictedCategoriesEnum: Conversion[List[Schema.Creative.RestrictedCategoriesEnum], Option[List[Schema.Creative.RestrictedCategoriesEnum]]] = (fun: List[Schema.Creative.RestrictedCategoriesEnum]) => Option(fun)
		given putListSchemaCreativeDeclaredAttributesEnum: Conversion[List[Schema.Creative.DeclaredAttributesEnum], Option[List[Schema.Creative.DeclaredAttributesEnum]]] = (fun: List[Schema.Creative.DeclaredAttributesEnum]) => Option(fun)
		given putListInt: Conversion[List[Int], Option[List[Int]]] = (fun: List[Int]) => Option(fun)
		given putListSchemaCreativeDeclaredRestrictedCategoriesEnum: Conversion[List[Schema.Creative.DeclaredRestrictedCategoriesEnum], Option[List[Schema.Creative.DeclaredRestrictedCategoriesEnum]]] = (fun: List[Schema.Creative.DeclaredRestrictedCategoriesEnum]) => Option(fun)
		given putInt: Conversion[Int, Option[Int]] = (fun: Int) => Option(fun)
		given putSchemaCreativeServingDecision: Conversion[Schema.CreativeServingDecision, Option[Schema.CreativeServingDecision]] = (fun: Schema.CreativeServingDecision) => Option(fun)
		given putSchemaVideoMetadata: Conversion[Schema.VideoMetadata, Option[Schema.VideoMetadata]] = (fun: Schema.VideoMetadata) => Option(fun)
		given putSchemaVideoMetadataVastVersionEnum: Conversion[Schema.VideoMetadata.VastVersionEnum, Option[Schema.VideoMetadata.VastVersionEnum]] = (fun: Schema.VideoMetadata.VastVersionEnum) => Option(fun)
		given putListSchemaMediaFile: Conversion[List[Schema.MediaFile], Option[List[Schema.MediaFile]]] = (fun: List[Schema.MediaFile]) => Option(fun)
		given putSchemaMediaFileMimeTypeEnum: Conversion[Schema.MediaFile.MimeTypeEnum, Option[Schema.MediaFile.MimeTypeEnum]] = (fun: Schema.MediaFile.MimeTypeEnum) => Option(fun)
		given putSchemaImage: Conversion[Schema.Image, Option[Schema.Image]] = (fun: Schema.Image) => Option(fun)
		given putBigDecimal: Conversion[BigDecimal, Option[BigDecimal]] = (fun: BigDecimal) => Option(fun)
		given putListSchemaAdvertiserAndBrand: Conversion[List[Schema.AdvertiserAndBrand], Option[List[Schema.AdvertiserAndBrand]]] = (fun: List[Schema.AdvertiserAndBrand]) => Option(fun)
		given putListSchemaCreativeServingDecisionDetectedAttributesEnum: Conversion[List[Schema.CreativeServingDecision.DetectedAttributesEnum], Option[List[Schema.CreativeServingDecision.DetectedAttributesEnum]]] = (fun: List[Schema.CreativeServingDecision.DetectedAttributesEnum]) => Option(fun)
		given putSchemaAdTechnologyProviders: Conversion[Schema.AdTechnologyProviders, Option[Schema.AdTechnologyProviders]] = (fun: Schema.AdTechnologyProviders) => Option(fun)
		given putSchemaPolicyCompliance: Conversion[Schema.PolicyCompliance, Option[Schema.PolicyCompliance]] = (fun: Schema.PolicyCompliance) => Option(fun)
		given putSchemaCreativeServingDecisionDetectedCategoriesTaxonomyEnum: Conversion[Schema.CreativeServingDecision.DetectedCategoriesTaxonomyEnum, Option[Schema.CreativeServingDecision.DetectedCategoriesTaxonomyEnum]] = (fun: Schema.CreativeServingDecision.DetectedCategoriesTaxonomyEnum) => Option(fun)
		given putSchemaPolicyComplianceStatusEnum: Conversion[Schema.PolicyCompliance.StatusEnum, Option[Schema.PolicyCompliance.StatusEnum]] = (fun: Schema.PolicyCompliance.StatusEnum) => Option(fun)
		given putListSchemaPolicyTopicEntry: Conversion[List[Schema.PolicyTopicEntry], Option[List[Schema.PolicyTopicEntry]]] = (fun: List[Schema.PolicyTopicEntry]) => Option(fun)
		given putListSchemaPolicyTopicEvidence: Conversion[List[Schema.PolicyTopicEvidence], Option[List[Schema.PolicyTopicEvidence]]] = (fun: List[Schema.PolicyTopicEvidence]) => Option(fun)
		given putSchemaDestinationNotWorkingEvidence: Conversion[Schema.DestinationNotWorkingEvidence, Option[Schema.DestinationNotWorkingEvidence]] = (fun: Schema.DestinationNotWorkingEvidence) => Option(fun)
		given putSchemaDestinationNotCrawlableEvidence: Conversion[Schema.DestinationNotCrawlableEvidence, Option[Schema.DestinationNotCrawlableEvidence]] = (fun: Schema.DestinationNotCrawlableEvidence) => Option(fun)
		given putSchemaHttpCallEvidence: Conversion[Schema.HttpCallEvidence, Option[Schema.HttpCallEvidence]] = (fun: Schema.HttpCallEvidence) => Option(fun)
		given putSchemaDomainCallEvidence: Conversion[Schema.DomainCallEvidence, Option[Schema.DomainCallEvidence]] = (fun: Schema.DomainCallEvidence) => Option(fun)
		given putSchemaHttpCookieEvidence: Conversion[Schema.HttpCookieEvidence, Option[Schema.HttpCookieEvidence]] = (fun: Schema.HttpCookieEvidence) => Option(fun)
		given putSchemaDestinationUrlEvidence: Conversion[Schema.DestinationUrlEvidence, Option[Schema.DestinationUrlEvidence]] = (fun: Schema.DestinationUrlEvidence) => Option(fun)
		given putSchemaDownloadSizeEvidence: Conversion[Schema.DownloadSizeEvidence, Option[Schema.DownloadSizeEvidence]] = (fun: Schema.DownloadSizeEvidence) => Option(fun)
		given putSchemaDestinationNotWorkingEvidenceDnsErrorEnum: Conversion[Schema.DestinationNotWorkingEvidence.DnsErrorEnum, Option[Schema.DestinationNotWorkingEvidence.DnsErrorEnum]] = (fun: Schema.DestinationNotWorkingEvidence.DnsErrorEnum) => Option(fun)
		given putSchemaDestinationNotWorkingEvidenceInvalidPageEnum: Conversion[Schema.DestinationNotWorkingEvidence.InvalidPageEnum, Option[Schema.DestinationNotWorkingEvidence.InvalidPageEnum]] = (fun: Schema.DestinationNotWorkingEvidence.InvalidPageEnum) => Option(fun)
		given putSchemaDestinationNotWorkingEvidenceRedirectionErrorEnum: Conversion[Schema.DestinationNotWorkingEvidence.RedirectionErrorEnum, Option[Schema.DestinationNotWorkingEvidence.RedirectionErrorEnum]] = (fun: Schema.DestinationNotWorkingEvidence.RedirectionErrorEnum) => Option(fun)
		given putSchemaDestinationNotWorkingEvidenceUrlRejectedEnum: Conversion[Schema.DestinationNotWorkingEvidence.UrlRejectedEnum, Option[Schema.DestinationNotWorkingEvidence.UrlRejectedEnum]] = (fun: Schema.DestinationNotWorkingEvidence.UrlRejectedEnum) => Option(fun)
		given putSchemaDestinationNotWorkingEvidencePlatformEnum: Conversion[Schema.DestinationNotWorkingEvidence.PlatformEnum, Option[Schema.DestinationNotWorkingEvidence.PlatformEnum]] = (fun: Schema.DestinationNotWorkingEvidence.PlatformEnum) => Option(fun)
		given putSchemaDestinationNotCrawlableEvidenceReasonEnum: Conversion[Schema.DestinationNotCrawlableEvidence.ReasonEnum, Option[Schema.DestinationNotCrawlableEvidence.ReasonEnum]] = (fun: Schema.DestinationNotCrawlableEvidence.ReasonEnum) => Option(fun)
		given putListSchemaDomainCalls: Conversion[List[Schema.DomainCalls], Option[List[Schema.DomainCalls]]] = (fun: List[Schema.DomainCalls]) => Option(fun)
		given putListSchemaUrlDownloadSize: Conversion[List[Schema.UrlDownloadSize], Option[List[Schema.UrlDownloadSize]]] = (fun: List[Schema.UrlDownloadSize]) => Option(fun)
		given putListSchemaPretargetingConfig: Conversion[List[Schema.PretargetingConfig], Option[List[Schema.PretargetingConfig]]] = (fun: List[Schema.PretargetingConfig]) => Option(fun)
		given putSchemaPretargetingConfigStateEnum: Conversion[Schema.PretargetingConfig.StateEnum, Option[Schema.PretargetingConfig.StateEnum]] = (fun: Schema.PretargetingConfig.StateEnum) => Option(fun)
		given putListSchemaPretargetingConfigIncludedFormatsEnum: Conversion[List[Schema.PretargetingConfig.IncludedFormatsEnum], Option[List[Schema.PretargetingConfig.IncludedFormatsEnum]]] = (fun: List[Schema.PretargetingConfig.IncludedFormatsEnum]) => Option(fun)
		given putSchemaNumericTargetingDimension: Conversion[Schema.NumericTargetingDimension, Option[Schema.NumericTargetingDimension]] = (fun: Schema.NumericTargetingDimension) => Option(fun)
		given putSchemaPretargetingConfigInterstitialTargetingEnum: Conversion[Schema.PretargetingConfig.InterstitialTargetingEnum, Option[Schema.PretargetingConfig.InterstitialTargetingEnum]] = (fun: Schema.PretargetingConfig.InterstitialTargetingEnum) => Option(fun)
		given putListSchemaPretargetingConfigAllowedUserTargetingModesEnum: Conversion[List[Schema.PretargetingConfig.AllowedUserTargetingModesEnum], Option[List[Schema.PretargetingConfig.AllowedUserTargetingModesEnum]]] = (fun: List[Schema.PretargetingConfig.AllowedUserTargetingModesEnum]) => Option(fun)
		given putListSchemaPretargetingConfigIncludedUserIdTypesEnum: Conversion[List[Schema.PretargetingConfig.IncludedUserIdTypesEnum], Option[List[Schema.PretargetingConfig.IncludedUserIdTypesEnum]]] = (fun: List[Schema.PretargetingConfig.IncludedUserIdTypesEnum]) => Option(fun)
		given putListSchemaPretargetingConfigIncludedPlatformsEnum: Conversion[List[Schema.PretargetingConfig.IncludedPlatformsEnum], Option[List[Schema.PretargetingConfig.IncludedPlatformsEnum]]] = (fun: List[Schema.PretargetingConfig.IncludedPlatformsEnum]) => Option(fun)
		given putListSchemaCreativeDimensions: Conversion[List[Schema.CreativeDimensions], Option[List[Schema.CreativeDimensions]]] = (fun: List[Schema.CreativeDimensions]) => Option(fun)
		given putListSchemaPretargetingConfigIncludedEnvironmentsEnum: Conversion[List[Schema.PretargetingConfig.IncludedEnvironmentsEnum], Option[List[Schema.PretargetingConfig.IncludedEnvironmentsEnum]]] = (fun: List[Schema.PretargetingConfig.IncludedEnvironmentsEnum]) => Option(fun)
		given putSchemaStringTargetingDimension: Conversion[Schema.StringTargetingDimension, Option[Schema.StringTargetingDimension]] = (fun: Schema.StringTargetingDimension) => Option(fun)
		given putSchemaAppTargeting: Conversion[Schema.AppTargeting, Option[Schema.AppTargeting]] = (fun: Schema.AppTargeting) => Option(fun)
		given putSchemaStringTargetingDimensionTargetingModeEnum: Conversion[Schema.StringTargetingDimension.TargetingModeEnum, Option[Schema.StringTargetingDimension.TargetingModeEnum]] = (fun: Schema.StringTargetingDimension.TargetingModeEnum) => Option(fun)
		given putSchemaAddTargetedSitesRequestTargetingModeEnum: Conversion[Schema.AddTargetedSitesRequest.TargetingModeEnum, Option[Schema.AddTargetedSitesRequest.TargetingModeEnum]] = (fun: Schema.AddTargetedSitesRequest.TargetingModeEnum) => Option(fun)
		given putSchemaAddTargetedAppsRequestTargetingModeEnum: Conversion[Schema.AddTargetedAppsRequest.TargetingModeEnum, Option[Schema.AddTargetedAppsRequest.TargetingModeEnum]] = (fun: Schema.AddTargetedAppsRequest.TargetingModeEnum) => Option(fun)
		given putSchemaAddTargetedPublishersRequestTargetingModeEnum: Conversion[Schema.AddTargetedPublishersRequest.TargetingModeEnum, Option[Schema.AddTargetedPublishersRequest.TargetingModeEnum]] = (fun: Schema.AddTargetedPublishersRequest.TargetingModeEnum) => Option(fun)
		given putListSchemaPublisherConnection: Conversion[List[Schema.PublisherConnection], Option[List[Schema.PublisherConnection]]] = (fun: List[Schema.PublisherConnection]) => Option(fun)
		given putSchemaPublisherConnectionPublisherPlatformEnum: Conversion[Schema.PublisherConnection.PublisherPlatformEnum, Option[Schema.PublisherConnection.PublisherPlatformEnum]] = (fun: Schema.PublisherConnection.PublisherPlatformEnum) => Option(fun)
		given putSchemaPublisherConnectionBiddingStateEnum: Conversion[Schema.PublisherConnection.BiddingStateEnum, Option[Schema.PublisherConnection.BiddingStateEnum]] = (fun: Schema.PublisherConnection.BiddingStateEnum) => Option(fun)
		given putSchemaUserListStatusEnum: Conversion[Schema.UserList.StatusEnum, Option[Schema.UserList.StatusEnum]] = (fun: Schema.UserList.StatusEnum) => Option(fun)
		given putSchemaUrlRestriction: Conversion[Schema.UrlRestriction, Option[Schema.UrlRestriction]] = (fun: Schema.UrlRestriction) => Option(fun)
		given putSchemaUrlRestrictionRestrictionTypeEnum: Conversion[Schema.UrlRestriction.RestrictionTypeEnum, Option[Schema.UrlRestriction.RestrictionTypeEnum]] = (fun: Schema.UrlRestriction.RestrictionTypeEnum) => Option(fun)
		given putSchemaDate: Conversion[Schema.Date, Option[Schema.Date]] = (fun: Schema.Date) => Option(fun)
		given putListSchemaUserList: Conversion[List[Schema.UserList], Option[List[Schema.UserList]]] = (fun: List[Schema.UserList]) => Option(fun)
	}
	object OptionDefault {
		given getString: Conversion[Option[String], String] = (fun: Option[String]) => fun.getOrElse("")
	}
}
