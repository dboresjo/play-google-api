package com.boresjo.play.api.google.merchantapi

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Formats {
	given fmtMerchantReview: Format[Schema.MerchantReview] = Json.format[Schema.MerchantReview]
	given fmtMerchantReviewAttributes: Format[Schema.MerchantReviewAttributes] = Json.format[Schema.MerchantReviewAttributes]
	given fmtCustomAttribute: Format[Schema.CustomAttribute] = Json.format[Schema.CustomAttribute]
	given fmtMerchantReviewStatus: Format[Schema.MerchantReviewStatus] = Json.format[Schema.MerchantReviewStatus]
	given fmtMerchantReviewAttributesCollectionMethodEnum: Format[Schema.MerchantReviewAttributes.CollectionMethodEnum] = JsonEnumFormat[Schema.MerchantReviewAttributes.CollectionMethodEnum]
	given fmtMerchantReviewDestinationStatus: Format[Schema.MerchantReviewDestinationStatus] = Json.format[Schema.MerchantReviewDestinationStatus]
	given fmtMerchantReviewItemLevelIssue: Format[Schema.MerchantReviewItemLevelIssue] = Json.format[Schema.MerchantReviewItemLevelIssue]
	given fmtMerchantReviewDestinationStatusReportingContextEnum: Format[Schema.MerchantReviewDestinationStatus.ReportingContextEnum] = JsonEnumFormat[Schema.MerchantReviewDestinationStatus.ReportingContextEnum]
	given fmtMerchantReviewItemLevelIssueSeverityEnum: Format[Schema.MerchantReviewItemLevelIssue.SeverityEnum] = JsonEnumFormat[Schema.MerchantReviewItemLevelIssue.SeverityEnum]
	given fmtMerchantReviewItemLevelIssueReportingContextEnum: Format[Schema.MerchantReviewItemLevelIssue.ReportingContextEnum] = JsonEnumFormat[Schema.MerchantReviewItemLevelIssue.ReportingContextEnum]
	given fmtListMerchantReviewsResponse: Format[Schema.ListMerchantReviewsResponse] = Json.format[Schema.ListMerchantReviewsResponse]
	given fmtEmpty: Format[Schema.Empty] = Json.format[Schema.Empty]
	given fmtProductReview: Format[Schema.ProductReview] = Json.format[Schema.ProductReview]
	given fmtProductReviewAttributes: Format[Schema.ProductReviewAttributes] = Json.format[Schema.ProductReviewAttributes]
	given fmtProductReviewStatus: Format[Schema.ProductReviewStatus] = Json.format[Schema.ProductReviewStatus]
	given fmtReviewLink: Format[Schema.ReviewLink] = Json.format[Schema.ReviewLink]
	given fmtProductReviewAttributesCollectionMethodEnum: Format[Schema.ProductReviewAttributes.CollectionMethodEnum] = JsonEnumFormat[Schema.ProductReviewAttributes.CollectionMethodEnum]
	given fmtReviewLinkTypeEnum: Format[Schema.ReviewLink.TypeEnum] = JsonEnumFormat[Schema.ReviewLink.TypeEnum]
	given fmtProductReviewDestinationStatus: Format[Schema.ProductReviewDestinationStatus] = Json.format[Schema.ProductReviewDestinationStatus]
	given fmtProductReviewItemLevelIssue: Format[Schema.ProductReviewItemLevelIssue] = Json.format[Schema.ProductReviewItemLevelIssue]
	given fmtProductReviewDestinationStatusReportingContextEnum: Format[Schema.ProductReviewDestinationStatus.ReportingContextEnum] = JsonEnumFormat[Schema.ProductReviewDestinationStatus.ReportingContextEnum]
	given fmtProductReviewItemLevelIssueSeverityEnum: Format[Schema.ProductReviewItemLevelIssue.SeverityEnum] = JsonEnumFormat[Schema.ProductReviewItemLevelIssue.SeverityEnum]
	given fmtProductReviewItemLevelIssueReportingContextEnum: Format[Schema.ProductReviewItemLevelIssue.ReportingContextEnum] = JsonEnumFormat[Schema.ProductReviewItemLevelIssue.ReportingContextEnum]
	given fmtListProductReviewsResponse: Format[Schema.ListProductReviewsResponse] = Json.format[Schema.ListProductReviewsResponse]
	given fmtProductStatusChangeMessage: Format[Schema.ProductStatusChangeMessage] = Json.format[Schema.ProductStatusChangeMessage]
	given fmtProductStatusChangeMessageResourceTypeEnum: Format[Schema.ProductStatusChangeMessage.ResourceTypeEnum] = JsonEnumFormat[Schema.ProductStatusChangeMessage.ResourceTypeEnum]
	given fmtProductStatusChangeMessageAttributeEnum: Format[Schema.ProductStatusChangeMessage.AttributeEnum] = JsonEnumFormat[Schema.ProductStatusChangeMessage.AttributeEnum]
	given fmtProductChange: Format[Schema.ProductChange] = Json.format[Schema.ProductChange]
	given fmtProductChangeReportingContextEnum: Format[Schema.ProductChange.ReportingContextEnum] = JsonEnumFormat[Schema.ProductChange.ReportingContextEnum]
}
