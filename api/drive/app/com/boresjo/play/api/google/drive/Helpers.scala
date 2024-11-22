package com.boresjo.play.api.google.drive

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Helpers {
	import Schema.*
	object AutoOption {
		given putListSchemaOperation: Conversion[List[Schema.Operation], Option[List[Schema.Operation]]] = (fun: List[Schema.Operation]) => Option(fun)
		given putString: Conversion[String, Option[String]] = (fun: String) => Option(fun)
		given putMapStringJsValue: Conversion[Map[String, JsValue], Option[Map[String, JsValue]]] = (fun: Map[String, JsValue]) => Option(fun)
		given putBoolean: Conversion[Boolean, Option[Boolean]] = (fun: Boolean) => Option(fun)
		given putSchemaStatus: Conversion[Schema.Status, Option[Schema.Status]] = (fun: Schema.Status) => Option(fun)
		given putInt: Conversion[Int, Option[Int]] = (fun: Int) => Option(fun)
		given putListMapStringJsValue: Conversion[List[Map[String, JsValue]], Option[List[Map[String, JsValue]]]] = (fun: List[Map[String, JsValue]]) => Option(fun)
		given putSchemaAboutStorageQuotaItem: Conversion[Schema.About.StorageQuotaItem, Option[Schema.About.StorageQuotaItem]] = (fun: Schema.About.StorageQuotaItem) => Option(fun)
		given putListSchemaAboutDriveThemesItem: Conversion[List[Schema.About.DriveThemesItem], Option[List[Schema.About.DriveThemesItem]]] = (fun: List[Schema.About.DriveThemesItem]) => Option(fun)
		given putMapStringListString: Conversion[Map[String, List[String]], Option[Map[String, List[String]]]] = (fun: Map[String, List[String]]) => Option(fun)
		given putSchemaUser: Conversion[Schema.User, Option[Schema.User]] = (fun: Schema.User) => Option(fun)
		given putListString: Conversion[List[String], Option[List[String]]] = (fun: List[String]) => Option(fun)
		given putMapStringString: Conversion[Map[String, String], Option[Map[String, String]]] = (fun: Map[String, String]) => Option(fun)
		given putListSchemaAboutTeamDriveThemesItem: Conversion[List[Schema.About.TeamDriveThemesItem], Option[List[Schema.About.TeamDriveThemesItem]]] = (fun: List[Schema.About.TeamDriveThemesItem]) => Option(fun)
		given putListSchemaAppIcons: Conversion[List[Schema.AppIcons], Option[List[Schema.AppIcons]]] = (fun: List[Schema.AppIcons]) => Option(fun)
		given putListSchemaApp: Conversion[List[Schema.App], Option[List[Schema.App]]] = (fun: List[Schema.App]) => Option(fun)
		given putListSchemaChange: Conversion[List[Schema.Change], Option[List[Schema.Change]]] = (fun: List[Schema.Change]) => Option(fun)
		given putSchemaFile: Conversion[Schema.File, Option[Schema.File]] = (fun: Schema.File) => Option(fun)
		given putSchemaTeamDrive: Conversion[Schema.TeamDrive, Option[Schema.TeamDrive]] = (fun: Schema.TeamDrive) => Option(fun)
		given putSchemaDrive: Conversion[Schema.Drive, Option[Schema.Drive]] = (fun: Schema.Drive) => Option(fun)
		given putSchemaFileContentHintsItem: Conversion[Schema.File.ContentHintsItem, Option[Schema.File.ContentHintsItem]] = (fun: Schema.File.ContentHintsItem) => Option(fun)
		given putListSchemaUser: Conversion[List[Schema.User], Option[List[Schema.User]]] = (fun: List[Schema.User]) => Option(fun)
		given putListSchemaPermission: Conversion[List[Schema.Permission], Option[List[Schema.Permission]]] = (fun: List[Schema.Permission]) => Option(fun)
		given putSchemaFileCapabilitiesItem: Conversion[Schema.File.CapabilitiesItem, Option[Schema.File.CapabilitiesItem]] = (fun: Schema.File.CapabilitiesItem) => Option(fun)
		given putSchemaFileImageMediaMetadataItem: Conversion[Schema.File.ImageMediaMetadataItem, Option[Schema.File.ImageMediaMetadataItem]] = (fun: Schema.File.ImageMediaMetadataItem) => Option(fun)
		given putSchemaFileVideoMediaMetadataItem: Conversion[Schema.File.VideoMediaMetadataItem, Option[Schema.File.VideoMediaMetadataItem]] = (fun: Schema.File.VideoMediaMetadataItem) => Option(fun)
		given putSchemaFileShortcutDetailsItem: Conversion[Schema.File.ShortcutDetailsItem, Option[Schema.File.ShortcutDetailsItem]] = (fun: Schema.File.ShortcutDetailsItem) => Option(fun)
		given putListSchemaContentRestriction: Conversion[List[Schema.ContentRestriction], Option[List[Schema.ContentRestriction]]] = (fun: List[Schema.ContentRestriction]) => Option(fun)
		given putSchemaFileLinkShareMetadataItem: Conversion[Schema.File.LinkShareMetadataItem, Option[Schema.File.LinkShareMetadataItem]] = (fun: Schema.File.LinkShareMetadataItem) => Option(fun)
		given putSchemaFileLabelInfoItem: Conversion[Schema.File.LabelInfoItem, Option[Schema.File.LabelInfoItem]] = (fun: Schema.File.LabelInfoItem) => Option(fun)
		given putListSchemaPermissionPermissionDetailsItem: Conversion[List[Schema.Permission.PermissionDetailsItem], Option[List[Schema.Permission.PermissionDetailsItem]]] = (fun: List[Schema.Permission.PermissionDetailsItem]) => Option(fun)
		given putListSchemaPermissionTeamDrivePermissionDetailsItem: Conversion[List[Schema.Permission.TeamDrivePermissionDetailsItem], Option[List[Schema.Permission.TeamDrivePermissionDetailsItem]]] = (fun: List[Schema.Permission.TeamDrivePermissionDetailsItem]) => Option(fun)
		given putMapStringSchemaLabelField: Conversion[Map[String, Schema.LabelField], Option[Map[String, Schema.LabelField]]] = (fun: Map[String, Schema.LabelField]) => Option(fun)
		given putSchemaTeamDriveCapabilitiesItem: Conversion[Schema.TeamDrive.CapabilitiesItem, Option[Schema.TeamDrive.CapabilitiesItem]] = (fun: Schema.TeamDrive.CapabilitiesItem) => Option(fun)
		given putSchemaTeamDriveBackgroundImageFileItem: Conversion[Schema.TeamDrive.BackgroundImageFileItem, Option[Schema.TeamDrive.BackgroundImageFileItem]] = (fun: Schema.TeamDrive.BackgroundImageFileItem) => Option(fun)
		given putSchemaTeamDriveRestrictionsItem: Conversion[Schema.TeamDrive.RestrictionsItem, Option[Schema.TeamDrive.RestrictionsItem]] = (fun: Schema.TeamDrive.RestrictionsItem) => Option(fun)
		given putSchemaDriveCapabilitiesItem: Conversion[Schema.Drive.CapabilitiesItem, Option[Schema.Drive.CapabilitiesItem]] = (fun: Schema.Drive.CapabilitiesItem) => Option(fun)
		given putSchemaDriveBackgroundImageFileItem: Conversion[Schema.Drive.BackgroundImageFileItem, Option[Schema.Drive.BackgroundImageFileItem]] = (fun: Schema.Drive.BackgroundImageFileItem) => Option(fun)
		given putSchemaDriveRestrictionsItem: Conversion[Schema.Drive.RestrictionsItem, Option[Schema.Drive.RestrictionsItem]] = (fun: Schema.Drive.RestrictionsItem) => Option(fun)
		given putListSchemaReply: Conversion[List[Schema.Reply], Option[List[Schema.Reply]]] = (fun: List[Schema.Reply]) => Option(fun)
		given putSchemaCommentQuotedFileContentItem: Conversion[Schema.Comment.QuotedFileContentItem, Option[Schema.Comment.QuotedFileContentItem]] = (fun: Schema.Comment.QuotedFileContentItem) => Option(fun)
		given putListSchemaComment: Conversion[List[Schema.Comment], Option[List[Schema.Comment]]] = (fun: List[Schema.Comment]) => Option(fun)
		given putListSchemaDrive: Conversion[List[Schema.Drive], Option[List[Schema.Drive]]] = (fun: List[Schema.Drive]) => Option(fun)
		given putListSchemaFile: Conversion[List[Schema.File], Option[List[Schema.File]]] = (fun: List[Schema.File]) => Option(fun)
		given putListSchemaLabel: Conversion[List[Schema.Label], Option[List[Schema.Label]]] = (fun: List[Schema.Label]) => Option(fun)
		given putListSchemaLabelModification: Conversion[List[Schema.LabelModification], Option[List[Schema.LabelModification]]] = (fun: List[Schema.LabelModification]) => Option(fun)
		given putListSchemaLabelFieldModification: Conversion[List[Schema.LabelFieldModification], Option[List[Schema.LabelFieldModification]]] = (fun: List[Schema.LabelFieldModification]) => Option(fun)
		given putListSchemaRevision: Conversion[List[Schema.Revision], Option[List[Schema.Revision]]] = (fun: List[Schema.Revision]) => Option(fun)
		given putListSchemaTeamDrive: Conversion[List[Schema.TeamDrive], Option[List[Schema.TeamDrive]]] = (fun: List[Schema.TeamDrive]) => Option(fun)
		given putListSchemaAccessProposalRoleAndView: Conversion[List[Schema.AccessProposalRoleAndView], Option[List[Schema.AccessProposalRoleAndView]]] = (fun: List[Schema.AccessProposalRoleAndView]) => Option(fun)
		given putSchemaResolveAccessProposalRequestActionEnum: Conversion[Schema.ResolveAccessProposalRequest.ActionEnum, Option[Schema.ResolveAccessProposalRequest.ActionEnum]] = (fun: Schema.ResolveAccessProposalRequest.ActionEnum) => Option(fun)
		given putListSchemaAccessProposal: Conversion[List[Schema.AccessProposal], Option[List[Schema.AccessProposal]]] = (fun: List[Schema.AccessProposal]) => Option(fun)
	}
	object OptionDefault {
		given getString: Conversion[Option[String], String] = (fun: Option[String]) => fun.getOrElse("")
	}
}
