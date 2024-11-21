package com.boresjo.play.api.google.merchantapi

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Helpers {
	import Schema.*
	object AutoOption {
		given putString: Conversion[String, Option[String]] = (fun: String) => Option(fun)
		given putSchemaMerchantReviewAttributes: Conversion[Schema.MerchantReviewAttributes, Option[Schema.MerchantReviewAttributes]] = (fun: Schema.MerchantReviewAttributes) => Option(fun)
		given putListSchemaCustomAttribute: Conversion[List[Schema.CustomAttribute], Option[List[Schema.CustomAttribute]]] = (fun: List[Schema.CustomAttribute]) => Option(fun)
		given putSchemaMerchantReviewStatus: Conversion[Schema.MerchantReviewStatus, Option[Schema.MerchantReviewStatus]] = (fun: Schema.MerchantReviewStatus) => Option(fun)
		given putBigDecimal: Conversion[BigDecimal, Option[BigDecimal]] = (fun: BigDecimal) => Option(fun)
		given putBoolean: Conversion[Boolean, Option[Boolean]] = (fun: Boolean) => Option(fun)
		given putSchemaMerchantReviewAttributesCollectionMethodEnum: Conversion[Schema.MerchantReviewAttributes.CollectionMethodEnum, Option[Schema.MerchantReviewAttributes.CollectionMethodEnum]] = (fun: Schema.MerchantReviewAttributes.CollectionMethodEnum) => Option(fun)
		given putListSchemaMerchantReviewDestinationStatus: Conversion[List[Schema.MerchantReviewDestinationStatus], Option[List[Schema.MerchantReviewDestinationStatus]]] = (fun: List[Schema.MerchantReviewDestinationStatus]) => Option(fun)
		given putListSchemaMerchantReviewItemLevelIssue: Conversion[List[Schema.MerchantReviewItemLevelIssue], Option[List[Schema.MerchantReviewItemLevelIssue]]] = (fun: List[Schema.MerchantReviewItemLevelIssue]) => Option(fun)
		given putSchemaMerchantReviewDestinationStatusReportingContextEnum: Conversion[Schema.MerchantReviewDestinationStatus.ReportingContextEnum, Option[Schema.MerchantReviewDestinationStatus.ReportingContextEnum]] = (fun: Schema.MerchantReviewDestinationStatus.ReportingContextEnum) => Option(fun)
		given putSchemaMerchantReviewItemLevelIssueSeverityEnum: Conversion[Schema.MerchantReviewItemLevelIssue.SeverityEnum, Option[Schema.MerchantReviewItemLevelIssue.SeverityEnum]] = (fun: Schema.MerchantReviewItemLevelIssue.SeverityEnum) => Option(fun)
		given putSchemaMerchantReviewItemLevelIssueReportingContextEnum: Conversion[Schema.MerchantReviewItemLevelIssue.ReportingContextEnum, Option[Schema.MerchantReviewItemLevelIssue.ReportingContextEnum]] = (fun: Schema.MerchantReviewItemLevelIssue.ReportingContextEnum) => Option(fun)
		given putListSchemaMerchantReview: Conversion[List[Schema.MerchantReview], Option[List[Schema.MerchantReview]]] = (fun: List[Schema.MerchantReview]) => Option(fun)
		given putSchemaProductReviewAttributes: Conversion[Schema.ProductReviewAttributes, Option[Schema.ProductReviewAttributes]] = (fun: Schema.ProductReviewAttributes) => Option(fun)
		given putSchemaProductReviewStatus: Conversion[Schema.ProductReviewStatus, Option[Schema.ProductReviewStatus]] = (fun: Schema.ProductReviewStatus) => Option(fun)
		given putListString: Conversion[List[String], Option[List[String]]] = (fun: List[String]) => Option(fun)
		given putSchemaReviewLink: Conversion[Schema.ReviewLink, Option[Schema.ReviewLink]] = (fun: Schema.ReviewLink) => Option(fun)
		given putSchemaProductReviewAttributesCollectionMethodEnum: Conversion[Schema.ProductReviewAttributes.CollectionMethodEnum, Option[Schema.ProductReviewAttributes.CollectionMethodEnum]] = (fun: Schema.ProductReviewAttributes.CollectionMethodEnum) => Option(fun)
		given putSchemaReviewLinkTypeEnum: Conversion[Schema.ReviewLink.TypeEnum, Option[Schema.ReviewLink.TypeEnum]] = (fun: Schema.ReviewLink.TypeEnum) => Option(fun)
		given putListSchemaProductReviewDestinationStatus: Conversion[List[Schema.ProductReviewDestinationStatus], Option[List[Schema.ProductReviewDestinationStatus]]] = (fun: List[Schema.ProductReviewDestinationStatus]) => Option(fun)
		given putListSchemaProductReviewItemLevelIssue: Conversion[List[Schema.ProductReviewItemLevelIssue], Option[List[Schema.ProductReviewItemLevelIssue]]] = (fun: List[Schema.ProductReviewItemLevelIssue]) => Option(fun)
		given putSchemaProductReviewDestinationStatusReportingContextEnum: Conversion[Schema.ProductReviewDestinationStatus.ReportingContextEnum, Option[Schema.ProductReviewDestinationStatus.ReportingContextEnum]] = (fun: Schema.ProductReviewDestinationStatus.ReportingContextEnum) => Option(fun)
		given putSchemaProductReviewItemLevelIssueSeverityEnum: Conversion[Schema.ProductReviewItemLevelIssue.SeverityEnum, Option[Schema.ProductReviewItemLevelIssue.SeverityEnum]] = (fun: Schema.ProductReviewItemLevelIssue.SeverityEnum) => Option(fun)
		given putSchemaProductReviewItemLevelIssueReportingContextEnum: Conversion[Schema.ProductReviewItemLevelIssue.ReportingContextEnum, Option[Schema.ProductReviewItemLevelIssue.ReportingContextEnum]] = (fun: Schema.ProductReviewItemLevelIssue.ReportingContextEnum) => Option(fun)
		given putListSchemaProductReview: Conversion[List[Schema.ProductReview], Option[List[Schema.ProductReview]]] = (fun: List[Schema.ProductReview]) => Option(fun)
		given putSchemaProductStatusChangeMessageResourceTypeEnum: Conversion[Schema.ProductStatusChangeMessage.ResourceTypeEnum, Option[Schema.ProductStatusChangeMessage.ResourceTypeEnum]] = (fun: Schema.ProductStatusChangeMessage.ResourceTypeEnum) => Option(fun)
		given putSchemaProductStatusChangeMessageAttributeEnum: Conversion[Schema.ProductStatusChangeMessage.AttributeEnum, Option[Schema.ProductStatusChangeMessage.AttributeEnum]] = (fun: Schema.ProductStatusChangeMessage.AttributeEnum) => Option(fun)
		given putListSchemaProductChange: Conversion[List[Schema.ProductChange], Option[List[Schema.ProductChange]]] = (fun: List[Schema.ProductChange]) => Option(fun)
		given putSchemaProductChangeReportingContextEnum: Conversion[Schema.ProductChange.ReportingContextEnum, Option[Schema.ProductChange.ReportingContextEnum]] = (fun: Schema.ProductChange.ReportingContextEnum) => Option(fun)
	}
	object OptionDefault {
		given getString: Conversion[Option[String], String] = (fun: Option[String]) => fun.getOrElse("")
	}
}
