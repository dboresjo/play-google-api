package com.boresjo.play.api.google.drive

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	case class ListOperationsResponse(
	  /** A list of operations that matches the specified filter in the request. */
		operations: Option[List[Schema.Operation]] = None,
	  /** The standard List next-page token. */
		nextPageToken: Option[String] = None
	)
	
	case class Operation(
	  /** The server-assigned name, which is only unique within the same service that originally returns it. If you use the default HTTP mapping, the `name` should be a resource name ending with `operations/{unique_id}`. */
		name: Option[String] = None,
	  /** Service-specific metadata associated with the operation. It typically contains progress information and common metadata such as create time. Some services might not provide such metadata. Any method that returns a long-running operation should document the metadata type, if any. */
		metadata: Option[Map[String, JsValue]] = None,
	  /** If the value is `false`, it means the operation is still in progress. If `true`, the operation is completed, and either `error` or `response` is available. */
		done: Option[Boolean] = None,
	  /** The error result of the operation in case of failure or cancellation. */
		error: Option[Schema.Status] = None,
	  /** The normal, successful response of the operation. If the original method returns no data on success, such as `Delete`, the response is `google.protobuf.Empty`. If the original method is standard `Get`/`Create`/`Update`, the response should be the resource. For other methods, the response should have the type `XxxResponse`, where `Xxx` is the original method name. For example, if the original method name is `TakeSnapshot()`, the inferred response type is `TakeSnapshotResponse`. */
		response: Option[Map[String, JsValue]] = None
	)
	
	case class Status(
	  /** The status code, which should be an enum value of google.rpc.Code. */
		code: Option[Int] = None,
	  /** A developer-facing error message, which should be in English. Any user-facing error message should be localized and sent in the google.rpc.Status.details field, or localized by the client. */
		message: Option[String] = None,
	  /** A list of messages that carry the error details. There is a common set of message types for APIs to use. */
		details: Option[List[Map[String, JsValue]]] = None
	)
	
	object About {
		case class StorageQuotaItem(
		  /** The usage limit, if applicable. This will not be present if the user has unlimited storage. For users that are part of an organization with pooled storage, this is the limit for the organization, rather than the individual user. */
			limit: Option[String] = None,
		  /** The usage by all files in Google Drive. */
			usageInDrive: Option[String] = None,
		  /** The usage by trashed files in Google Drive. */
			usageInDriveTrash: Option[String] = None,
		  /** The total usage across all services. For users that are part of an organization with pooled storage, this is the usage across all services for the organization, rather than the individual user. */
			usage: Option[String] = None
		)
		
		case class DriveThemesItem(
		  /** The ID of the theme. */
			id: Option[String] = None,
		  /** A link to this theme's background image. */
			backgroundImageLink: Option[String] = None,
		  /** The color of this theme as an RGB hex string. */
			colorRgb: Option[String] = None
		)
		
		case class TeamDriveThemesItem(
		  /** Deprecated: Use `driveThemes/id` instead. */
			id: Option[String] = None,
		  /** Deprecated: Use `driveThemes/backgroundImageLink` instead. */
			backgroundImageLink: Option[String] = None,
		  /** Deprecated: Use `driveThemes/colorRgb` instead. */
			colorRgb: Option[String] = None
		)
	}
	case class About(
	  /** Identifies what kind of resource this is. Value: the fixed string `"drive#about"`. */
		kind: Option[String] = Some("""drive#about"""),
	  /** The user's storage quota limits and usage. For users that are part of an organization with pooled storage, information about the limit and usage across all services is for the organization, rather than the individual user. All fields are measured in bytes. */
		storageQuota: Option[Schema.About.StorageQuotaItem] = None,
	  /** A list of themes that are supported for shared drives. */
		driveThemes: Option[List[Schema.About.DriveThemesItem]] = None,
	  /** Whether the user can create shared drives. */
		canCreateDrives: Option[Boolean] = None,
	  /** A map of source MIME type to possible targets for all supported imports. */
		importFormats: Option[Map[String, List[String]]] = None,
	  /** A map of source MIME type to possible targets for all supported exports. */
		exportFormats: Option[Map[String, List[String]]] = None,
	  /** Whether the user has installed the requesting app. */
		appInstalled: Option[Boolean] = None,
	  /** The authenticated user. */
		user: Option[Schema.User] = None,
	  /** The currently supported folder colors as RGB hex strings. */
		folderColorPalette: Option[List[String]] = None,
	  /** A map of maximum import sizes by MIME type, in bytes. */
		maxImportSizes: Option[Map[String, String]] = None,
	  /** The maximum upload size in bytes. */
		maxUploadSize: Option[String] = None,
	  /** Deprecated: Use `driveThemes` instead. */
		teamDriveThemes: Option[List[Schema.About.TeamDriveThemesItem]] = None,
	  /** Deprecated: Use `canCreateDrives` instead. */
		canCreateTeamDrives: Option[Boolean] = None
	)
	
	case class User(
	  /** Output only. A plain text displayable name for this user. */
		displayName: Option[String] = None,
	  /** Output only. Identifies what kind of resource this is. Value: the fixed string `"drive#user"`. */
		kind: Option[String] = Some("""drive#user"""),
	  /** Output only. Whether this user is the requesting user. */
		me: Option[Boolean] = None,
	  /** Output only. The user's ID as visible in Permission resources. */
		permissionId: Option[String] = None,
	  /** Output only. The email address of the user. This may not be present in certain contexts if the user has not made their email address visible to the requester. */
		emailAddress: Option[String] = None,
	  /** Output only. A link to the user's profile photo, if available. */
		photoLink: Option[String] = None
	)
	
	case class App(
	  /** The name of the app. */
		name: Option[String] = None,
	  /** The type of object this app creates such as a Chart. If empty, the app name should be used instead. */
		objectType: Option[String] = None,
	  /** Whether this app supports creating objects. */
		supportsCreate: Option[Boolean] = None,
	  /** A link to the product listing for this app. */
		productUrl: Option[String] = None,
	  /** The list of primary MIME types. */
		primaryMimeTypes: Option[List[String]] = None,
	  /** The list of secondary MIME types. */
		secondaryMimeTypes: Option[List[String]] = None,
	  /** The list of primary file extensions. */
		primaryFileExtensions: Option[List[String]] = None,
	  /** The list of secondary file extensions. */
		secondaryFileExtensions: Option[List[String]] = None,
	  /** The ID of the app. */
		id: Option[String] = None,
	  /** Whether this app supports importing from Google Docs. */
		supportsImport: Option[Boolean] = None,
	  /** Whether the app is installed. */
		installed: Option[Boolean] = None,
	  /** Whether the app is authorized to access data on the user's Drive. */
		authorized: Option[Boolean] = None,
	  /** The various icons for the app. */
		icons: Option[List[Schema.AppIcons]] = None,
	  /** Whether the app is selected as the default handler for the types it supports. */
		useByDefault: Option[Boolean] = None,
	  /** Output only. Identifies what kind of resource this is. Value: the fixed string "drive#app". */
		kind: Option[String] = Some("""drive#app"""),
	  /** A short description of the app. */
		shortDescription: Option[String] = None,
	  /** A long description of the app. */
		longDescription: Option[String] = None,
	  /** Whether this app supports opening more than one file. */
		supportsMultiOpen: Option[Boolean] = None,
	  /** The ID of the product listing for this app. */
		productId: Option[String] = None,
	  /** The template URL for opening files with this app. The template contains {ids} or {exportIds} to be replaced by the actual file IDs. For more information, see Open Files for the full documentation. */
		openUrlTemplate: Option[String] = None,
	  /** The URL to create a file with this app. */
		createUrl: Option[String] = None,
	  /** The template URL to create a file with this app in a given folder. The template contains the {folderId} to be replaced by the folder ID house the new file. */
		createInFolderTemplate: Option[String] = None,
	  /** Whether this app supports creating files when offline. */
		supportsOfflineCreate: Option[Boolean] = None,
	  /** Whether the app has Drive-wide scope. An app with Drive-wide scope can access all files in the user's Drive. */
		hasDriveWideScope: Option[Boolean] = None
	)
	
	case class AppIcons(
	  /** Size of the icon. Represented as the maximum of the width and height. */
		size: Option[Int] = None,
	  /** Category of the icon. Allowed values are: &#42; `application` - The icon for the application. &#42; `document` - The icon for a file associated with the app. &#42; `documentShared` - The icon for a shared file associated with the app. */
		category: Option[String] = None,
	  /** URL for the icon. */
		iconUrl: Option[String] = None
	)
	
	case class AppList(
	  /** The list of app IDs that the user has specified to use by default. The list is in reverse-priority order (lowest to highest). */
		defaultAppIds: Option[List[String]] = None,
	  /** Output only. Identifies what kind of resource this is. Value: the fixed string "drive#appList". */
		kind: Option[String] = Some("""drive#appList"""),
	  /** A link back to this list. */
		selfLink: Option[String] = None,
	  /** The list of apps. */
		items: Option[List[Schema.App]] = None
	)
	
	case class StartPageToken(
	  /** The starting page token for listing future changes. The page token doesn't expire. */
		startPageToken: Option[String] = None,
	  /** Identifies what kind of resource this is. Value: the fixed string `"drive#startPageToken"`. */
		kind: Option[String] = Some("""drive#startPageToken""")
	)
	
	case class ChangeList(
	  /** Identifies what kind of resource this is. Value: the fixed string `"drive#changeList"`. */
		kind: Option[String] = Some("""drive#changeList"""),
	  /** The page token for the next page of changes. This will be absent if the end of the changes list has been reached. The page token doesn't expire. */
		nextPageToken: Option[String] = None,
	  /** The starting page token for future changes. This will be present only if the end of the current changes list has been reached. The page token doesn't expire. */
		newStartPageToken: Option[String] = None,
	  /** The list of changes. If nextPageToken is populated, then this list may be incomplete and an additional page of results should be fetched. */
		changes: Option[List[Schema.Change]] = None
	)
	
	case class Change(
	  /** Identifies what kind of resource this is. Value: the fixed string `"drive#change"`. */
		kind: Option[String] = Some("""drive#change"""),
	  /** Whether the file or shared drive has been removed from this list of changes, for example by deletion or loss of access. */
		removed: Option[Boolean] = None,
	  /** The updated state of the file. Present if the type is file and the file has not been removed from this list of changes. */
		file: Option[Schema.File] = None,
	  /** The ID of the file which has changed. */
		fileId: Option[String] = None,
	  /** The time of this change (RFC 3339 date-time). */
		time: Option[String] = None,
	  /** The ID of the shared drive associated with this change. */
		driveId: Option[String] = None,
	  /** Deprecated: Use `changeType` instead. */
		`type`: Option[String] = None,
	  /** Deprecated: Use `driveId` instead. */
		teamDriveId: Option[String] = None,
	  /** Deprecated: Use `drive` instead. */
		teamDrive: Option[Schema.TeamDrive] = None,
	  /** The type of the change. Possible values are `file` and `drive`. */
		changeType: Option[String] = None,
	  /** The updated state of the shared drive. Present if the changeType is drive, the user is still a member of the shared drive, and the shared drive has not been deleted. */
		drive: Option[Schema.Drive] = None
	)
	
	object File {
		object ContentHintsItem {
			case class ThumbnailItem(
			  /** The thumbnail data encoded with URL-safe Base64 (RFC 4648 section 5). */
				image: Option[String] = None,
			  /** The MIME type of the thumbnail. */
				mimeType: Option[String] = None
			)
		}
		case class ContentHintsItem(
		  /** Text to be indexed for the file to improve fullText queries. This is limited to 128KB in length and may contain HTML elements. */
			indexableText: Option[String] = None,
		  /** A thumbnail for the file. This will only be used if Google Drive cannot generate a standard thumbnail. */
			thumbnail: Option[Schema.File.ContentHintsItem.ThumbnailItem] = None
		)
		
		case class CapabilitiesItem(
		  /** Deprecated: Output only. */
			canChangeViewersCanCopyContent: Option[Boolean] = None,
		  /** Output only. Whether the current user can move children of this folder outside of the shared drive. This is false when the item is not a folder. Only populated for items in shared drives. */
			canMoveChildrenOutOfDrive: Option[Boolean] = None,
		  /** Output only. Whether the current user can read the shared drive to which this file belongs. Only populated for items in shared drives. */
			canReadDrive: Option[Boolean] = None,
		  /** Output only. Whether the current user can edit this file. Other factors may limit the type of changes a user can make to a file. For example, see `canChangeCopyRequiresWriterPermission` or `canModifyContent`. */
			canEdit: Option[Boolean] = None,
		  /** Output only. Whether the current user can copy this file. For an item in a shared drive, whether the current user can copy non-folder descendants of this item, or this item itself if it is not a folder. */
			canCopy: Option[Boolean] = None,
		  /** Output only. Whether the current user can comment on this file. */
			canComment: Option[Boolean] = None,
		  /** Output only. Whether the current user can add children to this folder. This is always false when the item is not a folder. */
			canAddChildren: Option[Boolean] = None,
		  /** Output only. Whether the current user can delete this file. */
			canDelete: Option[Boolean] = None,
		  /** Output only. Whether the current user can download this file. */
			canDownload: Option[Boolean] = None,
		  /** Output only. Whether the current user can list the children of this folder. This is always false when the item is not a folder. */
			canListChildren: Option[Boolean] = None,
		  /** Output only. Whether the current user can remove children from this folder. This is always false when the item is not a folder. For a folder in a shared drive, use `canDeleteChildren` or `canTrashChildren` instead. */
			canRemoveChildren: Option[Boolean] = None,
		  /** Output only. Whether the current user can rename this file. */
			canRename: Option[Boolean] = None,
		  /** Output only. Whether the current user can move this file to trash. */
			canTrash: Option[Boolean] = None,
		  /** Output only. Whether the current user can read the revisions resource of this file. For a shared drive item, whether revisions of non-folder descendants of this item, or this item itself if it is not a folder, can be read. */
			canReadRevisions: Option[Boolean] = None,
		  /** Deprecated: Output only. Use `canReadDrive` instead. */
			canReadTeamDrive: Option[Boolean] = None,
		  /** Deprecated: Output only. Use `canMoveItemWithinDrive` or `canMoveItemOutOfDrive` instead. */
			canMoveTeamDriveItem: Option[Boolean] = None,
		  /** Output only. Whether the current user can change the `copyRequiresWriterPermission` restriction of this file. */
			canChangeCopyRequiresWriterPermission: Option[Boolean] = None,
		  /** Deprecated: Output only. Use `canMoveItemOutOfDrive` instead. */
			canMoveItemIntoTeamDrive: Option[Boolean] = None,
		  /** Output only. Whether the current user can restore this file from trash. */
			canUntrash: Option[Boolean] = None,
		  /** Output only. Whether the current user can modify the content of this file. */
			canModifyContent: Option[Boolean] = None,
		  /** Deprecated: Output only. Use `canMoveItemWithinDrive` instead. */
			canMoveItemWithinTeamDrive: Option[Boolean] = None,
		  /** Deprecated: Output only. Use `canMoveItemOutOfDrive` instead. */
			canMoveItemOutOfTeamDrive: Option[Boolean] = None,
		  /** Output only. Whether the current user can delete children of this folder. This is false when the item is not a folder. Only populated for items in shared drives. */
			canDeleteChildren: Option[Boolean] = None,
		  /** Deprecated: Output only. Use `canMoveChildrenOutOfDrive` instead. */
			canMoveChildrenOutOfTeamDrive: Option[Boolean] = None,
		  /** Deprecated: Output only. Use `canMoveChildrenWithinDrive` instead. */
			canMoveChildrenWithinTeamDrive: Option[Boolean] = None,
		  /** Output only. Whether the current user can trash children of this folder. This is false when the item is not a folder. Only populated for items in shared drives. */
			canTrashChildren: Option[Boolean] = None,
		  /** Output only. Whether the current user can move this item outside of this drive by changing its parent. Note that a request to change the parent of the item may still fail depending on the new parent that is being added. */
			canMoveItemOutOfDrive: Option[Boolean] = None,
		  /** Output only. Whether the current user can add a parent for the item without removing an existing parent in the same request. Not populated for shared drive files. */
			canAddMyDriveParent: Option[Boolean] = None,
		  /** Output only. Whether the current user can remove a parent from the item without adding another parent in the same request. Not populated for shared drive files. */
			canRemoveMyDriveParent: Option[Boolean] = None,
		  /** Output only. Whether the current user can move this item within this drive. Note that a request to change the parent of the item may still fail depending on the new parent that is being added and the parent that is being removed. */
			canMoveItemWithinDrive: Option[Boolean] = None,
		  /** Output only. Whether the current user can modify the sharing settings for this file. */
			canShare: Option[Boolean] = None,
		  /** Output only. Whether the current user can move children of this folder within this drive. This is false when the item is not a folder. Note that a request to move the child may still fail depending on the current user's access to the child and to the destination folder. */
			canMoveChildrenWithinDrive: Option[Boolean] = None,
		  /** Deprecated: Output only. Use one of `canModifyEditorContentRestriction`, `canModifyOwnerContentRestriction` or `canRemoveContentRestriction`. */
			canModifyContentRestriction: Option[Boolean] = None,
		  /** Output only. Whether the current user can add a folder from another drive (different shared drive or My Drive) to this folder. This is false when the item is not a folder. Only populated for items in shared drives. */
			canAddFolderFromAnotherDrive: Option[Boolean] = None,
		  /** Output only. Whether the current user can change the securityUpdateEnabled field on link share metadata. */
			canChangeSecurityUpdateEnabled: Option[Boolean] = None,
		  /** Output only. Whether the current user is the pending owner of the file. Not populated for shared drive files. */
			canAcceptOwnership: Option[Boolean] = None,
		  /** Output only. Whether the current user can read the labels on the file. */
			canReadLabels: Option[Boolean] = None,
		  /** Output only. Whether the current user can modify the labels on the file. */
			canModifyLabels: Option[Boolean] = None,
		  /** Output only. Whether the current user can add or modify content restrictions on the file which are editor restricted. */
			canModifyEditorContentRestriction: Option[Boolean] = None,
		  /** Output only. Whether the current user can add or modify content restrictions which are owner restricted. */
			canModifyOwnerContentRestriction: Option[Boolean] = None,
		  /** Output only. Whether there is a content restriction on the file that can be removed by the current user. */
			canRemoveContentRestriction: Option[Boolean] = None
		)
		
		object ImageMediaMetadataItem {
			case class LocationItem(
			  /** Output only. The latitude stored in the image. */
				latitude: Option[BigDecimal] = None,
			  /** Output only. The longitude stored in the image. */
				longitude: Option[BigDecimal] = None,
			  /** Output only. The altitude stored in the image. */
				altitude: Option[BigDecimal] = None
			)
		}
		case class ImageMediaMetadataItem(
		  /** Output only. Whether a flash was used to create the photo. */
			flashUsed: Option[Boolean] = None,
		  /** Output only. The metering mode used to create the photo. */
			meteringMode: Option[String] = None,
		  /** Output only. The type of sensor used to create the photo. */
			sensor: Option[String] = None,
		  /** Output only. The exposure mode used to create the photo. */
			exposureMode: Option[String] = None,
		  /** Output only. The color space of the photo. */
			colorSpace: Option[String] = None,
		  /** Output only. The white balance mode used to create the photo. */
			whiteBalance: Option[String] = None,
		  /** Output only. The width of the image in pixels. */
			width: Option[Int] = None,
		  /** Output only. The height of the image in pixels. */
			height: Option[Int] = None,
		  /** Output only. Geographic location information stored in the image. */
			location: Option[Schema.File.ImageMediaMetadataItem.LocationItem] = None,
		  /** Output only. The number of clockwise 90 degree rotations applied from the image's original orientation. */
			rotation: Option[Int] = None,
		  /** Output only. The date and time the photo was taken (EXIF DateTime). */
			time: Option[String] = None,
		  /** Output only. The make of the camera used to create the photo. */
			cameraMake: Option[String] = None,
		  /** Output only. The model of the camera used to create the photo. */
			cameraModel: Option[String] = None,
		  /** Output only. The length of the exposure, in seconds. */
			exposureTime: Option[BigDecimal] = None,
		  /** Output only. The aperture used to create the photo (f-number). */
			aperture: Option[BigDecimal] = None,
		  /** Output only. The focal length used to create the photo, in millimeters. */
			focalLength: Option[BigDecimal] = None,
		  /** Output only. The ISO speed used to create the photo. */
			isoSpeed: Option[Int] = None,
		  /** Output only. The exposure bias of the photo (APEX value). */
			exposureBias: Option[BigDecimal] = None,
		  /** Output only. The smallest f-number of the lens at the focal length used to create the photo (APEX value). */
			maxApertureValue: Option[BigDecimal] = None,
		  /** Output only. The distance to the subject of the photo, in meters. */
			subjectDistance: Option[Int] = None,
		  /** Output only. The lens used to create the photo. */
			lens: Option[String] = None
		)
		
		case class VideoMediaMetadataItem(
		  /** Output only. The width of the video in pixels. */
			width: Option[Int] = None,
		  /** Output only. The height of the video in pixels. */
			height: Option[Int] = None,
		  /** Output only. The duration of the video in milliseconds. */
			durationMillis: Option[String] = None
		)
		
		case class ShortcutDetailsItem(
		  /** The ID of the file that this shortcut points to. Can only be set on `files.create` requests. */
			targetId: Option[String] = None,
		  /** Output only. The MIME type of the file that this shortcut points to. The value of this field is a snapshot of the target's MIME type, captured when the shortcut is created. */
			targetMimeType: Option[String] = None,
		  /** Output only. The ResourceKey for the target file. */
			targetResourceKey: Option[String] = None
		)
		
		case class LinkShareMetadataItem(
		  /** Output only. Whether the file is eligible for security update. */
			securityUpdateEligible: Option[Boolean] = None,
		  /** Output only. Whether the security update is enabled for this file. */
			securityUpdateEnabled: Option[Boolean] = None
		)
		
		case class LabelInfoItem(
		  /** Output only. The set of labels on the file as requested by the label IDs in the `includeLabels` parameter. By default, no labels are returned. */
			labels: Option[List[Schema.Label]] = None
		)
	}
	case class File(
	  /** Output only. Identifies what kind of resource this is. Value: the fixed string `"drive#file"`. */
		kind: Option[String] = Some("""drive#file"""),
	  /** Output only. ID of the shared drive the file resides in. Only populated for items in shared drives. */
		driveId: Option[String] = None,
	  /** Output only. The final component of `fullFileExtension`. This is only available for files with binary content in Google Drive. */
		fileExtension: Option[String] = None,
	  /** Whether the options to copy, print, or download this file, should be disabled for readers and commenters. */
		copyRequiresWriterPermission: Option[Boolean] = None,
	  /** Output only. The MD5 checksum for the content of the file. This is only applicable to files with binary content in Google Drive. */
		md5Checksum: Option[String] = None,
	  /** Additional information about the content of the file. These fields are never populated in responses. */
		contentHints: Option[Schema.File.ContentHintsItem] = None,
	  /** Whether users with only `writer` permission can modify the file's permissions. Not populated for items in shared drives. */
		writersCanShare: Option[Boolean] = None,
	  /** Output only. Whether the file has been viewed by this user. */
		viewedByMe: Option[Boolean] = None,
	  /** The MIME type of the file. Google Drive attempts to automatically detect an appropriate value from uploaded content, if no value is provided. The value cannot be changed unless a new revision is uploaded. If a file is created with a Google Doc MIME type, the uploaded content is imported, if possible. The supported import formats are published in the About resource. */
		mimeType: Option[String] = None,
	  /** Output only. Links for exporting Docs Editors files to specific formats. */
		exportLinks: Option[Map[String, String]] = None,
	  /** The ID of the parent folder containing the file. A file can only have one parent folder; specifying multiple parents isn't supported. If not specified as part of a create request, the file is placed directly in the user's My Drive folder. If not specified as part of a copy request, the file inherits any discoverable parent of the source file. Update requests must use the `addParents` and `removeParents` parameters to modify the parents list. */
		parents: Option[List[String]] = None,
	  /** Output only. A short-lived link to the file's thumbnail, if available. Typically lasts on the order of hours. Not intended for direct usage on web applications due to [Cross-Origin Resource Sharing (CORS)](https://developer.mozilla.org/en-US/docs/Web/HTTP/CORS) policies, consider using a proxy server. Only populated when the requesting app can access the file's content. If the file isn't shared publicly, the URL returned in `Files.thumbnailLink` must be fetched using a credentialed request. */
		thumbnailLink: Option[String] = None,
	  /** Output only. A static, unauthenticated link to the file's icon. */
		iconLink: Option[String] = None,
	  /** Output only. Whether the file has been shared. Not populated for items in shared drives. */
		shared: Option[Boolean] = None,
	  /** Output only. The last user to modify the file. This field is only populated when the last modification was performed by a signed-in user. */
		lastModifyingUser: Option[Schema.User] = None,
	  /** Output only. The owner of this file. Only certain legacy files may have more than one owner. This field isn't populated for items in shared drives. */
		owners: Option[List[Schema.User]] = None,
	  /** Output only. The ID of the file's head revision. This is currently only available for files with binary content in Google Drive. */
		headRevisionId: Option[String] = None,
	  /** Output only. The user who shared the file with the requesting user, if applicable. */
		sharingUser: Option[Schema.User] = None,
	  /** Output only. A link for opening the file in a relevant Google editor or viewer in a browser. */
		webViewLink: Option[String] = None,
	  /** Output only. A link for downloading the content of the file in a browser. This is only available for files with binary content in Google Drive. */
		webContentLink: Option[String] = None,
	  /** Output only. Size in bytes of blobs and first party editor files. Won't be populated for files that have no size, like shortcuts and folders. */
		size: Option[String] = None,
	  /** Deprecated: Use `copyRequiresWriterPermission` instead. */
		viewersCanCopyContent: Option[Boolean] = None,
	  /** Output only. The full list of permissions for the file. This is only available if the requesting user can share the file. Not populated for items in shared drives. */
		permissions: Option[List[Schema.Permission]] = None,
	  /** Output only. Whether this file has a thumbnail. This does not indicate whether the requesting app has access to the thumbnail. To check access, look for the presence of the thumbnailLink field. */
		hasThumbnail: Option[Boolean] = None,
	  /** Output only. The list of spaces which contain the file. The currently supported values are 'drive', 'appDataFolder' and 'photos'. */
		spaces: Option[List[String]] = None,
	  /** The color for a folder or a shortcut to a folder as an RGB hex string. The supported colors are published in the `folderColorPalette` field of the About resource. If an unsupported color is specified, the closest color in the palette is used instead. */
		folderColorRgb: Option[String] = None,
	  /** The ID of the file. */
		id: Option[String] = None,
	  /** The name of the file. This is not necessarily unique within a folder. Note that for immutable items such as the top level folders of shared drives, My Drive root folder, and Application Data folder the name is constant. */
		name: Option[String] = None,
	  /** A short description of the file. */
		description: Option[String] = None,
	  /** Whether the user has starred the file. */
		starred: Option[Boolean] = None,
	  /** Whether the file has been trashed, either explicitly or from a trashed parent folder. Only the owner may trash a file, and other users cannot see files in the owner's trash. */
		trashed: Option[Boolean] = None,
	  /** Output only. Whether the file has been explicitly trashed, as opposed to recursively trashed from a parent folder. */
		explicitlyTrashed: Option[Boolean] = None,
	  /** The time at which the file was created (RFC 3339 date-time). */
		createdTime: Option[String] = None,
	  /** he last time the file was modified by anyone (RFC 3339 date-time). Note that setting modifiedTime will also update modifiedByMeTime for the user. */
		modifiedTime: Option[String] = None,
	  /** The last time the file was modified by the user (RFC 3339 date-time). */
		modifiedByMeTime: Option[String] = None,
	  /** The last time the file was viewed by the user (RFC 3339 date-time). */
		viewedByMeTime: Option[String] = None,
	  /** The time at which the file was shared with the user, if applicable (RFC 3339 date-time). */
		sharedWithMeTime: Option[String] = None,
	  /** Output only. The number of storage quota bytes used by the file. This includes the head revision as well as previous revisions with `keepForever` enabled. */
		quotaBytesUsed: Option[String] = None,
	  /** Output only. A monotonically increasing version number for the file. This reflects every change made to the file on the server, even those not visible to the user. */
		version: Option[String] = None,
	  /** The original filename of the uploaded content if available, or else the original value of the `name` field. This is only available for files with binary content in Google Drive. */
		originalFilename: Option[String] = None,
	  /** Output only. Whether the user owns the file. Not populated for items in shared drives. */
		ownedByMe: Option[Boolean] = None,
	  /** Output only. The full file extension extracted from the `name` field. May contain multiple concatenated extensions, such as "tar.gz". This is only available for files with binary content in Google Drive. This is automatically updated when the `name` field changes, however it is not cleared if the new name does not contain a valid extension. */
		fullFileExtension: Option[String] = None,
	  /** A collection of arbitrary key-value pairs which are visible to all apps.
	Entries with null values are cleared in update and copy requests. */
		properties: Option[Map[String, String]] = None,
	  /** A collection of arbitrary key-value pairs which are private to the requesting app.
	Entries with null values are cleared in update and copy requests. These properties can only be retrieved using an authenticated request. An authenticated request uses an access token obtained with a OAuth 2 client ID. You cannot use an API key to retrieve private properties. */
		appProperties: Option[Map[String, String]] = None,
	  /** Output only. Whether the file was created or opened by the requesting app. */
		isAppAuthorized: Option[Boolean] = None,
	  /** Deprecated: Output only. Use `driveId` instead. */
		teamDriveId: Option[String] = None,
	  /** Output only. Capabilities the current user has on this file. Each capability corresponds to a fine-grained action that a user may take. */
		capabilities: Option[Schema.File.CapabilitiesItem] = None,
	  /** Output only. Whether there are permissions directly on this file. This field is only populated for items in shared drives. */
		hasAugmentedPermissions: Option[Boolean] = None,
	  /** Output only. If the file has been explicitly trashed, the user who trashed it. Only populated for items in shared drives. */
		trashingUser: Option[Schema.User] = None,
	  /** Output only. The thumbnail version for use in thumbnail cache invalidation. */
		thumbnailVersion: Option[String] = None,
	  /** The time that the item was trashed (RFC 3339 date-time). Only populated for items in shared drives. */
		trashedTime: Option[String] = None,
	  /** Output only. Whether the file has been modified by this user. */
		modifiedByMe: Option[Boolean] = None,
	  /** Output only. List of permission IDs for users with access to this file. */
		permissionIds: Option[List[String]] = None,
	  /** Output only. Additional metadata about image media, if available. */
		imageMediaMetadata: Option[Schema.File.ImageMediaMetadataItem] = None,
	  /** Output only. Additional metadata about video media. This may not be available immediately upon upload. */
		videoMediaMetadata: Option[Schema.File.VideoMediaMetadataItem] = None,
	  /** Shortcut file details. Only populated for shortcut files, which have the mimeType field set to `application/vnd.google-apps.shortcut`. Can only be set on `files.create` requests. */
		shortcutDetails: Option[Schema.File.ShortcutDetailsItem] = None,
	  /** Restrictions for accessing the content of the file. Only populated if such a restriction exists. */
		contentRestrictions: Option[List[Schema.ContentRestriction]] = None,
	  /** Output only. A key needed to access the item via a shared link. */
		resourceKey: Option[String] = None,
	  /** Contains details about the link URLs that clients are using to refer to this item. */
		linkShareMetadata: Option[Schema.File.LinkShareMetadataItem] = None,
	  /** Output only. An overview of the labels on the file. */
		labelInfo: Option[Schema.File.LabelInfoItem] = None,
	  /** Output only. The SHA1 checksum associated with this file, if available. This field is only populated for files with content stored in Google Drive; it is not populated for Docs Editors or shortcut files. */
		sha1Checksum: Option[String] = None,
	  /** Output only. The SHA256 checksum associated with this file, if available. This field is only populated for files with content stored in Google Drive; it is not populated for Docs Editors or shortcut files. */
		sha256Checksum: Option[String] = None
	)
	
	object Permission {
		case class PermissionDetailsItem(
		  /** Output only. The permission type for this user. While new values may be added in future, the following are currently possible: &#42; `file` &#42; `member` */
			permissionType: Option[String] = None,
		  /** Output only. The ID of the item from which this permission is inherited. This is an output-only field. */
			inheritedFrom: Option[String] = None,
		  /** Output only. The primary role for this user. While new values may be added in the future, the following are currently possible: &#42; `organizer` &#42; `fileOrganizer` &#42; `writer` &#42; `commenter` &#42; `reader` */
			role: Option[String] = None,
		  /** Output only. Whether this permission is inherited. This field is always populated. This is an output-only field. */
			inherited: Option[Boolean] = None
		)
		
		case class TeamDrivePermissionDetailsItem(
		  /** Deprecated: Output only. Use `permissionDetails/permissionType` instead. */
			teamDrivePermissionType: Option[String] = None,
		  /** Deprecated: Output only. Use `permissionDetails/inheritedFrom` instead. */
			inheritedFrom: Option[String] = None,
		  /** Deprecated: Output only. Use `permissionDetails/role` instead. */
			role: Option[String] = None,
		  /** Deprecated: Output only. Use `permissionDetails/inherited` instead. */
			inherited: Option[Boolean] = None
		)
	}
	case class Permission(
	  /** Output only. The ID of this permission. This is a unique identifier for the grantee, and is published in User resources as `permissionId`. IDs should be treated as opaque values. */
		id: Option[String] = None,
	  /** Output only. The "pretty" name of the value of the permission. The following is a list of examples for each type of permission: &#42; `user` - User's full name, as defined for their Google account, such as "Joe Smith." &#42; `group` - Name of the Google Group, such as "The Company Administrators." &#42; `domain` - String domain name, such as "thecompany.com." &#42; `anyone` - No `displayName` is present. */
		displayName: Option[String] = None,
	  /** The type of the grantee. Valid values are: &#42; `user` &#42; `group` &#42; `domain` &#42; `anyone` When creating a permission, if `type` is `user` or `group`, you must provide an `emailAddress` for the user or group. When `type` is `domain`, you must provide a `domain`. There isn't extra information required for an `anyone` type. */
		`type`: Option[String] = None,
	  /** Output only. Identifies what kind of resource this is. Value: the fixed string `"drive#permission"`. */
		kind: Option[String] = Some("""drive#permission"""),
	  /** Output only. Details of whether the permissions on this shared drive item are inherited or directly on this item. This is an output-only field which is present only for shared drive items. */
		permissionDetails: Option[List[Schema.Permission.PermissionDetailsItem]] = None,
	  /** Output only. A link to the user's profile photo, if available. */
		photoLink: Option[String] = None,
	  /** The email address of the user or group to which this permission refers. */
		emailAddress: Option[String] = None,
	  /** The role granted by this permission. While new values may be supported in the future, the following are currently allowed: &#42; `owner` &#42; `organizer` &#42; `fileOrganizer` &#42; `writer` &#42; `commenter` &#42; `reader` */
		role: Option[String] = None,
	  /** Whether the permission allows the file to be discovered through search. This is only applicable for permissions of type `domain` or `anyone`. */
		allowFileDiscovery: Option[Boolean] = None,
	  /** The domain to which this permission refers. */
		domain: Option[String] = None,
	  /** The time at which this permission will expire (RFC 3339 date-time). Expiration times have the following restrictions: - They can only be set on user and group permissions - The time must be in the future - The time cannot be more than a year in the future */
		expirationTime: Option[String] = None,
	  /** Output only. Deprecated: Output only. Use `permissionDetails` instead. */
		teamDrivePermissionDetails: Option[List[Schema.Permission.TeamDrivePermissionDetailsItem]] = None,
	  /** Output only. Whether the account associated with this permission has been deleted. This field only pertains to user and group permissions. */
		deleted: Option[Boolean] = None,
	  /** Indicates the view for this permission. Only populated for permissions that belong to a view. 'published' is the only supported value. */
		view: Option[String] = None,
	  /** Whether the account associated with this permission is a pending owner. Only populated for `user` type permissions for files that are not in a shared drive. */
		pendingOwner: Option[Boolean] = None
	)
	
	case class ContentRestriction(
	  /** Whether the content of the file is read-only. If a file is read-only, a new revision of the file may not be added, comments may not be added or modified, and the title of the file may not be modified. */
		readOnly: Option[Boolean] = None,
	  /** Reason for why the content of the file is restricted. This is only mutable on requests that also set `readOnly=true`. */
		reason: Option[String] = None,
	  /** Output only. The type of the content restriction. Currently the only possible value is `globalContentRestriction`. */
		`type`: Option[String] = None,
	  /** Output only. The user who set the content restriction. Only populated if `readOnly` is true. */
		restrictingUser: Option[Schema.User] = None,
	  /** The time at which the content restriction was set (formatted RFC 3339 timestamp). Only populated if readOnly is true. */
		restrictionTime: Option[String] = None,
	  /** Whether the content restriction can only be modified or removed by a user who owns the file. For files in shared drives, any user with `organizer` capabilities can modify or remove this content restriction. */
		ownerRestricted: Option[Boolean] = None,
	  /** Output only. Whether the content restriction was applied by the system, for example due to an esignature. Users cannot modify or remove system restricted content restrictions. */
		systemRestricted: Option[Boolean] = None
	)
	
	case class Label(
	  /** The ID of the label. */
		id: Option[String] = None,
	  /** The revision ID of the label. */
		revisionId: Option[String] = None,
	  /** This is always drive#label */
		kind: Option[String] = None,
	  /** A map of the fields on the label, keyed by the field's ID. */
		fields: Option[Map[String, Schema.LabelField]] = None
	)
	
	case class LabelField(
	  /** This is always drive#labelField. */
		kind: Option[String] = None,
	  /** The identifier of this label field. */
		id: Option[String] = None,
	  /** The field type. While new values may be supported in the future, the following are currently allowed: &#42; `dateString` &#42; `integer` &#42; `selection` &#42; `text` &#42; `user` */
		valueType: Option[String] = None,
	  /** Only present if valueType is dateString. RFC 3339 formatted date: YYYY-MM-DD. */
		dateString: Option[List[String]] = None,
	  /** Only present if `valueType` is `integer`. */
		integer: Option[List[String]] = None,
	  /** Only present if `valueType` is `selection` */
		selection: Option[List[String]] = None,
	  /** Only present if `valueType` is `text`. */
		text: Option[List[String]] = None,
	  /** Only present if `valueType` is `user`. */
		user: Option[List[Schema.User]] = None
	)
	
	object TeamDrive {
		case class CapabilitiesItem(
		  /** Whether the current user can add children to folders in this Team Drive. */
			canAddChildren: Option[Boolean] = None,
		  /** Whether the current user can comment on files in this Team Drive. */
			canComment: Option[Boolean] = None,
		  /** Whether the current user can copy files in this Team Drive. */
			canCopy: Option[Boolean] = None,
		  /** Whether the current user can delete this Team Drive. Attempting to delete the Team Drive may still fail if there are untrashed items inside the Team Drive. */
			canDeleteTeamDrive: Option[Boolean] = None,
		  /** Whether the current user can download files in this Team Drive. */
			canDownload: Option[Boolean] = None,
		  /** Whether the current user can edit files in this Team Drive */
			canEdit: Option[Boolean] = None,
		  /** Whether the current user can list the children of folders in this Team Drive. */
			canListChildren: Option[Boolean] = None,
		  /** Whether the current user can add members to this Team Drive or remove them or change their role. */
			canManageMembers: Option[Boolean] = None,
		  /** Whether the current user can read the revisions resource of files in this Team Drive. */
			canReadRevisions: Option[Boolean] = None,
		  /** Deprecated: Use `canDeleteChildren` or `canTrashChildren` instead. */
			canRemoveChildren: Option[Boolean] = None,
		  /** Whether the current user can rename files or folders in this Team Drive. */
			canRename: Option[Boolean] = None,
		  /** Whether the current user can rename this Team Drive. */
			canRenameTeamDrive: Option[Boolean] = None,
		  /** Whether the current user can change the background of this Team Drive. */
			canChangeTeamDriveBackground: Option[Boolean] = None,
		  /** Whether the current user can share files or folders in this Team Drive. */
			canShare: Option[Boolean] = None,
		  /** Whether the current user can change the `copyRequiresWriterPermission` restriction of this Team Drive. */
			canChangeCopyRequiresWriterPermissionRestriction: Option[Boolean] = None,
		  /** Whether the current user can change the `domainUsersOnly` restriction of this Team Drive. */
			canChangeDomainUsersOnlyRestriction: Option[Boolean] = None,
		  /** Whether the current user can change the `sharingFoldersRequiresOrganizerPermission` restriction of this Team Drive. */
			canChangeSharingFoldersRequiresOrganizerPermissionRestriction: Option[Boolean] = None,
		  /** Whether the current user can change the `teamMembersOnly` restriction of this Team Drive. */
			canChangeTeamMembersOnlyRestriction: Option[Boolean] = None,
		  /** Whether the current user can delete children from folders in this Team Drive. */
			canDeleteChildren: Option[Boolean] = None,
		  /** Whether the current user can trash children from folders in this Team Drive. */
			canTrashChildren: Option[Boolean] = None,
		  /** Whether the current user can reset the Team Drive restrictions to defaults. */
			canResetTeamDriveRestrictions: Option[Boolean] = None
		)
		
		case class BackgroundImageFileItem(
		  /** The ID of an image file in Drive to use for the background image. */
			id: Option[String] = None,
		  /** The X coordinate of the upper left corner of the cropping area in the background image. This is a value in the closed range of 0 to 1. This value represents the horizontal distance from the left side of the entire image to the left side of the cropping area divided by the width of the entire image. */
			xCoordinate: Option[BigDecimal] = None,
		  /** The Y coordinate of the upper left corner of the cropping area in the background image. This is a value in the closed range of 0 to 1. This value represents the vertical distance from the top side of the entire image to the top side of the cropping area divided by the height of the entire image. */
			yCoordinate: Option[BigDecimal] = None,
		  /** The width of the cropped image in the closed range of 0 to 1. This value represents the width of the cropped image divided by the width of the entire image. The height is computed by applying a width to height aspect ratio of 80 to 9. The resulting image must be at least 1280 pixels wide and 144 pixels high. */
			width: Option[BigDecimal] = None
		)
		
		case class RestrictionsItem(
		  /** Whether the options to copy, print, or download files inside this Team Drive, should be disabled for readers and commenters. When this restriction is set to `true`, it will override the similarly named field to `true` for any file inside this Team Drive. */
			copyRequiresWriterPermission: Option[Boolean] = None,
		  /** Whether access to this Team Drive and items inside this Team Drive is restricted to users of the domain to which this Team Drive belongs. This restriction may be overridden by other sharing policies controlled outside of this Team Drive. */
			domainUsersOnly: Option[Boolean] = None,
		  /** Whether access to items inside this Team Drive is restricted to members of this Team Drive. */
			teamMembersOnly: Option[Boolean] = None,
		  /** Whether administrative privileges on this Team Drive are required to modify restrictions. */
			adminManagedRestrictions: Option[Boolean] = None,
		  /** If true, only users with the organizer role can share folders. If false, users with either the organizer role or the file organizer role can share folders. */
			sharingFoldersRequiresOrganizerPermission: Option[Boolean] = None
		)
	}
	case class TeamDrive(
	  /** The ID of this Team Drive which is also the ID of the top level folder of this Team Drive. */
		id: Option[String] = None,
	  /** The name of this Team Drive. */
		name: Option[String] = None,
	  /** The color of this Team Drive as an RGB hex string. It can only be set on a `drive.teamdrives.update` request that does not set `themeId`. */
		colorRgb: Option[String] = None,
	  /** Identifies what kind of resource this is. Value: the fixed string `"drive#teamDrive"`. */
		kind: Option[String] = Some("""drive#teamDrive"""),
	  /** A short-lived link to this Team Drive's background image. */
		backgroundImageLink: Option[String] = None,
	  /** Capabilities the current user has on this Team Drive. */
		capabilities: Option[Schema.TeamDrive.CapabilitiesItem] = None,
	  /** The ID of the theme from which the background image and color will be set. The set of possible `teamDriveThemes` can be retrieved from a `drive.about.get` response. When not specified on a `drive.teamdrives.create` request, a random theme is chosen from which the background image and color are set. This is a write-only field; it can only be set on requests that don't set `colorRgb` or `backgroundImageFile`. */
		themeId: Option[String] = None,
	  /** An image file and cropping parameters from which a background image for this Team Drive is set. This is a write only field; it can only be set on `drive.teamdrives.update` requests that don't set `themeId`. When specified, all fields of the `backgroundImageFile` must be set. */
		backgroundImageFile: Option[Schema.TeamDrive.BackgroundImageFileItem] = None,
	  /** The time at which the Team Drive was created (RFC 3339 date-time). */
		createdTime: Option[String] = None,
	  /** A set of restrictions that apply to this Team Drive or items inside this Team Drive. */
		restrictions: Option[Schema.TeamDrive.RestrictionsItem] = None,
	  /** The organizational unit of this shared drive. This field is only populated on `drives.list` responses when the `useDomainAdminAccess` parameter is set to `true`. */
		orgUnitId: Option[String] = None
	)
	
	object Drive {
		case class CapabilitiesItem(
		  /** Output only. Whether the current user can add children to folders in this shared drive. */
			canAddChildren: Option[Boolean] = None,
		  /** Output only. Whether the current user can comment on files in this shared drive. */
			canComment: Option[Boolean] = None,
		  /** Output only. Whether the current user can copy files in this shared drive. */
			canCopy: Option[Boolean] = None,
		  /** Output only. Whether the current user can delete this shared drive. Attempting to delete the shared drive may still fail if there are untrashed items inside the shared drive. */
			canDeleteDrive: Option[Boolean] = None,
		  /** Output only. Whether the current user can download files in this shared drive. */
			canDownload: Option[Boolean] = None,
		  /** Output only. Whether the current user can edit files in this shared drive */
			canEdit: Option[Boolean] = None,
		  /** Output only. Whether the current user can list the children of folders in this shared drive. */
			canListChildren: Option[Boolean] = None,
		  /** Output only. Whether the current user can add members to this shared drive or remove them or change their role. */
			canManageMembers: Option[Boolean] = None,
		  /** Output only. Whether the current user can read the revisions resource of files in this shared drive. */
			canReadRevisions: Option[Boolean] = None,
		  /** Output only. Whether the current user can rename files or folders in this shared drive. */
			canRename: Option[Boolean] = None,
		  /** Output only. Whether the current user can rename this shared drive. */
			canRenameDrive: Option[Boolean] = None,
		  /** Output only. Whether the current user can change the background of this shared drive. */
			canChangeDriveBackground: Option[Boolean] = None,
		  /** Output only. Whether the current user can share files or folders in this shared drive. */
			canShare: Option[Boolean] = None,
		  /** Output only. Whether the current user can change the `copyRequiresWriterPermission` restriction of this shared drive. */
			canChangeCopyRequiresWriterPermissionRestriction: Option[Boolean] = None,
		  /** Output only. Whether the current user can change the `domainUsersOnly` restriction of this shared drive. */
			canChangeDomainUsersOnlyRestriction: Option[Boolean] = None,
		  /** Output only. Whether the current user can change the `driveMembersOnly` restriction of this shared drive. */
			canChangeDriveMembersOnlyRestriction: Option[Boolean] = None,
		  /** Output only. Whether the current user can change the `sharingFoldersRequiresOrganizerPermission` restriction of this shared drive. */
			canChangeSharingFoldersRequiresOrganizerPermissionRestriction: Option[Boolean] = None,
		  /** Output only. Whether the current user can reset the shared drive restrictions to defaults. */
			canResetDriveRestrictions: Option[Boolean] = None,
		  /** Output only. Whether the current user can delete children from folders in this shared drive. */
			canDeleteChildren: Option[Boolean] = None,
		  /** Output only. Whether the current user can trash children from folders in this shared drive. */
			canTrashChildren: Option[Boolean] = None
		)
		
		case class BackgroundImageFileItem(
		  /** The ID of an image file in Google Drive to use for the background image. */
			id: Option[String] = None,
		  /** The X coordinate of the upper left corner of the cropping area in the background image. This is a value in the closed range of 0 to 1. This value represents the horizontal distance from the left side of the entire image to the left side of the cropping area divided by the width of the entire image. */
			xCoordinate: Option[BigDecimal] = None,
		  /** The Y coordinate of the upper left corner of the cropping area in the background image. This is a value in the closed range of 0 to 1. This value represents the vertical distance from the top side of the entire image to the top side of the cropping area divided by the height of the entire image. */
			yCoordinate: Option[BigDecimal] = None,
		  /** The width of the cropped image in the closed range of 0 to 1. This value represents the width of the cropped image divided by the width of the entire image. The height is computed by applying a width to height aspect ratio of 80 to 9. The resulting image must be at least 1280 pixels wide and 144 pixels high. */
			width: Option[BigDecimal] = None
		)
		
		case class RestrictionsItem(
		  /** Whether the options to copy, print, or download files inside this shared drive, should be disabled for readers and commenters. When this restriction is set to `true`, it will override the similarly named field to `true` for any file inside this shared drive. */
			copyRequiresWriterPermission: Option[Boolean] = None,
		  /** Whether access to this shared drive and items inside this shared drive is restricted to users of the domain to which this shared drive belongs. This restriction may be overridden by other sharing policies controlled outside of this shared drive. */
			domainUsersOnly: Option[Boolean] = None,
		  /** Whether access to items inside this shared drive is restricted to its members. */
			driveMembersOnly: Option[Boolean] = None,
		  /** Whether administrative privileges on this shared drive are required to modify restrictions. */
			adminManagedRestrictions: Option[Boolean] = None,
		  /** If true, only users with the organizer role can share folders. If false, users with either the organizer role or the file organizer role can share folders. */
			sharingFoldersRequiresOrganizerPermission: Option[Boolean] = None
		)
	}
	case class Drive(
	  /** Output only. The ID of this shared drive which is also the ID of the top level folder of this shared drive. */
		id: Option[String] = None,
	  /** The name of this shared drive. */
		name: Option[String] = None,
	  /** The color of this shared drive as an RGB hex string. It can only be set on a `drive.drives.update` request that does not set `themeId`. */
		colorRgb: Option[String] = None,
	  /** Output only. Identifies what kind of resource this is. Value: the fixed string `"drive#drive"`. */
		kind: Option[String] = Some("""drive#drive"""),
	  /** Output only. A short-lived link to this shared drive's background image. */
		backgroundImageLink: Option[String] = None,
	  /** Output only. Capabilities the current user has on this shared drive. */
		capabilities: Option[Schema.Drive.CapabilitiesItem] = None,
	  /** The ID of the theme from which the background image and color will be set. The set of possible `driveThemes` can be retrieved from a `drive.about.get` response. When not specified on a `drive.drives.create` request, a random theme is chosen from which the background image and color are set. This is a write-only field; it can only be set on requests that don't set `colorRgb` or `backgroundImageFile`. */
		themeId: Option[String] = None,
	  /** An image file and cropping parameters from which a background image for this shared drive is set. This is a write only field; it can only be set on `drive.drives.update` requests that don't set `themeId`. When specified, all fields of the `backgroundImageFile` must be set. */
		backgroundImageFile: Option[Schema.Drive.BackgroundImageFileItem] = None,
	  /** The time at which the shared drive was created (RFC 3339 date-time). */
		createdTime: Option[String] = None,
	  /** Whether the shared drive is hidden from default view. */
		hidden: Option[Boolean] = None,
	  /** A set of restrictions that apply to this shared drive or items inside this shared drive. Note that restrictions can't be set when creating a shared drive. To add a restriction, first create a shared drive and then use `drives.update` to add restrictions. */
		restrictions: Option[Schema.Drive.RestrictionsItem] = None,
	  /** Output only. The organizational unit of this shared drive. This field is only populated on `drives.list` responses when the `useDomainAdminAccess` parameter is set to `true`. */
		orgUnitId: Option[String] = None
	)
	
	case class Channel(
	  /** A Boolean value to indicate whether payload is wanted. Optional. */
		payload: Option[Boolean] = None,
	  /** A UUID or similar unique string that identifies this channel. */
		id: Option[String] = None,
	  /** An opaque ID that identifies the resource being watched on this channel. Stable across different API versions. */
		resourceId: Option[String] = None,
	  /** A version-specific identifier for the watched resource. */
		resourceUri: Option[String] = None,
	  /** An arbitrary string delivered to the target address with each notification delivered over this channel. Optional. */
		token: Option[String] = None,
	  /** Date and time of notification channel expiration, expressed as a Unix timestamp, in milliseconds. Optional. */
		expiration: Option[String] = None,
	  /** The type of delivery mechanism used for this channel. Valid values are "web_hook" or "webhook". */
		`type`: Option[String] = None,
	  /** The address where notifications are delivered for this channel. */
		address: Option[String] = None,
	  /** Additional parameters controlling delivery channel behavior. Optional. */
		params: Option[Map[String, String]] = None,
	  /** Identifies this as a notification channel used to watch for changes to a resource, which is `api#channel`. */
		kind: Option[String] = Some("""api#channel""")
	)
	
	object Comment {
		case class QuotedFileContentItem(
		  /** The MIME type of the quoted content. */
			mimeType: Option[String] = None,
		  /** The quoted content itself. This is interpreted as plain text if set through the API. */
			value: Option[String] = None
		)
	}
	case class Comment(
	  /** Output only. The ID of the comment. */
		id: Option[String] = None,
	  /** Output only. Identifies what kind of resource this is. Value: the fixed string `"drive#comment"`. */
		kind: Option[String] = Some("""drive#comment"""),
	  /** The time at which the comment was created (RFC 3339 date-time). */
		createdTime: Option[String] = None,
	  /** The last time the comment or any of its replies was modified (RFC 3339 date-time). */
		modifiedTime: Option[String] = None,
	  /** Output only. Whether the comment has been resolved by one of its replies. */
		resolved: Option[Boolean] = None,
	  /** A region of the document represented as a JSON string. For details on defining anchor properties, refer to [Manage comments and replies](https://developers.google.com/drive/api/v3/manage-comments). */
		anchor: Option[String] = None,
	  /** Output only. The full list of replies to the comment in chronological order. */
		replies: Option[List[Schema.Reply]] = None,
	  /** Output only. The author of the comment. The author's email address and permission ID will not be populated. */
		author: Option[Schema.User] = None,
	  /** Output only. Whether the comment has been deleted. A deleted comment has no content. */
		deleted: Option[Boolean] = None,
	  /** Output only. The content of the comment with HTML formatting. */
		htmlContent: Option[String] = None,
	  /** The plain text content of the comment. This field is used for setting the content, while `htmlContent` should be displayed. */
		content: Option[String] = None,
	  /** The file content to which the comment refers, typically within the anchor region. For a text file, for example, this would be the text at the location of the comment. */
		quotedFileContent: Option[Schema.Comment.QuotedFileContentItem] = None
	)
	
	case class Reply(
	  /** Output only. The ID of the reply. */
		id: Option[String] = None,
	  /** Output only. Identifies what kind of resource this is. Value: the fixed string `"drive#reply"`. */
		kind: Option[String] = Some("""drive#reply"""),
	  /** The time at which the reply was created (RFC 3339 date-time). */
		createdTime: Option[String] = None,
	  /** The last time the reply was modified (RFC 3339 date-time). */
		modifiedTime: Option[String] = None,
	  /** The action the reply performed to the parent comment. Valid values are: &#42; `resolve` &#42; `reopen` */
		action: Option[String] = None,
	  /** Output only. The author of the reply. The author's email address and permission ID will not be populated. */
		author: Option[Schema.User] = None,
	  /** Output only. Whether the reply has been deleted. A deleted reply has no content. */
		deleted: Option[Boolean] = None,
	  /** Output only. The content of the reply with HTML formatting. */
		htmlContent: Option[String] = None,
	  /** The plain text content of the reply. This field is used for setting the content, while `htmlContent` should be displayed. This is required on creates if no `action` is specified. */
		content: Option[String] = None
	)
	
	case class CommentList(
	  /** Identifies what kind of resource this is. Value: the fixed string `"drive#commentList"`. */
		kind: Option[String] = Some("""drive#commentList"""),
	  /** The list of comments. If nextPageToken is populated, then this list may be incomplete and an additional page of results should be fetched. */
		comments: Option[List[Schema.Comment]] = None,
	  /** The page token for the next page of comments. This will be absent if the end of the comments list has been reached. If the token is rejected for any reason, it should be discarded, and pagination should be restarted from the first page of results. The page token is typically valid for several hours. However, if new items are added or removed, your expected results might differ. */
		nextPageToken: Option[String] = None
	)
	
	case class DriveList(
	  /** The page token for the next page of shared drives. This will be absent if the end of the list has been reached. If the token is rejected for any reason, it should be discarded, and pagination should be restarted from the first page of results. The page token is typically valid for several hours. However, if new items are added or removed, your expected results might differ. */
		nextPageToken: Option[String] = None,
	  /** Identifies what kind of resource this is. Value: the fixed string `"drive#driveList"`. */
		kind: Option[String] = Some("""drive#driveList"""),
	  /** The list of shared drives. If nextPageToken is populated, then this list may be incomplete and an additional page of results should be fetched. */
		drives: Option[List[Schema.Drive]] = None
	)
	
	case class GeneratedIds(
	  /** The IDs generated for the requesting user in the specified space. */
		ids: Option[List[String]] = None,
	  /** The type of file that can be created with these IDs. */
		space: Option[String] = None,
	  /** Identifies what kind of resource this is. Value: the fixed string `"drive#generatedIds"`. */
		kind: Option[String] = Some("""drive#generatedIds""")
	)
	
	case class FileList(
	  /** The page token for the next page of files. This will be absent if the end of the files list has been reached. If the token is rejected for any reason, it should be discarded, and pagination should be restarted from the first page of results. The page token is typically valid for several hours. However, if new items are added or removed, your expected results might differ. */
		nextPageToken: Option[String] = None,
	  /** Identifies what kind of resource this is. Value: the fixed string `"drive#fileList"`. */
		kind: Option[String] = Some("""drive#fileList"""),
	  /** Whether the search process was incomplete. If true, then some search results might be missing, since all documents were not searched. This can occur when searching multiple drives with the 'allDrives' corpora, but all corpora couldn't be searched. When this happens, it's suggested that clients narrow their query by choosing a different corpus such as 'user' or 'drive'. */
		incompleteSearch: Option[Boolean] = None,
	  /** The list of files. If nextPageToken is populated, then this list may be incomplete and an additional page of results should be fetched. */
		files: Option[List[Schema.File]] = None
	)
	
	case class LabelList(
	  /** The list of labels. */
		labels: Option[List[Schema.Label]] = None,
	  /** The page token for the next page of labels. This field will be absent if the end of the list has been reached. If the token is rejected for any reason, it should be discarded, and pagination should be restarted from the first page of results. The page token is typically valid for several hours. However, if new items are added or removed, your expected results might differ. */
		nextPageToken: Option[String] = None,
	  /** This is always drive#labelList */
		kind: Option[String] = None
	)
	
	case class ModifyLabelsRequest(
	  /** The list of modifications to apply to the labels on the file. */
		labelModifications: Option[List[Schema.LabelModification]] = None,
	  /** This is always drive#modifyLabelsRequest. */
		kind: Option[String] = None
	)
	
	case class LabelModification(
	  /** The ID of the label to modify. */
		labelId: Option[String] = None,
	  /** The list of modifications to this label's fields. */
		fieldModifications: Option[List[Schema.LabelFieldModification]] = None,
	  /** If true, the label will be removed from the file. */
		removeLabel: Option[Boolean] = None,
	  /** This is always drive#labelModification. */
		kind: Option[String] = None
	)
	
	case class LabelFieldModification(
	  /** The ID of the field to be modified. */
		fieldId: Option[String] = None,
	  /** This is always drive#labelFieldModification. */
		kind: Option[String] = None,
	  /** Replaces the value of a dateString Field with these new values. The string must be in the RFC 3339 full-date format: YYYY-MM-DD. */
		setDateValues: Option[List[String]] = None,
	  /** Sets the value of a `text` field. */
		setTextValues: Option[List[String]] = None,
	  /** Replaces a `selection` field with these new values. */
		setSelectionValues: Option[List[String]] = None,
	  /** Replaces the value of an `integer` field with these new values. */
		setIntegerValues: Option[List[String]] = None,
	  /** Replaces a `user` field with these new values. The values must be valid email addresses. */
		setUserValues: Option[List[String]] = None,
	  /** Unsets the values for this field. */
		unsetValues: Option[Boolean] = None
	)
	
	case class ModifyLabelsResponse(
	  /** The list of labels which were added or updated by the request. */
		modifiedLabels: Option[List[Schema.Label]] = None,
	  /** This is always drive#modifyLabelsResponse */
		kind: Option[String] = None
	)
	
	case class PermissionList(
	  /** The page token for the next page of permissions. This field will be absent if the end of the permissions list has been reached. If the token is rejected for any reason, it should be discarded, and pagination should be restarted from the first page of results. The page token is typically valid for several hours. However, if new items are added or removed, your expected results might differ. */
		nextPageToken: Option[String] = None,
	  /** Identifies what kind of resource this is. Value: the fixed string `"drive#permissionList"`. */
		kind: Option[String] = Some("""drive#permissionList"""),
	  /** The list of permissions. If nextPageToken is populated, then this list may be incomplete and an additional page of results should be fetched. */
		permissions: Option[List[Schema.Permission]] = None
	)
	
	case class ReplyList(
	  /** Identifies what kind of resource this is. Value: the fixed string `"drive#replyList"`. */
		kind: Option[String] = Some("""drive#replyList"""),
	  /** The list of replies. If nextPageToken is populated, then this list may be incomplete and an additional page of results should be fetched. */
		replies: Option[List[Schema.Reply]] = None,
	  /** The page token for the next page of replies. This will be absent if the end of the replies list has been reached. If the token is rejected for any reason, it should be discarded, and pagination should be restarted from the first page of results. The page token is typically valid for several hours. However, if new items are added or removed, your expected results might differ. */
		nextPageToken: Option[String] = None
	)
	
	case class Revision(
	  /** Output only. The ID of the revision. */
		id: Option[String] = None,
	  /** Output only. The MIME type of the revision. */
		mimeType: Option[String] = None,
	  /** Output only. Identifies what kind of resource this is. Value: the fixed string `"drive#revision"`. */
		kind: Option[String] = Some("""drive#revision"""),
	  /** Whether this revision is published. This is only applicable to Docs Editors files. */
		published: Option[Boolean] = None,
	  /** Output only. Links for exporting Docs Editors files to specific formats. */
		exportLinks: Option[Map[String, String]] = None,
	  /** Whether to keep this revision forever, even if it is no longer the head revision. If not set, the revision will be automatically purged 30 days after newer content is uploaded. This can be set on a maximum of 200 revisions for a file. This field is only applicable to files with binary content in Drive. */
		keepForever: Option[Boolean] = None,
	  /** Output only. The MD5 checksum of the revision's content. This is only applicable to files with binary content in Drive. */
		md5Checksum: Option[String] = None,
	  /** The last time the revision was modified (RFC 3339 date-time). */
		modifiedTime: Option[String] = None,
	  /** Whether subsequent revisions will be automatically republished. This is only applicable to Docs Editors files. */
		publishAuto: Option[Boolean] = None,
	  /** Whether this revision is published outside the domain. This is only applicable to Docs Editors files. */
		publishedOutsideDomain: Option[Boolean] = None,
	  /** Output only. A link to the published revision. This is only populated for Google Sites files. */
		publishedLink: Option[String] = None,
	  /** Output only. The size of the revision's content in bytes. This is only applicable to files with binary content in Drive. */
		size: Option[String] = None,
	  /** Output only. The original filename used to create this revision. This is only applicable to files with binary content in Drive. */
		originalFilename: Option[String] = None,
	  /** Output only. The last user to modify this revision. This field is only populated when the last modification was performed by a signed-in user. */
		lastModifyingUser: Option[Schema.User] = None
	)
	
	case class RevisionList(
	  /** The page token for the next page of revisions. This will be absent if the end of the revisions list has been reached. If the token is rejected for any reason, it should be discarded, and pagination should be restarted from the first page of results. The page token is typically valid for several hours. However, if new items are added or removed, your expected results might differ. */
		nextPageToken: Option[String] = None,
	  /** Identifies what kind of resource this is. Value: the fixed string `"drive#revisionList"`. */
		kind: Option[String] = Some("""drive#revisionList"""),
	  /** The list of revisions. If nextPageToken is populated, then this list may be incomplete and an additional page of results should be fetched. */
		revisions: Option[List[Schema.Revision]] = None
	)
	
	case class TeamDriveList(
	  /** The page token for the next page of Team Drives. This will be absent if the end of the Team Drives list has been reached. If the token is rejected for any reason, it should be discarded, and pagination should be restarted from the first page of results. The page token is typically valid for several hours. However, if new items are added or removed, your expected results might differ. */
		nextPageToken: Option[String] = None,
	  /** Identifies what kind of resource this is. Value: the fixed string `"drive#teamDriveList"`. */
		kind: Option[String] = Some("""drive#teamDriveList"""),
	  /** The list of Team Drives. If nextPageToken is populated, then this list may be incomplete and an additional page of results should be fetched. */
		teamDrives: Option[List[Schema.TeamDrive]] = None
	)
	
	case class AccessProposal(
	  /** The file id that the proposal for access is on */
		fileId: Option[String] = None,
	  /** The id of the access proposal */
		proposalId: Option[String] = None,
	  /** The email address of the requesting user */
		requesterEmailAddress: Option[String] = None,
	  /** The email address of the user that will receive permissions if accepted */
		recipientEmailAddress: Option[String] = None,
	  /** A wrapper for the role and view of an access proposal. */
		rolesAndViews: Option[List[Schema.AccessProposalRoleAndView]] = None,
	  /** The message that the requester added to the proposal */
		requestMessage: Option[String] = None,
	  /** The creation time */
		createTime: Option[String] = None
	)
	
	case class AccessProposalRoleAndView(
	  /** The role that was proposed by the requester New values may be added in the future, but the following are currently possible: &#42; `writer` &#42; `commenter` &#42; `reader` */
		role: Option[String] = None,
	  /** Indicates the view for this access proposal. Only populated for proposals that belong to a view. `published` is the only supported value. */
		view: Option[String] = None
	)
	
	object ResolveAccessProposalRequest {
		enum ActionEnum extends Enum[ActionEnum] { case ACTION_UNSPECIFIED, ACCEPT, DENY }
	}
	case class ResolveAccessProposalRequest(
	  /** Optional. The roles the approver has allowed, if any. Note: This field is required for the `ACCEPT` action. */
		role: Option[List[String]] = None,
	  /** Optional. Indicates the view for this access proposal. This should only be set when the proposal belongs to a view. `published` is the only supported value. */
		view: Option[String] = None,
	  /** Required. The action to take on the AccessProposal. */
		action: Option[Schema.ResolveAccessProposalRequest.ActionEnum] = None,
	  /** Optional. Whether to send an email to the requester when the AccessProposal is denied or accepted. */
		sendNotification: Option[Boolean] = None
	)
	
	case class ListAccessProposalsResponse(
	  /** The list of Access Proposals. This field is only populated in v3 and v3beta. */
		accessProposals: Option[List[Schema.AccessProposal]] = None,
	  /** The continuation token for the next page of results. This will be absent if the end of the results list has been reached. If the token is rejected for any reason, it should be discarded, and pagination should be restarted from the first page of results. */
		nextPageToken: Option[String] = None
	)
}
