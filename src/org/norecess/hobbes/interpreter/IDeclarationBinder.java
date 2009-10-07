package org.norecess.hobbes.interpreter;

import java.util.List;

import org.norecess.citkit.environment.IEnvironment;
import org.norecess.citkit.tir.DeclarationTIR;
import org.norecess.citkit.tir.data.DatumTIR;
import org.norecess.citkit.visitors.DeclarationTIRVisitor;

public interface IDeclarationBinder extends
		DeclarationTIRVisitor<IEnvironment<DatumTIR>> {

	IEnvironment<DatumTIR> bind(List<? extends DeclarationTIR> declarations);

}
