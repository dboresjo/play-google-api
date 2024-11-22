package com.boresjo.play.api.google.androidpublisher

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
		"""https://www.googleapis.com/auth/androidpublisher""" /* View and manage your Google Play Developer account */
	)

	private val BASE_URL = "https://androidpublisher.googleapis.com/"

	object users {
		/** Grant access for a user to the given developer account. */
		class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/androidpublisher""")
			/** Perform the request */
			def withUser(body: Schema.User) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.User])
		}
		object create {
			def apply(developersId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"androidpublisher/v3/developers/${developersId}/users").addQueryStringParameters("parent" -> parent.toString))
		}
		/** Lists all users with access to a developer account. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListUsersResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/androidpublisher""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListUsersResponse])
		}
		object list {
			def apply(developersId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"androidpublisher/v3/developers/${developersId}/users").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
			given Conversion[list, Future[Schema.ListUsersResponse]] = (fun: list) => fun.apply()
		}
		/** Updates access for the user to the developer account. */
		class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/androidpublisher""")
			/** Optional. The list of fields to be updated.<br>Format: google-fieldmask */
			def withUpdateMask(updateMask: String) = new patch(req.addQueryStringParameters("updateMask" -> updateMask.toString))
			/** Perform the request */
			def withUser(body: Schema.User) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.User])
		}
		object patch {
			def apply(developersId :PlayApi, usersId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"androidpublisher/v3/developers/${developersId}/users/${usersId}").addQueryStringParameters("name" -> name.toString))
		}
		/** Removes all access for the user to the given developer account. */
		class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Unit]) {
			val scopes = Seq("""https://www.googleapis.com/auth/androidpublisher""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_ => ())
		}
		object delete {
			def apply(developersId :PlayApi, usersId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"androidpublisher/v3/developers/${developersId}/users/${usersId}").addQueryStringParameters("name" -> name.toString))
			given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
		}
	}
	object grants {
		/** Grant access for a user to the given package. */
		class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/androidpublisher""")
			/** Perform the request */
			def withGrant(body: Schema.Grant) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Grant])
		}
		object create {
			def apply(developersId :PlayApi, usersId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"androidpublisher/v3/developers/${developersId}/users/${usersId}/grants").addQueryStringParameters("parent" -> parent.toString))
		}
		/** Updates access for the user to the given package. */
		class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/androidpublisher""")
			/** Optional. The list of fields to be updated.<br>Format: google-fieldmask */
			def withUpdateMask(updateMask: String) = new patch(req.addQueryStringParameters("updateMask" -> updateMask.toString))
			/** Perform the request */
			def withGrant(body: Schema.Grant) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Grant])
		}
		object patch {
			def apply(developersId :PlayApi, usersId :PlayApi, grantsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"androidpublisher/v3/developers/${developersId}/users/${usersId}/grants/${grantsId}").addQueryStringParameters("name" -> name.toString))
		}
		/** Removes all access for the user to the given package or developer account. */
		class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Unit]) {
			val scopes = Seq("""https://www.googleapis.com/auth/androidpublisher""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_ => ())
		}
		object delete {
			def apply(developersId :PlayApi, usersId :PlayApi, grantsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"androidpublisher/v3/developers/${developersId}/users/${usersId}/grants/${grantsId}").addQueryStringParameters("name" -> name.toString))
			given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
		}
	}
	object apprecovery {
		/** Cancel an already executing app recovery action. Note that this action changes status of the recovery action to CANCELED. */
		class cancel(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/androidpublisher""")
			/** Perform the request */
			def withCancelAppRecoveryRequest(body: Schema.CancelAppRecoveryRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.CancelAppRecoveryResponse])
		}
		object cancel {
			def apply(packageName: String, appRecoveryId: String)(using signer: RequestSigner, ec: ExecutionContext): cancel = new cancel(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/appRecoveries/${appRecoveryId}:cancel").addQueryStringParameters())
		}
		/** Create an app recovery action with recovery status as DRAFT. Note that this action does not execute the recovery action. */
		class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/androidpublisher""")
			/** Perform the request */
			def withCreateDraftAppRecoveryRequest(body: Schema.CreateDraftAppRecoveryRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.AppRecoveryAction])
		}
		object create {
			def apply(packageName: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/appRecoveries").addQueryStringParameters())
		}
		/** Incrementally update targeting for a recovery action. Note that only the criteria selected during the creation of recovery action can be expanded. */
		class addTargeting(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/androidpublisher""")
			/** Perform the request */
			def withAddTargetingRequest(body: Schema.AddTargetingRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.AddTargetingResponse])
		}
		object addTargeting {
			def apply(packageName: String, appRecoveryId: String)(using signer: RequestSigner, ec: ExecutionContext): addTargeting = new addTargeting(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/appRecoveries/${appRecoveryId}:addTargeting").addQueryStringParameters())
		}
		/** Deploy an already created app recovery action with recovery status DRAFT. Note that this action activates the recovery action for all targeted users and changes its status to ACTIVE. */
		class deploy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/androidpublisher""")
			/** Perform the request */
			def withDeployAppRecoveryRequest(body: Schema.DeployAppRecoveryRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.DeployAppRecoveryResponse])
		}
		object deploy {
			def apply(packageName: String, appRecoveryId: String)(using signer: RequestSigner, ec: ExecutionContext): deploy = new deploy(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/appRecoveries/${appRecoveryId}:deploy").addQueryStringParameters())
		}
		/** List all app recovery action resources associated with a particular package name and app version. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListAppRecoveriesResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/androidpublisher""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListAppRecoveriesResponse])
		}
		object list {
			def apply(packageName: String, versionCode: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/appRecoveries").addQueryStringParameters("versionCode" -> versionCode.toString))
			given Conversion[list, Future[Schema.ListAppRecoveriesResponse]] = (fun: list) => fun.apply()
		}
	}
	object edits {
		/** Commits an app edit. */
		class commit(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.AppEdit]) {
			val scopes = Seq("""https://www.googleapis.com/auth/androidpublisher""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("POST")).map(_.json.as[Schema.AppEdit])
		}
		object commit {
			def apply(packageName: String, editId: String, changesNotSentForReview: Boolean)(using signer: RequestSigner, ec: ExecutionContext): commit = new commit(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/edits/${editId}:commit").addQueryStringParameters("changesNotSentForReview" -> changesNotSentForReview.toString))
			given Conversion[commit, Future[Schema.AppEdit]] = (fun: commit) => fun.apply()
		}
		/** Validates an app edit. */
		class validate(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.AppEdit]) {
			val scopes = Seq("""https://www.googleapis.com/auth/androidpublisher""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("POST")).map(_.json.as[Schema.AppEdit])
		}
		object validate {
			def apply(packageName: String, editId: String)(using signer: RequestSigner, ec: ExecutionContext): validate = new validate(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/edits/${editId}:validate").addQueryStringParameters())
			given Conversion[validate, Future[Schema.AppEdit]] = (fun: validate) => fun.apply()
		}
		/** Deletes an app edit. */
		class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Unit]) {
			val scopes = Seq("""https://www.googleapis.com/auth/androidpublisher""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_ => ())
		}
		object delete {
			def apply(packageName: String, editId: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/edits/${editId}").addQueryStringParameters())
			given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
		}
		/** Gets an app edit. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.AppEdit]) {
			val scopes = Seq("""https://www.googleapis.com/auth/androidpublisher""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.AppEdit])
		}
		object get {
			def apply(packageName: String, editId: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/edits/${editId}").addQueryStringParameters())
			given Conversion[get, Future[Schema.AppEdit]] = (fun: get) => fun.apply()
		}
		/** Creates a new edit for an app. */
		class insert(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/androidpublisher""")
			/** Perform the request */
			def withAppEdit(body: Schema.AppEdit) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.AppEdit])
		}
		object insert {
			def apply(packageName: String)(using signer: RequestSigner, ec: ExecutionContext): insert = new insert(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/edits").addQueryStringParameters())
		}
		object expansionfiles {
			/** Fetches the expansion file configuration for the specified APK. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ExpansionFile]) {
				val scopes = Seq("""https://www.googleapis.com/auth/androidpublisher""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ExpansionFile])
			}
			object get {
				def apply(packageName: String, editId: String, apkVersionCode: Int, expansionFileType: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/edits/${editId}/apks/${apkVersionCode}/expansionFiles/${expansionFileType}").addQueryStringParameters())
				given Conversion[get, Future[Schema.ExpansionFile]] = (fun: get) => fun.apply()
			}
			/** Updates the APK's expansion file configuration to reference another APK's expansion file. To add a new expansion file use the Upload method. */
			class update(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/androidpublisher""")
				/** Perform the request */
				def withExpansionFile(body: Schema.ExpansionFile) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.ExpansionFile])
			}
			object update {
				def apply(packageName: String, editId: String, apkVersionCode: Int, expansionFileType: String)(using signer: RequestSigner, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/edits/${editId}/apks/${apkVersionCode}/expansionFiles/${expansionFileType}").addQueryStringParameters())
			}
			/** Patches the APK's expansion file configuration to reference another APK's expansion file. To add a new expansion file use the Upload method. */
			class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/androidpublisher""")
				/** Perform the request */
				def withExpansionFile(body: Schema.ExpansionFile) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.ExpansionFile])
			}
			object patch {
				def apply(packageName: String, editId: String, apkVersionCode: Int, expansionFileType: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/edits/${editId}/apks/${apkVersionCode}/expansionFiles/${expansionFileType}").addQueryStringParameters())
			}
			/** Uploads a new expansion file and attaches to the specified APK. */
			class upload(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ExpansionFilesUploadResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/androidpublisher""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("POST")).map(_.json.as[Schema.ExpansionFilesUploadResponse])
			}
			object upload {
				def apply(packageName: String, editId: String, apkVersionCode: Int, expansionFileType: String)(using signer: RequestSigner, ec: ExecutionContext): upload = new upload(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/edits/${editId}/apks/${apkVersionCode}/expansionFiles/${expansionFileType}").addQueryStringParameters())
				given Conversion[upload, Future[Schema.ExpansionFilesUploadResponse]] = (fun: upload) => fun.apply()
			}
		}
		object countryavailability {
			/** Gets country availability. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.TrackCountryAvailability]) {
				val scopes = Seq("""https://www.googleapis.com/auth/androidpublisher""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.TrackCountryAvailability])
			}
			object get {
				def apply(packageName: String, editId: String, track: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/edits/${editId}/countryAvailability/${track}").addQueryStringParameters())
				given Conversion[get, Future[Schema.TrackCountryAvailability]] = (fun: get) => fun.apply()
			}
		}
		object testers {
			/** Gets testers. Note: Testers resource does not support email lists. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Testers]) {
				val scopes = Seq("""https://www.googleapis.com/auth/androidpublisher""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Testers])
			}
			object get {
				def apply(packageName: String, editId: String, track: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/edits/${editId}/testers/${track}").addQueryStringParameters())
				given Conversion[get, Future[Schema.Testers]] = (fun: get) => fun.apply()
			}
			/** Updates testers. Note: Testers resource does not support email lists. */
			class update(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/androidpublisher""")
				/** Perform the request */
				def withTesters(body: Schema.Testers) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.Testers])
			}
			object update {
				def apply(packageName: String, editId: String, track: String)(using signer: RequestSigner, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/edits/${editId}/testers/${track}").addQueryStringParameters())
			}
			/** Patches testers. Note: Testers resource does not support email lists. */
			class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/androidpublisher""")
				/** Perform the request */
				def withTesters(body: Schema.Testers) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Testers])
			}
			object patch {
				def apply(packageName: String, editId: String, track: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/edits/${editId}/testers/${track}").addQueryStringParameters())
			}
		}
		object tracks {
			/** Creates a new track. */
			class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/androidpublisher""")
				/** Perform the request */
				def withTrackConfig(body: Schema.TrackConfig) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Track])
			}
			object create {
				def apply(packageName: String, editId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/edits/${editId}/tracks").addQueryStringParameters())
			}
			/** Gets a track. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Track]) {
				val scopes = Seq("""https://www.googleapis.com/auth/androidpublisher""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Track])
			}
			object get {
				def apply(packageName: String, editId: String, track: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/edits/${editId}/tracks/${track}").addQueryStringParameters())
				given Conversion[get, Future[Schema.Track]] = (fun: get) => fun.apply()
			}
			/** Updates a track. */
			class update(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/androidpublisher""")
				/** Perform the request */
				def withTrack(body: Schema.Track) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.Track])
			}
			object update {
				def apply(packageName: String, editId: String, track: String)(using signer: RequestSigner, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/edits/${editId}/tracks/${track}").addQueryStringParameters())
			}
			/** Patches a track. */
			class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/androidpublisher""")
				/** Perform the request */
				def withTrack(body: Schema.Track) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Track])
			}
			object patch {
				def apply(packageName: String, editId: String, track: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/edits/${editId}/tracks/${track}").addQueryStringParameters())
			}
			/** Lists all tracks. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.TracksListResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/androidpublisher""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.TracksListResponse])
			}
			object list {
				def apply(packageName: String, editId: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/edits/${editId}/tracks").addQueryStringParameters())
				given Conversion[list, Future[Schema.TracksListResponse]] = (fun: list) => fun.apply()
			}
		}
		object deobfuscationfiles {
			/** Uploads a new deobfuscation file and attaches to the specified APK. */
			class upload(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.DeobfuscationFilesUploadResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/androidpublisher""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("POST")).map(_.json.as[Schema.DeobfuscationFilesUploadResponse])
			}
			object upload {
				def apply(packageName: String, editId: String, apkVersionCode: Int, deobfuscationFileType: String)(using signer: RequestSigner, ec: ExecutionContext): upload = new upload(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/edits/${editId}/apks/${apkVersionCode}/deobfuscationFiles/${deobfuscationFileType}").addQueryStringParameters())
				given Conversion[upload, Future[Schema.DeobfuscationFilesUploadResponse]] = (fun: upload) => fun.apply()
			}
		}
		object images {
			/** Lists all images. The response may be empty. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ImagesListResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/androidpublisher""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ImagesListResponse])
			}
			object list {
				def apply(packageName: String, editId: String, language: String, imageType: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/edits/${editId}/listings/${language}/${imageType}").addQueryStringParameters())
				given Conversion[list, Future[Schema.ImagesListResponse]] = (fun: list) => fun.apply()
			}
			/** Deletes the image (specified by id) from the edit. */
			class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Unit]) {
				val scopes = Seq("""https://www.googleapis.com/auth/androidpublisher""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_ => ())
			}
			object delete {
				def apply(packageName: String, editId: String, language: String, imageType: String, imageId: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/edits/${editId}/listings/${language}/${imageType}/${imageId}").addQueryStringParameters())
				given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
			}
			/** Deletes all images for the specified language and image type. Returns an empty response if no images are found. */
			class deleteall(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ImagesDeleteAllResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/androidpublisher""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.ImagesDeleteAllResponse])
			}
			object deleteall {
				def apply(packageName: String, editId: String, language: String, imageType: String)(using signer: RequestSigner, ec: ExecutionContext): deleteall = new deleteall(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/edits/${editId}/listings/${language}/${imageType}").addQueryStringParameters())
				given Conversion[deleteall, Future[Schema.ImagesDeleteAllResponse]] = (fun: deleteall) => fun.apply()
			}
			/** Uploads an image of the specified language and image type, and adds to the edit. */
			class upload(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ImagesUploadResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/androidpublisher""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("POST")).map(_.json.as[Schema.ImagesUploadResponse])
			}
			object upload {
				def apply(packageName: String, editId: String, language: String, imageType: String)(using signer: RequestSigner, ec: ExecutionContext): upload = new upload(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/edits/${editId}/listings/${language}/${imageType}").addQueryStringParameters())
				given Conversion[upload, Future[Schema.ImagesUploadResponse]] = (fun: upload) => fun.apply()
			}
		}
		object listings {
			/** Deletes all store listings. */
			class deleteall(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Unit]) {
				val scopes = Seq("""https://www.googleapis.com/auth/androidpublisher""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_ => ())
			}
			object deleteall {
				def apply(packageName: String, editId: String)(using signer: RequestSigner, ec: ExecutionContext): deleteall = new deleteall(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/edits/${editId}/listings").addQueryStringParameters())
				given Conversion[deleteall, Future[Unit]] = (fun: deleteall) => fun.apply()
			}
			/** Deletes a localized store listing. */
			class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Unit]) {
				val scopes = Seq("""https://www.googleapis.com/auth/androidpublisher""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_ => ())
			}
			object delete {
				def apply(packageName: String, editId: String, language: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/edits/${editId}/listings/${language}").addQueryStringParameters())
				given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
			}
			/** Gets a localized store listing. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Listing]) {
				val scopes = Seq("""https://www.googleapis.com/auth/androidpublisher""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Listing])
			}
			object get {
				def apply(packageName: String, editId: String, language: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/edits/${editId}/listings/${language}").addQueryStringParameters())
				given Conversion[get, Future[Schema.Listing]] = (fun: get) => fun.apply()
			}
			/** Creates or updates a localized store listing. */
			class update(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/androidpublisher""")
				/** Perform the request */
				def withListing(body: Schema.Listing) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.Listing])
			}
			object update {
				def apply(packageName: String, editId: String, language: String)(using signer: RequestSigner, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/edits/${editId}/listings/${language}").addQueryStringParameters())
			}
			/** Patches a localized store listing. */
			class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/androidpublisher""")
				/** Perform the request */
				def withListing(body: Schema.Listing) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Listing])
			}
			object patch {
				def apply(packageName: String, editId: String, language: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/edits/${editId}/listings/${language}").addQueryStringParameters())
			}
			/** Lists all localized store listings. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListingsListResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/androidpublisher""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListingsListResponse])
			}
			object list {
				def apply(packageName: String, editId: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/edits/${editId}/listings").addQueryStringParameters())
				given Conversion[list, Future[Schema.ListingsListResponse]] = (fun: list) => fun.apply()
			}
		}
		object bundles {
			/** Lists all current Android App Bundles of the app and edit. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.BundlesListResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/androidpublisher""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.BundlesListResponse])
			}
			object list {
				def apply(packageName: String, editId: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/edits/${editId}/bundles").addQueryStringParameters())
				given Conversion[list, Future[Schema.BundlesListResponse]] = (fun: list) => fun.apply()
			}
			/** Uploads a new Android App Bundle to this edit. If you are using the Google API client libraries, please increase the timeout of the http request before calling this endpoint (a timeout of 2 minutes is recommended). See [Timeouts and Errors](https://developers.google.com/api-client-library/java/google-api-java-client/errors) for an example in java. */
			class upload(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Bundle]) {
				val scopes = Seq("""https://www.googleapis.com/auth/androidpublisher""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("POST")).map(_.json.as[Schema.Bundle])
			}
			object upload {
				def apply(packageName: String, editId: String, ackBundleInstallationWarning: Boolean, deviceTierConfigId: String)(using signer: RequestSigner, ec: ExecutionContext): upload = new upload(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/edits/${editId}/bundles").addQueryStringParameters("ackBundleInstallationWarning" -> ackBundleInstallationWarning.toString, "deviceTierConfigId" -> deviceTierConfigId.toString))
				given Conversion[upload, Future[Schema.Bundle]] = (fun: upload) => fun.apply()
			}
		}
		object apks {
			/** Uploads an APK and adds to the current edit. */
			class upload(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Apk]) {
				val scopes = Seq("""https://www.googleapis.com/auth/androidpublisher""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("POST")).map(_.json.as[Schema.Apk])
			}
			object upload {
				def apply(packageName: String, editId: String)(using signer: RequestSigner, ec: ExecutionContext): upload = new upload(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/edits/${editId}/apks").addQueryStringParameters())
				given Conversion[upload, Future[Schema.Apk]] = (fun: upload) => fun.apply()
			}
			/** Lists all current APKs of the app and edit. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ApksListResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/androidpublisher""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ApksListResponse])
			}
			object list {
				def apply(packageName: String, editId: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/edits/${editId}/apks").addQueryStringParameters())
				given Conversion[list, Future[Schema.ApksListResponse]] = (fun: list) => fun.apply()
			}
			/** Creates a new APK without uploading the APK itself to Google Play, instead hosting the APK at a specified URL. This function is only available to organizations using Managed Play whose application is configured to restrict distribution to the organizations. */
			class addexternallyhosted(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/androidpublisher""")
				/** Perform the request */
				def withApksAddExternallyHostedRequest(body: Schema.ApksAddExternallyHostedRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.ApksAddExternallyHostedResponse])
			}
			object addexternallyhosted {
				def apply(packageName: String, editId: String)(using signer: RequestSigner, ec: ExecutionContext): addexternallyhosted = new addexternallyhosted(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/edits/${editId}/apks/externallyHosted").addQueryStringParameters())
			}
		}
		object details {
			/** Gets details of an app. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.AppDetails]) {
				val scopes = Seq("""https://www.googleapis.com/auth/androidpublisher""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.AppDetails])
			}
			object get {
				def apply(packageName: String, editId: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/edits/${editId}/details").addQueryStringParameters())
				given Conversion[get, Future[Schema.AppDetails]] = (fun: get) => fun.apply()
			}
			/** Updates details of an app. */
			class update(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/androidpublisher""")
				/** Perform the request */
				def withAppDetails(body: Schema.AppDetails) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.AppDetails])
			}
			object update {
				def apply(packageName: String, editId: String)(using signer: RequestSigner, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/edits/${editId}/details").addQueryStringParameters())
			}
			/** Patches details of an app. */
			class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/androidpublisher""")
				/** Perform the request */
				def withAppDetails(body: Schema.AppDetails) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.AppDetails])
			}
			object patch {
				def apply(packageName: String, editId: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/edits/${editId}/details").addQueryStringParameters())
			}
		}
	}
	object externaltransactions {
		/** Creates a new external transaction. */
		class createexternaltransaction(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/androidpublisher""")
			/** Perform the request */
			def withExternalTransaction(body: Schema.ExternalTransaction) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.ExternalTransaction])
		}
		object createexternaltransaction {
			def apply(applicationsId :PlayApi, parent: String, externalTransactionId: String)(using signer: RequestSigner, ec: ExecutionContext): createexternaltransaction = new createexternaltransaction(ws.url(BASE_URL + s"androidpublisher/v3/applications/${applicationsId}/externalTransactions").addQueryStringParameters("parent" -> parent.toString, "externalTransactionId" -> externalTransactionId.toString))
		}
		/** Refunds or partially refunds an existing external transaction. */
		class refundexternaltransaction(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/androidpublisher""")
			/** Perform the request */
			def withRefundExternalTransactionRequest(body: Schema.RefundExternalTransactionRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.ExternalTransaction])
		}
		object refundexternaltransaction {
			def apply(applicationsId :PlayApi, externalTransactionsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): refundexternaltransaction = new refundexternaltransaction(ws.url(BASE_URL + s"androidpublisher/v3/applications/${applicationsId}/externalTransactions/${externalTransactionsId}:refund").addQueryStringParameters("name" -> name.toString))
		}
		/** Gets an existing external transaction. */
		class getexternaltransaction(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ExternalTransaction]) {
			val scopes = Seq("""https://www.googleapis.com/auth/androidpublisher""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ExternalTransaction])
		}
		object getexternaltransaction {
			def apply(applicationsId :PlayApi, externalTransactionsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): getexternaltransaction = new getexternaltransaction(ws.url(BASE_URL + s"androidpublisher/v3/applications/${applicationsId}/externalTransactions/${externalTransactionsId}").addQueryStringParameters("name" -> name.toString))
			given Conversion[getexternaltransaction, Future[Schema.ExternalTransaction]] = (fun: getexternaltransaction) => fun.apply()
		}
	}
	object generatedapks {
		/** Returns download metadata for all APKs that were generated from a given app bundle. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GeneratedApksListResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/androidpublisher""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GeneratedApksListResponse])
		}
		object list {
			def apply(packageName: String, versionCode: Int)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/generatedApks/${versionCode}").addQueryStringParameters())
			given Conversion[list, Future[Schema.GeneratedApksListResponse]] = (fun: list) => fun.apply()
		}
		/** Downloads a single signed APK generated from an app bundle. */
		class download(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Unit]) {
			val scopes = Seq("""https://www.googleapis.com/auth/androidpublisher""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_ => ())
		}
		object download {
			def apply(packageName: String, versionCode: Int, downloadId: String)(using signer: RequestSigner, ec: ExecutionContext): download = new download(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/generatedApks/${versionCode}/downloads/${downloadId}:download").addQueryStringParameters())
			given Conversion[download, Future[Unit]] = (fun: download) => fun.apply()
		}
	}
	object inappproducts {
		/** Creates an in-app product (a managed product or a subscription). This method should no longer be used to create subscriptions. See [this article](https://android-developers.googleblog.com/2023/06/changes-to-google-play-developer-api-june-2023.html) for more information. */
		class insert(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/androidpublisher""")
			/** Perform the request */
			def withInAppProduct(body: Schema.InAppProduct) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.InAppProduct])
		}
		object insert {
			def apply(packageName: String, autoConvertMissingPrices: Boolean)(using signer: RequestSigner, ec: ExecutionContext): insert = new insert(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/inappproducts").addQueryStringParameters("autoConvertMissingPrices" -> autoConvertMissingPrices.toString))
		}
		/** Updates or inserts one or more in-app products (managed products or subscriptions). Set the latencyTolerance field on nested requests to PRODUCT_UPDATE_LATENCY_TOLERANCE_LATENCY_TOLERANT to achieve maximum update throughput. This method should no longer be used to update subscriptions. See [this article](https://android-developers.googleblog.com/2023/06/changes-to-google-play-developer-api-june-2023.html) for more information. */
		class batchUpdate(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/androidpublisher""")
			/** Perform the request */
			def withInappproductsBatchUpdateRequest(body: Schema.InappproductsBatchUpdateRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.InappproductsBatchUpdateResponse])
		}
		object batchUpdate {
			def apply(packageName: String)(using signer: RequestSigner, ec: ExecutionContext): batchUpdate = new batchUpdate(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/inappproducts:batchUpdate").addQueryStringParameters())
		}
		/** Deletes in-app products (managed products or subscriptions). Set the latencyTolerance field on nested requests to PRODUCT_UPDATE_LATENCY_TOLERANCE_LATENCY_TOLERANT to achieve maximum update throughput. This method should not be used to delete subscriptions. See [this article](https://android-developers.googleblog.com/2023/06/changes-to-google-play-developer-api-june-2023.html) for more information. */
		class batchDelete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/androidpublisher""")
			/** Perform the request */
			def withInappproductsBatchDeleteRequest(body: Schema.InappproductsBatchDeleteRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_ => ())
		}
		object batchDelete {
			def apply(packageName: String)(using signer: RequestSigner, ec: ExecutionContext): batchDelete = new batchDelete(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/inappproducts:batchDelete").addQueryStringParameters())
		}
		/** Deletes an in-app product (a managed product or a subscription). This method should no longer be used to delete subscriptions. See [this article](https://android-developers.googleblog.com/2023/06/changes-to-google-play-developer-api-june-2023.html) for more information. */
		class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Unit]) {
			val scopes = Seq("""https://www.googleapis.com/auth/androidpublisher""")
			/** Optional. The latency tolerance for the propagation of this product update. Defaults to latency-sensitive.<br>Possible values:<br>PRODUCT_UPDATE_LATENCY_TOLERANCE_UNSPECIFIED: Defaults to PRODUCT_UPDATE_LATENCY_TOLERANCE_LATENCY_SENSITIVE.<br>PRODUCT_UPDATE_LATENCY_TOLERANCE_LATENCY_SENSITIVE: The update will propagate to clients within several minutes on average and up to a few hours in rare cases. Throughput is limited to 7,200 updates per app per hour.<br>PRODUCT_UPDATE_LATENCY_TOLERANCE_LATENCY_TOLERANT: The update will propagate to clients within 24 hours. Supports high throughput of up to 720,000 updates per app per hour using batch modification methods. */
			def withLatencyTolerance(latencyTolerance: String) = new delete(req.addQueryStringParameters("latencyTolerance" -> latencyTolerance.toString))
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_ => ())
		}
		object delete {
			def apply(packageName: String, sku: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/inappproducts/${sku}").addQueryStringParameters())
			given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
		}
		/** Gets an in-app product, which can be a managed product or a subscription. This method should no longer be used to retrieve subscriptions. See [this article](https://android-developers.googleblog.com/2023/06/changes-to-google-play-developer-api-june-2023.html) for more information. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.InAppProduct]) {
			val scopes = Seq("""https://www.googleapis.com/auth/androidpublisher""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.InAppProduct])
		}
		object get {
			def apply(packageName: String, sku: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/inappproducts/${sku}").addQueryStringParameters())
			given Conversion[get, Future[Schema.InAppProduct]] = (fun: get) => fun.apply()
		}
		/** Updates an in-app product (a managed product or a subscription). This method should no longer be used to update subscriptions. See [this article](https://android-developers.googleblog.com/2023/06/changes-to-google-play-developer-api-june-2023.html) for more information. */
		class update(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/androidpublisher""")
			/** Optional. The latency tolerance for the propagation of this product update. Defaults to latency-sensitive.<br>Possible values:<br>PRODUCT_UPDATE_LATENCY_TOLERANCE_UNSPECIFIED: Defaults to PRODUCT_UPDATE_LATENCY_TOLERANCE_LATENCY_SENSITIVE.<br>PRODUCT_UPDATE_LATENCY_TOLERANCE_LATENCY_SENSITIVE: The update will propagate to clients within several minutes on average and up to a few hours in rare cases. Throughput is limited to 7,200 updates per app per hour.<br>PRODUCT_UPDATE_LATENCY_TOLERANCE_LATENCY_TOLERANT: The update will propagate to clients within 24 hours. Supports high throughput of up to 720,000 updates per app per hour using batch modification methods. */
			def withLatencyTolerance(latencyTolerance: String) = new update(req.addQueryStringParameters("latencyTolerance" -> latencyTolerance.toString))
			/** Perform the request */
			def withInAppProduct(body: Schema.InAppProduct) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.InAppProduct])
		}
		object update {
			def apply(packageName: String, sku: String, autoConvertMissingPrices: Boolean, allowMissing: Boolean)(using signer: RequestSigner, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/inappproducts/${sku}").addQueryStringParameters("autoConvertMissingPrices" -> autoConvertMissingPrices.toString, "allowMissing" -> allowMissing.toString))
		}
		/** Patches an in-app product (a managed product or a subscription). This method should no longer be used to update subscriptions. See [this article](https://android-developers.googleblog.com/2023/06/changes-to-google-play-developer-api-june-2023.html) for more information. */
		class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/androidpublisher""")
			/** Optional. The latency tolerance for the propagation of this product update. Defaults to latency-sensitive.<br>Possible values:<br>PRODUCT_UPDATE_LATENCY_TOLERANCE_UNSPECIFIED: Defaults to PRODUCT_UPDATE_LATENCY_TOLERANCE_LATENCY_SENSITIVE.<br>PRODUCT_UPDATE_LATENCY_TOLERANCE_LATENCY_SENSITIVE: The update will propagate to clients within several minutes on average and up to a few hours in rare cases. Throughput is limited to 7,200 updates per app per hour.<br>PRODUCT_UPDATE_LATENCY_TOLERANCE_LATENCY_TOLERANT: The update will propagate to clients within 24 hours. Supports high throughput of up to 720,000 updates per app per hour using batch modification methods. */
			def withLatencyTolerance(latencyTolerance: String) = new patch(req.addQueryStringParameters("latencyTolerance" -> latencyTolerance.toString))
			/** Perform the request */
			def withInAppProduct(body: Schema.InAppProduct) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.InAppProduct])
		}
		object patch {
			def apply(packageName: String, sku: String, autoConvertMissingPrices: Boolean)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/inappproducts/${sku}").addQueryStringParameters("autoConvertMissingPrices" -> autoConvertMissingPrices.toString))
		}
		/** Reads multiple in-app products, which can be managed products or subscriptions. This method should not be used to retrieve subscriptions. See [this article](https://android-developers.googleblog.com/2023/06/changes-to-google-play-developer-api-june-2023.html) for more information. */
		class batchGet(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.InappproductsBatchGetResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/androidpublisher""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.InappproductsBatchGetResponse])
		}
		object batchGet {
			def apply(packageName: String, sku: String)(using signer: RequestSigner, ec: ExecutionContext): batchGet = new batchGet(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/inappproducts:batchGet").addQueryStringParameters("sku" -> sku.toString))
			given Conversion[batchGet, Future[Schema.InappproductsBatchGetResponse]] = (fun: batchGet) => fun.apply()
		}
		/** Lists all in-app products - both managed products and subscriptions. If an app has a large number of in-app products, the response may be paginated. In this case the response field `tokenPagination.nextPageToken` will be set and the caller should provide its value as a `token` request parameter to retrieve the next page. This method should no longer be used to retrieve subscriptions. See [this article](https://android-developers.googleblog.com/2023/06/changes-to-google-play-developer-api-june-2023.html) for more information. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.InappproductsListResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/androidpublisher""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.InappproductsListResponse])
		}
		object list {
			def apply(packageName: String, token: String, startIndex: Int, maxResults: Int)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/inappproducts").addQueryStringParameters("token" -> token.toString, "startIndex" -> startIndex.toString, "maxResults" -> maxResults.toString))
			given Conversion[list, Future[Schema.InappproductsListResponse]] = (fun: list) => fun.apply()
		}
	}
	object internalappsharingartifacts {
		/** Uploads an APK to internal app sharing. If you are using the Google API client libraries, please increase the timeout of the http request before calling this endpoint (a timeout of 2 minutes is recommended). See [Timeouts and Errors](https://developers.google.com/api-client-library/java/google-api-java-client/errors) for an example in java. */
		class uploadapk(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.InternalAppSharingArtifact]) {
			val scopes = Seq("""https://www.googleapis.com/auth/androidpublisher""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("POST")).map(_.json.as[Schema.InternalAppSharingArtifact])
		}
		object uploadapk {
			def apply(packageName: String)(using signer: RequestSigner, ec: ExecutionContext): uploadapk = new uploadapk(ws.url(BASE_URL + s"androidpublisher/v3/applications/internalappsharing/${packageName}/artifacts/apk").addQueryStringParameters())
			given Conversion[uploadapk, Future[Schema.InternalAppSharingArtifact]] = (fun: uploadapk) => fun.apply()
		}
		/** Uploads an app bundle to internal app sharing. If you are using the Google API client libraries, please increase the timeout of the http request before calling this endpoint (a timeout of 2 minutes is recommended). See [Timeouts and Errors](https://developers.google.com/api-client-library/java/google-api-java-client/errors) for an example in java. */
		class uploadbundle(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.InternalAppSharingArtifact]) {
			val scopes = Seq("""https://www.googleapis.com/auth/androidpublisher""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("POST")).map(_.json.as[Schema.InternalAppSharingArtifact])
		}
		object uploadbundle {
			def apply(packageName: String)(using signer: RequestSigner, ec: ExecutionContext): uploadbundle = new uploadbundle(ws.url(BASE_URL + s"androidpublisher/v3/applications/internalappsharing/${packageName}/artifacts/bundle").addQueryStringParameters())
			given Conversion[uploadbundle, Future[Schema.InternalAppSharingArtifact]] = (fun: uploadbundle) => fun.apply()
		}
	}
	object orders {
		/** Refunds a user's subscription or in-app purchase order. Orders older than 3 years cannot be refunded. */
		class refund(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Unit]) {
			val scopes = Seq("""https://www.googleapis.com/auth/androidpublisher""")
			/** Whether to revoke the purchased item. If set to true, access to the subscription or in-app item will be terminated immediately. If the item is a recurring subscription, all future payments will also be terminated. Consumed in-app items need to be handled by developer's app. (optional). */
			def withRevoke(revoke: Boolean) = new refund(req.addQueryStringParameters("revoke" -> revoke.toString))
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("POST")).map(_ => ())
		}
		object refund {
			def apply(packageName: String, orderId: String)(using signer: RequestSigner, ec: ExecutionContext): refund = new refund(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/orders/${orderId}:refund").addQueryStringParameters())
			given Conversion[refund, Future[Unit]] = (fun: refund) => fun.apply()
		}
	}
	object applications {
		/** Writes the Safety Labels declaration of an app. */
		class dataSafety(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/androidpublisher""")
			/** Perform the request */
			def withSafetyLabelsUpdateRequest(body: Schema.SafetyLabelsUpdateRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.SafetyLabelsUpdateResponse])
		}
		object dataSafety {
			def apply(packageName: String)(using signer: RequestSigner, ec: ExecutionContext): dataSafety = new dataSafety(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/dataSafety").addQueryStringParameters())
		}
		object deviceTierConfigs {
			/** Creates a new device tier config for an app. */
			class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/androidpublisher""")
				/** Perform the request */
				def withDeviceTierConfig(body: Schema.DeviceTierConfig) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.DeviceTierConfig])
			}
			object create {
				def apply(packageName: String, allowUnknownDevices: Boolean)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/deviceTierConfigs").addQueryStringParameters("allowUnknownDevices" -> allowUnknownDevices.toString))
			}
			/** Returns a particular device tier config. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.DeviceTierConfig]) {
				val scopes = Seq("""https://www.googleapis.com/auth/androidpublisher""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.DeviceTierConfig])
			}
			object get {
				def apply(packageName: String, deviceTierConfigId: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/deviceTierConfigs/${deviceTierConfigId}").addQueryStringParameters())
				given Conversion[get, Future[Schema.DeviceTierConfig]] = (fun: get) => fun.apply()
			}
			/** Returns created device tier configs, ordered by descending creation time. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListDeviceTierConfigsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/androidpublisher""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListDeviceTierConfigsResponse])
			}
			object list {
				def apply(packageName: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/deviceTierConfigs").addQueryStringParameters("pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListDeviceTierConfigsResponse]] = (fun: list) => fun.apply()
			}
		}
	}
	object monetization {
		/** Calculates the region prices, using today's exchange rate and country-specific pricing patterns, based on the price in the request for a set of regions. */
		class convertRegionPrices(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/androidpublisher""")
			/** Perform the request */
			def withConvertRegionPricesRequest(body: Schema.ConvertRegionPricesRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.ConvertRegionPricesResponse])
		}
		object convertRegionPrices {
			def apply(packageName: String)(using signer: RequestSigner, ec: ExecutionContext): convertRegionPrices = new convertRegionPrices(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/pricing:convertRegionPrices").addQueryStringParameters())
		}
		object subscriptions {
			/** Creates a new subscription. Newly added base plans will remain in draft state until activated. */
			class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/androidpublisher""")
				/** Perform the request */
				def withSubscription(body: Schema.Subscription) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Subscription])
			}
			object create {
				def apply(packageName: String, productId: String, regionsVersionVersion: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/subscriptions").addQueryStringParameters("productId" -> productId.toString, "regionsVersion.version" -> regionsVersionVersion.toString))
			}
			/** Updates a batch of subscriptions. Set the latencyTolerance field on nested requests to PRODUCT_UPDATE_LATENCY_TOLERANCE_LATENCY_TOLERANT to achieve maximum update throughput. */
			class batchUpdate(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/androidpublisher""")
				/** Perform the request */
				def withBatchUpdateSubscriptionsRequest(body: Schema.BatchUpdateSubscriptionsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.BatchUpdateSubscriptionsResponse])
			}
			object batchUpdate {
				def apply(packageName: String)(using signer: RequestSigner, ec: ExecutionContext): batchUpdate = new batchUpdate(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/subscriptions:batchUpdate").addQueryStringParameters())
			}
			/** Deletes a subscription. A subscription can only be deleted if it has never had a base plan published. */
			class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Unit]) {
				val scopes = Seq("""https://www.googleapis.com/auth/androidpublisher""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_ => ())
			}
			object delete {
				def apply(packageName: String, productId: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/subscriptions/${productId}").addQueryStringParameters())
				given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
			}
			/** Reads a single subscription. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Subscription]) {
				val scopes = Seq("""https://www.googleapis.com/auth/androidpublisher""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Subscription])
			}
			object get {
				def apply(packageName: String, productId: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/subscriptions/${productId}").addQueryStringParameters())
				given Conversion[get, Future[Schema.Subscription]] = (fun: get) => fun.apply()
			}
			/** Updates an existing subscription. */
			class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/androidpublisher""")
				/** Optional. If set to true, and the subscription with the given package_name and product_id doesn't exist, the subscription will be created. If a new subscription is created, update_mask is ignored. */
				def withAllowMissing(allowMissing: Boolean) = new patch(req.addQueryStringParameters("allowMissing" -> allowMissing.toString))
				/** Optional. The latency tolerance for the propagation of this product update. Defaults to latency-sensitive.<br>Possible values:<br>PRODUCT_UPDATE_LATENCY_TOLERANCE_UNSPECIFIED: Defaults to PRODUCT_UPDATE_LATENCY_TOLERANCE_LATENCY_SENSITIVE.<br>PRODUCT_UPDATE_LATENCY_TOLERANCE_LATENCY_SENSITIVE: The update will propagate to clients within several minutes on average and up to a few hours in rare cases. Throughput is limited to 7,200 updates per app per hour.<br>PRODUCT_UPDATE_LATENCY_TOLERANCE_LATENCY_TOLERANT: The update will propagate to clients within 24 hours. Supports high throughput of up to 720,000 updates per app per hour using batch modification methods. */
				def withLatencyTolerance(latencyTolerance: String) = new patch(req.addQueryStringParameters("latencyTolerance" -> latencyTolerance.toString))
				/** Perform the request */
				def withSubscription(body: Schema.Subscription) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Subscription])
			}
			object patch {
				def apply(packageName: String, productId: String, updateMask: String, regionsVersionVersion: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/subscriptions/${productId}").addQueryStringParameters("updateMask" -> updateMask.toString, "regionsVersion.version" -> regionsVersionVersion.toString))
			}
			/** Lists all subscriptions under a given app. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListSubscriptionsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/androidpublisher""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListSubscriptionsResponse])
			}
			object list {
				def apply(packageName: String, pageSize: Int, pageToken: String, showArchived: Boolean)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/subscriptions").addQueryStringParameters("pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "showArchived" -> showArchived.toString))
				given Conversion[list, Future[Schema.ListSubscriptionsResponse]] = (fun: list) => fun.apply()
			}
			/** Reads one or more subscriptions. */
			class batchGet(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.BatchGetSubscriptionsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/androidpublisher""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.BatchGetSubscriptionsResponse])
			}
			object batchGet {
				def apply(packageName: String, productIds: String)(using signer: RequestSigner, ec: ExecutionContext): batchGet = new batchGet(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/subscriptions:batchGet").addQueryStringParameters("productIds" -> productIds.toString))
				given Conversion[batchGet, Future[Schema.BatchGetSubscriptionsResponse]] = (fun: batchGet) => fun.apply()
			}
			/** Deprecated: subscription archiving is not supported. */
			class archive(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/androidpublisher""")
				/** Perform the request */
				def withArchiveSubscriptionRequest(body: Schema.ArchiveSubscriptionRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Subscription])
			}
			object archive {
				def apply(packageName: String, productId: String)(using signer: RequestSigner, ec: ExecutionContext): archive = new archive(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/subscriptions/${productId}:archive").addQueryStringParameters())
			}
			object basePlans {
				/** Deactivates a base plan. Once deactivated, the base plan will become unavailable to new subscribers, but existing subscribers will maintain their subscription */
				class deactivate(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/androidpublisher""")
					/** Perform the request */
					def withDeactivateBasePlanRequest(body: Schema.DeactivateBasePlanRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Subscription])
				}
				object deactivate {
					def apply(packageName: String, productId: String, basePlanId: String)(using signer: RequestSigner, ec: ExecutionContext): deactivate = new deactivate(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/subscriptions/${productId}/basePlans/${basePlanId}:deactivate").addQueryStringParameters())
				}
				/** Migrates subscribers from one or more legacy price cohorts to the current price. Requests result in Google Play notifying affected subscribers. Only up to 250 simultaneous legacy price cohorts are supported. */
				class migratePrices(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/androidpublisher""")
					/** Perform the request */
					def withMigrateBasePlanPricesRequest(body: Schema.MigrateBasePlanPricesRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.MigrateBasePlanPricesResponse])
				}
				object migratePrices {
					def apply(packageName: String, productId: String, basePlanId: String)(using signer: RequestSigner, ec: ExecutionContext): migratePrices = new migratePrices(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/subscriptions/${productId}/basePlans/${basePlanId}:migratePrices").addQueryStringParameters())
				}
				/** Activates or deactivates base plans across one or multiple subscriptions. Set the latencyTolerance field on nested requests to PRODUCT_UPDATE_LATENCY_TOLERANCE_LATENCY_TOLERANT to achieve maximum update throughput. */
				class batchUpdateStates(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/androidpublisher""")
					/** Perform the request */
					def withBatchUpdateBasePlanStatesRequest(body: Schema.BatchUpdateBasePlanStatesRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.BatchUpdateBasePlanStatesResponse])
				}
				object batchUpdateStates {
					def apply(packageName: String, productId: String)(using signer: RequestSigner, ec: ExecutionContext): batchUpdateStates = new batchUpdateStates(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/subscriptions/${productId}/basePlans:batchUpdateStates").addQueryStringParameters())
				}
				/** Batch variant of the MigrateBasePlanPrices endpoint. Set the latencyTolerance field on nested requests to PRODUCT_UPDATE_LATENCY_TOLERANCE_LATENCY_TOLERANT to achieve maximum update throughput. */
				class batchMigratePrices(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/androidpublisher""")
					/** Perform the request */
					def withBatchMigrateBasePlanPricesRequest(body: Schema.BatchMigrateBasePlanPricesRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.BatchMigrateBasePlanPricesResponse])
				}
				object batchMigratePrices {
					def apply(packageName: String, productId: String)(using signer: RequestSigner, ec: ExecutionContext): batchMigratePrices = new batchMigratePrices(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/subscriptions/${productId}/basePlans:batchMigratePrices").addQueryStringParameters())
				}
				/** Deletes a base plan. Can only be done for draft base plans. This action is irreversible. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Unit]) {
					val scopes = Seq("""https://www.googleapis.com/auth/androidpublisher""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_ => ())
				}
				object delete {
					def apply(packageName: String, productId: String, basePlanId: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/subscriptions/${productId}/basePlans/${basePlanId}").addQueryStringParameters())
					given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
				}
				/** Activates a base plan. Once activated, base plans will be available to new subscribers. */
				class activate(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/androidpublisher""")
					/** Perform the request */
					def withActivateBasePlanRequest(body: Schema.ActivateBasePlanRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Subscription])
				}
				object activate {
					def apply(packageName: String, productId: String, basePlanId: String)(using signer: RequestSigner, ec: ExecutionContext): activate = new activate(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/subscriptions/${productId}/basePlans/${basePlanId}:activate").addQueryStringParameters())
				}
				object offers {
					/** Deactivates a subscription offer. Once deactivated, existing subscribers will maintain their subscription, but the offer will become unavailable to new subscribers. */
					class deactivate(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/androidpublisher""")
						/** Perform the request */
						def withDeactivateSubscriptionOfferRequest(body: Schema.DeactivateSubscriptionOfferRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.SubscriptionOffer])
					}
					object deactivate {
						def apply(packageName: String, productId: String, basePlanId: String, offerId: String)(using signer: RequestSigner, ec: ExecutionContext): deactivate = new deactivate(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/subscriptions/${productId}/basePlans/${basePlanId}/offers/${offerId}:deactivate").addQueryStringParameters())
					}
					/** Creates a new subscription offer. Only auto-renewing base plans can have subscription offers. The offer state will be DRAFT until it is activated. */
					class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/androidpublisher""")
						/** Perform the request */
						def withSubscriptionOffer(body: Schema.SubscriptionOffer) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.SubscriptionOffer])
					}
					object create {
						def apply(packageName: String, productId: String, basePlanId: String, offerId: String, regionsVersionVersion: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/subscriptions/${productId}/basePlans/${basePlanId}/offers").addQueryStringParameters("offerId" -> offerId.toString, "regionsVersion.version" -> regionsVersionVersion.toString))
					}
					/** Updates a batch of subscription offer states. Set the latencyTolerance field on nested requests to PRODUCT_UPDATE_LATENCY_TOLERANCE_LATENCY_TOLERANT to achieve maximum update throughput. */
					class batchUpdateStates(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/androidpublisher""")
						/** Perform the request */
						def withBatchUpdateSubscriptionOfferStatesRequest(body: Schema.BatchUpdateSubscriptionOfferStatesRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.BatchUpdateSubscriptionOfferStatesResponse])
					}
					object batchUpdateStates {
						def apply(packageName: String, productId: String, basePlanId: String)(using signer: RequestSigner, ec: ExecutionContext): batchUpdateStates = new batchUpdateStates(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/subscriptions/${productId}/basePlans/${basePlanId}/offers:batchUpdateStates").addQueryStringParameters())
					}
					/** Updates a batch of subscription offers. Set the latencyTolerance field on nested requests to PRODUCT_UPDATE_LATENCY_TOLERANCE_LATENCY_TOLERANT to achieve maximum update throughput. */
					class batchUpdate(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/androidpublisher""")
						/** Perform the request */
						def withBatchUpdateSubscriptionOffersRequest(body: Schema.BatchUpdateSubscriptionOffersRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.BatchUpdateSubscriptionOffersResponse])
					}
					object batchUpdate {
						def apply(packageName: String, productId: String, basePlanId: String)(using signer: RequestSigner, ec: ExecutionContext): batchUpdate = new batchUpdate(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/subscriptions/${productId}/basePlans/${basePlanId}/offers:batchUpdate").addQueryStringParameters())
					}
					/** Deletes a subscription offer. Can only be done for draft offers. This action is irreversible. */
					class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Unit]) {
						val scopes = Seq("""https://www.googleapis.com/auth/androidpublisher""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_ => ())
					}
					object delete {
						def apply(packageName: String, productId: String, basePlanId: String, offerId: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/subscriptions/${productId}/basePlans/${basePlanId}/offers/${offerId}").addQueryStringParameters())
						given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
					}
					/** Reads a single offer */
					class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.SubscriptionOffer]) {
						val scopes = Seq("""https://www.googleapis.com/auth/androidpublisher""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.SubscriptionOffer])
					}
					object get {
						def apply(packageName: String, productId: String, basePlanId: String, offerId: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/subscriptions/${productId}/basePlans/${basePlanId}/offers/${offerId}").addQueryStringParameters())
						given Conversion[get, Future[Schema.SubscriptionOffer]] = (fun: get) => fun.apply()
					}
					/** Activates a subscription offer. Once activated, subscription offers will be available to new subscribers. */
					class activate(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/androidpublisher""")
						/** Perform the request */
						def withActivateSubscriptionOfferRequest(body: Schema.ActivateSubscriptionOfferRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.SubscriptionOffer])
					}
					object activate {
						def apply(packageName: String, productId: String, basePlanId: String, offerId: String)(using signer: RequestSigner, ec: ExecutionContext): activate = new activate(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/subscriptions/${productId}/basePlans/${basePlanId}/offers/${offerId}:activate").addQueryStringParameters())
					}
					/** Updates an existing subscription offer. */
					class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/androidpublisher""")
						/** Optional. If set to true, and the subscription offer with the given package_name, product_id, base_plan_id and offer_id doesn't exist, an offer will be created. If a new offer is created, update_mask is ignored. */
						def withAllowMissing(allowMissing: Boolean) = new patch(req.addQueryStringParameters("allowMissing" -> allowMissing.toString))
						/** Optional. The latency tolerance for the propagation of this product update. Defaults to latency-sensitive.<br>Possible values:<br>PRODUCT_UPDATE_LATENCY_TOLERANCE_UNSPECIFIED: Defaults to PRODUCT_UPDATE_LATENCY_TOLERANCE_LATENCY_SENSITIVE.<br>PRODUCT_UPDATE_LATENCY_TOLERANCE_LATENCY_SENSITIVE: The update will propagate to clients within several minutes on average and up to a few hours in rare cases. Throughput is limited to 7,200 updates per app per hour.<br>PRODUCT_UPDATE_LATENCY_TOLERANCE_LATENCY_TOLERANT: The update will propagate to clients within 24 hours. Supports high throughput of up to 720,000 updates per app per hour using batch modification methods. */
						def withLatencyTolerance(latencyTolerance: String) = new patch(req.addQueryStringParameters("latencyTolerance" -> latencyTolerance.toString))
						/** Perform the request */
						def withSubscriptionOffer(body: Schema.SubscriptionOffer) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.SubscriptionOffer])
					}
					object patch {
						def apply(packageName: String, productId: String, basePlanId: String, offerId: String, updateMask: String, regionsVersionVersion: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/subscriptions/${productId}/basePlans/${basePlanId}/offers/${offerId}").addQueryStringParameters("updateMask" -> updateMask.toString, "regionsVersion.version" -> regionsVersionVersion.toString))
					}
					/** Reads one or more subscription offers. */
					class batchGet(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/androidpublisher""")
						/** Perform the request */
						def withBatchGetSubscriptionOffersRequest(body: Schema.BatchGetSubscriptionOffersRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.BatchGetSubscriptionOffersResponse])
					}
					object batchGet {
						def apply(packageName: String, productId: String, basePlanId: String)(using signer: RequestSigner, ec: ExecutionContext): batchGet = new batchGet(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/subscriptions/${productId}/basePlans/${basePlanId}/offers:batchGet").addQueryStringParameters())
					}
					/** Lists all offers under a given subscription. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListSubscriptionOffersResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/androidpublisher""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListSubscriptionOffersResponse])
					}
					object list {
						def apply(packageName: String, productId: String, basePlanId: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/subscriptions/${productId}/basePlans/${basePlanId}/offers").addQueryStringParameters("pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
						given Conversion[list, Future[Schema.ListSubscriptionOffersResponse]] = (fun: list) => fun.apply()
					}
				}
			}
		}
	}
	object purchases {
		object products {
			/** Checks the purchase and consumption status of an inapp item. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ProductPurchase]) {
				val scopes = Seq("""https://www.googleapis.com/auth/androidpublisher""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ProductPurchase])
			}
			object get {
				def apply(packageName: String, productId: String, token: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/purchases/products/${productId}/tokens/${token}").addQueryStringParameters())
				given Conversion[get, Future[Schema.ProductPurchase]] = (fun: get) => fun.apply()
			}
			/** Acknowledges a purchase of an inapp item. */
			class acknowledge(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/androidpublisher""")
				/** Perform the request */
				def withProductPurchasesAcknowledgeRequest(body: Schema.ProductPurchasesAcknowledgeRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_ => ())
			}
			object acknowledge {
				def apply(packageName: String, productId: String, token: String)(using signer: RequestSigner, ec: ExecutionContext): acknowledge = new acknowledge(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/purchases/products/${productId}/tokens/${token}:acknowledge").addQueryStringParameters())
			}
			/** Consumes a purchase for an inapp item. */
			class consume(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Unit]) {
				val scopes = Seq("""https://www.googleapis.com/auth/androidpublisher""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("POST")).map(_ => ())
			}
			object consume {
				def apply(packageName: String, productId: String, token: String)(using signer: RequestSigner, ec: ExecutionContext): consume = new consume(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/purchases/products/${productId}/tokens/${token}:consume").addQueryStringParameters())
				given Conversion[consume, Future[Unit]] = (fun: consume) => fun.apply()
			}
		}
		object subscriptions {
			/** Defers a user's subscription purchase until a specified future expiration time. */
			class defer(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/androidpublisher""")
				/** Perform the request */
				def withSubscriptionPurchasesDeferRequest(body: Schema.SubscriptionPurchasesDeferRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.SubscriptionPurchasesDeferResponse])
			}
			object defer {
				def apply(packageName: String, subscriptionId: String, token: String)(using signer: RequestSigner, ec: ExecutionContext): defer = new defer(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/purchases/subscriptions/${subscriptionId}/tokens/${token}:defer").addQueryStringParameters())
			}
			/** Cancels a user's subscription purchase. The subscription remains valid until its expiration time. */
			class cancel(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Unit]) {
				val scopes = Seq("""https://www.googleapis.com/auth/androidpublisher""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("POST")).map(_ => ())
			}
			object cancel {
				def apply(packageName: String, subscriptionId: String, token: String)(using signer: RequestSigner, ec: ExecutionContext): cancel = new cancel(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/purchases/subscriptions/${subscriptionId}/tokens/${token}:cancel").addQueryStringParameters())
				given Conversion[cancel, Future[Unit]] = (fun: cancel) => fun.apply()
			}
			/** Acknowledges a subscription purchase. */
			class acknowledge(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/androidpublisher""")
				/** Perform the request */
				def withSubscriptionPurchasesAcknowledgeRequest(body: Schema.SubscriptionPurchasesAcknowledgeRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_ => ())
			}
			object acknowledge {
				def apply(packageName: String, subscriptionId: String, token: String)(using signer: RequestSigner, ec: ExecutionContext): acknowledge = new acknowledge(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/purchases/subscriptions/${subscriptionId}/tokens/${token}:acknowledge").addQueryStringParameters())
			}
			/** Refunds a user's subscription purchase, but the subscription remains valid until its expiration time and it will continue to recur. */
			class refund(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Unit]) {
				val scopes = Seq("""https://www.googleapis.com/auth/androidpublisher""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("POST")).map(_ => ())
			}
			object refund {
				def apply(packageName: String, subscriptionId: String, token: String)(using signer: RequestSigner, ec: ExecutionContext): refund = new refund(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/purchases/subscriptions/${subscriptionId}/tokens/${token}:refund").addQueryStringParameters())
				given Conversion[refund, Future[Unit]] = (fun: refund) => fun.apply()
			}
			/** Refunds and immediately revokes a user's subscription purchase. Access to the subscription will be terminated immediately and it will stop recurring. */
			class revoke(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Unit]) {
				val scopes = Seq("""https://www.googleapis.com/auth/androidpublisher""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("POST")).map(_ => ())
			}
			object revoke {
				def apply(packageName: String, subscriptionId: String, token: String)(using signer: RequestSigner, ec: ExecutionContext): revoke = new revoke(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/purchases/subscriptions/${subscriptionId}/tokens/${token}:revoke").addQueryStringParameters())
				given Conversion[revoke, Future[Unit]] = (fun: revoke) => fun.apply()
			}
			/** Checks whether a user's subscription purchase is valid and returns its expiry time. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.SubscriptionPurchase]) {
				val scopes = Seq("""https://www.googleapis.com/auth/androidpublisher""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.SubscriptionPurchase])
			}
			object get {
				def apply(packageName: String, subscriptionId: String, token: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/purchases/subscriptions/${subscriptionId}/tokens/${token}").addQueryStringParameters())
				given Conversion[get, Future[Schema.SubscriptionPurchase]] = (fun: get) => fun.apply()
			}
		}
		object subscriptionsv2 {
			/** Get metadata about a subscription */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.SubscriptionPurchaseV2]) {
				val scopes = Seq("""https://www.googleapis.com/auth/androidpublisher""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.SubscriptionPurchaseV2])
			}
			object get {
				def apply(packageName: String, token: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/purchases/subscriptionsv2/tokens/${token}").addQueryStringParameters())
				given Conversion[get, Future[Schema.SubscriptionPurchaseV2]] = (fun: get) => fun.apply()
			}
			/** Revoke a subscription purchase for the user. */
			class revoke(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/androidpublisher""")
				/** Perform the request */
				def withRevokeSubscriptionPurchaseRequest(body: Schema.RevokeSubscriptionPurchaseRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.RevokeSubscriptionPurchaseResponse])
			}
			object revoke {
				def apply(packageName: String, token: String)(using signer: RequestSigner, ec: ExecutionContext): revoke = new revoke(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/purchases/subscriptionsv2/tokens/${token}:revoke").addQueryStringParameters())
			}
		}
		object voidedpurchases {
			/** Lists the purchases that were canceled, refunded or charged-back. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.VoidedPurchasesListResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/androidpublisher""")
				/** Optional. Whether to include voided purchases of quantity-based partial refunds, which are applicable only to multi-quantity purchases. If true, additional voided purchases may be returned with voidedQuantity that indicates the refund quantity of a quantity-based partial refund. The default value is false. */
				def withIncludeQuantityBasedPartialRefund(includeQuantityBasedPartialRefund: Boolean) = new list(req.addQueryStringParameters("includeQuantityBasedPartialRefund" -> includeQuantityBasedPartialRefund.toString))
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.VoidedPurchasesListResponse])
			}
			object list {
				def apply(packageName: String, maxResults: Int, startIndex: Int, token: String, startTime: String, endTime: String, `type`: Int)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/purchases/voidedpurchases").addQueryStringParameters("maxResults" -> maxResults.toString, "startIndex" -> startIndex.toString, "token" -> token.toString, "startTime" -> startTime.toString, "endTime" -> endTime.toString, "type" -> `type`.toString))
				given Conversion[list, Future[Schema.VoidedPurchasesListResponse]] = (fun: list) => fun.apply()
			}
		}
	}
	object reviews {
		/** Gets a single review. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Review]) {
			val scopes = Seq("""https://www.googleapis.com/auth/androidpublisher""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Review])
		}
		object get {
			def apply(packageName: String, reviewId: String, translationLanguage: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/reviews/${reviewId}").addQueryStringParameters("translationLanguage" -> translationLanguage.toString))
			given Conversion[get, Future[Schema.Review]] = (fun: get) => fun.apply()
		}
		/** Lists all reviews. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ReviewsListResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/androidpublisher""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ReviewsListResponse])
		}
		object list {
			def apply(packageName: String, token: String, startIndex: Int, maxResults: Int, translationLanguage: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/reviews").addQueryStringParameters("token" -> token.toString, "startIndex" -> startIndex.toString, "maxResults" -> maxResults.toString, "translationLanguage" -> translationLanguage.toString))
			given Conversion[list, Future[Schema.ReviewsListResponse]] = (fun: list) => fun.apply()
		}
		/** Replies to a single review, or updates an existing reply. */
		class reply(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/androidpublisher""")
			/** Perform the request */
			def withReviewsReplyRequest(body: Schema.ReviewsReplyRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.ReviewsReplyResponse])
		}
		object reply {
			def apply(packageName: String, reviewId: String)(using signer: RequestSigner, ec: ExecutionContext): reply = new reply(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/reviews/${reviewId}:reply").addQueryStringParameters())
		}
	}
	object systemapks {
		object variants {
			/** Creates an APK which is suitable for inclusion in a system image from an already uploaded Android App Bundle. */
			class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/androidpublisher""")
				/** Perform the request */
				def withVariant(body: Schema.Variant) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Variant])
			}
			object create {
				def apply(packageName: String, versionCode: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/systemApks/${versionCode}/variants").addQueryStringParameters())
			}
			/** Returns the list of previously created system APK variants. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.SystemApksListResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/androidpublisher""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.SystemApksListResponse])
			}
			object list {
				def apply(packageName: String, versionCode: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/systemApks/${versionCode}/variants").addQueryStringParameters())
				given Conversion[list, Future[Schema.SystemApksListResponse]] = (fun: list) => fun.apply()
			}
			/** Returns a previously created system APK variant. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Variant]) {
				val scopes = Seq("""https://www.googleapis.com/auth/androidpublisher""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Variant])
			}
			object get {
				def apply(packageName: String, versionCode: String, variantId: Int)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/systemApks/${versionCode}/variants/${variantId}").addQueryStringParameters())
				given Conversion[get, Future[Schema.Variant]] = (fun: get) => fun.apply()
			}
			/** Downloads a previously created system APK which is suitable for inclusion in a system image. */
			class download(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Unit]) {
				val scopes = Seq("""https://www.googleapis.com/auth/androidpublisher""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_ => ())
			}
			object download {
				def apply(packageName: String, versionCode: String, variantId: Int)(using signer: RequestSigner, ec: ExecutionContext): download = new download(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/systemApks/${versionCode}/variants/${variantId}:download").addQueryStringParameters())
				given Conversion[download, Future[Unit]] = (fun: download) => fun.apply()
			}
		}
	}
}
