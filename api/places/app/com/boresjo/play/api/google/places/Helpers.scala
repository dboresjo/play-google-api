package com.boresjo.play.api.google.places

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Helpers {
	import Schema.*
	object AutoOption {
		given putBoolean: Conversion[Boolean, Option[Boolean]] = (fun: Boolean) => Option(fun)
		given putSchemaGoogleMapsPlacesV1Circle: Conversion[Schema.GoogleMapsPlacesV1Circle, Option[Schema.GoogleMapsPlacesV1Circle]] = (fun: Schema.GoogleMapsPlacesV1Circle) => Option(fun)
		given putString: Conversion[String, Option[String]] = (fun: String) => Option(fun)
		given putInt: Conversion[Int, Option[Int]] = (fun: Int) => Option(fun)
		given putSchemaGoogleTypeLatLng: Conversion[Schema.GoogleTypeLatLng, Option[Schema.GoogleTypeLatLng]] = (fun: Schema.GoogleTypeLatLng) => Option(fun)
		given putSchemaGoogleMapsPlacesV1RoutingParametersTravelModeEnum: Conversion[Schema.GoogleMapsPlacesV1RoutingParameters.TravelModeEnum, Option[Schema.GoogleMapsPlacesV1RoutingParameters.TravelModeEnum]] = (fun: Schema.GoogleMapsPlacesV1RoutingParameters.TravelModeEnum) => Option(fun)
		given putSchemaGoogleMapsPlacesV1RoutingParametersRoutingPreferenceEnum: Conversion[Schema.GoogleMapsPlacesV1RoutingParameters.RoutingPreferenceEnum, Option[Schema.GoogleMapsPlacesV1RoutingParameters.RoutingPreferenceEnum]] = (fun: Schema.GoogleMapsPlacesV1RoutingParameters.RoutingPreferenceEnum) => Option(fun)
		given putSchemaGoogleMapsPlacesV1RouteModifiers: Conversion[Schema.GoogleMapsPlacesV1RouteModifiers, Option[Schema.GoogleMapsPlacesV1RouteModifiers]] = (fun: Schema.GoogleMapsPlacesV1RouteModifiers) => Option(fun)
		given putListSchemaGoogleMapsPlacesV1AuthorAttribution: Conversion[List[Schema.GoogleMapsPlacesV1AuthorAttribution], Option[List[Schema.GoogleMapsPlacesV1AuthorAttribution]]] = (fun: List[Schema.GoogleMapsPlacesV1AuthorAttribution]) => Option(fun)
		given putSchemaGoogleTypeLocalizedText: Conversion[Schema.GoogleTypeLocalizedText, Option[Schema.GoogleTypeLocalizedText]] = (fun: Schema.GoogleTypeLocalizedText) => Option(fun)
		given putSchemaGoogleMapsPlacesV1References: Conversion[Schema.GoogleMapsPlacesV1References, Option[Schema.GoogleMapsPlacesV1References]] = (fun: Schema.GoogleMapsPlacesV1References) => Option(fun)
		given putSchemaGoogleMapsPlacesV1Polyline: Conversion[Schema.GoogleMapsPlacesV1Polyline, Option[Schema.GoogleMapsPlacesV1Polyline]] = (fun: Schema.GoogleMapsPlacesV1Polyline) => Option(fun)
		given putSchemaGoogleMapsPlacesV1AutocompletePlacesResponseSuggestionStructuredFormat: Conversion[Schema.GoogleMapsPlacesV1AutocompletePlacesResponseSuggestionStructuredFormat, Option[Schema.GoogleMapsPlacesV1AutocompletePlacesResponseSuggestionStructuredFormat]] = (fun: Schema.GoogleMapsPlacesV1AutocompletePlacesResponseSuggestionStructuredFormat) => Option(fun)
		given putSchemaGoogleMapsPlacesV1AutocompletePlacesResponseSuggestionFormattableText: Conversion[Schema.GoogleMapsPlacesV1AutocompletePlacesResponseSuggestionFormattableText, Option[Schema.GoogleMapsPlacesV1AutocompletePlacesResponseSuggestionFormattableText]] = (fun: Schema.GoogleMapsPlacesV1AutocompletePlacesResponseSuggestionFormattableText) => Option(fun)
		given putSchemaGoogleMapsPlacesV1Review: Conversion[Schema.GoogleMapsPlacesV1Review, Option[Schema.GoogleMapsPlacesV1Review]] = (fun: Schema.GoogleMapsPlacesV1Review) => Option(fun)
		given putSchemaGoogleMapsPlacesV1ContextualContentJustificationReviewJustificationHighlightedText: Conversion[Schema.GoogleMapsPlacesV1ContextualContentJustificationReviewJustificationHighlightedText, Option[Schema.GoogleMapsPlacesV1ContextualContentJustificationReviewJustificationHighlightedText]] = (fun: Schema.GoogleMapsPlacesV1ContextualContentJustificationReviewJustificationHighlightedText) => Option(fun)
		given putListSchemaGoogleMapsPlacesV1ContextualContentJustificationReviewJustificationHighlightedTextHighlightedTextRange: Conversion[List[Schema.GoogleMapsPlacesV1ContextualContentJustificationReviewJustificationHighlightedTextHighlightedTextRange], Option[List[Schema.GoogleMapsPlacesV1ContextualContentJustificationReviewJustificationHighlightedTextHighlightedTextRange]]] = (fun: List[Schema.GoogleMapsPlacesV1ContextualContentJustificationReviewJustificationHighlightedTextHighlightedTextRange]) => Option(fun)
		given putSchemaGoogleTypeDate: Conversion[Schema.GoogleTypeDate, Option[Schema.GoogleTypeDate]] = (fun: Schema.GoogleTypeDate) => Option(fun)
		given putListSchemaGoogleMapsPlacesV1RoutingSummary: Conversion[List[Schema.GoogleMapsPlacesV1RoutingSummary], Option[List[Schema.GoogleMapsPlacesV1RoutingSummary]]] = (fun: List[Schema.GoogleMapsPlacesV1RoutingSummary]) => Option(fun)
		given putListSchemaGoogleMapsPlacesV1ContextualContent: Conversion[List[Schema.GoogleMapsPlacesV1ContextualContent], Option[List[Schema.GoogleMapsPlacesV1ContextualContent]]] = (fun: List[Schema.GoogleMapsPlacesV1ContextualContent]) => Option(fun)
		given putListSchemaGoogleMapsPlacesV1Place: Conversion[List[Schema.GoogleMapsPlacesV1Place], Option[List[Schema.GoogleMapsPlacesV1Place]]] = (fun: List[Schema.GoogleMapsPlacesV1Place]) => Option(fun)
		given putSchemaGoogleMapsPlacesV1PlaceOpeningHoursPeriodPoint: Conversion[Schema.GoogleMapsPlacesV1PlaceOpeningHoursPeriodPoint, Option[Schema.GoogleMapsPlacesV1PlaceOpeningHoursPeriodPoint]] = (fun: Schema.GoogleMapsPlacesV1PlaceOpeningHoursPeriodPoint) => Option(fun)
		given putSchemaGoogleGeoTypeViewport: Conversion[Schema.GoogleGeoTypeViewport, Option[Schema.GoogleGeoTypeViewport]] = (fun: Schema.GoogleGeoTypeViewport) => Option(fun)
		given putListSchemaGoogleMapsPlacesV1PlaceSubDestination: Conversion[List[Schema.GoogleMapsPlacesV1PlaceSubDestination], Option[List[Schema.GoogleMapsPlacesV1PlaceSubDestination]]] = (fun: List[Schema.GoogleMapsPlacesV1PlaceSubDestination]) => Option(fun)
		given putSchemaGoogleMapsPlacesV1FuelOptions: Conversion[Schema.GoogleMapsPlacesV1FuelOptions, Option[Schema.GoogleMapsPlacesV1FuelOptions]] = (fun: Schema.GoogleMapsPlacesV1FuelOptions) => Option(fun)
		given putSchemaGoogleMapsPlacesV1PlacePaymentOptions: Conversion[Schema.GoogleMapsPlacesV1PlacePaymentOptions, Option[Schema.GoogleMapsPlacesV1PlacePaymentOptions]] = (fun: Schema.GoogleMapsPlacesV1PlacePaymentOptions) => Option(fun)
		given putSchemaGoogleMapsPlacesV1PlacePlusCode: Conversion[Schema.GoogleMapsPlacesV1PlacePlusCode, Option[Schema.GoogleMapsPlacesV1PlacePlusCode]] = (fun: Schema.GoogleMapsPlacesV1PlacePlusCode) => Option(fun)
		given putSchemaGoogleMapsPlacesV1PlaceGoogleMapsLinks: Conversion[Schema.GoogleMapsPlacesV1PlaceGoogleMapsLinks, Option[Schema.GoogleMapsPlacesV1PlaceGoogleMapsLinks]] = (fun: Schema.GoogleMapsPlacesV1PlaceGoogleMapsLinks) => Option(fun)
		given putSchemaGoogleMapsPlacesV1PlaceAccessibilityOptions: Conversion[Schema.GoogleMapsPlacesV1PlaceAccessibilityOptions, Option[Schema.GoogleMapsPlacesV1PlaceAccessibilityOptions]] = (fun: Schema.GoogleMapsPlacesV1PlaceAccessibilityOptions) => Option(fun)
		given putListSchemaGoogleMapsPlacesV1PlaceAttribution: Conversion[List[Schema.GoogleMapsPlacesV1PlaceAttribution], Option[List[Schema.GoogleMapsPlacesV1PlaceAttribution]]] = (fun: List[Schema.GoogleMapsPlacesV1PlaceAttribution]) => Option(fun)
		given putListSchemaGoogleMapsPlacesV1Photo: Conversion[List[Schema.GoogleMapsPlacesV1Photo], Option[List[Schema.GoogleMapsPlacesV1Photo]]] = (fun: List[Schema.GoogleMapsPlacesV1Photo]) => Option(fun)
		given putSchemaGoogleMapsPlacesV1PlaceBusinessStatusEnum: Conversion[Schema.GoogleMapsPlacesV1Place.BusinessStatusEnum, Option[Schema.GoogleMapsPlacesV1Place.BusinessStatusEnum]] = (fun: Schema.GoogleMapsPlacesV1Place.BusinessStatusEnum) => Option(fun)
		given putListString: Conversion[List[String], Option[List[String]]] = (fun: List[String]) => Option(fun)
		given putListSchemaGoogleMapsPlacesV1PlaceAddressComponent: Conversion[List[Schema.GoogleMapsPlacesV1PlaceAddressComponent], Option[List[Schema.GoogleMapsPlacesV1PlaceAddressComponent]]] = (fun: List[Schema.GoogleMapsPlacesV1PlaceAddressComponent]) => Option(fun)
		given putListSchemaGoogleMapsPlacesV1PlaceContainingPlace: Conversion[List[Schema.GoogleMapsPlacesV1PlaceContainingPlace], Option[List[Schema.GoogleMapsPlacesV1PlaceContainingPlace]]] = (fun: List[Schema.GoogleMapsPlacesV1PlaceContainingPlace]) => Option(fun)
		given putSchemaGoogleMapsPlacesV1PlaceGenerativeSummary: Conversion[Schema.GoogleMapsPlacesV1PlaceGenerativeSummary, Option[Schema.GoogleMapsPlacesV1PlaceGenerativeSummary]] = (fun: Schema.GoogleMapsPlacesV1PlaceGenerativeSummary) => Option(fun)
		given putSchemaGoogleMapsPlacesV1PlaceOpeningHours: Conversion[Schema.GoogleMapsPlacesV1PlaceOpeningHours, Option[Schema.GoogleMapsPlacesV1PlaceOpeningHours]] = (fun: Schema.GoogleMapsPlacesV1PlaceOpeningHours) => Option(fun)
		given putSchemaGoogleMapsPlacesV1AddressDescriptor: Conversion[Schema.GoogleMapsPlacesV1AddressDescriptor, Option[Schema.GoogleMapsPlacesV1AddressDescriptor]] = (fun: Schema.GoogleMapsPlacesV1AddressDescriptor) => Option(fun)
		given putBigDecimal: Conversion[BigDecimal, Option[BigDecimal]] = (fun: BigDecimal) => Option(fun)
		given putSchemaGoogleMapsPlacesV1PriceRange: Conversion[Schema.GoogleMapsPlacesV1PriceRange, Option[Schema.GoogleMapsPlacesV1PriceRange]] = (fun: Schema.GoogleMapsPlacesV1PriceRange) => Option(fun)
		given putSchemaGoogleMapsPlacesV1PlaceParkingOptions: Conversion[Schema.GoogleMapsPlacesV1PlaceParkingOptions, Option[Schema.GoogleMapsPlacesV1PlaceParkingOptions]] = (fun: Schema.GoogleMapsPlacesV1PlaceParkingOptions) => Option(fun)
		given putListSchemaGoogleMapsPlacesV1Review: Conversion[List[Schema.GoogleMapsPlacesV1Review], Option[List[Schema.GoogleMapsPlacesV1Review]]] = (fun: List[Schema.GoogleMapsPlacesV1Review]) => Option(fun)
		given putListSchemaGoogleMapsPlacesV1PlaceOpeningHours: Conversion[List[Schema.GoogleMapsPlacesV1PlaceOpeningHours], Option[List[Schema.GoogleMapsPlacesV1PlaceOpeningHours]]] = (fun: List[Schema.GoogleMapsPlacesV1PlaceOpeningHours]) => Option(fun)
		given putSchemaGoogleMapsPlacesV1PlaceAreaSummary: Conversion[Schema.GoogleMapsPlacesV1PlaceAreaSummary, Option[Schema.GoogleMapsPlacesV1PlaceAreaSummary]] = (fun: Schema.GoogleMapsPlacesV1PlaceAreaSummary) => Option(fun)
		given putSchemaGoogleMapsPlacesV1EVChargeOptions: Conversion[Schema.GoogleMapsPlacesV1EVChargeOptions, Option[Schema.GoogleMapsPlacesV1EVChargeOptions]] = (fun: Schema.GoogleMapsPlacesV1EVChargeOptions) => Option(fun)
		given putSchemaGoogleMapsPlacesV1PlacePriceLevelEnum: Conversion[Schema.GoogleMapsPlacesV1Place.PriceLevelEnum, Option[Schema.GoogleMapsPlacesV1Place.PriceLevelEnum]] = (fun: Schema.GoogleMapsPlacesV1Place.PriceLevelEnum) => Option(fun)
		given putListSchemaGoogleMapsPlacesV1AddressDescriptorLandmark: Conversion[List[Schema.GoogleMapsPlacesV1AddressDescriptorLandmark], Option[List[Schema.GoogleMapsPlacesV1AddressDescriptorLandmark]]] = (fun: List[Schema.GoogleMapsPlacesV1AddressDescriptorLandmark]) => Option(fun)
		given putListSchemaGoogleMapsPlacesV1AddressDescriptorArea: Conversion[List[Schema.GoogleMapsPlacesV1AddressDescriptorArea], Option[List[Schema.GoogleMapsPlacesV1AddressDescriptorArea]]] = (fun: List[Schema.GoogleMapsPlacesV1AddressDescriptorArea]) => Option(fun)
		given putSchemaGoogleMapsPlacesV1SearchTextRequestEVOptions: Conversion[Schema.GoogleMapsPlacesV1SearchTextRequestEVOptions, Option[Schema.GoogleMapsPlacesV1SearchTextRequestEVOptions]] = (fun: Schema.GoogleMapsPlacesV1SearchTextRequestEVOptions) => Option(fun)
		given putSchemaGoogleMapsPlacesV1RoutingParameters: Conversion[Schema.GoogleMapsPlacesV1RoutingParameters, Option[Schema.GoogleMapsPlacesV1RoutingParameters]] = (fun: Schema.GoogleMapsPlacesV1RoutingParameters) => Option(fun)
		given putSchemaGoogleMapsPlacesV1SearchTextRequestLocationRestriction: Conversion[Schema.GoogleMapsPlacesV1SearchTextRequestLocationRestriction, Option[Schema.GoogleMapsPlacesV1SearchTextRequestLocationRestriction]] = (fun: Schema.GoogleMapsPlacesV1SearchTextRequestLocationRestriction) => Option(fun)
		given putSchemaGoogleMapsPlacesV1SearchTextRequestSearchAlongRouteParameters: Conversion[Schema.GoogleMapsPlacesV1SearchTextRequestSearchAlongRouteParameters, Option[Schema.GoogleMapsPlacesV1SearchTextRequestSearchAlongRouteParameters]] = (fun: Schema.GoogleMapsPlacesV1SearchTextRequestSearchAlongRouteParameters) => Option(fun)
		given putSchemaGoogleMapsPlacesV1SearchTextRequestLocationBias: Conversion[Schema.GoogleMapsPlacesV1SearchTextRequestLocationBias, Option[Schema.GoogleMapsPlacesV1SearchTextRequestLocationBias]] = (fun: Schema.GoogleMapsPlacesV1SearchTextRequestLocationBias) => Option(fun)
		given putSchemaGoogleMapsPlacesV1SearchTextRequestRankPreferenceEnum: Conversion[Schema.GoogleMapsPlacesV1SearchTextRequest.RankPreferenceEnum, Option[Schema.GoogleMapsPlacesV1SearchTextRequest.RankPreferenceEnum]] = (fun: Schema.GoogleMapsPlacesV1SearchTextRequest.RankPreferenceEnum) => Option(fun)
		given putListSchemaGoogleMapsPlacesV1SearchTextRequestPriceLevelsEnum: Conversion[List[Schema.GoogleMapsPlacesV1SearchTextRequest.PriceLevelsEnum], Option[List[Schema.GoogleMapsPlacesV1SearchTextRequest.PriceLevelsEnum]]] = (fun: List[Schema.GoogleMapsPlacesV1SearchTextRequest.PriceLevelsEnum]) => Option(fun)
		given putSchemaGoogleMapsPlacesV1ContextualContentJustificationReviewJustification: Conversion[Schema.GoogleMapsPlacesV1ContextualContentJustificationReviewJustification, Option[Schema.GoogleMapsPlacesV1ContextualContentJustificationReviewJustification]] = (fun: Schema.GoogleMapsPlacesV1ContextualContentJustificationReviewJustification) => Option(fun)
		given putSchemaGoogleMapsPlacesV1ContextualContentJustificationBusinessAvailabilityAttributesJustification: Conversion[Schema.GoogleMapsPlacesV1ContextualContentJustificationBusinessAvailabilityAttributesJustification, Option[Schema.GoogleMapsPlacesV1ContextualContentJustificationBusinessAvailabilityAttributesJustification]] = (fun: Schema.GoogleMapsPlacesV1ContextualContentJustificationBusinessAvailabilityAttributesJustification) => Option(fun)
		given putSchemaGoogleMapsPlacesV1AutocompletePlacesRequestLocationBias: Conversion[Schema.GoogleMapsPlacesV1AutocompletePlacesRequestLocationBias, Option[Schema.GoogleMapsPlacesV1AutocompletePlacesRequestLocationBias]] = (fun: Schema.GoogleMapsPlacesV1AutocompletePlacesRequestLocationBias) => Option(fun)
		given putSchemaGoogleMapsPlacesV1AutocompletePlacesRequestLocationRestriction: Conversion[Schema.GoogleMapsPlacesV1AutocompletePlacesRequestLocationRestriction, Option[Schema.GoogleMapsPlacesV1AutocompletePlacesRequestLocationRestriction]] = (fun: Schema.GoogleMapsPlacesV1AutocompletePlacesRequestLocationRestriction) => Option(fun)
		given putSchemaGoogleMapsPlacesV1SearchNearbyRequestRankPreferenceEnum: Conversion[Schema.GoogleMapsPlacesV1SearchNearbyRequest.RankPreferenceEnum, Option[Schema.GoogleMapsPlacesV1SearchNearbyRequest.RankPreferenceEnum]] = (fun: Schema.GoogleMapsPlacesV1SearchNearbyRequest.RankPreferenceEnum) => Option(fun)
		given putSchemaGoogleMapsPlacesV1SearchNearbyRequestLocationRestriction: Conversion[Schema.GoogleMapsPlacesV1SearchNearbyRequestLocationRestriction, Option[Schema.GoogleMapsPlacesV1SearchNearbyRequestLocationRestriction]] = (fun: Schema.GoogleMapsPlacesV1SearchNearbyRequestLocationRestriction) => Option(fun)
		given putListSchemaGoogleMapsPlacesV1AutocompletePlacesResponseSuggestion: Conversion[List[Schema.GoogleMapsPlacesV1AutocompletePlacesResponseSuggestion], Option[List[Schema.GoogleMapsPlacesV1AutocompletePlacesResponseSuggestion]]] = (fun: List[Schema.GoogleMapsPlacesV1AutocompletePlacesResponseSuggestion]) => Option(fun)
		given putListSchemaGoogleMapsPlacesV1EVChargeOptionsConnectorAggregation: Conversion[List[Schema.GoogleMapsPlacesV1EVChargeOptionsConnectorAggregation], Option[List[Schema.GoogleMapsPlacesV1EVChargeOptionsConnectorAggregation]]] = (fun: List[Schema.GoogleMapsPlacesV1EVChargeOptionsConnectorAggregation]) => Option(fun)
		given putListSchemaGoogleMapsPlacesV1ContentBlock: Conversion[List[Schema.GoogleMapsPlacesV1ContentBlock], Option[List[Schema.GoogleMapsPlacesV1ContentBlock]]] = (fun: List[Schema.GoogleMapsPlacesV1ContentBlock]) => Option(fun)
		given putSchemaGoogleMapsPlacesV1AddressDescriptorAreaContainmentEnum: Conversion[Schema.GoogleMapsPlacesV1AddressDescriptorArea.ContainmentEnum, Option[Schema.GoogleMapsPlacesV1AddressDescriptorArea.ContainmentEnum]] = (fun: Schema.GoogleMapsPlacesV1AddressDescriptorArea.ContainmentEnum) => Option(fun)
		given putListSchemaGoogleMapsPlacesV1AutocompletePlacesResponseSuggestionStringRange: Conversion[List[Schema.GoogleMapsPlacesV1AutocompletePlacesResponseSuggestionStringRange], Option[List[Schema.GoogleMapsPlacesV1AutocompletePlacesResponseSuggestionStringRange]]] = (fun: List[Schema.GoogleMapsPlacesV1AutocompletePlacesResponseSuggestionStringRange]) => Option(fun)
		given putListSchemaGoogleMapsPlacesV1FuelOptionsFuelPrice: Conversion[List[Schema.GoogleMapsPlacesV1FuelOptionsFuelPrice], Option[List[Schema.GoogleMapsPlacesV1FuelOptionsFuelPrice]]] = (fun: List[Schema.GoogleMapsPlacesV1FuelOptionsFuelPrice]) => Option(fun)
		given putSchemaGoogleMapsPlacesV1AutocompletePlacesResponseSuggestionPlacePrediction: Conversion[Schema.GoogleMapsPlacesV1AutocompletePlacesResponseSuggestionPlacePrediction, Option[Schema.GoogleMapsPlacesV1AutocompletePlacesResponseSuggestionPlacePrediction]] = (fun: Schema.GoogleMapsPlacesV1AutocompletePlacesResponseSuggestionPlacePrediction) => Option(fun)
		given putSchemaGoogleMapsPlacesV1AutocompletePlacesResponseSuggestionQueryPrediction: Conversion[Schema.GoogleMapsPlacesV1AutocompletePlacesResponseSuggestionQueryPrediction, Option[Schema.GoogleMapsPlacesV1AutocompletePlacesResponseSuggestionQueryPrediction]] = (fun: Schema.GoogleMapsPlacesV1AutocompletePlacesResponseSuggestionQueryPrediction) => Option(fun)
		given putListSchemaGoogleMapsPlacesV1SearchTextRequestEVOptionsConnectorTypesEnum: Conversion[List[Schema.GoogleMapsPlacesV1SearchTextRequestEVOptions.ConnectorTypesEnum], Option[List[Schema.GoogleMapsPlacesV1SearchTextRequestEVOptions.ConnectorTypesEnum]]] = (fun: List[Schema.GoogleMapsPlacesV1SearchTextRequestEVOptions.ConnectorTypesEnum]) => Option(fun)
		given putSchemaGoogleMapsPlacesV1EVChargeOptionsConnectorAggregationTypeEnum: Conversion[Schema.GoogleMapsPlacesV1EVChargeOptionsConnectorAggregation.TypeEnum, Option[Schema.GoogleMapsPlacesV1EVChargeOptionsConnectorAggregation.TypeEnum]] = (fun: Schema.GoogleMapsPlacesV1EVChargeOptionsConnectorAggregation.TypeEnum) => Option(fun)
		given putListSchemaGoogleMapsPlacesV1ContextualContentJustification: Conversion[List[Schema.GoogleMapsPlacesV1ContextualContentJustification], Option[List[Schema.GoogleMapsPlacesV1ContextualContentJustification]]] = (fun: List[Schema.GoogleMapsPlacesV1ContextualContentJustification]) => Option(fun)
		given putSchemaGoogleMapsPlacesV1AuthorAttribution: Conversion[Schema.GoogleMapsPlacesV1AuthorAttribution, Option[Schema.GoogleMapsPlacesV1AuthorAttribution]] = (fun: Schema.GoogleMapsPlacesV1AuthorAttribution) => Option(fun)
		given putSchemaGoogleMapsPlacesV1FuelOptionsFuelPriceTypeEnum: Conversion[Schema.GoogleMapsPlacesV1FuelOptionsFuelPrice.TypeEnum, Option[Schema.GoogleMapsPlacesV1FuelOptionsFuelPrice.TypeEnum]] = (fun: Schema.GoogleMapsPlacesV1FuelOptionsFuelPrice.TypeEnum) => Option(fun)
		given putSchemaGoogleTypeMoney: Conversion[Schema.GoogleTypeMoney, Option[Schema.GoogleTypeMoney]] = (fun: Schema.GoogleTypeMoney) => Option(fun)
		given putListSchemaGoogleMapsPlacesV1RoutingSummaryLeg: Conversion[List[Schema.GoogleMapsPlacesV1RoutingSummaryLeg], Option[List[Schema.GoogleMapsPlacesV1RoutingSummaryLeg]]] = (fun: List[Schema.GoogleMapsPlacesV1RoutingSummaryLeg]) => Option(fun)
		given putListSchemaGoogleMapsPlacesV1PlaceOpeningHoursSpecialDay: Conversion[List[Schema.GoogleMapsPlacesV1PlaceOpeningHoursSpecialDay], Option[List[Schema.GoogleMapsPlacesV1PlaceOpeningHoursSpecialDay]]] = (fun: List[Schema.GoogleMapsPlacesV1PlaceOpeningHoursSpecialDay]) => Option(fun)
		given putListSchemaGoogleMapsPlacesV1PlaceOpeningHoursPeriod: Conversion[List[Schema.GoogleMapsPlacesV1PlaceOpeningHoursPeriod], Option[List[Schema.GoogleMapsPlacesV1PlaceOpeningHoursPeriod]]] = (fun: List[Schema.GoogleMapsPlacesV1PlaceOpeningHoursPeriod]) => Option(fun)
		given putSchemaGoogleMapsPlacesV1PlaceOpeningHoursSecondaryHoursTypeEnum: Conversion[Schema.GoogleMapsPlacesV1PlaceOpeningHours.SecondaryHoursTypeEnum, Option[Schema.GoogleMapsPlacesV1PlaceOpeningHours.SecondaryHoursTypeEnum]] = (fun: Schema.GoogleMapsPlacesV1PlaceOpeningHours.SecondaryHoursTypeEnum) => Option(fun)
		given putSchemaGoogleMapsPlacesV1AddressDescriptorLandmarkSpatialRelationshipEnum: Conversion[Schema.GoogleMapsPlacesV1AddressDescriptorLandmark.SpatialRelationshipEnum, Option[Schema.GoogleMapsPlacesV1AddressDescriptorLandmark.SpatialRelationshipEnum]] = (fun: Schema.GoogleMapsPlacesV1AddressDescriptorLandmark.SpatialRelationshipEnum) => Option(fun)
	}
	object OptionDefault {
		given getString: Conversion[Option[String], String] = (fun: Option[String]) => fun.getOrElse("")
	}
}
