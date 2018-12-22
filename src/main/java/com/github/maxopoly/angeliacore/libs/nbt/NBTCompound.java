package com.github.maxopoly.angeliacore.libs.nbt;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.ArrayUtils;

public class NBTCompound extends NBTElement {

	public static final byte COMPOUND_START_ID = 10;

	public static final byte COMPOUND_END_ID = 0;
	private Map<String, NBTElement> content;

	public NBTCompound(String name) {
		super(name);
		this.content = new HashMap<String, NBTElement>();
	}

	public void add(NBTElement element) {
		if (element.getName() == null) {
			throw new IllegalArgumentException("Can't add unnamed tags to compounds");
		}
		content.put(element.getName(), element);
	}

	@Override
	public NBTElement clone() {
		NBTCompound comp = new NBTCompound(name);
		for (NBTElement element : content.values()) {
			comp.add(element.clone());
		}
		return comp;
	}

	@Override
	public boolean equals(Object o) {
		return o instanceof NBTCompound && ((NBTCompound) o).content.equals(content);
	}

	public NBTElement getElement(String name) {
		return content.get(name);
	}

	@Override
	public byte getID() {
		return COMPOUND_START_ID;
	}

	@Override
	public String getTypeName() {
		return "compound";
	}

	public byte[] serialize() {
		return ArrayUtils.addAll(serializingPrefix(), serializeContent());
	}

	@Override
	public byte[] serializeContent() {
		// to avoid copying everything around often we first collect results in a list
		// and only concatenate them once
		List<byte[]> elements = new LinkedList<byte[]>();
		int length = 0;
		for (NBTElement element : content.values()) {
			byte[] temp = ArrayUtils.addAll(element.serializingPrefix(), element.serializeContent());
			elements.add(temp);
			length += temp.length;
		}
		byte[] res = new byte[length + 1];
		int index = 0;
		for (byte[] curr : elements) {
			for (int i = 0; i < curr.length; i++) {
				res[i + index] = curr[i];
			}
			index += curr.length;
		}
		// end tag
		res[res.length - 1] = 0;
		return res;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (Entry<String, NBTElement> entry : content.entrySet()) {
			sb.append(entry.getKey());
			sb.append(": ");
			sb.append(entry.getValue());
			sb.append(", ");
		}
		// remove trailing comma
		int length = sb.length() > 0 ? sb.length() - 2 : sb.length();
		return String.format("%s: {%s}", name != null ? name : "", sb.substring(0, length));
	}
}
