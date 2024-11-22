package com.boresjo.play.api.google.walletobjects

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
		"""https://www.googleapis.com/auth/wallet_object.issuer""" /* Private Service: https://www.googleapis.com/auth/wallet_object.issuer */
	)

	private val BASE_URL = "https://walletobjects.googleapis.com/"

	object genericobject {
		/** Inserts a generic object with the given ID and properties. */
		class insert(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/wallet_object.issuer""")
			/** Perform the request */
			def withGenericObject(body: Schema.GenericObject) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GenericObject])
		}
		object insert {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): insert = new insert(ws.url(BASE_URL + s"walletobjects/v1/genericObject").addQueryStringParameters())
		}
		/** Adds a message to the generic object referenced by the given object ID. */
		class addmessage(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/wallet_object.issuer""")
			/** Perform the request */
			def withAddMessageRequest(body: Schema.AddMessageRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GenericObjectAddMessageResponse])
		}
		object addmessage {
			def apply(resourceId: String)(using signer: RequestSigner, ec: ExecutionContext): addmessage = new addmessage(ws.url(BASE_URL + s"walletobjects/v1/genericObject/${resourceId}/addMessage").addQueryStringParameters())
		}
		/** Returns the generic object with the given object ID. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GenericObject]) {
			val scopes = Seq("""https://www.googleapis.com/auth/wallet_object.issuer""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GenericObject])
		}
		object get {
			def apply(resourceId: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"walletobjects/v1/genericObject/${resourceId}").addQueryStringParameters())
			given Conversion[get, Future[Schema.GenericObject]] = (fun: get) => fun.apply()
		}
		/** Updates the generic object referenced by the given object ID. */
		class update(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/wallet_object.issuer""")
			/** Perform the request */
			def withGenericObject(body: Schema.GenericObject) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.GenericObject])
		}
		object update {
			def apply(resourceId: String)(using signer: RequestSigner, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"walletobjects/v1/genericObject/${resourceId}").addQueryStringParameters())
		}
		/** Updates the generic object referenced by the given object ID. This method supports patch semantics. */
		class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/wallet_object.issuer""")
			/** Perform the request */
			def withGenericObject(body: Schema.GenericObject) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GenericObject])
		}
		object patch {
			def apply(resourceId: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"walletobjects/v1/genericObject/${resourceId}").addQueryStringParameters())
		}
		/** Returns a list of all generic objects for a given issuer ID. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GenericObjectListResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/wallet_object.issuer""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GenericObjectListResponse])
		}
		object list {
			def apply(maxResults: Int, token: String, classId: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"walletobjects/v1/genericObject").addQueryStringParameters("maxResults" -> maxResults.toString, "token" -> token.toString, "classId" -> classId.toString))
			given Conversion[list, Future[Schema.GenericObjectListResponse]] = (fun: list) => fun.apply()
		}
	}
	object loyaltyobject {
		/** Adds a message to the loyalty object referenced by the given object ID. */
		class addmessage(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/wallet_object.issuer""")
			/** Perform the request */
			def withAddMessageRequest(body: Schema.AddMessageRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.LoyaltyObjectAddMessageResponse])
		}
		object addmessage {
			def apply(resourceId: String)(using signer: RequestSigner, ec: ExecutionContext): addmessage = new addmessage(ws.url(BASE_URL + s"walletobjects/v1/loyaltyObject/${resourceId}/addMessage").addQueryStringParameters())
		}
		/** Returns the loyalty object with the given object ID. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.LoyaltyObject]) {
			val scopes = Seq("""https://www.googleapis.com/auth/wallet_object.issuer""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.LoyaltyObject])
		}
		object get {
			def apply(resourceId: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"walletobjects/v1/loyaltyObject/${resourceId}").addQueryStringParameters())
			given Conversion[get, Future[Schema.LoyaltyObject]] = (fun: get) => fun.apply()
		}
		/** Updates the loyalty object referenced by the given object ID. */
		class update(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/wallet_object.issuer""")
			/** Perform the request */
			def withLoyaltyObject(body: Schema.LoyaltyObject) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.LoyaltyObject])
		}
		object update {
			def apply(resourceId: String)(using signer: RequestSigner, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"walletobjects/v1/loyaltyObject/${resourceId}").addQueryStringParameters())
		}
		/** Updates the loyalty object referenced by the given object ID. This method supports patch semantics. */
		class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/wallet_object.issuer""")
			/** Perform the request */
			def withLoyaltyObject(body: Schema.LoyaltyObject) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.LoyaltyObject])
		}
		object patch {
			def apply(resourceId: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"walletobjects/v1/loyaltyObject/${resourceId}").addQueryStringParameters())
		}
		/** Returns a list of all loyalty objects for a given issuer ID. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.LoyaltyObjectListResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/wallet_object.issuer""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.LoyaltyObjectListResponse])
		}
		object list {
			def apply(maxResults: Int, classId: String, token: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"walletobjects/v1/loyaltyObject").addQueryStringParameters("maxResults" -> maxResults.toString, "classId" -> classId.toString, "token" -> token.toString))
			given Conversion[list, Future[Schema.LoyaltyObjectListResponse]] = (fun: list) => fun.apply()
		}
		/** Modifies linked offer objects for the loyalty object with the given ID. */
		class modifylinkedofferobjects(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/wallet_object.issuer""")
			/** Perform the request */
			def withModifyLinkedOfferObjectsRequest(body: Schema.ModifyLinkedOfferObjectsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.LoyaltyObject])
		}
		object modifylinkedofferobjects {
			def apply(resourceId: String)(using signer: RequestSigner, ec: ExecutionContext): modifylinkedofferobjects = new modifylinkedofferobjects(ws.url(BASE_URL + s"walletobjects/v1/loyaltyObject/${resourceId}/modifyLinkedOfferObjects").addQueryStringParameters())
		}
		/** Inserts an loyalty object with the given ID and properties. */
		class insert(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/wallet_object.issuer""")
			/** Perform the request */
			def withLoyaltyObject(body: Schema.LoyaltyObject) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.LoyaltyObject])
		}
		object insert {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): insert = new insert(ws.url(BASE_URL + s"walletobjects/v1/loyaltyObject").addQueryStringParameters())
		}
	}
	object offerobject {
		/** Inserts an offer object with the given ID and properties. */
		class insert(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/wallet_object.issuer""")
			/** Perform the request */
			def withOfferObject(body: Schema.OfferObject) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.OfferObject])
		}
		object insert {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): insert = new insert(ws.url(BASE_URL + s"walletobjects/v1/offerObject").addQueryStringParameters())
		}
		/** Adds a message to the offer object referenced by the given object ID. */
		class addmessage(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/wallet_object.issuer""")
			/** Perform the request */
			def withAddMessageRequest(body: Schema.AddMessageRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.OfferObjectAddMessageResponse])
		}
		object addmessage {
			def apply(resourceId: String)(using signer: RequestSigner, ec: ExecutionContext): addmessage = new addmessage(ws.url(BASE_URL + s"walletobjects/v1/offerObject/${resourceId}/addMessage").addQueryStringParameters())
		}
		/** Returns the offer object with the given object ID. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.OfferObject]) {
			val scopes = Seq("""https://www.googleapis.com/auth/wallet_object.issuer""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.OfferObject])
		}
		object get {
			def apply(resourceId: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"walletobjects/v1/offerObject/${resourceId}").addQueryStringParameters())
			given Conversion[get, Future[Schema.OfferObject]] = (fun: get) => fun.apply()
		}
		/** Updates the offer object referenced by the given object ID. */
		class update(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/wallet_object.issuer""")
			/** Perform the request */
			def withOfferObject(body: Schema.OfferObject) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.OfferObject])
		}
		object update {
			def apply(resourceId: String)(using signer: RequestSigner, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"walletobjects/v1/offerObject/${resourceId}").addQueryStringParameters())
		}
		/** Updates the offer object referenced by the given object ID. This method supports patch semantics. */
		class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/wallet_object.issuer""")
			/** Perform the request */
			def withOfferObject(body: Schema.OfferObject) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.OfferObject])
		}
		object patch {
			def apply(resourceId: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"walletobjects/v1/offerObject/${resourceId}").addQueryStringParameters())
		}
		/** Returns a list of all offer objects for a given issuer ID. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.OfferObjectListResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/wallet_object.issuer""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.OfferObjectListResponse])
		}
		object list {
			def apply(maxResults: Int, classId: String, token: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"walletobjects/v1/offerObject").addQueryStringParameters("maxResults" -> maxResults.toString, "classId" -> classId.toString, "token" -> token.toString))
			given Conversion[list, Future[Schema.OfferObjectListResponse]] = (fun: list) => fun.apply()
		}
	}
	object issuer {
		/** Inserts an issuer with the given ID and properties. */
		class insert(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/wallet_object.issuer""")
			/** Perform the request */
			def withIssuer(body: Schema.Issuer) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Issuer])
		}
		object insert {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): insert = new insert(ws.url(BASE_URL + s"walletobjects/v1/issuer").addQueryStringParameters())
		}
		/** Returns the issuer with the given issuer ID. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Issuer]) {
			val scopes = Seq("""https://www.googleapis.com/auth/wallet_object.issuer""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Issuer])
		}
		object get {
			def apply(resourceId: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"walletobjects/v1/issuer/${resourceId}").addQueryStringParameters())
			given Conversion[get, Future[Schema.Issuer]] = (fun: get) => fun.apply()
		}
		/** Updates the issuer referenced by the given issuer ID. */
		class update(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/wallet_object.issuer""")
			/** Perform the request */
			def withIssuer(body: Schema.Issuer) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.Issuer])
		}
		object update {
			def apply(resourceId: String)(using signer: RequestSigner, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"walletobjects/v1/issuer/${resourceId}").addQueryStringParameters())
		}
		/** Updates the issuer referenced by the given issuer ID. This method supports patch semantics. */
		class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/wallet_object.issuer""")
			/** Perform the request */
			def withIssuer(body: Schema.Issuer) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Issuer])
		}
		object patch {
			def apply(resourceId: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"walletobjects/v1/issuer/${resourceId}").addQueryStringParameters())
		}
		/** Returns a list of all issuers shared to the caller. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.IssuerListResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/wallet_object.issuer""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.IssuerListResponse])
		}
		object list {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"walletobjects/v1/issuer").addQueryStringParameters())
			given Conversion[list, Future[Schema.IssuerListResponse]] = (fun: list) => fun.apply()
		}
	}
	object flightclass {
		/** Inserts an flight class with the given ID and properties. */
		class insert(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/wallet_object.issuer""")
			/** Perform the request */
			def withFlightClass(body: Schema.FlightClass) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.FlightClass])
		}
		object insert {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): insert = new insert(ws.url(BASE_URL + s"walletobjects/v1/flightClass").addQueryStringParameters())
		}
		/** Adds a message to the flight class referenced by the given class ID. */
		class addmessage(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/wallet_object.issuer""")
			/** Perform the request */
			def withAddMessageRequest(body: Schema.AddMessageRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.FlightClassAddMessageResponse])
		}
		object addmessage {
			def apply(resourceId: String)(using signer: RequestSigner, ec: ExecutionContext): addmessage = new addmessage(ws.url(BASE_URL + s"walletobjects/v1/flightClass/${resourceId}/addMessage").addQueryStringParameters())
		}
		/** Returns the flight class with the given class ID. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.FlightClass]) {
			val scopes = Seq("""https://www.googleapis.com/auth/wallet_object.issuer""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.FlightClass])
		}
		object get {
			def apply(resourceId: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"walletobjects/v1/flightClass/${resourceId}").addQueryStringParameters())
			given Conversion[get, Future[Schema.FlightClass]] = (fun: get) => fun.apply()
		}
		/** Updates the flight class referenced by the given class ID. */
		class update(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/wallet_object.issuer""")
			/** Perform the request */
			def withFlightClass(body: Schema.FlightClass) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.FlightClass])
		}
		object update {
			def apply(resourceId: String)(using signer: RequestSigner, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"walletobjects/v1/flightClass/${resourceId}").addQueryStringParameters())
		}
		/** Updates the flight class referenced by the given class ID. This method supports patch semantics. */
		class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/wallet_object.issuer""")
			/** Perform the request */
			def withFlightClass(body: Schema.FlightClass) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.FlightClass])
		}
		object patch {
			def apply(resourceId: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"walletobjects/v1/flightClass/${resourceId}").addQueryStringParameters())
		}
		/** Returns a list of all flight classes for a given issuer ID. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.FlightClassListResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/wallet_object.issuer""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.FlightClassListResponse])
		}
		object list {
			def apply(maxResults: Int, issuerId: String, token: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"walletobjects/v1/flightClass").addQueryStringParameters("maxResults" -> maxResults.toString, "issuerId" -> issuerId.toString, "token" -> token.toString))
			given Conversion[list, Future[Schema.FlightClassListResponse]] = (fun: list) => fun.apply()
		}
	}
	object eventticketobject {
		/** Adds a message to the event ticket object referenced by the given object ID. */
		class addmessage(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/wallet_object.issuer""")
			/** Perform the request */
			def withAddMessageRequest(body: Schema.AddMessageRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.EventTicketObjectAddMessageResponse])
		}
		object addmessage {
			def apply(resourceId: String)(using signer: RequestSigner, ec: ExecutionContext): addmessage = new addmessage(ws.url(BASE_URL + s"walletobjects/v1/eventTicketObject/${resourceId}/addMessage").addQueryStringParameters())
		}
		/** Returns the event ticket object with the given object ID. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.EventTicketObject]) {
			val scopes = Seq("""https://www.googleapis.com/auth/wallet_object.issuer""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.EventTicketObject])
		}
		object get {
			def apply(resourceId: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"walletobjects/v1/eventTicketObject/${resourceId}").addQueryStringParameters())
			given Conversion[get, Future[Schema.EventTicketObject]] = (fun: get) => fun.apply()
		}
		/** Updates the event ticket object referenced by the given object ID. */
		class update(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/wallet_object.issuer""")
			/** Perform the request */
			def withEventTicketObject(body: Schema.EventTicketObject) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.EventTicketObject])
		}
		object update {
			def apply(resourceId: String)(using signer: RequestSigner, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"walletobjects/v1/eventTicketObject/${resourceId}").addQueryStringParameters())
		}
		/** Updates the event ticket object referenced by the given object ID. This method supports patch semantics. */
		class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/wallet_object.issuer""")
			/** Perform the request */
			def withEventTicketObject(body: Schema.EventTicketObject) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.EventTicketObject])
		}
		object patch {
			def apply(resourceId: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"walletobjects/v1/eventTicketObject/${resourceId}").addQueryStringParameters())
		}
		/** Returns a list of all event ticket objects for a given issuer ID. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.EventTicketObjectListResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/wallet_object.issuer""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.EventTicketObjectListResponse])
		}
		object list {
			def apply(token: String, classId: String, maxResults: Int)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"walletobjects/v1/eventTicketObject").addQueryStringParameters("token" -> token.toString, "classId" -> classId.toString, "maxResults" -> maxResults.toString))
			given Conversion[list, Future[Schema.EventTicketObjectListResponse]] = (fun: list) => fun.apply()
		}
		/** Modifies linked offer objects for the event ticket object with the given ID. */
		class modifylinkedofferobjects(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/wallet_object.issuer""")
			/** Perform the request */
			def withModifyLinkedOfferObjectsRequest(body: Schema.ModifyLinkedOfferObjectsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.EventTicketObject])
		}
		object modifylinkedofferobjects {
			def apply(resourceId: String)(using signer: RequestSigner, ec: ExecutionContext): modifylinkedofferobjects = new modifylinkedofferobjects(ws.url(BASE_URL + s"walletobjects/v1/eventTicketObject/${resourceId}/modifyLinkedOfferObjects").addQueryStringParameters())
		}
		/** Inserts an event ticket object with the given ID and properties. */
		class insert(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/wallet_object.issuer""")
			/** Perform the request */
			def withEventTicketObject(body: Schema.EventTicketObject) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.EventTicketObject])
		}
		object insert {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): insert = new insert(ws.url(BASE_URL + s"walletobjects/v1/eventTicketObject").addQueryStringParameters())
		}
	}
	object loyaltyclass {
		/** Inserts an loyalty class with the given ID and properties. */
		class insert(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/wallet_object.issuer""")
			/** Perform the request */
			def withLoyaltyClass(body: Schema.LoyaltyClass) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.LoyaltyClass])
		}
		object insert {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): insert = new insert(ws.url(BASE_URL + s"walletobjects/v1/loyaltyClass").addQueryStringParameters())
		}
		/** Adds a message to the loyalty class referenced by the given class ID. */
		class addmessage(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/wallet_object.issuer""")
			/** Perform the request */
			def withAddMessageRequest(body: Schema.AddMessageRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.LoyaltyClassAddMessageResponse])
		}
		object addmessage {
			def apply(resourceId: String)(using signer: RequestSigner, ec: ExecutionContext): addmessage = new addmessage(ws.url(BASE_URL + s"walletobjects/v1/loyaltyClass/${resourceId}/addMessage").addQueryStringParameters())
		}
		/** Returns the loyalty class with the given class ID. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.LoyaltyClass]) {
			val scopes = Seq("""https://www.googleapis.com/auth/wallet_object.issuer""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.LoyaltyClass])
		}
		object get {
			def apply(resourceId: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"walletobjects/v1/loyaltyClass/${resourceId}").addQueryStringParameters())
			given Conversion[get, Future[Schema.LoyaltyClass]] = (fun: get) => fun.apply()
		}
		/** Updates the loyalty class referenced by the given class ID. */
		class update(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/wallet_object.issuer""")
			/** Perform the request */
			def withLoyaltyClass(body: Schema.LoyaltyClass) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.LoyaltyClass])
		}
		object update {
			def apply(resourceId: String)(using signer: RequestSigner, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"walletobjects/v1/loyaltyClass/${resourceId}").addQueryStringParameters())
		}
		/** Updates the loyalty class referenced by the given class ID. This method supports patch semantics. */
		class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/wallet_object.issuer""")
			/** Perform the request */
			def withLoyaltyClass(body: Schema.LoyaltyClass) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.LoyaltyClass])
		}
		object patch {
			def apply(resourceId: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"walletobjects/v1/loyaltyClass/${resourceId}").addQueryStringParameters())
		}
		/** Returns a list of all loyalty classes for a given issuer ID. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.LoyaltyClassListResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/wallet_object.issuer""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.LoyaltyClassListResponse])
		}
		object list {
			def apply(maxResults: Int, token: String, issuerId: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"walletobjects/v1/loyaltyClass").addQueryStringParameters("maxResults" -> maxResults.toString, "token" -> token.toString, "issuerId" -> issuerId.toString))
			given Conversion[list, Future[Schema.LoyaltyClassListResponse]] = (fun: list) => fun.apply()
		}
	}
	object offerclass {
		/** Inserts an offer class with the given ID and properties. */
		class insert(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/wallet_object.issuer""")
			/** Perform the request */
			def withOfferClass(body: Schema.OfferClass) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.OfferClass])
		}
		object insert {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): insert = new insert(ws.url(BASE_URL + s"walletobjects/v1/offerClass").addQueryStringParameters())
		}
		/** Adds a message to the offer class referenced by the given class ID. */
		class addmessage(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/wallet_object.issuer""")
			/** Perform the request */
			def withAddMessageRequest(body: Schema.AddMessageRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.OfferClassAddMessageResponse])
		}
		object addmessage {
			def apply(resourceId: String)(using signer: RequestSigner, ec: ExecutionContext): addmessage = new addmessage(ws.url(BASE_URL + s"walletobjects/v1/offerClass/${resourceId}/addMessage").addQueryStringParameters())
		}
		/** Returns the offer class with the given class ID. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.OfferClass]) {
			val scopes = Seq("""https://www.googleapis.com/auth/wallet_object.issuer""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.OfferClass])
		}
		object get {
			def apply(resourceId: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"walletobjects/v1/offerClass/${resourceId}").addQueryStringParameters())
			given Conversion[get, Future[Schema.OfferClass]] = (fun: get) => fun.apply()
		}
		/** Updates the offer class referenced by the given class ID. */
		class update(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/wallet_object.issuer""")
			/** Perform the request */
			def withOfferClass(body: Schema.OfferClass) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.OfferClass])
		}
		object update {
			def apply(resourceId: String)(using signer: RequestSigner, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"walletobjects/v1/offerClass/${resourceId}").addQueryStringParameters())
		}
		/** Updates the offer class referenced by the given class ID. This method supports patch semantics. */
		class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/wallet_object.issuer""")
			/** Perform the request */
			def withOfferClass(body: Schema.OfferClass) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.OfferClass])
		}
		object patch {
			def apply(resourceId: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"walletobjects/v1/offerClass/${resourceId}").addQueryStringParameters())
		}
		/** Returns a list of all offer classes for a given issuer ID. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.OfferClassListResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/wallet_object.issuer""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.OfferClassListResponse])
		}
		object list {
			def apply(issuerId: String, maxResults: Int, token: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"walletobjects/v1/offerClass").addQueryStringParameters("issuerId" -> issuerId.toString, "maxResults" -> maxResults.toString, "token" -> token.toString))
			given Conversion[list, Future[Schema.OfferClassListResponse]] = (fun: list) => fun.apply()
		}
	}
	object genericclass {
		/** Inserts a generic class with the given ID and properties. */
		class insert(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/wallet_object.issuer""")
			/** Perform the request */
			def withGenericClass(body: Schema.GenericClass) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GenericClass])
		}
		object insert {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): insert = new insert(ws.url(BASE_URL + s"walletobjects/v1/genericClass").addQueryStringParameters())
		}
		/** Adds a message to the generic class referenced by the given class ID. */
		class addmessage(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/wallet_object.issuer""")
			/** Perform the request */
			def withAddMessageRequest(body: Schema.AddMessageRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GenericClassAddMessageResponse])
		}
		object addmessage {
			def apply(resourceId: String)(using signer: RequestSigner, ec: ExecutionContext): addmessage = new addmessage(ws.url(BASE_URL + s"walletobjects/v1/genericClass/${resourceId}/addMessage").addQueryStringParameters())
		}
		/** Returns the generic class with the given class ID. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GenericClass]) {
			val scopes = Seq("""https://www.googleapis.com/auth/wallet_object.issuer""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GenericClass])
		}
		object get {
			def apply(resourceId: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"walletobjects/v1/genericClass/${resourceId}").addQueryStringParameters())
			given Conversion[get, Future[Schema.GenericClass]] = (fun: get) => fun.apply()
		}
		/** Updates the Generic class referenced by the given class ID. */
		class update(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/wallet_object.issuer""")
			/** Perform the request */
			def withGenericClass(body: Schema.GenericClass) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.GenericClass])
		}
		object update {
			def apply(resourceId: String)(using signer: RequestSigner, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"walletobjects/v1/genericClass/${resourceId}").addQueryStringParameters())
		}
		/** Updates the generic class referenced by the given class ID. This method supports patch semantics. */
		class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/wallet_object.issuer""")
			/** Perform the request */
			def withGenericClass(body: Schema.GenericClass) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GenericClass])
		}
		object patch {
			def apply(resourceId: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"walletobjects/v1/genericClass/${resourceId}").addQueryStringParameters())
		}
		/** Returns a list of all generic classes for a given issuer ID. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GenericClassListResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/wallet_object.issuer""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GenericClassListResponse])
		}
		object list {
			def apply(issuerId: String, token: String, maxResults: Int)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"walletobjects/v1/genericClass").addQueryStringParameters("issuerId" -> issuerId.toString, "token" -> token.toString, "maxResults" -> maxResults.toString))
			given Conversion[list, Future[Schema.GenericClassListResponse]] = (fun: list) => fun.apply()
		}
	}
	object eventticketclass {
		/** Inserts an event ticket class with the given ID and properties. */
		class insert(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/wallet_object.issuer""")
			/** Perform the request */
			def withEventTicketClass(body: Schema.EventTicketClass) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.EventTicketClass])
		}
		object insert {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): insert = new insert(ws.url(BASE_URL + s"walletobjects/v1/eventTicketClass").addQueryStringParameters())
		}
		/** Adds a message to the event ticket class referenced by the given class ID. */
		class addmessage(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/wallet_object.issuer""")
			/** Perform the request */
			def withAddMessageRequest(body: Schema.AddMessageRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.EventTicketClassAddMessageResponse])
		}
		object addmessage {
			def apply(resourceId: String)(using signer: RequestSigner, ec: ExecutionContext): addmessage = new addmessage(ws.url(BASE_URL + s"walletobjects/v1/eventTicketClass/${resourceId}/addMessage").addQueryStringParameters())
		}
		/** Returns the event ticket class with the given class ID. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.EventTicketClass]) {
			val scopes = Seq("""https://www.googleapis.com/auth/wallet_object.issuer""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.EventTicketClass])
		}
		object get {
			def apply(resourceId: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"walletobjects/v1/eventTicketClass/${resourceId}").addQueryStringParameters())
			given Conversion[get, Future[Schema.EventTicketClass]] = (fun: get) => fun.apply()
		}
		/** Updates the event ticket class referenced by the given class ID. */
		class update(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/wallet_object.issuer""")
			/** Perform the request */
			def withEventTicketClass(body: Schema.EventTicketClass) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.EventTicketClass])
		}
		object update {
			def apply(resourceId: String)(using signer: RequestSigner, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"walletobjects/v1/eventTicketClass/${resourceId}").addQueryStringParameters())
		}
		/** Updates the event ticket class referenced by the given class ID. This method supports patch semantics. */
		class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/wallet_object.issuer""")
			/** Perform the request */
			def withEventTicketClass(body: Schema.EventTicketClass) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.EventTicketClass])
		}
		object patch {
			def apply(resourceId: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"walletobjects/v1/eventTicketClass/${resourceId}").addQueryStringParameters())
		}
		/** Returns a list of all event ticket classes for a given issuer ID. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.EventTicketClassListResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/wallet_object.issuer""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.EventTicketClassListResponse])
		}
		object list {
			def apply(token: String, maxResults: Int, issuerId: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"walletobjects/v1/eventTicketClass").addQueryStringParameters("token" -> token.toString, "maxResults" -> maxResults.toString, "issuerId" -> issuerId.toString))
			given Conversion[list, Future[Schema.EventTicketClassListResponse]] = (fun: list) => fun.apply()
		}
	}
	object transitobject {
		/** Inserts an transit object with the given ID and properties. */
		class insert(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/wallet_object.issuer""")
			/** Perform the request */
			def withTransitObject(body: Schema.TransitObject) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.TransitObject])
		}
		object insert {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): insert = new insert(ws.url(BASE_URL + s"walletobjects/v1/transitObject").addQueryStringParameters())
		}
		/** Adds a message to the transit object referenced by the given object ID. */
		class addmessage(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/wallet_object.issuer""")
			/** Perform the request */
			def withAddMessageRequest(body: Schema.AddMessageRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.TransitObjectAddMessageResponse])
		}
		object addmessage {
			def apply(resourceId: String)(using signer: RequestSigner, ec: ExecutionContext): addmessage = new addmessage(ws.url(BASE_URL + s"walletobjects/v1/transitObject/${resourceId}/addMessage").addQueryStringParameters())
		}
		/** Returns the transit object with the given object ID. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.TransitObject]) {
			val scopes = Seq("""https://www.googleapis.com/auth/wallet_object.issuer""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.TransitObject])
		}
		object get {
			def apply(resourceId: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"walletobjects/v1/transitObject/${resourceId}").addQueryStringParameters())
			given Conversion[get, Future[Schema.TransitObject]] = (fun: get) => fun.apply()
		}
		/** Updates the transit object referenced by the given object ID. */
		class update(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/wallet_object.issuer""")
			/** Perform the request */
			def withTransitObject(body: Schema.TransitObject) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.TransitObject])
		}
		object update {
			def apply(resourceId: String)(using signer: RequestSigner, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"walletobjects/v1/transitObject/${resourceId}").addQueryStringParameters())
		}
		/** Updates the transit object referenced by the given object ID. This method supports patch semantics. */
		class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/wallet_object.issuer""")
			/** Perform the request */
			def withTransitObject(body: Schema.TransitObject) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.TransitObject])
		}
		object patch {
			def apply(resourceId: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"walletobjects/v1/transitObject/${resourceId}").addQueryStringParameters())
		}
		/** Returns a list of all transit objects for a given issuer ID. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.TransitObjectListResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/wallet_object.issuer""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.TransitObjectListResponse])
		}
		object list {
			def apply(classId: String, maxResults: Int, token: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"walletobjects/v1/transitObject").addQueryStringParameters("classId" -> classId.toString, "maxResults" -> maxResults.toString, "token" -> token.toString))
			given Conversion[list, Future[Schema.TransitObjectListResponse]] = (fun: list) => fun.apply()
		}
	}
	object giftcardobject {
		/** Inserts an gift card object with the given ID and properties. */
		class insert(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/wallet_object.issuer""")
			/** Perform the request */
			def withGiftCardObject(body: Schema.GiftCardObject) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GiftCardObject])
		}
		object insert {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): insert = new insert(ws.url(BASE_URL + s"walletobjects/v1/giftCardObject").addQueryStringParameters())
		}
		/** Adds a message to the gift card object referenced by the given object ID. */
		class addmessage(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/wallet_object.issuer""")
			/** Perform the request */
			def withAddMessageRequest(body: Schema.AddMessageRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GiftCardObjectAddMessageResponse])
		}
		object addmessage {
			def apply(resourceId: String)(using signer: RequestSigner, ec: ExecutionContext): addmessage = new addmessage(ws.url(BASE_URL + s"walletobjects/v1/giftCardObject/${resourceId}/addMessage").addQueryStringParameters())
		}
		/** Returns the gift card object with the given object ID. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GiftCardObject]) {
			val scopes = Seq("""https://www.googleapis.com/auth/wallet_object.issuer""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GiftCardObject])
		}
		object get {
			def apply(resourceId: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"walletobjects/v1/giftCardObject/${resourceId}").addQueryStringParameters())
			given Conversion[get, Future[Schema.GiftCardObject]] = (fun: get) => fun.apply()
		}
		/** Updates the gift card object referenced by the given object ID. */
		class update(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/wallet_object.issuer""")
			/** Perform the request */
			def withGiftCardObject(body: Schema.GiftCardObject) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.GiftCardObject])
		}
		object update {
			def apply(resourceId: String)(using signer: RequestSigner, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"walletobjects/v1/giftCardObject/${resourceId}").addQueryStringParameters())
		}
		/** Updates the gift card object referenced by the given object ID. This method supports patch semantics. */
		class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/wallet_object.issuer""")
			/** Perform the request */
			def withGiftCardObject(body: Schema.GiftCardObject) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GiftCardObject])
		}
		object patch {
			def apply(resourceId: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"walletobjects/v1/giftCardObject/${resourceId}").addQueryStringParameters())
		}
		/** Returns a list of all gift card objects for a given issuer ID. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GiftCardObjectListResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/wallet_object.issuer""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GiftCardObjectListResponse])
		}
		object list {
			def apply(classId: String, maxResults: Int, token: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"walletobjects/v1/giftCardObject").addQueryStringParameters("classId" -> classId.toString, "maxResults" -> maxResults.toString, "token" -> token.toString))
			given Conversion[list, Future[Schema.GiftCardObjectListResponse]] = (fun: list) => fun.apply()
		}
	}
	object flightobject {
		/** Inserts an flight object with the given ID and properties. */
		class insert(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/wallet_object.issuer""")
			/** Perform the request */
			def withFlightObject(body: Schema.FlightObject) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.FlightObject])
		}
		object insert {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): insert = new insert(ws.url(BASE_URL + s"walletobjects/v1/flightObject").addQueryStringParameters())
		}
		/** Adds a message to the flight object referenced by the given object ID. */
		class addmessage(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/wallet_object.issuer""")
			/** Perform the request */
			def withAddMessageRequest(body: Schema.AddMessageRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.FlightObjectAddMessageResponse])
		}
		object addmessage {
			def apply(resourceId: String)(using signer: RequestSigner, ec: ExecutionContext): addmessage = new addmessage(ws.url(BASE_URL + s"walletobjects/v1/flightObject/${resourceId}/addMessage").addQueryStringParameters())
		}
		/** Returns the flight object with the given object ID. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.FlightObject]) {
			val scopes = Seq("""https://www.googleapis.com/auth/wallet_object.issuer""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.FlightObject])
		}
		object get {
			def apply(resourceId: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"walletobjects/v1/flightObject/${resourceId}").addQueryStringParameters())
			given Conversion[get, Future[Schema.FlightObject]] = (fun: get) => fun.apply()
		}
		/** Updates the flight object referenced by the given object ID. */
		class update(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/wallet_object.issuer""")
			/** Perform the request */
			def withFlightObject(body: Schema.FlightObject) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.FlightObject])
		}
		object update {
			def apply(resourceId: String)(using signer: RequestSigner, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"walletobjects/v1/flightObject/${resourceId}").addQueryStringParameters())
		}
		/** Updates the flight object referenced by the given object ID. This method supports patch semantics. */
		class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/wallet_object.issuer""")
			/** Perform the request */
			def withFlightObject(body: Schema.FlightObject) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.FlightObject])
		}
		object patch {
			def apply(resourceId: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"walletobjects/v1/flightObject/${resourceId}").addQueryStringParameters())
		}
		/** Returns a list of all flight objects for a given issuer ID. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.FlightObjectListResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/wallet_object.issuer""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.FlightObjectListResponse])
		}
		object list {
			def apply(classId: String, maxResults: Int, token: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"walletobjects/v1/flightObject").addQueryStringParameters("classId" -> classId.toString, "maxResults" -> maxResults.toString, "token" -> token.toString))
			given Conversion[list, Future[Schema.FlightObjectListResponse]] = (fun: list) => fun.apply()
		}
	}
	object media {
		/** Downloads rotating barcode values for the transit object referenced by the given object ID. */
		class download(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Media]) {
			val scopes = Seq("""https://www.googleapis.com/auth/wallet_object.issuer""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Media])
		}
		object download {
			def apply(resourceId: String)(using signer: RequestSigner, ec: ExecutionContext): download = new download(ws.url(BASE_URL + s"walletobjects/v1/transitObject/${resourceId}/downloadRotatingBarcodeValues").addQueryStringParameters())
			given Conversion[download, Future[Schema.Media]] = (fun: download) => fun.apply()
		}
		/** Uploads rotating barcode values for the transit object referenced by the given object ID. Note the max upload size is specified in google3/production/config/cdd/apps-upload/customers/payments-consumer-passes/config.gcl and enforced by Scotty. */
		class upload(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/wallet_object.issuer""")
			/** Perform the request */
			def withTransitObjectUploadRotatingBarcodeValuesRequest(body: Schema.TransitObjectUploadRotatingBarcodeValuesRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.TransitObjectUploadRotatingBarcodeValuesResponse])
		}
		object upload {
			def apply(resourceId: String)(using signer: RequestSigner, ec: ExecutionContext): upload = new upload(ws.url(BASE_URL + s"walletobjects/v1/transitObject/${resourceId}/uploadRotatingBarcodeValues").addQueryStringParameters())
		}
	}
	object giftcardclass {
		/** Inserts an gift card class with the given ID and properties. */
		class insert(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/wallet_object.issuer""")
			/** Perform the request */
			def withGiftCardClass(body: Schema.GiftCardClass) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GiftCardClass])
		}
		object insert {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): insert = new insert(ws.url(BASE_URL + s"walletobjects/v1/giftCardClass").addQueryStringParameters())
		}
		/** Adds a message to the gift card class referenced by the given class ID. */
		class addmessage(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/wallet_object.issuer""")
			/** Perform the request */
			def withAddMessageRequest(body: Schema.AddMessageRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GiftCardClassAddMessageResponse])
		}
		object addmessage {
			def apply(resourceId: String)(using signer: RequestSigner, ec: ExecutionContext): addmessage = new addmessage(ws.url(BASE_URL + s"walletobjects/v1/giftCardClass/${resourceId}/addMessage").addQueryStringParameters())
		}
		/** Returns the gift card class with the given class ID. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GiftCardClass]) {
			val scopes = Seq("""https://www.googleapis.com/auth/wallet_object.issuer""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GiftCardClass])
		}
		object get {
			def apply(resourceId: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"walletobjects/v1/giftCardClass/${resourceId}").addQueryStringParameters())
			given Conversion[get, Future[Schema.GiftCardClass]] = (fun: get) => fun.apply()
		}
		/** Updates the gift card class referenced by the given class ID. */
		class update(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/wallet_object.issuer""")
			/** Perform the request */
			def withGiftCardClass(body: Schema.GiftCardClass) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.GiftCardClass])
		}
		object update {
			def apply(resourceId: String)(using signer: RequestSigner, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"walletobjects/v1/giftCardClass/${resourceId}").addQueryStringParameters())
		}
		/** Updates the gift card class referenced by the given class ID. This method supports patch semantics. */
		class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/wallet_object.issuer""")
			/** Perform the request */
			def withGiftCardClass(body: Schema.GiftCardClass) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GiftCardClass])
		}
		object patch {
			def apply(resourceId: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"walletobjects/v1/giftCardClass/${resourceId}").addQueryStringParameters())
		}
		/** Returns a list of all gift card classes for a given issuer ID. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GiftCardClassListResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/wallet_object.issuer""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GiftCardClassListResponse])
		}
		object list {
			def apply(issuerId: String, maxResults: Int, token: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"walletobjects/v1/giftCardClass").addQueryStringParameters("issuerId" -> issuerId.toString, "maxResults" -> maxResults.toString, "token" -> token.toString))
			given Conversion[list, Future[Schema.GiftCardClassListResponse]] = (fun: list) => fun.apply()
		}
	}
	object smarttap {
		/** Inserts the smart tap. */
		class insert(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/wallet_object.issuer""")
			/** Perform the request */
			def withSmartTap(body: Schema.SmartTap) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.SmartTap])
		}
		object insert {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): insert = new insert(ws.url(BASE_URL + s"walletobjects/v1/smartTap").addQueryStringParameters())
		}
	}
	object transitclass {
		/** Inserts a transit class with the given ID and properties. */
		class insert(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/wallet_object.issuer""")
			/** Perform the request */
			def withTransitClass(body: Schema.TransitClass) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.TransitClass])
		}
		object insert {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): insert = new insert(ws.url(BASE_URL + s"walletobjects/v1/transitClass").addQueryStringParameters())
		}
		/** Adds a message to the transit class referenced by the given class ID. */
		class addmessage(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/wallet_object.issuer""")
			/** Perform the request */
			def withAddMessageRequest(body: Schema.AddMessageRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.TransitClassAddMessageResponse])
		}
		object addmessage {
			def apply(resourceId: String)(using signer: RequestSigner, ec: ExecutionContext): addmessage = new addmessage(ws.url(BASE_URL + s"walletobjects/v1/transitClass/${resourceId}/addMessage").addQueryStringParameters())
		}
		/** Returns the transit class with the given class ID. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.TransitClass]) {
			val scopes = Seq("""https://www.googleapis.com/auth/wallet_object.issuer""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.TransitClass])
		}
		object get {
			def apply(resourceId: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"walletobjects/v1/transitClass/${resourceId}").addQueryStringParameters())
			given Conversion[get, Future[Schema.TransitClass]] = (fun: get) => fun.apply()
		}
		/** Updates the transit class referenced by the given class ID. */
		class update(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/wallet_object.issuer""")
			/** Perform the request */
			def withTransitClass(body: Schema.TransitClass) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.TransitClass])
		}
		object update {
			def apply(resourceId: String)(using signer: RequestSigner, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"walletobjects/v1/transitClass/${resourceId}").addQueryStringParameters())
		}
		/** Updates the transit class referenced by the given class ID. This method supports patch semantics. */
		class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/wallet_object.issuer""")
			/** Perform the request */
			def withTransitClass(body: Schema.TransitClass) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.TransitClass])
		}
		object patch {
			def apply(resourceId: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"walletobjects/v1/transitClass/${resourceId}").addQueryStringParameters())
		}
		/** Returns a list of all transit classes for a given issuer ID. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.TransitClassListResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/wallet_object.issuer""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.TransitClassListResponse])
		}
		object list {
			def apply(maxResults: Int, issuerId: String, token: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"walletobjects/v1/transitClass").addQueryStringParameters("maxResults" -> maxResults.toString, "issuerId" -> issuerId.toString, "token" -> token.toString))
			given Conversion[list, Future[Schema.TransitClassListResponse]] = (fun: list) => fun.apply()
		}
	}
	object permissions {
		/** Returns the permissions for the given issuer id. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Permissions]) {
			val scopes = Seq("""https://www.googleapis.com/auth/wallet_object.issuer""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Permissions])
		}
		object get {
			def apply(resourceId: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"walletobjects/v1/permissions/${resourceId}").addQueryStringParameters())
			given Conversion[get, Future[Schema.Permissions]] = (fun: get) => fun.apply()
		}
		/** Updates the permissions for the given issuer. */
		class update(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/wallet_object.issuer""")
			/** Perform the request */
			def withPermissions(body: Schema.Permissions) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.Permissions])
		}
		object update {
			def apply(resourceId: String)(using signer: RequestSigner, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"walletobjects/v1/permissions/${resourceId}").addQueryStringParameters())
		}
	}
	object jwt {
		/** Inserts the resources in the JWT. */
		class insert(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/wallet_object.issuer""")
			/** Perform the request */
			def withJwtResource(body: Schema.JwtResource) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.JwtInsertResponse])
		}
		object insert {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): insert = new insert(ws.url(BASE_URL + s"walletobjects/v1/jwt").addQueryStringParameters())
		}
	}
}
