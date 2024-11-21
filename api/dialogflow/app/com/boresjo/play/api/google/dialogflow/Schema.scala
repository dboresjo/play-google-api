package com.boresjo.play.api.google.dialogflow

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	case class GoogleLongrunningListOperationsResponse(
	  /** A list of operations that matches the specified filter in the request. */
		operations: Option[List[Schema.GoogleLongrunningOperation]] = None,
	  /** The standard List next-page token. */
		nextPageToken: Option[String] = None
	)
	
	case class GoogleLongrunningOperation(
	  /** The server-assigned name, which is only unique within the same service that originally returns it. If you use the default HTTP mapping, the `name` should be a resource name ending with `operations/{unique_id}`. */
		name: Option[String] = None,
	  /** Service-specific metadata associated with the operation. It typically contains progress information and common metadata such as create time. Some services might not provide such metadata. Any method that returns a long-running operation should document the metadata type, if any. */
		metadata: Option[Map[String, JsValue]] = None,
	  /** If the value is `false`, it means the operation is still in progress. If `true`, the operation is completed, and either `error` or `response` is available. */
		done: Option[Boolean] = None,
	  /** The error result of the operation in case of failure or cancellation. */
		error: Option[Schema.GoogleRpcStatus] = None,
	  /** The normal, successful response of the operation. If the original method returns no data on success, such as `Delete`, the response is `google.protobuf.Empty`. If the original method is standard `Get`/`Create`/`Update`, the response should be the resource. For other methods, the response should have the type `XxxResponse`, where `Xxx` is the original method name. For example, if the original method name is `TakeSnapshot()`, the inferred response type is `TakeSnapshotResponse`. */
		response: Option[Map[String, JsValue]] = None
	)
	
	case class GoogleRpcStatus(
	  /** The status code, which should be an enum value of google.rpc.Code. */
		code: Option[Int] = None,
	  /** A developer-facing error message, which should be in English. Any user-facing error message should be localized and sent in the google.rpc.Status.details field, or localized by the client. */
		message: Option[String] = None,
	  /** A list of messages that carry the error details. There is a common set of message types for APIs to use. */
		details: Option[List[Map[String, JsValue]]] = None
	)
	
	case class GoogleProtobufEmpty(
	
	)
	
	case class GoogleCloudDialogflowCxV3ListPagesResponse(
	  /** The list of pages. There will be a maximum number of items returned based on the page_size field in the request. */
		pages: Option[List[Schema.GoogleCloudDialogflowCxV3Page]] = None,
	  /** Token to retrieve the next page of results, or empty if there are no more results in the list. */
		nextPageToken: Option[String] = None
	)
	
	case class GoogleCloudDialogflowCxV3Page(
	  /** The unique identifier of the page. Required for the Pages.UpdatePage method. Pages.CreatePage populates the name automatically. Format: `projects//locations//agents//flows//pages/`. */
		name: Option[String] = None,
	  /** Required. The human-readable name of the page, unique within the flow. */
		displayName: Option[String] = None,
	  /** The description of the page. The maximum length is 500 characters. */
		description: Option[String] = None,
	  /** The fulfillment to call when the session is entering the page. */
		entryFulfillment: Option[Schema.GoogleCloudDialogflowCxV3Fulfillment] = None,
	  /** The form associated with the page, used for collecting parameters relevant to the page. */
		form: Option[Schema.GoogleCloudDialogflowCxV3Form] = None,
	  /** Ordered list of `TransitionRouteGroups` added to the page. Transition route groups must be unique within a page. If the page links both flow-level transition route groups and agent-level transition route groups, the flow-level ones will have higher priority and will be put before the agent-level ones. &#42; If multiple transition routes within a page scope refer to the same intent, then the precedence order is: page's transition route -> page's transition route group -> flow's transition routes. &#42; If multiple transition route groups within a page contain the same intent, then the first group in the ordered list takes precedence. Format:`projects//locations//agents//flows//transitionRouteGroups/` or `projects//locations//agents//transitionRouteGroups/` for agent-level groups. */
		transitionRouteGroups: Option[List[String]] = None,
	  /** A list of transitions for the transition rules of this page. They route the conversation to another page in the same flow, or another flow. When we are in a certain page, the TransitionRoutes are evalauted in the following order: &#42; TransitionRoutes defined in the page with intent specified. &#42; TransitionRoutes defined in the transition route groups with intent specified. &#42; TransitionRoutes defined in flow with intent specified. &#42; TransitionRoutes defined in the transition route groups with intent specified. &#42; TransitionRoutes defined in the page with only condition specified. &#42; TransitionRoutes defined in the transition route groups with only condition specified. */
		transitionRoutes: Option[List[Schema.GoogleCloudDialogflowCxV3TransitionRoute]] = None,
	  /** Handlers associated with the page to handle events such as webhook errors, no match or no input. */
		eventHandlers: Option[List[Schema.GoogleCloudDialogflowCxV3EventHandler]] = None,
	  /** Hierarchical advanced settings for this page. The settings exposed at the lower level overrides the settings exposed at the higher level. */
		advancedSettings: Option[Schema.GoogleCloudDialogflowCxV3AdvancedSettings] = None,
	  /** Optional. Knowledge connector configuration. */
		knowledgeConnectorSettings: Option[Schema.GoogleCloudDialogflowCxV3KnowledgeConnectorSettings] = None
	)
	
	case class GoogleCloudDialogflowCxV3Fulfillment(
	  /** The list of rich message responses to present to the user. */
		messages: Option[List[Schema.GoogleCloudDialogflowCxV3ResponseMessage]] = None,
	  /** The webhook to call. Format: `projects//locations//agents//webhooks/`. */
		webhook: Option[String] = None,
	  /** Whether Dialogflow should return currently queued fulfillment response messages in streaming APIs. If a webhook is specified, it happens before Dialogflow invokes webhook. Warning: 1) This flag only affects streaming API. Responses are still queued and returned once in non-streaming API. 2) The flag can be enabled in any fulfillment but only the first 3 partial responses will be returned. You may only want to apply it to fulfillments that have slow webhooks. */
		returnPartialResponses: Option[Boolean] = None,
	  /** The value of this field will be populated in the WebhookRequest `fulfillmentInfo.tag` field by Dialogflow when the associated webhook is called. The tag is typically used by the webhook service to identify which fulfillment is being called, but it could be used for other purposes. This field is required if `webhook` is specified. */
		tag: Option[String] = None,
	  /** Set parameter values before executing the webhook. */
		setParameterActions: Option[List[Schema.GoogleCloudDialogflowCxV3FulfillmentSetParameterAction]] = None,
	  /** Conditional cases for this fulfillment. */
		conditionalCases: Option[List[Schema.GoogleCloudDialogflowCxV3FulfillmentConditionalCases]] = None,
	  /** Hierarchical advanced settings for this fulfillment. The settings exposed at the lower level overrides the settings exposed at the higher level. */
		advancedSettings: Option[Schema.GoogleCloudDialogflowCxV3AdvancedSettings] = None,
	  /** If the flag is true, the agent will utilize LLM to generate a text response. If LLM generation fails, the defined responses in the fulfillment will be respected. This flag is only useful for fulfillments associated with no-match event handlers. */
		enableGenerativeFallback: Option[Boolean] = None
	)
	
	object GoogleCloudDialogflowCxV3ResponseMessage {
		enum ResponseTypeEnum extends Enum[ResponseTypeEnum] { case RESPONSE_TYPE_UNSPECIFIED, ENTRY_PROMPT, PARAMETER_PROMPT, HANDLER_PROMPT }
	}
	case class GoogleCloudDialogflowCxV3ResponseMessage(
	  /** Returns a text response. */
		text: Option[Schema.GoogleCloudDialogflowCxV3ResponseMessageText] = None,
	  /** Returns a response containing a custom, platform-specific payload. */
		payload: Option[Map[String, JsValue]] = None,
	  /** Indicates that the conversation succeeded. */
		conversationSuccess: Option[Schema.GoogleCloudDialogflowCxV3ResponseMessageConversationSuccess] = None,
	  /** A text or ssml response that is preferentially used for TTS output audio synthesis, as described in the comment on the ResponseMessage message. */
		outputAudioText: Option[Schema.GoogleCloudDialogflowCxV3ResponseMessageOutputAudioText] = None,
	  /** Hands off conversation to a human agent. */
		liveAgentHandoff: Option[Schema.GoogleCloudDialogflowCxV3ResponseMessageLiveAgentHandoff] = None,
	  /** Output only. A signal that indicates the interaction with the Dialogflow agent has ended. This message is generated by Dialogflow only when the conversation reaches `END_SESSION` page. It is not supposed to be defined by the user. It's guaranteed that there is at most one such message in each response. */
		endInteraction: Option[Schema.GoogleCloudDialogflowCxV3ResponseMessageEndInteraction] = None,
	  /** Signal that the client should play an audio clip hosted at a client-specific URI. Dialogflow uses this to construct mixed_audio. However, Dialogflow itself does not try to read or process the URI in any way. */
		playAudio: Option[Schema.GoogleCloudDialogflowCxV3ResponseMessagePlayAudio] = None,
	  /** Output only. An audio response message composed of both the synthesized Dialogflow agent responses and responses defined via play_audio. This message is generated by Dialogflow only and not supposed to be defined by the user. */
		mixedAudio: Option[Schema.GoogleCloudDialogflowCxV3ResponseMessageMixedAudio] = None,
	  /** A signal that the client should transfer the phone call connected to this agent to a third-party endpoint. */
		telephonyTransferCall: Option[Schema.GoogleCloudDialogflowCxV3ResponseMessageTelephonyTransferCall] = None,
	  /** Represents info card for knowledge answers, to be better rendered in Dialogflow Messenger. */
		knowledgeInfoCard: Option[Schema.GoogleCloudDialogflowCxV3ResponseMessageKnowledgeInfoCard] = None,
	  /** Response type. */
		responseType: Option[Schema.GoogleCloudDialogflowCxV3ResponseMessage.ResponseTypeEnum] = None,
	  /** The channel which the response is associated with. Clients can specify the channel via QueryParameters.channel, and only associated channel response will be returned. */
		channel: Option[String] = None
	)
	
	case class GoogleCloudDialogflowCxV3ResponseMessageText(
	  /** Required. A collection of text response variants. If multiple variants are defined, only one text response variant is returned at runtime. */
		text: Option[List[String]] = None,
	  /** Output only. Whether the playback of this message can be interrupted by the end user's speech and the client can then starts the next Dialogflow request. */
		allowPlaybackInterruption: Option[Boolean] = None
	)
	
	case class GoogleCloudDialogflowCxV3ResponseMessageConversationSuccess(
	  /** Custom metadata. Dialogflow doesn't impose any structure on this. */
		metadata: Option[Map[String, JsValue]] = None
	)
	
	case class GoogleCloudDialogflowCxV3ResponseMessageOutputAudioText(
	  /** The raw text to be synthesized. */
		text: Option[String] = None,
	  /** The SSML text to be synthesized. For more information, see [SSML](/speech/text-to-speech/docs/ssml). */
		ssml: Option[String] = None,
	  /** Output only. Whether the playback of this message can be interrupted by the end user's speech and the client can then starts the next Dialogflow request. */
		allowPlaybackInterruption: Option[Boolean] = None
	)
	
	case class GoogleCloudDialogflowCxV3ResponseMessageLiveAgentHandoff(
	  /** Custom metadata for your handoff procedure. Dialogflow doesn't impose any structure on this. */
		metadata: Option[Map[String, JsValue]] = None
	)
	
	case class GoogleCloudDialogflowCxV3ResponseMessageEndInteraction(
	
	)
	
	case class GoogleCloudDialogflowCxV3ResponseMessagePlayAudio(
	  /** Required. URI of the audio clip. Dialogflow does not impose any validation on this value. It is specific to the client that reads it. */
		audioUri: Option[String] = None,
	  /** Output only. Whether the playback of this message can be interrupted by the end user's speech and the client can then starts the next Dialogflow request. */
		allowPlaybackInterruption: Option[Boolean] = None
	)
	
	case class GoogleCloudDialogflowCxV3ResponseMessageMixedAudio(
	  /** Segments this audio response is composed of. */
		segments: Option[List[Schema.GoogleCloudDialogflowCxV3ResponseMessageMixedAudioSegment]] = None
	)
	
	case class GoogleCloudDialogflowCxV3ResponseMessageMixedAudioSegment(
	  /** Raw audio synthesized from the Dialogflow agent's response using the output config specified in the request. */
		audio: Option[String] = None,
	  /** Client-specific URI that points to an audio clip accessible to the client. Dialogflow does not impose any validation on it. */
		uri: Option[String] = None,
	  /** Output only. Whether the playback of this segment can be interrupted by the end user's speech and the client should then start the next Dialogflow request. */
		allowPlaybackInterruption: Option[Boolean] = None
	)
	
	case class GoogleCloudDialogflowCxV3ResponseMessageTelephonyTransferCall(
	  /** Transfer the call to a phone number in [E.164 format](https://en.wikipedia.org/wiki/E.164). */
		phoneNumber: Option[String] = None
	)
	
	case class GoogleCloudDialogflowCxV3ResponseMessageKnowledgeInfoCard(
	
	)
	
	case class GoogleCloudDialogflowCxV3FulfillmentSetParameterAction(
	  /** Display name of the parameter. */
		parameter: Option[String] = None,
	  /** The new value of the parameter. A null value clears the parameter. */
		value: Option[JsValue] = None
	)
	
	case class GoogleCloudDialogflowCxV3FulfillmentConditionalCases(
	  /** A list of cascading if-else conditions. */
		cases: Option[List[Schema.GoogleCloudDialogflowCxV3FulfillmentConditionalCasesCase]] = None
	)
	
	case class GoogleCloudDialogflowCxV3FulfillmentConditionalCasesCase(
	  /** The condition to activate and select this case. Empty means the condition is always true. The condition is evaluated against form parameters or session parameters. See the [conditions reference](https://cloud.google.com/dialogflow/cx/docs/reference/condition). */
		condition: Option[String] = None,
	  /** A list of case content. */
		caseContent: Option[List[Schema.GoogleCloudDialogflowCxV3FulfillmentConditionalCasesCaseCaseContent]] = None
	)
	
	case class GoogleCloudDialogflowCxV3FulfillmentConditionalCasesCaseCaseContent(
	  /** Returned message. */
		message: Option[Schema.GoogleCloudDialogflowCxV3ResponseMessage] = None,
	  /** Additional cases to be evaluated. */
		additionalCases: Option[Schema.GoogleCloudDialogflowCxV3FulfillmentConditionalCases] = None
	)
	
	case class GoogleCloudDialogflowCxV3AdvancedSettings(
	  /** If present, incoming audio is exported by Dialogflow to the configured Google Cloud Storage destination. Exposed at the following levels: - Agent level - Flow level */
		audioExportGcsDestination: Option[Schema.GoogleCloudDialogflowCxV3GcsDestination] = None,
	  /** Settings for speech to text detection. Exposed at the following levels: - Agent level - Flow level - Page level - Parameter level */
		speechSettings: Option[Schema.GoogleCloudDialogflowCxV3AdvancedSettingsSpeechSettings] = None,
	  /** Settings for DTMF. Exposed at the following levels: - Agent level - Flow level - Page level - Parameter level. */
		dtmfSettings: Option[Schema.GoogleCloudDialogflowCxV3AdvancedSettingsDtmfSettings] = None,
	  /** Settings for logging. Settings for Dialogflow History, Contact Center messages, StackDriver logs, and speech logging. Exposed at the following levels: - Agent level. */
		loggingSettings: Option[Schema.GoogleCloudDialogflowCxV3AdvancedSettingsLoggingSettings] = None
	)
	
	case class GoogleCloudDialogflowCxV3GcsDestination(
	  /** Required. The Google Cloud Storage URI for the exported objects. A URI is of the form: `gs://bucket/object-name-or-prefix` Whether a full object name, or just a prefix, its usage depends on the Dialogflow operation. */
		uri: Option[String] = None
	)
	
	case class GoogleCloudDialogflowCxV3AdvancedSettingsSpeechSettings(
	  /** Sensitivity of the speech model that detects the end of speech. Scale from 0 to 100. */
		endpointerSensitivity: Option[Int] = None,
	  /** Timeout before detecting no speech. */
		noSpeechTimeout: Option[String] = None,
	  /** Use timeout based endpointing, interpreting endpointer sensitivy as seconds of timeout value. */
		useTimeoutBasedEndpointing: Option[Boolean] = None,
	  /** Mapping from language to Speech-to-Text model. The mapped Speech-to-Text model will be selected for requests from its corresponding language. For more information, see [Speech models](https://cloud.google.com/dialogflow/cx/docs/concept/speech-models). */
		models: Option[Map[String, String]] = None
	)
	
	case class GoogleCloudDialogflowCxV3AdvancedSettingsDtmfSettings(
	  /** If true, incoming audio is processed for DTMF (dual tone multi frequency) events. For example, if the caller presses a button on their telephone keypad and DTMF processing is enabled, Dialogflow will detect the event (e.g. a "3" was pressed) in the incoming audio and pass the event to the bot to drive business logic (e.g. when 3 is pressed, return the account balance). */
		enabled: Option[Boolean] = None,
	  /** Max length of DTMF digits. */
		maxDigits: Option[Int] = None,
	  /** The digit that terminates a DTMF digit sequence. */
		finishDigit: Option[String] = None,
	  /** Interdigit timeout setting for matching dtmf input to regex. */
		interdigitTimeoutDuration: Option[String] = None,
	  /** Endpoint timeout setting for matching dtmf input to regex. */
		endpointingTimeoutDuration: Option[String] = None
	)
	
	case class GoogleCloudDialogflowCxV3AdvancedSettingsLoggingSettings(
	  /** Enables Google Cloud Logging. */
		enableStackdriverLogging: Option[Boolean] = None,
	  /** Enables DF Interaction logging. */
		enableInteractionLogging: Option[Boolean] = None,
	  /** Enables consent-based end-user input redaction, if true, a pre-defined session parameter `$session.params.conversation-redaction` will be used to determine if the utterance should be redacted. */
		enableConsentBasedRedaction: Option[Boolean] = None
	)
	
	case class GoogleCloudDialogflowCxV3Form(
	  /** Parameters to collect from the user. */
		parameters: Option[List[Schema.GoogleCloudDialogflowCxV3FormParameter]] = None
	)
	
	case class GoogleCloudDialogflowCxV3FormParameter(
	  /** Required. The human-readable name of the parameter, unique within the form. */
		displayName: Option[String] = None,
	  /** Indicates whether the parameter is required. Optional parameters will not trigger prompts; however, they are filled if the user specifies them. Required parameters must be filled before form filling concludes. */
		required: Option[Boolean] = None,
	  /** Required. The entity type of the parameter. Format: `projects/-/locations/-/agents/-/entityTypes/` for system entity types (for example, `projects/-/locations/-/agents/-/entityTypes/sys.date`), or `projects//locations//agents//entityTypes/` for developer entity types. */
		entityType: Option[String] = None,
	  /** Indicates whether the parameter represents a list of values. */
		isList: Option[Boolean] = None,
	  /** Required. Defines fill behavior for the parameter. */
		fillBehavior: Option[Schema.GoogleCloudDialogflowCxV3FormParameterFillBehavior] = None,
	  /** The default value of an optional parameter. If the parameter is required, the default value will be ignored. */
		defaultValue: Option[JsValue] = None,
	  /** Indicates whether the parameter content should be redacted in log. If redaction is enabled, the parameter content will be replaced by parameter name during logging. Note: the parameter content is subject to redaction if either parameter level redaction or entity type level redaction is enabled. */
		redact: Option[Boolean] = None,
	  /** Hierarchical advanced settings for this parameter. The settings exposed at the lower level overrides the settings exposed at the higher level. */
		advancedSettings: Option[Schema.GoogleCloudDialogflowCxV3AdvancedSettings] = None
	)
	
	case class GoogleCloudDialogflowCxV3FormParameterFillBehavior(
	  /** Required. The fulfillment to provide the initial prompt that the agent can present to the user in order to fill the parameter. */
		initialPromptFulfillment: Option[Schema.GoogleCloudDialogflowCxV3Fulfillment] = None,
	  /** The handlers for parameter-level events, used to provide reprompt for the parameter or transition to a different page/flow. The supported events are: &#42; `sys.no-match-`, where N can be from 1 to 6 &#42; `sys.no-match-default` &#42; `sys.no-input-`, where N can be from 1 to 6 &#42; `sys.no-input-default` &#42; `sys.invalid-parameter` `initial_prompt_fulfillment` provides the first prompt for the parameter. If the user's response does not fill the parameter, a no-match/no-input event will be triggered, and the fulfillment associated with the `sys.no-match-1`/`sys.no-input-1` handler (if defined) will be called to provide a prompt. The `sys.no-match-2`/`sys.no-input-2` handler (if defined) will respond to the next no-match/no-input event, and so on. A `sys.no-match-default` or `sys.no-input-default` handler will be used to handle all following no-match/no-input events after all numbered no-match/no-input handlers for the parameter are consumed. A `sys.invalid-parameter` handler can be defined to handle the case where the parameter values have been `invalidated` by webhook. For example, if the user's response fill the parameter, however the parameter was invalidated by webhook, the fulfillment associated with the `sys.invalid-parameter` handler (if defined) will be called to provide a prompt. If the event handler for the corresponding event can't be found on the parameter, `initial_prompt_fulfillment` will be re-prompted. */
		repromptEventHandlers: Option[List[Schema.GoogleCloudDialogflowCxV3EventHandler]] = None
	)
	
	case class GoogleCloudDialogflowCxV3EventHandler(
	  /** Output only. The unique identifier of this event handler. */
		name: Option[String] = None,
	  /** Required. The name of the event to handle. */
		event: Option[String] = None,
	  /** The fulfillment to call when the event occurs. Handling webhook errors with a fulfillment enabled with webhook could cause infinite loop. It is invalid to specify such fulfillment for a handler handling webhooks. */
		triggerFulfillment: Option[Schema.GoogleCloudDialogflowCxV3Fulfillment] = None,
	  /** The target page to transition to. Format: `projects//locations//agents//flows//pages/`. */
		targetPage: Option[String] = None,
	  /** The target flow to transition to. Format: `projects//locations//agents//flows/`. */
		targetFlow: Option[String] = None,
	  /** The target playbook to transition to. Format: `projects//locations//agents//playbooks/`. */
		targetPlaybook: Option[String] = None
	)
	
	case class GoogleCloudDialogflowCxV3TransitionRoute(
	  /** Output only. The unique identifier of this transition route. */
		name: Option[String] = None,
	  /** Optional. The description of the transition route. The maximum length is 500 characters. */
		description: Option[String] = None,
	  /** The unique identifier of an Intent. Format: `projects//locations//agents//intents/`. Indicates that the transition can only happen when the given intent is matched. At least one of `intent` or `condition` must be specified. When both `intent` and `condition` are specified, the transition can only happen when both are fulfilled. */
		intent: Option[String] = None,
	  /** The condition to evaluate against form parameters or session parameters. See the [conditions reference](https://cloud.google.com/dialogflow/cx/docs/reference/condition). At least one of `intent` or `condition` must be specified. When both `intent` and `condition` are specified, the transition can only happen when both are fulfilled. */
		condition: Option[String] = None,
	  /** The fulfillment to call when the condition is satisfied. At least one of `trigger_fulfillment` and `target` must be specified. When both are defined, `trigger_fulfillment` is executed first. */
		triggerFulfillment: Option[Schema.GoogleCloudDialogflowCxV3Fulfillment] = None,
	  /** The target page to transition to. Format: `projects//locations//agents//flows//pages/`. */
		targetPage: Option[String] = None,
	  /** The target flow to transition to. Format: `projects//locations//agents//flows/`. */
		targetFlow: Option[String] = None
	)
	
	case class GoogleCloudDialogflowCxV3KnowledgeConnectorSettings(
	  /** Whether Knowledge Connector is enabled or not. */
		enabled: Option[Boolean] = None,
	  /** The fulfillment to be triggered. When the answers from the Knowledge Connector are selected by Dialogflow, you can utitlize the request scoped parameter `$request.knowledge.answers` (contains up to the 5 highest confidence answers) and `$request.knowledge.questions` (contains the corresponding questions) to construct the fulfillment. */
		triggerFulfillment: Option[Schema.GoogleCloudDialogflowCxV3Fulfillment] = None,
	  /** The target page to transition to. Format: `projects//locations//agents//flows//pages/`. */
		targetPage: Option[String] = None,
	  /** The target flow to transition to. Format: `projects//locations//agents//flows/`. */
		targetFlow: Option[String] = None,
	  /** Optional. List of related data store connections. */
		dataStoreConnections: Option[List[Schema.GoogleCloudDialogflowCxV3DataStoreConnection]] = None
	)
	
	object GoogleCloudDialogflowCxV3DataStoreConnection {
		enum DataStoreTypeEnum extends Enum[DataStoreTypeEnum] { case DATA_STORE_TYPE_UNSPECIFIED, PUBLIC_WEB, UNSTRUCTURED, STRUCTURED }
	}
	case class GoogleCloudDialogflowCxV3DataStoreConnection(
	  /** The type of the connected data store. */
		dataStoreType: Option[Schema.GoogleCloudDialogflowCxV3DataStoreConnection.DataStoreTypeEnum] = None,
	  /** The full name of the referenced data store. Formats: `projects/{project}/locations/{location}/collections/{collection}/dataStores/{data_store}` `projects/{project}/locations/{location}/dataStores/{data_store}` */
		dataStore: Option[String] = None
	)
	
	case class GoogleCloudDialogflowCxV3Flow(
	  /** The unique identifier of the flow. Format: `projects//locations//agents//flows/`. */
		name: Option[String] = None,
	  /** Required. The human-readable name of the flow. */
		displayName: Option[String] = None,
	  /** The description of the flow. The maximum length is 500 characters. If exceeded, the request is rejected. */
		description: Option[String] = None,
	  /** A flow's transition routes serve two purposes: &#42; They are responsible for matching the user's first utterances in the flow. &#42; They are inherited by every page's transition routes and can support use cases such as the user saying "help" or "can I talk to a human?", which can be handled in a common way regardless of the current page. Transition routes defined in the page have higher priority than those defined in the flow. TransitionRoutes are evalauted in the following order: &#42; TransitionRoutes with intent specified. &#42; TransitionRoutes with only condition specified. TransitionRoutes with intent specified are inherited by pages in the flow. */
		transitionRoutes: Option[List[Schema.GoogleCloudDialogflowCxV3TransitionRoute]] = None,
	  /** A flow's event handlers serve two purposes: &#42; They are responsible for handling events (e.g. no match, webhook errors) in the flow. &#42; They are inherited by every page's event handlers, which can be used to handle common events regardless of the current page. Event handlers defined in the page have higher priority than those defined in the flow. Unlike transition_routes, these handlers are evaluated on a first-match basis. The first one that matches the event get executed, with the rest being ignored. */
		eventHandlers: Option[List[Schema.GoogleCloudDialogflowCxV3EventHandler]] = None,
	  /** A flow's transition route group serve two purposes: &#42; They are responsible for matching the user's first utterances in the flow. &#42; They are inherited by every page's transition route groups. Transition route groups defined in the page have higher priority than those defined in the flow. Format: `projects//locations//agents//flows//transitionRouteGroups/` or `projects//locations//agents//transitionRouteGroups/` for agent-level groups. */
		transitionRouteGroups: Option[List[String]] = None,
	  /** NLU related settings of the flow. */
		nluSettings: Option[Schema.GoogleCloudDialogflowCxV3NluSettings] = None,
	  /** Hierarchical advanced settings for this flow. The settings exposed at the lower level overrides the settings exposed at the higher level. */
		advancedSettings: Option[Schema.GoogleCloudDialogflowCxV3AdvancedSettings] = None,
	  /** Optional. Knowledge connector configuration. */
		knowledgeConnectorSettings: Option[Schema.GoogleCloudDialogflowCxV3KnowledgeConnectorSettings] = None,
	  /** Optional. Multi-lingual agent settings for this flow. */
		multiLanguageSettings: Option[Schema.GoogleCloudDialogflowCxV3FlowMultiLanguageSettings] = None,
	  /** Indicates whether the flow is locked for changes. If the flow is locked, modifications to the flow will be rejected. */
		locked: Option[Boolean] = None
	)
	
	object GoogleCloudDialogflowCxV3NluSettings {
		enum ModelTypeEnum extends Enum[ModelTypeEnum] { case MODEL_TYPE_UNSPECIFIED, MODEL_TYPE_STANDARD, MODEL_TYPE_ADVANCED }
		enum ModelTrainingModeEnum extends Enum[ModelTrainingModeEnum] { case MODEL_TRAINING_MODE_UNSPECIFIED, MODEL_TRAINING_MODE_AUTOMATIC, MODEL_TRAINING_MODE_MANUAL }
	}
	case class GoogleCloudDialogflowCxV3NluSettings(
	  /** Indicates the type of NLU model. */
		modelType: Option[Schema.GoogleCloudDialogflowCxV3NluSettings.ModelTypeEnum] = None,
	  /** To filter out false positive results and still get variety in matched natural language inputs for your agent, you can tune the machine learning classification threshold. If the returned score value is less than the threshold value, then a no-match event will be triggered. The score values range from 0.0 (completely uncertain) to 1.0 (completely certain). If set to 0.0, the default of 0.3 is used. You can set a separate classification threshold for the flow in each language enabled for the agent. */
		classificationThreshold: Option[BigDecimal] = None,
	  /** Indicates NLU model training mode. */
		modelTrainingMode: Option[Schema.GoogleCloudDialogflowCxV3NluSettings.ModelTrainingModeEnum] = None
	)
	
	case class GoogleCloudDialogflowCxV3FlowMultiLanguageSettings(
	  /** Optional. Enable multi-language detection for this flow. This can be set only if agent level multi language setting is enabled. */
		enableMultiLanguageDetection: Option[Boolean] = None,
	  /** Optional. Agent will respond in the detected language if the detected language code is in the supported resolved languages for this flow. This will be used only if multi-language training is enabled in the agent and multi-language detection is enabled in the flow. The supported languages must be a subset of the languages supported by the agent. */
		supportedResponseLanguageCodes: Option[List[String]] = None
	)
	
	case class GoogleCloudDialogflowCxV3ListFlowsResponse(
	  /** The list of flows. There will be a maximum number of items returned based on the page_size field in the request. */
		flows: Option[List[Schema.GoogleCloudDialogflowCxV3Flow]] = None,
	  /** Token to retrieve the next page of results, or empty if there are no more results in the list. */
		nextPageToken: Option[String] = None
	)
	
	case class GoogleCloudDialogflowCxV3TrainFlowRequest(
	
	)
	
	case class GoogleCloudDialogflowCxV3ValidateFlowRequest(
	  /** If not specified, the agent's default language is used. */
		languageCode: Option[String] = None
	)
	
	case class GoogleCloudDialogflowCxV3FlowValidationResult(
	  /** The unique identifier of the flow validation result. Format: `projects//locations//agents//flows//validationResult`. */
		name: Option[String] = None,
	  /** Contains all validation messages. */
		validationMessages: Option[List[Schema.GoogleCloudDialogflowCxV3ValidationMessage]] = None,
	  /** Last time the flow was validated. */
		updateTime: Option[String] = None
	)
	
	object GoogleCloudDialogflowCxV3ValidationMessage {
		enum ResourceTypeEnum extends Enum[ResourceTypeEnum] { case RESOURCE_TYPE_UNSPECIFIED, AGENT, INTENT, INTENT_TRAINING_PHRASE, INTENT_PARAMETER, INTENTS, INTENT_TRAINING_PHRASES, ENTITY_TYPE, ENTITY_TYPES, WEBHOOK, FLOW, PAGE, PAGES, TRANSITION_ROUTE_GROUP, AGENT_TRANSITION_ROUTE_GROUP }
		enum SeverityEnum extends Enum[SeverityEnum] { case SEVERITY_UNSPECIFIED, INFO, WARNING, ERROR }
	}
	case class GoogleCloudDialogflowCxV3ValidationMessage(
	  /** The type of the resources where the message is found. */
		resourceType: Option[Schema.GoogleCloudDialogflowCxV3ValidationMessage.ResourceTypeEnum] = None,
	  /** The names of the resources where the message is found. */
		resources: Option[List[String]] = None,
	  /** The resource names of the resources where the message is found. */
		resourceNames: Option[List[Schema.GoogleCloudDialogflowCxV3ResourceName]] = None,
	  /** Indicates the severity of the message. */
		severity: Option[Schema.GoogleCloudDialogflowCxV3ValidationMessage.SeverityEnum] = None,
	  /** The message detail. */
		detail: Option[String] = None
	)
	
	case class GoogleCloudDialogflowCxV3ResourceName(
	  /** Name. */
		name: Option[String] = None,
	  /** Display name. */
		displayName: Option[String] = None
	)
	
	object GoogleCloudDialogflowCxV3ImportFlowRequest {
		enum ImportOptionEnum extends Enum[ImportOptionEnum] { case IMPORT_OPTION_UNSPECIFIED, KEEP, FALLBACK }
	}
	case class GoogleCloudDialogflowCxV3ImportFlowRequest(
	  /** The [Google Cloud Storage](https://cloud.google.com/storage/docs/) URI to import flow from. The format of this URI must be `gs:///`. Dialogflow performs a read operation for the Cloud Storage object on the caller's behalf, so your request authentication must have read permissions for the object. For more information, see [Dialogflow access control](https://cloud.google.com/dialogflow/cx/docs/concept/access-control#storage). */
		flowUri: Option[String] = None,
	  /** Uncompressed raw byte content for flow. */
		flowContent: Option[String] = None,
	  /** Flow import mode. If not specified, `KEEP` is assumed. */
		importOption: Option[Schema.GoogleCloudDialogflowCxV3ImportFlowRequest.ImportOptionEnum] = None,
	  /** Optional. Specifies the import strategy used when resolving resource conflicts. */
		flowImportStrategy: Option[Schema.GoogleCloudDialogflowCxV3FlowImportStrategy] = None
	)
	
	object GoogleCloudDialogflowCxV3FlowImportStrategy {
		enum GlobalImportStrategyEnum extends Enum[GlobalImportStrategyEnum] { case IMPORT_STRATEGY_UNSPECIFIED, IMPORT_STRATEGY_CREATE_NEW, IMPORT_STRATEGY_REPLACE, IMPORT_STRATEGY_KEEP, IMPORT_STRATEGY_MERGE, IMPORT_STRATEGY_THROW_ERROR }
	}
	case class GoogleCloudDialogflowCxV3FlowImportStrategy(
	  /** Optional. Import strategy for resource conflict resolution, applied globally throughout the flow. It will be applied for all display name conflicts in the imported content. If not specified, 'CREATE_NEW' is assumed. */
		globalImportStrategy: Option[Schema.GoogleCloudDialogflowCxV3FlowImportStrategy.GlobalImportStrategyEnum] = None
	)
	
	case class GoogleCloudDialogflowCxV3ExportFlowRequest(
	  /** Optional. The [Google Cloud Storage](https://cloud.google.com/storage/docs/) URI to export the flow to. The format of this URI must be `gs:///`. If left unspecified, the serialized flow is returned inline. Dialogflow performs a write operation for the Cloud Storage object on the caller's behalf, so your request authentication must have write permissions for the object. For more information, see [Dialogflow access control](https://cloud.google.com/dialogflow/cx/docs/concept/access-control#storage). */
		flowUri: Option[String] = None,
	  /** Optional. Whether to export flows referenced by the specified flow. */
		includeReferencedFlows: Option[Boolean] = None
	)
	
	object GoogleCloudDialogflowCxV3SecuritySettings {
		enum RedactionStrategyEnum extends Enum[RedactionStrategyEnum] { case REDACTION_STRATEGY_UNSPECIFIED, REDACT_WITH_SERVICE }
		enum RedactionScopeEnum extends Enum[RedactionScopeEnum] { case REDACTION_SCOPE_UNSPECIFIED, REDACT_DISK_STORAGE }
		enum RetentionStrategyEnum extends Enum[RetentionStrategyEnum] { case RETENTION_STRATEGY_UNSPECIFIED, REMOVE_AFTER_CONVERSATION }
		enum PurgeDataTypesEnum extends Enum[PurgeDataTypesEnum] { case PURGE_DATA_TYPE_UNSPECIFIED, DIALOGFLOW_HISTORY }
	}
	case class GoogleCloudDialogflowCxV3SecuritySettings(
	  /** Resource name of the settings. Required for the SecuritySettingsService.UpdateSecuritySettings method. SecuritySettingsService.CreateSecuritySettings populates the name automatically. Format: `projects//locations//securitySettings/`. */
		name: Option[String] = None,
	  /** Required. The human-readable name of the security settings, unique within the location. */
		displayName: Option[String] = None,
	  /** Strategy that defines how we do redaction. */
		redactionStrategy: Option[Schema.GoogleCloudDialogflowCxV3SecuritySettings.RedactionStrategyEnum] = None,
	  /** Defines the data for which Dialogflow applies redaction. Dialogflow does not redact data that it does not have access to – for example, Cloud logging. */
		redactionScope: Option[Schema.GoogleCloudDialogflowCxV3SecuritySettings.RedactionScopeEnum] = None,
	  /** [DLP](https://cloud.google.com/dlp/docs) inspect template name. Use this template to define inspect base settings. The `DLP Inspect Templates Reader` role is needed on the Dialogflow service identity service account (has the form `service-PROJECT_NUMBER@gcp-sa-dialogflow.iam.gserviceaccount.com`) for your agent's project. If empty, we use the default DLP inspect config. The template name will have one of the following formats: `projects//locations//inspectTemplates/` OR `organizations//locations//inspectTemplates/` Note: `inspect_template` must be located in the same region as the `SecuritySettings`. */
		inspectTemplate: Option[String] = None,
	  /** [DLP](https://cloud.google.com/dlp/docs) deidentify template name. Use this template to define de-identification configuration for the content. The `DLP De-identify Templates Reader` role is needed on the Dialogflow service identity service account (has the form `service-PROJECT_NUMBER@gcp-sa-dialogflow.iam.gserviceaccount.com`) for your agent's project. If empty, Dialogflow replaces sensitive info with `[redacted]` text. The template name will have one of the following formats: `projects//locations//deidentifyTemplates/` OR `organizations//locations//deidentifyTemplates/` Note: `deidentify_template` must be located in the same region as the `SecuritySettings`. */
		deidentifyTemplate: Option[String] = None,
	  /** Retains the data for the specified number of days. User must set a value lower than Dialogflow's default 365d TTL (30 days for Agent Assist traffic), higher value will be ignored and use default. Setting a value higher than that has no effect. A missing value or setting to 0 also means we use default TTL. When data retention configuration is changed, it only applies to the data created after the change; the TTL of existing data created before the change stays intact. */
		retentionWindowDays: Option[Int] = None,
	  /** Specifies the retention behavior defined by SecuritySettings.RetentionStrategy. */
		retentionStrategy: Option[Schema.GoogleCloudDialogflowCxV3SecuritySettings.RetentionStrategyEnum] = None,
	  /** List of types of data to remove when retention settings triggers purge. */
		purgeDataTypes: Option[List[Schema.GoogleCloudDialogflowCxV3SecuritySettings.PurgeDataTypesEnum]] = None,
	  /** Controls audio export settings for post-conversation analytics when ingesting audio to conversations via Participants.AnalyzeContent or Participants.StreamingAnalyzeContent. If retention_strategy is set to REMOVE_AFTER_CONVERSATION or audio_export_settings.gcs_bucket is empty, audio export is disabled. If audio export is enabled, audio is recorded and saved to audio_export_settings.gcs_bucket, subject to retention policy of audio_export_settings.gcs_bucket. This setting won't effect audio input for implicit sessions via Sessions.DetectIntent or Sessions.StreamingDetectIntent. */
		audioExportSettings: Option[Schema.GoogleCloudDialogflowCxV3SecuritySettingsAudioExportSettings] = None,
	  /** Controls conversation exporting settings to Insights after conversation is completed. If retention_strategy is set to REMOVE_AFTER_CONVERSATION, Insights export is disabled no matter what you configure here. */
		insightsExportSettings: Option[Schema.GoogleCloudDialogflowCxV3SecuritySettingsInsightsExportSettings] = None
	)
	
	object GoogleCloudDialogflowCxV3SecuritySettingsAudioExportSettings {
		enum AudioFormatEnum extends Enum[AudioFormatEnum] { case AUDIO_FORMAT_UNSPECIFIED, MULAW, MP3, OGG }
	}
	case class GoogleCloudDialogflowCxV3SecuritySettingsAudioExportSettings(
	  /** Cloud Storage bucket to export audio record to. Setting this field would grant the Storage Object Creator role to the Dialogflow Service Agent. API caller that tries to modify this field should have the permission of storage.buckets.setIamPolicy. */
		gcsBucket: Option[String] = None,
	  /** Filename pattern for exported audio. */
		audioExportPattern: Option[String] = None,
	  /** Enable audio redaction if it is true. Note that this only redacts end-user audio data; Synthesised audio from the virtual agent is not redacted. */
		enableAudioRedaction: Option[Boolean] = None,
	  /** File format for exported audio file. Currently only in telephony recordings. */
		audioFormat: Option[Schema.GoogleCloudDialogflowCxV3SecuritySettingsAudioExportSettings.AudioFormatEnum] = None,
	  /** Whether to store TTS audio. By default, TTS audio from the virtual agent is not exported. */
		storeTtsAudio: Option[Boolean] = None
	)
	
	case class GoogleCloudDialogflowCxV3SecuritySettingsInsightsExportSettings(
	  /** If enabled, we will automatically exports conversations to Insights and Insights runs its analyzers. */
		enableInsightsExport: Option[Boolean] = None
	)
	
	case class GoogleCloudDialogflowCxV3ListSecuritySettingsResponse(
	  /** The list of security settings. */
		securitySettings: Option[List[Schema.GoogleCloudDialogflowCxV3SecuritySettings]] = None,
	  /** Token to retrieve the next page of results, or empty if there are no more results in the list. */
		nextPageToken: Option[String] = None
	)
	
	case class GoogleCloudDialogflowCxV3ListAgentsResponse(
	  /** The list of agents. There will be a maximum number of items returned based on the page_size field in the request. */
		agents: Option[List[Schema.GoogleCloudDialogflowCxV3Agent]] = None,
	  /** Token to retrieve the next page of results, or empty if there are no more results in the list. */
		nextPageToken: Option[String] = None
	)
	
	case class GoogleCloudDialogflowCxV3Agent(
	  /** The unique identifier of the agent. Required for the Agents.UpdateAgent method. Agents.CreateAgent populates the name automatically. Format: `projects//locations//agents/`. */
		name: Option[String] = None,
	  /** Required. The human-readable name of the agent, unique within the location. */
		displayName: Option[String] = None,
	  /** Required. Immutable. The default language of the agent as a language tag. See [Language Support](https://cloud.google.com/dialogflow/cx/docs/reference/language) for a list of the currently supported language codes. This field cannot be set by the Agents.UpdateAgent method. */
		defaultLanguageCode: Option[String] = None,
	  /** The list of all languages supported by the agent (except for the `default_language_code`). */
		supportedLanguageCodes: Option[List[String]] = None,
	  /** Required. The time zone of the agent from the [time zone database](https://www.iana.org/time-zones), e.g., America/New_York, Europe/Paris. */
		timeZone: Option[String] = None,
	  /** The description of the agent. The maximum length is 500 characters. If exceeded, the request is rejected. */
		description: Option[String] = None,
	  /** The URI of the agent's avatar. Avatars are used throughout the Dialogflow console and in the self-hosted [Web Demo](https://cloud.google.com/dialogflow/docs/integrations/web-demo) integration. */
		avatarUri: Option[String] = None,
	  /** Speech recognition related settings. */
		speechToTextSettings: Option[Schema.GoogleCloudDialogflowCxV3SpeechToTextSettings] = None,
	  /** Immutable. Name of the start flow in this agent. A start flow will be automatically created when the agent is created, and can only be deleted by deleting the agent. Format: `projects//locations//agents//flows/`. */
		startFlow: Option[String] = None,
	  /** Name of the SecuritySettings reference for the agent. Format: `projects//locations//securitySettings/`. */
		securitySettings: Option[String] = None,
	  /** Indicates if stackdriver logging is enabled for the agent. Please use agent.advanced_settings instead. */
		enableStackdriverLogging: Option[Boolean] = None,
	  /** Indicates if automatic spell correction is enabled in detect intent requests. */
		enableSpellCorrection: Option[Boolean] = None,
	  /** Optional. Enable training multi-lingual models for this agent. These models will be trained on all the languages supported by the agent. */
		enableMultiLanguageTraining: Option[Boolean] = None,
	  /** Indicates whether the agent is locked for changes. If the agent is locked, modifications to the agent will be rejected except for RestoreAgent. */
		locked: Option[Boolean] = None,
	  /** Hierarchical advanced settings for this agent. The settings exposed at the lower level overrides the settings exposed at the higher level. */
		advancedSettings: Option[Schema.GoogleCloudDialogflowCxV3AdvancedSettings] = None,
	  /** Git integration settings for this agent. */
		gitIntegrationSettings: Option[Schema.GoogleCloudDialogflowCxV3AgentGitIntegrationSettings] = None,
	  /** Settings on instructing the speech synthesizer on how to generate the output audio content. */
		textToSpeechSettings: Option[Schema.GoogleCloudDialogflowCxV3TextToSpeechSettings] = None,
	  /** Gen App Builder-related agent-level settings. */
		genAppBuilderSettings: Option[Schema.GoogleCloudDialogflowCxV3AgentGenAppBuilderSettings] = None,
	  /** Optional. Answer feedback collection settings. */
		answerFeedbackSettings: Option[Schema.GoogleCloudDialogflowCxV3AgentAnswerFeedbackSettings] = None,
	  /** Optional. Settings for end user personalization. */
		personalizationSettings: Option[Schema.GoogleCloudDialogflowCxV3AgentPersonalizationSettings] = None,
	  /** Optional. Settings for custom client certificates. */
		clientCertificateSettings: Option[Schema.GoogleCloudDialogflowCxV3AgentClientCertificateSettings] = None
	)
	
	case class GoogleCloudDialogflowCxV3SpeechToTextSettings(
	  /** Whether to use speech adaptation for speech recognition. */
		enableSpeechAdaptation: Option[Boolean] = None
	)
	
	case class GoogleCloudDialogflowCxV3AgentGitIntegrationSettings(
	  /** GitHub settings. */
		githubSettings: Option[Schema.GoogleCloudDialogflowCxV3AgentGitIntegrationSettingsGithubSettings] = None
	)
	
	case class GoogleCloudDialogflowCxV3AgentGitIntegrationSettingsGithubSettings(
	  /** The unique repository display name for the GitHub repository. */
		displayName: Option[String] = None,
	  /** The GitHub repository URI related to the agent. */
		repositoryUri: Option[String] = None,
	  /** The branch of the GitHub repository tracked for this agent. */
		trackingBranch: Option[String] = None,
	  /** The access token used to authenticate the access to the GitHub repository. */
		accessToken: Option[String] = None,
	  /** A list of branches configured to be used from Dialogflow. */
		branches: Option[List[String]] = None
	)
	
	case class GoogleCloudDialogflowCxV3TextToSpeechSettings(
	  /** Configuration of how speech should be synthesized, mapping from language (https://cloud.google.com/dialogflow/cx/docs/reference/language) to SynthesizeSpeechConfig. These settings affect: - The [phone gateway](https://cloud.google.com/dialogflow/cx/docs/concept/integration/phone-gateway) synthesize configuration set via Agent.text_to_speech_settings. - How speech is synthesized when invoking session APIs. Agent.text_to_speech_settings only applies if OutputAudioConfig.synthesize_speech_config is not specified. */
		synthesizeSpeechConfigs: Option[Map[String, Schema.GoogleCloudDialogflowCxV3SynthesizeSpeechConfig]] = None
	)
	
	case class GoogleCloudDialogflowCxV3SynthesizeSpeechConfig(
	  /** Optional. Speaking rate/speed, in the range [0.25, 4.0]. 1.0 is the normal native speed supported by the specific voice. 2.0 is twice as fast, and 0.5 is half as fast. If unset(0.0), defaults to the native 1.0 speed. Any other values < 0.25 or > 4.0 will return an error. */
		speakingRate: Option[BigDecimal] = None,
	  /** Optional. Speaking pitch, in the range [-20.0, 20.0]. 20 means increase 20 semitones from the original pitch. -20 means decrease 20 semitones from the original pitch. */
		pitch: Option[BigDecimal] = None,
	  /** Optional. Volume gain (in dB) of the normal native volume supported by the specific voice, in the range [-96.0, 16.0]. If unset, or set to a value of 0.0 (dB), will play at normal native signal amplitude. A value of -6.0 (dB) will play at approximately half the amplitude of the normal native signal amplitude. A value of +6.0 (dB) will play at approximately twice the amplitude of the normal native signal amplitude. We strongly recommend not to exceed +10 (dB) as there's usually no effective increase in loudness for any value greater than that. */
		volumeGainDb: Option[BigDecimal] = None,
	  /** Optional. An identifier which selects 'audio effects' profiles that are applied on (post synthesized) text to speech. Effects are applied on top of each other in the order they are given. */
		effectsProfileId: Option[List[String]] = None,
	  /** Optional. The desired voice of the synthesized audio. */
		voice: Option[Schema.GoogleCloudDialogflowCxV3VoiceSelectionParams] = None
	)
	
	object GoogleCloudDialogflowCxV3VoiceSelectionParams {
		enum SsmlGenderEnum extends Enum[SsmlGenderEnum] { case SSML_VOICE_GENDER_UNSPECIFIED, SSML_VOICE_GENDER_MALE, SSML_VOICE_GENDER_FEMALE, SSML_VOICE_GENDER_NEUTRAL }
	}
	case class GoogleCloudDialogflowCxV3VoiceSelectionParams(
	  /** Optional. The name of the voice. If not set, the service will choose a voice based on the other parameters such as language_code and ssml_gender. For the list of available voices, please refer to [Supported voices and languages](https://cloud.google.com/text-to-speech/docs/voices). */
		name: Option[String] = None,
	  /** Optional. The preferred gender of the voice. If not set, the service will choose a voice based on the other parameters such as language_code and name. Note that this is only a preference, not requirement. If a voice of the appropriate gender is not available, the synthesizer substitutes a voice with a different gender rather than failing the request. */
		ssmlGender: Option[Schema.GoogleCloudDialogflowCxV3VoiceSelectionParams.SsmlGenderEnum] = None
	)
	
	case class GoogleCloudDialogflowCxV3AgentGenAppBuilderSettings(
	  /** Required. The full name of the Gen App Builder engine related to this agent if there is one. Format: `projects/{Project ID}/locations/{Location ID}/collections/{Collection ID}/engines/{Engine ID}` */
		engine: Option[String] = None
	)
	
	case class GoogleCloudDialogflowCxV3AgentAnswerFeedbackSettings(
	  /** Optional. If enabled, end users will be able to provide answer feedback to Dialogflow responses. Feature works only if interaction logging is enabled in the Dialogflow agent. */
		enableAnswerFeedback: Option[Boolean] = None
	)
	
	case class GoogleCloudDialogflowCxV3AgentPersonalizationSettings(
	  /** Optional. Default end user metadata, used when processing DetectIntent requests. Recommended to be filled as a template instead of hard-coded value, for example { "age": "$session.params.age" }. The data will be merged with the QueryParameters.end_user_metadata in DetectIntentRequest.query_params during query processing. */
		defaultEndUserMetadata: Option[Map[String, JsValue]] = None
	)
	
	case class GoogleCloudDialogflowCxV3AgentClientCertificateSettings(
	  /** Required. The ssl certificate encoded in PEM format. This string must include the begin header and end footer lines. */
		sslCertificate: Option[String] = None,
	  /** Required. The name of the SecretManager secret version resource storing the private key encoded in PEM format. Format: `projects/{project}/secrets/{secret}/versions/{version}` */
		privateKey: Option[String] = None,
	  /** Optional. The name of the SecretManager secret version resource storing the passphrase. 'passphrase' should be left unset if the private key is not encrypted. Format: `projects/{project}/secrets/{secret}/versions/{version}` */
		passphrase: Option[String] = None
	)
	
	object GoogleCloudDialogflowCxV3ExportAgentRequest {
		enum DataFormatEnum extends Enum[DataFormatEnum] { case DATA_FORMAT_UNSPECIFIED, BLOB, JSON_PACKAGE }
	}
	case class GoogleCloudDialogflowCxV3ExportAgentRequest(
	  /** Optional. The [Google Cloud Storage](https://cloud.google.com/storage/docs/) URI to export the agent to. The format of this URI must be `gs:///`. If left unspecified, the serialized agent is returned inline. Dialogflow performs a write operation for the Cloud Storage object on the caller's behalf, so your request authentication must have write permissions for the object. For more information, see [Dialogflow access control](https://cloud.google.com/dialogflow/cx/docs/concept/access-control#storage). */
		agentUri: Option[String] = None,
	  /** Optional. The data format of the exported agent. If not specified, `BLOB` is assumed. */
		dataFormat: Option[Schema.GoogleCloudDialogflowCxV3ExportAgentRequest.DataFormatEnum] = None,
	  /** Optional. Environment name. If not set, draft environment is assumed. Format: `projects//locations//agents//environments/`. */
		environment: Option[String] = None,
	  /** Optional. The Git branch to export the agent to. */
		gitDestination: Option[Schema.GoogleCloudDialogflowCxV3ExportAgentRequestGitDestination] = None,
	  /** Optional. Whether to include BigQuery Export setting. */
		includeBigqueryExportSettings: Option[Boolean] = None
	)
	
	case class GoogleCloudDialogflowCxV3ExportAgentRequestGitDestination(
	  /** Tracking branch for the git push. */
		trackingBranch: Option[String] = None,
	  /** Commit message for the git push. */
		commitMessage: Option[String] = None
	)
	
	object GoogleCloudDialogflowCxV3RestoreAgentRequest {
		enum RestoreOptionEnum extends Enum[RestoreOptionEnum] { case RESTORE_OPTION_UNSPECIFIED, KEEP, FALLBACK }
	}
	case class GoogleCloudDialogflowCxV3RestoreAgentRequest(
	  /** The [Google Cloud Storage](https://cloud.google.com/storage/docs/) URI to restore agent from. The format of this URI must be `gs:///`. Dialogflow performs a read operation for the Cloud Storage object on the caller's behalf, so your request authentication must have read permissions for the object. For more information, see [Dialogflow access control](https://cloud.google.com/dialogflow/cx/docs/concept/access-control#storage). */
		agentUri: Option[String] = None,
	  /** Uncompressed raw byte content for agent. */
		agentContent: Option[String] = None,
	  /** Setting for restoring from a git branch */
		gitSource: Option[Schema.GoogleCloudDialogflowCxV3RestoreAgentRequestGitSource] = None,
	  /** Agent restore mode. If not specified, `KEEP` is assumed. */
		restoreOption: Option[Schema.GoogleCloudDialogflowCxV3RestoreAgentRequest.RestoreOptionEnum] = None
	)
	
	case class GoogleCloudDialogflowCxV3RestoreAgentRequestGitSource(
	  /** tracking branch for the git pull */
		trackingBranch: Option[String] = None
	)
	
	case class GoogleCloudDialogflowCxV3ValidateAgentRequest(
	  /** If not specified, the agent's default language is used. */
		languageCode: Option[String] = None
	)
	
	case class GoogleCloudDialogflowCxV3AgentValidationResult(
	  /** The unique identifier of the agent validation result. Format: `projects//locations//agents//validationResult`. */
		name: Option[String] = None,
	  /** Contains all flow validation results. */
		flowValidationResults: Option[List[Schema.GoogleCloudDialogflowCxV3FlowValidationResult]] = None
	)
	
	case class GoogleCloudDialogflowCxV3GenerativeSettings(
	  /** Format: `projects//locations//agents//generativeSettings`. */
		name: Option[String] = None,
	  /** Settings for Generative Fallback. */
		fallbackSettings: Option[Schema.GoogleCloudDialogflowCxV3GenerativeSettingsFallbackSettings] = None,
	  /** Settings for Generative Safety. */
		generativeSafetySettings: Option[Schema.GoogleCloudDialogflowCxV3SafetySettings] = None,
	  /** Settings for knowledge connector. */
		knowledgeConnectorSettings: Option[Schema.GoogleCloudDialogflowCxV3GenerativeSettingsKnowledgeConnectorSettings] = None,
	  /** Language for this settings. */
		languageCode: Option[String] = None
	)
	
	case class GoogleCloudDialogflowCxV3GenerativeSettingsFallbackSettings(
	  /** Display name of the selected prompt. */
		selectedPrompt: Option[String] = None,
	  /** Stored prompts that can be selected, for example default templates like "conservative" or "chatty", or user defined ones. */
		promptTemplates: Option[List[Schema.GoogleCloudDialogflowCxV3GenerativeSettingsFallbackSettingsPromptTemplate]] = None
	)
	
	case class GoogleCloudDialogflowCxV3GenerativeSettingsFallbackSettingsPromptTemplate(
	  /** Prompt name. */
		displayName: Option[String] = None,
	  /** Prompt text that is sent to a LLM on no-match default, placeholders are filled downstream. For example: "Here is a conversation $conversation, a response is: " */
		promptText: Option[String] = None,
	  /** If the flag is true, the prompt is frozen and cannot be modified by users. */
		frozen: Option[Boolean] = None
	)
	
	case class GoogleCloudDialogflowCxV3SafetySettings(
	  /** Banned phrases for generated text. */
		bannedPhrases: Option[List[Schema.GoogleCloudDialogflowCxV3SafetySettingsPhrase]] = None
	)
	
	case class GoogleCloudDialogflowCxV3SafetySettingsPhrase(
	  /** Required. Text input which can be used for prompt or banned phrases. */
		text: Option[String] = None,
	  /** Required. Language code of the phrase. */
		languageCode: Option[String] = None
	)
	
	case class GoogleCloudDialogflowCxV3GenerativeSettingsKnowledgeConnectorSettings(
	  /** Name of the company, organization or other entity that the agent represents. Used for knowledge connector LLM prompt and for knowledge search. */
		business: Option[String] = None,
	  /** Name of the virtual agent. Used for LLM prompt. Can be left empty. */
		agent: Option[String] = None,
	  /** Identity of the agent, e.g. "virtual agent", "AI assistant". */
		agentIdentity: Option[String] = None,
	  /** Company description, used for LLM prompt, e.g. "a family company selling freshly roasted coffee beans". */
		businessDescription: Option[String] = None,
	  /** Agent scope, e.g. "Example company website", "internal Example company website for employees", "manual of car owner". */
		agentScope: Option[String] = None,
	  /** Whether to disable fallback to Data Store search results (in case the LLM couldn't pick a proper answer). Per default the feature is enabled. */
		disableDataStoreFallback: Option[Boolean] = None
	)
	
	case class GoogleCloudDialogflowCxV3ListChangelogsResponse(
	  /** The list of changelogs. There will be a maximum number of items returned based on the page_size field in the request. The changelogs will be ordered by timestamp. */
		changelogs: Option[List[Schema.GoogleCloudDialogflowCxV3Changelog]] = None,
	  /** Token to retrieve the next page of results, or empty if there are no more results in the list. */
		nextPageToken: Option[String] = None
	)
	
	case class GoogleCloudDialogflowCxV3Changelog(
	  /** The unique identifier of the changelog. Format: `projects//locations//agents//changelogs/`. */
		name: Option[String] = None,
	  /** Email address of the authenticated user. */
		userEmail: Option[String] = None,
	  /** The affected resource display name of the change. */
		displayName: Option[String] = None,
	  /** The action of the change. */
		action: Option[String] = None,
	  /** The affected resource type. */
		`type`: Option[String] = None,
	  /** The affected resource name of the change. */
		resource: Option[String] = None,
	  /** The timestamp of the change. */
		createTime: Option[String] = None,
	  /** The affected language code of the change. */
		languageCode: Option[String] = None
	)
	
	case class GoogleCloudDialogflowCxV3ListDeploymentsResponse(
	  /** The list of deployments. There will be a maximum number of items returned based on the page_size field in the request. The list may in some cases be empty or contain fewer entries than page_size even if this isn't the last page. */
		deployments: Option[List[Schema.GoogleCloudDialogflowCxV3Deployment]] = None,
	  /** Token to retrieve the next page of results, or empty if there are no more results in the list. */
		nextPageToken: Option[String] = None
	)
	
	object GoogleCloudDialogflowCxV3Deployment {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, RUNNING, SUCCEEDED, FAILED }
	}
	case class GoogleCloudDialogflowCxV3Deployment(
	  /** The name of the deployment. Format: projects//locations//agents//environments//deployments/. */
		name: Option[String] = None,
	  /** The name of the flow version for this deployment. Format: projects//locations//agents//flows//versions/. */
		flowVersion: Option[String] = None,
	  /** The current state of the deployment. */
		state: Option[Schema.GoogleCloudDialogflowCxV3Deployment.StateEnum] = None,
	  /** Result of the deployment. */
		result: Option[Schema.GoogleCloudDialogflowCxV3DeploymentResult] = None,
	  /** Start time of this deployment. */
		startTime: Option[String] = None,
	  /** End time of this deployment. */
		endTime: Option[String] = None
	)
	
	case class GoogleCloudDialogflowCxV3DeploymentResult(
	  /** Results of test cases running before the deployment. Format: `projects//locations//agents//testCases//results/`. */
		deploymentTestResults: Option[List[String]] = None,
	  /** The name of the experiment triggered by this deployment. Format: projects//locations//agents//environments//experiments/. */
		experiment: Option[String] = None
	)
	
	object GoogleCloudDialogflowCxV3EntityType {
		enum KindEnum extends Enum[KindEnum] { case KIND_UNSPECIFIED, KIND_MAP, KIND_LIST, KIND_REGEXP }
		enum AutoExpansionModeEnum extends Enum[AutoExpansionModeEnum] { case AUTO_EXPANSION_MODE_UNSPECIFIED, AUTO_EXPANSION_MODE_DEFAULT }
	}
	case class GoogleCloudDialogflowCxV3EntityType(
	  /** The unique identifier of the entity type. Required for EntityTypes.UpdateEntityType. Format: `projects//locations//agents//entityTypes/`. */
		name: Option[String] = None,
	  /** Required. The human-readable name of the entity type, unique within the agent. */
		displayName: Option[String] = None,
	  /** Required. Indicates the kind of entity type. */
		kind: Option[Schema.GoogleCloudDialogflowCxV3EntityType.KindEnum] = None,
	  /** Indicates whether the entity type can be automatically expanded. */
		autoExpansionMode: Option[Schema.GoogleCloudDialogflowCxV3EntityType.AutoExpansionModeEnum] = None,
	  /** The collection of entity entries associated with the entity type. */
		entities: Option[List[Schema.GoogleCloudDialogflowCxV3EntityTypeEntity]] = None,
	  /** Collection of exceptional words and phrases that shouldn't be matched. For example, if you have a size entity type with entry `giant`(an adjective), you might consider adding `giants`(a noun) as an exclusion. If the kind of entity type is `KIND_MAP`, then the phrases specified by entities and excluded phrases should be mutually exclusive. */
		excludedPhrases: Option[List[Schema.GoogleCloudDialogflowCxV3EntityTypeExcludedPhrase]] = None,
	  /** Enables fuzzy entity extraction during classification. */
		enableFuzzyExtraction: Option[Boolean] = None,
	  /** Indicates whether parameters of the entity type should be redacted in log. If redaction is enabled, page parameters and intent parameters referring to the entity type will be replaced by parameter name when logging. */
		redact: Option[Boolean] = None
	)
	
	case class GoogleCloudDialogflowCxV3EntityTypeEntity(
	  /** Required. The primary value associated with this entity entry. For example, if the entity type is &#42;vegetable&#42;, the value could be &#42;scallions&#42;. For `KIND_MAP` entity types: &#42; A canonical value to be used in place of synonyms. For `KIND_LIST` entity types: &#42; A string that can contain references to other entity types (with or without aliases). */
		value: Option[String] = None,
	  /** Required. A collection of value synonyms. For example, if the entity type is &#42;vegetable&#42;, and `value` is &#42;scallions&#42;, a synonym could be &#42;green onions&#42;. For `KIND_LIST` entity types: &#42; This collection must contain exactly one synonym equal to `value`. */
		synonyms: Option[List[String]] = None
	)
	
	case class GoogleCloudDialogflowCxV3EntityTypeExcludedPhrase(
	  /** Required. The word or phrase to be excluded. */
		value: Option[String] = None
	)
	
	case class GoogleCloudDialogflowCxV3ListEntityTypesResponse(
	  /** The list of entity types. There will be a maximum number of items returned based on the page_size field in the request. */
		entityTypes: Option[List[Schema.GoogleCloudDialogflowCxV3EntityType]] = None,
	  /** Token to retrieve the next page of results, or empty if there are no more results in the list. */
		nextPageToken: Option[String] = None
	)
	
	object GoogleCloudDialogflowCxV3ExportEntityTypesRequest {
		enum DataFormatEnum extends Enum[DataFormatEnum] { case DATA_FORMAT_UNSPECIFIED, BLOB, JSON_PACKAGE }
	}
	case class GoogleCloudDialogflowCxV3ExportEntityTypesRequest(
	  /** Required. The name of the entity types to export. Format: `projects//locations//agents//entityTypes/`. */
		entityTypes: Option[List[String]] = None,
	  /** Optional. The [Google Cloud Storage](https://cloud.google.com/storage/docs/) URI to export the entity types to. The format of this URI must be `gs:///`. Dialogflow performs a write operation for the Cloud Storage object on the caller's behalf, so your request authentication must have write permissions for the object. For more information, see [Dialogflow access control](https://cloud.google.com/dialogflow/cx/docs/concept/access-control#storage). */
		entityTypesUri: Option[String] = None,
	  /** Optional. The option to return the serialized entity types inline. */
		entityTypesContentInline: Option[Boolean] = None,
	  /** Optional. The data format of the exported entity types. If not specified, `BLOB` is assumed. */
		dataFormat: Option[Schema.GoogleCloudDialogflowCxV3ExportEntityTypesRequest.DataFormatEnum] = None,
	  /** Optional. The language to retrieve the entity type for. The following fields are language dependent: &#42; `EntityType.entities.value` &#42; `EntityType.entities.synonyms` &#42; `EntityType.excluded_phrases.value` If not specified, all language dependent fields will be retrieved. [Many languages](https://cloud.google.com/dialogflow/docs/reference/language) are supported. Note: languages must be enabled in the agent before they can be used. */
		languageCode: Option[String] = None
	)
	
	object GoogleCloudDialogflowCxV3ImportEntityTypesRequest {
		enum MergeOptionEnum extends Enum[MergeOptionEnum] { case MERGE_OPTION_UNSPECIFIED, REPLACE, MERGE, RENAME, REPORT_CONFLICT, KEEP }
	}
	case class GoogleCloudDialogflowCxV3ImportEntityTypesRequest(
	  /** The [Google Cloud Storage](https://cloud.google.com/storage/docs/) URI to import entity types from. The format of this URI must be `gs:///`. Dialogflow performs a read operation for the Cloud Storage object on the caller's behalf, so your request authentication must have read permissions for the object. For more information, see [Dialogflow access control](https://cloud.google.com/dialogflow/cx/docs/concept/access-control#storage). */
		entityTypesUri: Option[String] = None,
	  /** Uncompressed byte content of entity types. */
		entityTypesContent: Option[Schema.GoogleCloudDialogflowCxV3InlineSource] = None,
	  /** Required. Merge option for importing entity types. */
		mergeOption: Option[Schema.GoogleCloudDialogflowCxV3ImportEntityTypesRequest.MergeOptionEnum] = None,
	  /** Optional. The target entity type to import into. Format: `projects//locations//agents//entity_types/`. If set, there should be only one entity type included in entity_types, of which the type should match the type of the target entity type. All entities in the imported entity type will be added to the target entity type. */
		targetEntityType: Option[String] = None
	)
	
	case class GoogleCloudDialogflowCxV3InlineSource(
	  /** The uncompressed byte content for the objects. */
		content: Option[String] = None
	)
	
	case class GoogleCloudDialogflowCxV3ListIntentsResponse(
	  /** The list of intents. There will be a maximum number of items returned based on the page_size field in the request. */
		intents: Option[List[Schema.GoogleCloudDialogflowCxV3Intent]] = None,
	  /** Token to retrieve the next page of results, or empty if there are no more results in the list. */
		nextPageToken: Option[String] = None
	)
	
	case class GoogleCloudDialogflowCxV3Intent(
	  /** The unique identifier of the intent. Required for the Intents.UpdateIntent method. Intents.CreateIntent populates the name automatically. Format: `projects//locations//agents//intents/`. */
		name: Option[String] = None,
	  /** Required. The human-readable name of the intent, unique within the agent. */
		displayName: Option[String] = None,
	  /** The collection of training phrases the agent is trained on to identify the intent. */
		trainingPhrases: Option[List[Schema.GoogleCloudDialogflowCxV3IntentTrainingPhrase]] = None,
	  /** The collection of parameters associated with the intent. */
		parameters: Option[List[Schema.GoogleCloudDialogflowCxV3IntentParameter]] = None,
	  /** The priority of this intent. Higher numbers represent higher priorities. - If the supplied value is unspecified or 0, the service translates the value to 500,000, which corresponds to the `Normal` priority in the console. - If the supplied value is negative, the intent is ignored in runtime detect intent requests. */
		priority: Option[Int] = None,
	  /** Indicates whether this is a fallback intent. Currently only default fallback intent is allowed in the agent, which is added upon agent creation. Adding training phrases to fallback intent is useful in the case of requests that are mistakenly matched, since training phrases assigned to fallback intents act as negative examples that triggers no-match event. */
		isFallback: Option[Boolean] = None,
	  /** The key/value metadata to label an intent. Labels can contain lowercase letters, digits and the symbols '-' and '_'. International characters are allowed, including letters from unicase alphabets. Keys must start with a letter. Keys and values can be no longer than 63 characters and no more than 128 bytes. Prefix "sys-" is reserved for Dialogflow defined labels. Currently allowed Dialogflow defined labels include: &#42; sys-head &#42; sys-contextual The above labels do not require value. "sys-head" means the intent is a head intent. "sys.contextual" means the intent is a contextual intent. */
		labels: Option[Map[String, String]] = None,
	  /** Human readable description for better understanding an intent like its scope, content, result etc. Maximum character limit: 140 characters. */
		description: Option[String] = None
	)
	
	case class GoogleCloudDialogflowCxV3IntentTrainingPhrase(
	  /** Output only. The unique identifier of the training phrase. */
		id: Option[String] = None,
	  /** Required. The ordered list of training phrase parts. The parts are concatenated in order to form the training phrase. Note: The API does not automatically annotate training phrases like the Dialogflow Console does. Note: Do not forget to include whitespace at part boundaries, so the training phrase is well formatted when the parts are concatenated. If the training phrase does not need to be annotated with parameters, you just need a single part with only the Part.text field set. If you want to annotate the training phrase, you must create multiple parts, where the fields of each part are populated in one of two ways: - `Part.text` is set to a part of the phrase that has no parameters. - `Part.text` is set to a part of the phrase that you want to annotate, and the `parameter_id` field is set. */
		parts: Option[List[Schema.GoogleCloudDialogflowCxV3IntentTrainingPhrasePart]] = None,
	  /** Indicates how many times this example was added to the intent. */
		repeatCount: Option[Int] = None
	)
	
	case class GoogleCloudDialogflowCxV3IntentTrainingPhrasePart(
	  /** Required. The text for this part. */
		text: Option[String] = None,
	  /** The parameter used to annotate this part of the training phrase. This field is required for annotated parts of the training phrase. */
		parameterId: Option[String] = None
	)
	
	case class GoogleCloudDialogflowCxV3IntentParameter(
	  /** Required. The unique identifier of the parameter. This field is used by training phrases to annotate their parts. */
		id: Option[String] = None,
	  /** Required. The entity type of the parameter. Format: `projects/-/locations/-/agents/-/entityTypes/` for system entity types (for example, `projects/-/locations/-/agents/-/entityTypes/sys.date`), or `projects//locations//agents//entityTypes/` for developer entity types. */
		entityType: Option[String] = None,
	  /** Indicates whether the parameter represents a list of values. */
		isList: Option[Boolean] = None,
	  /** Indicates whether the parameter content should be redacted in log. If redaction is enabled, the parameter content will be replaced by parameter name during logging. Note: the parameter content is subject to redaction if either parameter level redaction or entity type level redaction is enabled. */
		redact: Option[Boolean] = None
	)
	
	object GoogleCloudDialogflowCxV3ImportIntentsRequest {
		enum MergeOptionEnum extends Enum[MergeOptionEnum] { case MERGE_OPTION_UNSPECIFIED, REJECT, REPLACE, MERGE, RENAME, REPORT_CONFLICT, KEEP }
	}
	case class GoogleCloudDialogflowCxV3ImportIntentsRequest(
	  /** The [Google Cloud Storage](https://cloud.google.com/storage/docs/) URI to import intents from. The format of this URI must be `gs:///`. Dialogflow performs a read operation for the Cloud Storage object on the caller's behalf, so your request authentication must have read permissions for the object. For more information, see [Dialogflow access control](https://cloud.google.com/dialogflow/cx/docs/concept/access-control#storage). */
		intentsUri: Option[String] = None,
	  /** Uncompressed byte content of intents. */
		intentsContent: Option[Schema.GoogleCloudDialogflowCxV3InlineSource] = None,
	  /** Merge option for importing intents. If not specified, `REJECT` is assumed. */
		mergeOption: Option[Schema.GoogleCloudDialogflowCxV3ImportIntentsRequest.MergeOptionEnum] = None
	)
	
	object GoogleCloudDialogflowCxV3ExportIntentsRequest {
		enum DataFormatEnum extends Enum[DataFormatEnum] { case DATA_FORMAT_UNSPECIFIED, BLOB, JSON, CSV }
	}
	case class GoogleCloudDialogflowCxV3ExportIntentsRequest(
	  /** Required. The name of the intents to export. Format: `projects//locations//agents//intents/`. */
		intents: Option[List[String]] = None,
	  /** Optional. The [Google Cloud Storage](https://cloud.google.com/storage/docs/) URI to export the intents to. The format of this URI must be `gs:///`. Dialogflow performs a write operation for the Cloud Storage object on the caller's behalf, so your request authentication must have write permissions for the object. For more information, see [Dialogflow access control](https://cloud.google.com/dialogflow/cx/docs/concept/access-control#storage). */
		intentsUri: Option[String] = None,
	  /** Optional. The option to return the serialized intents inline. */
		intentsContentInline: Option[Boolean] = None,
	  /** Optional. The data format of the exported intents. If not specified, `BLOB` is assumed. */
		dataFormat: Option[Schema.GoogleCloudDialogflowCxV3ExportIntentsRequest.DataFormatEnum] = None
	)
	
	case class GoogleCloudDialogflowCxV3ListSessionEntityTypesResponse(
	  /** The list of session entity types. There will be a maximum number of items returned based on the page_size field in the request. */
		sessionEntityTypes: Option[List[Schema.GoogleCloudDialogflowCxV3SessionEntityType]] = None,
	  /** Token to retrieve the next page of results, or empty if there are no more results in the list. */
		nextPageToken: Option[String] = None
	)
	
	object GoogleCloudDialogflowCxV3SessionEntityType {
		enum EntityOverrideModeEnum extends Enum[EntityOverrideModeEnum] { case ENTITY_OVERRIDE_MODE_UNSPECIFIED, ENTITY_OVERRIDE_MODE_OVERRIDE, ENTITY_OVERRIDE_MODE_SUPPLEMENT }
	}
	case class GoogleCloudDialogflowCxV3SessionEntityType(
	  /** Required. The unique identifier of the session entity type. Format: `projects//locations//agents//sessions//entityTypes/` or `projects//locations//agents//environments//sessions//entityTypes/`. If `Environment ID` is not specified, we assume default 'draft' environment. */
		name: Option[String] = None,
	  /** Required. Indicates whether the additional data should override or supplement the custom entity type definition. */
		entityOverrideMode: Option[Schema.GoogleCloudDialogflowCxV3SessionEntityType.EntityOverrideModeEnum] = None,
	  /** Required. The collection of entities to override or supplement the custom entity type. */
		entities: Option[List[Schema.GoogleCloudDialogflowCxV3EntityTypeEntity]] = None
	)
	
	case class GoogleCloudDialogflowCxV3DetectIntentRequest(
	  /** The parameters of this query. */
		queryParams: Option[Schema.GoogleCloudDialogflowCxV3QueryParameters] = None,
	  /** Required. The input specification. */
		queryInput: Option[Schema.GoogleCloudDialogflowCxV3QueryInput] = None,
	  /** Instructs the speech synthesizer how to generate the output audio. */
		outputAudioConfig: Option[Schema.GoogleCloudDialogflowCxV3OutputAudioConfig] = None
	)
	
	case class GoogleCloudDialogflowCxV3QueryParameters(
	  /** The time zone of this conversational query from the [time zone database](https://www.iana.org/time-zones), e.g., America/New_York, Europe/Paris. If not provided, the time zone specified in the agent is used. */
		timeZone: Option[String] = None,
	  /** The geo location of this conversational query. */
		geoLocation: Option[Schema.GoogleTypeLatLng] = None,
	  /** Additional session entity types to replace or extend developer entity types with. The entity synonyms apply to all languages and persist for the session of this query. */
		sessionEntityTypes: Option[List[Schema.GoogleCloudDialogflowCxV3SessionEntityType]] = None,
	  /** This field can be used to pass custom data into the webhook associated with the agent. Arbitrary JSON objects are supported. Some integrations that query a Dialogflow agent may provide additional information in the payload. In particular, for the Dialogflow Phone Gateway integration, this field has the form: ``` { "telephony": { "caller_id": "+18558363987" } } ``` */
		payload: Option[Map[String, JsValue]] = None,
	  /** Additional parameters to be put into session parameters. To remove a parameter from the session, clients should explicitly set the parameter value to null. You can reference the session parameters in the agent with the following format: $session.params.parameter-id. Depending on your protocol or client library language, this is a map, associative array, symbol table, dictionary, or JSON object composed of a collection of (MapKey, MapValue) pairs: &#42; MapKey type: string &#42; MapKey value: parameter name &#42; MapValue type: If parameter's entity type is a composite entity then use map, otherwise, depending on the parameter value type, it could be one of string, number, boolean, null, list or map. &#42; MapValue value: If parameter's entity type is a composite entity then use map from composite entity property names to property values, otherwise, use parameter value. */
		parameters: Option[Map[String, JsValue]] = None,
	  /** The unique identifier of the page to override the current page in the session. Format: `projects//locations//agents//flows//pages/`. If `current_page` is specified, the previous state of the session will be ignored by Dialogflow, including the previous page and the previous session parameters. In most cases, current_page and parameters should be configured together to direct a session to a specific state. */
		currentPage: Option[String] = None,
	  /** Whether to disable webhook calls for this request. */
		disableWebhook: Option[Boolean] = None,
	  /** Configures whether sentiment analysis should be performed. If not provided, sentiment analysis is not performed. */
		analyzeQueryTextSentiment: Option[Boolean] = None,
	  /** This field can be used to pass HTTP headers for a webhook call. These headers will be sent to webhook along with the headers that have been configured through Dialogflow web console. The headers defined within this field will overwrite the headers configured through Dialogflow console if there is a conflict. Header names are case-insensitive. Google's specified headers are not allowed. Including: "Host", "Content-Length", "Connection", "From", "User-Agent", "Accept-Encoding", "If-Modified-Since", "If-None-Match", "X-Forwarded-For", etc. */
		webhookHeaders: Option[Map[String, String]] = None,
	  /** A list of flow versions to override for the request. Format: `projects//locations//agents//flows//versions/`. If version 1 of flow X is included in this list, the traffic of flow X will go through version 1 regardless of the version configuration in the environment. Each flow can have at most one version specified in this list. */
		flowVersions: Option[List[String]] = None,
	  /** The channel which this query is for. If specified, only the ResponseMessage associated with the channel will be returned. If no ResponseMessage is associated with the channel, it falls back to the ResponseMessage with unspecified channel. If unspecified, the ResponseMessage with unspecified channel will be returned. */
		channel: Option[String] = None,
	  /** Optional. Configure lifetime of the Dialogflow session. By default, a Dialogflow session remains active and its data is stored for 30 minutes after the last request is sent for the session. This value should be no longer than 1 day. */
		sessionTtl: Option[String] = None,
	  /** Optional. Information about the end-user to improve the relevance and accuracy of generative answers. This will be interpreted and used by a language model, so, for good results, the data should be self-descriptive, and in a simple structure. Example: ```json { "subscription plan": "Business Premium Plus", "devices owned": [ {"model": "Google Pixel 7"}, {"model": "Google Pixel Tablet"} ] } ``` */
		endUserMetadata: Option[Map[String, JsValue]] = None,
	  /** Optional. Search configuration for UCS search queries. */
		searchConfig: Option[Schema.GoogleCloudDialogflowCxV3SearchConfig] = None,
	  /** Optional. If set to true and data stores are involved in serving the request then DetectIntentResponse.query_result.data_store_connection_signals will be filled with data that can help evaluations. */
		populateDataStoreConnectionSignals: Option[Boolean] = None
	)
	
	case class GoogleTypeLatLng(
	  /** The latitude in degrees. It must be in the range [-90.0, +90.0]. */
		latitude: Option[BigDecimal] = None,
	  /** The longitude in degrees. It must be in the range [-180.0, +180.0]. */
		longitude: Option[BigDecimal] = None
	)
	
	case class GoogleCloudDialogflowCxV3SearchConfig(
	  /** Optional. Boosting configuration for the datastores. */
		boostSpecs: Option[List[Schema.GoogleCloudDialogflowCxV3BoostSpecs]] = None,
	  /** Optional. Filter configuration for the datastores. */
		filterSpecs: Option[List[Schema.GoogleCloudDialogflowCxV3FilterSpecs]] = None
	)
	
	case class GoogleCloudDialogflowCxV3BoostSpecs(
	  /** Optional. Data Stores where the boosting configuration is applied. The full names of the referenced data stores. Formats: `projects/{project}/locations/{location}/collections/{collection}/dataStores/{data_store}` `projects/{project}/locations/{location}/dataStores/{data_store}` */
		dataStores: Option[List[String]] = None,
	  /** Optional. A list of boosting specifications. */
		spec: Option[List[Schema.GoogleCloudDialogflowCxV3BoostSpec]] = None
	)
	
	case class GoogleCloudDialogflowCxV3BoostSpec(
	  /** Optional. Condition boost specifications. If a document matches multiple conditions in the specifictions, boost scores from these specifications are all applied and combined in a non-linear way. Maximum number of specifications is 20. */
		conditionBoostSpecs: Option[List[Schema.GoogleCloudDialogflowCxV3BoostSpecConditionBoostSpec]] = None
	)
	
	case class GoogleCloudDialogflowCxV3BoostSpecConditionBoostSpec(
	  /** Optional. An expression which specifies a boost condition. The syntax and supported fields are the same as a filter expression. Examples: &#42; To boost documents with document ID "doc_1" or "doc_2", and color "Red" or "Blue": &#42; (id: ANY("doc_1", "doc_2")) AND (color: ANY("Red","Blue")) */
		condition: Option[String] = None,
	  /** Optional. Strength of the condition boost, which should be in [-1, 1]. Negative boost means demotion. Default is 0.0. Setting to 1.0 gives the document a big promotion. However, it does not necessarily mean that the boosted document will be the top result at all times, nor that other documents will be excluded. Results could still be shown even when none of them matches the condition. And results that are significantly more relevant to the search query can still trump your heavily favored but irrelevant documents. Setting to -1.0 gives the document a big demotion. However, results that are deeply relevant might still be shown. The document will have an upstream battle to get a fairly high ranking, but it is not blocked out completely. Setting to 0.0 means no boost applied. The boosting condition is ignored. */
		boost: Option[BigDecimal] = None,
	  /** Optional. Complex specification for custom ranking based on customer defined attribute value. */
		boostControlSpec: Option[Schema.GoogleCloudDialogflowCxV3BoostSpecConditionBoostSpecBoostControlSpec] = None
	)
	
	object GoogleCloudDialogflowCxV3BoostSpecConditionBoostSpecBoostControlSpec {
		enum AttributeTypeEnum extends Enum[AttributeTypeEnum] { case ATTRIBUTE_TYPE_UNSPECIFIED, NUMERICAL, FRESHNESS }
		enum InterpolationTypeEnum extends Enum[InterpolationTypeEnum] { case INTERPOLATION_TYPE_UNSPECIFIED, LINEAR }
	}
	case class GoogleCloudDialogflowCxV3BoostSpecConditionBoostSpecBoostControlSpec(
	  /** Optional. The name of the field whose value will be used to determine the boost amount. */
		fieldName: Option[String] = None,
	  /** Optional. The attribute type to be used to determine the boost amount. The attribute value can be derived from the field value of the specified field_name. In the case of numerical it is straightforward i.e. attribute_value = numerical_field_value. In the case of freshness however, attribute_value = (time.now() - datetime_field_value). */
		attributeType: Option[Schema.GoogleCloudDialogflowCxV3BoostSpecConditionBoostSpecBoostControlSpec.AttributeTypeEnum] = None,
	  /** Optional. The interpolation type to be applied to connect the control points listed below. */
		interpolationType: Option[Schema.GoogleCloudDialogflowCxV3BoostSpecConditionBoostSpecBoostControlSpec.InterpolationTypeEnum] = None,
	  /** Optional. The control points used to define the curve. The monotonic function (defined through the interpolation_type above) passes through the control points listed here. */
		controlPoints: Option[List[Schema.GoogleCloudDialogflowCxV3BoostSpecConditionBoostSpecBoostControlSpecControlPoint]] = None
	)
	
	case class GoogleCloudDialogflowCxV3BoostSpecConditionBoostSpecBoostControlSpecControlPoint(
	  /** Optional. Can be one of: 1. The numerical field value. 2. The duration spec for freshness: The value must be formatted as an XSD `dayTimeDuration` value (a restricted subset of an ISO 8601 duration value). The pattern for this is: `nDnM]`. */
		attributeValue: Option[String] = None,
	  /** Optional. The value between -1 to 1 by which to boost the score if the attribute_value evaluates to the value specified above. */
		boostAmount: Option[BigDecimal] = None
	)
	
	case class GoogleCloudDialogflowCxV3FilterSpecs(
	  /** Optional. Data Stores where the boosting configuration is applied. The full names of the referenced data stores. Formats: `projects/{project}/locations/{location}/collections/{collection}/dataStores/{data_store}` `projects/{project}/locations/{location}/dataStores/{data_store}` */
		dataStores: Option[List[String]] = None,
	  /** Optional. The filter expression to be applied. Expression syntax is documented at https://cloud.google.com/generative-ai-app-builder/docs/filter-search-metadata#filter-expression-syntax */
		filter: Option[String] = None
	)
	
	case class GoogleCloudDialogflowCxV3QueryInput(
	  /** The natural language text to be processed. */
		text: Option[Schema.GoogleCloudDialogflowCxV3TextInput] = None,
	  /** The intent to be triggered. */
		intent: Option[Schema.GoogleCloudDialogflowCxV3IntentInput] = None,
	  /** The natural language speech audio to be processed. */
		audio: Option[Schema.GoogleCloudDialogflowCxV3AudioInput] = None,
	  /** The event to be triggered. */
		event: Option[Schema.GoogleCloudDialogflowCxV3EventInput] = None,
	  /** The DTMF event to be handled. */
		dtmf: Option[Schema.GoogleCloudDialogflowCxV3DtmfInput] = None,
	  /** Required. The language of the input. See [Language Support](https://cloud.google.com/dialogflow/cx/docs/reference/language) for a list of the currently supported language codes. Note that queries in the same session do not necessarily need to specify the same language. */
		languageCode: Option[String] = None
	)
	
	case class GoogleCloudDialogflowCxV3TextInput(
	  /** Required. The UTF-8 encoded natural language text to be processed. */
		text: Option[String] = None
	)
	
	case class GoogleCloudDialogflowCxV3IntentInput(
	  /** Required. The unique identifier of the intent. Format: `projects//locations//agents//intents/`. */
		intent: Option[String] = None
	)
	
	case class GoogleCloudDialogflowCxV3AudioInput(
	  /** Required. Instructs the speech recognizer how to process the speech audio. */
		config: Option[Schema.GoogleCloudDialogflowCxV3InputAudioConfig] = None,
	  /** The natural language speech audio to be processed. A single request can contain up to 2 minutes of speech audio data. The transcribed text cannot contain more than 256 bytes. For non-streaming audio detect intent, both `config` and `audio` must be provided. For streaming audio detect intent, `config` must be provided in the first request and `audio` must be provided in all following requests. */
		audio: Option[String] = None
	)
	
	object GoogleCloudDialogflowCxV3InputAudioConfig {
		enum AudioEncodingEnum extends Enum[AudioEncodingEnum] { case AUDIO_ENCODING_UNSPECIFIED, AUDIO_ENCODING_LINEAR_16, AUDIO_ENCODING_FLAC, AUDIO_ENCODING_MULAW, AUDIO_ENCODING_AMR, AUDIO_ENCODING_AMR_WB, AUDIO_ENCODING_OGG_OPUS, AUDIO_ENCODING_SPEEX_WITH_HEADER_BYTE, AUDIO_ENCODING_ALAW }
		enum ModelVariantEnum extends Enum[ModelVariantEnum] { case SPEECH_MODEL_VARIANT_UNSPECIFIED, USE_BEST_AVAILABLE, USE_STANDARD, USE_ENHANCED }
	}
	case class GoogleCloudDialogflowCxV3InputAudioConfig(
	  /** Required. Audio encoding of the audio content to process. */
		audioEncoding: Option[Schema.GoogleCloudDialogflowCxV3InputAudioConfig.AudioEncodingEnum] = None,
	  /** Sample rate (in Hertz) of the audio content sent in the query. Refer to [Cloud Speech API documentation](https://cloud.google.com/speech-to-text/docs/basics) for more details. */
		sampleRateHertz: Option[Int] = None,
	  /** Optional. If `true`, Dialogflow returns SpeechWordInfo in StreamingRecognitionResult with information about the recognized speech words, e.g. start and end time offsets. If false or unspecified, Speech doesn't return any word-level information. */
		enableWordInfo: Option[Boolean] = None,
	  /** Optional. A list of strings containing words and phrases that the speech recognizer should recognize with higher likelihood. See [the Cloud Speech documentation](https://cloud.google.com/speech-to-text/docs/basics#phrase-hints) for more details. */
		phraseHints: Option[List[String]] = None,
	  /** Optional. Which Speech model to select for the given request. For more information, see [Speech models](https://cloud.google.com/dialogflow/cx/docs/concept/speech-models). */
		model: Option[String] = None,
	  /** Optional. Which variant of the Speech model to use. */
		modelVariant: Option[Schema.GoogleCloudDialogflowCxV3InputAudioConfig.ModelVariantEnum] = None,
	  /** Optional. If `false` (default), recognition does not cease until the client closes the stream. If `true`, the recognizer will detect a single spoken utterance in input audio. Recognition ceases when it detects the audio's voice has stopped or paused. In this case, once a detected intent is received, the client should close the stream and start a new request with a new stream as needed. Note: This setting is relevant only for streaming methods. */
		singleUtterance: Option[Boolean] = None,
	  /** Configuration of barge-in behavior during the streaming of input audio. */
		bargeInConfig: Option[Schema.GoogleCloudDialogflowCxV3BargeInConfig] = None,
	  /** If `true`, the request will opt out for STT conformer model migration. This field will be deprecated once force migration takes place in June 2024. Please refer to [Dialogflow CX Speech model migration](https://cloud.google.com/dialogflow/cx/docs/concept/speech-model-migration). */
		optOutConformerModelMigration: Option[Boolean] = None
	)
	
	case class GoogleCloudDialogflowCxV3BargeInConfig(
	  /** Duration that is not eligible for barge-in at the beginning of the input audio. */
		noBargeInDuration: Option[String] = None,
	  /** Total duration for the playback at the beginning of the input audio. */
		totalDuration: Option[String] = None
	)
	
	case class GoogleCloudDialogflowCxV3EventInput(
	  /** Name of the event. */
		event: Option[String] = None
	)
	
	case class GoogleCloudDialogflowCxV3DtmfInput(
	  /** The dtmf digits. */
		digits: Option[String] = None,
	  /** The finish digit (if any). */
		finishDigit: Option[String] = None
	)
	
	object GoogleCloudDialogflowCxV3OutputAudioConfig {
		enum AudioEncodingEnum extends Enum[AudioEncodingEnum] { case OUTPUT_AUDIO_ENCODING_UNSPECIFIED, OUTPUT_AUDIO_ENCODING_LINEAR_16, OUTPUT_AUDIO_ENCODING_MP3, OUTPUT_AUDIO_ENCODING_MP3_64_KBPS, OUTPUT_AUDIO_ENCODING_OGG_OPUS, OUTPUT_AUDIO_ENCODING_MULAW, OUTPUT_AUDIO_ENCODING_ALAW }
	}
	case class GoogleCloudDialogflowCxV3OutputAudioConfig(
	  /** Required. Audio encoding of the synthesized audio content. */
		audioEncoding: Option[Schema.GoogleCloudDialogflowCxV3OutputAudioConfig.AudioEncodingEnum] = None,
	  /** Optional. The synthesis sample rate (in hertz) for this audio. If not provided, then the synthesizer will use the default sample rate based on the audio encoding. If this is different from the voice's natural sample rate, then the synthesizer will honor this request by converting to the desired sample rate (which might result in worse audio quality). */
		sampleRateHertz: Option[Int] = None,
	  /** Optional. Configuration of how speech should be synthesized. If not specified, Agent.text_to_speech_settings is applied. */
		synthesizeSpeechConfig: Option[Schema.GoogleCloudDialogflowCxV3SynthesizeSpeechConfig] = None
	)
	
	object GoogleCloudDialogflowCxV3DetectIntentResponse {
		enum ResponseTypeEnum extends Enum[ResponseTypeEnum] { case RESPONSE_TYPE_UNSPECIFIED, PARTIAL, FINAL }
	}
	case class GoogleCloudDialogflowCxV3DetectIntentResponse(
	  /** Output only. The unique identifier of the response. It can be used to locate a response in the training example set or for reporting issues. */
		responseId: Option[String] = None,
	  /** The result of the conversational query. */
		queryResult: Option[Schema.GoogleCloudDialogflowCxV3QueryResult] = None,
	  /** The audio data bytes encoded as specified in the request. Note: The output audio is generated based on the values of default platform text responses found in the `query_result.response_messages` field. If multiple default text responses exist, they will be concatenated when generating audio. If no default platform text responses exist, the generated audio content will be empty. In some scenarios, multiple output audio fields may be present in the response structure. In these cases, only the top-most-level audio output has content. */
		outputAudio: Option[String] = None,
	  /** The config used by the speech synthesizer to generate the output audio. */
		outputAudioConfig: Option[Schema.GoogleCloudDialogflowCxV3OutputAudioConfig] = None,
	  /** Response type. */
		responseType: Option[Schema.GoogleCloudDialogflowCxV3DetectIntentResponse.ResponseTypeEnum] = None,
	  /** Indicates whether the partial response can be cancelled when a later response arrives. e.g. if the agent specified some music as partial response, it can be cancelled. */
		allowCancellation: Option[Boolean] = None
	)
	
	case class GoogleCloudDialogflowCxV3QueryResult(
	  /** If natural language text was provided as input, this field will contain a copy of the text. */
		text: Option[String] = None,
	  /** If an intent was provided as input, this field will contain a copy of the intent identifier. Format: `projects//locations//agents//intents/`. */
		triggerIntent: Option[String] = None,
	  /** If natural language speech audio was provided as input, this field will contain the transcript for the audio. */
		transcript: Option[String] = None,
	  /** If an event was provided as input, this field will contain the name of the event. */
		triggerEvent: Option[String] = None,
	  /** If a DTMF was provided as input, this field will contain a copy of the DtmfInput. */
		dtmf: Option[Schema.GoogleCloudDialogflowCxV3DtmfInput] = None,
	  /** The language that was triggered during intent detection. See [Language Support](https://cloud.google.com/dialogflow/cx/docs/reference/language) for a list of the currently supported language codes. */
		languageCode: Option[String] = None,
	  /** The collected session parameters. Depending on your protocol or client library language, this is a map, associative array, symbol table, dictionary, or JSON object composed of a collection of (MapKey, MapValue) pairs: &#42; MapKey type: string &#42; MapKey value: parameter name &#42; MapValue type: If parameter's entity type is a composite entity then use map, otherwise, depending on the parameter value type, it could be one of string, number, boolean, null, list or map. &#42; MapValue value: If parameter's entity type is a composite entity then use map from composite entity property names to property values, otherwise, use parameter value. */
		parameters: Option[Map[String, JsValue]] = None,
	  /** The list of rich messages returned to the client. Responses vary from simple text messages to more sophisticated, structured payloads used to drive complex logic. */
		responseMessages: Option[List[Schema.GoogleCloudDialogflowCxV3ResponseMessage]] = None,
	  /** The list of webhook call status in the order of call sequence. */
		webhookStatuses: Option[List[Schema.GoogleRpcStatus]] = None,
	  /** The list of webhook payload in WebhookResponse.payload, in the order of call sequence. If some webhook call fails or doesn't return any payload, an empty `Struct` would be used instead. */
		webhookPayloads: Option[List[Map[String, JsValue]]] = None,
	  /** The current Page. Some, not all fields are filled in this message, including but not limited to `name` and `display_name`. */
		currentPage: Option[Schema.GoogleCloudDialogflowCxV3Page] = None,
	  /** The Intent that matched the conversational query. Some, not all fields are filled in this message, including but not limited to: `name` and `display_name`. This field is deprecated, please use QueryResult.match instead. */
		intent: Option[Schema.GoogleCloudDialogflowCxV3Intent] = None,
	  /** The intent detection confidence. Values range from 0.0 (completely uncertain) to 1.0 (completely certain). This value is for informational purpose only and is only used to help match the best intent within the classification threshold. This value may change for the same end-user expression at any time due to a model retraining or change in implementation. This field is deprecated, please use QueryResult.match instead. */
		intentDetectionConfidence: Option[BigDecimal] = None,
	  /** Intent match result, could be an intent or an event. */
		`match`: Option[Schema.GoogleCloudDialogflowCxV3Match] = None,
	  /** The free-form diagnostic info. For example, this field could contain webhook call latency. The fields of this data can change without notice, so you should not write code that depends on its structure. One of the fields is called "Alternative Matched Intents", which may aid with debugging. The following describes these intent results: - The list is empty if no intent was matched to end-user input. - Only intents that are referenced in the currently active flow are included. - The matched intent is included. - Other intents that could have matched end-user input, but did not match because they are referenced by intent routes that are out of [scope](https://cloud.google.com/dialogflow/cx/docs/concept/handler#scope), are included. - Other intents referenced by intent routes in scope that matched end-user input, but had a lower confidence score. */
		diagnosticInfo: Option[Map[String, JsValue]] = None,
	  /** The sentiment analyss result, which depends on `analyze_query_text_sentiment`, specified in the request. */
		sentimentAnalysisResult: Option[Schema.GoogleCloudDialogflowCxV3SentimentAnalysisResult] = None,
	  /** Returns the current advanced settings including IVR settings. Even though the operations configured by these settings are performed by Dialogflow, the client may need to perform special logic at the moment. For example, if Dialogflow exports audio to Google Cloud Storage, then the client may need to wait for the resulting object to appear in the bucket before proceeding. */
		advancedSettings: Option[Schema.GoogleCloudDialogflowCxV3AdvancedSettings] = None,
	  /** Indicates whether the Thumbs up/Thumbs down rating controls are need to be shown for the response in the Dialogflow Messenger widget. */
		allowAnswerFeedback: Option[Boolean] = None,
	  /** Optional. Data store connection feature output signals. Filled only when data stores are involved in serving the query and DetectIntentRequest.populate_data_store_connection_signals is set to true in the request. */
		dataStoreConnectionSignals: Option[Schema.GoogleCloudDialogflowCxV3DataStoreConnectionSignals] = None
	)
	
	object GoogleCloudDialogflowCxV3Match {
		enum MatchTypeEnum extends Enum[MatchTypeEnum] { case MATCH_TYPE_UNSPECIFIED, INTENT, DIRECT_INTENT, PARAMETER_FILLING, NO_MATCH, NO_INPUT, EVENT, KNOWLEDGE_CONNECTOR, PLAYBOOK }
	}
	case class GoogleCloudDialogflowCxV3Match(
	  /** The Intent that matched the query. Some, not all fields are filled in this message, including but not limited to: `name` and `display_name`. Only filled for `INTENT` match type. */
		intent: Option[Schema.GoogleCloudDialogflowCxV3Intent] = None,
	  /** The event that matched the query. Filled for `EVENT`, `NO_MATCH` and `NO_INPUT` match types. */
		event: Option[String] = None,
	  /** The collection of parameters extracted from the query. Depending on your protocol or client library language, this is a map, associative array, symbol table, dictionary, or JSON object composed of a collection of (MapKey, MapValue) pairs: &#42; MapKey type: string &#42; MapKey value: parameter name &#42; MapValue type: If parameter's entity type is a composite entity then use map, otherwise, depending on the parameter value type, it could be one of string, number, boolean, null, list or map. &#42; MapValue value: If parameter's entity type is a composite entity then use map from composite entity property names to property values, otherwise, use parameter value. */
		parameters: Option[Map[String, JsValue]] = None,
	  /** Final text input which was matched during MatchIntent. This value can be different from original input sent in request because of spelling correction or other processing. */
		resolvedInput: Option[String] = None,
	  /** Type of this Match. */
		matchType: Option[Schema.GoogleCloudDialogflowCxV3Match.MatchTypeEnum] = None,
	  /** The confidence of this match. Values range from 0.0 (completely uncertain) to 1.0 (completely certain). This value is for informational purpose only and is only used to help match the best intent within the classification threshold. This value may change for the same end-user expression at any time due to a model retraining or change in implementation. */
		confidence: Option[BigDecimal] = None
	)
	
	case class GoogleCloudDialogflowCxV3SentimentAnalysisResult(
	  /** Sentiment score between -1.0 (negative sentiment) and 1.0 (positive sentiment). */
		score: Option[BigDecimal] = None,
	  /** A non-negative number in the [0, +inf) range, which represents the absolute magnitude of sentiment, regardless of score (positive or negative). */
		magnitude: Option[BigDecimal] = None
	)
	
	case class GoogleCloudDialogflowCxV3DataStoreConnectionSignals(
	  /** Optional. Diagnostic info related to the rewriter model call. */
		rewriterModelCallSignals: Option[Schema.GoogleCloudDialogflowCxV3DataStoreConnectionSignalsRewriterModelCallSignals] = None,
	  /** Optional. Rewritten string query used for search. */
		rewrittenQuery: Option[String] = None,
	  /** Optional. Search snippets included in the answer generation prompt. */
		searchSnippets: Option[List[Schema.GoogleCloudDialogflowCxV3DataStoreConnectionSignalsSearchSnippet]] = None,
	  /** Optional. Diagnostic info related to the answer generation model call. */
		answerGenerationModelCallSignals: Option[Schema.GoogleCloudDialogflowCxV3DataStoreConnectionSignalsAnswerGenerationModelCallSignals] = None,
	  /** Optional. The final compiled answer. */
		answer: Option[String] = None,
	  /** Optional. Answer parts with relevant citations. Concatenation of texts should add up the `answer` (not counting whitespaces). */
		answerParts: Option[List[Schema.GoogleCloudDialogflowCxV3DataStoreConnectionSignalsAnswerPart]] = None,
	  /** Optional. Snippets cited by the answer generation model from the most to least relevant. */
		citedSnippets: Option[List[Schema.GoogleCloudDialogflowCxV3DataStoreConnectionSignalsCitedSnippet]] = None,
	  /** Optional. Grounding signals. */
		groundingSignals: Option[Schema.GoogleCloudDialogflowCxV3DataStoreConnectionSignalsGroundingSignals] = None,
	  /** Optional. Safety check result. */
		safetySignals: Option[Schema.GoogleCloudDialogflowCxV3DataStoreConnectionSignalsSafetySignals] = None
	)
	
	case class GoogleCloudDialogflowCxV3DataStoreConnectionSignalsRewriterModelCallSignals(
	  /** Prompt as sent to the model. */
		renderedPrompt: Option[String] = None,
	  /** Output of the generative model. */
		modelOutput: Option[String] = None,
	  /** Name of the generative model. For example, "gemini-ultra", "gemini-pro", "gemini-1.5-flash" etc. Defaults to "Other" if the model is unknown. */
		model: Option[String] = None
	)
	
	case class GoogleCloudDialogflowCxV3DataStoreConnectionSignalsSearchSnippet(
	  /** Title of the enclosing document. */
		documentTitle: Option[String] = None,
	  /** Uri for the document. Present if specified for the document. */
		documentUri: Option[String] = None,
	  /** Text included in the prompt. */
		text: Option[String] = None
	)
	
	case class GoogleCloudDialogflowCxV3DataStoreConnectionSignalsAnswerGenerationModelCallSignals(
	  /** Prompt as sent to the model. */
		renderedPrompt: Option[String] = None,
	  /** Output of the generative model. */
		modelOutput: Option[String] = None,
	  /** Name of the generative model. For example, "gemini-ultra", "gemini-pro", "gemini-1.5-flash" etc. Defaults to "Other" if the model is unknown. */
		model: Option[String] = None
	)
	
	case class GoogleCloudDialogflowCxV3DataStoreConnectionSignalsAnswerPart(
	  /** Substring of the answer. */
		text: Option[String] = None,
	  /** Citations for this answer part. Indices of `search_snippets`. */
		supportingIndices: Option[List[Int]] = None
	)
	
	case class GoogleCloudDialogflowCxV3DataStoreConnectionSignalsCitedSnippet(
	  /** Details of the snippet. */
		searchSnippet: Option[Schema.GoogleCloudDialogflowCxV3DataStoreConnectionSignalsSearchSnippet] = None,
	  /** Index of the snippet in `search_snippets` field. */
		snippetIndex: Option[Int] = None
	)
	
	object GoogleCloudDialogflowCxV3DataStoreConnectionSignalsGroundingSignals {
		enum DecisionEnum extends Enum[DecisionEnum] { case GROUNDING_DECISION_UNSPECIFIED, ACCEPTED_BY_GROUNDING, REJECTED_BY_GROUNDING }
		enum ScoreEnum extends Enum[ScoreEnum] { case GROUNDING_SCORE_BUCKET_UNSPECIFIED, VERY_LOW, LOW, MEDIUM, HIGH, VERY_HIGH }
	}
	case class GoogleCloudDialogflowCxV3DataStoreConnectionSignalsGroundingSignals(
	  /** Represents the decision of the grounding check. */
		decision: Option[Schema.GoogleCloudDialogflowCxV3DataStoreConnectionSignalsGroundingSignals.DecisionEnum] = None,
	  /** Grounding score bucket setting. */
		score: Option[Schema.GoogleCloudDialogflowCxV3DataStoreConnectionSignalsGroundingSignals.ScoreEnum] = None
	)
	
	object GoogleCloudDialogflowCxV3DataStoreConnectionSignalsSafetySignals {
		enum DecisionEnum extends Enum[DecisionEnum] { case SAFETY_DECISION_UNSPECIFIED, ACCEPTED_BY_SAFETY_CHECK, REJECTED_BY_SAFETY_CHECK }
		enum BannedPhraseMatchEnum extends Enum[BannedPhraseMatchEnum] { case BANNED_PHRASE_MATCH_UNSPECIFIED, BANNED_PHRASE_MATCH_NONE, BANNED_PHRASE_MATCH_QUERY, BANNED_PHRASE_MATCH_RESPONSE }
	}
	case class GoogleCloudDialogflowCxV3DataStoreConnectionSignalsSafetySignals(
	  /** Safety decision. */
		decision: Option[Schema.GoogleCloudDialogflowCxV3DataStoreConnectionSignalsSafetySignals.DecisionEnum] = None,
	  /** Specifies banned phrase match subject. */
		bannedPhraseMatch: Option[Schema.GoogleCloudDialogflowCxV3DataStoreConnectionSignalsSafetySignals.BannedPhraseMatchEnum] = None,
	  /** The matched banned phrase if there was a match. */
		matchedBannedPhrase: Option[String] = None
	)
	
	case class GoogleCloudDialogflowCxV3MatchIntentRequest(
	  /** The parameters of this query. */
		queryParams: Option[Schema.GoogleCloudDialogflowCxV3QueryParameters] = None,
	  /** Required. The input specification. */
		queryInput: Option[Schema.GoogleCloudDialogflowCxV3QueryInput] = None,
	  /** Persist session parameter changes from `query_params`. */
		persistParameterChanges: Option[Boolean] = None
	)
	
	case class GoogleCloudDialogflowCxV3MatchIntentResponse(
	  /** If natural language text was provided as input, this field will contain a copy of the text. */
		text: Option[String] = None,
	  /** If an intent was provided as input, this field will contain a copy of the intent identifier. Format: `projects//locations//agents//intents/`. */
		triggerIntent: Option[String] = None,
	  /** If natural language speech audio was provided as input, this field will contain the transcript for the audio. */
		transcript: Option[String] = None,
	  /** If an event was provided as input, this field will contain a copy of the event name. */
		triggerEvent: Option[String] = None,
	  /** Match results, if more than one, ordered descendingly by the confidence we have that the particular intent matches the query. */
		matches: Option[List[Schema.GoogleCloudDialogflowCxV3Match]] = None,
	  /** The current Page. Some, not all fields are filled in this message, including but not limited to `name` and `display_name`. */
		currentPage: Option[Schema.GoogleCloudDialogflowCxV3Page] = None
	)
	
	case class GoogleCloudDialogflowCxV3FulfillIntentRequest(
	  /** Must be same as the corresponding MatchIntent request, otherwise the behavior is undefined. */
		matchIntentRequest: Option[Schema.GoogleCloudDialogflowCxV3MatchIntentRequest] = None,
	  /** The matched intent/event to fulfill. */
		`match`: Option[Schema.GoogleCloudDialogflowCxV3Match] = None,
	  /** Instructs the speech synthesizer how to generate output audio. */
		outputAudioConfig: Option[Schema.GoogleCloudDialogflowCxV3OutputAudioConfig] = None
	)
	
	case class GoogleCloudDialogflowCxV3FulfillIntentResponse(
	  /** Output only. The unique identifier of the response. It can be used to locate a response in the training example set or for reporting issues. */
		responseId: Option[String] = None,
	  /** The result of the conversational query. */
		queryResult: Option[Schema.GoogleCloudDialogflowCxV3QueryResult] = None,
	  /** The audio data bytes encoded as specified in the request. Note: The output audio is generated based on the values of default platform text responses found in the `query_result.response_messages` field. If multiple default text responses exist, they will be concatenated when generating audio. If no default platform text responses exist, the generated audio content will be empty. In some scenarios, multiple output audio fields may be present in the response structure. In these cases, only the top-most-level audio output has content. */
		outputAudio: Option[String] = None,
	  /** The config used by the speech synthesizer to generate the output audio. */
		outputAudioConfig: Option[Schema.GoogleCloudDialogflowCxV3OutputAudioConfig] = None
	)
	
	case class GoogleCloudDialogflowCxV3SubmitAnswerFeedbackRequest(
	  /** Required. ID of the response to update its feedback. This is the same as DetectIntentResponse.response_id. */
		responseId: Option[String] = None,
	  /** Required. Feedback provided for a bot answer. */
		answerFeedback: Option[Schema.GoogleCloudDialogflowCxV3AnswerFeedback] = None,
	  /** Optional. The mask to control which fields to update. If the mask is not present, all fields will be updated. */
		updateMask: Option[String] = None
	)
	
	object GoogleCloudDialogflowCxV3AnswerFeedback {
		enum RatingEnum extends Enum[RatingEnum] { case RATING_UNSPECIFIED, THUMBS_UP, THUMBS_DOWN }
	}
	case class GoogleCloudDialogflowCxV3AnswerFeedback(
	  /** Optional. Rating from user for the specific Dialogflow response. */
		rating: Option[Schema.GoogleCloudDialogflowCxV3AnswerFeedback.RatingEnum] = None,
	  /** Optional. In case of thumbs down rating provided, users can optionally provide context about the rating. */
		ratingReason: Option[Schema.GoogleCloudDialogflowCxV3AnswerFeedbackRatingReason] = None,
	  /** Optional. Custom rating from the user about the provided answer, with maximum length of 1024 characters. For example, client could use a customized JSON object to indicate the rating. */
		customRating: Option[String] = None
	)
	
	case class GoogleCloudDialogflowCxV3AnswerFeedbackRatingReason(
	  /** Optional. Custom reason labels for thumbs down rating provided by the user. The maximum number of labels allowed is 10 and the maximum length of a single label is 128 characters. */
		reasonLabels: Option[List[String]] = None,
	  /** Optional. Additional feedback about the rating. This field can be populated without choosing a predefined `reason`. */
		feedback: Option[String] = None
	)
	
	case class GoogleCloudDialogflowCxV3ListTransitionRouteGroupsResponse(
	  /** The list of transition route groups. There will be a maximum number of items returned based on the page_size field in the request. The list may in some cases be empty or contain fewer entries than page_size even if this isn't the last page. */
		transitionRouteGroups: Option[List[Schema.GoogleCloudDialogflowCxV3TransitionRouteGroup]] = None,
	  /** Token to retrieve the next page of results, or empty if there are no more results in the list. */
		nextPageToken: Option[String] = None
	)
	
	case class GoogleCloudDialogflowCxV3TransitionRouteGroup(
	  /** The unique identifier of the transition route group. TransitionRouteGroups.CreateTransitionRouteGroup populates the name automatically. Format: `projects//locations//agents//flows//transitionRouteGroups/` . */
		name: Option[String] = None,
	  /** Required. The human-readable name of the transition route group, unique within the flow. The display name can be no longer than 30 characters. */
		displayName: Option[String] = None,
	  /** Transition routes associated with the TransitionRouteGroup. */
		transitionRoutes: Option[List[Schema.GoogleCloudDialogflowCxV3TransitionRoute]] = None
	)
	
	case class GoogleCloudDialogflowCxV3ListTestCasesResponse(
	  /** The list of test cases. There will be a maximum number of items returned based on the page_size field in the request. */
		testCases: Option[List[Schema.GoogleCloudDialogflowCxV3TestCase]] = None,
	  /** Token to retrieve the next page of results, or empty if there are no more results in the list. */
		nextPageToken: Option[String] = None
	)
	
	case class GoogleCloudDialogflowCxV3TestCase(
	  /** The unique identifier of the test case. TestCases.CreateTestCase will populate the name automatically. Otherwise use format: `projects//locations//agents//testCases/`. */
		name: Option[String] = None,
	  /** Tags are short descriptions that users may apply to test cases for organizational and filtering purposes. Each tag should start with "#" and has a limit of 30 characters. */
		tags: Option[List[String]] = None,
	  /** Required. The human-readable name of the test case, unique within the agent. Limit of 200 characters. */
		displayName: Option[String] = None,
	  /** Additional freeform notes about the test case. Limit of 400 characters. */
		notes: Option[String] = None,
	  /** Config for the test case. */
		testConfig: Option[Schema.GoogleCloudDialogflowCxV3TestConfig] = None,
	  /** The conversation turns uttered when the test case was created, in chronological order. These include the canonical set of agent utterances that should occur when the agent is working properly. */
		testCaseConversationTurns: Option[List[Schema.GoogleCloudDialogflowCxV3ConversationTurn]] = None,
	  /** Output only. When the test was created. */
		creationTime: Option[String] = None,
	  /** The latest test result. */
		lastTestResult: Option[Schema.GoogleCloudDialogflowCxV3TestCaseResult] = None
	)
	
	case class GoogleCloudDialogflowCxV3TestConfig(
	  /** Session parameters to be compared when calculating differences. */
		trackingParameters: Option[List[String]] = None,
	  /** Flow name to start the test case with. Format: `projects//locations//agents//flows/`. Only one of `flow` and `page` should be set to indicate the starting point of the test case. If neither is set, the test case will start with start page on the default start flow. */
		flow: Option[String] = None,
	  /** The page to start the test case with. Format: `projects//locations//agents//flows//pages/`. Only one of `flow` and `page` should be set to indicate the starting point of the test case. If neither is set, the test case will start with start page on the default start flow. */
		page: Option[String] = None
	)
	
	case class GoogleCloudDialogflowCxV3ConversationTurn(
	  /** The user input. */
		userInput: Option[Schema.GoogleCloudDialogflowCxV3ConversationTurnUserInput] = None,
	  /** The virtual agent output. */
		virtualAgentOutput: Option[Schema.GoogleCloudDialogflowCxV3ConversationTurnVirtualAgentOutput] = None
	)
	
	case class GoogleCloudDialogflowCxV3ConversationTurnUserInput(
	  /** Supports text input, event input, dtmf input in the test case. */
		input: Option[Schema.GoogleCloudDialogflowCxV3QueryInput] = None,
	  /** Parameters that need to be injected into the conversation during intent detection. */
		injectedParameters: Option[Map[String, JsValue]] = None,
	  /** If webhooks should be allowed to trigger in response to the user utterance. Often if parameters are injected, webhooks should not be enabled. */
		isWebhookEnabled: Option[Boolean] = None,
	  /** Whether sentiment analysis is enabled. */
		enableSentimentAnalysis: Option[Boolean] = None
	)
	
	case class GoogleCloudDialogflowCxV3ConversationTurnVirtualAgentOutput(
	  /** The session parameters available to the bot at this point. */
		sessionParameters: Option[Map[String, JsValue]] = None,
	  /** Output only. If this is part of a result conversation turn, the list of differences between the original run and the replay for this output, if any. */
		differences: Option[List[Schema.GoogleCloudDialogflowCxV3TestRunDifference]] = None,
	  /** Required. Input only. The diagnostic info output for the turn. Required to calculate the testing coverage. */
		diagnosticInfo: Option[Map[String, JsValue]] = None,
	  /** The Intent that triggered the response. Only name and displayName will be set. */
		triggeredIntent: Option[Schema.GoogleCloudDialogflowCxV3Intent] = None,
	  /** The Page on which the utterance was spoken. Only name and displayName will be set. */
		currentPage: Option[Schema.GoogleCloudDialogflowCxV3Page] = None,
	  /** The text responses from the agent for the turn. */
		textResponses: Option[List[Schema.GoogleCloudDialogflowCxV3ResponseMessageText]] = None,
	  /** Response error from the agent in the test result. If set, other output is empty. */
		status: Option[Schema.GoogleRpcStatus] = None
	)
	
	object GoogleCloudDialogflowCxV3TestRunDifference {
		enum TypeEnum extends Enum[TypeEnum] { case DIFF_TYPE_UNSPECIFIED, INTENT, PAGE, PARAMETERS, UTTERANCE, FLOW }
	}
	case class GoogleCloudDialogflowCxV3TestRunDifference(
	  /** The type of diff. */
		`type`: Option[Schema.GoogleCloudDialogflowCxV3TestRunDifference.TypeEnum] = None,
	  /** A human readable description of the diff, showing the actual output vs expected output. */
		description: Option[String] = None
	)
	
	object GoogleCloudDialogflowCxV3TestCaseResult {
		enum TestResultEnum extends Enum[TestResultEnum] { case TEST_RESULT_UNSPECIFIED, PASSED, FAILED }
	}
	case class GoogleCloudDialogflowCxV3TestCaseResult(
	  /** The resource name for the test case result. Format: `projects//locations//agents//testCases//results/`. */
		name: Option[String] = None,
	  /** Environment where the test was run. If not set, it indicates the draft environment. */
		environment: Option[String] = None,
	  /** The conversation turns uttered during the test case replay in chronological order. */
		conversationTurns: Option[List[Schema.GoogleCloudDialogflowCxV3ConversationTurn]] = None,
	  /** Whether the test case passed in the agent environment. */
		testResult: Option[Schema.GoogleCloudDialogflowCxV3TestCaseResult.TestResultEnum] = None,
	  /** The time that the test was run. */
		testTime: Option[String] = None
	)
	
	case class GoogleCloudDialogflowCxV3BatchDeleteTestCasesRequest(
	  /** Required. Format of test case names: `projects//locations//agents//testCases/`. */
		names: Option[List[String]] = None
	)
	
	case class GoogleCloudDialogflowCxV3RunTestCaseRequest(
	  /** Optional. Environment name. If not set, draft environment is assumed. Format: `projects//locations//agents//environments/`. */
		environment: Option[String] = None
	)
	
	case class GoogleCloudDialogflowCxV3BatchRunTestCasesRequest(
	  /** Optional. If not set, draft environment is assumed. Format: `projects//locations//agents//environments/`. */
		environment: Option[String] = None,
	  /** Required. Format: `projects//locations//agents//testCases/`. */
		testCases: Option[List[String]] = None
	)
	
	case class GoogleCloudDialogflowCxV3CalculateCoverageResponse(
	  /** The agent to calculate coverage for. Format: `projects//locations//agents/`. */
		agent: Option[String] = None,
	  /** Intent coverage. */
		intentCoverage: Option[Schema.GoogleCloudDialogflowCxV3IntentCoverage] = None,
	  /** Transition (excluding transition route groups) coverage. */
		transitionCoverage: Option[Schema.GoogleCloudDialogflowCxV3TransitionCoverage] = None,
	  /** Transition route group coverage. */
		routeGroupCoverage: Option[Schema.GoogleCloudDialogflowCxV3TransitionRouteGroupCoverage] = None
	)
	
	case class GoogleCloudDialogflowCxV3IntentCoverage(
	  /** The list of Intents present in the agent */
		intents: Option[List[Schema.GoogleCloudDialogflowCxV3IntentCoverageIntent]] = None,
	  /** The percent of intents in the agent that are covered. */
		coverageScore: Option[BigDecimal] = None
	)
	
	case class GoogleCloudDialogflowCxV3IntentCoverageIntent(
	  /** The intent full resource name */
		intent: Option[String] = None,
	  /** Whether the intent is covered by at least one of the agent's test cases. */
		covered: Option[Boolean] = None
	)
	
	case class GoogleCloudDialogflowCxV3TransitionCoverage(
	  /** The list of Transitions present in the agent. */
		transitions: Option[List[Schema.GoogleCloudDialogflowCxV3TransitionCoverageTransition]] = None,
	  /** The percent of transitions in the agent that are covered. */
		coverageScore: Option[BigDecimal] = None
	)
	
	case class GoogleCloudDialogflowCxV3TransitionCoverageTransition(
	  /** The start node of a transition. */
		source: Option[Schema.GoogleCloudDialogflowCxV3TransitionCoverageTransitionNode] = None,
	  /** The index of a transition in the transition list. Starting from 0. */
		index: Option[Int] = None,
	  /** The end node of a transition. */
		target: Option[Schema.GoogleCloudDialogflowCxV3TransitionCoverageTransitionNode] = None,
	  /** Whether the transition is covered by at least one of the agent's test cases. */
		covered: Option[Boolean] = None,
	  /** Intent route or condition route. */
		transitionRoute: Option[Schema.GoogleCloudDialogflowCxV3TransitionRoute] = None,
	  /** Event handler. */
		eventHandler: Option[Schema.GoogleCloudDialogflowCxV3EventHandler] = None
	)
	
	case class GoogleCloudDialogflowCxV3TransitionCoverageTransitionNode(
	  /** Indicates a transition to a Page. Only some fields such as name and displayname will be set. */
		page: Option[Schema.GoogleCloudDialogflowCxV3Page] = None,
	  /** Indicates a transition to a Flow. Only some fields such as name and displayname will be set. */
		flow: Option[Schema.GoogleCloudDialogflowCxV3Flow] = None
	)
	
	case class GoogleCloudDialogflowCxV3TransitionRouteGroupCoverage(
	  /** Transition route group coverages. */
		coverages: Option[List[Schema.GoogleCloudDialogflowCxV3TransitionRouteGroupCoverageCoverage]] = None,
	  /** The percent of transition routes in all the transition route groups that are covered. */
		coverageScore: Option[BigDecimal] = None
	)
	
	case class GoogleCloudDialogflowCxV3TransitionRouteGroupCoverageCoverage(
	  /** Transition route group metadata. Only name and displayName will be set. */
		routeGroup: Option[Schema.GoogleCloudDialogflowCxV3TransitionRouteGroup] = None,
	  /** The list of transition routes and coverage in the transition route group. */
		transitions: Option[List[Schema.GoogleCloudDialogflowCxV3TransitionRouteGroupCoverageCoverageTransition]] = None,
	  /** The percent of transition routes in the transition route group that are covered. */
		coverageScore: Option[BigDecimal] = None
	)
	
	case class GoogleCloudDialogflowCxV3TransitionRouteGroupCoverageCoverageTransition(
	  /** Intent route or condition route. */
		transitionRoute: Option[Schema.GoogleCloudDialogflowCxV3TransitionRoute] = None,
	  /** Whether the transition route is covered by at least one of the agent's test cases. */
		covered: Option[Boolean] = None
	)
	
	case class GoogleCloudDialogflowCxV3ImportTestCasesRequest(
	  /** The [Google Cloud Storage](https://cloud.google.com/storage/docs/) URI to import test cases from. The format of this URI must be `gs:///`. Dialogflow performs a read operation for the Cloud Storage object on the caller's behalf, so your request authentication must have read permissions for the object. For more information, see [Dialogflow access control](https://cloud.google.com/dialogflow/cx/docs/concept/access-control#storage). */
		gcsUri: Option[String] = None,
	  /** Uncompressed raw byte content for test cases. */
		content: Option[String] = None
	)
	
	object GoogleCloudDialogflowCxV3ExportTestCasesRequest {
		enum DataFormatEnum extends Enum[DataFormatEnum] { case DATA_FORMAT_UNSPECIFIED, BLOB, JSON }
	}
	case class GoogleCloudDialogflowCxV3ExportTestCasesRequest(
	  /** The [Google Cloud Storage](https://cloud.google.com/storage/docs/) URI to export the test cases to. The format of this URI must be `gs:///`. If unspecified, the serialized test cases is returned inline. Dialogflow performs a write operation for the Cloud Storage object on the caller's behalf, so your request authentication must have write permissions for the object. For more information, see [Dialogflow access control](https://cloud.google.com/dialogflow/cx/docs/concept/access-control#storage). */
		gcsUri: Option[String] = None,
	  /** The data format of the exported test cases. If not specified, `BLOB` is assumed. */
		dataFormat: Option[Schema.GoogleCloudDialogflowCxV3ExportTestCasesRequest.DataFormatEnum] = None,
	  /** The filter expression used to filter exported test cases, see [API Filtering](https://aip.dev/160). The expression is case insensitive and supports the following syntax: name = [OR name = ] ... For example: &#42; "name = t1 OR name = t2" matches the test case with the exact resource name "t1" or "t2". */
		filter: Option[String] = None
	)
	
	case class GoogleCloudDialogflowCxV3ListTestCaseResultsResponse(
	  /** The list of test case results. */
		testCaseResults: Option[List[Schema.GoogleCloudDialogflowCxV3TestCaseResult]] = None,
	  /** Token to retrieve the next page of results, or empty if there are no more results in the list. */
		nextPageToken: Option[String] = None
	)
	
	case class GoogleCloudDialogflowCxV3ListWebhooksResponse(
	  /** The list of webhooks. There will be a maximum number of items returned based on the page_size field in the request. */
		webhooks: Option[List[Schema.GoogleCloudDialogflowCxV3Webhook]] = None,
	  /** Token to retrieve the next page of results, or empty if there are no more results in the list. */
		nextPageToken: Option[String] = None
	)
	
	case class GoogleCloudDialogflowCxV3Webhook(
	  /** The unique identifier of the webhook. Required for the Webhooks.UpdateWebhook method. Webhooks.CreateWebhook populates the name automatically. Format: `projects//locations//agents//webhooks/`. */
		name: Option[String] = None,
	  /** Required. The human-readable name of the webhook, unique within the agent. */
		displayName: Option[String] = None,
	  /** Configuration for a generic web service. */
		genericWebService: Option[Schema.GoogleCloudDialogflowCxV3WebhookGenericWebService] = None,
	  /** Configuration for a [Service Directory](https://cloud.google.com/service-directory) service. */
		serviceDirectory: Option[Schema.GoogleCloudDialogflowCxV3WebhookServiceDirectoryConfig] = None,
	  /** Webhook execution timeout. Execution is considered failed if Dialogflow doesn't receive a response from webhook at the end of the timeout period. Defaults to 5 seconds, maximum allowed timeout is 30 seconds. */
		timeout: Option[String] = None,
	  /** Indicates whether the webhook is disabled. */
		disabled: Option[Boolean] = None
	)
	
	object GoogleCloudDialogflowCxV3WebhookGenericWebService {
		enum ServiceAgentAuthEnum extends Enum[ServiceAgentAuthEnum] { case SERVICE_AGENT_AUTH_UNSPECIFIED, NONE, ID_TOKEN, ACCESS_TOKEN }
		enum WebhookTypeEnum extends Enum[WebhookTypeEnum] { case WEBHOOK_TYPE_UNSPECIFIED, STANDARD, FLEXIBLE }
		enum HttpMethodEnum extends Enum[HttpMethodEnum] { case HTTP_METHOD_UNSPECIFIED, POST, GET, HEAD, PUT, DELETE, PATCH, OPTIONS }
	}
	case class GoogleCloudDialogflowCxV3WebhookGenericWebService(
	  /** Required. The webhook URI for receiving POST requests. It must use https protocol. */
		uri: Option[String] = None,
	  /** The user name for HTTP Basic authentication. */
		username: Option[String] = None,
	  /** The password for HTTP Basic authentication. */
		password: Option[String] = None,
	  /** The HTTP request headers to send together with webhook requests. */
		requestHeaders: Option[Map[String, String]] = None,
	  /** Optional. Specifies a list of allowed custom CA certificates (in DER format) for HTTPS verification. This overrides the default SSL trust store. If this is empty or unspecified, Dialogflow will use Google's default trust store to verify certificates. N.B. Make sure the HTTPS server certificates are signed with "subject alt name". For instance a certificate can be self-signed using the following command, ``` openssl x509 -req -days 200 -in example.com.csr \ -signkey example.com.key \ -out example.com.crt \ -extfile <(printf "\nsubjectAltName='DNS:www.example.com'") ``` */
		allowedCaCerts: Option[List[String]] = None,
	  /** Optional. The OAuth configuration of the webhook. If specified, Dialogflow will initiate the OAuth client credential flow to exchange an access token from the 3rd party platform and put it in the auth header. */
		oauthConfig: Option[Schema.GoogleCloudDialogflowCxV3WebhookGenericWebServiceOAuthConfig] = None,
	  /** Optional. Indicate the auth token type generated from the [Diglogflow service agent](https://cloud.google.com/iam/docs/service-agents#dialogflow-service-agent). The generated token is sent in the Authorization header. */
		serviceAgentAuth: Option[Schema.GoogleCloudDialogflowCxV3WebhookGenericWebService.ServiceAgentAuthEnum] = None,
	  /** Optional. Type of the webhook. */
		webhookType: Option[Schema.GoogleCloudDialogflowCxV3WebhookGenericWebService.WebhookTypeEnum] = None,
	  /** Optional. HTTP method for the flexible webhook calls. Standard webhook always uses POST. */
		httpMethod: Option[Schema.GoogleCloudDialogflowCxV3WebhookGenericWebService.HttpMethodEnum] = None,
	  /** Optional. Defines a custom JSON object as request body to send to flexible webhook. */
		requestBody: Option[String] = None,
	  /** Optional. Maps the values extracted from specific fields of the flexible webhook response into session parameters. - Key: session parameter name - Value: field path in the webhook response */
		parameterMapping: Option[Map[String, String]] = None
	)
	
	case class GoogleCloudDialogflowCxV3WebhookGenericWebServiceOAuthConfig(
	  /** Required. The client ID provided by the 3rd party platform. */
		clientId: Option[String] = None,
	  /** Required. The client secret provided by the 3rd party platform. */
		clientSecret: Option[String] = None,
	  /** Required. The token endpoint provided by the 3rd party platform to exchange an access token. */
		tokenEndpoint: Option[String] = None,
	  /** Optional. The OAuth scopes to grant. */
		scopes: Option[List[String]] = None
	)
	
	case class GoogleCloudDialogflowCxV3WebhookServiceDirectoryConfig(
	  /** Required. The name of [Service Directory](https://cloud.google.com/service-directory) service. Format: `projects//locations//namespaces//services/`. `Location ID` of the service directory must be the same as the location of the agent. */
		service: Option[String] = None,
	  /** Generic Service configuration of this webhook. */
		genericWebService: Option[Schema.GoogleCloudDialogflowCxV3WebhookGenericWebService] = None
	)
	
	case class GoogleCloudDialogflowCxV3ListEnvironmentsResponse(
	  /** The list of environments. There will be a maximum number of items returned based on the page_size field in the request. The list may in some cases be empty or contain fewer entries than page_size even if this isn't the last page. */
		environments: Option[List[Schema.GoogleCloudDialogflowCxV3Environment]] = None,
	  /** Token to retrieve the next page of results, or empty if there are no more results in the list. */
		nextPageToken: Option[String] = None
	)
	
	case class GoogleCloudDialogflowCxV3Environment(
	  /** The name of the environment. Format: `projects//locations//agents//environments/`. */
		name: Option[String] = None,
	  /** Required. The human-readable name of the environment (unique in an agent). Limit of 64 characters. */
		displayName: Option[String] = None,
	  /** The human-readable description of the environment. The maximum length is 500 characters. If exceeded, the request is rejected. */
		description: Option[String] = None,
	  /** A list of configurations for flow versions. You should include version configs for all flows that are reachable from `Start Flow` in the agent. Otherwise, an error will be returned. */
		versionConfigs: Option[List[Schema.GoogleCloudDialogflowCxV3EnvironmentVersionConfig]] = None,
	  /** Output only. Update time of this environment. */
		updateTime: Option[String] = None,
	  /** The test cases config for continuous tests of this environment. */
		testCasesConfig: Option[Schema.GoogleCloudDialogflowCxV3EnvironmentTestCasesConfig] = None,
	  /** The webhook configuration for this environment. */
		webhookConfig: Option[Schema.GoogleCloudDialogflowCxV3EnvironmentWebhookConfig] = None
	)
	
	case class GoogleCloudDialogflowCxV3EnvironmentVersionConfig(
	  /** Required. Both flow and playbook versions are supported. Format for flow version: projects//locations//agents//flows//versions/. Format for playbook version: projects//locations//agents//playbooks//versions/. */
		version: Option[String] = None
	)
	
	case class GoogleCloudDialogflowCxV3EnvironmentTestCasesConfig(
	  /** A list of test case names to run. They should be under the same agent. Format of each test case name: `projects//locations//agents//testCases/` */
		testCases: Option[List[String]] = None,
	  /** Whether to run test cases in TestCasesConfig.test_cases periodically. Default false. If set to true, run once a day. */
		enableContinuousRun: Option[Boolean] = None,
	  /** Whether to run test cases in TestCasesConfig.test_cases before deploying a flow version to the environment. Default false. */
		enablePredeploymentRun: Option[Boolean] = None
	)
	
	case class GoogleCloudDialogflowCxV3EnvironmentWebhookConfig(
	  /** The list of webhooks to override for the agent environment. The webhook must exist in the agent. You can override fields in `generic_web_service` and `service_directory`. */
		webhookOverrides: Option[List[Schema.GoogleCloudDialogflowCxV3Webhook]] = None
	)
	
	case class GoogleCloudDialogflowCxV3LookupEnvironmentHistoryResponse(
	  /** Represents a list of snapshots for an environment. Time of the snapshots is stored in `update_time`. */
		environments: Option[List[Schema.GoogleCloudDialogflowCxV3Environment]] = None,
	  /** Token to retrieve the next page of results, or empty if there are no more results in the list. */
		nextPageToken: Option[String] = None
	)
	
	case class GoogleCloudDialogflowCxV3RunContinuousTestRequest(
	
	)
	
	case class GoogleCloudDialogflowCxV3ListContinuousTestResultsResponse(
	  /** The list of continuous test results. */
		continuousTestResults: Option[List[Schema.GoogleCloudDialogflowCxV3ContinuousTestResult]] = None,
	  /** Token to retrieve the next page of results, or empty if there are no more results in the list. */
		nextPageToken: Option[String] = None
	)
	
	object GoogleCloudDialogflowCxV3ContinuousTestResult {
		enum ResultEnum extends Enum[ResultEnum] { case AGGREGATED_TEST_RESULT_UNSPECIFIED, PASSED, FAILED }
	}
	case class GoogleCloudDialogflowCxV3ContinuousTestResult(
	  /** The resource name for the continuous test result. Format: `projects//locations//agents//environments//continuousTestResults/`. */
		name: Option[String] = None,
	  /** The result of this continuous test run, i.e. whether all the tests in this continuous test run pass or not. */
		result: Option[Schema.GoogleCloudDialogflowCxV3ContinuousTestResult.ResultEnum] = None,
	  /** A list of individual test case results names in this continuous test run. */
		testCaseResults: Option[List[String]] = None,
	  /** Time when the continuous testing run starts. */
		runTime: Option[String] = None
	)
	
	case class GoogleCloudDialogflowCxV3DeployFlowRequest(
	  /** Required. The flow version to deploy. Format: `projects//locations//agents//flows//versions/`. */
		flowVersion: Option[String] = None
	)
	
	case class GoogleCloudDialogflowCxV3ListExperimentsResponse(
	  /** The list of experiments. There will be a maximum number of items returned based on the page_size field in the request. The list may in some cases be empty or contain fewer entries than page_size even if this isn't the last page. */
		experiments: Option[List[Schema.GoogleCloudDialogflowCxV3Experiment]] = None,
	  /** Token to retrieve the next page of results, or empty if there are no more results in the list. */
		nextPageToken: Option[String] = None
	)
	
	object GoogleCloudDialogflowCxV3Experiment {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, DRAFT, RUNNING, DONE, ROLLOUT_FAILED }
	}
	case class GoogleCloudDialogflowCxV3Experiment(
	  /** The name of the experiment. Format: projects//locations//agents//environments//experiments/. */
		name: Option[String] = None,
	  /** Required. The human-readable name of the experiment (unique in an environment). Limit of 64 characters. */
		displayName: Option[String] = None,
	  /** The human-readable description of the experiment. */
		description: Option[String] = None,
	  /** The current state of the experiment. Transition triggered by Experiments.StartExperiment: DRAFT->RUNNING. Transition triggered by Experiments.CancelExperiment: DRAFT->DONE or RUNNING->DONE. */
		state: Option[Schema.GoogleCloudDialogflowCxV3Experiment.StateEnum] = None,
	  /** The definition of the experiment. */
		definition: Option[Schema.GoogleCloudDialogflowCxV3ExperimentDefinition] = None,
	  /** The configuration for auto rollout. If set, there should be exactly two variants in the experiment (control variant being the default version of the flow), the traffic allocation for the non-control variant will gradually increase to 100% when conditions are met, and eventually replace the control variant to become the default version of the flow. */
		rolloutConfig: Option[Schema.GoogleCloudDialogflowCxV3RolloutConfig] = None,
	  /** State of the auto rollout process. */
		rolloutState: Option[Schema.GoogleCloudDialogflowCxV3RolloutState] = None,
	  /** The reason why rollout has failed. Should only be set when state is ROLLOUT_FAILED. */
		rolloutFailureReason: Option[String] = None,
	  /** Inference result of the experiment. */
		result: Option[Schema.GoogleCloudDialogflowCxV3ExperimentResult] = None,
	  /** Creation time of this experiment. */
		createTime: Option[String] = None,
	  /** Start time of this experiment. */
		startTime: Option[String] = None,
	  /** End time of this experiment. */
		endTime: Option[String] = None,
	  /** Last update time of this experiment. */
		lastUpdateTime: Option[String] = None,
	  /** Maximum number of days to run the experiment/rollout. If auto-rollout is not enabled, default value and maximum will be 30 days. If auto-rollout is enabled, default value and maximum will be 6 days. */
		experimentLength: Option[String] = None,
	  /** The history of updates to the experiment variants. */
		variantsHistory: Option[List[Schema.GoogleCloudDialogflowCxV3VariantsHistory]] = None
	)
	
	case class GoogleCloudDialogflowCxV3ExperimentDefinition(
	  /** The condition defines which subset of sessions are selected for this experiment. If not specified, all sessions are eligible. E.g. "query_input.language_code=en" See the [conditions reference](https://cloud.google.com/dialogflow/cx/docs/reference/condition). */
		condition: Option[String] = None,
	  /** The flow versions as the variants of this experiment. */
		versionVariants: Option[Schema.GoogleCloudDialogflowCxV3VersionVariants] = None
	)
	
	case class GoogleCloudDialogflowCxV3VersionVariants(
	  /** A list of flow version variants. */
		variants: Option[List[Schema.GoogleCloudDialogflowCxV3VersionVariantsVariant]] = None
	)
	
	case class GoogleCloudDialogflowCxV3VersionVariantsVariant(
	  /** The name of the flow version. Format: `projects//locations//agents//flows//versions/`. */
		version: Option[String] = None,
	  /** Percentage of the traffic which should be routed to this version of flow. Traffic allocation for a single flow must sum up to 1.0. */
		trafficAllocation: Option[BigDecimal] = None,
	  /** Whether the variant is for the control group. */
		isControlGroup: Option[Boolean] = None
	)
	
	case class GoogleCloudDialogflowCxV3RolloutConfig(
	  /** Steps to roll out a flow version. Steps should be sorted by percentage in ascending order. */
		rolloutSteps: Option[List[Schema.GoogleCloudDialogflowCxV3RolloutConfigRolloutStep]] = None,
	  /** The conditions that are used to evaluate the success of a rollout step. If not specified, all rollout steps will proceed to the next one unless failure conditions are met. E.g. "containment_rate > 60% AND callback_rate < 20%". See the [conditions reference](https://cloud.google.com/dialogflow/cx/docs/reference/condition). */
		rolloutCondition: Option[String] = None,
	  /** The conditions that are used to evaluate the failure of a rollout step. If not specified, no rollout steps will fail. E.g. "containment_rate < 10% OR average_turn_count < 3". See the [conditions reference](https://cloud.google.com/dialogflow/cx/docs/reference/condition). */
		failureCondition: Option[String] = None
	)
	
	case class GoogleCloudDialogflowCxV3RolloutConfigRolloutStep(
	  /** The name of the rollout step; */
		displayName: Option[String] = None,
	  /** The percentage of traffic allocated to the flow version of this rollout step. (0%, 100%]. */
		trafficPercent: Option[Int] = None,
	  /** The minimum time that this step should last. Should be longer than 1 hour. If not set, the default minimum duration for each step will be 1 hour. */
		minDuration: Option[String] = None
	)
	
	case class GoogleCloudDialogflowCxV3RolloutState(
	  /** Display name of the current auto rollout step. */
		step: Option[String] = None,
	  /** Index of the current step in the auto rollout steps list. */
		stepIndex: Option[Int] = None,
	  /** Start time of the current step. */
		startTime: Option[String] = None
	)
	
	case class GoogleCloudDialogflowCxV3ExperimentResult(
	  /** Version variants and metrics. */
		versionMetrics: Option[List[Schema.GoogleCloudDialogflowCxV3ExperimentResultVersionMetrics]] = None,
	  /** The last time the experiment's stats data was updated. Will have default value if stats have never been computed for this experiment. */
		lastUpdateTime: Option[String] = None
	)
	
	case class GoogleCloudDialogflowCxV3ExperimentResultVersionMetrics(
	  /** The name of the flow Version. Format: `projects//locations//agents//flows//versions/`. */
		version: Option[String] = None,
	  /** The metrics and corresponding confidence intervals in the inference result. */
		metrics: Option[List[Schema.GoogleCloudDialogflowCxV3ExperimentResultMetric]] = None,
	  /** Number of sessions that were allocated to this version. */
		sessionCount: Option[Int] = None
	)
	
	object GoogleCloudDialogflowCxV3ExperimentResultMetric {
		enum TypeEnum extends Enum[TypeEnum] { case METRIC_UNSPECIFIED, CONTAINED_SESSION_NO_CALLBACK_RATE, LIVE_AGENT_HANDOFF_RATE, CALLBACK_SESSION_RATE, ABANDONED_SESSION_RATE, SESSION_END_RATE }
		enum CountTypeEnum extends Enum[CountTypeEnum] { case COUNT_TYPE_UNSPECIFIED, TOTAL_NO_MATCH_COUNT, TOTAL_TURN_COUNT, AVERAGE_TURN_COUNT }
	}
	case class GoogleCloudDialogflowCxV3ExperimentResultMetric(
	  /** Ratio-based metric type. Only one of type or count_type is specified in each Metric. */
		`type`: Option[Schema.GoogleCloudDialogflowCxV3ExperimentResultMetric.TypeEnum] = None,
	  /** Count-based metric type. Only one of type or count_type is specified in each Metric. */
		countType: Option[Schema.GoogleCloudDialogflowCxV3ExperimentResultMetric.CountTypeEnum] = None,
	  /** Ratio value of a metric. */
		ratio: Option[BigDecimal] = None,
	  /** Count value of a metric. */
		count: Option[BigDecimal] = None,
	  /** The probability that the treatment is better than all other treatments in the experiment */
		confidenceInterval: Option[Schema.GoogleCloudDialogflowCxV3ExperimentResultConfidenceInterval] = None
	)
	
	case class GoogleCloudDialogflowCxV3ExperimentResultConfidenceInterval(
	  /** The confidence level used to construct the interval, i.e. there is X% chance that the true value is within this interval. */
		confidenceLevel: Option[BigDecimal] = None,
	  /** The percent change between an experiment metric's value and the value for its control. */
		ratio: Option[BigDecimal] = None,
	  /** Lower bound of the interval. */
		lowerBound: Option[BigDecimal] = None,
	  /** Upper bound of the interval. */
		upperBound: Option[BigDecimal] = None
	)
	
	case class GoogleCloudDialogflowCxV3VariantsHistory(
	  /** The flow versions as the variants. */
		versionVariants: Option[Schema.GoogleCloudDialogflowCxV3VersionVariants] = None,
	  /** Update time of the variants. */
		updateTime: Option[String] = None
	)
	
	case class GoogleCloudDialogflowCxV3StartExperimentRequest(
	
	)
	
	case class GoogleCloudDialogflowCxV3StopExperimentRequest(
	
	)
	
	case class GoogleCloudDialogflowCxV3ListGeneratorsResponse(
	  /** The list of generators. There will be a maximum number of items returned based on the page_size field in the request. */
		generators: Option[List[Schema.GoogleCloudDialogflowCxV3Generator]] = None,
	  /** Token to retrieve the next page of results, or empty if there are no more results in the list. */
		nextPageToken: Option[String] = None
	)
	
	case class GoogleCloudDialogflowCxV3Generator(
	  /** The unique identifier of the generator. Must be set for the Generators.UpdateGenerator method. Generators.CreateGenerate populates the name automatically. Format: `projects//locations//agents//generators/`. */
		name: Option[String] = None,
	  /** Required. The human-readable name of the generator, unique within the agent. The prompt contains pre-defined parameters such as $conversation, $last-user-utterance, etc. populated by Dialogflow. It can also contain custom placeholders which will be resolved during fulfillment. */
		displayName: Option[String] = None,
	  /** Required. Prompt for the LLM model. */
		promptText: Option[Schema.GoogleCloudDialogflowCxV3Phrase] = None,
	  /** Optional. List of custom placeholders in the prompt text. */
		placeholders: Option[List[Schema.GoogleCloudDialogflowCxV3GeneratorPlaceholder]] = None,
	  /** Parameters passed to the LLM to configure its behavior. */
		modelParameter: Option[Schema.GoogleCloudDialogflowCxV3GeneratorModelParameter] = None
	)
	
	case class GoogleCloudDialogflowCxV3Phrase(
	  /** Required. Text input which can be used for prompt or banned phrases. */
		text: Option[String] = None
	)
	
	case class GoogleCloudDialogflowCxV3GeneratorPlaceholder(
	  /** Unique ID used to map custom placeholder to parameters in fulfillment. */
		id: Option[String] = None,
	  /** Custom placeholder value in the prompt text. */
		name: Option[String] = None
	)
	
	case class GoogleCloudDialogflowCxV3GeneratorModelParameter(
	  /** The temperature used for sampling. Temperature sampling occurs after both topP and topK have been applied. Valid range: [0.0, 1.0] Low temperature = less random. High temperature = more random. */
		temperature: Option[BigDecimal] = None,
	  /** The maximum number of tokens to generate. */
		maxDecodeSteps: Option[Int] = None,
	  /** If set, only the tokens comprising the top top_p probability mass are considered. If both top_p and top_k are set, top_p will be used for further refining candidates selected with top_k. Valid range: (0.0, 1.0]. Small topP = less random. Large topP = more random. */
		topP: Option[BigDecimal] = None,
	  /** If set, the sampling process in each step is limited to the top_k tokens with highest probabilities. Valid range: [1, 40] or 1000+. Small topK = less random. Large topK = more random. */
		topK: Option[Int] = None
	)
	
	case class GoogleCloudDialogflowCxV3ListVersionsResponse(
	  /** A list of versions. There will be a maximum number of items returned based on the page_size field in the request. The list may in some cases be empty or contain fewer entries than page_size even if this isn't the last page. */
		versions: Option[List[Schema.GoogleCloudDialogflowCxV3Version]] = None,
	  /** Token to retrieve the next page of results, or empty if there are no more results in the list. */
		nextPageToken: Option[String] = None
	)
	
	object GoogleCloudDialogflowCxV3Version {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, RUNNING, SUCCEEDED, FAILED }
	}
	case class GoogleCloudDialogflowCxV3Version(
	  /** Format: projects//locations//agents//flows//versions/. Version ID is a self-increasing number generated by Dialogflow upon version creation. */
		name: Option[String] = None,
	  /** Required. The human-readable name of the version. Limit of 64 characters. */
		displayName: Option[String] = None,
	  /** The description of the version. The maximum length is 500 characters. If exceeded, the request is rejected. */
		description: Option[String] = None,
	  /** Output only. The NLU settings of the flow at version creation. */
		nluSettings: Option[Schema.GoogleCloudDialogflowCxV3NluSettings] = None,
	  /** Output only. Create time of the version. */
		createTime: Option[String] = None,
	  /** Output only. The state of this version. This field is read-only and cannot be set by create and update methods. */
		state: Option[Schema.GoogleCloudDialogflowCxV3Version.StateEnum] = None
	)
	
	case class GoogleCloudDialogflowCxV3LoadVersionRequest(
	  /** This field is used to prevent accidental overwrite of other agent resources, which can potentially impact other flow's behavior. If `allow_override_agent_resources` is false, conflicted agent-level resources will not be overridden (i.e. intents, entities, webhooks). */
		allowOverrideAgentResources: Option[Boolean] = None
	)
	
	case class GoogleCloudDialogflowCxV3CompareVersionsRequest(
	  /** Required. Name of the target flow version to compare with the base version. Use version ID `0` to indicate the draft version of the specified flow. Format: `projects//locations//agents//flows//versions/`. */
		targetVersion: Option[String] = None,
	  /** The language to compare the flow versions for. If not specified, the agent's default language is used. [Many languages](https://cloud.google.com/dialogflow/docs/reference/language) are supported. Note: languages must be enabled in the agent before they can be used. */
		languageCode: Option[String] = None
	)
	
	case class GoogleCloudDialogflowCxV3CompareVersionsResponse(
	  /** JSON representation of the base version content. */
		baseVersionContentJson: Option[String] = None,
	  /** JSON representation of the target version content. */
		targetVersionContentJson: Option[String] = None,
	  /** The timestamp when the two version compares. */
		compareTime: Option[String] = None
	)
	
	case class GoogleCloudLocationListLocationsResponse(
	  /** A list of locations that matches the specified filter in the request. */
		locations: Option[List[Schema.GoogleCloudLocationLocation]] = None,
	  /** The standard List next-page token. */
		nextPageToken: Option[String] = None
	)
	
	case class GoogleCloudLocationLocation(
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
	
	case class GoogleCloudDialogflowCxV3CreateVersionOperationMetadata(
	  /** Name of the created version. Format: `projects//locations//agents//flows//versions/`. */
		version: Option[String] = None
	)
	
	case class GoogleCloudDialogflowCxV3ExportAgentResponse(
	  /** The URI to a file containing the exported agent. This field is populated if `agent_uri` is specified in ExportAgentRequest. */
		agentUri: Option[String] = None,
	  /** Uncompressed raw byte content for agent. This field is populated if none of `agent_uri` and `git_destination` are specified in ExportAgentRequest. */
		agentContent: Option[String] = None,
	  /** Commit SHA of the git push. This field is populated if `git_destination` is specified in ExportAgentRequest. */
		commitSha: Option[String] = None
	)
	
	case class GoogleCloudDialogflowCxV3ExportFlowResponse(
	  /** The URI to a file containing the exported flow. This field is populated only if `flow_uri` is specified in ExportFlowRequest. */
		flowUri: Option[String] = None,
	  /** Uncompressed raw byte content for flow. */
		flowContent: Option[String] = None
	)
	
	case class GoogleCloudDialogflowCxV3ExportIntentsMetadata(
	
	)
	
	case class GoogleCloudDialogflowCxV3ExportIntentsResponse(
	  /** The URI to a file containing the exported intents. This field is populated only if `intents_uri` is specified in ExportIntentsRequest. */
		intentsUri: Option[String] = None,
	  /** Uncompressed byte content for intents. This field is populated only if `intents_content_inline` is set to true in ExportIntentsRequest. */
		intentsContent: Option[Schema.GoogleCloudDialogflowCxV3InlineDestination] = None
	)
	
	case class GoogleCloudDialogflowCxV3InlineDestination(
	  /** Output only. The uncompressed byte content for the objects. Only populated in responses. */
		content: Option[String] = None
	)
	
	case class GoogleCloudDialogflowCxV3ImportFlowResponse(
	  /** The unique identifier of the new flow. Format: `projects//locations//agents//flows/`. */
		flow: Option[String] = None
	)
	
	case class GoogleCloudDialogflowCxV3ImportEntityTypesMetadata(
	
	)
	
	case class GoogleCloudDialogflowCxV3ImportEntityTypesResponse(
	  /** The unique identifier of the imported entity types. Format: `projects//locations//agents//entity_types/`. */
		entityTypes: Option[List[String]] = None,
	  /** Info which resources have conflicts when REPORT_CONFLICT merge_option is set in ImportEntityTypesRequest. */
		conflictingResources: Option[Schema.GoogleCloudDialogflowCxV3ImportEntityTypesResponseConflictingResources] = None
	)
	
	case class GoogleCloudDialogflowCxV3ImportEntityTypesResponseConflictingResources(
	  /** Display names of conflicting entity types. */
		entityTypeDisplayNames: Option[List[String]] = None,
	  /** Display names of conflicting entities. */
		entityDisplayNames: Option[List[String]] = None
	)
	
	case class GoogleCloudDialogflowCxV3ExportEntityTypesMetadata(
	
	)
	
	case class GoogleCloudDialogflowCxV3ExportEntityTypesResponse(
	  /** The URI to a file containing the exported entity types. This field is populated only if `entity_types_uri` is specified in ExportEntityTypesRequest. */
		entityTypesUri: Option[String] = None,
	  /** Uncompressed byte content for entity types. This field is populated only if `entity_types_content_inline` is set to true in ExportEntityTypesRequest. */
		entityTypesContent: Option[Schema.GoogleCloudDialogflowCxV3InlineDestination] = None
	)
	
	case class GoogleCloudDialogflowCxV3ImportIntentsMetadata(
	
	)
	
	case class GoogleCloudDialogflowCxV3ImportIntentsResponse(
	  /** The unique identifier of the imported intents. Format: `projects//locations//agents//intents/`. */
		intents: Option[List[String]] = None,
	  /** Info which resources have conflicts when REPORT_CONFLICT merge_option is set in ImportIntentsRequest. */
		conflictingResources: Option[Schema.GoogleCloudDialogflowCxV3ImportIntentsResponseConflictingResources] = None
	)
	
	case class GoogleCloudDialogflowCxV3ImportIntentsResponseConflictingResources(
	  /** Display names of conflicting intents. */
		intentDisplayNames: Option[List[String]] = None,
	  /** Display names of conflicting entities. */
		entityDisplayNames: Option[List[String]] = None
	)
	
	case class GoogleCloudDialogflowCxV3WebhookRequest(
	  /** Always present. The unique identifier of the DetectIntentResponse that will be returned to the API caller. */
		detectIntentResponseId: Option[String] = None,
	  /** If natural language text was provided as input, this field will contain a copy of the text. */
		text: Option[String] = None,
	  /** If an intent was provided as input, this field will contain a copy of the intent identifier. Format: `projects//locations//agents//intents/`. */
		triggerIntent: Option[String] = None,
	  /** If natural language speech audio was provided as input, this field will contain the transcript for the audio. */
		transcript: Option[String] = None,
	  /** If an event was provided as input, this field will contain the name of the event. */
		triggerEvent: Option[String] = None,
	  /** If DTMF was provided as input, this field will contain the DTMF digits. */
		dtmfDigits: Option[String] = None,
	  /** The language code specified in the original request. */
		languageCode: Option[String] = None,
	  /** Always present. Information about the fulfillment that triggered this webhook call. */
		fulfillmentInfo: Option[Schema.GoogleCloudDialogflowCxV3WebhookRequestFulfillmentInfo] = None,
	  /** Information about the last matched intent. */
		intentInfo: Option[Schema.GoogleCloudDialogflowCxV3WebhookRequestIntentInfo] = None,
	  /** Information about page status. */
		pageInfo: Option[Schema.GoogleCloudDialogflowCxV3PageInfo] = None,
	  /** Information about session status. */
		sessionInfo: Option[Schema.GoogleCloudDialogflowCxV3SessionInfo] = None,
	  /** The list of rich message responses to present to the user. Webhook can choose to append or replace this list in WebhookResponse.fulfillment_response; */
		messages: Option[List[Schema.GoogleCloudDialogflowCxV3ResponseMessage]] = None,
	  /** Custom data set in QueryParameters.payload. */
		payload: Option[Map[String, JsValue]] = None,
	  /** The sentiment analysis result of the current user request. The field is filled when sentiment analysis is configured to be enabled for the request. */
		sentimentAnalysisResult: Option[Schema.GoogleCloudDialogflowCxV3WebhookRequestSentimentAnalysisResult] = None,
	  /** Information about the language of the request. */
		languageInfo: Option[Schema.GoogleCloudDialogflowCxV3LanguageInfo] = None
	)
	
	case class GoogleCloudDialogflowCxV3WebhookRequestFulfillmentInfo(
	  /** Always present. The value of the Fulfillment.tag field will be populated in this field by Dialogflow when the associated webhook is called. The tag is typically used by the webhook service to identify which fulfillment is being called, but it could be used for other purposes. */
		tag: Option[String] = None
	)
	
	case class GoogleCloudDialogflowCxV3WebhookRequestIntentInfo(
	  /** Always present. The unique identifier of the last matched intent. Format: `projects//locations//agents//intents/`. */
		lastMatchedIntent: Option[String] = None,
	  /** Always present. The display name of the last matched intent. */
		displayName: Option[String] = None,
	  /** Parameters identified as a result of intent matching. This is a map of the name of the identified parameter to the value of the parameter identified from the user's utterance. All parameters defined in the matched intent that are identified will be surfaced here. */
		parameters: Option[Map[String, Schema.GoogleCloudDialogflowCxV3WebhookRequestIntentInfoIntentParameterValue]] = None,
	  /** The confidence of the matched intent. Values range from 0.0 (completely uncertain) to 1.0 (completely certain). */
		confidence: Option[BigDecimal] = None
	)
	
	case class GoogleCloudDialogflowCxV3WebhookRequestIntentInfoIntentParameterValue(
	  /** Always present. Original text value extracted from user utterance. */
		originalValue: Option[String] = None,
	  /** Always present. Structured value for the parameter extracted from user utterance. */
		resolvedValue: Option[JsValue] = None
	)
	
	case class GoogleCloudDialogflowCxV3PageInfo(
	  /** Always present for WebhookRequest. Ignored for WebhookResponse. The unique identifier of the current page. Format: `projects//locations//agents//flows//pages/`. */
		currentPage: Option[String] = None,
	  /** Always present for WebhookRequest. Ignored for WebhookResponse. The display name of the current page. */
		displayName: Option[String] = None,
	  /** Optional for both WebhookRequest and WebhookResponse. Information about the form. */
		formInfo: Option[Schema.GoogleCloudDialogflowCxV3PageInfoFormInfo] = None
	)
	
	case class GoogleCloudDialogflowCxV3PageInfoFormInfo(
	  /** Optional for both WebhookRequest and WebhookResponse. The parameters contained in the form. Note that the webhook cannot add or remove any form parameter. */
		parameterInfo: Option[List[Schema.GoogleCloudDialogflowCxV3PageInfoFormInfoParameterInfo]] = None
	)
	
	object GoogleCloudDialogflowCxV3PageInfoFormInfoParameterInfo {
		enum StateEnum extends Enum[StateEnum] { case PARAMETER_STATE_UNSPECIFIED, EMPTY, INVALID, FILLED }
	}
	case class GoogleCloudDialogflowCxV3PageInfoFormInfoParameterInfo(
	  /** Always present for WebhookRequest. Required for WebhookResponse. The human-readable name of the parameter, unique within the form. This field cannot be modified by the webhook. */
		displayName: Option[String] = None,
	  /** Optional for both WebhookRequest and WebhookResponse. Indicates whether the parameter is required. Optional parameters will not trigger prompts; however, they are filled if the user specifies them. Required parameters must be filled before form filling concludes. */
		required: Option[Boolean] = None,
	  /** Always present for WebhookRequest. Required for WebhookResponse. The state of the parameter. This field can be set to INVALID by the webhook to invalidate the parameter; other values set by the webhook will be ignored. */
		state: Option[Schema.GoogleCloudDialogflowCxV3PageInfoFormInfoParameterInfo.StateEnum] = None,
	  /** Optional for both WebhookRequest and WebhookResponse. The value of the parameter. This field can be set by the webhook to change the parameter value. */
		value: Option[JsValue] = None,
	  /** Optional for WebhookRequest. Ignored for WebhookResponse. Indicates if the parameter value was just collected on the last conversation turn. */
		justCollected: Option[Boolean] = None
	)
	
	case class GoogleCloudDialogflowCxV3SessionInfo(
	  /** Always present for WebhookRequest. Ignored for WebhookResponse. The unique identifier of the session. This field can be used by the webhook to identify a session. Format: `projects//locations//agents//sessions/` or `projects//locations//agents//environments//sessions/` if environment is specified. */
		session: Option[String] = None,
	  /** Optional for WebhookRequest. Optional for WebhookResponse. All parameters collected from forms and intents during the session. Parameters can be created, updated, or removed by the webhook. To remove a parameter from the session, the webhook should explicitly set the parameter value to null in WebhookResponse. The map is keyed by parameters' display names. */
		parameters: Option[Map[String, JsValue]] = None
	)
	
	case class GoogleCloudDialogflowCxV3WebhookRequestSentimentAnalysisResult(
	  /** Sentiment score between -1.0 (negative sentiment) and 1.0 (positive sentiment). */
		score: Option[BigDecimal] = None,
	  /** A non-negative number in the [0, +inf) range, which represents the absolute magnitude of sentiment, regardless of score (positive or negative). */
		magnitude: Option[BigDecimal] = None
	)
	
	case class GoogleCloudDialogflowCxV3LanguageInfo(
	  /** The language code specified in the original request. */
		inputLanguageCode: Option[String] = None,
	  /** The language code detected for this request based on the user conversation. */
		resolvedLanguageCode: Option[String] = None,
	  /** The confidence score of the detected language between 0 and 1. */
		confidenceScore: Option[BigDecimal] = None
	)
	
	case class GoogleCloudDialogflowCxV3WebhookResponse(
	  /** The fulfillment response to send to the user. This field can be omitted by the webhook if it does not intend to send any response to the user. */
		fulfillmentResponse: Option[Schema.GoogleCloudDialogflowCxV3WebhookResponseFulfillmentResponse] = None,
	  /** Information about page status. This field can be omitted by the webhook if it does not intend to modify page status. */
		pageInfo: Option[Schema.GoogleCloudDialogflowCxV3PageInfo] = None,
	  /** Information about session status. This field can be omitted by the webhook if it does not intend to modify session status. */
		sessionInfo: Option[Schema.GoogleCloudDialogflowCxV3SessionInfo] = None,
	  /** Value to append directly to QueryResult.webhook_payloads. */
		payload: Option[Map[String, JsValue]] = None,
	  /** The target page to transition to. Format: `projects//locations//agents//flows//pages/`. */
		targetPage: Option[String] = None,
	  /** The target flow to transition to. Format: `projects//locations//agents//flows/`. */
		targetFlow: Option[String] = None
	)
	
	object GoogleCloudDialogflowCxV3WebhookResponseFulfillmentResponse {
		enum MergeBehaviorEnum extends Enum[MergeBehaviorEnum] { case MERGE_BEHAVIOR_UNSPECIFIED, APPEND, REPLACE }
	}
	case class GoogleCloudDialogflowCxV3WebhookResponseFulfillmentResponse(
	  /** The list of rich message responses to present to the user. */
		messages: Option[List[Schema.GoogleCloudDialogflowCxV3ResponseMessage]] = None,
	  /** Merge behavior for `messages`. */
		mergeBehavior: Option[Schema.GoogleCloudDialogflowCxV3WebhookResponseFulfillmentResponse.MergeBehaviorEnum] = None
	)
	
	case class GoogleCloudDialogflowCxV3BatchRunTestCasesMetadata(
	  /** The test errors. */
		errors: Option[List[Schema.GoogleCloudDialogflowCxV3TestError]] = None
	)
	
	case class GoogleCloudDialogflowCxV3TestError(
	  /** The test case resource name. */
		testCase: Option[String] = None,
	  /** The status associated with the test. */
		status: Option[Schema.GoogleRpcStatus] = None,
	  /** The timestamp when the test was completed. */
		testTime: Option[String] = None
	)
	
	case class GoogleCloudDialogflowCxV3BatchRunTestCasesResponse(
	  /** The test case results. The detailed conversation turns are empty in this response. */
		results: Option[List[Schema.GoogleCloudDialogflowCxV3TestCaseResult]] = None
	)
	
	case class GoogleCloudDialogflowCxV3RunTestCaseMetadata(
	
	)
	
	case class GoogleCloudDialogflowCxV3RunTestCaseResponse(
	  /** The result. */
		result: Option[Schema.GoogleCloudDialogflowCxV3TestCaseResult] = None
	)
	
	case class GoogleCloudDialogflowCxV3ExportTestCasesMetadata(
	
	)
	
	case class GoogleCloudDialogflowCxV3ExportTestCasesResponse(
	  /** The URI to a file containing the exported test cases. This field is populated only if `gcs_uri` is specified in ExportTestCasesRequest. */
		gcsUri: Option[String] = None,
	  /** Uncompressed raw byte content for test cases. */
		content: Option[String] = None
	)
	
	case class GoogleCloudDialogflowCxV3ImportTestCasesMetadata(
	  /** Errors for failed test cases. */
		errors: Option[List[Schema.GoogleCloudDialogflowCxV3TestCaseError]] = None
	)
	
	case class GoogleCloudDialogflowCxV3TestCaseError(
	  /** The test case. */
		testCase: Option[Schema.GoogleCloudDialogflowCxV3TestCase] = None,
	  /** The status associated with the test case. */
		status: Option[Schema.GoogleRpcStatus] = None
	)
	
	case class GoogleCloudDialogflowCxV3ImportTestCasesResponse(
	  /** The unique identifiers of the new test cases. Format: `projects//locations//agents//testCases/`. */
		names: Option[List[String]] = None
	)
	
	case class GoogleCloudDialogflowCxV3RunContinuousTestMetadata(
	  /** The test errors. */
		errors: Option[List[Schema.GoogleCloudDialogflowCxV3TestError]] = None
	)
	
	case class GoogleCloudDialogflowCxV3RunContinuousTestResponse(
	  /** The result for a continuous test run. */
		continuousTestResult: Option[Schema.GoogleCloudDialogflowCxV3ContinuousTestResult] = None
	)
	
	case class GoogleCloudDialogflowCxV3DeployFlowMetadata(
	  /** Errors of running deployment tests. */
		testErrors: Option[List[Schema.GoogleCloudDialogflowCxV3TestError]] = None
	)
	
	case class GoogleCloudDialogflowCxV3DeployFlowResponse(
	  /** The updated environment where the flow is deployed. */
		environment: Option[Schema.GoogleCloudDialogflowCxV3Environment] = None,
	  /** The name of the flow version Deployment. Format: `projects//locations//agents//environments//deployments/`. */
		deployment: Option[String] = None
	)
	
	case class GoogleCloudDialogflowCxV3ConversationSignals(
	  /** Required. Turn signals for the current turn. */
		turnSignals: Option[Schema.GoogleCloudDialogflowCxV3TurnSignals] = None
	)
	
	object GoogleCloudDialogflowCxV3TurnSignals {
		enum FailureReasonsEnum extends Enum[FailureReasonsEnum] { case FAILURE_REASON_UNSPECIFIED, FAILED_INTENT, FAILED_WEBHOOK }
	}
	case class GoogleCloudDialogflowCxV3TurnSignals(
	  /** Whether NLU predicted NO_MATCH. */
		noMatch: Option[Boolean] = None,
	  /** Whether user provided no input. */
		noUserInput: Option[Boolean] = None,
	  /** Whether user was using DTMF input. */
		dtmfUsed: Option[Boolean] = None,
	  /** Whether user was specifically asking for a live agent. */
		userEscalated: Option[Boolean] = None,
	  /** Whether agent responded with LiveAgentHandoff fulfillment. */
		agentEscalated: Option[Boolean] = None,
	  /** Whether turn resulted in End Session page. */
		reachedEndPage: Option[Boolean] = None,
	  /** Human-readable statuses of the webhooks triggered during this turn. */
		webhookStatuses: Option[List[String]] = None,
	  /** Failure reasons of the turn. */
		failureReasons: Option[List[Schema.GoogleCloudDialogflowCxV3TurnSignals.FailureReasonsEnum]] = None,
	  /** Sentiment score of the user utterance if [sentiment](https://cloud.google.com/dialogflow/cx/docs/concept/sentiment) was enabled. */
		sentimentScore: Option[BigDecimal] = None,
	  /** Sentiment magnitude of the user utterance if [sentiment](https://cloud.google.com/dialogflow/cx/docs/concept/sentiment) was enabled. */
		sentimentMagnitude: Option[BigDecimal] = None
	)
	
	case class GoogleCloudDialogflowCxV3beta1CreateVersionOperationMetadata(
	  /** Name of the created version. Format: `projects//locations//agents//flows//versions/`. */
		version: Option[String] = None
	)
	
	case class GoogleCloudDialogflowCxV3beta1ExportAgentResponse(
	  /** The URI to a file containing the exported agent. This field is populated if `agent_uri` is specified in ExportAgentRequest. */
		agentUri: Option[String] = None,
	  /** Uncompressed raw byte content for agent. This field is populated if none of `agent_uri` and `git_destination` are specified in ExportAgentRequest. */
		agentContent: Option[String] = None,
	  /** Commit SHA of the git push. This field is populated if `git_destination` is specified in ExportAgentRequest. */
		commitSha: Option[String] = None
	)
	
	case class GoogleCloudDialogflowCxV3beta1ExportFlowResponse(
	  /** The URI to a file containing the exported flow. This field is populated only if `flow_uri` is specified in ExportFlowRequest. */
		flowUri: Option[String] = None,
	  /** Uncompressed raw byte content for flow. */
		flowContent: Option[String] = None
	)
	
	case class GoogleCloudDialogflowCxV3beta1ExportIntentsMetadata(
	
	)
	
	case class GoogleCloudDialogflowCxV3beta1ExportIntentsResponse(
	  /** The URI to a file containing the exported intents. This field is populated only if `intents_uri` is specified in ExportIntentsRequest. */
		intentsUri: Option[String] = None,
	  /** Uncompressed byte content for intents. This field is populated only if `intents_content_inline` is set to true in ExportIntentsRequest. */
		intentsContent: Option[Schema.GoogleCloudDialogflowCxV3beta1InlineDestination] = None
	)
	
	case class GoogleCloudDialogflowCxV3beta1InlineDestination(
	  /** Output only. The uncompressed byte content for the objects. Only populated in responses. */
		content: Option[String] = None
	)
	
	case class GoogleCloudDialogflowCxV3beta1ImportFlowResponse(
	  /** The unique identifier of the new flow. Format: `projects//locations//agents//flows/`. */
		flow: Option[String] = None
	)
	
	case class GoogleCloudDialogflowCxV3beta1ImportEntityTypesMetadata(
	
	)
	
	case class GoogleCloudDialogflowCxV3beta1ImportEntityTypesResponse(
	  /** The unique identifier of the imported entity types. Format: `projects//locations//agents//entity_types/`. */
		entityTypes: Option[List[String]] = None,
	  /** Info which resources have conflicts when REPORT_CONFLICT merge_option is set in ImportEntityTypesRequest. */
		conflictingResources: Option[Schema.GoogleCloudDialogflowCxV3beta1ImportEntityTypesResponseConflictingResources] = None
	)
	
	case class GoogleCloudDialogflowCxV3beta1ImportEntityTypesResponseConflictingResources(
	  /** Display names of conflicting entity types. */
		entityTypeDisplayNames: Option[List[String]] = None,
	  /** Display names of conflicting entities. */
		entityDisplayNames: Option[List[String]] = None
	)
	
	case class GoogleCloudDialogflowCxV3beta1ExportEntityTypesMetadata(
	
	)
	
	case class GoogleCloudDialogflowCxV3beta1ExportEntityTypesResponse(
	  /** The URI to a file containing the exported entity types. This field is populated only if `entity_types_uri` is specified in ExportEntityTypesRequest. */
		entityTypesUri: Option[String] = None,
	  /** Uncompressed byte content for entity types. This field is populated only if `entity_types_content_inline` is set to true in ExportEntityTypesRequest. */
		entityTypesContent: Option[Schema.GoogleCloudDialogflowCxV3beta1InlineDestination] = None
	)
	
	case class GoogleCloudDialogflowCxV3beta1ImportIntentsMetadata(
	
	)
	
	case class GoogleCloudDialogflowCxV3beta1ImportIntentsResponse(
	  /** The unique identifier of the imported intents. Format: `projects//locations//agents//intents/`. */
		intents: Option[List[String]] = None,
	  /** Info which resources have conflicts when REPORT_CONFLICT merge_option is set in ImportIntentsRequest. */
		conflictingResources: Option[Schema.GoogleCloudDialogflowCxV3beta1ImportIntentsResponseConflictingResources] = None
	)
	
	case class GoogleCloudDialogflowCxV3beta1ImportIntentsResponseConflictingResources(
	  /** Display names of conflicting intents. */
		intentDisplayNames: Option[List[String]] = None,
	  /** Display names of conflicting entities. */
		entityDisplayNames: Option[List[String]] = None
	)
	
	case class GoogleCloudDialogflowCxV3beta1WebhookRequest(
	  /** Always present. The unique identifier of the DetectIntentResponse that will be returned to the API caller. */
		detectIntentResponseId: Option[String] = None,
	  /** If natural language text was provided as input, this field will contain a copy of the text. */
		text: Option[String] = None,
	  /** If an intent was provided as input, this field will contain a copy of the intent identifier. Format: `projects//locations//agents//intents/`. */
		triggerIntent: Option[String] = None,
	  /** If natural language speech audio was provided as input, this field will contain the transcript for the audio. */
		transcript: Option[String] = None,
	  /** If an event was provided as input, this field will contain the name of the event. */
		triggerEvent: Option[String] = None,
	  /** If DTMF was provided as input, this field will contain the DTMF digits. */
		dtmfDigits: Option[String] = None,
	  /** The language code specified in the original request. */
		languageCode: Option[String] = None,
	  /** Always present. Information about the fulfillment that triggered this webhook call. */
		fulfillmentInfo: Option[Schema.GoogleCloudDialogflowCxV3beta1WebhookRequestFulfillmentInfo] = None,
	  /** Information about the last matched intent. */
		intentInfo: Option[Schema.GoogleCloudDialogflowCxV3beta1WebhookRequestIntentInfo] = None,
	  /** Information about page status. */
		pageInfo: Option[Schema.GoogleCloudDialogflowCxV3beta1PageInfo] = None,
	  /** Information about session status. */
		sessionInfo: Option[Schema.GoogleCloudDialogflowCxV3beta1SessionInfo] = None,
	  /** The list of rich message responses to present to the user. Webhook can choose to append or replace this list in WebhookResponse.fulfillment_response; */
		messages: Option[List[Schema.GoogleCloudDialogflowCxV3beta1ResponseMessage]] = None,
	  /** Custom data set in QueryParameters.payload. */
		payload: Option[Map[String, JsValue]] = None,
	  /** The sentiment analysis result of the current user request. The field is filled when sentiment analysis is configured to be enabled for the request. */
		sentimentAnalysisResult: Option[Schema.GoogleCloudDialogflowCxV3beta1WebhookRequestSentimentAnalysisResult] = None,
	  /** Information about the language of the request. */
		languageInfo: Option[Schema.GoogleCloudDialogflowCxV3beta1LanguageInfo] = None
	)
	
	case class GoogleCloudDialogflowCxV3beta1WebhookRequestFulfillmentInfo(
	  /** Always present. The value of the Fulfillment.tag field will be populated in this field by Dialogflow when the associated webhook is called. The tag is typically used by the webhook service to identify which fulfillment is being called, but it could be used for other purposes. */
		tag: Option[String] = None
	)
	
	case class GoogleCloudDialogflowCxV3beta1WebhookRequestIntentInfo(
	  /** Always present. The unique identifier of the last matched intent. Format: `projects//locations//agents//intents/`. */
		lastMatchedIntent: Option[String] = None,
	  /** Always present. The display name of the last matched intent. */
		displayName: Option[String] = None,
	  /** Parameters identified as a result of intent matching. This is a map of the name of the identified parameter to the value of the parameter identified from the user's utterance. All parameters defined in the matched intent that are identified will be surfaced here. */
		parameters: Option[Map[String, Schema.GoogleCloudDialogflowCxV3beta1WebhookRequestIntentInfoIntentParameterValue]] = None,
	  /** The confidence of the matched intent. Values range from 0.0 (completely uncertain) to 1.0 (completely certain). */
		confidence: Option[BigDecimal] = None
	)
	
	case class GoogleCloudDialogflowCxV3beta1WebhookRequestIntentInfoIntentParameterValue(
	  /** Always present. Original text value extracted from user utterance. */
		originalValue: Option[String] = None,
	  /** Always present. Structured value for the parameter extracted from user utterance. */
		resolvedValue: Option[JsValue] = None
	)
	
	case class GoogleCloudDialogflowCxV3beta1PageInfo(
	  /** Always present for WebhookRequest. Ignored for WebhookResponse. The unique identifier of the current page. Format: `projects//locations//agents//flows//pages/`. */
		currentPage: Option[String] = None,
	  /** Always present for WebhookRequest. Ignored for WebhookResponse. The display name of the current page. */
		displayName: Option[String] = None,
	  /** Optional for both WebhookRequest and WebhookResponse. Information about the form. */
		formInfo: Option[Schema.GoogleCloudDialogflowCxV3beta1PageInfoFormInfo] = None
	)
	
	case class GoogleCloudDialogflowCxV3beta1PageInfoFormInfo(
	  /** Optional for both WebhookRequest and WebhookResponse. The parameters contained in the form. Note that the webhook cannot add or remove any form parameter. */
		parameterInfo: Option[List[Schema.GoogleCloudDialogflowCxV3beta1PageInfoFormInfoParameterInfo]] = None
	)
	
	object GoogleCloudDialogflowCxV3beta1PageInfoFormInfoParameterInfo {
		enum StateEnum extends Enum[StateEnum] { case PARAMETER_STATE_UNSPECIFIED, EMPTY, INVALID, FILLED }
	}
	case class GoogleCloudDialogflowCxV3beta1PageInfoFormInfoParameterInfo(
	  /** Always present for WebhookRequest. Required for WebhookResponse. The human-readable name of the parameter, unique within the form. This field cannot be modified by the webhook. */
		displayName: Option[String] = None,
	  /** Optional for both WebhookRequest and WebhookResponse. Indicates whether the parameter is required. Optional parameters will not trigger prompts; however, they are filled if the user specifies them. Required parameters must be filled before form filling concludes. */
		required: Option[Boolean] = None,
	  /** Always present for WebhookRequest. Required for WebhookResponse. The state of the parameter. This field can be set to INVALID by the webhook to invalidate the parameter; other values set by the webhook will be ignored. */
		state: Option[Schema.GoogleCloudDialogflowCxV3beta1PageInfoFormInfoParameterInfo.StateEnum] = None,
	  /** Optional for both WebhookRequest and WebhookResponse. The value of the parameter. This field can be set by the webhook to change the parameter value. */
		value: Option[JsValue] = None,
	  /** Optional for WebhookRequest. Ignored for WebhookResponse. Indicates if the parameter value was just collected on the last conversation turn. */
		justCollected: Option[Boolean] = None
	)
	
	case class GoogleCloudDialogflowCxV3beta1SessionInfo(
	  /** Always present for WebhookRequest. Ignored for WebhookResponse. The unique identifier of the session. This field can be used by the webhook to identify a session. Format: `projects//locations//agents//sessions/` or `projects//locations//agents//environments//sessions/` if environment is specified. */
		session: Option[String] = None,
	  /** Optional for WebhookRequest. Optional for WebhookResponse. All parameters collected from forms and intents during the session. Parameters can be created, updated, or removed by the webhook. To remove a parameter from the session, the webhook should explicitly set the parameter value to null in WebhookResponse. The map is keyed by parameters' display names. */
		parameters: Option[Map[String, JsValue]] = None
	)
	
	case class GoogleCloudDialogflowCxV3beta1ResponseMessage(
	  /** Returns a text response. */
		text: Option[Schema.GoogleCloudDialogflowCxV3beta1ResponseMessageText] = None,
	  /** Returns a response containing a custom, platform-specific payload. */
		payload: Option[Map[String, JsValue]] = None,
	  /** Indicates that the conversation succeeded. */
		conversationSuccess: Option[Schema.GoogleCloudDialogflowCxV3beta1ResponseMessageConversationSuccess] = None,
	  /** A text or ssml response that is preferentially used for TTS output audio synthesis, as described in the comment on the ResponseMessage message. */
		outputAudioText: Option[Schema.GoogleCloudDialogflowCxV3beta1ResponseMessageOutputAudioText] = None,
	  /** Hands off conversation to a human agent. */
		liveAgentHandoff: Option[Schema.GoogleCloudDialogflowCxV3beta1ResponseMessageLiveAgentHandoff] = None,
	  /** Output only. A signal that indicates the interaction with the Dialogflow agent has ended. This message is generated by Dialogflow only when the conversation reaches `END_SESSION` page. It is not supposed to be defined by the user. It's guaranteed that there is at most one such message in each response. */
		endInteraction: Option[Schema.GoogleCloudDialogflowCxV3beta1ResponseMessageEndInteraction] = None,
	  /** Signal that the client should play an audio clip hosted at a client-specific URI. Dialogflow uses this to construct mixed_audio. However, Dialogflow itself does not try to read or process the URI in any way. */
		playAudio: Option[Schema.GoogleCloudDialogflowCxV3beta1ResponseMessagePlayAudio] = None,
	  /** Output only. An audio response message composed of both the synthesized Dialogflow agent responses and responses defined via play_audio. This message is generated by Dialogflow only and not supposed to be defined by the user. */
		mixedAudio: Option[Schema.GoogleCloudDialogflowCxV3beta1ResponseMessageMixedAudio] = None,
	  /** A signal that the client should transfer the phone call connected to this agent to a third-party endpoint. */
		telephonyTransferCall: Option[Schema.GoogleCloudDialogflowCxV3beta1ResponseMessageTelephonyTransferCall] = None,
	  /** Represents info card for knowledge answers, to be better rendered in Dialogflow Messenger. */
		knowledgeInfoCard: Option[Schema.GoogleCloudDialogflowCxV3beta1ResponseMessageKnowledgeInfoCard] = None,
	  /** Returns the definition of a tool call that should be executed by the client. */
		toolCall: Option[Schema.GoogleCloudDialogflowCxV3beta1ToolCall] = None,
	  /** The channel which the response is associated with. Clients can specify the channel via QueryParameters.channel, and only associated channel response will be returned. */
		channel: Option[String] = None
	)
	
	case class GoogleCloudDialogflowCxV3beta1ResponseMessageText(
	  /** Required. A collection of text response variants. If multiple variants are defined, only one text response variant is returned at runtime. */
		text: Option[List[String]] = None,
	  /** Output only. Whether the playback of this message can be interrupted by the end user's speech and the client can then starts the next Dialogflow request. */
		allowPlaybackInterruption: Option[Boolean] = None
	)
	
	case class GoogleCloudDialogflowCxV3beta1ResponseMessageConversationSuccess(
	  /** Custom metadata. Dialogflow doesn't impose any structure on this. */
		metadata: Option[Map[String, JsValue]] = None
	)
	
	case class GoogleCloudDialogflowCxV3beta1ResponseMessageOutputAudioText(
	  /** The raw text to be synthesized. */
		text: Option[String] = None,
	  /** The SSML text to be synthesized. For more information, see [SSML](/speech/text-to-speech/docs/ssml). */
		ssml: Option[String] = None,
	  /** Output only. Whether the playback of this message can be interrupted by the end user's speech and the client can then starts the next Dialogflow request. */
		allowPlaybackInterruption: Option[Boolean] = None
	)
	
	case class GoogleCloudDialogflowCxV3beta1ResponseMessageLiveAgentHandoff(
	  /** Custom metadata for your handoff procedure. Dialogflow doesn't impose any structure on this. */
		metadata: Option[Map[String, JsValue]] = None
	)
	
	case class GoogleCloudDialogflowCxV3beta1ResponseMessageEndInteraction(
	
	)
	
	case class GoogleCloudDialogflowCxV3beta1ResponseMessagePlayAudio(
	  /** Required. URI of the audio clip. Dialogflow does not impose any validation on this value. It is specific to the client that reads it. */
		audioUri: Option[String] = None,
	  /** Output only. Whether the playback of this message can be interrupted by the end user's speech and the client can then starts the next Dialogflow request. */
		allowPlaybackInterruption: Option[Boolean] = None
	)
	
	case class GoogleCloudDialogflowCxV3beta1ResponseMessageMixedAudio(
	  /** Segments this audio response is composed of. */
		segments: Option[List[Schema.GoogleCloudDialogflowCxV3beta1ResponseMessageMixedAudioSegment]] = None
	)
	
	case class GoogleCloudDialogflowCxV3beta1ResponseMessageMixedAudioSegment(
	  /** Raw audio synthesized from the Dialogflow agent's response using the output config specified in the request. */
		audio: Option[String] = None,
	  /** Client-specific URI that points to an audio clip accessible to the client. Dialogflow does not impose any validation on it. */
		uri: Option[String] = None,
	  /** Output only. Whether the playback of this segment can be interrupted by the end user's speech and the client should then start the next Dialogflow request. */
		allowPlaybackInterruption: Option[Boolean] = None
	)
	
	case class GoogleCloudDialogflowCxV3beta1ResponseMessageTelephonyTransferCall(
	  /** Transfer the call to a phone number in [E.164 format](https://en.wikipedia.org/wiki/E.164). */
		phoneNumber: Option[String] = None
	)
	
	case class GoogleCloudDialogflowCxV3beta1ResponseMessageKnowledgeInfoCard(
	
	)
	
	case class GoogleCloudDialogflowCxV3beta1ToolCall(
	  /** Required. The tool associated with this call. Format: `projects//locations//agents//tools/`. */
		tool: Option[String] = None,
	  /** Required. The name of the tool's action associated with this call. */
		action: Option[String] = None,
	  /** Optional. The action's input parameters. */
		inputParameters: Option[Map[String, JsValue]] = None
	)
	
	case class GoogleCloudDialogflowCxV3beta1WebhookRequestSentimentAnalysisResult(
	  /** Sentiment score between -1.0 (negative sentiment) and 1.0 (positive sentiment). */
		score: Option[BigDecimal] = None,
	  /** A non-negative number in the [0, +inf) range, which represents the absolute magnitude of sentiment, regardless of score (positive or negative). */
		magnitude: Option[BigDecimal] = None
	)
	
	case class GoogleCloudDialogflowCxV3beta1LanguageInfo(
	  /** The language code specified in the original request. */
		inputLanguageCode: Option[String] = None,
	  /** The language code detected for this request based on the user conversation. */
		resolvedLanguageCode: Option[String] = None,
	  /** The confidence score of the detected language between 0 and 1. */
		confidenceScore: Option[BigDecimal] = None
	)
	
	case class GoogleCloudDialogflowCxV3beta1WebhookResponse(
	  /** The fulfillment response to send to the user. This field can be omitted by the webhook if it does not intend to send any response to the user. */
		fulfillmentResponse: Option[Schema.GoogleCloudDialogflowCxV3beta1WebhookResponseFulfillmentResponse] = None,
	  /** Information about page status. This field can be omitted by the webhook if it does not intend to modify page status. */
		pageInfo: Option[Schema.GoogleCloudDialogflowCxV3beta1PageInfo] = None,
	  /** Information about session status. This field can be omitted by the webhook if it does not intend to modify session status. */
		sessionInfo: Option[Schema.GoogleCloudDialogflowCxV3beta1SessionInfo] = None,
	  /** Value to append directly to QueryResult.webhook_payloads. */
		payload: Option[Map[String, JsValue]] = None,
	  /** The target page to transition to. Format: `projects//locations//agents//flows//pages/`. */
		targetPage: Option[String] = None,
	  /** The target flow to transition to. Format: `projects//locations//agents//flows/`. */
		targetFlow: Option[String] = None
	)
	
	object GoogleCloudDialogflowCxV3beta1WebhookResponseFulfillmentResponse {
		enum MergeBehaviorEnum extends Enum[MergeBehaviorEnum] { case MERGE_BEHAVIOR_UNSPECIFIED, APPEND, REPLACE }
	}
	case class GoogleCloudDialogflowCxV3beta1WebhookResponseFulfillmentResponse(
	  /** The list of rich message responses to present to the user. */
		messages: Option[List[Schema.GoogleCloudDialogflowCxV3beta1ResponseMessage]] = None,
	  /** Merge behavior for `messages`. */
		mergeBehavior: Option[Schema.GoogleCloudDialogflowCxV3beta1WebhookResponseFulfillmentResponse.MergeBehaviorEnum] = None
	)
	
	case class GoogleCloudDialogflowCxV3beta1BatchRunTestCasesMetadata(
	  /** The test errors. */
		errors: Option[List[Schema.GoogleCloudDialogflowCxV3beta1TestError]] = None
	)
	
	case class GoogleCloudDialogflowCxV3beta1TestError(
	  /** The test case resource name. */
		testCase: Option[String] = None,
	  /** The status associated with the test. */
		status: Option[Schema.GoogleRpcStatus] = None,
	  /** The timestamp when the test was completed. */
		testTime: Option[String] = None
	)
	
	case class GoogleCloudDialogflowCxV3beta1BatchRunTestCasesResponse(
	  /** The test case results. The detailed conversation turns are empty in this response. */
		results: Option[List[Schema.GoogleCloudDialogflowCxV3beta1TestCaseResult]] = None
	)
	
	object GoogleCloudDialogflowCxV3beta1TestCaseResult {
		enum TestResultEnum extends Enum[TestResultEnum] { case TEST_RESULT_UNSPECIFIED, PASSED, FAILED }
	}
	case class GoogleCloudDialogflowCxV3beta1TestCaseResult(
	  /** The resource name for the test case result. Format: `projects//locations//agents//testCases//results/`. */
		name: Option[String] = None,
	  /** Environment where the test was run. If not set, it indicates the draft environment. */
		environment: Option[String] = None,
	  /** The conversation turns uttered during the test case replay in chronological order. */
		conversationTurns: Option[List[Schema.GoogleCloudDialogflowCxV3beta1ConversationTurn]] = None,
	  /** Whether the test case passed in the agent environment. */
		testResult: Option[Schema.GoogleCloudDialogflowCxV3beta1TestCaseResult.TestResultEnum] = None,
	  /** The time that the test was run. */
		testTime: Option[String] = None
	)
	
	case class GoogleCloudDialogflowCxV3beta1ConversationTurn(
	  /** The user input. */
		userInput: Option[Schema.GoogleCloudDialogflowCxV3beta1ConversationTurnUserInput] = None,
	  /** The virtual agent output. */
		virtualAgentOutput: Option[Schema.GoogleCloudDialogflowCxV3beta1ConversationTurnVirtualAgentOutput] = None
	)
	
	case class GoogleCloudDialogflowCxV3beta1ConversationTurnUserInput(
	  /** Supports text input, event input, dtmf input in the test case. */
		input: Option[Schema.GoogleCloudDialogflowCxV3beta1QueryInput] = None,
	  /** Parameters that need to be injected into the conversation during intent detection. */
		injectedParameters: Option[Map[String, JsValue]] = None,
	  /** If webhooks should be allowed to trigger in response to the user utterance. Often if parameters are injected, webhooks should not be enabled. */
		isWebhookEnabled: Option[Boolean] = None,
	  /** Whether sentiment analysis is enabled. */
		enableSentimentAnalysis: Option[Boolean] = None
	)
	
	case class GoogleCloudDialogflowCxV3beta1QueryInput(
	  /** The natural language text to be processed. */
		text: Option[Schema.GoogleCloudDialogflowCxV3beta1TextInput] = None,
	  /** The intent to be triggered. */
		intent: Option[Schema.GoogleCloudDialogflowCxV3beta1IntentInput] = None,
	  /** The natural language speech audio to be processed. */
		audio: Option[Schema.GoogleCloudDialogflowCxV3beta1AudioInput] = None,
	  /** The event to be triggered. */
		event: Option[Schema.GoogleCloudDialogflowCxV3beta1EventInput] = None,
	  /** The DTMF event to be handled. */
		dtmf: Option[Schema.GoogleCloudDialogflowCxV3beta1DtmfInput] = None,
	  /** The results of a tool executed by the client. */
		toolCallResult: Option[Schema.GoogleCloudDialogflowCxV3beta1ToolCallResult] = None,
	  /** Required. The language of the input. See [Language Support](https://cloud.google.com/dialogflow/cx/docs/reference/language) for a list of the currently supported language codes. Note that queries in the same session do not necessarily need to specify the same language. */
		languageCode: Option[String] = None
	)
	
	case class GoogleCloudDialogflowCxV3beta1TextInput(
	  /** Required. The UTF-8 encoded natural language text to be processed. */
		text: Option[String] = None
	)
	
	case class GoogleCloudDialogflowCxV3beta1IntentInput(
	  /** Required. The unique identifier of the intent. Format: `projects//locations//agents//intents/`. */
		intent: Option[String] = None
	)
	
	case class GoogleCloudDialogflowCxV3beta1AudioInput(
	  /** Required. Instructs the speech recognizer how to process the speech audio. */
		config: Option[Schema.GoogleCloudDialogflowCxV3beta1InputAudioConfig] = None,
	  /** The natural language speech audio to be processed. A single request can contain up to 2 minutes of speech audio data. The transcribed text cannot contain more than 256 bytes. For non-streaming audio detect intent, both `config` and `audio` must be provided. For streaming audio detect intent, `config` must be provided in the first request and `audio` must be provided in all following requests. */
		audio: Option[String] = None
	)
	
	object GoogleCloudDialogflowCxV3beta1InputAudioConfig {
		enum AudioEncodingEnum extends Enum[AudioEncodingEnum] { case AUDIO_ENCODING_UNSPECIFIED, AUDIO_ENCODING_LINEAR_16, AUDIO_ENCODING_FLAC, AUDIO_ENCODING_MULAW, AUDIO_ENCODING_AMR, AUDIO_ENCODING_AMR_WB, AUDIO_ENCODING_OGG_OPUS, AUDIO_ENCODING_SPEEX_WITH_HEADER_BYTE, AUDIO_ENCODING_ALAW }
		enum ModelVariantEnum extends Enum[ModelVariantEnum] { case SPEECH_MODEL_VARIANT_UNSPECIFIED, USE_BEST_AVAILABLE, USE_STANDARD, USE_ENHANCED }
	}
	case class GoogleCloudDialogflowCxV3beta1InputAudioConfig(
	  /** Required. Audio encoding of the audio content to process. */
		audioEncoding: Option[Schema.GoogleCloudDialogflowCxV3beta1InputAudioConfig.AudioEncodingEnum] = None,
	  /** Sample rate (in Hertz) of the audio content sent in the query. Refer to [Cloud Speech API documentation](https://cloud.google.com/speech-to-text/docs/basics) for more details. */
		sampleRateHertz: Option[Int] = None,
	  /** Optional. If `true`, Dialogflow returns SpeechWordInfo in StreamingRecognitionResult with information about the recognized speech words, e.g. start and end time offsets. If false or unspecified, Speech doesn't return any word-level information. */
		enableWordInfo: Option[Boolean] = None,
	  /** Optional. A list of strings containing words and phrases that the speech recognizer should recognize with higher likelihood. See [the Cloud Speech documentation](https://cloud.google.com/speech-to-text/docs/basics#phrase-hints) for more details. */
		phraseHints: Option[List[String]] = None,
	  /** Optional. Which Speech model to select for the given request. For more information, see [Speech models](https://cloud.google.com/dialogflow/cx/docs/concept/speech-models). */
		model: Option[String] = None,
	  /** Optional. Which variant of the Speech model to use. */
		modelVariant: Option[Schema.GoogleCloudDialogflowCxV3beta1InputAudioConfig.ModelVariantEnum] = None,
	  /** Optional. If `false` (default), recognition does not cease until the client closes the stream. If `true`, the recognizer will detect a single spoken utterance in input audio. Recognition ceases when it detects the audio's voice has stopped or paused. In this case, once a detected intent is received, the client should close the stream and start a new request with a new stream as needed. Note: This setting is relevant only for streaming methods. */
		singleUtterance: Option[Boolean] = None,
	  /** Configuration of barge-in behavior during the streaming of input audio. */
		bargeInConfig: Option[Schema.GoogleCloudDialogflowCxV3beta1BargeInConfig] = None,
	  /** If `true`, the request will opt out for STT conformer model migration. This field will be deprecated once force migration takes place in June 2024. Please refer to [Dialogflow CX Speech model migration](https://cloud.google.com/dialogflow/cx/docs/concept/speech-model-migration). */
		optOutConformerModelMigration: Option[Boolean] = None
	)
	
	case class GoogleCloudDialogflowCxV3beta1BargeInConfig(
	  /** Duration that is not eligible for barge-in at the beginning of the input audio. */
		noBargeInDuration: Option[String] = None,
	  /** Total duration for the playback at the beginning of the input audio. */
		totalDuration: Option[String] = None
	)
	
	case class GoogleCloudDialogflowCxV3beta1EventInput(
	  /** Name of the event. */
		event: Option[String] = None
	)
	
	case class GoogleCloudDialogflowCxV3beta1DtmfInput(
	  /** The dtmf digits. */
		digits: Option[String] = None,
	  /** The finish digit (if any). */
		finishDigit: Option[String] = None
	)
	
	case class GoogleCloudDialogflowCxV3beta1ToolCallResult(
	  /** Required. The tool associated with this call. Format: `projects//locations//agents//tools/`. */
		tool: Option[String] = None,
	  /** Required. The name of the tool's action associated with this call. */
		action: Option[String] = None,
	  /** The tool call's error. */
		error: Option[Schema.GoogleCloudDialogflowCxV3beta1ToolCallResultError] = None,
	  /** The tool call's output parameters. */
		outputParameters: Option[Map[String, JsValue]] = None
	)
	
	case class GoogleCloudDialogflowCxV3beta1ToolCallResultError(
	  /** Optional. The error message of the function. */
		message: Option[String] = None
	)
	
	case class GoogleCloudDialogflowCxV3beta1ConversationTurnVirtualAgentOutput(
	  /** The session parameters available to the bot at this point. */
		sessionParameters: Option[Map[String, JsValue]] = None,
	  /** Output only. If this is part of a result conversation turn, the list of differences between the original run and the replay for this output, if any. */
		differences: Option[List[Schema.GoogleCloudDialogflowCxV3beta1TestRunDifference]] = None,
	  /** Required. Input only. The diagnostic info output for the turn. Required to calculate the testing coverage. */
		diagnosticInfo: Option[Map[String, JsValue]] = None,
	  /** The Intent that triggered the response. Only name and displayName will be set. */
		triggeredIntent: Option[Schema.GoogleCloudDialogflowCxV3beta1Intent] = None,
	  /** The Page on which the utterance was spoken. Only name and displayName will be set. */
		currentPage: Option[Schema.GoogleCloudDialogflowCxV3beta1Page] = None,
	  /** The text responses from the agent for the turn. */
		textResponses: Option[List[Schema.GoogleCloudDialogflowCxV3beta1ResponseMessageText]] = None,
	  /** Response error from the agent in the test result. If set, other output is empty. */
		status: Option[Schema.GoogleRpcStatus] = None
	)
	
	object GoogleCloudDialogflowCxV3beta1TestRunDifference {
		enum TypeEnum extends Enum[TypeEnum] { case DIFF_TYPE_UNSPECIFIED, INTENT, PAGE, PARAMETERS, UTTERANCE, FLOW }
	}
	case class GoogleCloudDialogflowCxV3beta1TestRunDifference(
	  /** The type of diff. */
		`type`: Option[Schema.GoogleCloudDialogflowCxV3beta1TestRunDifference.TypeEnum] = None,
	  /** A human readable description of the diff, showing the actual output vs expected output. */
		description: Option[String] = None
	)
	
	case class GoogleCloudDialogflowCxV3beta1Intent(
	  /** The unique identifier of the intent. Required for the Intents.UpdateIntent method. Intents.CreateIntent populates the name automatically. Format: `projects//locations//agents//intents/`. */
		name: Option[String] = None,
	  /** Required. The human-readable name of the intent, unique within the agent. */
		displayName: Option[String] = None,
	  /** The collection of training phrases the agent is trained on to identify the intent. */
		trainingPhrases: Option[List[Schema.GoogleCloudDialogflowCxV3beta1IntentTrainingPhrase]] = None,
	  /** The collection of parameters associated with the intent. */
		parameters: Option[List[Schema.GoogleCloudDialogflowCxV3beta1IntentParameter]] = None,
	  /** The priority of this intent. Higher numbers represent higher priorities. - If the supplied value is unspecified or 0, the service translates the value to 500,000, which corresponds to the `Normal` priority in the console. - If the supplied value is negative, the intent is ignored in runtime detect intent requests. */
		priority: Option[Int] = None,
	  /** Indicates whether this is a fallback intent. Currently only default fallback intent is allowed in the agent, which is added upon agent creation. Adding training phrases to fallback intent is useful in the case of requests that are mistakenly matched, since training phrases assigned to fallback intents act as negative examples that triggers no-match event. */
		isFallback: Option[Boolean] = None,
	  /** The key/value metadata to label an intent. Labels can contain lowercase letters, digits and the symbols '-' and '_'. International characters are allowed, including letters from unicase alphabets. Keys must start with a letter. Keys and values can be no longer than 63 characters and no more than 128 bytes. Prefix "sys-" is reserved for Dialogflow defined labels. Currently allowed Dialogflow defined labels include: &#42; sys-head &#42; sys-contextual The above labels do not require value. "sys-head" means the intent is a head intent. "sys-contextual" means the intent is a contextual intent. */
		labels: Option[Map[String, String]] = None,
	  /** Human readable description for better understanding an intent like its scope, content, result etc. Maximum character limit: 140 characters. */
		description: Option[String] = None
	)
	
	case class GoogleCloudDialogflowCxV3beta1IntentTrainingPhrase(
	  /** Output only. The unique identifier of the training phrase. */
		id: Option[String] = None,
	  /** Required. The ordered list of training phrase parts. The parts are concatenated in order to form the training phrase. Note: The API does not automatically annotate training phrases like the Dialogflow Console does. Note: Do not forget to include whitespace at part boundaries, so the training phrase is well formatted when the parts are concatenated. If the training phrase does not need to be annotated with parameters, you just need a single part with only the Part.text field set. If you want to annotate the training phrase, you must create multiple parts, where the fields of each part are populated in one of two ways: - `Part.text` is set to a part of the phrase that has no parameters. - `Part.text` is set to a part of the phrase that you want to annotate, and the `parameter_id` field is set. */
		parts: Option[List[Schema.GoogleCloudDialogflowCxV3beta1IntentTrainingPhrasePart]] = None,
	  /** Indicates how many times this example was added to the intent. */
		repeatCount: Option[Int] = None
	)
	
	case class GoogleCloudDialogflowCxV3beta1IntentTrainingPhrasePart(
	  /** Required. The text for this part. */
		text: Option[String] = None,
	  /** The parameter used to annotate this part of the training phrase. This field is required for annotated parts of the training phrase. */
		parameterId: Option[String] = None
	)
	
	case class GoogleCloudDialogflowCxV3beta1IntentParameter(
	  /** Required. The unique identifier of the parameter. This field is used by training phrases to annotate their parts. */
		id: Option[String] = None,
	  /** Required. The entity type of the parameter. Format: `projects/-/locations/-/agents/-/entityTypes/` for system entity types (for example, `projects/-/locations/-/agents/-/entityTypes/sys.date`), or `projects//locations//agents//entityTypes/` for developer entity types. */
		entityType: Option[String] = None,
	  /** Indicates whether the parameter represents a list of values. */
		isList: Option[Boolean] = None,
	  /** Indicates whether the parameter content should be redacted in log. If redaction is enabled, the parameter content will be replaced by parameter name during logging. Note: the parameter content is subject to redaction if either parameter level redaction or entity type level redaction is enabled. */
		redact: Option[Boolean] = None
	)
	
	case class GoogleCloudDialogflowCxV3beta1Page(
	  /** The unique identifier of the page. Required for the Pages.UpdatePage method. Pages.CreatePage populates the name automatically. Format: `projects//locations//agents//flows//pages/`. */
		name: Option[String] = None,
	  /** Required. The human-readable name of the page, unique within the flow. */
		displayName: Option[String] = None,
	  /** The description of the page. The maximum length is 500 characters. */
		description: Option[String] = None,
	  /** The fulfillment to call when the session is entering the page. */
		entryFulfillment: Option[Schema.GoogleCloudDialogflowCxV3beta1Fulfillment] = None,
	  /** The form associated with the page, used for collecting parameters relevant to the page. */
		form: Option[Schema.GoogleCloudDialogflowCxV3beta1Form] = None,
	  /** Ordered list of `TransitionRouteGroups` added to the page. Transition route groups must be unique within a page. If the page links both flow-level transition route groups and agent-level transition route groups, the flow-level ones will have higher priority and will be put before the agent-level ones. &#42; If multiple transition routes within a page scope refer to the same intent, then the precedence order is: page's transition route -> page's transition route group -> flow's transition routes. &#42; If multiple transition route groups within a page contain the same intent, then the first group in the ordered list takes precedence. Format:`projects//locations//agents//flows//transitionRouteGroups/` or `projects//locations//agents//transitionRouteGroups/` for agent-level groups. */
		transitionRouteGroups: Option[List[String]] = None,
	  /** A list of transitions for the transition rules of this page. They route the conversation to another page in the same flow, or another flow. When we are in a certain page, the TransitionRoutes are evalauted in the following order: &#42; TransitionRoutes defined in the page with intent specified. &#42; TransitionRoutes defined in the transition route groups with intent specified. &#42; TransitionRoutes defined in flow with intent specified. &#42; TransitionRoutes defined in the transition route groups with intent specified. &#42; TransitionRoutes defined in the page with only condition specified. &#42; TransitionRoutes defined in the transition route groups with only condition specified. */
		transitionRoutes: Option[List[Schema.GoogleCloudDialogflowCxV3beta1TransitionRoute]] = None,
	  /** Handlers associated with the page to handle events such as webhook errors, no match or no input. */
		eventHandlers: Option[List[Schema.GoogleCloudDialogflowCxV3beta1EventHandler]] = None,
	  /** Hierarchical advanced settings for this page. The settings exposed at the lower level overrides the settings exposed at the higher level. */
		advancedSettings: Option[Schema.GoogleCloudDialogflowCxV3beta1AdvancedSettings] = None,
	  /** Optional. Knowledge connector configuration. */
		knowledgeConnectorSettings: Option[Schema.GoogleCloudDialogflowCxV3beta1KnowledgeConnectorSettings] = None
	)
	
	case class GoogleCloudDialogflowCxV3beta1Fulfillment(
	  /** The list of rich message responses to present to the user. */
		messages: Option[List[Schema.GoogleCloudDialogflowCxV3beta1ResponseMessage]] = None,
	  /** The webhook to call. Format: `projects//locations//agents//webhooks/`. */
		webhook: Option[String] = None,
	  /** Whether Dialogflow should return currently queued fulfillment response messages in streaming APIs. If a webhook is specified, it happens before Dialogflow invokes webhook. Warning: 1) This flag only affects streaming API. Responses are still queued and returned once in non-streaming API. 2) The flag can be enabled in any fulfillment but only the first 3 partial responses will be returned. You may only want to apply it to fulfillments that have slow webhooks. */
		returnPartialResponses: Option[Boolean] = None,
	  /** The value of this field will be populated in the WebhookRequest `fulfillmentInfo.tag` field by Dialogflow when the associated webhook is called. The tag is typically used by the webhook service to identify which fulfillment is being called, but it could be used for other purposes. This field is required if `webhook` is specified. */
		tag: Option[String] = None,
	  /** Set parameter values before executing the webhook. */
		setParameterActions: Option[List[Schema.GoogleCloudDialogflowCxV3beta1FulfillmentSetParameterAction]] = None,
	  /** Conditional cases for this fulfillment. */
		conditionalCases: Option[List[Schema.GoogleCloudDialogflowCxV3beta1FulfillmentConditionalCases]] = None,
	  /** Hierarchical advanced settings for this fulfillment. The settings exposed at the lower level overrides the settings exposed at the higher level. */
		advancedSettings: Option[Schema.GoogleCloudDialogflowCxV3beta1AdvancedSettings] = None,
	  /** If the flag is true, the agent will utilize LLM to generate a text response. If LLM generation fails, the defined responses in the fulfillment will be respected. This flag is only useful for fulfillments associated with no-match event handlers. */
		enableGenerativeFallback: Option[Boolean] = None
	)
	
	case class GoogleCloudDialogflowCxV3beta1FulfillmentSetParameterAction(
	  /** Display name of the parameter. */
		parameter: Option[String] = None,
	  /** The new value of the parameter. A null value clears the parameter. */
		value: Option[JsValue] = None
	)
	
	case class GoogleCloudDialogflowCxV3beta1FulfillmentConditionalCases(
	  /** A list of cascading if-else conditions. */
		cases: Option[List[Schema.GoogleCloudDialogflowCxV3beta1FulfillmentConditionalCasesCase]] = None
	)
	
	case class GoogleCloudDialogflowCxV3beta1FulfillmentConditionalCasesCase(
	  /** The condition to activate and select this case. Empty means the condition is always true. The condition is evaluated against form parameters or session parameters. See the [conditions reference](https://cloud.google.com/dialogflow/cx/docs/reference/condition). */
		condition: Option[String] = None,
	  /** A list of case content. */
		caseContent: Option[List[Schema.GoogleCloudDialogflowCxV3beta1FulfillmentConditionalCasesCaseCaseContent]] = None
	)
	
	case class GoogleCloudDialogflowCxV3beta1FulfillmentConditionalCasesCaseCaseContent(
	  /** Returned message. */
		message: Option[Schema.GoogleCloudDialogflowCxV3beta1ResponseMessage] = None,
	  /** Additional cases to be evaluated. */
		additionalCases: Option[Schema.GoogleCloudDialogflowCxV3beta1FulfillmentConditionalCases] = None
	)
	
	case class GoogleCloudDialogflowCxV3beta1AdvancedSettings(
	  /** If present, incoming audio is exported by Dialogflow to the configured Google Cloud Storage destination. Exposed at the following levels: - Agent level - Flow level */
		audioExportGcsDestination: Option[Schema.GoogleCloudDialogflowCxV3beta1GcsDestination] = None,
	  /** Settings for speech to text detection. Exposed at the following levels: - Agent level - Flow level - Page level - Parameter level */
		speechSettings: Option[Schema.GoogleCloudDialogflowCxV3beta1AdvancedSettingsSpeechSettings] = None,
	  /** Settings for DTMF. Exposed at the following levels: - Agent level - Flow level - Page level - Parameter level. */
		dtmfSettings: Option[Schema.GoogleCloudDialogflowCxV3beta1AdvancedSettingsDtmfSettings] = None,
	  /** Settings for logging. Settings for Dialogflow History, Contact Center messages, StackDriver logs, and speech logging. Exposed at the following levels: - Agent level. */
		loggingSettings: Option[Schema.GoogleCloudDialogflowCxV3beta1AdvancedSettingsLoggingSettings] = None
	)
	
	case class GoogleCloudDialogflowCxV3beta1GcsDestination(
	  /** Required. The Google Cloud Storage URI for the exported objects. A URI is of the form: `gs://bucket/object-name-or-prefix` Whether a full object name, or just a prefix, its usage depends on the Dialogflow operation. */
		uri: Option[String] = None
	)
	
	case class GoogleCloudDialogflowCxV3beta1AdvancedSettingsSpeechSettings(
	  /** Sensitivity of the speech model that detects the end of speech. Scale from 0 to 100. */
		endpointerSensitivity: Option[Int] = None,
	  /** Timeout before detecting no speech. */
		noSpeechTimeout: Option[String] = None,
	  /** Use timeout based endpointing, interpreting endpointer sensitivy as seconds of timeout value. */
		useTimeoutBasedEndpointing: Option[Boolean] = None,
	  /** Mapping from language to Speech-to-Text model. The mapped Speech-to-Text model will be selected for requests from its corresponding language. For more information, see [Speech models](https://cloud.google.com/dialogflow/cx/docs/concept/speech-models). */
		models: Option[Map[String, String]] = None
	)
	
	case class GoogleCloudDialogflowCxV3beta1AdvancedSettingsDtmfSettings(
	  /** If true, incoming audio is processed for DTMF (dual tone multi frequency) events. For example, if the caller presses a button on their telephone keypad and DTMF processing is enabled, Dialogflow will detect the event (e.g. a "3" was pressed) in the incoming audio and pass the event to the bot to drive business logic (e.g. when 3 is pressed, return the account balance). */
		enabled: Option[Boolean] = None,
	  /** Max length of DTMF digits. */
		maxDigits: Option[Int] = None,
	  /** The digit that terminates a DTMF digit sequence. */
		finishDigit: Option[String] = None,
	  /** Interdigit timeout setting for matching dtmf input to regex. */
		interdigitTimeoutDuration: Option[String] = None,
	  /** Endpoint timeout setting for matching dtmf input to regex. */
		endpointingTimeoutDuration: Option[String] = None
	)
	
	case class GoogleCloudDialogflowCxV3beta1AdvancedSettingsLoggingSettings(
	  /** Enables Google Cloud Logging. */
		enableStackdriverLogging: Option[Boolean] = None,
	  /** Enables DF Interaction logging. */
		enableInteractionLogging: Option[Boolean] = None,
	  /** Enables consent-based end-user input redaction, if true, a pre-defined session parameter `$session.params.conversation-redaction` will be used to determine if the utterance should be redacted. */
		enableConsentBasedRedaction: Option[Boolean] = None
	)
	
	case class GoogleCloudDialogflowCxV3beta1Form(
	  /** Parameters to collect from the user. */
		parameters: Option[List[Schema.GoogleCloudDialogflowCxV3beta1FormParameter]] = None
	)
	
	case class GoogleCloudDialogflowCxV3beta1FormParameter(
	  /** Required. The human-readable name of the parameter, unique within the form. */
		displayName: Option[String] = None,
	  /** Indicates whether the parameter is required. Optional parameters will not trigger prompts; however, they are filled if the user specifies them. Required parameters must be filled before form filling concludes. */
		required: Option[Boolean] = None,
	  /** Required. The entity type of the parameter. Format: `projects/-/locations/-/agents/-/entityTypes/` for system entity types (for example, `projects/-/locations/-/agents/-/entityTypes/sys.date`), or `projects//locations//agents//entityTypes/` for developer entity types. */
		entityType: Option[String] = None,
	  /** Indicates whether the parameter represents a list of values. */
		isList: Option[Boolean] = None,
	  /** Required. Defines fill behavior for the parameter. */
		fillBehavior: Option[Schema.GoogleCloudDialogflowCxV3beta1FormParameterFillBehavior] = None,
	  /** The default value of an optional parameter. If the parameter is required, the default value will be ignored. */
		defaultValue: Option[JsValue] = None,
	  /** Indicates whether the parameter content should be redacted in log. If redaction is enabled, the parameter content will be replaced by parameter name during logging. Note: the parameter content is subject to redaction if either parameter level redaction or entity type level redaction is enabled. */
		redact: Option[Boolean] = None,
	  /** Hierarchical advanced settings for this parameter. The settings exposed at the lower level overrides the settings exposed at the higher level. */
		advancedSettings: Option[Schema.GoogleCloudDialogflowCxV3beta1AdvancedSettings] = None
	)
	
	case class GoogleCloudDialogflowCxV3beta1FormParameterFillBehavior(
	  /** Required. The fulfillment to provide the initial prompt that the agent can present to the user in order to fill the parameter. */
		initialPromptFulfillment: Option[Schema.GoogleCloudDialogflowCxV3beta1Fulfillment] = None,
	  /** The handlers for parameter-level events, used to provide reprompt for the parameter or transition to a different page/flow. The supported events are: &#42; `sys.no-match-`, where N can be from 1 to 6 &#42; `sys.no-match-default` &#42; `sys.no-input-`, where N can be from 1 to 6 &#42; `sys.no-input-default` &#42; `sys.invalid-parameter` `initial_prompt_fulfillment` provides the first prompt for the parameter. If the user's response does not fill the parameter, a no-match/no-input event will be triggered, and the fulfillment associated with the `sys.no-match-1`/`sys.no-input-1` handler (if defined) will be called to provide a prompt. The `sys.no-match-2`/`sys.no-input-2` handler (if defined) will respond to the next no-match/no-input event, and so on. A `sys.no-match-default` or `sys.no-input-default` handler will be used to handle all following no-match/no-input events after all numbered no-match/no-input handlers for the parameter are consumed. A `sys.invalid-parameter` handler can be defined to handle the case where the parameter values have been `invalidated` by webhook. For example, if the user's response fill the parameter, however the parameter was invalidated by webhook, the fulfillment associated with the `sys.invalid-parameter` handler (if defined) will be called to provide a prompt. If the event handler for the corresponding event can't be found on the parameter, `initial_prompt_fulfillment` will be re-prompted. */
		repromptEventHandlers: Option[List[Schema.GoogleCloudDialogflowCxV3beta1EventHandler]] = None
	)
	
	case class GoogleCloudDialogflowCxV3beta1EventHandler(
	  /** Output only. The unique identifier of this event handler. */
		name: Option[String] = None,
	  /** Required. The name of the event to handle. */
		event: Option[String] = None,
	  /** The fulfillment to call when the event occurs. Handling webhook errors with a fulfillment enabled with webhook could cause infinite loop. It is invalid to specify such fulfillment for a handler handling webhooks. */
		triggerFulfillment: Option[Schema.GoogleCloudDialogflowCxV3beta1Fulfillment] = None,
	  /** The target page to transition to. Format: `projects//locations//agents//flows//pages/`. */
		targetPage: Option[String] = None,
	  /** The target flow to transition to. Format: `projects//locations//agents//flows/`. */
		targetFlow: Option[String] = None,
	  /** The target playbook to transition to. Format: `projects//locations//agents//playbooks/`. */
		targetPlaybook: Option[String] = None
	)
	
	case class GoogleCloudDialogflowCxV3beta1TransitionRoute(
	  /** Output only. The unique identifier of this transition route. */
		name: Option[String] = None,
	  /** Optional. The description of the transition route. The maximum length is 500 characters. */
		description: Option[String] = None,
	  /** The unique identifier of an Intent. Format: `projects//locations//agents//intents/`. Indicates that the transition can only happen when the given intent is matched. At least one of `intent` or `condition` must be specified. When both `intent` and `condition` are specified, the transition can only happen when both are fulfilled. */
		intent: Option[String] = None,
	  /** The condition to evaluate against form parameters or session parameters. See the [conditions reference](https://cloud.google.com/dialogflow/cx/docs/reference/condition). At least one of `intent` or `condition` must be specified. When both `intent` and `condition` are specified, the transition can only happen when both are fulfilled. */
		condition: Option[String] = None,
	  /** The fulfillment to call when the condition is satisfied. At least one of `trigger_fulfillment` and `target` must be specified. When both are defined, `trigger_fulfillment` is executed first. */
		triggerFulfillment: Option[Schema.GoogleCloudDialogflowCxV3beta1Fulfillment] = None,
	  /** The target page to transition to. Format: `projects//locations//agents//flows//pages/`. */
		targetPage: Option[String] = None,
	  /** The target flow to transition to. Format: `projects//locations//agents//flows/`. */
		targetFlow: Option[String] = None
	)
	
	case class GoogleCloudDialogflowCxV3beta1KnowledgeConnectorSettings(
	  /** Whether Knowledge Connector is enabled or not. */
		enabled: Option[Boolean] = None,
	  /** The fulfillment to be triggered. When the answers from the Knowledge Connector are selected by Dialogflow, you can utitlize the request scoped parameter `$request.knowledge.answers` (contains up to the 5 highest confidence answers) and `$request.knowledge.questions` (contains the corresponding questions) to construct the fulfillment. */
		triggerFulfillment: Option[Schema.GoogleCloudDialogflowCxV3beta1Fulfillment] = None,
	  /** The target page to transition to. Format: `projects//locations//agents//flows//pages/`. */
		targetPage: Option[String] = None,
	  /** The target flow to transition to. Format: `projects//locations//agents//flows/`. */
		targetFlow: Option[String] = None,
	  /** Optional. List of related data store connections. */
		dataStoreConnections: Option[List[Schema.GoogleCloudDialogflowCxV3beta1DataStoreConnection]] = None
	)
	
	object GoogleCloudDialogflowCxV3beta1DataStoreConnection {
		enum DataStoreTypeEnum extends Enum[DataStoreTypeEnum] { case DATA_STORE_TYPE_UNSPECIFIED, PUBLIC_WEB, UNSTRUCTURED, STRUCTURED }
	}
	case class GoogleCloudDialogflowCxV3beta1DataStoreConnection(
	  /** The type of the connected data store. */
		dataStoreType: Option[Schema.GoogleCloudDialogflowCxV3beta1DataStoreConnection.DataStoreTypeEnum] = None,
	  /** The full name of the referenced data store. Formats: `projects/{project}/locations/{location}/collections/{collection}/dataStores/{data_store}` `projects/{project}/locations/{location}/dataStores/{data_store}` */
		dataStore: Option[String] = None
	)
	
	case class GoogleCloudDialogflowCxV3beta1RunTestCaseMetadata(
	
	)
	
	case class GoogleCloudDialogflowCxV3beta1RunTestCaseResponse(
	  /** The result. */
		result: Option[Schema.GoogleCloudDialogflowCxV3beta1TestCaseResult] = None
	)
	
	case class GoogleCloudDialogflowCxV3beta1ExportTestCasesMetadata(
	
	)
	
	case class GoogleCloudDialogflowCxV3beta1ExportTestCasesResponse(
	  /** The URI to a file containing the exported test cases. This field is populated only if `gcs_uri` is specified in ExportTestCasesRequest. */
		gcsUri: Option[String] = None,
	  /** Uncompressed raw byte content for test cases. */
		content: Option[String] = None
	)
	
	case class GoogleCloudDialogflowCxV3beta1ImportTestCasesMetadata(
	  /** Errors for failed test cases. */
		errors: Option[List[Schema.GoogleCloudDialogflowCxV3beta1TestCaseError]] = None
	)
	
	case class GoogleCloudDialogflowCxV3beta1TestCaseError(
	  /** The test case. */
		testCase: Option[Schema.GoogleCloudDialogflowCxV3beta1TestCase] = None,
	  /** The status associated with the test case. */
		status: Option[Schema.GoogleRpcStatus] = None
	)
	
	case class GoogleCloudDialogflowCxV3beta1TestCase(
	  /** The unique identifier of the test case. TestCases.CreateTestCase will populate the name automatically. Otherwise use format: `projects//locations//agents//testCases/`. */
		name: Option[String] = None,
	  /** Tags are short descriptions that users may apply to test cases for organizational and filtering purposes. Each tag should start with "#" and has a limit of 30 characters. */
		tags: Option[List[String]] = None,
	  /** Required. The human-readable name of the test case, unique within the agent. Limit of 200 characters. */
		displayName: Option[String] = None,
	  /** Additional freeform notes about the test case. Limit of 400 characters. */
		notes: Option[String] = None,
	  /** Config for the test case. */
		testConfig: Option[Schema.GoogleCloudDialogflowCxV3beta1TestConfig] = None,
	  /** The conversation turns uttered when the test case was created, in chronological order. These include the canonical set of agent utterances that should occur when the agent is working properly. */
		testCaseConversationTurns: Option[List[Schema.GoogleCloudDialogflowCxV3beta1ConversationTurn]] = None,
	  /** Output only. When the test was created. */
		creationTime: Option[String] = None,
	  /** The latest test result. */
		lastTestResult: Option[Schema.GoogleCloudDialogflowCxV3beta1TestCaseResult] = None
	)
	
	case class GoogleCloudDialogflowCxV3beta1TestConfig(
	  /** Session parameters to be compared when calculating differences. */
		trackingParameters: Option[List[String]] = None,
	  /** Flow name to start the test case with. Format: `projects//locations//agents//flows/`. Only one of `flow` and `page` should be set to indicate the starting point of the test case. If neither is set, the test case will start with start page on the default start flow. */
		flow: Option[String] = None,
	  /** The page to start the test case with. Format: `projects//locations//agents//flows//pages/`. Only one of `flow` and `page` should be set to indicate the starting point of the test case. If neither is set, the test case will start with start page on the default start flow. */
		page: Option[String] = None
	)
	
	case class GoogleCloudDialogflowCxV3beta1ImportTestCasesResponse(
	  /** The unique identifiers of the new test cases. Format: `projects//locations//agents//testCases/`. */
		names: Option[List[String]] = None
	)
	
	case class GoogleCloudDialogflowCxV3beta1RunContinuousTestMetadata(
	  /** The test errors. */
		errors: Option[List[Schema.GoogleCloudDialogflowCxV3beta1TestError]] = None
	)
	
	case class GoogleCloudDialogflowCxV3beta1RunContinuousTestResponse(
	  /** The result for a continuous test run. */
		continuousTestResult: Option[Schema.GoogleCloudDialogflowCxV3beta1ContinuousTestResult] = None
	)
	
	object GoogleCloudDialogflowCxV3beta1ContinuousTestResult {
		enum ResultEnum extends Enum[ResultEnum] { case AGGREGATED_TEST_RESULT_UNSPECIFIED, PASSED, FAILED }
	}
	case class GoogleCloudDialogflowCxV3beta1ContinuousTestResult(
	  /** The resource name for the continuous test result. Format: `projects//locations//agents//environments//continuousTestResults/`. */
		name: Option[String] = None,
	  /** The result of this continuous test run, i.e. whether all the tests in this continuous test run pass or not. */
		result: Option[Schema.GoogleCloudDialogflowCxV3beta1ContinuousTestResult.ResultEnum] = None,
	  /** A list of individual test case results names in this continuous test run. */
		testCaseResults: Option[List[String]] = None,
	  /** Time when the continuous testing run starts. */
		runTime: Option[String] = None
	)
	
	case class GoogleCloudDialogflowCxV3beta1DeployFlowMetadata(
	  /** Errors of running deployment tests. */
		testErrors: Option[List[Schema.GoogleCloudDialogflowCxV3beta1TestError]] = None
	)
	
	case class GoogleCloudDialogflowCxV3beta1DeployFlowResponse(
	  /** The updated environment where the flow is deployed. */
		environment: Option[Schema.GoogleCloudDialogflowCxV3beta1Environment] = None,
	  /** The name of the flow version deployment. Format: `projects//locations//agents//environments//deployments/`. */
		deployment: Option[String] = None
	)
	
	case class GoogleCloudDialogflowCxV3beta1Environment(
	  /** The name of the environment. Format: `projects//locations//agents//environments/`. */
		name: Option[String] = None,
	  /** Required. The human-readable name of the environment (unique in an agent). Limit of 64 characters. */
		displayName: Option[String] = None,
	  /** The human-readable description of the environment. The maximum length is 500 characters. If exceeded, the request is rejected. */
		description: Option[String] = None,
	  /** A list of configurations for flow versions. You should include version configs for all flows that are reachable from `Start Flow` in the agent. Otherwise, an error will be returned. */
		versionConfigs: Option[List[Schema.GoogleCloudDialogflowCxV3beta1EnvironmentVersionConfig]] = None,
	  /** Output only. Update time of this environment. */
		updateTime: Option[String] = None,
	  /** The test cases config for continuous tests of this environment. */
		testCasesConfig: Option[Schema.GoogleCloudDialogflowCxV3beta1EnvironmentTestCasesConfig] = None,
	  /** The webhook configuration for this environment. */
		webhookConfig: Option[Schema.GoogleCloudDialogflowCxV3beta1EnvironmentWebhookConfig] = None
	)
	
	case class GoogleCloudDialogflowCxV3beta1EnvironmentVersionConfig(
	  /** Required. Both flow and playbook versions are supported. Format for flow version: projects//locations//agents//flows//versions/. Format for playbook version: projects//locations//agents//playbooks//versions/. */
		version: Option[String] = None
	)
	
	case class GoogleCloudDialogflowCxV3beta1EnvironmentTestCasesConfig(
	  /** A list of test case names to run. They should be under the same agent. Format of each test case name: `projects//locations//agents//testCases/` */
		testCases: Option[List[String]] = None,
	  /** Whether to run test cases in TestCasesConfig.test_cases periodically. Default false. If set to true, run once a day. */
		enableContinuousRun: Option[Boolean] = None,
	  /** Whether to run test cases in TestCasesConfig.test_cases before deploying a flow version to the environment. Default false. */
		enablePredeploymentRun: Option[Boolean] = None
	)
	
	case class GoogleCloudDialogflowCxV3beta1EnvironmentWebhookConfig(
	  /** The list of webhooks to override for the agent environment. The webhook must exist in the agent. You can override fields in `generic_web_service` and `service_directory`. */
		webhookOverrides: Option[List[Schema.GoogleCloudDialogflowCxV3beta1Webhook]] = None
	)
	
	case class GoogleCloudDialogflowCxV3beta1Webhook(
	  /** The unique identifier of the webhook. Required for the Webhooks.UpdateWebhook method. Webhooks.CreateWebhook populates the name automatically. Format: `projects//locations//agents//webhooks/`. */
		name: Option[String] = None,
	  /** Required. The human-readable name of the webhook, unique within the agent. */
		displayName: Option[String] = None,
	  /** Configuration for a generic web service. */
		genericWebService: Option[Schema.GoogleCloudDialogflowCxV3beta1WebhookGenericWebService] = None,
	  /** Configuration for a [Service Directory](https://cloud.google.com/service-directory) service. */
		serviceDirectory: Option[Schema.GoogleCloudDialogflowCxV3beta1WebhookServiceDirectoryConfig] = None,
	  /** Webhook execution timeout. Execution is considered failed if Dialogflow doesn't receive a response from webhook at the end of the timeout period. Defaults to 5 seconds, maximum allowed timeout is 30 seconds. */
		timeout: Option[String] = None,
	  /** Indicates whether the webhook is disabled. */
		disabled: Option[Boolean] = None
	)
	
	object GoogleCloudDialogflowCxV3beta1WebhookGenericWebService {
		enum ServiceAgentAuthEnum extends Enum[ServiceAgentAuthEnum] { case SERVICE_AGENT_AUTH_UNSPECIFIED, NONE, ID_TOKEN, ACCESS_TOKEN }
		enum WebhookTypeEnum extends Enum[WebhookTypeEnum] { case WEBHOOK_TYPE_UNSPECIFIED, STANDARD, FLEXIBLE }
		enum HttpMethodEnum extends Enum[HttpMethodEnum] { case HTTP_METHOD_UNSPECIFIED, POST, GET, HEAD, PUT, DELETE, PATCH, OPTIONS }
	}
	case class GoogleCloudDialogflowCxV3beta1WebhookGenericWebService(
	  /** Required. The webhook URI for receiving POST requests. It must use https protocol. */
		uri: Option[String] = None,
	  /** The user name for HTTP Basic authentication. */
		username: Option[String] = None,
	  /** The password for HTTP Basic authentication. */
		password: Option[String] = None,
	  /** The HTTP request headers to send together with webhook requests. */
		requestHeaders: Option[Map[String, String]] = None,
	  /** Optional. Specifies a list of allowed custom CA certificates (in DER format) for HTTPS verification. This overrides the default SSL trust store. If this is empty or unspecified, Dialogflow will use Google's default trust store to verify certificates. N.B. Make sure the HTTPS server certificates are signed with "subject alt name". For instance a certificate can be self-signed using the following command, ``` openssl x509 -req -days 200 -in example.com.csr \ -signkey example.com.key \ -out example.com.crt \ -extfile <(printf "\nsubjectAltName='DNS:www.example.com'") ``` */
		allowedCaCerts: Option[List[String]] = None,
	  /** Optional. The OAuth configuration of the webhook. If specified, Dialogflow will initiate the OAuth client credential flow to exchange an access token from the 3rd party platform and put it in the auth header. */
		oauthConfig: Option[Schema.GoogleCloudDialogflowCxV3beta1WebhookGenericWebServiceOAuthConfig] = None,
	  /** Optional. Indicate the auth token type generated from the [Diglogflow service agent](https://cloud.google.com/iam/docs/service-agents#dialogflow-service-agent). The generated token is sent in the Authorization header. */
		serviceAgentAuth: Option[Schema.GoogleCloudDialogflowCxV3beta1WebhookGenericWebService.ServiceAgentAuthEnum] = None,
	  /** Optional. Type of the webhook. */
		webhookType: Option[Schema.GoogleCloudDialogflowCxV3beta1WebhookGenericWebService.WebhookTypeEnum] = None,
	  /** Optional. HTTP method for the flexible webhook calls. Standard webhook always uses POST. */
		httpMethod: Option[Schema.GoogleCloudDialogflowCxV3beta1WebhookGenericWebService.HttpMethodEnum] = None,
	  /** Optional. Defines a custom JSON object as request body to send to flexible webhook. */
		requestBody: Option[String] = None,
	  /** Optional. Maps the values extracted from specific fields of the flexible webhook response into session parameters. - Key: session parameter name - Value: field path in the webhook response */
		parameterMapping: Option[Map[String, String]] = None
	)
	
	case class GoogleCloudDialogflowCxV3beta1WebhookGenericWebServiceOAuthConfig(
	  /** Required. The client ID provided by the 3rd party platform. */
		clientId: Option[String] = None,
	  /** Required. The client secret provided by the 3rd party platform. */
		clientSecret: Option[String] = None,
	  /** Required. The token endpoint provided by the 3rd party platform to exchange an access token. */
		tokenEndpoint: Option[String] = None,
	  /** Optional. The OAuth scopes to grant. */
		scopes: Option[List[String]] = None
	)
	
	case class GoogleCloudDialogflowCxV3beta1WebhookServiceDirectoryConfig(
	  /** Required. The name of [Service Directory](https://cloud.google.com/service-directory) service. Format: `projects//locations//namespaces//services/`. `Location ID` of the service directory must be the same as the location of the agent. */
		service: Option[String] = None,
	  /** Generic Service configuration of this webhook. */
		genericWebService: Option[Schema.GoogleCloudDialogflowCxV3beta1WebhookGenericWebService] = None
	)
	
	case class GoogleCloudDialogflowCxV3beta1ConversationSignals(
	  /** Required. Turn signals for the current turn. */
		turnSignals: Option[Schema.GoogleCloudDialogflowCxV3beta1TurnSignals] = None
	)
	
	object GoogleCloudDialogflowCxV3beta1TurnSignals {
		enum FailureReasonsEnum extends Enum[FailureReasonsEnum] { case FAILURE_REASON_UNSPECIFIED, FAILED_INTENT, FAILED_WEBHOOK }
	}
	case class GoogleCloudDialogflowCxV3beta1TurnSignals(
	  /** Whether NLU predicted NO_MATCH. */
		noMatch: Option[Boolean] = None,
	  /** Whether user provided no input. */
		noUserInput: Option[Boolean] = None,
	  /** Whether user was using DTMF input. */
		dtmfUsed: Option[Boolean] = None,
	  /** Whether user was specifically asking for a live agent. */
		userEscalated: Option[Boolean] = None,
	  /** Whether agent responded with LiveAgentHandoff fulfillment. */
		agentEscalated: Option[Boolean] = None,
	  /** Whether turn resulted in End Session page. */
		reachedEndPage: Option[Boolean] = None,
	  /** Human-readable statuses of the webhooks triggered during this turn. */
		webhookStatuses: Option[List[String]] = None,
	  /** Failure reasons of the turn. */
		failureReasons: Option[List[Schema.GoogleCloudDialogflowCxV3beta1TurnSignals.FailureReasonsEnum]] = None,
	  /** Sentiment score of the user utterance if [sentiment](https://cloud.google.com/dialogflow/cx/docs/concept/sentiment) was enabled. */
		sentimentScore: Option[BigDecimal] = None,
	  /** Sentiment magnitude of the user utterance if [sentiment](https://cloud.google.com/dialogflow/cx/docs/concept/sentiment) was enabled. */
		sentimentMagnitude: Option[BigDecimal] = None
	)
	
	case class GoogleCloudDialogflowV2BatchUpdateEntityTypesResponse(
	  /** The collection of updated or created entity types. */
		entityTypes: Option[List[Schema.GoogleCloudDialogflowV2EntityType]] = None
	)
	
	object GoogleCloudDialogflowV2EntityType {
		enum KindEnum extends Enum[KindEnum] { case KIND_UNSPECIFIED, KIND_MAP, KIND_LIST, KIND_REGEXP }
		enum AutoExpansionModeEnum extends Enum[AutoExpansionModeEnum] { case AUTO_EXPANSION_MODE_UNSPECIFIED, AUTO_EXPANSION_MODE_DEFAULT }
	}
	case class GoogleCloudDialogflowV2EntityType(
	  /** The unique identifier of the entity type. Required for EntityTypes.UpdateEntityType and EntityTypes.BatchUpdateEntityTypes methods. Format: `projects//agent/entityTypes/`. */
		name: Option[String] = None,
	  /** Required. The name of the entity type. */
		displayName: Option[String] = None,
	  /** Required. Indicates the kind of entity type. */
		kind: Option[Schema.GoogleCloudDialogflowV2EntityType.KindEnum] = None,
	  /** Optional. Indicates whether the entity type can be automatically expanded. */
		autoExpansionMode: Option[Schema.GoogleCloudDialogflowV2EntityType.AutoExpansionModeEnum] = None,
	  /** Optional. The collection of entity entries associated with the entity type. */
		entities: Option[List[Schema.GoogleCloudDialogflowV2EntityTypeEntity]] = None,
	  /** Optional. Enables fuzzy entity extraction during classification. */
		enableFuzzyExtraction: Option[Boolean] = None
	)
	
	case class GoogleCloudDialogflowV2EntityTypeEntity(
	  /** Required. The primary value associated with this entity entry. For example, if the entity type is &#42;vegetable&#42;, the value could be &#42;scallions&#42;. For `KIND_MAP` entity types: &#42; A reference value to be used in place of synonyms. For `KIND_LIST` entity types: &#42; A string that can contain references to other entity types (with or without aliases). */
		value: Option[String] = None,
	  /** Required. A collection of value synonyms. For example, if the entity type is &#42;vegetable&#42;, and `value` is &#42;scallions&#42;, a synonym could be &#42;green onions&#42;. For `KIND_LIST` entity types: &#42; This collection must contain exactly one synonym equal to `value`. */
		synonyms: Option[List[String]] = None
	)
	
	case class GoogleCloudDialogflowV2BatchUpdateIntentsResponse(
	  /** The collection of updated or created intents. */
		intents: Option[List[Schema.GoogleCloudDialogflowV2Intent]] = None
	)
	
	object GoogleCloudDialogflowV2Intent {
		enum WebhookStateEnum extends Enum[WebhookStateEnum] { case WEBHOOK_STATE_UNSPECIFIED, WEBHOOK_STATE_ENABLED, WEBHOOK_STATE_ENABLED_FOR_SLOT_FILLING }
		enum DefaultResponsePlatformsEnum extends Enum[DefaultResponsePlatformsEnum] { case PLATFORM_UNSPECIFIED, FACEBOOK, SLACK, TELEGRAM, KIK, SKYPE, LINE, VIBER, ACTIONS_ON_GOOGLE, GOOGLE_HANGOUTS }
	}
	case class GoogleCloudDialogflowV2Intent(
	  /** Optional. The unique identifier of this intent. Required for Intents.UpdateIntent and Intents.BatchUpdateIntents methods. Format: `projects//agent/intents/`. */
		name: Option[String] = None,
	  /** Required. The name of this intent. */
		displayName: Option[String] = None,
	  /** Optional. Indicates whether webhooks are enabled for the intent. */
		webhookState: Option[Schema.GoogleCloudDialogflowV2Intent.WebhookStateEnum] = None,
	  /** Optional. The priority of this intent. Higher numbers represent higher priorities. - If the supplied value is unspecified or 0, the service translates the value to 500,000, which corresponds to the `Normal` priority in the console. - If the supplied value is negative, the intent is ignored in runtime detect intent requests. */
		priority: Option[Int] = None,
	  /** Optional. Indicates whether this is a fallback intent. */
		isFallback: Option[Boolean] = None,
	  /** Optional. Indicates whether Machine Learning is disabled for the intent. Note: If `ml_disabled` setting is set to true, then this intent is not taken into account during inference in `ML ONLY` match mode. Also, auto-markup in the UI is turned off. */
		mlDisabled: Option[Boolean] = None,
	  /** Optional. Indicates that a live agent should be brought in to handle the interaction with the user. In most cases, when you set this flag to true, you would also want to set end_interaction to true as well. Default is false. */
		liveAgentHandoff: Option[Boolean] = None,
	  /** Optional. Indicates that this intent ends an interaction. Some integrations (e.g., Actions on Google or Dialogflow phone gateway) use this information to close interaction with an end user. Default is false. */
		endInteraction: Option[Boolean] = None,
	  /** Optional. The list of context names required for this intent to be triggered. Format: `projects//agent/sessions/-/contexts/`. */
		inputContextNames: Option[List[String]] = None,
	  /** Optional. The collection of event names that trigger the intent. If the collection of input contexts is not empty, all of the contexts must be present in the active user session for an event to trigger this intent. Event names are limited to 150 characters. */
		events: Option[List[String]] = None,
	  /** Optional. The collection of examples that the agent is trained on. */
		trainingPhrases: Option[List[Schema.GoogleCloudDialogflowV2IntentTrainingPhrase]] = None,
	  /** Optional. The name of the action associated with the intent. Note: The action name must not contain whitespaces. */
		action: Option[String] = None,
	  /** Optional. The collection of contexts that are activated when the intent is matched. Context messages in this collection should not set the parameters field. Setting the `lifespan_count` to 0 will reset the context when the intent is matched. Format: `projects//agent/sessions/-/contexts/`. */
		outputContexts: Option[List[Schema.GoogleCloudDialogflowV2Context]] = None,
	  /** Optional. Indicates whether to delete all contexts in the current session when this intent is matched. */
		resetContexts: Option[Boolean] = None,
	  /** Optional. The collection of parameters associated with the intent. */
		parameters: Option[List[Schema.GoogleCloudDialogflowV2IntentParameter]] = None,
	  /** Optional. The collection of rich messages corresponding to the `Response` field in the Dialogflow console. */
		messages: Option[List[Schema.GoogleCloudDialogflowV2IntentMessage]] = None,
	  /** Optional. The list of platforms for which the first responses will be copied from the messages in PLATFORM_UNSPECIFIED (i.e. default platform). */
		defaultResponsePlatforms: Option[List[Schema.GoogleCloudDialogflowV2Intent.DefaultResponsePlatformsEnum]] = None,
	  /** Output only. Read-only. The unique identifier of the root intent in the chain of followup intents. It identifies the correct followup intents chain for this intent. We populate this field only in the output. Format: `projects//agent/intents/`. */
		rootFollowupIntentName: Option[String] = None,
	  /** Read-only after creation. The unique identifier of the parent intent in the chain of followup intents. You can set this field when creating an intent, for example with CreateIntent or BatchUpdateIntents, in order to make this intent a followup intent. It identifies the parent followup intent. Format: `projects//agent/intents/`. */
		parentFollowupIntentName: Option[String] = None,
	  /** Output only. Read-only. Information about all followup intents that have this intent as a direct or indirect parent. We populate this field only in the output. */
		followupIntentInfo: Option[List[Schema.GoogleCloudDialogflowV2IntentFollowupIntentInfo]] = None
	)
	
	object GoogleCloudDialogflowV2IntentTrainingPhrase {
		enum TypeEnum extends Enum[TypeEnum] { case TYPE_UNSPECIFIED, EXAMPLE, TEMPLATE }
	}
	case class GoogleCloudDialogflowV2IntentTrainingPhrase(
	  /** Output only. The unique identifier of this training phrase. */
		name: Option[String] = None,
	  /** Required. The type of the training phrase. */
		`type`: Option[Schema.GoogleCloudDialogflowV2IntentTrainingPhrase.TypeEnum] = None,
	  /** Required. The ordered list of training phrase parts. The parts are concatenated in order to form the training phrase. Note: The API does not automatically annotate training phrases like the Dialogflow Console does. Note: Do not forget to include whitespace at part boundaries, so the training phrase is well formatted when the parts are concatenated. If the training phrase does not need to be annotated with parameters, you just need a single part with only the Part.text field set. If you want to annotate the training phrase, you must create multiple parts, where the fields of each part are populated in one of two ways: - `Part.text` is set to a part of the phrase that has no parameters. - `Part.text` is set to a part of the phrase that you want to annotate, and the `entity_type`, `alias`, and `user_defined` fields are all set. */
		parts: Option[List[Schema.GoogleCloudDialogflowV2IntentTrainingPhrasePart]] = None,
	  /** Optional. Indicates how many times this example was added to the intent. Each time a developer adds an existing sample by editing an intent or training, this counter is increased. */
		timesAddedCount: Option[Int] = None
	)
	
	case class GoogleCloudDialogflowV2IntentTrainingPhrasePart(
	  /** Required. The text for this part. */
		text: Option[String] = None,
	  /** Optional. The entity type name prefixed with `@`. This field is required for annotated parts of the training phrase. */
		entityType: Option[String] = None,
	  /** Optional. The parameter name for the value extracted from the annotated part of the example. This field is required for annotated parts of the training phrase. */
		alias: Option[String] = None,
	  /** Optional. Indicates whether the text was manually annotated. This field is set to true when the Dialogflow Console is used to manually annotate the part. When creating an annotated part with the API, you must set this to true. */
		userDefined: Option[Boolean] = None
	)
	
	case class GoogleCloudDialogflowV2Context(
	  /** Required. The unique identifier of the context. Format: `projects//agent/sessions//contexts/`, or `projects//agent/environments//users//sessions//contexts/`. The `Context ID` is always converted to lowercase, may only contain characters in `a-zA-Z0-9_-%` and may be at most 250 bytes long. If `Environment ID` is not specified, we assume default 'draft' environment. If `User ID` is not specified, we assume default '-' user. The following context names are reserved for internal use by Dialogflow. You should not use these contexts or create contexts with these names: &#42; `__system_counters__` &#42; `&#42;_id_dialog_context` &#42; `&#42;_dialog_params_size` */
		name: Option[String] = None,
	  /** Optional. The number of conversational query requests after which the context expires. The default is `0`. If set to `0`, the context expires immediately. Contexts expire automatically after 20 minutes if there are no matching queries. */
		lifespanCount: Option[Int] = None,
	  /** Optional. The collection of parameters associated with this context. Depending on your protocol or client library language, this is a map, associative array, symbol table, dictionary, or JSON object composed of a collection of (MapKey, MapValue) pairs: &#42; MapKey type: string &#42; MapKey value: parameter name &#42; MapValue type: If parameter's entity type is a composite entity then use map, otherwise, depending on the parameter value type, it could be one of string, number, boolean, null, list or map. &#42; MapValue value: If parameter's entity type is a composite entity then use map from composite entity property names to property values, otherwise, use parameter value. */
		parameters: Option[Map[String, JsValue]] = None
	)
	
	case class GoogleCloudDialogflowV2IntentParameter(
	  /** The unique identifier of this parameter. */
		name: Option[String] = None,
	  /** Required. The name of the parameter. */
		displayName: Option[String] = None,
	  /** Optional. The definition of the parameter value. It can be: - a constant string, - a parameter value defined as `$parameter_name`, - an original parameter value defined as `$parameter_name.original`, - a parameter value from some context defined as `#context_name.parameter_name`. */
		value: Option[String] = None,
	  /** Optional. The default value to use when the `value` yields an empty result. Default values can be extracted from contexts by using the following syntax: `#context_name.parameter_name`. */
		defaultValue: Option[String] = None,
	  /** Optional. The name of the entity type, prefixed with `@`, that describes values of the parameter. If the parameter is required, this must be provided. */
		entityTypeDisplayName: Option[String] = None,
	  /** Optional. Indicates whether the parameter is required. That is, whether the intent cannot be completed without collecting the parameter value. */
		mandatory: Option[Boolean] = None,
	  /** Optional. The collection of prompts that the agent can present to the user in order to collect a value for the parameter. */
		prompts: Option[List[String]] = None,
	  /** Optional. Indicates whether the parameter represents a list of values. */
		isList: Option[Boolean] = None
	)
	
	object GoogleCloudDialogflowV2IntentMessage {
		enum PlatformEnum extends Enum[PlatformEnum] { case PLATFORM_UNSPECIFIED, FACEBOOK, SLACK, TELEGRAM, KIK, SKYPE, LINE, VIBER, ACTIONS_ON_GOOGLE, GOOGLE_HANGOUTS }
	}
	case class GoogleCloudDialogflowV2IntentMessage(
	  /** The text response. */
		text: Option[Schema.GoogleCloudDialogflowV2IntentMessageText] = None,
	  /** The image response. */
		image: Option[Schema.GoogleCloudDialogflowV2IntentMessageImage] = None,
	  /** The quick replies response. */
		quickReplies: Option[Schema.GoogleCloudDialogflowV2IntentMessageQuickReplies] = None,
	  /** The card response. */
		card: Option[Schema.GoogleCloudDialogflowV2IntentMessageCard] = None,
	  /** A custom platform-specific response. */
		payload: Option[Map[String, JsValue]] = None,
	  /** The voice and text-only responses for Actions on Google. */
		simpleResponses: Option[Schema.GoogleCloudDialogflowV2IntentMessageSimpleResponses] = None,
	  /** The basic card response for Actions on Google. */
		basicCard: Option[Schema.GoogleCloudDialogflowV2IntentMessageBasicCard] = None,
	  /** The suggestion chips for Actions on Google. */
		suggestions: Option[Schema.GoogleCloudDialogflowV2IntentMessageSuggestions] = None,
	  /** The link out suggestion chip for Actions on Google. */
		linkOutSuggestion: Option[Schema.GoogleCloudDialogflowV2IntentMessageLinkOutSuggestion] = None,
	  /** The list card response for Actions on Google. */
		listSelect: Option[Schema.GoogleCloudDialogflowV2IntentMessageListSelect] = None,
	  /** The carousel card response for Actions on Google. */
		carouselSelect: Option[Schema.GoogleCloudDialogflowV2IntentMessageCarouselSelect] = None,
	  /** Browse carousel card for Actions on Google. */
		browseCarouselCard: Option[Schema.GoogleCloudDialogflowV2IntentMessageBrowseCarouselCard] = None,
	  /** Table card for Actions on Google. */
		tableCard: Option[Schema.GoogleCloudDialogflowV2IntentMessageTableCard] = None,
	  /** The media content card for Actions on Google. */
		mediaContent: Option[Schema.GoogleCloudDialogflowV2IntentMessageMediaContent] = None,
	  /** Optional. The platform that this message is intended for. */
		platform: Option[Schema.GoogleCloudDialogflowV2IntentMessage.PlatformEnum] = None
	)
	
	case class GoogleCloudDialogflowV2IntentMessageText(
	  /** Optional. The collection of the agent's responses. */
		text: Option[List[String]] = None
	)
	
	case class GoogleCloudDialogflowV2IntentMessageImage(
	  /** Optional. The public URI to an image file. */
		imageUri: Option[String] = None,
	  /** Optional. A text description of the image to be used for accessibility, e.g., screen readers. */
		accessibilityText: Option[String] = None
	)
	
	case class GoogleCloudDialogflowV2IntentMessageQuickReplies(
	  /** Optional. The title of the collection of quick replies. */
		title: Option[String] = None,
	  /** Optional. The collection of quick replies. */
		quickReplies: Option[List[String]] = None
	)
	
	case class GoogleCloudDialogflowV2IntentMessageCard(
	  /** Optional. The title of the card. */
		title: Option[String] = None,
	  /** Optional. The subtitle of the card. */
		subtitle: Option[String] = None,
	  /** Optional. The public URI to an image file for the card. */
		imageUri: Option[String] = None,
	  /** Optional. The collection of card buttons. */
		buttons: Option[List[Schema.GoogleCloudDialogflowV2IntentMessageCardButton]] = None
	)
	
	case class GoogleCloudDialogflowV2IntentMessageCardButton(
	  /** Optional. The text to show on the button. */
		text: Option[String] = None,
	  /** Optional. The text to send back to the Dialogflow API or a URI to open. */
		postback: Option[String] = None
	)
	
	case class GoogleCloudDialogflowV2IntentMessageSimpleResponses(
	  /** Required. The list of simple responses. */
		simpleResponses: Option[List[Schema.GoogleCloudDialogflowV2IntentMessageSimpleResponse]] = None
	)
	
	case class GoogleCloudDialogflowV2IntentMessageSimpleResponse(
	  /** One of text_to_speech or ssml must be provided. The plain text of the speech output. Mutually exclusive with ssml. */
		textToSpeech: Option[String] = None,
	  /** One of text_to_speech or ssml must be provided. Structured spoken response to the user in the SSML format. Mutually exclusive with text_to_speech. */
		ssml: Option[String] = None,
	  /** Optional. The text to display. */
		displayText: Option[String] = None
	)
	
	case class GoogleCloudDialogflowV2IntentMessageBasicCard(
	  /** Optional. The title of the card. */
		title: Option[String] = None,
	  /** Optional. The subtitle of the card. */
		subtitle: Option[String] = None,
	  /** Required, unless image is present. The body text of the card. */
		formattedText: Option[String] = None,
	  /** Optional. The image for the card. */
		image: Option[Schema.GoogleCloudDialogflowV2IntentMessageImage] = None,
	  /** Optional. The collection of card buttons. */
		buttons: Option[List[Schema.GoogleCloudDialogflowV2IntentMessageBasicCardButton]] = None
	)
	
	case class GoogleCloudDialogflowV2IntentMessageBasicCardButton(
	  /** Required. The title of the button. */
		title: Option[String] = None,
	  /** Required. Action to take when a user taps on the button. */
		openUriAction: Option[Schema.GoogleCloudDialogflowV2IntentMessageBasicCardButtonOpenUriAction] = None
	)
	
	case class GoogleCloudDialogflowV2IntentMessageBasicCardButtonOpenUriAction(
	  /** Required. The HTTP or HTTPS scheme URI. */
		uri: Option[String] = None
	)
	
	case class GoogleCloudDialogflowV2IntentMessageSuggestions(
	  /** Required. The list of suggested replies. */
		suggestions: Option[List[Schema.GoogleCloudDialogflowV2IntentMessageSuggestion]] = None
	)
	
	case class GoogleCloudDialogflowV2IntentMessageSuggestion(
	  /** Required. The text shown the in the suggestion chip. */
		title: Option[String] = None
	)
	
	case class GoogleCloudDialogflowV2IntentMessageLinkOutSuggestion(
	  /** Required. The name of the app or site this chip is linking to. */
		destinationName: Option[String] = None,
	  /** Required. The URI of the app or site to open when the user taps the suggestion chip. */
		uri: Option[String] = None
	)
	
	case class GoogleCloudDialogflowV2IntentMessageListSelect(
	  /** Optional. The overall title of the list. */
		title: Option[String] = None,
	  /** Required. List items. */
		items: Option[List[Schema.GoogleCloudDialogflowV2IntentMessageListSelectItem]] = None,
	  /** Optional. Subtitle of the list. */
		subtitle: Option[String] = None
	)
	
	case class GoogleCloudDialogflowV2IntentMessageListSelectItem(
	  /** Required. Additional information about this option. */
		info: Option[Schema.GoogleCloudDialogflowV2IntentMessageSelectItemInfo] = None,
	  /** Required. The title of the list item. */
		title: Option[String] = None,
	  /** Optional. The main text describing the item. */
		description: Option[String] = None,
	  /** Optional. The image to display. */
		image: Option[Schema.GoogleCloudDialogflowV2IntentMessageImage] = None
	)
	
	case class GoogleCloudDialogflowV2IntentMessageSelectItemInfo(
	  /** Required. A unique key that will be sent back to the agent if this response is given. */
		key: Option[String] = None,
	  /** Optional. A list of synonyms that can also be used to trigger this item in dialog. */
		synonyms: Option[List[String]] = None
	)
	
	case class GoogleCloudDialogflowV2IntentMessageCarouselSelect(
	  /** Required. Carousel items. */
		items: Option[List[Schema.GoogleCloudDialogflowV2IntentMessageCarouselSelectItem]] = None
	)
	
	case class GoogleCloudDialogflowV2IntentMessageCarouselSelectItem(
	  /** Required. Additional info about the option item. */
		info: Option[Schema.GoogleCloudDialogflowV2IntentMessageSelectItemInfo] = None,
	  /** Required. Title of the carousel item. */
		title: Option[String] = None,
	  /** Optional. The body text of the card. */
		description: Option[String] = None,
	  /** Optional. The image to display. */
		image: Option[Schema.GoogleCloudDialogflowV2IntentMessageImage] = None
	)
	
	object GoogleCloudDialogflowV2IntentMessageBrowseCarouselCard {
		enum ImageDisplayOptionsEnum extends Enum[ImageDisplayOptionsEnum] { case IMAGE_DISPLAY_OPTIONS_UNSPECIFIED, GRAY, WHITE, CROPPED, BLURRED_BACKGROUND }
	}
	case class GoogleCloudDialogflowV2IntentMessageBrowseCarouselCard(
	  /** Required. List of items in the Browse Carousel Card. Minimum of two items, maximum of ten. */
		items: Option[List[Schema.GoogleCloudDialogflowV2IntentMessageBrowseCarouselCardBrowseCarouselCardItem]] = None,
	  /** Optional. Settings for displaying the image. Applies to every image in items. */
		imageDisplayOptions: Option[Schema.GoogleCloudDialogflowV2IntentMessageBrowseCarouselCard.ImageDisplayOptionsEnum] = None
	)
	
	case class GoogleCloudDialogflowV2IntentMessageBrowseCarouselCardBrowseCarouselCardItem(
	  /** Required. Action to present to the user. */
		openUriAction: Option[Schema.GoogleCloudDialogflowV2IntentMessageBrowseCarouselCardBrowseCarouselCardItemOpenUrlAction] = None,
	  /** Required. Title of the carousel item. Maximum of two lines of text. */
		title: Option[String] = None,
	  /** Optional. Description of the carousel item. Maximum of four lines of text. */
		description: Option[String] = None,
	  /** Optional. Hero image for the carousel item. */
		image: Option[Schema.GoogleCloudDialogflowV2IntentMessageImage] = None,
	  /** Optional. Text that appears at the bottom of the Browse Carousel Card. Maximum of one line of text. */
		footer: Option[String] = None
	)
	
	object GoogleCloudDialogflowV2IntentMessageBrowseCarouselCardBrowseCarouselCardItemOpenUrlAction {
		enum UrlTypeHintEnum extends Enum[UrlTypeHintEnum] { case URL_TYPE_HINT_UNSPECIFIED, AMP_ACTION, AMP_CONTENT }
	}
	case class GoogleCloudDialogflowV2IntentMessageBrowseCarouselCardBrowseCarouselCardItemOpenUrlAction(
	  /** Required. URL */
		url: Option[String] = None,
	  /** Optional. Specifies the type of viewer that is used when opening the URL. Defaults to opening via web browser. */
		urlTypeHint: Option[Schema.GoogleCloudDialogflowV2IntentMessageBrowseCarouselCardBrowseCarouselCardItemOpenUrlAction.UrlTypeHintEnum] = None
	)
	
	case class GoogleCloudDialogflowV2IntentMessageTableCard(
	  /** Required. Title of the card. */
		title: Option[String] = None,
	  /** Optional. Subtitle to the title. */
		subtitle: Option[String] = None,
	  /** Optional. Image which should be displayed on the card. */
		image: Option[Schema.GoogleCloudDialogflowV2IntentMessageImage] = None,
	  /** Optional. Display properties for the columns in this table. */
		columnProperties: Option[List[Schema.GoogleCloudDialogflowV2IntentMessageColumnProperties]] = None,
	  /** Optional. Rows in this table of data. */
		rows: Option[List[Schema.GoogleCloudDialogflowV2IntentMessageTableCardRow]] = None,
	  /** Optional. List of buttons for the card. */
		buttons: Option[List[Schema.GoogleCloudDialogflowV2IntentMessageBasicCardButton]] = None
	)
	
	object GoogleCloudDialogflowV2IntentMessageColumnProperties {
		enum HorizontalAlignmentEnum extends Enum[HorizontalAlignmentEnum] { case HORIZONTAL_ALIGNMENT_UNSPECIFIED, LEADING, CENTER, TRAILING }
	}
	case class GoogleCloudDialogflowV2IntentMessageColumnProperties(
	  /** Required. Column heading. */
		header: Option[String] = None,
	  /** Optional. Defines text alignment for all cells in this column. */
		horizontalAlignment: Option[Schema.GoogleCloudDialogflowV2IntentMessageColumnProperties.HorizontalAlignmentEnum] = None
	)
	
	case class GoogleCloudDialogflowV2IntentMessageTableCardRow(
	  /** Optional. List of cells that make up this row. */
		cells: Option[List[Schema.GoogleCloudDialogflowV2IntentMessageTableCardCell]] = None,
	  /** Optional. Whether to add a visual divider after this row. */
		dividerAfter: Option[Boolean] = None
	)
	
	case class GoogleCloudDialogflowV2IntentMessageTableCardCell(
	  /** Required. Text in this cell. */
		text: Option[String] = None
	)
	
	object GoogleCloudDialogflowV2IntentMessageMediaContent {
		enum MediaTypeEnum extends Enum[MediaTypeEnum] { case RESPONSE_MEDIA_TYPE_UNSPECIFIED, AUDIO }
	}
	case class GoogleCloudDialogflowV2IntentMessageMediaContent(
	  /** Optional. What type of media is the content (ie "audio"). */
		mediaType: Option[Schema.GoogleCloudDialogflowV2IntentMessageMediaContent.MediaTypeEnum] = None,
	  /** Required. List of media objects. */
		mediaObjects: Option[List[Schema.GoogleCloudDialogflowV2IntentMessageMediaContentResponseMediaObject]] = None
	)
	
	case class GoogleCloudDialogflowV2IntentMessageMediaContentResponseMediaObject(
	  /** Required. Name of media card. */
		name: Option[String] = None,
	  /** Optional. Description of media card. */
		description: Option[String] = None,
	  /** Optional. Image to display above media content. */
		largeImage: Option[Schema.GoogleCloudDialogflowV2IntentMessageImage] = None,
	  /** Optional. Icon to display above media content. */
		icon: Option[Schema.GoogleCloudDialogflowV2IntentMessageImage] = None,
	  /** Required. Url where the media is stored. */
		contentUrl: Option[String] = None
	)
	
	case class GoogleCloudDialogflowV2IntentFollowupIntentInfo(
	  /** The unique identifier of the followup intent. Format: `projects//agent/intents/`. */
		followupIntentName: Option[String] = None,
	  /** The unique identifier of the followup intent's parent. Format: `projects//agent/intents/`. */
		parentFollowupIntentName: Option[String] = None
	)
	
	object GoogleCloudDialogflowV2ClearSuggestionFeatureConfigOperationMetadata {
		enum ParticipantRoleEnum extends Enum[ParticipantRoleEnum] { case ROLE_UNSPECIFIED, HUMAN_AGENT, AUTOMATED_AGENT, END_USER }
		enum SuggestionFeatureTypeEnum extends Enum[SuggestionFeatureTypeEnum] { case TYPE_UNSPECIFIED, ARTICLE_SUGGESTION, FAQ, SMART_REPLY, KNOWLEDGE_SEARCH, KNOWLEDGE_ASSIST }
	}
	case class GoogleCloudDialogflowV2ClearSuggestionFeatureConfigOperationMetadata(
	  /** The resource name of the conversation profile. Format: `projects//locations//conversationProfiles/` */
		conversationProfile: Option[String] = None,
	  /** Required. The participant role to remove the suggestion feature config. Only HUMAN_AGENT or END_USER can be used. */
		participantRole: Option[Schema.GoogleCloudDialogflowV2ClearSuggestionFeatureConfigOperationMetadata.ParticipantRoleEnum] = None,
	  /** Required. The type of the suggestion feature to remove. */
		suggestionFeatureType: Option[Schema.GoogleCloudDialogflowV2ClearSuggestionFeatureConfigOperationMetadata.SuggestionFeatureTypeEnum] = None,
	  /** Timestamp whe the request was created. The time is measured on server side. */
		createTime: Option[String] = None
	)
	
	object GoogleCloudDialogflowV2ConversationEvent {
		enum TypeEnum extends Enum[TypeEnum] { case TYPE_UNSPECIFIED, CONVERSATION_STARTED, CONVERSATION_FINISHED, HUMAN_INTERVENTION_NEEDED, NEW_MESSAGE, UNRECOVERABLE_ERROR }
	}
	case class GoogleCloudDialogflowV2ConversationEvent(
	  /** The unique identifier of the conversation this notification refers to. Format: `projects//conversations/`. */
		conversation: Option[String] = None,
	  /** The type of the event that this notification refers to. */
		`type`: Option[Schema.GoogleCloudDialogflowV2ConversationEvent.TypeEnum] = None,
	  /** More detailed information about an error. Only set for type UNRECOVERABLE_ERROR_IN_PHONE_CALL. */
		errorStatus: Option[Schema.GoogleRpcStatus] = None,
	  /** Payload of NEW_MESSAGE event. */
		newMessagePayload: Option[Schema.GoogleCloudDialogflowV2Message] = None
	)
	
	object GoogleCloudDialogflowV2Message {
		enum ParticipantRoleEnum extends Enum[ParticipantRoleEnum] { case ROLE_UNSPECIFIED, HUMAN_AGENT, AUTOMATED_AGENT, END_USER }
	}
	case class GoogleCloudDialogflowV2Message(
	  /** Optional. The unique identifier of the message. Format: `projects//locations//conversations//messages/`. */
		name: Option[String] = None,
	  /** Required. The message content. */
		content: Option[String] = None,
	  /** Optional. The message language. This should be a [BCP-47](https://www.rfc-editor.org/rfc/bcp/bcp47.txt) language tag. Example: "en-US". */
		languageCode: Option[String] = None,
	  /** Output only. The participant that sends this message. */
		participant: Option[String] = None,
	  /** Output only. The role of the participant. */
		participantRole: Option[Schema.GoogleCloudDialogflowV2Message.ParticipantRoleEnum] = None,
	  /** Output only. The time when the message was created in Contact Center AI. */
		createTime: Option[String] = None,
	  /** Optional. The time when the message was sent. */
		sendTime: Option[String] = None,
	  /** Output only. The annotation for the message. */
		messageAnnotation: Option[Schema.GoogleCloudDialogflowV2MessageAnnotation] = None,
	  /** Output only. The sentiment analysis result for the message. */
		sentimentAnalysis: Option[Schema.GoogleCloudDialogflowV2SentimentAnalysisResult] = None
	)
	
	case class GoogleCloudDialogflowV2MessageAnnotation(
	  /** The collection of annotated message parts ordered by their position in the message. You can recover the annotated message by concatenating [AnnotatedMessagePart.text]. */
		parts: Option[List[Schema.GoogleCloudDialogflowV2AnnotatedMessagePart]] = None,
	  /** Indicates whether the text message contains entities. */
		containEntities: Option[Boolean] = None
	)
	
	case class GoogleCloudDialogflowV2AnnotatedMessagePart(
	  /** A part of a message possibly annotated with an entity. */
		text: Option[String] = None,
	  /** The [Dialogflow system entity type](https://cloud.google.com/dialogflow/docs/reference/system-entities) of this message part. If this is empty, Dialogflow could not annotate the phrase part with a system entity. */
		entityType: Option[String] = None,
	  /** The [Dialogflow system entity formatted value ](https://cloud.google.com/dialogflow/docs/reference/system-entities) of this message part. For example for a system entity of type `@sys.unit-currency`, this may contain: { "amount": 5, "currency": "USD" }  */
		formattedValue: Option[JsValue] = None
	)
	
	case class GoogleCloudDialogflowV2SentimentAnalysisResult(
	  /** The sentiment analysis result for `query_text`. */
		queryTextSentiment: Option[Schema.GoogleCloudDialogflowV2Sentiment] = None
	)
	
	case class GoogleCloudDialogflowV2Sentiment(
	  /** Sentiment score between -1.0 (negative sentiment) and 1.0 (positive sentiment). */
		score: Option[BigDecimal] = None,
	  /** A non-negative number in the [0, +inf) range, which represents the absolute magnitude of sentiment, regardless of score (positive or negative). */
		magnitude: Option[BigDecimal] = None
	)
	
	case class GoogleCloudDialogflowV2ExportAgentResponse(
	  /** The URI to a file containing the exported agent. This field is populated only if `agent_uri` is specified in `ExportAgentRequest`. */
		agentUri: Option[String] = None,
	  /** Zip compressed raw byte content for agent. */
		agentContent: Option[String] = None
	)
	
	case class GoogleCloudDialogflowV2HumanAgentAssistantEvent(
	  /** The conversation this notification refers to. Format: `projects//conversations/`. */
		conversation: Option[String] = None,
	  /** The participant that the suggestion is compiled for. Format: `projects//conversations//participants/`. It will not be set in legacy workflow. */
		participant: Option[String] = None,
	  /** The suggestion results payload that this notification refers to. */
		suggestionResults: Option[List[Schema.GoogleCloudDialogflowV2SuggestionResult]] = None
	)
	
	case class GoogleCloudDialogflowV2SuggestionResult(
	  /** Error status if the request failed. */
		error: Option[Schema.GoogleRpcStatus] = None,
	  /** SuggestArticlesResponse if request is for ARTICLE_SUGGESTION. */
		suggestArticlesResponse: Option[Schema.GoogleCloudDialogflowV2SuggestArticlesResponse] = None,
	  /** SuggestKnowledgeAssistResponse if request is for KNOWLEDGE_ASSIST. */
		suggestKnowledgeAssistResponse: Option[Schema.GoogleCloudDialogflowV2SuggestKnowledgeAssistResponse] = None,
	  /** SuggestFaqAnswersResponse if request is for FAQ_ANSWER. */
		suggestFaqAnswersResponse: Option[Schema.GoogleCloudDialogflowV2SuggestFaqAnswersResponse] = None,
	  /** SuggestSmartRepliesResponse if request is for SMART_REPLY. */
		suggestSmartRepliesResponse: Option[Schema.GoogleCloudDialogflowV2SuggestSmartRepliesResponse] = None
	)
	
	case class GoogleCloudDialogflowV2SuggestArticlesResponse(
	  /** Articles ordered by score in descending order. */
		articleAnswers: Option[List[Schema.GoogleCloudDialogflowV2ArticleAnswer]] = None,
	  /** The name of the latest conversation message used to compile suggestion for. Format: `projects//locations//conversations//messages/`. */
		latestMessage: Option[String] = None,
	  /** Number of messages prior to and including latest_message to compile the suggestion. It may be smaller than the SuggestArticlesRequest.context_size field in the request if there aren't that many messages in the conversation. */
		contextSize: Option[Int] = None
	)
	
	case class GoogleCloudDialogflowV2ArticleAnswer(
	  /** The article title. */
		title: Option[String] = None,
	  /** The article URI. */
		uri: Option[String] = None,
	  /** Article snippets. */
		snippets: Option[List[String]] = None,
	  /** Article match confidence. The system's confidence score that this article is a good match for this conversation, as a value from 0.0 (completely uncertain) to 1.0 (completely certain). */
		confidence: Option[BigDecimal] = None,
	  /** A map that contains metadata about the answer and the document from which it originates. */
		metadata: Option[Map[String, String]] = None,
	  /** The name of answer record, in the format of "projects//locations//answerRecords/" */
		answerRecord: Option[String] = None
	)
	
	case class GoogleCloudDialogflowV2SuggestKnowledgeAssistResponse(
	  /** Output only. Knowledge Assist suggestion. */
		knowledgeAssistAnswer: Option[Schema.GoogleCloudDialogflowV2KnowledgeAssistAnswer] = None,
	  /** The name of the latest conversation message used to compile suggestion for. Format: `projects//locations//conversations//messages/`. */
		latestMessage: Option[String] = None,
	  /** Number of messages prior to and including latest_message to compile the suggestion. It may be smaller than the SuggestKnowledgeAssistRequest.context_size field in the request if there are fewer messages in the conversation. */
		contextSize: Option[Int] = None
	)
	
	case class GoogleCloudDialogflowV2KnowledgeAssistAnswer(
	  /** The query suggested based on the context. Suggestion is made only if it is different from the previous suggestion. */
		suggestedQuery: Option[Schema.GoogleCloudDialogflowV2KnowledgeAssistAnswerSuggestedQuery] = None,
	  /** The answer generated for the suggested query. Whether or not an answer is generated depends on how confident we are about the generated query. */
		suggestedQueryAnswer: Option[Schema.GoogleCloudDialogflowV2KnowledgeAssistAnswerKnowledgeAnswer] = None,
	  /** The name of the answer record. Format: `projects//locations//answer Records/`. */
		answerRecord: Option[String] = None
	)
	
	case class GoogleCloudDialogflowV2KnowledgeAssistAnswerSuggestedQuery(
	  /** Suggested query text. */
		queryText: Option[String] = None
	)
	
	case class GoogleCloudDialogflowV2KnowledgeAssistAnswerKnowledgeAnswer(
	  /** The piece of text from the `source` that answers this suggested query. */
		answerText: Option[String] = None,
	  /** Populated if the prediction came from FAQ. */
		faqSource: Option[Schema.GoogleCloudDialogflowV2KnowledgeAssistAnswerKnowledgeAnswerFaqSource] = None,
	  /** Populated if the prediction was Generative. */
		generativeSource: Option[Schema.GoogleCloudDialogflowV2KnowledgeAssistAnswerKnowledgeAnswerGenerativeSource] = None
	)
	
	case class GoogleCloudDialogflowV2KnowledgeAssistAnswerKnowledgeAnswerFaqSource(
	  /** The corresponding FAQ question. */
		question: Option[String] = None
	)
	
	case class GoogleCloudDialogflowV2KnowledgeAssistAnswerKnowledgeAnswerGenerativeSource(
	  /** All snippets used for this Generative Prediction, with their source URI and data. */
		snippets: Option[List[Schema.GoogleCloudDialogflowV2KnowledgeAssistAnswerKnowledgeAnswerGenerativeSourceSnippet]] = None
	)
	
	case class GoogleCloudDialogflowV2KnowledgeAssistAnswerKnowledgeAnswerGenerativeSourceSnippet(
	  /** URI the data is sourced from. */
		uri: Option[String] = None,
	  /** Text taken from that URI. */
		text: Option[String] = None,
	  /** Title of the document. */
		title: Option[String] = None,
	  /** Metadata of the document. */
		metadata: Option[Map[String, JsValue]] = None
	)
	
	case class GoogleCloudDialogflowV2SuggestFaqAnswersResponse(
	  /** Answers extracted from FAQ documents. */
		faqAnswers: Option[List[Schema.GoogleCloudDialogflowV2FaqAnswer]] = None,
	  /** The name of the latest conversation message used to compile suggestion for. Format: `projects//locations//conversations//messages/`. */
		latestMessage: Option[String] = None,
	  /** Number of messages prior to and including latest_message to compile the suggestion. It may be smaller than the SuggestFaqAnswersRequest.context_size field in the request if there aren't that many messages in the conversation. */
		contextSize: Option[Int] = None
	)
	
	case class GoogleCloudDialogflowV2FaqAnswer(
	  /** The piece of text from the `source` knowledge base document. */
		answer: Option[String] = None,
	  /** The system's confidence score that this Knowledge answer is a good match for this conversational query, range from 0.0 (completely uncertain) to 1.0 (completely certain). */
		confidence: Option[BigDecimal] = None,
	  /** The corresponding FAQ question. */
		question: Option[String] = None,
	  /** Indicates which Knowledge Document this answer was extracted from. Format: `projects//locations//agent/knowledgeBases//documents/`. */
		source: Option[String] = None,
	  /** A map that contains metadata about the answer and the document from which it originates. */
		metadata: Option[Map[String, String]] = None,
	  /** The name of answer record, in the format of "projects//locations//answerRecords/" */
		answerRecord: Option[String] = None
	)
	
	case class GoogleCloudDialogflowV2SuggestSmartRepliesResponse(
	  /** Output only. Multiple reply options provided by smart reply service. The order is based on the rank of the model prediction. The maximum number of the returned replies is set in SmartReplyConfig. */
		smartReplyAnswers: Option[List[Schema.GoogleCloudDialogflowV2SmartReplyAnswer]] = None,
	  /** The name of the latest conversation message used to compile suggestion for. Format: `projects//locations//conversations//messages/`. */
		latestMessage: Option[String] = None,
	  /** Number of messages prior to and including latest_message to compile the suggestion. It may be smaller than the SuggestSmartRepliesRequest.context_size field in the request if there aren't that many messages in the conversation. */
		contextSize: Option[Int] = None
	)
	
	case class GoogleCloudDialogflowV2SmartReplyAnswer(
	  /** The content of the reply. */
		reply: Option[String] = None,
	  /** Smart reply confidence. The system's confidence score that this reply is a good match for this conversation, as a value from 0.0 (completely uncertain) to 1.0 (completely certain). */
		confidence: Option[BigDecimal] = None,
	  /** The name of answer record, in the format of "projects//locations//answerRecords/" */
		answerRecord: Option[String] = None
	)
	
	case class GoogleCloudDialogflowV2ImportDocumentsResponse(
	  /** Includes details about skipped documents or any other warnings. */
		warnings: Option[List[Schema.GoogleRpcStatus]] = None
	)
	
	object GoogleCloudDialogflowV2KnowledgeOperationMetadata {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, PENDING, RUNNING, DONE }
	}
	case class GoogleCloudDialogflowV2KnowledgeOperationMetadata(
	  /** Output only. The current state of this operation. */
		state: Option[Schema.GoogleCloudDialogflowV2KnowledgeOperationMetadata.StateEnum] = None,
	  /** The name of the knowledge base interacted with during the operation. */
		knowledgeBase: Option[String] = None,
	  /** Metadata for the Export Data Operation such as the destination of export. */
		exportOperationMetadata: Option[Schema.GoogleCloudDialogflowV2ExportOperationMetadata] = None
	)
	
	case class GoogleCloudDialogflowV2ExportOperationMetadata(
	  /** Cloud Storage file path of the exported data. */
		exportedGcsDestination: Option[Schema.GoogleCloudDialogflowV2GcsDestination] = None
	)
	
	case class GoogleCloudDialogflowV2GcsDestination(
	  /** The Google Cloud Storage URIs for the output. A URI is of the form: `gs://bucket/object-prefix-or-name` Whether a prefix or name is used depends on the use case. The requesting user must have "write-permission" to the bucket. */
		uri: Option[String] = None
	)
	
	case class GoogleCloudDialogflowV2OriginalDetectIntentRequest(
	  /** The source of this request, e.g., `google`, `facebook`, `slack`. It is set by Dialogflow-owned servers. */
		source: Option[String] = None,
	  /** Optional. The version of the protocol used for this request. This field is AoG-specific. */
		version: Option[String] = None,
	  /** Optional. This field is set to the value of the `QueryParameters.payload` field passed in the request. Some integrations that query a Dialogflow agent may provide additional information in the payload. In particular, for the Dialogflow Phone Gateway integration, this field has the form: { "telephony": { "caller_id": "+18558363987" } } Note: The caller ID field (`caller_id`) will be redacted for Trial Edition agents and populated with the caller ID in [E.164 format](https://en.wikipedia.org/wiki/E.164) for Essentials Edition agents. */
		payload: Option[Map[String, JsValue]] = None
	)
	
	object GoogleCloudDialogflowV2SetSuggestionFeatureConfigOperationMetadata {
		enum ParticipantRoleEnum extends Enum[ParticipantRoleEnum] { case ROLE_UNSPECIFIED, HUMAN_AGENT, AUTOMATED_AGENT, END_USER }
		enum SuggestionFeatureTypeEnum extends Enum[SuggestionFeatureTypeEnum] { case TYPE_UNSPECIFIED, ARTICLE_SUGGESTION, FAQ, SMART_REPLY, KNOWLEDGE_SEARCH, KNOWLEDGE_ASSIST }
	}
	case class GoogleCloudDialogflowV2SetSuggestionFeatureConfigOperationMetadata(
	  /** The resource name of the conversation profile. Format: `projects//locations//conversationProfiles/` */
		conversationProfile: Option[String] = None,
	  /** Required. The participant role to add or update the suggestion feature config. Only HUMAN_AGENT or END_USER can be used. */
		participantRole: Option[Schema.GoogleCloudDialogflowV2SetSuggestionFeatureConfigOperationMetadata.ParticipantRoleEnum] = None,
	  /** Required. The type of the suggestion feature to add or update. */
		suggestionFeatureType: Option[Schema.GoogleCloudDialogflowV2SetSuggestionFeatureConfigOperationMetadata.SuggestionFeatureTypeEnum] = None,
	  /** Timestamp whe the request was created. The time is measured on server side. */
		createTime: Option[String] = None
	)
	
	case class GoogleCloudDialogflowV2WebhookRequest(
	  /** The unique identifier of detectIntent request session. Can be used to identify end-user inside webhook implementation. Format: `projects//agent/sessions/`, or `projects//agent/environments//users//sessions/`. */
		session: Option[String] = None,
	  /** The unique identifier of the response. Contains the same value as `[Streaming]DetectIntentResponse.response_id`. */
		responseId: Option[String] = None,
	  /** The result of the conversational query or event processing. Contains the same value as `[Streaming]DetectIntentResponse.query_result`. */
		queryResult: Option[Schema.GoogleCloudDialogflowV2QueryResult] = None,
	  /** Optional. The contents of the original request that was passed to `[Streaming]DetectIntent` call. */
		originalDetectIntentRequest: Option[Schema.GoogleCloudDialogflowV2OriginalDetectIntentRequest] = None
	)
	
	case class GoogleCloudDialogflowV2QueryResult(
	  /** The original conversational query text: - If natural language text was provided as input, `query_text` contains a copy of the input. - If natural language speech audio was provided as input, `query_text` contains the speech recognition result. If speech recognizer produced multiple alternatives, a particular one is picked. - If automatic spell correction is enabled, `query_text` will contain the corrected user input. */
		queryText: Option[String] = None,
	  /** The language that was triggered during intent detection. See [Language Support](https://cloud.google.com/dialogflow/docs/reference/language) for a list of the currently supported language codes. */
		languageCode: Option[String] = None,
	  /** The Speech recognition confidence between 0.0 and 1.0. A higher number indicates an estimated greater likelihood that the recognized words are correct. The default of 0.0 is a sentinel value indicating that confidence was not set. This field is not guaranteed to be accurate or set. In particular this field isn't set for StreamingDetectIntent since the streaming endpoint has separate confidence estimates per portion of the audio in StreamingRecognitionResult. */
		speechRecognitionConfidence: Option[BigDecimal] = None,
	  /** The action name from the matched intent. */
		action: Option[String] = None,
	  /** The collection of extracted parameters. Depending on your protocol or client library language, this is a map, associative array, symbol table, dictionary, or JSON object composed of a collection of (MapKey, MapValue) pairs: &#42; MapKey type: string &#42; MapKey value: parameter name &#42; MapValue type: If parameter's entity type is a composite entity then use map, otherwise, depending on the parameter value type, it could be one of string, number, boolean, null, list or map. &#42; MapValue value: If parameter's entity type is a composite entity then use map from composite entity property names to property values, otherwise, use parameter value. */
		parameters: Option[Map[String, JsValue]] = None,
	  /** This field is set to: - `false` if the matched intent has required parameters and not all of the required parameter values have been collected. - `true` if all required parameter values have been collected, or if the matched intent doesn't contain any required parameters. */
		allRequiredParamsPresent: Option[Boolean] = None,
	  /** Indicates whether the conversational query triggers a cancellation for slot filling. For more information, see the [cancel slot filling documentation](https://cloud.google.com/dialogflow/es/docs/intents-actions-parameters#cancel). */
		cancelsSlotFilling: Option[Boolean] = None,
	  /** The text to be pronounced to the user or shown on the screen. Note: This is a legacy field, `fulfillment_messages` should be preferred. */
		fulfillmentText: Option[String] = None,
	  /** The collection of rich messages to present to the user. */
		fulfillmentMessages: Option[List[Schema.GoogleCloudDialogflowV2IntentMessage]] = None,
	  /** If the query was fulfilled by a webhook call, this field is set to the value of the `source` field returned in the webhook response. */
		webhookSource: Option[String] = None,
	  /** If the query was fulfilled by a webhook call, this field is set to the value of the `payload` field returned in the webhook response. */
		webhookPayload: Option[Map[String, JsValue]] = None,
	  /** The collection of output contexts. If applicable, `output_contexts.parameters` contains entries with name `.original` containing the original parameter values before the query. */
		outputContexts: Option[List[Schema.GoogleCloudDialogflowV2Context]] = None,
	  /** The intent that matched the conversational query. Some, not all fields are filled in this message, including but not limited to: `name`, `display_name`, `end_interaction` and `is_fallback`. */
		intent: Option[Schema.GoogleCloudDialogflowV2Intent] = None,
	  /** The intent detection confidence. Values range from 0.0 (completely uncertain) to 1.0 (completely certain). This value is for informational purpose only and is only used to help match the best intent within the classification threshold. This value may change for the same end-user expression at any time due to a model retraining or change in implementation. If there are `multiple knowledge_answers` messages, this value is set to the greatest `knowledgeAnswers.match_confidence` value in the list. */
		intentDetectionConfidence: Option[BigDecimal] = None,
	  /** Free-form diagnostic information for the associated detect intent request. The fields of this data can change without notice, so you should not write code that depends on its structure. The data may contain: - webhook call latency - webhook errors */
		diagnosticInfo: Option[Map[String, JsValue]] = None,
	  /** The sentiment analysis result, which depends on the `sentiment_analysis_request_config` specified in the request. */
		sentimentAnalysisResult: Option[Schema.GoogleCloudDialogflowV2SentimentAnalysisResult] = None
	)
	
	case class GoogleCloudDialogflowV2WebhookResponse(
	  /** Optional. The text response message intended for the end-user. It is recommended to use `fulfillment_messages.text.text[0]` instead. When provided, Dialogflow uses this field to populate QueryResult.fulfillment_text sent to the integration or API caller. */
		fulfillmentText: Option[String] = None,
	  /** Optional. The rich response messages intended for the end-user. When provided, Dialogflow uses this field to populate QueryResult.fulfillment_messages sent to the integration or API caller. */
		fulfillmentMessages: Option[List[Schema.GoogleCloudDialogflowV2IntentMessage]] = None,
	  /** Optional. A custom field used to identify the webhook source. Arbitrary strings are supported. When provided, Dialogflow uses this field to populate QueryResult.webhook_source sent to the integration or API caller. */
		source: Option[String] = None,
	  /** Optional. This field can be used to pass custom data from your webhook to the integration or API caller. Arbitrary JSON objects are supported. When provided, Dialogflow uses this field to populate QueryResult.webhook_payload sent to the integration or API caller. This field is also used by the [Google Assistant integration](https://cloud.google.com/dialogflow/docs/integrations/aog) for rich response messages. See the format definition at [Google Assistant Dialogflow webhook format](https://developers.google.com/assistant/actions/build/json/dialogflow-webhook-json) */
		payload: Option[Map[String, JsValue]] = None,
	  /** Optional. The collection of output contexts that will overwrite currently active contexts for the session and reset their lifespans. When provided, Dialogflow uses this field to populate QueryResult.output_contexts sent to the integration or API caller. */
		outputContexts: Option[List[Schema.GoogleCloudDialogflowV2Context]] = None,
	  /** Optional. Invokes the supplied events. When this field is set, Dialogflow ignores the `fulfillment_text`, `fulfillment_messages`, and `payload` fields. */
		followupEventInput: Option[Schema.GoogleCloudDialogflowV2EventInput] = None,
	  /** Optional. Additional session entity types to replace or extend developer entity types with. The entity synonyms apply to all languages and persist for the session. Setting this data from a webhook overwrites the session entity types that have been set using `detectIntent`, `streamingDetectIntent` or SessionEntityType management methods. */
		sessionEntityTypes: Option[List[Schema.GoogleCloudDialogflowV2SessionEntityType]] = None
	)
	
	case class GoogleCloudDialogflowV2EventInput(
	  /** Required. The unique identifier of the event. */
		name: Option[String] = None,
	  /** The collection of parameters associated with the event. Depending on your protocol or client library language, this is a map, associative array, symbol table, dictionary, or JSON object composed of a collection of (MapKey, MapValue) pairs: &#42; MapKey type: string &#42; MapKey value: parameter name &#42; MapValue type: If parameter's entity type is a composite entity then use map, otherwise, depending on the parameter value type, it could be one of string, number, boolean, null, list or map. &#42; MapValue value: If parameter's entity type is a composite entity then use map from composite entity property names to property values, otherwise, use parameter value. */
		parameters: Option[Map[String, JsValue]] = None,
	  /** Required. The language of this query. See [Language Support](https://cloud.google.com/dialogflow/docs/reference/language) for a list of the currently supported language codes. Note that queries in the same session do not necessarily need to specify the same language. This field is ignored when used in the context of a WebhookResponse.followup_event_input field, because the language was already defined in the originating detect intent request. */
		languageCode: Option[String] = None
	)
	
	object GoogleCloudDialogflowV2SessionEntityType {
		enum EntityOverrideModeEnum extends Enum[EntityOverrideModeEnum] { case ENTITY_OVERRIDE_MODE_UNSPECIFIED, ENTITY_OVERRIDE_MODE_OVERRIDE, ENTITY_OVERRIDE_MODE_SUPPLEMENT }
	}
	case class GoogleCloudDialogflowV2SessionEntityType(
	  /** Required. The unique identifier of this session entity type. Format: `projects//agent/sessions//entityTypes/`, or `projects//agent/environments//users//sessions//entityTypes/`. If `Environment ID` is not specified, we assume default 'draft' environment. If `User ID` is not specified, we assume default '-' user. `` must be the display name of an existing entity type in the same agent that will be overridden or supplemented. */
		name: Option[String] = None,
	  /** Required. Indicates whether the additional data should override or supplement the custom entity type definition. */
		entityOverrideMode: Option[Schema.GoogleCloudDialogflowV2SessionEntityType.EntityOverrideModeEnum] = None,
	  /** Required. The collection of entities associated with this session entity type. */
		entities: Option[List[Schema.GoogleCloudDialogflowV2EntityTypeEntity]] = None
	)
	
	case class GoogleCloudDialogflowV2DeleteConversationDatasetOperationMetadata(
	
	)
	
	case class GoogleCloudDialogflowV2ImportConversationDataOperationResponse(
	  /** The resource name of the imported conversation dataset. Format: `projects//locations//conversationDatasets/` */
		conversationDataset: Option[String] = None,
	  /** Number of conversations imported successfully. */
		importCount: Option[Int] = None
	)
	
	case class GoogleCloudDialogflowV2ImportConversationDataOperationMetadata(
	  /** The resource name of the imported conversation dataset. Format: `projects//locations//conversationDatasets/` */
		conversationDataset: Option[String] = None,
	  /** Partial failures are failures that don't fail the whole long running operation, e.g. single files that couldn't be read. */
		partialFailures: Option[List[Schema.GoogleRpcStatus]] = None,
	  /** Timestamp when import conversation data request was created. The time is measured on server side. */
		createTime: Option[String] = None
	)
	
	case class GoogleCloudDialogflowV2InitializeEncryptionSpecMetadata(
	  /** Output only. The original request for initialization. */
		request: Option[Schema.GoogleCloudDialogflowV2InitializeEncryptionSpecRequest] = None
	)
	
	case class GoogleCloudDialogflowV2InitializeEncryptionSpecRequest(
	  /** Required. The encryption spec used for CMEK encryption. It is required that the kms key is in the same region as the endpoint. The same key will be used for all provisioned resources, if encryption is available. If the kms_key_name is left empty, no encryption will be enforced. */
		encryptionSpec: Option[Schema.GoogleCloudDialogflowV2EncryptionSpec] = None
	)
	
	case class GoogleCloudDialogflowV2EncryptionSpec(
	  /** Immutable. The resource name of the encryption key specification resource. Format: projects/{project}/locations/{location}/encryptionSpec */
		name: Option[String] = None,
	  /** Required. The name of customer-managed encryption key that is used to secure a resource and its sub-resources. If empty, the resource is secured by the default Google encryption key. Only the key in the same location as this resource is allowed to be used for encryption. Format: `projects/{project}/locations/{location}/keyRings/{keyRing}/cryptoKeys/{key}` */
		kmsKey: Option[String] = None
	)
	
	case class GoogleCloudDialogflowV2CreateConversationDatasetOperationMetadata(
	  /** The resource name of the conversation dataset that will be created. Format: `projects//locations//conversationDatasets/` */
		conversationDataset: Option[String] = None
	)
	
	object GoogleCloudDialogflowV2ConversationModel {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, CREATING, UNDEPLOYED, DEPLOYING, DEPLOYED, UNDEPLOYING, DELETING, FAILED, PENDING }
	}
	case class GoogleCloudDialogflowV2ConversationModel(
	  /** ConversationModel resource name. Format: `projects//conversationModels/` */
		name: Option[String] = None,
	  /** Required. The display name of the model. At most 64 bytes long. */
		displayName: Option[String] = None,
	  /** Output only. Creation time of this model. */
		createTime: Option[String] = None,
	  /** Required. Datasets used to create model. */
		datasets: Option[List[Schema.GoogleCloudDialogflowV2InputDataset]] = None,
	  /** Output only. State of the model. A model can only serve prediction requests after it gets deployed. */
		state: Option[Schema.GoogleCloudDialogflowV2ConversationModel.StateEnum] = None,
	  /** Language code for the conversation model. If not specified, the language is en-US. Language at ConversationModel should be set for all non en-us languages. This should be a [BCP-47](https://www.rfc-editor.org/rfc/bcp/bcp47.txt) language tag. Example: "en-US". */
		languageCode: Option[String] = None,
	  /** Metadata for article suggestion models. */
		articleSuggestionModelMetadata: Option[Schema.GoogleCloudDialogflowV2ArticleSuggestionModelMetadata] = None,
	  /** Metadata for smart reply models. */
		smartReplyModelMetadata: Option[Schema.GoogleCloudDialogflowV2SmartReplyModelMetadata] = None,
	  /** Output only. A read only boolean field reflecting Zone Separation status of the model. */
		satisfiesPzs: Option[Boolean] = None,
	  /** Output only. A read only boolean field reflecting Zone Isolation status of the model. */
		satisfiesPzi: Option[Boolean] = None
	)
	
	case class GoogleCloudDialogflowV2InputDataset(
	  /** Required. ConversationDataset resource name. Format: `projects//locations//conversationDatasets/` */
		dataset: Option[String] = None
	)
	
	object GoogleCloudDialogflowV2ArticleSuggestionModelMetadata {
		enum TrainingModelTypeEnum extends Enum[TrainingModelTypeEnum] { case MODEL_TYPE_UNSPECIFIED, SMART_REPLY_DUAL_ENCODER_MODEL, SMART_REPLY_BERT_MODEL }
	}
	case class GoogleCloudDialogflowV2ArticleSuggestionModelMetadata(
	  /** Optional. Type of the article suggestion model. If not provided, model_type is used. */
		trainingModelType: Option[Schema.GoogleCloudDialogflowV2ArticleSuggestionModelMetadata.TrainingModelTypeEnum] = None
	)
	
	object GoogleCloudDialogflowV2SmartReplyModelMetadata {
		enum TrainingModelTypeEnum extends Enum[TrainingModelTypeEnum] { case MODEL_TYPE_UNSPECIFIED, SMART_REPLY_DUAL_ENCODER_MODEL, SMART_REPLY_BERT_MODEL }
	}
	case class GoogleCloudDialogflowV2SmartReplyModelMetadata(
	  /** Optional. Type of the smart reply model. If not provided, model_type is used. */
		trainingModelType: Option[Schema.GoogleCloudDialogflowV2SmartReplyModelMetadata.TrainingModelTypeEnum] = None
	)
	
	object GoogleCloudDialogflowV2CreateConversationModelOperationMetadata {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, PENDING, SUCCEEDED, FAILED, CANCELLED, CANCELLING, TRAINING }
	}
	case class GoogleCloudDialogflowV2CreateConversationModelOperationMetadata(
	  /** The resource name of the conversation model. Format: `projects//conversationModels/` */
		conversationModel: Option[String] = None,
	  /** State of CreateConversationModel operation. */
		state: Option[Schema.GoogleCloudDialogflowV2CreateConversationModelOperationMetadata.StateEnum] = None,
	  /** Timestamp when the request to create conversation model is submitted. The time is measured on server side. */
		createTime: Option[String] = None
	)
	
	case class GoogleCloudDialogflowV2DeleteConversationModelOperationMetadata(
	  /** The resource name of the conversation model. Format: `projects//conversationModels/` */
		conversationModel: Option[String] = None,
	  /** Timestamp when delete conversation model request was created. The time is measured on server side. */
		createTime: Option[String] = None
	)
	
	case class GoogleCloudDialogflowV2DeployConversationModelOperationMetadata(
	  /** The resource name of the conversation model. Format: `projects//conversationModels/` */
		conversationModel: Option[String] = None,
	  /** Timestamp when request to deploy conversation model was submitted. The time is measured on server side. */
		createTime: Option[String] = None
	)
	
	case class GoogleCloudDialogflowV2UndeployConversationModelOperationMetadata(
	  /** The resource name of the conversation model. Format: `projects//conversationModels/` */
		conversationModel: Option[String] = None,
	  /** Timestamp when the request to undeploy conversation model was submitted. The time is measured on server side. */
		createTime: Option[String] = None
	)
	
	object GoogleCloudDialogflowV2CreateConversationModelEvaluationOperationMetadata {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, INITIALIZING, RUNNING, CANCELLED, SUCCEEDED, FAILED }
	}
	case class GoogleCloudDialogflowV2CreateConversationModelEvaluationOperationMetadata(
	  /** The resource name of the conversation model. Format: `projects//locations//conversationModels//evaluations/` */
		conversationModelEvaluation: Option[String] = None,
	  /** The resource name of the conversation model. Format: `projects//locations//conversationModels/` */
		conversationModel: Option[String] = None,
	  /** State of CreateConversationModel operation. */
		state: Option[Schema.GoogleCloudDialogflowV2CreateConversationModelEvaluationOperationMetadata.StateEnum] = None,
	  /** Timestamp when the request to create conversation model was submitted. The time is measured on server side. */
		createTime: Option[String] = None
	)
	
	case class GoogleCloudDialogflowV2beta1BatchUpdateEntityTypesResponse(
	  /** The collection of updated or created entity types. */
		entityTypes: Option[List[Schema.GoogleCloudDialogflowV2beta1EntityType]] = None
	)
	
	object GoogleCloudDialogflowV2beta1EntityType {
		enum KindEnum extends Enum[KindEnum] { case KIND_UNSPECIFIED, KIND_MAP, KIND_LIST, KIND_REGEXP }
		enum AutoExpansionModeEnum extends Enum[AutoExpansionModeEnum] { case AUTO_EXPANSION_MODE_UNSPECIFIED, AUTO_EXPANSION_MODE_DEFAULT }
	}
	case class GoogleCloudDialogflowV2beta1EntityType(
	  /** The unique identifier of the entity type. Required for EntityTypes.UpdateEntityType and EntityTypes.BatchUpdateEntityTypes methods. Supported formats: - `projects//agent/entityTypes/` - `projects//locations//agent/entityTypes/` */
		name: Option[String] = None,
	  /** Required. The name of the entity type. */
		displayName: Option[String] = None,
	  /** Required. Indicates the kind of entity type. */
		kind: Option[Schema.GoogleCloudDialogflowV2beta1EntityType.KindEnum] = None,
	  /** Optional. Indicates whether the entity type can be automatically expanded. */
		autoExpansionMode: Option[Schema.GoogleCloudDialogflowV2beta1EntityType.AutoExpansionModeEnum] = None,
	  /** Optional. The collection of entity entries associated with the entity type. */
		entities: Option[List[Schema.GoogleCloudDialogflowV2beta1EntityTypeEntity]] = None,
	  /** Optional. Enables fuzzy entity extraction during classification. */
		enableFuzzyExtraction: Option[Boolean] = None
	)
	
	case class GoogleCloudDialogflowV2beta1EntityTypeEntity(
	  /** Required. The primary value associated with this entity entry. For example, if the entity type is &#42;vegetable&#42;, the value could be &#42;scallions&#42;. For `KIND_MAP` entity types: &#42; A reference value to be used in place of synonyms. For `KIND_LIST` entity types: &#42; A string that can contain references to other entity types (with or without aliases). */
		value: Option[String] = None,
	  /** Required. A collection of value synonyms. For example, if the entity type is &#42;vegetable&#42;, and `value` is &#42;scallions&#42;, a synonym could be &#42;green onions&#42;. For `KIND_LIST` entity types: &#42; This collection must contain exactly one synonym equal to `value`. */
		synonyms: Option[List[String]] = None
	)
	
	case class GoogleCloudDialogflowV2beta1BatchUpdateIntentsResponse(
	  /** The collection of updated or created intents. */
		intents: Option[List[Schema.GoogleCloudDialogflowV2beta1Intent]] = None
	)
	
	object GoogleCloudDialogflowV2beta1Intent {
		enum WebhookStateEnum extends Enum[WebhookStateEnum] { case WEBHOOK_STATE_UNSPECIFIED, WEBHOOK_STATE_ENABLED, WEBHOOK_STATE_ENABLED_FOR_SLOT_FILLING }
		enum DefaultResponsePlatformsEnum extends Enum[DefaultResponsePlatformsEnum] { case PLATFORM_UNSPECIFIED, FACEBOOK, SLACK, TELEGRAM, KIK, SKYPE, LINE, VIBER, ACTIONS_ON_GOOGLE, TELEPHONY, GOOGLE_HANGOUTS }
	}
	case class GoogleCloudDialogflowV2beta1Intent(
	  /** Optional. The unique identifier of this intent. Required for Intents.UpdateIntent and Intents.BatchUpdateIntents methods. Supported formats: - `projects//agent/intents/` - `projects//locations//agent/intents/` */
		name: Option[String] = None,
	  /** Required. The name of this intent. */
		displayName: Option[String] = None,
	  /** Optional. Indicates whether webhooks are enabled for the intent. */
		webhookState: Option[Schema.GoogleCloudDialogflowV2beta1Intent.WebhookStateEnum] = None,
	  /** Optional. The priority of this intent. Higher numbers represent higher priorities. - If the supplied value is unspecified or 0, the service translates the value to 500,000, which corresponds to the `Normal` priority in the console. - If the supplied value is negative, the intent is ignored in runtime detect intent requests. */
		priority: Option[Int] = None,
	  /** Optional. Indicates whether this is a fallback intent. */
		isFallback: Option[Boolean] = None,
	  /** Optional. Indicates whether Machine Learning is enabled for the intent. Note: If `ml_enabled` setting is set to false, then this intent is not taken into account during inference in `ML ONLY` match mode. Also, auto-markup in the UI is turned off. DEPRECATED! Please use `ml_disabled` field instead. NOTE: If both `ml_enabled` and `ml_disabled` are either not set or false, then the default value is determined as follows: - Before April 15th, 2018 the default is: ml_enabled = false / ml_disabled = true. - After April 15th, 2018 the default is: ml_enabled = true / ml_disabled = false. */
		mlEnabled: Option[Boolean] = None,
	  /** Optional. Indicates whether Machine Learning is disabled for the intent. Note: If `ml_disabled` setting is set to true, then this intent is not taken into account during inference in `ML ONLY` match mode. Also, auto-markup in the UI is turned off. */
		mlDisabled: Option[Boolean] = None,
	  /** Optional. Indicates that a live agent should be brought in to handle the interaction with the user. In most cases, when you set this flag to true, you would also want to set end_interaction to true as well. Default is false. */
		liveAgentHandoff: Option[Boolean] = None,
	  /** Optional. Indicates that this intent ends an interaction. Some integrations (e.g., Actions on Google or Dialogflow phone gateway) use this information to close interaction with an end user. Default is false. */
		endInteraction: Option[Boolean] = None,
	  /** Optional. The list of context names required for this intent to be triggered. Formats: - `projects//agent/sessions/-/contexts/` - `projects//locations//agent/sessions/-/contexts/` */
		inputContextNames: Option[List[String]] = None,
	  /** Optional. The collection of event names that trigger the intent. If the collection of input contexts is not empty, all of the contexts must be present in the active user session for an event to trigger this intent. Event names are limited to 150 characters. */
		events: Option[List[String]] = None,
	  /** Optional. The collection of examples that the agent is trained on. */
		trainingPhrases: Option[List[Schema.GoogleCloudDialogflowV2beta1IntentTrainingPhrase]] = None,
	  /** Optional. The name of the action associated with the intent. Note: The action name must not contain whitespaces. */
		action: Option[String] = None,
	  /** Optional. The collection of contexts that are activated when the intent is matched. Context messages in this collection should not set the parameters field. Setting the `lifespan_count` to 0 will reset the context when the intent is matched. Format: `projects//agent/sessions/-/contexts/`. */
		outputContexts: Option[List[Schema.GoogleCloudDialogflowV2beta1Context]] = None,
	  /** Optional. Indicates whether to delete all contexts in the current session when this intent is matched. */
		resetContexts: Option[Boolean] = None,
	  /** Optional. The collection of parameters associated with the intent. */
		parameters: Option[List[Schema.GoogleCloudDialogflowV2beta1IntentParameter]] = None,
	  /** Optional. The collection of rich messages corresponding to the `Response` field in the Dialogflow console. */
		messages: Option[List[Schema.GoogleCloudDialogflowV2beta1IntentMessage]] = None,
	  /** Optional. The list of platforms for which the first responses will be copied from the messages in PLATFORM_UNSPECIFIED (i.e. default platform). */
		defaultResponsePlatforms: Option[List[Schema.GoogleCloudDialogflowV2beta1Intent.DefaultResponsePlatformsEnum]] = None,
	  /** Output only. The unique identifier of the root intent in the chain of followup intents. It identifies the correct followup intents chain for this intent. Format: `projects//agent/intents/`. */
		rootFollowupIntentName: Option[String] = None,
	  /** Optional. The unique identifier of the parent intent in the chain of followup intents. You can set this field when creating an intent, for example with CreateIntent or BatchUpdateIntents, in order to make this intent a followup intent. It identifies the parent followup intent. Format: `projects//agent/intents/`. */
		parentFollowupIntentName: Option[String] = None,
	  /** Output only. Information about all followup intents that have this intent as a direct or indirect parent. We populate this field only in the output. */
		followupIntentInfo: Option[List[Schema.GoogleCloudDialogflowV2beta1IntentFollowupIntentInfo]] = None
	)
	
	object GoogleCloudDialogflowV2beta1IntentTrainingPhrase {
		enum TypeEnum extends Enum[TypeEnum] { case TYPE_UNSPECIFIED, EXAMPLE, TEMPLATE }
	}
	case class GoogleCloudDialogflowV2beta1IntentTrainingPhrase(
	  /** Output only. The unique identifier of this training phrase. */
		name: Option[String] = None,
	  /** Required. The type of the training phrase. */
		`type`: Option[Schema.GoogleCloudDialogflowV2beta1IntentTrainingPhrase.TypeEnum] = None,
	  /** Required. The ordered list of training phrase parts. The parts are concatenated in order to form the training phrase. Note: The API does not automatically annotate training phrases like the Dialogflow Console does. Note: Do not forget to include whitespace at part boundaries, so the training phrase is well formatted when the parts are concatenated. If the training phrase does not need to be annotated with parameters, you just need a single part with only the Part.text field set. If you want to annotate the training phrase, you must create multiple parts, where the fields of each part are populated in one of two ways: - `Part.text` is set to a part of the phrase that has no parameters. - `Part.text` is set to a part of the phrase that you want to annotate, and the `entity_type`, `alias`, and `user_defined` fields are all set. */
		parts: Option[List[Schema.GoogleCloudDialogflowV2beta1IntentTrainingPhrasePart]] = None,
	  /** Optional. Indicates how many times this example was added to the intent. Each time a developer adds an existing sample by editing an intent or training, this counter is increased. */
		timesAddedCount: Option[Int] = None
	)
	
	case class GoogleCloudDialogflowV2beta1IntentTrainingPhrasePart(
	  /** Required. The text for this part. */
		text: Option[String] = None,
	  /** Optional. The entity type name prefixed with `@`. This field is required for annotated parts of the training phrase. */
		entityType: Option[String] = None,
	  /** Optional. The parameter name for the value extracted from the annotated part of the example. This field is required for annotated parts of the training phrase. */
		alias: Option[String] = None,
	  /** Optional. Indicates whether the text was manually annotated. This field is set to true when the Dialogflow Console is used to manually annotate the part. When creating an annotated part with the API, you must set this to true. */
		userDefined: Option[Boolean] = None
	)
	
	case class GoogleCloudDialogflowV2beta1Context(
	  /** Required. The unique identifier of the context. Supported formats: - `projects//agent/sessions//contexts/`, - `projects//locations//agent/sessions//contexts/`, - `projects//agent/environments//users//sessions//contexts/`, - `projects//locations//agent/environments//users//sessions//contexts/`, The `Context ID` is always converted to lowercase, may only contain characters in `a-zA-Z0-9_-%` and may be at most 250 bytes long. If `Environment ID` is not specified, we assume default 'draft' environment. If `User ID` is not specified, we assume default '-' user. The following context names are reserved for internal use by Dialogflow. You should not use these contexts or create contexts with these names: &#42; `__system_counters__` &#42; `&#42;_id_dialog_context` &#42; `&#42;_dialog_params_size` */
		name: Option[String] = None,
	  /** Optional. The number of conversational query requests after which the context expires. The default is `0`. If set to `0`, the context expires immediately. Contexts expire automatically after 20 minutes if there are no matching queries. */
		lifespanCount: Option[Int] = None,
	  /** Optional. The collection of parameters associated with this context. Depending on your protocol or client library language, this is a map, associative array, symbol table, dictionary, or JSON object composed of a collection of (MapKey, MapValue) pairs: &#42; MapKey type: string &#42; MapKey value: parameter name &#42; MapValue type: If parameter's entity type is a composite entity then use map, otherwise, depending on the parameter value type, it could be one of string, number, boolean, null, list or map. &#42; MapValue value: If parameter's entity type is a composite entity then use map from composite entity property names to property values, otherwise, use parameter value. */
		parameters: Option[Map[String, JsValue]] = None
	)
	
	case class GoogleCloudDialogflowV2beta1IntentParameter(
	  /** The unique identifier of this parameter. */
		name: Option[String] = None,
	  /** Required. The name of the parameter. */
		displayName: Option[String] = None,
	  /** Optional. The definition of the parameter value. It can be: - a constant string, - a parameter value defined as `$parameter_name`, - an original parameter value defined as `$parameter_name.original`, - a parameter value from some context defined as `#context_name.parameter_name`. */
		value: Option[String] = None,
	  /** Optional. The default value to use when the `value` yields an empty result. Default values can be extracted from contexts by using the following syntax: `#context_name.parameter_name`. */
		defaultValue: Option[String] = None,
	  /** Optional. The name of the entity type, prefixed with `@`, that describes values of the parameter. If the parameter is required, this must be provided. */
		entityTypeDisplayName: Option[String] = None,
	  /** Optional. Indicates whether the parameter is required. That is, whether the intent cannot be completed without collecting the parameter value. */
		mandatory: Option[Boolean] = None,
	  /** Optional. The collection of prompts that the agent can present to the user in order to collect a value for the parameter. */
		prompts: Option[List[String]] = None,
	  /** Optional. Indicates whether the parameter represents a list of values. */
		isList: Option[Boolean] = None
	)
	
	object GoogleCloudDialogflowV2beta1IntentMessage {
		enum PlatformEnum extends Enum[PlatformEnum] { case PLATFORM_UNSPECIFIED, FACEBOOK, SLACK, TELEGRAM, KIK, SKYPE, LINE, VIBER, ACTIONS_ON_GOOGLE, TELEPHONY, GOOGLE_HANGOUTS }
	}
	case class GoogleCloudDialogflowV2beta1IntentMessage(
	  /** Returns a text response. */
		text: Option[Schema.GoogleCloudDialogflowV2beta1IntentMessageText] = None,
	  /** Displays an image. */
		image: Option[Schema.GoogleCloudDialogflowV2beta1IntentMessageImage] = None,
	  /** Displays quick replies. */
		quickReplies: Option[Schema.GoogleCloudDialogflowV2beta1IntentMessageQuickReplies] = None,
	  /** Displays a card. */
		card: Option[Schema.GoogleCloudDialogflowV2beta1IntentMessageCard] = None,
	  /** A custom platform-specific response. */
		payload: Option[Map[String, JsValue]] = None,
	  /** Returns a voice or text-only response for Actions on Google. */
		simpleResponses: Option[Schema.GoogleCloudDialogflowV2beta1IntentMessageSimpleResponses] = None,
	  /** Displays a basic card for Actions on Google. */
		basicCard: Option[Schema.GoogleCloudDialogflowV2beta1IntentMessageBasicCard] = None,
	  /** Displays suggestion chips for Actions on Google. */
		suggestions: Option[Schema.GoogleCloudDialogflowV2beta1IntentMessageSuggestions] = None,
	  /** Displays a link out suggestion chip for Actions on Google. */
		linkOutSuggestion: Option[Schema.GoogleCloudDialogflowV2beta1IntentMessageLinkOutSuggestion] = None,
	  /** Displays a list card for Actions on Google. */
		listSelect: Option[Schema.GoogleCloudDialogflowV2beta1IntentMessageListSelect] = None,
	  /** Displays a carousel card for Actions on Google. */
		carouselSelect: Option[Schema.GoogleCloudDialogflowV2beta1IntentMessageCarouselSelect] = None,
	  /** Plays audio from a file in Telephony Gateway. */
		telephonyPlayAudio: Option[Schema.GoogleCloudDialogflowV2beta1IntentMessageTelephonyPlayAudio] = None,
	  /** Synthesizes speech in Telephony Gateway. */
		telephonySynthesizeSpeech: Option[Schema.GoogleCloudDialogflowV2beta1IntentMessageTelephonySynthesizeSpeech] = None,
	  /** Transfers the call in Telephony Gateway. */
		telephonyTransferCall: Option[Schema.GoogleCloudDialogflowV2beta1IntentMessageTelephonyTransferCall] = None,
	  /** Rich Business Messaging (RBM) text response. RBM allows businesses to send enriched and branded versions of SMS. See https://jibe.google.com/business-messaging. */
		rbmText: Option[Schema.GoogleCloudDialogflowV2beta1IntentMessageRbmText] = None,
	  /** Standalone Rich Business Messaging (RBM) rich card response. */
		rbmStandaloneRichCard: Option[Schema.GoogleCloudDialogflowV2beta1IntentMessageRbmStandaloneCard] = None,
	  /** Rich Business Messaging (RBM) carousel rich card response. */
		rbmCarouselRichCard: Option[Schema.GoogleCloudDialogflowV2beta1IntentMessageRbmCarouselCard] = None,
	  /** Browse carousel card for Actions on Google. */
		browseCarouselCard: Option[Schema.GoogleCloudDialogflowV2beta1IntentMessageBrowseCarouselCard] = None,
	  /** Table card for Actions on Google. */
		tableCard: Option[Schema.GoogleCloudDialogflowV2beta1IntentMessageTableCard] = None,
	  /** The media content card for Actions on Google. */
		mediaContent: Option[Schema.GoogleCloudDialogflowV2beta1IntentMessageMediaContent] = None,
	  /** Optional. The platform that this message is intended for. */
		platform: Option[Schema.GoogleCloudDialogflowV2beta1IntentMessage.PlatformEnum] = None
	)
	
	case class GoogleCloudDialogflowV2beta1IntentMessageText(
	  /** Optional. The collection of the agent's responses. */
		text: Option[List[String]] = None
	)
	
	case class GoogleCloudDialogflowV2beta1IntentMessageImage(
	  /** Optional. The public URI to an image file. */
		imageUri: Option[String] = None,
	  /** A text description of the image to be used for accessibility, e.g., screen readers. Required if image_uri is set for CarouselSelect. */
		accessibilityText: Option[String] = None
	)
	
	case class GoogleCloudDialogflowV2beta1IntentMessageQuickReplies(
	  /** Optional. The title of the collection of quick replies. */
		title: Option[String] = None,
	  /** Optional. The collection of quick replies. */
		quickReplies: Option[List[String]] = None
	)
	
	case class GoogleCloudDialogflowV2beta1IntentMessageCard(
	  /** Optional. The title of the card. */
		title: Option[String] = None,
	  /** Optional. The subtitle of the card. */
		subtitle: Option[String] = None,
	  /** Optional. The public URI to an image file for the card. */
		imageUri: Option[String] = None,
	  /** Optional. The collection of card buttons. */
		buttons: Option[List[Schema.GoogleCloudDialogflowV2beta1IntentMessageCardButton]] = None
	)
	
	case class GoogleCloudDialogflowV2beta1IntentMessageCardButton(
	  /** Optional. The text to show on the button. */
		text: Option[String] = None,
	  /** Optional. The text to send back to the Dialogflow API or a URI to open. */
		postback: Option[String] = None
	)
	
	case class GoogleCloudDialogflowV2beta1IntentMessageSimpleResponses(
	  /** Required. The list of simple responses. */
		simpleResponses: Option[List[Schema.GoogleCloudDialogflowV2beta1IntentMessageSimpleResponse]] = None
	)
	
	case class GoogleCloudDialogflowV2beta1IntentMessageSimpleResponse(
	  /** One of text_to_speech or ssml must be provided. The plain text of the speech output. Mutually exclusive with ssml. */
		textToSpeech: Option[String] = None,
	  /** One of text_to_speech or ssml must be provided. Structured spoken response to the user in the SSML format. Mutually exclusive with text_to_speech. */
		ssml: Option[String] = None,
	  /** Optional. The text to display. */
		displayText: Option[String] = None
	)
	
	case class GoogleCloudDialogflowV2beta1IntentMessageBasicCard(
	  /** Optional. The title of the card. */
		title: Option[String] = None,
	  /** Optional. The subtitle of the card. */
		subtitle: Option[String] = None,
	  /** Required, unless image is present. The body text of the card. */
		formattedText: Option[String] = None,
	  /** Optional. The image for the card. */
		image: Option[Schema.GoogleCloudDialogflowV2beta1IntentMessageImage] = None,
	  /** Optional. The collection of card buttons. */
		buttons: Option[List[Schema.GoogleCloudDialogflowV2beta1IntentMessageBasicCardButton]] = None
	)
	
	case class GoogleCloudDialogflowV2beta1IntentMessageBasicCardButton(
	  /** Required. The title of the button. */
		title: Option[String] = None,
	  /** Required. Action to take when a user taps on the button. */
		openUriAction: Option[Schema.GoogleCloudDialogflowV2beta1IntentMessageBasicCardButtonOpenUriAction] = None
	)
	
	case class GoogleCloudDialogflowV2beta1IntentMessageBasicCardButtonOpenUriAction(
	  /** Required. The HTTP or HTTPS scheme URI. */
		uri: Option[String] = None
	)
	
	case class GoogleCloudDialogflowV2beta1IntentMessageSuggestions(
	  /** Required. The list of suggested replies. */
		suggestions: Option[List[Schema.GoogleCloudDialogflowV2beta1IntentMessageSuggestion]] = None
	)
	
	case class GoogleCloudDialogflowV2beta1IntentMessageSuggestion(
	  /** Required. The text shown the in the suggestion chip. */
		title: Option[String] = None
	)
	
	case class GoogleCloudDialogflowV2beta1IntentMessageLinkOutSuggestion(
	  /** Required. The name of the app or site this chip is linking to. */
		destinationName: Option[String] = None,
	  /** Required. The URI of the app or site to open when the user taps the suggestion chip. */
		uri: Option[String] = None
	)
	
	case class GoogleCloudDialogflowV2beta1IntentMessageListSelect(
	  /** Optional. The overall title of the list. */
		title: Option[String] = None,
	  /** Required. List items. */
		items: Option[List[Schema.GoogleCloudDialogflowV2beta1IntentMessageListSelectItem]] = None,
	  /** Optional. Subtitle of the list. */
		subtitle: Option[String] = None
	)
	
	case class GoogleCloudDialogflowV2beta1IntentMessageListSelectItem(
	  /** Required. Additional information about this option. */
		info: Option[Schema.GoogleCloudDialogflowV2beta1IntentMessageSelectItemInfo] = None,
	  /** Required. The title of the list item. */
		title: Option[String] = None,
	  /** Optional. The main text describing the item. */
		description: Option[String] = None,
	  /** Optional. The image to display. */
		image: Option[Schema.GoogleCloudDialogflowV2beta1IntentMessageImage] = None
	)
	
	case class GoogleCloudDialogflowV2beta1IntentMessageSelectItemInfo(
	  /** Required. A unique key that will be sent back to the agent if this response is given. */
		key: Option[String] = None,
	  /** Optional. A list of synonyms that can also be used to trigger this item in dialog. */
		synonyms: Option[List[String]] = None
	)
	
	case class GoogleCloudDialogflowV2beta1IntentMessageCarouselSelect(
	  /** Required. Carousel items. */
		items: Option[List[Schema.GoogleCloudDialogflowV2beta1IntentMessageCarouselSelectItem]] = None
	)
	
	case class GoogleCloudDialogflowV2beta1IntentMessageCarouselSelectItem(
	  /** Required. Additional info about the option item. */
		info: Option[Schema.GoogleCloudDialogflowV2beta1IntentMessageSelectItemInfo] = None,
	  /** Required. Title of the carousel item. */
		title: Option[String] = None,
	  /** Optional. The body text of the card. */
		description: Option[String] = None,
	  /** Optional. The image to display. */
		image: Option[Schema.GoogleCloudDialogflowV2beta1IntentMessageImage] = None
	)
	
	case class GoogleCloudDialogflowV2beta1IntentMessageTelephonyPlayAudio(
	  /** Required. URI to a Google Cloud Storage object containing the audio to play, e.g., "gs://bucket/object". The object must contain a single channel (mono) of linear PCM audio (2 bytes / sample) at 8kHz. This object must be readable by the `service-@gcp-sa-dialogflow.iam.gserviceaccount.com` service account where is the number of the Telephony Gateway project (usually the same as the Dialogflow agent project). If the Google Cloud Storage bucket is in the Telephony Gateway project, this permission is added by default when enabling the Dialogflow V2 API. For audio from other sources, consider using the `TelephonySynthesizeSpeech` message with SSML. */
		audioUri: Option[String] = None
	)
	
	case class GoogleCloudDialogflowV2beta1IntentMessageTelephonySynthesizeSpeech(
	  /** The raw text to be synthesized. */
		text: Option[String] = None,
	  /** The SSML to be synthesized. For more information, see [SSML](https://developers.google.com/actions/reference/ssml). */
		ssml: Option[String] = None
	)
	
	case class GoogleCloudDialogflowV2beta1IntentMessageTelephonyTransferCall(
	  /** Required. The phone number to transfer the call to in [E.164 format](https://en.wikipedia.org/wiki/E.164). We currently only allow transferring to US numbers (+1xxxyyyzzzz). */
		phoneNumber: Option[String] = None
	)
	
	case class GoogleCloudDialogflowV2beta1IntentMessageRbmText(
	  /** Required. Text sent and displayed to the user. */
		text: Option[String] = None,
	  /** Optional. One or more suggestions to show to the user. */
		rbmSuggestion: Option[List[Schema.GoogleCloudDialogflowV2beta1IntentMessageRbmSuggestion]] = None
	)
	
	case class GoogleCloudDialogflowV2beta1IntentMessageRbmSuggestion(
	  /** Predefined replies for user to select instead of typing */
		reply: Option[Schema.GoogleCloudDialogflowV2beta1IntentMessageRbmSuggestedReply] = None,
	  /** Predefined client side actions that user can choose */
		action: Option[Schema.GoogleCloudDialogflowV2beta1IntentMessageRbmSuggestedAction] = None
	)
	
	case class GoogleCloudDialogflowV2beta1IntentMessageRbmSuggestedReply(
	  /** Suggested reply text. */
		text: Option[String] = None,
	  /** Opaque payload that the Dialogflow receives in a user event when the user taps the suggested reply. This data will be also forwarded to webhook to allow performing custom business logic. */
		postbackData: Option[String] = None
	)
	
	case class GoogleCloudDialogflowV2beta1IntentMessageRbmSuggestedAction(
	  /** Text to display alongside the action. */
		text: Option[String] = None,
	  /** Opaque payload that the Dialogflow receives in a user event when the user taps the suggested action. This data will be also forwarded to webhook to allow performing custom business logic. */
		postbackData: Option[String] = None,
	  /** Suggested client side action: Dial a phone number */
		dial: Option[Schema.GoogleCloudDialogflowV2beta1IntentMessageRbmSuggestedActionRbmSuggestedActionDial] = None,
	  /** Suggested client side action: Open a URI on device */
		openUrl: Option[Schema.GoogleCloudDialogflowV2beta1IntentMessageRbmSuggestedActionRbmSuggestedActionOpenUri] = None,
	  /** Suggested client side action: Share user location */
		shareLocation: Option[Schema.GoogleCloudDialogflowV2beta1IntentMessageRbmSuggestedActionRbmSuggestedActionShareLocation] = None
	)
	
	case class GoogleCloudDialogflowV2beta1IntentMessageRbmSuggestedActionRbmSuggestedActionDial(
	  /** Required. The phone number to fill in the default dialer app. This field should be in [E.164](https://en.wikipedia.org/wiki/E.164) format. An example of a correctly formatted phone number: +15556767888. */
		phoneNumber: Option[String] = None
	)
	
	case class GoogleCloudDialogflowV2beta1IntentMessageRbmSuggestedActionRbmSuggestedActionOpenUri(
	  /** Required. The uri to open on the user device */
		uri: Option[String] = None
	)
	
	case class GoogleCloudDialogflowV2beta1IntentMessageRbmSuggestedActionRbmSuggestedActionShareLocation(
	
	)
	
	object GoogleCloudDialogflowV2beta1IntentMessageRbmStandaloneCard {
		enum CardOrientationEnum extends Enum[CardOrientationEnum] { case CARD_ORIENTATION_UNSPECIFIED, HORIZONTAL, VERTICAL }
		enum ThumbnailImageAlignmentEnum extends Enum[ThumbnailImageAlignmentEnum] { case THUMBNAIL_IMAGE_ALIGNMENT_UNSPECIFIED, LEFT, RIGHT }
	}
	case class GoogleCloudDialogflowV2beta1IntentMessageRbmStandaloneCard(
	  /** Required. Orientation of the card. */
		cardOrientation: Option[Schema.GoogleCloudDialogflowV2beta1IntentMessageRbmStandaloneCard.CardOrientationEnum] = None,
	  /** Required if orientation is horizontal. Image preview alignment for standalone cards with horizontal layout. */
		thumbnailImageAlignment: Option[Schema.GoogleCloudDialogflowV2beta1IntentMessageRbmStandaloneCard.ThumbnailImageAlignmentEnum] = None,
	  /** Required. Card content. */
		cardContent: Option[Schema.GoogleCloudDialogflowV2beta1IntentMessageRbmCardContent] = None
	)
	
	case class GoogleCloudDialogflowV2beta1IntentMessageRbmCardContent(
	  /** Optional. Title of the card (at most 200 bytes). At least one of the title, description or media must be set. */
		title: Option[String] = None,
	  /** Optional. Description of the card (at most 2000 bytes). At least one of the title, description or media must be set. */
		description: Option[String] = None,
	  /** Optional. However at least one of the title, description or media must be set. Media (image, GIF or a video) to include in the card. */
		media: Option[Schema.GoogleCloudDialogflowV2beta1IntentMessageRbmCardContentRbmMedia] = None,
	  /** Optional. List of suggestions to include in the card. */
		suggestions: Option[List[Schema.GoogleCloudDialogflowV2beta1IntentMessageRbmSuggestion]] = None
	)
	
	object GoogleCloudDialogflowV2beta1IntentMessageRbmCardContentRbmMedia {
		enum HeightEnum extends Enum[HeightEnum] { case HEIGHT_UNSPECIFIED, SHORT, MEDIUM, TALL }
	}
	case class GoogleCloudDialogflowV2beta1IntentMessageRbmCardContentRbmMedia(
	  /** Required. Publicly reachable URI of the file. The RBM platform determines the MIME type of the file from the content-type field in the HTTP headers when the platform fetches the file. The content-type field must be present and accurate in the HTTP response from the URL. */
		fileUri: Option[String] = None,
	  /** Optional. Publicly reachable URI of the thumbnail.If you don't provide a thumbnail URI, the RBM platform displays a blank placeholder thumbnail until the user's device downloads the file. Depending on the user's setting, the file may not download automatically and may require the user to tap a download button. */
		thumbnailUri: Option[String] = None,
	  /** Required for cards with vertical orientation. The height of the media within a rich card with a vertical layout. For a standalone card with horizontal layout, height is not customizable, and this field is ignored. */
		height: Option[Schema.GoogleCloudDialogflowV2beta1IntentMessageRbmCardContentRbmMedia.HeightEnum] = None
	)
	
	object GoogleCloudDialogflowV2beta1IntentMessageRbmCarouselCard {
		enum CardWidthEnum extends Enum[CardWidthEnum] { case CARD_WIDTH_UNSPECIFIED, SMALL, MEDIUM }
	}
	case class GoogleCloudDialogflowV2beta1IntentMessageRbmCarouselCard(
	  /** Required. The width of the cards in the carousel. */
		cardWidth: Option[Schema.GoogleCloudDialogflowV2beta1IntentMessageRbmCarouselCard.CardWidthEnum] = None,
	  /** Required. The cards in the carousel. A carousel must have at least 2 cards and at most 10. */
		cardContents: Option[List[Schema.GoogleCloudDialogflowV2beta1IntentMessageRbmCardContent]] = None
	)
	
	object GoogleCloudDialogflowV2beta1IntentMessageBrowseCarouselCard {
		enum ImageDisplayOptionsEnum extends Enum[ImageDisplayOptionsEnum] { case IMAGE_DISPLAY_OPTIONS_UNSPECIFIED, GRAY, WHITE, CROPPED, BLURRED_BACKGROUND }
	}
	case class GoogleCloudDialogflowV2beta1IntentMessageBrowseCarouselCard(
	  /** Required. List of items in the Browse Carousel Card. Minimum of two items, maximum of ten. */
		items: Option[List[Schema.GoogleCloudDialogflowV2beta1IntentMessageBrowseCarouselCardBrowseCarouselCardItem]] = None,
	  /** Optional. Settings for displaying the image. Applies to every image in items. */
		imageDisplayOptions: Option[Schema.GoogleCloudDialogflowV2beta1IntentMessageBrowseCarouselCard.ImageDisplayOptionsEnum] = None
	)
	
	case class GoogleCloudDialogflowV2beta1IntentMessageBrowseCarouselCardBrowseCarouselCardItem(
	  /** Required. Action to present to the user. */
		openUriAction: Option[Schema.GoogleCloudDialogflowV2beta1IntentMessageBrowseCarouselCardBrowseCarouselCardItemOpenUrlAction] = None,
	  /** Required. Title of the carousel item. Maximum of two lines of text. */
		title: Option[String] = None,
	  /** Optional. Description of the carousel item. Maximum of four lines of text. */
		description: Option[String] = None,
	  /** Optional. Hero image for the carousel item. */
		image: Option[Schema.GoogleCloudDialogflowV2beta1IntentMessageImage] = None,
	  /** Optional. Text that appears at the bottom of the Browse Carousel Card. Maximum of one line of text. */
		footer: Option[String] = None
	)
	
	object GoogleCloudDialogflowV2beta1IntentMessageBrowseCarouselCardBrowseCarouselCardItemOpenUrlAction {
		enum UrlTypeHintEnum extends Enum[UrlTypeHintEnum] { case URL_TYPE_HINT_UNSPECIFIED, AMP_ACTION, AMP_CONTENT }
	}
	case class GoogleCloudDialogflowV2beta1IntentMessageBrowseCarouselCardBrowseCarouselCardItemOpenUrlAction(
	  /** Required. URL */
		url: Option[String] = None,
	  /** Optional. Specifies the type of viewer that is used when opening the URL. Defaults to opening via web browser. */
		urlTypeHint: Option[Schema.GoogleCloudDialogflowV2beta1IntentMessageBrowseCarouselCardBrowseCarouselCardItemOpenUrlAction.UrlTypeHintEnum] = None
	)
	
	case class GoogleCloudDialogflowV2beta1IntentMessageTableCard(
	  /** Required. Title of the card. */
		title: Option[String] = None,
	  /** Optional. Subtitle to the title. */
		subtitle: Option[String] = None,
	  /** Optional. Image which should be displayed on the card. */
		image: Option[Schema.GoogleCloudDialogflowV2beta1IntentMessageImage] = None,
	  /** Optional. Display properties for the columns in this table. */
		columnProperties: Option[List[Schema.GoogleCloudDialogflowV2beta1IntentMessageColumnProperties]] = None,
	  /** Optional. Rows in this table of data. */
		rows: Option[List[Schema.GoogleCloudDialogflowV2beta1IntentMessageTableCardRow]] = None,
	  /** Optional. List of buttons for the card. */
		buttons: Option[List[Schema.GoogleCloudDialogflowV2beta1IntentMessageBasicCardButton]] = None
	)
	
	object GoogleCloudDialogflowV2beta1IntentMessageColumnProperties {
		enum HorizontalAlignmentEnum extends Enum[HorizontalAlignmentEnum] { case HORIZONTAL_ALIGNMENT_UNSPECIFIED, LEADING, CENTER, TRAILING }
	}
	case class GoogleCloudDialogflowV2beta1IntentMessageColumnProperties(
	  /** Required. Column heading. */
		header: Option[String] = None,
	  /** Optional. Defines text alignment for all cells in this column. */
		horizontalAlignment: Option[Schema.GoogleCloudDialogflowV2beta1IntentMessageColumnProperties.HorizontalAlignmentEnum] = None
	)
	
	case class GoogleCloudDialogflowV2beta1IntentMessageTableCardRow(
	  /** Optional. List of cells that make up this row. */
		cells: Option[List[Schema.GoogleCloudDialogflowV2beta1IntentMessageTableCardCell]] = None,
	  /** Optional. Whether to add a visual divider after this row. */
		dividerAfter: Option[Boolean] = None
	)
	
	case class GoogleCloudDialogflowV2beta1IntentMessageTableCardCell(
	  /** Required. Text in this cell. */
		text: Option[String] = None
	)
	
	object GoogleCloudDialogflowV2beta1IntentMessageMediaContent {
		enum MediaTypeEnum extends Enum[MediaTypeEnum] { case RESPONSE_MEDIA_TYPE_UNSPECIFIED, AUDIO }
	}
	case class GoogleCloudDialogflowV2beta1IntentMessageMediaContent(
	  /** Optional. What type of media is the content (ie "audio"). */
		mediaType: Option[Schema.GoogleCloudDialogflowV2beta1IntentMessageMediaContent.MediaTypeEnum] = None,
	  /** Required. List of media objects. */
		mediaObjects: Option[List[Schema.GoogleCloudDialogflowV2beta1IntentMessageMediaContentResponseMediaObject]] = None
	)
	
	case class GoogleCloudDialogflowV2beta1IntentMessageMediaContentResponseMediaObject(
	  /** Required. Name of media card. */
		name: Option[String] = None,
	  /** Optional. Description of media card. */
		description: Option[String] = None,
	  /** Optional. Image to display above media content. */
		largeImage: Option[Schema.GoogleCloudDialogflowV2beta1IntentMessageImage] = None,
	  /** Optional. Icon to display above media content. */
		icon: Option[Schema.GoogleCloudDialogflowV2beta1IntentMessageImage] = None,
	  /** Required. Url where the media is stored. */
		contentUrl: Option[String] = None
	)
	
	case class GoogleCloudDialogflowV2beta1IntentFollowupIntentInfo(
	  /** The unique identifier of the followup intent. Format: `projects//agent/intents/`. */
		followupIntentName: Option[String] = None,
	  /** The unique identifier of the followup intent's parent. Format: `projects//agent/intents/`. */
		parentFollowupIntentName: Option[String] = None
	)
	
	object GoogleCloudDialogflowV2beta1ClearSuggestionFeatureConfigOperationMetadata {
		enum ParticipantRoleEnum extends Enum[ParticipantRoleEnum] { case ROLE_UNSPECIFIED, HUMAN_AGENT, AUTOMATED_AGENT, END_USER }
		enum SuggestionFeatureTypeEnum extends Enum[SuggestionFeatureTypeEnum] { case TYPE_UNSPECIFIED, ARTICLE_SUGGESTION, FAQ, SMART_REPLY, DIALOGFLOW_ASSIST, CONVERSATION_SUMMARIZATION, KNOWLEDGE_SEARCH, KNOWLEDGE_ASSIST }
	}
	case class GoogleCloudDialogflowV2beta1ClearSuggestionFeatureConfigOperationMetadata(
	  /** The resource name of the conversation profile. Format: `projects//locations//conversationProfiles/` */
		conversationProfile: Option[String] = None,
	  /** Required. The participant role to remove the suggestion feature config. Only HUMAN_AGENT or END_USER can be used. */
		participantRole: Option[Schema.GoogleCloudDialogflowV2beta1ClearSuggestionFeatureConfigOperationMetadata.ParticipantRoleEnum] = None,
	  /** Required. The type of the suggestion feature to remove. */
		suggestionFeatureType: Option[Schema.GoogleCloudDialogflowV2beta1ClearSuggestionFeatureConfigOperationMetadata.SuggestionFeatureTypeEnum] = None,
	  /** Timestamp whe the request was created. The time is measured on server side. */
		createTime: Option[String] = None
	)
	
	object GoogleCloudDialogflowV2beta1ConversationEvent {
		enum TypeEnum extends Enum[TypeEnum] { case TYPE_UNSPECIFIED, CONVERSATION_STARTED, CONVERSATION_FINISHED, HUMAN_INTERVENTION_NEEDED, NEW_MESSAGE, UNRECOVERABLE_ERROR }
	}
	case class GoogleCloudDialogflowV2beta1ConversationEvent(
	  /** Required. The unique identifier of the conversation this notification refers to. Format: `projects//conversations/`. */
		conversation: Option[String] = None,
	  /** Required. The type of the event that this notification refers to. */
		`type`: Option[Schema.GoogleCloudDialogflowV2beta1ConversationEvent.TypeEnum] = None,
	  /** Optional. More detailed information about an error. Only set for type UNRECOVERABLE_ERROR_IN_PHONE_CALL. */
		errorStatus: Option[Schema.GoogleRpcStatus] = None,
	  /** Payload of NEW_MESSAGE event. */
		newMessagePayload: Option[Schema.GoogleCloudDialogflowV2beta1Message] = None
	)
	
	object GoogleCloudDialogflowV2beta1Message {
		enum ParticipantRoleEnum extends Enum[ParticipantRoleEnum] { case ROLE_UNSPECIFIED, HUMAN_AGENT, AUTOMATED_AGENT, END_USER }
	}
	case class GoogleCloudDialogflowV2beta1Message(
	  /** Optional. The unique identifier of the message. Format: `projects//locations//conversations//messages/`. */
		name: Option[String] = None,
	  /** Required. The message content. */
		content: Option[String] = None,
	  /** Optional. Automated agent responses. */
		responseMessages: Option[List[Schema.GoogleCloudDialogflowV2beta1ResponseMessage]] = None,
	  /** Optional. The message language. This should be a [BCP-47](https://www.rfc-editor.org/rfc/bcp/bcp47.txt) language tag. Example: "en-US". */
		languageCode: Option[String] = None,
	  /** Output only. The participant that sends this message. */
		participant: Option[String] = None,
	  /** Output only. The role of the participant. */
		participantRole: Option[Schema.GoogleCloudDialogflowV2beta1Message.ParticipantRoleEnum] = None,
	  /** Output only. The time when the message was created in Contact Center AI. */
		createTime: Option[String] = None,
	  /** Optional. The time when the message was sent. */
		sendTime: Option[String] = None,
	  /** Output only. The annotation for the message. */
		messageAnnotation: Option[Schema.GoogleCloudDialogflowV2beta1MessageAnnotation] = None,
	  /** Output only. The sentiment analysis result for the message. */
		sentimentAnalysis: Option[Schema.GoogleCloudDialogflowV2beta1SentimentAnalysisResult] = None
	)
	
	case class GoogleCloudDialogflowV2beta1ResponseMessage(
	  /** Returns a text response. */
		text: Option[Schema.GoogleCloudDialogflowV2beta1ResponseMessageText] = None,
	  /** Returns a response containing a custom, platform-specific payload. */
		payload: Option[Map[String, JsValue]] = None,
	  /** Hands off conversation to a live agent. */
		liveAgentHandoff: Option[Schema.GoogleCloudDialogflowV2beta1ResponseMessageLiveAgentHandoff] = None,
	  /** A signal that indicates the interaction with the Dialogflow agent has ended. */
		endInteraction: Option[Schema.GoogleCloudDialogflowV2beta1ResponseMessageEndInteraction] = None,
	  /** An audio response message composed of both the synthesized Dialogflow agent responses and the audios hosted in places known to the client. */
		mixedAudio: Option[Schema.GoogleCloudDialogflowV2beta1ResponseMessageMixedAudio] = None,
	  /** A signal that the client should transfer the phone call connected to this agent to a third-party endpoint. */
		telephonyTransferCall: Option[Schema.GoogleCloudDialogflowV2beta1ResponseMessageTelephonyTransferCall] = None
	)
	
	case class GoogleCloudDialogflowV2beta1ResponseMessageText(
	  /** A collection of text response variants. If multiple variants are defined, only one text response variant is returned at runtime. */
		text: Option[List[String]] = None
	)
	
	case class GoogleCloudDialogflowV2beta1ResponseMessageLiveAgentHandoff(
	  /** Custom metadata for your handoff procedure. Dialogflow doesn't impose any structure on this. */
		metadata: Option[Map[String, JsValue]] = None
	)
	
	case class GoogleCloudDialogflowV2beta1ResponseMessageEndInteraction(
	
	)
	
	case class GoogleCloudDialogflowV2beta1ResponseMessageMixedAudio(
	  /** Segments this audio response is composed of. */
		segments: Option[List[Schema.GoogleCloudDialogflowV2beta1ResponseMessageMixedAudioSegment]] = None
	)
	
	case class GoogleCloudDialogflowV2beta1ResponseMessageMixedAudioSegment(
	  /** Raw audio synthesized from the Dialogflow agent's response using the output config specified in the request. */
		audio: Option[String] = None,
	  /** Client-specific URI that points to an audio clip accessible to the client. */
		uri: Option[String] = None,
	  /** Whether the playback of this segment can be interrupted by the end user's speech and the client should then start the next Dialogflow request. */
		allowPlaybackInterruption: Option[Boolean] = None
	)
	
	case class GoogleCloudDialogflowV2beta1ResponseMessageTelephonyTransferCall(
	  /** Transfer the call to a phone number in [E.164 format](https://en.wikipedia.org/wiki/E.164). */
		phoneNumber: Option[String] = None,
	  /** Transfer the call to a SIP endpoint. */
		sipUri: Option[String] = None
	)
	
	case class GoogleCloudDialogflowV2beta1MessageAnnotation(
	  /** Optional. The collection of annotated message parts ordered by their position in the message. You can recover the annotated message by concatenating [AnnotatedMessagePart.text]. */
		parts: Option[List[Schema.GoogleCloudDialogflowV2beta1AnnotatedMessagePart]] = None,
	  /** Required. Indicates whether the text message contains entities. */
		containEntities: Option[Boolean] = None
	)
	
	case class GoogleCloudDialogflowV2beta1AnnotatedMessagePart(
	  /** Required. A part of a message possibly annotated with an entity. */
		text: Option[String] = None,
	  /** Optional. The [Dialogflow system entity type](https://cloud.google.com/dialogflow/docs/reference/system-entities) of this message part. If this is empty, Dialogflow could not annotate the phrase part with a system entity. */
		entityType: Option[String] = None,
	  /** Optional. The [Dialogflow system entity formatted value ](https://cloud.google.com/dialogflow/docs/reference/system-entities) of this message part. For example for a system entity of type `@sys.unit-currency`, this may contain: { "amount": 5, "currency": "USD" }  */
		formattedValue: Option[JsValue] = None
	)
	
	case class GoogleCloudDialogflowV2beta1SentimentAnalysisResult(
	  /** The sentiment analysis result for `query_text`. */
		queryTextSentiment: Option[Schema.GoogleCloudDialogflowV2beta1Sentiment] = None
	)
	
	case class GoogleCloudDialogflowV2beta1Sentiment(
	  /** Sentiment score between -1.0 (negative sentiment) and 1.0 (positive sentiment). */
		score: Option[BigDecimal] = None,
	  /** A non-negative number in the [0, +inf) range, which represents the absolute magnitude of sentiment, regardless of score (positive or negative). */
		magnitude: Option[BigDecimal] = None
	)
	
	case class GoogleCloudDialogflowV2beta1ExportAgentResponse(
	  /** The URI to a file containing the exported agent. This field is populated only if `agent_uri` is specified in `ExportAgentRequest`. */
		agentUri: Option[String] = None,
	  /** Zip compressed raw byte content for agent. */
		agentContent: Option[String] = None
	)
	
	case class GoogleCloudDialogflowV2beta1HumanAgentAssistantEvent(
	  /** The conversation this notification refers to. Format: `projects//conversations/`. */
		conversation: Option[String] = None,
	  /** The participant that the suggestion is compiled for. And This field is used to call Participants.ListSuggestions API. Format: `projects//conversations//participants/`. It will not be set in legacy workflow. HumanAgentAssistantConfig.name for more information. */
		participant: Option[String] = None,
	  /** The suggestion results payload that this notification refers to. It will only be set when HumanAgentAssistantConfig.SuggestionConfig.group_suggestion_responses sets to true. */
		suggestionResults: Option[List[Schema.GoogleCloudDialogflowV2beta1SuggestionResult]] = None
	)
	
	case class GoogleCloudDialogflowV2beta1SuggestionResult(
	  /** Error status if the request failed. */
		error: Option[Schema.GoogleRpcStatus] = None,
	  /** SuggestArticlesResponse if request is for ARTICLE_SUGGESTION. */
		suggestArticlesResponse: Option[Schema.GoogleCloudDialogflowV2beta1SuggestArticlesResponse] = None,
	  /** SuggestKnowledgeAssistResponse if request is for KNOWLEDGE_ASSIST. */
		suggestKnowledgeAssistResponse: Option[Schema.GoogleCloudDialogflowV2beta1SuggestKnowledgeAssistResponse] = None,
	  /** SuggestFaqAnswersResponse if request is for FAQ_ANSWER. */
		suggestFaqAnswersResponse: Option[Schema.GoogleCloudDialogflowV2beta1SuggestFaqAnswersResponse] = None,
	  /** SuggestSmartRepliesResponse if request is for SMART_REPLY. */
		suggestSmartRepliesResponse: Option[Schema.GoogleCloudDialogflowV2beta1SuggestSmartRepliesResponse] = None,
	  /** SuggestDialogflowAssistsResponse if request is for DIALOGFLOW_ASSIST. */
		suggestDialogflowAssistsResponse: Option[Schema.GoogleCloudDialogflowV2beta1SuggestDialogflowAssistsResponse] = None,
	  /** SuggestDialogflowAssistsResponse if request is for ENTITY_EXTRACTION. */
		suggestEntityExtractionResponse: Option[Schema.GoogleCloudDialogflowV2beta1SuggestDialogflowAssistsResponse] = None
	)
	
	case class GoogleCloudDialogflowV2beta1SuggestArticlesResponse(
	  /** Output only. Articles ordered by score in descending order. */
		articleAnswers: Option[List[Schema.GoogleCloudDialogflowV2beta1ArticleAnswer]] = None,
	  /** The name of the latest conversation message used to compile suggestion for. Format: `projects//locations//conversations//messages/`. */
		latestMessage: Option[String] = None,
	  /** Number of messages prior to and including latest_message to compile the suggestion. It may be smaller than the SuggestArticlesResponse.context_size field in the request if there aren't that many messages in the conversation. */
		contextSize: Option[Int] = None
	)
	
	case class GoogleCloudDialogflowV2beta1ArticleAnswer(
	  /** The article title. */
		title: Option[String] = None,
	  /** The article URI. */
		uri: Option[String] = None,
	  /** Output only. Article snippets. */
		snippets: Option[List[String]] = None,
	  /** A map that contains metadata about the answer and the document from which it originates. */
		metadata: Option[Map[String, String]] = None,
	  /** The name of answer record, in the format of "projects//locations//answerRecords/" */
		answerRecord: Option[String] = None
	)
	
	case class GoogleCloudDialogflowV2beta1SuggestKnowledgeAssistResponse(
	  /** Output only. Knowledge Assist suggestion. */
		knowledgeAssistAnswer: Option[Schema.GoogleCloudDialogflowV2beta1KnowledgeAssistAnswer] = None,
	  /** The name of the latest conversation message used to compile suggestion for. Format: `projects//locations//conversations//messages/`. */
		latestMessage: Option[String] = None,
	  /** Number of messages prior to and including latest_message to compile the suggestion. It may be smaller than the SuggestKnowledgeAssistRequest.context_size field in the request if there are fewer messages in the conversation. */
		contextSize: Option[Int] = None
	)
	
	case class GoogleCloudDialogflowV2beta1KnowledgeAssistAnswer(
	  /** The query suggested based on the context. Suggestion is made only if it is different from the previous suggestion. */
		suggestedQuery: Option[Schema.GoogleCloudDialogflowV2beta1KnowledgeAssistAnswerSuggestedQuery] = None,
	  /** The answer generated for the suggested query. Whether or not an answer is generated depends on how confident we are about the generated query. */
		suggestedQueryAnswer: Option[Schema.GoogleCloudDialogflowV2beta1KnowledgeAssistAnswerKnowledgeAnswer] = None,
	  /** The name of the answer record. Format: `projects//locations//answer Records/`. */
		answerRecord: Option[String] = None
	)
	
	case class GoogleCloudDialogflowV2beta1KnowledgeAssistAnswerSuggestedQuery(
	  /** Suggested query text. */
		queryText: Option[String] = None
	)
	
	case class GoogleCloudDialogflowV2beta1KnowledgeAssistAnswerKnowledgeAnswer(
	  /** The piece of text from the `source` that answers this suggested query. */
		answerText: Option[String] = None,
	  /** Populated if the prediction came from FAQ. */
		faqSource: Option[Schema.GoogleCloudDialogflowV2beta1KnowledgeAssistAnswerKnowledgeAnswerFaqSource] = None,
	  /** Populated if the prediction was Generative. */
		generativeSource: Option[Schema.GoogleCloudDialogflowV2beta1KnowledgeAssistAnswerKnowledgeAnswerGenerativeSource] = None
	)
	
	case class GoogleCloudDialogflowV2beta1KnowledgeAssistAnswerKnowledgeAnswerFaqSource(
	  /** The corresponding FAQ question. */
		question: Option[String] = None
	)
	
	case class GoogleCloudDialogflowV2beta1KnowledgeAssistAnswerKnowledgeAnswerGenerativeSource(
	  /** All snippets used for this Generative Prediction, with their source URI and data. */
		snippets: Option[List[Schema.GoogleCloudDialogflowV2beta1KnowledgeAssistAnswerKnowledgeAnswerGenerativeSourceSnippet]] = None
	)
	
	case class GoogleCloudDialogflowV2beta1KnowledgeAssistAnswerKnowledgeAnswerGenerativeSourceSnippet(
	  /** URI the data is sourced from. */
		uri: Option[String] = None,
	  /** Text taken from that URI. */
		text: Option[String] = None,
	  /** Title of the document. */
		title: Option[String] = None,
	  /** Metadata of the document. */
		metadata: Option[Map[String, JsValue]] = None
	)
	
	case class GoogleCloudDialogflowV2beta1SuggestFaqAnswersResponse(
	  /** Output only. Answers extracted from FAQ documents. */
		faqAnswers: Option[List[Schema.GoogleCloudDialogflowV2beta1FaqAnswer]] = None,
	  /** The name of the latest conversation message used to compile suggestion for. Format: `projects//locations//conversations//messages/`. */
		latestMessage: Option[String] = None,
	  /** Number of messages prior to and including latest_message to compile the suggestion. It may be smaller than the SuggestFaqAnswersRequest.context_size field in the request if there aren't that many messages in the conversation. */
		contextSize: Option[Int] = None
	)
	
	case class GoogleCloudDialogflowV2beta1FaqAnswer(
	  /** The piece of text from the `source` knowledge base document. */
		answer: Option[String] = None,
	  /** The system's confidence score that this Knowledge answer is a good match for this conversational query, range from 0.0 (completely uncertain) to 1.0 (completely certain). */
		confidence: Option[BigDecimal] = None,
	  /** The corresponding FAQ question. */
		question: Option[String] = None,
	  /** Indicates which Knowledge Document this answer was extracted from. Format: `projects//locations//agent/knowledgeBases//documents/`. */
		source: Option[String] = None,
	  /** A map that contains metadata about the answer and the document from which it originates. */
		metadata: Option[Map[String, String]] = None,
	  /** The name of answer record, in the format of "projects//locations//answerRecords/" */
		answerRecord: Option[String] = None
	)
	
	case class GoogleCloudDialogflowV2beta1SuggestSmartRepliesResponse(
	  /** Output only. Multiple reply options provided by smart reply service. The order is based on the rank of the model prediction. The maximum number of the returned replies is set in SmartReplyConfig. */
		smartReplyAnswers: Option[List[Schema.GoogleCloudDialogflowV2beta1SmartReplyAnswer]] = None,
	  /** The name of the latest conversation message used to compile suggestion for. Format: `projects//locations//conversations//messages/`. */
		latestMessage: Option[String] = None,
	  /** Number of messages prior to and including latest_message to compile the suggestion. It may be smaller than the SuggestSmartRepliesRequest.context_size field in the request if there aren't that many messages in the conversation. */
		contextSize: Option[Int] = None
	)
	
	case class GoogleCloudDialogflowV2beta1SmartReplyAnswer(
	  /** The content of the reply. */
		reply: Option[String] = None,
	  /** Smart reply confidence. The system's confidence score that this reply is a good match for this conversation, as a value from 0.0 (completely uncertain) to 1.0 (completely certain). */
		confidence: Option[BigDecimal] = None,
	  /** The name of answer record, in the format of "projects//locations//answerRecords/" */
		answerRecord: Option[String] = None
	)
	
	case class GoogleCloudDialogflowV2beta1SuggestDialogflowAssistsResponse(
	  /** Output only. Multiple reply options provided by Dialogflow assist service. The order is based on the rank of the model prediction. */
		dialogflowAssistAnswers: Option[List[Schema.GoogleCloudDialogflowV2beta1DialogflowAssistAnswer]] = None,
	  /** The name of the latest conversation message used to suggest answer. Format: `projects//locations//conversations//messages/`. */
		latestMessage: Option[String] = None,
	  /** Number of messages prior to and including latest_message to compile the suggestion. It may be smaller than the SuggestDialogflowAssistsRequest.context_size field in the request if there aren't that many messages in the conversation. */
		contextSize: Option[Int] = None
	)
	
	case class GoogleCloudDialogflowV2beta1DialogflowAssistAnswer(
	  /** Result from v2 agent. */
		queryResult: Option[Schema.GoogleCloudDialogflowV2beta1QueryResult] = None,
	  /** An intent suggestion generated from conversation. */
		intentSuggestion: Option[Schema.GoogleCloudDialogflowV2beta1IntentSuggestion] = None,
	  /** The name of answer record, in the format of "projects//locations//answerRecords/" */
		answerRecord: Option[String] = None
	)
	
	case class GoogleCloudDialogflowV2beta1QueryResult(
	  /** The original conversational query text: - If natural language text was provided as input, `query_text` contains a copy of the input. - If natural language speech audio was provided as input, `query_text` contains the speech recognition result. If speech recognizer produced multiple alternatives, a particular one is picked. - If automatic spell correction is enabled, `query_text` will contain the corrected user input. */
		queryText: Option[String] = None,
	  /** The language that was triggered during intent detection. See [Language Support](https://cloud.google.com/dialogflow/docs/reference/language) for a list of the currently supported language codes. */
		languageCode: Option[String] = None,
	  /** The Speech recognition confidence between 0.0 and 1.0. A higher number indicates an estimated greater likelihood that the recognized words are correct. The default of 0.0 is a sentinel value indicating that confidence was not set. This field is not guaranteed to be accurate or set. In particular this field isn't set for StreamingDetectIntent since the streaming endpoint has separate confidence estimates per portion of the audio in StreamingRecognitionResult. */
		speechRecognitionConfidence: Option[BigDecimal] = None,
	  /** The action name from the matched intent. */
		action: Option[String] = None,
	  /** The collection of extracted parameters. Depending on your protocol or client library language, this is a map, associative array, symbol table, dictionary, or JSON object composed of a collection of (MapKey, MapValue) pairs: &#42; MapKey type: string &#42; MapKey value: parameter name &#42; MapValue type: If parameter's entity type is a composite entity then use map, otherwise, depending on the parameter value type, it could be one of string, number, boolean, null, list or map. &#42; MapValue value: If parameter's entity type is a composite entity then use map from composite entity property names to property values, otherwise, use parameter value. */
		parameters: Option[Map[String, JsValue]] = None,
	  /** This field is set to: - `false` if the matched intent has required parameters and not all of the required parameter values have been collected. - `true` if all required parameter values have been collected, or if the matched intent doesn't contain any required parameters. */
		allRequiredParamsPresent: Option[Boolean] = None,
	  /** Indicates whether the conversational query triggers a cancellation for slot filling. For more information, see the [cancel slot filling documentation](https://cloud.google.com/dialogflow/es/docs/intents-actions-parameters#cancel). */
		cancelsSlotFilling: Option[Boolean] = None,
	  /** The text to be pronounced to the user or shown on the screen. Note: This is a legacy field, `fulfillment_messages` should be preferred. */
		fulfillmentText: Option[String] = None,
	  /** The collection of rich messages to present to the user. */
		fulfillmentMessages: Option[List[Schema.GoogleCloudDialogflowV2beta1IntentMessage]] = None,
	  /** If the query was fulfilled by a webhook call, this field is set to the value of the `source` field returned in the webhook response. */
		webhookSource: Option[String] = None,
	  /** If the query was fulfilled by a webhook call, this field is set to the value of the `payload` field returned in the webhook response. */
		webhookPayload: Option[Map[String, JsValue]] = None,
	  /** The collection of output contexts. If applicable, `output_contexts.parameters` contains entries with name `.original` containing the original parameter values before the query. */
		outputContexts: Option[List[Schema.GoogleCloudDialogflowV2beta1Context]] = None,
	  /** The intent that matched the conversational query. Some, not all fields are filled in this message, including but not limited to: `name`, `display_name`, `end_interaction` and `is_fallback`. */
		intent: Option[Schema.GoogleCloudDialogflowV2beta1Intent] = None,
	  /** The intent detection confidence. Values range from 0.0 (completely uncertain) to 1.0 (completely certain). This value is for informational purpose only and is only used to help match the best intent within the classification threshold. This value may change for the same end-user expression at any time due to a model retraining or change in implementation. If there are `multiple knowledge_answers` messages, this value is set to the greatest `knowledgeAnswers.match_confidence` value in the list. */
		intentDetectionConfidence: Option[BigDecimal] = None,
	  /** Free-form diagnostic information for the associated detect intent request. The fields of this data can change without notice, so you should not write code that depends on its structure. The data may contain: - webhook call latency - webhook errors */
		diagnosticInfo: Option[Map[String, JsValue]] = None,
	  /** The sentiment analysis result, which depends on the `sentiment_analysis_request_config` specified in the request. */
		sentimentAnalysisResult: Option[Schema.GoogleCloudDialogflowV2beta1SentimentAnalysisResult] = None,
	  /** The result from Knowledge Connector (if any), ordered by decreasing `KnowledgeAnswers.match_confidence`. */
		knowledgeAnswers: Option[Schema.GoogleCloudDialogflowV2beta1KnowledgeAnswers] = None
	)
	
	case class GoogleCloudDialogflowV2beta1KnowledgeAnswers(
	  /** A list of answers from Knowledge Connector. */
		answers: Option[List[Schema.GoogleCloudDialogflowV2beta1KnowledgeAnswersAnswer]] = None
	)
	
	object GoogleCloudDialogflowV2beta1KnowledgeAnswersAnswer {
		enum MatchConfidenceLevelEnum extends Enum[MatchConfidenceLevelEnum] { case MATCH_CONFIDENCE_LEVEL_UNSPECIFIED, LOW, MEDIUM, HIGH }
	}
	case class GoogleCloudDialogflowV2beta1KnowledgeAnswersAnswer(
	  /** Indicates which Knowledge Document this answer was extracted from. Format: `projects//knowledgeBases//documents/`. */
		source: Option[String] = None,
	  /** The corresponding FAQ question if the answer was extracted from a FAQ Document, empty otherwise. */
		faqQuestion: Option[String] = None,
	  /** The piece of text from the `source` knowledge base document that answers this conversational query. */
		answer: Option[String] = None,
	  /** The system's confidence level that this knowledge answer is a good match for this conversational query. NOTE: The confidence level for a given `` pair may change without notice, as it depends on models that are constantly being improved. However, it will change less frequently than the confidence score below, and should be preferred for referencing the quality of an answer. */
		matchConfidenceLevel: Option[Schema.GoogleCloudDialogflowV2beta1KnowledgeAnswersAnswer.MatchConfidenceLevelEnum] = None,
	  /** The system's confidence score that this Knowledge answer is a good match for this conversational query. The range is from 0.0 (completely uncertain) to 1.0 (completely certain). Note: The confidence score is likely to vary somewhat (possibly even for identical requests), as the underlying model is under constant improvement. It may be deprecated in the future. We recommend using `match_confidence_level` which should be generally more stable. */
		matchConfidence: Option[BigDecimal] = None
	)
	
	case class GoogleCloudDialogflowV2beta1IntentSuggestion(
	  /** The display name of the intent. */
		displayName: Option[String] = None,
	  /** The unique identifier of this intent. Format: `projects//locations//agent/intents/`. */
		intentV2: Option[String] = None,
	  /** Human readable description for better understanding an intent like its scope, content, result etc. Maximum character limit: 140 characters. */
		description: Option[String] = None
	)
	
	case class GoogleCloudDialogflowV2beta1ImportDocumentsResponse(
	  /** Includes details about skipped documents or any other warnings. */
		warnings: Option[List[Schema.GoogleRpcStatus]] = None
	)
	
	case class GoogleCloudDialogflowV2beta1InitializeEncryptionSpecMetadata(
	  /** Output only. The original request for initialization. */
		request: Option[Schema.GoogleCloudDialogflowV2beta1InitializeEncryptionSpecRequest] = None
	)
	
	case class GoogleCloudDialogflowV2beta1InitializeEncryptionSpecRequest(
	  /** Required. The encryption spec used for CMEK encryption. It is required that the kms key is in the same region as the endpoint. The same key will be used for all provisioned resources, if encryption is available. If the kms_key_name is left empty, no encryption will be enforced. */
		encryptionSpec: Option[Schema.GoogleCloudDialogflowV2beta1EncryptionSpec] = None
	)
	
	case class GoogleCloudDialogflowV2beta1EncryptionSpec(
	  /** Immutable. The resource name of the encryption key specification resource. Format: projects/{project}/locations/{location}/encryptionSpec */
		name: Option[String] = None,
	  /** Required. The name of customer-managed encryption key that is used to secure a resource and its sub-resources. If empty, the resource is secured by the default Google encryption key. Only the key in the same location as this resource is allowed to be used for encryption. Format: `projects/{project}/locations/{location}/keyRings/{keyRing}/cryptoKeys/{key}` */
		kmsKey: Option[String] = None
	)
	
	object GoogleCloudDialogflowV2beta1KnowledgeOperationMetadata {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, PENDING, RUNNING, DONE }
	}
	case class GoogleCloudDialogflowV2beta1KnowledgeOperationMetadata(
	  /** Required. Output only. The current state of this operation. */
		state: Option[Schema.GoogleCloudDialogflowV2beta1KnowledgeOperationMetadata.StateEnum] = None,
	  /** The name of the knowledge base interacted with during the operation. */
		knowledgeBase: Option[String] = None,
	  /** Metadata for the Export Data Operation such as the destination of export. */
		exportOperationMetadata: Option[Schema.GoogleCloudDialogflowV2beta1ExportOperationMetadata] = None
	)
	
	case class GoogleCloudDialogflowV2beta1ExportOperationMetadata(
	  /** Cloud Storage file path of the exported data. */
		exportedGcsDestination: Option[Schema.GoogleCloudDialogflowV2beta1GcsDestination] = None
	)
	
	case class GoogleCloudDialogflowV2beta1GcsDestination(
	  /** Required. The Google Cloud Storage URIs for the output. A URI is of the form: `gs://bucket/object-prefix-or-name` Whether a prefix or name is used depends on the use case. The requesting user must have "write-permission" to the bucket. */
		uri: Option[String] = None
	)
	
	case class GoogleCloudDialogflowV2beta1OriginalDetectIntentRequest(
	  /** The source of this request, e.g., `google`, `facebook`, `slack`. It is set by Dialogflow-owned servers. */
		source: Option[String] = None,
	  /** Optional. The version of the protocol used for this request. This field is AoG-specific. */
		version: Option[String] = None,
	  /** Optional. This field is set to the value of the `QueryParameters.payload` field passed in the request. Some integrations that query a Dialogflow agent may provide additional information in the payload. In particular, for the Dialogflow Phone Gateway integration, this field has the form: { "telephony": { "caller_id": "+18558363987" } } Note: The caller ID field (`caller_id`) will be redacted for Trial Edition agents and populated with the caller ID in [E.164 format](https://en.wikipedia.org/wiki/E.164) for Essentials Edition agents. */
		payload: Option[Map[String, JsValue]] = None
	)
	
	object GoogleCloudDialogflowV2beta1SetSuggestionFeatureConfigOperationMetadata {
		enum ParticipantRoleEnum extends Enum[ParticipantRoleEnum] { case ROLE_UNSPECIFIED, HUMAN_AGENT, AUTOMATED_AGENT, END_USER }
		enum SuggestionFeatureTypeEnum extends Enum[SuggestionFeatureTypeEnum] { case TYPE_UNSPECIFIED, ARTICLE_SUGGESTION, FAQ, SMART_REPLY, DIALOGFLOW_ASSIST, CONVERSATION_SUMMARIZATION, KNOWLEDGE_SEARCH, KNOWLEDGE_ASSIST }
	}
	case class GoogleCloudDialogflowV2beta1SetSuggestionFeatureConfigOperationMetadata(
	  /** The resource name of the conversation profile. Format: `projects//locations//conversationProfiles/` */
		conversationProfile: Option[String] = None,
	  /** Required. The participant role to add or update the suggestion feature config. Only HUMAN_AGENT or END_USER can be used. */
		participantRole: Option[Schema.GoogleCloudDialogflowV2beta1SetSuggestionFeatureConfigOperationMetadata.ParticipantRoleEnum] = None,
	  /** Required. The type of the suggestion feature to add or update. */
		suggestionFeatureType: Option[Schema.GoogleCloudDialogflowV2beta1SetSuggestionFeatureConfigOperationMetadata.SuggestionFeatureTypeEnum] = None,
	  /** Timestamp whe the request was created. The time is measured on server side. */
		createTime: Option[String] = None
	)
	
	case class GoogleCloudDialogflowV2beta1WebhookRequest(
	  /** The unique identifier of detectIntent request session. Can be used to identify end-user inside webhook implementation. Supported formats: - `projects//agent/sessions/, - `projects//locations//agent/sessions/`, - `projects//agent/environments//users//sessions/`, - `projects//locations//agent/environments//users//sessions/`, */
		session: Option[String] = None,
	  /** The unique identifier of the response. Contains the same value as `[Streaming]DetectIntentResponse.response_id`. */
		responseId: Option[String] = None,
	  /** The result of the conversational query or event processing. Contains the same value as `[Streaming]DetectIntentResponse.query_result`. */
		queryResult: Option[Schema.GoogleCloudDialogflowV2beta1QueryResult] = None,
	  /** Alternative query results from KnowledgeService. */
		alternativeQueryResults: Option[List[Schema.GoogleCloudDialogflowV2beta1QueryResult]] = None,
	  /** Optional. The contents of the original request that was passed to `[Streaming]DetectIntent` call. */
		originalDetectIntentRequest: Option[Schema.GoogleCloudDialogflowV2beta1OriginalDetectIntentRequest] = None
	)
	
	case class GoogleCloudDialogflowV2beta1WebhookResponse(
	  /** Optional. The text response message intended for the end-user. It is recommended to use `fulfillment_messages.text.text[0]` instead. When provided, Dialogflow uses this field to populate QueryResult.fulfillment_text sent to the integration or API caller. */
		fulfillmentText: Option[String] = None,
	  /** Optional. The rich response messages intended for the end-user. When provided, Dialogflow uses this field to populate QueryResult.fulfillment_messages sent to the integration or API caller. */
		fulfillmentMessages: Option[List[Schema.GoogleCloudDialogflowV2beta1IntentMessage]] = None,
	  /** Optional. A custom field used to identify the webhook source. Arbitrary strings are supported. When provided, Dialogflow uses this field to populate QueryResult.webhook_source sent to the integration or API caller. */
		source: Option[String] = None,
	  /** Optional. This field can be used to pass custom data from your webhook to the integration or API caller. Arbitrary JSON objects are supported. When provided, Dialogflow uses this field to populate QueryResult.webhook_payload sent to the integration or API caller. This field is also used by the [Google Assistant integration](https://cloud.google.com/dialogflow/docs/integrations/aog) for rich response messages. See the format definition at [Google Assistant Dialogflow webhook format](https://developers.google.com/assistant/actions/build/json/dialogflow-webhook-json) */
		payload: Option[Map[String, JsValue]] = None,
	  /** Optional. The collection of output contexts that will overwrite currently active contexts for the session and reset their lifespans. When provided, Dialogflow uses this field to populate QueryResult.output_contexts sent to the integration or API caller. */
		outputContexts: Option[List[Schema.GoogleCloudDialogflowV2beta1Context]] = None,
	  /** Optional. Invokes the supplied events. When this field is set, Dialogflow ignores the `fulfillment_text`, `fulfillment_messages`, and `payload` fields. */
		followupEventInput: Option[Schema.GoogleCloudDialogflowV2beta1EventInput] = None,
	  /** Indicates that a live agent should be brought in to handle the interaction with the user. In most cases, when you set this flag to true, you would also want to set end_interaction to true as well. Default is false. */
		liveAgentHandoff: Option[Boolean] = None,
	  /** Optional. Indicates that this intent ends an interaction. Some integrations (e.g., Actions on Google or Dialogflow phone gateway) use this information to close interaction with an end user. Default is false. */
		endInteraction: Option[Boolean] = None,
	  /** Optional. Additional session entity types to replace or extend developer entity types with. The entity synonyms apply to all languages and persist for the session. Setting this data from a webhook overwrites the session entity types that have been set using `detectIntent`, `streamingDetectIntent` or SessionEntityType management methods. */
		sessionEntityTypes: Option[List[Schema.GoogleCloudDialogflowV2beta1SessionEntityType]] = None
	)
	
	case class GoogleCloudDialogflowV2beta1EventInput(
	  /** Required. The unique identifier of the event. */
		name: Option[String] = None,
	  /** The collection of parameters associated with the event. Depending on your protocol or client library language, this is a map, associative array, symbol table, dictionary, or JSON object composed of a collection of (MapKey, MapValue) pairs: &#42; MapKey type: string &#42; MapKey value: parameter name &#42; MapValue type: If parameter's entity type is a composite entity then use map, otherwise, depending on the parameter value type, it could be one of string, number, boolean, null, list or map. &#42; MapValue value: If parameter's entity type is a composite entity then use map from composite entity property names to property values, otherwise, use parameter value. */
		parameters: Option[Map[String, JsValue]] = None,
	  /** Required. The language of this query. See [Language Support](https://cloud.google.com/dialogflow/docs/reference/language) for a list of the currently supported language codes. Note that queries in the same session do not necessarily need to specify the same language. This field is ignored when used in the context of a WebhookResponse.followup_event_input field, because the language was already defined in the originating detect intent request. */
		languageCode: Option[String] = None
	)
	
	object GoogleCloudDialogflowV2beta1SessionEntityType {
		enum EntityOverrideModeEnum extends Enum[EntityOverrideModeEnum] { case ENTITY_OVERRIDE_MODE_UNSPECIFIED, ENTITY_OVERRIDE_MODE_OVERRIDE, ENTITY_OVERRIDE_MODE_SUPPLEMENT }
	}
	case class GoogleCloudDialogflowV2beta1SessionEntityType(
	  /** Required. The unique identifier of this session entity type. Supported formats: - `projects//agent/sessions//entityTypes/` - `projects//locations//agent/sessions//entityTypes/` - `projects//agent/environments//users//sessions//entityTypes/` - `projects//locations//agent/environments/ /users//sessions//entityTypes/` If `Location ID` is not specified we assume default 'us' location. If `Environment ID` is not specified, we assume default 'draft' environment. If `User ID` is not specified, we assume default '-' user. `` must be the display name of an existing entity type in the same agent that will be overridden or supplemented. */
		name: Option[String] = None,
	  /** Required. Indicates whether the additional data should override or supplement the custom entity type definition. */
		entityOverrideMode: Option[Schema.GoogleCloudDialogflowV2beta1SessionEntityType.EntityOverrideModeEnum] = None,
	  /** Required. The collection of entities associated with this session entity type. */
		entities: Option[List[Schema.GoogleCloudDialogflowV2beta1EntityTypeEntity]] = None
	)
	
	case class GoogleCloudDialogflowV3alpha1ConversationSignals(
	  /** Required. Turn signals for the current turn. */
		turnSignals: Option[Schema.GoogleCloudDialogflowV3alpha1TurnSignals] = None
	)
	
	object GoogleCloudDialogflowV3alpha1TurnSignals {
		enum FailureReasonsEnum extends Enum[FailureReasonsEnum] { case FAILURE_REASON_UNSPECIFIED, FAILED_INTENT, FAILED_WEBHOOK }
	}
	case class GoogleCloudDialogflowV3alpha1TurnSignals(
	  /** Whether NLU predicted NO_MATCH. */
		noMatch: Option[Boolean] = None,
	  /** Whether user provided no input. */
		noUserInput: Option[Boolean] = None,
	  /** Whether user was using DTMF input. */
		dtmfUsed: Option[Boolean] = None,
	  /** Whether user was specifically asking for a live agent. */
		userEscalated: Option[Boolean] = None,
	  /** Whether agent responded with LiveAgentHandoff fulfillment. */
		agentEscalated: Option[Boolean] = None,
	  /** Whether agent has triggered the event corresponding to user abandoning the conversation. */
		triggeredAbandonmentEvent: Option[Boolean] = None,
	  /** Whether turn resulted in End Session page. */
		reachedEndPage: Option[Boolean] = None,
	  /** Human-readable statuses of the webhooks triggered during this turn. */
		webhookStatuses: Option[List[String]] = None,
	  /** Failure reasons of the turn. */
		failureReasons: Option[List[Schema.GoogleCloudDialogflowV3alpha1TurnSignals.FailureReasonsEnum]] = None,
	  /** Sentiment score of the user utterance if [sentiment](https://cloud.google.com/dialogflow/cx/docs/concept/sentiment) was enabled. */
		sentimentScore: Option[BigDecimal] = None,
	  /** Sentiment magnitude of the user utterance if [sentiment](https://cloud.google.com/dialogflow/cx/docs/concept/sentiment) was enabled. */
		sentimentMagnitude: Option[BigDecimal] = None
	)
}
