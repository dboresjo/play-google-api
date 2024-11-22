package com.boresjo.play.api.google.developerconnect

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Formats {
	given fmtListOperationsResponse: Format[Schema.ListOperationsResponse] = Json.format[Schema.ListOperationsResponse]
	given fmtOperation: Format[Schema.Operation] = Json.format[Schema.Operation]
	given fmtStatus: Format[Schema.Status] = Json.format[Schema.Status]
	given fmtEmpty: Format[Schema.Empty] = Json.format[Schema.Empty]
	given fmtCancelOperationRequest: Format[Schema.CancelOperationRequest] = Json.format[Schema.CancelOperationRequest]
	given fmtListConnectionsResponse: Format[Schema.ListConnectionsResponse] = Json.format[Schema.ListConnectionsResponse]
	given fmtConnection: Format[Schema.Connection] = Json.format[Schema.Connection]
	given fmtGitHubConfig: Format[Schema.GitHubConfig] = Json.format[Schema.GitHubConfig]
	given fmtGitHubEnterpriseConfig: Format[Schema.GitHubEnterpriseConfig] = Json.format[Schema.GitHubEnterpriseConfig]
	given fmtGitLabConfig: Format[Schema.GitLabConfig] = Json.format[Schema.GitLabConfig]
	given fmtGitLabEnterpriseConfig: Format[Schema.GitLabEnterpriseConfig] = Json.format[Schema.GitLabEnterpriseConfig]
	given fmtInstallationState: Format[Schema.InstallationState] = Json.format[Schema.InstallationState]
	given fmtCryptoKeyConfig: Format[Schema.CryptoKeyConfig] = Json.format[Schema.CryptoKeyConfig]
	given fmtGitHubConfigGithubAppEnum: Format[Schema.GitHubConfig.GithubAppEnum] = JsonEnumFormat[Schema.GitHubConfig.GithubAppEnum]
	given fmtOAuthCredential: Format[Schema.OAuthCredential] = Json.format[Schema.OAuthCredential]
	given fmtServiceDirectoryConfig: Format[Schema.ServiceDirectoryConfig] = Json.format[Schema.ServiceDirectoryConfig]
	given fmtUserCredential: Format[Schema.UserCredential] = Json.format[Schema.UserCredential]
	given fmtInstallationStateStageEnum: Format[Schema.InstallationState.StageEnum] = JsonEnumFormat[Schema.InstallationState.StageEnum]
	given fmtGitRepositoryLink: Format[Schema.GitRepositoryLink] = Json.format[Schema.GitRepositoryLink]
	given fmtListGitRepositoryLinksResponse: Format[Schema.ListGitRepositoryLinksResponse] = Json.format[Schema.ListGitRepositoryLinksResponse]
	given fmtFetchReadWriteTokenRequest: Format[Schema.FetchReadWriteTokenRequest] = Json.format[Schema.FetchReadWriteTokenRequest]
	given fmtFetchReadWriteTokenResponse: Format[Schema.FetchReadWriteTokenResponse] = Json.format[Schema.FetchReadWriteTokenResponse]
	given fmtFetchReadTokenRequest: Format[Schema.FetchReadTokenRequest] = Json.format[Schema.FetchReadTokenRequest]
	given fmtFetchReadTokenResponse: Format[Schema.FetchReadTokenResponse] = Json.format[Schema.FetchReadTokenResponse]
	given fmtFetchLinkableGitRepositoriesResponse: Format[Schema.FetchLinkableGitRepositoriesResponse] = Json.format[Schema.FetchLinkableGitRepositoriesResponse]
	given fmtLinkableGitRepository: Format[Schema.LinkableGitRepository] = Json.format[Schema.LinkableGitRepository]
	given fmtFetchGitHubInstallationsResponse: Format[Schema.FetchGitHubInstallationsResponse] = Json.format[Schema.FetchGitHubInstallationsResponse]
	given fmtInstallation: Format[Schema.Installation] = Json.format[Schema.Installation]
	given fmtFetchGitRefsResponse: Format[Schema.FetchGitRefsResponse] = Json.format[Schema.FetchGitRefsResponse]
	given fmtProcessGitHubEnterpriseWebhookRequest: Format[Schema.ProcessGitHubEnterpriseWebhookRequest] = Json.format[Schema.ProcessGitHubEnterpriseWebhookRequest]
	given fmtHttpBody: Format[Schema.HttpBody] = Json.format[Schema.HttpBody]
	given fmtProcessGitLabEnterpriseWebhookRequest: Format[Schema.ProcessGitLabEnterpriseWebhookRequest] = Json.format[Schema.ProcessGitLabEnterpriseWebhookRequest]
	given fmtProcessGitLabWebhookRequest: Format[Schema.ProcessGitLabWebhookRequest] = Json.format[Schema.ProcessGitLabWebhookRequest]
	given fmtListLocationsResponse: Format[Schema.ListLocationsResponse] = Json.format[Schema.ListLocationsResponse]
	given fmtLocation: Format[Schema.Location] = Json.format[Schema.Location]
	given fmtOperationMetadata: Format[Schema.OperationMetadata] = Json.format[Schema.OperationMetadata]
}
