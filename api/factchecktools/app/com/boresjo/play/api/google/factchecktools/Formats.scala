package com.boresjo.play.api.google.factchecktools

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Formats {
	given fmtGoogleProtobufEmpty: Format[Schema.GoogleProtobufEmpty] = Json.format[Schema.GoogleProtobufEmpty]
	given fmtGoogleFactcheckingFactchecktoolsV1alpha1ClaimReview: Format[Schema.GoogleFactcheckingFactchecktoolsV1alpha1ClaimReview] = Json.format[Schema.GoogleFactcheckingFactchecktoolsV1alpha1ClaimReview]
	given fmtGoogleFactcheckingFactchecktoolsV1alpha1Publisher: Format[Schema.GoogleFactcheckingFactchecktoolsV1alpha1Publisher] = Json.format[Schema.GoogleFactcheckingFactchecktoolsV1alpha1Publisher]
	given fmtGoogleFactcheckingFactchecktoolsV1alpha1Claim: Format[Schema.GoogleFactcheckingFactchecktoolsV1alpha1Claim] = Json.format[Schema.GoogleFactcheckingFactchecktoolsV1alpha1Claim]
	given fmtGoogleFactcheckingFactchecktoolsV1alpha1ClaimReviewMarkup: Format[Schema.GoogleFactcheckingFactchecktoolsV1alpha1ClaimReviewMarkup] = Json.format[Schema.GoogleFactcheckingFactchecktoolsV1alpha1ClaimReviewMarkup]
	given fmtGoogleFactcheckingFactchecktoolsV1alpha1ClaimAuthor: Format[Schema.GoogleFactcheckingFactchecktoolsV1alpha1ClaimAuthor] = Json.format[Schema.GoogleFactcheckingFactchecktoolsV1alpha1ClaimAuthor]
	given fmtGoogleFactcheckingFactchecktoolsV1alpha1ClaimRating: Format[Schema.GoogleFactcheckingFactchecktoolsV1alpha1ClaimRating] = Json.format[Schema.GoogleFactcheckingFactchecktoolsV1alpha1ClaimRating]
	given fmtGoogleFactcheckingFactchecktoolsV1alpha1ClaimReviewAuthor: Format[Schema.GoogleFactcheckingFactchecktoolsV1alpha1ClaimReviewAuthor] = Json.format[Schema.GoogleFactcheckingFactchecktoolsV1alpha1ClaimReviewAuthor]
	given fmtGoogleFactcheckingFactchecktoolsV1alpha1FactCheckedClaimSearchResponse: Format[Schema.GoogleFactcheckingFactchecktoolsV1alpha1FactCheckedClaimSearchResponse] = Json.format[Schema.GoogleFactcheckingFactchecktoolsV1alpha1FactCheckedClaimSearchResponse]
	given fmtGoogleFactcheckingFactchecktoolsV1alpha1FactCheckedClaimImageSearchResponseResult: Format[Schema.GoogleFactcheckingFactchecktoolsV1alpha1FactCheckedClaimImageSearchResponseResult] = Json.format[Schema.GoogleFactcheckingFactchecktoolsV1alpha1FactCheckedClaimImageSearchResponseResult]
	given fmtGoogleFactcheckingFactchecktoolsV1alpha1ClaimReviewMarkupPage: Format[Schema.GoogleFactcheckingFactchecktoolsV1alpha1ClaimReviewMarkupPage] = Json.format[Schema.GoogleFactcheckingFactchecktoolsV1alpha1ClaimReviewMarkupPage]
	given fmtGoogleFactcheckingFactchecktoolsV1alpha1FactCheckedClaimImageSearchResponse: Format[Schema.GoogleFactcheckingFactchecktoolsV1alpha1FactCheckedClaimImageSearchResponse] = Json.format[Schema.GoogleFactcheckingFactchecktoolsV1alpha1FactCheckedClaimImageSearchResponse]
	given fmtGoogleFactcheckingFactchecktoolsV1alpha1ListClaimReviewMarkupPagesResponse: Format[Schema.GoogleFactcheckingFactchecktoolsV1alpha1ListClaimReviewMarkupPagesResponse] = Json.format[Schema.GoogleFactcheckingFactchecktoolsV1alpha1ListClaimReviewMarkupPagesResponse]
}
