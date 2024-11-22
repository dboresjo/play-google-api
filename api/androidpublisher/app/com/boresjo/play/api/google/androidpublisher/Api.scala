package com.boresjo.play.api.google.androidpublisher

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://androidpublisher.googleapis.com/"

	object users {
		class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withUser(body: Schema.User) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.User])
		}
		object create {
			def apply(developersId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"androidpublisher/v3/developers/${developersId}/users").addQueryStringParameters("parent" -> parent.toString))
		}
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListUsersResponse]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListUsersResponse])
		}
		object list {
			def apply(developersId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"androidpublisher/v3/developers/${developersId}/users").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
			given Conversion[list, Future[Schema.ListUsersResponse]] = (fun: list) => fun.apply()
		}
		class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			/** Optional. The list of fields to be updated.<br>Format: google-fieldmask */
			def withUpdateMask(updateMask: String) = new patch(req.addQueryStringParameters("updateMask" -> updateMask.toString))
			def withUser(body: Schema.User) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.User])
		}
		object patch {
			def apply(developersId :PlayApi, usersId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"androidpublisher/v3/developers/${developersId}/users/${usersId}").addQueryStringParameters("name" -> name.toString))
		}
		class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Unit]) {
			def apply() = auth.exec(req,_.execute("DELETE")).map(_ => ())
		}
		object delete {
			def apply(developersId :PlayApi, usersId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"androidpublisher/v3/developers/${developersId}/users/${usersId}").addQueryStringParameters("name" -> name.toString))
			given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
		}
	}
	object grants {
		class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withGrant(body: Schema.Grant) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Grant])
		}
		object create {
			def apply(developersId :PlayApi, usersId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"androidpublisher/v3/developers/${developersId}/users/${usersId}/grants").addQueryStringParameters("parent" -> parent.toString))
		}
		class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			/** Optional. The list of fields to be updated.<br>Format: google-fieldmask */
			def withUpdateMask(updateMask: String) = new patch(req.addQueryStringParameters("updateMask" -> updateMask.toString))
			def withGrant(body: Schema.Grant) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Grant])
		}
		object patch {
			def apply(developersId :PlayApi, usersId :PlayApi, grantsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"androidpublisher/v3/developers/${developersId}/users/${usersId}/grants/${grantsId}").addQueryStringParameters("name" -> name.toString))
		}
		class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Unit]) {
			def apply() = auth.exec(req,_.execute("DELETE")).map(_ => ())
		}
		object delete {
			def apply(developersId :PlayApi, usersId :PlayApi, grantsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"androidpublisher/v3/developers/${developersId}/users/${usersId}/grants/${grantsId}").addQueryStringParameters("name" -> name.toString))
			given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
		}
	}
	object apprecovery {
		class cancel(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withCancelAppRecoveryRequest(body: Schema.CancelAppRecoveryRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.CancelAppRecoveryResponse])
		}
		object cancel {
			def apply(packageName: String, appRecoveryId: String)(using auth: AuthToken, ec: ExecutionContext): cancel = new cancel(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/appRecoveries/${appRecoveryId}:cancel").addQueryStringParameters())
		}
		class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withCreateDraftAppRecoveryRequest(body: Schema.CreateDraftAppRecoveryRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.AppRecoveryAction])
		}
		object create {
			def apply(packageName: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/appRecoveries").addQueryStringParameters())
		}
		class addTargeting(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withAddTargetingRequest(body: Schema.AddTargetingRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.AddTargetingResponse])
		}
		object addTargeting {
			def apply(packageName: String, appRecoveryId: String)(using auth: AuthToken, ec: ExecutionContext): addTargeting = new addTargeting(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/appRecoveries/${appRecoveryId}:addTargeting").addQueryStringParameters())
		}
		class deploy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withDeployAppRecoveryRequest(body: Schema.DeployAppRecoveryRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.DeployAppRecoveryResponse])
		}
		object deploy {
			def apply(packageName: String, appRecoveryId: String)(using auth: AuthToken, ec: ExecutionContext): deploy = new deploy(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/appRecoveries/${appRecoveryId}:deploy").addQueryStringParameters())
		}
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListAppRecoveriesResponse]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListAppRecoveriesResponse])
		}
		object list {
			def apply(packageName: String, versionCode: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/appRecoveries").addQueryStringParameters("versionCode" -> versionCode.toString))
			given Conversion[list, Future[Schema.ListAppRecoveriesResponse]] = (fun: list) => fun.apply()
		}
	}
	object edits {
		class commit(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.AppEdit]) {
			def apply() = auth.exec(req,_.execute("POST")).map(_.json.as[Schema.AppEdit])
		}
		object commit {
			def apply(packageName: String, editId: String, changesNotSentForReview: Boolean)(using auth: AuthToken, ec: ExecutionContext): commit = new commit(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/edits/${editId}:commit").addQueryStringParameters("changesNotSentForReview" -> changesNotSentForReview.toString))
			given Conversion[commit, Future[Schema.AppEdit]] = (fun: commit) => fun.apply()
		}
		class validate(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.AppEdit]) {
			def apply() = auth.exec(req,_.execute("POST")).map(_.json.as[Schema.AppEdit])
		}
		object validate {
			def apply(packageName: String, editId: String)(using auth: AuthToken, ec: ExecutionContext): validate = new validate(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/edits/${editId}:validate").addQueryStringParameters())
			given Conversion[validate, Future[Schema.AppEdit]] = (fun: validate) => fun.apply()
		}
		class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Unit]) {
			def apply() = auth.exec(req,_.execute("DELETE")).map(_ => ())
		}
		object delete {
			def apply(packageName: String, editId: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/edits/${editId}").addQueryStringParameters())
			given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
		}
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.AppEdit]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.AppEdit])
		}
		object get {
			def apply(packageName: String, editId: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/edits/${editId}").addQueryStringParameters())
			given Conversion[get, Future[Schema.AppEdit]] = (fun: get) => fun.apply()
		}
		class insert(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withAppEdit(body: Schema.AppEdit) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.AppEdit])
		}
		object insert {
			def apply(packageName: String)(using auth: AuthToken, ec: ExecutionContext): insert = new insert(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/edits").addQueryStringParameters())
		}
		object expansionfiles {
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ExpansionFile]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ExpansionFile])
			}
			object get {
				def apply(packageName: String, editId: String, apkVersionCode: Int, expansionFileType: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/edits/${editId}/apks/${apkVersionCode}/expansionFiles/${expansionFileType}").addQueryStringParameters())
				given Conversion[get, Future[Schema.ExpansionFile]] = (fun: get) => fun.apply()
			}
			class update(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withExpansionFile(body: Schema.ExpansionFile) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.ExpansionFile])
			}
			object update {
				def apply(packageName: String, editId: String, apkVersionCode: Int, expansionFileType: String)(using auth: AuthToken, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/edits/${editId}/apks/${apkVersionCode}/expansionFiles/${expansionFileType}").addQueryStringParameters())
			}
			class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withExpansionFile(body: Schema.ExpansionFile) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.ExpansionFile])
			}
			object patch {
				def apply(packageName: String, editId: String, apkVersionCode: Int, expansionFileType: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/edits/${editId}/apks/${apkVersionCode}/expansionFiles/${expansionFileType}").addQueryStringParameters())
			}
			class upload(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ExpansionFilesUploadResponse]) {
				def apply() = auth.exec(req,_.execute("POST")).map(_.json.as[Schema.ExpansionFilesUploadResponse])
			}
			object upload {
				def apply(packageName: String, editId: String, apkVersionCode: Int, expansionFileType: String)(using auth: AuthToken, ec: ExecutionContext): upload = new upload(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/edits/${editId}/apks/${apkVersionCode}/expansionFiles/${expansionFileType}").addQueryStringParameters())
				given Conversion[upload, Future[Schema.ExpansionFilesUploadResponse]] = (fun: upload) => fun.apply()
			}
		}
		object countryavailability {
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.TrackCountryAvailability]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.TrackCountryAvailability])
			}
			object get {
				def apply(packageName: String, editId: String, track: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/edits/${editId}/countryAvailability/${track}").addQueryStringParameters())
				given Conversion[get, Future[Schema.TrackCountryAvailability]] = (fun: get) => fun.apply()
			}
		}
		object testers {
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Testers]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Testers])
			}
			object get {
				def apply(packageName: String, editId: String, track: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/edits/${editId}/testers/${track}").addQueryStringParameters())
				given Conversion[get, Future[Schema.Testers]] = (fun: get) => fun.apply()
			}
			class update(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withTesters(body: Schema.Testers) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.Testers])
			}
			object update {
				def apply(packageName: String, editId: String, track: String)(using auth: AuthToken, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/edits/${editId}/testers/${track}").addQueryStringParameters())
			}
			class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withTesters(body: Schema.Testers) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Testers])
			}
			object patch {
				def apply(packageName: String, editId: String, track: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/edits/${editId}/testers/${track}").addQueryStringParameters())
			}
		}
		object tracks {
			class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withTrackConfig(body: Schema.TrackConfig) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Track])
			}
			object create {
				def apply(packageName: String, editId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/edits/${editId}/tracks").addQueryStringParameters())
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Track]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Track])
			}
			object get {
				def apply(packageName: String, editId: String, track: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/edits/${editId}/tracks/${track}").addQueryStringParameters())
				given Conversion[get, Future[Schema.Track]] = (fun: get) => fun.apply()
			}
			class update(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withTrack(body: Schema.Track) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.Track])
			}
			object update {
				def apply(packageName: String, editId: String, track: String)(using auth: AuthToken, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/edits/${editId}/tracks/${track}").addQueryStringParameters())
			}
			class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withTrack(body: Schema.Track) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Track])
			}
			object patch {
				def apply(packageName: String, editId: String, track: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/edits/${editId}/tracks/${track}").addQueryStringParameters())
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.TracksListResponse]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.TracksListResponse])
			}
			object list {
				def apply(packageName: String, editId: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/edits/${editId}/tracks").addQueryStringParameters())
				given Conversion[list, Future[Schema.TracksListResponse]] = (fun: list) => fun.apply()
			}
		}
		object deobfuscationfiles {
			class upload(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.DeobfuscationFilesUploadResponse]) {
				def apply() = auth.exec(req,_.execute("POST")).map(_.json.as[Schema.DeobfuscationFilesUploadResponse])
			}
			object upload {
				def apply(packageName: String, editId: String, apkVersionCode: Int, deobfuscationFileType: String)(using auth: AuthToken, ec: ExecutionContext): upload = new upload(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/edits/${editId}/apks/${apkVersionCode}/deobfuscationFiles/${deobfuscationFileType}").addQueryStringParameters())
				given Conversion[upload, Future[Schema.DeobfuscationFilesUploadResponse]] = (fun: upload) => fun.apply()
			}
		}
		object images {
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ImagesListResponse]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ImagesListResponse])
			}
			object list {
				def apply(packageName: String, editId: String, language: String, imageType: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/edits/${editId}/listings/${language}/${imageType}").addQueryStringParameters())
				given Conversion[list, Future[Schema.ImagesListResponse]] = (fun: list) => fun.apply()
			}
			class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Unit]) {
				def apply() = auth.exec(req,_.execute("DELETE")).map(_ => ())
			}
			object delete {
				def apply(packageName: String, editId: String, language: String, imageType: String, imageId: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/edits/${editId}/listings/${language}/${imageType}/${imageId}").addQueryStringParameters())
				given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
			}
			class deleteall(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ImagesDeleteAllResponse]) {
				def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.ImagesDeleteAllResponse])
			}
			object deleteall {
				def apply(packageName: String, editId: String, language: String, imageType: String)(using auth: AuthToken, ec: ExecutionContext): deleteall = new deleteall(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/edits/${editId}/listings/${language}/${imageType}").addQueryStringParameters())
				given Conversion[deleteall, Future[Schema.ImagesDeleteAllResponse]] = (fun: deleteall) => fun.apply()
			}
			class upload(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ImagesUploadResponse]) {
				def apply() = auth.exec(req,_.execute("POST")).map(_.json.as[Schema.ImagesUploadResponse])
			}
			object upload {
				def apply(packageName: String, editId: String, language: String, imageType: String)(using auth: AuthToken, ec: ExecutionContext): upload = new upload(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/edits/${editId}/listings/${language}/${imageType}").addQueryStringParameters())
				given Conversion[upload, Future[Schema.ImagesUploadResponse]] = (fun: upload) => fun.apply()
			}
		}
		object listings {
			class deleteall(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Unit]) {
				def apply() = auth.exec(req,_.execute("DELETE")).map(_ => ())
			}
			object deleteall {
				def apply(packageName: String, editId: String)(using auth: AuthToken, ec: ExecutionContext): deleteall = new deleteall(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/edits/${editId}/listings").addQueryStringParameters())
				given Conversion[deleteall, Future[Unit]] = (fun: deleteall) => fun.apply()
			}
			class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Unit]) {
				def apply() = auth.exec(req,_.execute("DELETE")).map(_ => ())
			}
			object delete {
				def apply(packageName: String, editId: String, language: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/edits/${editId}/listings/${language}").addQueryStringParameters())
				given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Listing]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Listing])
			}
			object get {
				def apply(packageName: String, editId: String, language: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/edits/${editId}/listings/${language}").addQueryStringParameters())
				given Conversion[get, Future[Schema.Listing]] = (fun: get) => fun.apply()
			}
			class update(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withListing(body: Schema.Listing) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.Listing])
			}
			object update {
				def apply(packageName: String, editId: String, language: String)(using auth: AuthToken, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/edits/${editId}/listings/${language}").addQueryStringParameters())
			}
			class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withListing(body: Schema.Listing) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Listing])
			}
			object patch {
				def apply(packageName: String, editId: String, language: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/edits/${editId}/listings/${language}").addQueryStringParameters())
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListingsListResponse]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListingsListResponse])
			}
			object list {
				def apply(packageName: String, editId: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/edits/${editId}/listings").addQueryStringParameters())
				given Conversion[list, Future[Schema.ListingsListResponse]] = (fun: list) => fun.apply()
			}
		}
		object bundles {
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.BundlesListResponse]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.BundlesListResponse])
			}
			object list {
				def apply(packageName: String, editId: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/edits/${editId}/bundles").addQueryStringParameters())
				given Conversion[list, Future[Schema.BundlesListResponse]] = (fun: list) => fun.apply()
			}
			class upload(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Bundle]) {
				def apply() = auth.exec(req,_.execute("POST")).map(_.json.as[Schema.Bundle])
			}
			object upload {
				def apply(packageName: String, editId: String, ackBundleInstallationWarning: Boolean, deviceTierConfigId: String)(using auth: AuthToken, ec: ExecutionContext): upload = new upload(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/edits/${editId}/bundles").addQueryStringParameters("ackBundleInstallationWarning" -> ackBundleInstallationWarning.toString, "deviceTierConfigId" -> deviceTierConfigId.toString))
				given Conversion[upload, Future[Schema.Bundle]] = (fun: upload) => fun.apply()
			}
		}
		object apks {
			class upload(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Apk]) {
				def apply() = auth.exec(req,_.execute("POST")).map(_.json.as[Schema.Apk])
			}
			object upload {
				def apply(packageName: String, editId: String)(using auth: AuthToken, ec: ExecutionContext): upload = new upload(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/edits/${editId}/apks").addQueryStringParameters())
				given Conversion[upload, Future[Schema.Apk]] = (fun: upload) => fun.apply()
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ApksListResponse]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ApksListResponse])
			}
			object list {
				def apply(packageName: String, editId: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/edits/${editId}/apks").addQueryStringParameters())
				given Conversion[list, Future[Schema.ApksListResponse]] = (fun: list) => fun.apply()
			}
			class addexternallyhosted(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withApksAddExternallyHostedRequest(body: Schema.ApksAddExternallyHostedRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.ApksAddExternallyHostedResponse])
			}
			object addexternallyhosted {
				def apply(packageName: String, editId: String)(using auth: AuthToken, ec: ExecutionContext): addexternallyhosted = new addexternallyhosted(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/edits/${editId}/apks/externallyHosted").addQueryStringParameters())
			}
		}
		object details {
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.AppDetails]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.AppDetails])
			}
			object get {
				def apply(packageName: String, editId: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/edits/${editId}/details").addQueryStringParameters())
				given Conversion[get, Future[Schema.AppDetails]] = (fun: get) => fun.apply()
			}
			class update(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withAppDetails(body: Schema.AppDetails) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.AppDetails])
			}
			object update {
				def apply(packageName: String, editId: String)(using auth: AuthToken, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/edits/${editId}/details").addQueryStringParameters())
			}
			class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withAppDetails(body: Schema.AppDetails) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.AppDetails])
			}
			object patch {
				def apply(packageName: String, editId: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/edits/${editId}/details").addQueryStringParameters())
			}
		}
	}
	object externaltransactions {
		class createexternaltransaction(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withExternalTransaction(body: Schema.ExternalTransaction) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.ExternalTransaction])
		}
		object createexternaltransaction {
			def apply(applicationsId :PlayApi, parent: String, externalTransactionId: String)(using auth: AuthToken, ec: ExecutionContext): createexternaltransaction = new createexternaltransaction(ws.url(BASE_URL + s"androidpublisher/v3/applications/${applicationsId}/externalTransactions").addQueryStringParameters("parent" -> parent.toString, "externalTransactionId" -> externalTransactionId.toString))
		}
		class refundexternaltransaction(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withRefundExternalTransactionRequest(body: Schema.RefundExternalTransactionRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.ExternalTransaction])
		}
		object refundexternaltransaction {
			def apply(applicationsId :PlayApi, externalTransactionsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): refundexternaltransaction = new refundexternaltransaction(ws.url(BASE_URL + s"androidpublisher/v3/applications/${applicationsId}/externalTransactions/${externalTransactionsId}:refund").addQueryStringParameters("name" -> name.toString))
		}
		class getexternaltransaction(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ExternalTransaction]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ExternalTransaction])
		}
		object getexternaltransaction {
			def apply(applicationsId :PlayApi, externalTransactionsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): getexternaltransaction = new getexternaltransaction(ws.url(BASE_URL + s"androidpublisher/v3/applications/${applicationsId}/externalTransactions/${externalTransactionsId}").addQueryStringParameters("name" -> name.toString))
			given Conversion[getexternaltransaction, Future[Schema.ExternalTransaction]] = (fun: getexternaltransaction) => fun.apply()
		}
	}
	object generatedapks {
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GeneratedApksListResponse]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GeneratedApksListResponse])
		}
		object list {
			def apply(packageName: String, versionCode: Int)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/generatedApks/${versionCode}").addQueryStringParameters())
			given Conversion[list, Future[Schema.GeneratedApksListResponse]] = (fun: list) => fun.apply()
		}
		class download(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Unit]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_ => ())
		}
		object download {
			def apply(packageName: String, versionCode: Int, downloadId: String)(using auth: AuthToken, ec: ExecutionContext): download = new download(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/generatedApks/${versionCode}/downloads/${downloadId}:download").addQueryStringParameters())
			given Conversion[download, Future[Unit]] = (fun: download) => fun.apply()
		}
	}
	object inappproducts {
		class insert(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withInAppProduct(body: Schema.InAppProduct) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.InAppProduct])
		}
		object insert {
			def apply(packageName: String, autoConvertMissingPrices: Boolean)(using auth: AuthToken, ec: ExecutionContext): insert = new insert(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/inappproducts").addQueryStringParameters("autoConvertMissingPrices" -> autoConvertMissingPrices.toString))
		}
		class batchUpdate(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withInappproductsBatchUpdateRequest(body: Schema.InappproductsBatchUpdateRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.InappproductsBatchUpdateResponse])
		}
		object batchUpdate {
			def apply(packageName: String)(using auth: AuthToken, ec: ExecutionContext): batchUpdate = new batchUpdate(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/inappproducts:batchUpdate").addQueryStringParameters())
		}
		class batchDelete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withInappproductsBatchDeleteRequest(body: Schema.InappproductsBatchDeleteRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_ => ())
		}
		object batchDelete {
			def apply(packageName: String)(using auth: AuthToken, ec: ExecutionContext): batchDelete = new batchDelete(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/inappproducts:batchDelete").addQueryStringParameters())
		}
		class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Unit]) {
			/** Optional. The latency tolerance for the propagation of this product update. Defaults to latency-sensitive.<br>Possible values:<br>PRODUCT_UPDATE_LATENCY_TOLERANCE_UNSPECIFIED: Defaults to PRODUCT_UPDATE_LATENCY_TOLERANCE_LATENCY_SENSITIVE.<br>PRODUCT_UPDATE_LATENCY_TOLERANCE_LATENCY_SENSITIVE: The update will propagate to clients within several minutes on average and up to a few hours in rare cases. Throughput is limited to 7,200 updates per app per hour.<br>PRODUCT_UPDATE_LATENCY_TOLERANCE_LATENCY_TOLERANT: The update will propagate to clients within 24 hours. Supports high throughput of up to 720,000 updates per app per hour using batch modification methods. */
			def withLatencyTolerance(latencyTolerance: String) = new delete(req.addQueryStringParameters("latencyTolerance" -> latencyTolerance.toString))
			def apply() = auth.exec(req,_.execute("DELETE")).map(_ => ())
		}
		object delete {
			def apply(packageName: String, sku: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/inappproducts/${sku}").addQueryStringParameters())
			given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
		}
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.InAppProduct]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.InAppProduct])
		}
		object get {
			def apply(packageName: String, sku: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/inappproducts/${sku}").addQueryStringParameters())
			given Conversion[get, Future[Schema.InAppProduct]] = (fun: get) => fun.apply()
		}
		class update(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			/** Optional. The latency tolerance for the propagation of this product update. Defaults to latency-sensitive.<br>Possible values:<br>PRODUCT_UPDATE_LATENCY_TOLERANCE_UNSPECIFIED: Defaults to PRODUCT_UPDATE_LATENCY_TOLERANCE_LATENCY_SENSITIVE.<br>PRODUCT_UPDATE_LATENCY_TOLERANCE_LATENCY_SENSITIVE: The update will propagate to clients within several minutes on average and up to a few hours in rare cases. Throughput is limited to 7,200 updates per app per hour.<br>PRODUCT_UPDATE_LATENCY_TOLERANCE_LATENCY_TOLERANT: The update will propagate to clients within 24 hours. Supports high throughput of up to 720,000 updates per app per hour using batch modification methods. */
			def withLatencyTolerance(latencyTolerance: String) = new update(req.addQueryStringParameters("latencyTolerance" -> latencyTolerance.toString))
			def withInAppProduct(body: Schema.InAppProduct) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.InAppProduct])
		}
		object update {
			def apply(packageName: String, sku: String, autoConvertMissingPrices: Boolean, allowMissing: Boolean)(using auth: AuthToken, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/inappproducts/${sku}").addQueryStringParameters("autoConvertMissingPrices" -> autoConvertMissingPrices.toString, "allowMissing" -> allowMissing.toString))
		}
		class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			/** Optional. The latency tolerance for the propagation of this product update. Defaults to latency-sensitive.<br>Possible values:<br>PRODUCT_UPDATE_LATENCY_TOLERANCE_UNSPECIFIED: Defaults to PRODUCT_UPDATE_LATENCY_TOLERANCE_LATENCY_SENSITIVE.<br>PRODUCT_UPDATE_LATENCY_TOLERANCE_LATENCY_SENSITIVE: The update will propagate to clients within several minutes on average and up to a few hours in rare cases. Throughput is limited to 7,200 updates per app per hour.<br>PRODUCT_UPDATE_LATENCY_TOLERANCE_LATENCY_TOLERANT: The update will propagate to clients within 24 hours. Supports high throughput of up to 720,000 updates per app per hour using batch modification methods. */
			def withLatencyTolerance(latencyTolerance: String) = new patch(req.addQueryStringParameters("latencyTolerance" -> latencyTolerance.toString))
			def withInAppProduct(body: Schema.InAppProduct) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.InAppProduct])
		}
		object patch {
			def apply(packageName: String, sku: String, autoConvertMissingPrices: Boolean)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/inappproducts/${sku}").addQueryStringParameters("autoConvertMissingPrices" -> autoConvertMissingPrices.toString))
		}
		class batchGet(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.InappproductsBatchGetResponse]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.InappproductsBatchGetResponse])
		}
		object batchGet {
			def apply(packageName: String, sku: String)(using auth: AuthToken, ec: ExecutionContext): batchGet = new batchGet(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/inappproducts:batchGet").addQueryStringParameters("sku" -> sku.toString))
			given Conversion[batchGet, Future[Schema.InappproductsBatchGetResponse]] = (fun: batchGet) => fun.apply()
		}
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.InappproductsListResponse]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.InappproductsListResponse])
		}
		object list {
			def apply(packageName: String, token: String, startIndex: Int, maxResults: Int)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/inappproducts").addQueryStringParameters("token" -> token.toString, "startIndex" -> startIndex.toString, "maxResults" -> maxResults.toString))
			given Conversion[list, Future[Schema.InappproductsListResponse]] = (fun: list) => fun.apply()
		}
	}
	object internalappsharingartifacts {
		class uploadapk(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.InternalAppSharingArtifact]) {
			def apply() = auth.exec(req,_.execute("POST")).map(_.json.as[Schema.InternalAppSharingArtifact])
		}
		object uploadapk {
			def apply(packageName: String)(using auth: AuthToken, ec: ExecutionContext): uploadapk = new uploadapk(ws.url(BASE_URL + s"androidpublisher/v3/applications/internalappsharing/${packageName}/artifacts/apk").addQueryStringParameters())
			given Conversion[uploadapk, Future[Schema.InternalAppSharingArtifact]] = (fun: uploadapk) => fun.apply()
		}
		class uploadbundle(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.InternalAppSharingArtifact]) {
			def apply() = auth.exec(req,_.execute("POST")).map(_.json.as[Schema.InternalAppSharingArtifact])
		}
		object uploadbundle {
			def apply(packageName: String)(using auth: AuthToken, ec: ExecutionContext): uploadbundle = new uploadbundle(ws.url(BASE_URL + s"androidpublisher/v3/applications/internalappsharing/${packageName}/artifacts/bundle").addQueryStringParameters())
			given Conversion[uploadbundle, Future[Schema.InternalAppSharingArtifact]] = (fun: uploadbundle) => fun.apply()
		}
	}
	object orders {
		class refund(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Unit]) {
			/** Whether to revoke the purchased item. If set to true, access to the subscription or in-app item will be terminated immediately. If the item is a recurring subscription, all future payments will also be terminated. Consumed in-app items need to be handled by developer's app. (optional). */
			def withRevoke(revoke: Boolean) = new refund(req.addQueryStringParameters("revoke" -> revoke.toString))
			def apply() = auth.exec(req,_.execute("POST")).map(_ => ())
		}
		object refund {
			def apply(packageName: String, orderId: String)(using auth: AuthToken, ec: ExecutionContext): refund = new refund(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/orders/${orderId}:refund").addQueryStringParameters())
			given Conversion[refund, Future[Unit]] = (fun: refund) => fun.apply()
		}
	}
	object applications {
		class dataSafety(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withSafetyLabelsUpdateRequest(body: Schema.SafetyLabelsUpdateRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.SafetyLabelsUpdateResponse])
		}
		object dataSafety {
			def apply(packageName: String)(using auth: AuthToken, ec: ExecutionContext): dataSafety = new dataSafety(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/dataSafety").addQueryStringParameters())
		}
		object deviceTierConfigs {
			class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withDeviceTierConfig(body: Schema.DeviceTierConfig) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.DeviceTierConfig])
			}
			object create {
				def apply(packageName: String, allowUnknownDevices: Boolean)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/deviceTierConfigs").addQueryStringParameters("allowUnknownDevices" -> allowUnknownDevices.toString))
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.DeviceTierConfig]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.DeviceTierConfig])
			}
			object get {
				def apply(packageName: String, deviceTierConfigId: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/deviceTierConfigs/${deviceTierConfigId}").addQueryStringParameters())
				given Conversion[get, Future[Schema.DeviceTierConfig]] = (fun: get) => fun.apply()
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListDeviceTierConfigsResponse]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListDeviceTierConfigsResponse])
			}
			object list {
				def apply(packageName: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/deviceTierConfigs").addQueryStringParameters("pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListDeviceTierConfigsResponse]] = (fun: list) => fun.apply()
			}
		}
	}
	object monetization {
		class convertRegionPrices(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withConvertRegionPricesRequest(body: Schema.ConvertRegionPricesRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.ConvertRegionPricesResponse])
		}
		object convertRegionPrices {
			def apply(packageName: String)(using auth: AuthToken, ec: ExecutionContext): convertRegionPrices = new convertRegionPrices(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/pricing:convertRegionPrices").addQueryStringParameters())
		}
		object subscriptions {
			class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withSubscription(body: Schema.Subscription) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Subscription])
			}
			object create {
				def apply(packageName: String, productId: String, regionsVersionVersion: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/subscriptions").addQueryStringParameters("productId" -> productId.toString, "regionsVersion.version" -> regionsVersionVersion.toString))
			}
			class batchUpdate(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withBatchUpdateSubscriptionsRequest(body: Schema.BatchUpdateSubscriptionsRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.BatchUpdateSubscriptionsResponse])
			}
			object batchUpdate {
				def apply(packageName: String)(using auth: AuthToken, ec: ExecutionContext): batchUpdate = new batchUpdate(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/subscriptions:batchUpdate").addQueryStringParameters())
			}
			class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Unit]) {
				def apply() = auth.exec(req,_.execute("DELETE")).map(_ => ())
			}
			object delete {
				def apply(packageName: String, productId: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/subscriptions/${productId}").addQueryStringParameters())
				given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Subscription]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Subscription])
			}
			object get {
				def apply(packageName: String, productId: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/subscriptions/${productId}").addQueryStringParameters())
				given Conversion[get, Future[Schema.Subscription]] = (fun: get) => fun.apply()
			}
			class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				/** Optional. If set to true, and the subscription with the given package_name and product_id doesn't exist, the subscription will be created. If a new subscription is created, update_mask is ignored. */
				def withAllowMissing(allowMissing: Boolean) = new patch(req.addQueryStringParameters("allowMissing" -> allowMissing.toString))
				/** Optional. The latency tolerance for the propagation of this product update. Defaults to latency-sensitive.<br>Possible values:<br>PRODUCT_UPDATE_LATENCY_TOLERANCE_UNSPECIFIED: Defaults to PRODUCT_UPDATE_LATENCY_TOLERANCE_LATENCY_SENSITIVE.<br>PRODUCT_UPDATE_LATENCY_TOLERANCE_LATENCY_SENSITIVE: The update will propagate to clients within several minutes on average and up to a few hours in rare cases. Throughput is limited to 7,200 updates per app per hour.<br>PRODUCT_UPDATE_LATENCY_TOLERANCE_LATENCY_TOLERANT: The update will propagate to clients within 24 hours. Supports high throughput of up to 720,000 updates per app per hour using batch modification methods. */
				def withLatencyTolerance(latencyTolerance: String) = new patch(req.addQueryStringParameters("latencyTolerance" -> latencyTolerance.toString))
				def withSubscription(body: Schema.Subscription) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Subscription])
			}
			object patch {
				def apply(packageName: String, productId: String, updateMask: String, regionsVersionVersion: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/subscriptions/${productId}").addQueryStringParameters("updateMask" -> updateMask.toString, "regionsVersion.version" -> regionsVersionVersion.toString))
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListSubscriptionsResponse]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListSubscriptionsResponse])
			}
			object list {
				def apply(packageName: String, pageSize: Int, pageToken: String, showArchived: Boolean)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/subscriptions").addQueryStringParameters("pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "showArchived" -> showArchived.toString))
				given Conversion[list, Future[Schema.ListSubscriptionsResponse]] = (fun: list) => fun.apply()
			}
			class batchGet(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.BatchGetSubscriptionsResponse]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.BatchGetSubscriptionsResponse])
			}
			object batchGet {
				def apply(packageName: String, productIds: String)(using auth: AuthToken, ec: ExecutionContext): batchGet = new batchGet(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/subscriptions:batchGet").addQueryStringParameters("productIds" -> productIds.toString))
				given Conversion[batchGet, Future[Schema.BatchGetSubscriptionsResponse]] = (fun: batchGet) => fun.apply()
			}
			class archive(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withArchiveSubscriptionRequest(body: Schema.ArchiveSubscriptionRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Subscription])
			}
			object archive {
				def apply(packageName: String, productId: String)(using auth: AuthToken, ec: ExecutionContext): archive = new archive(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/subscriptions/${productId}:archive").addQueryStringParameters())
			}
			object basePlans {
				class deactivate(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withDeactivateBasePlanRequest(body: Schema.DeactivateBasePlanRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Subscription])
				}
				object deactivate {
					def apply(packageName: String, productId: String, basePlanId: String)(using auth: AuthToken, ec: ExecutionContext): deactivate = new deactivate(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/subscriptions/${productId}/basePlans/${basePlanId}:deactivate").addQueryStringParameters())
				}
				class migratePrices(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withMigrateBasePlanPricesRequest(body: Schema.MigrateBasePlanPricesRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.MigrateBasePlanPricesResponse])
				}
				object migratePrices {
					def apply(packageName: String, productId: String, basePlanId: String)(using auth: AuthToken, ec: ExecutionContext): migratePrices = new migratePrices(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/subscriptions/${productId}/basePlans/${basePlanId}:migratePrices").addQueryStringParameters())
				}
				class batchUpdateStates(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withBatchUpdateBasePlanStatesRequest(body: Schema.BatchUpdateBasePlanStatesRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.BatchUpdateBasePlanStatesResponse])
				}
				object batchUpdateStates {
					def apply(packageName: String, productId: String)(using auth: AuthToken, ec: ExecutionContext): batchUpdateStates = new batchUpdateStates(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/subscriptions/${productId}/basePlans:batchUpdateStates").addQueryStringParameters())
				}
				class batchMigratePrices(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withBatchMigrateBasePlanPricesRequest(body: Schema.BatchMigrateBasePlanPricesRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.BatchMigrateBasePlanPricesResponse])
				}
				object batchMigratePrices {
					def apply(packageName: String, productId: String)(using auth: AuthToken, ec: ExecutionContext): batchMigratePrices = new batchMigratePrices(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/subscriptions/${productId}/basePlans:batchMigratePrices").addQueryStringParameters())
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Unit]) {
					def apply() = auth.exec(req,_.execute("DELETE")).map(_ => ())
				}
				object delete {
					def apply(packageName: String, productId: String, basePlanId: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/subscriptions/${productId}/basePlans/${basePlanId}").addQueryStringParameters())
					given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
				}
				class activate(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withActivateBasePlanRequest(body: Schema.ActivateBasePlanRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Subscription])
				}
				object activate {
					def apply(packageName: String, productId: String, basePlanId: String)(using auth: AuthToken, ec: ExecutionContext): activate = new activate(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/subscriptions/${productId}/basePlans/${basePlanId}:activate").addQueryStringParameters())
				}
				object offers {
					class deactivate(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withDeactivateSubscriptionOfferRequest(body: Schema.DeactivateSubscriptionOfferRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.SubscriptionOffer])
					}
					object deactivate {
						def apply(packageName: String, productId: String, basePlanId: String, offerId: String)(using auth: AuthToken, ec: ExecutionContext): deactivate = new deactivate(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/subscriptions/${productId}/basePlans/${basePlanId}/offers/${offerId}:deactivate").addQueryStringParameters())
					}
					class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withSubscriptionOffer(body: Schema.SubscriptionOffer) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.SubscriptionOffer])
					}
					object create {
						def apply(packageName: String, productId: String, basePlanId: String, offerId: String, regionsVersionVersion: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/subscriptions/${productId}/basePlans/${basePlanId}/offers").addQueryStringParameters("offerId" -> offerId.toString, "regionsVersion.version" -> regionsVersionVersion.toString))
					}
					class batchUpdateStates(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withBatchUpdateSubscriptionOfferStatesRequest(body: Schema.BatchUpdateSubscriptionOfferStatesRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.BatchUpdateSubscriptionOfferStatesResponse])
					}
					object batchUpdateStates {
						def apply(packageName: String, productId: String, basePlanId: String)(using auth: AuthToken, ec: ExecutionContext): batchUpdateStates = new batchUpdateStates(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/subscriptions/${productId}/basePlans/${basePlanId}/offers:batchUpdateStates").addQueryStringParameters())
					}
					class batchUpdate(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withBatchUpdateSubscriptionOffersRequest(body: Schema.BatchUpdateSubscriptionOffersRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.BatchUpdateSubscriptionOffersResponse])
					}
					object batchUpdate {
						def apply(packageName: String, productId: String, basePlanId: String)(using auth: AuthToken, ec: ExecutionContext): batchUpdate = new batchUpdate(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/subscriptions/${productId}/basePlans/${basePlanId}/offers:batchUpdate").addQueryStringParameters())
					}
					class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Unit]) {
						def apply() = auth.exec(req,_.execute("DELETE")).map(_ => ())
					}
					object delete {
						def apply(packageName: String, productId: String, basePlanId: String, offerId: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/subscriptions/${productId}/basePlans/${basePlanId}/offers/${offerId}").addQueryStringParameters())
						given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
					}
					class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.SubscriptionOffer]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.SubscriptionOffer])
					}
					object get {
						def apply(packageName: String, productId: String, basePlanId: String, offerId: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/subscriptions/${productId}/basePlans/${basePlanId}/offers/${offerId}").addQueryStringParameters())
						given Conversion[get, Future[Schema.SubscriptionOffer]] = (fun: get) => fun.apply()
					}
					class activate(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withActivateSubscriptionOfferRequest(body: Schema.ActivateSubscriptionOfferRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.SubscriptionOffer])
					}
					object activate {
						def apply(packageName: String, productId: String, basePlanId: String, offerId: String)(using auth: AuthToken, ec: ExecutionContext): activate = new activate(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/subscriptions/${productId}/basePlans/${basePlanId}/offers/${offerId}:activate").addQueryStringParameters())
					}
					class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						/** Optional. If set to true, and the subscription offer with the given package_name, product_id, base_plan_id and offer_id doesn't exist, an offer will be created. If a new offer is created, update_mask is ignored. */
						def withAllowMissing(allowMissing: Boolean) = new patch(req.addQueryStringParameters("allowMissing" -> allowMissing.toString))
						/** Optional. The latency tolerance for the propagation of this product update. Defaults to latency-sensitive.<br>Possible values:<br>PRODUCT_UPDATE_LATENCY_TOLERANCE_UNSPECIFIED: Defaults to PRODUCT_UPDATE_LATENCY_TOLERANCE_LATENCY_SENSITIVE.<br>PRODUCT_UPDATE_LATENCY_TOLERANCE_LATENCY_SENSITIVE: The update will propagate to clients within several minutes on average and up to a few hours in rare cases. Throughput is limited to 7,200 updates per app per hour.<br>PRODUCT_UPDATE_LATENCY_TOLERANCE_LATENCY_TOLERANT: The update will propagate to clients within 24 hours. Supports high throughput of up to 720,000 updates per app per hour using batch modification methods. */
						def withLatencyTolerance(latencyTolerance: String) = new patch(req.addQueryStringParameters("latencyTolerance" -> latencyTolerance.toString))
						def withSubscriptionOffer(body: Schema.SubscriptionOffer) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.SubscriptionOffer])
					}
					object patch {
						def apply(packageName: String, productId: String, basePlanId: String, offerId: String, updateMask: String, regionsVersionVersion: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/subscriptions/${productId}/basePlans/${basePlanId}/offers/${offerId}").addQueryStringParameters("updateMask" -> updateMask.toString, "regionsVersion.version" -> regionsVersionVersion.toString))
					}
					class batchGet(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withBatchGetSubscriptionOffersRequest(body: Schema.BatchGetSubscriptionOffersRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.BatchGetSubscriptionOffersResponse])
					}
					object batchGet {
						def apply(packageName: String, productId: String, basePlanId: String)(using auth: AuthToken, ec: ExecutionContext): batchGet = new batchGet(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/subscriptions/${productId}/basePlans/${basePlanId}/offers:batchGet").addQueryStringParameters())
					}
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListSubscriptionOffersResponse]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListSubscriptionOffersResponse])
					}
					object list {
						def apply(packageName: String, productId: String, basePlanId: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/subscriptions/${productId}/basePlans/${basePlanId}/offers").addQueryStringParameters("pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
						given Conversion[list, Future[Schema.ListSubscriptionOffersResponse]] = (fun: list) => fun.apply()
					}
				}
			}
		}
	}
	object purchases {
		object products {
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ProductPurchase]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ProductPurchase])
			}
			object get {
				def apply(packageName: String, productId: String, token: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/purchases/products/${productId}/tokens/${token}").addQueryStringParameters())
				given Conversion[get, Future[Schema.ProductPurchase]] = (fun: get) => fun.apply()
			}
			class acknowledge(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withProductPurchasesAcknowledgeRequest(body: Schema.ProductPurchasesAcknowledgeRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_ => ())
			}
			object acknowledge {
				def apply(packageName: String, productId: String, token: String)(using auth: AuthToken, ec: ExecutionContext): acknowledge = new acknowledge(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/purchases/products/${productId}/tokens/${token}:acknowledge").addQueryStringParameters())
			}
			class consume(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Unit]) {
				def apply() = auth.exec(req,_.execute("POST")).map(_ => ())
			}
			object consume {
				def apply(packageName: String, productId: String, token: String)(using auth: AuthToken, ec: ExecutionContext): consume = new consume(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/purchases/products/${productId}/tokens/${token}:consume").addQueryStringParameters())
				given Conversion[consume, Future[Unit]] = (fun: consume) => fun.apply()
			}
		}
		object subscriptions {
			class defer(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withSubscriptionPurchasesDeferRequest(body: Schema.SubscriptionPurchasesDeferRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.SubscriptionPurchasesDeferResponse])
			}
			object defer {
				def apply(packageName: String, subscriptionId: String, token: String)(using auth: AuthToken, ec: ExecutionContext): defer = new defer(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/purchases/subscriptions/${subscriptionId}/tokens/${token}:defer").addQueryStringParameters())
			}
			class cancel(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Unit]) {
				def apply() = auth.exec(req,_.execute("POST")).map(_ => ())
			}
			object cancel {
				def apply(packageName: String, subscriptionId: String, token: String)(using auth: AuthToken, ec: ExecutionContext): cancel = new cancel(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/purchases/subscriptions/${subscriptionId}/tokens/${token}:cancel").addQueryStringParameters())
				given Conversion[cancel, Future[Unit]] = (fun: cancel) => fun.apply()
			}
			class acknowledge(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withSubscriptionPurchasesAcknowledgeRequest(body: Schema.SubscriptionPurchasesAcknowledgeRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_ => ())
			}
			object acknowledge {
				def apply(packageName: String, subscriptionId: String, token: String)(using auth: AuthToken, ec: ExecutionContext): acknowledge = new acknowledge(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/purchases/subscriptions/${subscriptionId}/tokens/${token}:acknowledge").addQueryStringParameters())
			}
			class refund(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Unit]) {
				def apply() = auth.exec(req,_.execute("POST")).map(_ => ())
			}
			object refund {
				def apply(packageName: String, subscriptionId: String, token: String)(using auth: AuthToken, ec: ExecutionContext): refund = new refund(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/purchases/subscriptions/${subscriptionId}/tokens/${token}:refund").addQueryStringParameters())
				given Conversion[refund, Future[Unit]] = (fun: refund) => fun.apply()
			}
			class revoke(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Unit]) {
				def apply() = auth.exec(req,_.execute("POST")).map(_ => ())
			}
			object revoke {
				def apply(packageName: String, subscriptionId: String, token: String)(using auth: AuthToken, ec: ExecutionContext): revoke = new revoke(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/purchases/subscriptions/${subscriptionId}/tokens/${token}:revoke").addQueryStringParameters())
				given Conversion[revoke, Future[Unit]] = (fun: revoke) => fun.apply()
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.SubscriptionPurchase]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.SubscriptionPurchase])
			}
			object get {
				def apply(packageName: String, subscriptionId: String, token: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/purchases/subscriptions/${subscriptionId}/tokens/${token}").addQueryStringParameters())
				given Conversion[get, Future[Schema.SubscriptionPurchase]] = (fun: get) => fun.apply()
			}
		}
		object subscriptionsv2 {
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.SubscriptionPurchaseV2]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.SubscriptionPurchaseV2])
			}
			object get {
				def apply(packageName: String, token: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/purchases/subscriptionsv2/tokens/${token}").addQueryStringParameters())
				given Conversion[get, Future[Schema.SubscriptionPurchaseV2]] = (fun: get) => fun.apply()
			}
			class revoke(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withRevokeSubscriptionPurchaseRequest(body: Schema.RevokeSubscriptionPurchaseRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.RevokeSubscriptionPurchaseResponse])
			}
			object revoke {
				def apply(packageName: String, token: String)(using auth: AuthToken, ec: ExecutionContext): revoke = new revoke(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/purchases/subscriptionsv2/tokens/${token}:revoke").addQueryStringParameters())
			}
		}
		object voidedpurchases {
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.VoidedPurchasesListResponse]) {
				/** Optional. Whether to include voided purchases of quantity-based partial refunds, which are applicable only to multi-quantity purchases. If true, additional voided purchases may be returned with voidedQuantity that indicates the refund quantity of a quantity-based partial refund. The default value is false. */
				def withIncludeQuantityBasedPartialRefund(includeQuantityBasedPartialRefund: Boolean) = new list(req.addQueryStringParameters("includeQuantityBasedPartialRefund" -> includeQuantityBasedPartialRefund.toString))
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.VoidedPurchasesListResponse])
			}
			object list {
				def apply(packageName: String, maxResults: Int, startIndex: Int, token: String, startTime: String, endTime: String, `type`: Int)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/purchases/voidedpurchases").addQueryStringParameters("maxResults" -> maxResults.toString, "startIndex" -> startIndex.toString, "token" -> token.toString, "startTime" -> startTime.toString, "endTime" -> endTime.toString, "type" -> `type`.toString))
				given Conversion[list, Future[Schema.VoidedPurchasesListResponse]] = (fun: list) => fun.apply()
			}
		}
	}
	object reviews {
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Review]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Review])
		}
		object get {
			def apply(packageName: String, reviewId: String, translationLanguage: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/reviews/${reviewId}").addQueryStringParameters("translationLanguage" -> translationLanguage.toString))
			given Conversion[get, Future[Schema.Review]] = (fun: get) => fun.apply()
		}
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ReviewsListResponse]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ReviewsListResponse])
		}
		object list {
			def apply(packageName: String, token: String, startIndex: Int, maxResults: Int, translationLanguage: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/reviews").addQueryStringParameters("token" -> token.toString, "startIndex" -> startIndex.toString, "maxResults" -> maxResults.toString, "translationLanguage" -> translationLanguage.toString))
			given Conversion[list, Future[Schema.ReviewsListResponse]] = (fun: list) => fun.apply()
		}
		class reply(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withReviewsReplyRequest(body: Schema.ReviewsReplyRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.ReviewsReplyResponse])
		}
		object reply {
			def apply(packageName: String, reviewId: String)(using auth: AuthToken, ec: ExecutionContext): reply = new reply(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/reviews/${reviewId}:reply").addQueryStringParameters())
		}
	}
	object systemapks {
		object variants {
			class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withVariant(body: Schema.Variant) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Variant])
			}
			object create {
				def apply(packageName: String, versionCode: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/systemApks/${versionCode}/variants").addQueryStringParameters())
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.SystemApksListResponse]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.SystemApksListResponse])
			}
			object list {
				def apply(packageName: String, versionCode: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/systemApks/${versionCode}/variants").addQueryStringParameters())
				given Conversion[list, Future[Schema.SystemApksListResponse]] = (fun: list) => fun.apply()
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Variant]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Variant])
			}
			object get {
				def apply(packageName: String, versionCode: String, variantId: Int)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/systemApks/${versionCode}/variants/${variantId}").addQueryStringParameters())
				given Conversion[get, Future[Schema.Variant]] = (fun: get) => fun.apply()
			}
			class download(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Unit]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_ => ())
			}
			object download {
				def apply(packageName: String, versionCode: String, variantId: Int)(using auth: AuthToken, ec: ExecutionContext): download = new download(ws.url(BASE_URL + s"androidpublisher/v3/applications/${packageName}/systemApks/${versionCode}/variants/${variantId}:download").addQueryStringParameters())
				given Conversion[download, Future[Unit]] = (fun: download) => fun.apply()
			}
		}
	}
}
