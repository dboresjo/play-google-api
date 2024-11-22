package com.boresjo.play.api.google.blogger

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
		"""https://www.googleapis.com/auth/blogger.readonly""" /* View your Blogger account */,
		"""https://www.googleapis.com/auth/blogger""" /* Manage your Blogger account */
	)

	private val BASE_URL = "https://blogger.googleapis.com/"

	object postUserInfos {
		/** Lists post and user info pairs. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.PostUserInfosList]) {
			val scopes = Seq("""https://www.googleapis.com/auth/blogger""", """https://www.googleapis.com/auth/blogger.readonly""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.PostUserInfosList])
		}
		object list {
			def apply(orderBy: String, startDate: String, fetchBodies: Boolean, endDate: String, blogId: String, pageToken: String, maxResults: Int, status: String, userId: String, labels: String, view: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v3/users/${userId}/blogs/${blogId}/posts").addQueryStringParameters("orderBy" -> orderBy.toString, "startDate" -> startDate.toString, "fetchBodies" -> fetchBodies.toString, "endDate" -> endDate.toString, "pageToken" -> pageToken.toString, "maxResults" -> maxResults.toString, "status" -> status.toString, "labels" -> labels.toString, "view" -> view.toString))
			given Conversion[list, Future[Schema.PostUserInfosList]] = (fun: list) => fun.apply()
		}
		/** Gets one post and user info pair, by post_id and user_id. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.PostUserInfo]) {
			val scopes = Seq("""https://www.googleapis.com/auth/blogger""", """https://www.googleapis.com/auth/blogger.readonly""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.PostUserInfo])
		}
		object get {
			def apply(maxComments: Int, postId: String, blogId: String, userId: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v3/users/${userId}/blogs/${blogId}/posts/${postId}").addQueryStringParameters("maxComments" -> maxComments.toString))
			given Conversion[get, Future[Schema.PostUserInfo]] = (fun: get) => fun.apply()
		}
	}
	object posts {
		/** Inserts a post. */
		class insert(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/blogger""")
			/** Perform the request */
			def withPost(body: Schema.Post) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Post])
		}
		object insert {
			def apply(fetchImages: Boolean, fetchBody: Boolean, isDraft: Boolean, blogId: String)(using signer: RequestSigner, ec: ExecutionContext): insert = new insert(ws.url(BASE_URL + s"v3/blogs/${blogId}/posts").addQueryStringParameters("fetchImages" -> fetchImages.toString, "fetchBody" -> fetchBody.toString, "isDraft" -> isDraft.toString))
		}
		/** Gets a post by path. */
		class getByPath(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Post]) {
			val scopes = Seq("""https://www.googleapis.com/auth/blogger""", """https://www.googleapis.com/auth/blogger.readonly""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Post])
		}
		object getByPath {
			def apply(maxComments: Int, blogId: String, view: String, path: String)(using signer: RequestSigner, ec: ExecutionContext): getByPath = new getByPath(ws.url(BASE_URL + s"v3/blogs/${blogId}/posts/bypath").addQueryStringParameters("maxComments" -> maxComments.toString, "view" -> view.toString, "path" -> path.toString))
			given Conversion[getByPath, Future[Schema.Post]] = (fun: getByPath) => fun.apply()
		}
		/** Publishes a post. */
		class publish(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Post]) {
			val scopes = Seq("""https://www.googleapis.com/auth/blogger""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("POST")).map(_.json.as[Schema.Post])
		}
		object publish {
			def apply(publishDate: String, postId: String, blogId: String)(using signer: RequestSigner, ec: ExecutionContext): publish = new publish(ws.url(BASE_URL + s"v3/blogs/${blogId}/posts/${postId}/publish").addQueryStringParameters("publishDate" -> publishDate.toString))
			given Conversion[publish, Future[Schema.Post]] = (fun: publish) => fun.apply()
		}
		/** Deletes a post by blog id and post id. */
		class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Unit]) {
			val scopes = Seq("""https://www.googleapis.com/auth/blogger""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_ => ())
		}
		object delete {
			def apply(useTrash: Boolean, postId: String, blogId: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v3/blogs/${blogId}/posts/${postId}").addQueryStringParameters("useTrash" -> useTrash.toString))
			given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
		}
		/** Gets a post by blog id and post id */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Post]) {
			val scopes = Seq("""https://www.googleapis.com/auth/blogger""", """https://www.googleapis.com/auth/blogger.readonly""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Post])
		}
		object get {
			def apply(fetchBody: Boolean, blogId: String, view: String, maxComments: Int, fetchImages: Boolean, postId: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v3/blogs/${blogId}/posts/${postId}").addQueryStringParameters("fetchBody" -> fetchBody.toString, "view" -> view.toString, "maxComments" -> maxComments.toString, "fetchImages" -> fetchImages.toString))
			given Conversion[get, Future[Schema.Post]] = (fun: get) => fun.apply()
		}
		/** Updates a post by blog id and post id. */
		class update(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/blogger""")
			/** Perform the request */
			def withPost(body: Schema.Post) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.Post])
		}
		object update {
			def apply(revert: Boolean, blogId: String, maxComments: Int, fetchBody: Boolean, postId: String, publish: Boolean, fetchImages: Boolean)(using signer: RequestSigner, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"v3/blogs/${blogId}/posts/${postId}").addQueryStringParameters("revert" -> revert.toString, "maxComments" -> maxComments.toString, "fetchBody" -> fetchBody.toString, "publish" -> publish.toString, "fetchImages" -> fetchImages.toString))
		}
		/** Patches a post. */
		class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/blogger""")
			/** Perform the request */
			def withPost(body: Schema.Post) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Post])
		}
		object patch {
			def apply(publish: Boolean, revert: Boolean, fetchImages: Boolean, postId: String, fetchBody: Boolean, blogId: String, maxComments: Int)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v3/blogs/${blogId}/posts/${postId}").addQueryStringParameters("publish" -> publish.toString, "revert" -> revert.toString, "fetchImages" -> fetchImages.toString, "fetchBody" -> fetchBody.toString, "maxComments" -> maxComments.toString))
		}
		/** Reverts a published or scheduled post to draft state. */
		class revert(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Post]) {
			val scopes = Seq("""https://www.googleapis.com/auth/blogger""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("POST")).map(_.json.as[Schema.Post])
		}
		object revert {
			def apply(postId: String, blogId: String)(using signer: RequestSigner, ec: ExecutionContext): revert = new revert(ws.url(BASE_URL + s"v3/blogs/${blogId}/posts/${postId}/revert").addQueryStringParameters())
			given Conversion[revert, Future[Schema.Post]] = (fun: revert) => fun.apply()
		}
		/** Lists posts. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.PostList]) {
			val scopes = Seq("""https://www.googleapis.com/auth/blogger""", """https://www.googleapis.com/auth/blogger.readonly""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.PostList])
		}
		object list {
			def apply(sortOption: String, pageToken: String, maxResults: Int, blogId: String, fetchBodies: Boolean, endDate: String, status: String, labels: String, startDate: String, fetchImages: Boolean, orderBy: String, view: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v3/blogs/${blogId}/posts").addQueryStringParameters("sortOption" -> sortOption.toString, "pageToken" -> pageToken.toString, "maxResults" -> maxResults.toString, "fetchBodies" -> fetchBodies.toString, "endDate" -> endDate.toString, "status" -> status.toString, "labels" -> labels.toString, "startDate" -> startDate.toString, "fetchImages" -> fetchImages.toString, "orderBy" -> orderBy.toString, "view" -> view.toString))
			given Conversion[list, Future[Schema.PostList]] = (fun: list) => fun.apply()
		}
		/** Searches for posts matching given query terms in the specified blog. */
		class search(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.PostList]) {
			val scopes = Seq("""https://www.googleapis.com/auth/blogger""", """https://www.googleapis.com/auth/blogger.readonly""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.PostList])
		}
		object search {
			def apply(fetchBodies: Boolean, q: String, blogId: String, orderBy: String)(using signer: RequestSigner, ec: ExecutionContext): search = new search(ws.url(BASE_URL + s"v3/blogs/${blogId}/posts/search").addQueryStringParameters("fetchBodies" -> fetchBodies.toString, "q" -> q.toString, "orderBy" -> orderBy.toString))
			given Conversion[search, Future[Schema.PostList]] = (fun: search) => fun.apply()
		}
	}
	object blogUserInfos {
		/** Gets one blog and user info pair by blog id and user id. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.BlogUserInfo]) {
			val scopes = Seq("""https://www.googleapis.com/auth/blogger""", """https://www.googleapis.com/auth/blogger.readonly""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.BlogUserInfo])
		}
		object get {
			def apply(userId: String, maxPosts: Int, blogId: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v3/users/${userId}/blogs/${blogId}").addQueryStringParameters("maxPosts" -> maxPosts.toString))
			given Conversion[get, Future[Schema.BlogUserInfo]] = (fun: get) => fun.apply()
		}
	}
	object blogs {
		/** Lists blogs by user. */
		class listByUser(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.BlogList]) {
			val scopes = Seq("""https://www.googleapis.com/auth/blogger""", """https://www.googleapis.com/auth/blogger.readonly""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.BlogList])
		}
		object listByUser {
			def apply(view: String, role: String, userId: String, status: String, fetchUserInfo: Boolean)(using signer: RequestSigner, ec: ExecutionContext): listByUser = new listByUser(ws.url(BASE_URL + s"v3/users/${userId}/blogs").addQueryStringParameters("view" -> view.toString, "role" -> role.toString, "status" -> status.toString, "fetchUserInfo" -> fetchUserInfo.toString))
			given Conversion[listByUser, Future[Schema.BlogList]] = (fun: listByUser) => fun.apply()
		}
		/** Gets a blog by url. */
		class getByUrl(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Blog]) {
			val scopes = Seq("""https://www.googleapis.com/auth/blogger""", """https://www.googleapis.com/auth/blogger.readonly""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Blog])
		}
		object getByUrl {
			def apply(view: String, url: String)(using signer: RequestSigner, ec: ExecutionContext): getByUrl = new getByUrl(ws.url(BASE_URL + s"v3/blogs/byurl").addQueryStringParameters("view" -> view.toString, "url" -> url.toString))
			given Conversion[getByUrl, Future[Schema.Blog]] = (fun: getByUrl) => fun.apply()
		}
		/** Gets a blog by id. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Blog]) {
			val scopes = Seq("""https://www.googleapis.com/auth/blogger""", """https://www.googleapis.com/auth/blogger.readonly""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Blog])
		}
		object get {
			def apply(view: String, maxPosts: Int, blogId: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v3/blogs/${blogId}").addQueryStringParameters("view" -> view.toString, "maxPosts" -> maxPosts.toString))
			given Conversion[get, Future[Schema.Blog]] = (fun: get) => fun.apply()
		}
	}
	object comments {
		/** Marks a comment as not spam by blog id, post id and comment id. */
		class approve(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Comment]) {
			val scopes = Seq("""https://www.googleapis.com/auth/blogger""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("POST")).map(_.json.as[Schema.Comment])
		}
		object approve {
			def apply(blogId: String, postId: String, commentId: String)(using signer: RequestSigner, ec: ExecutionContext): approve = new approve(ws.url(BASE_URL + s"v3/blogs/${blogId}/posts/${postId}/comments/${commentId}/approve").addQueryStringParameters())
			given Conversion[approve, Future[Schema.Comment]] = (fun: approve) => fun.apply()
		}
		/** Lists comments by blog. */
		class listByBlog(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.CommentList]) {
			val scopes = Seq("""https://www.googleapis.com/auth/blogger""", """https://www.googleapis.com/auth/blogger.readonly""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.CommentList])
		}
		object listByBlog {
			def apply(endDate: String, pageToken: String, startDate: String, blogId: String, status: String, maxResults: Int, fetchBodies: Boolean)(using signer: RequestSigner, ec: ExecutionContext): listByBlog = new listByBlog(ws.url(BASE_URL + s"v3/blogs/${blogId}/comments").addQueryStringParameters("endDate" -> endDate.toString, "pageToken" -> pageToken.toString, "startDate" -> startDate.toString, "status" -> status.toString, "maxResults" -> maxResults.toString, "fetchBodies" -> fetchBodies.toString))
			given Conversion[listByBlog, Future[Schema.CommentList]] = (fun: listByBlog) => fun.apply()
		}
		/** Deletes a comment by blog id, post id and comment id. */
		class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Unit]) {
			val scopes = Seq("""https://www.googleapis.com/auth/blogger""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_ => ())
		}
		object delete {
			def apply(commentId: String, postId: String, blogId: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v3/blogs/${blogId}/posts/${postId}/comments/${commentId}").addQueryStringParameters())
			given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
		}
		/** Removes the content of a comment by blog id, post id and comment id. */
		class removeContent(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Comment]) {
			val scopes = Seq("""https://www.googleapis.com/auth/blogger""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("POST")).map(_.json.as[Schema.Comment])
		}
		object removeContent {
			def apply(commentId: String, blogId: String, postId: String)(using signer: RequestSigner, ec: ExecutionContext): removeContent = new removeContent(ws.url(BASE_URL + s"v3/blogs/${blogId}/posts/${postId}/comments/${commentId}/removecontent").addQueryStringParameters())
			given Conversion[removeContent, Future[Schema.Comment]] = (fun: removeContent) => fun.apply()
		}
		/** Lists comments. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.CommentList]) {
			val scopes = Seq("""https://www.googleapis.com/auth/blogger""", """https://www.googleapis.com/auth/blogger.readonly""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.CommentList])
		}
		object list {
			def apply(fetchBodies: Boolean, pageToken: String, startDate: String, endDate: String, status: String, blogId: String, view: String, postId: String, maxResults: Int)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v3/blogs/${blogId}/posts/${postId}/comments").addQueryStringParameters("fetchBodies" -> fetchBodies.toString, "pageToken" -> pageToken.toString, "startDate" -> startDate.toString, "endDate" -> endDate.toString, "status" -> status.toString, "view" -> view.toString, "maxResults" -> maxResults.toString))
			given Conversion[list, Future[Schema.CommentList]] = (fun: list) => fun.apply()
		}
		/** Gets a comment by id. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Comment]) {
			val scopes = Seq("""https://www.googleapis.com/auth/blogger""", """https://www.googleapis.com/auth/blogger.readonly""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Comment])
		}
		object get {
			def apply(commentId: String, postId: String, blogId: String, view: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v3/blogs/${blogId}/posts/${postId}/comments/${commentId}").addQueryStringParameters("view" -> view.toString))
			given Conversion[get, Future[Schema.Comment]] = (fun: get) => fun.apply()
		}
		/** Marks a comment as spam by blog id, post id and comment id. */
		class markAsSpam(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Comment]) {
			val scopes = Seq("""https://www.googleapis.com/auth/blogger""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("POST")).map(_.json.as[Schema.Comment])
		}
		object markAsSpam {
			def apply(blogId: String, postId: String, commentId: String)(using signer: RequestSigner, ec: ExecutionContext): markAsSpam = new markAsSpam(ws.url(BASE_URL + s"v3/blogs/${blogId}/posts/${postId}/comments/${commentId}/spam").addQueryStringParameters())
			given Conversion[markAsSpam, Future[Schema.Comment]] = (fun: markAsSpam) => fun.apply()
		}
	}
	object users {
		/** Gets one user by user_id. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.User]) {
			val scopes = Seq("""https://www.googleapis.com/auth/blogger""", """https://www.googleapis.com/auth/blogger.readonly""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.User])
		}
		object get {
			def apply(userId: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v3/users/${userId}").addQueryStringParameters())
			given Conversion[get, Future[Schema.User]] = (fun: get) => fun.apply()
		}
	}
	object pages {
		/** Inserts a page. */
		class insert(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/blogger""")
			/** Perform the request */
			def withPage(body: Schema.Page) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Page])
		}
		object insert {
			def apply(isDraft: Boolean, blogId: String)(using signer: RequestSigner, ec: ExecutionContext): insert = new insert(ws.url(BASE_URL + s"v3/blogs/${blogId}/pages").addQueryStringParameters("isDraft" -> isDraft.toString))
		}
		/** Publishes a page. */
		class publish(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Page]) {
			val scopes = Seq("""https://www.googleapis.com/auth/blogger""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("POST")).map(_.json.as[Schema.Page])
		}
		object publish {
			def apply(blogId: String, pageId: String)(using signer: RequestSigner, ec: ExecutionContext): publish = new publish(ws.url(BASE_URL + s"v3/blogs/${blogId}/pages/${pageId}/publish").addQueryStringParameters())
			given Conversion[publish, Future[Schema.Page]] = (fun: publish) => fun.apply()
		}
		/** Deletes a page by blog id and page id. */
		class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Unit]) {
			val scopes = Seq("""https://www.googleapis.com/auth/blogger""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_ => ())
		}
		object delete {
			def apply(useTrash: Boolean, pageId: String, blogId: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v3/blogs/${blogId}/pages/${pageId}").addQueryStringParameters("useTrash" -> useTrash.toString))
			given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
		}
		/** Gets a page by blog id and page id. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Page]) {
			val scopes = Seq("""https://www.googleapis.com/auth/blogger""", """https://www.googleapis.com/auth/blogger.readonly""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Page])
		}
		object get {
			def apply(blogId: String, view: String, pageId: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v3/blogs/${blogId}/pages/${pageId}").addQueryStringParameters("view" -> view.toString))
			given Conversion[get, Future[Schema.Page]] = (fun: get) => fun.apply()
		}
		/** Updates a page by blog id and page id. */
		class update(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/blogger""")
			/** Perform the request */
			def withPage(body: Schema.Page) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.Page])
		}
		object update {
			def apply(blogId: String, revert: Boolean, pageId: String, publish: Boolean)(using signer: RequestSigner, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"v3/blogs/${blogId}/pages/${pageId}").addQueryStringParameters("revert" -> revert.toString, "publish" -> publish.toString))
		}
		/** Patches a page. */
		class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/blogger""")
			/** Perform the request */
			def withPage(body: Schema.Page) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Page])
		}
		object patch {
			def apply(pageId: String, publish: Boolean, blogId: String, revert: Boolean)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v3/blogs/${blogId}/pages/${pageId}").addQueryStringParameters("publish" -> publish.toString, "revert" -> revert.toString))
		}
		/** Reverts a published or scheduled page to draft state. */
		class revert(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Page]) {
			val scopes = Seq("""https://www.googleapis.com/auth/blogger""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("POST")).map(_.json.as[Schema.Page])
		}
		object revert {
			def apply(pageId: String, blogId: String)(using signer: RequestSigner, ec: ExecutionContext): revert = new revert(ws.url(BASE_URL + s"v3/blogs/${blogId}/pages/${pageId}/revert").addQueryStringParameters())
			given Conversion[revert, Future[Schema.Page]] = (fun: revert) => fun.apply()
		}
		/** Lists pages. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.PageList]) {
			val scopes = Seq("""https://www.googleapis.com/auth/blogger""", """https://www.googleapis.com/auth/blogger.readonly""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.PageList])
		}
		object list {
			def apply(maxResults: Int, view: String, pageToken: String, status: String, blogId: String, fetchBodies: Boolean)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v3/blogs/${blogId}/pages").addQueryStringParameters("maxResults" -> maxResults.toString, "view" -> view.toString, "pageToken" -> pageToken.toString, "status" -> status.toString, "fetchBodies" -> fetchBodies.toString))
			given Conversion[list, Future[Schema.PageList]] = (fun: list) => fun.apply()
		}
	}
	object pageViews {
		/** Gets page views by blog id. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Pageviews]) {
			val scopes = Seq("""https://www.googleapis.com/auth/blogger""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Pageviews])
		}
		object get {
			def apply(range: String, blogId: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v3/blogs/${blogId}/pageviews").addQueryStringParameters("range" -> range.toString))
			given Conversion[get, Future[Schema.Pageviews]] = (fun: get) => fun.apply()
		}
	}
}
