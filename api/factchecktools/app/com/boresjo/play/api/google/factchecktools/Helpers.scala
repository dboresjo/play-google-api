package com.boresjo.play.api.google.factchecktools

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Helpers {
	import Schema.*
	object AutoOption {
		given putString: Conversion[String, Option[String]] = (fun: String) => Option(fun)
		given putSchemaGoogleFactcheckingFactchecktoolsV1alpha1Publisher: Conversion[Schema.GoogleFactcheckingFactchecktoolsV1alpha1Publisher, Option[Schema.GoogleFactcheckingFactchecktoolsV1alpha1Publisher]] = (fun: Schema.GoogleFactcheckingFactchecktoolsV1alpha1Publisher) => Option(fun)
		given putListSchemaGoogleFactcheckingFactchecktoolsV1alpha1ClaimReview: Conversion[List[Schema.GoogleFactcheckingFactchecktoolsV1alpha1ClaimReview], Option[List[Schema.GoogleFactcheckingFactchecktoolsV1alpha1ClaimReview]]] = (fun: List[Schema.GoogleFactcheckingFactchecktoolsV1alpha1ClaimReview]) => Option(fun)
		given putListString: Conversion[List[String], Option[List[String]]] = (fun: List[String]) => Option(fun)
		given putSchemaGoogleFactcheckingFactchecktoolsV1alpha1ClaimAuthor: Conversion[Schema.GoogleFactcheckingFactchecktoolsV1alpha1ClaimAuthor, Option[Schema.GoogleFactcheckingFactchecktoolsV1alpha1ClaimAuthor]] = (fun: Schema.GoogleFactcheckingFactchecktoolsV1alpha1ClaimAuthor) => Option(fun)
		given putSchemaGoogleFactcheckingFactchecktoolsV1alpha1ClaimRating: Conversion[Schema.GoogleFactcheckingFactchecktoolsV1alpha1ClaimRating, Option[Schema.GoogleFactcheckingFactchecktoolsV1alpha1ClaimRating]] = (fun: Schema.GoogleFactcheckingFactchecktoolsV1alpha1ClaimRating) => Option(fun)
		given putListSchemaGoogleFactcheckingFactchecktoolsV1alpha1Claim: Conversion[List[Schema.GoogleFactcheckingFactchecktoolsV1alpha1Claim], Option[List[Schema.GoogleFactcheckingFactchecktoolsV1alpha1Claim]]] = (fun: List[Schema.GoogleFactcheckingFactchecktoolsV1alpha1Claim]) => Option(fun)
		given putInt: Conversion[Int, Option[Int]] = (fun: Int) => Option(fun)
		given putSchemaGoogleFactcheckingFactchecktoolsV1alpha1Claim: Conversion[Schema.GoogleFactcheckingFactchecktoolsV1alpha1Claim, Option[Schema.GoogleFactcheckingFactchecktoolsV1alpha1Claim]] = (fun: Schema.GoogleFactcheckingFactchecktoolsV1alpha1Claim) => Option(fun)
		given putListSchemaGoogleFactcheckingFactchecktoolsV1alpha1ClaimReviewMarkup: Conversion[List[Schema.GoogleFactcheckingFactchecktoolsV1alpha1ClaimReviewMarkup], Option[List[Schema.GoogleFactcheckingFactchecktoolsV1alpha1ClaimReviewMarkup]]] = (fun: List[Schema.GoogleFactcheckingFactchecktoolsV1alpha1ClaimReviewMarkup]) => Option(fun)
		given putSchemaGoogleFactcheckingFactchecktoolsV1alpha1ClaimReviewAuthor: Conversion[Schema.GoogleFactcheckingFactchecktoolsV1alpha1ClaimReviewAuthor, Option[Schema.GoogleFactcheckingFactchecktoolsV1alpha1ClaimReviewAuthor]] = (fun: Schema.GoogleFactcheckingFactchecktoolsV1alpha1ClaimReviewAuthor) => Option(fun)
		given putListSchemaGoogleFactcheckingFactchecktoolsV1alpha1FactCheckedClaimImageSearchResponseResult: Conversion[List[Schema.GoogleFactcheckingFactchecktoolsV1alpha1FactCheckedClaimImageSearchResponseResult], Option[List[Schema.GoogleFactcheckingFactchecktoolsV1alpha1FactCheckedClaimImageSearchResponseResult]]] = (fun: List[Schema.GoogleFactcheckingFactchecktoolsV1alpha1FactCheckedClaimImageSearchResponseResult]) => Option(fun)
		given putListSchemaGoogleFactcheckingFactchecktoolsV1alpha1ClaimReviewMarkupPage: Conversion[List[Schema.GoogleFactcheckingFactchecktoolsV1alpha1ClaimReviewMarkupPage], Option[List[Schema.GoogleFactcheckingFactchecktoolsV1alpha1ClaimReviewMarkupPage]]] = (fun: List[Schema.GoogleFactcheckingFactchecktoolsV1alpha1ClaimReviewMarkupPage]) => Option(fun)
	}
	object OptionDefault {
		given getString: Conversion[Option[String], String] = (fun: Option[String]) => fun.getOrElse("")
	}
}
