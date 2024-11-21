package com.boresjo.play.api.google.firebaseappcheck

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://firebaseappcheck.googleapis.com/"

	object oauthClients {
		class exchangeDebugToken(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withGoogleFirebaseAppcheckV1ExchangeDebugTokenRequest(body: Schema.GoogleFirebaseAppcheckV1ExchangeDebugTokenRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleFirebaseAppcheckV1AppCheckToken])
		}
		object exchangeDebugToken {
			def apply(oauthClientsId :PlayApi, app: String)(using auth: AuthToken, ec: ExecutionContext): exchangeDebugToken = new exchangeDebugToken(auth(ws.url(BASE_URL + s"v1/oauthClients/${oauthClientsId}:exchangeDebugToken")).addQueryStringParameters("app" -> app.toString))
		}
		class generateAppAttestChallenge(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withGoogleFirebaseAppcheckV1GenerateAppAttestChallengeRequest(body: Schema.GoogleFirebaseAppcheckV1GenerateAppAttestChallengeRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleFirebaseAppcheckV1GenerateAppAttestChallengeResponse])
		}
		object generateAppAttestChallenge {
			def apply(oauthClientsId :PlayApi, app: String)(using auth: AuthToken, ec: ExecutionContext): generateAppAttestChallenge = new generateAppAttestChallenge(auth(ws.url(BASE_URL + s"v1/oauthClients/${oauthClientsId}:generateAppAttestChallenge")).addQueryStringParameters("app" -> app.toString))
		}
		class exchangeAppAttestAssertion(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withGoogleFirebaseAppcheckV1ExchangeAppAttestAssertionRequest(body: Schema.GoogleFirebaseAppcheckV1ExchangeAppAttestAssertionRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleFirebaseAppcheckV1AppCheckToken])
		}
		object exchangeAppAttestAssertion {
			def apply(oauthClientsId :PlayApi, app: String)(using auth: AuthToken, ec: ExecutionContext): exchangeAppAttestAssertion = new exchangeAppAttestAssertion(auth(ws.url(BASE_URL + s"v1/oauthClients/${oauthClientsId}:exchangeAppAttestAssertion")).addQueryStringParameters("app" -> app.toString))
		}
		class exchangeAppAttestAttestation(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withGoogleFirebaseAppcheckV1ExchangeAppAttestAttestationRequest(body: Schema.GoogleFirebaseAppcheckV1ExchangeAppAttestAttestationRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleFirebaseAppcheckV1ExchangeAppAttestAttestationResponse])
		}
		object exchangeAppAttestAttestation {
			def apply(oauthClientsId :PlayApi, app: String)(using auth: AuthToken, ec: ExecutionContext): exchangeAppAttestAttestation = new exchangeAppAttestAttestation(auth(ws.url(BASE_URL + s"v1/oauthClients/${oauthClientsId}:exchangeAppAttestAttestation")).addQueryStringParameters("app" -> app.toString))
		}
	}
	object jwks {
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleFirebaseAppcheckV1PublicJwkSet]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.GoogleFirebaseAppcheckV1PublicJwkSet])
		}
		object get {
			def apply(name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/jwks")).addQueryStringParameters("name" -> name.toString))
			given Conversion[get, Future[Schema.GoogleFirebaseAppcheckV1PublicJwkSet]] = (fun: get) => fun.apply()
		}
	}
	object projects {
		object apps {
			class generatePlayIntegrityChallenge(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGoogleFirebaseAppcheckV1GeneratePlayIntegrityChallengeRequest(body: Schema.GoogleFirebaseAppcheckV1GeneratePlayIntegrityChallengeRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleFirebaseAppcheckV1GeneratePlayIntegrityChallengeResponse])
			}
			object generatePlayIntegrityChallenge {
				def apply(projectsId :PlayApi, appsId :PlayApi, app: String)(using auth: AuthToken, ec: ExecutionContext): generatePlayIntegrityChallenge = new generatePlayIntegrityChallenge(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/apps/${appsId}:generatePlayIntegrityChallenge")).addQueryStringParameters("app" -> app.toString))
			}
			class exchangeRecaptchaV3Token(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGoogleFirebaseAppcheckV1ExchangeRecaptchaV3TokenRequest(body: Schema.GoogleFirebaseAppcheckV1ExchangeRecaptchaV3TokenRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleFirebaseAppcheckV1AppCheckToken])
			}
			object exchangeRecaptchaV3Token {
				def apply(projectsId :PlayApi, appsId :PlayApi, app: String)(using auth: AuthToken, ec: ExecutionContext): exchangeRecaptchaV3Token = new exchangeRecaptchaV3Token(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/apps/${appsId}:exchangeRecaptchaV3Token")).addQueryStringParameters("app" -> app.toString))
			}
			class exchangeRecaptchaEnterpriseToken(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGoogleFirebaseAppcheckV1ExchangeRecaptchaEnterpriseTokenRequest(body: Schema.GoogleFirebaseAppcheckV1ExchangeRecaptchaEnterpriseTokenRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleFirebaseAppcheckV1AppCheckToken])
			}
			object exchangeRecaptchaEnterpriseToken {
				def apply(projectsId :PlayApi, appsId :PlayApi, app: String)(using auth: AuthToken, ec: ExecutionContext): exchangeRecaptchaEnterpriseToken = new exchangeRecaptchaEnterpriseToken(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/apps/${appsId}:exchangeRecaptchaEnterpriseToken")).addQueryStringParameters("app" -> app.toString))
			}
			class exchangeCustomToken(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGoogleFirebaseAppcheckV1ExchangeCustomTokenRequest(body: Schema.GoogleFirebaseAppcheckV1ExchangeCustomTokenRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleFirebaseAppcheckV1AppCheckToken])
			}
			object exchangeCustomToken {
				def apply(projectsId :PlayApi, appsId :PlayApi, app: String)(using auth: AuthToken, ec: ExecutionContext): exchangeCustomToken = new exchangeCustomToken(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/apps/${appsId}:exchangeCustomToken")).addQueryStringParameters("app" -> app.toString))
			}
			class exchangePlayIntegrityToken(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGoogleFirebaseAppcheckV1ExchangePlayIntegrityTokenRequest(body: Schema.GoogleFirebaseAppcheckV1ExchangePlayIntegrityTokenRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleFirebaseAppcheckV1AppCheckToken])
			}
			object exchangePlayIntegrityToken {
				def apply(projectsId :PlayApi, appsId :PlayApi, app: String)(using auth: AuthToken, ec: ExecutionContext): exchangePlayIntegrityToken = new exchangePlayIntegrityToken(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/apps/${appsId}:exchangePlayIntegrityToken")).addQueryStringParameters("app" -> app.toString))
			}
			class exchangeDebugToken(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGoogleFirebaseAppcheckV1ExchangeDebugTokenRequest(body: Schema.GoogleFirebaseAppcheckV1ExchangeDebugTokenRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleFirebaseAppcheckV1AppCheckToken])
			}
			object exchangeDebugToken {
				def apply(projectsId :PlayApi, appsId :PlayApi, app: String)(using auth: AuthToken, ec: ExecutionContext): exchangeDebugToken = new exchangeDebugToken(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/apps/${appsId}:exchangeDebugToken")).addQueryStringParameters("app" -> app.toString))
			}
			class exchangeAppAttestAttestation(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGoogleFirebaseAppcheckV1ExchangeAppAttestAttestationRequest(body: Schema.GoogleFirebaseAppcheckV1ExchangeAppAttestAttestationRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleFirebaseAppcheckV1ExchangeAppAttestAttestationResponse])
			}
			object exchangeAppAttestAttestation {
				def apply(projectsId :PlayApi, appsId :PlayApi, app: String)(using auth: AuthToken, ec: ExecutionContext): exchangeAppAttestAttestation = new exchangeAppAttestAttestation(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/apps/${appsId}:exchangeAppAttestAttestation")).addQueryStringParameters("app" -> app.toString))
			}
			class exchangeSafetyNetToken(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGoogleFirebaseAppcheckV1ExchangeSafetyNetTokenRequest(body: Schema.GoogleFirebaseAppcheckV1ExchangeSafetyNetTokenRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleFirebaseAppcheckV1AppCheckToken])
			}
			object exchangeSafetyNetToken {
				def apply(projectsId :PlayApi, appsId :PlayApi, app: String)(using auth: AuthToken, ec: ExecutionContext): exchangeSafetyNetToken = new exchangeSafetyNetToken(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/apps/${appsId}:exchangeSafetyNetToken")).addQueryStringParameters("app" -> app.toString))
			}
			class generateAppAttestChallenge(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGoogleFirebaseAppcheckV1GenerateAppAttestChallengeRequest(body: Schema.GoogleFirebaseAppcheckV1GenerateAppAttestChallengeRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleFirebaseAppcheckV1GenerateAppAttestChallengeResponse])
			}
			object generateAppAttestChallenge {
				def apply(projectsId :PlayApi, appsId :PlayApi, app: String)(using auth: AuthToken, ec: ExecutionContext): generateAppAttestChallenge = new generateAppAttestChallenge(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/apps/${appsId}:generateAppAttestChallenge")).addQueryStringParameters("app" -> app.toString))
			}
			class exchangeAppAttestAssertion(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGoogleFirebaseAppcheckV1ExchangeAppAttestAssertionRequest(body: Schema.GoogleFirebaseAppcheckV1ExchangeAppAttestAssertionRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleFirebaseAppcheckV1AppCheckToken])
			}
			object exchangeAppAttestAssertion {
				def apply(projectsId :PlayApi, appsId :PlayApi, app: String)(using auth: AuthToken, ec: ExecutionContext): exchangeAppAttestAssertion = new exchangeAppAttestAssertion(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/apps/${appsId}:exchangeAppAttestAssertion")).addQueryStringParameters("app" -> app.toString))
			}
			class exchangeDeviceCheckToken(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGoogleFirebaseAppcheckV1ExchangeDeviceCheckTokenRequest(body: Schema.GoogleFirebaseAppcheckV1ExchangeDeviceCheckTokenRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleFirebaseAppcheckV1AppCheckToken])
			}
			object exchangeDeviceCheckToken {
				def apply(projectsId :PlayApi, appsId :PlayApi, app: String)(using auth: AuthToken, ec: ExecutionContext): exchangeDeviceCheckToken = new exchangeDeviceCheckToken(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/apps/${appsId}:exchangeDeviceCheckToken")).addQueryStringParameters("app" -> app.toString))
			}
			object debugTokens {
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleFirebaseAppcheckV1DebugToken(body: Schema.GoogleFirebaseAppcheckV1DebugToken) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleFirebaseAppcheckV1DebugToken])
				}
				object create {
					def apply(projectsId :PlayApi, appsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/apps/${appsId}/debugTokens")).addQueryStringParameters("parent" -> parent.toString))
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
					def apply() = req.execute("DELETE").map(_.json.as[Schema.GoogleProtobufEmpty])
				}
				object delete {
					def apply(projectsId :PlayApi, appsId :PlayApi, debugTokensId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/apps/${appsId}/debugTokens/${debugTokensId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleFirebaseAppcheckV1DebugToken]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.GoogleFirebaseAppcheckV1DebugToken])
				}
				object get {
					def apply(projectsId :PlayApi, appsId :PlayApi, debugTokensId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/apps/${appsId}/debugTokens/${debugTokensId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleFirebaseAppcheckV1DebugToken]] = (fun: get) => fun.apply()
				}
				class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleFirebaseAppcheckV1DebugToken(body: Schema.GoogleFirebaseAppcheckV1DebugToken) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.GoogleFirebaseAppcheckV1DebugToken])
				}
				object patch {
					def apply(projectsId :PlayApi, appsId :PlayApi, debugTokensId :PlayApi, updateMask: String, name: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/apps/${appsId}/debugTokens/${debugTokensId}")).addQueryStringParameters("updateMask" -> updateMask.toString, "name" -> name.toString))
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleFirebaseAppcheckV1ListDebugTokensResponse]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.GoogleFirebaseAppcheckV1ListDebugTokensResponse])
				}
				object list {
					def apply(projectsId :PlayApi, appsId :PlayApi, pageToken: String, pageSize: Int, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/apps/${appsId}/debugTokens")).addQueryStringParameters("pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString, "parent" -> parent.toString))
					given Conversion[list, Future[Schema.GoogleFirebaseAppcheckV1ListDebugTokensResponse]] = (fun: list) => fun.apply()
				}
			}
			object safetyNetConfig {
				class batchGet(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleFirebaseAppcheckV1BatchGetSafetyNetConfigsResponse]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.GoogleFirebaseAppcheckV1BatchGetSafetyNetConfigsResponse])
				}
				object batchGet {
					def apply(projectsId :PlayApi, parent: String, names: String)(using auth: AuthToken, ec: ExecutionContext): batchGet = new batchGet(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/apps/-/safetyNetConfig:batchGet")).addQueryStringParameters("parent" -> parent.toString, "names" -> names.toString))
					given Conversion[batchGet, Future[Schema.GoogleFirebaseAppcheckV1BatchGetSafetyNetConfigsResponse]] = (fun: batchGet) => fun.apply()
				}
				class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleFirebaseAppcheckV1SafetyNetConfig(body: Schema.GoogleFirebaseAppcheckV1SafetyNetConfig) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.GoogleFirebaseAppcheckV1SafetyNetConfig])
				}
				object patch {
					def apply(projectsId :PlayApi, appsId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/apps/${appsId}/safetyNetConfig")).addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleFirebaseAppcheckV1SafetyNetConfig]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.GoogleFirebaseAppcheckV1SafetyNetConfig])
				}
				object get {
					def apply(projectsId :PlayApi, appsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/apps/${appsId}/safetyNetConfig")).addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleFirebaseAppcheckV1SafetyNetConfig]] = (fun: get) => fun.apply()
				}
			}
			object playIntegrityConfig {
				class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleFirebaseAppcheckV1PlayIntegrityConfig(body: Schema.GoogleFirebaseAppcheckV1PlayIntegrityConfig) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.GoogleFirebaseAppcheckV1PlayIntegrityConfig])
				}
				object patch {
					def apply(projectsId :PlayApi, appsId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/apps/${appsId}/playIntegrityConfig")).addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleFirebaseAppcheckV1PlayIntegrityConfig]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.GoogleFirebaseAppcheckV1PlayIntegrityConfig])
				}
				object get {
					def apply(projectsId :PlayApi, appsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/apps/${appsId}/playIntegrityConfig")).addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleFirebaseAppcheckV1PlayIntegrityConfig]] = (fun: get) => fun.apply()
				}
				class batchGet(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleFirebaseAppcheckV1BatchGetPlayIntegrityConfigsResponse]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.GoogleFirebaseAppcheckV1BatchGetPlayIntegrityConfigsResponse])
				}
				object batchGet {
					def apply(projectsId :PlayApi, names: String, parent: String)(using auth: AuthToken, ec: ExecutionContext): batchGet = new batchGet(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/apps/-/playIntegrityConfig:batchGet")).addQueryStringParameters("names" -> names.toString, "parent" -> parent.toString))
					given Conversion[batchGet, Future[Schema.GoogleFirebaseAppcheckV1BatchGetPlayIntegrityConfigsResponse]] = (fun: batchGet) => fun.apply()
				}
			}
			object recaptchaEnterpriseConfig {
				class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleFirebaseAppcheckV1RecaptchaEnterpriseConfig(body: Schema.GoogleFirebaseAppcheckV1RecaptchaEnterpriseConfig) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.GoogleFirebaseAppcheckV1RecaptchaEnterpriseConfig])
				}
				object patch {
					def apply(projectsId :PlayApi, appsId :PlayApi, updateMask: String, name: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/apps/${appsId}/recaptchaEnterpriseConfig")).addQueryStringParameters("updateMask" -> updateMask.toString, "name" -> name.toString))
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleFirebaseAppcheckV1RecaptchaEnterpriseConfig]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.GoogleFirebaseAppcheckV1RecaptchaEnterpriseConfig])
				}
				object get {
					def apply(projectsId :PlayApi, appsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/apps/${appsId}/recaptchaEnterpriseConfig")).addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleFirebaseAppcheckV1RecaptchaEnterpriseConfig]] = (fun: get) => fun.apply()
				}
				class batchGet(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleFirebaseAppcheckV1BatchGetRecaptchaEnterpriseConfigsResponse]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.GoogleFirebaseAppcheckV1BatchGetRecaptchaEnterpriseConfigsResponse])
				}
				object batchGet {
					def apply(projectsId :PlayApi, parent: String, names: String)(using auth: AuthToken, ec: ExecutionContext): batchGet = new batchGet(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/apps/-/recaptchaEnterpriseConfig:batchGet")).addQueryStringParameters("parent" -> parent.toString, "names" -> names.toString))
					given Conversion[batchGet, Future[Schema.GoogleFirebaseAppcheckV1BatchGetRecaptchaEnterpriseConfigsResponse]] = (fun: batchGet) => fun.apply()
				}
			}
			object appAttestConfig {
				class batchGet(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleFirebaseAppcheckV1BatchGetAppAttestConfigsResponse]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.GoogleFirebaseAppcheckV1BatchGetAppAttestConfigsResponse])
				}
				object batchGet {
					def apply(projectsId :PlayApi, parent: String, names: String)(using auth: AuthToken, ec: ExecutionContext): batchGet = new batchGet(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/apps/-/appAttestConfig:batchGet")).addQueryStringParameters("parent" -> parent.toString, "names" -> names.toString))
					given Conversion[batchGet, Future[Schema.GoogleFirebaseAppcheckV1BatchGetAppAttestConfigsResponse]] = (fun: batchGet) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleFirebaseAppcheckV1AppAttestConfig]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.GoogleFirebaseAppcheckV1AppAttestConfig])
				}
				object get {
					def apply(projectsId :PlayApi, appsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/apps/${appsId}/appAttestConfig")).addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleFirebaseAppcheckV1AppAttestConfig]] = (fun: get) => fun.apply()
				}
				class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleFirebaseAppcheckV1AppAttestConfig(body: Schema.GoogleFirebaseAppcheckV1AppAttestConfig) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.GoogleFirebaseAppcheckV1AppAttestConfig])
				}
				object patch {
					def apply(projectsId :PlayApi, appsId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/apps/${appsId}/appAttestConfig")).addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
				}
			}
			object recaptchaV3Config {
				class batchGet(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleFirebaseAppcheckV1BatchGetRecaptchaV3ConfigsResponse]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.GoogleFirebaseAppcheckV1BatchGetRecaptchaV3ConfigsResponse])
				}
				object batchGet {
					def apply(projectsId :PlayApi, names: String, parent: String)(using auth: AuthToken, ec: ExecutionContext): batchGet = new batchGet(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/apps/-/recaptchaV3Config:batchGet")).addQueryStringParameters("names" -> names.toString, "parent" -> parent.toString))
					given Conversion[batchGet, Future[Schema.GoogleFirebaseAppcheckV1BatchGetRecaptchaV3ConfigsResponse]] = (fun: batchGet) => fun.apply()
				}
				class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleFirebaseAppcheckV1RecaptchaV3Config(body: Schema.GoogleFirebaseAppcheckV1RecaptchaV3Config) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.GoogleFirebaseAppcheckV1RecaptchaV3Config])
				}
				object patch {
					def apply(projectsId :PlayApi, appsId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/apps/${appsId}/recaptchaV3Config")).addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleFirebaseAppcheckV1RecaptchaV3Config]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.GoogleFirebaseAppcheckV1RecaptchaV3Config])
				}
				object get {
					def apply(projectsId :PlayApi, appsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/apps/${appsId}/recaptchaV3Config")).addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleFirebaseAppcheckV1RecaptchaV3Config]] = (fun: get) => fun.apply()
				}
			}
			object deviceCheckConfig {
				class batchGet(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleFirebaseAppcheckV1BatchGetDeviceCheckConfigsResponse]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.GoogleFirebaseAppcheckV1BatchGetDeviceCheckConfigsResponse])
				}
				object batchGet {
					def apply(projectsId :PlayApi, names: String, parent: String)(using auth: AuthToken, ec: ExecutionContext): batchGet = new batchGet(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/apps/-/deviceCheckConfig:batchGet")).addQueryStringParameters("names" -> names.toString, "parent" -> parent.toString))
					given Conversion[batchGet, Future[Schema.GoogleFirebaseAppcheckV1BatchGetDeviceCheckConfigsResponse]] = (fun: batchGet) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleFirebaseAppcheckV1DeviceCheckConfig]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.GoogleFirebaseAppcheckV1DeviceCheckConfig])
				}
				object get {
					def apply(projectsId :PlayApi, appsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/apps/${appsId}/deviceCheckConfig")).addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleFirebaseAppcheckV1DeviceCheckConfig]] = (fun: get) => fun.apply()
				}
				class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleFirebaseAppcheckV1DeviceCheckConfig(body: Schema.GoogleFirebaseAppcheckV1DeviceCheckConfig) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.GoogleFirebaseAppcheckV1DeviceCheckConfig])
				}
				object patch {
					def apply(projectsId :PlayApi, appsId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/apps/${appsId}/deviceCheckConfig")).addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
				}
			}
		}
		object services {
			class batchUpdate(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGoogleFirebaseAppcheckV1BatchUpdateServicesRequest(body: Schema.GoogleFirebaseAppcheckV1BatchUpdateServicesRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleFirebaseAppcheckV1BatchUpdateServicesResponse])
			}
			object batchUpdate {
				def apply(projectsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): batchUpdate = new batchUpdate(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/services:batchUpdate")).addQueryStringParameters("parent" -> parent.toString))
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleFirebaseAppcheckV1Service]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.GoogleFirebaseAppcheckV1Service])
			}
			object get {
				def apply(projectsId :PlayApi, servicesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/services/${servicesId}")).addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.GoogleFirebaseAppcheckV1Service]] = (fun: get) => fun.apply()
			}
			class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGoogleFirebaseAppcheckV1Service(body: Schema.GoogleFirebaseAppcheckV1Service) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.GoogleFirebaseAppcheckV1Service])
			}
			object patch {
				def apply(projectsId :PlayApi, servicesId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/services/${servicesId}")).addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleFirebaseAppcheckV1ListServicesResponse]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.GoogleFirebaseAppcheckV1ListServicesResponse])
			}
			object list {
				def apply(projectsId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/services")).addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.GoogleFirebaseAppcheckV1ListServicesResponse]] = (fun: list) => fun.apply()
			}
			object resourcePolicies {
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleFirebaseAppcheckV1ResourcePolicy(body: Schema.GoogleFirebaseAppcheckV1ResourcePolicy) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleFirebaseAppcheckV1ResourcePolicy])
				}
				object create {
					def apply(projectsId :PlayApi, servicesId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/services/${servicesId}/resourcePolicies")).addQueryStringParameters("parent" -> parent.toString))
				}
				class batchUpdate(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleFirebaseAppcheckV1BatchUpdateResourcePoliciesRequest(body: Schema.GoogleFirebaseAppcheckV1BatchUpdateResourcePoliciesRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleFirebaseAppcheckV1BatchUpdateResourcePoliciesResponse])
				}
				object batchUpdate {
					def apply(projectsId :PlayApi, servicesId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): batchUpdate = new batchUpdate(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/services/${servicesId}/resourcePolicies:batchUpdate")).addQueryStringParameters("parent" -> parent.toString))
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
					def apply() = req.execute("DELETE").map(_.json.as[Schema.GoogleProtobufEmpty])
				}
				object delete {
					def apply(projectsId :PlayApi, servicesId :PlayApi, resourcePoliciesId :PlayApi, name: String, etag: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/services/${servicesId}/resourcePolicies/${resourcePoliciesId}")).addQueryStringParameters("name" -> name.toString, "etag" -> etag.toString))
					given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleFirebaseAppcheckV1ResourcePolicy]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.GoogleFirebaseAppcheckV1ResourcePolicy])
				}
				object get {
					def apply(projectsId :PlayApi, servicesId :PlayApi, resourcePoliciesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/services/${servicesId}/resourcePolicies/${resourcePoliciesId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleFirebaseAppcheckV1ResourcePolicy]] = (fun: get) => fun.apply()
				}
				class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleFirebaseAppcheckV1ResourcePolicy(body: Schema.GoogleFirebaseAppcheckV1ResourcePolicy) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.GoogleFirebaseAppcheckV1ResourcePolicy])
				}
				object patch {
					def apply(projectsId :PlayApi, servicesId :PlayApi, resourcePoliciesId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/services/${servicesId}/resourcePolicies/${resourcePoliciesId}")).addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleFirebaseAppcheckV1ListResourcePoliciesResponse]) {
					/** Optional. Filters the results by the specified rule. For the exact syntax of this field, please consult the [AIP-160](https://google.aip.dev/160) standard. Currently, since the only fields in the ResourcePolicy resource are the scalar fields `enforcement_mode` and `target_resource`, this method does not support the traversal operator (`.`) or the has operator (`:`). Here are some examples of valid filters: &#42; `enforcement_mode = ENFORCED` &#42; `target_resource = "//oauth2.googleapis.com/projects/12345/oauthClients/"` &#42; `enforcement_mode = ENFORCED AND target_resource = "//oauth2.googleapis.com/projects/12345/oauthClients/"` */
					def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
					def apply() = req.execute("GET").map(_.json.as[Schema.GoogleFirebaseAppcheckV1ListResourcePoliciesResponse])
				}
				object list {
					def apply(projectsId :PlayApi, servicesId :PlayApi, parent: String, pageToken: String, pageSize: Int)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/services/${servicesId}/resourcePolicies")).addQueryStringParameters("parent" -> parent.toString, "pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString))
					given Conversion[list, Future[Schema.GoogleFirebaseAppcheckV1ListResourcePoliciesResponse]] = (fun: list) => fun.apply()
				}
			}
		}
	}
}
