package com.boresjo.play.api.google.tasks

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	val scopes = Seq(
		"""https://www.googleapis.com/auth/tasks""" /* Create, edit, organize, and delete all your tasks */,
		"""https://www.googleapis.com/auth/tasks.readonly""" /* View your tasks */
	)

	private val BASE_URL = "https://tasks.googleapis.com/"

	object tasklists {
		/** Creates a new task list and adds it to the authenticated user's task lists. A user can have up to 2000 lists at a time. */
		class insert(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/tasks""")
			/** Perform the request */
			def withTaskList(body: Schema.TaskList) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.TaskList])
		}
		object insert {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): insert = new insert(ws.url(BASE_URL + s"tasks/v1/users/@me/lists").addQueryStringParameters())
		}
		/** Deletes the authenticated user's specified task list. If the list contains assigned tasks, both the assigned tasks and the original tasks in the assignment surface (Docs, Chat Spaces) are deleted. */
		class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Unit]) {
			val scopes = Seq("""https://www.googleapis.com/auth/tasks""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_ => ())
		}
		object delete {
			def apply(tasklist: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"tasks/v1/users/@me/lists/${tasklist}").addQueryStringParameters())
			given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
		}
		/** Returns the authenticated user's specified task list. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.TaskList]) {
			val scopes = Seq("""https://www.googleapis.com/auth/tasks""", """https://www.googleapis.com/auth/tasks.readonly""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.TaskList])
		}
		object get {
			def apply(tasklist: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"tasks/v1/users/@me/lists/${tasklist}").addQueryStringParameters())
			given Conversion[get, Future[Schema.TaskList]] = (fun: get) => fun.apply()
		}
		/** Updates the authenticated user's specified task list. */
		class update(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/tasks""")
			/** Perform the request */
			def withTaskList(body: Schema.TaskList) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.TaskList])
		}
		object update {
			def apply(tasklist: String)(using signer: RequestSigner, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"tasks/v1/users/@me/lists/${tasklist}").addQueryStringParameters())
		}
		/** Updates the authenticated user's specified task list. This method supports patch semantics. */
		class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/tasks""")
			/** Perform the request */
			def withTaskList(body: Schema.TaskList) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.TaskList])
		}
		object patch {
			def apply(tasklist: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"tasks/v1/users/@me/lists/${tasklist}").addQueryStringParameters())
		}
		/** Returns all the authenticated user's task lists. A user can have up to 2000 lists at a time. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.TaskLists]) {
			val scopes = Seq("""https://www.googleapis.com/auth/tasks""", """https://www.googleapis.com/auth/tasks.readonly""")
			/** Token specifying the result page to return. Optional. */
			def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
			/** Maximum number of task lists returned on one page. Optional. The default is 20 (max allowed: 100).<br>Format: int32 */
			def withMaxResults(maxResults: Int) = new list(req.addQueryStringParameters("maxResults" -> maxResults.toString))
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.TaskLists])
		}
		object list {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"tasks/v1/users/@me/lists").addQueryStringParameters())
			given Conversion[list, Future[Schema.TaskLists]] = (fun: list) => fun.apply()
		}
	}
	object tasks {
		/** Moves the specified task to another position in the destination task list. If the destination list is not specified, the task is moved within its current list. This can include putting it as a child task under a new parent and/or move it to a different position among its sibling tasks. A user can have up to 2,000 subtasks per task. */
		class move(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Task]) {
			val scopes = Seq("""https://www.googleapis.com/auth/tasks""")
			/** Optional. Destination task list identifier. If set, the task is moved from tasklist to the destinationTasklist list. Otherwise the task is moved within its current list. Recurrent tasks cannot currently be moved between lists. Optional. */
			def withDestinationTasklist(destinationTasklist: String) = new move(req.addQueryStringParameters("destinationTasklist" -> destinationTasklist.toString))
			/** New parent task identifier. If the task is moved to the top level, this parameter is omitted. Assigned tasks can not be set as parent task (have subtasks) or be moved under a parent task (become subtasks). Optional. */
			def withParent(parent: String) = new move(req.addQueryStringParameters("parent" -> parent.toString))
			/** New previous sibling task identifier. If the task is moved to the first position among its siblings, this parameter is omitted. Optional. */
			def withPrevious(previous: String) = new move(req.addQueryStringParameters("previous" -> previous.toString))
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("POST")).map(_.json.as[Schema.Task])
		}
		object move {
			def apply(tasklist: String, task: String)(using signer: RequestSigner, ec: ExecutionContext): move = new move(ws.url(BASE_URL + s"tasks/v1/lists/${tasklist}/tasks/${task}/move").addQueryStringParameters())
			given Conversion[move, Future[Schema.Task]] = (fun: move) => fun.apply()
		}
		/** Creates a new task on the specified task list. Tasks assigned from Docs or Chat Spaces cannot be inserted from Tasks Public API; they can only be created by assigning them from Docs or Chat Spaces. A user can have up to 20,000 non-hidden tasks per list and up to 100,000 tasks in total at a time. */
		class insert(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/tasks""")
			/** Parent task identifier. If the task is created at the top level, this parameter is omitted. An assigned task cannot be a parent task, nor can it have a parent. Setting the parent to an assigned task results in failure of the request. Optional. */
			def withParent(parent: String) = new insert(req.addQueryStringParameters("parent" -> parent.toString))
			/** Previous sibling task identifier. If the task is created at the first position among its siblings, this parameter is omitted. Optional. */
			def withPrevious(previous: String) = new insert(req.addQueryStringParameters("previous" -> previous.toString))
			/** Perform the request */
			def withTask(body: Schema.Task) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Task])
		}
		object insert {
			def apply(tasklist: String)(using signer: RequestSigner, ec: ExecutionContext): insert = new insert(ws.url(BASE_URL + s"tasks/v1/lists/${tasklist}/tasks").addQueryStringParameters())
		}
		/** Clears all completed tasks from the specified task list. The affected tasks will be marked as 'hidden' and no longer be returned by default when retrieving all tasks for a task list. */
		class clear(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Unit]) {
			val scopes = Seq("""https://www.googleapis.com/auth/tasks""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("POST")).map(_ => ())
		}
		object clear {
			def apply(tasklist: String)(using signer: RequestSigner, ec: ExecutionContext): clear = new clear(ws.url(BASE_URL + s"tasks/v1/lists/${tasklist}/clear").addQueryStringParameters())
			given Conversion[clear, Future[Unit]] = (fun: clear) => fun.apply()
		}
		/** Deletes the specified task from the task list. If the task is assigned, both the assigned task and the original task (in Docs, Chat Spaces) are deleted. To delete the assigned task only, navigate to the assignment surface and unassign the task from there. */
		class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Unit]) {
			val scopes = Seq("""https://www.googleapis.com/auth/tasks""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_ => ())
		}
		object delete {
			def apply(tasklist: String, task: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"tasks/v1/lists/${tasklist}/tasks/${task}").addQueryStringParameters())
			given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
		}
		/** Returns the specified task. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Task]) {
			val scopes = Seq("""https://www.googleapis.com/auth/tasks""", """https://www.googleapis.com/auth/tasks.readonly""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Task])
		}
		object get {
			def apply(tasklist: String, task: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"tasks/v1/lists/${tasklist}/tasks/${task}").addQueryStringParameters())
			given Conversion[get, Future[Schema.Task]] = (fun: get) => fun.apply()
		}
		/** Updates the specified task. */
		class update(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/tasks""")
			/** Perform the request */
			def withTask(body: Schema.Task) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.Task])
		}
		object update {
			def apply(task: String, tasklist: String)(using signer: RequestSigner, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"tasks/v1/lists/${tasklist}/tasks/${task}").addQueryStringParameters())
		}
		/** Updates the specified task. This method supports patch semantics. */
		class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/tasks""")
			/** Perform the request */
			def withTask(body: Schema.Task) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Task])
		}
		object patch {
			def apply(task: String, tasklist: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"tasks/v1/lists/${tasklist}/tasks/${task}").addQueryStringParameters())
		}
		/** Returns all tasks in the specified task list. Does not return assigned tasks be default (from Docs, Chat Spaces). A user can have up to 20,000 non-hidden tasks per list and up to 100,000 tasks in total at a time. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Tasks]) {
			val scopes = Seq("""https://www.googleapis.com/auth/tasks""", """https://www.googleapis.com/auth/tasks.readonly""")
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
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Tasks])
		}
		object list {
			def apply(tasklist: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"tasks/v1/lists/${tasklist}/tasks").addQueryStringParameters())
			given Conversion[list, Future[Schema.Tasks]] = (fun: list) => fun.apply()
		}
	}
}
