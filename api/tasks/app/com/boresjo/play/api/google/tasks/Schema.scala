package com.boresjo.play.api.google.tasks

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	case class Tasks(
	  /** Type of the resource. This is always "tasks#tasks". */
		kind: Option[String] = None,
	  /** ETag of the resource. */
		etag: Option[String] = None,
	  /** Token used to access the next page of this result. */
		nextPageToken: Option[String] = None,
	  /** Collection of tasks. */
		items: Option[List[Schema.Task]] = None
	)
	
	case class SpaceInfo(
	  /** Output only. The Chat space where this task originates from. The format is "spaces/{space}". */
		space: Option[String] = None
	)
	
	object Task {
		case class LinksItem(
		  /** Type of the link, e.g. "email". */
			`type`: Option[String] = None,
		  /** The URL. */
			link: Option[String] = None,
		  /** The description. In HTML speak: Everything between <a> and </a>. */
			description: Option[String] = None
		)
	}
	case class Task(
	  /** Output only. Last modification time of the task (as a RFC 3339 timestamp). */
		updated: Option[String] = None,
	  /** Output only. URL pointing to this task. Used to retrieve, update, or delete this task. */
		selfLink: Option[String] = None,
	  /** Notes describing the task. Tasks assigned from Google Docs cannot have notes. Optional. Maximum length allowed: 8192 characters. */
		notes: Option[String] = None,
	  /** Status of the task. This is either "needsAction" or "completed". */
		status: Option[String] = None,
	  /** Task identifier. */
		id: Option[String] = None,
	  /** Flag indicating whether the task is hidden. This is the case if the task had been marked completed when the task list was last cleared. The default is False. This field is read-only. */
		hidden: Option[Boolean] = None,
	  /** Due date of the task (as a RFC 3339 timestamp). Optional. The due date only records date information; the time portion of the timestamp is discarded when setting the due date. It isn't possible to read or write the time that a task is due via the API. */
		due: Option[String] = None,
	  /** Output only. An absolute link to the task in the Google Tasks Web UI. */
		webViewLink: Option[String] = None,
	  /** ETag of the resource. */
		etag: Option[String] = None,
	  /** Output only. Context information for assigned tasks. A task can be assigned to a user, currently possible from surfaces like Docs and Chat Spaces. This field is populated for tasks assigned to the current user and identifies where the task was assigned from. This field is read-only. */
		assignmentInfo: Option[Schema.AssignmentInfo] = None,
	  /** Output only. Parent task identifier. This field is omitted if it is a top-level task. Use the "move" method to move the task under a different parent or to the top level. A parent task can never be an assigned task (from Chat Spaces, Docs). This field is read-only. */
		parent: Option[String] = None,
	  /** Output only. Collection of links. This collection is read-only. */
		links: Option[List[Schema.Task.LinksItem]] = None,
	  /** Completion date of the task (as a RFC 3339 timestamp). This field is omitted if the task has not been completed. */
		completed: Option[String] = None,
	  /** Output only. Type of the resource. This is always "tasks#task". */
		kind: Option[String] = None,
	  /** Flag indicating whether the task has been deleted. For assigned tasks this field is read-only. They can only be deleted by calling tasks.delete, in which case both the assigned task and the original task (in Docs or Chat Spaces) are deleted. To delete the assigned task only, navigate to the assignment surface and unassign the task from there. The default is False. */
		deleted: Option[Boolean] = None,
	  /** Title of the task. Maximum length allowed: 1024 characters. */
		title: Option[String] = None,
	  /** Output only. String indicating the position of the task among its sibling tasks under the same parent task or at the top level. If this string is greater than another task's corresponding position string according to lexicographical ordering, the task is positioned after the other task under the same parent task (or at the top level). Use the "move" method to move the task to another position. */
		position: Option[String] = None
	)
	
	case class TaskLists(
	  /** Collection of task lists. */
		items: Option[List[Schema.TaskList]] = None,
	  /** ETag of the resource. */
		etag: Option[String] = None,
	  /** Token that can be used to request the next page of this result. */
		nextPageToken: Option[String] = None,
	  /** Type of the resource. This is always "tasks#taskLists". */
		kind: Option[String] = None
	)
	
	case class TaskList(
	  /** Title of the task list. Maximum length allowed: 1024 characters. */
		title: Option[String] = None,
	  /** Output only. URL pointing to this task list. Used to retrieve, update, or delete this task list. */
		selfLink: Option[String] = None,
	  /** Output only. Last modification time of the task list (as a RFC 3339 timestamp). */
		updated: Option[String] = None,
	  /** Task list identifier. */
		id: Option[String] = None,
	  /** ETag of the resource. */
		etag: Option[String] = None,
	  /** Output only. Type of the resource. This is always "tasks#taskList". */
		kind: Option[String] = None
	)
	
	case class DriveResourceInfo(
	  /** Output only. Identifier of the file in the Drive API. */
		driveFileId: Option[String] = None,
	  /** Output only. Resource key required to access files shared via a shared link. Not required for all files. See also developers.google.com/drive/api/guides/resource-keys. */
		resourceKey: Option[String] = None
	)
	
	object AssignmentInfo {
		enum SurfaceTypeEnum extends Enum[SurfaceTypeEnum] { case CONTEXT_TYPE_UNSPECIFIED, GMAIL, DOCUMENT, SPACE }
	}
	case class AssignmentInfo(
	  /** Output only. The type of surface this assigned task originates from. Currently limited to DOCUMENT or SPACE. */
		surfaceType: Option[Schema.AssignmentInfo.SurfaceTypeEnum] = None,
	  /** Output only. Information about the Drive file where this task originates from. Currently, the Drive file can only be a document. This field is read-only. */
		driveResourceInfo: Option[Schema.DriveResourceInfo] = None,
	  /** Output only. An absolute link to the original task in the surface of assignment (Docs, Chat spaces, etc.). */
		linkToTask: Option[String] = None,
	  /** Output only. Information about the Chat Space where this task originates from. This field is read-only. */
		spaceInfo: Option[Schema.SpaceInfo] = None
	)
}
