package com.boresjo.play.api.google.storage

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Helpers {
	import Schema.*
	object AutoOption {
		given putListSchemaBucketAccessControl: Conversion[List[Schema.BucketAccessControl], Option[List[Schema.BucketAccessControl]]] = (fun: List[Schema.BucketAccessControl]) => Option(fun)
		given putSchemaBucketBillingItem: Conversion[Schema.Bucket.BillingItem, Option[Schema.Bucket.BillingItem]] = (fun: Schema.Bucket.BillingItem) => Option(fun)
		given putListSchemaBucketCorsItem: Conversion[List[Schema.Bucket.CorsItem], Option[List[Schema.Bucket.CorsItem]]] = (fun: List[Schema.Bucket.CorsItem]) => Option(fun)
		given putSchemaBucketCustomPlacementConfigItem: Conversion[Schema.Bucket.CustomPlacementConfigItem, Option[Schema.Bucket.CustomPlacementConfigItem]] = (fun: Schema.Bucket.CustomPlacementConfigItem) => Option(fun)
		given putBoolean: Conversion[Boolean, Option[Boolean]] = (fun: Boolean) => Option(fun)
		given putListSchemaObjectAccessControl: Conversion[List[Schema.ObjectAccessControl], Option[List[Schema.ObjectAccessControl]]] = (fun: List[Schema.ObjectAccessControl]) => Option(fun)
		given putSchemaBucketEncryptionItem: Conversion[Schema.Bucket.EncryptionItem, Option[Schema.Bucket.EncryptionItem]] = (fun: Schema.Bucket.EncryptionItem) => Option(fun)
		given putString: Conversion[String, Option[String]] = (fun: String) => Option(fun)
		given putSchemaBucketHierarchicalNamespaceItem: Conversion[Schema.Bucket.HierarchicalNamespaceItem, Option[Schema.Bucket.HierarchicalNamespaceItem]] = (fun: Schema.Bucket.HierarchicalNamespaceItem) => Option(fun)
		given putSchemaBucketIamConfigurationItem: Conversion[Schema.Bucket.IamConfigurationItem, Option[Schema.Bucket.IamConfigurationItem]] = (fun: Schema.Bucket.IamConfigurationItem) => Option(fun)
		given putSchemaBucketIpFilterItem: Conversion[Schema.Bucket.IpFilterItem, Option[Schema.Bucket.IpFilterItem]] = (fun: Schema.Bucket.IpFilterItem) => Option(fun)
		given putMapStringString: Conversion[Map[String, String], Option[Map[String, String]]] = (fun: Map[String, String]) => Option(fun)
		given putSchemaBucketLifecycleItem: Conversion[Schema.Bucket.LifecycleItem, Option[Schema.Bucket.LifecycleItem]] = (fun: Schema.Bucket.LifecycleItem) => Option(fun)
		given putSchemaBucketAutoclassItem: Conversion[Schema.Bucket.AutoclassItem, Option[Schema.Bucket.AutoclassItem]] = (fun: Schema.Bucket.AutoclassItem) => Option(fun)
		given putSchemaBucketLoggingItem: Conversion[Schema.Bucket.LoggingItem, Option[Schema.Bucket.LoggingItem]] = (fun: Schema.Bucket.LoggingItem) => Option(fun)
		given putSchemaBucketOwnerItem: Conversion[Schema.Bucket.OwnerItem, Option[Schema.Bucket.OwnerItem]] = (fun: Schema.Bucket.OwnerItem) => Option(fun)
		given putSchemaBucketRetentionPolicyItem: Conversion[Schema.Bucket.RetentionPolicyItem, Option[Schema.Bucket.RetentionPolicyItem]] = (fun: Schema.Bucket.RetentionPolicyItem) => Option(fun)
		given putSchemaBucketObjectRetentionItem: Conversion[Schema.Bucket.ObjectRetentionItem, Option[Schema.Bucket.ObjectRetentionItem]] = (fun: Schema.Bucket.ObjectRetentionItem) => Option(fun)
		given putSchemaBucketSoftDeletePolicyItem: Conversion[Schema.Bucket.SoftDeletePolicyItem, Option[Schema.Bucket.SoftDeletePolicyItem]] = (fun: Schema.Bucket.SoftDeletePolicyItem) => Option(fun)
		given putSchemaBucketVersioningItem: Conversion[Schema.Bucket.VersioningItem, Option[Schema.Bucket.VersioningItem]] = (fun: Schema.Bucket.VersioningItem) => Option(fun)
		given putSchemaBucketWebsiteItem: Conversion[Schema.Bucket.WebsiteItem, Option[Schema.Bucket.WebsiteItem]] = (fun: Schema.Bucket.WebsiteItem) => Option(fun)
		given putListSchemaAnywhereCache: Conversion[List[Schema.AnywhereCache], Option[List[Schema.AnywhereCache]]] = (fun: List[Schema.AnywhereCache]) => Option(fun)
		given putSchemaBucketAccessControlProjectTeamItem: Conversion[Schema.BucketAccessControl.ProjectTeamItem, Option[Schema.BucketAccessControl.ProjectTeamItem]] = (fun: Schema.BucketAccessControl.ProjectTeamItem) => Option(fun)
		given putSchemaBucketStorageLayoutCustomPlacementConfigItem: Conversion[Schema.BucketStorageLayout.CustomPlacementConfigItem, Option[Schema.BucketStorageLayout.CustomPlacementConfigItem]] = (fun: Schema.BucketStorageLayout.CustomPlacementConfigItem) => Option(fun)
		given putSchemaBucketStorageLayoutHierarchicalNamespaceItem: Conversion[Schema.BucketStorageLayout.HierarchicalNamespaceItem, Option[Schema.BucketStorageLayout.HierarchicalNamespaceItem]] = (fun: Schema.BucketStorageLayout.HierarchicalNamespaceItem) => Option(fun)
		given putListSchemaBucket: Conversion[List[Schema.Bucket], Option[List[Schema.Bucket]]] = (fun: List[Schema.Bucket]) => Option(fun)
		given putSchemaObject: Conversion[Schema.Object, Option[Schema.Object]] = (fun: Schema.Object) => Option(fun)
		given putListSchemaComposeRequestSourceObjectsItem: Conversion[List[Schema.ComposeRequest.SourceObjectsItem], Option[List[Schema.ComposeRequest.SourceObjectsItem]]] = (fun: List[Schema.ComposeRequest.SourceObjectsItem]) => Option(fun)
		given putSchemaFolderPendingRenameInfoItem: Conversion[Schema.Folder.PendingRenameInfoItem, Option[Schema.Folder.PendingRenameInfoItem]] = (fun: Schema.Folder.PendingRenameInfoItem) => Option(fun)
		given putListSchemaFolder: Conversion[List[Schema.Folder], Option[List[Schema.Folder]]] = (fun: List[Schema.Folder]) => Option(fun)
		given putSchemaGoogleRpcStatus: Conversion[Schema.GoogleRpcStatus, Option[Schema.GoogleRpcStatus]] = (fun: Schema.GoogleRpcStatus) => Option(fun)
		given putMapStringJsValue: Conversion[Map[String, JsValue], Option[Map[String, JsValue]]] = (fun: Map[String, JsValue]) => Option(fun)
		given putListSchemaGoogleLongrunningOperation: Conversion[List[Schema.GoogleLongrunningOperation], Option[List[Schema.GoogleLongrunningOperation]]] = (fun: List[Schema.GoogleLongrunningOperation]) => Option(fun)
		given putInt: Conversion[Int, Option[Int]] = (fun: Int) => Option(fun)
		given putListMapStringJsValue: Conversion[List[Map[String, JsValue]], Option[List[Map[String, JsValue]]]] = (fun: List[Map[String, JsValue]]) => Option(fun)
		given putSchemaHmacKeyMetadata: Conversion[Schema.HmacKeyMetadata, Option[Schema.HmacKeyMetadata]] = (fun: Schema.HmacKeyMetadata) => Option(fun)
		given putListSchemaHmacKeyMetadata: Conversion[List[Schema.HmacKeyMetadata], Option[List[Schema.HmacKeyMetadata]]] = (fun: List[Schema.HmacKeyMetadata]) => Option(fun)
		given putListSchemaManagedFolder: Conversion[List[Schema.ManagedFolder], Option[List[Schema.ManagedFolder]]] = (fun: List[Schema.ManagedFolder]) => Option(fun)
		given putListString: Conversion[List[String], Option[List[String]]] = (fun: List[String]) => Option(fun)
		given putListSchemaNotification: Conversion[List[Schema.Notification], Option[List[Schema.Notification]]] = (fun: List[Schema.Notification]) => Option(fun)
		given putSchemaObjectCustomerEncryptionItem: Conversion[Schema.Object.CustomerEncryptionItem, Option[Schema.Object.CustomerEncryptionItem]] = (fun: Schema.Object.CustomerEncryptionItem) => Option(fun)
		given putSchemaObjectOwnerItem: Conversion[Schema.Object.OwnerItem, Option[Schema.Object.OwnerItem]] = (fun: Schema.Object.OwnerItem) => Option(fun)
		given putSchemaObjectRetentionItem: Conversion[Schema.Object.RetentionItem, Option[Schema.Object.RetentionItem]] = (fun: Schema.Object.RetentionItem) => Option(fun)
		given putSchemaObjectAccessControlProjectTeamItem: Conversion[Schema.ObjectAccessControl.ProjectTeamItem, Option[Schema.ObjectAccessControl.ProjectTeamItem]] = (fun: Schema.ObjectAccessControl.ProjectTeamItem) => Option(fun)
		given putListSchemaObject: Conversion[List[Schema.Object], Option[List[Schema.Object]]] = (fun: List[Schema.Object]) => Option(fun)
		given putListSchemaPolicyBindingsItem: Conversion[List[Schema.Policy.BindingsItem], Option[List[Schema.Policy.BindingsItem]]] = (fun: List[Schema.Policy.BindingsItem]) => Option(fun)
		given putSchemaRelocateBucketRequestDestinationCustomPlacementConfigItem: Conversion[Schema.RelocateBucketRequest.DestinationCustomPlacementConfigItem, Option[Schema.RelocateBucketRequest.DestinationCustomPlacementConfigItem]] = (fun: Schema.RelocateBucketRequest.DestinationCustomPlacementConfigItem) => Option(fun)
	}
	object OptionDefault {
		given getString: Conversion[Option[String], String] = (fun: Option[String]) => fun.getOrElse("")
	}
}
