package com.boresjo.play.api.google.essentialcontacts

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://essentialcontacts.googleapis.com/"

	object projects {
		object contacts {
			class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
				def apply() = req.execute("DELETE").map(_.json.as[Schema.GoogleProtobufEmpty])
			}
			object delete {
				def apply(projectsId :PlayApi, contactsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/contacts/${contactsId}")).addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
			}
			class sendTestMessage(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGoogleCloudEssentialcontactsV1SendTestMessageRequest(body: Schema.GoogleCloudEssentialcontactsV1SendTestMessageRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleProtobufEmpty])
			}
			object sendTestMessage {
				def apply(projectsId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): sendTestMessage = new sendTestMessage(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/contacts:sendTestMessage")).addQueryStringParameters("resource" -> resource.toString))
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudEssentialcontactsV1Contact]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudEssentialcontactsV1Contact])
			}
			object get {
				def apply(projectsId :PlayApi, contactsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/contacts/${contactsId}")).addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.GoogleCloudEssentialcontactsV1Contact]] = (fun: get) => fun.apply()
			}
			class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				/** Optional. The update mask applied to the resource. For the `FieldMask` definition, see https://developers.google.com/protocol-buffers/docs/reference/google.protobuf#fieldmask<br>Format: google-fieldmask */
				def withUpdateMask(updateMask: String) = new patch(req.addQueryStringParameters("updateMask" -> updateMask.toString))
				def withGoogleCloudEssentialcontactsV1Contact(body: Schema.GoogleCloudEssentialcontactsV1Contact) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.GoogleCloudEssentialcontactsV1Contact])
			}
			object patch {
				def apply(projectsId :PlayApi, contactsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/contacts/${contactsId}")).addQueryStringParameters("name" -> name.toString))
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudEssentialcontactsV1ListContactsResponse]) {
				/** Optional. If present, retrieves the next batch of results from the preceding call to this method. `page_token` must be the value of `next_page_token` from the previous response. The values of other method parameters should be identical to those in the previous call. */
				def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
				/** Optional. The maximum number of results to return from this request. Non-positive values are ignored. The presence of `next_page_token` in the response indicates that more results might be available. If not specified, the default page_size is 100.<br>Format: int32 */
				def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
				def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudEssentialcontactsV1ListContactsResponse])
			}
			object list {
				def apply(projectsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/contacts")).addQueryStringParameters("parent" -> parent.toString))
				given Conversion[list, Future[Schema.GoogleCloudEssentialcontactsV1ListContactsResponse]] = (fun: list) => fun.apply()
			}
			class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGoogleCloudEssentialcontactsV1Contact(body: Schema.GoogleCloudEssentialcontactsV1Contact) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleCloudEssentialcontactsV1Contact])
			}
			object create {
				def apply(projectsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/contacts")).addQueryStringParameters("parent" -> parent.toString))
			}
			class compute(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudEssentialcontactsV1ComputeContactsResponse]) {
				/** Optional. If present, retrieves the next batch of results from the preceding call to this method. `page_token` must be the value of `next_page_token` from the previous response. The values of other method parameters should be identical to those in the previous call. */
				def withPageToken(pageToken: String) = new compute(req.addQueryStringParameters("pageToken" -> pageToken.toString))
				/** Optional. The maximum number of results to return from this request. Non-positive values are ignored. The presence of `next_page_token` in the response indicates that more results might be available. If not specified, the default page_size is 100.<br>Format: int32 */
				def withPageSize(pageSize: Int) = new compute(req.addQueryStringParameters("pageSize" -> pageSize.toString))
				def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudEssentialcontactsV1ComputeContactsResponse])
			}
			object compute {
				def apply(projectsId :PlayApi, parent: String, notificationCategories: String)(using auth: AuthToken, ec: ExecutionContext): compute = new compute(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/contacts:compute")).addQueryStringParameters("parent" -> parent.toString, "notificationCategories" -> notificationCategories.toString))
				given Conversion[compute, Future[Schema.GoogleCloudEssentialcontactsV1ComputeContactsResponse]] = (fun: compute) => fun.apply()
			}
		}
	}
	object folders {
		object contacts {
			class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
				def apply() = req.execute("DELETE").map(_.json.as[Schema.GoogleProtobufEmpty])
			}
			object delete {
				def apply(foldersId :PlayApi, contactsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/folders/${foldersId}/contacts/${contactsId}")).addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
			}
			class sendTestMessage(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGoogleCloudEssentialcontactsV1SendTestMessageRequest(body: Schema.GoogleCloudEssentialcontactsV1SendTestMessageRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleProtobufEmpty])
			}
			object sendTestMessage {
				def apply(foldersId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): sendTestMessage = new sendTestMessage(auth(ws.url(BASE_URL + s"v1/folders/${foldersId}/contacts:sendTestMessage")).addQueryStringParameters("resource" -> resource.toString))
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudEssentialcontactsV1Contact]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudEssentialcontactsV1Contact])
			}
			object get {
				def apply(foldersId :PlayApi, contactsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/folders/${foldersId}/contacts/${contactsId}")).addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.GoogleCloudEssentialcontactsV1Contact]] = (fun: get) => fun.apply()
			}
			class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				/** Optional. The update mask applied to the resource. For the `FieldMask` definition, see https://developers.google.com/protocol-buffers/docs/reference/google.protobuf#fieldmask<br>Format: google-fieldmask */
				def withUpdateMask(updateMask: String) = new patch(req.addQueryStringParameters("updateMask" -> updateMask.toString))
				def withGoogleCloudEssentialcontactsV1Contact(body: Schema.GoogleCloudEssentialcontactsV1Contact) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.GoogleCloudEssentialcontactsV1Contact])
			}
			object patch {
				def apply(foldersId :PlayApi, contactsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"v1/folders/${foldersId}/contacts/${contactsId}")).addQueryStringParameters("name" -> name.toString))
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudEssentialcontactsV1ListContactsResponse]) {
				/** Optional. If present, retrieves the next batch of results from the preceding call to this method. `page_token` must be the value of `next_page_token` from the previous response. The values of other method parameters should be identical to those in the previous call. */
				def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
				/** Optional. The maximum number of results to return from this request. Non-positive values are ignored. The presence of `next_page_token` in the response indicates that more results might be available. If not specified, the default page_size is 100.<br>Format: int32 */
				def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
				def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudEssentialcontactsV1ListContactsResponse])
			}
			object list {
				def apply(foldersId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/folders/${foldersId}/contacts")).addQueryStringParameters("parent" -> parent.toString))
				given Conversion[list, Future[Schema.GoogleCloudEssentialcontactsV1ListContactsResponse]] = (fun: list) => fun.apply()
			}
			class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGoogleCloudEssentialcontactsV1Contact(body: Schema.GoogleCloudEssentialcontactsV1Contact) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleCloudEssentialcontactsV1Contact])
			}
			object create {
				def apply(foldersId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/folders/${foldersId}/contacts")).addQueryStringParameters("parent" -> parent.toString))
			}
			class compute(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudEssentialcontactsV1ComputeContactsResponse]) {
				/** Optional. The maximum number of results to return from this request. Non-positive values are ignored. The presence of `next_page_token` in the response indicates that more results might be available. If not specified, the default page_size is 100.<br>Format: int32 */
				def withPageSize(pageSize: Int) = new compute(req.addQueryStringParameters("pageSize" -> pageSize.toString))
				/** Optional. If present, retrieves the next batch of results from the preceding call to this method. `page_token` must be the value of `next_page_token` from the previous response. The values of other method parameters should be identical to those in the previous call. */
				def withPageToken(pageToken: String) = new compute(req.addQueryStringParameters("pageToken" -> pageToken.toString))
				def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudEssentialcontactsV1ComputeContactsResponse])
			}
			object compute {
				def apply(foldersId :PlayApi, notificationCategories: String, parent: String)(using auth: AuthToken, ec: ExecutionContext): compute = new compute(auth(ws.url(BASE_URL + s"v1/folders/${foldersId}/contacts:compute")).addQueryStringParameters("notificationCategories" -> notificationCategories.toString, "parent" -> parent.toString))
				given Conversion[compute, Future[Schema.GoogleCloudEssentialcontactsV1ComputeContactsResponse]] = (fun: compute) => fun.apply()
			}
		}
	}
	object organizations {
		object contacts {
			class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
				def apply() = req.execute("DELETE").map(_.json.as[Schema.GoogleProtobufEmpty])
			}
			object delete {
				def apply(organizationsId :PlayApi, contactsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/contacts/${contactsId}")).addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
			}
			class sendTestMessage(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGoogleCloudEssentialcontactsV1SendTestMessageRequest(body: Schema.GoogleCloudEssentialcontactsV1SendTestMessageRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleProtobufEmpty])
			}
			object sendTestMessage {
				def apply(organizationsId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): sendTestMessage = new sendTestMessage(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/contacts:sendTestMessage")).addQueryStringParameters("resource" -> resource.toString))
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudEssentialcontactsV1Contact]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudEssentialcontactsV1Contact])
			}
			object get {
				def apply(organizationsId :PlayApi, contactsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/contacts/${contactsId}")).addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.GoogleCloudEssentialcontactsV1Contact]] = (fun: get) => fun.apply()
			}
			class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				/** Optional. The update mask applied to the resource. For the `FieldMask` definition, see https://developers.google.com/protocol-buffers/docs/reference/google.protobuf#fieldmask<br>Format: google-fieldmask */
				def withUpdateMask(updateMask: String) = new patch(req.addQueryStringParameters("updateMask" -> updateMask.toString))
				def withGoogleCloudEssentialcontactsV1Contact(body: Schema.GoogleCloudEssentialcontactsV1Contact) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.GoogleCloudEssentialcontactsV1Contact])
			}
			object patch {
				def apply(organizationsId :PlayApi, contactsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/contacts/${contactsId}")).addQueryStringParameters("name" -> name.toString))
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudEssentialcontactsV1ListContactsResponse]) {
				/** Optional. The maximum number of results to return from this request. Non-positive values are ignored. The presence of `next_page_token` in the response indicates that more results might be available. If not specified, the default page_size is 100.<br>Format: int32 */
				def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
				/** Optional. If present, retrieves the next batch of results from the preceding call to this method. `page_token` must be the value of `next_page_token` from the previous response. The values of other method parameters should be identical to those in the previous call. */
				def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
				def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudEssentialcontactsV1ListContactsResponse])
			}
			object list {
				def apply(organizationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/contacts")).addQueryStringParameters("parent" -> parent.toString))
				given Conversion[list, Future[Schema.GoogleCloudEssentialcontactsV1ListContactsResponse]] = (fun: list) => fun.apply()
			}
			class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGoogleCloudEssentialcontactsV1Contact(body: Schema.GoogleCloudEssentialcontactsV1Contact) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleCloudEssentialcontactsV1Contact])
			}
			object create {
				def apply(organizationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/contacts")).addQueryStringParameters("parent" -> parent.toString))
			}
			class compute(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudEssentialcontactsV1ComputeContactsResponse]) {
				/** Optional. The maximum number of results to return from this request. Non-positive values are ignored. The presence of `next_page_token` in the response indicates that more results might be available. If not specified, the default page_size is 100.<br>Format: int32 */
				def withPageSize(pageSize: Int) = new compute(req.addQueryStringParameters("pageSize" -> pageSize.toString))
				/** Optional. If present, retrieves the next batch of results from the preceding call to this method. `page_token` must be the value of `next_page_token` from the previous response. The values of other method parameters should be identical to those in the previous call. */
				def withPageToken(pageToken: String) = new compute(req.addQueryStringParameters("pageToken" -> pageToken.toString))
				def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudEssentialcontactsV1ComputeContactsResponse])
			}
			object compute {
				def apply(organizationsId :PlayApi, notificationCategories: String, parent: String)(using auth: AuthToken, ec: ExecutionContext): compute = new compute(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/contacts:compute")).addQueryStringParameters("notificationCategories" -> notificationCategories.toString, "parent" -> parent.toString))
				given Conversion[compute, Future[Schema.GoogleCloudEssentialcontactsV1ComputeContactsResponse]] = (fun: compute) => fun.apply()
			}
		}
	}
}
