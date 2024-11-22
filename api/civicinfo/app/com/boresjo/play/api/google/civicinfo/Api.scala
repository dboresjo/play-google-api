package com.boresjo.play.api.google.civicinfo

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://civicinfo.googleapis.com/"

	object elections {
		class voterInfoQuery(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.VoterInfoResponse]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.VoterInfoResponse])
		}
		object voterInfoQuery {
			def apply(address: String, electionId: String, officialOnly: Boolean, returnAllAvailableData: Boolean, productionDataOnly: Boolean)(using auth: AuthToken, ec: ExecutionContext): voterInfoQuery = new voterInfoQuery(ws.url(BASE_URL + s"civicinfo/v2/voterinfo").addQueryStringParameters("address" -> address.toString, "electionId" -> electionId.toString, "officialOnly" -> officialOnly.toString, "returnAllAvailableData" -> returnAllAvailableData.toString, "productionDataOnly" -> productionDataOnly.toString))
			given Conversion[voterInfoQuery, Future[Schema.VoterInfoResponse]] = (fun: voterInfoQuery) => fun.apply()
		}
		class electionQuery(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ElectionsQueryResponse]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ElectionsQueryResponse])
		}
		object electionQuery {
			def apply(productionDataOnly: Boolean)(using auth: AuthToken, ec: ExecutionContext): electionQuery = new electionQuery(ws.url(BASE_URL + s"civicinfo/v2/elections").addQueryStringParameters("productionDataOnly" -> productionDataOnly.toString))
			given Conversion[electionQuery, Future[Schema.ElectionsQueryResponse]] = (fun: electionQuery) => fun.apply()
		}
	}
	object representatives {
		class representativeInfoByDivision(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.RepresentativeInfoData]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.RepresentativeInfoData])
		}
		object representativeInfoByDivision {
			def apply(ocdId: String, recursive: Boolean, roles: String, levels: String)(using auth: AuthToken, ec: ExecutionContext): representativeInfoByDivision = new representativeInfoByDivision(ws.url(BASE_URL + s"civicinfo/v2/representatives/${ocdId}").addQueryStringParameters("recursive" -> recursive.toString, "roles" -> roles.toString, "levels" -> levels.toString))
			given Conversion[representativeInfoByDivision, Future[Schema.RepresentativeInfoData]] = (fun: representativeInfoByDivision) => fun.apply()
		}
		class representativeInfoByAddress(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.RepresentativeInfoResponse]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.RepresentativeInfoResponse])
		}
		object representativeInfoByAddress {
			def apply(address: String, roles: String, levels: String, includeOffices: Boolean)(using auth: AuthToken, ec: ExecutionContext): representativeInfoByAddress = new representativeInfoByAddress(ws.url(BASE_URL + s"civicinfo/v2/representatives").addQueryStringParameters("address" -> address.toString, "roles" -> roles.toString, "levels" -> levels.toString, "includeOffices" -> includeOffices.toString))
			given Conversion[representativeInfoByAddress, Future[Schema.RepresentativeInfoResponse]] = (fun: representativeInfoByAddress) => fun.apply()
		}
	}
	object divisions {
		class search(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.DivisionSearchResponse]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.DivisionSearchResponse])
		}
		object search {
			def apply(query: String)(using auth: AuthToken, ec: ExecutionContext): search = new search(ws.url(BASE_URL + s"civicinfo/v2/divisions").addQueryStringParameters("query" -> query.toString))
			given Conversion[search, Future[Schema.DivisionSearchResponse]] = (fun: search) => fun.apply()
		}
		class queryDivisionByAddress(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.DivisionByAddressResponse]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.DivisionByAddressResponse])
		}
		object queryDivisionByAddress {
			def apply(address: String)(using auth: AuthToken, ec: ExecutionContext): queryDivisionByAddress = new queryDivisionByAddress(ws.url(BASE_URL + s"civicinfo/v2/divisionsByAddress").addQueryStringParameters("address" -> address.toString))
			given Conversion[queryDivisionByAddress, Future[Schema.DivisionByAddressResponse]] = (fun: queryDivisionByAddress) => fun.apply()
		}
	}
}
