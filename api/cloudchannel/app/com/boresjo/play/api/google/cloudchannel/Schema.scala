package com.boresjo.play.api.google.cloudchannel

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	case class GoogleLongrunningListOperationsResponse(
	  /** A list of operations that matches the specified filter in the request. */
		operations: Option[List[Schema.GoogleLongrunningOperation]] = None,
	  /** The standard List next-page token. */
		nextPageToken: Option[String] = None
	)
	
	case class GoogleLongrunningOperation(
	  /** The server-assigned name, which is only unique within the same service that originally returns it. If you use the default HTTP mapping, the `name` should be a resource name ending with `operations/{unique_id}`. */
		name: Option[String] = None,
	  /** Service-specific metadata associated with the operation. It typically contains progress information and common metadata such as create time. Some services might not provide such metadata. Any method that returns a long-running operation should document the metadata type, if any. */
		metadata: Option[Map[String, JsValue]] = None,
	  /** If the value is `false`, it means the operation is still in progress. If `true`, the operation is completed, and either `error` or `response` is available. */
		done: Option[Boolean] = None,
	  /** The error result of the operation in case of failure or cancellation. */
		error: Option[Schema.GoogleRpcStatus] = None,
	  /** The normal, successful response of the operation. If the original method returns no data on success, such as `Delete`, the response is `google.protobuf.Empty`. If the original method is standard `Get`/`Create`/`Update`, the response should be the resource. For other methods, the response should have the type `XxxResponse`, where `Xxx` is the original method name. For example, if the original method name is `TakeSnapshot()`, the inferred response type is `TakeSnapshotResponse`. */
		response: Option[Map[String, JsValue]] = None
	)
	
	case class GoogleRpcStatus(
	  /** The status code, which should be an enum value of google.rpc.Code. */
		code: Option[Int] = None,
	  /** A developer-facing error message, which should be in English. Any user-facing error message should be localized and sent in the google.rpc.Status.details field, or localized by the client. */
		message: Option[String] = None,
	  /** A list of messages that carry the error details. There is a common set of message types for APIs to use. */
		details: Option[List[Map[String, JsValue]]] = None
	)
	
	case class GoogleProtobufEmpty(
	
	)
	
	case class GoogleLongrunningCancelOperationRequest(
	
	)
	
	case class GoogleCloudChannelV1RunReportJobRequest(
	  /** Optional. The range of usage or invoice dates to include in the result. */
		dateRange: Option[Schema.GoogleCloudChannelV1DateRange] = None,
	  /** Optional. A structured string that defines conditions on dimension columns to restrict the report output. Filters support logical operators (AND, OR, NOT) and conditional operators (=, !=, <, >, <=, and >=) using `column_id` as keys. For example: `(customer:"accounts/C123abc/customers/S456def" OR customer:"accounts/C123abc/customers/S789ghi") AND invoice_start_date.year >= 2022` */
		filter: Option[String] = None,
	  /** Optional. The BCP-47 language code, such as "en-US". If specified, the response is localized to the corresponding language code if the original data sources support it. Default is "en-US". */
		languageCode: Option[String] = None
	)
	
	case class GoogleCloudChannelV1DateRange(
	  /** The earliest usage date time (inclusive). If you use time groupings (daily, weekly, etc), each group uses midnight to midnight (Pacific time). The usage start date is rounded down to include all usage from the specified date. We recommend that clients pass `usage_start_date_time` in Pacific time. */
		usageStartDateTime: Option[Schema.GoogleTypeDateTime] = None,
	  /** The latest usage date time (exclusive). If you use time groupings (daily, weekly, etc), each group uses midnight to midnight (Pacific time). The usage end date is rounded down to include all usage from the specified date. We recommend that clients pass `usage_start_date_time` in Pacific time. */
		usageEndDateTime: Option[Schema.GoogleTypeDateTime] = None,
	  /** The earliest invoice date (inclusive). If this value is not the first day of a month, this will move it back to the first day of the given month. */
		invoiceStartDate: Option[Schema.GoogleTypeDate] = None,
	  /** The latest invoice date (inclusive). If this value is not the last day of a month, this will move it forward to the last day of the given month. */
		invoiceEndDate: Option[Schema.GoogleTypeDate] = None
	)
	
	case class GoogleTypeDateTime(
	  /** Optional. Year of date. Must be from 1 to 9999, or 0 if specifying a datetime without a year. */
		year: Option[Int] = None,
	  /** Optional. Month of year. Must be from 1 to 12, or 0 if specifying a datetime without a month. */
		month: Option[Int] = None,
	  /** Optional. Day of month. Must be from 1 to 31 and valid for the year and month, or 0 if specifying a datetime without a day. */
		day: Option[Int] = None,
	  /** Optional. Hours of day in 24 hour format. Should be from 0 to 23, defaults to 0 (midnight). An API may choose to allow the value "24:00:00" for scenarios like business closing time. */
		hours: Option[Int] = None,
	  /** Optional. Minutes of hour of day. Must be from 0 to 59, defaults to 0. */
		minutes: Option[Int] = None,
	  /** Optional. Seconds of minutes of the time. Must normally be from 0 to 59, defaults to 0. An API may allow the value 60 if it allows leap-seconds. */
		seconds: Option[Int] = None,
	  /** Optional. Fractions of seconds in nanoseconds. Must be from 0 to 999,999,999, defaults to 0. */
		nanos: Option[Int] = None,
	  /** UTC offset. Must be whole seconds, between -18 hours and +18 hours. For example, a UTC offset of -4:00 would be represented as { seconds: -14400 }. */
		utcOffset: Option[String] = None,
	  /** Time zone. */
		timeZone: Option[Schema.GoogleTypeTimeZone] = None
	)
	
	case class GoogleTypeTimeZone(
	  /** IANA Time Zone Database time zone, e.g. "America/New_York". */
		id: Option[String] = None,
	  /** Optional. IANA Time Zone Database version number, e.g. "2019a". */
		version: Option[String] = None
	)
	
	case class GoogleTypeDate(
	  /** Year of the date. Must be from 1 to 9999, or 0 to specify a date without a year. */
		year: Option[Int] = None,
	  /** Month of a year. Must be from 1 to 12, or 0 to specify a year without a month and day. */
		month: Option[Int] = None,
	  /** Day of a month. Must be from 1 to 31 and valid for the year and month, or 0 to specify a year by itself or a year and month where the day isn't significant. */
		day: Option[Int] = None
	)
	
	case class GoogleCloudChannelV1FetchReportResultsRequest(
	  /** Optional. Requested page size of the report. The server may return fewer results than requested. If you don't specify a page size, the server uses a sensible default (may change over time). The maximum value is 30,000; the server will change larger values to 30,000. */
		pageSize: Option[Int] = None,
	  /** Optional. A token that specifies a page of results beyond the first page. Obtained through FetchReportResultsResponse.next_page_token of the previous CloudChannelReportsService.FetchReportResults call. */
		pageToken: Option[String] = None,
	  /** Optional. List of keys specifying which report partitions to return. If empty, returns all partitions. */
		partitionKeys: Option[List[String]] = None
	)
	
	case class GoogleCloudChannelV1FetchReportResultsResponse(
	  /** The metadata for the report results (display name, columns, row count, and date ranges). */
		reportMetadata: Option[Schema.GoogleCloudChannelV1ReportResultsMetadata] = None,
	  /** The report's lists of values. Each row follows the settings and ordering of the columns from `report_metadata`. */
		rows: Option[List[Schema.GoogleCloudChannelV1Row]] = None,
	  /** Pass this token to FetchReportResultsRequest.page_token to retrieve the next page of results. */
		nextPageToken: Option[String] = None
	)
	
	case class GoogleCloudChannelV1ReportResultsMetadata(
	  /** Details of the completed report. */
		report: Option[Schema.GoogleCloudChannelV1Report] = None,
	  /** The total number of rows of data in the final report. */
		rowCount: Option[String] = None,
	  /** The date range of reported usage. */
		dateRange: Option[Schema.GoogleCloudChannelV1DateRange] = None,
	  /** The usage dates immediately preceding `date_range` with the same duration. Use this to calculate trending usage and costs. This is only populated if you request trending data. For example, if `date_range` is July 1-15, `preceding_date_range` will be June 16-30. */
		precedingDateRange: Option[Schema.GoogleCloudChannelV1DateRange] = None
	)
	
	case class GoogleCloudChannelV1Report(
	  /** Required. The report's resource name. Specifies the account and report used to generate report data. The report_id identifier is a UID (for example, `613bf59q`). Name uses the format: accounts/{account_id}/reports/{report_id} */
		name: Option[String] = None,
	  /** A human-readable name for this report. */
		displayName: Option[String] = None,
	  /** The list of columns included in the report. This defines the schema of the report results. */
		columns: Option[List[Schema.GoogleCloudChannelV1Column]] = None,
	  /** A description of other aspects of the report, such as the products it supports. */
		description: Option[String] = None
	)
	
	object GoogleCloudChannelV1Column {
		enum DataTypeEnum extends Enum[DataTypeEnum] { case DATA_TYPE_UNSPECIFIED, STRING, INT, DECIMAL, MONEY, DATE, DATE_TIME }
	}
	case class GoogleCloudChannelV1Column(
	  /** The unique name of the column (for example, customer_domain, channel_partner, customer_cost). You can use column IDs in RunReportJobRequest.filter. To see all reports and their columns, call CloudChannelReportsService.ListReports. */
		columnId: Option[String] = None,
	  /** The column's display name. */
		displayName: Option[String] = None,
	  /** The type of the values for this column. */
		dataType: Option[Schema.GoogleCloudChannelV1Column.DataTypeEnum] = None
	)
	
	case class GoogleCloudChannelV1Row(
	  /** The list of values in the row. */
		values: Option[List[Schema.GoogleCloudChannelV1ReportValue]] = None,
	  /** The key for the partition this row belongs to. This field is empty if the report is not partitioned. */
		partitionKey: Option[String] = None
	)
	
	case class GoogleCloudChannelV1ReportValue(
	  /** A value of type `string`. */
		stringValue: Option[String] = None,
	  /** A value of type `int`. */
		intValue: Option[String] = None,
	  /** A value of type `google.type.Decimal`, representing non-integer numeric values. */
		decimalValue: Option[Schema.GoogleTypeDecimal] = None,
	  /** A value of type `google.type.Money` (currency code, whole units, decimal units). */
		moneyValue: Option[Schema.GoogleTypeMoney] = None,
	  /** A value of type `google.type.Date` (year, month, day). */
		dateValue: Option[Schema.GoogleTypeDate] = None,
	  /** A value of type `google.type.DateTime` (year, month, day, hour, minute, second, and UTC offset or timezone.) */
		dateTimeValue: Option[Schema.GoogleTypeDateTime] = None
	)
	
	case class GoogleTypeDecimal(
	  /** The decimal value, as a string. The string representation consists of an optional sign, `+` (`U+002B`) or `-` (`U+002D`), followed by a sequence of zero or more decimal digits ("the integer"), optionally followed by a fraction, optionally followed by an exponent. An empty string &#42;&#42;should&#42;&#42; be interpreted as `0`. The fraction consists of a decimal point followed by zero or more decimal digits. The string must contain at least one digit in either the integer or the fraction. The number formed by the sign, the integer and the fraction is referred to as the significand. The exponent consists of the character `e` (`U+0065`) or `E` (`U+0045`) followed by one or more decimal digits. Services &#42;&#42;should&#42;&#42; normalize decimal values before storing them by: - Removing an explicitly-provided `+` sign (`+2.5` -> `2.5`). - Replacing a zero-length integer value with `0` (`.5` -> `0.5`). - Coercing the exponent character to upper-case, with explicit sign (`2.5e8` -> `2.5E+8`). - Removing an explicitly-provided zero exponent (`2.5E0` -> `2.5`). Services &#42;&#42;may&#42;&#42; perform additional normalization based on its own needs and the internal decimal implementation selected, such as shifting the decimal point and exponent value together (example: `2.5E-1` <-> `0.25`). Additionally, services &#42;&#42;may&#42;&#42; preserve trailing zeroes in the fraction to indicate increased precision, but are not required to do so. Note that only the `.` character is supported to divide the integer and the fraction; `,` &#42;&#42;should not&#42;&#42; be supported regardless of locale. Additionally, thousand separators &#42;&#42;should not&#42;&#42; be supported. If a service does support them, values &#42;&#42;must&#42;&#42; be normalized. The ENBF grammar is: DecimalString = '' | [Sign] Significand [Exponent]; Sign = '+' | '-'; Significand = Digits '.' | [Digits] '.' Digits; Exponent = ('e' | 'E') [Sign] Digits; Digits = { '0' | '1' | '2' | '3' | '4' | '5' | '6' | '7' | '8' | '9' }; Services &#42;&#42;should&#42;&#42; clearly document the range of supported values, the maximum supported precision (total number of digits), and, if applicable, the scale (number of digits after the decimal point), as well as how it behaves when receiving out-of-bounds values. Services &#42;&#42;may&#42;&#42; choose to accept values passed as input even when the value has a higher precision or scale than the service supports, and &#42;&#42;should&#42;&#42; round the value to fit the supported scale. Alternatively, the service &#42;&#42;may&#42;&#42; error with `400 Bad Request` (`INVALID_ARGUMENT` in gRPC) if precision would be lost. Services &#42;&#42;should&#42;&#42; error with `400 Bad Request` (`INVALID_ARGUMENT` in gRPC) if the service receives a value outside of the supported range. */
		value: Option[String] = None
	)
	
	case class GoogleTypeMoney(
	  /** The three-letter currency code defined in ISO 4217. */
		currencyCode: Option[String] = None,
	  /** The whole units of the amount. For example if `currencyCode` is `"USD"`, then 1 unit is one US dollar. */
		units: Option[String] = None,
	  /** Number of nano (10^-9) units of the amount. The value must be between -999,999,999 and +999,999,999 inclusive. If `units` is positive, `nanos` must be positive or zero. If `units` is zero, `nanos` can be positive, zero, or negative. If `units` is negative, `nanos` must be negative or zero. For example $-1.75 is represented as `units`=-1 and `nanos`=-750,000,000. */
		nanos: Option[Int] = None
	)
	
	case class GoogleCloudChannelV1ListReportsResponse(
	  /** The reports available to the partner. */
		reports: Option[List[Schema.GoogleCloudChannelV1Report]] = None,
	  /** Pass this token to FetchReportResultsRequest.page_token to retrieve the next page of results. */
		nextPageToken: Option[String] = None
	)
	
	case class GoogleCloudChannelV1ListCustomersResponse(
	  /** The customers belonging to a reseller or distributor. */
		customers: Option[List[Schema.GoogleCloudChannelV1Customer]] = None,
	  /** A token to retrieve the next page of results. Pass to ListCustomersRequest.page_token to obtain that page. */
		nextPageToken: Option[String] = None
	)
	
	case class GoogleCloudChannelV1Customer(
	  /** Output only. Resource name of the customer. Format: accounts/{account_id}/customers/{customer_id} */
		name: Option[String] = None,
	  /** Required. Name of the organization that the customer entity represents. */
		orgDisplayName: Option[String] = None,
	  /** Required. The organization address for the customer. To enforce US laws and embargoes, we require a region, postal code, and address lines. You must provide valid addresses for every customer. To set the customer's language, use the Customer-level language code. */
		orgPostalAddress: Option[Schema.GoogleTypePostalAddress] = None,
	  /** Primary contact info. */
		primaryContactInfo: Option[Schema.GoogleCloudChannelV1ContactInfo] = None,
	  /** Secondary contact email. You need to provide an alternate email to create different domains if a primary contact email already exists. Users will receive a notification with credentials when you create an admin.google.com account. Secondary emails are also recovery email addresses. Alternate emails are optional when you create Team customers. */
		alternateEmail: Option[String] = None,
	  /** Required. The customer's primary domain. Must match the primary contact email's domain. */
		domain: Option[String] = None,
	  /** Output only. Time when the customer was created. */
		createTime: Option[String] = None,
	  /** Output only. Time when the customer was updated. */
		updateTime: Option[String] = None,
	  /** Output only. The customer's Cloud Identity ID if the customer has a Cloud Identity resource. */
		cloudIdentityId: Option[String] = None,
	  /** Optional. The BCP-47 language code, such as "en-US" or "sr-Latn". For more information, see https://www.unicode.org/reports/tr35/#Unicode_locale_identifier. */
		languageCode: Option[String] = None,
	  /** Output only. Cloud Identity information for the customer. Populated only if a Cloud Identity account exists for this customer. */
		cloudIdentityInfo: Option[Schema.GoogleCloudChannelV1CloudIdentityInfo] = None,
	  /** Cloud Identity ID of the customer's channel partner. Populated only if a channel partner exists for this customer. */
		channelPartnerId: Option[String] = None,
	  /** Optional. External CRM ID for the customer. Populated only if a CRM ID exists for this customer. */
		correlationId: Option[String] = None
	)
	
	case class GoogleTypePostalAddress(
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
	
	case class GoogleCloudChannelV1ContactInfo(
	  /** The customer account contact's first name. Optional for Team customers. */
		firstName: Option[String] = None,
	  /** The customer account contact's last name. Optional for Team customers. */
		lastName: Option[String] = None,
	  /** Output only. The customer account contact's display name, formatted as a combination of the customer's first and last name. */
		displayName: Option[String] = None,
	  /** The customer account's contact email. Required for entitlements that create admin.google.com accounts, and serves as the customer's username for those accounts. Use this email to invite Team customers. */
		email: Option[String] = None,
	  /** Optional. The customer account contact's job title. */
		title: Option[String] = None,
	  /** The customer account's contact phone number. */
		phone: Option[String] = None
	)
	
	object GoogleCloudChannelV1CloudIdentityInfo {
		enum CustomerTypeEnum extends Enum[CustomerTypeEnum] { case CUSTOMER_TYPE_UNSPECIFIED, DOMAIN, TEAM }
	}
	case class GoogleCloudChannelV1CloudIdentityInfo(
	  /** CustomerType indicates verification type needed for using services. */
		customerType: Option[Schema.GoogleCloudChannelV1CloudIdentityInfo.CustomerTypeEnum] = None,
	  /** Output only. The primary domain name. */
		primaryDomain: Option[String] = None,
	  /** Output only. Whether the domain is verified. This field is not returned for a Customer's cloud_identity_info resource. Partners can use the domains.get() method of the Workspace SDK's Directory API, or listen to the PRIMARY_DOMAIN_VERIFIED Pub/Sub event in to track domain verification of their resolve Workspace customers. */
		isDomainVerified: Option[Boolean] = None,
	  /** The alternate email. */
		alternateEmail: Option[String] = None,
	  /** Phone number associated with the Cloud Identity. */
		phoneNumber: Option[String] = None,
	  /** Language code. */
		languageCode: Option[String] = None,
	  /** Output only. URI of Customer's Admin console dashboard. */
		adminConsoleUri: Option[String] = None,
	  /** Edu information about the customer. */
		eduData: Option[Schema.GoogleCloudChannelV1EduData] = None
	)
	
	object GoogleCloudChannelV1EduData {
		enum InstituteTypeEnum extends Enum[InstituteTypeEnum] { case INSTITUTE_TYPE_UNSPECIFIED, K12, UNIVERSITY }
		enum InstituteSizeEnum extends Enum[InstituteSizeEnum] { case INSTITUTE_SIZE_UNSPECIFIED, SIZE_1_100, SIZE_101_500, SIZE_501_1000, SIZE_1001_2000, SIZE_2001_5000, SIZE_5001_10000, SIZE_10001_OR_MORE }
	}
	case class GoogleCloudChannelV1EduData(
	  /** Designated institute type of customer. */
		instituteType: Option[Schema.GoogleCloudChannelV1EduData.InstituteTypeEnum] = None,
	  /** Size of the institute. */
		instituteSize: Option[Schema.GoogleCloudChannelV1EduData.InstituteSizeEnum] = None,
	  /** Web address for the edu customer's institution. */
		website: Option[String] = None
	)
	
	case class GoogleCloudChannelV1CheckCloudIdentityAccountsExistRequest(
	  /** Required. Domain to fetch for Cloud Identity account customers, including domain and team customers. For team customers, please use the domain for their emails. */
		domain: Option[String] = None,
	  /** Optional. Primary admin email to fetch for Cloud Identity account team customer. */
		primaryAdminEmail: Option[String] = None
	)
	
	case class GoogleCloudChannelV1CheckCloudIdentityAccountsExistResponse(
	  /** The Cloud Identity accounts associated with the domain. */
		cloudIdentityAccounts: Option[List[Schema.GoogleCloudChannelV1CloudIdentityCustomerAccount]] = None
	)
	
	object GoogleCloudChannelV1CloudIdentityCustomerAccount {
		enum CustomerTypeEnum extends Enum[CustomerTypeEnum] { case CUSTOMER_TYPE_UNSPECIFIED, DOMAIN, TEAM }
	}
	case class GoogleCloudChannelV1CloudIdentityCustomerAccount(
	  /** Returns true if a Cloud Identity account exists for a specific domain. */
		existing: Option[Boolean] = None,
	  /** Returns true if the Cloud Identity account is associated with a customer of the Channel Services partner (with active subscriptions or purchase consents). */
		owned: Option[Boolean] = None,
	  /** If owned = true, the name of the customer that owns the Cloud Identity account. Customer_name uses the format: accounts/{account_id}/customers/{customer_id} */
		customerName: Option[String] = None,
	  /** If existing = true, the Cloud Identity ID of the customer. */
		customerCloudIdentityId: Option[String] = None,
	  /** If existing = true, the type of the customer. */
		customerType: Option[Schema.GoogleCloudChannelV1CloudIdentityCustomerAccount.CustomerTypeEnum] = None,
	  /** If existing = true, and is 2-tier customer, the channel partner of the customer. */
		channelPartnerCloudIdentityId: Option[String] = None
	)
	
	case class GoogleCloudChannelV1ImportCustomerRequest(
	  /** Required. Customer domain. */
		domain: Option[String] = None,
	  /** Required. Customer's Cloud Identity ID */
		cloudIdentityId: Option[String] = None,
	  /** Required. Customer's primary admin email. */
		primaryAdminEmail: Option[String] = None,
	  /** Optional. The super admin of the resold customer generates this token to authorize a reseller to access their Cloud Identity and purchase entitlements on their behalf. You can omit this token after authorization. See https://support.google.com/a/answer/7643790 for more details. */
		authToken: Option[String] = None,
	  /** Required. Choose to overwrite an existing customer if found. This must be set to true if there is an existing customer with a conflicting region code or domain. */
		overwriteIfExists: Option[Boolean] = None,
	  /** Optional. Cloud Identity ID of a channel partner who will be the direct reseller for the customer's order. This field is required for 2-tier transfer scenarios and can be provided via the request Parent binding as well. */
		channelPartnerId: Option[String] = None,
	  /** Optional. Specifies the customer that will receive imported Cloud Identity information. Format: accounts/{account_id}/customers/{customer_id} */
		customer: Option[String] = None
	)
	
	case class GoogleCloudChannelV1ProvisionCloudIdentityRequest(
	  /** CloudIdentity-specific customer information. */
		cloudIdentityInfo: Option[Schema.GoogleCloudChannelV1CloudIdentityInfo] = None,
	  /** Admin user information. */
		user: Option[Schema.GoogleCloudChannelV1AdminUser] = None,
	  /** Validate the request and preview the review, but do not post it. */
		validateOnly: Option[Boolean] = None
	)
	
	case class GoogleCloudChannelV1AdminUser(
	  /** Primary email of the admin user. */
		email: Option[String] = None,
	  /** Given name of the admin user. */
		givenName: Option[String] = None,
	  /** Family name of the admin user. */
		familyName: Option[String] = None
	)
	
	case class GoogleCloudChannelV1ListEntitlementsResponse(
	  /** The reseller customer's entitlements. */
		entitlements: Option[List[Schema.GoogleCloudChannelV1Entitlement]] = None,
	  /** A token to list the next page of results. Pass to ListEntitlementsRequest.page_token to obtain that page. */
		nextPageToken: Option[String] = None
	)
	
	object GoogleCloudChannelV1Entitlement {
		enum ProvisioningStateEnum extends Enum[ProvisioningStateEnum] { case PROVISIONING_STATE_UNSPECIFIED, ACTIVE, SUSPENDED }
		enum SuspensionReasonsEnum extends Enum[SuspensionReasonsEnum] { case SUSPENSION_REASON_UNSPECIFIED, RESELLER_INITIATED, TRIAL_ENDED, RENEWAL_WITH_TYPE_CANCEL, PENDING_TOS_ACCEPTANCE, OTHER }
	}
	case class GoogleCloudChannelV1Entitlement(
	  /** Output only. Resource name of an entitlement in the form: accounts/{account_id}/customers/{customer_id}/entitlements/{entitlement_id}. */
		name: Option[String] = None,
	  /** Output only. The time at which the entitlement is created. */
		createTime: Option[String] = None,
	  /** Output only. The time at which the entitlement is updated. */
		updateTime: Option[String] = None,
	  /** Required. The offer resource name for which the entitlement is to be created. Takes the form: accounts/{account_id}/offers/{offer_id}. */
		offer: Option[String] = None,
	  /** Commitment settings for a commitment-based Offer. Required for commitment based offers. */
		commitmentSettings: Option[Schema.GoogleCloudChannelV1CommitmentSettings] = None,
	  /** Output only. Current provisioning state of the entitlement. */
		provisioningState: Option[Schema.GoogleCloudChannelV1Entitlement.ProvisioningStateEnum] = None,
	  /** Output only. Service provisioning details for the entitlement. */
		provisionedService: Option[Schema.GoogleCloudChannelV1ProvisionedService] = None,
	  /** Output only. Enumerable of all current suspension reasons for an entitlement. */
		suspensionReasons: Option[List[Schema.GoogleCloudChannelV1Entitlement.SuspensionReasonsEnum]] = None,
	  /** Optional. This purchase order (PO) information is for resellers to use for their company tracking usage. If a purchaseOrderId value is given, it appears in the API responses and shows up in the invoice. The property accepts up to 80 plain text characters. This is only supported for Google Workspace entitlements. */
		purchaseOrderId: Option[String] = None,
	  /** Output only. Settings for trial offers. */
		trialSettings: Option[Schema.GoogleCloudChannelV1TrialSettings] = None,
	  /** Association information to other entitlements. */
		associationInfo: Option[Schema.GoogleCloudChannelV1AssociationInfo] = None,
	  /** Extended entitlement parameters. When creating an entitlement, valid parameter names and values are defined in the Offer.parameter_definitions. For Google Workspace, the following Parameters may be accepted as input: - max_units: The maximum assignable units for a flexible offer OR - num_units: The total commitment for commitment-based offers The response may additionally include the following output-only Parameters: - assigned_units: The number of licenses assigned to users. For Google Cloud billing subaccounts, the following Parameter may be accepted as input: - display_name: The display name of the billing subaccount. */
		parameters: Option[List[Schema.GoogleCloudChannelV1Parameter]] = None,
	  /** Optional. The billing account resource name that is used to pay for this entitlement. */
		billingAccount: Option[String] = None,
	  /** Optional. Price reference ID for the offer. Optional field only for offers that require additional price information. Used to guarantee that the pricing is consistent between quoting the offer and placing the order. Yet to be implemented: this field is currently not evaluated in the API if populated in a request. */
		priceReferenceId: Option[String] = None
	)
	
	case class GoogleCloudChannelV1CommitmentSettings(
	  /** Output only. Commitment start timestamp. */
		startTime: Option[String] = None,
	  /** Output only. Commitment end timestamp. */
		endTime: Option[String] = None,
	  /** Optional. Renewal settings applicable for a commitment-based Offer. */
		renewalSettings: Option[Schema.GoogleCloudChannelV1RenewalSettings] = None
	)
	
	object GoogleCloudChannelV1RenewalSettings {
		enum PaymentPlanEnum extends Enum[PaymentPlanEnum] { case PAYMENT_PLAN_UNSPECIFIED, COMMITMENT, FLEXIBLE, FREE, TRIAL, OFFLINE }
	}
	case class GoogleCloudChannelV1RenewalSettings(
	  /** If false, the plan will be completed at the end date. */
		enableRenewal: Option[Boolean] = None,
	  /** If true and enable_renewal = true, the unit (for example seats or licenses) will be set to the number of active units at renewal time. */
		resizeUnitCount: Option[Boolean] = None,
	  /** Describes how a reseller will be billed. */
		paymentPlan: Option[Schema.GoogleCloudChannelV1RenewalSettings.PaymentPlanEnum] = None,
	  /** Describes how frequently the reseller will be billed, such as once per month. */
		paymentCycle: Option[Schema.GoogleCloudChannelV1Period] = None
	)
	
	object GoogleCloudChannelV1Period {
		enum PeriodTypeEnum extends Enum[PeriodTypeEnum] { case PERIOD_TYPE_UNSPECIFIED, DAY, MONTH, YEAR }
	}
	case class GoogleCloudChannelV1Period(
	  /** Total duration of Period Type defined. */
		duration: Option[Int] = None,
	  /** Period Type. */
		periodType: Option[Schema.GoogleCloudChannelV1Period.PeriodTypeEnum] = None
	)
	
	case class GoogleCloudChannelV1ProvisionedService(
	  /** Output only. Provisioning ID of the entitlement. For Google Workspace, this is the underlying Subscription ID. For Google Cloud, this is the Billing Account ID of the billing subaccount. */
		provisioningId: Option[String] = None,
	  /** Output only. The product pertaining to the provisioning resource as specified in the Offer. */
		productId: Option[String] = None,
	  /** Output only. The SKU pertaining to the provisioning resource as specified in the Offer. */
		skuId: Option[String] = None
	)
	
	case class GoogleCloudChannelV1TrialSettings(
	  /** Determines if the entitlement is in a trial or not: &#42; `true` - The entitlement is in trial. &#42; `false` - The entitlement is not in trial. */
		trial: Option[Boolean] = None,
	  /** Date when the trial ends. The value is in milliseconds using the UNIX Epoch format. See an example [Epoch converter](https://www.epochconverter.com). */
		endTime: Option[String] = None
	)
	
	case class GoogleCloudChannelV1AssociationInfo(
	  /** The name of the base entitlement, for which this entitlement is an add-on. */
		baseEntitlement: Option[String] = None
	)
	
	case class GoogleCloudChannelV1Parameter(
	  /** Name of the parameter. */
		name: Option[String] = None,
	  /** Value of the parameter. */
		value: Option[Schema.GoogleCloudChannelV1Value] = None,
	  /** Output only. Specifies whether this parameter is allowed to be changed. For example, for a Google Workspace Business Starter entitlement in commitment plan, num_units is editable when entitlement is active. */
		editable: Option[Boolean] = None
	)
	
	case class GoogleCloudChannelV1Value(
	  /** Represents an int64 value. */
		int64Value: Option[String] = None,
	  /** Represents a string value. */
		stringValue: Option[String] = None,
	  /** Represents a double value. */
		doubleValue: Option[BigDecimal] = None,
	  /** Represents an 'Any' proto value. */
		protoValue: Option[Map[String, JsValue]] = None,
	  /** Represents a boolean value. */
		boolValue: Option[Boolean] = None
	)
	
	case class GoogleCloudChannelV1ListTransferableSkusRequest(
	  /** Customer's Cloud Identity ID */
		cloudIdentityId: Option[String] = None,
	  /** A reseller is required to create a customer and use the resource name of the created customer here. Customer_name uses the format: accounts/{account_id}/customers/{customer_id} */
		customerName: Option[String] = None,
	  /** The requested page size. Server might return fewer results than requested. If unspecified, returns at most 100 SKUs. The maximum value is 1000; the server will coerce values above 1000. Optional. */
		pageSize: Option[Int] = None,
	  /** A token for a page of results other than the first page. Obtained using ListTransferableSkusResponse.next_page_token of the previous CloudChannelService.ListTransferableSkus call. Optional. */
		pageToken: Option[String] = None,
	  /** Optional. The super admin of the resold customer generates this token to authorize a reseller to access their Cloud Identity and purchase entitlements on their behalf. You can omit this token after authorization. See https://support.google.com/a/answer/7643790 for more details. */
		authToken: Option[String] = None,
	  /** The BCP-47 language code. For example, "en-US". The response will localize in the corresponding language code, if specified. The default value is "en-US". Optional. */
		languageCode: Option[String] = None
	)
	
	case class GoogleCloudChannelV1ListTransferableSkusResponse(
	  /** Information about existing SKUs for a customer that needs a transfer. */
		transferableSkus: Option[List[Schema.GoogleCloudChannelV1TransferableSku]] = None,
	  /** A token to retrieve the next page of results. Pass to ListTransferableSkusRequest.page_token to obtain that page. */
		nextPageToken: Option[String] = None
	)
	
	case class GoogleCloudChannelV1TransferableSku(
	  /** Describes the transfer eligibility of a SKU. */
		transferEligibility: Option[Schema.GoogleCloudChannelV1TransferEligibility] = None,
	  /** The SKU pertaining to the provisioning resource as specified in the Offer. */
		sku: Option[Schema.GoogleCloudChannelV1Sku] = None,
	  /** Optional. The customer to transfer has an entitlement with the populated legacy SKU. */
		legacySku: Option[Schema.GoogleCloudChannelV1Sku] = None
	)
	
	object GoogleCloudChannelV1TransferEligibility {
		enum IneligibilityReasonEnum extends Enum[IneligibilityReasonEnum] { case REASON_UNSPECIFIED, PENDING_TOS_ACCEPTANCE, SKU_NOT_ELIGIBLE, SKU_SUSPENDED, CHANNEL_PARTNER_NOT_AUTHORIZED_FOR_SKU }
	}
	case class GoogleCloudChannelV1TransferEligibility(
	  /** Whether reseller is eligible to transfer the SKU. */
		isEligible: Option[Boolean] = None,
	  /** Localized description if reseller is not eligible to transfer the SKU. */
		description: Option[String] = None,
	  /** Specified the reason for ineligibility. */
		ineligibilityReason: Option[Schema.GoogleCloudChannelV1TransferEligibility.IneligibilityReasonEnum] = None
	)
	
	case class GoogleCloudChannelV1Sku(
	  /** Resource Name of the SKU. Format: products/{product_id}/skus/{sku_id} */
		name: Option[String] = None,
	  /** Marketing information for the SKU. */
		marketingInfo: Option[Schema.GoogleCloudChannelV1MarketingInfo] = None,
	  /** Product the SKU is associated with. */
		product: Option[Schema.GoogleCloudChannelV1Product] = None
	)
	
	case class GoogleCloudChannelV1MarketingInfo(
	  /** Human readable name. */
		displayName: Option[String] = None,
	  /** Human readable description. Description can contain HTML. */
		description: Option[String] = None,
	  /** Default logo. */
		defaultLogo: Option[Schema.GoogleCloudChannelV1Media] = None
	)
	
	object GoogleCloudChannelV1Media {
		enum TypeEnum extends Enum[TypeEnum] { case MEDIA_TYPE_UNSPECIFIED, MEDIA_TYPE_IMAGE }
	}
	case class GoogleCloudChannelV1Media(
	  /** Title of the media. */
		title: Option[String] = None,
	  /** URL of the media. */
		content: Option[String] = None,
	  /** Type of the media. */
		`type`: Option[Schema.GoogleCloudChannelV1Media.TypeEnum] = None
	)
	
	case class GoogleCloudChannelV1Product(
	  /** Resource Name of the Product. Format: products/{product_id} */
		name: Option[String] = None,
	  /** Marketing information for the product. */
		marketingInfo: Option[Schema.GoogleCloudChannelV1MarketingInfo] = None
	)
	
	case class GoogleCloudChannelV1ListTransferableOffersRequest(
	  /** Customer's Cloud Identity ID */
		cloudIdentityId: Option[String] = None,
	  /** A reseller should create a customer and use the resource name of that customer here. */
		customerName: Option[String] = None,
	  /** Requested page size. Server might return fewer results than requested. If unspecified, returns at most 100 offers. The maximum value is 1000; the server will coerce values above 1000. */
		pageSize: Option[Int] = None,
	  /** A token for a page of results other than the first page. Obtained using ListTransferableOffersResponse.next_page_token of the previous CloudChannelService.ListTransferableOffers call. */
		pageToken: Option[String] = None,
	  /** Required. The SKU to look up Offers for. */
		sku: Option[String] = None,
	  /** Optional. The BCP-47 language code. For example, "en-US". The response will localize in the corresponding language code, if specified. The default value is "en-US". */
		languageCode: Option[String] = None,
	  /** Optional. The Billing Account to look up Offers for. Format: accounts/{account_id}/billingAccounts/{billing_account_id}. This field is only relevant for multi-currency accounts. It should be left empty for single currency accounts. */
		billingAccount: Option[String] = None
	)
	
	case class GoogleCloudChannelV1ListTransferableOffersResponse(
	  /** Information about Offers for a customer that can be used for transfer. */
		transferableOffers: Option[List[Schema.GoogleCloudChannelV1TransferableOffer]] = None,
	  /** A token to retrieve the next page of results. Pass to ListTransferableOffersRequest.page_token to obtain that page. */
		nextPageToken: Option[String] = None
	)
	
	case class GoogleCloudChannelV1TransferableOffer(
	  /** Offer with parameter constraints updated to allow the Transfer. */
		offer: Option[Schema.GoogleCloudChannelV1Offer] = None,
	  /** Optional. Price reference ID for the offer. Optional field only for offers that require additional price information. Used to guarantee that the pricing is consistent between quoting the offer and placing the order. */
		priceReferenceId: Option[String] = None
	)
	
	case class GoogleCloudChannelV1Offer(
	  /** Resource Name of the Offer. Format: accounts/{account_id}/offers/{offer_id} */
		name: Option[String] = None,
	  /** Marketing information for the Offer. */
		marketingInfo: Option[Schema.GoogleCloudChannelV1MarketingInfo] = None,
	  /** SKU the offer is associated with. */
		sku: Option[Schema.GoogleCloudChannelV1Sku] = None,
	  /** Describes the payment plan for the Offer. */
		plan: Option[Schema.GoogleCloudChannelV1Plan] = None,
	  /** Constraints on transacting the Offer. */
		constraints: Option[Schema.GoogleCloudChannelV1Constraints] = None,
	  /** Price for each monetizable resource type. */
		priceByResources: Option[List[Schema.GoogleCloudChannelV1PriceByResource]] = None,
	  /** Start of the Offer validity time. */
		startTime: Option[String] = None,
	  /** Output only. End of the Offer validity time. */
		endTime: Option[String] = None,
	  /** Parameters required to use current Offer to purchase. */
		parameterDefinitions: Option[List[Schema.GoogleCloudChannelV1ParameterDefinition]] = None,
	  /** The deal code of the offer to get a special promotion or discount. */
		dealCode: Option[String] = None
	)
	
	object GoogleCloudChannelV1Plan {
		enum PaymentPlanEnum extends Enum[PaymentPlanEnum] { case PAYMENT_PLAN_UNSPECIFIED, COMMITMENT, FLEXIBLE, FREE, TRIAL, OFFLINE }
		enum PaymentTypeEnum extends Enum[PaymentTypeEnum] { case PAYMENT_TYPE_UNSPECIFIED, PREPAY, POSTPAY }
	}
	case class GoogleCloudChannelV1Plan(
	  /** Describes how a reseller will be billed. */
		paymentPlan: Option[Schema.GoogleCloudChannelV1Plan.PaymentPlanEnum] = None,
	  /** Specifies when the payment needs to happen. */
		paymentType: Option[Schema.GoogleCloudChannelV1Plan.PaymentTypeEnum] = None,
	  /** Describes how frequently the reseller will be billed, such as once per month. */
		paymentCycle: Option[Schema.GoogleCloudChannelV1Period] = None,
	  /** Present for Offers with a trial period. For trial-only Offers, a paid service needs to start before the trial period ends for continued service. For Regular Offers with a trial period, the regular pricing goes into effect when trial period ends, or if paid service is started before the end of the trial period. */
		trialPeriod: Option[Schema.GoogleCloudChannelV1Period] = None,
	  /** Reseller Billing account to charge after an offer transaction. Only present for Google Cloud offers. */
		billingAccount: Option[String] = None
	)
	
	case class GoogleCloudChannelV1Constraints(
	  /** Represents constraints required to purchase the Offer for a customer. */
		customerConstraints: Option[Schema.GoogleCloudChannelV1CustomerConstraints] = None
	)
	
	object GoogleCloudChannelV1CustomerConstraints {
		enum AllowedCustomerTypesEnum extends Enum[AllowedCustomerTypesEnum] { case CUSTOMER_TYPE_UNSPECIFIED, DOMAIN, TEAM }
		enum PromotionalOrderTypesEnum extends Enum[PromotionalOrderTypesEnum] { case PROMOTIONAL_TYPE_UNSPECIFIED, NEW_UPGRADE, TRANSFER, PROMOTION_SWITCH }
	}
	case class GoogleCloudChannelV1CustomerConstraints(
	  /** Allowed geographical regions of the customer. */
		allowedRegions: Option[List[String]] = None,
	  /** Allowed Customer Type. */
		allowedCustomerTypes: Option[List[Schema.GoogleCloudChannelV1CustomerConstraints.AllowedCustomerTypesEnum]] = None,
	  /** Allowed Promotional Order Type. Present for Promotional offers. */
		promotionalOrderTypes: Option[List[Schema.GoogleCloudChannelV1CustomerConstraints.PromotionalOrderTypesEnum]] = None
	)
	
	object GoogleCloudChannelV1PriceByResource {
		enum ResourceTypeEnum extends Enum[ResourceTypeEnum] { case RESOURCE_TYPE_UNSPECIFIED, SEAT, MAU, GB, LICENSED_USER, MINUTES, IAAS_USAGE, SUBSCRIPTION }
	}
	case class GoogleCloudChannelV1PriceByResource(
	  /** Resource Type. Example: SEAT */
		resourceType: Option[Schema.GoogleCloudChannelV1PriceByResource.ResourceTypeEnum] = None,
	  /** Price of the Offer. Present if there are no price phases. */
		price: Option[Schema.GoogleCloudChannelV1Price] = None,
	  /** Specifies the price by time range. */
		pricePhases: Option[List[Schema.GoogleCloudChannelV1PricePhase]] = None
	)
	
	case class GoogleCloudChannelV1Price(
	  /** Base price. */
		basePrice: Option[Schema.GoogleTypeMoney] = None,
	  /** Discount percentage, represented as decimal. For example, a 20% discount will be represent as 0.2. */
		discount: Option[BigDecimal] = None,
	  /** Effective Price after applying the discounts. */
		effectivePrice: Option[Schema.GoogleTypeMoney] = None,
	  /** Link to external price list, such as link to Google Voice rate card. */
		externalPriceUri: Option[String] = None
	)
	
	object GoogleCloudChannelV1PricePhase {
		enum PeriodTypeEnum extends Enum[PeriodTypeEnum] { case PERIOD_TYPE_UNSPECIFIED, DAY, MONTH, YEAR }
	}
	case class GoogleCloudChannelV1PricePhase(
	  /** Defines the phase period type. */
		periodType: Option[Schema.GoogleCloudChannelV1PricePhase.PeriodTypeEnum] = None,
	  /** Defines first period for the phase. */
		firstPeriod: Option[Int] = None,
	  /** Defines first period for the phase. */
		lastPeriod: Option[Int] = None,
	  /** Price of the phase. Present if there are no price tiers. */
		price: Option[Schema.GoogleCloudChannelV1Price] = None,
	  /** Price by the resource tiers. */
		priceTiers: Option[List[Schema.GoogleCloudChannelV1PriceTier]] = None
	)
	
	case class GoogleCloudChannelV1PriceTier(
	  /** First resource for which the tier price applies. */
		firstResource: Option[Int] = None,
	  /** Last resource for which the tier price applies. */
		lastResource: Option[Int] = None,
	  /** Price of the tier. */
		price: Option[Schema.GoogleCloudChannelV1Price] = None
	)
	
	object GoogleCloudChannelV1ParameterDefinition {
		enum ParameterTypeEnum extends Enum[ParameterTypeEnum] { case PARAMETER_TYPE_UNSPECIFIED, INT64, STRING, DOUBLE, BOOLEAN }
	}
	case class GoogleCloudChannelV1ParameterDefinition(
	  /** Name of the parameter. */
		name: Option[String] = None,
	  /** Data type of the parameter. Minimal value, Maximum value and allowed values will use specified data type here. */
		parameterType: Option[Schema.GoogleCloudChannelV1ParameterDefinition.ParameterTypeEnum] = None,
	  /** Minimal value of the parameter, if applicable. Inclusive. For example, minimal commitment when purchasing Anthos is 0.01. Applicable to INT64 and DOUBLE parameter types. */
		minValue: Option[Schema.GoogleCloudChannelV1Value] = None,
	  /** Maximum value of the parameter, if applicable. Inclusive. For example, maximum seats when purchasing Google Workspace Business Standard. Applicable to INT64 and DOUBLE parameter types. */
		maxValue: Option[Schema.GoogleCloudChannelV1Value] = None,
	  /** If not empty, parameter values must be drawn from this list. For example, [us-west1, us-west2, ...] Applicable to STRING parameter type. */
		allowedValues: Option[List[Schema.GoogleCloudChannelV1Value]] = None,
	  /** If set to true, parameter is optional to purchase this Offer. */
		optional: Option[Boolean] = None
	)
	
	case class GoogleCloudChannelV1CreateEntitlementRequest(
	  /** Required. The entitlement to create. */
		entitlement: Option[Schema.GoogleCloudChannelV1Entitlement] = None,
	  /** Optional. You can specify an optional unique request ID, and if you need to retry your request, the server will know to ignore the request if it's complete. For example, you make an initial request and the request times out. If you make the request again with the same request ID, the server can check if it received the original operation with the same request ID. If it did, it will ignore the second request. The request ID must be a valid [UUID](https://tools.ietf.org/html/rfc4122) with the exception that zero UUID is not supported (`00000000-0000-0000-0000-000000000000`). */
		requestId: Option[String] = None
	)
	
	case class GoogleCloudChannelV1ChangeParametersRequest(
	  /** Required. Entitlement parameters to update. You can only change editable parameters. To view the available Parameters for a request, refer to the Offer.parameter_definitions from the desired offer. */
		parameters: Option[List[Schema.GoogleCloudChannelV1Parameter]] = None,
	  /** Optional. You can specify an optional unique request ID, and if you need to retry your request, the server will know to ignore the request if it's complete. For example, you make an initial request and the request times out. If you make the request again with the same request ID, the server can check if it received the original operation with the same request ID. If it did, it will ignore the second request. The request ID must be a valid [UUID](https://tools.ietf.org/html/rfc4122) with the exception that zero UUID is not supported (`00000000-0000-0000-0000-000000000000`). */
		requestId: Option[String] = None,
	  /** Optional. Purchase order ID provided by the reseller. */
		purchaseOrderId: Option[String] = None
	)
	
	case class GoogleCloudChannelV1ChangeRenewalSettingsRequest(
	  /** Required. New renewal settings. */
		renewalSettings: Option[Schema.GoogleCloudChannelV1RenewalSettings] = None,
	  /** Optional. You can specify an optional unique request ID, and if you need to retry your request, the server will know to ignore the request if it's complete. For example, you make an initial request and the request times out. If you make the request again with the same request ID, the server can check if it received the original operation with the same request ID. If it did, it will ignore the second request. The request ID must be a valid [UUID](https://tools.ietf.org/html/rfc4122) with the exception that zero UUID is not supported (`00000000-0000-0000-0000-000000000000`). */
		requestId: Option[String] = None
	)
	
	case class GoogleCloudChannelV1ChangeOfferRequest(
	  /** Required. New Offer. Format: accounts/{account_id}/offers/{offer_id}. */
		offer: Option[String] = None,
	  /** Optional. Parameters needed to purchase the Offer. To view the available Parameters refer to the Offer.parameter_definitions from the desired offer. */
		parameters: Option[List[Schema.GoogleCloudChannelV1Parameter]] = None,
	  /** Optional. Purchase order id provided by the reseller. */
		purchaseOrderId: Option[String] = None,
	  /** Optional. You can specify an optional unique request ID, and if you need to retry your request, the server will know to ignore the request if it's complete. For example, you make an initial request and the request times out. If you make the request again with the same request ID, the server can check if it received the original operation with the same request ID. If it did, it will ignore the second request. The request ID must be a valid [UUID](https://tools.ietf.org/html/rfc4122) with the exception that zero UUID is not supported (`00000000-0000-0000-0000-000000000000`). */
		requestId: Option[String] = None,
	  /** Optional. The billing account resource name that is used to pay for this entitlement when setting up billing on a trial subscription. This field is only relevant for multi-currency accounts. It should be left empty for single currency accounts. */
		billingAccount: Option[String] = None,
	  /** Optional. Price reference ID for the offer. Optional field only for offers that require additional price information. Used to guarantee that the pricing is consistent between quoting the offer and placing the order. Yet to be implemented: this field is currently not evaluated in the API if populated in a request. */
		priceReferenceId: Option[String] = None
	)
	
	case class GoogleCloudChannelV1StartPaidServiceRequest(
	  /** Optional. You can specify an optional unique request ID, and if you need to retry your request, the server will know to ignore the request if it's complete. For example, you make an initial request and the request times out. If you make the request again with the same request ID, the server can check if it received the original operation with the same request ID. If it did, it will ignore the second request. The request ID must be a valid [UUID](https://tools.ietf.org/html/rfc4122) with the exception that zero UUID is not supported (`00000000-0000-0000-0000-000000000000`). */
		requestId: Option[String] = None
	)
	
	case class GoogleCloudChannelV1SuspendEntitlementRequest(
	  /** Optional. You can specify an optional unique request ID, and if you need to retry your request, the server will know to ignore the request if it's complete. For example, you make an initial request and the request times out. If you make the request again with the same request ID, the server can check if it received the original operation with the same request ID. If it did, it will ignore the second request. The request ID must be a valid [UUID](https://tools.ietf.org/html/rfc4122) with the exception that zero UUID is not supported (`00000000-0000-0000-0000-000000000000`). */
		requestId: Option[String] = None
	)
	
	case class GoogleCloudChannelV1CancelEntitlementRequest(
	  /** Optional. You can specify an optional unique request ID, and if you need to retry your request, the server will know to ignore the request if it's complete. For example, you make an initial request and the request times out. If you make the request again with the same request ID, the server can check if it received the original operation with the same request ID. If it did, it will ignore the second request. The request ID must be a valid [UUID](https://tools.ietf.org/html/rfc4122) with the exception that zero UUID is not supported (`00000000-0000-0000-0000-000000000000`). */
		requestId: Option[String] = None
	)
	
	case class GoogleCloudChannelV1ActivateEntitlementRequest(
	  /** Optional. You can specify an optional unique request ID, and if you need to retry your request, the server will know to ignore the request if it's complete. For example, you make an initial request and the request times out. If you make the request again with the same request ID, the server can check if it received the original operation with the same request ID. If it did, it will ignore the second request. The request ID must be a valid [UUID](https://tools.ietf.org/html/rfc4122) with the exception that zero UUID is not supported (`00000000-0000-0000-0000-000000000000`). */
		requestId: Option[String] = None
	)
	
	case class GoogleCloudChannelV1TransferEntitlementsRequest(
	  /** Required. The new entitlements to create or transfer. */
		entitlements: Option[List[Schema.GoogleCloudChannelV1Entitlement]] = None,
	  /** The super admin of the resold customer generates this token to authorize a reseller to access their Cloud Identity and purchase entitlements on their behalf. You can omit this token after authorization. See https://support.google.com/a/answer/7643790 for more details. */
		authToken: Option[String] = None,
	  /** Optional. You can specify an optional unique request ID, and if you need to retry your request, the server will know to ignore the request if it's complete. For example, you make an initial request and the request times out. If you make the request again with the same request ID, the server can check if it received the original operation with the same request ID. If it did, it will ignore the second request. The request ID must be a valid [UUID](https://tools.ietf.org/html/rfc4122) with the exception that zero UUID is not supported (`00000000-0000-0000-0000-000000000000`). */
		requestId: Option[String] = None
	)
	
	case class GoogleCloudChannelV1TransferEntitlementsToGoogleRequest(
	  /** Required. The entitlements to transfer to Google. */
		entitlements: Option[List[Schema.GoogleCloudChannelV1Entitlement]] = None,
	  /** Optional. You can specify an optional unique request ID, and if you need to retry your request, the server will know to ignore the request if it's complete. For example, you make an initial request and the request times out. If you make the request again with the same request ID, the server can check if it received the original operation with the same request ID. If it did, it will ignore the second request. The request ID must be a valid [UUID](https://tools.ietf.org/html/rfc4122) with the exception that zero UUID is not supported (`00000000-0000-0000-0000-000000000000`). */
		requestId: Option[String] = None
	)
	
	case class GoogleCloudChannelV1ListChannelPartnerLinksResponse(
	  /** The Channel partner links for a reseller. */
		channelPartnerLinks: Option[List[Schema.GoogleCloudChannelV1ChannelPartnerLink]] = None,
	  /** A token to retrieve the next page of results. Pass to ListChannelPartnerLinksRequest.page_token to obtain that page. */
		nextPageToken: Option[String] = None
	)
	
	object GoogleCloudChannelV1ChannelPartnerLink {
		enum LinkStateEnum extends Enum[LinkStateEnum] { case CHANNEL_PARTNER_LINK_STATE_UNSPECIFIED, INVITED, ACTIVE, REVOKED, SUSPENDED }
	}
	case class GoogleCloudChannelV1ChannelPartnerLink(
	  /** Output only. Resource name for the channel partner link, in the format accounts/{account_id}/channelPartnerLinks/{id}. */
		name: Option[String] = None,
	  /** Required. Cloud Identity ID of the linked reseller. */
		resellerCloudIdentityId: Option[String] = None,
	  /** Required. State of the channel partner link. */
		linkState: Option[Schema.GoogleCloudChannelV1ChannelPartnerLink.LinkStateEnum] = None,
	  /** Output only. URI of the web page where partner accepts the link invitation. */
		inviteLinkUri: Option[String] = None,
	  /** Output only. Timestamp of when the channel partner link is created. */
		createTime: Option[String] = None,
	  /** Output only. Timestamp of when the channel partner link is updated. */
		updateTime: Option[String] = None,
	  /** Output only. Public identifier that a customer must use to generate a transfer token to move to this distributor-reseller combination. */
		publicId: Option[String] = None,
	  /** Output only. Cloud Identity info of the channel partner (IR). */
		channelPartnerCloudIdentityInfo: Option[Schema.GoogleCloudChannelV1CloudIdentityInfo] = None
	)
	
	case class GoogleCloudChannelV1UpdateChannelPartnerLinkRequest(
	  /** Required. The channel partner link to update. Only channel_partner_link.link_state is allowed for updates. */
		channelPartnerLink: Option[Schema.GoogleCloudChannelV1ChannelPartnerLink] = None,
	  /** Required. The update mask that applies to the resource. The only allowable value for an update mask is channel_partner_link.link_state. */
		updateMask: Option[String] = None
	)
	
	case class GoogleCloudChannelV1CustomerRepricingConfig(
	  /** Output only. Resource name of the CustomerRepricingConfig. Format: accounts/{account_id}/customers/{customer_id}/customerRepricingConfigs/{id}. */
		name: Option[String] = None,
	  /** Required. The configuration for bill modifications made by a reseller before sending it to customers. */
		repricingConfig: Option[Schema.GoogleCloudChannelV1RepricingConfig] = None,
	  /** Output only. Timestamp of an update to the repricing rule. If `update_time` is after RepricingConfig.effective_invoice_month then it indicates this was set mid-month. */
		updateTime: Option[String] = None
	)
	
	object GoogleCloudChannelV1RepricingConfig {
		enum RebillingBasisEnum extends Enum[RebillingBasisEnum] { case REBILLING_BASIS_UNSPECIFIED, COST_AT_LIST, DIRECT_CUSTOMER_COST }
	}
	case class GoogleCloudChannelV1RepricingConfig(
	  /** Applies the repricing configuration at the entitlement level. Note: If a ChannelPartnerRepricingConfig using RepricingConfig.EntitlementGranularity becomes effective, then no existing or future RepricingConfig.ChannelPartnerGranularity will apply to the RepricingConfig.EntitlementGranularity.entitlement. This is the recommended value for both CustomerRepricingConfig and ChannelPartnerRepricingConfig. */
		entitlementGranularity: Option[Schema.GoogleCloudChannelV1RepricingConfigEntitlementGranularity] = None,
	  /** Applies the repricing configuration at the channel partner level. Only ChannelPartnerRepricingConfig supports this value. Deprecated: This is no longer supported. Use RepricingConfig.entitlement_granularity instead. */
		channelPartnerGranularity: Option[Schema.GoogleCloudChannelV1RepricingConfigChannelPartnerGranularity] = None,
	  /** Required. The YearMonth when these adjustments activate. The Day field needs to be "0" since we only accept YearMonth repricing boundaries. */
		effectiveInvoiceMonth: Option[Schema.GoogleTypeDate] = None,
	  /** Required. Information about the adjustment. */
		adjustment: Option[Schema.GoogleCloudChannelV1RepricingAdjustment] = None,
	  /** Required. The RebillingBasis to use for this bill. Specifies the relative cost based on repricing costs you will apply. */
		rebillingBasis: Option[Schema.GoogleCloudChannelV1RepricingConfig.RebillingBasisEnum] = None,
	  /** The conditional overrides to apply for this configuration. If you list multiple overrides, only the first valid override is used. If you don't list any overrides, the API uses the normal adjustment and rebilling basis. */
		conditionalOverrides: Option[List[Schema.GoogleCloudChannelV1ConditionalOverride]] = None
	)
	
	case class GoogleCloudChannelV1RepricingConfigEntitlementGranularity(
	  /** Resource name of the entitlement. Format: accounts/{account_id}/customers/{customer_id}/entitlements/{entitlement_id} */
		entitlement: Option[String] = None
	)
	
	case class GoogleCloudChannelV1RepricingConfigChannelPartnerGranularity(
	
	)
	
	case class GoogleCloudChannelV1RepricingAdjustment(
	  /** Flat markup or markdown on an entire bill. */
		percentageAdjustment: Option[Schema.GoogleCloudChannelV1PercentageAdjustment] = None
	)
	
	case class GoogleCloudChannelV1PercentageAdjustment(
	  /** The percentage of the bill to adjust. For example: Mark down by 1% => "-1.00" Mark up by 1% => "1.00" Pass-Through => "0.00" */
		percentage: Option[Schema.GoogleTypeDecimal] = None
	)
	
	object GoogleCloudChannelV1ConditionalOverride {
		enum RebillingBasisEnum extends Enum[RebillingBasisEnum] { case REBILLING_BASIS_UNSPECIFIED, COST_AT_LIST, DIRECT_CUSTOMER_COST }
	}
	case class GoogleCloudChannelV1ConditionalOverride(
	  /** Required. Information about the applied override's adjustment. */
		adjustment: Option[Schema.GoogleCloudChannelV1RepricingAdjustment] = None,
	  /** Required. The RebillingBasis to use for the applied override. Shows the relative cost based on your repricing costs. */
		rebillingBasis: Option[Schema.GoogleCloudChannelV1ConditionalOverride.RebillingBasisEnum] = None,
	  /** Required. Specifies the condition which, if met, will apply the override. */
		repricingCondition: Option[Schema.GoogleCloudChannelV1RepricingCondition] = None
	)
	
	case class GoogleCloudChannelV1RepricingCondition(
	  /** SKU Group condition for override. */
		skuGroupCondition: Option[Schema.GoogleCloudChannelV1SkuGroupCondition] = None
	)
	
	case class GoogleCloudChannelV1SkuGroupCondition(
	  /** Specifies a SKU group (https://cloud.google.com/skus/sku-groups). Resource name of SKU group. Format: accounts/{account}/skuGroups/{sku_group}. Example: "accounts/C01234/skuGroups/3d50fd57-3157-4577-a5a9-a219b8490041". */
		skuGroup: Option[String] = None
	)
	
	case class GoogleCloudChannelV1ListCustomerRepricingConfigsResponse(
	  /** The repricing configs for this channel partner. */
		customerRepricingConfigs: Option[List[Schema.GoogleCloudChannelV1CustomerRepricingConfig]] = None,
	  /** A token to retrieve the next page of results. Pass to ListCustomerRepricingConfigsRequest.page_token to obtain that page. */
		nextPageToken: Option[String] = None
	)
	
	case class GoogleCloudChannelV1ChannelPartnerRepricingConfig(
	  /** Output only. Resource name of the ChannelPartnerRepricingConfig. Format: accounts/{account_id}/channelPartnerLinks/{channel_partner_id}/channelPartnerRepricingConfigs/{id}. */
		name: Option[String] = None,
	  /** Required. The configuration for bill modifications made by a reseller before sending it to ChannelPartner. */
		repricingConfig: Option[Schema.GoogleCloudChannelV1RepricingConfig] = None,
	  /** Output only. Timestamp of an update to the repricing rule. If `update_time` is after RepricingConfig.effective_invoice_month then it indicates this was set mid-month. */
		updateTime: Option[String] = None
	)
	
	case class GoogleCloudChannelV1ListChannelPartnerRepricingConfigsResponse(
	  /** The repricing configs for this channel partner. */
		channelPartnerRepricingConfigs: Option[List[Schema.GoogleCloudChannelV1ChannelPartnerRepricingConfig]] = None,
	  /** A token to retrieve the next page of results. Pass to ListChannelPartnerRepricingConfigsRequest.page_token to obtain that page. */
		nextPageToken: Option[String] = None
	)
	
	case class GoogleCloudChannelV1ListSkuGroupsResponse(
	  /** The list of SKU groups requested. */
		skuGroups: Option[List[Schema.GoogleCloudChannelV1SkuGroup]] = None,
	  /** A token to retrieve the next page of results. Pass to ListSkuGroupsRequest.page_token to obtain that page. */
		nextPageToken: Option[String] = None
	)
	
	case class GoogleCloudChannelV1SkuGroup(
	  /** Resource name of SKU group. Format: accounts/{account}/skuGroups/{sku_group}. Example: "accounts/C01234/skuGroups/3d50fd57-3157-4577-a5a9-a219b8490041". */
		name: Option[String] = None,
	  /** Unique human readable identifier for the SKU group. */
		displayName: Option[String] = None
	)
	
	case class GoogleCloudChannelV1ListSkuGroupBillableSkusResponse(
	  /** The list of billable SKUs in the requested SKU group. */
		billableSkus: Option[List[Schema.GoogleCloudChannelV1BillableSku]] = None,
	  /** A token to retrieve the next page of results. Pass to ListSkuGroupBillableSkusRequest.page_token to obtain that page. */
		nextPageToken: Option[String] = None
	)
	
	case class GoogleCloudChannelV1BillableSku(
	  /** Resource name of Billable SKU. Format: billableSkus/{sku}. Example: billableSkus/6E1B-6634-470F". */
		sku: Option[String] = None,
	  /** Unique human readable name for the SKU. */
		skuDisplayName: Option[String] = None,
	  /** Resource name of Service which contains Repricing SKU. Format: services/{service}. Example: "services/B7D9-FDCB-15D8". */
		service: Option[String] = None,
	  /** Unique human readable name for the Service. */
		serviceDisplayName: Option[String] = None
	)
	
	case class GoogleCloudChannelV1ListProductsResponse(
	  /** List of Products requested. */
		products: Option[List[Schema.GoogleCloudChannelV1Product]] = None,
	  /** A token to retrieve the next page of results. */
		nextPageToken: Option[String] = None
	)
	
	case class GoogleCloudChannelV1ListSkusResponse(
	  /** The list of SKUs requested. */
		skus: Option[List[Schema.GoogleCloudChannelV1Sku]] = None,
	  /** A token to retrieve the next page of results. */
		nextPageToken: Option[String] = None
	)
	
	case class GoogleCloudChannelV1ListOffersResponse(
	  /** The list of Offers requested. */
		offers: Option[List[Schema.GoogleCloudChannelV1Offer]] = None,
	  /** A token to retrieve the next page of results. */
		nextPageToken: Option[String] = None
	)
	
	case class GoogleCloudChannelV1ListPurchasableSkusResponse(
	  /** The list of SKUs requested. */
		purchasableSkus: Option[List[Schema.GoogleCloudChannelV1PurchasableSku]] = None,
	  /** A token to retrieve the next page of results. */
		nextPageToken: Option[String] = None
	)
	
	case class GoogleCloudChannelV1PurchasableSku(
	  /** SKU */
		sku: Option[Schema.GoogleCloudChannelV1Sku] = None
	)
	
	case class GoogleCloudChannelV1ListPurchasableOffersResponse(
	  /** The list of Offers requested. */
		purchasableOffers: Option[List[Schema.GoogleCloudChannelV1PurchasableOffer]] = None,
	  /** A token to retrieve the next page of results. */
		nextPageToken: Option[String] = None
	)
	
	case class GoogleCloudChannelV1PurchasableOffer(
	  /** Offer. */
		offer: Option[Schema.GoogleCloudChannelV1Offer] = None,
	  /** Optional. Price reference ID for the offer. Optional field only for offers that require additional price information. Used to guarantee that the pricing is consistent between quoting the offer and placing the order. */
		priceReferenceId: Option[String] = None
	)
	
	case class GoogleCloudChannelV1QueryEligibleBillingAccountsResponse(
	  /** List of SKU purchase groups where each group represents a set of SKUs that must be purchased using the same billing account. Each SKU from [QueryEligibleBillingAccountsRequest.skus] will appear in exactly one SKU group. */
		skuPurchaseGroups: Option[List[Schema.GoogleCloudChannelV1SkuPurchaseGroup]] = None
	)
	
	case class GoogleCloudChannelV1SkuPurchaseGroup(
	  /** Resource names of the SKUs included in this group. Format: products/{product_id}/skus/{sku_id}. */
		skus: Option[List[String]] = None,
	  /** List of billing accounts that are eligible to purhcase these SKUs. */
		billingAccountPurchaseInfos: Option[List[Schema.GoogleCloudChannelV1BillingAccountPurchaseInfo]] = None
	)
	
	case class GoogleCloudChannelV1BillingAccountPurchaseInfo(
	  /** The billing account resource. */
		billingAccount: Option[Schema.GoogleCloudChannelV1BillingAccount] = None
	)
	
	case class GoogleCloudChannelV1BillingAccount(
	  /** Output only. Resource name of the billing account. Format: accounts/{account_id}/billingAccounts/{billing_account_id}. */
		name: Option[String] = None,
	  /** Display name of the billing account. */
		displayName: Option[String] = None,
	  /** Output only. The time when this billing account was created. */
		createTime: Option[String] = None,
	  /** Output only. The 3-letter currency code defined in ISO 4217. */
		currencyCode: Option[String] = None,
	  /** Output only. The CLDR region code. */
		regionCode: Option[String] = None
	)
	
	case class GoogleCloudChannelV1RegisterSubscriberRequest(
	  /** Optional. Resource name of the account. Required if integrator is not provided. Otherwise, leave this field empty/unset. */
		account: Option[String] = None,
	  /** Required. Service account that provides subscriber access to the registered topic. */
		serviceAccount: Option[String] = None,
	  /** Optional. Resource name of the integrator. Required if account is not provided. Otherwise, leave this field empty/unset. */
		integrator: Option[String] = None
	)
	
	case class GoogleCloudChannelV1RegisterSubscriberResponse(
	  /** Name of the topic the subscriber will listen to. */
		topic: Option[String] = None
	)
	
	case class GoogleCloudChannelV1UnregisterSubscriberRequest(
	  /** Optional. Resource name of the account. Required if integrator is not provided. Otherwise, leave this field empty/unset. */
		account: Option[String] = None,
	  /** Required. Service account to unregister from subscriber access to the topic. */
		serviceAccount: Option[String] = None,
	  /** Optional. Resource name of the integrator. Required if account is not provided. Otherwise, leave this field empty/unset. */
		integrator: Option[String] = None
	)
	
	case class GoogleCloudChannelV1UnregisterSubscriberResponse(
	  /** Name of the topic the service account subscriber access was removed from. */
		topic: Option[String] = None
	)
	
	case class GoogleCloudChannelV1ListSubscribersResponse(
	  /** Name of the topic registered with the reseller. */
		topic: Option[String] = None,
	  /** List of service accounts which have subscriber access to the topic. */
		serviceAccounts: Option[List[String]] = None,
	  /** A token that can be sent as `page_token` to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None
	)
	
	case class GoogleCloudChannelV1ListEntitlementChangesResponse(
	  /** The list of entitlement changes. */
		entitlementChanges: Option[List[Schema.GoogleCloudChannelV1EntitlementChange]] = None,
	  /** A token to list the next page of results. */
		nextPageToken: Option[String] = None
	)
	
	object GoogleCloudChannelV1EntitlementChange {
		enum SuspensionReasonEnum extends Enum[SuspensionReasonEnum] { case SUSPENSION_REASON_UNSPECIFIED, RESELLER_INITIATED, TRIAL_ENDED, RENEWAL_WITH_TYPE_CANCEL, PENDING_TOS_ACCEPTANCE, OTHER }
		enum CancellationReasonEnum extends Enum[CancellationReasonEnum] { case CANCELLATION_REASON_UNSPECIFIED, SERVICE_TERMINATED, RELATIONSHIP_ENDED, PARTIAL_TRANSFER }
		enum ActivationReasonEnum extends Enum[ActivationReasonEnum] { case ACTIVATION_REASON_UNSPECIFIED, RESELLER_REVOKED_SUSPENSION, CUSTOMER_ACCEPTED_PENDING_TOS, RENEWAL_SETTINGS_CHANGED, OTHER_ACTIVATION_REASON }
		enum ChangeTypeEnum extends Enum[ChangeTypeEnum] { case CHANGE_TYPE_UNSPECIFIED, CREATED, PRICE_PLAN_SWITCHED, COMMITMENT_CHANGED, RENEWED, SUSPENDED, ACTIVATED, CANCELLED, SKU_CHANGED, RENEWAL_SETTING_CHANGED, PAID_SUBSCRIPTION_STARTED, LICENSE_CAP_CHANGED, SUSPENSION_DETAILS_CHANGED, TRIAL_END_DATE_EXTENDED, TRIAL_STARTED }
		enum OperatorTypeEnum extends Enum[OperatorTypeEnum] { case OPERATOR_TYPE_UNSPECIFIED, CUSTOMER_SERVICE_REPRESENTATIVE, SYSTEM, CUSTOMER, RESELLER }
	}
	case class GoogleCloudChannelV1EntitlementChange(
	  /** Suspension reason for the Entitlement. */
		suspensionReason: Option[Schema.GoogleCloudChannelV1EntitlementChange.SuspensionReasonEnum] = None,
	  /** Cancellation reason for the Entitlement. */
		cancellationReason: Option[Schema.GoogleCloudChannelV1EntitlementChange.CancellationReasonEnum] = None,
	  /** The Entitlement's activation reason */
		activationReason: Option[Schema.GoogleCloudChannelV1EntitlementChange.ActivationReasonEnum] = None,
	  /** e.g. purchase_number change reason, entered by CRS. */
		otherChangeReason: Option[String] = None,
	  /** Required. Resource name of an entitlement in the form: accounts/{account_id}/customers/{customer_id}/entitlements/{entitlement_id} */
		entitlement: Option[String] = None,
	  /** Required. Resource name of the Offer at the time of change. Takes the form: accounts/{account_id}/offers/{offer_id}. */
		offer: Option[String] = None,
	  /** Service provisioned for an Entitlement. */
		provisionedService: Option[Schema.GoogleCloudChannelV1ProvisionedService] = None,
	  /** The change action type. */
		changeType: Option[Schema.GoogleCloudChannelV1EntitlementChange.ChangeTypeEnum] = None,
	  /** The submitted time of the change. */
		createTime: Option[String] = None,
	  /** Operator type responsible for the change. */
		operatorType: Option[Schema.GoogleCloudChannelV1EntitlementChange.OperatorTypeEnum] = None,
	  /** Extended parameters, such as: purchase_order_number, gcp_details; internal_correlation_id, long_running_operation_id, order_id; etc. */
		parameters: Option[List[Schema.GoogleCloudChannelV1Parameter]] = None,
	  /** Human-readable identifier that shows what operator made a change. When the operator_type is RESELLER, this is the user's email address. For all other operator types, this is empty. */
		operator: Option[String] = None
	)
	
	object GoogleCloudChannelV1alpha1CustomerEvent {
		enum EventTypeEnum extends Enum[EventTypeEnum] { case TYPE_UNSPECIFIED, PRIMARY_DOMAIN_CHANGED, PRIMARY_DOMAIN_VERIFIED }
	}
	case class GoogleCloudChannelV1alpha1CustomerEvent(
	  /** Resource name of the customer. Format: accounts/{account_id}/customers/{customer_id} */
		customer: Option[String] = None,
	  /** Type of event which happened for the customer. */
		eventType: Option[Schema.GoogleCloudChannelV1alpha1CustomerEvent.EventTypeEnum] = None
	)
	
	object GoogleCloudChannelV1CustomerEvent {
		enum EventTypeEnum extends Enum[EventTypeEnum] { case TYPE_UNSPECIFIED, PRIMARY_DOMAIN_CHANGED, PRIMARY_DOMAIN_VERIFIED }
	}
	case class GoogleCloudChannelV1CustomerEvent(
	  /** Resource name of the customer. Format: accounts/{account_id}/customers/{customer_id} */
		customer: Option[String] = None,
	  /** Type of event which happened for the customer. */
		eventType: Option[Schema.GoogleCloudChannelV1CustomerEvent.EventTypeEnum] = None
	)
	
	object GoogleCloudChannelV1alpha1EntitlementEvent {
		enum EventTypeEnum extends Enum[EventTypeEnum] { case TYPE_UNSPECIFIED, CREATED, PRICE_PLAN_SWITCHED, COMMITMENT_CHANGED, RENEWED, SUSPENDED, ACTIVATED, CANCELLED, SKU_CHANGED, RENEWAL_SETTING_CHANGED, PAID_SERVICE_STARTED, LICENSE_ASSIGNMENT_CHANGED, LICENSE_CAP_CHANGED }
	}
	case class GoogleCloudChannelV1alpha1EntitlementEvent(
	  /** Resource name of an entitlement of the form: accounts/{account_id}/customers/{customer_id}/entitlements/{entitlement_id} */
		entitlement: Option[String] = None,
	  /** Type of event which happened for the entitlement. */
		eventType: Option[Schema.GoogleCloudChannelV1alpha1EntitlementEvent.EventTypeEnum] = None
	)
	
	object GoogleCloudChannelV1EntitlementEvent {
		enum EventTypeEnum extends Enum[EventTypeEnum] { case TYPE_UNSPECIFIED, CREATED, PRICE_PLAN_SWITCHED, COMMITMENT_CHANGED, RENEWED, SUSPENDED, ACTIVATED, CANCELLED, SKU_CHANGED, RENEWAL_SETTING_CHANGED, PAID_SERVICE_STARTED, LICENSE_ASSIGNMENT_CHANGED, LICENSE_CAP_CHANGED }
	}
	case class GoogleCloudChannelV1EntitlementEvent(
	  /** Resource name of an entitlement of the form: accounts/{account_id}/customers/{customer_id}/entitlements/{entitlement_id} */
		entitlement: Option[String] = None,
	  /** Type of event which happened for the entitlement. */
		eventType: Option[Schema.GoogleCloudChannelV1EntitlementEvent.EventTypeEnum] = None
	)
	
	object GoogleCloudChannelV1alpha1OperationMetadata {
		enum OperationTypeEnum extends Enum[OperationTypeEnum] { case OPERATION_TYPE_UNSPECIFIED, CREATE_ENTITLEMENT, CHANGE_QUANTITY, CHANGE_RENEWAL_SETTINGS, CHANGE_PLAN, START_PAID_SERVICE, CHANGE_SKU, ACTIVATE_ENTITLEMENT, SUSPEND_ENTITLEMENT, CANCEL_ENTITLEMENT, TRANSFER_ENTITLEMENTS, TRANSFER_ENTITLEMENTS_TO_GOOGLE, CHANGE_OFFER, CHANGE_PARAMETERS, PROVISION_CLOUD_IDENTITY }
	}
	case class GoogleCloudChannelV1alpha1OperationMetadata(
	  /** The RPC that initiated this Long Running Operation. */
		operationType: Option[Schema.GoogleCloudChannelV1alpha1OperationMetadata.OperationTypeEnum] = None
	)
	
	object GoogleCloudChannelV1OperationMetadata {
		enum OperationTypeEnum extends Enum[OperationTypeEnum] { case OPERATION_TYPE_UNSPECIFIED, CREATE_ENTITLEMENT, CHANGE_RENEWAL_SETTINGS, START_PAID_SERVICE, ACTIVATE_ENTITLEMENT, SUSPEND_ENTITLEMENT, CANCEL_ENTITLEMENT, TRANSFER_ENTITLEMENTS, TRANSFER_ENTITLEMENTS_TO_GOOGLE, CHANGE_OFFER, CHANGE_PARAMETERS, PROVISION_CLOUD_IDENTITY }
	}
	case class GoogleCloudChannelV1OperationMetadata(
	  /** The RPC that initiated this Long Running Operation. */
		operationType: Option[Schema.GoogleCloudChannelV1OperationMetadata.OperationTypeEnum] = None
	)
	
	case class GoogleCloudChannelV1alpha1SubscriberEvent(
	  /** Customer event sent as part of Pub/Sub event to partners. */
		customerEvent: Option[Schema.GoogleCloudChannelV1alpha1CustomerEvent] = None,
	  /** Entitlement event sent as part of Pub/Sub event to partners. */
		entitlementEvent: Option[Schema.GoogleCloudChannelV1alpha1EntitlementEvent] = None,
	  /** Channel Partner event sent as part of Pub/Sub event to partners. */
		channelPartnerEvent: Option[Schema.GoogleCloudChannelV1alpha1ChannelPartnerEvent] = None,
	  /** Opportunity event sent as part of Pub/Sub event to partners/integrators. */
		opportunityEvent: Option[Schema.GoogleCloudChannelV1alpha1OpportunityEvent] = None
	)
	
	object GoogleCloudChannelV1alpha1ChannelPartnerEvent {
		enum EventTypeEnum extends Enum[EventTypeEnum] { case TYPE_UNSPECIFIED, LINK_STATE_CHANGED, PARTNER_ADVANTAGE_INFO_CHANGED }
	}
	case class GoogleCloudChannelV1alpha1ChannelPartnerEvent(
	  /** Resource name for the Channel Partner Link. Channel_partner uses the format: accounts/{account_id}/channelPartnerLinks/{channel_partner_id} */
		channelPartner: Option[String] = None,
	  /** Type of event which happened for the channel partner. */
		eventType: Option[Schema.GoogleCloudChannelV1alpha1ChannelPartnerEvent.EventTypeEnum] = None
	)
	
	object GoogleCloudChannelV1alpha1OpportunityEvent {
		enum EventTypeEnum extends Enum[EventTypeEnum] { case TYPE_UNSPECIFIED, CREATED, UPDATED }
	}
	case class GoogleCloudChannelV1alpha1OpportunityEvent(
	  /** Resource name of the opportunity. Format: opportunities/{opportunity} */
		opportunity: Option[String] = None,
	  /** Type of event which happened for the opportunity. */
		eventType: Option[Schema.GoogleCloudChannelV1alpha1OpportunityEvent.EventTypeEnum] = None
	)
	
	case class GoogleCloudChannelV1SubscriberEvent(
	  /** Customer event sent as part of Pub/Sub event to partners. */
		customerEvent: Option[Schema.GoogleCloudChannelV1CustomerEvent] = None,
	  /** Entitlement event sent as part of Pub/Sub event to partners. */
		entitlementEvent: Option[Schema.GoogleCloudChannelV1EntitlementEvent] = None
	)
	
	case class GoogleCloudChannelV1alpha1TransferEntitlementsResponse(
	  /** The transferred entitlements. */
		entitlements: Option[List[Schema.GoogleCloudChannelV1alpha1Entitlement]] = None
	)
	
	object GoogleCloudChannelV1alpha1Entitlement {
		enum ProvisioningStateEnum extends Enum[ProvisioningStateEnum] { case PROVISIONING_STATE_UNSPECIFIED, ACTIVE, CANCELED, COMPLETE, PENDING, SUSPENDED }
		enum SuspensionReasonsEnum extends Enum[SuspensionReasonsEnum] { case SUSPENSION_REASON_UNSPECIFIED, RESELLER_INITIATED, TRIAL_ENDED, RENEWAL_WITH_TYPE_CANCEL, PENDING_TOS_ACCEPTANCE, OTHER }
	}
	case class GoogleCloudChannelV1alpha1Entitlement(
	  /** Output only. Resource name of an entitlement in the form: accounts/{account_id}/customers/{customer_id}/entitlements/{entitlement_id}. */
		name: Option[String] = None,
	  /** Output only. The time at which the entitlement is created. */
		createTime: Option[String] = None,
	  /** Output only. The time at which the entitlement is updated. */
		updateTime: Option[String] = None,
	  /** Cloud Identity ID of a channel partner who will be the direct reseller for the customer's order. This field is generally used in 2-tier ordering, where the order is placed by a top-level distributor on behalf of their channel partner or reseller. Required for distributors. Deprecated: `channel_partner_id` has been moved to the Customer. */
		channelPartnerId: Option[String] = None,
	  /** Required. The offer resource name for which the entitlement is to be created. Takes the form: accounts/{account_id}/offers/{offer_id}. */
		offer: Option[String] = None,
	  /** Number of units for a commitment-based Offer. For example, for seat-based Offers, this would be the number of seats; for license-based Offers, this would be the number of licenses. Required for creating commitment-based Offers. Deprecated: Use `parameters` instead. */
		numUnits: Option[Int] = None,
	  /** Maximum number of units for a non commitment-based Offer, such as Flexible, Trial or Free entitlements. For commitment-based entitlements, this is a read-only field, which only the internal support team can update. Deprecated: Use `parameters` instead. */
		maxUnits: Option[Int] = None,
	  /** The current number of users that are assigned a license for the product defined in provisioned_service.skuId. Read-only. Deprecated: Use `parameters` instead. */
		assignedUnits: Option[Int] = None,
	  /** Commitment settings for a commitment-based Offer. Required for commitment based offers. */
		commitmentSettings: Option[Schema.GoogleCloudChannelV1alpha1CommitmentSettings] = None,
	  /** Output only. Current provisioning state of the entitlement. */
		provisioningState: Option[Schema.GoogleCloudChannelV1alpha1Entitlement.ProvisioningStateEnum] = None,
	  /** Output only. Service provisioning details for the entitlement. */
		provisionedService: Option[Schema.GoogleCloudChannelV1alpha1ProvisionedService] = None,
	  /** Output only. Enumerable of all current suspension reasons for an entitlement. */
		suspensionReasons: Option[List[Schema.GoogleCloudChannelV1alpha1Entitlement.SuspensionReasonsEnum]] = None,
	  /** Optional. This purchase order (PO) information is for resellers to use for their company tracking usage. If a purchaseOrderId value is given, it appears in the API responses and shows up in the invoice. The property accepts up to 80 plain text characters. This is only supported for Google Workspace entitlements. */
		purchaseOrderId: Option[String] = None,
	  /** Output only. Settings for trial offers. */
		trialSettings: Option[Schema.GoogleCloudChannelV1alpha1TrialSettings] = None,
	  /** Association information to other entitlements. */
		associationInfo: Option[Schema.GoogleCloudChannelV1alpha1AssociationInfo] = None,
	  /** Extended entitlement parameters. When creating an entitlement, valid parameter names and values are defined in the Offer.parameter_definitions. For Google Workspace, the following Parameters may be accepted as input: - max_units: The maximum assignable units for a flexible offer OR - num_units: The total commitment for commitment-based offers The response may additionally include the following output-only Parameters: - assigned_units: The number of licenses assigned to users. For Google Cloud billing subaccounts, the following Parameter may be accepted as input: - display_name: The display name of the billing subaccount. */
		parameters: Option[List[Schema.GoogleCloudChannelV1alpha1Parameter]] = None,
	  /** Optional. The billing account resource name that is used to pay for this entitlement. */
		billingAccount: Option[String] = None,
	  /** Optional. Price reference ID for the offer. Optional field only for offers that require additional price information. Used to guarantee that the pricing is consistent between quoting the offer and placing the order. Yet to be implemented: this field is currently not evaluated in the API if populated in a request. */
		priceReferenceId: Option[String] = None
	)
	
	case class GoogleCloudChannelV1alpha1CommitmentSettings(
	  /** Output only. Commitment start timestamp. */
		startTime: Option[String] = None,
	  /** Output only. Commitment end timestamp. */
		endTime: Option[String] = None,
	  /** Optional. Renewal settings applicable for a commitment-based Offer. */
		renewalSettings: Option[Schema.GoogleCloudChannelV1alpha1RenewalSettings] = None
	)
	
	object GoogleCloudChannelV1alpha1RenewalSettings {
		enum PaymentOptionEnum extends Enum[PaymentOptionEnum] { case PAYMENT_OPTION_UNSPECIFIED, ANNUAL, MONTHLY }
		enum PaymentPlanEnum extends Enum[PaymentPlanEnum] { case PAYMENT_PLAN_UNSPECIFIED, COMMITMENT, FLEXIBLE, FREE, TRIAL, OFFLINE }
	}
	case class GoogleCloudChannelV1alpha1RenewalSettings(
	  /** If false, the plan will be completed at the end date. */
		enableRenewal: Option[Boolean] = None,
	  /** If true and enable_renewal = true, the unit (for example seats or licenses) will be set to the number of active units at renewal time. */
		resizeUnitCount: Option[Boolean] = None,
	  /** If true, disables commitment-based offer on renewal and switches to flexible or pay as you go. Deprecated: Use `payment_plan` instead. */
		disableCommitment: Option[Boolean] = None,
	  /** Set if enable_renewal=true. Deprecated: Use `payment_cycle` instead. */
		paymentOption: Option[Schema.GoogleCloudChannelV1alpha1RenewalSettings.PaymentOptionEnum] = None,
	  /** Describes how a reseller will be billed. */
		paymentPlan: Option[Schema.GoogleCloudChannelV1alpha1RenewalSettings.PaymentPlanEnum] = None,
	  /** Describes how frequently the reseller will be billed, such as once per month. */
		paymentCycle: Option[Schema.GoogleCloudChannelV1alpha1Period] = None,
	  /** Output only. The offer resource name that the entitlement will renew on at the end date. Takes the form: accounts/{account_id}/offers/{offer_id}. */
		scheduledRenewalOffer: Option[String] = None
	)
	
	object GoogleCloudChannelV1alpha1Period {
		enum PeriodTypeEnum extends Enum[PeriodTypeEnum] { case PERIOD_TYPE_UNSPECIFIED, DAY, MONTH, YEAR }
	}
	case class GoogleCloudChannelV1alpha1Period(
	  /** Total duration of Period Type defined. */
		duration: Option[Int] = None,
	  /** Period Type. */
		periodType: Option[Schema.GoogleCloudChannelV1alpha1Period.PeriodTypeEnum] = None
	)
	
	case class GoogleCloudChannelV1alpha1ProvisionedService(
	  /** Output only. Provisioning ID of the entitlement. For Google Workspace, this is the underlying Subscription ID. For Google Cloud, this is the Billing Account ID of the billing subaccount. */
		provisioningId: Option[String] = None,
	  /** Output only. The product pertaining to the provisioning resource as specified in the Offer. */
		productId: Option[String] = None,
	  /** Output only. The SKU pertaining to the provisioning resource as specified in the Offer. */
		skuId: Option[String] = None
	)
	
	case class GoogleCloudChannelV1alpha1TrialSettings(
	  /** Determines if the entitlement is in a trial or not: &#42; `true` - The entitlement is in trial. &#42; `false` - The entitlement is not in trial. */
		trial: Option[Boolean] = None,
	  /** Date when the trial ends. The value is in milliseconds using the UNIX Epoch format. See an example [Epoch converter](https://www.epochconverter.com). */
		endTime: Option[String] = None
	)
	
	case class GoogleCloudChannelV1alpha1AssociationInfo(
	  /** The name of the base entitlement, for which this entitlement is an add-on. */
		baseEntitlement: Option[String] = None
	)
	
	case class GoogleCloudChannelV1alpha1Parameter(
	  /** Name of the parameter. */
		name: Option[String] = None,
	  /** Value of the parameter. */
		value: Option[Schema.GoogleCloudChannelV1alpha1Value] = None,
	  /** Output only. Specifies whether this parameter is allowed to be changed. For example, for a Google Workspace Business Starter entitlement in commitment plan, num_units is editable when entitlement is active. */
		editable: Option[Boolean] = None
	)
	
	case class GoogleCloudChannelV1alpha1Value(
	  /** Represents an int64 value. */
		int64Value: Option[String] = None,
	  /** Represents a string value. */
		stringValue: Option[String] = None,
	  /** Represents a double value. */
		doubleValue: Option[BigDecimal] = None,
	  /** Represents an 'Any' proto value. */
		protoValue: Option[Map[String, JsValue]] = None,
	  /** Represents a boolean value. */
		boolValue: Option[Boolean] = None
	)
	
	case class GoogleCloudChannelV1TransferEntitlementsResponse(
	  /** The transferred entitlements. */
		entitlements: Option[List[Schema.GoogleCloudChannelV1Entitlement]] = None
	)
	
	case class GoogleCloudChannelV1alpha1RunReportJobResponse(
	  /** Pass `report_job.name` to FetchReportResultsRequest.report_job to retrieve the report's results. */
		reportJob: Option[Schema.GoogleCloudChannelV1alpha1ReportJob] = None,
	  /** The metadata for the report's results (display name, columns, row count, and date range). If you view this before the operation finishes, you may see incomplete data. */
		reportMetadata: Option[Schema.GoogleCloudChannelV1alpha1ReportResultsMetadata] = None
	)
	
	case class GoogleCloudChannelV1alpha1ReportJob(
	  /** Required. The resource name of a report job. Name uses the format: `accounts/{account_id}/reportJobs/{report_job_id}` */
		name: Option[String] = None,
	  /** The current status of report generation. */
		reportStatus: Option[Schema.GoogleCloudChannelV1alpha1ReportStatus] = None
	)
	
	object GoogleCloudChannelV1alpha1ReportStatus {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, STARTED, WRITING, AVAILABLE, FAILED }
	}
	case class GoogleCloudChannelV1alpha1ReportStatus(
	  /** The current state of the report generation process. */
		state: Option[Schema.GoogleCloudChannelV1alpha1ReportStatus.StateEnum] = None,
	  /** The report generation's start time. */
		startTime: Option[String] = None,
	  /** The report generation's completion time. */
		endTime: Option[String] = None
	)
	
	case class GoogleCloudChannelV1alpha1ReportResultsMetadata(
	  /** Details of the completed report. */
		report: Option[Schema.GoogleCloudChannelV1alpha1Report] = None,
	  /** The total number of rows of data in the final report. */
		rowCount: Option[String] = None,
	  /** The date range of reported usage. */
		dateRange: Option[Schema.GoogleCloudChannelV1alpha1DateRange] = None,
	  /** The usage dates immediately preceding `date_range` with the same duration. Use this to calculate trending usage and costs. This is only populated if you request trending data. For example, if `date_range` is July 1-15, `preceding_date_range` will be June 16-30. */
		precedingDateRange: Option[Schema.GoogleCloudChannelV1alpha1DateRange] = None
	)
	
	case class GoogleCloudChannelV1alpha1Report(
	  /** Required. The report's resource name. Specifies the account and report used to generate report data. The report_id identifier is a UID (for example, `613bf59q`). Name uses the format: accounts/{account_id}/reports/{report_id} */
		name: Option[String] = None,
	  /** A human-readable name for this report. */
		displayName: Option[String] = None,
	  /** The list of columns included in the report. This defines the schema of the report results. */
		columns: Option[List[Schema.GoogleCloudChannelV1alpha1Column]] = None,
	  /** A description of other aspects of the report, such as the products it supports. */
		description: Option[String] = None
	)
	
	object GoogleCloudChannelV1alpha1Column {
		enum DataTypeEnum extends Enum[DataTypeEnum] { case DATA_TYPE_UNSPECIFIED, STRING, INT, DECIMAL, MONEY, DATE, DATE_TIME }
	}
	case class GoogleCloudChannelV1alpha1Column(
	  /** The unique name of the column (for example, customer_domain, channel_partner, customer_cost). You can use column IDs in RunReportJobRequest.filter. To see all reports and their columns, call CloudChannelReportsService.ListReports. */
		columnId: Option[String] = None,
	  /** The column's display name. */
		displayName: Option[String] = None,
	  /** The type of the values for this column. */
		dataType: Option[Schema.GoogleCloudChannelV1alpha1Column.DataTypeEnum] = None
	)
	
	case class GoogleCloudChannelV1alpha1DateRange(
	  /** The earliest usage date time (inclusive). If you use time groupings (daily, weekly, etc), each group uses midnight to midnight (Pacific time). The usage start date is rounded down to include all usage from the specified date. We recommend that clients pass `usage_start_date_time` in Pacific time. */
		usageStartDateTime: Option[Schema.GoogleTypeDateTime] = None,
	  /** The latest usage date time (exclusive). If you use time groupings (daily, weekly, etc), each group uses midnight to midnight (Pacific time). The usage end date is rounded down to include all usage from the specified date. We recommend that clients pass `usage_start_date_time` in Pacific time. */
		usageEndDateTime: Option[Schema.GoogleTypeDateTime] = None,
	  /** The earliest invoice date (inclusive). If this value is not the first day of a month, this will move it back to the first day of the given month. */
		invoiceStartDate: Option[Schema.GoogleTypeDate] = None,
	  /** The latest invoice date (inclusive). If this value is not the last day of a month, this will move it forward to the last day of the given month. */
		invoiceEndDate: Option[Schema.GoogleTypeDate] = None
	)
	
	case class GoogleCloudChannelV1RunReportJobResponse(
	  /** Pass `report_job.name` to FetchReportResultsRequest.report_job to retrieve the report's results. */
		reportJob: Option[Schema.GoogleCloudChannelV1ReportJob] = None,
	  /** The metadata for the report's results (display name, columns, row count, and date range). If you view this before the operation finishes, you may see incomplete data. */
		reportMetadata: Option[Schema.GoogleCloudChannelV1ReportResultsMetadata] = None
	)
	
	case class GoogleCloudChannelV1ReportJob(
	  /** Required. The resource name of a report job. Name uses the format: `accounts/{account_id}/reportJobs/{report_job_id}` */
		name: Option[String] = None,
	  /** The current status of report generation. */
		reportStatus: Option[Schema.GoogleCloudChannelV1ReportStatus] = None
	)
	
	object GoogleCloudChannelV1ReportStatus {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, STARTED, WRITING, AVAILABLE, FAILED }
	}
	case class GoogleCloudChannelV1ReportStatus(
	  /** The current state of the report generation process. */
		state: Option[Schema.GoogleCloudChannelV1ReportStatus.StateEnum] = None,
	  /** The report generation's start time. */
		startTime: Option[String] = None,
	  /** The report generation's completion time. */
		endTime: Option[String] = None
	)
}
