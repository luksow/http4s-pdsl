package pl.iterators.stir.server.directives

/**
 * @groupname attribute Attribute directives
 * @groupprio attribute 115
 */
trait AttributeDirectives {
//  import BasicDirectives._
//  import RouteDirectives._
//
//  /**
//   * Extracts a request attribute value for the given key.
//   *
//   * @group attribute
//   */
//  def attribute[T](key: AttributeKey[T]): Directive1[T] =
//    extract(_.request.attribute(key)).flatMap {
//      case Some(value) => provide(value)
//      case None        => reject(new MissingAttributeRejection[T](key))
//    }
//
//  /**
//   * Extracts an optional request attribute for the given key.
//   *
//   * @group attribute
//   */
//  def optionalAttribute[T](key: AttributeKey[T]): Directive1[Option[T]] =
//    extract(_.request.attribute(key))
}

object AttributeDirectives extends AttributeDirectives
