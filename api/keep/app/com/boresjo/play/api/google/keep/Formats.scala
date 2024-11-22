package com.boresjo.play.api.google.keep

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Formats {
	given fmtNote: Format[Schema.Note] = Json.format[Schema.Note]
	given fmtAttachment: Format[Schema.Attachment] = Json.format[Schema.Attachment]
	given fmtPermission: Format[Schema.Permission] = Json.format[Schema.Permission]
	given fmtSection: Format[Schema.Section] = Json.format[Schema.Section]
	given fmtPermissionRoleEnum: Format[Schema.Permission.RoleEnum] = JsonEnumFormat[Schema.Permission.RoleEnum]
	given fmtUser: Format[Schema.User] = Json.format[Schema.User]
	given fmtGroup: Format[Schema.Group] = Json.format[Schema.Group]
	given fmtFamily: Format[Schema.Family] = Json.format[Schema.Family]
	given fmtTextContent: Format[Schema.TextContent] = Json.format[Schema.TextContent]
	given fmtListContent: Format[Schema.ListContent] = Json.format[Schema.ListContent]
	given fmtListItem: Format[Schema.ListItem] = Json.format[Schema.ListItem]
	given fmtListNotesResponse: Format[Schema.ListNotesResponse] = Json.format[Schema.ListNotesResponse]
	given fmtEmpty: Format[Schema.Empty] = Json.format[Schema.Empty]
	given fmtBatchCreatePermissionsRequest: Format[Schema.BatchCreatePermissionsRequest] = Json.format[Schema.BatchCreatePermissionsRequest]
	given fmtCreatePermissionRequest: Format[Schema.CreatePermissionRequest] = Json.format[Schema.CreatePermissionRequest]
	given fmtBatchCreatePermissionsResponse: Format[Schema.BatchCreatePermissionsResponse] = Json.format[Schema.BatchCreatePermissionsResponse]
	given fmtBatchDeletePermissionsRequest: Format[Schema.BatchDeletePermissionsRequest] = Json.format[Schema.BatchDeletePermissionsRequest]
}
