package com.boresjo.play.api.google.people

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Helpers {
	import Schema.*
	object AutoOption {
		given putString: Conversion[String, Option[String]] = (fun: String) => Option(fun)
		given putSchemaPersonMetadata: Conversion[Schema.PersonMetadata, Option[Schema.PersonMetadata]] = (fun: Schema.PersonMetadata) => Option(fun)
		given putListSchemaAddress: Conversion[List[Schema.Address], Option[List[Schema.Address]]] = (fun: List[Schema.Address]) => Option(fun)
		given putSchemaPersonAgeRangeEnum: Conversion[Schema.Person.AgeRangeEnum, Option[Schema.Person.AgeRangeEnum]] = (fun: Schema.Person.AgeRangeEnum) => Option(fun)
		given putListSchemaAgeRangeType: Conversion[List[Schema.AgeRangeType], Option[List[Schema.AgeRangeType]]] = (fun: List[Schema.AgeRangeType]) => Option(fun)
		given putListSchemaBiography: Conversion[List[Schema.Biography], Option[List[Schema.Biography]]] = (fun: List[Schema.Biography]) => Option(fun)
		given putListSchemaBirthday: Conversion[List[Schema.Birthday], Option[List[Schema.Birthday]]] = (fun: List[Schema.Birthday]) => Option(fun)
		given putListSchemaBraggingRights: Conversion[List[Schema.BraggingRights], Option[List[Schema.BraggingRights]]] = (fun: List[Schema.BraggingRights]) => Option(fun)
		given putListSchemaCalendarUrl: Conversion[List[Schema.CalendarUrl], Option[List[Schema.CalendarUrl]]] = (fun: List[Schema.CalendarUrl]) => Option(fun)
		given putListSchemaClientData: Conversion[List[Schema.ClientData], Option[List[Schema.ClientData]]] = (fun: List[Schema.ClientData]) => Option(fun)
		given putListSchemaCoverPhoto: Conversion[List[Schema.CoverPhoto], Option[List[Schema.CoverPhoto]]] = (fun: List[Schema.CoverPhoto]) => Option(fun)
		given putListSchemaEmailAddress: Conversion[List[Schema.EmailAddress], Option[List[Schema.EmailAddress]]] = (fun: List[Schema.EmailAddress]) => Option(fun)
		given putListSchemaEvent: Conversion[List[Schema.Event], Option[List[Schema.Event]]] = (fun: List[Schema.Event]) => Option(fun)
		given putListSchemaExternalId: Conversion[List[Schema.ExternalId], Option[List[Schema.ExternalId]]] = (fun: List[Schema.ExternalId]) => Option(fun)
		given putListSchemaFileAs: Conversion[List[Schema.FileAs], Option[List[Schema.FileAs]]] = (fun: List[Schema.FileAs]) => Option(fun)
		given putListSchemaGender: Conversion[List[Schema.Gender], Option[List[Schema.Gender]]] = (fun: List[Schema.Gender]) => Option(fun)
		given putListSchemaImClient: Conversion[List[Schema.ImClient], Option[List[Schema.ImClient]]] = (fun: List[Schema.ImClient]) => Option(fun)
		given putListSchemaInterest: Conversion[List[Schema.Interest], Option[List[Schema.Interest]]] = (fun: List[Schema.Interest]) => Option(fun)
		given putListSchemaLocale: Conversion[List[Schema.Locale], Option[List[Schema.Locale]]] = (fun: List[Schema.Locale]) => Option(fun)
		given putListSchemaLocation: Conversion[List[Schema.Location], Option[List[Schema.Location]]] = (fun: List[Schema.Location]) => Option(fun)
		given putListSchemaMembership: Conversion[List[Schema.Membership], Option[List[Schema.Membership]]] = (fun: List[Schema.Membership]) => Option(fun)
		given putListSchemaMiscKeyword: Conversion[List[Schema.MiscKeyword], Option[List[Schema.MiscKeyword]]] = (fun: List[Schema.MiscKeyword]) => Option(fun)
		given putListSchemaName: Conversion[List[Schema.Name], Option[List[Schema.Name]]] = (fun: List[Schema.Name]) => Option(fun)
		given putListSchemaNickname: Conversion[List[Schema.Nickname], Option[List[Schema.Nickname]]] = (fun: List[Schema.Nickname]) => Option(fun)
		given putListSchemaOccupation: Conversion[List[Schema.Occupation], Option[List[Schema.Occupation]]] = (fun: List[Schema.Occupation]) => Option(fun)
		given putListSchemaOrganization: Conversion[List[Schema.Organization], Option[List[Schema.Organization]]] = (fun: List[Schema.Organization]) => Option(fun)
		given putListSchemaPhoneNumber: Conversion[List[Schema.PhoneNumber], Option[List[Schema.PhoneNumber]]] = (fun: List[Schema.PhoneNumber]) => Option(fun)
		given putListSchemaPhoto: Conversion[List[Schema.Photo], Option[List[Schema.Photo]]] = (fun: List[Schema.Photo]) => Option(fun)
		given putListSchemaRelation: Conversion[List[Schema.Relation], Option[List[Schema.Relation]]] = (fun: List[Schema.Relation]) => Option(fun)
		given putListSchemaRelationshipInterest: Conversion[List[Schema.RelationshipInterest], Option[List[Schema.RelationshipInterest]]] = (fun: List[Schema.RelationshipInterest]) => Option(fun)
		given putListSchemaRelationshipStatus: Conversion[List[Schema.RelationshipStatus], Option[List[Schema.RelationshipStatus]]] = (fun: List[Schema.RelationshipStatus]) => Option(fun)
		given putListSchemaResidence: Conversion[List[Schema.Residence], Option[List[Schema.Residence]]] = (fun: List[Schema.Residence]) => Option(fun)
		given putListSchemaSipAddress: Conversion[List[Schema.SipAddress], Option[List[Schema.SipAddress]]] = (fun: List[Schema.SipAddress]) => Option(fun)
		given putListSchemaSkill: Conversion[List[Schema.Skill], Option[List[Schema.Skill]]] = (fun: List[Schema.Skill]) => Option(fun)
		given putListSchemaTagline: Conversion[List[Schema.Tagline], Option[List[Schema.Tagline]]] = (fun: List[Schema.Tagline]) => Option(fun)
		given putListSchemaUrl: Conversion[List[Schema.Url], Option[List[Schema.Url]]] = (fun: List[Schema.Url]) => Option(fun)
		given putListSchemaUserDefined: Conversion[List[Schema.UserDefined], Option[List[Schema.UserDefined]]] = (fun: List[Schema.UserDefined]) => Option(fun)
		given putListSchemaSource: Conversion[List[Schema.Source], Option[List[Schema.Source]]] = (fun: List[Schema.Source]) => Option(fun)
		given putListString: Conversion[List[String], Option[List[String]]] = (fun: List[String]) => Option(fun)
		given putBoolean: Conversion[Boolean, Option[Boolean]] = (fun: Boolean) => Option(fun)
		given putSchemaPersonMetadataObjectTypeEnum: Conversion[Schema.PersonMetadata.ObjectTypeEnum, Option[Schema.PersonMetadata.ObjectTypeEnum]] = (fun: Schema.PersonMetadata.ObjectTypeEnum) => Option(fun)
		given putSchemaSourceTypeEnum: Conversion[Schema.Source.TypeEnum, Option[Schema.Source.TypeEnum]] = (fun: Schema.Source.TypeEnum) => Option(fun)
		given putSchemaProfileMetadata: Conversion[Schema.ProfileMetadata, Option[Schema.ProfileMetadata]] = (fun: Schema.ProfileMetadata) => Option(fun)
		given putSchemaProfileMetadataObjectTypeEnum: Conversion[Schema.ProfileMetadata.ObjectTypeEnum, Option[Schema.ProfileMetadata.ObjectTypeEnum]] = (fun: Schema.ProfileMetadata.ObjectTypeEnum) => Option(fun)
		given putListSchemaProfileMetadataUserTypesEnum: Conversion[List[Schema.ProfileMetadata.UserTypesEnum], Option[List[Schema.ProfileMetadata.UserTypesEnum]]] = (fun: List[Schema.ProfileMetadata.UserTypesEnum]) => Option(fun)
		given putSchemaFieldMetadata: Conversion[Schema.FieldMetadata, Option[Schema.FieldMetadata]] = (fun: Schema.FieldMetadata) => Option(fun)
		given putSchemaSource: Conversion[Schema.Source, Option[Schema.Source]] = (fun: Schema.Source) => Option(fun)
		given putSchemaAgeRangeTypeAgeRangeEnum: Conversion[Schema.AgeRangeType.AgeRangeEnum, Option[Schema.AgeRangeType.AgeRangeEnum]] = (fun: Schema.AgeRangeType.AgeRangeEnum) => Option(fun)
		given putSchemaBiographyContentTypeEnum: Conversion[Schema.Biography.ContentTypeEnum, Option[Schema.Biography.ContentTypeEnum]] = (fun: Schema.Biography.ContentTypeEnum) => Option(fun)
		given putSchemaDate: Conversion[Schema.Date, Option[Schema.Date]] = (fun: Schema.Date) => Option(fun)
		given putInt: Conversion[Int, Option[Int]] = (fun: Int) => Option(fun)
		given putSchemaContactGroupMembership: Conversion[Schema.ContactGroupMembership, Option[Schema.ContactGroupMembership]] = (fun: Schema.ContactGroupMembership) => Option(fun)
		given putSchemaDomainMembership: Conversion[Schema.DomainMembership, Option[Schema.DomainMembership]] = (fun: Schema.DomainMembership) => Option(fun)
		given putSchemaMiscKeywordTypeEnum: Conversion[Schema.MiscKeyword.TypeEnum, Option[Schema.MiscKeyword.TypeEnum]] = (fun: Schema.MiscKeyword.TypeEnum) => Option(fun)
		given putSchemaNicknameTypeEnum: Conversion[Schema.Nickname.TypeEnum, Option[Schema.Nickname.TypeEnum]] = (fun: Schema.Nickname.TypeEnum) => Option(fun)
		given putSchemaPerson: Conversion[Schema.Person, Option[Schema.Person]] = (fun: Schema.Person) => Option(fun)
		given putListSchemaUpdateContactPhotoRequestSourcesEnum: Conversion[List[Schema.UpdateContactPhotoRequest.SourcesEnum], Option[List[Schema.UpdateContactPhotoRequest.SourcesEnum]]] = (fun: List[Schema.UpdateContactPhotoRequest.SourcesEnum]) => Option(fun)
		given putListSchemaSearchResult: Conversion[List[Schema.SearchResult], Option[List[Schema.SearchResult]]] = (fun: List[Schema.SearchResult]) => Option(fun)
		given putListSchemaContactToCreate: Conversion[List[Schema.ContactToCreate], Option[List[Schema.ContactToCreate]]] = (fun: List[Schema.ContactToCreate]) => Option(fun)
		given putListSchemaBatchCreateContactsRequestSourcesEnum: Conversion[List[Schema.BatchCreateContactsRequest.SourcesEnum], Option[List[Schema.BatchCreateContactsRequest.SourcesEnum]]] = (fun: List[Schema.BatchCreateContactsRequest.SourcesEnum]) => Option(fun)
		given putListSchemaPersonResponse: Conversion[List[Schema.PersonResponse], Option[List[Schema.PersonResponse]]] = (fun: List[Schema.PersonResponse]) => Option(fun)
		given putSchemaStatus: Conversion[Schema.Status, Option[Schema.Status]] = (fun: Schema.Status) => Option(fun)
		given putListMapStringJsValue: Conversion[List[Map[String, JsValue]], Option[List[Map[String, JsValue]]]] = (fun: List[Map[String, JsValue]]) => Option(fun)
		given putMapStringSchemaPerson: Conversion[Map[String, Schema.Person], Option[Map[String, Schema.Person]]] = (fun: Map[String, Schema.Person]) => Option(fun)
		given putListSchemaBatchUpdateContactsRequestSourcesEnum: Conversion[List[Schema.BatchUpdateContactsRequest.SourcesEnum], Option[List[Schema.BatchUpdateContactsRequest.SourcesEnum]]] = (fun: List[Schema.BatchUpdateContactsRequest.SourcesEnum]) => Option(fun)
		given putMapStringSchemaPersonResponse: Conversion[Map[String, Schema.PersonResponse], Option[Map[String, Schema.PersonResponse]]] = (fun: Map[String, Schema.PersonResponse]) => Option(fun)
		given putListSchemaPerson: Conversion[List[Schema.Person], Option[List[Schema.Person]]] = (fun: List[Schema.Person]) => Option(fun)
		given putListSchemaCopyOtherContactToMyContactsGroupRequestSourcesEnum: Conversion[List[Schema.CopyOtherContactToMyContactsGroupRequest.SourcesEnum], Option[List[Schema.CopyOtherContactToMyContactsGroupRequest.SourcesEnum]]] = (fun: List[Schema.CopyOtherContactToMyContactsGroupRequest.SourcesEnum]) => Option(fun)
		given putListSchemaContactGroupResponse: Conversion[List[Schema.ContactGroupResponse], Option[List[Schema.ContactGroupResponse]]] = (fun: List[Schema.ContactGroupResponse]) => Option(fun)
		given putSchemaContactGroup: Conversion[Schema.ContactGroup, Option[Schema.ContactGroup]] = (fun: Schema.ContactGroup) => Option(fun)
		given putSchemaContactGroupMetadata: Conversion[Schema.ContactGroupMetadata, Option[Schema.ContactGroupMetadata]] = (fun: Schema.ContactGroupMetadata) => Option(fun)
		given putSchemaContactGroupGroupTypeEnum: Conversion[Schema.ContactGroup.GroupTypeEnum, Option[Schema.ContactGroup.GroupTypeEnum]] = (fun: Schema.ContactGroup.GroupTypeEnum) => Option(fun)
		given putListSchemaGroupClientData: Conversion[List[Schema.GroupClientData], Option[List[Schema.GroupClientData]]] = (fun: List[Schema.GroupClientData]) => Option(fun)
		given putListSchemaContactGroup: Conversion[List[Schema.ContactGroup], Option[List[Schema.ContactGroup]]] = (fun: List[Schema.ContactGroup]) => Option(fun)
	}
	object OptionDefault {
		given getString: Conversion[Option[String], String] = (fun: Option[String]) => fun.getOrElse("")
	}
}
