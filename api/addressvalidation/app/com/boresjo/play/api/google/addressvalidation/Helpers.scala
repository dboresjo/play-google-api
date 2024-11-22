package com.boresjo.play.api.google.addressvalidation

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Helpers {
	import Schema.*
	object AutoOption {
		given putString: Conversion[String, Option[String]] = (fun: String) => Option(fun)
		given putBoolean: Conversion[Boolean, Option[Boolean]] = (fun: Boolean) => Option(fun)
		given putSchemaGoogleMapsAddressvalidationV1ValidationResult: Conversion[Schema.GoogleMapsAddressvalidationV1ValidationResult, Option[Schema.GoogleMapsAddressvalidationV1ValidationResult]] = (fun: Schema.GoogleMapsAddressvalidationV1ValidationResult) => Option(fun)
		given putListString: Conversion[List[String], Option[List[String]]] = (fun: List[String]) => Option(fun)
		given putInt: Conversion[Int, Option[Int]] = (fun: Int) => Option(fun)
		given putSchemaGoogleMapsAddressvalidationV1Verdict: Conversion[Schema.GoogleMapsAddressvalidationV1Verdict, Option[Schema.GoogleMapsAddressvalidationV1Verdict]] = (fun: Schema.GoogleMapsAddressvalidationV1Verdict) => Option(fun)
		given putSchemaGoogleMapsAddressvalidationV1Address: Conversion[Schema.GoogleMapsAddressvalidationV1Address, Option[Schema.GoogleMapsAddressvalidationV1Address]] = (fun: Schema.GoogleMapsAddressvalidationV1Address) => Option(fun)
		given putSchemaGoogleMapsAddressvalidationV1Geocode: Conversion[Schema.GoogleMapsAddressvalidationV1Geocode, Option[Schema.GoogleMapsAddressvalidationV1Geocode]] = (fun: Schema.GoogleMapsAddressvalidationV1Geocode) => Option(fun)
		given putSchemaGoogleMapsAddressvalidationV1AddressMetadata: Conversion[Schema.GoogleMapsAddressvalidationV1AddressMetadata, Option[Schema.GoogleMapsAddressvalidationV1AddressMetadata]] = (fun: Schema.GoogleMapsAddressvalidationV1AddressMetadata) => Option(fun)
		given putSchemaGoogleMapsAddressvalidationV1UspsData: Conversion[Schema.GoogleMapsAddressvalidationV1UspsData, Option[Schema.GoogleMapsAddressvalidationV1UspsData]] = (fun: Schema.GoogleMapsAddressvalidationV1UspsData) => Option(fun)
		given putBigDecimal: Conversion[BigDecimal, Option[BigDecimal]] = (fun: BigDecimal) => Option(fun)
		given putSchemaGoogleTypeLatLng: Conversion[Schema.GoogleTypeLatLng, Option[Schema.GoogleTypeLatLng]] = (fun: Schema.GoogleTypeLatLng) => Option(fun)
		given putSchemaGoogleMapsAddressvalidationV1AddressComponentConfirmationLevelEnum: Conversion[Schema.GoogleMapsAddressvalidationV1AddressComponent.ConfirmationLevelEnum, Option[Schema.GoogleMapsAddressvalidationV1AddressComponent.ConfirmationLevelEnum]] = (fun: Schema.GoogleMapsAddressvalidationV1AddressComponent.ConfirmationLevelEnum) => Option(fun)
		given putSchemaGoogleMapsAddressvalidationV1ComponentName: Conversion[Schema.GoogleMapsAddressvalidationV1ComponentName, Option[Schema.GoogleMapsAddressvalidationV1ComponentName]] = (fun: Schema.GoogleMapsAddressvalidationV1ComponentName) => Option(fun)
		given putSchemaGoogleTypePostalAddress: Conversion[Schema.GoogleTypePostalAddress, Option[Schema.GoogleTypePostalAddress]] = (fun: Schema.GoogleTypePostalAddress) => Option(fun)
		given putSchemaGoogleMapsAddressvalidationV1LanguageOptions: Conversion[Schema.GoogleMapsAddressvalidationV1LanguageOptions, Option[Schema.GoogleMapsAddressvalidationV1LanguageOptions]] = (fun: Schema.GoogleMapsAddressvalidationV1LanguageOptions) => Option(fun)
		given putListSchemaGoogleMapsAddressvalidationV1AddressComponent: Conversion[List[Schema.GoogleMapsAddressvalidationV1AddressComponent], Option[List[Schema.GoogleMapsAddressvalidationV1AddressComponent]]] = (fun: List[Schema.GoogleMapsAddressvalidationV1AddressComponent]) => Option(fun)
		given putSchemaGoogleMapsAddressvalidationV1PlusCode: Conversion[Schema.GoogleMapsAddressvalidationV1PlusCode, Option[Schema.GoogleMapsAddressvalidationV1PlusCode]] = (fun: Schema.GoogleMapsAddressvalidationV1PlusCode) => Option(fun)
		given putSchemaGoogleGeoTypeViewport: Conversion[Schema.GoogleGeoTypeViewport, Option[Schema.GoogleGeoTypeViewport]] = (fun: Schema.GoogleGeoTypeViewport) => Option(fun)
		given putSchemaGoogleMapsAddressvalidationV1ProvideValidationFeedbackRequestConclusionEnum: Conversion[Schema.GoogleMapsAddressvalidationV1ProvideValidationFeedbackRequest.ConclusionEnum, Option[Schema.GoogleMapsAddressvalidationV1ProvideValidationFeedbackRequest.ConclusionEnum]] = (fun: Schema.GoogleMapsAddressvalidationV1ProvideValidationFeedbackRequest.ConclusionEnum) => Option(fun)
		given putSchemaGoogleMapsAddressvalidationV1VerdictGeocodeGranularityEnum: Conversion[Schema.GoogleMapsAddressvalidationV1Verdict.GeocodeGranularityEnum, Option[Schema.GoogleMapsAddressvalidationV1Verdict.GeocodeGranularityEnum]] = (fun: Schema.GoogleMapsAddressvalidationV1Verdict.GeocodeGranularityEnum) => Option(fun)
		given putSchemaGoogleMapsAddressvalidationV1VerdictValidationGranularityEnum: Conversion[Schema.GoogleMapsAddressvalidationV1Verdict.ValidationGranularityEnum, Option[Schema.GoogleMapsAddressvalidationV1Verdict.ValidationGranularityEnum]] = (fun: Schema.GoogleMapsAddressvalidationV1Verdict.ValidationGranularityEnum) => Option(fun)
		given putSchemaGoogleMapsAddressvalidationV1VerdictInputGranularityEnum: Conversion[Schema.GoogleMapsAddressvalidationV1Verdict.InputGranularityEnum, Option[Schema.GoogleMapsAddressvalidationV1Verdict.InputGranularityEnum]] = (fun: Schema.GoogleMapsAddressvalidationV1Verdict.InputGranularityEnum) => Option(fun)
		given putSchemaGoogleMapsAddressvalidationV1UspsAddress: Conversion[Schema.GoogleMapsAddressvalidationV1UspsAddress, Option[Schema.GoogleMapsAddressvalidationV1UspsAddress]] = (fun: Schema.GoogleMapsAddressvalidationV1UspsAddress) => Option(fun)
	}
	object OptionDefault {
		given getString: Conversion[Option[String], String] = (fun: Option[String]) => fun.getOrElse("")
	}
}
