package com.boresjo.play.api.google.mybusinessbusinessinformation

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Helpers {
	import Schema.*
	object AutoOption {
		given putListSchemaAttributeMetadata: Conversion[List[Schema.AttributeMetadata], Option[List[Schema.AttributeMetadata]]] = (fun: List[Schema.AttributeMetadata]) => Option(fun)
		given putString: Conversion[String, Option[String]] = (fun: String) => Option(fun)
		given putSchemaAttributeMetadataValueTypeEnum: Conversion[Schema.AttributeMetadata.ValueTypeEnum, Option[Schema.AttributeMetadata.ValueTypeEnum]] = (fun: Schema.AttributeMetadata.ValueTypeEnum) => Option(fun)
		given putBoolean: Conversion[Boolean, Option[Boolean]] = (fun: Boolean) => Option(fun)
		given putListSchemaAttributeValueMetadata: Conversion[List[Schema.AttributeValueMetadata], Option[List[Schema.AttributeValueMetadata]]] = (fun: List[Schema.AttributeValueMetadata]) => Option(fun)
		given putJsValue: Conversion[JsValue, Option[JsValue]] = (fun: JsValue) => Option(fun)
		given putListSchemaAttribute: Conversion[List[Schema.Attribute], Option[List[Schema.Attribute]]] = (fun: List[Schema.Attribute]) => Option(fun)
		given putSchemaAttributeValueTypeEnum: Conversion[Schema.Attribute.ValueTypeEnum, Option[Schema.Attribute.ValueTypeEnum]] = (fun: Schema.Attribute.ValueTypeEnum) => Option(fun)
		given putListJsValue: Conversion[List[JsValue], Option[List[JsValue]]] = (fun: List[JsValue]) => Option(fun)
		given putSchemaRepeatedEnumAttributeValue: Conversion[Schema.RepeatedEnumAttributeValue, Option[Schema.RepeatedEnumAttributeValue]] = (fun: Schema.RepeatedEnumAttributeValue) => Option(fun)
		given putListSchemaUriAttributeValue: Conversion[List[Schema.UriAttributeValue], Option[List[Schema.UriAttributeValue]]] = (fun: List[Schema.UriAttributeValue]) => Option(fun)
		given putListString: Conversion[List[String], Option[List[String]]] = (fun: List[String]) => Option(fun)
		given putListSchemaCategory: Conversion[List[Schema.Category], Option[List[Schema.Category]]] = (fun: List[Schema.Category]) => Option(fun)
		given putListSchemaServiceType: Conversion[List[Schema.ServiceType], Option[List[Schema.ServiceType]]] = (fun: List[Schema.ServiceType]) => Option(fun)
		given putListSchemaMoreHoursType: Conversion[List[Schema.MoreHoursType], Option[List[Schema.MoreHoursType]]] = (fun: List[Schema.MoreHoursType]) => Option(fun)
		given putListSchemaChainName: Conversion[List[Schema.ChainName], Option[List[Schema.ChainName]]] = (fun: List[Schema.ChainName]) => Option(fun)
		given putListSchemaChainUri: Conversion[List[Schema.ChainUri], Option[List[Schema.ChainUri]]] = (fun: List[Schema.ChainUri]) => Option(fun)
		given putInt: Conversion[Int, Option[Int]] = (fun: Int) => Option(fun)
		given putListSchemaChain: Conversion[List[Schema.Chain], Option[List[Schema.Chain]]] = (fun: List[Schema.Chain]) => Option(fun)
		given putSchemaLocation: Conversion[Schema.Location, Option[Schema.Location]] = (fun: Schema.Location) => Option(fun)
		given putSchemaPhoneNumbers: Conversion[Schema.PhoneNumbers, Option[Schema.PhoneNumbers]] = (fun: Schema.PhoneNumbers) => Option(fun)
		given putSchemaCategories: Conversion[Schema.Categories, Option[Schema.Categories]] = (fun: Schema.Categories) => Option(fun)
		given putSchemaPostalAddress: Conversion[Schema.PostalAddress, Option[Schema.PostalAddress]] = (fun: Schema.PostalAddress) => Option(fun)
		given putSchemaBusinessHours: Conversion[Schema.BusinessHours, Option[Schema.BusinessHours]] = (fun: Schema.BusinessHours) => Option(fun)
		given putSchemaSpecialHours: Conversion[Schema.SpecialHours, Option[Schema.SpecialHours]] = (fun: Schema.SpecialHours) => Option(fun)
		given putSchemaServiceAreaBusiness: Conversion[Schema.ServiceAreaBusiness, Option[Schema.ServiceAreaBusiness]] = (fun: Schema.ServiceAreaBusiness) => Option(fun)
		given putSchemaAdWordsLocationExtensions: Conversion[Schema.AdWordsLocationExtensions, Option[Schema.AdWordsLocationExtensions]] = (fun: Schema.AdWordsLocationExtensions) => Option(fun)
		given putSchemaLatLng: Conversion[Schema.LatLng, Option[Schema.LatLng]] = (fun: Schema.LatLng) => Option(fun)
		given putSchemaOpenInfo: Conversion[Schema.OpenInfo, Option[Schema.OpenInfo]] = (fun: Schema.OpenInfo) => Option(fun)
		given putSchemaMetadata: Conversion[Schema.Metadata, Option[Schema.Metadata]] = (fun: Schema.Metadata) => Option(fun)
		given putSchemaProfile: Conversion[Schema.Profile, Option[Schema.Profile]] = (fun: Schema.Profile) => Option(fun)
		given putSchemaRelationshipData: Conversion[Schema.RelationshipData, Option[Schema.RelationshipData]] = (fun: Schema.RelationshipData) => Option(fun)
		given putListSchemaMoreHours: Conversion[List[Schema.MoreHours], Option[List[Schema.MoreHours]]] = (fun: List[Schema.MoreHours]) => Option(fun)
		given putListSchemaServiceItem: Conversion[List[Schema.ServiceItem], Option[List[Schema.ServiceItem]]] = (fun: List[Schema.ServiceItem]) => Option(fun)
		given putSchemaCategory: Conversion[Schema.Category, Option[Schema.Category]] = (fun: Schema.Category) => Option(fun)
		given putListSchemaTimePeriod: Conversion[List[Schema.TimePeriod], Option[List[Schema.TimePeriod]]] = (fun: List[Schema.TimePeriod]) => Option(fun)
		given putSchemaTimePeriodOpenDayEnum: Conversion[Schema.TimePeriod.OpenDayEnum, Option[Schema.TimePeriod.OpenDayEnum]] = (fun: Schema.TimePeriod.OpenDayEnum) => Option(fun)
		given putSchemaTimeOfDay: Conversion[Schema.TimeOfDay, Option[Schema.TimeOfDay]] = (fun: Schema.TimeOfDay) => Option(fun)
		given putSchemaTimePeriodCloseDayEnum: Conversion[Schema.TimePeriod.CloseDayEnum, Option[Schema.TimePeriod.CloseDayEnum]] = (fun: Schema.TimePeriod.CloseDayEnum) => Option(fun)
		given putListSchemaSpecialHourPeriod: Conversion[List[Schema.SpecialHourPeriod], Option[List[Schema.SpecialHourPeriod]]] = (fun: List[Schema.SpecialHourPeriod]) => Option(fun)
		given putSchemaDate: Conversion[Schema.Date, Option[Schema.Date]] = (fun: Schema.Date) => Option(fun)
		given putSchemaServiceAreaBusinessBusinessTypeEnum: Conversion[Schema.ServiceAreaBusiness.BusinessTypeEnum, Option[Schema.ServiceAreaBusiness.BusinessTypeEnum]] = (fun: Schema.ServiceAreaBusiness.BusinessTypeEnum) => Option(fun)
		given putSchemaPlaces: Conversion[Schema.Places, Option[Schema.Places]] = (fun: Schema.Places) => Option(fun)
		given putListSchemaPlaceInfo: Conversion[List[Schema.PlaceInfo], Option[List[Schema.PlaceInfo]]] = (fun: List[Schema.PlaceInfo]) => Option(fun)
		given putBigDecimal: Conversion[BigDecimal, Option[BigDecimal]] = (fun: BigDecimal) => Option(fun)
		given putSchemaOpenInfoStatusEnum: Conversion[Schema.OpenInfo.StatusEnum, Option[Schema.OpenInfo.StatusEnum]] = (fun: Schema.OpenInfo.StatusEnum) => Option(fun)
		given putSchemaRelevantLocation: Conversion[Schema.RelevantLocation, Option[Schema.RelevantLocation]] = (fun: Schema.RelevantLocation) => Option(fun)
		given putListSchemaRelevantLocation: Conversion[List[Schema.RelevantLocation], Option[List[Schema.RelevantLocation]]] = (fun: List[Schema.RelevantLocation]) => Option(fun)
		given putSchemaRelevantLocationRelationTypeEnum: Conversion[Schema.RelevantLocation.RelationTypeEnum, Option[Schema.RelevantLocation.RelationTypeEnum]] = (fun: Schema.RelevantLocation.RelationTypeEnum) => Option(fun)
		given putSchemaStructuredServiceItem: Conversion[Schema.StructuredServiceItem, Option[Schema.StructuredServiceItem]] = (fun: Schema.StructuredServiceItem) => Option(fun)
		given putSchemaFreeFormServiceItem: Conversion[Schema.FreeFormServiceItem, Option[Schema.FreeFormServiceItem]] = (fun: Schema.FreeFormServiceItem) => Option(fun)
		given putSchemaMoney: Conversion[Schema.Money, Option[Schema.Money]] = (fun: Schema.Money) => Option(fun)
		given putSchemaLabel: Conversion[Schema.Label, Option[Schema.Label]] = (fun: Schema.Label) => Option(fun)
		given putListSchemaGoogleLocation: Conversion[List[Schema.GoogleLocation], Option[List[Schema.GoogleLocation]]] = (fun: List[Schema.GoogleLocation]) => Option(fun)
		given putListSchemaLocation: Conversion[List[Schema.Location], Option[List[Schema.Location]]] = (fun: List[Schema.Location]) => Option(fun)
	}
	object OptionDefault {
		given getString: Conversion[Option[String], String] = (fun: Option[String]) => fun.getOrElse("")
	}
}
