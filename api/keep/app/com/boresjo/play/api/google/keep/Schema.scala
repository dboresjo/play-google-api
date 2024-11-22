package com.boresjo.play.api.google.keep

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	case class Note(
	  /** Output only. The resource name of this note. See general note on identifiers in KeepService. */
		name: Option[String] = None,
	  /** Output only. When this note was created. */
		createTime: Option[String] = None,
	  /** Output only. When this note was last modified. */
		updateTime: Option[String] = None,
	  /** Output only. When this note was trashed. If `trashed`, the note is eventually deleted. If the note is not trashed, this field is not set (and the trashed field is `false`). */
		trashTime: Option[String] = None,
	  /** Output only. `true` if this note has been trashed. If trashed, the note is eventually deleted. */
		trashed: Option[Boolean] = None,
	  /** Output only. The attachments attached to this note. */
		attachments: Option[List[Schema.Attachment]] = None,
	  /** Output only. The list of permissions set on the note. Contains at least one entry for the note owner. */
		permissions: Option[List[Schema.Permission]] = None,
	  /** The title of the note. Length must be less than 1,000 characters. */
		title: Option[String] = None,
	  /** The body of the note. */
		body: Option[Schema.Section] = None
	)
	
	case class Attachment(
	  /** The resource name; */
		name: Option[String] = None,
	  /** The MIME types (IANA media types) in which the attachment is available. */
		mimeType: Option[List[String]] = None
	)
	
	object Permission {
		enum RoleEnum extends Enum[RoleEnum] { case ROLE_UNSPECIFIED, OWNER, WRITER }
	}
	case class Permission(
	  /** Output only. The resource name. */
		name: Option[String] = None,
	  /** The role granted by this permission. The role determines the entity’s ability to read, write, and share notes. */
		role: Option[Schema.Permission.RoleEnum] = None,
	  /** The email associated with the member. If set on create, the `email` field in the `User` or `Group` message must either be empty or match this field. On read, may be unset if the member does not have an associated email. */
		email: Option[String] = None,
	  /** Output only. The user to whom this role applies. */
		user: Option[Schema.User] = None,
	  /** Output only. The group to which this role applies. */
		group: Option[Schema.Group] = None,
	  /** Output only. The Google Family to which this role applies. */
		family: Option[Schema.Family] = None,
	  /** Output only. Whether this member has been deleted. If the member is recovered, this value is set to false and the recovered member retains the role on the note. */
		deleted: Option[Boolean] = None
	)
	
	case class User(
	  /** The user's email. */
		email: Option[String] = None
	)
	
	case class Group(
	  /** The group email. */
		email: Option[String] = None
	)
	
	case class Family(
	
	)
	
	case class Section(
	  /** Used if this section's content is a block of text. The length of the text content must be less than 20,000 characters. */
		text: Option[Schema.TextContent] = None,
	  /** Used if this section's content is a list. */
		list: Option[Schema.ListContent] = None
	)
	
	case class TextContent(
	  /** The text of the note. The limits on this vary with the specific field using this type. */
		text: Option[String] = None
	)
	
	case class ListContent(
	  /** The items in the list. The number of items must be less than 1,000. */
		listItems: Option[List[Schema.ListItem]] = None
	)
	
	case class ListItem(
	  /** If set, list of list items nested under this list item. Only one level of nesting is allowed. */
		childListItems: Option[List[Schema.ListItem]] = None,
	  /** The text of this item. Length must be less than 1,000 characters. */
		text: Option[Schema.TextContent] = None,
	  /** Whether this item has been checked off or not. */
		checked: Option[Boolean] = None
	)
	
	case class ListNotesResponse(
	  /** A page of notes. */
		notes: Option[List[Schema.Note]] = None,
	  /** Next page's `page_token` field. */
		nextPageToken: Option[String] = None
	)
	
	case class Empty(
	
	)
	
	case class BatchCreatePermissionsRequest(
	  /** The request message specifying the resources to create. */
		requests: Option[List[Schema.CreatePermissionRequest]] = None
	)
	
	case class CreatePermissionRequest(
	  /** Required. The parent note where this permission will be created. Format: `notes/{note}` */
		parent: Option[String] = None,
	  /** Required. The permission to create. One of Permission.email, User.email or Group.email must be supplied. */
		permission: Option[Schema.Permission] = None
	)
	
	case class BatchCreatePermissionsResponse(
	  /** Permissions created. */
		permissions: Option[List[Schema.Permission]] = None
	)
	
	case class BatchDeletePermissionsRequest(
	  /** Required. The names of the permissions to delete. Format: `notes/{note}/permissions/{permission}` */
		names: Option[List[String]] = None
	)
}
