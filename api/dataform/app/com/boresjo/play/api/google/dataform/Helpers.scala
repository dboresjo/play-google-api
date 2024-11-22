package com.boresjo.play.api.google.dataform

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Helpers {
	import Schema.*
	object AutoOption {
		given putListSchemaRepository: Conversion[List[Schema.Repository], Option[List[Schema.Repository]]] = (fun: List[Schema.Repository]) => Option(fun)
		given putString: Conversion[String, Option[String]] = (fun: String) => Option(fun)
		given putListString: Conversion[List[String], Option[List[String]]] = (fun: List[String]) => Option(fun)
		given putSchemaGitRemoteSettings: Conversion[Schema.GitRemoteSettings, Option[Schema.GitRemoteSettings]] = (fun: Schema.GitRemoteSettings) => Option(fun)
		given putSchemaWorkspaceCompilationOverrides: Conversion[Schema.WorkspaceCompilationOverrides, Option[Schema.WorkspaceCompilationOverrides]] = (fun: Schema.WorkspaceCompilationOverrides) => Option(fun)
		given putMapStringString: Conversion[Map[String, String], Option[Map[String, String]]] = (fun: Map[String, String]) => Option(fun)
		given putBoolean: Conversion[Boolean, Option[Boolean]] = (fun: Boolean) => Option(fun)
		given putSchemaDataEncryptionState: Conversion[Schema.DataEncryptionState, Option[Schema.DataEncryptionState]] = (fun: Schema.DataEncryptionState) => Option(fun)
		given putSchemaSshAuthenticationConfig: Conversion[Schema.SshAuthenticationConfig, Option[Schema.SshAuthenticationConfig]] = (fun: Schema.SshAuthenticationConfig) => Option(fun)
		given putSchemaGitRemoteSettingsTokenStatusEnum: Conversion[Schema.GitRemoteSettings.TokenStatusEnum, Option[Schema.GitRemoteSettings.TokenStatusEnum]] = (fun: Schema.GitRemoteSettings.TokenStatusEnum) => Option(fun)
		given putSchemaCommitMetadata: Conversion[Schema.CommitMetadata, Option[Schema.CommitMetadata]] = (fun: Schema.CommitMetadata) => Option(fun)
		given putMapStringSchemaFileOperation: Conversion[Map[String, Schema.FileOperation], Option[Map[String, Schema.FileOperation]]] = (fun: Map[String, Schema.FileOperation]) => Option(fun)
		given putSchemaCommitAuthor: Conversion[Schema.CommitAuthor, Option[Schema.CommitAuthor]] = (fun: Schema.CommitAuthor) => Option(fun)
		given putSchemaWriteFile: Conversion[Schema.WriteFile, Option[Schema.WriteFile]] = (fun: Schema.WriteFile) => Option(fun)
		given putSchemaDeleteFile: Conversion[Schema.DeleteFile, Option[Schema.DeleteFile]] = (fun: Schema.DeleteFile) => Option(fun)
		given putListSchemaDirectoryEntry: Conversion[List[Schema.DirectoryEntry], Option[List[Schema.DirectoryEntry]]] = (fun: List[Schema.DirectoryEntry]) => Option(fun)
		given putListSchemaCommitLogEntry: Conversion[List[Schema.CommitLogEntry], Option[List[Schema.CommitLogEntry]]] = (fun: List[Schema.CommitLogEntry]) => Option(fun)
		given putSchemaComputeRepositoryAccessTokenStatusResponseTokenStatusEnum: Conversion[Schema.ComputeRepositoryAccessTokenStatusResponse.TokenStatusEnum, Option[Schema.ComputeRepositoryAccessTokenStatusResponse.TokenStatusEnum]] = (fun: Schema.ComputeRepositoryAccessTokenStatusResponse.TokenStatusEnum) => Option(fun)
		given putListSchemaWorkspace: Conversion[List[Schema.Workspace], Option[List[Schema.Workspace]]] = (fun: List[Schema.Workspace]) => Option(fun)
		given putListSchemaUncommittedFileChange: Conversion[List[Schema.UncommittedFileChange], Option[List[Schema.UncommittedFileChange]]] = (fun: List[Schema.UncommittedFileChange]) => Option(fun)
		given putSchemaUncommittedFileChangeStateEnum: Conversion[Schema.UncommittedFileChange.StateEnum, Option[Schema.UncommittedFileChange.StateEnum]] = (fun: Schema.UncommittedFileChange.StateEnum) => Option(fun)
		given putInt: Conversion[Int, Option[Int]] = (fun: Int) => Option(fun)
		given putListSchemaSearchResult: Conversion[List[Schema.SearchResult], Option[List[Schema.SearchResult]]] = (fun: List[Schema.SearchResult]) => Option(fun)
		given putSchemaFileSearchResult: Conversion[Schema.FileSearchResult, Option[Schema.FileSearchResult]] = (fun: Schema.FileSearchResult) => Option(fun)
		given putSchemaDirectorySearchResult: Conversion[Schema.DirectorySearchResult, Option[Schema.DirectorySearchResult]] = (fun: Schema.DirectorySearchResult) => Option(fun)
		given putListSchemaReleaseConfig: Conversion[List[Schema.ReleaseConfig], Option[List[Schema.ReleaseConfig]]] = (fun: List[Schema.ReleaseConfig]) => Option(fun)
		given putSchemaCodeCompilationConfig: Conversion[Schema.CodeCompilationConfig, Option[Schema.CodeCompilationConfig]] = (fun: Schema.CodeCompilationConfig) => Option(fun)
		given putListSchemaScheduledReleaseRecord: Conversion[List[Schema.ScheduledReleaseRecord], Option[List[Schema.ScheduledReleaseRecord]]] = (fun: List[Schema.ScheduledReleaseRecord]) => Option(fun)
		given putSchemaNotebookRuntimeOptions: Conversion[Schema.NotebookRuntimeOptions, Option[Schema.NotebookRuntimeOptions]] = (fun: Schema.NotebookRuntimeOptions) => Option(fun)
		given putSchemaStatus: Conversion[Schema.Status, Option[Schema.Status]] = (fun: Schema.Status) => Option(fun)
		given putListMapStringJsValue: Conversion[List[Map[String, JsValue]], Option[List[Map[String, JsValue]]]] = (fun: List[Map[String, JsValue]]) => Option(fun)
		given putListSchemaCompilationResult: Conversion[List[Schema.CompilationResult], Option[List[Schema.CompilationResult]]] = (fun: List[Schema.CompilationResult]) => Option(fun)
		given putListSchemaCompilationError: Conversion[List[Schema.CompilationError], Option[List[Schema.CompilationError]]] = (fun: List[Schema.CompilationError]) => Option(fun)
		given putSchemaTarget: Conversion[Schema.Target, Option[Schema.Target]] = (fun: Schema.Target) => Option(fun)
		given putListSchemaCompilationResultAction: Conversion[List[Schema.CompilationResultAction], Option[List[Schema.CompilationResultAction]]] = (fun: List[Schema.CompilationResultAction]) => Option(fun)
		given putSchemaRelation: Conversion[Schema.Relation, Option[Schema.Relation]] = (fun: Schema.Relation) => Option(fun)
		given putSchemaOperations: Conversion[Schema.Operations, Option[Schema.Operations]] = (fun: Schema.Operations) => Option(fun)
		given putSchemaAssertion: Conversion[Schema.Assertion, Option[Schema.Assertion]] = (fun: Schema.Assertion) => Option(fun)
		given putSchemaDeclaration: Conversion[Schema.Declaration, Option[Schema.Declaration]] = (fun: Schema.Declaration) => Option(fun)
		given putSchemaNotebook: Conversion[Schema.Notebook, Option[Schema.Notebook]] = (fun: Schema.Notebook) => Option(fun)
		given putListSchemaTarget: Conversion[List[Schema.Target], Option[List[Schema.Target]]] = (fun: List[Schema.Target]) => Option(fun)
		given putSchemaRelationDescriptor: Conversion[Schema.RelationDescriptor, Option[Schema.RelationDescriptor]] = (fun: Schema.RelationDescriptor) => Option(fun)
		given putSchemaRelationRelationTypeEnum: Conversion[Schema.Relation.RelationTypeEnum, Option[Schema.Relation.RelationTypeEnum]] = (fun: Schema.Relation.RelationTypeEnum) => Option(fun)
		given putSchemaIncrementalTableConfig: Conversion[Schema.IncrementalTableConfig, Option[Schema.IncrementalTableConfig]] = (fun: Schema.IncrementalTableConfig) => Option(fun)
		given putListSchemaColumnDescriptor: Conversion[List[Schema.ColumnDescriptor], Option[List[Schema.ColumnDescriptor]]] = (fun: List[Schema.ColumnDescriptor]) => Option(fun)
		given putListSchemaWorkflowConfig: Conversion[List[Schema.WorkflowConfig], Option[List[Schema.WorkflowConfig]]] = (fun: List[Schema.WorkflowConfig]) => Option(fun)
		given putSchemaInvocationConfig: Conversion[Schema.InvocationConfig, Option[Schema.InvocationConfig]] = (fun: Schema.InvocationConfig) => Option(fun)
		given putListSchemaScheduledExecutionRecord: Conversion[List[Schema.ScheduledExecutionRecord], Option[List[Schema.ScheduledExecutionRecord]]] = (fun: List[Schema.ScheduledExecutionRecord]) => Option(fun)
		given putListSchemaWorkflowInvocation: Conversion[List[Schema.WorkflowInvocation], Option[List[Schema.WorkflowInvocation]]] = (fun: List[Schema.WorkflowInvocation]) => Option(fun)
		given putSchemaWorkflowInvocationStateEnum: Conversion[Schema.WorkflowInvocation.StateEnum, Option[Schema.WorkflowInvocation.StateEnum]] = (fun: Schema.WorkflowInvocation.StateEnum) => Option(fun)
		given putSchemaInterval: Conversion[Schema.Interval, Option[Schema.Interval]] = (fun: Schema.Interval) => Option(fun)
		given putListSchemaWorkflowInvocationAction: Conversion[List[Schema.WorkflowInvocationAction], Option[List[Schema.WorkflowInvocationAction]]] = (fun: List[Schema.WorkflowInvocationAction]) => Option(fun)
		given putSchemaBigQueryAction: Conversion[Schema.BigQueryAction, Option[Schema.BigQueryAction]] = (fun: Schema.BigQueryAction) => Option(fun)
		given putSchemaNotebookAction: Conversion[Schema.NotebookAction, Option[Schema.NotebookAction]] = (fun: Schema.NotebookAction) => Option(fun)
		given putSchemaWorkflowInvocationActionStateEnum: Conversion[Schema.WorkflowInvocationAction.StateEnum, Option[Schema.WorkflowInvocationAction.StateEnum]] = (fun: Schema.WorkflowInvocationAction.StateEnum) => Option(fun)
		given putListSchemaLocation: Conversion[List[Schema.Location], Option[List[Schema.Location]]] = (fun: List[Schema.Location]) => Option(fun)
		given putMapStringJsValue: Conversion[Map[String, JsValue], Option[Map[String, JsValue]]] = (fun: Map[String, JsValue]) => Option(fun)
		given putSchemaPolicy: Conversion[Schema.Policy, Option[Schema.Policy]] = (fun: Schema.Policy) => Option(fun)
		given putListSchemaBinding: Conversion[List[Schema.Binding], Option[List[Schema.Binding]]] = (fun: List[Schema.Binding]) => Option(fun)
		given putSchemaExpr: Conversion[Schema.Expr, Option[Schema.Expr]] = (fun: Schema.Expr) => Option(fun)
	}
	object OptionDefault {
		given getString: Conversion[Option[String], String] = (fun: Option[String]) => fun.getOrElse("")
	}
}
