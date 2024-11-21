package com.boresjo.play.api.google.dataform

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	case class ListRepositoriesResponse(
	  /** List of repositories. */
		repositories: Option[List[Schema.Repository]] = None,
	  /** A token which can be sent as `page_token` to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None,
	  /** Locations which could not be reached. */
		unreachable: Option[List[String]] = None
	)
	
	case class Repository(
	  /** Identifier. The repository's name. */
		name: Option[String] = None,
	  /** Output only. The timestamp of when the repository was created. */
		createTime: Option[String] = None,
	  /** Optional. The repository's user-friendly name. */
		displayName: Option[String] = None,
	  /** Optional. If set, configures this repository to be linked to a Git remote. */
		gitRemoteSettings: Option[Schema.GitRemoteSettings] = None,
	  /** Optional. The name of the Secret Manager secret version to be used to interpolate variables into the .npmrc file for package installation operations. Must be in the format `projects/&#42;/secrets/&#42;/versions/&#42;`. The file itself must be in a JSON format. */
		npmrcEnvironmentVariablesSecretVersion: Option[String] = None,
	  /** Optional. If set, fields of `workspace_compilation_overrides` override the default compilation settings that are specified in dataform.json when creating workspace-scoped compilation results. See documentation for `WorkspaceCompilationOverrides` for more information. */
		workspaceCompilationOverrides: Option[Schema.WorkspaceCompilationOverrides] = None,
	  /** Optional. Repository user labels. */
		labels: Option[Map[String, String]] = None,
	  /** Optional. Input only. If set to true, the authenticated user will be granted the roles/dataform.admin role on the created repository. To modify access to the created repository later apply setIamPolicy from https://cloud.google.com/dataform/reference/rest#rest-resource:-v1beta1.projects.locations.repositories */
		setAuthenticatedUserAdmin: Option[Boolean] = None,
	  /** Optional. The service account to run workflow invocations under. */
		serviceAccount: Option[String] = None,
	  /** Optional. The reference to a KMS encryption key. If provided, it will be used to encrypt user data in the repository and all child resources. It is not possible to add or update the encryption key after the repository is created. Example: `projects/{kms_project}/locations/{location}/keyRings/{key_location}/cryptoKeys/{key}` */
		kmsKeyName: Option[String] = None,
	  /** Output only. A data encryption state of a Git repository if this Repository is protected by a KMS key. */
		dataEncryptionState: Option[Schema.DataEncryptionState] = None
	)
	
	object GitRemoteSettings {
		enum TokenStatusEnum extends Enum[TokenStatusEnum] { case TOKEN_STATUS_UNSPECIFIED, NOT_FOUND, INVALID, VALID }
	}
	case class GitRemoteSettings(
	  /** Required. The Git remote's URL. */
		url: Option[String] = None,
	  /** Required. The Git remote's default branch name. */
		defaultBranch: Option[String] = None,
	  /** Optional. The name of the Secret Manager secret version to use as an authentication token for Git operations. Must be in the format `projects/&#42;/secrets/&#42;/versions/&#42;`. */
		authenticationTokenSecretVersion: Option[String] = None,
	  /** Optional. Authentication fields for remote uris using SSH protocol. */
		sshAuthenticationConfig: Option[Schema.SshAuthenticationConfig] = None,
	  /** Output only. Deprecated: The field does not contain any token status information. Instead use https://cloud.google.com/dataform/reference/rest/v1beta1/projects.locations.repositories/computeAccessTokenStatus */
		tokenStatus: Option[Schema.GitRemoteSettings.TokenStatusEnum] = None
	)
	
	case class SshAuthenticationConfig(
	  /** Required. The name of the Secret Manager secret version to use as a ssh private key for Git operations. Must be in the format `projects/&#42;/secrets/&#42;/versions/&#42;`. */
		userPrivateKeySecretVersion: Option[String] = None,
	  /** Required. Content of a public SSH key to verify an identity of a remote Git host. */
		hostPublicKey: Option[String] = None
	)
	
	case class WorkspaceCompilationOverrides(
	  /** Optional. The default database (Google Cloud project ID). */
		defaultDatabase: Option[String] = None,
	  /** Optional. The suffix that should be appended to all schema (BigQuery dataset ID) names. */
		schemaSuffix: Option[String] = None,
	  /** Optional. The prefix that should be prepended to all table names. */
		tablePrefix: Option[String] = None
	)
	
	case class DataEncryptionState(
	  /** The KMS key version name with which data of a resource is encrypted. */
		kmsKeyVersionName: Option[String] = None
	)
	
	case class Empty(
	
	)
	
	case class CommitRepositoryChangesRequest(
	  /** Required. The changes to commit to the repository. */
		commitMetadata: Option[Schema.CommitMetadata] = None,
	  /** Optional. The commit SHA which must be the repository's current HEAD before applying this commit; otherwise this request will fail. If unset, no validation on the current HEAD commit SHA is performed. */
		requiredHeadCommitSha: Option[String] = None,
	  /** A map to the path of the file to the operation. The path is the full file path including filename, from repository root. */
		fileOperations: Option[Map[String, Schema.FileOperation]] = None
	)
	
	case class CommitMetadata(
	  /** Required. The commit's author. */
		author: Option[Schema.CommitAuthor] = None,
	  /** Optional. The commit's message. */
		commitMessage: Option[String] = None
	)
	
	case class CommitAuthor(
	  /** Required. The commit author's name. */
		name: Option[String] = None,
	  /** Required. The commit author's email address. */
		emailAddress: Option[String] = None
	)
	
	case class FileOperation(
	  /** Represents the write operation. */
		writeFile: Option[Schema.WriteFile] = None,
	  /** Represents the delete operation. */
		deleteFile: Option[Schema.DeleteFile] = None
	)
	
	case class WriteFile(
	  /** The file's contents. */
		contents: Option[String] = None
	)
	
	case class DeleteFile(
	
	)
	
	case class CommitRepositoryChangesResponse(
	  /** The commit SHA of the current commit. */
		commitSha: Option[String] = None
	)
	
	case class ReadRepositoryFileResponse(
	  /** The file's contents. */
		contents: Option[String] = None
	)
	
	case class QueryRepositoryDirectoryContentsResponse(
	  /** List of entries in the directory. */
		directoryEntries: Option[List[Schema.DirectoryEntry]] = None,
	  /** A token, which can be sent as `page_token` to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None
	)
	
	case class DirectoryEntry(
	  /** A file in the directory. */
		file: Option[String] = None,
	  /** A child directory in the directory. */
		directory: Option[String] = None
	)
	
	case class FetchRepositoryHistoryResponse(
	  /** A list of commit logs, ordered by 'git log' default order. */
		commits: Option[List[Schema.CommitLogEntry]] = None,
	  /** A token, which can be sent as `page_token` to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None
	)
	
	case class CommitLogEntry(
	  /** Commit timestamp. */
		commitTime: Option[String] = None,
	  /** The commit SHA for this commit log entry. */
		commitSha: Option[String] = None,
	  /** The commit author for this commit log entry. */
		author: Option[Schema.CommitAuthor] = None,
	  /** The commit message for this commit log entry. */
		commitMessage: Option[String] = None
	)
	
	object ComputeRepositoryAccessTokenStatusResponse {
		enum TokenStatusEnum extends Enum[TokenStatusEnum] { case TOKEN_STATUS_UNSPECIFIED, NOT_FOUND, INVALID, VALID }
	}
	case class ComputeRepositoryAccessTokenStatusResponse(
	  /** Indicates the status of the Git access token. */
		tokenStatus: Option[Schema.ComputeRepositoryAccessTokenStatusResponse.TokenStatusEnum] = None
	)
	
	case class FetchRemoteBranchesResponse(
	  /** The remote repository's branch names. */
		branches: Option[List[String]] = None
	)
	
	case class ListWorkspacesResponse(
	  /** List of workspaces. */
		workspaces: Option[List[Schema.Workspace]] = None,
	  /** A token, which can be sent as `page_token` to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None,
	  /** Locations which could not be reached. */
		unreachable: Option[List[String]] = None
	)
	
	case class Workspace(
	  /** Identifier. The workspace's name. */
		name: Option[String] = None,
	  /** Output only. The timestamp of when the workspace was created. */
		createTime: Option[String] = None,
	  /** Output only. A data encryption state of a Git repository if this Workspace is protected by a KMS key. */
		dataEncryptionState: Option[Schema.DataEncryptionState] = None
	)
	
	case class InstallNpmPackagesRequest(
	
	)
	
	case class InstallNpmPackagesResponse(
	
	)
	
	case class PullGitCommitsRequest(
	  /** Optional. The name of the branch in the Git remote from which to pull commits. If left unset, the repository's default branch name will be used. */
		remoteBranch: Option[String] = None,
	  /** Required. The author of any merge commit which may be created as a result of merging fetched Git commits into this workspace. */
		author: Option[Schema.CommitAuthor] = None
	)
	
	case class PushGitCommitsRequest(
	  /** Optional. The name of the branch in the Git remote to which commits should be pushed. If left unset, the repository's default branch name will be used. */
		remoteBranch: Option[String] = None
	)
	
	case class FetchFileGitStatusesResponse(
	  /** A list of all files which have uncommitted Git changes. There will only be a single entry for any given file. */
		uncommittedFileChanges: Option[List[Schema.UncommittedFileChange]] = None
	)
	
	object UncommittedFileChange {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, ADDED, DELETED, MODIFIED, HAS_CONFLICTS }
	}
	case class UncommittedFileChange(
	  /** The file's full path including filename, relative to the workspace root. */
		path: Option[String] = None,
	  /** Indicates the status of the file. */
		state: Option[Schema.UncommittedFileChange.StateEnum] = None
	)
	
	case class FetchGitAheadBehindResponse(
	  /** The number of commits in the remote branch that are not in the workspace. */
		commitsAhead: Option[Int] = None,
	  /** The number of commits in the workspace that are not in the remote branch. */
		commitsBehind: Option[Int] = None
	)
	
	case class CommitWorkspaceChangesRequest(
	  /** Required. The commit's author. */
		author: Option[Schema.CommitAuthor] = None,
	  /** Optional. The commit's message. */
		commitMessage: Option[String] = None,
	  /** Optional. Full file paths to commit including filename, rooted at workspace root. If left empty, all files will be committed. */
		paths: Option[List[String]] = None
	)
	
	case class ResetWorkspaceChangesRequest(
	  /** Optional. Full file paths to reset back to their committed state including filename, rooted at workspace root. If left empty, all files will be reset. */
		paths: Option[List[String]] = None,
	  /** Optional. If set to true, untracked files will be deleted. */
		clean: Option[Boolean] = None
	)
	
	case class FetchFileDiffResponse(
	  /** The raw formatted Git diff for the file. */
		formattedDiff: Option[String] = None
	)
	
	case class QueryDirectoryContentsResponse(
	  /** List of entries in the directory. */
		directoryEntries: Option[List[Schema.DirectoryEntry]] = None,
	  /** A token, which can be sent as `page_token` to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None
	)
	
	case class SearchFilesResponse(
	  /** List of matched results. */
		searchResults: Option[List[Schema.SearchResult]] = None,
	  /** Optional. A token, which can be sent as `page_token` to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None
	)
	
	case class SearchResult(
	  /** Details when search result is a file. */
		file: Option[Schema.FileSearchResult] = None,
	  /** Details when search result is a directory. */
		directory: Option[Schema.DirectorySearchResult] = None
	)
	
	case class FileSearchResult(
	  /** File system path relative to the workspace root. */
		path: Option[String] = None
	)
	
	case class DirectorySearchResult(
	  /** File system path relative to the workspace root. */
		path: Option[String] = None
	)
	
	case class MakeDirectoryRequest(
	  /** Required. The directory's full path including directory name, relative to the workspace root. */
		path: Option[String] = None
	)
	
	case class MakeDirectoryResponse(
	
	)
	
	case class RemoveDirectoryRequest(
	  /** Required. The directory's full path including directory name, relative to the workspace root. */
		path: Option[String] = None
	)
	
	case class MoveDirectoryRequest(
	  /** Required. The directory's full path including directory name, relative to the workspace root. */
		path: Option[String] = None,
	  /** Required. The new path for the directory including directory name, rooted at workspace root. */
		newPath: Option[String] = None
	)
	
	case class MoveDirectoryResponse(
	
	)
	
	case class ReadFileResponse(
	  /** The file's contents. */
		fileContents: Option[String] = None
	)
	
	case class RemoveFileRequest(
	  /** Required. The file's full path including filename, relative to the workspace root. */
		path: Option[String] = None
	)
	
	case class MoveFileRequest(
	  /** Required. The file's full path including filename, relative to the workspace root. */
		path: Option[String] = None,
	  /** Required. The file's new path including filename, relative to the workspace root. */
		newPath: Option[String] = None
	)
	
	case class MoveFileResponse(
	
	)
	
	case class WriteFileRequest(
	  /** Required. The file. */
		path: Option[String] = None,
	  /** Required. The file's contents. */
		contents: Option[String] = None
	)
	
	case class WriteFileResponse(
	
	)
	
	case class ListReleaseConfigsResponse(
	  /** List of release configs. */
		releaseConfigs: Option[List[Schema.ReleaseConfig]] = None,
	  /** A token, which can be sent as `page_token` to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None,
	  /** Locations which could not be reached. */
		unreachable: Option[List[String]] = None
	)
	
	case class ReleaseConfig(
	  /** Identifier. The release config's name. */
		name: Option[String] = None,
	  /** Required. Git commit/tag/branch name at which the repository should be compiled. Must exist in the remote repository. Examples: - a commit SHA: `12ade345` - a tag: `tag1` - a branch name: `branch1` */
		gitCommitish: Option[String] = None,
	  /** Optional. If set, fields of `code_compilation_config` override the default compilation settings that are specified in dataform.json. */
		codeCompilationConfig: Option[Schema.CodeCompilationConfig] = None,
	  /** Optional. Optional schedule (in cron format) for automatic creation of compilation results. */
		cronSchedule: Option[String] = None,
	  /** Optional. Specifies the time zone to be used when interpreting cron_schedule. Must be a time zone name from the time zone database (https://en.wikipedia.org/wiki/List_of_tz_database_time_zones). If left unspecified, the default is UTC. */
		timeZone: Option[String] = None,
	  /** Output only. Records of the 10 most recent scheduled release attempts, ordered in descending order of `release_time`. Updated whenever automatic creation of a compilation result is triggered by cron_schedule. */
		recentScheduledReleaseRecords: Option[List[Schema.ScheduledReleaseRecord]] = None,
	  /** Optional. The name of the currently released compilation result for this release config. This value is updated when a compilation result is automatically created from this release config (using cron_schedule), or when this resource is updated by API call (perhaps to roll back to an earlier release). The compilation result must have been created using this release config. Must be in the format `projects/&#42;/locations/&#42;/repositories/&#42;/compilationResults/&#42;`. */
		releaseCompilationResult: Option[String] = None,
	  /** Optional. Disables automatic creation of compilation results. */
		disabled: Option[Boolean] = None
	)
	
	case class CodeCompilationConfig(
	  /** Optional. The default database (Google Cloud project ID). */
		defaultDatabase: Option[String] = None,
	  /** Optional. The default schema (BigQuery dataset ID). */
		defaultSchema: Option[String] = None,
	  /** Optional. The default BigQuery location to use. Defaults to "US". See the BigQuery docs for a full list of locations: https://cloud.google.com/bigquery/docs/locations. */
		defaultLocation: Option[String] = None,
	  /** Optional. The default schema (BigQuery dataset ID) for assertions. */
		assertionSchema: Option[String] = None,
	  /** Optional. User-defined variables that are made available to project code during compilation. */
		vars: Option[Map[String, String]] = None,
	  /** Optional. The suffix that should be appended to all database (Google Cloud project ID) names. */
		databaseSuffix: Option[String] = None,
	  /** Optional. The suffix that should be appended to all schema (BigQuery dataset ID) names. */
		schemaSuffix: Option[String] = None,
	  /** Optional. The prefix that should be prepended to all table names. */
		tablePrefix: Option[String] = None,
		defaultNotebookRuntimeOptions: Option[Schema.NotebookRuntimeOptions] = None
	)
	
	case class NotebookRuntimeOptions(
	  /** Optional. The GCS location to upload the result to. Format: `gs://bucket-name`. */
		gcsOutputBucket: Option[String] = None
	)
	
	case class ScheduledReleaseRecord(
	  /** The name of the created compilation result, if one was successfully created. Must be in the format `projects/&#42;/locations/&#42;/repositories/&#42;/compilationResults/&#42;`. */
		compilationResult: Option[String] = None,
	  /** The error status encountered upon this attempt to create the compilation result, if the attempt was unsuccessful. */
		errorStatus: Option[Schema.Status] = None,
	  /** The timestamp of this release attempt. */
		releaseTime: Option[String] = None
	)
	
	case class Status(
	  /** The status code, which should be an enum value of google.rpc.Code. */
		code: Option[Int] = None,
	  /** A developer-facing error message, which should be in English. Any user-facing error message should be localized and sent in the google.rpc.Status.details field, or localized by the client. */
		message: Option[String] = None,
	  /** A list of messages that carry the error details. There is a common set of message types for APIs to use. */
		details: Option[List[Map[String, JsValue]]] = None
	)
	
	case class ListCompilationResultsResponse(
	  /** List of compilation results. */
		compilationResults: Option[List[Schema.CompilationResult]] = None,
	  /** A token, which can be sent as `page_token` to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None,
	  /** Locations which could not be reached. */
		unreachable: Option[List[String]] = None
	)
	
	case class CompilationResult(
	  /** Immutable. Git commit/tag/branch name at which the repository should be compiled. Must exist in the remote repository. Examples: - a commit SHA: `12ade345` - a tag: `tag1` - a branch name: `branch1` */
		gitCommitish: Option[String] = None,
	  /** Immutable. The name of the workspace to compile. Must be in the format `projects/&#42;/locations/&#42;/repositories/&#42;/workspaces/&#42;`. */
		workspace: Option[String] = None,
	  /** Immutable. The name of the release config to compile. Must be in the format `projects/&#42;/locations/&#42;/repositories/&#42;/releaseConfigs/&#42;`. */
		releaseConfig: Option[String] = None,
	  /** Output only. The compilation result's name. */
		name: Option[String] = None,
	  /** Immutable. If set, fields of `code_compilation_config` override the default compilation settings that are specified in dataform.json. */
		codeCompilationConfig: Option[Schema.CodeCompilationConfig] = None,
	  /** Output only. The fully resolved Git commit SHA of the code that was compiled. Not set for compilation results whose source is a workspace. */
		resolvedGitCommitSha: Option[String] = None,
	  /** Output only. The version of `@dataform/core` that was used for compilation. */
		dataformCoreVersion: Option[String] = None,
	  /** Output only. Errors encountered during project compilation. */
		compilationErrors: Option[List[Schema.CompilationError]] = None,
	  /** Output only. Only set if the repository has a KMS Key. */
		dataEncryptionState: Option[Schema.DataEncryptionState] = None,
	  /** Output only. The timestamp of when the compilation result was created. */
		createTime: Option[String] = None
	)
	
	case class CompilationError(
	  /** Output only. The error's top level message. */
		message: Option[String] = None,
	  /** Output only. The error's full stack trace. */
		stack: Option[String] = None,
	  /** Output only. The path of the file where this error occurred, if available, relative to the project root. */
		path: Option[String] = None,
	  /** Output only. The identifier of the action where this error occurred, if available. */
		actionTarget: Option[Schema.Target] = None
	)
	
	case class Target(
	  /** The action's database (Google Cloud project ID) . */
		database: Option[String] = None,
	  /** The action's schema (BigQuery dataset ID), within `database`. */
		schema: Option[String] = None,
	  /** The action's name, within `database` and `schema`. */
		name: Option[String] = None
	)
	
	case class QueryCompilationResultActionsResponse(
	  /** List of compilation result actions. */
		compilationResultActions: Option[List[Schema.CompilationResultAction]] = None,
	  /** A token, which can be sent as `page_token` to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None
	)
	
	case class CompilationResultAction(
	  /** The database relation created/updated by this action. */
		relation: Option[Schema.Relation] = None,
	  /** The database operations executed by this action. */
		operations: Option[Schema.Operations] = None,
	  /** The assertion executed by this action. */
		assertion: Option[Schema.Assertion] = None,
	  /** The declaration declared by this action. */
		declaration: Option[Schema.Declaration] = None,
	  /** The notebook executed by this action. */
		notebook: Option[Schema.Notebook] = None,
	  /** This action's identifier. Unique within the compilation result. */
		target: Option[Schema.Target] = None,
	  /** The action's identifier if the project had been compiled without any overrides configured. Unique within the compilation result. */
		canonicalTarget: Option[Schema.Target] = None,
	  /** The full path including filename in which this action is located, relative to the workspace root. */
		filePath: Option[String] = None
	)
	
	object Relation {
		enum RelationTypeEnum extends Enum[RelationTypeEnum] { case RELATION_TYPE_UNSPECIFIED, TABLE, VIEW, INCREMENTAL_TABLE, MATERIALIZED_VIEW }
	}
	case class Relation(
	  /** A list of actions that this action depends on. */
		dependencyTargets: Option[List[Schema.Target]] = None,
	  /** Whether this action is disabled (i.e. should not be run). */
		disabled: Option[Boolean] = None,
	  /** Arbitrary, user-defined tags on this action. */
		tags: Option[List[String]] = None,
	  /** Descriptor for the relation and its columns. */
		relationDescriptor: Option[Schema.RelationDescriptor] = None,
	  /** The type of this relation. */
		relationType: Option[Schema.Relation.RelationTypeEnum] = None,
	  /** The SELECT query which returns rows which this relation should contain. */
		selectQuery: Option[String] = None,
	  /** SQL statements to be executed before creating the relation. */
		preOperations: Option[List[String]] = None,
	  /** SQL statements to be executed after creating the relation. */
		postOperations: Option[List[String]] = None,
	  /** Configures `INCREMENTAL_TABLE` settings for this relation. Only set if `relation_type` is `INCREMENTAL_TABLE`. */
		incrementalTableConfig: Option[Schema.IncrementalTableConfig] = None,
	  /** The SQL expression used to partition the relation. */
		partitionExpression: Option[String] = None,
	  /** A list of columns or SQL expressions used to cluster the table. */
		clusterExpressions: Option[List[String]] = None,
	  /** Sets the partition expiration in days. */
		partitionExpirationDays: Option[Int] = None,
	  /** Specifies whether queries on this table must include a predicate filter that filters on the partitioning column. */
		requirePartitionFilter: Option[Boolean] = None,
	  /** Additional options that will be provided as key/value pairs into the options clause of a create table/view statement. See https://cloud.google.com/bigquery/docs/reference/standard-sql/data-definition-language for more information on which options are supported. */
		additionalOptions: Option[Map[String, String]] = None
	)
	
	case class RelationDescriptor(
	  /** A text description of the relation. */
		description: Option[String] = None,
	  /** A list of descriptions of columns within the relation. */
		columns: Option[List[Schema.ColumnDescriptor]] = None,
	  /** A set of BigQuery labels that should be applied to the relation. */
		bigqueryLabels: Option[Map[String, String]] = None
	)
	
	case class ColumnDescriptor(
	  /** The identifier for the column. Each entry in `path` represents one level of nesting. */
		path: Option[List[String]] = None,
	  /** A textual description of the column. */
		description: Option[String] = None,
	  /** A list of BigQuery policy tags that will be applied to the column. */
		bigqueryPolicyTags: Option[List[String]] = None
	)
	
	case class IncrementalTableConfig(
	  /** The SELECT query which returns rows which should be inserted into the relation if it already exists and is not being refreshed. */
		incrementalSelectQuery: Option[String] = None,
	  /** Whether this table should be protected from being refreshed. */
		refreshDisabled: Option[Boolean] = None,
	  /** A set of columns or SQL expressions used to define row uniqueness. If any duplicates are discovered (as defined by `unique_key_parts`), only the newly selected rows (as defined by `incremental_select_query`) will be included in the relation. */
		uniqueKeyParts: Option[List[String]] = None,
	  /** A SQL expression conditional used to limit the set of existing rows considered for a merge operation (see `unique_key_parts` for more information). */
		updatePartitionFilter: Option[String] = None,
	  /** SQL statements to be executed before inserting new rows into the relation. */
		incrementalPreOperations: Option[List[String]] = None,
	  /** SQL statements to be executed after inserting new rows into the relation. */
		incrementalPostOperations: Option[List[String]] = None
	)
	
	case class Operations(
	  /** A list of actions that this action depends on. */
		dependencyTargets: Option[List[Schema.Target]] = None,
	  /** Whether this action is disabled (i.e. should not be run). */
		disabled: Option[Boolean] = None,
	  /** Arbitrary, user-defined tags on this action. */
		tags: Option[List[String]] = None,
	  /** Descriptor for any output relation and its columns. Only set if `has_output` is true. */
		relationDescriptor: Option[Schema.RelationDescriptor] = None,
	  /** A list of arbitrary SQL statements that will be executed without alteration. */
		queries: Option[List[String]] = None,
	  /** Whether these operations produce an output relation. */
		hasOutput: Option[Boolean] = None
	)
	
	case class Assertion(
	  /** A list of actions that this action depends on. */
		dependencyTargets: Option[List[Schema.Target]] = None,
	  /** The parent action of this assertion. Only set if this assertion was automatically generated. */
		parentAction: Option[Schema.Target] = None,
	  /** Whether this action is disabled (i.e. should not be run). */
		disabled: Option[Boolean] = None,
	  /** Arbitrary, user-defined tags on this action. */
		tags: Option[List[String]] = None,
	  /** The SELECT query which must return zero rows in order for this assertion to succeed. */
		selectQuery: Option[String] = None,
	  /** Descriptor for the assertion's automatically-generated view and its columns. */
		relationDescriptor: Option[Schema.RelationDescriptor] = None
	)
	
	case class Declaration(
	  /** Descriptor for the relation and its columns. Used as documentation only, i.e. values here will result in no changes to the relation's metadata. */
		relationDescriptor: Option[Schema.RelationDescriptor] = None
	)
	
	case class Notebook(
	  /** A list of actions that this action depends on. */
		dependencyTargets: Option[List[Schema.Target]] = None,
	  /** Whether this action is disabled (i.e. should not be run). */
		disabled: Option[Boolean] = None,
	  /** The contents of the notebook. */
		contents: Option[String] = None,
	  /** Arbitrary, user-defined tags on this action. */
		tags: Option[List[String]] = None
	)
	
	case class ListWorkflowConfigsResponse(
	  /** List of workflow configs. */
		workflowConfigs: Option[List[Schema.WorkflowConfig]] = None,
	  /** A token, which can be sent as `page_token` to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None,
	  /** Locations which could not be reached. */
		unreachable: Option[List[String]] = None
	)
	
	case class WorkflowConfig(
	  /** Identifier. The workflow config's name. */
		name: Option[String] = None,
	  /** Required. The name of the release config whose release_compilation_result should be executed. Must be in the format `projects/&#42;/locations/&#42;/repositories/&#42;/releaseConfigs/&#42;`. */
		releaseConfig: Option[String] = None,
	  /** Optional. If left unset, a default InvocationConfig will be used. */
		invocationConfig: Option[Schema.InvocationConfig] = None,
	  /** Optional. Optional schedule (in cron format) for automatic execution of this workflow config. */
		cronSchedule: Option[String] = None,
	  /** Optional. Specifies the time zone to be used when interpreting cron_schedule. Must be a time zone name from the time zone database (https://en.wikipedia.org/wiki/List_of_tz_database_time_zones). If left unspecified, the default is UTC. */
		timeZone: Option[String] = None,
	  /** Output only. Records of the 10 most recent scheduled execution attempts, ordered in descending order of `execution_time`. Updated whenever automatic creation of a workflow invocation is triggered by cron_schedule. */
		recentScheduledExecutionRecords: Option[List[Schema.ScheduledExecutionRecord]] = None,
	  /** Output only. The timestamp of when the WorkflowConfig was created. */
		createTime: Option[String] = None,
	  /** Output only. The timestamp of when the WorkflowConfig was last updated. */
		updateTime: Option[String] = None
	)
	
	case class InvocationConfig(
	  /** Optional. The set of action identifiers to include. */
		includedTargets: Option[List[Schema.Target]] = None,
	  /** Optional. The set of tags to include. */
		includedTags: Option[List[String]] = None,
	  /** Optional. When set to true, transitive dependencies of included actions will be executed. */
		transitiveDependenciesIncluded: Option[Boolean] = None,
	  /** Optional. When set to true, transitive dependents of included actions will be executed. */
		transitiveDependentsIncluded: Option[Boolean] = None,
	  /** Optional. When set to true, any incremental tables will be fully refreshed. */
		fullyRefreshIncrementalTablesEnabled: Option[Boolean] = None,
	  /** Optional. The service account to run workflow invocations under. */
		serviceAccount: Option[String] = None
	)
	
	case class ScheduledExecutionRecord(
	  /** The name of the created workflow invocation, if one was successfully created. Must be in the format `projects/&#42;/locations/&#42;/repositories/&#42;/workflowInvocations/&#42;`. */
		workflowInvocation: Option[String] = None,
	  /** The error status encountered upon this attempt to create the workflow invocation, if the attempt was unsuccessful. */
		errorStatus: Option[Schema.Status] = None,
	  /** The timestamp of this execution attempt. */
		executionTime: Option[String] = None
	)
	
	case class ListWorkflowInvocationsResponse(
	  /** List of workflow invocations. */
		workflowInvocations: Option[List[Schema.WorkflowInvocation]] = None,
	  /** A token, which can be sent as `page_token` to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None,
	  /** Locations which could not be reached. */
		unreachable: Option[List[String]] = None
	)
	
	object WorkflowInvocation {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, RUNNING, SUCCEEDED, CANCELLED, FAILED, CANCELING }
	}
	case class WorkflowInvocation(
	  /** Immutable. The name of the compilation result to use for this invocation. Must be in the format `projects/&#42;/locations/&#42;/repositories/&#42;/compilationResults/&#42;`. */
		compilationResult: Option[String] = None,
	  /** Immutable. The name of the workflow config to invoke. Must be in the format `projects/&#42;/locations/&#42;/repositories/&#42;/workflowConfigs/&#42;`. */
		workflowConfig: Option[String] = None,
	  /** Output only. The workflow invocation's name. */
		name: Option[String] = None,
	  /** Immutable. If left unset, a default InvocationConfig will be used. */
		invocationConfig: Option[Schema.InvocationConfig] = None,
	  /** Output only. This workflow invocation's current state. */
		state: Option[Schema.WorkflowInvocation.StateEnum] = None,
	  /** Output only. This workflow invocation's timing details. */
		invocationTiming: Option[Schema.Interval] = None,
	  /** Output only. The resolved compilation result that was used to create this invocation. Will be in the format `projects/&#42;/locations/&#42;/repositories/&#42;/compilationResults/&#42;`. */
		resolvedCompilationResult: Option[String] = None,
	  /** Output only. Only set if the repository has a KMS Key. */
		dataEncryptionState: Option[Schema.DataEncryptionState] = None
	)
	
	case class Interval(
	  /** Optional. Inclusive start of the interval. If specified, a Timestamp matching this interval will have to be the same or after the start. */
		startTime: Option[String] = None,
	  /** Optional. Exclusive end of the interval. If specified, a Timestamp matching this interval will have to be before the end. */
		endTime: Option[String] = None
	)
	
	case class CancelWorkflowInvocationRequest(
	
	)
	
	case class QueryWorkflowInvocationActionsResponse(
	  /** List of workflow invocation actions. */
		workflowInvocationActions: Option[List[Schema.WorkflowInvocationAction]] = None,
	  /** A token, which can be sent as `page_token` to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None
	)
	
	object WorkflowInvocationAction {
		enum StateEnum extends Enum[StateEnum] { case PENDING, RUNNING, SKIPPED, DISABLED, SUCCEEDED, CANCELLED, FAILED }
	}
	case class WorkflowInvocationAction(
	  /** Output only. The workflow action's bigquery action details. */
		bigqueryAction: Option[Schema.BigQueryAction] = None,
	  /** Output only. The workflow action's notebook action details. */
		notebookAction: Option[Schema.NotebookAction] = None,
	  /** Output only. This action's identifier. Unique within the workflow invocation. */
		target: Option[Schema.Target] = None,
	  /** Output only. The action's identifier if the project had been compiled without any overrides configured. Unique within the compilation result. */
		canonicalTarget: Option[Schema.Target] = None,
	  /** Output only. This action's current state. */
		state: Option[Schema.WorkflowInvocationAction.StateEnum] = None,
	  /** Output only. If and only if action's state is FAILED a failure reason is set. */
		failureReason: Option[String] = None,
	  /** Output only. This action's timing details. `start_time` will be set if the action is in [RUNNING, SUCCEEDED, CANCELLED, FAILED] state. `end_time` will be set if the action is in [SUCCEEDED, CANCELLED, FAILED] state. */
		invocationTiming: Option[Schema.Interval] = None
	)
	
	case class BigQueryAction(
	  /** Output only. The generated BigQuery SQL script that will be executed. */
		sqlScript: Option[String] = None,
	  /** Output only. The ID of the BigQuery job that executed the SQL in sql_script. Only set once the job has started to run. */
		jobId: Option[String] = None
	)
	
	case class NotebookAction(
	  /** Output only. The code contents of a Notebook to be run. */
		contents: Option[String] = None,
	  /** Output only. The ID of the Vertex job that executed the notebook in contents and also the ID used for the outputs created in GCS buckets. Only set once the job has started to run. */
		jobId: Option[String] = None
	)
	
	case class Config(
	  /** Identifier. The config name. */
		name: Option[String] = None,
	  /** Optional. The default KMS key that is used if no encryption key is provided when a repository is created. */
		defaultKmsKeyName: Option[String] = None
	)
	
	case class ListLocationsResponse(
	  /** A list of locations that matches the specified filter in the request. */
		locations: Option[List[Schema.Location]] = None,
	  /** The standard List next-page token. */
		nextPageToken: Option[String] = None
	)
	
	case class Location(
	  /** Resource name for the location, which may vary between implementations. For example: `"projects/example-project/locations/us-east1"` */
		name: Option[String] = None,
	  /** The canonical id for this location. For example: `"us-east1"`. */
		locationId: Option[String] = None,
	  /** The friendly name for this location, typically a nearby city name. For example, "Tokyo". */
		displayName: Option[String] = None,
	  /** Cross-service attributes for the location. For example {"cloud.googleapis.com/region": "us-east1"} */
		labels: Option[Map[String, String]] = None,
	  /** Service-specific metadata. For example the available capacity at the given location. */
		metadata: Option[Map[String, JsValue]] = None
	)
	
	case class SetIamPolicyRequest(
	  /** REQUIRED: The complete policy to be applied to the `resource`. The size of the policy is limited to a few 10s of KB. An empty policy is a valid policy but certain Google Cloud services (such as Projects) might reject them. */
		policy: Option[Schema.Policy] = None
	)
	
	case class Policy(
	  /** Specifies the format of the policy. Valid values are `0`, `1`, and `3`. Requests that specify an invalid value are rejected. Any operation that affects conditional role bindings must specify version `3`. This requirement applies to the following operations: &#42; Getting a policy that includes a conditional role binding &#42; Adding a conditional role binding to a policy &#42; Changing a conditional role binding in a policy &#42; Removing any role binding, with or without a condition, from a policy that includes conditions &#42;&#42;Important:&#42;&#42; If you use IAM Conditions, you must include the `etag` field whenever you call `setIamPolicy`. If you omit this field, then IAM allows you to overwrite a version `3` policy with a version `1` policy, and all of the conditions in the version `3` policy are lost. If a policy does not include any conditions, operations on that policy may specify any valid version or leave the field unset. To learn which resources support conditions in their IAM policies, see the [IAM documentation](https://cloud.google.com/iam/help/conditions/resource-policies). */
		version: Option[Int] = None,
	  /** Associates a list of `members`, or principals, with a `role`. Optionally, may specify a `condition` that determines how and when the `bindings` are applied. Each of the `bindings` must contain at least one principal. The `bindings` in a `Policy` can refer to up to 1,500 principals; up to 250 of these principals can be Google groups. Each occurrence of a principal counts towards these limits. For example, if the `bindings` grant 50 different roles to `user:alice@example.com`, and not to any other principal, then you can add another 1,450 principals to the `bindings` in the `Policy`. */
		bindings: Option[List[Schema.Binding]] = None,
	  /** `etag` is used for optimistic concurrency control as a way to help prevent simultaneous updates of a policy from overwriting each other. It is strongly suggested that systems make use of the `etag` in the read-modify-write cycle to perform policy updates in order to avoid race conditions: An `etag` is returned in the response to `getIamPolicy`, and systems are expected to put that etag in the request to `setIamPolicy` to ensure that their change will be applied to the same version of the policy. &#42;&#42;Important:&#42;&#42; If you use IAM Conditions, you must include the `etag` field whenever you call `setIamPolicy`. If you omit this field, then IAM allows you to overwrite a version `3` policy with a version `1` policy, and all of the conditions in the version `3` policy are lost. */
		etag: Option[String] = None
	)
	
	case class Binding(
	  /** Role that is assigned to the list of `members`, or principals. For example, `roles/viewer`, `roles/editor`, or `roles/owner`. For an overview of the IAM roles and permissions, see the [IAM documentation](https://cloud.google.com/iam/docs/roles-overview). For a list of the available pre-defined roles, see [here](https://cloud.google.com/iam/docs/understanding-roles). */
		role: Option[String] = None,
	  /** Specifies the principals requesting access for a Google Cloud resource. `members` can have the following values: &#42; `allUsers`: A special identifier that represents anyone who is on the internet; with or without a Google account. &#42; `allAuthenticatedUsers`: A special identifier that represents anyone who is authenticated with a Google account or a service account. Does not include identities that come from external identity providers (IdPs) through identity federation. &#42; `user:{emailid}`: An email address that represents a specific Google account. For example, `alice@example.com` . &#42; `serviceAccount:{emailid}`: An email address that represents a Google service account. For example, `my-other-app@appspot.gserviceaccount.com`. &#42; `serviceAccount:{projectid}.svc.id.goog[{namespace}/{kubernetes-sa}]`: An identifier for a [Kubernetes service account](https://cloud.google.com/kubernetes-engine/docs/how-to/kubernetes-service-accounts). For example, `my-project.svc.id.goog[my-namespace/my-kubernetes-sa]`. &#42; `group:{emailid}`: An email address that represents a Google group. For example, `admins@example.com`. &#42; `domain:{domain}`: The G Suite domain (primary) that represents all the users of that domain. For example, `google.com` or `example.com`. &#42; `principal://iam.googleapis.com/locations/global/workforcePools/{pool_id}/subject/{subject_attribute_value}`: A single identity in a workforce identity pool. &#42; `principalSet://iam.googleapis.com/locations/global/workforcePools/{pool_id}/group/{group_id}`: All workforce identities in a group. &#42; `principalSet://iam.googleapis.com/locations/global/workforcePools/{pool_id}/attribute.{attribute_name}/{attribute_value}`: All workforce identities with a specific attribute value. &#42; `principalSet://iam.googleapis.com/locations/global/workforcePools/{pool_id}/&#42;`: All identities in a workforce identity pool. &#42; `principal://iam.googleapis.com/projects/{project_number}/locations/global/workloadIdentityPools/{pool_id}/subject/{subject_attribute_value}`: A single identity in a workload identity pool. &#42; `principalSet://iam.googleapis.com/projects/{project_number}/locations/global/workloadIdentityPools/{pool_id}/group/{group_id}`: A workload identity pool group. &#42; `principalSet://iam.googleapis.com/projects/{project_number}/locations/global/workloadIdentityPools/{pool_id}/attribute.{attribute_name}/{attribute_value}`: All identities in a workload identity pool with a certain attribute. &#42; `principalSet://iam.googleapis.com/projects/{project_number}/locations/global/workloadIdentityPools/{pool_id}/&#42;`: All identities in a workload identity pool. &#42; `deleted:user:{emailid}?uid={uniqueid}`: An email address (plus unique identifier) representing a user that has been recently deleted. For example, `alice@example.com?uid=123456789012345678901`. If the user is recovered, this value reverts to `user:{emailid}` and the recovered user retains the role in the binding. &#42; `deleted:serviceAccount:{emailid}?uid={uniqueid}`: An email address (plus unique identifier) representing a service account that has been recently deleted. For example, `my-other-app@appspot.gserviceaccount.com?uid=123456789012345678901`. If the service account is undeleted, this value reverts to `serviceAccount:{emailid}` and the undeleted service account retains the role in the binding. &#42; `deleted:group:{emailid}?uid={uniqueid}`: An email address (plus unique identifier) representing a Google group that has been recently deleted. For example, `admins@example.com?uid=123456789012345678901`. If the group is recovered, this value reverts to `group:{emailid}` and the recovered group retains the role in the binding. &#42; `deleted:principal://iam.googleapis.com/locations/global/workforcePools/{pool_id}/subject/{subject_attribute_value}`: Deleted single identity in a workforce identity pool. For example, `deleted:principal://iam.googleapis.com/locations/global/workforcePools/my-pool-id/subject/my-subject-attribute-value`. */
		members: Option[List[String]] = None,
	  /** The condition that is associated with this binding. If the condition evaluates to `true`, then this binding applies to the current request. If the condition evaluates to `false`, then this binding does not apply to the current request. However, a different role binding might grant the same role to one or more of the principals in this binding. To learn which resources support conditions in their IAM policies, see the [IAM documentation](https://cloud.google.com/iam/help/conditions/resource-policies). */
		condition: Option[Schema.Expr] = None
	)
	
	case class Expr(
	  /** Textual representation of an expression in Common Expression Language syntax. */
		expression: Option[String] = None,
	  /** Optional. Title for the expression, i.e. a short string describing its purpose. This can be used e.g. in UIs which allow to enter the expression. */
		title: Option[String] = None,
	  /** Optional. Description of the expression. This is a longer text which describes the expression, e.g. when hovered over it in a UI. */
		description: Option[String] = None,
	  /** Optional. String indicating the location of the expression for error reporting, e.g. a file name and a position in the file. */
		location: Option[String] = None
	)
	
	case class TestIamPermissionsRequest(
	  /** The set of permissions to check for the `resource`. Permissions with wildcards (such as `&#42;` or `storage.&#42;`) are not allowed. For more information see [IAM Overview](https://cloud.google.com/iam/docs/overview#permissions). */
		permissions: Option[List[String]] = None
	)
	
	case class TestIamPermissionsResponse(
	  /** A subset of `TestPermissionsRequest.permissions` that the caller is allowed. */
		permissions: Option[List[String]] = None
	)
	
	case class OperationMetadata(
	  /** Output only. The time the operation was created. */
		createTime: Option[String] = None,
	  /** Output only. The time the operation finished running. */
		endTime: Option[String] = None,
	  /** Output only. Server-defined resource path for the target of the operation. */
		target: Option[String] = None,
	  /** Output only. Name of the verb executed by the operation. */
		verb: Option[String] = None,
	  /** Output only. Human-readable status of the operation, if any. */
		statusDetail: Option[String] = None,
	  /** Output only. Identifies whether the user has requested cancellation of the operation. Operations that have been cancelled successfully have Operation.error value with a google.rpc.Status.code of 1, corresponding to `Code.CANCELLED`. */
		cancelRequested: Option[Boolean] = None,
	  /** Output only. API version used to start the operation. */
		apiVersion: Option[String] = None
	)
}
