package com.boresjo.play.api.google.storage

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Formats {
	given fmtBucket: Format[Schema.Bucket] = Json.format[Schema.Bucket]
	given fmtBucketAccessControl: Format[Schema.BucketAccessControl] = Json.format[Schema.BucketAccessControl]
	given fmtBucketBillingItem: Format[Schema.Bucket.BillingItem] = Json.format[Schema.Bucket.BillingItem]
	given fmtBucketCorsItem: Format[Schema.Bucket.CorsItem] = Json.format[Schema.Bucket.CorsItem]
	given fmtBucketCustomPlacementConfigItem: Format[Schema.Bucket.CustomPlacementConfigItem] = Json.format[Schema.Bucket.CustomPlacementConfigItem]
	given fmtObjectAccessControl: Format[Schema.ObjectAccessControl] = Json.format[Schema.ObjectAccessControl]
	given fmtBucketEncryptionItem: Format[Schema.Bucket.EncryptionItem] = Json.format[Schema.Bucket.EncryptionItem]
	given fmtBucketHierarchicalNamespaceItem: Format[Schema.Bucket.HierarchicalNamespaceItem] = Json.format[Schema.Bucket.HierarchicalNamespaceItem]
	given fmtBucketIamConfigurationItem: Format[Schema.Bucket.IamConfigurationItem] = Json.format[Schema.Bucket.IamConfigurationItem]
	given fmtBucketIamConfigurationItemBucketPolicyOnlyItem: Format[Schema.Bucket.IamConfigurationItem.BucketPolicyOnlyItem] = Json.format[Schema.Bucket.IamConfigurationItem.BucketPolicyOnlyItem]
	given fmtBucketIamConfigurationItemUniformBucketLevelAccessItem: Format[Schema.Bucket.IamConfigurationItem.UniformBucketLevelAccessItem] = Json.format[Schema.Bucket.IamConfigurationItem.UniformBucketLevelAccessItem]
	given fmtBucketIpFilterItem: Format[Schema.Bucket.IpFilterItem] = Json.format[Schema.Bucket.IpFilterItem]
	given fmtBucketIpFilterItemPublicNetworkSourceItem: Format[Schema.Bucket.IpFilterItem.PublicNetworkSourceItem] = Json.format[Schema.Bucket.IpFilterItem.PublicNetworkSourceItem]
	given fmtBucketIpFilterItemVpcNetworkSourcesItem: Format[Schema.Bucket.IpFilterItem.VpcNetworkSourcesItem] = Json.format[Schema.Bucket.IpFilterItem.VpcNetworkSourcesItem]
	given fmtBucketLifecycleItem: Format[Schema.Bucket.LifecycleItem] = Json.format[Schema.Bucket.LifecycleItem]
	given fmtBucketLifecycleItemRuleItem: Format[Schema.Bucket.LifecycleItem.RuleItem] = Json.format[Schema.Bucket.LifecycleItem.RuleItem]
	given fmtBucketLifecycleItemRuleItemActionItem: Format[Schema.Bucket.LifecycleItem.RuleItem.ActionItem] = Json.format[Schema.Bucket.LifecycleItem.RuleItem.ActionItem]
	given fmtBucketLifecycleItemRuleItemConditionItem: Format[Schema.Bucket.LifecycleItem.RuleItem.ConditionItem] = Json.format[Schema.Bucket.LifecycleItem.RuleItem.ConditionItem]
	given fmtBucketAutoclassItem: Format[Schema.Bucket.AutoclassItem] = Json.format[Schema.Bucket.AutoclassItem]
	given fmtBucketLoggingItem: Format[Schema.Bucket.LoggingItem] = Json.format[Schema.Bucket.LoggingItem]
	given fmtBucketOwnerItem: Format[Schema.Bucket.OwnerItem] = Json.format[Schema.Bucket.OwnerItem]
	given fmtBucketRetentionPolicyItem: Format[Schema.Bucket.RetentionPolicyItem] = Json.format[Schema.Bucket.RetentionPolicyItem]
	given fmtBucketObjectRetentionItem: Format[Schema.Bucket.ObjectRetentionItem] = Json.format[Schema.Bucket.ObjectRetentionItem]
	given fmtBucketSoftDeletePolicyItem: Format[Schema.Bucket.SoftDeletePolicyItem] = Json.format[Schema.Bucket.SoftDeletePolicyItem]
	given fmtBucketVersioningItem: Format[Schema.Bucket.VersioningItem] = Json.format[Schema.Bucket.VersioningItem]
	given fmtBucketWebsiteItem: Format[Schema.Bucket.WebsiteItem] = Json.format[Schema.Bucket.WebsiteItem]
	given fmtAdvanceRelocateBucketOperationRequest: Format[Schema.AdvanceRelocateBucketOperationRequest] = Json.format[Schema.AdvanceRelocateBucketOperationRequest]
	given fmtAnywhereCache: Format[Schema.AnywhereCache] = Json.format[Schema.AnywhereCache]
	given fmtAnywhereCaches: Format[Schema.AnywhereCaches] = Json.format[Schema.AnywhereCaches]
	given fmtBucketAccessControlProjectTeamItem: Format[Schema.BucketAccessControl.ProjectTeamItem] = Json.format[Schema.BucketAccessControl.ProjectTeamItem]
	given fmtBucketAccessControls: Format[Schema.BucketAccessControls] = Json.format[Schema.BucketAccessControls]
	given fmtBucketStorageLayout: Format[Schema.BucketStorageLayout] = Json.format[Schema.BucketStorageLayout]
	given fmtBucketStorageLayoutCustomPlacementConfigItem: Format[Schema.BucketStorageLayout.CustomPlacementConfigItem] = Json.format[Schema.BucketStorageLayout.CustomPlacementConfigItem]
	given fmtBucketStorageLayoutHierarchicalNamespaceItem: Format[Schema.BucketStorageLayout.HierarchicalNamespaceItem] = Json.format[Schema.BucketStorageLayout.HierarchicalNamespaceItem]
	given fmtBuckets: Format[Schema.Buckets] = Json.format[Schema.Buckets]
	given fmtChannel: Format[Schema.Channel] = Json.format[Schema.Channel]
	given fmtComposeRequest: Format[Schema.ComposeRequest] = Json.format[Schema.ComposeRequest]
	given fmtObject: Format[Schema.Object] = Json.format[Schema.Object]
	given fmtComposeRequestSourceObjectsItem: Format[Schema.ComposeRequest.SourceObjectsItem] = Json.format[Schema.ComposeRequest.SourceObjectsItem]
	given fmtComposeRequestSourceObjectsItemObjectPreconditionsItem: Format[Schema.ComposeRequest.SourceObjectsItem.ObjectPreconditionsItem] = Json.format[Schema.ComposeRequest.SourceObjectsItem.ObjectPreconditionsItem]
	given fmtFolder: Format[Schema.Folder] = Json.format[Schema.Folder]
	given fmtFolderPendingRenameInfoItem: Format[Schema.Folder.PendingRenameInfoItem] = Json.format[Schema.Folder.PendingRenameInfoItem]
	given fmtFolders: Format[Schema.Folders] = Json.format[Schema.Folders]
	given fmtExpr: Format[Schema.Expr] = Json.format[Schema.Expr]
	given fmtGoogleLongrunningOperation: Format[Schema.GoogleLongrunningOperation] = Json.format[Schema.GoogleLongrunningOperation]
	given fmtGoogleRpcStatus: Format[Schema.GoogleRpcStatus] = Json.format[Schema.GoogleRpcStatus]
	given fmtGoogleLongrunningListOperationsResponse: Format[Schema.GoogleLongrunningListOperationsResponse] = Json.format[Schema.GoogleLongrunningListOperationsResponse]
	given fmtHmacKey: Format[Schema.HmacKey] = Json.format[Schema.HmacKey]
	given fmtHmacKeyMetadata: Format[Schema.HmacKeyMetadata] = Json.format[Schema.HmacKeyMetadata]
	given fmtHmacKeysMetadata: Format[Schema.HmacKeysMetadata] = Json.format[Schema.HmacKeysMetadata]
	given fmtManagedFolder: Format[Schema.ManagedFolder] = Json.format[Schema.ManagedFolder]
	given fmtManagedFolders: Format[Schema.ManagedFolders] = Json.format[Schema.ManagedFolders]
	given fmtNotification: Format[Schema.Notification] = Json.format[Schema.Notification]
	given fmtNotifications: Format[Schema.Notifications] = Json.format[Schema.Notifications]
	given fmtObjectCustomerEncryptionItem: Format[Schema.Object.CustomerEncryptionItem] = Json.format[Schema.Object.CustomerEncryptionItem]
	given fmtObjectOwnerItem: Format[Schema.Object.OwnerItem] = Json.format[Schema.Object.OwnerItem]
	given fmtObjectRetentionItem: Format[Schema.Object.RetentionItem] = Json.format[Schema.Object.RetentionItem]
	given fmtObjectAccessControlProjectTeamItem: Format[Schema.ObjectAccessControl.ProjectTeamItem] = Json.format[Schema.ObjectAccessControl.ProjectTeamItem]
	given fmtObjectAccessControls: Format[Schema.ObjectAccessControls] = Json.format[Schema.ObjectAccessControls]
	given fmtObjects: Format[Schema.Objects] = Json.format[Schema.Objects]
	given fmtPolicy: Format[Schema.Policy] = Json.format[Schema.Policy]
	given fmtPolicyBindingsItem: Format[Schema.Policy.BindingsItem] = Json.format[Schema.Policy.BindingsItem]
	given fmtRelocateBucketRequest: Format[Schema.RelocateBucketRequest] = Json.format[Schema.RelocateBucketRequest]
	given fmtRelocateBucketRequestDestinationCustomPlacementConfigItem: Format[Schema.RelocateBucketRequest.DestinationCustomPlacementConfigItem] = Json.format[Schema.RelocateBucketRequest.DestinationCustomPlacementConfigItem]
	given fmtRewriteResponse: Format[Schema.RewriteResponse] = Json.format[Schema.RewriteResponse]
	given fmtServiceAccount: Format[Schema.ServiceAccount] = Json.format[Schema.ServiceAccount]
	given fmtTestIamPermissionsResponse: Format[Schema.TestIamPermissionsResponse] = Json.format[Schema.TestIamPermissionsResponse]
	given fmtBulkRestoreObjectsRequest: Format[Schema.BulkRestoreObjectsRequest] = Json.format[Schema.BulkRestoreObjectsRequest]
}
