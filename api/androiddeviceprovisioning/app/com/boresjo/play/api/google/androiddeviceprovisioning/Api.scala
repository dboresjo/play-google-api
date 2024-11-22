package com.boresjo.play.api.google.androiddeviceprovisioning

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://androiddeviceprovisioning.googleapis.com/"

	object operations {
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Operation])
		}
		object get {
			def apply(operationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
			given Conversion[get, Future[Schema.Operation]] = (fun: get) => fun.apply()
		}
	}
	object partners {
		object vendors {
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListVendorsResponse]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListVendorsResponse])
			}
			object list {
				def apply(partnersId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/partners/${partnersId}/vendors").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListVendorsResponse]] = (fun: list) => fun.apply()
			}
			object customers {
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListVendorCustomersResponse]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListVendorCustomersResponse])
				}
				object list {
					def apply(partnersId :PlayApi, vendorsId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/partners/${partnersId}/vendors/${vendorsId}/customers").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListVendorCustomersResponse]] = (fun: list) => fun.apply()
				}
			}
		}
		object customers {
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListCustomersResponse]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListCustomersResponse])
			}
			object list {
				def apply(partnersId :PlayApi, partnerId: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/partners/${partnersId}/customers").addQueryStringParameters("partnerId" -> partnerId.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListCustomersResponse]] = (fun: list) => fun.apply()
			}
			class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withCreateCustomerRequest(body: Schema.CreateCustomerRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Company])
			}
			object create {
				def apply(partnersId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/partners/${partnersId}/customers").addQueryStringParameters("parent" -> parent.toString))
			}
		}
		object devices {
			class unclaim(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withUnclaimDeviceRequest(body: Schema.UnclaimDeviceRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Empty])
			}
			object unclaim {
				def apply(partnersId :PlayApi, partnerId: String)(using auth: AuthToken, ec: ExecutionContext): unclaim = new unclaim(ws.url(BASE_URL + s"v1/partners/${partnersId}/devices:unclaim").addQueryStringParameters("partnerId" -> partnerId.toString))
			}
			class claim(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withClaimDeviceRequest(body: Schema.ClaimDeviceRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.ClaimDeviceResponse])
			}
			object claim {
				def apply(partnersId :PlayApi, partnerId: String)(using auth: AuthToken, ec: ExecutionContext): claim = new claim(ws.url(BASE_URL + s"v1/partners/${partnersId}/devices:claim").addQueryStringParameters("partnerId" -> partnerId.toString))
			}
			class claimAsync(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withClaimDevicesRequest(body: Schema.ClaimDevicesRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
			}
			object claimAsync {
				def apply(partnersId :PlayApi, partnerId: String)(using auth: AuthToken, ec: ExecutionContext): claimAsync = new claimAsync(ws.url(BASE_URL + s"v1/partners/${partnersId}/devices:claimAsync").addQueryStringParameters("partnerId" -> partnerId.toString))
			}
			class findByIdentifier(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withFindDevicesByDeviceIdentifierRequest(body: Schema.FindDevicesByDeviceIdentifierRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.FindDevicesByDeviceIdentifierResponse])
			}
			object findByIdentifier {
				def apply(partnersId :PlayApi, partnerId: String)(using auth: AuthToken, ec: ExecutionContext): findByIdentifier = new findByIdentifier(ws.url(BASE_URL + s"v1/partners/${partnersId}/devices:findByIdentifier").addQueryStringParameters("partnerId" -> partnerId.toString))
			}
			class updateMetadataAsync(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withUpdateDeviceMetadataInBatchRequest(body: Schema.UpdateDeviceMetadataInBatchRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
			}
			object updateMetadataAsync {
				def apply(partnersId :PlayApi, partnerId: String)(using auth: AuthToken, ec: ExecutionContext): updateMetadataAsync = new updateMetadataAsync(ws.url(BASE_URL + s"v1/partners/${partnersId}/devices:updateMetadataAsync").addQueryStringParameters("partnerId" -> partnerId.toString))
			}
			class findByOwner(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withFindDevicesByOwnerRequest(body: Schema.FindDevicesByOwnerRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.FindDevicesByOwnerResponse])
			}
			object findByOwner {
				def apply(partnersId :PlayApi, partnerId: String)(using auth: AuthToken, ec: ExecutionContext): findByOwner = new findByOwner(ws.url(BASE_URL + s"v1/partners/${partnersId}/devices:findByOwner").addQueryStringParameters("partnerId" -> partnerId.toString))
			}
			class metadata(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withUpdateDeviceMetadataRequest(body: Schema.UpdateDeviceMetadataRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.DeviceMetadata])
			}
			object metadata {
				def apply(partnersId :PlayApi, devicesId :PlayApi, deviceId: String, metadataOwnerId: String)(using auth: AuthToken, ec: ExecutionContext): metadata = new metadata(ws.url(BASE_URL + s"v1/partners/${partnersId}/devices/${devicesId}/metadata").addQueryStringParameters("deviceId" -> deviceId.toString, "metadataOwnerId" -> metadataOwnerId.toString))
			}
			class getSimLockState(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGetDeviceSimLockStateRequest(body: Schema.GetDeviceSimLockStateRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GetDeviceSimLockStateResponse])
			}
			object getSimLockState {
				def apply(partnersId :PlayApi, partnerId: String)(using auth: AuthToken, ec: ExecutionContext): getSimLockState = new getSimLockState(ws.url(BASE_URL + s"v1/partners/${partnersId}/devices:getSimLockState").addQueryStringParameters("partnerId" -> partnerId.toString))
			}
			class unclaimAsync(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withUnclaimDevicesRequest(body: Schema.UnclaimDevicesRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
			}
			object unclaimAsync {
				def apply(partnersId :PlayApi, partnerId: String)(using auth: AuthToken, ec: ExecutionContext): unclaimAsync = new unclaimAsync(ws.url(BASE_URL + s"v1/partners/${partnersId}/devices:unclaimAsync").addQueryStringParameters("partnerId" -> partnerId.toString))
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Device]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Device])
			}
			object get {
				def apply(partnersId :PlayApi, devicesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/partners/${partnersId}/devices/${devicesId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.Device]] = (fun: get) => fun.apply()
			}
		}
	}
	object customers {
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.CustomerListCustomersResponse]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.CustomerListCustomersResponse])
		}
		object list {
			def apply(pageToken: String, pageSize: Int)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/customers").addQueryStringParameters("pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString))
			given Conversion[list, Future[Schema.CustomerListCustomersResponse]] = (fun: list) => fun.apply()
		}
		object configurations {
			class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withConfiguration(body: Schema.Configuration) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Configuration])
			}
			object create {
				def apply(customersId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/customers/${customersId}/configurations").addQueryStringParameters("parent" -> parent.toString))
			}
			class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
				def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
			}
			object delete {
				def apply(customersId :PlayApi, configurationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/customers/${customersId}/configurations/${configurationsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Configuration]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Configuration])
			}
			object get {
				def apply(customersId :PlayApi, configurationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/customers/${customersId}/configurations/${configurationsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.Configuration]] = (fun: get) => fun.apply()
			}
			class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withConfiguration(body: Schema.Configuration) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Configuration])
			}
			object patch {
				def apply(customersId :PlayApi, configurationsId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/customers/${customersId}/configurations/${configurationsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.CustomerListConfigurationsResponse]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.CustomerListConfigurationsResponse])
			}
			object list {
				def apply(customersId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/customers/${customersId}/configurations").addQueryStringParameters("parent" -> parent.toString))
				given Conversion[list, Future[Schema.CustomerListConfigurationsResponse]] = (fun: list) => fun.apply()
			}
		}
		object dpcs {
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.CustomerListDpcsResponse]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.CustomerListDpcsResponse])
			}
			object list {
				def apply(customersId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/customers/${customersId}/dpcs").addQueryStringParameters("parent" -> parent.toString))
				given Conversion[list, Future[Schema.CustomerListDpcsResponse]] = (fun: list) => fun.apply()
			}
		}
		object devices {
			class unclaim(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withCustomerUnclaimDeviceRequest(body: Schema.CustomerUnclaimDeviceRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Empty])
			}
			object unclaim {
				def apply(customersId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): unclaim = new unclaim(ws.url(BASE_URL + s"v1/customers/${customersId}/devices:unclaim").addQueryStringParameters("parent" -> parent.toString))
			}
			class removeConfiguration(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withCustomerRemoveConfigurationRequest(body: Schema.CustomerRemoveConfigurationRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Empty])
			}
			object removeConfiguration {
				def apply(customersId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): removeConfiguration = new removeConfiguration(ws.url(BASE_URL + s"v1/customers/${customersId}/devices:removeConfiguration").addQueryStringParameters("parent" -> parent.toString))
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Device]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Device])
			}
			object get {
				def apply(customersId :PlayApi, devicesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/customers/${customersId}/devices/${devicesId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.Device]] = (fun: get) => fun.apply()
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.CustomerListDevicesResponse]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.CustomerListDevicesResponse])
			}
			object list {
				def apply(customersId :PlayApi, pageToken: String, parent: String, pageSize: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/customers/${customersId}/devices").addQueryStringParameters("pageToken" -> pageToken.toString, "parent" -> parent.toString, "pageSize" -> pageSize.toString))
				given Conversion[list, Future[Schema.CustomerListDevicesResponse]] = (fun: list) => fun.apply()
			}
			class applyConfiguration(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withCustomerApplyConfigurationRequest(body: Schema.CustomerApplyConfigurationRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Empty])
			}
			object applyConfiguration {
				def apply(customersId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): applyConfiguration = new applyConfiguration(ws.url(BASE_URL + s"v1/customers/${customersId}/devices:applyConfiguration").addQueryStringParameters("parent" -> parent.toString))
			}
		}
	}
}
