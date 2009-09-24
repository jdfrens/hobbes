package org.norecess.hobbes.typechecker;

import org.norecess.citkit.types.PrimitiveType;
import org.norecess.citkit.visitors.ExpressionTIRVisitor;

public interface ITypeChecker extends ExpressionTIRVisitor<PrimitiveType> {

}
