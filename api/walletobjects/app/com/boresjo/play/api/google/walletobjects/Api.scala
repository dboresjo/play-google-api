package com.boresjo.play.api.google.walletobjects

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://walletobjects.googleapis.com/"

	object genericobject {
		class insert(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withGenericObject(body: Schema.GenericObject) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GenericObject])
		}
		object insert {
			def apply()(using auth: AuthToken, ec: ExecutionContext): insert = new insert(auth(ws.url(BASE_URL + s"walletobjects/v1/genericObject")).addQueryStringParameters())
		}
		class addmessage(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withAddMessageRequest(body: Schema.AddMessageRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GenericObjectAddMessageResponse])
		}
		object addmessage {
			def apply(resourceId: String)(using auth: AuthToken, ec: ExecutionContext): addmessage = new addmessage(auth(ws.url(BASE_URL + s"walletobjects/v1/genericObject/${resourceId}/addMessage")).addQueryStringParameters())
		}
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GenericObject]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.GenericObject])
		}
		object get {
			def apply(resourceId: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"walletobjects/v1/genericObject/${resourceId}")).addQueryStringParameters())
			given Conversion[get, Future[Schema.GenericObject]] = (fun: get) => fun.apply()
		}
		class update(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withGenericObject(body: Schema.GenericObject) = req.withBody(Json.toJson(body)).execute("PUT").map(_.json.as[Schema.GenericObject])
		}
		object update {
			def apply(resourceId: String)(using auth: AuthToken, ec: ExecutionContext): update = new update(auth(ws.url(BASE_URL + s"walletobjects/v1/genericObject/${resourceId}")).addQueryStringParameters())
		}
		class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withGenericObject(body: Schema.GenericObject) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.GenericObject])
		}
		object patch {
			def apply(resourceId: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"walletobjects/v1/genericObject/${resourceId}")).addQueryStringParameters())
		}
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GenericObjectListResponse]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.GenericObjectListResponse])
		}
		object list {
			def apply(maxResults: Int, token: String, classId: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"walletobjects/v1/genericObject")).addQueryStringParameters("maxResults" -> maxResults.toString, "token" -> token.toString, "classId" -> classId.toString))
			given Conversion[list, Future[Schema.GenericObjectListResponse]] = (fun: list) => fun.apply()
		}
	}
	object loyaltyobject {
		class addmessage(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withAddMessageRequest(body: Schema.AddMessageRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.LoyaltyObjectAddMessageResponse])
		}
		object addmessage {
			def apply(resourceId: String)(using auth: AuthToken, ec: ExecutionContext): addmessage = new addmessage(auth(ws.url(BASE_URL + s"walletobjects/v1/loyaltyObject/${resourceId}/addMessage")).addQueryStringParameters())
		}
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.LoyaltyObject]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.LoyaltyObject])
		}
		object get {
			def apply(resourceId: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"walletobjects/v1/loyaltyObject/${resourceId}")).addQueryStringParameters())
			given Conversion[get, Future[Schema.LoyaltyObject]] = (fun: get) => fun.apply()
		}
		class update(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withLoyaltyObject(body: Schema.LoyaltyObject) = req.withBody(Json.toJson(body)).execute("PUT").map(_.json.as[Schema.LoyaltyObject])
		}
		object update {
			def apply(resourceId: String)(using auth: AuthToken, ec: ExecutionContext): update = new update(auth(ws.url(BASE_URL + s"walletobjects/v1/loyaltyObject/${resourceId}")).addQueryStringParameters())
		}
		class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withLoyaltyObject(body: Schema.LoyaltyObject) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.LoyaltyObject])
		}
		object patch {
			def apply(resourceId: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"walletobjects/v1/loyaltyObject/${resourceId}")).addQueryStringParameters())
		}
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.LoyaltyObjectListResponse]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.LoyaltyObjectListResponse])
		}
		object list {
			def apply(maxResults: Int, classId: String, token: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"walletobjects/v1/loyaltyObject")).addQueryStringParameters("maxResults" -> maxResults.toString, "classId" -> classId.toString, "token" -> token.toString))
			given Conversion[list, Future[Schema.LoyaltyObjectListResponse]] = (fun: list) => fun.apply()
		}
		class modifylinkedofferobjects(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withModifyLinkedOfferObjectsRequest(body: Schema.ModifyLinkedOfferObjectsRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.LoyaltyObject])
		}
		object modifylinkedofferobjects {
			def apply(resourceId: String)(using auth: AuthToken, ec: ExecutionContext): modifylinkedofferobjects = new modifylinkedofferobjects(auth(ws.url(BASE_URL + s"walletobjects/v1/loyaltyObject/${resourceId}/modifyLinkedOfferObjects")).addQueryStringParameters())
		}
		class insert(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withLoyaltyObject(body: Schema.LoyaltyObject) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.LoyaltyObject])
		}
		object insert {
			def apply()(using auth: AuthToken, ec: ExecutionContext): insert = new insert(auth(ws.url(BASE_URL + s"walletobjects/v1/loyaltyObject")).addQueryStringParameters())
		}
	}
	object offerobject {
		class insert(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withOfferObject(body: Schema.OfferObject) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.OfferObject])
		}
		object insert {
			def apply()(using auth: AuthToken, ec: ExecutionContext): insert = new insert(auth(ws.url(BASE_URL + s"walletobjects/v1/offerObject")).addQueryStringParameters())
		}
		class addmessage(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withAddMessageRequest(body: Schema.AddMessageRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.OfferObjectAddMessageResponse])
		}
		object addmessage {
			def apply(resourceId: String)(using auth: AuthToken, ec: ExecutionContext): addmessage = new addmessage(auth(ws.url(BASE_URL + s"walletobjects/v1/offerObject/${resourceId}/addMessage")).addQueryStringParameters())
		}
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.OfferObject]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.OfferObject])
		}
		object get {
			def apply(resourceId: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"walletobjects/v1/offerObject/${resourceId}")).addQueryStringParameters())
			given Conversion[get, Future[Schema.OfferObject]] = (fun: get) => fun.apply()
		}
		class update(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withOfferObject(body: Schema.OfferObject) = req.withBody(Json.toJson(body)).execute("PUT").map(_.json.as[Schema.OfferObject])
		}
		object update {
			def apply(resourceId: String)(using auth: AuthToken, ec: ExecutionContext): update = new update(auth(ws.url(BASE_URL + s"walletobjects/v1/offerObject/${resourceId}")).addQueryStringParameters())
		}
		class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withOfferObject(body: Schema.OfferObject) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.OfferObject])
		}
		object patch {
			def apply(resourceId: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"walletobjects/v1/offerObject/${resourceId}")).addQueryStringParameters())
		}
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.OfferObjectListResponse]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.OfferObjectListResponse])
		}
		object list {
			def apply(maxResults: Int, classId: String, token: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"walletobjects/v1/offerObject")).addQueryStringParameters("maxResults" -> maxResults.toString, "classId" -> classId.toString, "token" -> token.toString))
			given Conversion[list, Future[Schema.OfferObjectListResponse]] = (fun: list) => fun.apply()
		}
	}
	object issuer {
		class insert(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withIssuer(body: Schema.Issuer) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Issuer])
		}
		object insert {
			def apply()(using auth: AuthToken, ec: ExecutionContext): insert = new insert(auth(ws.url(BASE_URL + s"walletobjects/v1/issuer")).addQueryStringParameters())
		}
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Issuer]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.Issuer])
		}
		object get {
			def apply(resourceId: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"walletobjects/v1/issuer/${resourceId}")).addQueryStringParameters())
			given Conversion[get, Future[Schema.Issuer]] = (fun: get) => fun.apply()
		}
		class update(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withIssuer(body: Schema.Issuer) = req.withBody(Json.toJson(body)).execute("PUT").map(_.json.as[Schema.Issuer])
		}
		object update {
			def apply(resourceId: String)(using auth: AuthToken, ec: ExecutionContext): update = new update(auth(ws.url(BASE_URL + s"walletobjects/v1/issuer/${resourceId}")).addQueryStringParameters())
		}
		class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withIssuer(body: Schema.Issuer) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.Issuer])
		}
		object patch {
			def apply(resourceId: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"walletobjects/v1/issuer/${resourceId}")).addQueryStringParameters())
		}
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.IssuerListResponse]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.IssuerListResponse])
		}
		object list {
			def apply()(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"walletobjects/v1/issuer")).addQueryStringParameters())
			given Conversion[list, Future[Schema.IssuerListResponse]] = (fun: list) => fun.apply()
		}
	}
	object flightclass {
		class insert(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withFlightClass(body: Schema.FlightClass) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.FlightClass])
		}
		object insert {
			def apply()(using auth: AuthToken, ec: ExecutionContext): insert = new insert(auth(ws.url(BASE_URL + s"walletobjects/v1/flightClass")).addQueryStringParameters())
		}
		class addmessage(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withAddMessageRequest(body: Schema.AddMessageRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.FlightClassAddMessageResponse])
		}
		object addmessage {
			def apply(resourceId: String)(using auth: AuthToken, ec: ExecutionContext): addmessage = new addmessage(auth(ws.url(BASE_URL + s"walletobjects/v1/flightClass/${resourceId}/addMessage")).addQueryStringParameters())
		}
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.FlightClass]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.FlightClass])
		}
		object get {
			def apply(resourceId: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"walletobjects/v1/flightClass/${resourceId}")).addQueryStringParameters())
			given Conversion[get, Future[Schema.FlightClass]] = (fun: get) => fun.apply()
		}
		class update(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withFlightClass(body: Schema.FlightClass) = req.withBody(Json.toJson(body)).execute("PUT").map(_.json.as[Schema.FlightClass])
		}
		object update {
			def apply(resourceId: String)(using auth: AuthToken, ec: ExecutionContext): update = new update(auth(ws.url(BASE_URL + s"walletobjects/v1/flightClass/${resourceId}")).addQueryStringParameters())
		}
		class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withFlightClass(body: Schema.FlightClass) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.FlightClass])
		}
		object patch {
			def apply(resourceId: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"walletobjects/v1/flightClass/${resourceId}")).addQueryStringParameters())
		}
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.FlightClassListResponse]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.FlightClassListResponse])
		}
		object list {
			def apply(maxResults: Int, issuerId: String, token: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"walletobjects/v1/flightClass")).addQueryStringParameters("maxResults" -> maxResults.toString, "issuerId" -> issuerId.toString, "token" -> token.toString))
			given Conversion[list, Future[Schema.FlightClassListResponse]] = (fun: list) => fun.apply()
		}
	}
	object eventticketobject {
		class addmessage(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withAddMessageRequest(body: Schema.AddMessageRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.EventTicketObjectAddMessageResponse])
		}
		object addmessage {
			def apply(resourceId: String)(using auth: AuthToken, ec: ExecutionContext): addmessage = new addmessage(auth(ws.url(BASE_URL + s"walletobjects/v1/eventTicketObject/${resourceId}/addMessage")).addQueryStringParameters())
		}
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.EventTicketObject]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.EventTicketObject])
		}
		object get {
			def apply(resourceId: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"walletobjects/v1/eventTicketObject/${resourceId}")).addQueryStringParameters())
			given Conversion[get, Future[Schema.EventTicketObject]] = (fun: get) => fun.apply()
		}
		class update(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withEventTicketObject(body: Schema.EventTicketObject) = req.withBody(Json.toJson(body)).execute("PUT").map(_.json.as[Schema.EventTicketObject])
		}
		object update {
			def apply(resourceId: String)(using auth: AuthToken, ec: ExecutionContext): update = new update(auth(ws.url(BASE_URL + s"walletobjects/v1/eventTicketObject/${resourceId}")).addQueryStringParameters())
		}
		class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withEventTicketObject(body: Schema.EventTicketObject) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.EventTicketObject])
		}
		object patch {
			def apply(resourceId: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"walletobjects/v1/eventTicketObject/${resourceId}")).addQueryStringParameters())
		}
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.EventTicketObjectListResponse]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.EventTicketObjectListResponse])
		}
		object list {
			def apply(token: String, classId: String, maxResults: Int)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"walletobjects/v1/eventTicketObject")).addQueryStringParameters("token" -> token.toString, "classId" -> classId.toString, "maxResults" -> maxResults.toString))
			given Conversion[list, Future[Schema.EventTicketObjectListResponse]] = (fun: list) => fun.apply()
		}
		class modifylinkedofferobjects(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withModifyLinkedOfferObjectsRequest(body: Schema.ModifyLinkedOfferObjectsRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.EventTicketObject])
		}
		object modifylinkedofferobjects {
			def apply(resourceId: String)(using auth: AuthToken, ec: ExecutionContext): modifylinkedofferobjects = new modifylinkedofferobjects(auth(ws.url(BASE_URL + s"walletobjects/v1/eventTicketObject/${resourceId}/modifyLinkedOfferObjects")).addQueryStringParameters())
		}
		class insert(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withEventTicketObject(body: Schema.EventTicketObject) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.EventTicketObject])
		}
		object insert {
			def apply()(using auth: AuthToken, ec: ExecutionContext): insert = new insert(auth(ws.url(BASE_URL + s"walletobjects/v1/eventTicketObject")).addQueryStringParameters())
		}
	}
	object loyaltyclass {
		class insert(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withLoyaltyClass(body: Schema.LoyaltyClass) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.LoyaltyClass])
		}
		object insert {
			def apply()(using auth: AuthToken, ec: ExecutionContext): insert = new insert(auth(ws.url(BASE_URL + s"walletobjects/v1/loyaltyClass")).addQueryStringParameters())
		}
		class addmessage(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withAddMessageRequest(body: Schema.AddMessageRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.LoyaltyClassAddMessageResponse])
		}
		object addmessage {
			def apply(resourceId: String)(using auth: AuthToken, ec: ExecutionContext): addmessage = new addmessage(auth(ws.url(BASE_URL + s"walletobjects/v1/loyaltyClass/${resourceId}/addMessage")).addQueryStringParameters())
		}
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.LoyaltyClass]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.LoyaltyClass])
		}
		object get {
			def apply(resourceId: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"walletobjects/v1/loyaltyClass/${resourceId}")).addQueryStringParameters())
			given Conversion[get, Future[Schema.LoyaltyClass]] = (fun: get) => fun.apply()
		}
		class update(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withLoyaltyClass(body: Schema.LoyaltyClass) = req.withBody(Json.toJson(body)).execute("PUT").map(_.json.as[Schema.LoyaltyClass])
		}
		object update {
			def apply(resourceId: String)(using auth: AuthToken, ec: ExecutionContext): update = new update(auth(ws.url(BASE_URL + s"walletobjects/v1/loyaltyClass/${resourceId}")).addQueryStringParameters())
		}
		class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withLoyaltyClass(body: Schema.LoyaltyClass) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.LoyaltyClass])
		}
		object patch {
			def apply(resourceId: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"walletobjects/v1/loyaltyClass/${resourceId}")).addQueryStringParameters())
		}
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.LoyaltyClassListResponse]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.LoyaltyClassListResponse])
		}
		object list {
			def apply(maxResults: Int, token: String, issuerId: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"walletobjects/v1/loyaltyClass")).addQueryStringParameters("maxResults" -> maxResults.toString, "token" -> token.toString, "issuerId" -> issuerId.toString))
			given Conversion[list, Future[Schema.LoyaltyClassListResponse]] = (fun: list) => fun.apply()
		}
	}
	object offerclass {
		class insert(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withOfferClass(body: Schema.OfferClass) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.OfferClass])
		}
		object insert {
			def apply()(using auth: AuthToken, ec: ExecutionContext): insert = new insert(auth(ws.url(BASE_URL + s"walletobjects/v1/offerClass")).addQueryStringParameters())
		}
		class addmessage(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withAddMessageRequest(body: Schema.AddMessageRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.OfferClassAddMessageResponse])
		}
		object addmessage {
			def apply(resourceId: String)(using auth: AuthToken, ec: ExecutionContext): addmessage = new addmessage(auth(ws.url(BASE_URL + s"walletobjects/v1/offerClass/${resourceId}/addMessage")).addQueryStringParameters())
		}
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.OfferClass]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.OfferClass])
		}
		object get {
			def apply(resourceId: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"walletobjects/v1/offerClass/${resourceId}")).addQueryStringParameters())
			given Conversion[get, Future[Schema.OfferClass]] = (fun: get) => fun.apply()
		}
		class update(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withOfferClass(body: Schema.OfferClass) = req.withBody(Json.toJson(body)).execute("PUT").map(_.json.as[Schema.OfferClass])
		}
		object update {
			def apply(resourceId: String)(using auth: AuthToken, ec: ExecutionContext): update = new update(auth(ws.url(BASE_URL + s"walletobjects/v1/offerClass/${resourceId}")).addQueryStringParameters())
		}
		class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withOfferClass(body: Schema.OfferClass) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.OfferClass])
		}
		object patch {
			def apply(resourceId: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"walletobjects/v1/offerClass/${resourceId}")).addQueryStringParameters())
		}
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.OfferClassListResponse]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.OfferClassListResponse])
		}
		object list {
			def apply(issuerId: String, maxResults: Int, token: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"walletobjects/v1/offerClass")).addQueryStringParameters("issuerId" -> issuerId.toString, "maxResults" -> maxResults.toString, "token" -> token.toString))
			given Conversion[list, Future[Schema.OfferClassListResponse]] = (fun: list) => fun.apply()
		}
	}
	object genericclass {
		class insert(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withGenericClass(body: Schema.GenericClass) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GenericClass])
		}
		object insert {
			def apply()(using auth: AuthToken, ec: ExecutionContext): insert = new insert(auth(ws.url(BASE_URL + s"walletobjects/v1/genericClass")).addQueryStringParameters())
		}
		class addmessage(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withAddMessageRequest(body: Schema.AddMessageRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GenericClassAddMessageResponse])
		}
		object addmessage {
			def apply(resourceId: String)(using auth: AuthToken, ec: ExecutionContext): addmessage = new addmessage(auth(ws.url(BASE_URL + s"walletobjects/v1/genericClass/${resourceId}/addMessage")).addQueryStringParameters())
		}
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GenericClass]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.GenericClass])
		}
		object get {
			def apply(resourceId: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"walletobjects/v1/genericClass/${resourceId}")).addQueryStringParameters())
			given Conversion[get, Future[Schema.GenericClass]] = (fun: get) => fun.apply()
		}
		class update(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withGenericClass(body: Schema.GenericClass) = req.withBody(Json.toJson(body)).execute("PUT").map(_.json.as[Schema.GenericClass])
		}
		object update {
			def apply(resourceId: String)(using auth: AuthToken, ec: ExecutionContext): update = new update(auth(ws.url(BASE_URL + s"walletobjects/v1/genericClass/${resourceId}")).addQueryStringParameters())
		}
		class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withGenericClass(body: Schema.GenericClass) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.GenericClass])
		}
		object patch {
			def apply(resourceId: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"walletobjects/v1/genericClass/${resourceId}")).addQueryStringParameters())
		}
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GenericClassListResponse]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.GenericClassListResponse])
		}
		object list {
			def apply(issuerId: String, token: String, maxResults: Int)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"walletobjects/v1/genericClass")).addQueryStringParameters("issuerId" -> issuerId.toString, "token" -> token.toString, "maxResults" -> maxResults.toString))
			given Conversion[list, Future[Schema.GenericClassListResponse]] = (fun: list) => fun.apply()
		}
	}
	object eventticketclass {
		class insert(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withEventTicketClass(body: Schema.EventTicketClass) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.EventTicketClass])
		}
		object insert {
			def apply()(using auth: AuthToken, ec: ExecutionContext): insert = new insert(auth(ws.url(BASE_URL + s"walletobjects/v1/eventTicketClass")).addQueryStringParameters())
		}
		class addmessage(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withAddMessageRequest(body: Schema.AddMessageRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.EventTicketClassAddMessageResponse])
		}
		object addmessage {
			def apply(resourceId: String)(using auth: AuthToken, ec: ExecutionContext): addmessage = new addmessage(auth(ws.url(BASE_URL + s"walletobjects/v1/eventTicketClass/${resourceId}/addMessage")).addQueryStringParameters())
		}
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.EventTicketClass]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.EventTicketClass])
		}
		object get {
			def apply(resourceId: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"walletobjects/v1/eventTicketClass/${resourceId}")).addQueryStringParameters())
			given Conversion[get, Future[Schema.EventTicketClass]] = (fun: get) => fun.apply()
		}
		class update(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withEventTicketClass(body: Schema.EventTicketClass) = req.withBody(Json.toJson(body)).execute("PUT").map(_.json.as[Schema.EventTicketClass])
		}
		object update {
			def apply(resourceId: String)(using auth: AuthToken, ec: ExecutionContext): update = new update(auth(ws.url(BASE_URL + s"walletobjects/v1/eventTicketClass/${resourceId}")).addQueryStringParameters())
		}
		class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withEventTicketClass(body: Schema.EventTicketClass) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.EventTicketClass])
		}
		object patch {
			def apply(resourceId: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"walletobjects/v1/eventTicketClass/${resourceId}")).addQueryStringParameters())
		}
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.EventTicketClassListResponse]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.EventTicketClassListResponse])
		}
		object list {
			def apply(token: String, maxResults: Int, issuerId: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"walletobjects/v1/eventTicketClass")).addQueryStringParameters("token" -> token.toString, "maxResults" -> maxResults.toString, "issuerId" -> issuerId.toString))
			given Conversion[list, Future[Schema.EventTicketClassListResponse]] = (fun: list) => fun.apply()
		}
	}
	object transitobject {
		class insert(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withTransitObject(body: Schema.TransitObject) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.TransitObject])
		}
		object insert {
			def apply()(using auth: AuthToken, ec: ExecutionContext): insert = new insert(auth(ws.url(BASE_URL + s"walletobjects/v1/transitObject")).addQueryStringParameters())
		}
		class addmessage(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withAddMessageRequest(body: Schema.AddMessageRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.TransitObjectAddMessageResponse])
		}
		object addmessage {
			def apply(resourceId: String)(using auth: AuthToken, ec: ExecutionContext): addmessage = new addmessage(auth(ws.url(BASE_URL + s"walletobjects/v1/transitObject/${resourceId}/addMessage")).addQueryStringParameters())
		}
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.TransitObject]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.TransitObject])
		}
		object get {
			def apply(resourceId: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"walletobjects/v1/transitObject/${resourceId}")).addQueryStringParameters())
			given Conversion[get, Future[Schema.TransitObject]] = (fun: get) => fun.apply()
		}
		class update(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withTransitObject(body: Schema.TransitObject) = req.withBody(Json.toJson(body)).execute("PUT").map(_.json.as[Schema.TransitObject])
		}
		object update {
			def apply(resourceId: String)(using auth: AuthToken, ec: ExecutionContext): update = new update(auth(ws.url(BASE_URL + s"walletobjects/v1/transitObject/${resourceId}")).addQueryStringParameters())
		}
		class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withTransitObject(body: Schema.TransitObject) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.TransitObject])
		}
		object patch {
			def apply(resourceId: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"walletobjects/v1/transitObject/${resourceId}")).addQueryStringParameters())
		}
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.TransitObjectListResponse]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.TransitObjectListResponse])
		}
		object list {
			def apply(classId: String, maxResults: Int, token: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"walletobjects/v1/transitObject")).addQueryStringParameters("classId" -> classId.toString, "maxResults" -> maxResults.toString, "token" -> token.toString))
			given Conversion[list, Future[Schema.TransitObjectListResponse]] = (fun: list) => fun.apply()
		}
	}
	object giftcardobject {
		class insert(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withGiftCardObject(body: Schema.GiftCardObject) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GiftCardObject])
		}
		object insert {
			def apply()(using auth: AuthToken, ec: ExecutionContext): insert = new insert(auth(ws.url(BASE_URL + s"walletobjects/v1/giftCardObject")).addQueryStringParameters())
		}
		class addmessage(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withAddMessageRequest(body: Schema.AddMessageRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GiftCardObjectAddMessageResponse])
		}
		object addmessage {
			def apply(resourceId: String)(using auth: AuthToken, ec: ExecutionContext): addmessage = new addmessage(auth(ws.url(BASE_URL + s"walletobjects/v1/giftCardObject/${resourceId}/addMessage")).addQueryStringParameters())
		}
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GiftCardObject]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.GiftCardObject])
		}
		object get {
			def apply(resourceId: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"walletobjects/v1/giftCardObject/${resourceId}")).addQueryStringParameters())
			given Conversion[get, Future[Schema.GiftCardObject]] = (fun: get) => fun.apply()
		}
		class update(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withGiftCardObject(body: Schema.GiftCardObject) = req.withBody(Json.toJson(body)).execute("PUT").map(_.json.as[Schema.GiftCardObject])
		}
		object update {
			def apply(resourceId: String)(using auth: AuthToken, ec: ExecutionContext): update = new update(auth(ws.url(BASE_URL + s"walletobjects/v1/giftCardObject/${resourceId}")).addQueryStringParameters())
		}
		class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withGiftCardObject(body: Schema.GiftCardObject) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.GiftCardObject])
		}
		object patch {
			def apply(resourceId: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"walletobjects/v1/giftCardObject/${resourceId}")).addQueryStringParameters())
		}
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GiftCardObjectListResponse]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.GiftCardObjectListResponse])
		}
		object list {
			def apply(classId: String, maxResults: Int, token: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"walletobjects/v1/giftCardObject")).addQueryStringParameters("classId" -> classId.toString, "maxResults" -> maxResults.toString, "token" -> token.toString))
			given Conversion[list, Future[Schema.GiftCardObjectListResponse]] = (fun: list) => fun.apply()
		}
	}
	object flightobject {
		class insert(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withFlightObject(body: Schema.FlightObject) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.FlightObject])
		}
		object insert {
			def apply()(using auth: AuthToken, ec: ExecutionContext): insert = new insert(auth(ws.url(BASE_URL + s"walletobjects/v1/flightObject")).addQueryStringParameters())
		}
		class addmessage(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withAddMessageRequest(body: Schema.AddMessageRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.FlightObjectAddMessageResponse])
		}
		object addmessage {
			def apply(resourceId: String)(using auth: AuthToken, ec: ExecutionContext): addmessage = new addmessage(auth(ws.url(BASE_URL + s"walletobjects/v1/flightObject/${resourceId}/addMessage")).addQueryStringParameters())
		}
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.FlightObject]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.FlightObject])
		}
		object get {
			def apply(resourceId: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"walletobjects/v1/flightObject/${resourceId}")).addQueryStringParameters())
			given Conversion[get, Future[Schema.FlightObject]] = (fun: get) => fun.apply()
		}
		class update(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withFlightObject(body: Schema.FlightObject) = req.withBody(Json.toJson(body)).execute("PUT").map(_.json.as[Schema.FlightObject])
		}
		object update {
			def apply(resourceId: String)(using auth: AuthToken, ec: ExecutionContext): update = new update(auth(ws.url(BASE_URL + s"walletobjects/v1/flightObject/${resourceId}")).addQueryStringParameters())
		}
		class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withFlightObject(body: Schema.FlightObject) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.FlightObject])
		}
		object patch {
			def apply(resourceId: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"walletobjects/v1/flightObject/${resourceId}")).addQueryStringParameters())
		}
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.FlightObjectListResponse]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.FlightObjectListResponse])
		}
		object list {
			def apply(classId: String, maxResults: Int, token: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"walletobjects/v1/flightObject")).addQueryStringParameters("classId" -> classId.toString, "maxResults" -> maxResults.toString, "token" -> token.toString))
			given Conversion[list, Future[Schema.FlightObjectListResponse]] = (fun: list) => fun.apply()
		}
	}
	object media {
		class download(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Media]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.Media])
		}
		object download {
			def apply(resourceId: String)(using auth: AuthToken, ec: ExecutionContext): download = new download(auth(ws.url(BASE_URL + s"walletobjects/v1/transitObject/${resourceId}/downloadRotatingBarcodeValues")).addQueryStringParameters())
			given Conversion[download, Future[Schema.Media]] = (fun: download) => fun.apply()
		}
		class upload(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withTransitObjectUploadRotatingBarcodeValuesRequest(body: Schema.TransitObjectUploadRotatingBarcodeValuesRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.TransitObjectUploadRotatingBarcodeValuesResponse])
		}
		object upload {
			def apply(resourceId: String)(using auth: AuthToken, ec: ExecutionContext): upload = new upload(auth(ws.url(BASE_URL + s"walletobjects/v1/transitObject/${resourceId}/uploadRotatingBarcodeValues")).addQueryStringParameters())
		}
	}
	object giftcardclass {
		class insert(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withGiftCardClass(body: Schema.GiftCardClass) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GiftCardClass])
		}
		object insert {
			def apply()(using auth: AuthToken, ec: ExecutionContext): insert = new insert(auth(ws.url(BASE_URL + s"walletobjects/v1/giftCardClass")).addQueryStringParameters())
		}
		class addmessage(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withAddMessageRequest(body: Schema.AddMessageRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GiftCardClassAddMessageResponse])
		}
		object addmessage {
			def apply(resourceId: String)(using auth: AuthToken, ec: ExecutionContext): addmessage = new addmessage(auth(ws.url(BASE_URL + s"walletobjects/v1/giftCardClass/${resourceId}/addMessage")).addQueryStringParameters())
		}
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GiftCardClass]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.GiftCardClass])
		}
		object get {
			def apply(resourceId: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"walletobjects/v1/giftCardClass/${resourceId}")).addQueryStringParameters())
			given Conversion[get, Future[Schema.GiftCardClass]] = (fun: get) => fun.apply()
		}
		class update(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withGiftCardClass(body: Schema.GiftCardClass) = req.withBody(Json.toJson(body)).execute("PUT").map(_.json.as[Schema.GiftCardClass])
		}
		object update {
			def apply(resourceId: String)(using auth: AuthToken, ec: ExecutionContext): update = new update(auth(ws.url(BASE_URL + s"walletobjects/v1/giftCardClass/${resourceId}")).addQueryStringParameters())
		}
		class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withGiftCardClass(body: Schema.GiftCardClass) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.GiftCardClass])
		}
		object patch {
			def apply(resourceId: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"walletobjects/v1/giftCardClass/${resourceId}")).addQueryStringParameters())
		}
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GiftCardClassListResponse]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.GiftCardClassListResponse])
		}
		object list {
			def apply(issuerId: String, maxResults: Int, token: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"walletobjects/v1/giftCardClass")).addQueryStringParameters("issuerId" -> issuerId.toString, "maxResults" -> maxResults.toString, "token" -> token.toString))
			given Conversion[list, Future[Schema.GiftCardClassListResponse]] = (fun: list) => fun.apply()
		}
	}
	object smarttap {
		class insert(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withSmartTap(body: Schema.SmartTap) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.SmartTap])
		}
		object insert {
			def apply()(using auth: AuthToken, ec: ExecutionContext): insert = new insert(auth(ws.url(BASE_URL + s"walletobjects/v1/smartTap")).addQueryStringParameters())
		}
	}
	object transitclass {
		class insert(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withTransitClass(body: Schema.TransitClass) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.TransitClass])
		}
		object insert {
			def apply()(using auth: AuthToken, ec: ExecutionContext): insert = new insert(auth(ws.url(BASE_URL + s"walletobjects/v1/transitClass")).addQueryStringParameters())
		}
		class addmessage(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withAddMessageRequest(body: Schema.AddMessageRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.TransitClassAddMessageResponse])
		}
		object addmessage {
			def apply(resourceId: String)(using auth: AuthToken, ec: ExecutionContext): addmessage = new addmessage(auth(ws.url(BASE_URL + s"walletobjects/v1/transitClass/${resourceId}/addMessage")).addQueryStringParameters())
		}
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.TransitClass]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.TransitClass])
		}
		object get {
			def apply(resourceId: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"walletobjects/v1/transitClass/${resourceId}")).addQueryStringParameters())
			given Conversion[get, Future[Schema.TransitClass]] = (fun: get) => fun.apply()
		}
		class update(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withTransitClass(body: Schema.TransitClass) = req.withBody(Json.toJson(body)).execute("PUT").map(_.json.as[Schema.TransitClass])
		}
		object update {
			def apply(resourceId: String)(using auth: AuthToken, ec: ExecutionContext): update = new update(auth(ws.url(BASE_URL + s"walletobjects/v1/transitClass/${resourceId}")).addQueryStringParameters())
		}
		class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withTransitClass(body: Schema.TransitClass) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.TransitClass])
		}
		object patch {
			def apply(resourceId: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"walletobjects/v1/transitClass/${resourceId}")).addQueryStringParameters())
		}
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.TransitClassListResponse]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.TransitClassListResponse])
		}
		object list {
			def apply(maxResults: Int, issuerId: String, token: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"walletobjects/v1/transitClass")).addQueryStringParameters("maxResults" -> maxResults.toString, "issuerId" -> issuerId.toString, "token" -> token.toString))
			given Conversion[list, Future[Schema.TransitClassListResponse]] = (fun: list) => fun.apply()
		}
	}
	object permissions {
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Permissions]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.Permissions])
		}
		object get {
			def apply(resourceId: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"walletobjects/v1/permissions/${resourceId}")).addQueryStringParameters())
			given Conversion[get, Future[Schema.Permissions]] = (fun: get) => fun.apply()
		}
		class update(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withPermissions(body: Schema.Permissions) = req.withBody(Json.toJson(body)).execute("PUT").map(_.json.as[Schema.Permissions])
		}
		object update {
			def apply(resourceId: String)(using auth: AuthToken, ec: ExecutionContext): update = new update(auth(ws.url(BASE_URL + s"walletobjects/v1/permissions/${resourceId}")).addQueryStringParameters())
		}
	}
	object jwt {
		class insert(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withJwtResource(body: Schema.JwtResource) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.JwtInsertResponse])
		}
		object insert {
			def apply()(using auth: AuthToken, ec: ExecutionContext): insert = new insert(auth(ws.url(BASE_URL + s"walletobjects/v1/jwt")).addQueryStringParameters())
		}
	}
}
