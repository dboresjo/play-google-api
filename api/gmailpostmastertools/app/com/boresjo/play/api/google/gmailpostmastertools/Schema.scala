package com.boresjo.play.api.google.gmailpostmastertools

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	object TrafficStats {
		enum DomainReputationEnum extends Enum[DomainReputationEnum] { case REPUTATION_CATEGORY_UNSPECIFIED, HIGH, MEDIUM, LOW, BAD }
	}
	case class TrafficStats(
	  /** Reputation of the domain. */
		domainReputation: Option[Schema.TrafficStats.DomainReputationEnum] = None,
	  /** The resource name of the traffic statistics. Traffic statistic names have the form `domains/{domain}/trafficStats/{date}`, where domain_name is the fully qualified domain name (i.e., mymail.mydomain.com) of the domain this traffic statistics pertains to and date is the date in yyyymmdd format that these statistics corresponds to. For example: domains/mymail.mydomain.com/trafficStats/20160807 */
		name: Option[String] = None,
	  /** The ratio of mail that passed [DMARC](https://dmarc.org/) alignment checks vs all mail received from the domain that successfully authenticated with either of [SPF](http://www.openspf.org/) or [DKIM](http://www.dkim.org/). */
		dmarcSuccessRatio: Option[BigDecimal] = None,
	  /** The ratio of mail that successfully authenticated with DKIM vs. all mail that attempted to authenticate with [DKIM](http://www.dkim.org/). Spoofed mail is excluded. */
		dkimSuccessRatio: Option[BigDecimal] = None,
	  /** Reputation information pertaining to the IP addresses of the email servers for the domain. There is exactly one entry for each reputation category except REPUTATION_CATEGORY_UNSPECIFIED. */
		ipReputations: Option[List[Schema.IpReputation]] = None,
	  /** The ratio of user-report spam vs. email that was sent to the inbox. This is potentially inexact -- users may want to refer to the description of the interval fields userReportedSpamRatioLowerBound and userReportedSpamRatioUpperBound for more explicit accuracy guarantees. This metric only pertains to emails authenticated by [DKIM](http://www.dkim.org/). */
		userReportedSpamRatio: Option[BigDecimal] = None,
	  /** The upper bound of the confidence interval for the user reported spam ratio. If this field is set, then the value of userReportedSpamRatio is set to the midpoint of this interval and is thus inexact. However, the true ratio is guaranteed to be in between this upper bound and the corresponding lower bound 95% of the time. This metric only pertains to emails authenticated by [DKIM](http://www.dkim.org/). */
		userReportedSpamRatioUpperBound: Option[BigDecimal] = None,
	  /** Delivery errors for the domain. This metric only pertains to traffic that passed [SPF](http://www.openspf.org/) or [DKIM](http://www.dkim.org/). */
		deliveryErrors: Option[List[Schema.DeliveryError]] = None,
	  /** The ratio of mail that successfully authenticated with SPF vs. all mail that attempted to authenticate with [SPF](http://www.openspf.org/). Spoofed mail is excluded. */
		spfSuccessRatio: Option[BigDecimal] = None,
	  /** Spammy [Feedback loop identifiers] (https://support.google.com/mail/answer/6254652) with their individual spam rates. This metric only pertains to traffic that is authenticated by [DKIM](http://www.dkim.org/). */
		spammyFeedbackLoops: Option[List[Schema.FeedbackLoop]] = None,
	  /** The ratio of incoming mail (to Gmail), that passed secure transport (TLS) vs all mail received from that domain. This metric only pertains to traffic that passed [SPF](http://www.openspf.org/) or [DKIM](http://www.dkim.org/). */
		inboundEncryptionRatio: Option[BigDecimal] = None,
	  /** The lower bound of the confidence interval for the user reported spam ratio. If this field is set, then the value of userReportedSpamRatio is set to the midpoint of this interval and is thus inexact. However, the true ratio is guaranteed to be in between this lower bound and the corresponding upper bound 95% of the time. This metric only pertains to emails authenticated by [DKIM](http://www.dkim.org/). */
		userReportedSpamRatioLowerBound: Option[BigDecimal] = None,
	  /** The ratio of outgoing mail (from Gmail) that was accepted over secure transport (TLS). */
		outboundEncryptionRatio: Option[BigDecimal] = None
	)
	
	object DeliveryError {
		enum ErrorTypeEnum extends Enum[ErrorTypeEnum] { case DELIVERY_ERROR_TYPE_UNSPECIFIED, RATE_LIMIT_EXCEEDED, SUSPECTED_SPAM, CONTENT_SPAMMY, BAD_ATTACHMENT, BAD_DMARC_POLICY, LOW_IP_REPUTATION, LOW_DOMAIN_REPUTATION, IP_IN_RBL, DOMAIN_IN_RBL, BAD_PTR_RECORD }
		enum ErrorClassEnum extends Enum[ErrorClassEnum] { case DELIVERY_ERROR_CLASS_UNSPECIFIED, PERMANENT_ERROR, TEMPORARY_ERROR }
	}
	case class DeliveryError(
	  /** The type of delivery error. */
		errorType: Option[Schema.DeliveryError.ErrorTypeEnum] = None,
	  /** The ratio of messages where the error occurred vs all authenticated traffic. */
		errorRatio: Option[BigDecimal] = None,
	  /** The class of delivery error. */
		errorClass: Option[Schema.DeliveryError.ErrorClassEnum] = None
	)
	
	case class ListDomainsResponse(
	  /** The list of domains. */
		domains: Option[List[Schema.Domain]] = None,
	  /** Token to retrieve the next page of results, or empty if there are no more results in the list. */
		nextPageToken: Option[String] = None
	)
	
	case class ListTrafficStatsResponse(
	  /** The list of TrafficStats. */
		trafficStats: Option[List[Schema.TrafficStats]] = None,
	  /** Token to retrieve the next page of results, or empty if there are no more results in the list. */
		nextPageToken: Option[String] = None
	)
	
	object IpReputation {
		enum ReputationEnum extends Enum[ReputationEnum] { case REPUTATION_CATEGORY_UNSPECIFIED, HIGH, MEDIUM, LOW, BAD }
	}
	case class IpReputation(
	  /** The reputation category this IP reputation represents. */
		reputation: Option[Schema.IpReputation.ReputationEnum] = None,
	  /** Total number of unique IPs in this reputation category. This metric only pertains to traffic that passed [SPF](http://www.openspf.org/) or [DKIM](http://www.dkim.org/). */
		ipCount: Option[String] = None,
	  /** A sample of IPs in this reputation category. */
		sampleIps: Option[List[String]] = None
	)
	
	case class FeedbackLoop(
	  /** Feedback loop identifier that uniquely identifies individual campaigns. */
		id: Option[String] = None,
	  /** The ratio of user marked spam messages with the identifier vs the total number of inboxed messages with that identifier. */
		spamRatio: Option[BigDecimal] = None
	)
	
	object Domain {
		enum PermissionEnum extends Enum[PermissionEnum] { case PERMISSION_UNSPECIFIED, OWNER, READER, NONE }
	}
	case class Domain(
	  /** User’s permission for this domain. Assigned by the server. */
		permission: Option[Schema.Domain.PermissionEnum] = None,
	  /** Timestamp when the user registered this domain. Assigned by the server. */
		createTime: Option[String] = None,
	  /** The resource name of the Domain. Domain names have the form `domains/{domain_name}`, where domain_name is the fully qualified domain name (i.e., mymail.mydomain.com). */
		name: Option[String] = None
	)
}
