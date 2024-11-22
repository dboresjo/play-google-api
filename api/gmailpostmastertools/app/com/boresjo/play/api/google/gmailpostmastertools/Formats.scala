package com.boresjo.play.api.google.gmailpostmastertools

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Formats {
	given fmtTrafficStats: Format[Schema.TrafficStats] = Json.format[Schema.TrafficStats]
	given fmtTrafficStatsDomainReputationEnum: Format[Schema.TrafficStats.DomainReputationEnum] = JsonEnumFormat[Schema.TrafficStats.DomainReputationEnum]
	given fmtIpReputation: Format[Schema.IpReputation] = Json.format[Schema.IpReputation]
	given fmtDeliveryError: Format[Schema.DeliveryError] = Json.format[Schema.DeliveryError]
	given fmtFeedbackLoop: Format[Schema.FeedbackLoop] = Json.format[Schema.FeedbackLoop]
	given fmtDeliveryErrorErrorTypeEnum: Format[Schema.DeliveryError.ErrorTypeEnum] = JsonEnumFormat[Schema.DeliveryError.ErrorTypeEnum]
	given fmtDeliveryErrorErrorClassEnum: Format[Schema.DeliveryError.ErrorClassEnum] = JsonEnumFormat[Schema.DeliveryError.ErrorClassEnum]
	given fmtListDomainsResponse: Format[Schema.ListDomainsResponse] = Json.format[Schema.ListDomainsResponse]
	given fmtDomain: Format[Schema.Domain] = Json.format[Schema.Domain]
	given fmtListTrafficStatsResponse: Format[Schema.ListTrafficStatsResponse] = Json.format[Schema.ListTrafficStatsResponse]
	given fmtIpReputationReputationEnum: Format[Schema.IpReputation.ReputationEnum] = JsonEnumFormat[Schema.IpReputation.ReputationEnum]
	given fmtDomainPermissionEnum: Format[Schema.Domain.PermissionEnum] = JsonEnumFormat[Schema.Domain.PermissionEnum]
}
