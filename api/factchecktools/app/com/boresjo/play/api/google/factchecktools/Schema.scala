package com.boresjo.play.api.google.factchecktools

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	case class GoogleProtobufEmpty(
	
	)
	
	case class GoogleFactcheckingFactchecktoolsV1alpha1ClaimReview(
	  /** The date the claim was reviewed. */
		reviewDate: Option[String] = None,
	  /** Textual rating. For instance, "Mostly false". */
		textualRating: Option[String] = None,
	  /** The publisher of this claim review. */
		publisher: Option[Schema.GoogleFactcheckingFactchecktoolsV1alpha1Publisher] = None,
	  /** The title of this claim review, if it can be determined. */
		title: Option[String] = None,
	  /** The language this review was written in. For instance, "en" or "de". */
		languageCode: Option[String] = None,
	  /** The URL of this claim review. */
		url: Option[String] = None
	)
	
	case class GoogleFactcheckingFactchecktoolsV1alpha1Claim(
	  /** One or more reviews of this claim (namely, a fact-checking article). */
		claimReview: Option[List[Schema.GoogleFactcheckingFactchecktoolsV1alpha1ClaimReview]] = None,
	  /** The claim text. For instance, "Crime has doubled in the last 2 years." */
		text: Option[String] = None,
	  /** A person or organization stating the claim. For instance, "John Doe". */
		claimant: Option[String] = None,
	  /** The date that the claim was made. */
		claimDate: Option[String] = None
	)
	
	case class GoogleFactcheckingFactchecktoolsV1alpha1Publisher(
	  /** The name of this publisher. For instance, "Awesome Fact Checks". */
		name: Option[String] = None,
	  /** Host-level site name, without the protocol or "www" prefix. For instance, "awesomefactchecks.com". This value of this field is based purely on the claim review URL. */
		site: Option[String] = None
	)
	
	case class GoogleFactcheckingFactchecktoolsV1alpha1ClaimReviewMarkup(
	  /** The date when the claim was made or entered public discourse. Corresponds to `ClaimReview.itemReviewed.datePublished`. */
		claimDate: Option[String] = None,
	  /** A list of links to works in which this claim appears, aside from the one specified in `claim_first_appearance`. Corresponds to `ClaimReview.itemReviewed[@type=Claim].appearance.url`. */
		claimAppearances: Option[List[String]] = None,
	  /** The location where this claim was made. Corresponds to `ClaimReview.itemReviewed.name`. */
		claimLocation: Option[String] = None,
	  /** This field is optional, and will default to the page URL. We provide this field to allow you the override the default value, but the only permitted override is the page URL plus an optional anchor link ("page jump"). Corresponds to `ClaimReview.url` */
		url: Option[String] = None,
	  /** A short summary of the claim being evaluated. Corresponds to `ClaimReview.claimReviewed`. */
		claimReviewed: Option[String] = None,
	  /** Info about the author of this claim. */
		claimAuthor: Option[Schema.GoogleFactcheckingFactchecktoolsV1alpha1ClaimAuthor] = None,
	  /** A link to a work in which this claim first appears. Corresponds to `ClaimReview.itemReviewed[@type=Claim].firstAppearance.url`. */
		claimFirstAppearance: Option[String] = None,
	  /** Info about the rating of this claim review. */
		rating: Option[Schema.GoogleFactcheckingFactchecktoolsV1alpha1ClaimRating] = None
	)
	
	case class GoogleFactcheckingFactchecktoolsV1alpha1ClaimReviewAuthor(
	  /** Name of the organization that is publishing the fact check. Corresponds to `ClaimReview.author.name`. */
		name: Option[String] = None,
	  /** Corresponds to `ClaimReview.author.image`. */
		imageUrl: Option[String] = None
	)
	
	case class GoogleFactcheckingFactchecktoolsV1alpha1FactCheckedClaimSearchResponse(
	  /** The next pagination token in the Search response. It should be used as the `page_token` for the following request. An empty value means no more results. */
		nextPageToken: Option[String] = None,
	  /** The list of claims and all of their associated information. */
		claims: Option[List[Schema.GoogleFactcheckingFactchecktoolsV1alpha1Claim]] = None
	)
	
	case class GoogleFactcheckingFactchecktoolsV1alpha1ClaimRating(
	  /** Corresponds to `ClaimReview.reviewRating.ratingExplanation`. */
		ratingExplanation: Option[String] = None,
	  /** Corresponds to `ClaimReview.reviewRating.image`. */
		imageUrl: Option[String] = None,
	  /** The truthfulness rating as a human-readible short word or phrase. Corresponds to `ClaimReview.reviewRating.alternateName`. */
		textualRating: Option[String] = None,
	  /** A numeric rating of this claim, in the range worstRating — bestRating inclusive. Corresponds to `ClaimReview.reviewRating.ratingValue`. */
		ratingValue: Option[Int] = None,
	  /** For numeric ratings, the best value possible in the scale from worst to best. Corresponds to `ClaimReview.reviewRating.bestRating`. */
		bestRating: Option[Int] = None,
	  /** For numeric ratings, the worst value possible in the scale from worst to best. Corresponds to `ClaimReview.reviewRating.worstRating`. */
		worstRating: Option[Int] = None
	)
	
	case class GoogleFactcheckingFactchecktoolsV1alpha1FactCheckedClaimImageSearchResponseResult(
	  /** A claim which matched the query. */
		claim: Option[Schema.GoogleFactcheckingFactchecktoolsV1alpha1Claim] = None
	)
	
	case class GoogleFactcheckingFactchecktoolsV1alpha1ClaimReviewMarkupPage(
	  /** The date when the fact check was published. Similar to the URL, semantically this is a page-level field, and each `ClaimReview` on this page will contain the same value. Corresponds to `ClaimReview.datePublished` */
		publishDate: Option[String] = None,
	  /** A list of individual claim reviews for this page. Each item in the list corresponds to one `ClaimReview` element. */
		claimReviewMarkups: Option[List[Schema.GoogleFactcheckingFactchecktoolsV1alpha1ClaimReviewMarkup]] = None,
	  /** The URL of the page associated with this `ClaimReview` markup. While every individual `ClaimReview` has its own URL field, semantically this is a page-level field, and each `ClaimReview` on this page will use this value unless individually overridden. Corresponds to `ClaimReview.url` */
		pageUrl: Option[String] = None,
	  /** The name of this `ClaimReview` markup page resource, in the form of `pages/{page_id}`. Except for update requests, this field is output-only and should not be set by the user. */
		name: Option[String] = None,
	  /** Info about the author of this claim review. Similar to the above, semantically these are page-level fields, and each `ClaimReview` on this page will contain the same values. */
		claimReviewAuthor: Option[Schema.GoogleFactcheckingFactchecktoolsV1alpha1ClaimReviewAuthor] = None,
	  /** The version ID for this markup. Except for update requests, this field is output-only and should not be set by the user. */
		versionId: Option[String] = None
	)
	
	case class GoogleFactcheckingFactchecktoolsV1alpha1ClaimAuthor(
	  /** A person or organization stating the claim. For instance, "John Doe". Corresponds to `ClaimReview.itemReviewed.author.name`. */
		name: Option[String] = None,
	  /** Corresponds to `ClaimReview.itemReviewed.author.image`. */
		imageUrl: Option[String] = None,
	  /** Corresponds to `ClaimReview.itemReviewed.author.sameAs`. */
		sameAs: Option[String] = None,
	  /** Corresponds to `ClaimReview.itemReviewed.author.jobTitle`. */
		jobTitle: Option[String] = None
	)
	
	case class GoogleFactcheckingFactchecktoolsV1alpha1FactCheckedClaimImageSearchResponse(
	  /** The next pagination token in the Search response. It should be used as the `page_token` for the following request. An empty value means no more results. */
		nextPageToken: Option[String] = None,
	  /** The list of claims and all of their associated information. */
		results: Option[List[Schema.GoogleFactcheckingFactchecktoolsV1alpha1FactCheckedClaimImageSearchResponseResult]] = None
	)
	
	case class GoogleFactcheckingFactchecktoolsV1alpha1ListClaimReviewMarkupPagesResponse(
	  /** The result list of pages of `ClaimReview` markup. */
		claimReviewMarkupPages: Option[List[Schema.GoogleFactcheckingFactchecktoolsV1alpha1ClaimReviewMarkupPage]] = None,
	  /** The next pagination token in the Search response. It should be used as the `page_token` for the following request. An empty value means no more results. */
		nextPageToken: Option[String] = None
	)
}
