package com.boresjo.play.api.google.drivelabels

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://drivelabels.googleapis.com/"

	object users {
		class getCapabilities(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleAppsDriveLabelsV2UserCapabilities]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleAppsDriveLabelsV2UserCapabilities])
		}
		object getCapabilities {
			def apply(usersId :PlayApi, customer: String, name: String)(using auth: AuthToken, ec: ExecutionContext): getCapabilities = new getCapabilities(ws.url(BASE_URL + s"v2/users/${usersId}/capabilities").addQueryStringParameters("customer" -> customer.toString, "name" -> name.toString))
			given Conversion[getCapabilities, Future[Schema.GoogleAppsDriveLabelsV2UserCapabilities]] = (fun: getCapabilities) => fun.apply()
		}
	}
	object limits {
		class getLabel(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleAppsDriveLabelsV2LabelLimits]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleAppsDriveLabelsV2LabelLimits])
		}
		object getLabel {
			def apply(name: String)(using auth: AuthToken, ec: ExecutionContext): getLabel = new getLabel(ws.url(BASE_URL + s"v2/limits/label").addQueryStringParameters("name" -> name.toString))
			given Conversion[getLabel, Future[Schema.GoogleAppsDriveLabelsV2LabelLimits]] = (fun: getLabel) => fun.apply()
		}
	}
	object labels {
		class enable(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withGoogleAppsDriveLabelsV2EnableLabelRequest(body: Schema.GoogleAppsDriveLabelsV2EnableLabelRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleAppsDriveLabelsV2Label])
		}
		object enable {
			def apply(labelsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): enable = new enable(ws.url(BASE_URL + s"v2/labels/${labelsId}:enable").addQueryStringParameters("name" -> name.toString))
		}
		class updateLabelCopyMode(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withGoogleAppsDriveLabelsV2UpdateLabelCopyModeRequest(body: Schema.GoogleAppsDriveLabelsV2UpdateLabelCopyModeRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleAppsDriveLabelsV2Label])
		}
		object updateLabelCopyMode {
			def apply(labelsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): updateLabelCopyMode = new updateLabelCopyMode(ws.url(BASE_URL + s"v2/labels/${labelsId}:updateLabelCopyMode").addQueryStringParameters("name" -> name.toString))
		}
		class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withGoogleAppsDriveLabelsV2Label(body: Schema.GoogleAppsDriveLabelsV2Label) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleAppsDriveLabelsV2Label])
		}
		object create {
			def apply(useAdminAccess: Boolean, languageCode: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v2/labels").addQueryStringParameters("useAdminAccess" -> useAdminAccess.toString, "languageCode" -> languageCode.toString))
		}
		class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
			def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
		}
		object delete {
			def apply(labelsId :PlayApi, writeControlRequiredRevisionId: String, useAdminAccess: Boolean, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v2/labels/${labelsId}").addQueryStringParameters("writeControl.requiredRevisionId" -> writeControlRequiredRevisionId.toString, "useAdminAccess" -> useAdminAccess.toString, "name" -> name.toString))
			given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
		}
		class delta(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withGoogleAppsDriveLabelsV2DeltaUpdateLabelRequest(body: Schema.GoogleAppsDriveLabelsV2DeltaUpdateLabelRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleAppsDriveLabelsV2DeltaUpdateLabelResponse])
		}
		object delta {
			def apply(labelsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delta = new delta(ws.url(BASE_URL + s"v2/labels/${labelsId}:delta").addQueryStringParameters("name" -> name.toString))
		}
		class disable(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withGoogleAppsDriveLabelsV2DisableLabelRequest(body: Schema.GoogleAppsDriveLabelsV2DisableLabelRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleAppsDriveLabelsV2Label])
		}
		object disable {
			def apply(labelsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): disable = new disable(ws.url(BASE_URL + s"v2/labels/${labelsId}:disable").addQueryStringParameters("name" -> name.toString))
		}
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleAppsDriveLabelsV2Label]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleAppsDriveLabelsV2Label])
		}
		object get {
			def apply(labelsId :PlayApi, useAdminAccess: Boolean, languageCode: String, name: String, view: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/labels/${labelsId}").addQueryStringParameters("useAdminAccess" -> useAdminAccess.toString, "languageCode" -> languageCode.toString, "name" -> name.toString, "view" -> view.toString))
			given Conversion[get, Future[Schema.GoogleAppsDriveLabelsV2Label]] = (fun: get) => fun.apply()
		}
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleAppsDriveLabelsV2ListLabelsResponse]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleAppsDriveLabelsV2ListLabelsResponse])
		}
		object list {
			def apply(useAdminAccess: Boolean, publishedOnly: Boolean, pageSize: Int, view: String, pageToken: String, customer: String, minimumRole: String, languageCode: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/labels").addQueryStringParameters("useAdminAccess" -> useAdminAccess.toString, "publishedOnly" -> publishedOnly.toString, "pageSize" -> pageSize.toString, "view" -> view.toString, "pageToken" -> pageToken.toString, "customer" -> customer.toString, "minimumRole" -> minimumRole.toString, "languageCode" -> languageCode.toString))
			given Conversion[list, Future[Schema.GoogleAppsDriveLabelsV2ListLabelsResponse]] = (fun: list) => fun.apply()
		}
		class publish(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withGoogleAppsDriveLabelsV2PublishLabelRequest(body: Schema.GoogleAppsDriveLabelsV2PublishLabelRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleAppsDriveLabelsV2Label])
		}
		object publish {
			def apply(labelsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): publish = new publish(ws.url(BASE_URL + s"v2/labels/${labelsId}:publish").addQueryStringParameters("name" -> name.toString))
		}
		class updatePermissions(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withGoogleAppsDriveLabelsV2LabelPermission(body: Schema.GoogleAppsDriveLabelsV2LabelPermission) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleAppsDriveLabelsV2LabelPermission])
		}
		object updatePermissions {
			def apply(labelsId :PlayApi, useAdminAccess: Boolean, parent: String)(using auth: AuthToken, ec: ExecutionContext): updatePermissions = new updatePermissions(ws.url(BASE_URL + s"v2/labels/${labelsId}/permissions").addQueryStringParameters("useAdminAccess" -> useAdminAccess.toString, "parent" -> parent.toString))
		}
		object locks {
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleAppsDriveLabelsV2ListLabelLocksResponse]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleAppsDriveLabelsV2ListLabelLocksResponse])
			}
			object list {
				def apply(labelsId :PlayApi, pageSize: Int, pageToken: String, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/labels/${labelsId}/locks").addQueryStringParameters("pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "parent" -> parent.toString))
				given Conversion[list, Future[Schema.GoogleAppsDriveLabelsV2ListLabelLocksResponse]] = (fun: list) => fun.apply()
			}
		}
		object permissions {
			class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGoogleAppsDriveLabelsV2LabelPermission(body: Schema.GoogleAppsDriveLabelsV2LabelPermission) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleAppsDriveLabelsV2LabelPermission])
			}
			object create {
				def apply(labelsId :PlayApi, parent: String, useAdminAccess: Boolean)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v2/labels/${labelsId}/permissions").addQueryStringParameters("parent" -> parent.toString, "useAdminAccess" -> useAdminAccess.toString))
			}
			class batchUpdate(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGoogleAppsDriveLabelsV2BatchUpdateLabelPermissionsRequest(body: Schema.GoogleAppsDriveLabelsV2BatchUpdateLabelPermissionsRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleAppsDriveLabelsV2BatchUpdateLabelPermissionsResponse])
			}
			object batchUpdate {
				def apply(labelsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): batchUpdate = new batchUpdate(ws.url(BASE_URL + s"v2/labels/${labelsId}/permissions:batchUpdate").addQueryStringParameters("parent" -> parent.toString))
			}
			class batchDelete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGoogleAppsDriveLabelsV2BatchDeleteLabelPermissionsRequest(body: Schema.GoogleAppsDriveLabelsV2BatchDeleteLabelPermissionsRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleProtobufEmpty])
			}
			object batchDelete {
				def apply(labelsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): batchDelete = new batchDelete(ws.url(BASE_URL + s"v2/labels/${labelsId}/permissions:batchDelete").addQueryStringParameters("parent" -> parent.toString))
			}
			class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
				def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
			}
			object delete {
				def apply(labelsId :PlayApi, permissionsId :PlayApi, useAdminAccess: Boolean, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v2/labels/${labelsId}/permissions/${permissionsId}").addQueryStringParameters("useAdminAccess" -> useAdminAccess.toString, "name" -> name.toString))
				given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleAppsDriveLabelsV2ListLabelPermissionsResponse]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleAppsDriveLabelsV2ListLabelPermissionsResponse])
			}
			object list {
				def apply(labelsId :PlayApi, useAdminAccess: Boolean, pageSize: Int, parent: String, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/labels/${labelsId}/permissions").addQueryStringParameters("useAdminAccess" -> useAdminAccess.toString, "pageSize" -> pageSize.toString, "parent" -> parent.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.GoogleAppsDriveLabelsV2ListLabelPermissionsResponse]] = (fun: list) => fun.apply()
			}
		}
		object revisions {
			class updatePermissions(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGoogleAppsDriveLabelsV2LabelPermission(body: Schema.GoogleAppsDriveLabelsV2LabelPermission) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleAppsDriveLabelsV2LabelPermission])
			}
			object updatePermissions {
				def apply(labelsId :PlayApi, revisionsId :PlayApi, parent: String, useAdminAccess: Boolean)(using auth: AuthToken, ec: ExecutionContext): updatePermissions = new updatePermissions(ws.url(BASE_URL + s"v2/labels/${labelsId}/revisions/${revisionsId}/permissions").addQueryStringParameters("parent" -> parent.toString, "useAdminAccess" -> useAdminAccess.toString))
			}
			object permissions {
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleAppsDriveLabelsV2LabelPermission(body: Schema.GoogleAppsDriveLabelsV2LabelPermission) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleAppsDriveLabelsV2LabelPermission])
				}
				object create {
					def apply(labelsId :PlayApi, revisionsId :PlayApi, useAdminAccess: Boolean, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v2/labels/${labelsId}/revisions/${revisionsId}/permissions").addQueryStringParameters("useAdminAccess" -> useAdminAccess.toString, "parent" -> parent.toString))
				}
				class batchUpdate(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleAppsDriveLabelsV2BatchUpdateLabelPermissionsRequest(body: Schema.GoogleAppsDriveLabelsV2BatchUpdateLabelPermissionsRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleAppsDriveLabelsV2BatchUpdateLabelPermissionsResponse])
				}
				object batchUpdate {
					def apply(labelsId :PlayApi, revisionsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): batchUpdate = new batchUpdate(ws.url(BASE_URL + s"v2/labels/${labelsId}/revisions/${revisionsId}/permissions:batchUpdate").addQueryStringParameters("parent" -> parent.toString))
				}
				class batchDelete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleAppsDriveLabelsV2BatchDeleteLabelPermissionsRequest(body: Schema.GoogleAppsDriveLabelsV2BatchDeleteLabelPermissionsRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleProtobufEmpty])
				}
				object batchDelete {
					def apply(labelsId :PlayApi, revisionsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): batchDelete = new batchDelete(ws.url(BASE_URL + s"v2/labels/${labelsId}/revisions/${revisionsId}/permissions:batchDelete").addQueryStringParameters("parent" -> parent.toString))
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
					def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
				}
				object delete {
					def apply(labelsId :PlayApi, revisionsId :PlayApi, permissionsId :PlayApi, name: String, useAdminAccess: Boolean)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v2/labels/${labelsId}/revisions/${revisionsId}/permissions/${permissionsId}").addQueryStringParameters("name" -> name.toString, "useAdminAccess" -> useAdminAccess.toString))
					given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleAppsDriveLabelsV2ListLabelPermissionsResponse]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleAppsDriveLabelsV2ListLabelPermissionsResponse])
				}
				object list {
					def apply(labelsId :PlayApi, revisionsId :PlayApi, useAdminAccess: Boolean, pageSize: Int, pageToken: String, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/labels/${labelsId}/revisions/${revisionsId}/permissions").addQueryStringParameters("useAdminAccess" -> useAdminAccess.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "parent" -> parent.toString))
					given Conversion[list, Future[Schema.GoogleAppsDriveLabelsV2ListLabelPermissionsResponse]] = (fun: list) => fun.apply()
				}
			}
			object locks {
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleAppsDriveLabelsV2ListLabelLocksResponse]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleAppsDriveLabelsV2ListLabelLocksResponse])
				}
				object list {
					def apply(labelsId :PlayApi, revisionsId :PlayApi, pageToken: String, pageSize: Int, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/labels/${labelsId}/revisions/${revisionsId}/locks").addQueryStringParameters("pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString, "parent" -> parent.toString))
					given Conversion[list, Future[Schema.GoogleAppsDriveLabelsV2ListLabelLocksResponse]] = (fun: list) => fun.apply()
				}
			}
		}
	}
}
