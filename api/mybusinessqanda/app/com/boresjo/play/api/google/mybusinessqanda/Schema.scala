package com.boresjo.play.api.google.mybusinessqanda

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	case class ListQuestionsResponse(
	  /** The requested questions, */
		questions: Option[List[Schema.Question]] = None,
	  /** The total number of questions posted for this location across all pages. */
		totalSize: Option[Int] = None,
	  /** If the number of questions exceeds the requested max page size, this field is populated with a token to fetch the next page of questions on a subsequent call. If there are no more questions, this field is not present in the response. */
		nextPageToken: Option[String] = None
	)
	
	case class Question(
	  /** Immutable. The unique name for the question. locations/&#42;/questions/&#42; This field will be ignored if set during question creation. */
		name: Option[String] = None,
	  /** Output only. The author of the question. */
		author: Option[Schema.Author] = None,
	  /** Output only. The number of upvotes for the question. */
		upvoteCount: Option[Int] = None,
	  /** Required. The text of the question. It should contain at least three words and the total length should be greater than or equal to 10 characters. The maximum length is 4096 characters. */
		text: Option[String] = None,
	  /** Output only. The timestamp for when the question was written. */
		createTime: Option[String] = None,
	  /** Output only. The timestamp for when the question was last modified. */
		updateTime: Option[String] = None,
	  /** Output only. A list of answers to the question, sorted by upvotes. This may not be a complete list of answers depending on the request parameters (answers_per_question) */
		topAnswers: Option[List[Schema.Answer]] = None,
	  /** Output only. The total number of answers posted for this question. */
		totalAnswerCount: Option[Int] = None
	)
	
	object Author {
		enum TypeEnum extends Enum[TypeEnum] { case AUTHOR_TYPE_UNSPECIFIED, REGULAR_USER, LOCAL_GUIDE, MERCHANT }
	}
	case class Author(
	  /** The display name of the user */
		displayName: Option[String] = None,
	  /** The profile photo URI of the user. */
		profilePhotoUri: Option[String] = None,
	  /** The type of user the author is. */
		`type`: Option[Schema.Author.TypeEnum] = None
	)
	
	case class Answer(
	  /** Output only. The unique name for the answer locations/&#42;/questions/&#42;/answers/&#42; */
		name: Option[String] = None,
	  /** Output only. The author of the answer. Will only be set during list operations. */
		author: Option[Schema.Author] = None,
	  /** Output only. The number of upvotes for the answer. */
		upvoteCount: Option[Int] = None,
	  /** Required. The text of the answer. It should contain at least one non-whitespace character. The maximum length is 4096 characters. */
		text: Option[String] = None,
	  /** Output only. The timestamp for when the answer was written. Only retrieved during ListResponse fetching. */
		createTime: Option[String] = None,
	  /** Output only. The timestamp for when the answer was last modified. */
		updateTime: Option[String] = None
	)
	
	case class ListAnswersResponse(
	  /** The requested answers. */
		answers: Option[List[Schema.Answer]] = None,
	  /** The total number of answers posted for this question across all pages. */
		totalSize: Option[Int] = None,
	  /** If the number of answers exceeds the requested max page size, this field is populated with a token to fetch the next page of answers on a subsequent call. If there are no more answers, this field is not present in the response. */
		nextPageToken: Option[String] = None
	)
	
	case class Empty(
	
	)
	
	case class UpsertAnswerRequest(
	  /** Required. The new answer. */
		answer: Option[Schema.Answer] = None
	)
}
