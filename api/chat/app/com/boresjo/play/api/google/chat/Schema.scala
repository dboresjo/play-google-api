package com.boresjo.play.api.google.chat

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	case class Media(
	  /** Name of the media resource. */
		resourceName: Option[String] = None
	)
	
	case class Message(
	  /** Identifier. Resource name of the message. Format: `spaces/{space}/messages/{message}` Where `{space}` is the ID of the space where the message is posted and `{message}` is a system-assigned ID for the message. For example, `spaces/AAAAAAAAAAA/messages/BBBBBBBBBBB.BBBBBBBBBBB`. If you set a custom ID when you create a message, you can use this ID to specify the message in a request by replacing `{message}` with the value from the `clientAssignedMessageId` field. For example, `spaces/AAAAAAAAAAA/messages/client-custom-name`. For details, see [Name a message](https://developers.google.com/workspace/chat/create-messages#name_a_created_message). */
		name: Option[String] = None,
	  /** Output only. The user who created the message. If your Chat app [authenticates as a user](https://developers.google.com/workspace/chat/authenticate-authorize-chat-user), the output populates the [user](https://developers.google.com/workspace/chat/api/reference/rest/v1/User) `name` and `type`. */
		sender: Option[Schema.User] = None,
	  /** Optional. Immutable. For spaces created in Chat, the time at which the message was created. This field is output only, except when used in import mode spaces. For import mode spaces, set this field to the historical timestamp at which the message was created in the source in order to preserve the original creation time. */
		createTime: Option[String] = None,
	  /** Output only. The time at which the message was last edited by a user. If the message has never been edited, this field is empty. */
		lastUpdateTime: Option[String] = None,
	  /** Output only. The time at which the message was deleted in Google Chat. If the message is never deleted, this field is empty. */
		deleteTime: Option[String] = None,
	  /** Optional. Plain-text body of the message. The first link to an image, video, or web page generates a [preview chip](https://developers.google.com/workspace/chat/preview-links). You can also [@mention a Google Chat user](https://developers.google.com/workspace/chat/format-messages#messages-@mention), or everyone in the space. To learn about creating text messages, see [Send a message](https://developers.google.com/workspace/chat/create-messages). */
		text: Option[String] = None,
	  /** Output only. Contains the message `text` with markups added to communicate formatting. This field might not capture all formatting visible in the UI, but includes the following: &#42; [Markup syntax](https://developers.google.com/workspace/chat/format-messages) for bold, italic, strikethrough, monospace, monospace block, and bulleted list. &#42; [User mentions](https://developers.google.com/workspace/chat/format-messages#messages-@mention) using the format ``. &#42; Custom hyperlinks using the format `<{url}|{rendered_text}>` where the first string is the URL and the second is the rendered text—for example, ``. &#42; Custom emoji using the format `:{emoji_name}:`—for example, `:smile:`. This doesn't apply to Unicode emoji, such as `U+1F600` for a grinning face emoji. For more information, see [View text formatting sent in a message](https://developers.google.com/workspace/chat/format-messages#view_text_formatting_sent_in_a_message) */
		formattedText: Option[String] = None,
	  /** Deprecated: Use `cards_v2` instead. Rich, formatted, and interactive cards that you can use to display UI elements such as: formatted texts, buttons, and clickable images. Cards are normally displayed below the plain-text body of the message. `cards` and `cards_v2` can have a maximum size of 32 KB. */
		cards: Option[List[Schema.Card]] = None,
	  /** Optional. An array of [cards](https://developers.google.com/workspace/chat/api/reference/rest/v1/cards). Only Chat apps can create cards. If your Chat app [authenticates as a user](https://developers.google.com/workspace/chat/authenticate-authorize-chat-user), the messages can't contain cards. To learn how to create a message that contains cards, see [Send a message](https://developers.google.com/workspace/chat/create-messages). [Card builder](https://addons.gsuite.google.com/uikit/builder) */
		cardsV2: Option[List[Schema.CardWithId]] = None,
	  /** Output only. Annotations associated with the `text` in this message. */
		annotations: Option[List[Schema.Annotation]] = None,
	  /** The thread the message belongs to. For example usage, see [Start or reply to a message thread](https://developers.google.com/workspace/chat/create-messages#create-message-thread). */
		thread: Option[Schema.Thread] = None,
	  /** Output only. If your Chat app [authenticates as a user](https://developers.google.com/workspace/chat/authenticate-authorize-chat-user), the output only populates the [space](https://developers.google.com/workspace/chat/api/reference/rest/v1/spaces) `name`. */
		space: Option[Schema.Space] = None,
	  /** Optional. A plain-text description of the message's cards, used when the actual cards can't be displayed—for example, mobile notifications. */
		fallbackText: Option[String] = None,
	  /** Input only. Parameters that a Chat app can use to configure how its response is posted. */
		actionResponse: Option[Schema.ActionResponse] = None,
	  /** Output only. Plain-text body of the message with all Chat app mentions stripped out. */
		argumentText: Option[String] = None,
	  /** Output only. Slash command information, if applicable. */
		slashCommand: Option[Schema.SlashCommand] = None,
	  /** Optional. User-uploaded attachment. */
		attachment: Option[List[Schema.Attachment]] = None,
	  /** Output only. A URL in `spaces.messages.text` that matches a link preview pattern. For more information, see [Preview links](https://developers.google.com/workspace/chat/preview-links). */
		matchedUrl: Option[Schema.MatchedUrl] = None,
	  /** Output only. When `true`, the message is a response in a reply thread. When `false`, the message is visible in the space's top-level conversation as either the first message of a thread or a message with no threaded replies. If the space doesn't support reply in threads, this field is always `false`. */
		threadReply: Option[Boolean] = None,
	  /** Optional. A custom ID for the message. You can use field to identify a message, or to get, delete, or update a message. To set a custom ID, specify the [`messageId`](https://developers.google.com/workspace/chat/api/reference/rest/v1/spaces.messages/create#body.QUERY_PARAMETERS.message_id) field when you create the message. For details, see [Name a message](https://developers.google.com/workspace/chat/create-messages#name_a_created_message). */
		clientAssignedMessageId: Option[String] = None,
	  /** Output only. The list of emoji reaction summaries on the message. */
		emojiReactionSummaries: Option[List[Schema.EmojiReactionSummary]] = None,
	  /** Optional. Immutable. Input for creating a message, otherwise output only. The user that can view the message. When set, the message is private and only visible to the specified user and the Chat app. To include this field in your request, you must call the Chat API using [app authentication](https://developers.google.com/workspace/chat/authenticate-authorize-chat-app) and omit the following: &#42; [Attachments](https://developers.google.com/workspace/chat/api/reference/rest/v1/spaces.messages.attachments) &#42; [Accessory widgets](https://developers.google.com/workspace/chat/api/reference/rest/v1/spaces.messages#Message.AccessoryWidget) For details, see [Send a message privately](https://developers.google.com/workspace/chat/create-messages#private). */
		privateMessageViewer: Option[Schema.User] = None,
	  /** Output only. Information about a deleted message. A message is deleted when `delete_time` is set. */
		deletionMetadata: Option[Schema.DeletionMetadata] = None,
	  /** Output only. Information about a message that's quoted by a Google Chat user in a space. Google Chat users can quote a message to reply to it. */
		quotedMessageMetadata: Option[Schema.QuotedMessageMetadata] = None,
	  /** Output only. GIF images that are attached to the message. */
		attachedGifs: Option[List[Schema.AttachedGif]] = None,
	  /** Optional. One or more interactive widgets that appear at the bottom of a message. You can add accessory widgets to messages that contain text, cards, or both text and cards. Not supported for messages that contain dialogs. For details, see [Add interactive widgets at the bottom of a message](https://developers.google.com/workspace/chat/create-messages#add-accessory-widgets). Creating a message with accessory widgets requires [app authentication] (https://developers.google.com/workspace/chat/authenticate-authorize-chat-app). */
		accessoryWidgets: Option[List[Schema.AccessoryWidget]] = None
	)
	
	object User {
		enum TypeEnum extends Enum[TypeEnum] { case TYPE_UNSPECIFIED, HUMAN, BOT }
	}
	case class User(
	  /** Resource name for a Google Chat user. Format: `users/{user}`. `users/app` can be used as an alias for the calling app bot user. For human users, `{user}` is the same user identifier as: - the `id` for the [Person](https://developers.google.com/people/api/rest/v1/people) in the People API. For example, `users/123456789` in Chat API represents the same person as the `123456789` Person profile ID in People API. - the `id` for a [user](https://developers.google.com/admin-sdk/directory/reference/rest/v1/users) in the Admin SDK Directory API. - the user's email address can be used as an alias for `{user}` in API requests. For example, if the People API Person profile ID for `user@example.com` is `123456789`, you can use `users/user@example.com` as an alias to reference `users/123456789`. Only the canonical resource name (for example `users/123456789`) will be returned from the API. */
		name: Option[String] = None,
	  /** Output only. The user's display name. */
		displayName: Option[String] = None,
	  /** Unique identifier of the user's Google Workspace domain. */
		domainId: Option[String] = None,
	  /** User type. */
		`type`: Option[Schema.User.TypeEnum] = None,
	  /** Output only. When `true`, the user is deleted or their profile is not visible. */
		isAnonymous: Option[Boolean] = None
	)
	
	case class Card(
	  /** The header of the card. A header usually contains a title and an image. */
		header: Option[Schema.CardHeader] = None,
	  /** Sections are separated by a line divider. */
		sections: Option[List[Schema.Section]] = None,
	  /** The actions of this card. */
		cardActions: Option[List[Schema.CardAction]] = None,
	  /** Name of the card. */
		name: Option[String] = None
	)
	
	object CardHeader {
		enum ImageStyleEnum extends Enum[ImageStyleEnum] { case IMAGE_STYLE_UNSPECIFIED, IMAGE, AVATAR }
	}
	case class CardHeader(
	  /** The title must be specified. The header has a fixed height: if both a title and subtitle is specified, each takes up one line. If only the title is specified, it takes up both lines. */
		title: Option[String] = None,
	  /** The subtitle of the card header. */
		subtitle: Option[String] = None,
	  /** The image's type (for example, square border or circular border). */
		imageStyle: Option[Schema.CardHeader.ImageStyleEnum] = None,
	  /** The URL of the image in the card header. */
		imageUrl: Option[String] = None
	)
	
	case class Section(
	  /** The header of the section. Formatted text is supported. For more information about formatting text, see [Formatting text in Google Chat apps](https://developers.google.com/workspace/chat/format-messages#card-formatting) and [Formatting text in Google Workspace Add-ons](https://developers.google.com/apps-script/add-ons/concepts/widgets#text_formatting). */
		header: Option[String] = None,
	  /** A section must contain at least one widget. */
		widgets: Option[List[Schema.WidgetMarkup]] = None
	)
	
	case class WidgetMarkup(
	  /** Display a text paragraph in this widget. */
		textParagraph: Option[Schema.TextParagraph] = None,
	  /** Display an image in this widget. */
		image: Option[Schema.Image] = None,
	  /** Display a key value item in this widget. */
		keyValue: Option[Schema.KeyValue] = None,
	  /** A list of buttons. Buttons is also `oneof data` and only one of these fields should be set. */
		buttons: Option[List[Schema.Button]] = None
	)
	
	case class TextParagraph(
		text: Option[String] = None
	)
	
	case class Image(
	  /** The URL of the image. */
		imageUrl: Option[String] = None,
	  /** The `onclick` action. */
		onClick: Option[Schema.OnClick] = None,
	  /** The aspect ratio of this image (width and height). This field lets you reserve the right height for the image while waiting for it to load. It's not meant to override the built-in aspect ratio of the image. If unset, the server fills it by prefetching the image. */
		aspectRatio: Option[BigDecimal] = None
	)
	
	case class OnClick(
	  /** A form action is triggered by this `onclick` action if specified. */
		action: Option[Schema.FormAction] = None,
	  /** This `onclick` action triggers an open link action if specified. */
		openLink: Option[Schema.OpenLink] = None
	)
	
	case class FormAction(
	  /** The method name is used to identify which part of the form triggered the form submission. This information is echoed back to the Chat app as part of the card click event. You can use the same method name for several elements that trigger a common behavior. */
		actionMethodName: Option[String] = None,
	  /** List of action parameters. */
		parameters: Option[List[Schema.ActionParameter]] = None
	)
	
	case class ActionParameter(
	  /** The name of the parameter for the action script. */
		key: Option[String] = None,
	  /** The value of the parameter. */
		value: Option[String] = None
	)
	
	case class OpenLink(
	  /** The URL to open. */
		url: Option[String] = None
	)
	
	object KeyValue {
		enum IconEnum extends Enum[IconEnum] { case ICON_UNSPECIFIED, AIRPLANE, BOOKMARK, BUS, CAR, CLOCK, CONFIRMATION_NUMBER_ICON, DOLLAR, DESCRIPTION, EMAIL, EVENT_PERFORMER, EVENT_SEAT, FLIGHT_ARRIVAL, FLIGHT_DEPARTURE, HOTEL, HOTEL_ROOM_TYPE, INVITE, MAP_PIN, MEMBERSHIP, MULTIPLE_PEOPLE, OFFER, PERSON, PHONE, RESTAURANT_ICON, SHOPPING_CART, STAR, STORE, TICKET, TRAIN, VIDEO_CAMERA, VIDEO_PLAY }
	}
	case class KeyValue(
	  /** An enum value that's replaced by the Chat API with the corresponding icon image. */
		icon: Option[Schema.KeyValue.IconEnum] = None,
	  /** The icon specified by a URL. */
		iconUrl: Option[String] = None,
	  /** The text of the top label. Formatted text supported. For more information about formatting text, see [Formatting text in Google Chat apps](https://developers.google.com/workspace/chat/format-messages#card-formatting) and [Formatting text in Google Workspace Add-ons](https://developers.google.com/apps-script/add-ons/concepts/widgets#text_formatting). */
		topLabel: Option[String] = None,
	  /** The text of the content. Formatted text supported and always required. For more information about formatting text, see [Formatting text in Google Chat apps](https://developers.google.com/workspace/chat/format-messages#card-formatting) and [Formatting text in Google Workspace Add-ons](https://developers.google.com/apps-script/add-ons/concepts/widgets#text_formatting). */
		content: Option[String] = None,
	  /** If the content should be multiline. */
		contentMultiline: Option[Boolean] = None,
	  /** The text of the bottom label. Formatted text supported. For more information about formatting text, see [Formatting text in Google Chat apps](https://developers.google.com/workspace/chat/format-messages#card-formatting) and [Formatting text in Google Workspace Add-ons](https://developers.google.com/apps-script/add-ons/concepts/widgets#text_formatting). */
		bottomLabel: Option[String] = None,
	  /** The `onclick` action. Only the top label, bottom label, and content region are clickable. */
		onClick: Option[Schema.OnClick] = None,
	  /** A button that can be clicked to trigger an action. */
		button: Option[Schema.Button] = None
	)
	
	case class Button(
	  /** A button with text and `onclick` action. */
		textButton: Option[Schema.TextButton] = None,
	  /** A button with image and `onclick` action. */
		imageButton: Option[Schema.ImageButton] = None
	)
	
	case class TextButton(
	  /** The text of the button. */
		text: Option[String] = None,
	  /** The `onclick` action of the button. */
		onClick: Option[Schema.OnClick] = None
	)
	
	object ImageButton {
		enum IconEnum extends Enum[IconEnum] { case ICON_UNSPECIFIED, AIRPLANE, BOOKMARK, BUS, CAR, CLOCK, CONFIRMATION_NUMBER_ICON, DOLLAR, DESCRIPTION, EMAIL, EVENT_PERFORMER, EVENT_SEAT, FLIGHT_ARRIVAL, FLIGHT_DEPARTURE, HOTEL, HOTEL_ROOM_TYPE, INVITE, MAP_PIN, MEMBERSHIP, MULTIPLE_PEOPLE, OFFER, PERSON, PHONE, RESTAURANT_ICON, SHOPPING_CART, STAR, STORE, TICKET, TRAIN, VIDEO_CAMERA, VIDEO_PLAY }
	}
	case class ImageButton(
	  /** The icon specified by an `enum` that indices to an icon provided by Chat API. */
		icon: Option[Schema.ImageButton.IconEnum] = None,
	  /** The icon specified by a URL. */
		iconUrl: Option[String] = None,
	  /** The `onclick` action. */
		onClick: Option[Schema.OnClick] = None,
	  /** The name of this `image_button` that's used for accessibility. Default value is provided if this name isn't specified. */
		name: Option[String] = None
	)
	
	case class CardAction(
	  /** The label used to be displayed in the action menu item. */
		actionLabel: Option[String] = None,
	  /** The onclick action for this action item. */
		onClick: Option[Schema.OnClick] = None
	)
	
	case class CardWithId(
	  /** Required if the message contains multiple cards. A unique identifier for a card in a message. */
		cardId: Option[String] = None,
	  /** A card. Maximum size is 32 KB. */
		card: Option[Schema.GoogleAppsCardV1Card] = None
	)
	
	object GoogleAppsCardV1Card {
		enum SectionDividerStyleEnum extends Enum[SectionDividerStyleEnum] { case DIVIDER_STYLE_UNSPECIFIED, SOLID_DIVIDER, NO_DIVIDER }
		enum DisplayStyleEnum extends Enum[DisplayStyleEnum] { case DISPLAY_STYLE_UNSPECIFIED, PEEK, REPLACE }
	}
	case class GoogleAppsCardV1Card(
	  /** The header of the card. A header usually contains a leading image and a title. Headers always appear at the top of a card. */
		header: Option[Schema.GoogleAppsCardV1CardHeader] = None,
	  /** Contains a collection of widgets. Each section has its own, optional header. Sections are visually separated by a line divider. For an example in Google Chat apps, see [Define a section of a card](https://developers.google.com/workspace/chat/design-components-card-dialog#define_a_section_of_a_card). */
		sections: Option[List[Schema.GoogleAppsCardV1Section]] = None,
	  /** The divider style between the header, sections and footer. */
		sectionDividerStyle: Option[Schema.GoogleAppsCardV1Card.SectionDividerStyleEnum] = None,
	  /** The card's actions. Actions are added to the card's toolbar menu. [Google Workspace Add-ons](https://developers.google.com/workspace/add-ons): For example, the following JSON constructs a card action menu with `Settings` and `Send Feedback` options: ``` "card_actions": [ { "actionLabel": "Settings", "onClick": { "action": { "functionName": "goToView", "parameters": [ { "key": "viewType", "value": "SETTING" } ], "loadIndicator": "LoadIndicator.SPINNER" } } }, { "actionLabel": "Send Feedback", "onClick": { "openLink": { "url": "https://example.com/feedback" } } } ] ``` */
		cardActions: Option[List[Schema.GoogleAppsCardV1CardAction]] = None,
	  /** Name of the card. Used as a card identifier in card navigation. [Google Workspace Add-ons](https://developers.google.com/workspace/add-ons): */
		name: Option[String] = None,
	  /** The fixed footer shown at the bottom of this card. Setting `fixedFooter` without specifying a `primaryButton` or a `secondaryButton` causes an error. For Chat apps, you can use fixed footers in [dialogs](https://developers.google.com/workspace/chat/dialogs), but not [card messages](https://developers.google.com/workspace/chat/create-messages#create). [Google Workspace Add-ons and Chat apps](https://developers.google.com/workspace/extend): */
		fixedFooter: Option[Schema.GoogleAppsCardV1CardFixedFooter] = None,
	  /** In Google Workspace Add-ons, sets the display properties of the `peekCardHeader`. [Google Workspace Add-ons](https://developers.google.com/workspace/add-ons): */
		displayStyle: Option[Schema.GoogleAppsCardV1Card.DisplayStyleEnum] = None,
	  /** When displaying contextual content, the peek card header acts as a placeholder so that the user can navigate forward between the homepage cards and the contextual cards. [Google Workspace Add-ons](https://developers.google.com/workspace/add-ons): */
		peekCardHeader: Option[Schema.GoogleAppsCardV1CardHeader] = None
	)
	
	object GoogleAppsCardV1CardHeader {
		enum ImageTypeEnum extends Enum[ImageTypeEnum] { case SQUARE, CIRCLE }
	}
	case class GoogleAppsCardV1CardHeader(
	  /** Required. The title of the card header. The header has a fixed height: if both a title and subtitle are specified, each takes up one line. If only the title is specified, it takes up both lines. */
		title: Option[String] = None,
	  /** The subtitle of the card header. If specified, appears on its own line below the `title`. */
		subtitle: Option[String] = None,
	  /** The shape used to crop the image. [Google Workspace Add-ons and Chat apps](https://developers.google.com/workspace/extend): */
		imageType: Option[Schema.GoogleAppsCardV1CardHeader.ImageTypeEnum] = None,
	  /** The HTTPS URL of the image in the card header. */
		imageUrl: Option[String] = None,
	  /** The alternative text of this image that's used for accessibility. */
		imageAltText: Option[String] = None
	)
	
	case class GoogleAppsCardV1Section(
	  /** Text that appears at the top of a section. Supports simple HTML formatted text. For more information about formatting text, see [Formatting text in Google Chat apps](https://developers.google.com/workspace/chat/format-messages#card-formatting) and [Formatting text in Google Workspace Add-ons](https://developers.google.com/apps-script/add-ons/concepts/widgets#text_formatting). */
		header: Option[String] = None,
	  /** All the widgets in the section. Must contain at least one widget. */
		widgets: Option[List[Schema.GoogleAppsCardV1Widget]] = None,
	  /** Indicates whether this section is collapsible. Collapsible sections hide some or all widgets, but users can expand the section to reveal the hidden widgets by clicking &#42;&#42;Show more&#42;&#42;. Users can hide the widgets again by clicking &#42;&#42;Show less&#42;&#42;. To determine which widgets are hidden, specify `uncollapsibleWidgetsCount`. */
		collapsible: Option[Boolean] = None,
	  /** The number of uncollapsible widgets which remain visible even when a section is collapsed. For example, when a section contains five widgets and the `uncollapsibleWidgetsCount` is set to `2`, the first two widgets are always shown and the last three are collapsed by default. The `uncollapsibleWidgetsCount` is taken into account only when `collapsible` is `true`. */
		uncollapsibleWidgetsCount: Option[Int] = None,
	  /** Optional. Define the expand and collapse button of the section. This button will be shown only if the section is collapsible. If this field isn't set, the default button is used. [Google Chat apps](https://developers.google.com/workspace/chat): */
		collapseControl: Option[Schema.GoogleAppsCardV1CollapseControl] = None
	)
	
	object GoogleAppsCardV1Widget {
		enum HorizontalAlignmentEnum extends Enum[HorizontalAlignmentEnum] { case HORIZONTAL_ALIGNMENT_UNSPECIFIED, START, CENTER, END }
	}
	case class GoogleAppsCardV1Widget(
	  /** Displays a text paragraph. Supports simple HTML formatted text. For more information about formatting text, see [Formatting text in Google Chat apps](https://developers.google.com/workspace/chat/format-messages#card-formatting) and [Formatting text in Google Workspace Add-ons](https://developers.google.com/apps-script/add-ons/concepts/widgets#text_formatting). For example, the following JSON creates a bolded text: ``` "textParagraph": { "text": " &#42;bold text&#42;" } ``` */
		textParagraph: Option[Schema.GoogleAppsCardV1TextParagraph] = None,
	  /** Displays an image. For example, the following JSON creates an image with alternative text: ``` "image": { "imageUrl": "https://developers.google.com/workspace/chat/images/quickstart-app-avatar.png", "altText": "Chat app avatar" } ``` */
		image: Option[Schema.GoogleAppsCardV1Image] = None,
	  /** Displays a decorated text item. For example, the following JSON creates a decorated text widget showing email address: ``` "decoratedText": { "icon": { "knownIcon": "EMAIL" }, "topLabel": "Email Address", "text": "sasha@example.com", "bottomLabel": "This is a new Email address!", "switchControl": { "name": "has_send_welcome_email_to_sasha", "selected": false, "controlType": "CHECKBOX" } } ``` */
		decoratedText: Option[Schema.GoogleAppsCardV1DecoratedText] = None,
	  /** A list of buttons. For example, the following JSON creates two buttons. The first is a blue text button and the second is an image button that opens a link: ``` "buttonList": { "buttons": [ { "text": "Edit", "color": { "red": 0, "green": 0, "blue": 1, }, "disabled": true, }, { "icon": { "knownIcon": "INVITE", "altText": "check calendar" }, "onClick": { "openLink": { "url": "https://example.com/calendar" } } } ] } ``` */
		buttonList: Option[Schema.GoogleAppsCardV1ButtonList] = None,
	  /** Displays a text box that users can type into. For example, the following JSON creates a text input for an email address: ``` "textInput": { "name": "mailing_address", "label": "Mailing Address" } ``` As another example, the following JSON creates a text input for a programming language with static suggestions: ``` "textInput": { "name": "preferred_programing_language", "label": "Preferred Language", "initialSuggestions": { "items": [ { "text": "C++" }, { "text": "Java" }, { "text": "JavaScript" }, { "text": "Python" } ] } } ``` */
		textInput: Option[Schema.GoogleAppsCardV1TextInput] = None,
	  /** Displays a selection control that lets users select items. Selection controls can be checkboxes, radio buttons, switches, or dropdown menus. For example, the following JSON creates a dropdown menu that lets users choose a size: ``` "selectionInput": { "name": "size", "label": "Size" "type": "DROPDOWN", "items": [ { "text": "S", "value": "small", "selected": false }, { "text": "M", "value": "medium", "selected": true }, { "text": "L", "value": "large", "selected": false }, { "text": "XL", "value": "extra_large", "selected": false } ] } ``` */
		selectionInput: Option[Schema.GoogleAppsCardV1SelectionInput] = None,
	  /** Displays a widget that lets users input a date, time, or date and time. For example, the following JSON creates a date time picker to schedule an appointment: ``` "dateTimePicker": { "name": "appointment_time", "label": "Book your appointment at:", "type": "DATE_AND_TIME", "valueMsEpoch": "796435200000" } ``` */
		dateTimePicker: Option[Schema.GoogleAppsCardV1DateTimePicker] = None,
	  /** Displays a horizontal line divider between widgets. For example, the following JSON creates a divider: ``` "divider": { } ``` */
		divider: Option[Schema.GoogleAppsCardV1Divider] = None,
	  /** Displays a grid with a collection of items. A grid supports any number of columns and items. The number of rows is determined by the upper bounds of the number items divided by the number of columns. A grid with 10 items and 2 columns has 5 rows. A grid with 11 items and 2 columns has 6 rows. [Google Workspace Add-ons and Chat apps](https://developers.google.com/workspace/extend): For example, the following JSON creates a 2 column grid with a single item: ``` "grid": { "title": "A fine collection of items", "columnCount": 2, "borderStyle": { "type": "STROKE", "cornerRadius": 4 }, "items": [ { "image": { "imageUri": "https://www.example.com/image.png", "cropStyle": { "type": "SQUARE" }, "borderStyle": { "type": "STROKE" } }, "title": "An item", "textAlignment": "CENTER" } ], "onClick": { "openLink": { "url": "https://www.example.com" } } } ``` */
		grid: Option[Schema.GoogleAppsCardV1Grid] = None,
	  /** Displays up to 2 columns. To include more than 2 columns, or to use rows, use the `Grid` widget. For example, the following JSON creates 2 columns that each contain text paragraphs: ``` "columns": { "columnItems": [ { "horizontalSizeStyle": "FILL_AVAILABLE_SPACE", "horizontalAlignment": "CENTER", "verticalAlignment": "CENTER", "widgets": [ { "textParagraph": { "text": "First column text paragraph" } } ] }, { "horizontalSizeStyle": "FILL_AVAILABLE_SPACE", "horizontalAlignment": "CENTER", "verticalAlignment": "CENTER", "widgets": [ { "textParagraph": { "text": "Second column text paragraph" } } ] } ] } ``` */
		columns: Option[Schema.GoogleAppsCardV1Columns] = None,
	  /** A list of chips. For example, the following JSON creates two chips. The first is a text chip and the second is an icon chip that opens a link: ``` "chipList": { "chips": [ { "text": "Edit", "disabled": true, }, { "icon": { "knownIcon": "INVITE", "altText": "check calendar" }, "onClick": { "openLink": { "url": "https://example.com/calendar" } } } ] } ``` [Google Chat apps](https://developers.google.com/workspace/chat): */
		chipList: Option[Schema.GoogleAppsCardV1ChipList] = None,
	  /** Specifies whether widgets align to the left, right, or center of a column. */
		horizontalAlignment: Option[Schema.GoogleAppsCardV1Widget.HorizontalAlignmentEnum] = None
	)
	
	case class GoogleAppsCardV1TextParagraph(
	  /** The text that's shown in the widget. */
		text: Option[String] = None,
	  /** The maximum number of lines of text that are displayed in the widget. If the text exceeds the specified maximum number of lines, the excess content is concealed behind a &#42;&#42;show more&#42;&#42; button. If the text is equal or shorter than the specified maximum number of lines, a &#42;&#42;show more&#42;&#42; button isn't displayed. The default value is 0, in which case all context is displayed. Negative values are ignored. [Google Chat apps](https://developers.google.com/workspace/chat): */
		maxLines: Option[Int] = None
	)
	
	case class GoogleAppsCardV1Image(
	  /** The HTTPS URL that hosts the image. For example: ``` https://developers.google.com/workspace/chat/images/quickstart-app-avatar.png ``` */
		imageUrl: Option[String] = None,
	  /** When a user clicks the image, the click triggers this action. */
		onClick: Option[Schema.GoogleAppsCardV1OnClick] = None,
	  /** The alternative text of this image that's used for accessibility. */
		altText: Option[String] = None
	)
	
	case class GoogleAppsCardV1OnClick(
	  /** If specified, an action is triggered by this `onClick`. */
		action: Option[Schema.GoogleAppsCardV1Action] = None,
	  /** If specified, this `onClick` triggers an open link action. */
		openLink: Option[Schema.GoogleAppsCardV1OpenLink] = None,
	  /** An add-on triggers this action when the action needs to open a link. This differs from the `open_link` above in that this needs to talk to server to get the link. Thus some preparation work is required for web client to do before the open link action response comes back. [Google Workspace Add-ons](https://developers.google.com/workspace/add-ons): */
		openDynamicLinkAction: Option[Schema.GoogleAppsCardV1Action] = None,
	  /** A new card is pushed to the card stack after clicking if specified. [Google Workspace Add-ons](https://developers.google.com/workspace/add-ons): */
		card: Option[Schema.GoogleAppsCardV1Card] = None,
	  /** If specified, this `onClick` opens an overflow menu. [Google Chat apps](https://developers.google.com/workspace/chat): */
		overflowMenu: Option[Schema.GoogleAppsCardV1OverflowMenu] = None
	)
	
	object GoogleAppsCardV1Action {
		enum LoadIndicatorEnum extends Enum[LoadIndicatorEnum] { case SPINNER, NONE }
		enum InteractionEnum extends Enum[InteractionEnum] { case INTERACTION_UNSPECIFIED, OPEN_DIALOG }
	}
	case class GoogleAppsCardV1Action(
	  /** A custom function to invoke when the containing element is clicked or otherwise activated. For example usage, see [Read form data](https://developers.google.com/workspace/chat/read-form-data). */
		function: Option[String] = None,
	  /** List of action parameters. */
		parameters: Option[List[Schema.GoogleAppsCardV1ActionParameter]] = None,
	  /** Specifies the loading indicator that the action displays while making the call to the action. */
		loadIndicator: Option[Schema.GoogleAppsCardV1Action.LoadIndicatorEnum] = None,
	  /** Indicates whether form values persist after the action. The default value is `false`. If `true`, form values remain after the action is triggered. To let the user make changes while the action is being processed, set [`LoadIndicator`](https://developers.google.com/workspace/add-ons/reference/rpc/google.apps.card.v1#loadindicator) to `NONE`. For [card messages](https://developers.google.com/workspace/chat/api/guides/v1/messages/create#create) in Chat apps, you must also set the action's [`ResponseType`](https://developers.google.com/workspace/chat/api/reference/rest/v1/spaces.messages#responsetype) to `UPDATE_MESSAGE` and use the same [`card_id`](https://developers.google.com/workspace/chat/api/reference/rest/v1/spaces.messages#CardWithId) from the card that contained the action. If `false`, the form values are cleared when the action is triggered. To prevent the user from making changes while the action is being processed, set [`LoadIndicator`](https://developers.google.com/workspace/add-ons/reference/rpc/google.apps.card.v1#loadindicator) to `SPINNER`. */
		persistValues: Option[Boolean] = None,
	  /** Optional. Required when opening a [dialog](https://developers.google.com/workspace/chat/dialogs). What to do in response to an interaction with a user, such as a user clicking a button in a card message. If unspecified, the app responds by executing an `action`—like opening a link or running a function—as normal. By specifying an `interaction`, the app can respond in special interactive ways. For example, by setting `interaction` to `OPEN_DIALOG`, the app can open a [dialog](https://developers.google.com/workspace/chat/dialogs). When specified, a loading indicator isn't shown. If specified for an add-on, the entire card is stripped and nothing is shown in the client. [Google Chat apps](https://developers.google.com/workspace/chat): */
		interaction: Option[Schema.GoogleAppsCardV1Action.InteractionEnum] = None
	)
	
	case class GoogleAppsCardV1ActionParameter(
	  /** The name of the parameter for the action script. */
		key: Option[String] = None,
	  /** The value of the parameter. */
		value: Option[String] = None
	)
	
	object GoogleAppsCardV1OpenLink {
		enum OpenAsEnum extends Enum[OpenAsEnum] { case FULL_SIZE, OVERLAY }
		enum OnCloseEnum extends Enum[OnCloseEnum] { case NOTHING, RELOAD }
	}
	case class GoogleAppsCardV1OpenLink(
	  /** The URL to open. */
		url: Option[String] = None,
	  /** How to open a link. [Google Workspace Add-ons](https://developers.google.com/workspace/add-ons): */
		openAs: Option[Schema.GoogleAppsCardV1OpenLink.OpenAsEnum] = None,
	  /** Whether the client forgets about a link after opening it, or observes it until the window closes. [Google Workspace Add-ons](https://developers.google.com/workspace/add-ons): */
		onClose: Option[Schema.GoogleAppsCardV1OpenLink.OnCloseEnum] = None
	)
	
	case class GoogleAppsCardV1OverflowMenu(
	  /** Required. The list of menu options. */
		items: Option[List[Schema.GoogleAppsCardV1OverflowMenuItem]] = None
	)
	
	case class GoogleAppsCardV1OverflowMenuItem(
	  /** The icon displayed in front of the text. */
		startIcon: Option[Schema.GoogleAppsCardV1Icon] = None,
	  /** Required. The text that identifies or describes the item to users. */
		text: Option[String] = None,
	  /** Required. The action invoked when a menu option is selected. This `OnClick` cannot contain an `OverflowMenu`, any specified `OverflowMenu` is dropped and the menu item disabled. */
		onClick: Option[Schema.GoogleAppsCardV1OnClick] = None,
	  /** Whether the menu option is disabled. Defaults to false. */
		disabled: Option[Boolean] = None
	)
	
	object GoogleAppsCardV1Icon {
		enum ImageTypeEnum extends Enum[ImageTypeEnum] { case SQUARE, CIRCLE }
	}
	case class GoogleAppsCardV1Icon(
	  /** Display one of the built-in icons provided by Google Workspace. For example, to display an airplane icon, specify `AIRPLANE`. For a bus, specify `BUS`. For a full list of supported icons, see [built-in icons](https://developers.google.com/workspace/chat/format-messages#builtinicons). */
		knownIcon: Option[String] = None,
	  /** Display a custom icon hosted at an HTTPS URL. For example: ``` "iconUrl": "https://developers.google.com/workspace/chat/images/quickstart-app-avatar.png" ``` Supported file types include `.png` and `.jpg`. */
		iconUrl: Option[String] = None,
	  /** Display one of the [Google Material Icons](https://fonts.google.com/icons). For example, to display a [checkbox icon](https://fonts.google.com/icons?selected=Material%20Symbols%20Outlined%3Acheck_box%3AFILL%400%3Bwght%40400%3BGRAD%400%3Bopsz%4048), use ``` "material_icon": { "name": "check_box" } ``` [Google Chat apps](https://developers.google.com/workspace/chat): */
		materialIcon: Option[Schema.GoogleAppsCardV1MaterialIcon] = None,
	  /** Optional. A description of the icon used for accessibility. If unspecified, the default value `Button` is provided. As a best practice, you should set a helpful description for what the icon displays, and if applicable, what it does. For example, `A user's account portrait`, or `Opens a new browser tab and navigates to the Google Chat developer documentation at https://developers.google.com/workspace/chat`. If the icon is set in a `Button`, the `altText` appears as helper text when the user hovers over the button. However, if the button also sets `text`, the icon's `altText` is ignored. */
		altText: Option[String] = None,
	  /** The crop style applied to the image. In some cases, applying a `CIRCLE` crop causes the image to be drawn larger than a built-in icon. */
		imageType: Option[Schema.GoogleAppsCardV1Icon.ImageTypeEnum] = None
	)
	
	case class GoogleAppsCardV1MaterialIcon(
	  /** The icon name defined in the [Google Material Icon](https://fonts.google.com/icons), for example, `check_box`. Any invalid names are abandoned and replaced with empty string and results in the icon failing to render. */
		name: Option[String] = None,
	  /** Whether the icon renders as filled. Default value is false. To preview different icon settings, go to [Google Font Icons](https://fonts.google.com/icons) and adjust the settings under &#42;&#42;Customize&#42;&#42;. */
		fill: Option[Boolean] = None,
	  /** The stroke weight of the icon. Choose from {100, 200, 300, 400, 500, 600, 700}. If absent, default value is 400. If any other value is specified, the default value is used. To preview different icon settings, go to [Google Font Icons](https://fonts.google.com/icons) and adjust the settings under &#42;&#42;Customize&#42;&#42;. */
		weight: Option[Int] = None,
	  /** Weight and grade affect a symbol’s thickness. Adjustments to grade are more granular than adjustments to weight and have a small impact on the size of the symbol. Choose from {-25, 0, 200}. If absent, default value is 0. If any other value is specified, the default value is used. To preview different icon settings, go to [Google Font Icons](https://fonts.google.com/icons) and adjust the settings under &#42;&#42;Customize&#42;&#42;. */
		grade: Option[Int] = None
	)
	
	case class GoogleAppsCardV1DecoratedText(
	  /** Deprecated in favor of `startIcon`. */
		icon: Option[Schema.GoogleAppsCardV1Icon] = None,
	  /** The icon displayed in front of the text. */
		startIcon: Option[Schema.GoogleAppsCardV1Icon] = None,
	  /** The text that appears above `text`. Always truncates. */
		topLabel: Option[String] = None,
	  /** Required. The primary text. Supports simple formatting. For more information about formatting text, see [Formatting text in Google Chat apps](https://developers.google.com/workspace/chat/format-messages#card-formatting) and [Formatting text in Google Workspace Add-ons](https://developers.google.com/apps-script/add-ons/concepts/widgets#text_formatting). */
		text: Option[String] = None,
	  /** The wrap text setting. If `true`, the text wraps and displays on multiple lines. Otherwise, the text is truncated. Only applies to `text`, not `topLabel` and `bottomLabel`. */
		wrapText: Option[Boolean] = None,
	  /** The text that appears below `text`. Always wraps. */
		bottomLabel: Option[String] = None,
	  /** This action is triggered when users click `topLabel` or `bottomLabel`. */
		onClick: Option[Schema.GoogleAppsCardV1OnClick] = None,
	  /** A button that a user can click to trigger an action. */
		button: Option[Schema.GoogleAppsCardV1Button] = None,
	  /** A switch widget that a user can click to change its state and trigger an action. */
		switchControl: Option[Schema.GoogleAppsCardV1SwitchControl] = None,
	  /** An icon displayed after the text. Supports [built-in](https://developers.google.com/workspace/chat/format-messages#builtinicons) and [custom](https://developers.google.com/workspace/chat/format-messages#customicons) icons. */
		endIcon: Option[Schema.GoogleAppsCardV1Icon] = None
	)
	
	object GoogleAppsCardV1Button {
		enum TypeEnum extends Enum[TypeEnum] { case TYPE_UNSPECIFIED, OUTLINED, FILLED, FILLED_TONAL, BORDERLESS }
	}
	case class GoogleAppsCardV1Button(
	  /** The text displayed inside the button. */
		text: Option[String] = None,
	  /** An icon displayed inside the button. If both `icon` and `text` are set, then the icon appears before the text. */
		icon: Option[Schema.GoogleAppsCardV1Icon] = None,
	  /** Optional. The color of the button. If set, the button `type` is set to `FILLED` and the color of `text` and `icon` fields are set to a contrasting color for readability. For example, if the button color is set to blue, any text or icons in the button are set to white. To set the button color, specify a value for the `red`, `green`, and `blue` fields. The value must be a float number between 0 and 1 based on the RGB color value, where `0` (0/255) represents the absence of color and `1` (255/255) represents the maximum intensity of the color. For example, the following sets the color to red at its maximum intensity: ``` "color": { "red": 1, "green": 0, "blue": 0, } ``` The `alpha` field is unavailable for button color. If specified, this field is ignored. */
		color: Option[Schema.Color] = None,
	  /** Required. The action to perform when a user clicks the button, such as opening a hyperlink or running a custom function. */
		onClick: Option[Schema.GoogleAppsCardV1OnClick] = None,
	  /** If `true`, the button is displayed in an inactive state and doesn't respond to user actions. */
		disabled: Option[Boolean] = None,
	  /** The alternative text that's used for accessibility. Set descriptive text that lets users know what the button does. For example, if a button opens a hyperlink, you might write: "Opens a new browser tab and navigates to the Google Chat developer documentation at https://developers.google.com/workspace/chat". */
		altText: Option[String] = None,
	  /** Optional. The type of a button. If unset, button type defaults to `OUTLINED`. If the `color` field is set, the button type is forced to `FILLED` and any value set for this field is ignored. [Google Chat apps](https://developers.google.com/workspace/chat): */
		`type`: Option[Schema.GoogleAppsCardV1Button.TypeEnum] = None
	)
	
	case class Color(
	  /** The amount of red in the color as a value in the interval [0, 1]. */
		red: Option[BigDecimal] = None,
	  /** The amount of green in the color as a value in the interval [0, 1]. */
		green: Option[BigDecimal] = None,
	  /** The amount of blue in the color as a value in the interval [0, 1]. */
		blue: Option[BigDecimal] = None,
	  /** The fraction of this color that should be applied to the pixel. That is, the final pixel color is defined by the equation: `pixel color = alpha &#42; (this color) + (1.0 - alpha) &#42; (background color)` This means that a value of 1.0 corresponds to a solid color, whereas a value of 0.0 corresponds to a completely transparent color. This uses a wrapper message rather than a simple float scalar so that it is possible to distinguish between a default value and the value being unset. If omitted, this color object is rendered as a solid color (as if the alpha value had been explicitly given a value of 1.0). */
		alpha: Option[BigDecimal] = None
	)
	
	object GoogleAppsCardV1SwitchControl {
		enum ControlTypeEnum extends Enum[ControlTypeEnum] { case SWITCH, CHECKBOX, CHECK_BOX }
	}
	case class GoogleAppsCardV1SwitchControl(
	  /** The name by which the switch widget is identified in a form input event. For details about working with form inputs, see [Receive form data](https://developers.google.com/workspace/chat/read-form-data). */
		name: Option[String] = None,
	  /** The value entered by a user, returned as part of a form input event. For details about working with form inputs, see [Receive form data](https://developers.google.com/workspace/chat/read-form-data). */
		value: Option[String] = None,
	  /** When `true`, the switch is selected. */
		selected: Option[Boolean] = None,
	  /** The action to perform when the switch state is changed, such as what function to run. */
		onChangeAction: Option[Schema.GoogleAppsCardV1Action] = None,
	  /** How the switch appears in the user interface. [Google Workspace Add-ons and Chat apps](https://developers.google.com/workspace/extend): */
		controlType: Option[Schema.GoogleAppsCardV1SwitchControl.ControlTypeEnum] = None
	)
	
	case class GoogleAppsCardV1ButtonList(
	  /** An array of buttons. */
		buttons: Option[List[Schema.GoogleAppsCardV1Button]] = None
	)
	
	object GoogleAppsCardV1TextInput {
		enum TypeEnum extends Enum[TypeEnum] { case SINGLE_LINE, MULTIPLE_LINE }
	}
	case class GoogleAppsCardV1TextInput(
	  /** The name by which the text input is identified in a form input event. For details about working with form inputs, see [Receive form data](https://developers.google.com/workspace/chat/read-form-data). */
		name: Option[String] = None,
	  /** The text that appears above the text input field in the user interface. Specify text that helps the user enter the information your app needs. For example, if you are asking someone's name, but specifically need their surname, write `surname` instead of `name`. Required if `hintText` is unspecified. Otherwise, optional. */
		label: Option[String] = None,
	  /** Text that appears below the text input field meant to assist users by prompting them to enter a certain value. This text is always visible. Required if `label` is unspecified. Otherwise, optional. */
		hintText: Option[String] = None,
	  /** The value entered by a user, returned as part of a form input event. For details about working with form inputs, see [Receive form data](https://developers.google.com/workspace/chat/read-form-data). */
		value: Option[String] = None,
	  /** How a text input field appears in the user interface. For example, whether the field is single or multi-line. */
		`type`: Option[Schema.GoogleAppsCardV1TextInput.TypeEnum] = None,
	  /** What to do when a change occurs in the text input field. For example, a user adding to the field or deleting text. Examples of actions to take include running a custom function or opening a [dialog](https://developers.google.com/workspace/chat/dialogs) in Google Chat. */
		onChangeAction: Option[Schema.GoogleAppsCardV1Action] = None,
	  /** Suggested values that users can enter. These values appear when users click inside the text input field. As users type, the suggested values dynamically filter to match what the users have typed. For example, a text input field for programming language might suggest Java, JavaScript, Python, and C++. When users start typing `Jav`, the list of suggestions filters to show just `Java` and `JavaScript`. Suggested values help guide users to enter values that your app can make sense of. When referring to JavaScript, some users might enter `javascript` and others `java script`. Suggesting `JavaScript` can standardize how users interact with your app. When specified, `TextInput.type` is always `SINGLE_LINE`, even if it's set to `MULTIPLE_LINE`. [Google Workspace Add-ons and Chat apps](https://developers.google.com/workspace/extend): */
		initialSuggestions: Option[Schema.GoogleAppsCardV1Suggestions] = None,
	  /** Optional. Specify what action to take when the text input field provides suggestions to users who interact with it. If unspecified, the suggestions are set by `initialSuggestions` and are processed by the client. If specified, the app takes the action specified here, such as running a custom function. [Google Workspace Add-ons](https://developers.google.com/workspace/add-ons): */
		autoCompleteAction: Option[Schema.GoogleAppsCardV1Action] = None,
	  /** Text that appears in the text input field when the field is empty. Use this text to prompt users to enter a value. For example, `Enter a number from 0 to 100`. [Google Chat apps](https://developers.google.com/workspace/chat): */
		placeholderText: Option[String] = None
	)
	
	case class GoogleAppsCardV1Suggestions(
	  /** A list of suggestions used for autocomplete recommendations in text input fields. */
		items: Option[List[Schema.GoogleAppsCardV1SuggestionItem]] = None
	)
	
	case class GoogleAppsCardV1SuggestionItem(
	  /** The value of a suggested input to a text input field. This is equivalent to what users enter themselves. */
		text: Option[String] = None
	)
	
	object GoogleAppsCardV1SelectionInput {
		enum TypeEnum extends Enum[TypeEnum] { case CHECK_BOX, RADIO_BUTTON, SWITCH, DROPDOWN, MULTI_SELECT }
	}
	case class GoogleAppsCardV1SelectionInput(
	  /** Required. The name that identifies the selection input in a form input event. For details about working with form inputs, see [Receive form data](https://developers.google.com/workspace/chat/read-form-data). */
		name: Option[String] = None,
	  /** The text that appears above the selection input field in the user interface. Specify text that helps the user enter the information your app needs. For example, if users are selecting the urgency of a work ticket from a drop-down menu, the label might be "Urgency" or "Select urgency". */
		label: Option[String] = None,
	  /** The type of items that are displayed to users in a `SelectionInput` widget. Selection types support different types of interactions. For example, users can select one or more checkboxes, but they can only select one value from a dropdown menu. */
		`type`: Option[Schema.GoogleAppsCardV1SelectionInput.TypeEnum] = None,
	  /** An array of selectable items. For example, an array of radio buttons or checkboxes. Supports up to 100 items. */
		items: Option[List[Schema.GoogleAppsCardV1SelectionItem]] = None,
	  /** If specified, the form is submitted when the selection changes. If not specified, you must specify a separate button that submits the form. For details about working with form inputs, see [Receive form data](https://developers.google.com/workspace/chat/read-form-data). */
		onChangeAction: Option[Schema.GoogleAppsCardV1Action] = None,
	  /** For multiselect menus, the maximum number of items that a user can select. Minimum value is 1 item. If unspecified, defaults to 3 items. */
		multiSelectMaxSelectedItems: Option[Int] = None,
	  /** For multiselect menus, the number of text characters that a user inputs before the menu returns suggested selection items. If unset, the multiselect menu uses the following default values: &#42; If the menu uses a static array of `SelectionInput` items, defaults to 0 characters and immediately populates items from the array. &#42; If the menu uses a dynamic data source (`multi_select_data_source`), defaults to 3 characters before querying the data source to return suggested items. */
		multiSelectMinQueryLength: Option[Int] = None,
	  /** An external data source, such as a relational database. */
		externalDataSource: Option[Schema.GoogleAppsCardV1Action] = None,
	  /** A data source from Google Workspace. */
		platformDataSource: Option[Schema.GoogleAppsCardV1PlatformDataSource] = None
	)
	
	case class GoogleAppsCardV1SelectionItem(
	  /** The text that identifies or describes the item to users. */
		text: Option[String] = None,
	  /** The value associated with this item. The client should use this as a form input value. For details about working with form inputs, see [Receive form data](https://developers.google.com/workspace/chat/read-form-data). */
		value: Option[String] = None,
	  /** Whether the item is selected by default. If the selection input only accepts one value (such as for radio buttons or a dropdown menu), only set this field for one item. */
		selected: Option[Boolean] = None,
	  /** For multiselect menus, the URL for the icon displayed next to the item's `text` field. Supports PNG and JPEG files. Must be an `HTTPS` URL. For example, `https://developers.google.com/workspace/chat/images/quickstart-app-avatar.png`. */
		startIconUri: Option[String] = None,
	  /** For multiselect menus, a text description or label that's displayed below the item's `text` field. */
		bottomText: Option[String] = None
	)
	
	object GoogleAppsCardV1PlatformDataSource {
		enum CommonDataSourceEnum extends Enum[CommonDataSourceEnum] { case UNKNOWN, USER }
	}
	case class GoogleAppsCardV1PlatformDataSource(
	  /** A data source shared by all Google Workspace applications, such as users in a Google Workspace organization. */
		commonDataSource: Option[Schema.GoogleAppsCardV1PlatformDataSource.CommonDataSourceEnum] = None,
	  /** A data source that's unique to a Google Workspace host application, such spaces in Google Chat. This field supports the Google API Client Libraries but isn't available in the Cloud Client Libraries. To learn more, see [Install the client libraries](https://developers.google.com/workspace/chat/libraries). */
		hostAppDataSource: Option[Schema.HostAppDataSourceMarkup] = None
	)
	
	case class HostAppDataSourceMarkup(
	  /** A data source from Google Chat. */
		chatDataSource: Option[Schema.ChatClientDataSourceMarkup] = None
	)
	
	case class ChatClientDataSourceMarkup(
	  /** Google Chat spaces that the user is a member of. */
		spaceDataSource: Option[Schema.SpaceDataSource] = None
	)
	
	case class SpaceDataSource(
	  /** If set to `true`, the multiselect menu selects the current Google Chat space as an item by default. */
		defaultToCurrentSpace: Option[Boolean] = None
	)
	
	object GoogleAppsCardV1DateTimePicker {
		enum TypeEnum extends Enum[TypeEnum] { case DATE_AND_TIME, DATE_ONLY, TIME_ONLY }
	}
	case class GoogleAppsCardV1DateTimePicker(
	  /** The name by which the `DateTimePicker` is identified in a form input event. For details about working with form inputs, see [Receive form data](https://developers.google.com/workspace/chat/read-form-data). */
		name: Option[String] = None,
	  /** The text that prompts users to input a date, a time, or a date and time. For example, if users are scheduling an appointment, use a label such as `Appointment date` or `Appointment date and time`. */
		label: Option[String] = None,
	  /** Whether the widget supports inputting a date, a time, or the date and time. */
		`type`: Option[Schema.GoogleAppsCardV1DateTimePicker.TypeEnum] = None,
	  /** The default value displayed in the widget, in milliseconds since [Unix epoch time](https://en.wikipedia.org/wiki/Unix_time). Specify the value based on the type of picker (`DateTimePickerType`): &#42; `DATE_AND_TIME`: a calendar date and time in UTC. For example, to represent January 1, 2023 at 12:00 PM UTC, use `1672574400000`. &#42; `DATE_ONLY`: a calendar date at 00:00:00 UTC. For example, to represent January 1, 2023, use `1672531200000`. &#42; `TIME_ONLY`: a time in UTC. For example, to represent 12:00 PM, use `43200000` (or `12 &#42; 60 &#42; 60 &#42; 1000`). */
		valueMsEpoch: Option[String] = None,
	  /** The number representing the time zone offset from UTC, in minutes. If set, the `value_ms_epoch` is displayed in the specified time zone. If unset, the value defaults to the user's time zone setting. */
		timezoneOffsetDate: Option[Int] = None,
	  /** Triggered when the user clicks &#42;&#42;Save&#42;&#42; or &#42;&#42;Clear&#42;&#42; from the `DateTimePicker` interface. */
		onChangeAction: Option[Schema.GoogleAppsCardV1Action] = None
	)
	
	case class GoogleAppsCardV1Divider(
	
	)
	
	case class GoogleAppsCardV1Grid(
	  /** The text that displays in the grid header. */
		title: Option[String] = None,
	  /** The items to display in the grid. */
		items: Option[List[Schema.GoogleAppsCardV1GridItem]] = None,
	  /** The border style to apply to each grid item. */
		borderStyle: Option[Schema.GoogleAppsCardV1BorderStyle] = None,
	  /** The number of columns to display in the grid. A default value is used if this field isn't specified, and that default value is different depending on where the grid is shown (dialog versus companion). */
		columnCount: Option[Int] = None,
	  /** This callback is reused by each individual grid item, but with the item's identifier and index in the items list added to the callback's parameters. */
		onClick: Option[Schema.GoogleAppsCardV1OnClick] = None
	)
	
	object GoogleAppsCardV1GridItem {
		enum LayoutEnum extends Enum[LayoutEnum] { case GRID_ITEM_LAYOUT_UNSPECIFIED, TEXT_BELOW, TEXT_ABOVE }
	}
	case class GoogleAppsCardV1GridItem(
	  /** A user-specified identifier for this grid item. This identifier is returned in the parent grid's `onClick` callback parameters. */
		id: Option[String] = None,
	  /** The image that displays in the grid item. */
		image: Option[Schema.GoogleAppsCardV1ImageComponent] = None,
	  /** The grid item's title. */
		title: Option[String] = None,
	  /** The grid item's subtitle. */
		subtitle: Option[String] = None,
	  /** The layout to use for the grid item. */
		layout: Option[Schema.GoogleAppsCardV1GridItem.LayoutEnum] = None
	)
	
	case class GoogleAppsCardV1ImageComponent(
	  /** The image URL. */
		imageUri: Option[String] = None,
	  /** The accessibility label for the image. */
		altText: Option[String] = None,
	  /** The crop style to apply to the image. */
		cropStyle: Option[Schema.GoogleAppsCardV1ImageCropStyle] = None,
	  /** The border style to apply to the image. */
		borderStyle: Option[Schema.GoogleAppsCardV1BorderStyle] = None
	)
	
	object GoogleAppsCardV1ImageCropStyle {
		enum TypeEnum extends Enum[TypeEnum] { case IMAGE_CROP_TYPE_UNSPECIFIED, SQUARE, CIRCLE, RECTANGLE_CUSTOM, RECTANGLE_4_3 }
	}
	case class GoogleAppsCardV1ImageCropStyle(
	  /** The crop type. */
		`type`: Option[Schema.GoogleAppsCardV1ImageCropStyle.TypeEnum] = None,
	  /** The aspect ratio to use if the crop type is `RECTANGLE_CUSTOM`. For example, here's how to apply a 16:9 aspect ratio: ``` cropStyle { "type": "RECTANGLE_CUSTOM", "aspectRatio": 16/9 } ``` */
		aspectRatio: Option[BigDecimal] = None
	)
	
	object GoogleAppsCardV1BorderStyle {
		enum TypeEnum extends Enum[TypeEnum] { case BORDER_TYPE_UNSPECIFIED, NO_BORDER, STROKE }
	}
	case class GoogleAppsCardV1BorderStyle(
	  /** The border type. */
		`type`: Option[Schema.GoogleAppsCardV1BorderStyle.TypeEnum] = None,
	  /** The colors to use when the type is `BORDER_TYPE_STROKE`. To set the stroke color, specify a value for the `red`, `green`, and `blue` fields. The value must be a float number between 0 and 1 based on the RGB color value, where `0` (0/255) represents the absence of color and `1` (255/255) represents the maximum intensity of the color. For example, the following sets the color to red at its maximum intensity: ``` "color": { "red": 1, "green": 0, "blue": 0, } ``` The `alpha` field is unavailable for stroke color. If specified, this field is ignored. */
		strokeColor: Option[Schema.Color] = None,
	  /** The corner radius for the border. */
		cornerRadius: Option[Int] = None
	)
	
	case class GoogleAppsCardV1Columns(
	  /** An array of columns. You can include up to 2 columns in a card or dialog. */
		columnItems: Option[List[Schema.GoogleAppsCardV1Column]] = None
	)
	
	object GoogleAppsCardV1Column {
		enum HorizontalSizeStyleEnum extends Enum[HorizontalSizeStyleEnum] { case HORIZONTAL_SIZE_STYLE_UNSPECIFIED, FILL_AVAILABLE_SPACE, FILL_MINIMUM_SPACE }
		enum HorizontalAlignmentEnum extends Enum[HorizontalAlignmentEnum] { case HORIZONTAL_ALIGNMENT_UNSPECIFIED, START, CENTER, END }
		enum VerticalAlignmentEnum extends Enum[VerticalAlignmentEnum] { case VERTICAL_ALIGNMENT_UNSPECIFIED, CENTER, TOP, BOTTOM }
	}
	case class GoogleAppsCardV1Column(
	  /** Specifies how a column fills the width of the card. */
		horizontalSizeStyle: Option[Schema.GoogleAppsCardV1Column.HorizontalSizeStyleEnum] = None,
	  /** Specifies whether widgets align to the left, right, or center of a column. */
		horizontalAlignment: Option[Schema.GoogleAppsCardV1Column.HorizontalAlignmentEnum] = None,
	  /** Specifies whether widgets align to the top, bottom, or center of a column. */
		verticalAlignment: Option[Schema.GoogleAppsCardV1Column.VerticalAlignmentEnum] = None,
	  /** An array of widgets included in a column. Widgets appear in the order that they are specified. */
		widgets: Option[List[Schema.GoogleAppsCardV1Widgets]] = None
	)
	
	case class GoogleAppsCardV1Widgets(
	  /** TextParagraph widget. */
		textParagraph: Option[Schema.GoogleAppsCardV1TextParagraph] = None,
	  /** Image widget. */
		image: Option[Schema.GoogleAppsCardV1Image] = None,
	  /** DecoratedText widget. */
		decoratedText: Option[Schema.GoogleAppsCardV1DecoratedText] = None,
	  /** ButtonList widget. */
		buttonList: Option[Schema.GoogleAppsCardV1ButtonList] = None,
	  /** TextInput widget. */
		textInput: Option[Schema.GoogleAppsCardV1TextInput] = None,
	  /** SelectionInput widget. */
		selectionInput: Option[Schema.GoogleAppsCardV1SelectionInput] = None,
	  /** DateTimePicker widget. */
		dateTimePicker: Option[Schema.GoogleAppsCardV1DateTimePicker] = None,
	  /** ChipList widget. [Google Chat apps](https://developers.google.com/workspace/chat): */
		chipList: Option[Schema.GoogleAppsCardV1ChipList] = None
	)
	
	object GoogleAppsCardV1ChipList {
		enum LayoutEnum extends Enum[LayoutEnum] { case LAYOUT_UNSPECIFIED, WRAPPED, HORIZONTAL_SCROLLABLE }
	}
	case class GoogleAppsCardV1ChipList(
	  /** Specified chip list layout. */
		layout: Option[Schema.GoogleAppsCardV1ChipList.LayoutEnum] = None,
	  /** An array of chips. */
		chips: Option[List[Schema.GoogleAppsCardV1Chip]] = None
	)
	
	case class GoogleAppsCardV1Chip(
	  /** The icon image. If both `icon` and `text` are set, then the icon appears before the text. */
		icon: Option[Schema.GoogleAppsCardV1Icon] = None,
	  /** The text displayed inside the chip. */
		label: Option[String] = None,
	  /** Optional. The action to perform when a user clicks the chip, such as opening a hyperlink or running a custom function. */
		onClick: Option[Schema.GoogleAppsCardV1OnClick] = None,
	  /** Whether the chip is in an active state and responds to user actions. Defaults to `true`. Deprecated. Use `disabled` instead. */
		enabled: Option[Boolean] = None,
	  /** Whether the chip is in an inactive state and ignores user actions. Defaults to `false`. */
		disabled: Option[Boolean] = None,
	  /** The alternative text that's used for accessibility. Set descriptive text that lets users know what the chip does. For example, if a chip opens a hyperlink, write: "Opens a new browser tab and navigates to the Google Chat developer documentation at https://developers.google.com/workspace/chat". */
		altText: Option[String] = None
	)
	
	object GoogleAppsCardV1CollapseControl {
		enum HorizontalAlignmentEnum extends Enum[HorizontalAlignmentEnum] { case HORIZONTAL_ALIGNMENT_UNSPECIFIED, START, CENTER, END }
	}
	case class GoogleAppsCardV1CollapseControl(
	  /** The horizontal alignment of the expand and collapse button. */
		horizontalAlignment: Option[Schema.GoogleAppsCardV1CollapseControl.HorizontalAlignmentEnum] = None,
	  /** Optional. Define a customizable button to expand the section. Both expand_button and collapse_button field must be set. Only one field set will not take into effect. If this field isn't set, the default button is used. */
		expandButton: Option[Schema.GoogleAppsCardV1Button] = None,
	  /** Optional. Define a customizable button to collapse the section. Both expand_button and collapse_button field must be set. Only one field set will not take into effect. If this field isn't set, the default button is used. */
		collapseButton: Option[Schema.GoogleAppsCardV1Button] = None
	)
	
	case class GoogleAppsCardV1CardAction(
	  /** The label that displays as the action menu item. */
		actionLabel: Option[String] = None,
	  /** The `onClick` action for this action item. */
		onClick: Option[Schema.GoogleAppsCardV1OnClick] = None
	)
	
	case class GoogleAppsCardV1CardFixedFooter(
	  /** The primary button of the fixed footer. The button must be a text button with text and color set. */
		primaryButton: Option[Schema.GoogleAppsCardV1Button] = None,
	  /** The secondary button of the fixed footer. The button must be a text button with text and color set. If `secondaryButton` is set, you must also set `primaryButton`. */
		secondaryButton: Option[Schema.GoogleAppsCardV1Button] = None
	)
	
	object Annotation {
		enum TypeEnum extends Enum[TypeEnum] { case ANNOTATION_TYPE_UNSPECIFIED, USER_MENTION, SLASH_COMMAND, RICH_LINK }
	}
	case class Annotation(
	  /** The type of this annotation. */
		`type`: Option[Schema.Annotation.TypeEnum] = None,
	  /** Start index (0-based, inclusive) in the plain-text message body this annotation corresponds to. */
		startIndex: Option[Int] = None,
	  /** Length of the substring in the plain-text message body this annotation corresponds to. */
		length: Option[Int] = None,
	  /** The metadata of user mention. */
		userMention: Option[Schema.UserMentionMetadata] = None,
	  /** The metadata for a slash command. */
		slashCommand: Option[Schema.SlashCommandMetadata] = None,
	  /** The metadata for a rich link. */
		richLinkMetadata: Option[Schema.RichLinkMetadata] = None
	)
	
	object UserMentionMetadata {
		enum TypeEnum extends Enum[TypeEnum] { case TYPE_UNSPECIFIED, ADD, MENTION }
	}
	case class UserMentionMetadata(
	  /** The user mentioned. */
		user: Option[Schema.User] = None,
	  /** The type of user mention. */
		`type`: Option[Schema.UserMentionMetadata.TypeEnum] = None
	)
	
	object SlashCommandMetadata {
		enum TypeEnum extends Enum[TypeEnum] { case TYPE_UNSPECIFIED, ADD, INVOKE }
	}
	case class SlashCommandMetadata(
	  /** The Chat app whose command was invoked. */
		bot: Option[Schema.User] = None,
	  /** The type of slash command. */
		`type`: Option[Schema.SlashCommandMetadata.TypeEnum] = None,
	  /** The name of the invoked slash command. */
		commandName: Option[String] = None,
	  /** The command ID of the invoked slash command. */
		commandId: Option[String] = None,
	  /** Indicates whether the slash command is for a dialog. */
		triggersDialog: Option[Boolean] = None
	)
	
	object RichLinkMetadata {
		enum RichLinkTypeEnum extends Enum[RichLinkTypeEnum] { case RICH_LINK_TYPE_UNSPECIFIED, DRIVE_FILE, CHAT_SPACE }
	}
	case class RichLinkMetadata(
	  /** The URI of this link. */
		uri: Option[String] = None,
	  /** The rich link type. */
		richLinkType: Option[Schema.RichLinkMetadata.RichLinkTypeEnum] = None,
	  /** Data for a drive link. */
		driveLinkData: Option[Schema.DriveLinkData] = None,
	  /** Data for a chat space link. */
		chatSpaceLinkData: Option[Schema.ChatSpaceLinkData] = None
	)
	
	case class DriveLinkData(
	  /** A [DriveDataRef](https://developers.google.com/workspace/chat/api/reference/rest/v1/spaces.messages.attachments#drivedataref) which references a Google Drive file. */
		driveDataRef: Option[Schema.DriveDataRef] = None,
	  /** The mime type of the linked Google Drive resource. */
		mimeType: Option[String] = None
	)
	
	case class DriveDataRef(
	  /** The ID for the drive file. Use with the Drive API. */
		driveFileId: Option[String] = None
	)
	
	case class ChatSpaceLinkData(
	  /** The space of the linked Chat space resource. Format: `spaces/{space}` */
		space: Option[String] = None,
	  /** The thread of the linked Chat space resource. Format: `spaces/{space}/threads/{thread}` */
		thread: Option[String] = None,
	  /** The message of the linked Chat space resource. Format: `spaces/{space}/messages/{message}` */
		message: Option[String] = None
	)
	
	case class Thread(
	  /** Identifier. Resource name of the thread. Example: `spaces/{space}/threads/{thread}` */
		name: Option[String] = None,
	  /** Optional. Input for creating or updating a thread. Otherwise, output only. ID for the thread. Supports up to 4000 characters. This ID is unique to the Chat app that sets it. For example, if multiple Chat apps create a message using the same thread key, the messages are posted in different threads. To reply in a thread created by a person or another Chat app, specify the thread `name` field instead. */
		threadKey: Option[String] = None
	)
	
	object Space {
		enum TypeEnum extends Enum[TypeEnum] { case TYPE_UNSPECIFIED, ROOM, DM }
		enum SpaceTypeEnum extends Enum[SpaceTypeEnum] { case SPACE_TYPE_UNSPECIFIED, SPACE, GROUP_CHAT, DIRECT_MESSAGE }
		enum SpaceThreadingStateEnum extends Enum[SpaceThreadingStateEnum] { case SPACE_THREADING_STATE_UNSPECIFIED, THREADED_MESSAGES, GROUPED_MESSAGES, UNTHREADED_MESSAGES }
		enum SpaceHistoryStateEnum extends Enum[SpaceHistoryStateEnum] { case HISTORY_STATE_UNSPECIFIED, HISTORY_OFF, HISTORY_ON }
		enum PredefinedPermissionSettingsEnum extends Enum[PredefinedPermissionSettingsEnum] { case PREDEFINED_PERMISSION_SETTINGS_UNSPECIFIED, COLLABORATION_SPACE, ANNOUNCEMENT_SPACE }
	}
	case class Space(
	  /** Identifier. Resource name of the space. Format: `spaces/{space}` Where `{space}` represents the system-assigned ID for the space. You can obtain the space ID by calling the [`spaces.list()`](https://developers.google.com/workspace/chat/api/reference/rest/v1/spaces/list) method or from the space URL. For example, if the space URL is `https://mail.google.com/mail/u/0/#chat/space/AAAAAAAAA`, the space ID is `AAAAAAAAA`. */
		name: Option[String] = None,
	  /** Output only. Deprecated: Use `space_type` instead. The type of a space. */
		`type`: Option[Schema.Space.TypeEnum] = None,
	  /** Optional. The type of space. Required when creating a space or updating the space type of a space. Output only for other usage. */
		spaceType: Option[Schema.Space.SpaceTypeEnum] = None,
	  /** Optional. Whether the space is a DM between a Chat app and a single human. */
		singleUserBotDm: Option[Boolean] = None,
	  /** Output only. Deprecated: Use `spaceThreadingState` instead. Whether messages are threaded in this space. */
		threaded: Option[Boolean] = None,
	  /** Optional. The space's display name. Required when [creating a space](https://developers.google.com/workspace/chat/api/reference/rest/v1/spaces/create) with a `spaceType` of `SPACE`. If you receive the error message `ALREADY_EXISTS` when creating a space or updating the `displayName`, try a different `displayName`. An existing space within the Google Workspace organization might already use this display name. For direct messages, this field might be empty. Supports up to 128 characters. */
		displayName: Option[String] = None,
	  /** Optional. Immutable. Whether this space permits any Google Chat user as a member. Input when creating a space in a Google Workspace organization. Omit this field when creating spaces in the following conditions: &#42; The authenticated user uses a consumer account (unmanaged user account). By default, a space created by a consumer account permits any Google Chat user. For existing spaces, this field is output only. */
		externalUserAllowed: Option[Boolean] = None,
	  /** Output only. The threading state in the Chat space. */
		spaceThreadingState: Option[Schema.Space.SpaceThreadingStateEnum] = None,
	  /** Optional. Details about the space including description and rules. */
		spaceDetails: Option[Schema.SpaceDetails] = None,
	  /** Optional. The message history state for messages and threads in this space. */
		spaceHistoryState: Option[Schema.Space.SpaceHistoryStateEnum] = None,
	  /** Optional. Whether this space is created in `Import Mode` as part of a data migration into Google Workspace. While spaces are being imported, they aren't visible to users until the import is complete. Creating a space in `Import Mode`requires [user authentication](https://developers.google.com/workspace/chat/authenticate-authorize-chat-user). */
		importMode: Option[Boolean] = None,
	  /** Optional. Immutable. For spaces created in Chat, the time the space was created. This field is output only, except when used in import mode spaces. For import mode spaces, set this field to the historical timestamp at which the space was created in the source in order to preserve the original creation time. Only populated in the output when `spaceType` is `GROUP_CHAT` or `SPACE`. */
		createTime: Option[String] = None,
	  /** Output only. Timestamp of the last message in the space. */
		lastActiveTime: Option[String] = None,
	  /** Output only. For direct message (DM) spaces with a Chat app, whether the space was created by a Google Workspace administrator. Administrators can install and set up a direct message with a Chat app on behalf of users in their organization. To support admin install, your Chat app must feature direct messaging. */
		adminInstalled: Option[Boolean] = None,
	  /** Output only. The count of joined memberships grouped by member type. Populated when the `space_type` is `SPACE`, `DIRECT_MESSAGE` or `GROUP_CHAT`. */
		membershipCount: Option[Schema.MembershipCount] = None,
	  /** Optional. Specifies the [access setting](https://support.google.com/chat/answer/11971020) of the space. Only populated when the `space_type` is `SPACE`. */
		accessSettings: Option[Schema.AccessSettings] = None,
	  /** Output only. The URI for a user to access the space. */
		spaceUri: Option[String] = None,
	  /** Optional. Input only. Predefined space permission settings, input only when creating a space. If the field is not set, a collaboration space is created. After you create the space, settings are populated in the `PermissionSettings` field. */
		predefinedPermissionSettings: Option[Schema.Space.PredefinedPermissionSettingsEnum] = None,
	  /** Optional. Space permission settings for existing spaces. Input for updating exact space permission settings, where existing permission settings are replaced. Output lists current permission settings. */
		permissionSettings: Option[Schema.PermissionSettings] = None
	)
	
	case class SpaceDetails(
	  /** Optional. A description of the space. For example, describe the space's discussion topic, functional purpose, or participants. Supports up to 150 characters. */
		description: Option[String] = None,
	  /** Optional. The space's rules, expectations, and etiquette. Supports up to 5,000 characters. */
		guidelines: Option[String] = None
	)
	
	case class MembershipCount(
	  /** Output only. Count of human users that have directly joined the space, not counting users joined by having membership in a joined group. */
		joinedDirectHumanUserCount: Option[Int] = None,
	  /** Output only. Count of all groups that have directly joined the space. */
		joinedGroupCount: Option[Int] = None
	)
	
	object AccessSettings {
		enum AccessStateEnum extends Enum[AccessStateEnum] { case ACCESS_STATE_UNSPECIFIED, PRIVATE, DISCOVERABLE }
	}
	case class AccessSettings(
	  /** Output only. Indicates the access state of the space. */
		accessState: Option[Schema.AccessSettings.AccessStateEnum] = None,
	  /** Optional. The resource name of the [target audience](https://support.google.com/a/answer/9934697) who can discover the space, join the space, and preview the messages in the space. If unset, only users or Google Groups who have been individually invited or added to the space can access it. For details, see [Make a space discoverable to a target audience](https://developers.google.com/workspace/chat/space-target-audience). Format: `audiences/{audience}` To use the default target audience for the Google Workspace organization, set to `audiences/default`. Reading the target audience supports: - [User authentication](https://developers.google.com/workspace/chat/authenticate-authorize-chat-user) - [App authentication](https://developers.google.com/workspace/chat/authenticate-authorize-chat-app) with [administrator approval](https://support.google.com/a?p=chat-app-auth) with the `chat.app.spaces` scope in [Developer Preview](https://developers.google.com/workspace/preview). This field is not populated when using the `chat.bot` scope with [app authentication](https://developers.google.com/workspace/chat/authenticate-authorize-chat-app). Setting the target audience requires [user authentication](https://developers.google.com/workspace/chat/authenticate-authorize-chat-user). */
		audience: Option[String] = None
	)
	
	case class PermissionSettings(
	  /** Optional. Setting for managing members and groups in a space. */
		manageMembersAndGroups: Option[Schema.PermissionSetting] = None,
	  /** Optional. Setting for updating space name, avatar, description and guidelines. */
		modifySpaceDetails: Option[Schema.PermissionSetting] = None,
	  /** Optional. Setting for toggling space history on and off. */
		toggleHistory: Option[Schema.PermissionSetting] = None,
	  /** Optional. Setting for using @all in a space. */
		useAtMentionAll: Option[Schema.PermissionSetting] = None,
	  /** Optional. Setting for managing apps in a space. */
		manageApps: Option[Schema.PermissionSetting] = None,
	  /** Optional. Setting for managing webhooks in a space. */
		manageWebhooks: Option[Schema.PermissionSetting] = None,
	  /** Output only. Setting for posting messages in a space. */
		postMessages: Option[Schema.PermissionSetting] = None,
	  /** Optional. Setting for replying to messages in a space. */
		replyMessages: Option[Schema.PermissionSetting] = None
	)
	
	case class PermissionSetting(
	  /** Optional. Whether spaces managers have this permission. */
		managersAllowed: Option[Boolean] = None,
	  /** Optional. Whether non-manager members have this permission. */
		membersAllowed: Option[Boolean] = None
	)
	
	object ActionResponse {
		enum TypeEnum extends Enum[TypeEnum] { case TYPE_UNSPECIFIED, NEW_MESSAGE, UPDATE_MESSAGE, UPDATE_USER_MESSAGE_CARDS, REQUEST_CONFIG, DIALOG, UPDATE_WIDGET }
	}
	case class ActionResponse(
	  /** Input only. The type of Chat app response. */
		`type`: Option[Schema.ActionResponse.TypeEnum] = None,
	  /** Input only. URL for users to authenticate or configure. (Only for `REQUEST_CONFIG` response types.) */
		url: Option[String] = None,
	  /** Input only. A response to an interaction event related to a [dialog](https://developers.google.com/workspace/chat/dialogs). Must be accompanied by `ResponseType.Dialog`. */
		dialogAction: Option[Schema.DialogAction] = None,
	  /** Input only. The response of the updated widget. */
		updatedWidget: Option[Schema.UpdatedWidget] = None
	)
	
	case class DialogAction(
	  /** Input only. [Dialog](https://developers.google.com/workspace/chat/dialogs) for the request. */
		dialog: Option[Schema.Dialog] = None,
	  /** Input only. Status for a request to either invoke or submit a [dialog](https://developers.google.com/workspace/chat/dialogs). Displays a status and message to users, if necessary. For example, in case of an error or success. */
		actionStatus: Option[Schema.ActionStatus] = None
	)
	
	case class Dialog(
	  /** Input only. Body of the dialog, which is rendered in a modal. Google Chat apps don't support the following card entities: `DateTimePicker`, `OnChangeAction`. */
		body: Option[Schema.GoogleAppsCardV1Card] = None
	)
	
	object ActionStatus {
		enum StatusCodeEnum extends Enum[StatusCodeEnum] { case OK, CANCELLED, UNKNOWN, INVALID_ARGUMENT, DEADLINE_EXCEEDED, NOT_FOUND, ALREADY_EXISTS, PERMISSION_DENIED, UNAUTHENTICATED, RESOURCE_EXHAUSTED, FAILED_PRECONDITION, ABORTED, OUT_OF_RANGE, UNIMPLEMENTED, INTERNAL, UNAVAILABLE, DATA_LOSS }
	}
	case class ActionStatus(
	  /** The status code. */
		statusCode: Option[Schema.ActionStatus.StatusCodeEnum] = None,
	  /** The message to send users about the status of their request. If unset, a generic message based on the `status_code` is sent. */
		userFacingMessage: Option[String] = None
	)
	
	case class UpdatedWidget(
	  /** List of widget autocomplete results */
		suggestions: Option[Schema.SelectionItems] = None,
	  /** The ID of the updated widget. The ID must match the one for the widget that triggered the update request. */
		widget: Option[String] = None
	)
	
	case class SelectionItems(
	  /** An array of the SelectionItem objects. */
		items: Option[List[Schema.GoogleAppsCardV1SelectionItem]] = None
	)
	
	case class SlashCommand(
	  /** The ID of the slash command invoked. */
		commandId: Option[String] = None
	)
	
	object Attachment {
		enum SourceEnum extends Enum[SourceEnum] { case SOURCE_UNSPECIFIED, DRIVE_FILE, UPLOADED_CONTENT }
	}
	case class Attachment(
	  /** Optional. Resource name of the attachment, in the form `spaces/{space}/messages/{message}/attachments/{attachment}`. */
		name: Option[String] = None,
	  /** Output only. The original file name for the content, not the full path. */
		contentName: Option[String] = None,
	  /** Output only. The content type (MIME type) of the file. */
		contentType: Option[String] = None,
	  /** Optional. A reference to the attachment data. This field is used to create or update messages with attachments, or with the media API to download the attachment data. */
		attachmentDataRef: Option[Schema.AttachmentDataRef] = None,
	  /** Output only. A reference to the Google Drive attachment. This field is used with the Google Drive API. */
		driveDataRef: Option[Schema.DriveDataRef] = None,
	  /** Output only. The thumbnail URL which should be used to preview the attachment to a human user. Chat apps shouldn't use this URL to download attachment content. */
		thumbnailUri: Option[String] = None,
	  /** Output only. The download URL which should be used to allow a human user to download the attachment. Chat apps shouldn't use this URL to download attachment content. */
		downloadUri: Option[String] = None,
	  /** Output only. The source of the attachment. */
		source: Option[Schema.Attachment.SourceEnum] = None
	)
	
	case class AttachmentDataRef(
	  /** Optional. The resource name of the attachment data. This field is used with the media API to download the attachment data. */
		resourceName: Option[String] = None,
	  /** Optional. Opaque token containing a reference to an uploaded attachment. Treated by clients as an opaque string and used to create or update Chat messages with attachments. */
		attachmentUploadToken: Option[String] = None
	)
	
	case class MatchedUrl(
	  /** Output only. The URL that was matched. */
		url: Option[String] = None
	)
	
	case class EmojiReactionSummary(
	  /** Output only. Emoji associated with the reactions. */
		emoji: Option[Schema.Emoji] = None,
	  /** Output only. The total number of reactions using the associated emoji. */
		reactionCount: Option[Int] = None
	)
	
	case class Emoji(
	  /** Optional. A basic emoji represented by a unicode string. */
		unicode: Option[String] = None,
	  /** Output only. A custom emoji. */
		customEmoji: Option[Schema.CustomEmoji] = None
	)
	
	case class CustomEmoji(
	  /** Output only. Unique key for the custom emoji resource. */
		uid: Option[String] = None
	)
	
	object DeletionMetadata {
		enum DeletionTypeEnum extends Enum[DeletionTypeEnum] { case DELETION_TYPE_UNSPECIFIED, CREATOR, SPACE_OWNER, ADMIN, APP_MESSAGE_EXPIRY, CREATOR_VIA_APP, SPACE_OWNER_VIA_APP }
	}
	case class DeletionMetadata(
	  /** Indicates who deleted the message. */
		deletionType: Option[Schema.DeletionMetadata.DeletionTypeEnum] = None
	)
	
	case class QuotedMessageMetadata(
	  /** Output only. Resource name of the quoted message. Format: `spaces/{space}/messages/{message}` */
		name: Option[String] = None,
	  /** Output only. The timestamp when the quoted message was created or when the quoted message was last updated. */
		lastUpdateTime: Option[String] = None
	)
	
	case class AttachedGif(
	  /** Output only. The URL that hosts the GIF image. */
		uri: Option[String] = None
	)
	
	case class AccessoryWidget(
	  /** A list of buttons. */
		buttonList: Option[Schema.GoogleAppsCardV1ButtonList] = None
	)
	
	case class ListMessagesResponse(
	  /** List of messages. */
		messages: Option[List[Schema.Message]] = None,
	  /** You can send a token as `pageToken` to retrieve the next page of results. If empty, there are no subsequent pages. */
		nextPageToken: Option[String] = None
	)
	
	case class ListMembershipsResponse(
	  /** Unordered list. List of memberships in the requested (or first) page. */
		memberships: Option[List[Schema.Membership]] = None,
	  /** A token that you can send as `pageToken` to retrieve the next page of results. If empty, there are no subsequent pages. */
		nextPageToken: Option[String] = None
	)
	
	object Membership {
		enum StateEnum extends Enum[StateEnum] { case MEMBERSHIP_STATE_UNSPECIFIED, JOINED, INVITED, NOT_A_MEMBER }
		enum RoleEnum extends Enum[RoleEnum] { case MEMBERSHIP_ROLE_UNSPECIFIED, ROLE_MEMBER, ROLE_MANAGER }
	}
	case class Membership(
	  /** Identifier. Resource name of the membership, assigned by the server. Format: `spaces/{space}/members/{member}` */
		name: Option[String] = None,
	  /** Output only. State of the membership. */
		state: Option[Schema.Membership.StateEnum] = None,
	  /** Optional. User's role within a Chat space, which determines their permitted actions in the space. This field can only be used as input in `UpdateMembership`. */
		role: Option[Schema.Membership.RoleEnum] = None,
	  /** Optional. The Google Chat user or app the membership corresponds to. If your Chat app [authenticates as a user](https://developers.google.com/workspace/chat/authenticate-authorize-chat-user), the output populates the [user](https://developers.google.com/workspace/chat/api/reference/rest/v1/User) `name` and `type`. */
		member: Option[Schema.User] = None,
	  /** Optional. The Google Group the membership corresponds to. Reading or mutating memberships for Google Groups requires [user authentication](https://developers.google.com/workspace/chat/authenticate-authorize-chat-user). */
		groupMember: Option[Schema.Group] = None,
	  /** Optional. Immutable. The creation time of the membership, such as when a member joined or was invited to join a space. This field is output only, except when used to import historical memberships in import mode spaces. */
		createTime: Option[String] = None,
	  /** Optional. Immutable. The deletion time of the membership, such as when a member left or was removed from a space. This field is output only, except when used to import historical memberships in import mode spaces. */
		deleteTime: Option[String] = None
	)
	
	case class Group(
	  /** Resource name for a Google Group. Represents a [group](https://cloud.google.com/identity/docs/reference/rest/v1/groups) in Cloud Identity Groups API. Format: groups/{group} */
		name: Option[String] = None
	)
	
	case class Empty(
	
	)
	
	case class UploadAttachmentRequest(
	  /** Required. The filename of the attachment, including the file extension. */
		filename: Option[String] = None
	)
	
	case class UploadAttachmentResponse(
	  /** Reference to the uploaded attachment. */
		attachmentDataRef: Option[Schema.AttachmentDataRef] = None
	)
	
	case class ListSpacesResponse(
	  /** List of spaces in the requested (or first) page. Note: The `permissionSettings` field is not returned in the Space object for list requests. */
		spaces: Option[List[Schema.Space]] = None,
	  /** You can send a token as `pageToken` to retrieve the next page of results. If empty, there are no subsequent pages. */
		nextPageToken: Option[String] = None
	)
	
	case class SearchSpacesResponse(
	  /** A page of the requested spaces. */
		spaces: Option[List[Schema.Space]] = None,
	  /** A token that can be used to retrieve the next page. If this field is empty, there are no subsequent pages. */
		nextPageToken: Option[String] = None,
	  /** The total number of spaces that match the query, across all pages. If the result is over 10,000 spaces, this value is an estimate. */
		totalSize: Option[Int] = None
	)
	
	case class SetUpSpaceRequest(
	  /** Required. The `Space.spaceType` field is required. To create a space, set `Space.spaceType` to `SPACE` and set `Space.displayName`. If you receive the error message `ALREADY_EXISTS` when setting up a space, try a different `displayName`. An existing space within the Google Workspace organization might already use this display name. To create a group chat, set `Space.spaceType` to `GROUP_CHAT`. Don't set `Space.displayName`. To create a 1:1 conversation between humans, set `Space.spaceType` to `DIRECT_MESSAGE` and set `Space.singleUserBotDm` to `false`. Don't set `Space.displayName` or `Space.spaceDetails`. To create an 1:1 conversation between a human and the calling Chat app, set `Space.spaceType` to `DIRECT_MESSAGE` and `Space.singleUserBotDm` to `true`. Don't set `Space.displayName` or `Space.spaceDetails`. If a `DIRECT_MESSAGE` space already exists, that space is returned instead of creating a new space. */
		space: Option[Schema.Space] = None,
	  /** Optional. A unique identifier for this request. A random UUID is recommended. Specifying an existing request ID returns the space created with that ID instead of creating a new space. Specifying an existing request ID from the same Chat app with a different authenticated user returns an error. */
		requestId: Option[String] = None,
	  /** Optional. The Google Chat users or groups to invite to join the space. Omit the calling user, as they are added automatically. The set currently allows up to 20 memberships (in addition to the caller). For human membership, the `Membership.member` field must contain a `user` with `name` populated (format: `users/{user}`) and `type` set to `User.Type.HUMAN`. You can only add human users when setting up a space (adding Chat apps is only supported for direct message setup with the calling app). You can also add members using the user's email as an alias for {user}. For example, the `user.name` can be `users/example@gmail.com`. To invite Gmail users or users from external Google Workspace domains, user's email must be used for `{user}`. For Google group membership, the `Membership.group_member` field must contain a `group` with `name` populated (format `groups/{group}`). You can only add Google groups when setting `Space.spaceType` to `SPACE`. Optional when setting `Space.spaceType` to `SPACE`. Required when setting `Space.spaceType` to `GROUP_CHAT`, along with at least two memberships. Required when setting `Space.spaceType` to `DIRECT_MESSAGE` with a human user, along with exactly one membership. Must be empty when creating a 1:1 conversation between a human and the calling Chat app (when setting `Space.spaceType` to `DIRECT_MESSAGE` and `Space.singleUserBotDm` to `true`). */
		memberships: Option[List[Schema.Membership]] = None
	)
	
	case class CompleteImportSpaceRequest(
	
	)
	
	case class CompleteImportSpaceResponse(
	  /** The import mode space. */
		space: Option[Schema.Space] = None
	)
	
	case class Reaction(
	  /** Identifier. The resource name of the reaction. Format: `spaces/{space}/messages/{message}/reactions/{reaction}` */
		name: Option[String] = None,
	  /** Output only. The user who created the reaction. */
		user: Option[Schema.User] = None,
	  /** Required. The emoji used in the reaction. */
		emoji: Option[Schema.Emoji] = None
	)
	
	case class ListReactionsResponse(
	  /** List of reactions in the requested (or first) page. */
		reactions: Option[List[Schema.Reaction]] = None,
	  /** Continuation token to retrieve the next page of results. It's empty for the last page of results. */
		nextPageToken: Option[String] = None
	)
	
	case class SpaceReadState(
	  /** Resource name of the space read state. Format: `users/{user}/spaces/{space}/spaceReadState` */
		name: Option[String] = None,
	  /** Optional. The time when the user's space read state was updated. Usually this corresponds with either the timestamp of the last read message, or a timestamp specified by the user to mark the last read position in a space. */
		lastReadTime: Option[String] = None
	)
	
	case class ThreadReadState(
	  /** Resource name of the thread read state. Format: `users/{user}/spaces/{space}/threads/{thread}/threadReadState` */
		name: Option[String] = None,
	  /** The time when the user's thread read state was updated. Usually this corresponds with the timestamp of the last read message in a thread. */
		lastReadTime: Option[String] = None
	)
	
	case class SpaceEvent(
	  /** Resource name of the space event. Format: `spaces/{space}/spaceEvents/{spaceEvent}` */
		name: Option[String] = None,
	  /** Time when the event occurred. */
		eventTime: Option[String] = None,
	  /** Type of space event. Each event type has a batch version, which represents multiple instances of the event type that occur in a short period of time. For `spaceEvents.list()` requests, omit batch event types in your query filter. By default, the server returns both event type and its batch version. Supported event types for [messages](https://developers.google.com/workspace/chat/api/reference/rest/v1/spaces.messages): &#42; New message: `google.workspace.chat.message.v1.created` &#42; Updated message: `google.workspace.chat.message.v1.updated` &#42; Deleted message: `google.workspace.chat.message.v1.deleted` &#42; Multiple new messages: `google.workspace.chat.message.v1.batchCreated` &#42; Multiple updated messages: `google.workspace.chat.message.v1.batchUpdated` &#42; Multiple deleted messages: `google.workspace.chat.message.v1.batchDeleted` Supported event types for [memberships](https://developers.google.com/workspace/chat/api/reference/rest/v1/spaces.members): &#42; New membership: `google.workspace.chat.membership.v1.created` &#42; Updated membership: `google.workspace.chat.membership.v1.updated` &#42; Deleted membership: `google.workspace.chat.membership.v1.deleted` &#42; Multiple new memberships: `google.workspace.chat.membership.v1.batchCreated` &#42; Multiple updated memberships: `google.workspace.chat.membership.v1.batchUpdated` &#42; Multiple deleted memberships: `google.workspace.chat.membership.v1.batchDeleted` Supported event types for [reactions](https://developers.google.com/workspace/chat/api/reference/rest/v1/spaces.messages.reactions): &#42; New reaction: `google.workspace.chat.reaction.v1.created` &#42; Deleted reaction: `google.workspace.chat.reaction.v1.deleted` &#42; Multiple new reactions: `google.workspace.chat.reaction.v1.batchCreated` &#42; Multiple deleted reactions: `google.workspace.chat.reaction.v1.batchDeleted` Supported event types about the [space](https://developers.google.com/workspace/chat/api/reference/rest/v1/spaces): &#42; Updated space: `google.workspace.chat.space.v1.updated` &#42; Multiple space updates: `google.workspace.chat.space.v1.batchUpdated` */
		eventType: Option[String] = None,
	  /** Event payload for a new message. Event type: `google.workspace.chat.message.v1.created` */
		messageCreatedEventData: Option[Schema.MessageCreatedEventData] = None,
	  /** Event payload for an updated message. Event type: `google.workspace.chat.message.v1.updated` */
		messageUpdatedEventData: Option[Schema.MessageUpdatedEventData] = None,
	  /** Event payload for a deleted message. Event type: `google.workspace.chat.message.v1.deleted` */
		messageDeletedEventData: Option[Schema.MessageDeletedEventData] = None,
	  /** Event payload for multiple new messages. Event type: `google.workspace.chat.message.v1.batchCreated` */
		messageBatchCreatedEventData: Option[Schema.MessageBatchCreatedEventData] = None,
	  /** Event payload for multiple updated messages. Event type: `google.workspace.chat.message.v1.batchUpdated` */
		messageBatchUpdatedEventData: Option[Schema.MessageBatchUpdatedEventData] = None,
	  /** Event payload for multiple deleted messages. Event type: `google.workspace.chat.message.v1.batchDeleted` */
		messageBatchDeletedEventData: Option[Schema.MessageBatchDeletedEventData] = None,
	  /** Event payload for a space update. Event type: `google.workspace.chat.space.v1.updated` */
		spaceUpdatedEventData: Option[Schema.SpaceUpdatedEventData] = None,
	  /** Event payload for multiple updates to a space. Event type: `google.workspace.chat.space.v1.batchUpdated` */
		spaceBatchUpdatedEventData: Option[Schema.SpaceBatchUpdatedEventData] = None,
	  /** Event payload for a new membership. Event type: `google.workspace.chat.membership.v1.created` */
		membershipCreatedEventData: Option[Schema.MembershipCreatedEventData] = None,
	  /** Event payload for an updated membership. Event type: `google.workspace.chat.membership.v1.updated` */
		membershipUpdatedEventData: Option[Schema.MembershipUpdatedEventData] = None,
	  /** Event payload for a deleted membership. Event type: `google.workspace.chat.membership.v1.deleted` */
		membershipDeletedEventData: Option[Schema.MembershipDeletedEventData] = None,
	  /** Event payload for multiple new memberships. Event type: `google.workspace.chat.membership.v1.batchCreated` */
		membershipBatchCreatedEventData: Option[Schema.MembershipBatchCreatedEventData] = None,
	  /** Event payload for multiple updated memberships. Event type: `google.workspace.chat.membership.v1.batchUpdated` */
		membershipBatchUpdatedEventData: Option[Schema.MembershipBatchUpdatedEventData] = None,
	  /** Event payload for multiple deleted memberships. Event type: `google.workspace.chat.membership.v1.batchDeleted` */
		membershipBatchDeletedEventData: Option[Schema.MembershipBatchDeletedEventData] = None,
	  /** Event payload for a new reaction. Event type: `google.workspace.chat.reaction.v1.created` */
		reactionCreatedEventData: Option[Schema.ReactionCreatedEventData] = None,
	  /** Event payload for a deleted reaction. Event type: `google.workspace.chat.reaction.v1.deleted` */
		reactionDeletedEventData: Option[Schema.ReactionDeletedEventData] = None,
	  /** Event payload for multiple new reactions. Event type: `google.workspace.chat.reaction.v1.batchCreated` */
		reactionBatchCreatedEventData: Option[Schema.ReactionBatchCreatedEventData] = None,
	  /** Event payload for multiple deleted reactions. Event type: `google.workspace.chat.reaction.v1.batchDeleted` */
		reactionBatchDeletedEventData: Option[Schema.ReactionBatchDeletedEventData] = None
	)
	
	case class MessageCreatedEventData(
	  /** The new message. */
		message: Option[Schema.Message] = None
	)
	
	case class MessageUpdatedEventData(
	  /** The updated message. */
		message: Option[Schema.Message] = None
	)
	
	case class MessageDeletedEventData(
	  /** The deleted message. Only the `name`, `createTime`, `deleteTime`, and `deletionMetadata` fields are populated. */
		message: Option[Schema.Message] = None
	)
	
	case class MessageBatchCreatedEventData(
	  /** A list of new messages. */
		messages: Option[List[Schema.MessageCreatedEventData]] = None
	)
	
	case class MessageBatchUpdatedEventData(
	  /** A list of updated messages. */
		messages: Option[List[Schema.MessageUpdatedEventData]] = None
	)
	
	case class MessageBatchDeletedEventData(
	  /** A list of deleted messages. */
		messages: Option[List[Schema.MessageDeletedEventData]] = None
	)
	
	case class SpaceUpdatedEventData(
	  /** The updated space. */
		space: Option[Schema.Space] = None
	)
	
	case class SpaceBatchUpdatedEventData(
	  /** A list of updated spaces. */
		spaces: Option[List[Schema.SpaceUpdatedEventData]] = None
	)
	
	case class MembershipCreatedEventData(
	  /** The new membership. */
		membership: Option[Schema.Membership] = None
	)
	
	case class MembershipUpdatedEventData(
	  /** The updated membership. */
		membership: Option[Schema.Membership] = None
	)
	
	case class MembershipDeletedEventData(
	  /** The deleted membership. Only the `name` and `state` fields are populated. */
		membership: Option[Schema.Membership] = None
	)
	
	case class MembershipBatchCreatedEventData(
	  /** A list of new memberships. */
		memberships: Option[List[Schema.MembershipCreatedEventData]] = None
	)
	
	case class MembershipBatchUpdatedEventData(
	  /** A list of updated memberships. */
		memberships: Option[List[Schema.MembershipUpdatedEventData]] = None
	)
	
	case class MembershipBatchDeletedEventData(
	  /** A list of deleted memberships. */
		memberships: Option[List[Schema.MembershipDeletedEventData]] = None
	)
	
	case class ReactionCreatedEventData(
	  /** The new reaction. */
		reaction: Option[Schema.Reaction] = None
	)
	
	case class ReactionDeletedEventData(
	  /** The deleted reaction. */
		reaction: Option[Schema.Reaction] = None
	)
	
	case class ReactionBatchCreatedEventData(
	  /** A list of new reactions. */
		reactions: Option[List[Schema.ReactionCreatedEventData]] = None
	)
	
	case class ReactionBatchDeletedEventData(
	  /** A list of deleted reactions. */
		reactions: Option[List[Schema.ReactionDeletedEventData]] = None
	)
	
	case class ListSpaceEventsResponse(
	  /** Results are returned in chronological order (oldest event first). Note: The `permissionSettings` field is not returned in the Space object for list requests. */
		spaceEvents: Option[List[Schema.SpaceEvent]] = None,
	  /** Continuation token used to fetch more events. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None
	)
	
	case class ChatAppLogEntry(
	  /** The deployment that caused the error. For Chat apps built in Apps Script, this is the deployment ID defined by Apps Script. */
		deployment: Option[String] = None,
	  /** The error code and message. */
		error: Option[Schema.Status] = None,
	  /** The unencrypted `callback_method` name that was running when the error was encountered. */
		deploymentFunction: Option[String] = None
	)
	
	case class Status(
	  /** The status code, which should be an enum value of google.rpc.Code. */
		code: Option[Int] = None,
	  /** A developer-facing error message, which should be in English. Any user-facing error message should be localized and sent in the google.rpc.Status.details field, or localized by the client. */
		message: Option[String] = None,
	  /** A list of messages that carry the error details. There is a common set of message types for APIs to use. */
		details: Option[List[Map[String, JsValue]]] = None
	)
	
	object DeprecatedEvent {
		enum TypeEnum extends Enum[TypeEnum] { case UNSPECIFIED, MESSAGE, ADDED_TO_SPACE, REMOVED_FROM_SPACE, CARD_CLICKED, WIDGET_UPDATED }
		enum DialogEventTypeEnum extends Enum[DialogEventTypeEnum] { case TYPE_UNSPECIFIED, REQUEST_DIALOG, SUBMIT_DIALOG, CANCEL_DIALOG }
	}
	case class DeprecatedEvent(
	  /** The [type](/workspace/chat/api/reference/rest/v1/EventType) of user interaction with the Chat app, such as `MESSAGE` or `ADDED_TO_SPACE`. */
		`type`: Option[Schema.DeprecatedEvent.TypeEnum] = None,
	  /** The timestamp indicating when the interaction event occurred. */
		eventTime: Option[String] = None,
	  /** A secret value that legacy Chat apps can use to verify if a request is from Google. Google randomly generates the token, and its value remains static. You can obtain, revoke, or regenerate the token from the [Chat API configuration page](https://console.cloud.google.com/apis/api/chat.googleapis.com/hangouts-chat) in the Google Cloud Console. Modern Chat apps don't use this field. It is absent from API responses and the [Chat API configuration page](https://console.cloud.google.com/apis/api/chat.googleapis.com/hangouts-chat). */
		token: Option[String] = None,
	  /** The Chat app-defined key for the thread related to the interaction event. See [`spaces.messages.thread.threadKey`](/chat/api/reference/rest/v1/spaces.messages#Thread.FIELDS.thread_key) for more information. */
		threadKey: Option[String] = None,
	  /** For `ADDED_TO_SPACE`, `CARD_CLICKED`, and `MESSAGE` interaction events, the message that triggered the interaction event, if applicable. */
		message: Option[Schema.Message] = None,
	  /** The user that interacted with the Chat app. */
		user: Option[Schema.User] = None,
	  /** The space in which the user interacted with the Chat app. */
		space: Option[Schema.Space] = None,
	  /** For `CARD_CLICKED` interaction events, the form action data associated when a user clicks a card or dialog. To learn more, see [Read form data input by users on cards](https://developers.google.com/workspace/chat/read-form-data). */
		action: Option[Schema.FormAction] = None,
	  /** For `MESSAGE` interaction events, the URL that users must be redirected to after they complete an authorization or configuration flow outside of Google Chat. For more information, see [Connect a Chat app with other services and tools](https://developers.google.com/workspace/chat/connect-web-services-tools). */
		configCompleteRedirectUrl: Option[String] = None,
	  /** For `CARD_CLICKED` and `MESSAGE` interaction events, whether the user is interacting with or about to interact with a [dialog](https://developers.google.com/workspace/chat/dialogs). */
		isDialogEvent: Option[Boolean] = None,
	  /** The type of [dialog](https://developers.google.com/workspace/chat/dialogs) interaction event received. */
		dialogEventType: Option[Schema.DeprecatedEvent.DialogEventTypeEnum] = None,
	  /** Represents information about the user's client, such as locale, host app, and platform. For Chat apps, `CommonEventObject` includes information submitted by users interacting with [dialogs](https://developers.google.com/workspace/chat/dialogs), like data entered on a card. */
		common: Option[Schema.CommonEventObject] = None
	)
	
	object CommonEventObject {
		enum HostAppEnum extends Enum[HostAppEnum] { case UNSPECIFIED_HOST_APP, GMAIL, CALENDAR, DRIVE, DEMO, DOCS, MEET, SHEETS, SLIDES, DRAWINGS, CHAT }
		enum PlatformEnum extends Enum[PlatformEnum] { case UNKNOWN_PLATFORM, WEB, IOS, ANDROID }
	}
	case class CommonEventObject(
	  /** The full `locale.displayName` in the format of [ISO 639 language code]-[ISO 3166 country/region code] such as "en-US". */
		userLocale: Option[String] = None,
	  /** The hostApp enum which indicates the app the add-on is invoked from. Always `CHAT` for Chat apps. */
		hostApp: Option[Schema.CommonEventObject.HostAppEnum] = None,
	  /** The platform enum which indicates the platform where the event originates (`WEB`, `IOS`, or `ANDROID`). Not supported by Chat apps. */
		platform: Option[Schema.CommonEventObject.PlatformEnum] = None,
	  /** The timezone ID and offset from Coordinated Universal Time (UTC). Only supported for the event types [`CARD_CLICKED`](https://developers.google.com/chat/api/reference/rest/v1/EventType#ENUM_VALUES.CARD_CLICKED) and [`SUBMIT_DIALOG`](https://developers.google.com/chat/api/reference/rest/v1/DialogEventType#ENUM_VALUES.SUBMIT_DIALOG). */
		timeZone: Option[Schema.TimeZone] = None,
	  /** A map containing the values that a user inputs in a widget from a card or dialog. The map keys are the string IDs assigned to each widget, and the values represent inputs to the widget. For details, see [Process information inputted by users](https://developers.google.com/chat/ui/read-form-data). */
		formInputs: Option[Map[String, Schema.Inputs]] = None,
	  /** Custom [parameters](/chat/api/reference/rest/v1/cards#ActionParameter) passed to the invoked function. Both keys and values must be strings. */
		parameters: Option[Map[String, String]] = None,
	  /** Name of the invoked function associated with the widget. Only set for Chat apps. */
		invokedFunction: Option[String] = None
	)
	
	case class TimeZone(
	  /** The [IANA TZ](https://www.iana.org/time-zones) time zone database code, such as "America/Toronto". */
		id: Option[String] = None,
	  /** The user timezone offset, in milliseconds, from Coordinated Universal Time (UTC). */
		offset: Option[Int] = None
	)
	
	case class Inputs(
	  /** A list of strings that represent the values that the user inputs in a widget. If the widget only accepts one value, such as a [`TextInput`](https://developers.google.com/chat/api/reference/rest/v1/cards#TextInput) widget, the list contains one string object. If the widget accepts multiple values, such as a [`SelectionInput`](https://developers.google.com/chat/api/reference/rest/v1/cards#selectioninput) widget of checkboxes, the list contains a string object for each value that the user inputs or selects. */
		stringInputs: Option[Schema.StringInputs] = None,
	  /** Date and time input values from a [`DateTimePicker`](https://developers.google.com/chat/api/reference/rest/v1/cards#DateTimePicker) widget that accepts both a date and time. */
		dateTimeInput: Option[Schema.DateTimeInput] = None,
	  /** Date input values from a [`DateTimePicker`](https://developers.google.com/chat/api/reference/rest/v1/cards#DateTimePicker) widget that only accepts date values. */
		dateInput: Option[Schema.DateInput] = None,
	  /** Time input values from a [`DateTimePicker`](https://developers.google.com/chat/api/reference/rest/v1/cards#DateTimePicker) widget that only accepts time values. */
		timeInput: Option[Schema.TimeInput] = None
	)
	
	case class StringInputs(
	  /** An list of strings entered by the user. */
		value: Option[List[String]] = None
	)
	
	case class DateTimeInput(
	  /** Time since epoch time, in milliseconds. */
		msSinceEpoch: Option[String] = None,
	  /** Whether the `datetime` input includes a calendar date. */
		hasDate: Option[Boolean] = None,
	  /** Whether the `datetime` input includes a timestamp. */
		hasTime: Option[Boolean] = None
	)
	
	case class DateInput(
	  /** Time since epoch time, in milliseconds. */
		msSinceEpoch: Option[String] = None
	)
	
	case class TimeInput(
	  /** The hour on a 24-hour clock. */
		hours: Option[Int] = None,
	  /** The number of minutes past the hour. Valid values are 0 to 59. */
		minutes: Option[Int] = None
	)
}
