package com.boresjo.play.api.google.cloudprofiler

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Helpers {
	import Schema.*
	object AutoOption {
		given putSchemaDeployment: Conversion[Schema.Deployment, Option[Schema.Deployment]] = (fun: Schema.Deployment) => Option(fun)
		given putListSchemaCreateProfileRequestProfileTypeEnum: Conversion[List[Schema.CreateProfileRequest.ProfileTypeEnum], Option[List[Schema.CreateProfileRequest.ProfileTypeEnum]]] = (fun: List[Schema.CreateProfileRequest.ProfileTypeEnum]) => Option(fun)
		given putString: Conversion[String, Option[String]] = (fun: String) => Option(fun)
		given putMapStringString: Conversion[Map[String, String], Option[Map[String, String]]] = (fun: Map[String, String]) => Option(fun)
		given putSchemaProfileProfileTypeEnum: Conversion[Schema.Profile.ProfileTypeEnum, Option[Schema.Profile.ProfileTypeEnum]] = (fun: Schema.Profile.ProfileTypeEnum) => Option(fun)
		given putListSchemaProfile: Conversion[List[Schema.Profile], Option[List[Schema.Profile]]] = (fun: List[Schema.Profile]) => Option(fun)
		given putInt: Conversion[Int, Option[Int]] = (fun: Int) => Option(fun)
	}
	object OptionDefault {
		given getString: Conversion[Option[String], String] = (fun: Option[String]) => fun.getOrElse("")
	}
}
