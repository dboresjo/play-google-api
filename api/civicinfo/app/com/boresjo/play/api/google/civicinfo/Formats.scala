package com.boresjo.play.api.google.civicinfo

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Formats {
	given fmtVoterInfoResponse: Format[Schema.VoterInfoResponse] = Json.format[Schema.VoterInfoResponse]
	given fmtElection: Format[Schema.Election] = Json.format[Schema.Election]
	given fmtSimpleAddressType: Format[Schema.SimpleAddressType] = Json.format[Schema.SimpleAddressType]
	given fmtPollingLocation: Format[Schema.PollingLocation] = Json.format[Schema.PollingLocation]
	given fmtContest: Format[Schema.Contest] = Json.format[Schema.Contest]
	given fmtAdministrationRegion: Format[Schema.AdministrationRegion] = Json.format[Schema.AdministrationRegion]
	given fmtPrecinct: Format[Schema.Precinct] = Json.format[Schema.Precinct]
	given fmtElectionShapeLookupBehaviorEnum: Format[Schema.Election.ShapeLookupBehaviorEnum] = JsonEnumFormat[Schema.Election.ShapeLookupBehaviorEnum]
	given fmtSource: Format[Schema.Source] = Json.format[Schema.Source]
	given fmtContestLevelEnum: Format[Schema.Contest.LevelEnum] = JsonEnumFormat[Schema.Contest.LevelEnum]
	given fmtContestRolesEnum: Format[Schema.Contest.RolesEnum] = JsonEnumFormat[Schema.Contest.RolesEnum]
	given fmtElectoralDistrict: Format[Schema.ElectoralDistrict] = Json.format[Schema.ElectoralDistrict]
	given fmtCandidate: Format[Schema.Candidate] = Json.format[Schema.Candidate]
	given fmtElectoralDistrictScopeEnum: Format[Schema.ElectoralDistrict.ScopeEnum] = JsonEnumFormat[Schema.ElectoralDistrict.ScopeEnum]
	given fmtChannel: Format[Schema.Channel] = Json.format[Schema.Channel]
	given fmtAdministrativeBody: Format[Schema.AdministrativeBody] = Json.format[Schema.AdministrativeBody]
	given fmtElectionOfficial: Format[Schema.ElectionOfficial] = Json.format[Schema.ElectionOfficial]
	given fmtElectionsQueryResponse: Format[Schema.ElectionsQueryResponse] = Json.format[Schema.ElectionsQueryResponse]
	given fmtRepresentativeInfoData: Format[Schema.RepresentativeInfoData] = Json.format[Schema.RepresentativeInfoData]
	given fmtGeographicDivision: Format[Schema.GeographicDivision] = Json.format[Schema.GeographicDivision]
	given fmtOffice: Format[Schema.Office] = Json.format[Schema.Office]
	given fmtOfficial: Format[Schema.Official] = Json.format[Schema.Official]
	given fmtOfficeLevelsEnum: Format[Schema.Office.LevelsEnum] = JsonEnumFormat[Schema.Office.LevelsEnum]
	given fmtOfficeRolesEnum: Format[Schema.Office.RolesEnum] = JsonEnumFormat[Schema.Office.RolesEnum]
	given fmtRepresentativeInfoResponse: Format[Schema.RepresentativeInfoResponse] = Json.format[Schema.RepresentativeInfoResponse]
	given fmtDivisionSearchResponse: Format[Schema.DivisionSearchResponse] = Json.format[Schema.DivisionSearchResponse]
	given fmtDivisionSearchResult: Format[Schema.DivisionSearchResult] = Json.format[Schema.DivisionSearchResult]
	given fmtDivisionByAddressResponse: Format[Schema.DivisionByAddressResponse] = Json.format[Schema.DivisionByAddressResponse]
}
