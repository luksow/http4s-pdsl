package pl.iterators.stir.server.directives

import org.http4s.{Method, Status}
import pl.iterators.stir.server._

trait MethodDirectives {
  import BasicDirectives._
  import RouteDirectives._
  import ParameterDirectives._
  import MethodDirectives._

  /**
   * Rejects all non-DELETE requests.
   *
   * @group method
   */
  def delete: Directive0 = _delete

  /**
   * Rejects all non-GET requests.
   *
   * @group method
   */
  def get: Directive0 = _get

  /**
   * Rejects all non-HEAD requests.
   *
   * @group method
   */
  def head: Directive0 = _head

  /**
   * Rejects all non-OPTIONS requests.
   *
   * @group method
   */
  def options: Directive0 = _options

  /**
   * Rejects all non-PATCH requests.
   *
   * @group method
   */
  def patch: Directive0 = _patch

  /**
   * Rejects all non-POST requests.
   *
   * @group method
   */
  def post: Directive0 = _post

  /**
   * Rejects all non-PUT requests.
   *
   * @group method
   */
  def put: Directive0 = _put

  /**
   * Extracts the request method.
   *
   * @group method
   */
  def extractMethod: Directive1[Method] = _extractMethod

  /**
   * Rejects all requests whose HTTP method does not match the given one.
   *
   * @group method
   */
  //#method
  def method(httpMethod: Method): Directive0 =
    extractMethod.flatMap[Unit] {
      case `httpMethod` if httpMethod == httpMethod => pass
      case _ => reject(MethodRejection(httpMethod))
    } & cancelRejections(classOf[MethodRejection])
  //#method

  /**
   * Changes the HTTP method of the request to the value of the specified query string parameter. If the query string
   * parameter is not specified this directive has no effect. If the query string is specified as something that is not
   * a HTTP method, then this directive completes the request with a `501 Not Implemented` response.
   *
   * This directive is useful for:
   *  - Use in combination with JSONP (JSONP only supports GET)
   *  - Supporting older browsers that lack support for certain HTTP methods. E.g. IE8 does not support PATCH
   *
   * @group method
   */
  def overrideMethodWithParameter(paramName: String): Directive0 =
    parameter(paramName.optional) flatMap {
      case Some(method) =>
        Method.fromString(method.toUpperCase) match {
          case Right(m) => mapRequest(_.withMethod(m))
          case _ => complete(Status.NotImplemented)
        }
      case None => pass
    }
}

object MethodDirectives extends MethodDirectives {
  private val _extractMethod: Directive1[Method] =
    BasicDirectives.extract(_.request.method)

  // format: OFF
  private val _delete: Directive0 = method(Method.DELETE)
  private val _get: Directive0 = method(Method.GET)
  private val _head: Directive0 = method(Method.HEAD)
  private val _options: Directive0 = method(Method.OPTIONS)
  private val _patch: Directive0 = method(Method.PATCH)
  private val _post: Directive0 = method(Method.POST)
  private val _put: Directive0 = method(Method.PUT)
}