package com.boresjo.play.api.google.localservices

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	case class GoogleAdsHomeservicesLocalservicesV1AccountReport(
	  /** Unique identifier of the GLS account. */
		accountId: Option[String] = None,
	  /** Average review rating score from 1-5 stars. */
		averageFiveStarRating: Option[BigDecimal] = None,
	  /** Number of connected phone calls (duration over 30s) in current specified period. */
		currentPeriodConnectedPhoneCalls: Option[String] = None,
	  /** Number of charged leads the account received in current specified period. */
		currentPeriodChargedLeads: Option[String] = None,
	  /** Number of phone calls in current specified period, including both connected and unconnected calls. */
		currentPeriodPhoneCalls: Option[String] = None,
	  /** Total number of reviews the account has up to current date. */
		totalReview: Option[Int] = None,
	  /** Phone lead responsiveness of the account for the past 90 days from current date. This is computed by taking the total number of connected calls from charged phone leads and dividing by the total number of calls received. */
		phoneLeadResponsiveness: Option[BigDecimal] = None,
	  /** Currency code of the account. */
		currencyCode: Option[String] = None,
	  /** Average weekly budget in the currency code of the account. */
		averageWeeklyBudget: Option[BigDecimal] = None,
	  /** Aggregator specific information related to the account. */
		aggregatorInfo: Option[Schema.GoogleAdsHomeservicesLocalservicesV1AggregatorInfo] = None,
	  /** Number of charged leads the account received in previous specified period. */
		previousPeriodChargedLeads: Option[String] = None,
	  /** Number of impressions that customers have had in the past 2 days. */
		impressionsLastTwoDays: Option[String] = None,
	  /** Number of phone calls in previous specified period, including both connected and unconnected calls. */
		previousPeriodPhoneCalls: Option[String] = None,
	  /** Business name of the account. */
		businessName: Option[String] = None,
	  /** Total cost of the account in previous specified period in the account's specified currency. */
		previousPeriodTotalCost: Option[BigDecimal] = None,
	  /** Total cost of the account in current specified period in the account's specified currency. */
		currentPeriodTotalCost: Option[BigDecimal] = None,
	  /** Number of connected phone calls (duration over 30s) in previous specified period. */
		previousPeriodConnectedPhoneCalls: Option[String] = None
	)
	
	case class GoogleAdsHomeservicesLocalservicesV1PhoneLead(
	  /** Duration of the charged phone call in seconds. */
		chargedConnectedCallDurationSeconds: Option[String] = None,
	  /** Consumer phone number associated with the phone lead. */
		consumerPhoneNumber: Option[String] = None,
	  /** Timestamp of the phone call which resulted in a charged phone lead. */
		chargedCallTimestamp: Option[String] = None
	)
	
	case class GoogleAdsHomeservicesLocalservicesV1MessageLead(
	  /** Name of the customer who created the lead. */
		customerName: Option[String] = None,
	  /** Consumer phone number associated with the message lead. */
		consumerPhoneNumber: Option[String] = None,
	  /** The postal code of the customer who created the lead. */
		postalCode: Option[String] = None,
	  /** The job type of the specified lead. */
		jobType: Option[String] = None
	)
	
	case class GoogleAdsHomeservicesLocalservicesV1SearchDetailedLeadReportsResponse(
	  /** List of detailed lead reports uniquely identified by external lead id. */
		detailedLeadReports: Option[List[Schema.GoogleAdsHomeservicesLocalservicesV1DetailedLeadReport]] = None,
	  /** Pagination token to retrieve the next page of results. When `next_page_token` is not filled in, there is no next page and the list returned is the last page in the result set. */
		nextPageToken: Option[String] = None
	)
	
	case class GoogleTypeTimeZone(
	  /** Optional. IANA Time Zone Database version number, e.g. "2019a". */
		version: Option[String] = None,
	  /** IANA Time Zone Database time zone, e.g. "America/New_York". */
		id: Option[String] = None
	)
	
	case class GoogleAdsHomeservicesLocalservicesV1AggregatorInfo(
	  /** Provider id (listed in aggregator system) which maps to a account id in GLS system. */
		aggregatorProviderId: Option[String] = None
	)
	
	case class GoogleAdsHomeservicesLocalservicesV1SearchAccountReportsResponse(
	  /** List of account reports which maps 1:1 to a particular linked GLS account. */
		accountReports: Option[List[Schema.GoogleAdsHomeservicesLocalservicesV1AccountReport]] = None,
	  /** Pagination token to retrieve the next page of results. When `next_page_token` is not filled in, there is no next page and the list returned is the last page in the result set. */
		nextPageToken: Option[String] = None
	)
	
	object GoogleAdsHomeservicesLocalservicesV1DetailedLeadReport {
		enum ChargeStatusEnum extends Enum[ChargeStatusEnum] { case CHARGE_STATUS_UNSPECIFIED, CHARGED, NOT_CHARGED }
		enum LeadTypeEnum extends Enum[LeadTypeEnum] { case LEAD_TYPE_UNSPECIFIED, MESSAGE, PHONE_CALL, BOOKING }
	}
	case class GoogleAdsHomeservicesLocalservicesV1DetailedLeadReport(
	  /** Unique identifier of a Detailed Lead Report. */
		googleAdsLeadId: Option[String] = None,
	  /** Timezone of the particular provider associated to a lead. */
		timezone: Option[Schema.GoogleTypeTimeZone] = None,
	  /** Whether the lead has been charged. */
		chargeStatus: Option[Schema.GoogleAdsHomeservicesLocalservicesV1DetailedLeadReport.ChargeStatusEnum] = None,
	  /** Currency code. */
		currencyCode: Option[String] = None,
	  /** More information associated to only message leads. */
		messageLead: Option[Schema.GoogleAdsHomeservicesLocalservicesV1MessageLead] = None,
	  /** Timestamp of when the lead was created. */
		leadCreationTimestamp: Option[String] = None,
	  /** Dispute status related to the lead. */
		disputeStatus: Option[String] = None,
	  /** Aggregator specific information related to the lead. */
		aggregatorInfo: Option[Schema.GoogleAdsHomeservicesLocalservicesV1AggregatorInfo] = None,
	  /** More information associated to only phone leads. */
		phoneLead: Option[Schema.GoogleAdsHomeservicesLocalservicesV1PhoneLead] = None,
	  /** More information associated to only booking leads. */
		bookingLead: Option[Schema.GoogleAdsHomeservicesLocalservicesV1BookingLead] = None,
	  /** Lead category (e.g. hvac, plumber) */
		leadCategory: Option[String] = None,
	  /** Location of the associated account's home city. */
		geo: Option[String] = None,
	  /** Lead type. */
		leadType: Option[Schema.GoogleAdsHomeservicesLocalservicesV1DetailedLeadReport.LeadTypeEnum] = None,
	  /** Identifies account that received the lead. */
		accountId: Option[String] = None,
	  /** Deprecated in favor of google_ads_lead_id. Unique identifier of a Detailed Lead Report. */
		leadId: Option[String] = None,
	  /** Business name associated to the account. */
		businessName: Option[String] = None,
	  /** Price of the lead (available only after it has been charged). */
		leadPrice: Option[BigDecimal] = None
	)
	
	case class GoogleAdsHomeservicesLocalservicesV1BookingLead(
	  /** The job type of the specified lead. */
		jobType: Option[String] = None,
	  /** Timestamp of when service is provided by advertiser. */
		bookingAppointmentTimestamp: Option[String] = None,
	  /** Consumer phone number associated with the booking lead. */
		consumerPhoneNumber: Option[String] = None,
	  /** Name of the customer who created the lead. */
		customerName: Option[String] = None,
	  /** Consumer email associated with the booking lead. */
		consumerEmail: Option[String] = None
	)
}
