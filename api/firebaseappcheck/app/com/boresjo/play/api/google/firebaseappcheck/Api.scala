package com.boresjo.play.api.google.firebaseappcheck

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
		"""https://www.googleapis.com/auth/firebase""" /* View and administer all your Firebase data and settings */,
		"""https://www.googleapis.com/auth/cloud-platform""" /* See, edit, configure, and delete your Google Cloud data and see the email address for your Google Account. */
	)

	private val BASE_URL = "https://firebaseappcheck.googleapis.com/"

	object oauthClients {
		/** Validates a debug token secret that you have previously created using CreateDebugToken. If valid, returns an AppCheckToken. Note that a restrictive quota is enforced on this method to prevent accidental exposure of the app to abuse. */
		class exchangeDebugToken(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/firebase""")
			/** Perform the request */
			def withGoogleFirebaseAppcheckV1ExchangeDebugTokenRequest(body: Schema.GoogleFirebaseAppcheckV1ExchangeDebugTokenRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleFirebaseAppcheckV1AppCheckToken])
		}
		object exchangeDebugToken {
			def apply(oauthClientsId :PlayApi, app: String)(using signer: RequestSigner, ec: ExecutionContext): exchangeDebugToken = new exchangeDebugToken(ws.url(BASE_URL + s"v1/oauthClients/${oauthClientsId}:exchangeDebugToken").addQueryStringParameters("app" -> app.toString))
		}
		/** Generates a challenge that protects the integrity of an immediately following call to ExchangeAppAttestAttestation or ExchangeAppAttestAssertion. A challenge should not be reused for multiple calls. */
		class generateAppAttestChallenge(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/firebase""")
			/** Perform the request */
			def withGoogleFirebaseAppcheckV1GenerateAppAttestChallengeRequest(body: Schema.GoogleFirebaseAppcheckV1GenerateAppAttestChallengeRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleFirebaseAppcheckV1GenerateAppAttestChallengeResponse])
		}
		object generateAppAttestChallenge {
			def apply(oauthClientsId :PlayApi, app: String)(using signer: RequestSigner, ec: ExecutionContext): generateAppAttestChallenge = new generateAppAttestChallenge(ws.url(BASE_URL + s"v1/oauthClients/${oauthClientsId}:generateAppAttestChallenge").addQueryStringParameters("app" -> app.toString))
		}
		/** Accepts an App Attest assertion and an artifact previously obtained from ExchangeAppAttestAttestation and verifies those with Apple. If valid, returns an AppCheckToken. */
		class exchangeAppAttestAssertion(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/firebase""")
			/** Perform the request */
			def withGoogleFirebaseAppcheckV1ExchangeAppAttestAssertionRequest(body: Schema.GoogleFirebaseAppcheckV1ExchangeAppAttestAssertionRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleFirebaseAppcheckV1AppCheckToken])
		}
		object exchangeAppAttestAssertion {
			def apply(oauthClientsId :PlayApi, app: String)(using signer: RequestSigner, ec: ExecutionContext): exchangeAppAttestAssertion = new exchangeAppAttestAssertion(ws.url(BASE_URL + s"v1/oauthClients/${oauthClientsId}:exchangeAppAttestAssertion").addQueryStringParameters("app" -> app.toString))
		}
		/** Accepts an App Attest CBOR attestation and verifies it with Apple using your preconfigured team and bundle IDs. If valid, returns an attestation artifact that can later be exchanged for an AppCheckToken using ExchangeAppAttestAssertion. For convenience and performance, this method's response object will also contain an AppCheckToken (if the verification is successful). */
		class exchangeAppAttestAttestation(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/firebase""")
			/** Perform the request */
			def withGoogleFirebaseAppcheckV1ExchangeAppAttestAttestationRequest(body: Schema.GoogleFirebaseAppcheckV1ExchangeAppAttestAttestationRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleFirebaseAppcheckV1ExchangeAppAttestAttestationResponse])
		}
		object exchangeAppAttestAttestation {
			def apply(oauthClientsId :PlayApi, app: String)(using signer: RequestSigner, ec: ExecutionContext): exchangeAppAttestAttestation = new exchangeAppAttestAttestation(ws.url(BASE_URL + s"v1/oauthClients/${oauthClientsId}:exchangeAppAttestAttestation").addQueryStringParameters("app" -> app.toString))
		}
	}
	object jwks {
		/** Returns a public JWK set as specified by [RFC 7517](https://tools.ietf.org/html/rfc7517) that can be used to verify App Check tokens. Exactly one of the public keys in the returned set will successfully validate any App Check token that is currently valid. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleFirebaseAppcheckV1PublicJwkSet]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/firebase""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleFirebaseAppcheckV1PublicJwkSet])
		}
		object get {
			def apply(name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/jwks").addQueryStringParameters("name" -> name.toString))
			given Conversion[get, Future[Schema.GoogleFirebaseAppcheckV1PublicJwkSet]] = (fun: get) => fun.apply()
		}
	}
	object projects {
		object apps {
			/** Generates a challenge that protects the integrity of an immediately following integrity verdict request to the Play Integrity API. The next call to ExchangePlayIntegrityToken using the resulting integrity token will verify the presence and validity of the challenge. A challenge should not be reused for multiple calls. */
			class generatePlayIntegrityChallenge(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/firebase""")
				/** Perform the request */
				def withGoogleFirebaseAppcheckV1GeneratePlayIntegrityChallengeRequest(body: Schema.GoogleFirebaseAppcheckV1GeneratePlayIntegrityChallengeRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleFirebaseAppcheckV1GeneratePlayIntegrityChallengeResponse])
			}
			object generatePlayIntegrityChallenge {
				def apply(projectsId :PlayApi, appsId :PlayApi, app: String)(using signer: RequestSigner, ec: ExecutionContext): generatePlayIntegrityChallenge = new generatePlayIntegrityChallenge(ws.url(BASE_URL + s"v1/projects/${projectsId}/apps/${appsId}:generatePlayIntegrityChallenge").addQueryStringParameters("app" -> app.toString))
			}
			/** Validates a [reCAPTCHA v3 response token](https://developers.google.com/recaptcha/docs/v3). If valid, returns an AppCheckToken. */
			class exchangeRecaptchaV3Token(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/firebase""")
				/** Perform the request */
				def withGoogleFirebaseAppcheckV1ExchangeRecaptchaV3TokenRequest(body: Schema.GoogleFirebaseAppcheckV1ExchangeRecaptchaV3TokenRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleFirebaseAppcheckV1AppCheckToken])
			}
			object exchangeRecaptchaV3Token {
				def apply(projectsId :PlayApi, appsId :PlayApi, app: String)(using signer: RequestSigner, ec: ExecutionContext): exchangeRecaptchaV3Token = new exchangeRecaptchaV3Token(ws.url(BASE_URL + s"v1/projects/${projectsId}/apps/${appsId}:exchangeRecaptchaV3Token").addQueryStringParameters("app" -> app.toString))
			}
			/** Validates a [reCAPTCHA Enterprise response token](https://cloud.google.com/recaptcha-enterprise/docs/create-assessment#retrieve_token). If valid, returns an AppCheckToken. */
			class exchangeRecaptchaEnterpriseToken(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/firebase""")
				/** Perform the request */
				def withGoogleFirebaseAppcheckV1ExchangeRecaptchaEnterpriseTokenRequest(body: Schema.GoogleFirebaseAppcheckV1ExchangeRecaptchaEnterpriseTokenRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleFirebaseAppcheckV1AppCheckToken])
			}
			object exchangeRecaptchaEnterpriseToken {
				def apply(projectsId :PlayApi, appsId :PlayApi, app: String)(using signer: RequestSigner, ec: ExecutionContext): exchangeRecaptchaEnterpriseToken = new exchangeRecaptchaEnterpriseToken(ws.url(BASE_URL + s"v1/projects/${projectsId}/apps/${appsId}:exchangeRecaptchaEnterpriseToken").addQueryStringParameters("app" -> app.toString))
			}
			/** Validates a custom token signed using your project's Admin SDK service account credentials. If valid, returns an AppCheckToken. */
			class exchangeCustomToken(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/firebase""")
				/** Perform the request */
				def withGoogleFirebaseAppcheckV1ExchangeCustomTokenRequest(body: Schema.GoogleFirebaseAppcheckV1ExchangeCustomTokenRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleFirebaseAppcheckV1AppCheckToken])
			}
			object exchangeCustomToken {
				def apply(projectsId :PlayApi, appsId :PlayApi, app: String)(using signer: RequestSigner, ec: ExecutionContext): exchangeCustomToken = new exchangeCustomToken(ws.url(BASE_URL + s"v1/projects/${projectsId}/apps/${appsId}:exchangeCustomToken").addQueryStringParameters("app" -> app.toString))
			}
			/** Validates an [integrity verdict response token from Play Integrity](https://developer.android.com/google/play/integrity/verdict#decrypt-verify). If valid, returns an AppCheckToken. */
			class exchangePlayIntegrityToken(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/firebase""")
				/** Perform the request */
				def withGoogleFirebaseAppcheckV1ExchangePlayIntegrityTokenRequest(body: Schema.GoogleFirebaseAppcheckV1ExchangePlayIntegrityTokenRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleFirebaseAppcheckV1AppCheckToken])
			}
			object exchangePlayIntegrityToken {
				def apply(projectsId :PlayApi, appsId :PlayApi, app: String)(using signer: RequestSigner, ec: ExecutionContext): exchangePlayIntegrityToken = new exchangePlayIntegrityToken(ws.url(BASE_URL + s"v1/projects/${projectsId}/apps/${appsId}:exchangePlayIntegrityToken").addQueryStringParameters("app" -> app.toString))
			}
			/** Validates a debug token secret that you have previously created using CreateDebugToken. If valid, returns an AppCheckToken. Note that a restrictive quota is enforced on this method to prevent accidental exposure of the app to abuse. */
			class exchangeDebugToken(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/firebase""")
				/** Perform the request */
				def withGoogleFirebaseAppcheckV1ExchangeDebugTokenRequest(body: Schema.GoogleFirebaseAppcheckV1ExchangeDebugTokenRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleFirebaseAppcheckV1AppCheckToken])
			}
			object exchangeDebugToken {
				def apply(projectsId :PlayApi, appsId :PlayApi, app: String)(using signer: RequestSigner, ec: ExecutionContext): exchangeDebugToken = new exchangeDebugToken(ws.url(BASE_URL + s"v1/projects/${projectsId}/apps/${appsId}:exchangeDebugToken").addQueryStringParameters("app" -> app.toString))
			}
			/** Accepts an App Attest CBOR attestation and verifies it with Apple using your preconfigured team and bundle IDs. If valid, returns an attestation artifact that can later be exchanged for an AppCheckToken using ExchangeAppAttestAssertion. For convenience and performance, this method's response object will also contain an AppCheckToken (if the verification is successful). */
			class exchangeAppAttestAttestation(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/firebase""")
				/** Perform the request */
				def withGoogleFirebaseAppcheckV1ExchangeAppAttestAttestationRequest(body: Schema.GoogleFirebaseAppcheckV1ExchangeAppAttestAttestationRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleFirebaseAppcheckV1ExchangeAppAttestAttestationResponse])
			}
			object exchangeAppAttestAttestation {
				def apply(projectsId :PlayApi, appsId :PlayApi, app: String)(using signer: RequestSigner, ec: ExecutionContext): exchangeAppAttestAttestation = new exchangeAppAttestAttestation(ws.url(BASE_URL + s"v1/projects/${projectsId}/apps/${appsId}:exchangeAppAttestAttestation").addQueryStringParameters("app" -> app.toString))
			}
			/** Validates a [SafetyNet token](https://developer.android.com/training/safetynet/attestation#request-attestation-step). If valid, returns an AppCheckToken. */
			class exchangeSafetyNetToken(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/firebase""")
				/** Perform the request */
				def withGoogleFirebaseAppcheckV1ExchangeSafetyNetTokenRequest(body: Schema.GoogleFirebaseAppcheckV1ExchangeSafetyNetTokenRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleFirebaseAppcheckV1AppCheckToken])
			}
			object exchangeSafetyNetToken {
				def apply(projectsId :PlayApi, appsId :PlayApi, app: String)(using signer: RequestSigner, ec: ExecutionContext): exchangeSafetyNetToken = new exchangeSafetyNetToken(ws.url(BASE_URL + s"v1/projects/${projectsId}/apps/${appsId}:exchangeSafetyNetToken").addQueryStringParameters("app" -> app.toString))
			}
			/** Generates a challenge that protects the integrity of an immediately following call to ExchangeAppAttestAttestation or ExchangeAppAttestAssertion. A challenge should not be reused for multiple calls. */
			class generateAppAttestChallenge(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/firebase""")
				/** Perform the request */
				def withGoogleFirebaseAppcheckV1GenerateAppAttestChallengeRequest(body: Schema.GoogleFirebaseAppcheckV1GenerateAppAttestChallengeRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleFirebaseAppcheckV1GenerateAppAttestChallengeResponse])
			}
			object generateAppAttestChallenge {
				def apply(projectsId :PlayApi, appsId :PlayApi, app: String)(using signer: RequestSigner, ec: ExecutionContext): generateAppAttestChallenge = new generateAppAttestChallenge(ws.url(BASE_URL + s"v1/projects/${projectsId}/apps/${appsId}:generateAppAttestChallenge").addQueryStringParameters("app" -> app.toString))
			}
			/** Accepts an App Attest assertion and an artifact previously obtained from ExchangeAppAttestAttestation and verifies those with Apple. If valid, returns an AppCheckToken. */
			class exchangeAppAttestAssertion(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/firebase""")
				/** Perform the request */
				def withGoogleFirebaseAppcheckV1ExchangeAppAttestAssertionRequest(body: Schema.GoogleFirebaseAppcheckV1ExchangeAppAttestAssertionRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleFirebaseAppcheckV1AppCheckToken])
			}
			object exchangeAppAttestAssertion {
				def apply(projectsId :PlayApi, appsId :PlayApi, app: String)(using signer: RequestSigner, ec: ExecutionContext): exchangeAppAttestAssertion = new exchangeAppAttestAssertion(ws.url(BASE_URL + s"v1/projects/${projectsId}/apps/${appsId}:exchangeAppAttestAssertion").addQueryStringParameters("app" -> app.toString))
			}
			/** Accepts a [`device_token`](https://developer.apple.com/documentation/devicecheck/dcdevice) issued by DeviceCheck, and attempts to validate it with Apple. If valid, returns an AppCheckToken. */
			class exchangeDeviceCheckToken(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/firebase""")
				/** Perform the request */
				def withGoogleFirebaseAppcheckV1ExchangeDeviceCheckTokenRequest(body: Schema.GoogleFirebaseAppcheckV1ExchangeDeviceCheckTokenRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleFirebaseAppcheckV1AppCheckToken])
			}
			object exchangeDeviceCheckToken {
				def apply(projectsId :PlayApi, appsId :PlayApi, app: String)(using signer: RequestSigner, ec: ExecutionContext): exchangeDeviceCheckToken = new exchangeDeviceCheckToken(ws.url(BASE_URL + s"v1/projects/${projectsId}/apps/${appsId}:exchangeDeviceCheckToken").addQueryStringParameters("app" -> app.toString))
			}
			object debugTokens {
				/** Creates a new DebugToken for the specified app. For security reasons, after the creation operation completes, the `token` field cannot be updated or retrieved, but you can revoke the debug token using DeleteDebugToken. Each app can have a maximum of 20 debug tokens. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/firebase""")
					/** Perform the request */
					def withGoogleFirebaseAppcheckV1DebugToken(body: Schema.GoogleFirebaseAppcheckV1DebugToken) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleFirebaseAppcheckV1DebugToken])
				}
				object create {
					def apply(projectsId :PlayApi, appsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/apps/${appsId}/debugTokens").addQueryStringParameters("parent" -> parent.toString))
				}
				/** Deletes the specified DebugToken. A deleted debug token cannot be used to exchange for an App Check token. Use this method when you suspect the secret `token` has been compromised or when you no longer need the debug token. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/firebase""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
				}
				object delete {
					def apply(projectsId :PlayApi, appsId :PlayApi, debugTokensId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/apps/${appsId}/debugTokens/${debugTokensId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
				}
				/** Gets the specified DebugToken. For security reasons, the `token` field is never populated in the response. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleFirebaseAppcheckV1DebugToken]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/firebase""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleFirebaseAppcheckV1DebugToken])
				}
				object get {
					def apply(projectsId :PlayApi, appsId :PlayApi, debugTokensId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/apps/${appsId}/debugTokens/${debugTokensId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleFirebaseAppcheckV1DebugToken]] = (fun: get) => fun.apply()
				}
				/** Updates the specified DebugToken. For security reasons, the `token` field cannot be updated, nor will it be populated in the response, but you can revoke the debug token using DeleteDebugToken. */
				class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/firebase""")
					/** Perform the request */
					def withGoogleFirebaseAppcheckV1DebugToken(body: Schema.GoogleFirebaseAppcheckV1DebugToken) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleFirebaseAppcheckV1DebugToken])
				}
				object patch {
					def apply(projectsId :PlayApi, appsId :PlayApi, debugTokensId :PlayApi, updateMask: String, name: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/apps/${appsId}/debugTokens/${debugTokensId}").addQueryStringParameters("updateMask" -> updateMask.toString, "name" -> name.toString))
				}
				/** Lists all DebugTokens for the specified app. For security reasons, the `token` field is never populated in the response. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleFirebaseAppcheckV1ListDebugTokensResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/firebase""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleFirebaseAppcheckV1ListDebugTokensResponse])
				}
				object list {
					def apply(projectsId :PlayApi, appsId :PlayApi, pageToken: String, pageSize: Int, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/apps/${appsId}/debugTokens").addQueryStringParameters("pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString, "parent" -> parent.toString))
					given Conversion[list, Future[Schema.GoogleFirebaseAppcheckV1ListDebugTokensResponse]] = (fun: list) => fun.apply()
				}
			}
			object safetyNetConfig {
				/** Atomically gets the SafetyNetConfigs for the specified list of apps. */
				class batchGet(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleFirebaseAppcheckV1BatchGetSafetyNetConfigsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/firebase""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleFirebaseAppcheckV1BatchGetSafetyNetConfigsResponse])
				}
				object batchGet {
					def apply(projectsId :PlayApi, parent: String, names: String)(using signer: RequestSigner, ec: ExecutionContext): batchGet = new batchGet(ws.url(BASE_URL + s"v1/projects/${projectsId}/apps/-/safetyNetConfig:batchGet").addQueryStringParameters("parent" -> parent.toString, "names" -> names.toString))
					given Conversion[batchGet, Future[Schema.GoogleFirebaseAppcheckV1BatchGetSafetyNetConfigsResponse]] = (fun: batchGet) => fun.apply()
				}
				/** Updates the SafetyNetConfig for the specified app. While this configuration is incomplete or invalid, the app will be unable to exchange SafetyNet tokens for App Check tokens. */
				class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/firebase""")
					/** Perform the request */
					def withGoogleFirebaseAppcheckV1SafetyNetConfig(body: Schema.GoogleFirebaseAppcheckV1SafetyNetConfig) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleFirebaseAppcheckV1SafetyNetConfig])
				}
				object patch {
					def apply(projectsId :PlayApi, appsId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/apps/${appsId}/safetyNetConfig").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
				}
				/** Gets the SafetyNetConfig for the specified app. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleFirebaseAppcheckV1SafetyNetConfig]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/firebase""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleFirebaseAppcheckV1SafetyNetConfig])
				}
				object get {
					def apply(projectsId :PlayApi, appsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/apps/${appsId}/safetyNetConfig").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleFirebaseAppcheckV1SafetyNetConfig]] = (fun: get) => fun.apply()
				}
			}
			object playIntegrityConfig {
				/** Updates the PlayIntegrityConfig for the specified app. While this configuration is incomplete or invalid, the app will be unable to exchange Play Integrity tokens for App Check tokens. */
				class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/firebase""")
					/** Perform the request */
					def withGoogleFirebaseAppcheckV1PlayIntegrityConfig(body: Schema.GoogleFirebaseAppcheckV1PlayIntegrityConfig) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleFirebaseAppcheckV1PlayIntegrityConfig])
				}
				object patch {
					def apply(projectsId :PlayApi, appsId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/apps/${appsId}/playIntegrityConfig").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
				}
				/** Gets the PlayIntegrityConfig for the specified app. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleFirebaseAppcheckV1PlayIntegrityConfig]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/firebase""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleFirebaseAppcheckV1PlayIntegrityConfig])
				}
				object get {
					def apply(projectsId :PlayApi, appsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/apps/${appsId}/playIntegrityConfig").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleFirebaseAppcheckV1PlayIntegrityConfig]] = (fun: get) => fun.apply()
				}
				/** Atomically gets the PlayIntegrityConfigs for the specified list of apps. */
				class batchGet(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleFirebaseAppcheckV1BatchGetPlayIntegrityConfigsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/firebase""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleFirebaseAppcheckV1BatchGetPlayIntegrityConfigsResponse])
				}
				object batchGet {
					def apply(projectsId :PlayApi, names: String, parent: String)(using signer: RequestSigner, ec: ExecutionContext): batchGet = new batchGet(ws.url(BASE_URL + s"v1/projects/${projectsId}/apps/-/playIntegrityConfig:batchGet").addQueryStringParameters("names" -> names.toString, "parent" -> parent.toString))
					given Conversion[batchGet, Future[Schema.GoogleFirebaseAppcheckV1BatchGetPlayIntegrityConfigsResponse]] = (fun: batchGet) => fun.apply()
				}
			}
			object recaptchaEnterpriseConfig {
				/** Updates the RecaptchaEnterpriseConfig for the specified app. While this configuration is incomplete or invalid, the app will be unable to exchange reCAPTCHA Enterprise tokens for App Check tokens. */
				class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/firebase""")
					/** Perform the request */
					def withGoogleFirebaseAppcheckV1RecaptchaEnterpriseConfig(body: Schema.GoogleFirebaseAppcheckV1RecaptchaEnterpriseConfig) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleFirebaseAppcheckV1RecaptchaEnterpriseConfig])
				}
				object patch {
					def apply(projectsId :PlayApi, appsId :PlayApi, updateMask: String, name: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/apps/${appsId}/recaptchaEnterpriseConfig").addQueryStringParameters("updateMask" -> updateMask.toString, "name" -> name.toString))
				}
				/** Gets the RecaptchaEnterpriseConfig for the specified app. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleFirebaseAppcheckV1RecaptchaEnterpriseConfig]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/firebase""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleFirebaseAppcheckV1RecaptchaEnterpriseConfig])
				}
				object get {
					def apply(projectsId :PlayApi, appsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/apps/${appsId}/recaptchaEnterpriseConfig").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleFirebaseAppcheckV1RecaptchaEnterpriseConfig]] = (fun: get) => fun.apply()
				}
				/** Atomically gets the RecaptchaEnterpriseConfigs for the specified list of apps. */
				class batchGet(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleFirebaseAppcheckV1BatchGetRecaptchaEnterpriseConfigsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/firebase""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleFirebaseAppcheckV1BatchGetRecaptchaEnterpriseConfigsResponse])
				}
				object batchGet {
					def apply(projectsId :PlayApi, parent: String, names: String)(using signer: RequestSigner, ec: ExecutionContext): batchGet = new batchGet(ws.url(BASE_URL + s"v1/projects/${projectsId}/apps/-/recaptchaEnterpriseConfig:batchGet").addQueryStringParameters("parent" -> parent.toString, "names" -> names.toString))
					given Conversion[batchGet, Future[Schema.GoogleFirebaseAppcheckV1BatchGetRecaptchaEnterpriseConfigsResponse]] = (fun: batchGet) => fun.apply()
				}
			}
			object appAttestConfig {
				/** Atomically gets the AppAttestConfigs for the specified list of apps. */
				class batchGet(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleFirebaseAppcheckV1BatchGetAppAttestConfigsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/firebase""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleFirebaseAppcheckV1BatchGetAppAttestConfigsResponse])
				}
				object batchGet {
					def apply(projectsId :PlayApi, parent: String, names: String)(using signer: RequestSigner, ec: ExecutionContext): batchGet = new batchGet(ws.url(BASE_URL + s"v1/projects/${projectsId}/apps/-/appAttestConfig:batchGet").addQueryStringParameters("parent" -> parent.toString, "names" -> names.toString))
					given Conversion[batchGet, Future[Schema.GoogleFirebaseAppcheckV1BatchGetAppAttestConfigsResponse]] = (fun: batchGet) => fun.apply()
				}
				/** Gets the AppAttestConfig for the specified app. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleFirebaseAppcheckV1AppAttestConfig]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/firebase""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleFirebaseAppcheckV1AppAttestConfig])
				}
				object get {
					def apply(projectsId :PlayApi, appsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/apps/${appsId}/appAttestConfig").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleFirebaseAppcheckV1AppAttestConfig]] = (fun: get) => fun.apply()
				}
				/** Updates the AppAttestConfig for the specified app. While this configuration is incomplete or invalid, the app will be unable to exchange AppAttest tokens for App Check tokens. */
				class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/firebase""")
					/** Perform the request */
					def withGoogleFirebaseAppcheckV1AppAttestConfig(body: Schema.GoogleFirebaseAppcheckV1AppAttestConfig) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleFirebaseAppcheckV1AppAttestConfig])
				}
				object patch {
					def apply(projectsId :PlayApi, appsId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/apps/${appsId}/appAttestConfig").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
				}
			}
			object recaptchaV3Config {
				/** Atomically gets the RecaptchaV3Configs for the specified list of apps. For security reasons, the `site_secret` field is never populated in the response. */
				class batchGet(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleFirebaseAppcheckV1BatchGetRecaptchaV3ConfigsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/firebase""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleFirebaseAppcheckV1BatchGetRecaptchaV3ConfigsResponse])
				}
				object batchGet {
					def apply(projectsId :PlayApi, names: String, parent: String)(using signer: RequestSigner, ec: ExecutionContext): batchGet = new batchGet(ws.url(BASE_URL + s"v1/projects/${projectsId}/apps/-/recaptchaV3Config:batchGet").addQueryStringParameters("names" -> names.toString, "parent" -> parent.toString))
					given Conversion[batchGet, Future[Schema.GoogleFirebaseAppcheckV1BatchGetRecaptchaV3ConfigsResponse]] = (fun: batchGet) => fun.apply()
				}
				/** Updates the RecaptchaV3Config for the specified app. While this configuration is incomplete or invalid, the app will be unable to exchange reCAPTCHA V3 tokens for App Check tokens. For security reasons, the `site_secret` field is never populated in the response. */
				class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/firebase""")
					/** Perform the request */
					def withGoogleFirebaseAppcheckV1RecaptchaV3Config(body: Schema.GoogleFirebaseAppcheckV1RecaptchaV3Config) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleFirebaseAppcheckV1RecaptchaV3Config])
				}
				object patch {
					def apply(projectsId :PlayApi, appsId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/apps/${appsId}/recaptchaV3Config").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
				}
				/** Gets the RecaptchaV3Config for the specified app. For security reasons, the `site_secret` field is never populated in the response. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleFirebaseAppcheckV1RecaptchaV3Config]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/firebase""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleFirebaseAppcheckV1RecaptchaV3Config])
				}
				object get {
					def apply(projectsId :PlayApi, appsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/apps/${appsId}/recaptchaV3Config").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleFirebaseAppcheckV1RecaptchaV3Config]] = (fun: get) => fun.apply()
				}
			}
			object deviceCheckConfig {
				/** Atomically gets the DeviceCheckConfigs for the specified list of apps. For security reasons, the `private_key` field is never populated in the response. */
				class batchGet(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleFirebaseAppcheckV1BatchGetDeviceCheckConfigsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/firebase""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleFirebaseAppcheckV1BatchGetDeviceCheckConfigsResponse])
				}
				object batchGet {
					def apply(projectsId :PlayApi, names: String, parent: String)(using signer: RequestSigner, ec: ExecutionContext): batchGet = new batchGet(ws.url(BASE_URL + s"v1/projects/${projectsId}/apps/-/deviceCheckConfig:batchGet").addQueryStringParameters("names" -> names.toString, "parent" -> parent.toString))
					given Conversion[batchGet, Future[Schema.GoogleFirebaseAppcheckV1BatchGetDeviceCheckConfigsResponse]] = (fun: batchGet) => fun.apply()
				}
				/** Gets the DeviceCheckConfig for the specified app. For security reasons, the `private_key` field is never populated in the response. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleFirebaseAppcheckV1DeviceCheckConfig]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/firebase""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleFirebaseAppcheckV1DeviceCheckConfig])
				}
				object get {
					def apply(projectsId :PlayApi, appsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/apps/${appsId}/deviceCheckConfig").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleFirebaseAppcheckV1DeviceCheckConfig]] = (fun: get) => fun.apply()
				}
				/** Updates the DeviceCheckConfig for the specified app. While this configuration is incomplete or invalid, the app will be unable to exchange DeviceCheck tokens for App Check tokens. For security reasons, the `private_key` field is never populated in the response. */
				class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/firebase""")
					/** Perform the request */
					def withGoogleFirebaseAppcheckV1DeviceCheckConfig(body: Schema.GoogleFirebaseAppcheckV1DeviceCheckConfig) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleFirebaseAppcheckV1DeviceCheckConfig])
				}
				object patch {
					def apply(projectsId :PlayApi, appsId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/apps/${appsId}/deviceCheckConfig").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
				}
			}
		}
		object services {
			/** Atomically updates the specified Service configurations. */
			class batchUpdate(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/firebase""")
				/** Perform the request */
				def withGoogleFirebaseAppcheckV1BatchUpdateServicesRequest(body: Schema.GoogleFirebaseAppcheckV1BatchUpdateServicesRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleFirebaseAppcheckV1BatchUpdateServicesResponse])
			}
			object batchUpdate {
				def apply(projectsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): batchUpdate = new batchUpdate(ws.url(BASE_URL + s"v1/projects/${projectsId}/services:batchUpdate").addQueryStringParameters("parent" -> parent.toString))
			}
			/** Gets the Service configuration for the specified service name. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleFirebaseAppcheckV1Service]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/firebase""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleFirebaseAppcheckV1Service])
			}
			object get {
				def apply(projectsId :PlayApi, servicesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/services/${servicesId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.GoogleFirebaseAppcheckV1Service]] = (fun: get) => fun.apply()
			}
			/** Updates the specified Service configuration. */
			class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/firebase""")
				/** Perform the request */
				def withGoogleFirebaseAppcheckV1Service(body: Schema.GoogleFirebaseAppcheckV1Service) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleFirebaseAppcheckV1Service])
			}
			object patch {
				def apply(projectsId :PlayApi, servicesId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/services/${servicesId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
			}
			/** Lists all Service configurations for the specified project. Only Services which were explicitly configured using UpdateService or BatchUpdateServices will be returned. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleFirebaseAppcheckV1ListServicesResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/firebase""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleFirebaseAppcheckV1ListServicesResponse])
			}
			object list {
				def apply(projectsId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/services").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.GoogleFirebaseAppcheckV1ListServicesResponse]] = (fun: list) => fun.apply()
			}
			object resourcePolicies {
				/** Creates the specified ResourcePolicy configuration. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/firebase""")
					/** Perform the request */
					def withGoogleFirebaseAppcheckV1ResourcePolicy(body: Schema.GoogleFirebaseAppcheckV1ResourcePolicy) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleFirebaseAppcheckV1ResourcePolicy])
				}
				object create {
					def apply(projectsId :PlayApi, servicesId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/services/${servicesId}/resourcePolicies").addQueryStringParameters("parent" -> parent.toString))
				}
				/** Atomically updates the specified ResourcePolicy configurations. */
				class batchUpdate(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/firebase""")
					/** Perform the request */
					def withGoogleFirebaseAppcheckV1BatchUpdateResourcePoliciesRequest(body: Schema.GoogleFirebaseAppcheckV1BatchUpdateResourcePoliciesRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleFirebaseAppcheckV1BatchUpdateResourcePoliciesResponse])
				}
				object batchUpdate {
					def apply(projectsId :PlayApi, servicesId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): batchUpdate = new batchUpdate(ws.url(BASE_URL + s"v1/projects/${projectsId}/services/${servicesId}/resourcePolicies:batchUpdate").addQueryStringParameters("parent" -> parent.toString))
				}
				/** Deletes the specified ResourcePolicy configuration. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/firebase""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
				}
				object delete {
					def apply(projectsId :PlayApi, servicesId :PlayApi, resourcePoliciesId :PlayApi, name: String, etag: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/services/${servicesId}/resourcePolicies/${resourcePoliciesId}").addQueryStringParameters("name" -> name.toString, "etag" -> etag.toString))
					given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
				}
				/** Gets the requested ResourcePolicy configuration. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleFirebaseAppcheckV1ResourcePolicy]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/firebase""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleFirebaseAppcheckV1ResourcePolicy])
				}
				object get {
					def apply(projectsId :PlayApi, servicesId :PlayApi, resourcePoliciesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/services/${servicesId}/resourcePolicies/${resourcePoliciesId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleFirebaseAppcheckV1ResourcePolicy]] = (fun: get) => fun.apply()
				}
				/** Updates the specified ResourcePolicy configuration. */
				class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/firebase""")
					/** Perform the request */
					def withGoogleFirebaseAppcheckV1ResourcePolicy(body: Schema.GoogleFirebaseAppcheckV1ResourcePolicy) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleFirebaseAppcheckV1ResourcePolicy])
				}
				object patch {
					def apply(projectsId :PlayApi, servicesId :PlayApi, resourcePoliciesId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/services/${servicesId}/resourcePolicies/${resourcePoliciesId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
				}
				/** Lists all ResourcePolicy configurations for the specified project and service. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleFirebaseAppcheckV1ListResourcePoliciesResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/firebase""")
					/** Optional. Filters the results by the specified rule. For the exact syntax of this field, please consult the [AIP-160](https://google.aip.dev/160) standard. Currently, since the only fields in the ResourcePolicy resource are the scalar fields `enforcement_mode` and `target_resource`, this method does not support the traversal operator (`.`) or the has operator (`:`). Here are some examples of valid filters: &#42; `enforcement_mode = ENFORCED` &#42; `target_resource = "//oauth2.googleapis.com/projects/12345/oauthClients/"` &#42; `enforcement_mode = ENFORCED AND target_resource = "//oauth2.googleapis.com/projects/12345/oauthClients/"` */
					def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleFirebaseAppcheckV1ListResourcePoliciesResponse])
				}
				object list {
					def apply(projectsId :PlayApi, servicesId :PlayApi, parent: String, pageToken: String, pageSize: Int)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/services/${servicesId}/resourcePolicies").addQueryStringParameters("parent" -> parent.toString, "pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString))
					given Conversion[list, Future[Schema.GoogleFirebaseAppcheckV1ListResourcePoliciesResponse]] = (fun: list) => fun.apply()
				}
			}
		}
	}
}
