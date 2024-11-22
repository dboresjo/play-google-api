package com.boresjo.play.api.google.sasportal

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	case class SasPortalUpdateSignedDeviceRequest(
	  /** Required. The JSON Web Token signed using a CPI private key. Payload must be the JSON encoding of the device. The user_id field must be set. */
		encodedDevice: Option[String] = None,
	  /** Required. Unique installer ID (CPI ID) from the Certified Professional Installers database. */
		installerId: Option[String] = None
	)
	
	case class SasPortalDeviceMetadata(
	  /** Interference Coordination Group (ICG). A group of CBSDs that manage their own interference with the group. For more details, see [CBRSA-TS-2001 V3.0.0](https://ongoalliance.org/wp-content/uploads/2020/02/CBRSA-TS-2001-V3.0.0_Approved-for-publication.pdf). */
		interferenceCoordinationGroup: Option[String] = None,
	  /** If populated, the Antenna Model Pattern to use. Format is: `RecordCreatorId:PatternId` */
		antennaModel: Option[String] = None,
	  /** Output only. National Radio Quiet Zone validation info. */
		nrqzValidation: Option[Schema.SasPortalNrqzValidation] = None,
	  /** Common Channel Group (CCG). A group of CBSDs in the same ICG requesting a common primary channel assignment. For more details, see [CBRSA-TS-2001 V3.0.0](https://ongoalliance.org/wp-content/uploads/2020/02/CBRSA-TS-2001-V3.0.0_Approved-for-publication.pdf). */
		commonChannelGroup: Option[String] = None,
	  /** Output only. Set to `true` if a CPI has validated that they have coordinated with the National Quiet Zone office. */
		nrqzValidated: Option[Boolean] = None
	)
	
	case class SasPortalListGcpProjectDeploymentsResponse(
	  /** Optional. Deployments associated with the GCP project */
		deployments: Option[List[Schema.SasPortalGcpProjectDeployment]] = None
	)
	
	object SasPortalDeviceGrant {
		enum ChannelTypeEnum extends Enum[ChannelTypeEnum] { case CHANNEL_TYPE_UNSPECIFIED, CHANNEL_TYPE_GAA, CHANNEL_TYPE_PAL }
		enum StateEnum extends Enum[StateEnum] { case GRANT_STATE_UNSPECIFIED, GRANT_STATE_GRANTED, GRANT_STATE_TERMINATED, GRANT_STATE_SUSPENDED, GRANT_STATE_AUTHORIZED, GRANT_STATE_EXPIRED }
	}
	case class SasPortalDeviceGrant(
	  /** Type of channel used. */
		channelType: Option[Schema.SasPortalDeviceGrant.ChannelTypeEnum] = None,
	  /** The transmission frequency range. */
		frequencyRange: Option[Schema.SasPortalFrequencyRange] = None,
	  /** The DPA move lists on which this grant appears. */
		moveList: Option[List[Schema.SasPortalDpaMoveList]] = None,
	  /** The transmit expiration time of the last heartbeat. */
		lastHeartbeatTransmitExpireTime: Option[String] = None,
	  /** The expiration time of the grant. */
		expireTime: Option[String] = None,
	  /** If the grant is suspended, the reason(s) for suspension. */
		suspensionReason: Option[List[String]] = None,
	  /** State of the grant. */
		state: Option[Schema.SasPortalDeviceGrant.StateEnum] = None,
	  /** Grant Id. */
		grantId: Option[String] = None,
	  /** Maximum Equivalent Isotropically Radiated Power (EIRP) permitted by the grant. The maximum EIRP is in units of dBm/MHz. The value of `maxEirp` represents the average (RMS) EIRP that would be measured by the procedure defined in FCC part 96.41(e)(3). */
		maxEirp: Option[BigDecimal] = None
	)
	
	case class SasPortalTestPermissionsRequest(
	  /** Required. The resource for which the permissions are being requested. */
		resource: Option[String] = None,
	  /** The set of permissions to check for the `resource`. */
		permissions: Option[List[String]] = None
	)
	
	object SasPortalDevice {
		enum StateEnum extends Enum[StateEnum] { case DEVICE_STATE_UNSPECIFIED, RESERVED, REGISTERED, DEREGISTERED }
	}
	case class SasPortalDevice(
	  /** Configuration of the device, as specified via SAS Portal API. */
		preloadedConfig: Option[Schema.SasPortalDeviceConfig] = None,
	  /** Output only. Grants held by the device. */
		grants: Option[List[Schema.SasPortalDeviceGrant]] = None,
	  /** Output only. Current configuration of the device as registered to the SAS. */
		activeConfig: Option[Schema.SasPortalDeviceConfig] = None,
	  /** A serial number assigned to the device by the device manufacturer. */
		serialNumber: Option[String] = None,
	  /** Output only. Device state. */
		state: Option[Schema.SasPortalDevice.StateEnum] = None,
	  /** Device parameters that can be overridden by both SAS Portal and SAS registration requests. */
		deviceMetadata: Option[Schema.SasPortalDeviceMetadata] = None,
	  /** Output only. Current channels with scores. */
		currentChannels: Option[List[Schema.SasPortalChannelWithScore]] = None,
	  /** Only ranges that are within the allowlists are available for new grants. */
		grantRangeAllowlists: Option[List[Schema.SasPortalFrequencyRange]] = None,
	  /** The FCC identifier of the device. Refer to https://www.fcc.gov/oet/ea/fccid for FccID format. Accept underscores and periods because some test-SAS customers use them. */
		fccId: Option[String] = None,
	  /** Output only. The resource path name. */
		name: Option[String] = None,
	  /** Device display name. */
		displayName: Option[String] = None
	)
	
	case class SasPortalMigrateOrganizationRequest(
	  /** Required. Id of the SAS organization to be migrated. */
		organizationId: Option[String] = None
	)
	
	case class SasPortalSignDeviceRequest(
	  /** Required. The device to sign. The device fields name, fcc_id and serial_number must be set. The user_id field must be set. */
		device: Option[Schema.SasPortalDevice] = None
	)
	
	case class SasPortalGenerateSecretResponse(
	  /** The secret generated by the string and used by ValidateInstaller. */
		secret: Option[String] = None
	)
	
	case class SasPortalListCustomersResponse(
	  /** A pagination token returned from a previous call to ListCustomers that indicates from where listing should continue. If the field is missing or empty, it means there are no more customers. */
		nextPageToken: Option[String] = None,
	  /** The list of customers that match the request. */
		customers: Option[List[Schema.SasPortalCustomer]] = None
	)
	
	object SasPortalDeviceConfig {
		enum StateEnum extends Enum[StateEnum] { case DEVICE_CONFIG_STATE_UNSPECIFIED, DRAFT, FINAL }
		enum CategoryEnum extends Enum[CategoryEnum] { case DEVICE_CATEGORY_UNSPECIFIED, DEVICE_CATEGORY_A, DEVICE_CATEGORY_B }
		enum MeasurementCapabilitiesEnum extends Enum[MeasurementCapabilitiesEnum] { case MEASUREMENT_CAPABILITY_UNSPECIFIED, MEASUREMENT_CAPABILITY_RECEIVED_POWER_WITH_GRANT, MEASUREMENT_CAPABILITY_RECEIVED_POWER_WITHOUT_GRANT }
	}
	case class SasPortalDeviceConfig(
	  /** Installation parameters for the device. */
		installationParams: Option[Schema.SasPortalInstallationParams] = None,
	  /** State of the configuration. */
		state: Option[Schema.SasPortalDeviceConfig.StateEnum] = None,
	  /** Information about this device model. */
		model: Option[Schema.SasPortalDeviceModel] = None,
	  /** FCC category of the device. */
		category: Option[Schema.SasPortalDeviceConfig.CategoryEnum] = None,
	  /** The identifier of a device user. */
		userId: Option[String] = None,
	  /** Output only. Whether the configuration has been signed by a CPI. */
		isSigned: Option[Boolean] = None,
	  /** Information about this device's air interface. */
		airInterface: Option[Schema.SasPortalDeviceAirInterface] = None,
	  /** Output only. The last time the device configuration was edited. */
		updateTime: Option[String] = None,
	  /** The call sign of the device operator. */
		callSign: Option[String] = None,
	  /** Measurement reporting capabilities of the device. */
		measurementCapabilities: Option[List[Schema.SasPortalDeviceConfig.MeasurementCapabilitiesEnum]] = None
	)
	
	case class SasPortalSetupSasAnalyticsRequest(
	  /** Optional. User id to setup analytics for, if not provided the user id associated with the project is used. optional */
		userId: Option[String] = None
	)
	
	case class SasPortalProvisionDeploymentResponse(
	  /** Optional. Optional error message if the provisioning request is not successful. */
		errorMessage: Option[String] = None
	)
	
	case class SasPortalOrganization(
	  /** Name of organization */
		displayName: Option[String] = None,
	  /** Id of organization */
		id: Option[String] = None
	)
	
	case class SasPortalListDeploymentsResponse(
	  /** A pagination token returned from a previous call to ListDeployments that indicates from where listing should continue. If the field is missing or empty, it means there are no more deployments. */
		nextPageToken: Option[String] = None,
	  /** The deployments that match the request. */
		deployments: Option[List[Schema.SasPortalDeployment]] = None
	)
	
	case class SasPortalDpaMoveList(
	  /** The frequency range that the move list affects. */
		frequencyRange: Option[Schema.SasPortalFrequencyRange] = None,
	  /** The ID of the DPA. */
		dpaId: Option[String] = None
	)
	
	case class SasPortalMoveDeploymentRequest(
	  /** Required. The name of the new parent resource node or customer to reparent the deployment under. */
		destination: Option[String] = None
	)
	
	object SasPortalDeviceAirInterface {
		enum RadioTechnologyEnum extends Enum[RadioTechnologyEnum] { case RADIO_TECHNOLOGY_UNSPECIFIED, E_UTRA, CAMBIUM_NETWORKS, FOUR_G_BBW_SAA_1, NR, DOODLE_CBRS, CW, REDLINE, TARANA_WIRELESS, FAROS }
	}
	case class SasPortalDeviceAirInterface(
	  /** Optional. This field is related to the `radioTechnology` and provides the air interface specification that the CBSD is compliant with at the time of registration. */
		supportedSpec: Option[String] = None,
	  /** Conditional. This field specifies the radio access technology that is used for the CBSD. */
		radioTechnology: Option[Schema.SasPortalDeviceAirInterface.RadioTechnologyEnum] = None
	)
	
	case class SasPortalOperation(
	  /** The server-assigned name, which is only unique within the same service that originally returns it. If you use the default HTTP mapping, the `name` should be a resource name ending with `operations/{unique_id}`. */
		name: Option[String] = None,
	  /** Service-specific metadata associated with the operation. It typically contains progress information and common metadata such as create time. Some services might not provide such metadata. Any method that returns a long-running operation should document the metadata type, if any. */
		metadata: Option[Map[String, JsValue]] = None,
	  /** The error result of the operation in case of failure or cancellation. */
		error: Option[Schema.SasPortalStatus] = None,
	  /** The normal, successful response of the operation. If the original method returns no data on success, such as `Delete`, the response is `google.protobuf.Empty`. If the original method is standard `Get`/`Create`/`Update`, the response should be the resource. For other methods, the response should have the type `XxxResponse`, where `Xxx` is the original method name. For example, if the original method name is `TakeSnapshot()`, the inferred response type is `TakeSnapshotResponse`. */
		response: Option[Map[String, JsValue]] = None,
	  /** If the value is `false`, it means the operation is still in progress. If `true`, the operation is completed, and either `error` or `response` is available. */
		done: Option[Boolean] = None
	)
	
	case class SasPortalGenerateSecretRequest(
	
	)
	
	case class SasPortalDeployment(
	  /** Output only. Resource name. */
		name: Option[String] = None,
	  /** The deployment's display name. */
		displayName: Option[String] = None,
	  /** User ID used by the devices belonging to this deployment. Each deployment should be associated with one unique user ID. */
		sasUserIds: Option[List[String]] = None,
	  /** Output only. The FCC Registration Numbers (FRNs) copied from its direct parent. */
		frns: Option[List[String]] = None
	)
	
	case class SasPortalMigrateOrganizationResponse(
	  /** Optional. A list of deployment association that were created for the migration, or current associations if they already exist. */
		deploymentAssociation: Option[List[Schema.SasPortalDeploymentAssociation]] = None
	)
	
	case class SasPortalDeploymentAssociation(
	  /** GCP project id of the associated project. */
		gcpProjectId: Option[String] = None,
	  /** User id of the deployment. */
		userId: Option[String] = None
	)
	
	case class SasPortalNode(
	  /** Output only. Resource name. */
		name: Option[String] = None,
	  /** The node's display name. */
		displayName: Option[String] = None,
	  /** User ids used by the devices belonging to this node. */
		sasUserIds: Option[List[String]] = None
	)
	
	case class SasPortalProvisionDeploymentRequest(
	  /** Optional. If this field is set, and a new SAS Portal Organization needs to be created, its display name will be set to the value of this field. */
		newOrganizationDisplayName: Option[String] = None,
	  /** Optional. If this field is set then a new deployment will be created under the organization specified by this id. */
		organizationId: Option[String] = None,
	  /** Optional. If this field is set, and a new SAS Portal Deployment needs to be created, its display name will be set to the value of this field. */
		newDeploymentDisplayName: Option[String] = None
	)
	
	case class SasPortalPolicy(
	  /** The etag is used for optimistic concurrency control as a way to help prevent simultaneous updates of a policy from overwriting each other. It is strongly suggested that systems make use of the etag in the read-modify-write cycle to perform policy updates in order to avoid race conditions: An etag is returned in the response to GetPolicy, and systems are expected to put that etag in the request to SetPolicy to ensure that their change will be applied to the same version of the policy. If no etag is provided in the call to GetPolicy, then the existing policy is overwritten blindly. */
		etag: Option[String] = None,
	  /** List of assignments */
		assignments: Option[List[Schema.SasPortalAssignment]] = None
	)
	
	case class SasPortalGetPolicyRequest(
	  /** Required. The resource for which the policy is being requested. */
		resource: Option[String] = None
	)
	
	case class SasPortalFrequencyRange(
	  /** The lowest frequency of the frequency range in MHz. */
		lowFrequencyMhz: Option[BigDecimal] = None,
	  /** The highest frequency of the frequency range in MHz. */
		highFrequencyMhz: Option[BigDecimal] = None
	)
	
	case class SasPortalValidateInstallerResponse(
	
	)
	
	case class SasPortalSetupSasAnalyticsResponse(
	
	)
	
	case class SasPortalListNodesResponse(
	  /** The nodes that match the request. */
		nodes: Option[List[Schema.SasPortalNode]] = None,
	  /** A pagination token returned from a previous call to ListNodes that indicates from where listing should continue. If the field is missing or empty, it means there is no more nodes. */
		nextPageToken: Option[String] = None
	)
	
	case class SasPortalGcpProjectDeployment(
	  /** Whether SAS analytics has been enabled. */
		hasEnabledAnalytics: Option[Boolean] = None,
	  /** Deployment associated with the GCP project. */
		deployment: Option[Schema.SasPortalDeployment] = None
	)
	
	case class SasPortalStatus(
	  /** A list of messages that carry the error details. There is a common set of message types for APIs to use. */
		details: Option[List[Map[String, JsValue]]] = None,
	  /** The status code, which should be an enum value of google.rpc.Code. */
		code: Option[Int] = None,
	  /** A developer-facing error message, which should be in English. Any user-facing error message should be localized and sent in the google.rpc.Status.details field, or localized by the client. */
		message: Option[String] = None
	)
	
	case class SasPortalDeviceModel(
	  /** The firmware version of the device. */
		firmwareVersion: Option[String] = None,
	  /** The name of the device model. */
		name: Option[String] = None,
	  /** The name of the device vendor. */
		vendor: Option[String] = None,
	  /** The hardware version of the device. */
		hardwareVersion: Option[String] = None,
	  /** The software version of the device. */
		softwareVersion: Option[String] = None
	)
	
	case class SasPortalAssignment(
	  /** Required. Role that is assigned to `members`. */
		role: Option[String] = None,
	  /** The identities the role is assigned to. It can have the following values: &#42; `{user_email}`: An email address that represents a specific Google account. For example: `alice@gmail.com`. &#42; `{group_email}`: An email address that represents a Google group. For example, `viewers@gmail.com`. */
		members: Option[List[String]] = None
	)
	
	case class SasPortalMoveNodeRequest(
	  /** Required. The name of the new parent resource node or customer to reparent the node under. */
		destination: Option[String] = None
	)
	
	case class SasPortalChannelWithScore(
	  /** The frequency range of the channel. */
		frequencyRange: Option[Schema.SasPortalFrequencyRange] = None,
	  /** The channel score, normalized to be in the range [0,100]. */
		score: Option[BigDecimal] = None
	)
	
	object SasPortalNrqzValidation {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, DRAFT, FINAL }
	}
	case class SasPortalNrqzValidation(
	  /** Device longitude that's associated with the validation. */
		longitude: Option[BigDecimal] = None,
	  /** State of the NRQZ validation info. */
		state: Option[Schema.SasPortalNrqzValidation.StateEnum] = None,
	  /** Validation case ID. */
		caseId: Option[String] = None,
	  /** CPI who signed the validation. */
		cpiId: Option[String] = None,
	  /** Device latitude that's associated with the validation. */
		latitude: Option[BigDecimal] = None
	)
	
	object SasPortalMigrateOrganizationMetadata {
		enum OperationStateEnum extends Enum[OperationStateEnum] { case OPERATION_STATE_UNSPECIFIED, OPERATION_STATE_PENDING, OPERATION_STATE_RUNNING, OPERATION_STATE_SUCCEEDED, OPERATION_STATE_FAILED }
	}
	case class SasPortalMigrateOrganizationMetadata(
	  /** Output only. Current operation state */
		operationState: Option[Schema.SasPortalMigrateOrganizationMetadata.OperationStateEnum] = None
	)
	
	case class SasPortalEmpty(
	
	)
	
	case class SasPortalListDevicesResponse(
	  /** A pagination token returned from a previous call to ListDevices that indicates from where listing should continue. If the field is missing or empty, it means there is no more devices. */
		nextPageToken: Option[String] = None,
	  /** The devices that match the request. */
		devices: Option[List[Schema.SasPortalDevice]] = None
	)
	
	case class SasPortalSetupSasAnalyticsMetadata(
	
	)
	
	case class SasPortalTestPermissionsResponse(
	  /** A set of permissions that the caller is allowed. */
		permissions: Option[List[String]] = None
	)
	
	case class SasPortalSetPolicyRequest(
	  /** Optional. Set the field as `true` to disable the onboarding notification. */
		disableNotification: Option[Boolean] = None,
	  /** Required. The policy to be applied to the `resource`. */
		policy: Option[Schema.SasPortalPolicy] = None,
	  /** Required. The resource for which the policy is being specified. This policy replaces any existing policy. */
		resource: Option[String] = None
	)
	
	case class SasPortalCreateSignedDeviceRequest(
	  /** Required. JSON Web Token signed using a CPI private key. Payload must be the JSON encoding of the device. The user_id field must be set. */
		encodedDevice: Option[String] = None,
	  /** Required. Unique installer id (CPI ID) from the Certified Professional Installers database. */
		installerId: Option[String] = None
	)
	
	case class SasPortalMoveDeviceRequest(
	  /** Required. The name of the new parent resource node or customer to reparent the device under. */
		destination: Option[String] = None
	)
	
	case class SasPortalListLegacyOrganizationsResponse(
	  /** Optional. Legacy SAS organizations. */
		organizations: Option[List[Schema.SasPortalOrganization]] = None
	)
	
	case class SasPortalCustomer(
	  /** User IDs used by the devices belonging to this customer. */
		sasUserIds: Option[List[String]] = None,
	  /** Required. Name of the organization that the customer entity represents. */
		displayName: Option[String] = None,
	  /** Output only. Resource name of the customer. */
		name: Option[String] = None
	)
	
	object SasPortalInstallationParams {
		enum HeightTypeEnum extends Enum[HeightTypeEnum] { case HEIGHT_TYPE_UNSPECIFIED, HEIGHT_TYPE_AGL, HEIGHT_TYPE_AMSL }
	}
	case class SasPortalInstallationParams(
	  /** Boresight direction of the horizontal plane of the antenna in degrees with respect to true north. The value of this parameter is an integer with a value between 0 and 359 inclusive. A value of 0 degrees means true north; a value of 90 degrees means east. This parameter is optional for Category A devices and conditional for Category B devices. */
		antennaAzimuth: Option[Int] = None,
	  /** This parameter is the maximum device EIRP in units of dBm/10MHz and is an integer with a value between -127 and +47 (dBm/10 MHz) inclusive. If not included, SAS interprets it as maximum allowable EIRP in units of dBm/10MHz for device category. */
		eirpCapability: Option[Int] = None,
	  /** A positive number in meters to indicate accuracy of the device antenna vertical location. This optional parameter should only be present if its value is less than the FCC requirement of 3 meters. */
		verticalAccuracy: Option[BigDecimal] = None,
	  /** Device antenna height in meters. When the `heightType` parameter value is "AGL", the antenna height should be given relative to ground level. When the `heightType` parameter value is "AMSL", it is given with respect to WGS84 datum. */
		height: Option[BigDecimal] = None,
	  /** If present, this parameter specifies whether the CBSD is a CPE-CBSD or not. */
		cpeCbsdIndication: Option[Boolean] = None,
	  /** Antenna downtilt in degrees and is an integer with a value between -90 and +90 inclusive; a negative value means the antenna is tilted up (above horizontal). This parameter is optional for Category A devices and conditional for Category B devices. */
		antennaDowntilt: Option[Int] = None,
	  /** Latitude of the device antenna location in degrees relative to the WGS 84 datum. The allowed range is from -90.000000 to +90.000000. Positive values represent latitudes north of the equator; negative values south of the equator. */
		latitude: Option[BigDecimal] = None,
	  /** Specifies how the height is measured. */
		heightType: Option[Schema.SasPortalInstallationParams.HeightTypeEnum] = None,
	  /** Peak antenna gain in dBi. This parameter is a double with a value between -127 and +128 (dBi) inclusive. Part of Release 2 to support floating-point value */
		antennaGain: Option[BigDecimal] = None,
	  /** Whether the device antenna is indoor or not. `true`: indoor. `false`: outdoor. */
		indoorDeployment: Option[Boolean] = None,
	  /** A positive number in meters to indicate accuracy of the device antenna horizontal location. This optional parameter should only be present if its value is less than the FCC requirement of 50 meters. */
		horizontalAccuracy: Option[BigDecimal] = None,
	  /** 3-dB antenna beamwidth of the antenna in the horizontal-plane in degrees. This parameter is an unsigned integer having a value between 0 and 360 (degrees) inclusive; it is optional for Category A devices and conditional for Category B devices. */
		antennaBeamwidth: Option[Int] = None,
	  /** If an external antenna is used, the antenna model is optionally provided in this field. The string has a maximum length of 128 octets. */
		antennaModel: Option[String] = None,
	  /** Longitude of the device antenna location in degrees relative to the WGS 84 datum. The allowed range is from -180.000000 to +180.000000. Positive values represent longitudes east of the prime meridian; negative values west of the prime meridian. */
		longitude: Option[BigDecimal] = None
	)
	
	case class SasPortalValidateInstallerRequest(
	  /** Required. JSON Web Token signed using a CPI private key. Payload must include a "secret" claim whose value is the secret. */
		encodedSecret: Option[String] = None,
	  /** Required. Unique installer id (CPI ID) from the Certified Professional Installers database. */
		installerId: Option[String] = None,
	  /** Required. Secret returned by the GenerateSecret. */
		secret: Option[String] = None
	)
}
