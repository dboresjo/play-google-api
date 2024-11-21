package com.boresjo.play.api.google.transcoder

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	object Job {
		enum StateEnum extends Enum[StateEnum] { case PROCESSING_STATE_UNSPECIFIED, PENDING, RUNNING, SUCCEEDED, FAILED }
		enum ModeEnum extends Enum[ModeEnum] { case PROCESSING_MODE_UNSPECIFIED, PROCESSING_MODE_INTERACTIVE, PROCESSING_MODE_BATCH }
		enum OptimizationEnum extends Enum[OptimizationEnum] { case OPTIMIZATION_STRATEGY_UNSPECIFIED, AUTODETECT, DISABLED }
	}
	case class Job(
	  /** The resource name of the job. Format: `projects/{project_number}/locations/{location}/jobs/{job}` */
		name: Option[String] = None,
	  /** Input only. Specify the `input_uri` to populate empty `uri` fields in each element of `Job.config.inputs` or `JobTemplate.config.inputs` when using template. URI of the media. Input files must be at least 5 seconds in duration and stored in Cloud Storage (for example, `gs://bucket/inputs/file.mp4`). See [Supported input and output formats](https://cloud.google.com/transcoder/docs/concepts/supported-input-and-output-formats). */
		inputUri: Option[String] = None,
	  /** Input only. Specify the `output_uri` to populate an empty `Job.config.output.uri` or `JobTemplate.config.output.uri` when using template. URI for the output file(s). For example, `gs://my-bucket/outputs/`. See [Supported input and output formats](https://cloud.google.com/transcoder/docs/concepts/supported-input-and-output-formats). */
		outputUri: Option[String] = None,
	  /** Input only. Specify the `template_id` to use for populating `Job.config`. The default is `preset/web-hd`, which is the only supported preset. User defined JobTemplate: `{job_template_id}` */
		templateId: Option[String] = None,
	  /** The configuration for this job. */
		config: Option[Schema.JobConfig] = None,
	  /** Output only. The current state of the job. */
		state: Option[Schema.Job.StateEnum] = None,
	  /** Output only. The time the job was created. */
		createTime: Option[String] = None,
	  /** Output only. The time the transcoding started. */
		startTime: Option[String] = None,
	  /** Output only. The time the transcoding finished. */
		endTime: Option[String] = None,
	  /** Job time to live value in days, which will be effective after job completion. Job should be deleted automatically after the given TTL. Enter a value between 1 and 90. The default is 30. */
		ttlAfterCompletionDays: Option[Int] = None,
	  /** The labels associated with this job. You can use these to organize and group your jobs. */
		labels: Option[Map[String, String]] = None,
	  /** Output only. An error object that describes the reason for the failure. This property is always present when ProcessingState is `FAILED`. */
		error: Option[Schema.Status] = None,
	  /** The processing mode of the job. The default is `PROCESSING_MODE_INTERACTIVE`. */
		mode: Option[Schema.Job.ModeEnum] = None,
	  /** The processing priority of a batch job. This field can only be set for batch mode jobs. The default value is 0. This value cannot be negative. Higher values correspond to higher priorities for the job. */
		batchModePriority: Option[Int] = None,
	  /** Optional. The optimization strategy of the job. The default is `AUTODETECT`. */
		optimization: Option[Schema.Job.OptimizationEnum] = None
	)
	
	case class JobConfig(
	  /** List of input assets stored in Cloud Storage. */
		inputs: Option[List[Schema.Input]] = None,
	  /** List of edit atoms. Defines the ultimate timeline of the resulting file or manifest. */
		editList: Option[List[Schema.EditAtom]] = None,
	  /** List of elementary streams. */
		elementaryStreams: Option[List[Schema.ElementaryStream]] = None,
	  /** List of multiplexing settings for output streams. */
		muxStreams: Option[List[Schema.MuxStream]] = None,
	  /** List of output manifests. */
		manifests: Option[List[Schema.Manifest]] = None,
	  /** Output configuration. */
		output: Option[Schema.Output] = None,
	  /** List of ad breaks. Specifies where to insert ad break tags in the output manifests. */
		adBreaks: Option[List[Schema.AdBreak]] = None,
	  /** Destination on Pub/Sub. */
		pubsubDestination: Option[Schema.PubsubDestination] = None,
	  /** List of output sprite sheets. Spritesheets require at least one VideoStream in the Jobconfig. */
		spriteSheets: Option[List[Schema.SpriteSheet]] = None,
	  /** List of overlays on the output video, in descending Z-order. */
		overlays: Option[List[Schema.Overlay]] = None,
	  /** List of encryption configurations for the content. Each configuration has an ID. Specify this ID in the MuxStream.encryption_id field to indicate the configuration to use for that `MuxStream` output. */
		encryptions: Option[List[Schema.Encryption]] = None
	)
	
	case class Input(
	  /** A unique key for this input. Must be specified when using advanced mapping and edit lists. */
		key: Option[String] = None,
	  /** URI of the media. Input files must be at least 5 seconds in duration and stored in Cloud Storage (for example, `gs://bucket/inputs/file.mp4`). If empty, the value is populated from Job.input_uri. See [Supported input and output formats](https://cloud.google.com/transcoder/docs/concepts/supported-input-and-output-formats). */
		uri: Option[String] = None,
	  /** Preprocessing configurations. */
		preprocessingConfig: Option[Schema.PreprocessingConfig] = None
	)
	
	case class PreprocessingConfig(
	  /** Color preprocessing configuration. */
		color: Option[Schema.Color] = None,
	  /** Denoise preprocessing configuration. */
		denoise: Option[Schema.Denoise] = None,
	  /** Deblock preprocessing configuration. */
		deblock: Option[Schema.Deblock] = None,
	  /** Audio preprocessing configuration. */
		audio: Option[Schema.Audio] = None,
	  /** Specify the video cropping configuration. */
		crop: Option[Schema.Crop] = None,
	  /** Specify the video pad filter configuration. */
		pad: Option[Schema.Pad] = None,
	  /** Specify the video deinterlace configuration. */
		deinterlace: Option[Schema.Deinterlace] = None
	)
	
	case class Color(
	  /** Control color saturation of the video. Enter a value between -1 and 1, where -1 is fully desaturated and 1 is maximum saturation. 0 is no change. The default is 0. */
		saturation: Option[BigDecimal] = None,
	  /** Control black and white contrast of the video. Enter a value between -1 and 1, where -1 is minimum contrast and 1 is maximum contrast. 0 is no change. The default is 0. */
		contrast: Option[BigDecimal] = None,
	  /** Control brightness of the video. Enter a value between -1 and 1, where -1 is minimum brightness and 1 is maximum brightness. 0 is no change. The default is 0. */
		brightness: Option[BigDecimal] = None
	)
	
	case class Denoise(
	  /** Set strength of the denoise. Enter a value between 0 and 1. The higher the value, the smoother the image. 0 is no denoising. The default is 0. */
		strength: Option[BigDecimal] = None,
	  /** Set the denoiser mode. The default is `standard`. Supported denoiser modes: - `standard` - `grain` */
		tune: Option[String] = None
	)
	
	case class Deblock(
	  /** Set strength of the deblocker. Enter a value between 0 and 1. The higher the value, the stronger the block removal. 0 is no deblocking. The default is 0. */
		strength: Option[BigDecimal] = None,
	  /** Enable deblocker. The default is `false`. */
		enabled: Option[Boolean] = None
	)
	
	case class Audio(
	  /** Specify audio loudness normalization in loudness units relative to full scale (LUFS). Enter a value between -24 and 0 (the default), where: &#42; -24 is the Advanced Television Systems Committee (ATSC A/85) standard &#42; -23 is the EU R128 broadcast standard &#42; -19 is the prior standard for online mono audio &#42; -18 is the ReplayGain standard &#42; -16 is the prior standard for stereo audio &#42; -14 is the new online audio standard recommended by Spotify, as well as Amazon Echo &#42; 0 disables normalization */
		lufs: Option[BigDecimal] = None,
	  /** Enable boosting high frequency components. The default is `false`. &#42;&#42;Note:&#42;&#42; This field is not supported. */
		highBoost: Option[Boolean] = None,
	  /** Enable boosting low frequency components. The default is `false`. &#42;&#42;Note:&#42;&#42; This field is not supported. */
		lowBoost: Option[Boolean] = None
	)
	
	case class Crop(
	  /** The number of pixels to crop from the top. The default is 0. */
		topPixels: Option[Int] = None,
	  /** The number of pixels to crop from the bottom. The default is 0. */
		bottomPixels: Option[Int] = None,
	  /** The number of pixels to crop from the left. The default is 0. */
		leftPixels: Option[Int] = None,
	  /** The number of pixels to crop from the right. The default is 0. */
		rightPixels: Option[Int] = None
	)
	
	case class Pad(
	  /** The number of pixels to add to the top. The default is 0. */
		topPixels: Option[Int] = None,
	  /** The number of pixels to add to the bottom. The default is 0. */
		bottomPixels: Option[Int] = None,
	  /** The number of pixels to add to the left. The default is 0. */
		leftPixels: Option[Int] = None,
	  /** The number of pixels to add to the right. The default is 0. */
		rightPixels: Option[Int] = None
	)
	
	case class Deinterlace(
	  /** Specifies the Yet Another Deinterlacing Filter Configuration. */
		yadif: Option[Schema.YadifConfig] = None,
	  /** Specifies the Bob Weaver Deinterlacing Filter Configuration. */
		bwdif: Option[Schema.BwdifConfig] = None
	)
	
	case class YadifConfig(
	  /** Specifies the deinterlacing mode to adopt. The default is `send_frame`. Supported values: - `send_frame`: Output one frame for each frame - `send_field`: Output one frame for each field */
		mode: Option[String] = None,
	  /** Disable spacial interlacing. The default is `false`. */
		disableSpatialInterlacing: Option[Boolean] = None,
	  /** The picture field parity assumed for the input interlaced video. The default is `auto`. Supported values: - `tff`: Assume the top field is first - `bff`: Assume the bottom field is first - `auto`: Enable automatic detection of field parity */
		parity: Option[String] = None,
	  /** Deinterlace all frames rather than just the frames identified as interlaced. The default is `false`. */
		deinterlaceAllFrames: Option[Boolean] = None
	)
	
	case class BwdifConfig(
	  /** Specifies the deinterlacing mode to adopt. The default is `send_frame`. Supported values: - `send_frame`: Output one frame for each frame - `send_field`: Output one frame for each field */
		mode: Option[String] = None,
	  /** The picture field parity assumed for the input interlaced video. The default is `auto`. Supported values: - `tff`: Assume the top field is first - `bff`: Assume the bottom field is first - `auto`: Enable automatic detection of field parity */
		parity: Option[String] = None,
	  /** Deinterlace all frames rather than just the frames identified as interlaced. The default is `false`. */
		deinterlaceAllFrames: Option[Boolean] = None
	)
	
	case class EditAtom(
	  /** A unique key for this atom. Must be specified when using advanced mapping. */
		key: Option[String] = None,
	  /** List of Input.key values identifying files that should be used in this atom. The listed `inputs` must have the same timeline. */
		inputs: Option[List[String]] = None,
	  /** End time in seconds for the atom, relative to the input file timeline. When `end_time_offset` is not specified, the `inputs` are used until the end of the atom. */
		endTimeOffset: Option[String] = None,
	  /** Start time in seconds for the atom, relative to the input file timeline. The default is `0s`. */
		startTimeOffset: Option[String] = None
	)
	
	case class ElementaryStream(
	  /** A unique key for this elementary stream. */
		key: Option[String] = None,
	  /** Encoding of a video stream. */
		videoStream: Option[Schema.VideoStream] = None,
	  /** Encoding of an audio stream. */
		audioStream: Option[Schema.AudioStream] = None,
	  /** Encoding of a text stream. For example, closed captions or subtitles. */
		textStream: Option[Schema.TextStream] = None
	)
	
	case class VideoStream(
	  /** H264 codec settings. */
		h264: Option[Schema.H264CodecSettings] = None,
	  /** H265 codec settings. */
		h265: Option[Schema.H265CodecSettings] = None,
	  /** VP9 codec settings. */
		vp9: Option[Schema.Vp9CodecSettings] = None
	)
	
	object H264CodecSettings {
		enum FrameRateConversionStrategyEnum extends Enum[FrameRateConversionStrategyEnum] { case FRAME_RATE_CONVERSION_STRATEGY_UNSPECIFIED, DOWNSAMPLE, DROP_DUPLICATE }
	}
	case class H264CodecSettings(
	  /** The width of the video in pixels. Must be an even integer. When not specified, the width is adjusted to match the specified height and input aspect ratio. If both are omitted, the input width is used. For portrait videos that contain horizontal ASR and rotation metadata, provide the width, in pixels, per the horizontal ASR. The API calculates the height per the horizontal ASR. The API detects any rotation metadata and swaps the requested height and width for the output. */
		widthPixels: Option[Int] = None,
	  /** The height of the video in pixels. Must be an even integer. When not specified, the height is adjusted to match the specified width and input aspect ratio. If both are omitted, the input height is used. For portrait videos that contain horizontal ASR and rotation metadata, provide the height, in pixels, per the horizontal ASR. The API calculates the width per the horizontal ASR. The API detects any rotation metadata and swaps the requested height and width for the output. */
		heightPixels: Option[Int] = None,
	  /** Required. The target video frame rate in frames per second (FPS). Must be less than or equal to 120. */
		frameRate: Option[BigDecimal] = None,
	  /** Optional. Frame rate conversion strategy for desired frame rate. The default is `DOWNSAMPLE`. */
		frameRateConversionStrategy: Option[Schema.H264CodecSettings.FrameRateConversionStrategyEnum] = None,
	  /** Required. The video bitrate in bits per second. The minimum value is 1,000. The maximum value is 800,000,000. */
		bitrateBps: Option[Int] = None,
	  /** Pixel format to use. The default is `yuv420p`. Supported pixel formats: - `yuv420p` pixel format - `yuv422p` pixel format - `yuv444p` pixel format - `yuv420p10` 10-bit HDR pixel format - `yuv422p10` 10-bit HDR pixel format - `yuv444p10` 10-bit HDR pixel format - `yuv420p12` 12-bit HDR pixel format - `yuv422p12` 12-bit HDR pixel format - `yuv444p12` 12-bit HDR pixel format */
		pixelFormat: Option[String] = None,
	  /** Specify the mode. The default is `vbr`. Supported rate control modes: - `vbr` - variable bitrate - `crf` - constant rate factor */
		rateControlMode: Option[String] = None,
	  /** Target CRF level. Must be between 10 and 36, where 10 is the highest quality and 36 is the most efficient compression. The default is 21. */
		crfLevel: Option[Int] = None,
	  /** Specifies whether an open Group of Pictures (GOP) structure should be allowed or not. The default is `false`. */
		allowOpenGop: Option[Boolean] = None,
	  /** Select the GOP size based on the specified frame count. Must be greater than zero. */
		gopFrameCount: Option[Int] = None,
	  /** Select the GOP size based on the specified duration. The default is `3s`. Note that `gopDuration` must be less than or equal to [`segmentDuration`](#SegmentSettings), and [`segmentDuration`](#SegmentSettings) must be divisible by `gopDuration`. */
		gopDuration: Option[String] = None,
	  /** Use two-pass encoding strategy to achieve better video quality. H264CodecSettings.rate_control_mode must be `vbr`. The default is `false`. */
		enableTwoPass: Option[Boolean] = None,
	  /** Size of the Video Buffering Verifier (VBV) buffer in bits. Must be greater than zero. The default is equal to H264CodecSettings.bitrate_bps. */
		vbvSizeBits: Option[Int] = None,
	  /** Initial fullness of the Video Buffering Verifier (VBV) buffer in bits. Must be greater than zero. The default is equal to 90% of H264CodecSettings.vbv_size_bits. */
		vbvFullnessBits: Option[Int] = None,
	  /** The entropy coder to use. The default is `cabac`. Supported entropy coders: - `cavlc` - `cabac` */
		entropyCoder: Option[String] = None,
	  /** Allow B-pyramid for reference frame selection. This may not be supported on all decoders. The default is `false`. */
		bPyramid: Option[Boolean] = None,
	  /** The number of consecutive B-frames. Must be greater than or equal to zero. Must be less than H264CodecSettings.gop_frame_count if set. The default is 0. */
		bFrameCount: Option[Int] = None,
	  /** Specify the intensity of the adaptive quantizer (AQ). Must be between 0 and 1, where 0 disables the quantizer and 1 maximizes the quantizer. A higher value equals a lower bitrate but smoother image. The default is 0. */
		aqStrength: Option[BigDecimal] = None,
	  /** Enforces the specified codec profile. The following profiles are supported: &#42; `baseline` &#42; `main` &#42; `high` (default) The available options are [FFmpeg-compatible](https://trac.ffmpeg.org/wiki/Encode/H.264#Tune). Note that certain values for this field may cause the transcoder to override other fields you set in the `H264CodecSettings` message. */
		profile: Option[String] = None,
	  /** Enforces the specified codec tune. The available options are [FFmpeg-compatible](https://trac.ffmpeg.org/wiki/Encode/H.264#Tune). Note that certain values for this field may cause the transcoder to override other fields you set in the `H264CodecSettings` message. */
		tune: Option[String] = None,
	  /** Enforces the specified codec preset. The default is `veryfast`. The available options are [FFmpeg-compatible](https://trac.ffmpeg.org/wiki/Encode/H.264#Preset). Note that certain values for this field may cause the transcoder to override other fields you set in the `H264CodecSettings` message. */
		preset: Option[String] = None,
	  /** Optional. SDR color format setting for H264. */
		sdr: Option[Schema.H264ColorFormatSDR] = None,
	  /** Optional. HLG color format setting for H264. */
		hlg: Option[Schema.H264ColorFormatHLG] = None
	)
	
	case class H264ColorFormatSDR(
	
	)
	
	case class H264ColorFormatHLG(
	
	)
	
	object H265CodecSettings {
		enum FrameRateConversionStrategyEnum extends Enum[FrameRateConversionStrategyEnum] { case FRAME_RATE_CONVERSION_STRATEGY_UNSPECIFIED, DOWNSAMPLE, DROP_DUPLICATE }
	}
	case class H265CodecSettings(
	  /** The width of the video in pixels. Must be an even integer. When not specified, the width is adjusted to match the specified height and input aspect ratio. If both are omitted, the input width is used. For portrait videos that contain horizontal ASR and rotation metadata, provide the width, in pixels, per the horizontal ASR. The API calculates the height per the horizontal ASR. The API detects any rotation metadata and swaps the requested height and width for the output. */
		widthPixels: Option[Int] = None,
	  /** The height of the video in pixels. Must be an even integer. When not specified, the height is adjusted to match the specified width and input aspect ratio. If both are omitted, the input height is used. For portrait videos that contain horizontal ASR and rotation metadata, provide the height, in pixels, per the horizontal ASR. The API calculates the width per the horizontal ASR. The API detects any rotation metadata and swaps the requested height and width for the output. */
		heightPixels: Option[Int] = None,
	  /** Required. The target video frame rate in frames per second (FPS). Must be less than or equal to 120. */
		frameRate: Option[BigDecimal] = None,
	  /** Optional. Frame rate conversion strategy for desired frame rate. The default is `DOWNSAMPLE`. */
		frameRateConversionStrategy: Option[Schema.H265CodecSettings.FrameRateConversionStrategyEnum] = None,
	  /** Required. The video bitrate in bits per second. The minimum value is 1,000. The maximum value is 800,000,000. */
		bitrateBps: Option[Int] = None,
	  /** Pixel format to use. The default is `yuv420p`. Supported pixel formats: - `yuv420p` pixel format - `yuv422p` pixel format - `yuv444p` pixel format - `yuv420p10` 10-bit HDR pixel format - `yuv422p10` 10-bit HDR pixel format - `yuv444p10` 10-bit HDR pixel format - `yuv420p12` 12-bit HDR pixel format - `yuv422p12` 12-bit HDR pixel format - `yuv444p12` 12-bit HDR pixel format */
		pixelFormat: Option[String] = None,
	  /** Specify the mode. The default is `vbr`. Supported rate control modes: - `vbr` - variable bitrate - `crf` - constant rate factor */
		rateControlMode: Option[String] = None,
	  /** Target CRF level. Must be between 10 and 36, where 10 is the highest quality and 36 is the most efficient compression. The default is 21. */
		crfLevel: Option[Int] = None,
	  /** Specifies whether an open Group of Pictures (GOP) structure should be allowed or not. The default is `false`. */
		allowOpenGop: Option[Boolean] = None,
	  /** Select the GOP size based on the specified frame count. Must be greater than zero. */
		gopFrameCount: Option[Int] = None,
	  /** Select the GOP size based on the specified duration. The default is `3s`. Note that `gopDuration` must be less than or equal to [`segmentDuration`](#SegmentSettings), and [`segmentDuration`](#SegmentSettings) must be divisible by `gopDuration`. */
		gopDuration: Option[String] = None,
	  /** Use two-pass encoding strategy to achieve better video quality. H265CodecSettings.rate_control_mode must be `vbr`. The default is `false`. */
		enableTwoPass: Option[Boolean] = None,
	  /** Size of the Video Buffering Verifier (VBV) buffer in bits. Must be greater than zero. The default is equal to `VideoStream.bitrate_bps`. */
		vbvSizeBits: Option[Int] = None,
	  /** Initial fullness of the Video Buffering Verifier (VBV) buffer in bits. Must be greater than zero. The default is equal to 90% of H265CodecSettings.vbv_size_bits. */
		vbvFullnessBits: Option[Int] = None,
	  /** Allow B-pyramid for reference frame selection. This may not be supported on all decoders. The default is `false`. */
		bPyramid: Option[Boolean] = None,
	  /** The number of consecutive B-frames. Must be greater than or equal to zero. Must be less than H265CodecSettings.gop_frame_count if set. The default is 0. */
		bFrameCount: Option[Int] = None,
	  /** Specify the intensity of the adaptive quantizer (AQ). Must be between 0 and 1, where 0 disables the quantizer and 1 maximizes the quantizer. A higher value equals a lower bitrate but smoother image. The default is 0. */
		aqStrength: Option[BigDecimal] = None,
	  /** Enforces the specified codec profile. The following profiles are supported: &#42; 8-bit profiles &#42; `main` (default) &#42; `main-intra` &#42; `mainstillpicture` &#42; 10-bit profiles &#42; `main10` (default) &#42; `main10-intra` &#42; `main422-10` &#42; `main422-10-intra` &#42; `main444-10` &#42; `main444-10-intra` &#42; 12-bit profiles &#42; `main12` (default) &#42; `main12-intra` &#42; `main422-12` &#42; `main422-12-intra` &#42; `main444-12` &#42; `main444-12-intra` The available options are [FFmpeg-compatible](https://x265.readthedocs.io/). Note that certain values for this field may cause the transcoder to override other fields you set in the `H265CodecSettings` message. */
		profile: Option[String] = None,
	  /** Enforces the specified codec tune. The available options are [FFmpeg-compatible](https://trac.ffmpeg.org/wiki/Encode/H.265). Note that certain values for this field may cause the transcoder to override other fields you set in the `H265CodecSettings` message. */
		tune: Option[String] = None,
	  /** Enforces the specified codec preset. The default is `veryfast`. The available options are [FFmpeg-compatible](https://trac.ffmpeg.org/wiki/Encode/H.265). Note that certain values for this field may cause the transcoder to override other fields you set in the `H265CodecSettings` message. */
		preset: Option[String] = None,
	  /** Optional. SDR color format setting for H265. */
		sdr: Option[Schema.H265ColorFormatSDR] = None,
	  /** Optional. HLG color format setting for H265. */
		hlg: Option[Schema.H265ColorFormatHLG] = None,
	  /** Optional. HDR10 color format setting for H265. */
		hdr10: Option[Schema.H265ColorFormatHDR10] = None
	)
	
	case class H265ColorFormatSDR(
	
	)
	
	case class H265ColorFormatHLG(
	
	)
	
	case class H265ColorFormatHDR10(
	
	)
	
	object Vp9CodecSettings {
		enum FrameRateConversionStrategyEnum extends Enum[FrameRateConversionStrategyEnum] { case FRAME_RATE_CONVERSION_STRATEGY_UNSPECIFIED, DOWNSAMPLE, DROP_DUPLICATE }
	}
	case class Vp9CodecSettings(
	  /** The width of the video in pixels. Must be an even integer. When not specified, the width is adjusted to match the specified height and input aspect ratio. If both are omitted, the input width is used. For portrait videos that contain horizontal ASR and rotation metadata, provide the width, in pixels, per the horizontal ASR. The API calculates the height per the horizontal ASR. The API detects any rotation metadata and swaps the requested height and width for the output. */
		widthPixels: Option[Int] = None,
	  /** The height of the video in pixels. Must be an even integer. When not specified, the height is adjusted to match the specified width and input aspect ratio. If both are omitted, the input height is used. For portrait videos that contain horizontal ASR and rotation metadata, provide the height, in pixels, per the horizontal ASR. The API calculates the width per the horizontal ASR. The API detects any rotation metadata and swaps the requested height and width for the output. */
		heightPixels: Option[Int] = None,
	  /** Required. The target video frame rate in frames per second (FPS). Must be less than or equal to 120. */
		frameRate: Option[BigDecimal] = None,
	  /** Optional. Frame rate conversion strategy for desired frame rate. The default is `DOWNSAMPLE`. */
		frameRateConversionStrategy: Option[Schema.Vp9CodecSettings.FrameRateConversionStrategyEnum] = None,
	  /** Required. The video bitrate in bits per second. The minimum value is 1,000. The maximum value is 480,000,000. */
		bitrateBps: Option[Int] = None,
	  /** Pixel format to use. The default is `yuv420p`. Supported pixel formats: - `yuv420p` pixel format - `yuv422p` pixel format - `yuv444p` pixel format - `yuv420p10` 10-bit HDR pixel format - `yuv422p10` 10-bit HDR pixel format - `yuv444p10` 10-bit HDR pixel format - `yuv420p12` 12-bit HDR pixel format - `yuv422p12` 12-bit HDR pixel format - `yuv444p12` 12-bit HDR pixel format */
		pixelFormat: Option[String] = None,
	  /** Specify the mode. The default is `vbr`. Supported rate control modes: - `vbr` - variable bitrate */
		rateControlMode: Option[String] = None,
	  /** Target CRF level. Must be between 10 and 36, where 10 is the highest quality and 36 is the most efficient compression. The default is 21. &#42;&#42;Note:&#42;&#42; This field is not supported. */
		crfLevel: Option[Int] = None,
	  /** Select the GOP size based on the specified frame count. Must be greater than zero. */
		gopFrameCount: Option[Int] = None,
	  /** Select the GOP size based on the specified duration. The default is `3s`. Note that `gopDuration` must be less than or equal to [`segmentDuration`](#SegmentSettings), and [`segmentDuration`](#SegmentSettings) must be divisible by `gopDuration`. */
		gopDuration: Option[String] = None,
	  /** Enforces the specified codec profile. The following profiles are supported: &#42; `profile0` (default) &#42; `profile1` &#42; `profile2` &#42; `profile3` The available options are [WebM-compatible](https://www.webmproject.org/vp9/profiles/). Note that certain values for this field may cause the transcoder to override other fields you set in the `Vp9CodecSettings` message. */
		profile: Option[String] = None,
	  /** Optional. SDR color format setting for VP9. */
		sdr: Option[Schema.Vp9ColorFormatSDR] = None,
	  /** Optional. HLG color format setting for VP9. */
		hlg: Option[Schema.Vp9ColorFormatHLG] = None
	)
	
	case class Vp9ColorFormatSDR(
	
	)
	
	case class Vp9ColorFormatHLG(
	
	)
	
	case class AudioStream(
	  /** The codec for this audio stream. The default is `aac`. Supported audio codecs: - `aac` - `aac-he` - `aac-he-v2` - `mp3` - `ac3` - `eac3` - `vorbis` */
		codec: Option[String] = None,
	  /** Required. Audio bitrate in bits per second. Must be between 1 and 10,000,000. */
		bitrateBps: Option[Int] = None,
	  /** Number of audio channels. Must be between 1 and 6. The default is 2. */
		channelCount: Option[Int] = None,
	  /** A list of channel names specifying layout of the audio channels. This only affects the metadata embedded in the container headers, if supported by the specified format. The default is `["fl", "fr"]`. Supported channel names: - `fl` - Front left channel - `fr` - Front right channel - `sl` - Side left channel - `sr` - Side right channel - `fc` - Front center channel - `lfe` - Low frequency */
		channelLayout: Option[List[String]] = None,
	  /** The mapping for the JobConfig.edit_list atoms with audio EditAtom.inputs. */
		mapping: Option[List[Schema.AudioMapping]] = None,
	  /** The audio sample rate in Hertz. The default is 48000 Hertz. */
		sampleRateHertz: Option[Int] = None,
	  /** The BCP-47 language code, such as `en-US` or `sr-Latn`. For more information, see https://www.unicode.org/reports/tr35/#Unicode_locale_identifier. Not supported in MP4 files. */
		languageCode: Option[String] = None,
	  /** The name for this particular audio stream that will be added to the HLS/DASH manifest. Not supported in MP4 files. */
		displayName: Option[String] = None
	)
	
	case class AudioMapping(
	  /** Required. The EditAtom.key that references the atom with audio inputs in the JobConfig.edit_list. */
		atomKey: Option[String] = None,
	  /** Required. The Input.key that identifies the input file. */
		inputKey: Option[String] = None,
	  /** Required. The zero-based index of the track in the input file. */
		inputTrack: Option[Int] = None,
	  /** Required. The zero-based index of the channel in the input audio stream. */
		inputChannel: Option[Int] = None,
	  /** Required. The zero-based index of the channel in the output audio stream. */
		outputChannel: Option[Int] = None,
	  /** Audio volume control in dB. Negative values decrease volume, positive values increase. The default is 0. */
		gainDb: Option[BigDecimal] = None
	)
	
	case class TextStream(
	  /** The codec for this text stream. The default is `webvtt`. Supported text codecs: - `srt` - `ttml` - `cea608` - `cea708` - `webvtt` */
		codec: Option[String] = None,
	  /** The BCP-47 language code, such as `en-US` or `sr-Latn`. For more information, see https://www.unicode.org/reports/tr35/#Unicode_locale_identifier. Not supported in MP4 files. */
		languageCode: Option[String] = None,
	  /** The mapping for the JobConfig.edit_list atoms with text EditAtom.inputs. */
		mapping: Option[List[Schema.TextMapping]] = None,
	  /** The name for this particular text stream that will be added to the HLS/DASH manifest. Not supported in MP4 files. */
		displayName: Option[String] = None
	)
	
	case class TextMapping(
	  /** Required. The EditAtom.key that references atom with text inputs in the JobConfig.edit_list. */
		atomKey: Option[String] = None,
	  /** Required. The Input.key that identifies the input file. */
		inputKey: Option[String] = None,
	  /** Required. The zero-based index of the track in the input file. */
		inputTrack: Option[Int] = None
	)
	
	case class MuxStream(
	  /** A unique key for this multiplexed stream. */
		key: Option[String] = None,
	  /** The name of the generated file. The default is MuxStream.key with the extension suffix corresponding to the MuxStream.container. Individual segments also have an incremental 10-digit zero-padded suffix starting from 0 before the extension, such as `mux_stream0000000123.ts`. */
		fileName: Option[String] = None,
	  /** The container format. The default is `mp4` Supported container formats: - `ts` - `fmp4`- the corresponding file extension is `.m4s` - `mp4` - `vtt` - `ogg` See also: [Supported input and output formats](https://cloud.google.com/transcoder/docs/concepts/supported-input-and-output-formats) */
		container: Option[String] = None,
	  /** List of ElementaryStream.key values multiplexed in this stream. */
		elementaryStreams: Option[List[String]] = None,
	  /** Segment settings for `ts`, `fmp4` and `vtt`. */
		segmentSettings: Option[Schema.SegmentSettings] = None,
	  /** Identifier of the encryption configuration to use. If omitted, output will be unencrypted. */
		encryptionId: Option[String] = None,
	  /** Optional. `fmp4` container configuration. */
		fmp4: Option[Schema.Fmp4Config] = None
	)
	
	case class SegmentSettings(
	  /** Duration of the segments in seconds. The default is `6.0s`. Note that `segmentDuration` must be greater than or equal to [`gopDuration`](#videostream), and `segmentDuration` must be divisible by [`gopDuration`](#videostream). */
		segmentDuration: Option[String] = None,
	  /** Required. Create an individual segment file. The default is `false`. */
		individualSegments: Option[Boolean] = None
	)
	
	case class Fmp4Config(
	  /** Optional. Specify the codec tag string that will be used in the media bitstream. When not specified, the codec appropriate value is used. Supported H265 codec tags: - `hvc1` (default) - `hev1` */
		codecTag: Option[String] = None
	)
	
	object Manifest {
		enum TypeEnum extends Enum[TypeEnum] { case MANIFEST_TYPE_UNSPECIFIED, HLS, DASH }
	}
	case class Manifest(
	  /** The name of the generated file. The default is `manifest` with the extension suffix corresponding to the Manifest.type. */
		fileName: Option[String] = None,
	  /** Required. Type of the manifest. */
		`type`: Option[Schema.Manifest.TypeEnum] = None,
	  /** Required. List of user supplied MuxStream.key values that should appear in this manifest. When Manifest.type is `HLS`, a media manifest with name MuxStream.key and `.m3u8` extension is generated for each element in this list. */
		muxStreams: Option[List[String]] = None,
	  /** `DASH` manifest configuration. */
		dash: Option[Schema.DashConfig] = None
	)
	
	object DashConfig {
		enum SegmentReferenceSchemeEnum extends Enum[SegmentReferenceSchemeEnum] { case SEGMENT_REFERENCE_SCHEME_UNSPECIFIED, SEGMENT_LIST, SEGMENT_TEMPLATE_NUMBER }
	}
	case class DashConfig(
	  /** The segment reference scheme for a `DASH` manifest. The default is `SEGMENT_LIST`. */
		segmentReferenceScheme: Option[Schema.DashConfig.SegmentReferenceSchemeEnum] = None
	)
	
	case class Output(
	  /** URI for the output file(s). For example, `gs://my-bucket/outputs/`. Must be a directory and not a top-level bucket. If empty, the value is populated from Job.output_uri. See [Supported input and output formats](https://cloud.google.com/transcoder/docs/concepts/supported-input-and-output-formats). */
		uri: Option[String] = None
	)
	
	case class AdBreak(
	  /** Start time in seconds for the ad break, relative to the output file timeline. The default is `0s`. */
		startTimeOffset: Option[String] = None
	)
	
	case class PubsubDestination(
	  /** The name of the Pub/Sub topic to publish job completion notification to. For example: `projects/{project}/topics/{topic}`. */
		topic: Option[String] = None
	)
	
	case class SpriteSheet(
	  /** Format type. The default is `jpeg`. Supported formats: - `jpeg` */
		format: Option[String] = None,
	  /** Required. File name prefix for the generated sprite sheets. Each sprite sheet has an incremental 10-digit zero-padded suffix starting from 0 before the extension, such as `sprite_sheet0000000123.jpeg`. */
		filePrefix: Option[String] = None,
	  /** Required. The width of sprite in pixels. Must be an even integer. To preserve the source aspect ratio, set the SpriteSheet.sprite_width_pixels field or the SpriteSheet.sprite_height_pixels field, but not both (the API will automatically calculate the missing field). For portrait videos that contain horizontal ASR and rotation metadata, provide the width, in pixels, per the horizontal ASR. The API calculates the height per the horizontal ASR. The API detects any rotation metadata and swaps the requested height and width for the output. */
		spriteWidthPixels: Option[Int] = None,
	  /** Required. The height of sprite in pixels. Must be an even integer. To preserve the source aspect ratio, set the SpriteSheet.sprite_height_pixels field or the SpriteSheet.sprite_width_pixels field, but not both (the API will automatically calculate the missing field). For portrait videos that contain horizontal ASR and rotation metadata, provide the height, in pixels, per the horizontal ASR. The API calculates the width per the horizontal ASR. The API detects any rotation metadata and swaps the requested height and width for the output. */
		spriteHeightPixels: Option[Int] = None,
	  /** The maximum number of sprites per row in a sprite sheet. The default is 0, which indicates no maximum limit. */
		columnCount: Option[Int] = None,
	  /** The maximum number of rows per sprite sheet. When the sprite sheet is full, a new sprite sheet is created. The default is 0, which indicates no maximum limit. */
		rowCount: Option[Int] = None,
	  /** Start time in seconds, relative to the output file timeline. Determines the first sprite to pick. The default is `0s`. */
		startTimeOffset: Option[String] = None,
	  /** End time in seconds, relative to the output file timeline. When `end_time_offset` is not specified, the sprites are generated until the end of the output file. */
		endTimeOffset: Option[String] = None,
	  /** Total number of sprites. Create the specified number of sprites distributed evenly across the timeline of the output media. The default is 100. */
		totalCount: Option[Int] = None,
	  /** Starting from `0s`, create sprites at regular intervals. Specify the interval value in seconds. */
		interval: Option[String] = None,
	  /** The quality of the generated sprite sheet. Enter a value between 1 and 100, where 1 is the lowest quality and 100 is the highest quality. The default is 100. A high quality value corresponds to a low image data compression ratio. */
		quality: Option[Int] = None
	)
	
	case class Overlay(
	  /** Image overlay. */
		image: Option[Schema.Image] = None,
	  /** List of animations. The list should be chronological, without any time overlap. */
		animations: Option[List[Schema.Animation]] = None
	)
	
	case class Image(
	  /** Required. URI of the image in Cloud Storage. For example, `gs://bucket/inputs/image.png`. Only PNG and JPEG images are supported. */
		uri: Option[String] = None,
	  /** Normalized image resolution, based on output video resolution. Valid values: `0.0`–`1.0`. To respect the original image aspect ratio, set either `x` or `y` to `0.0`. To use the original image resolution, set both `x` and `y` to `0.0`. */
		resolution: Option[Schema.NormalizedCoordinate] = None,
	  /** Target image opacity. Valid values are from `1.0` (solid, default) to `0.0` (transparent), exclusive. Set this to a value greater than `0.0`. */
		alpha: Option[BigDecimal] = None
	)
	
	case class NormalizedCoordinate(
	  /** Normalized x coordinate. */
		x: Option[BigDecimal] = None,
	  /** Normalized y coordinate. */
		y: Option[BigDecimal] = None
	)
	
	case class Animation(
	  /** Display static overlay object. */
		animationStatic: Option[Schema.AnimationStatic] = None,
	  /** Display overlay object with fade animation. */
		animationFade: Option[Schema.AnimationFade] = None,
	  /** End previous animation. */
		animationEnd: Option[Schema.AnimationEnd] = None
	)
	
	case class AnimationStatic(
	  /** Normalized coordinates based on output video resolution. Valid values: `0.0`–`1.0`. `xy` is the upper-left coordinate of the overlay object. For example, use the x and y coordinates {0,0} to position the top-left corner of the overlay animation in the top-left corner of the output video. */
		xy: Option[Schema.NormalizedCoordinate] = None,
	  /** The time to start displaying the overlay object, in seconds. Default: 0 */
		startTimeOffset: Option[String] = None
	)
	
	object AnimationFade {
		enum FadeTypeEnum extends Enum[FadeTypeEnum] { case FADE_TYPE_UNSPECIFIED, FADE_IN, FADE_OUT }
	}
	case class AnimationFade(
	  /** Required. Type of fade animation: `FADE_IN` or `FADE_OUT`. */
		fadeType: Option[Schema.AnimationFade.FadeTypeEnum] = None,
	  /** Normalized coordinates based on output video resolution. Valid values: `0.0`–`1.0`. `xy` is the upper-left coordinate of the overlay object. For example, use the x and y coordinates {0,0} to position the top-left corner of the overlay animation in the top-left corner of the output video. */
		xy: Option[Schema.NormalizedCoordinate] = None,
	  /** The time to start the fade animation, in seconds. Default: 0 */
		startTimeOffset: Option[String] = None,
	  /** The time to end the fade animation, in seconds. Default: `start_time_offset` + 1s */
		endTimeOffset: Option[String] = None
	)
	
	case class AnimationEnd(
	  /** The time to end overlay object, in seconds. Default: 0 */
		startTimeOffset: Option[String] = None
	)
	
	case class Encryption(
	  /** Required. Identifier for this set of encryption options. */
		id: Option[String] = None,
	  /** Configuration for AES-128 encryption. */
		aes128: Option[Schema.Aes128Encryption] = None,
	  /** Configuration for SAMPLE-AES encryption. */
		sampleAes: Option[Schema.SampleAesEncryption] = None,
	  /** Configuration for MPEG Common Encryption (MPEG-CENC). */
		mpegCenc: Option[Schema.MpegCommonEncryption] = None,
	  /** Keys are stored in Google Secret Manager. */
		secretManagerKeySource: Option[Schema.SecretManagerSource] = None,
	  /** Required. DRM system(s) to use; at least one must be specified. If a DRM system is omitted, it is considered disabled. */
		drmSystems: Option[Schema.DrmSystems] = None
	)
	
	case class Aes128Encryption(
	
	)
	
	case class SampleAesEncryption(
	
	)
	
	case class MpegCommonEncryption(
	  /** Required. Specify the encryption scheme. Supported encryption schemes: - `cenc` - `cbcs` */
		scheme: Option[String] = None
	)
	
	case class SecretManagerSource(
	  /** Required. The name of the Secret Version containing the encryption key in the following format: `projects/{project}/secrets/{secret_id}/versions/{version_number}` Note that only numbered versions are supported. Aliases like "latest" are not supported. */
		secretVersion: Option[String] = None
	)
	
	case class DrmSystems(
	  /** Widevine configuration. */
		widevine: Option[Schema.Widevine] = None,
	  /** Fairplay configuration. */
		fairplay: Option[Schema.Fairplay] = None,
	  /** Playready configuration. */
		playready: Option[Schema.Playready] = None,
	  /** Clearkey configuration. */
		clearkey: Option[Schema.Clearkey] = None
	)
	
	case class Widevine(
	
	)
	
	case class Fairplay(
	
	)
	
	case class Playready(
	
	)
	
	case class Clearkey(
	
	)
	
	case class Status(
	  /** The status code, which should be an enum value of google.rpc.Code. */
		code: Option[Int] = None,
	  /** A developer-facing error message, which should be in English. Any user-facing error message should be localized and sent in the google.rpc.Status.details field, or localized by the client. */
		message: Option[String] = None,
	  /** A list of messages that carry the error details. There is a common set of message types for APIs to use. */
		details: Option[List[Map[String, JsValue]]] = None
	)
	
	case class ListJobsResponse(
	  /** List of jobs in the specified region. */
		jobs: Option[List[Schema.Job]] = None,
	  /** The pagination token. */
		nextPageToken: Option[String] = None,
	  /** List of regions that could not be reached. */
		unreachable: Option[List[String]] = None
	)
	
	case class Empty(
	
	)
	
	case class JobTemplate(
	  /** The resource name of the job template. Format: `projects/{project_number}/locations/{location}/jobTemplates/{job_template}` */
		name: Option[String] = None,
	  /** The configuration for this template. */
		config: Option[Schema.JobConfig] = None,
	  /** The labels associated with this job template. You can use these to organize and group your job templates. */
		labels: Option[Map[String, String]] = None
	)
	
	case class ListJobTemplatesResponse(
	  /** List of job templates in the specified region. */
		jobTemplates: Option[List[Schema.JobTemplate]] = None,
	  /** The pagination token. */
		nextPageToken: Option[String] = None,
	  /** List of regions that could not be reached. */
		unreachable: Option[List[String]] = None
	)
}
