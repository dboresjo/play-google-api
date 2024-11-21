package com.boresjo.play.api.google.mybusinessaccountmanagement

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	case class AcceptInvitationRequest(
	
	)
	
	case class Empty(
	
	)
	
	case class DeclineInvitationRequest(
	
	)
	
	case class ListInvitationsResponse(
	  /** A collection of invitations that are pending for the account. The number of invitations listed here cannot exceed 1000. */
		invitations: Option[List[Schema.Invitation]] = None
	)
	
	object Invitation {
		enum RoleEnum extends Enum[RoleEnum] { case ADMIN_ROLE_UNSPECIFIED, PRIMARY_OWNER, OWNER, MANAGER, SITE_MANAGER }
		enum TargetTypeEnum extends Enum[TargetTypeEnum] { case TARGET_TYPE_UNSPECIFIED, ACCOUNTS_ONLY, LOCATIONS_ONLY }
	}
	case class Invitation(
	  /** Required. The resource name for the invitation. `accounts/{account_id}/invitations/{invitation_id}`. */
		name: Option[String] = None,
	  /** The sparsely populated account this invitation is for. */
		targetAccount: Option[Schema.Account] = None,
	  /** The target location this invitation is for. */
		targetLocation: Option[Schema.TargetLocation] = None,
	  /** Output only. The invited role on the account. */
		role: Option[Schema.Invitation.RoleEnum] = None,
	  /** Output only. Specifies which target types should appear in the response. */
		targetType: Option[Schema.Invitation.TargetTypeEnum] = None
	)
	
	object Account {
		enum TypeEnum extends Enum[TypeEnum] { case ACCOUNT_TYPE_UNSPECIFIED, PERSONAL, LOCATION_GROUP, USER_GROUP, ORGANIZATION }
		enum RoleEnum extends Enum[RoleEnum] { case ACCOUNT_ROLE_UNSPECIFIED, PRIMARY_OWNER, OWNER, MANAGER, SITE_MANAGER }
		enum VerificationStateEnum extends Enum[VerificationStateEnum] { case VERIFICATION_STATE_UNSPECIFIED, VERIFIED, UNVERIFIED, VERIFICATION_REQUESTED }
		enum VettedStateEnum extends Enum[VettedStateEnum] { case VETTED_STATE_UNSPECIFIED, NOT_VETTED, VETTED, INVALID }
		enum PermissionLevelEnum extends Enum[PermissionLevelEnum] { case PERMISSION_LEVEL_UNSPECIFIED, OWNER_LEVEL, MEMBER_LEVEL }
	}
	case class Account(
	  /** Immutable. The resource name, in the format `accounts/{account_id}`. */
		name: Option[String] = None,
	  /** Required. The name of the account. For an account of type `PERSONAL`, this is the first and last name of the user account. */
		accountName: Option[String] = None,
	  /** Required. Input only. The resource name of the account which will be the primary owner of the account being created. It should be of the form `accounts/{account_id}`. */
		primaryOwner: Option[String] = None,
	  /** Required. Contains the type of account. Accounts of type PERSONAL and ORGANIZATION cannot be created using this API. */
		`type`: Option[Schema.Account.TypeEnum] = None,
	  /** Output only. Specifies the AccountRole of this account. */
		role: Option[Schema.Account.RoleEnum] = None,
	  /** Output only. If verified, future locations that are created are automatically connected to Google Maps, and have Google+ pages created, without requiring moderation. */
		verificationState: Option[Schema.Account.VerificationStateEnum] = None,
	  /** Output only. Indicates whether the account is vetted by Google. A vetted account is able to verify locations via the VETTED_PARTNER method. */
		vettedState: Option[Schema.Account.VettedStateEnum] = None,
	  /** Output only. Account reference number if provisioned. */
		accountNumber: Option[String] = None,
	  /** Output only. Specifies the permission level the user has for this account. */
		permissionLevel: Option[Schema.Account.PermissionLevelEnum] = None,
	  /** Output only. Additional info for an organization. This is populated only for an organization account. */
		organizationInfo: Option[Schema.OrganizationInfo] = None
	)
	
	case class OrganizationInfo(
	  /** Output only. The registered domain for the account. */
		registeredDomain: Option[String] = None,
	  /** Output only. The postal address for the account. */
		address: Option[Schema.PostalAddress] = None,
	  /** Output only. The contact number for the organization. */
		phoneNumber: Option[String] = None
	)
	
	case class PostalAddress(
	  /** The schema revision of the `PostalAddress`. This must be set to 0, which is the latest revision. All new revisions &#42;&#42;must&#42;&#42; be backward compatible with old revisions. */
		revision: Option[Int] = None,
	  /** Required. CLDR region code of the country/region of the address. This is never inferred and it is up to the user to ensure the value is correct. See https://cldr.unicode.org/ and https://www.unicode.org/cldr/charts/30/supplemental/territory_information.html for details. Example: "CH" for Switzerland. */
		regionCode: Option[String] = None,
	  /** Optional. BCP-47 language code of the contents of this address (if known). This is often the UI language of the input form or is expected to match one of the languages used in the address' country/region, or their transliterated equivalents. This can affect formatting in certain countries, but is not critical to the correctness of the data and will never affect any validation or other non-formatting related operations. If this value is not known, it should be omitted (rather than specifying a possibly incorrect default). Examples: "zh-Hant", "ja", "ja-Latn", "en". */
		languageCode: Option[String] = None,
	  /** Optional. Postal code of the address. Not all countries use or require postal codes to be present, but where they are used, they may trigger additional validation with other parts of the address (e.g. state/zip validation in the U.S.A.). */
		postalCode: Option[String] = None,
	  /** Optional. Additional, country-specific, sorting code. This is not used in most regions. Where it is used, the value is either a string like "CEDEX", optionally followed by a number (e.g. "CEDEX 7"), or just a number alone, representing the "sector code" (Jamaica), "delivery area indicator" (Malawi) or "post office indicator" (e.g. Côte d'Ivoire). */
		sortingCode: Option[String] = None,
	  /** Optional. Highest administrative subdivision which is used for postal addresses of a country or region. For example, this can be a state, a province, an oblast, or a prefecture. Specifically, for Spain this is the province and not the autonomous community (e.g. "Barcelona" and not "Catalonia"). Many countries don't use an administrative area in postal addresses. E.g. in Switzerland this should be left unpopulated. */
		administrativeArea: Option[String] = None,
	  /** Optional. Generally refers to the city/town portion of the address. Examples: US city, IT comune, UK post town. In regions of the world where localities are not well defined or do not fit into this structure well, leave locality empty and use address_lines. */
		locality: Option[String] = None,
	  /** Optional. Sublocality of the address. For example, this can be neighborhoods, boroughs, districts. */
		sublocality: Option[String] = None,
	  /** Unstructured address lines describing the lower levels of an address. Because values in address_lines do not have type information and may sometimes contain multiple values in a single field (e.g. "Austin, TX"), it is important that the line order is clear. The order of address lines should be "envelope order" for the country/region of the address. In places where this can vary (e.g. Japan), address_language is used to make it explicit (e.g. "ja" for large-to-small ordering and "ja-Latn" or "en" for small-to-large). This way, the most specific line of an address can be selected based on the language. The minimum permitted structural representation of an address consists of a region_code with all remaining information placed in the address_lines. It would be possible to format such an address very approximately without geocoding, but no semantic reasoning could be made about any of the address components until it was at least partially resolved. Creating an address only containing a region_code and address_lines, and then geocoding is the recommended way to handle completely unstructured addresses (as opposed to guessing which parts of the address should be localities or administrative areas). */
		addressLines: Option[List[String]] = None,
	  /** Optional. The recipient at the address. This field may, under certain circumstances, contain multiline information. For example, it might contain "care of" information. */
		recipients: Option[List[String]] = None,
	  /** Optional. The name of the organization at the address. */
		organization: Option[String] = None
	)
	
	case class TargetLocation(
	  /** The name of the location to which the user is invited. */
		locationName: Option[String] = None,
	  /** The address of the location to which the user is invited. */
		address: Option[String] = None
	)
	
	case class ListAccountAdminsResponse(
	  /** A collection of Admin instances. */
		accountAdmins: Option[List[Schema.Admin]] = None
	)
	
	object Admin {
		enum RoleEnum extends Enum[RoleEnum] { case ADMIN_ROLE_UNSPECIFIED, PRIMARY_OWNER, OWNER, MANAGER, SITE_MANAGER }
	}
	case class Admin(
	  /** Immutable. The resource name. For account admins, this is in the form: `accounts/{account_id}/admins/{admin_id}` For location admins, this is in the form: `locations/{location_id}/admins/{admin_id}` This field will be ignored if set during admin creation. */
		name: Option[String] = None,
	  /** Optional. The name of the admin. When making the initial invitation, this is the invitee's email address. On `GET` calls, the user's email address is returned if the invitation is still pending. Otherwise, it contains the user's first and last names. This field is only needed to be set during admin creation. */
		admin: Option[String] = None,
	  /** Immutable. The name of the Account resource that this Admin refers to. Used when calling locations.admins.create to invite a LocationGroup as an admin. If both this field and `admin` are set on `CREATE` requests, this field takes precedence and the email address in `admin` will be ignored. Format: `accounts/{account}`. */
		account: Option[String] = None,
	  /** Required. Specifies the role that this admin uses with the specified Account or Location. */
		role: Option[Schema.Admin.RoleEnum] = None,
	  /** Output only. Indicates whether this admin has a pending invitation for the specified resource. */
		pendingInvitation: Option[Boolean] = None
	)
	
	case class ListLocationAdminsResponse(
	  /** A collection of Admins. */
		admins: Option[List[Schema.Admin]] = None
	)
	
	case class TransferLocationRequest(
	  /** Required. Name of the account resource to transfer the location to (for example, "accounts/{account}"). */
		destinationAccount: Option[String] = None
	)
	
	case class ListAccountsResponse(
	  /** A collection of accounts to which the user has access. The personal account of the user doing the query will always be the first item of the result, unless it is filtered out. */
		accounts: Option[List[Schema.Account]] = None,
	  /** If the number of accounts exceeds the requested page size, this field is populated with a token to fetch the next page of accounts on a subsequent call to `accounts.list`. If there are no more accounts, this field is not present in the response. */
		nextPageToken: Option[String] = None
	)
}
