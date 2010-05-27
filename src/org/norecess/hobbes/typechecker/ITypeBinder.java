package org.norecess.hobbes.typechecker;

import org.norecess.citkit.environment.IEnvironment;
import org.norecess.citkit.types.HobbesType;
import org.norecess.citkit.visitors.DeclarationTIRVisitor;

public interface ITypeBinder extends
		DeclarationTIRVisitor<IEnvironment<HobbesType>> {

}
