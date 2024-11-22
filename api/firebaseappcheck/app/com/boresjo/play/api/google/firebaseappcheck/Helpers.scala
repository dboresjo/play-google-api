package com.boresjo.play.api.google.firebaseappcheck

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Helpers {
	import Schema.*
	object AutoOption {
		given putListSchemaGoogleFirebaseAppcheckV1DeviceCheckConfig: Conversion[List[Schema.GoogleFirebaseAppcheckV1DeviceCheckConfig], Option[List[Schema.GoogleFirebaseAppcheckV1DeviceCheckConfig]]] = (fun: List[Schema.GoogleFirebaseAppcheckV1DeviceCheckConfig]) => Option(fun)
		given putString: Conversion[String, Option[String]] = (fun: String) => Option(fun)
		given putListSchemaGoogleFirebaseAppcheckV1DebugToken: Conversion[List[Schema.GoogleFirebaseAppcheckV1DebugToken], Option[List[Schema.GoogleFirebaseAppcheckV1DebugToken]]] = (fun: List[Schema.GoogleFirebaseAppcheckV1DebugToken]) => Option(fun)
		given putBoolean: Conversion[Boolean, Option[Boolean]] = (fun: Boolean) => Option(fun)
		given putListSchemaGoogleFirebaseAppcheckV1Service: Conversion[List[Schema.GoogleFirebaseAppcheckV1Service], Option[List[Schema.GoogleFirebaseAppcheckV1Service]]] = (fun: List[Schema.GoogleFirebaseAppcheckV1Service]) => Option(fun)
		given putListSchemaGoogleFirebaseAppcheckV1ResourcePolicy: Conversion[List[Schema.GoogleFirebaseAppcheckV1ResourcePolicy], Option[List[Schema.GoogleFirebaseAppcheckV1ResourcePolicy]]] = (fun: List[Schema.GoogleFirebaseAppcheckV1ResourcePolicy]) => Option(fun)
		given putSchemaGoogleFirebaseAppcheckV1ResourcePolicyEnforcementModeEnum: Conversion[Schema.GoogleFirebaseAppcheckV1ResourcePolicy.EnforcementModeEnum, Option[Schema.GoogleFirebaseAppcheckV1ResourcePolicy.EnforcementModeEnum]] = (fun: Schema.GoogleFirebaseAppcheckV1ResourcePolicy.EnforcementModeEnum) => Option(fun)
		given putListSchemaGoogleFirebaseAppcheckV1RecaptchaV3Config: Conversion[List[Schema.GoogleFirebaseAppcheckV1RecaptchaV3Config], Option[List[Schema.GoogleFirebaseAppcheckV1RecaptchaV3Config]]] = (fun: List[Schema.GoogleFirebaseAppcheckV1RecaptchaV3Config]) => Option(fun)
		given putListSchemaGoogleFirebaseAppcheckV1UpdateServiceRequest: Conversion[List[Schema.GoogleFirebaseAppcheckV1UpdateServiceRequest], Option[List[Schema.GoogleFirebaseAppcheckV1UpdateServiceRequest]]] = (fun: List[Schema.GoogleFirebaseAppcheckV1UpdateServiceRequest]) => Option(fun)
		given putSchemaGoogleFirebaseAppcheckV1AppCheckToken: Conversion[Schema.GoogleFirebaseAppcheckV1AppCheckToken, Option[Schema.GoogleFirebaseAppcheckV1AppCheckToken]] = (fun: Schema.GoogleFirebaseAppcheckV1AppCheckToken) => Option(fun)
		given putListSchemaGoogleFirebaseAppcheckV1PlayIntegrityConfig: Conversion[List[Schema.GoogleFirebaseAppcheckV1PlayIntegrityConfig], Option[List[Schema.GoogleFirebaseAppcheckV1PlayIntegrityConfig]]] = (fun: List[Schema.GoogleFirebaseAppcheckV1PlayIntegrityConfig]) => Option(fun)
		given putListSchemaGoogleFirebaseAppcheckV1RecaptchaEnterpriseConfig: Conversion[List[Schema.GoogleFirebaseAppcheckV1RecaptchaEnterpriseConfig], Option[List[Schema.GoogleFirebaseAppcheckV1RecaptchaEnterpriseConfig]]] = (fun: List[Schema.GoogleFirebaseAppcheckV1RecaptchaEnterpriseConfig]) => Option(fun)
		given putListSchemaGoogleFirebaseAppcheckV1PublicJwk: Conversion[List[Schema.GoogleFirebaseAppcheckV1PublicJwk], Option[List[Schema.GoogleFirebaseAppcheckV1PublicJwk]]] = (fun: List[Schema.GoogleFirebaseAppcheckV1PublicJwk]) => Option(fun)
		given putSchemaGoogleFirebaseAppcheckV1ServiceEnforcementModeEnum: Conversion[Schema.GoogleFirebaseAppcheckV1Service.EnforcementModeEnum, Option[Schema.GoogleFirebaseAppcheckV1Service.EnforcementModeEnum]] = (fun: Schema.GoogleFirebaseAppcheckV1Service.EnforcementModeEnum) => Option(fun)
		given putListSchemaGoogleFirebaseAppcheckV1AppAttestConfig: Conversion[List[Schema.GoogleFirebaseAppcheckV1AppAttestConfig], Option[List[Schema.GoogleFirebaseAppcheckV1AppAttestConfig]]] = (fun: List[Schema.GoogleFirebaseAppcheckV1AppAttestConfig]) => Option(fun)
		given putSchemaGoogleFirebaseAppcheckV1ResourcePolicy: Conversion[Schema.GoogleFirebaseAppcheckV1ResourcePolicy, Option[Schema.GoogleFirebaseAppcheckV1ResourcePolicy]] = (fun: Schema.GoogleFirebaseAppcheckV1ResourcePolicy) => Option(fun)
		given putListSchemaGoogleFirebaseAppcheckV1SafetyNetConfig: Conversion[List[Schema.GoogleFirebaseAppcheckV1SafetyNetConfig], Option[List[Schema.GoogleFirebaseAppcheckV1SafetyNetConfig]]] = (fun: List[Schema.GoogleFirebaseAppcheckV1SafetyNetConfig]) => Option(fun)
		given putListSchemaGoogleFirebaseAppcheckV1UpdateResourcePolicyRequest: Conversion[List[Schema.GoogleFirebaseAppcheckV1UpdateResourcePolicyRequest], Option[List[Schema.GoogleFirebaseAppcheckV1UpdateResourcePolicyRequest]]] = (fun: List[Schema.GoogleFirebaseAppcheckV1UpdateResourcePolicyRequest]) => Option(fun)
		given putSchemaGoogleFirebaseAppcheckV1Service: Conversion[Schema.GoogleFirebaseAppcheckV1Service, Option[Schema.GoogleFirebaseAppcheckV1Service]] = (fun: Schema.GoogleFirebaseAppcheckV1Service) => Option(fun)
	}
	object OptionDefault {
		given getString: Conversion[Option[String], String] = (fun: Option[String]) => fun.getOrElse("")
	}
}
