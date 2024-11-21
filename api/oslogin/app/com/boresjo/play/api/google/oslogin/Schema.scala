package com.boresjo.play.api.google.oslogin

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	case class SshPublicKey(
	  /** Public key text in SSH format, defined by RFC4253 section 6.6. */
		key: Option[String] = None,
	  /** An expiration time in microseconds since epoch. */
		expirationTimeUsec: Option[String] = None,
	  /** Output only. The SHA-256 fingerprint of the SSH public key. */
		fingerprint: Option[String] = None,
	  /** Output only. The canonical resource name. */
		name: Option[String] = None
	)
	
	case class Empty(
	
	)
	
	case class LoginProfile(
	  /** Required. A unique user ID. */
		name: Option[String] = None,
	  /** The list of POSIX accounts associated with the user. */
		posixAccounts: Option[List[Schema.PosixAccount]] = None,
	  /** A map from SSH public key fingerprint to the associated key object. */
		sshPublicKeys: Option[Map[String, Schema.SshPublicKey]] = None
	)
	
	object PosixAccount {
		enum OperatingSystemTypeEnum extends Enum[OperatingSystemTypeEnum] { case OPERATING_SYSTEM_TYPE_UNSPECIFIED, LINUX, WINDOWS }
	}
	case class PosixAccount(
	  /** Only one POSIX account can be marked as primary. */
		primary: Option[Boolean] = None,
	  /** The username of the POSIX account. */
		username: Option[String] = None,
	  /** The user ID. */
		uid: Option[String] = None,
	  /** The default group ID. */
		gid: Option[String] = None,
	  /** The path to the home directory for this account. */
		homeDirectory: Option[String] = None,
	  /** The path to the logic shell for this account. */
		shell: Option[String] = None,
	  /** The GECOS (user information) entry for this account. */
		gecos: Option[String] = None,
	  /** System identifier for which account the username or uid applies to. By default, the empty value is used. */
		systemId: Option[String] = None,
	  /** Output only. A POSIX account identifier. */
		accountId: Option[String] = None,
	  /** The operating system type where this account applies. */
		operatingSystemType: Option[Schema.PosixAccount.OperatingSystemTypeEnum] = None,
	  /** Output only. The canonical resource name. */
		name: Option[String] = None
	)
	
	case class ImportSshPublicKeyResponse(
	  /** The login profile information for the user. */
		loginProfile: Option[Schema.LoginProfile] = None,
	  /** Detailed information about import results. */
		details: Option[String] = None
	)
}
