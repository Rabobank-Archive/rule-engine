package nl.rabobank.oss.rules

import nl.rabobank.oss.rules.derivations.Node
import nl.rabobank.oss.rules.facts.Fact

package object engine {

  type Context = Map[Fact[Any], Any]
  type Condition = Context => Boolean
  type Input = List[Fact[Any]]
  type Output = Fact[Any]
  type Level = List[Node]
  type Levels = List[Level]

}
