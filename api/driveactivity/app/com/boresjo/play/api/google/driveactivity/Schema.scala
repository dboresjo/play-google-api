package com.boresjo.play.api.google.driveactivity

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	case class QueryDriveActivityRequest(
	  /** Return activities for this Drive item. The format is `items/ITEM_ID`. */
		itemName: Option[String] = None,
	  /** Return activities for this Drive folder, plus all children and descendants. The format is `items/ITEM_ID`. */
		ancestorName: Option[String] = None,
	  /** Details on how to consolidate related actions that make up the activity. If not set, then related actions aren't consolidated. */
		consolidationStrategy: Option[Schema.ConsolidationStrategy] = None,
	  /** The minimum number of activities desired in the response; the server attempts to return at least this quantity. The server may also return fewer activities if it has a partial response ready before the request times out. If not set, a default value is used. */
		pageSize: Option[Int] = None,
	  /** The token identifies which page of results to return. Set this to the next_page_token value returned from a previous query to obtain the following page of results. If not set, the first page of results is returned. */
		pageToken: Option[String] = None,
	  /** The filtering for items returned from this query request. The format of the filter string is a sequence of expressions, joined by an optional "AND", where each expression is of the form "field operator value". Supported fields: - `time`: Uses numerical operators on date values either in terms of milliseconds since Jan 1, 1970 or in RFC 3339 format. Examples: - `time > 1452409200000 AND time <= 1492812924310` - `time >= "2016-01-10T01:02:03-05:00"` - `detail.action_detail_case`: Uses the "has" operator (:) and either a singular value or a list of allowed action types enclosed in parentheses, separated by a space. To exclude a result from the response, prepend a hyphen (`-`) to the beginning of the filter string. Examples: - `detail.action_detail_case:RENAME` - `detail.action_detail_case:(CREATE RESTORE)` - `-detail.action_detail_case:MOVE`  */
		filter: Option[String] = None
	)
	
	case class ConsolidationStrategy(
	  /** The individual activities are not consolidated. */
		none: Option[Schema.NoConsolidation] = None,
	  /** The individual activities are consolidated using the legacy strategy. */
		legacy: Option[Schema.Legacy] = None
	)
	
	case class NoConsolidation(
	
	)
	
	case class Legacy(
	
	)
	
	case class QueryDriveActivityResponse(
	  /** List of activity requested. */
		activities: Option[List[Schema.DriveActivity]] = None,
	  /** Token to retrieve the next page of results, or empty if there are no more results in the list. */
		nextPageToken: Option[String] = None
	)
	
	case class DriveActivity(
	  /** Key information about the primary action for this activity. This is either representative, or the most important, of all actions in the activity, according to the ConsolidationStrategy in the request. */
		primaryActionDetail: Option[Schema.ActionDetail] = None,
	  /** All actor(s) responsible for the activity. */
		actors: Option[List[Schema.Actor]] = None,
	  /** Details on all actions in this activity. */
		actions: Option[List[Schema.Action]] = None,
	  /** All Google Drive objects this activity is about (e.g. file, folder, drive). This represents the state of the target immediately after the actions occurred. */
		targets: Option[List[Schema.Target]] = None,
	  /** The activity occurred at this specific time. */
		timestamp: Option[String] = None,
	  /** The activity occurred over this time range. */
		timeRange: Option[Schema.TimeRange] = None
	)
	
	case class ActionDetail(
	  /** An object was created. */
		create: Option[Schema.Create] = None,
	  /** An object was edited. */
		edit: Option[Schema.Edit] = None,
	  /** An object was moved. */
		move: Option[Schema.Move] = None,
	  /** An object was renamed. */
		rename: Option[Schema.Rename] = None,
	  /** An object was deleted. */
		delete: Option[Schema.Delete] = None,
	  /** A deleted object was restored. */
		restore: Option[Schema.Restore] = None,
	  /** The permission on an object was changed. */
		permissionChange: Option[Schema.PermissionChange] = None,
	  /** A change about comments was made. */
		comment: Option[Schema.Comment] = None,
	  /** A change happened in data leak prevention status. */
		dlpChange: Option[Schema.DataLeakPreventionChange] = None,
	  /** An object was referenced in an application outside of Drive/Docs. */
		reference: Option[Schema.ApplicationReference] = None,
	  /** Settings were changed. */
		settingsChange: Option[Schema.SettingsChange] = None,
	  /** Label was changed. */
		appliedLabelChange: Option[Schema.AppliedLabelChange] = None
	)
	
	case class Create(
	  /** If present, indicates the object was newly created (e.g. as a blank document), not derived from a Drive object or external object. */
		`new`: Option[Schema.New] = None,
	  /** If present, indicates the object originated externally and was uploaded to Drive. */
		upload: Option[Schema.Upload] = None,
	  /** If present, indicates the object was created by copying an existing Drive object. */
		copy: Option[Schema.Copy] = None
	)
	
	case class New(
	
	)
	
	case class Upload(
	
	)
	
	case class Copy(
	  /** The original object. */
		originalObject: Option[Schema.TargetReference] = None
	)
	
	case class TargetReference(
	  /** The target is a Drive item. */
		driveItem: Option[Schema.DriveItemReference] = None,
	  /** The target is a shared drive. */
		drive: Option[Schema.DriveReference] = None,
	  /** This field is deprecated; please use the `drive` field instead. */
		teamDrive: Option[Schema.TeamDriveReference] = None
	)
	
	case class DriveItemReference(
	  /** The target Drive item. The format is `items/ITEM_ID`. */
		name: Option[String] = None,
	  /** The title of the Drive item. */
		title: Option[String] = None,
	  /** This field is deprecated; please use the `driveFile` field instead. */
		file: Option[Schema.File] = None,
	  /** This field is deprecated; please use the `driveFolder` field instead. */
		folder: Option[Schema.Folder] = None,
	  /** The Drive item is a file. */
		driveFile: Option[Schema.DriveFile] = None,
	  /** The Drive item is a folder. Includes information about the type of folder. */
		driveFolder: Option[Schema.DriveFolder] = None
	)
	
	case class File(
	
	)
	
	object Folder {
		enum TypeEnum extends Enum[TypeEnum] { case TYPE_UNSPECIFIED, MY_DRIVE_ROOT, TEAM_DRIVE_ROOT, STANDARD_FOLDER }
	}
	case class Folder(
	  /** This field is deprecated; please see `DriveFolder.type` instead. */
		`type`: Option[Schema.Folder.TypeEnum] = None
	)
	
	case class DriveFile(
	
	)
	
	object DriveFolder {
		enum TypeEnum extends Enum[TypeEnum] { case TYPE_UNSPECIFIED, MY_DRIVE_ROOT, SHARED_DRIVE_ROOT, STANDARD_FOLDER }
	}
	case class DriveFolder(
	  /** The type of Drive folder. */
		`type`: Option[Schema.DriveFolder.TypeEnum] = None
	)
	
	case class DriveReference(
	  /** The resource name of the shared drive. The format is `COLLECTION_ID/DRIVE_ID`. Clients should not assume a specific collection ID for this resource name. */
		name: Option[String] = None,
	  /** The title of the shared drive. */
		title: Option[String] = None
	)
	
	case class TeamDriveReference(
	  /** This field is deprecated; please see `DriveReference.name` instead. */
		name: Option[String] = None,
	  /** This field is deprecated; please see `DriveReference.title` instead. */
		title: Option[String] = None
	)
	
	case class Edit(
	
	)
	
	case class Move(
	  /** The added parent object(s). */
		addedParents: Option[List[Schema.TargetReference]] = None,
	  /** The removed parent object(s). */
		removedParents: Option[List[Schema.TargetReference]] = None
	)
	
	case class Rename(
	  /** The previous title of the drive object. */
		oldTitle: Option[String] = None,
	  /** The new title of the drive object. */
		newTitle: Option[String] = None
	)
	
	object Delete {
		enum TypeEnum extends Enum[TypeEnum] { case TYPE_UNSPECIFIED, TRASH, PERMANENT_DELETE }
	}
	case class Delete(
	  /** The type of delete action taken. */
		`type`: Option[Schema.Delete.TypeEnum] = None
	)
	
	object Restore {
		enum TypeEnum extends Enum[TypeEnum] { case TYPE_UNSPECIFIED, UNTRASH }
	}
	case class Restore(
	  /** The type of restore action taken. */
		`type`: Option[Schema.Restore.TypeEnum] = None
	)
	
	case class PermissionChange(
	  /** The set of permissions added by this change. */
		addedPermissions: Option[List[Schema.Permission]] = None,
	  /** The set of permissions removed by this change. */
		removedPermissions: Option[List[Schema.Permission]] = None
	)
	
	object Permission {
		enum RoleEnum extends Enum[RoleEnum] { case ROLE_UNSPECIFIED, OWNER, ORGANIZER, FILE_ORGANIZER, EDITOR, COMMENTER, VIEWER, PUBLISHED_VIEWER }
	}
	case class Permission(
	  /** Indicates the [Google Drive permissions role](https://developers.google.com/drive/web/manage-sharing#roles). The role determines a user's ability to read, write, and comment on items. */
		role: Option[Schema.Permission.RoleEnum] = None,
	  /** The user to whom this permission applies. */
		user: Option[Schema.User] = None,
	  /** The group to whom this permission applies. */
		group: Option[Schema.Group] = None,
	  /** The domain to whom this permission applies. */
		domain: Option[Schema.Domain] = None,
	  /** If set, this permission applies to anyone, even logged out users. */
		anyone: Option[Schema.Anyone] = None,
	  /** If true, the item can be discovered (e.g. in the user's "Shared with me" collection) without needing a link to the item. */
		allowDiscovery: Option[Boolean] = None
	)
	
	case class User(
	  /** A known user. */
		knownUser: Option[Schema.KnownUser] = None,
	  /** A user whose account has since been deleted. */
		deletedUser: Option[Schema.DeletedUser] = None,
	  /** A user about whom nothing is currently known. */
		unknownUser: Option[Schema.UnknownUser] = None
	)
	
	case class KnownUser(
	  /** The identifier for this user that can be used with the People API to get more information. The format is `people/ACCOUNT_ID`. See https://developers.google.com/people/. */
		personName: Option[String] = None,
	  /** True if this is the user making the request. */
		isCurrentUser: Option[Boolean] = None
	)
	
	case class DeletedUser(
	
	)
	
	case class UnknownUser(
	
	)
	
	case class Group(
	  /** The email address of the group. */
		email: Option[String] = None,
	  /** The title of the group. */
		title: Option[String] = None
	)
	
	case class Domain(
	  /** The name of the domain, e.g. `google.com`. */
		name: Option[String] = None,
	  /** An opaque string used to identify this domain. */
		legacyId: Option[String] = None
	)
	
	case class Anyone(
	
	)
	
	case class Comment(
	  /** A change on a regular posted comment. */
		post: Option[Schema.Post] = None,
	  /** A change on an assignment. */
		assignment: Option[Schema.Assignment] = None,
	  /** A change on a suggestion. */
		suggestion: Option[Schema.Suggestion] = None,
	  /** Users who are mentioned in this comment. */
		mentionedUsers: Option[List[Schema.User]] = None
	)
	
	object Post {
		enum SubtypeEnum extends Enum[SubtypeEnum] { case SUBTYPE_UNSPECIFIED, ADDED, DELETED, REPLY_ADDED, REPLY_DELETED, RESOLVED, REOPENED }
	}
	case class Post(
	  /** The sub-type of this event. */
		subtype: Option[Schema.Post.SubtypeEnum] = None
	)
	
	object Assignment {
		enum SubtypeEnum extends Enum[SubtypeEnum] { case SUBTYPE_UNSPECIFIED, ADDED, DELETED, REPLY_ADDED, REPLY_DELETED, RESOLVED, REOPENED, REASSIGNED }
	}
	case class Assignment(
	  /** The sub-type of this event. */
		subtype: Option[Schema.Assignment.SubtypeEnum] = None,
	  /** The user to whom the comment was assigned. */
		assignedUser: Option[Schema.User] = None
	)
	
	object Suggestion {
		enum SubtypeEnum extends Enum[SubtypeEnum] { case SUBTYPE_UNSPECIFIED, ADDED, DELETED, REPLY_ADDED, REPLY_DELETED, ACCEPTED, REJECTED, ACCEPT_DELETED, REJECT_DELETED }
	}
	case class Suggestion(
	  /** The sub-type of this event. */
		subtype: Option[Schema.Suggestion.SubtypeEnum] = None
	)
	
	object DataLeakPreventionChange {
		enum TypeEnum extends Enum[TypeEnum] { case TYPE_UNSPECIFIED, FLAGGED, CLEARED }
	}
	case class DataLeakPreventionChange(
	  /** The type of Data Leak Prevention (DLP) change. */
		`type`: Option[Schema.DataLeakPreventionChange.TypeEnum] = None
	)
	
	object ApplicationReference {
		enum TypeEnum extends Enum[TypeEnum] { case UNSPECIFIED_REFERENCE_TYPE, LINK, DISCUSS }
	}
	case class ApplicationReference(
	  /** The reference type corresponding to this event. */
		`type`: Option[Schema.ApplicationReference.TypeEnum] = None
	)
	
	case class SettingsChange(
	  /** The set of changes made to restrictions. */
		restrictionChanges: Option[List[Schema.RestrictionChange]] = None
	)
	
	object RestrictionChange {
		enum FeatureEnum extends Enum[FeatureEnum] { case FEATURE_UNSPECIFIED, SHARING_OUTSIDE_DOMAIN, DIRECT_SHARING, ITEM_DUPLICATION, DRIVE_FILE_STREAM, FILE_ORGANIZER_CAN_SHARE_FOLDERS }
		enum NewRestrictionEnum extends Enum[NewRestrictionEnum] { case RESTRICTION_UNSPECIFIED, UNRESTRICTED, FULLY_RESTRICTED }
	}
	case class RestrictionChange(
	  /** The feature which had a change in restriction policy. */
		feature: Option[Schema.RestrictionChange.FeatureEnum] = None,
	  /** The restriction in place after the change. */
		newRestriction: Option[Schema.RestrictionChange.NewRestrictionEnum] = None
	)
	
	case class AppliedLabelChange(
	  /** Changes that were made to the Label on the Target. */
		changes: Option[List[Schema.AppliedLabelChangeDetail]] = None
	)
	
	object AppliedLabelChangeDetail {
		enum TypesEnum extends Enum[TypesEnum] { case TYPE_UNSPECIFIED, LABEL_ADDED, LABEL_REMOVED, LABEL_FIELD_VALUE_CHANGED, LABEL_APPLIED_BY_ITEM_CREATE }
	}
	case class AppliedLabelChangeDetail(
	  /** The Label name representing the Label that changed. This name always contains the revision of the Label that was used when this Action occurred. The format is `labels/id@revision`. */
		label: Option[String] = None,
	  /** The types of changes made to the Label on the Target. */
		types: Option[List[Schema.AppliedLabelChangeDetail.TypesEnum]] = None,
	  /** The human-readable title of the label that changed. */
		title: Option[String] = None,
	  /** Field Changes. Only present if `types` contains `LABEL_FIELD_VALUE_CHANGED`. */
		fieldChanges: Option[List[Schema.FieldValueChange]] = None
	)
	
	case class FieldValueChange(
	  /** The ID of this field. Field IDs are unique within a Label. */
		fieldId: Option[String] = None,
	  /** The value that was previously set on the field. If not present, the field was newly set. At least one of {old_value|new_value} is always set. */
		oldValue: Option[Schema.FieldValue] = None,
	  /** The value that is now set on the field. If not present, the field was cleared. At least one of {old_value|new_value} is always set. */
		newValue: Option[Schema.FieldValue] = None,
	  /** The human-readable display name for this field. */
		displayName: Option[String] = None
	)
	
	case class FieldValue(
	  /** Text Field value. */
		text: Option[Schema.Text] = None,
	  /** Text List Field value. */
		textList: Option[Schema.TextList] = None,
	  /** Selection Field value. */
		selection: Option[Schema.Selection] = None,
	  /** Selection List Field value. */
		selectionList: Option[Schema.SelectionList] = None,
	  /** Integer Field value. */
		integer: Option[Schema.Integer] = None,
	  /** User Field value. */
		user: Option[Schema.SingleUser] = None,
	  /** User List Field value. */
		userList: Option[Schema.UserList] = None,
	  /** Date Field value. */
		date: Option[Schema.Date] = None
	)
	
	case class Text(
	  /** Value of Text Field. */
		value: Option[String] = None
	)
	
	case class TextList(
	  /** Text values. */
		values: Option[List[Schema.Text]] = None
	)
	
	case class Selection(
	  /** Selection value as Field Choice ID. */
		value: Option[String] = None,
	  /** Selection value as human-readable display string. */
		displayName: Option[String] = None
	)
	
	case class SelectionList(
	  /** Selection values. */
		values: Option[List[Schema.Selection]] = None
	)
	
	case class Integer(
	  /** Integer value. */
		value: Option[String] = None
	)
	
	case class SingleUser(
	  /** User value as email. */
		value: Option[String] = None
	)
	
	case class UserList(
	  /** User values. */
		values: Option[List[Schema.SingleUser]] = None
	)
	
	case class Date(
	  /** Date value. */
		value: Option[String] = None
	)
	
	case class Actor(
	  /** An end user. */
		user: Option[Schema.User] = None,
	  /** An anonymous user. */
		anonymous: Option[Schema.AnonymousUser] = None,
	  /** An account acting on behalf of another. */
		impersonation: Option[Schema.Impersonation] = None,
	  /** A non-user actor (i.e. system triggered). */
		system: Option[Schema.SystemEvent] = None,
	  /** An administrator. */
		administrator: Option[Schema.Administrator] = None
	)
	
	case class AnonymousUser(
	
	)
	
	case class Impersonation(
	  /** The impersonated user. */
		impersonatedUser: Option[Schema.User] = None
	)
	
	object SystemEvent {
		enum TypeEnum extends Enum[TypeEnum] { case TYPE_UNSPECIFIED, USER_DELETION, TRASH_AUTO_PURGE }
	}
	case class SystemEvent(
	  /** The type of the system event that may triggered activity. */
		`type`: Option[Schema.SystemEvent.TypeEnum] = None
	)
	
	case class Administrator(
	
	)
	
	case class Action(
	  /** The type and detailed information about the action. */
		detail: Option[Schema.ActionDetail] = None,
	  /** The actor responsible for this action (or empty if all actors are responsible). */
		actor: Option[Schema.Actor] = None,
	  /** The target this action affects (or empty if affecting all targets). This represents the state of the target immediately after this action occurred. */
		target: Option[Schema.Target] = None,
	  /** The action occurred at this specific time. */
		timestamp: Option[String] = None,
	  /** The action occurred over this time range. */
		timeRange: Option[Schema.TimeRange] = None
	)
	
	case class Target(
	  /** The target is a Drive item. */
		driveItem: Option[Schema.DriveItem] = None,
	  /** The target is a shared drive. */
		drive: Option[Schema.Drive] = None,
	  /** The target is a comment on a Drive file. */
		fileComment: Option[Schema.FileComment] = None,
	  /** This field is deprecated; please use the `drive` field instead. */
		teamDrive: Option[Schema.TeamDrive] = None
	)
	
	case class DriveItem(
	  /** The target Drive item. The format is `items/ITEM_ID`. */
		name: Option[String] = None,
	  /** The title of the Drive item. */
		title: Option[String] = None,
	  /** This field is deprecated; please use the `driveFile` field instead. */
		file: Option[Schema.File] = None,
	  /** This field is deprecated; please use the `driveFolder` field instead. */
		folder: Option[Schema.Folder] = None,
	  /** The Drive item is a file. */
		driveFile: Option[Schema.DriveFile] = None,
	  /** The Drive item is a folder. Includes information about the type of folder. */
		driveFolder: Option[Schema.DriveFolder] = None,
	  /** The MIME type of the Drive item. See https://developers.google.com/drive/v3/web/mime-types. */
		mimeType: Option[String] = None,
	  /** Information about the owner of this Drive item. */
		owner: Option[Schema.Owner] = None
	)
	
	case class Owner(
	  /** The user that owns the Drive item. */
		user: Option[Schema.User] = None,
	  /** The drive that owns the item. */
		drive: Option[Schema.DriveReference] = None,
	  /** This field is deprecated; please use the `drive` field instead. */
		teamDrive: Option[Schema.TeamDriveReference] = None,
	  /** The domain of the Drive item owner. */
		domain: Option[Schema.Domain] = None
	)
	
	case class Drive(
	  /** The resource name of the shared drive. The format is `COLLECTION_ID/DRIVE_ID`. Clients should not assume a specific collection ID for this resource name. */
		name: Option[String] = None,
	  /** The title of the shared drive. */
		title: Option[String] = None,
	  /** The root of this shared drive. */
		root: Option[Schema.DriveItem] = None
	)
	
	case class FileComment(
	  /** The comment in the discussion thread. This identifier is an opaque string compatible with the Drive API; see https://developers.google.com/drive/v3/reference/comments/get */
		legacyCommentId: Option[String] = None,
	  /** The discussion thread to which the comment was added. This identifier is an opaque string compatible with the Drive API and references the first comment in a discussion; see https://developers.google.com/drive/v3/reference/comments/get */
		legacyDiscussionId: Option[String] = None,
	  /** The link to the discussion thread containing this comment, for example, `https://docs.google.com/DOCUMENT_ID/edit?disco=THREAD_ID`. */
		linkToDiscussion: Option[String] = None,
	  /** The Drive item containing this comment. */
		parent: Option[Schema.DriveItem] = None
	)
	
	case class TeamDrive(
	  /** This field is deprecated; please see `Drive.name` instead. */
		name: Option[String] = None,
	  /** This field is deprecated; please see `Drive.title` instead. */
		title: Option[String] = None,
	  /** This field is deprecated; please see `Drive.root` instead. */
		root: Option[Schema.DriveItem] = None
	)
	
	case class TimeRange(
	  /** The start of the time range. */
		startTime: Option[String] = None,
	  /** The end of the time range. */
		endTime: Option[String] = None
	)
}
