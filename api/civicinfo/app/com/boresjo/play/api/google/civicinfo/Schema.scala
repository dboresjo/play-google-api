package com.boresjo.play.api.google.civicinfo

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	case class VoterInfoResponse(
	  /** The election that was queried. */
		election: Option[Schema.Election] = None,
	  /** When there are multiple elections for a voter address, the otherElections field is populated in the API response and there are two possibilities: 1. If the earliest election is not the intended election, specify the election ID of the desired election in a second API request using the electionId field. 2. If these elections occur on the same day, the API doesn?t return any polling location, contest, or election official information to ensure that an additional query is made. For user-facing applications, we recommend displaying these elections to the user to disambiguate. A second API request using the electionId field should be made for the election that is relevant to the user. */
		otherElections: Option[List[Schema.Election]] = None,
	  /** The normalized version of the requested address */
		normalizedInput: Option[Schema.SimpleAddressType] = None,
	  /** Locations where the voter is eligible to vote on election day. */
		pollingLocations: Option[List[Schema.PollingLocation]] = None,
	  /** Locations where the voter is eligible to vote early, prior to election day. */
		earlyVoteSites: Option[List[Schema.PollingLocation]] = None,
	  /** Locations where a voter is eligible to drop off a completed ballot. The voter must have received and completed a ballot prior to arriving at the location. The location may not have ballots available on the premises. These locations could be open on or before election day as indicated in the pollingHours field. */
		dropOffLocations: Option[List[Schema.PollingLocation]] = None,
	  /** Contests that will appear on the voter's ballot. */
		contests: Option[List[Schema.Contest]] = None,
	  /** Local Election Information for the state that the voter votes in. For the US, there will only be one element in this array. */
		state: Option[List[Schema.AdministrationRegion]] = None,
		precinctId: Option[String] = None,
	  /** Specifies whether voters in the precinct vote only by mailing their ballots (with the possible option of dropping off their ballots as well). */
		mailOnly: Option[Boolean] = None,
	  /** The precincts that match this voter's address. Will only be returned for project IDs which have been allowlisted as "partner projects". */
		precincts: Option[List[Schema.Precinct]] = None,
	  /** Identifies what kind of resource this is. Value: the fixed string "civicinfo#voterInfoResponse". */
		kind: Option[String] = Some("""civicinfo#voterInfoResponse""")
	)
	
	object Election {
		enum ShapeLookupBehaviorEnum extends Enum[ShapeLookupBehaviorEnum] { case shapeLookupDefault, shapeLookupDisabled, shapeLookupEnabled }
	}
	case class Election(
	  /** The unique ID of this election. */
		id: Option[String] = None,
	  /** A displayable name for the election. */
		name: Option[String] = None,
	  /** Day of the election in YYYY-MM-DD format. */
		electionDay: Option[String] = None,
	  /** The political division of the election. Represented as an OCD Division ID. Voters within these political jurisdictions are covered by this election. This is typically a state such as ocd-division/country:us/state:ca or for the midterms or general election the entire US (i.e. ocd-division/country:us). */
		ocdDivisionId: Option[String] = None,
		shapeLookupBehavior: Option[Schema.Election.ShapeLookupBehaviorEnum] = None
	)
	
	case class SimpleAddressType(
	  /** The name of the location. */
		locationName: Option[String] = None,
	  /** The street name and number of this address. */
		line1: Option[String] = None,
	  /** The second line the address, if needed. */
		line2: Option[String] = None,
	  /** The third line of the address, if needed. */
		line3: Option[String] = None,
	  /** The city or town for the address. */
		city: Option[String] = None,
	  /** The US two letter state abbreviation of the address. */
		state: Option[String] = None,
	  /** The US Postal Zip Code of the address. */
		zip: Option[String] = None
	)
	
	case class PollingLocation(
	  /** The address of the location. */
		address: Option[Schema.SimpleAddressType] = None,
	  /** Notes about this location (e.g. accessibility ramp or entrance to use). */
		notes: Option[String] = None,
	  /** A description of when this location is open. */
		pollingHours: Option[String] = None,
	  /** Latitude of the location, in degrees north of the equator. Note this field may not be available for some locations. */
		latitude: Option[BigDecimal] = None,
	  /** Longitude of the location, in degrees east of the Prime Meridian. Note this field may not be available for some locations. */
		longitude: Option[BigDecimal] = None,
	  /** The name of the early vote site or drop off location. This field is not populated for polling locations. */
		name: Option[String] = None,
	  /** The services provided by this early vote site or drop off location. This field is not populated for polling locations. */
		voterServices: Option[String] = None,
	  /** The first date that this early vote site or drop off location may be used. This field is not populated for polling locations. */
		startDate: Option[String] = None,
	  /** The last date that this early vote site or drop off location may be used. This field is not populated for polling locations. */
		endDate: Option[String] = None,
	  /** A list of sources for this location. If multiple sources are listed the data has been aggregated from those sources. */
		sources: Option[List[Schema.Source]] = None
	)
	
	case class Source(
	  /** The name of the data source. */
		name: Option[String] = None,
	  /** Whether this data comes from an official government source. */
		official: Option[Boolean] = None
	)
	
	object Contest {
		enum LevelEnum extends Enum[LevelEnum] { case international, country, administrativeArea1, regional, administrativeArea2, locality, subLocality1, subLocality2, special }
		enum RolesEnum extends Enum[RolesEnum] { case headOfState, headOfGovernment, deputyHeadOfGovernment, governmentOfficer, executiveCouncil, legislatorUpperBody, legislatorLowerBody, highestCourtJudge, judge, schoolBoard, specialPurposeOfficer, otherRole }
	}
	case class Contest(
	  /** The type of contest. Usually this will be 'General', 'Primary', or 'Run-off' for contests with candidates. For referenda this will be 'Referendum'. For Retention contests this will typically be 'Retention'. */
		`type`: Option[String] = None,
	  /** If this is a partisan election, the name of the party/parties it is for. */
		primaryParties: Option[List[String]] = None,
	  /** A description of any additional eligibility requirements for voting in this contest. */
		electorateSpecifications: Option[String] = None,
	  /** "Yes" or "No" depending on whether this a contest being held outside the normal election cycle. */
		special: Option[String] = None,
	  /** The official title on the ballot for this contest, only where available. */
		ballotTitle: Option[String] = None,
	  /** The name of the office for this contest. */
		office: Option[String] = None,
	  /** The levels of government of the office for this contest. There may be more than one in cases where a jurisdiction effectively acts at two different levels of government; for example, the mayor of the District of Columbia acts at "locality" level, but also effectively at both "administrative-area-2" and "administrative-area-1". */
		level: Option[List[Schema.Contest.LevelEnum]] = None,
	  /** The roles which this office fulfills. */
		roles: Option[List[Schema.Contest.RolesEnum]] = None,
	  /** Information about the electoral district that this contest is in. */
		district: Option[Schema.ElectoralDistrict] = None,
	  /** The number of candidates that will be elected to office in this contest. */
		numberElected: Option[String] = None,
	  /** The number of candidates that a voter may vote for in this contest. */
		numberVotingFor: Option[String] = None,
	  /** A number specifying the position of this contest on the voter's ballot. */
		ballotPlacement: Option[String] = None,
	  /** The title of the referendum (e.g. 'Proposition 42'). This field is only populated for contests of type 'Referendum'. */
		referendumTitle: Option[String] = None,
	  /** A brief description of the referendum. This field is only populated for contests of type 'Referendum'. */
		referendumSubtitle: Option[String] = None,
	  /** A link to the referendum. This field is only populated for contests of type 'Referendum'. */
		referendumUrl: Option[String] = None,
	  /** Specifies a short summary of the referendum that is typically on the ballot below the title but above the text. This field is only populated for contests of type 'Referendum'. */
		referendumBrief: Option[String] = None,
	  /** The full text of the referendum. This field is only populated for contests of type 'Referendum'. */
		referendumText: Option[String] = None,
	  /** A statement in favor of the referendum. It does not necessarily appear on the ballot. This field is only populated for contests of type 'Referendum'. */
		referendumProStatement: Option[String] = None,
	  /** A statement in opposition to the referendum. It does not necessarily appear on the ballot. This field is only populated for contests of type 'Referendum'. */
		referendumConStatement: Option[String] = None,
	  /** The threshold of votes that the referendum needs in order to pass, e.g. "two-thirds". This field is only populated for contests of type 'Referendum'. */
		referendumPassageThreshold: Option[String] = None,
	  /** Specifies what effect abstaining (not voting) on the proposition will have (i.e. whether abstaining is considered a vote against it). This field is only populated for contests of type 'Referendum'. */
		referendumEffectOfAbstain: Option[String] = None,
	  /** The set of ballot responses for the referendum. A ballot response represents a line on the ballot. Common examples might include "yes" or "no" for referenda. This field is only populated for contests of type 'Referendum'. */
		referendumBallotResponses: Option[List[String]] = None,
	  /** A list of sources for this contest. If multiple sources are listed, the data has been aggregated from those sources. */
		sources: Option[List[Schema.Source]] = None,
	  /** The candidate choices for this contest. */
		candidates: Option[List[Schema.Candidate]] = None
	)
	
	object ElectoralDistrict {
		enum ScopeEnum extends Enum[ScopeEnum] { case statewide, congressional, stateUpper, stateLower, countywide, judicial, schoolBoard, citywide, special, countyCouncil, township, ward, cityCouncil, national }
	}
	case class ElectoralDistrict(
	  /** The name of the district. */
		name: Option[String] = None,
	  /** The geographic scope of this district. If unspecified the district's geography is not known. One of: national, statewide, congressional, stateUpper, stateLower, countywide, judicial, schoolBoard, cityWide, township, countyCouncil, cityCouncil, ward, special */
		scope: Option[Schema.ElectoralDistrict.ScopeEnum] = None,
	  /** An identifier for this district, relative to its scope. For example, the 34th State Senate district would have id "34" and a scope of stateUpper. */
		id: Option[String] = None
	)
	
	case class Candidate(
	  /** The candidate's name. If this is a joint ticket it will indicate the name of the candidate at the top of a ticket followed by a / and that name of candidate at the bottom of the ticket. e.g. "Mitt Romney / Paul Ryan" */
		name: Option[String] = None,
	  /** The full name of the party the candidate is a member of. */
		party: Option[String] = None,
	  /** The URL for the candidate's campaign web site. */
		candidateUrl: Option[String] = None,
	  /** The voice phone number for the candidate's campaign office. */
		phone: Option[String] = None,
	  /** A URL for a photo of the candidate. */
		photoUrl: Option[String] = None,
	  /** The email address for the candidate's campaign. */
		email: Option[String] = None,
	  /** The order the candidate appears on the ballot for this contest. */
		orderOnBallot: Option[String] = None,
	  /** A list of known (social) media channels for this candidate. */
		channels: Option[List[Schema.Channel]] = None
	)
	
	case class Channel(
	  /** The type of channel. The following is a list of types of channels, but is not exhaustive. More channel types may be added at a later time. One of: GooglePlus, YouTube, Facebook, Twitter */
		`type`: Option[String] = None,
	  /** The unique public identifier for the candidate's channel. */
		id: Option[String] = None
	)
	
	case class AdministrationRegion(
	  /** The name of the jurisdiction. */
		name: Option[String] = None,
	  /** The election administration body for this area. */
		electionAdministrationBody: Option[Schema.AdministrativeBody] = None,
	  /** The city or county that provides election information for this voter. This object can have the same elements as state. */
		local_jurisdiction: Option[Schema.AdministrationRegion] = None,
	  /** A list of sources for this area. If multiple sources are listed the data has been aggregated from those sources. */
		sources: Option[List[Schema.Source]] = None
	)
	
	case class AdministrativeBody(
	  /** The name of this election administrative body. */
		name: Option[String] = None,
	  /** A URL provided by this administrative body for looking up general election information. */
		electionInfoUrl: Option[String] = None,
	  /** A URL provided by this administrative body for looking up how to register to vote. */
		electionRegistrationUrl: Option[String] = None,
	  /** A URL provided by this administrative body for confirming that the voter is registered to vote. */
		electionRegistrationConfirmationUrl: Option[String] = None,
	  /** A URL provided by this administrative body for information on absentee voting. */
		absenteeVotingInfoUrl: Option[String] = None,
	  /** A URL provided by this administrative body for looking up where to vote. */
		votingLocationFinderUrl: Option[String] = None,
	  /** A URL provided by this administrative body to give contest information to the voter. */
		ballotInfoUrl: Option[String] = None,
	  /** A URL provided by this administrative body describing election rules to the voter. */
		electionRulesUrl: Option[String] = None,
	  /** A description of the services this administrative body may provide. */
		voter_services: Option[List[String]] = None,
	  /** A description of the hours of operation for this administrative body. */
		hoursOfOperation: Option[String] = None,
	  /** The mailing address of this administrative body. */
		correspondenceAddress: Option[Schema.SimpleAddressType] = None,
	  /** The physical address of this administrative body. */
		physicalAddress: Option[Schema.SimpleAddressType] = None,
	  /** A last minute or emergency notification text provided by this administrative body. */
		electionNoticeText: Option[String] = None,
	  /** A URL provided by this administrative body for additional information related to the last minute or emergency notification. */
		electionNoticeUrl: Option[String] = None,
	  /** The election officials for this election administrative body. */
		electionOfficials: Option[List[Schema.ElectionOfficial]] = None
	)
	
	case class ElectionOfficial(
	  /** The full name of the election official. */
		name: Option[String] = None,
	  /** The title of the election official. */
		title: Option[String] = None,
	  /** The office phone number of the election official. */
		officePhoneNumber: Option[String] = None,
	  /** The fax number of the election official. */
		faxNumber: Option[String] = None,
	  /** The email address of the election official. */
		emailAddress: Option[String] = None
	)
	
	case class Precinct(
	  /** Required. Dataset ID. What datasets our Precincts come from. */
		datasetId: Option[String] = None,
	  /** Encouraged. The OCD ID of the precinct */
		ocdId: Option[List[String]] = None,
	  /** Required. A unique identifier for this precinct. */
		id: Option[String] = None,
	  /** Required. The name of the precinct. */
		name: Option[String] = None,
	  /** The number of the precinct. */
		number: Option[String] = None,
	  /** If present, this proto corresponds to one portion of split precinct. Other portions of this precinct are guaranteed to have the same `name`. If not present, this proto represents a full precicnt. */
		splitName: Option[String] = None,
	  /** Specifies if the precinct runs mail-only elections. */
		mailOnly: Option[Boolean] = None,
	  /** Specifies the ward the precinct is contained within. */
		ward: Option[String] = None,
	  /** ID(s) of the Contest message(s) for this precinct. */
		contestId: Option[List[String]] = None,
	  /** ID of the AdministrationRegion message for this precinct. Corresponds to LocalityId xml tag. */
		administrationRegionId: Option[String] = None,
	  /** ID(s) of the ElectoralDistrict message(s) for this precinct. */
		electoralDistrictId: Option[List[String]] = None,
	  /** ID(s) of the PollingLocation message(s) for this precinct. */
		pollingLocationId: Option[List[String]] = None,
	  /** ID(s) of the PollingLocation message(s) for this precinct. */
		earlyVoteSiteId: Option[List[String]] = None,
	  /** ID(s) of the SpatialBoundary message(s) for this precinct. Used to specify a geometrical boundary of the precinct. */
		spatialBoundaryId: Option[List[String]] = None
	)
	
	case class ElectionsQueryResponse(
	  /** A list of available elections */
		elections: Option[List[Schema.Election]] = None,
	  /** Identifies what kind of resource this is. Value: the fixed string "civicinfo#electionsQueryResponse". */
		kind: Option[String] = Some("""civicinfo#electionsQueryResponse""")
	)
	
	case class RepresentativeInfoData(
	  /** A map of political geographic divisions that contain the requested address, keyed by the unique Open Civic Data identifier for this division. */
		divisions: Option[Map[String, Schema.GeographicDivision]] = None,
	  /** Elected offices referenced by the divisions listed above. Will only be present if includeOffices was true in the request. */
		offices: Option[List[Schema.Office]] = None,
	  /** Officials holding the offices listed above. Will only be present if includeOffices was true in the request. */
		officials: Option[List[Schema.Official]] = None
	)
	
	case class GeographicDivision(
	  /** The name of the division. */
		name: Option[String] = None,
	  /** Any other valid OCD IDs that refer to the same division.\n\nBecause OCD IDs are meant to be human-readable and at least somewhat predictable, there are occasionally several identifiers for a single division. These identifiers are defined to be equivalent to one another, and one is always indicated as the primary identifier. The primary identifier will be returned in ocd_id above, and any other equivalent valid identifiers will be returned in this list.\n\nFor example, if this division's OCD ID is ocd-division/country:us/district:dc, this will contain ocd-division/country:us/state:dc. */
		alsoKnownAs: Option[List[String]] = None,
	  /** List of indices in the offices array, one for each office elected from this division. Will only be present if includeOffices was true (or absent) in the request. */
		officeIndices: Option[List[Int]] = None
	)
	
	object Office {
		enum LevelsEnum extends Enum[LevelsEnum] { case international, country, administrativeArea1, regional, administrativeArea2, locality, subLocality1, subLocality2, special }
		enum RolesEnum extends Enum[RolesEnum] { case headOfState, headOfGovernment, deputyHeadOfGovernment, governmentOfficer, executiveCouncil, legislatorUpperBody, legislatorLowerBody, highestCourtJudge, judge, schoolBoard, specialPurposeOfficer, otherRole }
	}
	case class Office(
	  /** The human-readable name of the office. */
		name: Option[String] = None,
	  /** The OCD ID of the division with which this office is associated. */
		divisionId: Option[String] = None,
	  /** The levels of government of which this office is part. There may be more than one in cases where a jurisdiction effectively acts at two different levels of government; for example, the mayor of the District of Columbia acts at "locality" level, but also effectively at both "administrative-area-2" and "administrative-area-1". */
		levels: Option[List[Schema.Office.LevelsEnum]] = None,
	  /** The roles which this office fulfills. Roles are not meant to be exhaustive, or to exactly specify the entire set of responsibilities of a given office, but are meant to be rough categories that are useful for general selection from or sorting of a list of offices. */
		roles: Option[List[Schema.Office.RolesEnum]] = None,
	  /** A list of sources for this office. If multiple sources are listed, the data has been aggregated from those sources. */
		sources: Option[List[Schema.Source]] = None,
	  /** List of indices in the officials array of people who presently hold this office. */
		officialIndices: Option[List[Int]] = None
	)
	
	case class Official(
	  /** The official's name. */
		name: Option[String] = None,
	  /** Addresses at which to contact the official. */
		address: Option[List[Schema.SimpleAddressType]] = None,
	  /** The full name of the party the official belongs to. */
		party: Option[String] = None,
	  /** The official's public contact phone numbers. */
		phones: Option[List[String]] = None,
	  /** The official's public website URLs. */
		urls: Option[List[String]] = None,
	  /** A URL for a photo of the official. */
		photoUrl: Option[String] = None,
	  /** The direct email addresses for the official. */
		emails: Option[List[String]] = None,
	  /** A list of known (social) media channels for this official. */
		channels: Option[List[Schema.Channel]] = None
	)
	
	case class RepresentativeInfoResponse(
	  /** The normalized version of the requested address */
		normalizedInput: Option[Schema.SimpleAddressType] = None,
	  /** Identifies what kind of resource this is. Value: the fixed string "civicinfo#representativeInfoResponse". */
		kind: Option[String] = Some("""civicinfo#representativeInfoResponse"""),
	  /** A map of political geographic divisions that contain the requested address, keyed by the unique Open Civic Data identifier for this division. */
		divisions: Option[Map[String, Schema.GeographicDivision]] = None,
	  /** Elected offices referenced by the divisions listed above. Will only be present if includeOffices was true in the request. */
		offices: Option[List[Schema.Office]] = None,
	  /** Officials holding the offices listed above. Will only be present if includeOffices was true in the request. */
		officials: Option[List[Schema.Official]] = None
	)
	
	case class DivisionSearchResponse(
		results: Option[List[Schema.DivisionSearchResult]] = None,
	  /** Identifies what kind of resource this is. Value: the fixed string "civicinfo#divisionSearchResponse". */
		kind: Option[String] = Some("""civicinfo#divisionSearchResponse""")
	)
	
	case class DivisionSearchResult(
	  /** The unique Open Civic Data identifier for this division */
		ocdId: Option[String] = None,
	  /** The name of the division. */
		name: Option[String] = None,
	  /** Other Open Civic Data identifiers that refer to the same division -- for example, those that refer to other political divisions whose boundaries are defined to be coterminous with this one. For example, ocd-division/country:us/state:wy will include an alias of ocd-division/country:us/state:wy/cd:1, since Wyoming has only one Congressional district. */
		aliases: Option[List[String]] = None
	)
	
	case class DivisionByAddressResponse(
	  /** The normalized version of the requested address. */
		normalizedInput: Option[Schema.SimpleAddressType] = None,
		divisions: Option[Map[String, Schema.GeographicDivision]] = None
	)
}
