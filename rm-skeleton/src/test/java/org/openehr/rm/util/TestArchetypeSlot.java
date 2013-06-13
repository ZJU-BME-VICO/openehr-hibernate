package org.openehr.rm.util;

import org.openehr.rm.binding.DADLBinding;

public class TestArchetypeSlot extends SkeletonGeneratorTestBase {

	public TestArchetypeSlot() throws Exception {		
	}
	
	public void testWithBloodPressureArchetype() throws Exception {
		archetype = loadArchetype("openEHR-EHR-OBSERVATION.archetype_slot.v1.adl");
		instance = generator.create(archetype, GenerationStrategy.MAXIMUM_EMPTY);
		DADLBinding binding = new DADLBinding();
		System.out.println(binding.toDADL(instance));
	}
}
