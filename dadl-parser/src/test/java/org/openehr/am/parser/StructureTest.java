package org.openehr.am.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StructureTest extends ParserTestBase {

	public StructureTest(String test) {
		super(test);
	}

	public void testParseSimpleDADL() throws Exception {
		List<String> testFiles = new ArrayList<String>();
		testFiles.add("blood_pressure_001.dadl");
		testFiles.add("blood_pressure_002.dadl");
		testFiles.add("observation.dadl");
		
		for (String str : testFiles) {
			DADLParser parser = new DADLParser(
					loadFromClasspath(str));
			ContentObject content = parser.parse();

			List<Map<String, SimpleValue>> list = new ArrayList<Map<String, SimpleValue>>();

			List<AttributeValue> avs = content.getAttributeValues();
			if (avs != null) {
				for (AttributeValue av : avs) {
					list.add(buildKeyValueMap(av.getValue(), "/" + av.getId()));
				}			
			}

			ComplexObjectBlock cob = content.getComplexObjectBlock();
			if (cob != null) {
				list.add(buildKeyValueMap(cob, "/"));
			}

			for (Map<String, SimpleValue> map : list) {
				for (String key : map.keySet()) {
					System.out.print(key + " = " + map.get(key).getValue());
					System.out.println();
				}
			}
		}

	}

	public void testTypedObjectWithKeyedAttributes() throws Exception {
		DADLParser parser = new DADLParser(loadFromClasspath("person_001.dadl"));
		parser.parse();
	}

	private Map<String, SimpleValue> buildKeyValueMap(Parsed p,
			String relativePath) {
		Map<String, SimpleValue> map = new HashMap<String, SimpleValue>();

		if (p != null) {

			if (p instanceof ContentObject) {

				ContentObject content = (ContentObject) p;
				for (AttributeValue av : content.getAttributeValues()) {
					map.putAll(buildKeyValueMap(av.getValue(), relativePath
							.concat("/").concat(av.getId())));
				}

				ComplexObjectBlock cob = content.getComplexObjectBlock();
				if (cob != null) {
					map.putAll(buildKeyValueMap(cob, relativePath));
				}

			} else if (p instanceof SingleAttributeObjectBlock) {

				SingleAttributeObjectBlock saob = (SingleAttributeObjectBlock) p;
				for (AttributeValue av : saob.getAttributeValues()) {
					map.putAll(buildKeyValueMap(av.getValue(), relativePath
							.concat("/").concat(av.getId())));
				}

			} else if (p instanceof MultipleAttributeObjectBlock) {

				MultipleAttributeObjectBlock maob = (MultipleAttributeObjectBlock) p;
				for (KeyedObject ko : maob.getKeyObjects()) {
					map.putAll(buildKeyValueMap(
							ko.getObject(),
							relativePath.concat("/").concat(
									ko.getKey().getValue().toString())));
				}

			} else if (p instanceof PrimitiveObjectBlock) {

				PrimitiveObjectBlock po = (PrimitiveObjectBlock) p;
				map.put(relativePath, po.getSimpleValue());

			}

		}

		return map;
	}
}
