package com.boresjo.play.api.google.civicinfo

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Helpers {
	import Schema.*
	object AutoOption {
		given putSchemaElection: Conversion[Schema.Election, Option[Schema.Election]] = (fun: Schema.Election) => Option(fun)
		given putListSchemaElection: Conversion[List[Schema.Election], Option[List[Schema.Election]]] = (fun: List[Schema.Election]) => Option(fun)
		given putSchemaSimpleAddressType: Conversion[Schema.SimpleAddressType, Option[Schema.SimpleAddressType]] = (fun: Schema.SimpleAddressType) => Option(fun)
		given putListSchemaPollingLocation: Conversion[List[Schema.PollingLocation], Option[List[Schema.PollingLocation]]] = (fun: List[Schema.PollingLocation]) => Option(fun)
		given putListSchemaContest: Conversion[List[Schema.Contest], Option[List[Schema.Contest]]] = (fun: List[Schema.Contest]) => Option(fun)
		given putListSchemaAdministrationRegion: Conversion[List[Schema.AdministrationRegion], Option[List[Schema.AdministrationRegion]]] = (fun: List[Schema.AdministrationRegion]) => Option(fun)
		given putString: Conversion[String, Option[String]] = (fun: String) => Option(fun)
		given putBoolean: Conversion[Boolean, Option[Boolean]] = (fun: Boolean) => Option(fun)
		given putListSchemaPrecinct: Conversion[List[Schema.Precinct], Option[List[Schema.Precinct]]] = (fun: List[Schema.Precinct]) => Option(fun)
		given putSchemaElectionShapeLookupBehaviorEnum: Conversion[Schema.Election.ShapeLookupBehaviorEnum, Option[Schema.Election.ShapeLookupBehaviorEnum]] = (fun: Schema.Election.ShapeLookupBehaviorEnum) => Option(fun)
		given putBigDecimal: Conversion[BigDecimal, Option[BigDecimal]] = (fun: BigDecimal) => Option(fun)
		given putListSchemaSource: Conversion[List[Schema.Source], Option[List[Schema.Source]]] = (fun: List[Schema.Source]) => Option(fun)
		given putListString: Conversion[List[String], Option[List[String]]] = (fun: List[String]) => Option(fun)
		given putListSchemaContestLevelEnum: Conversion[List[Schema.Contest.LevelEnum], Option[List[Schema.Contest.LevelEnum]]] = (fun: List[Schema.Contest.LevelEnum]) => Option(fun)
		given putListSchemaContestRolesEnum: Conversion[List[Schema.Contest.RolesEnum], Option[List[Schema.Contest.RolesEnum]]] = (fun: List[Schema.Contest.RolesEnum]) => Option(fun)
		given putSchemaElectoralDistrict: Conversion[Schema.ElectoralDistrict, Option[Schema.ElectoralDistrict]] = (fun: Schema.ElectoralDistrict) => Option(fun)
		given putListSchemaCandidate: Conversion[List[Schema.Candidate], Option[List[Schema.Candidate]]] = (fun: List[Schema.Candidate]) => Option(fun)
		given putSchemaElectoralDistrictScopeEnum: Conversion[Schema.ElectoralDistrict.ScopeEnum, Option[Schema.ElectoralDistrict.ScopeEnum]] = (fun: Schema.ElectoralDistrict.ScopeEnum) => Option(fun)
		given putListSchemaChannel: Conversion[List[Schema.Channel], Option[List[Schema.Channel]]] = (fun: List[Schema.Channel]) => Option(fun)
		given putSchemaAdministrativeBody: Conversion[Schema.AdministrativeBody, Option[Schema.AdministrativeBody]] = (fun: Schema.AdministrativeBody) => Option(fun)
		given putSchemaAdministrationRegion: Conversion[Schema.AdministrationRegion, Option[Schema.AdministrationRegion]] = (fun: Schema.AdministrationRegion) => Option(fun)
		given putListSchemaElectionOfficial: Conversion[List[Schema.ElectionOfficial], Option[List[Schema.ElectionOfficial]]] = (fun: List[Schema.ElectionOfficial]) => Option(fun)
		given putMapStringSchemaGeographicDivision: Conversion[Map[String, Schema.GeographicDivision], Option[Map[String, Schema.GeographicDivision]]] = (fun: Map[String, Schema.GeographicDivision]) => Option(fun)
		given putListSchemaOffice: Conversion[List[Schema.Office], Option[List[Schema.Office]]] = (fun: List[Schema.Office]) => Option(fun)
		given putListSchemaOfficial: Conversion[List[Schema.Official], Option[List[Schema.Official]]] = (fun: List[Schema.Official]) => Option(fun)
		given putListInt: Conversion[List[Int], Option[List[Int]]] = (fun: List[Int]) => Option(fun)
		given putListSchemaOfficeLevelsEnum: Conversion[List[Schema.Office.LevelsEnum], Option[List[Schema.Office.LevelsEnum]]] = (fun: List[Schema.Office.LevelsEnum]) => Option(fun)
		given putListSchemaOfficeRolesEnum: Conversion[List[Schema.Office.RolesEnum], Option[List[Schema.Office.RolesEnum]]] = (fun: List[Schema.Office.RolesEnum]) => Option(fun)
		given putListSchemaSimpleAddressType: Conversion[List[Schema.SimpleAddressType], Option[List[Schema.SimpleAddressType]]] = (fun: List[Schema.SimpleAddressType]) => Option(fun)
		given putListSchemaDivisionSearchResult: Conversion[List[Schema.DivisionSearchResult], Option[List[Schema.DivisionSearchResult]]] = (fun: List[Schema.DivisionSearchResult]) => Option(fun)
	}
	object OptionDefault {
		given getString: Conversion[Option[String], String] = (fun: Option[String]) => fun.getOrElse("")
	}
}
