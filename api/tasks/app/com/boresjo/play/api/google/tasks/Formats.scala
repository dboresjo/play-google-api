package com.boresjo.play.api.google.tasks

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Formats {
	given fmtTasks: Format[Schema.Tasks] = Json.format[Schema.Tasks]
	given fmtTask: Format[Schema.Task] = Json.format[Schema.Task]
	given fmtSpaceInfo: Format[Schema.SpaceInfo] = Json.format[Schema.SpaceInfo]
	given fmtAssignmentInfo: Format[Schema.AssignmentInfo] = Json.format[Schema.AssignmentInfo]
	given fmtTaskLinksItem: Format[Schema.Task.LinksItem] = Json.format[Schema.Task.LinksItem]
	given fmtTaskLists: Format[Schema.TaskLists] = Json.format[Schema.TaskLists]
	given fmtTaskList: Format[Schema.TaskList] = Json.format[Schema.TaskList]
	given fmtDriveResourceInfo: Format[Schema.DriveResourceInfo] = Json.format[Schema.DriveResourceInfo]
	given fmtAssignmentInfoSurfaceTypeEnum: Format[Schema.AssignmentInfo.SurfaceTypeEnum] = JsonEnumFormat[Schema.AssignmentInfo.SurfaceTypeEnum]
}
