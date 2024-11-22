package com.boresjo.play.api.google.developerconnect

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Helpers {
	import Schema.*
	object AutoOption {
		given putListSchemaOperation: Conversion[List[Schema.Operation], Option[List[Schema.Operation]]] = (fun: List[Schema.Operation]) => Option(fun)
		given putString: Conversion[String, Option[String]] = (fun: String) => Option(fun)
		given putMapStringJsValue: Conversion[Map[String, JsValue], Option[Map[String, JsValue]]] = (fun: Map[String, JsValue]) => Option(fun)
		given putBoolean: Conversion[Boolean, Option[Boolean]] = (fun: Boolean) => Option(fun)
		given putSchemaStatus: Conversion[Schema.Status, Option[Schema.Status]] = (fun: Schema.Status) => Option(fun)
		given putInt: Conversion[Int, Option[Int]] = (fun: Int) => Option(fun)
		given putListMapStringJsValue: Conversion[List[Map[String, JsValue]], Option[List[Map[String, JsValue]]]] = (fun: List[Map[String, JsValue]]) => Option(fun)
		given putListSchemaConnection: Conversion[List[Schema.Connection], Option[List[Schema.Connection]]] = (fun: List[Schema.Connection]) => Option(fun)
		given putListString: Conversion[List[String], Option[List[String]]] = (fun: List[String]) => Option(fun)
		given putSchemaGitHubConfig: Conversion[Schema.GitHubConfig, Option[Schema.GitHubConfig]] = (fun: Schema.GitHubConfig) => Option(fun)
		given putSchemaGitHubEnterpriseConfig: Conversion[Schema.GitHubEnterpriseConfig, Option[Schema.GitHubEnterpriseConfig]] = (fun: Schema.GitHubEnterpriseConfig) => Option(fun)
		given putSchemaGitLabConfig: Conversion[Schema.GitLabConfig, Option[Schema.GitLabConfig]] = (fun: Schema.GitLabConfig) => Option(fun)
		given putSchemaGitLabEnterpriseConfig: Conversion[Schema.GitLabEnterpriseConfig, Option[Schema.GitLabEnterpriseConfig]] = (fun: Schema.GitLabEnterpriseConfig) => Option(fun)
		given putMapStringString: Conversion[Map[String, String], Option[Map[String, String]]] = (fun: Map[String, String]) => Option(fun)
		given putSchemaInstallationState: Conversion[Schema.InstallationState, Option[Schema.InstallationState]] = (fun: Schema.InstallationState) => Option(fun)
		given putSchemaCryptoKeyConfig: Conversion[Schema.CryptoKeyConfig, Option[Schema.CryptoKeyConfig]] = (fun: Schema.CryptoKeyConfig) => Option(fun)
		given putSchemaGitHubConfigGithubAppEnum: Conversion[Schema.GitHubConfig.GithubAppEnum, Option[Schema.GitHubConfig.GithubAppEnum]] = (fun: Schema.GitHubConfig.GithubAppEnum) => Option(fun)
		given putSchemaOAuthCredential: Conversion[Schema.OAuthCredential, Option[Schema.OAuthCredential]] = (fun: Schema.OAuthCredential) => Option(fun)
		given putSchemaServiceDirectoryConfig: Conversion[Schema.ServiceDirectoryConfig, Option[Schema.ServiceDirectoryConfig]] = (fun: Schema.ServiceDirectoryConfig) => Option(fun)
		given putSchemaUserCredential: Conversion[Schema.UserCredential, Option[Schema.UserCredential]] = (fun: Schema.UserCredential) => Option(fun)
		given putSchemaInstallationStateStageEnum: Conversion[Schema.InstallationState.StageEnum, Option[Schema.InstallationState.StageEnum]] = (fun: Schema.InstallationState.StageEnum) => Option(fun)
		given putListSchemaGitRepositoryLink: Conversion[List[Schema.GitRepositoryLink], Option[List[Schema.GitRepositoryLink]]] = (fun: List[Schema.GitRepositoryLink]) => Option(fun)
		given putListSchemaLinkableGitRepository: Conversion[List[Schema.LinkableGitRepository], Option[List[Schema.LinkableGitRepository]]] = (fun: List[Schema.LinkableGitRepository]) => Option(fun)
		given putListSchemaInstallation: Conversion[List[Schema.Installation], Option[List[Schema.Installation]]] = (fun: List[Schema.Installation]) => Option(fun)
		given putSchemaHttpBody: Conversion[Schema.HttpBody, Option[Schema.HttpBody]] = (fun: Schema.HttpBody) => Option(fun)
		given putListSchemaLocation: Conversion[List[Schema.Location], Option[List[Schema.Location]]] = (fun: List[Schema.Location]) => Option(fun)
	}
	object OptionDefault {
		given getString: Conversion[Option[String], String] = (fun: Option[String]) => fun.getOrElse("")
	}
}
