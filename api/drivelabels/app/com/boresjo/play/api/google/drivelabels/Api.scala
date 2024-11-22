package com.boresjo.play.api.google.drivelabels

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
		"""https://www.googleapis.com/auth/drive.admin.labels""" /* See, edit, create, and delete all Google Drive labels in your organization, and see your organization's label-related admin policies */,
		"""https://www.googleapis.com/auth/drive.admin.labels.readonly""" /* See all Google Drive labels and label-related admin policies in your organization */,
		"""https://www.googleapis.com/auth/drive.labels""" /* See, edit, create, and delete your Google Drive labels */,
		"""https://www.googleapis.com/auth/drive.labels.readonly""" /* See your Google Drive labels */
	)

	private val BASE_URL = "https://drivelabels.googleapis.com/"

	object users {
		/** Gets the user capabilities. */
		class getCapabilities(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleAppsDriveLabelsV2UserCapabilities]) {
			val scopes = Seq("""https://www.googleapis.com/auth/drive.admin.labels""", """https://www.googleapis.com/auth/drive.admin.labels.readonly""", """https://www.googleapis.com/auth/drive.labels""", """https://www.googleapis.com/auth/drive.labels.readonly""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleAppsDriveLabelsV2UserCapabilities])
		}
		object getCapabilities {
			def apply(usersId :PlayApi, customer: String, name: String)(using signer: RequestSigner, ec: ExecutionContext): getCapabilities = new getCapabilities(ws.url(BASE_URL + s"v2/users/${usersId}/capabilities").addQueryStringParameters("customer" -> customer.toString, "name" -> name.toString))
			given Conversion[getCapabilities, Future[Schema.GoogleAppsDriveLabelsV2UserCapabilities]] = (fun: getCapabilities) => fun.apply()
		}
	}
	object limits {
		/** Get the constraints on the structure of a Label; such as, the maximum number of Fields allowed and maximum length of the label title. */
		class getLabel(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleAppsDriveLabelsV2LabelLimits]) {
			val scopes = Seq("""https://www.googleapis.com/auth/drive.admin.labels""", """https://www.googleapis.com/auth/drive.admin.labels.readonly""", """https://www.googleapis.com/auth/drive.labels""", """https://www.googleapis.com/auth/drive.labels.readonly""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleAppsDriveLabelsV2LabelLimits])
		}
		object getLabel {
			def apply(name: String)(using signer: RequestSigner, ec: ExecutionContext): getLabel = new getLabel(ws.url(BASE_URL + s"v2/limits/label").addQueryStringParameters("name" -> name.toString))
			given Conversion[getLabel, Future[Schema.GoogleAppsDriveLabelsV2LabelLimits]] = (fun: getLabel) => fun.apply()
		}
	}
	object labels {
		/** Enable a disabled Label and restore it to its published state. This will result in a new published revision based on the current disabled published revision. If there is an existing disabled draft revision, a new revision will be created based on that draft and will be enabled. */
		class enable(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/drive.admin.labels""", """https://www.googleapis.com/auth/drive.labels""")
			/** Perform the request */
			def withGoogleAppsDriveLabelsV2EnableLabelRequest(body: Schema.GoogleAppsDriveLabelsV2EnableLabelRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleAppsDriveLabelsV2Label])
		}
		object enable {
			def apply(labelsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): enable = new enable(ws.url(BASE_URL + s"v2/labels/${labelsId}:enable").addQueryStringParameters("name" -> name.toString))
		}
		/** Updates a Label's `CopyMode`. Changes to this policy are not revisioned, do not require publishing, and take effect immediately. */
		class updateLabelCopyMode(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/drive.admin.labels""", """https://www.googleapis.com/auth/drive.labels""")
			/** Perform the request */
			def withGoogleAppsDriveLabelsV2UpdateLabelCopyModeRequest(body: Schema.GoogleAppsDriveLabelsV2UpdateLabelCopyModeRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleAppsDriveLabelsV2Label])
		}
		object updateLabelCopyMode {
			def apply(labelsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): updateLabelCopyMode = new updateLabelCopyMode(ws.url(BASE_URL + s"v2/labels/${labelsId}:updateLabelCopyMode").addQueryStringParameters("name" -> name.toString))
		}
		/** Creates a new Label. */
		class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/drive.admin.labels""", """https://www.googleapis.com/auth/drive.labels""")
			/** Perform the request */
			def withGoogleAppsDriveLabelsV2Label(body: Schema.GoogleAppsDriveLabelsV2Label) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleAppsDriveLabelsV2Label])
		}
		object create {
			def apply(useAdminAccess: Boolean, languageCode: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v2/labels").addQueryStringParameters("useAdminAccess" -> useAdminAccess.toString, "languageCode" -> languageCode.toString))
		}
		/** Permanently deletes a Label and related metadata on Drive Items. Once deleted, the Label and related Drive item metadata will be deleted. Only draft Labels, and disabled Labels may be deleted. */
		class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
			val scopes = Seq("""https://www.googleapis.com/auth/drive.admin.labels""", """https://www.googleapis.com/auth/drive.labels""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
		}
		object delete {
			def apply(labelsId :PlayApi, writeControlRequiredRevisionId: String, useAdminAccess: Boolean, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v2/labels/${labelsId}").addQueryStringParameters("writeControl.requiredRevisionId" -> writeControlRequiredRevisionId.toString, "useAdminAccess" -> useAdminAccess.toString, "name" -> name.toString))
			given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
		}
		/** Updates a single Label by applying a set of update requests resulting in a new draft revision. The batch update is all-or-nothing: If any of the update requests are invalid, no changes are applied. The resulting draft revision must be published before the changes may be used with Drive Items. */
		class delta(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/drive.admin.labels""", """https://www.googleapis.com/auth/drive.labels""")
			/** Perform the request */
			def withGoogleAppsDriveLabelsV2DeltaUpdateLabelRequest(body: Schema.GoogleAppsDriveLabelsV2DeltaUpdateLabelRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleAppsDriveLabelsV2DeltaUpdateLabelResponse])
		}
		object delta {
			def apply(labelsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delta = new delta(ws.url(BASE_URL + s"v2/labels/${labelsId}:delta").addQueryStringParameters("name" -> name.toString))
		}
		/** Disable a published Label. Disabling a Label will result in a new disabled published revision based on the current published revision. If there is a draft revision, a new disabled draft revision will be created based on the latest draft revision. Older draft revisions will be deleted. Once disabled, a label may be deleted with `DeleteLabel`. */
		class disable(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/drive.admin.labels""", """https://www.googleapis.com/auth/drive.labels""")
			/** Perform the request */
			def withGoogleAppsDriveLabelsV2DisableLabelRequest(body: Schema.GoogleAppsDriveLabelsV2DisableLabelRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleAppsDriveLabelsV2Label])
		}
		object disable {
			def apply(labelsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): disable = new disable(ws.url(BASE_URL + s"v2/labels/${labelsId}:disable").addQueryStringParameters("name" -> name.toString))
		}
		/** Get a label by its resource name. Resource name may be any of: &#42; `labels/{id}` - See `labels/{id}@latest` &#42; `labels/{id}@latest` - Gets the latest revision of the label. &#42; `labels/{id}@published` - Gets the current published revision of the label. &#42; `labels/{id}@{revision_id}` - Gets the label at the specified revision ID. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleAppsDriveLabelsV2Label]) {
			val scopes = Seq("""https://www.googleapis.com/auth/drive.admin.labels""", """https://www.googleapis.com/auth/drive.admin.labels.readonly""", """https://www.googleapis.com/auth/drive.labels""", """https://www.googleapis.com/auth/drive.labels.readonly""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleAppsDriveLabelsV2Label])
		}
		object get {
			def apply(labelsId :PlayApi, useAdminAccess: Boolean, languageCode: String, name: String, view: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/labels/${labelsId}").addQueryStringParameters("useAdminAccess" -> useAdminAccess.toString, "languageCode" -> languageCode.toString, "name" -> name.toString, "view" -> view.toString))
			given Conversion[get, Future[Schema.GoogleAppsDriveLabelsV2Label]] = (fun: get) => fun.apply()
		}
		/** List labels. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleAppsDriveLabelsV2ListLabelsResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/drive.admin.labels""", """https://www.googleapis.com/auth/drive.admin.labels.readonly""", """https://www.googleapis.com/auth/drive.labels""", """https://www.googleapis.com/auth/drive.labels.readonly""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleAppsDriveLabelsV2ListLabelsResponse])
		}
		object list {
			def apply(useAdminAccess: Boolean, publishedOnly: Boolean, pageSize: Int, view: String, pageToken: String, customer: String, minimumRole: String, languageCode: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/labels").addQueryStringParameters("useAdminAccess" -> useAdminAccess.toString, "publishedOnly" -> publishedOnly.toString, "pageSize" -> pageSize.toString, "view" -> view.toString, "pageToken" -> pageToken.toString, "customer" -> customer.toString, "minimumRole" -> minimumRole.toString, "languageCode" -> languageCode.toString))
			given Conversion[list, Future[Schema.GoogleAppsDriveLabelsV2ListLabelsResponse]] = (fun: list) => fun.apply()
		}
		/** Publish all draft changes to the Label. Once published, the Label may not return to its draft state. See `google.apps.drive.labels.v2.Lifecycle` for more information. Publishing a Label will result in a new published revision. All previous draft revisions will be deleted. Previous published revisions will be kept but are subject to automated deletion as needed. Once published, some changes are no longer permitted. Generally, any change that would invalidate or cause new restrictions on existing metadata related to the Label will be rejected. For example, the following changes to a Label will be rejected after the Label is published: &#42; The label cannot be directly deleted. It must be disabled first, then deleted. &#42; Field.FieldType cannot be changed. &#42; Changes to Field validation options cannot reject something that was previously accepted. &#42; Reducing the max entries. */
		class publish(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/drive.admin.labels""", """https://www.googleapis.com/auth/drive.labels""")
			/** Perform the request */
			def withGoogleAppsDriveLabelsV2PublishLabelRequest(body: Schema.GoogleAppsDriveLabelsV2PublishLabelRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleAppsDriveLabelsV2Label])
		}
		object publish {
			def apply(labelsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): publish = new publish(ws.url(BASE_URL + s"v2/labels/${labelsId}:publish").addQueryStringParameters("name" -> name.toString))
		}
		/** Updates a Label's permissions. If a permission for the indicated principal doesn't exist, a new Label Permission is created, otherwise the existing permission is updated. Permissions affect the Label resource as a whole, are not revisioned, and do not require publishing. */
		class updatePermissions(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/drive.admin.labels""", """https://www.googleapis.com/auth/drive.labels""")
			/** Perform the request */
			def withGoogleAppsDriveLabelsV2LabelPermission(body: Schema.GoogleAppsDriveLabelsV2LabelPermission) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleAppsDriveLabelsV2LabelPermission])
		}
		object updatePermissions {
			def apply(labelsId :PlayApi, useAdminAccess: Boolean, parent: String)(using signer: RequestSigner, ec: ExecutionContext): updatePermissions = new updatePermissions(ws.url(BASE_URL + s"v2/labels/${labelsId}/permissions").addQueryStringParameters("useAdminAccess" -> useAdminAccess.toString, "parent" -> parent.toString))
		}
		object locks {
			/** Lists the LabelLocks on a Label. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleAppsDriveLabelsV2ListLabelLocksResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/drive.admin.labels""", """https://www.googleapis.com/auth/drive.admin.labels.readonly""", """https://www.googleapis.com/auth/drive.labels""", """https://www.googleapis.com/auth/drive.labels.readonly""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleAppsDriveLabelsV2ListLabelLocksResponse])
			}
			object list {
				def apply(labelsId :PlayApi, pageSize: Int, pageToken: String, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/labels/${labelsId}/locks").addQueryStringParameters("pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "parent" -> parent.toString))
				given Conversion[list, Future[Schema.GoogleAppsDriveLabelsV2ListLabelLocksResponse]] = (fun: list) => fun.apply()
			}
		}
		object permissions {
			/** Updates a Label's permissions. If a permission for the indicated principal doesn't exist, a new Label Permission is created, otherwise the existing permission is updated. Permissions affect the Label resource as a whole, are not revisioned, and do not require publishing. */
			class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/drive.admin.labels""", """https://www.googleapis.com/auth/drive.labels""")
				/** Perform the request */
				def withGoogleAppsDriveLabelsV2LabelPermission(body: Schema.GoogleAppsDriveLabelsV2LabelPermission) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleAppsDriveLabelsV2LabelPermission])
			}
			object create {
				def apply(labelsId :PlayApi, parent: String, useAdminAccess: Boolean)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v2/labels/${labelsId}/permissions").addQueryStringParameters("parent" -> parent.toString, "useAdminAccess" -> useAdminAccess.toString))
			}
			/** Updates Label permissions. If a permission for the indicated principal doesn't exist, a new Label Permission is created, otherwise the existing permission is updated. Permissions affect the Label resource as a whole, are not revisioned, and do not require publishing. */
			class batchUpdate(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/drive.admin.labels""", """https://www.googleapis.com/auth/drive.labels""")
				/** Perform the request */
				def withGoogleAppsDriveLabelsV2BatchUpdateLabelPermissionsRequest(body: Schema.GoogleAppsDriveLabelsV2BatchUpdateLabelPermissionsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleAppsDriveLabelsV2BatchUpdateLabelPermissionsResponse])
			}
			object batchUpdate {
				def apply(labelsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): batchUpdate = new batchUpdate(ws.url(BASE_URL + s"v2/labels/${labelsId}/permissions:batchUpdate").addQueryStringParameters("parent" -> parent.toString))
			}
			/** Deletes Label permissions. Permissions affect the Label resource as a whole, are not revisioned, and do not require publishing. */
			class batchDelete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/drive.admin.labels""", """https://www.googleapis.com/auth/drive.labels""")
				/** Perform the request */
				def withGoogleAppsDriveLabelsV2BatchDeleteLabelPermissionsRequest(body: Schema.GoogleAppsDriveLabelsV2BatchDeleteLabelPermissionsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleProtobufEmpty])
			}
			object batchDelete {
				def apply(labelsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): batchDelete = new batchDelete(ws.url(BASE_URL + s"v2/labels/${labelsId}/permissions:batchDelete").addQueryStringParameters("parent" -> parent.toString))
			}
			/** Deletes a Label's permission. Permissions affect the Label resource as a whole, are not revisioned, and do not require publishing. */
			class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
				val scopes = Seq("""https://www.googleapis.com/auth/drive.admin.labels""", """https://www.googleapis.com/auth/drive.labels""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
			}
			object delete {
				def apply(labelsId :PlayApi, permissionsId :PlayApi, useAdminAccess: Boolean, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v2/labels/${labelsId}/permissions/${permissionsId}").addQueryStringParameters("useAdminAccess" -> useAdminAccess.toString, "name" -> name.toString))
				given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
			}
			/** Lists a Label's permissions. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleAppsDriveLabelsV2ListLabelPermissionsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/drive.admin.labels""", """https://www.googleapis.com/auth/drive.admin.labels.readonly""", """https://www.googleapis.com/auth/drive.labels""", """https://www.googleapis.com/auth/drive.labels.readonly""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleAppsDriveLabelsV2ListLabelPermissionsResponse])
			}
			object list {
				def apply(labelsId :PlayApi, useAdminAccess: Boolean, pageSize: Int, parent: String, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/labels/${labelsId}/permissions").addQueryStringParameters("useAdminAccess" -> useAdminAccess.toString, "pageSize" -> pageSize.toString, "parent" -> parent.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.GoogleAppsDriveLabelsV2ListLabelPermissionsResponse]] = (fun: list) => fun.apply()
			}
		}
		object revisions {
			/** Updates a Label's permissions. If a permission for the indicated principal doesn't exist, a new Label Permission is created, otherwise the existing permission is updated. Permissions affect the Label resource as a whole, are not revisioned, and do not require publishing. */
			class updatePermissions(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/drive.admin.labels""", """https://www.googleapis.com/auth/drive.labels""")
				/** Perform the request */
				def withGoogleAppsDriveLabelsV2LabelPermission(body: Schema.GoogleAppsDriveLabelsV2LabelPermission) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleAppsDriveLabelsV2LabelPermission])
			}
			object updatePermissions {
				def apply(labelsId :PlayApi, revisionsId :PlayApi, parent: String, useAdminAccess: Boolean)(using signer: RequestSigner, ec: ExecutionContext): updatePermissions = new updatePermissions(ws.url(BASE_URL + s"v2/labels/${labelsId}/revisions/${revisionsId}/permissions").addQueryStringParameters("parent" -> parent.toString, "useAdminAccess" -> useAdminAccess.toString))
			}
			object permissions {
				/** Updates a Label's permissions. If a permission for the indicated principal doesn't exist, a new Label Permission is created, otherwise the existing permission is updated. Permissions affect the Label resource as a whole, are not revisioned, and do not require publishing. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/drive.admin.labels""", """https://www.googleapis.com/auth/drive.labels""")
					/** Perform the request */
					def withGoogleAppsDriveLabelsV2LabelPermission(body: Schema.GoogleAppsDriveLabelsV2LabelPermission) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleAppsDriveLabelsV2LabelPermission])
				}
				object create {
					def apply(labelsId :PlayApi, revisionsId :PlayApi, useAdminAccess: Boolean, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v2/labels/${labelsId}/revisions/${revisionsId}/permissions").addQueryStringParameters("useAdminAccess" -> useAdminAccess.toString, "parent" -> parent.toString))
				}
				/** Updates Label permissions. If a permission for the indicated principal doesn't exist, a new Label Permission is created, otherwise the existing permission is updated. Permissions affect the Label resource as a whole, are not revisioned, and do not require publishing. */
				class batchUpdate(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/drive.admin.labels""", """https://www.googleapis.com/auth/drive.labels""")
					/** Perform the request */
					def withGoogleAppsDriveLabelsV2BatchUpdateLabelPermissionsRequest(body: Schema.GoogleAppsDriveLabelsV2BatchUpdateLabelPermissionsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleAppsDriveLabelsV2BatchUpdateLabelPermissionsResponse])
				}
				object batchUpdate {
					def apply(labelsId :PlayApi, revisionsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): batchUpdate = new batchUpdate(ws.url(BASE_URL + s"v2/labels/${labelsId}/revisions/${revisionsId}/permissions:batchUpdate").addQueryStringParameters("parent" -> parent.toString))
				}
				/** Deletes Label permissions. Permissions affect the Label resource as a whole, are not revisioned, and do not require publishing. */
				class batchDelete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/drive.admin.labels""", """https://www.googleapis.com/auth/drive.labels""")
					/** Perform the request */
					def withGoogleAppsDriveLabelsV2BatchDeleteLabelPermissionsRequest(body: Schema.GoogleAppsDriveLabelsV2BatchDeleteLabelPermissionsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleProtobufEmpty])
				}
				object batchDelete {
					def apply(labelsId :PlayApi, revisionsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): batchDelete = new batchDelete(ws.url(BASE_URL + s"v2/labels/${labelsId}/revisions/${revisionsId}/permissions:batchDelete").addQueryStringParameters("parent" -> parent.toString))
				}
				/** Deletes a Label's permission. Permissions affect the Label resource as a whole, are not revisioned, and do not require publishing. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
					val scopes = Seq("""https://www.googleapis.com/auth/drive.admin.labels""", """https://www.googleapis.com/auth/drive.labels""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
				}
				object delete {
					def apply(labelsId :PlayApi, revisionsId :PlayApi, permissionsId :PlayApi, name: String, useAdminAccess: Boolean)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v2/labels/${labelsId}/revisions/${revisionsId}/permissions/${permissionsId}").addQueryStringParameters("name" -> name.toString, "useAdminAccess" -> useAdminAccess.toString))
					given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
				}
				/** Lists a Label's permissions. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleAppsDriveLabelsV2ListLabelPermissionsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/drive.admin.labels""", """https://www.googleapis.com/auth/drive.admin.labels.readonly""", """https://www.googleapis.com/auth/drive.labels""", """https://www.googleapis.com/auth/drive.labels.readonly""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleAppsDriveLabelsV2ListLabelPermissionsResponse])
				}
				object list {
					def apply(labelsId :PlayApi, revisionsId :PlayApi, useAdminAccess: Boolean, pageSize: Int, pageToken: String, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/labels/${labelsId}/revisions/${revisionsId}/permissions").addQueryStringParameters("useAdminAccess" -> useAdminAccess.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "parent" -> parent.toString))
					given Conversion[list, Future[Schema.GoogleAppsDriveLabelsV2ListLabelPermissionsResponse]] = (fun: list) => fun.apply()
				}
			}
			object locks {
				/** Lists the LabelLocks on a Label. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleAppsDriveLabelsV2ListLabelLocksResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/drive.admin.labels""", """https://www.googleapis.com/auth/drive.admin.labels.readonly""", """https://www.googleapis.com/auth/drive.labels""", """https://www.googleapis.com/auth/drive.labels.readonly""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleAppsDriveLabelsV2ListLabelLocksResponse])
				}
				object list {
					def apply(labelsId :PlayApi, revisionsId :PlayApi, pageToken: String, pageSize: Int, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/labels/${labelsId}/revisions/${revisionsId}/locks").addQueryStringParameters("pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString, "parent" -> parent.toString))
					given Conversion[list, Future[Schema.GoogleAppsDriveLabelsV2ListLabelLocksResponse]] = (fun: list) => fun.apply()
				}
			}
		}
	}
}
