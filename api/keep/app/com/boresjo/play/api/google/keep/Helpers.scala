package com.boresjo.play.api.google.keep

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Helpers {
	import Schema.*
	object AutoOption {
		given putString: Conversion[String, Option[String]] = (fun: String) => Option(fun)
		given putBoolean: Conversion[Boolean, Option[Boolean]] = (fun: Boolean) => Option(fun)
		given putListSchemaAttachment: Conversion[List[Schema.Attachment], Option[List[Schema.Attachment]]] = (fun: List[Schema.Attachment]) => Option(fun)
		given putListSchemaPermission: Conversion[List[Schema.Permission], Option[List[Schema.Permission]]] = (fun: List[Schema.Permission]) => Option(fun)
		given putSchemaSection: Conversion[Schema.Section, Option[Schema.Section]] = (fun: Schema.Section) => Option(fun)
		given putListString: Conversion[List[String], Option[List[String]]] = (fun: List[String]) => Option(fun)
		given putSchemaPermissionRoleEnum: Conversion[Schema.Permission.RoleEnum, Option[Schema.Permission.RoleEnum]] = (fun: Schema.Permission.RoleEnum) => Option(fun)
		given putSchemaUser: Conversion[Schema.User, Option[Schema.User]] = (fun: Schema.User) => Option(fun)
		given putSchemaGroup: Conversion[Schema.Group, Option[Schema.Group]] = (fun: Schema.Group) => Option(fun)
		given putSchemaFamily: Conversion[Schema.Family, Option[Schema.Family]] = (fun: Schema.Family) => Option(fun)
		given putSchemaTextContent: Conversion[Schema.TextContent, Option[Schema.TextContent]] = (fun: Schema.TextContent) => Option(fun)
		given putSchemaListContent: Conversion[Schema.ListContent, Option[Schema.ListContent]] = (fun: Schema.ListContent) => Option(fun)
		given putListSchemaListItem: Conversion[List[Schema.ListItem], Option[List[Schema.ListItem]]] = (fun: List[Schema.ListItem]) => Option(fun)
		given putListSchemaNote: Conversion[List[Schema.Note], Option[List[Schema.Note]]] = (fun: List[Schema.Note]) => Option(fun)
		given putListSchemaCreatePermissionRequest: Conversion[List[Schema.CreatePermissionRequest], Option[List[Schema.CreatePermissionRequest]]] = (fun: List[Schema.CreatePermissionRequest]) => Option(fun)
		given putSchemaPermission: Conversion[Schema.Permission, Option[Schema.Permission]] = (fun: Schema.Permission) => Option(fun)
	}
	object OptionDefault {
		given getString: Conversion[Option[String], String] = (fun: Option[String]) => fun.getOrElse("")
	}
}
