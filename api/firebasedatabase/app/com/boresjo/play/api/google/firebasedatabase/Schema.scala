package com.boresjo.play.api.google.firebasedatabase

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	case class ReenableDatabaseInstanceRequest(
	
	)
	
	case class DisableDatabaseInstanceRequest(
	
	)
	
	case class UndeleteDatabaseInstanceRequest(
	
	)
	
	object DatabaseInstance {
		enum StateEnum extends Enum[StateEnum] { case LIFECYCLE_STATE_UNSPECIFIED, ACTIVE, DISABLED, DELETED }
		enum TypeEnum extends Enum[TypeEnum] { case DATABASE_INSTANCE_TYPE_UNSPECIFIED, DEFAULT_DATABASE, USER_DATABASE }
	}
	case class DatabaseInstance(
	  /** The fully qualified resource name of the database instance, in the form: `projects/{project-number}/locations/{location-id}/instances/{database-id}`. */
		name: Option[String] = None,
	  /** Output only. The resource name of the project this instance belongs to. For example: `projects/{project-number}`. */
		project: Option[String] = None,
	  /** Output only. The database's lifecycle state. Read-only. */
		state: Option[Schema.DatabaseInstance.StateEnum] = None,
	  /** Output only. Output Only. The globally unique hostname of the database. */
		databaseUrl: Option[String] = None,
	  /** Immutable. The database instance type. On creation only USER_DATABASE is allowed, which is also the default when omitted. */
		`type`: Option[Schema.DatabaseInstance.TypeEnum] = None
	)
	
	case class ListDatabaseInstancesResponse(
	  /** List of each DatabaseInstance that is in the parent Firebase project. */
		instances: Option[List[Schema.DatabaseInstance]] = None,
	  /** If the result list is too large to fit in a single response, then a token is returned. If the string is empty, then this response is the last page of results. This token can be used in a subsequent call to `ListDatabaseInstances` to find the next group of database instances. Page tokens are short-lived and should not be persisted. */
		nextPageToken: Option[String] = None
	)
}
