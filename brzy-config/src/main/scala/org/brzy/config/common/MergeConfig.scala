package org.brzy.config.common

/**
 * Document Me..
 * 
 * @author Michael Fortin
 * @version $Id: $
 */

trait MergeConfig[T] {
  def <<(that:T):T
}