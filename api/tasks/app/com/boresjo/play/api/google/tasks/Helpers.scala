package com.boresjo.play.api.google.tasks

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Helpers {
	import Schema.*
	object AutoOption {
		given putString: Conversion[String, Option[String]] = (fun: String) => Option(fun)
		given putListSchemaTask: Conversion[List[Schema.Task], Option[List[Schema.Task]]] = (fun: List[Schema.Task]) => Option(fun)
		given putBoolean: Conversion[Boolean, Option[Boolean]] = (fun: Boolean) => Option(fun)
		given putSchemaAssignmentInfo: Conversion[Schema.AssignmentInfo, Option[Schema.AssignmentInfo]] = (fun: Schema.AssignmentInfo) => Option(fun)
		given putListSchemaTaskLinksItem: Conversion[List[Schema.Task.LinksItem], Option[List[Schema.Task.LinksItem]]] = (fun: List[Schema.Task.LinksItem]) => Option(fun)
		given putListSchemaTaskList: Conversion[List[Schema.TaskList], Option[List[Schema.TaskList]]] = (fun: List[Schema.TaskList]) => Option(fun)
		given putSchemaAssignmentInfoSurfaceTypeEnum: Conversion[Schema.AssignmentInfo.SurfaceTypeEnum, Option[Schema.AssignmentInfo.SurfaceTypeEnum]] = (fun: Schema.AssignmentInfo.SurfaceTypeEnum) => Option(fun)
		given putSchemaDriveResourceInfo: Conversion[Schema.DriveResourceInfo, Option[Schema.DriveResourceInfo]] = (fun: Schema.DriveResourceInfo) => Option(fun)
		given putSchemaSpaceInfo: Conversion[Schema.SpaceInfo, Option[Schema.SpaceInfo]] = (fun: Schema.SpaceInfo) => Option(fun)
	}
	object OptionDefault {
		given getString: Conversion[Option[String], String] = (fun: Option[String]) => fun.getOrElse("")
	}
}
