package com.boresjo.play.api.google.classroom

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Formats {
	given fmtCourse: Format[Schema.Course] = Json.format[Schema.Course]
	given fmtCourseCourseStateEnum: Format[Schema.Course.CourseStateEnum] = JsonEnumFormat[Schema.Course.CourseStateEnum]
	given fmtDriveFolder: Format[Schema.DriveFolder] = Json.format[Schema.DriveFolder]
	given fmtCourseMaterialSet: Format[Schema.CourseMaterialSet] = Json.format[Schema.CourseMaterialSet]
	given fmtGradebookSettings: Format[Schema.GradebookSettings] = Json.format[Schema.GradebookSettings]
	given fmtCourseMaterial: Format[Schema.CourseMaterial] = Json.format[Schema.CourseMaterial]
	given fmtDriveFile: Format[Schema.DriveFile] = Json.format[Schema.DriveFile]
	given fmtYouTubeVideo: Format[Schema.YouTubeVideo] = Json.format[Schema.YouTubeVideo]
	given fmtLink: Format[Schema.Link] = Json.format[Schema.Link]
	given fmtForm: Format[Schema.Form] = Json.format[Schema.Form]
	given fmtGradebookSettingsCalculationTypeEnum: Format[Schema.GradebookSettings.CalculationTypeEnum] = JsonEnumFormat[Schema.GradebookSettings.CalculationTypeEnum]
	given fmtGradebookSettingsDisplaySettingEnum: Format[Schema.GradebookSettings.DisplaySettingEnum] = JsonEnumFormat[Schema.GradebookSettings.DisplaySettingEnum]
	given fmtGradeCategory: Format[Schema.GradeCategory] = Json.format[Schema.GradeCategory]
	given fmtEmpty: Format[Schema.Empty] = Json.format[Schema.Empty]
	given fmtListCoursesResponse: Format[Schema.ListCoursesResponse] = Json.format[Schema.ListCoursesResponse]
	given fmtCourseAlias: Format[Schema.CourseAlias] = Json.format[Schema.CourseAlias]
	given fmtListCourseAliasesResponse: Format[Schema.ListCourseAliasesResponse] = Json.format[Schema.ListCourseAliasesResponse]
	given fmtCourseWork: Format[Schema.CourseWork] = Json.format[Schema.CourseWork]
	given fmtMaterial: Format[Schema.Material] = Json.format[Schema.Material]
	given fmtCourseWorkStateEnum: Format[Schema.CourseWork.StateEnum] = JsonEnumFormat[Schema.CourseWork.StateEnum]
	given fmtDate: Format[Schema.Date] = Json.format[Schema.Date]
	given fmtTimeOfDay: Format[Schema.TimeOfDay] = Json.format[Schema.TimeOfDay]
	given fmtCourseWorkWorkTypeEnum: Format[Schema.CourseWork.WorkTypeEnum] = JsonEnumFormat[Schema.CourseWork.WorkTypeEnum]
	given fmtCourseWorkAssigneeModeEnum: Format[Schema.CourseWork.AssigneeModeEnum] = JsonEnumFormat[Schema.CourseWork.AssigneeModeEnum]
	given fmtIndividualStudentsOptions: Format[Schema.IndividualStudentsOptions] = Json.format[Schema.IndividualStudentsOptions]
	given fmtCourseWorkSubmissionModificationModeEnum: Format[Schema.CourseWork.SubmissionModificationModeEnum] = JsonEnumFormat[Schema.CourseWork.SubmissionModificationModeEnum]
	given fmtAssignment: Format[Schema.Assignment] = Json.format[Schema.Assignment]
	given fmtMultipleChoiceQuestion: Format[Schema.MultipleChoiceQuestion] = Json.format[Schema.MultipleChoiceQuestion]
	given fmtSharedDriveFile: Format[Schema.SharedDriveFile] = Json.format[Schema.SharedDriveFile]
	given fmtSharedDriveFileShareModeEnum: Format[Schema.SharedDriveFile.ShareModeEnum] = JsonEnumFormat[Schema.SharedDriveFile.ShareModeEnum]
	given fmtListCourseWorkResponse: Format[Schema.ListCourseWorkResponse] = Json.format[Schema.ListCourseWorkResponse]
	given fmtAnnouncement: Format[Schema.Announcement] = Json.format[Schema.Announcement]
	given fmtAnnouncementStateEnum: Format[Schema.Announcement.StateEnum] = JsonEnumFormat[Schema.Announcement.StateEnum]
	given fmtAnnouncementAssigneeModeEnum: Format[Schema.Announcement.AssigneeModeEnum] = JsonEnumFormat[Schema.Announcement.AssigneeModeEnum]
	given fmtListAnnouncementsResponse: Format[Schema.ListAnnouncementsResponse] = Json.format[Schema.ListAnnouncementsResponse]
	given fmtCourseWorkMaterial: Format[Schema.CourseWorkMaterial] = Json.format[Schema.CourseWorkMaterial]
	given fmtCourseWorkMaterialStateEnum: Format[Schema.CourseWorkMaterial.StateEnum] = JsonEnumFormat[Schema.CourseWorkMaterial.StateEnum]
	given fmtCourseWorkMaterialAssigneeModeEnum: Format[Schema.CourseWorkMaterial.AssigneeModeEnum] = JsonEnumFormat[Schema.CourseWorkMaterial.AssigneeModeEnum]
	given fmtListCourseWorkMaterialResponse: Format[Schema.ListCourseWorkMaterialResponse] = Json.format[Schema.ListCourseWorkMaterialResponse]
	given fmtStudentSubmission: Format[Schema.StudentSubmission] = Json.format[Schema.StudentSubmission]
	given fmtStudentSubmissionStateEnum: Format[Schema.StudentSubmission.StateEnum] = JsonEnumFormat[Schema.StudentSubmission.StateEnum]
	given fmtStudentSubmissionCourseWorkTypeEnum: Format[Schema.StudentSubmission.CourseWorkTypeEnum] = JsonEnumFormat[Schema.StudentSubmission.CourseWorkTypeEnum]
	given fmtAssignmentSubmission: Format[Schema.AssignmentSubmission] = Json.format[Schema.AssignmentSubmission]
	given fmtShortAnswerSubmission: Format[Schema.ShortAnswerSubmission] = Json.format[Schema.ShortAnswerSubmission]
	given fmtMultipleChoiceSubmission: Format[Schema.MultipleChoiceSubmission] = Json.format[Schema.MultipleChoiceSubmission]
	given fmtSubmissionHistory: Format[Schema.SubmissionHistory] = Json.format[Schema.SubmissionHistory]
	given fmtAttachment: Format[Schema.Attachment] = Json.format[Schema.Attachment]
	given fmtStateHistory: Format[Schema.StateHistory] = Json.format[Schema.StateHistory]
	given fmtGradeHistory: Format[Schema.GradeHistory] = Json.format[Schema.GradeHistory]
	given fmtStateHistoryStateEnum: Format[Schema.StateHistory.StateEnum] = JsonEnumFormat[Schema.StateHistory.StateEnum]
	given fmtGradeHistoryGradeChangeTypeEnum: Format[Schema.GradeHistory.GradeChangeTypeEnum] = JsonEnumFormat[Schema.GradeHistory.GradeChangeTypeEnum]
	given fmtListStudentSubmissionsResponse: Format[Schema.ListStudentSubmissionsResponse] = Json.format[Schema.ListStudentSubmissionsResponse]
	given fmtTurnInStudentSubmissionRequest: Format[Schema.TurnInStudentSubmissionRequest] = Json.format[Schema.TurnInStudentSubmissionRequest]
	given fmtReclaimStudentSubmissionRequest: Format[Schema.ReclaimStudentSubmissionRequest] = Json.format[Schema.ReclaimStudentSubmissionRequest]
	given fmtReturnStudentSubmissionRequest: Format[Schema.ReturnStudentSubmissionRequest] = Json.format[Schema.ReturnStudentSubmissionRequest]
	given fmtModifyAttachmentsRequest: Format[Schema.ModifyAttachmentsRequest] = Json.format[Schema.ModifyAttachmentsRequest]
	given fmtModifyCourseWorkAssigneesRequest: Format[Schema.ModifyCourseWorkAssigneesRequest] = Json.format[Schema.ModifyCourseWorkAssigneesRequest]
	given fmtModifyCourseWorkAssigneesRequestAssigneeModeEnum: Format[Schema.ModifyCourseWorkAssigneesRequest.AssigneeModeEnum] = JsonEnumFormat[Schema.ModifyCourseWorkAssigneesRequest.AssigneeModeEnum]
	given fmtModifyIndividualStudentsOptions: Format[Schema.ModifyIndividualStudentsOptions] = Json.format[Schema.ModifyIndividualStudentsOptions]
	given fmtModifyAnnouncementAssigneesRequest: Format[Schema.ModifyAnnouncementAssigneesRequest] = Json.format[Schema.ModifyAnnouncementAssigneesRequest]
	given fmtModifyAnnouncementAssigneesRequestAssigneeModeEnum: Format[Schema.ModifyAnnouncementAssigneesRequest.AssigneeModeEnum] = JsonEnumFormat[Schema.ModifyAnnouncementAssigneesRequest.AssigneeModeEnum]
	given fmtTopic: Format[Schema.Topic] = Json.format[Schema.Topic]
	given fmtListTopicResponse: Format[Schema.ListTopicResponse] = Json.format[Schema.ListTopicResponse]
	given fmtAddOnContext: Format[Schema.AddOnContext] = Json.format[Schema.AddOnContext]
	given fmtStudentContext: Format[Schema.StudentContext] = Json.format[Schema.StudentContext]
	given fmtTeacherContext: Format[Schema.TeacherContext] = Json.format[Schema.TeacherContext]
	given fmtAddOnAttachment: Format[Schema.AddOnAttachment] = Json.format[Schema.AddOnAttachment]
	given fmtEmbedUri: Format[Schema.EmbedUri] = Json.format[Schema.EmbedUri]
	given fmtCopyHistory: Format[Schema.CopyHistory] = Json.format[Schema.CopyHistory]
	given fmtListAddOnAttachmentsResponse: Format[Schema.ListAddOnAttachmentsResponse] = Json.format[Schema.ListAddOnAttachmentsResponse]
	given fmtAddOnAttachmentStudentSubmission: Format[Schema.AddOnAttachmentStudentSubmission] = Json.format[Schema.AddOnAttachmentStudentSubmission]
	given fmtAddOnAttachmentStudentSubmissionPostSubmissionStateEnum: Format[Schema.AddOnAttachmentStudentSubmission.PostSubmissionStateEnum] = JsonEnumFormat[Schema.AddOnAttachmentStudentSubmission.PostSubmissionStateEnum]
	given fmtListGuardianInvitationsResponse: Format[Schema.ListGuardianInvitationsResponse] = Json.format[Schema.ListGuardianInvitationsResponse]
	given fmtGuardianInvitation: Format[Schema.GuardianInvitation] = Json.format[Schema.GuardianInvitation]
	given fmtGuardianInvitationStateEnum: Format[Schema.GuardianInvitation.StateEnum] = JsonEnumFormat[Schema.GuardianInvitation.StateEnum]
	given fmtUserProfile: Format[Schema.UserProfile] = Json.format[Schema.UserProfile]
	given fmtName: Format[Schema.Name] = Json.format[Schema.Name]
	given fmtGlobalPermission: Format[Schema.GlobalPermission] = Json.format[Schema.GlobalPermission]
	given fmtGlobalPermissionPermissionEnum: Format[Schema.GlobalPermission.PermissionEnum] = JsonEnumFormat[Schema.GlobalPermission.PermissionEnum]
	given fmtTeacher: Format[Schema.Teacher] = Json.format[Schema.Teacher]
	given fmtListTeachersResponse: Format[Schema.ListTeachersResponse] = Json.format[Schema.ListTeachersResponse]
	given fmtStudent: Format[Schema.Student] = Json.format[Schema.Student]
	given fmtListStudentsResponse: Format[Schema.ListStudentsResponse] = Json.format[Schema.ListStudentsResponse]
	given fmtListGuardiansResponse: Format[Schema.ListGuardiansResponse] = Json.format[Schema.ListGuardiansResponse]
	given fmtGuardian: Format[Schema.Guardian] = Json.format[Schema.Guardian]
	given fmtInvitation: Format[Schema.Invitation] = Json.format[Schema.Invitation]
	given fmtInvitationRoleEnum: Format[Schema.Invitation.RoleEnum] = JsonEnumFormat[Schema.Invitation.RoleEnum]
	given fmtListInvitationsResponse: Format[Schema.ListInvitationsResponse] = Json.format[Schema.ListInvitationsResponse]
	given fmtRegistration: Format[Schema.Registration] = Json.format[Schema.Registration]
	given fmtFeed: Format[Schema.Feed] = Json.format[Schema.Feed]
	given fmtCloudPubsubTopic: Format[Schema.CloudPubsubTopic] = Json.format[Schema.CloudPubsubTopic]
	given fmtFeedFeedTypeEnum: Format[Schema.Feed.FeedTypeEnum] = JsonEnumFormat[Schema.Feed.FeedTypeEnum]
	given fmtCourseRosterChangesInfo: Format[Schema.CourseRosterChangesInfo] = Json.format[Schema.CourseRosterChangesInfo]
	given fmtCourseWorkChangesInfo: Format[Schema.CourseWorkChangesInfo] = Json.format[Schema.CourseWorkChangesInfo]
}
