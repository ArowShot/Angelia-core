package com.github.maxopoly.angeliacore.block.states;

/**
 * Stuff like saplings with custom models
 *
 */
public class FancySpecialBlockState extends SingleConstBlockState {

	public FancySpecialBlockState(int id, byte metaData, float hardness, String texturePackIdentifier,
			String niceName) {
		super(id, metaData, hardness, texturePackIdentifier, niceName);
	}

	public FancySpecialBlockState(int id, float hardness, String texturePackIdentifier, String niceName) {
		super(id, (byte) 0, hardness, texturePackIdentifier, niceName);
	}

	@Override
	public boolean isFullBlock() {
		return false;
	}

	@Override
	public boolean isLiquid() {
		return false;
	}

	@Override
	public boolean isOpaque() {
		return false;
	}

}
