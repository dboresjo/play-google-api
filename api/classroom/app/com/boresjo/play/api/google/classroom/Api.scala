package com.boresjo.play.api.google.classroom

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	val scopes = Seq(
		"""https://www.googleapis.com/auth/classroom.announcements""" /* View and manage announcements in Google Classroom */,
		"""https://www.googleapis.com/auth/classroom.announcements.readonly""" /* View announcements in Google Classroom */,
		"""https://www.googleapis.com/auth/classroom.courses""" /* See, edit, create, and permanently delete your Google Classroom classes */,
		"""https://www.googleapis.com/auth/classroom.courses.readonly""" /* View your Google Classroom classes */,
		"""https://www.googleapis.com/auth/classroom.coursework.me""" /* See, create and edit coursework items including assignments, questions, and grades */,
		"""https://www.googleapis.com/auth/classroom.coursework.me.readonly""" /* View your course work and grades in Google Classroom */,
		"""https://www.googleapis.com/auth/classroom.coursework.students""" /* Manage course work and grades for students in the Google Classroom classes you teach and view the course work and grades for classes you administer */,
		"""https://www.googleapis.com/auth/classroom.coursework.students.readonly""" /* View course work and grades for students in the Google Classroom classes you teach or administer */,
		"""https://www.googleapis.com/auth/classroom.courseworkmaterials""" /* See, edit, and create classwork materials in Google Classroom */,
		"""https://www.googleapis.com/auth/classroom.courseworkmaterials.readonly""" /* See all classwork materials for your Google Classroom classes */,
		"""https://www.googleapis.com/auth/classroom.guardianlinks.me.readonly""" /* View your Google Classroom guardians */,
		"""https://www.googleapis.com/auth/classroom.guardianlinks.students""" /* View and manage guardians for students in your Google Classroom classes */,
		"""https://www.googleapis.com/auth/classroom.guardianlinks.students.readonly""" /* View guardians for students in your Google Classroom classes */,
		"""https://www.googleapis.com/auth/classroom.profile.emails""" /* View the email addresses of people in your classes */,
		"""https://www.googleapis.com/auth/classroom.profile.photos""" /* View the profile photos of people in your classes */,
		"""https://www.googleapis.com/auth/classroom.push-notifications""" /* Receive notifications about your Google Classroom data */,
		"""https://www.googleapis.com/auth/classroom.rosters""" /* Manage your Google Classroom class rosters */,
		"""https://www.googleapis.com/auth/classroom.rosters.readonly""" /* View your Google Classroom class rosters */,
		"""https://www.googleapis.com/auth/classroom.student-submissions.me.readonly""" /* View your course work and grades in Google Classroom */,
		"""https://www.googleapis.com/auth/classroom.student-submissions.students.readonly""" /* View course work and grades for students in the Google Classroom classes you teach or administer */,
		"""https://www.googleapis.com/auth/classroom.topics""" /* See, create, and edit topics in Google Classroom */,
		"""https://www.googleapis.com/auth/classroom.topics.readonly""" /* View topics in Google Classroom */
	)

	private val BASE_URL = "https://classroom.googleapis.com/"

	object courses {
		/** Creates a course. The user specified in `ownerId` is the owner of the created course and added as a teacher. A non-admin requesting user can only create a course with themselves as the owner. Domain admins can create courses owned by any user within their domain. This method returns the following error codes: &#42; `PERMISSION_DENIED` if the requesting user is not permitted to create courses or for access errors. &#42; `NOT_FOUND` if the primary teacher is not a valid user. &#42; `FAILED_PRECONDITION` if the course owner's account is disabled or for the following request errors: &#42; UserCannotOwnCourse &#42; UserGroupsMembershipLimitReached &#42; `ALREADY_EXISTS` if an alias was specified in the `id` and already exists. */
		class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/classroom.courses""")
			/** Perform the request */
			def withCourse(body: Schema.Course) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Course])
		}
		object create {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/courses").addQueryStringParameters())
		}
		/** Deletes a course. This method returns the following error codes: &#42; `PERMISSION_DENIED` if the requesting user is not permitted to delete the requested course or for access errors. &#42; `NOT_FOUND` if no course exists with the requested ID. */
		class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
			val scopes = Seq("""https://www.googleapis.com/auth/classroom.courses""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
		}
		object delete {
			def apply(id: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/courses/${id}").addQueryStringParameters())
			given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
		}
		/** Returns a course. This method returns the following error codes: &#42; `PERMISSION_DENIED` if the requesting user is not permitted to access the requested course or for access errors. &#42; `NOT_FOUND` if no course exists with the requested ID. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Course]) {
			val scopes = Seq("""https://www.googleapis.com/auth/classroom.courses""", """https://www.googleapis.com/auth/classroom.courses.readonly""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Course])
		}
		object get {
			def apply(id: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/courses/${id}").addQueryStringParameters())
			given Conversion[get, Future[Schema.Course]] = (fun: get) => fun.apply()
		}
		/** Updates a course. This method returns the following error codes: &#42; `PERMISSION_DENIED` if the requesting user is not permitted to modify the requested course or for access errors. &#42; `NOT_FOUND` if no course exists with the requested ID. &#42; `FAILED_PRECONDITION` for the following request errors: &#42; CourseNotModifiable */
		class update(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/classroom.courses""")
			/** Perform the request */
			def withCourse(body: Schema.Course) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.Course])
		}
		object update {
			def apply(id: String)(using signer: RequestSigner, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"v1/courses/${id}").addQueryStringParameters())
		}
		/** Updates one or more fields in a course. This method returns the following error codes: &#42; `PERMISSION_DENIED` if the requesting user is not permitted to modify the requested course or for access errors. &#42; `NOT_FOUND` if no course exists with the requested ID. &#42; `INVALID_ARGUMENT` if invalid fields are specified in the update mask or if no update mask is supplied. &#42; `FAILED_PRECONDITION` for the following request errors: &#42; CourseNotModifiable &#42; InactiveCourseOwner &#42; IneligibleOwner */
		class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/classroom.courses""")
			/** Perform the request */
			def withCourse(body: Schema.Course) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Course])
		}
		object patch {
			def apply(id: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/courses/${id}").addQueryStringParameters("updateMask" -> updateMask.toString))
		}
		/** Returns a list of courses that the requesting user is permitted to view, restricted to those that match the request. Returned courses are ordered by creation time, with the most recently created coming first. This method returns the following error codes: &#42; `PERMISSION_DENIED` for access errors. &#42; `INVALID_ARGUMENT` if the query argument is malformed. &#42; `NOT_FOUND` if any users specified in the query arguments do not exist. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListCoursesResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/classroom.courses""", """https://www.googleapis.com/auth/classroom.courses.readonly""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListCoursesResponse])
		}
		object list {
			def apply(studentId: String, teacherId: String, courseStates: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/courses").addQueryStringParameters("studentId" -> studentId.toString, "teacherId" -> teacherId.toString, "courseStates" -> courseStates.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
			given Conversion[list, Future[Schema.ListCoursesResponse]] = (fun: list) => fun.apply()
		}
		object aliases {
			/** Creates an alias for a course. This method returns the following error codes: &#42; `PERMISSION_DENIED` if the requesting user is not permitted to create the alias or for access errors. &#42; `NOT_FOUND` if the course does not exist. &#42; `ALREADY_EXISTS` if the alias already exists. &#42; `FAILED_PRECONDITION` if the alias requested does not make sense for the requesting user or course (for example, if a user not in a domain attempts to access a domain-scoped alias). */
			class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/classroom.courses""")
				/** Perform the request */
				def withCourseAlias(body: Schema.CourseAlias) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.CourseAlias])
			}
			object create {
				def apply(courseId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/courses/${courseId}/aliases").addQueryStringParameters())
			}
			/** Deletes an alias of a course. This method returns the following error codes: &#42; `PERMISSION_DENIED` if the requesting user is not permitted to remove the alias or for access errors. &#42; `NOT_FOUND` if the alias does not exist. &#42; `FAILED_PRECONDITION` if the alias requested does not make sense for the requesting user or course (for example, if a user not in a domain attempts to delete a domain-scoped alias). */
			class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
				val scopes = Seq("""https://www.googleapis.com/auth/classroom.courses""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
			}
			object delete {
				def apply(courseId: String, alias: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/courses/${courseId}/aliases/${alias}").addQueryStringParameters())
				given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
			}
			/** Returns a list of aliases for a course. This method returns the following error codes: &#42; `PERMISSION_DENIED` if the requesting user is not permitted to access the course or for access errors. &#42; `NOT_FOUND` if the course does not exist. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListCourseAliasesResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/classroom.courses""", """https://www.googleapis.com/auth/classroom.courses.readonly""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListCourseAliasesResponse])
			}
			object list {
				def apply(courseId: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/courses/${courseId}/aliases").addQueryStringParameters("pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListCourseAliasesResponse]] = (fun: list) => fun.apply()
			}
		}
		object posts {
			/** Gets metadata for Classroom add-ons in the context of a specific post. To maintain the integrity of its own data and permissions model, an add-on should call this to validate query parameters and the requesting user's role whenever the add-on is opened in an [iframe](https://developers.google.com/classroom/add-ons/get-started/iframes/iframes-overview). This method returns the following error codes: &#42; `PERMISSION_DENIED` for access errors. &#42; `INVALID_ARGUMENT` if the request is malformed. &#42; `NOT_FOUND` if one of the identified resources does not exist. */
			class getAddOnContext(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.AddOnContext]) {
				val scopes = Seq()
				/** Optional. Deprecated, use `item_id` instead. */
				def withPostId(postId: String) = new getAddOnContext(req.addQueryStringParameters("postId" -> postId.toString))
				/** Optional. Token that authorizes the request. The token is passed as a query parameter when the user is redirected from Classroom to the add-on's URL. The authorization token is required when neither of the following is true: &#42; The add-on has attachments on the post. &#42; The developer project issuing the request is the same project that created the post. */
				def withAddOnToken(addOnToken: String) = new getAddOnContext(req.addQueryStringParameters("addOnToken" -> addOnToken.toString))
				/** Optional. The identifier of the attachment. This field is required for all requests except when the user is in the [Attachment Discovery iframe](https://developers.google.com/classroom/add-ons/get-started/iframes/attachment-discovery-iframe). */
				def withAttachmentId(attachmentId: String) = new getAddOnContext(req.addQueryStringParameters("attachmentId" -> attachmentId.toString))
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.AddOnContext])
			}
			object getAddOnContext {
				def apply(postId :PlayApi, courseId: String, itemId: String)(using signer: RequestSigner, ec: ExecutionContext): getAddOnContext = new getAddOnContext(ws.url(BASE_URL + s"v1/courses/${courseId}/posts/${postId}/addOnContext").addQueryStringParameters("itemId" -> itemId.toString))
				given Conversion[getAddOnContext, Future[Schema.AddOnContext]] = (fun: getAddOnContext) => fun.apply()
			}
			object addOnAttachments {
				/** Creates an add-on attachment under a post. Requires the add-on to have permission to create new attachments on the post. This method returns the following error codes: &#42; `PERMISSION_DENIED` for access errors. &#42; `INVALID_ARGUMENT` if the request is malformed. &#42; `NOT_FOUND` if one of the identified resources does not exist. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq()
					/** Optional. Deprecated, use `item_id` instead. */
					def withPostId(postId: String) = new create(req.addQueryStringParameters("postId" -> postId.toString))
					/** Optional. Token that authorizes the request. The token is passed as a query parameter when the user is redirected from Classroom to the add-on's URL. This authorization token is required for in-Classroom attachment creation but optional for partner-first attachment creation. Returns an error if not provided for partner-first attachment creation and the developer projects that created the attachment and its parent stream item do not match. */
					def withAddOnToken(addOnToken: String) = new create(req.addQueryStringParameters("addOnToken" -> addOnToken.toString))
					/** Perform the request */
					def withAddOnAttachment(body: Schema.AddOnAttachment) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.AddOnAttachment])
				}
				object create {
					def apply(postId :PlayApi, courseId: String, itemId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/courses/${courseId}/posts/${postId}/addOnAttachments").addQueryStringParameters("itemId" -> itemId.toString))
				}
				/** Deletes an add-on attachment. Requires the add-on to have been the original creator of the attachment. This method returns the following error codes: &#42; `PERMISSION_DENIED` for access errors. &#42; `INVALID_ARGUMENT` if the request is malformed. &#42; `NOT_FOUND` if one of the identified resources does not exist. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					val scopes = Seq()
					/** Optional. Deprecated, use `item_id` instead. */
					def withPostId(postId: String) = new delete(req.addQueryStringParameters("postId" -> postId.toString))
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
				}
				object delete {
					def apply(postId :PlayApi, courseId: String, attachmentId: String, itemId: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/courses/${courseId}/posts/${postId}/addOnAttachments/${attachmentId}").addQueryStringParameters("itemId" -> itemId.toString))
					given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
				}
				/** Returns an add-on attachment. Requires the add-on requesting the attachment to be the original creator of the attachment. This method returns the following error codes: &#42; `PERMISSION_DENIED` for access errors. &#42; `INVALID_ARGUMENT` if the request is malformed. &#42; `NOT_FOUND` if one of the identified resources does not exist. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.AddOnAttachment]) {
					val scopes = Seq()
					/** Optional. Deprecated, use `item_id` instead. */
					def withPostId(postId: String) = new get(req.addQueryStringParameters("postId" -> postId.toString))
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.AddOnAttachment])
				}
				object get {
					def apply(postId :PlayApi, courseId: String, attachmentId: String, itemId: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/courses/${courseId}/posts/${postId}/addOnAttachments/${attachmentId}").addQueryStringParameters("itemId" -> itemId.toString))
					given Conversion[get, Future[Schema.AddOnAttachment]] = (fun: get) => fun.apply()
				}
				/** Updates an add-on attachment. Requires the add-on to have been the original creator of the attachment. This method returns the following error codes: &#42; `PERMISSION_DENIED` for access errors. &#42; `INVALID_ARGUMENT` if the request is malformed. &#42; `NOT_FOUND` if one of the identified resources does not exist. */
				class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq()
					/** Perform the request */
					def withAddOnAttachment(body: Schema.AddOnAttachment) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.AddOnAttachment])
				}
				object patch {
					def apply(courseId: String, postId: String, attachmentId: String, itemId: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/courses/${courseId}/posts/${postId}/addOnAttachments/${attachmentId}").addQueryStringParameters("itemId" -> itemId.toString, "updateMask" -> updateMask.toString))
				}
				/** Returns all attachments created by an add-on under the post. Requires the add-on to have active attachments on the post or have permission to create new attachments on the post. This method returns the following error codes: &#42; `PERMISSION_DENIED` for access errors. &#42; `INVALID_ARGUMENT` if the request is malformed. &#42; `NOT_FOUND` if one of the identified resources does not exist. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListAddOnAttachmentsResponse]) {
					val scopes = Seq()
					/** Optional. Identifier of the post under the course whose attachments to enumerate. Deprecated, use `item_id` instead. */
					def withPostId(postId: String) = new list(req.addQueryStringParameters("postId" -> postId.toString))
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListAddOnAttachmentsResponse])
				}
				object list {
					def apply(postId :PlayApi, courseId: String, itemId: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/courses/${courseId}/posts/${postId}/addOnAttachments").addQueryStringParameters("itemId" -> itemId.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListAddOnAttachmentsResponse]] = (fun: list) => fun.apply()
				}
				object studentSubmissions {
					/** Updates data associated with an add-on attachment submission. Requires the add-on to have been the original creator of the attachment and the attachment to have a positive `max_points` value set. This method returns the following error codes: &#42; `PERMISSION_DENIED` for access errors. &#42; `INVALID_ARGUMENT` if the request is malformed. &#42; `NOT_FOUND` if one of the identified resources does not exist. */
					class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq()
						/** Optional. Deprecated, use `item_id` instead. */
						def withPostId(postId: String) = new patch(req.addQueryStringParameters("postId" -> postId.toString))
						/** Perform the request */
						def withAddOnAttachmentStudentSubmission(body: Schema.AddOnAttachmentStudentSubmission) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.AddOnAttachmentStudentSubmission])
					}
					object patch {
						def apply(postId :PlayApi, courseId: String, attachmentId: String, submissionId: String, itemId: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/courses/${courseId}/posts/${postId}/addOnAttachments/${attachmentId}/studentSubmissions/${submissionId}").addQueryStringParameters("itemId" -> itemId.toString, "updateMask" -> updateMask.toString))
					}
					/** Returns a student submission for an add-on attachment. This method returns the following error codes: &#42; `PERMISSION_DENIED` for access errors. &#42; `INVALID_ARGUMENT` if the request is malformed. &#42; `NOT_FOUND` if one of the identified resources does not exist. */
					class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.AddOnAttachmentStudentSubmission]) {
						val scopes = Seq("""https://www.googleapis.com/auth/classroom.student-submissions.students.readonly""")
						/** Optional. Deprecated, use `item_id` instead. */
						def withPostId(postId: String) = new get(req.addQueryStringParameters("postId" -> postId.toString))
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.AddOnAttachmentStudentSubmission])
					}
					object get {
						def apply(postId :PlayApi, courseId: String, attachmentId: String, submissionId: String, itemId: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/courses/${courseId}/posts/${postId}/addOnAttachments/${attachmentId}/studentSubmissions/${submissionId}").addQueryStringParameters("itemId" -> itemId.toString))
						given Conversion[get, Future[Schema.AddOnAttachmentStudentSubmission]] = (fun: get) => fun.apply()
					}
				}
			}
		}
		object teachers {
			/** Creates a teacher of a course. Domain administrators are permitted to [directly add](https://developers.google.com/classroom/guides/manage-users) users within their domain as teachers to courses within their domain. Non-admin users should send an Invitation instead. This method returns the following error codes: &#42; `PERMISSION_DENIED` if the requesting user is not permitted to create teachers in this course or for access errors. &#42; `NOT_FOUND` if the requested course ID does not exist. &#42; `FAILED_PRECONDITION` if the requested user's account is disabled, for the following request errors: &#42; CourseMemberLimitReached &#42; CourseNotModifiable &#42; CourseTeacherLimitReached &#42; UserGroupsMembershipLimitReached &#42; InactiveCourseOwner &#42; `ALREADY_EXISTS` if the user is already a teacher or student in the course. */
			class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/classroom.profile.emails""", """https://www.googleapis.com/auth/classroom.profile.photos""", """https://www.googleapis.com/auth/classroom.rosters""")
				/** Perform the request */
				def withTeacher(body: Schema.Teacher) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Teacher])
			}
			object create {
				def apply(courseId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/courses/${courseId}/teachers").addQueryStringParameters())
			}
			/** Returns a teacher of a course. This method returns the following error codes: &#42; `PERMISSION_DENIED` if the requesting user is not permitted to view teachers of this course or for access errors. &#42; `NOT_FOUND` if no teacher of this course has the requested ID or if the course does not exist. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Teacher]) {
				val scopes = Seq("""https://www.googleapis.com/auth/classroom.profile.emails""", """https://www.googleapis.com/auth/classroom.profile.photos""", """https://www.googleapis.com/auth/classroom.rosters""", """https://www.googleapis.com/auth/classroom.rosters.readonly""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Teacher])
			}
			object get {
				def apply(courseId: String, userId: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/courses/${courseId}/teachers/${userId}").addQueryStringParameters())
				given Conversion[get, Future[Schema.Teacher]] = (fun: get) => fun.apply()
			}
			/** Removes the specified teacher from the specified course. This method returns the following error codes: &#42; `PERMISSION_DENIED` if the requesting user is not permitted to delete teachers of this course or for access errors. &#42; `NOT_FOUND` if no teacher of this course has the requested ID or if the course does not exist. &#42; `FAILED_PRECONDITION` if the requested ID belongs to the primary teacher of this course. &#42; `FAILED_PRECONDITION` if the requested ID belongs to the owner of the course Drive folder. &#42; `FAILED_PRECONDITION` if the course no longer has an active owner. */
			class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
				val scopes = Seq("""https://www.googleapis.com/auth/classroom.rosters""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
			}
			object delete {
				def apply(courseId: String, userId: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/courses/${courseId}/teachers/${userId}").addQueryStringParameters())
				given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
			}
			/** Returns a list of teachers of this course that the requester is permitted to view. This method returns the following error codes: &#42; `NOT_FOUND` if the course does not exist. &#42; `PERMISSION_DENIED` for access errors. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListTeachersResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/classroom.profile.emails""", """https://www.googleapis.com/auth/classroom.profile.photos""", """https://www.googleapis.com/auth/classroom.rosters""", """https://www.googleapis.com/auth/classroom.rosters.readonly""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListTeachersResponse])
			}
			object list {
				def apply(courseId: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/courses/${courseId}/teachers").addQueryStringParameters("pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListTeachersResponse]] = (fun: list) => fun.apply()
			}
		}
		object courseWork {
			/** Creates course work. The resulting course work (and corresponding student submissions) are associated with the Developer Console project of the [OAuth client ID](https://support.google.com/cloud/answer/6158849) used to make the request. Classroom API requests to modify course work and student submissions must be made with an OAuth client ID from the associated Developer Console project. This method returns the following error codes: &#42; `PERMISSION_DENIED` if the requesting user is not permitted to access the requested course, create course work in the requested course, share a Drive attachment, or for access errors. &#42; `INVALID_ARGUMENT` if the request is malformed. &#42; `NOT_FOUND` if the requested course does not exist. &#42; `FAILED_PRECONDITION` for the following request error: &#42; AttachmentNotVisible */
			class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/classroom.coursework.students""")
				/** Perform the request */
				def withCourseWork(body: Schema.CourseWork) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.CourseWork])
			}
			object create {
				def apply(courseId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/courses/${courseId}/courseWork").addQueryStringParameters())
			}
			/** Modifies assignee mode and options of a coursework. Only a teacher of the course that contains the coursework may call this method. This method returns the following error codes: &#42; `PERMISSION_DENIED` if the requesting user is not permitted to access the requested course or course work or for access errors. &#42; `INVALID_ARGUMENT` if the request is malformed. &#42; `NOT_FOUND` if the requested course or course work does not exist. */
			class modifyAssignees(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/classroom.coursework.students""")
				/** Perform the request */
				def withModifyCourseWorkAssigneesRequest(body: Schema.ModifyCourseWorkAssigneesRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.CourseWork])
			}
			object modifyAssignees {
				def apply(courseId: String, id: String)(using signer: RequestSigner, ec: ExecutionContext): modifyAssignees = new modifyAssignees(ws.url(BASE_URL + s"v1/courses/${courseId}/courseWork/${id}:modifyAssignees").addQueryStringParameters())
			}
			/** Deletes a course work. This request must be made by the Developer Console project of the [OAuth client ID](https://support.google.com/cloud/answer/6158849) used to create the corresponding course work item. This method returns the following error codes: &#42; `PERMISSION_DENIED` if the requesting developer project did not create the corresponding course work, if the requesting user is not permitted to delete the requested course or for access errors. &#42; `FAILED_PRECONDITION` if the requested course work has already been deleted. &#42; `NOT_FOUND` if no course exists with the requested ID. */
			class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
				val scopes = Seq("""https://www.googleapis.com/auth/classroom.coursework.students""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
			}
			object delete {
				def apply(courseId: String, id: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/courses/${courseId}/courseWork/${id}").addQueryStringParameters())
				given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
			}
			/** Gets metadata for Classroom add-ons in the context of a specific post. To maintain the integrity of its own data and permissions model, an add-on should call this to validate query parameters and the requesting user's role whenever the add-on is opened in an [iframe](https://developers.google.com/classroom/add-ons/get-started/iframes/iframes-overview). This method returns the following error codes: &#42; `PERMISSION_DENIED` for access errors. &#42; `INVALID_ARGUMENT` if the request is malformed. &#42; `NOT_FOUND` if one of the identified resources does not exist. */
			class getAddOnContext(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.AddOnContext]) {
				val scopes = Seq()
				/** Optional. Deprecated, use `item_id` instead. */
				def withPostId(postId: String) = new getAddOnContext(req.addQueryStringParameters("postId" -> postId.toString))
				/** Optional. Token that authorizes the request. The token is passed as a query parameter when the user is redirected from Classroom to the add-on's URL. The authorization token is required when neither of the following is true: &#42; The add-on has attachments on the post. &#42; The developer project issuing the request is the same project that created the post. */
				def withAddOnToken(addOnToken: String) = new getAddOnContext(req.addQueryStringParameters("addOnToken" -> addOnToken.toString))
				/** Optional. The identifier of the attachment. This field is required for all requests except when the user is in the [Attachment Discovery iframe](https://developers.google.com/classroom/add-ons/get-started/iframes/attachment-discovery-iframe). */
				def withAttachmentId(attachmentId: String) = new getAddOnContext(req.addQueryStringParameters("attachmentId" -> attachmentId.toString))
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.AddOnContext])
			}
			object getAddOnContext {
				def apply(courseId: String, itemId: String)(using signer: RequestSigner, ec: ExecutionContext): getAddOnContext = new getAddOnContext(ws.url(BASE_URL + s"v1/courses/${courseId}/courseWork/${itemId}/addOnContext").addQueryStringParameters())
				given Conversion[getAddOnContext, Future[Schema.AddOnContext]] = (fun: getAddOnContext) => fun.apply()
			}
			/** Returns course work. This method returns the following error codes: &#42; `PERMISSION_DENIED` if the requesting user is not permitted to access the requested course or course work, or for access errors. &#42; `INVALID_ARGUMENT` if the request is malformed. &#42; `NOT_FOUND` if the requested course or course work does not exist. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.CourseWork]) {
				val scopes = Seq("""https://www.googleapis.com/auth/classroom.coursework.me""", """https://www.googleapis.com/auth/classroom.coursework.me.readonly""", """https://www.googleapis.com/auth/classroom.coursework.students""", """https://www.googleapis.com/auth/classroom.coursework.students.readonly""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.CourseWork])
			}
			object get {
				def apply(courseId: String, id: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/courses/${courseId}/courseWork/${id}").addQueryStringParameters())
				given Conversion[get, Future[Schema.CourseWork]] = (fun: get) => fun.apply()
			}
			/** Returns a list of course work that the requester is permitted to view. Course students may only view `PUBLISHED` course work. Course teachers and domain administrators may view all course work. This method returns the following error codes: &#42; `PERMISSION_DENIED` if the requesting user is not permitted to access the requested course or for access errors. &#42; `INVALID_ARGUMENT` if the request is malformed. &#42; `NOT_FOUND` if the requested course does not exist. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListCourseWorkResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/classroom.coursework.me""", """https://www.googleapis.com/auth/classroom.coursework.me.readonly""", """https://www.googleapis.com/auth/classroom.coursework.students""", """https://www.googleapis.com/auth/classroom.coursework.students.readonly""")
				/** Optional sort ordering for results. A comma-separated list of fields with an optional sort direction keyword. Supported fields are `updateTime` and `dueDate`. Supported direction keywords are `asc` and `desc`. If not specified, `updateTime desc` is the default behavior. Examples: `dueDate asc,updateTime desc`, `updateTime,dueDate desc` */
				def withOrderBy(orderBy: String) = new list(req.addQueryStringParameters("orderBy" -> orderBy.toString))
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListCourseWorkResponse])
			}
			object list {
				def apply(courseId: String, courseWorkStates: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/courses/${courseId}/courseWork").addQueryStringParameters("courseWorkStates" -> courseWorkStates.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListCourseWorkResponse]] = (fun: list) => fun.apply()
			}
			/** Updates one or more fields of a course work. See google.classroom.v1.CourseWork for details of which fields may be updated and who may change them. This request must be made by the Developer Console project of the [OAuth client ID](https://support.google.com/cloud/answer/6158849) used to create the corresponding course work item. This method returns the following error codes: &#42; `PERMISSION_DENIED` if the requesting developer project did not create the corresponding course work, if the user is not permitted to make the requested modification to the student submission, or for access errors. &#42; `INVALID_ARGUMENT` if the request is malformed. &#42; `FAILED_PRECONDITION` if the requested course work has already been deleted. &#42; `NOT_FOUND` if the requested course or course work does not exist. */
			class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/classroom.coursework.students""")
				/** Perform the request */
				def withCourseWork(body: Schema.CourseWork) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.CourseWork])
			}
			object patch {
				def apply(courseId: String, id: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/courses/${courseId}/courseWork/${id}").addQueryStringParameters("updateMask" -> updateMask.toString))
			}
			object studentSubmissions {
				/** Modifies attachments of student submission. Attachments may only be added to student submissions belonging to course work objects with a `workType` of `ASSIGNMENT`. This request must be made by the Developer Console project of the [OAuth client ID](https://support.google.com/cloud/answer/6158849) used to create the corresponding course work item. This method returns the following error codes: &#42; `PERMISSION_DENIED` if the requesting user is not permitted to access the requested course or course work, if the user is not permitted to modify attachments on the requested student submission, or for access errors. &#42; `INVALID_ARGUMENT` if the request is malformed. &#42; `NOT_FOUND` if the requested course, course work, or student submission does not exist. */
				class modifyAttachments(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/classroom.coursework.me""", """https://www.googleapis.com/auth/classroom.coursework.students""")
					/** Perform the request */
					def withModifyAttachmentsRequest(body: Schema.ModifyAttachmentsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.StudentSubmission])
				}
				object modifyAttachments {
					def apply(courseId: String, courseWorkId: String, id: String)(using signer: RequestSigner, ec: ExecutionContext): modifyAttachments = new modifyAttachments(ws.url(BASE_URL + s"v1/courses/${courseId}/courseWork/${courseWorkId}/studentSubmissions/${id}:modifyAttachments").addQueryStringParameters())
				}
				/** Returns a student submission. Returning a student submission transfers ownership of attached Drive files to the student and may also update the submission state. Unlike the Classroom application, returning a student submission does not set assignedGrade to the draftGrade value. Only a teacher of the course that contains the requested student submission may call this method. This request must be made by the Developer Console project of the [OAuth client ID](https://support.google.com/cloud/answer/6158849) used to create the corresponding course work item. This method returns the following error codes: &#42; `PERMISSION_DENIED` if the requesting user is not permitted to access the requested course or course work, return the requested student submission, or for access errors. &#42; `INVALID_ARGUMENT` if the request is malformed. &#42; `NOT_FOUND` if the requested course, course work, or student submission does not exist. */
				class `return`(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/classroom.coursework.students""")
					/** Perform the request */
					def withReturnStudentSubmissionRequest(body: Schema.ReturnStudentSubmissionRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Empty])
				}
				object `return` {
					def apply(courseId: String, courseWorkId: String, id: String)(using signer: RequestSigner, ec: ExecutionContext): `return` = new `return`(ws.url(BASE_URL + s"v1/courses/${courseId}/courseWork/${courseWorkId}/studentSubmissions/${id}:return").addQueryStringParameters())
				}
				/** Reclaims a student submission on behalf of the student that owns it. Reclaiming a student submission transfers ownership of attached Drive files to the student and updates the submission state. Only the student that owns the requested student submission may call this method, and only for a student submission that has been turned in. This request must be made by the Developer Console project of the [OAuth client ID](https://support.google.com/cloud/answer/6158849) used to create the corresponding course work item. This method returns the following error codes: &#42; `PERMISSION_DENIED` if the requesting user is not permitted to access the requested course or course work, unsubmit the requested student submission, or for access errors. &#42; `FAILED_PRECONDITION` if the student submission has not been turned in. &#42; `INVALID_ARGUMENT` if the request is malformed. &#42; `NOT_FOUND` if the requested course, course work, or student submission does not exist. */
				class reclaim(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/classroom.coursework.me""")
					/** Perform the request */
					def withReclaimStudentSubmissionRequest(body: Schema.ReclaimStudentSubmissionRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Empty])
				}
				object reclaim {
					def apply(courseId: String, courseWorkId: String, id: String)(using signer: RequestSigner, ec: ExecutionContext): reclaim = new reclaim(ws.url(BASE_URL + s"v1/courses/${courseId}/courseWork/${courseWorkId}/studentSubmissions/${id}:reclaim").addQueryStringParameters())
				}
				/** Turns in a student submission. Turning in a student submission transfers ownership of attached Drive files to the teacher and may also update the submission state. This may only be called by the student that owns the specified student submission. This request must be made by the Developer Console project of the [OAuth client ID](https://support.google.com/cloud/answer/6158849) used to create the corresponding course work item. This method returns the following error codes: &#42; `PERMISSION_DENIED` if the requesting user is not permitted to access the requested course or course work, turn in the requested student submission, or for access errors. &#42; `INVALID_ARGUMENT` if the request is malformed. &#42; `NOT_FOUND` if the requested course, course work, or student submission does not exist. */
				class turnIn(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/classroom.coursework.me""")
					/** Perform the request */
					def withTurnInStudentSubmissionRequest(body: Schema.TurnInStudentSubmissionRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Empty])
				}
				object turnIn {
					def apply(courseId: String, courseWorkId: String, id: String)(using signer: RequestSigner, ec: ExecutionContext): turnIn = new turnIn(ws.url(BASE_URL + s"v1/courses/${courseId}/courseWork/${courseWorkId}/studentSubmissions/${id}:turnIn").addQueryStringParameters())
				}
				/** Returns a student submission. &#42; `PERMISSION_DENIED` if the requesting user is not permitted to access the requested course, course work, or student submission or for access errors. &#42; `INVALID_ARGUMENT` if the request is malformed. &#42; `NOT_FOUND` if the requested course, course work, or student submission does not exist. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.StudentSubmission]) {
					val scopes = Seq("""https://www.googleapis.com/auth/classroom.coursework.me""", """https://www.googleapis.com/auth/classroom.coursework.me.readonly""", """https://www.googleapis.com/auth/classroom.coursework.students""", """https://www.googleapis.com/auth/classroom.coursework.students.readonly""", """https://www.googleapis.com/auth/classroom.student-submissions.me.readonly""", """https://www.googleapis.com/auth/classroom.student-submissions.students.readonly""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.StudentSubmission])
				}
				object get {
					def apply(courseId: String, courseWorkId: String, id: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/courses/${courseId}/courseWork/${courseWorkId}/studentSubmissions/${id}").addQueryStringParameters())
					given Conversion[get, Future[Schema.StudentSubmission]] = (fun: get) => fun.apply()
				}
				/** Updates one or more fields of a student submission. See google.classroom.v1.StudentSubmission for details of which fields may be updated and who may change them. This request must be made by the Developer Console project of the [OAuth client ID](https://support.google.com/cloud/answer/6158849) used to create the corresponding course work item. This method returns the following error codes: &#42; `PERMISSION_DENIED` if the requesting developer project did not create the corresponding course work, if the user is not permitted to make the requested modification to the student submission, or for access errors. &#42; `INVALID_ARGUMENT` if the request is malformed. &#42; `NOT_FOUND` if the requested course, course work, or student submission does not exist. */
				class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/classroom.coursework.me""", """https://www.googleapis.com/auth/classroom.coursework.students""")
					/** Perform the request */
					def withStudentSubmission(body: Schema.StudentSubmission) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.StudentSubmission])
				}
				object patch {
					def apply(courseId: String, courseWorkId: String, id: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/courses/${courseId}/courseWork/${courseWorkId}/studentSubmissions/${id}").addQueryStringParameters("updateMask" -> updateMask.toString))
				}
				/** Returns a list of student submissions that the requester is permitted to view, factoring in the OAuth scopes of the request. `-` may be specified as the `course_work_id` to include student submissions for multiple course work items. Course students may only view their own work. Course teachers and domain administrators may view all student submissions. This method returns the following error codes: &#42; `PERMISSION_DENIED` if the requesting user is not permitted to access the requested course or course work, or for access errors. &#42; `INVALID_ARGUMENT` if the request is malformed. &#42; `NOT_FOUND` if the requested course does not exist. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListStudentSubmissionsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/classroom.coursework.me""", """https://www.googleapis.com/auth/classroom.coursework.me.readonly""", """https://www.googleapis.com/auth/classroom.coursework.students""", """https://www.googleapis.com/auth/classroom.coursework.students.readonly""", """https://www.googleapis.com/auth/classroom.student-submissions.me.readonly""", """https://www.googleapis.com/auth/classroom.student-submissions.students.readonly""")
					/** Optional argument to restrict returned student work to those owned by the student with the specified identifier. The identifier can be one of the following: &#42; the numeric identifier for the user &#42; the email address of the user &#42; the string literal `"me"`, indicating the requesting user */
					def withUserId(userId: String) = new list(req.addQueryStringParameters("userId" -> userId.toString))
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListStudentSubmissionsResponse])
				}
				object list {
					def apply(courseId: String, courseWorkId: String, states: String, late: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/courses/${courseId}/courseWork/${courseWorkId}/studentSubmissions").addQueryStringParameters("states" -> states.toString, "late" -> late.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListStudentSubmissionsResponse]] = (fun: list) => fun.apply()
				}
			}
			object addOnAttachments {
				/** Creates an add-on attachment under a post. Requires the add-on to have permission to create new attachments on the post. This method returns the following error codes: &#42; `PERMISSION_DENIED` for access errors. &#42; `INVALID_ARGUMENT` if the request is malformed. &#42; `NOT_FOUND` if one of the identified resources does not exist. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq()
					/** Optional. Deprecated, use `item_id` instead. */
					def withPostId(postId: String) = new create(req.addQueryStringParameters("postId" -> postId.toString))
					/** Optional. Token that authorizes the request. The token is passed as a query parameter when the user is redirected from Classroom to the add-on's URL. This authorization token is required for in-Classroom attachment creation but optional for partner-first attachment creation. Returns an error if not provided for partner-first attachment creation and the developer projects that created the attachment and its parent stream item do not match. */
					def withAddOnToken(addOnToken: String) = new create(req.addQueryStringParameters("addOnToken" -> addOnToken.toString))
					/** Perform the request */
					def withAddOnAttachment(body: Schema.AddOnAttachment) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.AddOnAttachment])
				}
				object create {
					def apply(courseId: String, itemId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/courses/${courseId}/courseWork/${itemId}/addOnAttachments").addQueryStringParameters())
				}
				/** Deletes an add-on attachment. Requires the add-on to have been the original creator of the attachment. This method returns the following error codes: &#42; `PERMISSION_DENIED` for access errors. &#42; `INVALID_ARGUMENT` if the request is malformed. &#42; `NOT_FOUND` if one of the identified resources does not exist. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					val scopes = Seq()
					/** Optional. Deprecated, use `item_id` instead. */
					def withPostId(postId: String) = new delete(req.addQueryStringParameters("postId" -> postId.toString))
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
				}
				object delete {
					def apply(courseId: String, itemId: String, attachmentId: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/courses/${courseId}/courseWork/${itemId}/addOnAttachments/${attachmentId}").addQueryStringParameters())
					given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
				}
				/** Returns an add-on attachment. Requires the add-on requesting the attachment to be the original creator of the attachment. This method returns the following error codes: &#42; `PERMISSION_DENIED` for access errors. &#42; `INVALID_ARGUMENT` if the request is malformed. &#42; `NOT_FOUND` if one of the identified resources does not exist. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.AddOnAttachment]) {
					val scopes = Seq()
					/** Optional. Deprecated, use `item_id` instead. */
					def withPostId(postId: String) = new get(req.addQueryStringParameters("postId" -> postId.toString))
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.AddOnAttachment])
				}
				object get {
					def apply(courseId: String, itemId: String, attachmentId: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/courses/${courseId}/courseWork/${itemId}/addOnAttachments/${attachmentId}").addQueryStringParameters())
					given Conversion[get, Future[Schema.AddOnAttachment]] = (fun: get) => fun.apply()
				}
				/** Updates an add-on attachment. Requires the add-on to have been the original creator of the attachment. This method returns the following error codes: &#42; `PERMISSION_DENIED` for access errors. &#42; `INVALID_ARGUMENT` if the request is malformed. &#42; `NOT_FOUND` if one of the identified resources does not exist. */
				class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq()
					/** Perform the request */
					def withAddOnAttachment(body: Schema.AddOnAttachment) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.AddOnAttachment])
				}
				object patch {
					def apply(courseId: String, itemId: String, attachmentId: String, postId: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/courses/${courseId}/courseWork/${itemId}/addOnAttachments/${attachmentId}").addQueryStringParameters("postId" -> postId.toString, "updateMask" -> updateMask.toString))
				}
				/** Returns all attachments created by an add-on under the post. Requires the add-on to have active attachments on the post or have permission to create new attachments on the post. This method returns the following error codes: &#42; `PERMISSION_DENIED` for access errors. &#42; `INVALID_ARGUMENT` if the request is malformed. &#42; `NOT_FOUND` if one of the identified resources does not exist. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListAddOnAttachmentsResponse]) {
					val scopes = Seq()
					/** Optional. Identifier of the post under the course whose attachments to enumerate. Deprecated, use `item_id` instead. */
					def withPostId(postId: String) = new list(req.addQueryStringParameters("postId" -> postId.toString))
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListAddOnAttachmentsResponse])
				}
				object list {
					def apply(courseId: String, itemId: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/courses/${courseId}/courseWork/${itemId}/addOnAttachments").addQueryStringParameters("pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListAddOnAttachmentsResponse]] = (fun: list) => fun.apply()
				}
				object studentSubmissions {
					/** Updates data associated with an add-on attachment submission. Requires the add-on to have been the original creator of the attachment and the attachment to have a positive `max_points` value set. This method returns the following error codes: &#42; `PERMISSION_DENIED` for access errors. &#42; `INVALID_ARGUMENT` if the request is malformed. &#42; `NOT_FOUND` if one of the identified resources does not exist. */
					class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq()
						/** Optional. Deprecated, use `item_id` instead. */
						def withPostId(postId: String) = new patch(req.addQueryStringParameters("postId" -> postId.toString))
						/** Perform the request */
						def withAddOnAttachmentStudentSubmission(body: Schema.AddOnAttachmentStudentSubmission) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.AddOnAttachmentStudentSubmission])
					}
					object patch {
						def apply(courseId: String, itemId: String, attachmentId: String, submissionId: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/courses/${courseId}/courseWork/${itemId}/addOnAttachments/${attachmentId}/studentSubmissions/${submissionId}").addQueryStringParameters("updateMask" -> updateMask.toString))
					}
					/** Returns a student submission for an add-on attachment. This method returns the following error codes: &#42; `PERMISSION_DENIED` for access errors. &#42; `INVALID_ARGUMENT` if the request is malformed. &#42; `NOT_FOUND` if one of the identified resources does not exist. */
					class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.AddOnAttachmentStudentSubmission]) {
						val scopes = Seq("""https://www.googleapis.com/auth/classroom.student-submissions.students.readonly""")
						/** Optional. Deprecated, use `item_id` instead. */
						def withPostId(postId: String) = new get(req.addQueryStringParameters("postId" -> postId.toString))
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.AddOnAttachmentStudentSubmission])
					}
					object get {
						def apply(courseId: String, itemId: String, attachmentId: String, submissionId: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/courses/${courseId}/courseWork/${itemId}/addOnAttachments/${attachmentId}/studentSubmissions/${submissionId}").addQueryStringParameters())
						given Conversion[get, Future[Schema.AddOnAttachmentStudentSubmission]] = (fun: get) => fun.apply()
					}
				}
			}
		}
		object announcements {
			/** Creates an announcement. This method returns the following error codes: &#42; `PERMISSION_DENIED` if the requesting user is not permitted to access the requested course, create announcements in the requested course, share a Drive attachment, or for access errors. &#42; `INVALID_ARGUMENT` if the request is malformed. &#42; `NOT_FOUND` if the requested course does not exist. &#42; `FAILED_PRECONDITION` for the following request error: &#42; AttachmentNotVisible */
			class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/classroom.announcements""")
				/** Perform the request */
				def withAnnouncement(body: Schema.Announcement) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Announcement])
			}
			object create {
				def apply(courseId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/courses/${courseId}/announcements").addQueryStringParameters())
			}
			/** Modifies assignee mode and options of an announcement. Only a teacher of the course that contains the announcement may call this method. This method returns the following error codes: &#42; `PERMISSION_DENIED` if the requesting user is not permitted to access the requested course or course work or for access errors. &#42; `INVALID_ARGUMENT` if the request is malformed. &#42; `NOT_FOUND` if the requested course or course work does not exist. */
			class modifyAssignees(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/classroom.announcements""")
				/** Perform the request */
				def withModifyAnnouncementAssigneesRequest(body: Schema.ModifyAnnouncementAssigneesRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Announcement])
			}
			object modifyAssignees {
				def apply(courseId: String, id: String)(using signer: RequestSigner, ec: ExecutionContext): modifyAssignees = new modifyAssignees(ws.url(BASE_URL + s"v1/courses/${courseId}/announcements/${id}:modifyAssignees").addQueryStringParameters())
			}
			/** Deletes an announcement. This request must be made by the Developer Console project of the [OAuth client ID](https://support.google.com/cloud/answer/6158849) used to create the corresponding announcement item. This method returns the following error codes: &#42; `PERMISSION_DENIED` if the requesting developer project did not create the corresponding announcement, if the requesting user is not permitted to delete the requested course or for access errors. &#42; `FAILED_PRECONDITION` if the requested announcement has already been deleted. &#42; `NOT_FOUND` if no course exists with the requested ID. */
			class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
				val scopes = Seq("""https://www.googleapis.com/auth/classroom.announcements""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
			}
			object delete {
				def apply(courseId: String, id: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/courses/${courseId}/announcements/${id}").addQueryStringParameters())
				given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
			}
			/** Gets metadata for Classroom add-ons in the context of a specific post. To maintain the integrity of its own data and permissions model, an add-on should call this to validate query parameters and the requesting user's role whenever the add-on is opened in an [iframe](https://developers.google.com/classroom/add-ons/get-started/iframes/iframes-overview). This method returns the following error codes: &#42; `PERMISSION_DENIED` for access errors. &#42; `INVALID_ARGUMENT` if the request is malformed. &#42; `NOT_FOUND` if one of the identified resources does not exist. */
			class getAddOnContext(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.AddOnContext]) {
				val scopes = Seq()
				/** Optional. Deprecated, use `item_id` instead. */
				def withPostId(postId: String) = new getAddOnContext(req.addQueryStringParameters("postId" -> postId.toString))
				/** Optional. Token that authorizes the request. The token is passed as a query parameter when the user is redirected from Classroom to the add-on's URL. The authorization token is required when neither of the following is true: &#42; The add-on has attachments on the post. &#42; The developer project issuing the request is the same project that created the post. */
				def withAddOnToken(addOnToken: String) = new getAddOnContext(req.addQueryStringParameters("addOnToken" -> addOnToken.toString))
				/** Optional. The identifier of the attachment. This field is required for all requests except when the user is in the [Attachment Discovery iframe](https://developers.google.com/classroom/add-ons/get-started/iframes/attachment-discovery-iframe). */
				def withAttachmentId(attachmentId: String) = new getAddOnContext(req.addQueryStringParameters("attachmentId" -> attachmentId.toString))
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.AddOnContext])
			}
			object getAddOnContext {
				def apply(courseId: String, itemId: String)(using signer: RequestSigner, ec: ExecutionContext): getAddOnContext = new getAddOnContext(ws.url(BASE_URL + s"v1/courses/${courseId}/announcements/${itemId}/addOnContext").addQueryStringParameters())
				given Conversion[getAddOnContext, Future[Schema.AddOnContext]] = (fun: getAddOnContext) => fun.apply()
			}
			/** Returns an announcement. This method returns the following error codes: &#42; `PERMISSION_DENIED` if the requesting user is not permitted to access the requested course or announcement, or for access errors. &#42; `INVALID_ARGUMENT` if the request is malformed. &#42; `NOT_FOUND` if the requested course or announcement does not exist. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Announcement]) {
				val scopes = Seq("""https://www.googleapis.com/auth/classroom.announcements""", """https://www.googleapis.com/auth/classroom.announcements.readonly""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Announcement])
			}
			object get {
				def apply(courseId: String, id: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/courses/${courseId}/announcements/${id}").addQueryStringParameters())
				given Conversion[get, Future[Schema.Announcement]] = (fun: get) => fun.apply()
			}
			/** Returns a list of announcements that the requester is permitted to view. Course students may only view `PUBLISHED` announcements. Course teachers and domain administrators may view all announcements. This method returns the following error codes: &#42; `PERMISSION_DENIED` if the requesting user is not permitted to access the requested course or for access errors. &#42; `INVALID_ARGUMENT` if the request is malformed. &#42; `NOT_FOUND` if the requested course does not exist. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListAnnouncementsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/classroom.announcements""", """https://www.googleapis.com/auth/classroom.announcements.readonly""")
				/** Optional sort ordering for results. A comma-separated list of fields with an optional sort direction keyword. Supported field is `updateTime`. Supported direction keywords are `asc` and `desc`. If not specified, `updateTime desc` is the default behavior. Examples: `updateTime asc`, `updateTime` */
				def withOrderBy(orderBy: String) = new list(req.addQueryStringParameters("orderBy" -> orderBy.toString))
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListAnnouncementsResponse])
			}
			object list {
				def apply(courseId: String, announcementStates: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/courses/${courseId}/announcements").addQueryStringParameters("announcementStates" -> announcementStates.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListAnnouncementsResponse]] = (fun: list) => fun.apply()
			}
			/** Updates one or more fields of an announcement. This method returns the following error codes: &#42; `PERMISSION_DENIED` if the requesting developer project did not create the corresponding announcement or for access errors. &#42; `INVALID_ARGUMENT` if the request is malformed. &#42; `FAILED_PRECONDITION` if the requested announcement has already been deleted. &#42; `NOT_FOUND` if the requested course or announcement does not exist */
			class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/classroom.announcements""")
				/** Perform the request */
				def withAnnouncement(body: Schema.Announcement) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Announcement])
			}
			object patch {
				def apply(courseId: String, id: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/courses/${courseId}/announcements/${id}").addQueryStringParameters("updateMask" -> updateMask.toString))
			}
			object addOnAttachments {
				/** Creates an add-on attachment under a post. Requires the add-on to have permission to create new attachments on the post. This method returns the following error codes: &#42; `PERMISSION_DENIED` for access errors. &#42; `INVALID_ARGUMENT` if the request is malformed. &#42; `NOT_FOUND` if one of the identified resources does not exist. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq()
					/** Optional. Deprecated, use `item_id` instead. */
					def withPostId(postId: String) = new create(req.addQueryStringParameters("postId" -> postId.toString))
					/** Optional. Token that authorizes the request. The token is passed as a query parameter when the user is redirected from Classroom to the add-on's URL. This authorization token is required for in-Classroom attachment creation but optional for partner-first attachment creation. Returns an error if not provided for partner-first attachment creation and the developer projects that created the attachment and its parent stream item do not match. */
					def withAddOnToken(addOnToken: String) = new create(req.addQueryStringParameters("addOnToken" -> addOnToken.toString))
					/** Perform the request */
					def withAddOnAttachment(body: Schema.AddOnAttachment) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.AddOnAttachment])
				}
				object create {
					def apply(courseId: String, itemId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/courses/${courseId}/announcements/${itemId}/addOnAttachments").addQueryStringParameters())
				}
				/** Deletes an add-on attachment. Requires the add-on to have been the original creator of the attachment. This method returns the following error codes: &#42; `PERMISSION_DENIED` for access errors. &#42; `INVALID_ARGUMENT` if the request is malformed. &#42; `NOT_FOUND` if one of the identified resources does not exist. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					val scopes = Seq()
					/** Optional. Deprecated, use `item_id` instead. */
					def withPostId(postId: String) = new delete(req.addQueryStringParameters("postId" -> postId.toString))
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
				}
				object delete {
					def apply(courseId: String, itemId: String, attachmentId: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/courses/${courseId}/announcements/${itemId}/addOnAttachments/${attachmentId}").addQueryStringParameters())
					given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
				}
				/** Returns an add-on attachment. Requires the add-on requesting the attachment to be the original creator of the attachment. This method returns the following error codes: &#42; `PERMISSION_DENIED` for access errors. &#42; `INVALID_ARGUMENT` if the request is malformed. &#42; `NOT_FOUND` if one of the identified resources does not exist. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.AddOnAttachment]) {
					val scopes = Seq()
					/** Optional. Deprecated, use `item_id` instead. */
					def withPostId(postId: String) = new get(req.addQueryStringParameters("postId" -> postId.toString))
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.AddOnAttachment])
				}
				object get {
					def apply(courseId: String, itemId: String, attachmentId: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/courses/${courseId}/announcements/${itemId}/addOnAttachments/${attachmentId}").addQueryStringParameters())
					given Conversion[get, Future[Schema.AddOnAttachment]] = (fun: get) => fun.apply()
				}
				/** Updates an add-on attachment. Requires the add-on to have been the original creator of the attachment. This method returns the following error codes: &#42; `PERMISSION_DENIED` for access errors. &#42; `INVALID_ARGUMENT` if the request is malformed. &#42; `NOT_FOUND` if one of the identified resources does not exist. */
				class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq()
					/** Perform the request */
					def withAddOnAttachment(body: Schema.AddOnAttachment) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.AddOnAttachment])
				}
				object patch {
					def apply(courseId: String, itemId: String, attachmentId: String, postId: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/courses/${courseId}/announcements/${itemId}/addOnAttachments/${attachmentId}").addQueryStringParameters("postId" -> postId.toString, "updateMask" -> updateMask.toString))
				}
				/** Returns all attachments created by an add-on under the post. Requires the add-on to have active attachments on the post or have permission to create new attachments on the post. This method returns the following error codes: &#42; `PERMISSION_DENIED` for access errors. &#42; `INVALID_ARGUMENT` if the request is malformed. &#42; `NOT_FOUND` if one of the identified resources does not exist. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListAddOnAttachmentsResponse]) {
					val scopes = Seq()
					/** Optional. Identifier of the post under the course whose attachments to enumerate. Deprecated, use `item_id` instead. */
					def withPostId(postId: String) = new list(req.addQueryStringParameters("postId" -> postId.toString))
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListAddOnAttachmentsResponse])
				}
				object list {
					def apply(courseId: String, itemId: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/courses/${courseId}/announcements/${itemId}/addOnAttachments").addQueryStringParameters("pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListAddOnAttachmentsResponse]] = (fun: list) => fun.apply()
				}
			}
		}
		object topics {
			/** Creates a topic. This method returns the following error codes: &#42; `PERMISSION_DENIED` if the requesting user is not permitted to access the requested course, create a topic in the requested course, or for access errors. &#42; `INVALID_ARGUMENT` if the request is malformed. &#42; `NOT_FOUND` if the requested course does not exist. */
			class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/classroom.topics""")
				/** Perform the request */
				def withTopic(body: Schema.Topic) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Topic])
			}
			object create {
				def apply(courseId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/courses/${courseId}/topics").addQueryStringParameters())
			}
			/** Deletes a topic. This method returns the following error codes: &#42; `PERMISSION_DENIED` if the requesting user is not allowed to delete the requested topic or for access errors. &#42; `FAILED_PRECONDITION` if the requested topic has already been deleted. &#42; `NOT_FOUND` if no course or topic exists with the requested ID. */
			class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
				val scopes = Seq("""https://www.googleapis.com/auth/classroom.topics""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
			}
			object delete {
				def apply(courseId: String, id: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/courses/${courseId}/topics/${id}").addQueryStringParameters())
				given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
			}
			/** Returns a topic. This method returns the following error codes: &#42; `PERMISSION_DENIED` if the requesting user is not permitted to access the requested course or topic, or for access errors. &#42; `INVALID_ARGUMENT` if the request is malformed. &#42; `NOT_FOUND` if the requested course or topic does not exist. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Topic]) {
				val scopes = Seq("""https://www.googleapis.com/auth/classroom.topics""", """https://www.googleapis.com/auth/classroom.topics.readonly""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Topic])
			}
			object get {
				def apply(courseId: String, id: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/courses/${courseId}/topics/${id}").addQueryStringParameters())
				given Conversion[get, Future[Schema.Topic]] = (fun: get) => fun.apply()
			}
			/** Updates one or more fields of a topic. This method returns the following error codes: &#42; `PERMISSION_DENIED` if the requesting developer project did not create the corresponding topic or for access errors. &#42; `INVALID_ARGUMENT` if the request is malformed. &#42; `NOT_FOUND` if the requested course or topic does not exist */
			class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/classroom.topics""")
				/** Perform the request */
				def withTopic(body: Schema.Topic) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Topic])
			}
			object patch {
				def apply(courseId: String, id: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/courses/${courseId}/topics/${id}").addQueryStringParameters("updateMask" -> updateMask.toString))
			}
			/** Returns the list of topics that the requester is permitted to view. This method returns the following error codes: &#42; `PERMISSION_DENIED` if the requesting user is not permitted to access the requested course or for access errors. &#42; `INVALID_ARGUMENT` if the request is malformed. &#42; `NOT_FOUND` if the requested course does not exist. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListTopicResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/classroom.topics""", """https://www.googleapis.com/auth/classroom.topics.readonly""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListTopicResponse])
			}
			object list {
				def apply(courseId: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/courses/${courseId}/topics").addQueryStringParameters("pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListTopicResponse]] = (fun: list) => fun.apply()
			}
		}
		object courseWorkMaterials {
			/** Creates a course work material. This method returns the following error codes: &#42; `PERMISSION_DENIED` if the requesting user is not permitted to access the requested course, create course work material in the requested course, share a Drive attachment, or for access errors. &#42; `INVALID_ARGUMENT` if the request is malformed or if more than 20 &#42; materials are provided. &#42; `NOT_FOUND` if the requested course does not exist. &#42; `FAILED_PRECONDITION` for the following request error: &#42; AttachmentNotVisible */
			class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/classroom.courseworkmaterials""")
				/** Perform the request */
				def withCourseWorkMaterial(body: Schema.CourseWorkMaterial) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.CourseWorkMaterial])
			}
			object create {
				def apply(courseId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/courses/${courseId}/courseWorkMaterials").addQueryStringParameters())
			}
			/** Deletes a course work material. This request must be made by the Developer Console project of the [OAuth client ID](https://support.google.com/cloud/answer/6158849) used to create the corresponding course work material item. This method returns the following error codes: &#42; `PERMISSION_DENIED` if the requesting developer project did not create the corresponding course work material, if the requesting user is not permitted to delete the requested course or for access errors. &#42; `FAILED_PRECONDITION` if the requested course work material has already been deleted. &#42; `NOT_FOUND` if no course exists with the requested ID. */
			class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
				val scopes = Seq("""https://www.googleapis.com/auth/classroom.courseworkmaterials""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
			}
			object delete {
				def apply(courseId: String, id: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/courses/${courseId}/courseWorkMaterials/${id}").addQueryStringParameters())
				given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
			}
			/** Gets metadata for Classroom add-ons in the context of a specific post. To maintain the integrity of its own data and permissions model, an add-on should call this to validate query parameters and the requesting user's role whenever the add-on is opened in an [iframe](https://developers.google.com/classroom/add-ons/get-started/iframes/iframes-overview). This method returns the following error codes: &#42; `PERMISSION_DENIED` for access errors. &#42; `INVALID_ARGUMENT` if the request is malformed. &#42; `NOT_FOUND` if one of the identified resources does not exist. */
			class getAddOnContext(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.AddOnContext]) {
				val scopes = Seq()
				/** Optional. Deprecated, use `item_id` instead. */
				def withPostId(postId: String) = new getAddOnContext(req.addQueryStringParameters("postId" -> postId.toString))
				/** Optional. Token that authorizes the request. The token is passed as a query parameter when the user is redirected from Classroom to the add-on's URL. The authorization token is required when neither of the following is true: &#42; The add-on has attachments on the post. &#42; The developer project issuing the request is the same project that created the post. */
				def withAddOnToken(addOnToken: String) = new getAddOnContext(req.addQueryStringParameters("addOnToken" -> addOnToken.toString))
				/** Optional. The identifier of the attachment. This field is required for all requests except when the user is in the [Attachment Discovery iframe](https://developers.google.com/classroom/add-ons/get-started/iframes/attachment-discovery-iframe). */
				def withAttachmentId(attachmentId: String) = new getAddOnContext(req.addQueryStringParameters("attachmentId" -> attachmentId.toString))
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.AddOnContext])
			}
			object getAddOnContext {
				def apply(courseId: String, itemId: String)(using signer: RequestSigner, ec: ExecutionContext): getAddOnContext = new getAddOnContext(ws.url(BASE_URL + s"v1/courses/${courseId}/courseWorkMaterials/${itemId}/addOnContext").addQueryStringParameters())
				given Conversion[getAddOnContext, Future[Schema.AddOnContext]] = (fun: getAddOnContext) => fun.apply()
			}
			/** Returns a course work material. This method returns the following error codes: &#42; `PERMISSION_DENIED` if the requesting user is not permitted to access the requested course or course work material, or for access errors. &#42; `INVALID_ARGUMENT` if the request is malformed. &#42; `NOT_FOUND` if the requested course or course work material does not exist. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.CourseWorkMaterial]) {
				val scopes = Seq("""https://www.googleapis.com/auth/classroom.courseworkmaterials""", """https://www.googleapis.com/auth/classroom.courseworkmaterials.readonly""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.CourseWorkMaterial])
			}
			object get {
				def apply(courseId: String, id: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/courses/${courseId}/courseWorkMaterials/${id}").addQueryStringParameters())
				given Conversion[get, Future[Schema.CourseWorkMaterial]] = (fun: get) => fun.apply()
			}
			/** Returns a list of course work material that the requester is permitted to view. Course students may only view `PUBLISHED` course work material. Course teachers and domain administrators may view all course work material. This method returns the following error codes: &#42; `PERMISSION_DENIED` if the requesting user is not permitted to access the requested course or for access errors. &#42; `INVALID_ARGUMENT` if the request is malformed. &#42; `NOT_FOUND` if the requested course does not exist. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListCourseWorkMaterialResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/classroom.courseworkmaterials""", """https://www.googleapis.com/auth/classroom.courseworkmaterials.readonly""")
				/** Optional sort ordering for results. A comma-separated list of fields with an optional sort direction keyword. Supported field is `updateTime`. Supported direction keywords are `asc` and `desc`. If not specified, `updateTime desc` is the default behavior. Examples: `updateTime asc`, `updateTime` */
				def withOrderBy(orderBy: String) = new list(req.addQueryStringParameters("orderBy" -> orderBy.toString))
				/** Optional filtering for course work material with at least one link material whose URL partially matches the provided string. */
				def withMaterialLink(materialLink: String) = new list(req.addQueryStringParameters("materialLink" -> materialLink.toString))
				/** Optional filtering for course work material with at least one Drive material whose ID matches the provided string. If `material_link` is also specified, course work material must have materials matching both filters. */
				def withMaterialDriveId(materialDriveId: String) = new list(req.addQueryStringParameters("materialDriveId" -> materialDriveId.toString))
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListCourseWorkMaterialResponse])
			}
			object list {
				def apply(courseId: String, courseWorkMaterialStates: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/courses/${courseId}/courseWorkMaterials").addQueryStringParameters("courseWorkMaterialStates" -> courseWorkMaterialStates.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListCourseWorkMaterialResponse]] = (fun: list) => fun.apply()
			}
			/** Updates one or more fields of a course work material. This method returns the following error codes: &#42; `PERMISSION_DENIED` if the requesting developer project for access errors. &#42; `INVALID_ARGUMENT` if the request is malformed. &#42; `FAILED_PRECONDITION` if the requested course work material has already been deleted. &#42; `NOT_FOUND` if the requested course or course work material does not exist */
			class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/classroom.courseworkmaterials""")
				/** Perform the request */
				def withCourseWorkMaterial(body: Schema.CourseWorkMaterial) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.CourseWorkMaterial])
			}
			object patch {
				def apply(courseId: String, id: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/courses/${courseId}/courseWorkMaterials/${id}").addQueryStringParameters("updateMask" -> updateMask.toString))
			}
			object addOnAttachments {
				/** Creates an add-on attachment under a post. Requires the add-on to have permission to create new attachments on the post. This method returns the following error codes: &#42; `PERMISSION_DENIED` for access errors. &#42; `INVALID_ARGUMENT` if the request is malformed. &#42; `NOT_FOUND` if one of the identified resources does not exist. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq()
					/** Optional. Deprecated, use `item_id` instead. */
					def withPostId(postId: String) = new create(req.addQueryStringParameters("postId" -> postId.toString))
					/** Optional. Token that authorizes the request. The token is passed as a query parameter when the user is redirected from Classroom to the add-on's URL. This authorization token is required for in-Classroom attachment creation but optional for partner-first attachment creation. Returns an error if not provided for partner-first attachment creation and the developer projects that created the attachment and its parent stream item do not match. */
					def withAddOnToken(addOnToken: String) = new create(req.addQueryStringParameters("addOnToken" -> addOnToken.toString))
					/** Perform the request */
					def withAddOnAttachment(body: Schema.AddOnAttachment) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.AddOnAttachment])
				}
				object create {
					def apply(courseId: String, itemId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/courses/${courseId}/courseWorkMaterials/${itemId}/addOnAttachments").addQueryStringParameters())
				}
				/** Deletes an add-on attachment. Requires the add-on to have been the original creator of the attachment. This method returns the following error codes: &#42; `PERMISSION_DENIED` for access errors. &#42; `INVALID_ARGUMENT` if the request is malformed. &#42; `NOT_FOUND` if one of the identified resources does not exist. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					val scopes = Seq()
					/** Optional. Deprecated, use `item_id` instead. */
					def withPostId(postId: String) = new delete(req.addQueryStringParameters("postId" -> postId.toString))
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
				}
				object delete {
					def apply(courseId: String, itemId: String, attachmentId: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/courses/${courseId}/courseWorkMaterials/${itemId}/addOnAttachments/${attachmentId}").addQueryStringParameters())
					given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
				}
				/** Returns an add-on attachment. Requires the add-on requesting the attachment to be the original creator of the attachment. This method returns the following error codes: &#42; `PERMISSION_DENIED` for access errors. &#42; `INVALID_ARGUMENT` if the request is malformed. &#42; `NOT_FOUND` if one of the identified resources does not exist. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.AddOnAttachment]) {
					val scopes = Seq()
					/** Optional. Deprecated, use `item_id` instead. */
					def withPostId(postId: String) = new get(req.addQueryStringParameters("postId" -> postId.toString))
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.AddOnAttachment])
				}
				object get {
					def apply(courseId: String, itemId: String, attachmentId: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/courses/${courseId}/courseWorkMaterials/${itemId}/addOnAttachments/${attachmentId}").addQueryStringParameters())
					given Conversion[get, Future[Schema.AddOnAttachment]] = (fun: get) => fun.apply()
				}
				/** Updates an add-on attachment. Requires the add-on to have been the original creator of the attachment. This method returns the following error codes: &#42; `PERMISSION_DENIED` for access errors. &#42; `INVALID_ARGUMENT` if the request is malformed. &#42; `NOT_FOUND` if one of the identified resources does not exist. */
				class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq()
					/** Perform the request */
					def withAddOnAttachment(body: Schema.AddOnAttachment) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.AddOnAttachment])
				}
				object patch {
					def apply(courseId: String, itemId: String, attachmentId: String, postId: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/courses/${courseId}/courseWorkMaterials/${itemId}/addOnAttachments/${attachmentId}").addQueryStringParameters("postId" -> postId.toString, "updateMask" -> updateMask.toString))
				}
				/** Returns all attachments created by an add-on under the post. Requires the add-on to have active attachments on the post or have permission to create new attachments on the post. This method returns the following error codes: &#42; `PERMISSION_DENIED` for access errors. &#42; `INVALID_ARGUMENT` if the request is malformed. &#42; `NOT_FOUND` if one of the identified resources does not exist. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListAddOnAttachmentsResponse]) {
					val scopes = Seq()
					/** Optional. Identifier of the post under the course whose attachments to enumerate. Deprecated, use `item_id` instead. */
					def withPostId(postId: String) = new list(req.addQueryStringParameters("postId" -> postId.toString))
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListAddOnAttachmentsResponse])
				}
				object list {
					def apply(courseId: String, itemId: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/courses/${courseId}/courseWorkMaterials/${itemId}/addOnAttachments").addQueryStringParameters("pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListAddOnAttachmentsResponse]] = (fun: list) => fun.apply()
				}
			}
		}
		object students {
			/** Adds a user as a student of a course. Domain administrators are permitted to [directly add](https://developers.google.com/classroom/guides/manage-users) users within their domain as students to courses within their domain. Students are permitted to add themselves to a course using an enrollment code. This method returns the following error codes: &#42; `PERMISSION_DENIED` if the requesting user is not permitted to create students in this course or for access errors. &#42; `NOT_FOUND` if the requested course ID does not exist. &#42; `FAILED_PRECONDITION` if the requested user's account is disabled, for the following request errors: &#42; CourseMemberLimitReached &#42; CourseNotModifiable &#42; UserGroupsMembershipLimitReached &#42; InactiveCourseOwner &#42; `ALREADY_EXISTS` if the user is already a student or teacher in the course. */
			class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/classroom.profile.emails""", """https://www.googleapis.com/auth/classroom.profile.photos""", """https://www.googleapis.com/auth/classroom.rosters""")
				/** Perform the request */
				def withStudent(body: Schema.Student) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Student])
			}
			object create {
				def apply(courseId: String, enrollmentCode: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/courses/${courseId}/students").addQueryStringParameters("enrollmentCode" -> enrollmentCode.toString))
			}
			/** Returns a student of a course. This method returns the following error codes: &#42; `PERMISSION_DENIED` if the requesting user is not permitted to view students of this course or for access errors. &#42; `NOT_FOUND` if no student of this course has the requested ID or if the course does not exist. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Student]) {
				val scopes = Seq("""https://www.googleapis.com/auth/classroom.profile.emails""", """https://www.googleapis.com/auth/classroom.profile.photos""", """https://www.googleapis.com/auth/classroom.rosters""", """https://www.googleapis.com/auth/classroom.rosters.readonly""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Student])
			}
			object get {
				def apply(courseId: String, userId: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/courses/${courseId}/students/${userId}").addQueryStringParameters())
				given Conversion[get, Future[Schema.Student]] = (fun: get) => fun.apply()
			}
			/** Deletes a student of a course. This method returns the following error codes: &#42; `PERMISSION_DENIED` if the requesting user is not permitted to delete students of this course or for access errors. &#42; `NOT_FOUND` if no student of this course has the requested ID or if the course does not exist. */
			class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
				val scopes = Seq("""https://www.googleapis.com/auth/classroom.rosters""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
			}
			object delete {
				def apply(courseId: String, userId: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/courses/${courseId}/students/${userId}").addQueryStringParameters())
				given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
			}
			/** Returns a list of students of this course that the requester is permitted to view. This method returns the following error codes: &#42; `NOT_FOUND` if the course does not exist. &#42; `PERMISSION_DENIED` for access errors. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListStudentsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/classroom.profile.emails""", """https://www.googleapis.com/auth/classroom.profile.photos""", """https://www.googleapis.com/auth/classroom.rosters""", """https://www.googleapis.com/auth/classroom.rosters.readonly""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListStudentsResponse])
			}
			object list {
				def apply(courseId: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/courses/${courseId}/students").addQueryStringParameters("pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListStudentsResponse]] = (fun: list) => fun.apply()
			}
		}
	}
	object userProfiles {
		/** Returns a user profile. This method returns the following error codes: &#42; `PERMISSION_DENIED` if the requesting user is not permitted to access this user profile, if no profile exists with the requested ID, or for access errors. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.UserProfile]) {
			val scopes = Seq("""https://www.googleapis.com/auth/classroom.profile.emails""", """https://www.googleapis.com/auth/classroom.profile.photos""", """https://www.googleapis.com/auth/classroom.rosters""", """https://www.googleapis.com/auth/classroom.rosters.readonly""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.UserProfile])
		}
		object get {
			def apply(userId: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/userProfiles/${userId}").addQueryStringParameters())
			given Conversion[get, Future[Schema.UserProfile]] = (fun: get) => fun.apply()
		}
		object guardianInvitations {
			/** Returns a list of guardian invitations that the requesting user is permitted to view, filtered by the parameters provided. This method returns the following error codes: &#42; `PERMISSION_DENIED` if a `student_id` is specified, and the requesting user is not permitted to view guardian invitations for that student, if `"-"` is specified as the `student_id` and the user is not a domain administrator, if guardians are not enabled for the domain in question, or for other access errors. &#42; `INVALID_ARGUMENT` if a `student_id` is specified, but its format cannot be recognized (it is not an email address, nor a `student_id` from the API, nor the literal string `me`). May also be returned if an invalid `page_token` or `state` is provided. &#42; `NOT_FOUND` if a `student_id` is specified, and its format can be recognized, but Classroom has no record of that student. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListGuardianInvitationsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/classroom.guardianlinks.students""", """https://www.googleapis.com/auth/classroom.guardianlinks.students.readonly""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListGuardianInvitationsResponse])
			}
			object list {
				def apply(studentId: String, invitedEmailAddress: String, states: String, pageToken: String, pageSize: Int)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/userProfiles/${studentId}/guardianInvitations").addQueryStringParameters("invitedEmailAddress" -> invitedEmailAddress.toString, "states" -> states.toString, "pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString))
				given Conversion[list, Future[Schema.ListGuardianInvitationsResponse]] = (fun: list) => fun.apply()
			}
			/** Returns a specific guardian invitation. This method returns the following error codes: &#42; `PERMISSION_DENIED` if the requesting user is not permitted to view guardian invitations for the student identified by the `student_id`, if guardians are not enabled for the domain in question, or for other access errors. &#42; `INVALID_ARGUMENT` if a `student_id` is specified, but its format cannot be recognized (it is not an email address, nor a `student_id` from the API, nor the literal string `me`). &#42; `NOT_FOUND` if Classroom cannot find any record of the given student or `invitation_id`. May also be returned if the student exists, but the requesting user does not have access to see that student. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GuardianInvitation]) {
				val scopes = Seq("""https://www.googleapis.com/auth/classroom.guardianlinks.students""", """https://www.googleapis.com/auth/classroom.guardianlinks.students.readonly""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GuardianInvitation])
			}
			object get {
				def apply(studentId: String, invitationId: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/userProfiles/${studentId}/guardianInvitations/${invitationId}").addQueryStringParameters())
				given Conversion[get, Future[Schema.GuardianInvitation]] = (fun: get) => fun.apply()
			}
			/** Creates a guardian invitation, and sends an email to the guardian asking them to confirm that they are the student's guardian. Once the guardian accepts the invitation, their `state` will change to `COMPLETED` and they will start receiving guardian notifications. A `Guardian` resource will also be created to represent the active guardian. The request object must have the `student_id` and `invited_email_address` fields set. Failing to set these fields, or setting any other fields in the request, will result in an error. This method returns the following error codes: &#42; `PERMISSION_DENIED` if the current user does not have permission to manage guardians, if the guardian in question has already rejected too many requests for that student, if guardians are not enabled for the domain in question, or for other access errors. &#42; `RESOURCE_EXHAUSTED` if the student or guardian has exceeded the guardian link limit. &#42; `INVALID_ARGUMENT` if the guardian email address is not valid (for example, if it is too long), or if the format of the student ID provided cannot be recognized (it is not an email address, nor a `user_id` from this API). This error will also be returned if read-only fields are set, or if the `state` field is set to to a value other than `PENDING`. &#42; `NOT_FOUND` if the student ID provided is a valid student ID, but Classroom has no record of that student. &#42; `ALREADY_EXISTS` if there is already a pending guardian invitation for the student and `invited_email_address` provided, or if the provided `invited_email_address` matches the Google account of an existing `Guardian` for this user. */
			class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/classroom.guardianlinks.students""")
				/** Perform the request */
				def withGuardianInvitation(body: Schema.GuardianInvitation) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GuardianInvitation])
			}
			object create {
				def apply(studentId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/userProfiles/${studentId}/guardianInvitations").addQueryStringParameters())
			}
			/** Modifies a guardian invitation. Currently, the only valid modification is to change the `state` from `PENDING` to `COMPLETE`. This has the effect of withdrawing the invitation. This method returns the following error codes: &#42; `PERMISSION_DENIED` if the current user does not have permission to manage guardians, if guardians are not enabled for the domain in question or for other access errors. &#42; `FAILED_PRECONDITION` if the guardian link is not in the `PENDING` state. &#42; `INVALID_ARGUMENT` if the format of the student ID provided cannot be recognized (it is not an email address, nor a `user_id` from this API), or if the passed `GuardianInvitation` has a `state` other than `COMPLETE`, or if it modifies fields other than `state`. &#42; `NOT_FOUND` if the student ID provided is a valid student ID, but Classroom has no record of that student, or if the `id` field does not refer to a guardian invitation known to Classroom. */
			class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/classroom.guardianlinks.students""")
				/** Perform the request */
				def withGuardianInvitation(body: Schema.GuardianInvitation) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GuardianInvitation])
			}
			object patch {
				def apply(studentId: String, invitationId: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/userProfiles/${studentId}/guardianInvitations/${invitationId}").addQueryStringParameters("updateMask" -> updateMask.toString))
			}
		}
		object guardians {
			/** Returns a list of guardians that the requesting user is permitted to view, restricted to those that match the request. To list guardians for any student that the requesting user may view guardians for, use the literal character `-` for the student ID. This method returns the following error codes: &#42; `PERMISSION_DENIED` if a `student_id` is specified, and the requesting user is not permitted to view guardian information for that student, if `"-"` is specified as the `student_id` and the user is not a domain administrator, if guardians are not enabled for the domain in question, if the `invited_email_address` filter is set by a user who is not a domain administrator, or for other access errors. &#42; `INVALID_ARGUMENT` if a `student_id` is specified, but its format cannot be recognized (it is not an email address, nor a `student_id` from the API, nor the literal string `me`). May also be returned if an invalid `page_token` is provided. &#42; `NOT_FOUND` if a `student_id` is specified, and its format can be recognized, but Classroom has no record of that student. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListGuardiansResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/classroom.guardianlinks.me.readonly""", """https://www.googleapis.com/auth/classroom.guardianlinks.students""", """https://www.googleapis.com/auth/classroom.guardianlinks.students.readonly""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListGuardiansResponse])
			}
			object list {
				def apply(studentId: String, invitedEmailAddress: String, pageToken: String, pageSize: Int)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/userProfiles/${studentId}/guardians").addQueryStringParameters("invitedEmailAddress" -> invitedEmailAddress.toString, "pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString))
				given Conversion[list, Future[Schema.ListGuardiansResponse]] = (fun: list) => fun.apply()
			}
			/** Returns a specific guardian. This method returns the following error codes: &#42; `PERMISSION_DENIED` if no user that matches the provided `student_id` is visible to the requesting user, if the requesting user is not permitted to view guardian information for the student identified by the `student_id`, if guardians are not enabled for the domain in question, or for other access errors. &#42; `INVALID_ARGUMENT` if a `student_id` is specified, but its format cannot be recognized (it is not an email address, nor a `student_id` from the API, nor the literal string `me`). &#42; `NOT_FOUND` if the requesting user is permitted to view guardians for the requested `student_id`, but no `Guardian` record exists for that student that matches the provided `guardian_id`. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Guardian]) {
				val scopes = Seq("""https://www.googleapis.com/auth/classroom.guardianlinks.me.readonly""", """https://www.googleapis.com/auth/classroom.guardianlinks.students""", """https://www.googleapis.com/auth/classroom.guardianlinks.students.readonly""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Guardian])
			}
			object get {
				def apply(studentId: String, guardianId: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/userProfiles/${studentId}/guardians/${guardianId}").addQueryStringParameters())
				given Conversion[get, Future[Schema.Guardian]] = (fun: get) => fun.apply()
			}
			/** Deletes a guardian. The guardian will no longer receive guardian notifications and the guardian will no longer be accessible via the API. This method returns the following error codes: &#42; `PERMISSION_DENIED` if no user that matches the provided `student_id` is visible to the requesting user, if the requesting user is not permitted to manage guardians for the student identified by the `student_id`, if guardians are not enabled for the domain in question, or for other access errors. &#42; `INVALID_ARGUMENT` if a `student_id` is specified, but its format cannot be recognized (it is not an email address, nor a `student_id` from the API). &#42; `NOT_FOUND` if the requesting user is permitted to modify guardians for the requested `student_id`, but no `Guardian` record exists for that student with the provided `guardian_id`. */
			class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
				val scopes = Seq("""https://www.googleapis.com/auth/classroom.guardianlinks.students""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
			}
			object delete {
				def apply(studentId: String, guardianId: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/userProfiles/${studentId}/guardians/${guardianId}").addQueryStringParameters())
				given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
			}
		}
	}
	object invitations {
		/** Accepts an invitation, removing it and adding the invited user to the teachers or students (as appropriate) of the specified course. Only the invited user may accept an invitation. This method returns the following error codes: &#42; `PERMISSION_DENIED` if the requesting user is not permitted to accept the requested invitation or for access errors. &#42; `FAILED_PRECONDITION` for the following request errors: &#42; CourseMemberLimitReached &#42; CourseNotModifiable &#42; CourseTeacherLimitReached &#42; UserGroupsMembershipLimitReached &#42; `NOT_FOUND` if no invitation exists with the requested ID. */
		class accept(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
			val scopes = Seq("""https://www.googleapis.com/auth/classroom.rosters""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("POST")).map(_.json.as[Schema.Empty])
		}
		object accept {
			def apply(id: String)(using signer: RequestSigner, ec: ExecutionContext): accept = new accept(ws.url(BASE_URL + s"v1/invitations/${id}:accept").addQueryStringParameters())
			given Conversion[accept, Future[Schema.Empty]] = (fun: accept) => fun.apply()
		}
		/** Creates an invitation. Only one invitation for a user and course may exist at a time. Delete and re-create an invitation to make changes. This method returns the following error codes: &#42; `PERMISSION_DENIED` if the requesting user is not permitted to create invitations for this course or for access errors. &#42; `NOT_FOUND` if the course or the user does not exist. &#42; `FAILED_PRECONDITION`: &#42; if the requested user's account is disabled. &#42; if the user already has this role or a role with greater permissions. &#42; for the following request errors: &#42; IneligibleOwner &#42; `ALREADY_EXISTS` if an invitation for the specified user and course already exists. */
		class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/classroom.rosters""")
			/** Perform the request */
			def withInvitation(body: Schema.Invitation) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Invitation])
		}
		object create {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/invitations").addQueryStringParameters())
		}
		/** Deletes an invitation. This method returns the following error codes: &#42; `PERMISSION_DENIED` if the requesting user is not permitted to delete the requested invitation or for access errors. &#42; `NOT_FOUND` if no invitation exists with the requested ID. */
		class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
			val scopes = Seq("""https://www.googleapis.com/auth/classroom.rosters""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
		}
		object delete {
			def apply(id: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/invitations/${id}").addQueryStringParameters())
			given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
		}
		/** Returns an invitation. This method returns the following error codes: &#42; `PERMISSION_DENIED` if the requesting user is not permitted to view the requested invitation or for access errors. &#42; `NOT_FOUND` if no invitation exists with the requested ID. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Invitation]) {
			val scopes = Seq("""https://www.googleapis.com/auth/classroom.rosters""", """https://www.googleapis.com/auth/classroom.rosters.readonly""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Invitation])
		}
		object get {
			def apply(id: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/invitations/${id}").addQueryStringParameters())
			given Conversion[get, Future[Schema.Invitation]] = (fun: get) => fun.apply()
		}
		/** Returns a list of invitations that the requesting user is permitted to view, restricted to those that match the list request. &#42;Note:&#42; At least one of `user_id` or `course_id` must be supplied. Both fields can be supplied. This method returns the following error codes: &#42; `PERMISSION_DENIED` for access errors. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListInvitationsResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/classroom.rosters""", """https://www.googleapis.com/auth/classroom.rosters.readonly""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListInvitationsResponse])
		}
		object list {
			def apply(userId: String, courseId: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/invitations").addQueryStringParameters("userId" -> userId.toString, "courseId" -> courseId.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
			given Conversion[list, Future[Schema.ListInvitationsResponse]] = (fun: list) => fun.apply()
		}
	}
	object registrations {
		/** Creates a `Registration`, causing Classroom to start sending notifications from the provided `feed` to the destination provided in `cloudPubSubTopic`. Returns the created `Registration`. Currently, this will be the same as the argument, but with server-assigned fields such as `expiry_time` and `id` filled in. Note that any value specified for the `expiry_time` or `id` fields will be ignored. While Classroom may validate the `cloudPubSubTopic` and return errors on a best effort basis, it is the caller's responsibility to ensure that it exists and that Classroom has permission to publish to it. This method may return the following error codes: &#42; `PERMISSION_DENIED` if: &#42; the authenticated user does not have permission to receive notifications from the requested field; or &#42; the current user has not granted access to the current Cloud project with the appropriate scope for the requested feed. Note that domain-wide delegation of authority is not currently supported for this purpose. If the request has the appropriate scope, but no grant exists, a Request Errors is returned. &#42; another access error is encountered. &#42; `INVALID_ARGUMENT` if: &#42; no `cloudPubsubTopic` is specified, or the specified `cloudPubsubTopic` is not valid; or &#42; no `feed` is specified, or the specified `feed` is not valid. &#42; `NOT_FOUND` if: &#42; the specified `feed` cannot be located, or the requesting user does not have permission to determine whether or not it exists; or &#42; the specified `cloudPubsubTopic` cannot be located, or Classroom has not been granted permission to publish to it. */
		class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/classroom.push-notifications""")
			/** Perform the request */
			def withRegistration(body: Schema.Registration) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Registration])
		}
		object create {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/registrations").addQueryStringParameters())
		}
		/** Deletes a `Registration`, causing Classroom to stop sending notifications for that `Registration`. */
		class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
			val scopes = Seq("""https://www.googleapis.com/auth/classroom.push-notifications""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
		}
		object delete {
			def apply(registrationId: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/registrations/${registrationId}").addQueryStringParameters())
			given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
		}
	}
}
