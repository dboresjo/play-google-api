package com.boresjo.play.api.google.groupssettings

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	case class Groups(
	  /** Allows the Group contents to be archived. Possible values are:  
	- true: Archive messages sent to the group. 
	- false: Do not keep an archive of messages sent to this group. If false, previously archived messages remain in the archive. */
		isArchived: Option[String] = None,
	  /** Specifies whether a collaborative inbox will remain turned on for the group. Possible values are:  
	- true 
	- false */
		enableCollaborativeInbox: Option[String] = None,
	  /** Deprecated. This is merged into the new whoCanAssistContent setting. Permission to mark a topic as a duplicate of another topic. Possible values are:  
	- ALL_MEMBERS 
	- OWNERS_AND_MANAGERS 
	- MANAGERS_ONLY 
	- OWNERS_ONLY 
	- NONE */
		whoCanMarkDuplicate: Option[String] = None,
	  /** The group's email address. This property can be updated using the Directory API. Note: Only a group owner can change a group's email address. A group manager can't do this.
	When you change your group's address using the Directory API or the control panel, you are changing the address your subscribers use to send email and the web address people use to access your group. People can't reach your group by visiting the old address. */
		email: Option[String] = None,
	  /** Allows the group to be archived only. Possible values are:  
	- true: Group is archived and the group is inactive. New messages to this group are rejected. The older archived messages are browseable and searchable.  
	- If true, the whoCanPostMessage property is set to NONE_CAN_POST.  
	- If reverted from true to false, whoCanPostMessages is set to ALL_MANAGERS_CAN_POST.  
	- false: The group is active and can receive messages.  
	- When false, updating whoCanPostMessage to NONE_CAN_POST, results in an error. */
		archiveOnly: Option[String] = None,
	  /** Specifies who can moderate content. Possible values are:  
	- ALL_MEMBERS 
	- OWNERS_AND_MANAGERS 
	- OWNERS_ONLY 
	- NONE */
		whoCanModerateContent: Option[String] = None,
	  /** Deprecated. The default message display font always has a value of "DEFAULT_FONT". */
		messageDisplayFont: Option[String] = None,
	  /** The primary language for group. For a group's primary language use the language tags from the G Suite languages found at G Suite Email Settings API Email Language Tags. */
		primaryLanguage: Option[String] = None,
	  /** Specifies who can approve members who ask to join groups. This permission will be deprecated once it is merged into the new whoCanModerateMembers setting. Possible values are:  
	- ALL_MEMBERS_CAN_APPROVE 
	- ALL_MANAGERS_CAN_APPROVE 
	- ALL_OWNERS_CAN_APPROVE 
	- NONE_CAN_APPROVE */
		whoCanApproveMembers: Option[String] = None,
	  /** When a message is rejected, this is text for the rejection notification sent to the message's author. By default, this property is empty and has no value in the API's response body. The maximum notification text size is 10,000 characters. Note: Requires sendMessageDenyNotification property to be true. */
		defaultMessageDenyNotificationText: Option[String] = None,
	  /** Allows a member to be notified if the member's message to the group is denied by the group owner. Possible values are:  
	- true: When a message is rejected, send the deny message notification to the message author.
	The defaultMessageDenyNotificationText property is dependent on the sendMessageDenyNotification property being true.
	 
	- false: When a message is rejected, no notification is sent. */
		sendMessageDenyNotification: Option[String] = None,
	  /** Deprecated. The maximum size of a message is 25Mb. */
		maxMessageBytes: Option[Int] = None,
	  /** Name of the group, which has a maximum size of 75 characters. */
		name: Option[String] = None,
	  /** Specifies moderation levels for messages detected as spam. Possible values are:  
	- ALLOW: Post the message to the group. 
	- MODERATE: Send the message to the moderation queue. This is the default. 
	- SILENTLY_MODERATE: Send the message to the moderation queue, but do not send notification to moderators. 
	- REJECT: Immediately reject the message. */
		spamModerationLevel: Option[String] = None,
	  /** An email address used when replying to a message if the replyTo property is set to REPLY_TO_CUSTOM. This address is defined by an account administrator.  
	- When the group's ReplyTo property is set to REPLY_TO_CUSTOM, the customReplyTo property holds a custom email address used when replying to a message. 
	- If the group's ReplyTo property is set to REPLY_TO_CUSTOM, the customReplyTo property must have a text value or an error is returned. */
		customReplyTo: Option[String] = None,
	  /** Description of the group. This property value may be an empty string if no group description has been entered. If entered, the maximum group description is no more than 300 characters. */
		description: Option[String] = None,
	  /** Specifies who receives the default reply. Possible values are:  
	- REPLY_TO_CUSTOM: For replies to messages, use the group's custom email address.
	When the group's ReplyTo property is set to REPLY_TO_CUSTOM, the customReplyTo property holds the custom email address used when replying to a message. If the group's ReplyTo property is set to REPLY_TO_CUSTOM, the customReplyTo property must have a value. Otherwise an error is returned.
	 
	- REPLY_TO_SENDER: The reply sent to author of message. 
	- REPLY_TO_LIST: This reply message is sent to the group. 
	- REPLY_TO_OWNER: The reply is sent to the owner(s) of the group. This does not include the group's managers. 
	- REPLY_TO_IGNORE: Group users individually decide where the message reply is sent. 
	- REPLY_TO_MANAGERS: This reply message is sent to the group's managers, which includes all managers and the group owner. */
		replyTo: Option[String] = None,
	  /** Permissions to post messages. Possible values are:  
	- NONE_CAN_POST: The group is disabled and archived. No one can post a message to this group.  
	- When archiveOnly is false, updating whoCanPostMessage to NONE_CAN_POST, results in an error. 
	- If archiveOnly is reverted from true to false, whoCanPostMessages is set to ALL_MANAGERS_CAN_POST.  
	- ALL_MANAGERS_CAN_POST: Managers, including group owners, can post messages. 
	- ALL_MEMBERS_CAN_POST: Any group member can post a message. 
	- ALL_OWNERS_CAN_POST: Only group owners can post a message. 
	- ALL_IN_DOMAIN_CAN_POST: Anyone in the account can post a message.  
	- ANYONE_CAN_POST: Any Internet user who outside your account can access your Google Groups service and post a message. Note: When whoCanPostMessage is set to ANYONE_CAN_POST, we recommend the messageModerationLevel be set to MODERATE_NON_MEMBERS to protect the group from possible spam. */
		whoCanPostMessage: Option[String] = None,
	  /** Indicates if favorite replies should be displayed above other replies.  
	- true: Favorite replies will be displayed above other replies. 
	- false: Favorite replies will not be displayed above other replies. */
		favoriteRepliesOnTop: Option[String] = None,
	  /** Deprecated. This is merged into the new whoCanModerateContent setting. Specifies who can delete topics. Possible values are:  
	- ALL_MEMBERS 
	- OWNERS_AND_MANAGERS 
	- OWNERS_ONLY 
	- NONE */
		whoCanDeleteTopics: Option[String] = None,
	  /** Deprecated. This is merged into the new whoCanModerateContent setting. Specifies who can post announcements, a special topic type. Possible values are:  
	- ALL_MEMBERS 
	- OWNERS_AND_MANAGERS 
	- OWNERS_ONLY 
	- NONE */
		whoCanPostAnnouncements: Option[String] = None,
	  /** Permission to contact owner of the group via web UI. Possible values are:  
	- ALL_IN_DOMAIN_CAN_CONTACT 
	- ALL_MANAGERS_CAN_CONTACT 
	- ALL_MEMBERS_CAN_CONTACT 
	- ANYONE_CAN_CONTACT 
	- ALL_OWNERS_CAN_CONTACT */
		whoCanContactOwner: Option[String] = None,
	  /** Specifies who can deny membership to users. This permission will be deprecated once it is merged into the new whoCanModerateMembers setting. Possible values are:  
	- ALL_MEMBERS 
	- OWNERS_AND_MANAGERS 
	- OWNERS_ONLY 
	- NONE */
		whoCanBanUsers: Option[String] = None,
	  /** Deprecated. This is merged into the new whoCanDiscoverGroup setting. Allows the group to be visible in the Groups Directory. Possible values are:  
	- true: All groups in the account are listed in the Groups directory. 
	- false: All groups in the account are not listed in the directory. */
		showInGroupDirectory: Option[String] = None,
	  /** Enables the group to be included in the Global Address List. For more information, see the help center. Possible values are:  
	- true: Group is included in the Global Address List. 
	- false: Group is not included in the Global Address List. */
		includeInGlobalAddressList: Option[String] = None,
	  /** Enables members to post messages as the group. Possible values are:  
	- true: Group member can post messages using the group's email address instead of their own email address. Message appear to originate from the group itself. Note: When true, any message moderation settings on individual users or new members do not apply to posts made on behalf of the group. 
	- false: Members can not post in behalf of the group's email address. */
		membersCanPostAsTheGroup: Option[String] = None,
	  /** The type of the resource. It is always groupsSettings#groups. */
		kind: Option[String] = Some("""groupsSettings#groups"""),
	  /** Deprecated. This is merged into the new whoCanModerateContent setting. Specifies who can prevent users from posting replies to topics. Possible values are:  
	- ALL_MEMBERS 
	- OWNERS_AND_MANAGERS 
	- OWNERS_ONLY 
	- NONE */
		whoCanLockTopics: Option[String] = None,
	  /** Permissions to view group messages. Possible values are:  
	- ANYONE_CAN_VIEW: Any Internet user can view the group's messages.  
	- ALL_IN_DOMAIN_CAN_VIEW: Anyone in your account can view this group's messages. 
	- ALL_MEMBERS_CAN_VIEW: All group members can view the group's messages. 
	- ALL_MANAGERS_CAN_VIEW: Any group manager can view this group's messages. */
		whoCanViewGroup: Option[String] = None,
	  /** Deprecated. This is merged into the new whoCanModerateContent setting. Specifies who can hide posts by reporting them as abuse. Possible values are:  
	- ALL_MEMBERS 
	- OWNERS_AND_MANAGERS 
	- OWNERS_ONLY 
	- NONE */
		whoCanHideAbuse: Option[String] = None,
	  /** Specifies who can moderate metadata. Possible values are:  
	- ALL_MEMBERS 
	- OWNERS_AND_MANAGERS 
	- MANAGERS_ONLY 
	- OWNERS_ONLY 
	- NONE */
		whoCanAssistContent: Option[String] = None,
	  /** Deprecated. This is merged into the new whoCanAssistContent setting. Permission to change tags and categories. Possible values are:  
	- ALL_MEMBERS 
	- OWNERS_AND_MANAGERS 
	- MANAGERS_ONLY 
	- OWNERS_ONLY 
	- NONE */
		whoCanModifyTagsAndCategories: Option[String] = None,
	  /** Deprecated. This is merged into the new whoCanAssistContent setting. Permission to unmark any post from a favorite reply. Possible values are:  
	- ALL_MEMBERS 
	- OWNERS_AND_MANAGERS 
	- MANAGERS_ONLY 
	- OWNERS_ONLY 
	- NONE */
		whoCanUnmarkFavoriteReplyOnAnyTopic: Option[String] = None,
	  /** Deprecated. This is merged into the new whoCanModerateContent setting. Specifies who can delete replies to topics. (Authors can always delete their own posts). Possible values are:  
	- ALL_MEMBERS 
	- OWNERS_AND_MANAGERS 
	- OWNERS_ONLY 
	- NONE */
		whoCanDeleteAnyPost: Option[String] = None,
	  /** Identifies whether members external to your organization can join the group. Possible values are:  
	- true: G Suite users external to your organization can become members of this group. 
	- false: Users not belonging to the organization are not allowed to become members of this group. */
		allowExternalMembers: Option[String] = None,
	  /** Permission to leave the group. Possible values are:  
	- ALL_MANAGERS_CAN_LEAVE 
	- ALL_MEMBERS_CAN_LEAVE 
	- NONE_CAN_LEAVE */
		whoCanLeaveGroup: Option[String] = None,
	  /** Deprecated. This is merged into the new whoCanModerateMembers setting. Permissions to add members. Possible values are:  
	- ALL_MEMBERS_CAN_ADD: Managers and members can directly add new members. 
	- ALL_MANAGERS_CAN_ADD: Only managers can directly add new members. this includes the group's owner. 
	- ALL_OWNERS_CAN_ADD: Only owners can directly add new members. 
	- NONE_CAN_ADD: No one can directly add new members. */
		whoCanAdd: Option[String] = None,
	  /** Moderation level of incoming messages. Possible values are:  
	- MODERATE_ALL_MESSAGES: All messages are sent to the group owner's email address for approval. If approved, the message is sent to the group. 
	- MODERATE_NON_MEMBERS: All messages from non group members are sent to the group owner's email address for approval. If approved, the message is sent to the group. 
	- MODERATE_NEW_MEMBERS: All messages from new members are sent to the group owner's email address for approval. If approved, the message is sent to the group. 
	- MODERATE_NONE: No moderator approval is required. Messages are delivered directly to the group. Note: When the whoCanPostMessage is set to ANYONE_CAN_POST, we recommend the messageModerationLevel be set to MODERATE_NON_MEMBERS to protect the group from possible spam.
	When memberCanPostAsTheGroup is true, any message moderation settings on individual users or new members will not apply to posts made on behalf of the group. */
		messageModerationLevel: Option[String] = None,
	  /** Specifies whether the group has a custom role that's included in one of the settings being merged. This field is read-only and update/patch requests to it are ignored. Possible values are:  
	- true 
	- false */
		customRolesEnabledForSettingsToBeMerged: Option[String] = None,
	  /** Deprecated. This is merged into the new whoCanModerateContent setting. Specifies who can move topics into the group or forum. Possible values are:  
	- ALL_MEMBERS 
	- OWNERS_AND_MANAGERS 
	- OWNERS_ONLY 
	- NONE */
		whoCanMoveTopicsIn: Option[String] = None,
	  /** Deprecated. This is merged into the new whoCanAssistContent setting. Permission to mark a topic as not needing a response. Possible values are:  
	- ALL_MEMBERS 
	- OWNERS_AND_MANAGERS 
	- MANAGERS_ONLY 
	- OWNERS_ONLY 
	- NONE */
		whoCanMarkNoResponseNeeded: Option[String] = None,
	  /** Deprecated. This is merged into the new whoCanAssistContent setting. Permission to assign topics in a forum to another user. Possible values are:  
	- ALL_MEMBERS 
	- OWNERS_AND_MANAGERS 
	- MANAGERS_ONLY 
	- OWNERS_ONLY 
	- NONE */
		whoCanAssignTopics: Option[String] = None,
	  /** Permission to join group. Possible values are:  
	- ANYONE_CAN_JOIN: Any Internet user who is outside your domain can access your Google Groups service and view the list of groups in your Groups directory. Warning: Group owners can add external addresses, outside of the domain to their groups. They can also allow people outside your domain to join their groups. If you later disable this option, any external addresses already added to users' groups remain in those groups. 
	- ALL_IN_DOMAIN_CAN_JOIN: Anyone in the account domain can join. This includes accounts with multiple domains. 
	- INVITED_CAN_JOIN: Candidates for membership can be invited to join.  
	- CAN_REQUEST_TO_JOIN: Non members can request an invitation to join. */
		whoCanJoin: Option[String] = None,
	  /** Default sender for members who can post messages as the group. Possible values are: - `DEFAULT_SELF`: By default messages will be sent from the user - `GROUP`: By default messages will be sent from the group */
		default_sender: Option[String] = None,
	  /** Set the content of custom footer text. The maximum number of characters is 1,000. */
		customFooterText: Option[String] = None,
	  /** Deprecated. This is merged into the new whoCanModerateContent setting. Specifies who can approve pending messages in the moderation queue. Possible values are:  
	- ALL_MEMBERS 
	- OWNERS_AND_MANAGERS 
	- OWNERS_ONLY 
	- NONE */
		whoCanApproveMessages: Option[String] = None,
	  /** Specifies the set of users for whom this group is discoverable. Possible values are:  
	- ANYONE_CAN_DISCOVER 
	- ALL_IN_DOMAIN_CAN_DISCOVER 
	- ALL_MEMBERS_CAN_DISCOVER */
		whoCanDiscoverGroup: Option[String] = None,
	  /** Deprecated. This is merged into the new whoCanModerateContent setting. Specifies who can make topics appear at the top of the topic list. Possible values are:  
	- ALL_MEMBERS 
	- OWNERS_AND_MANAGERS 
	- OWNERS_ONLY 
	- NONE */
		whoCanMakeTopicsSticky: Option[String] = None,
	  /** Allows posting from web. Possible values are:  
	- true: Allows any member to post to the group forum. 
	- false: Members only use Gmail to communicate with the group. */
		allowWebPosting: Option[String] = None,
	  /** Deprecated. This is merged into the new whoCanModerateContent setting. Specifies who can move topics out of the group or forum. Possible values are:  
	- ALL_MEMBERS 
	- OWNERS_AND_MANAGERS 
	- OWNERS_ONLY 
	- NONE */
		whoCanMoveTopicsOut: Option[String] = None,
	  /** Permissions to view membership. Possible values are:  
	- ALL_IN_DOMAIN_CAN_VIEW: Anyone in the account can view the group members list.
	If a group already has external members, those members can still send email to this group.
	 
	- ALL_MEMBERS_CAN_VIEW: The group members can view the group members list. 
	- ALL_MANAGERS_CAN_VIEW: The group managers can view group members list. */
		whoCanViewMembership: Option[String] = None,
	  /** Deprecated. This is merged into the new whoCanAssistContent setting. Permission to enter free form tags for topics in a forum. Possible values are:  
	- ALL_MEMBERS 
	- OWNERS_AND_MANAGERS 
	- MANAGERS_ONLY 
	- OWNERS_ONLY 
	- NONE */
		whoCanEnterFreeFormTags: Option[String] = None,
	  /** Deprecated. This is merged into the new whoCanModerateMembers setting. Permissions to invite new members. Possible values are:  
	- ALL_MEMBERS_CAN_INVITE: Managers and members can invite a new member candidate. 
	- ALL_MANAGERS_CAN_INVITE: Only managers can invite a new member. This includes the group's owner. 
	- ALL_OWNERS_CAN_INVITE: Only owners can invite a new member. 
	- NONE_CAN_INVITE: No one can invite a new member candidate. */
		whoCanInvite: Option[String] = None,
	  /** Deprecated. Allows Google to contact administrator of the group.  
	- true: Allow Google to contact managers of this group. Occasionally Google may send updates on the latest features, ask for input on new features, or ask for permission to highlight your group. 
	- false: Google can not contact managers of this group. */
		allowGoogleCommunication: Option[String] = None,
	  /** Deprecated. This is merged into the new whoCanAssistContent setting. Permission to mark a post for a topic they started as a favorite reply. Possible values are:  
	- ALL_MEMBERS 
	- OWNERS_AND_MANAGERS 
	- MANAGERS_ONLY 
	- OWNERS_ONLY 
	- NONE */
		whoCanMarkFavoriteReplyOnOwnTopic: Option[String] = None,
	  /** Deprecated. This is merged into the new whoCanAssistContent setting. Permission to unassign any topic in a forum. Possible values are:  
	- ALL_MEMBERS 
	- OWNERS_AND_MANAGERS 
	- MANAGERS_ONLY 
	- OWNERS_ONLY 
	- NONE */
		whoCanUnassignTopic: Option[String] = None,
	  /** Deprecated. This is merged into the new whoCanAssistContent setting. Permission to mark any other user's post as a favorite reply. Possible values are:  
	- ALL_MEMBERS 
	- OWNERS_AND_MANAGERS 
	- MANAGERS_ONLY 
	- OWNERS_ONLY 
	- NONE */
		whoCanMarkFavoriteReplyOnAnyTopic: Option[String] = None,
	  /** Specifies who can manage members. Possible values are:  
	- ALL_MEMBERS 
	- OWNERS_AND_MANAGERS 
	- OWNERS_ONLY 
	- NONE */
		whoCanModerateMembers: Option[String] = None,
	  /** Deprecated. This is merged into the new whoCanModerateMembers setting. Specifies who can change group members' roles. Possible values are:  
	- ALL_MEMBERS 
	- OWNERS_AND_MANAGERS 
	- OWNERS_ONLY 
	- NONE */
		whoCanModifyMembers: Option[String] = None,
	  /** Whether to include custom footer. Possible values are:  
	- true 
	- false */
		includeCustomFooter: Option[String] = None,
	  /** Deprecated. This is merged into the new whoCanAssistContent setting. Permission to take topics in a forum. Possible values are:  
	- ALL_MEMBERS 
	- OWNERS_AND_MANAGERS 
	- MANAGERS_ONLY 
	- OWNERS_ONLY 
	- NONE */
		whoCanTakeTopics: Option[String] = None,
	  /** Deprecated. This functionality is no longer supported in the Google Groups UI. The value is always "NONE". */
		whoCanAddReferences: Option[String] = None
	)
}
