package com.boresjo.play.api.google.classroom

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://classroom.googleapis.com/"

	object courses {
		class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withCourse(body: Schema.Course) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Course])
		}
		object create {
			def apply()(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/courses").addQueryStringParameters())
		}
		class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
			def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
		}
		object delete {
			def apply(id: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/courses/${id}").addQueryStringParameters())
			given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
		}
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Course]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Course])
		}
		object get {
			def apply(id: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/courses/${id}").addQueryStringParameters())
			given Conversion[get, Future[Schema.Course]] = (fun: get) => fun.apply()
		}
		class update(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withCourse(body: Schema.Course) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.Course])
		}
		object update {
			def apply(id: String)(using auth: AuthToken, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"v1/courses/${id}").addQueryStringParameters())
		}
		class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withCourse(body: Schema.Course) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Course])
		}
		object patch {
			def apply(id: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/courses/${id}").addQueryStringParameters("updateMask" -> updateMask.toString))
		}
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListCoursesResponse]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListCoursesResponse])
		}
		object list {
			def apply(studentId: String, teacherId: String, courseStates: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/courses").addQueryStringParameters("studentId" -> studentId.toString, "teacherId" -> teacherId.toString, "courseStates" -> courseStates.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
			given Conversion[list, Future[Schema.ListCoursesResponse]] = (fun: list) => fun.apply()
		}
		object aliases {
			class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withCourseAlias(body: Schema.CourseAlias) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.CourseAlias])
			}
			object create {
				def apply(courseId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/courses/${courseId}/aliases").addQueryStringParameters())
			}
			class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
				def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
			}
			object delete {
				def apply(courseId: String, alias: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/courses/${courseId}/aliases/${alias}").addQueryStringParameters())
				given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListCourseAliasesResponse]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListCourseAliasesResponse])
			}
			object list {
				def apply(courseId: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/courses/${courseId}/aliases").addQueryStringParameters("pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListCourseAliasesResponse]] = (fun: list) => fun.apply()
			}
		}
		object posts {
			class getAddOnContext(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.AddOnContext]) {
				/** Optional. Deprecated, use `item_id` instead. */
				def withPostId(postId: String) = new getAddOnContext(req.addQueryStringParameters("postId" -> postId.toString))
				/** Optional. Token that authorizes the request. The token is passed as a query parameter when the user is redirected from Classroom to the add-on's URL. The authorization token is required when neither of the following is true: &#42; The add-on has attachments on the post. &#42; The developer project issuing the request is the same project that created the post. */
				def withAddOnToken(addOnToken: String) = new getAddOnContext(req.addQueryStringParameters("addOnToken" -> addOnToken.toString))
				/** Optional. The identifier of the attachment. This field is required for all requests except when the user is in the [Attachment Discovery iframe](https://developers.google.com/classroom/add-ons/get-started/iframes/attachment-discovery-iframe). */
				def withAttachmentId(attachmentId: String) = new getAddOnContext(req.addQueryStringParameters("attachmentId" -> attachmentId.toString))
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.AddOnContext])
			}
			object getAddOnContext {
				def apply(postId :PlayApi, courseId: String, itemId: String)(using auth: AuthToken, ec: ExecutionContext): getAddOnContext = new getAddOnContext(ws.url(BASE_URL + s"v1/courses/${courseId}/posts/${postId}/addOnContext").addQueryStringParameters("itemId" -> itemId.toString))
				given Conversion[getAddOnContext, Future[Schema.AddOnContext]] = (fun: getAddOnContext) => fun.apply()
			}
			object addOnAttachments {
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					/** Optional. Deprecated, use `item_id` instead. */
					def withPostId(postId: String) = new create(req.addQueryStringParameters("postId" -> postId.toString))
					/** Optional. Token that authorizes the request. The token is passed as a query parameter when the user is redirected from Classroom to the add-on's URL. This authorization token is required for in-Classroom attachment creation but optional for partner-first attachment creation. Returns an error if not provided for partner-first attachment creation and the developer projects that created the attachment and its parent stream item do not match. */
					def withAddOnToken(addOnToken: String) = new create(req.addQueryStringParameters("addOnToken" -> addOnToken.toString))
					def withAddOnAttachment(body: Schema.AddOnAttachment) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.AddOnAttachment])
				}
				object create {
					def apply(postId :PlayApi, courseId: String, itemId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/courses/${courseId}/posts/${postId}/addOnAttachments").addQueryStringParameters("itemId" -> itemId.toString))
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					/** Optional. Deprecated, use `item_id` instead. */
					def withPostId(postId: String) = new delete(req.addQueryStringParameters("postId" -> postId.toString))
					def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
				}
				object delete {
					def apply(postId :PlayApi, courseId: String, attachmentId: String, itemId: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/courses/${courseId}/posts/${postId}/addOnAttachments/${attachmentId}").addQueryStringParameters("itemId" -> itemId.toString))
					given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.AddOnAttachment]) {
					/** Optional. Deprecated, use `item_id` instead. */
					def withPostId(postId: String) = new get(req.addQueryStringParameters("postId" -> postId.toString))
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.AddOnAttachment])
				}
				object get {
					def apply(postId :PlayApi, courseId: String, attachmentId: String, itemId: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/courses/${courseId}/posts/${postId}/addOnAttachments/${attachmentId}").addQueryStringParameters("itemId" -> itemId.toString))
					given Conversion[get, Future[Schema.AddOnAttachment]] = (fun: get) => fun.apply()
				}
				class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withAddOnAttachment(body: Schema.AddOnAttachment) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.AddOnAttachment])
				}
				object patch {
					def apply(courseId: String, postId: String, attachmentId: String, itemId: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/courses/${courseId}/posts/${postId}/addOnAttachments/${attachmentId}").addQueryStringParameters("itemId" -> itemId.toString, "updateMask" -> updateMask.toString))
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListAddOnAttachmentsResponse]) {
					/** Optional. Identifier of the post under the course whose attachments to enumerate. Deprecated, use `item_id` instead. */
					def withPostId(postId: String) = new list(req.addQueryStringParameters("postId" -> postId.toString))
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListAddOnAttachmentsResponse])
				}
				object list {
					def apply(postId :PlayApi, courseId: String, itemId: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/courses/${courseId}/posts/${postId}/addOnAttachments").addQueryStringParameters("itemId" -> itemId.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListAddOnAttachmentsResponse]] = (fun: list) => fun.apply()
				}
				object studentSubmissions {
					class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						/** Optional. Deprecated, use `item_id` instead. */
						def withPostId(postId: String) = new patch(req.addQueryStringParameters("postId" -> postId.toString))
						def withAddOnAttachmentStudentSubmission(body: Schema.AddOnAttachmentStudentSubmission) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.AddOnAttachmentStudentSubmission])
					}
					object patch {
						def apply(postId :PlayApi, courseId: String, attachmentId: String, submissionId: String, itemId: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/courses/${courseId}/posts/${postId}/addOnAttachments/${attachmentId}/studentSubmissions/${submissionId}").addQueryStringParameters("itemId" -> itemId.toString, "updateMask" -> updateMask.toString))
					}
					class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.AddOnAttachmentStudentSubmission]) {
						/** Optional. Deprecated, use `item_id` instead. */
						def withPostId(postId: String) = new get(req.addQueryStringParameters("postId" -> postId.toString))
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.AddOnAttachmentStudentSubmission])
					}
					object get {
						def apply(postId :PlayApi, courseId: String, attachmentId: String, submissionId: String, itemId: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/courses/${courseId}/posts/${postId}/addOnAttachments/${attachmentId}/studentSubmissions/${submissionId}").addQueryStringParameters("itemId" -> itemId.toString))
						given Conversion[get, Future[Schema.AddOnAttachmentStudentSubmission]] = (fun: get) => fun.apply()
					}
				}
			}
		}
		object teachers {
			class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withTeacher(body: Schema.Teacher) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Teacher])
			}
			object create {
				def apply(courseId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/courses/${courseId}/teachers").addQueryStringParameters())
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Teacher]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Teacher])
			}
			object get {
				def apply(courseId: String, userId: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/courses/${courseId}/teachers/${userId}").addQueryStringParameters())
				given Conversion[get, Future[Schema.Teacher]] = (fun: get) => fun.apply()
			}
			class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
				def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
			}
			object delete {
				def apply(courseId: String, userId: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/courses/${courseId}/teachers/${userId}").addQueryStringParameters())
				given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListTeachersResponse]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListTeachersResponse])
			}
			object list {
				def apply(courseId: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/courses/${courseId}/teachers").addQueryStringParameters("pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListTeachersResponse]] = (fun: list) => fun.apply()
			}
		}
		object courseWork {
			class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withCourseWork(body: Schema.CourseWork) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.CourseWork])
			}
			object create {
				def apply(courseId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/courses/${courseId}/courseWork").addQueryStringParameters())
			}
			class modifyAssignees(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withModifyCourseWorkAssigneesRequest(body: Schema.ModifyCourseWorkAssigneesRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.CourseWork])
			}
			object modifyAssignees {
				def apply(courseId: String, id: String)(using auth: AuthToken, ec: ExecutionContext): modifyAssignees = new modifyAssignees(ws.url(BASE_URL + s"v1/courses/${courseId}/courseWork/${id}:modifyAssignees").addQueryStringParameters())
			}
			class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
				def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
			}
			object delete {
				def apply(courseId: String, id: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/courses/${courseId}/courseWork/${id}").addQueryStringParameters())
				given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
			}
			class getAddOnContext(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.AddOnContext]) {
				/** Optional. Deprecated, use `item_id` instead. */
				def withPostId(postId: String) = new getAddOnContext(req.addQueryStringParameters("postId" -> postId.toString))
				/** Optional. Token that authorizes the request. The token is passed as a query parameter when the user is redirected from Classroom to the add-on's URL. The authorization token is required when neither of the following is true: &#42; The add-on has attachments on the post. &#42; The developer project issuing the request is the same project that created the post. */
				def withAddOnToken(addOnToken: String) = new getAddOnContext(req.addQueryStringParameters("addOnToken" -> addOnToken.toString))
				/** Optional. The identifier of the attachment. This field is required for all requests except when the user is in the [Attachment Discovery iframe](https://developers.google.com/classroom/add-ons/get-started/iframes/attachment-discovery-iframe). */
				def withAttachmentId(attachmentId: String) = new getAddOnContext(req.addQueryStringParameters("attachmentId" -> attachmentId.toString))
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.AddOnContext])
			}
			object getAddOnContext {
				def apply(courseId: String, itemId: String)(using auth: AuthToken, ec: ExecutionContext): getAddOnContext = new getAddOnContext(ws.url(BASE_URL + s"v1/courses/${courseId}/courseWork/${itemId}/addOnContext").addQueryStringParameters())
				given Conversion[getAddOnContext, Future[Schema.AddOnContext]] = (fun: getAddOnContext) => fun.apply()
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.CourseWork]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.CourseWork])
			}
			object get {
				def apply(courseId: String, id: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/courses/${courseId}/courseWork/${id}").addQueryStringParameters())
				given Conversion[get, Future[Schema.CourseWork]] = (fun: get) => fun.apply()
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListCourseWorkResponse]) {
				/** Optional sort ordering for results. A comma-separated list of fields with an optional sort direction keyword. Supported fields are `updateTime` and `dueDate`. Supported direction keywords are `asc` and `desc`. If not specified, `updateTime desc` is the default behavior. Examples: `dueDate asc,updateTime desc`, `updateTime,dueDate desc` */
				def withOrderBy(orderBy: String) = new list(req.addQueryStringParameters("orderBy" -> orderBy.toString))
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListCourseWorkResponse])
			}
			object list {
				def apply(courseId: String, courseWorkStates: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/courses/${courseId}/courseWork").addQueryStringParameters("courseWorkStates" -> courseWorkStates.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListCourseWorkResponse]] = (fun: list) => fun.apply()
			}
			class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withCourseWork(body: Schema.CourseWork) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.CourseWork])
			}
			object patch {
				def apply(courseId: String, id: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/courses/${courseId}/courseWork/${id}").addQueryStringParameters("updateMask" -> updateMask.toString))
			}
			object studentSubmissions {
				class modifyAttachments(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withModifyAttachmentsRequest(body: Schema.ModifyAttachmentsRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.StudentSubmission])
				}
				object modifyAttachments {
					def apply(courseId: String, courseWorkId: String, id: String)(using auth: AuthToken, ec: ExecutionContext): modifyAttachments = new modifyAttachments(ws.url(BASE_URL + s"v1/courses/${courseId}/courseWork/${courseWorkId}/studentSubmissions/${id}:modifyAttachments").addQueryStringParameters())
				}
				class `return`(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withReturnStudentSubmissionRequest(body: Schema.ReturnStudentSubmissionRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Empty])
				}
				object `return` {
					def apply(courseId: String, courseWorkId: String, id: String)(using auth: AuthToken, ec: ExecutionContext): `return` = new `return`(ws.url(BASE_URL + s"v1/courses/${courseId}/courseWork/${courseWorkId}/studentSubmissions/${id}:return").addQueryStringParameters())
				}
				class reclaim(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withReclaimStudentSubmissionRequest(body: Schema.ReclaimStudentSubmissionRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Empty])
				}
				object reclaim {
					def apply(courseId: String, courseWorkId: String, id: String)(using auth: AuthToken, ec: ExecutionContext): reclaim = new reclaim(ws.url(BASE_URL + s"v1/courses/${courseId}/courseWork/${courseWorkId}/studentSubmissions/${id}:reclaim").addQueryStringParameters())
				}
				class turnIn(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withTurnInStudentSubmissionRequest(body: Schema.TurnInStudentSubmissionRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Empty])
				}
				object turnIn {
					def apply(courseId: String, courseWorkId: String, id: String)(using auth: AuthToken, ec: ExecutionContext): turnIn = new turnIn(ws.url(BASE_URL + s"v1/courses/${courseId}/courseWork/${courseWorkId}/studentSubmissions/${id}:turnIn").addQueryStringParameters())
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.StudentSubmission]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.StudentSubmission])
				}
				object get {
					def apply(courseId: String, courseWorkId: String, id: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/courses/${courseId}/courseWork/${courseWorkId}/studentSubmissions/${id}").addQueryStringParameters())
					given Conversion[get, Future[Schema.StudentSubmission]] = (fun: get) => fun.apply()
				}
				class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withStudentSubmission(body: Schema.StudentSubmission) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.StudentSubmission])
				}
				object patch {
					def apply(courseId: String, courseWorkId: String, id: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/courses/${courseId}/courseWork/${courseWorkId}/studentSubmissions/${id}").addQueryStringParameters("updateMask" -> updateMask.toString))
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListStudentSubmissionsResponse]) {
					/** Optional argument to restrict returned student work to those owned by the student with the specified identifier. The identifier can be one of the following: &#42; the numeric identifier for the user &#42; the email address of the user &#42; the string literal `"me"`, indicating the requesting user */
					def withUserId(userId: String) = new list(req.addQueryStringParameters("userId" -> userId.toString))
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListStudentSubmissionsResponse])
				}
				object list {
					def apply(courseId: String, courseWorkId: String, states: String, late: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/courses/${courseId}/courseWork/${courseWorkId}/studentSubmissions").addQueryStringParameters("states" -> states.toString, "late" -> late.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListStudentSubmissionsResponse]] = (fun: list) => fun.apply()
				}
			}
			object addOnAttachments {
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					/** Optional. Deprecated, use `item_id` instead. */
					def withPostId(postId: String) = new create(req.addQueryStringParameters("postId" -> postId.toString))
					/** Optional. Token that authorizes the request. The token is passed as a query parameter when the user is redirected from Classroom to the add-on's URL. This authorization token is required for in-Classroom attachment creation but optional for partner-first attachment creation. Returns an error if not provided for partner-first attachment creation and the developer projects that created the attachment and its parent stream item do not match. */
					def withAddOnToken(addOnToken: String) = new create(req.addQueryStringParameters("addOnToken" -> addOnToken.toString))
					def withAddOnAttachment(body: Schema.AddOnAttachment) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.AddOnAttachment])
				}
				object create {
					def apply(courseId: String, itemId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/courses/${courseId}/courseWork/${itemId}/addOnAttachments").addQueryStringParameters())
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					/** Optional. Deprecated, use `item_id` instead. */
					def withPostId(postId: String) = new delete(req.addQueryStringParameters("postId" -> postId.toString))
					def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
				}
				object delete {
					def apply(courseId: String, itemId: String, attachmentId: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/courses/${courseId}/courseWork/${itemId}/addOnAttachments/${attachmentId}").addQueryStringParameters())
					given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.AddOnAttachment]) {
					/** Optional. Deprecated, use `item_id` instead. */
					def withPostId(postId: String) = new get(req.addQueryStringParameters("postId" -> postId.toString))
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.AddOnAttachment])
				}
				object get {
					def apply(courseId: String, itemId: String, attachmentId: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/courses/${courseId}/courseWork/${itemId}/addOnAttachments/${attachmentId}").addQueryStringParameters())
					given Conversion[get, Future[Schema.AddOnAttachment]] = (fun: get) => fun.apply()
				}
				class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withAddOnAttachment(body: Schema.AddOnAttachment) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.AddOnAttachment])
				}
				object patch {
					def apply(courseId: String, itemId: String, attachmentId: String, postId: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/courses/${courseId}/courseWork/${itemId}/addOnAttachments/${attachmentId}").addQueryStringParameters("postId" -> postId.toString, "updateMask" -> updateMask.toString))
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListAddOnAttachmentsResponse]) {
					/** Optional. Identifier of the post under the course whose attachments to enumerate. Deprecated, use `item_id` instead. */
					def withPostId(postId: String) = new list(req.addQueryStringParameters("postId" -> postId.toString))
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListAddOnAttachmentsResponse])
				}
				object list {
					def apply(courseId: String, itemId: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/courses/${courseId}/courseWork/${itemId}/addOnAttachments").addQueryStringParameters("pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListAddOnAttachmentsResponse]] = (fun: list) => fun.apply()
				}
				object studentSubmissions {
					class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						/** Optional. Deprecated, use `item_id` instead. */
						def withPostId(postId: String) = new patch(req.addQueryStringParameters("postId" -> postId.toString))
						def withAddOnAttachmentStudentSubmission(body: Schema.AddOnAttachmentStudentSubmission) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.AddOnAttachmentStudentSubmission])
					}
					object patch {
						def apply(courseId: String, itemId: String, attachmentId: String, submissionId: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/courses/${courseId}/courseWork/${itemId}/addOnAttachments/${attachmentId}/studentSubmissions/${submissionId}").addQueryStringParameters("updateMask" -> updateMask.toString))
					}
					class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.AddOnAttachmentStudentSubmission]) {
						/** Optional. Deprecated, use `item_id` instead. */
						def withPostId(postId: String) = new get(req.addQueryStringParameters("postId" -> postId.toString))
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.AddOnAttachmentStudentSubmission])
					}
					object get {
						def apply(courseId: String, itemId: String, attachmentId: String, submissionId: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/courses/${courseId}/courseWork/${itemId}/addOnAttachments/${attachmentId}/studentSubmissions/${submissionId}").addQueryStringParameters())
						given Conversion[get, Future[Schema.AddOnAttachmentStudentSubmission]] = (fun: get) => fun.apply()
					}
				}
			}
		}
		object announcements {
			class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withAnnouncement(body: Schema.Announcement) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Announcement])
			}
			object create {
				def apply(courseId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/courses/${courseId}/announcements").addQueryStringParameters())
			}
			class modifyAssignees(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withModifyAnnouncementAssigneesRequest(body: Schema.ModifyAnnouncementAssigneesRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Announcement])
			}
			object modifyAssignees {
				def apply(courseId: String, id: String)(using auth: AuthToken, ec: ExecutionContext): modifyAssignees = new modifyAssignees(ws.url(BASE_URL + s"v1/courses/${courseId}/announcements/${id}:modifyAssignees").addQueryStringParameters())
			}
			class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
				def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
			}
			object delete {
				def apply(courseId: String, id: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/courses/${courseId}/announcements/${id}").addQueryStringParameters())
				given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
			}
			class getAddOnContext(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.AddOnContext]) {
				/** Optional. Deprecated, use `item_id` instead. */
				def withPostId(postId: String) = new getAddOnContext(req.addQueryStringParameters("postId" -> postId.toString))
				/** Optional. Token that authorizes the request. The token is passed as a query parameter when the user is redirected from Classroom to the add-on's URL. The authorization token is required when neither of the following is true: &#42; The add-on has attachments on the post. &#42; The developer project issuing the request is the same project that created the post. */
				def withAddOnToken(addOnToken: String) = new getAddOnContext(req.addQueryStringParameters("addOnToken" -> addOnToken.toString))
				/** Optional. The identifier of the attachment. This field is required for all requests except when the user is in the [Attachment Discovery iframe](https://developers.google.com/classroom/add-ons/get-started/iframes/attachment-discovery-iframe). */
				def withAttachmentId(attachmentId: String) = new getAddOnContext(req.addQueryStringParameters("attachmentId" -> attachmentId.toString))
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.AddOnContext])
			}
			object getAddOnContext {
				def apply(courseId: String, itemId: String)(using auth: AuthToken, ec: ExecutionContext): getAddOnContext = new getAddOnContext(ws.url(BASE_URL + s"v1/courses/${courseId}/announcements/${itemId}/addOnContext").addQueryStringParameters())
				given Conversion[getAddOnContext, Future[Schema.AddOnContext]] = (fun: getAddOnContext) => fun.apply()
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Announcement]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Announcement])
			}
			object get {
				def apply(courseId: String, id: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/courses/${courseId}/announcements/${id}").addQueryStringParameters())
				given Conversion[get, Future[Schema.Announcement]] = (fun: get) => fun.apply()
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListAnnouncementsResponse]) {
				/** Optional sort ordering for results. A comma-separated list of fields with an optional sort direction keyword. Supported field is `updateTime`. Supported direction keywords are `asc` and `desc`. If not specified, `updateTime desc` is the default behavior. Examples: `updateTime asc`, `updateTime` */
				def withOrderBy(orderBy: String) = new list(req.addQueryStringParameters("orderBy" -> orderBy.toString))
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListAnnouncementsResponse])
			}
			object list {
				def apply(courseId: String, announcementStates: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/courses/${courseId}/announcements").addQueryStringParameters("announcementStates" -> announcementStates.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListAnnouncementsResponse]] = (fun: list) => fun.apply()
			}
			class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withAnnouncement(body: Schema.Announcement) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Announcement])
			}
			object patch {
				def apply(courseId: String, id: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/courses/${courseId}/announcements/${id}").addQueryStringParameters("updateMask" -> updateMask.toString))
			}
			object addOnAttachments {
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					/** Optional. Deprecated, use `item_id` instead. */
					def withPostId(postId: String) = new create(req.addQueryStringParameters("postId" -> postId.toString))
					/** Optional. Token that authorizes the request. The token is passed as a query parameter when the user is redirected from Classroom to the add-on's URL. This authorization token is required for in-Classroom attachment creation but optional for partner-first attachment creation. Returns an error if not provided for partner-first attachment creation and the developer projects that created the attachment and its parent stream item do not match. */
					def withAddOnToken(addOnToken: String) = new create(req.addQueryStringParameters("addOnToken" -> addOnToken.toString))
					def withAddOnAttachment(body: Schema.AddOnAttachment) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.AddOnAttachment])
				}
				object create {
					def apply(courseId: String, itemId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/courses/${courseId}/announcements/${itemId}/addOnAttachments").addQueryStringParameters())
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					/** Optional. Deprecated, use `item_id` instead. */
					def withPostId(postId: String) = new delete(req.addQueryStringParameters("postId" -> postId.toString))
					def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
				}
				object delete {
					def apply(courseId: String, itemId: String, attachmentId: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/courses/${courseId}/announcements/${itemId}/addOnAttachments/${attachmentId}").addQueryStringParameters())
					given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.AddOnAttachment]) {
					/** Optional. Deprecated, use `item_id` instead. */
					def withPostId(postId: String) = new get(req.addQueryStringParameters("postId" -> postId.toString))
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.AddOnAttachment])
				}
				object get {
					def apply(courseId: String, itemId: String, attachmentId: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/courses/${courseId}/announcements/${itemId}/addOnAttachments/${attachmentId}").addQueryStringParameters())
					given Conversion[get, Future[Schema.AddOnAttachment]] = (fun: get) => fun.apply()
				}
				class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withAddOnAttachment(body: Schema.AddOnAttachment) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.AddOnAttachment])
				}
				object patch {
					def apply(courseId: String, itemId: String, attachmentId: String, postId: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/courses/${courseId}/announcements/${itemId}/addOnAttachments/${attachmentId}").addQueryStringParameters("postId" -> postId.toString, "updateMask" -> updateMask.toString))
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListAddOnAttachmentsResponse]) {
					/** Optional. Identifier of the post under the course whose attachments to enumerate. Deprecated, use `item_id` instead. */
					def withPostId(postId: String) = new list(req.addQueryStringParameters("postId" -> postId.toString))
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListAddOnAttachmentsResponse])
				}
				object list {
					def apply(courseId: String, itemId: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/courses/${courseId}/announcements/${itemId}/addOnAttachments").addQueryStringParameters("pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListAddOnAttachmentsResponse]] = (fun: list) => fun.apply()
				}
			}
		}
		object topics {
			class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withTopic(body: Schema.Topic) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Topic])
			}
			object create {
				def apply(courseId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/courses/${courseId}/topics").addQueryStringParameters())
			}
			class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
				def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
			}
			object delete {
				def apply(courseId: String, id: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/courses/${courseId}/topics/${id}").addQueryStringParameters())
				given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Topic]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Topic])
			}
			object get {
				def apply(courseId: String, id: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/courses/${courseId}/topics/${id}").addQueryStringParameters())
				given Conversion[get, Future[Schema.Topic]] = (fun: get) => fun.apply()
			}
			class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withTopic(body: Schema.Topic) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Topic])
			}
			object patch {
				def apply(courseId: String, id: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/courses/${courseId}/topics/${id}").addQueryStringParameters("updateMask" -> updateMask.toString))
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListTopicResponse]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListTopicResponse])
			}
			object list {
				def apply(courseId: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/courses/${courseId}/topics").addQueryStringParameters("pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListTopicResponse]] = (fun: list) => fun.apply()
			}
		}
		object courseWorkMaterials {
			class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withCourseWorkMaterial(body: Schema.CourseWorkMaterial) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.CourseWorkMaterial])
			}
			object create {
				def apply(courseId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/courses/${courseId}/courseWorkMaterials").addQueryStringParameters())
			}
			class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
				def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
			}
			object delete {
				def apply(courseId: String, id: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/courses/${courseId}/courseWorkMaterials/${id}").addQueryStringParameters())
				given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
			}
			class getAddOnContext(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.AddOnContext]) {
				/** Optional. Deprecated, use `item_id` instead. */
				def withPostId(postId: String) = new getAddOnContext(req.addQueryStringParameters("postId" -> postId.toString))
				/** Optional. Token that authorizes the request. The token is passed as a query parameter when the user is redirected from Classroom to the add-on's URL. The authorization token is required when neither of the following is true: &#42; The add-on has attachments on the post. &#42; The developer project issuing the request is the same project that created the post. */
				def withAddOnToken(addOnToken: String) = new getAddOnContext(req.addQueryStringParameters("addOnToken" -> addOnToken.toString))
				/** Optional. The identifier of the attachment. This field is required for all requests except when the user is in the [Attachment Discovery iframe](https://developers.google.com/classroom/add-ons/get-started/iframes/attachment-discovery-iframe). */
				def withAttachmentId(attachmentId: String) = new getAddOnContext(req.addQueryStringParameters("attachmentId" -> attachmentId.toString))
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.AddOnContext])
			}
			object getAddOnContext {
				def apply(courseId: String, itemId: String)(using auth: AuthToken, ec: ExecutionContext): getAddOnContext = new getAddOnContext(ws.url(BASE_URL + s"v1/courses/${courseId}/courseWorkMaterials/${itemId}/addOnContext").addQueryStringParameters())
				given Conversion[getAddOnContext, Future[Schema.AddOnContext]] = (fun: getAddOnContext) => fun.apply()
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.CourseWorkMaterial]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.CourseWorkMaterial])
			}
			object get {
				def apply(courseId: String, id: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/courses/${courseId}/courseWorkMaterials/${id}").addQueryStringParameters())
				given Conversion[get, Future[Schema.CourseWorkMaterial]] = (fun: get) => fun.apply()
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListCourseWorkMaterialResponse]) {
				/** Optional sort ordering for results. A comma-separated list of fields with an optional sort direction keyword. Supported field is `updateTime`. Supported direction keywords are `asc` and `desc`. If not specified, `updateTime desc` is the default behavior. Examples: `updateTime asc`, `updateTime` */
				def withOrderBy(orderBy: String) = new list(req.addQueryStringParameters("orderBy" -> orderBy.toString))
				/** Optional filtering for course work material with at least one link material whose URL partially matches the provided string. */
				def withMaterialLink(materialLink: String) = new list(req.addQueryStringParameters("materialLink" -> materialLink.toString))
				/** Optional filtering for course work material with at least one Drive material whose ID matches the provided string. If `material_link` is also specified, course work material must have materials matching both filters. */
				def withMaterialDriveId(materialDriveId: String) = new list(req.addQueryStringParameters("materialDriveId" -> materialDriveId.toString))
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListCourseWorkMaterialResponse])
			}
			object list {
				def apply(courseId: String, courseWorkMaterialStates: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/courses/${courseId}/courseWorkMaterials").addQueryStringParameters("courseWorkMaterialStates" -> courseWorkMaterialStates.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListCourseWorkMaterialResponse]] = (fun: list) => fun.apply()
			}
			class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withCourseWorkMaterial(body: Schema.CourseWorkMaterial) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.CourseWorkMaterial])
			}
			object patch {
				def apply(courseId: String, id: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/courses/${courseId}/courseWorkMaterials/${id}").addQueryStringParameters("updateMask" -> updateMask.toString))
			}
			object addOnAttachments {
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					/** Optional. Deprecated, use `item_id` instead. */
					def withPostId(postId: String) = new create(req.addQueryStringParameters("postId" -> postId.toString))
					/** Optional. Token that authorizes the request. The token is passed as a query parameter when the user is redirected from Classroom to the add-on's URL. This authorization token is required for in-Classroom attachment creation but optional for partner-first attachment creation. Returns an error if not provided for partner-first attachment creation and the developer projects that created the attachment and its parent stream item do not match. */
					def withAddOnToken(addOnToken: String) = new create(req.addQueryStringParameters("addOnToken" -> addOnToken.toString))
					def withAddOnAttachment(body: Schema.AddOnAttachment) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.AddOnAttachment])
				}
				object create {
					def apply(courseId: String, itemId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/courses/${courseId}/courseWorkMaterials/${itemId}/addOnAttachments").addQueryStringParameters())
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					/** Optional. Deprecated, use `item_id` instead. */
					def withPostId(postId: String) = new delete(req.addQueryStringParameters("postId" -> postId.toString))
					def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
				}
				object delete {
					def apply(courseId: String, itemId: String, attachmentId: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/courses/${courseId}/courseWorkMaterials/${itemId}/addOnAttachments/${attachmentId}").addQueryStringParameters())
					given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.AddOnAttachment]) {
					/** Optional. Deprecated, use `item_id` instead. */
					def withPostId(postId: String) = new get(req.addQueryStringParameters("postId" -> postId.toString))
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.AddOnAttachment])
				}
				object get {
					def apply(courseId: String, itemId: String, attachmentId: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/courses/${courseId}/courseWorkMaterials/${itemId}/addOnAttachments/${attachmentId}").addQueryStringParameters())
					given Conversion[get, Future[Schema.AddOnAttachment]] = (fun: get) => fun.apply()
				}
				class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withAddOnAttachment(body: Schema.AddOnAttachment) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.AddOnAttachment])
				}
				object patch {
					def apply(courseId: String, itemId: String, attachmentId: String, postId: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/courses/${courseId}/courseWorkMaterials/${itemId}/addOnAttachments/${attachmentId}").addQueryStringParameters("postId" -> postId.toString, "updateMask" -> updateMask.toString))
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListAddOnAttachmentsResponse]) {
					/** Optional. Identifier of the post under the course whose attachments to enumerate. Deprecated, use `item_id` instead. */
					def withPostId(postId: String) = new list(req.addQueryStringParameters("postId" -> postId.toString))
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListAddOnAttachmentsResponse])
				}
				object list {
					def apply(courseId: String, itemId: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/courses/${courseId}/courseWorkMaterials/${itemId}/addOnAttachments").addQueryStringParameters("pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListAddOnAttachmentsResponse]] = (fun: list) => fun.apply()
				}
			}
		}
		object students {
			class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withStudent(body: Schema.Student) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Student])
			}
			object create {
				def apply(courseId: String, enrollmentCode: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/courses/${courseId}/students").addQueryStringParameters("enrollmentCode" -> enrollmentCode.toString))
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Student]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Student])
			}
			object get {
				def apply(courseId: String, userId: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/courses/${courseId}/students/${userId}").addQueryStringParameters())
				given Conversion[get, Future[Schema.Student]] = (fun: get) => fun.apply()
			}
			class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
				def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
			}
			object delete {
				def apply(courseId: String, userId: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/courses/${courseId}/students/${userId}").addQueryStringParameters())
				given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListStudentsResponse]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListStudentsResponse])
			}
			object list {
				def apply(courseId: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/courses/${courseId}/students").addQueryStringParameters("pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListStudentsResponse]] = (fun: list) => fun.apply()
			}
		}
	}
	object userProfiles {
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.UserProfile]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.UserProfile])
		}
		object get {
			def apply(userId: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/userProfiles/${userId}").addQueryStringParameters())
			given Conversion[get, Future[Schema.UserProfile]] = (fun: get) => fun.apply()
		}
		object guardianInvitations {
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListGuardianInvitationsResponse]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListGuardianInvitationsResponse])
			}
			object list {
				def apply(studentId: String, invitedEmailAddress: String, states: String, pageToken: String, pageSize: Int)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/userProfiles/${studentId}/guardianInvitations").addQueryStringParameters("invitedEmailAddress" -> invitedEmailAddress.toString, "states" -> states.toString, "pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString))
				given Conversion[list, Future[Schema.ListGuardianInvitationsResponse]] = (fun: list) => fun.apply()
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GuardianInvitation]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GuardianInvitation])
			}
			object get {
				def apply(studentId: String, invitationId: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/userProfiles/${studentId}/guardianInvitations/${invitationId}").addQueryStringParameters())
				given Conversion[get, Future[Schema.GuardianInvitation]] = (fun: get) => fun.apply()
			}
			class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGuardianInvitation(body: Schema.GuardianInvitation) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GuardianInvitation])
			}
			object create {
				def apply(studentId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/userProfiles/${studentId}/guardianInvitations").addQueryStringParameters())
			}
			class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGuardianInvitation(body: Schema.GuardianInvitation) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GuardianInvitation])
			}
			object patch {
				def apply(studentId: String, invitationId: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/userProfiles/${studentId}/guardianInvitations/${invitationId}").addQueryStringParameters("updateMask" -> updateMask.toString))
			}
		}
		object guardians {
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListGuardiansResponse]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListGuardiansResponse])
			}
			object list {
				def apply(studentId: String, invitedEmailAddress: String, pageToken: String, pageSize: Int)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/userProfiles/${studentId}/guardians").addQueryStringParameters("invitedEmailAddress" -> invitedEmailAddress.toString, "pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString))
				given Conversion[list, Future[Schema.ListGuardiansResponse]] = (fun: list) => fun.apply()
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Guardian]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Guardian])
			}
			object get {
				def apply(studentId: String, guardianId: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/userProfiles/${studentId}/guardians/${guardianId}").addQueryStringParameters())
				given Conversion[get, Future[Schema.Guardian]] = (fun: get) => fun.apply()
			}
			class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
				def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
			}
			object delete {
				def apply(studentId: String, guardianId: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/userProfiles/${studentId}/guardians/${guardianId}").addQueryStringParameters())
				given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
			}
		}
	}
	object invitations {
		class accept(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
			def apply() = auth.exec(req,_.execute("POST")).map(_.json.as[Schema.Empty])
		}
		object accept {
			def apply(id: String)(using auth: AuthToken, ec: ExecutionContext): accept = new accept(ws.url(BASE_URL + s"v1/invitations/${id}:accept").addQueryStringParameters())
			given Conversion[accept, Future[Schema.Empty]] = (fun: accept) => fun.apply()
		}
		class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withInvitation(body: Schema.Invitation) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Invitation])
		}
		object create {
			def apply()(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/invitations").addQueryStringParameters())
		}
		class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
			def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
		}
		object delete {
			def apply(id: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/invitations/${id}").addQueryStringParameters())
			given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
		}
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Invitation]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Invitation])
		}
		object get {
			def apply(id: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/invitations/${id}").addQueryStringParameters())
			given Conversion[get, Future[Schema.Invitation]] = (fun: get) => fun.apply()
		}
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListInvitationsResponse]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListInvitationsResponse])
		}
		object list {
			def apply(userId: String, courseId: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/invitations").addQueryStringParameters("userId" -> userId.toString, "courseId" -> courseId.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
			given Conversion[list, Future[Schema.ListInvitationsResponse]] = (fun: list) => fun.apply()
		}
	}
	object registrations {
		class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withRegistration(body: Schema.Registration) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Registration])
		}
		object create {
			def apply()(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/registrations").addQueryStringParameters())
		}
		class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
			def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
		}
		object delete {
			def apply(registrationId: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/registrations/${registrationId}").addQueryStringParameters())
			given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
		}
	}
}
