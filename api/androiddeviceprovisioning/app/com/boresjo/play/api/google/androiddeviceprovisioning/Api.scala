package com.boresjo.play.api.google.androiddeviceprovisioning

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	val scopes = Seq()

	private val BASE_URL = "https://androiddeviceprovisioning.googleapis.com/"

	object operations {
		/** Gets the latest state of a long-running operation. Clients can use this method to poll the operation result at intervals as recommended by the API service. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
			val scopes = Seq()
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Operation])
		}
		object get {
			def apply(operationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
			given Conversion[get, Future[Schema.Operation]] = (fun: get) => fun.apply()
		}
	}
	object partners {
		object vendors {
			/** Lists the vendors of the partner. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListVendorsResponse]) {
				val scopes = Seq()
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListVendorsResponse])
			}
			object list {
				def apply(partnersId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/partners/${partnersId}/vendors").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListVendorsResponse]] = (fun: list) => fun.apply()
			}
			object customers {
				/** Lists the customers of the vendor. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListVendorCustomersResponse]) {
					val scopes = Seq()
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListVendorCustomersResponse])
				}
				object list {
					def apply(partnersId :PlayApi, vendorsId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/partners/${partnersId}/vendors/${vendorsId}/customers").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListVendorCustomersResponse]] = (fun: list) => fun.apply()
				}
			}
		}
		object customers {
			/** Lists the customers that are enrolled to the reseller identified by the `partnerId` argument. This list includes customers that the reseller created and customers that enrolled themselves using the portal. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListCustomersResponse]) {
				val scopes = Seq()
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListCustomersResponse])
			}
			object list {
				def apply(partnersId :PlayApi, partnerId: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/partners/${partnersId}/customers").addQueryStringParameters("partnerId" -> partnerId.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListCustomersResponse]] = (fun: list) => fun.apply()
			}
			/** Creates a customer for zero-touch enrollment. After the method returns successfully, admin and owner roles can manage devices and EMM configs by calling API methods or using their zero-touch enrollment portal. The customer receives an email that welcomes them to zero-touch enrollment and explains how to sign into the portal. */
			class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq()
				/** Perform the request */
				def withCreateCustomerRequest(body: Schema.CreateCustomerRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Company])
			}
			object create {
				def apply(partnersId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/partners/${partnersId}/customers").addQueryStringParameters("parent" -> parent.toString))
			}
		}
		object devices {
			/** Unclaims a device from a customer and removes it from zero-touch enrollment. */
			class unclaim(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq()
				/** Perform the request */
				def withUnclaimDeviceRequest(body: Schema.UnclaimDeviceRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Empty])
			}
			object unclaim {
				def apply(partnersId :PlayApi, partnerId: String)(using signer: RequestSigner, ec: ExecutionContext): unclaim = new unclaim(ws.url(BASE_URL + s"v1/partners/${partnersId}/devices:unclaim").addQueryStringParameters("partnerId" -> partnerId.toString))
			}
			/** Claims a device for a customer and adds it to zero-touch enrollment. If the device is already claimed by another customer, the call returns an error. */
			class claim(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq()
				/** Perform the request */
				def withClaimDeviceRequest(body: Schema.ClaimDeviceRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.ClaimDeviceResponse])
			}
			object claim {
				def apply(partnersId :PlayApi, partnerId: String)(using signer: RequestSigner, ec: ExecutionContext): claim = new claim(ws.url(BASE_URL + s"v1/partners/${partnersId}/devices:claim").addQueryStringParameters("partnerId" -> partnerId.toString))
			}
			/** Claims a batch of devices for a customer asynchronously. Adds the devices to zero-touch enrollment. To learn more, read [Long‑running batch operations](/zero-touch/guides/how-it-works#operations). */
			class claimAsync(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq()
				/** Perform the request */
				def withClaimDevicesRequest(body: Schema.ClaimDevicesRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
			}
			object claimAsync {
				def apply(partnersId :PlayApi, partnerId: String)(using signer: RequestSigner, ec: ExecutionContext): claimAsync = new claimAsync(ws.url(BASE_URL + s"v1/partners/${partnersId}/devices:claimAsync").addQueryStringParameters("partnerId" -> partnerId.toString))
			}
			/** Finds devices by hardware identifiers, such as IMEI. */
			class findByIdentifier(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq()
				/** Perform the request */
				def withFindDevicesByDeviceIdentifierRequest(body: Schema.FindDevicesByDeviceIdentifierRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.FindDevicesByDeviceIdentifierResponse])
			}
			object findByIdentifier {
				def apply(partnersId :PlayApi, partnerId: String)(using signer: RequestSigner, ec: ExecutionContext): findByIdentifier = new findByIdentifier(ws.url(BASE_URL + s"v1/partners/${partnersId}/devices:findByIdentifier").addQueryStringParameters("partnerId" -> partnerId.toString))
			}
			/** Updates the reseller metadata attached to a batch of devices. This method updates devices asynchronously and returns an `Operation` that can be used to track progress. Read [Long‑running batch operations](/zero-touch/guides/how-it-works#operations). Android Devices only. */
			class updateMetadataAsync(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq()
				/** Perform the request */
				def withUpdateDeviceMetadataInBatchRequest(body: Schema.UpdateDeviceMetadataInBatchRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
			}
			object updateMetadataAsync {
				def apply(partnersId :PlayApi, partnerId: String)(using signer: RequestSigner, ec: ExecutionContext): updateMetadataAsync = new updateMetadataAsync(ws.url(BASE_URL + s"v1/partners/${partnersId}/devices:updateMetadataAsync").addQueryStringParameters("partnerId" -> partnerId.toString))
			}
			/** Finds devices claimed for customers. The results only contain devices registered to the reseller that's identified by the `partnerId` argument. The customer's devices purchased from other resellers don't appear in the results. */
			class findByOwner(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq()
				/** Perform the request */
				def withFindDevicesByOwnerRequest(body: Schema.FindDevicesByOwnerRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.FindDevicesByOwnerResponse])
			}
			object findByOwner {
				def apply(partnersId :PlayApi, partnerId: String)(using signer: RequestSigner, ec: ExecutionContext): findByOwner = new findByOwner(ws.url(BASE_URL + s"v1/partners/${partnersId}/devices:findByOwner").addQueryStringParameters("partnerId" -> partnerId.toString))
			}
			/** Updates reseller metadata associated with the device. Android devices only. */
			class metadata(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq()
				/** Perform the request */
				def withUpdateDeviceMetadataRequest(body: Schema.UpdateDeviceMetadataRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.DeviceMetadata])
			}
			object metadata {
				def apply(partnersId :PlayApi, devicesId :PlayApi, deviceId: String, metadataOwnerId: String)(using signer: RequestSigner, ec: ExecutionContext): metadata = new metadata(ws.url(BASE_URL + s"v1/partners/${partnersId}/devices/${devicesId}/metadata").addQueryStringParameters("deviceId" -> deviceId.toString, "metadataOwnerId" -> metadataOwnerId.toString))
			}
			/** Gets a device's SIM lock state. */
			class getSimLockState(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq()
				/** Perform the request */
				def withGetDeviceSimLockStateRequest(body: Schema.GetDeviceSimLockStateRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GetDeviceSimLockStateResponse])
			}
			object getSimLockState {
				def apply(partnersId :PlayApi, partnerId: String)(using signer: RequestSigner, ec: ExecutionContext): getSimLockState = new getSimLockState(ws.url(BASE_URL + s"v1/partners/${partnersId}/devices:getSimLockState").addQueryStringParameters("partnerId" -> partnerId.toString))
			}
			/** Unclaims a batch of devices for a customer asynchronously. Removes the devices from zero-touch enrollment. To learn more, read [Long‑running batch operations](/zero-touch/guides/how-it-works#operations). */
			class unclaimAsync(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq()
				/** Perform the request */
				def withUnclaimDevicesRequest(body: Schema.UnclaimDevicesRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
			}
			object unclaimAsync {
				def apply(partnersId :PlayApi, partnerId: String)(using signer: RequestSigner, ec: ExecutionContext): unclaimAsync = new unclaimAsync(ws.url(BASE_URL + s"v1/partners/${partnersId}/devices:unclaimAsync").addQueryStringParameters("partnerId" -> partnerId.toString))
			}
			/** Gets a device. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Device]) {
				val scopes = Seq()
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Device])
			}
			object get {
				def apply(partnersId :PlayApi, devicesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/partners/${partnersId}/devices/${devicesId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.Device]] = (fun: get) => fun.apply()
			}
		}
	}
	object customers {
		/** Lists the user's customer accounts. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.CustomerListCustomersResponse]) {
			val scopes = Seq()
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.CustomerListCustomersResponse])
		}
		object list {
			def apply(pageToken: String, pageSize: Int)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/customers").addQueryStringParameters("pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString))
			given Conversion[list, Future[Schema.CustomerListCustomersResponse]] = (fun: list) => fun.apply()
		}
		object configurations {
			/** Creates a new configuration. Once created, a customer can apply the configuration to devices. */
			class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq()
				/** Perform the request */
				def withConfiguration(body: Schema.Configuration) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Configuration])
			}
			object create {
				def apply(customersId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/customers/${customersId}/configurations").addQueryStringParameters("parent" -> parent.toString))
			}
			/** Deletes an unused configuration. The API call fails if the customer has devices with the configuration applied. */
			class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
				val scopes = Seq()
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
			}
			object delete {
				def apply(customersId :PlayApi, configurationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/customers/${customersId}/configurations/${configurationsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
			}
			/** Gets the details of a configuration. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Configuration]) {
				val scopes = Seq()
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Configuration])
			}
			object get {
				def apply(customersId :PlayApi, configurationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/customers/${customersId}/configurations/${configurationsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.Configuration]] = (fun: get) => fun.apply()
			}
			/** Updates a configuration's field values. */
			class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq()
				/** Perform the request */
				def withConfiguration(body: Schema.Configuration) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Configuration])
			}
			object patch {
				def apply(customersId :PlayApi, configurationsId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/customers/${customersId}/configurations/${configurationsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
			}
			/** Lists a customer's configurations. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.CustomerListConfigurationsResponse]) {
				val scopes = Seq()
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.CustomerListConfigurationsResponse])
			}
			object list {
				def apply(customersId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/customers/${customersId}/configurations").addQueryStringParameters("parent" -> parent.toString))
				given Conversion[list, Future[Schema.CustomerListConfigurationsResponse]] = (fun: list) => fun.apply()
			}
		}
		object dpcs {
			/** Lists the DPCs (device policy controllers) that support zero-touch enrollment. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.CustomerListDpcsResponse]) {
				val scopes = Seq()
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.CustomerListDpcsResponse])
			}
			object list {
				def apply(customersId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/customers/${customersId}/dpcs").addQueryStringParameters("parent" -> parent.toString))
				given Conversion[list, Future[Schema.CustomerListDpcsResponse]] = (fun: list) => fun.apply()
			}
		}
		object devices {
			/** Unclaims a device from a customer and removes it from zero-touch enrollment. After removing a device, a customer must contact their reseller to register the device into zero-touch enrollment again. */
			class unclaim(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq()
				/** Perform the request */
				def withCustomerUnclaimDeviceRequest(body: Schema.CustomerUnclaimDeviceRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Empty])
			}
			object unclaim {
				def apply(customersId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): unclaim = new unclaim(ws.url(BASE_URL + s"v1/customers/${customersId}/devices:unclaim").addQueryStringParameters("parent" -> parent.toString))
			}
			/** Removes a configuration from device. */
			class removeConfiguration(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq()
				/** Perform the request */
				def withCustomerRemoveConfigurationRequest(body: Schema.CustomerRemoveConfigurationRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Empty])
			}
			object removeConfiguration {
				def apply(customersId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): removeConfiguration = new removeConfiguration(ws.url(BASE_URL + s"v1/customers/${customersId}/devices:removeConfiguration").addQueryStringParameters("parent" -> parent.toString))
			}
			/** Gets the details of a device. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Device]) {
				val scopes = Seq()
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Device])
			}
			object get {
				def apply(customersId :PlayApi, devicesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/customers/${customersId}/devices/${devicesId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.Device]] = (fun: get) => fun.apply()
			}
			/** Lists a customer's devices. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.CustomerListDevicesResponse]) {
				val scopes = Seq()
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.CustomerListDevicesResponse])
			}
			object list {
				def apply(customersId :PlayApi, pageToken: String, parent: String, pageSize: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/customers/${customersId}/devices").addQueryStringParameters("pageToken" -> pageToken.toString, "parent" -> parent.toString, "pageSize" -> pageSize.toString))
				given Conversion[list, Future[Schema.CustomerListDevicesResponse]] = (fun: list) => fun.apply()
			}
			/** Applies a Configuration to the device to register the device for zero-touch enrollment. After applying a configuration to a device, the device automatically provisions itself on first boot, or next factory reset. */
			class applyConfiguration(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq()
				/** Perform the request */
				def withCustomerApplyConfigurationRequest(body: Schema.CustomerApplyConfigurationRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Empty])
			}
			object applyConfiguration {
				def apply(customersId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): applyConfiguration = new applyConfiguration(ws.url(BASE_URL + s"v1/customers/${customersId}/devices:applyConfiguration").addQueryStringParameters("parent" -> parent.toString))
			}
		}
	}
}
