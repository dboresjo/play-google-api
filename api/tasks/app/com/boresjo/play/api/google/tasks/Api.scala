package com.boresjo.play.api.google.tasks

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://tasks.googleapis.com/"

	object tasklists {
		class insert(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withTaskList(body: Schema.TaskList) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.TaskList])
		}
		object insert {
			def apply()(using auth: AuthToken, ec: ExecutionContext): insert = new insert(ws.url(BASE_URL + s"tasks/v1/users/@me/lists").addQueryStringParameters())
		}
		class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Unit]) {
			def apply() = auth.exec(req,_.execute("DELETE")).map(_ => ())
		}
		object delete {
			def apply(tasklist: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"tasks/v1/users/@me/lists/${tasklist}").addQueryStringParameters())
			given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
		}
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.TaskList]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.TaskList])
		}
		object get {
			def apply(tasklist: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"tasks/v1/users/@me/lists/${tasklist}").addQueryStringParameters())
			given Conversion[get, Future[Schema.TaskList]] = (fun: get) => fun.apply()
		}
		class update(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withTaskList(body: Schema.TaskList) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.TaskList])
		}
		object update {
			def apply(tasklist: String)(using auth: AuthToken, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"tasks/v1/users/@me/lists/${tasklist}").addQueryStringParameters())
		}
		class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withTaskList(body: Schema.TaskList) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.TaskList])
		}
		object patch {
			def apply(tasklist: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"tasks/v1/users/@me/lists/${tasklist}").addQueryStringParameters())
		}
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.TaskLists]) {
			/** Token specifying the result page to return. Optional. */
			def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
			/** Maximum number of task lists returned on one page. Optional. The default is 20 (max allowed: 100).<br>Format: int32 */
			def withMaxResults(maxResults: Int) = new list(req.addQueryStringParameters("maxResults" -> maxResults.toString))
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.TaskLists])
		}
		object list {
			def apply()(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"tasks/v1/users/@me/lists").addQueryStringParameters())
			given Conversion[list, Future[Schema.TaskLists]] = (fun: list) => fun.apply()
		}
	}
	object tasks {
		class move(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Task]) {
			/** Optional. Destination task list identifier. If set, the task is moved from tasklist to the destinationTasklist list. Otherwise the task is moved within its current list. Recurrent tasks cannot currently be moved between lists. Optional. */
			def withDestinationTasklist(destinationTasklist: String) = new move(req.addQueryStringParameters("destinationTasklist" -> destinationTasklist.toString))
			/** New parent task identifier. If the task is moved to the top level, this parameter is omitted. Assigned tasks can not be set as parent task (have subtasks) or be moved under a parent task (become subtasks). Optional. */
			def withParent(parent: String) = new move(req.addQueryStringParameters("parent" -> parent.toString))
			/** New previous sibling task identifier. If the task is moved to the first position among its siblings, this parameter is omitted. Optional. */
			def withPrevious(previous: String) = new move(req.addQueryStringParameters("previous" -> previous.toString))
			def apply() = auth.exec(req,_.execute("POST")).map(_.json.as[Schema.Task])
		}
		object move {
			def apply(tasklist: String, task: String)(using auth: AuthToken, ec: ExecutionContext): move = new move(ws.url(BASE_URL + s"tasks/v1/lists/${tasklist}/tasks/${task}/move").addQueryStringParameters())
			given Conversion[move, Future[Schema.Task]] = (fun: move) => fun.apply()
		}
		class insert(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			/** Parent task identifier. If the task is created at the top level, this parameter is omitted. An assigned task cannot be a parent task, nor can it have a parent. Setting the parent to an assigned task results in failure of the request. Optional. */
			def withParent(parent: String) = new insert(req.addQueryStringParameters("parent" -> parent.toString))
			/** Previous sibling task identifier. If the task is created at the first position among its siblings, this parameter is omitted. Optional. */
			def withPrevious(previous: String) = new insert(req.addQueryStringParameters("previous" -> previous.toString))
			def withTask(body: Schema.Task) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Task])
		}
		object insert {
			def apply(tasklist: String)(using auth: AuthToken, ec: ExecutionContext): insert = new insert(ws.url(BASE_URL + s"tasks/v1/lists/${tasklist}/tasks").addQueryStringParameters())
		}
		class clear(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Unit]) {
			def apply() = auth.exec(req,_.execute("POST")).map(_ => ())
		}
		object clear {
			def apply(tasklist: String)(using auth: AuthToken, ec: ExecutionContext): clear = new clear(ws.url(BASE_URL + s"tasks/v1/lists/${tasklist}/clear").addQueryStringParameters())
			given Conversion[clear, Future[Unit]] = (fun: clear) => fun.apply()
		}
		class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Unit]) {
			def apply() = auth.exec(req,_.execute("DELETE")).map(_ => ())
		}
		object delete {
			def apply(tasklist: String, task: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"tasks/v1/lists/${tasklist}/tasks/${task}").addQueryStringParameters())
			given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
		}
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Task]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Task])
		}
		object get {
			def apply(tasklist: String, task: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"tasks/v1/lists/${tasklist}/tasks/${task}").addQueryStringParameters())
			given Conversion[get, Future[Schema.Task]] = (fun: get) => fun.apply()
		}
		class update(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withTask(body: Schema.Task) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.Task])
		}
		object update {
			def apply(task: String, tasklist: String)(using auth: AuthToken, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"tasks/v1/lists/${tasklist}/tasks/${task}").addQueryStringParameters())
		}
		class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withTask(body: Schema.Task) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Task])
		}
		object patch {
			def apply(task: String, tasklist: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"tasks/v1/lists/${tasklist}/tasks/${task}").addQueryStringParameters())
		}
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Tasks]) {
			/** Lower bound for a task's due date (as a RFC 3339 timestamp) to filter by. Optional. The default is not to filter by due date. */
			def withDueMin(dueMin: String) = new list(req.addQueryStringParameters("dueMin" -> dueMin.toString))
			/** Flag indicating whether deleted tasks are returned in the result. Optional. The default is False. */
			def withShowDeleted(showDeleted: Boolean) = new list(req.addQueryStringParameters("showDeleted" -> showDeleted.toString))
			/** Token specifying the result page to return. Optional. */
			def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
			/** Lower bound for a task's completion date (as a RFC 3339 timestamp) to filter by. Optional. The default is not to filter by completion date. */
			def withCompletedMin(completedMin: String) = new list(req.addQueryStringParameters("completedMin" -> completedMin.toString))
			/** Upper bound for a task's due date (as a RFC 3339 timestamp) to filter by. Optional. The default is not to filter by due date. */
			def withDueMax(dueMax: String) = new list(req.addQueryStringParameters("dueMax" -> dueMax.toString))
			/** Maximum number of tasks returned on one page. Optional. The default is 20 (max allowed: 100).<br>Format: int32 */
			def withMaxResults(maxResults: Int) = new list(req.addQueryStringParameters("maxResults" -> maxResults.toString))
			/** Optional. Flag indicating whether tasks assigned to the current user are returned in the result. Optional. The default is False. */
			def withShowAssigned(showAssigned: Boolean) = new list(req.addQueryStringParameters("showAssigned" -> showAssigned.toString))
			/** Lower bound for a task's last modification time (as a RFC 3339 timestamp) to filter by. Optional. The default is not to filter by last modification time. */
			def withUpdatedMin(updatedMin: String) = new list(req.addQueryStringParameters("updatedMin" -> updatedMin.toString))
			/** Upper bound for a task's completion date (as a RFC 3339 timestamp) to filter by. Optional. The default is not to filter by completion date. */
			def withCompletedMax(completedMax: String) = new list(req.addQueryStringParameters("completedMax" -> completedMax.toString))
			/** Flag indicating whether hidden tasks are returned in the result. Optional. The default is False. */
			def withShowHidden(showHidden: Boolean) = new list(req.addQueryStringParameters("showHidden" -> showHidden.toString))
			/** Flag indicating whether completed tasks are returned in the result. Note that showHidden must also be True to show tasks completed in first party clients, such as the web UI and Google's mobile apps. Optional. The default is True. */
			def withShowCompleted(showCompleted: Boolean) = new list(req.addQueryStringParameters("showCompleted" -> showCompleted.toString))
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Tasks])
		}
		object list {
			def apply(tasklist: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"tasks/v1/lists/${tasklist}/tasks").addQueryStringParameters())
			given Conversion[list, Future[Schema.Tasks]] = (fun: list) => fun.apply()
		}
	}
}
