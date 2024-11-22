package com.boresjo.play.api.google.classroom

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	object Course {
		enum CourseStateEnum extends Enum[CourseStateEnum] { case COURSE_STATE_UNSPECIFIED, ACTIVE, ARCHIVED, PROVISIONED, DECLINED, SUSPENDED }
	}
	case class Course(
	  /** Identifier for this course assigned by Classroom. When creating a course, you may optionally set this identifier to an alias string in the request to create a corresponding alias. The `id` is still assigned by Classroom and cannot be updated after the course is created. Specifying this field in a course update mask results in an error. */
		id: Option[String] = None,
	  /** Name of the course. For example, "10th Grade Biology". The name is required. It must be between 1 and 750 characters and a valid UTF-8 string. */
		name: Option[String] = None,
	  /** Section of the course. For example, "Period 2". If set, this field must be a valid UTF-8 string and no longer than 2800 characters. */
		section: Option[String] = None,
	  /** Optional heading for the description. For example, "Welcome to 10th Grade Biology." If set, this field must be a valid UTF-8 string and no longer than 3600 characters. */
		descriptionHeading: Option[String] = None,
	  /** Optional description. For example, "We'll be learning about the structure of living creatures from a combination of textbooks, guest lectures, and lab work. Expect to be excited!" If set, this field must be a valid UTF-8 string and no longer than 30,000 characters. */
		description: Option[String] = None,
	  /** Optional room location. For example, "301". If set, this field must be a valid UTF-8 string and no longer than 650 characters. */
		room: Option[String] = None,
	  /** The identifier of the owner of a course. When specified as a parameter of a create course request, this field is required. The identifier can be one of the following: &#42; the numeric identifier for the user &#42; the email address of the user &#42; the string literal `"me"`, indicating the requesting user This must be set in a create request. Admins can also specify this field in a patch course request to transfer ownership. In other contexts, it is read-only. */
		ownerId: Option[String] = None,
	  /** Creation time of the course. Specifying this field in a course update mask results in an error. Read-only. */
		creationTime: Option[String] = None,
	  /** Time of the most recent update to this course. Specifying this field in a course update mask results in an error. Read-only. */
		updateTime: Option[String] = None,
	  /** Enrollment code to use when joining this course. Specifying this field in a course update mask results in an error. Read-only. */
		enrollmentCode: Option[String] = None,
	  /** State of the course. If unspecified, the default state is `PROVISIONED`. */
		courseState: Option[Schema.Course.CourseStateEnum] = None,
	  /** Absolute link to this course in the Classroom web UI. Read-only. */
		alternateLink: Option[String] = None,
	  /** The email address of a Google group containing all teachers of the course. This group does not accept email and can only be used for permissions. Read-only. */
		teacherGroupEmail: Option[String] = None,
	  /** The email address of a Google group containing all members of the course. This group does not accept email and can only be used for permissions. Read-only. */
		courseGroupEmail: Option[String] = None,
	  /** Information about a Drive Folder that is shared with all teachers of the course. This field will only be set for teachers of the course and domain administrators. Read-only. */
		teacherFolder: Option[Schema.DriveFolder] = None,
	  /** Sets of materials that appear on the "about" page of this course. Read-only. */
		courseMaterialSets: Option[List[Schema.CourseMaterialSet]] = None,
	  /** Whether or not guardian notifications are enabled for this course. Read-only. */
		guardiansEnabled: Option[Boolean] = None,
	  /** The Calendar ID for a calendar that all course members can see, to which Classroom adds events for course work and announcements in the course. The Calendar for a course is created asynchronously when the course is set to `CourseState.ACTIVE` for the first time (at creation time or when it is updated to `ACTIVE` through the UI or the API). The Calendar ID will not be populated until the creation process is completed. Read-only. */
		calendarId: Option[String] = None,
	  /** The gradebook settings that specify how a student's overall grade for the course will be calculated and who it will be displayed to. Read-only */
		gradebookSettings: Option[Schema.GradebookSettings] = None
	)
	
	case class DriveFolder(
	  /** Drive API resource ID. */
		id: Option[String] = None,
	  /** Title of the Drive folder. Read-only. */
		title: Option[String] = None,
	  /** URL that can be used to access the Drive folder. Read-only. */
		alternateLink: Option[String] = None
	)
	
	case class CourseMaterialSet(
	  /** Title for this set. */
		title: Option[String] = None,
	  /** Materials attached to this set. */
		materials: Option[List[Schema.CourseMaterial]] = None
	)
	
	case class CourseMaterial(
	  /** Google Drive file attachment. */
		driveFile: Option[Schema.DriveFile] = None,
	  /** Youtube video attachment. */
		youTubeVideo: Option[Schema.YouTubeVideo] = None,
	  /** Link atatchment. */
		link: Option[Schema.Link] = None,
	  /** Google Forms attachment. */
		form: Option[Schema.Form] = None
	)
	
	case class DriveFile(
	  /** Drive API resource ID. */
		id: Option[String] = None,
	  /** Title of the Drive item. Read-only. */
		title: Option[String] = None,
	  /** URL that can be used to access the Drive item. Read-only. */
		alternateLink: Option[String] = None,
	  /** URL of a thumbnail image of the Drive item. Read-only. */
		thumbnailUrl: Option[String] = None
	)
	
	case class YouTubeVideo(
	  /** YouTube API resource ID. */
		id: Option[String] = None,
	  /** Title of the YouTube video. Read-only. */
		title: Option[String] = None,
	  /** URL that can be used to view the YouTube video. Read-only. */
		alternateLink: Option[String] = None,
	  /** URL of a thumbnail image of the YouTube video. Read-only. */
		thumbnailUrl: Option[String] = None
	)
	
	case class Link(
	  /** URL to link to. This must be a valid UTF-8 string containing between 1 and 2024 characters. */
		url: Option[String] = None,
	  /** Title of the target of the URL. Read-only. */
		title: Option[String] = None,
	  /** URL of a thumbnail image of the target URL. Read-only. */
		thumbnailUrl: Option[String] = None
	)
	
	case class Form(
	  /** URL of the form. */
		formUrl: Option[String] = None,
	  /** URL of the form responses document. Only set if responses have been recorded and only when the requesting user is an editor of the form. Read-only. */
		responseUrl: Option[String] = None,
	  /** Title of the Form. Read-only. */
		title: Option[String] = None,
	  /** URL of a thumbnail image of the Form. Read-only. */
		thumbnailUrl: Option[String] = None
	)
	
	object GradebookSettings {
		enum CalculationTypeEnum extends Enum[CalculationTypeEnum] { case CALCULATION_TYPE_UNSPECIFIED, TOTAL_POINTS, WEIGHTED_CATEGORIES }
		enum DisplaySettingEnum extends Enum[DisplaySettingEnum] { case DISPLAY_SETTING_UNSPECIFIED, SHOW_OVERALL_GRADE, HIDE_OVERALL_GRADE, SHOW_TEACHERS_ONLY }
	}
	case class GradebookSettings(
	  /** Indicates how the overall grade is calculated. */
		calculationType: Option[Schema.GradebookSettings.CalculationTypeEnum] = None,
	  /** Indicates who can see the overall grade.. */
		displaySetting: Option[Schema.GradebookSettings.DisplaySettingEnum] = None,
	  /** Grade categories that are available for coursework in the course. */
		gradeCategories: Option[List[Schema.GradeCategory]] = None
	)
	
	case class GradeCategory(
	  /** ID of the grade category. */
		id: Option[String] = None,
	  /** Name of the grade category. */
		name: Option[String] = None,
	  /** The weight of the category average as part of overall average. A weight of 12.34% is represented as 123400 (100% is 1,000,000). The last two digits should always be zero since we use two decimal precision. Only applicable when grade calculation type is WEIGHTED_CATEGORIES. */
		weight: Option[Int] = None,
	  /** Default value of denominator. Only applicable when grade calculation type is TOTAL_POINTS. */
		defaultGradeDenominator: Option[Int] = None
	)
	
	case class Empty(
	
	)
	
	case class ListCoursesResponse(
	  /** Courses that match the list request. */
		courses: Option[List[Schema.Course]] = None,
	  /** Token identifying the next page of results to return. If empty, no further results are available. */
		nextPageToken: Option[String] = None
	)
	
	case class CourseAlias(
	  /** Alias string. The format of the string indicates the desired alias scoping. &#42; `d:` indicates a domain-scoped alias. Example: `d:math_101` &#42; `p:` indicates a project-scoped alias. Example: `p:abc123` This field has a maximum length of 256 characters. */
		alias: Option[String] = None
	)
	
	case class ListCourseAliasesResponse(
	  /** The course aliases. */
		aliases: Option[List[Schema.CourseAlias]] = None,
	  /** Token identifying the next page of results to return. If empty, no further results are available. */
		nextPageToken: Option[String] = None
	)
	
	object CourseWork {
		enum StateEnum extends Enum[StateEnum] { case COURSE_WORK_STATE_UNSPECIFIED, PUBLISHED, DRAFT, DELETED }
		enum WorkTypeEnum extends Enum[WorkTypeEnum] { case COURSE_WORK_TYPE_UNSPECIFIED, ASSIGNMENT, SHORT_ANSWER_QUESTION, MULTIPLE_CHOICE_QUESTION }
		enum AssigneeModeEnum extends Enum[AssigneeModeEnum] { case ASSIGNEE_MODE_UNSPECIFIED, ALL_STUDENTS, INDIVIDUAL_STUDENTS }
		enum SubmissionModificationModeEnum extends Enum[SubmissionModificationModeEnum] { case SUBMISSION_MODIFICATION_MODE_UNSPECIFIED, MODIFIABLE_UNTIL_TURNED_IN, MODIFIABLE }
	}
	case class CourseWork(
	  /** Identifier of the course. Read-only. */
		courseId: Option[String] = None,
	  /** Classroom-assigned identifier of this course work, unique per course. Read-only. */
		id: Option[String] = None,
	  /** Title of this course work. The title must be a valid UTF-8 string containing between 1 and 3000 characters. */
		title: Option[String] = None,
	  /** Optional description of this course work. If set, the description must be a valid UTF-8 string containing no more than 30,000 characters. */
		description: Option[String] = None,
	  /** Additional materials. CourseWork must have no more than 20 material items. */
		materials: Option[List[Schema.Material]] = None,
	  /** Status of this course work. If unspecified, the default state is `DRAFT`. */
		state: Option[Schema.CourseWork.StateEnum] = None,
	  /** Absolute link to this course work in the Classroom web UI. This is only populated if `state` is `PUBLISHED`. Read-only. */
		alternateLink: Option[String] = None,
	  /** Timestamp when this course work was created. Read-only. */
		creationTime: Option[String] = None,
	  /** Timestamp of the most recent change to this course work. Read-only. */
		updateTime: Option[String] = None,
	  /** Optional date, in UTC, that submissions for this course work are due. This must be specified if `due_time` is specified. */
		dueDate: Option[Schema.Date] = None,
	  /** Optional time of day, in UTC, that submissions for this course work are due. This must be specified if `due_date` is specified. */
		dueTime: Option[Schema.TimeOfDay] = None,
	  /** Optional timestamp when this course work is scheduled to be published. */
		scheduledTime: Option[String] = None,
	  /** Maximum grade for this course work. If zero or unspecified, this assignment is considered ungraded. This must be a non-negative integer value. */
		maxPoints: Option[BigDecimal] = None,
	  /** Type of this course work. The type is set when the course work is created and cannot be changed. */
		workType: Option[Schema.CourseWork.WorkTypeEnum] = None,
	  /** Whether this course work item is associated with the Developer Console project making the request. See CreateCourseWork for more details. Read-only. */
		associatedWithDeveloper: Option[Boolean] = None,
	  /** Assignee mode of the coursework. If unspecified, the default value is `ALL_STUDENTS`. */
		assigneeMode: Option[Schema.CourseWork.AssigneeModeEnum] = None,
	  /** Identifiers of students with access to the coursework. This field is set only if `assigneeMode` is `INDIVIDUAL_STUDENTS`. If the `assigneeMode` is `INDIVIDUAL_STUDENTS`, then only students specified in this field are assigned the coursework. */
		individualStudentsOptions: Option[Schema.IndividualStudentsOptions] = None,
	  /** Setting to determine when students are allowed to modify submissions. If unspecified, the default value is `MODIFIABLE_UNTIL_TURNED_IN`. */
		submissionModificationMode: Option[Schema.CourseWork.SubmissionModificationModeEnum] = None,
	  /** Assignment details. This is populated only when `work_type` is `ASSIGNMENT`. Read-only. */
		assignment: Option[Schema.Assignment] = None,
	  /** Multiple choice question details. For read operations, this field is populated only when `work_type` is `MULTIPLE_CHOICE_QUESTION`. For write operations, this field must be specified when creating course work with a `work_type` of `MULTIPLE_CHOICE_QUESTION`, and it must not be set otherwise. */
		multipleChoiceQuestion: Option[Schema.MultipleChoiceQuestion] = None,
	  /** Identifier for the user that created the coursework. Read-only. */
		creatorUserId: Option[String] = None,
	  /** Identifier for the topic that this coursework is associated with. Must match an existing topic in the course. */
		topicId: Option[String] = None,
	  /** The category that this coursework's grade contributes to. Present only when a category has been chosen for the coursework. May be used in calculating the overall grade. Read-only. */
		gradeCategory: Option[Schema.GradeCategory] = None
	)
	
	case class Material(
	  /** Google Drive file material. */
		driveFile: Option[Schema.SharedDriveFile] = None,
	  /** YouTube video material. */
		youtubeVideo: Option[Schema.YouTubeVideo] = None,
	  /** Link material. On creation, this is upgraded to a more appropriate type if possible, and this is reflected in the response. */
		link: Option[Schema.Link] = None,
	  /** Google Forms material. */
		form: Option[Schema.Form] = None
	)
	
	object SharedDriveFile {
		enum ShareModeEnum extends Enum[ShareModeEnum] { case UNKNOWN_SHARE_MODE, VIEW, EDIT, STUDENT_COPY }
	}
	case class SharedDriveFile(
	  /** Drive file details. */
		driveFile: Option[Schema.DriveFile] = None,
	  /** Mechanism by which students access the Drive item. */
		shareMode: Option[Schema.SharedDriveFile.ShareModeEnum] = None
	)
	
	case class Date(
	  /** Year of the date. Must be from 1 to 9999, or 0 to specify a date without a year. */
		year: Option[Int] = None,
	  /** Month of a year. Must be from 1 to 12, or 0 to specify a year without a month and day. */
		month: Option[Int] = None,
	  /** Day of a month. Must be from 1 to 31 and valid for the year and month, or 0 to specify a year by itself or a year and month where the day isn't significant. */
		day: Option[Int] = None
	)
	
	case class TimeOfDay(
	  /** Hours of a day in 24 hour format. Must be greater than or equal to 0 and typically must be less than or equal to 23. An API may choose to allow the value "24:00:00" for scenarios like business closing time. */
		hours: Option[Int] = None,
	  /** Minutes of an hour. Must be greater than or equal to 0 and less than or equal to 59. */
		minutes: Option[Int] = None,
	  /** Seconds of a minute. Must be greater than or equal to 0 and typically must be less than or equal to 59. An API may allow the value 60 if it allows leap-seconds. */
		seconds: Option[Int] = None,
	  /** Fractions of seconds, in nanoseconds. Must be greater than or equal to 0 and less than or equal to 999,999,999. */
		nanos: Option[Int] = None
	)
	
	case class IndividualStudentsOptions(
	  /** Identifiers for the students that have access to the coursework/announcement. */
		studentIds: Option[List[String]] = None
	)
	
	case class Assignment(
	  /** Drive folder where attachments from student submissions are placed. This is only populated for course teachers and administrators. */
		studentWorkFolder: Option[Schema.DriveFolder] = None
	)
	
	case class MultipleChoiceQuestion(
	  /** Possible choices. */
		choices: Option[List[String]] = None
	)
	
	case class ListCourseWorkResponse(
	  /** Course work items that match the request. */
		courseWork: Option[List[Schema.CourseWork]] = None,
	  /** Token identifying the next page of results to return. If empty, no further results are available. */
		nextPageToken: Option[String] = None
	)
	
	object Announcement {
		enum StateEnum extends Enum[StateEnum] { case ANNOUNCEMENT_STATE_UNSPECIFIED, PUBLISHED, DRAFT, DELETED }
		enum AssigneeModeEnum extends Enum[AssigneeModeEnum] { case ASSIGNEE_MODE_UNSPECIFIED, ALL_STUDENTS, INDIVIDUAL_STUDENTS }
	}
	case class Announcement(
	  /** Identifier of the course. Read-only. */
		courseId: Option[String] = None,
	  /** Classroom-assigned identifier of this announcement, unique per course. Read-only. */
		id: Option[String] = None,
	  /** Description of this announcement. The text must be a valid UTF-8 string containing no more than 30,000 characters. */
		text: Option[String] = None,
	  /** Additional materials. Announcements must have no more than 20 material items. */
		materials: Option[List[Schema.Material]] = None,
	  /** Status of this announcement. If unspecified, the default state is `DRAFT`. */
		state: Option[Schema.Announcement.StateEnum] = None,
	  /** Absolute link to this announcement in the Classroom web UI. This is only populated if `state` is `PUBLISHED`. Read-only. */
		alternateLink: Option[String] = None,
	  /** Timestamp when this announcement was created. Read-only. */
		creationTime: Option[String] = None,
	  /** Timestamp of the most recent change to this announcement. Read-only. */
		updateTime: Option[String] = None,
	  /** Optional timestamp when this announcement is scheduled to be published. */
		scheduledTime: Option[String] = None,
	  /** Assignee mode of the announcement. If unspecified, the default value is `ALL_STUDENTS`. */
		assigneeMode: Option[Schema.Announcement.AssigneeModeEnum] = None,
	  /** Identifiers of students with access to the announcement. This field is set only if `assigneeMode` is `INDIVIDUAL_STUDENTS`. If the `assigneeMode` is `INDIVIDUAL_STUDENTS`, then only students specified in this field can see the announcement. */
		individualStudentsOptions: Option[Schema.IndividualStudentsOptions] = None,
	  /** Identifier for the user that created the announcement. Read-only. */
		creatorUserId: Option[String] = None
	)
	
	case class ListAnnouncementsResponse(
	  /** Announcement items that match the request. */
		announcements: Option[List[Schema.Announcement]] = None,
	  /** Token identifying the next page of results to return. If empty, no further results are available. */
		nextPageToken: Option[String] = None
	)
	
	object CourseWorkMaterial {
		enum StateEnum extends Enum[StateEnum] { case COURSEWORK_MATERIAL_STATE_UNSPECIFIED, PUBLISHED, DRAFT, DELETED }
		enum AssigneeModeEnum extends Enum[AssigneeModeEnum] { case ASSIGNEE_MODE_UNSPECIFIED, ALL_STUDENTS, INDIVIDUAL_STUDENTS }
	}
	case class CourseWorkMaterial(
	  /** Identifier of the course. Read-only. */
		courseId: Option[String] = None,
	  /** Classroom-assigned identifier of this course work material, unique per course. Read-only. */
		id: Option[String] = None,
	  /** Title of this course work material. The title must be a valid UTF-8 string containing between 1 and 3000 characters. */
		title: Option[String] = None,
	  /** Optional description of this course work material. The text must be a valid UTF-8 string containing no more than 30,000 characters. */
		description: Option[String] = None,
	  /** Additional materials. A course work material must have no more than 20 material items. */
		materials: Option[List[Schema.Material]] = None,
	  /** Status of this course work material. If unspecified, the default state is `DRAFT`. */
		state: Option[Schema.CourseWorkMaterial.StateEnum] = None,
	  /** Absolute link to this course work material in the Classroom web UI. This is only populated if `state` is `PUBLISHED`. Read-only. */
		alternateLink: Option[String] = None,
	  /** Timestamp when this course work material was created. Read-only. */
		creationTime: Option[String] = None,
	  /** Timestamp of the most recent change to this course work material. Read-only. */
		updateTime: Option[String] = None,
	  /** Optional timestamp when this course work material is scheduled to be published. */
		scheduledTime: Option[String] = None,
	  /** Assignee mode of the course work material. If unspecified, the default value is `ALL_STUDENTS`. */
		assigneeMode: Option[Schema.CourseWorkMaterial.AssigneeModeEnum] = None,
	  /** Identifiers of students with access to the course work material. This field is set only if `assigneeMode` is `INDIVIDUAL_STUDENTS`. If the `assigneeMode` is `INDIVIDUAL_STUDENTS`, then only students specified in this field can see the course work material. */
		individualStudentsOptions: Option[Schema.IndividualStudentsOptions] = None,
	  /** Identifier for the user that created the course work material. Read-only. */
		creatorUserId: Option[String] = None,
	  /** Identifier for the topic that this course work material is associated with. Must match an existing topic in the course. */
		topicId: Option[String] = None
	)
	
	case class ListCourseWorkMaterialResponse(
	  /** Course work material items that match the request. */
		courseWorkMaterial: Option[List[Schema.CourseWorkMaterial]] = None,
	  /** Token identifying the next page of results to return. If empty, no further results are available. */
		nextPageToken: Option[String] = None
	)
	
	object StudentSubmission {
		enum StateEnum extends Enum[StateEnum] { case SUBMISSION_STATE_UNSPECIFIED, NEW, CREATED, TURNED_IN, RETURNED, RECLAIMED_BY_STUDENT }
		enum CourseWorkTypeEnum extends Enum[CourseWorkTypeEnum] { case COURSE_WORK_TYPE_UNSPECIFIED, ASSIGNMENT, SHORT_ANSWER_QUESTION, MULTIPLE_CHOICE_QUESTION }
	}
	case class StudentSubmission(
	  /** Identifier of the course. Read-only. */
		courseId: Option[String] = None,
	  /** Identifier for the course work this corresponds to. Read-only. */
		courseWorkId: Option[String] = None,
	  /** Classroom-assigned Identifier for the student submission. This is unique among submissions for the relevant course work. Read-only. */
		id: Option[String] = None,
	  /** Identifier for the student that owns this submission. Read-only. */
		userId: Option[String] = None,
	  /** Creation time of this submission. This may be unset if the student has not accessed this item. Read-only. */
		creationTime: Option[String] = None,
	  /** Last update time of this submission. This may be unset if the student has not accessed this item. Read-only. */
		updateTime: Option[String] = None,
	  /** State of this submission. Read-only. */
		state: Option[Schema.StudentSubmission.StateEnum] = None,
	  /** Whether this submission is late. Read-only. */
		late: Option[Boolean] = None,
	  /** Optional pending grade. If unset, no grade was set. This value must be non-negative. Decimal (that is, non-integer) values are allowed, but are rounded to two decimal places. This is only visible to and modifiable by course teachers. */
		draftGrade: Option[BigDecimal] = None,
	  /** Optional grade. If unset, no grade was set. This value must be non-negative. Decimal (that is, non-integer) values are allowed, but are rounded to two decimal places. This may be modified only by course teachers. */
		assignedGrade: Option[BigDecimal] = None,
	  /** Absolute link to the submission in the Classroom web UI. Read-only. */
		alternateLink: Option[String] = None,
	  /** Type of course work this submission is for. Read-only. */
		courseWorkType: Option[Schema.StudentSubmission.CourseWorkTypeEnum] = None,
	  /** Whether this student submission is associated with the Developer Console project making the request. See CreateCourseWork for more details. Read-only. */
		associatedWithDeveloper: Option[Boolean] = None,
	  /** Submission content when course_work_type is ASSIGNMENT. Students can modify this content using ModifyAttachments. */
		assignmentSubmission: Option[Schema.AssignmentSubmission] = None,
	  /** Submission content when course_work_type is SHORT_ANSWER_QUESTION. */
		shortAnswerSubmission: Option[Schema.ShortAnswerSubmission] = None,
	  /** Submission content when course_work_type is MULTIPLE_CHOICE_QUESTION. */
		multipleChoiceSubmission: Option[Schema.MultipleChoiceSubmission] = None,
	  /** The history of the submission (includes state and grade histories). Read-only. */
		submissionHistory: Option[List[Schema.SubmissionHistory]] = None
	)
	
	case class AssignmentSubmission(
	  /** Attachments added by the student. Drive files that correspond to materials with a share mode of STUDENT_COPY may not exist yet if the student has not accessed the assignment in Classroom. Some attachment metadata is only populated if the requesting user has permission to access it. Identifier and alternate_link fields are always available, but others (for example, title) may not be. */
		attachments: Option[List[Schema.Attachment]] = None
	)
	
	case class Attachment(
	  /** Google Drive file attachment. */
		driveFile: Option[Schema.DriveFile] = None,
	  /** Youtube video attachment. */
		youTubeVideo: Option[Schema.YouTubeVideo] = None,
	  /** Link attachment. */
		link: Option[Schema.Link] = None,
	  /** Google Forms attachment. */
		form: Option[Schema.Form] = None
	)
	
	case class ShortAnswerSubmission(
	  /** Student response to a short-answer question. */
		answer: Option[String] = None
	)
	
	case class MultipleChoiceSubmission(
	  /** Student's select choice. */
		answer: Option[String] = None
	)
	
	case class SubmissionHistory(
	  /** The state history information of the submission, if present. */
		stateHistory: Option[Schema.StateHistory] = None,
	  /** The grade history information of the submission, if present. */
		gradeHistory: Option[Schema.GradeHistory] = None
	)
	
	object StateHistory {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, CREATED, TURNED_IN, RETURNED, RECLAIMED_BY_STUDENT, STUDENT_EDITED_AFTER_TURN_IN }
	}
	case class StateHistory(
	  /** The workflow pipeline stage. */
		state: Option[Schema.StateHistory.StateEnum] = None,
	  /** When the submission entered this state. */
		stateTimestamp: Option[String] = None,
	  /** The teacher or student who made the change. */
		actorUserId: Option[String] = None
	)
	
	object GradeHistory {
		enum GradeChangeTypeEnum extends Enum[GradeChangeTypeEnum] { case UNKNOWN_GRADE_CHANGE_TYPE, DRAFT_GRADE_POINTS_EARNED_CHANGE, ASSIGNED_GRADE_POINTS_EARNED_CHANGE, MAX_POINTS_CHANGE }
	}
	case class GradeHistory(
	  /** The numerator of the grade at this time in the submission grade history. */
		pointsEarned: Option[BigDecimal] = None,
	  /** The denominator of the grade at this time in the submission grade history. */
		maxPoints: Option[BigDecimal] = None,
	  /** When the grade of the submission was changed. */
		gradeTimestamp: Option[String] = None,
	  /** The teacher who made the grade change. */
		actorUserId: Option[String] = None,
	  /** The type of grade change at this time in the submission grade history. */
		gradeChangeType: Option[Schema.GradeHistory.GradeChangeTypeEnum] = None
	)
	
	case class ListStudentSubmissionsResponse(
	  /** Student work that matches the request. */
		studentSubmissions: Option[List[Schema.StudentSubmission]] = None,
	  /** Token identifying the next page of results to return. If empty, no further results are available. */
		nextPageToken: Option[String] = None
	)
	
	case class TurnInStudentSubmissionRequest(
	
	)
	
	case class ReclaimStudentSubmissionRequest(
	
	)
	
	case class ReturnStudentSubmissionRequest(
	
	)
	
	case class ModifyAttachmentsRequest(
	  /** Attachments to add. A student submission may not have more than 20 attachments. Form attachments are not supported. */
		addAttachments: Option[List[Schema.Attachment]] = None
	)
	
	object ModifyCourseWorkAssigneesRequest {
		enum AssigneeModeEnum extends Enum[AssigneeModeEnum] { case ASSIGNEE_MODE_UNSPECIFIED, ALL_STUDENTS, INDIVIDUAL_STUDENTS }
	}
	case class ModifyCourseWorkAssigneesRequest(
	  /** Mode of the coursework describing whether it will be assigned to all students or specified individual students. */
		assigneeMode: Option[Schema.ModifyCourseWorkAssigneesRequest.AssigneeModeEnum] = None,
	  /** Set which students are assigned or not assigned to the coursework. Must be specified only when `assigneeMode` is `INDIVIDUAL_STUDENTS`. */
		modifyIndividualStudentsOptions: Option[Schema.ModifyIndividualStudentsOptions] = None
	)
	
	case class ModifyIndividualStudentsOptions(
	  /** IDs of students to be added as having access to this coursework/announcement. */
		addStudentIds: Option[List[String]] = None,
	  /** IDs of students to be removed from having access to this coursework/announcement. */
		removeStudentIds: Option[List[String]] = None
	)
	
	object ModifyAnnouncementAssigneesRequest {
		enum AssigneeModeEnum extends Enum[AssigneeModeEnum] { case ASSIGNEE_MODE_UNSPECIFIED, ALL_STUDENTS, INDIVIDUAL_STUDENTS }
	}
	case class ModifyAnnouncementAssigneesRequest(
	  /** Mode of the announcement describing whether it is accessible by all students or specified individual students. */
		assigneeMode: Option[Schema.ModifyAnnouncementAssigneesRequest.AssigneeModeEnum] = None,
	  /** Set which students can view or cannot view the announcement. Must be specified only when `assigneeMode` is `INDIVIDUAL_STUDENTS`. */
		modifyIndividualStudentsOptions: Option[Schema.ModifyIndividualStudentsOptions] = None
	)
	
	case class Topic(
	  /** Identifier of the course. Read-only. */
		courseId: Option[String] = None,
	  /** Unique identifier for the topic. Read-only. */
		topicId: Option[String] = None,
	  /** The name of the topic, generated by the user. Leading and trailing whitespaces, if any, are trimmed. Also, multiple consecutive whitespaces are collapsed into one inside the name. The result must be a non-empty string. Topic names are case sensitive, and must be no longer than 100 characters. */
		name: Option[String] = None,
	  /** The time the topic was last updated by the system. Read-only. */
		updateTime: Option[String] = None
	)
	
	case class ListTopicResponse(
	  /** Topic items that match the request. */
		topic: Option[List[Schema.Topic]] = None,
	  /** Token identifying the next page of results to return. If empty, no further results are available. */
		nextPageToken: Option[String] = None
	)
	
	case class AddOnContext(
	  /** Immutable. Identifier of the course. */
		courseId: Option[String] = None,
	  /** Immutable. Deprecated, use `item_id` instead. */
		postId: Option[String] = None,
	  /** Immutable. Identifier of the `Announcement`, `CourseWork`, or `CourseWorkMaterial` under which the attachment is attached. */
		itemId: Option[String] = None,
	  /** Optional. Whether the post allows the teacher to see student work and passback grades. */
		supportsStudentWork: Option[Boolean] = None,
	  /** Add-on context corresponding to the requesting user's role as a student. Its presence implies that the requesting user is a student in the course. */
		studentContext: Option[Schema.StudentContext] = None,
	  /** Add-on context corresponding to the requesting user's role as a teacher. Its presence implies that the requesting user is a teacher in the course. */
		teacherContext: Option[Schema.TeacherContext] = None
	)
	
	case class StudentContext(
	  /** Requesting user's submission id to be used for grade passback and to identify the student when showing student work to the teacher. This is set exactly when `supportsStudentWork` is `true`. */
		submissionId: Option[String] = None
	)
	
	case class TeacherContext(
	
	)
	
	case class AddOnAttachment(
	  /** Immutable. Identifier of the course. */
		courseId: Option[String] = None,
	  /** Immutable. Deprecated, use `item_id` instead. */
		postId: Option[String] = None,
	  /** Immutable. Identifier of the `Announcement`, `CourseWork`, or `CourseWorkMaterial` under which the attachment is attached. Unique per course. */
		itemId: Option[String] = None,
	  /** Immutable. Classroom-assigned identifier for this attachment, unique per post. */
		id: Option[String] = None,
	  /** Required. Title of this attachment. The title must be between 1 and 1000 characters. */
		title: Option[String] = None,
	  /** Required. URI to show the teacher view of the attachment. The URI will be opened in an iframe with the `courseId`, `itemId`, `itemType`, and `attachmentId` query parameters set. */
		teacherViewUri: Option[Schema.EmbedUri] = None,
	  /** Required. URI to show the student view of the attachment. The URI will be opened in an iframe with the `courseId`, `itemId`, `itemType`, and `attachmentId` query parameters set. */
		studentViewUri: Option[Schema.EmbedUri] = None,
	  /** URI for the teacher to see student work on the attachment, if applicable. The URI will be opened in an iframe with the `courseId`, `itemId`, `itemType`, `attachmentId`, and `submissionId` query parameters set. This is the same `submissionId` returned in the [`AddOnContext.studentContext`](//devsite.google.com/classroom/reference/rest/v1/AddOnContext#StudentContext) field when a student views the attachment. If the URI is omitted or removed, `max_points` will also be discarded. */
		studentWorkReviewUri: Option[Schema.EmbedUri] = None,
	  /** Date, in UTC, that work on this attachment is due. This must be specified if `due_time` is specified. */
		dueDate: Option[Schema.Date] = None,
	  /** Time of day, in UTC, that work on this attachment is due. This must be specified if `due_date` is specified. */
		dueTime: Option[Schema.TimeOfDay] = None,
	  /** Maximum grade for this attachment. Can only be set if `studentWorkReviewUri` is set. Set to a non-zero value to indicate that the attachment supports grade passback. If set, this must be a non-negative integer value. When set to zero, the attachment will not support grade passback. */
		maxPoints: Option[BigDecimal] = None,
	  /** Output only. Identifiers of attachments that were previous copies of this attachment. If the attachment was previously copied by virtue of its parent post being copied, this enumerates the identifiers of attachments that were its previous copies in ascending chronological order of copy. */
		copyHistory: Option[List[Schema.CopyHistory]] = None
	)
	
	case class EmbedUri(
	  /** Required. URI to be iframed after being populated with query parameters. This must be a valid UTF-8 string containing between 1 and 1800 characters. */
		uri: Option[String] = None
	)
	
	case class CopyHistory(
	  /** Immutable. Identifier of the course. */
		courseId: Option[String] = None,
	  /** Immutable. Deprecated, use `item_id` instead. */
		postId: Option[String] = None,
	  /** Immutable. Identifier of the `Announcement`, `CourseWork`, or `CourseWorkMaterial` under which the attachment is attached. */
		itemId: Option[String] = None,
	  /** Immutable. Identifier of the attachment. */
		attachmentId: Option[String] = None
	)
	
	case class ListAddOnAttachmentsResponse(
	  /** Attachments under the given post. */
		addOnAttachments: Option[List[Schema.AddOnAttachment]] = None,
	  /** A token, which can be sent as `pageToken` to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None
	)
	
	object AddOnAttachmentStudentSubmission {
		enum PostSubmissionStateEnum extends Enum[PostSubmissionStateEnum] { case SUBMISSION_STATE_UNSPECIFIED, NEW, CREATED, TURNED_IN, RETURNED, RECLAIMED_BY_STUDENT }
	}
	case class AddOnAttachmentStudentSubmission(
	  /** Student grade on this attachment. If unset, no grade was set. */
		pointsEarned: Option[BigDecimal] = None,
	  /** Submission state of add-on attachment's parent post (i.e. assignment). */
		postSubmissionState: Option[Schema.AddOnAttachmentStudentSubmission.PostSubmissionStateEnum] = None
	)
	
	case class ListGuardianInvitationsResponse(
	  /** Guardian invitations that matched the list request. */
		guardianInvitations: Option[List[Schema.GuardianInvitation]] = None,
	  /** Token identifying the next page of results to return. If empty, no further results are available. */
		nextPageToken: Option[String] = None
	)
	
	object GuardianInvitation {
		enum StateEnum extends Enum[StateEnum] { case GUARDIAN_INVITATION_STATE_UNSPECIFIED, PENDING, COMPLETE }
	}
	case class GuardianInvitation(
	  /** ID of the student (in standard format) */
		studentId: Option[String] = None,
	  /** Unique identifier for this invitation. Read-only. */
		invitationId: Option[String] = None,
	  /** Email address that the invitation was sent to. This field is only visible to domain administrators. */
		invitedEmailAddress: Option[String] = None,
	  /** The state that this invitation is in. */
		state: Option[Schema.GuardianInvitation.StateEnum] = None,
	  /** The time that this invitation was created. Read-only. */
		creationTime: Option[String] = None
	)
	
	case class UserProfile(
	  /** Identifier of the user. Read-only. */
		id: Option[String] = None,
	  /** Name of the user. Read-only. */
		name: Option[Schema.Name] = None,
	  /** Email address of the user. Must request `https://www.googleapis.com/auth/classroom.profile.emails` scope for this field to be populated in a response body. Read-only. */
		emailAddress: Option[String] = None,
	  /** URL of user's profile photo. Must request `https://www.googleapis.com/auth/classroom.profile.photos` scope for this field to be populated in a response body. Read-only. */
		photoUrl: Option[String] = None,
	  /** Global permissions of the user. Read-only. */
		permissions: Option[List[Schema.GlobalPermission]] = None,
	  /** Represents whether a Google Workspace for Education user's domain administrator has explicitly verified them as being a teacher. This field is always false if the user is not a member of a Google Workspace for Education domain. Read-only */
		verifiedTeacher: Option[Boolean] = None
	)
	
	case class Name(
	  /** The user's first name. Read-only. */
		givenName: Option[String] = None,
	  /** The user's last name. Read-only. */
		familyName: Option[String] = None,
	  /** The user's full name formed by concatenating the first and last name values. Read-only. */
		fullName: Option[String] = None
	)
	
	object GlobalPermission {
		enum PermissionEnum extends Enum[PermissionEnum] { case PERMISSION_UNSPECIFIED, CREATE_COURSE }
	}
	case class GlobalPermission(
	  /** Permission value. */
		permission: Option[Schema.GlobalPermission.PermissionEnum] = None
	)
	
	case class Teacher(
	  /** Identifier of the course. Read-only. */
		courseId: Option[String] = None,
	  /** Identifier of the user. When specified as a parameter of a request, this identifier can be one of the following: &#42; the numeric identifier for the user &#42; the email address of the user &#42; the string literal `"me"`, indicating the requesting user */
		userId: Option[String] = None,
	  /** Global user information for the teacher. Read-only. */
		profile: Option[Schema.UserProfile] = None
	)
	
	case class ListTeachersResponse(
	  /** Teachers who match the list request. */
		teachers: Option[List[Schema.Teacher]] = None,
	  /** Token identifying the next page of results to return. If empty, no further results are available. */
		nextPageToken: Option[String] = None
	)
	
	case class Student(
	  /** Identifier of the course. Read-only. */
		courseId: Option[String] = None,
	  /** Identifier of the user. When specified as a parameter of a request, this identifier can be one of the following: &#42; the numeric identifier for the user &#42; the email address of the user &#42; the string literal `"me"`, indicating the requesting user */
		userId: Option[String] = None,
	  /** Global user information for the student. Read-only. */
		profile: Option[Schema.UserProfile] = None,
	  /** Information about a Drive Folder for this student's work in this course. Only visible to the student and domain administrators. Read-only. */
		studentWorkFolder: Option[Schema.DriveFolder] = None
	)
	
	case class ListStudentsResponse(
	  /** Students who match the list request. */
		students: Option[List[Schema.Student]] = None,
	  /** Token identifying the next page of results to return. If empty, no further results are available. */
		nextPageToken: Option[String] = None
	)
	
	case class ListGuardiansResponse(
	  /** Guardians on this page of results that met the criteria specified in the request. */
		guardians: Option[List[Schema.Guardian]] = None,
	  /** Token identifying the next page of results to return. If empty, no further results are available. */
		nextPageToken: Option[String] = None
	)
	
	case class Guardian(
	  /** Identifier for the student to whom the guardian relationship applies. */
		studentId: Option[String] = None,
	  /** Identifier for the guardian. */
		guardianId: Option[String] = None,
	  /** User profile for the guardian. */
		guardianProfile: Option[Schema.UserProfile] = None,
	  /** The email address to which the initial guardian invitation was sent. This field is only visible to domain administrators. */
		invitedEmailAddress: Option[String] = None
	)
	
	object Invitation {
		enum RoleEnum extends Enum[RoleEnum] { case COURSE_ROLE_UNSPECIFIED, STUDENT, TEACHER, OWNER }
	}
	case class Invitation(
	  /** Identifier assigned by Classroom. Read-only. */
		id: Option[String] = None,
	  /** Identifier of the invited user. When specified as a parameter of a request, this identifier can be set to one of the following: &#42; the numeric identifier for the user &#42; the email address of the user &#42; the string literal `"me"`, indicating the requesting user */
		userId: Option[String] = None,
	  /** Identifier of the course to invite the user to. */
		courseId: Option[String] = None,
	  /** Role to invite the user to have. Must not be `COURSE_ROLE_UNSPECIFIED`. */
		role: Option[Schema.Invitation.RoleEnum] = None
	)
	
	case class ListInvitationsResponse(
	  /** Invitations that match the list request. */
		invitations: Option[List[Schema.Invitation]] = None,
	  /** Token identifying the next page of results to return. If empty, no further results are available. */
		nextPageToken: Option[String] = None
	)
	
	case class Registration(
	  /** A server-generated unique identifier for this `Registration`. Read-only. */
		registrationId: Option[String] = None,
	  /** Specification for the class of notifications that Classroom should deliver to the destination. */
		feed: Option[Schema.Feed] = None,
	  /** The Cloud Pub/Sub topic that notifications are to be sent to. */
		cloudPubsubTopic: Option[Schema.CloudPubsubTopic] = None,
	  /** The time until which the `Registration` is effective. This is a read-only field assigned by the server. */
		expiryTime: Option[String] = None
	)
	
	object Feed {
		enum FeedTypeEnum extends Enum[FeedTypeEnum] { case FEED_TYPE_UNSPECIFIED, DOMAIN_ROSTER_CHANGES, COURSE_ROSTER_CHANGES, COURSE_WORK_CHANGES }
	}
	case class Feed(
	  /** The type of feed. */
		feedType: Option[Schema.Feed.FeedTypeEnum] = None,
	  /** Information about a `Feed` with a `feed_type` of `COURSE_ROSTER_CHANGES`. This field must be specified if `feed_type` is `COURSE_ROSTER_CHANGES`. */
		courseRosterChangesInfo: Option[Schema.CourseRosterChangesInfo] = None,
	  /** Information about a `Feed` with a `feed_type` of `COURSE_WORK_CHANGES`. This field must be specified if `feed_type` is `COURSE_WORK_CHANGES`. */
		courseWorkChangesInfo: Option[Schema.CourseWorkChangesInfo] = None
	)
	
	case class CourseRosterChangesInfo(
	  /** The `course_id` of the course to subscribe to roster changes for. */
		courseId: Option[String] = None
	)
	
	case class CourseWorkChangesInfo(
	  /** The `course_id` of the course to subscribe to work changes for. */
		courseId: Option[String] = None
	)
	
	case class CloudPubsubTopic(
	  /** The `name` field of a Cloud Pub/Sub [Topic](https://cloud.google.com/pubsub/docs/reference/rest/v1/projects.topics#Topic). */
		topicName: Option[String] = None
	)
}
