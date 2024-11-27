# play-google-api

A comprehensive suite of Google SDKs that are idiomatically clean Scala 3 for Play Framework applications

## Example:
```scala
/** import common package */
import com.boresjo.play.api.*
/** import the package for the google API, in this case tasks */
import com.boresjo.play.api.google.tasks

/** A simple controller example */
class MyController @Inject()(val controllerComponents: ControllerComponents, oauth2: OAuth2Client, tasksApi: tasks.Api) extends BaseController {

  /** this method redirects to the google oauth2 login & authorisation page */ 
  def index() = Action { implicit request: Request[AnyContent] =>
    oauth2.accessRequest(tasksApi.scopes*)("/oauth2-callback")
  }

  /** handles the callback from the google and converts the supplied code into an OAuth2 token */
  def oauth2callback(code: String, scope: String) = Action.async { implicit request: Request[AnyContent] =>
    oauth2.requestToken(code)("/oauth2-callback").map { (token: OAuth2Token) =>
      import OAuth2Token.given
      TemporaryRedirect("http://localhost:9000/ok").withSession("google" -> Json.stringify(Json.toJson(token)))
    }
  }

  /** a simple example of how to use the google tasks API */
  def ok = Action.async { implicit request: Request[AnyContent] =>
    request.session.get("google").map { token =>
      val tokenRepository = oauth2.SimpleTokenRepository(OAuth2Token(token))
      given RequestSigner = new DefaultRequestSigner(tokenRepository)

      for (
        text1 <- tasksApi.tasklists.list().map { taskLists =>
          taskLists.items.get.map(i => s"${i.id}: " + i.title.get).mkString("\n")
        };
        nextToken <- tokenRepository.getCurrent()
      ) yield {
        Ok(text1 + "\n" + text2).withSession("google" -> Json.stringify(Json.toJson(nextToken)))
      }
    }.getOrElse {
      Future.successful(Unauthorized("No token"))
    }
  }
}
```